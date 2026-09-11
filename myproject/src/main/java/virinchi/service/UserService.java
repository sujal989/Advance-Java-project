package virinchi.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import virinchi.model.UserTable;
import virinchi.repository.UserRepository;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;


    // BCrypt password encoder
    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();


    // =========================
    // SIGNUP
    // =========================
    public boolean signup(
            String username,
            String email,
            String password
    ) {

        username = username.trim();
        email = email.trim();

        if (userRepository.existsByUsernameOrEmail(
                username,
                email
        )) {

            System.out.println(
                    "Signup failed - user already exists: "
                            + username
            );

            return false;
        }


        // Hash password using BCrypt
        String hashedPassword =
                passwordEncoder.encode(
                        password.trim()
                );


        // Generate 6-digit verification code
        SecureRandom random =
                new SecureRandom();

        int randomCode =
                100000 +
                        random.nextInt(900000);

        String verificationCode =
                String.valueOf(randomCode);


        UserTable newUser =
                new UserTable();

        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);
        newUser.setRole("user");
        newUser.setActive(true);


        // Not verified yet
        newUser.setEmailVerified(false);

        newUser.setVerificationCode(
                verificationCode
        );


        userRepository.save(newUser);


        // Send code to Gmail
        emailService.sendVerificationEmail(
                email,
                username,
                verificationCode
        );


        System.out.println(
                "Verification code sent to: "
                        + email
        );

        return true;
    }


    // =========================
    // VERIFY EMAIL
    // =========================
    public boolean verifyEmail(
            String email,
            String code
    ) {

        Optional<UserTable> userOpt =
                userRepository.findByEmail(
                        email.trim()
                );


        // User not found
        if (userOpt.isEmpty()) {

            System.out.println(
                    "Verification failed - email not found"
            );

            return false;
        }


        UserTable user =
                userOpt.get();


        // Already verified
        if (user.isEmailVerified()) {
            return true;
        }


        // Check verification code
        if (
                user.getVerificationCode() != null &&
                        user.getVerificationCode()
                                .equals(code.trim())
        ) {

            user.setEmailVerified(true);


            // Remove code after verification
            user.setVerificationCode(null);


            userRepository.save(user);


            System.out.println(
                    "Email verified successfully: "
                            + email
            );

            return true;
        }


        System.out.println(
                "Verification failed - incorrect code"
        );

        return false;
    }


    // =========================
    // LOGIN
    // =========================
    public UserTable login(
            String username,
            String password
    ) {

        Optional<UserTable> userOpt =
                userRepository.findByUsername(
                        username.trim()
                );


        if (userOpt.isEmpty()) {

            System.out.println(
                    "Login failed - user not found: "
                            + username
            );

            return null;
        }


        UserTable user =
                userOpt.get();


        // Account disabled by admin
        if (!user.isActive()) {

            System.out.println(
                    "Login failed - account inactive: "
                            + username
            );

            return null;
        }


        // Normal users must verify email
        if (
                !user.isEmailVerified() &&
                        !"admin".equalsIgnoreCase(
                                user.getRole()
                        )
        ) {

            System.out.println(
                    "Login failed - email not verified: "
                            + username
            );

            return null;
        }


        String storedPassword =
                user.getPassword();

        String inputPassword =
                password.trim();


        // =========================
        // BCrypt account
        // =========================
        if (
                storedPassword.startsWith("$2a$") ||
                        storedPassword.startsWith("$2b$") ||
                        storedPassword.startsWith("$2y$")
        ) {

            if (
                    passwordEncoder.matches(
                            inputPassword,
                            storedPassword
                    )
            ) {

                System.out.println(
                        "Login successful: "
                                + username
                );

                return user;
            }
        }


        // =========================
        // OLD SHA-256 ACCOUNT
        // =========================
        else {

            String oldHashedPassword =
                    DigestUtils.sha256Hex(
                            inputPassword
                    );


            if (
                    storedPassword.equals(
                            oldHashedPassword
                    )
            ) {

                // Upgrade old password to BCrypt
                String newHashedPassword =
                        passwordEncoder.encode(
                                inputPassword
                        );

                user.setPassword(
                        newHashedPassword
                );

                userRepository.save(user);


                System.out.println(
                        "Password upgraded to BCrypt for: "
                                + username
                );


                System.out.println(
                        "Login successful: "
                                + username
                );

                return user;
            }
        }


        System.out.println(
                "Login failed - wrong password: "
                        + username
        );

        return null;
    }
}
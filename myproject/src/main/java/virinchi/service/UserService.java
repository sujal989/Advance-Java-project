package virinchi.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virinchi.model.UserTable;
import virinchi.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ---------- Signup ----------
    public boolean signup(String username, String email, String password) {

        // 1. Check if username OR email already exists
        if (userRepository.existsByUsernameOrEmail(username.trim(), email.trim())) {
            System.out.println("Signup failed - user already exists: " + username);
            return false;
        }

        // 2. Hash the password (same algorithm as the old app - SHA-256)
        String hashedPassword = DigestUtils.sha256Hex(password.trim());

        // 3. Save the new user
        UserTable newUser = new UserTable();
        newUser.setUsername(username.trim());
        newUser.setEmail(email.trim());
        newUser.setPassword(hashedPassword);
        newUser.setRole("user");
        newUser.setActive(true);

        userRepository.save(newUser);
        System.out.println("Signup successful for: " + username);
        return true;
    }

    // ---------- Login ----------
    public UserTable login(String username, String password) {

        Optional<UserTable> userOpt = userRepository.findByUsername(username.trim());

        if (userOpt.isEmpty()) {
            System.out.println("Login failed - user not found: " + username);
            return null;
        }

        UserTable user = userOpt.get();
        String inputHashedPassword = DigestUtils.sha256Hex(password.trim());

        if (user.getPassword().equals(inputHashedPassword)) {
            System.out.println("Login successful: " + username);
            return user;
        } else {
            System.out.println("Login failed - wrong password: " + username);
            return null;
        }
    }
}
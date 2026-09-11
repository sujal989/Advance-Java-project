package virinchi.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import virinchi.model.UserTable;
import virinchi.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;


    // =========================
    // SIGNUP
    // =========================
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {

        Map<String, Object> response = new HashMap<>();

        boolean success =
                userService.signup(
                        username,
                        email,
                        password
                );


        if (success) {

            response.put("success", true);

            response.put(
                    "message",
                    "Signup successful. Verification code sent to your email."
            );

            response.put("email", email);

            return ResponseEntity.ok(response);

        } else {

            response.put("success", false);

            response.put(
                    "message",
                    "Username or email already exists"
            );

            return ResponseEntity
                    .badRequest()
                    .body(response);
        }
    }


    // =========================
    // VERIFY EMAIL
    // =========================
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyEmail(
            @RequestParam String email,
            @RequestParam String code) {

        Map<String, Object> response =
                new HashMap<>();


        boolean verified =
                userService.verifyEmail(
                        email,
                        code
                );


        if (verified) {

            response.put("success", true);

            response.put(
                    "message",
                    "Email verified successfully"
            );

            return ResponseEntity.ok(response);

        } else {

            response.put("success", false);

            response.put(
                    "message",
                    "Invalid email or verification code"
            );

            return ResponseEntity
                    .badRequest()
                    .body(response);
        }
    }


    // =========================
    // LOGIN
    // =========================
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session) {

        Map<String, Object> response =
                new HashMap<>();


        UserTable user =
                userService.login(
                        username,
                        password
                );


        if (user != null) {

            // Store user details in backend session
            session.setAttribute(
                    "username",
                    user.getUsername()
            );

            session.setAttribute(
                    "role",
                    user.getRole()
            );


            response.put("success", true);

            response.put(
                    "message",
                    "Login successful"
            );

            response.put(
                    "username",
                    user.getUsername()
            );

            response.put(
                    "email",
                    user.getEmail()
            );

            response.put(
                    "role",
                    user.getRole()
            );


            return ResponseEntity.ok(response);

        } else {

            response.put("success", false);

            response.put(
                    "message",
                    "Invalid credentials, inactive account, or email not verified"
            );


            return ResponseEntity
                    .status(401)
                    .body(response);
        }
    }


    // =========================
    // CHECK SESSION
    // =========================
    @GetMapping("/session")
    public ResponseEntity<Map<String, Object>> checkSession(
            HttpSession session) {

        Map<String, Object> response =
                new HashMap<>();


        String username =
                (String) session.getAttribute(
                        "username"
                );

        String role =
                (String) session.getAttribute(
                        "role"
                );


        if (username == null) {

            response.put("loggedIn", false);

            return ResponseEntity
                    .status(401)
                    .body(response);
        }


        response.put("loggedIn", true);
        response.put("username", username);
        response.put("role", role);


        return ResponseEntity.ok(response);
    }


    // =========================
    // LOGOUT
    // =========================
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(
            HttpSession session) {

        session.invalidate();


        Map<String, Object> response =
                new HashMap<>();

        response.put("success", true);

        response.put(
                "message",
                "Logout successful"
        );


        return ResponseEntity.ok(response);
    }
}
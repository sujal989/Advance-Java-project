package virinchi.controller;

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

    // POST /api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {

        Map<String, Object> response = new HashMap<>();
        boolean success = userService.signup(username, email, password);

        if (success) {
            response.put("success", true);
            response.put("message", "Signup successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Username or email already exists");
            return ResponseEntity.badRequest().body(response);
        }
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestParam String username,
            @RequestParam String password) {

        Map<String, Object> response = new HashMap<>();
        UserTable user = userService.login(username, password);

        if (user != null) {
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("username", user.getUsername());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(401).body(response);
        }
    }
}
package virinchi.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import virinchi.model.UserTable;
import virinchi.service.AdminService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    // =========================
    // CHECK ADMIN
    // =========================
    private ResponseEntity<Map<String, Object>> checkAdmin(
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


        // Not logged in
        if (username == null) {

            response.put("success", false);
            response.put(
                    "message",
                    "You must login first"
            );

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }


        // Logged in but not admin
        if (
                role == null ||
                        !role.equalsIgnoreCase("admin")
        ) {

            response.put("success", false);
            response.put(
                    "message",
                    "Admin access required"
            );

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(response);
        }


        // Admin is allowed
        return null;
    }


    // =========================
    // GET ALL USERS
    // =========================
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(
            HttpSession session) {

        ResponseEntity<Map<String, Object>> accessCheck =
                checkAdmin(session);

        if (accessCheck != null) {
            return accessCheck;
        }


        List<UserTable> users =
                adminService.getAllUsers();

        List<Map<String, Object>> response =
                new ArrayList<>();


        for (UserTable user : users) {

            Map<String, Object> userData =
                    new HashMap<>();

            userData.put(
                    "id",
                    user.getId()
            );

            userData.put(
                    "username",
                    user.getUsername()
            );

            userData.put(
                    "email",
                    user.getEmail()
            );

            userData.put(
                    "role",
                    user.getRole()
            );

            userData.put(
                    "active",
                    user.isActive()
            );


            response.add(userData);
        }


        return ResponseEntity.ok(response);
    }


    // =========================
    // ACTIVATE / DEACTIVATE USER
    // =========================
    @PutMapping("/users/{id}/status")
    public ResponseEntity<?> changeUserStatus(
            @PathVariable int id,
            @RequestParam boolean active,
            HttpSession session) {

        ResponseEntity<Map<String, Object>> accessCheck =
                checkAdmin(session);

        if (accessCheck != null) {
            return accessCheck;
        }


        UserTable user =
                adminService.changeUserStatus(
                        id,
                        active
                );


        Map<String, Object> response =
                new HashMap<>();


        if (user == null) {

            response.put("success", false);
            response.put(
                    "message",
                    "User not found"
            );

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }


        response.put("success", true);

        response.put(
                "message",
                "User status updated"
        );

        response.put(
                "id",
                user.getId()
        );

        response.put(
                "active",
                user.isActive()
        );


        return ResponseEntity.ok(response);
    }


    // =========================
    // DELETE USER
    // =========================
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable int id,
            HttpSession session) {

        ResponseEntity<Map<String, Object>> accessCheck =
                checkAdmin(session);

        if (accessCheck != null) {
            return accessCheck;
        }


        Map<String, Object> response =
                new HashMap<>();


        boolean deleted =
                adminService.deleteUser(id);


        if (deleted) {

            response.put("success", true);

            response.put(
                    "message",
                    "User deleted successfully"
            );


            return ResponseEntity.ok(response);
        }


        response.put("success", false);

        response.put(
                "message",
                "User not found or admin account cannot be deleted"
        );


        return ResponseEntity
                .badRequest()
                .body(response);
    }
}
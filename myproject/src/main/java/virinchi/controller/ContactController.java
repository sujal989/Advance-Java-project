package virinchi.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import virinchi.model.ContactMessage;
import virinchi.service.ContactService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class ContactController {

    @Autowired
    private ContactService contactService;


    // =========================
    // CHECK LOGIN
    // =========================
    private ResponseEntity<Map<String, Object>> checkLogin(
            HttpSession session
    ) {

        String username =
                (String) session.getAttribute("username");

        if (username == null) {

            Map<String, Object> response =
                    new HashMap<>();

            response.put("success", false);
            response.put(
                    "message",
                    "You must login first"
            );

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }

        return null;
    }


    // =========================
    // CHECK ADMIN
    // =========================
    private ResponseEntity<Map<String, Object>> checkAdmin(
            HttpSession session
    ) {

        ResponseEntity<Map<String, Object>> loginCheck =
                checkLogin(session);

        if (loginCheck != null) {
            return loginCheck;
        }


        String role =
                (String) session.getAttribute("role");


        if (
                role == null ||
                        !role.equalsIgnoreCase("admin")
        ) {

            Map<String, Object> response =
                    new HashMap<>();

            response.put("success", false);
            response.put(
                    "message",
                    "Admin access required"
            );

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(response);
        }


        return null;
    }


    // =========================
    // SAVE CONTACT MESSAGE
    // =========================
    @PostMapping
    public ResponseEntity<?> saveMessage(
            @Valid @RequestBody ContactMessage message,
            HttpSession session
    ) {

        ResponseEntity<Map<String, Object>> loginCheck =
                checkLogin(session);

        if (loginCheck != null) {
            return loginCheck;
        }


        String loggedInUsername =
                (String) session.getAttribute("username");


        // Do not trust username sent from frontend
        message.setUsername(loggedInUsername);


        ContactMessage savedMessage =
                contactService.saveMessage(message);


        return ResponseEntity.ok(savedMessage);
    }


    // =========================
    // GET ALL MESSAGES - ADMIN
    // =========================
    @GetMapping
    public ResponseEntity<?> getAllMessages(
            HttpSession session
    ) {

        ResponseEntity<Map<String, Object>> adminCheck =
                checkAdmin(session);

        if (adminCheck != null) {
            return adminCheck;
        }


        List<ContactMessage> messages =
                contactService.getAllMessages();


        return ResponseEntity.ok(messages);
    }
}
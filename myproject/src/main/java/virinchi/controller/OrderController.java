package virinchi.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import virinchi.model.Order;
import virinchi.service.OrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    // =========================
    // CHECK LOGIN
    // =========================
    private ResponseEntity<Map<String, Object>> checkLogin(
            HttpSession session) {

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
            HttpSession session) {

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
    // CREATE ORDER
    // =========================
    @PostMapping
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody Order order,
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
        order.setUsername(loggedInUsername);


        Order savedOrder =
                orderService.createOrder(order);


        return ResponseEntity.ok(savedOrder);
    }


    // =========================
    // GET ORDERS FOR ONE USER
    // =========================
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserOrders(
            @PathVariable String username,
            HttpSession session
    ) {

        ResponseEntity<Map<String, Object>> loginCheck =
                checkLogin(session);

        if (loginCheck != null) {
            return loginCheck;
        }


        String loggedInUsername =
                (String) session.getAttribute("username");

        String role =
                (String) session.getAttribute("role");


        // User can only view their own orders
        // Admin can view any user's orders
        if (
                !username.equals(loggedInUsername) &&
                        !"admin".equalsIgnoreCase(role)
        ) {

            Map<String, Object> response =
                    new HashMap<>();

            response.put("success", false);
            response.put(
                    "message",
                    "You cannot view another user's orders"
            );

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(response);
        }


        List<Order> orders =
                orderService
                        .getOrdersByUsername(username);


        return ResponseEntity.ok(orders);
    }


    // =========================
    // GET ALL ORDERS - ADMIN
    // =========================
    @GetMapping
    public ResponseEntity<?> getAllOrders(
            HttpSession session
    ) {

        ResponseEntity<Map<String, Object>> adminCheck =
                checkAdmin(session);

        if (adminCheck != null) {
            return adminCheck;
        }


        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }


    // =========================
    // GET ONE ORDER
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(
            @PathVariable int id,
            HttpSession session
    ) {

        ResponseEntity<Map<String, Object>> loginCheck =
                checkLogin(session);

        if (loginCheck != null) {
            return loginCheck;
        }


        Order order =
                orderService.getOrderById(id);


        if (order == null) {

            Map<String, Object> response =
                    new HashMap<>();

            response.put("success", false);
            response.put(
                    "message",
                    "Order not found"
            );

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }


        String loggedInUsername =
                (String) session.getAttribute("username");

        String role =
                (String) session.getAttribute("role");


        // User can only view their own order
        // Admin can view any order
        if (
                !order.getUsername()
                        .equals(loggedInUsername) &&
                        !"admin".equalsIgnoreCase(role)
        ) {

            Map<String, Object> response =
                    new HashMap<>();

            response.put("success", false);
            response.put(
                    "message",
                    "You cannot view this order"
            );

            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(response);
        }


        return ResponseEntity.ok(order);
    }


    // =========================
    // UPDATE ORDER STATUS - ADMIN
    // =========================
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable int id,
            @RequestParam String status,
            HttpSession session
    ) {

        ResponseEntity<Map<String, Object>> adminCheck =
                checkAdmin(session);

        if (adminCheck != null) {
            return adminCheck;
        }


        Order updatedOrder =
                orderService.updateOrderStatus(
                        id,
                        status
                );


        if (updatedOrder == null) {

            Map<String, Object> response =
                    new HashMap<>();

            response.put("success", false);
            response.put(
                    "message",
                    "Order not found"
            );

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }


        return ResponseEntity.ok(updatedOrder);
    }
}
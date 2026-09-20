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

        // Never trust username sent from frontend
        order.setUsername(loggedInUsername);

        Order savedOrder =
                orderService.createOrder(order);

        return ResponseEntity.ok(savedOrder);
    }


    // =========================
    // GET USER ORDERS
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
            return createMessage(
                    HttpStatus.NOT_FOUND,
                    "Order not found"
            );
        }

        String loggedInUsername =
                (String) session.getAttribute("username");

        String role =
                (String) session.getAttribute("role");

        if (
                !order.getUsername()
                        .equals(loggedInUsername) &&
                        !"admin".equalsIgnoreCase(role)
        ) {

            return createMessage(
                    HttpStatus.FORBIDDEN,
                    "You cannot view this order"
            );
        }

        return ResponseEntity.ok(order);
    }


    // =========================
    // CANCEL OWN ORDER
    // =========================
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(
            @PathVariable int id,
            HttpSession session
    ) {

        ResponseEntity<Map<String, Object>> loginCheck =
                checkLogin(session);

        if (loginCheck != null) {
            return loginCheck;
        }

        String username =
                (String) session.getAttribute("username");

        Order order =
                orderService.getOrderById(id);

        if (order == null) {
            return createMessage(
                    HttpStatus.NOT_FOUND,
                    "Order not found"
            );
        }

        // Customer can only cancel their own order
        if (!order.getUsername().equals(username)) {

            return createMessage(
                    HttpStatus.FORBIDDEN,
                    "You cannot cancel this order"
            );
        }

        try {

            Order cancelledOrder =
                    orderService.cancelOrder(id);

            return ResponseEntity.ok(
                    cancelledOrder
            );

        } catch (RuntimeException exception) {

            return createMessage(
                    HttpStatus.BAD_REQUEST,
                    exception.getMessage()
            );
        }
    }


    // =========================
    // DELETE OWN ORDER
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(
            @PathVariable int id,
            HttpSession session
    ) {

        ResponseEntity<Map<String, Object>> loginCheck =
                checkLogin(session);

        if (loginCheck != null) {
            return loginCheck;
        }

        String username =
                (String) session.getAttribute("username");

        Order order =
                orderService.getOrderById(id);

        if (order == null) {
            return createMessage(
                    HttpStatus.NOT_FOUND,
                    "Order not found"
            );
        }

        // Customer can only delete their own order
        if (!order.getUsername().equals(username)) {

            return createMessage(
                    HttpStatus.FORBIDDEN,
                    "You cannot delete this order"
            );
        }

        try {

            orderService.deleteOrder(id);

            Map<String, Object> response =
                    new HashMap<>();

            response.put("success", true);
            response.put(
                    "message",
                    "Order deleted successfully"
            );

            return ResponseEntity.ok(response);

        } catch (RuntimeException exception) {

            return createMessage(
                    HttpStatus.BAD_REQUEST,
                    exception.getMessage()
            );
        }
    }


    // =========================
    // UPDATE STATUS - ADMIN
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

            return createMessage(
                    HttpStatus.NOT_FOUND,
                    "Order not found"
            );
        }

        return ResponseEntity.ok(updatedOrder);
    }


    // =========================
    // MESSAGE RESPONSE
    // =========================
    private ResponseEntity<Map<String, Object>> createMessage(
            HttpStatus status,
            String message
    ) {

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "success",
                status.is2xxSuccessful()
        );

        response.put(
                "message",
                message
        );

        return ResponseEntity
                .status(status)
                .body(response);
    }
}
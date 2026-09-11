package virinchi.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import virinchi.model.Product;
import virinchi.service.ProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    // =========================
    // CHECK ADMIN
    // =========================
    private ResponseEntity<Map<String, Object>> checkAdmin(
            HttpSession session
    ) {

        String username =
                (String) session.getAttribute("username");

        String role =
                (String) session.getAttribute("role");


        // Not logged in
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


        // Logged in but not admin
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
    // GET ALL PRODUCTS - PUBLIC
    // =========================
    @GetMapping
    public List<Product> getAllProducts() {

        return productService.getAllProducts();
    }


    // =========================
    // GET PRODUCT BY ID - PUBLIC
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @PathVariable int id
    ) {

        Product product =
                productService.getProductById(id);


        if (product == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        return ResponseEntity.ok(product);
    }


    // =========================
    // ADD PRODUCT - ADMIN
    // =========================
    @PostMapping
    public ResponseEntity<?> addProduct(
            @RequestBody Product product,
            HttpSession session
    ) {

        ResponseEntity<Map<String, Object>> adminCheck =
                checkAdmin(session);


        if (adminCheck != null) {
            return adminCheck;
        }


        Product savedProduct =
                productService.addProduct(product);


        return ResponseEntity.ok(savedProduct);
    }


    // =========================
    // UPDATE PRODUCT - ADMIN
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable int id,
            @RequestBody Product product,
            HttpSession session
    ) {

        ResponseEntity<Map<String, Object>> adminCheck =
                checkAdmin(session);


        if (adminCheck != null) {
            return adminCheck;
        }


        Product updatedProduct =
                productService.updateProduct(
                        id,
                        product
                );


        if (updatedProduct == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        return ResponseEntity.ok(updatedProduct);
    }


    // =========================
    // DELETE PRODUCT - ADMIN
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable int id,
            HttpSession session
    ) {

        ResponseEntity<Map<String, Object>> adminCheck =
                checkAdmin(session);


        if (adminCheck != null) {
            return adminCheck;
        }


        boolean deleted =
                productService.deleteProduct(id);


        if (!deleted) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        return ResponseEntity.ok(
                "Product deleted successfully"
        );
    }


    // =========================
    // UPLOAD PRODUCT IMAGE - ADMIN
    // =========================
    @PostMapping(
            value = "/{id}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> uploadProductImage(
            @PathVariable int id,
            @RequestParam("file") MultipartFile file,
            HttpSession session
    ) {

        ResponseEntity<Map<String, Object>> adminCheck =
                checkAdmin(session);


        if (adminCheck != null) {
            return adminCheck;
        }


        try {

            if (file.isEmpty()) {

                return ResponseEntity
                        .badRequest()
                        .body("Please select an image");
            }


            if (
                    file.getContentType() == null ||
                            !file.getContentType().startsWith("image/")
            ) {

                return ResponseEntity
                        .badRequest()
                        .body("Only image files are allowed");
            }


            Product product =
                    productService.uploadProductImage(
                            id,
                            file
                    );


            if (product == null) {

                return ResponseEntity
                        .notFound()
                        .build();
            }


            return ResponseEntity.ok(
                    "Product image uploaded successfully"
            );


        } catch (IOException e) {

            return ResponseEntity
                    .internalServerError()
                    .body("Could not upload image");
        }
    }


    // =========================
    // GET PRODUCT IMAGE - PUBLIC
    // =========================
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProductImage(
            @PathVariable int id
    ) {

        byte[] imageData =
                productService.getProductImage(id);


        String imageType =
                productService.getProductImageType(id);


        if (
                imageData == null ||
                        imageType == null
        ) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        return ResponseEntity
                .ok()
                .contentType(
                        MediaType.parseMediaType(
                                imageType
                        )
                )
                .body(imageData);
    }
}
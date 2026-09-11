package virinchi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import virinchi.model.Order;
import virinchi.model.OrderItem;
import virinchi.model.Product;

import virinchi.repository.OrderRepository;
import virinchi.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    // Create a new order
    @Transactional
    public Order createOrder(Order order) {

        double subtotal = 0;

        order.setStatus("Order Placed");
        order.setCreatedAt(LocalDateTime.now());


        if (order.getItems() != null) {

            for (OrderItem item : order.getItems()) {

                Product product =
                        productRepository
                                .findById(item.getProductId())
                                .orElse(null);


                if (product == null) {

                    throw new RuntimeException(
                            "Product not found: " +
                                    item.getProductId()
                    );
                }


                int quantity =
                        item.getQuantity();


                if (quantity <= 0) {

                    throw new RuntimeException(
                            "Invalid quantity"
                    );
                }


                if (quantity > product.getStock()) {

                    throw new RuntimeException(
                            "Not enough stock for " +
                                    product.getName()
                    );
                }


                item.setName(product.getName());
                item.setPrice(product.getPrice());
                item.setImage(product.getImage());

                item.setOrder(order);


                subtotal +=
                        product.getPrice() *
                                quantity;


                int newStock =
                        product.getStock() -
                                quantity;

                product.setStock(newStock);

                productRepository.save(product);
            }
        }


        double delivery =
                subtotal > 0
                        ? 100
                        : 0;


        double total =
                subtotal + delivery;


        order.setSubtotal(subtotal);
        order.setDelivery(delivery);
        order.setTotal(total);


        return orderRepository.save(order);
    }


    // Get orders belonging to one user
    public List<Order> getOrdersByUsername(
            String username
    ) {

        return orderRepository
                .findByUsernameOrderByCreatedAtDesc(
                        username
                );
    }


    // Get every order - used by admin
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }


    // Get one order
    public Order getOrderById(int id) {

        return orderRepository
                .findById(id)
                .orElse(null);
    }


    // Change order status - used by admin
    public Order updateOrderStatus(
            int id,
            String status
    ) {

        Order order =
                orderRepository
                        .findById(id)
                        .orElse(null);


        if (order == null) {
            return null;
        }


        // Only allow valid order statuses
        if (
                !status.equalsIgnoreCase("Order Placed") &&
                        !status.equalsIgnoreCase("Processing") &&
                        !status.equalsIgnoreCase("Shipped") &&
                        !status.equalsIgnoreCase("Delivered") &&
                        !status.equalsIgnoreCase("Cancelled")
        ) {

            throw new RuntimeException(
                    "Invalid order status"
            );
        }


        // Save status with consistent formatting
        if (status.equalsIgnoreCase("Order Placed")) {
            order.setStatus("Order Placed");

        } else if (status.equalsIgnoreCase("Processing")) {
            order.setStatus("Processing");

        } else if (status.equalsIgnoreCase("Shipped")) {
            order.setStatus("Shipped");

        } else if (status.equalsIgnoreCase("Delivered")) {
            order.setStatus("Delivered");

        } else if (status.equalsIgnoreCase("Cancelled")) {
            order.setStatus("Cancelled");
        }


        return orderRepository.save(order);
    }
}
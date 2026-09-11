package virinchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import virinchi.model.Order;

import java.util.List;

public interface OrderRepository
        extends JpaRepository<Order, Integer> {

    List<Order> findByUsernameOrderByCreatedAtDesc(
            String username
    );
}
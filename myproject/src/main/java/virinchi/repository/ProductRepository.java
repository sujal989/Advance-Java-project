package virinchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import virinchi.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
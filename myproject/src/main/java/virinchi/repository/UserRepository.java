package virinchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import virinchi.model.UserTable;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserTable, Integer> {

    Optional<UserTable> findByUsername(String username);

    Optional<UserTable> findByEmail(String email);

    boolean existsByUsernameOrEmail(String username, String email);
}
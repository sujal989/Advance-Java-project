package virinchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import virinchi.model.ContactMessage;

import java.util.List;

public interface ContactMessageRepository
        extends JpaRepository<ContactMessage, Integer> {

    List<ContactMessage>
    findByUsernameOrderByCreatedAtDesc(String username);

}
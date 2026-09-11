package virinchi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import virinchi.model.ContactMessage;

public interface ContactMessageRepository
        extends JpaRepository<ContactMessage, Integer> {
}
package virinchi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virinchi.model.ContactMessage;
import virinchi.repository.ContactMessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;


    // =========================
    // SAVE NEW MESSAGE
    // =========================
    public ContactMessage saveMessage(
            ContactMessage message
    ) {

        message.setCreatedAt(
                LocalDateTime.now()
        );

        // New message has no admin reply yet
        message.setAdminReply(null);
        message.setRepliedAt(null);

        return contactMessageRepository.save(
                message
        );
    }


    // =========================
    // GET ALL - ADMIN
    // =========================
    public List<ContactMessage> getAllMessages() {

        return contactMessageRepository.findAll();
    }


    // =========================
    // GET USER'S OWN MESSAGES
    // =========================
    public List<ContactMessage> getMessagesByUsername(
            String username
    ) {

        return contactMessageRepository
                .findByUsernameOrderByCreatedAtDesc(
                        username
                );
    }


    // =========================
    // ADMIN REPLY
    // =========================
    public ContactMessage replyToMessage(
            int id,
            String reply
    ) {

        ContactMessage message =
                contactMessageRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Message not found"
                                )
                        );


        if (
                reply == null ||
                        reply.trim().isEmpty()
        ) {

            throw new RuntimeException(
                    "Reply cannot be empty"
            );
        }


        if (reply.trim().length() > 2000) {

            throw new RuntimeException(
                    "Reply cannot be more than 2000 characters"
            );
        }


        message.setAdminReply(
                reply.trim()
        );


        message.setRepliedAt(
                LocalDateTime.now()
        );


        return contactMessageRepository.save(
                message
        );
    }


    // =========================
    // DELETE MESSAGE
    // =========================
    public void deleteMessage(int id) {

        ContactMessage message =
                contactMessageRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Message not found"
                                )
                        );


        contactMessageRepository.delete(
                message
        );
    }
}
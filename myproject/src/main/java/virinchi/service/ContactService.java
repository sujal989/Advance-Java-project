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


    // Save a new contact message
    public ContactMessage saveMessage(ContactMessage message) {

        message.setCreatedAt(LocalDateTime.now());

        return contactMessageRepository.save(message);
    }


    // Get all contact messages for admin
    public List<ContactMessage> getAllMessages() {

        return contactMessageRepository.findAll();
    }
}
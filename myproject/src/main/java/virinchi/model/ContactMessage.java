package virinchi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_messages")
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    // Set by backend session
    @Column(nullable = false)
    private String username;


    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;


    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email")
    @Column(nullable = false)
    private String email;


    private String phone;


    @NotBlank(message = "Message is required")
    @Size(
            max = 2000,
            message = "Message cannot be more than 2000 characters"
    )
    @Column(nullable = false, length = 2000)
    private String message;


    @Column(nullable = false)
    private LocalDateTime createdAt;


    public ContactMessage() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
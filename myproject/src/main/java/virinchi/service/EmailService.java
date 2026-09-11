package virinchi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(
            String email,
            String username,
            String verificationCode) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("EliteWear Email Verification");

        message.setText(
                "Hello " + username + ",\n\n" +
                        "Thank you for registering with EliteWear.\n\n" +
                        "Your verification code is:\n\n" +
                        verificationCode + "\n\n" +
                        "Enter this code on the EliteWear verification page " +
                        "to verify your email address.\n\n" +
                        "EliteWear"
        );

        mailSender.send(message);
    }
}
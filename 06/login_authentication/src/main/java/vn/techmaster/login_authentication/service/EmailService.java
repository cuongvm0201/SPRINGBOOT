package vn.techmaster.login_authentication.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender emailSender;

    public void sendEmail(String email, String text){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Your verification code of my application");
        message.setText("http://localhost:8088/validate/"+text);

        // Send Message!
        emailSender.send(message);
    }
}

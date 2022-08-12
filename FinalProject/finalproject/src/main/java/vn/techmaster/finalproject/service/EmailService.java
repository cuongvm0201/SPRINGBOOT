package vn.techmaster.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service

public class EmailService {
    @Autowired private JavaMailSender emailSender;

    public void sendEmail(String email, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Your verification code of my application");
        message.setText("http://46.137.193.202/api/v1/security/validate/" + text);
        // message.setText("http://localhost:8083/api/v1/security/validate/" + text);
        // Send Message!
        emailSender.send(message);
    }

    public void sendMail(String email, String subject, String content) {
        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);

        // Send Message!
        emailSender.send(message);
    }

    public void replyInbox(String email, String subject, String content) {
        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);

        // Send Message!
        emailSender.send(message);
    }

    public void sendnewPass(String email, String pass){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Mật khẩu mới của bạn là: ");
        message.setText(pass);

        emailSender.send(message);


    }

}

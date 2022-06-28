package vn.techmaster.finalproject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import vn.techmaster.finalproject.exception.NotFoundException;
import vn.techmaster.finalproject.model.Bill;
import vn.techmaster.finalproject.repository.BillRepo;

@Service
@AllArgsConstructor
public class SendBillToMailService {
    private JavaMailSender emailSender;

    public void sendMail(String email, String subject, String content){
        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);

        // Send Message!
        emailSender.send(message);
    }
}

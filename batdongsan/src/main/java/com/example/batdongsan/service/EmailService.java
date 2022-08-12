package com.example.batdongsan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your verification code of my application");
        // message.setText("http://localhost:8080/account/quen-mat-khau/detail/" + email);
        message.setText("http://13.213.46.172/account/quen-mat-khau/detail/" + email);
        // Send Message!
        emailSender.send(message);
    }



}

package com.org.userdetails.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	public void sendPasswordResetEmail(String recipientEmail, String resetLink) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipientEmail);
		message.setSubject("Password Reset");
		message.setText("To reset your password, click the link below:\n" + resetLink);
		emailSender.send(message);
	}

}

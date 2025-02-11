package com.App.TradeApp.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationOtpEmail(String email, String otp) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            String subject = "Verify OTP";
            String text = "Your verification OTP is: " + otp;

            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text, true); // true = enable HTML
            mimeMessageHelper.setTo(email);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println("Error while constructing the email: " + e.getMessage());
        } catch (MailException e) {
            System.out.println("Failed to send OTP email: " + e.getMessage());
        }
    }
}

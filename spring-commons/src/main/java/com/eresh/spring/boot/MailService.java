package com.eresh.spring.boot;

import com.eresh.spring.boot.commons.exception.ApplicationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class MailService {

    public void sendEmail() throws ApplicationException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        try {
            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(587);

            mailSender.setUsername("eresh.zealous@gmail.com");
            mailSender.setPassword("Hyderabad1$");
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("eresh.zealous@gmail.com ");
            message.setSubject("Text");
            message.setText("Sample");
            mailSender.send(message);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}

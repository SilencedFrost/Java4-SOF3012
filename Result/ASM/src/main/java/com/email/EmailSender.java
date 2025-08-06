package com.email;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;
import com.config.ConfigLoader;

public class EmailSender {
    private static final Logger logger = Logger.getLogger(EmailSender.class.getName());

    public static void sendEmail(String to, String subject, String body) {
        ConfigLoader.loadEmailConfig();

        String from = ConfigLoader.getEmailUsername();
        String password = ConfigLoader.getEmailPassword();

        Properties props = new Properties();
        props.put("mail.smtp.auth", ConfigLoader.getSmtpAuth());
        props.put("mail.smtp.starttls.enable", ConfigLoader.getSmtpStartTls());
        props.put("mail.smtp.host", ConfigLoader.getSmtpHost());
        props.put("mail.smtp.port", ConfigLoader.getSmtpPort());
        props.put("mail.smtp.ssl.trust", ConfigLoader.getSmtpSslTrust());

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            logger.info("Email sent successfully!");

        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "Failed to send email: " + e.getMessage(), e);
        }
    }
}
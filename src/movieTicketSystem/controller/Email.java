package movieTicketSystem.controller;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Controller class to send emails
 */
public class Email {
    private static Email instanceVar;
    private Properties smtpSettings;
    private Properties loginDetails;

    /**
     * Private constructor enforcing singleton design pattern
     */
    private Email() {
        String smtpSettingsLocation = "config/gmail_smtp.properties";
        String loginDetailsLocation = "config/gmail_login.properties";

        try {
            this.smtpSettings = new Properties();
            this.smtpSettings.load(new FileReader(smtpSettingsLocation));
        }

        catch (IOException e) {
            System.out.printf("[FAIL] SMTP settings file could not be read at \'%s\'", smtpSettingsLocation);
        }

        try {
            this.loginDetails = new Properties();
            this.loginDetails.load(new FileReader(loginDetailsLocation));
        }

        catch (IOException e) {
            System.out.printf("[FAIL] Login details file could not be read at \'%s\'", loginDetailsLocation);
        }
    }

    /**
     * Returns the one and only instance of Email
     *
     * @return instance of Email
     */
    public static Email getInstance() {
        if (instanceVar == null) {
            instanceVar = new Email();
            return instanceVar;
        }

        else {
            return instanceVar;
        }
    }

    /**
     * Sends email
     *
     * @param to      receipt of the email
     * @param subject subject line of the email
     * @param body    body of the email
     */
    public void sendEmail(String to, String subject, String body) {
        Session session = Session.getDefaultInstance(this.smtpSettings,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(loginDetails.getProperty("mail.from"),
                                loginDetails.getProperty("mail.password"));
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
        }

        catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}

package movieTicketSystem.controller;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {
    private static Email instanceVar;
    private Properties smtpSettings;
    private Properties loginDetails;

    private Email() {
        String smtpSettingsLocation = "lib/Gmail_SMTP_Settings.txt";
        String loginDetailsLocation = "lib/Gmail_Login.txt";

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

    public static Email getInstance() {
        if (instanceVar == null) {
            instanceVar = new Email();
            return instanceVar;
        }

        else {
            return instanceVar;
        }
    }

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

    /*public static void main(String[] args) throws Exception {
        Email email = Email.getInstance();
        email.sendEmail("someone@example.com", "Test subject", "Test body");
    }*/
}

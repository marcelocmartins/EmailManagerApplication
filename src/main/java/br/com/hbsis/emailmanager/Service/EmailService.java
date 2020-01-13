package br.com.hbsis.emailmanager.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Service
public class EmailService {


    private final JavaMailSender javaMailSender;
    private final Store store;
    private final MailProperties mailProperties;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, Store store, MailProperties mailProperties) {
        this.javaMailSender = javaMailSender;
        this.store = store;
        this.mailProperties = mailProperties;
    }


    public void sendMail(String toEmail, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailMessage.setFrom("projetoemailmarcelo@gmail.com");

        javaMailSender.send(mailMessage);
    }

    public void sendMessageWithAttachment (String toEmail, String subject, String message, String pathToAttachment) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(message);

        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("Anexo", file);

        javaMailSender.send(mimeMessage);
    }

    public Message[] getMessages() throws MessagingException, IOException {
        Properties properties = new Properties();

//        properties.put(mailProperties.getHost());

        // create folder object
        Folder emailFolder = store.getFolder("INBOX");
        emailFolder.open(Folder.READ_ONLY);

        // retrieve the messages from the folder in an array and print it
        Message[] messages = emailFolder.getMessages();

        for(int i = 0, n = messages.length; i < n; i++) {
            Message message = messages[i];
            System.out.printf("Email Number: " + (i + 1));
            System.out.printf("Subject: " + message.getSubject());
            System.out.printf("From: " + message.getFrom());
            System.out.printf("Text: " + message.getContent().toString());
        }

        return messages;
    }
}

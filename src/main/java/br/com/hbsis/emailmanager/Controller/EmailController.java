package br.com.hbsis.emailmanager.Controller;

import br.com.hbsis.emailmanager.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class EmailController {

    private EmailService emailService;
//    private String destinatario;
//    private String assunto;
//    private String mensagem;



    @Autowired
    public EmailController (EmailService theEmailService) {
        this.emailService = theEmailService;
    }

    @GetMapping("/sendmail")
    public void sendMail(HttpServletResponse response, String destinatario, String assunto, String mensagem) throws IOException {

        emailService.sendMail(destinatario, assunto, mensagem);
        response.sendRedirect("emailsent.html");
    }

    @GetMapping("/sendMessageWithAttachment")
    public void sendMessageWithAttachment(HttpServletResponse response, String destinatario, String assunto, String mensagem, String pathToAttachment) throws IOException {

        emailService.sendMail(destinatario, assunto, mensagem);
        response.sendRedirect("emailsent.html");
    }

    @GetMapping("/")
    public Message[] getInboxMessages() throws IOException, MessagingException {
        return emailService.getMessages();
    }
}

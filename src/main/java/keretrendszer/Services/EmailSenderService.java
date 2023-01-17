package keretrendszer.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender sender;

    public void sendMail(String toEmail, String subject, String body){
        final SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toEmail);
        msg.setText(body);
        msg.setSubject(subject);
        msg.setSentDate(new Date());

        sender.send(msg);
    }
}

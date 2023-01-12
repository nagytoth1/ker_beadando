package keretrendszer.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailSender {
    @Autowired
    private JavaMailSender sender;

    private final String FROM_ADDRESS = "zajcevwebaruhaz@test.com";
    public void sendMail(String toEmail,
                         String subject,
                         String body){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(FROM_ADDRESS);
        msg.setTo(toEmail);
        msg.setText(body);
        msg.setSubject(subject);
        msg.setSentDate(new Date());

        sender.send(msg);
        System.out.println("Mail has been sent successfully...");
    }
}

package rapbattles.rap_battles.Util;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailSender implements Runnable{

    static Logger log = Logger.getLogger(EmailSender.class.getName());

    private String email;
    private String name;
    private String activation_code;

    public EmailSender(String email, String name, String activation_code) {
        this.email = email;
        this.name = name;
        this.activation_code = activation_code;
    }

    public void sendEmail(String email, String name, String activation_code) throws MessagingException {

        final String username = "FinalProjectITTnoReply@gmail.com";
        final String password = "A12345678@a";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("RapBattlesNoReply@gmail.com", true));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        msg.setSubject("Registration complete");
        msg.setContent("Congratulations "+name+"! You have successfully completed your registration for RapBattles." +
                " Please click on the link to activate your account: <a href=\"http://localhost:8080/user//activate/"+activation_code+"\">Activation link</a>", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("", "text/html");
        Transport.send(msg);
    }

    @Override
    public void run(){
        try {
            sendEmail(email,name,activation_code);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            e.getMessage();
        }
    }
}

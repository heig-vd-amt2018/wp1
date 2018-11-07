package ch.heigvd.amt.wp1.services.business.mail;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Stateless
public class EmailSender {
    @Resource(name = "java/mail/swhp")
    Session mailSession;

    public void sendEmail(String mail, String firstname, String lastname, String password) throws MessagingException, UnsupportedEncodingException
    {
        Message message = new MimeMessage(mailSession);

        message.setSubject("Hi, " + firstname + " " + lastname);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));
        message.setText("You need to reset your password. Your temporary password is : " + password );
        Transport.send(message);
    }
}
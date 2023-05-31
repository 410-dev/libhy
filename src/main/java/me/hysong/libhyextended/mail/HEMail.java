package me.hysong.libhyextended.mail;

import me.hysong.libhyextended.mail.servers.HEMailServer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

public class HEMail {
    public static int send(HEMailServer server, HEMailObject mailObject) {
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", server.getHost());
            properties.put("mail.smtp.port", server.getPort());
            properties.put("mail.smtp.auth", server.isUseSMTPAuth());
            properties.put("mail.smtp.starttls.enable", server.isUseTLS());
            properties.put("mail.user", server.getSenderMailAddress());
            properties.put("mail.password", server.getPassword());
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(server.getSenderMailAddress(), server.getPassword());
                }
            };
            Session session = Session.getInstance(properties, auth);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(server.getSenderName()));
            InternetAddress[] toAddresses = {new InternetAddress(mailObject.getRecipientAddress())};
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(mailObject.getSubject());
            msg.setSentDate(new Date());
            msg.setFrom(new InternetAddress(server.getSenderMailAddress(), server.getSenderName()));
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setHeader("Content-Transfer-Encoding", "quoted-printable");
//            TODO add support for HTML
            messageBodyPart.setHeader("Content-Type", "text/plain; charset=\"utf-8\"");
            messageBodyPart.setContent(mailObject.getBody(), "text/plain; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);
            Transport.send(msg);
            return 0;
        }catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
}
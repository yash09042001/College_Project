package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSend
{

    public static void sendMail(String from, String to, String subject,String message )
    {
        try {

          

            InternetAddress[] recipientAddress = InternetAddress.parse(to);
            Session m_Session;
            Message m_simpleMessage;
            InternetAddress m_fromAddress;
            Properties m_properties;

            m_properties = new Properties();
            m_properties.put("mail.smtp.host", "smtp.gmail.com");
            m_properties.put("mail.smtp.socketFactory.port", "465");
            m_properties.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            m_properties.put("mail.smtp.auth", "true");
            m_properties.put("mail.smtp.port", "465");

            m_Session = Session.getDefaultInstance(m_properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("testproject201@gmail.com", "solutions123"); // username and the password
                }
            });

            m_simpleMessage = new MimeMessage(m_Session);
            m_fromAddress = new InternetAddress(from);
//            m_toAddress = new InternetAddress(m_to);
            m_simpleMessage.setFrom(m_fromAddress);
//            m_simpleMessage.setRecipient(RecipientType.TO, m_toAddress);
            m_simpleMessage.setRecipients(RecipientType.TO, recipientAddress);
            m_simpleMessage.setSubject(subject);
            m_simpleMessage.setContent(message, "text/html");
            //m_simpleMessage.setContent(m_body,"text/plain");

            Transport.send(m_simpleMessage);
            System.out.println("mail has been send ...");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public static void sendAlertMail(String from, String to, String subject,String message) {
        try {

            from = "account@gmail.com";
            to = "";
           
              subject = "Payement received confirmation";
            message = "Dear,<br>Your form for Revaluation process is accepted.";

            InternetAddress[] recipientAddress = InternetAddress.parse(to);
            Session m_Session;
            Message m_simpleMessage;
            InternetAddress m_fromAddress;
            Properties m_properties;

            m_properties = new Properties();
            m_properties.put("mail.smtp.host", "smtp.gmail.com");
            m_properties.put("mail.smtp.socketFactory.port", "465");
            m_properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            m_properties.put("mail.smtp.auth", "true");
            m_properties.put("mail.smtp.port", "465");

            m_Session = Session.getDefaultInstance(m_properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("testproject201@gmail.com", "solutions123"); // username and the password
                }
            });

            m_simpleMessage = new MimeMessage(m_Session);
            m_fromAddress = new InternetAddress(from);
//            m_toAddress = new InternetAddress(m_to);
            m_simpleMessage.setFrom(m_fromAddress);
//            m_simpleMessage.setRecipient(RecipientType.TO, m_toAddress);
            m_simpleMessage.setRecipients(RecipientType.TO, recipientAddress);
            m_simpleMessage.setSubject(subject);
            m_simpleMessage.setContent(message, "text/html");
            //m_simpleMessage.setContent(m_body,"text/plain");

            Transport.send(m_simpleMessage);
            System.out.println("mail has been send ...");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, Exception {

        MailSend send_mail = new MailSend();
        String empName = "Antony Raj S";
        String title = "<b>Hi !" + empName + " Welcome to DeDuplication</b>";
//      send_mail.sendMail("testproject201@gmail.com", "pranaya.jadhav@eiosys.com", "Please apply for leave for the following dates", title+"<br>by<br><b>HR<b>");
        send_mail.sendMail("testproject201@gmail.com", "ankitap.eiosys@gmail.com", "Payement received confirmation","Dear,<br>Your form for Revaluation process is accepted.");
        System.out.println("mail has been send ...");
    }

//    static void sendMail("", ArrayList<String> to, String secret_Keys, String key1) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

   
}

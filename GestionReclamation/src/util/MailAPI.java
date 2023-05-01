/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.mail.PasswordAuthentication;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Yasmine
 */
public class MailAPI {
    public static void sendEmail(String to,String sub,String msg){
        try {
            Properties prop=new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            Session session=Session.getDefaultInstance(prop,new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication("aljaneeya@gmail.com", "khvcatfzlwkofzac");
                }
            });
            MimeMessage message=new MimeMessage(session);
            message.setSubject(sub);
            message.setText(msg);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            Transport.send(message);
            System.out.println("message sent");
        } catch (MessagingException ex) {
            Logger.getLogger(MailAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

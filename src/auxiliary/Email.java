/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliary;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Admin
 */
public class Email {
    public boolean sendMail(String recepient, String ms, String subject){
        System.out.println("Preparing to send email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.port", "587");
        String myAccountEmail = "yurayatskiv07@gmail.com";
        String password = "rdtesxozynjoylnz";
        Session session = Session.getInstance(properties, new Authenticator () {
            @Override
            protected PasswordAuthentication getPasswordAuthentication () {
                return new PasswordAuthentication (myAccountEmail, password);
            }
        });    
        Message message = prepareMessage(session, myAccountEmail, recepient, ms,subject);
        try {
            Transport.send (message);
        } catch (MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        System.out.println("Message sent successfully");
        return true;
    }    
    private static Message prepareMessage (Session session, String myAccountEmail, String recepient, String ms, String subject){
        try {
           Message message = new MimeMessage (session);
           message.setFrom(new InternetAddress (myAccountEmail));
           message.setRecipient (Message.RecipientType. TO, new InternetAddress(recepient));
           message.setSubject(subject);
           message.setContent(ms, "text/html; charset=utf-8");
            return message;
         } catch (Exception ex) {
             Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
         }
        return null;
   }
    
    public String resetPass(String number){
        String newpass="<html>\n" +
                    "    <body>\n" +
                    "        <h1 style='text-align:center; color:red;'>Змінити пароль</h1>\n" +
                    "        <p style='margin: 0 auto; text-align:center; font-size:16px;'>Для зміни паролю виберіть в додатку кнопку з цифрою:</p>\n" +
                    "        <h2 style='text-align:center; color:red;'>"+number+"</h3>\n" +
                    "    </body>\n" +
                    "</html>";
        return newpass;
    }
    
    public String registrationUser(String login, String pass){
        String message="<html><body> <div style='text-align: center;'>\n" +
                        "            <h1 style='text-align: center; color: red;'>Реєстрація</h1>\n" +
                        "            <p style='font-size: 16px;'>Вітаємо ви були зареєстровані в програмі VSTUP.</p>\n" +
                        "            <p style='font-size: 16px;'>Ваш логін: <span style='font-size: 16px; color: red;'>"+login+"</span></p>\n" +
                        "            <p style='font-size: 16px;'>Ваш пароль:<span style='font-size: 16px; color: red;'>"+pass+"</span> </p>\n" +
                        "    </div> </body>\n" +
                        "</html>";
        return message;
    }
    
    public String confirmRequest(){
        String message="<html>\n" +
                        "    <body>\n" +
                        "        <div style=\"text-align: center;\">\n" +
                        "            <h1 style=\"text-align: center; color: red;\">Підтверження</h1>\n" +
                        "            <p style=\"font-size: 20px;\">Вітаємо ваша заявка на вступ була опрацьована і прийнята.</p>\n" +
                        "        </div>\n" +
                        "    </body>\n" +
                        "</html>";
        return message;
    }
    
    public String rejectRequest(String reason){
        String message="<html>\n" +
                        "    <body>\n" +
                        "        <div style=\"text-align: center;\">\n" +
                        "            <h1 style=\"text-align: center; color: red;\">Відхилено</h1>\n" +
                        "            <p style=\"font-size: 20px;\">Ваша заявка на вступ була відхилена</p>\n" +
                        "            <p style=\"font-size: 20px;\">Причина: </p>\n" +
                        "            <p style=\"font-size: 20px; color: red;\">"+reason+"</p>\n" +
                        "        </div>\n" +
                        "    </body>\n" +
                        "</html>";
        return message;
    }
    
    
}

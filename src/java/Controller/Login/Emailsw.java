/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Login;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author Dell
 */
public class Emailsw {
    final String from = "hieppdhe171309@fpt.edu.vn";
    final String passwordw = "fzemcszwnyicwxad";
    final String to = "trungthanh26148@gmail.com";
    
    public void sendMail(String fromMail, String password, String code, String toMail){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "*");
        
        // create authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromMail, password);
            }
        };
         // create session
        Session session = Session.getInstance(props, auth);
        try {
            // create mime message
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML;charset=UTF-8");
            msg.setFrom(new InternetAddress("hieppdhe171309@fpt.edu.vn"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail, false));
            msg.setSubject( "" + code + " is your APOTHEKE Verification Code");
            msg.setSentDate(new Date());
           
             String emailBody = "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset='UTF-8'>"
                    + "<title>Verification Code</title>"
                    + "</head>"
                    + "<body>"
                    + "<p>Hi!</p>"
                    + "<p>You are completing new account verification. Your verification code is: <strong>" + code + "</strong>.</p>"
                    + "<p>Please complete the account verification process in 2 seconds.</p>"
                    + "<p>HoYoverse</p>"
                    + "<p>This is an automated email. Please do not reply to this email.</p>"
                    + "</body>"
                    + "</html>";
            msg.setContent(emailBody, "text/html; charset=UTF-8");
            // send email
            Transport.send(msg);
            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
  
}

package Utilities;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * SendEmail.java
 * Purpose:  Send an email
 *
 * @author Garisian Kana
 * @version 1.0
 *
 * Created on 2017-10-17
 */
public class SendEmail
{
    public static String makeMail(String recepient, ArrayList<HashMap<String, String>> emailableData)
    {
        // Sender's email ID needs to be mentioned
        String from = "garisian22@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(PrivateInformation.privateEmail, PrivateInformation.privatePassword);
                    }
                });

        String messageBody = createBody(emailableData);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recepient));

            // Set Subject: header field
            message.setSubject("Bookedmarked Data From BookMeNow");

            // Now set the actual message
            message.setText(messageBody);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
            return "failure";
        }
        return "success";
    }
    private static String createBody(ArrayList<HashMap<String, String>> emailableData)
    {
        String fulLString = "";
        for (HashMap<String, String> singleItem: emailableData)
        {
            fulLString += "===================================="+"\n";
            fulLString += singleItem.get("title")+"\n";
            fulLString += singleItem.get("summary")+"\n";
            fulLString += singleItem.get("url")+"\n";
            fulLString += singleItem.get("tags")+"\n";
        }
        return fulLString;
    }
}

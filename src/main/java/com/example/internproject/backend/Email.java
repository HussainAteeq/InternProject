package com.example.internproject.backend;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {
    public static void sendEmail(String recipient) throws Exception {
        System.out.println("Preparing to send email...");
        Properties properties=new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myAccount="hussainattique221@gmail.com";
        String password="onesliilslcxiomu";


        Session session= Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount,password);
            }
        });


        Message message=prepareMessage(session,myAccount,recipient);

        Transport.send(message);

    }

    private static Message prepareMessage(Session session,String myAccountEmail, String recipient) throws Exception {


        Message message=new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
        message.setSubject("Order Ready");
        message.setText("Kindly pick your order");
        return message;



    }
}
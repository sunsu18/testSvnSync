package com.sfr.util;


import java.io.File;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;

import javax.activation.FileDataSource;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import oracle.adf.share.logging.ADFLogger;


public class EmailUtility {
    public static final ADFLogger log = AccessDataControl.getSFRLogger();

    public EmailUtility() {
        super();
    }

    public static String sendEmail(String from, String to, String subject,
                                   String body, String protocol,
                                   String smtphost,
                                   String envProperty) throws MessagingException {
        // Add event code here...
        AccessDataControl accessDC = new AccessDataControl();
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", protocol);
        props.setProperty("mail.host", smtphost);
        // props.setProperty("mail.user", "Navin.Kumar@lntinfotech.com");
        //props.setProperty("mail.password", "");

        Session mailSession;
        mailSession = Session.getDefaultInstance(props, null);


        try {
            log.info(accessDC.getDisplayRecord() +
                     "EmailUtility.sendEmail : " + "entered email utility");
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(mailSession);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);


            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setContent(body, "text/html; charset=\"UTF-8\"");
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            File file = new File(envProperty);
            log.info(accessDC.getDisplayRecord() +
                     "EmailUtility.sendEmail : " + "Checking if file at " +
                     envProperty + " does really exists =" + file.exists());
            DataSource datasource = new FileDataSource(envProperty);
            messageBodyPart.setDataHandler(new DataHandler(datasource));
            messageBodyPart.setHeader("Content-ID", "<statoil2>");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            log.info(accessDC.getDisplayRecord() +
                     "EmailUtility.sendEmail : " + "between sending email ");

            // Send message
            Transport.send(message);
            log.info(accessDC.getDisplayRecord() +
                     "EmailUtility.sendEmail : " +
                     "Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }


        return null;
    }

}

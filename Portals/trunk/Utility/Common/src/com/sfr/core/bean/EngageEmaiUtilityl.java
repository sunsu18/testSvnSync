package com.sfr.core.bean;

import com.sfr.util.AccessDataControl;

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
import javax.mail.util.ByteArrayDataSource;

import oracle.adf.share.logging.ADFLogger;


public class EngageEmaiUtilityl {
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();

    public EngageEmaiUtilityl() {
        super();

    }

    public void sendEmail(String from, String to, String subject, String body,
                          String protocol, String smtphost, String cc,
                          byte[] responseByteArr, String envprop,
                          String filename) throws MessagingException {
        // Add event code here...
        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 " sending email to " + to + "for invoice " + filename +
                 "having byte array size as" + responseByteArr.length);
        //log.info(accessDC.getDisplayRecord() + this.getClass() + " "+from + " "+ subject + " " + protocol + " " + smtphost + " " + cc + " "+envprop);
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", protocol);
        props.setProperty("mail.host", smtphost);

        Session mailSession = Session.getDefaultInstance(props, null);
        try {

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(mailSession);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            //CC
            //message.setRecipients(Message.RecipientType.CC, cc);

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setContent(body, "text/html; charset=\"UTF-8\"");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            //String filename = "C:\\JDeveloper\\mywork\\SendEmail\\Project1\\public_html\\image\\addc.PNG";
            //String filename2="C:\\Users\\10604350\\Desktop\\Test_Case_WS.docx";
            //DataSource source = new FileDataSource(filename2);
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " Length 2" + responseByteArr.length);
            DataSource source =
                new ByteArrayDataSource(responseByteArr, "application/pdf");
            messageBodyPart.setDataHandler(new DataHandler(source));
            //String filename = filename.concat(".pdf");
            messageBodyPart.setFileName(filename.concat(".pdf"));

            multipart.addBodyPart(messageBodyPart);


            // Send the complete message parts
            message.setContent(multipart, "text/html; charset=\"UTF-8\"");


            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(envprop);

            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "image");

            multipart.addBodyPart(messageBodyPart);


            // Send the complete message parts
            message.setContent(multipart, "text/html; charset=\"UTF-8\"");


            //message.setContent(body,"text/html");


            // Send message
            Transport.send(message);
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
}

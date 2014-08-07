package com.sfr.engage.invoiceoverviewtaskflow;

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


public class emailbean {
    public emailbean() {
        super();
    }

    public void sendEmail(String from, String to, String subject, String body,
                          String protocol, String smtphost, String cc,
                          byte[] responseByteArr, String envprop,
                          String filename) throws MessagingException {

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", protocol);
        props.setProperty("mail.host", smtphost);

        Session mailSession = Session.getDefaultInstance(props, null);
        try {

            MimeMessage message = new MimeMessage(mailSession);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress(to));

            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setContent(body, "text/html; charset=\"UTF-8\"");

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();

            System.out.println("Length 2" + responseByteArr.length);
            DataSource source =
                new ByteArrayDataSource(responseByteArr, "application/pdf");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename.concat(".pdf"));
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart, "text/html; charset=\"UTF-8\"");


            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(envprop);

            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "image");

            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart, "text/html; charset=\"UTF-8\"");

            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
}

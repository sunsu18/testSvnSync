package com.sfr.engage.invoiceoverviewtaskflow;


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


public class emailbean {
    public static final ADFLogger LOGGER = AccessDataControl.getSFRLogger();
    private AccessDataControl accessDC = new AccessDataControl();

    public emailbean() {
        super();
    }

    public void sendEmail(String from, String to, String subject, String body, String protocol, String smtphost, String cc, byte[] responseByteArr,
                          String envprop, String filename) throws MessagingException {

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", protocol);
        props.setProperty("mail.host", smtphost);

        Session mailSession = Session.getDefaultInstance(props, null);
        try {

            MimeMessage message = new MimeMessage(mailSession);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setContent(body, "text/html; charset=\"UTF-8\"");

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Length 2" + responseByteArr.length);
            DataSource source = new ByteArrayDataSource(responseByteArr, "application/pdf");
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
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Sent message successfully....");
        } catch (MessagingException e) {
            LOGGER.severe(e);
        }

    }

    public void setAccessDC(AccessDataControl accessDC) {
        this.accessDC = accessDC;
    }

    public AccessDataControl getAccessDC() {
        return accessDC;
    }
}

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

    public void sendEmail(String from,String to,String subject,String body,String protocol,String smtphost,String cc,byte[] responseByteArr,String envprop,String filename) throws MessagingException
    {
    // Add event code here...
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
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                // Set Subject: header field
                message.setSubject(subject);

                // Create the message part
                BodyPart messageBodyPart = new MimeBodyPart();

                // Fill the message
    //                File file = new File(envProperty);
    //            System.out.println("File path : " + envProperty);
    //                System.out.println("EmailUtility.sendEmail : "+"Checking if file at " + envProperty + " does really exists =" + file.exists());
    //                DataSource datasource = new FileDataSource(envProperty);
    //                messageBodyPart.setDataHandler(new DataHandler(datasource));
    //                messageBodyPart.setHeader("Content-ID", "statoil");
                messageBodyPart.setContent(body, "text/html");

                // Create a multipar message
                Multipart multipart = new MimeMultipart();

                // Set text message part
                multipart.addBodyPart(messageBodyPart);

                // Part two is attachment
                messageBodyPart = new MimeBodyPart();
                //String filename = "C:\\JDeveloper\\mywork\\SendEmail\\Project1\\public_html\\image\\addc.PNG";
                //String filename2="C:\\Users\\10604350\\Desktop\\Test_Case_WS.docx";
                //DataSource source = new FileDataSource(filename2);
                System.out.println("Length 2"+ responseByteArr.length);
                DataSource source = new ByteArrayDataSource(responseByteArr,"application/pdf");
                messageBodyPart.setDataHandler(new DataHandler(source));
                //String filename = filename.concat(".pdf");
                messageBodyPart.setFileName(filename.concat(".pdf"));

                multipart.addBodyPart(messageBodyPart);


                // Send the complete message parts
                message.setContent(multipart,"text/html");


                messageBodyPart = new MimeBodyPart();
                DataSource fds = new FileDataSource(envprop);

                messageBodyPart.setDataHandler(new DataHandler(fds));
                messageBodyPart.setHeader("Content-ID", "image");

                    multipart.addBodyPart(messageBodyPart);


                    // Send the complete message parts
                    message.setContent(multipart,"text/html");


    //message.setContent(body,"text/html");


                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");
            }
        catch(MessagingException mex){
            mex.printStackTrace();
        }

    }
}

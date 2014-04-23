package com.SOAScheduler;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.URL;

import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class EndpointInvoke {
    static Logger logger = Logger.getLogger(EndpointInvoke.class);
    Calendar cal;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss a");
    public boolean InvokeEndpointURI(String msg, String endpt, String operation, String tablename,
                                         String triggerid, String groupname,
                                         Statement updateStatus, String level){
        
      
        
         if (level.equalsIgnoreCase("debug"))
            logger.setLevel(Level.DEBUG);
        else
            logger.setLevel(Level.INFO);
        
        SOAPConnection connection = null;

        try {
            cal = Calendar.getInstance();    
            logger.info("time= ");
            logger.info("time= "  + cal.getTime());
            
            logger.info("time= "  + df.format(cal.getTime()));
            SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
            logger.debug("-----Opening a SOAP Connection.-----");
            connection = factory.createConnection();
            java.net.URL endpoint = new URL(endpt);
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage message = messageFactory.createMessage();
            MimeHeaders headers = message.getMimeHeaders();
          
            if (operation == null){
                logger.debug("Default operation not specified. taking the operation as PROCESS");
                operation="process";
            }
            
            logger.debug("-----Adding operation-----");    
            headers.addHeader("SOAPAction",operation);
            //Populate the Message
            logger.debug("-----Populating messages-----");
            StringBuffer StringBuffer1 = new StringBuffer(msg);
            ByteArrayInputStream bis = new ByteArrayInputStream(StringBuffer1.toString().getBytes("UTF-8"));
            StreamSource payload = new StreamSource(bis);
            SOAPPart soapPart = message.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            soapPart.setContent(payload);
            message.saveChanges();
            
            logger.info("Invoking the endpoint : " + endpt);
            logger.debug("Invoking the endpoint : " + endpt);
            logger.info("time= "  + cal.getTime());
            SOAPMessage response = connection.call(message, endpt);
                        
//            SOAPBody soapBody = null;
            
            if (response != null) {
//              soapBody = response.getSOAPBody();
//              logger.info(soapBody.toString());
              logger.debug(response.toString());
              
            }
          
            logger.info("Endpoint invocation successful");
            logger.debug("Endpoint invocation successful");
            updateStatus.executeUpdate("UPDATE "+ tablename + " SET STATUS = 'RUNNING', ERROR_DESCRIPTION = NULL, ERROR_TS = NULL WHERE TRIGGERID = '" +
                                      triggerid + "' AND GROUPNAME='" + groupname + "'");
            logger.debug("Query Executed : UPDATE "+ tablename + " SET STATUS = 'RUNNING', ERROR_DESCRIPTION = NULL, ERROR_TS = NULL WHERE TRIGGERID = '" +
                                      triggerid + "' AND GROUPNAME='" + groupname + "'");
            connection.close();
            logger.debug("-----SOAP Connection closed-----");
            return true;

        } 
        catch (SOAPException se) {
            StringWriter w = new StringWriter();
            se.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
            logger.debug(w.toString());
            try{
//              updateStatus.executeUpdate("update "+ tablename + " set status = 'ERROR', error_description = '" + se.toString() +
//                                      "' where triggerid='" + triggerid + "' and groupname='" + groupname + "'");
// //                logger.debug("Query Executed : update "+ tablename + " set status = 'ERROR', error_description = '" + se.toString() +
//                                      "' where triggerid='" + triggerid + "' and groupname='" + groupname + "'");
              
                updateStatus.executeUpdate("UPDATE "+ tablename + " SET STATUS = 'ERROR', ERROR_DESCRIPTION = '" + se.toString() + 
                                           "' , ERROR_TS = TO_DATE('" + df.format(cal.getTime()) +"','YYYY-MON-DD HH:MI:SSAM') WHERE ERROR_TS IS NULL AND TRIGGERID='" 
                                           + triggerid + "' AND GROUPNAME='" + groupname + "'");

                logger.debug("Query Executed : UPDATE "+ tablename + " SET STATUS = 'ERROR', ERROR_DESCRIPTION = '" + se.toString() + 
                                           "' , ERROR_TS = TO_DATE('" + df.format(cal.getTime()) +"','YYYY-MON-DD HH:MI:SSAM') WHERE ERROR_TS IS NULL AND TRIGGERID='" 
                                           + triggerid + "' AND GROUPNAME='" + groupname + "'");
                return false;
            }
            catch (Exception e){
                e.printStackTrace(new PrintWriter(w));
                logger.error(w.toString());
              logger.debug(w.toString());
                return false;
            }
        }
        catch (Exception e){
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
          logger.debug(w.toString());
          try{
//              updateStatus.executeUpdate("update "+ tablename + " set status = 'ERROR', error_description = '" + e.toString() +
//                                    "' where triggerid='" + triggerid + "' and groupname='" + groupname + "'");
//              logger.debug("Query Executed : update "+ tablename + " set status = 'ERROR', error_description = '" + e.toString() +
//                                    "' where triggerid='" + triggerid + "' and groupname='" + groupname + "'");
                updateStatus.executeUpdate("UPDATE "+ tablename + " SET STATUS = 'ERROR', ERROR_DESCRIPTION = '" + e.toString() + 
                                           "' , ERROR_TS = TO_DATE('" + df.format(cal.getTime()) +"','YYYY-MON-DD HH:MI:SSAM') WHERE ERROR_TS IS NULL AND TRIGGERID='" 
                                           + triggerid + "' AND GROUPNAME='" + groupname + "'");
                logger.debug("Query Executed : UPDATE "+ tablename + " SET STATUS = 'ERROR', ERROR_DESCRIPTION = '" + e.toString() + 
                                           "' , ERROR_TS = TO_DATE('" + df.format(cal.getTime()) +"','YYYY-MON-DD HH:MI:SSAM') WHERE ERROR_TS IS NULL AND TRIGGERID='" 
                                           + triggerid + "' AND GROUPNAME='" + groupname + "'");
              return false;
          }
          catch (Exception ue){
              ue.printStackTrace(new PrintWriter(w));
              logger.error(w.toString());
            logger.debug(w.toString());
              return false;
          }
//            return false;
        }
    }
}

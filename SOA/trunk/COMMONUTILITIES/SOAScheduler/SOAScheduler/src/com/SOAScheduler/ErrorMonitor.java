package com.SOAScheduler;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class ErrorMonitor implements Job {
  final static Logger logger = Logger.getLogger(ErrorMonitor.class);
  protected String providerURL;
  protected String username;
  protected String password;
  protected String tableName;

  protected String TriggerID = null;
  protected String name = null;
  protected String GroupName = null;
  protected String CronExpr = null;
  protected String endpointuri = null;
  protected String input = null;
  protected String Operation = null;
  protected String Priority = null;
  protected String errordes = null;
  protected String enabled = null;
  protected String status = null;
  protected String threshold = null;
  protected String error_ts = null;
  protected String sendmail = null;
  protected String EmailBody = "";
  protected String errorts= "";


  public void execute(JobExecutionContext ctx) throws JobExecutionException {
    Statement ts = null;
    int noOfScheduledJobs = 0;
    int noOfUnScheduledJobs = 0;
    int noOfErroredJobs = 0;
    int noOfNeverScheduled = 0;
    int noOfActualEnabled = 0;
    Calendar cal= Calendar.getInstance();
    
    Properties prop = new Properties();
    try {
      logger.debug("Entering Error Monitor");
      URL url =
        getClass().getClassLoader().getResource("authentication.properties");
      InputStream in = url.openStream();
      prop.load(in);
        logger.debug("Properties loaded");
      providerURL = prop.getProperty("CustomDB_URL");
      username = prop.getProperty("CustomDB_USERNAME");
      password = prop.getProperty("CustomDB_PASSWORD");
      tableName = prop.getProperty("TableName");

      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection conn =
        DriverManager.getConnection(providerURL, username, password);
      ts = conn.createStatement();
      ResultSet table_rs = null;
        logger.debug("Connected to DB");

      table_rs =
          ts.executeQuery("SELECT TRIGGERID,NAME,GROUPNAME,ENABLED,STATUS,ERROR_DESCRIPTION,SENDMAIL,THRESHOLD, ERROR_TS FROM " +
                          tableName + " WHERE STATUS IS NOT NULL");
      logger.debug("Query executed : SELECT TRIGGERID,NAME,GROUPNAME,ENABLED,STATUS,ERROR_DESCRIPTION,SENDMAIL,THRESHOLD, ERROR_TS FROM " +
                          tableName + " WHERE STATUS IS NOT NULL");

      while (table_rs.next()) {

        TriggerID = table_rs.getString("triggerid");
        name = table_rs.getString("name");
        GroupName = table_rs.getString("groupname");
        enabled = table_rs.getString("enabled");
        status = table_rs.getString("status");
        errordes = table_rs.getString("error_description");
        sendmail = table_rs.getString("SENDMAIL");
        
        
        if (status.equalsIgnoreCase("RUNNING") ||
            status.equalsIgnoreCase("SCHEDULED"))
          noOfScheduledJobs++;

        if (status.equalsIgnoreCase("UNSCHEDULED"))
          noOfUnScheduledJobs++;

        if (status.equalsIgnoreCase("ERROR")) {      
        Timestamp tst = table_rs.getTimestamp("ERROR_TS");
        int threshold = table_rs.getInt("THRESHOLD");
        Calendar c = Calendar.getInstance();
        c.setTime(tst);
        logger.debug(c.getTime());
        c.add(Calendar.MINUTE, threshold);
        logger.debug(c.getTime());
        logger.debug(cal.getTime()); 
        
        if (c.getTime().compareTo(cal.getTime()) < 0 && sendmail.equalsIgnoreCase("Y")){
          noOfErroredJobs++;
          EmailBody =
              EmailBody + "<tr><td>" + noOfErroredJobs + "</td><td>" + TriggerID + "</td><td>" + name + "</td><td>" +
              GroupName + "</td><td>" + errordes +"</td></tr>";
        }
        }
      }

      table_rs =
          ts.executeQuery("SELECT COUNT(*) NULLCOUNT FROM " + tableName +
                          " WHERE STATUS IS NULL");
      logger.debug("Query executed : SELECT COUNT(*) NULLCOUNT FROM " + tableName +
                          " WHERE STATUS IS NULL");
      while (table_rs.next()) {
        logger.debug("Null Count" + table_rs.getInt("NullCount"));
        noOfNeverScheduled = table_rs.getInt("NullCount");
      }

      table_rs =
          ts.executeQuery("SELECT COUNT(*) ACTUALCOUNT FROM " + tableName +
                          " WHERE ENABLED='Y'");
      logger.debug("Query executed : SELECT COUNT(*) ACTUALCOUNT FROM " + tableName +
                          " WHERE ENABLED='Y'");
      while (table_rs.next()) {
        logger.debug("Actual Count " + table_rs.getInt("ActualCount"));
        noOfActualEnabled = table_rs.getInt("ActualCount");
      }

      ts.executeUpdate("UPDATE " + tableName +
                       " SET STATUS = 'RUNNING', ERROR_DESCRIPTION = NULL WHERE TRIGGERID='EM' AND GROUPNAME='EMF'");
      logger.debug("Query Executed : UPDATE " + tableName +
                       " SET STATUS = 'RUNNING', ERROR_DESCRIPTION = NULL WHERE TRIGGERID='EM' AND GROUPNAME='EMF'");



      if (noOfErroredJobs > 0) {

        EmailBody =
            "<html><font face='Trebuchet MS' size='2'><p>Dear Admin</p>" +
            "<p> There is was an error while running the following jobs.</p><p>" +
            "<table border='1'><b><tr><td>S.No</td><td>TriggerID</td><td>Name</td><td>Group</td><td>Error Description</td></tr></b>" +
            EmailBody +
            "</table>Please fix the error and schedule these jobs.</p>"+
            "<p>The current status of the scheduler is :</p><p>" +
            "<table border='1'><b><tr>Running/Scheduled<td></b></td><td>" + noOfScheduledJobs + "</td></tr>"+
            "<b><tr>Actual Enabled<td></b></td><td>" + noOfActualEnabled + "</td></tr>"+
            "<b><tr>Unscheduled<td></b></td><td>" + noOfUnScheduledJobs + "</td></tr>"+
            "<b><tr>Errored<td></b></td><td>" + noOfErroredJobs + "</td></tr>"+
            "<b><tr>Never Scheduled<td></b></td><td>" + noOfNeverScheduled + "</td></tr></table></p>"+
            "<p>Thank you<br/>Scheduler Error Monitor</p>" +
            "<hr/><p>*** This is an automatically generated email please do not reply *** </p></font></html>";
        // Common variables

        try {
          logger.debug("Entering mail send");
          String host = prop.getProperty("SMTPServer");
          String port = prop.getProperty("SMTPPort");
          String from = prop.getProperty("DefaultSenderAddress");
//          String to = "sharat.puranikmath@lntdebugtech.com;shreya.tawde@lntdebugtech.com";

          // Set properties
          Properties props = new Properties();
          props.put("mail.smtp.host", host);
          props.setProperty("mail.smtp.port", port);
          props.put("mail.debug", "true");

          // Get session
          Session session = Session.getInstance(props);
          // Instantiate a message
          Message msg = new MimeMessage(session);

          // Set the FROM message
          msg.setFrom(new InternetAddress(from));
          table_rs = ts.executeQuery("SELECT INPUT FROM " +
                          tableName + " WHERE TRIGGERID='EM' AND GROUPNAME='EMF'");
          while (table_rs.next()) {
            input = table_rs.getString("input");
          }
         
          // The recipients can be more than one so we use an array but you can
          // use 'new InternetAddress(to)' for only one address.
//          InternetAddress[] address = { new InternetAddress(to) };
          String[] toArray = input.split(";");
          InternetAddress[] address = new InternetAddress[toArray.length];
          for (int i = 0; i < toArray.length; i++)
          {
              address[i] = new InternetAddress(toArray[i]);
          }
          msg.setRecipients(Message.RecipientType.TO, address);

          // Set the message subject and date we sent it.
          msg.setSubject("Scheduler Error Monitor: " + noOfErroredJobs +  " job/s have errored out");
          msg.setSentDate(new Date());
          // Set message content
          msg.setContent(EmailBody, "text/html");
          //msg.setText(EmailBody);

          // Send the message
          Transport.send(msg);
          logger.debug("Mail send complete");
        } catch (MessagingException mex) {
          ts.executeUpdate("UPDATE " + tableName + " SET STATUS = 'ERROR', ERROR_DESCRIPTION = '" + 
                           mex.toString() + "' WHERE TRIGGERID='EM' AND GROUPNAME='EMF'");
          logger.debug("Query Executed : UPDATE " + tableName +
                           " SET STATUS = 'ERROR', ERROR_DESCRIPTION = '" + mex.toString() +
                           "' WHERE TRIGGERID='EM' AND GROUPNAME='EMF'");
          StringWriter w = new StringWriter();
          mex.printStackTrace(new PrintWriter(w));
          logger.error(w.toString());
          mex.printStackTrace();

        }
        table_rs.close();
        ts.close();
        logger.debug("Error Monitor complete");
      }
    } catch (Exception e) {
      try {
        ts.executeUpdate("UPDATE " + tableName + " SET STATUS = 'ERROR', ERROR_DESCRIPTION = '" +
                         e.toString() + "' WHERE TRIGGERID='EM' AND GROUPNAME='EMF'");
        logger.debug("Query Executed : UPDATE " + tableName + " SET STATUS = 'ERROR', ERROR_DESCRIPTION = '" +
                         e.toString() + "' WHERE TRIGGERID='EM' AND GROUPNAME='EMF'");

      } catch (Exception ue) {
        StringWriter w = new StringWriter();
        ue.printStackTrace(new PrintWriter(w));
        logger.error(w.toString());
      }
      StringWriter w = new StringWriter();
      e.printStackTrace(new PrintWriter(w));
      logger.error(w.toString());
      e.printStackTrace();
    }
  }
}


package com.SOAScheduler;


import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;


public class StatelessJob implements Job {
  static Logger logger = Logger.getLogger(StatefulJob.class);
  Statement updateStatus = null;
  String tableName = null;
  String triggerid = null;
  String groupname = null;
  Calendar cal;
  SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss a");

  public void execute(JobExecutionContext ctx) throws JobExecutionException {

    try {
      cal = Calendar.getInstance(); 
      JobKey key = ctx.getJobDetail().getKey();
      TriggerKey triggerKey = ctx.getTrigger().getKey();
      JobDataMap jobData2 = ctx.getMergedJobDataMap();
      String endpoint = jobData2.get("endpoint").toString();
      String input = jobData2.get("input").toString();
      String operation = jobData2.get("operation").toString();
      triggerid = jobData2.get("triggerid").toString();
      String groupname = jobData2.get("groupname").toString();
      String level = jobData2.get("level").toString();
      String errorUnscheduleFlag = jobData2.get("errorunscheduleflag").toString();

      if (level.equalsIgnoreCase("debug"))
        logger.setLevel(Level.DEBUG);
      else
        logger.setLevel(Level.INFO);
      logger.debug("Entering Stateless job class");
      SchedulerFactory schedFact =
        new StdSchedulerFactory("quartzClient.properties");
      Scheduler sch = schedFact.getScheduler();
      logger.debug(sch.getMetaData().toString());

      Properties prop = new Properties();
      URL url =
        getClass().getClassLoader().getResource("authentication.properties");
      InputStream in = url.openStream();
      prop.load(in);

      String providerURL = prop.getProperty("CustomDB_URL");
      String username = prop.getProperty("CustomDB_USERNAME");
      String password = prop.getProperty("CustomDB_PASSWORD");
      tableName = prop.getProperty("TableName");

      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection conn =
        DriverManager.getConnection(providerURL, username, password);
      updateStatus = conn.createStatement();

      EndpointInvoke epi = new EndpointInvoke();
      boolean result =
        epi.InvokeEndpointURI(input, endpoint, operation, tableName, triggerid, groupname, updateStatus, level);

      if (result == false ) {
        logger.info("Error in end point invocation -  " + endpoint);
        logger.debug("Error in end point invocation -  " + endpoint);
        if(errorUnscheduleFlag.equalsIgnoreCase("Y")){
          sch.unscheduleJob(triggerKey);
          logger.info("Due to error in end point invocation Job " + key + " unscheduled.");
          logger.debug("Due to error in end point invocation Job " + key + " unscheduled.");
        }

      }

    } catch (Exception e) {
      StringWriter w = new StringWriter();
      e.printStackTrace(new PrintWriter(w));
      logger.error(w.toString());
      logger.debug(w.toString());
      e.printStackTrace();
      try{
//          updateStatus.executeUpdate("update "+ tableName + " set status = 'ERROR', error_description = '" + e.toString() +
//                                "' where triggerid='" + triggerid + "' and groupname='" + groupname + "'");
//          logger.debug("Query Executed : update "+ tableName + " set status = 'ERROR', error_description = '" + e.toString() +
//                                "' where triggerid='" + triggerid + "' and groupname='" + groupname + "'");
          updateStatus.executeUpdate("UPDATE "+ tableName + " SET STATUS = 'ERROR', ERROR_DESCRIPTION = '" + e.toString() +
                                "', ERROR_TS = TO_DATE('" + df.format(cal.getTime()) +"','YYYY-MON-DD HH:MI:SSAM') WHERE ERROR_TS IS NULL AND TRIGGERID='" + triggerid + "' AND GROUPNAME='" + groupname + "'");
          logger.debug("Query Executed : UPDATE "+ tableName + " SET STATUS = 'ERROR', ERROR_DESCRIPTION = '" + e.toString() +
                                "', ERROR_TS = TO_DATE('" + df.format(cal.getTime()) +"','YYYY-MON-DD HH:MI:SSAM') WHERE ERROR_TS IS NULL AND TRIGGERID='" + triggerid + "' AND GROUPNAME='" + groupname + "'");
      }
      catch (Exception ue){
          ue.printStackTrace(new PrintWriter(w));
          logger.error(w.toString());
        logger.debug(w.toString());
      }
    }

  }

}

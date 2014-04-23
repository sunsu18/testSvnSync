package com.SOAScheduler;


import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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


//@DisallowConcurrentExecution
public class StatefulJob implements Job {
    final static Logger logger = Logger.getLogger(StatefulJob.class);
    
    public void execute(JobExecutionContext ctx) throws JobExecutionException{

        try {
            JobKey key = ctx.getJobDetail().getKey();
            TriggerKey  triggerKey = ctx.getTrigger().getKey();
            JobDataMap jobData2 = ctx.getMergedJobDataMap();
            String endpoint = jobData2.get("endpoint").toString();
            String input = jobData2.get("input").toString();
            String operation = jobData2.get("operation").toString();
            String triggerid = jobData2.get("triggerid").toString();
            String groupname = jobData2.get("groupname").toString();
            String level =jobData2.get("level").toString();

            if (level.equalsIgnoreCase("debug"))
                logger.setLevel(Level.DEBUG);
            else
                logger.setLevel(Level.INFO);
            logger.debug("Entering Stateful job class");
            SchedulerFactory schedFact = new StdSchedulerFactory("quartzClient.properties");
            Scheduler sch = schedFact.getScheduler();
            logger.debug(sch.getMetaData().toString());   

            Properties prop = new Properties();
            URL url = getClass().getClassLoader().getResource("authentication.properties");
            InputStream in = url.openStream();
            prop.load(in);
            
            String providerURL = prop.getProperty("CustomDB_URL");
            String username = prop.getProperty("CustomDB_USERNAME");
            String password = prop.getProperty("CustomDB_PASSWORD");
            String tableName = prop.getProperty("TableName");
            String TTtableName = prop.getProperty("TTTableName");

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection(providerURL, username, password);
            Statement stmt = conn.createStatement();
            ResultSet ttstatus = null;

            ttstatus =
              stmt.executeQuery("SELECT STATUS FROM " + TTtableName + " WHERE INTERFACEID = '" + triggerid + "' AND ROWNUM = 1 ORDER BY MODIFIEDDATE DESC");
            logger.debug("Query executed : SELECT STATUS FROM " + TTtableName + " WHERE INTERFACEID = '" + triggerid + "' AND ROWNUM = 1 ORDER BY MODIFIEDDATE DESC");
            
          if (!ttstatus.getString("status").equalsIgnoreCase("WIP")) {
            EndpointInvoke epi= new EndpointInvoke();
            boolean result = epi.InvokeEndpointURI(input, endpoint, operation, tableName ,triggerid, groupname, stmt, level);
        
            if (result == false){
                sch.unscheduleJob(triggerKey);
                logger.info("Due to error in end point invocation Job "  + key + " unscheduled.");
              logger.debug("Due to error in end point invocation Job "  + key + " unscheduled.");
            }           
          }
          
          else{
            
          }
        }
        catch(Exception e){
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
            e.printStackTrace();
        }
    }

}

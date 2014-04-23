package com.SOAScheduler;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.quartz.CronExpression;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;
import org.quartz.impl.matchers.GroupMatcher;


//Database Connection
//Servlet


public class SOASchedulerClient extends JFrame {

    static Logger logger = Logger.getLogger(SOASchedulerClient.class);
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;

    transient Connection pooledConnection = null;
    protected String EmailBody = "";
    protected String EmailSubject = "";
    protected String TriggerID = null;
    protected String name = null;
    protected String GroupName = null;
    protected String CronExpr = null;
    protected String endpointuri = null;
    protected String input = null;
    protected String Operation = null;
    protected String Priority = null;
    protected String Cal = null;
    protected String Statefuljob = null;
    protected String enabled = null;
    protected String status = null;
    protected String ErrorUnscheduleFlag = null;
    protected String providerURL;
    protected String username;
    protected String password;
    protected String tableName;
    protected String HolidayCalTable;
    protected String level = "info";
    protected String env = null;
    private final String hr =
        "\n_________________________________________________________________________________________________\n";
    private final String dothr =
        "------------------------------------------------------------------------------------------------------------------------------------\n";

    protected Scheduler sched;

    protected BorderLayout layoutMain = new BorderLayout();
    protected JPanel contentPane = new JPanel();
    protected JLabel statusBar = new JLabel();
    protected TextArea jTextAreaLog = new TextArea();
    protected JButton jButtonGetExecutingJobs = new JButton();
    protected JButton jButtonScheduleJobs = new JButton();
    private JButton jButtonUnscheduleJobs = new JButton();
    private JButton jButtonRescheduleJobs = new JButton();
    private JCheckBox jCheckBoxDebugInfo = new JCheckBox();
    private JButton jAddJob = new JButton();
    private JButton jCreateReport = new JButton();
    private JButton jErrorMonitor = new JButton();
    private JButton jButtonUpdateCalender = new JButton();


    String[] rscolumnNames = { "TRIGGERID", "NAME", "CRON EXPRESSION" };
    Object[][] rsdata;
    Statement rsstmt = null;
    Connection rsconn = null;
    PreparedStatement rspstmt = null;
    JLabel rsjLblMessage = null;
    JFrame rsframe = new JFrame();

    String[] jfcolumnNames = { "TRIGGERID", "NAME", "ENABLED" };
    Object[][] jfdata;
    Statement jfstmt = null;
    Connection jfconn = null;
    PreparedStatement jfpstmt = null;
    JLabel jfjLblMessage = null;
    JFrame jfframe = new JFrame();


    public SOASchedulerClient() {
        try {
            jbInit(); //Initializing Swing
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            Properties prop = new Properties();
            URL url =
                getClass().getClassLoader().getResource("authentication.properties");
            InputStream in = url.openStream();
            prop.load(in); //Load autthentication properties from the authentication file

            providerURL = prop.getProperty("CustomDB_URL");
            username = prop.getProperty("CustomDB_USERNAME");
            password = prop.getProperty("CustomDB_PASSWORD");
            tableName = prop.getProperty("TableName");
            HolidayCalTable = prop.getProperty("HolidayCalenderTable");


            SchedulerFactory schedFact =
                new StdSchedulerFactory("quartzClient.properties");
            sched =
                    schedFact.getScheduler(); //craeting a new sbhelular using schedularfactory

            logger.debug(sched.getMetaData().toString()); //Log scheduler metadata
            jTextAreaLog.append(sched.getMetaData().toString()); //Display meta data in text area
            jTextAreaLog.append(hr); //Put horizontal tab
            System.out.println(sched.getMetaData().toString()); // Print metadata on the console
            System.out.println(sched.getSchedulerName() + " Started = " +
                               sched.isStarted()); // Print on console if schedular has started


            //      Rescd();
            //      JobListing();
            //      UpdateCalender();
            //      addThresholdTime();
            //      CreateReport();

            jButtonGetExecutingJobs.setVisible(true);
            jButtonScheduleJobs.setVisible(true);
            jButtonUnscheduleJobs.setVisible(true);
            jButtonRescheduleJobs.setVisible(true);
            jCheckBoxDebugInfo.setVisible(true);
            jAddJob.setVisible(true);
            jErrorMonitor.setVisible(true);
            jCreateReport.setVisible(true);
            jButtonUpdateCalender.setVisible(true);

        } catch (Exception e) {
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            jTextAreaLog.append("Error connecting to Scheduler server.");
            jTextAreaLog.append(hr);
            logger.error("Error connecting to Scheduler server.");
            logger.error(w.toString());
            e.printStackTrace();
        } //print error in case schedular fails to start

    }

    private void jbInit() throws Exception {

        try {
            Properties prop = new Properties();
            URL url =
                getClass().getClassLoader().getResource("authentication.properties");
            InputStream in = url.openStream();
            prop.load(in); //load authentication properties in prop object
            env =
prop.getProperty("environment"); // load properties in environment variable
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        //Try block is unnecessary
        this.getContentPane().setLayout(layoutMain);
        contentPane.setLayout(null);
        this.setSize(new Dimension(805, 700));
        this.setTitle("Scheduler Client DashBoard - " + env);

        statusBar.setText("Welcome to SOA Scheduler Client");
        statusBar.setVisible(true);
        statusBar.setFont(new Font("Trebuchet MS", 0, 10));

        jTextAreaLog.setBounds(new Rectangle(5, 5, 790, 460));
        jTextAreaLog.setFont(new Font("Microsoft Sans Serif", 0, 12));
        jTextAreaLog.setEditable(false);

        jButtonGetExecutingJobs.setText("Show Scheduled Jobs");
        jButtonGetExecutingJobs.setBounds(new Rectangle(15, 520, 165, 20));
        jButtonGetExecutingJobs.setVisible(false);
        jButtonGetExecutingJobs.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonGetExecutingJobs_actionPerformed(e);
                }
            });

        jButtonScheduleJobs.setText("Schedule Jobs");
        jButtonScheduleJobs.setBounds(new Rectangle(205, 520, 165, 20));
        jButtonScheduleJobs.setVisible(false);
        jButtonScheduleJobs.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonScheduleJobs_actionPerformed(e);
                }
            });

        jButtonUnscheduleJobs.setText("Unschedule Jobs");
        jButtonUnscheduleJobs.setBounds(new Rectangle(395, 520, 165, 20));
        jButtonUnscheduleJobs.setVisible(false);
        jButtonUnscheduleJobs.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonUnscheduleJobs_actionPerformed(e);
                }
            });

        jButtonRescheduleJobs.setText("Reschedule Jobs");
        jButtonRescheduleJobs.setBounds(new Rectangle(585, 520, 165, 20));
        jButtonRescheduleJobs.setVisible(false);
        jButtonRescheduleJobs.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonRescheduleJobs_actionPerformed(e);
                }
            });

        jCheckBoxDebugInfo.setText("Include Debug info");
        jCheckBoxDebugInfo.setBounds(new Rectangle(210, 485, 165, 20));
        jCheckBoxDebugInfo.setVisible(false);
        jCheckBoxDebugInfo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jCheckBoxDebugInfo_actionPerformed(e);
                }
            });

        jAddJob.setText("Add/Modify Jobs");
        jAddJob.setBounds(new Rectangle(15, 560, 165, 20));
        jAddJob.setVisible(false);
        jAddJob.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jAddJob_actionPerformed(e);
                }
            });

        jErrorMonitor.setText("Error Monitor");
        jErrorMonitor.setBounds(new Rectangle(205, 560, 165, 20));
        jErrorMonitor.setVisible(false);
        jErrorMonitor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jErrorMonitor_actionPerformed(e);
                }
            });

        jCreateReport.setText("Enable Multiple Jobs");
        jCreateReport.setBounds(new Rectangle(395, 560, 165, 20));
        jCreateReport.setVisible(false);
        jCreateReport.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jCreateReport_actionPerformed(e);
                }
            });

        jButtonUpdateCalender.setText("Update Calender");
        jButtonUpdateCalender.setBounds(new Rectangle(585, 560, 165, 20));
        jButtonUpdateCalender.setVisible(false);
        jButtonUpdateCalender.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonUpdateCalender_actionPerformed(e);
                }
            });

        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        contentPane.add(jAddJob, null);
        contentPane.add(jCheckBoxDebugInfo, null);
        contentPane.add(jButtonRescheduleJobs, null);
        contentPane.add(jButtonUnscheduleJobs, null);
        contentPane.add(jButtonScheduleJobs, null);
        contentPane.add(jButtonGetExecutingJobs, null);
        contentPane.add(jTextAreaLog, null);
        contentPane.add(jErrorMonitor, null);
        contentPane.add(jCreateReport, null);
        contentPane.add(jButtonUpdateCalender, null);
        this.getContentPane().add(contentPane, BorderLayout.CENTER);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void jCheckBoxDebugInfo_actionPerformed(ActionEvent e) {
        if (jCheckBoxDebugInfo.isSelected()) {
            level = "debug";
            logger.setLevel(Level.DEBUG);
        } else {
            level = "info";
            logger.setLevel(Level.INFO);
        }
    }

    private void jButtonGetExecutingJobs_actionPerformed(ActionEvent e) {
        ShowScheduledJobs();
    }

    private void jButtonScheduleJobs_actionPerformed(ActionEvent e) {
        ScheduleJobs();
    }

    private void jButtonUnscheduleJobs_actionPerformed(ActionEvent e) {
        UnscheduleJobs();
    }

    private void jButtonRescheduleJobs_actionPerformed(ActionEvent e) {
        RecheduleJobs();
    }

    private void jAddJob_actionPerformed(ActionEvent e) {
        AddJob();
    }

    private void jErrorMonitor_actionPerformed(ActionEvent e) {
        ScheduleErrorMonitor();
    }

    private void jCreateReport_actionPerformed(ActionEvent e) {
        //    CreateReport();
        JobListing();
    }

    private void jButtonUpdateCalender_actionPerformed(ActionEvent e) {
        UpdateCalender();
    }
    //Search Job groups and search jobs in group

    private void ShowScheduledJobs() {
        try {
            SchedulerFactory schedFact =
                new StdSchedulerFactory("quartzClient.properties");
            sched = schedFact.getScheduler();
            List jobGroups = sched.getJobGroupNames();

            if (jobGroups.size() < 1) {
                jTextAreaLog.append("\nNo Scheduled Jobs found !!!");
                jTextAreaLog.append(hr);
            }

            else {
                for (Iterator i = jobGroups.iterator(); i.hasNext(); ) {
                    String jobGroup = (String)i.next();
                    Set jobsInGroup =
                        sched.getJobKeys(GroupMatcher.jobGroupEquals(jobGroup));
                    jTextAreaLog.append("\nThe number of jobs scheduled in Job Group: " +
                                        jobGroup + " = " + jobsInGroup.size() +
                                        "\n");
                    jTextAreaLog.append(dothr);
                    String formattedString =
                        String.format("%-55s %-20s %-50s %n", "Next Fire Time",
                                      "InterfaceID", "Name");
                    jTextAreaLog.append(formattedString);
                    //          jTextAreaLog.append("Name\t\t\t\tInterfaceID\t\tNext Fire Time\n");
                    jTextAreaLog.append(dothr);
                    for (Iterator j = jobsInGroup.iterator(); j.hasNext(); ) {
                        JobKey jobKey = (JobKey)j.next();
                        JobDetail jobDetail = sched.getJobDetail(jobKey);
                        List jobTriggers = sched.getTriggersOfJob(jobKey);

                        String jid = jobDetail.getKey().toString();
                        jid = jid.substring(jid.indexOf(".") + 1);
                        //            jTextAreaLog.append(jid + "\t\t\t\t");
                        if (jobTriggers != null && !jobTriggers.isEmpty()) {
                            Trigger jt = (Trigger)jobTriggers.get(0);
                            String tid = jt.getKey().toString();
                            tid = tid.substring(tid.indexOf(".") + 1);
                            //              jTextAreaLog.append("" + tid + "\t\t");
                            Date dt = new Date();
                            dt = jt.getNextFireTime();
                            SimpleDateFormat df =
                                new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z");
                            df.setTimeZone(TimeZone.getTimeZone("Europe/Oslo"));
                            //              jTextAreaLog.append("" +
                            //                                  df.format(dt)+ "" +
                            //                                  "\n");
                            String pf =
                                String.format("%-40s %-20s %-50s %n", df.format(dt),
                                              tid, jid);
                            jTextAreaLog.append(pf);
                        } else {
                            jTextAreaLog.append("No Trigger Found    ");
                        }
                    }
                    jTextAreaLog.append(dothr);
                }
                jTextAreaLog.append(hr);
            }

        } catch (Exception eje) {
            StringWriter w = new StringWriter();
            eje.printStackTrace(new PrintWriter(w));
            jTextAreaLog.append("Error connecting to Scheduler server.\n");
            jTextAreaLog.append(hr);
            logger.error("Error connecting to Scheduler server.");
            logger.error(w.toString());
            eje.printStackTrace();
        }
    }

    public void SendEmails(String EmailBody, String EmailSubject) {
        // Recipient's email ID needs to be mentioned.
        //String to = "abcd@gmail.com";

        // Sender's email ID needs to be mentioned
        //String from = "web@gmail.com";

        // Assuming you are sending email from localhost
        //String host = "localhost";
        Statement ts = null;
        // Get system properties
        // Properties properties = System.getProperties();
        try {
            Properties prop = new Properties();

            Statement js = null;
            ResultSet table_rs = null;
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn =
                DriverManager.getConnection(providerURL, username, password);
            ts = conn.createStatement();
            ResultSet trigger_rs = null;
            JobDetail job = null;
            Trigger trigger = null;
            logger.debug("Starting of Sending Email ");
            URL url =
                getClass().getClassLoader().getResource("authentication.properties");
            InputStream in = url.openStream();
            prop.load(in);
            logger.debug("Entering mail send");

            String host = prop.getProperty("SMTPServer");
            String port = prop.getProperty("SMTPPort");
            String from = prop.getProperty("DefaultSenderAddress");

            // Setup mail server
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.setProperty("mail.smtp.port", port);
            props.put("mail.debug", "true");

            // Get the default Session object.
            Session session = Session.getInstance(props);
            // Create a default MimeMessage object.
            Message msg = new MimeMessage(session);

            // Set the FROM message
            msg.setFrom(new InternetAddress(from));
            table_rs =
                    ts.executeQuery("select input from " + tableName + " where triggerid='EM' and groupname='EMF'");
            while (table_rs.next()) {
                input = table_rs.getString("input");
            }
            //  Set To: header field of the header. The recipients can be more than one so we use an array but you can
            // use 'new InternetAddress(to)' for only one address.
            //          InternetAddress[] address = { new InternetAddress(to) };
            String[] toArray = input.split(";");
            InternetAddress[] address = new InternetAddress[toArray.length];
            for (int i = 0; i < toArray.length; i++) {
                address[i] = new InternetAddress(toArray[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, address);


            // Set Subject: header field
            msg.setSubject("Scheduler Email Notification: " + TriggerID +
                           EmailSubject);
            msg.setSentDate(new Date());

            // Now set the actual message
            msg.setContent(EmailBody, "text/html");

            // Send message

            Transport.send(msg);

            logger.debug("Mail send complete");

        } catch (Exception e) {
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
        }
        /* catch (MessagingException mex) {
            try {
                ts.executeUpdate("update " + tableName +
                                                         " set status = 'ERROR', error_description = '" +
                                                         mex.toString() +
                                                         "' where triggerid='EM' and groupname='EMF'");
            } catch (SQLException e) {

            }
            logger.debug("Query Executed : update " +
                                                     tableName +
                                                     " set status = 'ERROR', error_description = '" +
                                                     mex.toString() +
                                                     "' where triggerid='EM' and groupname='EMF'");
              StringWriter w = new StringWriter();
              mex.printStackTrace(new PrintWriter(w));
              logger.error(w.toString());

             mex.printStackTrace();
          } catch (SQLException ue) {
            StringWriter w = new StringWriter();
            ue.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
        } catch (IOException e) {
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
        } catch (ClassNotFoundException err) {
            StringWriter w = new StringWriter();
            err.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
        }*/
    }


    public void ScheduleJobs() {

        try {
            TimeZone cetTime = TimeZone.getTimeZone("Europe/Oslo");
            Statement ts = null;
            Statement js = null;
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn =
                DriverManager.getConnection(providerURL, username, password);
            ts = conn.createStatement();
            ResultSet trigger_rs = null;
            JobDetail job = null;
            Trigger trigger = null;
            trigger_rs =
                    ts.executeQuery("SELECT TRIGGERID, NAME, CRONEXPR, PRIORITY FROM " +
                                    tableName +
                                    " WHERE ENABLED='Y' ORDER BY TRIGGERID ASC");
            logger.debug("Query executed : SELECT TRIGGERID, NAME, CRONEXPR, PRIORITY FROM " +
                         tableName +
                         " WHERE ENABLED='Y' ORDER BY TRIGGERID ASC");
            jTextAreaLog.append("\nThe following jobs were successfully scheduled\n");
            jTextAreaLog.append(dothr);
            String formattedString =
                String.format("%-20s %-30s %-55s %n", "InterfaceID", "Group",
                              "Name");
            jTextAreaLog.append(formattedString);
            jTextAreaLog.append(dothr);

            while (trigger_rs.next()) {
                logger.debug("Inside trigger while loop");

                ResultSet job_rs = null;
                TriggerID = trigger_rs.getString("triggerid");
                name = trigger_rs.getString("name");
                CronExpr = trigger_rs.getString("cronexpr");
                Priority = trigger_rs.getString("priority");

                logger.debug("-----Trigger Details-----\n" +
                        "TriggerID : " + TriggerID + "\nJobName : " + name +
                        "\nCronExpr : " + CronExpr + "\nPriority : " +
                        Priority);

                CronExpression cronValid = null;
                boolean isValid = cronValid.isValidExpression(CronExpr);

                if (isValid) {
                    js = conn.createStatement();

                    job_rs =
                            js.executeQuery("SELECT NAME,INPUT,GROUPNAME,ENDPOINT,OPERATION,STATEFULJOB,ENABLED,ERROR_UNSCHEDULE_FLAG,CALENDER FROM " +
                                            tableName + " WHERE TRIGGERID='" +
                                            trigger_rs.getString("triggerid") +
                                            "'");
                    logger.debug("Query executed : SELECT NAME,INPUT,GROUPNAME,ENDPOINT,OPERATION,STATEFULJOB,ENABLED,ERROR_UNSCHEDULE_FLAG,CALENDER FROM " +
                                 tableName + " WHERE TRIGGERID='" +
                                 trigger_rs.getString("triggerid") + "'");

                    while (job_rs.next()) {
                        logger.debug("Inside job while loop");

                        name = job_rs.getString("name");
                        GroupName = job_rs.getString("groupname");
                        endpointuri = job_rs.getString("endpoint");
                        Operation = job_rs.getString("operation");
                        input = job_rs.getString("input");
                        Statefuljob = job_rs.getString("statefuljob");
                        enabled = job_rs.getString("enabled");
                        ErrorUnscheduleFlag =
                                job_rs.getString("ERROR_UNSCHEDULE_FLAG");
                        Cal = job_rs.getString("CALENDER");

                        logger.debug("-----Job Details-----\n" +
                                "JobName : " + name + "\nGroupName : " +
                                GroupName + "\nEndpoint URI : " + endpointuri +
                                "\nOperation : " + Operation + "\nInput : " +
                                input + "\nStatefulJob : " + Statefuljob +
                                "\nEnabled : " + enabled);

                        JobDataMap jobData1 = new JobDataMap();
                        jobData1.put("endpoint", endpointuri);
                        jobData1.put("input", input);
                        jobData1.put("operation", Operation);
                        jobData1.put("tablename", tableName);
                        jobData1.put("triggerid", TriggerID);
                        jobData1.put("groupname", GroupName);
                        jobData1.put("errorunscheduleflag",
                                     ErrorUnscheduleFlag);
                        jobData1.put("level", level);

                        logger.debug("-----Datamap Details-----\n" +
                                jobData1.get("endpoint") +
                                jobData1.get("input") +
                                jobData1.get("operation"));

                        // JobDetail job = null;
                        if (Statefuljob.equalsIgnoreCase("Y")) {
                            job =
newJob(StatelessJob.class).withIdentity(name, GroupName).build();
                        } else {
                            job =
newJob(StatelessJob.class).withIdentity(name, GroupName).build();
                        }

                        trigger =
                                newTrigger().withIdentity(TriggerID, GroupName).modifiedByCalendar(Cal).withSchedule(cronSchedule(CronExpr).inTimeZone(cetTime)).withPriority(Integer.parseInt(Priority)).usingJobData(jobData1).build();


                        try {
                            js = conn.createStatement();
                            ResultSet table_rs = null;
                            if (sched.checkExists(job.getKey())) {
                                logger.info("Cannot schedule " + job.getKey() +
                                            " as Job already exists.");
                                //                  System.out.println("Cannot schedule " + job.getKey() +
                                //                                     " as Job already exists in " +
                                //                                     sched.getSchedulerName() + ".");
                                //                  jTextAreaLog.append("\n" +
                                //                      "Cannot schedule " + job.getKey() +
                                //                      " as Job already exists in " + sched.getSchedulerName() +
                                //                      ".");
                            } else {
                                sched.scheduleJob(job, trigger);
                                js.executeUpdate("UPDATE " + tableName +
                                                 " SET STATUS='SCHEDULED', ERROR_DESCRIPTION = '' WHERE TRIGGERID='" +
                                                 TriggerID + "'");
                                EmailBody =
                                        "<html><font face='Trebuchet MS' size='2'><p>Dear User,</p>" +
                                        "<p>Interface " + TriggerID +
                                        " has been Scheduled successfully.</p><p>" +
                                        "Regards, </p><p>SOA Admin</p><p><i>Note: This is an auto-generated mail. Please do not reply to this mail </i></p>";
                                EmailSubject = " job scheduled";
                                SendEmails(EmailBody, EmailSubject);
                                logger.debug("Query executed : UPDATE " +
                                             tableName +
                                             " SET STATUS='SCHEDULED', ERROR_DESCRIPTION = '' WHERE TRIGGERID='" +
                                             TriggerID + "'");
                                logger.info(job.getKey() +
                                            " successfully Scheduled");
                                //                  System.out.println(job.getKey() + " successfully Scheduled");
                                String pf =
                                    String.format("%-20s %-30s %-80s %n",
                                                  TriggerID, GroupName, name);
                                jTextAreaLog.append(pf);
                                //             jTextAreaLog.append("\n" +
                                //                      job.getKey() + " successfully Scheduled");
                            }


                        } catch (Exception se) {
                            StringWriter w = new StringWriter();
                            se.printStackTrace(new PrintWriter(w));
                            logger.error(w.toString());
                            jTextAreaLog.append(w.toString() + "\n");
                        }
                    }

                    logger.debug("Outside job while loop");
                    job_rs.close();
                    js.close();
                }

                else {
                    Statement updateMessage = null;
                    updateMessage = conn.createStatement();
                    jTextAreaLog.append("Invalid CRON expression specified for Trigger ID : " +
                                        TriggerID);
                    logger.error("Invalid CRON expression specified for Trigger ID : " +
                                 TriggerID);
                    System.out.println("Invalid CRON expression specified for Trigger ID : " +
                                       TriggerID);
                    updateMessage.executeUpdate("UPDATE " + tableName +
                                                " SET STATUS='ERROR', ERROR_DESCRIPTION = 'INVALID CRON EXPRESSION' WHERE TRIGGERID='" +
                                                TriggerID + "'");
                    logger.debug("Query executed : UPDATE " + tableName +
                                 " SET STATUS='ERROR', ERROR_DESCRIPTION = 'INVALID CRON EXPRESSION' WHERE TRIGGERID='" +
                                 TriggerID + "'");
                }
            }

            trigger_rs.close();
            ts.close();
            jTextAreaLog.append(hr);

        } catch (Exception e) {
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
            jTextAreaLog.append(w.toString() + "\n");
            e.printStackTrace();
        }

    }

    private void UnscheduleJobs() {
        try {
            Statement ts = null;
            Statement js = null;

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn =
                DriverManager.getConnection(providerURL, username, password);
            ts = conn.createStatement();
            ResultSet trigger_rs = null;

            trigger_rs =
                    ts.executeQuery("SELECT TRIGGERID,NAME,CRONEXPR,PRIORITY FROM " +
                                    tableName +
                                    " WHERE ENABLED='N' ORDER BY STATUS, TRIGGERID ASC");
            logger.debug("Query executed : SELECT TRIGGERID,NAME,CRONEXPR,PRIORITY FROM " +
                         tableName +
                         " WHERE ENABLED='N' ORDER BY STATUS, TRIGGERID ASC");

            jTextAreaLog.append("\nThe following jobs were successfully Unscheduled\n");
            jTextAreaLog.append(dothr);
            String formattedString =
                String.format("%-20s %-30s %-55s %n", "InterfaceID", "Group",
                              "Name");
            jTextAreaLog.append(formattedString);
            jTextAreaLog.append(dothr);

            while (trigger_rs.next()) {
                logger.debug("Inside trigger while loop");

                ResultSet job_rs = null;
                TriggerID = trigger_rs.getString("triggerid");
                name = trigger_rs.getString("name");
                CronExpr = trigger_rs.getString("cronexpr");
                Priority = trigger_rs.getString("priority");

                logger.debug("-----Trigger Details-----\n" +
                        "TriggerID : " + TriggerID + "\nJobName : " + name +
                        "\nCronExpr : " + CronExpr + "\nPriority : " +
                        Priority);

                js = conn.createStatement();

                job_rs =
                        js.executeQuery("SELECT NAME, INPUT, GROUPNAME, ENDPOINT, OPERATION, STATEFULJOB, ENABLED FROM " +
                                        tableName + " WHERE TRIGGERID='" +
                                        trigger_rs.getString("triggerid") +
                                        "'");
                logger.debug("Query executed : SELECT NAME, INPUT, GROUPNAME, ENDPOINT, OPERATION, STATEFULJOB, ENABLED FROM " +
                             tableName + " WHERE TRIGGERID='" +
                             trigger_rs.getString("triggerid") + "'");

                while (job_rs.next()) {
                    logger.debug("Inside job while loop");

                    name = job_rs.getString("name");
                    GroupName = job_rs.getString("groupname");
                    endpointuri = job_rs.getString("endpoint");
                    Operation = job_rs.getString("operation");
                    input = job_rs.getString("input");
                    Statefuljob = job_rs.getString("statefuljob");
                    enabled = job_rs.getString("enabled");

                    logger.debug("-----Job Details-----\n" +
                            "JobName : " + name + "\nGroupName : " +
                            GroupName + "\nEndpoint URI : " + endpointuri +
                            "\nOperation : " + Operation + "\nInput : " +
                            input + "\nStatefulJob : " + Statefuljob +
                            "\nEnabled : " + enabled);

                    JobDataMap jobData1 = new JobDataMap();
                    jobData1.put("endpoint", endpointuri);
                    jobData1.put("input", input);
                    jobData1.put("operation", Operation);
                    jobData1.put("tablename", tableName);
                    jobData1.put("triggerid", TriggerID);
                    jobData1.put("groupname", GroupName);

                    jobData1.put("level", level);

                    logger.debug("-----Datamap Details-----\n" +
                            jobData1.get("endpoint") + jobData1.get("input") +
                            jobData1.get("operation"));

                    JobDetail job = null;
                    if (Statefuljob.equalsIgnoreCase("Y")) {
                        job =
newJob(StatelessJob.class).withIdentity(name, GroupName).build();
                    } else {
                        job =
newJob(StatelessJob.class).withIdentity(name, GroupName).build();
                    }

                    TimeZone cetTime = TimeZone.getTimeZone("Europe/Oslo");

                    Trigger trigger =
                        newTrigger().withIdentity(TriggerID, GroupName).modifiedByCalendar(Cal).withSchedule(cronSchedule(CronExpr).inTimeZone(cetTime)).withPriority(Integer.parseInt(Priority)).usingJobData(jobData1).build();

                    try {
                        js = conn.createStatement();
                        if (sched.checkExists(trigger.getKey())) {
                            sched.unscheduleJob(trigger.getKey());
                            js.executeUpdate("UPDATE " + tableName +
                                             " SET STATUS='UNSCHEDULED' WHERE TRIGGERID='" +
                                             TriggerID + "'");
                            EmailBody =
                                    "<html><font face='Trebuchet MS' size='2'><p>Dear User,</p>" +
                                    "<p>Interface " + TriggerID +
                                    " has been Unscheduled successfully.</p><p>" +
                                    "Regards, </p><p>SOA Admin</p><p><i>Note: This is an auto-generated mail. Please do not reply to this mail </i></p>";
                            EmailSubject = " job unscheduled";

                            SendEmails(EmailBody, EmailSubject);

                            logger.debug("Query executed : UPDATE " +
                                         tableName +
                                         " SET STATUS='UNSCHEDULED' WHERE TRIGGERID='" +
                                         TriggerID + "'");
                            logger.info(job.getKey() +
                                        " successfully Unscheduled");
                            System.out.println(job.getKey() +
                                               " successfully Unscheduled");
                            String pf =
                                String.format("%-20s %-30s %-80s %n", TriggerID,
                                              GroupName, name);
                            jTextAreaLog.append(pf);


                            //                jTextAreaLog.append("\n" +
                            //                    job.getKey() + " successfully Unscheduled");
                        }
                        // put ur code here
                        /*  try {
                                Properties prop = new Properties();

                                logger.debug("Starting of Sending Email ");
                                URL url =
                                    getClass().getClassLoader().getResource("authentication.properties");
                                InputStream in = url.openStream();
                                prop.load(in);
                                ResultSet table_rs = null;
                                EmailBody =
                                        "<html><font face='Trebuchet MS' size='2'><p>Dear User,</p>" +
                                        "<p>Interface " + TriggerID +
                                        " has been Unscheduled successfully.</p><p>" +
                                        "Regards, </p><p>SOA Admin</p><p><i>Note: This is an auto-generated mail. Please do not reply to this mail </i></p>";


                                logger.debug("Entering mail send");
                                String host = prop.getProperty("SMTPServer");
                                String port = prop.getProperty("SMTPPort");
                                String from =
                                    prop.getProperty("DefaultSenderAddress");
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
                                table_rs =
                                        ts.executeQuery("select input from " +
                                                        tableName +
                                                        " where triggerid='EM' and groupname='EMF'");
                                while (table_rs.next()) {
                                    input = table_rs.getString("input");
                                }

                                // The recipients can be more than one so we use an array but you can
                                // use 'new InternetAddress(to)' for only one address.
                                //          InternetAddress[] address = { new InternetAddress(to) };
                                String[] toArray = input.split(";");
                                InternetAddress[] address =
                                    new InternetAddress[toArray.length];
                                for (int i = 0; i < toArray.length; i++) {
                                    address[i] =
                                            new InternetAddress(toArray[i]);
                                }
                                msg.setRecipients(Message.RecipientType.TO,
                                                  address);

                                // Set the message subject and date we sent it.
                                msg.setSubject("Scheduler Email Notification: " +
                                               TriggerID + " job unscheduled");
                                msg.setSentDate(new Date());
                                // Set message content
                                msg.setContent(EmailBody, "text/html");
                                //msg.setText(EmailBody);

                                // Send the message
                                Transport.send(msg);
                                logger.debug("Mail send complete");
                            } catch (MessagingException mex) {
                                ts.executeUpdate("update " + tableName +
                                                 " set status = 'ERROR', error_description = '" +
                                                 mex.toString() +
                                                 "' where triggerid='EM' and groupname='EMF'");
                                logger.debug("Query Executed : update " +
                                             tableName +
                                             " set status = 'ERROR', error_description = '" +
                                             mex.toString() +
                                             "' where triggerid='EM' and groupname='EMF'");
                                StringWriter w = new StringWriter();
                                mex.printStackTrace(new PrintWriter(w));
                                logger.error(w.toString());
                                mex.printStackTrace();

                            }
                            logger.debug("Error Monitor complete");
                            // end of code change
                        }*/

                        else {
                            logger.info("Cannot unschedule " + job.getKey() +
                                        " as Job does not exist");
                            System.out.println("Cannot unschedule " +
                                               job.getKey() +
                                               " as Job does not exist");
                            //jTextAreaLog.append("Cannot unschedule " + job.getKey() + " as Job does not exist in " + sched.getSchedulerName() + ".\n" );
                        }


                    } catch (Exception se) {
                        StringWriter w = new StringWriter();
                        se.printStackTrace(new PrintWriter(w));
                        logger.error(w.toString());
                        jTextAreaLog.append(w.toString() + "\n");
                    }
                }

                logger.debug("Outside job while loop");
                job_rs.close();
                js.close();
            }

            trigger_rs.close();
            ts.close();
            jTextAreaLog.append(hr);

        } catch (Exception e) {
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
            jTextAreaLog.append(w.toString() + "\n");
            e.printStackTrace();
        }

    }

    private void RecheduleJobs() {
        try {
            RescheduleJobsInit();
            Container cp = rsframe.getContentPane();
            JOptionPane.showMessageDialog(null, cp,
                                          "Modify Cron Expression for Rescheduling",
                                          JOptionPane.PLAIN_MESSAGE);
            rsjLblMessage.setText("");
            rsframe.dispose();

            Statement ts = null;
            Statement js = null;

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn =
                DriverManager.getConnection(providerURL, username, password);
            ts = conn.createStatement();
            ResultSet trigger_rs = null;

            trigger_rs =
                    ts.executeQuery("SELECT TRIGGERID, NAME, CRONEXPR, PRIORITY FROM " +
                                    tableName +
                                    " WHERE ENABLED='R' ORDER BY TRIGGERID ASC");
            logger.debug("Query executed : SELECT TRIGGERID, NAME, CRONEXPR, PRIORITY FROM " +
                         tableName +
                         " WHERE ENABLED='R' ORDER BY TRIGGERID ASC");

            while (trigger_rs.next()) {
                logger.debug("Inside trigger while loop");

                ResultSet job_rs = null;
                TriggerID = trigger_rs.getString("triggerid");
                name = trigger_rs.getString("name");
                CronExpr = trigger_rs.getString("cronexpr");
                Priority = trigger_rs.getString("priority");

                logger.debug("-----Trigger Details-----\n" +
                        "TriggerID : " + TriggerID + "\nJobName : " + name +
                        "\nCronExpr : " + CronExpr + "\nPriority : " +
                        Priority);

                CronExpression cronValid = null;
                boolean isValid = cronValid.isValidExpression(CronExpr);

                if (isValid) {
                    js = conn.createStatement();

                    job_rs =
                            js.executeQuery("SELECT NAME,INPUT,GROUPNAME,ENDPOINT,OPERATION,STATEFULJOB,ENABLED,ERROR_UNSCHEDULE_FLAG,CALENDER FROM " +
                                            tableName + " WHERE TRIGGERID='" +
                                            trigger_rs.getString("triggerid") +
                                            "'");
                    logger.debug("Query executed : NAME,INPUT,GROUPNAME,ENDPOINT,OPERATION,STATEFULJOB,ENABLED,ERROR_UNSCHEDULE_FLAG,CALENDER FROM " +
                                 tableName + " WHERE TRIGGERID='" +
                                 trigger_rs.getString("triggerid") + "'");

                    while (job_rs.next()) {
                        logger.debug("Inside job while loop");

                        name = job_rs.getString("name");
                        GroupName = job_rs.getString("groupname");
                        endpointuri = job_rs.getString("endpoint");
                        Operation = job_rs.getString("operation");
                        input = job_rs.getString("input");
                        Statefuljob = job_rs.getString("statefuljob");
                        enabled = job_rs.getString("enabled");
                        ErrorUnscheduleFlag =
                                job_rs.getString("ERROR_UNSCHEDULE_FLAG");
                        Cal = job_rs.getString("CALENDER");

                        logger.debug("-----Job Details-----\n" +
                                "JobName : " + name + "\nGroupName : " +
                                GroupName + "\nEndpoint URI : " + endpointuri +
                                "\nOperation : " + Operation + "\nInput : " +
                                input + "\nStatefulJob : " + Statefuljob +
                                "\nEnabled : " + enabled);

                        JobDataMap jobData1 = new JobDataMap();
                        jobData1.put("endpoint", endpointuri);
                        jobData1.put("input", input);
                        jobData1.put("operation", Operation);
                        jobData1.put("tablename", tableName);
                        jobData1.put("triggerid", TriggerID);
                        jobData1.put("groupname", GroupName);
                        jobData1.put("errorunscheduleflag",
                                     ErrorUnscheduleFlag);
                        jobData1.put("level", level);

                        logger.debug("-----Datamap Details-----\n" +
                                jobData1.get("endpoint") +
                                jobData1.get("input") +
                                jobData1.get("operation"));

                        JobDetail job = null;
                        if (Statefuljob.equalsIgnoreCase("Y")) {
                            job =
newJob(StatelessJob.class).withIdentity(name, GroupName).build();
                        } else {
                            job =
newJob(StatelessJob.class).withIdentity(name, GroupName).build();
                        }

                        TimeZone cetTime = TimeZone.getTimeZone("Europe/Oslo");

                        Trigger trigger =
                            newTrigger().withIdentity(TriggerID, GroupName).withSchedule(cronSchedule(CronExpr).inTimeZone(cetTime)).withPriority(Integer.parseInt(Priority)).usingJobData(jobData1).build();


                        try {
                            js = conn.createStatement();
                            Trigger oldTrigger =
                                newTrigger().withIdentity(TriggerID,
                                                          GroupName).build();

                            if (sched.checkExists(oldTrigger.getKey())) {
                                sched.rescheduleJob(oldTrigger.getKey(),
                                                    trigger);
                                js.executeUpdate("UPDATE " + tableName +
                                                 " SET ENABLED ='Y', STATUS = 'RESCHEDULED' WHERE TRIGGERID = '" +
                                                 TriggerID + "'");
                                logger.debug("Query executed : UPDATE " +
                                             tableName +
                                             " SET ENABLED ='Y', STATUS = 'RESCHEDULED' WHERE TRIGGERID = '" +
                                             TriggerID + "'");
                                logger.info(job.getKey() +
                                            " successfully Rescheduled");
                                //                  System.out.println(job.getKey() +
                                //                                     " successfully Rescheduled");
                                jTextAreaLog.append("\n" +
                                        job.getKey() +
                                        " successfully Rescheduled");
                                //                  js.executeUpdate("update " + tableName +
                                //                                   " set enabled='Y'where triggerid='" +
                                //                                   TriggerID + "'");
                            }

                            else {
                                logger.info("Cannot reschedule " +
                                            job.getKey() +
                                            " as Job does not exist.");
                                System.out.println("Cannot reschedule " +
                                                   job.getKey() +
                                                   " as Job does not exist.");
                                jTextAreaLog.append("\n" +
                                        "Cannot reschedule " + job.getKey() +
                                        " as Job does not exist.");
                                js.executeUpdate("UPDATE " + tableName +
                                                 " SET ENABLED='N' WHERE TRIGGERID = '" +
                                                 TriggerID + "'");
                                logger.debug("UPDATE " + tableName +
                                             " SET ENABLED='N' WHERE TRIGGERID = '" +
                                             TriggerID + "'");
                            }


                        } catch (Exception se) {
                            StringWriter w = new StringWriter();
                            se.printStackTrace(new PrintWriter(w));
                            logger.error(w.toString());
                            jTextAreaLog.append(w.toString() + "\n");
                        }
                    }

                    logger.debug("Outside job while loop");
                    job_rs.close();
                    js.close();

                }

                else {
                    Statement updateMessage = null;
                    updateMessage = conn.createStatement();
                    logger.error("Invalid CRON expression specified for Trigger ID : " +
                                 TriggerID);
                    System.out.println("Invalid CRON expression specified for Trigger ID : " +
                                       TriggerID);
                    jTextAreaLog.append("Invalid CRON expression specified for Trigger ID : " +
                                        TriggerID);
                    updateMessage.executeUpdate("UPDATE " + tableName +
                                                " SET STATUS='ERROR', ERROR_DESCRIPTION = 'INVALID CRON EXPRESSION' WHERE TRIGGERID='" +
                                                TriggerID + "'");
                    logger.debug("Query executed : UPDATE " + tableName +
                                 " SET STATUS='ERROR', ERROR_DESCRIPTION = 'INVALID CRON EXPRESSION' WHERE TRIGGERID='" +
                                 TriggerID + "'");
                }
            }

            trigger_rs.close();
            ts.close();
            jTextAreaLog.append(hr);
        } catch (Exception e) {
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
            jTextAreaLog.append(w.toString() + "\n");
            e.printStackTrace();
        }
    }

    private void RecheduleJobsFireTableUpdate() {
        rsjLblMessage.setText("");
        try {
            //      int[] updateCounts =
            rspstmt.executeBatch();
            //      for (int i = 0; i < updateCounts.length; i++)
            //        System.out.println(updateCounts[i]);
            rsconn.commit();
            rsjLblMessage.setText("<html><center><font color='green'>Updated successfully in the table</font></center></html>");
            //      conn.close();
            //      pstmt.close();
        } catch (Exception e) {
            rsjLblMessage.setText("<html><center><font color='red'>Error in updating the table. Please look into logs for further information.</font></center></html>");
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
            e.printStackTrace();
        }
    }

    public void RescheduleJobsInit() {

        JTable table = new JTable(new MyTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 500));
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(30);
        TableColumnModel columnModel = table.getColumnModel();

        try {
            //      Properties prop = new Properties();
            //      URL url =
            //        getClass().getClassLoader().getResource("authentication.properties");
            //      InputStream in = url.openStream();
            //      prop.load(in);
            //
            //      String providerURL = prop.getProperty("CustomDB_URL");
            //      String username = prop.getProperty("CustomDB_USERNAME");
            //      String password = prop.getProperty("CustomDB_PASSWORD");
            //      tableName = prop.getProperty("TableName");

            //      Statement stmt = null;
            Class.forName("oracle.jdbc.driver.OracleDriver");
            rsconn =
                    DriverManager.getConnection(providerURL, username, password);
            rsstmt = rsconn.createStatement();
            ResultSet table_rs = null;

            table_rs =
                    rsstmt.executeQuery("SELECT COUNT(*) NUM FROM " + tableName +
                                        " WHERE ENABLED IS NOT NULL ");
            int rowCount = 0;
            while (table_rs.next())
                rowCount = table_rs.getInt("NUM");

            rsdata = new Object[rowCount][3];

            table_rs =
                    rsstmt.executeQuery("SELECT TRIGGERID, NAME, CRONEXPR FROM " +
                                        tableName +
                                        " WHERE ENABLED IS NOT NULL ORDER BY TRIGGERID ASC");
            int i = 0;
            while (table_rs.next()) {
                rsdata[i][0] = table_rs.getString("TRIGGERID");
                rsdata[i][1] = table_rs.getString("NAME");
                rsdata[i][2] = table_rs.getString("CRONEXPR");
                TableColumn col = columnModel.getColumn(2);
                col.setCellEditor(new CronValidator());
                i++;
            }

            rsconn.setAutoCommit(false);
            rspstmt =
                    rsconn.prepareStatement("UPDATE " + tableName + " SET ENABLED = 'R' , CRONEXPR = ? WHERE TRIGGERID = ? AND NAME = ?");
            System.out.println("UPDATE " + tableName +
                               " SET ENABLED = 'R' , CRONEXPR = ? WHERE TRIGGERID = ? AND NAME = ?");
        } catch (Exception e) {
            rsjLblMessage.setText("<html><center><font color='red'>Error in updating the table. Please look into logs for further infornmation.</font></center></html>");
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
            e.printStackTrace();
        }

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        rsjLblMessage = new JLabel();
        JButton jBtnUpdate = new JButton("Update");
        jBtnUpdate.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jBtnUpdate_actionPerformed(e);

                }
            });


        Container contentPane = rsframe.getContentPane();
        contentPane.setLayout(new BorderLayout());
        scrollPane.setBounds(new Rectangle(5, 5, 500, 500));
        jBtnUpdate.setBounds(new Rectangle(200, 520, 100, 20));
        rsjLblMessage.setBounds(new Rectangle(5, 540, 490, 20));
        contentPane.setLayout(null);
        contentPane.add(scrollPane, null);
        contentPane.add(jBtnUpdate, null);
        contentPane.add(rsjLblMessage, null);
        contentPane.setPreferredSize(new Dimension(510, 580));
        //      frame.setContentPane(contentPane);
        //      frame.setSize(520, 600);
        //      frame.setVisible(true);
    }

    class MyTableModel extends AbstractTableModel {


        public int getColumnCount() {
            return rscolumnNames.length;
        }

        public int getRowCount() {
            return rsdata.length;
        }

        public String getColumnName(int col) {
            return rscolumnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return rsdata[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */

        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */

        public void setValueAt(Object value, int row, int col) {
            rsdata[row][col] = value;
            fireTableCellUpdated(row, col);
            try {
                System.out.println(rsdata[row][0].toString());
                System.out.println(rsdata[row][1].toString());
                System.out.println(rsdata[row][2].toString());

                rspstmt.setString(1, rsdata[row][2].toString());
                rspstmt.setString(2, rsdata[row][0].toString());
                rspstmt.setString(3, rsdata[row][1].toString());
                rspstmt.addBatch();

            }

            catch (Exception e) {
                rsjLblMessage.setText("<html><center><font color='red'>Error in updating the table. Please look into logs for further infornmation.</font></center></html>");
                StringWriter w = new StringWriter();
                e.printStackTrace(new PrintWriter(w));
                logger.error(w.toString());
                e.printStackTrace();
            }
        }

    }

    class CronValidator extends AbstractCellEditor implements TableCellEditor {

        JTextField textField;

        public CronValidator() {
            textField = new JTextField();
        }


        public Component getTableCellEditorComponent(JTable table,
                                                     Object value,
                                                     boolean isSelected,
                                                     int row, int col) {
            textField.setText(String.valueOf(value));
            return textField;
        }


        public Object getCellEditorValue() {
            String s = textField.getText();
            return s;
        }

        public boolean stopCellEditing() {
            String s = getCellEditorValue().toString();
            CronExpression cronValid = null;
            if (!cronValid.isValidExpression(s)) {

                fireEditingCanceled();
                return false;
            }
            return super.stopCellEditing();
        }

    }

    private void jBtnUpdate_actionPerformed(ActionEvent e) {
        RecheduleJobsFireTableUpdate();
    }

    private void AddJob() {
        try {
            DetailsForm df = new DetailsForm();
            List hcList = sched.getCalendarNames();
            String[] hc = (new String[hcList.size()]);
            hcList.toArray(hc);
            df.jbInit(hc);
        } catch (Exception e) {
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
            jTextAreaLog.append(w.toString() + "\n");
            e.printStackTrace();
        }
    }

    private void CreateReport() {

        Statement ts = null;
        int noOfScheduledJobs = 0;
        int noOfUnScheduledJobs = 0;
        int noOfErroredJobs = 0;
        int noOfNeverScheduled = 0;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn =
                DriverManager.getConnection(providerURL, username, password);
            ts = conn.createStatement();
            ResultSet table_rs = null;

            table_rs =
                    ts.executeQuery("SELECT TRIGGERID,NAME,GROUPNAME,ENABLED,STATUS FROM " +
                                    tableName + " WHERE STATUS IS NOT NULL");
            logger.debug("Query executed : SELECT TRIGGERID,NAME,GROUPNAME,ENABLED,STATUS FROM " +
                         tableName + " WHERE STATUS IS NOT NULL");

            jTextAreaLog.append("Current Status in Scheduler\n");
            jTextAreaLog.append("-----------------------------------------\n\n");

            while (table_rs.next()) {
                //        logger.debug("Inside trigger while loop");

                TriggerID = table_rs.getString("triggerid");
                name = table_rs.getString("name");
                GroupName = table_rs.getString("groupname");
                enabled = table_rs.getString("enabled");
                status = table_rs.getString("status");

                if (status.equalsIgnoreCase("RUNNING") ||
                    status.equalsIgnoreCase("SCHEDULED"))
                    noOfScheduledJobs++;

                if (status.equalsIgnoreCase("UNSCHEDULED"))
                    noOfUnScheduledJobs++;

                if (status.equalsIgnoreCase("ERROR"))
                    noOfErroredJobs++;
            }

            table_rs =
                    ts.executeQuery("SELECT COUNT(*) NULLCOUNT FROM " + tableName +
                                    " WHERE STATUS IS NULL");
            logger.debug("Query executed : SELECT COUNT(*) NULLCOUNT FROM " +
                         tableName + " WHERE STATUS IS NULL");
            while (table_rs.next()) {
                logger.debug(table_rs.getInt("NullCount"));
                noOfNeverScheduled = table_rs.getInt("NullCount");
            }

            table_rs.close();
            ts.close();
            jTextAreaLog.append("Running\\Scheduled Jobs \t " +
                                noOfScheduledJobs + "\n");
            jTextAreaLog.append("UnScheduled Jobs \t " + noOfUnScheduledJobs +
                                "\n");
            jTextAreaLog.append("Errored Jobs \t\t " + noOfErroredJobs + "\n");
            jTextAreaLog.append("Never Scheduled Jobs \t " +
                                noOfNeverScheduled + "\n");
            jTextAreaLog.append(hr);


        } catch (Exception e) {
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
            jTextAreaLog.append(w.toString() + "\n");
            e.printStackTrace();
        }
    }

    private void ScheduleErrorMonitor() {

        // for Error Monitor
        try {
            TimeZone cetTime = TimeZone.getTimeZone("Europe/Oslo");
            Statement ts = null;
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn =
                DriverManager.getConnection(providerURL, username, password);
            ts = conn.createStatement();
            ResultSet trigger_rs = null;
            JobDetail job = null;
            Trigger trigger = null;

            //      jTextAreaLog.append("Query will be executed\n");
            trigger_rs =
                    ts.executeQuery("SELECT TRIGGERID, NAME, GROUPNAME, CRONEXPR FROM " +
                                    tableName +
                                    " WHERE TRIGGERID='EM' AND GROUPNAME='EMF'");
            logger.debug("Query executed : SELECT TRIGGERID, NAME, GROUPNAME, CRONEXPR FROM " +
                         tableName +
                         " WHERE TRIGGERID='EM' AND GROUPNAME='EMF'");
            //      jTextAreaLog.append("Query executed : select triggerid, name, groupname, cronexpr from " +
            //                        tableName + " where triggerid='EM' and groupname='EMF'");
            while (trigger_rs.next()) {

                CronExpr = trigger_rs.getString("cronexpr");
                TriggerID = trigger_rs.getString("triggerid");
                name = trigger_rs.getString("name");
                GroupName = trigger_rs.getString("groupname");
                //      jTextAreaLog.append(CronExpr + "\n");
                job =
newJob(ErrorMonitor.class).withIdentity(name, GroupName).build();
                //        jTextAreaLog.append("Job set\n");
                trigger = newTrigger().withIdentity(TriggerID, GroupName).
                        //        .modifiedByCalendar("UserDefined1")
                        withSchedule(cronSchedule(CronExpr).inTimeZone(cetTime)).withPriority(1).build();

                //      jTextAreaLog.append( trigger.getCalendarName());
                //        jTextAreaLog.append("Trigger Set \n");

                Object[] options = { "Schedule", "Unschedule", "Reschedule" };
                int n =
                    JOptionPane.showOptionDialog(this, "Do you want to do with Error Monitor?",
                                                 "Error Monitoring Framework",
                                                 JOptionPane.YES_NO_CANCEL_OPTION,
                                                 JOptionPane.QUESTION_MESSAGE,
                                                 null,
                        //do not use a custom Icon
                        options, //the titles of buttons
                        options[0]); //default button title


                if (n == JOptionPane.YES_OPTION) {
                    if (!sched.checkExists(job.getKey())) {
                        //          jTextAreaLog.append("Inside if");
                        sched.scheduleJob(job, trigger);
                        jTextAreaLog.append("\nError Monitor Job successfully scheduled\n");
                        ts.executeQuery("UPDATE " + tableName +
                                        " SET STATUS='SCHEDULED', ERROR_DESCRIPTION='' WHERE TRIGGERID='EM' AND GROUPNAME='EMF'");
                        logger.debug("Query executed : UPDATE " + tableName +
                                     " SET STATUS='SCHEDULED', ERROR_DESCRIPTION='' WHERE TRIGGERID='EM' AND GROUPNAME='EMF'");
                    }

                    else {
                        jTextAreaLog.append("\nError Monitor Job is already present in the scheduler!!!\n");
                    }
                }

                if (n == JOptionPane.NO_OPTION) {
                    if (sched.checkExists(job.getKey())) {
                        sched.unscheduleJob(trigger.getKey());
                        jTextAreaLog.append("\nError Monitor Job successfully Unscheduled\n");
                        ts.executeQuery("UPDATE " + tableName +
                                        " SET STATUS='UNSCHEDULED', ERROR_DESCRIPTION='' WHERE TRIGGERID='EM' AND GROUPNAME='EMF'");
                        logger.debug("Query executed : UPDATE " + tableName +
                                     " SET STATUS='UNSCHEDULED', ERROR_DESCRIPTION='' WHERE TRIGGERID='EM' AND GROUPNAME='EMF'");
                    }

                    else {
                        jTextAreaLog.append("\nError Monitor Job is not present!!!\n");
                    }
                }

                if (n == JOptionPane.CANCEL_OPTION) {
                    CronExpr =
                            JOptionPane.showInputDialog(null, "Enter new CRON expression",
                                                        CronExpr, 1);
                    if (CronExpr != null) {
                        CronExpression cronValid = null;
                        if (cronValid.isValidExpression(CronExpr)) {
                            if (sched.checkExists(job.getKey())) {
                                Trigger newTrigger =
                                    newTrigger().withIdentity(TriggerID,
                                                              GroupName).withSchedule(cronSchedule(CronExpr).inTimeZone(cetTime)).withPriority(1).build();

                                sched.rescheduleJob(trigger.getKey(),
                                                    newTrigger);
                                jTextAreaLog.append("\nError monitor Job successfully Rescheduled\n");
                                ts.executeQuery("UPDATE " + tableName +
                                                " SET CRONEXPR ='" + CronExpr +
                                                "', STATUS='SCHEDULED', ERROR_DESCRIPTION='' WHERE TRIGGERID='EM' AND GROUPNAME='EMF'");
                                logger.debug("Query executed : UPDATE " +
                                             tableName + " SET CRONEXPR ='" +
                                             CronExpr +
                                             "', STATUS='SCHEDULED', ERROR_DESCRIPTION='' WHERE TRIGGERID='EM' AND GROUPNAME='EMF'");
                            } else {
                                jTextAreaLog.append("\nError Monitor Job is not present!!!\n");
                            }
                        } else {
                            jTextAreaLog.append("\nYou specified invalid Cron Expression!!!\n");
                        }
                    }
                }
                jTextAreaLog.append(hr);
            }

        } catch (Exception se) {
            se.printStackTrace();
            StringWriter w = new StringWriter();
            se.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
            jTextAreaLog.append(w.toString() + "\n");
        }

    }

    private void UpdateCalender() {

        HolidayCalendar hc = new HolidayCalendar();
        //    HolidayCalendar noHolidays = new HolidayCalendar();

        try {
            //      sched.addCalendar("No_Holidays", noHolidays, true, true);
            //      sched.deleteCalendar("UserDefined1");
            TimeZone cetTime = TimeZone.getTimeZone("Europe/Oslo");
            hc.setTimeZone(cetTime);
            Statement ts = null;
            Statement js = null;
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn =
                DriverManager.getConnection(providerURL, username, password);
            ts = conn.createStatement();
            js = conn.createStatement();
            ResultSet Cals = null;
            ResultSet trigger_rs = null;

            Cals =
ts.executeQuery("SELECT DISTINCT(CAL_NAME) FROM " + HolidayCalTable);
            logger.debug("SELECT DISTINCT(CAL_NAME) FROM " + HolidayCalTable);
            jTextAreaLog.append("The following Calenders were updated\n");

            while (Cals.next()) {
                String calName = Cals.getString("CAL_NAME");
                jTextAreaLog.append("\n------------------------\n");
                jTextAreaLog.append(calName + "\n------------------------\n");
                trigger_rs =
                        js.executeQuery("SELECT DATES FROM " + HolidayCalTable +
                                        " WHERE CAL_NAME = '" + calName + "'");
                logger.debug("SELECT DATES FROM " + HolidayCalTable +
                             " WHERE CAL_NAME = '" + calName + "'");

                while (trigger_rs.next()) {
                    jTextAreaLog.append(trigger_rs.getDate("DATES").toString() +
                                        "\n");
                    Date dt = trigger_rs.getDate("DATES");
                    Calendar cal = Calendar.getInstance(cetTime);
                    cal.setTime(dt);
                    cal.add(cal.DATE, 1);
                    dt = cal.getTime();
                    logger.debug("This date will be added to calender" +
                                 dt.toString() + "\n");
                    hc.addExcludedDate(dt);
                }
                sched.addCalendar(calName, hc, true, true);
                jTextAreaLog.append("------------------------\n");
            }
            jTextAreaLog.append(hr);
        } catch (Exception e) {
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            jTextAreaLog.append("Could not add calender to Scheduler\n");
            jTextAreaLog.append(hr);
            logger.error("Could not add calender to Scheduler");
            logger.error(w.toString());
            e.printStackTrace();
        }

    }

    private void JobListing() {
        //    JobForm jf = new JobForm();
        //    jf.jbInit();

        JobListingInit();
        Container cp = jfframe.getContentPane();
        JOptionPane.showMessageDialog(null, cp,
                                      "Select jobs which needs to be enabled",
                                      JOptionPane.PLAIN_MESSAGE);
        jfjLblMessage.setText("");
        jfframe.dispose();

        UnscheduleJobs();
        ScheduleJobs();
    }

    private void jfjBtnUpdate_actionPerformed(ActionEvent e) {
        JobListFireTableUpdate();
    }

    private void JobListFireTableUpdate() {
        jfjLblMessage.setText("");
        try {
            int[] updateCounts = jfpstmt.executeBatch();
            jfconn.commit();
            jfjLblMessage.setText("<html><center><font color='green'>Updated successfully in the table</font></center></html>");
        } catch (Exception e) {
            jfjLblMessage.setText("<html><center><font color='red'>Error in updating the table. Please look into logs for further information.</font></center></html>");
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
            e.printStackTrace();
        }
    }

    public void JobListingInit() {

        JTable table = new JTable(new JobListTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 500));
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(30);
        try {
            //      Properties prop = new Properties();
            //      URL url =
            //        getClass().getClassLoader().getResource("authentication.properties");
            //      InputStream in = url.openStream();
            //      prop.load(in);
            //
            //      String providerURL = prop.getProperty("CustomDB_URL");
            //      String username = prop.getProperty("CustomDB_USERNAME");
            //      String password = prop.getProperty("CustomDB_PASSWORD");
            //      tableName = prop.getProperty("TableName");
            //
            //      Statement jfstmt = null;
            Class.forName("oracle.jdbc.driver.OracleDriver");
            jfconn =
                    DriverManager.getConnection(providerURL, username, password);
            jfstmt = jfconn.createStatement();
            ResultSet table_rs = null;

            table_rs =
                    jfstmt.executeQuery("SELECT COUNT(*) NUM FROM " + tableName +
                                        " WHERE ENABLED IS NOT NULL");
            int rowCount = 0;
            while (table_rs.next())
                rowCount = table_rs.getInt("NUM");

            jfdata = new Object[rowCount][3];

            table_rs =
                    jfstmt.executeQuery("SELECT TRIGGERID, NAME, ENABLED FROM " +
                                        tableName +
                                        " WHERE ENABLED IS NOT NULL ORDER BY TRIGGERID ASC");
            int i = 0;
            while (table_rs.next()) {
                jfdata[i][0] = table_rs.getString("TRIGGERID");
                jfdata[i][1] = table_rs.getString("NAME");
                String e = table_rs.getString("ENABLED");
                if (e.equalsIgnoreCase("Y"))
                    jfdata[i][2] = new Boolean(true);
                else
                    jfdata[i][2] = new Boolean(false);
                i++;

            }

            jfconn.setAutoCommit(false);
            jfpstmt =
                    jfconn.prepareStatement("UPDATE " + tableName + " SET ENABLED = ? WHERE TRIGGERID = ? AND NAME = ?");
            System.out.println("UPDATE " + tableName +
                               " SET ENABLED = ? WHERE TRIGGERID = ? AND NAME = ?");
        } catch (Exception e) {
            jfjLblMessage.setText("<html><center><font color='red'>Error in updating the table. Please look into logs for further information.</font></center></html>");
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error(w.toString());
            e.printStackTrace();
        }

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        jfjLblMessage = new JLabel();
        JButton jfjBtnUpdate = new JButton("Update");
        jfjBtnUpdate.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jfjBtnUpdate_actionPerformed(e);

                }
            });
        Container contentPane = jfframe.getContentPane();
        contentPane.setLayout(new BorderLayout());
        scrollPane.setBounds(new Rectangle(5, 5, 500, 500));
        jfjBtnUpdate.setBounds(new Rectangle(200, 520, 100, 20));
        jfjLblMessage.setBounds(new Rectangle(5, 540, 490, 20));
        contentPane.setLayout(null);
        contentPane.add(scrollPane, null);
        contentPane.add(jfjBtnUpdate, null);
        contentPane.add(jfjLblMessage, null);
        contentPane.setPreferredSize(new Dimension(510, 580));
        jfframe.setContentPane(contentPane);
    }

    class JobListTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        public int getColumnCount() {
            return jfcolumnNames.length;
        }

        public int getRowCount() {
            return jfdata.length;
        }

        public String getColumnName(int col) {
            return jfcolumnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return jfdata[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */

        public boolean isCellEditable(int row, int col) {
            //Note that the jfdata/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * jfdata can change.
         */

        public void setValueAt(Object value, int row, int col) {
            jfdata[row][col] = value;
            fireTableCellUpdated(row, col);

            try {
                String e;
                if (value.toString().equalsIgnoreCase("true"))
                    e = "Y";
                else
                    e = "N";

                //        System.out.println(jfdata[row][0].toString());
                //        System.out.println(jfdata[row][1].toString());

                jfpstmt.setString(1, e);
                jfpstmt.setString(2, jfdata[row][0].toString());
                jfpstmt.setString(3, jfdata[row][1].toString());
                jfpstmt.addBatch();
            }

            catch (Exception e) {
                jfjLblMessage.setText("<html><center><font color='red'>Error in updating the table. Please look into logs for further infornmation.</font></center></html>");
                StringWriter w = new StringWriter();
                e.printStackTrace(new PrintWriter(w));
                logger.error(w.toString());
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {

        SOASchedulerClient clnt;
        clnt = new SOASchedulerClient();

    }

}

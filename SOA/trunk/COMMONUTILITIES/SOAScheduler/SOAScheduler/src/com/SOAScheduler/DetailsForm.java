package com.SOAScheduler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.InputStream;

import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.quartz.CronExpression;


/**
 * @author Sharat Puranikmath
 */

public class DetailsForm extends JFrame {
  private JLabel jLabelTriggerID = new JLabel();
  private JTextField jTextFieldTriggerID = new JTextField();
  private JLabel jLabelTriggerName = new JLabel();
  private JTextField jTextFieldTriggerName = new JTextField();
  private JLabel jLabelGroupName = new JLabel();
  private JTextField jTextFieldGroupName = new JTextField();
  private JLabel jLabelCronExpr = new JLabel();
  private JTextField jTextFieldCronExpr = new JTextField();
  private JLabel jLabelOperation = new JLabel();
  private JTextField jTextFieldOperation = new JTextField();
  private JLabel jLabelEndpoint = new JLabel();
  private JTextField jTextFieldEndpoint = new JTextField();
  private JLabel jLabelInput = new JLabel();
  private TextArea jTextAreaInput = new TextArea();
  private JLabel jLabelDescription = new JLabel();
  private TextArea jTextAreaDescription = new TextArea();
  private JLabel jLabelEnabled = new JLabel();
  private JCheckBox jCheckBoxEnabled = new JCheckBox();
  String enabled = "N";
  int priority = 5;
  String[] Priority =
  { "1 - High", "2", "3", "4", "5 - Medium", "6", "7", "8", "9 - Low" };
  private JLabel jLabelPriority = new JLabel();
  private JComboBox jComboBoxPriority = new JComboBox(Priority);
  private JLabel jLabelCalender = new JLabel();
//  private JComboBox jComboBoxCalender = new JComboBox();
    JComboBox jComboBoxCalender = new JComboBox();

  String StatefulJob = "N";
  private JLabel jLabelStatefulJob = new JLabel();
  private JCheckBox jCheckBoxStatefulJob = new JCheckBox();
  String ErrorUnscheduleFlag = "N";
  private JLabel jLabelErrorUnscheduleFlag = new JLabel();
  private JCheckBox jCheckBoxErrorUnscheduleFlag = new JCheckBox();
  private JButton jButtonSave = new JButton("Add Job");
  private JLabel jLabelError = new JLabel();
  private JButton jButtonFetch = new JButton("Fetch");
  private JButton jButtonModify = new JButton("Modify Job");
  private JLabel jLabelSendEmail = new JLabel();
  private JCheckBox jCheckBoxSendEmail = new JCheckBox();
  private JLabel jLabelThresholdTime = new JLabel();
  private JTextField jTextFieldThresholdTime = new JTextField();
  String SendEmailFlag="N";
  private JLabel jLabelStatus = new JLabel();
  private JTextField jTextFieldStatus = new JTextField();
  private JLabel jLabelErrorTS = new JLabel();
  private JTextField jTextFieldErrorTS = new JTextField();
  private JLabel jLabelErrorDescription = new JLabel();
  private TextArea jTextFieldErrorDescription = new TextArea();


  public DetailsForm() {

  }

  public void jbInit( String [] holCal) throws Exception {
    this.setSize(new Dimension(610, 700));
    this.setTitle("Add Jobs");
    this.setResizable(false);
    this.setLayout(null);

    jLabelTriggerID.setText("Trigger ID *");
    jLabelTriggerID.setBounds(new Rectangle(5, 5, 150, 20));
    jTextFieldTriggerID.setBounds(new Rectangle(170, 5, 350, 20));
    jButtonFetch.setBounds(new Rectangle(525, 5, 70, 20));
    jButtonFetch.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jButtonFetch_actionPerformed(e);
        }
      });
    this.getContentPane().add(jLabelTriggerID, null);
    this.getContentPane().add(jTextFieldTriggerID, null);
    this.getContentPane().add(jButtonFetch, null);

    jLabelTriggerName.setText("Trigger Name *");
    jLabelTriggerName.setBounds(new Rectangle(5, 30, 150, 20));
    jTextFieldTriggerName.setBounds(new Rectangle(170, 30, 350, 20));
    this.getContentPane().add(jLabelTriggerName, null);
    this.getContentPane().add(jTextFieldTriggerName, null);

    jLabelGroupName.setText("Group Name *");
    jLabelGroupName.setBounds(new Rectangle(5, 60, 150, 20));
    jTextFieldGroupName.setBounds(new Rectangle(170, 60, 350, 20));
    this.getContentPane().add(jLabelGroupName, null);
    this.getContentPane().add(jTextFieldGroupName, null);

    jLabelCronExpr.setText("Cron Expression *");
    jLabelCronExpr.setBounds(new Rectangle(5, 90, 150, 20));
    jTextFieldCronExpr.setBounds(new Rectangle(170, 90, 350, 20));
    this.getContentPane().add(jLabelCronExpr, null);
    this.getContentPane().add(jTextFieldCronExpr, null);

    jLabelOperation.setText("Operation *");
    jLabelOperation.setBounds(new Rectangle(5, 120, 150, 20));
    jTextFieldOperation.setBounds(new Rectangle(170, 120, 350, 20));
    this.getContentPane().add(jLabelOperation, null);
    this.getContentPane().add(jTextFieldOperation, null);

    jLabelEndpoint.setText("Endpoint *");
    jLabelEndpoint.setBounds(new Rectangle(5, 150, 150, 20));
    jTextFieldEndpoint.setBounds(new Rectangle(170, 150, 350, 20));
    this.getContentPane().add(jLabelEndpoint, null);
    this.getContentPane().add(jTextFieldEndpoint, null);

    jLabelInput.setText("Input *");
    jLabelInput.setBounds(new Rectangle(5, 180, 150, 20));
    jTextAreaInput.setBounds(new Rectangle(170, 180, 350, 100));
    this.getContentPane().add(jLabelInput, null);
    this.getContentPane().add(jTextAreaInput, null);

    jLabelDescription.setText("Description");
    jLabelDescription.setBounds(new Rectangle(5, 290, 150, 20));
    jTextAreaDescription.setBounds(new Rectangle(170, 290, 350, 100));
    this.getContentPane().add(jLabelDescription, null);
    this.getContentPane().add(jTextAreaDescription, null);

    jLabelEnabled.setText("Enabled");
    jLabelEnabled.setBounds(new Rectangle(5, 400, 150, 20));
    jCheckBoxEnabled.setBounds(new Rectangle(170, 400, 350, 20));
    this.getContentPane().add(jLabelEnabled, null);
    this.getContentPane().add(jCheckBoxEnabled, null);
    jCheckBoxEnabled.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jCheckBoxEnabled_actionPerformed(e);
        }
      });

    jLabelPriority.setText("Priority");
    jLabelPriority.setBounds(new Rectangle(5, 430, 100, 20));
    jComboBoxPriority.setBounds(new Rectangle(170, 430, 100, 20));
    jComboBoxPriority.setSelectedIndex(4);
    this.getContentPane().add(jLabelPriority, null);
    this.getContentPane().add(jComboBoxPriority, null);
    jComboBoxPriority.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jComboBoxPriority_actionPerformed(e);
        }
      });

    jLabelCalender.setText("Calender");
    jLabelCalender.setBounds(new Rectangle(310, 430, 70, 20));
//    JComboBox jComboBoxCalender = new JComboBox(holCal);
    for (int i =0; i < holCal.length; i++){
      jComboBoxCalender.addItem(holCal[i]);
    }

    jComboBoxCalender.setBounds(new Rectangle(380, 430, 150, 20));
    jComboBoxCalender.setSelectedItem("No_Holidays");
    this.getContentPane().add(jLabelCalender, null);
    this.getContentPane().add(jComboBoxCalender, null);
    jComboBoxCalender.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jComboBoxCalender_actionPerformed(e);
        }
      });
    
    jLabelStatefulJob.setText("Read TT Status?");
    jLabelStatefulJob.setBounds(new Rectangle(5, 460, 150, 20));
    jCheckBoxStatefulJob.setBounds(new Rectangle(170, 460, 100, 20));
    this.getContentPane().add(jLabelStatefulJob, null);
    this.getContentPane().add(jCheckBoxStatefulJob, null);
    jCheckBoxStatefulJob.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jCheckBoxStatefulJob_actionPerformed(e);
        }
      });

    jLabelErrorUnscheduleFlag.setText("Unschedule on Error?");
    jLabelErrorUnscheduleFlag.setBounds(new Rectangle(310, 460, 200, 20));
    jCheckBoxErrorUnscheduleFlag.setBounds(new Rectangle(480, 460, 100, 20));
    this.getContentPane().add(jLabelErrorUnscheduleFlag, null);
    this.getContentPane().add(jCheckBoxErrorUnscheduleFlag, null);
    jCheckBoxErrorUnscheduleFlag.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jCheckBoxErrorUnscheduleFlag_actionPerformed(e);
        }
      });
    
    jLabelSendEmail.setText("Send Email on Error?");
    jLabelSendEmail.setBounds(new Rectangle(5, 490, 150, 20));
    jCheckBoxSendEmail.setBounds(new Rectangle(170, 490, 100, 20));
    this.getContentPane().add(jLabelSendEmail, null);
    this.getContentPane().add(jCheckBoxSendEmail, null);
    jCheckBoxSendEmail.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jCheckBoxSendEmail_actionPerformed(e);
        }
      });
    
    jLabelThresholdTime.setText("Threshold Time for Email (mins)*");
    jLabelThresholdTime.setBounds(new Rectangle(310, 490, 200, 20));
    jTextFieldThresholdTime.setBounds(new Rectangle(520, 490, 50, 20));
    jTextFieldThresholdTime.setToolTipText("Enter the threshold time after which error mail will sent. The time in minutes is to be specified in positive integers only.");
    jLabelThresholdTime.setToolTipText("Enter the threshold time after which error mail will sent. The time in minutes is to be specified in positive integers only.");
    this.getContentPane().add(jLabelThresholdTime, null);
    this.getContentPane().add(jTextFieldThresholdTime, null);
//    jCheckBoxErrorUnscheduleFlag.addActionListener(new ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//          jCheckBoxErrorUnscheduleFlag_actionPerformed(e);
//        }
//      });
    
    jLabelStatus.setText("Status");
    jLabelStatus.setBounds(new Rectangle(5, 520, 150, 20));
    jTextFieldStatus.setBounds(new Rectangle(170, 520, 100, 20));
    jTextFieldStatus.setEditable(false);
    this.getContentPane().add(jLabelStatus, null);
    this.getContentPane().add(jTextFieldStatus, null);

    jLabelErrorTS.setText("Last Error Time");
    jLabelErrorTS.setBounds(new Rectangle(310, 520, 200, 20));
    jTextFieldErrorTS.setBounds(new Rectangle(420, 520, 150, 20));
    jTextFieldErrorTS.setEditable(false);
    this.getContentPane().add(jLabelErrorTS, null);
    this.getContentPane().add(jTextFieldErrorTS, null);
    
    jLabelErrorDescription.setText("Error Description");
    jLabelErrorDescription.setBounds(new Rectangle(5, 550, 150, 20));
    jTextFieldErrorDescription.setBounds(new Rectangle(170, 550, 350, 20));
    jTextFieldErrorDescription.setEditable(false);
    this.getContentPane().add(jLabelErrorDescription, null);
    this.getContentPane().add(jTextFieldErrorDescription, null);

    jButtonSave.setBounds(new Rectangle(100, 600, 150, 20));
    this.getContentPane().add(jButtonSave, null);
    jButtonSave.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jButtonSave_actionPerformed(e);
        }
      });

    jLabelError.setBounds(new Rectangle(5, 620, 595, 80));
    jLabelError.setForeground(Color.RED);
    this.getContentPane().add(jLabelError, null);
    jButtonModify.setBounds(new Rectangle(300, 600, 150, 20));
    jButtonModify.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jButtonModify_actionPerformed(e);
        }
      });
    this.getContentPane().add(jButtonModify, null);

    this.setVisible(true);

  }

  private void jCheckBoxEnabled_actionPerformed(ActionEvent e) {
    if (jCheckBoxEnabled.isSelected()) {
      enabled = "Y";
    } else {
      enabled = "N";
    }
  }

  private void jCheckBoxStatefulJob_actionPerformed(ActionEvent e) {
    if (jCheckBoxStatefulJob.isSelected()) {
      StatefulJob = "Y";
    } else {
      StatefulJob = "N";
    }
  }
  
  private void jCheckBoxErrorUnscheduleFlag_actionPerformed(ActionEvent e) {
    if (jCheckBoxErrorUnscheduleFlag.isSelected()) {
      ErrorUnscheduleFlag = "Y";
    } else {
      ErrorUnscheduleFlag = "N";
    }
  }

  private void jComboBoxPriority_actionPerformed(ActionEvent e) {
    priority = jComboBoxPriority.getSelectedIndex() + 1;

  }
  
  private void jComboBoxCalender_actionPerformed(ActionEvent e) {
  
  }
  
  private void jCheckBoxSendEmail_actionPerformed(ActionEvent e) {
    if (jCheckBoxSendEmail.isSelected()) {
      SendEmailFlag = "Y";
    } else {
      SendEmailFlag = "N";
    }
  }

  private void jButtonSave_actionPerformed(ActionEvent e) {
    try {
      jLabelError.setText("");
      if (jTextFieldTriggerID.getText().equalsIgnoreCase("")|| jTextFieldTriggerName.getText().equalsIgnoreCase("") || jTextFieldGroupName.getText().equalsIgnoreCase("")|| jTextFieldCronExpr.getText().equalsIgnoreCase("") || jTextFieldOperation.getText().equalsIgnoreCase("") || jTextFieldEndpoint.getText().equalsIgnoreCase("") || jTextAreaInput.getText().equalsIgnoreCase("")|| jTextFieldThresholdTime.getText().equalsIgnoreCase(""))
        jLabelError.setText("Please enter all the values which are marked with * ");
       
       else {
        CronExpression cronValid = null;
        if ( !cronValid.isValidExpression(jTextFieldCronExpr.getText())){
          jLabelError.setText("You have specified invalid Cron Expression. Please correct the Cron expression and resubmit");
        }
        else{
          Properties prop = new Properties();
          URL url =
            getClass().getClassLoader().getResource("authentication.properties");
          InputStream in = url.openStream();
          prop.load(in);
  
          String providerURL = prop.getProperty("CustomDB_URL");
          String username = prop.getProperty("CustomDB_USERNAME");
          String password = prop.getProperty("CustomDB_PASSWORD");
          String tableName = prop.getProperty("TableName");
  
          Statement ts = null;
          Class.forName("oracle.jdbc.driver.OracleDriver");
          Connection conn =
            DriverManager.getConnection(providerURL, username, password);
          ts = conn.createStatement();
          ResultSet trigger_rs = null;
  
          if (jCheckBoxEnabled.isSelected()) {
            enabled = "Y";
          } else {
            enabled = "N";
          }
          if (jCheckBoxStatefulJob.isSelected()) {
            StatefulJob = "Y";
          } else {
            StatefulJob = "N";
          }
          if (jCheckBoxErrorUnscheduleFlag.isSelected()) {
            ErrorUnscheduleFlag = "Y";
          } else {
            ErrorUnscheduleFlag = "N";
          }
          if (jCheckBoxSendEmail.isSelected()) {
            SendEmailFlag = "Y";
          } else {
            SendEmailFlag = "N";
          }
          
          System.out.println("INSERT INTO " + tableName + " (TRIGGERID,NAME,GROUPNAME,CRONEXPR,OPERATION,ENDPOINT,INPUT,DESCRIPTION,ENABLED,PRIORITY,STATEFULJOB,ERROR_UNSCHEDULE_FLAG,CALENDER, SENDMAIL, THRESHOLD) VALUES ('" +
                              jTextFieldTriggerID.getText().replaceAll("\\s", "") + "','" +
                              jTextFieldTriggerName.getText().replaceAll("\\s", "") + "','" +
                              jTextFieldGroupName.getText().replaceAll("\\s", "") + "','" +
                              jTextFieldCronExpr.getText() + "','" +
                              jTextFieldOperation.getText() + "','" +
                              jTextFieldEndpoint.getText() + "','" +
                              jTextAreaInput.getText() + "','" +
                              jTextAreaDescription.getText() + "','" + enabled +
                              "','" + priority + "','" + StatefulJob + "','" + ErrorUnscheduleFlag + "','" 
                              + jComboBoxCalender.getSelectedItem() + "','" + SendEmailFlag + "'," + jTextFieldThresholdTime.getText() + ")");
          
          String ttime= jTextFieldThresholdTime.getText();
          if (ttime.equalsIgnoreCase(""))
            ttime="0";
  
          trigger_rs =
              ts.executeQuery("INSERT INTO " + tableName + " (TRIGGERID,NAME,GROUPNAME,CRONEXPR,OPERATION,ENDPOINT,INPUT,DESCRIPTION,ENABLED,PRIORITY,STATEFULJOB,ERROR_UNSCHEDULE_FLAG,CALENDER, SENDMAIL, THRESHOLD) VALUES ('" +
                              jTextFieldTriggerID.getText().replaceAll("\\s", "") + "','" +
                              jTextFieldTriggerName.getText().replaceAll("\\s", "") + "','" +
                              jTextFieldGroupName.getText().replaceAll("\\s", "") + "','" +
                              jTextFieldCronExpr.getText() + "','" +
                              jTextFieldOperation.getText() + "','" +
                              jTextFieldEndpoint.getText() + "','" +
                              jTextAreaInput.getText() + "','" +
                              jTextAreaDescription.getText() + "','" + enabled +
                              "','" + priority + "','" + StatefulJob + "','" + ErrorUnscheduleFlag + "','" 
                              + jComboBoxCalender.getSelectedItem() + "','" + SendEmailFlag + "'," + jTextFieldThresholdTime.getText() + ")");
  
          jLabelError.setText("<html><font color='green'> Job added successfully to the table " +
                              tableName + "</font></html>");
        }
      }


    } catch (Exception ex) {
      ex.printStackTrace();
      jLabelError.setText("<html>" + ex.toString() + "</html>");
    }
  }

  private void jButtonFetch_actionPerformed(ActionEvent e) {
    
    try {
      jLabelError.setText("");
      jTextFieldCronExpr.setText("");
      jTextFieldOperation.setText("");
      jTextFieldEndpoint.setText("");
      jTextAreaInput.setText("");
      jTextAreaDescription.setText("");
      jCheckBoxEnabled.setSelected(false);
      jComboBoxPriority.setSelectedIndex(4);
      jCheckBoxStatefulJob.setSelected(false);
      jComboBoxCalender.setSelectedItem("No_Holidays");
      jCheckBoxErrorUnscheduleFlag.setSelected(false);
      jCheckBoxSendEmail.setSelected(false);
      jTextFieldThresholdTime.setText("0");
      jTextFieldStatus.setText("");
      jTextFieldErrorTS.setText("");
      jTextFieldErrorDescription.setText("");
      if (jTextFieldTriggerID.getText().equalsIgnoreCase(""))
        jLabelError.setText("Please enter TriggerID");
else
      {
        Properties prop = new Properties();
        URL url =
          getClass().getClassLoader().getResource("authentication.properties");
        InputStream in = url.openStream();
        prop.load(in);

        String providerURL = prop.getProperty("CustomDB_URL");
        String username = prop.getProperty("CustomDB_USERNAME");
        String password = prop.getProperty("CustomDB_PASSWORD");
        String tableName = prop.getProperty("TableName");

        Statement ts = null;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn =
          DriverManager.getConnection(providerURL, username, password);
        ts = conn.createStatement();
        ResultSet trigger_rs = null;

//        System.out.println("select * from " + tableName +  " where triggerid = '" + jTextFieldTriggerID.getText() + "'");
        trigger_rs= ts.executeQuery("SELECT * FROM " + tableName +  " WHERE TRIGGERID = '" + jTextFieldTriggerID.getText() + "'");
        boolean flag=false;
        while(trigger_rs.next()){
          flag =true;
          jTextFieldTriggerName.setText(trigger_rs.getString("name"));
          jTextFieldGroupName.setText(trigger_rs.getString("groupname"));
          jTextFieldCronExpr.setText(trigger_rs.getString("cronexpr"));
          jTextFieldOperation.setText(trigger_rs.getString("operation"));
          jTextFieldEndpoint.setText(trigger_rs.getString("endpoint"));
          jTextAreaInput.setText(trigger_rs.getString("input"));
          jTextAreaDescription.setText(trigger_rs.getString("description"));
          if (trigger_rs.getString("enabled").equalsIgnoreCase("Y"))
            jCheckBoxEnabled.setSelected(true);
          else
            jCheckBoxEnabled.setSelected(false);
//          System.out.println(trigger_rs.getInt("priority"));
          jComboBoxPriority.setSelectedIndex(trigger_rs.getInt("priority") - 1 );
          jComboBoxCalender.setSelectedItem(trigger_rs.getString("CALENDER"));
          if (trigger_rs.getString("statefuljob").equalsIgnoreCase("Y"))
            jCheckBoxStatefulJob.setSelected(true);
          else
            jCheckBoxStatefulJob.setSelected(false);
          if (trigger_rs.getString("ERROR_UNSCHEDULE_FLAG").equalsIgnoreCase("Y"))
            jCheckBoxErrorUnscheduleFlag.setSelected(true);
          else
            jCheckBoxErrorUnscheduleFlag.setSelected(false);
          if (trigger_rs.getString("SENDMAIL").equalsIgnoreCase("Y"))
            jCheckBoxSendEmail.setSelected(true);
          else
            jCheckBoxSendEmail.setSelected(false);
          jTextFieldThresholdTime.setText(trigger_rs.getString("THRESHOLD"));
          jTextFieldStatus.setText(trigger_rs.getString("STATUS"));
          jTextFieldErrorTS.setText(trigger_rs.getString("ERROR_TS"));
          jTextFieldErrorDescription.setText(trigger_rs.getString("ERROR_DESCRIPTION"));
         
          
//          if(trigger_rs.getString("status").equalsIgnoreCase("RUNNING")||trigger_rs.getString("status").equalsIgnoreCase("SCHEDULED")){
//            System.out.println(trigger_rs.getString("status"));
//            jButtonSave.setEnabled(false);
//          }
      }

        if (flag==false){
        jLabelError.setText("<html><font color='red'>No entry found for " + jTextFieldTriggerID.getText() + "</font></html>");
          jTextFieldTriggerName.setText("");
          jTextFieldGroupName.setText("");
          jTextFieldCronExpr.setText("");
          jTextFieldOperation.setText("");
          jTextFieldEndpoint.setText("");
          jTextAreaInput.setText("");
          jTextAreaDescription.setText("");
          jCheckBoxEnabled.setSelected(false);
          jComboBoxPriority.setSelectedIndex(4);
          jCheckBoxStatefulJob.setSelected(false);
          jComboBoxCalender.setSelectedItem("No_Holidays");
          jCheckBoxErrorUnscheduleFlag.setSelected(false);
          jCheckBoxSendEmail.setSelected(false);
          jTextFieldThresholdTime.setText("");
          jTextFieldStatus.setText("");
          jTextFieldErrorTS.setText("");
          jTextFieldErrorDescription.setText("");
          
        }
      }

    } catch (Exception ex) {
      ex.printStackTrace();
      jLabelError.setText("<html>" + ex.toString() + "</html>");
    }
    
  }

  private void jButtonModify_actionPerformed(ActionEvent e) {
    try {
      jLabelError.setText("");
      if (jTextFieldTriggerID.getText().equalsIgnoreCase("")|| jTextFieldTriggerName.getText().equalsIgnoreCase("") || jTextFieldGroupName.getText().equalsIgnoreCase("")|| jTextFieldCronExpr.getText().equalsIgnoreCase("") || jTextFieldOperation.getText().equalsIgnoreCase("") || jTextFieldEndpoint.getText().equalsIgnoreCase("") || jTextAreaInput.getText().equalsIgnoreCase(""))
        jLabelError.setText("Please enter all the values which are marked with * ");
       
       else {
        CronExpression cronValid = null;
        if ( !cronValid.isValidExpression(jTextFieldCronExpr.getText())){
          jLabelError.setText("You have specified invalid Cron Expression. Please correct the Cron expression and resubmit");
        }
        else{
          Properties prop = new Properties();
          URL url =
            getClass().getClassLoader().getResource("authentication.properties");
          InputStream in = url.openStream();
          prop.load(in);
  
          String providerURL = prop.getProperty("CustomDB_URL");
          String username = prop.getProperty("CustomDB_USERNAME");
          String password = prop.getProperty("CustomDB_PASSWORD");
          String tableName = prop.getProperty("TableName");
  
          Statement ts = null;
          Class.forName("oracle.jdbc.driver.OracleDriver");
          Connection conn =
            DriverManager.getConnection(providerURL, username, password);
          ts = conn.createStatement();
          ResultSet trigger_rs = null;
          
          if (jCheckBoxEnabled.isSelected()) {
            enabled = "Y";
          } else {
            enabled = "N";
          }
          if (jCheckBoxStatefulJob.isSelected()) {
            StatefulJob = "Y";
          } else {
            StatefulJob = "N";
          }
          
          if (jCheckBoxErrorUnscheduleFlag.isSelected()) {
            ErrorUnscheduleFlag = "Y";
          } else {
            ErrorUnscheduleFlag = "N";
          }
            
            if (jCheckBoxSendEmail.isSelected()) {
              SendEmailFlag = "Y";
            } else {
              SendEmailFlag = "N";
            }
          
          String ttime= jTextFieldThresholdTime.getText();
          if (ttime.equalsIgnoreCase(""))
            ttime="0";
          
//          System.out.println("UPDATE " + tableName + " SET NAME = '" + jTextFieldTriggerName.getText().replaceAll("\\s", "") + 
//                              "', GROUPNAME = '" + jTextFieldGroupName.getText().replaceAll("\\s", "") + 
//                              "', CRONEXPR = '" + jTextFieldCronExpr.getText() + 
//                              "', OPERATION = '" + jTextFieldOperation.getText() + 
//                              "', ENDPOINT = '" + jTextFieldEndpoint.getText() +
//                              "', INPUT = '" + jTextAreaInput.getText() +
//                              "', DESCRIPTION = '" + jTextAreaDescription.getText() + 
//                              "', ENABLED = '" + enabled +
//                              "', PRIORITY = '" + priority + 
//                              "', STATEFULJOB = '" + StatefulJob + 
//                              "', ERROR_UNSCHEDULE_FLAG = '" + ErrorUnscheduleFlag + 
//                              "', CALENDER = '" + jComboBoxCalender.getSelectedItem() +
//                              "', SENDMAIL = '" + SendEmailFlag +
//                              "', THRESHOLD = '" + ttime +
//                              "' WHERE TRIGGERID = '" + jTextFieldTriggerID.getText().replaceAll("\\s", "") + "'");
  
          trigger_rs =
              ts.executeQuery("UPDATE " + tableName + " SET NAME = '" + jTextFieldTriggerName.getText().replaceAll("\\s", "") + 
                              "', GROUPNAME = '" + jTextFieldGroupName.getText().replaceAll("\\s", "") + 
                              "', CRONEXPR = '" + jTextFieldCronExpr.getText() + 
                              "', OPERATION = '" + jTextFieldOperation.getText() + 
                              "', ENDPOINT = '" + jTextFieldEndpoint.getText() +
                              "', INPUT = '" + jTextAreaInput.getText() +
                              "', DESCRIPTION = '" + jTextAreaDescription.getText() + 
                              "', ENABLED = '" + enabled +
                              "', PRIORITY = '" + priority + 
                              "', STATEFULJOB = '" + StatefulJob + 
                              "', ERROR_UNSCHEDULE_FLAG = '" + ErrorUnscheduleFlag + 
                              "', CALENDER = '" + jComboBoxCalender.getSelectedItem() +
                              "', SENDMAIL = '" + SendEmailFlag +
                              "', THRESHOLD = '" + ttime +
                              "' WHERE TRIGGERID = '" + jTextFieldTriggerID.getText().replaceAll("\\s", "") + "'");
  
          jLabelError.setText("<html><font color='green'> Job modified successfully in the table " +
                              tableName + "</font></html>");
        }
      }


    } catch (Exception ex) {
      ex.printStackTrace();
      jLabelError.setText("<html>" + ex.toString() + "</html>");
    }
    
  }
}

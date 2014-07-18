package com.sfr.engage.core;

import com.sfr.util.AccessDataControl;

import com.sfr.util.constants.Constants;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import oracle.jdbc.OracleTypes;

import java.util.HashMap;

import java.util.Iterator;

import java.util.Map;

import javax.naming.InitialContext;

import javax.sql.DataSource;

public class ReportBundle {
    
    public ReportBundle() {
        super();
    }
    
    public String getContentsForReport(String pageBundle,String countryCode,String colList)throws Exception {       
        String key = "";
        String value = "";
        String reportColumns ="";
        ResultSet reportRS;
        HashMap<String, String> mapDB = new HashMap<String, String>();        
            try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("jdbc/engagecustom");
            Connection conn = ds.getConnection();                    
            System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".generateReport : " +"retrieving Resource Bundle Details");
             String transactionsql = "{call PRT_GET_REPORT_RESOURCEBUNDLE(?,?)}";
             CallableStatement cst=conn.prepareCall(transactionsql);  
             cst.setString(1, pageBundle);
             cst.registerOutParameter(2, OracleTypes.CURSOR);  
     
            // execute PRT_GET_RESOURCEBUNDLE store procedure
                 cst.executeQuery();
    
            // get cursor and cast it to ResultSet
             reportRS = (ResultSet) cst.getObject(2);                
            
           
                while (reportRS.next()) {
                    if ("NO".equalsIgnoreCase(countryCode)) {
                        key = reportRS.getString("NO_NO");
                        value = reportRS.getString("EN");
                    } else {
                        key = reportRS.getString(countryCode);
                        value = reportRS.getString("EN");
                    }
                    System.out.println("Value ="+value);
                    mapDB.put(key, value); 
                }

            String[] headerValues =
                colList.split(Constants.ENGAGE_REPORT_DELIMITER);
            for (int col = 0; col < headerValues.length; col++) {
                System.out.println("String Value Form=" +
                                   headerValues[col].toString());


                for (Map.Entry<String, String> entryDB : mapDB.entrySet()) {
                    String keyDB = entryDB.getKey();
                    String valueDB = entryDB.getValue();
                    if (headerValues[col].toString().trim().equalsIgnoreCase(keyDB.trim())) {
                        reportColumns = reportColumns + valueDB + "|";
                    }
                }
            }
           if(reportColumns != null && reportColumns.equals(""))
           {
            reportColumns=reportColumns.substring(0, reportColumns.length()-1);
            
           }
                
            conn.close();
        } catch (Exception e) {            
            e.printStackTrace();
        }   
        return reportColumns;
    }  
    
}

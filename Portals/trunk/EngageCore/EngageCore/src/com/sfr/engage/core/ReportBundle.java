package com.sfr.engage.core;

import com.sfr.util.AccessDataControl;

import com.sfr.util.constants.Constants;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import oracle.jdbc.OracleTypes;

import java.util.HashMap;

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
        HashMap<String, String> map = parseHashMap(colList);
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
            while(reportRS.next())
            {
               if("NO".equalsIgnoreCase(countryCode))
               {
                   key=reportRS.getString("NO_NO");
                   value = reportRS.getString("EN");                  
               }else{
                   key=reportRS.getString(countryCode);
                   value = reportRS.getString("EN");
               }                
                if (map.containsKey(key)) {
                    reportColumns=reportColumns+value+"|";
                }
                                
            }            
            reportColumns=reportColumns.substring(0, reportColumns.length()-1);
                
            conn.close();
        } catch (Exception e) {            
            e.printStackTrace();
        }   
        return reportColumns;
    }
    
    
    private HashMap<String, String> parseHashMap(String params) throws Exception {
        try {
            String[] headerValues = params.split(Constants.ENGAGE_REPORT_DELIMITER);
            HashMap<String, String> map = new HashMap<String, String>();
            for (int col = 0; col < headerValues.length; col++) {  
                map.put(headerValues[col].toString(), "");
            }            
            return map;
        } catch (Exception e) {           
            throw e;
        }
    }    
}

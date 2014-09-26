package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class TransactionData {
    public TransactionData() {
        super();
    }
    private String driver,leftMin,leftMax,rightMin,rightMax,countryCode,cardID;
    private ArrayList transactionArray;
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    public ArrayList<TransactionDataOutput> GenerateTransactionData() throws SQLException 
    {
    //List<TransactionReportOutput> transactionArray;
    // transactionArray = new ArrayList<TransactionReportOutput>();
    this.transactionArray = new ArrayList<TransactionDataOutput>();
    this.conn = null;
    this.pstmt = null;
    try {
        
        this.driver = "oracle.jdbc.driver.OracleDriver";
        Class.forName(this.driver);
        String url = "jdbc:oracle:thin:@10.24.237.66:1521:wcp001t";
        String username = "wcpcustom";
        String password = "wcpcustom";
        this.conn = DriverManager.getConnection(url, username, password);
       
        //String CUSTOMERNO = "1053458";
        String query ="SELECT PALS_COUNTRY_CODE, UREF_TRANSACTION_ID, PURCHASE_COUNTRY_CODE, ORDER_ID, PRELIM_ID, CARD_1_ID, CARD_2_ID, CARD_ID_2_INFO, ODOMETER_PORTAL, ODOMETER, TRANSACTION_DT, TRANSACTION_TIME, STATION_NAME, ICC_INVOICE_NUMBER, INVOICE_NUMBER_NON_COLLECTIVE, INVOICING_DATE, PURCHASE_CURRENCY, EXCHANGE_RATE, ICC_YN, TRANSACTION_TYPE, PRELIM_STATUS_CODE, INVOICE_NUMBER_COLLECTIVE, KSID, PARTNER_ID, ACCOUNT_ID, CARDGROUP_MAIN_TYPE, CARDGROUP_SUB_TYPE, CARDGROUP_SEQ, MODIFIED_BY, PREVIOUS_ODOMETER, PALS_ORDER_MAIN_TYPE, PALS_ORDER_SUB_TYPE, ORDER_BACKWARD_REF, ORDER_FORWARD_REF, ORDER_VALID_CODE, ORDER_CREATE_CODE, SITE_NUMBER, INVOICED_CARD_ID, TERMINAL_ID, TERMINAL_SEQ, INVOICE_FLAG, WM_TRANS_REF_NUMBER, PALS_MODIFIED_DATE, PALS_MODIFIED_BY, PORTAL_MODIFIED_DATE, SOA_MODIFIED_DATE, SOA_MODIFIED_BY FROM PRT_CARD_TRANSACTION_HEADER WHERE(((TRANSACTION_TIME BETWEEN '" + this.leftMin + "' AND '" + this.leftMax + "' OR (TRANSACTION_TIME BETWEEN '" + this.rightMin + "' AND '" + this.rightMax + "')) AND (CARD_1_ID in('" + this.cardID + "') AND PALS_COUNTRY_CODE = '" + this.countryCode + "')))";
        System.out.println("Query :" + query );
            
        this.pstmt = this.conn.prepareStatement(query); // create a statement
        this.rs = this.pstmt.executeQuery(query);
        while(this.rs.next()) {
                TransactionDataOutput transaction = new TransactionDataOutput();

            transaction.setPALS_COUNTRY_CODE(this.rs.getString("PALS_COUNTRY_CODE"));
            transaction.setUREF_TRANSACTION_ID(this.rs.getString("UREF_TRANSACTION_ID"));
            transaction.setPURCHASE_COUNTRY_CODE(this.rs.getString("PURCHASE_COUNTRY_CODE"));
            transaction.setORDER_ID(this.rs.getString("ORDER_ID"));
            transaction.setPRELIM_ID(this.rs.getString("PRELIM_ID"));
            transaction.setCARD_1_ID(this.rs.getString("CARD_1_ID"));
            transaction.setCARD_2_ID(this.rs.getString("CARD_2_ID"));
            transaction.setCARD_ID_2_INFO(this.rs.getString("CARD_ID_2_INFO"));
            transaction.setODOMETER_PORTAL(this.rs.getString("ODOMETER_PORTAL"));
            transaction.setODOMETER(this.rs.getString("ODOMETER"));
            transaction.setTRANSACTION_DT(this.rs.getString("TRANSACTION_DT"));
            transaction.setTRANSACTION_TIME(this.rs.getString("TRANSACTION_TIME"));
            transaction.setSTATION_NAME(this.rs.getString("STATION_NAME"));
            transaction.setICC_INVOICE_NUMBER(this.rs.getString("ICC_INVOICE_NUMBER"));
            transaction.setINVOICE_NUMBER_NON_COLLECTIVE(this.rs.getString("INVOICE_NUMBER_NON_COLLECTIVE"));
            transaction.setINVOICING_DATE(this.rs.getString("INVOICING_DATE"));
            transaction.setPURCHASE_CURRENCY(this.rs.getString("PURCHASE_CURRENCY"));
            transaction.setEXCHANGE_RATE(this.rs.getString("EXCHANGE_RATE"));
            transaction.setICC_YN(this.rs.getString("ICC_YN"));
            transaction.setTRANSACTION_TYPE(this.rs.getString("TRANSACTION_TYPE"));
            transaction.setPRELIM_STATUS_CODE(this.rs.getString("PRELIM_STATUS_CODE"));
            transaction.setINVOICE_NUMBER_COLLECTIVE(this.rs.getString("INVOICE_NUMBER_COLLECTIVE"));
            transaction.setKSID(this.rs.getString("KSID"));
            transaction.setPARTNER_ID(this.rs.getString("PARTNER_ID"));
            transaction.setACCOUNT_ID(this.rs.getString("ACCOUNT_ID"));
            transaction.setCARDGROUP_MAIN_TYPE(this.rs.getString("CARDGROUP_MAIN_TYPE"));
            transaction.setCARDGROUP_SUB_TYPE(this.rs.getString("CARDGROUP_SUB_TYPE"));
            transaction.setCARDGROUP_SEQ(this.rs.getString("CARDGROUP_SEQ"));
            transaction.setMODIFIED_BY(this.rs.getString("MODIFIED_BY"));
            transaction.setPREVIOUS_ODOMETER(this.rs.getString("PREVIOUS_ODOMETER"));
            transaction.setPALS_ORDER_MAIN_TYPE(this.rs.getString("PALS_ORDER_MAIN_TYPE"));
            transaction.setPALS_ORDER_SUB_TYPE(this.rs.getString("PALS_ORDER_SUB_TYPE"));
            transaction.setORDER_BACKWARD_REF(this.rs.getString("ORDER_BACKWARD_REF"));
            transaction.setORDER_FORWARD_REF(this.rs.getString("ORDER_FORWARD_REF"));
            transaction.setORDER_VALID_CODE(this.rs.getString("ORDER_VALID_CODE"));
            transaction.setORDER_CREATE_CODE(this.rs.getString("ORDER_CREATE_CODE"));
            transaction.setSITE_NUMBER(this.rs.getString("SITE_NUMBER"));
            transaction.setINVOICED_CARD_ID(this.rs.getString("INVOICED_CARD_ID"));
            transaction.setTERMINAL_ID(this.rs.getString("TERMINAL_ID"));
            transaction.setTERMINAL_SEQ(this.rs.getString("TERMINAL_SEQ"));
            transaction.setINVOICE_FLAG(this.rs.getString("INVOICE_FLAG"));
            transaction.setWM_TRANS_REF_NUMBER(this.rs.getString("WM_TRANS_REF_NUMBER"));
            transaction.setPALS_MODIFIED_DATE(this.rs.getString("PALS_MODIFIED_DATE"));
            transaction.setPALS_MODIFIED_BY(this.rs.getString("PALS_MODIFIED_BY"));
            transaction.setPORTAL_MODIFIED_DATE(this.rs.getString("PORTAL_MODIFIED_DATE"));
            transaction.setSOA_MODIFIED_DATE(this.rs.getString("SOA_MODIFIED_DATE"));
            transaction.setSOA_MODIFIED_BY(this.rs.getString("SOA_MODIFIED_BY"));

                this.transactionArray.add(transaction);
        }
        rs.close();
    }catch(SQLException e) 
    {
        e.printStackTrace();
    }
    catch(Exception en) 
    {
        en.printStackTrace();
    } 
    finally 
    {
        if(this.pstmt != null)
            this.pstmt.close();
        if(this.conn != null)
            this.conn.close();
    }
    return(this.transactionArray);
    }

    public void setLeftMin(String leftMin) {
        this.leftMin = leftMin;
    }

    public String getLeftMin() {
        return leftMin;
    }

    public void setLeftMax(String leftMax) {
        this.leftMax = leftMax;
    }

    public String getLeftMax() {
        return leftMax;
    }

    public void setRightMin(String rightMin) {
        this.rightMin = rightMin;
    }

    public String getRightMin() {
        return rightMin;
    }

    public void setRightMax(String rightMax) {
        this.rightMax = rightMax;
    }

    public String getRightMax() {
        return rightMax;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getCardID() {
        return cardID;
    }
}

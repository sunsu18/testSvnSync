package e012portaltransactionreport;


import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import java.util.ArrayList;
import java.util.Currency;


public class TransactionReport {
    public TransactionReport() {
        super();
    }
    

    public static ArrayList<TransactionReportOutput> GenerateTransactionReport(TransactionReportInput transactionInput) throws SQLException 
{
    //TransactionReportInput input = new TransactionReportInput()
    ArrayList transactionArray = new ArrayList<TransactionReportOutput>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
        String Country=transactionInput.getCountry();
        String PARTNER_ID=transactionInput.getPartner();
        String AccountID = transactionInput.getAccount();
        String Type=transactionInput.getType();
        String ReportType=transactionInput.getReportType();
        String TransactionBasis =transactionInput.getTransactionBasis();
        String TransactionBasisValues =transactionInput.getTransactionBasisValues();
        String TransactionDateFrom =transactionInput.getTransactionDateFrom();
        String TransactionDateTo =transactionInput.getTransactionDateTo();
        
        String driver = "oracle.jdbc.driver.OracleDriver";
        Class.forName(driver);
        String url = "jdbc:oracle:thin:@10.24.237.66:1521:wcp001t";
        String username = "wcpcustom";
        String password = "wcpcustom";
        conn = DriverManager.getConnection(url, username, password);
       
        
        String AccountIDClause="";
        String reportTypeClause="";
        String purchaseCountryCode="";
        String CardClause=" ((instr('"+TransactionBasisValues+"',ksid)<>0 or instr('"+TransactionBasisValues+"',Card_2_id)<>0) AND ((card_id_2_info ='V2' OR card_id_2_info ='D' OR card_id_2_info ='V') OR CARD_ID_2_INFO IS NULL)) ";
        
        if(AccountID.length()>2749)
        {
            int length;
            for(int i=0;AccountID!="";i++) 
            {
                if(i!=0) {
                    AccountIDClause=AccountIDClause + " or ";
                }
                if(AccountID.length()>2749) {
                    length=2749;
                }
                else
                {
                    length=AccountID.length() - 1;
                }
                AccountIDClause= AccountIDClause + " INSTR('"+ AccountID.substring(0, length)+ "' ,PRT_CARD_TRANSACTION_HEADER.ACCOUNT_ID)<>0 ";
                if(AccountID.substring(length).length() <= 2) {
                    AccountID = "";
                }
                else
                AccountID = AccountID.substring(2751);
            }
        }
          else
        AccountIDClause=" INSTR( '"+ AccountID+ "' ,PRT_CARD_TRANSACTION_HEADER.ACCOUNT_ID)<>0 ";
        
        
        if(ReportType=="International") 
        {
            reportTypeClause = " AND PURCHASE_COUNTRY_CODE NOT IN('"+purchaseCountryCode+"') ";
        }
        
        
        //String CUSTOMERNO = "1053458";
        String query ="SELECT PRT_CARD_TRANSACTION_HEADER.UREF_TRANSACTION_ID,PRT_CARD_TRANSACTION_HEADER.PARTNER_ID,PRT_CARD_TRANSACTION_HEADER.PALS_COUNTRY_CODE,PRT_CARD_TRANSACTION_HEADER.TRANSACTION_TYPE,PRT_CARD_TRANSACTION_HEADER.PURCHASE_CURRENCY,PRT_CARD_TRANSACTION_HEADER.KSID,PRT_CARD_TRANSACTION_HEADER.PURCHASE_COUNTRY_CODE,PRT_CARD_TRANSACTION_HEADER.CARD_1_ID,PRT_CARD_TRANSACTION_HEADER.ODOMETER_PORTAL,PRT_CARD_TRANSACTION_HEADER.ODOMETER,\n" + 
        "PRT_CARD_TRANSACTION_HEADER.TRANSACTION_DT,PRT_CARD_TRANSACTION_HEADER.TRANSACTION_TIME,PRT_CARD_TRANSACTION_HEADER.STATION_NAME,PRT_CARD_TRANSACTION_HEADER.INVOICE_NUMBER_NON_COLLECTIVE,\n" + 
        "PRT_CARD_TRANSACTION_HEADER.MODIFIED_BY,PRT_CARD_TRANSACTION_HEADER.PORTAL_MODIFIED_DATE,PRT_CARD_TRANSACTION_HEADER.INVOICE_NUMBER_COLLECTIVE,PRT_CARD_TRANSACTION_HEADER.ACCOUNT_ID,PRT_CARD_TRANSACTION_HEADER.CARDGROUP_MAIN_TYPE,PRT_CARD_TRANSACTION_HEADER.CARDGROUP_SUB_TYPE,\n" + 
        "PRT_CARD_TRANSACTION_HEADER.CARDGROUP_SEQ,PRT_CARD_TRANSACTION_HEADER.PREVIOUS_ODOMETER,PRT_CARD_TRANSACTION_DETAIL.PRODUCT_NAME,PRT_CARD_TRANSACTION_DETAIL.QUANTITY,PRT_CARD_TRANSACTION_DETAIL.CURRENCY_UNIT_PRICE,\n" + 
        "PRT_CARD_TRANSACTION_DETAIL.INVOICED_VAT,PRT_CARD_TRANSACTION_DETAIL.INVOICED_NET_AMOUNT,PRT_CARD_TRANSACTION_DETAIL.INVOICE_DISCOUNT_AMOUNT,PRT_CARD_TRANSACTION_DETAIL.INVOICED_UNIT_PRICE,PRT_CARD_TRANSACTION_DETAIL.INVOICED_GROSS_AMOUNT,PRT_CARD_TRANSACTION_DETAIL.INVOICED_NET_AMOUNT_REBATED,PRT_CARD_TRANSACTION_DETAIL.INVOIVED_VAT_REBATED,PRT_CARD_TRANSACTION_DETAIL.INVOICED_UNIT_PRICE_REBATED,PRT_CARD_TRANSACTION_DETAIL.CURRENCY_GROSS_AMOUNT_REBATED,PRT_CARD_TRANSACTION_DETAIL.CURRENCY_UNIT_PRICE_REBATED,PRT_CARD_TRANSACTION_DETAIL.CURRENCY_GROSS_AMOUNT,PRT_CARD_TRANSACTION_DETAIL.UNIT_OF_MEASURE,PRT_CARD_TRANSACTION_DETAIL.CURRENCY_VAT,PRT_CARD_TRANSACTION_DETAIL.CURRENCY_DISCOUNT_AMOUNT,\n" + 
        "PRT_CARD_TRANSACTION_DETAIL.CURRENCY_NET_AMOUNT,PRT_CARD_TRANSACTION_DETAIL.INVOICED_GROSS_AMOUNT_REBATED,PRT_DRIVER_INFORMATION.DRIVER_NUMBER,PRT_DRIVER_INFORMATION.DRIVER_NAME,\n" + 
        "PRT_TRUCK_INFORMATION.VEHICLE_NUMBER,PRT_TRUCK_INFORMATION.INTERNAL_NAME\n" + 
        "FROM PRT_CARD_TRANSACTION_HEADER inner join PRT_CARD_TRANSACTION_DETAIL ON (PRT_CARD_TRANSACTION_HEADER.UREF_TRANSACTION_ID=PRT_CARD_TRANSACTION_DETAIL.UREF_TRANSACTION_ID\n" + 
        "and PRT_CARD_TRANSACTION_HEADER.pals_country_code=PRT_CARD_TRANSACTION_DETAIL.pals_country_code)\n" + 
        "left join PRT_DRIVER_INFORMATION ON PRT_CARD_TRANSACTION_HEADER.ACCOUNT_ID = PRT_DRIVER_INFORMATION.ACCOUNT_NUMBER \n" + 
        "AND ((PRT_CARD_TRANSACTION_HEADER.KSID = PRT_DRIVER_INFORMATION.CARD_NUMBER) OR (PRT_CARD_TRANSACTION_HEADER.CARD_2_ID = PRT_DRIVER_INFORMATION.CARD_NUMBER OR PRT_CARD_TRANSACTION_HEADER.CARD_2_ID = PRT_DRIVER_INFORMATION.REFERENCE_NUMBER)) \n" + 
        "AND PRT_CARD_TRANSACTION_HEADER.PALS_COUNTRY_CODE = PRT_DRIVER_INFORMATION.COUNTRY_CODE \n" + 
        "left join PRT_TRUCK_INFORMATION ON PRT_CARD_TRANSACTION_HEADER.ACCOUNT_ID = PRT_TRUCK_INFORMATION.ACCOUNT_NUMBER \n" + 
        "AND ((PRT_CARD_TRANSACTION_HEADER.KSID = PRT_TRUCK_INFORMATION.CARD_NUMBER) OR (PRT_CARD_TRANSACTION_HEADER.CARD_2_ID = PRT_TRUCK_INFORMATION.CARD_NUMBER OR PRT_CARD_TRANSACTION_HEADER.CARD_2_ID = PRT_TRUCK_INFORMATION.REFERENCE_NUMBER))\n" + 
        "AND PRT_CARD_TRANSACTION_HEADER.PALS_COUNTRY_CODE = PRT_TRUCK_INFORMATION.COUNTRY_CODE" +
        " WHERE  PRT_CARD_TRANSACTION_HEADER.PALS_COUNTRY_CODE = '" + Country + "'" +
        "AND PRT_CARD_TRANSACTION_DETAIL.PRICE_ELEMENT_CODE IS NULL "+
        "AND PRT_CARD_TRANSACTION_HEADER.PARTNER_ID ='" + PARTNER_ID +"' "+
        "AND INSTR('PRI',TRANSACTION_TYPE)<>0  " + 
        "AND TRANSACTION_DT >= '" + TransactionDateFrom+"' "+
        "AND TRANSACTION_DT <= '" + TransactionDateTo+"' "+
        "AND (" + AccountIDClause + ")" +
        "AND " + CardClause +
        reportTypeClause +
        "ORDER BY PRT_CARD_TRANSACTION_HEADER.TRANSACTION_DT DESC";
            
        pstmt = conn.prepareStatement(query); // create a statement
        //pstmt.setInt(1, MESSAGENO); // set input parameter 1
        //pstmt.setDate(2, (java.sql.Date)TIMESTAMP);// set input parameter 2
        //pstmt.setString(4, CustomerNo); // set input parameter 4
        ResultSet rs = pstmt.executeQuery(query);
        while (rs.next()) {
                TransactionReportOutput transaction = new TransactionReportOutput();
                transaction.setTRANSACTION_ID(rs.getString("UREF_TRANSACTION_ID"));
                transaction.setPARTNER_ID(rs.getString("PARTNER_ID"));
                transaction.setPALS_COUNTRY_CODE(rs.getString("PALS_COUNTRY_CODE"));
                transaction.setTRANSACTION_TYPE(rs.getString("TRANSACTION_TYPE"));
                transaction.setPURCHASE_CURRENCY(rs.getString("PURCHASE_CURRENCY"));
                transaction.setKSID(rs.getString("KSID"));
                transaction.setPURCHASE_COUNTRY_CODE(rs.getString("PURCHASE_COUNTRY_CODE"));
                transaction.setCARD_1_ID(rs.getString("CARD_1_ID"));
                transaction.setODOMETER_PORTAL(rs.getString("ODOMETER_PORTAL"));
                transaction.setODOMETER(rs.getString("ODOMETER"));
                transaction.setTRANSACTION_DT(rs.getString("TRANSACTION_DT"));
                transaction.setTRANSACTION_TIME(rs.getString("TRANSACTION_TIME"));
                transaction.setSTATION_NAME(rs.getString("STATION_NAME"));
                transaction.setINVOICE_NUMBER_NON_COLLECTIVE(rs.getString("INVOICE_NUMBER_NON_COLLECTIVE"));
                transaction.setMODIFIED_BY(rs.getString("MODIFIED_BY"));
                transaction.setPORTAL_MODIFIED_DATE(rs.getString("PORTAL_MODIFIED_DATE"));
                transaction.setINVOICE_NUMBER_COLLECTIVE(rs.getString("INVOICE_NUMBER_COLLECTIVE"));
                transaction.setACCOUNT_ID(rs.getString("ACCOUNT_ID"));
                transaction.setCARDGROUP_MAIN_TYPE(rs.getString("CARDGROUP_MAIN_TYPE"));
                transaction.setCARDGROUP_SUB_TYPE(rs.getString("CARDGROUP_SUB_TYPE"));
                transaction.setCARDGROUP_SEQ(rs.getString("CARDGROUP_SEQ"));
                transaction.setPREVIOUS_ODOMETER(rs.getString("PREVIOUS_ODOMETER"));
                transaction.setPRODUCT_NAME(rs.getString("PRODUCT_NAME"));
                transaction.setQUANTITY(rs.getString("QUANTITY"));
                transaction.setCURRENCY_UNIT_PRICE(rs.getString("CURRENCY_UNIT_PRICE"));
                transaction.setINVOICED_VAT(rs.getString("INVOICED_VAT"));
                transaction.setINVOICED_NET_AMOUNT(rs.getString("INVOICED_NET_AMOUNT"));
                transaction.setINVOICE_DISCOUNT_AMOUNT(rs.getString("INVOICE_DISCOUNT_AMOUNT"));
                transaction.setINVOICED_UNIT_PRICE(rs.getString("INVOICED_UNIT_PRICE"));
                transaction.setINVOICED_GROSS_AMOUNT(rs.getString("INVOICED_GROSS_AMOUNT"));
                transaction.setINVOICED_NET_AMOUNT_REBATED(rs.getString("INVOICED_NET_AMOUNT_REBATED"));
                transaction.setINVOIVED_VAT_REBATED(rs.getString("INVOIVED_VAT_REBATED"));
                transaction.setINVOICED_UNIT_PRICE_REBATED(rs.getString("INVOICED_UNIT_PRICE_REBATED"));
                transaction.setCURRENCY_GROSS_AMOUNT_REBATED(rs.getString("CURRENCY_GROSS_AMOUNT_REBATED"));
                transaction.setCURRENCY_UNIT_PRICE_REBATED(rs.getString("CURRENCY_UNIT_PRICE_REBATED"));
                transaction.setCURRENCY_GROSS_AMOUNT(rs.getString("CURRENCY_GROSS_AMOUNT"));
                transaction.setUNIT_OF_MEASURE(rs.getString("UNIT_OF_MEASURE"));
                transaction.setCURRENCY_VAT(rs.getString("CURRENCY_VAT"));
                transaction.setCURRENCY_DISCOUNT_AMOUNT(rs.getString("CURRENCY_DISCOUNT_AMOUNT"));
                transaction.setCURRENCY_NET_AMOUNT(rs.getString("CURRENCY_NET_AMOUNT"));
                transaction.setINVOICED_GROSS_AMOUNT_REBATED(rs.getString("INVOICED_GROSS_AMOUNT_REBATED"));
                transaction.setDRIVER_NUMBER(rs.getString("DRIVER_NUMBER"));
                transaction.setDRIVER_NAME(rs.getString("DRIVER_NAME"));
                transaction.setVEHICLE_NUMBER(rs.getString("VEHICLE_NUMBER"));
                transaction.setINTERNAL_NAME(rs.getString("INTERNAL_NAME"));
                transactionArray.add(transaction);
        }
        rs.close();
    } 
    catch (SQLException e) 
    {
        e.printStackTrace();
    } 
    catch (Exception e) 
    {
        e.printStackTrace();
    } 
    finally 
    {
        if(pstmt != null)
            pstmt.close();
        if(conn != null)
            conn.close();
    }
    return transactionArray;
}

    public static void main(String[] args) throws SQLException 
    {
        String transactionVal="";
        TransactionReportInput input= new TransactionReportInput();
        input.setCountry("DK");
        input.setAccount("0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241;0004930889;0004930905;0004930913;0004930921;0004930939;0004930947;0004930954;0004930962;0004930970;0004930988;0004930996;0004955241");
        input.setPartner("04942983");
        input.setType("");
        input.setReportType("");
        input.setTransactionBasis("");
        input.setTransactionBasisValues("0014860050,0014860068,0014860076,0014860084,0014860092,0014860100,0014860118,0014860126,0014860134,0014860142,0014860159,0014860167,0014860175,0014860183,0014860191,0014860209,0014860217,0014860225,0014860563,0014860571,0014860589,0014860597,0014860605,0014860613,0014860621,0014860639,0014860647,0014860654,0014860662,0014860670");
        input.setTransactionDateFrom("18-JUN-2014");
        input.setTransactionDateTo("18-JUL-2014");
        
        TransactionReportOutput transaction = new TransactionReportOutput();
        ArrayList transactionArray = new ArrayList<TransactionReportOutput>();
        transactionArray=GenerateTransactionReport(input);
        transaction=(TransactionReportOutput)transactionArray.get(1);
        System.out.println(transaction.getTRANSACTION_ID());
        System.out.println("Goodbye!");
        transactionVal=transactionVal +"<PARTNER_ID>"+transaction.getPARTNER_ID()+"</PARTNER_ID>";
              transactionVal=transactionVal +"<PALS_COUNTRY_CODE>"+transaction.getPALS_COUNTRY_CODE()+"</PALS_COUNTRY_CODE>";
              transactionVal=transactionVal +"<TRANSACTION_TYPE>"+transaction.getTRANSACTION_TYPE()+"</TRANSACTION_TYPE>";
              transactionVal=transactionVal +"<PURCHASE_CURRENCY>"+transaction.getPURCHASE_CURRENCY()+"</PURCHASE_CURRENCY>";
              transactionVal=transactionVal +"<KSID>"+transaction.getKSID()+"</KSID>";
              transactionVal=transactionVal +"<PURCHASE_COUNTRY_CODE>"+transaction.getPURCHASE_COUNTRY_CODE()+"</PURCHASE_COUNTRY_CODE>";
              transactionVal=transactionVal +"<CARD_1_ID>"+transaction.getCARD_1_ID()+"</CARD_1_ID>";
              transactionVal=transactionVal +"<ODOMETER_PORTAL>"+transaction.getODOMETER_PORTAL()+"</ODOMETER_PORTAL>";
              transactionVal=transactionVal +"<ODOMETER>"+transaction.getODOMETER()+"</ODOMETER>";
              transactionVal=transactionVal +"<TRANSACTION_DT>"+transaction.getTRANSACTION_DT()+"</TRANSACTION_DT>";
              transactionVal=transactionVal +"<TRANSACTION_TIME>"+transaction.getTRANSACTION_TIME()+"</TRANSACTION_TIME>";
              transactionVal=transactionVal +"<STATION_NAME>"+transaction.getSTATION_NAME()+"</STATION_NAME>";
              transactionVal=transactionVal +"<INVOICE_NUMBER_NON_COLLECTIVE>"+transaction.getINVOICE_NUMBER_NON_COLLECTIVE()+"</INVOICE_NUMBER_NON_COLLECTIVE>";
              transactionVal=transactionVal +"<MODIFIED_BY>"+transaction.getMODIFIED_BY()+"</MODIFIED_BY>";
              transactionVal=transactionVal +"<PORTAL_MODIFIED_DATE>"+transaction.getPORTAL_MODIFIED_DATE()+"</PORTAL_MODIFIED_DATE>";
              transactionVal=transactionVal +"<INVOICE_NUMBER_COLLECTIVE>"+transaction.getINVOICE_NUMBER_COLLECTIVE()+"</INVOICE_NUMBER_COLLECTIVE>";
              transactionVal=transactionVal +"<ACCOUNT_ID>"+transaction.getACCOUNT_ID()+"</ACCOUNT_ID>";
              transactionVal=transactionVal +"<CARDGROUP_MAIN_TYPE>"+transaction.getCARDGROUP_MAIN_TYPE()+"</CARDGROUP_MAIN_TYPE>";
              transactionVal=transactionVal +"<CARDGROUP_SUB_TYPE>"+transaction.getCARDGROUP_SUB_TYPE()+"</CARDGROUP_SUB_TYPE>";
              transactionVal=transactionVal +"<CARDGROUP_SEQ>"+transaction.getCARDGROUP_SEQ()+"</CARDGROUP_SEQ>";
              transactionVal=transactionVal +"<PREVIOUS_ODOMETER>"+transaction.getPREVIOUS_ODOMETER()+"</PREVIOUS_ODOMETER>";
              transactionVal=transactionVal +"<PRODUCT_NAME>"+transaction.getPRODUCT_NAME()+"</PRODUCT_NAME>";
              transactionVal=transactionVal +"<QUANTITY>"+transaction.getQUANTITY()+"</QUANTITY>";
              transactionVal=transactionVal +"<CURRENCY_UNIT_PRICE>"+transaction.getCURRENCY_UNIT_PRICE()+"</CURRENCY_UNIT_PRICE>";
              transactionVal=transactionVal +"<INVOICED_VAT>"+transaction.getINVOICED_VAT()+"</INVOICED_VAT>";
              transactionVal=transactionVal +"<INVOICED_NET_AMOUNT>"+transaction.getINVOICED_NET_AMOUNT()+"</INVOICED_NET_AMOUNT>";
              transactionVal=transactionVal +"<INVOICE_DISCOUNT_AMOUNT>"+transaction.getINVOICE_DISCOUNT_AMOUNT()+"</INVOICE_DISCOUNT_AMOUNT>";
              transactionVal=transactionVal +"<INVOICED_UNIT_PRICE>"+transaction.getINVOICED_UNIT_PRICE()+"</INVOICED_UNIT_PRICE>";
              transactionVal=transactionVal +"<INVOICED_GROSS_AMOUNT>"+transaction.getINVOICED_GROSS_AMOUNT()+"</INVOICED_GROSS_AMOUNT>";
              transactionVal=transactionVal +"<INVOICED_NET_AMOUNT_REBATED>"+transaction.getINVOICED_GROSS_AMOUNT_REBATED()+"</INVOICED_NET_AMOUNT_REBATED>";
              transactionVal=transactionVal +"<INVOIVED_VAT_REBATED>"+transaction.getINVOIVED_VAT_REBATED()+"</INVOIVED_VAT_REBATED>";
              transactionVal=transactionVal +"<INVOICED_UNIT_PRICE_REBATED>"+transaction.getINVOICED_UNIT_PRICE_REBATED()+"</INVOICED_UNIT_PRICE_REBATED>";
              transactionVal=transactionVal +"<CURRENCY_GROSS_AMOUNT_REBATED>"+transaction.getCURRENCY_GROSS_AMOUNT_REBATED()+"</CURRENCY_GROSS_AMOUNT_REBATED>";
              transactionVal=transactionVal +"<CURRENCY_UNIT_PRICE_REBATED>"+transaction.getCURRENCY_UNIT_PRICE_REBATED()+"</CURRENCY_UNIT_PRICE_REBATED>";
              transactionVal=transactionVal +"<CURRENCY_GROSS_AMOUNT>"+transaction.getCURRENCY_GROSS_AMOUNT()+"</CURRENCY_GROSS_AMOUNT>";
              transactionVal=transactionVal +"<UNIT_OF_MEASURE>"+transaction.getUNIT_OF_MEASURE()+"</UNIT_OF_MEASURE>";
              transactionVal=transactionVal +"<CURRENCY_VAT>"+transaction.getCURRENCY_VAT()+"</CURRENCY_VAT>";
              transactionVal=transactionVal +"<CURRENCY_DISCOUNT_AMOUNT>"+transaction.getCURRENCY_DISCOUNT_AMOUNT()+"</CURRENCY_DISCOUNT_AMOUNT>";
              transactionVal=transactionVal +"<CURRENCY_NET_AMOUNT>"+transaction.getCURRENCY_NET_AMOUNT()+"</CURRENCY_NET_AMOUNT>";
              transactionVal=transactionVal +"<INVOICED_GROSS_AMOUNT_REBATED>"+transaction.getINVOICED_GROSS_AMOUNT_REBATED()+"</INVOICED_GROSS_AMOUNT_REBATED>";
              transactionVal=transactionVal +"<DRIVER_NUMBER>"+transaction.getDRIVER_NUMBER()+"</DRIVER_NUMBER>";
              transactionVal=transactionVal +"<DRIVER_NAME>"+transaction.getDRIVER_NAME()+"</DRIVER_NAME>";
              transactionVal=transactionVal +"<VEHICLE_NUMBER>"+transaction.getVEHICLE_NUMBER()+"</VEHICLE_NUMBER>";
              transactionVal=transactionVal +"<INTERNAL_NAME>"+transaction.getINTERNAL_NAME()+"</INTERNAL_NAME>";
    }
}

package com.sfr.services.core.dao.factory;

import com.sfr.services.client.proxy.user.OIMUserManagermentImpl;
import com.sfr.util.AccessDataControl;
import com.sfr.util.ConfigurationUtility;
import com.sfr.util.constants.Constants;

import java.net.MalformedURLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

import javax.xml.ws.BindingProvider;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;

public class DAOFactory {
    
    public static ResourceBundle resourceBundle = null;
    public static DAOFactory factory = null;
    
    
    public DAOFactory() {
        super();
    }
    
    

    public static void setEndpointAddress(Object port, String newAddress) {

        assert port instanceof BindingProvider : "Doesn't appear to be a valid port";
        assert newAddress != null : "Doesn't appear to be a valid address";

        BindingProvider bp = (BindingProvider)port;

        Map<String, Object> context = bp.getRequestContext();

        Object oldAddress = context.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        System.out.println(AccessDataControl.getDisplayRecord() + "DAOFactory.setEndpointAddress : " +
                           "context.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY)" + context.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
        String newAddressValue = getPropertyValue(newAddress);


        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newAddressValue);


    }


    public static DAOFactory getInstance() {

        if (null == factory) {
            factory = new DAOFactory();
        }

        return factory;
    }

    public static String getDefaultLanguage() {
        String lang=getPropertyValue("DEFAULT_LANG");
        return lang;
    }
    
    public static String getDefaultProfile() {
        String default_profile = getPropertyValue("DEFAULT_PROFILE");
        return default_profile;
    }
    
    public static String getAboutStatoilLink(String aboutStatoilValue){
        String aboutStatoilLink = getPropertyValue(aboutStatoilValue);
        return aboutStatoilLink; 
    }
    
    public static String getContactStatoilLink(String contactStatoilValue){
        String contactStatoilLink = getPropertyValue(contactStatoilValue);
        return contactStatoilLink; 
    }
    
    public static String getPropertyValue(String PName) {
        return ConfigurationUtility.getPropertyValue(PName);
    }

   /* public ProcessCustomerCreationFirstLevelToJDEE1BSSV getProcessCustomerCreationFirstLevelToJDEE1BSSV() {
        Class<ProcessCustomerCreationFirstLevelToJDEE1BSSV> serviceEndPoint = ProcessCustomerCreationFirstLevelToJDEE1BSSV.class;
        String wsdlUrl = new DAOFactory().getPropertyValue(Constants.CUSTOMER_CREATION_FIRST_LEVEL_WSDL_URL);
        String targetNamespace =
            "http://xmlns.oracle.com/S0237WebCenterCustomerCreationFirstLevelToJDEE1App/S0237ProcessCustomerCreationFirstLevelWebCenterToJDEE1/ProcessCustomerCreationFirstLevelToJDEE1BSSV";
        String serviceName = "processcustomercreationfirstleveltojdee1bssv_client_ep";
        String portName = "ProcessCustomerCreationFirstLevelToJDEE1BSSV_pt";

        ProcessCustomerCreationFirstLevelToJDEE1BSSV processCustomerCreationFirstLevelToJDEE1BSSV = null;
        try {
            WebServiceProxy client = new WebServiceProxy(wsdlUrl, serviceName, targetNamespace);
            processCustomerCreationFirstLevelToJDEE1BSSV = client.getServicePort(portName, serviceEndPoint);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return processCustomerCreationFirstLevelToJDEE1BSSV;
    }*/

    /*public ProcessCustomerCreationFinalToJDEE1BSSV getProcessCustomerCreationFinalToJDEE1BSSV() {


        Class<ProcessCustomerCreationFinalToJDEE1BSSV> serviceEndPoint = ProcessCustomerCreationFinalToJDEE1BSSV.class;
        String wsdlUrl = new DAOFactory().getPropertyValue(Constants.CUSTOMER_CREATION_FINAL_WSDL_URL);
        String targetNamespace =
            "http://xmlns.oracle.com/S0258WebCenterCustomerCreationFinalToJDEE1App/S0258ProcessCustomerCreationFinalWebCenterToJDEE1/ProcessCustomerCreationFinalToJDEE1BSSV";
        String serviceName = "processcustomercreationfinaltojdee1bssv_client_ep";
        String portName = "ProcessCustomerCreationFinalToJDEE1BSSV_pt";

        ProcessCustomerCreationFinalToJDEE1BSSV processCustomerCreationFinalToJDEE1BSSV = null;
        try {
            WebServiceProxy client = new WebServiceProxy(wsdlUrl, serviceName, targetNamespace);
            processCustomerCreationFinalToJDEE1BSSV = client.getServicePort(portName, serviceEndPoint);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return processCustomerCreationFinalToJDEE1BSSV;
    }*/


   /* public ProcessCustomerLoginToJDEE1BSSV getProcessCustomerLoginToJDEE1BSSV() {
        System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".getProcessCustomerLoginToJDEE1BSSV : " +
                           "inside getProcessCustomerLoginToJDEE1BSSV");
        Class<ProcessCustomerLoginToJDEE1BSSV> serviceEndPoint = ProcessCustomerLoginToJDEE1BSSV.class;
        String wsdlUrl = new DAOFactory().getPropertyValue(Constants.CUSTOMER_INFO_WSDL_URL);
        String targetNamespace =
            "http://xmlns.oracle.com/S0230WebCenterCustomerLoginToJDEE1App/S0230ProcessCustomerLoginWebCenterToJDEE1/ProcessCustomerLoginToJDEE1BSSV";
        String serviceName = "processcustomerlogintojdee1bssv_client_ep";
        String portName = "ProcessCustomerLoginToJDEE1BSSV_pt";

        ProcessCustomerLoginToJDEE1BSSV processCustomerLoginToJDEE1BSSV = null;
        try {
            WebServiceProxy client = new WebServiceProxy(wsdlUrl, serviceName, targetNamespace);
            processCustomerLoginToJDEE1BSSV = client.getServicePort(portName, serviceEndPoint);
            System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".getProcessCustomerLoginToJDEE1BSSV : " +
                               "processCustomerLoginToJDEE1BSSV " + processCustomerLoginToJDEE1BSSV);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processCustomerLoginToJDEE1BSSV;
    }*/

    /*public ProcessInvoiceInquiryToJDEE1BSSV getProcessInvoiceInquiryToJDEE1BSSV() { //invoice check

        Class<ProcessInvoiceInquiryToJDEE1BSSV> serviceEndPoint = ProcessInvoiceInquiryToJDEE1BSSV.class;
        String wsdlUrl = new DAOFactory().getPropertyValue(Constants.INVOICE_INQ_WSDL_URL);
        String targetNamespace =
            "http://xmlns.oracle.com/S0233WebCenterInvoiceInquiryToJDEE1App/S0233ProcessInvoiceInquiryWebCenterToJDEE1/ProcessInvoiceInquiryToJDEE1BSSV";
        String serviceName = "processinvoiceinquirytojdee1bssv_client_ep";
        String portName = "ProcessInvoiceInquiryToJDEE1BSSV_pt";

        ProcessInvoiceInquiryToJDEE1BSSV processInvoiceInquiryToJDEE1BSSV = null;
        try {
            WebServiceProxy client = new WebServiceProxy(wsdlUrl, serviceName, targetNamespace);
            processInvoiceInquiryToJDEE1BSSV = client.getServicePort(portName, serviceEndPoint);

        } catch (MalformedURLException murle) {
            // TODO: Add catch code
            murle.printStackTrace();
        }
        return processInvoiceInquiryToJDEE1BSSV;

    }*/

    /*public ProcessOrderInquiryToJDEE1BSSV getProcessOrderInquiryToJDEE1BSSV() { //order check


        Class<ProcessOrderInquiryToJDEE1BSSV> serviceEndPoint = ProcessOrderInquiryToJDEE1BSSV.class;
        String wsdlUrl = new DAOFactory().getPropertyValue(Constants.ORDER_INQ_WSDL_URL);
        String targetNamespace =
            "http://xmlns.oracle.com/S0239WebCenterOrderInquiryToJDEE1App/S0239ProcessOrderInquiryWebCenterToJDEE1/ProcessOrderInquiryToJDEE1BSSV";
        String serviceName = "processorderinquirytojdee1bssv_client_ep";
        String portName = "ProcessOrderInquiryToJDEE1BSSV_pt";

        ProcessOrderInquiryToJDEE1BSSV processOrderInquiryToJDEE1BSSV = null;
        try {
            WebServiceProxy client = new WebServiceProxy(wsdlUrl, serviceName, targetNamespace);
            processOrderInquiryToJDEE1BSSV = client.getServicePort(portName, serviceEndPoint);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return processOrderInquiryToJDEE1BSSV;

    }*/

    /*public ProcessStatementInquiryToJDEE1BSSV getprocessStatementInquiryToJDEE1BSSV() { //ledger check

        Class<ProcessStatementInquiryToJDEE1BSSV> serviceEndPoint = ProcessStatementInquiryToJDEE1BSSV.class;
        String wsdlUrl = new DAOFactory().getPropertyValue(Constants.LEDGER_INQ_WSDL_URL);
        String targetNamespace =
            "http://xmlns.oracle.com/S0368WebCenterStatementInquiryToJDEE1App/S0368ProcessStatementInquiryWebCenterToJDEE1/ProcessStatementInquiryToJDEE1BSSV";
        String serviceName = "processstatementinquirytojdee1bssv_client_ep";
        String portName = "ProcessStatementInquiryToJDEE1BSSV_pt";

        ProcessStatementInquiryToJDEE1BSSV processStatementInquiryToJDEE1BSSV = null;
        try {
            WebServiceProxy client = new WebServiceProxy(wsdlUrl, serviceName, targetNamespace);
            processStatementInquiryToJDEE1BSSV = client.getServicePort(portName, serviceEndPoint);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return processStatementInquiryToJDEE1BSSV;

    }*/

    /*public ValidateSalesOrderWebCenterS0131ReqABCSImpl getValidateSalesOrderWebCenterS0131ReqABCSImpl() {

        Class<ValidateSalesOrderWebCenterS0131ReqABCSImpl> serviceEndPoint = ValidateSalesOrderWebCenterS0131ReqABCSImpl.class;
        String wsdlUrl = new DAOFactory().getPropertyValue(Constants.ORDER_VALIDATE_WSDL_URL);
        String targetNamespace = "http://xmlns.oracle.com/ABCSImpl/WebCenterS0131/Core/ValidateSalesOrderWebCenterS0131ReqABCSImpl/V1.0";
        String serviceName = "ValidateSalesOrderWebCenterS0131ReqABCSImpl";
        String portName = "ValidateSalesOrderWebCenterS0131ReqABCSImpl_pt";

        ValidateSalesOrderWebCenterS0131ReqABCSImpl validateSalesOrderWebCenterS0131ReqABCSImpl = null;
        try {
            WebServiceProxy client = new WebServiceProxy(wsdlUrl, serviceName, targetNamespace);
            validateSalesOrderWebCenterS0131ReqABCSImpl =
                    (ValidateSalesOrderWebCenterS0131ReqABCSImpl)client.getServicePort(portName, serviceEndPoint);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return validateSalesOrderWebCenterS0131ReqABCSImpl;

    }*/


    /*public ProcessCustomerSearchToJDEE1BSSV getProcessCustomerSearchToJDEE1BSSV() { //check

        Class<ProcessCustomerSearchToJDEE1BSSV> serviceEndPoint = ProcessCustomerSearchToJDEE1BSSV.class;
        String wsdlUrl = new DAOFactory().getPropertyValue(Constants.CUSTOMER_SEARCH_WSDL_URL);
        String targetNamespace =
            "http://xmlns.oracle.com/S0238WebCenterCustomerSearchToJDEE1App/S0238ProcessCustomerSearchWebCenterToJDEE1/ProcessCustomerSearchToJDEE1BSSV";
        String serviceName = "processcustomersearchtojdee1bssv_client_ep";
        String portName = "ProcessCustomerSearchToJDEE1BSSV_pt";

        ProcessCustomerSearchToJDEE1BSSV processCustomerSearchToJDEE1BSSV = null;
        try {
            WebServiceProxy client = new WebServiceProxy(wsdlUrl, serviceName, targetNamespace);
            processCustomerSearchToJDEE1BSSV = client.getServicePort(portName, serviceEndPoint);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return processCustomerSearchToJDEE1BSSV;
    }*/


   /* public ProcessARInvoiceInquiryToJDEE1BSSV getProcessARInvoiceInquiryToJDEE1BSSV() { //ssinvoice
        Class<ProcessARInvoiceInquiryToJDEE1BSSV> serviceEndPoint = ProcessARInvoiceInquiryToJDEE1BSSV.class;
        String wsdlUrl = new DAOFactory().getPropertyValue(Constants.SS_INVOICE_INQ_WSDL_URL);
        String targetNamespace =
            "http://xmlns.oracle.com/S0367WebCenterARInvoiceInquiryToJDEE1App/S0367ProcessARInvoiceInquiryWebCenterToJDEE1/ProcessARInvoiceInquiryToJDEE1BSSV";
        String serviceName = "processarinvoiceinquirytojdee1bssv_client_ep";
        String portName = "ProcessARInvoiceInquiryToJDEE1BSSV_pt";

        ProcessARInvoiceInquiryToJDEE1BSSV processARInvoiceInquiryToJDEE1BSSV = null;
        try {
            WebServiceProxy client = new WebServiceProxy(wsdlUrl, serviceName, targetNamespace);
            processARInvoiceInquiryToJDEE1BSSV = client.getServicePort(portName, serviceEndPoint);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return processARInvoiceInquiryToJDEE1BSSV;
    }*/


    public OIMUserManagermentImpl getOIMUserManagermentImpl() {
        Class<OIMUserManagermentImpl> serviceEndPoint = OIMUserManagermentImpl.class;
        String wsdlUrl = new DAOFactory().getPropertyValue(Constants.OIM_WSDL_URL);
        String targetNamespace = "http://ws.oim.sfr.com/";
        String serviceName = "OIMUserManagermentImpl";
        String portName = "OIMUserManagermentImplPort";

        OIMUserManagermentImpl oIMUserManagermentImpl = null;
        try {
            WebServiceProxy client = new WebServiceProxy(wsdlUrl, serviceName, targetNamespace);
            SecurityPoliciesFeature securityFeatures = new SecurityPoliciesFeature(new String[] { "oracle/wss_username_token_client_policy" });
            oIMUserManagermentImpl = client.getServicePortWithFeatures(portName, serviceEndPoint, securityFeatures);
            ((BindingProvider)oIMUserManagermentImpl).getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
                                                                              getPropertyValue("OIM_WSDL_USERNAME"));
            ((BindingProvider)oIMUserManagermentImpl).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
                                                                              getPropertyValue("OIM_WSDL_PASSWORD"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return oIMUserManagermentImpl;
    }


    public static Connection getJNDIConnection() throws SQLException, NamingException {

        // initially no connection
        Connection connection = null;
        InitialContext objinitialContext = null;
        DataSource datasource = null;

        //              The WLInitialContextFactory creates initial contexts for accessing
        //              the WebLogic naming service. It can also be used to
        //              create a multitier connection to another naming service
        //              through a WebLogic Server.
        //
        //               use hashtable to map specific keys into table

        try {
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

            // env.put(Context.PROVIDER_URL, "t3://10.101.53.66:8001");
            // Below property is required only if you are running as stand-alone app
            // env.put(Context.PROVIDER_URL, getPropertyValue("JNDI_URI"));


            //to obtain the initial context that contains
            //the resource (a Java object).
            objinitialContext = new InitialContext(env);

            // use datasource for connection to physical data source.
            // Give JNDI name under lookup.
            datasource = (DataSource)objinitialContext.lookup(getPropertyValue("JNDI_NAME"));

            if (datasource != null) {
                connection = datasource.getConnection();
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw sqle;
        } catch (NamingException ne) {
            ne.printStackTrace();
            throw ne;
        } finally {
            try {

                if (objinitialContext != null) {
                    objinitialContext.close();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                throw ne;
            }
        }
        return connection;
    }
    
    /**
     * @param sqlQuery
     * @param params
     * @param returnParam
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public static Map<String,String> executeSqlStmtDoubleColumns(String sqlQuery, List<String> params, String returnParam1, String returnParam2) throws SQLException, NamingException {


        PreparedStatement objPrepStmt = null;
        ResultSet objRS = null;
        Map<String,String> result = new HashMap<String,String>();
        Connection connection = null;
        try {
            connection = getJNDIConnection();
            if (null != connection) {
                objPrepStmt = connection.prepareStatement(sqlQuery);
                if (null != objPrepStmt) {
                    for (int iCount = 0; iCount < params.size(); iCount++) {
                        objPrepStmt.setString(iCount + 1, params.get(iCount));
                    }
                    objRS = objPrepStmt.executeQuery();
                    if (null != objRS) {
                        while (objRS.next()) {
                            String key=null;
                            String value=null;
                            if (objRS.getString(returnParam1) != null) 
                                key= objRS.getString(returnParam1).trim();
                            if (objRS.getString(returnParam2) != null)
                                value= objRS.getString(returnParam2).trim();
                            if(key!=null && value!=null){
                                result.put(key, value);
                            }
                        }
                    }
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw sqe;
        } catch (NamingException ne) {
            ne.printStackTrace();
            throw ne;
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
        return result;


    }

    /**
     * @param sqlQuery
     * @param params
     * @param returnParam
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public static List<String> executeSqlStmt(String sqlQuery, List<String> params, String returnParam) throws SQLException, NamingException {


        PreparedStatement objPrepStmt = null;
        ResultSet objRS = null;
        List<String> result = new ArrayList<String>();
        Connection connection = null;
        try {
            connection = getJNDIConnection();
            if (null != connection) {
                objPrepStmt = connection.prepareStatement(sqlQuery);
                if (null != objPrepStmt) {
                    for (int iCount = 0; iCount < params.size(); iCount++) {
                        objPrepStmt.setString(iCount + 1, params.get(iCount));
                    }
                    objRS = objPrepStmt.executeQuery();
                    if (null != objRS) {
                        while (objRS.next()) {
                            if (objRS.getString(returnParam) != null)//Adding while performing US16
                                result.add(objRS.getString(returnParam).trim());
                        }
                    }
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw sqe;
        } catch (NamingException ne) {
            ne.printStackTrace();
            throw ne;
        } finally {
            if (null != connection) {
                connection.close();
            }
        }

        return result;


    }

    /**
     * @param productCode
     * @param userDefinedCodes
     * @param userDefinedCode
     * @return DESCRIPTION
     * @throws SQLException
     * @throws NamingException
     */
    public static String getUDCInfo(String productCode, String userDefinedCodes, String userDefinedCode) throws SQLException, NamingException {

        List<String> result = null;
        String description = null;

        List<String> params = new ArrayList<String>();
        params.add(productCode.trim());
        params.add(userDefinedCodes.trim());
        params.add(userDefinedCode.trim());
        result =
                executeSqlStmt("select description from prt_load_udc_info where trim(product_code) = ? and  trim(user_defined_codes) = ? and trim(user_defined_code) = ? ",
                               params, "DESCRIPTION");
        if (result != null && !result.isEmpty()) {
            description = result.get(0);
        }
        else
        {
            System.out.println(AccessDataControl.getDisplayRecord() + ".getUDCInfo" + "found multiple records for same combination:" + "productCode:" + 
                               productCode +"userDefinedCodes:" + userDefinedCodes + "userDefinedCode:" +userDefinedCode);
        }

        return description;
    }
    //TODO: Put top description and parameter description in comment
    /**
     * @param productCode
     * @param userDefinedCodes
     * @param description
     * @return USER_RESERVED_REFERENCE4
     * @throws SQLException
     * @throws NamingException
     */
    public static String getSpecialHandlingCodeFromUDCByDescription(String productCode, String userDefinedCodes,
                                                                    String description) throws SQLException, NamingException {
        List<String> result = null;
        String userref = null;
        List<String> params = new ArrayList<String>();
        params.add(productCode.trim());
        params.add(userDefinedCodes.trim());
        params.add(description.trim());
        result =
                executeSqlStmt("select user_reserved_reference4 from prt_load_udc_info where trim(product_code) = ? and  trim(user_defined_codes) = ? and trim(description) = ? ",
                               params, "USER_RESERVED_REFERENCE4");
        if (result != null && !result.isEmpty()) {
            userref = result.get(0);
        }else{
            System.out.println(AccessDataControl.getDisplayRecord() + "DAOFactory.getSpecialHandlingCodeFromUDC : userref is null or might contain more than one record.");
        }
        System.out.println(AccessDataControl.getDisplayRecord() + "DAOFactory.getSpecialHandlingCodeFromUDC : productCode:" + productCode +
                           " userDefinedCodes:" + userDefinedCodes + " description:" + description + ". Responde from DB is:" + userref);
        return userref;
    }

    /**
     * @param productCode
     * @param userDefinedCodes
     * @param description
     * @return userDefinedCode
     * @throws SQLException
     * @throws NamingException
     * Method used to get the code for reprising products 
     */
    public static String getUserDefinedCodeFromUDCByDescription(String productCode, String userDefinedCodes,
                                                                    String description) throws SQLException, NamingException {
        List<String> result = null;
        String userDefinedCode = null;
        List<String> params = new ArrayList<String>();
        params.add(productCode.trim());
        params.add(userDefinedCodes.trim());
        params.add(description.trim());
        result =
                executeSqlStmt("select user_defined_code from prt_load_udc_info where trim(product_code) = ? and  trim(user_defined_codes) = ? and trim(description) = ? ",
                               params, "USER_DEFINED_CODE");
        if (result != null && result.size()>0) {
            userDefinedCode = result.get(0);
        }else{
            System.out.println(AccessDataControl.getDisplayRecord() + "DAOFactory.getUserDefinedCodeFromUDCByDescription : userDefinedCode is null or might contain more than one record.");
        }
        System.out.println(AccessDataControl.getDisplayRecord() + "DAOFactory.getUserDefinedCodeFromUDCByDescription : productCode:" + productCode +
                           " userDefinedCodes:" + userDefinedCodes + " description:" + description + ". userDefinedCode from DB is:" + userDefinedCode);
        return userDefinedCode;
    }

    /**
     * @param productCode
     * @param userDefinedCodes
     * @param userDefinedCode
     * @return USER_RESERVED_REFERENCE4
     * @throws SQLException
     * @throws NamingException
     */
    public static String getSpecialHandlingCodeFromUDC(String productCode, String userDefinedCodes, String userDefinedCode) throws SQLException,
                                                                                                                                   NamingException {
        System.out.println();
        List<String> result = null;
        String userref = null;
        List<String> params = new ArrayList<String>();
        params.add(productCode.trim());
        params.add(userDefinedCodes.trim());
        params.add(userDefinedCode.trim());
        result =
                executeSqlStmt("select user_reserved_reference4 from prt_load_udc_info where trim(product_code) = ? and  trim(user_defined_codes) = ? and trim(user_defined_code) = ? ",
                               params, "USER_RESERVED_REFERENCE4");
        if (result != null && !result.isEmpty()) {
            userref = result.get(0);
        }
        System.out.println(AccessDataControl.getDisplayRecord() + "DAOFactory.getSpecialHandlingCodeFromUDC : productCode:" + productCode +
                           " userDefinedCodes:" + userDefinedCodes + " userDefinedCode:" + userDefinedCode + ". Responde from DB is:" + userref);
        return userref;
    }


    /**
     * @param branchPlantCode
     * @return COUNTRY
     * @throws SQLException
     * @throws NamingException
     */
    public static String getBranchPlantCountry(String branchPlantCode) throws SQLException, NamingException {
        List<String> result = null;
        String country = null;

        List<String> params = new ArrayList<String>();
        params.add(branchPlantCode.trim());

        result = executeSqlStmt("select country from prt_load_airport_bp_xref where trim(branch_plant) = ? ", params, "COUNTRY");
        if (result != null && !result.isEmpty()) {
            country = result.get(0);
        }
        System.out.println(AccessDataControl.getDisplayRecord() + "DAOFactory.getBranchPlantCountry : " + "country in DAOFactory:" + country);
        return country;
    }

    /**
     * @param destination
     * @return COUNTRY
     * @throws SQLException
     * @throws NamingException
     */
    public static String getDestinationCountry(String destination) throws SQLException, NamingException {
        List<String> result = null;
        String country = null;

        List<String> params = new ArrayList<String>();
        params.add(destination.trim());

        result = executeSqlStmt("select country from prt_load_airport_bp_xref where trim(airport_code) = ? ", params, "COUNTRY");
        if (result != null && !result.isEmpty()) {
            country = result.get(0);
        }
        System.out.println(AccessDataControl.getDisplayRecord() + "DAOFactory.getDestinationCountry : " + "country in DAOFactory:" + country);
        return country;
    }
    //TODO: use <code></code>
    /**
     * Hardcoded: product_code=55 and user_defined_codes=AP
     *
     * @param paymentCode
     * @return DESCRIPTION
     * @throws SQLException
     * @throws NamingException
     */
    public static String getPaymentMethod(String paymentCode) throws SQLException, NamingException {
        List<String> result = null;
        String paymentMethod = null;

        List<String> params = new ArrayList<String>();
        params.add("55");
        params.add("AP");
        params.add(paymentCode.trim());

        result =
                executeSqlStmt("select description from prt_load_udc_info where trim(product_code) = ? and trim(user_defined_codes) = ? and trim(user_defined_code) = ? ",
                               params, "DESCRIPTION");
        if (result != null && !result.isEmpty()) {
            paymentMethod = result.get(0);
        }
        System.out.println(AccessDataControl.getDisplayRecord() + "DAOFactory.getPaymentMethod : " + "paymentMethod in DAOFactory:" + paymentMethod);
        return paymentMethod;
    }

    /**
     * Hardcoded: product_code=55 and user_defined_codes=AP
     *
     * @param paymentCode
     * @return USER_RESERVED_REFERENCE4
     * @throws SQLException
     * @throws NamingException
     */
    public static String getSpecialHandlingCode(String paymentCode) throws SQLException, NamingException {
        List<String> result = null;
        String specialHandlingCode = null;

        List<String> params = new ArrayList<String>();
        params.add("55");
        params.add("AP");
        params.add(paymentCode.trim());

        result =
                executeSqlStmt("select user_reserved_reference4 from prt_load_udc_info where trim(product_code) = ? and trim(user_defined_codes) = ? and trim(user_defined_code) = ? ",
                               params, "USER_RESERVED_REFERENCE4");
        if (result != null && !result.isEmpty()) {
            specialHandlingCode = result.get(0);
        }
        System.out.println(AccessDataControl.getDisplayRecord() + "DAOFactory.getSpecialHandlingCode : " + "specialHandlingCode in DAOFactory:" +
                           specialHandlingCode);
        return specialHandlingCode;
    }

    /**
     * @param branchPlantCountry
     * @param shippingAddressNo
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public static String getAirlineNumber(String branchPlantCountry, String shippingAddressNo) throws SQLException, NamingException {
        List<String> result = null;
        String airlineNo = null;

        List<String> params = new ArrayList<String>();
        params.add(branchPlantCountry.trim());
        params.add(shippingAddressNo.trim());

        result =
                executeSqlStmt("select airline_code from prt_load_airline_sh_xref where trim(country) = ? and  trim(shipping_address) = ? ", params, "AIRLINE_CODE");
        if (result != null && !result.isEmpty()) {
            airlineNo = result.get(0);
        }
        System.out.println(AccessDataControl.getDisplayRecord() + "DAOFactory.getAirlineNumber : " + "airlineNo in DAOFactory:" + airlineNo);
        return airlineNo;
    }
    
    /*public UCMCustomWeb getUCMService()
    {
        Class<UCMCustomWeb> serviceEndPoint = UCMCustomWeb.class;
        String wsdlUrl = getPropertyValue(Constants.UCM_WSDL_URL);
        String targetNamespace = "http://ws.wcc.lnt.com/";
        String serviceName = "UCMCustomWebService";
        String portName = "UCMCustomWebPort";
        String ucmUsername = getPropertyValue(Constants.UCM_USERNAME);
        String ucmPassword = getPropertyValue(Constants.UCM_PASSWORD);
        UCMCustomWeb uCMCustomWeb = null;
        try {
            WebServiceProxy client = new WebServiceProxy(wsdlUrl, serviceName, targetNamespace);
            SecurityPoliciesFeature securityFeatures = new SecurityPoliciesFeature(new String[] { "oracle/wss_username_token_client_policy" });
            uCMCustomWeb = client.getServicePortWithFeatures(portName, serviceEndPoint, securityFeatures);
            ((BindingProvider)uCMCustomWeb).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, ucmUsername);
            ((BindingProvider)uCMCustomWeb).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, ucmPassword);
           
        } catch (Exception e) {
            System.out.println(AccessDataControl.getDisplayRecord() +this.getClass() + ".getUCMService : " +"Exception");
            e.printStackTrace();

        }
         return uCMCustomWeb;
    }*/

    public static Map<String, String> getMapDataFromFeature(String FEATURE_NAME, String FEATURE_VALUE,
                                                                  String CONTROL_ATTR, String CONTROL_ATTR_VALUE,
                                                                  String VALUE, String returnColumnName1,
                                                                  String returnColumnName2) throws SQLException,
                                                                                                   NamingException {
        Map<String, String> result = null;
        System.out.println("DAOFactory.getSingleColumnDataFromFeature before mapping:FEATURE_NAME<" + FEATURE_NAME +
                           ">FEATURE_VALUE<" + FEATURE_VALUE + ">CONTROL_ATTR<" + CONTROL_ATTR +
                           ">CONTROL_ATTR_VALUE<" + CONTROL_ATTR_VALUE + ">VALUE<" + VALUE + ">returnColumnName1<" +
                           returnColumnName1 + ">returnColumnName2<" + returnColumnName2);

        List<String> params = new ArrayList<String>();

        FEATURE_NAME = (FEATURE_NAME == null ? "%" : FEATURE_NAME);
        FEATURE_VALUE = (FEATURE_VALUE == null ? "%" : FEATURE_VALUE);
        CONTROL_ATTR = (CONTROL_ATTR == null ? "%" : CONTROL_ATTR);
        CONTROL_ATTR_VALUE = (CONTROL_ATTR_VALUE == null ? "%" : CONTROL_ATTR_VALUE);
        VALUE = (VALUE == null ? "%" : VALUE);

        System.out.println("DAOFactory.getSingleColumnDataFromFeature after mapping:FEATURE_NAME<" + FEATURE_NAME +
                           ">FEATURE_VALUE<" + FEATURE_VALUE + ">CONTROL_ATTR<" + CONTROL_ATTR +
                           ">CONTROL_ATTR_VALUE<" + CONTROL_ATTR_VALUE + ">VALUE<" + VALUE + ">returnColumnName1<" +
                           returnColumnName1 + ">returnColumnName2<" + returnColumnName2);

        params.add(FEATURE_NAME);
        params.add(FEATURE_VALUE);
        params.add(CONTROL_ATTR);
        params.add(CONTROL_ATTR_VALUE);
        params.add(VALUE);

        result =
                executeSqlStmtDoubleColumns("select * from prt_gen_feature where feature_name like ? and feature_value like ? and control_attr like ? and control_attr_value like ? and value like ?",
                                            params, returnColumnName1, returnColumnName2);

        return result;
    }

    public static List<String> getSingleColumnDataFromFeature(String FEATURE_NAME, String FEATURE_VALUE, String CONTROL_ATTR, String CONTROL_ATTR_VALUE, String VALUE, String columnName) throws SQLException, NamingException {
        List<String> result = null;
        System.out.println("DAOFactory.getSingleColumnDataFromFeature before mapping:FEATURE_NAME<"+FEATURE_NAME+">FEATURE_VALUE<"+FEATURE_VALUE+">CONTROL_ATTR<"+CONTROL_ATTR+">CONTROL_ATTR_VALUE<"+CONTROL_ATTR_VALUE+">VALUE<"+VALUE+">columnName<"+columnName);

        List<String> params = new ArrayList<String>();
        
        FEATURE_NAME=(FEATURE_NAME==null?"%":FEATURE_NAME);
        FEATURE_VALUE=(FEATURE_VALUE==null?"%":FEATURE_VALUE);
        CONTROL_ATTR=(CONTROL_ATTR==null?"%":CONTROL_ATTR);
        CONTROL_ATTR_VALUE=(CONTROL_ATTR_VALUE==null?"%":CONTROL_ATTR_VALUE);
        VALUE=(VALUE==null?"%":VALUE);
        
        System.out.println("DAOFactory.getSingleColumnDataFromFeature after mapping:FEATURE_NAME<"+FEATURE_NAME+">FEATURE_VALUE<"+FEATURE_VALUE+">CONTROL_ATTR<"+CONTROL_ATTR+">CONTROL_ATTR_VALUE<"+CONTROL_ATTR_VALUE+">VALUE<"+VALUE+">columnName<"+columnName);
        
        params.add(FEATURE_NAME);
        params.add(FEATURE_VALUE);
        params.add(CONTROL_ATTR);
        params.add(CONTROL_ATTR_VALUE);
        params.add(VALUE);
        
        
        
        result = executeSqlStmt("select * from prt_gen_feature where feature_name like ? and feature_value like ? and control_attr like ? and control_attr_value like ? and value like ?", params, columnName);
        System.out.println(AccessDataControl.getDisplayRecord() + "DAOFactory.getBranchPlantCountry : " + "country in DAOFactory:" + columnName);
        return result;
    }
    
    /* Added by Siddharth on 23/01/2014 to insert data in to PRT_SERVICE_INTERFACE_AUDIT */
    
    /*public static void insertIntoServiceInterfaceAudit(String interfaceId,String instanceId,String methodName,String status,Number responseTime,String loggedInUser,
                                                       String errorId,String errorMessage, boolean isCallBackServlet){
        System.out.println("Is it coming inside this method");
        PreparedStatement objPrepStmt = null;
        Connection connection = null;
        ExternalContext ectx = null;
        HttpServletRequest request = null;
        HttpSession session = null;
      
        if(!isCallBackServlet){
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession();
        User user = new User();
         if (loggedInUser == null) {
            if (null != session && session.getAttribute(Constants.SESSION_USER_INFO) != null) {
                user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
                loggedInUser = user.getEmailID();
            }
         }
            
        }
        try {
            connection = getJNDIConnection();
            
            
            String sqlQuery = "INSERT INTO PRT_SERVICE_INTERFACE_AUDIT"
                                + "(EVENT_ID, INTERAFCE_ID, INSTANCE_ID, METHOD_NAME,STATUS,RESPONSE_TIME,LOGGED_IN_USER,REQUEST_TIME,ERROR_ID,ERROR_MESSAGE) VALUES"
                                + "(SEQ_ID_PRT_SERVICE_INT_AUDIT.NEXTVAL,?,?,?,?,?,?,?,?,?)";

            if (null != connection) {
                objPrepStmt = connection.prepareStatement(sqlQuery);
                objPrepStmt.setString(1, interfaceId);
                objPrepStmt.setString(2, instanceId);
                objPrepStmt.setString(3, methodName);
                objPrepStmt.setString(4, status);
                objPrepStmt.setObject(5, responseTime);
                objPrepStmt.setString(6, loggedInUser);
                objPrepStmt.setTimestamp(7, getCurrentTimeStamp());
                objPrepStmt.setString(8, errorId);
                objPrepStmt.setString(9, errorMessage);
                objPrepStmt.executeUpdate();
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        } catch (NamingException ne) {
            ne.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
                if (null != objPrepStmt) {
                    objPrepStmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private static java.sql.Timestamp getCurrentTimeStamp() {
     
                    java.util.Date today = new java.util.Date();
                    return new java.sql.Timestamp(today.getTime());
     
            }*/
}

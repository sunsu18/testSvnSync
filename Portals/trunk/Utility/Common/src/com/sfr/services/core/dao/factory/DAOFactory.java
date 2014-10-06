package com.sfr.services.core.dao.factory;


import com.sfr.core.bean.Roles;
import com.sfr.core.bean.User;
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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import javax.xml.ws.BindingProvider;

import oracle.adf.share.logging.ADFLogger;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;


public class DAOFactory {

    public static ResourceBundle resourceBundle = null;
    public static DAOFactory factory = null;
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();


    public DAOFactory() {
        super();
    }


    public static void setEndpointAddress(Object port, String newAddress) {
        AccessDataControl accessDC = new AccessDataControl();
        assert port instanceof BindingProvider : "Doesn't appear to be a valid port";
        assert newAddress != null : "Doesn't appear to be a valid address";

        BindingProvider bp = (BindingProvider)port;

        Map<String, Object> context = bp.getRequestContext();

        Object oldAddress = context.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        log.info(accessDC.getDisplayRecord() + "DAOFactory.setEndpointAddress : " + "context.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY)" +
                 context.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
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
        String lang = getPropertyValue("DEFAULT_LANG");
        return lang;
    }

    public static String getDefaultProfile() {
        String default_profile = getPropertyValue("DEFAULT_PROFILE");
        return default_profile;
    }

    public static String getAboutStatoilLink(String aboutStatoilValue) {
        String aboutStatoilLink = getPropertyValue(aboutStatoilValue);
        return aboutStatoilLink;
    }

    public static String getContactStatoilLink(String contactStatoilValue) {
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
        log.info(accessDC.getDisplayRecord() + this.getClass() + this.getClass() + ".getProcessCustomerLoginToJDEE1BSSV : " +
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
            log.info(accessDC.getDisplayRecord() + this.getClass() + this.getClass() + ".getProcessCustomerLoginToJDEE1BSSV : " +
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
            ((BindingProvider)oIMUserManagermentImpl).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, getPropertyValue("OIM_WSDL_USERNAME"));
            ((BindingProvider)oIMUserManagermentImpl).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, getPropertyValue("OIM_WSDL_PASSWORD"));
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
            datasource = (DataSource)objinitialContext.lookup(getPropertyValue("JNDI_NAME").toString());

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
    
    public static Connection getEngageJNDIConnection() throws SQLException, NamingException {

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
            datasource = (DataSource)objinitialContext.lookup(getPropertyValue("ENGAGE_JNDI_NAME").toString());

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
    public static Map<String, String> executeSqlStmtDoubleColumns(String sqlQuery, List<String> params, String returnParam1,
                                                                  String returnParam2) throws SQLException, NamingException {


        PreparedStatement objPrepStmt = null;
        ResultSet objRS = null;
        Map<String, String> result = new HashMap<String, String>();
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
                            String key = null;
                            String value = null;
                            if (objRS.getString(returnParam1) != null)
                                key = objRS.getString(returnParam1).trim();
                            if (objRS.getString(returnParam2) != null)
                                value = objRS.getString(returnParam2).trim();
                            if (key != null && value != null) {
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
                            if (objRS.getString(returnParam) != null) //Adding while performing US16
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
        AccessDataControl accessDC = new AccessDataControl();
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
        } else {
            log.info(accessDC.getDisplayRecord() + ".getUDCInfo" + "found multiple records for same combination:" + "productCode:" + productCode +
                     "userDefinedCodes:" + userDefinedCodes + "userDefinedCode:" + userDefinedCode);
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
    public static String getSpecialHandlingCodeFromUDCByDescription(String productCode, String userDefinedCodes, String description) throws SQLException,
                                                                                                                                            NamingException {
        AccessDataControl accessDC = new AccessDataControl();
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
        } else {
            log.info(accessDC.getDisplayRecord() + "DAOFactory.getSpecialHandlingCodeFromUDC : userref is null or might contain more than one record.");
        }
        log.info(accessDC.getDisplayRecord() + "DAOFactory.getSpecialHandlingCodeFromUDC : productCode:" + productCode + " userDefinedCodes:" +
                 userDefinedCodes + " description:" + description + ". Responde from DB is:" + userref);
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
    public static String getUserDefinedCodeFromUDCByDescription(String productCode, String userDefinedCodes, String description) throws SQLException,
                                                                                                                                        NamingException {
        AccessDataControl accessDC = new AccessDataControl();
        List<String> result = null;
        String userDefinedCode = null;
        List<String> params = new ArrayList<String>();
        params.add(productCode.trim());
        params.add(userDefinedCodes.trim());
        params.add(description.trim());
        result =
                executeSqlStmt("select user_defined_code from prt_load_udc_info where trim(product_code) = ? and  trim(user_defined_codes) = ? and trim(description) = ? ",
                               params, "USER_DEFINED_CODE");
        if (result != null && result.size() > 0) {
            userDefinedCode = result.get(0);
        } else {
            log.info(accessDC.getDisplayRecord() +
                     "DAOFactory.getUserDefinedCodeFromUDCByDescription : userDefinedCode is null or might contain more than one record.");
        }
        log.info(accessDC.getDisplayRecord() + "DAOFactory.getUserDefinedCodeFromUDCByDescription : productCode:" + productCode + " userDefinedCodes:" +
                 userDefinedCodes + " description:" + description + ". userDefinedCode from DB is:" + userDefinedCode);
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
        AccessDataControl accessDC = new AccessDataControl();

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
        log.info(accessDC.getDisplayRecord() + "DAOFactory.getSpecialHandlingCodeFromUDC : productCode:" + productCode + " userDefinedCodes:" +
                 userDefinedCodes + " userDefinedCode:" + userDefinedCode + ". Responde from DB is:" + userref);
        return userref;
    }


    /**
     * @param branchPlantCode
     * @return COUNTRY
     * @throws SQLException
     * @throws NamingException
     */
    public static String getBranchPlantCountry(String branchPlantCode) throws SQLException, NamingException {
        AccessDataControl accessDC = new AccessDataControl();
        List<String> result = null;
        String country = null;

        List<String> params = new ArrayList<String>();
        params.add(branchPlantCode.trim());

        result = executeSqlStmt("select country from prt_load_airport_bp_xref where trim(branch_plant) = ? ", params, "COUNTRY");
        if (result != null && !result.isEmpty()) {
            country = result.get(0);
        }
        log.info(accessDC.getDisplayRecord() + "DAOFactory.getBranchPlantCountry : " + "country in DAOFactory:" + country);
        return country;
    }

    /**
     * @param destination
     * @return COUNTRY
     * @throws SQLException
     * @throws NamingException
     */
    public static String getDestinationCountry(String destination) throws SQLException, NamingException {
        AccessDataControl accessDC = new AccessDataControl();
        List<String> result = null;
        String country = null;

        List<String> params = new ArrayList<String>();
        params.add(destination.trim());

        result = executeSqlStmt("select country from prt_load_airport_bp_xref where trim(airport_code) = ? ", params, "COUNTRY");
        if (result != null && !result.isEmpty()) {
            country = result.get(0);
        }
        log.info(accessDC.getDisplayRecord() + "DAOFactory.getDestinationCountry : " + "country in DAOFactory:" + country);
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
        AccessDataControl accessDC = new AccessDataControl();
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
        log.info(accessDC.getDisplayRecord() + "DAOFactory.getPaymentMethod : " + "paymentMethod in DAOFactory:" + paymentMethod);
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
        AccessDataControl accessDC = new AccessDataControl();
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
        log.info(accessDC.getDisplayRecord() + "DAOFactory.getSpecialHandlingCode : " + "specialHandlingCode in DAOFactory:" + specialHandlingCode);
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
        AccessDataControl accessDC = new AccessDataControl();
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
        log.info(accessDC.getDisplayRecord() + "DAOFactory.getAirlineNumber : " + "airlineNo in DAOFactory:" + airlineNo);
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
            log.info(accessDC.getDisplayRecord() + this.getClass() +this.getClass() + ".getUCMService : " +"Exception");
            e.printStackTrace();

        }
         return uCMCustomWeb;
    }*/

    public static Map<String, String> getMapDataFromFeature(String FEATURE_NAME, String FEATURE_VALUE, String CONTROL_ATTR, String CONTROL_ATTR_VALUE,
                                                            String VALUE, String returnColumnName1, String returnColumnName2) throws SQLException,
                                                                                                                                     NamingException {
        AccessDataControl accessDC = new AccessDataControl();
        Map<String, String> result = null;
        log.info(accessDC.getDisplayRecord() + "DAOFactory.getSingleColumnDataFromFeature before mapping:FEATURE_NAME<" + FEATURE_NAME + ">FEATURE_VALUE<" +
                 FEATURE_VALUE + ">CONTROL_ATTR<" + CONTROL_ATTR + ">CONTROL_ATTR_VALUE<" + CONTROL_ATTR_VALUE + ">VALUE<" + VALUE + ">returnColumnName1<" +
                 returnColumnName1 + ">returnColumnName2<" + returnColumnName2);

        List<String> params = new ArrayList<String>();

        FEATURE_NAME = (FEATURE_NAME == null ? "%" : FEATURE_NAME);
        FEATURE_VALUE = (FEATURE_VALUE == null ? "%" : FEATURE_VALUE);
        CONTROL_ATTR = (CONTROL_ATTR == null ? "%" : CONTROL_ATTR);
        CONTROL_ATTR_VALUE = (CONTROL_ATTR_VALUE == null ? "%" : CONTROL_ATTR_VALUE);
        VALUE = (VALUE == null ? "%" : VALUE);

        log.info(accessDC.getDisplayRecord() + "DAOFactory.getSingleColumnDataFromFeature after mapping:FEATURE_NAME<" + FEATURE_NAME + ">FEATURE_VALUE<" +
                 FEATURE_VALUE + ">CONTROL_ATTR<" + CONTROL_ATTR + ">CONTROL_ATTR_VALUE<" + CONTROL_ATTR_VALUE + ">VALUE<" + VALUE + ">returnColumnName1<" +
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

    public static List<String> getSingleColumnDataFromFeature(String FEATURE_NAME, String FEATURE_VALUE, String CONTROL_ATTR, String CONTROL_ATTR_VALUE,
                                                              String VALUE, String columnName) throws SQLException, NamingException {
        AccessDataControl accessDC = new AccessDataControl();
        List<String> result = null;
        log.info(accessDC.getDisplayRecord() + "DAOFactory.getSingleColumnDataFromFeature before mapping:FEATURE_NAME<" + FEATURE_NAME + ">FEATURE_VALUE<" +
                 FEATURE_VALUE + ">CONTROL_ATTR<" + CONTROL_ATTR + ">CONTROL_ATTR_VALUE<" + CONTROL_ATTR_VALUE + ">VALUE<" + VALUE + ">columnName<" +
                 columnName);

        List<String> params = new ArrayList<String>();

        FEATURE_NAME = (FEATURE_NAME == null ? "%" : FEATURE_NAME);
        FEATURE_VALUE = (FEATURE_VALUE == null ? "%" : FEATURE_VALUE);
        CONTROL_ATTR = (CONTROL_ATTR == null ? "%" : CONTROL_ATTR);
        CONTROL_ATTR_VALUE = (CONTROL_ATTR_VALUE == null ? "%" : CONTROL_ATTR_VALUE);
        VALUE = (VALUE == null ? "%" : VALUE);

        log.info(accessDC.getDisplayRecord() + "DAOFactory.getSingleColumnDataFromFeature after mapping:FEATURE_NAME<" + FEATURE_NAME + ">FEATURE_VALUE<" +
                 FEATURE_VALUE + ">CONTROL_ATTR<" + CONTROL_ATTR + ">CONTROL_ATTR_VALUE<" + CONTROL_ATTR_VALUE + ">VALUE<" + VALUE + ">columnName<" +
                 columnName);

        params.add(FEATURE_NAME);
        params.add(FEATURE_VALUE);
        params.add(CONTROL_ATTR);
        params.add(CONTROL_ATTR_VALUE);
        params.add(VALUE);


        result =
                executeSqlStmt("select * from prt_gen_feature where feature_name like ? and feature_value like ? and control_attr like ? and control_attr_value like ? and value like ?",
                               params, columnName);
        log.info(accessDC.getDisplayRecord() + "DAOFactory.getBranchPlantCountry : " + "country in DAOFactory:" + columnName);
        return result;
    }

    /**
     * @param sqlQuery
     * @param params
     * This method is used to create User object fetched from the database for the entered user email/short id.
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public static User executeSqlQuery(String userEmail) throws SQLException, NamingException {

        AccessDataControl accessDC = new AccessDataControl();
        log.fine(accessDC.getDisplayRecord() + " Inside executeSqlQuery method to ceate user object from DB");
        PreparedStatement objPrepStmt = null;
        PreparedStatement objPrepStmt1 = null;
        PreparedStatement objPrepStmt2 = null;
        ResultSet objRS = null;
        ResultSet resultRs1 = null;
        ResultSet resultRs2 = null;
        Map<String, String> result = new HashMap<String, String>();
        Connection connection = null;

        List<String> listOfRoles = new ArrayList<String>();

        User user = new User();
        List<Roles> myRoleList = new ArrayList<Roles>();
        Roles roles = new Roles();
        String country = null;
        String rolesString = "";

        try {
            connection = getEngageJNDIConnection();
            if (null != connection) {
                List<String> params = new ArrayList<String>();
                List<String> params1 = new ArrayList<String>();
                //List<String> params2 = new ArrayList<String>();
                //params.add(userEmail.trim());
                //params1.add(userEmail.trim());
                //params2.add(userEmail.trim());
                String sqlStatement = null;
                if (userEmail.trim().contains("@")) {
                    params.add(userEmail.trim());
                    sqlStatement =
                            "SELECT USER_EMAIL,USER_FIRST_NAME,USER_MIDDLE_NAME,USER_LAST_NAME,USER_DOB,USER_PHONE_NO,USER_LANG,MODIFIED_BY,COUNTRY_CODE" +
                            ",USER_SHORTNAME,USER_STATUS FROM PRT_CARD_USER_INFORMATION WHERE trim(USER_EMAIL) = ? AND USER_STATUS ='ACTIVE' ";

                } else {
                    params.add(userEmail.trim());
                    sqlStatement =
                            "SELECT USER_EMAIL,USER_FIRST_NAME,USER_MIDDLE_NAME,USER_LAST_NAME,USER_DOB,USER_PHONE_NO,USER_LANG,MODIFIED_BY,COUNTRY_CODE" +
                            ",USER_SHORTNAME,USER_STATUS FROM PRT_CARD_USER_INFORMATION WHERE trim(USER_SHORTNAME) = ? AND USER_STATUS ='ACTIVE' ";
                }
                objPrepStmt = connection.prepareStatement(sqlStatement);
                if (null != objPrepStmt) {
                    for (int iCount = 0; iCount < params.size(); iCount++) {
                        objPrepStmt.setString(iCount + 1, params.get(iCount));
                    }
                    objRS = objPrepStmt.executeQuery();

                    while (objRS.next()) {
                        userEmail = objRS.getString("USER_EMAIL");
                        country = objRS.getString("COUNTRY_CODE");
                        if (objRS.getString("USER_EMAIL") != null) {
                            user.setEmailID(objRS.getString("USER_EMAIL"));
                        }
                        if (objRS.getString("USER_FIRST_NAME") != null) {
                            user.setFirstName(objRS.getString("USER_FIRST_NAME"));
                        }
                        if (objRS.getString("USER_MIDDLE_NAME") != null) {
                            user.setMiddleName(objRS.getString("USER_MIDDLE_NAME"));
                        }
                        if (objRS.getString("USER_LAST_NAME") != null) {
                            user.setLastName(objRS.getString("USER_LAST_NAME"));
                        }
                        if (objRS.getDate(5) != null) {
                            user.setDob(objRS.getDate(5));
                        }
                        if (objRS.getString("USER_PHONE_NO") != null) {
                            user.setPhoneNumber(objRS.getString("USER_PHONE_NO"));
                        }
                        if (objRS.getString("USER_LANG") != null) {
                            user.setLang(objRS.getString("USER_LANG"));
                        }
                        if (objRS.getString("COUNTRY_CODE") != null) {
                            user.setCountry(objRS.getString("COUNTRY_CODE"));
                        }
                        if (objRS.getString("USER_SHORTNAME") != null) {
                            user.setUserID(objRS.getString("USER_SHORTNAME"));
                        }
                        if (objRS.getString("USER_STATUS") != null) {
                            user.setActive(objRS.getString("USER_STATUS").toString().equalsIgnoreCase("enabled") ? true : false);
                        }
                    }
                }
                params1.add(userEmail.trim());
                params1.add(country);

                String sqlStatement1 = null;
                sqlStatement1 = "SELECT DISTINCT USER_ROLE FROM PRT_CARD_USER_ROLE_MAPPING where trim(USER_EMAIL) = ? and  trim(country_code) = ? ";
                objPrepStmt1 = connection.prepareStatement(sqlStatement1);
                if (null != objPrepStmt1) {
                    for (int iCount = 0; iCount < params1.size(); iCount++) {
                        objPrepStmt1.setString(iCount + 1, params1.get(iCount));
                    }
                    resultRs1 = objPrepStmt1.executeQuery();

                    while (resultRs1.next()) {
                        String roleName = null;
                        String roleAssociationType = null;
                        List<String> params2 = new ArrayList<String>();
                        List<String> roleAssociation = new ArrayList<String>();
                        roleName = resultRs1.getString("USER_ROLE");
                        log.info(accessDC.getDisplayRecord() + " Inside executeSqlQuery method +++++ Role names identifed for user " + roleName);
                        if (rolesString != null && rolesString.length() > 0) {
                            rolesString = rolesString + roleName + "|";
                        } else {
                            rolesString = "|" + rolesString + roleName + "|";
                        }
                        params2.add(userEmail.trim());
                        params2.add(country);
                        params2.add(roleName);
                        listOfRoles.add(roleName);

                        String sqlStatement2 = null;
                        sqlStatement2 =
                                "SELECT US_ROLE_ID,USER_EMAIL,USER_ROLE,MODIFIED_BY,MODIFIED_DATE,USER_ASSOCIATION,COUNTRY_CODE,ASSOCIATION_TYPE,PARTNER_ID" +
                                " FROM PRT_CARD_USER_ROLE_MAPPING WHERE trim(USER_EMAIL) = ? AND trim(country_code) = ? AND trim(USER_ROLE) = ? ";
                        objPrepStmt2 = connection.prepareStatement(sqlStatement2);
                        if (null != objPrepStmt2) {
                            for (int iCount = 0; iCount < params2.size(); iCount++) {
                                objPrepStmt2.setString(iCount + 1, params2.get(iCount));
                            }

                            resultRs2 = objPrepStmt2.executeQuery();

                            while (resultRs2.next()) {
                                roles = new Roles();
                                String roleAssocList = null;
                                if (resultRs2.getString("ASSOCIATION_TYPE") != null && resultRs2.getString("ASSOCIATION_TYPE").equals("PP") &&
                                    resultRs2.getString("USER_ASSOCIATION") != null) {
                                    roleAssocList = country + "PP" + resultRs2.getString("USER_ASSOCIATION").toString();
                                    roleAssociation.add(roleAssocList);
                                    roleAssociationType = resultRs2.getString("ASSOCIATION_TYPE");

                                } else if (resultRs2.getString("ASSOCIATION_TYPE") != null && resultRs2.getString("ASSOCIATION_TYPE").equals("AC") &&
                                           resultRs2.getString("USER_ASSOCIATION") != null && resultRs2.getString("PARTNER_ID") != null) {
                                    roleAssocList =
                                            country + "PP" + resultRs2.getString("PARTNER_ID") + "AC" + resultRs2.getString("USER_ASSOCIATION").toString();
                                    roleAssociation.add(roleAssocList);
                                    roleAssociationType = resultRs2.getString("ASSOCIATION_TYPE");

                                } else if (resultRs2.getString("ASSOCIATION_TYPE") != null && resultRs2.getString("ASSOCIATION_TYPE").equals("CG") &&
                                           resultRs2.getString("USER_ASSOCIATION") != null && resultRs2.getString("PARTNER_ID") != null) {
                                    roleAssocList =
                                            country + "PP" + resultRs2.getString("PARTNER_ID") + "CG" + resultRs2.getString("USER_ASSOCIATION").toString();
                                    roleAssociation.add(roleAssocList);
                                    roleAssociationType = resultRs2.getString("ASSOCIATION_TYPE");

                                } else if (resultRs2.getString("ASSOCIATION_TYPE") != null && resultRs2.getString("ASSOCIATION_TYPE").equals("CC") &&
                                           resultRs2.getString("USER_ASSOCIATION") != null && resultRs2.getString("PARTNER_ID") != null) {
                                    roleAssocList =
                                            country + "PP" + resultRs2.getString("PARTNER_ID") + "CC" + resultRs2.getString("USER_ASSOCIATION").toString();
                                    roleAssociation.add(roleAssocList);
                                    roleAssociationType = resultRs2.getString("ASSOCIATION_TYPE");

                                } else if (resultRs2.getString("USER_ASSOCIATION") != null && resultRs2.getString("ASSOCIATION_TYPE").equals("CSR")) {
                                    roleAssocList = country + resultRs2.getString("USER_ASSOCIATION").toString();
                                    roleAssociation.add(roleAssocList);
                                    roleAssociationType = resultRs2.getString("ASSOCIATION_TYPE");
                                } else {
                                    roleAssocList = "";
                                    roleAssociation.add(roleAssocList);
                                    roleAssociationType = "";
                                }

                            }
                        }
                        roles.setRoleName(roleName);
                        roles.setIdString(roleAssociation);
                        roles.setEngageRoleTypeAssociation(roleAssociationType);
                        myRoleList.add(roles);
                    }

                    user.setRolelist(rolesString);
                    user.setRoleList(myRoleList);
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
        return user;
    }
}

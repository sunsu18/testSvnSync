package com.sfr.engage.services.core.dao.factory;

import com.sfr.engage.services.client.ucm.UCMCustomWeb;

import com.sfr.services.core.dao.factory.WebServiceProxy;
import com.sfr.util.AccessDataControl;
import com.sfr.util.ConfigurationUtility;
import com.sfr.util.constants.Constants;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import javax.xml.ws.BindingProvider;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;

public class DAOFactory implements Serializable{

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    
    public DAOFactory() {
        super();
    }
    
    
    public static String getPropertyValue(String PName) {
        return ConfigurationUtility.getPropertyValue(PName);
    }
    
    public UCMCustomWeb getUCMService()
    {
        Class<UCMCustomWeb> serviceEndPoint = UCMCustomWeb.class;
        String wsdlUrl = getPropertyValue(Constants.ENGAGE_UCM_WSDL_URL);
        String targetNamespace = "http://ws.wcc.lnt.com/";
        String serviceName = "UCMCustomWebService";
        String portName = "UCMCustomWebPort";
        String ucmUsername = getPropertyValue(Constants.ENGAGE_UCM_USERNAME);
        String ucmPassword = getPropertyValue(Constants.ENGAGE_UCM_PASSWORD);
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
    }

    /**
     * This method is used to get JNDI connection for processing business logic
     * @return
     * @throws SQLException
     * @throws NamingException
     */
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
            datasource = (DataSource)objinitialContext.lookup("jdbc/engagecustom");

            if (datasource != null) {
                connection = datasource.getConnection();
                System.out.println("datasource found");
            } else
                System.out.println("No data source");

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
}

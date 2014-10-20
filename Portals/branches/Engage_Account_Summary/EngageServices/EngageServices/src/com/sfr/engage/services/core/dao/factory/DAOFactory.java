package com.sfr.engage.services.core.dao.factory;


import com.sfr.engage.services.client.alertsSubscription.Subscription;
import com.sfr.engage.services.client.ucm.UCMCustomWeb;
import com.sfr.services.core.dao.factory.WebServiceProxy;
import com.sfr.util.ConfigurationUtility;
import com.sfr.util.constants.Constants;

import java.net.MalformedURLException;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import javax.xml.ws.BindingProvider;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;


public class DAOFactory {


    public DAOFactory() {
        super();
    }


    /**
     * @param pName
     * @return
     */
    public static String getPropertyValue(String pName) {
        return ConfigurationUtility.getPropertyValue(pName);
    }

    /**
     * @return
     */
    public UCMCustomWeb getUCMService() {
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

        }
        return uCMCustomWeb;
    }

    /**
     * This method is used to get JNDI connection for processing business logic.
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public static Connection getJNDIConnection() throws SQLException, NamingException {

        Connection connection = null;
        InitialContext objinitialContext = null;
        DataSource datasource = null;

        try {
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

            objinitialContext = new InitialContext(env);

            datasource = (DataSource)objinitialContext.lookup("jdbc/engagecustom");

            if (datasource != null) {
                connection = datasource.getConnection();

            } else {

            }

        } catch (SQLException e) {

        } catch (NamingException e) {

        } finally {
            try {
                if (objinitialContext != null) {
                    objinitialContext.close();
                }
            } catch (NamingException ne) {

            }
        }
        return connection;
    }


    public Subscription getAlertSubscriptionService() {

        String wsdlUrl = "http://10.24.240.15:7021/soa-infra/services/CommonUtilities/SOARuleEngineSubscription/subscription_client_ep?WSDL";
        String targetNamespace = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription/Subscription";
        String serviceName = "subscription_client_ep";
        String portName = "Subscription_pt";

        Subscription subscriberRegistration = null;
        Class<Subscription> serviceEndPoint = Subscription.class;
        try {
            WebServiceProxy client = new WebServiceProxy(wsdlUrl, serviceName, targetNamespace);
            subscriberRegistration = client.getServicePort(portName, serviceEndPoint);
        } catch (MalformedURLException e) {

        }
        return subscriberRegistration;
    }
}

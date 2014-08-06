package com.sfr.services.core.dao.factory;


import com.sfr.services.client.proxy.user.OIMUserManagermentImpl;
import com.sfr.util.ConfigurationUtility;
import com.sfr.util.constants.Constants;

import java.net.MalformedURLException;

import javax.xml.ws.BindingProvider;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;


public class EngageDAOFactory {
    public static EngageDAOFactory factory = null;

    public EngageDAOFactory() {
        super();
    }

    public OIMUserManagermentImpl getEngOIMUserManagermentImpl() {
        Class<OIMUserManagermentImpl> serviceEndPoint =
            OIMUserManagermentImpl.class;
        String wsdlUrl =
            new EngageDAOFactory().getPropertyValue(Constants.ENGAGE_OIM_WSDL_URL);
        String targetNamespace = "http://ws.oim.sfr.com/";
        String serviceName = "OIMUserManagermentImpl";
        String portName = "OIMUserManagermentImplPort";

        OIMUserManagermentImpl oIMUserManagermentImpl = null;
        try {
            WebServiceProxy client =
                new WebServiceProxy(wsdlUrl, serviceName, targetNamespace);
            SecurityPoliciesFeature securityFeatures =
                new SecurityPoliciesFeature(new String[] { "oracle/wss_username_token_client_policy" });
            oIMUserManagermentImpl =
                    client.getServicePortWithFeatures(portName,
                                                      serviceEndPoint,
                                                      securityFeatures);
            ((BindingProvider)oIMUserManagermentImpl).getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
                                                                              getPropertyValue("OIM_WSDL_USERNAME"));
            ((BindingProvider)oIMUserManagermentImpl).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
                                                                              getPropertyValue("OIM_WSDL_PASSWORD"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return oIMUserManagermentImpl;
    }

    public static String getPropertyValue(String PName) {
        return ConfigurationUtility.getPropertyValue(PName);
    }

    public static EngageDAOFactory getInstance() {

        if (null == factory) {
            factory = new EngageDAOFactory();
        }

        return factory;
    }
}

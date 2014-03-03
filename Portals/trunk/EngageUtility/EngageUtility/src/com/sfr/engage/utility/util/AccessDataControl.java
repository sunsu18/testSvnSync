package com.sfr.engage.utility.util;

import com.sfr.engage.utility.constant.Constants;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.share.logging.ADFLogger;


public class AccessDataControl {

    public AccessDataControl() {
        super();
    }

    /* This method is to get the Log Handler depending upon the portal being run,
       On every backing bean we have to call this method to get the Adf logger */


    public static ADFLogger getSFRLogger() {

        HttpSession session = null;
        ExternalContext ectx;
        HttpServletRequest request;
        String portal = null;
        if (FacesContext.getCurrentInstance() != null) {
            ectx = FacesContext.getCurrentInstance().getExternalContext();
            if (ectx != null) {
                request = (HttpServletRequest)ectx.getRequest();
                if (request != null) {
                    session = request.getSession(false);
                    //read the portal being executed from user's session
                    if (session != null) {
                        if (session.getAttribute(Constants.SESSION_PORTAL_NAME) != null) {
                            portal = (String)session.getAttribute(Constants.SESSION_PORTAL_NAME);
                        }
                    }
                }
            }
        }
        ADFLogger logger = null;
        if (portal == null) {
            portal = Constants.CARD_PORTAL;
        }
        if (portal.equals(Constants.CARD_PORTAL)) {
            logger = ADFLogger.createADFLogger(ConfigurationUtility.getPropertyValue("LOGGER_CARD"));
        } else if (portal.equals(Constants.JET_PORTAL)) {
            logger = ADFLogger.createADFLogger(ConfigurationUtility.getPropertyValue("LOGGER_JET"));
        } else {
            logger = ADFLogger.createADFLogger(ConfigurationUtility.getPropertyValue("LOGGER_PETRO"));
        }

        return logger;
    }


}

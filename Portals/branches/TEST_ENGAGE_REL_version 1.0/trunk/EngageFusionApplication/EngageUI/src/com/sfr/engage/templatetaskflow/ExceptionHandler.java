package com.sfr.engage.templatetaskflow;


import com.sfr.util.AccessDataControl;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.share.logging.ADFLogger;


public class ExceptionHandler {
    public static final ADFLogger LOGGER = AccessDataControl.getSFRLogger();
    private AccessDataControl accessDC = new AccessDataControl();

    public ExceptionHandler() {
    }

    public void controllerExceptionHandler() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside Exception Handeler");
        FacesMessage message = new FacesMessage("This is custom Message for Jbo Exception-Exception Handeler");
        message.setSeverity(FacesMessage.SEVERITY_WARN);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);
    }

    public void setAccessDC(AccessDataControl accessDC) {
        this.accessDC = accessDC;
    }

    public AccessDataControl getAccessDC() {
        return accessDC;
    }
}

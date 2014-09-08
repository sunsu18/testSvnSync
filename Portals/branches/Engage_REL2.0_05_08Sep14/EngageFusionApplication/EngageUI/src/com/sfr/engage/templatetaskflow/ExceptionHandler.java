package com.sfr.engage.templatetaskflow;

import com.sfr.util.AccessDataControl;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.share.logging.ADFLogger;

public class ExceptionHandler {
    public static final ADFLogger _logger = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl(); 
    
    public ExceptionHandler() {
    }

    public void controllerExceptionHandler() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside Exception Handeler");
        FacesMessage message = new FacesMessage("This is custom Message for Jbo Exception-Exception Handeler");
        message.setSeverity(FacesMessage.SEVERITY_WARN);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);
    }
}

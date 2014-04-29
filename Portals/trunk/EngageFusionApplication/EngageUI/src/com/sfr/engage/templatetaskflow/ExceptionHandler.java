package com.sfr.engage.templatetaskflow;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class ExceptionHandler {
    public ExceptionHandler() {
    }

    public void controllerExceptionHandler() {
        // Add event code here...
        System.out.println("Inside Handeler");
        FacesMessage message = new FacesMessage("This is custom Message for Jbo Exception-Exception Handeler");
        message.setSeverity(FacesMessage.SEVERITY_WARN);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);
    }
}

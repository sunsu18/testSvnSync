package com.sfr.util;

import oracle.adf.share.logging.ADFLogger;

public class SFRException extends Exception {

    private String errorMessage;
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();

    public SFRException(Throwable throwable) {
        super(throwable);
    }

    public SFRException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SFRException(String errorMessage) {
        log.info(AccessDataControl.getDisplayRecord() +
                 "SFRException.SFRException : " + "---ERROR MESSAGE--" +
                 errorMessage);
        this.errorMessage = errorMessage;
    }

    public SFRException() {
        super();
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

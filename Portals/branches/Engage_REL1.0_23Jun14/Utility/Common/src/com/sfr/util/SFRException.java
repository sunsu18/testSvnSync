package com.sfr.util;

public class SFRException extends Exception {
    
    private String errorMessage;
    public SFRException(Throwable throwable) {
        super(throwable);
    }

    public SFRException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SFRException(String errorMessage) {
        System.out.println(AccessDataControl.getDisplayRecord()+"SFRException.SFRException : "+"---ERROR MESSAGE--"+errorMessage);            
        this.errorMessage=errorMessage;
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

package com.sfr.core.bean;

import com.sfr.util.ThreadSerialization;



public class BusinessError extends ThreadSerialization{
    private static final long serialVersionUID = 1L;
    private String errorMessage;
    private String errorCode;
    private String severity;

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getSeverity() {
        return severity;
    }
}

package com.sfr.engage.core;

import java.math.BigInteger;

public class AlertsSubscribeCustomerType {
    public AlertsSubscribeCustomerType() {
        super();
    }
    
    private String customerID;

    private String fIrstName;

    private String lastName;

    private String emailID;

    private BigInteger mobileNumber;

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setFIrstName(String fIrstName) {
        this.fIrstName = fIrstName;
    }

    public String getFIrstName() {
        return fIrstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setMobileNumber(BigInteger mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public BigInteger getMobileNumber() {
        return mobileNumber;
    }
}

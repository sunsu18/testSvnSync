package com.sfr.engage.core;

import java.math.BigInteger;



public class AlertsSubscribeRequest {
    public AlertsSubscribeRequest() {
        super();
    }
    
    private BigInteger ruleID;
    
    private AlertsSubscribeCustomerType customer;

    private AlertsSubscribeFrequencyType subscribeFrequency;
  
    private String notificationChannel;
 
    private String notificationFormat;


    public void setRuleID(BigInteger ruleID) {
        this.ruleID = ruleID;
    }

    public BigInteger getRuleID() {
        return ruleID;
    }

    public void setCustomer(AlertsSubscribeCustomerType customer) {
        this.customer = customer;
    }

    public AlertsSubscribeCustomerType getCustomer() {
        return customer;
    }

    public void setSubscribeFrequency(AlertsSubscribeFrequencyType subscribeFrequency) {
        this.subscribeFrequency = subscribeFrequency;
    }

    public AlertsSubscribeFrequencyType getSubscribeFrequency() {
        return subscribeFrequency;
    }

    public void setNotificationChannel(String notificationChannel) {
        this.notificationChannel = notificationChannel;
    }

    public String getNotificationChannel() {
        return notificationChannel;
    }

    public void setNotificationFormat(String notificationFormat) {
        this.notificationFormat = notificationFormat;
    }

    public String getNotificationFormat() {
        return notificationFormat;
    }
}

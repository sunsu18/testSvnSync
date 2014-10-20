package com.sfr.engage.core;


public class AlertsUnsubscribeRequest {
    public AlertsUnsubscribeRequest() {
        super();
    }


    private String subscriptionID;

    private String customerID;

    public void setSubscriptionID(String subscriptionID) {
        this.subscriptionID = subscriptionID;
    }

    public String getSubscriptionID() {
        return subscriptionID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerID() {
        return customerID;
    }
}

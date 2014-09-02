package com.sfr.engage.services.core.dao;

import com.sfr.engage.core.AlertsSubscribeRequest;
import com.sfr.engage.core.AlertsSubscribeResponse;
import com.sfr.engage.services.client.alerts.SubscriberRegistration;
import com.sfr.engage.services.client.alerts.type.CustomerType;
import com.sfr.engage.services.client.alerts.type.SubscribeFrequencyType;
import com.sfr.engage.services.client.alerts.type.SubscribeRequestType;
import com.sfr.engage.services.client.alerts.type.SubscribeResponseType;
import com.sfr.engage.services.core.dao.factory.DAOFactory;

public class AlertsDao {
    public AlertsDao() {
        super();
    }
    private SubscriberRegistration subscribeAlerts; 
    ;
    
    public AlertsSubscribeResponse subscribeAlerts(AlertsSubscribeRequest subscribeRequest) {
        AlertsSubscribeResponse serviceResponse = new AlertsSubscribeResponse();
        
        if(subscribeRequest!=null) {
            DAOFactory factory = new DAOFactory();
                    subscribeAlerts = factory.setAlerts();
            

              SubscribeFrequencyType subscribeFrequencyType = new SubscribeFrequencyType();
              CustomerType customerType = new CustomerType();
              SubscribeRequestType subscribeRequestType = new SubscribeRequestType();
              SubscribeResponseType subscribeResponseType = new SubscribeResponseType();
            
            
            
            
            if(subscribeRequest.getCustomer()!=null) {
                customerType.setCustomerID(subscribeRequest.getCustomer().getCustomerID());
                customerType.setEmailID(subscribeRequest.getCustomer().getEmailID());
                customerType.setFIrstName(subscribeRequest.getCustomer().getFIrstName());
                customerType.setLastName(subscribeRequest.getCustomer().getLastName());
                customerType.setMobileNumber(subscribeRequest.getCustomer().getMobileNumber());
                subscribeRequestType.setCustomer(customerType);
            }
            
            if(subscribeRequest.getSubscribeFrequency()!=null) {
                subscribeFrequencyType.setScheduleDate(subscribeRequest.getSubscribeFrequency().getScheduleDate());
                subscribeFrequencyType.setScheduleDay(subscribeRequest.getSubscribeFrequency().getScheduleDay());
                subscribeFrequencyType.setScheduleFrequency(subscribeRequest.getSubscribeFrequency().getScheduleFrequency());
                subscribeFrequencyType.setScheduleMonth(subscribeRequest.getSubscribeFrequency().getScheduleMonth());
                subscribeRequestType.setSubscribeFrequency(subscribeFrequencyType);
            }
            
            
            subscribeRequestType.setNotificationChannel(subscribeRequest.getNotificationChannel());
            subscribeRequestType.setNotificationFormat(subscribeRequest.getNotificationFormat());
            subscribeRequestType.setRuleID(subscribeRequest.getRuleID());
            
            
            try{
                subscribeResponseType = subscribeAlerts.process(subscribeRequestType);    
            }
            catch(Exception e) {
                System.out.println("Exception  " + e.getMessage());
            }
            
            
            serviceResponse.setSubscriptionID(subscribeResponseType.getSubscriptionID());    

        }
        return serviceResponse;
            
        
    }
}

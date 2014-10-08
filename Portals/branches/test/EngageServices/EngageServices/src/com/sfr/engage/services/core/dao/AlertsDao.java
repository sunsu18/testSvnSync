package com.sfr.engage.services.core.dao;


import com.sfr.engage.core.AlertsSubscribeRequest;
import com.sfr.engage.core.AlertsSubscribeResponse;
import com.sfr.engage.core.AlertsUnsubscribeRequest;
import com.sfr.engage.core.AlertsUnsubscribeResponse;
import com.sfr.engage.services.client.alertsSubscription.Subscription;
import com.sfr.engage.services.client.alertsSubscription.type.CustomerType;
import com.sfr.engage.services.client.alertsSubscription.type.SubscribeCollectionType;
import com.sfr.engage.services.client.alertsSubscription.type.SubscribeFrequencyType;
import com.sfr.engage.services.client.alertsSubscription.type.SubscribeRequestType;
import com.sfr.engage.services.client.alertsSubscription.type.SubscribeResponseCollectionType;
import com.sfr.engage.services.client.alertsSubscription.type.SubscribeResponseType;
import com.sfr.engage.services.client.alertsSubscription.type.UnsubscribeCollectionType;
import com.sfr.engage.services.client.alertsSubscription.type.UnsubscribeRequestType;
import com.sfr.engage.services.client.alertsSubscription.type.UnsubscribeResponseCollectionType;
import com.sfr.engage.services.client.alertsSubscription.type.UnsubscribeResponseType;
import com.sfr.engage.services.core.dao.factory.DAOFactory;
import com.sfr.util.AccessDataControl;

import java.util.List;

import oracle.adf.share.logging.ADFLogger;


public class AlertsDao {

    public static final ADFLogger log = AccessDataControl.getSFRLogger();

    public AlertsDao() {
        super();
    }
    //private SubscriberRegistration alertSubscriptionService;
    private Subscription alertSubscriptionService;

    public AlertsSubscribeResponse subscribeAlerts(AlertsSubscribeRequest subscribeRequest) {
        AlertsSubscribeResponse serviceResponse = new AlertsSubscribeResponse();
        SubscribeCollectionType request = new SubscribeCollectionType();

        if (subscribeRequest != null) {
            DAOFactory factory = new DAOFactory();
            alertSubscriptionService = factory.getAlertSubscriptionService();

            SubscribeFrequencyType subscribeFrequencyType = new SubscribeFrequencyType();
            CustomerType customerType = new CustomerType();
            SubscribeRequestType subscribeRequestType = new SubscribeRequestType();
            SubscribeResponseType subscribeResponseType = null;

            if (subscribeRequest.getCustomer() != null) {
                customerType.setCustomerID(subscribeRequest.getCustomer().getCustomerID());
                customerType.setEmailID(subscribeRequest.getCustomer().getEmailID());
                customerType.setFirstName(subscribeRequest.getCustomer().getFIrstName());
                customerType.setLastName(subscribeRequest.getCustomer().getLastName());
                customerType.setMobileNumber(subscribeRequest.getCustomer().getMobileNumber());
                request.setCustomer(customerType);
                //subscribeRequestType.setCustomer(customerType);

            }
            if (subscribeRequest.getSubscribeFrequency() != null) {
                subscribeFrequencyType.setScheduleDate(subscribeRequest.getSubscribeFrequency().getScheduleDate());
                subscribeFrequencyType.setScheduleDay(subscribeRequest.getSubscribeFrequency().getScheduleDay());
                subscribeFrequencyType.setScheduleFrequency(subscribeRequest.getSubscribeFrequency().getScheduleFrequency());
                subscribeFrequencyType.setScheduleMonth(subscribeRequest.getSubscribeFrequency().getScheduleMonth());
                request.setSubscribeFrequency(subscribeFrequencyType);
                //subscribeRequestType.setSubscribeFrequency(subscribeFrequencyType);
            }
            request.setNotificationChannel(subscribeRequest.getNotificationChannel());
            request.setNotificationFormat(subscribeRequest.getNotificationFormat());
            request.setRuleID(subscribeRequest.getRuleID());
            request.setIdentifier("1");
            // subscribeRequestType.getSubscribeCollection().set(0, request);
            SubscribeRequestType newReq = new SubscribeRequestType();

            newReq.getSubscribeCollection().add(request);

            //List<SubscribeCollectionType> collection = (List<SubscribeCollectionType>)subscribeRequestType.getSubscribeCollection().set(0, request);
            try {
                subscribeResponseType = alertSubscriptionService.subscribe(newReq);

            } catch (Exception e) {
                log.severe(e);
            }
            List<SubscribeResponseCollectionType> responseLIst = subscribeResponseType.getSubscribeCollection();
            for (int i = 0; i < responseLIst.size(); i++)

            {
                System.out.println("subs id " + responseLIst.get(i).getSubscriptionID());
                serviceResponse.setSubscriptionID(responseLIst.get(i).getSubscriptionID());
                break;
            }
            //serviceResponse.setSubscriptionID(subscribeResponseType.getSubscribeCollection().get(0).getSubscriptionID());
        }
        return serviceResponse;
    }


    public AlertsUnsubscribeResponse unsubscribeAlerts(AlertsUnsubscribeRequest unsubscribeRequest) {
        AlertsUnsubscribeResponse serviceResponse = new AlertsUnsubscribeResponse();
        UnsubscribeCollectionType requestCollection = new UnsubscribeCollectionType();
        UnsubscribeRequestType unsubRequest = new UnsubscribeRequestType();
        UnsubscribeResponseType UnsubscribeResponseTypeObj = new UnsubscribeResponseType();


        if (unsubscribeRequest != null) {
            DAOFactory factory = new DAOFactory();
            alertSubscriptionService = factory.getAlertSubscriptionService();


            if (unsubscribeRequest.getCustomerID() != null && unsubscribeRequest.getSubscriptionID() != null) {
                requestCollection.setCustomerID(unsubscribeRequest.getCustomerID());
                requestCollection.setSubscriptionID(unsubscribeRequest.getSubscriptionID());
                unsubRequest.getUnsubscribeCollection().add(requestCollection);


            }


            try {
                UnsubscribeResponseTypeObj = alertSubscriptionService.unsubscribe(unsubRequest);

            } catch (Exception e) {
                log.severe(e);
            }
            List<UnsubscribeResponseCollectionType> responseUnsubscribe = UnsubscribeResponseTypeObj.getUnsubscribeCollection();

            //List<SubscribeResponseCollectionType> responseLIst = UnsubscribeResponseTypeObj.getSubscribeCollection();
            for (int i = 0; i < responseUnsubscribe.size(); i++) {
                System.out.println("status " + responseUnsubscribe.get(i).getStatus());
                serviceResponse.setStatus(responseUnsubscribe.get(i).getStatus());
            }
//            for (int i = 0; i < responseLIst.size(); i++)
            //
            //            {
            //                System.out.println("subs id " + responseLIst.get(i).getSubscriptionID());
            //                serviceResponse.setSubscriptionID(responseLIst.get(i).getSubscriptionID());
            //                break;
            //            }
            //serviceResponse.setSubscriptionID(subscribeResponseType.getSubscribeCollection().get(0).getSubscriptionID());
        }
        return serviceResponse;
    }
}

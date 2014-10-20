package com.sfr.engage.services.client.alertsSubscription;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;


// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (11.1.1.0.0, build 111209.0821.28162)

@WebService(wsdlLocation = "http://10.24.240.15:7021/soa-infra/services/CommonUtilities/SOARuleEngineSubscription/subscription_client_ep?WSDL",
            targetNamespace = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription/Subscription", name = "Subscription")
@XmlSeeAlso( { com.sfr.engage.services.client.alertsSubscription.type.ObjectFactory.class })
@SOAPBinding(style = Style.DOCUMENT, parameterStyle = ParameterStyle.BARE)
public interface Subscription {


    @WebMethod(action = "subscribe")
    @SOAPBinding(parameterStyle = ParameterStyle.BARE)
    @Action(input = "subscribe",
            fault = { @FaultAction(value = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription/Subscription/Subscription/subscribe/Fault/Fault",
                                   className = com.sfr.engage.services.client.alertsSubscription.FaultResponseMessage.class) },
            output = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription/Subscription/Subscription/subscribeResponse")
    @WebResult(targetNamespace = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription", partName = "payload", name = "SubscribeResponse")
    public com.sfr.engage.services.client.alertsSubscription.type.SubscribeResponseType subscribe(@WebParam(targetNamespace =
                                                                                                            "http://www.lntinfotech.com/integration/SOARuleEngineSubscription",
                                                                                                            partName = "payload", name = "SubscribeRequest")
        com.sfr.engage.services.client.alertsSubscription.type.SubscribeRequestType payload) throws com.sfr.engage.services.client.alertsSubscription.FaultResponseMessage;

    @WebMethod(action = "unsubscribe")
    @SOAPBinding(parameterStyle = ParameterStyle.BARE)
    @Action(input = "unsubscribe",
            fault = { @FaultAction(value = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription/Subscription/Subscription/unsubscribe/Fault/Fault",
                                   className = com.sfr.engage.services.client.alertsSubscription.FaultResponseMessage.class) },
            output = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription/Subscription/Subscription/unsubscribeResponse")
    @WebResult(targetNamespace = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription", partName = "payload", name = "UnsubscribeResponse")
    public com.sfr.engage.services.client.alertsSubscription.type.UnsubscribeResponseType unsubscribe(@WebParam(targetNamespace =
                                                                                                                "http://www.lntinfotech.com/integration/SOARuleEngineSubscription",
                                                                                                                partName = "payload",
                                                                                                                name = "UnsubscribeRequest")
        com.sfr.engage.services.client.alertsSubscription.type.UnsubscribeRequestType payload) throws com.sfr.engage.services.client.alertsSubscription.FaultResponseMessage;

    @WebMethod(action = "update")
    @SOAPBinding(parameterStyle = ParameterStyle.BARE)
    @Action(input = "update",
            fault = { @FaultAction(value = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription/Subscription/Subscription/update/Fault/Fault",
                                   className = com.sfr.engage.services.client.alertsSubscription.FaultResponseMessage.class) },
            output = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription/Subscription/Subscription/updateResponse")
    @WebResult(targetNamespace = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription", partName = "payload", name = "UpdateResponse")
    public com.sfr.engage.services.client.alertsSubscription.type.UpdateResponseType update(@WebParam(targetNamespace =
                                                                                                      "http://www.lntinfotech.com/integration/SOARuleEngineSubscription",
                                                                                                      partName = "payload", name = "UpdateRequest")
        com.sfr.engage.services.client.alertsSubscription.type.UpdateRequestType payload) throws com.sfr.engage.services.client.alertsSubscription.FaultResponseMessage;
}

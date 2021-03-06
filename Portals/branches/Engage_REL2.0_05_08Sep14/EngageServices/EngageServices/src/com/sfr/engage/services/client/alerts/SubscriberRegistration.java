package com.sfr.engage.services.client.alerts;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (11.1.1.0.0, build 111209.0821.28162)

@WebService(targetNamespace="http://xmlns.oracle.com/SubscriberRegistrationApp/SubscriberRegistration/SubscriberRegistration",
  name="SubscriberRegistration")
@XmlSeeAlso(
  { com.sfr.engage.services.client.alerts.type.ObjectFactory.class })
@SOAPBinding(style=Style.DOCUMENT, parameterStyle=ParameterStyle.BARE)
public interface SubscriberRegistration
{
  @WebMethod(action="process")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @Action(input="process", output="http://xmlns.oracle.com/SubscriberRegistrationApp/SubscriberRegistration/SubscriberRegistration/SubscriberRegistration/processResponse")
  @WebResult(targetNamespace="http://www.statoilfuelretail.com/integration/engage/RuleEngineSubscribe",
    partName="payload", name="SubscribeResponse")
  public com.sfr.engage.services.client.alerts.type.SubscribeResponseType process(@WebParam(targetNamespace="http://www.statoilfuelretail.com/integration/engage/RuleEngineSubscribe",
      partName="payload", name="SubscribeRequest")
    com.sfr.engage.services.client.alerts.type.SubscribeRequestType payload);
}

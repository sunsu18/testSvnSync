
package com.sfr.engage.services.client.alertsSubscription.type;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sfr.engage.services.client.alertsSubscription.type package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Fault_QNAME = new QName("http://www.lntinfotech.com/integration/SOARuleEngineSubscription", "Fault");
    private final static QName _UpdateResponse_QNAME = new QName("http://www.lntinfotech.com/integration/SOARuleEngineSubscription", "UpdateResponse");
    private final static QName _UnsubscribeResponse_QNAME = new QName("http://www.lntinfotech.com/integration/SOARuleEngineSubscription", "UnsubscribeResponse");
    private final static QName _UpdateRequest_QNAME = new QName("http://www.lntinfotech.com/integration/SOARuleEngineSubscription", "UpdateRequest");
    private final static QName _UnsubscribeRequest_QNAME = new QName("http://www.lntinfotech.com/integration/SOARuleEngineSubscription", "UnsubscribeRequest");
    private final static QName _SubscribeRequest_QNAME = new QName("http://www.lntinfotech.com/integration/SOARuleEngineSubscription", "SubscribeRequest");
    private final static QName _SubscribeResponse_QNAME = new QName("http://www.lntinfotech.com/integration/SOARuleEngineSubscription", "SubscribeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sfr.engage.services.client.alertsSubscription.type
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SubscribeResponseType }
     * 
     */
    public SubscribeResponseType createSubscribeResponseType() {
        return new SubscribeResponseType();
    }

    /**
     * Create an instance of {@link SubscribeRequestType }
     * 
     */
    public SubscribeRequestType createSubscribeRequestType() {
        return new SubscribeRequestType();
    }

    /**
     * Create an instance of {@link UnsubscribeRequestType }
     * 
     */
    public UnsubscribeRequestType createUnsubscribeRequestType() {
        return new UnsubscribeRequestType();
    }

    /**
     * Create an instance of {@link UpdateRequestType }
     * 
     */
    public UpdateRequestType createUpdateRequestType() {
        return new UpdateRequestType();
    }

    /**
     * Create an instance of {@link UnsubscribeResponseType }
     * 
     */
    public UnsubscribeResponseType createUnsubscribeResponseType() {
        return new UnsubscribeResponseType();
    }

    /**
     * Create an instance of {@link UpdateResponseType }
     * 
     */
    public UpdateResponseType createUpdateResponseType() {
        return new UpdateResponseType();
    }

    /**
     * Create an instance of {@link FaultType }
     * 
     */
    public FaultType createFaultType() {
        return new FaultType();
    }

    /**
     * Create an instance of {@link SubscribeResponseCollectionType }
     * 
     */
    public SubscribeResponseCollectionType createSubscribeResponseCollectionType() {
        return new SubscribeResponseCollectionType();
    }

    /**
     * Create an instance of {@link CustomerType }
     * 
     */
    public CustomerType createCustomerType() {
        return new CustomerType();
    }

    /**
     * Create an instance of {@link ParametersType }
     * 
     */
    public ParametersType createParametersType() {
        return new ParametersType();
    }

    /**
     * Create an instance of {@link ParameterType }
     * 
     */
    public ParameterType createParameterType() {
        return new ParameterType();
    }

    /**
     * Create an instance of {@link SubscribeCollectionType }
     * 
     */
    public SubscribeCollectionType createSubscribeCollectionType() {
        return new SubscribeCollectionType();
    }

    /**
     * Create an instance of {@link SubscribeFrequencyType }
     * 
     */
    public SubscribeFrequencyType createSubscribeFrequencyType() {
        return new SubscribeFrequencyType();
    }

    /**
     * Create an instance of {@link UnsubscribeCollectionType }
     * 
     */
    public UnsubscribeCollectionType createUnsubscribeCollectionType() {
        return new UnsubscribeCollectionType();
    }

    /**
     * Create an instance of {@link UpdateSubscriptionCollectionType }
     * 
     */
    public UpdateSubscriptionCollectionType createUpdateSubscriptionCollectionType() {
        return new UpdateSubscriptionCollectionType();
    }

    /**
     * Create an instance of {@link UpdateResponseCollectionType }
     * 
     */
    public UpdateResponseCollectionType createUpdateResponseCollectionType() {
        return new UpdateResponseCollectionType();
    }

    /**
     * Create an instance of {@link UnsubscribeResponseCollectionType }
     * 
     */
    public UnsubscribeResponseCollectionType createUnsubscribeResponseCollectionType() {
        return new UnsubscribeResponseCollectionType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription", name = "Fault")
    public JAXBElement<FaultType> createFault(FaultType value) {
        return new JAXBElement<FaultType>(_Fault_QNAME, FaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription", name = "UpdateResponse")
    public JAXBElement<UpdateResponseType> createUpdateResponse(UpdateResponseType value) {
        return new JAXBElement<UpdateResponseType>(_UpdateResponse_QNAME, UpdateResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnsubscribeResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription", name = "UnsubscribeResponse")
    public JAXBElement<UnsubscribeResponseType> createUnsubscribeResponse(UnsubscribeResponseType value) {
        return new JAXBElement<UnsubscribeResponseType>(_UnsubscribeResponse_QNAME, UnsubscribeResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription", name = "UpdateRequest")
    public JAXBElement<UpdateRequestType> createUpdateRequest(UpdateRequestType value) {
        return new JAXBElement<UpdateRequestType>(_UpdateRequest_QNAME, UpdateRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnsubscribeRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription", name = "UnsubscribeRequest")
    public JAXBElement<UnsubscribeRequestType> createUnsubscribeRequest(UnsubscribeRequestType value) {
        return new JAXBElement<UnsubscribeRequestType>(_UnsubscribeRequest_QNAME, UnsubscribeRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription", name = "SubscribeRequest")
    public JAXBElement<SubscribeRequestType> createSubscribeRequest(SubscribeRequestType value) {
        return new JAXBElement<SubscribeRequestType>(_SubscribeRequest_QNAME, SubscribeRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.lntinfotech.com/integration/SOARuleEngineSubscription", name = "SubscribeResponse")
    public JAXBElement<SubscribeResponseType> createSubscribeResponse(SubscribeResponseType value) {
        return new JAXBElement<SubscribeResponseType>(_SubscribeResponse_QNAME, SubscribeResponseType.class, null, value);
    }

}

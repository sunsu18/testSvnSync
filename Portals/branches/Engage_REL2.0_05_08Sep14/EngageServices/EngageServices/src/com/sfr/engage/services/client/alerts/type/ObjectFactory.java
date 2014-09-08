
package com.sfr.engage.services.client.alerts.type;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sfr.engage.services.client.alerts.type package. 
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

    private final static QName _SubscribeRequest_QNAME = new QName("http://www.statoilfuelretail.com/integration/engage/RuleEngineSubscribe", "SubscribeRequest");
    private final static QName _SubscribeResponse_QNAME = new QName("http://www.statoilfuelretail.com/integration/engage/RuleEngineSubscribe", "SubscribeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sfr.engage.services.client.alerts.type
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
     * Create an instance of {@link ParametersType }
     * 
     */
    public ParametersType createParametersType() {
        return new ParametersType();
    }

    /**
     * Create an instance of {@link CustomerType }
     * 
     */
    public CustomerType createCustomerType() {
        return new CustomerType();
    }

    /**
     * Create an instance of {@link ParameterType }
     * 
     */
    public ParameterType createParameterType() {
        return new ParameterType();
    }

    /**
     * Create an instance of {@link SubscribeFrequencyType }
     * 
     */
    public SubscribeFrequencyType createSubscribeFrequencyType() {
        return new SubscribeFrequencyType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.statoilfuelretail.com/integration/engage/RuleEngineSubscribe", name = "SubscribeRequest")
    public JAXBElement<SubscribeRequestType> createSubscribeRequest(SubscribeRequestType value) {
        return new JAXBElement<SubscribeRequestType>(_SubscribeRequest_QNAME, SubscribeRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.statoilfuelretail.com/integration/engage/RuleEngineSubscribe", name = "SubscribeResponse")
    public JAXBElement<SubscribeResponseType> createSubscribeResponse(SubscribeResponseType value) {
        return new JAXBElement<SubscribeResponseType>(_SubscribeResponse_QNAME, SubscribeResponseType.class, null, value);
    }

}

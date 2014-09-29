
package com.sfr.engage.services.client.alertsSubscription.type;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SubscribeCollectionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SubscribeCollectionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Identifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RuleID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Customer" type="{http://www.lntinfotech.com/integration/SOARuleEngineSubscription}CustomerType"/>
 *         &lt;element name="SubscribeFrequency" type="{http://www.lntinfotech.com/integration/SOARuleEngineSubscription}SubscribeFrequencyType"/>
 *         &lt;element name="NotificationChannel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NotificationFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Parameters" type="{http://www.lntinfotech.com/integration/SOARuleEngineSubscription}ParametersType"/>
 *         &lt;element name="Custom1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Custom2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Custom3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Custom4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Custom5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubscribeCollectionType", propOrder = {
    "identifier",
    "ruleID",
    "customer",
    "subscribeFrequency",
    "notificationChannel",
    "notificationFormat",
    "parameters",
    "custom1",
    "custom2",
    "custom3",
    "custom4",
    "custom5"
})
@XmlSeeAlso({
    UpdateSubscriptionCollectionType.class
})
public class SubscribeCollectionType {

    @XmlElement(name = "Identifier", required = true)
    protected String identifier;
    @XmlElement(name = "RuleID", required = true)
    protected BigInteger ruleID;
    @XmlElement(name = "Customer", required = true)
    protected CustomerType customer;
    @XmlElement(name = "SubscribeFrequency", required = true)
    protected SubscribeFrequencyType subscribeFrequency;
    @XmlElement(name = "NotificationChannel", required = true)
    protected String notificationChannel;
    @XmlElement(name = "NotificationFormat", required = true)
    protected String notificationFormat;
    @XmlElement(name = "Parameters", required = true)
    protected ParametersType parameters;
    @XmlElement(name = "Custom1")
    protected String custom1;
    @XmlElement(name = "Custom2")
    protected String custom2;
    @XmlElement(name = "Custom3")
    protected String custom3;
    @XmlElement(name = "Custom4")
    protected String custom4;
    @XmlElement(name = "Custom5")
    protected String custom5;

    /**
     * Gets the value of the identifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the value of the identifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifier(String value) {
        this.identifier = value;
    }

    /**
     * Gets the value of the ruleID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRuleID() {
        return ruleID;
    }

    /**
     * Sets the value of the ruleID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRuleID(BigInteger value) {
        this.ruleID = value;
    }

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerType }
     *     
     */
    public CustomerType getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerType }
     *     
     */
    public void setCustomer(CustomerType value) {
        this.customer = value;
    }

    /**
     * Gets the value of the subscribeFrequency property.
     * 
     * @return
     *     possible object is
     *     {@link SubscribeFrequencyType }
     *     
     */
    public SubscribeFrequencyType getSubscribeFrequency() {
        return subscribeFrequency;
    }

    /**
     * Sets the value of the subscribeFrequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubscribeFrequencyType }
     *     
     */
    public void setSubscribeFrequency(SubscribeFrequencyType value) {
        this.subscribeFrequency = value;
    }

    /**
     * Gets the value of the notificationChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotificationChannel() {
        return notificationChannel;
    }

    /**
     * Sets the value of the notificationChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotificationChannel(String value) {
        this.notificationChannel = value;
    }

    /**
     * Gets the value of the notificationFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotificationFormat() {
        return notificationFormat;
    }

    /**
     * Sets the value of the notificationFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotificationFormat(String value) {
        this.notificationFormat = value;
    }

    /**
     * Gets the value of the parameters property.
     * 
     * @return
     *     possible object is
     *     {@link ParametersType }
     *     
     */
    public ParametersType getParameters() {
        return parameters;
    }

    /**
     * Sets the value of the parameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParametersType }
     *     
     */
    public void setParameters(ParametersType value) {
        this.parameters = value;
    }

    /**
     * Gets the value of the custom1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustom1() {
        return custom1;
    }

    /**
     * Sets the value of the custom1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustom1(String value) {
        this.custom1 = value;
    }

    /**
     * Gets the value of the custom2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustom2() {
        return custom2;
    }

    /**
     * Sets the value of the custom2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustom2(String value) {
        this.custom2 = value;
    }

    /**
     * Gets the value of the custom3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustom3() {
        return custom3;
    }

    /**
     * Sets the value of the custom3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustom3(String value) {
        this.custom3 = value;
    }

    /**
     * Gets the value of the custom4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustom4() {
        return custom4;
    }

    /**
     * Sets the value of the custom4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustom4(String value) {
        this.custom4 = value;
    }

    /**
     * Gets the value of the custom5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustom5() {
        return custom5;
    }

    /**
     * Sets the value of the custom5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustom5(String value) {
        this.custom5 = value;
    }

}

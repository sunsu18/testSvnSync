
package com.sfr.engage.services.client.alertsSubscription.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateSubscriptionCollectionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateSubscriptionCollectionType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.lntinfotech.com/integration/SOARuleEngineSubscription}SubscribeCollectionType">
 *       &lt;sequence>
 *         &lt;element name="SubscriptionID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateSubscriptionCollectionType", propOrder = {
    "subscriptionID"
})
public class UpdateSubscriptionCollectionType
    extends SubscribeCollectionType
{

    @XmlElement(name = "SubscriptionID", required = true)
    protected String subscriptionID;

    /**
     * Gets the value of the subscriptionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubscriptionID() {
        return subscriptionID;
    }

    /**
     * Sets the value of the subscriptionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubscriptionID(String value) {
        this.subscriptionID = value;
    }

}

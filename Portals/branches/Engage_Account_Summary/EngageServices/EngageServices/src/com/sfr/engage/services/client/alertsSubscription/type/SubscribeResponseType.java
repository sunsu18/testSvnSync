
package com.sfr.engage.services.client.alertsSubscription.type;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SubscribeResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SubscribeResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SubscribeCollection" type="{http://www.lntinfotech.com/integration/SOARuleEngineSubscription}SubscribeResponseCollectionType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubscribeResponseType", propOrder = {
    "subscribeCollection"
})
public class SubscribeResponseType {

    @XmlElement(name = "SubscribeCollection", required = true)
    protected List<SubscribeResponseCollectionType> subscribeCollection;

    /**
     * Gets the value of the subscribeCollection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subscribeCollection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubscribeCollection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubscribeResponseCollectionType }
     * 
     * 
     */
    public List<SubscribeResponseCollectionType> getSubscribeCollection() {
        if (subscribeCollection == null) {
            subscribeCollection = new ArrayList<SubscribeResponseCollectionType>();
        }
        return this.subscribeCollection;
    }

}

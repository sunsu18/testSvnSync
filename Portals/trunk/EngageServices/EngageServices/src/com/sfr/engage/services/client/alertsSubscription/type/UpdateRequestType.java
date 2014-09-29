
package com.sfr.engage.services.client.alertsSubscription.type;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UpdateSubscriptionCollection" type="{http://www.lntinfotech.com/integration/SOARuleEngineSubscription}UpdateSubscriptionCollectionType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateRequestType", propOrder = {
    "updateSubscriptionCollection"
})
public class UpdateRequestType {

    @XmlElement(name = "UpdateSubscriptionCollection", required = true)
    protected List<UpdateSubscriptionCollectionType> updateSubscriptionCollection;

    /**
     * Gets the value of the updateSubscriptionCollection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the updateSubscriptionCollection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdateSubscriptionCollection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UpdateSubscriptionCollectionType }
     * 
     * 
     */
    public List<UpdateSubscriptionCollectionType> getUpdateSubscriptionCollection() {
        if (updateSubscriptionCollection == null) {
            updateSubscriptionCollection = new ArrayList<UpdateSubscriptionCollectionType>();
        }
        return this.updateSubscriptionCollection;
    }

}

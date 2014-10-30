
package com.sfr.services.client.proxy.user.type;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oimUserManagementResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oimUserManagementResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OIMwServiceError" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="OIMwServiceIdentityResult" type="{http://ws.oim.sfr.com/}identity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="WServiceResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WServiceStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oimUserManagementResult", propOrder = {
    "oiMwServiceError",
    "oiMwServiceIdentityResult",
    "wServiceResult",
    "wServiceStatus"
})
public class OimUserManagementResult {

    @XmlElement(name = "OIMwServiceError", nillable = true)
    protected List<String> oiMwServiceError;
    @XmlElement(name = "OIMwServiceIdentityResult", nillable = true)
    protected List<Identity> oiMwServiceIdentityResult;
    @XmlElement(name = "WServiceResult")
    protected String wServiceResult;
    @XmlElement(name = "WServiceStatus")
    protected String wServiceStatus;

    /**
     * Gets the value of the oiMwServiceError property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oiMwServiceError property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOIMwServiceError().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOIMwServiceError() {
        if (oiMwServiceError == null) {
            oiMwServiceError = new ArrayList<String>();
        }
        return this.oiMwServiceError;
    }

    /**
     * Gets the value of the oiMwServiceIdentityResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oiMwServiceIdentityResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOIMwServiceIdentityResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Identity }
     * 
     * 
     */
    public List<Identity> getOIMwServiceIdentityResult() {
        if (oiMwServiceIdentityResult == null) {
            oiMwServiceIdentityResult = new ArrayList<Identity>();
        }
        return this.oiMwServiceIdentityResult;
    }

    /**
     * Gets the value of the wServiceResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWServiceResult() {
        return wServiceResult;
    }

    /**
     * Sets the value of the wServiceResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWServiceResult(String value) {
        this.wServiceResult = value;
    }

    /**
     * Gets the value of the wServiceStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWServiceStatus() {
        return wServiceStatus;
    }

    /**
     * Sets the value of the wServiceStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWServiceStatus(String value) {
        this.wServiceStatus = value;
    }

}

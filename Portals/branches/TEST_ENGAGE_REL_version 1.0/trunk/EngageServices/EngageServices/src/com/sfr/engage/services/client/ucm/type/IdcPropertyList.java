
package com.sfr.engage.services.client.ucm.type;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for idcPropertyList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="idcPropertyList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customMetdata" type="{http://ws.wcc.lnt.com/}property" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DDocTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="XContentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="XSourceSystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="XSubType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "idcPropertyList", propOrder = {
    "customMetdata",
    "dDocTitle",
    "xContentType",
    "xSourceSystem",
    "xSubType"
})
public class IdcPropertyList {

    @XmlElement(nillable = true)
    protected List<Property> customMetdata;
    @XmlElement(name = "DDocTitle")
    protected String dDocTitle;
    @XmlElement(name = "XContentType")
    protected String xContentType;
    @XmlElement(name = "XSourceSystem")
    protected String xSourceSystem;
    @XmlElement(name = "XSubType")
    protected String xSubType;

    /**
     * Gets the value of the customMetdata property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customMetdata property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomMetdata().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Property }
     * 
     * 
     */
    public List<Property> getCustomMetdata() {
        if (customMetdata == null) {
            customMetdata = new ArrayList<Property>();
        }
        return this.customMetdata;
    }

    /**
     * Gets the value of the dDocTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDDocTitle() {
        return dDocTitle;
    }

    /**
     * Sets the value of the dDocTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDDocTitle(String value) {
        this.dDocTitle = value;
    }

    /**
     * Gets the value of the xContentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXContentType() {
        return xContentType;
    }

    /**
     * Sets the value of the xContentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXContentType(String value) {
        this.xContentType = value;
    }

    /**
     * Gets the value of the xSourceSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXSourceSystem() {
        return xSourceSystem;
    }

    /**
     * Sets the value of the xSourceSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXSourceSystem(String value) {
        this.xSourceSystem = value;
    }

    /**
     * Gets the value of the xSubType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXSubType() {
        return xSubType;
    }

    /**
     * Sets the value of the xSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXSubType(String value) {
        this.xSubType = value;
    }

}

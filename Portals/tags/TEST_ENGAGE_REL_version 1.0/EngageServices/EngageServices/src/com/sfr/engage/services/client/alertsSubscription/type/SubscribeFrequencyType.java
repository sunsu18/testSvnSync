
package com.sfr.engage.services.client.alertsSubscription.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SubscribeFrequencyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SubscribeFrequencyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ScheduleFrequency" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ScheduleDay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ScheduleDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ScheduleMonth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubscribeFrequencyType", propOrder = {
    "scheduleFrequency",
    "scheduleDay",
    "scheduleDate",
    "scheduleMonth"
})
public class SubscribeFrequencyType {

    @XmlElement(name = "ScheduleFrequency", required = true)
    protected String scheduleFrequency;
    @XmlElement(name = "ScheduleDay")
    protected String scheduleDay;
    @XmlElement(name = "ScheduleDate")
    protected String scheduleDate;
    @XmlElement(name = "ScheduleMonth")
    protected String scheduleMonth;

    /**
     * Gets the value of the scheduleFrequency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduleFrequency() {
        return scheduleFrequency;
    }

    /**
     * Sets the value of the scheduleFrequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduleFrequency(String value) {
        this.scheduleFrequency = value;
    }

    /**
     * Gets the value of the scheduleDay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduleDay() {
        return scheduleDay;
    }

    /**
     * Sets the value of the scheduleDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduleDay(String value) {
        this.scheduleDay = value;
    }

    /**
     * Gets the value of the scheduleDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduleDate() {
        return scheduleDate;
    }

    /**
     * Sets the value of the scheduleDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduleDate(String value) {
        this.scheduleDate = value;
    }

    /**
     * Gets the value of the scheduleMonth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduleMonth() {
        return scheduleMonth;
    }

    /**
     * Sets the value of the scheduleMonth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduleMonth(String value) {
        this.scheduleMonth = value;
    }

}

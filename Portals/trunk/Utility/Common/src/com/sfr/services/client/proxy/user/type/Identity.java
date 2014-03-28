
package com.sfr.services.client.proxy.user.type;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for identity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="identity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addRoleList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="associated_AirportID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="aviation_EmployeeID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="b2BEmployee_CustomerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="b2BManager_CustomerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="b2C_CustomerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CARD_ADMINID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CARD_B2B_ADMINID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CARD_B2B_EMPID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CARD_B2B_MGRID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CARD_B2C_JETID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CARD_B2C_PETROID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CARD_B2C_SFRID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CARD_CSRID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CARD_SALES_REPID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DOB" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="designation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="employeeNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="externalCSR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="externalUSERTYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="internalCSR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="marine_Employee" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="marine_Manager" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="middleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="phoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="removeRoleList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reseller_CustomerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reseller_EmployeeID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serviceStation_EmployeeIDExternal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serviceStation_EmployeeIDInternal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serviceStation_ManagerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="siteIDs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="supplierID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userLang" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userManagerExt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userRoles" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="webshop_Manager" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "identity", propOrder = {
    "addRoleList",
    "associatedAirportID",
    "aviationEmployeeID",
    "b2BEmployeeCustomerID",
    "b2BManagerCustomerID",
    "b2CCustomerID",
    "cardadminid",
    "cardb2BADMINID",
    "cardb2BEMPID",
    "cardb2BMGRID",
    "cardb2CJETID",
    "cardb2CPETROID",
    "cardb2CSFRID",
    "cardcsrid",
    "cardsalesrepid",
    "dob",
    "designation",
    "email",
    "employeeNumber",
    "externalCSR",
    "externalUSERTYPE",
    "firstName",
    "internalCSR",
    "lastName",
    "marineEmployee",
    "marineManager",
    "middleName",
    "phoneNumber",
    "position",
    "removeRoleList",
    "resellerCustomerID",
    "resellerEmployeeID",
    "serviceStationEmployeeIDExternal",
    "serviceStationEmployeeIDInternal",
    "serviceStationManagerID",
    "siteIDs",
    "supplierID",
    "title",
    "userLang",
    "userManagerExt",
    "userRoles",
    "webshopManager"
})
public class Identity {

    @XmlElement(nillable = true)
    protected List<String> addRoleList;
    @XmlElement(name = "associated_AirportID")
    protected String associatedAirportID;
    @XmlElement(name = "aviation_EmployeeID")
    protected String aviationEmployeeID;
    @XmlElement(name = "b2BEmployee_CustomerID")
    protected String b2BEmployeeCustomerID;
    @XmlElement(name = "b2BManager_CustomerID")
    protected String b2BManagerCustomerID;
    @XmlElement(name = "b2C_CustomerID")
    protected String b2CCustomerID;
    @XmlElement(name = "CARD_ADMINID")
    protected String cardadminid;
    @XmlElement(name = "CARD_B2B_ADMINID")
    protected String cardb2BADMINID;
    @XmlElement(name = "CARD_B2B_EMPID")
    protected String cardb2BEMPID;
    @XmlElement(name = "CARD_B2B_MGRID")
    protected String cardb2BMGRID;
    @XmlElement(name = "CARD_B2C_JETID")
    protected String cardb2CJETID;
    @XmlElement(name = "CARD_B2C_PETROID")
    protected String cardb2CPETROID;
    @XmlElement(name = "CARD_B2C_SFRID")
    protected String cardb2CSFRID;
    @XmlElement(name = "CARD_CSRID")
    protected String cardcsrid;
    @XmlElement(name = "CARD_SALES_REPID")
    protected String cardsalesrepid;
    @XmlElement(name = "DOB")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dob;
    protected String designation;
    protected String email;
    protected String employeeNumber;
    protected String externalCSR;
    protected String externalUSERTYPE;
    protected String firstName;
    protected String internalCSR;
    protected String lastName;
    @XmlElement(name = "marine_Employee")
    protected String marineEmployee;
    @XmlElement(name = "marine_Manager")
    protected String marineManager;
    protected String middleName;
    protected String phoneNumber;
    protected String position;
    @XmlElement(nillable = true)
    protected List<String> removeRoleList;
    @XmlElement(name = "reseller_CustomerID")
    protected String resellerCustomerID;
    @XmlElement(name = "reseller_EmployeeID")
    protected String resellerEmployeeID;
    @XmlElement(name = "serviceStation_EmployeeIDExternal")
    protected String serviceStationEmployeeIDExternal;
    @XmlElement(name = "serviceStation_EmployeeIDInternal")
    protected String serviceStationEmployeeIDInternal;
    @XmlElement(name = "serviceStation_ManagerID")
    protected String serviceStationManagerID;
    protected String siteIDs;
    protected String supplierID;
    protected String title;
    protected String userLang;
    protected String userManagerExt;
    protected String userRoles;
    @XmlElement(name = "webshop_Manager")
    protected String webshopManager;

    /**
     * Gets the value of the addRoleList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addRoleList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddRoleList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAddRoleList() {
        if (addRoleList == null) {
            addRoleList = new ArrayList<String>();
        }
        return this.addRoleList;
    }

    /**
     * Gets the value of the associatedAirportID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssociatedAirportID() {
        return associatedAirportID;
    }

    /**
     * Sets the value of the associatedAirportID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssociatedAirportID(String value) {
        this.associatedAirportID = value;
    }

    /**
     * Gets the value of the aviationEmployeeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAviationEmployeeID() {
        return aviationEmployeeID;
    }

    /**
     * Sets the value of the aviationEmployeeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAviationEmployeeID(String value) {
        this.aviationEmployeeID = value;
    }

    /**
     * Gets the value of the b2BEmployeeCustomerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getB2BEmployeeCustomerID() {
        return b2BEmployeeCustomerID;
    }

    /**
     * Sets the value of the b2BEmployeeCustomerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setB2BEmployeeCustomerID(String value) {
        this.b2BEmployeeCustomerID = value;
    }

    /**
     * Gets the value of the b2BManagerCustomerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getB2BManagerCustomerID() {
        return b2BManagerCustomerID;
    }

    /**
     * Sets the value of the b2BManagerCustomerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setB2BManagerCustomerID(String value) {
        this.b2BManagerCustomerID = value;
    }

    /**
     * Gets the value of the b2CCustomerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getB2CCustomerID() {
        return b2CCustomerID;
    }

    /**
     * Sets the value of the b2CCustomerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setB2CCustomerID(String value) {
        this.b2CCustomerID = value;
    }

    /**
     * Gets the value of the cardadminid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDADMINID() {
        return cardadminid;
    }

    /**
     * Sets the value of the cardadminid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDADMINID(String value) {
        this.cardadminid = value;
    }

    /**
     * Gets the value of the cardb2BADMINID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDB2BADMINID() {
        return cardb2BADMINID;
    }

    /**
     * Sets the value of the cardb2BADMINID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDB2BADMINID(String value) {
        this.cardb2BADMINID = value;
    }

    /**
     * Gets the value of the cardb2BEMPID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDB2BEMPID() {
        return cardb2BEMPID;
    }

    /**
     * Sets the value of the cardb2BEMPID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDB2BEMPID(String value) {
        this.cardb2BEMPID = value;
    }

    /**
     * Gets the value of the cardb2BMGRID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDB2BMGRID() {
        return cardb2BMGRID;
    }

    /**
     * Sets the value of the cardb2BMGRID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDB2BMGRID(String value) {
        this.cardb2BMGRID = value;
    }

    /**
     * Gets the value of the cardb2CJETID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDB2CJETID() {
        return cardb2CJETID;
    }

    /**
     * Sets the value of the cardb2CJETID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDB2CJETID(String value) {
        this.cardb2CJETID = value;
    }

    /**
     * Gets the value of the cardb2CPETROID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDB2CPETROID() {
        return cardb2CPETROID;
    }

    /**
     * Sets the value of the cardb2CPETROID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDB2CPETROID(String value) {
        this.cardb2CPETROID = value;
    }

    /**
     * Gets the value of the cardb2CSFRID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDB2CSFRID() {
        return cardb2CSFRID;
    }

    /**
     * Sets the value of the cardb2CSFRID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDB2CSFRID(String value) {
        this.cardb2CSFRID = value;
    }

    /**
     * Gets the value of the cardcsrid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDCSRID() {
        return cardcsrid;
    }

    /**
     * Sets the value of the cardcsrid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDCSRID(String value) {
        this.cardcsrid = value;
    }

    /**
     * Gets the value of the cardsalesrepid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCARDSALESREPID() {
        return cardsalesrepid;
    }

    /**
     * Sets the value of the cardsalesrepid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCARDSALESREPID(String value) {
        this.cardsalesrepid = value;
    }

    /**
     * Gets the value of the dob property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDOB() {
        return dob;
    }

    /**
     * Sets the value of the dob property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDOB(XMLGregorianCalendar value) {
        this.dob = value;
    }

    /**
     * Gets the value of the designation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * Sets the value of the designation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesignation(String value) {
        this.designation = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the employeeNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     * Sets the value of the employeeNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmployeeNumber(String value) {
        this.employeeNumber = value;
    }

    /**
     * Gets the value of the externalCSR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalCSR() {
        return externalCSR;
    }

    /**
     * Sets the value of the externalCSR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalCSR(String value) {
        this.externalCSR = value;
    }

    /**
     * Gets the value of the externalUSERTYPE property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalUSERTYPE() {
        return externalUSERTYPE;
    }

    /**
     * Sets the value of the externalUSERTYPE property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalUSERTYPE(String value) {
        this.externalUSERTYPE = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the internalCSR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternalCSR() {
        return internalCSR;
    }

    /**
     * Sets the value of the internalCSR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternalCSR(String value) {
        this.internalCSR = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the marineEmployee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarineEmployee() {
        return marineEmployee;
    }

    /**
     * Sets the value of the marineEmployee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarineEmployee(String value) {
        this.marineEmployee = value;
    }

    /**
     * Gets the value of the marineManager property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarineManager() {
        return marineManager;
    }

    /**
     * Sets the value of the marineManager property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarineManager(String value) {
        this.marineManager = value;
    }

    /**
     * Gets the value of the middleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleName(String value) {
        this.middleName = value;
    }

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosition(String value) {
        this.position = value;
    }

    /**
     * Gets the value of the removeRoleList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the removeRoleList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemoveRoleList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRemoveRoleList() {
        if (removeRoleList == null) {
            removeRoleList = new ArrayList<String>();
        }
        return this.removeRoleList;
    }

    /**
     * Gets the value of the resellerCustomerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResellerCustomerID() {
        return resellerCustomerID;
    }

    /**
     * Sets the value of the resellerCustomerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResellerCustomerID(String value) {
        this.resellerCustomerID = value;
    }

    /**
     * Gets the value of the resellerEmployeeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResellerEmployeeID() {
        return resellerEmployeeID;
    }

    /**
     * Sets the value of the resellerEmployeeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResellerEmployeeID(String value) {
        this.resellerEmployeeID = value;
    }

    /**
     * Gets the value of the serviceStationEmployeeIDExternal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceStationEmployeeIDExternal() {
        return serviceStationEmployeeIDExternal;
    }

    /**
     * Sets the value of the serviceStationEmployeeIDExternal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceStationEmployeeIDExternal(String value) {
        this.serviceStationEmployeeIDExternal = value;
    }

    /**
     * Gets the value of the serviceStationEmployeeIDInternal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceStationEmployeeIDInternal() {
        return serviceStationEmployeeIDInternal;
    }

    /**
     * Sets the value of the serviceStationEmployeeIDInternal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceStationEmployeeIDInternal(String value) {
        this.serviceStationEmployeeIDInternal = value;
    }

    /**
     * Gets the value of the serviceStationManagerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceStationManagerID() {
        return serviceStationManagerID;
    }

    /**
     * Sets the value of the serviceStationManagerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceStationManagerID(String value) {
        this.serviceStationManagerID = value;
    }

    /**
     * Gets the value of the siteIDs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteIDs() {
        return siteIDs;
    }

    /**
     * Sets the value of the siteIDs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteIDs(String value) {
        this.siteIDs = value;
    }

    /**
     * Gets the value of the supplierID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplierID() {
        return supplierID;
    }

    /**
     * Sets the value of the supplierID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplierID(String value) {
        this.supplierID = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the userLang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserLang() {
        return userLang;
    }

    /**
     * Sets the value of the userLang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserLang(String value) {
        this.userLang = value;
    }

    /**
     * Gets the value of the userManagerExt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserManagerExt() {
        return userManagerExt;
    }

    /**
     * Sets the value of the userManagerExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserManagerExt(String value) {
        this.userManagerExt = value;
    }

    /**
     * Gets the value of the userRoles property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserRoles() {
        return userRoles;
    }

    /**
     * Sets the value of the userRoles property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserRoles(String value) {
        this.userRoles = value;
    }

    /**
     * Gets the value of the webshopManager property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebshopManager() {
        return webshopManager;
    }

    /**
     * Sets the value of the webshopManager property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebshopManager(String value) {
        this.webshopManager = value;
    }

}

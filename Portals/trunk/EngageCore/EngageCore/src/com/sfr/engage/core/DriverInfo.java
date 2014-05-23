package com.sfr.engage.core;

import java.io.Serializable;

import java.util.Date;


/**
 * TODO : ASHTHA - 30, Apr, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC toString() method
 *  2. Override toString() method
 */
public class DriverInfo implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;

    /**
     */
    public DriverInfo() {
        super();
    }

    private String prtDriverInformationPK;
    private String accountId;
    private String cardNumber;
    private String embossCardNumber;
    private String driverName;
    private String driverNumber;
    private String nationality;
    private Integer mobileNumber;
    private String remarks;
    private String passportNumber;
    private Date passportExpiry;
    private String licenseNumber;
    private Date licenseExpiry;
    private Date employStart;
    private Date employEnd;
    private String countryCd;
    private String modifiedBy;

    /**
     * @param prtDriverInformationPK
     */
    public void setPrtDriverInformationPK(String prtDriverInformationPK) {
        this.prtDriverInformationPK = prtDriverInformationPK;
    }

    /**
     * @return
     */
    public String getPrtDriverInformationPK() {
        return prtDriverInformationPK;
    }

    /**
     * @param accountId
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * @return
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * @param cardNumber
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * @return
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * @param driverName
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * @return
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * @param driverNumber
     */
    public void setDriverNumber(String driverNumber) {
        this.driverNumber = driverNumber;
    }

    /**
     * @return
     */
    public String getDriverNumber() {
        return driverNumber;
    }

    /**
     * @param nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * @return
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @param mobileNumber
     */
    public void setMobileNumber(Integer mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * @return
     */
    public Integer getMobileNumber() {
        return mobileNumber;
    }

    /**
     * @param remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param passportNumber
     */
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    /**
     * @return
     */
    public String getPassportNumber() {
        return passportNumber;
    }

    /**
     * @param passportExpiry
     */
    public void setPassportExpiry(Date passportExpiry) {
        this.passportExpiry = passportExpiry;
    }

    /**
     * @return
     */
    public Date getPassportExpiry() {
        return passportExpiry;
    }

    /**
     * @param licenseNumber
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    /**
     * @return
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * @param licenseExpiry
     */
    public void setLicenseExpiry(Date licenseExpiry) {
        this.licenseExpiry = licenseExpiry;
    }

    /**
     * @return
     */
    public Date getLicenseExpiry() {
        return licenseExpiry;
    }

    /**
     * @param employStart
     */
    public void setEmployStart(Date employStart) {
        this.employStart = employStart;
    }

    /**
     * @return
     */
    public Date getEmployStart() {
        return employStart;
    }

    /**
     * @param employEnd
     */
    public void setEmployEnd(Date employEnd) {
        this.employEnd = employEnd;
    }

    /**
     * @return
     */
    public Date getEmployEnd() {
        return employEnd;
    }

    /**
     * @param countryCd
     */
    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
    }

    /**
     * @return
     */
    public String getCountryCd() {
        return countryCd;
    }

    /**
     * @param modifiedBy
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @return
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setEmbossCardNumber(String embossCardNumber) {
        this.embossCardNumber = embossCardNumber;
    }

    public String getEmbossCardNumber() {
        return embossCardNumber;
    }
}

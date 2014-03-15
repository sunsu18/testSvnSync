package com.sfr.engage.core;

import java.io.Serializable;

import java.util.Date;

public class DriverInfo implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    public DriverInfo() {
        super();
    }
    
        private String prtDriverInformationPK;
        private String accountId;
        private String cardNumber;
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

    public void setPrtDriverInformationPK(String prtDriverInformationPK) {
        this.prtDriverInformationPK = prtDriverInformationPK;
    }

    public String getPrtDriverInformationPK() {
        return prtDriverInformationPK;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverNumber(String driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getDriverNumber() {
        return driverNumber;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setMobileNumber(Integer mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getMobileNumber() {
        return mobileNumber;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportExpiry(Date passportExpiry) {
        this.passportExpiry = passportExpiry;
    }

    public Date getPassportExpiry() {
        return passportExpiry;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseExpiry(Date licenseExpiry) {
        this.licenseExpiry = licenseExpiry;
    }

    public Date getLicenseExpiry() {
        return licenseExpiry;
    }

    public void setEmployStart(Date employStart) {
        this.employStart = employStart;
    }

    public Date getEmployStart() {
        return employStart;
    }

    public void setEmployEnd(Date employEnd) {
        this.employEnd = employEnd;
    }

    public Date getEmployEnd() {
        return employEnd;
    }

    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
    }

    public String getCountryCd() {
        return countryCd;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }
}

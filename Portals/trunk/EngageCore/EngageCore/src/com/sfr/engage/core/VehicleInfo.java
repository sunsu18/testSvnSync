package com.sfr.engage.core;

import java.io.Serializable;

import java.util.Date;


/**
 * TODO : ASHTHA - 30, Apr, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC toString() method
 *  2. Override toString() method
 */
public class VehicleInfo implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;

    /**
     */
    public VehicleInfo() {
        super();
    }
    private String prtTruckInformationPK;
    private String accountNumber;
    private String cardNumber;
    private String vehicleNumber;
    private String internalName;
    private String registrationNumber;
    private String brand;
    private Integer year;
    private Date registrationDate;
    private Date endDate;
    private String fuelType;
    private Integer maxFuel;
    private Integer odoMeter;
    private String remarks;
    private String modifiedBy;


    /**
     * @param prtTruckInformationPK
     */
    public void setPrtTruckInformationPK(String prtTruckInformationPK) {
        this.prtTruckInformationPK = prtTruckInformationPK;
    }

    /**
     * @return
     */
    public String getPrtTruckInformationPK() {
        return prtTruckInformationPK;
    }

    /**
     * @param accountNumber
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return
     */
    public String getAccountNumber() {
        return accountNumber;
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
     * @param vehicleNumber
     */
    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    /**
     * @return
     */
    public String getVehicleNumber() {
        return vehicleNumber;
    }

    /**
     * @param internalName
     */
    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    /**
     * @return
     */
    public String getInternalName() {
        return internalName;
    }

    /**
     * @param registrationNumber
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * @return
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * @param brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param registrationDate
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * @return
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param fuelType
     */
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * @return
     */
    public String getFuelType() {
        return fuelType;
    }

    /**
     * @param maxFuel
     */
    public void setMaxFuel(Integer maxFuel) {
        this.maxFuel = maxFuel;
    }

    /**
     * @return
     */
    public Integer getMaxFuel() {
        return maxFuel;
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

    /**
     * @param odoMeter
     */
    public void setOdoMeter(Integer odoMeter) {
        this.odoMeter = odoMeter;
    }

    /**
     * @return
     */
    public Integer getOdoMeter() {
        return odoMeter;
    }
}

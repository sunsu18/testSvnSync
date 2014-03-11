package com.sfr.engage.core;

import java.util.Date;

public class VehicleInfo {
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
    private Integer ODOMeter;
    private String remarks;
    private String modifiedBy;


    public void setPrtTruckInformationPK(String prtTruckInformationPK) {
        this.prtTruckInformationPK = prtTruckInformationPK;
    }

    public String getPrtTruckInformationPK() {
        return prtTruckInformationPK;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getYear() {
        return year;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setMaxFuel(Integer maxFuel) {
        this.maxFuel = maxFuel;
    }

    public Integer getMaxFuel() {
        return maxFuel;
    }

    public void setODOMeter(Integer ODOMeter) {
        this.ODOMeter = ODOMeter;
    }

    public Integer getODOMeter() {
        return ODOMeter;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }
}

package com.sfr.engage.core;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;


/**
 * TODO : ASHTHA - 30, Apr, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC toString() method
 *  2. Override toString() method
 */
public class Account implements Comparable<Account>, Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private List<VehicleInfo> vehicleInfoList;
    private List<DriverInfo> driverInfoList;


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
     * @return
     */
    public List<VehicleInfo> getVehicleInfoList() {
        if (vehicleInfoList == null) {
            vehicleInfoList = new ArrayList<VehicleInfo>();
        }
        return vehicleInfoList;
    }

    /**
     * @return
     */
    public List<DriverInfo> getDriverInfoList() {
        if (driverInfoList == null) {
            driverInfoList = new ArrayList<DriverInfo>();
        }
        return driverInfoList;
    }

    /**
     * @param vehicleInfoList
     */
    public void setVehicleInfoList(List<VehicleInfo> vehicleInfoList) {
        this.vehicleInfoList = vehicleInfoList;
    }

    /**
     * @param driverInfoList
     */
    public void setDriverInfoList(List<DriverInfo> driverInfoList) {
        this.driverInfoList = driverInfoList;
    }

    public int compareTo(Account o) {
        return this.getAccountNumber().compareTo(o.getAccountNumber());
    }
}

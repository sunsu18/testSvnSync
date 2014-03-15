package com.sfr.engage.core;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private List<VehicleInfo> vehicleInfoList;
    private List<DriverInfo> driverInfoList;


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    
    public List<VehicleInfo> getVehicleInfoList() {
        if(vehicleInfoList==null) {
            vehicleInfoList = new ArrayList<VehicleInfo>();
        }
        return vehicleInfoList;
    }
    
    public List<DriverInfo> getDriverInfoList() {
        if(driverInfoList==null) {
            driverInfoList = new ArrayList<DriverInfo>();
        }
        return driverInfoList;
    }

    public void setVehicleInfoList(List<VehicleInfo> vehicleInfoList) {
        this.vehicleInfoList = vehicleInfoList;
    }

    public void setDriverInfoList(List<DriverInfo> driverInfoList) {
        this.driverInfoList = driverInfoList;
    }
}

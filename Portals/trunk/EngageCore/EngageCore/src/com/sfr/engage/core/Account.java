package com.sfr.engage.core;

import java.util.ArrayList;
import java.util.List;

public class Account{
  
    private String accountNumber;
   List<VehicleInfo> vehicleInfoList;
   List<DriverInfo> driverInfoList;


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
}

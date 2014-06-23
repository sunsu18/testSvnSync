package com.sfr.core.bean;

import com.sfr.util.ThreadSerialization;

import java.util.ArrayList;
import java.util.List;

public class ShippingInfo extends ThreadSerialization{
    private static final long serialVersionUID = 1L; 

    
    private int customerId; // Take it from parent customer

    private Address shipToAddress; //CustomerResults/ShipTo/addressLine1 and other

    private Address billToAddress; //Bill to address details

    private List<TankInfo> tankInfoList; //CustomerResults/ShipTo/TankInfo

    private List<HeatingDeviceInfo> heatingDeviceList; //Heating Device Info

    private boolean shipToSameAsSoldToFlag; // to be send to WS will not get it back

    private boolean billToSameAsSoldToFlag; // to be send to WS will not get it back

    private boolean billToSameAsOtherBillToFlag; // yet to be ddecided

    private boolean billToSameAsShipToFlag; // not to be send to webservice

    private String actionType; // Action type from the Portal  U = Update

    private int tankSize;

    private int heatingDeviceSize;

    private String taxRateInfo; // To identify whether the shiping info is valid or not for order creation. This field depends on CustomerLoginResponse.CustomerResults.Detail of S0230 WSDl
    
    private String contactInfo;
    
    private String contactPerson;
    
    private String contactPhone;
    private String secMailingName;

    public void setCustomerTankInfoList(List<TankInfo> tankInfoList) {
        this.tankInfoList = tankInfoList;
    }

    public List<TankInfo> getTankInfoList() {
        return tankInfoList;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setShipToAddress(Address shipToAddress) {
        this.shipToAddress = shipToAddress;
    }

    public Address getShipToAddress() {
        return shipToAddress;
    }

    public void setBillToAddress(Address billToAddress) {
        this.billToAddress = billToAddress;
    }

    public Address getBillToAddress() {
        return billToAddress;
    }

    public void setHeatingDeviceList(List<HeatingDeviceInfo> heatingDeviceList) {
        this.heatingDeviceList = heatingDeviceList;
    }

    public List<HeatingDeviceInfo> getHeatingDeviceList() {
        return heatingDeviceList;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setShipToSameAsSoldToFlag(boolean shipToSameAsSoldToFlag) {
        this.shipToSameAsSoldToFlag = shipToSameAsSoldToFlag;
    }

    public boolean isShipToSameAsSoldToFlag() {
        return shipToSameAsSoldToFlag;
    }

    public void setBillToSameAsSoldToFlag(boolean billToSameAsSoldToFlag) {
        this.billToSameAsSoldToFlag = billToSameAsSoldToFlag;
    }

    public boolean isBillToSameAsSoldToFlag() {
        return billToSameAsSoldToFlag;
    }

    public void setBillToSameAsShipToFlag(boolean billToSameAsShipToFlag) {
        this.billToSameAsShipToFlag = billToSameAsShipToFlag;
    }

    public boolean isBillToSameAsShipToFlag() {
        return billToSameAsShipToFlag;
    }

    public void setTankSize(int tankSize) {
        this.tankSize = tankSize;
    }

    public int getTankSize() {
        return tankSize;
    }

    public void setHeatingDeviceSize(int heatingDeviceSize) {
        this.heatingDeviceSize = heatingDeviceSize;
    }

    public int getHeatingDeviceSize() {
        return heatingDeviceSize;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        
        sb.append("\t\t<Shipping Info Begins>");
        sb.append("ShippingInfo: actionType=<" + this.actionType + ">" );
        sb.append("ShippingInfo: billToSameAsShipToFlag=<" + this.billToSameAsShipToFlag + ">" );
        sb.append("ShippingInfo: billToSameAsSoldToFlag=<" + this.billToSameAsSoldToFlag + ">" );
        sb.append("ShippingInfo: shipToSameAsSoldToFlag=<" + this.shipToSameAsSoldToFlag + ">");
        sb.append("ShippingInfo: billToSameAsOtherBillToFlag=<" + this.billToSameAsOtherBillToFlag + ">");
        sb.append("ShippingInfo: tankSize=<" + this.tankSize + ">" );
        sb.append("ShippingInfo: heatingDeviceSize=<" + this.heatingDeviceSize + ">" );
        sb.append("ShippingInfo: customerId=<" + this.customerId + ">" );

        sb.append(NEW_LINE+"\t\t\tshipToAddress begins:----------------> " );
        if (this.shipToAddress != null) {
            sb.append(this.shipToAddress);
        }else{
            sb.append("ShipToAddress IS NULL " );    
        }
        sb.append("<----------------shipToAddress ends"+NEW_LINE );
        
        sb.append("\t\t\tbillToAddress begins:----------------> " );
        if (this.billToAddress != null) {
            sb.append( this.billToAddress );
        }
        else{
            sb.append( "billToAddress IS NULL");
        }
        sb.append("<----------------billToAddress ends"+NEW_LINE );
        sb.append("\t\t\ttankInfoList begins->");
        if (this.tankInfoList != null) {
            sb.append(" tankInfoList.size()=<" + tankInfoList.size() + ">=" );
            for (int i = 0; i < this.tankInfoList.size(); i++) {
                sb.append(NEW_LINE+"\t\t\t\ttankInfoList object nubmer :-> " + i );
                TankInfo tank = tankInfoList.get(i);
                if (tank != null)
                    sb.append( tank );
                else
                    sb.append("tankInfoList.get(" + i + ") is null");
            }
        }else{
            sb.append("ShippingInfo: tankInfoList IS NULL" );
        }
        sb.append("<-----------tankInfoList ends."+NEW_LINE);
        sb.append("\t\t\theatingDeviceList begins:->");
        if (this.heatingDeviceList != null) {
            sb.append("heatingDeviceList.size()=<" + heatingDeviceList.size() + ">=" );
            for (int i = 0; i < this.heatingDeviceList.size(); i++) {
                sb.append(NEW_LINE+"\t\t\t\theatingDeviceList object nubmer :-> " + i );
                HeatingDeviceInfo heat = heatingDeviceList.get(i);
                if (heat != null)
                    sb.append( heat);
                else
                    sb.append("heatingDeviceList.get(" + i + ") is null" );

            }
        }else{
            sb.append("heatingDeviceList IS NULL" );
        }
        sb.append("<-----------heatingDeviceList ends."+NEW_LINE);
        sb.append("\t\t<Shipping Info Ends/>"+NEW_LINE );
        return sb.toString();


    }

    public void setTaxRateInfo(String taxRateInfo) {
        this.taxRateInfo = taxRateInfo;
    }

    public String getTaxRateInfo() {
        return taxRateInfo;
    }

    public void setBillToSameAsOtherBillToFlag(boolean billToSameAsotherBilltoFlag) {
        this.billToSameAsOtherBillToFlag = billToSameAsotherBilltoFlag;
    }

    public boolean isBillToSameAsOtherBillToFlag() {
        return billToSameAsOtherBillToFlag;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() {
        return contactPhone;
    }


    public void setSecMailingName(String secMailingName) {
        this.secMailingName = secMailingName;
    }

    public String getSecMailingName() {
        return secMailingName;
    }

    @Override
    public boolean equals(Object obj) {
        boolean istrue=false;
        if (obj instanceof ShippingInfo) {

            ShippingInfo newShippingInfo = (ShippingInfo)obj;
            if (this.getShipToAddress() != null && this.getShipToAddress().getAddressNumber() != null) {
                if (newShippingInfo != null && newShippingInfo.getShipToAddress() != null && newShippingInfo.getShipToAddress().getAddressNumber() != null) {
                    if (this.getShipToAddress().getAddressNumber().equals(newShippingInfo.getShipToAddress().getAddressNumber())) {
                        istrue = true;
                    } else {
                        istrue = false;
                    }
                } else {
                    istrue = false;
                }
            } else {
                istrue = false;
            }
        }
        return istrue;
    }
}

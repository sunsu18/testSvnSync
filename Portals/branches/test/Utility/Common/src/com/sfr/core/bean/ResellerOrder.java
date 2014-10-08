package com.sfr.core.bean;

import java.util.Date;

public class ResellerOrder extends Order {

    private static final long serialVersionUID = 1L;
    private String resellerPlanningFlag;
    private String primaryVehicleId;
    private Date requestByDate;
    private String tripNo;
    private String billOfLadingNo;
    private String deliveryInstructions1;
    private String deliveryInstructions2;

    public void setResellerPlanningFlag(String resellerPlanningFlag) {
        this.resellerPlanningFlag = resellerPlanningFlag;
    }

    public String getResellerPlanningFlag() {
        return resellerPlanningFlag;
    }

    public void setPrimaryVehicleId(String primaryVehicleId) {
        this.primaryVehicleId = primaryVehicleId;
    }

    public String getPrimaryVehicleId() {
        return primaryVehicleId;
    }

    public void setRequestByDate(Date requestByDate) {
        this.requestByDate = requestByDate;
    }

    public Date getRequestByDate() {
        return requestByDate;
    }

    public void setTripNo(String tripNo) {
        this.tripNo = tripNo;
    }

    public String getTripNo() {
        return tripNo;
    }

    public void setBillOfLadingNo(String billOfLadingNo) {
        this.billOfLadingNo = billOfLadingNo;
    }

    public String getBillOfLadingNo() {
        return billOfLadingNo;
    }

    public void setDeliveryInstructions1(String deliveryInstructions1) {
        this.deliveryInstructions1 = deliveryInstructions1;
    }

    public String getDeliveryInstructions1() {
        return deliveryInstructions1;
    }

    public void setDeliveryInstructions2(String deliveryInstructions2) {
        this.deliveryInstructions2 = deliveryInstructions2;
    }

    public String getDeliveryInstructions2() {
        return deliveryInstructions2;
    }
}

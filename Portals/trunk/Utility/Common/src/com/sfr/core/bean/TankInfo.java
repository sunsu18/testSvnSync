package com.sfr.core.bean;

import com.sfr.util.AccessDataControl;
import com.sfr.util.ThreadSerialization;

import java.util.Date;

public class TankInfo extends ThreadSerialization {

    private static final long serialVersionUID = 1L;
    private int deliveryId; // take from parent del address
    //primary key
    private String tanknumber; //CustomerResults/ShipTo/TankInfo/tankNumber

    // To be passed from portal
    private String tankDescription; //CustomerResults/ShipTo/TankInfo/tankDescription
    private double tankCapacity; //  Capacity
    private boolean belowGround; // CustomerResults/ShipTo/TankInfo/belowGround

    private Integer shortItemNumber; //hidden
    private String itemNumber; //hidden

    private String itemDescription; //itemDescription1

    private String actionType; // Action type from the Portal  U = Update, A= Add , D=Delete

    //Other fields present in webservice request-response , will be passed as blank

    private String guageID;
    private String tankProcessType;
    private String interfaceID;
    private double unpumpCapacity;
    private double maxFillUpQuntity;
    private Date startDate;
    private Date endDate;
    private String tankUOM; //CustomerResults/ShipTo/TankInfo/tankUOM
    private String tankStatus;
    private Integer stockWarning;
    private double capacityMinimum;

    public void setTankDescription(String tankDescription) {
        this.tankDescription = tankDescription;
    }

    public String getTankDescription() {
        return tankDescription;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setTanknumber(String tanknumber) {
        this.tanknumber = tanknumber;
    }

    public String getTanknumber() {
        return tanknumber;
    }

    public void setTankCapacity(double tankCapacity) {
        this.tankCapacity = tankCapacity;
    }

    public double getTankCapacity() {
        return tankCapacity;
    }

    public void setShortItemNumber(Integer shortItemNumber) {
        this.shortItemNumber = shortItemNumber;
    }

    public Integer getShortItemNumber() {
        return shortItemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setGuageID(String guageID) {
        this.guageID = guageID;
    }

    public String getGuageID() {
        return guageID;
    }

    public void setTankProcessType(String tankProcessType) {
        this.tankProcessType = tankProcessType;
    }

    public String getTankProcessType() {
        return tankProcessType;
    }

    public void setInterfaceID(String interfaceID) {
        this.interfaceID = interfaceID;
    }

    public String getInterfaceID() {
        return interfaceID;
    }

    public void setUnpumpCapacity(double unpumpCapacity) {
        this.unpumpCapacity = unpumpCapacity;
    }

    public double getUnpumpCapacity() {
        return unpumpCapacity;
    }

    public void setMaxFillUpQuntity(double maxFillUpQuntity) {
        this.maxFillUpQuntity = maxFillUpQuntity;
    }

    public double getMaxFillUpQuntity() {
        return maxFillUpQuntity;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setTankUOM(String tankUOM) {
        this.tankUOM = tankUOM;
    }

    public String getTankUOM() {
        return tankUOM;
    }

    public void setTankStatus(String tankStatus) {
        this.tankStatus = tankStatus;
    }

    public String getTankStatus() {
        return tankStatus;
    }

    public void setStockWarning(Integer stockWarning) {
        this.stockWarning = stockWarning;
    }

    public Integer getStockWarning() {
        return stockWarning;
    }

    public void setCapacityMinimum(double capacityMinimum) {
        this.capacityMinimum = capacityMinimum;
    }

    public double getCapacityMinimum() {
        return capacityMinimum;
    }

    public void setBelowGround(boolean belowGround) {
        this.belowGround = belowGround;
    }

    public boolean isBelowGround() {
        return belowGround;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");

        sb.append("<TankInfo Begins>");
        sb.append("  actionType=<" + this.actionType + ">");
        sb.append("  belowGround=<" + this.belowGround + ">");
        sb.append("  capacityMinimum=<" + this.capacityMinimum + ">");
        sb.append("  deliveryId=<" + this.deliveryId + ">");
        sb.append("  endDate=<" + this.endDate + ">");
        sb.append("  guageID=<" + this.guageID + ">");
        sb.append("  interfaceID=<" + this.interfaceID + ">");
        sb.append("  isBelowGround=<" + this.isBelowGround() + ">");
        sb.append("  itemDescription=<" + this.itemDescription + ">");
        sb.append("  itemNumber=<" + this.itemNumber + ">");
        sb.append("  maxFillUpQuntity=<" + this.maxFillUpQuntity + ">");
        sb.append("  shortItemNumber=<" + this.shortItemNumber + ">");
        sb.append("  startDate=<" + this.startDate + ">");
        sb.append("  stockWarning=<" + this.stockWarning + ">");
        sb.append("  tankCapacity=<" + this.tankCapacity + ">");
        sb.append("  tankDescription=<" + this.tankDescription + ">");
        sb.append("  tanknumber=<" + this.tanknumber + ">");
        sb.append("  tankProcessType=<" + this.tankProcessType + ">");
        sb.append("  tankStatus=<" + this.tankStatus + ">");
        sb.append("  tankUOM=<" + this.tankUOM + ">");
        sb.append("  unpumpCapacity=<" + this.unpumpCapacity + ">");

        sb.append("<TankInfo Ends/>");
        return sb.toString();
    }

    public static void main(String[] args) {
        TankInfo t = new TankInfo();

    }
}

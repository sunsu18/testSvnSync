package com.sfr.core.bean;

import com.sfr.util.AccessDataControl;
import com.sfr.util.ThreadSerialization;

public class HeatingDeviceInfo extends ThreadSerialization{
    private static final long serialVersionUID = 1L;
    private String deviceNumber; // Number of device
    private String deviceType;
    private String deviceName;
    private String actionType; // Action type from the Portal  U = Update
    private Integer lineId; // primary key for Heating Info in JDE same as TYCCode

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Integer getLineId() {
        return lineId;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
      
        sb.append("<HeatingDeviceInfo Begins>" );
        sb.append("  actionType=<" + this.actionType + ">" );
        sb.append("  deviceName=<" + this.deviceName + ">" );
        sb.append("  deviceNumber=<" + this.deviceNumber + ">" );
        sb.append("  deviceType=<" + this.deviceType + ">" );
        sb.append("  lineId=<" + this.lineId + ">" );


        sb.append("<HeatingDeviceInfo Ends/>");
        return sb.toString();
    }
    public static void main(String[] args)
    {
        HeatingDeviceInfo t=new HeatingDeviceInfo();
  //      System.out.println(AccessDataControl.getDisplayRecord()+"HeatingDeviceInfo.main : "+t);
    }

}

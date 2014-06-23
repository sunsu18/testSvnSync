package com.sfr.core.bean;

import com.sfr.util.ThreadSerialization;

public class COOPInfo extends ThreadSerialization{


    private static final long serialVersionUID = 1L;
    private String COOPNumber;
    private Integer COOPLineId; // primary key for COOP in JDE same as TYCCode
    private String actionType; // Add(A),Update(U) or Delete(D)

    public void setCOOPNumber(String COOPNumber) {
        this.COOPNumber = COOPNumber;
    }

    public String getCOOPNumber() {
        return COOPNumber;
    }

    public void setCOOPLineId(Integer COOPLineId) {
        this.COOPLineId = COOPLineId;
    }

    public Integer getCOOPLineId() {
        return COOPLineId;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
       
        sb.append("<COOPInfo Begins" );
        sb.append("actionType=<" + this.actionType + ">");
        sb.append("COOPLineId=<" + this.COOPLineId + ">" );
        sb.append("COOPNumber=<" + this.COOPNumber + ">" );
       


        sb.append("<COOPInfo Ends/>" );
        return sb.toString();
    }
}

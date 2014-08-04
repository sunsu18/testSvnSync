package com;

public class ExcelReportInput {
    private String RH[], RD, RT;
    
    public ExcelReportInput() {
        super();
    }

    public void setRH(String[] RH) {
        this.RH = RH;
    }

    public String[] getRH() {
        return RH;
    }

    public void setRD(String RD) {
        this.RD = RD;
    }

    public String getRD() {
        return RD;
    }

    public void setRT(String RT) {
        this.RT = RT;
    }

    public String getRT() {
        return RT;
    }
}

package com.sfr.core.bean;

public class MarineOrder extends Order {
    private static final long serialVersionUID = 1L;
    private String nameOfVessel; //boat name

    public void setNameOfVessel(String nameOfVessel) {
        this.nameOfVessel = nameOfVessel;
    }

    public String getNameOfVessel() {
        return nameOfVessel;
    }
}

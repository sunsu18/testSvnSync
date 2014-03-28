package com.sfr.core.bean;

public class WebshopOrder extends ResellerOrder{
    private static final long serialVersionUID = 1L;
    private String fillUp;

    public void setFillUp(String fillUp) {
        this.fillUp = fillUp;
    }

    public String getFillUp() {
        return fillUp;
    }
}

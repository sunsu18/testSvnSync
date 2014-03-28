package com.sfr.core.bean;

import com.sfr.util.ThreadSerialization;

public class OrderPricingInfo extends ThreadSerialization{ //class name is PricingInfo

    private static final long serialVersionUID = 1L;
    private Integer totalAmount; //16July with h,m,l--Double
    private Integer totalAmountNet; //16July with h,m,l--Double
    private Integer VAT; //16July with h,m,l--Double
    private Integer additionalAmount; //not inv but msy be order //16July with h,m,l
    private Integer shippingCharges; //not inv but msy be order//16July with h,m,l

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmountNet(Integer totalAmountNet) {
        this.totalAmountNet = totalAmountNet;
    }

    public Integer getTotalAmountNet() {
        return totalAmountNet;
    }

    public void setAdditionalAmount(Integer additionalAmount) {
        this.additionalAmount = additionalAmount;
    }

    public Integer getAdditionalAmount() {
        return additionalAmount;
    }

    public void setShippingCharges(Integer shippingCharges) {
        this.shippingCharges = shippingCharges;
    }

    public Integer getShippingCharges() {
        return shippingCharges;
    }

    public void setVAT(Integer VAT) {
        this.VAT = VAT;
    }

    public Integer getVAT() {
        return VAT;
    }
}

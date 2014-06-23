package com.sfr.core.bean;

import com.sfr.util.ThreadSerialization;

public class OrderAdditionalInfo extends ThreadSerialization{
    private static final long serialVersionUID = 1L;
    private String referenceNumber; //Is this same as Customer reference number?
    private String contactPerson;
    private String contactNote;
    private String contactPhone;

    private String deliveryInfoFreeText;
    private String billingInfoFreeText;
    private String shippingInfoFreeText;
    private String invoiceInfoFreeText;

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactNote(String contactNote) {
        this.contactNote = contactNote;
    }

    public String getContactNote() {
        return contactNote;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setDeliveryInfoFreeText(String deliveryInfoFreeText) {
        this.deliveryInfoFreeText = deliveryInfoFreeText;
    }

    public String getDeliveryInfoFreeText() {
        return deliveryInfoFreeText;
    }

    public void setBillingInfoFreeText(String billingInfoFreeText) {
        this.billingInfoFreeText = billingInfoFreeText;
    }

    public String getBillingInfoFreeText() {
        return billingInfoFreeText;
    }

    public void setShippingInfoFreeText(String shippingInfoFreeText) {
        this.shippingInfoFreeText = shippingInfoFreeText;
    }

    public String getShippingInfoFreeText() {
        return shippingInfoFreeText;
    }

    public void setInvoiceInfoFreeText(String invoiceInfoFreeText) {
        this.invoiceInfoFreeText = invoiceInfoFreeText;
    }

    public String getInvoiceInfoFreeText() {
        return invoiceInfoFreeText;
    }
}

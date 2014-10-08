package com.sfr.core.bean;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

public class Invoice extends BaseBean {

    private static final long serialVersionUID = 1L;
    private int invoiceNumber; // verified - used in header
    private Date invoicedate; // verified - used for SS Invoice
    private Date invoiceDueDate; // verified - Forfallsdato - used in header
    private Date billingDate; // verified - used in header
    private String invoiceCurrrencyCode; // verified - used in header
    private String paymentTypeCode; // verified - used in header - will need UDC conversion
    private String paymentTypeDescription; // verified - used in header - UDC description for payment Type Code
    private String mediaObjectLink; // verified - useed as Invoice level info - UCM URL link
    private String status; //verified - used in header as Invoice status
    private String message; // verified - used for SS Invoice
    private String description; // verified - used for SS InvoiceType Description (Document type desc)
    private String payerName; // Verified - Displayed in header
    /*
     * Invoice Amount = Net Invoice AMount + Additional charges
     * Net Invoice AMount = Invoice Amount - Additional charges
     * Gross Invoice AMount = Invoice amount + VAT
     */
    private Double invoiceAmount; // Verified - Displayed in header
    private Double grossInvoiceAmount; // Verified - Displayed in header
    private Double VAT; // Verified - Displayed in header
    private Double openAmount; // used for SS Invoice to hold the open(due) amount
    private String invoiceType; // Verified - Not displayed  document typre for SS Invoice
    private String invoiceCompany; // Verified - Not displayed
    private String ssInvoiceNumber; // verified - used for SS Invoice
    private Double netInvoiceAmount; // Verified - Not displayed
    private Address payerAddress; // Verified - Displayed in header
    //    private BigDecimal ssTotalAmount;//for service station total amount
    private List<OrderedProduct> orderedProductList;
    private String payStatus; //for service station display of status
    private Integer nettingId;
    private Date ssInvoiceDueDate;

    public void setOrderedProductList(List<OrderedProduct> orderedProductList) {
        this.orderedProductList = orderedProductList;
    }

    public List<OrderedProduct> getOrderedProductList() {
        return orderedProductList;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceCompany(String invoiceCompany) {
        this.invoiceCompany = invoiceCompany;
    }

    public String getInvoiceCompany() {
        return invoiceCompany;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setInvoiceCurrrencyCode(String invoiceCurrrencyCode) {
        this.invoiceCurrrencyCode = invoiceCurrrencyCode;
    }

    public String getInvoiceCurrrencyCode() {
        return invoiceCurrrencyCode;
    }

    public void setInvoiceDueDate(Date invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
    }

    public Date getInvoiceDueDate() {
        return invoiceDueDate;
    }

    public void setMediaObjectLink(String mediaObjectLink) {
        this.mediaObjectLink = mediaObjectLink;
    }

    public String getMediaObjectLink() {
        return mediaObjectLink;
    }


    public void setPaymentTypeCode(String paymentTypeCode) {
        this.paymentTypeCode = paymentTypeCode;
    }

    public String getPaymentTypeCode() {
        return paymentTypeCode;
    }

    public void setPaymentTypeDescription(String paymentTypeDescription) {
        this.paymentTypeDescription = paymentTypeDescription;
    }

    public String getPaymentTypeDescription() {
        return paymentTypeDescription;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerAddress(Address payerAddress) {
        this.payerAddress = payerAddress;
    }

    public Address getPayerAddress() {
        return payerAddress;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setGrossInvoiceAmount(Double grossInvoiceAmount) {
        this.grossInvoiceAmount = grossInvoiceAmount;
    }

    public Double getGrossInvoiceAmount() {
        return grossInvoiceAmount;
    }

    public void setVAT(Double VAT) {
        this.VAT = VAT;
    }

    public Double getVAT() {
        return VAT;
    }

    public void setNetInvoiceAmount(Double netInvoiceAmount) {
        this.netInvoiceAmount = netInvoiceAmount;
    }

    public Double getNetInvoiceAmount() {
        return netInvoiceAmount;
    }

    public void setInvoicedate(Date invoicedate) {
        this.invoicedate = invoicedate;
    }

    public Date getInvoicedate() {
        return invoicedate;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setOpenAmount(Double openAmount) {
        this.openAmount = openAmount;
    }

    public Double getOpenAmount() {
        return openAmount;
    }

    public void setSsInvoiceNumber(String ssInvoiceNumber) {
        this.ssInvoiceNumber = ssInvoiceNumber;
    }

    public String getSsInvoiceNumber() {
        return ssInvoiceNumber;
    }


    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setNettingId(Integer nettingId) {
        this.nettingId = nettingId;
    }

    public Integer getNettingId() {
        return nettingId;
    }

    public void setSsInvoiceDueDate(Date ssInvoiceDueDate) {
        this.ssInvoiceDueDate = ssInvoiceDueDate;
    }

    public Date getSsInvoiceDueDate() {
        return ssInvoiceDueDate;
    }
}

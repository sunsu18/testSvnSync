package com.sfr.core.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.Date;
import java.util.List;

public class Account extends BaseBean {
    private static final long serialVersionUID = 1L;
    private Integer payerId;
    private String payer;
    private Double openAmount;
    private String referenceNumber;
    private List<AccountDetail> accountDetailList;
    private Address AddressInfo;
    private String CurrrencyCode;
    /*Variables added for AR/AP Inquiry */
    private Integer payerNumber;
    private BigInteger customerNumber;
    private Date fromDate;
    private Date toDate;
    private BigDecimal totalOpeningBalance;
    private BigDecimal totalClosingBalance;

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayer() {
        return payer;
    }

    public void setOpenAmount(Double openAmount) {
        this.openAmount = openAmount;
    }

    public Double getOpenAmount() {
        return openAmount;
    }

    public void setAccountDetailList(List<AccountDetail> accountDetailList) {
        this.accountDetailList = accountDetailList;
    }

    public List<AccountDetail> getAccountDetailList() {
        return accountDetailList;
    }

    public void setPayerId(Integer payerId) {
        this.payerId = payerId;
    }

    public Integer getPayerId() {
        return payerId;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setAddressInfo(Address AddressInfo) {
        this.AddressInfo = AddressInfo;
    }

    public Address getAddressInfo() {
        return AddressInfo;
    }

    public void setCurrrencyCode(String CurrrencyCode) {
        this.CurrrencyCode = CurrrencyCode;
    }

    public String getCurrrencyCode() {
        return CurrrencyCode;
    }

    public void setPayerNumber(Integer payerNumber) {
        this.payerNumber = payerNumber;
    }

    public Integer getPayerNumber() {
        return payerNumber;
    }

    public void setCustomerNumber(BigInteger customerNumber) {
        this.customerNumber = customerNumber;
    }

    public BigInteger getCustomerNumber() {
        return customerNumber;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setTotalOpeningBalance(BigDecimal totalOpeningBalance) {
        this.totalOpeningBalance = totalOpeningBalance;
    }

    public BigDecimal getTotalOpeningBalance() {
        return totalOpeningBalance;
    }

    public void setTotalClosingBalance(BigDecimal totalClosingBalance) {
        this.totalClosingBalance = totalClosingBalance;
    }

    public BigDecimal getTotalClosingBalance() {
        return totalClosingBalance;
    }
}

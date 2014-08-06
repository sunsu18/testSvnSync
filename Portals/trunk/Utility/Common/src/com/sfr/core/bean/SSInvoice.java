package com.sfr.core.bean;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

public class SSInvoice extends BaseBean {
    private String nextFetchDocumentNo;
    private String previousFetchDocumentNo;
    private String nextFetchTableDetails;
    private String previousFetchTableDetails;
    private String nextFetchDocumentType;
    private String previousFetchDocumentType;
    private BigDecimal ssTotalAmount; //for service station pagewise total amount
    private List<Invoice> ssInvoiceList;
    private BigDecimal grandTotalAmount; //for service station grand total amount
    private Date nextFetchOrderDate;
    private Date previousFetchOrderDate;
    private String payItemForNextResults;
    private String payItemForPreviousResults;

    public void setNextFetchDocumentNo(String nextFetchDocumentNo) {
        this.nextFetchDocumentNo = nextFetchDocumentNo;
    }

    public String getNextFetchDocumentNo() {
        return nextFetchDocumentNo;
    }

    public void setPreviousFetchDocumentNo(String previousFetchDocumentNo) {
        this.previousFetchDocumentNo = previousFetchDocumentNo;
    }

    public String getPreviousFetchDocumentNo() {
        return previousFetchDocumentNo;
    }

    public void setNextFetchTableDetails(String nextFetchTableDetails) {
        this.nextFetchTableDetails = nextFetchTableDetails;
    }

    public String getNextFetchTableDetails() {
        return nextFetchTableDetails;
    }

    public void setPreviousFetchTableDetails(String previousFetchTableDetails) {
        this.previousFetchTableDetails = previousFetchTableDetails;
    }

    public String getPreviousFetchTableDetails() {
        return previousFetchTableDetails;
    }

    public void setNextFetchDocumentType(String nextFetchDocumentType) {
        this.nextFetchDocumentType = nextFetchDocumentType;
    }

    public String getNextFetchDocumentType() {
        return nextFetchDocumentType;
    }

    public void setPreviousFetchDocumentType(String previousFetchDocumentType) {
        this.previousFetchDocumentType = previousFetchDocumentType;
    }

    public String getPreviousFetchDocumentType() {
        return previousFetchDocumentType;
    }

    public void setSsTotalAmount(BigDecimal ssTotalAmount) {
        this.ssTotalAmount = ssTotalAmount;
    }

    public BigDecimal getSsTotalAmount() {
        return ssTotalAmount;
    }

    public void setSsInvoiceList(List<Invoice> ssInvoiceList) {
        this.ssInvoiceList = ssInvoiceList;
    }

    public List<Invoice> getSsInvoiceList() {
        return ssInvoiceList;
    }

    public void setGrandTotalAmount(BigDecimal grandTotalAmount) {
        this.grandTotalAmount = grandTotalAmount;
    }

    public BigDecimal getGrandTotalAmount() {
        return grandTotalAmount;
    }

    public void setNextFetchOrderDate(Date nextFetchOrderDate) {
        this.nextFetchOrderDate = nextFetchOrderDate;
    }

    public Date getNextFetchOrderDate() {
        return nextFetchOrderDate;
    }

    public void setPreviousFetchOrderDate(Date previousFetchOrderDate) {
        this.previousFetchOrderDate = previousFetchOrderDate;
    }

    public Date getPreviousFetchOrderDate() {
        return previousFetchOrderDate;
    }

    public void setPayItemForNextResults(String payItemForNextResults) {
        this.payItemForNextResults = payItemForNextResults;
    }

    public String getPayItemForNextResults() {
        return payItemForNextResults;
    }

    public void setPayItemForPreviousResults(String payItemForPreviousResults) {
        this.payItemForPreviousResults = payItemForPreviousResults;
    }

    public String getPayItemForPreviousResults() {
        return payItemForPreviousResults;
    }
}

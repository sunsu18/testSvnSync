package com.sfr.core.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.Date;

public class AccountDetail extends BaseBean {
    private static final long serialVersionUID = 1L;
    private Integer payerId;
    private String payStatus;
    private BigInteger documentNo;
    private String documentTypeDesc;
    private Date generalLedger;
    private Date reciepteDate;
    private Double incomingAmount;
    private Double outgoingAmount;
    private String CurrrencyCode;
    /* AR/AP INQ */
    private BigInteger billingDoc;
    private BigDecimal amount;
    private String docType;
    private String reference;
    private String assignment;
    private Date docDate;
    private Date dueDate;
    private BigInteger clearingdoc;
    private String textDD;
    private String dueStatus;
    private String attachment;

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }


    public void setDocumentTypeDesc(String documentTypeDesc) {
        this.documentTypeDesc = documentTypeDesc;
    }

    public String getDocumentTypeDesc() {
        return documentTypeDesc;
    }

    public void setGeneralLedger(Date generalLedger) {
        this.generalLedger = generalLedger;
    }

    public Date getGeneralLedger() {
        return generalLedger;
    }

    public void setRecieptDate(Date reciepteDate) {
        this.reciepteDate = reciepteDate;
    }

    public Date getRecieptDate() {
        return reciepteDate;
    }

    public void setIncomingAmount(Double incomingAmount) {
        this.incomingAmount = incomingAmount;
    }

    public Double getIncomingAmount() {
        return incomingAmount;
    }

    public void setOutgoingAmount(Double outgoingAmount) {
        this.outgoingAmount = outgoingAmount;
    }

    public Double getOutgoingAmount() {
        return outgoingAmount;
    }

    public void setPayerId(Integer payerId) {
        this.payerId = payerId;
    }

    public Integer getPayerId() {
        return payerId;
    }

    public void setCurrrencyCode(String CurrrencyCode) {
        this.CurrrencyCode = CurrrencyCode;
    }

    public String getCurrrencyCode() {
        return CurrrencyCode;
    }


    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocType() {
        return docType;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setClearingdoc(BigInteger clearingdoc) {
        this.clearingdoc = clearingdoc;
    }

    public BigInteger getClearingdoc() {
        return clearingdoc;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setTextDD(String textDD) {
        this.textDD = textDD;
    }

    public String getTextDD() {
        return textDD;
    }

    public void setDocumentNo(BigInteger documentNo) {
        this.documentNo = documentNo;
    }

    public BigInteger getDocumentNo() {
        return documentNo;
    }

    public void setBillingDoc(BigInteger billingDoc) {
        this.billingDoc = billingDoc;
    }

    public BigInteger getBillingDoc() {
        return billingDoc;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setDueStatus(String dueStatus) {
        this.dueStatus = dueStatus;
    }

    public String getDueStatus() {
        return dueStatus;
    }
}

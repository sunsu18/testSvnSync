package com.sfr.core.bean;

import com.sfr.util.ThreadSerialization;

public class LedgerDetail extends ThreadSerialization {
    private static final long serialVersionUID = 1L;
    private String status;
    private String documentNo;
    private String documentType;
    private String clearing;
    private String incoming;
    private String outgoing;
    private String posting;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setClearing(String clearing) {
        this.clearing = clearing;
    }

    public String getClearing() {
        return clearing;
    }

    public void setIncoming(String incoming) {
        this.incoming = incoming;
    }

    public String getIncoming() {
        return incoming;
    }

    public void setOutgoing(String outgoing) {
        this.outgoing = outgoing;
    }

    public String getOutgoing() {
        return outgoing;
    }

    public void setPosting(String posting) {
        this.posting = posting;
    }

    public String getPosting() {
        return posting;
    }
}

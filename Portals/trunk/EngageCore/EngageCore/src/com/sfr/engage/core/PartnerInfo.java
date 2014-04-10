package com.sfr.engage.core;

import java.io.Serializable;

import java.util.List;

public class PartnerInfo implements Serializable{

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private String partnerValue = "111";
    private List<AccountInfo> accountList;
    
    public PartnerInfo() {
        super();
    }


    public void setPartnerValue(String partnerValue) {
        this.partnerValue = partnerValue;
    }

    public String getPartnerValue() {
        return partnerValue;
    }

    public void setAccountList(List<AccountInfo> accountList) {
        this.accountList = accountList;
    }

    public List<AccountInfo> getAccountList() {
        return accountList;
    }
}

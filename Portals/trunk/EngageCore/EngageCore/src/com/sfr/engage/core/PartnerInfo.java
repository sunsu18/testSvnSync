package com.sfr.engage.core;

import java.io.Serializable;

import java.util.List;

public class PartnerInfo implements Serializable{

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private String partnerValue;
    private String country;
    private List<AccountInfo> accountList;
    private boolean companyOverview;
    
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

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCompanyOverview(boolean companyOverview) {
        this.companyOverview = companyOverview;
    }

    public boolean isCompanyOverview() {
        return companyOverview;
    }
}

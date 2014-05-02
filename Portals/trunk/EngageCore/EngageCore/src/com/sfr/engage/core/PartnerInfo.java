package com.sfr.engage.core;

import java.io.Serializable;

import java.util.List;


/**
 * TODO : ASHTHA - 30, Apr, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC toString() method
 *  2. Override toString() method
 */
public class PartnerInfo implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private String partnerValue;
    private String country;
    private List<AccountInfo> accountList;
    private boolean companyOverview;

    /**
     */
    public PartnerInfo() {
        super();
    }


    /**
     * @param partnerValue
     */
    public void setPartnerValue(String partnerValue) {
        this.partnerValue = partnerValue;
    }

    /**
     * @return
     */
    public String getPartnerValue() {
        return partnerValue;
    }

    /**
     * @param accountList
     */
    public void setAccountList(List<AccountInfo> accountList) {
        this.accountList = accountList;
    }

    /**
     * @return
     */
    public List<AccountInfo> getAccountList() {
        return accountList;
    }

    /**
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param companyOverview
     */
    public void setCompanyOverview(boolean companyOverview) {
        this.companyOverview = companyOverview;
    }

    /**
     * @return
     */
    public boolean isCompanyOverview() {
        return companyOverview;
    }
}

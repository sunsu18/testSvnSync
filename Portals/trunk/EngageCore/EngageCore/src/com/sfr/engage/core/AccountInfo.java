package com.sfr.engage.core;

import java.io.Serializable;

import java.util.List;

public class AccountInfo implements Serializable{

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private List<CardGroupInfo> cardGroup;
    private boolean accountOverview;
        
    public AccountInfo() {
        super();
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setCardGroup(List<CardGroupInfo> cardGroup) {
        this.cardGroup = cardGroup;
    }

    public List<CardGroupInfo> getCardGroup() {
        return cardGroup;
    }

    public void setAccountOverview(boolean accountOverview) {
        this.accountOverview = accountOverview;
    }

    public boolean isAccountOverview() {
        return accountOverview;
    }
}

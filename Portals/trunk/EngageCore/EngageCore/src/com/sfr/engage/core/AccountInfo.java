package com.sfr.engage.core;

import java.util.List;

public class AccountInfo {
    
    private String accountNumber;
    private List<CardGroupInfo> cardGroup;
        
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
}

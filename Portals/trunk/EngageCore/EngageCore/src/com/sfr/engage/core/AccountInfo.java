package com.sfr.engage.core;

import java.io.Serializable;

import java.util.List;


/**
 * TODO : ASHTHA - 30, Apr, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC toString() method
 *  2. Override toString() method
 */
public class AccountInfo implements Comparable<AccountInfo>, Serializable {

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private List<CardGroupInfo> cardGroup;
    private boolean accountOverview;

    /**
     */
    public AccountInfo() {
        super();
    }

    /**
     * @param accountNumber
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param cardGroup
     */
    public void setCardGroup(List<CardGroupInfo> cardGroup) {
        this.cardGroup = cardGroup;
    }

    /**
     * @return
     */
    public List<CardGroupInfo> getCardGroup() {
        return cardGroup;
    }

    /**
     * @param accountOverview
     */
    public void setAccountOverview(boolean accountOverview) {
        this.accountOverview = accountOverview;
    }

    /**
     * @return
     */
    public boolean isAccountOverview() {
        return accountOverview;
    }

    /**
     * To sort the list of Account objects based on Account number.
     * @param o - Object with which the current Account has to be compared
     * @return Returns
     */
    public int compareTo(AccountInfo o) {
        return this.getAccountNumber().compareTo(o.getAccountNumber());
    }
}

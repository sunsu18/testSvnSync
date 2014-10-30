package com.sfr.core.bean;

import java.util.List;

public class Ledger extends BaseBean {
    private static final long serialVersionUID = 1L;
    private String payer;
    private String balance;
    private String overDue;
    private List<LedgerDetail> ledgerDetailList;

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayer() {
        return payer;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }

    public void setOverDue(String overDue) {
        this.overDue = overDue;
    }

    public String getOverDue() {
        return overDue;
    }

    public void setLedgerDetailList(List<LedgerDetail> ledgerDetailList) {
        this.ledgerDetailList = ledgerDetailList;
    }

    public List<LedgerDetail> getLedgerDetailList() {
        return ledgerDetailList;
    }
}

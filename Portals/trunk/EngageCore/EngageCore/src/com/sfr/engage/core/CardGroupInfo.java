package com.sfr.engage.core;

import java.io.Serializable;

import java.util.List;

public class CardGroupInfo implements Serializable {

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private String cardGroupID;
    private String cardGroupMainType;
    private String cardGroupSubType;
    private String cardGroupSeq;
    private List<CardInfo> card;
    
    
    public CardGroupInfo() {
        super();
    }

    public void setCardGroupID(String cardGroupID) {
        this.cardGroupID = cardGroupID;
    }

    public String getCardGroupID() {
        return cardGroupID;
    }

    public void setCard(List<CardInfo> card) {
        this.card = card;
    }

    public List<CardInfo> getCard() {
        return card;
    }

    public void setCardGroupMainType(String cardGroupMainType) {
        this.cardGroupMainType = cardGroupMainType;
    }

    public String getCardGroupMainType() {
        return cardGroupMainType;
    }

    public void setCardGroupSubType(String cardGroupSubType) {
        this.cardGroupSubType = cardGroupSubType;
    }

    public String getCardGroupSubType() {
        return cardGroupSubType;
    }

    public void setCardGroupSeq(String cardGroupSeq) {
        this.cardGroupSeq = cardGroupSeq;
    }

    public String getCardGroupSeq() {
        return cardGroupSeq;
    }
}

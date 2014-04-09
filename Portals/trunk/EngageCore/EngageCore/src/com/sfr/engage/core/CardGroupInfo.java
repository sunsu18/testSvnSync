package com.sfr.engage.core;

import java.util.List;

public class CardGroupInfo {
    
    private String cardGroupID;
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
}

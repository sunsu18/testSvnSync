package com.sfr.engage.core;

import java.io.Serializable;

public class CardInfo implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private String cardID;
    private String externalCardID;
    public CardInfo() {
        super();
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getCardID() {
        return cardID;
    }

    public void setExternalCardID(String externalCardID) {
        this.externalCardID = externalCardID;
    }

    public String getExternalCardID() {
        return externalCardID;
    }
}

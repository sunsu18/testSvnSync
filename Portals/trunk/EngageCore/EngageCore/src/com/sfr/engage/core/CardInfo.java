package com.sfr.engage.core;

import java.io.Serializable;


public class CardInfo implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private String cardID;
    private String externalCardID;
    private String blockAction;
    private String displayCardNumber;
    private String cardTextline2;
    private boolean cardOverview;

    /**
     */
    public CardInfo() {
        super();
    }

    /**
     * @param cardID
     */
    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    /**
     * @return
     */
    public String getCardID() {
        return cardID;
    }

    /**
     * @param externalCardID
     */
    public void setExternalCardID(String externalCardID) {
        this.externalCardID = externalCardID;
    }

    /**
     * @return
     */
    public String getExternalCardID() {
        return externalCardID;
    }


    /**
     * @param cardOverview
     */
    public void setCardOverview(boolean cardOverview) {
        this.cardOverview = cardOverview;
    }

    /**
     * @return
     */
    public boolean isCardOverview() {
        return cardOverview;
    }

    /**
     * @param cardTextline2
     */
    public void setCardTextline2(String cardTextline2) {
        this.cardTextline2 = cardTextline2;
    }

    /**
     * @return
     */
    public String getCardTextline2() {
        return cardTextline2;
    }

    public void setDisplayCardNumber(String displayCardNumber) {
        this.displayCardNumber = displayCardNumber;
    }

    public String getDisplayCardNumber() {
        return displayCardNumber;
    }

    public void setBlockAction(String blockAction) {
        this.blockAction = blockAction;
    }

    public String getBlockAction() {
        return blockAction;
    }
}

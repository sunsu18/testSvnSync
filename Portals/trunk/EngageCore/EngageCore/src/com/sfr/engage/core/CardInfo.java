package com.sfr.engage.core;

import java.io.Serializable;

/**
 * TODO : ASHTHA - 30, Apr, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC toString() method
 *  2. Override toString() method
 */
public class CardInfo implements Comparable<CardInfo>, Serializable {
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

    /**
     * To sort the list of Account objects based on Account number.
     * @param o - Object with which the current Account has to be compared
     * @return Returns
     */
    public int compareTo(CardInfo o) {
        return this.getCardID().compareTo(o.getCardID());
    }

    public void setBlockAction(String blockAction) {
        this.blockAction = blockAction;
    }

    public String getBlockAction() {
        return blockAction;
    }
}

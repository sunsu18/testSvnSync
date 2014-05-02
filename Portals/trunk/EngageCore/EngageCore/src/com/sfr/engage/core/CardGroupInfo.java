package com.sfr.engage.core;

import java.io.Serializable;

import java.util.List;

/**
 * TODO : ASHTHA - 30, Apr, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC toString() method
 *  2. Override toString() method
 */
public class CardGroupInfo implements Serializable {

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private String cardGroupID;
    private String cardGroupMainType;
    private String cardGroupSubType;
    private String cardGroupSeq;
    private List<CardInfo> card;
    private boolean cardGroupOverview;


    /**
     */
    public CardGroupInfo() {
        super();
    }

    /**
     * @param cardGroupID
     */
    public void setCardGroupID(String cardGroupID) {
        this.cardGroupID = cardGroupID;
    }

    /**
     * @return
     */
    public String getCardGroupID() {
        return cardGroupID;
    }

    /**
     * @param card
     */
    public void setCard(List<CardInfo> card) {
        this.card = card;
    }

    /**
     * @return
     */
    public List<CardInfo> getCard() {
        return card;
    }

    /**
     * @param cardGroupMainType
     */
    public void setCardGroupMainType(String cardGroupMainType) {
        this.cardGroupMainType = cardGroupMainType;
    }

    /**
     * @return
     */
    public String getCardGroupMainType() {
        return cardGroupMainType;
    }

    /**
     * @param cardGroupSubType
     */
    public void setCardGroupSubType(String cardGroupSubType) {
        this.cardGroupSubType = cardGroupSubType;
    }

    /**
     * @return
     */
    public String getCardGroupSubType() {
        return cardGroupSubType;
    }

    /**
     * @param cardGroupSeq
     */
    public void setCardGroupSeq(String cardGroupSeq) {
        this.cardGroupSeq = cardGroupSeq;
    }

    /**
     * @return
     */
    public String getCardGroupSeq() {
        return cardGroupSeq;
    }

    /**
     * @param cardGroupOverview
     */
    public void setCardGroupOverview(boolean cardGroupOverview) {
        this.cardGroupOverview = cardGroupOverview;
    }

    /**
     * @return
     */
    public boolean isCardGroupOverview() {
        return cardGroupOverview;
    }
}

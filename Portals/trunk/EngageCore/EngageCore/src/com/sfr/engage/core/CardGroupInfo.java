package com.sfr.engage.core;

import java.io.Serializable;

import java.util.List;


/**
 * TODO : ASHTHA - 30, Apr, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC toString() method
 *  2. Override toString() method
 */
public class CardGroupInfo implements Comparable<CardGroupInfo>,Serializable {

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private String cardGroupID;
    private String displayCardGroupIdName;
    private String cardGroupMainType;
    private String cardGroupSubType;
    private String cardGroupSeq;
    //CardList constiting of all cards Active,Temp block and permanent block cards (including expired cards)
    private List<CardInfo> card;

    //CardList constiting of all  Active,Temp block cards (excluding expired cards)
    private List<CardInfo> unblockedCardList;

    //CardList constiting of all  Active cards (excluding expired cards)
    private List<CardInfo> activeCardList;

    //CardList constiting of all Temp block cards (excluding expired cards)
    private List<CardInfo> tempBlockCardList;

    //CardList constiting of all permanent block cards (including expired cards)
    private List<CardInfo> perBlockCardList;

    //CardList constiting of all Active and permanent block cards (including expired cards)
    private List<CardInfo> perBlockAndActiveCardList;

    //CardList constiting of allTemp block and permanent block cards (including expired cards)
    private List<CardInfo> perBlockAndTempBlockCardList;

    private String cardGroupName;
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

    public void setCardGroupName(String cardGroupName) {
        this.cardGroupName = cardGroupName;
    }

    public String getCardGroupName() {
        return cardGroupName;
    }

    public void setUnblockedCardList(List<CardInfo> unblockedCardList) {
        this.unblockedCardList = unblockedCardList;
    }

    public List<CardInfo> getUnblockedCardList() {
        return unblockedCardList;
    }

    public void setDisplayCardGroupIdName(String displayCardGroupIdName) {
        this.displayCardGroupIdName = displayCardGroupIdName;
    }

    public String getDisplayCardGroupIdName() {
        return displayCardGroupIdName;
    }

    /**
     * To sort the list of Account objects based on Account number.
     * @param o - Object with which the current Account has to be compared
     * @return Returns
     */
    public int compareTo(CardGroupInfo o) {
        return this.getDisplayCardGroupIdName().compareTo(o.getDisplayCardGroupIdName());
    }

    public void setActiveCardList(List<CardInfo> activeCardList) {
        this.activeCardList = activeCardList;
    }

    public List<CardInfo> getActiveCardList() {
        return activeCardList;
    }

    public void setTempBlockCardList(List<CardInfo> tempBlockCardList) {
        this.tempBlockCardList = tempBlockCardList;
    }

    public List<CardInfo> getTempBlockCardList() {
        return tempBlockCardList;
    }

    public void setPerBlockCardList(List<CardInfo> perBlockCardList) {
        this.perBlockCardList = perBlockCardList;
    }

    public List<CardInfo> getPerBlockCardList() {
        return perBlockCardList;
    }

    public void setPerBlockAndActiveCardList(List<CardInfo> perBlockAndActiveCardList) {
        this.perBlockAndActiveCardList = perBlockAndActiveCardList;
    }

    public List<CardInfo> getPerBlockAndActiveCardList() {
        return perBlockAndActiveCardList;
    }

    public void setPerBlockAndTempBlockCardList(List<CardInfo> perBlockAndTempBlockCardList) {
        this.perBlockAndTempBlockCardList = perBlockAndTempBlockCardList;
    }

    public List<CardInfo> getPerBlockAndTempBlockCardList() {
        return perBlockAndTempBlockCardList;
    }
}

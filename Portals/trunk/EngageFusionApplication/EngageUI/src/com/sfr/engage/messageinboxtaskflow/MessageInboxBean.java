package com.sfr.engage.messageinboxtaskflow;


import com.sfr.core.bean.User;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.core.ValueListSplit;
import com.sfr.engage.model.queries.rvo.PrtCustomerCardMapRVO1RowImpl;
import com.sfr.engage.model.queries.uvo.PrtNotificationVOImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.ADFUtils;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;
import com.sfr.util.validations.Conversion;

import java.io.IOException;
import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.DBSequence;


public class MessageInboxBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    private boolean isMessageAdmin = false;
    private List<SelectItem> categoryList;
    private List<String> categoryValue = null;
    private List<SelectItem> messageTypeList;
    private List<String> messageTypeValue = null;
    private ResourceBundle resourceBundle;
    private List<SelectItem> partnerList = null;
    private List<String> partnerValue = null;
    private List<String> accountValue;
    private List<SelectItem> accountList = null;
    private List<String> cardGroupValue;
    private List<SelectItem> cardGroupList = null;
    private List<String> cardValue;
    private List<SelectItem> cardList = null;
    private String customerTypeValue;
    public static final ADFLogger LOGGER = AccessDataControl.getSFRLogger();
    private AccessDataControl accessDC = new AccessDataControl();
    private Conversion conversionUtility;
    private String langSession = "";
    private boolean resultFromTo = true;
    private boolean resultToFrom = true;
    private boolean isSearchTableVisible = false;
    private List<PartnerInfo> partnerInfoList;
    private final int minusThree = -3;
    private User userInfo;
    private String userEmail = null;
    private String accountQuery = "(";
    private String cardGroupQuery = "(";
    private String cardQuery = "((";
    private Map<String, String> mapAccountDetailListValue;
    private Map<String, String> mapAccountListValue;
    private Map<String, String> mapCardGroupListValue;
    private Map<String, String> mapCardListValue;
   

    public MessageInboxBean() {
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " constructor of Message Inbox");
        conversionUtility = new Conversion();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        resourceBundle = new EngageResourceBundle();

        if (session != null) {


            if (null != session.getAttribute(Constants.SESSION_USER_INFO)) {
                userInfo = (User)session.getAttribute(Constants.SESSION_USER_INFO);
            }

            if (userInfo != null) {

                userEmail = userInfo.getEmailID();
            }

            resetValues();

            langSession = (String)session.getAttribute(Constants.userLang);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " langSession" + langSession);


            if (session.getAttribute("account_Query_MessageInbox") != null) {
                accountQuery = session.getAttribute("account_Query_MessageInbox").toString().trim();
                mapAccountListValue = (Map<String, String>)session.getAttribute("map_Account_List_MessageInbox");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account Query & mapAccountList is found");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account " + accountQuery);
            }

            if (session.getAttribute("cardGroup_Query_MessageInbox") != null) {
                cardGroupQuery = session.getAttribute("cardGroup_Query_MessageInbox").toString().trim();
                mapCardGroupListValue = (Map<String, String>)session.getAttribute("map_CardGroup_List_MessageInbox");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Query & mapCardGroupList is found");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup " + cardGroupQuery);
            }
            if (session.getAttribute("card_Query_MessageInbox") != null) {
                cardQuery = session.getAttribute("card_Query_MessageInbox").toString().trim();
                mapCardListValue = (Map<String, String>)session.getAttribute("map_Card_List_MessageInbox");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "card Query & mapCardList is found");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card " + cardQuery);
            }


            Date dateNow = new java.util.Date();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(dateNow);
            gc.add(GregorianCalendar.MONTH, minusThree);
            Date dateBefore = gc.getTime();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yy");
            String tmpFrom = dateformat.format(dateBefore);
            String tmpTo = dateformat.format(dateNow);


            removeWhereClause(); //method to remove dynamic where clause
            long estimetedRowCount =
                applyWhereClause(false, true, populateStringValues(getCategoryValue().toString()), populateStringValues(getMessageTypeValue().toString()),
                                 tmpFrom, tmpTo); //method to execute query


            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Messages Row Count " + estimetedRowCount);
            if (estimetedRowCount > 0) {
                isSearchTableVisible = true;
            } else {
                isSearchTableVisible = false;
            }


        }
    }

    private void resetValues() {
        partnerList = new ArrayList<SelectItem>();
        partnerValue = new ArrayList<String>();
        accountList = new ArrayList<SelectItem>();
        accountValue = new ArrayList<String>();
        cardGroupList = new ArrayList<SelectItem>();
        cardGroupValue = new ArrayList<String>();
        cardValue = new ArrayList<String>();
        cardList = new ArrayList<SelectItem>();
        messageTypeValue = new ArrayList<String>();
        categoryValue = new ArrayList<String>();
        categoryValue.add(resourceBundle.getObject("ENGAGE_CATEGORY_ADMIN").toString());
        categoryValue.add(resourceBundle.getObject(Constants.ENGAGECATEGORYNONADMINLITERAL).toString());
        messageTypeValue.add("NO");
        messageTypeValue.add("YES");

        if (session.getAttribute("Partner_Object_List") != null) {
            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
        }

        if (partnerInfoList != null && partnerInfoList.size() > 0) {
            for (int i = 0; i < partnerInfoList.size(); i++) {
                if (partnerInfoList.get(i).getPartnerName() != null && partnerInfoList.get(i).getPartnerValue() != null) {
                    SelectItem selectItemPartner = new SelectItem();
                    selectItemPartner.setLabel(partnerInfoList.get(i).getPartnerName().toString());
                    selectItemPartner.setValue(partnerInfoList.get(i).getPartnerValue().toString());
                    partnerList.add(selectItemPartner);
                    partnerValue.add(partnerInfoList.get(i).getPartnerValue().toString());
                }

                if (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {
                    for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
                        if (partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() != null) {
                            SelectItem selectItem = new SelectItem();
                            selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            accountList.add(selectItem);
                            accountValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup() != null &&
                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size() > 0) {

                                for (int cg = 0; cg < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); cg++) {

                                    SelectItem selectItemCardGroup = new SelectItem();
                                    selectItemCardGroup.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getDisplayCardGroupIdName().toString());
                                    selectItemCardGroup.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                                 partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCardGroupID().toString());
                                    cardGroupList.add(selectItemCardGroup);
                                    cardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                       partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCardGroupID().toString());

                                    if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard() != null &&
                                        partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().size() > 0) {
                                        for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().size(); cc++) {
                                            SelectItem selectItemCard = new SelectItem();
                                            selectItemCard.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
                                            selectItemCard.setValue(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                            cardList.add(selectItemCard);
                                            cardValue.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                        }
                                    }
                                    Collections.sort(cardGroupList, comparator);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private Comparator<SelectItem> comparator = new Comparator<SelectItem>() {
        @Override
        public int compare(SelectItem s1, SelectItem s2) {
            return s1.getLabel().compareTo(s2.getLabel());
        }
    };

    /**
     * @return bindings Object
     */
    public Bindings getBindings() {
        if (bindings == null) {
            bindings = new Bindings();
        }
        return bindings;
    }

    public void setIsMessageAdmin(boolean isMessageAdmin) {
        this.isMessageAdmin = isMessageAdmin;
    }

    public boolean isIsMessageAdmin() {
        return isMessageAdmin;
    }


    public void setCategoryList(List<SelectItem> categoryList) {
        this.categoryList = categoryList;
    }

    public List<SelectItem> getCategoryList() {
        if (categoryList == null) {
            categoryList = new ArrayList<SelectItem>();
            SelectItem selectItem = new SelectItem();
            if (resourceBundle.containsKey(Constants.ENGAGE_CATEGORY_ADMIN_LITERAL)) {
                selectItem.setLabel(resourceBundle.getObject(Constants.ENGAGE_CATEGORY_ADMIN_LITERAL).toString());
                selectItem.setValue(resourceBundle.getObject(Constants.ENGAGE_CATEGORY_ADMIN_LITERAL).toString());
                categoryList.add(selectItem);
            }
            SelectItem selectItem1 = new SelectItem();
            if (resourceBundle.containsKey(Constants.ENGAGECATEGORYNONADMINLITERAL)) {
                selectItem1.setLabel(resourceBundle.getObject(Constants.ENGAGECATEGORYNONADMINLITERAL).toString());
                selectItem1.setValue(resourceBundle.getObject(Constants.ENGAGECATEGORYNONADMINLITERAL).toString());
                categoryList.add(selectItem1);
            }

        }
        return categoryList;
    }

    public void setMessageTypeList(List<SelectItem> messageTypeList) {
        this.messageTypeList = messageTypeList;
    }

    public List<SelectItem> getMessageTypeList() {
        if (messageTypeList == null) {
            messageTypeList = new ArrayList<SelectItem>();

            SelectItem selectItem1 = new SelectItem();
            if (resourceBundle.containsKey("ENG_READ")) {
                selectItem1.setLabel(resourceBundle.getObject("ENG_READ").toString());
                selectItem1.setValue("NO");
                messageTypeList.add(selectItem1);
            }
            SelectItem selectItem2 = new SelectItem();
            if (resourceBundle.containsKey("ENG_UNREAD")) {
                selectItem2.setLabel(resourceBundle.getObject("ENG_UNREAD").toString());
                selectItem2.setValue("YES");
                messageTypeList.add(selectItem2);
            }
        }
        return messageTypeList;
    }


    public String[] stringSplitter(String passedVal) {

        return passedVal.split(",");
    }

    public void partnerValueChangeListener(ValueChangeEvent valueChangeEvent) {

        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside partnerValueChangeListener method of Message Inbox");
        if (valueChangeEvent.getNewValue() != null) {
            accountList = new ArrayList<SelectItem>();
            accountValue = new ArrayList<String>();
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();
            cardList = new ArrayList<SelectItem>();
            cardValue = new ArrayList<String>();
            String[] partnerString = stringSplitter(populateStringValues(valueChangeEvent.getNewValue().toString()));
            if (partnerString.length > 0) {
                for (int i = 0; i < partnerInfoList.size(); i++) {
                    for (int p = 0; p < partnerString.length; p++) {
                        if (partnerInfoList.get(i).getPartnerValue().toString() != null &&
                            partnerInfoList.get(i).getPartnerValue().toString().equals(partnerString[p].trim())) {
                            if (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {
                                for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
                                    if (partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() != null) {
                                        SelectItem selectItem = new SelectItem();
                                        selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                        selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                        accountList.add(selectItem);
                                        accountValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                    }

                                    for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
                                        if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() != null) {
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                            selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                            cardGroupList.add(selectItem);
                                            cardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                               partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                        }

                                        for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++) {
                                            if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() !=
                                                null) {
                                                SelectItem selectItem = new SelectItem();
                                                selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                                selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                cardList.add(selectItem);
                                                cardValue.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Collections.sort(accountList, comparator);
                Collections.sort(cardGroupList, comparator);
                Collections.sort(cardList, comparator);
            }
        } else {
            getBindings().getAccount().setValue(null);
            getBindings().getCardGroup().setValue(null);
            getBindings().getCard().setValue(null);
            this.accountValue = null;
            this.accountList = null;
            this.cardGroupValue = null;
            this.cardGroupList = null;
            this.cardList = null;
            this.cardValue = null;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside partnerValueChangeListener method of Message Inbox");

    }

    public void accountValueChangeListener(ValueChangeEvent valueChangeEvent) {

        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside accountValueChangeListener method of Message Inbox");
        if (valueChangeEvent.getNewValue() != null) {
            String[] accountString = stringSplitter(populateStringValues(valueChangeEvent.getNewValue().toString()).replaceAll(" ", ""));
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "accountlist");
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();
            cardList = new ArrayList<SelectItem>();
            cardValue = new ArrayList<String>();
            for (int z = 0; z < partnerInfoList.size(); z++) {
                if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {

                        for (int j = 0; j < accountString.length; j++) {
                            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "accc frmo partnerlist " +
                                        partnerInfoList.get(z).getAccountList().get(i).getAccountNumber());
                            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "acc comparing " +
                                        accountString[j].substring(Constants.EIGHT, Constants.EIGHTEEN).trim());
                            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "checking bopolean " +
                                        partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().equals(accountString[j].substring(Constants.EIGHT,
                                                                                                                                            Constants.EIGHTEEN).trim()));
                            if (partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() != null &&
                                partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().equals(accountString[j].substring(Constants.EIGHT,
                                                                                                                                    Constants.EIGHTEEN).trim())) {
                                if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() > 0) {
                                    for (int k = 0; k < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size(); k++) {

                                        if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID() != null) {
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                            selectItem.setValue(partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                                partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString() +
                                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                            cardGroupList.add(selectItem);
                                            cardGroupValue.add(partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                               partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                                                        partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                        partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString() +
                                                        partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString() +
                                                        "added in cardGroupValue");
                                        }
                                        for (int cc = 0; cc < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().size(); cc++) {
                                            if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getExternalCardID() !=
                                                null) {
                                                SelectItem selectItem = new SelectItem();
                                                selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                                selectItem.setValue(partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                                    partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString() +
                                                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString() +
                                                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                cardList.add(selectItem);
                                                cardValue.add(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                                                            partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                            partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString() +
                                                            partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString() +
                                                            partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString() +
                                                            "added in cardNumberValue");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Collections.sort(accountList, comparator);
            Collections.sort(cardGroupList, comparator);
            Collections.sort(cardList, comparator);
        } else {
            getBindings().getCardGroup().setValue(null);
            getBindings().getCard().setValue(null);
            this.cardGroupValue = null;
            this.cardValue = null;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside accountValueChangeListener method of Message Inbox");

    }

    public void cgValueChangeListener(ValueChangeEvent valueChangeEvent) {

        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside cardgroupValueChangeListener method of Message Inbox");
        if (valueChangeEvent.getNewValue() != null) {
            String[] cardgroupString = stringSplitter(populateStringValues(valueChangeEvent.getNewValue().toString()).replaceAll(" ", ""));
            cardList = new ArrayList<SelectItem>();
            cardValue = new ArrayList<String>();
            for (int z = 0; z < partnerInfoList.size(); z++) {
                if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                        if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                            partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() > 0) {
                            for (int cg = 0; cg < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size(); cg++) {
                                for (int cgs = 0; cgs < cardgroupString.length; cgs++) {
                                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "cardgrp from partnerlist  " +
                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString().trim());
                                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "comparing cardgrp " +
                                                cardgroupString[cgs].substring(Constants.EIGHTEEN, Constants.TWENTYNINE).trim());
                                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "checkin boolean " +
                                                (partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                 partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() +
                                                 partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString().trim()).equals(cardgroupString[cgs].trim()));
                                    if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                                        ((partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                          partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() +
                                          partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString().trim()).equals(cardgroupString[cgs].trim()))) {
                                        for (int cc = 0; cc < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().size(); cc++) {
                                            if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID() != null &&
                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getExternalCardID() !=
                                                null) {
                                                SelectItem selectItem = new SelectItem();
                                                selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
                                                selectItem.setValue(partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                                    partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString() +
                                                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString() +
                                                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                                cardList.add(selectItem);
                                                cardValue.add(partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                              partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString() +
                                                              partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString() +
                                                              partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Collections.sort(cardGroupList, comparator);
                    Collections.sort(cardList, comparator);
                }
            }
        } else {
            getBindings().getCard().setValue(null);
            this.cardValue = null;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside cardgroupValueChangeListener method of Message Inbox");
    }


    public void clearSearchListener(ActionEvent actionEvent) {

        //        this.partnerValue = null;
        //        isSearchTableVisible = false;
        //        messageTypeValue = null;
        //        getBindings().getFromDate().setValue(null);
        //        getBindings().getToDate().setValue(null);
        //        accountList = new ArrayList<SelectItem>();
        //        accountValue = new ArrayList<String>();
        //        cardGroupValue = new ArrayList<String>();
        //        cardGroupList = new ArrayList<SelectItem>();
        //        cardValue = new ArrayList<String>();
        //        cardList = new ArrayList<SelectItem>();
        resetValues();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFromDate());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getToDate());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMessageType());

    }

    public String showErrorMessage(String errorVar) {
        if (errorVar != null && resourceBundle.containsKey(errorVar)) {

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject(errorVar), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;

        }
        return null;
    }

    public String populateStringValues(String var) {
        String passingValues = null;
        if (var != null) {
            String lovValues = var.trim();
            String selectedValues = lovValues.substring(1, lovValues.length() - 1);
            passingValues = selectedValues.trim();

        }
        return passingValues;
    }

    public void searchResultsListener(ActionEvent actionEvent) {
        searchResults();
    }

    private void searchResults() {

        String messageValue = null;
        String newFromDate = null;
        String newToDate = null;

        isSearchTableVisible = false;
        displayErrorComponent(getBindings().getMessageType(), false);
        displayErrorComponent(getBindings().getCategory(), false);

        displayErrorComponent(getBindings().getPartnerNumber(), false);
        displayErrorComponent(getBindings().getAccount(), false);
        displayErrorComponent(getBindings().getCardGroup(), false);
        displayErrorComponent(getBindings().getCard(), false);

        if (getBindings().getMessageType().getValue() != null) {

            if (getBindings().getCategory().getValue() != null && getBindings().getMessageType().getValue() != null &&
                getBindings().getFromDate().getValue() != null && getBindings().getToDate().getValue() != null) {


                if (getBindings().getFromDate().getValue() != null && getBindings().getToDate().getValue() != null) {
                    DateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
                    Date effectiveFromDate = (java.util.Date)getBindings().getFromDate().getValue();
                    Date effectiveToDate1 = (java.util.Date)getBindings().getToDate().getValue();
                    newFromDate = sdf.format(effectiveFromDate);
                    newToDate = sdf.format(effectiveToDate1);

                    if (effectiveToDate1.before(effectiveFromDate)) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "value of new from date ================>" + newFromDate);
                        showErrorMessage("ENGAGE_VALID_FROM_TO_DATE");
                        return;
                    }
                }


                String selectedCategory = "";
                if (getBindings().getCategory().getValue() != null) {
                    selectedCategory = populateStringValues(getBindings().getCategory().getValue().toString());
                }

                if (selectedCategory.trim().equalsIgnoreCase(resourceBundle.getObject("ENGAGE_CATEGORY_ADMIN").toString())) {

                    // add code for only fetching messages

                    if (getBindings().getMessageType().getValue() != null) {
                        messageValue = populateStringValues(getBindings().getMessageType().getValue().toString());
                    }


                    removeWhereClause();
                    long estimetedRowCount = applyWhereClause(true, false, selectedCategory, messageValue, newFromDate, newToDate);


                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Messages Row Count " + estimetedRowCount);
                    if (estimetedRowCount > 0) {
                        isSearchTableVisible = true;
                    } else {
                        isSearchTableVisible = false;

                        if (resourceBundle.containsKey("ENG_NO_ADMIN_MSGS")) {
                            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("NO_DATA"), "");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                        return;
                    }

                }

                else if (selectedCategory.trim().equalsIgnoreCase(resourceBundle.getObject(Constants.ENGAGECATEGORYNONADMINLITERAL).toString())) {

                    if (getBindings().getPartnerNumber().getValue() != null && getBindings().getAccount().getValue() != null &&
                        getBindings().getCardGroup().getValue() != null && getBindings().getCard().getValue() != null) {

                        // add code for only fetching alerts

                        if (getBindings().getMessageType().getValue() != null) {
                            messageValue = populateStringValues(getBindings().getMessageType().getValue().toString());
                        }

                        removeWhereClause();
                        long estimetedRowCount = applyWhereClause(false, false, selectedCategory, messageValue, newFromDate, newToDate);


                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Messages Row Count " + estimetedRowCount);
                        if (estimetedRowCount > 0) {
                            isSearchTableVisible = true;
                        } else {
                            isSearchTableVisible = false;

                            if (resourceBundle.containsKey("ENG_NO_ADMIN_MSGS")) {
                                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("NO_DATA"), "");
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                            }
                            return;
                        }


                    } else {
                        if (getBindings().getPartnerNumber().getValue() == null) {
                            displayErrorComponent(getBindings().getPartnerNumber(), true);
                        }
                        if (getBindings().getAccount().getValue() == null) {
                            displayErrorComponent(getBindings().getAccount(), true);
                        }
                        if (getBindings().getCardGroup().getValue() == null) {
                            displayErrorComponent(getBindings().getCardGroup(), true);
                        }
                        if (getBindings().getCard().getValue() == null) {
                            displayErrorComponent(getBindings().getCard(), true);
                        }

                        showErrorMessage(Constants.ENGAGE_SELECT_TRANSACTION_MANDATORY_LITERAL);
                        return;
                    }


                }

                else {

                    // add code for only fetching alerts and messages

                    if (getBindings().getPartnerNumber().getValue() != null && getBindings().getAccount().getValue() != null &&
                        getBindings().getCardGroup().getValue() != null && getBindings().getCard().getValue() != null) {


                        if (getBindings().getMessageType().getValue() != null) {
                            messageValue = populateStringValues(getBindings().getMessageType().getValue().toString());
                        }


                        removeWhereClause();
                        long estimetedRowCount = applyWhereClause(false, false, selectedCategory, messageValue, newFromDate, newToDate);


                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Messages Row Count " + estimetedRowCount);
                        if (estimetedRowCount > 0) {
                            isSearchTableVisible = true;
                        } else {
                            isSearchTableVisible = false;

                            if (resourceBundle.containsKey("ENG_NO_ADMIN_MSGS")) {
                                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("NO_DATA"), "");
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                            }
                            return;
                        }


                    } else {
                        if (getBindings().getPartnerNumber().getValue() == null) {
                            displayErrorComponent(getBindings().getPartnerNumber(), true);
                        }
                        if (getBindings().getAccount().getValue() == null) {
                            displayErrorComponent(getBindings().getAccount(), true);
                        }
                        if (getBindings().getCardGroup().getValue() == null) {
                            displayErrorComponent(getBindings().getCardGroup(), true);
                        }
                        if (getBindings().getCard().getValue() == null) {
                            displayErrorComponent(getBindings().getCard(), true);
                        }

                        showErrorMessage(Constants.ENGAGE_SELECT_TRANSACTION_MANDATORY_LITERAL);
                        return;
                    }

                }

            } else {

                if (getBindings().getCategory().getValue() == null) {
                    displayErrorComponent(getBindings().getCategory(), true);
                }


                showErrorMessage(Constants.ENGAGE_SELECT_TRANSACTION_MANDATORY_LITERAL);
                return;
            }

        } else {
            displayErrorComponent(getBindings().getMessageType(), true);
            showErrorMessage(Constants.ENGAGE_SELECT_TRANSACTION_MANDATORY_LITERAL);
        }

    }


    public void populateCustomerType() {

        Date date = new Date();
        java.sql.Date passedDate = new java.sql.Date(date.getTime());
        Set<String> cardTypeSet = new HashSet<String>();
        List<String> customerTypeList = new ArrayList<String>();
        if (session.getAttribute("cardTypeList") != null) {

            cardTypeSet = (Set<String>)session.getAttribute("cardTypeList");
        }

        List<String> cardTypeListTemp = new ArrayList<String>(cardTypeSet);
        String cardTypeList = cardTypeListTemp.toString().substring(1, cardTypeListTemp.toString().length() - 1).replace("", "");

        ViewObject prtCustomerCardMapVO = ADFUtils.getViewObject("PrtCustomerCardMapRVO1_1Iterator");
        prtCustomerCardMapVO.setNamedWhereClauseParam("cardType", cardTypeList);
        prtCustomerCardMapVO.executeQuery();
        if (prtCustomerCardMapVO.getEstimatedRowCount() != 0) {
            while (prtCustomerCardMapVO.hasNext()) {
                PrtCustomerCardMapRVO1RowImpl currRow = (PrtCustomerCardMapRVO1RowImpl)prtCustomerCardMapVO.next();
                if (currRow != null) {
                    customerTypeList.add(currRow.getCustomerType());
                    customerTypeValue = customerTypeList.toString().substring(1, customerTypeList.toString().length() - 1).replace("", "");
                }
            }
            customerTypeValue = customerTypeValue + ",ALL";
        }
    }

    public void displayErrorComponent(UIComponent component, boolean status) {

        RichSelectManyChoice soc = new RichSelectManyChoice();

        if (component instanceof RichSelectManyChoice) {
            soc = (RichSelectManyChoice)component;
            if (status) {
                soc.setStyleClass("af_mandatoryfield");
                if (component.getId().contains("selectManyChoice1") || component.getId().contains("smc1") || component.getId().contains("selectManyChoice3") ||
                    component.getId().contains("selectManyChoice4") || component.getId().contains("smc4") || component.getId().contains("smc2")) {
                    soc.setStyleClass("af_mandatoryfield");
                }

            } else {
                soc.setStyleClass("af_nonmandatoryfield");
                if (component.getId().contains("selectManyChoice1") || component.getId().contains("smc1") || component.getId().contains("selectManyChoice3") ||
                    component.getId().contains("selectManyChoice4") || component.getId().contains("smc4") || component.getId().contains("smc2")) {
                    soc.setStyleClass("af_nonmandatoryfield");
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(soc);
        }

    }

    private Boolean isComponentEmpty(UIComponent rit1) {

        RichSelectManyChoice soc = new RichSelectManyChoice();
        if (rit1 instanceof RichSelectManyChoice) {
            soc = (RichSelectManyChoice)rit1;
            if (soc.getValue() == null || soc.getValue().equals("")) {

                displayErrorComponent(soc, true);
                return true;
            } else {
                displayErrorComponent(soc, false);
                return false;
            }
        }
        return true;
    }


    public void setPartnerList(List<SelectItem> partnerList) {
        this.partnerList = partnerList;
    }

    public List<SelectItem> getPartnerList() {
        return partnerList;
    }

    public void setPartnerValue(List<String> partnerValue) {
        this.partnerValue = partnerValue;
    }

    public List<String> getPartnerValue() {
        return partnerValue;
    }

    public void setAccountValue(List<String> accountValue) {
        this.accountValue = accountValue;
    }

    public List<String> getAccountValue() {
        return accountValue;
    }

    public void setAccountList(List<SelectItem> accountList) {
        this.accountList = accountList;
    }

    public List<SelectItem> getAccountList() {
        return accountList;
    }

    public void setCardGroupValue(List<String> cardGroupValue) {
        this.cardGroupValue = cardGroupValue;
    }

    public List<String> getCardGroupValue() {
        return cardGroupValue;
    }

    public void setCardGroupList(List<SelectItem> cardGroupList) {
        this.cardGroupList = cardGroupList;
    }

    public List<SelectItem> getCardGroupList() {
        return cardGroupList;
    }

    public void setCardValue(List<String> cardValue) {
        this.cardValue = cardValue;
    }

    public List<String> getCardValue() {
        return cardValue;
    }

    public void setCardList(List<SelectItem> cardList) {
        this.cardList = cardList;
    }

    public List<SelectItem> getCardList() {
        return cardList;
    }

    public void setCustomerTypeValue(String customerTypeValue) {
        this.customerTypeValue = customerTypeValue;
    }

    public String getCustomerTypeValue() {
        return customerTypeValue;
    }


    public void setCategoryValue(List<String> categoryValue) {
        this.categoryValue = categoryValue;
    }

    public List<String> getCategoryValue() {
        return categoryValue;
    }

    public void setMessageTypeValue(List<String> messageTypeValue) {
        this.messageTypeValue = messageTypeValue;
    }

    public List<String> getMessageTypeValue() {
        return messageTypeValue;
    }

    public void categoryValueChangeEvent(ValueChangeEvent valueChangeEvent) {
        isSearchTableVisible = false;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().searchTablePanel);
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "inside category VCE");

        String categoryValueLocal = "";
        if (valueChangeEvent.getNewValue() != null) {
            categoryValueLocal = populateStringValues(valueChangeEvent.getNewValue().toString());
        }


        if (categoryValueLocal.trim().equals(resourceBundle.getObject("ENGAGE_CATEGORY_ADMIN").toString())) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "inside category VCE for Admin ");
            isMessageAdmin = true;

        } else if (categoryValueLocal.trim().equals(resourceBundle.getObject(Constants.ENGAGECATEGORYNONADMINLITERAL).toString())) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "inside category VCE for non-Admin ");
            isMessageAdmin = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartnerNumber());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());

        } else {
            isMessageAdmin = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartnerNumber());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
        }

    }

    public void closeMessagePopup(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside closeMessagePopup method of MessageInbox");
        getBindings().getMessageInboxPopUp().hide();
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside closeMessagePopup method of MessageInbox");
    }


    public void goToCardOverview(ActionEvent actionEvent) {

        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);

        try {
            String urlRedirect = request.getContextPath() + "/faces/card/account/accountsummary";
            ectx.redirect(urlRedirect);
        } catch (IOException e) {
            LOGGER.severe(accessDC.getDisplayRecord() + this.getClass() + " Error while redirecting to Account summary page");
        }
    }

    public void goToTransaction(ActionEvent actionEvent) {

        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);

        try {
            String urlRedirect = request.getContextPath() + "/faces/card/transaction/transactions";
            ectx.redirect(urlRedirect);
        } catch (IOException e) {
            LOGGER.severe(accessDC.getDisplayRecord() + this.getClass() + " Error while redirecting to Transactions overview page");
        }
    }


    public void viewMessageDetails(ActionEvent actionEvent) {
        String categoryType = "";

        String showFlag = "";

        BindingContainer localBinding = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding itr = (DCIteratorBinding)localBinding.get("PrtNotificationVO1Iterator");
        Row row = itr.getCurrentRow();
        if (row != null) {

            categoryType = (String)row.getAttribute("NotiCategory");
            oracle.jbo.domain.DBSequence notiId = (DBSequence)row.getAttribute("NotiId");
            showFlag = (String)row.getAttribute("ShowFlag");

            ViewObject prtNotificationVO = ADFUtils.getViewObject("PrtNotificationVO1Iterator");
            // create the key object
            Key key = new Key(new Object[] { notiId });
            PrtNotificationVOImpl vo = (PrtNotificationVOImpl)prtNotificationVO;
            //find the row using key reference in View Object.
            Row k = vo.getRow(key);
            //using this method we can set the new value to FirstName

            if (showFlag.equalsIgnoreCase("YES")) {

                k.setAttribute("ShowFlag", "NO");

                BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
                OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                operationBinding.execute();

            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMessageTablePopup());
        }


        if (categoryType.equalsIgnoreCase("Alerts")) {
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside viewMessageDetails method of Message Inbox");
            RichPopup.PopupHints ps = new RichPopup.PopupHints();
            getBindings().getMessageInboxPopUp().show(ps);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMessageInboxPopUp());
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside viewMessageDetails method of Message Inbox");
        } else {
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside viewMessageDetails method of Message Inbox");
            RichPopup.PopupHints ps = new RichPopup.PopupHints();
            getBindings().getMessageInboxPopUp2().show(ps);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMessageInboxPopUp2());
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside viewMessageDetails method of Message Inbox");
        }

    }

    public void setIsSearchTableVisible(boolean isSearchTableVisible) {
        this.isSearchTableVisible = isSearchTableVisible;
    }

    public boolean isIsSearchTableVisible() {
        return isSearchTableVisible;
    }

    public void messageTypeValueChangeEvent(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            isSearchTableVisible = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().searchTablePanel);
        }
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setComparator(Comparator<SelectItem> comparator) {
        this.comparator = comparator;
    }

    public Comparator<SelectItem> getComparator() {
        return comparator;
    }

    private void removeWhereClause() {
        ViewObject prtNotificationVO = ADFUtils.getViewObject("PrtNotificationVO1Iterator");


        if (cardGroupQuery.length() > 1 && cardGroupQuery != null && cardQuery.length() > 1 && cardQuery != null) {
            if (prtNotificationVO.getWhereClause() != null) {
                if (((accountQuery + "AND " + cardGroupQuery + "AND " + cardQuery).trim().equalsIgnoreCase(prtNotificationVO.getWhereClause().trim())) ||
                    ((accountQuery + " AND " + cardGroupQuery + "AND " + cardQuery).trim().equalsIgnoreCase(prtNotificationVO.getWhereClause().trim()))) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "inside  cardGroup with out purchase code where removal class");

                    if (mapAccountListValue != null) {
                        for (int i = 0; i < mapAccountListValue.size(); i++) {

                            prtNotificationVO.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL + i);
                        }
                    } else {
                        prtNotificationVO.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL);
                    }

                    if (mapCardGroupListValue != null) {
                        for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                            prtNotificationVO.removeNamedWhereClauseParam(Constants.CARDGROUPLITERAL + i);
                        }
                    } else {
                        prtNotificationVO.removeNamedWhereClauseParam(Constants.CARDGROUPLITERAL);
                    }
                    if (mapCardListValue != null) {
                        for (int i = 0; i < mapCardListValue.size(); i++) {
                            prtNotificationVO.removeNamedWhereClauseParam(Constants.CARDLITERAL + i);
                        }

                    } else {
                        prtNotificationVO.removeNamedWhereClauseParam(Constants.CARDLITERAL);
                    }

                    prtNotificationVO.setWhereClause("");
                    prtNotificationVO.executeQuery();
                }
            }
        }

    }


    private Long applyWhereClause(boolean isMessage, boolean onPageLoad, String selectedCategory, String messageValue, String newFromDate, String newToDate) {
        String accountTemp = "";
        String cgTemp = "";
        String cardTemp = "";

        if (onPageLoad) {
            accountTemp = populateStringValues(getAccountValue().toString());
            cgTemp = populateStringValues(getCardGroupValue().toString());
            cardTemp = populateStringValues(getCardValue().toString());
        } else {
            accountTemp = populateStringValues(getBindings().getAccount().getValue().toString());
            cgTemp = populateStringValues(getBindings().getCardGroup().getValue().toString());
            cardTemp = populateStringValues(getBindings().getCard().getValue().toString());
        }


        ViewObject prtNotificationVO = ADFUtils.getViewObject("PrtNotificationVO1Iterator");

        prtNotificationVO.setNamedWhereClauseParam("categoryValue", selectedCategory);
        prtNotificationVO.setNamedWhereClauseParam("showFlag", messageValue);
        prtNotificationVO.setNamedWhereClauseParam("fromDate", newFromDate);
        prtNotificationVO.setNamedWhereClauseParam("toDate", newToDate);
        prtNotificationVO.setNamedWhereClauseParam("countryCode", langSession);
        prtNotificationVO.setNamedWhereClauseParam("userId", userEmail);

        if (!isMessage) {
            
            accountQuery = "(";
            cardGroupQuery = "(";
            cardQuery = "(";
            if (accountValue.size() > Constants.ONEFIFTY) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
                mapAccountListValue = ValueListSplit.callValueList(accountValue.size(), accountValue);
                for (int i = 0; i < mapAccountListValue.size(); i++) {
                    String values = Constants.ACCOUNT_LITERAL + i;
                    accountQuery = accountQuery + "INSTR(:" + values + ",ACCOUNT_ID)<>0 OR ";
                }
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Account Query Values =" + accountQuery);
                accountQuery = accountQuery.substring(0, accountQuery.length() - Constants.THREE);
                accountQuery = accountQuery + " ACCOUNT_ID is null )";

            } else {
                mapAccountListValue = null;
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                accountQuery = "(INSTR(:account,ACCOUNT_ID)<>0 OR ACCOUNT_ID is null) ";
            }

            if (cardGroupValue.size() > Constants.ONEFIFTY) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values > 150 ");
                mapCardGroupListValue = ValueListSplit.callValueList(cardGroupValue.size(), cardGroupValue);
                for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                    String values = Constants.CARDGROUPLITERAL + i;
                    cardGroupQuery =
                            cardGroupQuery + "INSTR(:" + values + ",PARTNER||CARDGROUP_MAIN||CARDGROUP_SUB||CARDGROUP_SEQ)<>0 OR ";
                }
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "CARDGROUP Query Values =" + cardGroupQuery);
                cardGroupQuery = cardGroupQuery.substring(0, cardGroupQuery.length() - Constants.THREE);
                cardGroupQuery = cardGroupQuery + "PARTNER||CARDGROUP_MAIN||CARDGROUP_SUB||CARDGROUP_SEQ is null)";
                
            } else {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CARDGroup Values < 150 ");
                mapCardGroupListValue = null;
                cardGroupQuery =
                        "(INSTR(:cardGroup,PARTNER||CARDGROUP_MAIN||CARDGROUP_SUB||CARDGROUP_SEQ)<>0 OR PARTNER||CARDGROUP_MAIN||CARDGROUP_SUB||CARDGROUP_SEQ is null) ";
               

            }

            if (cardValue.size() > Constants.ONEFIFTY) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values > 150 ");
                mapCardListValue = ValueListSplit.callValueList(cardValue.size(), cardValue);
                for (int i = 0; i < mapCardListValue.size(); i++) {
                    String values = Constants.CARDLITERAL + i;
                    
                    cardQuery = cardQuery + "INSTR(:" + values + ", CARD_PK)<>0 OR ";
                }
                cardQuery = cardQuery.substring(0, cardQuery.length() - Constants.THREE);
                cardQuery = cardQuery + ") OR CARD_PK is null ";

                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "CARD Query Values =" + cardQuery);
                

            } else {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CARD Values < 150 ");
                mapCardListValue = null;
                cardQuery = "(INSTR(:card,CARD_PK)<>0 OR CARD_PK is null ) ";
                
            }

            prtNotificationVO.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + cardQuery);
            System.out.println("main query" + prtNotificationVO.getWhereClause());

            //account

            if (accountValue.size() > Constants.ONEFIFTY) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
                mapAccountListValue = ValueListSplit.callValueList(accountValue.size(), accountValue);
                for (int i = 0; i < mapAccountListValue.size(); i++) {

                    prtNotificationVO.defineNamedWhereClauseParam(Constants.ACCOUNT_LITERAL + i, mapAccountListValue.get(Constants.LISTNAME_LITERAL + i),
                                                                  null);
                }

            } else {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                prtNotificationVO.defineNamedWhereClauseParam(Constants.ACCOUNT_LITERAL, accountTemp, null);
            }

            // cardgroup
            if (cardGroupValue.size() > Constants.ONEFIFTY) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
                mapCardGroupListValue = ValueListSplit.callValueList(cardGroupValue.size(), cardGroupValue);
                for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                    prtNotificationVO.defineNamedWhereClauseParam(Constants.CARDGROUPLITERAL + i, mapCardGroupListValue.get(Constants.LISTNAME_LITERAL + i),
                                                                  null);
                }

            } else {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                prtNotificationVO.defineNamedWhereClauseParam(Constants.CARDGROUPLITERAL, cgTemp, null);
            }

            //card
            if (cardValue.size() > Constants.ONEFIFTY) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
                mapCardListValue = ValueListSplit.callValueList(cardValue.size(), cardValue);
                for (int i = 0; i < mapCardListValue.size(); i++) {
                    prtNotificationVO.defineNamedWhereClauseParam(Constants.CARDLITERAL + i, mapCardListValue.get(Constants.LISTNAME_LITERAL + i), null);
                }

            } else {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                prtNotificationVO.defineNamedWhereClauseParam(Constants.CARDLITERAL, cardTemp, null);
            }


        }

        System.out.println("query applied" + prtNotificationVO.getWhereClause().toString());

        prtNotificationVO.executeQuery();

        if (!isMessage) {
            session.setAttribute("account_Query_MessageInbox", accountQuery);
            session.setAttribute("map_Account_List_MessageInbox", mapAccountListValue);
            session.setAttribute("cardGroup_Query_MessageInbox", cardGroupQuery);
            session.setAttribute("map_CardGroup_List_MessageInbox", mapCardGroupListValue);
            session.setAttribute("card_Query_MessageInbox", cardQuery);
            session.setAttribute("map_Card_List_MessageInbox", mapCardListValue);
        }

        return prtNotificationVO.getEstimatedRowCount();
    }

   


    public class Bindings {

        private RichSelectManyChoice partnerNumber;
        private RichSelectManyChoice cardGroup;
        private RichSelectManyChoice account;
        private RichSelectManyChoice card;
        private RichInputDate fromDate;
        private RichInputDate toDate;
        private RichSelectManyChoice category;
        private RichSelectManyChoice messageType;
        private RichPanelGroupLayout searchTablePanel;
        private RichPopup messageInboxPopUp;
        private RichPopup messageInboxPopUp2;
        private RichTable messageTablePopup;
        
        public void setMessageTablePopup(RichTable messageTablePopup) {
            this.messageTablePopup = messageTablePopup;
        }

        public RichTable getMessageTablePopup() {
            return messageTablePopup;
        }
        
        public void setPartnerNumber(RichSelectManyChoice partnerNumber) {
            this.partnerNumber = partnerNumber;
        }

        public RichSelectManyChoice getPartnerNumber() {
            return partnerNumber;
        }

        public void setCardGroup(RichSelectManyChoice cardGroup) {
            this.cardGroup = cardGroup;
        }

        public RichSelectManyChoice getCardGroup() {
            return cardGroup;
        }

        public void setAccount(RichSelectManyChoice account) {
            this.account = account;
        }

        public RichSelectManyChoice getAccount() {
            return account;
        }

        public void setCard(RichSelectManyChoice card) {
            this.card = card;
        }

        public RichSelectManyChoice getCard() {
            return card;
        }

        public void setFromDate(RichInputDate fromDate) {

            if (resultFromTo) {

                Date dateNow = new java.util.Date();
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(dateNow);
                gc.add(GregorianCalendar.MONTH, minusThree);
                Date dateBefore = gc.getTime();
                SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
                String tmp = dateformat.format(dateBefore);
                fromDate.setValue(tmp);
                resultFromTo = false;
            }

            this.fromDate = fromDate;


        }

        public RichInputDate getFromDate() {
            return fromDate;
        }

        public void setToDate(RichInputDate toDate) {
            if (resultToFrom) {

                Date dateNow = new java.util.Date();
                SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
                String tmp = dateformat.format(dateNow);
                toDate.setValue(tmp);
                resultToFrom = false;
            }
            this.toDate = toDate;
        }

        public RichInputDate getToDate() {
            return toDate;
        }

        public void setCategory(RichSelectManyChoice category) {
            this.category = category;
        }

        public RichSelectManyChoice getCategory() {
            return category;
        }

        public void setMessageType(RichSelectManyChoice messageType) {
            this.messageType = messageType;
        }

        public RichSelectManyChoice getMessageType() {
            return messageType;
        }

        public void setSearchTablePanel(RichPanelGroupLayout searchTablePanel) {
            this.searchTablePanel = searchTablePanel;
        }

        public RichPanelGroupLayout getSearchTablePanel() {
            return searchTablePanel;
        }

        public void setMessageInboxPopUp(RichPopup messageInboxPopUp) {
            this.messageInboxPopUp = messageInboxPopUp;
        }

        public RichPopup getMessageInboxPopUp() {
            return messageInboxPopUp;
        }

        public void setMessageInboxPopUp2(RichPopup messageInboxPopUp2) {
            this.messageInboxPopUp2 = messageInboxPopUp2;
        }

        public RichPopup getMessageInboxPopUp2() {
            return messageInboxPopUp2;
        }
    }
}

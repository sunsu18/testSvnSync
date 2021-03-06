package com.sfr.engage.alertstaskflow;


import com.sfr.core.bean.User;
import com.sfr.engage.core.AlertsSubscribeCustomerType;
import com.sfr.engage.core.AlertsSubscribeFrequencyType;
import com.sfr.engage.core.AlertsSubscribeRequest;
import com.sfr.engage.core.AlertsSubscribeResponse;
import com.sfr.engage.core.AlertsUnsubscribeRequest;
import com.sfr.engage.core.AlertsUnsubscribeResponse;
import com.sfr.engage.core.FuelTimings;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.core.ValueListSplit;
import com.sfr.engage.model.queries.rvo.PrtCardFuelCapacityRVORowImpl;
import com.sfr.engage.model.queries.rvo.PrtCardRuleBusinessHoursRVORowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.ADFUtils;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;
import com.sfr.util.validations.Conversion;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanRadio;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;


public class Alerts {
    private List<FuelTimings> fueltimings = new ArrayList<FuelTimings>();
    private List<String> weekdays = new ArrayList<String>();
    private List<FuelTimings> configureFuelTimings = new ArrayList<FuelTimings>();
    private HttpSession session;
    private transient Bindings bindings;
    private ExternalContext ectx;
    private HttpServletRequest request;
    private List<PartnerInfo> partnerInfoList;
    private List<SelectItem> partnerAlert1List;
    private List<SelectItem> configurePartnerAlert1List;
    private List<String> partnerValue = null;
    private String searchString;
    private boolean selectionPanel = true;
    private boolean cardsSelectionPanel = false;
    private String configuredPartner;
    private String selectedPartner;
    private List<SelectItem> partnerIdList;
    private List<SelectItem> partnerIdList2;
    private List<SelectItem> configurePartnerIdList;
    private List<String> partnerIdValue;
    private List<String> partnerIdValue2;
    private String configurePartnerIdValue;
    private List<String> configurePartnerIdValue2;
    private List<SelectItem> accountIdList;
    private List<String> accountIdValue;
    private List<SelectItem> accountIdList2;
    private List<String> accountIdValue2;
    private List<SelectItem> configureAccountIdList;
    private List<String> configureAccountIdValue;
    private List<String> initailAccountIdVAlue;
    private List<String> initailAccountId2VAlue;
    private List<SelectItem> cardGroupList;
    private List<String> cardGroupValue;
    private List<SelectItem> cardGroupList2;
    private List<String> cardGroupValue2;
    private List<SelectItem> configureCardGroupList;
    private List<String> configureCardGroupValue;
    private List<String> initialCardGroupValue;
    private List<String> initialCardGroup2Value;
    private List<SelectItem> cardNumberList;
    private List<String> cardNumberValue;
    private List<SelectItem> cardNumberList2;
    private List<String> cardNumberValue2;
    private List<String> initialCardValue;
    private List<String> initialCard2Value;
    private List<SelectItem> configureCardNumberList;
    private List<String> configureCardNumberValue;
    private String langValue;
    private AlertsSubscribeRequest req = new AlertsSubscribeRequest();
    private AlertsSubscribeResponse response = new AlertsSubscribeResponse();
    private String userEmail;
    private String userFirstName;
    private String userMobileNo;
    private String userAssociationType;
    private String countryCode;
    private EngageResourceBundle resourceBundle;
    private List<String> suggestedCardNumberList;
    private String passingPartner = "";
    private String passingAccount = "";
    private String passingCardgrpMain = "";
    private String passingCardgrpSub = "";
    private String passingCardgrpSeq = "";
    private String passingCardKsId = "";
    public static final ADFLogger LOGGER = AccessDataControl.getSFRLogger();
    private AccessDataControl accessDC = new AccessDataControl();
    private String accountQuery = "(";
    private String cardGroupQuery = "(";
    private String cardQuery = "(";
    private Map<String, String> mapAccountListValue;
    private Map<String, String> mapCardGroupListValue;
    private Map<String, String> mapCardListValue;
    private Boolean isTableVisible;
    private Boolean okVisibleForQuality;
    private Boolean editVisibleForQuality;
    private Boolean okVisibleForBusinessHr;
    private Boolean editVisibleForBusinessHr;
    private static final String PRTCARDRULESUBSCRIPTIONVO1ITERATORLITRERAL = "PrtCardRuleSubscriptionVO1Iterator";
    private static final String PRTCARDFUELCAPACITYVO1ITERATORLITRERAL = "PrtCardFuelCapacityVO1Iterator";
    private static final String PRTCARDRULESUBSCRIPTIONRVO1ITERATORLITRERAL = "PrtCardRuleSubscriptionRVO1Iterator";
    private static final String LOCALNO = "9898989898";
    private static final String AFMANDATORYFIELDLITERAL = "af_mandatoryfield";
    private static final String AFNONMANDATORYFIELDLITERAL = "af_nonmandatoryfield";

    public Alerts() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside constructor of Alerts");

        weekdays.add("MONDAY");
        weekdays.add("TUESDAY");
        weekdays.add("WEDNESDAY");
        weekdays.add("THURSDAY");
        weekdays.add("FRIDAY");
        weekdays.add("SATURDAY");
        weekdays.add("SUNDAY");
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        partnerInfoList = new ArrayList<PartnerInfo>();
        partnerAlert1List = new ArrayList<SelectItem>();
        Conversion conv = new Conversion();
        
        okVisibleForQuality = false;
        okVisibleForBusinessHr = false;
        editVisibleForBusinessHr = true;
        editVisibleForQuality = true;

        if (session.getAttribute(Constants.DISPLAY_PORTAL_LANG) != null) {
            langValue = conv.getCustomerCountryCode((String)session.getAttribute(Constants.DISPLAY_PORTAL_LANG));
        }
        userEmail = "";
        userFirstName = "";
        userMobileNo = "";
        countryCode = "";
        if (session != null && session.getAttribute(Constants.SESSION_USER_INFO) != null) {
            User user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
            if (user.getEmailID() != null && user.getFirstName() != null) {
                userEmail = user.getEmailID();
                userFirstName = user.getFirstName();
            }
            if (user.getPhoneNumber() != null) {
                userMobileNo = user.getPhoneNumber();
            } else {
                userMobileNo = "1234567890";
            }
            if(user.getRoleList() != null && user.getRoleList().get(0) != null && user.getRoleList().get(0).getRoleName() != null){
                            if(user.getRoleList().get(0).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_ADMIN)){
                                userAssociationType = "PP";
                            }else if(user.getRoleList().get(0).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_EMP)){
                                userAssociationType = "CC";
                            }else if(user.getRoleList().get(0).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR)){
                                if(user.getRoleList().get(0).getIdString() != null && user.getRoleList().get(0).getIdString().get(0) != null){
                                    if(user.getRoleList().get(0).getIdString().get(0).contains("AC")){
                                        userAssociationType = "AC";
                                    }else{
                                        userAssociationType = "CG";
                                    }
                                }
                            }
                        }
        }

        if (session.getAttribute(Constants.PARTNER_LANG_LITERAL) != null) {
            countryCode = (String)session.getAttribute(Constants.PARTNER_LANG_LITERAL);
        }

        if (session.getAttribute("Partner_Object_List") != null) {
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "partnerlist not null");
            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
        }

        populateDefaultDropdown(true);

        if (session != null) {
            if (session.getAttribute(Constants.ALERTS_ACCOUNT_QUERY_LITERAL) != null) {
                accountQuery = session.getAttribute(Constants.ALERTS_ACCOUNT_QUERY_LITERAL).toString().trim();
                mapAccountListValue = (Map<String, String>)session.getAttribute("map_Account_List");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account Query & mapAccountList is found");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account " + accountQuery);
            }
            if (session.getAttribute(Constants.ALERTS_CARDGROUP_QUERY_LITERAL) != null) {
                cardGroupQuery = session.getAttribute(Constants.ALERTS_CARDGROUP_QUERY_LITERAL).toString().trim();
                mapCardGroupListValue = (Map<String, String>)session.getAttribute("map_CardGroup_List");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Query & mapCardGroupList is found");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup " + cardGroupQuery);
            }
            if (session.getAttribute(Constants.ALERTS_CARD_QUERY_LITERAL) != null) {
                cardQuery = session.getAttribute(Constants.ALERTS_CARD_QUERY_LITERAL).toString().trim();
                mapCardListValue = (Map<String, String>)session.getAttribute("map_Card_List");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Query & mapCardList is found");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card " + accountQuery);
            }
        }
        viewSubscribedAlertsUI(partnerIdValue2.toString(), accountIdValue2.toString(), cardGroupValue2.toString(), cardNumberValue2.toString());
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside constructor of Alerts");
    }

    /**
     * @return bindings Object
     */
    public Bindings getBindings() {
        if (bindings == null) {
            bindings = new Bindings();
        }
        return bindings;
    }

    public String getPartnerName(String partnerId) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside getPartnerName method of Alerts");
        if (partnerInfoList != null && partnerInfoList.size() > 0) {
            for (int i = 0; i < partnerInfoList.size(); i++) {
                if (partnerInfoList.get(i).getPartnerName() != null && partnerInfoList.get(i).getPartnerValue() != null &&
                    partnerInfoList.get(i).getPartnerValue().equals(partnerId.trim())) {
                    return partnerInfoList.get(i).getPartnerName();
                }
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside getPartnerName method of Alerts");
        return null;
    }

    public void populateDefaultDropdown(boolean changeViewAlertsDropdown) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside populateDefaultDropdown method of Alerts");
        partnerValue = new ArrayList<String>();
        partnerAlert1List = new ArrayList<SelectItem>();
        partnerIdList = new ArrayList<SelectItem>();
        partnerIdValue = new ArrayList<String>();
        accountIdList = new ArrayList<SelectItem>();
        accountIdValue = new ArrayList<String>();
        initailAccountIdVAlue = new ArrayList<String>();
        initialCardGroupValue = new ArrayList<String>();
        initialCardValue = new ArrayList<String>();
        cardGroupList = new ArrayList<SelectItem>();
        cardGroupValue = new ArrayList<String>();
        cardNumberList = new ArrayList<SelectItem>();
        cardNumberValue = new ArrayList<String>();
        if(changeViewAlertsDropdown){
            partnerIdList2 = new ArrayList<SelectItem>();
            partnerIdValue2 = new ArrayList<String>();
            accountIdList2 = new ArrayList<SelectItem>();
            accountIdValue2 = new ArrayList<String>();
            initailAccountId2VAlue = new ArrayList<String>();
            initialCardGroup2Value = new ArrayList<String>();
            initialCard2Value = new ArrayList<String>();
            cardGroupList2 = new ArrayList<SelectItem>();
            cardGroupValue2 = new ArrayList<String>();
            cardNumberList2 = new ArrayList<SelectItem>();
            cardNumberValue2 = new ArrayList<String>();
            suggestedCardNumberList = new ArrayList<String>();
        }
        if (partnerInfoList != null && partnerInfoList.size() > 0) {
            for (int i = 0; i < partnerInfoList.size(); i++) {
                if (partnerInfoList.get(i).getPartnerName() != null && partnerInfoList.get(i).getPartnerValue() != null) {
                    SelectItem selectItem = new SelectItem();
                    selectItem.setLabel(partnerInfoList.get(i).getPartnerName().toString());
                    selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString());
                    partnerIdList.add(selectItem);
                    if (changeViewAlertsDropdown) {
                        partnerIdList2.add(selectItem);
                        partnerIdValue2.add(partnerInfoList.get(i).getPartnerValue().toString());
                    }
                    partnerIdValue.add(partnerInfoList.get(i).getPartnerValue().toString());
                    partnerAlert1List.add(selectItem);
                }

                if (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {
                    for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
                        if (partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() != null) {
                            SelectItem selectItem = new SelectItem();
                            selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString() +
                                                partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            accountIdList.add(selectItem);
                            if (changeViewAlertsDropdown) {
                                accountIdList2.add(selectItem);
                                accountIdValue2.add(partnerInfoList.get(i).getPartnerValue().toString() +
                                                    partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            }
                            accountIdValue.add(partnerInfoList.get(i).getPartnerValue().toString() +
                                               partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());

                        }
                        for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
                            if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() != null) {
                                SelectItem selectItem = new SelectItem();
                                selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                    partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                    partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                cardGroupList.add(selectItem);
                                if (changeViewAlertsDropdown) {
                                    cardGroupList2.add(selectItem);
                                    cardGroupValue2.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                        partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                        partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                }
                                cardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                   partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                   partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());

                            }

                            for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++) {
                                if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                    partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() != null) {
                                    SelectItem selectItem = new SelectItem();
                                    selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                    selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                        partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                        partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString() +
                                                        partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                    cardNumberList.add(selectItem);
                                    if (changeViewAlertsDropdown) {
                                        cardNumberList2.add(selectItem);
                                        cardNumberValue2.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                             partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                             partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString() +
                                                             partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                        suggestedCardNumberList.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                    }
                                    cardNumberValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                        partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                        partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString() +
                                                        partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                }
                            }
                        }
                    }
                }
            }
            initailAccountIdVAlue = accountIdValue;
            initialCardGroupValue = cardGroupValue;
            initialCardValue = cardNumberValue;
            if (changeViewAlertsDropdown) {
                initailAccountId2VAlue = accountIdValue2;
                initialCardGroup2Value = cardGroupValue2;
                initialCard2Value = cardNumberValue2;
            }
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Account id value length cons" + initailAccountIdVAlue.size());
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Cardgroup id value length cons" + initialCardGroupValue.size());
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Card id value length cons" + initialCardValue.size());
            Collections.sort(accountIdList, comparator);
            Collections.sort(cardGroupList, comparator);
            Collections.sort(cardNumberList, comparator);
            if (changeViewAlertsDropdown) {
                Collections.sort(accountIdList2, comparator);
                Collections.sort(cardGroupList2, comparator);
                Collections.sort(cardNumberList2, comparator);
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside populateDefaultDropdown method of Alerts");
    }

    public void defaultTimings() {

        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside defaultTimings method of Alerts");

        resourceBundle = new EngageResourceBundle();

        fueltimings = new ArrayList<FuelTimings>();
        fueltimings.clear();
        FuelTimings f = new FuelTimings();
        if (resourceBundle.containsKey("MONDAY")) {
            f.setWeekday((String)resourceBundle.getObject("MONDAY"));
        }
        f.setFromHh("07");
        f.setFromMm("00");
        f.setToHh("17");
        f.setToMm("00");
        fueltimings.add(f);

        f = new FuelTimings();
        if (resourceBundle.containsKey("TUESDAY")) {
            f.setWeekday((String)resourceBundle.getObject("TUESDAY"));
        }
        f.setFromHh("07");
        f.setFromMm("00");
        f.setToHh("17");
        f.setToMm("00");
        fueltimings.add(f);

        f = new FuelTimings();
        if (resourceBundle.containsKey("WEDNESDAY")) {
            f.setWeekday((String)resourceBundle.getObject("WEDNESDAY"));
        }
        f.setFromHh("07");
        f.setFromMm("00");
        f.setToHh("17");
        f.setToMm("00");
        fueltimings.add(f);

        f = new FuelTimings();
        if (resourceBundle.containsKey("THURSDAY")) {
            f.setWeekday((String)resourceBundle.getObject("THURSDAY"));
        }
        f.setFromHh("07");
        f.setFromMm("00");
        f.setToHh("17");
        f.setToMm("00");
        fueltimings.add(f);

        f = new FuelTimings();
        if (resourceBundle.containsKey("FRIDAY")) {
            f.setWeekday((String)resourceBundle.getObject("FRIDAY"));
        }
        f.setFromHh("07");
        f.setFromMm("00");
        f.setToHh("17");
        f.setToMm("00");
        fueltimings.add(f);

        f = new FuelTimings();
        if (resourceBundle.containsKey("SATURDAY")) {
            f.setWeekday((String)resourceBundle.getObject("SATURDAY"));
        }
        f.setFromHh("07");
        f.setFromMm("00");
        f.setToHh("17");
        f.setToMm("00");
        fueltimings.add(f);

        f = new FuelTimings();
        if (resourceBundle.containsKey("SUNDAY")) {
            f.setWeekday((String)resourceBundle.getObject("SUNDAY"));
        }
        f.setFromHh("07");
        f.setFromMm("00");
        f.setToHh("17");
        f.setToMm("00");
        fueltimings.add(f);
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside defaultTimings method of Alerts");
    }

    public void configureDefaultTimings(String subscriptionId, String country) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside configureDefaultTimings method of Alerts");
        resourceBundle = new EngageResourceBundle();

        configureFuelTimings = new ArrayList<FuelTimings>();
        configureFuelTimings.clear();
        DCBindingContainer localBinding;
        localBinding = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter1;
        if (localBinding != null && localBinding.findIteratorBinding("PrtCardRuleBusinessHoursRVO1Iterator") != null) {
            iter1 = localBinding.findIteratorBinding("PrtCardRuleBusinessHoursRVO1Iterator");
            ViewObject businessHoursVO = iter1.getViewObject();
            businessHoursVO.setNamedWhereClauseParam("SubID", subscriptionId);
            businessHoursVO.setNamedWhereClauseParam(Constants.COUNTRYCODE_LITERAL, country);
            businessHoursVO.executeQuery();

            FuelTimings f;
            if (businessHoursVO.getEstimatedRowCount() != 0) {
                while (businessHoursVO.hasNext()) {
                    f = new FuelTimings();
                    PrtCardRuleBusinessHoursRVORowImpl currRow = (PrtCardRuleBusinessHoursRVORowImpl)businessHoursVO.next();
                    if (currRow != null) {
                        if (currRow.getDay() != null && currRow.getBusiStartFrom() != null && currRow.getBusiStartTo() != null) {
                            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "currRow.getDay() " + currRow.getDay());
                            if (resourceBundle.containsKey(currRow.getDay().toString().toUpperCase())) {
                                LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "resourceBundle contains currRow.getDay()");
                                f.setWeekday((String)resourceBundle.getObject(currRow.getDay().toString().toUpperCase()));
                                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "f.setWeekday " + f.getWeekday());
                            }
                            f.setFromHh(currRow.getBusiStartFrom().toString().substring(0, currRow.getBusiStartFrom().toString().indexOf(":")));
                            f.setFromMm(currRow.getBusiStartFrom().toString().substring(currRow.getBusiStartFrom().toString().indexOf(":") + 1));
                            f.setToHh(currRow.getBusiStartTo().toString().substring(0, currRow.getBusiStartTo().toString().indexOf(":")));
                            f.setToMm(currRow.getBusiStartTo().toString().substring(currRow.getBusiStartTo().toString().indexOf(":") + 1));
                        }
                        configureFuelTimings.add(f);
                    }
                }
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside configureDefaultTimings method of Alerts");
    }


    public void configureBusinessHoursAlert(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside configureBusinessHoursAlert method of Alerts");
        isTableVisible = false;
        getBindings().getAlertSuccessProperty().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlertSuccessProperty());
        getBindings().getAlert1ValidData().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert1ValidData());
        displayErrorComponent(getBindings().getAlert1PartnerValues(), false);
        getBindings().getMandatoryMsg1().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg1());
        defaultTimings();
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "fueltimings size " + fueltimings.size());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert1Table());
        getBindings().getAlert1ValidData().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert1ValidData());
        getBindings().getAlertSuccessProperty().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlertSuccessProperty());
        getBindings().getAlert1PartnerValues().resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert1PartnerValues());
        getBindings().getFromTimingsHh().setReadOnly(true);
        getBindings().getFromTimingsMm().setReadOnly(true);
        getBindings().getToTimingsHh().setReadOnly(true);
        getBindings().getToTimingsMm().setReadOnly(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFromTimingsHh());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFromTimingsMm());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getToTimingsHh());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getToTimingsMm());
        getBindings().getCloseButtonAlert1().setRendered(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCloseButtonAlert1());
        getBindings().getOkButtonAlert1().setVisible(true);
        getBindings().getCancelButtonAlert1().setVisible(true);
        getBindings().getEditButtonAlert1().setVisible(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getOkButtonAlert1());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCancelButtonAlert1());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditButtonAlert1());
        getBindings().getAlert1PartnerValues().setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert1PartnerValues());
        RichPopup.PopupHints ps = new RichPopup.PopupHints();
        getBindings().getAlert1Popup().show(ps);
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside configureBusinessHoursAlert method of Alerts");
    }

    public void setFueltimings(List<FuelTimings> fueltimings) {
        this.fueltimings = fueltimings;
    }

    public List<FuelTimings> getFueltimings() {
        return fueltimings;
    }

    public void claoseAlert1Popup(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside claoseAlert1Popup method of Alerts");
        getBindings().getAlert1Popup().hide();
        getBindings().getAlert2Popup().hide();
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside claoseAlert1Popup method of Alerts");
    }

    public void editAlert1Timings(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside editAlert1Timings method of Alerts");
        getBindings().getFromTimingsHh().setReadOnly(false);
        getBindings().getFromTimingsMm().setReadOnly(false);
        getBindings().getToTimingsHh().setReadOnly(false);
        getBindings().getToTimingsMm().setReadOnly(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFromTimingsHh());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFromTimingsMm());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getToTimingsHh());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getToTimingsMm());
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside editAlert1Timings method of Alerts");
    }

    public void editAlert2Timings(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside editAlert2Timings method of Alerts");
        okVisibleForQuality = true;
        editVisibleForQuality = false;
        getBindings().getSuccessAlert3().setRendered(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert3());
        getBindings().getConfigureFuelCapacityAlert2().setDisabled(false);
        getBindings().getConfigureLtrPerDayRadio().setDisabled(false);
        getBindings().getConfigureLtrPerWeekRadio().setDisabled(false);
        getBindings().getConfigureLtrPerMonthRadio().setDisabled(false);
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside editAlert2Timings method of Alerts");
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setEctx(ExternalContext ectx) {
        this.ectx = ectx;
    }

    public ExternalContext getEctx() {
        return ectx;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setPartnerInfoList(List<PartnerInfo> partnerInfoList) {
        this.partnerInfoList = partnerInfoList;
    }

    public List<PartnerInfo> getPartnerInfoList() {
        return partnerInfoList;
    }

    public void setPartnerValue(List<String> partnerValue) {
        this.partnerValue = partnerValue;
    }

    public List<String> getPartnerValue() {
        return partnerValue;
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

    public void configureFuelCapacityAlert(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside configureFuelCapacityAlert method of Alerts");
        isTableVisible = false;
        getBindings().getMandatoryMsg().setRendered(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg());
        getBindings().getAlert2ValidData().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert2ValidData());
        getBindings().getMandatoryMsg().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg());
        displayErrorComponent(getBindings().getPartnerDropdownAlert2(), false);
        displayErrorComponent(getBindings().getAccountDropdwonAlert2(), false);
        displayErrorComponent(getBindings().getCardGroupDowndownAlert2(), false);
        displayErrorComponent(getBindings().getCardDropdownAlert2(), false);
        displayErrorComponent(getBindings().getFuelCapacityAlert2(), false);
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "populating default dropdown");

        populateDefaultDropdown(false);
        RichPopup.PopupHints ps = new RichPopup.PopupHints();
        getBindings().getAlert2Popup().show(ps);
        getBindings().getSuccessAlert2().setRendered(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert2());
        getBindings().getFuelCapacityAlert2().setDisabled(false);
        getBindings().getFuelCapacityAlert2().resetValue();
        getBindings().getOkButtonAlert2().setRendered(true);
        getBindings().getCancelButtonAlert2().setRendered(true);
        getBindings().getLtrPerMonthRadio().setDisabled(false);
        getBindings().getLtrPerMonthRadio().resetValue();
        getBindings().getLtrPerWeekRadio().setDisabled(false);
        getBindings().getLtrPerWeekRadio().resetValue();
        getBindings().getLtrPerDayRadio().setDisabled(false);
        getBindings().getLtrPerDayRadio().resetValue();
        getBindings().getLtrPerDayRadio().setSelected(true);
        getBindings().getCardDropdownAlert2().setDisabled(false);
        getBindings().getCardDropdownAlert2().resetValue();
        getBindings().getCardGroupDowndownAlert2().setDisabled(false);
        getBindings().getCardGroupDowndownAlert2().resetValue();
        getBindings().getAccountDropdwonAlert2().setDisabled(false);
        getBindings().getAccountDropdwonAlert2().resetValue();
        getBindings().getPartnerDropdownAlert2().setDisabled(false);
        getBindings().getPartnerDropdownAlert2().resetValue();
        getBindings().getCloseButtonAlert2().setRendered(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFuelCapacityAlert2());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getOkButtonAlert2());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCancelButtonAlert2());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLtrPerMonthRadio());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLtrPerWeekRadio());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLtrPerDayRadio());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardDropdownAlert2());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupDowndownAlert2());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccountDropdwonAlert2());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartnerDropdownAlert2());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCloseButtonAlert2());
        getBindings().getSuccessAlert2().setRendered(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert2());
        getBindings().getAlert2ValidData().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert2ValidData());
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside configureFuelCapacityAlert method of Alerts");
    }

    public void setLangValue(String langValue) {
        this.langValue = langValue;
    }

    public String getLangValue() {
        return langValue;
    }

    public void setBusinessHoursAlert(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside setBusinessHoursAlert method of Alerts");
        BindingContainer localBinding = BindingContext.getCurrent().getCurrentBindingsEntry();
        if (validateinput(true)) {
            if (localBinding != null) {
                OperationBinding operationBinding = localBinding.getOperationBinding(Constants.SUBSCRIBE_ALERTS_LITERAL);
                if (operationBinding != null) {
                    BigInteger bigint = BigInteger.ONE;
                    req.setRuleID(bigint);
                    AlertsSubscribeCustomerType customerobj = new AlertsSubscribeCustomerType();
                    customerobj.setEmailID(userEmail);
                    customerobj.setCustomerID(userEmail);
                    customerobj.setFIrstName(userFirstName);
                    BigInteger bigint2 = new BigInteger(userMobileNo);
                    customerobj.setMobileNumber(bigint2);
                    req.setCustomer(customerobj);
                    AlertsSubscribeFrequencyType freq = new AlertsSubscribeFrequencyType();
                    freq.setScheduleFrequency(Constants.DAILY_LITERAL);
                    req.setSubscribeFrequency(freq);
                    req.setNotificationChannel(Constants.EMAIL_LITERAL);
                    req.setNotificationFormat(Constants.EXCEL_LITERAL);
                    operationBinding.getParamsMap().put(Constants.SUBSCRIBE_REQUEST_LITERAL, req);
                    response = (AlertsSubscribeResponse)operationBinding.execute();
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "response " + response);
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "response = " + response.getSubscriptionID());
                    if (response.getSubscriptionID() != null) {
                        DCBindingContainer bindings2 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                        DCIteratorBinding cardRuleSubscriptionIter = bindings2.findIteratorBinding(PRTCARDRULESUBSCRIPTIONVO1ITERATORLITRERAL);
                        DCIteratorBinding cardRuleBusinessHoursIter = bindings2.findIteratorBinding("PrtCardRuleBusinessHoursVO1Iterator");
                        if (cardRuleSubscriptionIter != null && cardRuleBusinessHoursIter != null) {
                            ViewObject cardRuleSubscription = cardRuleSubscriptionIter.getViewObject();
                            ViewObject cardRuleBusinessHours = cardRuleBusinessHoursIter.getViewObject();
                            Row cardRuleSubscriptionRow = cardRuleSubscription.createRow();
                            if (session.getAttribute(Constants.PARTNER_LANG_LITERAL) != null) {
                                cardRuleSubscriptionRow.setAttribute("CountryCode", session.getAttribute(Constants.PARTNER_LANG_LITERAL));
                            }
                            cardRuleSubscriptionRow.setAttribute(Constants.USER_ID_LITERAL, userEmail);
                            cardRuleSubscriptionRow.setAttribute(Constants.SUBSCR_ID_LITERAL, response.getSubscriptionID().toString().trim());
                            cardRuleSubscriptionRow.setAttribute(Constants.RULE_ID_LITERAL, "1");
                            cardRuleSubscriptionRow.setAttribute(Constants.SUBSCRSTATUS_LITERAL, Constants.ACTIVE_LITERAL);
                            cardRuleSubscriptionRow.setAttribute(Constants.PARTNER_ID_LITERAL, getBindings().getAlert1PartnerValues().getValue().toString());
                            cardRuleSubscriptionRow.setAttribute(Constants.ACCOUNTIDLITERAL, Constants.ALL_LITERAL);
                            cardRuleSubscriptionRow.setAttribute(Constants.CARDGROUP_MAIN_LITERAL, Constants.ALL_LITERAL);
                            cardRuleSubscriptionRow.setAttribute(Constants.CARDGROUP_SUB_LITERAL, Constants.ALL_LITERAL);
                            cardRuleSubscriptionRow.setAttribute(Constants.CARDGROUP_SEQ_LITERAL, Constants.ALL_LITERAL);
                            cardRuleSubscriptionRow.setAttribute(Constants.MODIFIEDBYLITERAL, userEmail);
                            cardRuleSubscriptionRow.setAttribute(Constants.CADRDKSID_LITERAL, Constants.ALL_LITERAL);
                            cardRuleSubscriptionRow.setAttribute(Constants.ASSOCIATIONTYPELITERAL, userAssociationType);
                            cardRuleSubscription.insertRow(cardRuleSubscriptionRow);
                            RichTable rt = getBindings().getAlert1Table();
                            Object o;
                            FuelTimings checkFuelTimings;
                            for (int i = 0; i < Constants.SEVEN; i++) {
                                o = rt.getRowData(i);
                                checkFuelTimings = (FuelTimings)o;
                                Row cardRuleBusinessHoursRow = cardRuleBusinessHours.createRow();
                                if (session.getAttribute(Constants.PARTNER_LANG_LITERAL) != null) {
                                    cardRuleBusinessHoursRow.setAttribute("CountryCode", session.getAttribute(Constants.PARTNER_LANG_LITERAL));
                                }
                                cardRuleBusinessHoursRow.setAttribute(Constants.SUBSCR_ID_LITERAL, response.getSubscriptionID().toString().trim());
                                cardRuleBusinessHoursRow.setAttribute(Constants.RULE_ID_LITERAL, "1");

                                cardRuleBusinessHoursRow.setAttribute("Day", weekdays.get(i));
                                cardRuleBusinessHoursRow.setAttribute("BusiStartFrom",
                                                                      checkFuelTimings.getFromHh().toString().trim() + ":" + checkFuelTimings.getFromMm().toString().trim());
                                cardRuleBusinessHoursRow.setAttribute("BusiStartTo",
                                                                      checkFuelTimings.getToHh().toString().trim() + ":" + checkFuelTimings.getToMm().toString().trim());
                                cardRuleBusinessHoursRow.setAttribute(Constants.MODIFIEDBYLITERAL, userEmail);
                                cardRuleBusinessHours.insertRow(cardRuleBusinessHoursRow);
                            }
                            operationBinding = localBinding.getOperationBinding(Constants.COMMIT_LITERAL);
                            operationBinding.execute();
                        }

                        getBindings().getAlertSuccessProperty().setVisible(true);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlertSuccessProperty());
                        getBindings().getAlert1ValidData().setVisible(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert1ValidData());
                        getBindings().getCloseButtonAlert1().setRendered(true);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCloseButtonAlert1());
                        getBindings().getOkButtonAlert1().setVisible(false);
                        getBindings().getCancelButtonAlert1().setVisible(false);
                        getBindings().getEditButtonAlert1().setVisible(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getOkButtonAlert1());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCancelButtonAlert1());
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditButtonAlert1());
                        getBindings().getAlert1PartnerValues().setDisabled(true);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert1PartnerValues());
                        getBindings().getFromTimingsHh().setReadOnly(true);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFromTimingsHh());
                        getBindings().getFromTimingsMm().setReadOnly(true);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFromTimingsMm());
                        getBindings().getToTimingsHh().setReadOnly(true);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getToTimingsHh());
                        getBindings().getToTimingsMm().setReadOnly(true);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getToTimingsMm());
                    }
                }
            }

        } else {
            getBindings().getAlertSuccessProperty().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlertSuccessProperty());
            getBindings().getAlert1ValidData().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert1ValidData());
            getBindings().getMandatoryMsg1().setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg1());
                
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside setBusinessHoursAlert method of Alerts");
    }

    public String showErrorMessage(String errorVar) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside showErrorMessage method of Alerts");
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "throwing error message");
        resourceBundle = new EngageResourceBundle();
        if (errorVar != null && resourceBundle.containsKey(errorVar)) {

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject(errorVar), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;

        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside showErrorMessage method of Alerts");
        return null;
    }

    public boolean validateinput(boolean flag1) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside validateinput method of Alerts");
        boolean validinput = true;
        boolean flag2 = false;
        RichTable rt = null;
        if (flag1) {

            if (getBindings().getAlert1PartnerValues().getValue() != null && getBindings().getAlert1PartnerValues().getValue().toString() != null) {
                displayErrorComponent(getBindings().getAlert1PartnerValues(), false);
                flag2 = true;
                rt = getBindings().getAlert1Table();
            } else {
                validinput = false;
                displayErrorComponent(getBindings().getAlert1PartnerValues(), true);
            }
        } else {
            flag2 = true;
            rt = getBindings().getConfigureAlert1Table();
        }

        if (flag2) {
            Object o;
            FuelTimings checkFuelTimings;
            int fromHh;
            int fromMm;
            int toHh;
            int toMm;
            for (int i = 0; i < Constants.SEVEN; i++) {
                o = rt.getRowData(i);
                checkFuelTimings = (FuelTimings)o;
                String regex = "\\d+";
                if (checkFuelTimings != null && checkFuelTimings.getFromHh() != null && !checkFuelTimings.getFromHh().equals("") &&
                    checkFuelTimings.getFromHh().toString().trim() != null && checkFuelTimings.getFromMm() != null &&
                    checkFuelTimings.getFromMm().toString().trim() != null && !checkFuelTimings.getFromMm().equals("") && checkFuelTimings.getToHh() != null &&
                    checkFuelTimings.getToHh().toString().trim() != null && !checkFuelTimings.getToHh().equals("") && checkFuelTimings.getToMm() != null &&
                    checkFuelTimings.getToMm().toString().trim() != null && !checkFuelTimings.getToMm().equals("")) {
                    if (checkFuelTimings.getFromHh().toString().trim().matches(regex) && checkFuelTimings.getFromMm().toString().trim().matches(regex) &&
                        checkFuelTimings.getToHh().toString().trim().matches(regex) && checkFuelTimings.getToMm().toString().trim().matches(regex)) {
                        fromHh = Integer.parseInt(checkFuelTimings.getFromHh().toString().trim());
                        fromMm = Integer.parseInt(checkFuelTimings.getFromMm().toString().trim());
                        toHh = Integer.parseInt(checkFuelTimings.getToHh().toString().trim());
                        toMm = Integer.parseInt(checkFuelTimings.getToMm().toString().trim());
                        if (!(fromHh < Constants.TWENTYFOUR && toHh < Constants.TWENTYFOUR && fromMm < Constants.SIXTY && toMm < Constants.SIXTY)) {
                            validinput = false;
                            if (flag1) {
                                getBindings().getAlert1ValidData().setVisible(true);
                                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert1ValidData());
                                getBindings().getAlertSuccessProperty().setVisible(false);
                                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlertSuccessProperty());
                                getBindings().getMandatoryMsg1().setVisible(false);
                                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg1());
                            } else {
                                getBindings().getInvalidInputMsg().setVisible(true);
                                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvalidInputMsg());
                                getBindings().getMandatoryMsg4().setVisible(false);
                                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg4());
                                getBindings().getSuccessAlert4().setVisible(false);
                                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert4());
                            }
                            break;
                        }
                    } else {
                        validinput = false;
                        if (flag1) {
                            getBindings().getAlert1ValidData().setVisible(true);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert1ValidData());
                            getBindings().getAlertSuccessProperty().setVisible(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlertSuccessProperty());
                            getBindings().getMandatoryMsg1().setVisible(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg1());
                        } else {
                            getBindings().getInvalidInputMsg().setVisible(true);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvalidInputMsg());
                            getBindings().getMandatoryMsg4().setVisible(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg4());
                            getBindings().getSuccessAlert4().setVisible(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert4());
                        }
                        break;
                    }
                } else {
                    validinput = false;
                    if (flag1) {
                        if (checkFuelTimings != null && checkFuelTimings.getFromHh() == null && checkFuelTimings.getFromHh().toString().trim() == null) {
                            displayErrorComponent(getBindings().getFromTimingsHh(), true);
                        }
                        if (checkFuelTimings != null && checkFuelTimings.getFromMm() == null && checkFuelTimings.getFromMm().toString().trim() == null) {
                            displayErrorComponent(getBindings().getFromTimingsMm(), true);
                        }
                        if (checkFuelTimings != null &&  checkFuelTimings.getToHh() == null && checkFuelTimings.getToHh().toString().trim() == null) {
                            displayErrorComponent(getBindings().getToTimingsHh(), true);
                        }
                        if (checkFuelTimings != null &&  checkFuelTimings.getToMm() == null && checkFuelTimings.getToMm().toString().trim() == null) {
                            displayErrorComponent(getBindings().getToTimingsMm(), true);
                        }
                        getBindings().getMandatoryMsg1().setVisible(true);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg1());
                        getBindings().getAlert1ValidData().setVisible(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert1ValidData());
                        getBindings().getAlertSuccessProperty().setVisible(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlertSuccessProperty());
                    } else {
                        if (checkFuelTimings != null && checkFuelTimings.getFromHh().toString().trim().equals("")) {
                            displayErrorComponent(getBindings().getConfigureFromTimingsHh(), true);
                        }
                        if (checkFuelTimings != null && checkFuelTimings.getFromMm().toString().trim().equals("")) {
                            displayErrorComponent(getBindings().getConfigureFromTimingsMm(), true);
                        }
                        if (checkFuelTimings != null && checkFuelTimings.getToHh().toString().trim().equals("")) {
                            displayErrorComponent(getBindings().getConfigureToTimingsHh(), true);
                        }
                        if (checkFuelTimings != null && checkFuelTimings.getToMm().toString().trim().equals("")) {
                            displayErrorComponent(getBindings().getConfigureToTimingsMm(), true);
                        }
                        getBindings().getMandatoryMsg4().setVisible(true);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg4());
                        getBindings().getInvalidInputMsg().setVisible(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvalidInputMsg());
                        getBindings().getSuccessAlert4().setVisible(false);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert4());
                    }
                    break;
                }
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside validateinput method of Alerts");
        return validinput;
    }

    public String populateStringValues(String var) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside populateStringValues method of Alerts");
        String passingValues = null;
        if (var != null) {
            String lovValues = var.trim();
            String selectedValues = lovValues.substring(1, lovValues.length() - 1);
            passingValues = selectedValues.trim();
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "passingValues --> " + passingValues);
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside populateStringValues method of Alerts");
        return passingValues;
    }

    public void setPartnerAlert1List(List<SelectItem> partnerAlert1List) {
        this.partnerAlert1List = partnerAlert1List;
    }

    public List<SelectItem> getPartnerAlert1List() {
        return partnerAlert1List;
    }

    private Comparator<SelectItem> comparator = new Comparator<SelectItem>() {
        @Override
        public int compare(SelectItem s1, SelectItem s2) {
            return s1.getLabel().compareTo(s2.getLabel());
        }
    };

    public String[] stringSplitter(String passedVal) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside StringConversion method of Alerts");

        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting StringConversion method of Alerts");
        return passedVal.split(",");

    }

    public void partnerValueChangeListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside partnerValueChangeListener method of Alerts");
        if (valueChangeEvent.getNewValue() != null) {
            accountIdList = new ArrayList<SelectItem>();
            accountIdValue = new ArrayList<String>();
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();
            cardNumberList = new ArrayList<SelectItem>();
            cardNumberValue = new ArrayList<String>();
            String[] partnerString = stringSplitter(populateStringValues(valueChangeEvent.getNewValue().toString()));
            if (partnerString.length > 0) {
                for (int i = 0; i < partnerInfoList.size(); i++) {
                    for (int p = 0; p < partnerString.length; p++) {
                        if (partnerInfoList.get(i).getPartnerValue().toString() != null &&
                            partnerInfoList.get(i).getPartnerValue().toString().equals(partnerString[p].trim()) &&
                            (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0)) {

                            for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
                                if (partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() != null) {
                                    SelectItem selectItem = new SelectItem();
                                    selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                    selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString() +
                                                        partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                    accountIdList.add(selectItem);
                                    accountIdValue.add(partnerInfoList.get(i).getPartnerValue().toString() +
                                                       partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                }

                                for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
                                    if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() != null) {
                                        SelectItem selectItem = new SelectItem();
                                        selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                        selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                            partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                            partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                        cardGroupList.add(selectItem);
                                        cardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                           partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                           partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                    }

                                    for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++) {
                                        if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                            partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() !=
                                            null) {
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                            selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                                partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString() +
                                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                            cardNumberList.add(selectItem);
                                            cardNumberValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                                partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString() +
                                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
                initailAccountIdVAlue = accountIdValue;
                initialCardGroupValue = cardGroupValue;
                initialCardValue = cardNumberValue;
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Account id value length partnerValueChangeListener" +
                            initailAccountIdVAlue.size());
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Cardgroup id value length partnerValueChangeListener" +
                            initialCardGroupValue.size());
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Card id value length partnerValueChangeListener" + initialCardValue.size());
                Collections.sort(accountIdList, comparator);
                Collections.sort(cardGroupList, comparator);
                Collections.sort(cardNumberList, comparator);
            }
        } else {
            getBindings().getAccountDropdwonAlert2().setValue(null);
            getBindings().getCardDropdownAlert2().setValue(null);
            getBindings().getCardDropdownAlert2().setValue(null);
            this.accountIdValue = null;
            this.accountIdList = null;
            this.cardGroupValue = null;
            this.cardGroupList = null;
            this.cardNumberList = null;
            this.cardNumberValue = null;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccountDropdwonAlert2());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupDowndownAlert2());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardDropdownAlert2());
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside partnerValueChangeListener method of Alerts");
    }

    public void partner2ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside partner2ValueChangeListener method of Alerts");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null) {
            accountIdList2 = new ArrayList<SelectItem>();
            accountIdValue2 = new ArrayList<String>();
            cardGroupList2 = new ArrayList<SelectItem>();
            cardGroupValue2 = new ArrayList<String>();
            cardNumberList2 = new ArrayList<SelectItem>();
            cardNumberValue2 = new ArrayList<String>();
            String[] partnerString = stringSplitter(populateStringValues(valueChangeEvent.getNewValue().toString()));
            if (partnerString.length > 0) {
                for (int i = 0; i < partnerInfoList.size(); i++) {
                    for (int p = 0; p < partnerString.length; p++) {
                        if (partnerInfoList.get(i).getPartnerValue().toString() != null &&
                            partnerInfoList.get(i).getPartnerValue().toString().equals(partnerString[p].trim()) &&
                            (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0)) {

                            for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
                                if (partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() != null) {
                                    SelectItem selectItem = new SelectItem();
                                    selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                    selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString() +
                                                        partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                    accountIdList2.add(selectItem);
                                    accountIdValue2.add(partnerInfoList.get(i).getPartnerValue().toString() +
                                                        partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                }
                                for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
                                    if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() != null) {
                                        SelectItem selectItem = new SelectItem();
                                        selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                        selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                            partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                            partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                        cardGroupList2.add(selectItem);
                                        cardGroupValue2.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                            partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                            partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                    }
                                    for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++) {
                                        if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                            partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() !=
                                            null) {
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                            selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                                partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString() +
                                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                            cardNumberList2.add(selectItem);
                                            cardNumberValue2.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                                 partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                                 partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString() +
                                                                 partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                        }
                                    }
                                }
                            }

                        }
                    }

                }
                initailAccountId2VAlue = accountIdValue2;
                initialCardGroup2Value = cardGroupValue2;
                initialCard2Value = cardNumberValue2;
                Collections.sort(accountIdList2, comparator);
                Collections.sort(cardGroupList2, comparator);
                Collections.sort(cardNumberList2, comparator);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertsAccountDropdown());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertCardGroupDropdown());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertDropdown());
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside partner2ValueChangeListener method of Alerts");
    }

    public void account2ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside account2ValueChangeListener method of Alerts");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null) {
            String[] accountString = stringSplitter(populateStringValues(valueChangeEvent.getNewValue().toString()).replaceAll(" ", ""));
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "accountlist");

            cardGroupList2 = new ArrayList<SelectItem>();
            cardGroupValue2 = new ArrayList<String>();
            cardNumberList2 = new ArrayList<SelectItem>();
            cardNumberValue2 = new ArrayList<String>();
            for (int z = 0; z < partnerInfoList.size(); z++) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "z " + z);
                if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "i " + i);
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
                                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "k " + k);
                                        if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID() != null) {
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                            selectItem.setValue(partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                                partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString() +
                                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                            cardGroupList2.add(selectItem);

                                            cardGroupValue2.add(partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                                partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString() +
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
                                                cardNumberList2.add(selectItem);
                                                cardNumberValue2.add(partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                                     partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString() +
                                                                     partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString() +
                                                                     partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
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

            initialCard2Value = cardNumberValue2;
            initialCardGroup2Value = cardGroupValue2;
            Collections.sort(accountIdList2, comparator);
            Collections.sort(cardGroupList2, comparator);
            Collections.sort(cardNumberList2, comparator);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertsAccountDropdown());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertCardGroupDropdown());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertDropdown());
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside account2ValueChangeListener method of Alerts");
    }

    public void accountValueChangeListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside accountValueChangeListener method of Alerts");
        if (valueChangeEvent.getNewValue() != null) {
            String[] accountString = stringSplitter(populateStringValues(valueChangeEvent.getNewValue().toString()).replaceAll(" ", ""));
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "accountlist");
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();
            cardNumberList = new ArrayList<SelectItem>();
            cardNumberValue = new ArrayList<String>();
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
                                                               partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString() +
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
                                                cardNumberList.add(selectItem);
                                                cardNumberValue.add(partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                                    partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString() +
                                                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString() +
                                                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
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
            initialCardValue = cardNumberValue;
            initialCardGroupValue = cardGroupValue;
            Collections.sort(accountIdList, comparator);
            Collections.sort(cardGroupList, comparator);
            Collections.sort(cardNumberList, comparator);
        } else {
            getBindings().getCardGroupDowndownAlert2().setValue(null);
            getBindings().getCardDropdownAlert2().setValue(null);
            this.cardGroupValue = null;
            this.cardNumberValue = null;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccountDropdwonAlert2());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupDowndownAlert2());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardDropdownAlert2());
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside accountValueChangeListener method of Alerts");
    }

    public void cardgroup2ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside cardgroup2ValueChangeListener method of Alerts");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null) {
            String[] cardgroupString = stringSplitter(populateStringValues(valueChangeEvent.getNewValue().toString()).replaceAll(" ", ""));
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "accountlist");
            cardNumberList2 = new ArrayList<SelectItem>();
            cardNumberValue2 = new ArrayList<String>();
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
                                                cardNumberList2.add(selectItem);
                                                cardNumberValue2.add(partnerInfoList.get(z).getPartnerValue().toString().trim() +
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
                    initialCard2Value = cardNumberValue2;
                    Collections.sort(cardGroupList2, comparator);
                    Collections.sort(cardNumberList2, comparator);
                }
            }
        } else {
            getBindings().getCardDropdownAlert2().setValue(null);
            this.cardNumberValue = null;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertsAccountDropdown());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertCardGroupDropdown());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertDropdown());
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside cardgroup2ValueChangeListener method of Alerts");
    }


    public void cardgroupValueChangeListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside cardgroupValueChangeListener method of Alerts");
        if (valueChangeEvent.getNewValue() != null) {
            String[] cardgroupString = stringSplitter(populateStringValues(valueChangeEvent.getNewValue().toString()).replaceAll(" ", ""));
            cardNumberList = new ArrayList<SelectItem>();
            cardNumberValue = new ArrayList<String>();
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
                                                cardNumberList.add(selectItem);
                                                cardNumberValue.add(partnerInfoList.get(z).getPartnerValue().toString().trim() +
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
                    initialCardValue = cardNumberValue;
                    Collections.sort(cardGroupList, comparator);
                    Collections.sort(cardNumberList, comparator);
                }
            }
        } else {
            getBindings().getCardDropdownAlert2().setValue(null);
            this.cardNumberValue = null;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccountDropdwonAlert2());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupDowndownAlert2());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardDropdownAlert2());
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside cardgroupValueChangeListener method of Alerts");
    }

    public void setPartnerIdList(List<SelectItem> partnerIdList) {
        this.partnerIdList = partnerIdList;
    }

    public List<SelectItem> getPartnerIdList() {
        return partnerIdList;
    }

    public void setPartnerIdValue(List<String> partnerIdValue) {
        this.partnerIdValue = partnerIdValue;
    }

    public List<String> getPartnerIdValue() {
        return partnerIdValue;
    }

    public void setAccountIdList(List<SelectItem> accountIdList) {
        this.accountIdList = accountIdList;
    }

    public List<SelectItem> getAccountIdList() {
        return accountIdList;
    }

    public void setAccountIdValue(List<String> accountIdValue) {
        this.accountIdValue = accountIdValue;
    }

    public List<String> getAccountIdValue() {
        return accountIdValue;
    }

    public void setCardNumberList(List<SelectItem> cardNumberList) {
        this.cardNumberList = cardNumberList;
    }

    public List<SelectItem> getCardNumberList() {
        return cardNumberList;
    }

    public void setCardNumberValue(List<String> cardNumberValue) {
        this.cardNumberValue = cardNumberValue;
    }

    public List<String> getCardNumberValue() {
        return cardNumberValue;
    }

    public void setFuelCapacityAlert(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside setFuelCapacityAlert method of Alerts");
        if (validateinput2()) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "inside alerts bean");
            String localUserEmail = "";
            String localUserFirstName = "";
            String localUserMobileNo = "";
            if (session != null && session.getAttribute(Constants.SESSION_USER_INFO) != null) {
                User user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
                if (user.getEmailID() != null && user.getFirstName() != null) {
                    localUserEmail = user.getEmailID();
                    localUserFirstName = user.getFirstName();
                }
                if (user.getPhoneNumber() != null) {
                    localUserMobileNo = user.getPhoneNumber();
                } else {
                    localUserMobileNo = LOCALNO;
                }
            }

            String cardListString = "";
            for (String s : initialCardValue) {
                cardListString += s + ",";
            }
            cardListString = cardListString.substring(0, cardListString.length() - 1);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "converted cardListString arraylist " + cardListString);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "converted cardListString arraylist " +
                        populateStringValues(getBindings().getCardDropdownAlert2().getValue().toString().trim()));
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "length1 " + cardListString.length());
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "lenght2 " +
                        (populateStringValues(getBindings().getCardDropdownAlert2().getValue().toString().trim()).replaceAll(" ", "")).length());

            if (cardListString.length() ==
                (populateStringValues(getBindings().getCardDropdownAlert2().getValue().toString().trim()).replaceAll(" ", "")).length()) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "card dropdown is unchanged");
                readCardGroup();
            } else {
                readCard();
            }

        } else {
            getBindings().getSuccessAlert2().setRendered(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert2());
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside setFuelCapacityAlert method of Alerts");
    }

    public boolean validateinput2() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside validateinput2 method of Alerts");
        boolean validinput2 = true;
        if (getBindings().getPartnerDropdownAlert2().getValue() != null &&
            getBindings().getPartnerDropdownAlert2().getValue().toString().trim().replaceAll(" ", "") != null &&
            !getBindings().getPartnerDropdownAlert2().getValue().toString().trim().replaceAll(" ", "").equals("") &&
            getBindings().getAccountDropdwonAlert2().getValue() != null &&
            getBindings().getAccountDropdwonAlert2().getValue().toString().trim().replaceAll(" ", "") != null &&
            !getBindings().getAccountDropdwonAlert2().getValue().toString().trim().replaceAll(" ", "").equals("") &&
            getBindings().getCardGroupDowndownAlert2().getValue() != null &&
            getBindings().getCardGroupDowndownAlert2().getValue().toString().trim().replaceAll(" ", "") != null &&
            !getBindings().getCardGroupDowndownAlert2().getValue().toString().trim().replaceAll(" ", "").equals("") &&
            getBindings().getCardDropdownAlert2().getValue() != null &&
            getBindings().getCardDropdownAlert2().getValue().toString().trim().replaceAll(" ", "") != null &&
            !getBindings().getCardDropdownAlert2().getValue().toString().trim().replaceAll(" ", "").equals("") &&
            getBindings().getFuelCapacityAlert2().getValue() != null && getBindings().getFuelCapacityAlert2().getValue().toString().trim() != null) {
            String regex = "\\d+";
            if (getBindings().getFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ", "").matches(regex)) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "fuelCapacityAlert2 " +
                            getBindings().getFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ", ""));
                displayErrorComponent(getBindings().getFuelCapacityAlert2(), false);
            } else {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "fuelCapacityAlert22 " +
                            getBindings().getFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ", ""));
                validinput2 = false;
                displayErrorComponent(getBindings().getFuelCapacityAlert2(), true);
                getBindings().getAlert2ValidData().setVisible(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert2ValidData());
                getBindings().getMandatoryMsg3().setRendered(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg3());
            }
        } else {
            validinput2 = false;
            if (getBindings().getPartnerDropdownAlert2().getValue() != null) {
                displayErrorComponent(getBindings().getPartnerDropdownAlert2(), false);
            } else {
                displayErrorComponent(getBindings().getPartnerDropdownAlert2(), true);
            }
            if (getBindings().getAccountDropdwonAlert2().getValue() != null) {
                displayErrorComponent(getBindings().getAccountDropdwonAlert2(), false);
            } else {
                displayErrorComponent(getBindings().getAccountDropdwonAlert2(), true);
            }
            if (getBindings().getCardGroupDowndownAlert2().getValue() != null) {
                displayErrorComponent(getBindings().getCardGroupDowndownAlert2(), false);
            } else {
                displayErrorComponent(getBindings().getCardGroupDowndownAlert2(), true);
            }
            if (getBindings().getCardDropdownAlert2().getValue() != null) {
                displayErrorComponent(getBindings().getCardDropdownAlert2(), false);
            } else {
                displayErrorComponent(getBindings().getCardDropdownAlert2(), true);
            }
            if (getBindings().getFuelCapacityAlert2().getValue() != null) {
                displayErrorComponent(getBindings().getFuelCapacityAlert2(), false);
            } else {
                displayErrorComponent(getBindings().getFuelCapacityAlert2(), true);
            }
            getBindings().getMandatoryMsg3().setRendered(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg3());
            getBindings().getAlert2ValidData().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert2ValidData());
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside validateinput2 method of Alerts");
        return validinput2;
    }

    public void readCardGroup() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside readCardGroup method of Alerts");
        String cardGroupListString = "";
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Account id value length readCardGroup" + cardGroupValue.size());
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Account id value length readCardGroup" + initialCardGroupValue.size());

        for (String s : initialCardGroupValue) {
            cardGroupListString += s + ",";
        }
        cardGroupListString = cardGroupListString.substring(0, cardGroupListString.length() - 1);
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "converted cardGroupListString arraylist " + cardGroupListString);
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "converted cardGroupListString arraylist " +
                    populateStringValues(getBindings().getCardGroupDowndownAlert2().getValue().toString().trim()));
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "length1 " + cardGroupListString.length());
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "lenght2 " +
                    (populateStringValues(getBindings().getCardGroupDowndownAlert2().getValue().toString().trim()).replaceAll(" ", "")).length());

        if (cardGroupListString.length() ==
            (populateStringValues(getBindings().getCardGroupDowndownAlert2().getValue().toString().trim()).replaceAll(" ", "")).length()) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "cardGroup dropdown is unchanged");
            readAccount();
        } else {
            //logic to store in db at cardgroup level

            BindingContainer localBinding = BindingContext.getCurrent().getCurrentBindingsEntry();
            if (localBinding != null) {
                OperationBinding operationBinding = localBinding.getOperationBinding(Constants.SUBSCRIBE_ALERTS_LITERAL);
                if (operationBinding != null) {
                    BigInteger bigint = BigInteger.ONE;
                    req.setRuleID(bigint);
                    AlertsSubscribeCustomerType customerobj = new AlertsSubscribeCustomerType();
                    customerobj.setEmailID(userEmail);
                    customerobj.setCustomerID(userEmail);
                    customerobj.setFIrstName(userFirstName);
                    BigInteger bigint2 = new BigInteger(LOCALNO);
                    customerobj.setMobileNumber(bigint2);
                    req.setCustomer(customerobj);

                    AlertsSubscribeFrequencyType freq = new AlertsSubscribeFrequencyType();
                    freq.setScheduleFrequency(Constants.DAILY_LITERAL);

                    req.setSubscribeFrequency(freq);
                    req.setNotificationChannel(Constants.EMAIL_LITERAL);
                    req.setNotificationFormat(Constants.EXCEL_LITERAL);
                    DCBindingContainer bindings2 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                    DCIteratorBinding cardRuleSubscriptionIter = bindings2.findIteratorBinding(PRTCARDRULESUBSCRIPTIONVO1ITERATORLITRERAL);

                    DCIteratorBinding prtCardFuelCapacityIter = bindings2.findIteratorBinding(PRTCARDFUELCAPACITYVO1ITERATORLITRERAL);

                    ViewObject cardRuleSubscription = cardRuleSubscriptionIter.getViewObject();
                    ViewObject prtCardFuelCapacity = prtCardFuelCapacityIter.getViewObject();

                    if (cardRuleSubscriptionIter != null && prtCardFuelCapacityIter != null) {
                        String cardgroup[] =
                            (populateStringValues(getBindings().getCardGroupDowndownAlert2().getValue().toString().trim()).replaceAll(" ", "")).split(",");
                        String accountId = "";
                        String partnerId = "";
                        String cardgroupId = "";
                        for (int cardgrp = 0; cardgrp < cardgroup.length; cardgrp++) {
                            operationBinding.getParamsMap().put(Constants.SUBSCRIBE_REQUEST_LITERAL, req);
                            response = (AlertsSubscribeResponse)operationBinding.execute();
                            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "response = " + response.getSubscriptionID());

                            if (response.getSubscriptionID() != null){
                                Row cardRuleSubscriptionRow = cardRuleSubscription.createRow();
                                cardRuleSubscriptionRow.setAttribute("CountryCode", countryCode);
                                cardRuleSubscriptionRow.setAttribute(Constants.USER_ID_LITERAL, userEmail);
                                cardRuleSubscriptionRow.setAttribute(Constants.SUBSCR_ID_LITERAL, response.getSubscriptionID().toString().trim());
                                cardRuleSubscriptionRow.setAttribute(Constants.RULE_ID_LITERAL, "2");
                                cardRuleSubscriptionRow.setAttribute(Constants.SUBSCRSTATUS_LITERAL, Constants.ACTIVE_LITERAL);
                                partnerId = cardgroup[cardgrp].substring(0, Constants.EIGHT);
                                accountId = cardgroup[cardgrp].substring(Constants.EIGHT, Constants.EIGHTEEN);
                                cardgroupId = cardgroup[cardgrp].substring(Constants.EIGHTEEN);
                                cardRuleSubscriptionRow.setAttribute(Constants.PARTNER_ID_LITERAL, partnerId);
                                cardRuleSubscriptionRow.setAttribute(Constants.ACCOUNTIDLITERAL, accountId);
                                cardRuleSubscriptionRow.setAttribute(Constants.CARDGROUP_MAIN_LITERAL, cardgroupId.substring(0, Constants.THREE));
                                cardRuleSubscriptionRow.setAttribute(Constants.CARDGROUP_SUB_LITERAL, cardgroupId.substring(Constants.THREE, Constants.SIX));
                                cardRuleSubscriptionRow.setAttribute(Constants.CARDGROUP_SEQ_LITERAL, cardgroupId.substring(Constants.SIX));
                                cardRuleSubscriptionRow.setAttribute(Constants.MODIFIEDBYLITERAL, userEmail);
                                cardRuleSubscriptionRow.setAttribute(Constants.CADRDKSID_LITERAL, Constants.ALL_LITERAL);
                                cardRuleSubscriptionRow.setAttribute(Constants.ASSOCIATIONTYPELITERAL, userAssociationType);
                                cardRuleSubscription.insertRow(cardRuleSubscriptionRow);
                                Row prtCardFuelCapacityRow = prtCardFuelCapacity.createRow();
                                prtCardFuelCapacityRow.setAttribute("CountryCode", countryCode);
                                prtCardFuelCapacityRow.setAttribute(Constants.SUBSCR_ID_LITERAL, response.getSubscriptionID().toString().trim());
                                prtCardFuelCapacityRow.setAttribute(Constants.RULE_ID_LITERAL, "2");

                                if (getBindings().getLtrPerDayRadio().getValue() != null &&
                                    getBindings().getLtrPerDayRadio().getValue().toString().equalsIgnoreCase(Constants.TRUE_LITERAL)) {
                                    prtCardFuelCapacityRow.setAttribute(Constants.FUEL_PER_DAY_LITERAL,
                                                                        getBindings().getFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ",
                                                                                                                                                      ""));
                                }

                                if (getBindings().getLtrPerWeekRadio().getValue() != null &&
                                    getBindings().getLtrPerWeekRadio().getValue().toString().equalsIgnoreCase(Constants.TRUE_LITERAL)) {
                                    prtCardFuelCapacityRow.setAttribute(Constants.FUEL_PER_WEEK_LITERAL,
                                                                        getBindings().getFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ",
                                                                                                                                                      ""));
                                }

                                if (getBindings().getLtrPerMonthRadio().getValue() != null &&
                                    getBindings().getLtrPerMonthRadio().getValue().toString().equalsIgnoreCase(Constants.TRUE_LITERAL)) {
                                    prtCardFuelCapacityRow.setAttribute(Constants.FUEL_PER_MONTH_LITERAL,
                                                                        getBindings().getFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ",
                                                                                                                                                      ""));
                                }

                                prtCardFuelCapacityRow.setAttribute(Constants.MODIFIEDBYLITERAL, userEmail);
                            }
                        }
                        operationBinding = localBinding.getOperationBinding(Constants.COMMIT_LITERAL);
                        operationBinding.execute();
                    }
                }
            }

            getBindings().getAlert2ValidData().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert2ValidData());
            getBindings().getSuccessAlert2().setRendered(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert2());
            getBindings().getFuelCapacityAlert2().setDisabled(true);
            getBindings().getOkButtonAlert2().setRendered(false);
            getBindings().getCancelButtonAlert2().setRendered(false);
            getBindings().getLtrPerMonthRadio().setDisabled(true);
            getBindings().getLtrPerWeekRadio().setDisabled(true);
            getBindings().getLtrPerDayRadio().setDisabled(true);
            getBindings().getCardDropdownAlert2().setDisabled(true);
            getBindings().getCardGroupDowndownAlert2().setDisabled(true);
            getBindings().getAccountDropdwonAlert2().setDisabled(true);
            getBindings().getPartnerDropdownAlert2().setDisabled(true);
            getBindings().getCloseButtonAlert2().setRendered(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFuelCapacityAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getOkButtonAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCancelButtonAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLtrPerMonthRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLtrPerWeekRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLtrPerDayRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardDropdownAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupDowndownAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccountDropdwonAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartnerDropdownAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCloseButtonAlert2());
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside readCardGroup method of Alerts");
    }

    public void readCard() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside readCard method of Alerts");
        //logic to store in db at card level

        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Account id value length readCardGroup" + cardNumberList.size());
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Account id value length readCardGroup" + initialCardValue.size());

        BindingContainer localBinding = BindingContext.getCurrent().getCurrentBindingsEntry();

        if (localBinding != null) {
            OperationBinding operationBinding = localBinding.getOperationBinding(Constants.SUBSCRIBE_ALERTS_LITERAL);


            if (operationBinding != null) {
                BigInteger bigint = BigInteger.ONE;
                req.setRuleID(bigint);
                AlertsSubscribeCustomerType customerobj = new AlertsSubscribeCustomerType();
                customerobj.setEmailID(userEmail);
                customerobj.setCustomerID(userEmail);
                customerobj.setFIrstName(userFirstName);
                BigInteger bigint2 = new BigInteger(LOCALNO);
                customerobj.setMobileNumber(bigint2);
                req.setCustomer(customerobj);

                AlertsSubscribeFrequencyType freq = new AlertsSubscribeFrequencyType();
                freq.setScheduleFrequency(Constants.DAILY_LITERAL);

                req.setSubscribeFrequency(freq);
                req.setNotificationChannel(Constants.EMAIL_LITERAL);
                req.setNotificationFormat(Constants.EXCEL_LITERAL);
                DCBindingContainer bindings2 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding CardRuleSubscriptionIter = bindings2.findIteratorBinding(PRTCARDRULESUBSCRIPTIONVO1ITERATORLITRERAL);

                DCIteratorBinding PrtCardFuelCapacityIter = bindings2.findIteratorBinding(PRTCARDFUELCAPACITYVO1ITERATORLITRERAL);

                ViewObject cardRuleSubscription = CardRuleSubscriptionIter.getViewObject();
                ViewObject prtCardFuelCapacity = PrtCardFuelCapacityIter.getViewObject();

                if (CardRuleSubscriptionIter != null && PrtCardFuelCapacityIter != null) {
                    String card[] = (populateStringValues(getBindings().getCardDropdownAlert2().getValue().toString().trim()).replaceAll(" ", "")).split(",");
                    String accountId = "";
                    String partnerId = "";
                    String cardgroupId = "";
                    String cardId = "";
                    for (int cardcc = 0; cardcc < card.length; cardcc++) {
                        operationBinding.getParamsMap().put(Constants.SUBSCRIBE_REQUEST_LITERAL, req);
                        response = (AlertsSubscribeResponse)operationBinding.execute();
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "response = " + response.getSubscriptionID());

                        if (response.getSubscriptionID() != null) {
                            Row cardRuleSubscriptionRow = cardRuleSubscription.createRow();
                            cardRuleSubscriptionRow.setAttribute("CountryCode", countryCode);
                            cardRuleSubscriptionRow.setAttribute(Constants.USER_ID_LITERAL, userEmail);
                            cardRuleSubscriptionRow.setAttribute(Constants.SUBSCR_ID_LITERAL, response.getSubscriptionID().toString().trim());
                            cardRuleSubscriptionRow.setAttribute(Constants.RULE_ID_LITERAL, "2");
                            cardRuleSubscriptionRow.setAttribute(Constants.SUBSCRSTATUS_LITERAL, Constants.ACTIVE_LITERAL);
                            partnerId = card[cardcc].substring(0, Constants.EIGHT);
                            accountId = card[cardcc].substring(Constants.EIGHT, Constants.EIGHTEEN);
                            cardgroupId = card[cardcc].substring(Constants.EIGHTEEN, Constants.TWENTYNINE);
                            cardId = card[cardcc].substring(Constants.TWENTYNINE);
                            cardRuleSubscriptionRow.setAttribute(Constants.PARTNER_ID_LITERAL, partnerId);
                            cardRuleSubscriptionRow.setAttribute(Constants.ACCOUNTIDLITERAL, accountId);
                            cardRuleSubscriptionRow.setAttribute(Constants.CARDGROUP_MAIN_LITERAL, cardgroupId.substring(0, Constants.THREE));
                            cardRuleSubscriptionRow.setAttribute(Constants.CARDGROUP_SUB_LITERAL, cardgroupId.substring(Constants.THREE, Constants.SIX));
                            cardRuleSubscriptionRow.setAttribute(Constants.CARDGROUP_SEQ_LITERAL, cardgroupId.substring(Constants.SIX, Constants.ELEVEN));
                            cardRuleSubscriptionRow.setAttribute(Constants.MODIFIEDBYLITERAL, userEmail);
                            cardRuleSubscriptionRow.setAttribute(Constants.CADRDKSID_LITERAL, cardId);
                            cardRuleSubscriptionRow.setAttribute(Constants.ASSOCIATIONTYPELITERAL, userAssociationType);
                            cardRuleSubscription.insertRow(cardRuleSubscriptionRow);
                            Row prtCardFuelCapacityRow = prtCardFuelCapacity.createRow();
                            prtCardFuelCapacityRow.setAttribute("CountryCode", countryCode);
                            prtCardFuelCapacityRow.setAttribute(Constants.SUBSCR_ID_LITERAL, response.getSubscriptionID().toString().trim());
                            prtCardFuelCapacityRow.setAttribute(Constants.RULE_ID_LITERAL, "2");
                            if (getBindings().getLtrPerDayRadio().getValue() != null &&
                                getBindings().getLtrPerDayRadio().getValue().toString().equalsIgnoreCase(Constants.TRUE_LITERAL)) {
                                prtCardFuelCapacityRow.setAttribute(Constants.FUEL_PER_DAY_LITERAL,
                                                                    getBindings().getFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ", ""));
                            }
                            if (getBindings().getLtrPerWeekRadio().getValue() != null &&
                                getBindings().getLtrPerWeekRadio().getValue().toString().equalsIgnoreCase(Constants.TRUE_LITERAL)) {
                                prtCardFuelCapacityRow.setAttribute(Constants.FUEL_PER_WEEK_LITERAL,
                                                                    getBindings().getFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ", ""));
                            }
                            if (getBindings().getLtrPerMonthRadio().getValue() != null &&
                                getBindings().getLtrPerMonthRadio().getValue().toString().equalsIgnoreCase(Constants.TRUE_LITERAL)) {
                                prtCardFuelCapacityRow.setAttribute(Constants.FUEL_PER_MONTH_LITERAL,
                                                                    getBindings().getFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ", ""));
                            }
                            prtCardFuelCapacityRow.setAttribute(Constants.MODIFIEDBYLITERAL, userEmail);
                        }
                    }
                    operationBinding = localBinding.getOperationBinding(Constants.COMMIT_LITERAL);
                    operationBinding.execute();
                }
            }

            getBindings().getAlert2ValidData().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert2ValidData());
            getBindings().getSuccessAlert2().setRendered(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert2());
            getBindings().getFuelCapacityAlert2().setDisabled(true);
            getBindings().getOkButtonAlert2().setRendered(false);
            getBindings().getCancelButtonAlert2().setRendered(false);
            getBindings().getLtrPerMonthRadio().setDisabled(true);
            getBindings().getLtrPerWeekRadio().setDisabled(true);
            getBindings().getLtrPerDayRadio().setDisabled(true);
            getBindings().getCardDropdownAlert2().setDisabled(true);
            getBindings().getCardGroupDowndownAlert2().setDisabled(true);
            getBindings().getAccountDropdwonAlert2().setDisabled(true);
            getBindings().getPartnerDropdownAlert2().setDisabled(true);
            getBindings().getCloseButtonAlert2().setRendered(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFuelCapacityAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getOkButtonAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCancelButtonAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLtrPerMonthRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLtrPerWeekRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLtrPerDayRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardDropdownAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupDowndownAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccountDropdwonAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartnerDropdownAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCloseButtonAlert2());
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside readCard method of Alerts");
    }

    public void readAccount() {
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Account id value length readAccount" + accountIdValue.size());
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Account id value length readAccount" + initailAccountIdVAlue.size());
        String accountListString = "";

        for (String s : initailAccountIdVAlue) {
            accountListString += s + ",";
        }
        accountListString = accountListString.substring(0, accountListString.length() - 1);

        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "converted accListString arraylist " + accountListString);
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "converted accListString arraylist " +
                    (populateStringValues(getBindings().getAccountDropdwonAlert2().getValue().toString().trim()).replaceAll(" ", "")));
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "length1 " + accountListString.length());
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "lenght2 " +
                    (populateStringValues(getBindings().getAccountDropdwonAlert2().getValue().toString().trim()).replaceAll(" ", "")).length());


        if (accountListString.length() ==
            (populateStringValues(getBindings().getAccountDropdwonAlert2().getValue().toString().trim()).replaceAll(" ", "")).length()) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "account dropdown is unchanged");
            readPartner();
        } else {

            //logic to store in db at account level


            BindingContainer localBinding = BindingContext.getCurrent().getCurrentBindingsEntry();

            if (localBinding != null) {
                OperationBinding operationBinding = localBinding.getOperationBinding(Constants.SUBSCRIBE_ALERTS_LITERAL);


                if (operationBinding != null) {
                    BigInteger bigint = BigInteger.ONE;
                    req.setRuleID(bigint);
                    AlertsSubscribeCustomerType customerobj = new AlertsSubscribeCustomerType();
                    customerobj.setEmailID(userEmail);
                    customerobj.setCustomerID(userEmail);
                    customerobj.setFIrstName(userFirstName);
                    BigInteger bigint2 = new BigInteger(LOCALNO);
                    customerobj.setMobileNumber(bigint2);
                    req.setCustomer(customerobj);

                    AlertsSubscribeFrequencyType freq = new AlertsSubscribeFrequencyType();
                    freq.setScheduleFrequency(Constants.DAILY_LITERAL);

                    req.setSubscribeFrequency(freq);
                    req.setNotificationChannel(Constants.EMAIL_LITERAL);
                    req.setNotificationFormat(Constants.EXCEL_LITERAL);
                    DCBindingContainer bindings2 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                    DCIteratorBinding CardRuleSubscriptionIter = bindings2.findIteratorBinding(PRTCARDRULESUBSCRIPTIONVO1ITERATORLITRERAL);
                    DCIteratorBinding PrtCardFuelCapacityIter = bindings2.findIteratorBinding(PRTCARDFUELCAPACITYVO1ITERATORLITRERAL);

                    ViewObject cardRuleSubscription = CardRuleSubscriptionIter.getViewObject();
                    ViewObject prtCardFuelCapacity = PrtCardFuelCapacityIter.getViewObject();

                    if (CardRuleSubscriptionIter != null && PrtCardFuelCapacityIter != null) {
                        String account[] =
                            (populateStringValues(getBindings().getAccountDropdwonAlert2().getValue().toString().trim()).replaceAll(" ", "")).split(",");
                        String accountId = "";
                        String partnerId = "";
                        for (int acc = 0; acc < account.length; acc++) {
                            operationBinding.getParamsMap().put(Constants.SUBSCRIBE_REQUEST_LITERAL, req);
                            response = (AlertsSubscribeResponse)operationBinding.execute();
                            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "response = " + response.getSubscriptionID());

                            if (response.getSubscriptionID() != null) {
                                Row cardRuleSubscriptionRow = cardRuleSubscription.createRow();
                                cardRuleSubscriptionRow.setAttribute("CountryCode", countryCode);
                                cardRuleSubscriptionRow.setAttribute(Constants.USER_ID_LITERAL, userEmail);
                                cardRuleSubscriptionRow.setAttribute(Constants.SUBSCR_ID_LITERAL, response.getSubscriptionID().toString().trim());
                                cardRuleSubscriptionRow.setAttribute(Constants.RULE_ID_LITERAL, "2");
                                cardRuleSubscriptionRow.setAttribute(Constants.SUBSCRSTATUS_LITERAL, Constants.ACTIVE_LITERAL);
                                partnerId = account[acc].substring(0, Constants.EIGHT);
                                accountId = account[acc].substring(Constants.EIGHT);
                                cardRuleSubscriptionRow.setAttribute(Constants.PARTNER_ID_LITERAL, partnerId);
                                cardRuleSubscriptionRow.setAttribute(Constants.ACCOUNTIDLITERAL, accountId);
                                cardRuleSubscriptionRow.setAttribute(Constants.CARDGROUP_MAIN_LITERAL, Constants.ALL_LITERAL);
                                cardRuleSubscriptionRow.setAttribute(Constants.CARDGROUP_SUB_LITERAL, Constants.ALL_LITERAL);
                                cardRuleSubscriptionRow.setAttribute(Constants.CARDGROUP_SEQ_LITERAL, Constants.ALL_LITERAL);
                                cardRuleSubscriptionRow.setAttribute(Constants.MODIFIEDBYLITERAL, userEmail);
                                cardRuleSubscriptionRow.setAttribute(Constants.CADRDKSID_LITERAL, Constants.ALL_LITERAL);
                                cardRuleSubscriptionRow.setAttribute(Constants.ASSOCIATIONTYPELITERAL, userAssociationType);
                                cardRuleSubscription.insertRow(cardRuleSubscriptionRow);
                                Row prtCardFuelCapacityRow = prtCardFuelCapacity.createRow();
                                prtCardFuelCapacityRow.setAttribute("CountryCode", countryCode);
                                prtCardFuelCapacityRow.setAttribute(Constants.SUBSCR_ID_LITERAL, response.getSubscriptionID().toString().trim());
                                prtCardFuelCapacityRow.setAttribute(Constants.RULE_ID_LITERAL, "2");
                                if (getBindings().getLtrPerDayRadio().getValue() != null &&
                                    getBindings().getLtrPerDayRadio().getValue().toString().equalsIgnoreCase(Constants.TRUE_LITERAL)) {
                                    prtCardFuelCapacityRow.setAttribute(Constants.FUEL_PER_DAY_LITERAL,
                                                                        getBindings().getFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ",
                                                                                                                                                      ""));
                                }
                                if (getBindings().getLtrPerWeekRadio().getValue() != null &&
                                    getBindings().getLtrPerWeekRadio().getValue().toString().equalsIgnoreCase(Constants.TRUE_LITERAL)) {
                                    prtCardFuelCapacityRow.setAttribute(Constants.FUEL_PER_WEEK_LITERAL,
                                                                        getBindings().getFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ",
                                                                                                                                                      ""));
                                }
                                if (getBindings().getLtrPerMonthRadio().getValue() != null &&
                                    getBindings().getLtrPerMonthRadio().getValue().toString().equalsIgnoreCase(Constants.TRUE_LITERAL)) {
                                    prtCardFuelCapacityRow.setAttribute(Constants.FUEL_PER_MONTH_LITERAL,
                                                                        getBindings().getFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ",
                                                                                                                                                      ""));
                                }
                                prtCardFuelCapacityRow.setAttribute(Constants.MODIFIEDBYLITERAL, userEmail);
                            }
                        }
                        operationBinding = localBinding.getOperationBinding(Constants.COMMIT_LITERAL);
                        operationBinding.execute();
                    }
                }
            }
            getBindings().getAlert2ValidData().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert2ValidData());
            getBindings().getSuccessAlert2().setRendered(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert2());
            getBindings().getFuelCapacityAlert2().setDisabled(true);
            getBindings().getOkButtonAlert2().setRendered(false);
            getBindings().getCancelButtonAlert2().setRendered(false);
            getBindings().getLtrPerMonthRadio().setDisabled(true);
            getBindings().getLtrPerWeekRadio().setDisabled(true);
            getBindings().getLtrPerDayRadio().setDisabled(true);
            getBindings().getCardDropdownAlert2().setDisabled(true);
            getBindings().getCardGroupDowndownAlert2().setDisabled(true);
            getBindings().getAccountDropdwonAlert2().setDisabled(true);
            getBindings().getPartnerDropdownAlert2().setDisabled(true);
            getBindings().getCloseButtonAlert2().setRendered(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFuelCapacityAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getOkButtonAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCancelButtonAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLtrPerMonthRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLtrPerWeekRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLtrPerDayRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardDropdownAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupDowndownAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccountDropdwonAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartnerDropdownAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCloseButtonAlert2());
        }
    }

    public void readPartner() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside readPartner method of Alerts");
        String partnerListString = "";
        for (String s : partnerIdValue) {
            partnerListString += s + ",";
        }
        partnerListString = partnerListString.substring(0, partnerListString.length() - 1);
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "converted partnerListString arraylist " + partnerListString);
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "converted partnerListString arraylist " +
                    populateStringValues(getBindings().getPartnerDropdownAlert2().getValue().toString().trim()));
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "length1 " + partnerListString.length());
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "lenght2 " +
                    (populateStringValues(getBindings().getPartnerDropdownAlert2().getValue().toString().trim()).replaceAll(" ", "")).length());
        if (partnerListString.length() ==
            (populateStringValues(getBindings().getPartnerDropdownAlert2().getValue().toString().trim()).replaceAll(" ", "")).length()) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "partner dropdown is unchanged");
        }
        //logic to store in db at partner level
        BindingContainer localBinding = BindingContext.getCurrent().getCurrentBindingsEntry();
        if (localBinding != null) {
            OperationBinding operationBinding = localBinding.getOperationBinding(Constants.SUBSCRIBE_ALERTS_LITERAL);
            if (operationBinding != null) {
                BigInteger bigint = BigInteger.ONE;
                req.setRuleID(bigint);
                AlertsSubscribeCustomerType customerobj = new AlertsSubscribeCustomerType();
                customerobj.setEmailID(userEmail);
                customerobj.setCustomerID(userEmail);
                customerobj.setFIrstName(userFirstName);
                BigInteger bigint2 = new BigInteger(LOCALNO);
                customerobj.setMobileNumber(bigint2);
                req.setCustomer(customerobj);
                AlertsSubscribeFrequencyType freq = new AlertsSubscribeFrequencyType();
                freq.setScheduleFrequency(Constants.DAILY_LITERAL);
                req.setSubscribeFrequency(freq);
                req.setNotificationChannel(Constants.EMAIL_LITERAL);
                req.setNotificationFormat(Constants.EXCEL_LITERAL);
                DCBindingContainer bindings2 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding CardRuleSubscriptionIter = bindings2.findIteratorBinding(PRTCARDRULESUBSCRIPTIONVO1ITERATORLITRERAL);
                DCIteratorBinding PrtCardFuelCapacityIter = bindings2.findIteratorBinding(PRTCARDFUELCAPACITYVO1ITERATORLITRERAL);
                ViewObject cardRuleSubscription = CardRuleSubscriptionIter.getViewObject();
                ViewObject prtCardFuelCapacity = PrtCardFuelCapacityIter.getViewObject();
                if (CardRuleSubscriptionIter != null && PrtCardFuelCapacityIter != null) {
                    String partner[] =
                        (populateStringValues(getBindings().getPartnerDropdownAlert2().getValue().toString().trim()).replaceAll(" ", "")).split(",");
                    for (int part = 0; part < partner.length; part++) {
                        operationBinding.getParamsMap().put(Constants.SUBSCRIBE_REQUEST_LITERAL, req);
                        response = (AlertsSubscribeResponse)operationBinding.execute();
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "response = " + response.getSubscriptionID());
                        if (response.getSubscriptionID() != null) {
                            Row cardRuleSubscriptionRow = cardRuleSubscription.createRow();
                            cardRuleSubscriptionRow.setAttribute("CountryCode", countryCode);
                            cardRuleSubscriptionRow.setAttribute(Constants.USER_ID_LITERAL, userEmail);
                            cardRuleSubscriptionRow.setAttribute(Constants.SUBSCR_ID_LITERAL, response.getSubscriptionID().toString().trim());
                            cardRuleSubscriptionRow.setAttribute(Constants.RULE_ID_LITERAL, "2");
                            cardRuleSubscriptionRow.setAttribute(Constants.SUBSCRSTATUS_LITERAL, Constants.ACTIVE_LITERAL);
                            cardRuleSubscriptionRow.setAttribute(Constants.PARTNER_ID_LITERAL, partner[part]);
                            cardRuleSubscriptionRow.setAttribute(Constants.ACCOUNTIDLITERAL, Constants.ALL_LITERAL);
                            cardRuleSubscriptionRow.setAttribute(Constants.CARDGROUP_MAIN_LITERAL, Constants.ALL_LITERAL);
                            cardRuleSubscriptionRow.setAttribute(Constants.CARDGROUP_SUB_LITERAL, Constants.ALL_LITERAL);
                            cardRuleSubscriptionRow.setAttribute(Constants.CARDGROUP_SEQ_LITERAL, Constants.ALL_LITERAL);
                            cardRuleSubscriptionRow.setAttribute(Constants.MODIFIEDBYLITERAL, userEmail);
                            cardRuleSubscriptionRow.setAttribute(Constants.CADRDKSID_LITERAL, Constants.ALL_LITERAL);
                            cardRuleSubscriptionRow.setAttribute(Constants.ASSOCIATIONTYPELITERAL, userAssociationType);
                            cardRuleSubscription.insertRow(cardRuleSubscriptionRow);
                            Row prtCardFuelCapacityRow = prtCardFuelCapacity.createRow();
                            prtCardFuelCapacityRow.setAttribute("CountryCode", countryCode);
                            prtCardFuelCapacityRow.setAttribute(Constants.SUBSCR_ID_LITERAL, response.getSubscriptionID().toString().trim());
                            prtCardFuelCapacityRow.setAttribute(Constants.RULE_ID_LITERAL, "2");
                            if (getBindings().getLtrPerDayRadio().getValue() != null &&
                                getBindings().getLtrPerDayRadio().getValue().toString().equalsIgnoreCase(Constants.TRUE_LITERAL)) {
                                prtCardFuelCapacityRow.setAttribute(Constants.FUEL_PER_DAY_LITERAL,
                                                                    getBindings().getFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ", ""));
                            }

                            if (getBindings().getLtrPerWeekRadio().getValue() != null &&
                                getBindings().getLtrPerWeekRadio().getValue().toString().equalsIgnoreCase(Constants.TRUE_LITERAL)) {
                                prtCardFuelCapacityRow.setAttribute(Constants.FUEL_PER_WEEK_LITERAL,
                                                                    getBindings().getFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ", ""));
                            }

                            if (getBindings().getLtrPerMonthRadio().getValue() != null &&
                                getBindings().getLtrPerMonthRadio().getValue().toString().equalsIgnoreCase(Constants.TRUE_LITERAL)) {
                                prtCardFuelCapacityRow.setAttribute(Constants.FUEL_PER_MONTH_LITERAL,
                                                                    getBindings().getFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ", ""));
                            }

                            prtCardFuelCapacityRow.setAttribute(Constants.MODIFIEDBYLITERAL, userEmail);
                        }
                    }
                    operationBinding = localBinding.getOperationBinding(Constants.COMMIT_LITERAL);
                    operationBinding.execute();
                }
            }
            getBindings().getAlert2ValidData().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAlert2ValidData());
            getBindings().getSuccessAlert2().setRendered(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert2());
            getBindings().getFuelCapacityAlert2().setDisabled(true);
            getBindings().getOkButtonAlert2().setRendered(false);
            getBindings().getCancelButtonAlert2().setRendered(false);
            getBindings().getLtrPerMonthRadio().setDisabled(true);
            getBindings().getLtrPerWeekRadio().setDisabled(true);
            getBindings().getLtrPerDayRadio().setDisabled(true);
            getBindings().getCardDropdownAlert2().setDisabled(true);
            getBindings().getCardGroupDowndownAlert2().setDisabled(true);
            getBindings().getAccountDropdwonAlert2().setDisabled(true);
            getBindings().getPartnerDropdownAlert2().setDisabled(true);
            getBindings().getCloseButtonAlert2().setRendered(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFuelCapacityAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getOkButtonAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCancelButtonAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLtrPerMonthRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLtrPerWeekRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLtrPerDayRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardDropdownAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupDowndownAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccountDropdwonAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartnerDropdownAlert2());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCloseButtonAlert2());
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside readPartner method of Alerts");
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setInitailAccountIdVAlue(List<String> initailAccountIdVAlue) {
        this.initailAccountIdVAlue = initailAccountIdVAlue;
    }

    public List<String> getInitailAccountIdVAlue() {
        return initailAccountIdVAlue;
    }

    public void setInitialCardGroupValue(List<String> initialCardGroupValue) {
        this.initialCardGroupValue = initialCardGroupValue;
    }

    public List<String> getInitialCardGroupValue() {
        return initialCardGroupValue;
    }

    public void setInitialCardValue(List<String> initialCardValue) {
        this.initialCardValue = initialCardValue;
    }

    public List<String> getInitialCardValue() {
        return initialCardValue;
    }

    public void viewSubscribedAlerts(ActionEvent actionEvent) {
        resetTableFilter();
        if(getBindings().getViewAlertDropdown().getValue() != null){
            displayErrorComponent(getBindings().getViewAlertDropdown(), false);
            if(getBindings().getViewAlertCardGroupDropdown().getValue() != null){
                displayErrorComponent(getBindings().getViewAlertCardGroupDropdown(), false);
                if(getBindings().getViewAlertsAccountDropdown().getValue() != null){
                    displayErrorComponent(getBindings().getViewAlertsAccountDropdown(), false);
                    if(getBindings().getViewAlertsPartnerDropdown().getValue() != null){
                        displayErrorComponent(getBindings().getViewAlertsPartnerDropdown(), false);
                        viewSubscribedAlertsUI(getBindings().getViewAlertsPartnerDropdown().getValue().toString().trim(),
                                               getBindings().getViewAlertsAccountDropdown().getValue().toString().trim(),
                                               getBindings().getViewAlertCardGroupDropdown().getValue().toString().trim(),
                                               getBindings().getViewAlertDropdown().getValue().toString().trim());
                    }else{
                        showErrorMessage(Constants.ENGAGENOPARTNER_LITERAL);
                        displayErrorComponent(getBindings().getViewAlertsPartnerDropdown(), true);
                    }
                }else{
                    showErrorMessage("ENGAGE_NO_ACCOUNT");
                    displayErrorComponent(getBindings().getViewAlertsAccountDropdown(), true);
                }
            } else {
                showErrorMessage(Constants.ENGAGENOPARTNER_LITERAL);
                displayErrorComponent(getBindings().getViewAlertCardGroupDropdown(), true);
            }
        } else{
            showErrorMessage(Constants.ENGAGENOPARTNER_LITERAL);
            displayErrorComponent(getBindings().getViewAlertDropdown(), true);
        }
    }

    public void viewSubscribedAlertsUI(String partnerPV, String accountPV, String cgPV, String cardPV) {
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside viewSubscribedAlerts method of Alerts");
        String partnerArg = partnerPV;
        String accountArg =accountPV;
        String cgArg = cgPV;
        String cardArg = cardPV;
        ViewObject prtCardRuleSubscriptionVO = ADFUtils.getViewObject(PRTCARDRULESUBSCRIPTIONRVO1ITERATORLITRERAL);
        if (selectionPanel) {
            if (prtCardRuleSubscriptionVO != null) {
                String cardListString = "";
                for (String s : initialCard2Value) {
                    cardListString += s + ",";
                }
                cardListString = cardListString.substring(0, cardListString.length() - 1);
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "converted cardListString arraylist " + cardListString);
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "converted cardListString arraylist " +
                                populateStringValues(cardArg));
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "length1 " + cardListString.length());
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "lenght2 " +
                                (populateStringValues(cardArg).replaceAll(" ", "")).length());
                    if (cardListString.length() ==
                        (populateStringValues(cardArg).replaceAll(" ", "")).length()) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "card dropdown is unchanged");
                        readCardGroupLevel(partnerArg, accountArg, cgArg);
                    } else {
                        readCardlevel();
                    }
            }
        } else if (cardsSelectionPanel) {
            if (getBindings().getSearchStringInputtext().getValue() != null) {
                displayErrorComponent(getBindings().getSearchStringInputtext(), false);
                if (suggestedCardNumberList.contains(getBindings().getSearchStringInputtext().getValue().toString().trim())) {
                    searchCorrespondingDetalis();
                    if (prtCardRuleSubscriptionVO != null) {
                        removeWhereClause(prtCardRuleSubscriptionVO);
                        prtCardRuleSubscriptionVO.setNamedWhereClauseParam(Constants.PID_LITERAL, passingPartner);
                        prtCardRuleSubscriptionVO.setNamedWhereClauseParam(Constants.USERIDLITERAL, userEmail);
                        prtCardRuleSubscriptionVO.setNamedWhereClauseParam(Constants.COUNTRYCODE_LITERAL, countryCode);
                        cardQuery = "(INSTR(:card,PARTNER_ID||ACCOUNT_ID||CARDGROUP_MAIN||CARDGROUP_SUB||CARDGROUP_SEQ||CARD_KSID)<>0 OR (CARD_KSID = 'ALL')";
                        String cardValuesList = passingPartner + passingAccount + passingCardgrpMain + passingCardgrpSub + passingCardgrpSeq + passingCardKsId;
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "cardValuesList " + cardValuesList);
                        prtCardRuleSubscriptionVO.defineNamedWhereClauseParam(Constants.CARDLITERAL, cardValuesList, null);
                        accountQuery = "INSTR(:account,PARTNER_ID||ACCOUNT_ID)<>0 OR (ACCOUNT_ID = 'ALL')) ";
                        String accountValueList = passingPartner + passingAccount;
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "accountValueList " + accountValueList);
                        prtCardRuleSubscriptionVO.defineNamedWhereClauseParam(Constants.ACCOUNT_LITERAL, accountValueList, null);
                        cardGroupQuery =
                                "(INSTR(:cardGroup,PARTNER_ID||ACCOUNT_ID||CARDGROUP_MAIN||CARDGROUP_SUB||CARDGROUP_SEQ)<>0 OR ((CARDGROUP_MAIN = 'ALL') AND (CARDGROUP_SUB = 'ALL') AND (CARDGROUP_SEQ = 'ALL'))) ";
                        String cardgrpValueList = passingPartner + passingAccount + passingCardgrpMain + passingCardgrpSub + passingCardgrpSeq;
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "cardgrpValueList " + cardgrpValueList);
                        prtCardRuleSubscriptionVO.defineNamedWhereClauseParam(Constants.CARDGROUPLITERAL, cardgrpValueList, null);
                        prtCardRuleSubscriptionVO.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + cardQuery);
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Query for card search : " + prtCardRuleSubscriptionVO.getQuery());
                        prtCardRuleSubscriptionVO.executeQuery();
                        session.setAttribute(Constants.ALERTS_ACCOUNT_QUERY_LITERAL, accountQuery);
                        session.setAttribute(Constants.ALERTS_CARDGROUP_QUERY_LITERAL, cardGroupQuery);
                        session.setAttribute(Constants.ALERTS_CARD_QUERY_LITERAL, cardQuery);
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                                    "Estimated row count for Card Rule Subscription Query in case of card selection :" +
                                    prtCardRuleSubscriptionVO.getEstimatedRowCount());
                        isTableVisible = true;
                    }
                }
            } else {
                showErrorMessage(Constants.ENGAGENOPARTNER_LITERAL);
                displayErrorComponent(getBindings().getSearchStringInputtext(), true);
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside viewSubscribedAlerts method of Alerts");
    }

    public void readCardlevel() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside readCardlevel method of Alerts");
        ViewObject prtCardRuleSubscriptionVO = ADFUtils.getViewObject(PRTCARDRULESUBSCRIPTIONRVO1ITERATORLITRERAL);
        String[] values = populateStringValues(getBindings().getViewAlertDropdown().getValue().toString()).split(",");
        String[] partner = new String[values.length];
        String[] account = new String[values.length];
        String[] cardgrp = new String[values.length];
        String[] card = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            partner[i] = values[i].trim().substring(0, Constants.EIGHT);
            account[i] = values[i].trim().substring(Constants.EIGHT, Constants.EIGHTEEN);
            cardgrp[i] = values[i].trim().substring(Constants.EIGHTEEN, Constants.TWENTYNINE);
            card[i] = values[i].trim().substring(Constants.TWENTYNINE);
        }

        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "partner values to be passed are ");
        for (int j = 0; j < partner.length; j++) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + partner[j]);
        }

        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "account values to be passed are ");
        for (int j = 0; j < account.length; j++) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + account[j]);
        }

        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "cardgrp values to be passed are ");
        for (int j = 0; j < cardgrp.length; j++) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + cardgrp[j]);
        }

        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "card values to be passed are ");
        for (int j = 0; j < card.length; j++) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + card[j]);
        }

        prtCardRuleSubscriptionVO.setNamedWhereClauseParam(Constants.PID_LITERAL, mergeArraytoCommaSeperatedSting(partner));
        prtCardRuleSubscriptionVO.setNamedWhereClauseParam(Constants.USERIDLITERAL, userEmail);
        prtCardRuleSubscriptionVO.setNamedWhereClauseParam(Constants.COUNTRYCODE_LITERAL, countryCode);
        removeWhereClause(prtCardRuleSubscriptionVO);
        String accountPassingValue = mergeArraytoCommaSeperatedSting(account);
        String cardGroupPassingValue = mergeArraytoCommaSeperatedSting(cardgrp);
        String cardPassingValue = mergeArraytoCommaSeperatedSting(card);
        createAccountQuery();
        createCardGroupQuery();
        createCardQuery();
        prtCardRuleSubscriptionVO.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + cardQuery);
        addAccountQuery(prtCardRuleSubscriptionVO, accountPassingValue);
        addCardGroupQuery(prtCardRuleSubscriptionVO, cardGroupPassingValue);
        addCardQuery(prtCardRuleSubscriptionVO, cardPassingValue);
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Query: " + prtCardRuleSubscriptionVO.getQuery());
        prtCardRuleSubscriptionVO.executeQuery();
        session.setAttribute(Constants.ALERTS_ACCOUNT_QUERY_LITERAL, accountQuery);
        session.setAttribute("alerts_map_Account_List", mapAccountListValue);
        session.setAttribute(Constants.ALERTS_CARDGROUP_QUERY_LITERAL, cardGroupQuery);
        session.setAttribute("alerts_map_CardGroup_List", mapCardGroupListValue);
        session.setAttribute(Constants.ALERTS_CARD_QUERY_LITERAL, cardQuery);
        session.setAttribute("alerts_map_Card_List", mapCardListValue);
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Queries are saved in session");
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Estimated row count for Card Rule Subscription Query:" +
                    prtCardRuleSubscriptionVO.getEstimatedRowCount());
        isTableVisible = true;
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside readCardlevel method of Alerts");
    }

    public void readCardGroupLevel(String partnerPV, String accountPV, String cgPV) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside readCardGroupLevel method of Alerts");
        String partnerArg = partnerPV;
        String accountArg = accountPV;
        String cgArg = cgPV;
        String cardGroupListString = "";
        for (String s : initialCardGroup2Value) {
            cardGroupListString += s + ",";
        }
        cardGroupListString = cardGroupListString.substring(0, cardGroupListString.length() - 1);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "converted cardGroupListString arraylist " + cardGroupListString);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "converted cardGroupListString arraylist " +
                        populateStringValues(cgArg));
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "length1 " + cardGroupListString.length());
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "lenght2 " +
                        (populateStringValues(cgArg).replaceAll(" ", "")).length());

            if (cardGroupListString.length() ==
                (populateStringValues(cgArg).replaceAll(" ", "")).length()) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "cardGroup dropdown is unchanged");
                readAccountlevel(partnerArg, accountArg);
            } else {
                String[] values = populateStringValues(getBindings().getViewAlertCardGroupDropdown().getValue().toString()).split(",");
                String[] partner = new String[values.length];
                String[] account = new String[values.length];
                String[] cardgrp = new String[values.length];

                for (int i = 0; i < values.length; i++) {
                    partner[i] = values[i].trim().substring(0, Constants.EIGHT);
                    account[i] = values[i].trim().substring(Constants.EIGHT, Constants.EIGHTEEN);
                    cardgrp[i] = values[i].trim().substring(Constants.EIGHTEEN);
                }

                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "partner values to be passed are ");
                for (int j = 0; j < partner.length; j++) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + partner[j]);
                }

                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "account values to be passed are ");
                for (int j = 0; j < account.length; j++) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + account[j]);
                }

                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "cardgrp values to be passed are ");
                for (int j = 0; j < cardgrp.length; j++) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + cardgrp[j]);
                }

                ViewObject prtCardRuleSubscriptionVO = ADFUtils.getViewObject(PRTCARDRULESUBSCRIPTIONRVO1ITERATORLITRERAL);
                removeWhereClause(prtCardRuleSubscriptionVO);
                prtCardRuleSubscriptionVO.setNamedWhereClauseParam(Constants.PID_LITERAL, mergeArraytoCommaSeperatedSting(partner));
                prtCardRuleSubscriptionVO.setNamedWhereClauseParam(Constants.USERIDLITERAL, userEmail);
                prtCardRuleSubscriptionVO.setNamedWhereClauseParam(Constants.COUNTRYCODE_LITERAL, countryCode);
                createAccountQuery();
                createCardGroupQuery();
                String accountPassingValue = mergeArraytoCommaSeperatedSting(account);
                String cardGroupPassingValue = mergeArraytoCommaSeperatedSting(cardgrp);
                addAccountQuery(prtCardRuleSubscriptionVO, accountPassingValue);
                addCardGroupQuery(prtCardRuleSubscriptionVO, cardGroupPassingValue);
                prtCardRuleSubscriptionVO.setWhereClause(accountQuery + "AND " + cardGroupQuery);
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Query: " + prtCardRuleSubscriptionVO.getQuery());
                prtCardRuleSubscriptionVO.executeQuery();
                session.setAttribute(Constants.ALERTS_ACCOUNT_QUERY_LITERAL, accountQuery);
                session.setAttribute("alerts_map_Account_List", mapAccountListValue);
                session.setAttribute(Constants.ALERTS_CARDGROUP_QUERY_LITERAL, cardGroupQuery);
                session.setAttribute("alerts_map_CardGroup_List", mapCardGroupListValue);
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Estimated row count for Card Group Rule Subscription Query:" +
                            prtCardRuleSubscriptionVO.getEstimatedRowCount());
                isTableVisible = true;
            }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside readCardGroupLevel method of Alerts");
    }

    public void readAccountlevel(String partnerPV, String accountPV) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside readAccountlevel method of Alerts");
        String partnerArg = partnerPV;
        String accountArg =accountPV;
        String accountListString = "";
        for (String s : initailAccountId2VAlue) {
            accountListString += s + ",";
        }
        accountListString = accountListString.substring(0, accountListString.length() - 1);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "converted accListString arraylist " + accountListString);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "converted accListString arraylist " +
                        (populateStringValues(accountArg).replaceAll(" ", "")));
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "length1 " + accountListString.length());
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "lenght2 " +
                        (populateStringValues(accountArg).replaceAll(" ", "")).length());

            if (accountListString.length() ==
                (populateStringValues(accountArg).replaceAll(" ", "")).length()) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "account dropdown is unchanged");
                readPartnerLevel(partnerArg);
            } else {
                String[] values = populateStringValues(getBindings().getViewAlertsAccountDropdown().getValue().toString()).split(",");
                String[] partner = new String[values.length];
                String[] account = new String[values.length];

                for (int i = 0; i < values.length; i++) {
                    partner[i] = values[i].trim().substring(0, Constants.EIGHT);
                    account[i] = values[i].trim().substring(Constants.EIGHT);
                }

                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "partner values to be passed are ");
                for (int j = 0; j < partner.length; j++) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + partner[j]);
                }

                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "account values to be passed are ");
                for (int j = 0; j < account.length; j++) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + account[j]);
                }
                ViewObject prtCardRuleSubscriptionVO = ADFUtils.getViewObject(PRTCARDRULESUBSCRIPTIONRVO1ITERATORLITRERAL);
                removeWhereClause(prtCardRuleSubscriptionVO);
                prtCardRuleSubscriptionVO.setNamedWhereClauseParam(Constants.PID_LITERAL, mergeArraytoCommaSeperatedSting(partner));
                prtCardRuleSubscriptionVO.setNamedWhereClauseParam(Constants.USERIDLITERAL, userEmail);
                prtCardRuleSubscriptionVO.setNamedWhereClauseParam(Constants.COUNTRYCODE_LITERAL, countryCode);
                createAccountQuery();
                prtCardRuleSubscriptionVO.setWhereClause(accountQuery);
                String passingValues = this.mergeArraytoCommaSeperatedSting(account);
                addAccountQuery(prtCardRuleSubscriptionVO, passingValues);
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Query: " + prtCardRuleSubscriptionVO.getQuery());
                prtCardRuleSubscriptionVO.executeQuery();
                session.setAttribute(Constants.ALERTS_ACCOUNT_QUERY_LITERAL, accountQuery);
                session.setAttribute("alerts_map_Account_List", mapAccountListValue);
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Estimated row count for Account Subscription Query:" +
                            prtCardRuleSubscriptionVO.getEstimatedRowCount());
                isTableVisible = true;
            }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside readAccountlevel method of Alerts");
    }

    private String mergeArraytoCommaSeperatedSting(String[] data) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside mergeArraytoCommaSeperatedSting method of Alerts");
        String commaSeperatedString = "";
        for (int i = 0; i < data.length; i++) {
            commaSeperatedString = data[i];
            if (i < (data.length - 1)) {
                commaSeperatedString = commaSeperatedString + ",";
            }
        }
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "List of Data:" + commaSeperatedString);
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside mergeArraytoCommaSeperatedSting method of Alerts");
        return commaSeperatedString;
    }

    public void readPartnerLevel(String partnerPV) {
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Inside readPartnerLevel method of Alerts");
        String partnerArg = partnerPV;
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "reading at partner level");
            String[] values = populateStringValues(partnerArg).split(",");
            String[] partner = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                partner[i] = values[i].trim().substring(0, Constants.EIGHT);
            }
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "partner values to be passed are ");
            for (int j = 0; j < partner.length; j++) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + partner[j]);
            }
            ViewObject prtCardRuleSubscriptionVO = ADFUtils.getViewObject(PRTCARDRULESUBSCRIPTIONRVO1ITERATORLITRERAL);
            removeWhereClause(prtCardRuleSubscriptionVO);
            prtCardRuleSubscriptionVO.setNamedWhereClauseParam(Constants.PID_LITERAL,
                                                               populateStringValues(partnerArg));
            prtCardRuleSubscriptionVO.setNamedWhereClauseParam(Constants.USERIDLITERAL, userEmail);
            prtCardRuleSubscriptionVO.setNamedWhereClauseParam(Constants.COUNTRYCODE_LITERAL, countryCode);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Query: " + prtCardRuleSubscriptionVO.getQuery());
            prtCardRuleSubscriptionVO.executeQuery();
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Estimated row count for Card Rule Subscription Query:" +
                        prtCardRuleSubscriptionVO.getEstimatedRowCount());
            isTableVisible = true;
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Outside readPartnerLevel method of Alerts");
    }

    public void searchCorrespondingDetalis() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside searchCorrespondingDetalis method of Alerts");
        for (int i = 0; i < partnerInfoList.size(); i++) {
            if (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {
                passingPartner = partnerInfoList.get(i).getPartnerValue();
                for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
                    passingAccount = partnerInfoList.get(i).getAccountList().get(j).getAccountNumber();
                    for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
                        passingCardgrpMain = partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupMainType();
                        passingCardgrpSub = partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupSubType();
                        passingCardgrpSeq = partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupSeq();
                        for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++) {
                            if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() != null &&
                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().equalsIgnoreCase(getBindings().getSearchStringInputtext().getValue().toString().trim())) {
                                passingCardKsId = partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID();
                                return;
                            }
                        }
                    }
                }
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside searchCorrespondingDetalis method of Alerts");
    }

    public void viewConfiguredAlertPopup(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside viewConfiguredAlertPopup method of Alerts");
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.SUBSCRID_KEY_LITERAL) != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                        AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.SUBSCRID_KEY_LITERAL).toString().trim());
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.RULEID_KEY_LITERAL) != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                        AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.RULEID_KEY_LITERAL).toString().trim());
            if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.RULEID_KEY_LITERAL).toString().trim().equalsIgnoreCase("1")) {
                configuredPartner = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.PARTNERID_KEY_LITERAL).toString().trim();
                configureDefaultTimings(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.SUBSCRID_KEY_LITERAL).toString().trim(),
                                        countryCode);
                getBindings().getSuccessAlert4().setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert4());
                getBindings().getInvalidInputMsg().setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvalidInputMsg());
                getBindings().getMandatoryMsg4().setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg4());
                okVisibleForBusinessHr = false;
                editVisibleForBusinessHr = true;
                getBindings().getConfigureFromTimingsHh().setReadOnly(true);
                getBindings().getConfigureFromTimingsMm().setReadOnly(true);
                getBindings().getConfigureToTimingsHh().setReadOnly(true);
                getBindings().getConfigureToTimingsMm().setReadOnly(true);
                RichPopup.PopupHints ps = new RichPopup.PopupHints();
                getBindings().getConfigureAlert1Popup().show(ps);
            } else if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.RULEID_KEY_LITERAL).toString().trim().equalsIgnoreCase("2")) {
                configurePartnerIdList = new ArrayList<SelectItem>();
                configurePartnerIdValue2 = new ArrayList<String>();
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(getPartnerName(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.PARTNERID_KEY_LITERAL).toString().trim()));
                selectItem.setValue(getPartnerName(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.PARTNERID_KEY_LITERAL).toString().trim()));
                configurePartnerIdValue2.add(getPartnerName(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.PARTNERID_KEY_LITERAL).toString().trim()));
                configurePartnerIdList.add(selectItem);
                configureAccountIdList = new ArrayList<SelectItem>();
                configureAccountIdValue = new ArrayList<String>();
                selectItem = new SelectItem();
                selectItem.setLabel(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ACCOUNTID_KEY_LITERAL).toString().trim());
                selectItem.setValue(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ACCOUNTID_KEY_LITERAL).toString().trim());
                configureAccountIdValue.add(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ACCOUNTID_KEY_LITERAL).toString().trim());
                configureAccountIdList.add(selectItem);
                configureCardGroupList = new ArrayList<SelectItem>();
                configureCardGroupValue = new ArrayList<String>();
                selectItem = new SelectItem();
                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.CARDGROUP_KEY_LITERAL).toString().trim().equals("ALLALLALL")) {
                    selectItem.setLabel(Constants.ALL_LITERAL);
                    selectItem.setValue(Constants.ALL_LITERAL);
                    configureCardGroupValue.add(Constants.ALL_LITERAL);
                    configureCardGroupList.add(selectItem);
                } else {
                    selectItem.setLabel(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.CARDGROUP_KEY_LITERAL).toString().trim());
                    selectItem.setValue(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.CARDGROUP_KEY_LITERAL).toString().trim());
                    configureCardGroupValue.add(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.CARDGROUP_KEY_LITERAL).toString().trim());
                    configureCardGroupList.add(selectItem);
                }

                configureCardNumberList = new ArrayList<SelectItem>();
                configureCardNumberValue = new ArrayList<String>();
                selectItem = new SelectItem();

                selectItem.setLabel(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.CARDKSID_KEY_LITERAL).toString().trim());
                selectItem.setValue(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.CARDKSID_KEY_LITERAL).toString().trim());
                configureCardNumberValue.add(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.CARDKSID_KEY_LITERAL).toString().trim());
                configureCardNumberList.add(selectItem);


                fetchFuelCapacity(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.SUBSCRID_KEY_LITERAL).toString().trim(), countryCode);
                okVisibleForQuality = false;
                editVisibleForQuality = true;
                getBindings().getSuccessAlert3().setRendered(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert3());
                getBindings().getInvalidInputMsg3().setRendered(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvalidInputMsg3());
                getBindings().getMandatoryMsg3().setRendered(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg3());
                getBindings().getConfigureFuelCapacityAlert2().setDisabled(true);
                getBindings().getConfigureLtrPerDayRadio().setDisabled(true);
                getBindings().getConfigureLtrPerWeekRadio().setDisabled(true);
                getBindings().getConfigureLtrPerMonthRadio().setDisabled(true);
                displayErrorComponent(getBindings().getConfigureFuelCapacityAlert2(), false);
                RichPopup.PopupHints ps = new RichPopup.PopupHints();
                getBindings().getConfigureAlert2Popup().show(ps);


            }
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.PARTNERID_KEY_LITERAL) != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                        AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.PARTNERID_KEY_LITERAL).toString().trim());
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ACCOUNTID_KEY_LITERAL) != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                        AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ACCOUNTID_KEY_LITERAL).toString().trim());
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.CARDGROUP_KEY_LITERAL) != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                        AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.CARDGROUP_KEY_LITERAL).toString().trim());
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.CARDKSID_KEY_LITERAL) != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                        AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.CARDKSID_KEY_LITERAL).toString().trim());
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside viewConfiguredAlertPopup method of Alerts");
    }


    public void fetchFuelCapacity(String subscriptionId, String country) {
        DCBindingContainer localBinding;

        localBinding = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();


        DCIteratorBinding iter1;
        if (localBinding != null && localBinding.findIteratorBinding("PrtCardFuelCapacityRVO1Iterator") != null) {
            iter1 = localBinding.findIteratorBinding("PrtCardFuelCapacityRVO1Iterator");


            ViewObject fuelCapacityVO = iter1.getViewObject();

            fuelCapacityVO.setNamedWhereClauseParam("subID", subscriptionId);

            fuelCapacityVO.setNamedWhereClauseParam(Constants.COUNTRYCODE_LITERAL, country);

            fuelCapacityVO.executeQuery();
            getBindings().getConfigureLtrPerDayRadio().setSelected(false);
            getBindings().getConfigureLtrPerWeekRadio().setSelected(false);
            getBindings().getConfigureLtrPerMonthRadio().setSelected(false);

            if (fuelCapacityVO.getEstimatedRowCount() != 0) {
                while (fuelCapacityVO.hasNext()) {


                    PrtCardFuelCapacityRVORowImpl currRow = (PrtCardFuelCapacityRVORowImpl)fuelCapacityVO.next();

                    if (currRow != null) {
                        if (currRow.getFuelPerDay() != null) {
                            getBindings().getConfigureLtrPerDayRadio().setSelected(true);
                            getBindings().getConfigureFuelCapacityAlert2().setValue(currRow.getFuelPerDay());
                        } else if (currRow.getFuelPerWeek() != null) {
                            getBindings().getConfigureLtrPerWeekRadio().setSelected(true);
                            getBindings().getConfigureFuelCapacityAlert2().setValue(currRow.getFuelPerWeek());
                        } else if (currRow.getFuelPerMonth() != null) {
                            getBindings().getConfigureLtrPerMonthRadio().setSelected(true);
                            getBindings().getConfigureFuelCapacityAlert2().setValue(currRow.getFuelPerMonth());
                        }

                    }


                }
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getConfigureLtrPerDayRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getConfigureLtrPerWeekRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getConfigureLtrPerMonthRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getConfigureFuelCapacityAlert2());
        }
    }

    public void setConfigurePartnerAlert1List(List<SelectItem> configurePartnerAlert1List) {
        this.configurePartnerAlert1List = configurePartnerAlert1List;
    }

    public List<SelectItem> getConfigurePartnerAlert1List() {
        return configurePartnerAlert1List;
    }

    public void setConfigureFuelTimings(List<FuelTimings> configureFuelTimings) {
        this.configureFuelTimings = configureFuelTimings;
    }

    public List<FuelTimings> getConfigureFuelTimings() {
        return configureFuelTimings;
    }

    public void editAlert1ConfigureTimings(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside editAlert1ConfigureTimings method of Alerts");
        okVisibleForBusinessHr = true;
        editVisibleForBusinessHr = false;
        getBindings().getConfigureFromTimingsHh().setReadOnly(false);
        getBindings().getConfigureFromTimingsMm().setReadOnly(false);
        getBindings().getConfigureToTimingsHh().setReadOnly(false);
        getBindings().getConfigureToTimingsMm().setReadOnly(false);
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside editAlert1ConfigureTimings method of Alerts");
    }

    public void setBusinessHoursConfigureAlert(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside setBusinessHoursConfigureAlert method of Alerts");
        if (validateinput(false)) {
            BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding fuelCaoItr = (DCIteratorBinding)bc.get("PrtCardRuleBusinessHoursVO1Iterator");
            ViewObject vo = ADFUtils.getViewObject("PrtCardRuleBusinessHoursVO1Iterator");
            vo.setWhereClause("subscr_id =: subscrId");
            if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.SUBSCRID_KEY_LITERAL) != null) {
                String subscrId = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.SUBSCRID_KEY_LITERAL).toString().trim();
                vo.defineNamedWhereClauseParam(Constants.SUBSCRIDLITERAL, subscrId, null);
            }
            vo.executeQuery();
            RichTable rt = getBindings().getConfigureAlert1Table();
            Object o;
            FuelTimings editFuelTimings;
            Row businessHr;
            for (int i = 0; i < Constants.SEVEN; i++) {
                o = rt.getRowData(i);
                editFuelTimings = (FuelTimings)o;
                businessHr = vo.next();
                businessHr.setAttribute("Day", weekdays.get(i));
                businessHr.setAttribute("BusiStartFrom", editFuelTimings.getFromHh().toString().trim() + ":" + editFuelTimings.getFromMm().toString().trim());
                businessHr.setAttribute("BusiStartTo", editFuelTimings.getToHh().toString().trim() + ":" + editFuelTimings.getToMm().toString().trim());
                businessHr.setAttribute(Constants.MODIFIEDBYLITERAL, userEmail);
            }
            if (bc != null) {
                OperationBinding operationBinding = bc.getOperationBinding("subscribeAlerts");
                if (operationBinding != null) {
                    operationBinding = bc.getOperationBinding(Constants.COMMIT_LITERAL);
                    operationBinding.execute();
                    getBindings().getSuccessAlert4().setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert4());
                    getBindings().getInvalidInputMsg().setVisible(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvalidInputMsg());
                    getBindings().getMandatoryMsg4().setVisible(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg4());
                    okVisibleForBusinessHr = false;
                    editVisibleForBusinessHr = true;
                    getBindings().getConfigureFromTimingsHh().setReadOnly(true);
                    getBindings().getConfigureFromTimingsMm().setReadOnly(true);
                    getBindings().getConfigureToTimingsHh().setReadOnly(true);
                    getBindings().getConfigureToTimingsMm().setReadOnly(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getConfigureFromTimingsHh());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getConfigureToTimingsHh());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getConfigureToTimingsMm());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getConfigureFromTimingsMm());
                }
            }
            vo.removeNamedWhereClauseParam(Constants.SUBSCRIDLITERAL);
            vo.setWhereClause("");
            vo.executeQuery();
        } else {
            getBindings().getSuccessAlert4().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert4());
                   }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside setBusinessHoursConfigureAlert method of Alerts");
    }

    public void claoseConfigureAlert1Popup(ActionEvent actionEvent) {
        getBindings().getConfigureAlert1Popup().hide();
    }

    public void closeConfigureAlert1Popup(ActionEvent actionEvent) {
        getBindings().getConfigureAlert1Popup().hide();
    }

    public void setConfigurePartnerIdValue(String configurePartnerIdValue) {
        this.configurePartnerIdValue = configurePartnerIdValue;
    }

    public String getConfigurePartnerIdValue() {
        if (configuredPartner != null) {
            configurePartnerIdValue = configuredPartner;
        }
        return configurePartnerIdValue;
    }

    public void setConfigurePartnerIdValue2(List<String> configurePartnerIdValue2) {
        this.configurePartnerIdValue2 = configurePartnerIdValue2;
    }

    public List<String> getConfigurePartnerIdValue2() {
        return configurePartnerIdValue2;
    }

    public void setConfigurePartnerIdList(List<SelectItem> configurePartnerIdList) {
        this.configurePartnerIdList = configurePartnerIdList;
    }

    public List<SelectItem> getConfigurePartnerIdList() {
        return configurePartnerIdList;
    }

    public void setConfigureAccountIdList(List<SelectItem> configureAccountIdList) {
        this.configureAccountIdList = configureAccountIdList;
    }

    public List<SelectItem> getConfigureAccountIdList() {
        return configureAccountIdList;
    }

    public void setConfigureAccountIdValue(List<String> configureAccountIdValue) {
        this.configureAccountIdValue = configureAccountIdValue;
    }

    public List<String> getConfigureAccountIdValue() {
        return configureAccountIdValue;
    }

    public void setConfigureCardGroupList(List<SelectItem> configureCardGroupList) {
        this.configureCardGroupList = configureCardGroupList;
    }

    public List<SelectItem> getConfigureCardGroupList() {
        return configureCardGroupList;
    }

    public void setConfigureCardGroupValue(List<String> configureCardGroupValue) {
        this.configureCardGroupValue = configureCardGroupValue;
    }

    public List<String> getConfigureCardGroupValue() {
        return configureCardGroupValue;
    }

    public void setConfigureCardNumberList(List<SelectItem> configureCardNumberList) {
        this.configureCardNumberList = configureCardNumberList;
    }

    public List<SelectItem> getConfigureCardNumberList() {
        return configureCardNumberList;
    }

    public void setConfigureCardNumberValue(List<String> configureCardNumberValue) {
        this.configureCardNumberValue = configureCardNumberValue;
    }

    public List<String> getConfigureCardNumberValue() {
        return configureCardNumberValue;
    }

    public void setConfigureFuelCapacityAlert(ActionEvent actionEvent) {
        if (getBindings().getConfigureFuelCapacityAlert2() != null) {
            if (!getBindings().getConfigureFuelCapacityAlert2().getValue().equals("")) {
                String regex = "\\d+";
                if (getBindings().getConfigureFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ", "").matches(regex)) {
                    BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
                    DCIteratorBinding fuelCaoItr = (DCIteratorBinding)bc.get(PRTCARDFUELCAPACITYVO1ITERATORLITRERAL);
                    ViewObject vo = ADFUtils.getViewObject(PRTCARDFUELCAPACITYVO1ITERATORLITRERAL);
                    vo.setWhereClause("subscr_id =: subscrId");
                    if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.SUBSCRID_KEY_LITERAL) != null) {
                        String subscrId = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.SUBSCRID_KEY_LITERAL).toString().trim();
                        vo.defineNamedWhereClauseParam(Constants.SUBSCRIDLITERAL, subscrId, null);
                    }
                    vo.executeQuery();
                    Row fuelCapRow = fuelCaoItr.getCurrentRow();
                    if (fuelCapRow != null) {
                        if (getBindings().getConfigureLtrPerDayRadio().getValue() != null &&
                            getBindings().getConfigureLtrPerDayRadio().getValue().toString().equalsIgnoreCase(Constants.TRUE_LITERAL)) {
                            fuelCapRow.setAttribute(Constants.FUEL_PER_DAY_LITERAL,
                                                    getBindings().getConfigureFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ", ""));
                        } else {
                            fuelCapRow.setAttribute(Constants.FUEL_PER_DAY_LITERAL, "");
                        }
                        if (getBindings().getConfigureLtrPerWeekRadio().getValue() != null &&
                            getBindings().getConfigureLtrPerWeekRadio().getValue().toString().equalsIgnoreCase(Constants.TRUE_LITERAL)) {
                            fuelCapRow.setAttribute(Constants.FUEL_PER_WEEK_LITERAL,
                                                    getBindings().getConfigureFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ", ""));
                        } else {
                            fuelCapRow.setAttribute(Constants.FUEL_PER_WEEK_LITERAL, "");
                        }
                        if (getBindings().getConfigureLtrPerMonthRadio().getValue() != null &&
                            getBindings().getConfigureLtrPerMonthRadio().getValue().toString().equalsIgnoreCase(Constants.TRUE_LITERAL)) {
                            fuelCapRow.setAttribute(Constants.FUEL_PER_MONTH_LITERAL,
                                                    getBindings().getConfigureFuelCapacityAlert2().getValue().toString().trim().replaceAll(" ", ""));
                        } else {
                            fuelCapRow.setAttribute(Constants.FUEL_PER_MONTH_LITERAL, "");
                        }
                        fuelCapRow.setAttribute(Constants.MODIFIEDBYLITERAL, userEmail);
                    }
                    if (bc != null) {
                        OperationBinding operationBinding = bc.getOperationBinding("subscribeAlerts");
                        if (operationBinding != null) {
                            operationBinding = bc.getOperationBinding(Constants.COMMIT_LITERAL);
                            operationBinding.execute();
                            getBindings().getSuccessAlert3().setRendered(true);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSuccessAlert3());
                            getBindings().getInvalidInputMsg3().setRendered(false);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvalidInputMsg3());
                            okVisibleForQuality = false;
                            editVisibleForQuality = true;
                            getBindings().getConfigureFuelCapacityAlert2().setDisabled(true);
                            getBindings().getConfigureLtrPerDayRadio().setDisabled(true);
                            getBindings().getConfigureLtrPerWeekRadio().setDisabled(true);
                            getBindings().getConfigureLtrPerMonthRadio().setDisabled(true);
                            displayErrorComponent(getBindings().getConfigureFuelCapacityAlert2(), false);
                        }
                    }
                    vo.removeNamedWhereClauseParam(Constants.SUBSCRIDLITERAL);
                    vo.setWhereClause("");
                    vo.executeQuery();
                } else {
                    getBindings().getInvalidInputMsg3().setRendered(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvalidInputMsg3());
                    getBindings().getMandatoryMsg3().setRendered(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg3());
                    displayErrorComponent(getBindings().getConfigureFuelCapacityAlert2(), true);
                }
            } else {
                displayErrorComponent(getBindings().getConfigureFuelCapacityAlert2(), true);
                getBindings().getMandatoryMsg3().setRendered(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMandatoryMsg3());
                getBindings().getInvalidInputMsg3().setRendered(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvalidInputMsg3());
            }
        }
    }

    public void claoseConfigureAlert2Popup(ActionEvent actionEvent) {
        getBindings().getConfigureAlert2Popup().hide();
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    public List suggesstedItemsResult(String string) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside suggesstedItemsResult method of Alerts");
        ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "size " + suggestedCardNumberList.size());
        for (int i = 0; i < suggestedCardNumberList.size(); i++) {
            if (suggestedCardNumberList.get(i).toString().toUpperCase().contains(string)) {
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(suggestedCardNumberList.get(i));
                selectItem.setValue(suggestedCardNumberList.get(i));
                selectItems.add(selectItem);
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside suggesstedItemsResult method of Alerts");
        return selectItems;
    }

    public void setSuggestedCardNumberList(List<String> suggestedCardNumberList) {
        this.suggestedCardNumberList = suggestedCardNumberList;
    }

    public List<String> getSuggestedCardNumberList() {
        return suggestedCardNumberList;
    }

    public void mainSelectionPanelRadioListner(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside mainSelectionPanelRadioListner method of Alerts");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().equals(true)) {
            getBindings().getViewAlertsPartnerDropdown().setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertsPartnerDropdown());
            getBindings().getViewAlertsAccountDropdown().setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertsAccountDropdown());
            getBindings().getViewAlertCardGroupDropdown().setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertCardGroupDropdown());
            getBindings().getViewAlertDropdown().setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertDropdown());
            searchString = null;
            getBindings().getSearchStringInputtext().setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchStringInputtext());
            isTableVisible = false;
        } else {
            getBindings().getViewAlertsPartnerDropdown().setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertsPartnerDropdown());
            getBindings().getViewAlertsAccountDropdown().setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertsAccountDropdown());
            getBindings().getViewAlertCardGroupDropdown().setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertCardGroupDropdown());
            getBindings().getViewAlertDropdown().setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertDropdown());
            getBindings().getSearchStringInputtext().setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchStringInputtext());
        }
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "valueChangeEvent mainSelectionPanelRadioListner" +
                    valueChangeEvent.getNewValue().toString());
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().equals(Constants.TRUE_LITERAL)) {
            selectionPanel = true;
            cardsSelectionPanel = false;
            if (selectionPanel) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Search by all");
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside mainSelectionPanelRadioListner method of Alerts");
    }

    public void cardSelectionPanelRadioListner(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside cardSelectionPanelRadioListner method of Alerts");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().equals(true)) {
            getBindings().getViewAlertsPartnerDropdown().setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertsPartnerDropdown());
            getBindings().getViewAlertsAccountDropdown().setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertsAccountDropdown());
            getBindings().getViewAlertCardGroupDropdown().setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertCardGroupDropdown());
            getBindings().getViewAlertDropdown().setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertDropdown());
            searchString = null;
            getBindings().getSearchStringInputtext().setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchStringInputtext());
        } else {
            getBindings().getViewAlertsPartnerDropdown().setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertsPartnerDropdown());
            getBindings().getViewAlertsAccountDropdown().setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertsAccountDropdown());
            getBindings().getViewAlertCardGroupDropdown().setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertCardGroupDropdown());
            getBindings().getViewAlertDropdown().setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getViewAlertDropdown());
            getBindings().getSearchStringInputtext().setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchStringInputtext());
        }

        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "valueChangeEvent cardSelectionPanelRadioListner" +
                    valueChangeEvent.getNewValue().toString());
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().equals(Constants.TRUE_LITERAL)) {
            selectionPanel = false;
            cardsSelectionPanel = true;
            if (cardsSelectionPanel) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Search by card number");
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside cardSelectionPanelRadioListner method of Alerts");
    }

    public void setSelectionPanel(boolean selectionPanel) {
        this.selectionPanel = selectionPanel;
    }

    public boolean isSelectionPanel() {
        return selectionPanel;
    }

    public void setCardsSelectionPanel(boolean cardsSelectionPanel) {
        this.cardsSelectionPanel = cardsSelectionPanel;
    }

    public boolean isCardsSelectionPanel() {
        return cardsSelectionPanel;
    }

    public void setPassingPartner(String passingPartner) {
        this.passingPartner = passingPartner;
    }

    public String getPassingPartner() {
        return passingPartner;
    }

    public void setPassingAccount(String passingAccount) {
        this.passingAccount = passingAccount;
    }

    public String getPassingAccount() {
        return passingAccount;
    }

    public void setPassingCardgrpMain(String passingCardgrpMain) {
        this.passingCardgrpMain = passingCardgrpMain;
    }

    public String getPassingCardgrpMain() {
        return passingCardgrpMain;
    }

    public void setPassingCardgrpSub(String passingCardgrpSub) {
        this.passingCardgrpSub = passingCardgrpSub;
    }

    public String getPassingCardgrpSub() {
        return passingCardgrpSub;
    }

    public void setPassingCardgrpSeq(String passingCardgrpSeq) {
        this.passingCardgrpSeq = passingCardgrpSeq;
    }

    public String getPassingCardgrpSeq() {
        return passingCardgrpSeq;
    }

    public void setPassingCardKsId(String passingCardKsId) {
        this.passingCardKsId = passingCardKsId;
    }

    public String getPassingCardKsId() {
        return passingCardKsId;
    }

    public void setPartnerIdList2(List<SelectItem> partnerIdList2) {
        this.partnerIdList2 = partnerIdList2;
    }

    public List<SelectItem> getPartnerIdList2() {
        return partnerIdList2;
    }

    public void setPartnerIdValue2(List<String> partnerIdValue2) {
        this.partnerIdValue2 = partnerIdValue2;
    }

    public List<String> getPartnerIdValue2() {
        return partnerIdValue2;
    }

    public void setAccountIdList2(List<SelectItem> accountIdList2) {
        this.accountIdList2 = accountIdList2;
    }

    public List<SelectItem> getAccountIdList2() {
        return accountIdList2;
    }

    public void setAccountIdValue2(List<String> accountIdValue2) {
        this.accountIdValue2 = accountIdValue2;
    }

    public List<String> getAccountIdValue2() {
        return accountIdValue2;
    }

    public void setCardGroupList2(List<SelectItem> cardGroupList2) {
        this.cardGroupList2 = cardGroupList2;
    }

    public List<SelectItem> getCardGroupList2() {
        return cardGroupList2;
    }

    public void setCardGroupValue2(List<String> cardGroupValue2) {
        this.cardGroupValue2 = cardGroupValue2;
    }

    public List<String> getCardGroupValue2() {
        return cardGroupValue2;
    }

    public void setCardNumberList2(List<SelectItem> cardNumberList2) {
        this.cardNumberList2 = cardNumberList2;
    }

    public List<SelectItem> getCardNumberList2() {
        return cardNumberList2;
    }

    public void setCardNumberValue2(List<String> cardNumberValue2) {
        this.cardNumberValue2 = cardNumberValue2;
    }

    public List<String> getCardNumberValue2() {
        return cardNumberValue2;
    }

    public void setConfiguredPartner(String configuredPartner) {
        this.configuredPartner = configuredPartner;
    }

    public String getConfiguredPartner() {
        return configuredPartner;
    }

    public void setInitailAccountId2VAlue(List<String> initailAccountId2VAlue) {
        this.initailAccountId2VAlue = initailAccountId2VAlue;
    }

    public List<String> getInitailAccountId2VAlue() {
        return initailAccountId2VAlue;
    }

    public void setInitialCardGroup2Value(List<String> initialCardGroup2Value) {
        this.initialCardGroup2Value = initialCardGroup2Value;
    }

    public List<String> getInitialCardGroup2Value() {
        return initialCardGroup2Value;
    }

    public void setInitialCard2Value(List<String> initialCard2Value) {
        this.initialCard2Value = initialCard2Value;
    }

    public List<String> getInitialCard2Value() {
        return initialCard2Value;
    }

    public void clearSearchListener(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside clearSearchListener method of Alerts");
        isTableVisible = false;
        populateDefaultDropdown(true);
        searchString = null;
        getBindings().getViewAlertsPartnerDropdown().setDisabled(false);
        getBindings().getViewAlertsAccountDropdown().setDisabled(false);
        getBindings().getViewAlertCardGroupDropdown().setDisabled(false);
        getBindings().getViewAlertDropdown().setDisabled(false);
        getBindings().getSearchStringInputtext().setDisabled(true);
        getBindings().getMainSelectionPanelRadio().setValue(true);
        getBindings().getCardSelectionPanelRadio().setValue(false);
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside clearSearchListener method of Alerts");
    }

    public void setSelectedPartner(String selectedPartner) {
        this.selectedPartner = selectedPartner;
    }

    public String getSelectedPartner() {
        if (configuredPartner != null) {
            selectedPartner = getPartnerName(configuredPartner);
        }
        return selectedPartner;
    }

    public void removeWhereClause(ViewObject vo) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside removeWhereClause method of Alerts");
        if (accountQuery.length() > 1 && accountQuery != null && cardGroupQuery.length() > 1 && cardGroupQuery != null && cardQuery.length() > 1 &&
            cardQuery != null) {
            if (vo.getWhereClause() != null) {
                if (((accountQuery + "AND " + cardGroupQuery + "AND " + cardQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim())) ||
                    ((accountQuery + " AND " + cardGroupQuery + " AND " + cardQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim()))) {
                    if (mapAccountListValue != null) {
                        for (int i = 0; i < mapAccountListValue.size(); i++) {
                            vo.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL + i);
                        }
                    } else {
                        vo.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL);
                    }
                    if (mapCardGroupListValue != null) {
                        for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                            vo.removeNamedWhereClauseParam(Constants.CARDGROUPLITERAL + i);
                        }
                    } else {
                        vo.removeNamedWhereClauseParam(Constants.CARDGROUPLITERAL);
                    }
                    if (mapCardListValue != null) {
                        for (int i = 0; i < mapCardListValue.size(); i++) {
                            vo.removeNamedWhereClauseParam(Constants.CARDLITERAL + i);
                        }
                    } else {
                        vo.removeNamedWhereClauseParam(Constants.CARDLITERAL);
                    }
                    vo.setWhereClause("");
                    vo.executeQuery();
                }
            }
        } else if (accountQuery.length() > 1 && accountQuery != null && cardGroupQuery.length() > 1 && cardGroupQuery != null) {
            if (vo.getWhereClause() != null) {
                if (((accountQuery + "AND " + cardGroupQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim())) ||
                    ((accountQuery + " AND " + cardGroupQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim()))) {
                    if (mapAccountListValue != null) {
                        for (int i = 0; i < mapAccountListValue.size(); i++) {
                            vo.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL + i);
                        }
                    } else {
                        vo.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL);
                    }
                    if (mapCardGroupListValue != null) {
                        for (int i = 0; i < mapCardGroupListValue.size(); i++) {

                            vo.removeNamedWhereClauseParam(Constants.CARDGROUPLITERAL + i);
                        }
                    } else {
                        vo.removeNamedWhereClauseParam(Constants.CARDGROUPLITERAL);
                    }
                    vo.setWhereClause("");
                    vo.executeQuery();
                }
            }
        } else if (accountQuery.length() > 1 && accountQuery != null) {
            if (vo.getWhereClause() != null) {
                if ((accountQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
                    if (mapAccountListValue != null) {
                        for (int i = 0; i < mapAccountListValue.size(); i++) {
                            vo.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL + i);
                        }
                    } else {
                        vo.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL);
                    }
                    vo.setWhereClause("");
                    vo.executeQuery();
                }
            }
        }
        accountQuery = "(";
        cardGroupQuery = "(";
        cardQuery = "(";
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside removeWhereClause method of Alerts");
    }

    public void createAccountQuery() {
        if (accountIdValue2.size() > Constants.ONEFIFTY) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 in Alerts");
            mapAccountListValue = ValueListSplit.callValueList(accountIdValue2.size(), accountIdValue2);
            accountQuery = accountQuery + "(ACCOUNT_ID = 'ALL') OR ";
            for (int i = 0; i < mapAccountListValue.size(); i++) {
                String values = Constants.ACCOUNT_LITERAL + i;
                accountQuery = accountQuery + "INSTR(:" + values + ",ACCOUNT_ID)<>0 OR ";
            }
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Account Query Values =" + accountQuery);
            accountQuery = accountQuery.substring(0, accountQuery.length() - Constants.THREE);
            accountQuery = accountQuery + ") ";
        } else {
            mapAccountListValue = null;
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 in Alerts");
            accountQuery = "((ACCOUNT_ID = 'ALL') OR (INSTR(:account,ACCOUNT_ID)<>0)) ";
        }
    }

    public void createCardGroupQuery() {
        if (cardGroupValue2.size() > Constants.ONEFIFTY) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values > 150 in Alerts");
            cardGroupQuery = cardGroupQuery + "((CARDGROUP_MAIN = 'ALL') AND (CARDGROUP_SUB = 'ALL') AND (CARDGROUP_SEQ = 'ALL')) OR ";
            mapCardGroupListValue = ValueListSplit.callValueList(cardGroupValue2.size(), cardGroupValue2);
            for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                String values = Constants.CARDGROUPLITERAL + i;
                cardGroupQuery = cardGroupQuery + "INSTR(:" + values + ",CARDGROUP_MAIN||CARDGROUP_SUB||CARDGROUP_SEQ)<>0 OR ";
            }
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "CARDGROUP Query Values =" + cardGroupQuery);
            cardGroupQuery = cardGroupQuery.substring(0, cardGroupQuery.length() - Constants.THREE);
            cardGroupQuery = cardGroupQuery + ") ";
        } else {
            mapCardGroupListValue = null;
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 in Alerts");
            cardGroupQuery =
                    "(((CARDGROUP_MAIN = 'ALL') AND (CARDGROUP_SUB = 'ALL') AND (CARDGROUP_SEQ = 'ALL')) OR (INSTR(:cardGroup,CARDGROUP_MAIN||CARDGROUP_SUB||CARDGROUP_SEQ)<>0)) ";
        }
    }

    public void createCardQuery() {
        if (cardNumberValue2.size() > Constants.ONEFIFTY) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values > 150 in Alerts");
            cardQuery = cardQuery + "(CARD_KSID = 'ALL') OR ";
            mapCardListValue = ValueListSplit.callValueList(cardNumberValue2.size(), cardNumberValue2);
            for (int i = 0; i < mapCardListValue.size(); i++) {
                String values = Constants.CARDLITERAL + i;
                cardQuery = cardQuery + "INSTR(:" + values + ",CARD_KSID)<>0 OR ";
            }
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "CARD Query Values =" + cardQuery);
            cardQuery = cardQuery.substring(0, cardQuery.length() - Constants.THREE);
            cardQuery = cardQuery + ") ";
        } else {
            mapCardListValue = null;
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values < 150 in Alerts");
            cardQuery = "((CARD_KSID = 'ALL') OR (INSTR(:card,CARD_KSID)<>0)) ";
        }
    }

    public void addAccountQuery(ViewObject vo, String passingValue) {
        if (accountIdValue2.size() > Constants.ONEFIFTY) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 in Alerts");
            mapAccountListValue = ValueListSplit.callValueList(accountIdValue2.size(), accountIdValue2);
            for (int i = 0; i < mapAccountListValue.size(); i++) {

                String listName = "listName" + i;
                vo.defineNamedWhereClauseParam(Constants.ACCOUNT_LITERAL + i, mapAccountListValue.get(listName), null);
            }
        } else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 in Alerts");
            vo.defineNamedWhereClauseParam(Constants.ACCOUNT_LITERAL, passingValue, null);
        }
    }

    public void addCardGroupQuery(ViewObject vo, String passingValue) {
        if (cardGroupValue2.size() > Constants.ONEFIFTY) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values > 150 in Alerts");
            mapCardGroupListValue = ValueListSplit.callValueList(cardGroupValue2.size(), cardGroupValue2);
            for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                String listName = "listName" + i;
                vo.defineNamedWhereClauseParam(Constants.CARDGROUPLITERAL + i, mapCardGroupListValue.get(listName), null);
            }
        } else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 in Alerts");
            vo.defineNamedWhereClauseParam(Constants.CARDGROUPLITERAL, passingValue, null);
        }
    }

    public void addCardQuery(ViewObject vo, String passingValue) {
        if (cardNumberValue2.size() > Constants.ONEFIFTY) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values > 150 in Alerts");
            mapCardListValue = ValueListSplit.callValueList(cardNumberValue2.size(), cardNumberValue2);
            for (int i = 0; i < mapCardListValue.size(); i++) {
                String values = "card" + i;
                String listName = "listName" + i;
                vo.defineNamedWhereClauseParam(Constants.CARDLITERAL + i, mapCardListValue.get(listName), null);
            }
        } else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values < 150 Alerts");
            vo.defineNamedWhereClauseParam(Constants.CARDLITERAL, passingValue, null);
        }
    }

    public void setIsTableVisible(Boolean isTableVisible) {
        this.isTableVisible = isTableVisible;
    }

    public Boolean getIsTableVisible() {
        return isTableVisible;
    }

    public void cardNumberValueChangeListener(ValueChangeEvent valueChangeEvent) {
        isTableVisible = false;
    }

    public void resetTableFilter() {
        FilterableQueryDescriptor queryDescriptor = (FilterableQueryDescriptor)getBindings().getSearchResultsTB().getFilterModel();
        if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null) {
            queryDescriptor.getFilterCriteria().clear();
            getBindings().getSearchResultsTB().queueEvent(new QueryEvent(getBindings().getSearchResultsTB(), queryDescriptor));
        }
    }

    public void filterTable(ActionEvent actionEvent) {
        FilterableQueryDescriptor qd = (FilterableQueryDescriptor)getBindings().getSearchResultsTB().getFilterModel();
        QueryEvent queryEvent = new QueryEvent(getBindings().getSearchResultsTB(), qd);
        getBindings().getSearchResultsTB().queueEvent(queryEvent);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResultsTB());
    }

    public void resetFilterTable(ActionEvent actionEvent) {
        resetTableFilter();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResultsTB());
    }

    public void displayErrorComponent(UIComponent component, boolean status) {

        RichSelectManyChoice smc = new RichSelectManyChoice();
        RichInputText rit = new RichInputText();
        RichSelectOneChoice soc = new RichSelectOneChoice();

        if (component instanceof RichInputText) {
            rit = (RichInputText)component;
            if (status) {
                rit.setStyleClass(AFMANDATORYFIELDLITERAL);
                if (component.getId().contains("it19") || component.getId().contains("it3") || component.getId().contains("it10") ||
                    component.getId().contains("it6") || component.getId().contains("it7") || component.getId().contains("it8") ||
                    component.getId().contains("it9") || component.getId().contains("it1") || component.getId().contains("it4") ||
                    component.getId().contains("it2") || component.getId().contains("it5")) {
                    rit.setStyleClass(AFMANDATORYFIELDLITERAL);
                }
            } else {
                rit.setStyleClass(AFNONMANDATORYFIELDLITERAL);
                if (component.getId().contains("it19") || component.getId().contains("it3") || component.getId().contains("it10") ||
                    component.getId().contains("it6") || component.getId().contains("it7") || component.getId().contains("it8") ||
                    component.getId().contains("it9") || component.getId().contains("it1") || component.getId().contains("it4") ||
                    component.getId().contains("it2") || component.getId().contains("it5")) {
                    rit.setStyleClass(AFNONMANDATORYFIELDLITERAL);
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(rit);
        } else if (component instanceof RichSelectManyChoice) {
            smc = (RichSelectManyChoice)component;
            if (status) {
                smc.setStyleClass(AFMANDATORYFIELDLITERAL);
                if (component.getId().contains("smc1") || component.getId().contains("smc4") || component.getId().contains("smc3") ||
                    component.getId().contains("soc3") || component.getId().contains("smc2") || component.getId().contains("smc5") ||
                    component.getId().contains("smc6") || component.getId().contains("smc7") || component.getId().contains("smc8") ||
                    component.getId().contains("smc9") || component.getId().contains("smc10") || component.getId().contains("smc11")) {
                    smc.setStyleClass(AFMANDATORYFIELDLITERAL);
                }

            } else {
                smc.setStyleClass(AFNONMANDATORYFIELDLITERAL);
                if (component.getId().contains("smc1") || component.getId().contains("smc4") || component.getId().contains("smc3") ||
                    component.getId().contains("soc3") || component.getId().contains("smc2") || component.getId().contains("smc5") ||
                    component.getId().contains("smc6") || component.getId().contains("smc7") || component.getId().contains("smc8") ||
                    component.getId().contains("smc9") || component.getId().contains("smc10") || component.getId().contains("smc11")) {
                    smc.setStyleClass(AFNONMANDATORYFIELDLITERAL);
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(soc);
        } else if (component instanceof RichInputText) {
            soc = (RichSelectOneChoice)component;
            if (status) {
                soc.setStyleClass(AFMANDATORYFIELDLITERAL);
                if (component.getId().contains("soc1")) {
                    soc.setStyleClass(AFMANDATORYFIELDLITERAL);
                }
            } else {
                soc.setStyleClass(AFNONMANDATORYFIELDLITERAL);
                if (component.getId().contains("soc1")) {
                    soc.setStyleClass(AFNONMANDATORYFIELDLITERAL);
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(rit);
        }
    }

    private Boolean isComponentEmpty(UIComponent rit1) {

        RichSelectManyChoice soc = new RichSelectManyChoice();
        RichInputText rit = new RichInputText();
        if (rit1 instanceof RichInputText) {
            rit = (RichInputText)rit1;
            if (rit.getValue() == null || rit.getValue().equals("")) {
                displayErrorComponent(rit, true);
                return true;
            } else {
                displayErrorComponent(rit, false);
                return false;
            }
        }

        else if (rit1 instanceof RichSelectManyChoice) {
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


    public void unsubscribeAlert(ActionEvent actionEvent) {
        AlertsUnsubscribeRequest unsubscribeRequest = new AlertsUnsubscribeRequest();
        AlertsUnsubscribeResponse unsubscribeResponse = new AlertsUnsubscribeResponse();

        BindingContainer localBinding = BindingContext.getCurrent().getCurrentBindingsEntry();

        if (localBinding != null) {
            OperationBinding operationBinding = localBinding.getOperationBinding("unsubscribeAlerts");


            if (operationBinding != null) {

                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.SUBSCRID_KEY_LITERAL) != null &&
                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.RULEID_KEY_LITERAL) != null) {
                    unsubscribeRequest.setSubscriptionID(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.SUBSCRID_KEY_LITERAL).toString());
                    unsubscribeRequest.setCustomerID(userEmail);
                    LOGGER.info("Subscribe Id " + AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.SUBSCRID_KEY_LITERAL));
                    operationBinding.getParamsMap().put("unsubscribeRequest", unsubscribeRequest);
                }

                unsubscribeResponse = (AlertsUnsubscribeResponse)operationBinding.execute();


            }
        }

        if (unsubscribeResponse.getStatus() != null) {
            LOGGER.info("response " + unsubscribeResponse.getStatus());
            localBinding = BindingContext.getCurrent().getCurrentBindingsEntry();

            if (localBinding != null) {
                OperationBinding operationBinding = localBinding.getOperationBinding("deleteAlert");


                if (operationBinding != null) {
                    if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.SUBSCRID_KEY_LITERAL) != null) {
                        LOGGER.info("1 " + AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.SUBSCRID_KEY_LITERAL));
                        operationBinding.getParamsMap().put("subsId",
                                                            AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.SUBSCRID_KEY_LITERAL).toString());
                    }
                    if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.RULEID_KEY_LITERAL) != null) {
                        LOGGER.info("2 " + AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.RULEID_KEY_LITERAL));
                        operationBinding.getParamsMap().put(Constants.RULE_ID_LITERAL,
                                                            AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.RULEID_KEY_LITERAL).toString());
                    }


                    operationBinding.getParamsMap().put("UserId", userEmail);
                    operationBinding.getParamsMap().put("countryCd", countryCode);

                    operationBinding.execute();

                    resetTableFilter();
                    if(getBindings().getViewAlertDropdown().getValue() != null){
                        if(getBindings().getViewAlertCardGroupDropdown().getValue() != null){
                            if(getBindings().getViewAlertsAccountDropdown().getValue() != null){
                                if(getBindings().getViewAlertsPartnerDropdown().getValue() != null){
                                    viewSubscribedAlertsUI(getBindings().getViewAlertsPartnerDropdown().getValue().toString().trim(),
                                                           getBindings().getViewAlertsAccountDropdown().getValue().toString().trim(),
                                                           getBindings().getViewAlertCardGroupDropdown().getValue().toString().trim(),
                                                           getBindings().getViewAlertDropdown().getValue().toString().trim());
                                }else{
                                    showErrorMessage(Constants.ENGAGENOPARTNER_LITERAL);
                                    displayErrorComponent(getBindings().getViewAlertsPartnerDropdown(), true);
                                }
                            }else{
                                showErrorMessage("ENGAGE_NO_ACCOUNT");
                                displayErrorComponent(getBindings().getViewAlertsAccountDropdown(), true);
                            }
                        } else {
                            showErrorMessage(Constants.ENGAGENOPARTNER_LITERAL);
                            displayErrorComponent(getBindings().getViewAlertCardGroupDropdown(), true);
                        }
                    } else{
                        showErrorMessage(Constants.ENGAGENOPARTNER_LITERAL);
                        displayErrorComponent(getBindings().getViewAlertDropdown(), true);
                    }


                }
            }
        }

    }

    public void confirmDeleteAlert(ActionEvent actionEvent) {
        RichPopup.PopupHints ps = new RichPopup.PopupHints();
        getBindings().getConfirmationPopup().show(ps);
    }

    public void closeRemoveAlertPopup(ActionEvent actionEvent) {
        getBindings().getConfirmationPopup().hide();
    }

    public void setOkVisibleForBusinessHr(Boolean okVisibleForBusinessHr) {
        this.okVisibleForBusinessHr = okVisibleForBusinessHr;
    }

    public Boolean getOkVisibleForBusinessHr() {
        return okVisibleForBusinessHr;
    }

    public void setEditVisibleForBusinessHr(Boolean editVisibleForBusinessHr) {
        this.editVisibleForBusinessHr = editVisibleForBusinessHr;
    }

    public Boolean getEditVisibleForBusinessHr() {
        return editVisibleForBusinessHr;
    }

    public void setOkVisibleForQuality(Boolean okVisibleForQuality) {
        this.okVisibleForQuality = okVisibleForQuality;
    }

    public Boolean getOkVisibleForQuality() {
        return okVisibleForQuality;
    }

    public void setEditVisibleForQuality(Boolean editVisibleForQuality) {
        this.editVisibleForQuality = editVisibleForQuality;
    }

    public Boolean getEditVisibleForQuality() {
        return editVisibleForQuality;
    }

    public void setReq(AlertsSubscribeRequest req) {
        this.req = req;
    }

    public AlertsSubscribeRequest getReq() {
        return req;
    }

    public void setResponse(AlertsSubscribeResponse response) {
        this.response = response;
    }

    public AlertsSubscribeResponse getResponse() {
        return response;
    }

    public void setResourceBundle(EngageResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public EngageResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setComparator(Comparator<SelectItem> comparator) {
        this.comparator = comparator;
    }

    public Comparator<SelectItem> getComparator() {
        return comparator;
    }

    public void setUserAssociationType(String userAssociationType) {
        this.userAssociationType = userAssociationType;
    }

    public String getUserAssociationType() {
        return userAssociationType;
    }

    public class Bindings {
        private RichPopup alert1Popup;
        private RichPopup alert2Popup;
        private RichPanelGroupLayout successfullalert;
        private RichPanelGroupLayout alertSuccessProperty;
        private RichDialog alert1PopupDialog;
        private RichSelectOneChoice alert1PartnerValues;
        private RichInputText fromTimingsHh;
        private RichInputText fromTimingsMm;
        private RichInputText toTimingsHh;
        private RichInputText toTimingsMm;
        private RichTable alert1Table;
        private RichSelectManyChoice accountDropdwonAlert2;
        private RichSelectManyChoice cardGroupDowndownAlert2;
        private RichSelectManyChoice cardDropdownAlert2;
        private RichSelectManyChoice partnerDropdownAlert2;
        private RichSelectBooleanRadio ltrPerDayRadio;
        private RichSelectBooleanRadio ltrPerWeekRadio;
        private RichSelectBooleanRadio ltrPerMonthRadio;
        private RichInputText fuelCapacityAlert2;
        private RichCommandButton okButtonAlert2;
        private RichCommandButton cancelButtonAlert2;
        private RichPanelGroupLayout successAlert2;
        private RichPanelGroupLayout successAlert3;
        private RichPanelGroupLayout successAlert4;
        private RichPanelGroupLayout invalidInputMsg;
        private RichPanelGroupLayout mandatoryMsg;
        private RichPanelGroupLayout mandatoryMsg4;
        private RichPanelGroupLayout invalidInputMsg3;
        private RichCommandButton closeButtonAlert2;
        private RichPanelGroupLayout mandatoryMsg3;
        private RichPanelGroupLayout mandatoryMsg1;
        private RichSelectManyChoice viewAlertDropdown;
        private RichSelectManyChoice viewAlertCardGroupDropdown;
        private RichSelectManyChoice viewAlertsPartnerDropdown;
        private RichSelectManyChoice viewAlertsAccountDropdown;
        private RichPanelGroupLayout alert1ValidData;
        private RichCommandButton closeButtonAlert1;
        private RichCommandButton cancelButtonAlert1;
        private RichCommandButton okButtonAlert1;
        private RichCommandButton editButtonAlert1;
        private RichPanelGroupLayout alert2ValidData;
        private RichPanelGroupLayout searchResultsPanel;
        private RichPopup configureAlert1Popup;
        private RichDialog configureAlert1PopupDialog;
        private RichSelectOneChoice configureAlert1PartnerValues;
        private RichTable configureAlert1Table;
        private RichInputText configureFromTimingsHh;
        private RichInputText configureFromTimingsMm;
        private RichInputText configureToTimingsHh;
        private RichInputText configureToTimingsMm;
        private RichCommandButton editButtonConfigureAlert1;
        private RichCommandButton okButtonConfigureAlert1;
        private RichCommandButton cancelButtonConfigureAlert1;
        private RichCommandButton closeButtonConfigureAlert1;
        private RichPopup configureAlert2Popup;
        private RichSelectManyChoice configurePartnerDropdownAlert2;
        private RichSelectManyChoice configureAccountDropdwonAlert2;
        private RichSelectManyChoice configureCardGroupDowndownAlert2;
        private RichSelectManyChoice configureCardDropdownAlert2;
        private RichSelectBooleanRadio configureLtrPerDayRadio;
        private RichSelectBooleanRadio configureLtrPerWeekRadio;
        private RichSelectBooleanRadio configureLtrPerMonthRadio;
        private RichInputText configureFuelCapacityAlert2;
        private RichCommandButton configureOkButtonAlert2;
        private RichCommandButton configureCloseButtonAlert2;
        private RichCommandButton configureCancelButtonAlert2;
        private RichInputText searchStringInputtext;
        private List<String> suggestedCardNumberList;
        private RichPanelGroupLayout mainSelectionPanel;
        private RichSelectBooleanRadio mainSelectionPanelRadio;
        private RichSelectBooleanRadio cardSelectionPanelRadio;
        private RichPanelGroupLayout cardSelectionPanel;
        private RichTable searchResultsTB;
        private RichPopup confirmationPopup;

        public void setConfirmationPopup(RichPopup confirmationPopup) {
            this.confirmationPopup = confirmationPopup;
        }

        public RichPopup getConfirmationPopup() {
            return confirmationPopup;
        }

        public void setSearchResultsTB(RichTable searchResultsTB) {
            this.searchResultsTB = searchResultsTB;
        }

        public RichTable getSearchResultsTB() {
            return searchResultsTB;
        }

        public void setAlert1Popup(RichPopup alert1Popup) {
            this.alert1Popup = alert1Popup;
        }

        public RichPopup getAlert1Popup() {
            return alert1Popup;
        }

        public void setAlert2Popup(RichPopup alert2Popup) {
            this.alert2Popup = alert2Popup;
        }

        public RichPopup getAlert2Popup() {
            return alert2Popup;
        }

        public void setSuccessfullalert(RichPanelGroupLayout successfullalert) {
            this.successfullalert = successfullalert;
        }

        public RichPanelGroupLayout getSuccessfullalert() {
            return successfullalert;
        }

        public void setAlertSuccessProperty(RichPanelGroupLayout alertSuccessProperty) {
            this.alertSuccessProperty = alertSuccessProperty;
        }

        public RichPanelGroupLayout getAlertSuccessProperty() {
            return alertSuccessProperty;
        }

        public void setAlert1PopupDialog(RichDialog alert1PopupDialog) {
            this.alert1PopupDialog = alert1PopupDialog;
        }

        public RichDialog getAlert1PopupDialog() {
            return alert1PopupDialog;
        }

        public void setAlert1PartnerValues(RichSelectOneChoice alert1PartnerValues) {
            this.alert1PartnerValues = alert1PartnerValues;
        }

        public RichSelectOneChoice getAlert1PartnerValues() {
            return alert1PartnerValues;
        }

        public void setFromTimingsHh(RichInputText fromTimingsHh) {
            this.fromTimingsHh = fromTimingsHh;
        }

        public RichInputText getFromTimingsHh() {
            return fromTimingsHh;
        }

        public void setFromTimingsMm(RichInputText fromTimingsMm) {
            this.fromTimingsMm = fromTimingsMm;
        }

        public RichInputText getFromTimingsMm() {
            return fromTimingsMm;
        }

        public void setToTimingsHh(RichInputText toTimingsHh) {
            this.toTimingsHh = toTimingsHh;
        }

        public RichInputText getToTimingsHh() {
            return toTimingsHh;
        }

        public void setToTimingsMm(RichInputText toTimingsMm) {
            this.toTimingsMm = toTimingsMm;
        }

        public RichInputText getToTimingsMm() {
            return toTimingsMm;
        }

        public void setAlert1Table(RichTable alert1Table) {
            this.alert1Table = alert1Table;
        }

        public RichTable getAlert1Table() {
            return alert1Table;
        }

        public void setAccountDropdwonAlert2(RichSelectManyChoice accountDropdwonAlert2) {
            this.accountDropdwonAlert2 = accountDropdwonAlert2;
        }

        public RichSelectManyChoice getAccountDropdwonAlert2() {
            return accountDropdwonAlert2;
        }

        public void setCardGroupDowndownAlert2(RichSelectManyChoice cardGroupDowndownAlert2) {
            this.cardGroupDowndownAlert2 = cardGroupDowndownAlert2;
        }

        public RichSelectManyChoice getCardGroupDowndownAlert2() {
            return cardGroupDowndownAlert2;
        }

        public void setCardDropdownAlert2(RichSelectManyChoice cardDropdownAlert2) {
            this.cardDropdownAlert2 = cardDropdownAlert2;
        }

        public RichSelectManyChoice getCardDropdownAlert2() {
            return cardDropdownAlert2;
        }

        public void setPartnerDropdownAlert2(RichSelectManyChoice partnerDropdownAlert2) {
            this.partnerDropdownAlert2 = partnerDropdownAlert2;
        }

        public RichSelectManyChoice getPartnerDropdownAlert2() {
            return partnerDropdownAlert2;
        }

        public void setLtrPerDayRadio(RichSelectBooleanRadio ltrPerDayRadio) {
            this.ltrPerDayRadio = ltrPerDayRadio;
        }

        public RichSelectBooleanRadio getLtrPerDayRadio() {
            return ltrPerDayRadio;
        }

        public void setLtrPerWeekRadio(RichSelectBooleanRadio ltrPerWeekRadio) {
            this.ltrPerWeekRadio = ltrPerWeekRadio;
        }

        public RichSelectBooleanRadio getLtrPerWeekRadio() {
            return ltrPerWeekRadio;
        }

        public void setLtrPerMonthRadio(RichSelectBooleanRadio ltrPerMonthRadio) {
            this.ltrPerMonthRadio = ltrPerMonthRadio;
        }

        public RichSelectBooleanRadio getLtrPerMonthRadio() {
            return ltrPerMonthRadio;
        }

        public void setFuelCapacityAlert2(RichInputText fuelCapacityAlert2) {
            this.fuelCapacityAlert2 = fuelCapacityAlert2;
        }

        public RichInputText getFuelCapacityAlert2() {
            return fuelCapacityAlert2;
        }

        public void setOkButtonAlert2(RichCommandButton okButtonAlert2) {
            this.okButtonAlert2 = okButtonAlert2;
        }

        public RichCommandButton getOkButtonAlert2() {
            return okButtonAlert2;
        }

        public void setCancelButtonAlert2(RichCommandButton cancelButtonAlert2) {
            this.cancelButtonAlert2 = cancelButtonAlert2;
        }

        public RichCommandButton getCancelButtonAlert2() {
            return cancelButtonAlert2;
        }

        public void setSuccessAlert2(RichPanelGroupLayout successAlert2) {
            this.successAlert2 = successAlert2;
        }

        public RichPanelGroupLayout getSuccessAlert2() {
            return successAlert2;
        }

        public void setCloseButtonAlert2(RichCommandButton closeButtonAlert2) {
            this.closeButtonAlert2 = closeButtonAlert2;
        }

        public RichCommandButton getCloseButtonAlert2() {
            return closeButtonAlert2;
        }

        public void setViewAlertDropdown(RichSelectManyChoice viewAlertDropdown) {
            this.viewAlertDropdown = viewAlertDropdown;
        }

        public RichSelectManyChoice getViewAlertDropdown() {
            return viewAlertDropdown;
        }

        public void setViewAlertCardGroupDropdown(RichSelectManyChoice viewAlertCardGroupDropdown) {
            this.viewAlertCardGroupDropdown = viewAlertCardGroupDropdown;
        }

        public RichSelectManyChoice getViewAlertCardGroupDropdown() {
            return viewAlertCardGroupDropdown;
        }

        public void setViewAlertsPartnerDropdown(RichSelectManyChoice viewAlertsPartnerDropdown) {
            this.viewAlertsPartnerDropdown = viewAlertsPartnerDropdown;
        }

        public RichSelectManyChoice getViewAlertsPartnerDropdown() {
            return viewAlertsPartnerDropdown;
        }

        public void setViewAlertsAccountDropdown(RichSelectManyChoice viewAlertsAccountDropdown) {
            this.viewAlertsAccountDropdown = viewAlertsAccountDropdown;
        }

        public RichSelectManyChoice getViewAlertsAccountDropdown() {
            return viewAlertsAccountDropdown;
        }

        public void setAlert1ValidData(RichPanelGroupLayout alert1ValidData) {
            this.alert1ValidData = alert1ValidData;
        }

        public RichPanelGroupLayout getAlert1ValidData() {
            return alert1ValidData;
        }

        public void setCloseButtonAlert1(RichCommandButton closeButtonAlert1) {
            this.closeButtonAlert1 = closeButtonAlert1;
        }

        public RichCommandButton getCloseButtonAlert1() {
            return closeButtonAlert1;
        }

        public void setCancelButtonAlert1(RichCommandButton cancelButtonAlert1) {
            this.cancelButtonAlert1 = cancelButtonAlert1;
        }

        public RichCommandButton getCancelButtonAlert1() {
            return cancelButtonAlert1;
        }

        public void setOkButtonAlert1(RichCommandButton okButtonAlert1) {
            this.okButtonAlert1 = okButtonAlert1;
        }

        public RichCommandButton getOkButtonAlert1() {
            return okButtonAlert1;
        }

        public void setEditButtonAlert1(RichCommandButton editButtonAlert1) {
            this.editButtonAlert1 = editButtonAlert1;
        }

        public RichCommandButton getEditButtonAlert1() {
            return editButtonAlert1;
        }

        public void setAlert2ValidData(RichPanelGroupLayout alert2ValidData) {
            this.alert2ValidData = alert2ValidData;
        }

        public RichPanelGroupLayout getAlert2ValidData() {
            return alert2ValidData;
        }

        public void setSearchResultsPanel(RichPanelGroupLayout searchResultsPanel) {
            this.searchResultsPanel = searchResultsPanel;
        }

        public RichPanelGroupLayout getSearchResultsPanel() {
            return searchResultsPanel;
        }

        public void setConfigureAlert1Popup(RichPopup configureAlert1Popup) {
            this.configureAlert1Popup = configureAlert1Popup;
        }

        public RichPopup getConfigureAlert1Popup() {
            return configureAlert1Popup;
        }

        public void setConfigureAlert1PopupDialog(RichDialog configureAlert1PopupDialog) {
            this.configureAlert1PopupDialog = configureAlert1PopupDialog;
        }

        public RichDialog getConfigureAlert1PopupDialog() {
            return configureAlert1PopupDialog;
        }

        public void setConfigureAlert1PartnerValues(RichSelectOneChoice configureAlert1PartnerValues) {
            this.configureAlert1PartnerValues = configureAlert1PartnerValues;
        }

        public RichSelectOneChoice getConfigureAlert1PartnerValues() {
            return configureAlert1PartnerValues;
        }

        public void setConfigureAlert1Table(RichTable configureAlert1Table) {
            this.configureAlert1Table = configureAlert1Table;
        }

        public RichTable getConfigureAlert1Table() {
            return configureAlert1Table;
        }

        public void setConfigureFromTimingsHh(RichInputText configureFromTimingsHh) {
            this.configureFromTimingsHh = configureFromTimingsHh;
        }

        public RichInputText getConfigureFromTimingsHh() {
            return configureFromTimingsHh;
        }

        public void setConfigureFromTimingsMm(RichInputText configureFromTimingsMm) {
            this.configureFromTimingsMm = configureFromTimingsMm;
        }

        public RichInputText getConfigureFromTimingsMm() {
            return configureFromTimingsMm;
        }

        public void setConfigureToTimingsHh(RichInputText configureToTimingsHh) {
            this.configureToTimingsHh = configureToTimingsHh;
        }

        public RichInputText getConfigureToTimingsHh() {
            return configureToTimingsHh;
        }

        public void setConfigureToTimingsMm(RichInputText configureToTimingsMm) {
            this.configureToTimingsMm = configureToTimingsMm;
        }

        public RichInputText getConfigureToTimingsMm() {
            return configureToTimingsMm;
        }

        public void setEditButtonConfigureAlert1(RichCommandButton editButtonConfigureAlert1) {
            this.editButtonConfigureAlert1 = editButtonConfigureAlert1;
        }

        public RichCommandButton getEditButtonConfigureAlert1() {
            return editButtonConfigureAlert1;
        }

        public void setOkButtonConfigureAlert1(RichCommandButton okButtonConfigureAlert1) {
            this.okButtonConfigureAlert1 = okButtonConfigureAlert1;
        }

        public RichCommandButton getOkButtonConfigureAlert1() {
            return okButtonConfigureAlert1;
        }

        public void setCancelButtonConfigureAlert1(RichCommandButton cancelButtonConfigureAlert1) {
            this.cancelButtonConfigureAlert1 = cancelButtonConfigureAlert1;
        }

        public RichCommandButton getCancelButtonConfigureAlert1() {
            return cancelButtonConfigureAlert1;
        }

        public void setCloseButtonConfigureAlert1(RichCommandButton closeButtonConfigureAlert1) {
            this.closeButtonConfigureAlert1 = closeButtonConfigureAlert1;
        }

        public RichCommandButton getCloseButtonConfigureAlert1() {
            return closeButtonConfigureAlert1;
        }

        public void setConfigureAlert2Popup(RichPopup configureAlert2Popup) {
            this.configureAlert2Popup = configureAlert2Popup;
        }

        public RichPopup getConfigureAlert2Popup() {
            return configureAlert2Popup;
        }

        public void setConfigurePartnerDropdownAlert2(RichSelectManyChoice configurePartnerDropdownAlert2) {
            this.configurePartnerDropdownAlert2 = configurePartnerDropdownAlert2;
        }

        public RichSelectManyChoice getConfigurePartnerDropdownAlert2() {
            return configurePartnerDropdownAlert2;
        }

        public void setConfigureAccountDropdwonAlert2(RichSelectManyChoice configureAccountDropdwonAlert2) {
            this.configureAccountDropdwonAlert2 = configureAccountDropdwonAlert2;
        }

        public RichSelectManyChoice getConfigureAccountDropdwonAlert2() {
            return configureAccountDropdwonAlert2;
        }

        public void setConfigureCardGroupDowndownAlert2(RichSelectManyChoice configureCardGroupDowndownAlert2) {
            this.configureCardGroupDowndownAlert2 = configureCardGroupDowndownAlert2;
        }

        public RichSelectManyChoice getConfigureCardGroupDowndownAlert2() {
            return configureCardGroupDowndownAlert2;
        }

        public void setConfigureCardDropdownAlert2(RichSelectManyChoice configureCardDropdownAlert2) {
            this.configureCardDropdownAlert2 = configureCardDropdownAlert2;
        }

        public RichSelectManyChoice getConfigureCardDropdownAlert2() {
            return configureCardDropdownAlert2;
        }

        public void setConfigureLtrPerDayRadio(RichSelectBooleanRadio configureLtrPerDayRadio) {
            this.configureLtrPerDayRadio = configureLtrPerDayRadio;
        }

        public RichSelectBooleanRadio getConfigureLtrPerDayRadio() {
            return configureLtrPerDayRadio;
        }

        public void setConfigureLtrPerWeekRadio(RichSelectBooleanRadio configureLtrPerWeekRadio) {
            this.configureLtrPerWeekRadio = configureLtrPerWeekRadio;
        }

        public RichSelectBooleanRadio getConfigureLtrPerWeekRadio() {
            return configureLtrPerWeekRadio;
        }

        public void setConfigureLtrPerMonthRadio(RichSelectBooleanRadio configureLtrPerMonthRadio) {
            this.configureLtrPerMonthRadio = configureLtrPerMonthRadio;
        }

        public RichSelectBooleanRadio getConfigureLtrPerMonthRadio() {
            return configureLtrPerMonthRadio;
        }

        public void setConfigureFuelCapacityAlert2(RichInputText configureFuelCapacityAlert2) {
            this.configureFuelCapacityAlert2 = configureFuelCapacityAlert2;
        }

        public RichInputText getConfigureFuelCapacityAlert2() {
            return configureFuelCapacityAlert2;
        }

        public void setConfigureOkButtonAlert2(RichCommandButton configureOkButtonAlert2) {
            this.configureOkButtonAlert2 = configureOkButtonAlert2;
        }

        public RichCommandButton getConfigureOkButtonAlert2() {
            return configureOkButtonAlert2;
        }

        public void setConfigureCloseButtonAlert2(RichCommandButton configureCloseButtonAlert2) {
            this.configureCloseButtonAlert2 = configureCloseButtonAlert2;
        }

        public RichCommandButton getConfigureCloseButtonAlert2() {
            return configureCloseButtonAlert2;
        }

        public void setConfigureCancelButtonAlert2(RichCommandButton configureCancelButtonAlert2) {
            this.configureCancelButtonAlert2 = configureCancelButtonAlert2;
        }

        public RichCommandButton getConfigureCancelButtonAlert2() {
            return configureCancelButtonAlert2;
        }

        public void setSearchStringInputtext(RichInputText searchStringInputtext) {
            this.searchStringInputtext = searchStringInputtext;
        }

        public RichInputText getSearchStringInputtext() {
            return searchStringInputtext;
        }

        public void setSuggestedCardNumberList(List<String> suggestedCardNumberList) {
            this.suggestedCardNumberList = suggestedCardNumberList;
        }

        public List<String> getSuggestedCardNumberList() {
            return suggestedCardNumberList;
        }

        public void setMainSelectionPanel(RichPanelGroupLayout mainSelectionPanel) {
            this.mainSelectionPanel = mainSelectionPanel;
        }

        public RichPanelGroupLayout getMainSelectionPanel() {
            return mainSelectionPanel;
        }

        public void setMainSelectionPanelRadio(RichSelectBooleanRadio mainSelectionPanelRadio) {
            this.mainSelectionPanelRadio = mainSelectionPanelRadio;
        }

        public RichSelectBooleanRadio getMainSelectionPanelRadio() {
            return mainSelectionPanelRadio;
        }

        public void setCardSelectionPanelRadio(RichSelectBooleanRadio cardSelectionPanelRadio) {
            this.cardSelectionPanelRadio = cardSelectionPanelRadio;
        }

        public RichSelectBooleanRadio getCardSelectionPanelRadio() {
            return cardSelectionPanelRadio;
        }

        public void setCardSelectionPanel(RichPanelGroupLayout cardSelectionPanel) {
            this.cardSelectionPanel = cardSelectionPanel;
        }

        public RichPanelGroupLayout getCardSelectionPanel() {
            return cardSelectionPanel;
        }

        public void setSuccessAlert3(RichPanelGroupLayout successAlert3) {
            this.successAlert3 = successAlert3;
        }

        public RichPanelGroupLayout getSuccessAlert3() {
            return successAlert3;
        }

        public void setSuccessAlert4(RichPanelGroupLayout successAlert4) {
            this.successAlert4 = successAlert4;
        }

        public RichPanelGroupLayout getSuccessAlert4() {
            return successAlert4;
        }

        public void setInvalidInputMsg(RichPanelGroupLayout invalidInputMsg) {
            this.invalidInputMsg = invalidInputMsg;
        }

        public RichPanelGroupLayout getInvalidInputMsg() {
            return invalidInputMsg;
        }

        public void setInvalidInputMsg3(RichPanelGroupLayout invalidInputMsg3) {
            this.invalidInputMsg3 = invalidInputMsg3;
        }

        public RichPanelGroupLayout getInvalidInputMsg3() {
            return invalidInputMsg3;
        }

        public void setMandatoryMsg(RichPanelGroupLayout mandatoryMsg) {
            this.mandatoryMsg = mandatoryMsg;
        }

        public RichPanelGroupLayout getMandatoryMsg() {
            return mandatoryMsg;
        }

        public void setMandatoryMsg3(RichPanelGroupLayout mandatoryMsg3) {
            this.mandatoryMsg3 = mandatoryMsg3;
        }

        public RichPanelGroupLayout getMandatoryMsg3() {
            return mandatoryMsg3;
        }

        public void setMandatoryMsg4(RichPanelGroupLayout mandatoryMsg4) {
            this.mandatoryMsg4 = mandatoryMsg4;
        }

        public RichPanelGroupLayout getMandatoryMsg4() {
            return mandatoryMsg4;
        }

        public void setMandatoryMsg1(RichPanelGroupLayout mandatoryMsg1) {
            this.mandatoryMsg1 = mandatoryMsg1;
        }

        public RichPanelGroupLayout getMandatoryMsg1() {
            return mandatoryMsg1;
        }
    }
}


package com.sfr.engage.alertstaskflow;

import com.sfr.core.bean.User;
import com.sfr.engage.core.AccountInfo;
import com.sfr.engage.core.AlertsSubscribeCustomerType;
import com.sfr.engage.core.AlertsSubscribeFrequencyType;
import com.sfr.engage.core.AlertsSubscribeRequest;
import com.sfr.engage.core.AlertsSubscribeResponse;
import com.sfr.engage.core.CardGroupInfo;
import com.sfr.engage.core.FuelTimings;

import com.sfr.engage.core.PartnerInfo;

import com.sfr.engage.model.queries.rvo.PrtCardFuelCapacityRVORowImpl;
import com.sfr.engage.model.queries.rvo.PrtCardRuleBusinessHoursRVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtAccountVORowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;

import com.sfr.util.ADFUtils;
import com.sfr.util.constants.Constants;

import com.sfr.util.validations.Conversion;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
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

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;


public class Alerts {
    private List<FuelTimings> fueltimings = new ArrayList<FuelTimings>();
    private List<FuelTimings> configureFuelTimings = new ArrayList<FuelTimings>();
    private RichPopup alert1Popup;

    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    private List<PartnerInfo> partnerInfoList;

    private List<SelectItem> partnerAlert1List;
    private List<SelectItem> configurePartnerAlert1List;
    private List<String> partnerValue = null;
    private String searchString;
    private boolean SelectionPanel = false;
    private boolean cardsSelectionPanel = false;
    
//    private List<SelectItem> partnerAlert2List;
//    private List<String> partnerValue2 = null;
//    
//    
//
//    private List<SelectItem> accountList = null;
//    private List<String> accountValue;
//    
//    private List<SelectItem> cardGroupList = null;
//    private List<String> cardGroupValue;
//    
//    private List<SelectItem> cardList = null;
//    private List<String> cardValue;
    
    
    private List<SelectItem> partnerIdList;
    private List<SelectItem> partnerIdList2;
    
    private List<SelectItem> configurePartnerIdList;
    
    
    private List<String> partnerIdValue;
    private List<String> partnerIdValue2;
    
    private List<String> configurePartnerIdValue;
    private List<String> configurePartnerIdValue2;
    
    private List<SelectItem> accountIdList;
    private List<String> accountIdValue;
    private List<SelectItem> accountIdList2;
    private List<String> accountIdValue2;
    
    private List<SelectItem> configureAccountIdList;
    private List<String> configureAccountIdValue;
    
    private List<String> initailAccountIdVAlue;
    private List<SelectItem> cardGroupList;
    private List<String> cardGroupValue;
    private List<SelectItem> cardGroupList2;
    private List<String> cardGroupValue2;
    
    private List<SelectItem> configureCardGroupList;
    private List<String> configureCardGroupValue;
    
    private List<String> initialCardGroupValue;
    private List<SelectItem> cardNumberList;
    private List<String> cardNumberValue;
    private List<SelectItem> cardNumberList2;
    private List<String> cardNumberValue2;
    
    private List<String> initialCardValue;
    
    private List<SelectItem> configureCardNumberList;
    private List<String> configureCardNumberValue;
    
    
    private RichPopup alert2Popup;
    private String langValue;
    AlertsSubscribeRequest req = new AlertsSubscribeRequest();
    AlertsSubscribeResponse response = new AlertsSubscribeResponse();
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
    private String userEmail;
    private String userFirstName;
    private String userMobileNo;
    private String CountryCode;
    private RichInputText fuelCapacityAlert2;
    private RichCommandButton okButtonAlert2;
    private RichCommandButton cancelButtonAlert2;
    private RichPanelGroupLayout successAlert2;
    private RichCommandButton closeButtonAlert2;
    EngageResourceBundle resourceBundle = new EngageResourceBundle();
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
    private String passingPartner = "";
    private String passingAccount = "";
    private String passingCardgrpMain = "";
    private String passingCardgrpSub = "";
    private String passingCardgrpSeq = "";
    private String passingCardKsId = "";
    private RichSelectManyChoice viewAlertDropdown;
    private RichSelectManyChoice viewAlertCardGroupDropdown;
    private RichSelectManyChoice viewAlertsPartnerDropdown;
    private RichSelectManyChoice viewAlertsAccountDropdown;


    public Alerts() {
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        partnerInfoList = new ArrayList<PartnerInfo>();
        partnerAlert1List = new ArrayList<SelectItem>();
  
        
        partnerValue = new ArrayList<String>();
       
        
        
        partnerIdList = new ArrayList<SelectItem>();
        partnerIdValue = new ArrayList<String>();
        
        partnerIdList2 = new ArrayList<SelectItem>();
        partnerIdValue2 = new ArrayList<String>();
        
        accountIdList = new ArrayList<SelectItem>();
        accountIdValue = new ArrayList<String>();
        accountIdList2 = new ArrayList<SelectItem>();
        accountIdValue2 = new ArrayList<String>();
        initailAccountIdVAlue = new ArrayList<String>();
        initialCardGroupValue = new ArrayList<String>();
        initialCardValue = new ArrayList<String>();
        cardGroupList = new ArrayList<SelectItem>();
        cardGroupValue = new ArrayList<String>();
        cardNumberList = new ArrayList<SelectItem>();
        cardNumberValue = new ArrayList<String>();
        cardGroupList2 = new ArrayList<SelectItem>();
        cardGroupValue2 = new ArrayList<String>();
        cardNumberList2 = new ArrayList<SelectItem>();
        cardNumberValue2 = new ArrayList<String>();
        suggestedCardNumberList = new ArrayList<String>();
        
        
        Conversion conv = new Conversion();
        
        
        if (session.getAttribute(Constants.DISPLAY_PORTAL_LANG) !=null) {
                        langValue = conv.getCustomerCountryCode((String)session.getAttribute(Constants.DISPLAY_PORTAL_LANG));
        }
        
        
        defaultTimings();
   
         
         
        userEmail = "";
        userFirstName = "";
        userMobileNo = "";
        CountryCode = "";
        if(session!=null && session.getAttribute(Constants.SESSION_USER_INFO)!=null)
        {
            User user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
            if(user.getEmailID()!=null && user.getFirstName()!=null)
            {
                userEmail = user.getEmailID(); 
                userFirstName = user.getFirstName();
                
            }
            if(user.getPhoneNumber()!=null)
            {userMobileNo = user.getPhoneNumber();}
            else {
                userMobileNo = "1234567890";
            }
        }
        
        if(session.getAttribute("partnerLang")!=null)
        {
        CountryCode = (String)session.getAttribute("partnerLang");
        }
                
        if (session.getAttribute("Partner_Object_List") != null) {
            System.out.println("partnerlist not null");
            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
        }

populateDefaultDropdown();
    }
    public void populateDefaultDropdown() {
        
        
        
        
        if (partnerInfoList != null && partnerInfoList.size() > 0) {
            for (int i = 0; i < partnerInfoList.size(); i++) {
                //lang = partnerInfoList.get(0).getCountry().toString().trim();
                if (partnerInfoList.get(i).getPartnerName() != null && partnerInfoList.get(i).getPartnerValue() != null) {
                    SelectItem selectItem = new SelectItem();
                    selectItem.setLabel(partnerInfoList.get(i).getPartnerName().toString());
                    selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString());
                    partnerIdList.add(selectItem);
                    partnerIdList2.add(selectItem);
                    partnerIdValue.add(partnerInfoList.get(i).getPartnerValue().toString());
                    partnerIdValue2.add(partnerInfoList.get(i).getPartnerValue().toString());
                    partnerAlert1List.add(selectItem);
                }

                if (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {
                    for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
                        if (partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() != null) {
                            SelectItem selectItem = new SelectItem();
                            selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString()+partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            accountIdList.add(selectItem);
                            accountIdList2.add(selectItem);
                            accountIdValue.add(partnerInfoList.get(i).getPartnerValue().toString()+partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            accountIdValue2.add(partnerInfoList.get(i).getPartnerValue().toString()+partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                        }
                        for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
                            if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() != null) {
                                SelectItem selectItem = new SelectItem();
                                selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() + partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString()+
                                                    partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                cardGroupList.add(selectItem);
                                cardGroupList2.add(selectItem);
                                cardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString()+
                                                   partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                cardGroupValue2.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString()+
                                                   partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                            }
                            
                            for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++){
                                if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                    partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() != null){
                                        SelectItem selectItem = new SelectItem();
                                        selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                        selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString()+
                                                   partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString()+ partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                        cardNumberList.add(selectItem);
                                        cardNumberList2.add(selectItem);
                                        cardNumberValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString()+
                                                   partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString()+partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                        cardNumberValue2.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString()+
                                                   partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString()+partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                        suggestedCardNumberList.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                    }
                            }
                        }
                    }
                }
            }
            initailAccountIdVAlue = accountIdValue;
            initialCardGroupValue = cardGroupValue;
            initialCardValue = cardNumberValue;
            System.out.println("Account id value length cons" + initailAccountIdVAlue.size());
            System.out.println("Cardgroup id value length cons" + initialCardGroupValue.size());
            System.out.println("Card id value length cons" + initialCardValue.size());

            Collections.sort(accountIdList, comparator);
            Collections.sort(cardGroupList, comparator);
            Collections.sort(cardNumberList, comparator);
            Collections.sort(accountIdList2, comparator);
            Collections.sort(cardGroupList2, comparator);
            Collections.sort(cardNumberList2, comparator);
        }
    }
    public void defaultTimings() {
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
    }
    
    public void configureDefaultTimings(String subscriptionId,String country) {
        configureFuelTimings = new ArrayList<FuelTimings>();
        configureFuelTimings.clear();
        
        
        DCBindingContainer bindings;

        bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
    


        DCIteratorBinding iter1;
        if (bindings != null &&
            bindings.findIteratorBinding("PrtCardRuleBusinessHoursRVO1Iterator") != null) {
            iter1 = bindings.findIteratorBinding("PrtCardRuleBusinessHoursRVO1Iterator");
        
        

        ViewObject businessHoursVO = iter1.getViewObject();
      
        businessHoursVO.setNamedWhereClauseParam("SubID",subscriptionId);
        
        businessHoursVO.setNamedWhereClauseParam("countryCode",country);

        businessHoursVO.executeQuery();

        FuelTimings f;
        if (businessHoursVO.getEstimatedRowCount() != 0) {
            while (businessHoursVO.hasNext()) {
                     f = new FuelTimings();
                
                PrtCardRuleBusinessHoursRVORowImpl currRow =
                    (PrtCardRuleBusinessHoursRVORowImpl)businessHoursVO.next();
                
        if (currRow != null) {
            if (currRow.getDay() != null && currRow.getBusiStartFrom() !=null && currRow.getBusiStartTo() != null) {
                System.out.println("currRow.getDay() " + currRow.getDay());
                if (resourceBundle.containsKey(currRow.getDay().toString().toUpperCase())) {
                    System.out.println("resourceBundle contains currRow.getDay()");
                    f.setWeekday((String)resourceBundle.getObject(currRow.getDay().toString().toUpperCase()));
                    System.out.println("f.setWeekday " + f.getWeekday());
                }
                f.setFromHh(currRow.getBusiStartFrom().toString().substring(0, currRow.getBusiStartFrom().toString().indexOf(":")));
                f.setFromMm(currRow.getBusiStartFrom().toString().substring(currRow.getBusiStartFrom().toString().indexOf(":")+1));
                f.setToHh(currRow.getBusiStartTo().toString().substring(0, currRow.getBusiStartTo().toString().indexOf(":")));
                f.setToMm(currRow.getBusiStartTo().toString().substring(currRow.getBusiStartTo().toString().indexOf(":")+1));
            }
        configureFuelTimings.add(f);
        }
            }
        }
            }
    }


    public void configureBusinessHoursAlert(ActionEvent actionEvent) {
        defaultTimings();
        System.out.println("fueltimings size " + fueltimings.size());
        AdfFacesContext.getCurrentInstance().addPartialTarget(alert1Table);
        alert1ValidData.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(alert1ValidData);
        alertSuccessProperty.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(alertSuccessProperty);
        alert1PartnerValues.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(alert1PartnerValues);
        fromTimingsHh.setReadOnly(true);
        fromTimingsMm.setReadOnly(true);
        toTimingsHh.setReadOnly(true);
        toTimingsMm.setReadOnly(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(fromTimingsHh);
        AdfFacesContext.getCurrentInstance().addPartialTarget(fromTimingsMm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(toTimingsHh);
        AdfFacesContext.getCurrentInstance().addPartialTarget(toTimingsMm);
        closeButtonAlert1.setRendered(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(closeButtonAlert1);
        okButtonAlert1.setVisible(true);
        cancelButtonAlert1.setVisible(true);
        editButtonAlert1.setVisible(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(okButtonAlert1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelButtonAlert1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(editButtonAlert1);
        alert1PartnerValues.setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(alert1PartnerValues);
        
        RichPopup.PopupHints ps = new RichPopup.PopupHints();
        alert1Popup.show(ps);
        
    }

    public void setFueltimings(List<FuelTimings> fueltimings) {
        this.fueltimings = fueltimings;
    }

    public List<FuelTimings> getFueltimings() {
        return fueltimings;
    }

    public void setAlert1Popup(RichPopup alert1Popup) {
        this.alert1Popup = alert1Popup;
    }

    public RichPopup getAlert1Popup() {
        return alert1Popup;
    }

    public void claoseAlert1Popup(ActionEvent actionEvent) {
 
        getAlert1Popup().hide();
        getAlert2Popup().hide();
    }

    public void editAlert1Timings(ActionEvent actionEvent) {
        
        fromTimingsHh.setReadOnly(false);
        fromTimingsMm.setReadOnly(false);
        toTimingsHh.setReadOnly(false);
        toTimingsMm.setReadOnly(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(fromTimingsHh);
        AdfFacesContext.getCurrentInstance().addPartialTarget(fromTimingsMm);
        AdfFacesContext.getCurrentInstance().addPartialTarget(toTimingsHh);
        AdfFacesContext.getCurrentInstance().addPartialTarget(toTimingsMm);
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

   

    public void setAlert2Popup(RichPopup alert2Popup) {
        this.alert2Popup = alert2Popup;
    }

    public RichPopup getAlert2Popup() {
        return alert2Popup;
    }

    public void configureFuelCapacityAlert(ActionEvent actionEvent) {
        
        
        System.out.println("cardNumberValue +  cardNumberList" + cardNumberValue.size() + " " + cardNumberList.size());
        System.out.println("cardGroupValue +  cardGroupList" + cardGroupValue.size() + " " + cardGroupList.size());
        System.out.println("accountIdValue +  accountIdList" + accountIdValue.size() + " " + accountIdList.size());
        System.out.println("partnerIdValue +  partnerIdList" + partnerIdValue.size() + " " + partnerIdList.size());
      //  if(cardNumberValue == null || cardNumberList == null || cardGroupValue == null || cardGroupList == null || accountIdValue == null || accountIdList == null || partnerIdValue == null || partnerIdList == null)  {
            System.out.println("populating default dropdown");
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
            
            populateDefaultDropdown();
        //}
        RichPopup.PopupHints ps = new RichPopup.PopupHints();
        alert2Popup.show(ps);
        successAlert2.setRendered(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(successAlert2);
        fuelCapacityAlert2.setDisabled(false);
        fuelCapacityAlert2.resetValue();
        okButtonAlert2.setRendered(true);
        cancelButtonAlert2.setRendered(true);
        ltrPerMonthRadio.setDisabled(false);
        ltrPerMonthRadio.resetValue();
        ltrPerWeekRadio.setDisabled(false);
        ltrPerWeekRadio.resetValue();
        ltrPerDayRadio.setDisabled(false);
        ltrPerDayRadio.resetValue();
        ltrPerDayRadio.setSelected(true);
        cardDropdownAlert2.setDisabled(false);
        cardDropdownAlert2.resetValue();
        cardGroupDowndownAlert2.setDisabled(false);
        cardGroupDowndownAlert2.resetValue();
        accountDropdwonAlert2.setDisabled(false);
        accountDropdwonAlert2.resetValue();
        partnerDropdownAlert2.setDisabled(false);
        partnerDropdownAlert2.resetValue();
        
        closeButtonAlert2.setRendered(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(fuelCapacityAlert2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(okButtonAlert2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelButtonAlert2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerMonthRadio);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerWeekRadio);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerDayRadio);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cardDropdownAlert2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cardGroupDowndownAlert2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(accountDropdwonAlert2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(partnerDropdownAlert2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(closeButtonAlert2);
        
        successAlert2.setRendered(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(successAlert2);
        alert2ValidData.setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(alert2ValidData);
    }

    public void setLangValue(String langValue) {
        this.langValue = langValue;
    }

    public String getLangValue() {
        return langValue;
    }

    public void setBusinessHoursAlert(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("inside alerts bean");

        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        
        if(validateinput())
        {
            
            
            
        if(bindings!=null) {
            
        
        OperationBinding operationBinding = bindings.getOperationBinding("subscribeAlerts");
        
            if(operationBinding!=null)
            {
              BigInteger bigint = new BigInteger("1");
              req.setRuleID(bigint);
              AlertsSubscribeCustomerType customerobj = new AlertsSubscribeCustomerType();
              customerobj.setEmailID(userEmail);
              customerobj.setCustomerID(userEmail);
              customerobj.setFIrstName(userFirstName);
              BigInteger bigint2 = new BigInteger(userMobileNo);
              customerobj.setMobileNumber(bigint2);
              req.setCustomer(customerobj);
              
              AlertsSubscribeFrequencyType freq = new AlertsSubscribeFrequencyType();
              freq.setScheduleFrequency("DAILY");
              
              req.setSubscribeFrequency(freq);
              req.setNotificationChannel("EMAIL");
              req.setNotificationFormat("EXCEL");
              
        operationBinding.getParamsMap().put("subscribeRequest", req);
        response = (AlertsSubscribeResponse)operationBinding.execute();
        
        
        
        System.out.println("response " + response);
        System.out.println("response = " +  response.getSubscriptionID());
        if(response.getSubscriptionID()!= null)
        {
           
            
        DCBindingContainer bindings2 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();    
        DCIteratorBinding CardRuleSubscriptionIter = bindings2.findIteratorBinding("PrtCardRuleSubscriptionVO1Iterator");
        DCIteratorBinding CardRuleBusinessHoursIter = bindings2.findIteratorBinding("PrtCardRuleBusinessHoursVO1Iterator");
         


                    if (CardRuleSubscriptionIter != null && CardRuleBusinessHoursIter!=null) {
                        
                        ViewObject cardRuleSubscription = CardRuleSubscriptionIter.getViewObject();  
                        ViewObject cardRuleBusinessHours = CardRuleBusinessHoursIter.getViewObject();  
                         
                        
                        Row cardRuleSubscriptionRow = cardRuleSubscription.createRow();
                        
                        
                        if(session.getAttribute("partnerLang")!=null)
                       {
                        cardRuleSubscriptionRow.setAttribute("CountryCode", session.getAttribute("partnerLang"));
                       }
                        
                        cardRuleSubscriptionRow.setAttribute("UserId", userEmail);
                        cardRuleSubscriptionRow.setAttribute("SubscrId",response.getSubscriptionID().toString().trim());
                        cardRuleSubscriptionRow.setAttribute("RuleId","1");
                        cardRuleSubscriptionRow.setAttribute("SubscrStatus","ACTIVE");
                        cardRuleSubscriptionRow.setAttribute("PartnerId", alert1PartnerValues.getValue().toString());
                        cardRuleSubscriptionRow.setAttribute("AccountId","ALL");
                        cardRuleSubscriptionRow.setAttribute("CardgroupMain","ALL");
                        cardRuleSubscriptionRow.setAttribute("CardgroupSub","ALL");
                        cardRuleSubscriptionRow.setAttribute("CardgroupSeq","ALL");
                        cardRuleSubscriptionRow.setAttribute("ModifiedBy", userEmail);
                        cardRuleSubscriptionRow.setAttribute("CardKsid", "ALL");
                        cardRuleSubscription.insertRow(cardRuleSubscriptionRow);
                        
                        RichTable rt = getAlert1Table();
                      
                        Object o;
                      FuelTimings checkFuelTimings;
                     
                      
                        

                        
                        for(int i=0;i<7;i++) {
                            
                            o = rt.getRowData(i);
                            checkFuelTimings = (FuelTimings) o;
                            
                            Row cardRuleBusinessHoursRow = cardRuleBusinessHours.createRow();
                          
                            if(session.getAttribute("partnerLang")!=null)
                            {
                            cardRuleBusinessHoursRow.setAttribute("CountryCode", session.getAttribute("partnerLang"));
                            
                            }
                          
                            cardRuleBusinessHoursRow.setAttribute("SubscrId", response.getSubscriptionID().toString().trim());
                            cardRuleBusinessHoursRow.setAttribute("RuleId", "1");
                            cardRuleBusinessHoursRow.setAttribute("Day", fueltimings.get(i).getWeekday());
                            cardRuleBusinessHoursRow.setAttribute("BusiStartFrom", checkFuelTimings.getFromHh().toString().trim()+":"+checkFuelTimings.getFromMm().toString().trim());
                            cardRuleBusinessHoursRow.setAttribute("BusiStartTo", checkFuelTimings.getToHh().toString().trim()+":"+checkFuelTimings.getToMm().toString().trim());
                            cardRuleBusinessHoursRow.setAttribute("ModifiedBy", userEmail);
                            cardRuleBusinessHours.insertRow(cardRuleBusinessHoursRow);
                            
                        }
                        
                        
                        operationBinding = bindings.getOperationBinding("Commit");
                        operationBinding.execute();
            
            
                        
                    }
                    
            alertSuccessProperty.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(alertSuccessProperty);
            alert1ValidData.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(alert1ValidData);
            closeButtonAlert1.setRendered(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(closeButtonAlert1);
            okButtonAlert1.setVisible(false);
            cancelButtonAlert1.setVisible(false);
            editButtonAlert1.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(okButtonAlert1);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cancelButtonAlert1);
            AdfFacesContext.getCurrentInstance().addPartialTarget(editButtonAlert1);
            alert1PartnerValues.setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(alert1PartnerValues);
            fromTimingsHh.setReadOnly(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(fromTimingsHh);
                fromTimingsMm.setReadOnly(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(fromTimingsMm);
                toTimingsHh.setReadOnly(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(toTimingsHh);
                toTimingsMm.setReadOnly(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(toTimingsMm);
            
        }
        


            }
        
        }
        
    }
        else {
            alertSuccessProperty.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(alertSuccessProperty);
            alert1ValidData.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(alert1ValidData);
            //showErrorMessage("Please Enter Valid Data");
        }
        
    }
    public String showErrorMessage(String errorVar) {
        System.out.println("throwing error message");
        if (errorVar != null) {
            if (resourceBundle.containsKey(errorVar)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject(errorVar), "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
        }
        return null;
    }
    
    public boolean validateinput() {
        boolean validinput = true;
        
        if(alert1PartnerValues.getValue()!=null && alert1PartnerValues.getValue().toString()!=null) {
            
            
            RichTable rt = getAlert1Table();
            Object o;
            FuelTimings checkFuelTimings;
            
            
            int fromHh;
            int fromMm;
            int toHh;
            int toMm;
                
            for(int i=0;i<7;i++) {
                
                o = rt.getRowData(i);
                checkFuelTimings = (FuelTimings) o;
                
                if(checkFuelTimings.getFromHh()!=null && checkFuelTimings.getFromHh().toString().trim()!=null && checkFuelTimings.getFromMm()!=null && checkFuelTimings.getFromMm().toString().trim()!=null && checkFuelTimings.getToHh()!=null && checkFuelTimings.getToHh().toString().trim()!=null && checkFuelTimings.getToMm()!=null && checkFuelTimings.getToMm().toString().trim()!=null) 
                {
                    String regex = "\\d+";
                    if(checkFuelTimings.getFromHh().toString().trim().matches(regex) && checkFuelTimings.getFromMm().toString().trim().matches(regex) && checkFuelTimings.getToHh().toString().trim().matches(regex) && checkFuelTimings.getToMm().toString().trim().matches(regex))
                   
                    {
                    fromHh = Integer.parseInt(checkFuelTimings.getFromHh().toString().trim());
                    fromMm = Integer.parseInt(checkFuelTimings.getFromMm().toString().trim());
                    toHh = Integer.parseInt(checkFuelTimings.getToHh().toString().trim());
                    toMm = Integer.parseInt(checkFuelTimings.getToMm().toString().trim());
                    
                    if(fromHh < 24 && toHh < 24 && fromMm < 60 && toMm <60) {
                        
                    }
                    else {
                        validinput = false;
                        break;
                    }
                    }
                    else {
                        validinput = false;
                        break;
                    }
                    
                
                    
                }else {
                    validinput = false;
                    break;
                }
                
                    
                    
                
            }
            
        }else {
            validinput = false;
        }
        
        return validinput;
    }
    
    public String populateStringValues(String var) {
        String passingValues = null;
        if (var != null) {
            String lovValues = var.trim();
            String selectedValues = lovValues.substring(1, lovValues.length() - 1);
            passingValues = selectedValues.trim();
            System.out.println("passingValues --> " + passingValues);
        }
        return passingValues;
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

    public void setPartnerAlert1List(List<SelectItem> partnerAlert1List) {
        this.partnerAlert1List = partnerAlert1List;
    }

    public List<SelectItem> getPartnerAlert1List() {
            

        return partnerAlert1List;
    }

 
    Comparator<SelectItem> comparator = new Comparator<SelectItem>() {
        @Override
        public int compare(SelectItem s1, SelectItem s2) {
            return s1.getLabel().compareTo(s2.getLabel());
        }
    };
    
    public String[] StringConversion(String passedVal) {
       // _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside StringConversion method of User Display");
        String[] val = passedVal.split(",");
      //  _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting StringConversion method of User Display");
        return val;
    }

    public void partnerValueChangeListener(ValueChangeEvent valueChangeEvent) {
       // _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside partnerValueChangeListener method of User Display");
        if (valueChangeEvent.getNewValue() != null) {
            accountIdList = new ArrayList<SelectItem>();
            accountIdValue = new ArrayList<String>();
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();
            cardNumberList = new ArrayList<SelectItem>();
            cardNumberValue = new ArrayList<String>();
            
            String[] partnerString = StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()));

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
                                        selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString()+partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                        accountIdList.add(selectItem);
                                        accountIdValue.add(partnerInfoList.get(i).getPartnerValue().toString()+partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                    }


                                    for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
                                        if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() != null) {
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                            selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() + partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                            cardGroupList.add(selectItem);
                                            cardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() + partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                               partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                        }
                                        
                                        for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++) {
                                            if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() != null){
                                                    SelectItem selectItem = new SelectItem();
                                                    selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                                    selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString()+partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString()+partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                    cardNumberList.add(selectItem);
                                                    cardNumberValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString()+partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString()+partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                }
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
                System.out.println("Account id value length partnerValueChangeListener" + initailAccountIdVAlue.size());
                System.out.println("Cardgroup id value length partnerValueChangeListener" + initialCardGroupValue.size());
                System.out.println("Card id value length partnerValueChangeListener" + initialCardValue.size());
                Collections.sort(accountIdList, comparator);
                Collections.sort(cardGroupList, comparator);
                Collections.sort(cardNumberList, comparator);
            }
        } else {
            accountDropdwonAlert2.setValue(null);
            cardDropdownAlert2.setValue(null);
            cardDropdownAlert2.setValue(null);
            this.accountIdValue = null;
            //System.out.println("Account id value length partnerelse" + accountIdValue.size());
            this.accountIdList = null;
            this.cardGroupValue = null;
            this.cardGroupList = null;
            this.cardNumberList = null;
            this.cardNumberValue = null;
        }
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(accountDropdwonAlert2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cardGroupDowndownAlert2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cardDropdownAlert2);
       
    }
    
    public void partner2ValueChangeListener(ValueChangeEvent valueChangeEvent) {
       // _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside partnerValueChangeListener method of User Display");
        if (valueChangeEvent.getNewValue() != null) {
            accountIdList2 = new ArrayList<SelectItem>();
            accountIdValue2 = new ArrayList<String>();
            cardGroupList2 = new ArrayList<SelectItem>();
            cardGroupValue2 = new ArrayList<String>();
            cardNumberList2 = new ArrayList<SelectItem>();
            cardNumberValue2 = new ArrayList<String>();
            
            String[] partnerString = StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()));

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
                                        selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString()+partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                        accountIdList2.add(selectItem);
                                        accountIdValue2.add(partnerInfoList.get(i).getPartnerValue().toString()+partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                    }


                                    for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
                                        if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() != null) {
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                            selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() + partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                            cardGroupList2.add(selectItem);
                                            cardGroupValue2.add(partnerInfoList.get(i).getPartnerValue().toString().trim() + partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString() +
                                                               partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                        }
                                        
                                        for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++) {
                                            if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() != null){
                                                    SelectItem selectItem = new SelectItem();
                                                    selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                                    selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString()+partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString()+partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                    cardNumberList2.add(selectItem);
                                                    cardNumberValue2.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString()+partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString()+partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
//                initailAccountIdVAlue = accountIdValue;
//                initialCardGroupValue = cardGroupValue;
//                initialCardValue = cardNumberValue;
//                System.out.println("Account id value length partnerValueChangeListener" + initailAccountIdVAlue.size());
//                System.out.println("Cardgroup id value length partnerValueChangeListener" + initialCardGroupValue.size());
//                System.out.println("Card id value length partnerValueChangeListener" + initialCardValue.size());
                Collections.sort(accountIdList2, comparator);
                Collections.sort(cardGroupList2, comparator);
                Collections.sort(cardNumberList2, comparator);
            }
        }
//        } else {
//            accountDropdwonAlert2.setValue(null);
//            cardDropdownAlert2.setValue(null);
//            cardDropdownAlert2.setValue(null);
//            this.accountIdValue = null;
//            //System.out.println("Account id value length partnerelse" + accountIdValue.size());
//            this.accountIdList = null;
//            this.cardGroupValue = null;
//            this.cardGroupList = null;
//            this.cardNumberList = null;
//            this.cardNumberValue = null;
//        }
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(viewAlertsAccountDropdown);
        AdfFacesContext.getCurrentInstance().addPartialTarget(viewAlertCardGroupDropdown);
        AdfFacesContext.getCurrentInstance().addPartialTarget(viewAlertDropdown);
       
    }
    
    public void account2ValueChangeListener(ValueChangeEvent valueChangeEvent) {
      
        if (valueChangeEvent.getNewValue() != null) {
            String[] accountString = StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()).replaceAll(" ", ""));
            
            System.out.println("accountlist");
            for(int y=0; y<accountString.length;y++)
                System.out.println(accountString[y]);
            cardGroupList2 = new ArrayList<SelectItem>();
            cardGroupValue2 = new ArrayList<String>();
            cardNumberList2 = new ArrayList<SelectItem>();
            cardNumberValue2 = new ArrayList<String>();
            
            for (int z = 0; z < partnerInfoList.size(); z++) {
                System.out.println("z " + z);
                if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                        System.out.println("i " + i);
                        for (int j = 0; j < accountString.length; j++) {
                            System.out.println("accc frmo partnerlist " + partnerInfoList.get(z).getAccountList().get(i).getAccountNumber());
                            System.out.println("acc comparing " + accountString[j].substring(8,18).trim());
                            System.out.println("checking bopolean " + partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().equals(accountString[j].substring(8,18).trim()));
                            if (partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() != null &&
                                partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().equals(accountString[j].substring(8,18).trim())) {
                                if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() > 0) {
                                    for (int k = 0; k < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size(); k++) {
                                        System.out.println("k " + k);
                                        if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID() != null) {
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                            selectItem.setValue(partnerInfoList.get(z).getPartnerValue().toString().trim() + partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+
                                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                            cardGroupList2.add(selectItem);
                                            
                                            cardGroupValue2.add(partnerInfoList.get(z).getPartnerValue().toString().trim() + partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+
                                                               partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                            System.out.println(partnerInfoList.get(z).getPartnerValue().toString().trim() + partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+
                                                               partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString() + "added in cardGroupValue");
                                        }
                                        
                                        for (int cc = 0; cc < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().size(); cc++) {
                                            if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getExternalCardID() != null){
                                                    SelectItem selectItem = new SelectItem();
                                                    selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                                    selectItem.setValue(partnerInfoList.get(z).getPartnerValue().toString().trim() +partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                    cardNumberList2.add(selectItem);
                                                    cardNumberValue2.add(partnerInfoList.get(z).getPartnerValue().toString().trim() +partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                    System.out.println(partnerInfoList.get(z).getPartnerValue().toString().trim() +partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString() + "added in cardNumberValue");
                                                }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                }
            }
            
//            initialCardValue = cardNumberValue;
//            initialCardGroupValue = cardGroupValue;
            Collections.sort(accountIdList2, comparator);
            Collections.sort(cardGroupList2, comparator);
            Collections.sort(cardNumberList2, comparator);
        }
    
//        } else {
//            cardGroupDowndownAlert2.setValue(null);
//            cardDropdownAlert2.setValue(null);
//            this.cardGroupValue = null;
//            this.cardNumberValue = null;
//        }
        
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(viewAlertsAccountDropdown);
        AdfFacesContext.getCurrentInstance().addPartialTarget(viewAlertCardGroupDropdown);
        AdfFacesContext.getCurrentInstance().addPartialTarget(viewAlertDropdown);
        
    }
    
    public void accountValueChangeListener(ValueChangeEvent valueChangeEvent) {
      
        if (valueChangeEvent.getNewValue() != null) {
            String[] accountString = StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()).replaceAll(" ", ""));
            
            System.out.println("accountlist");
            for(int y=0; y<accountString.length;y++)
                System.out.println(accountString[y]);
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();
            cardNumberList = new ArrayList<SelectItem>();
            cardNumberValue = new ArrayList<String>();
            
            for (int z = 0; z < partnerInfoList.size(); z++) {
                System.out.println("z " + z);
                if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                        System.out.println("i " + i);
                        for (int j = 0; j < accountString.length; j++) {
                            System.out.println("accc frmo partnerlist " + partnerInfoList.get(z).getAccountList().get(i).getAccountNumber());
                            System.out.println("acc comparing " + accountString[j].substring(8,18).trim());
                            System.out.println("checking bopolean " + partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().equals(accountString[j].substring(8,18).trim()));
                            if (partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() != null &&
                                partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().equals(accountString[j].substring(8,18).trim())) {
                                if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() > 0) {
                                    for (int k = 0; k < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size(); k++) {
                                        System.out.println("k " + k);
                                        if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID() != null) {
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                            selectItem.setValue(partnerInfoList.get(z).getPartnerValue().toString().trim() + partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+
                                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                            cardGroupList.add(selectItem);
                                            
                                            cardGroupValue.add(partnerInfoList.get(z).getPartnerValue().toString().trim() + partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+
                                                               partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                            System.out.println(partnerInfoList.get(z).getPartnerValue().toString().trim() + partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+
                                                               partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString() + "added in cardGroupValue");
                                        }
                                        
                                        for (int cc = 0; cc < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().size(); cc++) {
                                            if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getExternalCardID() != null){
                                                    SelectItem selectItem = new SelectItem();
                                                    selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                                    selectItem.setValue(partnerInfoList.get(z).getPartnerValue().toString().trim() +partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                    cardNumberList.add(selectItem);
                                                    cardNumberValue.add(partnerInfoList.get(z).getPartnerValue().toString().trim() +partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                    System.out.println(partnerInfoList.get(z).getPartnerValue().toString().trim() +partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString() + "added in cardNumberValue");
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
            cardGroupDowndownAlert2.setValue(null);
            cardDropdownAlert2.setValue(null);
            this.cardGroupValue = null;
            this.cardNumberValue = null;
        }
        
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(accountDropdwonAlert2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cardGroupDowndownAlert2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cardDropdownAlert2);
        
    }
    
    public void cardgroup2ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        
        if (valueChangeEvent.getNewValue() != null) {
            String[] cardgroupString = StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()).replaceAll(" ", ""));
            
            System.out.println("accountlist");
            for(int y=0; y<cardgroupString.length;y++)
                System.out.println(cardgroupString[y]);
            
            cardNumberList2 = new ArrayList<SelectItem>();
            cardNumberValue2 = new ArrayList<String>();
            
            for (int z = 0; z < partnerInfoList.size(); z++) {
                if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                        if(partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                           partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() > 0){
                            for(int cg = 0; cg < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size(); cg++){
                                for (int cgs = 0; cgs < cardgroupString.length; cgs++){
                                    System.out.println("cardgrp from partnerlist  " + partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString().trim());
                                    System.out.println("comparing cardgrp " + cardgroupString[cgs].substring(18, 29).trim());
                                    System.out.println("checkin boolean " + (partnerInfoList.get(z).getPartnerValue().toString().trim() +  partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() +
                                         partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString().trim()).equals(cardgroupString[cgs].trim()));
                                    if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                                        ((partnerInfoList.get(z).getPartnerValue().toString().trim() +  partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() +
                                         partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString().trim()).equals(cardgroupString[cgs].trim()))){
                                             for (int cc = 0; cc < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().size(); cc++) {
                                                if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID() != null &&
                                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getExternalCardID() != null){
                                                    SelectItem selectItem = new SelectItem();
                                                    selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
                                                    selectItem.setValue(partnerInfoList.get(z).getPartnerValue().toString().trim() +partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                                    cardNumberList2.add(selectItem);
                                                    cardNumberValue2.add(partnerInfoList.get(z).getPartnerValue().toString().trim() +partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                                }
                                             }
                                         }
                                }
                            }
                           }
                    }
//                    initialCardValue = cardNumberValue;
                    Collections.sort(cardGroupList2, comparator);
                    Collections.sort(cardNumberList2, comparator);
                }
            }
        }
//        } 
        else {
            cardDropdownAlert2.setValue(null);
            this.cardNumberValue = null;
        }
        
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(viewAlertsAccountDropdown);
        AdfFacesContext.getCurrentInstance().addPartialTarget(viewAlertCardGroupDropdown);
        AdfFacesContext.getCurrentInstance().addPartialTarget(viewAlertDropdown);
        
    }
  
  
    public void cardgroupValueChangeListener(ValueChangeEvent valueChangeEvent) {
        
        if (valueChangeEvent.getNewValue() != null) {
            String[] cardgroupString = StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()).replaceAll(" ", ""));
            
            System.out.println("accountlist");
            for(int y=0; y<cardgroupString.length;y++)
                System.out.println(cardgroupString[y]);
            
            cardNumberList = new ArrayList<SelectItem>();
            cardNumberValue = new ArrayList<String>();
            
            for (int z = 0; z < partnerInfoList.size(); z++) {
                if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                        if(partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                           partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() > 0){
                            for(int cg = 0; cg < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size(); cg++){
                                for (int cgs = 0; cgs < cardgroupString.length; cgs++){
                                    System.out.println("cardgrp from partnerlist  " + partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString().trim());
                                    System.out.println("comparing cardgrp " + cardgroupString[cgs].substring(18, 29).trim());
                                    System.out.println("checkin boolean " + (partnerInfoList.get(z).getPartnerValue().toString().trim() +  partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() +
                                         partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString().trim()).equals(cardgroupString[cgs].trim()));
                                    if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                                        ((partnerInfoList.get(z).getPartnerValue().toString().trim() +  partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() +
                                         partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString().trim()).equals(cardgroupString[cgs].trim()))){
                                             for (int cc = 0; cc < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().size(); cc++) {
                                                if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID() != null &&
                                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getExternalCardID() != null){
                                                    SelectItem selectItem = new SelectItem();
                                                    selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
                                                    selectItem.setValue(partnerInfoList.get(z).getPartnerValue().toString().trim() +partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                                    cardNumberList.add(selectItem);
                                                    cardNumberValue.add(partnerInfoList.get(z).getPartnerValue().toString().trim() +partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString()+partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
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
        } 
//        else {
//            cardDropdownAlert2.setValue(null);
//            this.cardNumberValue = null;
//        }
        
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(accountDropdwonAlert2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cardGroupDowndownAlert2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(cardDropdownAlert2);
        
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
       
        
//        fuelCapacityAlert2.setDisabled(true);
//        okButtonAlert2.setDisabled(true);
//        cancelButtonAlert2.setDisabled(true);
//        okButtonAlert2.setRendered(false);
//        cancelButtonAlert2.setRendered(false);
//        ltrPerDayRadio.setDisabled(true);
//        cardDropdownAlert2.setDisabled(true);
//        cardGroupDowndownAlert2.setDisabled(true);
//        accountDropdwonAlert2.setDisabled(true);
//        partnerDropdownAlert2.setDisabled(true);
//        successAlert2 .setRendered(false);
//        closeButtonAlert2.setRendered(false);
//        AdfFacesContext.getCurrentInstance().addPartialTarget(fuelCapacityAlert2);
//        AdfFacesContext.getCurrentInstance().addPartialTarget(okButtonAlert2);
//        AdfFacesContext.getCurrentInstance().addPartialTarget(cancelButtonAlert2);
//        AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerMonthRadio);
//        AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerWeekRadio);
//        AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerDayRadio);
//        AdfFacesContext.getCurrentInstance().addPartialTarget(cardDropdownAlert2);
//        AdfFacesContext.getCurrentInstance().addPartialTarget(cardGroupDowndownAlert2);
//        AdfFacesContext.getCurrentInstance().addPartialTarget(accountDropdwonAlert2);
//        AdfFacesContext.getCurrentInstance().addPartialTarget(partnerDropdownAlert2);
//        AdfFacesContext.getCurrentInstance().addPartialTarget(closeButtonAlert2);
//        
//        AdfFacesContext.getCurrentInstance().addPartialTarget(successAlert2);
    if(validateinput2())    
    {  
        System.out.println("inside alerts bean");
        String userEmail = "";
        String userFirstName = "";
        String userMobileNo = "";
        if(session!=null && session.getAttribute(Constants.SESSION_USER_INFO)!=null)
        {
            User user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
            if(user.getEmailID()!=null && user.getFirstName()!=null)
            {
                userEmail = user.getEmailID(); 
                userFirstName = user.getFirstName();
                
            }
            if(user.getPhoneNumber()!=null)
            {
            userMobileNo = user.getPhoneNumber();
            }
            else {
                userMobileNo = "9898989898";
            }
        }
        
        
       
       
        String cardListString = "";
        
                    for (String s : initialCardValue)
                    {
                        cardListString += s + ",";
                    }
        cardListString = cardListString.substring(0, cardListString.length()-1);
        
                System.out.println("converted cardListString arraylist " + cardListString);
                System.out.println("converted cardListString arraylist "  + populateStringValues(cardDropdownAlert2.getValue().toString().trim()));
                System.out.println("length1 " + cardListString.length());
                System.out.println("lenght2 " + (populateStringValues(cardDropdownAlert2.getValue().toString().trim()).replaceAll(" ", "")).length());
                
                
                
                if(cardListString.length()==(populateStringValues(cardDropdownAlert2.getValue().toString().trim()).replaceAll(" ", "")).length())
                {
                    System.out.println("card dropdown is unchanged");
                    readCardGroup();    
                }
                else 
                {
                    readCard();
                }
                
    
    }
    else {
       
        successAlert2.setRendered(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(successAlert2);
        alert2ValidData.setVisible(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(alert2ValidData);
    }
        
    }
    
    public boolean validateinput2() {
        boolean validinput2 = true;
        if(partnerDropdownAlert2.getValue()!=null && partnerDropdownAlert2.getValue().toString().trim().replaceAll(" ", "")!=null &&  partnerDropdownAlert2.getValue().toString().trim().replaceAll(" ", "")!="" && accountDropdwonAlert2.getValue()!=null && accountDropdwonAlert2.getValue().toString().trim().replaceAll(" ", "")!=null &&  accountDropdwonAlert2.getValue().toString().trim().replaceAll(" ", "")!="" && cardGroupDowndownAlert2.getValue()!=null && cardGroupDowndownAlert2.getValue().toString().trim().replaceAll(" ", "")!=null &&  cardGroupDowndownAlert2.getValue().toString().trim().replaceAll(" ", "")!="" && cardDropdownAlert2.getValue()!=null && cardDropdownAlert2.getValue().toString().trim().replaceAll(" ", "")!=null &&  cardDropdownAlert2.getValue().toString().trim().replaceAll(" ", "")!="" && fuelCapacityAlert2.getValue()!=null && fuelCapacityAlert2.getValue().toString().trim()!=null)
        {
            String regex = "\\d+";
           if( fuelCapacityAlert2.getValue().toString().trim().replaceAll(" ", "").matches(regex)) {
               System.out.println("fuelCapacityAlert2 " + fuelCapacityAlert2.getValue().toString().trim().replaceAll(" ", ""));
           }
           else {
               System.out.println("fuelCapacityAlert22 " + fuelCapacityAlert2.getValue().toString().trim().replaceAll(" ", ""));
               validinput2 = false;
             
           }
        }
        else {
            validinput2 = false;
        }
        
        return validinput2;
    }
    
    public void readCardGroup() {
        String cardGroupListString = "";
        System.out.println("Account id value length readCardGroup" + cardGroupValue.size());
        System.out.println("Account id value length readCardGroup" + initialCardGroupValue.size());
        
                    for (String s : initialCardGroupValue)
                    {
                        cardGroupListString += s + ",";
                    }
        cardGroupListString = cardGroupListString.substring(0, cardGroupListString.length()-1);
        
                System.out.println("converted cardGroupListString arraylist " + cardGroupListString);
                System.out.println("converted cardGroupListString arraylist "  + populateStringValues(cardGroupDowndownAlert2.getValue().toString().trim()));
                System.out.println("length1 " + cardGroupListString.length());
                System.out.println("lenght2 " + (populateStringValues(cardGroupDowndownAlert2.getValue().toString().trim()).replaceAll(" ", "")).length());
                
                
                
                if(cardGroupListString.length()==(populateStringValues(cardGroupDowndownAlert2.getValue().toString().trim()).replaceAll(" ", "")).length())
                { 
                    System.out.println("cardGroup dropdown is unchanged");
                    readAccount();
                }
                else 
                {
                    //logic to store in db at cardgroup level
                    
                    
                    BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();      
                            
                         if(bindings!=null) {
                             OperationBinding operationBinding = bindings.getOperationBinding("subscribeAlerts");
                             
                             
                                
                         
                          
                            
                                if(operationBinding!=null)
                                {
                                  BigInteger bigint = new BigInteger("1");
                                  req.setRuleID(bigint);
                                  AlertsSubscribeCustomerType customerobj = new AlertsSubscribeCustomerType();
                                  customerobj.setEmailID(userEmail);
                                  customerobj.setCustomerID(userEmail);
                                  customerobj.setFIrstName(userFirstName);
                                  BigInteger bigint2 = new BigInteger("9898989898");
                                  customerobj.setMobileNumber(bigint2);
                                  req.setCustomer(customerobj);
                                  
                                  AlertsSubscribeFrequencyType freq = new AlertsSubscribeFrequencyType();
                                  freq.setScheduleFrequency("DAILY");
                                  
                                  req.setSubscribeFrequency(freq);
                                  req.setNotificationChannel("EMAIL");
                                  req.setNotificationFormat("EXCEL");
                                  
                            
                            //response = (AlertsSubscribeResponse)operationBinding.execute();
                            
                            
                    //                        response.setSubscriptionID("136798");
                    //                        System.out.println("response " + response);
                    //                        System.out.println("response = " +  response.getSubscriptionID());
                           
                                
                                
                                
                                
                    //                            alertSuccessProperty.setVisible(true);
                    //                            AdfFacesContext.getCurrentInstance().addPartialTarget(alert1PopupDialog);
                                
                            DCBindingContainer bindings2 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();    
                            DCIteratorBinding CardRuleSubscriptionIter = bindings2.findIteratorBinding("PrtCardRuleSubscriptionVO1Iterator");
                    
                            DCIteratorBinding PrtCardFuelCapacityIter = bindings2.findIteratorBinding("PrtCardFuelCapacityVO1Iterator");  
                    
                                ViewObject cardRuleSubscription = CardRuleSubscriptionIter.getViewObject();   
                                ViewObject prtCardFuelCapacity = PrtCardFuelCapacityIter.getViewObject();
                                
                                        if (CardRuleSubscriptionIter != null && PrtCardFuelCapacityIter!=null) {
                                            
                                            
                                            //String partner[] = (populateStringValues(partnerDropdownAlert2.getValue().toString().trim()).replaceAll(" ", "")).split(",");
                                            String cardgroup[] = (populateStringValues(cardGroupDowndownAlert2.getValue().toString().trim()).replaceAll(" ", "")).split(",");
                                            String accountId = "";
                                            String partnerId = "";
                                            String cardgroupId = "";
                                            for(int cardgrp=0;cardgrp<cardgroup.length;cardgrp++)
                                            {
                                            operationBinding.getParamsMap().put("subscribeRequest", req);    
                                            response = (AlertsSubscribeResponse)operationBinding.execute();
                                            System.out.println("response = " +  response.getSubscriptionID());   
                                                
                                                if(response.getSubscriptionID()!=null)
                                                    
                                                {     
                                                Row cardRuleSubscriptionRow = cardRuleSubscription.createRow();
                                            
                                            
                                            
                                            cardRuleSubscriptionRow.setAttribute("CountryCode", CountryCode);
                                           
                                            
                                            cardRuleSubscriptionRow.setAttribute("UserId", userEmail);
                                            cardRuleSubscriptionRow.setAttribute("SubscrId",response.getSubscriptionID().toString().trim());
                                            cardRuleSubscriptionRow.setAttribute("RuleId","2");
                                            cardRuleSubscriptionRow.setAttribute("SubscrStatus","ACTIVE");
                                            
                                            partnerId = cardgroup[cardgrp].substring(0,8);
                                            accountId = cardgroup[cardgrp].substring(8,18);
                                            cardgroupId = cardgroup[cardgrp].substring(18);
                                            cardRuleSubscriptionRow.setAttribute("PartnerId", partnerId);
                                            cardRuleSubscriptionRow.setAttribute("AccountId",accountId);
                                            cardRuleSubscriptionRow.setAttribute("CardgroupMain",cardgroupId.substring(0, 3));
                                            cardRuleSubscriptionRow.setAttribute("CardgroupSub",cardgroupId.substring(3, 6));
                                            cardRuleSubscriptionRow.setAttribute("CardgroupSeq",cardgroupId.substring(6));
                                            cardRuleSubscriptionRow.setAttribute("ModifiedBy", userEmail);
                                            cardRuleSubscriptionRow.setAttribute("CardKsid", "ALL");
                                            cardRuleSubscription.insertRow(cardRuleSubscriptionRow);
                                                
                                            Row prtCardFuelCapacityRow = prtCardFuelCapacity.createRow();
                                            
                                            prtCardFuelCapacityRow.setAttribute("CountryCode", CountryCode);
                                            prtCardFuelCapacityRow.setAttribute("SubscrId", response.getSubscriptionID().toString().trim());
                                            prtCardFuelCapacityRow.setAttribute("RuleId", "2");
                                            
                                            if(ltrPerDayRadio.getValue()!=null && ltrPerDayRadio.getValue().toString().equalsIgnoreCase("true"))
                                            prtCardFuelCapacityRow.setAttribute("FuelPerDay", fuelCapacityAlert2.getValue().toString().trim().replaceAll(" ", ""));
                                            
                                            if(ltrPerWeekRadio.getValue()!=null && ltrPerWeekRadio.getValue().toString().equalsIgnoreCase("true"))
                                            prtCardFuelCapacityRow.setAttribute("FuelPerWeek", fuelCapacityAlert2.getValue().toString().trim().replaceAll(" ", ""));
                                            
                                            if(ltrPerMonthRadio.getValue()!=null && ltrPerMonthRadio.getValue().toString().equalsIgnoreCase("true"))
                                            prtCardFuelCapacityRow.setAttribute("FuelPerMonth", fuelCapacityAlert2.getValue().toString().trim().replaceAll(" ", ""));
                                            
                                            prtCardFuelCapacityRow.setAttribute("ModifiedBy", userEmail);
                                            //String seq = null;
                                           
                                            //prtCardFuelCapacityRow.setAttribute("RuleFuelCapId", seq);
                                                
                                            }
                                        }
                                            

                                            
                                            operationBinding = bindings.getOperationBinding("Commit");
                                            operationBinding.execute();
                                            
                                            
                                        }
                                        
                          
                                
                    
                    }
                         }
                    
                    alert2ValidData.setVisible(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(alert2ValidData); 
                    successAlert2.setRendered(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(successAlert2);
                    fuelCapacityAlert2.setDisabled(true);
                    okButtonAlert2.setRendered(false);
                    cancelButtonAlert2.setRendered(false);
                    ltrPerMonthRadio.setDisabled(true);
                    ltrPerWeekRadio.setDisabled(true);
                    ltrPerDayRadio.setDisabled(true);
                    cardDropdownAlert2.setDisabled(true);
                    cardGroupDowndownAlert2.setDisabled(true);
                    accountDropdwonAlert2.setDisabled(true);
                    partnerDropdownAlert2.setDisabled(true);
                    closeButtonAlert2.setRendered(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(fuelCapacityAlert2);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(okButtonAlert2);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(cancelButtonAlert2);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerMonthRadio);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerWeekRadio);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerDayRadio);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(cardDropdownAlert2);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(cardGroupDowndownAlert2);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(accountDropdwonAlert2);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(partnerDropdownAlert2);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(closeButtonAlert2);
                   
                    
                    
                    
                }
    }
    
    public void readCard() {
        //logic to store in db at card level
        
        System.out.println("Account id value length readCardGroup" + cardNumberList.size());
        System.out.println("Account id value length readCardGroup" + initialCardValue.size());
        
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();      
                
             if(bindings!=null) {
                 OperationBinding operationBinding = bindings.getOperationBinding("subscribeAlerts");
                 
                 
                    
             
              
                
                    if(operationBinding!=null)
                    {
                      BigInteger bigint = new BigInteger("1");
                      req.setRuleID(bigint);
                      AlertsSubscribeCustomerType customerobj = new AlertsSubscribeCustomerType();
                      customerobj.setEmailID(userEmail);
                      customerobj.setCustomerID(userEmail);
                      customerobj.setFIrstName(userFirstName);
                      BigInteger bigint2 = new BigInteger("9898989898");
                      customerobj.setMobileNumber(bigint2);
                      req.setCustomer(customerobj);
                      
                      AlertsSubscribeFrequencyType freq = new AlertsSubscribeFrequencyType();
                      freq.setScheduleFrequency("DAILY");
                      
                      req.setSubscribeFrequency(freq);
                      req.setNotificationChannel("EMAIL");
                      req.setNotificationFormat("EXCEL");
                      
                
                //response = (AlertsSubscribeResponse)operationBinding.execute();
                
                
        //                        response.setSubscriptionID("136798");
        //                        System.out.println("response " + response);
        //                        System.out.println("response = " +  response.getSubscriptionID());
               
                    
                    
                    
                    
        //                            alertSuccessProperty.setVisible(true);
        //                            AdfFacesContext.getCurrentInstance().addPartialTarget(alert1PopupDialog);
                    
                DCBindingContainer bindings2 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();    
                DCIteratorBinding CardRuleSubscriptionIter = bindings2.findIteratorBinding("PrtCardRuleSubscriptionVO1Iterator");
        
                DCIteratorBinding PrtCardFuelCapacityIter = bindings2.findIteratorBinding("PrtCardFuelCapacityVO1Iterator");  
        
                    ViewObject cardRuleSubscription = CardRuleSubscriptionIter.getViewObject();   
                    ViewObject prtCardFuelCapacity = PrtCardFuelCapacityIter.getViewObject();
                    
                            if (CardRuleSubscriptionIter != null && PrtCardFuelCapacityIter!=null) {
                                
                                
                                //String partner[] = (populateStringValues(partnerDropdownAlert2.getValue().toString().trim()).replaceAll(" ", "")).split(",");
                                String card[] = (populateStringValues(cardDropdownAlert2.getValue().toString().trim()).replaceAll(" ", "")).split(",");
                                String accountId = "";
                                String partnerId = "";
                                String cardgroupId = "";
                                String cardId = "";
                                for(int cardcc=0;cardcc<card.length;cardcc++)
                                {
                                operationBinding.getParamsMap().put("subscribeRequest", req);    
                                response = (AlertsSubscribeResponse)operationBinding.execute();
                                System.out.println("response = " +  response.getSubscriptionID());   
                                    
                                    if(response.getSubscriptionID()!=null)
                                        
                                    {     
                                    Row cardRuleSubscriptionRow = cardRuleSubscription.createRow();
                                
                                
                                
                                cardRuleSubscriptionRow.setAttribute("CountryCode", CountryCode);
                               
                                
                                cardRuleSubscriptionRow.setAttribute("UserId", userEmail);
                                cardRuleSubscriptionRow.setAttribute("SubscrId",response.getSubscriptionID().toString().trim());
                                cardRuleSubscriptionRow.setAttribute("RuleId","2");
                                cardRuleSubscriptionRow.setAttribute("SubscrStatus","ACTIVE");
                                
                                partnerId = card[cardcc].substring(0,8);
                                accountId = card[cardcc].substring(8,18);
                                cardgroupId = card[cardcc].substring(18,29);
                                cardId = card[cardcc].substring(29);
                                cardRuleSubscriptionRow.setAttribute("PartnerId", partnerId);
                                cardRuleSubscriptionRow.setAttribute("AccountId",accountId);
                                cardRuleSubscriptionRow.setAttribute("CardgroupMain",cardgroupId.substring(0, 3));
                                cardRuleSubscriptionRow.setAttribute("CardgroupSub",cardgroupId.substring(3, 6));
                                cardRuleSubscriptionRow.setAttribute("CardgroupSeq",cardgroupId.substring(6, 11));
                                cardRuleSubscriptionRow.setAttribute("ModifiedBy", userEmail);
                                cardRuleSubscriptionRow.setAttribute("CardKsid", cardId);
                                cardRuleSubscription.insertRow(cardRuleSubscriptionRow);
                                    
                                Row prtCardFuelCapacityRow = prtCardFuelCapacity.createRow();
                                
                                prtCardFuelCapacityRow.setAttribute("CountryCode", CountryCode);
                                prtCardFuelCapacityRow.setAttribute("SubscrId", response.getSubscriptionID().toString().trim());
                                prtCardFuelCapacityRow.setAttribute("RuleId", "2");
                                
                                if(ltrPerDayRadio.getValue()!=null && ltrPerDayRadio.getValue().toString().equalsIgnoreCase("true"))
                                prtCardFuelCapacityRow.setAttribute("FuelPerDay", fuelCapacityAlert2.getValue().toString().trim().replaceAll(" ", ""));
                                
                                if(ltrPerWeekRadio.getValue()!=null && ltrPerWeekRadio.getValue().toString().equalsIgnoreCase("true"))
                                prtCardFuelCapacityRow.setAttribute("FuelPerWeek", fuelCapacityAlert2.getValue().toString().trim().replaceAll(" ", ""));
                                
                                if(ltrPerMonthRadio.getValue()!=null && ltrPerMonthRadio.getValue().toString().equalsIgnoreCase("true"))
                                prtCardFuelCapacityRow.setAttribute("FuelPerMonth", fuelCapacityAlert2.getValue().toString().trim().replaceAll(" ", ""));
                                
                                prtCardFuelCapacityRow.setAttribute("ModifiedBy", userEmail);
                                //String seq = null;
                               
                                //prtCardFuelCapacityRow.setAttribute("RuleFuelCapId", seq);
                                    
                                }
                            }
                                

                                
                                operationBinding = bindings.getOperationBinding("Commit");
                                operationBinding.execute();
                                
                                
                            }
                            
              
                    
        
        }
               
                 alert2ValidData.setVisible(false);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(alert2ValidData);
                 
                 
                 successAlert2.setRendered(true);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(successAlert2);
                 fuelCapacityAlert2.setDisabled(true);
                 okButtonAlert2.setRendered(false);
                 cancelButtonAlert2.setRendered(false);
                 ltrPerMonthRadio.setDisabled(true);
                 ltrPerWeekRadio.setDisabled(true);
                 ltrPerDayRadio.setDisabled(true);
                 cardDropdownAlert2.setDisabled(true);
                 cardGroupDowndownAlert2.setDisabled(true);
                 accountDropdwonAlert2.setDisabled(true);
                 partnerDropdownAlert2.setDisabled(true);
                 
                 closeButtonAlert2.setRendered(true);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(fuelCapacityAlert2);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(okButtonAlert2);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(cancelButtonAlert2);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerMonthRadio);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerWeekRadio);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerDayRadio);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(cardDropdownAlert2);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(cardGroupDowndownAlert2);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(accountDropdwonAlert2);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(partnerDropdownAlert2);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(closeButtonAlert2);
             }
        
    }
    
    public void readAccount() {
        System.out.println("Account id value length readAccount" + accountIdValue.size());
        System.out.println("Account id value length readAccount" + initailAccountIdVAlue.size());
        String accountListString = "";
        
                    for (String s : initailAccountIdVAlue)
                    {
                        accountListString += s + ",";
                    }
        accountListString = accountListString.substring(0, accountListString.length()-1);
        
                System.out.println("converted accListString arraylist " + accountListString);
                System.out.println("converted accListString arraylist "  + (populateStringValues(accountDropdwonAlert2.getValue().toString().trim()).replaceAll(" ", "")));
        System.out.println("length1 " + accountListString.length());
        System.out.println("lenght2 " + (populateStringValues(accountDropdwonAlert2.getValue().toString().trim()).replaceAll(" ", "")).length());
        
                
                
                
                if(accountListString.length()==(populateStringValues(accountDropdwonAlert2.getValue().toString().trim()).replaceAll(" ", "")).length())
                {
                    System.out.println("account dropdown is unchanged");
                    readPartner();
                }
                else {
                    
                    //logic to store in db at account level
                           
                            
                    BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();      
                            
                         if(bindings!=null) {
                             OperationBinding operationBinding = bindings.getOperationBinding("subscribeAlerts");
                             
                             
                                
                         
                          
                            
                                if(operationBinding!=null)
                                {
                                  BigInteger bigint = new BigInteger("1");
                                  req.setRuleID(bigint);
                                  AlertsSubscribeCustomerType customerobj = new AlertsSubscribeCustomerType();
                                  customerobj.setEmailID(userEmail);
                                  customerobj.setCustomerID(userEmail);
                                  customerobj.setFIrstName(userFirstName);
                                  BigInteger bigint2 = new BigInteger("9898989898");
                                  customerobj.setMobileNumber(bigint2);
                                  req.setCustomer(customerobj);
                                  
                                  AlertsSubscribeFrequencyType freq = new AlertsSubscribeFrequencyType();
                                  freq.setScheduleFrequency("DAILY");
                                  
                                  req.setSubscribeFrequency(freq);
                                  req.setNotificationChannel("EMAIL");
                                  req.setNotificationFormat("EXCEL");
                                  
                            
                            //response = (AlertsSubscribeResponse)operationBinding.execute();
                            
                            
                    //                        response.setSubscriptionID("136798");
                    //                        System.out.println("response " + response);
                    //                        System.out.println("response = " +  response.getSubscriptionID());
                           
                                
                                
                                
                                
                    //                            alertSuccessProperty.setVisible(true);
                    //                            AdfFacesContext.getCurrentInstance().addPartialTarget(alert1PopupDialog);
                                
                            DCBindingContainer bindings2 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();    
                            DCIteratorBinding CardRuleSubscriptionIter = bindings2.findIteratorBinding("PrtCardRuleSubscriptionVO1Iterator");
                    
                            DCIteratorBinding PrtCardFuelCapacityIter = bindings2.findIteratorBinding("PrtCardFuelCapacityVO1Iterator");  
                    
                                ViewObject cardRuleSubscription = CardRuleSubscriptionIter.getViewObject();   
                                ViewObject prtCardFuelCapacity = PrtCardFuelCapacityIter.getViewObject();
                                
                                        if (CardRuleSubscriptionIter != null && PrtCardFuelCapacityIter!=null) {
                                            
                                            
                                            //String partner[] = (populateStringValues(partnerDropdownAlert2.getValue().toString().trim()).replaceAll(" ", "")).split(",");
                                            String account[] = (populateStringValues(accountDropdwonAlert2.getValue().toString().trim()).replaceAll(" ", "")).split(",");
                                            String accountId = "";
                                            String partnerId = "";
                                            for(int acc=0;acc<account.length;acc++)
                                            {
                                            operationBinding.getParamsMap().put("subscribeRequest", req);    
                                            response = (AlertsSubscribeResponse)operationBinding.execute();
                                            System.out.println("response = " +  response.getSubscriptionID());   
                                                
                                                if(response.getSubscriptionID()!=null)
                                                    
                                                {     
                                                Row cardRuleSubscriptionRow = cardRuleSubscription.createRow();
                                            
                                            
                                            
                                            cardRuleSubscriptionRow.setAttribute("CountryCode", CountryCode);
                                           
                                            
                                            cardRuleSubscriptionRow.setAttribute("UserId", userEmail);
                                            cardRuleSubscriptionRow.setAttribute("SubscrId",response.getSubscriptionID().toString().trim());
                                            cardRuleSubscriptionRow.setAttribute("RuleId","2");
                                            cardRuleSubscriptionRow.setAttribute("SubscrStatus","ACTIVE");
                                            
                                            partnerId = account[acc].substring(0,8);
                                            accountId = account[acc].substring(8);
                                            cardRuleSubscriptionRow.setAttribute("PartnerId", partnerId);
                                            cardRuleSubscriptionRow.setAttribute("AccountId",accountId);
                                            cardRuleSubscriptionRow.setAttribute("CardgroupMain","ALL");
                                            cardRuleSubscriptionRow.setAttribute("CardgroupSub","ALL");
                                            cardRuleSubscriptionRow.setAttribute("CardgroupSeq","ALL");
                                            cardRuleSubscriptionRow.setAttribute("ModifiedBy", userEmail);
                                            cardRuleSubscriptionRow.setAttribute("CardKsid", "ALL");
                                            cardRuleSubscription.insertRow(cardRuleSubscriptionRow);
                                                
                                            Row prtCardFuelCapacityRow = prtCardFuelCapacity.createRow();
                                            
                                            prtCardFuelCapacityRow.setAttribute("CountryCode", CountryCode);
                                            prtCardFuelCapacityRow.setAttribute("SubscrId", response.getSubscriptionID().toString().trim());
                                            prtCardFuelCapacityRow.setAttribute("RuleId", "2");
                                            
                                            if(ltrPerDayRadio.getValue()!=null && ltrPerDayRadio.getValue().toString().equalsIgnoreCase("true"))
                                            prtCardFuelCapacityRow.setAttribute("FuelPerDay", fuelCapacityAlert2.getValue().toString().trim().replaceAll(" ", ""));
                                            
                                            if(ltrPerWeekRadio.getValue()!=null && ltrPerWeekRadio.getValue().toString().equalsIgnoreCase("true"))
                                            prtCardFuelCapacityRow.setAttribute("FuelPerWeek", fuelCapacityAlert2.getValue().toString().trim().replaceAll(" ", ""));
                                            
                                            if(ltrPerMonthRadio.getValue()!=null && ltrPerMonthRadio.getValue().toString().equalsIgnoreCase("true"))
                                            prtCardFuelCapacityRow.setAttribute("FuelPerMonth", fuelCapacityAlert2.getValue().toString().trim().replaceAll(" ", ""));
                                            
                                            prtCardFuelCapacityRow.setAttribute("ModifiedBy", userEmail);
//                                            String seq = null;
//                                            seq = "1839"+acc;
//                                            prtCardFuelCapacityRow.setAttribute("RuleFuelCapId", seq);
                                                
                                            }
                                        }
                                            

                                            
                                            operationBinding = bindings.getOperationBinding("Commit");
                                            operationBinding.execute();
                                            
                                            
                                        }
                                        
                          
                                
                    
                    }
                         }
                    alert2ValidData.setVisible(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(alert2ValidData); 
                 successAlert2.setRendered(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(successAlert2);
                    fuelCapacityAlert2.setDisabled(true);
                    okButtonAlert2.setRendered(false);
                    cancelButtonAlert2.setRendered(false);
                    ltrPerMonthRadio.setDisabled(true);
                    ltrPerWeekRadio.setDisabled(true);
                    ltrPerDayRadio.setDisabled(true);
                    cardDropdownAlert2.setDisabled(true);
                    cardGroupDowndownAlert2.setDisabled(true);
                    accountDropdwonAlert2.setDisabled(true);
                    partnerDropdownAlert2.setDisabled(true);
                    
                    closeButtonAlert2.setRendered(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(fuelCapacityAlert2);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(okButtonAlert2);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(cancelButtonAlert2);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerMonthRadio);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerWeekRadio);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerDayRadio);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(cardDropdownAlert2);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(cardGroupDowndownAlert2);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(accountDropdwonAlert2);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(partnerDropdownAlert2);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(closeButtonAlert2);
                }
        
    }
    
    public void readPartner() {
        String partnerListString = "";
        
                    for (String s : partnerIdValue)
                    {
                        partnerListString += s + ",";
                    }
        partnerListString = partnerListString.substring(0, partnerListString.length()-1);
        
                System.out.println("converted partnerListString arraylist " + partnerListString);
                System.out.println("converted partnerListString arraylist "  + populateStringValues(partnerDropdownAlert2.getValue().toString().trim()));
                System.out.println("length1 " + partnerListString.length());
                System.out.println("lenght2 " + (populateStringValues(partnerDropdownAlert2.getValue().toString().trim()).replaceAll(" ", "")).length());
                
                
                
                if(partnerListString.length()==(populateStringValues(partnerDropdownAlert2.getValue().toString().trim()).replaceAll(" ", "")).length())
                
                {System.out.println("partner dropdown is unchanged");}
                //logic to store in db at partner level
                
                
               
               
           
                
                

                       
                        
                BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();      
                        
                     if(bindings!=null) {
                         OperationBinding operationBinding = bindings.getOperationBinding("subscribeAlerts");
                         
                         
                            
                     
                      
                        
                            if(operationBinding!=null)
                            {
                              BigInteger bigint = new BigInteger("1");
                              req.setRuleID(bigint);
                              AlertsSubscribeCustomerType customerobj = new AlertsSubscribeCustomerType();
                              customerobj.setEmailID(userEmail);
                              customerobj.setCustomerID(userEmail);
                              customerobj.setFIrstName(userFirstName);
                              BigInteger bigint2 = new BigInteger("9898989898");
                              customerobj.setMobileNumber(bigint2);
                              req.setCustomer(customerobj);
                              
                              AlertsSubscribeFrequencyType freq = new AlertsSubscribeFrequencyType();
                              freq.setScheduleFrequency("DAILY");
                              
                              req.setSubscribeFrequency(freq);
                              req.setNotificationChannel("EMAIL");
                              req.setNotificationFormat("EXCEL");
                              
                        
                        //response = (AlertsSubscribeResponse)operationBinding.execute();
                        
                        
//                        response.setSubscriptionID("136798");
//                        System.out.println("response " + response);
//                        System.out.println("response = " +  response.getSubscriptionID());
                       
                            
                            
                            
                            
//                            alertSuccessProperty.setVisible(true);
//                            AdfFacesContext.getCurrentInstance().addPartialTarget(alert1PopupDialog);
                            
                        DCBindingContainer bindings2 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();    
                        DCIteratorBinding CardRuleSubscriptionIter = bindings2.findIteratorBinding("PrtCardRuleSubscriptionVO1Iterator");
                
                        DCIteratorBinding PrtCardFuelCapacityIter = bindings2.findIteratorBinding("PrtCardFuelCapacityVO1Iterator");  
                
                            ViewObject cardRuleSubscription = CardRuleSubscriptionIter.getViewObject();   
                            ViewObject prtCardFuelCapacity = PrtCardFuelCapacityIter.getViewObject();
                            
                                    if (CardRuleSubscriptionIter != null && PrtCardFuelCapacityIter!=null) {
                                        
                                        
                                        String partner[] = (populateStringValues(partnerDropdownAlert2.getValue().toString().trim()).replaceAll(" ", "")).split(",");
                                        
                                        for(int part=0;part<partner.length;part++)
                                        {
                                        operationBinding.getParamsMap().put("subscribeRequest", req);    
                                        response = (AlertsSubscribeResponse)operationBinding.execute();
                                        System.out.println("response = " +  response.getSubscriptionID());   
                                            
                                            if(response.getSubscriptionID()!=null)
                                                
                                            {     
                                            Row cardRuleSubscriptionRow = cardRuleSubscription.createRow();
                                        
                                        
                                        
                                        cardRuleSubscriptionRow.setAttribute("CountryCode", CountryCode);
                                       
                                        
                                        cardRuleSubscriptionRow.setAttribute("UserId", userEmail);
                                        cardRuleSubscriptionRow.setAttribute("SubscrId",response.getSubscriptionID().toString().trim());
                                        cardRuleSubscriptionRow.setAttribute("RuleId","2");
                                        cardRuleSubscriptionRow.setAttribute("SubscrStatus","ACTIVE");
                                        cardRuleSubscriptionRow.setAttribute("PartnerId", partner[part]);
                                        cardRuleSubscriptionRow.setAttribute("AccountId","ALL");
                                        cardRuleSubscriptionRow.setAttribute("CardgroupMain","ALL");
                                        cardRuleSubscriptionRow.setAttribute("CardgroupSub","ALL");
                                        cardRuleSubscriptionRow.setAttribute("CardgroupSeq","ALL");
                                        cardRuleSubscriptionRow.setAttribute("ModifiedBy", userEmail);
                                        cardRuleSubscriptionRow.setAttribute("CardKsid", "ALL");
                                        cardRuleSubscription.insertRow(cardRuleSubscriptionRow);
                                            
                                        Row prtCardFuelCapacityRow = prtCardFuelCapacity.createRow();
                                        
                                        prtCardFuelCapacityRow.setAttribute("CountryCode", CountryCode);
                                        prtCardFuelCapacityRow.setAttribute("SubscrId", response.getSubscriptionID().toString().trim());
                                        prtCardFuelCapacityRow.setAttribute("RuleId", "2");
                                        
                                        if(ltrPerDayRadio.getValue()!=null && ltrPerDayRadio.getValue().toString().equalsIgnoreCase("true"))
                                        prtCardFuelCapacityRow.setAttribute("FuelPerDay", fuelCapacityAlert2.getValue().toString().trim().replaceAll(" ", ""));
                                        
                                        if(ltrPerWeekRadio.getValue()!=null && ltrPerWeekRadio.getValue().toString().equalsIgnoreCase("true"))
                                        prtCardFuelCapacityRow.setAttribute("FuelPerWeek", fuelCapacityAlert2.getValue().toString().trim().replaceAll(" ", ""));
                                        
                                        if(ltrPerMonthRadio.getValue()!=null && ltrPerMonthRadio.getValue().toString().equalsIgnoreCase("true"))
                                        prtCardFuelCapacityRow.setAttribute("FuelPerMonth", fuelCapacityAlert2.getValue().toString().trim().replaceAll(" ", ""));
                                        
                                        prtCardFuelCapacityRow.setAttribute("ModifiedBy", userEmail);
//                                        String seq = null;
//                                        seq = "1819"+part;
//                                        prtCardFuelCapacityRow.setAttribute("RuleFuelCapId", seq);
                                            
                                        }
                                    }
                                        

                                        
                                        operationBinding = bindings.getOperationBinding("Commit");
                                        operationBinding.execute();
                                        
                                        
                                    }
                                    
                      
                            
                
    }
                            
                         alert2ValidData.setVisible(false);
                         AdfFacesContext.getCurrentInstance().addPartialTarget(alert2ValidData); 
                         successAlert2.setRendered(true);
                         AdfFacesContext.getCurrentInstance().addPartialTarget(successAlert2);
                         fuelCapacityAlert2.setDisabled(true);
                         okButtonAlert2.setRendered(false);
                         cancelButtonAlert2.setRendered(false);
                         ltrPerMonthRadio.setDisabled(true);
                         ltrPerWeekRadio.setDisabled(true);
                         ltrPerDayRadio.setDisabled(true);
                         cardDropdownAlert2.setDisabled(true);
                         cardGroupDowndownAlert2.setDisabled(true);
                         accountDropdwonAlert2.setDisabled(true);
                         partnerDropdownAlert2.setDisabled(true);
                         
                         closeButtonAlert2.setRendered(true);
                         AdfFacesContext.getCurrentInstance().addPartialTarget(fuelCapacityAlert2);
                         AdfFacesContext.getCurrentInstance().addPartialTarget(okButtonAlert2);
                         AdfFacesContext.getCurrentInstance().addPartialTarget(cancelButtonAlert2);
                         AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerMonthRadio);
                         AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerWeekRadio);
                         AdfFacesContext.getCurrentInstance().addPartialTarget(ltrPerDayRadio);
                         AdfFacesContext.getCurrentInstance().addPartialTarget(cardDropdownAlert2);
                         AdfFacesContext.getCurrentInstance().addPartialTarget(cardGroupDowndownAlert2);
                         AdfFacesContext.getCurrentInstance().addPartialTarget(accountDropdwonAlert2);
                         AdfFacesContext.getCurrentInstance().addPartialTarget(partnerDropdownAlert2);
                         AdfFacesContext.getCurrentInstance().addPartialTarget(closeButtonAlert2);
                     }
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

    public void setCountryCode(String CountryCode) {
        this.CountryCode = CountryCode;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setFuelCapacityAlert2(RichInputText fuelCapacityAlert2) {
        this.fuelCapacityAlert2 = fuelCapacityAlert2;
    }

    public RichInputText getFuelCapacityAlert2() {
        return fuelCapacityAlert2;
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

    public void closeAlert1Popup(ActionEvent actionEvent) {
        // Add event code here...
        
        getAlert1Popup().hide();
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

    public void viewSubscribedAlerts(ActionEvent actionEvent) {
        
                                      
        ViewObject prtCardRuleSubscriptionVO = ADFUtils.getViewObject("PrtCardRuleSubscriptionRVO1Iterator");
        if(SelectionPanel)
        {
        if(!prtCardRuleSubscriptionVO.equals(null)){
            prtCardRuleSubscriptionVO.setNamedWhereClauseParam("pId", populateStringValues(viewAlertsPartnerDropdown.getValue().toString()));
            prtCardRuleSubscriptionVO.setNamedWhereClauseParam("userId", userEmail);
            prtCardRuleSubscriptionVO.setNamedWhereClauseParam("countryCode", CountryCode);
            String accountQuery = "(";
            String cardGroupQuery = "(";
            String cardQuery = "(";
            

            if (accountIdValue2.size() > 150) {
//                mapAccountListValue = valueList.callValueList(accountValue.size(), accountValue);
//                for (int i = 0; i < mapAccountListValue.size(); i++) {
//                    String values = "account" + i;
//                    accountQuery = accountQuery + "INSTR(:" + values + ",ACCOUNT_ID)<>0 OR ";
//                }
//                _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Account Query Values =" + accountQuery);
//                accountQuery = accountQuery.substring(0, accountQuery.length() - 3);
//                accountQuery = accountQuery + ")";

            } else {
                accountQuery = "INSTR(:account,PARTNER_ID||ACCOUNT_ID)<>0 OR (ACCOUNT_ID = 'ALL')) ";
                prtCardRuleSubscriptionVO.defineNamedWhereClauseParam("account", populateStringValues(viewAlertsAccountDropdown.getValue().toString()), null);
                

            }
            
            
            if (cardGroupValue2.size() > 150) {

//                        mapCardListValue = valueList.callValueList(cardValue.size(), cardValue);
//                        for (int i = 0; i < mapCardListValue.size(); i++) {
//                            String values = "card" + i;
//                            cardQuery = cardQuery + "INSTR(:" + values + ",INVOICED_CARD)<>0 OR ";
//                        }
//                        cardQuery = cardQuery.substring(0, cardQuery.length() - 3);
//                        cardQuery = cardQuery + ")";
//
//                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + "CARD Query Values =" + cardQuery);
//                        invoiceVO.setWhereClause(accountQuery + "AND " + cardQuery);
//                        for (int i = 0; i < mapCardListValue.size(); i++) {
//                            String values = "card" + i;
//                            String listName = "listName" + i;
//                            invoiceVO.defineNamedWhereClauseParam(values, mapCardListValue.get(listName), null);
//                        }


                    } else {
                
                        cardGroupQuery = "(INSTR(:cardGroup,PARTNER_ID||ACCOUNT_ID||CARDGROUP_MAIN||CARDGROUP_SUB||CARDGROUP_SEQ)<>0 OR ((CARDGROUP_MAIN = 'ALL') AND (CARDGROUP_SUB = 'ALL') AND (CARDGROUP_SEQ = 'ALL'))) ";
                        prtCardRuleSubscriptionVO.defineNamedWhereClauseParam("cardGroup", populateStringValues(viewAlertCardGroupDropdown.getValue().toString()), null);

                    
                    }

        

                    if (cardNumberValue2.size() > 150) {
//                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values > 150 ");
//                        mapCardGroupListValue = valueList.callValueList(cardGroupValue.size(), cardGroupValue);
//                        for (int i = 0; i < mapCardGroupListValue.size(); i++) {
//                            String values = "cardGroup" + i;
//                            cardGroupQuery =
//                                    cardGroupQuery + "INSTR(:" + values + ",PARTNER_ID||CARDGROUP_MAIN_TYPE||CARDGROUP_SUB_TYPE||CARDGROUP_SEQ)<>0 OR ";
//                        }
//                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + "CARDGROUP Query Values =" + cardGroupQuery);
//                        cardGroupQuery = cardGroupQuery.substring(0, cardGroupQuery.length() - 3);
//                        cardGroupQuery = cardGroupQuery + ")";
//                        invoiceVO.setWhereClause(accountQuery + "AND " + cardGroupQuery);
//                        for (int i = 0; i < mapCardGroupListValue.size(); i++) {
//                            String values = "cardGroup" + i;
//                            String listName = "listName" + i;
//                            invoiceVO.defineNamedWhereClauseParam(values, mapCardGroupListValue.get(listName), null);
//                        }

                    } else {
                        cardQuery = "(INSTR(:card,PARTNER_ID||ACCOUNT_ID||CARDGROUP_MAIN||CARDGROUP_SUB||CARDGROUP_SEQ||CARD_KSID)<>0 OR (CARD_KSID = 'ALL')";
                        String cardValuesList = populateStringValues(viewAlertDropdown.getValue().toString());
                        prtCardRuleSubscriptionVO.defineNamedWhereClauseParam("card", cardValuesList, null);
                        prtCardRuleSubscriptionVO.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + cardQuery);

                    }
                    System.out.println("Query: "+prtCardRuleSubscriptionVO.getQuery());
                    prtCardRuleSubscriptionVO.executeQuery();
                    System.out.println("Estimated row count for Card Rule Subscription Query:" + prtCardRuleSubscriptionVO.getEstimatedRowCount());
                    searchResultsPanel.setRendered(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(searchResultsPanel);

            }
    }
        else if(cardsSelectionPanel) {
            
            if(suggestedCardNumberList.contains(searchStringInputtext.getValue().toString().trim()))
            {
            searchCorrespondingDetalis();

            if(!prtCardRuleSubscriptionVO.equals(null)){
                prtCardRuleSubscriptionVO.setNamedWhereClauseParam("pId",passingPartner);
                prtCardRuleSubscriptionVO.setNamedWhereClauseParam("userId", userEmail);
                prtCardRuleSubscriptionVO.setNamedWhereClauseParam("countryCode", CountryCode);
                String cardQuery = "(INSTR(:card,PARTNER_ID||ACCOUNT_ID||CARDGROUP_MAIN||CARDGROUP_SUB||CARDGROUP_SEQ||CARD_KSID)<>0 OR (CARD_KSID = 'ALL')";
                String cardValuesList = passingPartner+passingAccount+passingCardgrpMain+passingCardgrpSub+passingCardgrpSeq+passingCardKsId;
                System.out.println("cardValuesList " +cardValuesList);
                prtCardRuleSubscriptionVO.defineNamedWhereClauseParam("card", cardValuesList, null);
                
            String accountQuery = "INSTR(:account,PARTNER_ID||ACCOUNT_ID)<>0 OR (ACCOUNT_ID = 'ALL')) ";
            String accountValueList = passingPartner+passingAccount;
            System.out.println("accountValueList " + accountValueList);
            prtCardRuleSubscriptionVO.defineNamedWhereClauseParam("account", accountValueList , null);
            
            String cardGroupQuery = "(INSTR(:cardGroup,PARTNER_ID||ACCOUNT_ID||CARDGROUP_MAIN||CARDGROUP_SUB||CARDGROUP_SEQ)<>0 OR ((CARDGROUP_MAIN = 'ALL') AND (CARDGROUP_SUB = 'ALL') AND (CARDGROUP_SEQ = 'ALL'))) ";
            String cardgrpValueList = passingPartner+passingAccount+passingCardgrpMain+passingCardgrpSub+passingCardgrpSeq;
            System.out.println("cardgrpValueList " + cardgrpValueList);
            prtCardRuleSubscriptionVO.defineNamedWhereClauseParam("cardGroup", cardgrpValueList, null);
                        
                        
                prtCardRuleSubscriptionVO.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + cardQuery);
                System.out.println("Query for card search : "+prtCardRuleSubscriptionVO.getQuery());
                prtCardRuleSubscriptionVO.executeQuery();
                System.out.println("Estimated row count for Card Rule Subscription Query in case of card selection :" + prtCardRuleSubscriptionVO.getEstimatedRowCount());
                searchResultsPanel.setRendered(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(searchResultsPanel);
        }
        }
        }
    }
    
    
    
    
    public void searchCorrespondingDetalis() {
        for (int i = 0; i < partnerInfoList.size(); i++) {
            if (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {
                passingPartner = partnerInfoList.get(i).getPartnerValue();
                for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
                    passingAccount = partnerInfoList.get(i).getAccountList().get(j).getAccountNumber();
                    for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
                        passingCardgrpMain = partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupMainType();
                        passingCardgrpSub = partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupSubType();
                        passingCardgrpSeq = partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupSeq();
                        for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++){
                            if( partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() != null &&
                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().equalsIgnoreCase(searchStringInputtext.getValue().toString().trim())){
                                passingCardKsId = partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID();
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
    
    

    public void setSearchResultsPanel(RichPanelGroupLayout searchResultsPanel) {
        this.searchResultsPanel = searchResultsPanel;
    }

    public RichPanelGroupLayout getSearchResultsPanel() {
        return searchResultsPanel;
    }

    public void viewConfiguredAlertPopup(ActionEvent actionEvent) {
        // Add event code here...
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("SubscrIdkey") != null) {
            System.out.println(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("SubscrIdkey").toString().trim());
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("RuleIdkey") != null) {
            System.out.println(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("RuleIdkey").toString().trim());
            if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("RuleIdkey").toString().trim().equalsIgnoreCase("1")) {
                configurePartnerAlert1List = new ArrayList<SelectItem>();
                configurePartnerIdValue = new ArrayList<String>();
                SelectItem selectItem = new SelectItem();
                 selectItem.setLabel(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("PartnerIdkey").toString().trim());
                 selectItem.setValue(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("PartnerIdkey").toString().trim());
                 //partnerIdList.add(selectItem);
                 configurePartnerIdValue.add(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("PartnerIdkey").toString().trim());
                 configurePartnerAlert1List.add(selectItem);
                configureDefaultTimings(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("SubscrIdkey").toString().trim(),CountryCode);
                configureAlert1PartnerValues.setValue(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("PartnerIdkey").toString().trim());
                RichPopup.PopupHints ps = new RichPopup.PopupHints();
                configureAlert1Popup.show(ps);
            }
            else
                if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("RuleIdkey").toString().trim().equalsIgnoreCase("2")) {
               
                configurePartnerIdList = new ArrayList<SelectItem>();
                configurePartnerIdValue2 = new ArrayList<String>();
                SelectItem selectItem = new SelectItem();
                 selectItem.setLabel(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("PartnerIdkey").toString().trim());
                 selectItem.setValue(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("PartnerIdkey").toString().trim());
                 //partnerIdList.add(selectItem);
                 configurePartnerIdValue2.add(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("PartnerIdkey").toString().trim());
                 configurePartnerIdList.add(selectItem);
                 
                 
                 
                configureAccountIdList = new ArrayList<SelectItem>();
                configureAccountIdValue = new ArrayList<String>();
                selectItem = new SelectItem();
                 selectItem.setLabel(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("AccountIdkey").toString().trim());
                 selectItem.setValue(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("AccountIdkey").toString().trim());
                 //partnerIdList.add(selectItem);
                 configureAccountIdValue.add(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("AccountIdkey").toString().trim());
                 configureAccountIdList.add(selectItem);
                 
                configureCardGroupList = new ArrayList<SelectItem>();
                configureCardGroupValue = new ArrayList<String>();
                selectItem = new SelectItem();
                if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("CardGroupkey").toString().trim().equals("ALLALLALL"))
                {
                 selectItem.setLabel("ALL");
                 selectItem.setValue("ALL");
                 //partnerIdList.add(selectItem);
                 configureCardGroupValue.add("ALL");
                 configureCardGroupList.add(selectItem);
                }
                
                configureCardNumberList = new ArrayList<SelectItem>();
                configureCardNumberValue = new ArrayList<String>();
                selectItem = new SelectItem();
               
                 selectItem.setLabel(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("CardKsidkey").toString().trim());
                 selectItem.setValue(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("CardKsidkey").toString().trim());
                 //partnerIdList.add(selectItem);
                 configureCardNumberValue.add(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("CardKsidkey").toString().trim());
                 configureCardNumberList.add(selectItem);

                
                
                fetchFuelCapacity(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("SubscrIdkey").toString().trim(),CountryCode);
                RichPopup.PopupHints ps = new RichPopup.PopupHints();
                configureAlert2Popup.show(ps);
                
                
                
            }
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("PartnerIdkey") != null) {
             System.out.println(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("PartnerIdkey").toString().trim());
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("AccountIdkey") != null) {
            System.out.println(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("AccountIdkey").toString().trim());
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("CardGroupkey") != null) {
            System.out.println(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("CardGroupkey").toString().trim());
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("CardKsidkey") != null) {
            System.out.println(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("CardKsidkey").toString().trim());
        }
    }
    
    
    
    public void fetchFuelCapacity(String subscriptionId, String country) {
        DCBindingContainer bindings;

        bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        


        DCIteratorBinding iter1;
        if (bindings != null &&
            bindings.findIteratorBinding("PrtCardFuelCapacityRVO1Iterator") != null) {
            iter1 = bindings.findIteratorBinding("PrtCardFuelCapacityRVO1Iterator");
        
        

        ViewObject fuelCapacityVO = iter1.getViewObject();
        
        fuelCapacityVO.setNamedWhereClauseParam("subID",subscriptionId);
        
        fuelCapacityVO.setNamedWhereClauseParam("countryCode",country);

        fuelCapacityVO.executeQuery();
                configureLtrPerDayRadio.setSelected(false);
                configureLtrPerWeekRadio.setSelected(false);
                configureLtrPerMonthRadio.setSelected(false);
        
        if (fuelCapacityVO.getEstimatedRowCount() != 0) {
            while (fuelCapacityVO.hasNext()) {
                
                
                PrtCardFuelCapacityRVORowImpl currRow =
                    (PrtCardFuelCapacityRVORowImpl)fuelCapacityVO.next();
                
        if (currRow != null) {
            if(currRow.getFuelPerDay()!=null) {
                configureLtrPerDayRadio.setSelected(true);
                configureFuelCapacityAlert2.setValue(currRow.getFuelPerDay());
            }
            else if(currRow.getFuelPerWeek()!=null) {
                configureLtrPerWeekRadio.setSelected(true);
                configureFuelCapacityAlert2.setValue(currRow.getFuelPerWeek());
            }else if(currRow.getFuelPerMonth() != null) {
                configureLtrPerMonthRadio.setSelected(true);
                configureFuelCapacityAlert2.setValue(currRow.getFuelPerMonth());
            }
            
        }
        
        
    }
        }
        
                AdfFacesContext.getCurrentInstance().addPartialTarget(configureLtrPerDayRadio);
                AdfFacesContext.getCurrentInstance().addPartialTarget(configureLtrPerWeekRadio);
                AdfFacesContext.getCurrentInstance().addPartialTarget(configureLtrPerMonthRadio);
                AdfFacesContext.getCurrentInstance().addPartialTarget(configureFuelCapacityAlert2);
            }
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

    public void editAlert1ConfigureTimings(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setBusinessHoursConfigureAlert(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void setOkButtonConfigureAlert1(RichCommandButton okButtonConfigureAlert1) {
        this.okButtonConfigureAlert1 = okButtonConfigureAlert1;
    }

    public RichCommandButton getOkButtonConfigureAlert1() {
        return okButtonConfigureAlert1;
    }

    public void claoseConfigureAlert1Popup(ActionEvent actionEvent) {
        // Add event code here...
        
        getConfigureAlert1Popup().hide();
        
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

    public void closeConfigureAlert1Popup(ActionEvent actionEvent) {
        // Add event code here...
        getConfigureAlert1Popup().hide();
    }

    public void setConfigurePartnerIdValue(List<String> configurePartnerIdValue) {
        this.configurePartnerIdValue = configurePartnerIdValue;
    }

    public List<String> getConfigurePartnerIdValue() {
        return configurePartnerIdValue;
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

    public void setConfigureFuelCapacityAlert(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void claoseConfigureAlert2Popup(ActionEvent actionEvent) {
        // Add event code here...
        getConfigureAlert2Popup().hide();
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

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchStringInputtext(RichInputText searchStringInputtext) {
        this.searchStringInputtext = searchStringInputtext;
    }

    public RichInputText getSearchStringInputtext() {
        return searchStringInputtext;
    }

    public List suggesstedItemsResult(String string) {
        // Add event code here...
        ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();
        System.out.println("size " + suggestedCardNumberList.size());
        for(int i = 0; i < suggestedCardNumberList.size(); i++){
            if(suggestedCardNumberList.get(i).toString().toUpperCase().contains(string)){
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(suggestedCardNumberList.get(i));
                selectItem.setValue(suggestedCardNumberList.get(i));
                selectItems.add(selectItem);
            }
        }
        return selectItems;
        
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

    public void mainSelectionPanelRadioListner(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
//        if(cardSelectionPanelRadio.getValue()!=null && cardSelectionPanelRadio.getValue().toString().equals(true)) {
//            SelectionPanel = false;
//            cardsSelectionPanel = true;
//            if(cardsSelectionPanel)
//            System.out.println("Search by card number");
//        }
//        else
        System.out.println("valueChangeEvent " + valueChangeEvent.getNewValue().toString());
            if(valueChangeEvent.getNewValue()!= null && valueChangeEvent.getNewValue().toString().equals("true")) {
            SelectionPanel = true;
            cardsSelectionPanel = false;
            if(SelectionPanel)
                System.out.println("Search by all");
        }
    }

    public void cardSelectionPanelRadioListner(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("valueChangeEvent " + valueChangeEvent.getNewValue().toString());
        if(valueChangeEvent.getNewValue()!=null && valueChangeEvent.getNewValue().toString().equals("true")) {
            SelectionPanel = false;
            cardsSelectionPanel = true;
            if(cardsSelectionPanel)
            System.out.println("Search by card number");
        }
//        else
//            if(mainSelectionPanelRadio.getValue().toString().equals(true)) {
//            SelectionPanel = true;
//            cardsSelectionPanel = false;
//            if(SelectionPanel)
//                System.out.println("Search by all");
//        }
    }


    public void setSelectionPanel(boolean SelectionPanel) {
        this.SelectionPanel = SelectionPanel;
    }

    public boolean isSelectionPanel() {
        return SelectionPanel;
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
}


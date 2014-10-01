package com.sfr.engage.userdisplaytaskflow;


import com.sfr.core.bean.BaseBean;
import com.sfr.core.bean.Roles;
import com.sfr.core.bean.User;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.core.UserInfoRolesDetails;
import com.sfr.engage.core.ValueListSplit;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.ADFUtils;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;
import com.sfr.util.validations.Conversion;
import com.sfr.util.validations.Validations;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanRadio;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.component.rich.output.RichSpacer;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;


public class UserInfoDisplayBean {
    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    public static final ADFLogger _logger = AccessDataControl.getSFRLogger();
    private AccessDataControl accessDC = new AccessDataControl();
    private List<SelectItem> partnerIdList;
    private List<String> partnerIdValue;
    private List<PartnerInfo> partnerInfoList;
    private List<SelectItem> accountIdList;
    private List<String> accountIdValue;
    private List<SelectItem> cardGroupList;
    private List<String> cardGroupValue;
    private List<SelectItem> cardNumberList;
    private List<String> cardNumberValue;
    private List<String> suggestedCardNumberList;
    private List<SelectItem> roleList;
    private List<String> roleValue;
    private String lang;
    private transient Bindings bindings;
    private ResourceBundle resourceBundle;
    private Boolean isTableVisible = false;
    private Boolean isCgMgrVisible = false;
    private Boolean isEmpVisible = false;
    private Boolean isAccMgrVisible = false;
    private Boolean isAdminVisible = false;
    private RichSelectBooleanRadio multipleCardRadio;
    private String searchString;
    private String accountQuery = "(";
    private String cardGroupQuery = "(";
    private String cardQuery = "(";
    private Map<String, String> mapAccountListValue;
    private Map<String, String> mapCardGroupListValue;
    private Map<String, String> mapCardListValue;
    private ValueListSplit valueList;

    private List<SelectItem> addEditUserPartnerID;
    private List<SelectItem> addEditUserAccountID;
    private List<SelectItem> addEditUserCardGroupID;
    private List<SelectItem> addEditCardID;
    private List<String> addEditPartnerValue;
    private List<String> addEditAccountValue;
    private List<String> addEditCardGroupValue;
    private List<String> addEditCardValue;
    private String userEmail = null;
    private boolean isCreateAdmin = false;
    private boolean isCreateACMgr = false;
    private boolean isCreateCGMgr = false;
    private boolean isCreateEmployee = false;
    private String addAdminInputValue = null;
    private String addACMgrInputValue = null;
    private String addCGMgrInputValue = null;
    private String addEmployeeInputValue = null;
    private List<UserInfoRolesDetails> userRoleDeatils;
    private String showPopUpHeading = null;
    private String showPopUpTableheading = null;

    private Map<String, String> partnerIdMap;
    //private Map<String, String> accountIdMap;
    private Map<String, String> cardGroupIdMap;
    private Map<String, String> cardIdMap;

    private Map<String, String> accountToPartnermap;
    private Map<String, String> cardToPartnerMap;
    private Map<String, String> cardGroupToPartnerMap;

    private Map<String, String> partnerNameMap;
    private Map<String, String> cardGroupDescMap;
    private Map<String, String> cardEmbossMap;
    private boolean showEmailpanel = false;
    private Integer roleCount = 0;
    private String rolesToCreate = "";
    private String warningMsg = "";
    private String emailValue = null;


    public UserInfoDisplayBean() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Constructor of User Display");
        resourceBundle = new EngageResourceBundle();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);


        //        partnerIdValue = new ArrayList<String>();
        roleValue = new ArrayList<String>();
        valueList = new ValueListSplit();
        roleValue.add("WCP_CARD_B2B_ADMIN");
        roleValue.add("WCP_CARD_B2B_MGR_AC");
        roleValue.add("WCP_CARD_B2B_MGR_CG");
        roleValue.add("WCP_CARD_B2B_EMP");

        userEmail = "";

        partnerIdMap = new HashMap<String, String>();
        //        accountIdMap = new HashMap<String, String>();
        cardGroupIdMap = new HashMap<String, String>();
        cardIdMap = new HashMap<String, String>();

        accountToPartnermap = new HashMap<String, String>();
        cardToPartnerMap = new HashMap<String, String>();
        cardGroupToPartnerMap = new HashMap<String, String>();

        partnerNameMap = new HashMap<String, String>();
        cardGroupDescMap = new HashMap<String, String>();
        cardEmbossMap = new HashMap<String, String>();

        populateCustomerData("Default");

        //        if (session.getAttribute("Partner_Object_List") != null) {
        //            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
        //            if (partnerInfoList != null && partnerInfoList.size() > 0) {
        //                partnerIdList = new ArrayList<SelectItem>();
        //                partnerIdValue = new ArrayList<String>();
        //                accountIdList = new ArrayList<SelectItem>();
        //                accountIdValue = new ArrayList<String>();
        //                cardGroupList = new ArrayList<SelectItem>();
        //                cardGroupValue = new ArrayList<String>();
        //                cardNumberList = new ArrayList<SelectItem>();
        //                cardNumberValue = new ArrayList<String>();
        //                suggestedCardNumberList = new ArrayList<String>();
        //                for (int i = 0; i < partnerInfoList.size(); i++) {
        //                    lang = partnerInfoList.get(0).getCountry().toString().trim();
        //                    if (partnerInfoList.get(i).getPartnerName() != null && partnerInfoList.get(i).getPartnerValue() != null) {
        //                        SelectItem selectItem = new SelectItem();
        //                        selectItem.setLabel(partnerInfoList.get(i).getPartnerName().toString());
        //                        selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString());
        //                        partnerIdList.add(selectItem);
        //                        partnerIdValue.add(partnerInfoList.get(i).getPartnerValue().toString());
        //                        addEditUserPartnerID.add(selectItem);
        //                        addEditPartnerValue.add(partnerInfoList.get(i).getPartnerValue().toString());
        //                        partnerIdMap.put(partnerInfoList.get(i).getPartnerName().toString(), partnerInfoList.get(i).getPartnerValue().toString());
        //                        partnerNameMap.put(partnerInfoList.get(i).getPartnerValue().toString(), partnerInfoList.get(i).getPartnerName().toString());
        //
        //                    }
        //
        //                    if (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {
        //                        for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
        //                            if (partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() != null) {
        //                                SelectItem selectItem = new SelectItem();
        //                                selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
        //                                selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
        //                                accountIdList.add(selectItem);
        //                                accountIdValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
        //                                addEditUserAccountID.add(selectItem);
        //                                addEditAccountValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
        //                                //                                accountIdMap.put(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString(),
        //                                //                                                 partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
        //                                accountToPartnermap.put(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString(),
        //                                                        partnerInfoList.get(i).getPartnerValue().toString());
        //
        //                            }
        //                            for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
        //                                if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() != null) {
        //                                    SelectItem selectItem = new SelectItem();
        //                                    selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
        //                                    selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
        //                                                        partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
        //                                    cardGroupList.add(selectItem);
        //                                    cardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
        //                                                       partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
        //                                    addEditUserCardGroupID.add(selectItem);
        //                                    addEditCardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
        //                                                              partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
        //                                    cardGroupIdMap.put(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString(),
        //                                                       partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
        //                                    cardGroupDescMap.put(partnerInfoList.get(i).getPartnerValue().toString().trim() +
        //                                                         partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString(),
        //                                                         partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
        //                                    cardGroupToPartnerMap.put(partnerInfoList.get(i).getPartnerValue().toString().trim() +
        //                                                              partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString(),
        //                                                              partnerInfoList.get(i).getPartnerValue().toString());
        //                                }
        //
        //                                for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++) {
        //                                    if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
        //                                        partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() != null) {
        //                                        SelectItem selectItem = new SelectItem();
        //                                        selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
        //                                        selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
        //                                        cardNumberList.add(selectItem);
        //                                        suggestedCardNumberList.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
        //                                        cardNumberValue.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
        //                                        addEditCardID.add(selectItem);
        //                                        addEditCardValue.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
        //                                        cardIdMap.put(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString(),
        //                                                      partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
        //                                        cardToPartnerMap.put(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString(),
        //                                                             partnerInfoList.get(i).getPartnerValue().toString());
        //                                        cardEmbossMap.put(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString(),
        //                                                          partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
        //                                    }
        //                                }
        //                            }
        //                        }
        //                    }
        //                }
        //
        //                Collections.sort(accountIdList, comparator);
        //                Collections.sort(cardGroupList, comparator);
        //                Collections.sort(cardNumberList, comparator);
        //
        //                Collections.sort(addEditUserAccountID, comparator);
        //                Collections.sort(addEditUserCardGroupID, comparator);
        //                Collections.sort(addEditCardID, comparator);
        //            }
        //        }

        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting Constructor of User Display");
    }

    Comparator<SelectItem> comparator = new Comparator<SelectItem>() {
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

    public void populateCustomerData(String changeValues) {

        if (session.getAttribute("Partner_Object_List") != null) {
            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
            if (partnerInfoList != null && partnerInfoList.size() > 0) {
                if (changeValues.equals("Default")) {
                    partnerIdList = new ArrayList<SelectItem>();
                    partnerIdValue = new ArrayList<String>();
                    accountIdList = new ArrayList<SelectItem>();
                    accountIdValue = new ArrayList<String>();
                    cardGroupList = new ArrayList<SelectItem>();
                    cardGroupValue = new ArrayList<String>();
                    cardNumberList = new ArrayList<SelectItem>();
                    cardNumberValue = new ArrayList<String>();
                    suggestedCardNumberList = new ArrayList<String>();
                } else {

                    addEditUserPartnerID = new ArrayList<SelectItem>();
                    addEditUserAccountID = new ArrayList<SelectItem>();
                    addEditUserCardGroupID = new ArrayList<SelectItem>();
                    addEditCardID = new ArrayList<SelectItem>();

                    addEditPartnerValue = new ArrayList<String>();
                    addEditAccountValue = new ArrayList<String>();
                    addEditCardGroupValue = new ArrayList<String>();
                    addEditCardValue = new ArrayList<String>();
                }


                for (int i = 0; i < partnerInfoList.size(); i++) {
                    lang = partnerInfoList.get(0).getCountry().toString().trim();
                    if (partnerInfoList.get(i).getPartnerName() != null && partnerInfoList.get(i).getPartnerValue() != null) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.setLabel(partnerInfoList.get(i).getPartnerName().toString());
                        selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString());
                        if (changeValues.equals("Default")) {
                            partnerIdList.add(selectItem);
                            partnerIdValue.add(partnerInfoList.get(i).getPartnerValue().toString());
                            partnerIdMap.put(partnerInfoList.get(i).getPartnerName().toString(), partnerInfoList.get(i).getPartnerValue().toString());
                            partnerNameMap.put(partnerInfoList.get(i).getPartnerValue().toString(), partnerInfoList.get(i).getPartnerName().toString());
                        } else {
                            addEditUserPartnerID.add(selectItem);
                            addEditPartnerValue.add(partnerInfoList.get(i).getPartnerValue().toString());
                        }

                    }

                    if (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {
                        for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
                            if (partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() != null) {
                                SelectItem selectItem = new SelectItem();
                                selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                if (changeValues.equals("Default")) {
                                    accountIdList.add(selectItem);
                                    accountIdValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                    accountToPartnermap.put(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString(),
                                                            partnerInfoList.get(i).getPartnerValue().toString());

                                    //                                accountIdMap.put(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString(),
                                    //                                                 partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                } else {
                                    addEditUserAccountID.add(selectItem);
                                    addEditAccountValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());

                                }

                            }
                            for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
                                if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() != null) {
                                    SelectItem selectItem = new SelectItem();
                                    selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                    selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                        partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                    if (changeValues.equals("Default")) {
                                        cardGroupList.add(selectItem);
                                        cardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                           partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                        cardGroupIdMap.put(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString(),
                                                           partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                        cardGroupDescMap.put(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                             partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString(),
                                                             partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                        cardGroupToPartnerMap.put(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString(),
                                                                  partnerInfoList.get(i).getPartnerValue().toString());
                                    } else {
                                        addEditUserCardGroupID.add(selectItem);
                                        addEditCardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                                  partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                    }
                                }

                                for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++) {
                                    if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                        partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() != null) {
                                        SelectItem selectItem = new SelectItem();
                                        selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                        selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                        if (changeValues.equals("Default")) {
                                            cardNumberList.add(selectItem);
                                            suggestedCardNumberList.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                            cardNumberValue.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                            cardIdMap.put(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString(),
                                                          partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                            cardToPartnerMap.put(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString(),
                                                                 partnerInfoList.get(i).getPartnerValue().toString());
                                            cardEmbossMap.put(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString(),
                                                              partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                        } else {
                                            addEditCardID.add(selectItem);
                                            addEditCardValue.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (changeValues.equals("Default")) {
                    Collections.sort(accountIdList, comparator);
                    Collections.sort(cardGroupList, comparator);
                    Collections.sort(cardNumberList, comparator);
                } else {

                    Collections.sort(addEditUserAccountID, comparator);
                    Collections.sort(addEditUserCardGroupID, comparator);
                    Collections.sort(addEditCardID, comparator);
                }
            }
        }
    }

    public String populateStringValues(String var) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside populateStringValues method of User Display");
        String passingValues = null;
        if (var != null) {
            String lovValues = var.trim();
            String selectedValues = lovValues.substring(1, lovValues.length() - 1);
            passingValues = selectedValues.trim();
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting populateStringValues method of User Display");
        return passingValues;
    }

    public String[] StringConversion(String passedVal) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside StringConversion method of User Display");
        String[] val = passedVal.split(",");
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting StringConversion method of User Display");
        return val;
    }

    public void partnerValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside partnerValueChangeListener method of User Display");
        isTableVisible = false;
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
                                        selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                        accountIdList.add(selectItem);
                                        accountIdValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
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
                                                cardNumberList.add(selectItem);
                                                cardNumberValue.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
                Collections.sort(accountIdList, comparator);
                Collections.sort(cardGroupList, comparator);
                Collections.sort(cardNumberList, comparator);
            }
        } else {
            getBindings().getAccount().setValue(null);
            getBindings().getCardGroup().setValue(null);
            getBindings().getCard().setValue(null);
            this.accountIdValue = null;
            this.accountIdList = null;
            this.cardGroupValue = null;
            this.cardGroupList = null;
            this.cardNumberList = null;
            this.cardNumberValue = null;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside partnerValueChangeListener method of User Display");
    }

    public void popUpPartnerValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside popup partnerValueChangeListener method of User Display");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null) {
            addEditUserAccountID = new ArrayList<SelectItem>();
            addEditUserCardGroupID = new ArrayList<SelectItem>();
            addEditCardID = new ArrayList<SelectItem>();
            addEditAccountValue = new ArrayList<String>();
            addEditCardGroupValue = new ArrayList<String>();
            addEditCardValue = new ArrayList<String>();

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
                                        selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                        addEditUserAccountID.add(selectItem);
                                        addEditAccountValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                    }


                                    for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
                                        if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() != null) {
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                            selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                            addEditUserCardGroupID.add(selectItem);
                                            addEditCardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                                      partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                        }

                                        for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++) {
                                            if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() !=
                                                null) {
                                                SelectItem selectItem = new SelectItem();
                                                selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                                selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                addEditCardID.add(selectItem);
                                                addEditCardValue.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
                Collections.sort(addEditUserAccountID, comparator);
                Collections.sort(addEditUserCardGroupID, comparator);
                Collections.sort(addEditCardID, comparator);
            }
            getBindings().getShowErrorMsg().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
        } else {
            getBindings().getAddEditAccountId().setValue(null);
            getBindings().getAddEditCardGroupId().setValue(null);
            getBindings().getAddEditCardId().setValue(null);
            this.addEditAccountValue = null;
            this.addEditUserAccountID = null;
            this.addEditCardGroupValue = null;
            this.addEditUserCardGroupID = null;
            this.addEditCardID = null;
            this.addEditCardValue = null;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside pop up partnerValueChangeListener method of User Display");
    }

    public void accountValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside accountValueChangeListener method of User Display");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null) {
            String[] accountString = StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()));
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();
            cardNumberList = new ArrayList<SelectItem>();
            cardNumberValue = new ArrayList<String>();

            for (int z = 0; z < partnerInfoList.size(); z++) {
                if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                        for (int j = 0; j < accountString.length; j++) {
                            if (partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() != null &&
                                partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().equals(accountString[j].trim())) {
                                if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() > 0) {
                                    for (int k = 0; k < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size(); k++) {
                                        if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID() != null) {
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                            selectItem.setValue(partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                            cardGroupList.add(selectItem);
                                            cardGroupValue.add(partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                               partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                        }

                                        for (int cc = 0; cc < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().size(); cc++) {
                                            if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getExternalCardID() !=
                                                null) {
                                                SelectItem selectItem = new SelectItem();
                                                selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                                selectItem.setValue(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                cardNumberList.add(selectItem);
                                                cardNumberValue.add(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Collections.sort(accountIdList, comparator);
                    Collections.sort(cardGroupList, comparator);
                    Collections.sort(cardNumberList, comparator);
                }
            }

        } else {
            getBindings().getCardGroup().setValue(null);
            getBindings().getCard().setValue(null);
            this.cardGroupValue = null;
            this.cardNumberValue = null;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside accountValueChangeListener method of User Display");
    }

    public void popUpAccountValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside popup accountValueChangeListener method of User Display");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null) {
            String[] accountString = StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()));
            addEditUserCardGroupID = new ArrayList<SelectItem>();
            addEditCardID = new ArrayList<SelectItem>();
            addEditCardGroupValue = new ArrayList<String>();
            addEditCardValue = new ArrayList<String>();

            for (int z = 0; z < partnerInfoList.size(); z++) {
                if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                        for (int j = 0; j < accountString.length; j++) {
                            if (partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() != null &&
                                partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().equals(accountString[j].trim())) {
                                if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() > 0) {
                                    for (int k = 0; k < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size(); k++) {
                                        if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID() != null) {
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                            selectItem.setValue(partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                            addEditUserCardGroupID.add(selectItem);
                                            addEditCardGroupValue.add(partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                                      partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                        }

                                        for (int cc = 0; cc < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().size(); cc++) {
                                            if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getExternalCardID() !=
                                                null) {
                                                SelectItem selectItem = new SelectItem();
                                                selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                                selectItem.setValue(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                addEditCardID.add(selectItem);
                                                addEditCardValue.add(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Collections.sort(addEditUserAccountID, comparator);
                    Collections.sort(addEditUserCardGroupID, comparator);
                    Collections.sort(addEditCardID, comparator);
                }
            }
            getBindings().getShowErrorMsg().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());

        } else {
            getBindings().getAddEditCardGroupId().setValue(null);
            getBindings().getAddEditCardId().setValue(null);
            this.addEditCardGroupValue = null;
            this.addEditCardValue = null;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside popup accountValueChangeListener method of User Display");
    }

    public void cardgroupValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside cardgroupValueChangeListener method of User Display");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null) {
            String[] cardgroupString = StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()));
            cardNumberList = new ArrayList<SelectItem>();
            cardNumberValue = new ArrayList<String>();

            for (int z = 0; z < partnerInfoList.size(); z++) {
                if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                        if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                            partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() > 0) {
                            for (int cg = 0; cg < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size(); cg++) {
                                for (int cgs = 0; cgs < cardgroupString.length; cgs++) {
                                    if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                                        (partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                         partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString().trim()).equals(cardgroupString[cgs].trim())) {
                                        for (int cc = 0; cc < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().size(); cc++) {
                                            if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID() != null &&
                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getExternalCardID() !=
                                                null) {
                                                SelectItem selectItem = new SelectItem();
                                                selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
                                                selectItem.setValue(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                                cardNumberList.add(selectItem);
                                                cardNumberValue.add(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Collections.sort(cardGroupList, comparator);
                    Collections.sort(cardNumberList, comparator);
                }
            }
        } else {
            getBindings().getCard().setValue(null);
            this.cardNumberValue = null;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside cardgroupValueChangeListener method of User Display");
    }

    public void popUpCardgroupValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside pop up cardgroupValueChangeListener method of User Display");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null) {
            String[] cardgroupString = StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()));
            addEditCardID = new ArrayList<SelectItem>();
            addEditCardValue = new ArrayList<String>();

            for (int z = 0; z < partnerInfoList.size(); z++) {
                if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                        if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                            partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() > 0) {
                            for (int cg = 0; cg < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size(); cg++) {
                                for (int cgs = 0; cgs < cardgroupString.length; cgs++) {
                                    if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                                        (partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                         partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString().trim()).equals(cardgroupString[cgs].trim())) {
                                        for (int cc = 0; cc < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().size(); cc++) {
                                            if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID() != null &&
                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getExternalCardID() !=
                                                null) {
                                                SelectItem selectItem = new SelectItem();
                                                selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
                                                selectItem.setValue(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                                addEditCardID.add(selectItem);
                                                addEditCardValue.add(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Collections.sort(addEditUserCardGroupID, comparator);
                    Collections.sort(addEditCardID, comparator);
                }
            }
            getBindings().getShowErrorMsg().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
        } else {
            getBindings().getAddEditCardId().setValue(null);
            this.addEditCardValue = null;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside pop up cardgroupValueChangeListener method of User Display");
    }

    public void multicardRadioValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside multicardRadioValueChangeListener method of User Display");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().equals(true)) {
            getBindings().getPartner().setDisabled(false);
            getBindings().getAccount().setDisabled(false);
            getBindings().getCardGroup().setDisabled(false);
            getBindings().getCard().setDisabled(false);
            getBindings().getRole().setDisabled(false);
            getBindings().getSearchStringInputtext().resetValue();
            getBindings().getSearchStringInputtext().setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartner());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRole());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchStringInputtext());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
        } else if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().equals(false)) {
            getBindings().getPartner().setDisabled(true);
            getBindings().getAccount().setDisabled(true);
            getBindings().getCardGroup().setDisabled(true);
            getBindings().getCard().setDisabled(true);
            getBindings().getRole().setDisabled(true);
            getBindings().getSearchStringInputtext().setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartner());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRole());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchStringInputtext());
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside multicardRadioValueChangeListener method of User Display");
    }

    public void singlecardRadioValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside singlecardRadioValueChangeListener method of User Display");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().equals(true)) {
            getBindings().getPartner().setDisabled(true);
            getBindings().getAccount().setDisabled(true);
            getBindings().getCardGroup().setDisabled(true);
            getBindings().getCard().setDisabled(true);
            getBindings().getRole().setDisabled(true);
            getBindings().getSearchStringInputtext().setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartner());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRole());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchStringInputtext());
        } else if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().equals(false)) {
            getBindings().getPartner().setDisabled(false);
            getBindings().getAccount().setDisabled(false);
            getBindings().getCardGroup().setDisabled(false);
            getBindings().getCard().setDisabled(false);
            getBindings().getRole().setDisabled(false);
            getBindings().getSearchStringInputtext().resetValue();
            getBindings().getSearchStringInputtext().setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartner());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRole());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchStringInputtext());
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside singlecardRadioValueChangeListener method of User Display");
    }

    public void roleValueChangeListener(ValueChangeEvent valueChangeEvent) {
        isTableVisible = false;
    }

    public List<SelectItem> getRoleList() {
        if (roleList == null) {
            roleList = new ArrayList<SelectItem>();
            SelectItem selectItem = new SelectItem();
            if (resourceBundle.containsKey("B2B_ADMIN")) {
                selectItem.setLabel(resourceBundle.getObject("B2B_ADMIN").toString());
                selectItem.setValue("WCP_CARD_B2B_ADMIN");
                roleList.add(selectItem);
            }
            SelectItem selectItem1 = new SelectItem();
            if (resourceBundle.containsKey("B2B_MANAGER_AC")) {
                selectItem1.setLabel(resourceBundle.getObject("B2B_MANAGER_AC").toString());
                selectItem1.setValue("WCP_CARD_B2B_MGR_AC");
                roleList.add(selectItem1);
            }
            SelectItem selectItem2 = new SelectItem();
            if (resourceBundle.containsKey("B2B_MANAGER_CG")) {
                selectItem2.setLabel(resourceBundle.getObject("B2B_MANAGER_CG").toString());
                selectItem2.setValue("WCP_CARD_B2B_MGR_CG");
                roleList.add(selectItem2);
            }
            SelectItem selectItem3 = new SelectItem();
            if (resourceBundle.containsKey("B2B_EMPLOYEE")) {
                selectItem3.setLabel(resourceBundle.getObject("B2B_EMPLOYEE").toString());
                selectItem3.setValue("WCP_CARD_B2B_EMP");
                roleList.add(selectItem3);
            }
        }
        return roleList;
    }

    /**
     * @param errorVar
     * @return
     */
    public String showErrorMessage(String errorVar) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside showErrorMessage method of User Dispaly");
        if (errorVar != null) {
            if (resourceBundle.containsKey(errorVar)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject(errorVar), "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting showErrorMessage method of User Dispaly");
        return null;
    }

    public String showErrorMessagePopup(String errorVar) {
        String errorMsg = "";
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside showErrorMessage method of User Dispaly");
        if (errorVar != null) {

            if (resourceBundle.containsKey(errorVar)) {
                errorMsg = resourceBundle.getObject(errorVar).toString();
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting showErrorMessage method of User Dispaly");
        return errorMsg;
    }

    public List suggestedSearchString(String string) {
        ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (int i = 0; i < suggestedCardNumberList.size(); i++) {
            if (suggestedCardNumberList.get(i).toString().toUpperCase().contains(string)) {
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(suggestedCardNumberList.get(i));
                selectItem.setValue(suggestedCardNumberList.get(i));
                selectItems.add(selectItem);
            }
        }
        return selectItems;
    }

    public void searchResult(ActionEvent actionEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside searchResult method of User Display");
        showSearchResults();
        //        isTableVisible = true;
        //        isCgMgrVisible = false;
        //        isEmpVisible = false;
        //        isAccMgrVisible = false;
        //        isAdminVisible = false;
        //        boolean resultDisplay = false;
        //
        //        try {
        //            if (getBindings().getMultipleCardRadio().getValue().equals(true) && getBindings().getSingleCardRadio().getValue().equals(false)) {
        //                if (getBindings().getPartner().getValue() != null) {
        //                    if (getBindings().getAccount().getValue() != null) {
        //                    } else {
        //                        showErrorMessage("ENGAGE_NO_ACCOUNT");
        //                    }
        //
        //                    if (getBindings().getCardGroup().getValue() != null) {
        //                    } else {
        //                        showErrorMessage("ENGAGE_NO_CARD_GROUP");
        //                    }
        //
        //                    if (getBindings().getCard().getValue() != null) {
        //                    } else {
        //                        showErrorMessage("ENGAGE_NO_CARD");
        //                    }
        //
        //                    if (getBindings().getRole().getValue() != null) {
        //                    } else {
        //                        showErrorMessage("ENGAGE_NO_ROLE");
        //                    }
        //
        //                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_ADMIN")) {
        //                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForAdminRVO1Iterator");
        //                        vo.setNamedWhereClauseParam("cc", lang);
        //                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_ADMIN);
        //                        vo.setNamedWhereClauseParam("PID", populateStringValues(getBindings().getPartner().getValue().toString().trim()));
        //                        vo.executeQuery();
        //                        isTableVisible = true;
        //                        resultDisplay = true;
        //                    }
        //                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_MGR_AC")) {
        //                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForAccMgrRVO1Iterator");
        //                        if (accountQuery.length() > 1 && accountQuery != null) {
        //                            if (vo.getWhereClause() != null) {
        //                                if (accountQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
        //                                    if (mapAccountListValue != null) {
        //                                        for (int i = 0; i < mapAccountListValue.size(); i++) {
        //                                            String values = "account" + i;
        //                                            vo.removeNamedWhereClauseParam(values);
        //                                        }
        //                                    } else {
        //                                        vo.removeNamedWhereClauseParam("account");
        //                                    }
        //                                    vo.setWhereClause("");
        //                                    vo.executeQuery();
        //                                }
        //                            }
        //                        }
        //                        accountQuery = "(";
        //                        vo.setNamedWhereClauseParam("cc", lang);
        //                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_MGR);
        //                        vo.setNamedWhereClauseParam("PID", populateStringValues(getBindings().getPartner().getValue().toString()));
        //                        if (accountIdValue.size() > 150) {
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
        //                            mapAccountListValue = valueList.callValueList(accountIdValue.size(), accountIdValue);
        //                            for (int i = 0; i < mapAccountListValue.size(); i++) {
        //                                String values = "account" + i;
        //                                accountQuery = accountQuery + "INSTR(:" + values + ",ACCOUNT_ID)<>0 OR ";
        //                            }
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Account Query Values =" + accountQuery);
        //                            accountQuery = accountQuery.substring(0, accountQuery.length() - 3);
        //                            accountQuery = accountQuery + ")";
        //                        } else {
        //                            mapAccountListValue = null;
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
        //                            accountQuery = "INSTR(:account,ACCOUNT_ID)<>0  ";
        //                        }
        //                        vo.setWhereClause(accountQuery);
        //                        String passingRole = "WCP_CARD_B2B_MGR";
        //
        //                        if (accountIdValue.size() > 150) {
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
        //                            mapAccountListValue = valueList.callValueList(accountIdValue.size(), accountIdValue);
        //                            for (int i = 0; i < mapAccountListValue.size(); i++) {
        //                                String values = "account" + i;
        //                                String listName = "listName" + i;
        //                                vo.defineNamedWhereClauseParam(values, mapAccountListValue.get(listName), null);
        //                            }
        //                        } else {
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
        //                            vo.defineNamedWhereClauseParam("account", populateStringValues(getBindings().getAccount().getValue().toString()), null);
        //                        }
        //
        //                        vo.executeQuery();
        //                        session.setAttribute("user_display_account_Query", accountQuery);
        //                        session.setAttribute("user_display_map_Account_List", mapAccountListValue);
        //                        isTableVisible = true;
        //                        resultDisplay = true;
        //                    }
        //                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_MGR_CG")) {
        //
        //                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForCgMgrRVO1Iterator");
        //                        if (cardGroupQuery.length() > 1 && cardGroupQuery != null) {
        //                            if (vo.getWhereClause() != null) {
        //                                if (cardGroupQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
        //                                    if (mapCardGroupListValue != null) {
        //                                        for (int i = 0; i < mapCardGroupListValue.size(); i++) {
        //                                            String values = "cardGroup" + i;
        //                                            vo.removeNamedWhereClauseParam(values);
        //                                        }
        //                                    } else {
        //                                        vo.removeNamedWhereClauseParam("cardGroup");
        //                                    }
        //                                    vo.setWhereClause("");
        //                                    vo.executeQuery();
        //                                }
        //                            }
        //                        }
        //                        cardGroupQuery = "(";
        //
        //                        if (cardGroupValue.size() > 150) {
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values > 150 ");
        //                            mapCardGroupListValue = valueList.callValueList(cardGroupValue.size(), cardGroupValue);
        //                            for (int i = 0; i < mapCardGroupListValue.size(); i++) {
        //                                String values = "cardGroup" + i;
        //                                cardGroupQuery = cardGroupQuery + "INSTR(:" + values + ",PARTNER_ID||CARD_GROUP)<>0 OR ";
        //                            }
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + "CARDGROUP Query Values =" + cardGroupQuery);
        //                            cardGroupQuery = cardGroupQuery.substring(0, cardGroupQuery.length() - 3);
        //                            cardGroupQuery = cardGroupQuery + ")";
        //                        } else {
        //                            mapCardGroupListValue = null;
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 ");
        //                            cardGroupQuery = "INSTR(:cardGroup,PARTNER_ID||CARD_GROUP)<>0 ";
        //                        }
        //
        //                        vo.setWhereClause(cardGroupQuery);
        //                        vo.setNamedWhereClauseParam("cc", lang);
        //                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_MGR);
        //                        vo.setNamedWhereClauseParam("PID", populateStringValues(getBindings().getPartner().getValue().toString()));
        //                        if (cardGroupValue.size() > 150) {
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values > 150 ");
        //                            mapCardGroupListValue = valueList.callValueList(cardGroupValue.size(), cardGroupValue);
        //                            for (int i = 0; i < mapCardGroupListValue.size(); i++) {
        //                                String values = "cardGroup" + i;
        //                                String listName = "listName" + i;
        //                                vo.defineNamedWhereClauseParam(values, mapCardGroupListValue.get(listName), null);
        //                            }
        //                        } else {
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 ");
        //                            vo.defineNamedWhereClauseParam("cardGroup", populateStringValues(getBindings().getCardGroup().getValue().toString()), null);
        //                        }
        //
        //                        vo.executeQuery();
        //                        session.setAttribute("user_display_cardGroup_Query", cardGroupQuery);
        //                        session.setAttribute("user_display_map_CardGroup_List", mapCardGroupListValue);
        //                        isTableVisible = true;
        //                        resultDisplay = true;
        //                    }
        //                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_EMP")) {
        //
        //                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForEmpRVO1Iterator");
        //                        if (cardQuery.length() > 1 && cardQuery != null) {
        //                            if (vo.getWhereClause() != null) {
        //                                if (cardQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
        //                                    if (mapCardListValue != null) {
        //                                        for (int i = 0; i < mapCardListValue.size(); i++) {
        //                                            String values = "card" + i;
        //                                            vo.removeNamedWhereClauseParam(values);
        //                                        }
        //                                    } else {
        //                                        vo.removeNamedWhereClauseParam("card");
        //                                    }
        //                                    vo.setWhereClause("");
        //                                    vo.executeQuery();
        //                                }
        //                            }
        //                        }
        //                        cardQuery = "(";
        //
        //                        if (cardNumberValue.size() > 150) {
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values > 150 ");
        //                            mapCardListValue = valueList.callValueList(cardNumberValue.size(), cardNumberValue);
        //                            for (int i = 0; i < mapCardListValue.size(); i++) {
        //                                String values = "card" + i;
        //                                cardQuery = cardQuery + "INSTR(:" + values + ",CARD)<>0 OR ";
        //                            }
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + "CARD Query Values =" + cardQuery);
        //                            cardQuery = cardQuery.substring(0, cardQuery.length() - 3);
        //                            cardQuery = cardQuery + ")";
        //                        } else {
        //                            mapCardListValue = null;
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values < 150 ");
        //                            cardQuery = "INSTR(:card,CARD)<>0 ";
        //                        }
        //                        vo.setWhereClause(cardQuery);
        //                        vo.setNamedWhereClauseParam("cc", lang);
        //                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_EMP);
        //                        vo.setNamedWhereClauseParam("PID", populateStringValues(getBindings().getPartner().getValue().toString()));
        //                        if (cardNumberValue.size() > 150) {
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values > 150 ");
        //                            mapCardListValue = valueList.callValueList(cardNumberValue.size(), cardNumberValue);
        //                            for (int i = 0; i < mapCardListValue.size(); i++) {
        //                                String values = "card" + i;
        //                                String listName = "listName" + i;
        //                                vo.defineNamedWhereClauseParam(values, mapCardListValue.get(listName), null);
        //                            }
        //                        } else {
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values < 150 ");
        //                            vo.defineNamedWhereClauseParam("card", populateStringValues(getBindings().getCard().getValue().toString()), null);
        //                        }
        //
        //                        vo.executeQuery();
        //                        session.setAttribute("user_display_card_Query", cardQuery);
        //                        session.setAttribute("user_display_map_Card_List", mapCardListValue);
        //                        isTableVisible = true;
        //                        resultDisplay = true;
        //                    }
        //
        //                } else {
        //                    showErrorMessage("ENGAGE_NO_PARTNER");
        //                }
        //            } else if (getBindings().getSingleCardRadio().getValue().equals(true) && getBindings().getMultipleCardRadio().getValue().equals(false)) {
        //                if (getBindings().getSearchStringInputtext().getValue() != null) {
        //                    String enteredCard = getBindings().getSearchStringInputtext().getValue().toString();
        //                    String passingPartner = "", passingAccount = "", passingCardgroup = "", passingCard = "", passingRole =
        //                        "WCP_CARD_B2B_ADMIN,WCP_CARD_B2B_MGR_AC,WCP_CARD_B2B_MGR_CG,WCP_CARD_B2B_EMP";
        //                    for (int i = 0; i < partnerInfoList.size(); i++) {
        //                        if (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {
        //                            for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
        //                                for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
        //                                    for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++) {
        //                                        if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
        //                                            partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() !=
        //                                            null &&
        //                                            partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().equalsIgnoreCase(enteredCard)) {
        //                                            passingCard = partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID();
        //                                            passingCardgroup =
        //                                                    partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString();
        //                                            passingAccount = partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString();
        //                                            passingPartner = partnerInfoList.get(i).getPartnerValue().toString();
        //                                        }
        //                                    }
        //                                }
        //                            }
        //                        }
        //                    }
        //
        //                    //execute query
        //                    if (session != null) {
        //                        if (session.getAttribute("user_display_account_Query") != null) {
        //                            accountQuery = session.getAttribute("user_display_account_Query").toString().trim();
        //                            mapAccountListValue = (Map<String, String>)session.getAttribute("user_display_map_Account_List");
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account Query & mapAccountList is found");
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account " + accountQuery);
        //                        }
        //                        if (session.getAttribute("user_display_cardGroup_Query") != null) {
        //                            cardGroupQuery = session.getAttribute("user_display_cardGroup_Query").toString().trim();
        //                            mapCardGroupListValue = (Map<String, String>)session.getAttribute("user_display_map_CardGroup_List");
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Query & mapCardGroupList is found");
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup " + cardGroupQuery);
        //                        }
        //                        if (session.getAttribute("user_display_card_Query") != null) {
        //                            cardQuery = session.getAttribute("user_display_card_Query").toString().trim();
        //                            mapCardListValue = (Map<String, String>)session.getAttribute("user_display_map_Card_List");
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Query & mapCardList is found");
        //                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card " + cardQuery);
        //                        }
        //                    }
        //
        //                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_ADMIN")) {
        //                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForAdminRVO1Iterator");
        //                        vo.setNamedWhereClauseParam("cc", lang);
        //                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_ADMIN);
        //                        vo.setNamedWhereClauseParam("PID", passingPartner);
        //                        vo.executeQuery();
        //                        isTableVisible = true;
        //                        resultDisplay = true;
        //                    }
        //                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_MGR_AC")) {
        //                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForAccMgrRVO1Iterator");
        //                        if (accountQuery.length() > 1 && accountQuery != null) {
        //                            if (vo.getWhereClause() != null) {
        //                                if (accountQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
        //                                    if (mapAccountListValue != null) {
        //                                        for (int i = 0; i < mapAccountListValue.size(); i++) {
        //                                            String values = "account" + i;
        //                                            vo.removeNamedWhereClauseParam(values);
        //                                        }
        //                                    } else {
        //                                        vo.removeNamedWhereClauseParam("account");
        //                                    }
        //                                    vo.setWhereClause("");
        //                                    vo.executeQuery();
        //                                }
        //                            }
        //                        }
        //                        accountQuery = "(";
        //
        //                        mapAccountListValue = null;
        //                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
        //                        accountQuery = "INSTR(:account,ACCOUNT_ID)<>0 ";
        //                        vo.setWhereClause(accountQuery);
        //                        vo.setNamedWhereClauseParam("cc", lang);
        //                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_MGR);
        //                        vo.setNamedWhereClauseParam("PID", passingPartner);
        //                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
        //                        vo.defineNamedWhereClauseParam("account", passingAccount, null);
        //                        vo.executeQuery();
        //                        session.setAttribute("user_display_account_Query", accountQuery);
        //                        isAccMgrVisible = true;
        //                    }
        //                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_MGR_CG")) {
        //
        //                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForCgMgrRVO1Iterator");
        //                        if (cardGroupQuery.length() > 1 && cardGroupQuery != null) {
        //                            if (vo.getWhereClause() != null) {
        //                                if (cardGroupQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
        //                                    if (mapCardGroupListValue != null) {
        //                                        for (int i = 0; i < mapCardGroupListValue.size(); i++) {
        //                                            String values = "cardGroup" + i;
        //                                            vo.removeNamedWhereClauseParam(values);
        //                                        }
        //                                    } else {
        //                                        vo.removeNamedWhereClauseParam("cardGroup");
        //                                    }
        //                                    vo.setWhereClause("");
        //                                    vo.executeQuery();
        //                                }
        //                            }
        //                        }
        //                        cardGroupQuery = "(";
        //
        //                        mapCardGroupListValue = null;
        //                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 ");
        //                        cardGroupQuery = "INSTR(:cardGroup,PARTNER_ID||CARD_GROUP)<>0 ";
        //
        //                        vo.setWhereClause(cardGroupQuery);
        //                        vo.setNamedWhereClauseParam("cc", lang);
        //                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_MGR);
        //                        vo.setNamedWhereClauseParam("PID", passingPartner);
        //                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 ");
        //                        vo.defineNamedWhereClauseParam("cardGroup", passingPartner + passingCardgroup, null);
        //
        //                        vo.executeQuery();
        //                        session.setAttribute("user_display_cardGroup_Query", cardGroupQuery);
        //                        session.setAttribute("user_display_map_CardGroup_List", mapCardGroupListValue);
        //                        isTableVisible = true;
        //                        resultDisplay = true;
        //                    }
        //                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_EMP")) {
        //
        //                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForEmpRVO1Iterator");
        //                        if (cardQuery.length() > 1 && cardQuery != null) {
        //                            if (vo.getWhereClause() != null) {
        //                                if (cardQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
        //                                    if (mapCardListValue != null) {
        //                                        for (int i = 0; i < mapCardListValue.size(); i++) {
        //                                            String values = "card" + i;
        //                                            vo.removeNamedWhereClauseParam(values);
        //                                        }
        //                                    } else {
        //                                        vo.removeNamedWhereClauseParam("card");
        //                                    }
        //                                    vo.setWhereClause("");
        //                                    vo.executeQuery();
        //                                }
        //                            }
        //                        }
        //                        cardQuery = "(";
        //
        //                        mapCardListValue = null;
        //                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values < 150 ");
        //                        cardQuery = "INSTR(:card,CARD)<>0 ";
        //                        vo.setWhereClause(cardQuery);
        //                        vo.setNamedWhereClauseParam("cc", lang);
        //                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_EMP);
        //                        vo.setNamedWhereClauseParam("PID", passingPartner);
        //                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values < 150 ");
        //                        vo.defineNamedWhereClauseParam("card", passingCard, null);
        //
        //                        vo.executeQuery();
        //                        session.setAttribute("user_display_card_Query", cardQuery);
        //                        session.setAttribute("user_display_map_Card_List", mapCardListValue);
        //                        isTableVisible = true;
        //                        resultDisplay = true;
        //                    }
        //                } else {
        //                    showErrorMessage("ENGAGE_NO_CARD");
        //                }
        //            }
        //            if (resultDisplay) {
        //                if (getBindings().getSearchStringInputtext().getValue() != null && getBindings().getSearchStringInputtext().getValue().equals(true)) {
        //                    isCgMgrVisible = true;
        //                    isEmpVisible = true;
        //                    isAccMgrVisible = true;
        //                    isAdminVisible = true;
        //                } else {
        //                    if (getBindings().getRole().getValue().toString().contains("WCP_CARD_B2B_ADMIN")) {
        //                        isAdminVisible = true;
        //                    }
        //                    if (getBindings().getRole().getValue().toString().contains("WCP_CARD_B2B_MGR_AC")) {
        //                        isAccMgrVisible = true;
        //                    }
        //                    if (getBindings().getRole().getValue().toString().contains("WCP_CARD_B2B_MGR_CG")) {
        //                        isCgMgrVisible = true;
        //                    }
        //                    if (getBindings().getRole().getValue().toString().contains("WCP_CARD_B2B_EMP")) {
        //                        isEmpVisible = true;
        //                    }
        //                }
        //            }
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside searchResult method of User Display");
    }

    public void showSearchResults() {

        isTableVisible = true;
        isCgMgrVisible = false;
        isEmpVisible = false;
        isAccMgrVisible = false;
        isAdminVisible = false;
        boolean resultDisplay = false;

        try {
            if (getBindings().getMultipleCardRadio().getValue().equals(true) && getBindings().getSingleCardRadio().getValue().equals(false)) {
                if (getBindings().getPartner().getValue() != null) {
                    if (getBindings().getAccount().getValue() != null) {
                    } else {
                        showErrorMessage("ENGAGE_NO_ACCOUNT");
                    }

                    if (getBindings().getCardGroup().getValue() != null) {
                    } else {
                        showErrorMessage("ENGAGE_NO_CARD_GROUP");
                    }

                    if (getBindings().getCard().getValue() != null) {
                    } else {
                        showErrorMessage("ENGAGE_NO_CARD");
                    }

                    if (getBindings().getRole().getValue() != null) {
                    } else {
                        showErrorMessage("ENGAGE_NO_ROLE");
                    }

                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_ADMIN")) {
                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForAdminRVO1Iterator");
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_ADMIN);
                        vo.setNamedWhereClauseParam("PID", populateStringValues(getBindings().getPartner().getValue().toString().trim()));
                        vo.executeQuery();
                        isTableVisible = true;
                        resultDisplay = true;
                    }
                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_MGR_AC")) {
                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForAccMgrRVO1Iterator");
                        if (accountQuery.length() > 1 && accountQuery != null) {
                            if (vo.getWhereClause() != null) {
                                if (accountQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
                                    if (mapAccountListValue != null) {
                                        for (int i = 0; i < mapAccountListValue.size(); i++) {
                                            String values = "account" + i;
                                            vo.removeNamedWhereClauseParam(values);
                                        }
                                    } else {
                                        vo.removeNamedWhereClauseParam("account");
                                    }
                                    vo.setWhereClause("");
                                    vo.executeQuery();
                                }
                            }
                        }
                        accountQuery = "(";
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_MGR);
                        vo.setNamedWhereClauseParam("PID", populateStringValues(getBindings().getPartner().getValue().toString()));
                        if (accountIdValue.size() > 150) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
                            mapAccountListValue = valueList.callValueList(accountIdValue.size(), accountIdValue);
                            for (int i = 0; i < mapAccountListValue.size(); i++) {
                                String values = "account" + i;
                                accountQuery = accountQuery + "INSTR(:" + values + ",ACCOUNT_ID)<>0 OR ";
                            }
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Account Query Values =" + accountQuery);
                            accountQuery = accountQuery.substring(0, accountQuery.length() - 3);
                            accountQuery = accountQuery + ")";
                        } else {
                            mapAccountListValue = null;
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                            accountQuery = "INSTR(:account,ACCOUNT_ID)<>0  ";
                        }
                        vo.setWhereClause(accountQuery);
                        String passingRole = "WCP_CARD_B2B_MGR";

                        if (accountIdValue.size() > 150) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
                            mapAccountListValue = valueList.callValueList(accountIdValue.size(), accountIdValue);
                            for (int i = 0; i < mapAccountListValue.size(); i++) {
                                String values = "account" + i;
                                String listName = "listName" + i;
                                vo.defineNamedWhereClauseParam(values, mapAccountListValue.get(listName), null);
                            }
                        } else {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                            vo.defineNamedWhereClauseParam("account", populateStringValues(getBindings().getAccount().getValue().toString()), null);
                        }

                        vo.executeQuery();
                        session.setAttribute("user_display_account_Query", accountQuery);
                        session.setAttribute("user_display_map_Account_List", mapAccountListValue);
                        isTableVisible = true;
                        resultDisplay = true;
                    }
                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_MGR_CG")) {

                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForCgMgrRVO1Iterator");
                        if (cardGroupQuery.length() > 1 && cardGroupQuery != null) {
                            if (vo.getWhereClause() != null) {
                                if (cardGroupQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
                                    if (mapCardGroupListValue != null) {
                                        for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                                            String values = "cardGroup" + i;
                                            vo.removeNamedWhereClauseParam(values);
                                        }
                                    } else {
                                        vo.removeNamedWhereClauseParam("cardGroup");
                                    }
                                    vo.setWhereClause("");
                                    vo.executeQuery();
                                }
                            }
                        }
                        cardGroupQuery = "(";

                        if (cardGroupValue.size() > 150) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values > 150 ");
                            mapCardGroupListValue = valueList.callValueList(cardGroupValue.size(), cardGroupValue);
                            for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                                String values = "cardGroup" + i;
                                cardGroupQuery = cardGroupQuery + "INSTR(:" + values + ",PARTNER_ID||CARD_GROUP)<>0 OR ";
                            }
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + "CARDGROUP Query Values =" + cardGroupQuery);
                            cardGroupQuery = cardGroupQuery.substring(0, cardGroupQuery.length() - 3);
                            cardGroupQuery = cardGroupQuery + ")";
                        } else {
                            mapCardGroupListValue = null;
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 ");
                            cardGroupQuery = "INSTR(:cardGroup,PARTNER_ID||CARD_GROUP)<>0 ";
                        }

                        vo.setWhereClause(cardGroupQuery);
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_MGR);
                        vo.setNamedWhereClauseParam("PID", populateStringValues(getBindings().getPartner().getValue().toString()));
                        if (cardGroupValue.size() > 150) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values > 150 ");
                            mapCardGroupListValue = valueList.callValueList(cardGroupValue.size(), cardGroupValue);
                            for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                                String values = "cardGroup" + i;
                                String listName = "listName" + i;
                                vo.defineNamedWhereClauseParam(values, mapCardGroupListValue.get(listName), null);
                            }
                        } else {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 ");
                            vo.defineNamedWhereClauseParam("cardGroup", populateStringValues(getBindings().getCardGroup().getValue().toString()), null);
                        }

                        vo.executeQuery();
                        session.setAttribute("user_display_cardGroup_Query", cardGroupQuery);
                        session.setAttribute("user_display_map_CardGroup_List", mapCardGroupListValue);
                        isTableVisible = true;
                        resultDisplay = true;
                    }
                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_EMP")) {

                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForEmpRVO1Iterator");
                        if (cardQuery.length() > 1 && cardQuery != null) {
                            if (vo.getWhereClause() != null) {
                                if (cardQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
                                    if (mapCardListValue != null) {
                                        for (int i = 0; i < mapCardListValue.size(); i++) {
                                            String values = "card" + i;
                                            vo.removeNamedWhereClauseParam(values);
                                        }
                                    } else {
                                        vo.removeNamedWhereClauseParam("card");
                                    }
                                    vo.setWhereClause("");
                                    vo.executeQuery();
                                }
                            }
                        }
                        cardQuery = "(";

                        if (cardNumberValue.size() > 150) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values > 150 ");
                            mapCardListValue = valueList.callValueList(cardNumberValue.size(), cardNumberValue);
                            for (int i = 0; i < mapCardListValue.size(); i++) {
                                String values = "card" + i;
                                cardQuery = cardQuery + "INSTR(:" + values + ",CARD)<>0 OR ";
                            }
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + "CARD Query Values =" + cardQuery);
                            cardQuery = cardQuery.substring(0, cardQuery.length() - 3);
                            cardQuery = cardQuery + ")";
                        } else {
                            mapCardListValue = null;
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values < 150 ");
                            cardQuery = "INSTR(:card,CARD)<>0 ";
                        }
                        vo.setWhereClause(cardQuery);
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_EMP);
                        vo.setNamedWhereClauseParam("PID", populateStringValues(getBindings().getPartner().getValue().toString()));
                        if (cardNumberValue.size() > 150) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values > 150 ");
                            mapCardListValue = valueList.callValueList(cardNumberValue.size(), cardNumberValue);
                            for (int i = 0; i < mapCardListValue.size(); i++) {
                                String values = "card" + i;
                                String listName = "listName" + i;
                                vo.defineNamedWhereClauseParam(values, mapCardListValue.get(listName), null);
                            }
                        } else {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values < 150 ");
                            vo.defineNamedWhereClauseParam("card", populateStringValues(getBindings().getCard().getValue().toString()), null);
                        }

                        vo.executeQuery();
                        session.setAttribute("user_display_card_Query", cardQuery);
                        session.setAttribute("user_display_map_Card_List", mapCardListValue);
                        isTableVisible = true;
                        resultDisplay = true;
                    }

                } else {
                    showErrorMessage("ENGAGE_NO_PARTNER");
                }
            } else if (getBindings().getSingleCardRadio().getValue().equals(true) && getBindings().getMultipleCardRadio().getValue().equals(false)) {
                if (getBindings().getSearchStringInputtext().getValue() != null) {
                    String enteredCard = getBindings().getSearchStringInputtext().getValue().toString();
                    String passingPartner = "", passingAccount = "", passingCardgroup = "", passingCard = "", passingRole =
                        "WCP_CARD_B2B_ADMIN,WCP_CARD_B2B_MGR_AC,WCP_CARD_B2B_MGR_CG,WCP_CARD_B2B_EMP";
                    for (int i = 0; i < partnerInfoList.size(); i++) {
                        if (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {
                            for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
                                for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
                                    for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++) {
                                        if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                            partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() !=
                                            null &&
                                            partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().equalsIgnoreCase(enteredCard)) {
                                            passingCard = partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID();
                                            passingCardgroup =
                                                    partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString();
                                            passingAccount = partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString();
                                            passingPartner = partnerInfoList.get(i).getPartnerValue().toString();
                                        }
                                    }
                                }
                            }
                        }
                    }

                    //execute query
                    if (session != null) {
                        if (session.getAttribute("user_display_account_Query") != null) {
                            accountQuery = session.getAttribute("user_display_account_Query").toString().trim();
                            mapAccountListValue = (Map<String, String>)session.getAttribute("user_display_map_Account_List");
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account Query & mapAccountList is found");
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account " + accountQuery);
                        }
                        if (session.getAttribute("user_display_cardGroup_Query") != null) {
                            cardGroupQuery = session.getAttribute("user_display_cardGroup_Query").toString().trim();
                            mapCardGroupListValue = (Map<String, String>)session.getAttribute("user_display_map_CardGroup_List");
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Query & mapCardGroupList is found");
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup " + cardGroupQuery);
                        }
                        if (session.getAttribute("user_display_card_Query") != null) {
                            cardQuery = session.getAttribute("user_display_card_Query").toString().trim();
                            mapCardListValue = (Map<String, String>)session.getAttribute("user_display_map_Card_List");
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Query & mapCardList is found");
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card " + cardQuery);
                        }
                    }

                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_ADMIN")) {
                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForAdminRVO1Iterator");
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_ADMIN);
                        vo.setNamedWhereClauseParam("PID", passingPartner);
                        vo.executeQuery();
                        isTableVisible = true;
                        resultDisplay = true;
                    }
                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_MGR_AC")) {
                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForAccMgrRVO1Iterator");
                        if (accountQuery.length() > 1 && accountQuery != null) {
                            if (vo.getWhereClause() != null) {
                                if (accountQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
                                    if (mapAccountListValue != null) {
                                        for (int i = 0; i < mapAccountListValue.size(); i++) {
                                            String values = "account" + i;
                                            vo.removeNamedWhereClauseParam(values);
                                        }
                                    } else {
                                        vo.removeNamedWhereClauseParam("account");
                                    }
                                    vo.setWhereClause("");
                                    vo.executeQuery();
                                }
                            }
                        }
                        accountQuery = "(";

                        mapAccountListValue = null;
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                        accountQuery = "INSTR(:account,ACCOUNT_ID)<>0 ";
                        vo.setWhereClause(accountQuery);
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_MGR);
                        vo.setNamedWhereClauseParam("PID", passingPartner);
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                        vo.defineNamedWhereClauseParam("account", passingAccount, null);
                        vo.executeQuery();
                        session.setAttribute("user_display_account_Query", accountQuery);
                        isAccMgrVisible = true;
                    }
                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_MGR_CG")) {

                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForCgMgrRVO1Iterator");
                        if (cardGroupQuery.length() > 1 && cardGroupQuery != null) {
                            if (vo.getWhereClause() != null) {
                                if (cardGroupQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
                                    if (mapCardGroupListValue != null) {
                                        for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                                            String values = "cardGroup" + i;
                                            vo.removeNamedWhereClauseParam(values);
                                        }
                                    } else {
                                        vo.removeNamedWhereClauseParam("cardGroup");
                                    }
                                    vo.setWhereClause("");
                                    vo.executeQuery();
                                }
                            }
                        }
                        cardGroupQuery = "(";

                        mapCardGroupListValue = null;
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 ");
                        cardGroupQuery = "INSTR(:cardGroup,PARTNER_ID||CARD_GROUP)<>0 ";

                        vo.setWhereClause(cardGroupQuery);
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_MGR);
                        vo.setNamedWhereClauseParam("PID", passingPartner);
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 ");
                        vo.defineNamedWhereClauseParam("cardGroup", passingPartner + passingCardgroup, null);

                        vo.executeQuery();
                        session.setAttribute("user_display_cardGroup_Query", cardGroupQuery);
                        session.setAttribute("user_display_map_CardGroup_List", mapCardGroupListValue);
                        isTableVisible = true;
                        resultDisplay = true;
                    }
                    if (getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_EMP")) {

                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForEmpRVO1Iterator");
                        if (cardQuery.length() > 1 && cardQuery != null) {
                            if (vo.getWhereClause() != null) {
                                if (cardQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
                                    if (mapCardListValue != null) {
                                        for (int i = 0; i < mapCardListValue.size(); i++) {
                                            String values = "card" + i;
                                            vo.removeNamedWhereClauseParam(values);
                                        }
                                    } else {
                                        vo.removeNamedWhereClauseParam("card");
                                    }
                                    vo.setWhereClause("");
                                    vo.executeQuery();
                                }
                            }
                        }
                        cardQuery = "(";

                        mapCardListValue = null;
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values < 150 ");
                        cardQuery = "INSTR(:card,CARD)<>0 ";
                        vo.setWhereClause(cardQuery);
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName", Constants.ROLE_WCP_CARD_B2B_EMP);
                        vo.setNamedWhereClauseParam("PID", passingPartner);
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values < 150 ");
                        vo.defineNamedWhereClauseParam("card", passingCard, null);

                        vo.executeQuery();
                        session.setAttribute("user_display_card_Query", cardQuery);
                        session.setAttribute("user_display_map_Card_List", mapCardListValue);
                        isTableVisible = true;
                        resultDisplay = true;
                    }
                } else {
                    showErrorMessage("ENGAGE_NO_CARD");
                }
            }
            if (resultDisplay) {
                if (getBindings().getSearchStringInputtext().getValue() != null && getBindings().getSearchStringInputtext().getValue().equals(true)) {
                    isCgMgrVisible = true;
                    isEmpVisible = true;
                    isAccMgrVisible = true;
                    isAdminVisible = true;
                } else {
                    if (getBindings().getRole().getValue().toString().contains("WCP_CARD_B2B_ADMIN")) {
                        isAdminVisible = true;
                    }
                    if (getBindings().getRole().getValue().toString().contains("WCP_CARD_B2B_MGR_AC")) {
                        isAccMgrVisible = true;
                    }
                    if (getBindings().getRole().getValue().toString().contains("WCP_CARD_B2B_MGR_CG")) {
                        isCgMgrVisible = true;
                    }
                    if (getBindings().getRole().getValue().toString().contains("WCP_CARD_B2B_EMP")) {
                        isEmpVisible = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clearSearchListener(ActionEvent actionEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside clearSearchListener method of User Display");
        getBindings().getPartner().setValue(null);
        getBindings().getAccount().setValue(null);
        getBindings().getCardGroup().setValue(null);
        getBindings().getCard().setValue(null);
        getBindings().getSearchStringInputtext().resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchStringInputtext());
        this.partnerIdValue = null;
        this.accountIdValue = null;
        accountIdList = new ArrayList<SelectItem>();
        this.cardGroupValue = null;
        cardGroupList = new ArrayList<SelectItem>();
        this.cardNumberValue = null;
        cardNumberList = new ArrayList<SelectItem>();
        roleValue = new ArrayList<String>();
        roleValue.add("WCP_CARD_B2B_ADMIN");
        roleValue.add("WCP_CARD_B2B_MGR_AC");
        roleValue.add("WCP_CARD_B2B_MGR_CG");
        roleValue.add("WCP_CARD_B2B_EMP");
        isTableVisible = false;
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside clearSearchListener method of User Display");
    }

    public String goToAddEditButtonAction() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside goToAddEditButtonAction method of User Display");
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        User userInfo = new User();
        userRoleDeatils = new ArrayList<UserInfoRolesDetails>();
        userEmail = "";
        showEmailpanel = false;
        if (session != null && null != session.getAttribute(Constants.SESSION_USER_INFO)) {
            userInfo = (User)session.getAttribute(Constants.SESSION_USER_INFO);
            if (null != userInfo) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " checking user info is not null");
                userEmail = userInfo.getEmailID();
                String[] associatesRoleName = null;
                if (userInfo.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN)) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside B2b Admin");
                    associatesRoleName = StringConversion(Constants.ENGAGE_B2B_ADMIN_USER);
                    for (int count = 0; count < associatesRoleName.length; count++) {
                        UserInfoRolesDetails userInfoDetails = new UserInfoRolesDetails();

                        userInfoDetails.setRoleName(associatesRoleName[count]);
                        userInfoDetails.setCheckUserRole(false);
                        userInfoDetails.setAssociationValue("");
                        userInfoDetails.setPartnerId("");
                        userRoleDeatils.add(userInfoDetails);
                    }
                } else if (userInfo.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR)) {

                    if (userInfo.getRoleList() != null && userInfo.getRoleList().size() > 0) {
                        for (int check = 0; check < userInfo.getRoleList().size(); check++) {
                            for (int i = 0; i < userInfo.getRoleList().get(check).getIdString().size(); i++) {
                                if (userInfo.getRoleList().get(check).getIdString().get(i).contains("AC")) {
                                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside B2b Mgr Ac");
                                    associatesRoleName = StringConversion(Constants.ENGAGE_B2B_MGR_AC_USER);
                                    for (int count = 0; count < associatesRoleName.length; count++) {
                                        UserInfoRolesDetails userInfoDetails = new UserInfoRolesDetails();

                                        userInfoDetails.setRoleName(associatesRoleName[count]);
                                        userInfoDetails.setCheckUserRole(false);
                                        userInfoDetails.setAssociationValue("");
                                        userInfoDetails.setPartnerId("");
                                        userRoleDeatils.add(userInfoDetails);
                                    }
                                    break;
                                }

                                if (userInfo.getRoleList().get(check).getIdString().get(i).contains("CG")) {
                                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside B2b Mgr CG");
                                    associatesRoleName = StringConversion(Constants.ENGAGE_B2B_MGR_CG_USER);
                                    for (int count = 0; count < associatesRoleName.length; count++) {
                                        UserInfoRolesDetails userInfoDetails = new UserInfoRolesDetails();

                                        userInfoDetails.setRoleName(associatesRoleName[count]);
                                        userInfoDetails.setCheckUserRole(false);
                                        userInfoDetails.setAssociationValue("");
                                        userInfoDetails.setPartnerId("");
                                        userRoleDeatils.add(userInfoDetails);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside B2b card employee");
                    associatesRoleName = StringConversion(Constants.ENGAGE_B2B_EMP_USER);
                    for (int count = 0; count < associatesRoleName.length; count++) {
                        UserInfoRolesDetails userInfoDetails = new UserInfoRolesDetails();

                        userInfoDetails.setRoleName(associatesRoleName[count]);
                        userInfoDetails.setCheckUserRole(false);
                        userInfoDetails.setAssociationValue("");
                        userInfoDetails.setPartnerId("");
                        userRoleDeatils.add(userInfoDetails);
                    }
                }
            }
            emailValue = null;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting goToAddEditButtonAction method of User Display");
        return "goToAddEdit";
    }

    public void emailIdValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside emailIdValueChangeListener method of User Display");
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length() > 0) {
            boolean validEmail = false;
            Validations emailCheck = new Validations();
            validEmail = emailCheck.validateEmail(valueChangeEvent.getNewValue().toString().trim());
            if (validEmail) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " email id validated");
                showEmailpanel = true;
                getBindings().getUserFirstName().setSubmittedValue("");
                getBindings().getUserFirstName().setValue("");
                getBindings().getUserMiddleName().setSubmittedValue("");
                getBindings().getUserMiddleName().setValue("");
                getBindings().getUserLastName().setSubmittedValue("");
                getBindings().getUserLastName().setValue("");
                getBindings().getUserPhoneNumber().setSubmittedValue("");
                getBindings().getUserPhoneNumber().setValue("");
                getBindings().getDateOfBirth().setSubmittedValue("");
                getBindings().getDateOfBirth().setValue("");

                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getUserFirstName());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getUserMiddleName());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getUserLastName());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getUserPhoneNumber());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDateOfBirth());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
            } else {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " invalid email id entered");
                showErrorMessage("ENTER_VALID_EMAIL");
            }

        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting emailIdValueChangeListener method of User Display");
    }


    public String addEditUserPopUpSaveAction() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside addEditUserPopUpSaveAction method of User Display");
        String selectedValues = null;
        RichTable userTable = getBindings().getUserRoleInfoTable();
        UserInfoRolesDetails selectedRow = (UserInfoRolesDetails)userTable.getSelectedRowData();
        warningMsg = "";
        if (selectedRow != null) {

            if (selectedRow.getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_ADMIN)) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " inside save action for B2B admin");
                if (getBindings().getAddEditPartnerId().getValue() != null) {
                    roleCount = roleCount + 1;
                    rolesToCreate = rolesToCreate + selectedRow.getRoleName().trim() + ",";

                    if (getBindings().getAddEditPartnerId().getValue() != null && getBindings().getAddEditPartnerId().getValue().toString().length() > 0) {
                        selectedValues =
                                getBindings().getAddEditPartnerId().getValue().toString().substring(1, getBindings().getAddEditPartnerId().getValue().toString().length() -
                                                                                                    1);
                        selectedRow.setAssociationValue(populateSelectManyChoiceValues(selectedValues.trim().replaceAll(" ", ""), selectedRow.getRoleName()));

                    }
                } else {
                    warningMsg = showErrorMessagePopup("ENGAGE_NO_PARTNER");
                    getBindings().getShowErrorMsg().setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                    return null;

                }
            } else if (selectedRow.getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR_AC)) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " inside save action for B2B mgr AC");
                if (getBindings().getAddEditPartnerId().getValue() != null) {
                } else {
                    warningMsg = showErrorMessagePopup("ENGAGE_NO_PARTNER");
                    getBindings().getShowErrorMsg().setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                    return null;
                }

                if (getBindings().getAddEditAccountId().getValue() != null) {
                    roleCount = roleCount + 1;
                    rolesToCreate = rolesToCreate + selectedRow.getRoleName().trim() + ",";
                    if (getBindings().getAddEditAccountId().getValue() != null && getBindings().getAddEditAccountId().getValue().toString().length() > 0) {
                        selectedRow.setAssociationValue(populateStringValues(getBindings().getAddEditAccountId().getValue().toString().trim().replaceAll(" ",
                                                                                                                                                         "")));
                    }
                } else {
                    warningMsg = showErrorMessagePopup("ENGAGE_NO_ACCOUNT");
                    getBindings().getShowErrorMsg().setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                    return null;
                }

            } else if (selectedRow.getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR_CG)) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " inside save action for B2B mgr CG");
                if (getBindings().getAddEditPartnerId().getValue() != null) {
                } else {
                    warningMsg = showErrorMessagePopup("ENGAGE_NO_PARTNER");
                    getBindings().getShowErrorMsg().setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                    return null;
                }
                if (getBindings().getAddEditAccountId().getValue() != null) {
                } else {
                    warningMsg = showErrorMessagePopup("ENGAGE_NO_ACCOUNT");
                    getBindings().getShowErrorMsg().setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                    return null;
                }

                if (getBindings().getAddEditCardGroupId().getValue() != null) {
                    roleCount = roleCount + 1;
                    rolesToCreate = rolesToCreate + selectedRow.getRoleName().trim() + ",";
                    if (getBindings().getAddEditCardGroupId().getValue() != null && getBindings().getAddEditCardGroupId().getValue().toString().length() > 0) {
                        selectedValues =
                                getBindings().getAddEditCardGroupId().getValue().toString().substring(1, getBindings().getAddEditCardGroupId().getValue().toString().length() -
                                                                                                      1);
                        selectedRow.setAssociationValue(populateSelectManyChoiceValues(selectedValues.trim().replaceAll(" ", ""), selectedRow.getRoleName()));
                    }
                } else {
                    warningMsg = showErrorMessagePopup("ENGAGE_NO_CARD_GROUP");
                    getBindings().getShowErrorMsg().setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                    return null;
                }
            } else {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " inside save action for B2B card admin ");
                if (getBindings().getAddEditPartnerId().getValue() != null) {
                } else {
                    warningMsg = showErrorMessagePopup("ENGAGE_NO_PARTNER");
                    getBindings().getShowErrorMsg().setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                    return null;
                }
                if (getBindings().getAddEditAccountId().getValue() != null) {
                } else {
                    warningMsg = showErrorMessagePopup("ENGAGE_NO_ACCOUNT");
                    getBindings().getShowErrorMsg().setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                    return null;
                }

                if (getBindings().getAddEditCardGroupId().getValue() != null) {
                } else {
                    warningMsg = showErrorMessagePopup("ENGAGE_NO_CARD_GROUP");
                    getBindings().getShowErrorMsg().setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                    return null;
                }

                if (getBindings().getAddEditCardId().getValue() != null) {
                    roleCount = roleCount + 1;
                    rolesToCreate = rolesToCreate + selectedRow.getRoleName().trim() + ",";
                    if (getBindings().getAddEditCardId().getValue() != null && getBindings().getAddEditCardId().getValue().toString().length() > 0) {
                        selectedValues =
                                getBindings().getAddEditCardId().getValue().toString().substring(1, getBindings().getAddEditCardId().getValue().toString().length() -
                                                                                                 1);
                        selectedRow.setAssociationValue(populateSelectManyChoiceValues(selectedValues.trim().replaceAll(" ", ""), selectedRow.getRoleName()));
                    }
                } else {
                    warningMsg = showErrorMessagePopup("ENGAGE_NO_CARD");
                    getBindings().getShowErrorMsg().setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                    return null;
                }
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting addEditUserPopUpSaveAction method of User Display");
        getBindings().getAddEditUserPopUp().hide();
        return null;
    }

    public String populateSelectManyChoiceValues(String var, String role) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside populateSelectManyChoiceValues method of User Display");
        String passingValues = "";
        String returnStringParam = "";
        if (var != null && role != null && role.equals(Constants.ROLE_WCP_CARD_B2B_ADMIN)) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside populateSelectManyChoiceValues for b2b admin");
            String[] convertedString = StringConversion(var.trim());
            if (convertedString.length > 0) {
                for (int count = 0; count < convertedString.length; count++) {
                    passingValues = partnerNameMap.get(convertedString[count].trim());
                    returnStringParam = returnStringParam + passingValues + ",";


                }
            }
        } else if (role.equals(Constants.ROLE_WCP_CARD_B2B_MGR_CG)) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside populateSelectManyChoiceValues for b2b mgr cg");
            String[] convertedString = StringConversion(var);
            if (convertedString.length > 0) {
                for (int count = 0; count < convertedString.length; count++) {
                    passingValues = cardGroupDescMap.get(convertedString[count].trim());
                    returnStringParam = returnStringParam + passingValues + ",";
                }
            }
        } else {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside populateSelectManyChoiceValues for b2b card employee");
            String[] convertedString = StringConversion(var);
            if (convertedString.length > 0) {
                for (int count = 0; count < convertedString.length; count++) {
                    passingValues = cardEmbossMap.get(convertedString[count].trim());
                    returnStringParam = returnStringParam + passingValues + ",";
                }
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting populateSelectManyChoiceValues method of User Display");
        return returnStringParam.substring(0, returnStringParam.length() - 1);
    }

    public String addEditUserPopUpCloseAction() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside addEditUserPopUpCloseAction method of User Display");
        isCreateAdmin = false;
        isCreateACMgr = false;
        isCreateCGMgr = false;
        isCreateEmployee = false;
        getBindings().getAddEditUserPopUp().hide();
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting addEditUserPopUpCloseAction method of User Display");
        return null;
    }

    public String manageUserCommandLinkAction() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside manageUserCommandLinkAction method of User Display");
        showPopUpHeading = null;
        populateCustomerData("Add");
        RichTable userTable = getBindings().getUserRoleInfoTable();
        UserInfoRolesDetails selectedRow = (UserInfoRolesDetails)userTable.getSelectedRowData();
        if (selectedRow != null) {
            if (selectedRow.getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_ADMIN)) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside manageUserCommandLinkAction for B2B admin");
                isCreateAdmin = true;
                //                isCreateACMgr = false;
                //                isCreateCGMgr = false;
                //                isCreateEmployee = false;
                showSelectManyChoiceOnRole(selectedRow.getRoleName());
                showPopUpHeading = fetchAddEditPopUpHeading(selectedRow.getRoleName());
                showPopUpTableheading = fetchAddEditPopUpTableHeading(selectedRow.getRoleName());
            } else if (selectedRow.getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR_AC)) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside manageUserCommandLinkAction for B2B mgr AC");
                //                isCreateAdmin = false;
                isCreateACMgr = true;
                //                isCreateCGMgr = false;
                //                isCreateEmployee = false;
                showSelectManyChoiceOnRole(selectedRow.getRoleName());
                showPopUpHeading = fetchAddEditPopUpHeading(selectedRow.getRoleName());
                showPopUpTableheading = fetchAddEditPopUpTableHeading(selectedRow.getRoleName());
            } else if (selectedRow.getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR_CG)) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside manageUserCommandLinkAction for B2B mgr CG");
                //                isCreateAdmin = false;
                //                isCreateACMgr = false;
                isCreateCGMgr = true;
                //                isCreateEmployee = false;
                showSelectManyChoiceOnRole(selectedRow.getRoleName());
                showPopUpHeading = fetchAddEditPopUpHeading(selectedRow.getRoleName());
                showPopUpTableheading = fetchAddEditPopUpTableHeading(selectedRow.getRoleName());
            } else {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside manageUserCommandLinkAction for B2B card employee");
                //                isCreateAdmin = false;
                //                isCreateACMgr = false;
                //                isCreateCGMgr = false;
                isCreateEmployee = true;
                showSelectManyChoiceOnRole(selectedRow.getRoleName());
                showPopUpHeading = fetchAddEditPopUpHeading(selectedRow.getRoleName());
                showPopUpTableheading = fetchAddEditPopUpTableHeading(selectedRow.getRoleName());
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting manageUserCommandLinkAction method of User Display");
        getBindings().getAddEditUserPopUp().show(new RichPopup.PopupHints());
        return null;
    }

    public void showSelectManyChoiceOnRole(String roleName) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside showSelectManyChoiceOnRole method of User Display");
        if (roleName != null && roleName.equals(Constants.ROLE_WCP_CARD_B2B_ADMIN)) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside showSelectManyChoiceOnRole for B2B admin");
            getBindings().getPopUpPartnerId().setRendered(true);
            getBindings().getPopUpPartnerColun().setRendered(true);
            getBindings().getPopUpPartnerSpacer().setRendered(true);
            getBindings().getAddEditPartnerId().setRendered(true);

            getBindings().getPopUpAccountId().setRendered(false);
            getBindings().getPopUpAccountColon().setRendered(false);
            getBindings().getPopUpAccountSpacer().setRendered(false);
            getBindings().getAddEditAccountId().setRendered(false);
            getBindings().getPopUpCardGroupId().setRendered(false);
            getBindings().getPopUpCardGroupColon().setRendered(false);
            getBindings().getPopUpCardGroupSpacer().setRendered(false);
            getBindings().getAddEditCardGroupId().setRendered(false);
            getBindings().getPopUpCardId().setRendered(false);
            getBindings().getPopUpCardColon().setRendered(false);
            getBindings().getPopUpCardSpacer().setRendered(false);
            getBindings().getAddEditCardId().setRendered(false);
        } else if (roleName.equals(Constants.ROLE_WCP_CARD_B2B_MGR_AC)) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside showSelectManyChoiceOnRole for B2B mgr AC");
            getBindings().getPopUpPartnerId().setRendered(true);
            getBindings().getPopUpPartnerColun().setRendered(true);
            getBindings().getPopUpPartnerSpacer().setRendered(true);
            getBindings().getAddEditPartnerId().setRendered(true);
            getBindings().getPopUpAccountId().setRendered(true);
            getBindings().getPopUpAccountColon().setRendered(true);
            getBindings().getPopUpAccountSpacer().setRendered(true);
            getBindings().getAddEditAccountId().setRendered(true);
            getBindings().getPopUpCardGroupId().setRendered(false);
            getBindings().getPopUpCardGroupColon().setRendered(false);
            getBindings().getPopUpCardGroupSpacer().setRendered(false);
            getBindings().getAddEditCardGroupId().setRendered(false);
            getBindings().getPopUpCardId().setRendered(false);
            getBindings().getPopUpCardColon().setRendered(false);
            getBindings().getPopUpCardSpacer().setRendered(false);
            getBindings().getAddEditCardId().setRendered(false);

        } else if (roleName.equals(Constants.ROLE_WCP_CARD_B2B_MGR_CG)) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside showSelectManyChoiceOnRole for B2B mgr CG");
            getBindings().getPopUpPartnerId().setRendered(true);
            getBindings().getPopUpPartnerColun().setRendered(true);
            getBindings().getPopUpPartnerSpacer().setRendered(true);
            getBindings().getAddEditPartnerId().setRendered(true);
            getBindings().getPopUpAccountId().setRendered(true);
            getBindings().getPopUpAccountColon().setRendered(true);
            getBindings().getPopUpAccountSpacer().setRendered(true);
            getBindings().getAddEditAccountId().setRendered(true);
            getBindings().getPopUpCardGroupId().setRendered(true);
            getBindings().getPopUpCardGroupColon().setRendered(true);
            getBindings().getPopUpCardGroupSpacer().setRendered(true);
            getBindings().getAddEditCardGroupId().setRendered(true);
            getBindings().getPopUpCardId().setRendered(false);
            getBindings().getPopUpCardColon().setRendered(false);
            getBindings().getPopUpCardSpacer().setRendered(false);
            getBindings().getAddEditCardId().setRendered(false);
        } else {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside showSelectManyChoiceOnRole for B2B card employee");
            getBindings().getPopUpPartnerId().setRendered(true);
            getBindings().getPopUpPartnerColun().setRendered(true);
            getBindings().getPopUpPartnerSpacer().setRendered(true);
            getBindings().getAddEditPartnerId().setRendered(true);
            getBindings().getPopUpAccountId().setRendered(true);
            getBindings().getPopUpAccountColon().setRendered(true);
            getBindings().getPopUpAccountSpacer().setRendered(true);
            getBindings().getAddEditAccountId().setRendered(true);
            getBindings().getPopUpCardGroupId().setRendered(true);
            getBindings().getPopUpCardGroupColon().setRendered(true);
            getBindings().getPopUpCardGroupSpacer().setRendered(true);
            getBindings().getAddEditCardGroupId().setRendered(true);
            getBindings().getPopUpCardId().setRendered(true);
            getBindings().getPopUpCardColon().setRendered(true);
            getBindings().getPopUpCardSpacer().setRendered(true);
            getBindings().getAddEditCardId().setRendered(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPopUpPartnerId());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPopUpPartnerColun());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPopUpPartnerSpacer());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddEditPartnerId());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPopUpAccountId());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPopUpAccountColon());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPopUpAccountSpacer());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddEditAccountId());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPopUpCardGroupId());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPopUpCardGroupColon());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPopUpCardGroupSpacer());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddEditCardGroupId());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPopUpCardId());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPopUpCardColon());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPopUpCardSpacer());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddEditCardId());
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting showSelectManyChoiceOnRole method of User Display");
    }

    public String fetchAddEditPopUpHeading(String roleName) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting fetchAddEditPopUpHeading method of User Display");
        String transalatedString = null;

        if (roleName != null && roleName.equals(Constants.ROLE_WCP_CARD_B2B_ADMIN)) {
            if (resourceBundle.containsKey("ADD_EDIT_USER_ADMIN")) {
                transalatedString = resourceBundle.getObject("ADD_EDIT_USER_ADMIN").toString();
            }
        } else if (roleName.equals(Constants.ROLE_WCP_CARD_B2B_MGR_AC)) {
            if (resourceBundle.containsKey("ADD_EDIT_USER_MGR_AC")) {
                transalatedString = resourceBundle.getObject("ADD_EDIT_USER_MGR_AC").toString();
            }
        } else if (roleName.equals(Constants.ROLE_WCP_CARD_B2B_MGR_CG)) {
            if (resourceBundle.containsKey("ADD_EDIT_USER_MGR_CG")) {
                transalatedString = resourceBundle.getObject("ADD_EDIT_USER_MGR_CG").toString();
            }
        } else {
            if (resourceBundle.containsKey("ADD_EDIT_USER_EMP")) {
                transalatedString = resourceBundle.getObject("ADD_EDIT_USER_EMP").toString();
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting fetchAddEditPopUpHeading method of User Display");
        return transalatedString;
    }

    public String fetchAddEditPopUpTableHeading(String roleName) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside fetchAddEditPopUpTableHeading method of User Display");
        String transalatedString = null;

        if (roleName != null && roleName.equals(Constants.ROLE_WCP_CARD_B2B_ADMIN)) {
            if (resourceBundle.containsKey("ALREADY_ASSOCIATED_PARTNERS")) {
                transalatedString = resourceBundle.getObject("ALREADY_ASSOCIATED_PARTNERS").toString();
            }
        } else if (roleName.equals(Constants.ROLE_WCP_CARD_B2B_MGR_AC)) {
            if (resourceBundle.containsKey("ALREADY_ASSOCIATED_ACCOUNTS")) {
                transalatedString = resourceBundle.getObject("ALREADY_ASSOCIATED_ACCOUNTS").toString();
            }
        } else if (roleName.equals(Constants.ROLE_WCP_CARD_B2B_MGR_CG)) {
            if (resourceBundle.containsKey("ALREADY_ASSOCIATED_CARDGROUP")) {
                transalatedString = resourceBundle.getObject("ALREADY_ASSOCIATED_CARDGROUP").toString();
            }
        } else {
            if (resourceBundle.containsKey("ALREADY_ASSOCIATED_CARDS")) {
                transalatedString = resourceBundle.getObject("ALREADY_ASSOCIATED_CARDS").toString();
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting fetchAddEditPopUpTableHeading method of User Display");
        return transalatedString;
    }

    public String SaveUserInfoAction() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside SaveUserInfoAction method of User Display");
        User userInfo = new User();
        Conversion conv = new Conversion();
        Integer phoneNumber = 0;
        Date dateOfBirth;
        oracle.jbo.domain.Date newJboDOBDate = new oracle.jbo.domain.Date();
        DCBindingContainer bc = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        if (getBindings().getUserEmailId().getValue() != null && getBindings().getUserEmailId().getValue().toString().length() > 0) {
            userInfo.setEmailID(getBindings().getUserEmailId().getValue().toString().trim());
            userInfo.setCountry(lang);
        } else {
            showErrorMessage("ENTER_VALID_EMAIL_ID");
        }

        if (getBindings().getUserFirstName().getValue() != null && getBindings().getUserFirstName().getValue().toString().length() > 0) {
            userInfo.setFirstName(getBindings().getUserFirstName().getValue().toString().trim());
        } else {
            showErrorMessage("ENTER_FIRST_NAME");
        }

        if (getBindings().getUserLastName().getValue() != null && getBindings().getUserLastName().getValue().toString().length() > 0) {
            userInfo.setLastName(getBindings().getUserLastName().getValue().toString().trim());
        } else {
            showErrorMessage("ENTER_LAST_NAME");
        }

        if (getBindings().getUserPhoneNumber().getValue() != null && getBindings().getUserPhoneNumber().getValue().toString().length() > 0) {
            userInfo.setPhoneNumber(getBindings().getUserPhoneNumber().getValue().toString().trim());
            int i = Integer.parseInt(getBindings().getUserPhoneNumber().getValue().toString().trim());
            phoneNumber = i;
        } else {
            showErrorMessage("ENTER_PHONE_NUMBER");
        }

        if (getBindings().getDateOfBirth().getValue() != null) {
            dateOfBirth = (Date)getBindings().getDateOfBirth().getValue();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sqlDateString = sdf.format(dateOfBirth);
            java.sql.Date sqlDate = java.sql.Date.valueOf(sqlDateString);
            newJboDOBDate = new oracle.jbo.domain.Date(sqlDate);
            userInfo.setDob(dateOfBirth);
        } else {
            showErrorMessage("ENTER_DATE_OF_BIRTH");
        }


        if (getBindings().getUserMiddleName() != null) {
            userInfo.setMiddleName(getBindings().getUserMiddleName().getValue().toString().trim());
        }

        if (roleCount > 0 && rolesToCreate != null && rolesToCreate.length() > 0) {
            List<Roles> passingRoleList = new ArrayList<Roles>();
            String checkB2bMgr = "No";
            String rolesToPass = rolesToCreate.substring(0, rolesToCreate.length() - 1);
            String[] passedRoles = rolesToPass.split(",");
            for (int i = 0; i < passedRoles.length; i++) {
                Roles rolesInfo = new Roles();
                if (passedRoles[i].trim().equals(Constants.ROLE_WCP_CARD_B2B_ADMIN)) {
                    rolesInfo.setRoleName(Constants.ROLE_WCP_CARD_B2B_ADMIN);
                    rolesInfo.setAssigned(true);
                    passingRoleList.add(rolesInfo);
                } else if (passedRoles[i].trim().equals(Constants.ROLE_WCP_CARD_B2B_MGR_AC)) {
                    checkB2bMgr = "Yes";
                    rolesInfo.setRoleName(Constants.ROLE_WCP_CARD_B2B_MGR);
                    rolesInfo.setAssigned(true);
                    passingRoleList.add(rolesInfo);
                } else if (passedRoles[i].trim().equals(Constants.ROLE_WCP_CARD_B2B_MGR_CG)) {
                    if (checkB2bMgr.equals("Yes")) {
                        break;
                    } else {
                        rolesInfo.setRoleName(Constants.ROLE_WCP_CARD_B2B_MGR);
                        rolesInfo.setAssigned(true);
                        passingRoleList.add(rolesInfo);
                    }

                } else {
                    rolesInfo.setRoleName(Constants.ROLE_WCP_CARD_B2B_EMP);
                    rolesInfo.setAssigned(true);
                    passingRoleList.add(rolesInfo);
                }
            }
            userInfo.setRoleList(passingRoleList);
        }

        if (userInfo != null) {
            System.out.println("User first name=====>" + userInfo.getFirstName());
            System.out.println("User dob=====>" + userInfo.getDob());
            System.out.println("User phone number=====>" + userInfo.getPhoneNumber());
            for (int i = 0; i < userInfo.getRoleList().size(); i++) {
                System.out.println("value in role list====>" + userInfo.getRoleList().get(i).getRoleName());
            }
            BaseBean result = new BaseBean();
            OperationBinding userCreateOpn = bc.getOperationBinding("createUser");
            result = (BaseBean)userCreateOpn.getParamsMap().put("user", userInfo);
            //        }
            if (result != null) {
                System.out.println("--------------------------------" + result);
            } else {
                System.out.println("AAAAAAAAA--------------------------------");
            }


            try {
                DCIteratorBinding prtCardUserInfoItr = bc.findIteratorBinding("PrtCardUserInformationVO1Iterator");
                DCIteratorBinding prtCardUserRoleMapItr = bc.findIteratorBinding("PrtCardUserRoleMappingVO1Iterator");

                if (prtCardUserInfoItr != null && prtCardUserRoleMapItr != null) {
                    ViewObject cardUserInfoVO = ADFUtils.getViewObject("PrtCardUserInformationVO1Iterator");
                    ViewObject userRoleMapVO = ADFUtils.getViewObject("PrtCardUserRoleMappingVO1Iterator");

                    Row userInfoRow = cardUserInfoVO.createRow();
                    userInfoRow.setAttribute("CountryCode", lang);
                    userInfoRow.setAttribute("UserEmail", getBindings().getUserEmailId().getValue().toString().trim());
                    userInfoRow.setAttribute("UserFirstName", getBindings().getUserFirstName().getValue().toString().trim());
                    userInfoRow.setAttribute("UserMiddleName", getBindings().getUserMiddleName().getValue().toString().trim());
                    userInfoRow.setAttribute("UserLastName", getBindings().getUserLastName().getValue().toString().trim());
                    userInfoRow.setAttribute("UserDob", newJboDOBDate);
                    userInfoRow.setAttribute("UserPhoneNo", (Number)phoneNumber);
                    userInfoRow.setAttribute("UserLang", conv.getCustomerCountryCode(lang));
                    userInfoRow.setAttribute("ModifiedBy", userEmail);

                    userInfoRow.setAttribute("UserStatus", "ACTIVE");
                    cardUserInfoVO.insertRow(userInfoRow);
                    RichTable userInfoTable = getBindings().getUserRoleInfoTable();
                    Object userInfoObject;
                    UserInfoRolesDetails allSelectedRows;

                    List<UserInfoRolesDetails> roleList = new ArrayList<UserInfoRolesDetails>();
                    for (int count = 0; count < userRoleDeatils.size(); count++) {
                        userInfoObject = userInfoTable.getRowData(count);
                        allSelectedRows = (UserInfoRolesDetails)userInfoObject;

                        if (allSelectedRows.getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_ADMIN)) {
                            String[] paramString = StringConversion(allSelectedRows.getAssociationValue());
                            for (int pCount = 0; pCount < paramString.length; pCount++) {
                                if (paramString[pCount].trim() != null && paramString[pCount].length() > 0) {
                                    UserInfoRolesDetails roleInfo = new UserInfoRolesDetails();
                                    roleInfo.setRoleName(allSelectedRows.getRoleName());
                                    roleInfo.setAssociationValue(partnerIdMap.get(paramString[pCount].trim()));
                                    roleInfo.setAssociationType(Constants.ENGAGE_B2B_ADMIN_ASSOC);
                                    roleInfo.setPartnerId(partnerIdMap.get(paramString[pCount].trim()));
                                    roleList.add(roleInfo);
                                }
                            }

                        } else if (allSelectedRows.getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR_AC)) {
                            String[] paramString = StringConversion(allSelectedRows.getAssociationValue());
                            for (int pCount = 0; pCount < paramString.length; pCount++) {
                                if (paramString[pCount].trim() != null && paramString[pCount].length() > 0) {
                                    UserInfoRolesDetails roleInfo = new UserInfoRolesDetails();
                                    roleInfo.setRoleName(Constants.ROLE_WCP_CARD_B2B_MGR);
                                    roleInfo.setAssociationValue(paramString[pCount].trim());
                                    roleInfo.setAssociationType(Constants.ENGAGE_B2B_MGR_AC_ASSOC);
                                    roleInfo.setPartnerId(accountToPartnermap.get(paramString[pCount].trim()));
                                    roleList.add(roleInfo);
                                }
                            }
                        } else if (allSelectedRows.getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR_CG)) {
                            String[] paramString = StringConversion(allSelectedRows.getAssociationValue());
                            for (int pCount = 0; pCount < paramString.length; pCount++) {
                                if (paramString[pCount].trim() != null && paramString[pCount].length() > 0) {
                                    UserInfoRolesDetails roleInfo = new UserInfoRolesDetails();
                                    roleInfo.setRoleName(Constants.ROLE_WCP_CARD_B2B_MGR);
                                    roleInfo.setAssociationValue(cardGroupIdMap.get(paramString[pCount].trim()));
                                    roleInfo.setAssociationType(Constants.ENGAGE_B2B_MGR_CG_ASSOC);
                                    roleInfo.setPartnerId(cardGroupToPartnerMap.get(paramString[pCount].trim()));
                                    roleList.add(roleInfo);
                                }
                            }
                        } else {
                            String[] paramString = StringConversion(allSelectedRows.getAssociationValue());
                            for (int pCount = 0; pCount < paramString.length; pCount++) {
                                if (paramString[pCount].trim() != null && paramString[pCount].length() > 0) {
                                    UserInfoRolesDetails roleInfo = new UserInfoRolesDetails();
                                    roleInfo.setRoleName(allSelectedRows.getRoleName());
                                    roleInfo.setAssociationValue(cardIdMap.get(paramString[pCount].trim()));
                                    roleInfo.setAssociationType(Constants.ENGAGE_B2B_EMP_ASSOC);
                                    roleInfo.setPartnerId(cardToPartnerMap.get(paramString[pCount].trim()));
                                    roleList.add(roleInfo);
                                }
                            }
                        }
                    }

                    for (int roleCount = 0; roleCount < roleList.size(); roleCount++) {
                        Row userRoleMapRow = userRoleMapVO.createRow();
                        userRoleMapRow.setAttribute("CountryCode", lang);
                        userRoleMapRow.setAttribute("UserEmail", getBindings().getUserEmailId().getValue().toString().trim());
                        userRoleMapRow.setAttribute("UserRole", roleList.get(roleCount).getRoleName());
                        userRoleMapRow.setAttribute("AssociationType", roleList.get(roleCount).getAssociationType());
                        userRoleMapRow.setAttribute("UserAssociation", roleList.get(roleCount).getAssociationValue());
                        userRoleMapRow.setAttribute("PartnerId", roleList.get(roleCount).getPartnerId());
                        userRoleMapRow.setAttribute("ModifiedBy", userEmail);

                        userRoleMapVO.insertRow(userRoleMapRow);
                    }
                    OperationBinding operationBinding = bc.getOperationBinding("Commit");
                    operationBinding.execute();
                }
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting SaveUserInfoAction method of User Display");
        showSearchResults();
        return "addEditToSearch";
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

    public void setCardGroupList(List<SelectItem> cardGroupList) {
        this.cardGroupList = cardGroupList;
    }

    public List<SelectItem> getCardGroupList() {
        return cardGroupList;
    }

    public void setCardGroupValue(List<String> cardGroupValue) {
        this.cardGroupValue = cardGroupValue;
    }

    public List<String> getCardGroupValue() {
        return cardGroupValue;
    }

    public void setRoleList(List<SelectItem> roleList) {
        this.roleList = roleList;
    }

    public void setRoleValue(List<String> roleValue) {
        this.roleValue = roleValue;
    }

    public List<String> getRoleValue() {
        return roleValue;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setIsTableVisible(Boolean isTableVisible) {
        this.isTableVisible = isTableVisible;
    }

    public Boolean getIsTableVisible() {
        return isTableVisible;
    }

    public void setMultipleCardRadio(RichSelectBooleanRadio multipleCardRadio) {
        this.multipleCardRadio = multipleCardRadio;
    }

    public RichSelectBooleanRadio getMultipleCardRadio() {
        return multipleCardRadio;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setIsCgMgrVisible(Boolean isCgMgrVisible) {
        this.isCgMgrVisible = isCgMgrVisible;
    }

    public Boolean getIsCgMgrVisible() {
        return isCgMgrVisible;
    }

    public void setIsEmpVisible(Boolean isEmpVisible) {
        this.isEmpVisible = isEmpVisible;
    }

    public Boolean getIsEmpVisible() {
        return isEmpVisible;
    }

    public void setIsAccMgrVisible(Boolean isAccMgrVisible) {
        this.isAccMgrVisible = isAccMgrVisible;
    }

    public Boolean getIsAccMgrVisible() {
        return isAccMgrVisible;
    }

    public void setIsAdminVisible(Boolean isAdminVisible) {
        this.isAdminVisible = isAdminVisible;
    }

    public Boolean getIsAdminVisible() {
        return isAdminVisible;
    }

    public void setAddEditUserPartnerID(List<SelectItem> addEditUserPartnerID) {
        this.addEditUserPartnerID = addEditUserPartnerID;
    }

    public List<SelectItem> getAddEditUserPartnerID() {
        return addEditUserPartnerID;
    }

    public void setAddEditUserAccountID(List<SelectItem> addEditUserAccountID) {
        this.addEditUserAccountID = addEditUserAccountID;
    }

    public List<SelectItem> getAddEditUserAccountID() {
        return addEditUserAccountID;
    }

    public void setAddEditUserCardGroupID(List<SelectItem> addEditUserCardGroupID) {
        this.addEditUserCardGroupID = addEditUserCardGroupID;
    }

    public List<SelectItem> getAddEditUserCardGroupID() {
        return addEditUserCardGroupID;
    }

    public void setAddEditCardID(List<SelectItem> addEditCardID) {
        this.addEditCardID = addEditCardID;
    }

    public List<SelectItem> getAddEditCardID() {
        return addEditCardID;
    }

    public void setAddEditPartnerValue(List<String> addEditPartnerValue) {
        this.addEditPartnerValue = addEditPartnerValue;
    }

    public List<String> getAddEditPartnerValue() {
        return addEditPartnerValue;
    }

    public void setAddEditAccountValue(List<String> addEditAccountValue) {
        this.addEditAccountValue = addEditAccountValue;
    }

    public List<String> getAddEditAccountValue() {
        return addEditAccountValue;
    }

    public void setAddEditCardGroupValue(List<String> addEditCardGroupValue) {
        this.addEditCardGroupValue = addEditCardGroupValue;
    }

    public List<String> getAddEditCardGroupValue() {
        return addEditCardGroupValue;
    }

    public void setAddEditCardValue(List<String> addEditCardValue) {
        this.addEditCardValue = addEditCardValue;
    }

    public List<String> getAddEditCardValue() {
        return addEditCardValue;
    }

    public void setIsCreateAdmin(boolean isCreateAdmin) {
        this.isCreateAdmin = isCreateAdmin;
    }

    public boolean isIsCreateAdmin() {
        return isCreateAdmin;
    }

    public void setIsCreateACMgr(boolean isCreateACMgr) {
        this.isCreateACMgr = isCreateACMgr;
    }

    public boolean isIsCreateACMgr() {
        return isCreateACMgr;
    }

    public void setIsCreateCGMgr(boolean isCreateCGMgr) {
        this.isCreateCGMgr = isCreateCGMgr;
    }

    public boolean isIsCreateCGMgr() {
        return isCreateCGMgr;
    }

    public void setIsCreateEmployee(boolean isCreateEmployee) {
        this.isCreateEmployee = isCreateEmployee;
    }

    public boolean isIsCreateEmployee() {
        return isCreateEmployee;
    }

    public void setAddAdminInputValue(String addAdminInputValue) {
        this.addAdminInputValue = addAdminInputValue;
    }

    public String getAddAdminInputValue() {
        return addAdminInputValue;
    }

    public void setAddACMgrInputValue(String addACMgrInputValue) {
        this.addACMgrInputValue = addACMgrInputValue;
    }

    public String getAddACMgrInputValue() {
        return addACMgrInputValue;
    }

    public void setAddCGMgrInputValue(String addCGMgrInputValue) {
        this.addCGMgrInputValue = addCGMgrInputValue;
    }

    public String getAddCGMgrInputValue() {
        return addCGMgrInputValue;
    }

    public void setAddEmployeeInputValue(String addEmployeeInputValue) {
        this.addEmployeeInputValue = addEmployeeInputValue;
    }

    public String getAddEmployeeInputValue() {
        return addEmployeeInputValue;
    }

    public void setUserRoleDeatils(List<UserInfoRolesDetails> userRoleDeatils) {
        this.userRoleDeatils = userRoleDeatils;
    }

    public List<UserInfoRolesDetails> getUserRoleDeatils() {
        return userRoleDeatils;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setShowPopUpHeading(String showPopUpHeading) {
        this.showPopUpHeading = showPopUpHeading;
    }

    public String getShowPopUpHeading() {
        return showPopUpHeading;
    }

    public void setShowPopUpTableheading(String showPopUpTableheading) {
        this.showPopUpTableheading = showPopUpTableheading;
    }

    public String getShowPopUpTableheading() {
        return showPopUpTableheading;
    }

    public void setPartnerIdMap(Map<String, String> partnerIdMap) {
        this.partnerIdMap = partnerIdMap;
    }

    public Map<String, String> getPartnerIdMap() {
        return partnerIdMap;
    }

    //    public void setAccountIdMap(Map<String, String> accountIdMap) {
    //        this.accountIdMap = accountIdMap;
    //    }
    //
    //    public Map<String, String> getAccountIdMap() {
    //        return accountIdMap;
    //    }

    public void setCardGroupIdMap(Map<String, String> cardGroupIdMap) {
        this.cardGroupIdMap = cardGroupIdMap;
    }

    public Map<String, String> getCardGroupIdMap() {
        return cardGroupIdMap;
    }

    public void setCardIdMap(Map<String, String> cardIdMap) {
        this.cardIdMap = cardIdMap;
    }

    public Map<String, String> getCardIdMap() {
        return cardIdMap;
    }

    public void setAccountToPartnermap(Map<String, String> accountToPartnermap) {
        this.accountToPartnermap = accountToPartnermap;
    }

    public Map<String, String> getAccountToPartnermap() {
        return accountToPartnermap;
    }

    public void setCardToPartnerMap(Map<String, String> cardToPartnerMap) {
        this.cardToPartnerMap = cardToPartnerMap;
    }

    public Map<String, String> getCardToPartnerMap() {
        return cardToPartnerMap;
    }

    public void setPartnerNameMap(Map<String, String> partnerNameMap) {
        this.partnerNameMap = partnerNameMap;
    }

    public Map<String, String> getPartnerNameMap() {
        return partnerNameMap;
    }

    public void setCardGroupDescMap(Map<String, String> cardGroupDescMap) {
        this.cardGroupDescMap = cardGroupDescMap;
    }

    public Map<String, String> getCardGroupDescMap() {
        return cardGroupDescMap;
    }

    public void setCardEmbossMap(Map<String, String> cardEmbossMap) {
        this.cardEmbossMap = cardEmbossMap;
    }

    public Map<String, String> getCardEmbossMap() {
        return cardEmbossMap;
    }

    public void setCardGroupToPartnerMap(Map<String, String> cardGroupToPartnerMap) {
        this.cardGroupToPartnerMap = cardGroupToPartnerMap;
    }

    public Map<String, String> getCardGroupToPartnerMap() {
        return cardGroupToPartnerMap;
    }

    public void setShowEmailpanel(boolean showEmailpanel) {
        this.showEmailpanel = showEmailpanel;
    }

    public boolean isShowEmailpanel() {
        return showEmailpanel;
    }

    public void setRoleCount(Integer roleCount) {
        this.roleCount = roleCount;
    }

    public Integer getRoleCount() {
        return roleCount;
    }

    public void setRolesToCreate(String rolesToCreate) {
        this.rolesToCreate = rolesToCreate;
    }

    public String getRolesToCreate() {
        return rolesToCreate;
    }

    public void setWarningMsg(String warningMsg) {
        this.warningMsg = warningMsg;
    }

    public String getWarningMsg() {
        return warningMsg;
    }

    public void setEmailValue(String emailValue) {
        this.emailValue = emailValue;
    }

    public String getEmailValue() {
        return emailValue;
    }

    public void displayErrorComponent(UIComponent component, boolean status) {

        RichSelectManyChoice soc = new RichSelectManyChoice();
        RichInputText rit = new RichInputText();

        if (component instanceof RichInputText) {
            rit = (RichInputText)component;
            if (status) {
                rit.setContentStyle("af_mandatoryfield");
                if (component.getId().contains("it7") || component.getId().contains("it1") ||
                    component.getId().contains("it2") || component.getId().contains("it4") || component.getId().contains("id1"))
                    rit.setStyleClass("af_mandatoryfield");


            } else {
                rit.setContentStyle("af_nonmandatoryfield");
                if (component.getId().contains("it7") || component.getId().contains("it1") ||
                    component.getId().contains("it2") || component.getId().contains("it4") || component.getId().contains("id1"))
                    rit.setStyleClass("af_nonmandatoryfield");

            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(rit);

        }  
        else if (component instanceof RichSelectManyChoice) {
            soc = (RichSelectManyChoice)component;
            if (status) {
                soc.setStyleClass("af_mandatoryfield");
                if (component.getId().contains("smc1") || component.getId().contains("soc3") || component.getId().contains("smc2") ||
                    component.getId().contains("smc3") || component.getId().contains("smc4"))
                    soc.setStyleClass("af_mandatoryfield");

            } else {
                soc.setStyleClass("af_nonmandatoryfield");
                if (component.getId().contains("smc1") || component.getId().contains("soc3") || component.getId().contains("smc2") ||
                    component.getId().contains("smc3") || component.getId().contains("smc4"))
                    soc.setStyleClass("af_nonmandatoryfield");
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(soc);
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



    public class Bindings {

        private RichSelectManyChoice partner;
        private RichSelectManyChoice account;
        private RichSelectManyChoice cardGroup;
        private RichSelectManyChoice card;
        private RichSelectManyChoice role;
        private RichSelectBooleanRadio multipleCardRadio;
        private RichSelectBooleanRadio singleCardRadio;
        private RichInputText searchStringInputtext;
        private RichPopup addEditUserPopUp;
        private RichPanelGroupLayout searchResults;
        private RichSelectManyChoice addEditPartnerId;
        private RichSelectManyChoice addEditAccountId;
        private RichSelectManyChoice addEditCardGroupId;
        private RichSelectManyChoice addEditCardId;
        private RichTable userRoleInfoTable;
        private RichInputText userFirstName;
        private RichInputText userLastName;
        private RichInputText userMiddleName;
        private RichInputText userPhoneNumber;
        private RichInputText userEmailId;
        private RichOutputText popUpPartnerId;
        private RichOutputText popUpPartnerColun;
        private RichSpacer popUpPartnerSpacer;
        private RichOutputText popUpAccountId;
        private RichOutputText popUpAccountColon;
        private RichSpacer popUpAccountSpacer;
        private RichOutputText popUpCardGroupId;
        private RichOutputText popUpCardGroupColon;
        private RichSpacer popUpCardGroupSpacer;
        private RichOutputText popUpCardId;
        private RichOutputText popUpCardColon;
        private RichSpacer popUpCardSpacer;
        private RichInputDate dateOfBirth;
        private RichPanelGroupLayout showErrorMsg;


        public void setPartner(RichSelectManyChoice partner) {
            this.partner = partner;
        }

        public RichSelectManyChoice getPartner() {
            return partner;
        }

        public void setAccount(RichSelectManyChoice account) {
            this.account = account;
        }

        public RichSelectManyChoice getAccount() {
            return account;
        }

        public void setCardGroup(RichSelectManyChoice cardGroup) {
            this.cardGroup = cardGroup;
        }

        public RichSelectManyChoice getCardGroup() {
            return cardGroup;
        }

        public void setCard(RichSelectManyChoice card) {
            this.card = card;
        }

        public RichSelectManyChoice getCard() {
            return card;
        }

        public void setRole(RichSelectManyChoice role) {
            this.role = role;
        }

        public RichSelectManyChoice getRole() {
            return role;
        }

        public void setMultipleCardRadio(RichSelectBooleanRadio multipleCardRadio) {
            this.multipleCardRadio = multipleCardRadio;
        }

        public RichSelectBooleanRadio getMultipleCardRadio() {
            return multipleCardRadio;
        }

        public void setSingleCardRadio(RichSelectBooleanRadio singleCardRadio) {
            this.singleCardRadio = singleCardRadio;
        }

        public RichSelectBooleanRadio getSingleCardRadio() {
            return singleCardRadio;
        }

        public void setSearchStringInputtext(RichInputText searchStringInputtext) {
            this.searchStringInputtext = searchStringInputtext;
        }

        public RichInputText getSearchStringInputtext() {
            return searchStringInputtext;
        }


        public void setAddEditPartnerId(RichSelectManyChoice addEditPartnerId) {
            this.addEditPartnerId = addEditPartnerId;
        }

        public RichSelectManyChoice getAddEditPartnerId() {
            return addEditPartnerId;
        }

        public void setAddEditAccountId(RichSelectManyChoice addEditAccountId) {
            this.addEditAccountId = addEditAccountId;
        }

        public RichSelectManyChoice getAddEditAccountId() {
            return addEditAccountId;
        }

        public void setAddEditCardGroupId(RichSelectManyChoice addEditCardGroupId) {
            this.addEditCardGroupId = addEditCardGroupId;
        }

        public RichSelectManyChoice getAddEditCardGroupId() {
            return addEditCardGroupId;
        }

        public void setAddEditCardId(RichSelectManyChoice addEditCardId) {
            this.addEditCardId = addEditCardId;
        }

        public RichSelectManyChoice getAddEditCardId() {
            return addEditCardId;
        }


        public void setSearchResults(RichPanelGroupLayout searchResults) {
            this.searchResults = searchResults;
        }

        public RichPanelGroupLayout getSearchResults() {
            return searchResults;
        }

        public void setAddEditUserPopUp(RichPopup addEditUserPopUp) {
            this.addEditUserPopUp = addEditUserPopUp;
        }

        public RichPopup getAddEditUserPopUp() {
            return addEditUserPopUp;
        }

        public void setUserRoleInfoTable(RichTable userRoleInfoTable) {
            this.userRoleInfoTable = userRoleInfoTable;
        }

        public RichTable getUserRoleInfoTable() {
            return userRoleInfoTable;
        }

        public void setUserFirstName(RichInputText userFirstName) {
            this.userFirstName = userFirstName;
        }

        public RichInputText getUserFirstName() {
            return userFirstName;
        }

        public void setUserLastName(RichInputText userLastName) {
            this.userLastName = userLastName;
        }

        public RichInputText getUserLastName() {
            return userLastName;
        }

        public void setUserMiddleName(RichInputText userMiddleName) {
            this.userMiddleName = userMiddleName;
        }

        public RichInputText getUserMiddleName() {
            return userMiddleName;
        }

        public void setUserPhoneNumber(RichInputText userPhoneNumber) {
            this.userPhoneNumber = userPhoneNumber;
        }

        public RichInputText getUserPhoneNumber() {
            return userPhoneNumber;
        }

        public void setUserEmailId(RichInputText userEmailId) {
            this.userEmailId = userEmailId;
        }

        public RichInputText getUserEmailId() {
            return userEmailId;
        }

        public void setPopUpPartnerId(RichOutputText popUpPartnerId) {
            this.popUpPartnerId = popUpPartnerId;
        }

        public RichOutputText getPopUpPartnerId() {
            return popUpPartnerId;
        }

        public void setPopUpPartnerColun(RichOutputText popUpPartnerColun) {
            this.popUpPartnerColun = popUpPartnerColun;
        }

        public RichOutputText getPopUpPartnerColun() {
            return popUpPartnerColun;
        }

        public void setPopUpPartnerSpacer(RichSpacer popUpPartnerSpacer) {
            this.popUpPartnerSpacer = popUpPartnerSpacer;
        }

        public RichSpacer getPopUpPartnerSpacer() {
            return popUpPartnerSpacer;
        }

        public void setPopUpAccountId(RichOutputText popUpAccountId) {
            this.popUpAccountId = popUpAccountId;
        }

        public RichOutputText getPopUpAccountId() {
            return popUpAccountId;
        }

        public void setPopUpAccountColon(RichOutputText popUpAccountColon) {
            this.popUpAccountColon = popUpAccountColon;
        }

        public RichOutputText getPopUpAccountColon() {
            return popUpAccountColon;
        }

        public void setPopUpAccountSpacer(RichSpacer popUpAccountSpacer) {
            this.popUpAccountSpacer = popUpAccountSpacer;
        }

        public RichSpacer getPopUpAccountSpacer() {
            return popUpAccountSpacer;
        }

        public void setPopUpCardGroupId(RichOutputText popUpCardGroupId) {
            this.popUpCardGroupId = popUpCardGroupId;
        }

        public RichOutputText getPopUpCardGroupId() {
            return popUpCardGroupId;
        }

        public void setPopUpCardGroupColon(RichOutputText popUpCardGroupColon) {
            this.popUpCardGroupColon = popUpCardGroupColon;
        }

        public RichOutputText getPopUpCardGroupColon() {
            return popUpCardGroupColon;
        }

        public void setPopUpCardGroupSpacer(RichSpacer popUpCardGroupSpacer) {
            this.popUpCardGroupSpacer = popUpCardGroupSpacer;
        }

        public RichSpacer getPopUpCardGroupSpacer() {
            return popUpCardGroupSpacer;
        }

        public void setPopUpCardId(RichOutputText popUpCardId) {
            this.popUpCardId = popUpCardId;
        }

        public RichOutputText getPopUpCardId() {
            return popUpCardId;
        }

        public void setPopUpCardColon(RichOutputText popUpCardColon) {
            this.popUpCardColon = popUpCardColon;
        }

        public RichOutputText getPopUpCardColon() {
            return popUpCardColon;
        }

        public void setPopUpCardSpacer(RichSpacer popUpCardSpacer) {
            this.popUpCardSpacer = popUpCardSpacer;
        }

        public RichSpacer getPopUpCardSpacer() {
            return popUpCardSpacer;
        }

        public void setDateOfBirth(RichInputDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public RichInputDate getDateOfBirth() {
            return dateOfBirth;
        }

        public void setShowErrorMsg(RichPanelGroupLayout showErrorMsg) {
            this.showErrorMsg = showErrorMsg;
        }

        public RichPanelGroupLayout getShowErrorMsg() {
            return showErrorMsg;
        }
    }
}

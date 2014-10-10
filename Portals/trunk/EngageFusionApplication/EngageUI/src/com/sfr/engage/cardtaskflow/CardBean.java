package com.sfr.engage.cardtaskflow;


import com.sfr.core.bean.User;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.core.ReportBundle;
import com.sfr.engage.core.ValueListSplit;
import com.sfr.engage.model.queries.rvo.PrtExportInfoRVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtViewCardsVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtViewVehicleDriverVORowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.ADFUtils;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;
import com.sfr.util.validations.Conversion;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

import java.sql.SQLException;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectManyShuttle;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class CardBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    private HttpSession session;
    private Boolean isTableVisible = false;
    private List<PartnerInfo> partnerInfoList;
    private String cardGroupSubtypePassValues;
    private String cardGroupMaintypePassValue;
    private String cardGroupSeqPassValues;
    private String lang;
    public static final ADFLogger LOGGER = AccessDataControl.getSFRLogger();
    private AccessDataControl accessDC = new AccessDataControl();
    private List<SelectItem> accountIdList;
    private List<String> accountIdValue;
    private List<SelectItem> cardGroupList;
    private List<String> cardGroupValue;
    private List<SelectItem> statusList;
    private List<String> statusValue;
    private List<SelectItem> partnerIdList;
    private List<String> partnerIdValue;
    private ResourceBundle resourceBundle;
    private boolean driverPGL = false;
    private boolean vehiclePGL = false;
    private String cardAssociation = null;
    private String associatedAccount = null;
    private List<SelectItem> vehicleNumberList;
    private String vehicleNumberValue;
    private List<SelectItem> driverNameList;
    private String driverNameValue;
    private Map<String, String> truckDriverList = new HashMap<String, String>();
    private String displayDriverName;
    private String displayVehicleName;
    private String strViewCardTotalColumns = "";
    private String strViewCardExtraColumns = "";
    private String strViewCardPrepopulatedColumns = "";
    private List shuttleList = new ArrayList();
    private List shuttleValue;
    private boolean shuttleStatus = false;
    private String driverNumber = null;
    private String vehicleNumber = null;
    private RichSelectOneChoice driverNameAssociation;
    private String warningMsg = null;
    private String infoMsgAssociated = null;
    private String infoMsgModifiedDate = null;
    private String infoMsgModifiedBy = null;
    private boolean showErrorMsgEditFlag = false;
    private String internalCardNumber = null;
    private String cardEmbossNum = null;
    private String internalName = null;
    private String DriverNumber = null;
    private String driverName = null;
    private String VehicleNumber = null;
    private String vehicleModifiedBy = null;
    private String vehicleModifiedDate = null;
    private String driverModifiedBy = null;
    private String driverModifiedDate = null;
    private boolean vehicleModifiedByVisible = false;
    private boolean vehicleModifiedDateVisible = false;
    private boolean driverModifiedByVisible = false;
    private boolean driverModifiedDateVisible = false;
    private boolean showEditInfoMessage = false;
    private String accountQuery = "(";
    private String cardGroupQuery = "(";
    private String expiryQuery = "";
    private Map<String, String> mapAccountListValue;
    private Map<String, String> mapCardGroupListValue;
    private String contentType;
    private String fileName;
    private boolean reset = false;
    private List<String> cardTypeNameList = new ArrayList<String>();
    private String currencyCode;
    private Conversion conversionUtility;
    private Locale locale;
    private boolean blockedDateTime;
    private User user = null;
    private Boolean isEditVisible;
    private static final String VIEW_CARD_ACCOUNT_QUERY_LITERAL = "view_card_account_Query";
    private static final String VIEW_CARD_CARDGROUP_QUERY_LITRERAL = "view_card_cardGroup_Query";
    private static final String VIEW_CARD_EXPIRY_QUERY_LITRERAL = "view_card_expiry_Query";
    private static final String PRTVIEWCARDSVO1ITERATOR_LITRERAL = "PrtViewCardsVO1Iterator";


    public CardBean() {
        super();

        conversionUtility = new Conversion();
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Constructor of View Cards");
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        statusValue = new ArrayList<String>();
        resourceBundle = new EngageResourceBundle();
        partnerIdValue = new ArrayList<String>();
        if (user == null) {
            user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
        }

        isEditVisible = true;
        if (user.getRoleList().get(0).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR) &&
            (user.getRoleList().get(0).getIdString().get(0).contains("CG"))) {

            isEditVisible = false;
            LOGGER.info("cg manager-----------------------------------------------------" + isEditVisible);

        }

        LOGGER.info("final isEditVisible value-----------------------------------------------------" + isEditVisible);
        if (session.getAttribute("Partner_Object_List") != null) {
            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
            if (partnerInfoList != null && partnerInfoList.size() > 0) {
                partnerIdList = new ArrayList<SelectItem>();
                accountIdList = new ArrayList<SelectItem>();
                accountIdValue = new ArrayList<String>();
                cardGroupList = new ArrayList<SelectItem>();
                cardGroupValue = new ArrayList<String>();
                for (int i = 0; i < partnerInfoList.size(); i++) {
                    lang = partnerInfoList.get(0).getCountry().toString().trim();
                    if (partnerInfoList.get(i).getPartnerName() != null && partnerInfoList.get(i).getPartnerValue() != null) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.setLabel(partnerInfoList.get(i).getPartnerName().toString());
                        selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString());
                        partnerIdList.add(selectItem);
                        partnerIdValue.add(partnerInfoList.get(i).getPartnerValue().toString());
                    }

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
                            }
                        }
                    }
                }

                Collections.sort(accountIdList, comparator);
                Collections.sort(cardGroupList, comparator);
            }
        }


        if (session != null) {
            if (session.getAttribute(VIEW_CARD_ACCOUNT_QUERY_LITERAL) != null) {
                accountQuery = session.getAttribute(VIEW_CARD_ACCOUNT_QUERY_LITERAL).toString().trim();
                mapAccountListValue = (Map<String, String>)session.getAttribute("map_Account_List");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account Query & mapAccountList is found");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account " + accountQuery);
            }
            if (session.getAttribute(VIEW_CARD_CARDGROUP_QUERY_LITRERAL) != null) {
                cardGroupQuery = session.getAttribute(VIEW_CARD_CARDGROUP_QUERY_LITRERAL).toString().trim();
                mapCardGroupListValue = (Map<String, String>)session.getAttribute("map_CardGroup_List");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Query & mapCardGroupList is found");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup " + cardGroupQuery);
            }
            if (session.getAttribute(VIEW_CARD_EXPIRY_QUERY_LITRERAL) != null) {
                expiryQuery = session.getAttribute(VIEW_CARD_EXPIRY_QUERY_LITRERAL).toString().trim();
            }
        }

        statusValue.add(Constants.ENGAGE_CARD_ACTIVE);

        if (lang != null) {
            currencyCode = conversionUtility.getCurrencyCode(lang);
            locale = conversionUtility.getLocaleFromCountryCode(lang);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "currencyCode :" + currencyCode);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Locale :" + locale);
        } else {
            currencyCode = conversionUtility.getCurrencyCode("SE");
            locale = conversionUtility.getLocaleFromCountryCode("SE");
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Default:currencyCode :" + currencyCode);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Default:Locale :" + locale);
        }
        displayErrorComponent(getBindings().getPartner(), false);
        displayErrorComponent(getBindings().getAccount(), false);
        displayErrorComponent(getBindings().getCardGroup(), false);
        displayErrorComponent(getBindings().getStatus(), false);
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting Constructor of View Cards");

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

    public String populateStringValues(String var) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside populateStringValues method of View Cards");
        String passingValues = null;
        if (var != null) {
            String lovValues = var.trim();
            String selectedValues = lovValues.substring(1, lovValues.length() - 1);
            passingValues = selectedValues.trim();
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting populateStringValues method of View Cards");
        return passingValues;
    }

    public String[] stringSplitter(String passedVal) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside StringConversion method of View Cards");
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting StringConversion method of View Cards");
        return passedVal.split(",");
    }

    /**
     * @param valueChangeEvent
     */
    public void accountValueChangeListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside accountValueChangeListener method of View Cards");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null) {
            String[] accountString = stringSplitter(populateStringValues(valueChangeEvent.getNewValue().toString()));
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();
            for (int z = 0; z < partnerInfoList.size(); z++) {

                if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                        for (int j = 0; j < accountString.length; j++) {
                            if (partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() != null &&
                                partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().equals(accountString[j].trim()) &&
                                (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                                 partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() > 0)) {

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
                                }

                            }
                        }
                    }
                    Collections.sort(accountIdList, comparator);
                    Collections.sort(cardGroupList, comparator);
                }
            }

        } else {
            getBindings().getCardGroup().setValue(null);
            getBindings().getStatus().setValue(null);
            this.cardGroupValue = null;
            this.statusValue = null;
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting accountValueChangeListener method of View Cards");

    }


    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    /**
     * @param errorVar
     * @return
     */
    public String showErrorMessage(String errorVar) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside showErrorMessage method of View Cards");
        if (errorVar != null && resourceBundle.containsKey(errorVar)) {

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject(errorVar), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;

        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting showErrorMessage method of View Cards");
        return null;
    }

    public List<SelectItem> getStatusList() {

        if (statusList == null) {
            statusList = new ArrayList<SelectItem>();
            SelectItem selectItem = new SelectItem();

            if (resourceBundle.containsKey(Constants.UNBLOCKED_PLURAL_LITERAL)) {
                selectItem.setLabel(resourceBundle.getObject(Constants.UNBLOCKED_PLURAL_LITERAL).toString());
                selectItem.setValue(Constants.ENGAGE_CARD_ACTIVE);
                statusList.add(selectItem);
            }
            SelectItem selectItem1 = new SelectItem();
            if (resourceBundle.containsKey(Constants.TEMPORARY_BLOCKED_LITERAL)) {
                selectItem1.setLabel(resourceBundle.getObject(Constants.TEMPORARY_BLOCKED_LITERAL).toString());
                selectItem1.setValue(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED);
                statusList.add(selectItem1);
            }
            SelectItem selectItem2 = new SelectItem();
            if (resourceBundle.containsKey(Constants.PERMANENT_BLOCKED_LITERAL)) {
                selectItem2.setLabel(resourceBundle.getObject(Constants.PERMANENT_BLOCKED_LITERAL).toString());
                selectItem2.setValue(Constants.ENGAGE_CARD_PERMANENT_BLOCKED);
                statusList.add(selectItem2);
            }
            SelectItem selectItem3 = new SelectItem();
            if (resourceBundle.containsKey(Constants.EXPIRED_LITERAL)) {
                selectItem3.setLabel(resourceBundle.getObject(Constants.EXPIRED_LITERAL).toString());
                selectItem3.setValue("E");
                statusList.add(selectItem3);
            }
        }
        return statusList;
    }

    public void setStatusValue(List<String> statusValue) {
        this.statusValue = statusValue;
    }

    public List<String> getStatusValue() {
        return statusValue;
    }

    public void setAccountIdList(List<SelectItem> accountIdList) {
        this.accountIdList = accountIdList;
    }

    public void setPartnerIdList(List<SelectItem> partnerIdList) {
        this.partnerIdList = partnerIdList;
    }

    public List<SelectItem> getPartnerIdList() {
        return partnerIdList;
    }

    public void setPartnerInfoList(List<PartnerInfo> partnerInfoList) {
        this.partnerInfoList = partnerInfoList;
    }

    public List<PartnerInfo> getPartnerInfoList() {
        return partnerInfoList;
    }

    public void partnerValueChangeListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside partnerValueChangeListener method of View Cards");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null) {
            accountIdList = new ArrayList<SelectItem>();
            accountIdValue = new ArrayList<String>();
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();
            statusValue = new ArrayList<String>();
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
                                }
                            }

                        }
                    }

                }
                Collections.sort(accountIdList, comparator);
                Collections.sort(cardGroupList, comparator);
            }
            statusValue.add(Constants.ENGAGE_CARD_ACTIVE);
            statusValue.add(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED);
            statusValue.add(Constants.ENGAGE_CARD_PERMANENT_BLOCKED);
            statusValue.add("E");

        } else {
            getBindings().getCardGroup().setValue(null);
            getBindings().getAccount().setValue(null);
            this.cardGroupValue = null;
            this.cardGroupList = null;
            this.accountIdValue = null;
            this.accountIdList = null;
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting partnerValueChangeListener method of View Cards");

    }

    public void populateCardGroupValues(String cardGrpVar) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside populateCardGroupValues method of View Cards");
        String[] cardGroupvalues;
        int cardGroupCount = 0;

        String cardGroupMaintype = "";
        String cardGroupSubtype = "";
        String cardGroupSeq = "";

        cardGroupSubtypePassValues = "";
        cardGroupMaintypePassValue = "";
        cardGroupSeqPassValues = "";

        if (cardGrpVar != null) {
            if (cardGrpVar.contains(",")) {
                cardGroupvalues = cardGrpVar.split(",");
                cardGroupCount = cardGroupvalues.length;
            } else {
                cardGroupCount = 1;
                cardGroupvalues = new String[1];
                cardGroupvalues[0] = cardGrpVar;
            }

            for (int cGrp = 0; cGrp < cardGroupCount; cGrp++) {
                cardGroupMaintype = cardGroupMaintype + cardGroupvalues[cGrp].trim().substring(0, Constants.THREE);
                cardGroupMaintype = cardGroupMaintype + ",";

                cardGroupSubtype = cardGroupSubtype + cardGroupvalues[cGrp].trim().substring(Constants.THREE, Constants.SIX);
                cardGroupSubtype = cardGroupSubtype + ",";

                cardGroupSeq = cardGroupSeq + cardGroupvalues[cGrp].trim().substring(Constants.SIX);
                cardGroupSeq = cardGroupSeq + ",";
            }

            cardGroupMaintypePassValue = cardGroupMaintype.trim().substring(0, cardGroupMaintype.length() - 1);
            cardGroupSubtypePassValues = cardGroupSubtype.trim().substring(0, cardGroupSubtype.length() - 1);
            cardGroupSeqPassValues = cardGroupSeq.trim().substring(0, cardGroupSeq.length() - 1);

            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " " + "card group main type======>" + cardGroupMaintypePassValue);
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " " + "card group sub type===>" + cardGroupSubtypePassValues);
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " " + "card group sequence value====>" + cardGroupSeqPassValues);
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting populateCardGroupValues method of View Cards");
    }

    public void setIsTableVisible(Boolean isTableVisible) {
        this.isTableVisible = isTableVisible;
    }

    public Boolean getIsTableVisible() {
        return isTableVisible;
    }

    public String searchViewCardActionEvent(ActionEvent actionEvent) {
        searchResults();
        return null;
    }

    public String searchResults() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside searchResults method of View Cards");
        isTableVisible = false;
        String statusPassingValues = null;
        String currentDate = "";

        try {
            if (getBindings().getPartner().getValue() != null) {
                displayErrorComponent(getBindings().getPartner(), false);
                if (getBindings().getAccount().getValue() != null) {
                    displayErrorComponent(getBindings().getAccount(), false);
                } else {
                    showErrorMessage("ENGAGE_NO_ACCOUNT");
                    displayErrorComponent(getBindings().getAccount(), true);
                    return null;
                }

                if (getBindings().getStatus().getValue() != null) {
                    statusPassingValues = populateStringValues(getBindings().getStatus().getValue().toString());
                    displayErrorComponent(getBindings().getStatus(), false);
                } else {
                    showErrorMessage("ENGAGE_NO_STATUS");
                    displayErrorComponent(getBindings().getStatus(), true);
                    return null;
                }

                if (getBindings().getCardGroup().getValue() != null) {
                    displayErrorComponent(getBindings().getCardGroup(), false);
                } else {
                    showErrorMessage("ENGAGE_NO_CARD_GROUP");
                    displayErrorComponent(getBindings().getCardGroup(), true);
                    return null;
                }

                if (getBindings().getPartner().getValue() != null) {
                    ViewObject vo = ADFUtils.getViewObject(PRTVIEWCARDSVO1ITERATOR_LITRERAL);

                    if (accountQuery.length() > 1 && accountQuery != null && cardGroupQuery.length() > 1 && expiryQuery != null &&
                        vo.getWhereClause() != null) {

                        if (((accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim())) ||
                            ((accountQuery + " AND " + cardGroupQuery + " AND " + expiryQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim()))) {
                            if (mapAccountListValue != null) {
                                for (int i = 0; i < mapAccountListValue.size(); i++) {
                                    vo.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL + i);
                                }
                            } else {
                                vo.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL);
                            }
                            if (mapCardGroupListValue != null) {
                                for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                                    String values = "cardGroup" + i;
                                    vo.removeNamedWhereClauseParam(values);
                                }
                            } else {
                                vo.removeNamedWhereClauseParam("cardGroup");
                            }
                            if (expiryQuery.contains(Constants.CURRENT_DATE_LITERAL)) {
                                vo.removeNamedWhereClauseParam(Constants.CURRENT_DATE_LITERAL);
                            }
                            vo.setWhereClause("");
                            vo.executeQuery();
                        } else {
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
                    resetTableFilter();
                    accountQuery = "(";
                    cardGroupQuery = "(";
                    expiryQuery = "";

                    if (accountIdValue.size() > Constants.ONEFIFTY) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
                        mapAccountListValue = ValueListSplit.callValueList(accountIdValue.size(), accountIdValue);
                        for (int i = 0; i < mapAccountListValue.size(); i++) {
                            String values = Constants.ACCOUNT_LITERAL + i;
                            accountQuery = accountQuery + "INSTR(:" + values + ",ACCOUNT_ID)<>0 OR ";
                        }
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Account Query Values =" + accountQuery);
                        accountQuery = accountQuery.substring(0, accountQuery.length() - Constants.THREE);
                        accountQuery = accountQuery + ") ";
                    } else {
                        mapAccountListValue = null;
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                        accountQuery = "(INSTR(:account,ACCOUNT_ID)<>0 ) ";
                    }

                    if (cardGroupValue.size() > Constants.ONEFIFTY) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values > 150 ");
                        mapCardGroupListValue = ValueListSplit.callValueList(cardGroupValue.size(), cardGroupValue);
                        for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                            String values = "cardGroup" + i;
                            cardGroupQuery =
                                    cardGroupQuery + "INSTR(:" + values + ",PARTNER_ID||CARDGROUP_MAIN_TYPE||CARDGROUP_SUB_TYPE||CARDGROUP_SEQ)<>0 OR ";
                        }
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "CARDGROUP Query Values =" + cardGroupQuery);
                        cardGroupQuery = cardGroupQuery.substring(0, cardGroupQuery.length() - Constants.THREE);
                        cardGroupQuery = cardGroupQuery + ") ";
                    } else {
                        mapCardGroupListValue = null;
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 ");
                        cardGroupQuery = "(INSTR(:cardGroup,PARTNER_ID||CARDGROUP_MAIN_TYPE||CARDGROUP_SUB_TYPE||CARDGROUP_SEQ)<>0) ";
                    }

                    vo.setNamedWhereClauseParam("partnerId", getBindings().getPartner().getValue().toString().trim());

                    vo.setNamedWhereClauseParam(Constants.COUNTRY_CD_LITERAL, lang);

                    //for active,temporary blocked status or only active or only temporary blocked status(01, 0, 1)
                    if (!statusPassingValues.contains(Constants.ENGAGE_CARD_PERMANENT_BLOCKED) && !statusPassingValues.contains("E")) {
                        expiryQuery = "(CARD_EXPIRY > =: currentDate)";
                        vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, statusPassingValues);
                        vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                    } else {
                        if (!statusPassingValues.contains("E")) {
                            if (statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) &&
                                !statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED)) {
                                //for active & permanent blocked status(02)
                                vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, statusPassingValues);
                                expiryQuery =
                                        "((BLOCK_ACTION = '" + Constants.ENGAGE_CARD_ACTIVE + "' AND CARD_EXPIRY > =: currentDate) OR (BLOCK_ACTION = '" +
                                        Constants.ENGAGE_CARD_PERMANENT_BLOCKED + "'))";
                                vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                            } else if (!statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) &&
                                       statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED)) {
                                //for active temporary blocked status(01)
                                vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, statusPassingValues);
                                expiryQuery =
                                        "((BLOCK_ACTION = '" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "' AND CARD_EXPIRY > =: currentDate) OR (BLOCK_ACTION = '" +
                                        Constants.ENGAGE_CARD_PERMANENT_BLOCKED + "'))";
                                vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                            } else if (statusPassingValues.equalsIgnoreCase(Constants.ENGAGE_CARD_PERMANENT_BLOCKED)) {
                                //for permanent blocked status(2)
                                vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, statusPassingValues);
                                vo.setWhereClause(accountQuery + "AND " + cardGroupQuery);
                            } else {
                                //for active,permanent and temporary blocked status(012)
                                String status =
                                    Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "," + Constants.ENGAGE_CARD_PERMANENT_BLOCKED;
                                vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                                expiryQuery =
                                        "((BLOCK_ACTION IN ('" + Constants.ENGAGE_CARD_ACTIVE + "','" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "') AND CARD_EXPIRY > =: currentDate) OR (BLOCK_ACTION = '" +
                                        Constants.ENGAGE_CARD_PERMANENT_BLOCKED + "'))";
                                vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                            }
                        } else {
                            if (statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) &&
                                !statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED)) {
                                if (statusPassingValues.contains(Constants.ENGAGE_CARD_PERMANENT_BLOCKED)) {
                                    //for active, expired and permanent blocked status(02E)
                                    String status =
                                        Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "," + Constants.ENGAGE_CARD_PERMANENT_BLOCKED;
                                    vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                                    expiryQuery =
                                            "((BLOCK_ACTION = '" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "' AND CARD_EXPIRY < =: currentDate) OR BLOCK_ACTION IN ('" +
                                            Constants.ENGAGE_CARD_ACTIVE + "','" + Constants.ENGAGE_CARD_PERMANENT_BLOCKED + "'))";
                                } else {
                                    //for expired and active status(0E)
                                    String status = Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED;
                                    vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                                    expiryQuery =
                                            "((BLOCK_ACTION = '" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "' AND CARD_EXPIRY < =: currentDate) OR (BLOCK_ACTION = '" +
                                            Constants.ENGAGE_CARD_ACTIVE + "'))";
                                }

                                vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                            } else if (!statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) &&
                                       statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED)) {
                                if (statusPassingValues.contains(Constants.ENGAGE_CARD_PERMANENT_BLOCKED)) {
                                    //for expired ,permanent & temporary blocked status(12E)
                                    String status =
                                        Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "," + Constants.ENGAGE_CARD_PERMANENT_BLOCKED;
                                    vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                                    expiryQuery =
                                            "((BLOCK_ACTION = '" + Constants.ENGAGE_CARD_ACTIVE + "' AND CARD_EXPIRY < =: currentDate) OR (BLOCK_ACTION IN ('" +
                                            Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "','" + Constants.ENGAGE_CARD_PERMANENT_BLOCKED + "')))";
                                } else {
                                    //for expired and temporary blocked status(1E)
                                    String status = Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED;
                                    vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                                    expiryQuery =
                                            "((BLOCK_ACTION = '" + Constants.ENGAGE_CARD_ACTIVE + "' AND CARD_EXPIRY < =: currentDate) OR (BLOCK_ACTION = '" +
                                            Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "'))";
                                }
                                vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                            } else if (statusPassingValues.contains(Constants.ENGAGE_CARD_PERMANENT_BLOCKED) &&
                                       !statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) &&
                                       !statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED)) {
                                //for expired and permanent blocked status(2E)
                                String status =
                                    Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "," + Constants.ENGAGE_CARD_PERMANENT_BLOCKED;
                                vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                                expiryQuery =
                                        "((BLOCK_ACTION IN ('" + Constants.ENGAGE_CARD_ACTIVE + "','" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "') AND CARD_EXPIRY < =: currentDate) OR (BLOCK_ACTION = '" +
                                        Constants.ENGAGE_CARD_PERMANENT_BLOCKED + "'))";
                                vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                            } else if (statusPassingValues.equalsIgnoreCase("E")) {
                                //for expired status(E)
                                String status = Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED;
                                vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                                expiryQuery =
                                        "(BLOCK_ACTION IN ('" + Constants.ENGAGE_CARD_ACTIVE + "','" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "') AND CARD_EXPIRY < =: currentDate)";
                                vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                            } else if (statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) &&
                                       statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED) &&
                                       !statusPassingValues.contains(Constants.ENGAGE_CARD_PERMANENT_BLOCKED)) {
                                //for active, expired and temporary blocked status(01E)
                                String status = Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED;
                                vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                                expiryQuery = "BLOCK_ACTION IN ('" + Constants.ENGAGE_CARD_ACTIVE + "','" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "')";
                                vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                            } else {
                                //for All combination - 012E
                                String status =
                                    Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "," + Constants.ENGAGE_CARD_PERMANENT_BLOCKED;
                                vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                                vo.setWhereClause(accountQuery + "AND " + cardGroupQuery);
                            }
                        }
                    }

                    if (accountIdValue.size() > Constants.ONEFIFTY) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
                        mapAccountListValue = ValueListSplit.callValueList(accountIdValue.size(), accountIdValue);
                        for (int i = 0; i < mapAccountListValue.size(); i++) {


                            vo.defineNamedWhereClauseParam(Constants.ACCOUNT_LITERAL + i, mapAccountListValue.get(Constants.LISTNAME_LITERAL + i), null);
                        }
                    } else {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                        vo.defineNamedWhereClauseParam(Constants.ACCOUNT_LITERAL, populateStringValues(getBindings().getAccount().getValue().toString()),
                                                       null);
                    }

                    if (cardGroupValue.size() > Constants.ONEFIFTY) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values > 150 ");
                        mapCardGroupListValue = ValueListSplit.callValueList(cardGroupValue.size(), cardGroupValue);
                        for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                            String values = "cardGroup" + i;

                            vo.defineNamedWhereClauseParam(values, mapCardGroupListValue.get(Constants.LISTNAME_LITERAL + i), null);
                        }
                    } else {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 ");
                        vo.defineNamedWhereClauseParam("cardGroup", populateStringValues(getBindings().getCardGroup().getValue().toString()), null);
                    }

                    if (!statusPassingValues.equalsIgnoreCase(Constants.ENGAGE_CARD_PERMANENT_BLOCKED) &&
                        !(statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) &&
                          statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED) &&
                          statusPassingValues.contains(Constants.ENGAGE_CARD_PERMANENT_BLOCKED) && statusPassingValues.contains("E")) &&
                        !(statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) &&
                          statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED) && statusPassingValues.contains("E"))) {
                        Date dateNow = new java.util.Date();
                        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yy");
                        currentDate = dateformat.format(dateNow);
                        vo.defineNamedWhereClauseParam(Constants.CURRENT_DATE_LITERAL, currentDate, null);
                    }

                    vo.executeQuery();

                    session.setAttribute(VIEW_CARD_ACCOUNT_QUERY_LITERAL, accountQuery);
                    session.setAttribute("view_card_map_Account_List", mapAccountListValue);
                    session.setAttribute(VIEW_CARD_CARDGROUP_QUERY_LITRERAL, cardGroupQuery);
                    session.setAttribute("view_card_map_CardGroup_List", mapCardGroupListValue);
                    session.setAttribute(VIEW_CARD_EXPIRY_QUERY_LITRERAL, expiryQuery);
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Queries are saved in session");
                    isTableVisible = true;
                }
            } else {
                showErrorMessage("ENGAGE_NO_PARTNER");
                displayErrorComponent(getBindings().getPartner(), true);
                return null;
            }
        } catch (Exception e) {
            LOGGER.severe(e);
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting searchResults method of View Cards");
        return null;
    }

    public void setCardGroupSubtypePassValues(String cardGroupSubtypePassValues) {
        this.cardGroupSubtypePassValues = cardGroupSubtypePassValues;
    }

    public String getCardGroupSubtypePassValues() {
        return cardGroupSubtypePassValues;
    }

    public void setCardGroupMaintypePassValue(String cardGroupMaintypePassValue) {
        this.cardGroupMaintypePassValue = cardGroupMaintypePassValue;
    }

    public String getCardGroupMaintypePassValue() {
        return cardGroupMaintypePassValue;
    }

    public void setCardGroupSeqPassValues(String cardGroupSeqPassValues) {
        this.cardGroupSeqPassValues = cardGroupSeqPassValues;
    }

    public String getCardGroupSeqPassValues() {
        return cardGroupSeqPassValues;
    }

    public void clearSearchListener(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside clearSearchListener method of View Cards");
        getBindings().getPartner().setValue(null);
        getBindings().getStatus().setValue(null);
        this.partnerIdValue = null;
        this.accountIdValue = null;
        accountIdList = new ArrayList<SelectItem>();
        this.cardGroupValue = null;
        cardGroupList = new ArrayList<SelectItem>();
        statusValue = new ArrayList<String>();
        statusValue.add(Constants.ENGAGE_CARD_ACTIVE);
        isTableVisible = false;
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting clearSearchListener method of View Cards");
    }

    public void radioButtonValueChangeListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside radioButtonValueChangeListener method of View Cards");
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().equals(Constants.DRIVER_LITERAL)) {
                this.getBindings().getVehicleNumber().setValue(null);
                this.displayVehicleName = null;
                showErrorMsgEditFlag = false;
                driverPGL = true;
                vehiclePGL = false;
                vehicleModifiedByVisible = false;
                vehicleModifiedDateVisible = false;
                driverModifiedByVisible = false;
                driverModifiedDateVisible = false;
                populateValue(valueChangeEvent.getNewValue().toString());

                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NUMBER_LITERAL) != null) {
                    driverNameValue = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NUMBER_LITERAL).toString().trim();
                    if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_MODIFIED_BY_LITERAL) != null &&
                        AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedDate") != null) {
                        vehicleModifiedByVisible = false;
                        vehicleModifiedDateVisible = false;
                        driverModifiedByVisible = true;
                        driverModifiedDateVisible = true;
                        driverModifiedBy = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_MODIFIED_BY_LITERAL).toString().trim();
                        driverModifiedDate = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedDate").toString().trim();
                    }
                }

                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NAME_LITERAL) != null) {
                    displayDriverName = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NAME_LITERAL).toString().trim();
                }

                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNumber());
            } else if (valueChangeEvent.getNewValue().equals(Constants.VEHICLE_LITERAL)) {
                this.getBindings().getDriverNumber().setValue(null);
                showErrorMsgEditFlag = false;
                this.displayDriverName = null;
                driverPGL = false;
                vehiclePGL = true;
                populateValue(valueChangeEvent.getNewValue().toString());
                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_NUMBER_LITERAL) != null) {
                    vehicleNumberValue = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_NUMBER_LITERAL).toString().trim();
                    if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_MODIFIED_BY_LITERAL) != null &&
                        AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_MODIFIED_DATE_LITERAL) != null) {
                        driverModifiedByVisible = false;
                        driverModifiedDateVisible = false;
                        vehicleModifiedByVisible = true;
                        vehicleModifiedDateVisible = true;
                        vehicleModifiedBy =
                                AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_MODIFIED_BY_LITERAL).toString().trim();
                        vehicleModifiedDate =
                                AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_MODIFIED_DATE_LITERAL).toString().trim();
                    }
                }

                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.INTERNAL_NAME_LITERAL) != null) {
                    displayVehicleName = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.INTERNAL_NAME_LITERAL).toString().trim();
                }

                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleNumber());

            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting radioButtonValueChangeListener method of View Cards");
    }

    public void setDriverPGL(boolean driverPGL) {
        this.driverPGL = driverPGL;
    }

    public boolean isDriverPGL() {
        return driverPGL;
    }

    public void setVehiclePGL(boolean vehiclePGL) {
        this.vehiclePGL = vehiclePGL;
    }

    public boolean isVehiclePGL() {
        return vehiclePGL;
    }

    public void statusValueChangeListener(ValueChangeEvent valueChangeEvent) {
        isTableVisible = false;
    }

    public void cardGroupValueChangeListener(ValueChangeEvent valueChangeEvent) {
        isTableVisible = false;
    }

    public void setCardAssociation(String cardAssociation) {
        this.cardAssociation = cardAssociation;
    }

    public String getCardAssociation() {
        return cardAssociation;
    }


    public void populateValue(String paramType) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside populateValue method of View Cards");
        if (paramType != null) {
            associatedAccount = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ASSOCIATED_ACOUNT_LITERAL).toString().trim();
            if (paramType.equals(Constants.VEHICLE_LITERAL) || paramType.equals(Constants.DRIVER_LITERAL)) {
                if (vehiclePGL) {
                    vehicleNumberList = new ArrayList<SelectItem>();
                }
                if (driverPGL) {
                    driverNameList = new ArrayList<SelectItem>();
                }
                ViewObject vo = ADFUtils.getViewObject("PrtViewVehicleDriverVO1Iterator");
                if (associatedAccount != null) {
                    vo.setNamedWhereClauseParam("accountValue", associatedAccount);
                }
                vo.setNamedWhereClauseParam(Constants.COUNTRY_CD_LITERAL, lang);
                vo.setNamedWhereClauseParam("paramValue", paramType);
                if (paramType.equals(Constants.DRIVER_LITERAL)) {
                    vo.setNamedWhereClauseParam("driverNumber", null);
                } else {
                    vo.setNamedWhereClauseParam("vehicleNumber", null);
                }
                vo.executeQuery();

                if (vo.getEstimatedRowCount() > 0) {
                    for (int n = 0; n < vo.getEstimatedRowCount(); n++) {
                        while (vo.hasNext()) {
                            PrtViewVehicleDriverVORowImpl currRow = (PrtViewVehicleDriverVORowImpl)vo.next();
                            if (currRow != null) {
                                if (paramType.equals(Constants.VEHICLE_LITERAL)) {
                                    SelectItem selectItem = new SelectItem();
                                    if (currRow.getAttribute(Constants.VEHICLE_NUMBER_LITERAL) != null) {
                                        selectItem.setLabel(currRow.getVehicleNumber().toString().trim());
                                        selectItem.setValue(currRow.getVehicleNumber().toString().trim());
                                        truckDriverList.put(currRow.getVehicleNumber().toString(), currRow.getInternalName());
                                    }
                                    vehicleNumberList.add(selectItem);
                                } else {
                                    SelectItem selectItem = new SelectItem();
                                    if (currRow.getAttribute(Constants.DRIVER_NUMBER_LITERAL) != null) {
                                        selectItem.setLabel(currRow.getDriverNumber().toString().trim());
                                        selectItem.setValue(currRow.getDriverNumber().toString().trim());
                                        truckDriverList.put(currRow.getDriverNumber().toString(), currRow.getDriverName());
                                    }
                                    driverNameList.add(selectItem);
                                }
                            }
                        }
                    }
                }
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting populateValue method of View Cards");
    }

    public void setAssociatedAccount(String associatedAccount) {
        this.associatedAccount = associatedAccount;
    }

    public String getAssociatedAccount() {
        return associatedAccount;
    }

    public void setVehicleNumberList(List<SelectItem> vehicleNumberList) {
        this.vehicleNumberList = vehicleNumberList;
    }

    public List<SelectItem> getVehicleNumberList() {
        return vehicleNumberList;
    }

    public void setVehicleNumberValue(String vehicleNumberValue) {
        this.vehicleNumberValue = vehicleNumberValue;
    }

    public String getVehicleNumberValue() {
        return vehicleNumberValue;
    }

    public void setDriverNameList(List<SelectItem> driverNameList) {
        this.driverNameList = driverNameList;
    }

    public List<SelectItem> getDriverNameList() {
        return driverNameList;
    }

    public void setDriverNameValue(String driverNameValue) {
        this.driverNameValue = driverNameValue;
    }

    public String getDriverNameValue() {
        return driverNameValue;
    }

    public void setTruckDriverList(Map<String, String> truckDriverList) {
        this.truckDriverList = truckDriverList;
    }

    public Map<String, String> getTruckDriverList() {
        return truckDriverList;
    }

    public void driverValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            displayDriverName = truckDriverList.get(valueChangeEvent.getNewValue().toString());
        }
    }

    public void setDisplayDriverName(String displayDriverName) {
        this.displayDriverName = displayDriverName;
    }

    public String getDisplayDriverName() {
        return displayDriverName;
    }

    public void vehicleValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            displayVehicleName = truckDriverList.get(valueChangeEvent.getNewValue().toString());
        }
    }

    public void setDisplayVehicleName(String displayVehicleName) {
        this.displayVehicleName = displayVehicleName;
    }

    public String getDisplayVehicleName() {
        return displayVehicleName;
    }

    public void checkVehicleAssociation() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside checkVehicleAssociation method of View Cards");
        ViewObject vehicleVo = ADFUtils.getViewObject("PrtViewVehicleDriverVO1Iterator");
        vehicleVo.setNamedWhereClauseParam(Constants.COUNTRY_CD_LITERAL, lang);
        vehicleVo.setNamedWhereClauseParam("paramValue", Constants.VEHICLE_LITERAL);
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ASSOCIATED_ACOUNT_LITERAL) != null) {

            vehicleVo.setNamedWhereClauseParam("accountValue",
                                               AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ASSOCIATED_ACOUNT_LITERAL).toString().trim());
        }

        if (getBindings().getVehicleNumber().getValue() != null) {

            vehicleVo.setNamedWhereClauseParam("vehicleNumber", getBindings().getVehicleNumber().getValue());
        }
        vehicleVo.executeQuery();

        if (vehicleVo.getEstimatedRowCount() > 0) {

            while (vehicleVo.hasNext()) {
                String currentDate = "";
                PrtViewVehicleDriverVORowImpl currRow = (PrtViewVehicleDriverVORowImpl)vehicleVo.next();
                if (currRow != null) {

                    if (currRow.getCardNumber() != null) {

                        if (resourceBundle.containsKey("TRUCK_CARD_ALREADY_EXIST")) {

                            showErrorMsgEditFlag = true;
                            warningMsg = resourceBundle.getObject("TRUCK_CARD_ALREADY_EXIST").toString().concat(" ").concat(currRow.getCardEmbossNum());
                        }
                    } else {


                        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber") != null) {
                            internalCardNumber = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber").toString().trim();
                        }
                        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ASSOCIATED_ACOUNT_LITERAL) != null) {
                            associatedAccount =
                                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ASSOCIATED_ACOUNT_LITERAL).toString().trim();
                        }

                        resetVehicleDriver();
                        User userLocal = null;
                        String modifiedBy = null;
                        userLocal = (User)session.getAttribute(Constants.SESSION_USER_INFO);
                        modifiedBy = userLocal.getFirstName().concat(" ").concat(userLocal.getLastName());
                        BindingContainer localBinding = BindingContext.getCurrent().getCurrentBindingsEntry();
                        OperationBinding operationBinding = localBinding.getOperationBinding(Constants.UPDATE_VEHICLE_DRIVER_LITERAL);
                        operationBinding.getParamsMap().put(Constants.CARD_NUMBER_LITERAL, internalCardNumber);
                        operationBinding.getParamsMap().put(Constants.TYPE_LITERAL, Constants.VEHICLE_LITERAL);
                        operationBinding.getParamsMap().put(Constants.COUNTRY_CD_LITERAL, lang);
                        operationBinding.getParamsMap().put(Constants.VEHICLE_DIRVER_VALUE_LITERAL, getBindings().getVehicleNumber().getValue());
                        operationBinding.getParamsMap().put(Constants.ASSOCIATED_ACOUNT_LITERAL, associatedAccount);
                        operationBinding.getParamsMap().put(Constants.MODIFIED_BY_LITERAL, modifiedBy);
                        operationBinding.execute();
                        String accountPassingValues = null;
                        String statusPassingValues = null;
                        String cardGroupPassingValues = null;
                        if (getBindings().getPartner().getValue() != null) {
                            if (getBindings().getAccount().getValue() != null) {
                                accountPassingValues = populateStringValues(getBindings().getAccount().getValue().toString());
                            } else {
                                showErrorMessage("ENGAGE_NO_ACCOUNT");
                            }

                            if (getBindings().getStatus().getValue() != null) {
                                statusPassingValues = populateStringValues(getBindings().getStatus().getValue().toString());
                            } else {
                                showErrorMessage("ENGAGE_NO_STATUS");
                            }

                            if (getBindings().getCardGroup().getValue() != null) {
                                cardGroupPassingValues = populateStringValues(getBindings().getCardGroup().getValue().toString());
                                populateCardGroupValues(cardGroupPassingValues);
                            } else {
                                showErrorMessage("ENGAGE_NO_CARD_GROUP");
                            }
                            if (getBindings().getPartner().getValue() != null) {
                                executeViewCardsVO();
                                isTableVisible = true;
                            }
                        }

                        if (resourceBundle.containsKey("VEHICLE_ASSOCIATED")) {
                            getBindings().getTruckdriverDetails().hide();
                            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("VEHICLE_ASSOCIATED"), "");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
                }
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting checkVehicleAssociation method of View Cards");
    }

    public void checkDriverAssociation() {
        String currentDate = "";
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside checkDriverAssociation method of View Cards");
        ViewObject driverVo = ADFUtils.getViewObject("PrtViewVehicleDriverVO1Iterator");
        driverVo.setNamedWhereClauseParam(Constants.COUNTRY_CD_LITERAL, lang);
        driverVo.setNamedWhereClauseParam("paramValue", Constants.DRIVER_LITERAL);
        if (getBindings().getDriverNumber().getValue() != null) {
            driverVo.setNamedWhereClauseParam("driverNumber", getBindings().getDriverNumber().getValue());
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ASSOCIATED_ACOUNT_LITERAL) != null) {
            driverVo.setNamedWhereClauseParam("accountValue",
                                              AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ASSOCIATED_ACOUNT_LITERAL).toString().trim());
        }
        driverVo.executeQuery();
        if (driverVo.getEstimatedRowCount() > 0) {
            while (driverVo.hasNext()) {
                PrtViewVehicleDriverVORowImpl currRow = (PrtViewVehicleDriverVORowImpl)driverVo.next();
                if (currRow != null) {
                    if (currRow.getCardNumber() != null) {

                        if (resourceBundle.containsKey("DRIVER_CARD_ALREADY_EXIST")) {

                            showErrorMsgEditFlag = true;
                            warningMsg = resourceBundle.getObject("DRIVER_CARD_ALREADY_EXIST").toString().concat(" ").concat(currRow.getCardEmbossNum());

                        }
                    }

                    else {

                        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber") != null) {
                            internalCardNumber = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber").toString().trim();
                        }
                        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ASSOCIATED_ACOUNT_LITERAL) != null) {
                            associatedAccount =
                                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ASSOCIATED_ACOUNT_LITERAL).toString().trim();
                        }

                        resetVehicleDriver();
                        User localUser = null;
                        String modifiedBy = null;
                        localUser = (User)session.getAttribute(Constants.SESSION_USER_INFO);
                        modifiedBy = localUser.getFirstName().concat(" ").concat(localUser.getLastName());

                        BindingContainer loccalBinding = BindingContext.getCurrent().getCurrentBindingsEntry();
                        OperationBinding operationBinding = loccalBinding.getOperationBinding(Constants.UPDATE_VEHICLE_DRIVER_LITERAL);
                        operationBinding.getParamsMap().put(Constants.CARD_NUMBER_LITERAL, internalCardNumber);
                        operationBinding.getParamsMap().put(Constants.TYPE_LITERAL, Constants.DRIVER_LITERAL);
                        operationBinding.getParamsMap().put(Constants.COUNTRY_CD_LITERAL, lang);
                        operationBinding.getParamsMap().put(Constants.VEHICLE_DIRVER_VALUE_LITERAL, getBindings().getDriverNumber().getValue());
                        operationBinding.getParamsMap().put(Constants.ASSOCIATED_ACOUNT_LITERAL, associatedAccount);
                        operationBinding.getParamsMap().put(Constants.MODIFIED_BY_LITERAL, modifiedBy);
                        operationBinding.execute();

                        String accountPassingValues = null;
                        String statusPassingValues = null;
                        String cardGroupPassingValues = null;

                        if (getBindings().getPartner().getValue() != null) {
                            if (getBindings().getAccount().getValue() != null) {
                                accountPassingValues = populateStringValues(getBindings().getAccount().getValue().toString());
                            } else {
                                showErrorMessage("ENGAGE_NO_ACCOUNT");

                            }

                            if (getBindings().getStatus().getValue() != null) {
                                statusPassingValues = populateStringValues(getBindings().getStatus().getValue().toString());
                            } else {
                                showErrorMessage("ENGAGE_NO_STATUS");

                            }

                            if (getBindings().getCardGroup().getValue() != null) {
                                cardGroupPassingValues = populateStringValues(getBindings().getCardGroup().getValue().toString());
                                populateCardGroupValues(cardGroupPassingValues);

                            } else {
                                showErrorMessage("ENGAGE_NO_CARD_GROUP");

                            }

                            if (getBindings().getPartner().getValue() != null) {
                                executeViewCardsVO();
                                isTableVisible = true;
                            }
                        }


                        if (resourceBundle.containsKey("DRIVER_ASSOCIATED")) {
                            getBindings().getTruckdriverDetails().hide();
                            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("DRIVER_ASSOCIATED"), "");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }

                    }

                }
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting checkDriverAssociation method of View Cards");
    }


    public void exportExcelSpecificAction(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside exportExcelSpecificAction method of View Cards");
        shuttleStatus = false;
        String langDB = (String)session.getAttribute("langReport");
        if (langDB.equalsIgnoreCase(Constants.LANGUAGE_ENGLISH)) {
            langDB = "EN";
        } else {
            langDB = langDB.substring(langDB.length() - 2, langDB.length());
            langDB = langDB.toUpperCase();
        }
        String selectCriteria = Constants.DEFAULT_LITERAL;
        if (!blockedDateTime) {
            selectCriteria = "Active";
        }
        ViewObject prtExportInfoRVO = ADFUtils.getViewObject("PrtExportInfoRVO1Iterator");
        prtExportInfoRVO.setNamedWhereClauseParam("country_Code", langDB);
        prtExportInfoRVO.setNamedWhereClauseParam("report_Page", "VIEWCARDS");
        prtExportInfoRVO.setNamedWhereClauseParam("report_Type", Constants.DEFAULT_LITERAL);
        prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria", selectCriteria);
        prtExportInfoRVO.executeQuery();
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + " PrtExportInfoRVO Estimated Row Count in CardGroup:" +
                    prtExportInfoRVO.getEstimatedRowCount());
        if (prtExportInfoRVO.getEstimatedRowCount() > 0) {
            while (prtExportInfoRVO.hasNext()) {
                PrtExportInfoRVORowImpl prtExportRow = (PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                strViewCardTotalColumns = prtExportRow.getTotalColumns();
                strViewCardExtraColumns = prtExportRow.getExtraColumns();
            }
        }
        if (strViewCardTotalColumns != null) {
            String[] strHead = strViewCardTotalColumns.split(Constants.ENGAGE_REPORT_DELIMITER);
            shuttleList = new ArrayList<SelectItem>();
            for (int col = 0; col < strHead.length; col++) {
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(strHead[col]);
                selectItem.setValue(strHead[col]);
                shuttleList.add(selectItem);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShuttleExcel());
            getBindings().getSelectionExportOneRadio().setValue(Constants.XLS_LITERAL);
            getBindings().getSpecificColumns().show(new RichPopup.PopupHints());
        } else {
            if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR_DB")) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR_DB"), "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting exportExcelSpecificAction method of View Cards");
    }

    public String saveVehicleDriver() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside saveVehicleDriver method of View Cards");
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NUMBER_LITERAL) != null ||
            AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_NUMBER_LITERAL) != null) {
            if (vehiclePGL) {
                if (getBindings().getVehicleNumber().getValue() == null) {
                    showErrorMsgEditFlag = true;
                    warningMsg = resourceBundle.getObject("VEHICLE_EMPTY").toString();
                }
                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NUMBER_LITERAL) != null &&
                    (resourceBundle.containsKey("DRIVER_CARD_EXIST"))) {

                    showErrorMsgEditFlag = true;
                    warningMsg =
                            resourceBundle.getObject("DRIVER_CARD_EXIST").toString().concat(" ").concat(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NAME_LITERAL).toString());

                }
                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_NUMBER_LITERAL) != null &&
                    getBindings().getVehicleNumber().getValue().equals(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_NUMBER_LITERAL))) {

                    getBindings().getTruckdriverDetails().hide();
                }

                else {
                    reset = false;
                    checkVehicleAssociation();
                }
            } else {
                if (driverPGL) {
                    if (getBindings().getDriverNumber().getValue() == null) {
                        showErrorMsgEditFlag = true;
                        warningMsg = resourceBundle.getObject("DRIVER_EMPTY").toString();
                    }
                    if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_NUMBER_LITERAL) != null &&
                        resourceBundle.containsKey("TRUCK_CARD_EXIST")) {

                        showErrorMsgEditFlag = true;
                        warningMsg =
                                resourceBundle.getObject("TRUCK_CARD_EXIST").toString().concat(" ").concat(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_NUMBER_LITERAL).toString());

                    }
                    if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NUMBER_LITERAL) != null &&
                        getBindings().getDriverNumber().getValue().equals(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NUMBER_LITERAL))) {
                        getBindings().getTruckdriverDetails().hide();
                    } else {

                        reset = false;
                        checkDriverAssociation();

                    }
                }
            }
        } else {
            if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NUMBER_LITERAL) == null &&
                AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_NUMBER_LITERAL) == null) {
                if (vehiclePGL) {
                    if (getBindings().getVehicleNumber().getValue() == null) {
                        showErrorMsgEditFlag = true;
                        warningMsg = resourceBundle.getObject("VEHICLE_EMPTY").toString();
                    } else {
                        checkVehicleAssociation();
                    }
                } else {
                    if (driverPGL) {
                        if (getBindings().getDriverNumber().getValue() == null) {
                            showErrorMsgEditFlag = true;
                            warningMsg = resourceBundle.getObject("DRIVER_EMPTY").toString();
                        } else {
                            checkDriverAssociation();
                        }
                    }
                }
            }
        }

        showEditInfoMessage = false;
        getBindings().getVehicleDriverRadio().setSubmittedValue(null);
        getBindings().getVehicleDriverRadio().setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleDriverRadio());
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting saveVehicleDriver method of View Cards");
        return null;
    }

    /**
     * @param driverNumber
     */
    public void setDriverNumber(String driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getDriverNumber() {
        return driverNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setDriverNameAssociation(RichSelectOneChoice driverNameAssociation) {
        this.driverNameAssociation = driverNameAssociation;
    }

    public RichSelectOneChoice getDriverNameAssociation() {
        return driverNameAssociation;
    }

    public void setWarningMsg(String warningMsg) {
        this.warningMsg = warningMsg;
    }

    public String getWarningMsg() {
        return warningMsg;
    }

    public void setShowErrorMsgEditFlag(boolean showErrorMsgEditFlag) {
        this.showErrorMsgEditFlag = showErrorMsgEditFlag;
    }

    public boolean isShowErrorMsgEditFlag() {
        return showErrorMsgEditFlag;
    }

    public void setInternalCardNumber(String internalCardNumber) {
        this.internalCardNumber = internalCardNumber;
    }

    public String getInternalCardNumber() {
        return internalCardNumber;
    }

    public String closePopUp() {
        return null;
    }

    public List getShuttleList() {
        return shuttleList;
    }

    public void setShuttleValue(List shuttleValue) {
        this.shuttleValue = shuttleValue;
    }

    public List getShuttleValue() {
        if (!shuttleStatus) {
            String langDB = (String)session.getAttribute("langReport");
            if (langDB.equalsIgnoreCase(Constants.LANGUAGE_ENGLISH)) {
                langDB = "EN";
            } else {
                langDB = langDB.substring(langDB.length() - 2, langDB.length());
                langDB = langDB.toUpperCase();
            }
            shuttleValue = new ArrayList();
            ViewObject prtExportInfoRVO = ADFUtils.getViewObject("PrtExportInfoRVO1Iterator");
            prtExportInfoRVO.setNamedWhereClauseParam("country_Code", langDB);
            prtExportInfoRVO.setNamedWhereClauseParam("report_Page", "VIEWCARDS");
            prtExportInfoRVO.setNamedWhereClauseParam("report_Type", Constants.DEFAULT_LITERAL);
            prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria", Constants.DEFAULT_LITERAL);
            prtExportInfoRVO.executeQuery();
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + " PrtExportInfoRVO Estimated Row Count in CardGroup shuttle:" +
                        prtExportInfoRVO.getEstimatedRowCount());
            if (prtExportInfoRVO.getEstimatedRowCount() > 0) {
                while (prtExportInfoRVO.hasNext()) {
                    PrtExportInfoRVORowImpl prtExportRow = (PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                    strViewCardPrepopulatedColumns = prtExportRow.getPrePopulatedColumns();
                }
            }
            if (strViewCardPrepopulatedColumns != null) {
                String[] strHead = strViewCardPrepopulatedColumns.split(Constants.ENGAGE_REPORT_DELIMITER);
                for (int col = 0; col < strHead.length; col++) {
                    shuttleValue.add(strHead[col]);
                }
            }
        }
        return shuttleValue;
    }

    public void getValuesForExcel(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside getValuesForExcel method of View Cards");
        if (shuttleValue == null && getBindings().getSelectionExportOneRadio().getValue() == null) {
            if (shuttleValue == null) {
                if (resourceBundle.containsKey(Constants.TRANSACTION_SPECIFIC_ERROR_LITERAL)) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject(Constants.TRANSACTION_SPECIFIC_ERROR_LITERAL), "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                if (resourceBundle.containsKey(Constants.TRANSACTION_SPECIFIC_ERROR_SELECTION_LITERAL)) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject(Constants.TRANSACTION_SPECIFIC_ERROR_SELECTION_LITERAL),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        } else {
            if (getBindings().getSelectionExportOneRadio().getValue() != null) {
                if (shuttleValue == null) {
                    if (resourceBundle.containsKey(Constants.TRANSACTION_SPECIFIC_ERROR_LITERAL)) {
                        FacesMessage msg =
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject(Constants.TRANSACTION_SPECIFIC_ERROR_LITERAL), "");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                } else {
                    if (shuttleValue.size() > 0 && getBindings().getSelectionExportOneRadio().getValue() != null) {
                        shuttleStatus = true;
                        getBindings().getConfirmationExcel().show(new RichPopup.PopupHints());
                    }
                }
            } else {
                if (resourceBundle.containsKey(Constants.TRANSACTION_SPECIFIC_ERROR_SELECTION_LITERAL)) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject(Constants.TRANSACTION_SPECIFIC_ERROR_SELECTION_LITERAL),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting getValuesForExcel method of View Cards");
    }

    public String excelDownLoad() {
        return null;
    }

    public String formatConversion(Date date) {
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    public String checkALL(String selectedValues, String type) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside checkALL method of View Cards");
        String val = "";
        String[] listValues = selectedValues.split(",");
        if (listValues.length > 1) {
            if (Constants.PARTNER_LITERAL.equalsIgnoreCase(type)) {
                if (partnerInfoList.size() == listValues.length) {
                    if (resourceBundle.containsKey(Constants.ENG_ALL_LITERAL)) {
                        val = (String)resourceBundle.getObject(Constants.ENG_ALL_LITERAL);
                    }
                } else {
                    val = selectedValues;
                }
            } else if ("Account".equalsIgnoreCase(type)) {
                if (accountIdList.size() == listValues.length) {
                    if (resourceBundle.containsKey(Constants.ENG_ALL_LITERAL)) {
                        val = (String)resourceBundle.getObject(Constants.ENG_ALL_LITERAL);
                    }
                } else {
                    val = selectedValues;
                }
            } else if ("CardGroup".equalsIgnoreCase(type)) {
                if (cardGroupList.size() == listValues.length) {
                    if (resourceBundle.containsKey(Constants.ENG_ALL_LITERAL)) {
                        val = (String)resourceBundle.getObject(Constants.ENG_ALL_LITERAL);
                    }
                } else {
                    val = selectedValues;
                }
            } else if ("Status".equalsIgnoreCase(type)) {
                if (statusList.size() == listValues.length) {
                    if (resourceBundle.containsKey(Constants.ENG_ALL_LITERAL)) {
                        val = (String)resourceBundle.getObject(Constants.ENG_ALL_LITERAL);
                    }
                } else {
                    val = selectedValues;
                }
            }

        } else {
            val = selectedValues;
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting checkALL method of View Cards");
        return val;
    }

    public String statusConversion(String statusLabell) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside statusConversion method of View Cards");
        String statusLabel = statusLabell;
        if (statusLabel != null) {
            statusLabel = statusLabel.trim();

            if (statusLabel.equalsIgnoreCase(Constants.ENGAGE_CARD_ACTIVE)) {
                if (resourceBundle.containsKey(Constants.UNBLOCKED_PLURAL_LITERAL)) {
                    return resourceBundle.getObject(Constants.UNBLOCKED_PLURAL_LITERAL).toString();
                }
            } else if (statusLabel.equalsIgnoreCase(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED)) {
                if (resourceBundle.containsKey(Constants.TEMPORARY_BLOCKED_LITERAL)) {
                    return resourceBundle.getObject(Constants.TEMPORARY_BLOCKED_LITERAL).toString();
                }
            } else if (statusLabel.equalsIgnoreCase(Constants.ENGAGE_CARD_PERMANENT_BLOCKED)) {
                if (resourceBundle.containsKey(Constants.PERMANENT_BLOCKED_LITERAL)) {
                    return resourceBundle.getObject(Constants.PERMANENT_BLOCKED_LITERAL).toString();
                }
            } else if (statusLabel.equalsIgnoreCase("E") && resourceBundle.containsKey(Constants.EXPIRED_LITERAL)) {

                return resourceBundle.getObject(Constants.EXPIRED_LITERAL).toString();

            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting statusConversion method of View Cards");
        return null;
    }

    public String statusConversionList(String status) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside statusConversionList method of View Cards");
        if (status != null) {
            String statusValueList = "";
            String[] sta = status.split(",");
            for (int i = 0; i < sta.length; i++) {
                statusValueList = statusValueList + statusConversion(sta[i]) + ",";
            }
            statusValueList = statusValueList.substring(0, statusValueList.length() - 1);
            return statusValueList;
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting statusConversionList method of View Cards");
        return null;
    }

    public void specificExportExcelListener(FacesContext facesContext, OutputStream outputStream) throws IOException, SQLException {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside specificExportExcelListener method of View Cards");
        String selectedValues = "";
        for (int i = 0; i < shuttleValue.size(); i++) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Item =" + i + " value== " + shuttleValue.get(i));
            selectedValues = selectedValues + shuttleValue.get(i).toString().trim() + "|";
        }
        selectedValues = selectedValues.substring(0, selectedValues.length() - 1);

        ReportBundle rb = new ReportBundle();
        String langDB = (String)session.getAttribute("langReport");
        if (langDB.equalsIgnoreCase(Constants.LANGUAGE_ENGLISH)) {
            langDB = Constants.LANGUAGE_ENGLISH.toUpperCase();
        } else {
            langDB = langDB.substring(langDB.length() - 2, langDB.length());
            langDB = langDB.toUpperCase();
        }
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "langDB =" + langDB);
        String columnsReport = rb.getContentsForReport("VIEWCARDS", langDB, selectedValues);
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "From Resource Bundle:" + columnsReport);

        String partnerCompanyName = "";
        String[] partnerCompanyNameList = stringSplitter(populateStringValues(getBindings().getPartner().getValue().toString().trim()));
        String cardGroupDescName = "";
        String[] cardGroupDescList = stringSplitter(populateStringValues(getBindings().getCardGroup().getValue().toString().trim()));
        String[] accountString = stringSplitter(populateStringValues(getBindings().getAccount().getValue().toString().trim()));
        for (int z = 0; z < partnerInfoList.size(); z++) {
            if (partnerCompanyNameList.length > 0) {
                for (int p = 0; p < partnerCompanyNameList.length; p++) {
                    if (partnerInfoList.get(z).getPartnerValue() != null && partnerCompanyNameList[p] != null &&
                        (partnerInfoList.get(z).getPartnerValue().trim().equalsIgnoreCase(partnerCompanyNameList[p].trim()))) {

                        partnerCompanyName = partnerCompanyName + partnerInfoList.get(z).getPartnerValue().toString().trim() + ",";
                        if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                            for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                                if (accountString.length > 0) {
                                    for (int j = 0; j < accountString.length; j++) {
                                        if (partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() != null &&
                                            partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().trim().equals(accountString[j].trim()) &&
                                            (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                                             partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() > 0)) {

                                            for (int k = 0; k < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size(); k++) {
                                                if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID() != null &&
                                                    cardGroupDescList.length > 0) {
                                                    for (int cg = 0; cg < cardGroupDescList.length; cg++) {
                                                        if ((partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                             partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().trim()).equals(cardGroupDescList[cg].trim())) {
                                                            cardGroupDescName =
                                                                    cardGroupDescName + partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getDisplayCardGroupIdName() +
                                                                    ",";
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        partnerCompanyName = (String)partnerCompanyName.subSequence(0, (partnerCompanyName.length()) - 1);
        cardGroupDescName = (String)cardGroupDescName.subSequence(0, (cardGroupDescName.length()) - 1);
        String[] headerDataValues = columnsReport.split(Constants.ENGAGE_REPORT_DELIMITER);
        String[] headerValues = selectedValues.split(Constants.ENGAGE_REPORT_DELIMITER);

        if (Constants.XLS_LITERAL.equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Report in Excel Format");
            HSSFWorkbook XLS = new HSSFWorkbook();
            HSSFRow XLS_SH_R = null;
            HSSFCell XLS_SH_R_C = null;
            HSSFCellStyle cs = XLS.createCellStyle();
            HSSFFont f = XLS.createFont();

            HSSFSheet XLS_SH = XLS.createSheet();
            XLS.setSheetName(0, "Card_Report");

            f.setFontHeightInPoints((short)Constants.TEN);
            f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            f.setColor((short)0);
            cs.setFont(f);

            HSSFCellStyle csRight = XLS.createCellStyle();
            HSSFFont fnumberData = XLS.createFont();
            fnumberData.setFontHeightInPoints((short)Constants.TEN);
            fnumberData.setColor((short)0);
            csRight.setFont(fnumberData);
            csRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);


            HSSFCellStyle csData = XLS.createCellStyle();
            HSSFFont fData = XLS.createFont();
            fData.setFontHeightInPoints((short)Constants.TEN);
            fData.setColor((short)0);
            csData.setFont(fData);

            XLS_SH.setColumnWidth(Constants.FIFTY, Constants.FIFTY);

            XLS_SH_R = XLS_SH.createRow(0);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if (resourceBundle.containsKey("PARTNER_ENG")) {
                XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("PARTNER_ENG") + ": " + checkALL(partnerCompanyName, Constants.PARTNER_LITERAL));
            }

            XLS_SH_R = XLS_SH.createRow(1);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if (resourceBundle.containsKey("ACCOUNT")) {
                XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("ACCOUNT") + ": " +
                                        checkALL((populateStringValues(getBindings().getAccount().getValue().toString())), "Account"));
            }

            XLS_SH_R = XLS_SH.createRow(2);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if (resourceBundle.containsKey("CARDGROUP")) {
                XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("CARDGROUP") + ": " + checkALL(cardGroupDescName, "CardGroup"));
            }

            XLS_SH_R = XLS_SH.createRow(Constants.THREE);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if (resourceBundle.containsKey("STATUS")) {
                XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("STATUS") + ": " +
                                        checkALL((statusConversionList(populateStringValues(getBindings().getStatus().getValue().toString()))), "Status"));
            }

            for (int row = Constants.FOUR; row < Constants.SIX; row++) {
                XLS_SH_R = XLS_SH.createRow(row);
            }


            HSSFCellStyle css = XLS.createCellStyle();
            HSSFFont fcss = XLS.createFont();
            fcss.setFontHeightInPoints((short)Constants.TEN);
            fcss.setItalic(true);
            fcss.setColor((short)0);
            css.setFont(fcss);

            XLS_SH_R = XLS_SH.createRow(Constants.SIX);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if (resourceBundle.containsKey("ENG_VIEW_CARD_NOTE")) {
                XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("ENG_VIEW_CARD_NOTE"));
            }

            XLS_SH_R = XLS_SH.createRow(Constants.SEVEN);

            XLS_SH_R = XLS_SH.createRow(Constants.EIGHT);
            for (int col = 0; col < headerValues.length; col++) {
                XLS_SH_R_C = XLS_SH_R.createCell(col);
                XLS_SH_R_C.setCellStyle(css);
                XLS_SH_R_C.setCellValue(headerValues[col]);
            }

            int rowVal = Constants.EIGHT;

            ViewObject prtViewCardsVO = ADFUtils.getViewObject(PRTVIEWCARDSVO1ITERATOR_LITRERAL);
            RowSetIterator iterator = prtViewCardsVO.createRowSetIterator(null);
            iterator.reset();
            while (iterator.hasNext()) {
                PrtViewCardsVORowImpl row = (PrtViewCardsVORowImpl)iterator.next();
                rowVal = rowVal + 1;
                XLS_SH_R = XLS_SH.createRow(rowVal);
                if (row != null) {
                    for (int cellValue = 0; cellValue < headerDataValues.length; cellValue++) {
                        if (Constants.PARTNER_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getPartnerId() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getPartnerId().toString().trim());
                            }
                        } else if ("Account".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getAccountId() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getAccountId().toString().trim());
                            }
                        }

                        else if ("CardGroup Id".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCardgroupId() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCardgroupId().trim());
                            }
                        } else if ("CardGroup Description".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCardgroupDescription() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCardgroupDescription());
                            }
                        } else if ("Card".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCardEmbossNum() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCardEmbossNum().toString().trim());
                            }
                        } else if ("Card Textline 2".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCardTextline2() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCardTextline2().toString().trim());
                            }
                        } else if ("Type".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCardType() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCardType().toString().trim());
                            }
                        } else if ("Type Description".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getTypeName() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getTypeName().toString().trim());
                            }
                        } else if ("Status".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getBlockAction() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(statusConversion(row.getBlockAction().toString().trim()));
                            }
                        } else if ("Produced Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getManufacturedDate() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                java.sql.Date date = row.getManufacturedDate().dateValue();
                                Date passedDate = new Date(date.getTime());
                                XLS_SH_R_C.setCellValue(formatConversion(passedDate));
                            }
                        } else if ("Blocked".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getBlockTime() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                Date date = new Date(row.getBlockTime().dateValue().getTime());
                                SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
                                XLS_SH_R_C.setCellValue(sdf.format(date));
                            }
                        } else if ("Expiry".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCardExpiryDate() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                java.sql.Date date = row.getCardExpiryDate().dateValue();
                                Date passedDate = new Date(date.getTime());
                                XLS_SH_R_C.setCellValue(formatConversion(passedDate));
                            }
                        } else if ("Last Used".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getLastUsed() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                Date date = new Date(row.getLastUsed().dateValue().getTime());
                                SimpleDateFormat sdf = new java.text.SimpleDateFormat(Constants.DATE_FORMAT_LITERAL);
                                XLS_SH_R_C.setCellValue(sdf.format(date));
                            }
                        } else if ("Avg Monthly Usage".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getQuaterlyTxReportTxThreeMonths3() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csRight);
                                XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuaterlyTxReportTxThreeMonths3().toString())), locale));
                            }
                        } else if ("Avg Monthly Fuelings".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getQuaterlyFuelReportFuelThreeMonths3() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csRight);
                                XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuaterlyFuelReportFuelThreeMonths3().toString())), locale));
                            }
                        } else if (Constants.VEHICLE_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getVehicleNumber() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getVehicleNumber().toString().trim());
                            }
                        } else if (Constants.DRIVER_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getDriverName() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getDriverName().toString().trim());
                            }
                        }

                        else if ("Partner Name".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCompanyName() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCompanyName().toString().trim());
                            }
                        } else if ("Company Type".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCompanyType() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCompanyType().toString().trim());
                            }
                        } else if ("Company Address".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getPartnerAddr1() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getPartnerAddr1().toString().trim());
                            }
                        } else if ("Postal Code".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getPartnerPostalCode() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getPartnerPostalCode().toString().trim());
                            }
                        } else if ("City".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getPartnerCity() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getPartnerCity().toString().trim());
                            }
                        } else if ("Card subgroup ID".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCardgroupSubType() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCardgroupSubType().toString().trim());
                            }
                        } else if ("Card group number".equalsIgnoreCase(headerDataValues[cellValue].trim()) && row.getCardgroupSeq() != null) {

                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getCardgroupSeq().toString().trim());

                        }
                    }
                }
            }
            iterator.closeRowSetIterator();
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Printing excel Data completed");
            XLS.write(outputStream);
            outputStream.close();

        } else if ("csv".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Report in CSV Format");
            PrintWriter out = new PrintWriter(outputStream);

            for (int col = 0; col < headerValues.length; col++) {
                out.print(headerValues[col].trim());
                if (col < headerValues.length - 1) {
                    out.print(";");
                }
            }
            out.println();
            ViewObject prtViewCardsVO = ADFUtils.getViewObject(PRTVIEWCARDSVO1ITERATOR_LITRERAL);
            RowSetIterator iterator = prtViewCardsVO.createRowSetIterator(null);
            iterator.reset();
            while (iterator.hasNext()) {
                PrtViewCardsVORowImpl row = (PrtViewCardsVORowImpl)iterator.next();
                if (row != null) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Printing Data");
                    for (int cellValue = 0; cellValue < headerDataValues.length; cellValue++) {
                        if (Constants.PARTNER_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getPartnerId() != null) {
                                out.print(row.getPartnerId().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Account".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getAccountId() != null) {
                                out.print(row.getAccountId().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("CardGroup Description".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCardgroupDescription() != null) {
                                out.print(row.getCardgroupDescription());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print("|");
                            }
                        } else if ("CardGroup Id".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCardgroupId() != null) {
                                out.print(row.getCardgroupId());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print("|");
                            }
                        }

                        else if ("Card".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCardEmbossNum() != null) {
                                out.print(row.getCardEmbossNum().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print("|");
                            }
                        } else if ("Card Textline 2".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCardTextline2() != null) {
                                out.print(row.getCardTextline2().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print("|");
                            }
                        }

                        else if ("Type".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCardType() != null) {
                                out.print(row.getCardType().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print("|");
                            }
                        } else if ("Type Description".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getTypeName() != null) {
                                out.print(row.getTypeName().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print("|");
                            }
                        } else if ("Status".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getBlockAction() != null) {
                                out.print(statusConversion(row.getBlockAction().toString().trim()));
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print("|");
                            }
                        } else if ("Produced Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getManufacturedDate() != null) {
                                Date date = new Date(row.getManufacturedDate().dateValue().getTime());
                                out.print(formatConversion(date));
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print("|");
                            }
                        } else if ("Blocked".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getBlockTime() != null) {
                                Date date = new Date(row.getBlockTime().dateValue().getTime());
                                SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
                                out.print(sdf.format(date));
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print("|");
                            }
                        }

                        else if ("Expiry".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCardExpiryDate() != null) {
                                Date date = new Date(row.getCardExpiryDate().dateValue().getTime());
                                out.print(formatConversion(date));
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print("|");
                            }
                        }

                        else if ("Last Used".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getLastUsed() != null) {
                                Date date = new Date(row.getLastUsed().dateValue().getTime());
                                SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
                                out.print(sdf.format(date));
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print("|");
                            }
                        } else if ("Avg Monthly Usage".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getQuaterlyTxReportTxThreeMonths3() != null) {
                                out.print(formatConversion((Float.parseFloat(row.getQuaterlyTxReportTxThreeMonths3().toString())), locale));
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print("|");
                            }
                        } else if ("Avg Monthly Fuelings".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getQuaterlyFuelReportFuelThreeMonths3() != null) {
                                out.print(formatConversion((Float.parseFloat(row.getQuaterlyFuelReportFuelThreeMonths3().toString())), locale));
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print("|");
                            }
                        } else if (Constants.VEHICLE_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getVehicleNumber() != null) {
                                out.print(row.getVehicleNumber().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print("|");
                            }
                        } else if (Constants.DRIVER_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getDriverName() != null) {
                                out.print(row.getDriverName().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print("|");
                            }
                        }

                        else if ("Partner Name".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCompanyName() != null) {
                                out.print(row.getCompanyName().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Company Type".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCompanyType() != null) {
                                out.print(row.getCompanyType().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Company Address".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getPartnerAddr1() != null) {
                                out.print(row.getPartnerAddr1().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Postal Code".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getPartnerPostalCode() != null) {
                                out.print(row.getPartnerPostalCode().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("City".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getPartnerCity() != null) {
                                out.print(row.getPartnerCity().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Card subgroup ID".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCardgroupSubType() != null) {
                                out.print(row.getCardgroupSubType().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Card group number".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                            if (row.getCardgroupSeq() != null) {
                                out.print(row.getCardgroupSeq().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        }
                    }
                    out.println();
                }
            }
            out.println();
            iterator.closeRowSetIterator();
            out.close();
        } else {
            if ("csv2".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Report in CSV2 Format");
                PrintWriter out = new PrintWriter(outputStream);

                for (int col = 0; col < headerValues.length; col++) {
                    out.print(headerValues[col].trim());
                    if (col < headerValues.length - 1) {
                        out.print("|");
                    }
                }
                out.println();
                ViewObject prtViewCardsVO = ADFUtils.getViewObject(PRTVIEWCARDSVO1ITERATOR_LITRERAL);
                RowSetIterator iterator = prtViewCardsVO.createRowSetIterator(null);
                iterator.reset();
                while (iterator.hasNext()) {
                    PrtViewCardsVORowImpl row = (PrtViewCardsVORowImpl)iterator.next();
                    if (row != null) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Printing Data");
                        for (int cellValue = 0; cellValue < headerDataValues.length; cellValue++) {
                            if (Constants.PARTNER_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getPartnerId() != null) {
                                    out.print(row.getPartnerId().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Account".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getAccountId() != null) {
                                    out.print(row.getAccountId().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("CardGroup Description".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getCardgroupDescription() != null) {
                                    out.print(row.getCardgroupDescription());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("CardGroup Id".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getCardgroupId() != null) {
                                    out.print(row.getCardgroupId());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Type".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getCardType() != null) {
                                    out.print(row.getCardType().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Type Description".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getTypeName() != null) {
                                    out.print(row.getTypeName().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Last Used".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getLastUsed() != null) {
                                    Date date = new Date(row.getLastUsed().dateValue().getTime());
                                    SimpleDateFormat sdf = new java.text.SimpleDateFormat(Constants.DATE_FORMAT_LITERAL);
                                    out.print(sdf.format(date));
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Avg Monthly Usage".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getQuaterlyTxReportTxThreeMonths3() != null) {
                                    out.print(formatConversion((Float.parseFloat(row.getQuaterlyTxReportTxThreeMonths3().toString())), locale));
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Avg Monthly Fuelings".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getQuaterlyFuelReportFuelThreeMonths3() != null) {
                                    out.print(formatConversion((Float.parseFloat(row.getQuaterlyFuelReportFuelThreeMonths3().toString())), locale));
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Card".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getCardEmbossNum() != null) {
                                    out.print(row.getCardEmbossNum().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Card Textline 2".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getCardTextline2() != null) {
                                    out.print(row.getCardTextline2().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if (Constants.VEHICLE_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getVehicleNumber() != null) {
                                    out.print(row.getVehicleNumber().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if (Constants.DRIVER_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getDriverName() != null) {
                                    out.print(row.getDriverName().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Status".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getBlockAction() != null) {
                                    out.print(statusConversion(row.getBlockAction().toString().trim()));
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Expiry".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getCardExpiryDate() != null) {
                                    Date date = new Date(row.getCardExpiryDate().dateValue().getTime());
                                    out.print(formatConversion(date));
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Produced Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getManufacturedDate() != null) {
                                    Date date = new Date(row.getManufacturedDate().dateValue().getTime());
                                    out.print(formatConversion(date));
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Blocked".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getBlockTime() != null) {
                                    Date date = new Date(row.getBlockTime().dateValue().getTime());
                                    SimpleDateFormat sdf = new java.text.SimpleDateFormat(Constants.DATE_FORMAT_LITERAL);
                                    out.print(sdf.format(date));
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Partner Name".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getCompanyName() != null) {
                                    out.print(row.getCompanyName().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Company Type".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getCompanyType() != null) {
                                    out.print(row.getCompanyType().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Company Address".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getPartnerAddr1() != null) {
                                    out.print(row.getPartnerAddr1().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Postal Code".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getPartnerPostalCode() != null) {
                                    out.print(row.getPartnerPostalCode().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("City".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getPartnerCity() != null) {
                                    out.print(row.getPartnerCity().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Card subgroup ID".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getCardgroupSubType() != null) {
                                    out.print(row.getCardgroupSubType().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Card group number".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getCardgroupSeq() != null) {
                                    out.print(row.getCardgroupSeq().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            }
                        }
                        out.println();
                    }
                }
                out.println();
                iterator.closeRowSetIterator();
                out.close();
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting specificExportExcelListener method of View Cards");
    }


    public void displayErrorComponent(UIComponent component, boolean status) {

        RichSelectManyChoice soc = new RichSelectManyChoice();

        if (component instanceof RichSelectManyChoice) {
            soc = (RichSelectManyChoice)component;
            if (status) {
                soc.setStyleClass("af_mandatoryfield");
                if (component.getId().contains("soc3") || component.getId().contains("smc2") || component.getId().contains("smc7") ||
                    component.getId().contains("smc8")) {
                    soc.setStyleClass("af_mandatoryfield");
                }

            } else {
                soc.setStyleClass("af_nonmandatoryfield");
                if (component.getId().contains("soc3") || component.getId().contains("smc2") || component.getId().contains("smc7") ||
                    component.getId().contains("smc8")) {
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

    public String formatConversion(Float passedValue, Locale countryLocale) {
        String val = "";
        NumberFormat numberFormat = NumberFormat.getInstance(countryLocale);
        numberFormat.setMaximumFractionDigits(2);
        val = numberFormat.format(passedValue);
        return val;
    }

    public String confirmationCancelAction() {
        getBindings().getConfirmationExcel().hide();
        return null;
    }

    public void setCardEmbossNum(String cardEmbossNum) {
        this.cardEmbossNum = cardEmbossNum;
    }

    public String getCardEmbossNum() {
        return cardEmbossNum;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setDriverNumber1(String driverNumber) {
        this.DriverNumber = driverNumber;
    }

    public String getDriverNumber1() {
        return DriverNumber;
    }

    public void editVehicleDriverListener(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside editVehicleDriverListener method of View Cards");
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardAssociation") != null) {
            cardAssociation = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardAssociation").toString().trim();
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber") != null) {
            internalCardNumber = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber").toString().trim();
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardEmbossNum") != null) {
            cardEmbossNum = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardEmbossNum").toString().trim();
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.INTERNAL_NAME_LITERAL) != null) {
            internalName = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.INTERNAL_NAME_LITERAL).toString().trim();
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NUMBER_LITERAL) != null) {
            DriverNumber = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NUMBER_LITERAL).toString().trim();
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NAME_LITERAL) != null) {
            driverName = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NAME_LITERAL).toString().trim();
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_NUMBER_LITERAL) != null) {
            VehicleNumber = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_NUMBER_LITERAL).toString().trim();
        }
        this.driverNameValue = null;
        this.vehicleNumberValue = null;
        driverNameList = new ArrayList<SelectItem>();
        vehicleNumberList = new ArrayList<SelectItem>();
        this.displayDriverName = null;
        this.displayVehicleName = null;

        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNumber());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleNumber());
        getBindings().getTruckdriverDetails().show(new RichPopup.PopupHints());
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_NUMBER_LITERAL) != null ||
            AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NUMBER_LITERAL) != null) {
            if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_NUMBER_LITERAL) != null) {
                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_MODIFIED_BY_LITERAL) != null) {
                    vehicleModifiedBy = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_MODIFIED_BY_LITERAL).toString().trim();
                }
                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_MODIFIED_DATE_LITERAL) != null) {
                    vehicleModifiedDate =
                            AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_MODIFIED_DATE_LITERAL).toString().trim();
                }

                if (resourceBundle.containsKey("TRUCK_CARD_ALREADY_EXIST")) {
                    showEditInfoMessage = true;
                    infoMsgAssociated = resourceBundle.getObject("CARD_ASSOCIATED_VEHICLE").toString().concat(" " + VehicleNumber);
                    infoMsgModifiedDate = resourceBundle.getObject("MODIFIED_BY").toString().concat(" " + vehicleModifiedBy);
                    infoMsgModifiedBy = resourceBundle.getObject("MODIFIED_DATE").toString().concat(" " + vehicleModifiedDate);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditInfoMessage());
                }
            } else {
                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NUMBER_LITERAL) != null) {
                    if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_MODIFIED_BY_LITERAL) != null) {
                        driverModifiedBy = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_MODIFIED_BY_LITERAL).toString().trim();
                    }
                    if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedDate") != null) {
                        driverModifiedDate = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedDate").toString().trim();
                    }

                    if (resourceBundle.containsKey("DRIVER_CARD_ALREADY_EXIST")) {
                        showEditInfoMessage = true;
                        infoMsgAssociated = resourceBundle.getObject("CARD_ASSOCIATED_DRIVER").toString().concat(" " + DriverNumber);
                        infoMsgModifiedDate = resourceBundle.getObject("MODIFIED_DATE").toString().concat(" " + driverModifiedDate);
                        infoMsgModifiedBy = resourceBundle.getObject("MODIFIED_BY").toString().concat(" " + driverModifiedBy);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditInfoMessage());
                    }
                }
            }
        } else {
            showEditInfoMessage = false;
            getBindings().getVehicleDriverRadio().setSubmittedValue(null);
            getBindings().getVehicleDriverRadio().setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleDriverRadio());
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside editVehicleDriverListener method of View Cards");
    }

    public void closePopUpListener(ActionEvent actionEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside closePopUpListener method of View Cards");
        getBindings().getVehicleDriverRadio().setSubmittedValue(null);
        getBindings().getVehicleDriverRadio().setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleDriverRadio());
        getBindings().getTruckdriverDetails().hide();
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting closePopUpListener method of View Cards");
    }

    public void setVehicleModifiedBy(String vehicleModifiedBy) {
        this.vehicleModifiedBy = vehicleModifiedBy;
    }

    public String getVehicleModifiedBy() {
        return vehicleModifiedBy;
    }

    public void setVehicleModifiedDate(String vehicleModifiedDate) {
        this.vehicleModifiedDate = vehicleModifiedDate;
    }

    public String getVehicleModifiedDate() {
        return vehicleModifiedDate;
    }

    public void setDriverModifiedBy(String driverModifiedBy) {
        this.driverModifiedBy = driverModifiedBy;
    }

    public String getDriverModifiedBy() {
        return driverModifiedBy;
    }

    public void setDriverModifiedDate(String driverModifiedDate) {
        this.driverModifiedDate = driverModifiedDate;
    }

    public String getDriverModifiedDate() {
        return driverModifiedDate;
    }

    public void setVehicleModifiedByVisible(boolean vehicleModifiedByVisible) {
        this.vehicleModifiedByVisible = vehicleModifiedByVisible;
    }

    public boolean isVehicleModifiedByVisible() {
        return vehicleModifiedByVisible;
    }

    public void setVehicleModifiedDateVisible(boolean vehicleModifiedDateVisible) {
        this.vehicleModifiedDateVisible = vehicleModifiedDateVisible;
    }

    public boolean isVehicleModifiedDateVisible() {
        return vehicleModifiedDateVisible;
    }

    public void setDriverModifiedByVisible(boolean driverModifiedByVisible) {
        this.driverModifiedByVisible = driverModifiedByVisible;
    }

    public boolean isDriverModifiedByVisible() {
        return driverModifiedByVisible;
    }

    public void setDriverModifiedDateVisible(boolean driverModifiedDateVisible) {
        this.driverModifiedDateVisible = driverModifiedDateVisible;
    }

    public boolean isDriverModifiedDateVisible() {
        return driverModifiedDateVisible;
    }

    public void setShowEditInfoMessage(boolean showEditInfoMessage) {
        this.showEditInfoMessage = showEditInfoMessage;
    }

    public boolean isShowEditInfoMessage() {
        return showEditInfoMessage;
    }

    public void setInfoMsgAssociated(String infoMsgAssociated) {
        this.infoMsgAssociated = infoMsgAssociated;
    }

    public String getInfoMsgAssociated() {
        return infoMsgAssociated;
    }

    public void setInfoMsgModifiedDate(String infoMsgModifiedDate) {
        this.infoMsgModifiedDate = infoMsgModifiedDate;
    }

    public String getInfoMsgModifiedDate() {
        return infoMsgModifiedDate;
    }

    public void setInfoMsgModifiedBy(String infoMsgModifiedBy) {
        this.infoMsgModifiedBy = infoMsgModifiedBy;
    }

    public String getInfoMsgModifiedBy() {
        return infoMsgModifiedBy;
    }

    public String resetVehicleDriver() {

        associatedAccount = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ASSOCIATED_ACOUNT_LITERAL).toString().trim();
        User localUser = null;
        String modifiedBy = null;
        localUser = (User)session.getAttribute(Constants.SESSION_USER_INFO);
        modifiedBy = localUser.getFirstName().concat(" ").concat(localUser.getLastName());

        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NUMBER_LITERAL) != null) {
            BindingContainer localBinding = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = localBinding.getOperationBinding(Constants.UPDATE_VEHICLE_DRIVER_LITERAL);
            operationBinding.getParamsMap().put(Constants.CARD_NUMBER_LITERAL, null);
            operationBinding.getParamsMap().put(Constants.TYPE_LITERAL, Constants.DRIVER_LITERAL);
            operationBinding.getParamsMap().put(Constants.COUNTRY_CD_LITERAL, lang);
            operationBinding.getParamsMap().put(Constants.VEHICLE_DIRVER_VALUE_LITERAL,
                                                AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.DRIVER_NUMBER_LITERAL).toString());
            operationBinding.getParamsMap().put(Constants.ASSOCIATED_ACOUNT_LITERAL, associatedAccount);
            operationBinding.getParamsMap().put(Constants.MODIFIED_BY_LITERAL, modifiedBy);
            operationBinding.execute();

            if (reset && resourceBundle.containsKey("DRIVER_DISASSOCIATED")) {

                driverModifiedByVisible = false;
                driverModifiedDateVisible = false;
                getBindings().getTruckdriverDetails().hide();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("DRIVER_DISASSOCIATED"), "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_NUMBER_LITERAL) != null) {

            BindingContainer localbinding = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = localbinding.getOperationBinding(Constants.UPDATE_VEHICLE_DRIVER_LITERAL);
            operationBinding.getParamsMap().put(Constants.CARD_NUMBER_LITERAL, null);
            operationBinding.getParamsMap().put(Constants.TYPE_LITERAL, Constants.VEHICLE_LITERAL);
            operationBinding.getParamsMap().put(Constants.COUNTRY_CD_LITERAL, lang);
            operationBinding.getParamsMap().put(Constants.VEHICLE_DIRVER_VALUE_LITERAL,
                                                AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.VEHICLE_NUMBER_LITERAL).toString());
            operationBinding.getParamsMap().put(Constants.ASSOCIATED_ACOUNT_LITERAL, associatedAccount);
            operationBinding.getParamsMap().put(Constants.MODIFIED_BY_LITERAL, modifiedBy);
            operationBinding.execute();

            if (reset && resourceBundle.containsKey("VEHICLE_DISASSOCIATED")) {
                vehicleModifiedByVisible = false;
                vehicleModifiedDateVisible = false;
                getBindings().getTruckdriverDetails().hide();
                getBindings().getVehicleDriverRadio().setSubmittedValue(null);
                getBindings().getVehicleDriverRadio().setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleDriverRadio());
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("VEHICLE_DISASSOCIATED"), "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }


        executeViewCardsVO();
        isTableVisible = true;
        return null;
    }

    public void executeViewCardsVO() {
        String currentDate = "";
        String accountPassingValues = null;
        String statusPassingValues = null;
        String cardGroupPassingValues = null;

        if (getBindings().getPartner().getValue() != null) {
            if (getBindings().getAccount().getValue() != null) {
                accountPassingValues = populateStringValues(getBindings().getAccount().getValue().toString());
            }
            if (getBindings().getStatus().getValue() != null) {
                statusPassingValues = populateStringValues(getBindings().getStatus().getValue().toString());
            }
            if (getBindings().getCardGroup().getValue() != null) {
                cardGroupPassingValues = populateStringValues(getBindings().getCardGroup().getValue().toString());
                populateCardGroupValues(cardGroupPassingValues);
            }
        }
        ViewObject vo = ADFUtils.getViewObject(PRTVIEWCARDSVO1ITERATOR_LITRERAL);


        if (session.getAttribute(VIEW_CARD_ACCOUNT_QUERY_LITERAL) != null) {
            accountQuery = session.getAttribute(VIEW_CARD_ACCOUNT_QUERY_LITERAL).toString().trim();
        }
        if (session.getAttribute(VIEW_CARD_CARDGROUP_QUERY_LITRERAL) != null) {
            cardGroupQuery = session.getAttribute(VIEW_CARD_CARDGROUP_QUERY_LITRERAL).toString().trim();
        }
        if (session.getAttribute(VIEW_CARD_EXPIRY_QUERY_LITRERAL) != null) {
            expiryQuery = session.getAttribute(VIEW_CARD_EXPIRY_QUERY_LITRERAL).toString().trim();
        }

        vo.setNamedWhereClauseParam("partnerId", getBindings().getPartner().getValue().toString().trim());
        vo.setNamedWhereClauseParam(Constants.COUNTRY_CD_LITERAL, lang);

        //for active,temporary blocked status or only active or only temporary blocked status(01, 0, 1)
        if (!statusPassingValues.contains(Constants.ENGAGE_CARD_PERMANENT_BLOCKED) && !statusPassingValues.contains("E")) {
            expiryQuery = "(CARD_EXPIRY > =: currentDate)";
            vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, statusPassingValues);
            vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
        } else {
            if (!statusPassingValues.contains("E")) {
                if (statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) && !statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED)) {
                    //for active & permanent blocked status(02)
                    vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, statusPassingValues);
                    expiryQuery =
                            "((BLOCK_ACTION = '" + Constants.ENGAGE_CARD_ACTIVE + "' AND CARD_EXPIRY > =: currentDate) OR (BLOCK_ACTION = '" + Constants.ENGAGE_CARD_PERMANENT_BLOCKED +
                            "'))";
                    vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                } else if (!statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) &&
                           statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED)) {
                    //for active temporary blocked status(01)
                    vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, statusPassingValues);
                    expiryQuery =
                            "((BLOCK_ACTION = '" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "' AND CARD_EXPIRY > =: currentDate) OR (BLOCK_ACTION = '" + Constants.ENGAGE_CARD_PERMANENT_BLOCKED +
                            "'))";
                    vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                } else if (statusPassingValues.equalsIgnoreCase(Constants.ENGAGE_CARD_PERMANENT_BLOCKED)) {
                    //for permanent blocked status(2)
                    vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, statusPassingValues);
                    vo.setWhereClause(accountQuery + "AND " + cardGroupQuery);
                } else {
                    //for active,permanent and temporary blocked status(012)
                    String status =
                        Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "," + Constants.ENGAGE_CARD_PERMANENT_BLOCKED;
                    vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                    expiryQuery =
                            "((BLOCK_ACTION IN ('" + Constants.ENGAGE_CARD_ACTIVE + "','" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "') AND CARD_EXPIRY > =: currentDate) OR (BLOCK_ACTION = '" +
                            Constants.ENGAGE_CARD_PERMANENT_BLOCKED + "'))";
                    vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                }
            } else {
                if (statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) && !statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED)) {
                    if (statusPassingValues.contains(Constants.ENGAGE_CARD_PERMANENT_BLOCKED)) {
                        //for active, expired and permanent blocked status(02E)
                        String status =
                            Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "," + Constants.ENGAGE_CARD_PERMANENT_BLOCKED;
                        vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                        expiryQuery =
                                "((BLOCK_ACTION = '" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "' AND CARD_EXPIRY < =: currentDate) OR BLOCK_ACTION IN ('" +
                                Constants.ENGAGE_CARD_ACTIVE + "','" + Constants.ENGAGE_CARD_PERMANENT_BLOCKED + "'))";
                    } else {
                        //for expired and active status(0E)
                        String status = Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED;
                        vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                        expiryQuery =
                                "((BLOCK_ACTION = '" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "' AND CARD_EXPIRY < =: currentDate) OR (BLOCK_ACTION = '" +
                                Constants.ENGAGE_CARD_ACTIVE + "'))";
                    }

                    vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                } else if (!statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) &&
                           statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED)) {
                    if (statusPassingValues.contains(Constants.ENGAGE_CARD_PERMANENT_BLOCKED)) {
                        //for expired ,permanent & temporary blocked status(12E)
                        String status =
                            Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "," + Constants.ENGAGE_CARD_PERMANENT_BLOCKED;
                        vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                        expiryQuery =
                                "((BLOCK_ACTION = '" + Constants.ENGAGE_CARD_ACTIVE + "' AND CARD_EXPIRY < =: currentDate) OR (BLOCK_ACTION IN ('" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED +
                                "','" + Constants.ENGAGE_CARD_PERMANENT_BLOCKED + "')))";
                    } else {
                        //for expired and temporary blocked status(1E)
                        String status = Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED;
                        vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                        expiryQuery =
                                "((BLOCK_ACTION = '" + Constants.ENGAGE_CARD_ACTIVE + "' AND CARD_EXPIRY < =: currentDate) OR (BLOCK_ACTION = '" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED +
                                "'))";
                    }
                    vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                } else if (statusPassingValues.contains(Constants.ENGAGE_CARD_PERMANENT_BLOCKED) &&
                           !statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) &&
                           !statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED)) {
                    //for expire? and permanent blocked status(2E)
                    String status =
                        Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "," + Constants.ENGAGE_CARD_PERMANENT_BLOCKED;
                    vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                    expiryQuery =
                            "((BLOCK_ACTION IN ('" + Constants.ENGAGE_CARD_ACTIVE + "','" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "') AND CARD_EXPIRY < =: currentDate) OR (BLOCK_ACTION = '" +
                            Constants.ENGAGE_CARD_PERMANENT_BLOCKED + "'))";
                    vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                } else if (statusPassingValues.equalsIgnoreCase("E")) {
                    //for expired status(E)
                    String status = Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED;
                    vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                    expiryQuery =
                            "(BLOCK_ACTION IN ('" + Constants.ENGAGE_CARD_ACTIVE + "','" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "') AND CARD_EXPIRY < =: currentDate)";
                    vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                } else if (statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) &&
                           statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED) &&
                           !statusPassingValues.contains(Constants.ENGAGE_CARD_PERMANENT_BLOCKED)) {
                    //for active, expired and temporary blocked status(01E)
                    String status = Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED;
                    vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                    expiryQuery = "BLOCK_ACTION IN ('" + Constants.ENGAGE_CARD_ACTIVE + "','" + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "')";
                    vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + expiryQuery);
                } else {
                    //for All combination - 012E
                    String status =
                        Constants.ENGAGE_CARD_ACTIVE + "," + Constants.ENGAGE_CARD_TEMPORARY_BLOCKED + "," + Constants.ENGAGE_CARD_PERMANENT_BLOCKED;
                    vo.setNamedWhereClauseParam(Constants.STATUS_LITERAL, status);
                    vo.setWhereClause(accountQuery + "AND " + cardGroupQuery);
                }
            }
        }

        if (accountIdValue.size() > Constants.ONEFIFTY) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
            mapAccountListValue = ValueListSplit.callValueList(accountIdValue.size(), accountIdValue);
            for (int i = 0; i < mapAccountListValue.size(); i++) {

                vo.defineNamedWhereClauseParam(Constants.ACCOUNT_LITERAL + i, mapAccountListValue.get(Constants.LISTNAME_LITERAL + i), null);
            }
        } else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
            vo.defineNamedWhereClauseParam("Constants.ACCOUNT_LITERAL", populateStringValues(getBindings().getAccount().getValue().toString()), null);
        }

        if (cardGroupValue.size() > Constants.ONEFIFTY) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values > 150 ");
            mapCardGroupListValue = ValueListSplit.callValueList(cardGroupValue.size(), cardGroupValue);
            for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                String values = "cardGroup" + i;

                vo.defineNamedWhereClauseParam(values, mapCardGroupListValue.get(Constants.LISTNAME_LITERAL + i), null);
            }
        } else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 ");
            vo.defineNamedWhereClauseParam("cardGroup", populateStringValues(getBindings().getCardGroup().getValue().toString()), null);
        }

        if (!statusPassingValues.equalsIgnoreCase(Constants.ENGAGE_CARD_PERMANENT_BLOCKED) &&
            !(statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) && statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED) &&
              statusPassingValues.contains(Constants.ENGAGE_CARD_PERMANENT_BLOCKED) && statusPassingValues.contains("E")) &&
            !(statusPassingValues.contains(Constants.ENGAGE_CARD_ACTIVE) && statusPassingValues.contains(Constants.ENGAGE_CARD_TEMPORARY_BLOCKED) &&
              statusPassingValues.contains("E"))) {
            Date dateNow = new java.util.Date();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yy");
            currentDate = dateformat.format(dateNow);
            vo.defineNamedWhereClauseParam(Constants.CURRENT_DATE_LITERAL, currentDate, null);
        }
        vo.executeQuery();
    }

    public void disassociateVehicleDriver(ActionEvent actionEvent) {
        this.reset = true;
        resetVehicleDriver();
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public boolean isReset() {
        return reset;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        if (Constants.XLS_LITERAL.equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            contentType = "application/vnd.ms-excel";
        } else if ("csv".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            contentType = "text/plain";
        } else {
            if ("csv2".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
                contentType = "text/plain";
            }
        }
        return contentType;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        if (Constants.XLS_LITERAL.equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            fileName = "Card_Report.xls";
        } else if ("csv".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            fileName = "Card_Report.csv";
        } else {
            if ("csv2".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
                fileName = "Card_Report.csv";
            }
        }
        return fileName;
    }

    public void resetTableFilter() {
        FilterableQueryDescriptor queryDescriptor = (FilterableQueryDescriptor)getBindings().getSearchResultsTB().getFilterModel();
        if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null) {
            queryDescriptor.getFilterCriteria().clear();
            getBindings().getSearchResultsTB().queueEvent(new QueryEvent(getBindings().getSearchResultsTB(), queryDescriptor));
        }
    }

    public List<String> getCardTypeNameList() {
        return cardTypeNameList;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
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

    public void setPartnerIdValue(List<String> partnerIdValue) {
        this.partnerIdValue = partnerIdValue;
    }

    public List<String> getPartnerIdValue() {
        return partnerIdValue;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setConversionUtility(Conversion conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    public Conversion getConversionUtility() {
        return conversionUtility;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setBlockedDateTime(boolean blockedDateTime) {
        this.blockedDateTime = blockedDateTime;
    }

    public boolean isBlockedDateTime() {
        String statusPassingValues = null;
        if (getBindings().getStatus().getValue() != null) {
            statusPassingValues = populateStringValues(getBindings().getStatus().getValue().toString());
            if (statusPassingValues.equalsIgnoreCase(Constants.ENGAGE_CARD_ACTIVE)) {
                blockedDateTime = false;
            } else {
                blockedDateTime = true;
            }
        }
        return blockedDateTime;
    }

    public void setIsEditVisible(Boolean isEditVisible) {
        this.isEditVisible = isEditVisible;
    }

    public Boolean getIsEditVisible() {
        return isEditVisible;
    }

    public void setComparator(Comparator<SelectItem> comparator) {
        this.comparator = comparator;
    }

    public Comparator<SelectItem> getComparator() {
        return comparator;
    }

    public class Bindings {
        private RichSelectManyChoice partner;
        private RichSelectManyChoice cardGroup;
        private RichSelectManyChoice account;
        private RichSelectManyChoice status;
        private RichPopup truckdriverDetails;
        private RichSelectOneChoice driverNumber;
        private RichSelectOneChoice vehicleNumber;
        private RichOutputText showEditErrorMessage;
        private RichSelectOneRadio vehicleDriverRadio;
        private RichSelectManyShuttle shuttleExcel;
        private RichPopup specificColumns;
        private RichPopup confirmationExcel;
        private RichPanelGroupLayout driverPgl;
        private RichPanelGroupLayout vehiclePgl;
        private RichOutputText showEditInfoMessage;
        private RichSelectOneRadio selectionExportOneRadio;
        private RichTable searchResultsTB;

        public void setPartner(RichSelectManyChoice partner) {
            this.partner = partner;
        }

        public RichSelectManyChoice getPartner() {
            return partner;
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

        public void setStatus(RichSelectManyChoice status) {
            this.status = status;
        }

        public RichSelectManyChoice getStatus() {
            return status;
        }

        public void setTruckdriverDetails(RichPopup truckdriverDetails) {
            this.truckdriverDetails = truckdriverDetails;
        }

        public RichPopup getTruckdriverDetails() {
            showErrorMsgEditFlag = false;
            vehiclePGL = false;
            driverPGL = false;

            return truckdriverDetails;
        }

        public void setDriverNumber(RichSelectOneChoice driverNumber) {
            this.driverNumber = driverNumber;
        }

        public RichSelectOneChoice getDriverNumber() {
            return driverNumber;
        }

        public void setVehicleNumber(RichSelectOneChoice vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
        }

        public RichSelectOneChoice getVehicleNumber() {
            return vehicleNumber;
        }

        public void setShowEditErrorMessage(RichOutputText showEditErrorMessage) {
            this.showEditErrorMessage = showEditErrorMessage;
        }

        public RichOutputText getShowEditErrorMessage() {
            return showEditErrorMessage;
        }

        public void setVehicleDriverRadio(RichSelectOneRadio vehicleDriverRadio) {
            this.vehicleDriverRadio = vehicleDriverRadio;
        }

        public RichSelectOneRadio getVehicleDriverRadio() {
            return vehicleDriverRadio;
        }

        public void setShuttleExcel(RichSelectManyShuttle shuttleExcel) {
            this.shuttleExcel = shuttleExcel;
        }

        public RichSelectManyShuttle getShuttleExcel() {
            return shuttleExcel;
        }

        public void setSpecificColumns(RichPopup specificColumns) {
            this.specificColumns = specificColumns;
        }

        public RichPopup getSpecificColumns() {
            return specificColumns;
        }

        public void setConfirmationExcel(RichPopup confirmationExcel) {
            this.confirmationExcel = confirmationExcel;
        }

        public RichPopup getConfirmationExcel() {
            return confirmationExcel;
        }

        public void setDriverPgl(RichPanelGroupLayout driverPgl) {
            this.driverPgl = driverPgl;
        }

        public RichPanelGroupLayout getDriverPgl() {
            return driverPgl;
        }

        public void setVehiclePgl(RichPanelGroupLayout vehiclePgl) {
            this.vehiclePgl = vehiclePgl;
        }

        public RichPanelGroupLayout getVehiclePgl() {
            return vehiclePgl;
        }

        public void setShowEditInfoMessage(RichOutputText showEditInfoMessage) {
            this.showEditInfoMessage = showEditInfoMessage;
        }

        public RichOutputText getShowEditInfoMessage() {
            return showEditInfoMessage;
        }

        public void setSelectionExportOneRadio(RichSelectOneRadio selectionExportOneRadio) {
            this.selectionExportOneRadio = selectionExportOneRadio;
        }

        public RichSelectOneRadio getSelectionExportOneRadio() {
            return selectionExportOneRadio;
        }

        public void setSearchResultsTB(RichTable searchResultsTB) {
            this.searchResultsTB = searchResultsTB;
        }

        public RichTable getSearchResultsTB() {
            return searchResultsTB;
        }
    }
}


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
    private ExternalContext ectx;
    private HttpServletRequest request;
    private Boolean isTableVisible = false;
    private PartnerInfo partnerInfo;
    private List<PartnerInfo> partnerInfoList;
    private String cardGroupSubtypePassValues;
    private String cardGroupMaintypePassValue;
    private String cardGroupSeqPassValues;
    private String partnerId;
    private String partnerCountry;
    private String lang;
    public static final ADFLogger _logger = AccessDataControl.getSFRLogger();
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
    private String InternalName = null;
    private String DriverNumber = null;
    private String DriverName = null;
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
    private ValueListSplit valueList;
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


    public CardBean() {
        super();
        conversionUtility = new Conversion();
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside Constructor of View Cards");
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        statusValue = new ArrayList<String>();
        valueList = new ValueListSplit();
        resourceBundle = new EngageResourceBundle();
        partnerId = null;
        partnerIdValue = new ArrayList<String>();
        if (session.getAttribute("Partner_Object_List") != null) {
            partnerInfoList =
                    (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
            if (partnerInfoList != null && partnerInfoList.size() > 0) {
                partnerIdList = new ArrayList<SelectItem>();
                accountIdList = new ArrayList<SelectItem>();
                accountIdValue = new ArrayList<String>();
                cardGroupList = new ArrayList<SelectItem>();
                cardGroupValue = new ArrayList<String>();
                for (int i = 0; i < partnerInfoList.size(); i++) {
                    lang =
partnerInfoList.get(0).getCountry().toString().trim();
                    if (partnerInfoList.get(i).getPartnerName() != null &&
                        partnerInfoList.get(i).getPartnerValue() != null) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.setLabel(partnerInfoList.get(i).getPartnerName().toString());
                        selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString());
                        partnerIdList.add(selectItem);
                        partnerIdValue.add(partnerInfoList.get(i).getPartnerValue().toString());
                    }

                    if (partnerInfoList.get(i).getAccountList() != null &&
                        partnerInfoList.get(i).getAccountList().size() > 0) {
                        for (int j = 0;
                             j < partnerInfoList.get(i).getAccountList().size();
                             j++) {
                            if (partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() !=
                                null) {
                                SelectItem selectItem = new SelectItem();
                                selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                accountIdList.add(selectItem);
                                accountIdValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            }
                            for (int k = 0;
                                 k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size();
                                 k++) {
                                if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() !=
                                    null) {
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
            if (session.getAttribute("view_card_account_Query") != null) {
                accountQuery =
                        session.getAttribute("view_card_account_Query").toString().trim();
                mapAccountListValue =
                        (Map<String, String>)session.getAttribute("map_Account_List");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " + "account Query & mapAccountList is found");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " + "account " + accountQuery);
            }
            if (session.getAttribute("view_card_cardGroup_Query") != null) {
                cardGroupQuery =
                        session.getAttribute("view_card_cardGroup_Query").toString().trim();
                mapCardGroupListValue =
                        (Map<String, String>)session.getAttribute("map_CardGroup_List");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " +
                             "CardGroup Query & mapCardGroupList is found");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " + "CardGroup " + cardGroupQuery);
            }
            if (session.getAttribute("view_card_expiry_Query") != null) {
                expiryQuery =
                        session.getAttribute("view_card_expiry_Query").toString().trim();
            }
        }

        statusValue.add("0");

        if (lang != null) {
            currencyCode = conversionUtility.getCurrencyCode(lang);
            locale = conversionUtility.getLocaleFromCountryCode(lang);
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "currencyCode :" + currencyCode);
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Locale :" + locale);
        } else {
            currencyCode = conversionUtility.getCurrencyCode("SE");
            locale = conversionUtility.getLocaleFromCountryCode("SE");
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Default:currencyCode :" + currencyCode);
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Default:Locale :" + locale);
        }

        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting Constructor of View Cards");

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

    public List<SelectItem> getAccountIdList() {
        return accountIdList;
    }

    public void setAccountIdValue(List<String> accountIdValue) {
        this.accountIdValue = accountIdValue;
    }

    public List<String> getAccountIdValue() {
        return accountIdValue;
    }

    public void setCardGroupList(ArrayList<SelectItem> cardGroupList) {
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside populateStringValues method of View Cards");
        String passingValues = null;
        if (var != null) {
            String lovValues = var.trim();
            String selectedValues =
                lovValues.substring(1, lovValues.length() - 1);
            passingValues = selectedValues.trim();
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting populateStringValues method of View Cards");
        return passingValues;
    }

    public String[] StringConversion(String passedVal) {

        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside StringConversion method of View Cards");
        List<String> container;

        String[] val = passedVal.split(",");
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting StringConversion method of View Cards");
        return val;
    }

    /**
     * @param valueChangeEvent
     */
    public void accountValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside accountValueChangeListener method of View Cards");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null) {

            String[] accountString =
                StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()));
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();

            for (int z = 0; z < partnerInfoList.size(); z++) {

                if (partnerInfoList.get(z).getAccountList() != null &&
                    partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0;
                         i < partnerInfoList.get(z).getAccountList().size();
                         i++) {
                        for (int j = 0; j < accountString.length; j++) {
                            if (partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() !=
                                null &&
                                partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().equals(accountString[j].trim())) {
                                if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() !=
                                    null &&
                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() >
                                    0) {
                                    for (int k = 0;
                                         k < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size();
                                         k++) {
                                        if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID() !=
                                            null) {
                                            SelectItem selectItem =
                                                new SelectItem();
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting accountValueChangeListener method of View Cards");

    }


    public void setStatusList(ArrayList<SelectItem> statusList) {
        this.statusList = statusList;
    }

    /**
     * @param errorVar
     * @return
     */
    public String showErrorMessage(String errorVar) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside showErrorMessage method of View Cards");
        if (errorVar != null) {
            if (resourceBundle.containsKey(errorVar)) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     (String)resourceBundle.getObject(errorVar),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting showErrorMessage method of View Cards");
        return null;
    }

    public List<SelectItem> getStatusList() {

        if (statusList == null) {
            statusList = new ArrayList<SelectItem>();
            SelectItem selectItem = new SelectItem();

            if (resourceBundle.containsKey("UNBLOCKED_PLURAL")) {
                selectItem.setLabel(resourceBundle.getObject("UNBLOCKED_PLURAL").toString());
                selectItem.setValue("0");
                statusList.add(selectItem);
            }
            SelectItem selectItem1 = new SelectItem();
            if (resourceBundle.containsKey("TEMPORARY_BLOCKED")) {
                selectItem1.setLabel(resourceBundle.getObject("TEMPORARY_BLOCKED").toString());
                selectItem1.setValue("1");
                statusList.add(selectItem1);
            }
            SelectItem selectItem2 = new SelectItem();
            if (resourceBundle.containsKey("PERMANENT_BLOCKED")) {
                selectItem2.setLabel(resourceBundle.getObject("PERMANENT_BLOCKED").toString());
                selectItem2.setValue("2");
                statusList.add(selectItem2);
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

    public void setAccountIdList(ArrayList<SelectItem> accountIdList) {
        this.accountIdList = accountIdList;
    }

    public void setPartnerIdList(ArrayList<SelectItem> partnerIdList) {
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside partnerValueChangeListener method of View Cards");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null) {
            accountIdList = new ArrayList<SelectItem>();
            accountIdValue = new ArrayList<String>();
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();
            statusValue = new ArrayList<String>();

            String[] partnerString =
                StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()));

            if (partnerString.length > 0) {
                for (int i = 0; i < partnerInfoList.size(); i++) {
                    for (int p = 0; p < partnerString.length; p++) {
                        if (partnerInfoList.get(i).getPartnerValue().toString() !=
                            null &&
                            partnerInfoList.get(i).getPartnerValue().toString().equals(partnerString[p].trim())) {
                            if (partnerInfoList.get(i).getAccountList() !=
                                null &&
                                partnerInfoList.get(i).getAccountList().size() >
                                0) {

                                for (int j = 0;
                                     j < partnerInfoList.get(i).getAccountList().size();
                                     j++) {

                                    if (partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() !=
                                        null) {

                                        SelectItem selectItem =
                                            new SelectItem();
                                        selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                        selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                        accountIdList.add(selectItem);
                                        accountIdValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());

                                    }


                                    for (int k = 0;
                                         k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size();
                                         k++) {
                                        if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() !=
                                            null) {
                                            SelectItem selectItem =
                                                new SelectItem();
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

                }
                Collections.sort(accountIdList, comparator);
                Collections.sort(cardGroupList, comparator);
            }
            statusValue.add("0");
            statusValue.add("1");
            statusValue.add("2");

        } else {
            getBindings().getCardGroup().setValue(null);
            getBindings().getAccount().setValue(null);
            this.cardGroupValue = null;
            this.cardGroupList = null;
            this.accountIdValue = null;
            this.accountIdList = null;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting partnerValueChangeListener method of View Cards");


    }

    public void populateCardGroupValues(String cardGrpVar) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside populateCardGroupValues method of View Cards");
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
                cardGroupMaintype =
                        cardGroupMaintype + cardGroupvalues[cGrp].trim().substring(0,
                                                                                   3);
                cardGroupMaintype = cardGroupMaintype + ",";

                cardGroupSubtype =
                        cardGroupSubtype + cardGroupvalues[cGrp].trim().substring(3,
                                                                                  6);
                cardGroupSubtype = cardGroupSubtype + ",";

                cardGroupSeq =
                        cardGroupSeq + cardGroupvalues[cGrp].trim().substring(6);
                cardGroupSeq = cardGroupSeq + ",";
            }

            cardGroupMaintypePassValue =
                    cardGroupMaintype.trim().substring(0, cardGroupMaintype.length() -
                                                       1);
            cardGroupSubtypePassValues =
                    cardGroupSubtype.trim().substring(0, cardGroupSubtype.length() -
                                                      1);
            cardGroupSeqPassValues =
                    cardGroupSeq.trim().substring(0, cardGroupSeq.length() -
                                                  1);

            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "card group main type======>" +
                         cardGroupMaintypePassValue);
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "card group sub type===>" +
                         cardGroupSubtypePassValues);
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "card group sequence value====>" +
                         cardGroupSeqPassValues);
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting populateCardGroupValues method of View Cards");
    }

    public void setIsTableVisible(Boolean isTableVisible) {
        this.isTableVisible = isTableVisible;
    }

    public Boolean getIsTableVisible() {
        return isTableVisible;
    }

    public String searchViewCardAction_event(ActionEvent actionEvent) {
        searchResults();
        return null;
    }

    public String searchResults() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside searchResults method of View Cards");
        isTableVisible = false;

        String statusPassingValues = null;
        String currentDate = "";

        try {
            if (getBindings().getPartner().getValue() != null) {
                if (getBindings().getAccount().getValue() != null) {

                } else {
                    showErrorMessage("ENGAGE_NO_ACCOUNT");
                    return null;
                }

                if (getBindings().getStatus().getValue() != null) {
                    statusPassingValues =
                            populateStringValues(getBindings().getStatus().getValue().toString());
                } else {
                    showErrorMessage("ENGAGE_NO_STATUS");
                    return null;
                }

                if (getBindings().getCardGroup().getValue() != null) {

                } else {
                    showErrorMessage("ENGAGE_NO_CARD_GROUP");
                    return null;
                }

                if (getBindings().getPartner().getValue() != null) {
                    ViewObject vo =
                        ADFUtils.getViewObject("PrtViewCardsVO1Iterator");

                    if (accountQuery.length() > 1 && accountQuery != null &&
                        cardGroupQuery.length() > 1 && expiryQuery != null) {
                        if (vo.getWhereClause() != null) {
                            if (((accountQuery + "AND " + cardGroupQuery +
                                  "AND " +
                                  expiryQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim())) ||
                                ((accountQuery + " AND " + cardGroupQuery +
                                  " AND " +
                                  expiryQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim()))) {
                                if (mapAccountListValue != null) {
                                    for (int i = 0;
                                         i < mapAccountListValue.size(); i++) {
                                        String values = "account" + i;
                                        vo.removeNamedWhereClauseParam(values);
                                    }
                                } else {
                                    vo.removeNamedWhereClauseParam("account");
                                }
                                if (mapCardGroupListValue != null) {
                                    for (int i = 0;
                                         i < mapCardGroupListValue.size();
                                         i++) {
                                        String values = "cardGroup" + i;
                                        vo.removeNamedWhereClauseParam(values);
                                    }
                                } else {
                                    vo.removeNamedWhereClauseParam("cardGroup");
                                }

                                vo.removeNamedWhereClauseParam("currentDate");
                                vo.setWhereClause("");
                                vo.executeQuery();
                            } else {
                                if (((accountQuery + "AND " +
                                      cardGroupQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim())) ||
                                    ((accountQuery + " AND " +
                                      cardGroupQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim()))) {
                                    if (mapAccountListValue != null) {
                                        for (int i = 0;
                                             i < mapAccountListValue.size();
                                             i++) {
                                            String values = "account" + i;
                                            vo.removeNamedWhereClauseParam(values);
                                        }
                                    } else {
                                        vo.removeNamedWhereClauseParam("account");
                                    }
                                    if (mapCardGroupListValue != null) {
                                        for (int i = 0;
                                             i < mapCardGroupListValue.size();
                                             i++) {
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
                    }
                    resetTableFilter();
                    accountQuery = "(";
                    cardGroupQuery = "(";
                    expiryQuery = "";

                    if (accountIdValue.size() > 150) {
                        _logger.info(accessDC.getDisplayRecord() +
                                     this.getClass() + " " +
                                     "Account Values > 150 ");
                        mapAccountListValue =
                                valueList.callValueList(accountIdValue.size(),
                                                        accountIdValue);
                        for (int i = 0; i < mapAccountListValue.size(); i++) {
                            String values = "account" + i;
                            accountQuery =
                                    accountQuery + "INSTR(:" + values + ",ACCOUNT_ID)<>0 OR ";
                        }
                        _logger.info(accessDC.getDisplayRecord() +
                                     this.getClass() +
                                     "Account Query Values =" + accountQuery);
                        accountQuery =
                                accountQuery.substring(0, accountQuery.length() -
                                                       3);
                        accountQuery = accountQuery + ")";
                    } else {
                        mapAccountListValue = null;
                        _logger.info(accessDC.getDisplayRecord() +
                                     this.getClass() + " " +
                                     "Account Values < 150 ");
                        accountQuery = "(INSTR(:account,ACCOUNT_ID)<>0 ) ";
                    }


                    if (cardGroupValue.size() > 150) {
                        _logger.info(accessDC.getDisplayRecord() +
                                     this.getClass() + " " +
                                     "CardGroup Values > 150 ");
                        mapCardGroupListValue =
                                valueList.callValueList(cardGroupValue.size(),
                                                        cardGroupValue);
                        for (int i = 0; i < mapCardGroupListValue.size();
                             i++) {
                            String values = "cardGroup" + i;
                            cardGroupQuery =
                                    cardGroupQuery + "INSTR(:" + values +
                                    ",PARTNER_ID||CARDGROUP_MAIN_TYPE||CARDGROUP_SUB_TYPE||CARDGROUP_SEQ)<>0 OR ";
                        }
                        _logger.info(accessDC.getDisplayRecord() +
                                     this.getClass() +
                                     "CARDGROUP Query Values =" +
                                     cardGroupQuery);
                        cardGroupQuery =
                                cardGroupQuery.substring(0, cardGroupQuery.length() -
                                                         3);
                        cardGroupQuery = cardGroupQuery + ")";
                    } else {
                        mapCardGroupListValue = null;
                        _logger.info(accessDC.getDisplayRecord() +
                                     this.getClass() + " " +
                                     "CardGroup Values < 150 ");
                        cardGroupQuery =
                                "(INSTR(:cardGroup,PARTNER_ID||CARDGROUP_MAIN_TYPE||CARDGROUP_SUB_TYPE||CARDGROUP_SEQ)<>0) ";
                    }


                    vo.setNamedWhereClauseParam("partnerId",
                                                getBindings().getPartner().getValue().toString().trim());

                    vo.setNamedWhereClauseParam("countryCd", lang);

                    if (!statusPassingValues.contains("2")) {
                        Date dateNow = new java.util.Date();
                        SimpleDateFormat dateformat =
                            new SimpleDateFormat("dd-MMM-yy");
                        currentDate = dateformat.format(dateNow);
                        expiryQuery = "(CARD_EXPIRY > =: currentDate)";
                        vo.setNamedWhereClauseParam("status",
                                                    statusPassingValues);
                        vo.setWhereClause(accountQuery + "AND " +
                                          cardGroupQuery + "AND " +
                                          expiryQuery);
                    } else {
                        if (statusPassingValues.contains("0") &&
                            !statusPassingValues.contains("1")) {
                            String status = "0,1,2";
                            vo.setNamedWhereClauseParam("status", status);
                            Date dateNow = new java.util.Date();
                            SimpleDateFormat dateformat =
                                new SimpleDateFormat("dd-MMM-yy");
                            currentDate = dateformat.format(dateNow);
                            expiryQuery =
                                    "((BLOCK_ACTION = '1' AND CARD_EXPIRY < =: currentDate) OR (BLOCK_ACTION IN ('0','2')))";
                            vo.setWhereClause(accountQuery + "AND " +
                                              cardGroupQuery + "AND " +
                                              expiryQuery);
                        } else if (!statusPassingValues.contains("0") &&
                                   statusPassingValues.contains("1")) {
                            String status = "0,1,2";
                            vo.setNamedWhereClauseParam("status", status);
                            Date dateNow = new java.util.Date();
                            SimpleDateFormat dateformat =
                                new SimpleDateFormat("dd-MMM-yy");
                            currentDate = dateformat.format(dateNow);
                            expiryQuery =
                                    "((BLOCK_ACTION = '0' AND CARD_EXPIRY < =: currentDate) OR (BLOCK_ACTION IN ('1','2')))";
                            vo.setWhereClause(accountQuery + "AND " +
                                              cardGroupQuery + "AND " +
                                              expiryQuery);
                        } else if (statusPassingValues.equalsIgnoreCase("2")) {
                            String status = "0,1,2";
                            vo.setNamedWhereClauseParam("status", status);
                            Date dateNow = new java.util.Date();
                            SimpleDateFormat dateformat =
                                new SimpleDateFormat("dd-MMM-yy");
                            currentDate = dateformat.format(dateNow);
                            expiryQuery =
                                    "((BLOCK_ACTION IN ('0','1') AND CARD_EXPIRY < =: currentDate) OR (BLOCK_ACTION = '2'))";
                            vo.setWhereClause(accountQuery + "AND " +
                                              cardGroupQuery + "AND " +
                                              expiryQuery);
                        } else {
                            vo.setNamedWhereClauseParam("status",
                                                        statusPassingValues);
                            vo.setWhereClause(accountQuery + "AND " +
                                              cardGroupQuery);
                        }
                    }


                    if (accountIdValue.size() > 150) {
                        _logger.info(accessDC.getDisplayRecord() +
                                     this.getClass() + " " +
                                     "Account Values > 150 ");
                        mapAccountListValue =
                                valueList.callValueList(accountIdValue.size(),
                                                        accountIdValue);
                        for (int i = 0; i < mapAccountListValue.size(); i++) {
                            String values = "account" + i;
                            String listName = "listName" + i;
                            vo.defineNamedWhereClauseParam(values,
                                                           mapAccountListValue.get(listName),
                                                           null);
                        }
                    } else {
                        _logger.info(accessDC.getDisplayRecord() +
                                     this.getClass() + " " +
                                     "Account Values < 150 ");
                        vo.defineNamedWhereClauseParam("account",
                                                       populateStringValues(getBindings().getAccount().getValue().toString()),
                                                       null);
                    }


                    if (cardGroupValue.size() > 150) {
                        _logger.info(accessDC.getDisplayRecord() +
                                     this.getClass() + " " +
                                     "CardGroup Values > 150 ");
                        mapCardGroupListValue =
                                valueList.callValueList(cardGroupValue.size(),
                                                        cardGroupValue);
                        for (int i = 0; i < mapCardGroupListValue.size();
                             i++) {
                            String values = "cardGroup" + i;
                            String listName = "listName" + i;
                            vo.defineNamedWhereClauseParam(values,
                                                           mapCardGroupListValue.get(listName),
                                                           null);
                        }
                    } else {
                        _logger.info(accessDC.getDisplayRecord() +
                                     this.getClass() + " " +
                                     "CardGroup Values < 150 ");
                        vo.defineNamedWhereClauseParam("cardGroup",
                                                       populateStringValues(getBindings().getCardGroup().getValue().toString()),
                                                       null);
                    }

                    if (currentDate != null &&
                        (!statusPassingValues.contains("2") ||
                         !statusPassingValues.contains("1") ||
                         !statusPassingValues.contains("0"))) {
                        vo.defineNamedWhereClauseParam("currentDate",
                                                       currentDate, null);
                    }

                    vo.executeQuery();

                    session.setAttribute("view_card_account_Query",
                                         accountQuery);
                    session.setAttribute("view_card_map_Account_List",
                                         mapAccountListValue);
                    session.setAttribute("view_card_cardGroup_Query",
                                         cardGroupQuery);
                    session.setAttribute("view_card_map_CardGroup_List",
                                         mapCardGroupListValue);
                    session.setAttribute("view_card_expiry_Query",
                                         expiryQuery);
                    _logger.info(accessDC.getDisplayRecord() +
                                 this.getClass() + " " +
                                 "Queries are saved in session");

                    isTableVisible = true;
                }
            } else {
                showErrorMessage("ENGAGE_NO_PARTNER");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting searchResults method of View Cards");
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside clearSearchListener method of View Cards");
        getBindings().getPartner().setValue(null);
        getBindings().getStatus().setValue(null);
        this.partnerIdValue = null;
        this.accountIdValue = null;
        accountIdList = new ArrayList<SelectItem>();
        this.cardGroupValue = null;
        cardGroupList = new ArrayList<SelectItem>();
        statusValue = new ArrayList<String>();
        statusValue.add("0");
        isTableVisible = false;
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting clearSearchListener method of View Cards");
    }

    public void radioButtonValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside radioButtonValueChangeListener method of View Cards");
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().equals("Driver")) {
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

                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") !=
                    null) {
                    driverNameValue =
                            AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber").toString().trim();
                    if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedBy") !=
                        null &&
                        AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedDate") !=
                        null) {
                        vehicleModifiedByVisible = false;
                        vehicleModifiedDateVisible = false;
                        driverModifiedByVisible = true;
                        driverModifiedDateVisible = true;
                        driverModifiedBy =
                                AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedBy").toString().trim();
                        driverModifiedDate =
                                AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedDate").toString().trim();
                    }
                }

                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverName") !=
                    null) {
                    displayDriverName =
                            AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverName").toString().trim();
                }

                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNumber());
            } else if (valueChangeEvent.getNewValue().equals("Vehicle")) {
                this.getBindings().getDriverNumber().setValue(null);
                showErrorMsgEditFlag = false;
                this.displayDriverName = null;
                driverPGL = false;
                vehiclePGL = true;
                populateValue(valueChangeEvent.getNewValue().toString());
                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") !=
                    null) {
                    vehicleNumberValue =
                            AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber").toString().trim();
                    if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedBy") !=
                        null &&
                        AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedDate") !=
                        null) {
                        driverModifiedByVisible = false;
                        driverModifiedDateVisible = false;
                        vehicleModifiedByVisible = true;
                        vehicleModifiedDateVisible = true;
                        vehicleModifiedBy =
                                AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedBy").toString().trim();
                        vehicleModifiedDate =
                                AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedDate").toString().trim();
                    }
                }

                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("InternalName") !=
                    null) {
                    displayVehicleName =
                            AdfFacesContext.getCurrentInstance().getPageFlowScope().get("InternalName").toString().trim();
                }

                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleNumber());

            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting radioButtonValueChangeListener method of View Cards");
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside populateValue method of View Cards");
        if (paramType != null) {
            associatedAccount =
                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim();
            if (paramType.equals("Vehicle") || paramType.equals("Driver")) {
                if (vehiclePGL) {
                    vehicleNumberList = new ArrayList<SelectItem>();
                }
                if (driverPGL) {
                    driverNameList = new ArrayList<SelectItem>();
                }
                ViewObject vo =
                    ADFUtils.getViewObject("PrtViewVehicleDriverVO1Iterator");
                if (associatedAccount != null) {
                    vo.setNamedWhereClauseParam("accountValue",
                                                associatedAccount);
                }
                vo.setNamedWhereClauseParam("countryCd", lang);
                vo.setNamedWhereClauseParam("paramValue", paramType);
                if (paramType.equals("Driver")) {
                    vo.setNamedWhereClauseParam("driverNumber", null);
                } else {
                    vo.setNamedWhereClauseParam("vehicleNumber", null);
                }
                vo.executeQuery();

                if (vo.getEstimatedRowCount() > 0) {
                    for (int n = 0; n < vo.getEstimatedRowCount(); n++) {
                        while (vo.hasNext()) {
                            PrtViewVehicleDriverVORowImpl currRow =
                                (PrtViewVehicleDriverVORowImpl)vo.next();
                            if (currRow != null) {
                                if (paramType.equals("Vehicle")) {
                                    SelectItem selectItem = new SelectItem();
                                    if (currRow.getAttribute("VehicleNumber") !=
                                        null) {
                                        selectItem.setLabel(currRow.getVehicleNumber().toString().trim());
                                        selectItem.setValue(currRow.getVehicleNumber().toString().trim());
                                        truckDriverList.put(currRow.getVehicleNumber().toString(),
                                                            currRow.getInternalName());
                                    }
                                    vehicleNumberList.add(selectItem);
                                } else {
                                    SelectItem selectItem = new SelectItem();
                                    if (currRow.getAttribute("DriverNumber") !=
                                        null) {
                                        selectItem.setLabel(currRow.getDriverNumber().toString().trim());
                                        selectItem.setValue(currRow.getDriverNumber().toString().trim());
                                        truckDriverList.put(currRow.getDriverNumber().toString(),
                                                            currRow.getDriverName());
                                    }
                                    driverNameList.add(selectItem);
                                }
                            }
                        }
                    }
                }
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting populateValue method of View Cards");
    }

    public void setAssociatedAccount(String associatedAccount) {
        this.associatedAccount = associatedAccount;
    }

    public String getAssociatedAccount() {
        return associatedAccount;
    }

    public void setVehicleNumberList(ArrayList<SelectItem> vehicleNumberList) {
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

    public void setDriverNameList(ArrayList<SelectItem> driverNameList) {
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
            displayDriverName =
                    truckDriverList.get(valueChangeEvent.getNewValue().toString());
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
            displayVehicleName =
                    truckDriverList.get(valueChangeEvent.getNewValue().toString());
        }
    }

    public void setDisplayVehicleName(String displayVehicleName) {
        this.displayVehicleName = displayVehicleName;
    }

    public String getDisplayVehicleName() {
        return displayVehicleName;
    }

    public void checkVehicleAssociation() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside checkVehicleAssociation method of View Cards");
        ViewObject vehicleVo =
            ADFUtils.getViewObject("PrtViewVehicleDriverVO1Iterator");
        vehicleVo.setNamedWhereClauseParam("countryCd", lang);
        vehicleVo.setNamedWhereClauseParam("paramValue", "Vehicle");
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount") !=
            null) {


            vehicleVo.setNamedWhereClauseParam("accountValue",
                                               AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim());
        }

        if (getBindings().getVehicleNumber().getValue() != null) {


            vehicleVo.setNamedWhereClauseParam("vehicleNumber",
                                               getBindings().getVehicleNumber().getValue());
        }
        vehicleVo.executeQuery();

        if (vehicleVo.getEstimatedRowCount() > 0) {


            while (vehicleVo.hasNext()) {
                String currentDate = "";
                PrtViewVehicleDriverVORowImpl currRow =
                    (PrtViewVehicleDriverVORowImpl)vehicleVo.next();
                if (currRow != null) {

                    if (currRow.getCardNumber() != null) {


                        if (resourceBundle.containsKey("TRUCK_CARD_ALREADY_EXIST")) {

                            showErrorMsgEditFlag = true;
                            warningMsg =
                                    resourceBundle.getObject("TRUCK_CARD_ALREADY_EXIST").toString().concat(" ").concat(currRow.getCardEmbossNum());
                        }
                    } else {


                        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber") !=
                            null) {
                            internalCardNumber =
                                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber").toString().trim();
                        }
                        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount") !=
                            null) {
                            associatedAccount =
                                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim();
                        }

                        resetVehicleDriver();
                        User user = null;
                        String modifiedBy = null;
                        user =
(User)session.getAttribute(Constants.SESSION_USER_INFO);
                        modifiedBy =
                                user.getFirstName().concat(" ").concat(user.getLastName());
                        BindingContainer bindings =
                            BindingContext.getCurrent().getCurrentBindingsEntry();
                        OperationBinding operationBinding =
                            bindings.getOperationBinding("updateVehicleDriver");
                        operationBinding.getParamsMap().put("cardNumber",
                                                            internalCardNumber);
                        operationBinding.getParamsMap().put("type", "Vehicle");
                        operationBinding.getParamsMap().put("countryCd", lang);
                        operationBinding.getParamsMap().put("vehicleDriverValue",
                                                            getBindings().getVehicleNumber().getValue());
                        operationBinding.getParamsMap().put("associatedAccount",
                                                            associatedAccount);

                        operationBinding.getParamsMap().put("modifiedBy",
                                                            modifiedBy);


                        Object result = operationBinding.execute();
                        String accountPassingValues = null;
                        String statusPassingValues = null;
                        String cardGroupPassingValues = null;
                        if (getBindings().getPartner().getValue() != null) {
                            if (getBindings().getAccount().getValue() !=
                                null) {
                                accountPassingValues =
                                        populateStringValues(getBindings().getAccount().getValue().toString());
                            } else {
                                showErrorMessage("ENGAGE_NO_ACCOUNT");
                            }

                            if (getBindings().getStatus().getValue() != null) {
                                statusPassingValues =
                                        populateStringValues(getBindings().getStatus().getValue().toString());
                            } else {
                                showErrorMessage("ENGAGE_NO_STATUS");
                            }

                            if (getBindings().getCardGroup().getValue() !=
                                null) {
                                cardGroupPassingValues =
                                        populateStringValues(getBindings().getCardGroup().getValue().toString());
                                populateCardGroupValues(cardGroupPassingValues);
                            } else {
                                showErrorMessage("ENGAGE_NO_CARD_GROUP");
                            }
                            if (getBindings().getPartner().getValue() !=
                                null) {
                                executeViewCardsVO();
                                isTableVisible = true;
                            }
                        }


                        if (resourceBundle.containsKey("VEHICLE_ASSOCIATED")) {
                            getBindings().getTruckdriverDetails().hide();
                            FacesMessage msg =
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                 (String)resourceBundle.getObject("VEHICLE_ASSOCIATED"),
                                                 "");
                            FacesContext.getCurrentInstance().addMessage(null,
                                                                         msg);
                        }
                    }

                }
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting checkVehicleAssociation method of View Cards");
    }

    public void checkDriverAssociation() {
        String currentDate = "";
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside checkDriverAssociation method of View Cards");
        ViewObject driverVo =
            ADFUtils.getViewObject("PrtViewVehicleDriverVO1Iterator");
        driverVo.setNamedWhereClauseParam("countryCd", lang);
        driverVo.setNamedWhereClauseParam("paramValue", "Driver");
        if (getBindings().getDriverNumber().getValue() != null) {
            driverVo.setNamedWhereClauseParam("driverNumber",
                                              getBindings().getDriverNumber().getValue());
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount") !=
            null) {
            driverVo.setNamedWhereClauseParam("accountValue",
                                              AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim());
        }
        driverVo.executeQuery();
        if (driverVo.getEstimatedRowCount() > 0) {
            while (driverVo.hasNext()) {
                PrtViewVehicleDriverVORowImpl currRow =
                    (PrtViewVehicleDriverVORowImpl)driverVo.next();
                if (currRow != null) {
                    if (currRow.getCardNumber() != null) {

                        if (resourceBundle.containsKey("DRIVER_CARD_ALREADY_EXIST")) {

                            showErrorMsgEditFlag = true;
                            warningMsg =
                                    resourceBundle.getObject("DRIVER_CARD_ALREADY_EXIST").toString().concat(" ").concat(currRow.getCardEmbossNum());

                        }
                    }

                    else {


                        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber") !=
                            null) {
                            internalCardNumber =
                                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber").toString().trim();
                        }
                        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount") !=
                            null) {
                            associatedAccount =
                                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim();
                        }

                        resetVehicleDriver();
                        User user = null;
                        String modifiedBy = null;
                        user =
(User)session.getAttribute(Constants.SESSION_USER_INFO);
                        modifiedBy =
                                user.getFirstName().concat(" ").concat(user.getLastName());


                        BindingContainer bindings =
                            BindingContext.getCurrent().getCurrentBindingsEntry();
                        OperationBinding operationBinding =
                            bindings.getOperationBinding("updateVehicleDriver");
                        operationBinding.getParamsMap().put("cardNumber",
                                                            internalCardNumber);

                        operationBinding.getParamsMap().put("type", "Driver");
                        operationBinding.getParamsMap().put("countryCd", lang);

                        operationBinding.getParamsMap().put("vehicleDriverValue",
                                                            getBindings().getDriverNumber().getValue());

                        operationBinding.getParamsMap().put("associatedAccount",
                                                            associatedAccount);

                        operationBinding.getParamsMap().put("modifiedBy",
                                                            modifiedBy);


                        Object result = operationBinding.execute();


                        String accountPassingValues = null;
                        String statusPassingValues = null;
                        String cardGroupPassingValues = null;

                        if (getBindings().getPartner().getValue() != null) {
                            if (getBindings().getAccount().getValue() !=
                                null) {
                                accountPassingValues =
                                        populateStringValues(getBindings().getAccount().getValue().toString());
                            } else {
                                showErrorMessage("ENGAGE_NO_ACCOUNT");

                            }

                            if (getBindings().getStatus().getValue() != null) {
                                statusPassingValues =
                                        populateStringValues(getBindings().getStatus().getValue().toString());
                            } else {
                                showErrorMessage("ENGAGE_NO_STATUS");

                            }

                            if (getBindings().getCardGroup().getValue() !=
                                null) {
                                cardGroupPassingValues =
                                        populateStringValues(getBindings().getCardGroup().getValue().toString());
                                populateCardGroupValues(cardGroupPassingValues);

                            } else {
                                showErrorMessage("ENGAGE_NO_CARD_GROUP");

                            }

                            if (getBindings().getPartner().getValue() !=
                                null) {
                                executeViewCardsVO();
                                isTableVisible = true;
                            }
                        }


                        if (resourceBundle.containsKey("DRIVER_ASSOCIATED")) {
                            getBindings().getTruckdriverDetails().hide();
                            FacesMessage msg =
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                 (String)resourceBundle.getObject("DRIVER_ASSOCIATED"),
                                                 "");
                            FacesContext.getCurrentInstance().addMessage(null,
                                                                         msg);
                        }

                    }

                }
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting checkDriverAssociation method of View Cards");
    }


    public void exportExcelSpecificAction(ActionEvent actionEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside exportExcelSpecificAction method of View Cards");
        shuttleStatus = false;
        String langDB = (String)session.getAttribute("lang");
        langDB = langDB.substring(langDB.length() - 2, langDB.length());
        langDB = langDB.toUpperCase();
        ViewObject prtExportInfoRVO =
            ADFUtils.getViewObject("PrtExportInfoRVO1Iterator");
        prtExportInfoRVO.setNamedWhereClauseParam("country_Code", langDB);
        prtExportInfoRVO.setNamedWhereClauseParam("report_Page", "VIEWCARDS");
        prtExportInfoRVO.setNamedWhereClauseParam("report_Type", "Default");
        prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria",
                                                  "Default");
        prtExportInfoRVO.executeQuery();
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                     " PrtExportInfoRVO Estimated Row Count in CardGroup:" +
                     prtExportInfoRVO.getEstimatedRowCount());
        if (prtExportInfoRVO.getEstimatedRowCount() > 0) {
            while (prtExportInfoRVO.hasNext()) {
                PrtExportInfoRVORowImpl prtExportRow =
                    (PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                strViewCardTotalColumns = prtExportRow.getTotalColumns();
                strViewCardExtraColumns = prtExportRow.getExtraColumns();
            }
        }
        if (strViewCardTotalColumns != null) {
            String[] strHead =
                strViewCardTotalColumns.split(Constants.ENGAGE_REPORT_DELIMITER);
            shuttleList = new ArrayList<SelectItem>();
            for (int col = 0; col < strHead.length; col++) {
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(strHead[col].toString());
                selectItem.setValue(strHead[col].toString());
                shuttleList.add(selectItem);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShuttleExcel());
            getBindings().getSelectionExportOneRadio().setValue("xls");
            getBindings().getSpecificColumns().show(new RichPopup.PopupHints());
        } else {
            if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR_DB")) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR_DB"),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting exportExcelSpecificAction method of View Cards");
    }

    public String saveVehicleDriver() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside saveVehicleDriver method of View Cards");
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") !=
            null ||
            AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") !=
            null) {
            if (vehiclePGL) {
                if (getBindings().getVehicleNumber().getValue() == null) {
                    showErrorMsgEditFlag = true;
                    warningMsg =
                            resourceBundle.getObject("VEHICLE_EMPTY").toString();
                }
                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") !=
                    null) {
                    if (resourceBundle.containsKey("DRIVER_CARD_EXIST")) {
                        showErrorMsgEditFlag = true;
                        warningMsg =
                                resourceBundle.getObject("DRIVER_CARD_EXIST").toString().concat(" ").concat(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverName").toString());
                    }
                }
                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") !=
                    null &&
                    getBindings().getVehicleNumber().getValue().equals(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber"))) {

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
                        warningMsg =
                                resourceBundle.getObject("DRIVER_EMPTY").toString();
                    }
                    if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") !=
                        null) {
                        if (resourceBundle.containsKey("TRUCK_CARD_EXIST")) {
                            showErrorMsgEditFlag = true;
                            warningMsg =
                                    resourceBundle.getObject("TRUCK_CARD_EXIST").toString().concat(" ").concat(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber").toString());
                        }
                    }
                    if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") !=
                        null &&
                        getBindings().getDriverNumber().getValue().equals(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber"))) {
                        getBindings().getTruckdriverDetails().hide();
                    } else {

                        reset = false;
                        checkDriverAssociation();

                    }
                }
            }
        } else {
            if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") ==
                null &&
                AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") ==
                null) {
                if (vehiclePGL) {
                    if (getBindings().getVehicleNumber().getValue() == null) {
                        showErrorMsgEditFlag = true;
                        warningMsg =
                                resourceBundle.getObject("VEHICLE_EMPTY").toString();
                    } else {
                        checkVehicleAssociation();
                    }
                } else {
                    if (driverPGL) {
                        if (getBindings().getDriverNumber().getValue() ==
                            null) {
                            showErrorMsgEditFlag = true;
                            warningMsg =
                                    resourceBundle.getObject("DRIVER_EMPTY").toString();
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting saveVehicleDriver method of View Cards");
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
            String langDB = (String)session.getAttribute("lang");
            langDB = langDB.substring(langDB.length() - 2, langDB.length());
            langDB = langDB.toUpperCase();
            shuttleValue = new ArrayList();
            ViewObject prtExportInfoRVO =
                ADFUtils.getViewObject("PrtExportInfoRVO1Iterator");
            prtExportInfoRVO.setNamedWhereClauseParam("country_Code", langDB);
            prtExportInfoRVO.setNamedWhereClauseParam("report_Page",
                                                      "VIEWCARDS");
            prtExportInfoRVO.setNamedWhereClauseParam("report_Type",
                                                      "Default");
            prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria",
                                                      "Default");
            prtExportInfoRVO.executeQuery();
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         " PrtExportInfoRVO Estimated Row Count in CardGroup shuttle:" +
                         prtExportInfoRVO.getEstimatedRowCount());
            if (prtExportInfoRVO.getEstimatedRowCount() > 0) {
                while (prtExportInfoRVO.hasNext()) {
                    PrtExportInfoRVORowImpl prtExportRow =
                        (PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                    strViewCardPrepopulatedColumns =
                            prtExportRow.getPrePopulatedColumns();
                }
            }
            if (strViewCardPrepopulatedColumns != null) {
                String[] strHead =
                    strViewCardPrepopulatedColumns.split(Constants.ENGAGE_REPORT_DELIMITER);
                for (int col = 0; col < strHead.length; col++) {
                    shuttleValue.add(strHead[col].toString());
                }
            }
        }
        return shuttleValue;
    }

    public void getValuesForExcel(ActionEvent actionEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside getValuesForExcel method of View Cards");
        if (shuttleValue == null &&
            getBindings().getSelectionExportOneRadio().getValue() == null) {
            if (shuttleValue == null) {
                if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR_SELECTION")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR_SELECTION"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        } else {
            if (getBindings().getSelectionExportOneRadio().getValue() !=
                null) {
                if (shuttleValue == null) {
                    if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR")) {
                        FacesMessage msg =
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                             (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR"),
                                             "");
                        FacesContext.getCurrentInstance().addMessage(null,
                                                                     msg);
                    }
                } else {
                    if (shuttleValue.size() > 0 &&
                        getBindings().getSelectionExportOneRadio().getValue() !=
                        null) {
                        shuttleStatus = true;
                        getBindings().getConfirmationExcel().show(new RichPopup.PopupHints());
                    }
                }
            } else {
                if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR_SELECTION")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR_SELECTION"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting getValuesForExcel method of View Cards");
    }

    public String excelDownLoad() {
        return null;
    }

    public String formatConversion(Date date) {
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    public String checkALL(String selectedValues, String type) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside checkALL method of View Cards");
        String val = "";
        String[] listValues = selectedValues.split(",");
        if (listValues.length > 1) {
            if ("Partner".equalsIgnoreCase(type)) {
                if (partnerInfoList.size() == listValues.length) {
                    if (resourceBundle.containsKey("ENG_ALL")) {
                        val = (String)resourceBundle.getObject("ENG_ALL");
                    }
                } else {
                    val = selectedValues;
                }
            } else if ("Account".equalsIgnoreCase(type)) {
                if (accountIdList.size() == listValues.length) {
                    if (resourceBundle.containsKey("ENG_ALL")) {
                        val = (String)resourceBundle.getObject("ENG_ALL");
                    }
                } else {
                    val = selectedValues;
                }
            } else if ("CardGroup".equalsIgnoreCase(type)) {
                if (cardGroupList.size() == listValues.length) {
                    if (resourceBundle.containsKey("ENG_ALL")) {
                        val = (String)resourceBundle.getObject("ENG_ALL");
                    }
                } else {
                    val = selectedValues;
                }
            } else if ("Status".equalsIgnoreCase(type)) {
                if (statusList.size() == listValues.length) {
                    if (resourceBundle.containsKey("ENG_ALL")) {
                        val = (String)resourceBundle.getObject("ENG_ALL");
                    }
                } else {
                    val = selectedValues;
                }
            }

        } else {
            val = selectedValues;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting checkALL method of View Cards");
        return val;
    }

    public String statusConversion(String statusLabel) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside statusConversion method of View Cards");
        if (statusLabel != null) {
            statusLabel = statusLabel.trim();

            if (statusLabel.equalsIgnoreCase("0")) {

                if (resourceBundle.containsKey("UNBLOCKED_PLURAL"))

                    return resourceBundle.getObject("UNBLOCKED_PLURAL").toString();
            } else if (statusLabel.equalsIgnoreCase("1")) {


                if (resourceBundle.containsKey("TEMPORARY_BLOCKED"))
                    return resourceBundle.getObject("TEMPORARY_BLOCKED").toString();
            } else if (statusLabel.equalsIgnoreCase("2")) {

                if (resourceBundle.containsKey("PERMANENT_BLOCKED"))
                    return resourceBundle.getObject("PERMANENT_BLOCKED").toString();
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting statusConversion method of View Cards");
        return null;
    }

    public String statusConversionList(String status) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside statusConversionList method of View Cards");
        if (status != null) {
            String statusValueList = "";
            String[] sta = status.split(",");
            for (int i = 0; i < sta.length; i++) {
                statusValueList =
                        statusValueList + statusConversion(sta[i]) + ",";
            }
            statusValueList =
                    statusValueList.substring(0, statusValueList.length() - 1);
            return statusValueList;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting statusConversionList method of View Cards");
        return null;
    }

    public void specificExportExcelListener(FacesContext facesContext,
                                            OutputStream outputStream) throws IOException,
                                                                              SQLException,
                                                                              Exception {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside specificExportExcelListener method of View Cards");
        String selectedValues = "";
        for (int i = 0; i < shuttleValue.size(); i++) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Item =" + i + " value== " + shuttleValue.get(i));
            selectedValues =
                    selectedValues + shuttleValue.get(i).toString().trim() +
                    "|";
        }
        selectedValues =
                selectedValues.substring(0, selectedValues.length() - 1);

        ReportBundle rb = new ReportBundle();


        String langDB = (String)session.getAttribute("lang");
        langDB = langDB.substring(langDB.length() - 2, langDB.length());
        langDB = langDB.toUpperCase();
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                     "langDB =" + langDB);
        String columnsReport =
            rb.getContentsForReport("VIEWCARDS", langDB, selectedValues);
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                     "From Resource Bundle:" + columnsReport);

        String partnerCompanyName = "";
        String[] partnerCompanyNameList =
            StringConversion(populateStringValues(getBindings().getPartner().getValue().toString().trim()));
        String cardGroupDescName = "";
        String[] cardGroupDescList =
            StringConversion(populateStringValues(getBindings().getCardGroup().getValue().toString().trim()));
        String[] accountString =
            StringConversion(populateStringValues(getBindings().getAccount().getValue().toString().trim()));
        for (int z = 0; z < partnerInfoList.size(); z++) {
            if (partnerCompanyNameList.length > 0) {
                for (int p = 0; p < partnerCompanyNameList.length; p++) {
                    if (partnerInfoList.get(z).getPartnerValue() != null &&
                        partnerCompanyNameList[p] != null) {
                        if (partnerInfoList.get(z).getPartnerValue().trim().equalsIgnoreCase(partnerCompanyNameList[p].trim())) {
                            partnerCompanyName =
                                    partnerCompanyName + partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                    ",";
                            if (partnerInfoList.get(z).getAccountList() !=
                                null &&
                                partnerInfoList.get(z).getAccountList().size() >
                                0) {
                                for (int i = 0;
                                     i < partnerInfoList.get(z).getAccountList().size();
                                     i++) {
                                    if (accountString.length > 0) {
                                        for (int j = 0;
                                             j < accountString.length; j++) {
                                            if (partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() !=
                                                null &&
                                                partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().trim().equals(accountString[j].trim())) {
                                                if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() !=
                                                    null &&
                                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() >
                                                    0) {
                                                    for (int k = 0;
                                                         k < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size();
                                                         k++) {
                                                        if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID() !=
                                                            null &&
                                                            cardGroupDescList.length >
                                                            0) {
                                                            for (int cg = 0;
                                                                 cg <
                                                                 cardGroupDescList.length;
                                                                 cg++) {
                                                                if ((partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                                     partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().trim()).equals(cardGroupDescList[cg].toString().trim())) {
                                                                    cardGroupDescName =
                                                                            cardGroupDescName +
                                                                            partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getDisplayCardGroupIdName() +
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
            }
        }
        partnerCompanyName =
                (String)partnerCompanyName.subSequence(0, (partnerCompanyName.length()) -
                                                       1);
        cardGroupDescName =
                (String)cardGroupDescName.subSequence(0, (cardGroupDescName.length()) -
                                                      1);
        String[] headerDataValues =
            columnsReport.split(Constants.ENGAGE_REPORT_DELIMITER);
        String[] headerValues =
            selectedValues.split(Constants.ENGAGE_REPORT_DELIMITER);

        if ("xls".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Report in Excel Format");
            HSSFWorkbook XLS = new HSSFWorkbook();
            HSSFRow XLS_SH_R = null;
            HSSFCell XLS_SH_R_C = null;
            int intRow = 0;
            HSSFCellStyle cs = XLS.createCellStyle();
            HSSFFont f = XLS.createFont();

            HSSFSheet XLS_SH = XLS.createSheet();
            XLS.setSheetName(0, "CardReport");

            f.setFontHeightInPoints((short)10);
            f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            f.setColor((short)0);
            cs.setFont(f);

            HSSFCellStyle csRight = XLS.createCellStyle();
            HSSFFont fnumberData = XLS.createFont();
            fnumberData.setFontHeightInPoints((short)10);
            fnumberData.setColor((short)0);
            csRight.setFont(fnumberData);
            csRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);


            HSSFCellStyle csData = XLS.createCellStyle();
            HSSFFont fData = XLS.createFont();
            fData.setFontHeightInPoints((short)10);
            fData.setColor((short)0);
            csData.setFont(fData);

            XLS_SH.setColumnWidth(50, 50);

            XLS_SH_R = XLS_SH.createRow(0);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if (resourceBundle.containsKey("PARTNER_ENG")) {
                XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("PARTNER_ENG") +
                                        ": " +
                                        checkALL(partnerCompanyName, "Partner"));
            }

            XLS_SH_R = XLS_SH.createRow(1);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if (resourceBundle.containsKey("ACCOUNT")) {
                XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("ACCOUNT") +
                                        ": " +
                                        checkALL((populateStringValues(getBindings().getAccount().getValue().toString())),
                                                 "Account"));
            }

            XLS_SH_R = XLS_SH.createRow(2);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if (resourceBundle.containsKey("CARDGROUP")) {
                XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("CARDGROUP") +
                                        ": " +
                                        checkALL(cardGroupDescName, "CardGroup"));
            }

            XLS_SH_R = XLS_SH.createRow(3);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if (resourceBundle.containsKey("STATUS")) {
                XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("STATUS") +
                                        ": " +
                                        checkALL((statusConversionList(populateStringValues(getBindings().getStatus().getValue().toString()))),
                                                 "Status"));
            }

            for (int row = 4; row < 6; row++) {
                XLS_SH_R = XLS_SH.createRow(row);
            }


            HSSFCellStyle css = XLS.createCellStyle();
            HSSFFont fcss = XLS.createFont();
            fcss.setFontHeightInPoints((short)10);
            fcss.setItalic(true);
            fcss.setColor((short)0);
            css.setFont(fcss);

            XLS_SH_R = XLS_SH.createRow(6);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if (resourceBundle.containsKey("ENG_VIEW_CARD_NOTE")) {
                XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("ENG_VIEW_CARD_NOTE"));
            }

            XLS_SH_R = XLS_SH.createRow(7);

            XLS_SH_R = XLS_SH.createRow(8);
            for (int col = 0; col < headerValues.length; col++) {
                XLS_SH_R_C = XLS_SH_R.createCell(col);
                XLS_SH_R_C.setCellStyle(css);
                XLS_SH_R_C.setCellValue(headerValues[col].toString());
            }

            int rowVal = 8;

            ViewObject prtViewCardsVO =
                ADFUtils.getViewObject("PrtViewCardsVO1Iterator");
            RowSetIterator iterator =
                prtViewCardsVO.createRowSetIterator(null);
            iterator.reset();
            while (iterator.hasNext()) {
                PrtViewCardsVORowImpl row =
                    (PrtViewCardsVORowImpl)iterator.next();
                rowVal = rowVal + 1;
                XLS_SH_R = XLS_SH.createRow(rowVal);
                if (row != null) {
                    for (int cellValue = 0;
                         cellValue < headerDataValues.length; cellValue++) {
                        if ("Partner".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getPartnerId() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getPartnerId().toString().trim());
                            }
                        } else if ("Account".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getAccountId() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getAccountId().toString().trim());
                            }
                        } else if ("CardGroup Description".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardgroupDescription() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCardgroupDescription());
                            }
                        } else if ("CardGroup Id".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardgroupId() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCardgroupId().trim());
                            }
                        } else if ("Type".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardType() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCardType().toString().trim());
                            }
                        } else if ("Type Description".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getTypeName() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getTypeName().toString().trim());
                            }
                        } else if ("Last Used".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getLastUsed() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                Date date =
                                    new Date(row.getLastUsed().dateValue().getTime());
                                SimpleDateFormat sdf =
                                    new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
                                XLS_SH_R_C.setCellValue(sdf.format(date));
                            }
                        } else if ("Avg Monthly Usage".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getQuaterlyTxReportTxThreeMonths3() !=
                                null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csRight);
                                XLS_SH_R_C.setCellValue(row.getQuaterlyTxReportTxThreeMonths3().toString().trim());
                            }
                        } else if ("Avg Monthly Fuelings".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getQuaterlyFuelReportFuelThreeMonths3() !=
                                null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csRight);
                                XLS_SH_R_C.setCellValue(row.getQuaterlyFuelReportFuelThreeMonths3().toString());
                            }
                        } else if ("Card".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardEmbossNum() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCardEmbossNum().toString().trim());
                            }
                        } else if ("Card Textline 2".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardTextline2() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCardTextline2().toString().trim());
                            }
                        } else if ("Vehicle".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getVehicleNumber() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getVehicleNumber().toString().trim());
                            }
                        } else if ("Driver".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getDriverName() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getDriverName().toString().trim());
                            }
                        } else if ("Status".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getBlockAction() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(statusConversion(row.getBlockAction().toString().trim()));
                            }
                        } else if ("Expiry".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardExpiryDate() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                java.sql.Date date =
                                    row.getCardExpiryDate().dateValue();
                                Date passedDate = new Date(date.getTime());
                                XLS_SH_R_C.setCellValue(formatConversion(passedDate));
                            }
                        } else if ("Manufactured".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getManufacturedDate() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                java.sql.Date date =
                                    row.getManufacturedDate().dateValue();
                                Date passedDate = new Date(date.getTime());
                                XLS_SH_R_C.setCellValue(formatConversion(passedDate));
                            }
                        } else if ("Blocked".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getBlockTime() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                                Date date =
                                    new Date(row.getBlockTime().dateValue().getTime());
                                SimpleDateFormat sdf =
                                    new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
                                XLS_SH_R_C.setCellValue(sdf.format(date));
                            }
                        }
                    }

                }
            }
            iterator.closeRowSetIterator();
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Printing excel Data completed");
            XLS.write(outputStream);
            outputStream.close();

        } else if ("csv".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Report in CSV Format");
            PrintWriter out = new PrintWriter(outputStream);

            for (int col = 0; col < headerValues.length; col++) {
                out.print(headerValues[col].toString().trim());
                if (col < headerValues.length - 1) {
                    out.print(";");
                }
            }
            out.println();
            ViewObject prtViewCardsVO =
                ADFUtils.getViewObject("PrtViewCardsVO1Iterator");
            RowSetIterator iterator =
                prtViewCardsVO.createRowSetIterator(null);
            iterator.reset();
            while (iterator.hasNext()) {
                PrtViewCardsVORowImpl row =
                    (PrtViewCardsVORowImpl)iterator.next();
                if (row != null) {
                    _logger.info(accessDC.getDisplayRecord() +
                                 this.getClass() + " " + "Printing Data");
                    for (int cellValue = 0;
                         cellValue < headerDataValues.length; cellValue++) {
                        if ("Partner".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getPartnerId() != null) {
                                out.print(row.getPartnerId().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Account".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getAccountId() != null) {
                                out.print(row.getAccountId().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("CardGroup Description".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardgroupDescription() != null) {
                                out.print(row.getCardgroupDescription());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("CardGroup Id".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardgroupId() != null) {
                                out.print(row.getCardgroupId());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Type".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardType() != null) {
                                out.print(row.getCardType().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Type Description".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getTypeName() != null) {
                                out.print(row.getTypeName().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Last Used".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getLastUsed() != null) {
                                Date date =
                                    new Date(row.getLastUsed().dateValue().getTime());
                                SimpleDateFormat sdf =
                                    new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
                                out.print(sdf.format(date));
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Avg Monthly Usage".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getQuaterlyTxReportTxThreeMonths3() !=
                                null) {
                                out.print(row.getQuaterlyTxReportTxThreeMonths3().toString());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Avg Monthly Fuelings".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getQuaterlyFuelReportFuelThreeMonths3() !=
                                null) {
                                out.print(row.getQuaterlyFuelReportFuelThreeMonths3().toString());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Card".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardEmbossNum() != null) {
                                out.print(row.getCardEmbossNum().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Card Textline 2".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardTextline2() != null) {
                                out.print(row.getCardTextline2().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Vehicle".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getVehicleNumber() != null) {
                                out.print(row.getVehicleNumber().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Driver".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getDriverName() != null) {
                                out.print(row.getDriverName().toString().trim());
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Status".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getBlockAction() != null) {
                                out.print(statusConversion(row.getBlockAction().toString().trim()));
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Expiry".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardExpiryDate() != null) {
                                Date date =
                                    new Date(row.getCardExpiryDate().dateValue().getTime());
                                out.print(formatConversion(date));
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Manufactured".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getManufacturedDate() != null) {
                                Date date =
                                    new Date(row.getManufacturedDate().dateValue().getTime());
                                out.print(formatConversion(date));
                            }
                            if (cellValue != headerDataValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Blocked".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getBlockTime() != null) {
                                Date date =
                                    new Date(row.getBlockTime().dateValue().getTime());
                                SimpleDateFormat sdf =
                                    new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
                                out.print(sdf.format(date));
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
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " + "Report in CSV2 Format");
                PrintWriter out = new PrintWriter(outputStream);

                for (int col = 0; col < headerValues.length; col++) {
                    out.print(headerValues[col].toString().trim());
                    if (col < headerValues.length - 1) {
                        out.print("|");
                    }
                }
                out.println();
                ViewObject prtViewCardsVO =
                    ADFUtils.getViewObject("PrtViewCardsVO1Iterator");
                RowSetIterator iterator =
                    prtViewCardsVO.createRowSetIterator(null);
                iterator.reset();
                while (iterator.hasNext()) {
                    PrtViewCardsVORowImpl row =
                        (PrtViewCardsVORowImpl)iterator.next();
                    if (row != null) {
                        _logger.info(accessDC.getDisplayRecord() +
                                     this.getClass() + " " + "Printing Data");
                        for (int cellValue = 0;
                             cellValue < headerDataValues.length;
                             cellValue++) {
                            if ("Partner".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getPartnerId() != null) {
                                    out.print(row.getPartnerId().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Account".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getAccountId() != null) {
                                    out.print(row.getAccountId().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("CardGroup Description".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getCardgroupDescription() != null) {
                                    out.print(row.getCardgroupDescription());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("CardGroup Id".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getCardgroupId() != null) {
                                    out.print(row.getCardgroupId());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Type".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getCardType() != null) {
                                    out.print(row.getCardType().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Type Description".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getTypeName() != null) {
                                    out.print(row.getTypeName().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Last Used".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getLastUsed() != null) {
                                    Date date =
                                        new Date(row.getLastUsed().dateValue().getTime());
                                    SimpleDateFormat sdf =
                                        new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
                                    out.print(sdf.format(date));
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Avg Monthly Usage".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getQuaterlyTxReportTxThreeMonths3() !=
                                    null) {
                                    out.print(row.getQuaterlyTxReportTxThreeMonths3().toString());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Avg Monthly Fuelings".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getQuaterlyFuelReportFuelThreeMonths3() !=
                                    null) {
                                    out.print(row.getQuaterlyFuelReportFuelThreeMonths3().toString());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Card".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getCardEmbossNum() != null) {
                                    out.print(row.getCardEmbossNum().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Card Textline 2".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getCardTextline2() != null) {
                                    out.print(row.getCardTextline2().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Vehicle".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getVehicleNumber() != null) {
                                    out.print(row.getVehicleNumber().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Driver".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getDriverName() != null) {
                                    out.print(row.getDriverName().toString().trim());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Status".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getBlockAction() != null) {
                                    out.print(statusConversion(row.getBlockAction().toString().trim()));
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Expiry".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getCardExpiryDate() != null) {
                                    Date date =
                                        new Date(row.getCardExpiryDate().dateValue().getTime());
                                    out.print(formatConversion(date));
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Manufactured".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getManufacturedDate() != null) {
                                    Date date =
                                        new Date(row.getManufacturedDate().dateValue().getTime());
                                    out.print(formatConversion(date));
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Blocked".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getBlockTime() != null) {
                                    Date date =
                                        new Date(row.getBlockTime().dateValue().getTime());
                                    SimpleDateFormat sdf =
                                        new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
                                    out.print(sdf.format(date));
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting specificExportExcelListener method of View Cards");
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

    public void setInternalName(String InternalName) {
        this.InternalName = InternalName;
    }

    public String getInternalName() {
        return InternalName;
    }

    public void setDriverNumber1(String DriverNumber) {
        this.DriverNumber = DriverNumber;
    }

    public String getDriverNumber1() {
        return DriverNumber;
    }

    public void editVehicleDriverListener(ActionEvent actionEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside editVehicleDriverListener method of View Cards");
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardAssociation") !=
            null)
            cardAssociation =
                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardAssociation").toString().trim();
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber") !=
            null)
            internalCardNumber =
                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber").toString().trim();
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardEmbossNum") !=
            null)
            cardEmbossNum =
                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardEmbossNum").toString().trim();
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("InternalName") !=
            null)
            InternalName =
                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get("InternalName").toString().trim();
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") !=
            null)
            DriverNumber =
                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber").toString().trim();
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverName") !=
            null)
            DriverName =
                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverName").toString().trim();
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") !=
            null)
            VehicleNumber =
                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber").toString().trim();
        this.driverNameValue = null;
        this.vehicleNumberValue = null;
        driverNameList = new ArrayList<SelectItem>();
        vehicleNumberList = new ArrayList<SelectItem>();

        this.displayDriverName = null;
        this.displayVehicleName = null;

        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNumber());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleNumber());
        getBindings().getTruckdriverDetails().show(new RichPopup.PopupHints());
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") !=
            null ||
            AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") !=
            null) {
            if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") !=
                null) {
                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedBy") !=
                    null)
                    vehicleModifiedBy =
                            AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedBy").toString().trim();
                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedDate") !=
                    null)
                    vehicleModifiedDate =
                            AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedDate").toString().trim();

                if (resourceBundle.containsKey("TRUCK_CARD_ALREADY_EXIST")) {
                    showEditInfoMessage = true;
                    infoMsgAssociated =
                            resourceBundle.getObject("CARD_ASSOCIATED_VEHICLE").toString().concat(" " +
                                                                                                  VehicleNumber);
                    infoMsgModifiedDate =
                            resourceBundle.getObject("MODIFIED_BY").toString().concat(" " +
                                                                                      vehicleModifiedBy);
                    infoMsgModifiedBy =
                            resourceBundle.getObject("MODIFIED_DATE").toString().concat(" " +
                                                                                        vehicleModifiedDate);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditInfoMessage());
                }
            } else {
                if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") !=
                    null) {
                    if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedBy") !=
                        null)
                        driverModifiedBy =
                                AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedBy").toString().trim();
                    if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedDate") !=
                        null)
                        driverModifiedDate =
                                AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedDate").toString().trim();


                    if (resourceBundle.containsKey("DRIVER_CARD_ALREADY_EXIST")) {
                        showEditInfoMessage = true;
                        infoMsgAssociated =
                                resourceBundle.getObject("CARD_ASSOCIATED_DRIVER").toString().concat(" " +
                                                                                                     DriverNumber);
                        infoMsgModifiedDate =
                                resourceBundle.getObject("MODIFIED_DATE").toString().concat(" " +
                                                                                            driverModifiedDate);
                        infoMsgModifiedBy =
                                resourceBundle.getObject("MODIFIED_BY").toString().concat(" " +
                                                                                          driverModifiedBy);
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Outside editVehicleDriverListener method of View Cards");
    }

    public void closePopUpListener(ActionEvent actionEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Inside closePopUpListener method of View Cards");
        getBindings().getVehicleDriverRadio().setSubmittedValue(null);
        getBindings().getVehicleDriverRadio().setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleDriverRadio());
        getBindings().getTruckdriverDetails().hide();
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                     " Exiting closePopUpListener method of View Cards");
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

        associatedAccount =
                AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim();
        User user = null;
        String modifiedBy = null;
        user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
        modifiedBy =
                user.getFirstName().concat(" ").concat(user.getLastName());

        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") !=
            null) {
            BindingContainer bindings =
                BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding =
                bindings.getOperationBinding("updateVehicleDriver");
            operationBinding.getParamsMap().put("cardNumber", null);

            operationBinding.getParamsMap().put("type", "Driver");
            operationBinding.getParamsMap().put("countryCd", lang);

            operationBinding.getParamsMap().put("vehicleDriverValue",
                                                AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber").toString());

            operationBinding.getParamsMap().put("associatedAccount",
                                                associatedAccount);

            operationBinding.getParamsMap().put("modifiedBy", modifiedBy);


            Object result = operationBinding.execute();

            if (reset) {
                if (resourceBundle.containsKey("DRIVER_DISASSOCIATED")) {
                    driverModifiedByVisible = false;
                    driverModifiedDateVisible = false;

                    getBindings().getTruckdriverDetails().hide();
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                         (String)resourceBundle.getObject("DRIVER_DISASSOCIATED"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        } else if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") !=
                   null) {

            BindingContainer bindings =
                BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding =
                bindings.getOperationBinding("updateVehicleDriver");
            operationBinding.getParamsMap().put("cardNumber", null);

            operationBinding.getParamsMap().put("type", "Vehicle");
            operationBinding.getParamsMap().put("countryCd", lang);

            operationBinding.getParamsMap().put("vehicleDriverValue",
                                                AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber").toString());

            operationBinding.getParamsMap().put("associatedAccount",
                                                associatedAccount);
            operationBinding.getParamsMap().put("modifiedBy", modifiedBy);


            Object result = operationBinding.execute();


            if (reset) {
                if (resourceBundle.containsKey("VEHICLE_DISASSOCIATED")) {

                    vehicleModifiedByVisible = false;
                    vehicleModifiedDateVisible = false;

                    getBindings().getTruckdriverDetails().hide();
                    getBindings().getVehicleDriverRadio().setSubmittedValue(null);
                    getBindings().getVehicleDriverRadio().setValue(null);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleDriverRadio());
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                         (String)resourceBundle.getObject("VEHICLE_DISASSOCIATED"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

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
                accountPassingValues =
                        populateStringValues(getBindings().getAccount().getValue().toString());
            }
            if (getBindings().getStatus().getValue() != null) {
                statusPassingValues =
                        populateStringValues(getBindings().getStatus().getValue().toString());
            }
            if (getBindings().getCardGroup().getValue() != null) {
                cardGroupPassingValues =
                        populateStringValues(getBindings().getCardGroup().getValue().toString());
                populateCardGroupValues(cardGroupPassingValues);

            }


        }
        ViewObject vo = ADFUtils.getViewObject("PrtViewCardsVO1Iterator");


        if (session.getAttribute("view_card_account_Query") != null) {
            accountQuery =
                    session.getAttribute("view_card_account_Query").toString().trim();
        }
        if (session.getAttribute("view_card_cardGroup_Query") != null) {
            cardGroupQuery =
                    session.getAttribute("view_card_cardGroup_Query").toString().trim();
        }
        if (session.getAttribute("view_card_expiry_Query") != null) {
            expiryQuery =
                    session.getAttribute("view_card_expiry_Query").toString().trim();
        }


        vo.setNamedWhereClauseParam("partnerId",
                                    getBindings().getPartner().getValue().toString().trim());

        vo.setNamedWhereClauseParam("countryCd", lang);


        if (!statusPassingValues.contains("2")) {
            Date dateNow = new java.util.Date();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yy");
            currentDate = dateformat.format(dateNow);
            expiryQuery = "(CARD_EXPIRY > =: currentDate)";
            vo.setNamedWhereClauseParam("status", statusPassingValues);
            vo.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " +
                              expiryQuery);
        } else {
            if (statusPassingValues.contains("0") &&
                !statusPassingValues.contains("1")) {
                String status = "0,1,2";
                vo.setNamedWhereClauseParam("status", status);
                Date dateNow = new java.util.Date();
                SimpleDateFormat dateformat =
                    new SimpleDateFormat("dd-MMM-yy");
                currentDate = dateformat.format(dateNow);
                expiryQuery =
                        "((BLOCK_ACTION = '1' AND CARD_EXPIRY < =: currentDate) OR (BLOCK_ACTION IN ('0','2')))";
                vo.setWhereClause(accountQuery + "AND " + cardGroupQuery +
                                  "AND " + expiryQuery);
            } else if (!statusPassingValues.contains("0") &&
                       statusPassingValues.contains("1")) {
                String status = "0,1,2";
                vo.setNamedWhereClauseParam("status", status);
                Date dateNow = new java.util.Date();
                SimpleDateFormat dateformat =
                    new SimpleDateFormat("dd-MMM-yy");
                currentDate = dateformat.format(dateNow);
                expiryQuery =
                        "((BLOCK_ACTION = '0' AND CARD_EXPIRY < =: currentDate) OR (BLOCK_ACTION IN ('1','2')))";
                vo.setWhereClause(accountQuery + "AND " + cardGroupQuery +
                                  "AND " + expiryQuery);
            } else if (statusPassingValues.equalsIgnoreCase("2")) {
                String status = "0,1,2";
                vo.setNamedWhereClauseParam("status", status);
                Date dateNow = new java.util.Date();
                SimpleDateFormat dateformat =
                    new SimpleDateFormat("dd-MMM-yy");
                currentDate = dateformat.format(dateNow);
                expiryQuery =
                        "((BLOCK_ACTION IN ('0','1') AND CARD_EXPIRY < =: currentDate) OR (BLOCK_ACTION = '2'))";
                vo.setWhereClause(accountQuery + "AND " + cardGroupQuery +
                                  "AND " + expiryQuery);
            } else {
                vo.setNamedWhereClauseParam("status", statusPassingValues);
                vo.setWhereClause(accountQuery + "AND " + cardGroupQuery);
            }
        }

        if (accountIdValue.size() > 150) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Account Values > 150 ");
            mapAccountListValue =
                    valueList.callValueList(accountIdValue.size(),
                                            accountIdValue);
            for (int i = 0; i < mapAccountListValue.size(); i++) {
                String values = "account" + i;
                String listName = "listName" + i;
                vo.defineNamedWhereClauseParam(values,
                                               mapAccountListValue.get(listName),
                                               null);
            }
        } else {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Account Values < 150 ");
            vo.defineNamedWhereClauseParam("account",
                                           populateStringValues(getBindings().getAccount().getValue().toString()),
                                           null);
        }


        if (cardGroupValue.size() > 150) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "CardGroup Values > 150 ");
            mapCardGroupListValue =
                    valueList.callValueList(cardGroupValue.size(),
                                            cardGroupValue);
            for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                String values = "cardGroup" + i;
                String listName = "listName" + i;
                vo.defineNamedWhereClauseParam(values,
                                               mapCardGroupListValue.get(listName),
                                               null);
            }
        } else {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "CardGroup Values < 150 ");
            vo.defineNamedWhereClauseParam("cardGroup",
                                           populateStringValues(getBindings().getCardGroup().getValue().toString()),
                                           null);
        }


        if (currentDate != null &&
            (!statusPassingValues.contains("2") || !statusPassingValues.contains("1") ||
             !statusPassingValues.contains("0"))) {
            vo.defineNamedWhereClauseParam("currentDate", currentDate, null);
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
        if ("xls".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
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
        if ("xls".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
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
        FilterableQueryDescriptor queryDescriptor =
            (FilterableQueryDescriptor)getBindings().getSearchResultsTB().getFilterModel();
        if (queryDescriptor != null &&
            queryDescriptor.getFilterCriteria() != null) {
            queryDescriptor.getFilterCriteria().clear();
            getBindings().getSearchResultsTB().queueEvent(new QueryEvent(getBindings().getSearchResultsTB(),
                                                                         queryDescriptor));
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
        FilterableQueryDescriptor qd =
            (FilterableQueryDescriptor)getBindings().getSearchResultsTB().getFilterModel();
        QueryEvent queryEvent =
            new QueryEvent(getBindings().getSearchResultsTB(), qd);
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


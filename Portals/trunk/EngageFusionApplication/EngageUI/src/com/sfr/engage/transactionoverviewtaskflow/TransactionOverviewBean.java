package com.sfr.engage.transactionoverviewtaskflow;


import com.sfr.core.bean.*;
import com.sfr.engage.core.*;
import com.sfr.engage.model.queries.rvo.*;
import com.sfr.engage.model.resources.*;
import com.sfr.util.*;
import com.sfr.util.constants.*;
import com.sfr.util.validations.*;

import java.io.*;

import java.sql.*;

import java.text.*;

import java.util.*;
import java.util.Date;

import javax.el.*;

import javax.faces.application.*;
import javax.faces.context.*;
import javax.faces.event.*;
import javax.faces.model.*;

import javax.servlet.http.*;

import oracle.adf.model.*;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.*;
import oracle.adf.view.rich.component.rich.*;
import oracle.adf.view.rich.component.rich.data.*;
import oracle.adf.view.rich.component.rich.input.*;
import oracle.adf.view.rich.component.rich.layout.*;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.*;
import oracle.adf.view.rich.event.*;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;
import oracle.adf.view.rich.util.*;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.*;

import org.apache.poi.hssf.usermodel.*;


public class TransactionOverviewBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;

    private ArrayList<SelectItem> partnerIdList;
    private List<String> partnerIdValue;
    private ArrayList<String> partnerIdValues;
    private ArrayList<SelectItem> accountIdList;
    private List<String> accountIdValue;
    private ArrayList<SelectItem> reportFormatList;
    private String reportFormatValue;
    private ArrayList<SelectItem> termianlList;
    private List shuttleList = new ArrayList();
    private List shuttleValue;
    private List<String> terminalValue;
    private ArrayList<SelectItem> typeList;
    private List<String> typeValue;
    private ArrayList<SelectItem> cardNumberList;
    private List<String> cardNumberValue;
    private ArrayList<SelectItem> cardGroupList;
    private List<String> cardGroupValue;
    private ArrayList<SelectItem> vehicleNumberList;
    private List<String> vehicleNumberValue;
    private ArrayList<SelectItem> driverNameList;
    private List<String> driverNameValue;
    private boolean cardIdPGL = false;
    private boolean cardGPGL = false;
    private boolean dNamePGL = false;
    private boolean vNumberPGL = false;
    private boolean reportDefault = false;
    private boolean reportRawData = false;
    private boolean filterValue=false;
    private boolean reportInternationalTx = false;
    private boolean reportPriceSpecification = false;
    private boolean noteInternational=false;
    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    private List<PartnerInfo> partnerInfoList;
    private Boolean isTableVisible = false;
    private Boolean value=false;
    ResourceBundle resourceBundle;
    private Float sum = 0.00f;
    private Float foreignGrossAmountSum = 0.00f;
    private Float vatSum = 0.00f;
    private Float netAmountSum = 0.00f;    
    private String cardGroupSubtypePassValues;
    private String cardGroupMaintypePassValue;
    private String cardGroupSeqPassValues;
    private String partnerId;
    private String contentType;
    private String fileName;
    private Locale locale;
    private String partnerCountry = null;
    Conversion conversionUtility;
    private ValueListSplit valueList;
    private String accountQuery="(";
    private String accountQueryVehicle="(";
    private String accountQueryDriver="(";
    private String cardGroupQuery="(";
    private String cardQuery="((";
    private Map<String,String> mapAccountListValue; 
    private Map<String,String> mapAccountDriverListValue;
    private Map<String,String> mapAccountVehicleListValue;
    private Map<String,String> mapCardGroupListValue;      
    private Map<String,String> mapCardListValue; 
    private String lang;
    private String currencyCode;
    private String strCardGroup;
    private String strCard;
    private String strVehicle;
    private String strDriver;
    private String noteInternationalVal;

    private String strCardGroupPrePopulated;
    private String strCardPrePopulated;
    private String strVehiclePrePopulated;
    private String strDriverPrePopulated;

    private String strCardGroupExtra;
    private String strCardExtra;
    private String strVehicleExtra;
    private String strDriverExtra;
    private boolean shuttleStatus=false;
    private boolean noteVisible=true;
    private boolean fromDateInitial=true;
    private boolean toDateInitial=true;
    private boolean vehicleName=false;

    private String vehicleNumberOdometer = "";
    private String odometerPartner = "";
    private String odometerAccount = "";
    private String odometerKsid = "";
    private String odometerPortal = null;
    private String urefTransactionId = null;
    private String palsCountryCode = null;
    public static final ADFLogger _logger = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();

    public TransactionOverviewBean() {
        conversionUtility = new Conversion();
        valueList = new ValueListSplit();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        conversionUtility = new Conversion();
        resourceBundle = new EngageResourceBundle();
        accountIdList = new ArrayList<SelectItem>();
        terminalValue = new ArrayList<String>();
        typeValue = new ArrayList<String>();
        partnerId = null;

        if (session.getAttribute("Partner_Object_List") != null) {
            partnerInfoList =
                    (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
            if (partnerInfoList != null) {
                partnerIdList = new ArrayList<SelectItem>();
                partnerIdValue = new ArrayList<String>();
                partnerIdValues = new ArrayList<String>();
                accountIdList = new ArrayList<SelectItem>();
                accountIdValue = new ArrayList<String>();
                for (int k = 0; k < partnerInfoList.size(); k++) {  
                    lang = partnerInfoList.get(0).getCountry().toString().trim();
                    SelectItem selectItem = new SelectItem();
                    if(partnerInfoList.get(k).getPartnerName()!=null && partnerInfoList.get(k).getPartnerValue()!=null)
                    {
                    selectItem.setLabel(partnerInfoList.get(k).getPartnerName().toString());
                    selectItem.setValue(partnerInfoList.get(k).getPartnerValue().toString());
                    partnerIdList.add(selectItem);                                    
                    partnerIdValue.add(partnerInfoList.get(k).getPartnerValue());                        
                    partnerIdValues.add(partnerInfoList.get(k).getPartnerValue());
                    }
                    if (partnerInfoList.get(k).getAccountList() != null &&
                        partnerInfoList.get(k).getAccountList().size() > 0) {                        
                        for (int ac = 0;ac < partnerInfoList.get(k).getAccountList().size();ac++) {                            
                            SelectItem selectItemAcc = new SelectItem();
                            selectItemAcc.setLabel(partnerInfoList.get(k).getAccountList().get(ac).getAccountNumber().toString());
                            selectItemAcc.setValue(partnerInfoList.get(k).getAccountList().get(ac).getAccountNumber().toString());
                            accountIdList.add(selectItemAcc);
                            accountIdValue.add(partnerInfoList.get(k).getAccountList().get(ac).getAccountNumber().toString());
                        }
                    }
                    
                }
            }  
            Collections.sort (partnerIdList,comparator);
            Collections.sort (accountIdList,comparator);            
        }
        if(session!=null) {
            if(session.getAttribute("account_Query")!=null)
            {            
            accountQuery=session.getAttribute("account_Query").toString().trim();
            mapAccountListValue= (Map<String,String>)session.getAttribute("map_Account_List");
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                 "account Query & mapAccountList is found");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                     "account "+accountQuery);
            }
            if(session.getAttribute("cardGroup_Query")!=null)
            {
            cardGroupQuery=session.getAttribute("cardGroup_Query").toString().trim();
            mapCardGroupListValue= (Map<String,String>)session.getAttribute("map_CardGroup_List");
             _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                 "CardGroup Query & mapCardGroupList is found");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                     "CardGroup "+cardGroupQuery);
            }
            if(session.getAttribute("card_Query")!=null)
            {
            cardQuery=session.getAttribute("card_Query").toString().trim();
            mapCardListValue= (Map<String,String>)session.getAttribute("map_Card_List");
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                 "card Query & mapCardList is found");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                     "Card "+cardQuery);
            }
            
            if(session.getAttribute("account_Query_Driver")!=null)
            {
            accountQueryDriver        =session.getAttribute("account_Query_Driver").toString().trim();
            mapAccountDriverListValue = (Map<String,String>)session.getAttribute("map_Account_List_Driver");
             _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                 "Account Query Driver & map_Account_List_Driver is found");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                     "Account Query Driver "+accountQueryDriver);
            }
            
            if(session.getAttribute("account_Query_Vehicle")!=null)
            {
            accountQueryVehicle           = session.getAttribute("account_Query_Vehicle").toString().trim();
            mapAccountVehicleListValue    = (Map<String,String>)session.getAttribute("map_Account_List_Vehicle");
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                 "Account Query Vehicle & map_Account_List_Vehicle is found");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                     "Account Query Vehicle "+accountQueryVehicle);
            }
            
            
        }


        terminalValue.add("HOME");
        terminalValue.add("EXTERNAL");

        typeValue.add("PRE");
        typeValue.add("PRI");
        typeValue.add("FAK");

        //lang=(String)session.getAttribute(Constants.SESSION_LANGUAGE);

//        if (session != null) {
//            lang = (String)session.getAttribute(Constants.userLang);
//        }
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                     "Language :" + lang);

        if (lang != null) {
            currencyCode = conversionUtility.getCurrencyCode(lang);
            locale = conversionUtility.getLocaleFromCountryCode(lang);
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"currencyCode :" + currencyCode);
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"Locale :" + locale);
        } else {
            currencyCode = conversionUtility.getCurrencyCode("SE");
            locale = conversionUtility.getLocaleFromCountryCode("SE");
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"Default:currencyCode :" + currencyCode);
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"Default:Locale :" + locale);
        }
        
       
        reportFormatValue="Default";
        
       
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

    /**
     * This method is used to populate Account Id
     * @return accountIdList
     */
    public ArrayList<SelectItem> getAccountIdList() {
        return accountIdList;
    }

    public ArrayList<SelectItem> getCardNumberList() {
        return cardNumberList;
    }

    public ArrayList<SelectItem> getCardGroupList() {
        return cardGroupList;
    }

    public ArrayList<SelectItem> getVehicleNumberList() {
        return vehicleNumberList;
    }

    public ArrayList<SelectItem> getDriverNameList() {
        return driverNameList;
    }


    public void setAccountIdValue(List<String> accountIdValue) {
        this.accountIdValue = accountIdValue;
    }

    public List<String> getAccountIdValue() {
        return accountIdValue;
    }


    public void partnerIdValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "partner Id=====>" + valueChangeEvent.getNewValue());
            partnerIdValues = new ArrayList<String>();
            partnerIdValues = (ArrayList<String>)valueChangeEvent.getNewValue();
            accountIdList = new ArrayList<SelectItem>();
            accountIdValue = new ArrayList<String>();
            if (partnerInfoList != null) {
                for (int i = 0; i < partnerIdValues.size(); i++) {
                    for (int k = 0; k < partnerInfoList.size(); k++) {
                        if (partnerIdValues.get(i).equalsIgnoreCase(partnerInfoList.get(k).getPartnerValue().toString())) {                          
                            if (partnerInfoList.get(k).getAccountList() !=
                                null &&
                                partnerInfoList.get(k).getAccountList().size() >
                                0) { 
                                for (int ac = 0;
                                     ac < partnerInfoList.get(k).getAccountList().size();
                                     ac++) {
                                    _logger.info(accessDC.getDisplayRecord() +
                                                 this.getClass() + " " +
                                                 partnerInfoList.get(k).getAccountList().get(ac).getAccountNumber().toString());
                                    SelectItem selectItem = new SelectItem();
                                    selectItem.setLabel(partnerInfoList.get(k).getAccountList().get(ac).getAccountNumber().toString());
                                    selectItem.setValue(partnerInfoList.get(k).getAccountList().get(ac).getAccountNumber().toString());
                                    accountIdList.add(selectItem);
                                    accountIdValue.add(partnerInfoList.get(k).getAccountList().get(ac).getAccountNumber().toString());
                                }
                            }

                        }
                    }
                }
            }
        }else {                
               accountIdList = new ArrayList<SelectItem>();
               accountIdValue = new ArrayList<String>(); 
               cardNumberList = new ArrayList<SelectItem>();
               cardNumberValue = new ArrayList<String>();
               cardGroupList = new ArrayList<SelectItem>();
               cardGroupValue = new ArrayList<String>();
               vehicleNumberList = new ArrayList<SelectItem>();
               vehicleNumberValue = new ArrayList<String>(); 
                driverNameList = new ArrayList<SelectItem>();
                driverNameValue = new ArrayList<String>(); 
            }
            getBindings().getCardCardGrpDrVhOneRadio().setValue(null);            
            cardIdPGL = false;
            cardGPGL = false;
            vNumberPGL = false;
            dNamePGL = false;
            if (isTableVisible) {
                isTableVisible = false;
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowSearchResultPG());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardCardGrpDrVhOneRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "account list is created");
        


    }

    public void radioButtonValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (getBindings().getAccount().getValue() != null) {
            if (valueChangeEvent.getNewValue() != null) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " +
                             "value of radioButon value change event======>" +
                             valueChangeEvent.getNewValue());
                if (valueChangeEvent.getNewValue().equals("CardGroup")) {
                    cardGPGL = true;
                    cardIdPGL = false;
                    dNamePGL = false;
                    vNumberPGL = false;
                    String accountValue =
                        populateStringValues(getBindings().getAccount().getValue().toString());
                    populateValue(valueChangeEvent.getNewValue().toString(),
                                  accountValue);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
                } else if (valueChangeEvent.getNewValue().equals("Card")) {
                    cardGPGL = false;
                    cardIdPGL = true;
                    dNamePGL = false;
                    vNumberPGL = false;
                    String accountValue =
                        populateStringValues(getBindings().getAccount().getValue().toString());
                    populateValue(valueChangeEvent.getNewValue().toString(),
                                  accountValue);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
                } else if (valueChangeEvent.getNewValue().equals("Vehicle")) {
                    cardGPGL = false;
                    cardIdPGL = false;
                    dNamePGL = false;
                    vNumberPGL = true;
                    String accountValue =
                        populateStringValues(getBindings().getAccount().getValue().toString());
                    populateValue(valueChangeEvent.getNewValue().toString(),
                                  accountValue);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
                } else {
                    cardGPGL = false;
                    cardIdPGL = false;
                    dNamePGL = true;
                    vNumberPGL = false;
                    String accountValue =
                        populateStringValues(getBindings().getAccount().getValue().toString());
                    populateValue(valueChangeEvent.getNewValue().toString(),
                                  accountValue);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
                }
            }
        } else {
            showErrorMessage("LINKED_PARTNER");
        }
        if (isTableVisible) {
            isTableVisible = false;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowSearchResultPG());
    }

    public void setCardIdPGL(boolean cardIdPGL) {
        this.cardIdPGL = cardIdPGL;
    }

    public boolean isCardIdPGL() {
        return cardIdPGL;
    }

    public void setCardGPGL(boolean cardGPGL) {
        this.cardGPGL = cardGPGL;
    }

    public boolean isCardGPGL() {
        return cardGPGL;
    }

    public void setDNamePGL(boolean dNamePGL) {
        this.dNamePGL = dNamePGL;
    }

    public boolean isDNamePGL() {
        return dNamePGL;
    }

    public void setVNumberPGL(boolean vNumberPGL) {
        this.vNumberPGL = vNumberPGL;
    }

    public boolean isVNumberPGL() {
        return vNumberPGL;
    }

    public ArrayList<SelectItem> getTermianlList() {
        if (termianlList == null) {
            termianlList = new ArrayList<SelectItem>();
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel("HOME");
            selectItem.setValue("HOME");
            termianlList.add(selectItem);
            SelectItem selectItem1 = new SelectItem();
            selectItem1.setLabel("EXTERNAL");
            selectItem1.setValue("EXTERNAL");
            termianlList.add(selectItem1);
        }
        return termianlList;
    }

    public List getShuttleList() {
        //           shuttleList.add(new javax.faces.model.SelectItem("Value1"));
        //                   shuttleList.add(new javax.faces.model.SelectItem("Value2"));
        //                   shuttleList.add(new javax.faces.model.SelectItem("Value3"));
        //                   shuttleList.add(new javax.faces.model.SelectItem("Option1"));
        //                   shuttleList.add(new javax.faces.model.SelectItem("Option2"));
        //                   shuttleList.add(new javax.faces.model.SelectItem("Option3"));
        return shuttleList;
    }

    public List getShuttleValue() {
        if (getBindings().getCardCardGrpDrVhOneRadio().getValue() != null && getBindings().getReportFormat().getValue() != null) {
            if(!shuttleStatus)
            {
            String langDB=(String)session.getAttribute("lang");
            langDB=langDB.substring(langDB.length()-2, langDB.length());
            langDB=langDB.toUpperCase();
            if ("CardGroup".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {
                shuttleValue = new ArrayList();
                ViewObject prtExportInfoRVO =
                    ADFUtils.getViewObject("PrtExportInfoRVO1Iterator");
                prtExportInfoRVO.setNamedWhereClauseParam("country_Code",
                                                          langDB);
                prtExportInfoRVO.setNamedWhereClauseParam("report_Page",
                                                          "TRANSACTION");
                prtExportInfoRVO.setNamedWhereClauseParam("report_Type",
                                                          getBindings().getReportFormat().getValue().toString());
                prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria",
                                                          getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());
                prtExportInfoRVO.executeQuery();
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " +
                             " PrtExportInfoRVO Estimated Row Count in CardGroup shuttle:" +
                             prtExportInfoRVO.getEstimatedRowCount());
                if (prtExportInfoRVO.getEstimatedRowCount() > 0) {
                    while (prtExportInfoRVO.hasNext()) {
                        PrtExportInfoRVORowImpl prtExportRow =
                            (PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                        strCardGroupPrePopulated =
                                prtExportRow.getPrePopulatedColumns();
                    }
                }
                if (strCardGroupPrePopulated != null) {

                    String[] strHead = strCardGroupPrePopulated.split(Constants.ENGAGE_REPORT_DELIMITER);
                    for (int col = 0; col < strHead.length; col++) {
                        shuttleValue.add(strHead[col].toString());
                    }
                    if(vehicleName) {
                        shuttleValue.add("Fordonsnummer");
                        shuttleValue.add("Namn");                        
                    }
                }
            } else if ("Card".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {
                shuttleValue = new ArrayList();
                ViewObject prtExportInfoRVO =
                    ADFUtils.getViewObject("PrtExportInfoRVO1Iterator");
                prtExportInfoRVO.setNamedWhereClauseParam("country_Code",
                                                          langDB);
                prtExportInfoRVO.setNamedWhereClauseParam("report_Page",
                                                          "TRANSACTION");
                prtExportInfoRVO.setNamedWhereClauseParam("report_Type",
                                                          getBindings().getReportFormat().getValue().toString());
                prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria",
                                                          getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());
                prtExportInfoRVO.executeQuery();
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " +
                             " PrtExportInfoRVO Estimated Row Count in Card shuttle:" +
                             prtExportInfoRVO.getEstimatedRowCount());
                if (prtExportInfoRVO.getEstimatedRowCount() > 0) {
                    while (prtExportInfoRVO.hasNext()) {
                        PrtExportInfoRVORowImpl prtExportRow =
                            (PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                        strCardPrePopulated =
                                prtExportRow.getPrePopulatedColumns();
                    }
                }
                if (strCardPrePopulated != null) {
                    String[] strHead = strCardPrePopulated.split(Constants.ENGAGE_REPORT_DELIMITER);
                    for (int col = 0; col < strHead.length; col++) {
                        shuttleValue.add(strHead[col].toString());
                    }
                    if(vehicleName) {
                        shuttleValue.add("Fordonsnummer");
                        shuttleValue.add("Namn");                        
                    }
                }
            } else if ("Vehicle".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {
                shuttleValue = new ArrayList();
                ViewObject prtExportInfoRVO =
                    ADFUtils.getViewObject("PrtExportInfoRVO1Iterator");
                prtExportInfoRVO.setNamedWhereClauseParam("country_Code",
                                                          langDB);
                prtExportInfoRVO.setNamedWhereClauseParam("report_Page",
                                                          "TRANSACTION");
                prtExportInfoRVO.setNamedWhereClauseParam("report_Type",
                                                          getBindings().getReportFormat().getValue().toString());
                prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria",
                                                          getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());
                prtExportInfoRVO.executeQuery();
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " +
                             " PrtExportInfoRVO Estimated Row Count in vehicle shuttle:" +
                             prtExportInfoRVO.getEstimatedRowCount());
                if (prtExportInfoRVO.getEstimatedRowCount() > 0) {
                    while (prtExportInfoRVO.hasNext()) {
                        PrtExportInfoRVORowImpl prtExportRow =
                            (PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                        strVehiclePrePopulated =
                                prtExportRow.getPrePopulatedColumns();
                    }
                }
                if (strVehiclePrePopulated != null) {
                    String[] strHead = strVehiclePrePopulated.split(Constants.ENGAGE_REPORT_DELIMITER);
                    for (int col = 0; col < strHead.length; col++) {
                        shuttleValue.add(strHead[col].toString());
                    }
                }
            } else {
                shuttleValue = new ArrayList();
                ViewObject prtExportInfoRVO =
                    ADFUtils.getViewObject("PrtExportInfoRVO1Iterator");
                prtExportInfoRVO.setNamedWhereClauseParam("country_Code",
                                                          langDB);
                prtExportInfoRVO.setNamedWhereClauseParam("report_Page",
                                                          "TRANSACTION");
                prtExportInfoRVO.setNamedWhereClauseParam("report_Type",
                                                          getBindings().getReportFormat().getValue().toString());
                prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria",
                                                          getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());
                prtExportInfoRVO.executeQuery();
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " +
                             " PrtExportInfoRVO Estimated Row Count in Driver shuttle:" +
                             prtExportInfoRVO.getEstimatedRowCount());
                if (prtExportInfoRVO.getEstimatedRowCount() > 0) {
                    while (prtExportInfoRVO.hasNext()) {
                        PrtExportInfoRVORowImpl prtExportRow =
                            (PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                        strDriverPrePopulated =
                                prtExportRow.getPrePopulatedColumns();
                    }
                }
                if (strDriverPrePopulated != null) {
                    String[] strHead = strDriverPrePopulated.split(Constants.ENGAGE_REPORT_DELIMITER);
                    for (int col = 0; col < strHead.length; col++) {
                        shuttleValue.add(strHead[col].toString());
                    }
                }
            }
        }

        }
        return shuttleValue;
    }

    public void setShuttleValue(List shuttleValue) {
        this.shuttleValue = shuttleValue;
    }
    
    Comparator<SelectItem> comparator = new Comparator<SelectItem>() {
        @Override
        public int compare(SelectItem s1, SelectItem s2) {
            return s1.getLabel().compareTo(s2.getLabel());
        }
    };

    public ArrayList<SelectItem> getTypeList() {
        if (typeList == null) {
            typeList = new ArrayList<SelectItem>();
            SelectItem selectItem = new SelectItem();
            if (resourceBundle.containsKey("PRELIMINARY")){                
                selectItem.setLabel(resourceBundle.getObject("PRELIMINARY").toString());
                selectItem.setValue("PRE");
                typeList.add(selectItem);
            }            
            SelectItem selectItem1 = new SelectItem();
            if (resourceBundle.containsKey("PRICED")){
                selectItem1.setLabel(resourceBundle.getObject("PRICED").toString());
                selectItem1.setValue("PRI");
                typeList.add(selectItem1);
            }            
            SelectItem selectItem2 = new SelectItem();
            if (resourceBundle.containsKey("INVOICE")){
                selectItem2.setLabel(resourceBundle.getObject("INVOICE").toString());
                selectItem2.setValue("FAK");
                typeList.add(selectItem2);
            }            
        }
        return typeList;
    }

    public void populateValue(String paramType, String accountValue) {
        if (paramType != null) {
            String[] listValues = accountValue.split(",");
            if (paramType.equals("CardGroup")) {
                cardGroupList = new ArrayList<SelectItem>();
                cardGroupValue = new ArrayList<String>();
                if (partnerInfoList != null) {
                    for (int i = 0; i < partnerIdValues.size(); i++) {                        
                        for (int k = 0; k < partnerInfoList.size(); k++) {
                            if (partnerIdValues.get(i).equalsIgnoreCase(partnerInfoList.get(k).getPartnerValue().toString())) {
                                for (int accVal = 0; accVal < listValues.length; accVal++) {
                                    for (int ac = 0; ac < partnerInfoList.get(k).getAccountList().size(); ac++) {
                                        if (listValues[accVal].trim().equalsIgnoreCase(partnerInfoList.get(k).getAccountList().get(ac).getAccountNumber().toString())) {
                                            for (int cg = 0;cg < partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().size();cg++) {                                                
                                                SelectItem selectItem =
                                                    new SelectItem();
                                                selectItem.setLabel(partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getDisplayCardGroupIdName().toString());
                                                selectItem.setValue(partnerInfoList.get(k).getPartnerValue().toString().trim()+ partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupID().toString());
                                                cardGroupList.add(selectItem);
                                                cardGroupValue.add(partnerInfoList.get(k).getPartnerValue().toString().trim()+partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupID().toString());
                                            }
                                        }

                                    }

                                }
                            }
                        }
                    } 
                    Collections.sort (cardGroupList,comparator);
                }
            } else if (paramType.equals("Card")) {
                cardNumberList = new ArrayList<SelectItem>();
                cardNumberValue = new ArrayList<String>();
                if (partnerInfoList != null) {
                    for (int i = 0; i < partnerIdValues.size(); i++) {
                        for (int k = 0; k < partnerInfoList.size(); k++) {
                            if (partnerIdValues.get(i).equalsIgnoreCase(partnerInfoList.get(k).getPartnerValue().toString())) {                                
                                for (int accVal = 0;
                                     accVal < listValues.length; accVal++) {                                    
                                    for (int ac = 0;
                                         ac < partnerInfoList.get(k).getAccountList().size();
                                         ac++) {
                                        if (listValues[accVal].trim().equalsIgnoreCase(partnerInfoList.get(k).getAccountList().get(ac).getAccountNumber().toString())) {

                                            for (int cg = 0;
                                                 cg < partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().size();
                                                 cg++) {

                                                if (partnerInfoList.get(k).getAccountList().get(ac).getCardGroup() !=
                                                    null &&
                                                    partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().size() >
                                                    0) {

                                                    for (int cc = 0;
                                                         cc < partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().size();
                                                         cc++) {
                                                        if (partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID() !=
                                                            null &&
                                                            partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID() !=
                                                            null) {
                                                            SelectItem selectItem =
                                                                new SelectItem();
                                                            selectItem.setLabel(partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
                                                            selectItem.setValue(partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                                            cardNumberList.add(selectItem);
                                                            cardNumberValue.add(partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
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
                    Collections.sort (cardNumberList,comparator);
                }
            } else {
                if (paramType.equals("Vehicle") || paramType.equals("Driver")) {
                    accountQueryDriver = "(";
                    accountQueryVehicle = "(";
                    ViewObject vehicleVo = ADFUtils.getViewObject("PrtCardTransactionVehicleInfoRVO1Iterator");
                    ViewObject driverVo = ADFUtils.getViewObject("PrtCardDriverVehicleInfoRVO1Iterator");
                    
                    if(vNumberPGL){
//                            ViewObject vehicleVo = ADFUtils.getViewObject("PrtCardTransactionVehicleInfoRVO1Iterator");
                            
                            if(accountQueryDriver.length() > 1 && accountQueryDriver!= null){
                                if(accountQueryDriver.equalsIgnoreCase(driverVo.getWhereClause())){
                                    if(mapAccountDriverListValue!=null){  
                                        for(int i=0;i< mapAccountDriverListValue.size();i++) {
                                                String values="account"+i;                            
                                                driverVo.removeNamedWhereClauseParam(values);
                                        }
                                    }else{
                                                driverVo.removeNamedWhereClauseParam("account");
                                        }
                                    driverVo.setWhereClause("");
                                    driverVo.executeQuery();
                                }
                            }
                                
                            if(accountQueryVehicle.length() > 1 && accountQueryVehicle!= null){
                                if(accountQueryVehicle.equalsIgnoreCase(vehicleVo.getWhereClause())){
                                    if(mapAccountVehicleListValue!=null){  
                                        for(int i=0;i< mapAccountVehicleListValue.size();i++) {
                                                String values="account"+i;                            
                                                vehicleVo.removeNamedWhereClauseParam(values);
                                        }
                                    }else{
                                                vehicleVo.removeNamedWhereClauseParam("account");
                                        }
                                    vehicleVo.setWhereClause("");
                                    vehicleVo.executeQuery();
                                }
                            }
                            
                            vehicleNumberList = new ArrayList<SelectItem>();
                            vehicleNumberValue = new ArrayList<String>();
                            
                            vehicleVo.setNamedWhereClauseParam("countryCd", lang);
                            vehicleVo.setNamedWhereClauseParam("paramValue", paramType);
                            
                            if(accountIdValue.size()>150) { 
                                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values inside vehicle/driver > 150 ");
                                mapAccountVehicleListValue = valueList.callValueList(accountIdValue.size(), accountIdValue);
                                for(int i=0;i<mapAccountVehicleListValue.size();i++) {
                                  String values="account"+i;
                                   accountQueryVehicle=accountQueryVehicle+"INSTR(:"+values+",ACCOUNT_NUMBER)<>0 OR ";
                                }
                                 _logger.info(accessDC.getDisplayRecord() + this.getClass() +"Account Query vehicle/driver Values ="+accountQueryVehicle);
                                   accountQueryVehicle=accountQueryVehicle.substring(0, accountQueryVehicle.length()-3);
                                   accountQueryVehicle=accountQueryVehicle+")";
                            }else{
                                mapAccountListValue=null;
                                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values inside vehicle/driver < 150 ");
                                accountQueryVehicle="(INSTR(:account,ACCOUNT_NUMBER)<>0)";
                            }
                            
                            vehicleVo.setWhereClause(accountQueryVehicle);
                            
                            if(accountIdValue.size()>150) {      
                                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values inside vehicle > 150 ");
                                            mapAccountVehicleListValue=valueList.callValueList(accountIdValue.size(), accountIdValue); 
                                            for(int i=0;i<mapAccountVehicleListValue.size();i++) {
                                            String values="account"+i;
                                            String listName="listName"+i;
                                            vehicleVo.defineNamedWhereClauseParam(values, mapAccountVehicleListValue.get(listName),null);
                                            }   
                                                    
                            }else {
                                 _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values inside vehicle < 150 ");
                                    if (getBindings().getAccount().getValue() != null) {
                                        vehicleVo.defineNamedWhereClauseParam("account", populateStringValues(getBindings().getAccount().getValue().toString()),null);
                                    }
                            }  
                             vehicleVo.executeQuery();
                             session.setAttribute("account_Query_Vehicle",accountQueryVehicle);
                             session.setAttribute("map_Account_List_Vehicle",mapAccountVehicleListValue);
                                if (vehicleVo.getEstimatedRowCount() > 0){
                                    for (int n = 0; n < vehicleVo.getEstimatedRowCount(); n++) {
                                        while (vehicleVo.hasNext()) {
                                            PrtCardTransactionVehicleInfoRVORowImpl currRow =(PrtCardTransactionVehicleInfoRVORowImpl)vehicleVo.next();
                                            if (currRow != null) {
                                                if(currRow.getPrtCardPk()!=null){
                                                    SelectItem selectItem =new SelectItem();
                                                    selectItem.setLabel(currRow.getAttribute("VehicleNumber").toString());
                                                    selectItem.setValue(currRow.getAttribute("PrtCardPk").toString());
                                                    vehicleNumberList.add(selectItem);
                                                    vehicleNumberValue.add(currRow.getAttribute("PrtCardPk").toString());
                                                }else{
                                                    if(currRow.getReferenceNumber()!=null) {
                                                        SelectItem selectItem = new SelectItem();
                                                        selectItem.setLabel(currRow.getAttribute("VehicleNumber").toString());
                                                        selectItem.setValue(currRow.getReferenceNumber());
                                                        vehicleNumberList.add(selectItem);
                                                        vehicleNumberValue.add(currRow.getReferenceNumber());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            Collections.sort (vehicleNumberList,comparator);
                        }
                    if (dNamePGL) {
                        
                        if(accountQueryDriver.length() > 1 && accountQueryDriver!= null){
                            if(accountQueryDriver.equalsIgnoreCase(driverVo.getWhereClause())){
                                if(mapAccountDriverListValue!=null){  
                                    for(int i=0;i< mapAccountDriverListValue.size();i++) {
                                            String values="account"+i;                            
                                            driverVo.removeNamedWhereClauseParam(values);
                                    }
                                }else{
                                            driverVo.removeNamedWhereClauseParam("account");
                                    }
                                driverVo.setWhereClause("");
                                driverVo.executeQuery();
                            }
                        }
                            
                        if(accountQueryVehicle.length() > 1 && accountQueryVehicle!= null){
                            if(accountQueryVehicle.equalsIgnoreCase(vehicleVo.getWhereClause())){
                                if(mapAccountVehicleListValue!=null){  
                                    for(int i=0;i< mapAccountVehicleListValue.size();i++) {
                                            String values="account"+i;                            
                                            vehicleVo.removeNamedWhereClauseParam(values);
                                    }
                                }else{
                                            vehicleVo.removeNamedWhereClauseParam("account");
                                    }
                                vehicleVo.setWhereClause("");
                                vehicleVo.executeQuery();
                            }
                        }
                        
//                      ViewObject vo = ADFUtils.getViewObject("PrtCardDriverVehicleInfoRVO1Iterator");
                        driverNameList = new ArrayList<SelectItem>();
                        driverNameValue = new ArrayList<String>();
                        driverVo.setNamedWhereClauseParam("countryCd", lang);
                        driverVo.setNamedWhereClauseParam("paramValue", paramType);
                        
                        if(accountIdValue.size()>150) { 
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values inside vehicle/driver > 150 ");
                            mapAccountDriverListValue = valueList.callValueList(accountIdValue.size(), accountIdValue);
                            for(int i=0;i<mapAccountDriverListValue.size();i++) {
                              String values="account"+i;
                                accountQueryDriver=accountQueryDriver+"INSTR(:"+values+",ACCOUNT_NUMBER)<>0 OR ";
                            }
                             _logger.info(accessDC.getDisplayRecord() + this.getClass() +"Account Query vehicle/driver Values ="+accountQueryDriver);
                               accountQueryDriver=accountQueryDriver.substring(0, accountQueryDriver.length()-3);
                               accountQueryDriver=accountQueryDriver+")";
                        }else{
                            mapAccountListValue=null;
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values inside vehicle/driver < 150 ");
                            accountQueryDriver="(INSTR(:account,ACCOUNT_NUMBER)<>0)";
                        }
                        
                        driverVo.setWhereClause(accountQueryDriver);
                        
                        if(accountIdValue.size()>150) {      
                                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values inside vehicle > 150 ");
                                        mapAccountDriverListValue=valueList.callValueList(accountIdValue.size(), accountIdValue); 
                                        for(int i=0;i<mapAccountDriverListValue.size();i++) {
                                        String values="account"+i;
                                        String listName="listName"+i;
                                        driverVo.defineNamedWhereClauseParam(values, mapAccountDriverListValue.get(listName),null);
                                        }   
                                                
                        }else {
                                 _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values inside vehicle < 150 ");
                                    if (getBindings().getAccount().getValue() != null) {
                                        driverVo.defineNamedWhereClauseParam("account", populateStringValues(getBindings().getAccount().getValue().toString()),null);
                                    }
                        }  
                    driverVo.executeQuery();
                    session.setAttribute("account_Query_Driver",accountQueryDriver);
                    session.setAttribute("map_Account_List_Driver",mapAccountDriverListValue);
                    if (driverVo.getEstimatedRowCount() > 0) {
                        for (int n = 0; n < driverVo.getEstimatedRowCount(); n++) {
                            while (driverVo.hasNext()) {
                                PrtCardDriverVehicleInfoRVORowImpl currRow =(PrtCardDriverVehicleInfoRVORowImpl)driverVo.next();
                                if (currRow != null){
                                    if(currRow.getPrtCardPk()!=null){
                                        SelectItem selectItem =new SelectItem();
                                        selectItem.setLabel(currRow.getAttribute("DriverName").toString());
                                        selectItem.setValue(currRow.getAttribute("PrtCardPk").toString());
                                        driverNameList.add(selectItem);
                                        driverNameValue.add(currRow.getAttribute("PrtCardPk").toString());
                                        }else{
                                            if(currRow.getReferenceNumber()!=null) {
                                                SelectItem selectItem =new SelectItem();
                                                selectItem.setLabel(currRow.getAttribute("DriverName").toString());
                                                selectItem.setValue(currRow.getReferenceNumber());
                                                driverNameList.add(selectItem);
                                                driverNameValue.add(currRow.getReferenceNumber());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Collections.sort (driverNameList,comparator);
                    }
                }
            }
        }
    }
    
    public void resetTableFilter()
        {
            FilterableQueryDescriptor queryDescriptor =
                (FilterableQueryDescriptor) getBindings().getSearchResultsTB().getFilterModel();
            if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null)
            {
                queryDescriptor.getFilterCriteria().clear();
                getBindings().getSearchResultsTB().queueEvent(new QueryEvent(getBindings().getSearchResultsTB(), queryDescriptor));
            }
        }

    public String searchResults() {
        sum = 0.00f;
        //String terminalPassingValues  = null;
        String transTypePassingValues = null;
        String cardNumberPasingValues = null;
        String cardGroupPassingValues = null;
        String newFromDate = null;
        String newToDate = null;
        if (getBindings().getPartner().getValue() != null &&
            getBindings().getReportFormat().getValue() != null &&
            getBindings().getFromDate().getValue() != null &&
            getBindings().getToDate().getValue() != null &&
            getBindings().getCardCardGrpDrVhOneRadio().getValue() != null) {
            //            if(getBindings().getTerminalType().getValue() != null){
            //            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "value of terminal type================>"+getBindings().getTerminalType().getValue().toString().trim());
            //               terminalPassingValues =  populateStringValues(getBindings().getTerminalType().getValue().toString());
            //            }else{
            //                showErrorMessage("ENGAGE_NO_TERMINAL_TYPE");
            //                return null;
            //            }

            if (getBindings().getTransationType().getValue() != null) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " +
                             "value of transaction type================>" +
                             getBindings().getTransationType().getValue().toString().trim());
                transTypePassingValues =
                        populateStringValues(getBindings().getTransationType().getValue().toString());
            } else {
                showErrorMessage("ENGAGE_NO_TRANSACTION_TYPE");
                return null;
            }

            if (getBindings().getFromDate().getValue() != null &&
                getBindings().getToDate().getValue() != null) {
                DateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
                Date effectiveFromDate =
                    (java.util.Date)getBindings().getFromDate().getValue();
                Date effectiveToDate1 =
                    (java.util.Date)getBindings().getToDate().getValue();
                newFromDate = sdf.format(effectiveFromDate);
                newToDate = sdf.format(effectiveToDate1);

                if (effectiveToDate1.before(effectiveFromDate)) {
                    _logger.info(accessDC.getDisplayRecord() +
                                 this.getClass() + " " +
                                 "value of new from date ================>" +
                                 newFromDate);
                    showErrorMessage("ENGAGE_VALID_FROM_TO_DATE");
                    return null;
                }
            }

            if (cardGPGL) {
                if (getBindings().getCardGroup().getValue() != null) {
                    _logger.info(accessDC.getDisplayRecord() +
                                 this.getClass() + " " +
                                 "value of card group================>" +
                                 getBindings().getCardGroup().getValue().toString().trim());
                    cardGroupPassingValues =
                            populateStringValues(getBindings().getCardGroup().getValue().toString());
                    populateCardGroupValues(cardGroupPassingValues);
                } else {
                    showErrorMessage("ENGAGE_NO_CARD_GROUP");
                    return null;
                }
            }

            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "bollean value of vehicle====>" + vNumberPGL +
                         dNamePGL);

            if (cardIdPGL) {
                if (getBindings().getCard().getValue() != null) {
                    _logger.info(accessDC.getDisplayRecord() +
                                 this.getClass() + " " +
                                 "value of card  for card Id================>" +
                                 getBindings().getCard().getValue().toString().trim());
                    cardNumberPasingValues =
                            populateStringValues(getBindings().getCard().getValue().toString());
                } else {
                    showErrorMessage("ENGAGE_NO_CARD");
                    return null;
                }
            }

            if (vNumberPGL) {
                if (getBindings().getVehicleNumber().getValue() != null) {
                    _logger.info(accessDC.getDisplayRecord() +
                                 this.getClass() + " " +
                                 "value of card  for vehicle ================>" +
                                 getBindings().getVehicleNumber().getValue().toString().trim());
                    cardNumberPasingValues =
                            populateStringValues(getBindings().getVehicleNumber().getValue().toString());
                } else {
                    _logger.info(accessDC.getDisplayRecord() +
                                 this.getClass() + " " +
                                 "Is it coming inside vehicle else number block");
                    showErrorMessage("ENGAGE_NO_VEHICLE");
                    return null;
                }
            }

            if (dNamePGL) {
                if (getBindings().getDriverName().getValue() != null) {
                    _logger.info(accessDC.getDisplayRecord() +
                                 this.getClass() + " " +
                                 "value of card driver================>" +
                                 getBindings().getDriverName().getValue().toString().trim());
                    cardNumberPasingValues =
                            populateStringValues(getBindings().getDriverName().getValue().toString());
                } else {
                    showErrorMessage("ENGAGE_NO_DRIVER");
                    return null;
                }
            }


            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "value of trans type ================>" +
                         transTypePassingValues);
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "value of card111111 ================>" +
                         cardNumberPasingValues);
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Account Values ================>" +
                         getBindings().getAccount().getValue());

            isTableVisible = false;
           //mapListValue=valueList.callValueList(arg0, arg1);
                
            ViewObject vo =
                ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");            
            if(cardQuery.length()>2 && cardQuery != null && cardGroupQuery.length()<=2) {            
                if(((accountQuery+"AND "+ cardQuery +"AND PURCHASE_COUNTRY_CODE NOT IN(:purchaseCountryCode)").trim().equalsIgnoreCase(vo.getWhereClause().trim()))  || ((accountQuery+" AND "+ cardQuery +"AND PURCHASE_COUNTRY_CODE NOT IN(:purchaseCountryCode)").trim().equalsIgnoreCase(vo.getWhereClause().trim()))) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                " " + "inside  card where removal with purchase code class");
                    if(mapAccountListValue!=null)
                    {  
                    for(int i=0;i< mapAccountListValue.size();i++) {
                            String values="account"+i;                            
                            vo.removeNamedWhereClauseParam(values);
                    }
                    }else{
                        vo.removeNamedWhereClauseParam("account");
                    }
                    
                    if(mapCardListValue!=null)
                    { 
                    for(int i=0;i< mapCardListValue.size();i++) {
                            String values="card"+i;                            
                            vo.removeNamedWhereClauseParam(values);
                    }
                        for(int i=0;i< mapCardListValue.size();i++) {
                                String values="card2id"+i;                            
                                vo.removeNamedWhereClauseParam(values);
                        }
                    }else{
                        vo.removeNamedWhereClauseParam("card");
                        vo.removeNamedWhereClauseParam("card2id");
                    }
                    vo.removeNamedWhereClauseParam("purchaseCountryCode");
                    vo.setWhereClause("");
                    vo.executeQuery();  
            }else{   
                if(((accountQuery+"AND "+cardQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim())) || ((accountQuery+" AND "+cardQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim()))) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                " " + "inside  card with out purchase code where removal class");
                    if(mapAccountListValue!=null)
                    {  
                    for(int i=0;i< mapAccountListValue.size();i++) {
                            String values="account"+i;                            
                            vo.removeNamedWhereClauseParam(values);
                    }
                    }else {
                        vo.removeNamedWhereClauseParam("account");
                    }
                    if(mapCardListValue!=null)
                    {  
                    for(int i=0;i< mapCardListValue.size();i++) {
                            String values="card"+i;                            
                            vo.removeNamedWhereClauseParam(values);
                    }
                        for(int i=0;i< mapCardListValue.size();i++) {
                                String values="card2id"+i;                            
                                vo.removeNamedWhereClauseParam(values);
                        }
                    }else{
                        vo.removeNamedWhereClauseParam("card");
                        vo.removeNamedWhereClauseParam("card2id");
                    }
                    vo.setWhereClause("");
                    vo.executeQuery();         
                }    
            }                
            }else {   
                if(cardGroupQuery.length()>1 && cardGroupQuery != null && cardQuery.length()<=2) {                  
                    if(((accountQuery+"AND "+cardGroupQuery+"AND PURCHASE_COUNTRY_CODE NOT IN(:purchaseCountryCode)").trim().equalsIgnoreCase(vo.getWhereClause().trim())) || ((accountQuery+" AND "+cardGroupQuery+"AND PURCHASE_COUNTRY_CODE NOT IN(:purchaseCountryCode)").trim().equalsIgnoreCase(vo.getWhereClause().trim()))) {
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                            " " + "inside cardGroup with purchase code where removal class");
                        if(mapAccountListValue!=null)
                        {     
                        for(int i=0;i< mapAccountListValue.size();i++) {
                                String values="account"+i;                            
                                vo.removeNamedWhereClauseParam(values);
                        }
                        }else {
                            vo.removeNamedWhereClauseParam("account");
                        }
                        if(mapCardGroupListValue!=null)
                        {
                        for(int i=0;i< mapCardGroupListValue.size();i++) {
                                String values="cardGroup"+i;                            
                                vo.removeNamedWhereClauseParam(values);
                        }
                        }else {
                            vo.removeNamedWhereClauseParam("cardGroup");
                        }
                        vo.removeNamedWhereClauseParam("purchaseCountryCode");
                        vo.setWhereClause("");
                        vo.executeQuery();                     
                }else{  
                    if(((accountQuery +"AND "+ cardGroupQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim())) || ((accountQuery +" AND "+ cardGroupQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim()))) {
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                    " " + "inside  cardGroup with out purchase code where removal class");
                        if(mapAccountListValue!=null)
                        {
                        for(int i=0;i< mapAccountListValue.size();i++) {
                                String values="account"+i;                            
                                vo.removeNamedWhereClauseParam(values);
                        }
                        }else{
                            vo.removeNamedWhereClauseParam("account");
                        }
                        if(mapCardGroupListValue!=null)
                        {
                        for(int i=0;i< mapCardGroupListValue.size();i++) {
                                String values="cardGroup"+i;                            
                                vo.removeNamedWhereClauseParam(values);
                        }
                        }else{
                            vo.removeNamedWhereClauseParam("cardGroup");
                        }
                        vo.setWhereClause("");
                        vo.executeQuery(); 
                    }  
              }
            }
                
            }
            resetTableFilter();
            accountQuery="(";
            cardGroupQuery="(";
            cardQuery="((";
            
            vo.setNamedWhereClauseParam("countryCd", lang);
            vo.setNamedWhereClauseParam("partnerId",populateStringValues(getBindings().getPartner().getValue().toString()));
            vo.setNamedWhereClauseParam("type", transTypePassingValues);
            vo.setNamedWhereClauseParam("fromDate", newFromDate);
            vo.setNamedWhereClauseParam("toDate", newToDate);
            if(accountIdValue.size()>150) {      
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                 " " + "Account Values > 150 ");
                mapAccountListValue=valueList.callValueList(accountIdValue.size(), accountIdValue);         
                     for(int i=0;i<mapAccountListValue.size();i++) {
                      String values="account"+i;
                    accountQuery=accountQuery+"INSTR(:"+values+",ACCOUNT_ID)<>0 OR ";
                    }
                     _logger.info(accessDC.getDisplayRecord() + this.getClass() +"Account Query Values ="+accountQuery);
                       accountQuery=accountQuery.substring(0, accountQuery.length()-3);
                        accountQuery=accountQuery+")";
                        
            }else {
                    mapAccountListValue=null;
                 _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                  " " + "Account Values < 150 ");
                accountQuery="(INSTR(:account,ACCOUNT_ID)<>0 ) ";                 
            }   

            if (cardIdPGL || vNumberPGL || dNamePGL) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " + "Inside block for card");               
                if(getBindings().getReportFormat().getValue()!=null) {
                      if("Card".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())){
                          if("International".equalsIgnoreCase(getBindings().getReportFormat().getValue().toString().trim())) {                                            
                        if(cardNumberValue.size()>150) {      
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                             " " + "Card Values > 150 ");
                            mapCardListValue=valueList.callValueList(cardNumberValue.size(), cardNumberValue);         
                                 for(int i=0;i<mapCardListValue.size();i++) {
                                  String values="card"+i;
                                cardQuery=cardQuery+"INSTR(:"+values+",KSID)<>0 OR ";
                                }                                 
                            cardQuery=cardQuery.substring(0, cardQuery.length()-3);
                            cardQuery=cardQuery+") OR ("; 
                            for(int i=0;i<mapCardListValue.size();i++) {
                             String values="card2id"+i;
                            cardQuery=cardQuery+"INSTR(:"+values+",CARD_2_ID)<>0 OR ";
                            }     
                            cardQuery=cardQuery.substring(0, cardQuery.length()-3);
                            cardQuery=cardQuery+") AND ((CARD_ID_2_INFO ='V2' OR CARD_ID_2_INFO ='D' OR CARD_ID_2_INFO ='V') OR CARD_ID_2_INFO IS NULL))";   
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() +"CARD Query Values ="+cardQuery);
                            vo.setWhereClause(accountQuery+"AND "+cardQuery+"AND PURCHASE_COUNTRY_CODE NOT IN(:purchaseCountryCode)");        
                            for(int i=0;i<mapCardListValue.size();i++) {
                            String values="card"+i;
                            String listName="listName"+i;
                            vo.defineNamedWhereClauseParam(values, mapCardListValue.get(listName),
                                                                               null);
                            }   
                            for(int i=0;i<mapCardListValue.size();i++) {
                            String values="card2id"+i;
                            String listName="listName"+i;
                            vo.defineNamedWhereClauseParam(values, mapCardListValue.get(listName),
                                                                               null);
                            }   
                                    
                        }else {
                             _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                              " " + "CARD Values < 150 ");
                            mapCardListValue=null;
                            cardQuery="((INSTR(:card,KSID)<>0 OR INSTR(:card2id,CARD_2_ID)<>0) AND ((CARD_ID_2_INFO ='V2' OR CARD_ID_2_INFO ='D' OR CARD_ID_2_INFO ='V') OR CARD_ID_2_INFO IS NULL))";
                            vo.setWhereClause(accountQuery+"AND "+cardQuery+"AND PURCHASE_COUNTRY_CODE NOT IN(:purchaseCountryCode)");
                             vo.defineNamedWhereClauseParam("card", cardNumberPasingValues,null);
                             vo.defineNamedWhereClauseParam("card2id", cardNumberPasingValues,null);
                        }                         
                        vo.defineNamedWhereClauseParam("purchaseCountryCode", lang,
                                                       null);
                    }else {                        
                        if(cardNumberValue.size()>150) {      
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                             " " + "Card Values > 150 ");
                            mapCardListValue=valueList.callValueList(cardNumberValue.size(), cardNumberValue);         
                                 for(int i=0;i<mapCardListValue.size();i++) {
                                  String values="card"+i;
                                cardQuery=cardQuery+"INSTR(:"+values+",KSID)<>0 OR ";
                                }                                 
                                cardQuery=cardQuery.substring(0, cardQuery.length()-3);
                            cardQuery=cardQuery+") OR (";
                             for(int i=0;i<mapCardListValue.size();i++) {
                              String values="card2id"+i;
                              cardQuery=cardQuery+"INSTR(:"+values+",CARD_2_ID)<>0 OR ";
                               }     
                            cardQuery=cardQuery.substring(0, cardQuery.length()-3);
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() +"CARD Query Values ="+cardQuery);
                            cardQuery=cardQuery+") AND ((CARD_ID_2_INFO ='V2' OR CARD_ID_2_INFO ='D' OR CARD_ID_2_INFO ='V') OR CARD_ID_2_INFO IS NULL))";  
                            vo.setWhereClause(accountQuery+"AND "+cardQuery);        
                            for(int i=0;i<mapCardListValue.size();i++) {
                            String values="card"+i;
                            String listName="listName"+i;
                            vo.defineNamedWhereClauseParam(values, mapCardListValue.get(listName),
                                                                               null);
                            } 
                            for(int i=0;i<mapCardListValue.size();i++) {
                            String values="card2id"+i;
                            String listName="listName"+i;
                            vo.defineNamedWhereClauseParam(values, mapCardListValue.get(listName),
                                                                               null);
                            }   
                            
                                    
                        }else {
                             _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                              " " + "CARD Values < 150 ");
                            mapCardListValue=null;
                            cardQuery="((INSTR(:card,KSID)<>0 OR INSTR(:card2id,CARD_2_ID)<>0) AND ((CARD_ID_2_INFO ='V2' OR CARD_ID_2_INFO ='D' OR CARD_ID_2_INFO ='V') OR CARD_ID_2_INFO IS NULL))";
                            vo.setWhereClause(accountQuery+"AND "+cardQuery);
                             vo.defineNamedWhereClauseParam("card", cardNumberPasingValues,null);
                             vo.defineNamedWhereClauseParam("card2id", cardNumberPasingValues,null);
                        } 
                    }
                  }else if("Vehicle".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())){
                      _logger.info(accessDC.getDisplayRecord() + this.getClass() +"Vehicle");
                          if("International".equalsIgnoreCase(getBindings().getReportFormat().getValue().toString().trim())) {                                            
                        if(vehicleNumberValue.size()>150) {      
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                             " " + "Vehicle Values > 150 ");
                            mapCardListValue=valueList.callValueList(vehicleNumberValue.size(), vehicleNumberValue);         
                                 for(int i=0;i<mapCardListValue.size();i++) {
                                  String values="card"+i;
                                cardQuery=cardQuery+"INSTR(:"+values+",KSID)<>0 OR ";
                                }                                 
                            cardQuery=cardQuery.substring(0, cardQuery.length()-3);
                            cardQuery=cardQuery+") OR ("; 
                            for(int i=0;i<mapCardListValue.size();i++) {
                             String values="card2id"+i;
                            cardQuery=cardQuery+"INSTR(:"+values+",CARD_2_ID)<>0 OR ";
                            }     
                            cardQuery=cardQuery.substring(0, cardQuery.length()-3);
                            cardQuery=cardQuery+") AND ((CARD_ID_2_INFO ='V2' OR CARD_ID_2_INFO ='D' OR CARD_ID_2_INFO ='V') OR CARD_ID_2_INFO IS NULL))";   
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() +"CARD Query Values ="+cardQuery);
                            vo.setWhereClause(accountQuery+"AND "+cardQuery+"AND PURCHASE_COUNTRY_CODE NOT IN(:purchaseCountryCode)");        
                            for(int i=0;i<mapCardListValue.size();i++) {
                            String values="card"+i;
                            String listName="listName"+i;
                            vo.defineNamedWhereClauseParam(values, mapCardListValue.get(listName),
                                                                               null);
                            }   
                            for(int i=0;i<mapCardListValue.size();i++) {
                            String values="card2id"+i;
                            String listName="listName"+i;
                            vo.defineNamedWhereClauseParam(values, mapCardListValue.get(listName),
                                                                               null);
                            }   
                                    
                        }else {
                             _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                              " " + "Vehicle Values < 150 ");
                            mapCardListValue=null;
                            cardQuery="((INSTR(:card,KSID)<>0 OR INSTR(:card2id,CARD_2_ID)<>0) AND ((CARD_ID_2_INFO ='V2' OR CARD_ID_2_INFO ='D' OR CARD_ID_2_INFO ='V') OR CARD_ID_2_INFO IS NULL))";
                            vo.setWhereClause(accountQuery+"AND "+cardQuery+"AND PURCHASE_COUNTRY_CODE NOT IN(:purchaseCountryCode)");
                             vo.defineNamedWhereClauseParam("card", cardNumberPasingValues,null);                            
                             vo.defineNamedWhereClauseParam("card2id", cardNumberPasingValues,null);
                        }                         
                        vo.defineNamedWhereClauseParam("purchaseCountryCode", lang,
                                                       null);
                    }else {                        
                        if(vehicleNumberValue.size()>150) {      
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                             " " + "Vehicle Values > 150 ");
                            mapCardListValue=valueList.callValueList(vehicleNumberValue.size(), vehicleNumberValue);         
                                 for(int i=0;i<mapCardListValue.size();i++) {
                                  String values="card"+i;
                                cardQuery=cardQuery+"INSTR(:"+values+",KSID)<>0 OR ";
                                }                                 
                                cardQuery=cardQuery.substring(0, cardQuery.length()-3);
                            cardQuery=cardQuery+") OR (";
                             for(int i=0;i<mapCardListValue.size();i++) {
                              String values="card2id"+i;
                              cardQuery=cardQuery+"INSTR(:"+values+",CARD_2_ID)<>0 OR ";
                               }     
                            cardQuery=cardQuery.substring(0, cardQuery.length()-3);
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() +"CARD Query Values ="+cardQuery);
                            cardQuery=cardQuery+") AND ((CARD_ID_2_INFO ='V2' OR CARD_ID_2_INFO ='D' OR CARD_ID_2_INFO ='V') OR CARD_ID_2_INFO IS NULL))";  
                            vo.setWhereClause(accountQuery+"AND "+cardQuery);        
                            for(int i=0;i<mapCardListValue.size();i++) {
                            String values="card"+i;
                            String listName="listName"+i;
                            vo.defineNamedWhereClauseParam(values, mapCardListValue.get(listName),
                                                                               null);
                            } 
                            for(int i=0;i<mapCardListValue.size();i++) {
                            String values="card2id"+i;
                            String listName="listName"+i;
                            vo.defineNamedWhereClauseParam(values, mapCardListValue.get(listName),
                                                                               null);
                            }   
                            
                                    
                        }else {
                             _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                              " " + "Vehicle Values < 150 ");
                            mapCardListValue=null;
                            cardQuery="((INSTR(:card,KSID)<>0 OR INSTR(:card2id,CARD_2_ID)<>0) AND ((CARD_ID_2_INFO ='V2' OR CARD_ID_2_INFO ='D' OR CARD_ID_2_INFO ='V') OR CARD_ID_2_INFO IS NULL))";
                            vo.setWhereClause(accountQuery+"AND "+cardQuery);
                             vo.defineNamedWhereClauseParam("card", cardNumberPasingValues,null);
                             vo.defineNamedWhereClauseParam("card2id", cardNumberPasingValues,null);
                        } 
                    }
                  }
                    else
                      {
                          if("Driver".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())){
                                _logger.info(accessDC.getDisplayRecord() + this.getClass() +"Driver");
                                              if("International".equalsIgnoreCase(getBindings().getReportFormat().getValue().toString().trim())) {                                            
                                            if(driverNameValue.size()>150) {      
                                                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                                                 " " + "Driver Values > 150 ");
                                                mapCardListValue=valueList.callValueList(driverNameValue.size(), driverNameValue);         
                                                     for(int i=0;i<mapCardListValue.size();i++) {
                                                      String values="card"+i;
                                                    cardQuery=cardQuery+"INSTR(:"+values+",KSID)<>0 OR ";
                                                    }                                 
                                                cardQuery=cardQuery.substring(0, cardQuery.length()-3);
                                                cardQuery=cardQuery+") OR ("; 
                                                for(int i=0;i<mapCardListValue.size();i++) {
                                                 String values="card2id"+i;
                                                cardQuery=cardQuery+"INSTR(:"+values+",CARD_2_ID)<>0 OR ";
                                                }     
                                                cardQuery=cardQuery.substring(0, cardQuery.length()-3);
                                                cardQuery=cardQuery+") AND ((CARD_ID_2_INFO ='V2' OR CARD_ID_2_INFO ='D' OR CARD_ID_2_INFO ='V') OR CARD_ID_2_INFO IS NULL))";   
                                                _logger.info(accessDC.getDisplayRecord() + this.getClass() +"CARD Query Values ="+cardQuery);
                                                vo.setWhereClause(accountQuery+"AND "+cardQuery+"AND PURCHASE_COUNTRY_CODE NOT IN(:purchaseCountryCode)");        
                                                for(int i=0;i<mapCardListValue.size();i++) {
                                                String values="card"+i;
                                                String listName="listName"+i;
                                                vo.defineNamedWhereClauseParam(values, mapCardListValue.get(listName),
                                                                                                   null);
                                                }   
                                                for(int i=0;i<mapCardListValue.size();i++) {
                                                String values="card2id"+i;
                                                String listName="listName"+i;
                                                vo.defineNamedWhereClauseParam(values, mapCardListValue.get(listName),
                                                                                                   null);
                                                }   
                                                        
                                            }else {
                                                 _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                                                  " " + "Driver Values < 150 ");
                                                mapCardListValue=null;
                                                cardQuery="((INSTR(:card,KSID)<>0 OR INSTR(:card2id,CARD_2_ID)<>0) AND ((CARD_ID_2_INFO ='V2' OR CARD_ID_2_INFO ='D' OR CARD_ID_2_INFO ='V') OR CARD_ID_2_INFO IS NULL))";
                                                vo.setWhereClause(accountQuery+"AND "+cardQuery+"AND PURCHASE_COUNTRY_CODE NOT IN(:purchaseCountryCode)");
                                                 vo.defineNamedWhereClauseParam("card", cardNumberPasingValues,null);                            
                                                 vo.defineNamedWhereClauseParam("card2id", cardNumberPasingValues,null);
                                            }                         
                                            vo.defineNamedWhereClauseParam("purchaseCountryCode", lang,
                                                                           null);
                                        }else {                        
                                            if(driverNameValue.size()>150) {      
                                                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                                                 " " + "Driver Values > 150 ");
                                                mapCardListValue=valueList.callValueList(driverNameValue.size(), driverNameValue);         
                                                     for(int i=0;i<mapCardListValue.size();i++) {
                                                      String values="card"+i;
                                                    cardQuery=cardQuery+"INSTR(:"+values+",KSID)<>0 OR ";
                                                    }                                 
                                                    cardQuery=cardQuery.substring(0, cardQuery.length()-3);
                                                cardQuery=cardQuery+") OR (";
                                                 for(int i=0;i<mapCardListValue.size();i++) {
                                                  String values="card2id"+i;
                                                  cardQuery=cardQuery+"INSTR(:"+values+",CARD_2_ID)<>0 OR ";
                                                   }     
                                                cardQuery=cardQuery.substring(0, cardQuery.length()-3);
                                                _logger.info(accessDC.getDisplayRecord() + this.getClass() +"CARD Query Values ="+cardQuery);
                                                cardQuery=cardQuery+") AND ((CARD_ID_2_INFO ='V2' OR CARD_ID_2_INFO ='D' OR CARD_ID_2_INFO ='V') OR CARD_ID_2_INFO IS NULL))";  
                                                vo.setWhereClause(accountQuery+"AND "+cardQuery);        
                                                for(int i=0;i<mapCardListValue.size();i++) {
                                                String values="card"+i;
                                                String listName="listName"+i;
                                                vo.defineNamedWhereClauseParam(values, mapCardListValue.get(listName),
                                                                                                   null);
                                                } 
                                                for(int i=0;i<mapCardListValue.size();i++) {
                                                String values="card2id"+i;
                                                String listName="listName"+i;
                                                vo.defineNamedWhereClauseParam(values, mapCardListValue.get(listName),
                                                                                                   null);
                                                }   
                                                
                                                        
                                            }else {
                                                 _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                                                  " " + "Driver Values < 150 ");
                                                mapCardListValue=null;
                                                cardQuery="((INSTR(:card,KSID)<>0 OR INSTR(:card2id,CARD_2_ID)<>0) AND ((CARD_ID_2_INFO ='V2' OR CARD_ID_2_INFO ='D' OR CARD_ID_2_INFO ='V') OR CARD_ID_2_INFO IS NULL))";
                                                vo.setWhereClause(accountQuery+"AND "+cardQuery);
                                                 vo.defineNamedWhereClauseParam("card", cardNumberPasingValues,null);
                                                 vo.defineNamedWhereClauseParam("card2id", cardNumberPasingValues,null);
                                            } 
                                        }
                            }
                      }
                }               
                
            } else {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " + "Coming inside card group block");
                if(getBindings().getReportFormat().getValue()!=null) { 
                    if("International".equalsIgnoreCase(getBindings().getReportFormat().getValue().toString().trim())) {                    
                        if(cardGroupValue.size()>150) {      
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                             " " + "CardGroup Values > 150 ");
                            mapCardGroupListValue=valueList.callValueList(cardGroupValue.size(), cardGroupValue);         
                                 for(int i=0;i<mapCardGroupListValue.size();i++) {
                                  String values="cardGroup"+i;
                                cardGroupQuery=cardGroupQuery+"INSTR(:"+values+",PARTNER_ID||CARDGROUP_MAIN_TYPE||CARDGROUP_SUB_TYPE||CARDGROUP_SEQ)<>0 OR ";
                                }
                                 _logger.info(accessDC.getDisplayRecord() + this.getClass() +"CARDGROUP Query Values ="+cardGroupQuery);
                                   cardGroupQuery=cardGroupQuery.substring(0, cardGroupQuery.length()-3);
                                    cardGroupQuery=cardGroupQuery+")"; 
                            vo.setWhereClause(accountQuery+"AND "+cardGroupQuery+"AND PURCHASE_COUNTRY_CODE NOT IN(:purchaseCountryCode)");        
                            for(int i=0;i<mapCardGroupListValue.size();i++) {
                            String values="cardGroup"+i;
                            String listName="listName"+i;
                            vo.defineNamedWhereClauseParam(values, mapCardGroupListValue.get(listName),
                                                                               null);
                            }   
                                    
                        }else {
                             _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                              " " + "CARD Values < 150 ");
                            mapCardGroupListValue=null;
                            cardGroupQuery="INSTR(:cardGroup,PARTNER_ID||CARDGROUP_MAIN_TYPE||CARDGROUP_SUB_TYPE||CARDGROUP_SEQ)<>0 ";
                            vo.setWhereClause(accountQuery+"AND "+cardGroupQuery+"AND PURCHASE_COUNTRY_CODE NOT IN(:purchaseCountryCode)");
                             vo.defineNamedWhereClauseParam("cardGroup", populateStringValues(getBindings().getCardGroup().getValue().toString()),null);
                        }                         
                        vo.defineNamedWhereClauseParam("purchaseCountryCode", lang,
                                                       null);
                        
                    }else {
                        if(cardGroupValue.size()>150) {      
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                             " " + "CardGroup Values > 150 ");
                            mapCardGroupListValue=valueList.callValueList(cardGroupValue.size(), cardGroupValue);         
                                 for(int i=0;i<mapCardGroupListValue.size();i++) {
                                    String values="cardGroup"+i;
                                    cardGroupQuery=cardGroupQuery+"INSTR(:"+values+",PARTNER_ID||CARDGROUP_MAIN_TYPE||CARDGROUP_SUB_TYPE||CARDGROUP_SEQ)<>0 OR ";
                                }
                                 _logger.info(accessDC.getDisplayRecord() + this.getClass() +"CARD Query Values ="+cardQuery);
                                   cardGroupQuery=cardGroupQuery.substring(0, cardGroupQuery.length()-3);
                                    cardGroupQuery=cardGroupQuery+")"; 
                                vo.setWhereClause(accountQuery+"AND "+cardGroupQuery);        
                                for(int i=0;i<mapCardGroupListValue.size();i++) {
                                String values="cardGroup"+i;
                                String listName="listName"+i;
                                vo.defineNamedWhereClauseParam(values, mapCardGroupListValue.get(listName),
                                                                                   null);
                                }   
                                    
                        }else {
                             _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                              " " + "CARD Values < 150 ");
                            mapCardGroupListValue=null;
                            cardGroupQuery="(INSTR(:cardGroup,PARTNER_ID||CARDGROUP_MAIN_TYPE||CARDGROUP_SUB_TYPE||CARDGROUP_SEQ)<>0) ";
                            vo.setWhereClause(accountQuery+"AND "+cardGroupQuery);
                             vo.defineNamedWhereClauseParam("cardGroup", populateStringValues(getBindings().getCardGroup().getValue().toString()),null);
                        } 
                    }
                
                }  
              
            }       
            
            
            if(accountIdValue.size()>150) {      
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                 " " + "Account Values > 150 ");
                mapAccountListValue=valueList.callValueList(accountIdValue.size(), accountIdValue); 
                for(int i=0;i<mapAccountListValue.size();i++) {
                String values="account"+i;
                String listName="listName"+i;
                vo.defineNamedWhereClauseParam(values, mapAccountListValue.get(listName),
                                                                   null);
                }   
                        
            }else {
                 _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                  " " + "Account Values < 150 ");
                 vo.defineNamedWhereClauseParam("account", populateStringValues(getBindings().getAccount().getValue().toString()),null);
            }  
            
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "where clause of view object=====>" +
                         vo.getQuery());
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "where clause of view object=====>" +
                         vo.getWhereClause());
             vo.executeQuery();             
                session.setAttribute("account_Query",accountQuery);
                session.setAttribute("map_Account_List",mapAccountListValue);
                session.setAttribute("cardGroup_Query",cardGroupQuery);
                session.setAttribute("map_CardGroup_List",mapCardGroupListValue);
                session.setAttribute("card_Query",cardQuery);
                session.setAttribute("map_Card_List",mapCardListValue);
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"Queries are saved in session");
            //isTableVisible = true;             
            value=true;
            sum = 0.00f;
            foreignGrossAmountSum = 0.00f;
            vatSum = 0.00f;
            netAmountSum = 0.00f; 
            RowSetIterator iterator = vo.createRowSetIterator(null);
            iterator.reset();
            if (vo.getEstimatedRowCount() != 0) {                
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " + "Inside Estimated row count" +
                             vo.getEstimatedRowCount());                
                while (iterator.hasNext()) {
                    PrtCardTransactionOverviewRVORowImpl row =
                        (PrtCardTransactionOverviewRVORowImpl)iterator.next(); 
                    if(row.getInvoicedGrossAmountRebated()!=null)
                    {
                    Float tempTotal =
                        row.getInvoicedGrossAmountRebated().floatValue();
                    sum = sum + tempTotal;
                    }
                    if(row.getCurrencyGrossAmount()!=null)
                    {
                    Float tempForeignTotal =
                        row.getCurrencyGrossAmount().floatValue();
                    foreignGrossAmountSum = foreignGrossAmountSum + tempForeignTotal;
                    }
                    if(row.getInvoivedVatRebated()!=null)
                    {
                    Float tempVatTotal =
                        row.getInvoivedVatRebated().floatValue();
                    vatSum = vatSum + tempVatTotal;
                    }
                    if(row.getInvoicedNetAmountRebated()!=null)
                    {
                    Float tempNetTotal =
                        row.getInvoicedNetAmountRebated().floatValue();
                    netAmountSum = netAmountSum + tempNetTotal;
                    }
                }
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"sum ="+sum);
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"foreignGrossAmountSum ="+foreignGrossAmountSum);
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"vatSum ="+vatSum);
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"netAmountSum ="+netAmountSum);

                //                for(int i=0;i<=vo.getEstimatedRowCount();i++ ){
                //                    Row rw = vo.getRowAtRangeIndex(i);
                //                        if(rw != null){
                //                            Float temp = rw.getAttribute("InvoicedNetAmountRebated");
                //                            sum = sum + temp;
                //                        }
                //                            //                vo.getViewObject().next();
                //                }

            }
        } else {
            showErrorMessage("ENGAGE_SELECT_TRANSACTION_MANDATORY");
            return null;
        }
        return null;
    }

    public String searchTransactionAction_event(ActionEvent actionEvent) {
        if (getBindings().getReportFormat().getValue() != null) {
            if ("Raw Data".equalsIgnoreCase(getBindings().getReportFormat().getValue().toString())) {
                reportRawData = true;
                reportInternationalTx = false;
                reportPriceSpecification = false;
                reportDefault = false;
            } else if ("International".equalsIgnoreCase(getBindings().getReportFormat().getValue().toString())) {
                reportRawData = false;
                reportInternationalTx = true;
                reportPriceSpecification = false;
                reportDefault = false;
            } else if ("Default".equalsIgnoreCase(getBindings().getReportFormat().getValue().toString())) {
                reportRawData = false;
                reportInternationalTx = false;
                reportPriceSpecification = false;
                reportDefault = true;
            } else {
                if ("Price Specification".equalsIgnoreCase(getBindings().getReportFormat().getValue().toString())) {
                    reportRawData = false;
                    reportInternationalTx = false;
                    reportPriceSpecification = true;
                    reportDefault = false;
                }
            }
        }
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"Partner ID 2-Card"+partnerInfoList.get(0).isConsistsTwoCard());
        if (lang == "SE" || "SE".equalsIgnoreCase(lang.trim())) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside Partner ID 2-Card");
            for (int i = 0; i < partnerIdValues.size(); i++) {
                for (int k = 0; k < partnerInfoList.size(); k++) {
                    if (partnerIdValues.get(i).equalsIgnoreCase(partnerInfoList.get(k).getPartnerValue().toString())) {
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"Partner ID 2-Card"+partnerInfoList.get(k).isConsistsTwoCard());
                        if (partnerInfoList.get(k).isConsistsTwoCard()) {
                              if("Default".equalsIgnoreCase(getBindings().getReportFormat().getValue().toString()))
                              {
                            vehicleName = true;
                              }else {
                                  vehicleName = false;
                              }
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"Partner ID 2-Card Vehicle Name"+vehicleName);
                        }
                    }
                }
            }
        }else {
            vehicleName = false;   
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"Partner ID 2-Card Vehicle Name"+vehicleName);
        }
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"Partner ID 2-Card Vehicle Name"+vehicleName);
        searchResults();                
        return null;
    }

    public void clearSearchListener(ActionEvent actionEvent) {
        // Add event code here...
        getBindings().getPartner().setValue(null);
        this.accountIdValue = null;
        accountIdList = new ArrayList<SelectItem>();
        //getBindings().getAccount().setValue(null);
        this.accountIdValue = null;
        this.partnerIdValue = null;
        this.reportFormatValue = "Default";
        getBindings().getCardCardGrpDrVhOneRadio().setValue(null);        
        getBindings().getFromDate().setValue(null);
        getBindings().getToDate().setValue(null);
        isTableVisible = false;
        cardIdPGL = false;
        cardGPGL = false;
        vNumberPGL = false;
        dNamePGL = false;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartner());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardCardGrpDrVhOneRadio());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getReportFormat());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFromDate());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getToDate());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowSearchResultPG());
    }

    public String showErrorMessage(String errorVar) {
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
        return null;
    }


    public String populateStringValues(String var) {
        String passingValues = null;
        if (var != null) {
            String lovValues = var.trim();
            String selectedValues =
                lovValues.substring(1, lovValues.length() - 1);
            passingValues = selectedValues.trim();

        }
        return passingValues;
    }

    public void populateCardGroupValues(String cardGrpVar) {
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

            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "card group main type======>" +
                         cardGroupMaintypePassValue);
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "card group sub type===>" +
                         cardGroupSubtypePassValues);
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "card group sequence value====>" +
                         cardGroupSeqPassValues);
        }
    }

    public void reportFormatValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null) {
            if("International".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())) {
                noteVisible=false;
            }else {
                noteVisible=true;
            }
        }
        if (isTableVisible) {
            isTableVisible = false;
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getNoteText());  
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowSearchResultPG());              
    }

    public void accountValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null) {
            getBindings().getCardCardGrpDrVhOneRadio().setValue(null);
            cardGPGL = false;
            cardIdPGL = false;
            dNamePGL = false;
            vNumberPGL = false;

            if (isTableVisible) {
                isTableVisible = false;
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowSearchResultPG());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardCardGrpDrVhOneRadio());
        }
    }

    public void setTypeValue(List<String> typeValue) {
        this.typeValue = typeValue;
    }

    public List<String> getTypeValue() {
        return typeValue;
    }

    public void setCardNumberValue(List<String> cardNumberValue) {
        this.cardNumberValue = cardNumberValue;
    }

    public List<String> getCardNumberValue() {
        return cardNumberValue;
    }

    public void setCardGroupValue(List<String> cardGroupValue) {
        this.cardGroupValue = cardGroupValue;
    }

    public List<String> getCardGroupValue() {
        return cardGroupValue;
    }

    public void setVehicleNumberValue(List<String> vehicleNumberValue) {
        this.vehicleNumberValue = vehicleNumberValue;
    }

    public List<String> getVehicleNumberValue() {
        return vehicleNumberValue;
    }

    public void setDriverNameValue(List<String> driverNameValue) {
        this.driverNameValue = driverNameValue;
    }

    public List<String> getDriverNameValue() {
        return driverNameValue;
    }

    public void setTerminalValue(List<String> terminalValue) {
        this.terminalValue = terminalValue;
    }

    public List<String> getTerminalValue() {
        return terminalValue;
    }

    public void setIsTableVisible(Boolean isTableVisible) {
        this.isTableVisible = isTableVisible;
    }

    public Boolean getIsTableVisible() {
        return isTableVisible;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public Float getSum() {
        return sum;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerId() {
        return partnerId;
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

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }


    public String formatConversion(Date date) {
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    public String checkALL(String selectedValues, String type) {
        String val = "";
        String[] listValues = selectedValues.split(",");
        if (listValues.length > 1) {
            if ("Terminal".equalsIgnoreCase(type)) {
                if (termianlList.size() == listValues.length) {
                    val = "ALL";
                } else {
                    val = selectedValues;
                }
            } else if ("Type".equalsIgnoreCase(type)) {
                if (typeList.size() == listValues.length) {
                    val = "ALL";
                } else {
                    for (int i = 0; i < listValues.length; i++) {
                        if ("PRE".equalsIgnoreCase(listValues[i].toString().trim())) {
                            val = val + "Preliminary" + ", ";
                        } else if ("PRI".equalsIgnoreCase(listValues[i].toString().trim())) {
                            val = val + "Priced" + ", ";
                        } else if ("FAK".equalsIgnoreCase(listValues[i].toString().trim())) {
                            val = val + "Invoice" + ", ";
                        }
                    }
                    val = val.substring(0, val.length() - 1);
                }
            }
        }

        return val;
    }

    public String formatConversion(Float passedValue, Locale countryLocale) {
        String val = "";
        NumberFormat numberFormat = NumberFormat.getInstance(countryLocale);
        numberFormat.setMaximumFractionDigits(2);
        val = numberFormat.format(passedValue); 
        return val;
    }

    public String getTimeHour(Timestamp timeStamp) {
        String val = "";
        java.util.Date date = new Date(timeStamp.getTime());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        val = format.format(date);
        return val;
    }

    public void exportToExcelListener(FacesContext facesContext,
                                      OutputStream outputStream) throws IOException {
        //        // Add event code here...
        //        String partnerCompanyName="";
        //        ViewObject partnerVO = ADFUtils.getViewObject("PrtPartnerVO1Iterator");
        //        partnerVO.setWhereClause("PARTNER_ID =: partnerId");
        //        partnerVO.defineNamedWhereClauseParam("partnerId", partnerId,null);
        //        partnerVO.executeQuery();
        //        if (partnerVO.getEstimatedRowCount() != 0) {
        //            while(partnerVO.hasNext()) {
        //                PrtPartnerVORowImpl row=(PrtPartnerVORowImpl)partnerVO.next();
        //                partnerCompanyName=row.getFirstLastName();
        //            }
        //        }
        //
        //        HSSFWorkbook XLS = new HSSFWorkbook();
        //        HSSFRow XLS_SH_R=null;
        //        HSSFCell XLS_SH_R_C=null;
        //        int intRow=0;
        //        HSSFCellStyle cs = XLS.createCellStyle();
        //        HSSFFont f =XLS.createFont();
        //
        //        //create sheet
        //        HSSFSheet XLS_SH=XLS.createSheet();
        //        XLS.setSheetName(0,"TransactionReport");
        //
        //        f.setFontHeightInPoints((short) 10);
        //        f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //        f.setColor((short)0);
        //        cs.setFont(f);
        //
        //        HSSFCellStyle csRight = XLS.createCellStyle();
        //        HSSFFont fnumberData =XLS.createFont();
        //        fnumberData.setFontHeightInPoints((short) 10);
        //        fnumberData.setColor((short)0);
        //        csRight.setFont(fnumberData);
        //        csRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT );
        //
        //        HSSFCellStyle csTotalAmt = XLS.createCellStyle();
        //        HSSFFont fontTotal =XLS.createFont();
        //        fontTotal.setFontHeightInPoints((short) 10);
        //        fontTotal.setColor((short)0);
        //        fontTotal.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //        csTotalAmt.setFont(fontTotal);
        //        csTotalAmt.setAlignment(HSSFCellStyle.ALIGN_RIGHT );
        //
        //        HSSFCellStyle csData = XLS.createCellStyle();
        //        HSSFFont fData =XLS.createFont();
        //        fData.setFontHeightInPoints((short) 10);
        //        fData.setColor((short)0);
        //        csData.setFont(fData);
        //
        //
        //        XLS_SH.setColumnWidth(50, 50);
        //        XLS_SH_R=XLS_SH.createRow(0);
        //        XLS_SH_R_C=XLS_SH_R.createCell(0);
        //        XLS_SH_R_C.setCellStyle(cs);
        //        XLS_SH_R_C.setCellValue("Company: "+partnerCompanyName);
        //
        //
        //
        //        XLS_SH_R= XLS_SH.createRow(1);
        //        XLS_SH_R_C=XLS_SH_R.createCell(0);
        //        XLS_SH_R_C.setCellStyle(cs);
        //        XLS_SH_R_C.setCellValue("Account: "+getBindings().getAccount().getValue().toString()+partnerCompanyName);
        //
        //
        //
        //        XLS_SH_R= XLS_SH.createRow(2);
        //        XLS_SH_R_C=XLS_SH_R.createCell(0);
        //        XLS_SH_R_C.setCellStyle(cs);
        //        XLS_SH_R_C.setCellValue("Terminal: "+checkALL((populateStringValues(getBindings().getTerminalType().getValue().toString())),"Terminal"));
        //
        //        XLS_SH_R= XLS_SH.createRow(3);
        //        XLS_SH_R_C=XLS_SH_R.createCell(0);
        //        XLS_SH_R_C.setCellStyle(cs);
        //        XLS_SH_R_C.setCellValue("Type: "+checkALL((populateStringValues(getBindings().getTransationType().getValue().toString())),"Type"));
        //
        //
        //        XLS_SH_R= XLS_SH.createRow(4);
        //        XLS_SH_R_C=XLS_SH_R.createCell(0);
        //        XLS_SH_R_C.setCellStyle(cs);
        //        XLS_SH_R_C.setCellValue("Period: "+formatConversion((Date)getBindings().getFromDate().getValue())+" to "+formatConversion((Date)getBindings().getToDate().getValue()));
        //
        //        for(int row=5;row<=7;row++) {
        //            XLS_SH_R= XLS_SH.createRow(row);
        //        }
        //
        //
        //        XLS_SH_R= XLS_SH.createRow(8);
        //        XLS_SH_R_C=XLS_SH_R.createCell(5);
        //        XLS_SH_R_C.setCellStyle(cs);
        //        XLS_SH_R_C.setCellValue("*Note : All prices below are in "+"NOK");
        //
        //        if(getBindings().getCardCardGrpDrVhOneRadio().getValue()!=null) {
        //            if("CardGroup".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {
        //                String[] strHead=strCardGroup.split(",");
        //                HSSFCellStyle css = XLS.createCellStyle();
        //                HSSFFont fcss =XLS.createFont();
        //                fcss.setFontHeightInPoints((short) 10);
        //                fcss.setItalic(true);
        //                fcss.setColor((short)0);
        //                css.setFont(fcss);
        //                XLS_SH_R= XLS_SH.createRow(9);
        //                for (int col = 0; col < strHead.length; col++)
        //                {
        //                XLS_SH_R_C =XLS_SH_R.createCell(col);
        //                XLS_SH_R_C.setCellStyle(css);
        //                XLS_SH_R_C.setCellValue(strHead[col].toString());
        //                }
        //                int rowVal=9;
        //
        //                ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");
        //                RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);
        //                iterator.reset();
        //                while (iterator.hasNext()) {
        //                  PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();
        //                    rowVal=rowVal+1;
        //                    XLS_SH_R= XLS_SH.createRow(rowVal);
        //                    if(row!=null) {
        //                    XLS_SH_R_C=XLS_SH_R.createCell(0);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                    if(row.getTransactionDt()!=null)
        //                    {
        //                        String time="";
        //                        if(row.getTransactionTime()!=null) {
        //                            time=getTimeHour(row.getTransactionTime());
        //                        }
        //                    XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().getTime()))+time);
        //                    }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(1);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                    if(row.getStationName()!=null)
        //                    {
        //                    XLS_SH_R_C.setCellValue(row.getStationName().toString());
        //                    }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(2);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                    if(row.getPurchaseCountryCode()!=null)
        //                    {
        //                    XLS_SH_R_C.setCellValue(row.getPurchaseCountryCode().toString());
        //                    }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(3);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                    if(row.getProductName()!=null)
        //                    {
        //                    XLS_SH_R_C.setCellValue(row.getProductName().toString());
        //                    }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(4);
        //                    XLS_SH_R_C.setCellStyle(csRight);
        //                    if(row.getQuantity()!=null)
        //                    {
        //
        //                    XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale));
        //                    }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(5);
        //                    XLS_SH_R_C.setCellStyle(csRight);
        //                    if(row.getInvoicedGrossAmount()!=null)
        //                    {
        //                        //_logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Locale ="+formatConversion(row.getInvoicedGrossAmount(),locale));
        //                    XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmount(),locale));
        //                    }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(6);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                    if(row.getRecieptNo()!=null)
        //                    {
        //                    XLS_SH_R_C.setCellValue(row.getRecieptNo().toString());
        //                    }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(7);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                    if(row.getInvoiceNumberCollective()!=null) {
        //                     XLS_SH_R_C.setCellValue(row.getInvoiceNumberCollective().toString());
        //                     }else {
        //                        if(row.getInvoiceNumberNonCollective()!=null) {
        //                        XLS_SH_R_C.setCellValue(row.getInvoiceNumberNonCollective().toString());
        //                             }
        //                         }
        //                     }
        //
        //                }
        //
        //
        //                //rowVal=rowVal+1;
        //                XLS_SH_R= XLS_SH.createRow(++rowVal);
        //                XLS_SH_R= XLS_SH.createRow(++rowVal);
        //
        //                XLS_SH_R= XLS_SH.createRow(++rowVal);
        //                XLS_SH_R_C=XLS_SH_R.createCell(0);
        //                XLS_SH_R_C.setCellStyle(cs);
        //                XLS_SH_R_C.setCellValue("Total Price");
        //                XLS_SH_R_C=XLS_SH_R.createCell(5);
        //                XLS_SH_R_C.setCellStyle(csTotalAmt);
        //                if(sum!=null)
        //                {
        //                XLS_SH_R_C.setCellValue(formatConversion(sum,locale));
        //                }
        //                 iterator.closeRowSetIterator();
        //
        //            }else if("Card".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())){
        //                String[] strHead=strCard.split(",");
        //                HSSFCellStyle css = XLS.createCellStyle();
        //                HSSFFont fcss =XLS.createFont();
        //                fcss.setFontHeightInPoints((short) 10);
        //                fcss.setItalic(true);
        //                fcss.setColor((short)0);
        //                css.setFont(fcss);
        //                XLS_SH_R= XLS_SH.createRow(9);
        //                for (int col = 0; col < strHead.length; col++)
        //                {
        //                XLS_SH_R_C =XLS_SH_R.createCell(col);
        //                XLS_SH_R_C.setCellStyle(css);
        //                XLS_SH_R_C.setCellValue(strHead[col].toString());
        //                }
        //
        //                int rowVal=9;
        //
        //                ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");
        //                RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);
        //                iterator.reset();
        //                while (iterator.hasNext()) {
        //                  PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();
        //                    rowVal=rowVal+1;
        //                    XLS_SH_R= XLS_SH.createRow(rowVal);
        //                    if(row!=null) {
        //                    XLS_SH_R_C=XLS_SH_R.createCell(0);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                         if(row.getTransactionDt()!=null)
        //                         {
        //                             String time="";
        //                             if(row.getTransactionTime()!=null) {
        //                                 time=getTimeHour(row.getTransactionTime());
        //                             }
        //                             XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().getTime()))+time);
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(1);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                         if(row.getKsid()!=null)
        //                         {
        //                    XLS_SH_R_C.setCellValue(row.getKsid().toString());
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(2);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                         if(row.getStationName()!=null)
        //                         {
        //                    XLS_SH_R_C.setCellValue(row.getStationName().toString());
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(3);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                         if(row.getPurchaseCountryCode()!=null)
        //                         {
        //                    XLS_SH_R_C.setCellValue(row.getPurchaseCountryCode().toString());
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(4);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                         if(row.getProductName()!=null)
        //                         {
        //                    XLS_SH_R_C.setCellValue(row.getProductName().toString());
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(5);
        //                    XLS_SH_R_C.setCellStyle(csRight);
        //                         if(row.getQuantity()!=null)
        //                         {
        //                             XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale));
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(6);
        //                    XLS_SH_R_C.setCellStyle(csRight);
        //                         if(row.getInvoicedGrossAmount()!=null)
        //                         {
        //                             XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmount(),locale));
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(7);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                         if(row.getRecieptNo()!=null)
        //                         {
        //                    XLS_SH_R_C.setCellValue(row.getRecieptNo().toString());
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(8);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                    if(row.getInvoiceNumberCollective()!=null) {
        //                     XLS_SH_R_C.setCellValue(row.getInvoiceNumberCollective().toString());
        //                     }else {
        //                        if(row.getInvoiceNumberNonCollective()!=null) {
        //                        XLS_SH_R_C.setCellValue(row.getInvoiceNumberNonCollective().toString());
        //                             }
        //                         }
        //                     }
        //
        //                }
        //
        //
        //                //rowVal=rowVal+1;
        //                XLS_SH_R= XLS_SH.createRow(++rowVal);
        //                XLS_SH_R= XLS_SH.createRow(++rowVal);
        //
        //                XLS_SH_R= XLS_SH.createRow(++rowVal);
        //                XLS_SH_R_C=XLS_SH_R.createCell(0);
        //                XLS_SH_R_C.setCellStyle(cs);
        //                XLS_SH_R_C.setCellValue("Total Price");
        //                XLS_SH_R_C=XLS_SH_R.createCell(6);
        //                XLS_SH_R_C.setCellStyle(csTotalAmt);
        //                if(sum!=null)
        //                {
        //                    XLS_SH_R_C.setCellValue(formatConversion(sum,locale));
        //                }
        //                 iterator.closeRowSetIterator();
        //
        //            }else if("Vehicle".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())){
        //                String[] strHead=strVehicle.split(",");
        //                HSSFCellStyle css = XLS.createCellStyle();
        //                HSSFFont fcss =XLS.createFont();
        //                fcss.setFontHeightInPoints((short) 10);
        //                fcss.setItalic(true);
        //                fcss.setColor((short)0);
        //                css.setFont(fcss);
        //                XLS_SH_R= XLS_SH.createRow(9);
        //                for (int col = 0; col < strHead.length; col++)
        //                {
        //                XLS_SH_R_C =XLS_SH_R.createCell(col);
        //                XLS_SH_R_C.setCellStyle(css);
        //                XLS_SH_R_C.setCellValue(strHead[col].toString());
        //                }
        //                int rowVal=9;
        //
        //                ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");
        //                RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);
        //                iterator.reset();
        //                while (iterator.hasNext()) {
        //                  PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();
        //                    rowVal=rowVal+1;
        //                    XLS_SH_R= XLS_SH.createRow(rowVal);
        //                    if(row!=null) {
        //                    XLS_SH_R_C=XLS_SH_R.createCell(0);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                      if(row.getTransactionDt()!=null)
        //                      {
        //                          String time="";
        //                          if(row.getTransactionTime()!=null) {
        //                              time=getTimeHour(row.getTransactionTime());
        //                          }
        //                          XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().getTime()))+time);
        //                      }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(1);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                      if(row.getVehicleNumber()!=null)
        //                      {
        //                    XLS_SH_R_C.setCellValue(row.getVehicleNumber().toString());
        //                      }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(2);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                      if(row.getStationName()!=null)
        //                      {
        //                    XLS_SH_R_C.setCellValue(row.getStationName().toString());
        //                      }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(3);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                      if(row.getPurchaseCountryCode()!=null)
        //                      {
        //                    XLS_SH_R_C.setCellValue(row.getPurchaseCountryCode().toString());
        //                      }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(4);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                      if(row.getProductName()!=null)
        //                      {
        //                    XLS_SH_R_C.setCellValue(row.getProductName().toString());
        //                      }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(5);
        //                    XLS_SH_R_C.setCellStyle(csRight);
        //                      if(row.getQuantity()!=null)
        //                      {
        //                          XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale));
        //                      }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(6);
        //                    XLS_SH_R_C.setCellStyle(csRight);
        //                      if(row.getInvoicedGrossAmount()!=null)
        //                      {
        //                          XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmount(),locale));
        //                      }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(7);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                      if(row.getRecieptNo()!=null)
        //                      {
        //                    XLS_SH_R_C.setCellValue(row.getRecieptNo().toString());
        //                      }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(8);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                    if(row.getInvoiceNumberCollective()!=null) {
        //                     XLS_SH_R_C.setCellValue(row.getInvoiceNumberCollective().toString());
        //                     }else {
        //                        if(row.getInvoiceNumberNonCollective()!=null) {
        //                        XLS_SH_R_C.setCellValue(row.getInvoiceNumberNonCollective().toString());
        //                             }
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(9);
        //                    XLS_SH_R_C.setCellStyle(csRight);
        //                    if(row.getOdometerPortal()!=null) {
        //                        XLS_SH_R_C.setCellValue(row.getOdometerPortal().toString());
        //                    }else {
        //                        if(row.getOdometer()!=null) {
        //                        XLS_SH_R_C.setCellValue(row.getOdometer().toString());
        //                             }
        //                    }
        //
        //                      XLS_SH_R_C=XLS_SH_R.createCell(10);
        //                      XLS_SH_R_C.setCellStyle(csRight);
        //                      if(row.getkmTotal()!=null)
        //                      {
        //                      XLS_SH_R_C.setCellValue(row.getkmTotal().toString());
        //                      }
        //
        //                      XLS_SH_R_C=XLS_SH_R.createCell(11);
        //                      XLS_SH_R_C.setCellStyle(csRight);
        //                      if(row.getkmPerLt()!=null)
        //                      {
        //                      XLS_SH_R_C.setCellValue(row.getkmPerLt().toString());
        //                      }
        //
        //                      XLS_SH_R_C=XLS_SH_R.createCell(12);
        //                      XLS_SH_R_C.setCellStyle(csRight);
        //                      if(row.getltPerHundred()!=null)
        //                      {
        //                      XLS_SH_R_C.setCellValue(row.getltPerHundred().toString());
        //                      }
        //
        //                  }
        //
        //                }
        //
        //
        //                //rowVal=rowVal+1;
        //                XLS_SH_R= XLS_SH.createRow(++rowVal);
        //                XLS_SH_R= XLS_SH.createRow(++rowVal);
        //
        //                XLS_SH_R= XLS_SH.createRow(++rowVal);
        //                XLS_SH_R_C=XLS_SH_R.createCell(0);
        //                XLS_SH_R_C.setCellStyle(cs);
        //                XLS_SH_R_C.setCellValue("Total Price");
        //                XLS_SH_R_C=XLS_SH_R.createCell(6);
        //                XLS_SH_R_C.setCellStyle(csTotalAmt);
        //                if(sum!=null)
        //                {
        //                    XLS_SH_R_C.setCellValue(formatConversion(sum,locale));
        //                }
        //                 iterator.closeRowSetIterator();
        //            }else {
        //                String[] strHead=strDriver.split(",");
        //                HSSFCellStyle css = XLS.createCellStyle();
        //                HSSFFont fcss =XLS.createFont();
        //                fcss.setFontHeightInPoints((short) 10);
        //                fcss.setItalic(true);
        //                fcss.setColor((short)0);
        //                css.setFont(fcss);
        //                XLS_SH_R= XLS_SH.createRow(9);
        //                for (int col = 0; col < strHead.length; col++)
        //                {
        //                XLS_SH_R_C =XLS_SH_R.createCell(col);
        //                XLS_SH_R_C.setCellStyle(css);
        //                XLS_SH_R_C.setCellValue(strHead[col].toString());
        //                }
        //
        //                int rowVal=9;
        //
        //                ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");
        //                RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);
        //                iterator.reset();
        //                while (iterator.hasNext()) {
        //                  PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();
        //                    rowVal=rowVal+1;
        //                    XLS_SH_R= XLS_SH.createRow(rowVal);
        //                    if(row!=null) {
        //                    XLS_SH_R_C=XLS_SH_R.createCell(0);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                         if(row.getTransactionDt()!=null)
        //                         {
        //                             String time="";
        //                             if(row.getTransactionTime()!=null) {
        //                                 time=getTimeHour(row.getTransactionTime());
        //                             }
        //                             XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().getTime()))+time);
        //                         }
        //                         XLS_SH_R_C=XLS_SH_R.createCell(1);
        //                         XLS_SH_R_C.setCellStyle(csData);
        //                         if(row.getDriverName()!=null)
        //                         {
        //                         XLS_SH_R_C.setCellValue(row.getDriverName().toString());
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(2);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                         if(row.getStationName()!=null)
        //                         {
        //                    XLS_SH_R_C.setCellValue(row.getStationName().toString());
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(3);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                         if(row.getPurchaseCountryCode()!=null)
        //                         {
        //                    XLS_SH_R_C.setCellValue(row.getPurchaseCountryCode().toString());
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(4);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                         if(row.getProductName()!=null)
        //                         {
        //                    XLS_SH_R_C.setCellValue(row.getProductName().toString());
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(5);
        //                    XLS_SH_R_C.setCellStyle(csRight);
        //                         if(row.getQuantity()!=null)
        //                         {
        //                             XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale));
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(6);
        //                    XLS_SH_R_C.setCellStyle(csRight);
        //                         if(row.getInvoicedGrossAmount()!=null)
        //                         {
        //                             XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmount(),locale));
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(7);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                         if(row.getRecieptNo()!=null)
        //                         {
        //                    XLS_SH_R_C.setCellValue(row.getRecieptNo().toString());
        //                         }
        //                    XLS_SH_R_C=XLS_SH_R.createCell(8);
        //                    XLS_SH_R_C.setCellStyle(csData);
        //                    if(row.getInvoiceNumberCollective()!=null) {
        //                     XLS_SH_R_C.setCellValue(row.getInvoiceNumberCollective().toString());
        //                     }else {
        //                        if(row.getInvoiceNumberNonCollective()!=null) {
        //                        XLS_SH_R_C.setCellValue(row.getInvoiceNumberNonCollective().toString());
        //                             }
        //                         }
        //                     }
        //
        //                }
        //
        //
        //                //rowVal=rowVal+1;
        //                XLS_SH_R= XLS_SH.createRow(++rowVal);
        //                XLS_SH_R= XLS_SH.createRow(++rowVal);
        //
        //                XLS_SH_R= XLS_SH.createRow(++rowVal);
        //                XLS_SH_R_C=XLS_SH_R.createCell(0);
        //                XLS_SH_R_C.setCellStyle(cs);
        //                XLS_SH_R_C.setCellValue("Total Price");
        //                XLS_SH_R_C=XLS_SH_R.createCell(6);
        //                XLS_SH_R_C.setCellStyle(csTotalAmt);
        //                if(sum!=null)
        //                {
        //                    XLS_SH_R_C.setCellValue(formatConversion(sum,locale));
        //                }
        //                 iterator.closeRowSetIterator();
        //
        //
        //            }
        //
        //        }
        //
        //        XLS.write(outputStream);
        //        outputStream.close();
        //
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public void specificExportExcelListener(FacesContext facesContext,
                                            OutputStream outputStream) throws IOException,
                                                                              SQLException,
                                                                              Exception {
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                     "Entering getValues..");
        String selectedValues = "";
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                     "Size ==" + shuttleValue.size());
        //StringBuilder text = new StringBuilder("Size = ").append(getSelectedEmployees().size()).append(", Items added are: ");
        for (int i = 0; i < shuttleValue.size(); i++) {
            //text.append("Item ").append(i).append(" = ").append(l.get(i)).append(", ");          
            selectedValues =
                    selectedValues + shuttleValue.get(i).toString().trim() +"|";
        }        
      
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                     "Formed String =" + selectedValues);
        String passedString =
            selectedValues.substring(0, selectedValues.length() - 1);  
        
        //Getting Resource Bundle Values from DB      
        ReportBundle rb=new ReportBundle();      
        String langDB=(String)session.getAttribute("lang");
        langDB=langDB.substring(langDB.length()-2, langDB.length());
        langDB=langDB.toUpperCase();
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                     "langDB =" + langDB);
        String columnsReport=rb.getContentsForReport("TRANSACTION",langDB,passedString);
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"From Resource Bundle:"+columnsReport);
        String[] headerDataValues = columnsReport.split(Constants.ENGAGE_REPORT_DELIMITER);


        String partnerCompanyName = "";
        
        if (partnerIdList.size() == partnerIdValues.size()) {
            if (resourceBundle.containsKey("ENG_ALL")) {  
                partnerCompanyName  = (String)resourceBundle.getObject("ENG_ALL");
            }
        } else {

            for (int i = 0; i < partnerIdValues.size(); i++) {
                for (int k = 0; k < partnerInfoList.size(); k++) {
                    if (partnerIdValues.get(i).equalsIgnoreCase(partnerInfoList.get(k).getPartnerValue().toString())) {
                        if (partnerInfoList.get(k).getPartnerName() != null)
                            partnerCompanyName =
                                    partnerCompanyName + partnerInfoList.get(k).getPartnerValue().toString() +
                                    ",";
                    }
                }
            }
            partnerCompanyName =
                    partnerCompanyName.substring(0, partnerCompanyName.length() -
                                                 1);
        }
        if ("xls".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Report in Excel Format");
            HSSFWorkbook XLS = new HSSFWorkbook();
            HSSFRow XLS_SH_R = null;
            HSSFCell XLS_SH_R_C = null;
            int intRow = 0;
            HSSFCellStyle cs = XLS.createCellStyle();
            HSSFFont f = XLS.createFont();

            //create sheet
            HSSFSheet XLS_SH = XLS.createSheet();
            if (resourceBundle.containsKey("ENG_TRANSACTION_REPORT")) {  
                XLS.setSheetName(0, (String)resourceBundle.getObject("ENG_TRANSACTION_REPORT"));
            }

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

            HSSFCellStyle csTotalAmt = XLS.createCellStyle();
            HSSFFont fontTotal = XLS.createFont();
            fontTotal.setFontHeightInPoints((short)10);
            fontTotal.setColor((short)0);
            fontTotal.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            csTotalAmt.setFont(fontTotal);
            csTotalAmt.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

            HSSFCellStyle csData = XLS.createCellStyle();
            HSSFFont fData = XLS.createFont();
            fData.setFontHeightInPoints((short)10);
            fData.setColor((short)0);
            csData.setFont(fData);


            XLS_SH.setColumnWidth(50, 50);
            XLS_SH_R = XLS_SH.createRow(0);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if (resourceBundle.containsKey("ENG_COMPANY")) {  
                XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("ENG_COMPANY")+": " + partnerCompanyName);
            }     
            
            XLS_SH_R = XLS_SH.createRow(1);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if (resourceBundle.containsKey("ACCOUNT")) {  
                String accountNumbers = "";                
                if (accountIdValue.size() == accountIdList.size()) {
                    if (resourceBundle.containsKey("ENG_ALL")) {  
                        accountNumbers  = (String)resourceBundle.getObject("ENG_ALL");
                    }
                } else {
                    accountNumbers = populateStringValues(getBindings().getAccount().getValue().toString());
                            
                }
                XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("ACCOUNT")+": " + accountNumbers);
            }              

            XLS_SH_R = XLS_SH.createRow(2);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if("All".equalsIgnoreCase(checkALL((populateStringValues(getBindings().getTransationType().getValue().toString())),"Type"))) {
                if (resourceBundle.containsKey("ENG_ALL") && resourceBundle.containsKey("TYPE")) {  
                    XLS_SH_R_C.setCellValue(resourceBundle.getObject("TYPE")+": "+resourceBundle.getObject("ENG_ALL"));
                }      
            }else{            
            XLS_SH_R_C.setCellValue(resourceBundle.getObject("TYPE")+": " + checkALL((populateStringValues(getBindings().getTransationType().getValue().toString())),"Type"));
            }

            XLS_SH_R = XLS_SH.createRow(3);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if (resourceBundle.containsKey("REPORT_ENG")) {  
                XLS_SH_R_C.setCellValue(resourceBundle.getObject("REPORT_ENG")+": "+getBindings().getReportFormat().getValue().toString());
            }  
            
            XLS_SH_R = XLS_SH.createRow(4);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if (resourceBundle.containsKey("ENG_PERIOD")) {  
                XLS_SH_R_C.setCellValue(resourceBundle.getObject("ENG_PERIOD") +
                                        ": " +
                                        formatConversion((Date)getBindings().getFromDate().getValue()) +
                                        " " +
                                        resourceBundle.getObject("TO_DATE") +
                                        " " +
                                        formatConversion((Date)getBindings().getToDate().getValue()));
            }  
            
            for (int row = 5; row <= 6; row++) {
                XLS_SH_R = XLS_SH.createRow(row);
            }
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Passed String =" + passedString);
            String[] headerValues = passedString.split(Constants.ENGAGE_REPORT_DELIMITER);          
            
            
            XLS_SH_R = XLS_SH.createRow(7);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            if (resourceBundle.containsKey("TRANSACTIONS_INTERNATIONAL_NOTE") && resourceBundle.containsKey("TRANSACTIONS_INTERNATIONAL_NOTE_1")) {  
                XLS_SH_R_C.setCellValue(resourceBundle.getObject("TRANSACTIONS_INTERNATIONAL_NOTE")+" "+resourceBundle.getObject("TRANSACTIONS_INTERNATIONAL_NOTE_1")); 
            }     
            
            XLS_SH_R = XLS_SH.createRow(8);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);
            for (int i = 0; i < headerDataValues.length; i++) {
                if ("Total Amount".equalsIgnoreCase(headerDataValues[i].toString().trim())) {
                    if(getBindings().getReportFormat().getValue()!=null) {
                        if("International".equalsIgnoreCase(getBindings().getReportFormat().getValue().toString().trim())) {
                            XLS_SH_R_C.setCellValue("");                           
                        }else {
                            if (resourceBundle.containsKey("ENGAGE_NOTE_ALL_PRICES_BELOW_ARE_IN")) {  
                                XLS_SH_R_C.setCellValue( resourceBundle.getObject("ENGAGE_NOTE_ALL_PRICES_BELOW_ARE_IN") + currencyCode);
                            }                               
                        }
                    }
                   
                }
            }


            HSSFCellStyle css = XLS.createCellStyle();
            HSSFFont fcss = XLS.createFont();
            fcss.setFontHeightInPoints((short)10);
            fcss.setItalic(true);
            fcss.setColor((short)0);
            css.setFont(fcss);
            XLS_SH_R = XLS_SH.createRow(9);
            int cellValueSpace =0;            
            for (int col = 0; col < headerDataValues.length; col++) {                
                if("Total Amount".equalsIgnoreCase(headerDataValues[col].toString()) || "ForeginGrossAmount".equalsIgnoreCase(headerDataValues[col].toString().trim()) || "Vat".equalsIgnoreCase(headerDataValues[col].toString().trim()) || "Net".equalsIgnoreCase(headerDataValues[col].toString().trim())) {
                    cellValueSpace=1;
                }
            } 
            int dataHeaderColumn=0;
            if(cellValueSpace>0) {
                dataHeaderColumn=dataHeaderColumn+cellValueSpace;
            }
            
            
            for (int col = 0; col < headerValues.length; col++) {
                XLS_SH_R_C = XLS_SH_R.createCell(dataHeaderColumn);
                XLS_SH_R_C.setCellStyle(css);
                XLS_SH_R_C.setCellValue(headerValues[col].toString());                
                dataHeaderColumn=dataHeaderColumn+1;
            }
            
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Created Header Data");
            int rowVal = 9;
            boolean val = false;
            boolean valForeign=false;
            boolean valVat=false;
            boolean valNet=false;
            int valLoc = 0;
            int valForeignLoc=0;
            int valVatLoc=0;
            int valNetLoc=0;
            ViewObject prtCardTransactionOverViewRVO =
                ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");
            RowSetIterator iterator =
                prtCardTransactionOverViewRVO.createRowSetIterator(null);
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "view object row count =="+prtCardTransactionOverViewRVO.getEstimatedRowCount());
            iterator.reset();
            while (iterator.hasNext()) {
                PrtCardTransactionOverviewRVORowImpl row =
                    (PrtCardTransactionOverviewRVORowImpl)iterator.next();
                rowVal = rowVal + 1;
                XLS_SH_R = XLS_SH.createRow(rowVal);
                if (row != null) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                 "Printing excel Data");   
                    int dataColumn=0;
                    if(cellValueSpace>0) {
                        dataColumn=dataColumn+cellValueSpace;
                    }
                    for (int cellValue=0; cellValue < headerDataValues.length;
                         cellValue++) { 
                        if ("Date".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getTransactionDt() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csData);
                                String time = "";
                                if (row.getTransactionTime() != null) {
                                    time =
                                        getTimeHour(row.getTransactionTime().timestampValue());
                                }
                                XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().timestampValue().getTime())) +
                                                        "  " + time);
                            }
                        } else if ("Partner".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getPartnerId() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getPartnerId().toString());
                            }
                        }else if ("Account".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getAccountId() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getAccountId().toString());
                            }
                        } else if ("Station".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getStationName() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getStationName().toString());
                            }
                        } else if ("Country".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getPurchaseCountryCode() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getPurchaseCountryCode().toString());
                            }
                        } else if ("Currency".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getPurchaseCurrency() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getPurchaseCurrency().toString());
                            }
                        } else if ("Product".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getProductName() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getProductName().toString());
                            }
                        } else if ("Vol".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getQuantity() != null) {  
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csRight);                                
                                XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuantity().toString())),
                                                                                                         locale));
                            }
                        } else if ("CardTextLine2".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardTextLine2() != null) {  
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csData);                                
                                XLS_SH_R_C.setCellValue(row.getCardTextLine2().toString());
                            }
                        }else if ("UnitOfMeasure".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getUnitOfMeasure() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getUnitOfMeasure().toString());
                            }
                        }else if ("ForeginUnitPrice".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCurrencyUnitPrice() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csRight);
                                XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getCurrencyUnitPrice().toString())),
                                                                         locale));
                            }
                        } else if ("ForeginGrossAmount".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {

                            if (row.getCurrencyGrossAmount() != null) {
                                valForeign = true;
                                valForeignLoc = dataColumn;
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csRight);
                                XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getCurrencyGrossAmount().toString())),
                                                                         locale));
                            }
                        } else if ("Total Amount".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            _logger.info(accessDC.getDisplayRecord() +
                                         this.getClass() + " " +
                                         "Header String =" +
                                         headerValues[cellValue].toString().trim());
                            if (row.getInvoicedGrossAmountRebated() != null) {
                                val = true;
                                valLoc = dataColumn;
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csRight);
                                _logger.info(accessDC.getDisplayRecord() +
                                             this.getClass() + " " +
                                             "Total Amount =" +
                                             row.getInvoicedGrossAmountRebated());
                                XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmountRebated(),
                                                                         locale));
                            }
                        } else if ("Invoice No".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                            XLS_SH_R_C.setCellStyle(csData);
                            if (row.getInvoiceNo() != null) {
                                XLS_SH_R_C.setCellValue(row.getInvoiceNo().toString());
                            } 
                        } else if ("Discounted Price".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                            XLS_SH_R_C.setCellStyle(csRight);
                            if (row.getInvoicedUnitPriceRebated() != null) {
                                XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedUnitPriceRebated(),
                                                                         locale));
                            } 
                        }else if ("Vat".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                            XLS_SH_R_C.setCellStyle(csRight);
                            if (row.getInvoivedVatRebated() != null) {
                                valVat = true;
                                valVatLoc = dataColumn;
                                XLS_SH_R_C.setCellValue(formatConversion(row.getInvoivedVatRebated(),
                                                                         locale));
                            } 
                        }else if ("Net".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                            XLS_SH_R_C.setCellStyle(csRight);
                            if (row.getInvoicedNetAmountRebated() != null) {
                                valNet = true;
                                valNetLoc = dataColumn;
                                XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedNetAmountRebated(),
                                                                         locale));
                            } 
                        }else if ("Card".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCard1Id() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCard1Id().toString());
                            }
                        } else if ("Vehicle No".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {                           
                            if (row.getVehicleNumber() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getVehicleNumber().toString());
                            }
                        } else if ("InternalName".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getInternalName() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getInternalName().toString());
                            }
                        } else if ("Odometer".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                            XLS_SH_R_C.setCellStyle(csRight);
                            if (row.getOdometerPortal() != null) {
                                XLS_SH_R_C.setCellValue(row.getOdometerPortal().toString());
                            } else {
                                if (row.getOdometer() != null) {
                                    XLS_SH_R_C.setCellValue(row.getOdometer().toString());
                                }
                            }
                        } else if ("TotalKM".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getkmTotal() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csRight);                                
                                XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getkmTotal().toString())),locale));
                            }
                        } else if ("KM/L".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getkmPerLt() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csRight);                                
                                XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getkmPerLt().toString())),locale));
                            }
                        } else if ("L/100KM".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getltPerHundred() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csRight);                                
                                XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getltPerHundred().toString())),locale));
                            }
                        } else if ("CardGroup Description".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardGroupDesc() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCardGroupDesc().toString());
                            }
                        } else if ("CardGroup Id".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardgroupId() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getCardgroupId().toString());
                            }
                        } 
                        else if ("DriverNumber".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getDriverNumber() != null) {
                                XLS_SH_R_C = XLS_SH_R.createCell(dataColumn);
                                XLS_SH_R_C.setCellStyle(csData);
                                XLS_SH_R_C.setCellValue(row.getDriverNumber().toString());
                            }
                        } else {
                            if ("Driver Name".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getDriverName() != null) {
                                    XLS_SH_R_C =
                                            XLS_SH_R.createCell(dataColumn);
                                    XLS_SH_R_C.setCellStyle(csData);
                                    XLS_SH_R_C.setCellValue(row.getDriverName().toString());
                                }
                            }
                        }
                        dataColumn=dataColumn+1;
                    }
                }
            }

            //rowVal=rowVal+1;
            XLS_SH_R = XLS_SH.createRow(++rowVal);
            XLS_SH_R = XLS_SH.createRow(++rowVal);

            if (val || valForeign || valVat || valNet) {                
                XLS_SH_R = XLS_SH.createRow(++rowVal);
                XLS_SH_R_C = XLS_SH_R.createCell(0);
                XLS_SH_R_C.setCellStyle(cs);
                if (resourceBundle.containsKey("ENGAGE_INVOICE_TOTAL_AMOUNT")) {  
                    XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("ENGAGE_INVOICE_TOTAL_AMOUNT"));
                }                      
                if (val) {
                    if (valLoc > 0) {                        
                        XLS_SH_R_C = XLS_SH_R.createCell(valLoc);
                        XLS_SH_R_C.setCellStyle(csTotalAmt);
                        if (sum != null) {
                            XLS_SH_R_C.setCellValue(formatConversion(sum,
                                                                     locale));
                        }
                    }
                }
                if (valForeign) {                    
                    if (valForeignLoc > 0) {
                        XLS_SH_R_C = XLS_SH_R.createCell(valForeignLoc);
                        XLS_SH_R_C.setCellStyle(csTotalAmt);
                        if (foreignGrossAmountSum != null) {
                            XLS_SH_R_C.setCellValue(formatConversion(foreignGrossAmountSum,
                                                                     locale));
                        }
                    }
                }
                if (valVat) {                    
                    if (valVatLoc > 0) {
                        XLS_SH_R_C = XLS_SH_R.createCell(valVatLoc);
                        XLS_SH_R_C.setCellStyle(csTotalAmt);
                        if (vatSum != null) {
                            XLS_SH_R_C.setCellValue(formatConversion(vatSum,
                                                                     locale));
                        }
                    }
                }
                if (valNet) {
                    if (valNetLoc > 0) {
                        XLS_SH_R_C = XLS_SH_R.createCell(valNetLoc);
                        XLS_SH_R_C.setCellStyle(csTotalAmt);
                        if (netAmountSum != null) {
                            XLS_SH_R_C.setCellValue(formatConversion(netAmountSum,
                                                                     locale));
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
            String[] headerValues = passedString.split(Constants.ENGAGE_REPORT_DELIMITER);
            for (int col = 0; col < headerValues.length; col++) {
                out.print(headerValues[col].toString());
                if (col < headerValues.length - 1) {
                    out.print(";");
                }
            }
            out.println();
            ViewObject prtCardTransactionOverViewRVO =
                ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");
            RowSetIterator iterator =
                prtCardTransactionOverViewRVO.createRowSetIterator(null);
            iterator.reset();
            while (iterator.hasNext()) {
                PrtCardTransactionOverviewRVORowImpl row =
                    (PrtCardTransactionOverviewRVORowImpl)iterator.next();
                if (row != null) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                 "Printing Data");
                    for (int cellValue = 0; cellValue < headerDataValues.length;
                         cellValue++) {


                        if ("Date".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getTransactionDt() != null) {

                                String time = "";
                                if (row.getTransactionTime() != null) {
                                    time = getTimeHour(row.getTransactionTime().timestampValue());
                                }
                                out.print(formatConversion(new Date(row.getTransactionDt().timestampValue().getTime())) +
                                          " " + time);
                                if (cellValue != headerValues.length - 1) {
                                    out.print(";");
                                }
                            }
                        } else if ("Partner".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getPartnerId() != null) {                               
                                    out.print(row.getPartnerId().toString());
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print(";");
                                }                            
                        }else if ("Account".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getAccountId() != null) {
                                out.print(row.getAccountId().toString());
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Station".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getStationName() != null) {
                                out.print(row.getStationName().toString());
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Country".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getPurchaseCountryCode() != null) {
                                out.print(row.getPurchaseCountryCode().toString());
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Currency".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getPurchaseCurrency() != null) {
                                out.print(row.getPurchaseCurrency().toString());
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("CardTextLine2".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardTextLine2() != null) {
                                out.print(row.getCardTextLine2().toString());
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        }else if ("Product".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getProductName() != null) {
                                out.print(row.getProductName().toString());
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Vol".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getQuantity() != null) {                                 
                                out.print(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale));
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("UnitOfMeasure".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getUnitOfMeasure() != null) {
                                out.print(row.getUnitOfMeasure().toString());
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        }else if ("ForeginUnitPrice".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCurrencyUnitPrice() != null) {
                                out.print(formatConversion((Float.parseFloat(row.getCurrencyUnitPrice().toString())),
                                                           locale));
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("ForeginGrossAmount".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCurrencyGrossAmount() != null) {
                                out.print(formatConversion((Float.parseFloat(row.getCurrencyGrossAmount().toString())),
                                                           locale));
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Total Amount".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getInvoicedNetAmountRebated() != null) {
                                out.print(formatConversion(row.getInvoicedNetAmountRebated(),
                                                           locale));
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Invoice No".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if(row.getInvoiceNo()!=null)
                            {
                                out.print(row.getInvoiceNo().toString());
                            }  
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        }  else if ("Discounted Price".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {                          
                            if (row.getInvoicedUnitPriceRebated() != null) {
                                out.print(formatConversion(row.getInvoicedUnitPriceRebated(),
                                                                         locale));
                            } 
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        }else if ("Vat".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            
                            if (row.getInvoivedVatRebated() != null) {
                                out.print(formatConversion(row.getInvoivedVatRebated(),
                                                                         locale));
                            } 
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        }else if ("Net".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {                           
                            if (row.getInvoicedNetAmountRebated() != null) {
                                out.print(formatConversion(row.getInvoicedNetAmountRebated(),
                                                                         locale));
                            } 
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        }else if ("Card".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCard1Id() != null) {
                                out.print(row.getCard1Id().toString());
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Vehicle No".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getVehicleNumber() != null) {
                                out.print(row.getVehicleNumber().toString());
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("InternalName".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getInternalName() != null) {
                                out.print(row.getInternalName().toString());
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("Odometer".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getOdometerPortal() != null) {
                                out.print(row.getOdometerPortal().toString());
                            } else {
                                if (row.getOdometer() != null) {
                                    out.print(row.getOdometer().toString());
                                }
                            }

                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("TotalKM".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getkmTotal() != null) {                                
                                out.print(formatConversion((Float.parseFloat(row.getkmTotal().toString())),locale));
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("KM/L".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getkmPerLt() != null) {                                
                                out.print(formatConversion((Float.parseFloat(row.getkmPerLt().toString())),locale));
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("L/100KM".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getltPerHundred() != null) {                                
                                out.print(formatConversion((Float.parseFloat(row.getltPerHundred().toString())),locale));
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("CardGroup Description".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardGroupDesc() != null) {
                                out.print(row.getCardGroupDesc().toString());
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("CardGroup Id".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getCardgroupId() != null) {
                                out.print(row.getCardgroupId().toString());
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else if ("DriverNumber".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            if (row.getDriverNumber() != null) {
                                out.print(row.getDriverNumber().toString());
                            }
                            if (cellValue != headerValues.length - 1) {
                                out.print(";");
                            }
                        } else {
                            if ("Driver Name".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getDriverName() != null) {
                                    out.print(row.getDriverName().toString());
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print(";");
                                }
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
                String[] headerValues = passedString.split(Constants.ENGAGE_REPORT_DELIMITER);
                for (int col = 0; col < headerValues.length; col++) {
                    out.print(headerValues[col].toString());
                    if (col < headerValues.length - 1) {
                        out.print("|");
                    }
                }
                out.println();
                ViewObject prtCardTransactionOverViewRVO =
                    ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");
                RowSetIterator iterator =
                    prtCardTransactionOverViewRVO.createRowSetIterator(null);
                iterator.reset();
                while (iterator.hasNext()) {
                    PrtCardTransactionOverviewRVORowImpl row =
                        (PrtCardTransactionOverviewRVORowImpl)iterator.next();
                    if (row != null) {

                        for (int cellValue = 0;
                             cellValue < headerDataValues.length; cellValue++) {


                            if ("Date".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getTransactionDt() != null) {

                                    String time = "";
                                    if (row.getTransactionTime() != null) {
                                        time =getTimeHour(row.getTransactionTime().timestampValue());
                                    }
                                    out.print(formatConversion(new Date(row.getTransactionDt().timestampValue().getTime())) +
                                              " " + time);
                                    if (cellValue != headerValues.length - 1) {
                                        out.print("|");
                                    }
                                }
                            } else if ("Partner".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getPartnerId() != null) {
                                    out.print(row.getPartnerId().toString());
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            }else if ("Account".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getAccountId() != null) {
                                    out.print(row.getAccountId().toString());
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Station".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getStationName() != null) {
                                    out.print(row.getStationName().toString());
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Country".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getPurchaseCountryCode() != null) {
                                    out.print(row.getPurchaseCountryCode().toString());
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Currency".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getPurchaseCurrency() != null) {
                                    out.print(row.getPurchaseCurrency().toString());
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Product".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getProductName() != null) {
                                    out.print(row.getProductName().toString());
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Vol".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getQuantity() != null) {                                    
                                    out.print(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale));
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            }  else if ("UnitOfMeasure".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                    if (row.getUnitOfMeasure() != null) {
                                        out.print(row.getUnitOfMeasure().toString());
                                        }
                                 if (cellValue != headerValues.length - 1) {
                                     out.print("|");
                                 }
                             } else if ("CardTextLine2".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                    if (row.getCardTextLine2() != null) {
                                        out.print(row.getCardTextLine2().toString());
                                        }
                                    if (cellValue != headerValues.length - 1) {
                                        out.print("|");
                                        }
                            }else if ("Discounted Price".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {                          
                            if (row.getInvoicedUnitPriceRebated() != null) {
                                out.print(formatConversion(row.getInvoicedUnitPriceRebated(),
                                                                         locale));
                            } 
                            if (cellValue != headerValues.length - 1) {
                                out.print("|");
                            }
                        }else if ("Vat".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                            
                            if (row.getInvoivedVatRebated() != null) {
                                out.print(formatConversion(row.getInvoivedVatRebated(),
                                                                         locale));
                            } 
                            if (cellValue != headerValues.length - 1) {
                                out.print("|");
                            }
                        }else if ("Net".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {                           
                            if (row.getInvoicedNetAmountRebated() != null) {
                                out.print(formatConversion(row.getInvoicedNetAmountRebated(),
                                                                         locale));
                            } 
                            if (cellValue != headerValues.length - 1) {
                                out.print("|");
                            }
                        }else if ("ForeginUnitPrice".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getCurrencyUnitPrice() != null) {
                                    out.print(formatConversion((Float.parseFloat(row.getCurrencyUnitPrice().toString())),
                                                               locale));
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("ForeginGrossAmount".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getCurrencyGrossAmount() != null) {
                                    out.print(formatConversion((Float.parseFloat(row.getCurrencyGrossAmount().toString())),
                                                               locale));
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Total Amount".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getInvoicedNetAmountRebated() !=
                                    null) {
                                    out.print(formatConversion(row.getInvoicedNetAmountRebated(),
                                                               locale));
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Invoice No".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getInvoiceNo() != null) {
                                    out.print(row.getInvoiceNo().toString());
                                } 
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Card".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getCard1Id() != null) {
                                    out.print(row.getCard1Id().toString());
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Vehicle No".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getVehicleNumber() != null) {
                                    out.print(row.getVehicleNumber().toString());
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("InternalName".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getInternalName() != null) {
                                    out.print(row.getInternalName().toString());
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Odometer".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getOdometerPortal() != null) {
                                    out.print(row.getOdometerPortal().toString());
                                } else {
                                    if (row.getOdometer() != null) {
                                        out.print(row.getOdometer().toString());
                                    }
                                }

                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("TotalKM".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getkmTotal() != null) {                                    
                                    out.print(formatConversion((Float.parseFloat(row.getkmTotal().toString())),locale));
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("KM/L".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getkmPerLt() != null) {                                    
                                    out.print(formatConversion((Float.parseFloat(row.getkmPerLt().toString())),locale));
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("L/100KM".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getltPerHundred() != null) {                                    
                                    out.print(formatConversion((Float.parseFloat(row.getltPerHundred().toString())),locale));
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("CardGroup Description".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getCardGroupDesc() != null) {
                                    out.print(row.getCardGroupDesc().toString());
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("CardGroup Id".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getCardgroupId() != null) {
                                    out.print(row.getCardgroupId().toString());
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("DriverNumber".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                if (row.getDriverNumber() != null) {
                                    out.print(row.getDriverNumber().toString());
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print("|");
                                }
                            } else {
                                if ("Driver Name".equalsIgnoreCase(headerDataValues[cellValue].toString().trim())) {
                                    if (row.getDriverName() != null) {
                                        out.print(row.getDriverName().toString());
                                    }
                                    if (cellValue != headerValues.length - 1) {
                                        out.print("|");
                                    }
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
    }
    
        
    public void resetFilterTable(ActionEvent actionEvent) {
        resetTableFilter();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResultsTB());
    }

    public void filterTable(ActionEvent actionEvent) {
        FilterableQueryDescriptor qd =
            (FilterableQueryDescriptor)getBindings().getSearchResultsTB().getFilterModel();
        QueryEvent queryEvent =
            new QueryEvent(getBindings().getSearchResultsTB(), qd);
        getBindings().getSearchResultsTB().queueEvent(queryEvent);
        filterValue=true;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResultsTB());
    }

    public void exportExcelSpecificAction(ActionEvent actionEvent) {
        shuttleStatus=false;
        String langDB=(String)session.getAttribute("lang");
        langDB=langDB.substring(langDB.length()-2, langDB.length());
        langDB=langDB.toUpperCase();
        if ("CardGroup".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {
            ViewObject prtExportInfoRVO =
                ADFUtils.getViewObject("PrtExportInfoRVO1Iterator");
            prtExportInfoRVO.setNamedWhereClauseParam("country_Code", langDB);
            prtExportInfoRVO.setNamedWhereClauseParam("report_Page",
                                                      "TRANSACTION");
            prtExportInfoRVO.setNamedWhereClauseParam("report_Type",
                                                      getBindings().getReportFormat().getValue().toString());
            prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria",
                                                      getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());
            prtExportInfoRVO.executeQuery();
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         " PrtExportInfoRVO Estimated Row Count in CardGroup:" +
                         prtExportInfoRVO.getEstimatedRowCount());
            if (prtExportInfoRVO.getEstimatedRowCount() > 0) {
                while (prtExportInfoRVO.hasNext()) {
                    PrtExportInfoRVORowImpl prtExportRow =
                        (PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                    strCardGroup = prtExportRow.getTotalColumns();
                    strCardGroupExtra = prtExportRow.getExtraColumns();
                }
            }
            if (strCardGroup != null) {
                String[] strHead = strCardGroup.split(Constants.ENGAGE_REPORT_DELIMITER);                
                shuttleList = new ArrayList<SelectItem>();
                for (int col = 0; col < strHead.length; col++) {                    
                    SelectItem selectItem = new SelectItem();
                    selectItem.setLabel(strHead[col].toString());
                    selectItem.setValue(strHead[col].toString());
                    shuttleList.add(selectItem);
                }
            }
        } else if ("Card".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {
            ViewObject prtExportInfoRVO =
                ADFUtils.getViewObject("PrtExportInfoRVO1Iterator");
            prtExportInfoRVO.setNamedWhereClauseParam("country_Code", langDB);
            prtExportInfoRVO.setNamedWhereClauseParam("report_Page",
                                                      "TRANSACTION");
            prtExportInfoRVO.setNamedWhereClauseParam("report_Type",
                                                      getBindings().getReportFormat().getValue().toString());
            prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria",
                                                      getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());
            prtExportInfoRVO.executeQuery();
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         " PrtExportInfoRVO Estimated Row Count in Card:" +
                         prtExportInfoRVO.getEstimatedRowCount());
            if (prtExportInfoRVO.getEstimatedRowCount() > 0) {
                while (prtExportInfoRVO.hasNext()) {
                    PrtExportInfoRVORowImpl prtExportRow =
                        (PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                    strCard = prtExportRow.getTotalColumns();
                    strCardExtra = prtExportRow.getExtraColumns();
                }
            }
            if (strCard != null) {
                String[] strHead = strCard.split(Constants.ENGAGE_REPORT_DELIMITER);
                shuttleList = new ArrayList<SelectItem>();
                for (int col = 0; col < strHead.length; col++) {
                    SelectItem selectItem = new SelectItem();
                    selectItem.setLabel(strHead[col].toString());
                    selectItem.setValue(strHead[col].toString());
                    shuttleList.add(selectItem);
                }
            }
        } else if ("Vehicle".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {
            ViewObject prtExportInfoRVO =
                ADFUtils.getViewObject("PrtExportInfoRVO1Iterator");
            prtExportInfoRVO.setNamedWhereClauseParam("country_Code", langDB);
            prtExportInfoRVO.setNamedWhereClauseParam("report_Page",
                                                      "TRANSACTION");
            prtExportInfoRVO.setNamedWhereClauseParam("report_Type",
                                                      getBindings().getReportFormat().getValue().toString());
            prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria",
                                                      getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());
            prtExportInfoRVO.executeQuery();
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         " PrtExportInfoRVO Estimated Row Count in Vehicle:" +
                         prtExportInfoRVO.getEstimatedRowCount());
            if (prtExportInfoRVO.getEstimatedRowCount() > 0) {
                while (prtExportInfoRVO.hasNext()) {
                    PrtExportInfoRVORowImpl prtExportRow =
                        (PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                    strVehicle = prtExportRow.getTotalColumns();
                    strVehicleExtra = prtExportRow.getExtraColumns();
                }
            }
            if (strVehicle != null) {
                String[] strHead = strVehicle.split(Constants.ENGAGE_REPORT_DELIMITER);
                shuttleList = new ArrayList<SelectItem>();
                for (int col = 0; col < strHead.length; col++) {
                    SelectItem selectItem = new SelectItem();
                    selectItem.setLabel(strHead[col].toString());
                    selectItem.setValue(strHead[col].toString());
                    shuttleList.add(selectItem);
                }
            }
        } else {
            ViewObject prtExportInfoRVO =
                ADFUtils.getViewObject("PrtExportInfoRVO1Iterator");
            prtExportInfoRVO.setNamedWhereClauseParam("country_Code", langDB);
            prtExportInfoRVO.setNamedWhereClauseParam("report_Page",
                                                      "TRANSACTION");
            prtExportInfoRVO.setNamedWhereClauseParam("report_Type",
                                                      getBindings().getReportFormat().getValue().toString());
            prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria",
                                                      getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());
            prtExportInfoRVO.executeQuery();
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         " PrtExportInfoRVO Estimated Row Count in Driver:" +
                         prtExportInfoRVO.getEstimatedRowCount());
            if (prtExportInfoRVO.getEstimatedRowCount() > 0) {
                while (prtExportInfoRVO.hasNext()) {
                    PrtExportInfoRVORowImpl prtExportRow =
                        (PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                    strDriver = prtExportRow.getTotalColumns();
                    strDriverExtra = prtExportRow.getExtraColumns();
                }
            }
            if (strDriver != null) {
                String[] strHead = strDriver.split(Constants.ENGAGE_REPORT_DELIMITER);
                shuttleList = new ArrayList<SelectItem>();
                for (int col = 0; col < strHead.length; col++) {
                    SelectItem selectItem = new SelectItem();
                    selectItem.setLabel(strHead[col].toString());
                    selectItem.setValue(strHead[col].toString());
                    shuttleList.add(selectItem);
                }
            }
        }

        boolean result = false;
        if (getBindings().getCardCardGrpDrVhOneRadio().getValue() != null) {
            if (strCardGroup != null) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShuttleExcel());
                result = true;
                getBindings().getSelectionExportOneRadio().setValue("xls");
                getBindings().getSpecificColumns().show(new RichPopup.PopupHints());
            } else if (strCard != null) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShuttleExcel());
                result = true;
                getBindings().getSelectionExportOneRadio().setValue("xls");
                getBindings().getSpecificColumns().show(new RichPopup.PopupHints());
            } else if (strVehicle != null) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShuttleExcel());
                result = true;
                getBindings().getSelectionExportOneRadio().setValue("xls");
                getBindings().getSpecificColumns().show(new RichPopup.PopupHints());
            } else {
                if (strDriver != null) {
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShuttleExcel());
                    result = true;
                    getBindings().getSelectionExportOneRadio().setValue("xls");
                    getBindings().getSpecificColumns().show(new RichPopup.PopupHints());
                }
            }
        }

        if (!result) {
            if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR_DB")) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR_DB"),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }


    }


    public void specificExcelAction(ActionEvent actionEvent) {
        // Add event code here...

    }

    public void getValuesForExcel(ActionEvent actionEvent) {
        // _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Size =="+shuttleValue.size());
        if(shuttleValue == null && getBindings().getSelectionExportOneRadio().getValue() == null) {
            if(shuttleValue == null)
            {
            if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR")) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR"),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            
            }else {
                if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR_SELECTION")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR_SELECTION"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }else
        {
            if(getBindings().getSelectionExportOneRadio().getValue() != null)
            {
                if(shuttleValue == null) {
                    if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR")) {
                        FacesMessage msg =
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                             (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR"),
                                             "");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }else
                {
                if (shuttleValue.size() > 0 &&
                    getBindings().getSelectionExportOneRadio().getValue() != null) {
                     shuttleStatus=true;
                    getBindings().getConfirmationExcel().show(new RichPopup.PopupHints());
                } 
            }
        }  else {
                if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR_SELECTION")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR_SELECTION"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
     }
    }

    public String confirmationCancelAction() {
        getBindings().getConfirmationExcel().hide();
        return null;
    }

    public String excelDownLoad() {

        return null;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String odometerEditAction() {
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vnumberkey") != null) {
            vehicleNumberOdometer =AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vnumberkey").toString().trim();
        }
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("odometerportalkey") != null) {
            odometerPortal =AdfFacesContext.getCurrentInstance().getPageFlowScope().get("odometerportalkey").toString().trim();
        } else {
            odometerPortal =AdfFacesContext.getCurrentInstance().getPageFlowScope().get("odometerkey").toString().trim();
        }
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"uref id=================>" +
                     AdfFacesContext.getCurrentInstance().getPageFlowScope().get("ureftranskey"));
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"pals country id=================>" +
                     AdfFacesContext.getCurrentInstance().getPageFlowScope().get("palscountrykey"));
        urefTransactionId = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("ureftranskey").toString().trim();
        palsCountryCode = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("palscountrykey").toString().trim();
        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("primarycard") != null){
            odometerKsid= AdfFacesContext.getCurrentInstance().getPageFlowScope().get("primarycard").toString().trim();
        }
        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("partnernumber") != null){
            odometerPartner = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("partnernumber").toString().trim();
        }
        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountid") != null){
            odometerAccount = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountid").toString().trim();
        }
        getBindings().getEditOdometerPopup().show(new RichPopup.PopupHints());

        return null;
    }

    public String editOdometerSave() {
        User user = null;
        String modifiedBy = null;
        user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
        modifiedBy = user.getFirstName().concat(" ").concat(user.getLastName());

        BindingContainer bindings =BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding =bindings.getOperationBinding("updateOdometerPortal");
        operationBinding.getParamsMap().put("urefTransactionId",urefTransactionId);
        operationBinding.getParamsMap().put("palsCountryCode",palsCountryCode);
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"odometer portal popup value=======>" +
                     getBindings().getOdometerPortalValue().getValue());
        operationBinding.getParamsMap().put("modifiedBy", modifiedBy);
        operationBinding.getParamsMap().put("odoMeterPortalValue",getBindings().getOdometerPortalValue().getValue());
        Object result = operationBinding.execute();
        if (operationBinding.getErrors().isEmpty()) {
            ViewObject transactionVo = ADFUtils.getViewObject("PrtCardTransactionHeaderUrefIdUpdateOdometerRvo1Iterator");
             if(odometerKsid != null && odometerPartner != null && odometerAccount != null){
                    transactionVo.setNamedWhereClauseParam("urefTransId", urefTransactionId);
                    transactionVo.setNamedWhereClauseParam("cardNumber", odometerKsid);
                    transactionVo.setNamedWhereClauseParam("countryCode", palsCountryCode);
                    transactionVo.setNamedWhereClauseParam("accountId", odometerAccount);
                    transactionVo.setNamedWhereClauseParam("partnerId", odometerPartner);
                }
             transactionVo.executeQuery();
             if(transactionVo.getEstimatedRowCount() >0){
                 while (transactionVo.hasNext()) {
                     PrtCardTransactionHeaderUrefIdUpdateOdometerRvoRowImpl currRow = (PrtCardTransactionHeaderUrefIdUpdateOdometerRvoRowImpl)transactionVo.next();
                 if(currRow != null && currRow.getUrefTransactionId() != null){
                     _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"uref transaction id of next record=======>" +
                                  currRow.getUrefTransactionId());
                 OperationBinding previousOdometerOpn =bindings.getOperationBinding("updatePreviousOdometer");
                 previousOdometerOpn.getParamsMap().put("cardNumber",odometerKsid);
                 previousOdometerOpn.getParamsMap().put("accountId",odometerAccount);
                 previousOdometerOpn.getParamsMap().put("countryCd",palsCountryCode);
                 previousOdometerOpn.getParamsMap().put("partnerId",odometerPartner);
                 previousOdometerOpn.getParamsMap().put("transactionId",currRow.getUrefTransactionId());
                 previousOdometerOpn.getParamsMap().put("previousOdometer",getBindings().getOdometerPortalValue().getValue());
                 previousOdometerOpn.execute();
                 }
                 }
             }
            getBindings().getEditOdometerPopup().hide();
            searchResults();
        }
        return null;
    }

    public String editDriverCancel() {
        ResetUtils.reset(getBindings().getEditOdometerPopup());
        getBindings().getEditOdometerPopup().hide();
        return null;

    }

    public void setVehicleNumberOdometer(String vehicleNumberOdometer) {
        this.vehicleNumberOdometer = vehicleNumberOdometer;
    }

    public String getVehicleNumberOdometer() {
        return vehicleNumberOdometer;
    }

    public void setOdometerPortal(String odometerPortal) {
        this.odometerPortal = odometerPortal;
    }

    public String getOdometerPortal() {
        return odometerPortal;
    }

    public void setUrefTransactionId(String urefTransactionId) {
        this.urefTransactionId = urefTransactionId;
    }

    public String getUrefTransactionId() {
        return urefTransactionId;
    }

    public void setPalsCountryCode(String palsCountryCode) {
        this.palsCountryCode = palsCountryCode;
    }

    public String getPalsCountryCode() {
        return palsCountryCode;
    }

    public void odometerLink_Action(ActionEvent event) {
        //        FacesContext context = FacesContext.getCurrentInstance();
        //           UIComponent source = (UIComponent)event.getSource();
        //           String alignId = source.getClientId(context);
        //           RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //              hints.add(RichPopup.PopupHints.HintTypes.HINT_ALIGN_ID,source)
        //              .add(RichPopup.PopupHints.HintTypes.HINT_LAUNCH_ID,source)
        //              .add(RichPopup.PopupHints.HintTypes.HINT_ALIGN,
        //              RichPopup.PopupHints.AlignTypes.ALIGN_AFTER_END);

        getBindings().getOdometer_PopUp().show(new RichPopup.PopupHints());

    }

    public static Object invokeEL(String el, Class[] paramTypes,
                                  Object[] params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory =
            facesContext.getApplication().getExpressionFactory();
        MethodExpression exp =
            expressionFactory.createMethodExpression(elContext, el,
                                                     Object.class, paramTypes);

        return exp.invoke(elContext, params);
    }

    public void queryListener(QueryEvent queryEvent) {
        // Add event code here...
        invokeEL("#{bindings.PrtCardTransactionOverviewRVO12Query.processQuery}",
                 new Class[] { QueryEvent.class },
                 new Object[] { queryEvent });
        ViewObject prtCardTransactionOverViewRVO =
            ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");
        RowSetIterator iterator =
            prtCardTransactionOverViewRVO.createRowSetIterator(null);        
        iterator.reset();
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                     "Estimated Row Count =" +
                     prtCardTransactionOverViewRVO.getEstimatedRowCount());
        sum = 0.00f;
        foreignGrossAmountSum = 0.00f;
        vatSum = 0.00f;
        netAmountSum = 0.00f; 
        if(prtCardTransactionOverViewRVO.getEstimatedRowCount()>0)
        {
            isTableVisible=true;
        while (iterator.hasNext()) {
            PrtCardTransactionOverviewRVORowImpl row =
                (PrtCardTransactionOverviewRVORowImpl)iterator.next(); 
            if(row.getInvoicedGrossAmountRebated()!=null)
            {
            Float tempTotal =
                row.getInvoicedGrossAmountRebated().floatValue();
            sum = sum + tempTotal;
            }
            if(row.getCurrencyGrossAmount()!=null)
            {
            Float tempForeignTotal =
                row.getCurrencyGrossAmount().floatValue();
            foreignGrossAmountSum = foreignGrossAmountSum + tempForeignTotal;
            }
            if(row.getInvoivedVatRebated()!=null)
            {
            Float tempVatTotal =
                row.getInvoivedVatRebated().floatValue();
            vatSum = vatSum + tempVatTotal;
            }
            if(row.getInvoicedNetAmountRebated()!=null)
            {
            Float tempNetTotal =
                row.getInvoicedNetAmountRebated().floatValue();
            netAmountSum = netAmountSum + tempNetTotal;
            }
        }     
        }else {
            if(filterValue)
            {
            isTableVisible=true;   
            }else {
                isTableVisible=false;   
            }
        }
        
        if(!isTableVisible && value && !filterValue) {
            if (resourceBundle.containsKey("NO_RECORDS_FOUND_TRANSACTIONS")) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                     (String)resourceBundle.getObject("NO_RECORDS_FOUND_TRANSACTIONS"),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowSearchResultPG()); 
        }    
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"sum ="+sum);
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"foreignGrossAmountSum ="+foreignGrossAmountSum);
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"vatSum ="+vatSum);
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"netAmountSum ="+netAmountSum);
        value=false;  
        filterValue=false;
                                                                                                        
    }

    public void selectionExport(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
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
            fileName = "Transaction_Report.xls";
        } else if ("csv".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            fileName = "Transaction_Report.csv";
        } else {
            if ("csv2".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
                fileName = "Transaction_Report.csv";
            }
        }
        return fileName;
    }

    public void setPartnerIdList(ArrayList<SelectItem> partnerIdList) {
        this.partnerIdList = partnerIdList;
    }

    public ArrayList<SelectItem> getPartnerIdList() {
        return partnerIdList;
    }

    public void setPartnerIdValue(List<String> partnerIdValue) {
        this.partnerIdValue = partnerIdValue;
    }

    public List<String> getPartnerIdValue() {
        return partnerIdValue;
    }

    public ArrayList<SelectItem> getReportFormatList() {
        if (reportFormatList == null) {
            reportFormatList = new ArrayList<SelectItem>();
            SelectItem selectItem = new SelectItem();
            if (resourceBundle.containsKey("DEFAULT")){
                selectItem.setLabel(resourceBundle.getObject("DEFAULT").toString());
                selectItem.setValue("Default");
                reportFormatList.add(selectItem);
            }
            SelectItem selectItem1 = new SelectItem();
            if (resourceBundle.containsKey("RAW_DATA")){
                selectItem1.setLabel(resourceBundle.getObject("RAW_DATA").toString());
                selectItem1.setValue("Raw Data");
                reportFormatList.add(selectItem1);
            }
            SelectItem selectItem2 = new SelectItem();
            if (resourceBundle.containsKey("INTERNATIONAL")){
                selectItem2.setLabel(resourceBundle.getObject("INTERNATIONAL").toString());
                selectItem2.setValue("International");
                reportFormatList.add(selectItem2);
            }
            SelectItem selectItem3 = new SelectItem();
            if (resourceBundle.containsKey("PRICE_SPECIFICATION")){
                selectItem3.setLabel(resourceBundle.getObject("PRICE_SPECIFICATION").toString());
                selectItem3.setValue("Price Specification");
                reportFormatList.add(selectItem3);
            }
        }
        return reportFormatList;
    }

    public void setReportFormatValue(String reportFormatValue) {
        this.reportFormatValue = reportFormatValue;
    }

    public String getReportFormatValue() {
        return reportFormatValue;
    }

    public void setReportDefault(boolean reportDefault) {
        this.reportDefault = reportDefault;
    }

    public boolean isReportDefault() {
        return reportDefault;
    }

    public void setReportRawData(boolean reportRawData) {
        this.reportRawData = reportRawData;
    }

    public boolean isReportRawData() {
        return reportRawData;
    }

    public void setReportInternationalTx(boolean reportInternationalTx) {
        this.reportInternationalTx = reportInternationalTx;
    }

    public boolean isReportInternationalTx() {
        return reportInternationalTx;
    }

    public void setReportPriceSpecification(boolean reportPriceSpecification) {
        this.reportPriceSpecification = reportPriceSpecification;
    }

    public boolean isReportPriceSpecification() {
        return reportPriceSpecification;
    }

    public void setNoteVisible(boolean noteVisible) {
        this.noteVisible = noteVisible;
    }

    public boolean isNoteVisible() {
        return noteVisible;
    }

    public void setOdometerPartner(String odometerPartner) {
        this.odometerPartner = odometerPartner;
    }

    public String getOdometerPartner() {
        return odometerPartner;
    }

    public void setOdometerAccount(String odometerAccount) {
        this.odometerAccount = odometerAccount;
    }

    public String getOdometerAccount() {
        return odometerAccount;
    }

    public void setOdometerKsid(String odometerKsid) {
        this.odometerKsid = odometerKsid;
    }

    public String getOdometerKsid() {
        return odometerKsid;
    }

    public void setNoteInternational(boolean noteInternational) {
        this.noteInternational = noteInternational;
    }

    public boolean isNoteInternational() {
        return noteInternational;
    }

    public void setNoteInternationalVal(String noteInternationalVal) {
        this.noteInternationalVal = noteInternationalVal;
    }

    public String getNoteInternationalVal() {
        return noteInternationalVal;
    }

    public void setForeignGrossAmountSum(Float foreignGrossAmountSum) {
        this.foreignGrossAmountSum = foreignGrossAmountSum;
    }

    public Float getForeignGrossAmountSum() {
        return foreignGrossAmountSum;
    }

    public void setVatSum(Float vatSum) {
        this.vatSum = vatSum;
    }

    public Float getVatSum() {
        return vatSum;
    }

    public void setNetAmountSum(Float netAmountSum) {
        this.netAmountSum = netAmountSum;
    }

    public Float getNetAmountSum() {
        return netAmountSum;
    }

    public void setVehicleName(boolean vehicleName) {
        this.vehicleName = vehicleName;
    }

    public boolean isVehicleName() {
        return vehicleName;
    }


    public class Bindings {
        private RichSelectManyChoice account;
        private RichSelectManyChoice partner;
        private RichSelectOneChoice reportFormat;
        private RichInputDate fromDate;
        private RichInputDate toDate;
        private RichSelectManyChoice card;
        private RichSelectManyChoice cardGroup;
        private RichSelectManyChoice driverName;
        private RichSelectManyChoice vehicleNumber;
        private RichSelectManyChoice terminalType;
        private RichSelectManyChoice transationType;
        private RichPanelGroupLayout vhNumberPGL;
        private RichPanelGroupLayout driverNamePGL;
        private RichPanelGroupLayout cardGroupPGL;
        private RichPanelGroupLayout cardNoPGL;
        private RichSelectOneRadio cardCardGrpDrVhOneRadio;
        private RichSelectOneRadio selectionExportOneRadio;
        private RichPanelGroupLayout showSearchResultPG;
        private RichPopup specificColumns;
        private RichPopup confirmationExcel;
        private RichSelectManyShuttle shuttleExcel;
        private RichPanelGroupLayout selectedPGL;
        private RichPopup editOdometerPopup;
        private RichInputText odometerPortalValue;
        private RichPopup odometer_PopUp;
        private RichTable searchTable;
        private RichOutputText noteText;
        private RichOutputText noteInternationalText;
        private RichTable searchResultsTB;

        public void setOdometer_PopUp(RichPopup odometer_PopUp) {
            this.odometer_PopUp = odometer_PopUp;
        }

        public RichPopup getOdometer_PopUp() {
            return odometer_PopUp;
        }


        public void setAccount(RichSelectManyChoice account) {
            this.account = account;
        }

        public RichSelectManyChoice getAccount() {
            return account;
        }

        public void setFromDate(RichInputDate fromDate) {
            if(fromDateInitial)
            {
            Date dateNow = new java.util.Date();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(dateNow);
            gc.add(GregorianCalendar.MONTH, -1);
            Date dateBefore = gc.getTime();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
            String tmp = dateformat.format(dateBefore);
            fromDate.setValue(tmp);
                fromDateInitial=false;
            }
            this.fromDate = fromDate;
        }

        public RichInputDate getFromDate() {
            return fromDate;
        }

        public void setToDate(RichInputDate toDate) {
            if(toDateInitial)
            {
            Date dateNow = new java.util.Date();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
            String tmp = dateformat.format(dateNow);
            toDate.setValue(tmp);
                toDateInitial=false;
            }
            this.toDate = toDate;
        }

        public RichInputDate getToDate() {
            return toDate;
        }

        public void setCard(RichSelectManyChoice card) {
            this.card = card;
        }

        public RichSelectManyChoice getCard() {
            return card;
        }

        public void setCardGroup(RichSelectManyChoice cardGroup) {
            this.cardGroup = cardGroup;
        }

        public RichSelectManyChoice getCardGroup() {
            return cardGroup;
        }

        public void setDriverName(RichSelectManyChoice driverName) {
            this.driverName = driverName;
        }

        public RichSelectManyChoice getDriverName() {
            return driverName;
        }

        public void setVehicleNumber(RichSelectManyChoice vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
        }

        public RichSelectManyChoice getVehicleNumber() {
            return vehicleNumber;
        }

        public void setTerminalType(RichSelectManyChoice terminalType) {
            this.terminalType = terminalType;
        }

        public RichSelectManyChoice getTerminalType() {
            return terminalType;
        }

        public void setTransationType(RichSelectManyChoice transationType) {
            this.transationType = transationType;
        }

        public RichSelectManyChoice getTransationType() {
            return transationType;
        }

        public void setVhNumberPGL(RichPanelGroupLayout vhNumberPGL) {
            this.vhNumberPGL = vhNumberPGL;
        }

        public RichPanelGroupLayout getVhNumberPGL() {
            return vhNumberPGL;
        }

        public void setDriverNamePGL(RichPanelGroupLayout driverNamePGL) {
            this.driverNamePGL = driverNamePGL;
        }

        public RichPanelGroupLayout getDriverNamePGL() {
            return driverNamePGL;
        }

        public void setCardGroupPGL(RichPanelGroupLayout cardGroupPGL) {
            this.cardGroupPGL = cardGroupPGL;
        }

        public RichPanelGroupLayout getCardGroupPGL() {
            return cardGroupPGL;
        }

        public void setCardNoPGL(RichPanelGroupLayout cardNoPGL) {
            this.cardNoPGL = cardNoPGL;
        }

        public RichPanelGroupLayout getCardNoPGL() {
            return cardNoPGL;
        }

        public void setCardCardGrpDrVhOneRadio(RichSelectOneRadio cardCardGrpDrVhOneRadio) {
            this.cardCardGrpDrVhOneRadio = cardCardGrpDrVhOneRadio;
        }

        public RichSelectOneRadio getCardCardGrpDrVhOneRadio() {
            return cardCardGrpDrVhOneRadio;
        }

        public void setShowSearchResultPG(RichPanelGroupLayout showSearchResultPG) {
            this.showSearchResultPG = showSearchResultPG;
        }

        public RichPanelGroupLayout getShowSearchResultPG() {
            return showSearchResultPG;
        }

        public void setSpecificColumns(RichPopup specificColumns) {
            this.specificColumns = specificColumns;
        }

        public RichPopup getSpecificColumns() {
            return specificColumns;
        }

        public void setShuttleExcel(RichSelectManyShuttle shuttleExcel) {
            this.shuttleExcel = shuttleExcel;
        }

        public RichSelectManyShuttle getShuttleExcel() {
            return shuttleExcel;
        }

        public void setConfirmationExcel(RichPopup confirmationExcel) {
            this.confirmationExcel = confirmationExcel;
        }

        public RichPopup getConfirmationExcel() {
            return confirmationExcel;
        }

        public void setSelectedPGL(RichPanelGroupLayout selectedPGL) {
            this.selectedPGL = selectedPGL;
        }

        public RichPanelGroupLayout getSelectedPGL() {
            return selectedPGL;
        }

        public void setEditOdometerPopup(RichPopup editOdometerPopup) {
            this.editOdometerPopup = editOdometerPopup;
        }

        public RichPopup getEditOdometerPopup() {
            return editOdometerPopup;
        }

        public void setOdometerPortalValue(RichInputText odometerPortalValue) {
            this.odometerPortalValue = odometerPortalValue;
        }

        public RichInputText getOdometerPortalValue() {
            return odometerPortalValue;
        }

        public void setSearchTable(RichTable searchTable) {
            this.searchTable = searchTable;
        }

        public RichTable getSearchTable() {
            return searchTable;
        }

        public void setSelectionExportOneRadio(RichSelectOneRadio selectionExportOneRadio) {
            this.selectionExportOneRadio = selectionExportOneRadio;
        }

        public RichSelectOneRadio getSelectionExportOneRadio() {
            return selectionExportOneRadio;
        }

        public void setPartner(RichSelectManyChoice partner) {
            this.partner = partner;
        }

        public RichSelectManyChoice getPartner() {
            return partner;
        }

        public void setReportFormat(RichSelectOneChoice reportFormat) {
            this.reportFormat = reportFormat;
        }

        public RichSelectOneChoice getReportFormat() {
            return reportFormat;
        }

        public void setNoteText(RichOutputText noteText) {
            this.noteText = noteText;
        }

        public RichOutputText getNoteText() {
            return noteText;
        }

        public void setSearchResultsTB(RichTable searchResultsTB) {
            this.searchResultsTB = searchResultsTB;
        }

        public RichTable getSearchResultsTB() {
            return searchResultsTB;
        }

        public void setNoteInternationalText(RichOutputText noteInternationalText) {
            this.noteInternationalText = noteInternationalText;
        }

        public RichOutputText getNoteInternationalText() {
            return noteInternationalText;
        }
    }
}

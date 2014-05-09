package com.sfr.engage.invoiceoverviewtaskflow;

import com.sfr.engage.authenticatedhometaskflow.AuthenticatedHomeBean;

import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.model.queries.rvo.PrtCardDriverVehicleInfoRVORowImpl;
import com.sfr.engage.model.queries.rvo.PrtCustomerCardMapRVO1RowImpl;
import com.sfr.engage.model.queries.uvo.PrtAccountVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtInvoiceVORowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.engage.services.client.ucm.UCMCustomWeb;
import com.sfr.engage.services.client.ucm.type.Property;
import com.sfr.engage.services.client.ucm.type.SearchInputVO;
import com.sfr.engage.services.client.ucm.type.SearchResultVO;
import com.sfr.engage.services.core.dao.factory.DAOFactory;
import com.sfr.util.ADFUtils;

import com.sfr.util.AccessDataControl;
import com.sfr.util.ConfigurationUtility;
import com.sfr.util.constants.Constants;
import com.sfr.util.validations.Conversion;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

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

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.SelectionEvent;

public class InvoiceOverviewBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    private String accountValue;
    private List<String> cardGroupValue;
    private String invoiceTypeValue;    
    private List<String> cardValue;    
    private ArrayList<SelectItem> accountList = null;
    private ArrayList<SelectItem> invoiceTypeList = null;
    private ArrayList<SelectItem> cardGroupList = null;
    private ArrayList<SelectItem> cardList = null;
    private boolean searchResults=false;
    private boolean cardGroupVisible=false;
    private boolean cardVisible=false;
    private boolean cGCardVisible=false;
    private String countryCode;
    private ResourceBundle resourceBundle;
    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    private PartnerInfo partnerInfo;
    private String cardGroupSubtypePassValues = "";
    private String cardGroupMaintypePassValue = "";
    private String cardGroupSeqPassValues     = "";
    private Conversion conversionUtility;
    private Locale locale;
    private String partnerId;
    private String currencyCode;
    private String lang;
    private String invoiceNumberPdfValue;
    Map<String,String> ucmInvoiceContentList = new HashMap<String,String>();
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();
    
    public InvoiceOverviewBean() {
        super();
        conversionUtility = new Conversion(); 
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);        
        resourceBundle = new EngageResourceBundle();
        accountList=new ArrayList<SelectItem>();
        if(session.getAttribute("Partner_Object_List") != null){
            partnerInfo = (PartnerInfo)session.getAttribute("Partner_Object_List");
        }
        if(partnerInfo != null){
            if(partnerInfo.getPartnerValue() != null){   
                partnerId = partnerInfo.getPartnerValue().toString(); 
            }
         
            if( partnerInfo.getAccountList() != null && partnerInfo.getAccountList().size() > 0){
                log.info(accessDC.getDisplayRecord() + this.getClass() + " " + "List of Account in partner info object=====>"+partnerInfo.getAccountList().size());
                for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
                    log.info(accessDC.getDisplayRecord() + this.getClass() + " " +"value of Account Id===========>"+partnerInfo.getAccountList().get(i).getAccountNumber().toString());
                    SelectItem selectItem = new SelectItem();
                    selectItem.setLabel(partnerInfo.getAccountList().get(i).getAccountNumber().toString());
                    selectItem.setValue(partnerInfo.getAccountList().get(i).getAccountNumber().toString());
                    accountList.add(selectItem);
                }
            }
        }
        //lang=(String)session.getAttribute(Constants.SESSION_LANGUAGE);
        
        lang="NO";
        
        if(lang=="NO")
        {
        currencyCode=conversionUtility.getCurrencyCode("NO");
        locale=conversionUtility.getLocaleFromCountryCode("NO");
        }else if(lang=="SE") {
            currencyCode=conversionUtility.getCurrencyCode("SE");
        }
    }
    
    public ArrayList<SelectItem> getAccountList() {
//        if (accountList == null) {
//            accountList = new ArrayList<SelectItem>();            
//                SelectItem selectItem = new SelectItem();
//                selectItem.setLabel("100");
//                selectItem.setValue("100");
//                accountList.add(selectItem);
//            SelectItem selectItem1 = new SelectItem();
//            selectItem1.setLabel("200");
//            selectItem1.setValue("200");
//            accountList.add(selectItem1);
        return accountList;
    }
    
    public ArrayList<SelectItem> getCardGroupList() {
    //        if (accountList == null) {
    //            accountList = new ArrayList<SelectItem>();
    //                SelectItem selectItem = new SelectItem();
    //                selectItem.setLabel("100");
    //                selectItem.setValue("100");
    //                accountList.add(selectItem);
    //            SelectItem selectItem1 = new SelectItem();
    //            selectItem1.setLabel("200");
    //            selectItem1.setValue("200");
    //            accountList.add(selectItem1);
        return cardGroupList;
    }
    
    public ArrayList<SelectItem> getCardList() {
    //        if (accountList == null) {
    //            accountList = new ArrayList<SelectItem>();
    //                SelectItem selectItem = new SelectItem();
    //                selectItem.setLabel("100");
    //                selectItem.setValue("100");
    //                accountList.add(selectItem);
    //            SelectItem selectItem1 = new SelectItem();
    //            selectItem1.setLabel("200");
    //            selectItem1.setValue("200");
    //            accountList.add(selectItem1);
        return cardList;
    }
    
    public ArrayList<SelectItem> getInvoiceTypeList() {
        if (invoiceTypeList == null) {
//            ViewObject vo = ADFUtils.getViewObject("PrtAccountRVO1Iterator");
//            vo.setNamedWhereClauseParam("countryCode", "en_US");
//            vo.executeQuery();
            invoiceTypeList = new ArrayList<SelectItem>();            
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel("Card");
                selectItem.setValue("Card");
                invoiceTypeList.add(selectItem);
            SelectItem selectItem1 = new SelectItem();
            selectItem1.setLabel("Bulk");
            selectItem1.setValue("Bulk");
            invoiceTypeList.add(selectItem1);  
//            while (vo.hasNext()) {
//                PrtAccountRVORowImpl currRow = (PrtAccountRVORowImpl)vo.next();
//                if (currRow.getAccountId() != null) {
//                    SelectItem selectItem = new SelectItem();
//                    selectItem.setLabel(currRow.getAccountId());
//                    selectItem.setValue(currRow.getAccountId());
//                    accountList.add(selectItem);
//                }
//            }
        }
        return invoiceTypeList;
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

    public void setAccountValue(String accountValue) {
        this.accountValue = accountValue;
    }

    public String getAccountValue() {
        return accountValue;
    }

    public void setInvoiceTypeValue(String invoiceTypeValue) {
        this.invoiceTypeValue = invoiceTypeValue;
    }

    public String getInvoiceTypeValue() {
        return invoiceTypeValue;
    }
    
    public void populateCardGroupValues(String cardGrpVar){
        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "PassedcardGrpVar ="+cardGrpVar);
           String[] cardGroupvalues;
           int cardGroupCount = 0;         
           
           String cardGroupMaintype = "";
           String cardGroupSubtype = "";
           String cardGroupSeq = "";           
          
           
           if(cardGrpVar != null ){ 
               if(cardGrpVar.contains(",")){
                   cardGroupvalues = cardGrpVar.split(",");
                   cardGroupCount  = cardGroupvalues.length;
               }else{
                   cardGroupCount  = 1;
                   cardGroupvalues = new String[1];
                   cardGroupvalues[0] = cardGrpVar;
               }
               
               for(int cGrp =0; cGrp < cardGroupCount; cGrp++){
                   cardGroupMaintype=cardGroupMaintype+cardGroupvalues[cGrp].trim().substring(0,3);  
                   cardGroupMaintype=cardGroupMaintype+",";
                   
                   cardGroupSubtype=cardGroupSubtype+cardGroupvalues[cGrp].trim().substring(3,6);  
                   cardGroupSubtype=cardGroupSubtype+",";
                   
                   cardGroupSeq=cardGroupSeq+cardGroupvalues[cGrp].trim().substring(6);  
                   cardGroupSeq=cardGroupSeq+",";  
               }
               
               log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "CardGroupMainType ="+cardGroupMaintype);
               log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "cardGroupSubtype ="+cardGroupSubtype);
               log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "cardGroupSeq ="+cardGroupSeq);                   
                  
                 cardGroupMaintypePassValue = cardGroupMaintype.trim().substring(0, cardGroupMaintype.length()-1);
                 cardGroupSubtypePassValues = cardGroupSubtype.trim().substring(0, cardGroupSubtype.length()-1);
                 cardGroupSeqPassValues     = cardGroupSeq.trim().substring(0, cardGroupSeq.length()-1);
           }
       }

    public void searchResultsListener(ActionEvent actionEvent) {
        // Add event code here...
        if(getBindings().getAccount().getValue()!=null && getBindings().getFromDate().getValue()!=null && getBindings().getToDate().getValue()!=null) {
            Date fromDate = (java.util.Date)getBindings().getFromDate().getValue();
            Date toDate = (java.util.Date)getBindings().getToDate().getValue();
            if (toDate.before(fromDate)) {
                if (resourceBundle.containsKey("INVOICE_TODATE_LESSTHAN")) {                    
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("INVOICE_TODATE_LESSTHAN"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null,
                                                                 msg);                                         
                }
          } 
            // else if (fromDate.after(toDate)) {
            //                if (resourceBundle.containsKey("REPORT_ERROR_MESSAGE_5")) {
            //                    reportErrormessage =
            //                            (String)resourceBundle.getObject("REPORT_ERROR_MESSAGE_5");
            //                    setErrorVisible(true);
            //                    setFormVisible(false);
            //                }            
            
            else {
                log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "AccountValue="+getBindings().getAccount().getValue());
                log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "FromDate ="+getBindings().getFromDate().getValue());
                ViewObject invoiceVO =
                    ADFUtils.getViewObject("PrtInvoiceVO1Iterator"); 
                if ("COUNTRY_CODE =:countryCode AND trim(ACCOUNT_ID) =:accountId AND INVOICE_DATE >=: fromDateBV AND INVOICE_DATE <=: toDateBV AND INVOICE_TYPE LIKE CONCAT(:invoiceType,'%')AND INSTR(:cardPK,PRT_CARD_PK)<>0".equalsIgnoreCase(invoiceVO.getWhereClause())) {
                    invoiceVO.removeNamedWhereClauseParam("countryCode");
                    invoiceVO.removeNamedWhereClauseParam("accountId");
                    invoiceVO.removeNamedWhereClauseParam("fromDateBV");
                    invoiceVO.removeNamedWhereClauseParam("toDateBV");
                    invoiceVO.removeNamedWhereClauseParam("invoiceType");
                    invoiceVO.removeNamedWhereClauseParam("cardPK");
                    invoiceVO.setWhereClause("");
                    invoiceVO.executeQuery();
                }else {
                    if ("COUNTRY_CODE =:countryCode AND trim(ACCOUNT_ID) =:accountId AND INVOICE_DATE >=: fromDateBV AND INVOICE_DATE <=: toDateBV AND INVOICE_TYPE LIKE CONCAT(:invoiceType,'%')AND INSTR(:cardGroupMainType,CARDGROUP_MAIN_TYPE)<>0 AND INSTR(:cardGroupSubType,CARDGROUP_SUB_TYPE)<>0 AND INSTR(:cardGroupSeqType,CARDGROUP_SEQ)<>0".equalsIgnoreCase(invoiceVO.getWhereClause())) {
                        invoiceVO.removeNamedWhereClauseParam("countryCode");
                        invoiceVO.removeNamedWhereClauseParam("accountId");
                        invoiceVO.removeNamedWhereClauseParam("fromDateBV");
                        invoiceVO.removeNamedWhereClauseParam("toDateBV");
                        invoiceVO.removeNamedWhereClauseParam("invoiceType");
                        invoiceVO.removeNamedWhereClauseParam("cardGroupMainType");
                        invoiceVO.removeNamedWhereClauseParam("cardGroupSubType");
                        invoiceVO.removeNamedWhereClauseParam("cardGroupSeqType");
                        invoiceVO.setWhereClause("");
                        invoiceVO.executeQuery();
                    }
                }
                invoiceVO.setWhereClause("COUNTRY_CODE =:countryCode AND trim(ACCOUNT_ID) =:accountId AND INVOICE_DATE >=: fromDateBV AND INVOICE_DATE <=: toDateBV AND INVOICE_TYPE LIKE CONCAT(:invoiceType,'%')");
               invoiceVO.defineNamedWhereClauseParam("accountId",getBindings().getAccount().getValue(),null);
                invoiceVO.defineNamedWhereClauseParam("countryCode",lang,null);
                invoiceVO.defineNamedWhereClauseParam("fromDateBV",formatConversion(fromDate).toString(),null);
                invoiceVO.defineNamedWhereClauseParam("toDateBV",formatConversion(toDate).toString(),null);
                if(getBindings().getInvoiceType().getValue()!=null) {
                    invoiceVO.defineNamedWhereClauseParam("invoiceType",getBindings().getInvoiceType().getValue(),null);
                }else {
                    invoiceVO.defineNamedWhereClauseParam("invoiceType",null,null);
                }
                String baseWhereClause=invoiceVO.getWhereClause();
                
                if(getBindings().getCardGpCardList().getValue()!=null) {
                    if("Card".equalsIgnoreCase(getBindings().getCardGpCardList().getValue().toString())) {
                        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Inside card");             
                         invoiceVO.setWhereClause(baseWhereClause+"AND INSTR(:cardPK,PRT_CARD_PK)<>0");
                         String cardValuesList=populateStringValues(getBindings().getCard().getValue().toString());
                          invoiceVO.defineNamedWhereClauseParam("cardPK",cardValuesList,null);
                    }else {
                        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Inside cardgroup");                
                            invoiceVO.setWhereClause(baseWhereClause+"AND INSTR(:cardGroupMainType,CARDGROUP_MAIN_TYPE)<>0 AND INSTR(:cardGroupSubType,CARDGROUP_SUB_TYPE)<>0 AND INSTR(:cardGroupSeqType,CARDGROUP_SEQ)<>0");                    
                            populateCardGroupValues(populateStringValues(getBindings().getCardGroup().getValue().toString()));                            
                        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "card group main type======>"+cardGroupMaintypePassValue);
                        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "card group sub type===>"+cardGroupSubtypePassValues);
                        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "card group sequence value====>"+cardGroupSeqPassValues);
                            invoiceVO.defineNamedWhereClauseParam("cardGroupMainType",cardGroupMaintypePassValue,null);
                            invoiceVO.defineNamedWhereClauseParam("cardGroupSubType",cardGroupSubtypePassValues,null);
                            invoiceVO.defineNamedWhereClauseParam("cardGroupSeqType",cardGroupSeqPassValues,null);                            
                    }
                    
                } 
                log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Query Formed is="+invoiceVO.getQuery());
                invoiceVO.executeQuery();
                log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Estimated Row count=="+invoiceVO.getEstimatedRowCount());
                searchResults=true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
                log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Where condition:"+invoiceVO.getWhereClause());              
                
            }
            
        }else {
            if (resourceBundle.containsKey("INVOICE_MANDATORY_CHECK")) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     (String)resourceBundle.getObject("INVOICE_MANDATORY_CHECK"),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null,
                                                             msg);
            }
        }
       
    }
    
    public String formatConversion(Date date) {
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MMM-yy");
        return sdf.format(date);
    }
    
    public String populateStringValues(String var){
        String passingValues = null;
        if(var != null){
            String lovValues = var.trim();
            String selectedValues = lovValues.substring(1, lovValues.length() - 1);
            passingValues = selectedValues.trim();
                
        }
        return passingValues;
    }

    public void clearSearchListener(ActionEvent actionEvent) {
        // Add event code here...
        getBindings().getAccount().setValue(null);
        this.accountValue=null;
        getBindings().getCardGpCardList().setValue(null);
        this.invoiceTypeValue=null;
        getBindings().getFromDate().setValue(null);
        getBindings().getToDate().setValue(null);
        searchResults=false;
        cGCardVisible=false;
        cardVisible=false;
        cardGroupVisible=false;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());        
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());        
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFromDate());  
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getToDate()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvoiceType()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
    }

    public void setCardValue(List<String> cardValue) {
        this.cardValue = cardValue;
    }

    public List<String> getCardValue() {
        return cardValue;
    }

    public void setCardGroupValue(List<String> cardGroupValue) {
        this.cardGroupValue = cardGroupValue;
    }

    public List<String> getCardGroupValue() {
        return cardGroupValue;
    }

    public String invoiceDetailsCancel() {
        ViewObject cardTransactionVO =
            ADFUtils.getViewObject("PrtCardTransactionInvoiceRVO1Iterator"); 
        if ("INVOICE_NUMBER_COLLECTIVE =:collecInvNo".equalsIgnoreCase(cardTransactionVO.getWhereClause())) {
            cardTransactionVO.removeNamedWhereClauseParam("collecInvNo");           
            cardTransactionVO.setWhereClause("");
            cardTransactionVO.executeQuery();
        }
        if ("INVOICE_NUMBER_NON_COLLECTIVE =:nonCollecInvNo".equalsIgnoreCase(cardTransactionVO.getWhereClause())) {
            cardTransactionVO.removeNamedWhereClauseParam("nonCollecInvNo");           
            cardTransactionVO.setWhereClause("");
            cardTransactionVO.executeQuery();
        }
        getBindings().getInvoiceDetails().hide();
        return null;
    }

    public String invoiceNumberAction() {
        ViewObject invoiceVO =
            ADFUtils.getViewObject("PrtInvoiceVO2Iterator");        
        String invoiceNumberValue =
            (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("invoiceNumberValue");
        String invoiceGroupingValue =null;            
        invoiceVO.setWhereClause("INVOICE_NUMBER =:invoiceNumber");
        invoiceVO.defineNamedWhereClauseParam("invoiceNumber",invoiceNumberValue,null);
        invoiceVO.executeQuery();
        if(invoiceVO.getEstimatedRowCount()==1) {
            while (invoiceVO.hasNext()) {
                PrtInvoiceVORowImpl currRow =
                    (PrtInvoiceVORowImpl)invoiceVO.next();
                if (currRow != null) {
                    invoiceGroupingValue = currRow.getInvoiceGrouping();
                }
            }
        }
        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "InvoiceNumber ="+invoiceNumberValue);
        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "InvoiceGroupingValue ="+invoiceGroupingValue);
        ViewObject cardTransactionVO =
            ADFUtils.getViewObject("PrtCardTransactionInvoiceRVO1Iterator");  
        if(invoiceGroupingValue!=null) {
            if(invoiceGroupingValue.equals("FAK")) {
                cardTransactionVO.setWhereClause("INVOICE_NUMBER_NON_COLLECTIVE =:nonCollecInvNo");
                cardTransactionVO.defineNamedWhereClauseParam("nonCollecInvNo",invoiceNumberValue,null);
            }else {
                if(invoiceGroupingValue.equals("SAM")) {
                    cardTransactionVO.setWhereClause("INVOICE_NUMBER_COLLECTIVE =:collecInvNo");
                    cardTransactionVO.defineNamedWhereClauseParam("collecInvNo",invoiceNumberValue,null);
                }
            }
            log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "cardTransaction Query="+cardTransactionVO.getQuery());
            cardTransactionVO.executeQuery();
            log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "cardTransactionVO estimatedRow:"+cardTransactionVO.getEstimatedRowCount());
        }
        
        getBindings().getInvoiceDetails().show(new RichPopup.PopupHints());
        return null;
    }

    public void cgValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(getBindings().getAccount().getValue()!=null)
        {
        if(valueChangeEvent.getNewValue()!=null) {
            log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Value ="+valueChangeEvent.getNewValue());
            if(valueChangeEvent.getNewValue().equals("CardGroup")) {
            populateValue(valueChangeEvent.getNewValue().toString());
                cGCardVisible=true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());        
            cardGroupVisible=true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());  
                cardVisible=false;
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());  
                
            }else{
                populateValue(valueChangeEvent.getNewValue().toString());
                cGCardVisible=true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());        
                cardVisible=true;
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());  
                cardGroupVisible=false;
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());  
                      
            }            
            
        }
        }else {
            if (resourceBundle.containsKey("INVOICE_MANDATORY_CHECK_1")) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     (String)resourceBundle.getObject("INVOICE_MANDATORY_CHECK_1"),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null,
                                                             msg);
            }
        }
    }
    
    public void populateValue(String paramType){
        if(paramType != null){
            if(paramType.equals("CardGroup")){
                cardGroupList  = new ArrayList<SelectItem>();
                cardGroupValue  = new ArrayList<String>();
                if(partnerInfo.getAccountList() != null && partnerInfo.getAccountList().size() > 0){
                    for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
                        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Account Number inside select one radio button==========>"+partnerInfo.getAccountList().get(i).getAccountNumber());
                        if(partnerInfo.getAccountList().get(i).getAccountNumber() != null && partnerInfo.getAccountList().get(i).getAccountNumber().equals(getBindings().getAccount().getValue())){
                            if(partnerInfo.getAccountList().get(i).getCardGroup() != null && partnerInfo.getAccountList().get(i).getCardGroup().size()>0){
                                for(int k =0 ; k< partnerInfo.getAccountList().get(i).getCardGroup().size(); k++){
                                    log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Card Group inside select one radio button==========>"+partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                    if(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID()!= null){
                                    SelectItem selectItem = new SelectItem();
                                    selectItem.setLabel(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                    selectItem.setValue(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                    cardGroupList.add(selectItem);
                                    cardGroupValue.add(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }else{
                if(paramType.equals("Card")){
                cardList  = new ArrayList<SelectItem>();
                cardValue  = new ArrayList<String>();
                if(partnerInfo.getAccountList() != null && partnerInfo.getAccountList().size() > 0){
                    for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
                        if(partnerInfo.getAccountList().get(i).getAccountNumber() != null && partnerInfo.getAccountList().get(i).getAccountNumber().equals(getBindings().getAccount().getValue())){
                            if(partnerInfo.getAccountList().get(i).getCardGroup() != null && partnerInfo.getAccountList().get(i).getCardGroup().size()>0){ 
                                for(int k =0 ; k< partnerInfo.getAccountList().get(i).getCardGroup().size(); k++){
                                    if(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard() != null && partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().size()>0){ 
                                    for(int m =0 ; m<partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().size(); m++){
                                            if(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID()!= null && partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getExternalCardID() != null){
                                                SelectItem selectItem = new SelectItem();
                                                selectItem.setLabel(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getExternalCardID().toString());
                                                selectItem.setValue(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID().toString());
                                                cardList.add(selectItem);
                                                cardValue.add(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID().toString());
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

    public void setSearchResults(boolean searchResults) {
        this.searchResults = searchResults;
    }

    public boolean isSearchResults() {
        return searchResults;
    }

   
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void accountValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue()!=null) {
            getBindings().getCardGpCardList().setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGpCardList());  
            
        }
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
    
    public static String getPropertyValue(String PName) {
        return ConfigurationUtility.getPropertyValue(PName);
    }

    public void getUCMService(FacesContext facesContext,OutputStream outputStream) throws IOException {  
        
        ViewObject invoiceVO =
            ADFUtils.getViewObject("PrtInvoiceVO1Iterator");     
        PrtInvoiceVORowImpl row=(PrtInvoiceVORowImpl)invoiceVO.getCurrentRow();
        String invoiceNumberValuePdf = row.getInvoiceNumber(); 
        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "invoice number"+invoiceNumberValuePdf);        
        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "PartnerId "+partnerId);
        byte[] responseByteArr = null;
        Boolean isError=false;        
        UCMCustomWeb uCMCustomWeb = null;
        
       if(session.getAttribute("ucmInvoiceContentList")!=null){    
       log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "session is available");
                    try {
                        ucmInvoiceContentList = (HashMap<String,String>)session.getAttribute("ucmInvoiceContentList");
                        String UCMInvoiceContentId = ucmInvoiceContentList.get(invoiceNumberValuePdf);
                        if (UCMInvoiceContentId != null && UCMInvoiceContentId.trim().length() > 0) {
                            log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "ContentId is available from session");
                            uCMCustomWeb = new DAOFactory().getUCMService();                            
                            responseByteArr = uCMCustomWeb.getFileFromUCM(DAOFactory.getPropertyValue(Constants.UCM_USERNAME), DAOFactory.getPropertyValue(Constants.UCM_PASSWORD),
                                                                UCMInvoiceContentId);
                            if (responseByteArr == null || responseByteArr.length == 0) {
                                isError = true;
                            } else
                            {
                                outputStream.write(responseByteArr);
                                
                            }
                        }  
                        else {
                            byte[] result=searchGetFile(invoiceNumberValuePdf);
                            if(result!=null && result.length!=0) {
                               outputStream.write(result);                            
                            }else
                            {
                            isError = true;
                            }
                            
                        }
                        
                        
                    } catch (Exception e) {
                        isError = true;
                        log.severe(accessDC.getDisplayRecord() + this.getClass()  + " " + ".fileDownload : " + "Exception");
                        e.printStackTrace();
                    }

        }
        else{
           log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "session is null");       
            byte[] result=searchGetFile(invoiceNumberValuePdf);
           if(result!=null && result.length!=0) {
               outputStream.write(result);             
           }else {
               isError = true;
           }
              
        }
        //retrieve error pdf in case of error
        if (isError) {                         
            uCMCustomWeb = new DAOFactory().getUCMService();              
                log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Error PDF ="+DAOFactory.getPropertyValue("ERROR_PDF_CID"));
                responseByteArr = uCMCustomWeb.getFileFromUCM(DAOFactory.getPropertyValue(Constants.ENGAGE_UCM_USERNAME), DAOFactory.getPropertyValue(Constants.ENGAGE_UCM_PASSWORD),                                                                
                                                                               DAOFactory.getPropertyValue("ERROR_PDF_CID"));
                                              outputStream.write(responseByteArr);
             log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Error while downloading PDF");

            }
    }
    
    public byte[] searchGetFile(String invoiceNumber) {
        
        byte[] responseByteArr = null;
        Boolean isError=false;
        String ucmContentId;
        UCMCustomWeb uCMCustomWeb = null;
        
        SearchInputVO searchInputVO = new SearchInputVO();
        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "UserName ="+getPropertyValue(Constants.ENGAGE_UCM_USERNAME));
        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Password ="+getPropertyValue(Constants.ENGAGE_UCM_PASSWORD));
        searchInputVO.setUsername(getPropertyValue(Constants.ENGAGE_UCM_USERNAME));
        searchInputVO.setPassword(getPropertyValue(Constants.ENGAGE_UCM_PASSWORD));
        searchInputVO.setSourceSystem("WebPortal");
        
        Property invoiceNo = new Property();
        invoiceNo.setName("xInvoiceNo");
        invoiceNo.setValue("29091991");
        
        Property partnerId = new Property();
        partnerId.setName("xPartnerId");
        partnerId.setValue("RON04031991");
        
        Property docType = new Property();
        docType.setName("xDocumentType");
        docType.setValue("PDF");
        
        Property contentType = new Property(); 
        contentType.setName("xContentType");
        contentType.setValue("O2C");
        
        Property subType = new Property(); 
        subType.setName("xSubType");
        subType.setValue("Self_Billing_Print_Reports");
        
        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "ENGAGE_UCM_WSDL_URL-------------"+DAOFactory.getPropertyValue(Constants.ENGAGE_UCM_WSDL_URL));
        
        searchInputVO.getSearchInputQueryProperty().add(invoiceNo);
        searchInputVO.getSearchInputQueryProperty().add(partnerId);
        searchInputVO.getSearchInputQueryProperty().add(docType);
        searchInputVO.getSearchInputQueryProperty().add(contentType);
        searchInputVO.getSearchInputQueryProperty().add(subType);
        
        searchInputVO.getSearchResultMetadata().add("dDocTitle");
        searchInputVO.getSearchResultMetadata().add("dDocName");
        
                try {
                    uCMCustomWeb = new DAOFactory().getUCMService();
                    if (uCMCustomWeb != null) {
                        List<SearchResultVO> UCMInvoiceContentIdList = uCMCustomWeb.searchDocument(searchInputVO); 
                        ucmContentId = UCMInvoiceContentIdList.get(0).getSearchResultMetadata().get(1).getValue();
                        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Content id="+ucmContentId);                        
                        if (ucmContentId != null && ucmContentId.trim().length() > 0) {
                        ucmInvoiceContentList.put(invoiceNumber,ucmContentId);
                        session.setAttribute("ucmInvoiceContentList", ucmInvoiceContentList);
                        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "get file from ucm");
                            responseByteArr = uCMCustomWeb.getFileFromUCM(DAOFactory.getPropertyValue(Constants.ENGAGE_UCM_USERNAME), DAOFactory.getPropertyValue(Constants.ENGAGE_UCM_PASSWORD),
                                                                ucmContentId); 
                        }                       
                    }
                } catch (Exception e) {                        
                    log.severe(accessDC.getDisplayRecord() + this.getClass()  + " " + ".fileDownload : " + "Exception");
                    e.printStackTrace();
                }
                return responseByteArr;
        
    }

    public void setInvoiceNumberPdfValue(String invoiceNumberPdfValue) {
        this.invoiceNumberPdfValue = invoiceNumberPdfValue;
    }

    public String getInvoiceNumberPdfValue() {
        return invoiceNumberPdfValue;
    }

    public void setCardGroupVisible(boolean cardGroupVisible) {
        this.cardGroupVisible = cardGroupVisible;
    }

    public boolean isCardGroupVisible() {
        return cardGroupVisible;
    }

    public void setCardVisible(boolean cardVisible) {
        this.cardVisible = cardVisible;
    }

    public boolean isCardVisible() {
        return cardVisible;
    }

    public void setCGCardVisible(boolean cGCardVisible) {
        this.cGCardVisible = cGCardVisible;
    }

    public boolean isCGCardVisible() {
        return cGCardVisible;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }


    public class Bindings {
        private RichSelectOneChoice account;
        private RichSelectOneChoice invoiceType;
        private RichSelectManyChoice cardGroup;
        private RichSelectManyChoice card;
        private RichInputDate fromDate;
        private RichInputDate toDate;
        private RichSelectOneRadio cardGpCardList;
        private RichPopup invoiceDetails;
        private RichPanelGroupLayout searchResults;
        private RichPanelGroupLayout cardGroupPGL;
        private RichPanelGroupLayout cardPGL;    
        private RichTable invoiceResults;
        

        public void setAccount(RichSelectOneChoice account) {
            this.account = account;
        }

        public RichSelectOneChoice getAccount() {
            return account;
        }

        public void setInvoiceType(RichSelectOneChoice invoiceType) {
            this.invoiceType = invoiceType;
        }

        public RichSelectOneChoice getInvoiceType() {
            return invoiceType;
        }

        public void setFromDate(RichInputDate fromDate) {
            this.fromDate = fromDate;
        }

        public RichInputDate getFromDate() {
            return fromDate;
        }

        public void setToDate(RichInputDate toDate) {
            this.toDate = toDate;
        }

        public RichInputDate getToDate() {
            return toDate;
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

        public void setSearchResults(RichPanelGroupLayout searchResults) {
            this.searchResults = searchResults;
        }

        public RichPanelGroupLayout getSearchResults() {
            return searchResults;
        }

        public void setInvoiceDetails(RichPopup invoiceDetails) {
            this.invoiceDetails = invoiceDetails;
        }

        public RichPopup getInvoiceDetails() {
            return invoiceDetails;
        }

        public void setCardGroupPGL(RichPanelGroupLayout cardGroupPGL) {
            this.cardGroupPGL = cardGroupPGL;
        }

        public RichPanelGroupLayout getCardGroupPGL() {
            return cardGroupPGL;
        }

        public void setCardPGL(RichPanelGroupLayout cardPGL) {
            this.cardPGL = cardPGL;
        }

        public RichPanelGroupLayout getCardPGL() {
            return cardPGL;
        }

        public void setCardGpCardList(RichSelectOneRadio cardGpCardList) {
            this.cardGpCardList = cardGpCardList;
        }

        public RichSelectOneRadio getCardGpCardList() {
            return cardGpCardList;
        }

        public void setInvoiceResults(RichTable invoiceResults) {
            this.invoiceResults = invoiceResults;
        }

        public RichTable getInvoiceResults() {
            return invoiceResults;
        }
    }
}

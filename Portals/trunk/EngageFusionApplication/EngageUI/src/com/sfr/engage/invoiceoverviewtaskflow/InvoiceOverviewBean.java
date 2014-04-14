package com.sfr.engage.invoiceoverviewtaskflow;

import com.sfr.engage.authenticatedhometaskflow.AuthenticatedHomeBean;

import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.model.queries.rvo.PrtCardDriverVehicleInfoRVORowImpl;
import com.sfr.engage.model.queries.rvo.PrtCustomerCardMapRVO1RowImpl;
import com.sfr.engage.model.queries.uvo.PrtAccountVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtInvoiceVORowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.ADFUtils;

import java.io.Serializable;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;
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

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.ViewObject;

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
    private boolean cardGroup=false;
    private boolean card=false;
    private String countryCode;
    private ResourceBundle resourceBundle;
    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    private PartnerInfo partnerInfo;
    private String cardGroupSubtypePassValues = "";
    private String cardGroupMaintypePassValue = "";
    private String cardGroupSeqPassValues     = "";
    
    public InvoiceOverviewBean() {
        super();
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
                String partnerId = partnerInfo.getPartnerValue().toString(); 
            }
         
            if( partnerInfo.getAccountList() != null && partnerInfo.getAccountList().size() > 0){
                System.out.println("List of Account in partner info object=====>"+partnerInfo.getAccountList().size());
                for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
                    System.out.println("value of Account Id===========>"+partnerInfo.getAccountList().get(i).getAccountNumber().toString());
                    SelectItem selectItem = new SelectItem();
                    selectItem.setLabel(partnerInfo.getAccountList().get(i).getAccountNumber().toString());
                    selectItem.setValue(partnerInfo.getAccountList().get(i).getAccountNumber().toString());
                    accountList.add(selectItem);
                }
            }
        }
        countryCode="no_NO";
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
        System.out.println("PassedcardGrpVar ="+cardGrpVar);
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
               
               System.out.println("CardGroupMainType ="+cardGroupMaintype);
               System.out.println("cardGroupSubtype ="+cardGroupSubtype);
               System.out.println("cardGroupSeq ="+cardGroupSeq);                   
                  
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
                System.out.println("AccountValue="+getBindings().getAccount().getValue());
                System.out.println("FromDate ="+getBindings().getFromDate().getValue());
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
                invoiceVO.defineNamedWhereClauseParam("countryCode",countryCode,null);
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
                        System.out.println("Inside card");             
                         invoiceVO.setWhereClause(baseWhereClause+"AND INSTR(:cardPK,PRT_CARD_PK)<>0");
                         String cardValuesList=populateStringValues(getBindings().getCard().getValue().toString());
                          invoiceVO.defineNamedWhereClauseParam("cardPK",cardValuesList,null);
                    }else {
                        System.out.println("Inside cardgroup");                
                            invoiceVO.setWhereClause(baseWhereClause+"AND INSTR(:cardGroupMainType,CARDGROUP_MAIN_TYPE)<>0 AND INSTR(:cardGroupSubType,CARDGROUP_SUB_TYPE)<>0 AND INSTR(:cardGroupSeqType,CARDGROUP_SEQ)<>0");                    
                            populateCardGroupValues(populateStringValues(getBindings().getCardGroup().getValue().toString()));                            
                        System.out.println("card group main type======>"+cardGroupMaintypePassValue);
                        System.out.println("card group sub type===>"+cardGroupSubtypePassValues);
                        System.out.println("card group sequence value====>"+cardGroupSeqPassValues);
                            invoiceVO.defineNamedWhereClauseParam("cardGroupMainType",cardGroupMaintypePassValue,null);
                            invoiceVO.defineNamedWhereClauseParam("cardGroupSubType",cardGroupSubtypePassValues,null);
                            invoiceVO.defineNamedWhereClauseParam("cardGroupSeqType",cardGroupSeqPassValues,null);                            
                    }
                    
                } 
                System.out.println("Query Formed is="+invoiceVO.getQuery());
                invoiceVO.executeQuery();
                System.out.println("Estimated Row count=="+invoiceVO.getEstimatedRowCount());
                searchResults=true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
                System.out.println("Where condition:"+invoiceVO.getWhereClause());              
                
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
        getBindings().getCardGpCardList().setValue(null);
        this.invoiceTypeValue=null;
        getBindings().getFromDate().setValue(null);
        getBindings().getToDate().setValue(null);
        searchResults=false;
        card=false;
        cardGroup=false;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());  
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardPGL()); 
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
            ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator"); 
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
        System.out.println("InvoiceNumber ="+invoiceNumberValue);
        System.out.println("InvoiceGroupingValue ="+invoiceGroupingValue);
        ViewObject cardTransactionVO =
            ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");  
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
            System.out.println("cardTransaction Query="+cardTransactionVO.getQuery());
            cardTransactionVO.executeQuery();
            System.out.println("cardTransactionVO estimatedRow:"+cardTransactionVO.getEstimatedRowCount());
        }
        
        getBindings().getInvoiceDetails().show(new RichPopup.PopupHints());
        return null;
    }

    public void cgValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(getBindings().getAccount().getValue()!=null)
        {
        if(valueChangeEvent.getNewValue()!=null) {
            System.out.println("Value ="+valueChangeEvent.getNewValue());
            if(valueChangeEvent.getNewValue().equals("CardGroup")) {
            populateValue(valueChangeEvent.getNewValue().toString());
            cardGroup=true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());  
                card=false;
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardPGL());  
                
            }else{
                populateValue(valueChangeEvent.getNewValue().toString());
                card=true;
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardPGL());  
                cardGroup=false;
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());  
                      
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
                        System.out.println("Account Number inside select one radio button==========>"+partnerInfo.getAccountList().get(i).getAccountNumber());
                        if(partnerInfo.getAccountList().get(i).getAccountNumber() != null && partnerInfo.getAccountList().get(i).getAccountNumber().equals(getBindings().getAccount().getValue())){
                            if(partnerInfo.getAccountList().get(i).getCardGroup() != null && partnerInfo.getAccountList().get(i).getCardGroup().size()>0){
                                for(int k =0 ; k< partnerInfo.getAccountList().get(i).getCardGroup().size(); k++){
                                    System.out.println("Card Group inside select one radio button==========>"+partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
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

    public void setCardGroup(boolean cardGroup) {
        this.cardGroup = cardGroup;
    }

    public boolean isCardGroup() {
        return cardGroup;
    }

    public void setCard(boolean card) {
        this.card = card;
    }

    public boolean isCard() {
        return card;
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
       
    }
}

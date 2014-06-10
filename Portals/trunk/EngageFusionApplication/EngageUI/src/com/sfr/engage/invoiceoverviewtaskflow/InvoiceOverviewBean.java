package com.sfr.engage.invoiceoverviewtaskflow;


import com.sfr.core.bean.EngageEmaiUtilityl;
import com.sfr.core.bean.User;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.model.queries.uvo.PrtNewInvoiceVORowImpl;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.component.rich.output.RichSpacer;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;


public class InvoiceOverviewBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    private List<String> accountValue;
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

    private List<PartnerInfo> partnerInfoList;
    private ArrayList<SelectItem> partnerList = null;
    private String partnerValue = null;
    private RichSelectOneRadio radioBtnPopUp;
    private RichPanelGroupLayout transactionPanel;
    private RichPanelGroupLayout invoiceCollectionPanel;
    private String defaultSelection="Transactions";
    private boolean isTransactionVisible= true;
    private boolean isInvoiceCollectionVisible= false;
    private RichOutputText collectiveInvoNoOt;
    private  Date fromDate;
    private  Date toDate;
    private RichPopup confirmation_mail_popup;
    private String to_recipient = null;
    private RichInputText email_recipient_popup;
    private RichOutputText invoice_form;
    private  emailbean email;
    User global_user = new User();
    String invoice_req;
    private RichSpacer spacerFetchUserEmail;
    private String mailRecipient;
    EngageEmaiUtilityl emailutility;

    public InvoiceOverviewBean() {
        super();
        email = new emailbean();
        conversionUtility = new Conversion();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        resourceBundle = new EngageResourceBundle();
        partnerList    = new ArrayList<SelectItem>();
         emailutility = new EngageEmaiUtilityl();



        if(session.getAttribute("Partner_Object_List") != null){
            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
        }

        if(partnerInfoList != null && partnerInfoList.size() > 0){
            for(int i=0 ; i<partnerInfoList.size() ; i++){
                SelectItem selectItemPartner = new SelectItem();
                selectItemPartner.setLabel(partnerInfoList.get(i).getPartnerName().toString());
                selectItemPartner.setValue(partnerInfoList.get(i).getPartnerValue().toString());
                partnerList.add(selectItemPartner);
            }


//            if( partnerInfo.getAccountList() != null && partnerInfo.getAccountList().size() > 0){
//                log.info(accessDC.getDisplayRecord() + this.getClass() + " " + "List of Account in partner info object=====>"+partnerInfo.getAccountList().size());
//                for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
//                    log.info(accessDC.getDisplayRecord() + this.getClass() + " " +"value of Account Id===========>"+partnerInfo.getAccountList().get(i).getAccountNumber().toString());
//                    SelectItem selectItem = new SelectItem();
//                    selectItem.setLabel(partnerInfo.getAccountList().get(i).getAccountNumber().toString());
//                    selectItem.setValue(partnerInfo.getAccountList().get(i).getAccountNumber().toString());
//                    accountList.add(selectItem);
//                }
//            }
        }
        //lang=(String)session.getAttribute(Constants.SESSION_LANGUAGE);

        if(session!= null) {
        lang = (String)session.getAttribute(Constants.userLang);
        }


        currencyCode=conversionUtility.getCurrencyCode(lang);
        locale=conversionUtility.getLocaleFromCountryCode(lang);

    }

    public ArrayList<SelectItem> getAccountList() {
        return accountList;
    }

    public ArrayList<SelectItem> getCardGroupList() {
        return cardGroupList;
    }

    public ArrayList<SelectItem> getCardList() {
        return cardList;
    }

    public ArrayList<SelectItem> getInvoiceTypeList() {
        if (invoiceTypeList == null) {
            invoiceTypeList = new ArrayList<SelectItem>();
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel("Card");
                selectItem.setValue("Card");
                invoiceTypeList.add(selectItem);
                SelectItem selectItem1 = new SelectItem();
                selectItem1.setLabel("Bulk");
                selectItem1.setValue("Bulk");
                invoiceTypeList.add(selectItem1);
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
             fromDate = (java.util.Date)getBindings().getFromDate().getValue();
             toDate = (java.util.Date)getBindings().getToDate().getValue();
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


            else {
                log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "AccountValue="+getBindings().getAccount().getValue());
                log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "FromDate ="+getBindings().getFromDate().getValue());
                ViewObject invoiceVO =ADFUtils.getViewObject("PrtNewInvoiceVO1Iterator");
                log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Before Query="+invoiceVO.getQuery());

                if ("INSTR(:cardPK,INVOICED_CARD)<>0".equalsIgnoreCase(invoiceVO.getWhereClause())) {

                    invoiceVO.removeNamedWhereClauseParam("cardPK");
                    invoiceVO.setWhereClause("");
                    invoiceVO.executeQuery();
                }else {
                    if ("INSTR(:cardGroupMainType,CARDGROUP_MAIN_TYPE)<>0 AND INSTR(:cardGroupSubType,CARDGROUP_SUB_TYPE)<>0 AND INSTR(:cardGroupSeqType,CARDGROUP_SEQ)<>0".equalsIgnoreCase(invoiceVO.getWhereClause())) {

                        invoiceVO.removeNamedWhereClauseParam("cardGroupMainType");
                        invoiceVO.removeNamedWhereClauseParam("cardGroupSubType");
                        invoiceVO.removeNamedWhereClauseParam("cardGroupSeqType");
                        invoiceVO.setWhereClause("");
                        invoiceVO.executeQuery();
                    }
                }
//                invoiceVO.setWhereClause("PARTNER_ID =:partnerId AND INSTR(:accountId,ACCOUNT_ID) <> 0 AND INVOICING_DATE >=: fromDateBV AND INVOICING_DATE <=: toDateBV");
                System.out.println(" Value of account Id=================>"+populateStringValues(getBindings().getAccount().getValue().toString()));
                invoiceVO.setNamedWhereClauseParam("accountId",populateStringValues(getBindings().getAccount().getValue().toString()));
                invoiceVO.setNamedWhereClauseParam("countryCode",lang);
                invoiceVO.setNamedWhereClauseParam("partnerId",getBindings().getPartnerNumber().getValue());
                invoiceVO.setNamedWhereClauseParam("fromDateBV",formatConversion(fromDate).toString());
                invoiceVO.setNamedWhereClauseParam("toDateBV",formatConversion(toDate).toString());
//                if(getBindings().getInvoiceType().getValue()!=null) {
//                    invoiceVO.defineNamedWhereClauseParam("invoiceType",getBindings().getInvoiceType().getValue(),null);
//                }else {
//                    invoiceVO.defineNamedWhereClauseParam("invoiceType",null,null);
//                }
//                String baseWhereClause=invoiceVO.getWhereClause();

                if(getBindings().getCardGpCardList().getValue()!=null) {
                    if("Card".equalsIgnoreCase(getBindings().getCardGpCardList().getValue().toString())) {
                        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Inside card");
                         invoiceVO.setWhereClause("INSTR(:cardPK,INVOICED_CARD)<>0");
                         String cardValuesList=populateStringValues(getBindings().getCard().getValue().toString());
                          invoiceVO.defineNamedWhereClauseParam("cardPK",cardValuesList,null);
                    }else {
                        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Inside cardgroup");
                            invoiceVO.setWhereClause("INSTR(:cardGroupMainType,CARDGROUP_MAIN_TYPE)<>0 AND INSTR(:cardGroupSubType,CARDGROUP_SUB_TYPE)<>0 AND INSTR(:cardGroupSeqType,CARDGROUP_SEQ)<>0");
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

        //this.accountValue=null;
        this.partnerValue = null;
        getBindings().getAccount().setSubmittedValue(null);
        getBindings().getAccount().setValue(null);
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
        defaultSelection="Transactions";
        AdfFacesContext.getCurrentInstance().addPartialTarget(radioBtnPopUp);
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

        String invoiceGroupingValue =null;
        defaultSelection="Transactions";
        AdfFacesContext.getCurrentInstance().addPartialTarget(radioBtnPopUp);
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding itr =
            (DCIteratorBinding)bindings.get("PrtNewInvoiceVO1Iterator");
        Row row =    itr.getCurrentRow();
        if (row != null) {

            invoiceGroupingValue = (String)row.getAttribute("InvoiceDocType");
        }

        String invoiceNumberValue =
            (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("invoiceNumberValue");

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
        radioBtnPopUp.setSubmittedValue(null);
        radioBtnPopUp.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(radioBtnPopUp);
        getBindings().getInvoiceDetails().show(new RichPopup.PopupHints());
        isTransactionVisible= false;
        isInvoiceCollectionVisible=false;

        return null;
    }

    public void cgValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(getBindings().getAccount().getValue()!=null)
        {
            String accountNumberPassingValues = null;
            String[] accountNumberValues;
            int accountCount = 0;
            accountNumberPassingValues =  populateStringValues(getBindings().getAccount().getValue().toString());
            cardGroupList  = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();
            cardList       = new ArrayList<SelectItem>();
            cardValue      = new ArrayList<String>();
            if(accountNumberPassingValues != null){
                if(accountNumberPassingValues.contains(",")){
                    accountNumberValues = accountNumberPassingValues.split(",");
                    accountCount  = accountNumberValues.length;
                }else{
                    accountCount  = 1;
                    accountNumberValues = new String[1];
                    accountNumberValues[0] = accountNumberPassingValues;
                }

                if(valueChangeEvent.getNewValue()!=null && accountCount > 0){
                    for(int acCount=0 ; acCount<accountCount; acCount++){
                        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Value ="+valueChangeEvent.getNewValue());
                        if(valueChangeEvent.getNewValue().equals("CardGroup")) {
                            populateValue(valueChangeEvent.getNewValue().toString(),accountNumberValues[acCount].trim());
                            cGCardVisible=true;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                            cardGroupVisible=true;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
                            cardVisible=false;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
                        }else{
                            populateValue(valueChangeEvent.getNewValue().toString(),accountNumberValues[acCount].trim());
                            cGCardVisible=true;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                            cardVisible=true;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
                            cardGroupVisible=false;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
                        }
                    }
                }
            }
        }else{
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

    public void populateValue(String paramType, String accountNumber){
        if(paramType != null){
            if(paramType.equals("CardGroup")){
//                cardGroupList  = new ArrayList<SelectItem>();
//                cardGroupValue  = new ArrayList<String>();
                popoluateCardCardgroupValues(accountNumber, paramType);
//                if(partnerInfo.getAccountList() != null && partnerInfo.getAccountList().size() > 0){
//                    for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
//                        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Account Number inside select one radio button==========>"+partnerInfo.getAccountList().get(i).getAccountNumber());
//                        if(partnerInfo.getAccountList().get(i).getAccountNumber() != null && partnerInfo.getAccountList().get(i).getAccountNumber().equals(getBindings().getAccount().getValue())){
//                            if(partnerInfo.getAccountList().get(i).getCardGroup() != null && partnerInfo.getAccountList().get(i).getCardGroup().size()>0){
//                                for(int k =0 ; k< partnerInfo.getAccountList().get(i).getCardGroup().size(); k++){
//                                    log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Card Group inside select one radio button==========>"+partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
//                                    if(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID()!= null){
//                                    SelectItem selectItem = new SelectItem();
//                                    selectItem.setLabel(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
//                                    selectItem.setValue(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
//                                    cardGroupList.add(selectItem);
//                                    cardGroupValue.add(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
            }else{
                    if(paramType.equals("Card")){
//                    cardList  = new ArrayList<SelectItem>();
//                    cardValue  = new ArrayList<String>();
                    popoluateCardCardgroupValues(accountNumber, paramType);
    //                if(partnerInfo.getAccountList() != null && partnerInfo.getAccountList().size() > 0){
    //                    for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
    //                        if(partnerInfo.getAccountList().get(i).getAccountNumber() != null && partnerInfo.getAccountList().get(i).getAccountNumber().equals(getBindings().getAccount().getValue())){
    //                            if(partnerInfo.getAccountList().get(i).getCardGroup() != null && partnerInfo.getAccountList().get(i).getCardGroup().size()>0){
    //                                for(int k =0 ; k< partnerInfo.getAccountList().get(i).getCardGroup().size(); k++){
    //                                    if(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard() != null && partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().size()>0){
    //                                    for(int m =0 ; m<partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().size(); m++){
    //                                            if(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID()!= null && partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getExternalCardID() != null){
    //                                                SelectItem selectItem = new SelectItem();
    //                                                selectItem.setLabel(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getExternalCardID().toString());
    //                                                selectItem.setValue(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID().toString());
    //                                                cardList.add(selectItem);
    //                                                cardValue.add(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID().toString());
    //                                            }
    //                                        }
    //                                    }
    //                                }
    //                            }
    //                        }
    //                    }
    //                }
                }
            }
        }
    }

    public void popoluateCardCardgroupValues(String passingAccountNumber , String paramType){
        if(passingAccountNumber != null && paramType != null){
            if(partnerInfoList != null && partnerInfoList.size() > 0){
                for(int pa=0 ; pa<partnerInfoList.size() ; pa++){
                    if(partnerInfoList.get(pa).getPartnerValue() != null && partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() >0){
                        for(int ac=0 ; ac<partnerInfoList.get(pa).getAccountList().size(); ac++){
                            if(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber() != null && partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().equals(passingAccountNumber)
                              && partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup() != null && partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size() >0){
                                for(int cg=0 ; cg<partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size(); cg++){
                                    if(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupID() != null){
                                        if(paramType.equals("CardGroup")){
                                            SelectItem selectItemCardGroup = new SelectItem();
                                            selectItemCardGroup.setLabel(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupName().toString());
                                            selectItemCardGroup.setValue(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupID().toString());
                                            cardGroupList.add(selectItemCardGroup);
                                            cardGroupValue.add(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupID().toString());
                                        }else{
                                            if(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard() != null
                                               && partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size()>0){
                                                for(int cc=0 ; cc<partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size(); cc++){
                                                    if(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID() != null
                                                       && partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID() != null){
                                                           SelectItem selectItem = new SelectItem();
                                                           selectItem.setLabel(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
                                                           selectItem.setValue(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID());
                                                           cardList.add(selectItem);
                                                           cardValue.add(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID());
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
            cGCardVisible    = false;
            cardGroupVisible = false;
            cardVisible      = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGpCardList());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());

        }
    }

    public void partnerValueChangeListener(ValueChangeEvent valueChangeEvent){
            if(valueChangeEvent.getNewValue()!=null) {
                getBindings().getCardGpCardList().setValue(null);
                cGCardVisible    = false;
                cardGroupVisible = false;
                cardVisible      = false;

                accountList    = new ArrayList<SelectItem>();
                accountValue   = new ArrayList<String>();
                if(partnerInfoList != null && partnerInfoList.size() > 0){
                    for(int i=0 ; i<partnerInfoList.size() ; i++){
                        if(partnerInfoList.get(i).getPartnerValue() != null && partnerInfoList.get(i).getPartnerValue().toString().equals(valueChangeEvent.getNewValue().toString())
                           && partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() >0){
                            for(int m=0 ; m<partnerInfoList.get(i).getAccountList().size(); m++){
                                if(partnerInfoList.get(i).getAccountList().get(m).getAccountNumber() != null){
                                    SelectItem selectItemAccount = new SelectItem();
                                    selectItemAccount.setLabel(partnerInfoList.get(i).getAccountList().get(m).getAccountNumber().toString());
                                    selectItemAccount.setValue(partnerInfoList.get(i).getAccountList().get(m).getAccountNumber().toString());
                                    accountList.add(selectItemAccount);
                                    accountValue.add(partnerInfoList.get(i).getAccountList().get(m).getAccountNumber().toString());
                                }
                            }
                        }
                    }
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGpCardList());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());

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
            ADFUtils.getViewObject("PrtNewInvoiceVO1Iterator");
        PrtNewInvoiceVORowImpl row=(PrtNewInvoiceVORowImpl)invoiceVO.getCurrentRow();
        String invoiceNumberValuePdf = row.getFinalinvoice();
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

    public String open_popup() {
        System.out.println("Inside popup");


        ViewObject invoiceVO =
            ADFUtils.getViewObject("PrtNewInvoiceVO1Iterator");
        PrtNewInvoiceVORowImpl row=(PrtNewInvoiceVORowImpl)invoiceVO.getCurrentRow();
        String invoiceNumberValuePdf = row.getFinalinvoice();
        log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "invoice number"+invoiceNumberValuePdf);
        //log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "PartnerId "+partnerId);

        if(invoiceNumberValuePdf != null)
        {

            System.out.println("Invoice requested " + invoiceNumberValuePdf);

        ectx = FacesContext.getCurrentInstance().getExternalContext();
                request = (HttpServletRequest)ectx.getRequest();
                session = request.getSession(false);


        session.setAttribute("SESSION_USER_INVOICE_REQ", invoiceNumberValuePdf);


        }

        else
        {
            System.out.println("Note able to find requested invoice");

        }


        RichPopup.PopupHints ps = new RichPopup.PopupHints();
        confirmation_mail_popup.show(ps);

        return null;
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
        invoiceNo.setValue(invoiceNumber.toString().trim());

        Property partnerId = new Property();
        partnerId.setName("xPartnerId");
        partnerId.setValue(getBindings().getPartnerNumber().getValue().toString().trim());

        Property docType = new Property();
        docType.setName("xDocumentType");
        docType.setValue("PDF");

        Property contentType = new Property();
        contentType.setName("xContentType");
        //TODO : To be read from Property file
        contentType.setValue("FCP");

        Property subType = new Property();
        subType.setName("xSubType");
        //TODO : To be read from Property file
        //subType.setValue("Self_Billing_Print_Reports");
        subType.setValue("Invoice");


        Property country = new Property();
        country.setName("xCountry");
        country.setValue(lang);



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

    public void setPartnerList(ArrayList<SelectItem> partnerList) {
        this.partnerList = partnerList;
    }

    public ArrayList<SelectItem> getPartnerList() {
        return partnerList;
    }

    public void setAccountValue(List<String> accountValue) {
        this.accountValue = accountValue;
    }

    public List<String> getAccountValue() {
        return accountValue;
    }

    public void setPartnerValue(String partnerValue) {
        this.partnerValue = partnerValue;
    }

    public String getPartnerValue() {
        return partnerValue;
    }

    public void setRadioBtnPopUp(RichSelectOneRadio radioBtnPopUp) {
        this.radioBtnPopUp = radioBtnPopUp;
    }

    public RichSelectOneRadio getRadioBtnPopUp() {
        return radioBtnPopUp;
    }

    public void radioBtnPopUpVCE(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent !=null && valueChangeEvent.getNewValue()!=null && valueChangeEvent.getNewValue().equals("Transactions")){
            isTransactionVisible=true;
            isInvoiceCollectionVisible=false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(invoiceCollectionPanel);
            AdfFacesContext.getCurrentInstance().addPartialTarget(transactionPanel);
        }
        else{
            isTransactionVisible=false;
            isInvoiceCollectionVisible=true;
//            Date fromDate = (java.util.Date)getBindings().getFromDate().getValue();
//            Date toDate = (java.util.Date)getBindings().getToDate().getValue();
            String invoiceNo= collectiveInvoNoOt.getValue().toString();


            ViewObject invoiceDetailVO =ADFUtils.getViewObject("PrtInvoiceDetailVo1Iterator");
            invoiceDetailVO.setWhereClause("PARTNER_ID =:partnerId AND INSTR(:accountId,ACCOUNT_ID) <> 0 AND INVOICING_DATE >=: fromDateBV AND INVOICING_DATE <=: toDateBV AND COLLECTIVE_INVOICE_NUMBER =: invoiceNo");
            System.out.println(" Value of account Id=================>"+populateStringValues(getBindings().getAccount().getValue().toString()));
            invoiceDetailVO.setNamedWhereClauseParam("accountId",populateStringValues(getBindings().getAccount().getValue().toString()));
            invoiceDetailVO.setNamedWhereClauseParam("countryCode",lang);
            invoiceDetailVO.setNamedWhereClauseParam("partnerId",getBindings().getPartnerNumber().getValue());
            invoiceDetailVO.setNamedWhereClauseParam("fromDateBV",formatConversion(fromDate).toString());
            invoiceDetailVO.setNamedWhereClauseParam("toDateBV",formatConversion(toDate).toString());
            invoiceDetailVO.setNamedWhereClauseParam("invoiceNo",invoiceNo);
            log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Query Formed for detail is="+invoiceDetailVO.getQuery());
                            invoiceDetailVO.executeQuery();
                            log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Estimated Row count of details=="+invoiceDetailVO.getEstimatedRowCount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(transactionPanel);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invoiceCollectionPanel);
        }
    }

    public void setTransactionPanel(RichPanelGroupLayout transactionPanel) {
        this.transactionPanel = transactionPanel;
    }

    public RichPanelGroupLayout getTransactionPanel() {
        return transactionPanel;
    }

    public void setInvoiceCollectionPanel(RichPanelGroupLayout invoiceCollectionPanel) {
        this.invoiceCollectionPanel = invoiceCollectionPanel;
    }

    public RichPanelGroupLayout getInvoiceCollectionPanel() {
        return invoiceCollectionPanel;
    }

    public void setDefaultSelection(String defaultSelection) {
        this.defaultSelection = defaultSelection;
    }

    public String getDefaultSelection() {
        return defaultSelection;
    }

    public void setIsTransactionVisible(boolean isTransactionVisible) {
        this.isTransactionVisible = isTransactionVisible;
    }

    public boolean isIsTransactionVisible() {
        return isTransactionVisible;
    }

    public void setIsInvoiceCollectionVisible(boolean isInvoiceCollectionVisible) {
        this.isInvoiceCollectionVisible = isInvoiceCollectionVisible;
    }

    public boolean isIsInvoiceCollectionVisible() {
        return isInvoiceCollectionVisible;
    }

    public void setCollectiveInvoNoOt(RichOutputText collectiveInvoNoOt) {
        this.collectiveInvoNoOt = collectiveInvoNoOt;
    }

    public RichOutputText getCollectiveInvoNoOt() {
        return collectiveInvoNoOt;
    }



    public void setConfirmation_mail_popup(RichPopup confirmation_mail_popup) {
        this.confirmation_mail_popup = confirmation_mail_popup;
    }

    public RichPopup getConfirmation_mail_popup() {
        return confirmation_mail_popup;
    }

    public void confirmation_popup_value(DialogEvent dialogEvent) {
        // Add event code here...

        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok)
            {
        //
               String mail_result =  triggermail();
               System.out.println("Notification of Mail " + mail_result);
            }
        else
        {
            System.out.println("mail cancelled");
        return;
        }
    }

    private String getLocalizedString(String Key, String countryCode) {
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass() + "invoiceOverviewBean.getLocalizedString : "+"in getLocalizedString");
                HashMap paramList = new HashMap();

                paramList.put("translationkey", Key);
                paramList.put("ccCode", countryCode);
                String value=accessDC.callDCForErrorMsg("getTranslation", paramList);
                if(value!=null)
                    return value;
                else
                    return "";
                }

    public String triggermail() {

        DAOFactory daoFactory = new DAOFactory();


        if(session!= null) {
        lang = (String)session.getAttribute(Constants.userLang);
        System.out.println("lang "+lang);
        }
        String contact_Link=daoFactory.getPropertyValue("CONTACT_STATOIL"+"_"+lang);
        String engagePortalLink=daoFactory.getPropertyValue("WSPORTAL_LINK"+"_"+lang);

        System.out.println("In mail meethod");
        boolean sendEmail = false;



        ectx = FacesContext.getCurrentInstance().getExternalContext();
                request = (HttpServletRequest)ectx.getRequest();
                session = request.getSession(false);

        invoice_req = null;

        if (session != null) {
        if(session.getAttribute("SESSION_USER_INVOICE_REQ") != null)
        {
        invoice_req = session.getAttribute("SESSION_USER_INVOICE_REQ").toString();
        System.out.println("invoice req = " + invoice_req);
        }
        }




                     String[] months = {"January", "February",
                       "March", "April", "May", "June", "July",
                       "August", "September", "October", "November",
                       "December"};

                       Calendar cal = Calendar.getInstance();
                       String month = months[cal.get(Calendar.MONTH)];
                        int year =cal.get(Calendar.YEAR);
                     int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

                       System.out.println("Month name: " + month);
                        System.out.println("year name: " + year);
                     System.out.println("Day name: " + dayOfMonth);


                   String env = "/u01/WCP_DEV/stores/images/statoil_logo.jpg";
                   //String env = "C:\\Users\\10604129\\Desktop\\IMG_3380.jpg";



    //                   String env = "C:\\Users\\10604350\\Desktop\\Chrysanthemum.jpg";
                String email2="<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
        //            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<title>Email from SFR</title>\n" +
                "<style>"+
                     "a:link {text-decoration:none;}"+
                     "a:visited {text-decoration:none;}"+
                     "a:hover {text-decoration:underline;}"+
                     "a:active {text-decoration:underline;}"+
                     "</style>"+

                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "  <tr>\n" +
                "    <td align=\"left\" valign=\"top\" bgcolor=\"\" style=\"background-color:;\"><br>\n" +
                "    <br>\n" +
                "    <table width=\"800\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "      <tr>" +
        //            "        <td align=\"left\" valign=\"top\" style=\"padding:5px;\"><img src=\"images/logo.png\" width=\"298\" height=\"67\" style=\"display:block;\"></td>\n" +
                "      </tr>" +
                "      <tr>" +
                "        <td align=\"left\" valign=\"top\"><img src=\"cid:image\" width=\"\" height=\"50\" style=\"display:block;\"></td>\n" +
                "      </tr>" +
                     "<tr> " +
                        "<td align=\"left\" valign=\"top\" style=\"background-color:rgb(255,255,255); color:#ffffff; font-family:gerogia; font-size:6px;\"><font Color=\"#ffffff\">hi</font></td>" +
                      "</tr>"+
                "      <tr>\n" +

        //            "        <td align=\"center\" valign=\"top\" bgcolor=\"#006c00\" style=\"background-color:rgb(58,56,57); color:white;\"></td>"+

                "        <td width=\"800\" align=\"center\" valign=\"top\" bgcolor=\"#006c00\" style=\"background-color:rgb(58,56,57); color:#000000;\">"+
            "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n" +
                "          <tr>\n" +
                "            <td width=\"50%\" align=\"left\" valign=\"top\" style=\"color:#ffffff; font-family:gerogia; font-size:16px;\">&nbsp;&nbsp;"+month+" "+dayOfMonth+", "+year +" </td>" +
                "            <td align=\"right\" valign=\"top\" style=\"color:#ffffff; font-family:gerogia; font-size:16px;\"><font Color=\"#73D2EE\">      </font></td>\n" +
                "          </tr>\n" +
                "        </table></td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <td align=\"left\" valign=\"top\" bgcolor=\"#ffffff\" style=\"background-color:#ffffff; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:0px;\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"10\" style=\"margin-bottom:10px;\">\n" +
                "          <tr>\n" +
                "            <td align=\"left\" valign=\"top\" style=\"font-family:gerogia; font-size:16px; color:#525252;\">\n" +
        //            "            <div style=\"font-size:20px; font-family:gerogia; color:#73D2EE;\"><b>Welcome to Statoil </b></div>\n" +
        //            "              <div style=\"font-size:28px;\">consectetur adipiscing elit. Vestibulum magna enim, volutpat nec imperdiet id</div>\n" +
                "<div style=\"font-size:16px;\"><br>\n" +
                    "<i>  Dear Customer, <br><br>" +
                //"<i>  Dear " + first_name + ",<br><br>" +
                getLocalizedString("ENCLOSED", lang) +
                "<a href=" + engagePortalLink +"><font Color=\"#F89518\">"+ getLocalizedString("ENGAGE_PORTAL", lang)+ "</font></a>"+"."+ getLocalizedString("HESITATE", lang) +"<br>" +
                getLocalizedString("AUTOGENERATED", lang) + "<br>"+
                getLocalizedString("CONTACTDETAILS", lang) + "<a href=" + contact_Link + "><font Color=\"#F89518\">here.</font></a>"+
                //"Sincerely,<br>SFR Engage Portal Team" +
        //            "TWe look forward to serve you better on your every online shopping experience. Do visit us soon!<br><br>"+
    //                "<a href=\"http://www.statoilfuelretail.com\"><font Color=\"#F89518\">www.statoilfuelretail.com</a></font>"+
        //            "<a href=\\\"http://10.24.240.6:11104/WsPortal\"><font Color=\"#73D2EE\">Click Here</font></a>" +
                              "<br><br></i>"+
        //            "<font Color=\"Maroon\"> Please do not reply to this email. More info at</font> <a href=\"http://www.statoilfuelretail.com\">" +
        //                 "  <font Color=\"#73D2EE\">www.statoilfuelretail.com</a></font></i>" +
                              "</div></td>\n" +

                "          </tr>\n" +
                "        </table>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "        <td align=\"left\" valign=\"top\" bgcolor=\"#006c00\" style=\"background-color:rgb(243,243,243); \"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"15\">\n" +
                "          <tr>\n" +
                "            <td align=\"left\" valign=\"top\" style=\"color:#7F7F7F; font-family:gerogia; font-size:16px; \">Copyright © 2013 Statoil Fuel & Retail<br>" +
                "     <td align=\"right\" valign=\"top\" style=\"color:#ffffff; font-family:gerogia; font-size:16px;\"><a href=" + contact_Link +"><font Color=\"#F89518\">Contact Statoil</font></a></td>" +
                "          </tr>\n" +
                "        </table></td>\n" +
                "      </tr>\n" +
                "  </table>\n" +
                "    <br>\n" +
                "    <br></td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>\n";


               String cc="Hiten.Karamchandani@lntinfotech.com";

            byte[] responseByteArr = null;
            UCMCustomWeb uCMCustomWeb = null;
            try {

           if(session.getAttribute("ucmInvoiceContentList")!=null)
                {
                ucmInvoiceContentList = (HashMap<String,String>)session.getAttribute("ucmInvoiceContentList");
                String UCMInvoiceContentId = ucmInvoiceContentList.get(invoice_req);
                if (UCMInvoiceContentId != null && UCMInvoiceContentId.trim().length() > 0) {
                    log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "ContentId is available from session");
                    uCMCustomWeb = new DAOFactory().getUCMService();
                    responseByteArr = uCMCustomWeb.getFileFromUCM(DAOFactory.getPropertyValue(Constants.UCM_USERNAME), DAOFactory.getPropertyValue(Constants.UCM_PASSWORD),
                                                        UCMInvoiceContentId);
                    if (responseByteArr == null || responseByteArr.length == 0) {
    //                        isError = true;
                        sendEmail = false;
                        System.out.println("Error");
                    }
                }
                else {
                    responseByteArr = searchGetFile(invoice_req);
                    if(responseByteArr!=null && responseByteArr.length!=0) {
                      System.out.println(responseByteArr.length);
                        sendEmail = true;
                    }else
                    {
                    //isError = true;
                    sendEmail = false;
                        System.out.println("Eoorororoo");
                    }

                }
            }
                else{
                   log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "session is null");
                     responseByteArr=searchGetFile(invoice_req);
                   if(responseByteArr!=null && responseByteArr.length!=0) {
                       System.out.println(responseByteArr.length);
                       sendEmail = true;
                   }else {
                       sendEmail=false;
                   System.out.println("Eoorororoodddd");
                   }

                }





            } catch (Exception e) {
                System.out.println("fileDownload : " + "Exception");
                e.printStackTrace();
            }





            System.out.println("sending email" + sendEmail +" to " + email_recipient_popup.getValue().toString() + "for invoice " + invoice_req +"having byte array size as"+ responseByteArr.length);

            System.out.println("hiten");

            try{
                if(sendEmail)
                {
            emailutility.sendEmail("no-reply.SFR-Services@statoilfuelretail.com",
            email_recipient_popup.getValue().toString(),
             "Statoilfuelretail : Invoice Delivery", email2, "smtp", "smtp.statoilfuelretail.com",cc,responseByteArr,env,invoice_req);
                }
                else {
                    System.out.println("Throw adf message of mail can not be send");
                }

                }
            catch(Exception e) {
                System.out.println("error in mail");
                e.printStackTrace();

            }




        return null;

    }
    public void setTo_recipient(String to_recipient) {
        this.to_recipient = to_recipient;
    }

    public String getTo_recipient() {
//        System.out.println("inside getTo_recipient");
//        if (session != null) {
//            if (null != session.getAttribute(Constants.SESSION_USER_INFO)) {
//                global_user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
//                System.out.println("Invoice bean : "+"user first name in my profile bean " + global_user.getFirstName());
//                to_recipient = global_user.getEmailID();
//            }
//            else
//                to_recipient = "";
//
//
//        }
//        else
//            System.out.println("No session found in constructor of InvoiceOverviewBean");
//
//        System.out.println("user det in cons" + global_user.getFirstName());
        return to_recipient;
    }

    public void setEmail_recipient_popup(RichInputText email_recipient_popup) {
        System.out.println("inside setEmail_recipient_popup");
        if (session != null) {
            if (null != session.getAttribute(Constants.SESSION_USER_INFO)) {
                global_user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
                if(global_user.getEmailID()!= null) {
                    System.out.println("Invoice bean : "+"user first name in my profile bean " + global_user.getEmailID());
                                    email_recipient_popup.setValue(global_user.getEmailID().trim());
                }else
                {System.out.println("Invoice bean : "+"user first name in my profile bean " + global_user.getFirstName());
                email_recipient_popup.setValue(global_user.getFirstName().toString().trim());}


            }
            else
                email_recipient_popup.setValue("temp");


        }
        else
            System.out.println("No session found in constructor of InvoiceOverviewBean");

        System.out.println("user det in cons" + global_user.getFirstName());
        this.email_recipient_popup = email_recipient_popup;
    }

    public RichInputText getEmail_recipient_popup() {
        return email_recipient_popup;
    }

    public void setInvoice_form(RichOutputText invoice_form) {
        this.invoice_form = invoice_form;
    }

    public RichOutputText getInvoice_form() {
        return invoice_form;
    }

    public void setSpacerFetchUserEmail(RichSpacer spacerFetchUserEmail) {
        System.out.println("inside setSpacerFetchUserEmail");

//        if (session != null) {
//            if (null != session.getAttribute(Constants.SESSION_USER_INFO)) {
//                global_user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
//                System.out.println("Invoice bean : "+"user first name in my profile bean " + global_user.getFirstName());
//                to_recipient = global_user.getEmailID();
//            }
//            else
//                to_recipient = "";
//
//
//        }
//        else
//            System.out.println("No session found in constructor of InvoiceOverviewBean");
//        AdfFacesContext.getCurrentInstance().addPartialTarget(email_recipient_popup);
//
//        System.out.println("user det in cons" + global_user.getFirstName());
        this.spacerFetchUserEmail = spacerFetchUserEmail;
    }

    public RichSpacer getSpacerFetchUserEmail() {


        return spacerFetchUserEmail;
    }

    public void setMailRecipient(String mailRecipient) {
    System.out.println("inside setMailRecipient");
//        if (session != null) {
//            if (null != session.getAttribute(Constants.SESSION_USER_INFO)) {
//                global_user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
//                System.out.println("Invoice bean : "+"user first name in my profile bean " + global_user.getFirstName());
//                mailRecipient = global_user.getEmailID();
//            }
//            else
//                mailRecipient = "";
//
//
//        }
//        else
//            System.out.println("No session found in constructor of InvoiceOverviewBean");
//
//        System.out.println("user det in cons" + global_user.getFirstName());



        this.mailRecipient = mailRecipient;
    }

    public String getMailRecipient() {
        System.out.println("inside getMailRecipient");
        return mailRecipient;
    }

    public class Bindings {
        private RichSelectManyChoice account;
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
        private RichSelectOneChoice partnerNumber;


       public void setInvoiceType(RichSelectOneChoice invoiceType) {
            this.invoiceType = invoiceType;
        }

        public RichSelectOneChoice getInvoiceType() {
            return invoiceType;
        }

        public void setFromDate(RichInputDate fromDate) {
            System.out.println("Date should be setted to sys date -1 month");
            Date dateNow = new java.util.Date();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(dateNow);
            gc.add(GregorianCalendar.MONTH, -1);
            Date dateBefore = gc.getTime();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
            String tmp = dateformat.format(dateBefore);
            fromDate.setValue(tmp);

            this.fromDate = fromDate;


        }

        public RichInputDate getFromDate() {
            return fromDate;
        }

        public void setToDate(RichInputDate toDate) {
            System.out.println("Date should be setted to sys date");
            Date dateNow = new java.util.Date();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
            String tmp = dateformat.format(dateNow);
            toDate.setValue(tmp);
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

        public void setPartnerNumber(RichSelectOneChoice partnerNumber) {
            this.partnerNumber = partnerNumber;
        }

        public RichSelectOneChoice getPartnerNumber() {
            return partnerNumber;
        }

        public void setAccount(RichSelectManyChoice account) {
            this.account = account;
        }

        public RichSelectManyChoice getAccount() {
            return account;
        }
    }
}

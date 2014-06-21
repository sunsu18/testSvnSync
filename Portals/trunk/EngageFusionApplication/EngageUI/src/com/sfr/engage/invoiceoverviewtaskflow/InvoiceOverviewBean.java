package com.sfr.engage.invoiceoverviewtaskflow;


import com.sfr.core.bean.EngageEmaiUtilityl;
import com.sfr.core.bean.User;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.core.ValueListSplit;
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

import java.text.DateFormat;
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
    private String accountQueryDetail="(";
    private String accountQuery="(";
    private String cardGroupQuery="(";
    private String cardQuery="(";
    private Map<String,String> mapAccountDetailListValue;
    private Map<String,String> mapAccountListValue;
    private Map<String,String> mapCardGroupListValue;
    private Map<String,String> mapCardListValue;
    public static final ADFLogger _logger = AccessDataControl.getSFRLogger();
    private ValueListSplit valueList;
    private RichOutputText mailResult;
    private RichOutputText mailResultInvoiceNotFound;
    private RichOutputText mailResultFailure;
    private boolean invoiceNotFound;
    private boolean successResult;
    private boolean failureResult;
    private boolean validEmail;
    private boolean resultFromTo=true;
    private boolean resultToFrom=true;
    private String mailLnag = "";
    private boolean setreceipent = true;
    private RichPanelGroupLayout resultPanel;

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
        valueList = new ValueListSplit();

        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Constructor of Invoice overview bean");

        if(session.getAttribute("lang")!= null) {
            mailLnag = (String)session.getAttribute("lang");
        }

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
if(partnerInfoList.size() == 1) {
    this.partnerValue=partnerInfoList.get(0).getPartnerValue().toString();

    accountList    = new ArrayList<SelectItem>();
    accountValue   = new ArrayList<String>();

    for(int i=0 ; i<partnerInfoList.size(); i++){

    if(partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0){

    for(int j=0;j<partnerInfoList.get(i).getAccountList().size();j++){


    if(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() != null){


    SelectItem selectItem = new SelectItem();
                        selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                        selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                        accountList.add(selectItem);
                        accountValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
    }
    }
    }
    }

}

//            if( partnerInfo.getAccountList() != null && partnerInfo.getAccountList().size() > 0){
//                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "List of Account in partner info object=====>"+partnerInfo.getAccountList().size());
//                for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
//                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"value of Account Id===========>"+partnerInfo.getAccountList().get(i).getAccountNumber().toString());
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

        //*Added for 1000 card isssue
        if(session!=null) {
            if(session.getAttribute("account_Query_Invoice_overview")!=null)
            {
            accountQuery=session.getAttribute("account_Query_Invoice_overview").toString().trim();
            mapAccountListValue= (Map<String,String>)session.getAttribute("map_Account_List_Invoice_overview");
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                 "account Query & mapAccountList is found");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                     "account "+accountQuery);
            }
            if(session.getAttribute("account_Query_Invoice_detail_overview")!=null)
            {
            accountQueryDetail=session.getAttribute("account_Query_Invoice_detail_overview").toString().trim();
            mapAccountDetailListValue= (Map<String,String>)session.getAttribute("map_Account_List_Invoice_detail_overview");
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                 "account Detail Query & mapAccountList is found");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                     "account "+accountQueryDetail);
            }
            if(session.getAttribute("cardGroup_Query_Invoice_overview")!=null)
            {
            cardGroupQuery=session.getAttribute("cardGroup_Query_Invoice_overview").toString().trim();
            mapCardGroupListValue= (Map<String,String>)session.getAttribute("map_CardGroup_List_Invoice_overview");
             _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                 "CardGroup Query & mapCardGroupList is found");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                     "CardGroup "+cardGroupQuery);
            }
            if(session.getAttribute("card_Query_Invoice_overview")!=null)
            {
            cardQuery=session.getAttribute("card_Query_Invoice_overview").toString().trim();
            mapCardListValue= (Map<String,String>)session.getAttribute("map_Card_List_Invoice_overview");
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                 "card Query & mapCardList is found");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                     "Card "+cardQuery);
            }

        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting constructor for invoice overview bean");
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
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "PassedcardGrpVar ="+cardGrpVar);
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

               _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "CardGroupMainType ="+cardGroupMaintype);
               _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "cardGroupSubtype ="+cardGroupSubtype);
               _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "cardGroupSeq ="+cardGroupSeq);

                 cardGroupMaintypePassValue = cardGroupMaintype.trim().substring(0, cardGroupMaintype.length()-1);
                 cardGroupSubtypePassValues = cardGroupSubtype.trim().substring(0, cardGroupSubtype.length()-1);
                 cardGroupSeqPassValues     = cardGroupSeq.trim().substring(0, cardGroupSeq.length()-1);
           }
       }

    public void searchResultsListener(ActionEvent actionEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside searchResultsListener for Invoices");
        // Add event code here...
        String newFromDate = null;
        String newToDate = null;
        if(getBindings().getPartnerNumber().getValue()!=null && getBindings().getAccount().getValue()!=null && getBindings().getFromDate().getValue()!=null && getBindings().getToDate().getValue()!=null
        && getBindings().getCardGpCardList().getValue()!=null && getBindings().getCardGroup().getValue()!=null && getBindings().getCard().getValue()!=null) {
//             fromDate = (java.util.Date)getBindings().getFromDate().getValue();
//             toDate = (java.util.Date)getBindings().getToDate().getValue();
//            if (toDate.before(fromDate)) {
//                if (resourceBundle.containsKey("INVOICE_TODATE_LESSTHAN")) {
//                    FacesMessage msg =
//                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                                         (String)resourceBundle.getObject("INVOICE_TODATE_LESSTHAN"),
//                                         "");
//                    FacesContext.getCurrentInstance().addMessage(null,
//                                                                 msg);
//                }
//          }


                DateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
                Date fromDate =
                    (java.util.Date)getBindings().getFromDate().getValue();
                Date toDate =
                    (java.util.Date)getBindings().getToDate().getValue();
                newFromDate = sdf.format(fromDate);
                newToDate = sdf.format(toDate);

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
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "PartnerValue="+getBindings().getPartnerNumber().getValue());
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "AccountValue="+getBindings().getAccount().getValue());
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "getCardGpCardList="+getBindings().getCardGpCardList().getValue());
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "getCardGroup="+getBindings().getCardGroup().getValue());
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "getCard="+getBindings().getCard().getValue());
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "ToDate="+getBindings().getToDate().getValue());
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "FromDate ="+newFromDate +"To Date = "+ newToDate);
                ViewObject invoiceVO =ADFUtils.getViewObject("PrtNewInvoiceVO1Iterator");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Before Query="+invoiceVO.getQuery());


                if(cardQuery.length()>1 && cardQuery != null && cardGroupQuery.length()<=2) {

                    if(((accountQuery+"AND "+cardQuery).trim().equalsIgnoreCase(invoiceVO.getWhereClause().trim())) || ((accountQuery+" AND "+cardQuery).trim().equalsIgnoreCase(invoiceVO.getWhereClause().trim()))) {
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                    " " + "inside  card with out purchase code where removal class");
                        if(mapAccountListValue!=null)
                        {
                        for(int i=0;i< mapAccountListValue.size();i++) {
                                String values="account"+i;
                                invoiceVO.removeNamedWhereClauseParam(values);
                        }
                        }else {
                            invoiceVO.removeNamedWhereClauseParam("account");
                        }
                        if(mapCardListValue!=null)
                        {
                        for(int i=0;i< mapCardListValue.size();i++) {
                                String values="card"+i;
                                invoiceVO.removeNamedWhereClauseParam(values);
                        }

                        }else{
                            invoiceVO.removeNamedWhereClauseParam("card");
                        }
                        invoiceVO.setWhereClause("");
                        invoiceVO.executeQuery();
                    }
                }
                else {
                                if(cardGroupQuery.length()>1 && cardGroupQuery != null && cardQuery.length()<=1) {

                                    if(((accountQuery +"AND "+ cardGroupQuery).trim().equalsIgnoreCase(invoiceVO.getWhereClause().trim())) || ((accountQuery +" AND "+ cardGroupQuery).trim().equalsIgnoreCase(invoiceVO.getWhereClause().trim()))) {
                                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                                    " " + "inside  cardGroup with out purchase code where removal class");
                                        if(mapAccountListValue!=null)
                                        {
                                        for(int i=0;i< mapAccountListValue.size();i++) {
                                                String values="account"+i;
                                                invoiceVO.removeNamedWhereClauseParam(values);
                                        }
                                        }else{
                                            invoiceVO.removeNamedWhereClauseParam("account");
                                        }
                                        if(mapCardGroupListValue!=null)
                                        {
                                        for(int i=0;i< mapCardGroupListValue.size();i++) {
                                                String values="cardGroup"+i;
                                                invoiceVO.removeNamedWhereClauseParam(values);
                                        }
                                        }else{
                                            invoiceVO.removeNamedWhereClauseParam("cardGroup");
                                        }
                                        invoiceVO.setWhereClause("");
                                        invoiceVO.executeQuery();
                                    }

                            }

                            }


//                if ("INSTR(:cardPK,INVOICED_CARD)<>0".equalsIgnoreCase(invoiceVO.getWhereClause())) {
//
//                    invoiceVO.removeNamedWhereClauseParam("cardPK");
//                    invoiceVO.setWhereClause("");
//                    invoiceVO.executeQuery();
//                }else {
//                    if ("INSTR(:cardGroupMainType,CARDGROUP_MAIN_TYPE)<>0 AND INSTR(:cardGroupSubType,CARDGROUP_SUB_TYPE)<>0 AND INSTR(:cardGroupSeqType,CARDGROUP_SEQ)<>0".equalsIgnoreCase(invoiceVO.getWhereClause())) {
//
//                        invoiceVO.removeNamedWhereClauseParam("cardGroupMainType");
//                        invoiceVO.removeNamedWhereClauseParam("cardGroupSubType");
//                        invoiceVO.removeNamedWhereClauseParam("cardGroupSeqType");
//                        invoiceVO.setWhereClause("");
//                        invoiceVO.executeQuery();
//                    }
//                }
//                invoiceVO.setWhereClause("PARTNER_ID =:partnerId AND INSTR(:accountId,ACCOUNT_ID) <> 0 AND INVOICING_DATE >=: fromDateBV AND INVOICING_DATE <=: toDateBV");
                accountQuery="(";
                cardGroupQuery="(";
                cardQuery="(";
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Value of account Id=================>"+populateStringValues(getBindings().getAccount().getValue().toString()));

//                invoiceVO.setNamedWhereClauseParam("accountId",populateStringValues(getBindings().getAccount().getValue().toString()));
                invoiceVO.setNamedWhereClauseParam("countryCode",lang);
                invoiceVO.setNamedWhereClauseParam("partnerId",getBindings().getPartnerNumber().getValue());
                invoiceVO.setNamedWhereClauseParam("fromDateBV",newFromDate);
                invoiceVO.setNamedWhereClauseParam("toDateBV",newToDate);
//                if(getBindings().getInvoiceType().getValue()!=null) {
//                    invoiceVO.defineNamedWhereClauseParam("invoiceType",getBindings().getInvoiceType().getValue(),null);
//                }else {
//                    invoiceVO.defineNamedWhereClauseParam("invoiceType",null,null);
//                }
//                String baseWhereClause=invoiceVO.getWhereClause();


                            if(accountValue.size()>250) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                 " " + "Account Values > 250 ");
                mapAccountListValue=valueList.callValueList(accountValue.size(), accountValue);
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
                                                  " " + "Account Values < 250 ");
                accountQuery="(INSTR(:account,ACCOUNT_ID)<>0 ) ";
            }


                if(getBindings().getCardGpCardList().getValue()!=null) {
                    if("Card".equalsIgnoreCase(getBindings().getCardGpCardList().getValue().toString())) {


                        if(cardValue.size()>250) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                             " " + "Card Values > 250 ");
                            mapCardListValue=valueList.callValueList(cardValue.size(), cardValue);
                                 for(int i=0;i<mapCardListValue.size();i++) {
                                  String values="card"+i;
                                cardQuery=cardQuery+"INSTR(:"+values+",INVOICED_CARD)<>0 OR ";
                                }
                            cardQuery=cardQuery.substring(0, cardQuery.length()-3);
                            cardQuery=cardQuery+")";
//                            "OR (";
//                            for(int i=0;i<mapCardListValue.size();i++) {
//                             String values="card2id"+i;
//                            cardQuery=cardQuery+"INSTR(:"+values+",CARD_2_ID)<>0 OR ";
//                            }
//                            cardQuery=cardQuery.substring(0, cardQuery.length()-3);
//                            cardQuery=cardQuery+") AND ((CARD_ID_2_INFO ='V2' OR CARD_ID_2_INFO ='D' OR CARD_ID_2_INFO ='V') OR CARD_ID_2_INFO IS NULL))";
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() +"CARD Query Values ="+cardQuery);
                            invoiceVO.setWhereClause(accountQuery+"AND "+cardQuery);
                            for(int i=0;i<mapCardListValue.size();i++) {
                            String values="card"+i;
                            String listName="listName"+i;
                            invoiceVO.defineNamedWhereClauseParam(values, mapCardListValue.get(listName),
                                                                               null);
                            }
//                            for(int i=0;i<mapCardListValue.size();i++) {
//                            String values="card2id"+i;
//                            String listName="listName"+i;
//                            invoiceVO.defineNamedWhereClauseParam(values, mapCardListValue.get(listName),
//                                                                               null);
//                            }

                        }else {
                             _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                              " " + "CARD Values < 250 ");
                            mapCardListValue=null;
                            cardQuery="(INSTR(:card,INVOICED_CARD)<>0)";
                            invoiceVO.setWhereClause(accountQuery+"AND "+cardQuery);
                            String cardValuesList=populateStringValues(getBindings().getCard().getValue().toString());
                             invoiceVO.defineNamedWhereClauseParam("card", cardValuesList,null);
                        }
                    }else {

                        if(cardGroupValue.size()>250) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                             " " + "CardGroup Values > 250 ");
                            mapCardGroupListValue=valueList.callValueList(cardGroupValue.size(), cardGroupValue);
                                 for(int i=0;i<mapCardGroupListValue.size();i++) {
                                  String values="cardGroup"+i;
                                cardGroupQuery=cardGroupQuery+"INSTR(:"+values+",CARDGROUP_MAIN_TYPE||CARDGROUP_SUB_TYPE||CARDGROUP_SEQ)<>0 OR ";
                                }
                                 _logger.info(accessDC.getDisplayRecord() + this.getClass() +"CARDGROUP Query Values ="+cardGroupQuery);
                                   cardGroupQuery=cardGroupQuery.substring(0, cardGroupQuery.length()-3);
                                    cardGroupQuery=cardGroupQuery+")";
                            invoiceVO.setWhereClause(accountQuery+"AND "+cardGroupQuery);
                            for(int i=0;i<mapCardGroupListValue.size();i++) {
                            String values="cardGroup"+i;
                            String listName="listName"+i;
                            invoiceVO.defineNamedWhereClauseParam(values, mapCardGroupListValue.get(listName),
                                                                               null);
                            }

                        }else {
                             _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                              " " + "CARD Values < 250 ");
                            mapCardGroupListValue=null;
                            cardGroupQuery="INSTR(:cardGroup,CARDGROUP_MAIN_TYPE||CARDGROUP_SUB_TYPE||CARDGROUP_SEQ)<>0 ";
//                            cardGroupQuery="INSTR(:cardGroupMainType,CARDGROUP_MAIN_TYPE)<>0 AND INSTR(:cardGroupSubType,CARDGROUP_SUB_TYPE)<>0 AND INSTR(:cardGroupSeqType,CARDGROUP_SEQ)<>0";
                            invoiceVO.setWhereClause(accountQuery+"AND "+cardGroupQuery);
                            invoiceVO.defineNamedWhereClauseParam("cardGroup", populateStringValues(getBindings().getCardGroup().getValue().toString()),null);
//                            invoiceVO.defineNamedWhereClauseParam("cardGroup",(cardGroupMaintypePassValue.toString()+cardGroupSubtypePassValues.toString()+cardGroupSeqPassValues.toString()).toString().trim());
//                            invoiceVO.defineNamedWhereClauseParam("cardGroupSubType",cardGroupSubtypePassValues,null);
//                            invoiceVO.defineNamedWhereClauseParam("cardGroupSeqType",cardGroupSeqPassValues,null);
                        }

                    }

                }
                if(accountValue.size()>250) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                     " " + "Account Values > 250 ");
                    mapAccountListValue=valueList.callValueList(accountValue.size(), accountValue);
                    for(int i=0;i<mapAccountListValue.size();i++) {
                    String values="account"+i;
                    String listName="listName"+i;
                    invoiceVO.defineNamedWhereClauseParam(values, mapAccountListValue.get(listName),
                                                                       null);
                    }

                }else {
                     _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                      " " + "Account Values < 250 ");
                     invoiceVO.defineNamedWhereClauseParam("account", populateStringValues(getBindings().getAccount().getValue().toString()),null);
                }
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Query Formed is="+invoiceVO.getQuery());
                invoiceVO.executeQuery();
                session.setAttribute("account_Query_Invoice_overview",accountQuery);
                session.setAttribute("map_Account_List_Invoice_overview",mapAccountListValue);
                session.setAttribute("cardGroup_Query_Invoice_overview",cardGroupQuery);
                session.setAttribute("map_CardGroup_List_Invoice_overview",mapCardGroupListValue);
                session.setAttribute("card_Query_Invoice_overview",cardQuery);
                session.setAttribute("map_Card_List_Invoice_overview",mapCardListValue);
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"Queries are saved in session");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Estimated Row count=="+invoiceVO.getEstimatedRowCount());
                if(invoiceVO.getEstimatedRowCount()>0){
                searchResults=true;
                }
                else{
                searchResults=false;
                    if (resourceBundle.containsKey("NO_RECORDS_FOUND_DRIVER")) {
                        FacesMessage msg =
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                             (String)resourceBundle.getObject("NO_RECORDS_FOUND_DRIVER"),
                                             "");
                        FacesContext.getCurrentInstance().addMessage(null,
                                                                     msg);
                    }
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Where condition:"+invoiceVO.getWhereClause());

            }

        }else {
            searchResults=false;
            if (resourceBundle.containsKey("INVOICE_MANDATORY_CHECK")) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     (String)resourceBundle.getObject("INVOICE_MANDATORY_CHECK"),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null,
                                                             msg);
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting searchResultsListener for Invoices");
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside clearSearchListener for Invoices");
        //this.accountValue=null;
        this.partnerValue = null;
        getBindings().getCardGpCardList().setSubmittedValue(null);
        getBindings().getCardGpCardList().setValue(null);
        getBindings().getFromDate().setValue(null);
        getBindings().getToDate().setValue(null);
        accountList=new ArrayList<SelectItem>();
        accountValue=new ArrayList<String>();
        cardGroupValue=new ArrayList<String>();
        cardGroupList=   new ArrayList<SelectItem>();
        cardValue=new ArrayList<String>();
        cardList=    new ArrayList<SelectItem>();
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

        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting clearSearchListener for Invoices");
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "inside invoiceDetailsCancel for Invoices");
        defaultSelection="Transactions";
        AdfFacesContext.getCurrentInstance().addPartialTarget(radioBtnPopUp);
        ViewObject cardTransactionVO =
            ADFUtils.getViewObject("PrtCardTransactionInvoiceRVO1Iterator");
        if ("INVOICE_NUMBER_COLLECTIVE =:collecInvNo and pals_country_code=:country_code".equalsIgnoreCase(cardTransactionVO.getWhereClause())) {
            cardTransactionVO.removeNamedWhereClauseParam("collecInvNo");
            cardTransactionVO.removeNamedWhereClauseParam("country_code");
            cardTransactionVO.setWhereClause("");
            cardTransactionVO.executeQuery();
        }
        if ("INVOICE_NUMBER_NON_COLLECTIVE =:nonCollecInvNo and pals_country_code=:country_code".equalsIgnoreCase(cardTransactionVO.getWhereClause())) {
            cardTransactionVO.removeNamedWhereClauseParam("nonCollecInvNo");
            cardTransactionVO.removeNamedWhereClauseParam("country_code");
            cardTransactionVO.setWhereClause("");
            cardTransactionVO.executeQuery();
        }
        getBindings().getInvoiceDetails().hide();
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting invoiceDetailsCancel for Invoices");
        return null;
    }

    public String invoiceNumberAction() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside invoiceNumberAction for Invoices");
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


                cardTransactionVO.setWhereClause("INVOICE_NUMBER_NON_COLLECTIVE =:nonCollecInvNo and pals_country_code=:country_code");
                cardTransactionVO.defineNamedWhereClauseParam("nonCollecInvNo",invoiceNumberValue,null);
                cardTransactionVO.defineNamedWhereClauseParam("country_code",lang,null);
            }else {
                if(invoiceGroupingValue.equals("SAM")) {

                    cardTransactionVO.setWhereClause("INVOICE_NUMBER_COLLECTIVE =:collecInvNo and pals_country_code=:country_code");
                    cardTransactionVO.defineNamedWhereClauseParam("collecInvNo",invoiceNumberValue,null);
                    cardTransactionVO.defineNamedWhereClauseParam("country_code",lang,null);
                }
            }
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "cardTransaction Query="+cardTransactionVO.getQuery());
            cardTransactionVO.executeQuery();
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "cardTransactionVO estimatedRow:"+cardTransactionVO.getEstimatedRowCount());
        }
        radioBtnPopUp.setSubmittedValue(null);
        radioBtnPopUp.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(radioBtnPopUp);
        getBindings().getInvoiceDetails().show(new RichPopup.PopupHints());
        isTransactionVisible= false;
        isInvoiceCollectionVisible=false;
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting invoiceNumberAction for Invoices");
        return null;
    }

    public void cgValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside cgValueChangeListener for Invoices");
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
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Value ="+valueChangeEvent.getNewValue());
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting cgValueChangeListener for Invoices");
    }

    public void populateValue(String paramType, String accountNumber){
        if(paramType != null){
            if(paramType.equals("CardGroup")){
//                cardGroupList  = new ArrayList<SelectItem>();
//                cardGroupValue  = new ArrayList<String>();
                popoluateCardCardgroupValues(accountNumber, paramType);
//                if(partnerInfo.getAccountList() != null && partnerInfo.getAccountList().size() > 0){
//                    for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
//                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Account Number inside select one radio button==========>"+partnerInfo.getAccountList().get(i).getAccountNumber());
//                        if(partnerInfo.getAccountList().get(i).getAccountNumber() != null && partnerInfo.getAccountList().get(i).getAccountNumber().equals(getBindings().getAccount().getValue())){
//                            if(partnerInfo.getAccountList().get(i).getCardGroup() != null && partnerInfo.getAccountList().get(i).getCardGroup().size()>0){
//                                for(int k =0 ; k< partnerInfo.getAccountList().get(i).getCardGroup().size(); k++){
//                                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Card Group inside select one radio button==========>"+partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside accountValueChangeListener for Invoices");
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting accountValueChangeListener for Invoices");
    }

    public void partnerValueChangeListener(ValueChangeEvent valueChangeEvent){
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside partnerValueChangeListner for Invoices");
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
            else{
                searchResults=false;
                cGCardVisible=false;
                cardVisible=false;
                cardGroupVisible=false;

                this.partnerValue = null;

                getBindings().getCardGpCardList().setSubmittedValue(null);
                getBindings().getCardGpCardList().setValue(null);
                accountList=new ArrayList<SelectItem>();
                accountValue=new ArrayList<String>();
                cardGroupValue=new ArrayList<String>();
                cardGroupList=   new ArrayList<SelectItem>();
                cardValue=new ArrayList<String>();
                cardList=    new ArrayList<SelectItem>();

                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGpCardList());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
            }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting partnerValueChangeListner for Invoices");
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside getUCMService for Invoices");
        ViewObject invoiceVO =
            ADFUtils.getViewObject("PrtNewInvoiceVO1Iterator");
        PrtNewInvoiceVORowImpl row=(PrtNewInvoiceVORowImpl)invoiceVO.getCurrentRow();
        String invoiceNumberValuePdf = row.getFinalinvoice();
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "invoice number"+invoiceNumberValuePdf);
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "PartnerId "+partnerId);
        byte[] responseByteArr = null;
        Boolean isError=false;
        UCMCustomWeb uCMCustomWeb = null;

       if(session.getAttribute("ucmInvoiceContentList")!=null){
       _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "session is available");
                    try {
                        ucmInvoiceContentList = (HashMap<String,String>)session.getAttribute("ucmInvoiceContentList");
                        String UCMInvoiceContentId = ucmInvoiceContentList.get(invoiceNumberValuePdf);
                        if (UCMInvoiceContentId != null && UCMInvoiceContentId.trim().length() > 0) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "ContentId is available from session");
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
                        _logger.severe(accessDC.getDisplayRecord() + this.getClass()  + " " + ".fileDownload : " + "Exception");
                        e.printStackTrace();
                    }

        }
        else{
           _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "session is null");
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
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Error PDF ="+DAOFactory.getPropertyValue("ERROR_PDF_CID"));
                responseByteArr = uCMCustomWeb.getFileFromUCM(DAOFactory.getPropertyValue(Constants.ENGAGE_UCM_USERNAME), DAOFactory.getPropertyValue(Constants.ENGAGE_UCM_PASSWORD),
                                                                               DAOFactory.getPropertyValue("ERROR_PDF_CID"));
                                              outputStream.write(responseByteArr);
             _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Error while downloading PDF");

            }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting getUCMService for Invoices");
    }

    public String open_popup() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside open_popup(Email functionality) for Invoices");

        successResult = false;
         invoiceNotFound = false;
         failureResult = false;
         //validEmail = false;


        ViewObject invoiceVO =
            ADFUtils.getViewObject("PrtNewInvoiceVO1Iterator");
        PrtNewInvoiceVORowImpl row=(PrtNewInvoiceVORowImpl)invoiceVO.getCurrentRow();
        String invoiceNumberValuePdf = row.getFinalinvoice();
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "invoice number"+invoiceNumberValuePdf);
        //_logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "PartnerId "+partnerId);

        if(invoiceNumberValuePdf != null)
        {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Invoice requested " + invoiceNumberValuePdf);


        ectx = FacesContext.getCurrentInstance().getExternalContext();
                request = (HttpServletRequest)ectx.getRequest();
                session = request.getSession(false);


        session.setAttribute("SESSION_USER_INVOICE_REQ", invoiceNumberValuePdf);


        }

        else
        {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Note able to find requested invoice");


        }


        RichPopup.PopupHints ps = new RichPopup.PopupHints();


        failureResult = false;
        invoiceNotFound = false;
        successResult = false;


        confirmation_mail_popup.show(ps);
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting open_popup(Email functionality) for Invoices");
        return null;
    }

    public byte[] searchGetFile(String invoiceNumber) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass()+ "Inside searchGetFile method");

        byte[] responseByteArr = null;
        Boolean isError=false;
        String ucmContentId;
        UCMCustomWeb uCMCustomWeb = null;

        SearchInputVO searchInputVO = new SearchInputVO();
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "UserName ="+getPropertyValue(Constants.ENGAGE_UCM_USERNAME));
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Password ="+getPropertyValue(Constants.ENGAGE_UCM_PASSWORD));
        searchInputVO.setUsername(getPropertyValue(Constants.ENGAGE_UCM_USERNAME));
        searchInputVO.setPassword(getPropertyValue(Constants.ENGAGE_UCM_PASSWORD));
        searchInputVO.setSourceSystem("WebPortal");

        Property prop[]=new Property[5];

        prop[0]= new Property();
        prop[0].setName("xDocumentNo");
        prop[0].setValue(invoiceNumber.toString().trim());

        prop[1]= new Property();
        prop[1].setName("xPartnerId");
        prop[1].setValue(getBindings().getPartnerNumber().getValue().toString().trim());

        prop[2]= new Property();
        prop[2].setName("xContentType");
        prop[2].setValue("FCP");

        prop[3]= new Property();
        prop[3].setName("xSubType");
        prop[3].setValue("Invoice");

        prop[4]= new Property();
        prop[4].setName("xCountry");
        prop[4].setValue(lang);



//        Property invoiceNo = new Property();
//        invoiceNo.setName("xDocumentNo");
//        invoiceNo.setValue(invoiceNumber.toString().trim());
//        //invoiceNo.setValue("100192878");
//
//        Property partnerId = new Property();
//        partnerId.setName("xPartnerId");
//        partnerId.setValue(getBindings().getPartnerNumber().getValue().toString().trim());
//        //partnerId.setValue("01656214");
//
////        Property docType = new Property();
////        docType.setName("xDocumentType");
////        docType.setValue("PDF");
//
//        Property contentType = new Property();
//        contentType.setName("xContentType");
//        //TODO : To be read from Property file
//        contentType.setValue(DAOFactory.getPropertyValue(Constants.ENGAGE_XCONTENTTYPE));
//        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "ENGAGE_XCONTENTTYPE is " + contentType.getValue());
//
//        Property subType = new Property();
//        subType.setName("xSubType");
//        //TODO : To be read from Property file
//        //subType.setValue("Self_Billing_Print_Reports");
//        //subType.setValue("Invoice");
//        subType.setValue(DAOFactory.getPropertyValue(Constants.ENGAGE_XSUBTYPE));
//        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "ENGAGE_XSUBTYPE is " + subType.getValue());
//
//
//        Property country = new Property();
//        country.setName("xCountry");
//        country.setValue(lang);
//        //country.setValue("DK");


searchInputVO.getSearchResultMetadata().add("dDocTitle");

        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "ENGAGE_UCM_WSDL_URL-------------"+DAOFactory.getPropertyValue(Constants.ENGAGE_UCM_WSDL_URL));

for(int i=0;i<prop.length;i++)
{
        searchInputVO.getSearchInputQueryProperty().add(prop[i]);
}


                try {
                    uCMCustomWeb = new DAOFactory().getUCMService();
                    if (uCMCustomWeb != null) {




                        for(int i=0;i<searchInputVO.getSearchInputQueryProperty().size();i++)
                        {_logger.info(accessDC.getDisplayRecord() + this.getClass() + "UCM input meta tags " +searchInputVO.getSearchInputQueryProperty().get(i).getValue());}
                        List<SearchResultVO> UCMInvoiceContentIdList = uCMCustomWeb.searchDocument(searchInputVO);

                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + "UCM LIST SIZE.get(0):"+UCMInvoiceContentIdList.get(0));// Instead of printing this, print what was the Invoice search criteria
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + "UCM LIST SIZE.get(0):.getSearchResultMetadata.size()  : " +UCMInvoiceContentIdList.get(0).getSearchResultMetadata().size());
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + "content id "+ UCMInvoiceContentIdList.get(0).getContentID());// toString ki wajeh se null pointer aata hai
                        if(UCMInvoiceContentIdList.size()>0)
                        {
                        ucmContentId = UCMInvoiceContentIdList.get(0).getContentID();
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Content id="+ucmContentId);
                        if (ucmContentId != null && ucmContentId.trim().length() > 0) {
                        ucmInvoiceContentList.put(invoiceNumber,ucmContentId);
                        session.setAttribute("ucmInvoiceContentList", ucmInvoiceContentList);
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "get file from ucm");
                            responseByteArr = uCMCustomWeb.getFileFromUCM(DAOFactory.getPropertyValue(Constants.ENGAGE_UCM_USERNAME), DAOFactory.getPropertyValue(Constants.ENGAGE_UCM_PASSWORD),
                                                                ucmContentId);
                        }
                        }else {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Content Id is not avialable in UCM");
                        }
                    }
                } catch (Exception e) {
                    _logger.severe(accessDC.getDisplayRecord() + this.getClass()  + " " + ".fileDownload : " + "Exception");
                    e.printStackTrace();
                }

        _logger.fine(accessDC.getDisplayRecord() + this.getClass()+ "Exiting searchGetFile method");
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "inside radioBtnPopUpVCE for Invoices");
        if(valueChangeEvent !=null && valueChangeEvent.getNewValue()!=null && valueChangeEvent.getNewValue().equals("Transactions")){
            isTransactionVisible=true;
            isInvoiceCollectionVisible=false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(invoiceCollectionPanel);
            AdfFacesContext.getCurrentInstance().addPartialTarget(transactionPanel);
        }
        else{
            isTransactionVisible=false;
            isInvoiceCollectionVisible=true;
            String invoiceNo= collectiveInvoNoOt.getValue().toString();


            ViewObject invoiceDetailVO =ADFUtils.getViewObject("PrtInvoiceDetailVo1Iterator");
            
            if(accountQueryDetail.length()>1) {            
                if(accountQueryDetail.trim().equalsIgnoreCase(invoiceDetailVO.getWhereClause().trim())){
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                " " + "inside  accountdetail query where removal");
                    if(mapAccountDetailListValue!=null)
                    {  
                    for(int i=0;i< mapAccountDetailListValue.size();i++) {
                            String values="account"+i;                            
                            invoiceDetailVO.removeNamedWhereClauseParam(values);
                    }
                    }else{
                        invoiceDetailVO.removeNamedWhereClauseParam("account");
                    }
                    invoiceDetailVO.setWhereClause("");
                    invoiceDetailVO.executeQuery();  
                 }
            }
            
            accountQueryDetail="(";
            invoiceDetailVO.setNamedWhereClauseParam("countryCode",lang);
            invoiceDetailVO.setNamedWhereClauseParam("partnerId",getBindings().getPartnerNumber().getValue());
            invoiceDetailVO.setNamedWhereClauseParam("invoiceNo",invoiceNo);
            
            
            if(accountValue.size()>250) {      
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                 " " + "Account Values > 250 ");
                mapAccountDetailListValue=valueList.callValueList(accountValue.size(), accountValue);         
                     for(int i=0;i<mapAccountDetailListValue.size();i++) {
                      String values="account"+i;
                    accountQueryDetail=accountQueryDetail+"INSTR(:"+values+",ACCOUNT_ID)<>0 OR ";
                    }
                     _logger.info(accessDC.getDisplayRecord() + this.getClass() +"Account Query Values ="+accountQueryDetail);
                       accountQueryDetail=accountQueryDetail.substring(0, accountQueryDetail.length()-3);
                        accountQueryDetail=accountQueryDetail+")";
                        
            }else {
                    mapAccountDetailListValue=null;
                 _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                  " " + "Account Values < 250 ");
                accountQueryDetail="(INSTR(:account,ACCOUNT_ID)<>0 ) ";                 
            }    
            
            invoiceDetailVO.setWhereClause(accountQueryDetail);
            
            if(accountValue.size()>250) {      
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                 " " + "Account Values > 250 ");
                mapAccountDetailListValue=valueList.callValueList(accountValue.size(), accountValue); 
                for(int i=0;i<mapAccountDetailListValue.size();i++) {
                String values="account"+i;
                String listName="listName"+i;
                invoiceDetailVO.defineNamedWhereClauseParam(values, mapAccountDetailListValue.get(listName),
                                                                   null);
                }   
                        
            }else {
                 _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                  " " + "Account Values < 250 ");
                 invoiceDetailVO.defineNamedWhereClauseParam("account", populateStringValues(getBindings().getAccount().getValue().toString()),null);
            }   
                       
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Query Formed for detail is="+invoiceDetailVO.getQuery());
            invoiceDetailVO.executeQuery();
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Estimated Row count of details=="+invoiceDetailVO.getEstimatedRowCount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(transactionPanel);
            AdfFacesContext.getCurrentInstance().addPartialTarget(invoiceCollectionPanel);             
            session.setAttribute("account_Query_Invoice_detail_overview",accountQueryDetail);
            session.setAttribute("map_Account_List_Invoice_detail_overview",mapAccountDetailListValue);
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"Queries are saved in session");
            
        }
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting radioBtnPopUpVCE for Invoices");
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside confirmation_popup_value for Invoices");
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok)
            {
        //
               String mail_result =  triggermail();
               _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Notification of Mail " + mail_result);
               if(mail_result !=null && mail_result.equalsIgnoreCase("success")) {
                   _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Mail send successfully");
                   /*
                   if (resourceBundle.containsKey("INVOICE_TODATE_LESSTHAN")) {
                   //                        FacesMessage msg =
                   //                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                   //                                             (String)resourceBundle.getObject("INVOICE_TODATE_LESSTHAN"),
                   //                                             "");
                       FacesMessage msg =
                           new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "Mail send successfully",
                                            "");
                       FacesContext.getCurrentInstance().addMessage(null,
                                                                    msg);
                   }


*/

                   failureResult = false;
                   invoiceNotFound = false;
                   successResult = true;
//                   mailResult.setVisible(true);
//                   mailResultInvoiceNotFound.setVisible(false);
//                   mailResultFailure.setVisible(false);
//                   AdfFacesContext.getCurrentInstance().addPartialTarget(mailResult);
//                   AdfFacesContext.getCurrentInstance().addPartialTarget(mailResultInvoiceNotFound);
//                   AdfFacesContext.getCurrentInstance().addPartialTarget(mailResultFailure);

               }
                   if(mail_result !=null && mail_result.equalsIgnoreCase("failure")) {

         /*              if (resourceBundle.containsKey("INVOICE_TODATE_LESSTHAN")) {
                       //                        FacesMessage msg =
                       //                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                       //                                             (String)resourceBundle.getObject("INVOICE_TODATE_LESSTHAN"),
                       //                                             "");
                           FacesMessage msg =
                               new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                "Sorry, Mail was not generated, please try after some time",
                                                "");
                           FacesContext.getCurrentInstance().addMessage(null,
                                                                        msg);
                       }  */

         failureResult = true;
         invoiceNotFound = false;
         successResult = true;

//         mailResult.setVisible(false);
//         mailResultInvoiceNotFound.setVisible(false);
//         mailResultFailure.setVisible(true);
//                       AdfFacesContext.getCurrentInstance().addPartialTarget(mailResult);
//                       AdfFacesContext.getCurrentInstance().addPartialTarget(mailResultInvoiceNotFound);
//                       AdfFacesContext.getCurrentInstance().addPartialTarget(mailResultFailure);

                   }
                if(mail_result == null) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Invoice not Found");
                /*
                 if (resourceBundle.containsKey("INVOICE_TODATE_LESSTHAN")) {
                //                        FacesMessage msg =
                //                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                //                                             (String)resourceBundle.getObject("INVOICE_TODATE_LESSTHAN"),
                //                                             "");
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         "Invoice not Found",
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null,
                                                                 msg);
                }*/


                failureResult = false;
                invoiceNotFound = true;
                successResult = false;


//                mailResult.setVisible(false);
//                mailResultInvoiceNotFound.setVisible(true);
//                mailResultFailure.setVisible(false);
//                    AdfFacesContext.getCurrentInstance().addPartialTarget(mailResult);
//                    AdfFacesContext.getCurrentInstance().addPartialTarget(mailResultInvoiceNotFound);
//                    AdfFacesContext.getCurrentInstance().addPartialTarget(mailResultFailure);


                }
            }
        else
        {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " mail cancelled");
        return;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting confirmation_popup_value for Invoices");
    }

    private String getLocalizedString(String Key, String countryCode) {

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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside Trigger Mail");
        DAOFactory daoFactory = new DAOFactory();


        if(session!= null) {
        lang = (String)session.getAttribute(Constants.userLang);
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " lang "+lang);
        }

        String contact_Link=daoFactory.getPropertyValue("CONTACT_STATOIL"+"_"+ conversionUtility.getLangForWERCSURL(mailLnag));
        String engagePortalLink=daoFactory.getPropertyValue("WSPORTAL_LINK"+"_"+ conversionUtility.getLangForWERCSURL(mailLnag));
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Support link in Mail is " + contact_Link);


        boolean sendEmail = false;



        ectx = FacesContext.getCurrentInstance().getExternalContext();
                request = (HttpServletRequest)ectx.getRequest();
                session = request.getSession(false);

        invoice_req = null;

        if (session != null) {
        if(session.getAttribute("SESSION_USER_INVOICE_REQ") != null)
        {
        invoice_req = session.getAttribute("SESSION_USER_INVOICE_REQ").toString();
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " invoice req = " + invoice_req);
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



                  String env = DAOFactory.getPropertyValue("STATOIL_IMAGE_MAIL");
                   //String env = "/u01/WCP_QAT/stores/images/statoil_logo.jpg";
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
        //            "        <td align=\"left\" valign=\"top\" style=\"padding:5px;\"><img src=\"images/_loggero.png\" width=\"298\" height=\"67\" style=\"display:block;\"></td>\n" +
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
                  //  "<i>  Dear Customer, <br><br>" +
                //"<i>  Dear " + first_name + ",<br><br>" +
                getLocalizedString("ENCLOSED", mailLnag) +
                //"<a href=" + engagePortalLink +"><font Color=\"#F89518\">"+ getLocalizedString("ENGAGE_PORTAL", lang)+ "</font></a>"+

                              "."+ getLocalizedString("HESITATE", mailLnag) +"<br>" +
                getLocalizedString("AUTOGENERATED", mailLnag) + "<br>"+
                getLocalizedString("CONTACTDETAILS", mailLnag) +" "+ "<a href=" + contact_Link + "><font Color=\"#F89518\">"+getLocalizedString("HERE", mailLnag)+"</font></a>"+
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
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "ContentId is available from session");
                    uCMCustomWeb = new DAOFactory().getUCMService();
                    responseByteArr = uCMCustomWeb.getFileFromUCM(DAOFactory.getPropertyValue(Constants.UCM_USERNAME), DAOFactory.getPropertyValue(Constants.UCM_PASSWORD),
                                                        UCMInvoiceContentId);
                    if (responseByteArr == null || responseByteArr.length == 0) {
    //                        isError = true;
                        sendEmail = false;
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Error");
                    }
                    else {
                        sendEmail = true;
                    }
                }
                else {
                    responseByteArr = searchGetFile(invoice_req);
                    if(responseByteArr!=null && responseByteArr.length!=0) {
                     _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Response byte array length " + responseByteArr.length);
                        sendEmail = true;
                    }else
                    {
                    //isError = true;
                    sendEmail = false;
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Eoorororoo");
                    }

                }
            }
                else{
                   _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "session is null");
                     responseByteArr=searchGetFile(invoice_req);
                   if(responseByteArr!=null && responseByteArr.length!=0) {
                       _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Response byte array length " + responseByteArr.length);
                       sendEmail = true;
                   }else {
                       sendEmail=false;
                   _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Eoorororoodddd");
                   }

                }





            } catch (Exception e) {
                _logger.severe(accessDC.getDisplayRecord() + this.getClass() + "fileDownload : " + "Exception");
                e.printStackTrace();
            }









            try{
                if(sendEmail)
                {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " sending email" + sendEmail +" to " + email_recipient_popup.getValue().toString() + "for invoice " + invoice_req +"having byte array size as"+ responseByteArr.length);
            emailutility.sendEmail("no-reply.SFR-Services@statoilfuelretail.com",
            email_recipient_popup.getValue().toString(),
             "Statoilfuelretail : Invoice Delivery", email2, "smtp", "smtp.statoilfuelretail.com",cc,responseByteArr,env,invoice_req);

            return "success";
                }
                else {

                    _logger.severe(accessDC.getDisplayRecord() + this.getClass() + " Throw adf message of mail can not be send");


                    _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting Trigger Mail()");
                    return null;
                }

                }
            catch(Exception e) {
                _logger.severe(accessDC.getDisplayRecord() + this.getClass() + " Error in mail");
                _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting Trigger Mail()");
                e.printStackTrace();
                return "failure";

            }






    }
    public void setTo_recipient(String to_recipient) {
        this.to_recipient = to_recipient;
    }

    public String getTo_recipient() {

        return to_recipient;
    }

    public void setEmail_recipient_popup(RichInputText email_recipient_popup) {

        if(setreceipent){
    if (session != null) {
            if (null != session.getAttribute(Constants.SESSION_USER_INFO)) {
                global_user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
                if(global_user.getEmailID()!= null) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() +" Invoice bean : "+"user email id in my profile bean " + global_user.getEmailID());
                                    email_recipient_popup.setValue(global_user.getEmailID().trim());

                }else
                {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Invoice bean : "+"user first name in my profile bean " + global_user.getFirstName());
                email_recipient_popup.setValue(global_user.getFirstName().toString().trim());
                }
                }


            }
            else
            {
                email_recipient_popup.setValue("");
            }
        setreceipent = false;


    }





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
        this.spacerFetchUserEmail = spacerFetchUserEmail;
    }

    public RichSpacer getSpacerFetchUserEmail() {


        return spacerFetchUserEmail;
    }

    public void setMailRecipient(String mailRecipient) {

        this.mailRecipient = mailRecipient;
    }

    public String getMailRecipient() {

        return mailRecipient;
    }

    public void setAccountQuery(String accountQuery) {
        this.accountQuery = accountQuery;
    }

    public String getAccountQuery() {
        return accountQuery;
    }

    public void setCardGroupQuery(String cardGroupQuery) {
        this.cardGroupQuery = cardGroupQuery;
    }

    public String getCardGroupQuery() {
        return cardGroupQuery;
    }

    public void setCardQuery(String cardQuery) {
        this.cardQuery = cardQuery;
    }

    public String getCardQuery() {
        return cardQuery;
    }

    public void setMapAccountListValue(Map<String, String> mapAccountListValue) {
        this.mapAccountListValue = mapAccountListValue;
    }

    public Map<String, String> getMapAccountListValue() {
        return mapAccountListValue;
    }

    public void setMapCardGroupListValue(Map<String, String> mapCardGroupListValue) {
        this.mapCardGroupListValue = mapCardGroupListValue;
    }

    public Map<String, String> getMapCardGroupListValue() {
        return mapCardGroupListValue;
    }

    public void setMapCardListValue(Map<String, String> mapCardListValue) {
        this.mapCardListValue = mapCardListValue;
    }

    public Map<String, String> getMapCardListValue() {
        return mapCardListValue;
    }

    public void setMailResult(RichOutputText mailResult) {
        this.mailResult = mailResult;
    }

    public RichOutputText getMailResult() {
        return mailResult;
    }

    public void setMailResultInvoiceNotFound(RichOutputText mailResultInvoiceNotFound) {
        this.mailResultInvoiceNotFound = mailResultInvoiceNotFound;
    }

    public RichOutputText getMailResultInvoiceNotFound() {
        return mailResultInvoiceNotFound;
    }

    public void setMailResultFailure(RichOutputText mailResultFailure) {
        this.mailResultFailure = mailResultFailure;
    }

    public RichOutputText getMailResultFailure() {
        return mailResultFailure;
    }

    public void setInvoiceNotFound(boolean invoiceNotFound) {
        this.invoiceNotFound = invoiceNotFound;
    }

    public boolean isInvoiceNotFound() {
        return invoiceNotFound;
    }

    public void setSuccessResult(boolean successResult) {
        this.successResult = successResult;
    }

    public boolean isSuccessResult() {
        return successResult;
    }

    public void setFailureResult(boolean failureResult) {
        this.failureResult = failureResult;
    }

    public boolean isFailureResult() {
        return failureResult;
    }



    public void triggerMailProcess(ActionEvent actionEvent) {
        // Add event code here...




        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Entering triggerMailProcess");

//        Validations validObj = new Validations();
//        boolean validemail = validObj.validateEmail(email_recipient_popup.getValue().toString());
//        if(validemail)
//        {
       // validEmail = false;
        failureResult = false;
        invoiceNotFound = false;
        successResult = false;
        //
               String mail_result =  triggermail();
                  _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Notification of Mail " + mail_result);
               if(mail_result !=null && mail_result.equalsIgnoreCase("success")) {
                      _logger.info(accessDC.getDisplayRecord() + this.getClass() +" Mail send successfully");
                   /*
                   if (resourceBundle.containsKey("INVOICE_TODATE_LESSTHAN")) {
                   //                        FacesMessage msg =
                   //                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                   //                                             (String)resourceBundle.getObject("INVOICE_TODATE_LESSTHAN"),
                   //                                             "");
                       FacesMessage msg =
                           new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "Mail send successfully",
                                            "");
                       FacesContext.getCurrentInstance().addMessage(null,
                                                                    msg);
                   }


        */

                   failureResult = false;
                   invoiceNotFound = false;
                   successResult = true;
                 //  validEmail = false;
        //                   mailResult.setVisible(true);
        //                   mailResultInvoiceNotFound.setVisible(false);
        //                   mailResultFailure.setVisible(false);
        //                   AdfFacesContext.getCurrentInstance().addPartialTarget(mailResult);
        //                   AdfFacesContext.getCurrentInstance().addPartialTarget(mailResultInvoiceNotFound);
        //                   AdfFacesContext.getCurrentInstance().addPartialTarget(mailResultFailure);

               }
                   if(mail_result !=null && mail_result.equalsIgnoreCase("failure")) {
                       _logger.severe(accessDC.getDisplayRecord() + this.getClass() + " Mail was not generated");
         /*              if (resourceBundle.containsKey("INVOICE_TODATE_LESSTHAN")) {
                       //                        FacesMessage msg =
                       //                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                       //                                             (String)resourceBundle.getObject("INVOICE_TODATE_LESSTHAN"),
                       //                                             "");
                           FacesMessage msg =
                               new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                "Sorry, Mail was not generated, please try after some time",
                                                "");
                           FacesContext.getCurrentInstance().addMessage(null,
                                                                        msg);
                       }  */

         failureResult = true;
         invoiceNotFound = false;
         successResult = true;
                      // validEmail = false;

        //         mailResult.setVisible(false);
        //         mailResultInvoiceNotFound.setVisible(false);
        //         mailResultFailure.setVisible(true);
        //                       AdfFacesContext.getCurrentInstance().addPartialTarget(mailResult);
        //                       AdfFacesContext.getCurrentInstance().addPartialTarget(mailResultInvoiceNotFound);
        //                       AdfFacesContext.getCurrentInstance().addPartialTarget(mailResultFailure);

                   }
                if(mail_result == null) {
                    _logger.severe(accessDC.getDisplayRecord() + this.getClass() + " Invoice not Found");




                failureResult = false;
                invoiceNotFound = true;
                successResult = false;
                //validEmail = false;




                }

                //_logger.info(accessDC.getDisplayRecord() + this.getClass() + "Result is " + failureResult + " " + invoiceNotFound + " " + successResult);


   // }
//        else {
//
//            validEmail = true;
//            failureResult = false;
//            invoiceNotFound = false;
//            successResult = false;
//
//        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting ntering triggerMailProcess");

    }

    public void setMailLnag(String mailLnag) {
        this.mailLnag = mailLnag;
    }

    public String getMailLnag() {
        return mailLnag;
    }

    public void setSetreceipent(boolean setreceipent) {
        this.setreceipent = setreceipent;
    }

    public boolean isSetreceipent() {
        return setreceipent;
    }

    public void closeEmailPopup(ActionEvent actionEvent) {
        // Add event code here...
        getConfirmation_mail_popup().hide();
    }

    public void setResultPanel(RichPanelGroupLayout resultPanel) {
        this.resultPanel = resultPanel;
    }

    public RichPanelGroupLayout getResultPanel() {
        return resultPanel;
    }

    public void setValidEmail(boolean validEmail) {
        this.validEmail = validEmail;
    }

    public boolean isValidEmail() {
        return validEmail;
    }

    public void resetResults(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        //System.out.println("value change listner");
        successResult = false;
        invoiceNotFound  = false;
        failureResult  = false;
        //validEmail  = false;
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
            if(resultFromTo)
            {

            Date dateNow = new java.util.Date();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(dateNow);
            gc.add(GregorianCalendar.MONTH, -1);
            Date dateBefore = gc.getTime();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
            String tmp = dateformat.format(dateBefore);
            fromDate.setValue(tmp);
                resultFromTo=false;
            }

            this.fromDate = fromDate;


        }

        public RichInputDate getFromDate() {
            return fromDate;
        }

        public void setToDate(RichInputDate toDate) {
            if(resultToFrom)
            {

            Date dateNow = new java.util.Date();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
            String tmp = dateformat.format(dateNow);
            toDate.setValue(tmp);
            resultToFrom=false;
            }
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

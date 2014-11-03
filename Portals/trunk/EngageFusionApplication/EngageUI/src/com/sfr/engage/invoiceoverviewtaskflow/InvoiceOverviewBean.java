package com.sfr.engage.invoiceoverviewtaskflow;


import com.sfr.core.bean.EngageEmaiUtilityl;
import com.sfr.core.bean.User;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.core.ReportBundle;
import com.sfr.engage.core.ValueListSplit;
import com.sfr.engage.model.queries.rvo.PrtCardTransactionInvoiceRVORowImpl;
import com.sfr.engage.model.queries.rvo.PrtExportInfoRVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtNewInvoiceCardVORowImpl;
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
import java.io.PrintWriter;
import java.io.Serializable;

import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
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
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectManyShuttle;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.component.rich.output.RichSpacer;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.BindingContainer;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class InvoiceOverviewBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    private List<String> accountValue;
    private List<String> cardGroupValue;
    private String invoiceTypeValue;
    private List<String> cardValue;
    private List<SelectItem> accountList = null;
    private List<SelectItem> cardGroupList = null;
    private List<SelectItem> cardList = null;
    private boolean searchResults = false;
    private boolean cardGroupVisible = false;
    private boolean cardVisible = false;
    private boolean cGCardVisible = false;
    private String countryCode;
    private ResourceBundle resourceBundle;
    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    private String cardGroupSubtypePassValues = "";
    private String cardGroupMaintypePassValue = "";
    private String cardGroupSeqPassValues = "";
    private Conversion conversionUtility;
    private Locale locale;
    private String partnerId;
    private String currencyCode;
    private String lang;
    private String invoiceNumberPdfValue;
    private Map<String, String> ucmInvoiceContentList = new HashMap<String, String>();
    private AccessDataControl accessDC = new AccessDataControl();
    private List<PartnerInfo> partnerInfoList;
    private List<SelectItem> partnerList = null;
    private List<String> partnerValue = null;
    private String defaultSelection = Constants.TRANSACTIONS__LITERAL;
    private boolean isTransactionVisible = true;
    private boolean isInvoiceCollectionVisible = false;
    private String toRecipient = null;
    private User globalUser = new User();
    private String partnerReq;
    private RichSpacer spacerFetchUserEmail;
    private String mailRecipient;
    private EngageEmaiUtilityl emailutility;
    private String accountQueryDetail = "(";
    private String accountQuery = "(";
    private String cardGroupQuery = "(";
    private String cardQuery = "(";
    private String generalQuery = "";
    private Map<String, String> mapAccountDetailListValue;
    private Map<String, String> mapAccountListValue;
    private Map<String, String> mapCardGroupListValue;
    private Map<String, String> mapCardListValue;
    public static final ADFLogger LOGGER = AccessDataControl.getSFRLogger();
    private RichOutputText mailResult;
    private RichOutputText mailResultInvoiceNotFound;
    private RichOutputText mailResultFailure;
    private boolean invoiceNotFound;
    private boolean successResult;
    private boolean failureResult;
    private boolean validEmail;
    private boolean resultFromTo = true;
    private boolean resultToFrom = true;
    private String mailLnag = "";
    private boolean setreceipent = true;
    private RichPanelGroupLayout resultPanel;
    private boolean shuttleStatus = false;
    private boolean shuttleStatusTransaction = true;
    private String strInvoicesPrepopulatedColumns = "";
    private String strInvoicesTotalColumns = "";
    private String strInvoicesExtraColumns = "";
    private String strTransactionPrepopulatedColumns = "";
    private String strTransactionTotalColumns = "";
    private String strTransactionExtraColumns = "";
    private List shuttleValue;
    private List shuttleValueTransaction;
    private List shuttleList = new ArrayList();
    private List shuttleListTransaction = new ArrayList();
    private String contentType;
    private String fileName;
    private String cardGroupRadio = Constants.CARD_GROUP_LITERAL;
    private String contentTypeTransaction;
    private String transactionFileNamefileName;
    private String transactionStandardTransaction;
    private String transactionStandardExtraTransaction;
    private boolean showNonCollectiveInvoicePanel = false;
    private String invoiceType;
    private boolean isCardGroup=true;
    private boolean isCard=false;
    private final int minusOne = -1;
    private static final String PRTNEWINVOICEVO1ITERATORLITRERAL = "PrtNewInvoiceVO1Iterator";
    private static final String PRTNEWINVOICECARDVO1ITERATORLITRERAL = "PrtNewInvoiceCardVO1Iterator";
    private static final String PRTCARDTRANSACTIONINVOICERVO1ITERATORLITRERAL = "PrtCardTransactionInvoiceRVO1Iterator";
    private static final String PRTEXPORTINFORVO1ITERATORLITRERAL = "PrtExportInfoRVO1Iterator";
    private static final String SELECT_CRITERIALITRERAL = "select_Criteria";
    private static final String ENGAGENOTEALLPRICESBELOWAREINLITRERAL = "ENGAGE_NOTE_ALL_PRICES_BELOW_ARE_IN";
    private static final String TRANSACTIONREPORTLITRERAL = "Transaction_Report.csv";
    private static final String CSV2LITRERAL = "csv2";
    private static final String PRINTINGDATALITRERAL = "Printing Data";
    private static final String LANGREPORTLITRERAL = "langReport";
    private static final String TRANSACTIONSPECIFICERRORDBLITRERAL = "TRANSACTION_SPECIFIC_ERROR_DB";
    private static final String INVOICECONTENTLISTLITRERAL = "ucmInvoiceContentList";
    private String accountQuery2 = "(";
    private String generalQuery2 = "";
    private Map<String, String> mapAccountListValue2;
    private String popUpInvoice = null;
    private String popUpInvoiceDate = null;
    private String popUpInvoiceNet = null;
    private String popUpInvoiceVat = null;
    private String popUpInvoiceGross = null;


    public InvoiceOverviewBean() {
        super();
        emailbean email = new emailbean();
        conversionUtility = new Conversion();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        partnerList = new ArrayList<SelectItem>();
        partnerValue = new ArrayList<String>();
        accountList = new ArrayList<SelectItem>();
        accountValue = new ArrayList<String>();
        emailutility = new EngageEmaiUtilityl();
        cardGroupList = new ArrayList<SelectItem>();
        cardGroupValue = new ArrayList<String>();
        cardValue = new ArrayList<String>();
        cGCardVisible = true;
        invoiceType = null;
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Constructor of Invoice overview bean");

        if (session.getAttribute("lang") != null) {
            mailLnag = (String)session.getAttribute("lang");
        }

        resetValues();

        if (session != null) {
            lang = (String)session.getAttribute(Constants.userLang);
        }

        currencyCode = conversionUtility.getCurrencyCode(lang);
        locale = conversionUtility.getLocaleFromCountryCode(lang);

        if (session != null) {
            if (session.getAttribute("account_Query_Invoice_overview") != null) {
                accountQuery = session.getAttribute("account_Query_Invoice_overview").toString().trim();
                mapAccountListValue = (Map<String, String>)session.getAttribute("map_Account_List_Invoice_overview");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account Query & mapAccountList is found");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account " + accountQuery);
            }
            
            if (session.getAttribute("account_Query_Card_Invoice_overview") != null) {
                accountQuery2 = session.getAttribute("account_Query_Card_Invoice_overview").toString().trim();
                mapAccountListValue2 = (Map<String, String>)session.getAttribute("map_Account_List_Card_Invoice_overview");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account Query2 & mapAccountList2 is found");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account2 " + accountQuery2);
            }
            
            if (session.getAttribute("generalQuery_Invoice_overview") != null) {
                generalQuery = session.getAttribute("generalQuery_Invoice_overview").toString().trim();
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "generalQuery is found");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "generalQuery " + generalQuery);
            } 
            
            if (session.getAttribute("generalQuery_Card_Invoice_overview") != null) {
                generalQuery2 = session.getAttribute("generalQuery_Card_Invoice_overview").toString().trim();
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "generalQuery2 is found");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "generalQuery2 " + generalQuery2);
            } 
            
            if (session.getAttribute("account_Query_Invoice_detail_overview") != null) {
                accountQueryDetail = session.getAttribute("account_Query_Invoice_detail_overview").toString().trim();
                mapAccountDetailListValue = (Map<String, String>)session.getAttribute("map_Account_List_Invoice_detail_overview");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account Detail Query & mapAccountList is found");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account " + accountQueryDetail);
            }
            if (session.getAttribute("cardGroup_Query_Invoice_overview") != null) {
                cardGroupQuery = session.getAttribute("cardGroup_Query_Invoice_overview").toString().trim();
                mapCardGroupListValue = (Map<String, String>)session.getAttribute("map_CardGroup_List_Invoice_overview");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Query & mapCardGroupList is found");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup " + cardGroupQuery);
            }
            if (session.getAttribute("card_Query_Invoice_overview") != null) {
                cardQuery = session.getAttribute("card_Query_Invoice_overview").toString().trim();
                mapCardListValue = (Map<String, String>)session.getAttribute("map_Card_List_Invoice_overview");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "card Query & mapCardList is found");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card " + cardQuery);
            }

        }
        
        Date dateNow = new java.util.Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dateNow);
        gc.add(GregorianCalendar.MONTH, minusOne);
        Date dateBefore = gc.getTime();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yy");
        String tmpFrom = dateformat.format(dateBefore);
        String tmpTo = dateformat.format(dateNow);
        
        removeWhereClause(); 
        long estimatedRowCount =
            applyWhereClause(true,tmpFrom, tmpTo); 


                    if (estimatedRowCount > 0) {
                        searchResults = true;
                        isCardGroup=true;
                    } else {
                        searchResults = false;
                    }
            
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting constructor for invoice overview bean");
    }

    private Comparator<SelectItem> comparator = new Comparator<SelectItem>() {
        @Override
        public int compare(SelectItem s1, SelectItem s2) {
            return s1.getLabel().compareTo(s2.getLabel());
        }
    };

    public List<SelectItem> getAccountList() {
        return accountList;
    }

    public List<SelectItem> getCardGroupList() {
        return cardGroupList;
    }

    public List<SelectItem> getCardList() {
        return cardList;
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

    public void populateCardGroupValues(String cardGrpVar) {
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "PassedcardGrpVar =" + cardGrpVar);
        String[] cardGroupvalues;
        int cardGroupCount = 0;

        String cardGroupMaintype = "";
        String cardGroupSubtype = "";
        String cardGroupSeq = "";


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

            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroupMainType =" + cardGroupMaintype);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "cardGroupSubtype =" + cardGroupSubtype);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "cardGroupSeq =" + cardGroupSeq);

            cardGroupMaintypePassValue = cardGroupMaintype.trim().substring(0, cardGroupMaintype.length() - 1);
            cardGroupSubtypePassValues = cardGroupSubtype.trim().substring(0, cardGroupSubtype.length() - 1);
            cardGroupSeqPassValues = cardGroupSeq.trim().substring(0, cardGroupSeq.length() - 1);
        }
    }

    public void searchResultsListener(ActionEvent actionEvent) {
        resourceBundle = new EngageResourceBundle();
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside searchResultsListener for Invoices");
        displayErrorComponent(getBindings().getPartnerNumber(), false);
        displayErrorComponent(getBindings().getAccount(), false);
        displayErrorComponent(getBindings().getCardGroup(), false);

        String newFromDate = null;
        String newToDate = null;
        if (getBindings().getPartnerNumber().getValue() != null && getBindings().getAccount().getValue() != null &&
            getBindings().getFromDate().getValue() != null && getBindings().getToDate().getValue() != null &&
            getBindings().getCardGpCardList().getValue() != null && (getBindings().getCardGroup().getValue() != null)) {
            if (getBindings().getCard().getValue() != null) {


                displayErrorComponent(getBindings().getCard(), false);


                DateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
                Date localFromDate = (java.util.Date)getBindings().getFromDate().getValue();
                Date localToDate = (java.util.Date)getBindings().getToDate().getValue();
                newFromDate = sdf.format(localFromDate);
                newToDate = sdf.format(localToDate);

                if (localToDate.before(localFromDate)) {
                    if (resourceBundle.containsKey("INVOICE_TODATE_LESSTHAN")) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("INVOICE_TODATE_LESSTHAN"), "");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }


                else {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "PartnerValue=" + getBindings().getPartnerNumber().getValue());
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "AccountValue=" + getBindings().getAccount().getValue());
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "getCardGpCardList=" + getBindings().getCardGpCardList().getValue());
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "getCardGroup=" + getBindings().getCardGroup().getValue());
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "getCard=" + getBindings().getCard().getValue());
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "ToDate=" + getBindings().getToDate().getValue());
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "FromDate =" + newFromDate + "To Date = " + newToDate);
                    
                    
                    if(isCardGroup)  {   
                    ViewObject invoiceVO = ADFUtils.getViewObject(PRTNEWINVOICEVO1ITERATORLITRERAL);
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Before Query=" + invoiceVO.getQuery());                   
                    
                                   
                    removeWhereClause();

                    resetTableFilter();
                    long estimatedRowCount =
                        applyWhereClause(false, newFromDate, newToDate); 

                    session.setAttribute("account_Query_Invoice_overview", accountQuery);
                    session.setAttribute("generalQuery_Invoice_overview", generalQuery);
                    session.setAttribute("map_Account_List_Invoice_overview", mapAccountListValue);
                    session.setAttribute("cardGroup_Query_Invoice_overview", cardGroupQuery);
                    session.setAttribute("map_CardGroup_List_Invoice_overview", mapCardGroupListValue);
                    
                    
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Queries are saved in session");
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Estimated Row count==" + invoiceVO.getEstimatedRowCount());
                    if (estimatedRowCount > 0) {
                        searchResults = true;
                        isCardGroup=true;
                        isCard=false;
                        
                    } else {
                        searchResults = false;
                        
                        if (resourceBundle.containsKey("NO_RECORDS_FOUND_DRIVER")) {
                            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("NO_RECORDS_FOUND_DRIVER"), "");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                        
                       
                    }
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Where condition:" + invoiceVO.getWhereClause());
                    }
                    
                    else{
                      
                        ViewObject invoiceCardVO = ADFUtils.getViewObject(PRTNEWINVOICECARDVO1ITERATORLITRERAL);
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Before Query=" + invoiceCardVO.getQuery());                   
                        
                                       
                        removeWhereClauseForCardInvoice();

                        resetTableFilter();
                        long estimatedRowCount =
                            applyWhereClauseForCardInvoice(false, newFromDate, newToDate); 

                        session.setAttribute("account_Query_Card_Invoice_overview", accountQuery2);
                        session.setAttribute("generalQuery_Card_Invoice_overview", generalQuery2);
                        session.setAttribute("map_Account_List_Card_Invoice_overview", mapAccountListValue2);                      
                        session.setAttribute("card_Query_Invoice_overview", cardQuery);
                        session.setAttribute("map_Card_List_Invoice_overview", mapCardListValue);
                        
                        
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Queries are saved in session");
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Estimated Row count==" + invoiceCardVO.getEstimatedRowCount());
                        if (estimatedRowCount > 0) {
                            searchResults = true;
                            isCard=true;
                            isCardGroup=false;
                            
                        } else {
                            searchResults = false;
                                              
                            if (resourceBundle.containsKey("NO_RECORDS_FOUND_DRIVER")) {
                                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("NO_RECORDS_FOUND_DRIVER"), "");
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                            }
                           
                        }
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Where condition:" + invoiceCardVO.getWhereClause());                        
                        
                        
                        
                        
                    }
                }
            } else {
                if (getBindings().getCard().getValue() == null) {
                    displayErrorComponent(getBindings().getCard(), true);

                    searchResults = false;
                    if (resourceBundle.containsKey("INVOICE_MANDATORY_CHECK")) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("INVOICE_MANDATORY_CHECK"), "");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }

            }
        } else {


            if (getBindings().getPartnerNumber().getValue() == null) {
                displayErrorComponent(getBindings().getPartnerNumber(), true);
            }

            if (getBindings().getAccount().getValue() == null) {
                displayErrorComponent(getBindings().getAccount(), true);
            }

            if (getBindings().getCardGroup().getValue() == null) {
                displayErrorComponent(getBindings().getCardGroup(), true);
            }


            searchResults = false;
            if (resourceBundle.containsKey("INVOICE_MANDATORY_CHECK")) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("INVOICE_MANDATORY_CHECK"), "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting searchResultsListener for Invoices");
    }

    private Long applyWhereClause( boolean onPageLoad,String newFromDate, String newToDate) {
        String accountTemp = "";
        String cgTemp = "";
        String partnerList = "";
        partnerList = populateStringValues(getPartnerValue().toString());
        ViewObject invoiceVO = ADFUtils.getViewObject(PRTNEWINVOICEVO1ITERATORLITRERAL);
        accountQuery = "(";
        cardGroupQuery = "(";
      
        if (onPageLoad) {
            accountTemp = populateStringValues(getAccountValue().toString());
            cgTemp = populateStringValues(getCardGroupValue().toString());
            
        } else {
            accountTemp = populateStringValues(getBindings().getAccount().getValue().toString());
            cgTemp = populateStringValues(getBindings().getCardGroup().getValue().toString());
            
        }
        
        generalQuery= "INSTR(:partner_id,partner_id)<>0  AND COUNTRY_CODE =:ccode AND INVOICING_DATE >=:fromDateBV AND INVOICING_DATE <=:toDateBV AND invoice_level is not null";
        invoiceVO.defineNamedWhereClauseParam("ccode", lang,null);
        invoiceVO.defineNamedWhereClauseParam("partner_id",partnerList,null);
        invoiceVO.defineNamedWhereClauseParam("fromDateBV", newFromDate,null);
        invoiceVO.defineNamedWhereClauseParam("toDateBV", newToDate,null);

        if (accountValue.size() > Constants.ONEFIFTY) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
            mapAccountListValue = ValueListSplit.callValueList(accountValue.size(), accountValue);
            for (int i = 0; i < mapAccountListValue.size(); i++) {
                String values = Constants.ACCOUNT_LITERAL + i;
                accountQuery = accountQuery + "INSTR(:" + values + ",ACCOUNT_ID)<>0 OR ";
            }
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Account Query Values =" + accountQuery);
            accountQuery = accountQuery.substring(0, accountQuery.length() - Constants.THREE);
            accountQuery = accountQuery + ")";

        } else {
            mapAccountListValue = null;
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
            accountQuery = "(INSTR(:account,ACCOUNT_ID)<>0 ) ";
        }
         
                if (cardGroupValue.size() > Constants.ONEFIFTY) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values > 150 ");
                    mapCardGroupListValue = ValueListSplit.callValueList(cardGroupValue.size(), cardGroupValue);
                    for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                        String values = Constants.CARDGROUPLITERAL + i;
                        cardGroupQuery =
                                cardGroupQuery + "INSTR(:" + values + ",PARTNER_ID||CARDGROUP_MAIN_TYPE||CARDGROUP_SUB_TYPE||CARDGROUP_SEQ)<>0 OR ";
                    }
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "CARDGROUP Query Values =" + cardGroupQuery);
                    cardGroupQuery = cardGroupQuery.substring(0, cardGroupQuery.length() - Constants.THREE);
                    cardGroupQuery = cardGroupQuery + ")";
                    invoiceVO.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + generalQuery);
                    for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                        invoiceVO.defineNamedWhereClauseParam(Constants.CARDGROUPLITERAL + i,
                                                              mapCardGroupListValue.get(Constants.LISTNAME_LITERAL + i), null);
                    }

                } else {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CARD Values < 150 ");
                    mapCardGroupListValue = null;
                    cardGroupQuery = "INSTR(:cardGroup,PARTNER_ID||CARDGROUP_MAIN_TYPE||CARDGROUP_SUB_TYPE||CARDGROUP_SEQ)<>0 ";
                    invoiceVO.setWhereClause(accountQuery + "AND " + cardGroupQuery + "AND " + generalQuery);
                    invoiceVO.defineNamedWhereClauseParam(Constants.CARDGROUPLITERAL,cgTemp, null);

                }
         
        if (accountValue.size() > Constants.ONEFIFTY) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
            mapAccountListValue = ValueListSplit.callValueList(accountValue.size(), accountValue);
            for (int i = 0; i < mapAccountListValue.size(); i++) {

                invoiceVO.defineNamedWhereClauseParam(Constants.ACCOUNT_LITERAL + i, mapAccountListValue.get(Constants.LISTNAME_LITERAL + i),
                                                      null);
            }

        } else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
            invoiceVO.defineNamedWhereClauseParam(Constants.ACCOUNT_LITERAL,accountTemp, null);
        }
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Query Formed is=" + invoiceVO.getQuery());
        invoiceVO.executeQuery();
        
        System.out.println("estimatedRowCount in ApplyWhereCaluse:::"+invoiceVO.getEstimatedRowCount());
        
        return invoiceVO.getEstimatedRowCount();
    }


    private void removeWhereClause() {
        System.out.println("Entering the Invoice removeWhereClause::::::::::::");
        ViewObject invoiceVO = ADFUtils.getViewObject(PRTNEWINVOICEVO1ITERATORLITRERAL);
        
            if (cardGroupQuery.length() > 1 && cardGroupQuery != null) {

                if (((accountQuery + "AND " + cardGroupQuery + "AND " + generalQuery).trim().equalsIgnoreCase(invoiceVO.getWhereClause())) ||
                    ((accountQuery + " AND " + cardGroupQuery + "AND " + generalQuery).trim().equalsIgnoreCase(invoiceVO.getWhereClause()))) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                "inside  cardGroup with out purchase code where removal class");
                    if (mapAccountListValue != null) {
                        for (int i = 0; i < mapAccountListValue.size(); i++) {

                            invoiceVO.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL + i);
                        }
                    } else {
                        invoiceVO.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL);
                    }
                    if (mapCardGroupListValue != null) {
                        for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                            invoiceVO.removeNamedWhereClauseParam(Constants.CARDGROUPLITERAL + i);
                        }
                    } else {
                        invoiceVO.removeNamedWhereClauseParam(Constants.CARDGROUPLITERAL);
                    }
                    invoiceVO.removeNamedWhereClauseParam("ccode");
                    invoiceVO.removeNamedWhereClauseParam("partner_id");
                    invoiceVO.removeNamedWhereClauseParam("fromDateBV");
                    invoiceVO.removeNamedWhereClauseParam("toDateBV");
                    invoiceVO.setWhereClause("");
                    invoiceVO.executeQuery();
                }
            }

       
        
    }
    
    private void resetValues() {

        partnerList = new ArrayList<SelectItem>();
        partnerValue = new ArrayList<String>();
        accountList = new ArrayList<SelectItem>();
        accountValue = new ArrayList<String>();
        emailutility = new EngageEmaiUtilityl();
        cardGroupList = new ArrayList<SelectItem>();
        cardGroupValue = new ArrayList<String>();
        cardValue = new ArrayList<String>();
        searchResults = false;
        
        if (session.getAttribute("Partner_Object_List") != null) {
            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
        }

        if (partnerInfoList != null && partnerInfoList.size() > 0) {
            for (int i = 0; i < partnerInfoList.size(); i++) {
                if (partnerInfoList.get(i).getPartnerName() != null && partnerInfoList.get(i).getPartnerValue() != null) {
                    SelectItem selectItemPartner = new SelectItem();
                    selectItemPartner.setLabel(partnerInfoList.get(i).getPartnerName().toString());
                    selectItemPartner.setValue(partnerInfoList.get(i).getPartnerValue().toString());
                    partnerList.add(selectItemPartner);
                    partnerValue.add(partnerInfoList.get(i).getPartnerValue().toString());
                }

                if (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {
                    for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
                        if (partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() != null) {
                            SelectItem selectItem = new SelectItem();
                            selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            accountList.add(selectItem);
                            accountValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup() != null &&
                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size() > 0) {
                                cGCardVisible = true;
                                cardGroupVisible = true;
                                cardVisible = false;

                                for (int cg = 0; cg < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); cg++) {

                                    SelectItem selectItemCardGroup = new SelectItem();
                                    selectItemCardGroup.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getDisplayCardGroupIdName().toString());
                                    selectItemCardGroup.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                                 partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCardGroupID().toString());
                                    cardGroupList.add(selectItemCardGroup);
                                    cardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                       partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCardGroupID().toString());
                                    cGCardVisible = true;
                                    cardGroupVisible = true;
                                    cardVisible = false;


                                }
                            }
                        }
                    }
                }
            }
        }

        Collections.sort(partnerList, comparator);
        Collections.sort(accountList, comparator);
        Collections.sort(cardGroupList, comparator);
        
    }
 
    public String formatConversion(Date date) {
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    public String populateStringValues(String var) {
        String passingValues = null;
        if (var != null) {
            String lovValues = var.trim();
            String selectedValues = lovValues.substring(1, lovValues.length() - 1);
            passingValues = selectedValues.trim();

        }
        return passingValues;
    }

    public void clearSearchListener(ActionEvent actionEvent) {

        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside clearSearchListener for Invoices");

        resetValues();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFromDate());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getToDate());

        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting clearSearchListener for Invoices");
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
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "inside invoiceDetailsCancel for Invoices");
        defaultSelection = Constants.TRANSACTIONS__LITERAL;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRadioBtnPopUp());
        ViewObject cardTransactionVO = ADFUtils.getViewObject(PRTCARDTRANSACTIONINVOICERVO1ITERATORLITRERAL);
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
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting invoiceDetailsCancel for Invoices");
        return null;
    }

    public String invoiceNumberAction() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside invoiceNumberAction for Invoices");
        String invoiceGroupingValue = null;
        defaultSelection = Constants.TRANSACTIONS__LITERAL;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRadioBtnPopUp());
        isTransactionVisible = true;
        invoiceType = null;
        Row row = null;
        if(isCardGroup){
        BindingContainer localBinding = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding itr = (DCIteratorBinding)localBinding.get(PRTNEWINVOICEVO1ITERATORLITRERAL);
        row = itr.getCurrentRow();
        
    }
    else{
        BindingContainer localBinding = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding itr = (DCIteratorBinding)localBinding.get(PRTNEWINVOICECARDVO1ITERATORLITRERAL);
        row = itr.getCurrentRow();
    }
        
        
        
        if (row != null) {
            
            popUpInvoice = (String)row.getAttribute("Finalinvoice");
            
            java.util.Date date = null;
            java.sql.Date sqlDateDob  = ((oracle.jbo.domain.Date)row.getAttribute("InvoiceDate")).dateValue();
            date = new Date(sqlDateDob.getTime());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String invDate = dateFormat.format(date);
           
            popUpInvoiceDate = invDate;
            
            popUpInvoiceNet = popUpInvoiceNet.valueOf(row.getAttribute("netAmount"));
            popUpInvoiceVat = popUpInvoiceVat.valueOf(row.getAttribute("InvVatAmt"));
            popUpInvoiceGross = popUpInvoiceGross.valueOf(row.getAttribute("InvGrossAmt"));
            
            invoiceGroupingValue = (String)row.getAttribute("InvoiceDocType");
        }

        String invoiceNumberValue = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("invoiceNumberValue");

        ViewObject cardTransactionVO = ADFUtils.getViewObject(PRTCARDTRANSACTIONINVOICERVO1ITERATORLITRERAL);

        if (invoiceGroupingValue != null) {
            invoiceType = invoiceGroupingValue;

            executeInvoiceTransactions(invoiceGroupingValue, invoiceNumberValue);
        }
        getBindings().getRadioBtnPopUp().setSubmittedValue(null);
        getBindings().getRadioBtnPopUp().setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRadioBtnPopUp());
        getBindings().getInvoiceDetails().show(new RichPopup.PopupHints());

        isInvoiceCollectionVisible = false;
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting invoiceNumberAction for Invoices");
        return null;
    }



    
    
    public void exportExcelSpecificActionTransactions(ActionEvent actionEvent) {
        resourceBundle = new EngageResourceBundle();

        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Entering exportExcelSpecificActionTransactions");
        defaultSelection = Constants.TRANSACTIONS__LITERAL;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRadioBtnPopUp());
        shuttleStatusTransaction = false;
        String langDB = (String)session.getAttribute(LANGREPORTLITRERAL);
        if (langDB.equalsIgnoreCase(Constants.LANGUAGE_ENGLISH)) {
            langDB = "EN";
        } else {
            langDB = langDB.substring(langDB.length() - 2, langDB.length());
            langDB = langDB.toUpperCase();
        }
        ViewObject prtExportInfoRVO1 = ADFUtils.getViewObject(PRTEXPORTINFORVO1ITERATORLITRERAL);
        prtExportInfoRVO1.setNamedWhereClauseParam(Constants.COUNTRY_CODE_LITERAL, langDB);
        prtExportInfoRVO1.setNamedWhereClauseParam("report_Page", "TRANSACTION");
        prtExportInfoRVO1.setNamedWhereClauseParam("report_Type", Constants.DEFAULT_LITERAL);
        prtExportInfoRVO1.setNamedWhereClauseParam(SELECT_CRITERIALITRERAL, Constants.DEFAULT_LITERAL);
        prtExportInfoRVO1.executeQuery();
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + " PrtExportInfoRVO Estimated Row Count :" +
                    prtExportInfoRVO1.getEstimatedRowCount());

        if (prtExportInfoRVO1.getEstimatedRowCount() > 0) {
            while (prtExportInfoRVO1.hasNext()) {
                PrtExportInfoRVORowImpl prtExportRow1 = (PrtExportInfoRVORowImpl)prtExportInfoRVO1.next();
                transactionStandardTransaction = prtExportRow1.getTotalColumns();
                transactionStandardExtraTransaction = prtExportRow1.getExtraColumns();
            }
        }
        if (transactionStandardTransaction != null) {
            String[] strHead = transactionStandardTransaction.split(Constants.ENGAGE_REPORT_DELIMITER);
            shuttleListTransaction = new ArrayList<SelectItem>();
            for (int col = 0; col < strHead.length; col++) {
                SelectItem selectItem1 = new SelectItem();
                selectItem1.setLabel(strHead[col]);
                selectItem1.setValue(strHead[col]);
                shuttleListTransaction.add(selectItem1);
            }
        }

        boolean result = false;

        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShuttleExcelTransactions());
        result = true;
        getBindings().getTransactionSelectionExportOneRadio().setValue(Constants.XLS_LITERAL);
        getBindings().getSpecificColumnsTransactions().show(new RichPopup.PopupHints());

        if (!result && resourceBundle.containsKey(TRANSACTIONSPECIFICERRORDBLITRERAL)) {

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject(TRANSACTIONSPECIFICERRORDBLITRERAL), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Exiting exportExcelSpecificActionTransactions");
    }


    public void cgValueChangeListener(ValueChangeEvent valueChangeEvent) {
        resourceBundle = new EngageResourceBundle();
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside cgValueChangeListener for Invoices");

        if (getBindings().getAccount().getValue() != null) {
            String accountNumberPassingValues = null;
            String[] accountNumberValues;
            int accountCount = 0;
            accountNumberPassingValues = populateStringValues(getBindings().getAccount().getValue().toString());
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();
            cardList = new ArrayList<SelectItem>();
            cardValue = new ArrayList<String>();
            if (accountNumberPassingValues != null) {
                if (accountNumberPassingValues.contains(",")) {
                    accountNumberValues = accountNumberPassingValues.split(",");
                    accountCount = accountNumberValues.length;
                } else {
                    accountCount = 1;
                    accountNumberValues = new String[1];
                    accountNumberValues[0] = accountNumberPassingValues;
                }

                if (valueChangeEvent.getNewValue() != null && accountCount > 0) {
                    for (int acCount = 0; acCount < accountCount; acCount++) {
                        if (valueChangeEvent.getNewValue().equals(Constants.CARD_GROUP_LITERAL)) {
                            isCardGroup=true;
                            isCard=false;
                            populateValue(valueChangeEvent.getNewValue().toString(), accountNumberValues[acCount].trim());
                            cGCardVisible = true;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                            cardGroupVisible = true;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
                            cardVisible = false;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
                            
                            
                        } else {
                            isCard=true;
                            isCardGroup=false;
                            populateValue(valueChangeEvent.getNewValue().toString(), accountNumberValues[acCount].trim());
                            cGCardVisible = true;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                            cardVisible = true;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
                            cardGroupVisible = false;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
                            
                        }
                    }
                }
            }
        } else {
            if (resourceBundle.containsKey("INVOICE_MANDATORY_CHECK_1")) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("INVOICE_MANDATORY_CHECK_1"), "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting cgValueChangeListener for Invoices");
    }

    public void populateValue(String paramType, String accountNumber) {
        if (paramType != null) {
            if (paramType.equals(Constants.CARD_GROUP_LITERAL)) {

                popoluateCardCardgroupValues(accountNumber, paramType);

            } else {
                if (paramType.equals(Constants.CARD_LITERAL)) {

                    popoluateCardCardgroupValues(accountNumber, paramType);

                }
            }
        }
    }

    public void popoluateCardCardgroupValues(String passingAccountNumber, String paramType) {
        if (passingAccountNumber != null && paramType != null && getBindings().getPartnerNumber().getValue() != null) {
            String[] partnerString;
            partnerString = stringSplitter(populateStringValues(getBindings().getPartnerNumber().getValue().toString()));
            if (partnerInfoList != null && partnerInfoList.size() > 0 && partnerString.length > 0) {
                for (int pa = 0; pa < partnerInfoList.size(); pa++) {
                    for (int p = 0; p < partnerString.length; p++) {
                        if (partnerInfoList.get(pa).getPartnerValue() != null && partnerString[p] != null &&
                            partnerInfoList.get(pa).getPartnerValue().equals(partnerString[p].trim()) && partnerInfoList.get(pa).getAccountList() != null &&
                            partnerInfoList.get(pa).getAccountList().size() > 0) {
                            for (int ac = 0; ac < partnerInfoList.get(pa).getAccountList().size(); ac++) {
                                if (partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber() != null &&
                                    partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().equals(passingAccountNumber) &&
                                    partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup() != null &&
                                    partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size() > 0) {
                                    for (int cg = 0; cg < partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size(); cg++) {
                                        if (partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupID() != null) {
                                            if (paramType.equals(Constants.CARD_GROUP_LITERAL)) {
                                                SelectItem selectItemCardGroup = new SelectItem();
                                                selectItemCardGroup.setLabel(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getDisplayCardGroupIdName().toString());
                                                selectItemCardGroup.setValue(partnerInfoList.get(pa).getPartnerValue().toString().trim() +
                                                                             partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupID().toString());
                                                cardGroupList.add(selectItemCardGroup);
                                                cardGroupValue.add(partnerInfoList.get(pa).getPartnerValue().toString().trim() +
                                                                   partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupID().toString());
                                            } else {
                                                if (partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard() != null &&
                                                    partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size() > 0) {
                                                    for (int cc = 0;
                                                         cc < partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size(); cc++) {
                                                        if (partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID() !=
                                                            null &&
                                                            partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID() !=
                                                            null) {
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
            if (paramType.equals(Constants.CARD_GROUP_LITERAL)) {
                Collections.sort(cardGroupList, comparator);
            } else {
                Collections.sort(cardList, comparator);
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
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside accountValueChangeListener for Invoices");


        if (valueChangeEvent.getNewValue() != null) {

            cGCardVisible = true;
            cardGroupVisible = true;
            cardVisible = false;

            getBindings().getCardGpCardList().setValue(Constants.CARD_GROUP_LITERAL);


            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGpCardList());


            String[] accountString = populateStringValues(valueChangeEvent.getNewValue().toString()).split(",");
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();

            for (int z = 0; z < partnerInfoList.size(); z++) {
                if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                        for (int j = 0; j < accountString.length; j++) {
                            if (partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() != null &&
                                partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().trim().equals(accountString[j].trim())) {
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
                                    }
                                }
                            }
                        }
                    }
                    Collections.sort(accountList, comparator);
                    Collections.sort(cardGroupList, comparator);
                }
            }

            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGpCardList());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());

        } else {

            searchResults = false;
            cGCardVisible = true;
            cardVisible = false;
            cardGroupVisible = true;


            cardGroupValue = new ArrayList<String>();
            cardGroupList = new ArrayList<SelectItem>();
            cardValue = new ArrayList<String>();
            cardList = new ArrayList<SelectItem>();
            getBindings().getCardGpCardList().setValue(Constants.CARD_GROUP_LITERAL);


            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGpCardList());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());

        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting accountValueChangeListener for Invoices");

    }

    public void partnerValueChangeListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside partnerValueChangeListner for Invoices");


        if (valueChangeEvent.getNewValue() != null) {
            String[] partnerString;
            partnerString = stringSplitter(populateStringValues(valueChangeEvent.getNewValue().toString()));

            cGCardVisible = true;
            cardGroupVisible = true;
            cardVisible = false;
            getBindings().getCardGpCardList().setValue(Constants.CARD_GROUP_LITERAL);

            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGpCardList());
            accountList = new ArrayList<SelectItem>();
            accountValue = new ArrayList<String>();
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();
            if (partnerInfoList != null && partnerInfoList.size() > 0 && partnerString.length > 0) {

                for (int i = 0; i < partnerInfoList.size(); i++) {
                    for (int pa = 0; pa < partnerString.length; pa++) {
                        if (partnerInfoList.get(i).getPartnerValue() != null && partnerString[pa] != null &&
                            partnerInfoList.get(i).getPartnerValue().toString().equals(partnerString[pa].trim()) &&
                            partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {
                            for (int m = 0; m < partnerInfoList.get(i).getAccountList().size(); m++) {
                                if (partnerInfoList.get(i).getAccountList().get(m).getAccountNumber() != null) {
                                    SelectItem selectItemAccount = new SelectItem();
                                    selectItemAccount.setLabel(partnerInfoList.get(i).getAccountList().get(m).getAccountNumber().toString());
                                    selectItemAccount.setValue(partnerInfoList.get(i).getAccountList().get(m).getAccountNumber().toString());
                                    accountList.add(selectItemAccount);
                                    accountValue.add(partnerInfoList.get(i).getAccountList().get(m).getAccountNumber().toString());
                                }

                                if (partnerInfoList.get(i).getAccountList().get(m).getCardGroup() != null &&
                                    partnerInfoList.get(i).getAccountList().get(m).getCardGroup().size() > 0) {
                                    cGCardVisible = true;
                                    cardGroupVisible = true;
                                    cardVisible = false;

                                    for (int cg = 0; cg < partnerInfoList.get(i).getAccountList().get(m).getCardGroup().size(); cg++) {

                                        SelectItem selectItemCardGroup = new SelectItem();
                                        selectItemCardGroup.setLabel(partnerInfoList.get(i).getAccountList().get(m).getCardGroup().get(cg).getDisplayCardGroupIdName().toString());
                                        selectItemCardGroup.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                                     partnerInfoList.get(i).getAccountList().get(m).getCardGroup().get(cg).getCardGroupID().toString());
                                        cardGroupList.add(selectItemCardGroup);
                                        cardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                           partnerInfoList.get(i).getAccountList().get(m).getCardGroup().get(cg).getCardGroupID().toString());
                                        cGCardVisible = true;
                                        cardGroupVisible = true;
                                        cardVisible = false;
                                        Collections.sort(cardGroupList, comparator);

                                    }
                                }
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

        } else {
            searchResults = false;
            cGCardVisible = true;
            cardVisible = false;
            cardGroupVisible = true;

            this.partnerValue = null;

            accountList = new ArrayList<SelectItem>();
            accountValue = new ArrayList<String>();
            cardGroupValue = new ArrayList<String>();
            cardGroupList = new ArrayList<SelectItem>();
            cardValue = new ArrayList<String>();
            cardList = new ArrayList<SelectItem>();
            getBindings().getCardGpCardList().setValue(Constants.CARD_GROUP_LITERAL);

            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGpCardList());

            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGpCardList());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting partnerValueChangeListner for Invoices");
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public static String getPropertyValue(String pName) {
        return ConfigurationUtility.getPropertyValue(pName);
    }

    public void displayErrorComponent(UIComponent component, boolean status) {

        RichSelectManyChoice soc = new RichSelectManyChoice();

        if (component instanceof RichSelectManyChoice) {
            soc = (RichSelectManyChoice)component;
            if (status) {
                soc.setStyleClass("af_mandatoryfield");
                if (component.getId().contains("smc45") || component.getId().contains("smc4") || component.getId().contains("smc3") ||
                    component.getId().contains("soc3")) {
                    soc.setStyleClass("af_mandatoryfield");
                }

            } else {
                soc.setStyleClass("af_nonmandatoryfield");
                if (component.getId().contains("smc45") || component.getId().contains("smc4") || component.getId().contains("smc3") ||
                    component.getId().contains("soc3")) {
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


    public void getUCMService(FacesContext facesContext, OutputStream outputStream) throws IOException {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside getUCMService for Invoices");
        String invoiceNumberValuePdf = "";
        String partnerNumberValuePdf ="";
        if(isCardGroup){
        ViewObject invoiceVO = ADFUtils.getViewObject(PRTNEWINVOICEVO1ITERATORLITRERAL);
        PrtNewInvoiceVORowImpl row = (PrtNewInvoiceVORowImpl)invoiceVO.getCurrentRow();
         invoiceNumberValuePdf = row.getFinalinvoice();
         partnerNumberValuePdf = row.getPartnerId();
        }
        else{
            ViewObject invoiceVO = ADFUtils.getViewObject(PRTNEWINVOICECARDVO1ITERATORLITRERAL);
            PrtNewInvoiceCardVORowImpl row = (PrtNewInvoiceCardVORowImpl)invoiceVO.getCurrentRow();
             invoiceNumberValuePdf = row.getFinalinvoice();
             partnerNumberValuePdf = row.getPartnerId();  
        }
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "invoice number" + invoiceNumberValuePdf);
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "PartnerId " + partnerId);
        
        byte[] responseByteArr = null;
        Boolean isError = false;
        UCMCustomWeb uCMCustomWeb = null;

        if (session.getAttribute(INVOICECONTENTLISTLITRERAL) != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "session is available");
            try {
                ucmInvoiceContentList = (HashMap<String, String>)session.getAttribute(INVOICECONTENTLISTLITRERAL);
                String ucmInvoiceContentId = ucmInvoiceContentList.get(invoiceNumberValuePdf);
                if (ucmInvoiceContentId != null && ucmInvoiceContentId.trim().length() > 0) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "ContentId is available from session");
                    uCMCustomWeb = new DAOFactory().getUCMService();
                    responseByteArr =
                            uCMCustomWeb.getFileFromUCM(DAOFactory.getPropertyValue(Constants.UCM_USERNAME), DAOFactory.getPropertyValue(Constants.UCM_PASSWORD),
                                                        ucmInvoiceContentId);
                    if (responseByteArr == null || responseByteArr.length == 0) {
                        isError = true;
                    } else {
                        outputStream.write(responseByteArr);

                    }
                } else {
                    byte[] result = searchGetFile(invoiceNumberValuePdf, partnerNumberValuePdf);
                    if (result != null && result.length != 0) {
                        outputStream.write(result);
                    } else {
                        isError = true;
                    }

                }


            } catch (Exception e) {
                isError = true;
                LOGGER.severe(accessDC.getDisplayRecord() + this.getClass() + " " + ".fileDownload : " + "Exception");
                LOGGER.severe(e);
            }

        } else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "session is null");
            byte[] result = searchGetFile(invoiceNumberValuePdf, partnerNumberValuePdf);
            if (result != null && result.length != 0) {
                outputStream.write(result);
            } else {
                isError = true;
            }

        }

        if (isError) {
            uCMCustomWeb = new DAOFactory().getUCMService();
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Error PDF =" + DAOFactory.getPropertyValue("ERROR_PDF_CID"));
            responseByteArr =
                    uCMCustomWeb.getFileFromUCM(DAOFactory.getPropertyValue(Constants.ENGAGE_UCM_USERNAME), DAOFactory.getPropertyValue(Constants.ENGAGE_UCM_PASSWORD),
                                                DAOFactory.getPropertyValue("ERROR_PDF_CID"));
            outputStream.write(responseByteArr);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Error while downloading PDF");

        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting getUCMService for Invoices");
    }

    public String openPopup() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside open_popup(Email functionality) for Invoices");
        String invoiceNumberValuePdf="";
        successResult = false;
        invoiceNotFound = false;
        failureResult = false;
        String partnerNumberValuePdf = "";
        
        if(isCardGroup){
        ViewObject invoiceVO = ADFUtils.getViewObject(PRTNEWINVOICEVO1ITERATORLITRERAL);
        PrtNewInvoiceVORowImpl row = (PrtNewInvoiceVORowImpl)invoiceVO.getCurrentRow();
        invoiceNumberValuePdf = row.getFinalinvoice();
        partnerNumberValuePdf = row.getPartnerId();
    }
    else{
        ViewObject invoiceVO = ADFUtils.getViewObject(PRTNEWINVOICECARDVO1ITERATORLITRERAL);
        PrtNewInvoiceCardVORowImpl row = (PrtNewInvoiceCardVORowImpl)invoiceVO.getCurrentRow();
        invoiceNumberValuePdf = row.getFinalinvoice();
        partnerNumberValuePdf = row.getPartnerId();
    }
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "invoice number" + invoiceNumberValuePdf);

        if (invoiceNumberValuePdf != null && partnerNumberValuePdf != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Invoice requested " + invoiceNumberValuePdf);


            ectx = FacesContext.getCurrentInstance().getExternalContext();
            request = (HttpServletRequest)ectx.getRequest();
            session = request.getSession(false);
            session.setAttribute("SESSION_USER_INVOICE_REQ", invoiceNumberValuePdf);
            session.setAttribute("SESSION_USER_PARTNER_REQ", partnerNumberValuePdf);

        }

        else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Note able to find requested invoice");
        }


        RichPopup.PopupHints ps = new RichPopup.PopupHints();


        failureResult = false;
        invoiceNotFound = false;
        successResult = false;
        getBindings().getConfirmationMailPopup().show(ps);
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting open_popup(Email functionality) for Invoices");
        return null;
    }

    public byte[] searchGetFile(String invoiceNumber, String partnerNumber) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside searchGetFile method");

        byte[] responseByteArr = null;
        String ucmContentId;
        UCMCustomWeb uCMCustomWeb = null;
        SearchInputVO searchInputVO = new SearchInputVO();
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "UserName =" + getPropertyValue(Constants.ENGAGE_UCM_USERNAME));
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Password =" + getPropertyValue(Constants.ENGAGE_UCM_PASSWORD));
        searchInputVO.setUsername(getPropertyValue(Constants.ENGAGE_UCM_USERNAME));
        searchInputVO.setPassword(getPropertyValue(Constants.ENGAGE_UCM_PASSWORD));
        searchInputVO.setSourceSystem("WebPortal");
        Property prop[] = new Property[Constants.FIVE];
        prop[0] = new Property();
        prop[0].setName("xDocumentNo");
        prop[0].setValue(invoiceNumber.trim());
        prop[1] = new Property();
        prop[1].setName("xPartnerId");
        prop[1].setValue(partnerNumber.trim());
        prop[2] = new Property();
        prop[2].setName("xContentType");
        prop[2].setValue("FCP");
        prop[Constants.THREE] = new Property();
        prop[Constants.THREE].setName("xSubType");
        prop[Constants.THREE].setValue("Invoice");

        prop[Constants.FOUR] = new Property();
        prop[Constants.FOUR].setName("xCountry");
        prop[Constants.FOUR].setValue(lang);

        searchInputVO.getSearchResultMetadata().add("dDocTitle");

        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "ENGAGE_UCM_WSDL_URL-------------" +
                    DAOFactory.getPropertyValue(Constants.ENGAGE_UCM_WSDL_URL));

        for (int i = 0; i < prop.length; i++) {
            searchInputVO.getSearchInputQueryProperty().add(prop[i]);
        }

        try {
            uCMCustomWeb = new DAOFactory().getUCMService();
            if (uCMCustomWeb != null) {

                for (int i = 0; i < searchInputVO.getSearchInputQueryProperty().size(); i++) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "UCM input meta tags " +
                                searchInputVO.getSearchInputQueryProperty().get(i).getValue());
                }
                List<SearchResultVO> ucmInvoiceContentIdList = uCMCustomWeb.searchDocument(searchInputVO);

                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "UCM LIST SIZE.get(0):" + ucmInvoiceContentIdList.get(0));
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "UCM LIST SIZE.get(0):.getSearchResultMetadata.size()  : " +
                            ucmInvoiceContentIdList.get(0).getSearchResultMetadata().size());
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "content id " + ucmInvoiceContentIdList.get(0).getContentID());
                if (ucmInvoiceContentIdList.size() > 0) {
                    ucmContentId = ucmInvoiceContentIdList.get(0).getContentID();
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Content id=" + ucmContentId);
                    if (ucmContentId != null && ucmContentId.trim().length() > 0) {
                        ucmInvoiceContentList.put(invoiceNumber, ucmContentId);
                        session.setAttribute(INVOICECONTENTLISTLITRERAL, ucmInvoiceContentList);
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "get file from ucm");
                        responseByteArr =
                                uCMCustomWeb.getFileFromUCM(DAOFactory.getPropertyValue(Constants.ENGAGE_UCM_USERNAME), DAOFactory.getPropertyValue(Constants.ENGAGE_UCM_PASSWORD),
                                                            ucmContentId);
                    }
                } else {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Content Id is not avialable in UCM");
                }
            }
        } catch (Exception e) {
            LOGGER.severe(accessDC.getDisplayRecord() + this.getClass() + " " + ".fileDownload : " + "Exception");
            LOGGER.severe(e);
        }

        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting searchGetFile method");
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

    public void setPartnerList(List<SelectItem> partnerList) {
        this.partnerList = partnerList;
    }

    public List<SelectItem> getPartnerList() {
        return partnerList;
    }

    public void setAccountValue(List<String> accountValue) {
        this.accountValue = accountValue;
    }

    public List<String> getAccountValue() {
        return accountValue;
    }

    public void radioBtnPopUpVCE(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "inside radioBtnPopUpVCE for Invoices");
        if (valueChangeEvent != null && valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().equals(Constants.TRANSACTIONS__LITERAL)) {
            if (getBindings().getCollectiveInvoNoOt().getValue() != null) {
                resetTableFilter();
                removeDynamicconditionOnTxQuery();
                executeInvoiceTransactions(invoiceType, getBindings().getCollectiveInvoNoOt().getValue().toString().trim());
            }
            isTransactionVisible = true;
            isInvoiceCollectionVisible = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvoiceCollectionPanel());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getTransactionPanel());
        } else {
            resetTableFilter();
            showNonCollectiveInvoicePanel = false;
            isTransactionVisible = false;
            isInvoiceCollectionVisible = true;
            String invoiceNo = getBindings().getCollectiveInvoNoOt().getValue().toString();
            ViewObject invoiceDetailVO = ADFUtils.getViewObject("PrtInvoiceDetailVo1Iterator");
            if (accountQueryDetail.length() > 1 && accountQueryDetail.trim().equalsIgnoreCase(invoiceDetailVO.getWhereClause().trim())) {

                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "inside  accountdetail query where removal");
                if (mapAccountDetailListValue != null) {
                    for (int i = 0; i < mapAccountDetailListValue.size(); i++) {
                        invoiceDetailVO.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL + i);
                    }
                } else {
                    invoiceDetailVO.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL);
                }
                invoiceDetailVO.setWhereClause("");
                invoiceDetailVO.executeQuery();

            }

            accountQueryDetail = "(";
            invoiceDetailVO.setNamedWhereClauseParam("countryCode", lang);
            invoiceDetailVO.setNamedWhereClauseParam("partnerId", populateStringValues(getBindings().getPartnerNumber().getValue().toString()));
            invoiceDetailVO.setNamedWhereClauseParam("invoiceNo", invoiceNo);

            if (accountValue.size() > Constants.ONEFIFTY) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
                mapAccountDetailListValue = ValueListSplit.callValueList(accountValue.size(), accountValue);
                for (int i = 0; i < mapAccountDetailListValue.size(); i++) {
                    String values = Constants.ACCOUNT_LITERAL + i;
                    accountQueryDetail = accountQueryDetail + "INSTR(:" + values + ",ACCOUNT_ID)<>0 OR ";
                }
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Account Query Values =" + accountQueryDetail);
                accountQueryDetail = accountQueryDetail.substring(0, accountQueryDetail.length() - Constants.THREE);
                accountQueryDetail = accountQueryDetail + ")";

            } else {
                mapAccountDetailListValue = null;
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                accountQueryDetail = "(INSTR(:account,ACCOUNT_ID)<>0 ) ";
            }

            invoiceDetailVO.setWhereClause(accountQueryDetail);

            if (accountValue.size() > Constants.ONEFIFTY) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
                mapAccountDetailListValue = ValueListSplit.callValueList(accountValue.size(), accountValue);
                for (int i = 0; i < mapAccountDetailListValue.size(); i++) {

                    invoiceDetailVO.defineNamedWhereClauseParam(Constants.ACCOUNT_LITERAL + i, mapAccountDetailListValue.get(Constants.LISTNAME_LITERAL + i),
                                                                null);
                }

            } else {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                invoiceDetailVO.defineNamedWhereClauseParam(Constants.ACCOUNT_LITERAL, populateStringValues(getBindings().getAccount().getValue().toString()),
                                                            null);
            }

            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Query Formed for detail is=" + invoiceDetailVO.getQuery());
            invoiceDetailVO.executeQuery();
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Estimated Row count of details==" + invoiceDetailVO.getEstimatedRowCount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getTransactionPanel());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvoiceCollectionPanel());
            session.setAttribute("account_Query_Invoice_detail_overview", accountQueryDetail);
            session.setAttribute("map_Account_List_Invoice_detail_overview", mapAccountDetailListValue);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Queries are saved in session");

        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting radioBtnPopUpVCE for Invoices");
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


    public void confirmationPopupValue(DialogEvent dialogEvent) {

        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside confirmation_popup_value for Invoices");
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok) {

            String localMailResult = triggermail();
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Notification of Mail " + localMailResult);
            if (localMailResult != null && localMailResult.equalsIgnoreCase("success")) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Mail send successfully");

                failureResult = false;
                invoiceNotFound = false;
                successResult = true;

            }
            if (localMailResult != null && localMailResult.equalsIgnoreCase("failure")) {

                failureResult = true;
                invoiceNotFound = false;
                successResult = true;

            }
            if (localMailResult == null) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Invoice not Found");
                failureResult = false;
                invoiceNotFound = true;
                successResult = false;
            }
        } else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " mail cancelled");
            return;
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting confirmation_popup_value for Invoices");
    }

    private String getLocalizedString(String key, String countryCode) {

        HashMap paramList = new HashMap();
        paramList.put("translationkey", key);
        paramList.put("ccCode", countryCode);
        String value = accessDC.callDCForErrorMsg("getTranslation", paramList);
        if (value != null) {
            return value;
        } else {
            return "";
        }
    }

    public String triggermail() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside Trigger Mail");
        DAOFactory daoFactory = new DAOFactory();

        if (session != null) {
            lang = (String)session.getAttribute(Constants.userLang);
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " lang " + lang);
        }

        String contactLink = daoFactory.getPropertyValue("CONTACT_STATOIL" + "_" + conversionUtility.getLangForWERCSURL(mailLnag));
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Support link in Mail is " + contactLink);

        boolean sendEmail = false;

        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        String invoiceReq = null;
        partnerReq = null;
        if (session != null && session.getAttribute("SESSION_USER_INVOICE_REQ") != null && session.getAttribute("SESSION_USER_PARTNER_REQ") != null) {
            invoiceReq = session.getAttribute("SESSION_USER_INVOICE_REQ").toString();
            partnerReq = session.getAttribute("SESSION_USER_PARTNER_REQ").toString();
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " invoice req = " + invoiceReq);

        }
        String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

        Calendar cal = Calendar.getInstance();
        String month = months[cal.get(Calendar.MONTH)];
        int year = cal.get(Calendar.YEAR);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        String env = DAOFactory.getPropertyValue("STATOIL_IMAGE_MAIL");
        String email2 = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "<title>Email from SFR</title>\n" +
            "<style>" + "a:link {text-decoration:none;}" + "a:visited {text-decoration:none;}" + "a:hover {text-decoration:underline;}" +
            "a:active {text-decoration:underline;}" + "</style>" + "</head>\n" +
            "\n" +
            "<body>\n" +
            "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
            "  <tr>\n" +
            "    <td align=\"left\" valign=\"top\" bgcolor=\"\" style=\"background-color:;\"><br>\n" +
            "    <br>\n" +
            "    <table width=\"800\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
            "      <tr>" + "      </tr>" + "      <tr>" +
            "        <td align=\"left\" valign=\"top\"><img src=\"cid:image\" width=\"\" height=\"50\" style=\"display:block;\"></td>\n" +
            "      </tr>" + "<tr> " +
            "<td align=\"left\" valign=\"top\" style=\"background-color:rgb(255,255,255); color:#ffffff; font-family:gerogia; font-size:6px;\"><font Color=\"#ffffff\">hi</font></td>" +
            "</tr>" + "      <tr>\n" +
            "        <td width=\"800\" align=\"center\" valign=\"top\" bgcolor=\"#006c00\" style=\"background-color:rgb(58,56,57); color:#000000;\">" +
            "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">\n" +
            "          <tr>\n" +
            "            <td width=\"50%\" align=\"left\" valign=\"top\" style=\"color:#ffffff; font-family:gerogia; font-size:16px;\">&nbsp;&nbsp;" + month +
            " " + dayOfMonth + ", " + year + " </td>" +
            "            <td align=\"right\" valign=\"top\" style=\"color:#ffffff; font-family:gerogia; font-size:16px;\"><font Color=\"#73D2EE\">      </font></td>\n" +
            "          </tr>\n" +
            "        </table></td>\n" +
            "      </tr>\n" +
            "      <tr>\n" +
            "        <td align=\"left\" valign=\"top\" bgcolor=\"#ffffff\" style=\"background-color:#ffffff; font-family:Arial, Helvetica, sans-serif; font-size:13px; color:#000000; padding:0px;\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"10\" style=\"margin-bottom:10px;\">\n" +
            "          <tr>\n" +
            "            <td align=\"left\" valign=\"top\" style=\"font-family:gerogia; font-size:16px; color:#525252;\">\n" +
            "<div style=\"font-size:16px;\"><br>\n" +
            getLocalizedString("ENCLOSED", mailLnag) + "." + getLocalizedString("HESITATE", mailLnag) + "<br>" +
            getLocalizedString("AUTOGENERATED", mailLnag) + "<br>" + getLocalizedString("CONTACTDETAILS", mailLnag) + " " + "<a href=" + contactLink +
            "><font Color=\"#F89518\">" + getLocalizedString("HERE", mailLnag) + "</font></a>" + "<br><br></i>" + "</div></td>\n" +
            "          </tr>\n" +
            "        </table>\n" +
            "      </tr>\n" +
            "      <tr>\n" +
            "        <td align=\"left\" valign=\"top\" bgcolor=\"#006c00\" style=\"background-color:rgb(243,243,243); \"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"15\">\n" +
            "          <tr>\n" +
            "            <td align=\"left\" valign=\"top\" style=\"color:#7F7F7F; font-family:gerogia; font-size:16px; \">Copyright © 2013 Statoil Fuel & Retail<br>" +
            "     <td align=\"right\" valign=\"top\" style=\"color:#ffffff; font-family:gerogia; font-size:16px;\"><a href=" + contactLink +
            "><font Color=\"#F89518\">Contact Statoil</font></a></td>" + "          </tr>\n" +
            "        </table></td>\n" +
            "      </tr>\n" +
            "  </table>\n" +
            "    <br>\n" +
            "    <br></td>\n" +
            "  </tr>\n" +
            "</table>\n" +
            "</body>\n" +
            "</html>\n";

        String cc = "Hiten.Karamchandani@lntinfotech.com";

        byte[] responseByteArr = null;
        UCMCustomWeb uCMCustomWeb = null;
        try {

            if (session.getAttribute(INVOICECONTENTLISTLITRERAL) != null) {
                ucmInvoiceContentList = (HashMap<String, String>)session.getAttribute(INVOICECONTENTLISTLITRERAL);
                String ucmInvoiceContentId = ucmInvoiceContentList.get(invoiceReq);
                if (ucmInvoiceContentId != null && ucmInvoiceContentId.trim().length() > 0) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "ContentId is available from session");
                    uCMCustomWeb = new DAOFactory().getUCMService();
                    responseByteArr =
                            uCMCustomWeb.getFileFromUCM(DAOFactory.getPropertyValue(Constants.UCM_USERNAME), DAOFactory.getPropertyValue(Constants.UCM_PASSWORD),
                                                        ucmInvoiceContentId);
                    if (responseByteArr == null || responseByteArr.length == 0) {

                        sendEmail = false;
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Error");
                    } else {
                        sendEmail = true;
                    }
                } else {
                    responseByteArr = searchGetFile(invoiceReq, partnerReq);
                    if (responseByteArr != null && responseByteArr.length != 0) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Response byte array length " + responseByteArr.length);
                        sendEmail = true;
                    } else {

                        sendEmail = false;
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Eoorororoo");
                    }
                }
            } else {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "session is null");
                responseByteArr = searchGetFile(invoiceReq, partnerReq);
                if (responseByteArr != null && responseByteArr.length != 0) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Response byte array length " + responseByteArr.length);
                    sendEmail = true;
                } else {
                    sendEmail = false;
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Eoorororoodddd");
                }
            }


        } catch (Exception e) {
            LOGGER.severe(accessDC.getDisplayRecord() + this.getClass() + "fileDownload : " + "Exception");
            LOGGER.severe(e);
        }

        try {
            if (sendEmail) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " sending email" + sendEmail + " to " +
                            getBindings().getEmailRecipientPopup().getValue().toString() + "for invoice " + invoiceReq + "having byte array size as" +
                            responseByteArr.length);
                emailutility.sendEmail("no-reply.SFR-Services@statoilfuelretail.com", getBindings().getEmailRecipientPopup().getValue().toString(),
                                       "Statoilfuelretail : Invoice Delivery", email2, "smtp", "smtp.statoilfuelretail.com", cc, responseByteArr, env,
                                       invoiceReq);
                return "success";
            } else {

                LOGGER.severe(accessDC.getDisplayRecord() + this.getClass() + " Throw adf message of mail can not be send");

                LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting Trigger Mail()");
                return null;
            }

        } catch (Exception e) {
            LOGGER.severe(accessDC.getDisplayRecord() + this.getClass() + " Error in mail");
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting Trigger Mail()");
            LOGGER.severe(e);
            return "failure";

        }


    }

    public void setToRecipient(String toRecipient) {
        this.toRecipient = toRecipient;
    }

    public String getToRecipient() {

        return toRecipient;
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

        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Entering triggerMailProcess");
        failureResult = false;
        invoiceNotFound = false;
        successResult = false;
        String localMailResult = triggermail();
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Notification of Mail " + localMailResult);
        if (localMailResult != null && localMailResult.equalsIgnoreCase("success")) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Mail send successfully");
            failureResult = false;
            invoiceNotFound = false;
            successResult = true;
        }
        if (localMailResult != null && localMailResult.equalsIgnoreCase("failure")) {
            LOGGER.severe(accessDC.getDisplayRecord() + this.getClass() + " Mail was not generated");
            failureResult = true;
            invoiceNotFound = false;
            successResult = true;

        }
        if (localMailResult == null) {
            LOGGER.severe(accessDC.getDisplayRecord() + this.getClass() + " Invoice not Found");
            failureResult = false;
            invoiceNotFound = true;
            successResult = false;
        }

        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting ntering triggerMailProcess");

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
        getBindings().getConfirmationMailPopup().hide();
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
        successResult = false;
        invoiceNotFound = false;
        failureResult = false;
    }

    public void exportExcelSpecificActionInvoices(ActionEvent actionEvent) {
        resourceBundle = new EngageResourceBundle();
        shuttleStatus = false;
        String langDB = (String)session.getAttribute(LANGREPORTLITRERAL);
        if (langDB.equalsIgnoreCase(Constants.LANGUAGE_ENGLISH)) {
            langDB = "EN";
        } else {
            langDB = langDB.substring(langDB.length() - 2, langDB.length());
            langDB = langDB.toUpperCase();
        }

        ViewObject prtExportInfoRVO = ADFUtils.getViewObject(PRTEXPORTINFORVO1ITERATORLITRERAL);
        prtExportInfoRVO.setNamedWhereClauseParam(Constants.COUNTRY_CODE_LITERAL, langDB);
        prtExportInfoRVO.setNamedWhereClauseParam("report_Page", Constants.INVOICES_LITERAL);
        prtExportInfoRVO.setNamedWhereClauseParam("report_Type", Constants.DEFAULT_LITERAL);
        prtExportInfoRVO.setNamedWhereClauseParam(SELECT_CRITERIALITRERAL, Constants.DEFAULT_LITERAL);
        prtExportInfoRVO.executeQuery();

        if (prtExportInfoRVO.getEstimatedRowCount() > 0) {
            while (prtExportInfoRVO.hasNext()) {
                PrtExportInfoRVORowImpl prtExportRow = (PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                strInvoicesTotalColumns = prtExportRow.getTotalColumns();
                strInvoicesExtraColumns = prtExportRow.getExtraColumns();

            }
        }

        if (strInvoicesTotalColumns != null) {
            String[] strHead = strInvoicesTotalColumns.split(Constants.ENGAGE_REPORT_DELIMITER);
            shuttleList = new ArrayList<SelectItem>();
            for (int col = 0; col < strHead.length; col++) {
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(strHead[col].trim());
                selectItem.setValue(strHead[col].trim());
                shuttleList.add(selectItem);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShuttleExcel());
            getBindings().getSelectionExportOneRadio().setValue(Constants.XLS_LITERAL);
            getBindings().getSpecificColumns().show(new RichPopup.PopupHints());
        } else {
            if (resourceBundle.containsKey(TRANSACTIONSPECIFICERRORDBLITRERAL)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject(TRANSACTIONSPECIFICERRORDBLITRERAL), "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }


    }

    public void getValuesForExcel(ActionEvent actionEvent) {
        resourceBundle = new EngageResourceBundle();
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside getValuesForExcel method of Invoices");
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
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting getValuesForExcel method of Invoices");
    }

    public String[] stringSplitter(String passedVal) {

        return passedVal.split(",");


    }

    public String checkALL(String selectedValues, String type) {
        resourceBundle = new EngageResourceBundle();
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside checkALL method of Invoices");
        String val = "";
        String[] listValues = selectedValues.split(",");
        if (listValues.length > 1) {

            if (Constants.ACCOUNT_LITERAL.equalsIgnoreCase(type)) {
                if (accountList.size() == listValues.length) {
                    if (resourceBundle.containsKey(Constants.ENG_ALL_LITERAL)) {
                        val = (String)resourceBundle.getObject(Constants.ENG_ALL_LITERAL);
                    }
                } else {
                    val = selectedValues;
                }
            } else {
                if (partnerList.size() == listValues.length) {
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
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting checkALL method of Invoices");
        return val;
    }


    public String getTimeHour(Timestamp timeStamp) {
        String val = "";
        java.util.Date date = new Date(timeStamp.getTime());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        val = format.format(date);
        return val;
    }

    public void specificTransactionExportExcelListener(FacesContext facesContext, OutputStream outputStream) throws IOException, SQLException, ParseException {
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Entering specificTransactionExportExcelListener");
        resourceBundle = new EngageResourceBundle();

        defaultSelection = Constants.TRANSACTIONS__LITERAL;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRadioBtnPopUp());
        if (shuttleValueTransaction == null && getBindings().getTransactionSelectionExportOneRadio().getValue() == null) {
            if (shuttleValueTransaction == null) {
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
            if (getBindings().getTransactionSelectionExportOneRadio().getValue() != null) {
                if (shuttleValueTransaction == null) {
                    if (resourceBundle.containsKey(Constants.TRANSACTION_SPECIFIC_ERROR_LITERAL)) {
                        FacesMessage msg =
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject(Constants.TRANSACTION_SPECIFIC_ERROR_LITERAL), "");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                } else {
                    if (shuttleValueTransaction.size() > 0 && getBindings().getTransactionSelectionExportOneRadio().getValue() != null) {
                        shuttleStatusTransaction = true;
                    }

                    String selectedValues = "";
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Size ==" + shuttleValueTransaction.size());
                    resourceBundle = new EngageResourceBundle();

                    for (int i = 0; i < shuttleValueTransaction.size(); i++) {

                        selectedValues = selectedValues + shuttleValueTransaction.get(i).toString().trim() + "|";
                    }

                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Formed String =" + selectedValues);
                    String passedString = selectedValues.substring(0, selectedValues.length() - 1);

                    ReportBundle rb = new ReportBundle();
                    String langDB = (String)session.getAttribute(LANGREPORTLITRERAL);
                    if (langDB.equalsIgnoreCase(Constants.LANGUAGE_ENGLISH)) {
                        langDB = "EN";
                    } else {
                        langDB = langDB.substring(langDB.length() - 2, langDB.length());
                        langDB = langDB.toUpperCase();
                    }
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "langDB =" + langDB);
                    String columnsReport = rb.getContentsForReport(Constants.INVOICES_LITERAL, langDB, passedString);
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "From Resource Bundle:" + columnsReport);
                    String[] headerDataValues = columnsReport.split(Constants.ENGAGE_REPORT_DELIMITER);
                    String[] headerValues = selectedValues.split(Constants.ENGAGE_REPORT_DELIMITER);

                    if (Constants.XLS_LITERAL.equalsIgnoreCase(getBindings().getTransactionSelectionExportOneRadio().getValue().toString())) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Report in Excel Format");
                        HSSFWorkbook XLS = new HSSFWorkbook();
                        HSSFRow XLS_SH_R = null;
                        HSSFCell XLS_SH_R_C = null;
                        HSSFCellStyle cs = XLS.createCellStyle();
                        HSSFFont f = XLS.createFont();
                        HSSFSheet XLS_SH = XLS.createSheet();
                        XLS.setSheetName(0, "Invoice Transactions Report");
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

                        HSSFCellStyle css = XLS.createCellStyle();
                        HSSFFont fcss = XLS.createFont();
                        fcss.setFontHeightInPoints((short)Constants.TEN);
                        fcss.setItalic(true);
                        fcss.setColor((short)0);
                        css.setFont(fcss);

                        XLS_SH_R = XLS_SH.createRow(0);
                        XLS_SH_R_C = XLS_SH_R.createCell(0);
                        XLS_SH_R_C.setCellStyle(cs);
                        if (resourceBundle.containsKey("ENGAGE_INVOICE_POPUP_1") && resourceBundle.containsKey("ENGAGE_INVOICE_POPUP_2")) {

//                            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                            Date date = sdf.parse(getBindings().getPopUpInvoiceDate().getValue().toString());

                            XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("ENGAGE_INVOICE_POPUP_1") + ": " +
                                                    getBindings().getCollectiveInvoNoOt().getValue().toString() + " " +
                                                    (String)resourceBundle.getObject("ENGAGE_INVOICE_POPUP_2") + ": " + getBindings().getPopUpInvoiceDate().getValue().toString());

                        }


                        XLS_SH_R = XLS_SH.createRow(1);
                        XLS_SH_R_C = XLS_SH_R.createCell(0);
                        XLS_SH_R_C.setCellStyle(cs);
                        if (resourceBundle.containsKey("ENGAGE_INVOICE_NET")) {
                            XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("ENGAGE_INVOICE_NET") + ": " +
                                                    formatConversion(Float.parseFloat(getBindings().getPopUpInvNet().getValue().toString()), locale));

                        }

                        XLS_SH_R = XLS_SH.createRow(2);
                        XLS_SH_R_C = XLS_SH_R.createCell(0);
                        XLS_SH_R_C.setCellStyle(cs);
                        if (resourceBundle.containsKey("ENGAGE_INVOICE_VAT")) {
                            XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("ENGAGE_INVOICE_VAT") + ": " +
                                                    formatConversion(Float.parseFloat(getBindings().getPopUpInvVat().getValue().toString()), locale));
                        }

                        XLS_SH_R = XLS_SH.createRow(Constants.THREE);
                        XLS_SH_R_C = XLS_SH_R.createCell(0);
                        XLS_SH_R_C.setCellStyle(cs);
                        if (resourceBundle.containsKey("ENGAGE_INVOICE_TOTAL_AMOUNT")) {
                            XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("ENGAGE_INVOICE_TOTAL_AMOUNT") + ": " +
                                                    formatConversion(Float.parseFloat(getBindings().getPopUpInvGross().getValue().toString()), locale));
                        }

                        XLS_SH_R = XLS_SH.createRow(Constants.FOUR);
                        XLS_SH_R = XLS_SH.createRow(Constants.FIVE);
                        XLS_SH_R_C = XLS_SH_R.createCell(0);
                        XLS_SH_R_C.setCellStyle(cs);
                        if (resourceBundle.containsKey(ENGAGENOTEALLPRICESBELOWAREINLITRERAL)) {
                            XLS_SH_R_C.setCellValue((String)resourceBundle.getObject(ENGAGENOTEALLPRICESBELOWAREINLITRERAL) + currencyCode);
                        }

                        XLS_SH_R = XLS_SH.createRow(Constants.SIX);
                        XLS_SH_R = XLS_SH.createRow(Constants.SEVEN);
                        for (int col = 0; col < headerValues.length; col++) {
                            XLS_SH_R_C = XLS_SH_R.createCell(col);
                            XLS_SH_R_C.setCellStyle(css);
                            XLS_SH_R_C.setCellValue(headerValues[col]);
                        }

                        int rowVal = Constants.SEVEN;

                        ViewObject cardTransactionVO = ADFUtils.getViewObject(PRTCARDTRANSACTIONINVOICERVO1ITERATORLITRERAL);
                        RowSetIterator iterator = cardTransactionVO.createRowSetIterator(null);
                        iterator.reset();
                        int rowNum = 0;
                        while (iterator.hasNext()) {
                            rowNum = rowNum + 1;
                            PrtCardTransactionInvoiceRVORowImpl row = (PrtCardTransactionInvoiceRVORowImpl)iterator.next();
                            rowVal = rowVal + 1;
                            XLS_SH_R = XLS_SH.createRow(rowVal);
                            if (row != null) {
                                for (int cellValue = 0; cellValue < headerDataValues.length; cellValue++) {
                                    if ("Line No".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                        XLS_SH_R_C.setCellStyle(csData);

                                        XLS_SH_R_C.setCellValue(rowNum);

                                    } else if ("Invoice No".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getInvoiceNumberNonCollective() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            XLS_SH_R_C.setCellValue(row.getInvoiceNumberNonCollective().toString().trim());
                                        }
                                    } else if ("Product".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getProductName() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            XLS_SH_R_C.setCellValue(row.getProductName());
                                        }
                                    } else if ("Quantity".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getQuantity() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            XLS_SH_R_C.setCellValue(row.getQuantity().toString().trim() + row.getUnitOfMeasure());
                                        }
                                    } else if ("Station".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getStationName() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            XLS_SH_R_C.setCellValue(row.getStationName().toString().trim());
                                        }
                                    } else if (Constants.CARD_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getCard1Id() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            XLS_SH_R_C.setCellValue(row.getCard1Id().toString().trim());
                                        }
                                    } else if (Constants.NET_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getInvoicedNetAmount() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csRight);
                                            XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getInvoicedNetAmount().toString())), locale));
                                        }
                                    } else if ("Gross Amount".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getInvoicedGrossAmount() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csRight);
                                            XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getInvoicedGrossAmount().toString())), locale));
                                        }

                                    }

                                    else if ("Vehicle No".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getVehicleNumber() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            XLS_SH_R_C.setCellValue(row.getVehicleNumber().toString());
                                        }
                                    } else if ("InternalName".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getInternalName() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            XLS_SH_R_C.setCellValue(row.getInternalName().toString());
                                        }
                                    } else if ("DriverNumber".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getDriverNumber() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            XLS_SH_R_C.setCellValue(row.getDriverNumber().toString());
                                        }
                                    } else if ("Driver Name".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getDriverName() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            XLS_SH_R_C.setCellValue(row.getDriverName().toString());
                                        }
                                    }

                                    else if ("Currency".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getPurchaseCurrency() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            XLS_SH_R_C.setCellValue(row.getPurchaseCurrency().toString());
                                        }
                                    } else if ("Unit price, purchase currency".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getCurrencyUnitPrice() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csRight);
                                            XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getCurrencyUnitPrice().toString())), locale));
                                        }
                                    } else if ("Gross amount, purchase currency".equalsIgnoreCase(headerDataValues[cellValue].trim())) {

                                        if (row.getCurrencyGrossAmount() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csRight);
                                            XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getCurrencyGrossAmount().toString())), locale));
                                        }
                                    } else if ("Discounted Price".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                        XLS_SH_R_C.setCellStyle(csRight);
                                        if (row.getInvoicedUnitPriceRebated() != null) {
                                            XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getInvoicedUnitPriceRebated().toString())),
                                                                                     locale));
                                        }
                                    }

                                    else if ("Odometer".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                        XLS_SH_R_C.setCellStyle(csRight);
                                        if (row.getOdometerPortal() != null) {
                                            XLS_SH_R_C.setCellValue(row.getOdometerPortal().toString());
                                        } else {
                                            if (row.getOdometer() != null) {
                                                XLS_SH_R_C.setCellValue(row.getOdometer().toString());
                                            }
                                        }
                                    } else if ("TotalKM".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getkmTotal() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csRight);
                                            XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getkmTotal().toString())), locale));
                                        }
                                    } else if ("KM/L".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getkmPerLt() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csRight);
                                            XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getkmPerLt().toString())), locale));
                                        }
                                    } else if ("L/100KM".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getltPerHundred() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csRight);
                                            XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getltPerHundred().toString())), locale));
                                        }

                                    } else if ("Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getTransactionDt() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            String time = "";
                                            if (row.getTransactionTime() != null) {
                                                time = getTimeHour(row.getTransactionTime().timestampValue());
                                            }
                                            XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().timestampValue().getTime())) + "  " +
                                                                    time);
                                        }
                                    } else if (Constants.PARTNER_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getPartnerId() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            XLS_SH_R_C.setCellValue(row.getPartnerId().toString());
                                        }
                                    } else if (Constants.ACCOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getAccountId() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            XLS_SH_R_C.setCellValue(row.getAccountId().toString());
                                        }
                                    } else if ("CardTextLine2".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getCardTextLine2() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            XLS_SH_R_C.setCellValue(row.getCardTextLine2().toString());
                                        }
                                    } else if (Constants.TOTAL_AMOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getInvoicedGrossAmountRebated() != null) {

                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csRight);
                                            XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmountRebated(), locale));
                                        }
                                    } else if (Constants.VAT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                        XLS_SH_R_C.setCellStyle(csRight);
                                        if (row.getInvoivedVatRebated() != null) {

                                            XLS_SH_R_C.setCellValue(formatConversion(row.getInvoivedVatRebated(), locale));
                                        }
                                    } else if ("CardGroup Description".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getCardGroupDesc() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            XLS_SH_R_C.setCellValue(row.getCardGroupDesc().toString());
                                        }
                                    } else if (Constants.CARD_GROUP_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getCardgroupId() != null) {
                                            XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                            XLS_SH_R_C.setCellStyle(csData);
                                            XLS_SH_R_C.setCellValue(row.getCardgroupId().toString());
                                        }
                                    } else if (Constants.CARD2ID_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim()) && row.getCard2Id() != null) {

                                        XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                        XLS_SH_R_C.setCellStyle(csData);
                                        XLS_SH_R_C.setCellValue(row.getCard2Id().toString());

                                    }
                                }
                            }
                        }
                        iterator.closeRowSetIterator();
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Printing excel Data completed");
                        XLS.write(outputStream);
                        outputStream.close();

                    } else if (Constants.CSV_LITERAL.equalsIgnoreCase(getBindings().getTransactionSelectionExportOneRadio().getValue().toString())) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Report in CSV Format");
                        PrintWriter out = new PrintWriter(outputStream);

                        for (int col = 0; col < headerValues.length; col++) {
                            out.print(headerValues[col].trim());
                            if (col < headerValues.length - 1) {
                                out.print(";");
                            }
                        }
                        out.println();
                        ViewObject cardTransactionVO = ADFUtils.getViewObject(PRTCARDTRANSACTIONINVOICERVO1ITERATORLITRERAL);
                        RowSetIterator iterator = cardTransactionVO.createRowSetIterator(null);
                        iterator.reset();
                        int rowNum = 0;
                        while (iterator.hasNext()) {
                            rowNum = rowNum + 1;
                            PrtCardTransactionInvoiceRVORowImpl row = (PrtCardTransactionInvoiceRVORowImpl)iterator.next();
                            if (row != null) {
                                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + PRINTINGDATALITRERAL);
                                for (int cellValue = 0; cellValue < headerDataValues.length; cellValue++) {
                                    if ("Line No".equalsIgnoreCase(headerDataValues[cellValue].trim())) {

                                        out.print(rowNum);

                                        if (cellValue != headerDataValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("Invoice No".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getInvoiceNumberNonCollective() != null) {
                                            out.print(row.getInvoiceNumberNonCollective().toString().trim());
                                        }
                                        if (cellValue != headerDataValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("Product".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getProductName() != null) {
                                            out.print(row.getProductName());
                                        }
                                        if (cellValue != headerDataValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("Quantity".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getQuantity() != null) {
                                            out.print(row.getQuantity() + row.getUnitOfMeasure());
                                        }
                                        if (cellValue != headerDataValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("Station".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getStationName() != null) {
                                            out.print(row.getStationName().toString().trim());
                                        }
                                        if (cellValue != headerDataValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if (Constants.CARD_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getCard1Id() != null) {
                                            out.print(row.getCard1Id().toString().trim());
                                        }
                                        if (cellValue != headerDataValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if (Constants.NET_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getInvoicedNetAmount() != null) {
                                            out.print((formatConversion((Float.parseFloat(row.getInvoicedNetAmount().toString())), locale)));
                                        }
                                        if (cellValue != headerDataValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("Gross Amount".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getInvoicedGrossAmount() != null) {
                                            out.print((formatConversion((Float.parseFloat(row.getInvoicedGrossAmount().toString())), locale)));
                                        }
                                        if (cellValue != headerDataValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("Vehicle No".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getVehicleNumber() != null) {
                                            out.print(row.getVehicleNumber().toString());
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("InternalName".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getInternalName() != null) {
                                            out.print(row.getInternalName().toString());
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("Odometer".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
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
                                    } else if ("TotalKM".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getkmTotal() != null) {
                                            out.print(formatConversion((Float.parseFloat(row.getkmTotal().toString())), locale));
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("KM/L".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getkmPerLt() != null) {
                                            out.print(formatConversion((Float.parseFloat(row.getkmPerLt().toString())), locale));
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("L/100KM".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getltPerHundred() != null) {
                                            out.print(formatConversion((Float.parseFloat(row.getltPerHundred().toString())), locale));
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("DriverNumber".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getDriverNumber() != null) {
                                            out.print(row.getDriverNumber().toString());
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("Driver Name".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getDriverName() != null) {
                                            out.print(row.getDriverName().toString());
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    }

                                    else if ("Discounted Price".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getInvoicedUnitPriceRebated() != null) {
                                            out.print(formatConversion(Float.parseFloat(row.getInvoicedUnitPriceRebated().toString()), locale));
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("Unit price, purchase currency".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getCurrencyUnitPrice() != null) {
                                            out.print(formatConversion((Float.parseFloat(row.getCurrencyUnitPrice().toString())), locale));
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("Gross amount, purchase currency".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getCurrencyGrossAmount() != null) {
                                            out.print(formatConversion((Float.parseFloat(row.getCurrencyGrossAmount().toString())), locale));
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("Currency".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getPurchaseCurrency() != null) {
                                            out.print(row.getPurchaseCurrency().toString());
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getTransactionDt() != null) {

                                            String time = "";
                                            if (row.getTransactionTime() != null) {
                                                time = getTimeHour(row.getTransactionTime().timestampValue());
                                            }
                                            out.print(formatConversion(new Date(row.getTransactionDt().timestampValue().getTime())) + " " + time);
                                            if (cellValue != headerValues.length - 1) {
                                                out.print(";");
                                            }
                                        }
                                    } else if (Constants.PARTNER_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getPartnerId() != null) {
                                            out.print(row.getPartnerId().toString());
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if (Constants.ACCOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getAccountId() != null) {
                                            out.print(row.getAccountId().toString());
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("CardTextLine2".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getCardTextLine2() != null) {
                                            out.print(row.getCardTextLine2().toString());
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if (Constants.TOTAL_AMOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getInvoicedNetAmountRebated() != null) {
                                            out.print(formatConversion(row.getInvoicedNetAmountRebated(), locale));
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if (Constants.VAT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {

                                        if (row.getInvoivedVatRebated() != null) {
                                            out.print(formatConversion(row.getInvoivedVatRebated(), locale));
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("CardGroup Description".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getCardGroupDesc() != null) {
                                            out.print(row.getCardGroupDesc().toString());
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if ("CardGroup Id".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getCardgroupId() != null) {
                                            out.print(row.getCardgroupId().toString());
                                        }
                                        if (cellValue != headerValues.length - 1) {
                                            out.print(";");
                                        }
                                    } else if (Constants.CARD2ID_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                        if (row.getCard2Id() != null) {
                                            out.print(row.getCard2Id().toString());
                                        }
                                        if (cellValue != headerValues.length - 1) {
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
                        if (CSV2LITRERAL.equalsIgnoreCase(getBindings().getTransactionSelectionExportOneRadio().getValue().toString())) {
                            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Report in CSV2 Format");
                            PrintWriter out = new PrintWriter(outputStream);

                            for (int col = 0; col < headerValues.length; col++) {
                                out.print(headerValues[col].trim());
                                if (col < headerValues.length - 1) {
                                    out.print("|");
                                }


                            }
                            out.println();
                            ViewObject cardTransactionVO = ADFUtils.getViewObject(PRTCARDTRANSACTIONINVOICERVO1ITERATORLITRERAL);
                            RowSetIterator iterator = cardTransactionVO.createRowSetIterator(null);
                            iterator.reset();
                            int rowNum = 0;
                            while (iterator.hasNext()) {
                                rowNum = rowNum + 1;
                                PrtCardTransactionInvoiceRVORowImpl row = (PrtCardTransactionInvoiceRVORowImpl)iterator.next();
                                if (row != null) {
                                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + PRINTINGDATALITRERAL);
                                    for (int cellValue = 0; cellValue < headerDataValues.length; cellValue++) {
                                        if ("Line No".equalsIgnoreCase(headerDataValues[cellValue].trim())) {

                                            out.print(rowNum);

                                            if (cellValue != headerDataValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("Invoice No".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getInvoiceNumberNonCollective() != null) {
                                                out.print(row.getInvoiceNumberNonCollective().toString().trim());
                                            }
                                            if (cellValue != headerDataValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("Product".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getProductName() != null) {
                                                out.print(row.getProductName());
                                            }
                                            if (cellValue != headerDataValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("Quantity".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getQuantity() != null) {
                                                out.print(row.getQuantity() + row.getUnitOfMeasure());
                                            }
                                            if (cellValue != headerDataValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("Station".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getStationName() != null) {
                                                out.print(row.getStationName().toString().trim());
                                            }
                                            if (cellValue != headerDataValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if (Constants.CARD_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getCard1Id() != null) {
                                                out.print(row.getCard1Id().toString().trim());
                                            }
                                            if (cellValue != headerDataValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if (Constants.NET_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getInvoicedNetAmount() != null) {
                                                out.print((formatConversion((Float.parseFloat(row.getInvoicedNetAmount().toString())), locale)));
                                            }
                                            if (cellValue != headerDataValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("Gross Amount".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getInvoicedGrossAmount() != null) {
                                                out.print((formatConversion((Float.parseFloat(row.getInvoicedGrossAmount().toString())), locale)));
                                            }
                                            if (cellValue != headerDataValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("Currency".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getPurchaseCurrency() != null) {
                                                out.print(row.getPurchaseCurrency().toString());
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("Unit price, purchase currency".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getCurrencyUnitPrice() != null) {
                                                out.print(formatConversion((Float.parseFloat(row.getCurrencyUnitPrice().toString())), locale));
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("Gross amount, purchase currency".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getCurrencyGrossAmount() != null) {
                                                out.print(formatConversion((Float.parseFloat(row.getCurrencyGrossAmount().toString())), locale));
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("Vehicle No".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getVehicleNumber() != null) {
                                                out.print(row.getVehicleNumber().toString());
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("InternalName".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getInternalName() != null) {
                                                out.print(row.getInternalName().toString());
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("Odometer".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
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
                                        } else if ("TotalKM".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getkmTotal() != null) {
                                                out.print(formatConversion((Float.parseFloat(row.getkmTotal().toString())), locale));
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("KM/L".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getkmPerLt() != null) {
                                                out.print(formatConversion((Float.parseFloat(row.getkmPerLt().toString())), locale));
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("L/100KM".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getltPerHundred() != null) {
                                                out.print(formatConversion((Float.parseFloat(row.getltPerHundred().toString())), locale));
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("DriverNumber".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getDriverNumber() != null) {
                                                out.print(row.getDriverNumber().toString());
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("Driver Name".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getDriverName() != null) {
                                                out.print(row.getDriverName().toString());
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        }

                                        else if ("Discounted Price".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getInvoicedUnitPriceRebated() != null) {
                                                out.print(formatConversion(Float.parseFloat(row.getInvoicedUnitPriceRebated().toString()), locale));
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getTransactionDt() != null) {

                                                String time = "";
                                                if (row.getTransactionTime() != null) {
                                                    time = getTimeHour(row.getTransactionTime().timestampValue());
                                                }
                                                out.print(formatConversion(new Date(row.getTransactionDt().timestampValue().getTime())) + " " + time);
                                                if (cellValue != headerValues.length - 1) {
                                                    out.print("|");
                                                }
                                            }
                                        } else if (Constants.PARTNER_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getPartnerId() != null) {
                                                out.print(row.getPartnerId().toString());
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if (Constants.ACCOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getAccountId() != null) {
                                                out.print(row.getAccountId().toString());
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("CardTextLine2".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getCardTextLine2() != null) {
                                                out.print(row.getCardTextLine2().toString());
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if (Constants.TOTAL_AMOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getInvoicedNetAmountRebated() != null) {
                                                out.print(formatConversion(row.getInvoicedNetAmountRebated(), locale));
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if (Constants.VAT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {

                                            if (row.getInvoivedVatRebated() != null) {
                                                out.print(formatConversion(row.getInvoivedVatRebated(), locale));
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("CardGroup Description".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getCardGroupDesc() != null) {
                                                out.print(row.getCardGroupDesc().toString());
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else if ("CardGroup Id".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                            if (row.getCardgroupId() != null) {
                                                out.print(row.getCardgroupId().toString());
                                            }
                                            if (cellValue != headerValues.length - 1) {
                                                out.print("|");
                                            }
                                        } else {
                                            if (Constants.CARD2ID_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                                if (row.getCard2Id() != null) {
                                                    out.print(row.getCard2Id().toString());
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
            } else {
                if (resourceBundle.containsKey(Constants.TRANSACTION_SPECIFIC_ERROR_SELECTION_LITERAL)) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject(Constants.TRANSACTION_SPECIFIC_ERROR_SELECTION_LITERAL),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }


        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Exiting specificTransactionExportExcelListener");


    }

    public void specificExportExcelListener(FacesContext facesContext, OutputStream outputStream) throws IOException, SQLException {
        resourceBundle = new EngageResourceBundle();
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside specificExportExcelListener method of Invoices");

        String selectedValues = "";
        for (int i = 0; i < shuttleValue.size(); i++) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Item =" + i + " value== " + shuttleValue.get(i));
            selectedValues = selectedValues + shuttleValue.get(i).toString().trim() + "|";
        }
        selectedValues = selectedValues.substring(0, selectedValues.length() - 1);

        ReportBundle rb = new ReportBundle();
        String langDB = (String)session.getAttribute(LANGREPORTLITRERAL);
        if (langDB.equalsIgnoreCase(Constants.LANGUAGE_ENGLISH)) {
            langDB = "EN";
        } else {
            langDB = langDB.substring(langDB.length() - 2, langDB.length());
            langDB = langDB.toUpperCase();
        }
        String columnsReport = rb.getContentsForReport(Constants.INVOICES_LITERAL, langDB, selectedValues);
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "From Resource Bundle:" + columnsReport);
        String[] headerDataValues = columnsReport.split(Constants.ENGAGE_REPORT_DELIMITER);
        String partnerCompanyName = "";
        String[] partnerCompanyNameList = stringSplitter(populateStringValues(getBindings().getPartnerNumber().getValue().toString().trim()));

        String cardGroupDescName = "";
        String[] cardGroupDescList = stringSplitter(populateStringValues(getBindings().getCardGroup().getValue().toString().trim()));
        String[] accountString = stringSplitter(populateStringValues(getBindings().getAccount().getValue().toString().trim()));

        for (int z = 0; z < partnerInfoList.size(); z++) {
            if (partnerCompanyNameList.length > 0 && partnerInfoList.get(z).getPartnerValue() != null) {
                for (int pa = 0; pa < partnerCompanyNameList.length; pa++) {
                    if (partnerCompanyNameList[pa].trim() != null &&
                        partnerInfoList.get(z).getPartnerValue().toString().trim().equals(partnerCompanyNameList[pa].trim())) {
                        partnerCompanyName = partnerInfoList.get(z).getPartnerName();
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Partner value:" + partnerCompanyName);
                        if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                            for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                                if (accountString.length > 0) {
                                    for (int j = 0; j < accountString.length; j++) {
                                        if (partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() != null &&
                                            partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().trim().equals(accountString[j].trim()) &&
                                            partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                                            partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() > 0) {

                                            for (int k = 0; k < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size(); k++) {
                                                if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID() != null &&
                                                    cardGroupDescList.length > 0) {
                                                    for (int cg = 0; cg < cardGroupDescList.length; cg++) {
                                                        if ((partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().trim()).equals(cardGroupDescList[cg].trim())) {
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
        if (cardGroupDescName != null && !cardGroupDescName.equals("")) {
            cardGroupDescName = (String)cardGroupDescName.subSequence(0, (cardGroupDescName.length()) - 1);
        }

        if (Constants.XLS_LITERAL.equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Report in Excel Format");
            HSSFWorkbook XLS = new HSSFWorkbook();
            HSSFRow XLS_SH_R = null;
            HSSFCell XLS_SH_R_C = null;
            HSSFCellStyle cs = XLS.createCellStyle();
            HSSFFont f = XLS.createFont();
            HSSFSheet XLS_SH = XLS.createSheet();
            XLS.setSheetName(0, "InvoiceReport");

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

            HSSFCellStyle csTotalAmt = XLS.createCellStyle();
            HSSFFont fontTotal = XLS.createFont();
            fontTotal.setFontHeightInPoints((short)Constants.TEN);
            fontTotal.setColor((short)0);
            fontTotal.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            csTotalAmt.setFont(fontTotal);
            csTotalAmt.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

            HSSFCellStyle csData = XLS.createCellStyle();
            HSSFFont fData = XLS.createFont();
            fData.setFontHeightInPoints((short)Constants.TEN);
            fData.setColor((short)0);
            csData.setFont(fData);

            XLS_SH.setColumnWidth(Constants.FIFTY, Constants.FIFTY);

            XLS_SH_R = XLS_SH.createRow(0);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);

            if (resourceBundle.containsKey("ENG_COMPANY")) {

                XLS_SH_R_C.setCellValue((String)resourceBundle.getObject("ENG_COMPANY") + ": " +
                                        checkALL((populateStringValues(getBindings().getPartnerNumber().getValue().toString())), Constants.PARTNER_LITERAL));
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

            if (resourceBundle.containsKey("ENG_PERIOD")) {

                XLS_SH_R_C.setCellValue(resourceBundle.getObject("ENG_PERIOD") + ": " + formatConversion((Date)getBindings().getFromDate().getValue()) + " " +
                                        resourceBundle.getObject("TO_DATE") + " " + formatConversion((Date)getBindings().getToDate().getValue()));
            }
            XLS_SH_R = XLS_SH.createRow(Constants.THREE);
            XLS_SH_R = XLS_SH.createRow(Constants.FOUR);
            XLS_SH_R = XLS_SH.createRow(Constants.FIVE);
            XLS_SH_R_C = XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);


            if (resourceBundle.containsKey(ENGAGENOTEALLPRICESBELOWAREINLITRERAL)) {
                XLS_SH_R_C.setCellValue(resourceBundle.getObject(ENGAGENOTEALLPRICESBELOWAREINLITRERAL) + currencyCode);
            }

            for (int row = Constants.SIX; row <= Constants.SEVEN; row++) {
                XLS_SH_R = XLS_SH.createRow(row);
            }

            String[] headerValues = selectedValues.split(Constants.ENGAGE_REPORT_DELIMITER);

            HSSFCellStyle css = XLS.createCellStyle();
            HSSFFont fcss = XLS.createFont();
            fcss.setFontHeightInPoints((short)Constants.TEN);
            fcss.setItalic(true);
            fcss.setColor((short)0);
            css.setFont(fcss);
            XLS_SH_R = XLS_SH.createRow(Constants.SEVEN);

            for (int col = 0; col < headerValues.length; col++) {
                XLS_SH_R_C = XLS_SH_R.createCell(col);
                XLS_SH_R_C.setCellStyle(css);
                XLS_SH_R_C.setCellValue(headerValues[col].trim());
            }

            int rowVal = Constants.EIGHT;
            RowSetIterator iterator = null;
            if(isCardGroup){
            ViewObject prtNewInvoiceVO = ADFUtils.getViewObject(PRTNEWINVOICEVO1ITERATORLITRERAL);
            iterator = prtNewInvoiceVO.createRowSetIterator(null);
            iterator.reset();
                while (iterator.hasNext()) {
                    PrtNewInvoiceVORowImpl row = (PrtNewInvoiceVORowImpl)iterator.next();
                    rowVal = rowVal + 1;
                    XLS_SH_R = XLS_SH.createRow(rowVal);
                    if (row != null) {
                        for (int cellValue = 0; cellValue < headerDataValues.length; cellValue++) {
                            if (Constants.PARTNER_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getPartnerId() != null) {
                                    XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                    XLS_SH_R_C.setCellStyle(csData);
                                    XLS_SH_R_C.setCellValue(row.getPartnerId().toString());
                                }
                            } else if (Constants.ACCOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getAccountId() != null) {
                                    XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                    XLS_SH_R_C.setCellStyle(csData);
                                    XLS_SH_R_C.setCellValue(row.getAccountId().toString());
                                }
                            } else if ("Invoice No".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getFinalinvoice() != null) {
                                    XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                    XLS_SH_R_C.setCellStyle(csData);
                                    XLS_SH_R_C.setCellValue(row.getFinalinvoice().toString());
                                }
                            } else if ("Invoice Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getInvoicingDate() != null) {
                                    XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                    XLS_SH_R_C.setCellStyle(csData);
                                    java.sql.Date date = row.getInvoicingDate().dateValue();
                                    Date passedDate = new Date(date.getTime());
                                    XLS_SH_R_C.setCellValue(formatConversion(passedDate));

                                }
                            } else if ("Due Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getInvoicingDueDate() != null) {
                                    XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                    XLS_SH_R_C.setCellStyle(csData);
                                    java.sql.Date date = row.getInvoicingDueDate().dateValue();
                                    Date passedDate = new Date(date.getTime());
                                    XLS_SH_R_C.setCellValue(formatConversion(passedDate));

                                }
                            }

                            else if (Constants.NET_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getnetAmount() != null) {
                                    XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                    XLS_SH_R_C.setCellStyle(csRight);
                                    XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getnetAmount().toString())), locale));
                                }
                            } else if (Constants.VAT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getInvVatAmt() != null) {
                                    XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                    XLS_SH_R_C.setCellStyle(csRight);
                                    XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getInvVatAmt().toString())), locale));
                                }
                            } else if (Constants.TOTAL_AMOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim()) && row.getInvGrossAmt() != null) {

                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csRight);
                                XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getInvGrossAmt().toString())), locale));

                            }
                        }

                    }
                }
            }
            
            else{
                ViewObject prtNewInvoiceCardVO = ADFUtils.getViewObject(PRTNEWINVOICECARDVO1ITERATORLITRERAL);
                iterator = prtNewInvoiceCardVO.createRowSetIterator(null);
                iterator.reset();
                while (iterator.hasNext()) {
                    PrtNewInvoiceCardVORowImpl row = (PrtNewInvoiceCardVORowImpl)iterator.next();
                    rowVal = rowVal + 1;
                    XLS_SH_R = XLS_SH.createRow(rowVal);
                    if (row != null) {
                        for (int cellValue = 0; cellValue < headerDataValues.length; cellValue++) {
                            if (Constants.PARTNER_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getPartnerId() != null) {
                                    XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                    XLS_SH_R_C.setCellStyle(csData);
                                    XLS_SH_R_C.setCellValue(row.getPartnerId().toString());
                                }
                            } else if (Constants.ACCOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getAccountId() != null) {
                                    XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                    XLS_SH_R_C.setCellStyle(csData);
                                    XLS_SH_R_C.setCellValue(row.getAccountId().toString());
                                }
                            } else if ("Invoice No".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getFinalinvoice() != null) {
                                    XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                    XLS_SH_R_C.setCellStyle(csData);
                                    XLS_SH_R_C.setCellValue(row.getFinalinvoice().toString());
                                }
                            } else if ("Invoice Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getInvoicingDate() != null) {
                                    XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                    XLS_SH_R_C.setCellStyle(csData);
                                    java.sql.Date date = row.getInvoicingDate().dateValue();
                                    Date passedDate = new Date(date.getTime());
                                    XLS_SH_R_C.setCellValue(formatConversion(passedDate));

                                }
                            } else if ("Due Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getInvoicingDueDate() != null) {
                                    XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                    XLS_SH_R_C.setCellStyle(csData);
                                    java.sql.Date date = row.getInvoicingDueDate().dateValue();
                                    Date passedDate = new Date(date.getTime());
                                    XLS_SH_R_C.setCellValue(formatConversion(passedDate));

                                }
                            }

                            else if (Constants.NET_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getnetAmount() != null) {
                                    XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                    XLS_SH_R_C.setCellStyle(csRight);
                                    XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getnetAmount().toString())), locale));
                                }
                            } else if (Constants.VAT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getInvVatAmt() != null) {
                                    XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                    XLS_SH_R_C.setCellStyle(csRight);
                                    XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getInvVatAmt().toString())), locale));
                                }
                            } else if (Constants.TOTAL_AMOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim()) && row.getInvGrossAmt() != null) {

                                XLS_SH_R_C = XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csRight);
                                XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getInvGrossAmt().toString())), locale));

                            }
                        }

                    }
                }
            }
            
            iterator.closeRowSetIterator();
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Printing excel Data completed");
            XLS.write(outputStream);
            outputStream.close();

        } else if (Constants.CSV_LITERAL.equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Report in CSV Format");

            PrintWriter out = new PrintWriter(outputStream);
            String[] headerValues = selectedValues.split(Constants.ENGAGE_REPORT_DELIMITER);
            for (int col = 0; col < headerValues.length; col++) {
                out.print(headerValues[col].trim());
                if (col < headerValues.length - 1) {
                    out.print(";");
                }
            }
            out.println();
            RowSetIterator iterator = null;
            if(isCardGroup){
            ViewObject prtNewInvoiceVO = ADFUtils.getViewObject(PRTNEWINVOICEVO1ITERATORLITRERAL);
            iterator = prtNewInvoiceVO.createRowSetIterator(null);
            iterator.reset();
                while (iterator.hasNext()) {
                    PrtNewInvoiceVORowImpl row = (PrtNewInvoiceVORowImpl)iterator.next();
                    if (row != null) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + PRINTINGDATALITRERAL);
                        for (int cellValue = 0; cellValue < headerDataValues.length; cellValue++) {

                            if (Constants.PARTNER_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getPartnerId() != null) {
                                    out.print(row.getPartnerId().toString());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print(";");
                                }
                            }

                            else if (Constants.ACCOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getAccountId() != null) {
                                    out.print(row.getAccountId().toString());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print(";");
                                }
                            }

                            else if ("Invoice No".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getFinalinvoice() != null) {
                                    out.print(row.getFinalinvoice().toString());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print(";");
                                }
                            } else if ("Invoice Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getInvoiceDate() != null) {
                                    java.sql.Date date = row.getInvoiceDate().dateValue();
                                    Date passedDate = new Date(date.getTime());
                                    out.print(formatConversion(passedDate));
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print(";");
                                }
                            } else if ("Due Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getInvoicingDueDate() != null) {
                                    java.sql.Date date = row.getInvoicingDueDate().dateValue();
                                    Date passedDate = new Date(date.getTime());
                                    out.print(formatConversion(passedDate));
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print(";");
                                }
                            }

                            else if (Constants.NET_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getnetAmount() != null) {
                                    out.print(row.getnetAmount().toString());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print(";");
                                }
                            } else if (Constants.VAT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getInvVatAmt() != null) {
                                    out.print(row.getInvVatAmt().toString());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print(";");
                                }
                            } else if (Constants.TOTAL_AMOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getInvGrossAmt() != null) {
                                    out.print(row.getInvGrossAmt().toString());
                                }
                                if (cellValue != headerValues.length - 1) {
                                    out.print(";");
                                }
                            }
                        }
                        out.println();
                    }
                }
            }
            else{
                ViewObject prtNewInvoiceCardVO = ADFUtils.getViewObject(PRTNEWINVOICECARDVO1ITERATORLITRERAL);
                iterator = prtNewInvoiceCardVO.createRowSetIterator(null);
                iterator.reset();
                    while (iterator.hasNext()) {
                        PrtNewInvoiceCardVORowImpl row = (PrtNewInvoiceCardVORowImpl)iterator.next();
                        if (row != null) {
                            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + PRINTINGDATALITRERAL);
                            for (int cellValue = 0; cellValue < headerDataValues.length; cellValue++) {

                                if (Constants.PARTNER_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getPartnerId() != null) {
                                        out.print(row.getPartnerId().toString());
                                    }
                                    if (cellValue != headerDataValues.length - 1) {
                                        out.print(";");
                                    }
                                }

                                else if (Constants.ACCOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getAccountId() != null) {
                                        out.print(row.getAccountId().toString());
                                    }
                                    if (cellValue != headerDataValues.length - 1) {
                                        out.print(";");
                                    }
                                }

                                else if ("Invoice No".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getFinalinvoice() != null) {
                                        out.print(row.getFinalinvoice().toString());
                                    }
                                    if (cellValue != headerDataValues.length - 1) {
                                        out.print(";");
                                    }
                                } else if ("Invoice Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getInvoiceDate() != null) {
                                        java.sql.Date date = row.getInvoiceDate().dateValue();
                                        Date passedDate = new Date(date.getTime());
                                        out.print(formatConversion(passedDate));
                                    }
                                    if (cellValue != headerDataValues.length - 1) {
                                        out.print(";");
                                    }
                                } else if ("Due Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getInvoicingDueDate() != null) {
                                        java.sql.Date date = row.getInvoicingDueDate().dateValue();
                                        Date passedDate = new Date(date.getTime());
                                        out.print(formatConversion(passedDate));
                                    }
                                    if (cellValue != headerDataValues.length - 1) {
                                        out.print(";");
                                    }
                                }

                                else if (Constants.NET_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getnetAmount() != null) {
                                        out.print(row.getnetAmount().toString());
                                    }
                                    if (cellValue != headerDataValues.length - 1) {
                                        out.print(";");
                                    }
                                } else if (Constants.VAT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getInvVatAmt() != null) {
                                        out.print(row.getInvVatAmt().toString());
                                    }
                                    if (cellValue != headerDataValues.length - 1) {
                                        out.print(";");
                                    }
                                } else if (Constants.TOTAL_AMOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getInvGrossAmt() != null) {
                                        out.print(row.getInvGrossAmt().toString());
                                    }
                                    if (cellValue != headerValues.length - 1) {
                                        out.print(";");
                                    }
                                }
                            }
                            out.println();
                        }
                    }
            }

            out.println();
            iterator.closeRowSetIterator();
            out.close();
        } else {
            if (CSV2LITRERAL.equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Report in CSV2 Format");

                PrintWriter out = new PrintWriter(outputStream);
                String[] headerValues = selectedValues.split(Constants.ENGAGE_REPORT_DELIMITER);
                for (int col = 0; col < headerValues.length; col++) {
                    out.print(headerValues[col].trim());
                    if (col < headerValues.length - 1) {
                        out.print("|");
                    }
                }
                out.println();
                RowSetIterator iterator = null;
                if(isCardGroup){
                ViewObject prtNewInvoiceVO = ADFUtils.getViewObject(PRTNEWINVOICEVO1ITERATORLITRERAL);
                iterator = prtNewInvoiceVO.createRowSetIterator(null);
                iterator.reset();
                while (iterator.hasNext()) {
                    PrtNewInvoiceVORowImpl row = (PrtNewInvoiceVORowImpl)iterator.next();
                    if (row != null) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + PRINTINGDATALITRERAL);
                        for (int cellValue = 0; cellValue < headerValues.length; cellValue++) {

                            if (Constants.PARTNER_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getPartnerId() != null) {
                                    out.print(row.getPartnerId().toString());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            }

                            else if (Constants.ACCOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getAccountId() != null) {
                                    out.print(row.getAccountId().toString());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            }

                            else if ("Invoice No".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getFinalinvoice() != null) {
                                    out.print(row.getFinalinvoice().toString());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Invoice Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getInvoiceDate() != null) {
                                    java.sql.Date date = row.getInvoiceDate().dateValue();
                                    Date passedDate = new Date(date.getTime());
                                    out.print(formatConversion(passedDate));
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if ("Due Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getInvoicingDueDate() != null) {
                                    java.sql.Date date = row.getInvoicingDueDate().dateValue();
                                    Date passedDate = new Date(date.getTime());
                                    out.print(formatConversion(passedDate));
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            }

                            else if (Constants.NET_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getnetAmount() != null) {
                                    out.print(row.getnetAmount().toString());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if (Constants.VAT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getInvVatAmt() != null) {
                                    out.print(row.getInvVatAmt().toString());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            } else if (Constants.TOTAL_AMOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                if (row.getInvGrossAmt() != null) {
                                    out.print(row.getInvGrossAmt().toString());
                                }
                                if (cellValue != headerDataValues.length - 1) {
                                    out.print("|");
                                }
                            }
                        }
                        out.println();
                    }
                }
            }
                else{
                    ViewObject prtNewInvoiceCardVO = ADFUtils.getViewObject(PRTNEWINVOICECARDVO1ITERATORLITRERAL);
                    iterator = prtNewInvoiceCardVO.createRowSetIterator(null);
                    iterator.reset();
                    while (iterator.hasNext()) {
                        PrtNewInvoiceCardVORowImpl row = (PrtNewInvoiceCardVORowImpl)iterator.next();
                        if (row != null) {
                            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + PRINTINGDATALITRERAL);
                            for (int cellValue = 0; cellValue < headerValues.length; cellValue++) {

                                if (Constants.PARTNER_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getPartnerId() != null) {
                                        out.print(row.getPartnerId().toString());
                                    }
                                    if (cellValue != headerDataValues.length - 1) {
                                        out.print("|");
                                    }
                                }

                                else if (Constants.ACCOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getAccountId() != null) {
                                        out.print(row.getAccountId().toString());
                                    }
                                    if (cellValue != headerDataValues.length - 1) {
                                        out.print("|");
                                    }
                                }

                                else if ("Invoice No".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getFinalinvoice() != null) {
                                        out.print(row.getFinalinvoice().toString());
                                    }
                                    if (cellValue != headerDataValues.length - 1) {
                                        out.print("|");
                                    }
                                } else if ("Invoice Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getInvoiceDate() != null) {
                                        java.sql.Date date = row.getInvoiceDate().dateValue();
                                        Date passedDate = new Date(date.getTime());
                                        out.print(formatConversion(passedDate));
                                    }
                                    if (cellValue != headerDataValues.length - 1) {
                                        out.print("|");
                                    }
                                } else if ("Due Date".equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getInvoicingDueDate() != null) {
                                        java.sql.Date date = row.getInvoicingDueDate().dateValue();
                                        Date passedDate = new Date(date.getTime());
                                        out.print(formatConversion(passedDate));
                                    }
                                    if (cellValue != headerDataValues.length - 1) {
                                        out.print("|");
                                    }
                                }

                                else if (Constants.NET_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getnetAmount() != null) {
                                        out.print(row.getnetAmount().toString());
                                    }
                                    if (cellValue != headerDataValues.length - 1) {
                                        out.print("|");
                                    }
                                } else if (Constants.VAT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getInvVatAmt() != null) {
                                        out.print(row.getInvVatAmt().toString());
                                    }
                                    if (cellValue != headerDataValues.length - 1) {
                                        out.print("|");
                                    }
                                } else if (Constants.TOTAL_AMOUNT_LITERAL.equalsIgnoreCase(headerDataValues[cellValue].trim())) {
                                    if (row.getInvGrossAmt() != null) {
                                        out.print(row.getInvGrossAmt().toString());
                                    }
                                    if (cellValue != headerDataValues.length - 1) {
                                        out.print("|");
                                    }
                                }
                            }
                            out.println();
                        }
                    }
                }
                out.println();
                iterator.closeRowSetIterator();
                out.close();
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting specificExportExcelListener method of Invoices");
    }

    public String excelDownLoad() {
        return null;
    }

    public String formatConversion(Float passedValue, Locale countryLocale) {
        String val = "";
        NumberFormat numberFormat = NumberFormat.getInstance(countryLocale);
        val = numberFormat.format(passedValue);
        return val;
    }

    public void filterTable(ActionEvent actionEvent) {

        FilterableQueryDescriptor qd =

            (FilterableQueryDescriptor)getBindings().getInvoiceResults().getFilterModel();

        QueryEvent queryEvent =

            new QueryEvent(getBindings().getInvoiceResults(), qd);

        getBindings().getInvoiceResults().queueEvent(queryEvent);

        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvoiceResults());

        FilterableQueryDescriptor qd1 =

            (FilterableQueryDescriptor)getBindings().getInvoiceResultsPopup().getFilterModel();


        QueryEvent queryEvent2 =

            new QueryEvent(getBindings().getInvoiceResultsPopup(), qd1);

        getBindings().getInvoiceResultsPopup().queueEvent(queryEvent2);

        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvoiceResultsPopup());


        FilterableQueryDescriptor qd2 =

            (FilterableQueryDescriptor)getBindings().getInvoiceResultsCollection().getFilterModel();


        QueryEvent queryEvent3 =

            new QueryEvent(getBindings().getInvoiceResultsCollection(), qd2);

        getBindings().getInvoiceResultsCollection().queueEvent(queryEvent3);

        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvoiceResultsCollection());
        
        
        FilterableQueryDescriptor qd3 =

            (FilterableQueryDescriptor)getBindings().getInvoiceResultsCards().getFilterModel();


        QueryEvent queryEvent4 =

            new QueryEvent(getBindings().getInvoiceResultsCards(), qd3);

        getBindings().getInvoiceResultsCards().queueEvent(queryEvent4);

        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvoiceResultsCards());


    }


    public void resetFilterTable(ActionEvent actionEvent) {
        resetTableFilter();
    }


    public void resetTableFilter()

    {

        FilterableQueryDescriptor queryDescriptor =

            (FilterableQueryDescriptor)getBindings().getInvoiceResults().getFilterModel();

        if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null) {
            queryDescriptor.getFilterCriteria().clear();
            getBindings().getInvoiceResults().queueEvent(new QueryEvent(getBindings().getInvoiceResults(), queryDescriptor));
        }

        FilterableQueryDescriptor queryDescriptor2 =

            (FilterableQueryDescriptor)getBindings().getInvoiceResultsPopup().getFilterModel();

        if (queryDescriptor2 != null && queryDescriptor2.getFilterCriteria() != null) {
            queryDescriptor2.getFilterCriteria().clear();
            getBindings().getInvoiceResultsPopup().queueEvent(new QueryEvent(getBindings().getInvoiceResultsPopup(), queryDescriptor2));
        }

        FilterableQueryDescriptor queryDescriptor3 =

            (FilterableQueryDescriptor)getBindings().getInvoiceResultsCollection().getFilterModel();

        if (queryDescriptor3 != null && queryDescriptor3.getFilterCriteria() != null) {
            queryDescriptor3.getFilterCriteria().clear();
            getBindings().getInvoiceResultsCollection().queueEvent(new QueryEvent(getBindings().getInvoiceResultsCollection(), queryDescriptor3));
        }
        
        
            
        FilterableQueryDescriptor queryDescriptor4 =   (FilterableQueryDescriptor)getBindings().getInvoiceResultsCards().getFilterModel();

        if (queryDescriptor4 != null && queryDescriptor4.getFilterCriteria() != null) {
            queryDescriptor4.getFilterCriteria().clear();
            getBindings().getInvoiceResultsCollection().queueEvent(new QueryEvent(getBindings().getInvoiceResultsCards(), queryDescriptor4));
        }
        

        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvoiceResults());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvoiceResultsCards());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvoiceResultsPopup());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvoiceResultsCollection());


    }


    public String confirmationCancelAction() {

        getBindings().getConfirmationExcel().hide();
        return null;
    }


    public void setShuttleStatus(boolean shuttleStatus) {
        this.shuttleStatus = shuttleStatus;
    }

    public boolean isShuttleStatus() {
        return shuttleStatus;
    }

    public void setStrInvoicesPrepopulatedColumns(String strInvoicesPrepopulatedColumns) {
        this.strInvoicesPrepopulatedColumns = strInvoicesPrepopulatedColumns;
    }

    public String getStrInvoicesPrepopulatedColumns() {
        return strInvoicesPrepopulatedColumns;
    }

    public void setStrInvoicesTotalColumns(String strInvoicesTotalColumns) {
        this.strInvoicesTotalColumns = strInvoicesTotalColumns;
    }

    public String getStrInvoicesTotalColumns() {
        return strInvoicesTotalColumns;
    }

    public void setStrInvoicesExtraColumns(String strInvoicesExtraColumns) {
        this.strInvoicesExtraColumns = strInvoicesExtraColumns;
    }

    public String getStrInvoicesExtraColumns() {
        return strInvoicesExtraColumns;
    }


    public List getShuttleValue() {

        if (!shuttleStatus) {
            String langDB = (String)session.getAttribute(LANGREPORTLITRERAL);
            if (langDB.equalsIgnoreCase(Constants.LANGUAGE_ENGLISH)) {
                langDB = "EN";
            } else {
                langDB = langDB.substring(langDB.length() - 2, langDB.length());
                langDB = langDB.toUpperCase();
            }
            shuttleValue = new ArrayList();
            ViewObject prtExportInfoRVO = ADFUtils.getViewObject(PRTEXPORTINFORVO1ITERATORLITRERAL);
            prtExportInfoRVO.setNamedWhereClauseParam(Constants.COUNTRY_CODE_LITERAL, langDB);
            prtExportInfoRVO.setNamedWhereClauseParam("report_Page", Constants.INVOICES_LITERAL);

            prtExportInfoRVO.setNamedWhereClauseParam(SELECT_CRITERIALITRERAL, Constants.DEFAULT_LITERAL);
            prtExportInfoRVO.executeQuery();
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + " PrtExportInfoRVO Estimated Row Count in CardGroup shuttle:" +
                        prtExportInfoRVO.getEstimatedRowCount());
            if (prtExportInfoRVO.getEstimatedRowCount() > 0) {

                while (prtExportInfoRVO.hasNext()) {
                    PrtExportInfoRVORowImpl prtExportRow = (PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                    strInvoicesPrepopulatedColumns = prtExportRow.getPrePopulatedColumns();
                }
            }
            if (strInvoicesPrepopulatedColumns != null) {

                String[] strHead = strInvoicesPrepopulatedColumns.split(Constants.ENGAGE_REPORT_DELIMITER);
                for (int col = 0; col < strHead.length; col++) {
                    shuttleValue.add(strHead[col].trim());
                }
            }
        }
        return shuttleValue;
    }

    public void setShuttleValue(List shuttleValue) {
        this.shuttleValue = shuttleValue;
    }

    public void setShuttleList(List shuttleList) {
        this.shuttleList = shuttleList;
    }

    public List getShuttleList() {
        return shuttleList;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        if (Constants.XLS_LITERAL.equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            contentType = "application/vnd.ms-excel";
        } else if (Constants.CSV_LITERAL.equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            contentType = Constants.CONTENT_TYPE_LITERAL;
        } else {
            if (CSV2LITRERAL.equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
                contentType = Constants.CONTENT_TYPE_LITERAL;
            }
        }

        return contentType;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {

        if (Constants.XLS_LITERAL.equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            fileName = "Invoice_Report.xls";
        } else if (Constants.CSV_LITERAL.equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            fileName = "Invoice_Report.csv";
        } else {
            if (CSV2LITRERAL.equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
                fileName = "Invoice_Report.csv";
            }
        }
        return fileName;
    }


    public String getTransactionFileName() {

        if (Constants.XLS_LITERAL.equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            fileName = "Transaction_Report.xls";
        } else if (Constants.CSV_LITERAL.equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            fileName = TRANSACTIONREPORTLITRERAL;
        } else {
            if (CSV2LITRERAL.equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
                fileName = TRANSACTIONREPORTLITRERAL;
            }
        }
        return fileName;
    }

    public void setPartnerValue(List<String> partnerValue) {
        this.partnerValue = partnerValue;
    }

    public List<String> getPartnerValue() {
        return partnerValue;
    }

    public void setPartnerReq(String partnerReq) {
        this.partnerReq = partnerReq;
    }

    public String getPartnerReq() {
        return partnerReq;
    }

    public void setCardGroupRadio(String cardGroupRadio) {
        this.cardGroupRadio = cardGroupRadio;
    }

    public String getCardGroupRadio() {
        return cardGroupRadio;
    }

    public void setShuttleListTransaction(List shuttleListTransaction) {
        this.shuttleListTransaction = shuttleListTransaction;
    }

    public List getShuttleListTransaction() {
        return shuttleListTransaction;
    }


    public void setShuttleValueTransaction(List shuttleValueTransaction) {
        this.shuttleValueTransaction = shuttleValueTransaction;
    }

    public List getShuttleValueTransaction() {

        if (!shuttleStatusTransaction) {
            String langDB = (String)session.getAttribute(LANGREPORTLITRERAL);
            if (langDB.equalsIgnoreCase(Constants.LANGUAGE_ENGLISH)) {
                langDB = "EN";
            } else {
                langDB = langDB.substring(langDB.length() - 2, langDB.length());
                langDB = langDB.toUpperCase();
            }

            shuttleValueTransaction = new ArrayList();
            ViewObject prtExportInfoRVO = ADFUtils.getViewObject(PRTEXPORTINFORVO1ITERATORLITRERAL);
            prtExportInfoRVO.setNamedWhereClauseParam(Constants.COUNTRY_CODE_LITERAL, langDB);
            prtExportInfoRVO.setNamedWhereClauseParam("report_Page", "TRANSACTION");
            prtExportInfoRVO.setNamedWhereClauseParam("report_Type", Constants.DEFAULT_LITERAL);
            prtExportInfoRVO.setNamedWhereClauseParam(SELECT_CRITERIALITRERAL, Constants.DEFAULT_LITERAL);
            prtExportInfoRVO.executeQuery();
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + " PrtExportInfoRVO Estimated Row Count in CardGroup shuttle:" +
                        prtExportInfoRVO.getEstimatedRowCount());

            if (prtExportInfoRVO.getEstimatedRowCount() > 0) {
                while (prtExportInfoRVO.hasNext()) {
                    PrtExportInfoRVORowImpl prtExportRow = (PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                    strTransactionPrepopulatedColumns = prtExportRow.getPrePopulatedColumns();
                }
            }
            if (strTransactionPrepopulatedColumns != null) {
                shuttleStatusTransaction = true;
                String[] strHead = strTransactionPrepopulatedColumns.split(Constants.ENGAGE_REPORT_DELIMITER);
                for (int col = 0; col < strHead.length; col++) {
                    shuttleValueTransaction.add(strHead[col].trim());
                }
            }
        }


        return shuttleValueTransaction;
    }

    public void setStrTransactionPrepopulatedColumns(String strTransactionPrepopulatedColumns) {
        this.strTransactionPrepopulatedColumns = strTransactionPrepopulatedColumns;
    }

    public String getStrTransactionPrepopulatedColumns() {
        return strTransactionPrepopulatedColumns;
    }

    public void setStrTransactionTotalColumns(String strTransactionTotalColumns) {
        this.strTransactionTotalColumns = strTransactionTotalColumns;
    }

    public String getStrTransactionTotalColumns() {
        return strTransactionTotalColumns;
    }

    public void setStrTransactionExtraColumns(String strTransactionExtraColumns) {
        this.strTransactionExtraColumns = strTransactionExtraColumns;
    }

    public String getStrTransactionExtraColumns() {
        return strTransactionExtraColumns;
    }


    public String getContentTypeTransaction() {

        if (Constants.XLS_LITERAL.equalsIgnoreCase(getBindings().getTransactionSelectionExportOneRadio().getValue().toString())) {
            contentTypeTransaction = "application/vnd.ms-excel";
        } else if (Constants.CSV_LITERAL.equalsIgnoreCase(getBindings().getTransactionSelectionExportOneRadio().getValue().toString())) {
            contentTypeTransaction = Constants.CONTENT_TYPE_LITERAL;
        } else {
            if (CSV2LITRERAL.equalsIgnoreCase(getBindings().getTransactionSelectionExportOneRadio().getValue().toString())) {
                contentTypeTransaction = Constants.CONTENT_TYPE_LITERAL;
            }
        }
        return contentTypeTransaction;
    }

    public void setTransactionFileNamefileName(String transactionFileNamefileName) {
        this.transactionFileNamefileName = transactionFileNamefileName;
    }

    public String getTransactionFileNamefileName() {


        if (Constants.XLS_LITERAL.equalsIgnoreCase(getBindings().getTransactionSelectionExportOneRadio().getValue().toString())) {
            transactionFileNamefileName = "Transaction_Report.xls";
        } else if (Constants.CSV_LITERAL.equalsIgnoreCase(getBindings().getTransactionSelectionExportOneRadio().getValue().toString())) {
            transactionFileNamefileName = TRANSACTIONREPORTLITRERAL;
        } else {
            if (CSV2LITRERAL.equalsIgnoreCase(getBindings().getTransactionSelectionExportOneRadio().getValue().toString())) {
                transactionFileNamefileName = TRANSACTIONREPORTLITRERAL;
            }
        }
        return transactionFileNamefileName;
    }


    public void executeInvoiceTransactions(String invoiceType, String invoiceNumber) {
        ViewObject cardTransactionVO = ADFUtils.getViewObject(PRTCARDTRANSACTIONINVOICERVO1ITERATORLITRERAL);

        if (invoiceNumber != null && invoiceType != null) {
            if (invoiceType.equals("FAK") || invoiceType.equals(Constants.ENGAGE_INVOICE_TX_LINK)) {
                cardTransactionVO.setWhereClause("INVOICE_NUMBER_NON_COLLECTIVE =:nonCollecInvNo and pals_country_code=:country_code");
                cardTransactionVO.defineNamedWhereClauseParam("nonCollecInvNo", invoiceNumber, null);
                cardTransactionVO.defineNamedWhereClauseParam("country_code", lang, null);
            } else {
                if (invoiceType.equals("SAM")) {

                    cardTransactionVO.setWhereClause("INVOICE_NUMBER_COLLECTIVE =:collecInvNo and pals_country_code=:country_code");
                    cardTransactionVO.defineNamedWhereClauseParam("collecInvNo", invoiceNumber, null);
                    cardTransactionVO.defineNamedWhereClauseParam("country_code", lang, null);
                }
            }
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "cardTransaction Query=" + cardTransactionVO.getQuery());
            cardTransactionVO.executeQuery();
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "cardTransactionVO estimatedRow:" + cardTransactionVO.getEstimatedRowCount());
        }
    }

    public void removeDynamicconditionOnTxQuery() {
        ViewObject cardTransactionVO = ADFUtils.getViewObject(PRTCARDTRANSACTIONINVOICERVO1ITERATORLITRERAL);
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

    }

    public void setContentTypeTransaction(String contentTypeTransaction) {
        this.contentTypeTransaction = contentTypeTransaction;
    }

    public void setShowNonCollectiveInvoicePanel(boolean showNonCollectiveInvoicePanel) {
        this.showNonCollectiveInvoicePanel = showNonCollectiveInvoicePanel;
    }

    public boolean isShowNonCollectiveInvoicePanel() {
        return showNonCollectiveInvoicePanel;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setComparator(Comparator<SelectItem> comparator) {
        this.comparator = comparator;
    }

    public Comparator<SelectItem> getComparator() {
        return comparator;
    }

    public void setIsCardGroup(boolean isCardGroup) {
        this.isCardGroup = isCardGroup;
    }

    public boolean isIsCardGroup() {
        return isCardGroup;
    }

    private void removeWhereClauseForCardInvoice() {
        System.out.println("Entering the Invoice removeWhereClause::::::::::::");
        ViewObject invoiceCardVO = ADFUtils.getViewObject(PRTNEWINVOICECARDVO1ITERATORLITRERAL);
        if (cardQuery.length() > 1 && cardQuery != null) {

                if (((accountQuery2 + "AND " + cardQuery + "AND " + generalQuery2).trim().equalsIgnoreCase(invoiceCardVO.getWhereClause())) ||
                    ((accountQuery2 + " AND " + cardQuery + "AND " + generalQuery2).trim().equalsIgnoreCase(invoiceCardVO.getWhereClause()))) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "inside  card with out purchase code where removal class");
                if (mapAccountListValue2 != null) {
                    for (int i = 0; i < mapAccountListValue2.size(); i++) {

                        invoiceCardVO.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL + i);
                    }
                } else {
                    invoiceCardVO.removeNamedWhereClauseParam(Constants.ACCOUNT_LITERAL);
                }
                if (mapCardListValue != null) {
                    for (int i = 0; i < mapCardListValue.size(); i++) {
                        invoiceCardVO.removeNamedWhereClauseParam(Constants.CARDLITERAL + i);
                    }

                } else {
                    invoiceCardVO.removeNamedWhereClauseParam(Constants.CARDLITERAL);
                }
                invoiceCardVO.removeNamedWhereClauseParam("ccode");
                invoiceCardVO.removeNamedWhereClauseParam("partner_id");
                invoiceCardVO.removeNamedWhereClauseParam("fromDateBV");
                invoiceCardVO.removeNamedWhereClauseParam("toDateBV");
                invoiceCardVO.setWhereClause("");
                invoiceCardVO.executeQuery();
            }
        }
    }

    private long applyWhereClauseForCardInvoice(boolean b, String newFromDate, String newToDate) {
        String accountTemp = "";
        String cardTemp = "";
        String partnerList = "";
        partnerList = populateStringValues(getPartnerValue().toString());
        ViewObject invoiceCardVO = ADFUtils.getViewObject(PRTNEWINVOICECARDVO1ITERATORLITRERAL);
        accountQuery2 = "(";
        cardQuery = "(";
        
        accountTemp = populateStringValues(getBindings().getAccount().getValue().toString());
        cardTemp = populateStringValues(getBindings().getCard().getValue().toString());
        
        
        generalQuery2= "INSTR(:partner_id,partner_id)<>0  AND COUNTRY_CODE =:ccode AND INVOICING_DATE >=:fromDateBV AND INVOICING_DATE <=:toDateBV AND invoice_level is not null";
        invoiceCardVO.defineNamedWhereClauseParam("ccode", lang,null);
        invoiceCardVO.defineNamedWhereClauseParam("partner_id",partnerList,null);
        invoiceCardVO.defineNamedWhereClauseParam("fromDateBV", newFromDate,null);
        invoiceCardVO.defineNamedWhereClauseParam("toDateBV", newToDate,null);

        if (accountValue.size() > Constants.ONEFIFTY) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
            mapAccountListValue2 = ValueListSplit.callValueList(accountValue.size(), accountValue);
            for (int i = 0; i < mapAccountListValue2.size(); i++) {
                String values = Constants.ACCOUNT_LITERAL + i;
                accountQuery2 = accountQuery2 + "INSTR(:" + values + ",ACCOUNT_ID)<>0 OR ";
            }
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Account Query Values =" + accountQuery);
            accountQuery2 = accountQuery2.substring(0, accountQuery2.length() - Constants.THREE);
            accountQuery2 = accountQuery2 + ")";

        } else {
            mapAccountListValue2 = null;
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
            accountQuery2 = "(INSTR(:account,ACCOUNT_ID)<>0 ) ";
        }


        

                if (cardValue.size() > Constants.ONEFIFTY) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values > 150 ");
                    mapCardListValue = ValueListSplit.callValueList(cardValue.size(), cardValue);
                    for (int i = 0; i < mapCardListValue.size(); i++) {
                        String values = Constants.CARDLITERAL + i;
                        cardQuery = cardQuery + "INSTR(:" + values + ",INVOICED_CARD)<>0 OR ";
                    }
                    cardQuery = cardQuery.substring(0, cardQuery.length() - Constants.THREE);
                    cardQuery = cardQuery + ")";

                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "CARD Query Values =" + cardQuery);
                    invoiceCardVO.setWhereClause(accountQuery + "AND " + cardQuery + "AND " + generalQuery);
                    for (int i = 0; i < mapCardListValue.size(); i++) {
                        invoiceCardVO.defineNamedWhereClauseParam(Constants.CARDLITERAL + i, mapCardListValue.get(Constants.LISTNAME_LITERAL + i),
                                                              null);
                    }

                } else {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CARD Values < 150 ");
                    mapCardListValue = null;
                    cardQuery = "(INSTR(:card,INVOICED_CARD)<>0)";
                    invoiceCardVO.setWhereClause(accountQuery + "AND " + cardQuery + "AND " + generalQuery);
        //                    String cardValuesList = populateStringValues(cardTemp);
                    invoiceCardVO.defineNamedWhereClauseParam(Constants.CARDLITERAL, cardTemp, null);
                }
            
        
        if (accountValue.size() > Constants.ONEFIFTY) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
            mapAccountListValue2 = ValueListSplit.callValueList(accountValue.size(), accountValue);
            for (int i = 0; i < mapAccountListValue2.size(); i++) {

                invoiceCardVO.defineNamedWhereClauseParam(Constants.ACCOUNT_LITERAL + i, mapAccountListValue2.get(Constants.LISTNAME_LITERAL + i),
                                                      null);
            }

        } else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
            invoiceCardVO.defineNamedWhereClauseParam(Constants.ACCOUNT_LITERAL,accountTemp, null);
        }
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Query Formed is=" + invoiceCardVO.getQuery());
        invoiceCardVO.executeQuery();
        
        System.out.println("estimatedRowCount in ApplyWhereCaluse:::"+invoiceCardVO.getEstimatedRowCount());
        
        return invoiceCardVO.getEstimatedRowCount();
    }

    public void setIsCard(boolean isCard) {
        this.isCard = isCard;
    }

    public boolean isIsCard() {
        return isCard;
    }

    public void setPopUpInvoice(String popUpInvoice) {
        this.popUpInvoice = popUpInvoice;
    }

    public String getPopUpInvoice() {
        return popUpInvoice;
    }

    public void setPopUpInvoiceDate(String popUpInvoiceDate) {
        this.popUpInvoiceDate = popUpInvoiceDate;
    }

    public String getPopUpInvoiceDate() {
        return popUpInvoiceDate;
    }

    public void setPopUpInvoiceNet(String popUpInvoiceNet) {
        this.popUpInvoiceNet = popUpInvoiceNet;
    }

    public String getPopUpInvoiceNet() {
        return popUpInvoiceNet;
    }

    public void setPopUpInvoiceVat(String popUpInvoiceVat) {
        this.popUpInvoiceVat = popUpInvoiceVat;
    }

    public String getPopUpInvoiceVat() {
        return popUpInvoiceVat;
    }

    public void setPopUpInvoiceGross(String popUpInvoiceGross) {
        this.popUpInvoiceGross = popUpInvoiceGross;
    }

    public String getPopUpInvoiceGross() {
        return popUpInvoiceGross;
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
        private RichTable invoiceResultsPopup;
        private RichSelectManyChoice partnerNumber;
        private RichPopup confirmationExcel;
        private RichSelectOneRadio selectionExportOneRadio;
        private RichPopup specificColumns;
        private RichSelectManyShuttle shuttleExcel;
        private RichTable invoiceResultsCollection;
        private RichSelectOneRadio radioBtnPopUp;
        private RichPanelGroupLayout transactionPanel;
        private RichPanelGroupLayout invoiceCollectionPanel;
        private RichOutputText collectiveInvoNoOt;
        private RichPopup confirmationMailPopup;
        private RichInputText emailRecipientPopup;
        private RichOutputText invoiceForm;
        private RichPopup specificColumnsTransactions;
        private RichSelectManyShuttle shuttleExcelTransactions;
        private RichSelectOneRadio transactionSelectionExportOneRadio;
        private RichPanelGroupLayout nonInvoiceTransactionPanel;
        private RichTable nonInvoiceCollectiveTxTable;
        private RichOutputText popUpInvoiceDate;
        private RichOutputText popUpInvNet;
        private RichOutputText popUpInvVat;
        private RichOutputText popUpInvGross;
        private RichTable invoiceResultsCards;

        public void setPopUpInvoiceDate(RichOutputText popUpInvoiceDate) {
            this.popUpInvoiceDate = popUpInvoiceDate;
        }

        public RichOutputText getPopUpInvoiceDate() {
            return popUpInvoiceDate;
        }

        public void setRadioBtnPopUp(RichSelectOneRadio radioBtnPopUp) {
            this.radioBtnPopUp = radioBtnPopUp;
        }

        public RichSelectOneRadio getRadioBtnPopUp() {
            return radioBtnPopUp;
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

        public void setCollectiveInvoNoOt(RichOutputText collectiveInvoNoOt) {
            this.collectiveInvoNoOt = collectiveInvoNoOt;
        }

        public RichOutputText getCollectiveInvoNoOt() {
            return collectiveInvoNoOt;
        }


        public void setConfirmationMailPopup(RichPopup confirmationMailPopup) {
            this.confirmationMailPopup = confirmationMailPopup;
        }

        public RichPopup getConfirmationMailPopup() {
            return confirmationMailPopup;
        }


        public void setPopUpInvNet(RichOutputText popUpInvNet) {
            this.popUpInvNet = popUpInvNet;
        }

        public RichOutputText getPopUpInvNet() {
            return popUpInvNet;
        }

        public void setPopUpInvVat(RichOutputText popUpInvVat) {
            this.popUpInvVat = popUpInvVat;
        }

        public RichOutputText getPopUpInvVat() {
            return popUpInvVat;
        }

        public void setPopUpInvGross(RichOutputText popUpInvGross) {
            this.popUpInvGross = popUpInvGross;
        }

        public RichOutputText getPopUpInvGross() {
            return popUpInvGross;
        }


        public void setInvoiceForm(RichOutputText invoiceForm) {
            this.invoiceForm = invoiceForm;
        }

        public RichOutputText getInvoiceForm() {
            return invoiceForm;
        }

        public void setSpecificColumnsTransactions(RichPopup specificColumnsTransactions) {
            this.specificColumnsTransactions = specificColumnsTransactions;
        }

        public RichPopup getSpecificColumnsTransactions() {
            return specificColumnsTransactions;
        }

        public void setShuttleExcelTransactions(RichSelectManyShuttle shuttleExcelTransactions) {
            this.shuttleExcelTransactions = shuttleExcelTransactions;
        }

        public RichSelectManyShuttle getShuttleExcelTransactions() {
            return shuttleExcelTransactions;
        }

        public void setTransactionSelectionExportOneRadio(RichSelectOneRadio transactionSelectionExportOneRadio) {
            this.transactionSelectionExportOneRadio = transactionSelectionExportOneRadio;
        }

        public RichSelectOneRadio getTransactionSelectionExportOneRadio() {
            return transactionSelectionExportOneRadio;
        }


        public void setInvoiceResultsCollection(RichTable invoiceResultsCollection) {
            this.invoiceResultsCollection = invoiceResultsCollection;
        }

        public RichTable getInvoiceResultsCollection() {
            return invoiceResultsCollection;
        }

        public void setInvoiceType(RichSelectOneChoice invoiceType) {
            this.invoiceType = invoiceType;
        }

        public RichSelectOneChoice getInvoiceType() {
            return invoiceType;
        }

        public void setFromDate(RichInputDate fromDate) {
            if (resultFromTo) {

                Date dateNow = new java.util.Date();
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(dateNow);
                gc.add(GregorianCalendar.MONTH, -1);
                Date dateBefore = gc.getTime();
                SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
                String tmp = dateformat.format(dateBefore);
                fromDate.setValue(tmp);
                resultFromTo = false;
            }

            this.fromDate = fromDate;


        }

        public RichInputDate getFromDate() {
            return fromDate;
        }

        public void setToDate(RichInputDate toDate) {
            if (resultToFrom) {

                Date dateNow = new java.util.Date();
                SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
                String tmp = dateformat.format(dateNow);
                toDate.setValue(tmp);
                resultToFrom = false;
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

        public void setPartnerNumber(RichSelectManyChoice partnerNumber) {
            this.partnerNumber = partnerNumber;
        }

        public RichSelectManyChoice getPartnerNumber() {
            return partnerNumber;
        }

        public void setAccount(RichSelectManyChoice account) {
            this.account = account;
        }

        public RichSelectManyChoice getAccount() {
            return account;
        }

        public void setSelectionExportOneRadio(RichSelectOneRadio selectionExportOneRadio) {
            this.selectionExportOneRadio = selectionExportOneRadio;
        }

        public RichSelectOneRadio getSelectionExportOneRadio() {
            return selectionExportOneRadio;
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

        public void setInvoiceResultsPopup(RichTable invoiceResultsPopup) {
            this.invoiceResultsPopup = invoiceResultsPopup;
        }

        public RichTable getInvoiceResultsPopup() {
            return invoiceResultsPopup;
        }

        public void setNonInvoiceTransactionPanel(RichPanelGroupLayout nonInvoiceTransactionPanel) {
            this.nonInvoiceTransactionPanel = nonInvoiceTransactionPanel;
        }

        public RichPanelGroupLayout getNonInvoiceTransactionPanel() {
            return nonInvoiceTransactionPanel;
        }

        public void setNonInvoiceCollectiveTxTable(RichTable nonInvoiceCollectiveTxTable) {
            this.nonInvoiceCollectiveTxTable = nonInvoiceCollectiveTxTable;
        }

        public RichTable getNonInvoiceCollectiveTxTable() {
            return nonInvoiceCollectiveTxTable;
        }

        public void setEmailRecipientPopup(RichInputText emailRecipientPopup) {


            if (setreceipent) {
                if (session != null) {
                    if (null != session.getAttribute(Constants.SESSION_USER_INFO)) {
                        globalUser = (User)session.getAttribute(Constants.SESSION_USER_INFO);
                        if (globalUser.getEmailID() != null) {
                            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Invoice bean : " + "user email id in my profile bean " +
                                        globalUser.getEmailID());
                            emailRecipientPopup.setValue(globalUser.getEmailID().trim());

                        } else {
                            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Invoice bean : " + "user first name in my profile bean " +
                                        globalUser.getFirstName());
                            emailRecipientPopup.setValue(globalUser.getFirstName().toString().trim());
                        }
                    }


                } else {
                    emailRecipientPopup.setValue("");
                }
                setreceipent = false;


            }

            this.emailRecipientPopup = emailRecipientPopup;
        }

        public RichInputText getEmailRecipientPopup() {
            return emailRecipientPopup;
        }

        public void setInvoiceResultsCards(RichTable invoiceResultsCards) {
            this.invoiceResultsCards = invoiceResultsCards;
        }

        public RichTable getInvoiceResultsCards() {
            return invoiceResultsCards;
        }


    }
}

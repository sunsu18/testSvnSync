package com.sfr.engage.authenticatedhometaskflow;


import com.sfr.core.bean.User;
import com.sfr.engage.core.Messages;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.model.queries.rvo.PrtCustomerCardMapRVO1RowImpl;
import com.sfr.engage.model.queries.rvo.PrtGenStringRVORowImpl;

import com.sfr.engage.model.queries.rvo.PrtPcmFeedsRVORowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.ADFUtils;

import com.sfr.engage.vehicleinfotaskflow.VehicleInfoBean;

import com.sfr.util.AccessDataControl;

import com.sfr.util.constants.Constants;
import com.sfr.util.validations.Conversion;

import java.io.Serializable;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Locale;

import java.util.ResourceBundle;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTree;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adf.view.rich.component.rich.output.RichSpacer;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

public class AuthenticatedHomeBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    private String infoValue = "";
     //TODO : Message class in EngageCore is not seriliazed.Make it serilizable class
    private List<Messages> messages;
    private boolean infoPanelVisible;
    private String customerTypeValue;
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();
    private PartnerInfo partnerInfo;
    private ArrayList<String> cards=new ArrayList<String>();
    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    private String country=null;
    private Locale locale;
    Conversion conversionUtility;
    private String emptyText=null;
    private User user = null;
    private boolean roleCsr = false;
    private RichPanelGroupLayout authenticatedPanel;
    ResourceBundle resourceBundle;
    private boolean authenticatedPanelVisible =false;
    private boolean invoicesPanel = false; 
    private boolean webshopPanel = false;
    private RichTree adfTree;

    /**
     * @return bindings Object
     */
    public Bindings getBindings() {
        if (bindings == null) {
            bindings = new Bindings();
        }
        return bindings;
    }
    
    //TODO : Add java docs for all methods.
    public AuthenticatedHomeBean() {
        conversionUtility = new Conversion();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        resourceBundle = new EngageResourceBundle();
        Date date = new Date();
        java.sql.Date passedDate = new java.sql.Date(date.getTime());
        ViewObject prtCustomerCardMapVO =
            ADFUtils.getViewObject("PrtCustomerCardMapRVO1_1Iterator");
        prtCustomerCardMapVO.setNamedWhereClauseParam("cardType", "FA1");
        prtCustomerCardMapVO.executeQuery();
        if (prtCustomerCardMapVO.getEstimatedRowCount() != 0) {
            while (prtCustomerCardMapVO.hasNext()) {
                PrtCustomerCardMapRVO1RowImpl currRow =
                    (PrtCustomerCardMapRVO1RowImpl)prtCustomerCardMapVO.next();
                if (currRow != null) {
                    customerTypeValue = currRow.getCustomerType();
                }
            }
        }
        //TODO : This block could be surrounded by try catch block.
        //TODO : Check if the below queries can be merged and make it one, otherwise okay.

        try {
            if (customerTypeValue != null) {
                ViewObject prtPCMFeedsVO =
                    ADFUtils.getViewObject("PrtPcmFeedsRVO1Iterator");
                prtPCMFeedsVO.setWhereClause("CUSTOMER_TYPE =: customerType AND INFORMATION_TYPE =:infoType AND COUNTRY_CODE=:countryCode AND EFFECTIVE_DATE <=:fromDate AND END_DATE >=:toDate");
                prtPCMFeedsVO.defineNamedWhereClauseParam("customerType",
                                                          customerTypeValue,
                                                          null);
                prtPCMFeedsVO.defineNamedWhereClauseParam("infoType",
                                                          "INFO_STATOIL",
                                                          null);
                prtPCMFeedsVO.defineNamedWhereClauseParam("countryCode",
                                                          "se_SE", null);
                prtPCMFeedsVO.defineNamedWhereClauseParam("fromDate",
                                                          passedDate, null);
                prtPCMFeedsVO.defineNamedWhereClauseParam("toDate", passedDate,
                                                          null);
                prtPCMFeedsVO.executeQuery();
                log.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Information Row Count " +
                         prtPCMFeedsVO.getEstimatedRowCount());

                if (prtPCMFeedsVO.getEstimatedRowCount() != 0) {
                    infoPanelVisible = true;
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " + "coming inside INFORMATION block");
                    while (prtPCMFeedsVO.hasNext()) {
                        PrtPcmFeedsRVORowImpl currRow =
                            (PrtPcmFeedsRVORowImpl)prtPCMFeedsVO.next();

                        if (currRow != null) {
                            if (currRow.getMessageLang() != null) {
                                infoValue =
                                        infoValue + currRow.getMessageLang();
                            } else {
                                if (currRow.getMessageEnglish() != null) {
                                    infoValue =
                                            infoValue + currRow.getMessageLang();
                                }
                            }
                        }
                    }
                }
                if (infoValue == null) {
                    infoPanelVisible = false;
                }

                prtPCMFeedsVO.defineNamedWhereClauseParam("customerType",
                                                          customerTypeValue,
                                                          null);
                prtPCMFeedsVO.defineNamedWhereClauseParam("infoType",
                                                          "MESSAGES", null);
                prtPCMFeedsVO.defineNamedWhereClauseParam("countryCode",
                                                          "no_NO", null);
                prtPCMFeedsVO.defineNamedWhereClauseParam("fromDate",
                                                          passedDate, null);
                prtPCMFeedsVO.defineNamedWhereClauseParam("toDate", passedDate,
                                                          null);
                prtPCMFeedsVO.executeQuery();
                log.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Messages Row Count " +
                         prtPCMFeedsVO.getEstimatedRowCount());
                if (prtPCMFeedsVO.getEstimatedRowCount() != 0) {
                    messages = new ArrayList<Messages>();
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " + "coming inside MESSAGE block");
                    while (prtPCMFeedsVO.hasNext()) {
                        PrtPcmFeedsRVORowImpl currRow =
                            (PrtPcmFeedsRVORowImpl)prtPCMFeedsVO.next();

                        if (currRow != null) {
                            Messages message = new Messages();
                            if (currRow.getMessageLang() != null) {
                                message.setMessage(currRow.getMessageLang());
                            } else {
                                if (currRow.getMessageEnglish() != null) {
                                    message.setMessage(currRow.getMessageEnglish());
                                }
                            }
                            if (message.getMessage() != null) {
                                messages.add(message);
                            }
                        }
                    }
                }

                if ("CUSTOMER_TYPE =: customerType AND INFORMATION_TYPE =:infoType AND COUNTRY_CODE=:countryCode AND EFFECTIVE_DATE <=:fromDate AND END_DATE >=:toDate".equalsIgnoreCase(prtPCMFeedsVO.getWhereClause())) {
                    prtPCMFeedsVO.removeNamedWhereClauseParam("customerType");
                    prtPCMFeedsVO.removeNamedWhereClauseParam("infoType");
                    prtPCMFeedsVO.removeNamedWhereClauseParam("countryCode");
                    prtPCMFeedsVO.removeNamedWhereClauseParam("fromDate");
                    prtPCMFeedsVO.removeNamedWhereClauseParam("toDate");
                    prtPCMFeedsVO.setWhereClause("");
                    prtPCMFeedsVO.executeQuery();
                }

            }
            if (session.getAttribute("Partner_Object_List") != null) {
                partnerInfo =
                        (PartnerInfo)session.getAttribute("Partner_Object_List");
            }

            if (partnerInfo != null) {
                log.fine(accessDC.getDisplayRecord() + this.getClass() + " " +
                         "Inside partner info object");

                if (partnerInfo.getCountry() != null) {

                    country = partnerInfo.getCountry().toString();


                } else {
                    country = "SE";
                }
                locale = conversionUtility.getLocaleFromCountryCode(country);


                if (partnerInfo.getAccountList() != null &&
                    partnerInfo.getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfo.getAccountList().size();
                         i++) {
                        if (partnerInfo.getAccountList().get(i) != null) {
                            if (partnerInfo.getAccountList().get(i).getCardGroup() !=
                                null &&
                                partnerInfo.getAccountList().get(i).getCardGroup().size() >
                                0) {
                                for (int k = 0;
                                     k < partnerInfo.getAccountList().get(i).getCardGroup().size();
                                     k++) {
                                    if (partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard() !=
                                        null &&
                                        partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().size() >
                                        0) {
                                        for (int m = 0;
                                             m < partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().size();
                                             m++) {
                                            cards.add(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID());
                                            System.out.println("CardList--->" +
                                                               partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID());


                                        }
                                    }
                                }


                            }
                        }
                    }
                }
                String idList = cards.toString();
                String cardId =
                    idList.substring(1, idList.length() - 1).replace(" ", "");

                System.out.println("arraylist after conversion is " + cardId);

                if (session != null) {
                user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
                roleCsr = false;
                if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_SFR) || user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) || user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN))
                { 
                authenticatedPanelVisible = true;
                invoicesPanel = true;
                 System.out.println("Higher roles found"); 
            
                }
                else if(user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_EMP)) {
                authenticatedPanelVisible = true;
                invoicesPanel = false;
                System.out.println("B2B Employee role found");

                }else if(user.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR) || user.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)) {
                authenticatedPanelVisible = false;
                invoicesPanel = false;
                System.out.println("CSR role found");
                }else if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_JET)|| user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_PETRO)){
                    authenticatedPanelVisible = true;
                    invoicesPanel = true;
                    webshopPanel = false;
                }

                    if(!authenticatedPanelVisible) {

                    //authenticatedPanel.setVisible(false);
                    //AdfFacesContext.getCurrentInstance().addPartialTarget(authenticatedPanel);
                    System.out.println("Authenticated Panel should not be visible");
                    //authenticatedPanelVisible = false;



                    } 
                    else{
                     System.out.println("Authenticated Panel should be visible"); 

                    searchInvoices(cardId, country);
                    searchTransactions(cardId, country);

                    }

                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }


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
    
    
    /**
     * @param messages
     */
    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

    /**
     * @return
     */
    public List<Messages> getMessages() {
        return messages;
    }

    public void setInfoPanelVisible(boolean infoPanelVisible) {
        this.infoPanelVisible = infoPanelVisible;
    }

    public boolean isInfoPanelVisible() {
        return infoPanelVisible;
    }

    public void setInfoValue(String infoValue) {
        this.infoValue = infoValue;
    }

    public String getInfoValue() {
        return infoValue;
    }

    public void setCustomerTypeValue(String customerTypeValue) {
        this.customerTypeValue = customerTypeValue;
    }

    public String getCustomerTypeValue() {
        return customerTypeValue;
    }

    private void searchInvoices(String cardId, String country) {
        ViewObject vo = ADFUtils.getViewObject("PrtHomeInvoiceRVO1Iterator");
        
        vo.setNamedWhereClauseParam("countryCode", country);
        vo.setNamedWhereClauseParam("cards", cardId);
        vo.executeQuery();
         
                     if (vo.getEstimatedRowCount() != 0) {
                         log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Inside Estimated row count" + vo.getEstimatedRowCount());
                         
                         }
                     else{
                        
                         emptyText = (String)resourceBundle.getObject("NO_INVOICES_TO_DISPLAY");
                     }
        
        
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
    }

    public String getEmptyText() {
        return emptyText;
    }


    public void setRoleCsr(boolean roleCsr) {
        this.roleCsr = roleCsr;
    }

    public boolean isRoleCsr() {
        return roleCsr;
    }

    public void setAuthenticatedPanel(RichPanelGroupLayout authenticatedPanel) {
        this.authenticatedPanel = authenticatedPanel;
    }

    public RichPanelGroupLayout getAuthenticatedPanel() {
        return authenticatedPanel;
    }

    public void setAuthenticatedPanelVisible(boolean authenticatedPanelVisible) {
        this.authenticatedPanelVisible = authenticatedPanelVisible;
    }

    public boolean isAuthenticatedPanelVisible() {
        return authenticatedPanelVisible;
    }

    public void setInvoicesPanel(boolean invoicesPanel) {
        this.invoicesPanel = invoicesPanel;
    }

    public boolean isInvoicesPanel() {
        return invoicesPanel;
    }

    private void searchTransactions(String cardId, String country) {
        ViewObject latestTransactionVO = ADFUtils.getViewObject("PrtHomeTransactions1Iterator");
        System.out.println("Country code in Transaction VO" + country);
        System.out.println("Arraylist in Transaction VO " + cardId);
        
        //latestTransactionVO.setWhereClause("INSTR(:cards,KSID)<>0");
        latestTransactionVO.setNamedWhereClauseParam("countryCode", country);
        latestTransactionVO.setNamedWhereClauseParam("cards", cardId);
        //latestTransactionVO.defineNamedWhereClauseParam("cards", cardId, null);
        
        System.out.println(latestTransactionVO.getQuery());
        
        latestTransactionVO.executeQuery();
        if (latestTransactionVO.getEstimatedRowCount() != 0) {
            log.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Inside Estimated row count latestTransactionVO" + latestTransactionVO.getEstimatedRowCount());
            
            }
        else{
           
            emptyText = (String)resourceBundle.getObject("NO_RECORDS_FOUND_TRANSACTIONS");
        }
        
    }

    public void setWebshopPanel(boolean webshopPanel) {
        this.webshopPanel = webshopPanel;
    }

    public boolean isWebshopPanel() {
        return webshopPanel;
    }

    public void setAdfTree(RichTree adfTree) {
        this.adfTree = adfTree;
    }

    public RichTree getAdfTree() {
        return adfTree;
    }


    public class Bindings {
        private RichOutputText infoText;
        private RichPanelGroupLayout infoPanel;
        private RichSpacer bindingSpacer;
        private RichTable invoiceTable;
        public void setInfoText(RichOutputText infoText) {
            this.infoText = infoText;
        }

        public RichOutputText getInfoText() {
            return infoText;
        }

        public void setInfoPanel(RichPanelGroupLayout infoPanel) {
            this.infoPanel = infoPanel;
        }

        public RichPanelGroupLayout getInfoPanel() {
            return infoPanel;
        }
        public void setInvoiceTable(RichTable invoiceTable) {
            this.invoiceTable = invoiceTable;
        }

        public RichTable getInvoiceTable() {
            return invoiceTable;
        }
        
    }
}


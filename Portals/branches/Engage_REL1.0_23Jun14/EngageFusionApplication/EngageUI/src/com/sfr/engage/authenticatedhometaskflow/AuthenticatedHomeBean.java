package com.sfr.engage.authenticatedhometaskflow;


import com.sfr.core.bean.User;
import com.sfr.engage.core.Messages;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.model.queries.rvo.PrtCustomerCardMapRVO1RowImpl;
import com.sfr.engage.model.queries.rvo.PrtPcmFeedsRVORowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.ADFUtils;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;
import com.sfr.util.validations.Conversion;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.share.security.SecurityContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTree;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.component.rich.output.RichSpacer;

import oracle.jbo.ViewObject;


public class AuthenticatedHomeBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    String customerType = "B2B";
    String lang = "SE";
    private String infoValue = "";
     //TODO : Message class in EngageCore is not seriliazed.Make it serilizable class
    private List<Messages> messages;
    private boolean infoPanelVisible;
    private String customerTypeValue;
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();
    private List<PartnerInfo> partnerInfoList;
    private ArrayList<String> partnerId=new ArrayList<String>();
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
    private boolean businessProfile = false;
    private boolean privateProfile = false;
    private String profile = "private";
    private String profileSession = "";
    private String langSession = "";
    SecurityContext securityContext = null;
    ADFContext adfCtx = null;
    private List<PartnerInfo> partnerListDefault = new ArrayList<PartnerInfo>();
    private User userInfo;
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
        log.info(accessDC.getDisplayRecord() + this.getClass() + " constructor of AuthenticatedHomeBean");
        conversionUtility = new Conversion();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        adfCtx = ADFContext.getCurrent();
        securityContext = adfCtx.getSecurityContext();
        if(securityContext.isAuthenticated()){
        if(session!=null)
        {

            if(session.getAttribute("profile")!=null)
            {

                profile = (String)session.getAttribute("profile");

                if (profile.equalsIgnoreCase("business")) {

                    businessProfile = true;
                    privateProfile = false;
                }else if (profile.equalsIgnoreCase("private")) {

                    businessProfile = false;
                    privateProfile = true;

                }
            }

            langSession =(String)session.getAttribute(Constants.userLang);
            log.info(accessDC.getDisplayRecord() + this.getClass() + " langSession" + langSession);

            if (user == null) {
                user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
            }


            if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_SFR)) {
                profileSession = "B2C"; }
            else
            {  profileSession = "B2B"; }




            Conversion conv = new Conversion();
            lang = (String)session.getAttribute("lang");
            log.info(accessDC.getDisplayRecord() + this.getClass() + " lang" + lang);
            profile = (String)session.getAttribute("profile");
            if(profile.equalsIgnoreCase("business"))
                { customerType = "B2B";}
            else
                { customerType = "B2C";}
            DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter;
            if (bindings != null) {
                iter = bindings.findIteratorBinding("ProductsDisplayRVO1Iterator");

            } else {
                log.severe(accessDC.getDisplayRecord() + this.getClass() + " ProductsDisplayRVO1Iterator bindings is null");
                iter = null;
            }
            ViewObject vo = iter.getViewObject();
            // TODO : ASHTHA - 02, May, 2014 : Query hardcodes the params. Instead values fetched from session should be used
            vo.setNamedWhereClauseParam("countryCode", langSession);
            vo.setNamedWhereClauseParam("catalogType", "PP");
            vo.setNamedWhereClauseParam("customerType", profileSession);
            vo.executeQuery();
            log.info(accessDC.getDisplayRecord() + this.getClass() + " Top products count is " + vo.getEstimatedRowCount());
        }
        resourceBundle = new EngageResourceBundle();
        Date date = new Date();
        java.sql.Date passedDate = new java.sql.Date(date.getTime());
        Set<String> cardTypeSet = new HashSet<String>();
        List<String> customerTypeList=new ArrayList<String>();
        if(session.getAttribute("cardTypeList")!=null){

            cardTypeSet = (Set<String>)session.getAttribute("cardTypeList");
        }

        List<String> cardTypeListTemp = new ArrayList<String>(cardTypeSet);
        String cardTypeList= cardTypeListTemp.toString().substring(1, cardTypeListTemp.toString().length()-1).replace("", "");

        ViewObject prtCustomerCardMapVO =
            ADFUtils.getViewObject("PrtCustomerCardMapRVO1_1Iterator");
        prtCustomerCardMapVO.setNamedWhereClauseParam("cardType", cardTypeList);
        prtCustomerCardMapVO.executeQuery();
        if (prtCustomerCardMapVO.getEstimatedRowCount() != 0) {
            while (prtCustomerCardMapVO.hasNext()) {
                PrtCustomerCardMapRVO1RowImpl currRow =
                    (PrtCustomerCardMapRVO1RowImpl)prtCustomerCardMapVO.next();
                if (currRow != null) {
                    customerTypeList.add(currRow.getCustomerType());
                    customerTypeValue=customerTypeList.toString().substring(1, customerTypeList.toString().length()-1).replace("", "");
                }
            }
            customerTypeValue=customerTypeValue + ",ALL";
        }
        //TODO : This block could be surrounded by try catch block.
        //TODO : Check if the below queries can be merged and make it one, otherwise okay.

        try {
            log.info(accessDC.getDisplayRecord() + this.getClass() + " lang passed in PRTPCMFEEDS is "+ conversionUtility.getCustomerCountryCode(langSession));
            if (customerTypeValue != null) {
                ViewObject prtPCMFeedsVO =
                    ADFUtils.getViewObject("PrtPcmFeedsRVO1Iterator");
                prtPCMFeedsVO.setWhereClause("INSTR(:customerType,CUSTOMER_TYPE)<>0  AND INFORMATION_TYPE =:infoType AND COUNTRY_CODE=:countryCode AND EFFECTIVE_DATE <=:fromDate AND END_DATE >=:toDate");
                prtPCMFeedsVO.defineNamedWhereClauseParam("customerType",
                                                          customerTypeValue,
                                                          null);
                prtPCMFeedsVO.defineNamedWhereClauseParam("infoType",
                                                          "INFO_STATOIL",
                                                          null);
                prtPCMFeedsVO.defineNamedWhereClauseParam("countryCode",
                                                          conversionUtility.getCustomerCountryCode(langSession), null);
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
                            if (currRow.getMessageLang().trim() != null) {
                           
                                if(!infoValue.toLowerCase().trim().contains(currRow.getMessageLang().toLowerCase().trim()))
                                infoValue =
                                        infoValue + currRow.getMessageLang();
                            } else {
                                if (currRow.getMessageEnglish().trim() != null && !infoValue.toLowerCase().trim().contains(currRow.getMessageLang().toLowerCase().trim())) {
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
                                                          conversionUtility.getCustomerCountryCode(langSession), null);
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

                if ("INSTR(:customerType,CUSTOMER_TYPE)<>0 AND INFORMATION_TYPE =:infoType AND COUNTRY_CODE=:countryCode AND EFFECTIVE_DATE <=:fromDate AND END_DATE >=:toDate".equalsIgnoreCase(prtPCMFeedsVO.getWhereClause())) {
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

                partnerInfoList = new ArrayList<PartnerInfo>();
                partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
            }

            if (partnerInfoList != null && partnerInfoList.size() > 0) {
                log.fine(accessDC.getDisplayRecord() + this.getClass() + " " + "Inside partner info object");

                if (partnerInfoList.get(0).getCountry() != null) {

                    country = partnerInfoList.get(0).getCountry().toString();
                } else {
                    country = "SE";
                }
                locale = conversionUtility.getLocaleFromCountryCode(country);

                 for(int pa=0 ; pa<partnerInfoList.size() ; pa++){
//                    if (partnerInfoList.get(pa).getPartnerValue() != null && partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() > 0){
//                        for (int ac = 0; ac < partnerInfoList.get(pa).getAccountList().size(); ac++) {
//                            if (partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup() !=null && partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size() > 0){
//                                for (int cg = 0;cg < partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size();cg++){
//                                    if (partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard() != null && partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size()>0){
//                                        for (int cc = 0;cc < partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size(); cc++){
//                                            if(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID() != null){
//                                                cards.add(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID());
//                                                System.out.println("CardList--->" +partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID());
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }


                   partnerId.add(partnerInfoList.get(pa).getPartnerValue());



//                    }
                 }
                String idList = partnerId.toString();
                String partnerIdList =idList.substring(1, idList.length() - 1).replace(" ", "");

                log.info(accessDC.getDisplayRecord() + this.getClass() + " arraylist after conversion is " + partnerIdList);

                if (session != null) {
                user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
                roleCsr = false;
                if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_SFR) || user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) || user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN))
                {
                authenticatedPanelVisible = true;
                invoicesPanel = true;
                 log.info(accessDC.getDisplayRecord() + this.getClass() + " Higher roles found");

                }
                else if(user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_EMP)) {
                authenticatedPanelVisible = true;
                invoicesPanel = false;
                log.info(accessDC.getDisplayRecord() + this.getClass() + " B2B Employee role found");

                }else if(user.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR) || user.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)) {
                authenticatedPanelVisible = false;
                invoicesPanel = false;
                log.info(accessDC.getDisplayRecord() + this.getClass() + " CSR role found");
                }else if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_JET)|| user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_PETRO)){
                    authenticatedPanelVisible = true;
                    invoicesPanel = true;
                    webshopPanel = false;
                }

                    if(!authenticatedPanelVisible) {

                    //authenticatedPanel.setVisible(false);
                    //AdfFacesContext.getCurrentInstance().addPartialTarget(authenticatedPanel);
                    log.info(accessDC.getDisplayRecord() + this.getClass() + " Authenticated Panel should not be visible");
                    //authenticatedPanelVisible = false;



                    }
                    else{
                     log.info(accessDC.getDisplayRecord() + this.getClass() + " Authenticated Panel should be visible");

                    searchInvoices(partnerIdList, country);
                    searchTransactions(partnerIdList, country);

                    }

                }


            }
        } catch (Exception e) {

            e.printStackTrace();
        }


    }
        log.info(accessDC.getDisplayRecord() + this.getClass() + " exiting constructor of AuthenticatedHomeBean");

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

    private void searchInvoices(String partnerId, String country) {
        ViewObject vo = ADFUtils.getViewObject("PrtHomeInvoiceRVO1Iterator");

        vo.setNamedWhereClauseParam("countryCode", country);
        vo.setNamedWhereClauseParam("partnerId", partnerId);
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

    private void searchTransactions(String partnerId, String country) {
        ViewObject latestTransactionVO = ADFUtils.getViewObject("PrtHomeTransactions1Iterator");
        log.info(accessDC.getDisplayRecord() + this.getClass() + " Country code in Transaction VO" + country);
        log.info(accessDC.getDisplayRecord() + this.getClass() + " Arraylist in Transaction VO " + partnerId);

        //latestTransactionVO.setWhereClause("INSTR(:cards,KSID)<>0");
        latestTransactionVO.setNamedWhereClauseParam("countryCode", country);
        latestTransactionVO.setNamedWhereClauseParam("partnerId", partnerId);
        //latestTransactionVO.defineNamedWhereClauseParam("cards", cardId, null);

        //System.out.println(latestTransactionVO.getQuery());

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

    public void setBusinessProfile(boolean businessProfile) {
        this.businessProfile = businessProfile;
    }

    public boolean isBusinessProfile() {
        return businessProfile;
    }

    public void setPrivateProfile(boolean privateProfile) {
        this.privateProfile = privateProfile;
    }

    public boolean isPrivateProfile() {
        return privateProfile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }



//    public String viewAllTransactions() {
//        // Add event code here...
//        try {
//            ectx.redirect(ectx.getRequestContextPath()+"/faces/card/transaction/transactions");
//        } catch (IOException e) {
//            System.out.println(" Error while redirecting to Invoices overview page");
//        }
//        return invoiceoverview;
//    }

//    public void viewAllTransactions() {
//        try {
//            ectx.redirect(ectx.getRequestContextPath()+"/faces/card/transaction/transactions");
//        } catch (IOException e) {
//            System.out.println(" Error while redirecting to Invoices overview page");
//    }
//    }

//    public void viewAllInvoices() {
//        // Add event code here...
//
//
//    }

    public void viewAllInvoices(ActionEvent actionEvent) {
        // Add event code here...

        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);

        try {
            //System.out.println("Request Context ="+ ectx.getRequestContextPath());
            String urlRedirect = request.getContextPath() + "/faces/card/transaction/invoiceoverview";
            ectx.redirect(urlRedirect);
        } catch (IOException e) {
            log.severe(accessDC.getDisplayRecord() + this.getClass() + " Error while redirecting to Invoices overview page");
        }
    }

    public void viewAllTransactions(ActionEvent actionEvent) {
        // Add event code here...

        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);

        try {
            //System.out.println("Request Context ="+ ectx.getRequestContextPath());
            String urlRedirect = request.getContextPath() + "/faces/card/transaction/transactions";
            ectx.redirect(urlRedirect);
        } catch (IOException e) {
            log.severe(accessDC.getDisplayRecord() + this.getClass() +" Error while redirecting to Transactions overview page");
        }
    }

    public void redirectToProductCatalog(ActionEvent actionEvent) {
        // Add event code here...

        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);


        try {
            //System.out.println("Request Context ="+ ectx.getRequestContextPath());
            //String urlRedirect = request.getContextPath() + "/faces/card/transaction/transactions";
            //System.out.println("https://shop.statoilfuelretail.com/WsPortal/faces/sfr/productCatalog?lang="+ session.getAttribute("lang")+"&profile="+session.getAttribute("profile"));
            ectx.redirect("https://shop.statoilfuelretail.com/WsPortal/faces/sfr/productCatalog?lang="+ session.getAttribute("lang")+"&profile="+session.getAttribute("profile"));

        } catch (IOException e) {
            log.severe(accessDC.getDisplayRecord() + this.getClass() +" Error while redirecting to Product Catalog overview page");
        }



    }

    public void setProfileSession(String profileSession) {
        this.profileSession = profileSession;
    }

    public String getProfileSession() {
        return profileSession;
    }

    public void setLangSession(String langSession) {
        this.langSession = langSession;
    }

    public String getLangSession() {
        return langSession;
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


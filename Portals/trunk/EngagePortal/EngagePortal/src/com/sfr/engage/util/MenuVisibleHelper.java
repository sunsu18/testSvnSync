package com.sfr.engage.util;


import com.sfr.core.bean.User;
import com.sfr.engage.model.queries.rvo.PrtCustomerCardMapRVO1RowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.share.security.SecurityContext;

import oracle.jbo.ViewObject;


/* TODO : ASHTHA - 02, May, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC
 *  2. Override toString() method
 *  3. Remove if not needed
 */
public class MenuVisibleHelper {

    private String menuHome = "Home";
    private String menuAccount = "Account";
    private String menuAccountSummary = "Account Summary";
    private String menuVehicles = "Vehicles";
    private String menuDrivers = "Drivers";
    private String menuMessages = "Messages";
    private String menuTransactions = "Transactions";
    private String menuTransactionOverview = "Transaction Overview";
    private String menuInvoiceOverview = "Invoice Overview";
    private String menuAROverview = "AR Overview";
    private String menuCardServices = "Card Services";
    private String menuViewCards = "View Cards";
    private String menuNewCard = "New Cards";
    private String menuProfiles = "Profiles";
    private String menuPricing = "Pricing";
    private String menuAgreements = "Agreements";
    private String menuListPrice = "List Prices";
    private String menuSetup = "Setup";
    private String menuWebProfiles = "Web Profiles";
    private String menuReportSetup = "Report Setup";
    private String menuAlerts = "Alerts";
    private String menuManageUsers = "Manage Users";
    private String menuWebAssist = "Web Assit";
    private String menuSupport = "Support";
    private String menuChangeAccount = "Change Account";
    private String menuContact = "Contact";
    private String menuFAQ = "FAQ";
    private String menuSelectAccount = "Select Account";
    private String menuChangePassword = "Change Password";
    private String menuCustomerView = "Customer View";
    EngageResourceBundle sfr = new EngageResourceBundle();

    private boolean visibleAccount = false;
    private boolean visibleAccountSummary = false;
    private boolean visibleVehicles = false;
    private boolean visibleDrivers = false;
    private boolean visibleTransactions = false;
    private boolean visibleTransactionOverview = false;
    private boolean visibleInvoiceOverview = false;
    private boolean visibleCardServices = false;
    private boolean visibleViewCards = false;
    private boolean visiblePricing = false;
    private boolean visibleListPrice = false;
    private boolean visibleSetup = false;
    private boolean visibleChangePassword = false;

    private User user = null;
    SecurityContext securityContext;
    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    private String lang;

    AccessDataControl accessDC = new AccessDataControl();
    public static final ADFLogger log = AccessDataControl.getSFRLogger();

    public MenuVisibleHelper() {
        super();
        sfr = new EngageResourceBundle();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        securityContext = ADFContext.getCurrent().getSecurityContext();
        if (user == null && session.getAttribute(Constants.SESSION_USER_INFO) != null) {
            user = (User)session.getAttribute(Constants.SESSION_USER_INFO);

            log.info(accessDC.getDisplayRecord() + this.getClass() + "Inside menuVisible helper construstor. user role list is " +user.getRolelist());
        }

        if (session != null) {
            lang = (String)session.getAttribute(Constants.userLang);
        }

    }

    public void setMenuHome(String menuHome) {
        this.menuHome = menuHome;
    }

    public String getMenuHome() {
        if (sfr.containsKey("HOME"))
            menuHome = (String)sfr.getObject("HOME");
        return menuHome;
    }

    public void setMenuAccount(String menuAccount) {
        this.menuAccount = menuAccount;
    }

    public String getMenuAccount() {
        if (sfr.containsKey("ACCOUNT"))
            menuAccount = (String)sfr.getObject("ACCOUNT");

        return menuAccount;
    }

    public void setMenuAccountSummary(String menuAccountSummary) {
        this.menuAccountSummary = menuAccountSummary;
    }

    public String getMenuAccountSummary() {
        if (sfr.containsKey("ACCOUNT_SUMMARY"))
            menuAccountSummary = (String)sfr.getObject("ACCOUNT_SUMMARY");

        return menuAccountSummary;
    }

    public void setMenuVehicles(String menuVehicles) {
        this.menuVehicles = menuVehicles;
    }

    public String getMenuVehicles() {
        if (sfr.containsKey("VEHICLES"))
            menuVehicles = (String)sfr.getObject("VEHICLES");
        return menuVehicles;
    }

    public void setMenuDrivers(String menuDrivers) {
        this.menuDrivers = menuDrivers;
    }

    public String getMenuDrivers() {
        if (sfr.containsKey("DRIVERS"))
            menuDrivers = (String)sfr.getObject("DRIVERS");
        return menuDrivers;
    }

    public void setMenuMessages(String menuMessages) {
        this.menuMessages = menuMessages;
    }

    public String getMenuMessages() {
        if (sfr.containsKey("MESSAGES"))
            menuMessages = (String)sfr.getObject("MESSAGES");
        return menuMessages;
    }

    public void setMenuTransactions(String menuTransactions) {
        this.menuTransactions = menuTransactions;
    }

    public String getMenuTransactions() {
        if (sfr.containsKey("TRANSACTIONS"))
            menuTransactions = (String)sfr.getObject("TRANSACTIONS");
        return menuTransactions;
    }

    public void setMenuTransactionOverview(String menuTransactionOverview) {
        this.menuTransactionOverview = menuTransactionOverview;
    }

    public String getMenuTransactionOverview() {
        if (sfr.containsKey("TRANSACTION_OVERVIEW"))
            menuTransactionOverview = (String)sfr.getObject("TRANSACTION_OVERVIEW");
        return menuTransactionOverview;
    }

    public void setMenuInvoiceOverview(String menuInvoiceOverview) {
        this.menuInvoiceOverview = menuInvoiceOverview;
    }

    public String getMenuInvoiceOverview() {
        if (sfr.containsKey("INVOICE_OVERVIEW"))
            menuInvoiceOverview = (String)sfr.getObject("INVOICE_OVERVIEW");
        return menuInvoiceOverview;
    }

    public void setMenuAROverview(String menuAROverview) {
        this.menuAROverview = menuAROverview;
    }

    public String getMenuAROverview() {
        if (sfr.containsKey("AR_OVERVIEW"))
            menuAROverview = (String)sfr.getObject("AR_OVERVIEW");
        return menuAROverview;
    }

    public void setMenuCardServices(String menuCardServices) {
        this.menuCardServices = menuCardServices;
    }

    public String getMenuCardServices() {
        if (sfr.containsKey("CARD_SERVICES"))
            menuCardServices = (String)sfr.getObject("CARD_SERVICES");
        return menuCardServices;
    }

    public void setMenuViewCards(String menuViewCards) {
        this.menuViewCards = menuViewCards;
    }

    public String getMenuViewCards() {
        if (sfr.containsKey("VIEW_CARDS"))
            menuViewCards = (String)sfr.getObject("VIEW_CARDS");
        return menuViewCards;
    }

    public void setMenuNewCard(String menuNewCard) {
        this.menuNewCard = menuNewCard;
    }

    public String getMenuNewCard() {
        if (sfr.containsKey("NEW_CARD"))
            menuNewCard = (String)sfr.getObject("NEW_CARD");
        return menuNewCard;
    }

    public void setMenuProfiles(String menuProfiles) {
        this.menuProfiles = menuProfiles;
    }

    public String getMenuProfiles() {
        if (sfr.containsKey("PROFILES"))
            menuProfiles = (String)sfr.getObject("PROFILES");
        return menuProfiles;
    }

    public void setMenuPricing(String menuPricing) {
        this.menuPricing = menuPricing;
    }

    public String getMenuPricing() {
        if (sfr.containsKey("PRICING"))
            menuPricing = (String)sfr.getObject("PRICING");
        return menuPricing;
    }

    public void setMenuAgreements(String menuAgreements) {
        this.menuAgreements = menuAgreements;
    }

    public String getMenuAgreements() {
        if (sfr.containsKey("AGREEMENTS"))
            menuAgreements = (String)sfr.getObject("AGREEMENTS");
        return menuAgreements;
    }

    public void setMenuListPrice(String menuListPrice) {
        this.menuListPrice = menuListPrice;
    }

    public String getMenuListPrice() {
        if (sfr.containsKey("LIST_PRICES"))
            menuListPrice = (String)sfr.getObject("LIST_PRICES");
        return menuListPrice;
    }

    public void setMenuSetup(String menuSetup) {
        this.menuSetup = menuSetup;
    }

    public String getMenuSetup() {
        if (sfr.containsKey("SETUP"))
            menuSetup = (String)sfr.getObject("SETUP");
        return menuSetup;
    }

    public void setMenuWebProfiles(String menuWebProfiles) {
        this.menuWebProfiles = menuWebProfiles;
    }

    public String getMenuWebProfiles() {
        if (sfr.containsKey("WEB_PROFILES"))
            menuWebProfiles = (String)sfr.getObject("WEB_PROFILES");
        return menuWebProfiles;
    }

    public void setMenuReportSetup(String menuReportSetup) {
        this.menuReportSetup = menuReportSetup;
    }

    public String getMenuReportSetup() {
        if (sfr.containsKey("REPORT_SETUP"))
            menuReportSetup = (String)sfr.getObject("REPORT_SETUP");
        return menuReportSetup;
    }

    public void setMenuAlerts(String menuAlerts) {
        this.menuAlerts = menuAlerts;
    }

    public String getMenuAlerts() {
        if (sfr.containsKey("ALERTS"))
            menuAlerts = (String)sfr.getObject("ALERTS");
        return menuAlerts;
    }

    public void setMenuManageUsers(String menuManageUsers) {
        this.menuManageUsers = menuManageUsers;
    }

    public String getMenuManageUsers() {
        if (sfr.containsKey("MANAGE_USERS"))
            menuManageUsers = (String)sfr.getObject("MANAGE_USERS");
        return menuManageUsers;
    }

    public void setMenuWebAssist(String menuWebAssist) {
        this.menuWebAssist = menuWebAssist;
    }

    public String getMenuWebAssist() {
        if (sfr.containsKey("WEB_ASSIST"))
            menuWebAssist = (String)sfr.getObject("WEB_ASSIST");
        return menuWebAssist;
    }

    public void setMenuSupport(String menuSupport) {
        this.menuSupport = menuSupport;
    }

    public String getMenuSupport() {
        if (sfr.containsKey("SUPPORT"))
            menuSupport = (String)sfr.getObject("SUPPORT");
        return menuSupport;
    }

    public void setMenuChangeAccount(String menuChangeAccount) {
        this.menuChangeAccount = menuChangeAccount;
    }

    public String getMenuChangeAccount() {
        if (sfr.containsKey("CHANGE_ACCOUNT"))
            menuChangeAccount = (String)sfr.getObject("CHANGE_ACCOUNT");
        return menuChangeAccount;
    }

    public void setMenuContact(String menuContact) {
        this.menuContact = menuContact;
    }

    public String getMenuContact() {
        if (sfr.containsKey("CONTACT"))
            menuContact = (String)sfr.getObject("CONTACT");
        return menuContact;
    }

    public void setMenuFAQ(String menuFAQ) {
        this.menuFAQ = menuFAQ;
    }

    public String getMenuFAQ() {
        if (sfr.containsKey("FAQ_ENG"))
            menuFAQ = (String)sfr.getObject("FAQ_ENG");
        return menuFAQ;
    }

    public void setMenuSelectAccount(String menuSelectAccount) {
        this.menuSelectAccount = menuSelectAccount;
    }

    public String getMenuSelectAccount() {
        if (sfr.containsKey("SELECT_ACCOUNT"))
            menuSelectAccount = (String)sfr.getObject("SELECT_ACCOUNT");
        return menuSelectAccount;
    }

    public void setMenuChangePassword(String menuChangePassword) {
        this.menuChangePassword = menuChangePassword;
    }

    public String getMenuChangePassword() {
        if (sfr.containsKey("ENGAGE_CHANGE_PASSWORD"))
            menuChangePassword = (String)sfr.getObject("ENGAGE_CHANGE_PASSWORD");
        return menuChangePassword;
    }

    public void setMenuCustomerView(String menuCustomerView) {
        this.menuCustomerView = menuCustomerView;
    }

    public String getMenuCustomerView() {
        if (sfr.containsKey("CUSTOMER_VIEW"))
            menuCustomerView = (String)sfr.getObject("CUSTOMER_VIEW");
        return menuCustomerView;
    }

    public void setVisibleAccount(boolean visibleAccount) {
        this.visibleAccount = visibleAccount;
    }

    public boolean isVisibleAccount() {
        if(securityContext.isAuthenticated()){
            if(user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_SFR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_JET) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_PETRO) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_EMP) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)){
                visibleAccount = true;
                //log.info(accessDC.getDisplayRecord() + this.getClass() + " Account tab Visible " );
            }
        }
        return visibleAccount;
    }

    public void setVisibleAccountSummary(boolean visibleAccountSummary) {
        this.visibleAccountSummary = visibleAccountSummary;
    }

    public boolean isVisibleAccountSummary() {
        if(securityContext.isAuthenticated()){
            if(user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_SFR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_JET) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_PETRO) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_EMP) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)){
                visibleAccountSummary = true;
               // log.info(accessDC.getDisplayRecord() + this.getClass() + " Account Summary link Visible " );
            }
        }
        return visibleAccountSummary;
    }

    public void setVisibleVehicles(boolean visibleVehicles) {
        this.visibleVehicles = visibleVehicles;
    }

    public boolean isVisibleVehicles() {
        if(securityContext.isAuthenticated()){
            if(lang != null && !lang.equalsIgnoreCase("SE")){
                if(user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) ||
                   user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN)||
                   user.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR)||
                   user.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)){
                    visibleVehicles = true;
                    //log.info(accessDC.getDisplayRecord() + this.getClass() + " Vehicle link Visible " );
                }
            }
        }
        return visibleVehicles;
    }

    public void setVisibleDrivers(boolean visibleDrivers) {
        this.visibleDrivers = visibleDrivers;
    }

    public boolean isVisibleDrivers() {
        if(securityContext.isAuthenticated()){
            if(lang != null && !lang.equalsIgnoreCase("SE")){
                if(user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) ||
                   user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN)||
                   user.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR)||
                   user.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)){
                    visibleDrivers = true;
                    //log.info(accessDC.getDisplayRecord() + this.getClass() + " Driver link Visible " );
                }
            }
        }
        return visibleDrivers;
    }

    public void setVisibleTransactions(boolean visibleTransactions) {
        this.visibleTransactions = visibleTransactions;
    }

    public boolean isVisibleTransactions() {
        if(securityContext.isAuthenticated()){
            if(user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_SFR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_JET) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_PETRO) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_EMP) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)){
                   visibleTransactions = true;
                  // log.info(accessDC.getDisplayRecord() + this.getClass() + " Transaction Tab Visible " );
               }

        }
        return visibleTransactions;
    }

    public void setVisibleTransactionOverview(boolean visibleTransactionOverview) {
        this.visibleTransactionOverview = visibleTransactionOverview;
    }

    public boolean isVisibleTransactionOverview() {
        if(securityContext.isAuthenticated()){
            if(user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_SFR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_JET) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_PETRO) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_EMP) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)){
                   visibleTransactionOverview = true;
                  // log.info(accessDC.getDisplayRecord() + this.getClass() + " Transaction Overview link Visible " );
               }
        }
        return visibleTransactionOverview;
    }

    public void setVisibleInvoiceOverview(boolean visibleInvoiceOverview) {
        this.visibleInvoiceOverview = visibleInvoiceOverview;
    }

    public boolean isVisibleInvoiceOverview() {
        if(securityContext.isAuthenticated()){
            if(user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_SFR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_JET) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_PETRO) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)){
                   visibleInvoiceOverview = true;
                  // log.info(accessDC.getDisplayRecord() + this.getClass() + " Invoice Overview link Visible " );
               }
        }
        return visibleInvoiceOverview;
    }

    public void setVisibleCardServices(boolean visibleCardServices) {
        this.visibleCardServices = visibleCardServices;
    }

    public boolean isVisibleCardServices() {
        if(securityContext.isAuthenticated()){
            if(user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_SFR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_JET) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_PETRO) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_EMP) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)){
                visibleCardServices = true;
                //log.info(accessDC.getDisplayRecord() + this.getClass() + " Card Service tab Visible " );
            }
        }
        return visibleCardServices;
    }

    public void setVisibleViewCards(boolean visibleViewCards) {
        this.visibleViewCards = visibleViewCards;
    }

    public boolean isVisibleViewCards() {
        if(securityContext.isAuthenticated()){
            if(user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_SFR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_JET) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_PETRO) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_EMP) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)){
                visibleViewCards = true;
               // log.info(accessDC.getDisplayRecord() + this.getClass() + " View Cards link Visible " );
            }
        }
        return visibleViewCards;
    }

    public void setVisiblePricing(boolean visiblePricing) {
        this.visiblePricing = visiblePricing;
    }

    public boolean isVisiblePricing() {
        if(securityContext.isAuthenticated()){
            if(session.getAttribute("ListPriceVisible") != null){
                visiblePricing = (Boolean.parseBoolean(session.getAttribute("ListPriceVisible").toString()));
                return visiblePricing;
            }
            else{
                if(user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN) ||
                   user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR)){
                       Set<String> cardTypeSet = new HashSet<String>();
                       List<String> customerTypeList=new ArrayList<String>();
                       if(session.getAttribute("cardTypeList")!=null){
                           cardTypeSet = (Set<String>)session.getAttribute("cardTypeList");
                       }

                       List<String> cardTypeListTemp = new ArrayList<String>(cardTypeSet);
                       String cardTypeList= cardTypeListTemp.toString().substring(1, cardTypeListTemp.toString().length()-1).replace("", "");

                       DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                       DCIteratorBinding iter1;
                       if (bindings != null) {
                           iter1 = bindings.findIteratorBinding("PrtCustomerCardMapRVO1_1Iterator");
                       }
                       else{
                           log.severe(accessDC.getDisplayRecord() + this.getClass() + "bindings is null for customer card map in menu visible helper");
                           iter1 = null;
                       }

                       if (iter1 != null) {
                           ViewObject prtCustomerCardMapVO = iter1.getViewObject();
                           if(cardTypeList != null){
                                prtCustomerCardMapVO.setNamedWhereClauseParam("cardType", cardTypeList);
                            }
                            prtCustomerCardMapVO.executeQuery();

                            if (prtCustomerCardMapVO.getEstimatedRowCount() != 0) {
                                while (prtCustomerCardMapVO.hasNext()) {
                                    PrtCustomerCardMapRVO1RowImpl currRow = (PrtCustomerCardMapRVO1RowImpl)prtCustomerCardMapVO.next();
                                    if (currRow != null) {
                                        customerTypeList.add(currRow.getCustomerType());
                                        log.info(accessDC.getDisplayRecord() + this.getClass() + " card type list for user is " + cardTypeList);

                                        log.info(accessDC.getDisplayRecord() + this.getClass() + " customer type list for user is " + customerTypeList);

                                        if(customerTypeList.toString().trim().contains("B2B TRUCK")){
                                            visiblePricing = true;
                                            log.info(accessDC.getDisplayRecord() + this.getClass() + " Pricing tab Visible " );

                                        }
                                    }
                                }
                            }
                            session.setAttribute("ListPriceVisible", visiblePricing);
                            //log.info(accessDC.getDisplayRecord() + this.getClass() + " pricing tab visibility set into session " + session.getAttribute("ListPriceVisible"));

                       }
                   }
            }
        }
        return visiblePricing;
    }

    public void setVisibleListPrice(boolean visibleListPrice) {
        this.visibleListPrice = visibleListPrice;
    }

    public boolean isVisibleListPrice() {
        if(securityContext.isAuthenticated()){
            if(visiblePricing){
                visibleListPrice = true;
            }
        }
        return visibleListPrice;
    }

    public void setVisibleSetup(boolean visibleSetup) {
        this.visibleSetup = visibleSetup;
    }

    public boolean isVisibleSetup() {
        if(securityContext.isAuthenticated()){
            if(user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_SFR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_JET) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_PETRO) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_EMP) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) ||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR)||
               user.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)){
                visibleSetup = true;
                //log.info(accessDC.getDisplayRecord() + this.getClass() + " Setup Tab Visible " );
            }
        }
        return visibleSetup;
    }

    public void setVisibleChangePassword(boolean visibleChangePassword) {
        this.visibleChangePassword = visibleChangePassword;
    }

    public boolean isVisibleChangePassword() {
        if(securityContext.isAuthenticated()){
            visibleChangePassword = true;
        }
        return visibleChangePassword;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}

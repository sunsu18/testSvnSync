package com.sfr.engage.util;

import com.sfr.engage.model.resources.EngageResourceBundle;
 /* TODO : ASHTHA - 02, May, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC
 *  2. Override toString() method
 *  3. Remove if not needed
 */
public class MenuVisibleHelper {
    
    String menuHome = "Home";
    String menuAccount = "Account";
    String menuAccountSummary = "Account Summary";
    String menuVehicles = "Vehicles";
    String menuDrivers = "Drivers";
    String menuMessages = "Messages";
    String menuTransactions = "Transactions";
    String menuTransactionOverview = "Transaction Overview";
    String menuInvoiceOverview = "Invoice Overview";
    String menuAROverview = "AR Overview";
    String menuCardServices = "Card Services";
    String menuViewCards = "View Cards";
    String menuNewCard = "New Cards";
    String menuProfiles = "Profiles";
    String menuPricing = "Pricing";
    String menuAgreements = "Agreements";
    String menuListPrice = "List Prices";
    String menuSetup = "Setup";
    String menuWebProfiles = "Web Profiles";
    String menuReportSetup = "Report Setup";
    String menuAlerts = "Alerts";
    String menuManageUsers = "Manage Users";
    String menuWebAssist = "Web Assit";
    String menuSupport = "Support";
    String menuChangeAccount = "Change Account";
    String menuContact = "Contact";
    String menuFAQ = "FAQ";
    String menuSelectAccount = "Select Account";
    String menuChangePassword = "Change Password";
    String menuCustomerView = "Customer View";
    EngageResourceBundle sfr = new EngageResourceBundle();
    
    public MenuVisibleHelper() {
        super();
        sfr = new EngageResourceBundle();
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
        if (sfr.containsKey("VIEW_CARD"))
            menuViewCards = (String)sfr.getObject("VIEW_CARD");
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
        if (sfr.containsKey("LIST_PRICE"))
            menuListPrice = (String)sfr.getObject("LIST_PRICE");
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
        if (sfr.containsKey("FAQ"))
            menuFAQ = (String)sfr.getObject("FAQ");
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
        if (sfr.containsKey("CHANGE_PASSWORD"))
            menuChangePassword = (String)sfr.getObject("CHANGE_PASSWORD");
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
}

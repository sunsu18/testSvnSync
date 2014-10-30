package com.sfr.engage.driverinfotaskflow;


import com.sfr.core.bean.User;
import com.sfr.engage.core.Account;
import com.sfr.engage.core.DriverInfo;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.model.queries.uvo.PrtDriverInformationVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtTruckInformationVORowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.ADFUtils;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.util.ResetUtils;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;


public class DriverInfoBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    private List<Account> myAccount;
    private boolean searchResultsShow = false;
    private Map<String, String> driverMap = new HashMap<String, String>();
    private ResourceBundle resourceBundle;
    private String driverN;
    private String accountsList;
    private List<SelectItem> linkedAccountList;
    private String addAccountIdVal = null;
    private String editAccountIdVal = null;
    private List<String> linkedAccountLOVValues;
    private String displayAccountNumber;
    private List<Account> myAccountDriver;
    private HttpSession session;
    private List<PartnerInfo> partnerInfoList;
    private List<SelectItem> cardNumberList;
    private List<SelectItem> editCardNumberList;
    private String addAccountIdDisplayValue = null;
    private String addCardIdDisplayValue = null;
    private String editAccountIdDisplayValue = null;
    private String editCardIdDisplayValue = null;
    private String countryParam;
    private List<String> linkedCardValues;
    private String cardId = null;
    private String warningMsg = null;
    private boolean showErrorMsgFlag = false;
    private boolean showErrorMsgEditFlag = false;
    private List<String> validateAccountCard;
    private String previousCardId = null;
    private String linkedPartnerLOVValues = null;
    private List<SelectItem> linkedPartnerList = null;
    private List<SelectItem> linkedAddAccountList;
    private List<SelectItem> linkedEditAccountList;
    private String addPartnerNumberDisplayValue;
    private String editPartnerNumberDisplayValue;
    private Map<String, String> cardNumberMap = new HashMap<String, String>();
    private String addPartnerIdVal = null;
    private String editPartnerIdVal = null;
    public static final ADFLogger LOGGER = AccessDataControl.getSFRLogger();
    private AccessDataControl accessDC = new AccessDataControl();
    private User user = null;
    private Boolean isEditVisible;
    private Boolean isEditDisable;
    private static final String WHERE_CLAUSE_LITERAL = "ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo";
    private static final String PRTDRIVERINFORMATIONVO2ITERATOR_LITRERAL = "PrtDriverInformationVO2Iterator";
    private static final String NO_DELETE_DRIVER_LITRERAL = "NO_DELETE_DRIVER";
    private static final String CHECKBOXPRIMARYKEY_LITRERAL = "checkBoxPrimaryKey";
    private static final String DRIVER_NAME_LITRERAL = "driverName";
    private static final String AFMANDATORYFIELDLITERAL = "af_mandatoryfield";
    private static final String AFNONMANDATORYFIELDLITERAL = "af_nonmandatoryfield";
    /**
     * @return bindings Object
     */
    public Bindings getBindings() {
        if (bindings == null) {
            bindings = new Bindings();
        }
        return bindings;
    }

    public DriverInfoBean() {
        super();
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Constructor of Driver Info");
        resourceBundle = new EngageResourceBundle();
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        countryParam = null;
        linkedCardValues = new ArrayList<String>();
        linkedPartnerLOVValues = null;
        linkedPartnerList = new ArrayList<SelectItem>();
        linkedAccountList = new ArrayList<SelectItem>();
        linkedAccountLOVValues = new ArrayList<String>();

        if (user == null) {
            user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
        }

        isEditVisible = true;
        isEditDisable = false;

        if (user.getRoleList().get(0).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR) && user.getRoleList().get(0).getIdString().get(0).contains("CG")) {

            isEditVisible = false;
            isEditDisable = true;
        }

        resetValues();

        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting from Constructor of Driver Info");
    }


    private void resetValues(){
       
        linkedCardValues = new ArrayList<String>();
        linkedPartnerLOVValues = null;
        linkedPartnerList = new ArrayList<SelectItem>();
        linkedAccountList = new ArrayList<SelectItem>();
        linkedAccountLOVValues = new ArrayList<String>();
       
       
        if (session.getAttribute("Partner_Object_List") != null) {
            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
        }
       
        if (partnerInfoList != null && partnerInfoList.size() > 0) {
            for (int pa = 0; pa < partnerInfoList.size(); pa++) {
                countryParam = partnerInfoList.get(0).getCountry().toString();
                SelectItem selectItemPartner = new SelectItem();
                if (partnerInfoList.get(pa).getPartnerName() != null && partnerInfoList.get(pa).getPartnerValue() != null) {
                    selectItemPartner.setLabel(partnerInfoList.get(pa).getPartnerName().toString());
                    selectItemPartner.setValue(partnerInfoList.get(pa).getPartnerValue().toString());
                    linkedPartnerList.add(selectItemPartner);
                }
                if (partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() > 0) {
                    for (int ac = 0; ac < partnerInfoList.get(pa).getAccountList().size(); ac++) {
                        if (partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup() != null &&
                            partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size() > 0) {
                            for (int cg = 0; cg < partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size(); cg++) {
                                if (partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard() != null &&
                                    partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size() > 0) {
                                    for (int cc = 0; cc < partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size(); cc++) {
                                        if (partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID() != null &&
                                            partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID() !=
                                            null) {
                                            linkedCardValues.add(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                            cardNumberMap.put(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString(),
                                                              partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (partnerInfoList.size() == 1 && partnerInfoList.get(0).getPartnerValue() != null) {
                linkedPartnerLOVValues = partnerInfoList.get(0).getPartnerValue().toString().trim();
                for (int pa = 0; pa < partnerInfoList.size(); pa++) {
                    if (partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() > 0) {
                        for (int ac = 0; ac < partnerInfoList.get(pa).getAccountList().size(); ac++) {
                            if (partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber() != null) {
                                SelectItem selectItemAccount = new SelectItem();
                                selectItemAccount.setLabel(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString());
                                selectItemAccount.setValue(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString());
                                linkedAccountList.add(selectItemAccount);
                                linkedAccountLOVValues.add(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString());
                            }
                        }
                    }
                }
            }
        }
    }

    public List<SelectItem> getLinkedAccountList() {
        return linkedAccountList;
    }

    /**
     * @param myAccount
     */
    public void setMyAccount(List<Account> myAccount) {
        this.myAccount = myAccount;
    }

    /**
     * @return
     */
    public List<Account> getMyAccount() {
        return myAccount;
    }

    /**
     * This method performs search functionality in VehicleInfo Page.
     *
     */
    public String searchAction() {
        searchResults(true);
        return null;
    }


    /**
     * This method is reusable for different scenario's in DriverInfo Page to show searchResults.
     */
    public String searchResults(boolean value) {
        try {
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Search method of Driver Info");
            if (value) {
                if (getBindings().getLinkedPartner().getValue() != null) {
                    displayErrorComponent(getBindings().getLinkedPartner(), false);
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside Search method of Driver Info for boolean true");
                    if (getBindings().getLinkedAccount().getValue() != null) {
                        displayErrorComponent(getBindings().getLinkedAccount(), false);
                        searchResultsExecution();
                    } else {
                        if (resourceBundle.containsKey("DRIVER_LINKED_ACCOUNT")) {
                            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("DRIVER_LINKED_ACCOUNT"), "");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            displayErrorComponent(getBindings().getLinkedAccount(), true);
                            return null;
                        }
                    }
                } else {
                    if (resourceBundle.containsKey("LINKED_PARTNER")) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("LINKED_PARTNER"), "");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        displayErrorComponent(getBindings().getLinkedPartner(), true);
                        return null;
                    }
                }
            } else {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside Search method of Driver Info for boolean fasle Add/Edit");
                if (getBindings().getLinkedPartner().getValue() == null && getBindings().getLinkedAccount().getValue() == null && addAccountIdVal != null &&
                    addPartnerIdVal != null) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                                " Inside Search method of Driver Info for Account and Partner values not selected(null)");
                    displayErrorComponent(getBindings().getLinkedPartner(), true);
                    if (linkedAccountLOVValues == null) {
                        linkedAccountLOVValues = new ArrayList<String>();
                        linkedAccountList = new ArrayList<SelectItem>();
                        linkedPartnerLOVValues = null;
                    }
                    if (linkedAddAccountList.size() > 0) {
                        for (int i = 0; i < linkedAddAccountList.size(); i++) {
                            linkedAccountList.add(linkedAddAccountList.get(i));
                        }
                    }
                    this.linkedPartnerLOVValues = addPartnerIdVal;
                    linkedAccountLOVValues.add(addAccountIdVal);
                } else {
                    if (getBindings().getLinkedPartner().getValue() != null && getBindings().getLinkedAccount().getValue() != null) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                                    " Inside Search method of Driver Info for Account and Partner values not null");
                        displayErrorComponent(getBindings().getLinkedPartner(), false);
                        String searchValues = getBindings().getLinkedAccount().getValue().toString().trim();
                        String[] search = stringSplitter(searchValues);
                        if (addAccountIdVal != null && addPartnerIdVal != null) {
                            if (addPartnerIdVal.equals(getBindings().getLinkedPartner().getValue().toString())) {
                                int count = 0;
                                for (int i = 0; i < search.length; i++) {
                                    if ((addAccountIdVal.equalsIgnoreCase(search[i].trim()))) {
                                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                                                    " Inside Search method of Driver Info for Add Account check.");
                                        count = 1;
                                    }
                                }
                                if (count == 0) {
                                    linkedAccountLOVValues.add(addAccountIdVal);
                                }
                            } else {
                                linkedAccountLOVValues = new ArrayList<String>();
                                linkedAccountList = new ArrayList<SelectItem>();
                                linkedPartnerLOVValues = null;

                                if (linkedAddAccountList.size() > 0) {
                                    for (int i = 0; i < linkedAddAccountList.size(); i++) {
                                        linkedAccountList.add(linkedAddAccountList.get(i));
                                    }
                                }
                                linkedAccountLOVValues.add(addAccountIdVal);
                                this.linkedPartnerLOVValues = addPartnerIdVal;
                            }
                        }

                        if (editAccountIdVal != null && editPartnerIdVal != null) {
                            if (editPartnerIdVal.equals(getBindings().getLinkedPartner().getValue().toString())) {
                                int count = 0;
                                for (int i = 0; i < search.length; i++) {
                                    if ((editAccountIdVal.equalsIgnoreCase(search[i].trim()))) {
                                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                                                    " Inside Search method of Driver Info for Edit Account check.");
                                        count = 1;
                                    }
                                }
                                if (count == 0) {
                                    linkedAccountLOVValues.add(editAccountIdVal);
                                }
                            } else {
                                linkedAccountLOVValues = new ArrayList<String>();
                                linkedAccountList = new ArrayList<SelectItem>();
                                linkedPartnerLOVValues = null;

                                if (linkedEditAccountList.size() > 0) {
                                    for (int i = 0; i < linkedEditAccountList.size(); i++) {
                                        linkedAccountList.add(linkedEditAccountList.get(i));
                                    }
                                }
                                linkedAccountLOVValues.add(editAccountIdVal);
                                this.linkedPartnerLOVValues = editPartnerIdVal;
                            }
                        }
                    }
                }
                LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting search method of Driver Info");
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLinkedAccount());
                addAccountIdVal = null;
                editAccountIdVal = null;
                searchResultsExecution();
            }
        } catch (JboException ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }

    public String[] stringSplitter(String passedVal) {

        return passedVal.substring(1, passedVal.length() - 1).split(",");
    }

    public String searchResultsExecution() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside searchResultsExecution method of Driver Info");
        int count = 0;
        String[] values;
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Select account number:::::::: " + getBindings().getLinkedAccount().getValue());
        String selectedValues = getBindings().getLinkedAccount().getValue().toString().trim();
        String passingValues = selectedValues.substring(1, selectedValues.length() - 1);
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Passing account number:::::::: " + passingValues);
        if (passingValues.contains(",")) {
            values = passingValues.split(",");
            count = values.length;

        } else {
            count = 1;
            values = new String[1];
            values[0] = passingValues;
        }
        myAccount = new ArrayList<Account>();
        for (int i = 0; i < count; i++) {
            Account acc = new Account();
            acc.setAccountNumber(values[i]);
            List<DriverInfo> myDriverList = new ArrayList<DriverInfo>();
            ViewObject vo = ADFUtils.getViewObject("PrtDriverInformationVO1Iterator");
            vo.setNamedWhereClauseParam(Constants.COUNTRY_CD_LITERAL, countryParam);
            vo.setWhereClause("trim(ACCOUNT_NUMBER) =: accountId AND trim(DRIVER_NAME) LIKE '%'||:driverName||'%'");

            vo.defineNamedWhereClauseParam(Constants.ACCOUNT_ID_LITERAL, values[i].trim(), null);
            if (getBindings().getDriverName().getValue() != null && getBindings().getDriverName().getValue().toString().length() > 0) {
                vo.defineNamedWhereClauseParam(DRIVER_NAME_LITRERAL, getBindings().getDriverName().getValue().toString().trim(), null);
            } else {
                vo.defineNamedWhereClauseParam(DRIVER_NAME_LITRERAL, null, null);
            }
            vo.executeQuery();
            if (vo.getEstimatedRowCount() != 0) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Row count after query execution:::::::: " + vo.getEstimatedRowCount());
                for (int j = 0; j < vo.getEstimatedRowCount(); j++) {
                    while (vo.hasNext()) {
                        PrtDriverInformationVORowImpl currRow = (PrtDriverInformationVORowImpl)vo.next();
                        if (currRow != null) {
                            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Current row card number:::::::: " + currRow.getCardNumber());
                            if (currRow.getCardNumber() == null || linkedCardValues.contains(currRow.getCardNumber().toString())) {
                                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Is it coming inside to create driver details details List");
                                DriverInfo driver = new DriverInfo();
                                if (currRow.getPrtDriverInformationPk() != null) {
                                    driver.setPrtDriverInformationPK(currRow.getPrtDriverInformationPk().toString());
                                }
                                driver.setAccountId(currRow.getAccountNumber());
                                driver.setCardNumber(currRow.getCardNumber());
                                driver.setEmbossCardNumber(cardNumberMap.get(currRow.getCardNumber()));
                                driver.setDriverName(currRow.getDriverName());
                                driver.setDriverNumber(currRow.getDriverNumber());
                                driver.setNationality(currRow.getNationality());
                                if (currRow.getMobileNumber() != null) {
                                    driver.setMobileNumber(Integer.parseInt(currRow.getMobileNumber().toString()));
                                }
                                driver.setRemarks(currRow.getRemarks());
                                driver.setPassportNumber(currRow.getPassportNumber());
                                if (currRow.getPassportExpiry() != null) {
                                    driver.setPassportExpiry(currRow.getPassportExpiry().getValue());
                                }
                                driver.setLicenseNumber(currRow.getLicenseNumber());
                                if (currRow.getLicenseExpiry() != null) {
                                    driver.setLicenseExpiry(currRow.getLicenseExpiry().getValue());
                                }
                                if (currRow.getEmployStart() != null) {
                                    driver.setEmployStart(currRow.getEmployStart().getValue());
                                }
                                if (currRow.getEmployEnd() != null) {
                                    driver.setEmployEnd(currRow.getEmployEnd().getValue());
                                }
                                driver.setCountryCd(currRow.getRemarks());
                                myDriverList.add(driver);
                            }
                        }
                    }
                }
            }
            if ("trim(ACCOUNT_ID) =: accountId AND trim(DRIVER_NAME) LIKE '%'||:driverName||'%'".equalsIgnoreCase(vo.getWhereClause())) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Inside driver search  dynamic where clause remove");
                vo.removeNamedWhereClauseParam(Constants.ACCOUNT_ID_LITERAL);
                vo.removeNamedWhereClauseParam(DRIVER_NAME_LITRERAL);
                vo.setWhereClause("");
                vo.executeQuery();
            }
            acc.setDriverInfoList(myDriverList);
            myAccount.add(acc);
        }
        myAccountDriver = new ArrayList<Account>();
        if (myAccount.size() > 0) {
            if (getBindings().getDriverName().getValue() != null && getBindings().getDriverName().getValue().toString().length() > 0) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Inside if block of new account list");
                for (int k = 0; k < myAccount.size(); k++) {
                    if (myAccount.get(k).getDriverInfoList().size() > 0) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Inside if block to create new Account list");
                        myAccountDriver.add(myAccount.get(k));
                    }
                }
            } else {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Inside else block of new account list");
                final int fifty = 50;
                if (myAccount.size() > fifty) {
                    for (int m = 0; m < myAccount.size(); m++) {
                        if (myAccount.get(m).getDriverInfoList().size() > 0) {
                            myAccountDriver.add(myAccount.get(m));
                        }
                    }
                } else {
                    for (int m = 0; m < myAccount.size(); m++) {
                        myAccountDriver.add(myAccount.get(m));
                    }
                }
            }
        }

        if (myAccountDriver.size() > 0) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside if block of the show condition of panel");
            searchResultsShow = true;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
        } else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside else block of the show condition of panel");
            searchResultsShow = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());

            if (resourceBundle.containsKey("NO_RECORDS_FOUND_DRIVER")) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("NO_RECORDS_FOUND_DRIVER"), "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }

        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting SearchResultsExecution method");
        return null;

    }

    /**
     * @param searchResultsShow
     */
    public void setSearchResultsShow(boolean searchResultsShow) {
        this.searchResultsShow = searchResultsShow;
    }

    /**
     * @return
     */
    public boolean isSearchResultsShow() {
        return searchResultsShow;
    }


    /**
     * This Method will save new driver information in DB.
     * @return
     */
    public String newDriverSave() {

        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Add new Driver save method");
        User localUser = null;
        String modifiedBy = null;
        localUser = (User)session.getAttribute(Constants.SESSION_USER_INFO);
        if (localUser != null) {
            modifiedBy = localUser.getFirstName().concat(" ").concat(localUser.getLastName());
            if (modifiedBy == null) {
                modifiedBy = localUser.getUserID();
            }
        }

        if (getBindings().getAddPartnerNumberId().getValue() != null && getBindings().getAddAccountId().getValue() != null &&
            getBindings().getAddDriverName().getValue() != null && getBindings().getAddDriverNumber().getValue() != null &&
            getBindings().getAddDriverName().getValue().toString().trim() != null && getBindings().getAddDriverNumber().getValue().toString().trim() != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside Add new driver save method after null check condition");
            ViewObject driverVo = ADFUtils.getViewObject("PrtDriverInformationVO3Iterator");
            driverVo.setNamedWhereClauseParam(Constants.COUNTRY_CD_LITERAL, countryParam);
            driverVo.setWhereClause(WHERE_CLAUSE_LITERAL);
            if (addAccountIdVal == null) {
                addAccountIdVal = getBindings().getAddAccountId().getValue().toString();
            }
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Value of addAccountIdVal:::::::::" + addAccountIdVal);
            driverVo.defineNamedWhereClauseParam(Constants.ACCOUNT_ID_LITERAL, addAccountIdVal, null);
            if (getBindings().getAddCardId().getValue() != null) {
                driverVo.defineNamedWhereClauseParam(Constants.CARD_NO_LITERAL, getBindings().getAddCardId().getValue().toString(), null);
            } else {
                driverVo.defineNamedWhereClauseParam(Constants.CARD_NO_LITERAL, " ", null);
            }
            driverVo.executeQuery();
            if (driverVo.getEstimatedRowCount() > 0) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside driver method to check Existing driver for selected card:::::::::");
                while (driverVo.hasNext()) {
                    PrtDriverInformationVORowImpl currRow = (PrtDriverInformationVORowImpl)driverVo.next();
                    if (currRow != null && resourceBundle.containsKey(Constants.DRIVER_CARD_EXIST_LITERAL)) {

                        warningMsg = resourceBundle.getObject(Constants.DRIVER_CARD_EXIST_LITERAL).toString().concat(" ").concat(currRow.getDriverName());
                        showErrorMsgFlag = true;
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                        return null;

                    }
                }
            }

            ViewObject truckVo = ADFUtils.getViewObject("PrtTruckInformationVO3Iterator");
            truckVo.setNamedWhereClauseParam(Constants.COUNTRY_CD_LITERAL, countryParam);
            truckVo.setWhereClause(WHERE_CLAUSE_LITERAL);
            truckVo.defineNamedWhereClauseParam(Constants.ACCOUNT_ID_LITERAL, addAccountIdVal, null);
            if (getBindings().getAddCardId().getValue() != null) {
                truckVo.defineNamedWhereClauseParam(Constants.CARD_NO_LITERAL, getBindings().getAddCardId().getValue().toString(), null);
            } else {
                truckVo.defineNamedWhereClauseParam(Constants.CARD_NO_LITERAL, "", null);
            }
            truckVo.executeQuery();
            if (truckVo.getEstimatedRowCount() > 0) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside vehicle method to check Existing vehicle for selected card:::::::::");
                while (truckVo.hasNext()) {
                    PrtTruckInformationVORowImpl currRow = (PrtTruckInformationVORowImpl)truckVo.next();
                    if (currRow != null && resourceBundle.containsKey(Constants.TRUCK_CARD_EXIST_LITERAL)) {

                        warningMsg = resourceBundle.getObject(Constants.TRUCK_CARD_EXIST_LITERAL).toString().concat(" ").concat(currRow.getVehicleNumber());
                        showErrorMsgFlag = true;
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                        return null;

                    }
                }
            }


            getBindings().getNewDriver().hide();
            BindingContainer localBinding = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding driverInfoItr = (DCIteratorBinding)localBinding.get(PRTDRIVERINFORMATIONVO2ITERATOR_LITRERAL);
            Row driverInfoRow = driverInfoItr.getCurrentRow();
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Before new driver save current row is not null+++++++++++");
            if (driverInfoRow != null) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside new driver save current row is not null:::::::::");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Value of add account id:::::::::" +
                            getBindings().getAddAccountId().getValue().toString());
                driverInfoRow.setAttribute("AccountNumber", getBindings().getAddAccountId().getValue().toString());
                if (getBindings().getAddCardId().getValue() != null) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Value of add card id:::::::::" +
                                getBindings().getAddCardId().getValue().toString());
                    driverInfoRow.setAttribute(Constants.CARDNUMBER_LITERAL, getBindings().getAddCardId().getValue().toString());
                } else {
                    driverInfoRow.setAttribute(Constants.CARDNUMBER_LITERAL, "");
                }
                if (getBindings().getAddReferenceNumber().getValue() != null) {
                    String refNumber = getBindings().getAddReferenceNumber().getValue().toString().trim();
                    String formattedRefNum = ("0000000000" + refNumber).substring(refNumber.length());
                    driverInfoRow.setAttribute("ReferenceNumber", formattedRefNum);
                }
                driverInfoRow.setAttribute("CountryCode", countryParam);
                driverInfoRow.setAttribute("DriverNumber", getBindings().getAddDriverNumber().getValue().toString().trim());
                driverInfoRow.setAttribute("DriverName", getBindings().getAddDriverName().getValue().toString().trim());
                OperationBinding newDriverOpn = localBinding.getOperationBinding("Commit");
                newDriverOpn.execute();
                if (newDriverOpn.getErrors().isEmpty()) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "NO failure in commit operation of driver add:::::::::");
                }
            }
        } else {

            if (resourceBundle.containsKey(Constants.ENGAGE_SELECT_TRANSACTION_MANDATORY_LITERAL)) {
                warningMsg = resourceBundle.getObject(Constants.ENGAGE_SELECT_TRANSACTION_MANDATORY_LITERAL).toString();
                showErrorMsgFlag = true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                return null;
            }
        }
        searchResults(false);
        if (resourceBundle.containsKey("DRIVER_ADD")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("DRIVER_ADD"), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting new driver save method:::::::::");

        return null;
    }

    private Comparator<SelectItem> comparator = new Comparator<SelectItem>() {
        @Override
        public int compare(SelectItem s1, SelectItem s2) {
            return s1.getLabel().compareTo(s2.getLabel());
        }
    };

    /**
     * @return
     */
    public String newDriverCancel() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside new driver cancel method :::::::::");
        ResetUtils.reset(getBindings().getNewDriver());
        getBindings().getNewDriver().hide();
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting new driver cancel method:::::::::");
        return null;
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

    /**
     * @return
     */
    public String newDriverAddAction() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside new driver add action method :::::::::");
        BindingContainer localBinding = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding createOpn = localBinding.getOperationBinding("CreateInsert");
        createOpn.execute();
        showErrorMsgFlag = false;
        linkedAddAccountList = new ArrayList<SelectItem>();
        addPartnerNumberDisplayValue = null;
        addAccountIdDisplayValue = null;
        addCardIdDisplayValue = null;
        cardNumberList = new ArrayList<SelectItem>();
        if (getBindings().getLinkedPartner().getValue() != null) {
            displayErrorComponent(getBindings().getLinkedPartner(), false);
            if (getBindings().getLinkedAccount().getValue() != null && linkedAccountLOVValues.size() > 0) {
                displayErrorComponent(getBindings().getLinkedAccount(), false);
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                            "Inside if block new driver add for partner and selected account values :::::::::");
                if (linkedAccountLOVValues.size() == 1) {
                    this.addAccountIdDisplayValue = populateStringValues(getBindings().getLinkedAccount().getValue().toString());
                }
                this.addPartnerNumberDisplayValue = getBindings().getLinkedPartner().getValue().toString();
                this.addCardIdDisplayValue = null;
                populateAccountNumber(getBindings().getLinkedPartner().getValue().toString(), Constants.ADD_LITERAL);
                populateCardNumberList(populateStringValues(getBindings().getLinkedAccount().getValue().toString()), "newDriverAdd",
                                       getBindings().getLinkedPartner().getValue().toString());
            } else {
                displayErrorComponent(getBindings().getLinkedAccount(), true);
            }
        } else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                        "Inside else block new driver add for no partner and selected account values:::::::::");
            displayErrorComponent(getBindings().getLinkedPartner(), true);
            this.addPartnerNumberDisplayValue = null;
            this.addAccountIdDisplayValue = null;
            this.addCardIdDisplayValue = null;
        }

        if (getBindings().getDriverName().getValue() != null) {
            getBindings().getAddDriverName().setSubmittedValue(null);
            getBindings().getAddDriverName().setSubmittedValue(getBindings().getDriverName().getValue().toString());
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting new driver add action method :::::::::");
        getBindings().getNewDriver().show(new RichPopup.PopupHints());

        return null;
    }

    /**
     * This Method will save edited driver information in DB.
     * @return
     */
    public String editDriverSave() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside new driver edit save method :::::::::");
        User localUser = null;
        String modifiedBy = null;
        localUser = (User)session.getAttribute(Constants.SESSION_USER_INFO);
        if (localUser != null) {
            modifiedBy = localUser.getFirstName().concat(" ").concat(localUser.getLastName());
            if (modifiedBy == null) {
                modifiedBy = localUser.getUserID();
            }
        }
        if (getBindings().getEditPartnerNumberId().getValue() != null && getBindings().getEditAccountId().getValue() != null &&
            getBindings().getEditDriverName().getValue() != null && getBindings().getEditDriverNumber().getValue() != null &&
            getBindings().getEditDriverName().getValue().toString().trim() != null &&
            getBindings().getEditDriverNumber().getValue().toString().trim() != null) {

            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Inside new driver edit save method after null check :::::::::");

            if (previousCardId != null && getBindings().getEditCardId().getValue() != null) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                            "Inside of edit driver save method to check for existing driver and previous card not null :::::::::");
                if (!previousCardId.equals(getBindings().getEditCardId().getValue().toString())) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                "Previous card not equals to selected edit card, dont allow edit, value of previous card:::::::::" + previousCardId);
                    ViewObject driverVo = ADFUtils.getViewObject("PrtDriverInformationVO3Iterator");
                    driverVo.setNamedWhereClauseParam(Constants.COUNTRY_CD_LITERAL, countryParam);
                    driverVo.setWhereClause(WHERE_CLAUSE_LITERAL);
                    driverVo.defineNamedWhereClauseParam(Constants.ACCOUNT_ID_LITERAL, editAccountIdVal, null);
                    if (getBindings().getEditCardId().getValue() != null) {
                        driverVo.defineNamedWhereClauseParam(Constants.CARD_NO_LITERAL, getBindings().getEditCardId().getValue().toString(), null);
                    } else {
                        driverVo.defineNamedWhereClauseParam(Constants.CARD_NO_LITERAL, "", null);
                    }
                    driverVo.executeQuery();
                    if (driverVo.getEstimatedRowCount() > 0) {
                        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Checking for existing driver in edit vehicle save method:::::::::");
                        while (driverVo.hasNext()) {
                            PrtDriverInformationVORowImpl currRow = (PrtDriverInformationVORowImpl)driverVo.next();
                            if (currRow != null && resourceBundle.containsKey(Constants.DRIVER_CARD_EXIST_LITERAL)) {

                                warningMsg =
                                        resourceBundle.getObject(Constants.DRIVER_CARD_EXIST_LITERAL).toString().concat(" ").concat(currRow.getDriverName());
                                showErrorMsgEditFlag = true;
                                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
                                return null;


                            }
                        }
                    }
                }
            } else {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                            "Inside of edit driver save method to check for existing truck and previous card is null :::::::::");
                ViewObject driverVo = ADFUtils.getViewObject("PrtDriverInformationVO3Iterator");
                driverVo.setNamedWhereClauseParam(Constants.COUNTRY_CD_LITERAL, countryParam);
                driverVo.setWhereClause(WHERE_CLAUSE_LITERAL);
                driverVo.defineNamedWhereClauseParam(Constants.ACCOUNT_ID_LITERAL, editAccountIdVal, null);
                if (getBindings().getEditCardId().getValue() != null) {
                    driverVo.defineNamedWhereClauseParam(Constants.CARD_NO_LITERAL, getBindings().getEditCardId().getValue().toString(), null);
                } else {
                    driverVo.defineNamedWhereClauseParam(Constants.CARD_NO_LITERAL, "", null);
                }
                driverVo.executeQuery();
                if (driverVo.getEstimatedRowCount() > 0) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() +
                                "Checking for existing driver in edit driver save method for previous card null:::::::::");
                    while (driverVo.hasNext()) {
                        PrtDriverInformationVORowImpl currRow = (PrtDriverInformationVORowImpl)driverVo.next();
                        if (currRow != null && resourceBundle.containsKey(Constants.DRIVER_CARD_EXIST_LITERAL)) {

                            warningMsg = resourceBundle.getObject(Constants.DRIVER_CARD_EXIST_LITERAL).toString().concat(" ").concat(currRow.getDriverName());
                            showErrorMsgEditFlag = true;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
                            return null;

                        }
                    }
                }
            }

            ViewObject truckVo = ADFUtils.getViewObject("PrtTruckInformationVO3Iterator");
            truckVo.setNamedWhereClauseParam(Constants.COUNTRY_CD_LITERAL, countryParam);
            truckVo.setWhereClause(WHERE_CLAUSE_LITERAL);
            truckVo.defineNamedWhereClauseParam(Constants.ACCOUNT_ID_LITERAL, editAccountIdVal, null);
            if (getBindings().getEditCardId().getValue() != null) {
                truckVo.defineNamedWhereClauseParam(Constants.CARD_NO_LITERAL, getBindings().getEditCardId().getValue().toString(), null);
            } else {
                truckVo.defineNamedWhereClauseParam(Constants.CARD_NO_LITERAL, "", null);
            }
            truckVo.executeQuery();
            if (truckVo.getEstimatedRowCount() > 0) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Inside of edit driver save method to check for existing vehicle :::::::::");
                while (truckVo.hasNext()) {
                    PrtTruckInformationVORowImpl currRow = (PrtTruckInformationVORowImpl)truckVo.next();
                    if (currRow != null && resourceBundle.containsKey(Constants.TRUCK_CARD_EXIST_LITERAL)) {

                        warningMsg = resourceBundle.getObject(Constants.TRUCK_CARD_EXIST_LITERAL).toString().concat(" ").concat(currRow.getVehicleNumber());
                        showErrorMsgEditFlag = true;
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
                        return null;
                    }

                }
            }

            getBindings().getEditDriver().hide();
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "value of editAccountIdVal:::::::::" + editAccountIdVal);
            BindingContainer localBindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding driverInfoItr = (DCIteratorBinding)localBindings.get(PRTDRIVERINFORMATIONVO2ITERATOR_LITRERAL);
            Row driverInfoRow = driverInfoItr.getCurrentRow();

            if (driverInfoRow != null) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Inside new driver save current row is not null:::::::::");
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Value of edit account number :::::::::" +
                            getBindings().getEditAccountId().getValue());

                driverInfoRow.setAttribute("AccountNumber", getBindings().getEditAccountId().getValue().toString());
                if (getBindings().getEditCardId().getValue() != null) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Value of Edit card id:::::::::" +
                                getBindings().getEditCardId().getValue().toString());
                    driverInfoRow.setAttribute(Constants.CARDNUMBER_LITERAL, getBindings().getEditCardId().getValue().toString());
                } else {
                    driverInfoRow.setAttribute(Constants.CARDNUMBER_LITERAL, "");
                }
                if (getBindings().getEditReferenceNumber().getValue() != null) {
                    String refNumber = getBindings().getEditReferenceNumber().getValue().toString().trim();
                    String formattedRefNum = ("0000000000" + refNumber).substring(refNumber.length());
                    driverInfoRow.setAttribute("ReferenceNumber", formattedRefNum);
                }
                driverInfoRow.setAttribute("CountryCode", countryParam);
                driverInfoRow.setAttribute("DriverNumber", getBindings().getEditDriverNumber().getValue().toString().trim());
                driverInfoRow.setAttribute("DriverName", getBindings().getEditDriverName().getValue().toString().trim());
                OperationBinding newDriverOpn = localBindings.getOperationBinding("Commit");
                newDriverOpn.execute();
                if (newDriverOpn.getErrors().isEmpty()) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " No failure or edit commit operation");
                }
            }
        } else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Throwing error for mandatory check");
            if (resourceBundle.containsKey(Constants.ENGAGE_SELECT_TRANSACTION_MANDATORY_LITERAL)) {
                warningMsg = resourceBundle.getObject(Constants.ENGAGE_SELECT_TRANSACTION_MANDATORY_LITERAL).toString();
                showErrorMsgEditFlag = true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
                return null;
            }
        }

        searchResults(false);
        if (resourceBundle.containsKey("DRIVER_EDIT")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("DRIVER_EDIT"), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting edit driver save method");
        return null;
    }

    /**
     * @return
     */
    public String editDriverCancel() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside edit driver cancel method");
        ResetUtils.reset(getBindings().getEditDriver());
        getBindings().getEditDriver().hide();
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting edit vehicle cancel method");
        return null;
    }

    /**
     *  This Method will execute VO and fetches selected row from DB and perform Edit operation.
     * @return
     */
    public String tableEditAction() {
        try {
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside table edit action method");
            showErrorMsgEditFlag = false;
            editAccountIdDisplayValue = null;
            editCardIdDisplayValue = null;
            editCardNumberList = new ArrayList<SelectItem>();
            linkedEditAccountList = new ArrayList<SelectItem>();
            String primaryKey = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("primarykey");
            String accountNumber = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountnumber");
            cardId = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardid");
            previousCardId = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardid");
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Value of primary key:::::::" + primaryKey);
            if (primaryKey != null && accountNumber != null && getBindings().getLinkedPartner().getValue() != null) {
                editPartnerNumberDisplayValue = getBindings().getLinkedPartner().getValue().toString();
                editAccountIdDisplayValue = accountNumber;
                editAccountIdVal = editAccountIdDisplayValue;
                if (editAccountIdDisplayValue != null) {
                    populateAccountNumber(getBindings().getLinkedPartner().getValue().toString(), Constants.EDIT_LITERAL);
                    populateCardNumberList(editAccountIdDisplayValue, "editButton", getBindings().getLinkedPartner().getValue().toString());
                }

                ViewObject vo = ADFUtils.getViewObject(PRTDRIVERINFORMATIONVO2ITERATOR_LITRERAL);
                vo.setNamedWhereClauseParam(Constants.COUNTRY_CD_LITERAL, countryParam);
                vo.setWhereClause("PRT_DRIVER_INFORMATION_PK =: prtDriverInformationPK");
                vo.defineNamedWhereClauseParam("prtDriverInformationPK", primaryKey, null);
                vo.executeQuery();
                getBindings().getEditDriver().show(new RichPopup.PopupHints());
            }
        } catch (JboException ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, ex.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting table edit action method");
        return null;
    }

    /**
     * This Method will show confirmation popup for Delete operation.
     * @return
     */
    public String driverDeleteAction() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside driver delete action method");
        if (driverMap.size() != 0) {
            ArrayList<String> validateCard = new ArrayList<String>();
            if (partnerInfoList != null && partnerInfoList.size() > 0) {
                for (int pa = 0; pa < partnerInfoList.size(); pa++) {
                    if (partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() > 0) {
                        for (int ac = 0; ac < partnerInfoList.get(pa).getAccountList().size(); ac++) {
                            if (validateAccountCard.contains(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString()) &&
                                !partnerInfoList.get(pa).getAccountList().get(ac).isAccountOverview()) {
                                validateCard.add(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString());
                            }
                        }
                    }
                }
            }
            if (validateCard != null && validateCard.size() > 0) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Inside driver delete action method and list of deleted cards" +
                            validateCard);
                if (resourceBundle.containsKey(NO_DELETE_DRIVER_LITRERAL)) {
                    String cardList = validateCard.toString();
                    String validateCardValues = cardList.substring(1, cardList.length() - 1).replace(" ", "");
                    String driverErrorMsg = resourceBundle.getObject(NO_DELETE_DRIVER_LITRERAL).toString().concat(validateCardValues);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, driverErrorMsg, "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
            }
            getBindings().getDeleteDriver().show(new RichPopup.PopupHints());
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("DRIVER_DELETE_FAILURE_1"), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting driver delete action method");
        return null;
    }

    /**
     * This Method will show confirmation popup for Delete All records operation.
     * @return
     */
    public String deleteAllRecordsAction() {

        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ACCOUNT_NUMER_LITERAL) != null) {
            int validateDeleteAccountCount = 0;
            displayAccountNumber = AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ACCOUNT_NUMER_LITERAL).toString().trim();
            if (partnerInfoList != null && partnerInfoList.size() > 0) {
                for (int pa = 0; pa < partnerInfoList.size(); pa++) {
                    if (partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() > 0) {
                        for (int ac = 0; ac < partnerInfoList.get(pa).getAccountList().size(); ac++) {
                            if (partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString().equals(displayAccountNumber) &&
                                !partnerInfoList.get(pa).getAccountList().get(ac).isAccountOverview()) {
                                validateDeleteAccountCount = 1;
                            }
                        }
                    }
                }
            }

            if (validateDeleteAccountCount > 0 && resourceBundle.containsKey(NO_DELETE_DRIVER_LITRERAL)) {

                String driverErrorMsg = resourceBundle.getObject(NO_DELETE_DRIVER_LITRERAL).toString().concat(displayAccountNumber);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, driverErrorMsg, "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;

            }
            int count = 0;
            if (myAccountDriver.size() > 0) {
                for (int i = 0; i < myAccountDriver.size(); i++) {
                    if (myAccountDriver.get(i).getAccountNumber().trim().equals(displayAccountNumber)) {
                        if (myAccountDriver.get(i).getDriverInfoList().size() > 0) {
                            count = 1;
                        } else {
                            count = 0;
                        }
                    }
                }
            }
            if (count > 0) {
                getBindings().getDeleteAllInfoDriver().show(new RichPopup.PopupHints());
            } else {
                if (resourceBundle.containsKey("NO_RECORDS_FOUND_DELETE_ALL")) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("NO_RECORDS_FOUND_DELETE_ALL"), "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * This Method will delete selected driver rows from DB.
     * @return
     */
    public String deleteDriverSave() {
        try {
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Driver delete save method");
            Iterator iter = driverMap.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String)iter.next();
                String vals = driverMap.get(key);
                ViewObject vo = ADFUtils.getViewObject(PRTDRIVERINFORMATIONVO2ITERATOR_LITRERAL);
                vo.setNamedWhereClauseParam(Constants.COUNTRY_CD_LITERAL, countryParam);
                vo.setWhereClause("PRT_DRIVER_INFORMATION_PK =: prtDriverInformationPK");
                vo.defineNamedWhereClauseParam("prtDriverInformationPK", vals, null);
                vo.executeQuery();
                if (vo.getEstimatedRowCount() != 0) {
                    LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                "Inside Driver delete save method to check row count and then removing row");
                    while (vo.hasNext()) {
                        Row r = vo.next();
                        vo.setCurrentRow(r);
                        vo.removeCurrentRow();
                    }
                }
            }
            BindingContainer localBindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = localBindings.getOperationBinding("Commit");
            operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside Driver delete for successful deletion");
                getBindings().getDeleteDriver().hide();
                driverMap = new HashMap<String, String>();
                searchResults(false);
                if (resourceBundle.containsKey("DRIVER_DELETE_SUCCESS")) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("DRIVER_DELETE_SUCCESS"), "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
            } else {
                if (resourceBundle.containsKey("DRIVER_DELETE_FAILURE")) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("DRIVER_DELETE_FAILURE"), "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
            }
        } catch (JboException ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting Driver delete save method");
        return null;
    }

    /**
     * This method will delete all the driver details for the Selected Account./
     * @return
     */
    public String delteAllForAccount() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside deleteAllForAccount method for account");
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ACCOUNT_NUMER_LITERAL) != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside deleteAllForAccount method for account after null check");
            BindingContainer localBindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = localBindings.getOperationBinding("deleteAllForAccount");
            operationBinding.getParamsMap().put(Constants.ACCOUNT_ID_LITERAL,
                                                AdfFacesContext.getCurrentInstance().getPageFlowScope().get(Constants.ACCOUNT_NUMER_LITERAL).toString().trim());
            operationBinding.getParamsMap().put("type", "driver");
            operationBinding.getParamsMap().put(Constants.COUNTRY_CD_LITERAL, countryParam);
            if (getBindings().getDriverName().getValue() != null && getBindings().getDriverName().getValue().toString().length() > 0) {
                operationBinding.getParamsMap().put("regDriverValue", getBindings().getDriverName().getValue().toString());
            } else {
                operationBinding.getParamsMap().put("regDriverValue", null);
            }
            operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
                getBindings().getDeleteAllInfoDriver().hide();
                searchResults(true);
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting deleteAllForAccount method for account");
        return null;
    }

    /**
     * @return
     */
    public String deleteDriverCancel() {
        getBindings().getDeleteDriver().hide();
        return null;
    }

    /**
     * This method will hide the Delete all account pop up when user click on 'NO'.
     * @return
     */
    public String deleteAllForAccountCancel() {
        getBindings().getDeleteAllInfoDriver().hide();
        return null;
    }

    /**
     * This Method will store selected driver rows in HashMap for Delete operation.
     * @param valueChangeEvent
     */
    public void deleteCheckBoxListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Driver delete check box listener to store primary key in Hash Map");
        if (valueChangeEvent.getNewValue().equals(true)) {
            validateAccountCard = new ArrayList<String>();
            validateAccountCard.add(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNo").toString());
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Value of primary key==>" +
                        AdfFacesContext.getCurrentInstance().getPageFlowScope().get(CHECKBOXPRIMARYKEY_LITRERAL));
            driverMap.put((String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get(CHECKBOXPRIMARYKEY_LITRERAL),
                          (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get(CHECKBOXPRIMARYKEY_LITRERAL));
        } else {
            validateAccountCard = new ArrayList<String>();
            validateAccountCard.remove(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNo").toString());
            if (driverMap.containsKey(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(CHECKBOXPRIMARYKEY_LITRERAL))) {
                driverMap.remove(AdfFacesContext.getCurrentInstance().getPageFlowScope().get(CHECKBOXPRIMARYKEY_LITRERAL));
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting Vehicle delete check box listener to store primary key in Hash Map");
    }

    /**
     * This Method will clears selected Linked Account and Driver Name from DriverInfo Page.
     * @param actionEvent
     */
    public void searchCancel(ActionEvent actionEvent) {
        try {
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Driver Search cancel method");
            
            resetValues();

            searchResultsShow = false;
            LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting Vehicle Search cancel method");
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindings().getLinkedPartner());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindings().getLinkedAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindings().getDriverName());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());

        } catch (JboException ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void addAccountNumberListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside AddAccountNumberValueChangeListener method");
        if (valueChangeEvent.getNewValue() != null && getBindings().getAddPartnerNumberId().getValue() != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside AddAccountNumberValueChangeListener method after null check");
            cardNumberList = new ArrayList<SelectItem>();
            populateCardNumberList(valueChangeEvent.getNewValue().toString(), Constants.ADD_LITERAL,
                                   getBindings().getAddPartnerNumberId().getValue().toString());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddCardId());
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting AddAccountNumberValueChangeListener method");
    }

    public void populateCardNumberList(String accountNo, String type, String partnerNumber) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside populateCardNumberList method");
        if (accountNo != null && (partnerInfoList != null && partnerInfoList.size() > 0)) {

            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside populateCardNumberList method after null check");
            for (int pa = 0; pa < partnerInfoList.size(); pa++) {
                if (partnerInfoList.get(pa).getPartnerValue().equals(partnerNumber) && partnerInfoList.get(pa).getAccountList() != null &&
                    partnerInfoList.get(pa).getAccountList().size() > 0) {
                    for (int ac = 0; ac < partnerInfoList.get(pa).getAccountList().size(); ac++) {
                        if (partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber() != null &&
                            partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().equals(accountNo) &&
                            (partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup() != null &&
                             partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size() > 0)) {

                            for (int cg = 0; cg < partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size(); cg++) {
                                if (partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard() != null &&
                                    partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size() > 0) {
                                    for (int cc = 0; cc < partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size(); cc++) {
                                        if (partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID() != null &&
                                            partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID() !=
                                            null) {
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
                                            selectItem.setValue(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                            if (type.equals(Constants.ADD_LITERAL)) {
                                                addAccountIdVal = accountNo;
                                                cardNumberList.add(selectItem);
                                            } else if (type.equals(Constants.EDIT_LITERAL)) {
                                                editAccountIdVal = accountNo;
                                                editCardNumberList.add(selectItem);
                                            } else if (type.equals("newDriverAdd")) {
                                                cardNumberList.add(selectItem);
                                            } else {
                                                editCardNumberList.add(selectItem);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (type.equals(Constants.ADD_LITERAL) || type.equals("newDriverAdd")) {
                Collections.sort(cardNumberList, comparator);
            } else {
                Collections.sort(editCardNumberList, comparator);
            }

        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting populateCardNumberList method");
    }


    public void setAddAccountIDVal(String addAccountIdVal) {
        this.addAccountIdVal = addAccountIdVal;
    }

    public String getAddAccountIdVal() {
        return addAccountIdVal;
    }

    public void setEditAccountIdVal(String editAccountIdVal) {
        this.editAccountIdVal = editAccountIdVal;
    }

    public String getEditAccountIdVal() {
        return editAccountIdVal;
    }

    public void setLinkedAccountLOVValues(List<String> linkedAccountLOVValues) {
        this.linkedAccountLOVValues = linkedAccountLOVValues;
    }

    public List<String> getLinkedAccountLOVValues() {
        return linkedAccountLOVValues;
    }

    public void editAccountNumberChangeListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside editAccountNumberValueChangeListener method");
        if (valueChangeEvent.getNewValue() != null && getBindings().getEditPartnerNumberId().getValue() != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside editAccountNumberValueChangeListener method after null check");
            editCardNumberList = new ArrayList<SelectItem>();
            cardId = null;
            populateCardNumberList(valueChangeEvent.getNewValue().toString(), Constants.EDIT_LITERAL,
                                   getBindings().getEditPartnerNumberId().getValue().toString());
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "editAccountNumber =" + editAccountIdVal);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditCardId());
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting editAccountNumberValueChangeListener method");
    }

    public void editCardNumberChangeListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside editCardNumberChangeListener method");
        if (valueChangeEvent.getNewValue() != null) {
            cardId = valueChangeEvent.getNewValue().toString();
        } else {
            this.cardId = null;
            this.cardId = "";
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting editCardNumberChangeListener method");
        this.showErrorMsgEditFlag = false;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditCardId());
    }

    public void addCardNumberChangeListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside addCardNumberChangeListener method");
        if (valueChangeEvent.getNewValue() != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside addCardNumberChangeListener method after null check");
            this.showErrorMsgEditFlag = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting addCardNumberChangeListener method");
    }


    /**
     * @param accountsList
     */
    public void setAccountsList(String accountsList) {
        this.accountsList = accountsList;
    }

    /**
     * @return
     */
    public String getAccountsList() {
        return accountsList;
    }

    /**
     * @param driverN
     */
    public void setDriverN(String driverN) {
        this.driverN = driverN;
    }

    /**
     * @return
     */
    public String getDriverN() {
        return driverN;
    }

    /**
     * @param displayAccountNumber
     */
    public void setDisplayAccountNumber(String displayAccountNumber) {
        this.displayAccountNumber = displayAccountNumber;
    }

    /**
     * @return
     */
    public String getDisplayAccountNumber() {
        return displayAccountNumber;
    }

    public void setMyAccountDriver(List<Account> myAccountDriver) {
        this.myAccountDriver = myAccountDriver;
    }

    public List<Account> getMyAccountDriver() {
        return myAccountDriver;
    }

    public void setCardNumberList(List<SelectItem> cardNumberList) {
        this.cardNumberList = cardNumberList;
    }

    public List<SelectItem> getCardNumberList() {
        return cardNumberList;
    }

    public void setAddAccountIdDisplayValue(String addAccountIdDisplayValue) {
        this.addAccountIdDisplayValue = addAccountIdDisplayValue;
    }

    public String getAddAccountIdDisplayValue() {
        return addAccountIdDisplayValue;
    }

    public void setAddCardIdDisplayValue(String addCardIdDisplayValue) {
        this.addCardIdDisplayValue = addCardIdDisplayValue;
    }

    public String getAddCardIdDisplayValue() {
        return addCardIdDisplayValue;
    }

    public void setEditAccountIdDisplayValue(String editAccountIdDisplayValue) {
        this.editAccountIdDisplayValue = editAccountIdDisplayValue;
    }

    public String getEditAccountIdDisplayValue() {
        return editAccountIdDisplayValue;
    }

    public void setEditCardIdDisplayValue(String editCardIdDisplayValue) {
        this.editCardIdDisplayValue = editCardIdDisplayValue;
    }

    public String getEditCardIdDisplayValue() {
        if (cardId != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside getEditCardIdDisplayValue method");
            editCardIdDisplayValue = cardId;
        }
        return editCardIdDisplayValue;
    }

    public void setEditCardNumberList(List<SelectItem> editCardNumberList) {
        this.editCardNumberList = editCardNumberList;
    }

    public List<SelectItem> getEditCardNumberList() {
        return editCardNumberList;
    }

    public void setCountryParam(String countryParam) {
        this.countryParam = countryParam;
    }

    public String getCountryParam() {
        return countryParam;
    }

    public void setLinkedCardValues(List<String> linkedCardValues) {
        this.linkedCardValues = linkedCardValues;
    }

    public List<String> getLinkedCardValues() {
        return linkedCardValues;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setWarningMsg(String warningMsg) {
        this.warningMsg = warningMsg;
    }

    public String getWarningMsg() {
        return warningMsg;
    }

    public void setShowErrorMsgFlag(boolean showErrorMsgFlag) {
        this.showErrorMsgFlag = showErrorMsgFlag;
    }

    public boolean isShowErrorMsgFlag() {
        return showErrorMsgFlag;
    }

    public void setShowErrorMsgEditFlag(boolean showErrorMsgEditFlag) {
        this.showErrorMsgEditFlag = showErrorMsgEditFlag;
    }

    public boolean isShowErrorMsgEditFlag() {
        return showErrorMsgEditFlag;
    }

    public void setValidateAccountCard(List<String> validateAccountCard) {
        this.validateAccountCard = validateAccountCard;
    }

    public List<String> getValidateAccountCard() {
        return validateAccountCard;
    }

    public void setPreviousCardId(String previousCardId) {
        this.previousCardId = previousCardId;
    }

    public String getPreviousCardId() {
        return previousCardId;
    }

    public void setLinkedPartnerLOVValues(String linkedPartnerLOVValues) {
        this.linkedPartnerLOVValues = linkedPartnerLOVValues;
    }

    public String getLinkedPartnerLOVValues() {
        return linkedPartnerLOVValues;
    }

    public void setLinkedPartnerList(List<SelectItem> linkedPartnerList) {
        this.linkedPartnerList = linkedPartnerList;
    }

    public List<SelectItem> getLinkedPartnerList() {
        return linkedPartnerList;
    }

    public void partnerNumberValueChangeListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside partnerNumberValueChangeLIstener method");
        if (valueChangeEvent.getNewValue() != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside partnerNumberValueChangeLIstener method after null check");
            this.searchResultsShow = false;
            this.driverN = null;
            linkedAccountList = new ArrayList<SelectItem>();
            linkedAccountLOVValues = new ArrayList<String>();
            if (partnerInfoList != null && partnerInfoList.size() > 0) {
                for (int pa = 0; pa < partnerInfoList.size(); pa++) {
                    if (partnerInfoList.get(pa).getPartnerValue() != null &&
                        partnerInfoList.get(pa).getPartnerValue().toString().equals(valueChangeEvent.getNewValue().toString()) &&
                        partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() > 0) {
                        for (int ac = 0; ac < partnerInfoList.get(pa).getAccountList().size(); ac++) {
                            if (partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber() != null) {
                                SelectItem selectItemAccount = new SelectItem();
                                selectItemAccount.setLabel(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString());
                                selectItemAccount.setValue(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString());
                                linkedAccountList.add(selectItemAccount);
                                linkedAccountLOVValues.add(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString());
                            }
                        }
                    }
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLinkedAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindings().getDriverName());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
        } else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside else partnerNumberValueChangeLIstener method for no selected partner");
            this.linkedAccountLOVValues = null;
            linkedAccountList = new ArrayList<SelectItem>();
            this.linkedAccountLOVValues = null;
            this.driverN = null;
            getBindings().getDriverName().setValue(null);
            searchResultsShow = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLinkedAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindings().getDriverName());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting partnerNumberValueChangeLIstener method");
    }

    public void setLinkedAddAccountList(List<SelectItem> linkedAddAccountList) {
        this.linkedAddAccountList = linkedAddAccountList;
    }

    public List<SelectItem> getLinkedAddAccountList() {
        return linkedAddAccountList;
    }

    public void addPartnerNumberListener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside AddPartnerNumberListener method");
        if (valueChangeEvent.getNewValue() != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside AddPartnerNumberListener method after null check");
            linkedAddAccountList = new ArrayList<SelectItem>();
            cardNumberList = new ArrayList<SelectItem>();
            addPartnerIdVal = null;
            addPartnerIdVal = valueChangeEvent.getNewValue().toString();
            populateAccountNumber(valueChangeEvent.getNewValue().toString(), Constants.ADD_LITERAL);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddAccountId());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddCardId());
        } else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside AddPartnerNumberListener method if no partner selected");
            this.addAccountIdDisplayValue = null;
            linkedAddAccountList = new ArrayList<SelectItem>();
            this.addAccountIdDisplayValue = null;
            cardNumberList = new ArrayList<SelectItem>();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddAccountId());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddCardId());
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting AddPartnerNumberListener method");
    }

    public void editPartnerNumberLIstener(ValueChangeEvent valueChangeEvent) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside EditPartnerNumberLIstener method");
        if (valueChangeEvent.getNewValue() != null) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside EditPartnerNumberLIstener method after null check");
            linkedEditAccountList = new ArrayList<SelectItem>();
            editCardNumberList = new ArrayList<SelectItem>();
            editPartnerIdVal = null;
            editPartnerIdVal = valueChangeEvent.getNewValue().toString();
            populateAccountNumber(valueChangeEvent.getNewValue().toString(), Constants.EDIT_LITERAL);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditAccountId());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditCardId());
        } else {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + " Inside EditPartnerNumberLIstener method if no partner selected");
            this.editAccountIdDisplayValue = null;
            linkedEditAccountList = new ArrayList<SelectItem>();
            this.editAccountIdDisplayValue = null;
            this.cardId = null;
            this.cardId = "";
            editCardNumberList = new ArrayList<SelectItem>();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditAccountId());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditCardId());
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting EditPartnerNumberLIstener method");
    }

    public void populateAccountNumber(String partnerId, String type) {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside PopulateAccountNmber method");
        if (partnerId != null && partnerInfoList != null && partnerInfoList.size() > 0) {

            for (int pa = 0; pa < partnerInfoList.size(); pa++) {
                if (partnerInfoList.get(pa).getPartnerValue().equals(partnerId) && partnerInfoList.get(pa).getAccountList() != null &&
                    partnerInfoList.get(pa).getAccountList().size() > 0) {
                    for (int ac = 0; ac < partnerInfoList.get(pa).getAccountList().size(); ac++) {
                        if (partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber() != null) {
                            SelectItem selectItem = new SelectItem();
                            selectItem.setLabel(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString());
                            selectItem.setValue(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString());
                            if (type.equals(Constants.ADD_LITERAL)) {
                                linkedAddAccountList.add(selectItem);
                            } else {
                                linkedEditAccountList.add(selectItem);
                            }
                        }
                    }
                }
            }
        }
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting PopulateAccountNmber method");
    }

    public void displayErrorComponent(UIComponent component, boolean status) {

        RichSelectManyChoice soc = new RichSelectManyChoice();
        RichSelectOneChoice soc1 = new RichSelectOneChoice();

        {
            if (component instanceof RichSelectManyChoice) {
                soc = (RichSelectManyChoice)component;
                if (status) {
                    soc.setStyleClass(AFMANDATORYFIELDLITERAL);
                    if (component.getId().contains("smc1")) {
                        soc.setStyleClass(AFMANDATORYFIELDLITERAL);
                    }

                } else {
                    soc.setStyleClass(AFNONMANDATORYFIELDLITERAL);
                    if (component.getId().contains("smc1")) {
                        soc.setStyleClass(AFNONMANDATORYFIELDLITERAL);
                    }
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(soc);
            }

            else if (component instanceof RichSelectOneChoice) {
                soc1 = (RichSelectOneChoice)component;
                if (status) {
                    soc1.setStyleClass(AFMANDATORYFIELDLITERAL);
                    if (component.getId().contains("partnerSOC") || component.getId().contains("soc1") || component.getId().contains("soc5") ||
                        component.getId().contains("soc6") || component.getId().contains("soc2")) {
                        soc1.setStyleClass(AFMANDATORYFIELDLITERAL);
                    }

                } else {
                    soc1.setStyleClass(AFNONMANDATORYFIELDLITERAL);
                    if (component.getId().contains("partnerSOC") || component.getId().contains("soc1") || component.getId().contains("soc5") ||
                        component.getId().contains("soc6") || component.getId().contains("soc2")) {
                        soc1.setStyleClass(AFNONMANDATORYFIELDLITERAL);
                    }
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(soc1);
            }

        }
    }

    private Boolean isComponentEmpty(UIComponent rit1) {

        RichSelectManyChoice soc = new RichSelectManyChoice();
        RichSelectOneChoice soc1 = new RichSelectOneChoice();
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

        else if (rit1 instanceof RichSelectOneChoice) {
            soc1 = (RichSelectOneChoice)rit1;
            if (soc1.getValue() == null || soc1.getValue().equals("")) {
                displayErrorComponent(soc1, true);
                return true;
            } else {
                displayErrorComponent(soc1, false);
                return false;
            }
        }

        return true;
    }

    public void setAddPartnerNumberDisplayValue(String addPartnerNumberDisplayValue) {
        this.addPartnerNumberDisplayValue = addPartnerNumberDisplayValue;
    }

    public String getAddPartnerNumberDisplayValue() {
        return addPartnerNumberDisplayValue;
    }

    public void setAddPartnerIdVal(String addPartnerIdVal) {
        this.addPartnerIdVal = addPartnerIdVal;
    }

    public String getAddPartnerIdVal() {
        return addPartnerIdVal;
    }

    public void setEditPartnerIdVal(String editPartnerIdVal) {
        this.editPartnerIdVal = editPartnerIdVal;
    }

    public String getEditPartnerIdVal() {
        return editPartnerIdVal;
    }

    public void setEditPartnerNumberDisplayValue(String editPartnerNumberDisplayValue) {
        this.editPartnerNumberDisplayValue = editPartnerNumberDisplayValue;
    }

    public String getEditPartnerNumberDisplayValue() {
        return editPartnerNumberDisplayValue;
    }

    public void setLinkedEditAccountList(List<SelectItem> linkedEditAccountList) {
        this.linkedEditAccountList = linkedEditAccountList;
    }

    public List<SelectItem> getLinkedEditAccountList() {
        return linkedEditAccountList;
    }

    public void setDriverMap(Map<String, String> driverMap) {
        this.driverMap = driverMap;
    }

    public Map<String, String> getDriverMap() {
        return driverMap;
    }

    public void setLinkedAccountList(List<SelectItem> linkedAccountList) {
        this.linkedAccountList = linkedAccountList;
    }

    public void setisEditVisible(Boolean isEditVisible) {
        this.isEditVisible = isEditVisible;
    }

    public Boolean getisEditVisible() {
        return isEditVisible;
    }

    public void setIsEditDisable(Boolean isEditDisable) {
        this.isEditDisable = isEditDisable;
    }

    public Boolean getIsEditDisable() {
        return isEditDisable;
    }

    public void setComparator(Comparator<SelectItem> comparator) {
        this.comparator = comparator;
    }

    public Comparator<SelectItem> getComparator() {
        return comparator;
    }

    public class Bindings {
        private RichSelectManyChoice linkedAccount;
        private RichPanelGroupLayout searchResults;
        private RichPopup newDriver;
        private RichPopup editDriver;
        private RichPopup deleteDriver;
        private RichInputText driverName;
        private RichPopup deleteAllInfoDriver;
        private RichPanelGroupLayout searchPanelGroupLayout;
        private RichSelectOneChoice addCardId;
        private RichSelectOneChoice addAccountId;
        private RichSelectOneChoice editAccountId;
        private RichSelectOneChoice editCardId;
        private RichOutputText showErrorMsg;
        private RichOutputText showEditErrorMessage;
        private RichSelectOneChoice linkedPartner;
        private RichInputText addDriverNumber;
        private RichInputText addDriverName;
        private RichSelectOneChoice addPartnerNumberId;
        private RichInputText editDriverNumber;
        private RichInputText editDriverName;
        private RichSelectOneChoice editPartnerNumberId;
        private RichInputText addReferenceNumber;
        private RichInputText editReferenceNumber;


        /**
         * @param linkedAccount
         */
        public void setLinkedAccount(RichSelectManyChoice linkedAccount) {
            this.linkedAccount = linkedAccount;
        }

        /**
         * @return
         */
        public RichSelectManyChoice getLinkedAccount() {
            return linkedAccount;
        }

        /**
         * @param searchResults
         */
        public void setSearchResults(RichPanelGroupLayout searchResults) {
            this.searchResults = searchResults;
        }

        /**
         * @return
         */
        public RichPanelGroupLayout getSearchResults() {
            return searchResults;
        }

        /**
         * @param newDriver
         */
        public void setNewDriver(RichPopup newDriver) {
            this.newDriver = newDriver;
        }

        /**
         * @return
         */
        public RichPopup getNewDriver() {
            return newDriver;
        }

        /**
         * @param editDriver
         */
        public void setEditDriver(RichPopup editDriver) {
            this.editDriver = editDriver;
        }

        /**
         * @return
         */
        public RichPopup getEditDriver() {
            return editDriver;
        }

        /**
         * @param deleteDriver
         */
        public void setDeleteDriver(RichPopup deleteDriver) {
            this.deleteDriver = deleteDriver;
        }

        /**
         * @return
         */
        public RichPopup getDeleteDriver() {
            return deleteDriver;
        }

        /**
         * @param driverName
         */
        public void setDriverName(RichInputText driverName) {
            this.driverName = driverName;
        }

        /**
         * @return
         */
        public RichInputText getDriverName() {
            return driverName;
        }

        public void setDeleteAllInfoDriver(RichPopup deleteAllInfoDriver) {
            this.deleteAllInfoDriver = deleteAllInfoDriver;
        }

        public RichPopup getDeleteAllInfoDriver() {
            return deleteAllInfoDriver;
        }

        public void setSearchPanelGroupLayout(RichPanelGroupLayout searchPanelGroupLayout) {
            this.searchPanelGroupLayout = searchPanelGroupLayout;
        }

        public RichPanelGroupLayout getSearchPanelGroupLayout() {
            return searchPanelGroupLayout;
        }

        public void setAddCardId(RichSelectOneChoice addCardId) {
            this.addCardId = addCardId;
        }

        public RichSelectOneChoice getAddCardId() {
            return addCardId;
        }

        public void setAddAccountId(RichSelectOneChoice addAccountId) {
            this.addAccountId = addAccountId;
        }

        public RichSelectOneChoice getAddAccountId() {
            return addAccountId;
        }

        public void setEditAccountId(RichSelectOneChoice editAccountId) {
            this.editAccountId = editAccountId;
        }

        public RichSelectOneChoice getEditAccountId() {
            return editAccountId;
        }

        public void setEditCardId(RichSelectOneChoice editCardId) {
            this.editCardId = editCardId;
        }

        public RichSelectOneChoice getEditCardId() {
            return editCardId;
        }

        public void setShowErrorMsg(RichOutputText showErrorMsg) {
            this.showErrorMsg = showErrorMsg;
        }

        public RichOutputText getShowErrorMsg() {
            return showErrorMsg;
        }

        public void setShowEditErrorMessage(RichOutputText showEditErrorMessage) {
            this.showEditErrorMessage = showEditErrorMessage;
        }

        public RichOutputText getShowEditErrorMessage() {
            return showEditErrorMessage;
        }

        public void setLinkedPartner(RichSelectOneChoice linkedPartner) {
            this.linkedPartner = linkedPartner;
        }

        public RichSelectOneChoice getLinkedPartner() {
            return linkedPartner;
        }

        public void setAddDriverNumber(RichInputText addDriverNumber) {
            this.addDriverNumber = addDriverNumber;
        }

        public RichInputText getAddDriverNumber() {
            return addDriverNumber;
        }

        public void setAddDriverName(RichInputText addDriverName) {
            this.addDriverName = addDriverName;
        }

        public RichInputText getAddDriverName() {
            return addDriverName;
        }

        public void setAddPartnerNumberId(RichSelectOneChoice addPartnerNumberId) {
            this.addPartnerNumberId = addPartnerNumberId;
        }

        public RichSelectOneChoice getAddPartnerNumberId() {
            return addPartnerNumberId;
        }

        public void setEditDriverNumber(RichInputText editDriverNumber) {
            this.editDriverNumber = editDriverNumber;
        }

        public RichInputText getEditDriverNumber() {
            return editDriverNumber;
        }

        public void setEditDriverName(RichInputText editDriverName) {
            this.editDriverName = editDriverName;
        }

        public RichInputText getEditDriverName() {
            return editDriverName;
        }

        public void setEditPartnerNumberId(RichSelectOneChoice editPartnerNumberId) {
            this.editPartnerNumberId = editPartnerNumberId;
        }

        public RichSelectOneChoice getEditPartnerNumberId() {
            return editPartnerNumberId;
        }

        public void setAddReferenceNumber(RichInputText addReferenceNumber) {
            this.addReferenceNumber = addReferenceNumber;
        }

        public RichInputText getAddReferenceNumber() {
            return addReferenceNumber;
        }

        public void setEditReferenceNumber(RichInputText editReferenceNumber) {
            this.editReferenceNumber = editReferenceNumber;
        }

        public RichInputText getEditReferenceNumber() {
            return editReferenceNumber;
        }
    }
}

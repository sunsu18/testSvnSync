package com.sfr.engage.vehicleinfotaskflow;


import com.sfr.core.bean.User;
import com.sfr.engage.core.Account;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.core.VehicleInfo;
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


public class VehicleInfoBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    private List<Account> myAccount;
    private boolean searchResultsShow = false;
    private ResourceBundle resourceBundle;
    private Map<String, String> val = new HashMap<String, String>();
    private List<VehicleInfo> moreColumnsTable;
    private String registrationNumber;
    private String accountsList;
    private List<SelectItem> linkedAccountList = null;
    private String addAccountNumberVal = null;
    private String editAccountNumberVal = null;
    private List<String> linkedAccountLOVValues;
    private String displayAccountNumber;
    private List<Account> myAccountList;

    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
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
    public static final ADFLogger _logger = AccessDataControl.getSFRLogger();
    private AccessDataControl accessDC = new AccessDataControl();


    /**
     * @return bindings Object
     */
    public Bindings getBindings() {
        if (bindings == null) {
            bindings = new Bindings();
        }
        return bindings;
    }

    public VehicleInfoBean() {
        super();
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Constructor of Vehicle Info");
        resourceBundle = new EngageResourceBundle();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        countryParam = null;
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
                countryParam = partnerInfoList.get(0).getCountry().toString().trim();
                SelectItem selectItemPartner = new SelectItem();
                if (partnerInfoList.get(pa).getPartnerName() != null && partnerInfoList.get(pa).getPartnerValue() != null) {
                    selectItemPartner.setLabel(partnerInfoList.get(pa).getPartnerName().toString().trim());
                    selectItemPartner.setValue(partnerInfoList.get(pa).getPartnerValue().toString().trim());
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
                                            linkedCardValues.add(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString().trim());
                                            cardNumberMap.put(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString().trim(),
                                                              partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString().trim());

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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting from Constructor of Vehicle Info");
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
     * @param actionEvent
     */
    /*public void searchAction(ActionEvent actionEvent) {
        searchResults(true);
    }*/

    /**
     * This method performs search functionality in VehicleInfo Page.
     *
     */
    public String searchAction() {
        searchResults(true);
        return null;
    }

    /**
     * This method is reusable for different scenario's in VehicleInfo Page to show searchResults.
     */
    public String searchResults(boolean value) {

        try {
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Search methof of Vehicle Info");
            if (value == true) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside Search methof of Vehicle Info for boolean true");
                if (getBindings().getLinkedPartner().getValue() != null) {
                    if (getBindings().getLinkedAccount().getValue() != null) {
                        searchResultsExecution();
                    } else {
                        if (resourceBundle.containsKey("VEHICLE_LINKED_ACCOUNT")) {
                            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("VEHICLE_LINKED_ACCOUNT"), "");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            return null;
                        }
                    }
                } else {
                    if (resourceBundle.containsKey("LINKED_PARTNER")) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("LINKED_PARTNER"), "");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        return null;
                    }
                }
            } else {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside Search method of Vehicle Info for boolean fasle Add/Edit");
                if (getBindings().getLinkedPartner().getValue() == null && getBindings().getLinkedAccount().getValue() == null &&
                    addAccountNumberVal != null && addPartnerIdVal != null) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                 " Inside Search method of Vehicle Info for Account and Partner values not selected(null)");
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
                    linkedAccountLOVValues.add(addAccountNumberVal);
                } else {
                    if (getBindings().getLinkedPartner().getValue() != null && getBindings().getLinkedAccount().getValue() != null) {
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                     " Inside Search method of Vehicle Info for Account and Partner values not null");
                        String searchValues = getBindings().getLinkedAccount().getValue().toString().trim();
                        String[] search = StringConversion(searchValues);
                        if (addAccountNumberVal != null && addPartnerIdVal != null) {
                            if (addPartnerIdVal.equals(getBindings().getLinkedPartner().getValue().toString().trim())) {
                                int count = 0;
                                for (int i = 0; i < search.length; i++) {
                                    if ((addAccountNumberVal.equalsIgnoreCase(search[i].trim()))) {
                                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                     " Inside Search method of Vehicle Info for Add Account check.");
                                        count = 1;
                                    }
                                }
                                if (count == 0) {
                                    linkedAccountLOVValues.add(addAccountNumberVal);
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
                                linkedAccountLOVValues.add(addAccountNumberVal);
                                this.linkedPartnerLOVValues = addPartnerIdVal;
                            }
                        }
                        if (editAccountNumberVal != null && editPartnerIdVal != null) {
                            if (editPartnerIdVal.equals(getBindings().getLinkedPartner().getValue().toString().trim())) {
                                int count = 0;
                                for (int i = 0; i < search.length; i++) {
                                    if ((editAccountNumberVal.equalsIgnoreCase(search[i].trim()))) {
                                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                     " Inside Search method of Vehicle Info for Edit Account check.");
                                        count = 1;
                                    }
                                }
                                if (count == 0) {
                                    linkedAccountLOVValues.add(editAccountNumberVal);
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
                                linkedAccountLOVValues.add(editAccountNumberVal);
                                this.linkedPartnerLOVValues = editPartnerIdVal;
                            }
                        }
                    }
                }
                _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting search method of Vehicle Info");
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLinkedAccount());
                addAccountNumberVal = null;
                editAccountNumberVal = null;
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

    public String[] StringConversion(String passedVal) {
        List<String> container;
        String tempString = passedVal.substring(1, passedVal.length() - 1);
        String[] val = tempString.split(",");
        return val;
    }

    public String searchResultsExecution() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside searchResultsExecution method of Vehicle Info");
        ViewObject vo = ADFUtils.getViewObject("PrtTruckInformationVO1Iterator");
        if ("trim(ACCOUNT_NUMBER) =: accountNumber AND trim(REGISTRATION_NUMBER) like '%'||:registrationNumber||'%'".equalsIgnoreCase(vo.getWhereClause())) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Removing dynamic where caluse for Account & Registration number");
            vo.removeNamedWhereClauseParam("accountNumber");
            vo.removeNamedWhereClauseParam("registrationNumber");
            vo.setWhereClause("");
            vo.executeQuery();
        }

        if ("trim(ACCOUNT_NUMBER) =: accountNumber".equalsIgnoreCase(vo.getWhereClause())) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Removing dynamic where caluse for Account");
            vo.removeNamedWhereClauseParam("accountNumber");
            vo.setWhereClause("");
            vo.executeQuery();
        }

        int count = 0;
        String[] values;
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Select account number:::::::: " + getBindings().getLinkedAccount().getValue());
        String selectedValues = getBindings().getLinkedAccount().getValue().toString().trim();
        String passingValues = selectedValues.substring(1, selectedValues.length() - 1);
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Passing account number:::::::: " + passingValues);
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
            List<VehicleInfo> myVehicleList = new ArrayList<VehicleInfo>();
            vo.setNamedWhereClauseParam("countryCd", countryParam);
            if (getBindings().getRegisterNumber().getValue() != null && getBindings().getRegisterNumber().getValue().toString().length() > 0) {
                vo.setWhereClause("trim(ACCOUNT_NUMBER) =: accountNumber AND trim(REGISTRATION_NUMBER) like '%'||:registrationNumber||'%'");
                vo.defineNamedWhereClauseParam("accountNumber", values[i].trim(), null);
                vo.defineNamedWhereClauseParam("registrationNumber", getBindings().getRegisterNumber().getValue().toString().trim(), null);
            } else {
                vo.setWhereClause("trim(ACCOUNT_NUMBER) =: accountNumber");
                vo.defineNamedWhereClauseParam("accountNumber", values[i].trim(), null);

            }
            vo.executeQuery();
            if (vo.getEstimatedRowCount() != 0) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Row count after query execution:::::::: " + vo.getEstimatedRowCount());
                for (int j = 0; j < vo.getEstimatedRowCount(); j++) {
                    while (vo.hasNext()) {
                        PrtTruckInformationVORowImpl currRow = (PrtTruckInformationVORowImpl)vo.next();
                        if (currRow != null) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Current row card number:::::::: " + currRow.getCardNumber());
                            if (currRow.getCardNumber() == null || linkedCardValues.contains(currRow.getCardNumber().toString())) {
                                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Is it coming inside to create vehicle details details List");
                                VehicleInfo vehicle = new VehicleInfo();
                                if (currRow.getPrtTruckInformationPk() != null) {
                                    vehicle.setPrtTruckInformationPK(currRow.getPrtTruckInformationPk().toString().trim());
                                }
                                vehicle.setAccountNumber(currRow.getAccountNumber());
                                vehicle.setVehicleNumber(currRow.getVehicleNumber());
                                vehicle.setCardNumber(currRow.getCardNumber());
                                vehicle.setEmbossCardNumber(cardNumberMap.get(currRow.getCardNumber()));
                                vehicle.setInternalName(currRow.getInternalName());
                                vehicle.setRegistrationNumber(currRow.getRegistrationNumber());
                                vehicle.setBrand(currRow.getBrand());
                                if (currRow.getYear() != null) {
                                    vehicle.setYear(Integer.parseInt(currRow.getYear().toString().trim()));
                                }
                                if (currRow.getRegistrationDate() != null) {
                                    vehicle.setRegistrationDate(currRow.getRegistrationDate().getValue());
                                }
                                if (currRow.getEndDate() != null) {
                                    vehicle.setEndDate(currRow.getEndDate().getValue());
                                }
                                vehicle.setRemarks(currRow.getRemarks());
                                vehicle.setFuelType(currRow.getFuelType());
                                if (currRow.getMaxFuel() != null) {
                                    vehicle.setMaxFuel(Integer.parseInt(currRow.getMaxFuel().toString().trim()));
                                }
                                if (currRow.getOdometer() != null) {
                                    vehicle.setOdoMeter(Integer.parseInt(currRow.getOdometer().toString().trim()));
                                }
                                myVehicleList.add(vehicle);
                            }
                        }
                    }
                }

            }
            acc.setVehicleInfoList(myVehicleList);
            myAccount.add(acc);
        }
        myAccountList = new ArrayList<Account>();
        if (myAccount.size() > 0) {
            if (getBindings().getRegisterNumber().getValue() != null && getBindings().getRegisterNumber().getValue().toString().length() > 0) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Inside if block of new account list");
                for (int k = 0; k < myAccount.size(); k++) {
                    if (myAccount.get(k).getVehicleInfoList().size() > 0) {
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Inside if block to create new Account list");
                        myAccountList.add(myAccount.get(k));
                    }
                }
            } else {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Inside else block of new account list");
                if (myAccount.size() > 50) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Inside else block of for list size greater than 50");
                    for (int m = 0; m < myAccount.size(); m++) {
                        if (myAccount.get(m).getVehicleInfoList().size() > 0) {
                            myAccountList.add(myAccount.get(m));
                        }
                    }
                } else {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Inside else block of for list size greater than 50");
                    for (int m = 0; m < myAccount.size(); m++) {
                        myAccountList.add(myAccount.get(m));
                    }
                }
            }
        }

        if (myAccountList.size() > 0) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside if block of the show condition of panel");
            searchResultsShow = true;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
        } else {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside else block of the show condition of panel");
            searchResultsShow = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
            if (resourceBundle.containsKey("NO_RECORDS_FOUND_VEHICLE")) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("NO_RECORDS_FOUND_VEHICLE"), "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting SearchResultsExecution method");
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
     * @param actionEvent
     */
    public void newVehicleSave(ActionEvent actionEvent) {

    }

    /**
     * This Method will save new vehicle information in DB.
     * @return
     */
    public String newVehicleSave() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Add new vehicle save method");
        User user = null;
        String modifiedBy = null;
        user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
        if (user != null) {
            modifiedBy = user.getFirstName().concat(" ").concat(user.getLastName());
            if (modifiedBy == null) {
                modifiedBy = user.getUserID();
            }
        }
        if (getBindings().getAddPartnerNumberId().getValue() != null && getBindings().getAddAccountId().getValue() != null &&
            getBindings().getAddVehicleNumber().getValue() != null && getBindings().getAddInternalName().getValue() != null &&
            getBindings().getAddVehicleNumber().getValue().toString().trim() != null &&
            getBindings().getAddInternalName().getValue().toString().trim() != null) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside Add new vehicle save method after null check condition");
            ViewObject driverVo = ADFUtils.getViewObject("PrtDriverInformationVO3Iterator");
            driverVo.setNamedWhereClauseParam("countryCd", countryParam);
            driverVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
            if (addAccountNumberVal == null) {
                if (getBindings().getAddAccountId().getValue() != null) {
                    addAccountNumberVal = getBindings().getAddAccountId().getValue().toString().trim();
                }
            }
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Value of addAccountNumberVal:::::::::" + addAccountNumberVal);
            driverVo.defineNamedWhereClauseParam("accountId", addAccountNumberVal, null);
            if (getBindings().getAddCardId().getValue() != null) {
                driverVo.defineNamedWhereClauseParam("cardNo", getBindings().getAddCardId().getValue().toString().trim(), null);
            } else {
                driverVo.defineNamedWhereClauseParam("cardNo", " ", null);
            }


            driverVo.executeQuery();
            if (driverVo.getEstimatedRowCount() > 0) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside vehicle method to check Existing driver for selected card:::::::::");
                while (driverVo.hasNext()) {
                    PrtDriverInformationVORowImpl currRow = (PrtDriverInformationVORowImpl)driverVo.next();
                    if (currRow != null) {
                        if (resourceBundle.containsKey("DRIVER_CARD_EXIST")) {
                            warningMsg = resourceBundle.getObject("DRIVER_CARD_EXIST").toString().concat(" ").concat(currRow.getDriverName());
                            showErrorMsgFlag = true;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                            return null;
                        }
                    }
                }
            }

            ViewObject truckVo = ADFUtils.getViewObject("PrtTruckInformationVO3Iterator");
            truckVo.setNamedWhereClauseParam("countryCd", countryParam);
            truckVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
            truckVo.defineNamedWhereClauseParam("accountId", addAccountNumberVal, null);
            if (getBindings().getAddCardId().getValue() != null) {
                truckVo.defineNamedWhereClauseParam("cardNo", getBindings().getAddCardId().getValue().toString().trim(), null);
            } else {
                truckVo.defineNamedWhereClauseParam("cardNo", "", null);
            }
            truckVo.executeQuery();
            if (truckVo.getEstimatedRowCount() > 0) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside vehicle method to check Existing vehicle for selected card:::::::::");
                while (truckVo.hasNext()) {
                    PrtTruckInformationVORowImpl currRow = (PrtTruckInformationVORowImpl)truckVo.next();
                    if (currRow != null) {
                        if (resourceBundle.containsKey("TRUCK_CARD_EXIST")) {
                            warningMsg = resourceBundle.getObject("TRUCK_CARD_EXIST").toString().concat(" ").concat(currRow.getVehicleNumber());
                            showErrorMsgFlag = true;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                            return null;
                        }
                    }
                }
            }


            getBindings().getNewVehicle().hide();

            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding vehicleInfoItr = (DCIteratorBinding)bindings.get("PrtTruckInformationVO2Iterator");
            Row vehicleInfoRow = vehicleInfoItr.getCurrentRow();

            if (vehicleInfoRow != null) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside new vehicle save current row is not null:::::::::");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Value of add account id:::::::::" +
                             getBindings().getAddAccountId().getValue().toString());
                if (getBindings().getAddAccountId().getValue() != null) {
                    vehicleInfoRow.setAttribute("AccountNumber", getBindings().getAddAccountId().getValue().toString().trim());
                }
                if (getBindings().getAddCardId().getValue() != null) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Value of add card id:::::::::" +
                                 getBindings().getAddCardId().getValue().toString());
                    vehicleInfoRow.setAttribute("CardNumber", getBindings().getAddCardId().getValue().toString().trim());
                } else {
                    vehicleInfoRow.setAttribute("CardNumber", "");
                }
                if (getBindings().getAddReferenceNumber().getValue() != null) {
                    String refNumber = getBindings().getAddReferenceNumber().getValue().toString().trim();
                    String formattedRefNum = ("0000000000" + refNumber).substring(refNumber.length());
                    vehicleInfoRow.setAttribute("ReferenceNumber", formattedRefNum);
                }
                vehicleInfoRow.setAttribute("ModifiedBy", modifiedBy);
                vehicleInfoRow.setAttribute("CountryCode", countryParam);
                vehicleInfoRow.setAttribute("VehicleNumber", getBindings().getAddVehicleNumber().getValue().toString().trim());
                vehicleInfoRow.setAttribute("InternalName", getBindings().getAddInternalName().getValue().toString().trim());

                if (getBindings().getAddRegistrationNumber().getValue() != null) {
                    vehicleInfoRow.setAttribute("RegistrationNumber", getBindings().getAddRegistrationNumber().getValue().toString().trim());
                }
                OperationBinding newDriverOpn = bindings.getOperationBinding("Commit");
                newDriverOpn.execute();
                if (newDriverOpn.getErrors().isEmpty()) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + "NO failure in commit operation of Vehicle add:::::::::");
                }
            }
        } else {
            if (resourceBundle.containsKey("ENGAGE_SELECT_TRANSACTION_MANDATORY")) {
                warningMsg = resourceBundle.getObject("ENGAGE_SELECT_TRANSACTION_MANDATORY").toString();
                showErrorMsgFlag = true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                return null;
            }
        }
        searchResults(false);
        if (resourceBundle.containsKey("VEHICLE_ADD")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("VEHICLE_ADD"), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting new vehicle save method:::::::::");
        return null;
    }

    /**
     * @return
     */
    public String newVehicleCancel() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside new vehicle cancel method :::::::::");
        ResetUtils.reset(getBindings().getNewVehicle());
        getBindings().getNewVehicle().hide();
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting new vehicle cancel method:::::::::");
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
    public String newVehicleAddAction() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside new vehicle add action method :::::::::");
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding createOpn = bindings.getOperationBinding("CreateInsert");
        createOpn.execute();
        showErrorMsgFlag = false;
        linkedAddAccountList = new ArrayList<SelectItem>();
        addPartnerNumberDisplayValue = null;
        addAccountIdDisplayValue = null;
        addCardIdDisplayValue = null;
        cardNumberList = new ArrayList<SelectItem>();
        if (getBindings().getLinkedPartner().getValue() != null) {
            if (getBindings().getLinkedAccount().getValue() != null && linkedAccountLOVValues.size() > 0) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             "Inside if block new vehicle add for partner and selected account values :::::::::");
                if (linkedAccountLOVValues.size() == 1) {
                    this.addAccountIdDisplayValue = populateStringValues(getBindings().getLinkedAccount().getValue().toString().trim());
                }
                this.addPartnerNumberDisplayValue = getBindings().getLinkedPartner().getValue().toString().trim();
                this.addCardIdDisplayValue = null;
                populateAccountNumber(getBindings().getLinkedPartner().getValue().toString(), "Add");
                populateCardNumberList(populateStringValues(getBindings().getLinkedAccount().getValue().toString()), "newVehicleAdd",
                                       getBindings().getLinkedPartner().getValue().toString());
            }
        } else {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                         "Inside else block new vehicle add for no partner and selected account values:::::::::");
            this.addPartnerNumberDisplayValue = null;
            this.addAccountIdDisplayValue = null;
            this.addCardIdDisplayValue = null;
        }

        if (getBindings().getRegisterNumber().getValue() != null) {
            getBindings().getAddRegistrationNumber().setSubmittedValue(null);
            getBindings().getAddRegistrationNumber().setSubmittedValue(getBindings().getRegisterNumber().getValue().toString().trim());
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting new vehicle add action method :::::::::");
        getBindings().getNewVehicle().show(new RichPopup.PopupHints());

        return null;
    }

    /**
     * This Method will save edited vehicle information in DB.
     * @return
     */
    public String editVehicleSave() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside new vehicle edit save method :::::::::");
        User user = null;
        String modifiedBy = null;
        user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
        if (user != null) {
            modifiedBy = user.getFirstName().concat(" ").concat(user.getLastName());
            if (modifiedBy == null) {
                modifiedBy = user.getUserID();
            }
        }
        if (getBindings().getEditPartnerNumberId().getValue() != null && getBindings().getEditAccountId().getValue() != null &&
            getBindings().getEditInternalName().getValue() != null && getBindings().getEditVehicleNumber().getValue() != null &&
            getBindings().getEditInternalName().getValue().toString().trim() != null &&
            getBindings().getEditVehicleNumber().getValue().toString().trim() != null) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Inside new vehicle edit save method after null check :::::::::");
            ViewObject driverVo = ADFUtils.getViewObject("PrtDriverInformationVO3Iterator");
            driverVo.setNamedWhereClauseParam("countryCd", countryParam);
            driverVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Value of edit account number :::::::::" +
                         getBindings().getEditAccountId().getValue());
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "value of editAccountNumberVal:::::::::" + editAccountNumberVal);
            driverVo.defineNamedWhereClauseParam("accountId", editAccountNumberVal, null);
            if (getBindings().getEditCardId().getValue() != null) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Value of edit card number :::::::::" +
                             getBindings().getEditCardId().getValue());
                driverVo.defineNamedWhereClauseParam("cardNo", getBindings().getEditCardId().getValue().toString().trim(), null);
            } else {
                driverVo.defineNamedWhereClauseParam("cardNo", "", null);
            }

            driverVo.executeQuery();
            if (driverVo.getEstimatedRowCount() > 0) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Inside of edit vehicle save method to check for existing driver :::::::::");
                while (driverVo.hasNext()) {
                    PrtDriverInformationVORowImpl currRow = (PrtDriverInformationVORowImpl)driverVo.next();
                    if (currRow != null) {
                        if (resourceBundle.containsKey("DRIVER_CARD_EXIST")) {
                            warningMsg = resourceBundle.getObject("DRIVER_CARD_EXIST").toString().concat(" ").concat(currRow.getDriverName());
                            showErrorMsgEditFlag = true;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
                            return null;
                        }
                    }
                }
            }

            if (previousCardId != null && getBindings().getEditCardId().getValue() != null) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             "Inside of edit vehicle save method to check for existing truck and previous card not null :::::::::");
                if (!previousCardId.equals(getBindings().getEditCardId().getValue().toString().trim())) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                 "Previous card not equals to selected edit card, dont allow edit, value of previous card:::::::::" + previousCardId);

                    ViewObject truckVo = ADFUtils.getViewObject("PrtTruckInformationVO3Iterator");
                    truckVo.setNamedWhereClauseParam("countryCd", countryParam);
                    truckVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
                    truckVo.defineNamedWhereClauseParam("accountId", editAccountNumberVal, null);
                    if (getBindings().getEditCardId().getValue() != null) {
                        truckVo.defineNamedWhereClauseParam("cardNo", getBindings().getEditCardId().getValue().toString().trim(), null);
                    } else {
                        truckVo.defineNamedWhereClauseParam("cardNo", "", null);
                    }
                    truckVo.executeQuery();
                    if (truckVo.getEstimatedRowCount() > 0) {
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Checking for existing vehicle in edit vehicle save method:::::::::");
                        while (truckVo.hasNext()) {
                            PrtTruckInformationVORowImpl currRow = (PrtTruckInformationVORowImpl)truckVo.next();
                            if (currRow != null) {
                                if (resourceBundle.containsKey("TRUCK_CARD_EXIST")) {
                                    warningMsg = resourceBundle.getObject("TRUCK_CARD_EXIST").toString().concat(" ").concat(currRow.getVehicleNumber());
                                    showErrorMsgEditFlag = true;
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
                                    return null;
                                }
                            }
                        }
                    }
                }
            } else {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                             "Inside of edit vehicle save method to check for existing truck and previous card is null :::::::::");
                ViewObject truckVo = ADFUtils.getViewObject("PrtTruckInformationVO3Iterator");
                truckVo.setNamedWhereClauseParam("countryCd", countryParam);
                truckVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
                truckVo.defineNamedWhereClauseParam("accountId", editAccountNumberVal, null);
                if (getBindings().getEditCardId().getValue() != null) {
                    truckVo.defineNamedWhereClauseParam("cardNo", getBindings().getEditCardId().getValue().toString().trim(), null);
                } else {
                    truckVo.defineNamedWhereClauseParam("cardNo", "", null);
                }
                truckVo.executeQuery();
                if (truckVo.getEstimatedRowCount() > 0) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                 "Checking for existing vehicle in edit vehicle save method for previous card null:::::::::");
                    while (truckVo.hasNext()) {
                        PrtTruckInformationVORowImpl currRow = (PrtTruckInformationVORowImpl)truckVo.next();
                        if (currRow != null) {
                            if (resourceBundle.containsKey("TRUCK_CARD_EXIST")) {
                                warningMsg = resourceBundle.getObject("TRUCK_CARD_EXIST").toString().concat(" ").concat(currRow.getVehicleNumber());
                                showErrorMsgEditFlag = true;
                                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
                                return null;
                            }
                        }
                    }
                }
            }

            getBindings().getEditVehicle().hide();
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding vehicleInfoItr = (DCIteratorBinding)bindings.get("PrtTruckInformationVO2Iterator");
            Row vehicleInfoRow = vehicleInfoItr.getCurrentRow();
            if (vehicleInfoRow != null) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Inside new vehicle save current row is not null:::::::::");
                vehicleInfoRow.setAttribute("AccountNumber", getBindings().getEditAccountId().getValue().toString());
                if (getBindings().getEditCardId().getValue() != null) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Value of Edit card id:::::::::" +
                                 getBindings().getEditCardId().getValue().toString());
                    vehicleInfoRow.setAttribute("CardNumber", getBindings().getEditCardId().getValue().toString().trim());
                } else {
                    vehicleInfoRow.setAttribute("CardNumber", "");
                }
                if (getBindings().getEditReferenceNumber().getValue() != null) {
                    String refNumber = getBindings().getEditReferenceNumber().getValue().toString().trim();
                    String formattedRefNum = ("0000000000" + refNumber).substring(refNumber.length());
                    vehicleInfoRow.setAttribute("ReferenceNumber", formattedRefNum);
                }
                vehicleInfoRow.setAttribute("ModifiedBy", modifiedBy);
                vehicleInfoRow.setAttribute("CountryCode", countryParam);
                vehicleInfoRow.setAttribute("VehicleNumber", getBindings().getEditVehicleNumber().getValue().toString().trim());
                vehicleInfoRow.setAttribute("InternalName", getBindings().getEditInternalName().getValue().toString().trim());
                if (getBindings().getEditRegistrationNumber().getValue() != null) {
                    vehicleInfoRow.setAttribute("RegistrationNumber", getBindings().getEditRegistrationNumber().getValue().toString().trim());
                }
                OperationBinding newDriverOpn = bindings.getOperationBinding("Commit");
                newDriverOpn.execute();
                if (newDriverOpn.getErrors().isEmpty()) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " No failure or edit commit operation");
                }
            }

        } else {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Throwing error for mandatory check");
            if (resourceBundle.containsKey("ENGAGE_SELECT_TRANSACTION_MANDATORY")) {
                warningMsg = resourceBundle.getObject("ENGAGE_SELECT_TRANSACTION_MANDATORY").toString();
                showErrorMsgEditFlag = true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
                return null;
            }
        }
        searchResults(false);
        if (resourceBundle.containsKey("VEHICLE_EDIT")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("VEHICLE_EDIT"), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting edit vehicle save method");
        return null;
    }

    /**
     * @return
     */
    public String editVehicleCancel() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside edit vehicle cancel method");
        ResetUtils.reset(getBindings().getEditVehicle());
        getBindings().getEditVehicle().hide();
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting edit vehicle cancel method");
        return null;
    }

    /**
     * This Method will execute VO and fetches selected row from DB and perform Edit operation.
     * @return
     */
    public String tableEditAction() {
        try {
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside table edit action method");
            showErrorMsgEditFlag = false;
            editAccountIdDisplayValue = null;
            editCardIdDisplayValue = null;
            editCardNumberList = new ArrayList<SelectItem>();
            linkedEditAccountList = new ArrayList<SelectItem>();
            String primaryKey = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("primarykey");
            String accountNumber = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountnumber");
            cardId = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardid");
            previousCardId = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardid");
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Value of primary key:::::::" + primaryKey);
            if (primaryKey != null && accountNumber != null && getBindings().getLinkedPartner().getValue() != null) {
                editPartnerNumberDisplayValue = getBindings().getLinkedPartner().getValue().toString().trim();
                editAccountIdDisplayValue = accountNumber;
                editAccountNumberVal = editAccountIdDisplayValue;
                if (editAccountIdDisplayValue != null) {
                    populateAccountNumber(getBindings().getLinkedPartner().getValue().toString().trim(), "Edit");
                    populateCardNumberList(editAccountIdDisplayValue, "editButton", getBindings().getLinkedPartner().getValue().toString().trim());
                }

                ViewObject vo = ADFUtils.getViewObject("PrtTruckInformationVO2Iterator");
                vo.setNamedWhereClauseParam("countryCd", countryParam);
                vo.setWhereClause("PRT_TRUCK_INFORMATION_PK =: prtTruckInformationPK");
                vo.defineNamedWhereClauseParam("prtTruckInformationPK", primaryKey, null);
                vo.executeQuery();
                getBindings().getEditVehicle().show(new RichPopup.PopupHints());
            }
        } catch (JboException ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, ex.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting table edit action method");
        return null;
    }

    /**
     * This Method will show confirmation popup for Delete operation.
     * @return
     */
    public String vehicleDeleteAction() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Vehicle delete action method");
        if (val.size() != 0) {
            ArrayList<String> validateCard = new ArrayList<String>();
            if (partnerInfoList != null && partnerInfoList.size() > 0) {
                for (int pa = 0; pa < partnerInfoList.size(); pa++) {
                    if (partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() > 0) {
                        for (int ac = 0; ac < partnerInfoList.get(pa).getAccountList().size(); ac++) {
                            if (validateAccountCard.contains(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString()) &&
                                partnerInfoList.get(pa).getAccountList().get(ac).isAccountOverview() == false) {
                                validateCard.add(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString());
                            }
                        }
                    }
                }
            }
            if (validateCard != null && validateCard.size() > 0) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Inside Vehicle delete action method and list of deleted cards" +
                             validateCard);
                if (resourceBundle.containsKey("NO_DELETE_VEHICLE")) {
                    String cardList = validateCard.toString();
                    String validateCardValues = cardList.substring(1, cardList.length() - 1).replace(" ", "");
                    String vehicleErrorMsg = resourceBundle.getObject("NO_DELETE_VEHICLE").toString().concat(validateCardValues);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, vehicleErrorMsg, "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
            }
            getBindings().getDeleteVehicle().show(new RichPopup.PopupHints());
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("VEHICLE_DELETE_FAILURE_1"), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting Vehicle delete action method");
        return null;
    }

    /**
     * This Method will delete selected vehicle rows from DB.
     * @return
     */
    public String deleteVehicleSave() {
        try {
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Vehicle delete save method");
            Iterator iter = val.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String)iter.next();
                String vals = val.get(key);
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + "key,val: " + key + "," + vals);
                ViewObject vo = ADFUtils.getViewObject("PrtTruckInformationVO2Iterator");
                vo.setNamedWhereClauseParam("countryCd", countryParam);
                vo.setWhereClause("PRT_TRUCK_INFORMATION_PK =: prtTruckInformationPK");
                vo.defineNamedWhereClauseParam("prtTruckInformationPK", vals, null);
                vo.executeQuery();
                if (vo.getEstimatedRowCount() != 0) {
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                 "Inside Vehicle delete save method to check row count and then removing row");
                    while (vo.hasNext()) {
                        Row r = vo.next();
                        vo.setCurrentRow(r);
                        vo.removeCurrentRow();
                    }
                }
            }
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside Vehicle delete for successful deletion");
                getBindings().getDeleteVehicle().hide();
                val = new HashMap<String, String>();
                searchResults(false);
                if (resourceBundle.containsKey("VEHICLE_DELETE_SUCCESS")) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("VEHICLE_DELETE_SUCCESS"), "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
            } else {
                if (resourceBundle.containsKey("VEHICLE_DELETE_FAILURE")) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("VEHICLE_DELETE_FAILURE"), "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
                _logger.severe(accessDC.getDisplayRecord() + this.getClass() + "Error while commiting");
            }
        } catch (JboException ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting Vehicle delete save method");
        return null;
    }

    /**
     * @return
     */
    public String deleteVehicleCancel() {
        getBindings().getDeleteVehicle().hide();
        return null;
    }

    /**
     * This Method will store selected vehicle rows in HashMap for Delete operation.
     * @param valueChangeEvent
     */
    public void deleteCheckBoxListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Vehicle delete check box listener to store primary key in Hash Map");
        if (valueChangeEvent.getNewValue().equals(true)) {
            validateAccountCard = new ArrayList<String>();
            validateAccountCard.add(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNo").toString());
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + " Value of primary key==>" +
                         AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));
            val.put((String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"),
                    (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));
        } else {
            validateAccountCard = new ArrayList<String>();
            validateAccountCard.remove(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNo").toString());
            if (val.containsKey(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"))) {
                val.remove(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting Vehicle delete check box listener to store primary key in Hash Map");
    }

    /**
     * @param moreColumnsTable
     */
    public void setMoreColumnsTable(List<VehicleInfo> moreColumnsTable) {
        this.moreColumnsTable = moreColumnsTable;
    }

    /**
     * @return
     */
    public List<VehicleInfo> getMoreColumnsTable() {
        return moreColumnsTable;
    }

    /**
     * This Method will clears selected Linked Account and Registration numbers from VehcileInfo Page.
     * @param actionEvent
     */
    public void searchCancel(ActionEvent actionEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Vehicle Search cancel method");
        ViewObject vo = ADFUtils.getViewObject("PrtTruckInformationVO1Iterator");

        if ("trim(ACCOUNT_NUMBER) =: accountNumber AND trim(REGISTRATION_NUMBER) like '%'||:registrationNumber||'%'".equalsIgnoreCase(vo.getWhereClause())) {
            vo.removeNamedWhereClauseParam("accountNumber");
            vo.removeNamedWhereClauseParam("registrationNumber");
            vo.setWhereClause("");
            vo.executeQuery();
        }

        if ("trim(ACCOUNT_NUMBER) =: accountNumber".equalsIgnoreCase(vo.getWhereClause())) {
            vo.removeNamedWhereClauseParam("accountNumber");
            vo.setWhereClause("");
            vo.executeQuery();
        }
        this.linkedPartnerLOVValues = null;
        this.linkedAccountLOVValues = null;
        linkedAccountList = new ArrayList<SelectItem>();
        this.linkedAccountLOVValues = null;
        this.registrationNumber = null;
        getBindings().getLinkedPartner().setSubmittedValue(null);
        getBindings().getLinkedPartner().setValue(null);
        getBindings().getRegisterNumber().setValue(null);
        searchResultsShow = false;
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting Vehicle Search cancel method");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindings().getLinkedPartner());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLinkedAccount());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRegisterNumber());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());

    }

    /**
     * @param registrationNumber
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * @return
     */
    public String getRegistrationNumber() {
        return registrationNumber;
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

    public void AddAccountNumberListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside AddAccountNumberValueChangeListener method");
        if (valueChangeEvent.getNewValue() != null && getBindings().getAddPartnerNumberId().getValue() != null) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside AddAccountNumberValueChangeListener method after null check");
            cardNumberList = new ArrayList<SelectItem>();
            populateCardNumberList(valueChangeEvent.getNewValue().toString(), "Add", getBindings().getAddPartnerNumberId().getValue().toString());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddCardId());
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting AddAccountNumberValueChangeListener method");
    }

    public void populateAccountNumber(String partnerId, String type) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside PopulateAccountNmber method");
        if (partnerId != null) {
            if (partnerInfoList != null && partnerInfoList.size() > 0) {
                for (int pa = 0; pa < partnerInfoList.size(); pa++) {
                    if (partnerInfoList.get(pa).getPartnerValue().equals(partnerId) && partnerInfoList.get(pa).getAccountList() != null &&
                        partnerInfoList.get(pa).getAccountList().size() > 0) {
                        for (int ac = 0; ac < partnerInfoList.get(pa).getAccountList().size(); ac++) {
                            if (partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber() != null) {
                                SelectItem selectItem = new SelectItem();
                                selectItem.setLabel(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString());
                                selectItem.setValue(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString());
                                if (type.equals("Add")) {
                                    linkedAddAccountList.add(selectItem);
                                } else {
                                    linkedEditAccountList.add(selectItem);
                                }
                            }
                        }
                    }
                }
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting PopulateAccountNmber method");
    }

    public void partnerNumberValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside partnerNumberValueChangeLIstener method");
        if (valueChangeEvent.getNewValue() != null) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside partnerNumberValueChangeLIstener method after null check");
            this.searchResultsShow = false;
            this.registrationNumber = null;
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
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRegisterNumber());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
        } else {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside else partnerNumberValueChangeLIstener method for no selected partner");
            this.linkedAccountLOVValues = null;
            linkedAccountList = new ArrayList<SelectItem>();
            this.linkedAccountLOVValues = null;
            this.registrationNumber = null;
            getBindings().getRegisterNumber().setValue(null);
            searchResultsShow = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLinkedAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRegisterNumber());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting partnerNumberValueChangeLIstener method");
    }

    Comparator<SelectItem> comparator = new Comparator<SelectItem>() {
        @Override
        public int compare(SelectItem s1, SelectItem s2) {
            return s1.getLabel().compareTo(s2.getLabel());
        }
    };

    public void populateCardNumberList(String accountNo, String type, String partnerNumber) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside populateCardNumberList method");
        if (accountNo != null) {
            if (partnerInfoList != null && partnerInfoList.size() > 0) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside populateCardNumberList method after null check");
                for (int pa = 0; pa < partnerInfoList.size(); pa++) {
                    if (partnerInfoList.get(pa).getPartnerValue().equals(partnerNumber) && partnerInfoList.get(pa).getAccountList() != null &&
                        partnerInfoList.get(pa).getAccountList().size() > 0) {
                        for (int ac = 0; ac < partnerInfoList.get(pa).getAccountList().size(); ac++) {
                            if (partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber() != null &&
                                partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().equals(accountNo)) {
                                if (partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup() != null &&
                                    partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size() > 0) {
                                    for (int cg = 0; cg < partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size(); cg++) {
                                        if (partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard() != null &&
                                            partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size() > 0) {
                                            for (int cc = 0; cc < partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size();
                                                 cc++) {
                                                if (partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID() !=
                                                    null &&
                                                    partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID() !=
                                                    null) {
                                                    SelectItem selectItem = new SelectItem();
                                                    selectItem.setLabel(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
                                                    selectItem.setValue(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                                    if (type.equals("Add")) {
                                                        addAccountNumberVal = accountNo;
                                                        cardNumberList.add(selectItem);
                                                    } else if (type.equals("Edit")) {
                                                        editAccountNumberVal = accountNo;
                                                        editCardNumberList.add(selectItem);
                                                    } else if (type.equals("newVehicleAdd")) {
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
                }
                if (type.equals("Add") || type.equals("newVehicleAdd")) {
                    Collections.sort(cardNumberList, comparator);
                } else {
                    Collections.sort(editCardNumberList, comparator);
                }
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting populateCardNumberList method");
    }

    public void setAddAccountNumberVal(String addAccountNumberVal) {
        this.addAccountNumberVal = addAccountNumberVal;
    }

    public String getAddAccountNumberVal() {
        return addAccountNumberVal;
    }

    public void setEditAccountNumberVal(String editAccountNumberVal) {
        this.editAccountNumberVal = editAccountNumberVal;
    }

    public String getEditAccountNumberVal() {
        return editAccountNumberVal;
    }

    public void setLinkedAccountLOVValues(List<String> linkedAccountLOVValues) {
        this.linkedAccountLOVValues = linkedAccountLOVValues;
    }

    public List<String> getLinkedAccountLOVValues() {
        return linkedAccountLOVValues;
    }

    public void editAccountNumberChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside editAccountNumberValueChangeListener method");
        if (valueChangeEvent.getNewValue() != null && getBindings().getEditPartnerNumberId().getValue() != null) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside editAccountNumberValueChangeListener method after null check");
            editCardNumberList = new ArrayList<SelectItem>();
            cardId = null;
            populateCardNumberList(valueChangeEvent.getNewValue().toString(), "Edit", getBindings().getEditPartnerNumberId().getValue().toString());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditCardId());
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + "editAccountNumber =" + editAccountNumberVal);
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting editAccountNumberValueChangeListener method");
    }

    public void editCardNumberChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside editCardNumberChangeListener method");
        if (valueChangeEvent.getNewValue() != null) {
            cardId = valueChangeEvent.getNewValue().toString();
        } else {
            this.cardId = null;
            this.cardId = "";
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting editCardNumberChangeListener method");
        this.showErrorMsgEditFlag = false;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditCardId());
    }

    public void addCardNumberChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside addCardNumberChangeListener method");
        if (valueChangeEvent.getNewValue() != null) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside addCardNumberChangeListener method after null check");
            this.showErrorMsgEditFlag = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting addCardNumberChangeListener method");
    }


    public void AddPartnerNumberListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside AddPartnerNumberListener method");
        if (valueChangeEvent.getNewValue() != null) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside AddPartnerNumberListener method after null check");
            linkedAddAccountList = new ArrayList<SelectItem>();
            cardNumberList = new ArrayList<SelectItem>();
            addPartnerIdVal = null;
            addPartnerIdVal = valueChangeEvent.getNewValue().toString();
            populateAccountNumber(valueChangeEvent.getNewValue().toString(), "Add");
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddAccountId());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddCardId());
        } else {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside AddPartnerNumberListener method if no partner selected");
            this.addAccountIdDisplayValue = null;
            linkedAddAccountList = new ArrayList<SelectItem>();
            this.addAccountIdDisplayValue = null;
            cardNumberList = new ArrayList<SelectItem>();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddAccountId());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddCardId());
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting AddPartnerNumberListener method");
    }

    public void EditPartnerNumberLIstener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside EditPartnerNumberLIstener method");
        if (valueChangeEvent.getNewValue() != null) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside EditPartnerNumberLIstener method after null check");
            linkedEditAccountList = new ArrayList<SelectItem>();
            editCardNumberList = new ArrayList<SelectItem>();
            editPartnerIdVal = null;
            editPartnerIdVal = valueChangeEvent.getNewValue().toString();
            populateAccountNumber(valueChangeEvent.getNewValue().toString(), "Edit");
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditAccountId());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditCardId());
        } else {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside EditPartnerNumberLIstener method if no partner selected");
            this.editAccountIdDisplayValue = null;
            linkedEditAccountList = new ArrayList<SelectItem>();
            this.editAccountIdDisplayValue = null;
            this.cardId = null;
            this.cardId = "";
            editCardNumberList = new ArrayList<SelectItem>();

            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditAccountId());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditCardId());
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting EditPartnerNumberLIstener method");
    }

    public String deleteAllRecordsAction() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside deleteAllRecordsAction method for account");
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNumber") != null) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside deleteAllRecordsAction method for account after null check");
            int validateDeleteAccountCount = 0;
            displayAccountNumber = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNumber").toString().trim();
            if (partnerInfoList != null && partnerInfoList.size() > 0) {
                for (int pa = 0; pa < partnerInfoList.size(); pa++) {
                    if (partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() > 0) {
                        for (int ac = 0; ac < partnerInfoList.get(pa).getAccountList().size(); ac++) {
                            if (partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString().equals(displayAccountNumber) &&
                                partnerInfoList.get(pa).getAccountList().get(ac).isAccountOverview() == false) {
                                validateDeleteAccountCount = 1;
                            }
                        }
                    }
                }
            }

            if (validateDeleteAccountCount > 0) {
                if (resourceBundle.containsKey("NO_DELETE_VEHICLE")) {
                    String vehicleErrorMsg = resourceBundle.getObject("NO_DELETE_VEHICLE").toString().concat(displayAccountNumber);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, vehicleErrorMsg, "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
            }

            int count = 0;
            if (myAccountList.size() > 0) {
                for (int i = 0; i < myAccountList.size(); i++) {
                    if (myAccountList.get(i).getAccountNumber().trim().equals(displayAccountNumber)) {
                        if (myAccountList.get(i).getVehicleInfoList().size() > 0) {
                            count = 1;
                        } else {
                            count = 0;
                        }
                    }
                }
            }
            if (count > 0) {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside deleteAllRecordsAction to open delete all pop up");
                getBindings().getDeleteAllInfoVehicle().show(new RichPopup.PopupHints());
            } else {
                if (resourceBundle.containsKey("NO_RECORDS_FOUND_DELETE_ALL")) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("NO_RECORDS_FOUND_DELETE_ALL"), "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting deleteAllRecordsAction method for account");
        return null;
    }

    public String deleteAllForAccount() {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside deleteAllForAccount method for account");
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNumber") != null) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside deleteAllForAccount method for account after null check");
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("deleteAllForAccount");
            operationBinding.getParamsMap().put("accountId", AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNumber").toString().trim());
            operationBinding.getParamsMap().put("type", "vehicle");
            operationBinding.getParamsMap().put("countryCd", countryParam);
            if (getBindings().getRegisterNumber().getValue() != null && getBindings().getRegisterNumber().getValue().toString().length() > 0) {
                operationBinding.getParamsMap().put("regDriverValue", registrationNumber);
            } else {
                operationBinding.getParamsMap().put("regDriverValue", null);
            }
            Object result = operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
                getBindings().getDeleteAllInfoVehicle().hide();
                searchResults(true);
            } else {

            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting deleteAllForAccount method for account");
        return null;
    }

    public String deleteAllForAccountCancel() {
        getBindings().getDeleteAllInfoVehicle().hide();
        return null;
    }

    public void setDisplayAccountNumber(String displayAccountNumber) {
        this.displayAccountNumber = displayAccountNumber;
    }

    public String getDisplayAccountNumber() {
        return displayAccountNumber;
    }

    public void setMyAccountList(List<Account> myAccountList) {
        this.myAccountList = myAccountList;
    }

    public List<Account> getMyAccountList() {
        return myAccountList;
    }

    public void setCardNumberList(ArrayList<SelectItem> cardNumberList) {
        this.cardNumberList = cardNumberList;
    }

    public List<SelectItem> getCardNumberList() {
        return cardNumberList;
    }

    public void setEditCardNumberList(ArrayList<SelectItem> editCardNumberList) {
        this.editCardNumberList = editCardNumberList;
    }

    public List<SelectItem> getEditCardNumberList() {
        return editCardNumberList;
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
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " Inside getEditCardIdDisplayValue method");
            editCardIdDisplayValue = cardId;
        }
        return editCardIdDisplayValue;
    }

    public void setCountryParam(String countryParam) {
        this.countryParam = countryParam;
    }

    public String getCountryParam() {
        return countryParam;
    }

    public void setLinkedCardValues(ArrayList<String> linkedCardValues) {
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

    public void setShowErrorMsgFlag(boolean showErrorMsgFlag) {
        this.showErrorMsgFlag = showErrorMsgFlag;
    }

    public boolean isShowErrorMsgFlag() {
        return showErrorMsgFlag;
    }

    public void setWarningMsg(String warningMsg) {
        this.warningMsg = warningMsg;
    }

    public String getWarningMsg() {
        return warningMsg;
    }

    public void setShowErrorMsgEditFlag(boolean showErrorMsgEditFlag) {
        this.showErrorMsgEditFlag = showErrorMsgEditFlag;
    }

    public boolean isShowErrorMsgEditFlag() {
        return showErrorMsgEditFlag;
    }

    public void setValidateAccountCard(ArrayList<String> validateAccountCard) {
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

    public void setLinkedPartnerList(ArrayList<SelectItem> linkedPartnerList) {
        this.linkedPartnerList = linkedPartnerList;
    }

    public List<SelectItem> getLinkedPartnerList() {
        return linkedPartnerList;
    }

    public void setLinkedAddAccountList(ArrayList<SelectItem> linkedAddAccountList) {
        this.linkedAddAccountList = linkedAddAccountList;
    }

    public List<SelectItem> getLinkedAddAccountList() {
        return linkedAddAccountList;
    }

    public void setLinkedEditAccountList(ArrayList<SelectItem> linkedEditAccountList) {
        this.linkedEditAccountList = linkedEditAccountList;
    }

    public List<SelectItem> getLinkedEditAccountList() {
        return linkedEditAccountList;
    }

    public void setAddPartnerNumberDisplayValue(String addPartnerNumberDisplayValue) {
        this.addPartnerNumberDisplayValue = addPartnerNumberDisplayValue;
    }

    public String getAddPartnerNumberDisplayValue() {
        return addPartnerNumberDisplayValue;
    }

    public void setEditPartnerNumberDisplayValue(String editPartnerNumberDisplayValue) {
        this.editPartnerNumberDisplayValue = editPartnerNumberDisplayValue;
    }

    public String getEditPartnerNumberDisplayValue() {
        return editPartnerNumberDisplayValue;
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

    public class Bindings {
        private RichSelectManyChoice linkedAccount;
        private RichPanelGroupLayout searchResults;
        private RichPopup newVehicle;
        private RichInputText registerNumber;
        private RichPopup editVehicle;
        private RichPopup deleteVehicle;
        private RichPopup moreColumnsPopup;
        private RichPopup deleteAllInfoVehicle;
        private RichPanelGroupLayout searchPanelGroupLayout;
        private RichSelectOneChoice addCardId;
        private RichSelectOneChoice addAccountId;
        private RichSelectOneChoice editAccountId;
        private RichSelectOneChoice editCardId;
        private RichOutputText showErrorMsg;
        private RichOutputText showEditErrorMessage;
        private RichSelectOneChoice linkedPartner;
        private RichInputText addInternalName;
        private RichInputText addVehicleNumber;
        private RichInputText editVehicleNumber;
        private RichInputText editInternalName;
        private RichInputText addRegistrationNumber;
        private RichSelectOneChoice addPartnerNumberId;
        private RichSelectOneChoice editPartnerNumberId;
        private RichInputText addReferenceNumber;
        private RichInputText editReferenceNumber;
        private RichInputText editRegistrationNumber;

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
         * @param newVehicle
         */
        public void setNewVehicle(RichPopup newVehicle) {
            this.newVehicle = newVehicle;
        }

        /**
         * @return
         */
        public RichPopup getNewVehicle() {
            return newVehicle;
        }

        /**
         * @param registerNumber
         */
        public void setRegisterNumber(RichInputText registerNumber) {
            this.registerNumber = registerNumber;
        }

        /**
         * @return
         */
        public RichInputText getRegisterNumber() {
            return registerNumber;
        }

        /**
         * @param deleteVehicle
         */
        public void setDeleteVehicle(RichPopup deleteVehicle) {
            this.deleteVehicle = deleteVehicle;
        }

        /**
         * @return
         */
        public RichPopup getDeleteVehicle() {
            return deleteVehicle;
        }

        /**
         * @param editVehicle
         */
        public void setEditVehicle(RichPopup editVehicle) {
            this.editVehicle = editVehicle;
        }

        /**
         * @return
         */
        public RichPopup getEditVehicle() {
            return editVehicle;
        }

        /**
         * @param moreColumnsPopup
         */
        public void setMoreColumnsPopup(RichPopup moreColumnsPopup) {
            this.moreColumnsPopup = moreColumnsPopup;
        }

        /**
         * @return
         */
        public RichPopup getMoreColumnsPopup() {
            return moreColumnsPopup;
        }

        /**
         * @param searchPanelGroupLayout
         */
        public void setSearchPanelGroupLayout(RichPanelGroupLayout searchPanelGroupLayout) {
            this.searchPanelGroupLayout = searchPanelGroupLayout;
        }

        /**
         * @return
         */
        public RichPanelGroupLayout getSearchPanelGroupLayout() {
            return searchPanelGroupLayout;
        }

        public void setDeleteAllInfoVehicle(RichPopup deleteAllInfoVehicle) {
            this.deleteAllInfoVehicle = deleteAllInfoVehicle;
        }

        public RichPopup getDeleteAllInfoVehicle() {
            return deleteAllInfoVehicle;
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

        public void setAddInternalName(RichInputText addInternalName) {
            this.addInternalName = addInternalName;
        }

        public RichInputText getAddInternalName() {
            return addInternalName;
        }

        public void setAddVehicleNumber(RichInputText addVehicleNumber) {
            this.addVehicleNumber = addVehicleNumber;
        }

        public RichInputText getAddVehicleNumber() {

            return addVehicleNumber;
        }

        public void setEditVehicleNumber(RichInputText editVehicleNumber) {
            this.editVehicleNumber = editVehicleNumber;
        }

        public RichInputText getEditVehicleNumber() {
            return editVehicleNumber;
        }

        public void setEditInternalName(RichInputText editInternalName) {
            this.editInternalName = editInternalName;
        }

        public RichInputText getEditInternalName() {
            return editInternalName;
        }

        public void setAddRegistrationNumber(RichInputText addRegistrationNumber) {
            this.addRegistrationNumber = addRegistrationNumber;
        }

        public RichInputText getAddRegistrationNumber() {
            return addRegistrationNumber;
        }

        public void setAddPartnerNumberId(RichSelectOneChoice addPartnerNumberId) {
            this.addPartnerNumberId = addPartnerNumberId;
        }

        public RichSelectOneChoice getAddPartnerNumberId() {
            return addPartnerNumberId;
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

        public void setEditRegistrationNumber(RichInputText editRegistrationNumber) {
            this.editRegistrationNumber = editRegistrationNumber;
        }

        public RichInputText getEditRegistrationNumber() {
            return editRegistrationNumber;
        }
    }
}

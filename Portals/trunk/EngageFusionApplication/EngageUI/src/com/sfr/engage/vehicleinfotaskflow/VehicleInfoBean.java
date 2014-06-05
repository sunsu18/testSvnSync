package com.sfr.engage.vehicleinfotaskflow;


import com.sfr.engage.core.Account;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.core.VehicleInfo;
import com.sfr.engage.model.queries.uvo.PrtAccountVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtDriverInformationVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtTruckInformationVORowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.ADFUtils;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
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
    private HashMap<String, String> val = new HashMap<String, String>();
    private List<VehicleInfo> moreColumnsTable;
    private String registrationNumber;
    private String accountsList;
    private ArrayList<SelectItem> linkedAccountList = null;
    private String addAccountNumberVal = null;
    private String editAccountNumberVal = null;
    private List<String> linkedAccountLOVValues;
    private String displayAccountNumber;
    private List<Account> myAccountList;

    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    private List<PartnerInfo> partnerInfoList;
    private ArrayList<SelectItem> cardNumberList;
    private ArrayList<SelectItem>editCardNumberList;
    private String addAccountIdDisplayValue = null;
    private String addCardIdDisplayValue = null;
    private String editAccountIdDisplayValue = null;
    private String editCardIdDisplayValue = null;
    private String countryParam;
    private ArrayList<String> linkedCardValues;
    private String cardId = null;
    private String warningMsg = null;
    private boolean showErrorMsgFlag = false;
    private boolean showErrorMsgEditFlag = false;
    private ArrayList<String> validateAccountCard;
    private String previousCardId = null;

    private String linkedPartnerLOVValues = null;
    private ArrayList<SelectItem> linkedPartnerList = null;
    private ArrayList<SelectItem> linkedAddAccountList;
    private ArrayList<SelectItem> linkedEditAccountList;
    private String addPartnerNumberDisplayValue;
    private String editPartnerNumberDisplayValue;
    HashMap<String, String> cardNumberMap = new HashMap<String, String>();
    private String addPartnerIdVal = null;
    private String editPartnerIdVal = null;

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
        resourceBundle = new EngageResourceBundle();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        countryParam = null;
        linkedCardValues = new ArrayList<String>();

        linkedPartnerLOVValues    = null;
        linkedPartnerList         = new ArrayList<SelectItem>();

        if(session.getAttribute("Partner_Object_List") != null){
            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
        }

        if(partnerInfoList != null && partnerInfoList.size() >0){
            for(int pa=0 ; pa<partnerInfoList.size(); pa++){
                countryParam = partnerInfoList.get(0).getCountry().toString().trim();
                System.out.println("value of countryParam==============>"+countryParam);
                SelectItem selectItemPartner = new SelectItem();
                selectItemPartner.setLabel(partnerInfoList.get(pa).getPartnerName().toString().trim());
                selectItemPartner.setValue(partnerInfoList.get(pa).getPartnerValue().toString().trim());
                linkedPartnerList.add(selectItemPartner);
                    if(partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() > 0){
                        for(int ac=0 ; ac<partnerInfoList.get(pa).getAccountList().size(); ac++){
                            if(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup() != null && partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size()>0){
                                for(int cg =0 ; cg< partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size(); cg++){
                                    if(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard() != null && partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size()>0){
                                        for(int cc =0 ; cc<partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size(); cc++){
                                            if(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID()!= null && partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID()!= null){
                                                linkedCardValues.add(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString().trim());
                                                cardNumberMap.put(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString().trim(), partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString().trim());
//                                                System.out.println("value of cards in constructor=========>"+linkedCardValues);
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

    public ArrayList<SelectItem> getLinkedAccountList() {
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
    public String searchAction(){
        searchResults(true);
        return null;
    }

    /**
     * This method is reusable for different scenario's in VehicleInfo Page to show searchResults.
     */
    public String searchResults(boolean value) {

        try {
            if (value == true) {
                if(getBindings().getLinkedPartner().getValue() != null){
                    if (getBindings().getLinkedAccount().getValue() != null) {
                        searchResultsExecution();
                    } else {
                        if (resourceBundle.containsKey("VEHICLE_LINKED_ACCOUNT")) {
                            FacesMessage msg =
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                 (String)resourceBundle.getObject("VEHICLE_LINKED_ACCOUNT"),
                                                 "");
                            FacesContext.getCurrentInstance().addMessage(null,
                                                                         msg);
                            return null;
                        }
                    }
                }else{
                    if (resourceBundle.containsKey("LINKED_PARTNER")) {
                        FacesMessage msg =
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                             (String)resourceBundle.getObject("LINKED_PARTNER"),
                                             "");
                        FacesContext.getCurrentInstance().addMessage(null,
                                                                     msg);
                        return null;
                    }
                }
            } else {
                System.out.println("Add/Edit number val");
                if (getBindings().getLinkedPartner().getValue() == null && getBindings().getLinkedAccount().getValue() == null &&
                    addAccountNumberVal != null && addPartnerIdVal != null) {
//                System.out.println("Linked Account Values=" + linkedAccountLOVValues);
//                    System.out.println("Adding only addAccount");
                    if(linkedAccountLOVValues==null) {
                        linkedAccountLOVValues=new ArrayList<String>();
                        linkedAccountList = new ArrayList<SelectItem>();
                        linkedPartnerLOVValues = null;
                    }
                    if(linkedAddAccountList.size()>0){
                        for(int i=0 ; i<linkedAddAccountList.size();i++){
                             linkedAccountList.add(linkedAddAccountList.get(i));
                        }
                    }
                    this.linkedPartnerLOVValues = addPartnerIdVal;
                    linkedAccountLOVValues.add(addAccountNumberVal);
                } else {
                    if (getBindings().getLinkedPartner().getValue() != null && getBindings().getLinkedAccount().getValue() != null) {
                        System.out.println("Comming");
                        String searchValues =getBindings().getLinkedAccount().getValue().toString().trim();
                        String[] search = StringConversion(searchValues);
                        System.out.println("searchResults =" + search.length);
                        if (addAccountNumberVal != null && addPartnerIdVal != null) {
                            if(addPartnerIdVal.equals(getBindings().getLinkedPartner().getValue().toString().trim())){
                                int count = 0;
                                for (int i = 0; i < search.length; i++) {
                                    System.out.println("String =" + search[i]);
                                    if ((addAccountNumberVal.equalsIgnoreCase(search[i].trim()))) {
                                        System.out.println("ADDInside");
                                        count = 1;
                                    }
                                }
                                if (count == 0) {
                                    linkedAccountLOVValues.add(addAccountNumberVal);
                                }
                            }else{
                                    linkedAccountLOVValues = new ArrayList<String>();
                                    linkedAccountList = new ArrayList<SelectItem>();
                                    linkedPartnerLOVValues = null;

                                    if(linkedAddAccountList.size()>0){
                                        for(int i=0 ; i<linkedAddAccountList.size();i++){
                                             linkedAccountList.add(linkedAddAccountList.get(i));
                                        }
                                    }
                                    linkedAccountLOVValues.add(addAccountNumberVal);
                                    this.linkedPartnerLOVValues = addPartnerIdVal;
                            }
                        }
                        if (editAccountNumberVal != null && editPartnerIdVal != null) {
                            if(editPartnerIdVal.equals(getBindings().getLinkedPartner().getValue().toString().trim())){
                                int count = 0;
                                for (int i = 0; i < search.length; i++) {
                                    System.out.println("String =" + search[i]);
                                    if ((editAccountNumberVal.equalsIgnoreCase(search[i].trim()))) {
                                        System.out.println("EditInside");
                                        count = 1;
                                    }
                                }
                                if (count ==0) {
                                    linkedAccountLOVValues.add(editAccountNumberVal);
                                }
                            }else{
                                linkedAccountLOVValues = new ArrayList<String>();
                                linkedAccountList = new ArrayList<SelectItem>();
                                linkedPartnerLOVValues = null;

                                if(linkedEditAccountList.size()>0){
                                    for(int i=0 ; i<linkedEditAccountList.size();i++){
                                         linkedAccountList.add(linkedEditAccountList.get(i));
                                    }
                                }
                                linkedAccountLOVValues.add(editAccountNumberVal);
                                this.linkedPartnerLOVValues = editPartnerIdVal;
                            }
                        }
                    }
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLinkedAccount());
                addAccountNumberVal = null;
                editAccountNumberVal = null;
                searchResultsExecution();
            }
        } catch (JboException ex) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),
                                 "");
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
        ViewObject vo =
            ADFUtils.getViewObject("PrtTruckInformationVO1Iterator");
        if ("trim(ACCOUNT_NUMBER) =: accountNumber AND trim(REGISTRATION_NUMBER) like '%'||:registrationNumber||'%'".equalsIgnoreCase(vo.getWhereClause())) {
            vo.removeNamedWhereClauseParam("accountNumber");
            vo.removeNamedWhereClauseParam("registrationNumber");
            vo.setWhereClause("");
            vo.executeQuery();
        }
        
        if("trim(ACCOUNT_NUMBER) =: accountNumber".equalsIgnoreCase(vo.getWhereClause())){
            vo.removeNamedWhereClauseParam("accountNumber");
            vo.setWhereClause("");
            vo.executeQuery();
        }
        
        System.out.println("searchResultsExecution");
        int count = 0;
        String[] values;
        System.out.println("Selected Valued==" +
                           getBindings().getLinkedAccount().getValue());
        String selectedValues =
            getBindings().getLinkedAccount().getValue().toString().trim();
        String passingValues =
            selectedValues.substring(1, selectedValues.length() - 1);
        System.out.println("PassedValues==" + passingValues);
        if (passingValues.contains(",")) {
            values = passingValues.split(",");
            System.out.println("Count==" + values.length);
            count = values.length;

        } else {
            count = 1;
            values = new String[1];
            values[0] = passingValues;
            System.out.println("Length==1");
        }
        myAccount = new ArrayList<Account>();
        for (int i = 0; i < count; i++) {
            Account acc = new Account();
            acc.setAccountNumber(values[i]);
            List<VehicleInfo> myVehicleList = new ArrayList<VehicleInfo>();
            vo.setNamedWhereClauseParam("countryCd", countryParam);
//            vo.setWhereClause("trim(ACCOUNT_NUMBER) =: accountNumber AND (PrtTruckInformationEO.REGISTRATION_NUMBER IS NULL OR PrtTruckInformationEO.REGISTRATION_NUMBER like concat(:registrationNumber,'%'))");
//            System.out.println("values of i" + values[i]);
//            vo.defineNamedWhereClauseParam("accountNumber", values[i].trim(),
//                                           null);
            if (getBindings().getRegisterNumber().getValue() != null && getBindings().getRegisterNumber().getValue().toString().length()>0) {
                vo.setWhereClause("trim(ACCOUNT_NUMBER) =: accountNumber AND trim(REGISTRATION_NUMBER) like '%'||:registrationNumber||'%'");
                vo.defineNamedWhereClauseParam("accountNumber", values[i].trim(),
                                               null);
                vo.defineNamedWhereClauseParam("registrationNumber",
                                               getBindings().getRegisterNumber().getValue().toString().trim(),
                                               null);
            } else {
                vo.setWhereClause("trim(ACCOUNT_NUMBER) =: accountNumber");
                vo.defineNamedWhereClauseParam("accountNumber", values[i].trim(),
                                               null);
//                vo.defineNamedWhereClauseParam("registrationNumber", null,
//                                               null);
            }
            System.out.println("Query ==" + vo.getQuery());
            vo.executeQuery();
            if (vo.getEstimatedRowCount() != 0) {
                System.out.println("RowCount" + values[i]);
                for (int j = 0; j < vo.getEstimatedRowCount(); j++) {
                    while (vo.hasNext()) {
                        PrtTruckInformationVORowImpl currRow =
                            (PrtTruckInformationVORowImpl)vo.next();
                        if (currRow != null) {
                                System.out.println("current card number=====>"+currRow.getCardNumber());
                                if(currRow.getCardNumber()== null || linkedCardValues.contains(currRow.getCardNumber().toString())){
                                    System.out.println("is it coming inside to get vehicle details details=================>");
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
                if(getBindings().getRegisterNumber().getValue() != null && getBindings().getRegisterNumber().getValue().toString().length()>0){
                System.out.println("Inside if block of new account list++++++++++++++++++++++++++++++");
                    for (int k = 0; k < myAccount.size(); k++) {
                        if (myAccount.get(k).getVehicleInfoList().size() > 0) {
                            System.out.println("Inside if block of driver info list");
                         myAccountList.add(myAccount.get(k));
                        }
                    }
                }else{
                    System.out.println("Inside else block of new account list++++++++++++++++++++++++++");
                    for(int m = 0; m < myAccount.size(); m++) {
                        myAccountList.add(myAccount.get(m));
                    }
                }
            }

            if (myAccountList.size() > 0) {
                System.out.println("Inside if block of the show condition of panel +++++++++++++++++++++");
                searchResultsShow = true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
            }else{
                searchResultsShow = false;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
                System.out.println("Inside else block of the show condition of panel +++++++++++++++++++++");
                if (resourceBundle.containsKey("NO_RECORDS_FOUND_VEHICLE")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                         (String)resourceBundle.getObject("NO_RECORDS_FOUND_VEHICLE"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
//                searchResultsShow = false;
//                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
            }
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

        if (getBindings().getAddPartnerNumberId().getValue() !=null && getBindings().getAddAccountId().getValue() != null 
            && getBindings().getAddVehicleNumber().getValue() != null && getBindings().getAddInternalName().getValue() != null 
            && getBindings().getAddVehicleNumber().getValue().toString().trim() != null && getBindings().getAddInternalName().getValue().toString().trim() != null  ) {
        
       
         
          
            System.out.println("is it coming inside the newDriverSave method++++++++++===============>");
            ViewObject driverVo = ADFUtils.getViewObject("PrtDriverInformationVO3Iterator");
            driverVo.setNamedWhereClauseParam("countryCd", countryParam);
            driverVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
            if(addAccountNumberVal == null){
                if(getBindings().getAddAccountId().getValue() != null)
                addAccountNumberVal = getBindings().getAddAccountId().getValue().toString().trim();
            }
            System.out.println("value of add account id=============>"+addAccountNumberVal);
            driverVo.defineNamedWhereClauseParam("accountId",addAccountNumberVal, null);
            if(getBindings().getAddCardId().getValue() != null){
             driverVo.defineNamedWhereClauseParam("cardNo",getBindings().getAddCardId().getValue().toString().trim(), null);
            }else{
                driverVo.defineNamedWhereClauseParam("cardNo"," ", null);
            }
            driverVo.executeQuery();
            if(driverVo.getEstimatedRowCount() > 0){
                System.out.println("is it coming inside the newDriverSave method===============++++++++++++++>");
                 while (driverVo.hasNext()) {
                        PrtDriverInformationVORowImpl currRow =(PrtDriverInformationVORowImpl)driverVo.next();
                         if (currRow != null) {
                            if (resourceBundle.containsKey("DRIVER_CARD_EXIST")) {
                                warningMsg = resourceBundle.getObject("DRIVER_CARD_EXIST").toString().concat(" ").concat(currRow.getDriverName());
                                showErrorMsgFlag = true;
                                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                                //FacesMessage msg =new FacesMessage(FacesMessage.SEVERITY_INFO,warningMsg,"");
                                //FacesContext.getCurrentInstance().addMessage(null, msg);
                                return null;
                            }
                         }
                    }
            }

            ViewObject truckVo = ADFUtils.getViewObject("PrtTruckInformationVO3Iterator");
            truckVo.setNamedWhereClauseParam("countryCd", countryParam);
            truckVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
            truckVo.defineNamedWhereClauseParam("accountId",addAccountNumberVal, null);
            if(getBindings().getAddCardId().getValue() != null){
            truckVo.defineNamedWhereClauseParam("cardNo",getBindings().getAddCardId().getValue().toString().trim(), null);
            }else{
                truckVo.defineNamedWhereClauseParam("cardNo","", null);
            }
            truckVo.executeQuery();
            if(truckVo.getEstimatedRowCount() > 0){
                while (truckVo.hasNext()) {
                       PrtTruckInformationVORowImpl currRow =(PrtTruckInformationVORowImpl)truckVo.next();
                        if (currRow != null) {
                            if (resourceBundle.containsKey("TRUCK_CARD_EXIST")) {
                                warningMsg = resourceBundle.getObject("TRUCK_CARD_EXIST").toString().concat(" ").concat(currRow.getVehicleNumber());
                                showErrorMsgFlag = true;
                                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                                //FacesMessage msg  = new FacesMessage(FacesMessage.SEVERITY_INFO, warningMsg,"");
                                //FacesContext.getCurrentInstance().addMessage(null, msg);
                                return null;
                            }
                        }
                }
            }


            getBindings().getNewVehicle().hide();
//            System.out.println("save =" + addAccountNumberVal);
            BindingContainer bindings       = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding vehicleInfoItr = (DCIteratorBinding)bindings.get("PrtTruckInformationVO2Iterator");
            Row vehicleInfoRow = vehicleInfoItr.getCurrentRow();
//            System.out.println("Before new vehicle save current row is not null+++++++++++");
                if(vehicleInfoRow != null){
                System.out.println("Inside new driver save current row is not null+++++++++++");
                System.out.println("value of add account id=====>"+getBindings().getAddAccountId().getValue().toString());
                if(getBindings().getAddAccountId().getValue() != null)
                vehicleInfoRow.setAttribute("AccountNumber", getBindings().getAddAccountId().getValue().toString().trim());
                if(getBindings().getAddCardId().getValue() != null){
                System.out.println("value of add card    id=====>"+getBindings().getAddCardId().getValue().toString());
                vehicleInfoRow.setAttribute("CardNumber",getBindings().getAddCardId().getValue().toString().trim());
                }else{
                    vehicleInfoRow.setAttribute("CardNumber","");
                }
                vehicleInfoRow.setAttribute("CountryCode", countryParam);
                vehicleInfoRow.setAttribute("VehicleNumber", getBindings().getAddVehicleNumber().getValue().toString().trim());
                vehicleInfoRow.setAttribute("InternalName", getBindings().getAddInternalName().getValue().toString().trim());
                OperationBinding newDriverOpn = bindings.getOperationBinding("Commit");
                newDriverOpn.execute();
                if(newDriverOpn.getErrors().isEmpty()){
                    System.out.println("No failure in the function");
                }
            }
        }else{
            if (resourceBundle.containsKey("ENGAGE_SELECT_TRANSACTION_MANDATORY")) {
                warningMsg = resourceBundle.getObject("ENGAGE_SELECT_TRANSACTION_MANDATORY").toString();
                showErrorMsgFlag = true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                //FacesMessage msg =new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("MANDATORY_CHECK"), "");
                //FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
        }
        searchResults(false);
        if (resourceBundle.containsKey("VEHICLE_ADD")) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("VEHICLE_ADD"),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        return null;
    }

    /**
     * @return
     */
    public String newVehicleCancel() {
        ResetUtils.reset(getBindings().getNewVehicle());
        getBindings().getNewVehicle().hide();
        return null;
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
     * @return
     */
    public String newVehicleAddAction() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding createOpn = bindings.getOperationBinding("CreateInsert");
        createOpn.execute();
        showErrorMsgFlag = false;
        linkedAddAccountList  = new ArrayList<SelectItem>();
        addPartnerNumberDisplayValue = null;
        addAccountIdDisplayValue = null;
        addCardIdDisplayValue    =null;
        cardNumberList = new ArrayList<SelectItem>();
        if(getBindings().getLinkedPartner().getValue()!= null){
            if(getBindings().getLinkedAccount().getValue() != null && linkedAccountLOVValues.size() >0){
                if(linkedAccountLOVValues.size()==1){
                    this.addAccountIdDisplayValue = populateStringValues(getBindings().getLinkedAccount().getValue().toString().trim());
                }
                this.addPartnerNumberDisplayValue = getBindings().getLinkedPartner().getValue().toString().trim();
                this.addCardIdDisplayValue = null;
                populateAccountNumber(getBindings().getLinkedPartner().getValue().toString(),"Add");
                populateCardNumberList(populateStringValues(getBindings().getLinkedAccount().getValue().toString()),"newVehicleAdd",getBindings().getLinkedPartner().getValue().toString());
            }
        }else{
                this.addPartnerNumberDisplayValue = null;
                this.addAccountIdDisplayValue = null;
                this.addCardIdDisplayValue    =null;
        }

        if(getBindings().getRegisterNumber().getValue() != null){
            getBindings().getAddRegistrationNumber().setSubmittedValue(null);
            getBindings().getAddRegistrationNumber().setSubmittedValue(getBindings().getRegisterNumber().getValue().toString().trim());
        }
        getBindings().getNewVehicle().show(new RichPopup.PopupHints());
        return null;
    }

    /**
     * This Method will save edited vehicle information in DB.
     * @return
     */
    public String editVehicleSave() {

        if (getBindings().getEditPartnerNumberId().getValue()!= null && getBindings().getEditAccountId().getValue() != null 
            && getBindings().getEditInternalName().getValue() != null && getBindings().getEditVehicleNumber().getValue()!= null && getBindings().getEditInternalName().getValue().toString().trim() != null && getBindings().getEditVehicleNumber().getValue().toString().trim()!= null   ) {

                ViewObject driverVo = ADFUtils.getViewObject("PrtDriverInformationVO3Iterator");
                driverVo.setNamedWhereClauseParam("countryCd", countryParam);
                driverVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
                System.out.println("value of edit account number===========>"+getBindings().getEditAccountId().getValue());
                System.out.println("value of edit account number===========>"+editAccountNumberVal);
                driverVo.defineNamedWhereClauseParam("accountId",editAccountNumberVal, null);
                if(getBindings().getEditCardId().getValue() != null){
                System.out.println("value of edit card id============>"+getBindings().getEditCardId().getValue());
                driverVo.defineNamedWhereClauseParam("cardNo",getBindings().getEditCardId().getValue().toString().trim(), null);
                }else{
                    System.out.println("value of edit card id in else block============?");
                    driverVo.defineNamedWhereClauseParam("cardNo","", null);
                }
                driverVo.executeQuery();
                if(driverVo.getEstimatedRowCount() > 0){
                    System.out.println("Is it coming inside edit vehicle save to check====================>");
                    while (driverVo.hasNext()) {
                    PrtDriverInformationVORowImpl currRow =(PrtDriverInformationVORowImpl)driverVo.next();
                         if (currRow != null) {
                            if (resourceBundle.containsKey("DRIVER_CARD_EXIST")) {
                                 warningMsg = resourceBundle.getObject("DRIVER_CARD_EXIST").toString().concat(" ").concat(currRow.getDriverName());
                                 showErrorMsgEditFlag = true;
                                 AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
                                //FacesMessage msg =new FacesMessage(FacesMessage.SEVERITY_INFO, warningMsg ,"");
                                //FacesContext.getCurrentInstance().addMessage(null, msg);
                                return null;
                            }
                         }
                    }
                }

                if(previousCardId != null && getBindings().getEditCardId().getValue() != null){
                    if(!previousCardId.equals(getBindings().getEditCardId().getValue().toString().trim())){
                        System.out.println("value of previuous card id===========>"+previousCardId);

                        ViewObject truckVo = ADFUtils.getViewObject("PrtTruckInformationVO3Iterator");
                        truckVo.setNamedWhereClauseParam("countryCd", countryParam);
                        truckVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
                        truckVo.defineNamedWhereClauseParam("accountId",editAccountNumberVal, null);
                        if(getBindings().getEditCardId().getValue() != null){
                        System.out.println("For checking truck======>"+getBindings().getEditCardId().getValue());
                        truckVo.defineNamedWhereClauseParam("cardNo",getBindings().getEditCardId().getValue().toString().trim(), null);
                        }else{
                            truckVo.defineNamedWhereClauseParam("cardNo","", null);
                        }
                        truckVo.executeQuery();
                        if(truckVo.getEstimatedRowCount() > 0){
                            System.out.println("For checking truck row count======>");
                            while (truckVo.hasNext()) {
                                 PrtTruckInformationVORowImpl currRow =(PrtTruckInformationVORowImpl)truckVo.next();
                                  if (currRow != null) {
                                      System.out.println("For checking truck row count======>++++++++++++");
                                        if (resourceBundle.containsKey("TRUCK_CARD_EXIST")) {
                                            System.out.println("1111111111111111111111111111111111111111111111111111111111");
                                             warningMsg = resourceBundle.getObject("TRUCK_CARD_EXIST").toString().concat(" ").concat(currRow.getVehicleNumber());
                                             showErrorMsgEditFlag = true;
                                             AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
                                            //FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, warningMsg,"");
                                            //FacesContext.getCurrentInstance().addMessage(null, msg);
                                            return null;
                                        }
                                  }
                            }
                        }
                    }
                }else{
                    ViewObject truckVo = ADFUtils.getViewObject("PrtTruckInformationVO3Iterator");
                    truckVo.setNamedWhereClauseParam("countryCd", countryParam);
                    truckVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
                    truckVo.defineNamedWhereClauseParam("accountId",editAccountNumberVal, null);
                    if(getBindings().getEditCardId().getValue() != null){
                    System.out.println("For checking truck======>"+getBindings().getEditCardId().getValue());
                    truckVo.defineNamedWhereClauseParam("cardNo",getBindings().getEditCardId().getValue().toString().trim(), null);
                    }else{
                        truckVo.defineNamedWhereClauseParam("cardNo","", null);
                    }
                    truckVo.executeQuery();
                    if(truckVo.getEstimatedRowCount() > 0){
                        System.out.println("For checking truck row count======>");
                        while (truckVo.hasNext()) {
                             PrtTruckInformationVORowImpl currRow =(PrtTruckInformationVORowImpl)truckVo.next();
                              if (currRow != null) {
                                  System.out.println("For checking truck row count======>++++++++++++");
                                    if (resourceBundle.containsKey("TRUCK_CARD_EXIST")) {
                                        System.out.println("1111111111111111111111111111111111111111111111111111111111");
                                         warningMsg = resourceBundle.getObject("TRUCK_CARD_EXIST").toString().concat(" ").concat(currRow.getVehicleNumber());
                                         showErrorMsgEditFlag = true;
                                         AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
                                        //FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, warningMsg,"");
                                        //FacesContext.getCurrentInstance().addMessage(null, msg);
                                        return null;
                                    }
                              }
                        }
                    }
                }

            getBindings().getEditVehicle().hide();
            System.out.println("save =" + editAccountNumberVal);
            BindingContainer bindings       = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding vehicleInfoItr = (DCIteratorBinding)bindings.get("PrtTruckInformationVO2Iterator");
            Row vehicleInfoRow = vehicleInfoItr.getCurrentRow();
            System.out.println("Before new vehicle save current row is not null+++++++++++");
                if(vehicleInfoRow != null){
                System.out.println("Inside new driver save current row is not null+++++++++++");
                System.out.println("value of add account id=====>"+getBindings().getEditAccountId().getValue().toString());

                vehicleInfoRow.setAttribute("AccountNumber", getBindings().getEditAccountId().getValue().toString());
                if(getBindings().getEditCardId().getValue() != null){
                System.out.println("value of add card    id=====>"+getBindings().getEditCardId().getValue().toString());
                vehicleInfoRow.setAttribute("CardNumber",getBindings().getEditCardId().getValue().toString().trim());
                }else{
                    vehicleInfoRow.setAttribute("CardNumber","");
                }
                vehicleInfoRow.setAttribute("CountryCode", countryParam);
                vehicleInfoRow.setAttribute("VehicleNumber",getBindings().getEditVehicleNumber().getValue().toString().trim());
                vehicleInfoRow.setAttribute("InternalName",getBindings().getEditInternalName().getValue().toString().trim());
                OperationBinding newDriverOpn = bindings.getOperationBinding("Commit");
                newDriverOpn.execute();
                if(newDriverOpn.getErrors().isEmpty()){
                    System.out.println("No failure in the function");
                }
            }

        }else{
            if (resourceBundle.containsKey("ENGAGE_SELECT_TRANSACTION_MANDATORY")) {
                warningMsg = resourceBundle.getObject("ENGAGE_SELECT_TRANSACTION_MANDATORY").toString();
                showErrorMsgEditFlag = true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
                //FacesMessage msg =new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("MANDATORY_CHECK"), "");
                //FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
        }
        searchResults(false);
        System.out.println("After Edit Save");
        if (resourceBundle.containsKey("VEHICLE_EDIT")) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("VEHICLE_EDIT"),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        return null;
    }

    /**
     * @return
     */
    public String editVehicleCancel() {
        ResetUtils.reset(getBindings().getEditVehicle());
        getBindings().getEditVehicle().hide();
        return null;
    }

    /**
     * This Method will execute VO and fetches selected row from DB and perform Edit operation.
     * @return
     */
    public String tableEditAction() {
        try {
            showErrorMsgEditFlag = false;
            editAccountIdDisplayValue = null;
            editCardIdDisplayValue = null;
            editCardNumberList   = new ArrayList<SelectItem>();
            linkedEditAccountList = new ArrayList<SelectItem>();
            String primaryKey    =(String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("primarykey");
            String accountNumber = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountnumber");
            cardId         = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardid");
            previousCardId = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardid");
            System.out.println("PrimaryKey =" + primaryKey);
            if (primaryKey != null && accountNumber != null && getBindings().getLinkedPartner().getValue() != null) {
                editPartnerNumberDisplayValue = getBindings().getLinkedPartner().getValue().toString().trim();
                editAccountIdDisplayValue = accountNumber;
                editAccountNumberVal = editAccountIdDisplayValue;
                if(editAccountIdDisplayValue != null){
                    populateAccountNumber(getBindings().getLinkedPartner().getValue().toString().trim(),"Edit");
                    populateCardNumberList(editAccountIdDisplayValue, "editButton",getBindings().getLinkedPartner().getValue().toString().trim());
                }

                ViewObject vo =ADFUtils.getViewObject("PrtTruckInformationVO2Iterator");
                vo.setNamedWhereClauseParam("countryCd", countryParam);
                vo.setWhereClause("PRT_TRUCK_INFORMATION_PK =: prtTruckInformationPK");
                vo.defineNamedWhereClauseParam("prtTruckInformationPK",primaryKey, null);
                vo.executeQuery();
                getBindings().getEditVehicle().show(new RichPopup.PopupHints());
            }
        } catch (JboException ex) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_INFO, ex.getMessage(),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }

    /**
     * This Method will show confirmation popup for Delete operation.
     * @return
     */
    public String vehicleDeleteAction() {
        if (val.size() != 0) {
        ArrayList<String> validateCard = new ArrayList<String>();
            if(partnerInfoList != null && partnerInfoList.size()>0){
                for(int pa=0 ; pa<partnerInfoList.size();pa++){
                    if( partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() > 0){
                        for(int ac=0 ; ac<partnerInfoList.get(pa).getAccountList().size(); ac++){
                            if(validateAccountCard.contains(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString()) && partnerInfoList.get(pa).getAccountList().get(ac).isAccountOverview() == false){
                                validateCard.add(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString());
                             }
                        }
                    }
                }
            }
            if(validateCard != null && validateCard.size() >0){
                System.out.println("value of deleted card to check======>"+validateCard);
                System.out.println("is it coming inside this method to check for delete");
                if (resourceBundle.containsKey("NO_DELETE_VEHICLE")) {
                    String cardList = validateCard.toString();
                    String validateCardValues = cardList.substring(1, cardList.length() - 1).replace(" ", "");
                    String vehicleErrorMsg = resourceBundle.getObject("NO_DELETE_VEHICLE").toString().concat(validateCardValues);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,vehicleErrorMsg,"");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
            }
            getBindings().getDeleteVehicle().show(new RichPopup.PopupHints());
        } else {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("VEHICLE_DELETE_FAILURE_1"),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        return null;
    }

    /**
     * This Method will delete selected vehicle rows from DB.
     * @return
     */
    public String deleteVehicleSave() {
        try {
            Iterator iter = val.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String)iter.next();
                String vals = val.get(key);
                System.out.println("key,val: " + key + "," + vals);
                ViewObject vo =
                    ADFUtils.getViewObject("PrtTruckInformationVO2Iterator");
                vo.setNamedWhereClauseParam("countryCd", countryParam);
                vo.setWhereClause("PRT_TRUCK_INFORMATION_PK =: prtTruckInformationPK");
                vo.defineNamedWhereClauseParam("prtTruckInformationPK", vals,
                                               null);
                vo.executeQuery();
                if (vo.getEstimatedRowCount() != 0) {
                    System.out.println("RowCount" + vo.getEstimatedRowCount());
                    while (vo.hasNext()) {
                        Row r = vo.next();
                        vo.setCurrentRow(r);
                        vo.removeCurrentRow();
                    }
                }
            }
            BindingContainer bindings =
                BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
                System.out.println("Success");
                getBindings().getDeleteVehicle().hide();
                val = new HashMap<String, String>();
                searchResults(false);
                if (resourceBundle.containsKey("VEHICLE_DELETE_SUCCESS")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                         (String)resourceBundle.getObject("VEHICLE_DELETE_SUCCESS"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
            } else {
                if (resourceBundle.containsKey("VEHICLE_DELETE_FAILURE")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("VEHICLE_DELETE_FAILURE"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
                System.out.println("Error while commiting");
            }
        } catch (JboException ex) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
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

        if (valueChangeEvent.getNewValue().equals(true)) {
            //validateAccountCard = null;
            validateAccountCard = new ArrayList<String>();
            validateAccountCard.add(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNo").toString());
            System.out.println("Value ==" +AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));
            val.put((String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"),
                    (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));
        } else {
            validateAccountCard = new ArrayList<String>();
            validateAccountCard.remove(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNo").toString());
            if (val.containsKey(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"))) {
                val.remove(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));
            }
        }
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

        ViewObject vo =
            ADFUtils.getViewObject("PrtTruckInformationVO1Iterator");
        
            if ("trim(ACCOUNT_NUMBER) =: accountNumber AND trim(REGISTRATION_NUMBER) like '%'||:registrationNumber||'%'".equalsIgnoreCase(vo.getWhereClause())) {
                vo.removeNamedWhereClauseParam("accountNumber");
                vo.removeNamedWhereClauseParam("registrationNumber");
                vo.setWhereClause("");
                vo.executeQuery();
            }
            
            if("trim(ACCOUNT_NUMBER) =: accountNumber".equalsIgnoreCase(vo.getWhereClause())){
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
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null && getBindings().getAddPartnerNumberId().getValue() != null) {
           /* BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding itr =(DCIteratorBinding)bindings.get("PrtAccountVO1Iterator");
            Row row =itr.getRowAtRangeIndex(((Integer)valueChangeEvent.getNewValue()));
            if (row != null) {
                addAccountNumberVal = (String)row.getAttribute("AccountId");
            }*/
           cardNumberList  = new ArrayList<SelectItem>();
           populateCardNumberList(valueChangeEvent.getNewValue().toString() , "Add",getBindings().getAddPartnerNumberId().getValue().toString());
           AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddCardId());
        }
    }

    public void populateAccountNumber(String partnerId , String type){
        if(partnerId != null){
            if(partnerInfoList != null && partnerInfoList.size() >0 ){
                for(int pa=0 ; pa<partnerInfoList.size(); pa++){
                    if(partnerInfoList.get(pa).getPartnerValue().equals(partnerId) && partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() > 0){
                        for(int ac=0 ; ac<partnerInfoList.get(pa).getAccountList().size(); ac++){
                            if(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber() != null){
                                SelectItem selectItem = new SelectItem();
                                selectItem.setLabel(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString());
                                selectItem.setValue(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString());
                                if(type.equals("Add")){
                                    linkedAddAccountList.add(selectItem);
                                }else{
                                    linkedEditAccountList.add(selectItem);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void partnerNumberValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null) {
            this.searchResultsShow = false;
            this.registrationNumber = null;
            linkedAccountList      = new ArrayList<SelectItem>();
            linkedAccountLOVValues = new ArrayList<String>();
            if(partnerInfoList != null && partnerInfoList.size() > 0){
                for(int pa=0 ; pa<partnerInfoList.size() ; pa++){
                    if(partnerInfoList.get(pa).getPartnerValue() != null && partnerInfoList.get(pa).getPartnerValue().toString().equals(valueChangeEvent.getNewValue().toString())
                       && partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() >0){
                        for(int ac=0 ; ac<partnerInfoList.get(pa).getAccountList().size(); ac++){
                            if(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber() != null){
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
        }else{
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
    }

    public void populateCardNumberList(String accountNo , String type , String partnerNumber){

        if(accountNo != null){
            if(partnerInfoList != null && partnerInfoList.size()>0){
                for( int pa=0 ; pa<partnerInfoList.size() ; pa++){
                    if(partnerInfoList.get(pa).getPartnerValue().equals(partnerNumber) && partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() > 0){
                        for(int ac=0 ; ac<partnerInfoList.get(pa).getAccountList().size(); ac++){
                            if(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber() != null && partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().equals(accountNo)){
                                if(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup() != null && partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size()>0){
                                    for(int cg =0 ; cg< partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size(); cg++){
                                        if(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard() != null && partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size()>0){
                                        for(int cc =0 ; cc<partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size(); cc++){
                                                if(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID()!= null && partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID()!= null){
                                                    SelectItem selectItem = new SelectItem();
                                                    selectItem.setLabel(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
                                                    selectItem.setValue(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                                    if(type.equals("Add")){
                                                    addAccountNumberVal = accountNo;
                                                      cardNumberList.add(selectItem);
//                                                      System.out.println("addAccountNumber =" + addAccountNumberVal);
                                                    }else if(type.equals("Edit")){
                                                        editAccountNumberVal = accountNo;
                                                        editCardNumberList.add(selectItem);
                                                    }else if(type.equals("newVehicleAdd")){
                                                            cardNumberList.add(selectItem);
                                                    }
                                                    else{
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
            }
        }
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
        if (valueChangeEvent.getNewValue() != null && getBindings().getEditPartnerNumberId().getValue() != null) {
            /*BindingContainer bindings =BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding itr =(DCIteratorBinding)bindings.get("PrtAccountVO1Iterator");
            Row row =itr.getRowAtRangeIndex(((Integer)valueChangeEvent.getNewValue()));
            if (row != null) {
                editAccountNumberVal = (String)row.getAttribute("AccountId");
            }*/
            editCardNumberList = new ArrayList<SelectItem>();
            cardId = null;
            populateCardNumberList(valueChangeEvent.getNewValue().toString() ,"Edit",getBindings().getEditPartnerNumberId().getValue().toString());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditCardId());
            System.out.println("editAccountNumber =" + editAccountNumberVal);
        }

    }

    public void editCardNumberChangeListener(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue() != null){
           cardId = valueChangeEvent.getNewValue().toString();
        }else{
            this.cardId = null;
            this.cardId = "";
        }
        this.showErrorMsgEditFlag = false;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditCardId());
    }

    public void addCardNumberChangeListener(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue() != null){

        this.showErrorMsgEditFlag = false;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
        }
    }



    public void AddPartnerNumberListener(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue() != null){
            linkedAddAccountList  = new ArrayList<SelectItem>();
            cardNumberList    = new ArrayList<SelectItem>();
            addPartnerIdVal = null;
            addPartnerIdVal = valueChangeEvent.getNewValue().toString();
            populateAccountNumber(valueChangeEvent.getNewValue().toString(),"Add");
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddAccountId());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddCardId());
        }else{
            this.addAccountIdDisplayValue = null;
            linkedAddAccountList  = new ArrayList<SelectItem>();
            this.addAccountIdDisplayValue = null;
            cardNumberList    = new ArrayList<SelectItem>();
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddAccountId());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddCardId());
        }
    }

    public void EditPartnerNumberLIstener(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue() != null){
            linkedEditAccountList  = new ArrayList<SelectItem>();
            editCardNumberList    = new ArrayList<SelectItem>();
            editPartnerIdVal = null;
            editPartnerIdVal = valueChangeEvent.getNewValue().toString();
            populateAccountNumber(valueChangeEvent.getNewValue().toString(),"Edit");
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditAccountId());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditCardId());
        }else{
            this.editAccountIdDisplayValue = null;
            linkedEditAccountList  = new ArrayList<SelectItem>();
            this.editAccountIdDisplayValue = null;
            this.cardId = null;
            this.cardId = "";
            editCardNumberList    = new ArrayList<SelectItem>();
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditAccountId());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditCardId());
        }
    }

    public String deleteAllRecordsAction() {
        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNumber")!=null)
        {
            int validateDeleteAccountCount = 0;
            displayAccountNumber = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNumber").toString().trim();
            if(partnerInfoList != null && partnerInfoList.size()>0){
                    for(int pa=0 ; pa<partnerInfoList.size();pa++){
                    if( partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() > 0){
                        for(int ac=0 ; ac<partnerInfoList.get(pa).getAccountList().size(); ac++){
                            if(partnerInfoList.get(pa).getAccountList().get(ac).getAccountNumber().toString().equals(displayAccountNumber) && partnerInfoList.get(pa).getAccountList().get(ac).isAccountOverview() == false){
                                 validateDeleteAccountCount = 1;
                             }
                        }
                    }
                }
            }

            if(validateDeleteAccountCount > 0){
                if (resourceBundle.containsKey("NO_DELETE_VEHICLE")) {
                    String vehicleErrorMsg = resourceBundle.getObject("NO_DELETE_VEHICLE").toString().concat(displayAccountNumber);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, vehicleErrorMsg ,"");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
            }

            int count = 0;
            if(myAccountList.size()>0){
                for(int i=0 ; i<myAccountList.size();i++){
                    if(myAccountList.get(i).getAccountNumber().trim().equals(displayAccountNumber)){
                        if(myAccountList.get(i).getVehicleInfoList().size()>0){
                            count = 1;
                        }else{
                            count = 0;
                        }
                    }
                }
            }
            if(count >0){
                getBindings().getDeleteAllInfoVehicle().show(new RichPopup.PopupHints());
            }else{
                if (resourceBundle.containsKey("NO_RECORDS_FOUND_DELETE_ALL")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("NO_RECORDS_FOUND_DELETE_ALL"),"");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
            }
        }
        return null;
    }

    public String deleteAllForAccount() {
        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNumber")!=null)
        {
            BindingContainer bindings =  BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("deleteAllForAccount");
            operationBinding.getParamsMap().put("accountId", AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNumber").toString().trim());
            operationBinding.getParamsMap().put("type", "vehicle");
            operationBinding.getParamsMap().put("countryCd", countryParam);
            if (getBindings().getRegisterNumber().getValue()!=null && getBindings().getRegisterNumber().getValue().toString().length()>0) {
            System.out.println("Inside vehicle bean this block===================");
                System.out.println("value of vehicle name in this block==================="+getBindings().getRegisterNumber().getValue().toString());
                operationBinding.getParamsMap().put("regDriverValue", registrationNumber);
            }else{
                System.out.println("Inside vehicle bean else block===================");
                operationBinding.getParamsMap().put("regDriverValue", null);
            }
            Object result = operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
                getBindings().getDeleteAllInfoVehicle().hide();
                searchResults(true);
            }else{

            }
        }
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

    public ArrayList<SelectItem> getCardNumberList() {
        return cardNumberList;
    }

    public void setEditCardNumberList(ArrayList<SelectItem> editCardNumberList) {
        this.editCardNumberList = editCardNumberList;
    }

    public ArrayList<SelectItem> getEditCardNumberList() {
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
        if(cardId != null){
            System.out.println("It is coming display value always=========>"+cardId);
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

    public ArrayList<String> getLinkedCardValues() {
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

    public ArrayList<String> getValidateAccountCard() {
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

    public ArrayList<SelectItem> getLinkedPartnerList() {
        return linkedPartnerList;
    }

    public void setLinkedAddAccountList(ArrayList<SelectItem> linkedAddAccountList) {
        this.linkedAddAccountList = linkedAddAccountList;
    }

    public ArrayList<SelectItem> getLinkedAddAccountList() {
        return linkedAddAccountList;
    }

    public void setLinkedEditAccountList(ArrayList<SelectItem> linkedEditAccountList) {
        this.linkedEditAccountList = linkedEditAccountList;
    }

    public ArrayList<SelectItem> getLinkedEditAccountList() {
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
    }
}

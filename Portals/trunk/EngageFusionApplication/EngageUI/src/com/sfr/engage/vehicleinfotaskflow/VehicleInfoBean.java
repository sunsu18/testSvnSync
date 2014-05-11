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
    private PartnerInfo partnerInfo;
    private ArrayList<SelectItem> cardNumberList;
    private ArrayList<SelectItem>editCardNumberList;
    private String addAccountIdDisplayValue = null;
    private String addCardIdDisplayValue = null;
    private String editAccountIdDisplayValue = null;
    private String editCardIdDisplayValue = null;
    private String countryParam;
    private ArrayList<String> linkedCardValues;


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
        linkedAccountLOVValues = new ArrayList<String>();
        
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        linkedAccountList =  new ArrayList<SelectItem>();
        countryParam = null;
        linkedCardValues = new ArrayList<String>();
        
        if(session.getAttribute("Partner_Object_List") != null){
                    partnerInfo = (PartnerInfo)session.getAttribute("Partner_Object_List");
                    countryParam = partnerInfo.getCountry().toString();
                    System.out.println("value of countryParam==============>"+countryParam);
        }
                
        if(partnerInfo != null){
            if( partnerInfo.getAccountList() != null && partnerInfo.getAccountList().size() > 0){
                for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
                    SelectItem selectItem = new SelectItem();
                    selectItem.setLabel(partnerInfo.getAccountList().get(i).getAccountNumber().toString());
                    selectItem.setValue(partnerInfo.getAccountList().get(i).getAccountNumber().toString());
                    linkedAccountLOVValues.add(partnerInfo.getAccountList().get(i).getAccountNumber().toString());
                    linkedAccountList.add(selectItem);
                    if(partnerInfo.getAccountList().get(i).getCardGroup() != null && partnerInfo.getAccountList().get(i).getCardGroup().size()>0){
                        for(int k =0 ; k< partnerInfo.getAccountList().get(i).getCardGroup().size(); k++){
                            if(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard() != null && partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().size()>0){
                                for(int m =0 ; m<partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().size(); m++){
                                    if(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID()!= null && partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getExternalCardID()!= null){
                                        linkedCardValues.add(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID().toString());
                                    System.out.println("value of cards in constructor=========>"+linkedCardValues);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        
        /*ViewObject vo = ADFUtils.getViewObject("PrtAccountVO1Iterator");
        vo.setNamedWhereClauseParam("countryCode", "en_US");
        vo.executeQuery();
        while (vo.hasNext()) {
            PrtAccountVORowImpl currRow = (PrtAccountVORowImpl)vo.next();
            if (currRow.getAccountId() != null) {
                linkedAccountLOVValues.add(currRow.getAccountId());
            }
        }*/
    }

    public ArrayList<SelectItem> getLinkedAccountList() {
        /*if (linkedAccountList == null) {
            ViewObject vo = ADFUtils.getViewObject("PrtAccountVO1Iterator");
            vo.setNamedWhereClauseParam("countryCode", countryParam);
            vo.executeQuery();
            linkedAccountList = new ArrayList<SelectItem>();
            while (vo.hasNext()) {
                PrtAccountVORowImpl currRow = (PrtAccountVORowImpl)vo.next();
                if (currRow.getAccountId() != null) {
                    SelectItem selectItem = new SelectItem();
                    selectItem.setLabel(currRow.getAccountId());
                    selectItem.setValue(currRow.getAccountId());
                    linkedAccountList.add(selectItem);
                }
            }
        }*/
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
    public void searchAction(ActionEvent actionEvent) {
        searchResults(true);
    }

    /**
     * This method is reusable for different scenario's in VehicleInfo Page to show searchResults.
     */
    public void searchResults(boolean value) {

        try {
            if (value == true) {
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
                    }
                }
            } else {
                System.out.println("Add/Edit number val");
                if (getBindings().getLinkedAccount().getValue() == null &&
                    addAccountNumberVal != null) {
                System.out.println("Linked Account Values="+linkedAccountLOVValues);
                    System.out.println("Adding only addAccount");
                    if(linkedAccountLOVValues==null) {
                        linkedAccountLOVValues=new ArrayList<String>();
                    }
                    linkedAccountLOVValues.add(addAccountNumberVal);
                } else {
                    if (getBindings().getLinkedAccount().getValue() != null) {
                        System.out.println("Comming");
                        String searchValues =
                            getBindings().getLinkedAccount().getValue().toString().trim();
                        String[] search = StringConversion(searchValues);
                        System.out.println("searchResults =" + search.length);
                        if (addAccountNumberVal != null) {
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
                        }
                        if (editAccountNumberVal != null) {
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
    }

    public String[] StringConversion(String passedVal) {
        List<String> container;
        String tempString = passedVal.substring(1, passedVal.length() - 1);
        String[] val = tempString.split(",");
        return val;
    }

    public void searchResultsExecution() {
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
            ViewObject vo =
                ADFUtils.getViewObject("PrtTruckInformationVO1Iterator");
            vo.setNamedWhereClauseParam("countryCd", countryParam);
            vo.setWhereClause("trim(ACCOUNT_NUMBER) =: accountNumber AND REGISTRATION_NUMBER LIKE CONCAT (:registrationNumber,'%')");
            System.out.println("values of i" + values[i]);
            vo.defineNamedWhereClauseParam("accountNumber", values[i].trim(),
                                           null);
            if (getBindings().getRegisterNumber().getValue() != null) {
                vo.defineNamedWhereClauseParam("registrationNumber",
                                               getBindings().getRegisterNumber().getValue().toString(),
                                               null);
            } else {
                vo.defineNamedWhereClauseParam("registrationNumber", null,
                                               null);
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
                                if(currRow.getCardNumber()== null || linkedCardValues.contains(currRow.getCardNumber().toString())){
                                VehicleInfo vehicle = new VehicleInfo();
                                if (currRow.getPrtTruckInformationPk() != null) {
                                    vehicle.setPrtTruckInformationPK(currRow.getPrtTruckInformationPk().toString());
                                }
                                vehicle.setAccountNumber(currRow.getAccountNumber());
                                vehicle.setVehicleNumber(currRow.getVehicleNumber());
                                vehicle.setCardNumber(currRow.getCardNumber());
                                vehicle.setInternalName(currRow.getInternalName());
                                vehicle.setRegistrationNumber(currRow.getRegistrationNumber());
                                vehicle.setBrand(currRow.getBrand());
                                if (currRow.getYear() != null) {
                                    vehicle.setYear(Integer.parseInt(currRow.getYear().toString()));
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
                                    vehicle.setMaxFuel(Integer.parseInt(currRow.getMaxFuel().toString()));
                                }
                                if (currRow.getOdometer() != null) {
                                    vehicle.setOdoMeter(Integer.parseInt(currRow.getOdometer().toString()));
                                }
                                myVehicleList.add(vehicle);
                            }
                       }
                    }
                }

            }
            if ("trim(ACCOUNT_NUMBER) =: accountNumber AND REGISTRATION_NUMBER LIKE CONCAT (:registrationNumber,'%')".equalsIgnoreCase(vo.getWhereClause())) {
                vo.removeNamedWhereClauseParam("accountNumber");
                vo.removeNamedWhereClauseParam("registrationNumber");
                vo.setWhereClause("");
                vo.executeQuery();
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
                System.out.println("Inside else block of the show condition of panel +++++++++++++++++++++");
                if (resourceBundle.containsKey("NO_RECORDS_FOUND_VEHICLE")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                         (String)resourceBundle.getObject("NO_RECORDS_FOUND_VEHICLE"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                searchResultsShow = false;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
            }
       
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
        
        if (getBindings().getAddAccountId().getValue() != null) {
            System.out.println("is it coming inside the newDriverSave method++++++++++===============>");
            ViewObject driverVo = ADFUtils.getViewObject("PrtDriverInformationVO3Iterator");
            driverVo.setNamedWhereClauseParam("countryCd", countryParam);
            driverVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
            System.out.println("value of add account id=============>"+addAccountNumberVal);
            driverVo.defineNamedWhereClauseParam("accountId",addAccountNumberVal, null);
            if(getBindings().getAddCardId().getValue() != null){
             driverVo.defineNamedWhereClauseParam("cardNo",getBindings().getAddCardId().getValue().toString(), null);
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
                                String warningMsg = resourceBundle.getObject("DRIVER_CARD_EXIST").toString().concat(" ").concat(currRow.getDriverName());
                                FacesMessage msg =new FacesMessage(FacesMessage.SEVERITY_INFO,warningMsg,"");
                                FacesContext.getCurrentInstance().addMessage(null, msg);
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
            truckVo.defineNamedWhereClauseParam("cardNo",getBindings().getAddCardId().getValue().toString(), null);
            }else{
                truckVo.defineNamedWhereClauseParam("cardNo","", null);
            }
            truckVo.executeQuery();
            if(truckVo.getEstimatedRowCount() > 0){
                while (truckVo.hasNext()) {
                       PrtTruckInformationVORowImpl currRow =(PrtTruckInformationVORowImpl)truckVo.next();
                        if (currRow != null) {
                            if (resourceBundle.containsKey("TRUCK_CARD_EXIST")) {
                                String warningMsg = resourceBundle.getObject("TRUCK_CARD_EXIST").toString().concat(" ").concat(currRow.getVehicleNumber());
                                FacesMessage msg  = new FacesMessage(FacesMessage.SEVERITY_INFO, warningMsg,"");
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                                return null;
                            }
                        }
                }
            }
            
            
            getBindings().getNewVehicle().hide();
            System.out.println("save =" + addAccountNumberVal);
            BindingContainer bindings       = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding vehicleInfoItr = (DCIteratorBinding)bindings.get("PrtTruckInformationVO2Iterator");
            Row vehicleInfoRow = vehicleInfoItr.getCurrentRow();
            System.out.println("Before new vehicle save current row is not null+++++++++++");
                if(vehicleInfoRow != null){
                System.out.println("Inside new driver save current row is not null+++++++++++");
                System.out.println("value of add account id=====>"+getBindings().getAddAccountId().getValue().toString());
                
                vehicleInfoRow.setAttribute("AccountNumber", getBindings().getAddAccountId().getValue().toString());
                if(getBindings().getAddCardId().getValue() != null){
                System.out.println("value of add card    id=====>"+getBindings().getAddCardId().getValue().toString());
                vehicleInfoRow.setAttribute("CardNumber",getBindings().getAddCardId().getValue().toString());
                }else{
                    vehicleInfoRow.setAttribute("CardNumber","");
                }
                vehicleInfoRow.setAttribute("CountryCode", countryParam);
                OperationBinding newDriverOpn = bindings.getOperationBinding("Commit");
                newDriverOpn.execute();
                if(newDriverOpn.getErrors().isEmpty()){
                    System.out.println("No failure in the function");
                }
            }
        }else{
            if (resourceBundle.containsKey("MANDATORY_CHECK")) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("MANDATORY_CHECK"),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
        }
        searchResults(false);
        if (resourceBundle.containsKey("VEHICLE_ADD")) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("VEHICLE_ADD"),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
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

    /**
     * @return
     */
    public String newVehicleAddAction() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding createOpn = bindings.getOperationBinding("CreateInsert");
        createOpn.execute();
        this.addAccountIdDisplayValue = null;
        this.addCardIdDisplayValue    =null;
        getBindings().getNewVehicle().show(new RichPopup.PopupHints());
        return null;
    }

    /**
     * This Method will save edited vehicle information in DB.
     * @return
     */
    public String editVehicleSave() {
        
        if (getBindings().getEditAccountId().getValue() != null) {
            ViewObject driverVo = ADFUtils.getViewObject("PrtDriverInformationVO3Iterator");
            driverVo.setNamedWhereClauseParam("countryCd", countryParam);
            driverVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
            System.out.println("value of edit account number===========>"+getBindings().getEditAccountId().getValue());
            System.out.println("value of edit account number===========>"+editAccountNumberVal);
            driverVo.defineNamedWhereClauseParam("accountId",editAccountNumberVal, null);
            if(getBindings().getEditCardId().getValue() != null){
            System.out.println("value of edit card id============>"+getBindings().getEditCardId().getValue());
            driverVo.defineNamedWhereClauseParam("cardNo",getBindings().getEditCardId().getValue().toString(), null);
            }else{
                System.out.println("value of edit card id in else block============?");
                driverVo.defineNamedWhereClauseParam("cardNo","", null);
            }
            driverVo.executeQuery();
            if(driverVo.getEstimatedRowCount() > 0){
                while (driverVo.hasNext()) {
                PrtDriverInformationVORowImpl currRow =(PrtDriverInformationVORowImpl)driverVo.next();
                     if (currRow != null) {
                        if (resourceBundle.containsKey("DRIVER_CARD_EXIST")) {
                            String warningMsg = resourceBundle.getObject("DRIVER_CARD_EXIST").toString().concat(" ").concat(currRow.getDriverName());
                            FacesMessage msg =new FacesMessage(FacesMessage.SEVERITY_INFO, warningMsg ,"");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            return null;
                        }
                     }
                }
            }
            
            ViewObject truckVo = ADFUtils.getViewObject("PrtTruckInformationVO3Iterator");
            truckVo.setNamedWhereClauseParam("countryCd", countryParam);
            truckVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
            truckVo.defineNamedWhereClauseParam("accountId",editAccountNumberVal, null);
            if(getBindings().getEditCardId().getValue() != null){
            System.out.println("For checking truck======>"+getBindings().getEditCardId().getValue());
            truckVo.defineNamedWhereClauseParam("cardNo",getBindings().getEditCardId().getValue().toString(), null);
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
                                String warningMsg = resourceBundle.getObject("TRUCK_CARD_EXIST").toString().concat(" ").concat(currRow.getVehicleNumber());   
                                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, warningMsg,"");
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                                return null;
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
                vehicleInfoRow.setAttribute("CardNumber",getBindings().getEditCardId().getValue().toString());
                }else{
                    vehicleInfoRow.setAttribute("CardNumber","");
                }
                vehicleInfoRow.setAttribute("CountryCode", countryParam);
                OperationBinding newDriverOpn = bindings.getOperationBinding("Commit");
                newDriverOpn.execute();
                if(newDriverOpn.getErrors().isEmpty()){
                    System.out.println("No failure in the function");
                }
            }

        }
        searchResults(false);
        System.out.println("After Edit Save");
        if (resourceBundle.containsKey("VEHICLE_EDIT")) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("VEHICLE_EDIT"),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
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
            editAccountIdDisplayValue = null;
            editCardIdDisplayValue = null;
            editCardNumberList   = new ArrayList<SelectItem>();
            String primaryKey    =(String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("primarykey");
            String accountNumber = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountnumber");
            String cardId        = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardid");
            System.out.println("PrimaryKey =" + primaryKey);
            if (primaryKey != null && accountNumber != null) {
                editAccountIdDisplayValue = accountNumber;
                editAccountNumberVal = editAccountIdDisplayValue;
                if(editAccountIdDisplayValue != null){
                    populateCardNumberList(editAccountIdDisplayValue, "editButton");
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
            getBindings().getDeleteVehicle().show(new RichPopup.PopupHints());
        } else {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("VEHICLE_DELETE_FAILURE_1"),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
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
                }
            } else {
                if (resourceBundle.containsKey("VEHICLE_DELETE_FAILURE")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("VEHICLE_DELETE_FAILURE"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
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
            System.out.println("Value ==" +
                               AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));
            val.put((String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"),
                    (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));
        } else {
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
        if ("trim(ACCOUNT_NUMBER) =: accountNumber AND REGISTRATION_NUMBER LIKE CONCAT (:registrationNumber,'%')".equalsIgnoreCase(vo.getWhereClause())) {
            vo.removeNamedWhereClauseParam("accountNumber");
            vo.removeNamedWhereClauseParam("registrationNumber");
            vo.setWhereClause("");
            vo.executeQuery();
        }
        getBindings().getLinkedAccount().setSubmittedValue(null);
        getBindings().getLinkedAccount().setValue(linkedAccountLOVValues);
        getBindings().getRegisterNumber().setValue(null);
        searchResultsShow = false;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRegisterNumber());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLinkedAccount());
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
        if (valueChangeEvent.getNewValue() != null) {
           /* BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding itr =(DCIteratorBinding)bindings.get("PrtAccountVO1Iterator");
            Row row =itr.getRowAtRangeIndex(((Integer)valueChangeEvent.getNewValue()));
            if (row != null) {
                addAccountNumberVal = (String)row.getAttribute("AccountId");
            }*/
           cardNumberList  = new ArrayList<SelectItem>();
           populateCardNumberList(valueChangeEvent.getNewValue().toString() , "Add");
           AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddCardId());
        }
    }
    
    public void populateCardNumberList(String accountNo , String type){
        
        if(accountNo != null){
            if(partnerInfo.getAccountList() != null && partnerInfo.getAccountList().size() > 0){
                for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
                    if(partnerInfo.getAccountList().get(i).getAccountNumber() != null && partnerInfo.getAccountList().get(i).getAccountNumber().equals(accountNo)){
                        if(partnerInfo.getAccountList().get(i).getCardGroup() != null && partnerInfo.getAccountList().get(i).getCardGroup().size()>0){ 
                            for(int k =0 ; k< partnerInfo.getAccountList().get(i).getCardGroup().size(); k++){
                                if(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard() != null && partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().size()>0){ 
                                for(int m =0 ; m<partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().size(); m++){
                                        if(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID()!= null && partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getExternalCardID()!= null){
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getExternalCardID().toString());
                                            selectItem.setValue(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID().toString());
                                            if(type.equals("Add")){
                                            //addAccountIdVal = valueChangeEvent.getNewValue().toString();
                                              addAccountNumberVal = accountNo;
                                              cardNumberList.add(selectItem);
                                              System.out.println("addAccountNumber =" + addAccountNumberVal);
                                            }else if(type.equals("Edit")){
                                                editAccountNumberVal = accountNo;
                                                editCardNumberList.add(selectItem);
                                            }else{
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
        if (valueChangeEvent.getNewValue() != null) {
            /*BindingContainer bindings =BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding itr =(DCIteratorBinding)bindings.get("PrtAccountVO1Iterator");
            Row row =itr.getRowAtRangeIndex(((Integer)valueChangeEvent.getNewValue()));
            if (row != null) {
                editAccountNumberVal = (String)row.getAttribute("AccountId");
            }*/
            editCardNumberList = new ArrayList<SelectItem>();
            populateCardNumberList(valueChangeEvent.getNewValue().toString() ,"Edit");
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditCardId());
            System.out.println("editAccountNumber =" + editAccountNumberVal);
        }

    }

    public String deleteAllRecordsAction() {
        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNumber")!=null)
        { 
            int count = 0;
            displayAccountNumber = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNumber").toString().trim();
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
//        String cardId = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardid");
//        if(cardId != null){
//        editCardIdDisplayValue = cardId;
//        }
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
    }
}

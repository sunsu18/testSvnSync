package com.sfr.engage.driverinfotaskflow;

import com.sfr.engage.core.Account;

import com.sfr.engage.core.DriverInfo;
import com.sfr.engage.core.PartnerInfo;
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


public class DriverInfoBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    private List<Account> myAccount;
    private boolean searchResultsShow = false;
    HashMap<String, String> driverMap = new HashMap<String, String>();
    ResourceBundle resourceBundle;
    private String driverN;
    private String accountsList;
    private ArrayList<SelectItem> linkedAccountList;
    private String addAccountIdVal = null;
    private String editAccountIdVal = null;
    private List<String> linkedAccountLOVValues;
    private String displayAccountNumber;
    private List<Account> myAccountDriver;
    
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

    public DriverInfoBean() {
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
        vo.setNamedWhereClauseParam("countryCode", "no_NO"); //changed to no_NO from en_US
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
            vo.setNamedWhereClauseParam("countryCode", "no_NO");
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
     * This method performs search functionality in DriverInfo Page.
     * @param actionEvent
     */
    public void searchAction(ActionEvent actionEvent) {
        searchResults(true);
    }

    /**
     * This method is reusable for different scenario's in DriverInfo Page to show searchResults.
     */
    public void searchResults(boolean value) {
        try {
            if (value == true) {
                if (getBindings().getLinkedAccount().getValue() != null) {
                    searchResultsExecution();
                } else {
                    if (resourceBundle.containsKey("DRIVER_LINKED_ACCOUNT")) {
                        FacesMessage msg =
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                             (String)resourceBundle.getObject("DRIVER_LINKED_ACCOUNT"),
                                             "");
                        FacesContext.getCurrentInstance().addMessage(null,
                                                                     msg);
                    }
                }
            } else {
                if (getBindings().getLinkedAccount().getValue() == null &&
                    addAccountIdVal != null) {
                    if (linkedAccountLOVValues == null) {
                        linkedAccountLOVValues = new ArrayList<String>();
                    }
                    linkedAccountLOVValues.add(addAccountIdVal);
                } else {
                    if (getBindings().getLinkedAccount().getValue() != null) {

                        System.out.println("Selected Valued==" +
                                           getBindings().getLinkedAccount().getValue());
                        String searchValues =
                            getBindings().getLinkedAccount().getValue().toString().trim();
                        String[] search = StringConversion(searchValues);
                        System.out.println("PassedValues==" + search.length);

                        if (addAccountIdVal != null) {
                            int count = 0;
                            for (int i = 0; i < search.length; i++) {
                                System.out.println("String =" + search[i]);
                                if ((addAccountIdVal.equalsIgnoreCase(search[i].trim()))) {
                                    System.out.println("ADDInside");
                                    count = 1;
                                }
                            }
                            if (count == 0) {
                                linkedAccountLOVValues.add(addAccountIdVal);
                            }

                        }

                        if (editAccountIdVal != null) {
                            int count = 0;
                            for (int i = 0; i < search.length; i++) {
                                System.out.println("String =" + search[i]);
                                if ((editAccountIdVal.equalsIgnoreCase(search[i].trim()))) {
                                    System.out.println("EditInside");
                                    count = 1;
                                }
                            }
                            if (count == 0) {
                                linkedAccountLOVValues.add(editAccountIdVal);
                            }
                        }
                    }
                }
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getLinkedAccount());
                addAccountIdVal = null;
                editAccountIdVal = null;
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
            List<DriverInfo> myDriverList = new ArrayList<DriverInfo>();
            ViewObject vo =ADFUtils.getViewObject("PrtDriverInformationVO1Iterator");
            vo.setNamedWhereClauseParam("countryCd", countryParam);
            vo.setWhereClause("trim(ACCOUNT_NUMBER) =: accountId AND DRIVER_NAME LIKE CONCAT (:driverName,'%')");
            System.out.println("values of i" + values[i]);
            vo.defineNamedWhereClauseParam("accountId", values[i].trim(),
                                           null);
            if (getBindings().getDriverName().getValue() != null) {
                vo.defineNamedWhereClauseParam("driverName",
                                               getBindings().getDriverName().getValue().toString(),
                                               null);
            } else {
                vo.defineNamedWhereClauseParam("driverName", null, null);
            }
            vo.executeQuery();
            if (vo.getEstimatedRowCount() != 0) {
                System.out.println("RowCount" + values[i]);
                for (int j = 0; j < vo.getEstimatedRowCount(); j++) {
                    while (vo.hasNext()) {
                        PrtDriverInformationVORowImpl currRow =
                            (PrtDriverInformationVORowImpl)vo.next();
                        if (currRow != null) {
                            //currRow.getCardNumber().isEmpty() || editCardNumberList.contains(currRow.getCardNumber())
                            //if(current Row Card number is blank || or is in the allowed card numbers which is list of cards in session set by MyPageListener Part)
                            if(currRow.getCardNumber()== null || linkedCardValues.contains(currRow.getCardNumber().toString())){
                                System.out.println("is it coming inside to get driver details=================>");
                                DriverInfo driver = new DriverInfo();
                                if (currRow.getPrtDriverInformationPk() != null) {
                                    driver.setPrtDriverInformationPK(currRow.getPrtDriverInformationPk().toString());
                                }
                                driver.setAccountId(currRow.getAccountNumber());
                                driver.setCardNumber(currRow.getCardNumber());
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
            if ("trim(ACCOUNT_ID) =: accountId AND DRIVER_NAME LIKE CONCAT (:driverName,'%')".equalsIgnoreCase(vo.getWhereClause())) {
                System.out.println("Is it coming inside remove where clause");
                vo.removeNamedWhereClauseParam("accountId");
                vo.removeNamedWhereClauseParam("driverName");
                vo.setWhereClause("");
                vo.executeQuery();
            }
            acc.setDriverInfoList(myDriverList);
            myAccount.add(acc);
        }

        myAccountDriver = new ArrayList<Account>();
        if (myAccount.size() > 0) {
            if (getBindings().getDriverName().getValue() != null &&
                getBindings().getDriverName().getValue().toString().length() >
                0) {
                System.out.println("Inside if block of new account list++++++++++++++++++++++++++++++");
                for (int k = 0; k < myAccount.size(); k++) {
                    if (myAccount.get(k).getDriverInfoList().size() > 0) {
                        System.out.println("Inside if block of driver info list");
                        myAccountDriver.add(myAccount.get(k));
                    }
                }
            } else {
                System.out.println("Inside else block of new account list++++++++++++++++++++++++++");
                for (int m = 0; m < myAccount.size(); m++) {
                    myAccountDriver.add(myAccount.get(m));
                }
            }
        }

        if (myAccountDriver.size() > 0) {
            System.out.println("Inside if block of the show condition of panel +++++++++++++++++++++");
            searchResultsShow = true;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
        } else {
            System.out.println("Inside else block of the show condition of panel +++++++++++++++++++++");
            if (resourceBundle.containsKey("NO_RECORDS_FOUND_DRIVER")) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("NO_RECORDS_FOUND_DRIVER"),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            searchResultsShow = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
        }

        // searchResultsShow = true;
        //AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
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
    public void newDriverSave(ActionEvent actionEvent) {
        // Add event code here...
    }

    /**
     * This Method will save new driver information in DB.
     * @return
     */
    public String newDriverSave() {
        // Add event code here...
        System.out.println("is it coming inside the newDriverSave method===============>");
        
        if (getBindings().getAddAccountId().getValue() != null) {
            System.out.println("is it coming inside the newDriverSave method++++++++++===============>");
            ViewObject driverVo = ADFUtils.getViewObject("PrtDriverInformationVO3Iterator");
            driverVo.setNamedWhereClauseParam("countryCd", countryParam);
            driverVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
            System.out.println("value of add account id=============>"+addAccountIdVal);
            driverVo.defineNamedWhereClauseParam("accountId",addAccountIdVal, null);
            if(getBindings().getAddCardId().getValue() != null){
            System.out.println("value od add card on save method=======>"+getBindings().getAddCardId().getValue().toString());
             driverVo.defineNamedWhereClauseParam("cardNo",getBindings().getAddCardId().getValue().toString(), null);
            }else{
                driverVo.defineNamedWhereClauseParam("cardNo"," ", null);
            }
            driverVo.executeQuery();
            if(driverVo.getEstimatedRowCount() > 0){
                while (driverVo.hasNext()) {
                PrtDriverInformationVORowImpl currRow =(PrtDriverInformationVORowImpl)driverVo.next();
                     if (currRow != null) {
                        System.out.println("is it coming inside the newDriverSave method===============++++++++++++++>");
                        if (resourceBundle.containsKey("DRIVER_CARD_EXIST")) {
                            System.out.println("value of driver name======================"+currRow.getDriverName());
                            String warningMsg = resourceBundle.getObject("DRIVER_CARD_EXIST").toString().concat(" ").concat(currRow.getDriverName());
                            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, warningMsg ,"");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            return null;
                        }
                     }
                }
            }
            
            ViewObject truckVo = ADFUtils.getViewObject("PrtTruckInformationVO3Iterator");
            truckVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
            truckVo.defineNamedWhereClauseParam("accountId",addAccountIdVal, null);
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
                            FacesMessage msg  = new FacesMessage(FacesMessage.SEVERITY_INFO, warningMsg, "");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            return null;
                        }
                     }
                }
            }
        
        
            getBindings().getNewDriver().hide();
            System.out.println("save =" + addAccountIdVal);
            BindingContainer bindings       = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding driverInfoItr = (DCIteratorBinding)bindings.get("PrtDriverInformationVO2Iterator");
            Row driverInfoRow = driverInfoItr.getCurrentRow();
            System.out.println("Before new driver save current row is not null+++++++++++");
                if(driverInfoRow != null){
                System.out.println("Inside new driver save current row is not null+++++++++++");
                System.out.println("value of add account id=====>"+getBindings().getAddAccountId().getValue().toString());
                driverInfoRow.setAttribute("AccountNumber", getBindings().getAddAccountId().getValue().toString());
                if(getBindings().getAddCardId().getValue() != null){
                System.out.println("value of add card    id=====>"+getBindings().getAddCardId().getValue().toString());
                driverInfoRow.setAttribute("CardNumber",getBindings().getAddCardId().getValue().toString());
                }else{
                    driverInfoRow.setAttribute("CardNumber","");
                }
                driverInfoRow.setAttribute("CountryCode", countryParam);
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
        if (resourceBundle.containsKey("DRIVER_ADD")) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("DRIVER_ADD"),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        // AdfFacesContext.getCurrentInstance().addPartialTarget(searchResults);
        return null;
    }

    /**
     * @return
     */
    public String newDriverCancel() {
        ResetUtils.reset(getBindings().getNewDriver());
        getBindings().getNewDriver().hide();
        return null;
    }

    /**
     * @return
     */
    public String newDriverAddAction() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding createOpn = bindings.getOperationBinding("CreateInsert");
        createOpn.execute();
        this.addAccountIdDisplayValue = null;
        this.addCardIdDisplayValue    =null;
        getBindings().getNewDriver().show(new RichPopup.PopupHints());
        return null;
    }

    /**
     * This Method will save edited driver information in DB.
     * @return
     */
    public String editDriverSave() {
        
        if (getBindings().getEditAccountId().getValue() != null) {
            
            ViewObject driverVo = ADFUtils.getViewObject("PrtDriverInformationVO3Iterator");
            driverVo.setNamedWhereClauseParam("countryCd", countryParam);
            driverVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
            driverVo.defineNamedWhereClauseParam("accountId",editAccountIdVal, null);
            if(getBindings().getEditCardId().getValue() != null){
            driverVo.defineNamedWhereClauseParam("cardNo",getBindings().getEditCardId().getValue().toString(), null);
            }else{
                driverVo.defineNamedWhereClauseParam("cardNo","", null);  
            }
            driverVo.executeQuery();
            if(driverVo.getEstimatedRowCount() > 0){
                while (driverVo.hasNext()) {
                PrtDriverInformationVORowImpl currRow =(PrtDriverInformationVORowImpl)driverVo.next();
                     if (currRow != null) {
                     System.out.println("is it coming inside the newDriverSave method===============++++++++++++++>");
                        if (resourceBundle.containsKey("DRIVER_CARD_EXIST")) {
                            String warningMsg = resourceBundle.getObject("DRIVER_CARD_EXIST").toString().concat(" ").concat(currRow.getDriverName());
                            FacesMessage msg  = new FacesMessage(FacesMessage.SEVERITY_INFO, warningMsg,"");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            return null;
                    
                        }
                     }
                }
            }
            
            ViewObject truckVo = ADFUtils.getViewObject("PrtTruckInformationVO3Iterator");
            truckVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
            truckVo.defineNamedWhereClauseParam("accountId",editAccountIdVal, null);
            if(getBindings().getEditCardId().getValue() != null){
            truckVo.defineNamedWhereClauseParam("cardNo",getBindings().getEditCardId().getValue().toString(), null);
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
                            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, warningMsg,"");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            return null;
                        }
                     }
                }
            }
            
            getBindings().getEditDriver().hide();
            System.out.println("save =" + editAccountIdVal);
            BindingContainer bindings       = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding driverInfoItr = (DCIteratorBinding)bindings.get("PrtDriverInformationVO2Iterator");
            Row driverInfoRow = driverInfoItr.getCurrentRow();
            System.out.println("Before edit driver save current row is not null+++++++++++");
                if(driverInfoRow != null){
                System.out.println("Inside edit driver save current row is not null+++++++++++");
                System.out.println("value of edit account id=====>"+getBindings().getEditAccountId().getValue().toString());
                
                driverInfoRow.setAttribute("AccountNumber", getBindings().getEditAccountId().getValue().toString());
                if(getBindings().getEditCardId().getValue() != null){
                    System.out.println("value of edit card    id=====>"+getBindings().getEditCardId().getValue().toString());
                driverInfoRow.setAttribute("CardNumber",getBindings().getEditCardId().getValue().toString());
                }else{
                    driverInfoRow.setAttribute("CardNumber","");
                }
                driverInfoRow.setAttribute("CountryCode", countryParam);
                OperationBinding newDriverOpn = bindings.getOperationBinding("Commit");
                newDriverOpn.execute();
                if(newDriverOpn.getErrors().isEmpty()){
                    System.out.println("No failure in the function");
                }
            }
        }
        searchResults(false);
        if (resourceBundle.containsKey("DRIVER_EDIT")) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("DRIVER_EDIT"),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }

    /**
     * @return
     */
    public String editDriverCancel() {
        ResetUtils.reset(getBindings().getEditDriver());
        getBindings().getEditDriver().hide();
        return null;
    }

    /**
     *  This Method will execute VO and fetches selected row from DB and perform Edit operation.
     * @return
     */
    public String tableEditAction() {
        try {
            editAccountIdDisplayValue   = null;
            editCardIdDisplayValue = null;
            editCardNumberList = new ArrayList<SelectItem>();
            String primaryKey = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("primarykey");
            String accountNumber = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountnumber");
            String cardId = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardid");
            System.out.println("PrimaryKey =" + primaryKey);
            if (primaryKey != null && accountNumber != null) {
                editAccountIdDisplayValue = accountNumber;
                editAccountIdVal = editAccountIdDisplayValue;
                if(editAccountIdDisplayValue != null){
                    populateCardNumberList(editAccountIdDisplayValue, "editButton");
                }
                
                ViewObject vo = ADFUtils.getViewObject("PrtDriverInformationVO2Iterator");
                vo.setNamedWhereClauseParam("countryCd", countryParam);
                vo.setWhereClause("PRT_DRIVER_INFORMATION_PK =: prtDriverInformationPK");
                vo.defineNamedWhereClauseParam("prtDriverInformationPK",primaryKey, null);
                vo.executeQuery();
                getBindings().getEditDriver().show(new RichPopup.PopupHints());
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
    public String driverDeleteAction() {
        if (driverMap.size() != 0) {
            getBindings().getDeleteDriver().show(new RichPopup.PopupHints());
        } else {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("DRIVER_DELETE_FAILURE_1"),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return null;
    }

    /**
     * This Method will show confirmation popup for Delete All records operation.
     * @return
     */
    public String deleteAllRecordsAction() {
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNumber") !=
            null) {
            int count = 0;
            displayAccountNumber =
                    AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNumber").toString().trim();
            if (myAccountDriver.size() > 0) {
                for (int i = 0; i < myAccountDriver.size(); i++) {
                    if (myAccountDriver.get(i).getAccountNumber().trim().equals(displayAccountNumber)) {
                        if (myAccountDriver.get(i).getDriverInfoList().size() >
                            0) {
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
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("NO_RECORDS_FOUND_DELETE_ALL"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
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
            Iterator iter = driverMap.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String)iter.next();
                String vals = driverMap.get(key);
                System.out.println("key,val: " + key + "," + vals);
                ViewObject vo =ADFUtils.getViewObject("PrtDriverInformationVO2Iterator");
                vo.setNamedWhereClauseParam("countryCd", countryParam);
                vo.setWhereClause("PRT_DRIVER_INFORMATION_PK =: prtDriverInformationPK");
                vo.defineNamedWhereClauseParam("prtDriverInformationPK", vals,
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
            Object result = operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
                getBindings().getDeleteDriver().hide();
                driverMap = new HashMap<String, String>();
                searchResults(false);
                if (resourceBundle.containsKey("DRIVER_DELETE_SUCCESS")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                         (String)resourceBundle.getObject("DRIVER_DELETE_SUCCESS"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                if (resourceBundle.containsKey("DRIVER_DELETE_FAILURE")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("DRIVER_DELETE_FAILURE"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
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
     * This method will delete all the driver details for the Selected Account./
     * @return
     */
    public String delteAllForAccount() {
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNumber") !=
            null) {
            BindingContainer bindings =
                BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding =
                bindings.getOperationBinding("deleteAllForAccount");
            operationBinding.getParamsMap().put("accountId",
                                                AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNumber").toString().trim());
            operationBinding.getParamsMap().put("type", "driver");
            operationBinding.getParamsMap().put("countryCd", countryParam);
            if (getBindings().getDriverName().getValue() != null &&
                getBindings().getDriverName().getValue().toString().length() >
                0) {
                System.out.println("Inside driver bean this block===================");
                System.out.println("value of driver name in this block===================" +
                                   getBindings().getDriverName().getValue().toString());
                operationBinding.getParamsMap().put("regDriverValue",
                                                    getBindings().getDriverName().getValue().toString());
            } else {
                System.out.println("Inside driver bean else block===================");
                operationBinding.getParamsMap().put("regDriverValue", null);
            }
            Object result = operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
                getBindings().getDeleteAllInfoDriver().hide();
                searchResults(true);
            } else {

            }
        }
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
        // Add event code here...
        if (valueChangeEvent.getNewValue().equals(true)) {
            System.out.println("Value ==" +
                               AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));
            driverMap.put((String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"),
                          (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));
        } else {
            if (driverMap.containsKey(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"))) {
                driverMap.remove(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));
            }
        }
    }

    /**
     * This Method will clears selected Linked Account and Driver Name from DriverInfo Page.
     * @param actionEvent
     */
    public void searchCancel(ActionEvent actionEvent) {
        try {
            ViewObject vo =
                ADFUtils.getViewObject("PrtDriverInformationVO1Iterator");
            if ("trim(ACCOUNT_NUMBER) =: accountId AND DRIVER_NAME LIKE CONCAT (:driverName,'%')".equalsIgnoreCase(vo.getWhereClause())) {
                vo.removeNamedWhereClauseParam("accountId");
                vo.removeNamedWhereClauseParam("driverName");
                vo.setWhereClause("");
                vo.executeQuery();
            }
            this.getBindings().getLinkedAccount().setValue(null);
            driverN = null;
            searchResultsShow = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindings().getDriverName());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindings().getLinkedAccount());
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

    public void AddAccountNumberListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null) {
//            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
//            DCIteratorBinding itr = (DCIteratorBinding)bindings.get("PrtAccountVO1Iterator");
//            Row row = itr.getRowAtRangeIndex(((Integer)valueChangeEvent.getNewValue()));
//            if (row != null) {
//                    addAccountIdVal = (String)row.getAttribute("AccountId");
//            }
             cardNumberList  = new ArrayList<SelectItem>();
             
             populateCardNumberList(valueChangeEvent.getNewValue().toString() , "Add");
             AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddCardId());
             System.out.println("addAccountNumber =" + addAccountIdVal);
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
                                              addAccountIdVal = accountNo;
                                              cardNumberList.add(selectItem);
                                              System.out.println("addAccountNumber =" + addAccountIdVal);
                                            }else if(type.equals("Edit")){
                                                editAccountIdVal = accountNo;
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
        if (valueChangeEvent.getNewValue() != null) {
//            BindingContainer bindings =BindingContext.getCurrent().getCurrentBindingsEntry();
//            DCIteratorBinding itr =(DCIteratorBinding)bindings.get("PrtAccountVO1Iterator");
//            Row row =itr.getRowAtRangeIndex(((Integer)valueChangeEvent.getNewValue()));
//            if (row != null) {
//                editAccountIdVal = (String)row.getAttribute("AccountId");
//            }
             editCardNumberList = new ArrayList<SelectItem>();
             populateCardNumberList(valueChangeEvent.getNewValue().toString() ,"Edit");
             System.out.println("editAccountNumber =" + editAccountIdVal);
             AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditCardId());
        }
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

    public void setCardNumberList(ArrayList<SelectItem> cardNumberList) {
        this.cardNumberList = cardNumberList;
    }

    public ArrayList<SelectItem> getCardNumberList() {
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
//        String cardId = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardid");
//        if(cardId != null){
//        editCardIdDisplayValue = cardId;
//        }
        return editCardIdDisplayValue;
    }

    public void setEditCardNumberList(ArrayList<SelectItem> editCardNumberList) {
        this.editCardNumberList = editCardNumberList;
    }

    public ArrayList<SelectItem> getEditCardNumberList() {
        return editCardNumberList;
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
    }
}

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

    public DriverInfoBean() {
        super();
        resourceBundle = new EngageResourceBundle();
        ectx                      = FacesContext.getCurrentInstance().getExternalContext();
        request                   = (HttpServletRequest)ectx.getRequest();
        session                   = request.getSession(false);
        countryParam              = null;
        linkedCardValues          = new ArrayList<String>();
        linkedPartnerLOVValues    = null;
        linkedPartnerList         = new ArrayList<SelectItem>();

       if(session.getAttribute("Partner_Object_List") != null){
                    partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
        }

        if(partnerInfoList != null && partnerInfoList.size() >0 ){
            for(int pa=0 ; pa<partnerInfoList.size(); pa++){
                countryParam = partnerInfoList.get(0).getCountry().toString();
                System.out.println("value of countryParam==============>"+countryParam);
                SelectItem selectItemPartner = new SelectItem();
                selectItemPartner.setLabel(partnerInfoList.get(pa).getPartnerName().toString());
                selectItemPartner.setValue(partnerInfoList.get(pa).getPartnerValue().toString());
                linkedPartnerList.add(selectItemPartner);
                if(partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() > 0){
                    for(int ac=0 ; ac<partnerInfoList.get(pa).getAccountList().size(); ac++){
                        if(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup() != null && partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size()>0){
                             for(int cg =0 ; cg< partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().size(); cg++){
                                 if(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard() != null && partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size()>0){
                                     for(int cc =0 ; cc<partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().size(); cc++){
                                         if(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID()!= null && partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID()!= null){
                                             linkedCardValues.add(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                             cardNumberMap.put(partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString(), partnerInfoList.get(pa).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
//                                             System.out.println("value of cards in constructor=========>"+linkedCardValues);
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
     * This method performs search functionality in DriverInfo Page.
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
     * This method is reusable for different scenario's in DriverInfo Page to show searchResults.
     */
    public String searchResults(boolean value) {
        try {
            if (value == true) {
                if(getBindings().getLinkedPartner().getValue() != null){
                System.out.println("Is it coming inside this trueeeeeeeeeeeeeeeeeeeeeeeeeeeee");
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
                System.out.println("Is it coming inside this falseeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                if (getBindings().getLinkedPartner().getValue() == null && getBindings().getLinkedAccount().getValue() == null &&
                    addAccountIdVal != null && addPartnerIdVal != null) {
                    System.out.println("Is it coming inside this AAAAAAAAAAAAAAAAAAAAAAAAA");
                    if (linkedAccountLOVValues == null) {
                        System.out.println("Is it coming inside this BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
                        linkedAccountLOVValues = new ArrayList<String>();
                        linkedAccountList = new ArrayList<SelectItem>();
                        linkedPartnerLOVValues = null;
                    }
                    if(linkedAddAccountList.size()>0){
                        for(int i=0 ; i<linkedAddAccountList.size();i++){
                             linkedAccountList.add(linkedAddAccountList.get(i));
                        }
                    }
                    this.linkedPartnerLOVValues = addPartnerIdVal;
                    linkedAccountLOVValues.add(addAccountIdVal);
                } else {
                    System.out.println("Is it coming inside this cccccccccccccccccccccccccccccccc");
                    if (getBindings().getLinkedPartner().getValue() != null && getBindings().getLinkedAccount().getValue() != null) {
                        System.out.println("Is it coming inside this ddddddddddddddddddddddddddddddddd");
                        System.out.println("Selected Valued111111111==" +getBindings().getLinkedAccount().getValue());
                        String searchValues =getBindings().getLinkedAccount().getValue().toString().trim();
                        String[] search = StringConversion(searchValues);
                        System.out.println("PassedValues11111111111111==" + search.length);

                        if (addAccountIdVal != null && addPartnerIdVal != null) {
                            if(addPartnerIdVal.equals(getBindings().getLinkedPartner().getValue().toString())){
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
                            }else{
                                    linkedAccountLOVValues = new ArrayList<String>();
                                    linkedAccountList = new ArrayList<SelectItem>();
                                    linkedPartnerLOVValues = null;

                                    if(linkedAddAccountList.size()>0){
                                        for(int i=0 ; i<linkedAddAccountList.size();i++){
                                             linkedAccountList.add(linkedAddAccountList.get(i));
                                        }
                                    }
                                    linkedAccountLOVValues.add(addAccountIdVal);
                                    this.linkedPartnerLOVValues = addPartnerIdVal;
                            }

                        }

                        if (editAccountIdVal != null && editPartnerIdVal != null) {
                            if(editPartnerIdVal.equals(getBindings().getLinkedPartner().getValue().toString())){
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
                            }else{
                                linkedAccountLOVValues = new ArrayList<String>();
                                linkedAccountList = new ArrayList<SelectItem>();
                                linkedPartnerLOVValues = null;

                                if(linkedEditAccountList.size()>0){
                                    for(int i=0 ; i<linkedEditAccountList.size();i++){
                                         linkedAccountList.add(linkedEditAccountList.get(i));
                                    }
                                }
                                linkedAccountLOVValues.add(editAccountIdVal);
                                this.linkedPartnerLOVValues = editPartnerIdVal;
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
        return null;
    }

    public String[] StringConversion(String passedVal) {
        List<String> container;
        String tempString = passedVal.substring(1, passedVal.length() - 1);
        String[] val = tempString.split(",");
        return val;
    }

    public String searchResultsExecution() {
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
            vo.setWhereClause("trim(ACCOUNT_NUMBER) =: accountId AND trim(DRIVER_NAME) LIKE CONCAT (:driverName,'%')");
            System.out.println("values of i" + values[i]);
            vo.defineNamedWhereClauseParam("accountId", values[i].trim(),
                                           null);
            if (getBindings().getDriverName().getValue() != null) {
                vo.defineNamedWhereClauseParam("driverName",
                                               getBindings().getDriverName().getValue().toString().trim(),
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
                            System.out.println("current card row====>"+currRow.getCardNumber());
                            if(currRow.getCardNumber()== null || linkedCardValues.contains(currRow.getCardNumber().toString())){
                                System.out.println("is it coming inside to get driver details=================>");
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
            if ("trim(ACCOUNT_ID) =: accountId AND trim(DRIVER_NAME) LIKE CONCAT (:driverName,'%')".equalsIgnoreCase(vo.getWhereClause())) {
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
                return null;
            }
            searchResultsShow = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
        }
        return null;
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

        if (getBindings().getAddPartnerNumberId().getValue() != null && getBindings().getAddAccountId().getValue() != null 
            && getBindings().getAddDriverName().getValue() != null && getBindings().getAddDriverNumber().getValue() != null) {
            System.out.println("is it coming inside the newDriverSave method++++++++++===============>");
            ViewObject driverVo = ADFUtils.getViewObject("PrtDriverInformationVO3Iterator");
            driverVo.setNamedWhereClauseParam("countryCd", countryParam);
            driverVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
            if(addAccountIdVal == null){
                addAccountIdVal = getBindings().getAddAccountId().getValue().toString();
            }
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
                            warningMsg = resourceBundle.getObject("DRIVER_CARD_EXIST").toString().concat(" ").concat(currRow.getDriverName());
                            showErrorMsgFlag = true;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                            //FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, warningMsg ,"");
                            //FacesContext.getCurrentInstance().addMessage(null, msg);
                            return null;
                        }
                     }
                }
            }

            ViewObject truckVo = ADFUtils.getViewObject("PrtTruckInformationVO3Iterator");
            truckVo.setNamedWhereClauseParam("countryCd", countryParam);
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
                            warningMsg = resourceBundle.getObject("TRUCK_CARD_EXIST").toString().concat(" ").concat(currRow.getVehicleNumber());
                            showErrorMsgFlag = true;
                            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                            //FacesMessage msg  = new FacesMessage(FacesMessage.SEVERITY_INFO, warningMsg, "");
                            //FacesContext.getCurrentInstance().addMessage(null, msg);
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

            if (resourceBundle.containsKey("ENGAGE_SELECT_TRANSACTION_MANDATORY")) {
                warningMsg = resourceBundle.getObject("ENGAGE_SELECT_TRANSACTION_MANDATORY").toString();
                showErrorMsgFlag = true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowErrorMsg());
                //FacesMessage msg =new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("MANDATORY_CHECK"),"");
                //FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
        }
        searchResults(false);
        if (resourceBundle.containsKey("DRIVER_ADD")) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("DRIVER_ADD"),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
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
    public String newDriverAddAction() {
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
                    this.addAccountIdDisplayValue = populateStringValues(getBindings().getLinkedAccount().getValue().toString());
                }
                this.addPartnerNumberDisplayValue = getBindings().getLinkedPartner().getValue().toString();
                this.addCardIdDisplayValue = null;
                populateAccountNumber(getBindings().getLinkedPartner().getValue().toString(),"Add");
                populateCardNumberList(populateStringValues(getBindings().getLinkedAccount().getValue().toString()),"newDriverAdd",getBindings().getLinkedPartner().getValue().toString());
            }
        }
        else{
            System.out.println("Is it coming inside add driver action else block");
        this.addPartnerNumberDisplayValue = null;
        this.addAccountIdDisplayValue = null;
        this.addCardIdDisplayValue    =null;
        }

        if(getBindings().getDriverName().getValue() != null){
            getBindings().getAddDriverName().setSubmittedValue(null);
            getBindings().getAddDriverName().setSubmittedValue(getBindings().getDriverName().getValue().toString());
        }
        getBindings().getNewDriver().show(new RichPopup.PopupHints());
        return null;
    }

    /**
     * This Method will save edited driver information in DB.
     * @return
     */
    public String editDriverSave() {

        if (getBindings().getEditPartnerNumberId().getValue() != null && getBindings().getEditAccountId().getValue() != null 
            && getBindings().getEditDriverName().getValue() != null && getBindings().getEditDriverNumber().getValue() != null) {

            System.out.println("cardid value inside edit driver save11111======>"+getBindings().getEditCardId().getValue());

            if(previousCardId != null && getBindings().getEditCardId().getValue() != null){
                System.out.println("cardid value inside edit driver save======>"+previousCardId);
                if(!previousCardId.equals(getBindings().getEditCardId().getValue().toString())){
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
                                    warningMsg = resourceBundle.getObject("DRIVER_CARD_EXIST").toString().concat(" ").concat(currRow.getDriverName());
                                    showErrorMsgEditFlag = true;
                                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
                                    //FacesMessage msg  = new FacesMessage(FacesMessage.SEVERITY_INFO, warningMsg,"");
                                    //FacesContext.getCurrentInstance().addMessage(null, msg);
                                    return null;

                                }
                             }
                        }
                    }
                }
            }else{
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
                                warningMsg = resourceBundle.getObject("DRIVER_CARD_EXIST").toString().concat(" ").concat(currRow.getDriverName());
                                showErrorMsgEditFlag = true;
                                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditErrorMessage());
                                //FacesMessage msg  = new FacesMessage(FacesMessage.SEVERITY_INFO, warningMsg,"");
                                //FacesContext.getCurrentInstance().addMessage(null, msg);
                                return null;

                            }
                         }
                    }
                }
            }

            ViewObject truckVo = ADFUtils.getViewObject("PrtTruckInformationVO3Iterator");
            truckVo.setNamedWhereClauseParam("countryCd", countryParam);
            truckVo.setWhereClause("ACCOUNT_NUMBER =: accountId AND CARD_NUMBER =: cardNo");
            truckVo.defineNamedWhereClauseParam("accountId",editAccountIdVal, null);
            if(getBindings().getEditCardId().getValue() != null){
            truckVo.defineNamedWhereClauseParam("cardNo",getBindings().getEditCardId().getValue().toString(), null);
            }else{
                truckVo.defineNamedWhereClauseParam("cardNo","", null);
            }
            truckVo.executeQuery();
            if(truckVo.getEstimatedRowCount() > 0){
                System.out.println("inside edit driver save to test vehicle===========>");
                while (truckVo.hasNext()) {
                    PrtTruckInformationVORowImpl currRow =(PrtTruckInformationVORowImpl)truckVo.next();
                     if (currRow != null) {
                        if (resourceBundle.containsKey("TRUCK_CARD_EXIST")) {
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
        if (resourceBundle.containsKey("DRIVER_EDIT")) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("DRIVER_EDIT"),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
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
            showErrorMsgEditFlag = false;
            editAccountIdDisplayValue   = null;
            editCardIdDisplayValue = null;
            editCardNumberList = new ArrayList<SelectItem>();
            linkedEditAccountList = new ArrayList<SelectItem>();
            String primaryKey = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("primarykey");
            String accountNumber = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountnumber");
            cardId = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardid");
            previousCardId = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardid");
            System.out.println("PrimaryKey =" + primaryKey);
            if (primaryKey != null && accountNumber != null && getBindings().getLinkedPartner().getValue() != null) {
                editPartnerNumberDisplayValue = getBindings().getLinkedPartner().getValue().toString();
                editAccountIdDisplayValue = accountNumber;
                editAccountIdVal = editAccountIdDisplayValue;
                if(editAccountIdDisplayValue != null){
                    populateAccountNumber(getBindings().getLinkedPartner().getValue().toString(),"Edit");
                    populateCardNumberList(editAccountIdDisplayValue, "editButton", getBindings().getLinkedPartner().getValue().toString());
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
                if (resourceBundle.containsKey("NO_DELETE_DRIVER")) {
                    String cardList = validateCard.toString();
                    String validateCardValues = cardList.substring(1, cardList.length() - 1).replace(" ", "");
                    String driverErrorMsg = resourceBundle.getObject("NO_DELETE_DRIVER").toString().concat(validateCardValues);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,driverErrorMsg,"");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
            }
            getBindings().getDeleteDriver().show(new RichPopup.PopupHints());
        } else {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("DRIVER_DELETE_FAILURE_1"),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
        }
        return null;
    }

    /**
     * This Method will show confirmation popup for Delete All records operation.
     * @return
     */
    public String deleteAllRecordsAction() {
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNumber") != null) {
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
                if (resourceBundle.containsKey("NO_DELETE_DRIVER")) {
                    String driverErrorMsg = resourceBundle.getObject("NO_DELETE_DRIVER").toString().concat(displayAccountNumber);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,driverErrorMsg,"");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
                }
            }
            int count = 0;
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
                    return null;
                }
            } else {
                if (resourceBundle.containsKey("DRIVER_DELETE_FAILURE")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("DRIVER_DELETE_FAILURE"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return null;
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
            validateAccountCard = new ArrayList<String>();
            validateAccountCard.add(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNo").toString());
            System.out.println("Value ==" +AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));
            driverMap.put((String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"),
                          (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));
        } else {
            validateAccountCard = new ArrayList<String>();
            validateAccountCard.remove(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("accountNo").toString());
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
            if ("trim(ACCOUNT_NUMBER) =: accountId AND trim(DRIVER_NAME) LIKE CONCAT (:driverName,'%')".equalsIgnoreCase(vo.getWhereClause())) {
                vo.removeNamedWhereClauseParam("accountId");
                vo.removeNamedWhereClauseParam("driverName");
                vo.setWhereClause("");
                vo.executeQuery();
            }

            this.linkedPartnerLOVValues = null;
            this.linkedAccountLOVValues = null;
            linkedAccountList = new ArrayList<SelectItem>();
            this.linkedAccountLOVValues = null;
            getBindings().getLinkedPartner().setSubmittedValue(null);
            getBindings().getLinkedPartner().setValue(null);
            driverN = null;
            searchResultsShow = false;

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindings().getLinkedPartner());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindings().getLinkedAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindings().getDriverName());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());

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
        if (valueChangeEvent.getNewValue() != null && getBindings().getAddPartnerNumberId().getValue() != null) {
//            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
//            DCIteratorBinding itr = (DCIteratorBinding)bindings.get("PrtAccountVO1Iterator");
//            Row row = itr.getRowAtRangeIndex(((Integer)valueChangeEvent.getNewValue()));
//            if (row != null) {
//                    addAccountIdVal = (String)row.getAttribute("AccountId");
//            }
             cardNumberList  = new ArrayList<SelectItem>();
             populateCardNumberList(valueChangeEvent.getNewValue().toString() , "Add" , getBindings().getAddPartnerNumberId().getValue().toString());
             AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAddCardId());
//             System.out.println("addAccountNumber =" + addAccountIdVal);
        }
    }

    public void populateCardNumberList(String accountNo , String type , String partnerNumber){

        if(accountNo != null){
            if(partnerInfoList != null && partnerInfoList.size()>0){
                for( int pa=0 ; pa<partnerInfoList.size() ; pa++){
                    if( partnerInfoList.get(pa).getPartnerValue().equals(partnerNumber) && partnerInfoList.get(pa).getAccountList() != null && partnerInfoList.get(pa).getAccountList().size() > 0){
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
                                                          addAccountIdVal = accountNo;
                                                          cardNumberList.add(selectItem);
//                                                          System.out.println("addAccountNumber =" + addAccountIdVal);
                                                        }else if(type.equals("Edit")){
                                                            editAccountIdVal = accountNo;
                                                            editCardNumberList.add(selectItem);
                                                        }else if(type.equals("newDriverAdd")){
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
        if (valueChangeEvent.getNewValue() != null && getBindings().getEditPartnerNumberId().getValue() != null) {
//            BindingContainer bindings =BindingContext.getCurrent().getCurrentBindingsEntry();
//            DCIteratorBinding itr =(DCIteratorBinding)bindings.get("PrtAccountVO1Iterator");
//            Row row =itr.getRowAtRangeIndex(((Integer)valueChangeEvent.getNewValue()));
//            if (row != null) {
//                editAccountIdVal = (String)row.getAttribute("AccountId");
//            }
             editCardNumberList = new ArrayList<SelectItem>();
             cardId = null;
             populateCardNumberList(valueChangeEvent.getNewValue().toString() ,"Edit",getBindings().getEditPartnerNumberId().getValue().toString());
             System.out.println("editAccountNumber =" + editAccountIdVal);
             AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getEditCardId());
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
        if(cardId != null){
            System.out.println("It is coming display value always=========>"+cardId);
            editCardIdDisplayValue = cardId;
        }
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

    public void partnerNumberValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null) {
             this.searchResultsShow = false;
             this.driverN = null;
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
             AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindings().getDriverName());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
         }else{
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
        
    }

    public void setLinkedAddAccountList(ArrayList<SelectItem> linkedAddAccountList) {
        this.linkedAddAccountList = linkedAddAccountList;
    }

    public ArrayList<SelectItem> getLinkedAddAccountList() {
        return linkedAddAccountList;
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

    public void setLinkedEditAccountList(ArrayList<SelectItem> linkedEditAccountList) {
        this.linkedEditAccountList = linkedEditAccountList;
    }

    public ArrayList<SelectItem> getLinkedEditAccountList() {
        return linkedEditAccountList;
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
    }
}

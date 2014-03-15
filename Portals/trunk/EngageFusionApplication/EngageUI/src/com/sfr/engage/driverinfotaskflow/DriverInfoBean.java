package com.sfr.engage.driverinfotaskflow;

import com.sfr.engage.core.Account;

import com.sfr.engage.core.DriverInfo;
import com.sfr.engage.model.queries.uvo.PrtDriverInformationVORowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.engage.utility.util.ADFUtils;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
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
    HashMap<String,String> driverMap=new HashMap<String,String>();
    ResourceBundle resourceBundle;
    private String driverN;
    private String accountsList;
        
        
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
            searchResults();  
        }
    
    /**
     * This method is reusable for different scenario's in DriverInfo Page to show searchResults.
     */
        public void searchResults() {
        try{
            if(getBindings().getLinkedAccount().getValue()!=null)
            {
            int count = 0;
            String[] values;
            System.out.println("Selected Valued==" + getBindings().getLinkedAccount().getValue());
            String selectedValues = getBindings().getLinkedAccount().getValue().toString().trim();
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
                ViewObject vo =
                    ADFUtils.getViewObject("PrtDriverInformationVO1Iterator");           
                vo.setWhereClause("trim(ACCOUNT_ID) =: accountId AND DRIVER_NAME LIKE CONCAT (:driverName,'%')");
                System.out.println("values of i"+values[i]);
                vo.defineNamedWhereClauseParam("accountId", values[i].trim(), null);
                if(getBindings().getDriverName().getValue() != null){
                    vo.defineNamedWhereClauseParam("driverName",  getBindings().getDriverName().getValue().toString(), null);
                }
                else{
                    vo.defineNamedWhereClauseParam("driverName",  null , null);
                }
                System.out.println("Query =="+vo.getQuery());
                vo.executeQuery();
                if (vo.getEstimatedRowCount() != 0) {
                    System.out.println("RowCount"+values[i]);
                    for (int j = 0; j < vo.getEstimatedRowCount(); j++) {
                        while (vo.hasNext()) {
                            PrtDriverInformationVORowImpl currRow =
                                (PrtDriverInformationVORowImpl)vo.next();
                            if (currRow != null) {
                                DriverInfo driver = new DriverInfo();
                                if(currRow.getPrtDriverInformationPk() != null){
                                driver.setPrtDriverInformationPK(currRow.getPrtDriverInformationPk().toString());
                                }
                                driver.setAccountId(currRow.getAccountId());
                                driver.setCardNumber(currRow.getCardNumber());
                                driver.setDriverName(currRow.getDriverName());
                                driver.setDriverNumber(currRow.getDriverNumber());
                                driver.setNationality(currRow.getNationality());
                                if(currRow.getMobileNumber()!= null){
                                driver.setMobileNumber(Integer.parseInt(currRow.getMobileNumber().toString()));
                                }
                                driver.setRemarks(currRow.getRemarks());                            
                                driver.setPassportNumber(currRow.getPassportNumber());
                                if(currRow.getPassportExpiry()!= null){
                                driver.setPassportExpiry(currRow.getPassportExpiry().getValue());
                                }
                                driver.setLicenseNumber(currRow.getLicenseNumber());
                                if(currRow.getLicenseExpiry()!= null){
                                driver.setLicenseExpiry(currRow.getLicenseExpiry().getValue());
                                }
                                if(currRow.getEmployStart()!= null){
                                driver.setEmployStart(currRow.getEmployStart().getValue());
                                }
                                if(currRow.getEmployEnd()!= null){
                                driver.setEmployEnd(currRow.getEmployEnd().getValue());
                                }
                                driver.setCountryCd(currRow.getRemarks());
                                myDriverList.add(driver);
                            }
                        }
                    }

                }       
                        if("trim(ACCOUNT_ID) =: accountId AND DRIVER_NAME LIKE CONCAT (:driverName,'%')".equalsIgnoreCase(vo.getWhereClause())) {
                            vo.removeNamedWhereClauseParam("accountId");
                            vo.removeNamedWhereClauseParam("driverName");
                            vo.setWhereClause("");
                            vo.executeQuery();
                        }
                acc.setDriverInfoList(myDriverList);
                myAccount.add(acc);
            }
            searchResultsShow = true;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
            }else {
                        if (resourceBundle.containsKey("DRIVER_LINKED_ACCOUNT")) {
                            FacesMessage msg =
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                 (String)resourceBundle.getObject("DRIVER_LINKED_ACCOUNT"),
                                                 "");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }
        }catch(JboException ex){
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),"");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }catch(Exception ex){
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),"");
            FacesContext.getCurrentInstance().addMessage(null, msg);
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
    public void newDriverSave(ActionEvent actionEvent) {
            // Add event code here...
        }

    /**
     * This Method will save new driver information in DB.
     * @return
     */
    public String newDriverSave() {
            // Add event code here...
            getBindings().getNewDriver().hide();
            searchResults();
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
            return null;
        }

    /**
     * This Method will save edited driver information in DB.
     * @return
     */
    public String editDriverSave() {        
            getBindings().getEditDriver().hide();
            searchResults();
            if (resourceBundle.containsKey("DRIVER_EDIT")) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("DRIVER_EDIT"),"");
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
        try{
            String primaryKey=(String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("primarykey"); 
            System.out.println("PrimaryKey ="+primaryKey);
            if(primaryKey!= null){
            ViewObject vo =
                ADFUtils.getViewObject("PrtDriverInformationVO2Iterator");
            vo.setWhereClause("PRT_DRIVER_INFORMATION_PK =: prtDriverInformationPK");        
            vo.defineNamedWhereClauseParam("prtDriverInformationPK", primaryKey, null);        
            vo.executeQuery();
            getBindings().getEditDriver().show(new RichPopup.PopupHints());
            }
        }catch(JboException ex){
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }catch(Exception ex){
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
            if(driverMap.size()!=0)
            { 
                getBindings().getDeleteDriver().show(new RichPopup.PopupHints());
            }else{
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     (String)resourceBundle.getObject("DRIVER_DELETE_FAILURE_1"),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            return null;
        }

    /**
     * This Method will delete selected driver rows from DB.
     * @return
     */
    public String deleteDriverSave() {
        try{
            Iterator iter = driverMap.keySet().iterator();
            while(iter.hasNext()) {
                String key = (String)iter.next();
                String vals = driverMap.get(key);
                System.out.println("key,val: " + key + "," + vals);
                ViewObject vo =
                    ADFUtils.getViewObject("PrtDriverInformationVO2Iterator"); 
                vo.setWhereClause("PRT_DRIVER_INFORMATION_PK =: prtDriverInformationPK");        
                vo.defineNamedWhereClauseParam("prtDriverInformationPK", vals, null);        
                vo.executeQuery();
                if(vo.getEstimatedRowCount()!=0) {
                    System.out.println("RowCount"+vo.getEstimatedRowCount());
                    while(vo.hasNext()) {
                        Row r=vo.next();
                        vo.setCurrentRow(r);
                        vo.removeCurrentRow();
                    }
                }            
            }     
            BindingContainer bindings =  BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
            System.out.println("Success");            
                getBindings().getDeleteDriver().hide();
                driverMap=new HashMap<String,String>();
                searchResults();
                if (resourceBundle.containsKey("DRIVER_DELETE_SUCCESS")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                         (String)resourceBundle.getObject("DRIVER_DELETE_SUCCESS"),"");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                if (resourceBundle.containsKey("DRIVER_DELETE_FAILURE")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("DRIVER_DELETE_FAILURE"),"");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
             System.out.println("Error while commiting");   
            }
        }catch (JboException ex) {
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
    public String deleteDriverCancel() {        
            getBindings().getDeleteDriver().hide();
            return null;
        }

    /**
     * This Method will store selected driver rows in HashMap for Delete operation.
     * @param valueChangeEvent
     */
    public void deleteCheckBoxListener(ValueChangeEvent valueChangeEvent) {
            // Add event code here... 
            if(valueChangeEvent.getNewValue().equals(true)) {
                System.out.println("Value =="+AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));            
                driverMap.put((String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"),(String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));           
            }else {
                if(driverMap.containsKey(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"))) {
                    driverMap.remove(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));
                }
            }
        }

    /**
     * This Method will clears selected Linked Account and Driver Name from DriverInfo Page.
     * @param actionEvent
     */
    public void searchCancel(ActionEvent actionEvent) {
        try{
            ViewObject vo = ADFUtils.getViewObject("PrtDriverInformationVO1Iterator");           
            if ("trim(ACCOUNT_ID) =: accountId AND DRIVER_NAME LIKE CONCAT (:driverName,'%')".equalsIgnoreCase(vo.getWhereClause())) {
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
        }catch (JboException ex) {
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
    
    public class Bindings{
        private RichSelectManyChoice linkedAccount;
        private RichPanelGroupLayout searchResults;
        private RichPopup newDriver;
        private RichPopup editDriver;
        private RichPopup deleteDriver;
        private RichInputText driverName;
        
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
    }
}

package com.sfr.engage.driverinfotaskflow;

import com.sfr.engage.core.Account;

import com.sfr.engage.core.DriverInfo;
import com.sfr.engage.model.queries.uvo.PrtDriverInformationVORowImpl;
import com.sfr.engage.utility.util.ADFUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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

import oracle.jbo.Row;
import oracle.jbo.ViewObject;


public class DriverInfoBean {
    private List<Account> myAccount;
        private RichSelectManyChoice linkedAccount;
        private RichPanelGroupLayout searchResults;
        private boolean searchResultsShow = false;
        private RichPopup newDriver;
        private RichPopup editDriver;
        private RichPopup deleteDriver;
        private RichInputText driverName;
        HashMap<String,String> driverMap=new HashMap<String,String>();
        
        public DriverInfoBean() {
            super();
        }
        
        public void setMyAccount(List<Account> myAccount) {
            this.myAccount = myAccount;
        }

        public List<Account> getMyAccount() {
            return myAccount;
        }

        public void searchAction(ActionEvent actionEvent) {
            // Add event code here...
            searchResults();  
        }
        
        public void searchResults() {
            if(linkedAccount.getValue()!=null)
            {
            int count = 0;
            String[] values;
            System.out.println("Selected Valued==" + linkedAccount.getValue());
            String selectedValues = linkedAccount.getValue().toString().trim();
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
                vo.setWhereClause("trim(ACCOUNT_ID) =: accountId");
                System.out.println("values of i"+values[i]);
                vo.defineNamedWhereClauseParam("accountId", values[i].trim(), null); 
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
                        if("trim(ACCOUNT_ID) =: accountId".equalsIgnoreCase(vo.getWhereClause())) {
                            vo.removeNamedWhereClauseParam("accountId");
                            vo.setWhereClause("");
                            vo.executeQuery();
                        }
                acc.setDriverInfoList(myDriverList);
                myAccount.add(acc);
            }
            searchResultsShow = true;
            AdfFacesContext.getCurrentInstance().addPartialTarget(searchResults);
            }
        }

        public void setLinkedAccount(RichSelectManyChoice linkedAccount) {
            this.linkedAccount = linkedAccount;
        }

        public RichSelectManyChoice getLinkedAccount() {
            return linkedAccount;
        }

        public void setSearchResults(RichPanelGroupLayout searchResults) {
            this.searchResults = searchResults;
        }

        public RichPanelGroupLayout getSearchResults() {
            return searchResults;
        }

        public void setSearchResultsShow(boolean searchResultsShow) {
            this.searchResultsShow = searchResultsShow;
        }

        public boolean isSearchResultsShow() {
            return searchResultsShow;
        }
        

        public void setNewDriver(RichPopup newDriver) {
            this.newDriver = newDriver;
        }

        public RichPopup getNewDriver() {
            return newDriver;
        }
        

        public void newDriverSave(ActionEvent actionEvent) {
            // Add event code here...
        }

        public String newDriverSave() {
            // Add event code here...
            newDriver.hide();
            searchResults();
           // AdfFacesContext.getCurrentInstance().addPartialTarget(searchResults);
            return null;
        }

        public String newDriverCancel() {
            // Add event code here...
            ResetUtils.reset(newDriver);
            newDriver.hide();           
            return null;
        }

        public String newDriverAddAction() {
            // Add event code here...
            return null;
        }

        public void setEditDriver(RichPopup editDriver) {
            this.editDriver = editDriver;
        }

        public RichPopup getEditDriver() {
            return editDriver;
        }    

        public String editDriverSave() {        
            editDriver.hide();
            searchResults();
            return null;
        }

        public String editDriverCancel() {        
            ResetUtils.reset(editDriver);
            editDriver.hide();
            return null;
        }

        public String tableEditAction() {            
            String primaryKey=(String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("primarykey"); 
            System.out.println("PrimaryKey ="+primaryKey);
            ViewObject vo =
                ADFUtils.getViewObject("PrtDriverInformationVO2Iterator");
            vo.setWhereClause("PRT_DRIVER_INFORMATION_PK =: prtDriverInformationPK");        
            vo.defineNamedWhereClauseParam("prtDriverInformationPK", primaryKey, null);        
            vo.executeQuery();
            getEditDriver().show(new RichPopup.PopupHints());
            return null;
        }

        public String driverDeleteAction() {
            // Add event code here...  
            getDeleteDriver().show(new RichPopup.PopupHints());
            return null;
        }

        public BindingContainer getBindings() {
            return BindingContext.getCurrent().getCurrentBindingsEntry();
        }
        
        public void setDeleteDriver(RichPopup deleteDriver) {
            this.deleteDriver = deleteDriver;
        }

        public RichPopup getDeleteDriver() {
            return deleteDriver;
        }
        
        public String deleteDriverSave() {
            // Add event code here...
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
        //                System.out.println("RowCount"+vo.getEstimatedRowCount());
        //                System.out.println("currentRow"+vo.getCurrentRow().getAttribute("PrtTruckInformationPk"));
        //                vo.removeCurrentRow();
                }            
            }     
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding =
                bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (operationBinding.getErrors().isEmpty()) {
            System.out.println("Success");            
                getDeleteDriver().hide();
                driverMap=new HashMap<String,String>();
                searchResults();
            } else {
             System.out.println("Error while commiting");   
            }
            return null;
        }

        public String deleteDriverCancel() {        
            getDeleteDriver().hide();
            return null;
        }

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
        
    public void searchCancel(ActionEvent actionEvent) {
        // Add event code here...
        
    }

    public void setDriverName(RichInputText driverName) {
        this.driverName = driverName;
    }

    public RichInputText getDriverName() {
        return driverName;
    }
}

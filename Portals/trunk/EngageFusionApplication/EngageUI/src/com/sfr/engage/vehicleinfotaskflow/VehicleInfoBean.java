package com.sfr.engage.vehicleinfotaskflow;

import com.sfr.engage.core.Account;

import com.sfr.engage.core.VehicleInfo;

import com.sfr.engage.model.queries.uvo.PrtTruckInformationVORowImpl;
import com.sfr.engage.utility.util.ADFUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.util.ResetUtils;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;


public class VehicleInfoBean {
    private List<Account> myAccount;
    private RichSelectManyChoice linkedAccount;
    private RichPanelGroupLayout searchResults;
    private boolean searchResultsShow = false;
    private RichPopup newVehicle;
    private RichInputText registerNumber;
    private RichPopup editVehicle;    
    private RichPopup deleteVehicle;
    HashMap<String,String> val=new HashMap<String,String>();  
    private boolean vehicleNumber;
    private RichPopup moreColumnsPopup;
    List<VehicleInfo> moreColumnsTable;
    private RichPanelGroupLayout searchPanelGroupLayout;

    public VehicleInfoBean() {
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
            List<VehicleInfo> myVehicleList = new ArrayList<VehicleInfo>();            
            ViewObject vo =
                ADFUtils.getViewObject("PrtTruckInformationVO1Iterator");           
            vo.setWhereClause("trim(ACCOUNT_NUMBER) =: accountNumber AND REGISTRATION_NUMBER LIKE CONCAT (:registrationNumber,'%')");
            System.out.println("values of i"+values[i]);
            vo.defineNamedWhereClauseParam("accountNumber", values[i].trim(), null); 
            if(registerNumber.getValue()!=null)
            {
            vo.defineNamedWhereClauseParam("registrationNumber",  registerNumber.getValue().toString(), null);
            }else {
                vo.defineNamedWhereClauseParam("registrationNumber",  null, null);
            }
            System.out.println("Query =="+vo.getQuery());
            vo.executeQuery();
            if (vo.getEstimatedRowCount() != 0) {
                System.out.println("RowCount"+values[i]);
                for (int j = 0; j < vo.getEstimatedRowCount(); j++) {
                    while (vo.hasNext()) {
                        PrtTruckInformationVORowImpl currRow =
                            (PrtTruckInformationVORowImpl)vo.next();
                        if (currRow != null) {
                            VehicleInfo vehicle = new VehicleInfo();
                            vehicle.setPrtTruckInformationPK(currRow.getPrtTruckInformationPk().toString());
                            vehicle.setAccountNumber(currRow.getAccountNumber());
                            vehicle.setVehicleNumber(currRow.getVehicleNumber());
                            vehicle.setCardNumber(currRow.getCardNumber());
                            vehicle.setInternalName(currRow.getInternalName());
                            vehicle.setRegistrationNumber(currRow.getRegistrationNumber());
                            vehicle.setBrand(currRow.getBrand());
                            vehicle.setYear(Integer.parseInt(currRow.getYear().toString()));                            
                            vehicle.setRegistrationDate(currRow.getRegistrationDate().getValue());
                            vehicle.setEndDate(currRow.getEndDate().getValue());
                            vehicle.setRemarks(currRow.getRemarks());
                            vehicle.setFuelType(currRow.getFuelType());
                            vehicle.setMaxFuel(Integer.parseInt(currRow.getMaxFuel().toString()));
                            vehicle.setODOMeter(Integer.parseInt(currRow.getOdometer().toString()));
                            myVehicleList.add(vehicle);
                        }
                    }
                }

            }       
                    if("trim(ACCOUNT_NUMBER) =: accountNumber AND REGISTRATION_NUMBER LIKE CONCAT (:registrationNumber,'%')".equalsIgnoreCase(vo.getWhereClause())) {
                        vo.removeNamedWhereClauseParam("accountNumber");
                        vo.removeNamedWhereClauseParam("registrationNumber");
                        vo.setWhereClause("");
                        vo.executeQuery();
                    }
            acc.setVehicleInfoList(myVehicleList);
            myAccount.add(acc);
        }
        searchResultsShow = true;
        vehicleNumber=false;
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

    public void setNewVehicle(RichPopup newVehicle) {
        this.newVehicle = newVehicle;
    }

    public RichPopup getNewVehicle() {
        return newVehicle;
    }

    public void newVehicleSave(ActionEvent actionEvent) {
        // Add event code here...
    }

    public String newVehicleSave() {
        // Add event code here...
        newVehicle.hide();
        searchResults();
       // AdfFacesContext.getCurrentInstance().addPartialTarget(searchResults);
        return null;
    }

    public String newVehicleCancel() {
        // Add event code here...
        ResetUtils.reset(newVehicle);
        newVehicle.hide();           
        return null;
    }

    public String newVehicleAddAction() {
        // Add event code here...
        return null;
    }

    public void setRegisterNumber(RichInputText registerNumber) {
        this.registerNumber = registerNumber;
    }

    public RichInputText getRegisterNumber() {
        return registerNumber;
    }

    public void setEditVehicle(RichPopup editVehicle) {
        this.editVehicle = editVehicle;
    }

    public RichPopup getEditVehicle() {
        return editVehicle;
    }

    public String editVehicleSave() {        
        editVehicle.hide();
        searchResults();
        return null;
    }

    public String editVehicleCancel() {        
        ResetUtils.reset(editVehicle);
        editVehicle.hide();
        return null;
    }

    public String tableEditAction() {            
        String primaryKey=(String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("primarykey"); 
        System.out.println("PrimaryKey ="+primaryKey);
        ViewObject vo =
            ADFUtils.getViewObject("PrtTruckInformationVO2Iterator");
        vo.setWhereClause("PRT_TRUCK_INFORMATION_PK =: prtTruckInformationPK");        
        vo.defineNamedWhereClauseParam("prtTruckInformationPK", primaryKey, null);        
        vo.executeQuery();
        getEditVehicle().show(new RichPopup.PopupHints());
        return null;
    }
    
    public String vehicleDeleteAction() {
        // Add event code here...  
        getDeleteVehicle().show(new RichPopup.PopupHints());
        return null;
    }

    public void setDeleteVehicle(RichPopup deleteVehicle) {
        this.deleteVehicle = deleteVehicle;
    }
    
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public RichPopup getDeleteVehicle() {
        return deleteVehicle;
    }

    public String deleteVehicleSave() {
        // Add event code here...
        Iterator iter = val.keySet().iterator();
        while(iter.hasNext()) {
            String key = (String)iter.next();
            String vals = val.get(key);
            System.out.println("key,val: " + key + "," + vals);
            ViewObject vo =
                ADFUtils.getViewObject("PrtTruckInformationVO2Iterator"); 
            vo.setWhereClause("PRT_TRUCK_INFORMATION_PK =: prtTruckInformationPK");        
            vo.defineNamedWhereClauseParam("prtTruckInformationPK", vals, null);        
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
            getDeleteVehicle().hide();
            val=new HashMap<String,String>();
            searchResults();
        } else {
         System.out.println("Error while commiting");   
        }
        return null;
    }

    public String deleteVehicleCancel() {        
        getDeleteVehicle().hide();
        return null;
    }

    public void deleteCheckBoxListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here... 
        if(valueChangeEvent.getNewValue().equals(true)) {
            System.out.println("Value =="+AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));            
            val.put((String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"),(String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));           
        }else {
            if(val.containsKey(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"))) {
                val.remove(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("checkBoxPrimaryKey"));
            }
        }
    }

    public void setVehicleNumber(boolean vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public boolean isVehicleNumber() {
        return vehicleNumber;
    }

    public void moreColumnsButton(ActionEvent actionEvent) {
        // Add event code here...
        vehicleNumber=true;
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchResults);        
    }    

    public void setMoreColumnsPopup(RichPopup moreColumnsPopup) {
        this.moreColumnsPopup = moreColumnsPopup;
    }

    public RichPopup getMoreColumnsPopup() {
        return moreColumnsPopup;
    }

    public void setMoreColumnsTable(List<VehicleInfo> moreColumnsTable) {
        this.moreColumnsTable = moreColumnsTable;
    }

    public List<VehicleInfo> getMoreColumnsTable() {
        return moreColumnsTable;
    }

    public void searchCancel(ActionEvent actionEvent) {
        // Add event code here...
        
    }

    public void setSearchPanelGroupLayout(RichPanelGroupLayout searchPanelGroupLayout) {
        this.searchPanelGroupLayout = searchPanelGroupLayout;
    }

    public RichPanelGroupLayout getSearchPanelGroupLayout() {
        return searchPanelGroupLayout;
    }
}

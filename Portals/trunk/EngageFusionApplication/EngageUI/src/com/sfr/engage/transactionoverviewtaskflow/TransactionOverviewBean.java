package com.sfr.engage.transactionoverviewtaskflow;


import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.model.queries.rvo.PrtCardDriverVehicleInfoRVORowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;

import com.sfr.util.ADFUtils;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;
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

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;


public class TransactionOverviewBean implements Serializable{
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;    
    private transient Bindings bindings;
    
    private ArrayList<SelectItem> accountIdList; 
    private String accountIdValue;
    private ArrayList<SelectItem> termianlList;
    private List<String> terminalValue;
    private ArrayList<SelectItem> typeList;
    private List<String> typeValue;
    private ArrayList<SelectItem> cardNumberList; 
    private List<String> cardNumberValue;
    private ArrayList<SelectItem> cardGroupList; 
    private List<String> cardGroupValue;
    private ArrayList<SelectItem> vehicleNumberList; 
    private List<String> vehicleNumberValue;
    private ArrayList<SelectItem> driverNameList; 
    private List<String> driverNameValue;
    private boolean cardIdPGL  = false;
    private boolean cardGPGL   = false;
    private boolean dNamePGL   = false;
    private boolean vNumberPGL = false;
    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    private PartnerInfo partnerInfo;
    private Boolean isTableVisible=false;
    ResourceBundle resourceBundle;
    private Float sum=0.0f;
    private String cardGroupSubtypePassValues;
    private String cardGroupMaintypePassValue;
    private String cardGroupSeqPassValues;
    private String partnerId;
    
   
    public TransactionOverviewBean() {
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        
        resourceBundle = new EngageResourceBundle();
        accountIdList  = new ArrayList<SelectItem>();
        terminalValue  = new ArrayList<String>();
        typeValue      = new ArrayList<String>();
        partnerId      = null;
        
        if(session.getAttribute("Partner_Object_List") != null){
            partnerInfo = (PartnerInfo)session.getAttribute("Partner_Object_List");
        }
        
            System.out.println("Inside partner info object");
            if(partnerInfo.getPartnerValue() != null){
                System.out.println("Inside partner info object value====>"+partnerInfo.getPartnerValue());
               partnerId = partnerInfo.getPartnerValue().toString(); 
               System.out.println("value of partner number========>"+partnerId);
            
         
            if( partnerInfo.getAccountList() != null && partnerInfo.getAccountList().size() > 0){
                System.out.println("List of Account in partner info object=====>"+partnerInfo.getAccountList().size());
                for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
                    System.out.println("value of Account Id===========>"+partnerInfo.getAccountList().get(i).getAccountNumber().toString());
                    SelectItem selectItem = new SelectItem();
                    selectItem.setLabel(partnerInfo.getAccountList().get(i).getAccountNumber().toString());
                    selectItem.setValue(partnerInfo.getAccountList().get(i).getAccountNumber().toString());
                    accountIdList.add(selectItem);
                }
            }
        }
        
        terminalValue.add("HOME");
        terminalValue.add("EXTERNAL");
        
        typeValue.add("PRI");
        typeValue.add("PR");
        typeValue.add("INV");
    }
    
    /**
     * @return bindings Object
     */
    public Bindings getBindings() {
        if (bindings == null) {
            bindings = new Bindings();
        }
        return bindings;
    }

    /**
     * This method is used to populate Account Id 
     * @return accountIdList
     */
    public ArrayList<SelectItem> getAccountIdList() {
        return accountIdList;
    }
    
    public ArrayList<SelectItem> getCardNumberList() {
        return cardNumberList;
    }

    public ArrayList<SelectItem> getCardGroupList() {
        return cardGroupList;
    }

    public ArrayList<SelectItem> getVehicleNumberList() {
        return vehicleNumberList;
    }

    public ArrayList<SelectItem> getDriverNameList() {
        return driverNameList;
    }
    

    public void setAccountIdValue(String accountIdValue) {
        this.accountIdValue = accountIdValue;
    }

    public String getAccountIdValue() {
        return accountIdValue;
    }

    public void radioButtonValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue() != null){
            System.out.println("value of radioButon value change event======>"+valueChangeEvent.getNewValue());
            if(valueChangeEvent.getNewValue().equals("CardGroup")){
                
                cardGPGL   = true;
                cardIdPGL = false;
                dNamePGL   = false;
                vNumberPGL = false;
                populateValue(valueChangeEvent.getNewValue().toString());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
            }else if(valueChangeEvent.getNewValue().equals("Card")){
                cardGPGL   = false;
                cardIdPGL    = true;
                dNamePGL   = false;
                vNumberPGL = false;
                populateValue(valueChangeEvent.getNewValue().toString());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
            }else if(valueChangeEvent.getNewValue().equals("Vehicle")){
                cardGPGL   = false;
                cardIdPGL    = false;
                dNamePGL   = false;
                vNumberPGL = true;
                populateValue(valueChangeEvent.getNewValue().toString());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
            }else{
                cardGPGL   = false;
                cardIdPGL    = false;
                dNamePGL   = true;
                vNumberPGL = false;
                populateValue(valueChangeEvent.getNewValue().toString());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
            }
        }
    }
    
    public void setCardIdPGL(boolean cardIdPGL) {
        this.cardIdPGL = cardIdPGL;
    }

    public boolean isCardIdPGL() {
        return cardIdPGL;
    }

    public void setCardGPGL(boolean cardGPGL) {
        this.cardGPGL = cardGPGL;
    }

    public boolean isCardGPGL() {
        return cardGPGL;
    }

    public void setDNamePGL(boolean dNamePGL) {
        this.dNamePGL = dNamePGL;
    }

    public boolean isDNamePGL() {
        return dNamePGL;
    }

    public void setVNumberPGL(boolean vNumberPGL) {
        this.vNumberPGL = vNumberPGL;
    }

    public boolean isVNumberPGL() {
        return vNumberPGL;
    }

    public ArrayList<SelectItem> getTermianlList() {
        if (termianlList == null) {
            termianlList = new ArrayList<SelectItem>();            
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel("HOME");
            selectItem.setValue("HOME");
            termianlList.add(selectItem);
            SelectItem selectItem1 = new SelectItem();
            selectItem1.setLabel("EXTERNAL");
            selectItem1.setValue("EXTERNAL");
            termianlList.add(selectItem1);
        }
        return termianlList;
    }

    public ArrayList<SelectItem> getTypeList() {
        if (typeList == null) {
            typeList = new ArrayList<SelectItem>();            
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel("Preliminary");
            selectItem.setValue("PRI");
            typeList.add(selectItem);
            SelectItem selectItem1 = new SelectItem();
            selectItem1.setLabel("Priced");
            selectItem1.setValue("PR");
            typeList.add(selectItem1);
            SelectItem selectItem2 = new SelectItem();
            selectItem2.setLabel("Invoice");
            selectItem2.setValue("INV");
            typeList.add(selectItem2);
        }
        return typeList;
    }
    
    public void populateValue(String paramType){
        if(paramType != null){
            if(paramType.equals("CardGroup")){
                cardGroupList  = new ArrayList<SelectItem>();
                cardGroupValue  = new ArrayList<String>();
                if(partnerInfo.getAccountList() != null && partnerInfo.getAccountList().size() > 0){
                    for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
                        System.out.println("Account Number inside select one radio button==========>"+partnerInfo.getAccountList().get(i).getAccountNumber());
                        if(partnerInfo.getAccountList().get(i).getAccountNumber() != null && partnerInfo.getAccountList().get(i).getAccountNumber().equals(getBindings().account.getValue())){
                            if(partnerInfo.getAccountList().get(i).getCardGroup() != null && partnerInfo.getAccountList().get(i).getCardGroup().size()>0){
                                for(int k =0 ; k< partnerInfo.getAccountList().get(i).getCardGroup().size(); k++){
                                    System.out.println("Card Group inside select one radio button==========>"+partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                    if(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID()!= null){
                                    SelectItem selectItem = new SelectItem();
                                    selectItem.setLabel(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                    selectItem.setValue(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                    cardGroupList.add(selectItem);
                                    cardGroupValue.add(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }else if(paramType.equals("Card")){
                cardNumberList  = new ArrayList<SelectItem>();
                cardNumberValue  = new ArrayList<String>();
                if(partnerInfo.getAccountList() != null && partnerInfo.getAccountList().size() > 0){
                    for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
                        if(partnerInfo.getAccountList().get(i).getAccountNumber() != null && partnerInfo.getAccountList().get(i).getAccountNumber().equals(getBindings().account.getValue())){
                            if(partnerInfo.getAccountList().get(i).getCardGroup() != null && partnerInfo.getAccountList().get(i).getCardGroup().size()>0){ 
                                for(int k =0 ; k< partnerInfo.getAccountList().get(i).getCardGroup().size(); k++){
                                    if(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard() != null && partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().size()>0){ 
                                    for(int m =0 ; m<partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().size(); m++){
                                            if(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID()!= null && partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getExternalCardID()!= null){
                                                SelectItem selectItem = new SelectItem();
                                                selectItem.setLabel(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getExternalCardID().toString());
                                                selectItem.setValue(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID().toString());
                                                cardNumberList.add(selectItem);
                                                cardNumberValue.add(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID().toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                    
            }else {
                    if(paramType.equals("Vehicle") || paramType.equals("Driver")){
                        if(vNumberPGL){
                        vehicleNumberList  = new ArrayList<SelectItem>();
                        vehicleNumberValue  = new ArrayList<String>();
                        }
                        if(dNamePGL){
                        driverNameList  = new ArrayList<SelectItem>();
                        driverNameValue  = new ArrayList<String>();
                        }
                        ViewObject vo = ADFUtils.getViewObject("PrtCardDriverVehicleInfoRVO1Iterator");
                        if(getBindings().account.getValue() != null){
                        vo.setNamedWhereClauseParam("accountValue",getBindings().account.getValue());
                        }
                        vo.setNamedWhereClauseParam("countryCd", "no_NO");
                        vo.setNamedWhereClauseParam("partnerValue", partnerId);
                        vo.setNamedWhereClauseParam("paramValue", paramType);
                        vo.executeQuery();
                        if(vo.getEstimatedRowCount() > 0){
                            for (int n = 0; n < vo.getEstimatedRowCount(); n++) {
                                while(vo.hasNext()){
                                    PrtCardDriverVehicleInfoRVORowImpl currRow = (PrtCardDriverVehicleInfoRVORowImpl)vo.next();
                                    if (currRow != null) {
                                        if(paramType.equals("Vehicle")){
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(currRow.getAttribute("VehicleNumber").toString());
                                            selectItem.setValue(currRow.getAttribute("PrtCardPk").toString());
                                            vehicleNumberList.add(selectItem);
                                            vehicleNumberValue.add(currRow.getAttribute("PrtCardPk").toString());
                                        }else{
                                            
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(currRow.getAttribute("DriverName").toString());
                                            selectItem.setValue(currRow.getAttribute("PrtCardPk").toString());
                                            driverNameList.add(selectItem);
                                            driverNameValue.add(currRow.getAttribute("PrtCardPk").toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }
    }
    
    public void searchTransactionAction_event(ActionEvent actionEvent) {
        sum=0.0f;
        String terminalPassingValues  = null;
        String transTypePassingValues = null;
        String cardNumberPasingValues = null ;
        String cardGroupPassingValues = null;
        String  newFromDate = null;
        String  newToDate = null;
        
        if(getBindings().getTerminalType().getValue() != null){
        System.out.println("value of terminal type================>"+getBindings().getTerminalType().getValue().toString().trim());
           terminalPassingValues =  populateStringValues(getBindings().getTerminalType().getValue().toString());
        }
        if(getBindings().getTransationType().getValue() != null){
        System.out.println("value of transaction type================>"+getBindings().getTransationType().getValue().toString().trim());
            transTypePassingValues =  populateStringValues(getBindings().getTransationType().getValue().toString());
        }
        if(getBindings().getAccount().getValue() != null){
        System.out.println("value of account================>"+getBindings().getAccount().getValue().toString().trim());
        }
        if(getBindings().getCardGroup().getValue() != null){
        System.out.println("value of card group================>"+getBindings().getCardGroup().getValue().toString().trim());
            cardGroupPassingValues =  populateStringValues(getBindings().getCardGroup().getValue().toString());
            populateCardGroupValues(cardGroupPassingValues);
        }
        if(getBindings().getCard().getValue() != null){
        System.out.println("value of card ================>"+getBindings().getCard().getValue().toString().trim());
            cardNumberPasingValues =  populateStringValues(getBindings().getCard().getValue().toString());
        }
        if(getBindings().getFromDate().getValue() != null){
            System.out.println("value of from date ================>"+getBindings().getFromDate().getValue());
            
            DateFormat   sdf = new SimpleDateFormat("dd-MMM-yy");
            Date effectiveDate= (java.util.Date)getBindings().getFromDate().getValue();
            newFromDate = sdf.format(effectiveDate);   
            System.out.println("value of new from date ================>"+newFromDate);
        }
        if(getBindings().getToDate().getValue() != null){
            System.out.println("value of to date ================>"+getBindings().getToDate().getValue());
            
            DateFormat   sdf1 = new SimpleDateFormat("dd-MMM-yy");
            Date effectiveDate1= (java.util.Date)getBindings().getToDate().getValue();
            newToDate = sdf1.format(effectiveDate1);
            System.out.println("value of new to date ================>"+newToDate);
        }
        System.out.println("value of terminal pass ================>"+terminalPassingValues);
        System.out.println("value of trans type ================>"+transTypePassingValues);
        System.out.println("value of card ================>"+cardNumberPasingValues);
        
        isTableVisible = false;
        ViewObject vo = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");
        if (cardIdPGL || vNumberPGL || dNamePGL) {
            System.out.println("Inside block for card");
            vo.setWhereClause("INSTR(:terminal,TERMINAL)<>0 AND  INSTR(:type,TRANSACTION_TYPE)<>0 AND INSTR(:card,KSID)<>0 AND PARTNER_ID = :partnerNumber AND ACCOUNT_ID = :accountId AND (TRANSACTION_DT between :fromDate AND :toDate)");
            vo.defineNamedWhereClauseParam("card", cardNumberPasingValues, null);
        }else{
            System.out.println("Coming inside card group block");
            vo.setWhereClause("INSTR(:terminal,TERMINAL)<>0 AND  INSTR(:type,TRANSACTION_TYPE)<>0 AND INSTR(:cardGrpMainType,CARDGROUP_MAIN_TYPE)<>0 AND INSTR(:cardgrpSubType,CARDGROUP_SUB_TYPE)<>0 AND INSTR(:cardGrpSeq,CARDGROUP_SEQ)<>0 AND PARTNER_ID = :partnerNumber AND ACCOUNT_ID = :accountId AND (TRANSACTION_DT between :fromDate AND :toDate)");
            vo.defineNamedWhereClauseParam("cardGrpMainType", cardGroupMaintypePassValue, null);
            vo.defineNamedWhereClauseParam("cardgrpSubType", cardGroupSubtypePassValues, null);
            vo.defineNamedWhereClauseParam("cardGrpSeq", cardGroupSeqPassValues, null);
        }
            vo.defineNamedWhereClauseParam("terminal", terminalPassingValues , null);
            vo.defineNamedWhereClauseParam("type", transTypePassingValues, null);
            vo.defineNamedWhereClauseParam("fromDate", newFromDate , null);
            vo.defineNamedWhereClauseParam("toDate", newToDate, null);
            //vo.defineNamedWhereClauseParam("countryCd", "no_NO", null);
            vo.defineNamedWhereClauseParam("partnerNumber", "26773218", null);
            vo.defineNamedWhereClauseParam("accountId", getBindings().getAccount().getValue().toString().trim(), null);
            vo.executeQuery();
            System.out.println("where clause of view object=====>"+vo.getWhereClause());
            if (vo.getEstimatedRowCount() != 0) {
                System.out.println("Inside Estimated row count" + vo.getEstimatedRowCount());
                for(int i=0;i<=vo.getEstimatedRowCount();i++ ){
                    Row rw = vo.getRowAtRangeIndex(i);
                        if(rw != null){               
                            Float temp = (Float)rw.getAttribute("InvoicedGrossAmount");
                            sum = sum + temp;
                        }
                            //                vo.getViewObject().next();
                }
                isTableVisible = true;
            } else {
                isTableVisible = false;
                if (resourceBundle.containsKey("NO_RECORDS_FOUND_TRANSACTIONS")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                         (String)resourceBundle.getObject("NO_RECORDS_FOUND_TRANSACTIONS"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
            System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
            if(cardIdPGL || vNumberPGL || dNamePGL){
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaa");
                    if("INSTR(:terminal,TERMINAL)<>0 AND  INSTR(:type,TRANSACTION_TYPE)<>0 AND INSTR(:card,KSID)<>0 AND PARTNER_ID = :partnerNumber AND ACCOUNT_ID = :accountId AND (TRANSACTION_DT between :fromDate AND :toDate)".equalsIgnoreCase(vo.getWhereClause())){
                    System.out.println("inside  card where removal class");
                        vo.removeNamedWhereClauseParam("card");
                        vo.removeNamedWhereClauseParam("terminal");
                        vo.removeNamedWhereClauseParam("type");
                        vo.removeNamedWhereClauseParam("fromDate");
                        vo.removeNamedWhereClauseParam("toDate");
                        vo.removeNamedWhereClauseParam("partnerNumber");
                        vo.removeNamedWhereClauseParam("accountId");
                        vo.setWhereClause("");
                        vo.executeQuery();
                }
            }else{
                    System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbb");
                    if("INSTR(:terminal,TERMINAL)<>0 AND  INSTR(:type,TRANSACTION_TYPE)<>0 AND INSTR(:cardGrpMainType,CARDGROUP_MAIN_TYPE)<>0 AND INSTR(:cardgrpSubType,CARDGROUP_SUB_TYPE)<>0 AND INSTR(:cardGrpSeq,CARDGROUP_SEQ)<>0 AND PARTNER_ID = :partnerNumber AND ACCOUNT_ID = :accountId AND (TRANSACTION_DT between :fromDate AND :toDate)".equalsIgnoreCase(vo.getWhereClause())){
                        System.out.println("inside  card group where removal class");
                        vo.removeNamedWhereClauseParam("cardGrpMainType");
                        vo.removeNamedWhereClauseParam("cardgrpSubType");
                        vo.removeNamedWhereClauseParam("cardGrpSeq");
                        vo.removeNamedWhereClauseParam("terminal");
                        vo.removeNamedWhereClauseParam("type");
                        vo.removeNamedWhereClauseParam("fromDate");
                        vo.removeNamedWhereClauseParam("toDate");
                        vo.removeNamedWhereClauseParam("partnerNumber");
                        vo.removeNamedWhereClauseParam("accountId");
                        vo.setWhereClause("");
                        vo.executeQuery();
                    }
                }
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
    
    public void populateCardGroupValues(String cardGrpVar){
        String[] cardGroupvalues;
        int cardGroupCount = 0;
        StringBuffer cardGroupMaintypeValues = new StringBuffer();
        StringBuffer cardGroupSubtypeValues  = new StringBuffer();
        StringBuffer cardGroupSeqValues      = new StringBuffer();
        
        String cardGroupMaintype = null;
        String cardGroupSubtype = null;
        String cardGroupSeq = null;
        
        cardGroupSubtypePassValues = null;
        cardGroupMaintypePassValue = null;
        cardGroupSeqPassValues     = null;
        
        if(cardGrpVar != null ){ 
            if(cardGrpVar.contains(",")){
                cardGroupvalues = cardGrpVar.split(",");
                cardGroupCount  = cardGroupvalues.length;
            }else{
                cardGroupCount  = 1;
                cardGroupvalues = new String[1];
                cardGroupvalues[0] = cardGrpVar;
            }
            
            for(int cGrp =0; cGrp < cardGroupCount; cGrp++){
                cardGroupMaintypeValues.append(cardGroupvalues[cGrp].substring(0,3));
                cardGroupMaintypeValues.append(",");
                
                cardGroupSubtypeValues.append(cardGroupvalues[cGrp].substring(3,6));
                cardGroupSubtypeValues.append(",");
                
                cardGroupSeqValues.append(cardGroupvalues[cGrp].substring(6));
                cardGroupSeqValues.append(",");
            }
                
                cardGroupMaintype = " " +cardGroupMaintypeValues;
                cardGroupSubtype  = " " +cardGroupSubtypeValues;
                cardGroupSeq      = " " +cardGroupSeqValues;
                
              cardGroupMaintypePassValue = cardGroupMaintype.trim().substring(0, cardGroupMaintype.length()-1);
              cardGroupSubtypePassValues = cardGroupSubtype.trim().substring(0, cardGroupSubtype.length()-1);
              cardGroupSeqPassValues     = cardGroupSeq.trim().substring(0, cardGroupSeq.length()-1);
              
              System.out.println("card group main type======>"+cardGroupMaintypePassValue);
              System.out.println("card group sub type===>"+cardGroupSubtypePassValues);
              System.out.println("card group sequence value====>"+cardGroupSeqPassValues);
        }
    }
    
    public void setTypeValue(List<String> typeValue) {
        this.typeValue = typeValue;
    }

    public List<String> getTypeValue() {
        return typeValue;
    }

    public void setCardNumberValue(List<String> cardNumberValue) {
        this.cardNumberValue = cardNumberValue;
    }

    public List<String> getCardNumberValue() {
        return cardNumberValue;
    }

    public void setCardGroupValue(List<String> cardGroupValue) {
        this.cardGroupValue = cardGroupValue;
    }

    public List<String> getCardGroupValue() {
        return cardGroupValue;
    }

    public void setVehicleNumberValue(List<String> vehicleNumberValue) {
        this.vehicleNumberValue = vehicleNumberValue;
    }

    public List<String> getVehicleNumberValue() {
        return vehicleNumberValue;
    }

    public void setDriverNameValue(List<String> driverNameValue) {
        this.driverNameValue = driverNameValue;
    }

    public List<String> getDriverNameValue() {
        return driverNameValue;
    }

    public void setTerminalValue(List<String> terminalValue) {
        this.terminalValue = terminalValue;
    }

    public List<String> getTerminalValue() {
        return terminalValue;
    }

    public void setIsTableVisible(Boolean isTableVisible) {
        this.isTableVisible = isTableVisible;
    }

    public Boolean getIsTableVisible() {
        return isTableVisible;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public Float getSum() {
        return sum;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setCardGroupSubtypePassValues(String cardGroupSubtypePassValues) {
        this.cardGroupSubtypePassValues = cardGroupSubtypePassValues;
    }

    public String getCardGroupSubtypePassValues() {
        return cardGroupSubtypePassValues;
    }

    public void setCardGroupMaintypePassValue(String cardGroupMaintypePassValue) {
        this.cardGroupMaintypePassValue = cardGroupMaintypePassValue;
    }

    public String getCardGroupMaintypePassValue() {
        return cardGroupMaintypePassValue;
    }

    public void setCardGroupSeqPassValues(String cardGroupSeqPassValues) {
        this.cardGroupSeqPassValues = cardGroupSeqPassValues;
    }

    public String getCardGroupSeqPassValues() {
        return cardGroupSeqPassValues;
    }

    public class Bindings {
        private RichSelectOneChoice account;
        private RichInputDate fromDate;
        private RichInputDate toDate;
        private RichSelectManyChoice card;
        private RichSelectManyChoice cardGroup;
        private RichSelectManyChoice driverName;
        private RichSelectManyChoice vehicleNumber;
        private RichSelectManyChoice terminalType;
        private RichSelectManyChoice transationType;
        private RichPanelGroupLayout vhNumberPGL;
        private RichPanelGroupLayout driverNamePGL;
        private RichPanelGroupLayout cardGroupPGL;
        private RichPanelGroupLayout cardNoPGL;
        

        public void setAccount(RichSelectOneChoice account) {
            this.account = account;
        }

        public RichSelectOneChoice getAccount() {
            return account;
        }

        public void setFromDate(RichInputDate fromDate) {
            this.fromDate = fromDate;
        }

        public RichInputDate getFromDate() {
            return fromDate;
        }

        public void setToDate(RichInputDate toDate) {
            this.toDate = toDate;
        }

        public RichInputDate getToDate() {
            return toDate;
        }
        
        public void setCard(RichSelectManyChoice card) {
            this.card = card;
        }

        public RichSelectManyChoice getCard() {
            return card;
        }

        public void setCardGroup(RichSelectManyChoice cardGroup) {
            this.cardGroup = cardGroup;
        }

        public RichSelectManyChoice getCardGroup() {
            return cardGroup;
        }

        public void setDriverName(RichSelectManyChoice driverName) {
            this.driverName = driverName;
        }

        public RichSelectManyChoice getDriverName() {
            return driverName;
        }

        public void setVehicleNumber(RichSelectManyChoice vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
        }

        public RichSelectManyChoice getVehicleNumber() {
            return vehicleNumber;
        }
        
        public void setTerminalType(RichSelectManyChoice terminalType) {
            this.terminalType = terminalType;
        }

        public RichSelectManyChoice getTerminalType() {
            return terminalType;
        }

        public void setTransationType(RichSelectManyChoice transationType) {
            this.transationType = transationType;
        }

        public RichSelectManyChoice getTransationType() {
            return transationType;
        }
        
        public void setVhNumberPGL(RichPanelGroupLayout vhNumberPGL) {
            this.vhNumberPGL = vhNumberPGL;
        }

        public RichPanelGroupLayout getVhNumberPGL() {
            return vhNumberPGL;
        }

        public void setDriverNamePGL(RichPanelGroupLayout driverNamePGL) {
            this.driverNamePGL = driverNamePGL;
        }

        public RichPanelGroupLayout getDriverNamePGL() {
            return driverNamePGL;
        }

        public void setCardGroupPGL(RichPanelGroupLayout cardGroupPGL) {
            this.cardGroupPGL = cardGroupPGL;
        }

        public RichPanelGroupLayout getCardGroupPGL() {
            return cardGroupPGL;
        }

        public void setCardNoPGL(RichPanelGroupLayout cardNoPGL) {
            this.cardNoPGL = cardNoPGL;
        }

        public RichPanelGroupLayout getCardNoPGL() {
            return cardNoPGL;
        }
    }
}
    
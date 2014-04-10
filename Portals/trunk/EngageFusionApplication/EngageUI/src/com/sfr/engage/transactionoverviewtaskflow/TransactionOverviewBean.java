package com.sfr.engage.transactionoverviewtaskflow;

import com.sfr.engage.core.AccountInfo;
import com.sfr.engage.core.CardGroupInfo;
import com.sfr.engage.core.PartnerInfo;

import com.sfr.engage.model.resources.EngageResourceBundle;

import com.sfr.util.ADFUtils;

import java.io.Serializable;

import java.util.ArrayList;

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
import oracle.adf.view.rich.context.AdfFacesContext;

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
   
    public TransactionOverviewBean() {
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        
        resourceBundle = new EngageResourceBundle();
        accountIdList  = new ArrayList<SelectItem>();
        terminalValue  = new ArrayList<String>();
        typeValue      = new ArrayList<String>();
        
        if(session.getAttribute("Partner_Object_List") != null){
            partnerInfo = (PartnerInfo)session.getAttribute("Partner_Object_List");
        }
        if(partnerInfo != null){
         String partnerId = partnerInfo.getPartnerValue().toString();  
         System.out.println("List of Account in partner info object=====>"+partnerInfo.getAccountList().size());
            if(partnerInfo.getAccountList().size() > 0){
                for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
                    System.out.println("value of Account Id===========>"+partnerInfo.getAccountList().get(i).getAccountNumber().toString());
                    SelectItem selectItem = new SelectItem();
                    selectItem.setLabel(partnerInfo.getAccountList().get(i).getAccountNumber().toString());
                    selectItem.setValue(partnerInfo.getAccountList().get(i).getAccountNumber().toString());
                    accountIdList.add(selectItem);
                }
            }
        }
        
        terminalValue.add("Home");
        terminalValue.add("External");
        
        typeValue.add("Preliminary");
        typeValue.add("Priced");
        typeValue.add("Invoice");
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
//        if (accountIdList == null) {
//            accountIdList = new ArrayList<SelectItem>();            
//            SelectItem selectItem = new SelectItem();
//            selectItem.setLabel("555");
//            selectItem.setValue("555");
//            accountIdList.add(selectItem);
//            SelectItem selectItem1 = new SelectItem();
//            selectItem1.setLabel("666");
//            selectItem1.setValue("666");
//            accountIdList.add(selectItem1);
//        }
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
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
            }else if(valueChangeEvent.getNewValue().equals("Card")){
                cardGPGL   = false;
                cardIdPGL    = true;
                dNamePGL   = false;
                vNumberPGL = false;
                populateValue(valueChangeEvent.getNewValue().toString());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
            }else if(valueChangeEvent.getNewValue().equals("Vehicle")){
                cardGPGL   = false;
                cardIdPGL    = false;
                dNamePGL   = false;
                vNumberPGL = true;
                populateValue(valueChangeEvent.getNewValue().toString());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleNumber());
            }else{
                cardGPGL   = false;
                cardIdPGL    = false;
                dNamePGL   = true;
                vNumberPGL = false;
                populateValue(valueChangeEvent.getNewValue().toString());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverName());
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
            selectItem.setLabel("Home");
            selectItem.setValue("Home");
            termianlList.add(selectItem);
            SelectItem selectItem1 = new SelectItem();
            selectItem1.setLabel("External");
            selectItem1.setValue("External");
            termianlList.add(selectItem1);
        }
        return termianlList;
    }

    public ArrayList<SelectItem> getTypeList() {
        if (typeList == null) {
            typeList = new ArrayList<SelectItem>();            
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel("Preliminary");
            selectItem.setValue("Preliminary");
            typeList.add(selectItem);
            SelectItem selectItem1 = new SelectItem();
            selectItem1.setLabel("Priced");
            selectItem1.setValue("Priced");
            typeList.add(selectItem1);
            SelectItem selectItem2 = new SelectItem();
            selectItem2.setLabel("Invoice");
            selectItem2.setValue("Invoice");
            typeList.add(selectItem2);
        }
        return typeList;
    }
    
    public void populateValue(String paramType){
        if(paramType != null){
            
            if(paramType.equals("CardGroup")){
                cardGroupList  = new ArrayList<SelectItem>();
                if(partnerInfo.getAccountList().size() > 0){
                    for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
                        System.out.println("Account Number inside select one radio button==========>"+partnerInfo.getAccountList().get(i).getAccountNumber());
                        if(partnerInfo.getAccountList().get(i).getAccountNumber().equals(getBindings().account.getValue())){
                            for(int k =0 ; k< partnerInfo.getAccountList().get(i).getCardGroup().size(); k++){
                                System.out.println("Card Group inside select one radio button==========>"+partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                if(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID()!= null){
                                SelectItem selectItem = new SelectItem();
                                selectItem.setLabel(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                selectItem.setValue(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                cardGroupList.add(selectItem);
                                }
                            }
                        }
                    }
                }
            }else if(paramType.equals("Card")){
                cardNumberList  = new ArrayList<SelectItem>();
                if(partnerInfo.getAccountList().size() > 0){
                    for(int i=0 ; i<partnerInfo.getAccountList().size(); i++){
                        if(partnerInfo.getAccountList().get(i).getAccountNumber().equals(getBindings().account.getValue())){
                            for(int k =0 ; k< partnerInfo.getAccountList().get(i).getCardGroup().size(); k++){
                                for(int m =0 ; m<partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().size(); m++){
                                    if(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID()!= null){
                                SelectItem selectItem = new SelectItem();
                                selectItem.setLabel(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID().toString());
                                selectItem.setValue(partnerInfo.getAccountList().get(i).getCardGroup().get(k).getCard().get(m).getCardID().toString());
                                cardNumberList.add(selectItem);
                                    }
                                }
                            }
                        }
                    }
                }
                    
            }else if(paramType.equals("Vehicle")){
                
            }else{
                
            }
        }
    }
    
    public void searchTransactionAction_event(ActionEvent actionEvent) {
        isTableVisible = false;
        Boolean card = true;
        if (card) {
            ViewObject vo =
                ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");
            vo.setWhereClause("trim(TERMINAL) =: terminal AND trim(TRANSACTION_TYPE) =: type AND trim(KSID) IN : card AND (TRANSACTION_DT between :fromDate AND :toDate)");

            vo.defineNamedWhereClauseParam("terminal", "EXTERNAL", null);
            vo.defineNamedWhereClauseParam("type", "PRI", null);
            vo.defineNamedWhereClauseParam("fromDate", "07-APR-14", null);
            vo.defineNamedWhereClauseParam("toDate", "10-APR-14", null);
            vo.defineNamedWhereClauseParam("card", "7458", null);
            vo.executeQuery();
            if (vo.getEstimatedRowCount() != 0) {
                System.out.println("EstimatedRowCount--> CARD" +
                                   vo.getEstimatedRowCount());
                isTableVisible = true;
            } else {
                isTableVisible = false;
                if (resourceBundle.containsKey("NO_RECORDS_FOUND_DRIVER")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                         (String)resourceBundle.getObject("NO_RECORDS_FOUND_TRANSACTIONS"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        } else {
            ViewObject vo =
                ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");
            vo.setWhereClause("trim(TERMINAL) =: terminal AND trim(TRANSACTION_TYPE) =: type AND trim(KSID) IN : card AND (TRANSACTION_DT between :fromDate AND :toDate)");

            vo.defineNamedWhereClauseParam("terminal", "EXTERNAL", null);
            vo.defineNamedWhereClauseParam("type", "PRI", null);
            vo.defineNamedWhereClauseParam("fromDate", "07-APR-14", null);
            vo.defineNamedWhereClauseParam("toDate", "10-APR-14", null);
            vo.defineNamedWhereClauseParam("card", "7458", null);
            vo.executeQuery();
            if (vo.getEstimatedRowCount() != 0) {
                System.out.println("EstimatedRowCount -->CARDGROUP" +
                                   vo.getEstimatedRowCount());
                isTableVisible = true;
            } else {
                isTableVisible = false;
                if (resourceBundle.containsKey("NO_RECORDS_FOUND_DRIVER")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                         (String)resourceBundle.getObject("NO_RECORDS_FOUND_DRIVER"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
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


    public class Bindings {
        private RichSelectOneChoice account;
        private RichInputDate fromDate;
        private RichInputDate toDate;
        private RichSelectManyChoice card;
        private RichSelectManyChoice cardGroup;
        private RichSelectManyChoice driverName;
        private RichSelectManyChoice vehicleNumber;

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
    }
}
    
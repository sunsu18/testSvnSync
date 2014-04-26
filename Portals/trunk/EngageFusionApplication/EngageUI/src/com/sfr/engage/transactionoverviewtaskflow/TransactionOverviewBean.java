package com.sfr.engage.transactionoverviewtaskflow;


import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.model.queries.rvo.PrtCardDriverVehicleInfoRVORowImpl;
import com.sfr.engage.model.queries.rvo.PrtCardTransactionOverviewRVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtPartnerVORowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;

import com.sfr.util.ADFUtils;

import com.sfr.util.constants.Constants;
import com.sfr.util.validations.Conversion;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import java.util.Locale;
import java.util.ResourceBundle;

import java.util.concurrent.TimeUnit;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectManyShuttle;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class TransactionOverviewBean implements Serializable{
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;    
    private transient Bindings bindings;
    
    private ArrayList<SelectItem> accountIdList; 
    private String accountIdValue;
    private ArrayList<SelectItem> termianlList;    
    private List shuttleList;
    private List shuttleValue;    
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
    private Locale locale;
    private String partnerCountry=null;
    Conversion conversionUtility;
    private String lang;
    private String currencyCode;
    private String strCardGroup="Date,Station,Country,Product,Vol,Total Amount,Receipt No,Invoice No";
    private String strCard="Date,Card,Station,Country,Product,Vol,Total Amount,Receipt No,Invoice No";
    private String strVehicle="Date,Vehicle No,Station,Country,Product,Vol,Total Amount,Receipt No,Invoice No,Odometer,TotalKM,KM/L,L/100KM";
    private String strDriver="Date,Driver Name,Station,Country,Product,Vol,Total Amount,Receipt No,Invoice No";
    


    public TransactionOverviewBean() {
        conversionUtility = new Conversion(); 
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        conversionUtility = new Conversion();
        resourceBundle = new EngageResourceBundle();
        accountIdList  = new ArrayList<SelectItem>();
        terminalValue  = new ArrayList<String>();
        typeValue      = new ArrayList<String>();
        partnerId      = null;
        
        if(session.getAttribute("Partner_Object_List") != null){
            partnerInfo = (PartnerInfo)session.getAttribute("Partner_Object_List");
        }
        
            if(partnerInfo != null){
            System.out.println("Inside partner info object");
            if(partnerInfo.getPartnerValue() != null){
                System.out.println("Inside partner info object value====>"+partnerInfo.getPartnerValue());
               partnerId = partnerInfo.getPartnerValue().toString(); 
               System.out.println("value of partner number========>"+partnerId);
               
               if(partnerInfo.getCountry() !=null){
               
                            partnerCountry=partnerInfo.getCountry().toString().substring(3);
                            
                
                }else{
                        partnerCountry="SE";
                    }
                        locale = conversionUtility.getLocaleFromCountryCode(partnerCountry);
            
         
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
            }
        
        terminalValue.add("HOME");
        terminalValue.add("EXTERNAL");
        
        typeValue.add("PRI");
        typeValue.add("PR");
        typeValue.add("INV");
        
        //lang=(String)session.getAttribute(Constants.SESSION_LANGUAGE);
        
        lang="no_NO";
        
        
        if(lang=="no_NO")
        {
        currencyCode=conversionUtility.getCurrencyCode("NO");
        locale=conversionUtility.getLocaleFromCountryCode("NO");
        }else if(lang=="se_SE") {
            currencyCode=conversionUtility.getCurrencyCode("SE");
            locale=conversionUtility.getLocaleFromCountryCode("SE");
        }
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
        if(getBindings().getAccount().getValue()!=null)
        {
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
                    cardIdPGL  = false;
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
        }else{
            showErrorMessage("ENGAGE_NO_ACCOUNT_CHECK");
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

    public List getShuttleList() {        
         return shuttleList;
       }
      
       public List getShuttleValue() {
         if (shuttleValue == null) {
           shuttleValue = new ArrayList<javax.faces.model.SelectItem>();
         }
         return shuttleValue;   
       }
      
       public void setShuttleValue(List shuttleValue) {
           this.shuttleValue = shuttleValue;
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
    
    public String searchTransactionAction_event(ActionEvent actionEvent) {
        sum=0.0f;
        String terminalPassingValues  = null;
        String transTypePassingValues = null;
        String cardNumberPasingValues = null ;
        String cardGroupPassingValues = null;
        String  newFromDate = null;
        String  newToDate = null;
        if(getBindings().getAccount().getValue() != null && getBindings().getFromDate().getValue() != null && getBindings().getToDate().getValue() != null){
            if(getBindings().getTerminalType().getValue() != null){
            System.out.println("value of terminal type================>"+getBindings().getTerminalType().getValue().toString().trim());
               terminalPassingValues =  populateStringValues(getBindings().getTerminalType().getValue().toString());
            }else{
                showErrorMessage("ENGAGE_NO_TERMINAL_TYPE");
                return null;
            }
            
            if(getBindings().getTransationType().getValue() != null){
            System.out.println("value of transaction type================>"+getBindings().getTransationType().getValue().toString().trim());
                transTypePassingValues =  populateStringValues(getBindings().getTransationType().getValue().toString());
            }else{
                showErrorMessage("ENGAGE_NO_TRANSACTION_TYPE");
                return null;
            }
            
            if(getBindings().getFromDate().getValue() != null && getBindings().getToDate().getValue() != null){
                DateFormat   sdf = new SimpleDateFormat("dd-MMM-yy");
                Date effectiveFromDate= (java.util.Date)getBindings().getFromDate().getValue();
                Date effectiveToDate1= (java.util.Date)getBindings().getToDate().getValue();
                newFromDate = sdf.format(effectiveFromDate);   
                newToDate   = sdf.format(effectiveToDate1);
                
                if (effectiveToDate1.before(effectiveFromDate)) {
                System.out.println("value of new from date ================>"+newFromDate);
                    showErrorMessage("ENGAGE_VALID_FROM_TO_DATE");
                    return null;
                }
            }
        
            if(cardGPGL){
                if(getBindings().getCardGroup().getValue() != null){
                System.out.println("value of card group================>"+getBindings().getCardGroup().getValue().toString().trim());
                    cardGroupPassingValues =  populateStringValues(getBindings().getCardGroup().getValue().toString());
                    populateCardGroupValues(cardGroupPassingValues);
                }
                else{
                    showErrorMessage("ENGAGE_NO_CARD_GROUP");
                    return null;
                }
            }
            
            System.out.println("bollean value of vehicle====>"+vNumberPGL+dNamePGL);
            
            if(cardIdPGL ){
                if(getBindings().getCard().getValue() != null){
                System.out.println("value of card  for card Id================>"+getBindings().getCard().getValue().toString().trim());
                    cardNumberPasingValues =  populateStringValues(getBindings().getCard().getValue().toString());
                }else{
                    showErrorMessage("ENGAGE_NO_CARD");
                    return null;
                }
            }
            
            if(vNumberPGL){
                if(getBindings().getVehicleNumber().getValue() != null){
                System.out.println("value of card  for vehicle ================>"+getBindings().getVehicleNumber().getValue().toString().trim());
                    cardNumberPasingValues =  populateStringValues(getBindings().getVehicleNumber().getValue().toString());
                }else{
                    System.out.println("Is it coming inside vehicle else number block");
                    showErrorMessage("ENGAGE_NO_VEHICLE");
                    return null;
                }
            }
            
            if(dNamePGL){
                if(getBindings().getDriverName().getValue() != null){
                System.out.println("value of card driver================>"+getBindings().getDriverName().getValue().toString().trim());
                    cardNumberPasingValues =  populateStringValues(getBindings().getDriverName().getValue().toString());
                }else{
                    showErrorMessage("ENGAGE_NO_DRIVER");
                    return null;
                }
            }
            
            
//            if(getBindings().getToDate().getValue() == null){
//                showErrorMessage("ENGAGE_NO_FROM_DATE");
//            }
//            
//            if(getBindings().getToDate().getValue() == null){
//                showErrorMessage("ENGAGE_NO_TO_DATE");
//            }
        System.out.println("value of terminal pass ================>"+terminalPassingValues);
        System.out.println("value of trans type ================>"+transTypePassingValues);
        System.out.println("value of card111111 ================>"+cardNumberPasingValues);
        
        isTableVisible = false;
        ViewObject vo = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");
        vo.setNamedWhereClauseParam("accountId", getBindings().getAccount().getValue().toString().trim());
        vo.setNamedWhereClauseParam("countryCd", "no_NO");
        
            if("INSTR(:terminal,TERMINAL)<>0 AND  INSTR(:type,TRANSACTION_TYPE)<>0 AND INSTR(:card,KSID)<>0 AND PARTNER_ID = :partnerNumber AND TRANSACTION_DT >= :fromDate AND TRANSACTION_DT <= :toDate".equalsIgnoreCase(vo.getWhereClause())){
            System.out.println("inside  card where removal class");
                vo.removeNamedWhereClauseParam("card");
                vo.removeNamedWhereClauseParam("terminal");
                vo.removeNamedWhereClauseParam("type");
                vo.removeNamedWhereClauseParam("fromDate");
                vo.removeNamedWhereClauseParam("toDate");
                vo.removeNamedWhereClauseParam("partnerNumber");
                //vo.removeNamedWhereClauseParam("accountId");
                vo.setWhereClause("");
                vo.executeQuery();
                System.out.println("count of vo=====>"+vo.getEstimatedRowCount());
            }else{
                if("INSTR(:terminal,TERMINAL)<>0 AND  INSTR(:type,TRANSACTION_TYPE)<>0 AND INSTR(:cardGrpMainType,CARDGROUP_MAIN_TYPE)<>0 AND INSTR(:cardgrpSubType,CARDGROUP_SUB_TYPE)<>0 AND INSTR(:cardGrpSeq,CARDGROUP_SEQ)<>0 AND PARTNER_ID = :partnerNumber AND TRANSACTION_DT >= :fromDate AND TRANSACTION_DT <= :toDate".equalsIgnoreCase(vo.getWhereClause())){
                    System.out.println("inside  card group where removal class");
                    vo.removeNamedWhereClauseParam("cardGrpMainType");
                    vo.removeNamedWhereClauseParam("cardgrpSubType");
                    vo.removeNamedWhereClauseParam("cardGrpSeq");
                    vo.removeNamedWhereClauseParam("terminal");
                    vo.removeNamedWhereClauseParam("type");
                    vo.removeNamedWhereClauseParam("fromDate");
                    vo.removeNamedWhereClauseParam("toDate");
                    vo.removeNamedWhereClauseParam("partnerNumber");
                    //vo.removeNamedWhereClauseParam("accountId");
                    vo.setWhereClause("");
                    vo.executeQuery();
                    System.out.println("count of vo1111111=====>"+vo.getEstimatedRowCount());
                }
            }
        
        if (cardIdPGL || vNumberPGL || dNamePGL) {
            System.out.println("Inside block for card");
            vo.setWhereClause("INSTR(:terminal,TERMINAL)<>0 AND  INSTR(:type,TRANSACTION_TYPE)<>0 AND INSTR(:card,KSID)<>0 AND PARTNER_ID = :partnerNumber AND TRANSACTION_DT >= :fromDate AND TRANSACTION_DT <= :toDate");
            vo.defineNamedWhereClauseParam("card", cardNumberPasingValues, null);
        }else{
            System.out.println("Coming inside card group block");
            vo.setWhereClause("INSTR(:terminal,TERMINAL)<>0 AND  INSTR(:type,TRANSACTION_TYPE)<>0 AND INSTR(:cardGrpMainType,CARDGROUP_MAIN_TYPE)<>0 AND INSTR(:cardgrpSubType,CARDGROUP_SUB_TYPE)<>0 AND INSTR(:cardGrpSeq,CARDGROUP_SEQ)<>0 AND PARTNER_ID = :partnerNumber AND TRANSACTION_DT >= :fromDate AND TRANSACTION_DT <= :toDate");
            vo.defineNamedWhereClauseParam("cardGrpMainType", cardGroupMaintypePassValue, null);
            vo.defineNamedWhereClauseParam("cardgrpSubType", cardGroupSubtypePassValues, null);
            vo.defineNamedWhereClauseParam("cardGrpSeq", cardGroupSeqPassValues, null);
        }
            vo.defineNamedWhereClauseParam("terminal", terminalPassingValues , null);
            vo.defineNamedWhereClauseParam("type", transTypePassingValues, null);
            vo.defineNamedWhereClauseParam("fromDate", newFromDate , null);
            vo.defineNamedWhereClauseParam("toDate", newToDate, null);
            //vo.defineNamedWhereClauseParam("countryCd", "no_NO", null);
            vo.defineNamedWhereClauseParam("partnerNumber", partnerId, null);
            //vo.defineNamedWhereClauseParam("accountId", getBindings().getAccount().getValue().toString().trim(), null);
            vo.executeQuery();
            isTableVisible = true;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowSearchResultPG());
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
        }else{
            showErrorMessage("ENGAGE_SELECT_TRANSACTION_MANDATORY");
            return null;
        }
        return null;
    }
    
    public void clearSearchListener(ActionEvent actionEvent) {
        // Add event code here...
        getBindings().getAccount().setValue(null);
        this.accountIdValue=null;
        getBindings().getCardCardGrpDrVhOneRadio().setValue(null);
        getBindings().getFromDate().setValue(null);
        getBindings().getToDate().setValue(null);
        isTableVisible=false;
        cardIdPGL     =false;
        cardGPGL      =false;
        vNumberPGL    =false;
        dNamePGL      =false; 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());  
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFromDate());  
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getToDate()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowSearchResultPG());
    }
    
    public String showErrorMessage(String errorVar){
        if(errorVar != null){
            if (resourceBundle.containsKey(errorVar)) {                    
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     (String)resourceBundle.getObject(errorVar),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null, msg);  
                return null;
            }
        }
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
    
    public void populateCardGroupValues(String cardGrpVar){
        String[] cardGroupvalues;
        int cardGroupCount = 0;
        
        String cardGroupMaintype = "";
        String cardGroupSubtype = "";
        String cardGroupSeq = "";
        
        cardGroupSubtypePassValues = "";
        cardGroupMaintypePassValue = "";
        cardGroupSeqPassValues     = "";
        
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
                cardGroupMaintype=cardGroupMaintype+cardGroupvalues[cGrp].trim().substring(0,3);  
                cardGroupMaintype=cardGroupMaintype+",";
                
                cardGroupSubtype=cardGroupSubtype+cardGroupvalues[cGrp].trim().substring(3,6);  
                cardGroupSubtype=cardGroupSubtype+",";
                
                cardGroupSeq=cardGroupSeq+cardGroupvalues[cGrp].trim().substring(6);  
                cardGroupSeq=cardGroupSeq+",";
            }
                
                cardGroupMaintypePassValue = cardGroupMaintype.trim().substring(0, cardGroupMaintype.length()-1);
                cardGroupSubtypePassValues = cardGroupSubtype.trim().substring(0, cardGroupSubtype.length()-1);
                cardGroupSeqPassValues     = cardGroupSeq.trim().substring(0, cardGroupSeq.length()-1);
              
              System.out.println("card group main type======>"+cardGroupMaintypePassValue);
              System.out.println("card group sub type===>"+cardGroupSubtypePassValues);
              System.out.println("card group sequence value====>"+cardGroupSeqPassValues);
        }
    }
    
    public void accountValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue()!=null) {
            getBindings().getCardCardGrpDrVhOneRadio().setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardCardGrpDrVhOneRadio());  
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

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
    
    
    public String formatConversion(Date date) {
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }
    
    public String checkALL(String selectedValues,String type) {
        String val="";
        String[] listValues=selectedValues.split(",");   
        if(listValues.length>1) {
        if("Terminal".equalsIgnoreCase(type)) {
            if(termianlList.size()  == listValues.length) {
                val="ALL";
            }else {
                val=selectedValues;
            }
        }else if("Type".equalsIgnoreCase(type)) {
            if(typeList.size()==listValues.length) {
                val="ALL";
            }else {
                val=selectedValues;
            }
        }
            
        }else {
            val=selectedValues;
        }
        
        return val;
    }
              
    public String formatConversion(Float passedValue,Locale countryLocale) {
        String val="";
        
        NumberFormat numberFormat = NumberFormat.getInstance(countryLocale);
        val = numberFormat.format(passedValue);        
        return val;
    }
    
    public String getTimeHour(Timestamp timeStamp) {
        String val="";        
        java.util.Date date = new Date(timeStamp.getTime());
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        val=format.format(date);  
        return val;
    }

    public void exportToExcelListener(FacesContext facesContext,
                                      OutputStream outputStream) throws IOException {
        // Add event code here...
        String partnerCompanyName="";
        ViewObject partnerVO = ADFUtils.getViewObject("PrtPartnerVO1Iterator");
        partnerVO.setWhereClause("PARTNER_ID =: partnerId");
        partnerVO.defineNamedWhereClauseParam("partnerId", partnerId,null);
        partnerVO.executeQuery();
        if (partnerVO.getEstimatedRowCount() != 0) {
            while(partnerVO.hasNext()) {
                PrtPartnerVORowImpl row=(PrtPartnerVORowImpl)partnerVO.next();
                partnerCompanyName=row.getFirstLastName();
            }
        }        
        
        HSSFWorkbook XLS = new HSSFWorkbook();
        HSSFRow XLS_SH_R=null;
        HSSFCell XLS_SH_R_C=null;
        int intRow=0;
        HSSFCellStyle cs = XLS.createCellStyle();
        HSSFFont f =XLS.createFont();
        
        //create sheet
        HSSFSheet XLS_SH=XLS.createSheet();
        XLS.setSheetName(0,"TransactionReport");
        
        f.setFontHeightInPoints((short) 10);
        f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);        
        f.setColor((short)0);
        cs.setFont(f);
        
        HSSFCellStyle csRight = XLS.createCellStyle();
        HSSFFont fnumberData =XLS.createFont();
        fnumberData.setFontHeightInPoints((short) 10);        
        fnumberData.setColor((short)0);        
        csRight.setFont(fnumberData);
        csRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT );
        
        HSSFCellStyle csTotalAmt = XLS.createCellStyle();
        HSSFFont fontTotal =XLS.createFont();
        fontTotal.setFontHeightInPoints((short) 10);        
        fontTotal.setColor((short)0);    
        fontTotal.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);        
        csTotalAmt.setFont(fontTotal);
        csTotalAmt.setAlignment(HSSFCellStyle.ALIGN_RIGHT );
        
        HSSFCellStyle csData = XLS.createCellStyle();
        HSSFFont fData =XLS.createFont();
        fData.setFontHeightInPoints((short) 10);            
        fData.setColor((short)0);
        csData.setFont(fData);        
       
       
        XLS_SH.setColumnWidth(50, 50);
        XLS_SH_R=XLS_SH.createRow(0);
        XLS_SH_R_C=XLS_SH_R.createCell(0);
        XLS_SH_R_C.setCellStyle(cs);        
        XLS_SH_R_C.setCellValue("Company: "+partnerCompanyName);
              
       
        
        XLS_SH_R= XLS_SH.createRow(1);
        XLS_SH_R_C=XLS_SH_R.createCell(0);
        XLS_SH_R_C.setCellStyle(cs);        
        XLS_SH_R_C.setCellValue("Account: "+getBindings().getAccount().getValue().toString()+partnerCompanyName);  
        
        
        
        XLS_SH_R= XLS_SH.createRow(2);
        XLS_SH_R_C=XLS_SH_R.createCell(0);
        XLS_SH_R_C.setCellStyle(cs);        
        XLS_SH_R_C.setCellValue("Terminal: "+checkALL((populateStringValues(getBindings().getTerminalType().getValue().toString())),"Terminal")); 
        
        XLS_SH_R= XLS_SH.createRow(3);
        XLS_SH_R_C=XLS_SH_R.createCell(0);
        XLS_SH_R_C.setCellStyle(cs);        
        XLS_SH_R_C.setCellValue("Type: "+checkALL((populateStringValues(getBindings().getTransationType().getValue().toString())),"Type"));        
       
        
        XLS_SH_R= XLS_SH.createRow(4);
        XLS_SH_R_C=XLS_SH_R.createCell(0);
        XLS_SH_R_C.setCellStyle(cs);        
        XLS_SH_R_C.setCellValue("Period: "+formatConversion((Date)getBindings().getFromDate().getValue())+" to "+formatConversion((Date)getBindings().getToDate().getValue()));
        
        for(int row=5;row<=7;row++) {
            XLS_SH_R= XLS_SH.createRow(row);            
        }                  
       
        
        XLS_SH_R= XLS_SH.createRow(8);       
        XLS_SH_R_C=XLS_SH_R.createCell(5);
        XLS_SH_R_C.setCellStyle(cs);  
        XLS_SH_R_C.setCellValue("*Note : All prices below are in "+"NOK");
        
        if(getBindings().getCardCardGrpDrVhOneRadio().getValue()!=null) {            
            if("CardGroup".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {                
                String[] strHead=strCardGroup.split(",");                
                HSSFCellStyle css = XLS.createCellStyle();
                HSSFFont fcss =XLS.createFont();
                fcss.setFontHeightInPoints((short) 10);
                fcss.setItalic(true);        
                fcss.setColor((short)0);
                css.setFont(fcss);
                XLS_SH_R= XLS_SH.createRow(9);
                for (int col = 0; col < strHead.length; col++)
                {
                XLS_SH_R_C =XLS_SH_R.createCell(col);
                XLS_SH_R_C.setCellStyle(css);
                XLS_SH_R_C.setCellValue(strHead[col].toString());
                }
                int rowVal=9;                
                
                ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");                
                RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);                      
                iterator.reset();
                while (iterator.hasNext()) {                       
                  PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();
                    rowVal=rowVal+1;
                    XLS_SH_R= XLS_SH.createRow(rowVal);
                    if(row!=null) {
                    XLS_SH_R_C=XLS_SH_R.createCell(0);
                    XLS_SH_R_C.setCellStyle(csData);
                    if(row.getTransactionDt()!=null)
                    {       
                        String time="";
                        if(row.getTransactionTime()!=null) {
                            time=getTimeHour(row.getTransactionTime());
                        }
                    XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().getTime()))+time);
                    }
                    XLS_SH_R_C=XLS_SH_R.createCell(1);
                    XLS_SH_R_C.setCellStyle(csData);
                    if(row.getStationName()!=null)
                    {
                    XLS_SH_R_C.setCellValue(row.getStationName().toString());
                    }
                    XLS_SH_R_C=XLS_SH_R.createCell(2);
                    XLS_SH_R_C.setCellStyle(csData);
                    if(row.getPurchaseCountryCode()!=null)
                    {
                    XLS_SH_R_C.setCellValue(row.getPurchaseCountryCode().toString());
                    }
                    XLS_SH_R_C=XLS_SH_R.createCell(3);
                    XLS_SH_R_C.setCellStyle(csData);
                    if(row.getProductName()!=null)
                    {
                    XLS_SH_R_C.setCellValue(row.getProductName().toString());
                    }
                    XLS_SH_R_C=XLS_SH_R.createCell(4);                    
                    XLS_SH_R_C.setCellStyle(csRight);
                    if(row.getQuantity()!=null)
                    {
                    
                    XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale));
                    }
                    XLS_SH_R_C=XLS_SH_R.createCell(5);                    
                    XLS_SH_R_C.setCellStyle(csRight);
                    if(row.getInvoicedGrossAmount()!=null)
                    {
                        //System.out.println("Locale ="+formatConversion(row.getInvoicedGrossAmount(),locale));                        
                    XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmount(),locale));
                    }
                    XLS_SH_R_C=XLS_SH_R.createCell(6);
                    XLS_SH_R_C.setCellStyle(csData);
                    if(row.getRecieptNo()!=null)
                    {
                    XLS_SH_R_C.setCellValue(row.getRecieptNo().toString());
                    }
                    XLS_SH_R_C=XLS_SH_R.createCell(7);
                    XLS_SH_R_C.setCellStyle(csData);
                    if(row.getInvoiceNumberCollective()!=null) {
                     XLS_SH_R_C.setCellValue(row.getInvoiceNumberCollective().toString());
                     }else {
                        if(row.getInvoiceNumberNonCollective()!=null) {
                        XLS_SH_R_C.setCellValue(row.getInvoiceNumberNonCollective().toString());
                             }
                         }   
                     }
                
                }

               
                //rowVal=rowVal+1;
                XLS_SH_R= XLS_SH.createRow(++rowVal);        
                XLS_SH_R= XLS_SH.createRow(++rowVal);        
                
                XLS_SH_R= XLS_SH.createRow(++rowVal);
                XLS_SH_R_C=XLS_SH_R.createCell(0);
                XLS_SH_R_C.setCellStyle(cs);        
                XLS_SH_R_C.setCellValue("Total Price");
                XLS_SH_R_C=XLS_SH_R.createCell(5);               
                XLS_SH_R_C.setCellStyle(csTotalAmt);  
                if(sum!=null)
                {
                XLS_SH_R_C.setCellValue(formatConversion(sum,locale));
                }
                 iterator.closeRowSetIterator();   
                
            }else if("Card".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())){                
                String[] strHead=strCard.split(",");                
                HSSFCellStyle css = XLS.createCellStyle();
                HSSFFont fcss =XLS.createFont();
                fcss.setFontHeightInPoints((short) 10);
                fcss.setItalic(true);        
                fcss.setColor((short)0);
                css.setFont(fcss);
                XLS_SH_R= XLS_SH.createRow(9);
                for (int col = 0; col < strHead.length; col++)
                {
                XLS_SH_R_C =XLS_SH_R.createCell(col);
                XLS_SH_R_C.setCellStyle(css);
                XLS_SH_R_C.setCellValue(strHead[col].toString());
                }
                
                int rowVal=9;                
                
                ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");                
                RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);                      
                iterator.reset();
                while (iterator.hasNext()) {                        
                  PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();
                    rowVal=rowVal+1;
                    XLS_SH_R= XLS_SH.createRow(rowVal);
                    if(row!=null) {
                    XLS_SH_R_C=XLS_SH_R.createCell(0);
                    XLS_SH_R_C.setCellStyle(csData);
                         if(row.getTransactionDt()!=null)
                         {
                             String time="";
                             if(row.getTransactionTime()!=null) {
                                 time=getTimeHour(row.getTransactionTime());
                             }
                             XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().getTime()))+time);
                         }
                    XLS_SH_R_C=XLS_SH_R.createCell(1);
                    XLS_SH_R_C.setCellStyle(csData);
                         if(row.getKsid()!=null)
                         {
                    XLS_SH_R_C.setCellValue(row.getKsid().toString());
                         }
                    XLS_SH_R_C=XLS_SH_R.createCell(2);
                    XLS_SH_R_C.setCellStyle(csData);
                         if(row.getStationName()!=null)
                         {
                    XLS_SH_R_C.setCellValue(row.getStationName().toString());
                         }
                    XLS_SH_R_C=XLS_SH_R.createCell(3);
                    XLS_SH_R_C.setCellStyle(csData);
                         if(row.getPurchaseCountryCode()!=null)
                         {
                    XLS_SH_R_C.setCellValue(row.getPurchaseCountryCode().toString());
                         }
                    XLS_SH_R_C=XLS_SH_R.createCell(4);
                    XLS_SH_R_C.setCellStyle(csData);
                         if(row.getProductName()!=null)
                         {
                    XLS_SH_R_C.setCellValue(row.getProductName().toString());
                         }
                    XLS_SH_R_C=XLS_SH_R.createCell(5);
                    XLS_SH_R_C.setCellStyle(csRight);
                         if(row.getQuantity()!=null)
                         {
                             XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale));
                         }
                    XLS_SH_R_C=XLS_SH_R.createCell(6);
                    XLS_SH_R_C.setCellStyle(csRight);
                         if(row.getInvoicedGrossAmount()!=null)
                         {
                             XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmount(),locale));
                         }
                    XLS_SH_R_C=XLS_SH_R.createCell(7);
                    XLS_SH_R_C.setCellStyle(csData);
                         if(row.getRecieptNo()!=null)
                         {
                    XLS_SH_R_C.setCellValue(row.getRecieptNo().toString());
                         }
                    XLS_SH_R_C=XLS_SH_R.createCell(8);
                    XLS_SH_R_C.setCellStyle(csData);
                    if(row.getInvoiceNumberCollective()!=null) {
                     XLS_SH_R_C.setCellValue(row.getInvoiceNumberCollective().toString());
                     }else {
                        if(row.getInvoiceNumberNonCollective()!=null) {
                        XLS_SH_R_C.setCellValue(row.getInvoiceNumberNonCollective().toString());
                             }
                         }   
                     }
                
                }

                
                //rowVal=rowVal+1;
                XLS_SH_R= XLS_SH.createRow(++rowVal);        
                XLS_SH_R= XLS_SH.createRow(++rowVal);        
                
                XLS_SH_R= XLS_SH.createRow(++rowVal);
                XLS_SH_R_C=XLS_SH_R.createCell(0);
                XLS_SH_R_C.setCellStyle(cs);        
                XLS_SH_R_C.setCellValue("Total Price");
                XLS_SH_R_C=XLS_SH_R.createCell(6);
                XLS_SH_R_C.setCellStyle(csTotalAmt);  
                if(sum!=null)
                {
                    XLS_SH_R_C.setCellValue(formatConversion(sum,locale));
                }
                 iterator.closeRowSetIterator();   
                
            }else if("Vehicle".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())){               
                String[] strHead=strVehicle.split(",");                
                HSSFCellStyle css = XLS.createCellStyle();
                HSSFFont fcss =XLS.createFont();
                fcss.setFontHeightInPoints((short) 10);
                fcss.setItalic(true);        
                fcss.setColor((short)0);
                css.setFont(fcss);
                XLS_SH_R= XLS_SH.createRow(9);
                for (int col = 0; col < strHead.length; col++)
                {
                XLS_SH_R_C =XLS_SH_R.createCell(col);
                XLS_SH_R_C.setCellStyle(css);
                XLS_SH_R_C.setCellValue(strHead[col].toString());
                }
                int rowVal=9;
                
                ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");                
                RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);                      
                iterator.reset();
                while (iterator.hasNext()) {                        
                  PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();
                    rowVal=rowVal+1;
                    XLS_SH_R= XLS_SH.createRow(rowVal);
                    if(row!=null) {
                    XLS_SH_R_C=XLS_SH_R.createCell(0);
                    XLS_SH_R_C.setCellStyle(csData);
                      if(row.getTransactionDt()!=null)
                      {
                          String time="";
                          if(row.getTransactionTime()!=null) {
                              time=getTimeHour(row.getTransactionTime());
                          }
                          XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().getTime()))+time);
                      }
                    XLS_SH_R_C=XLS_SH_R.createCell(1);
                    XLS_SH_R_C.setCellStyle(csData);
                      if(row.getVehicleNumber()!=null)
                      {
                    XLS_SH_R_C.setCellValue(row.getVehicleNumber().toString());
                      }
                    XLS_SH_R_C=XLS_SH_R.createCell(2);
                    XLS_SH_R_C.setCellStyle(csData);
                      if(row.getStationName()!=null)
                      {
                    XLS_SH_R_C.setCellValue(row.getStationName().toString());
                      }
                    XLS_SH_R_C=XLS_SH_R.createCell(3);
                    XLS_SH_R_C.setCellStyle(csData);
                      if(row.getPurchaseCountryCode()!=null)
                      {
                    XLS_SH_R_C.setCellValue(row.getPurchaseCountryCode().toString());
                      }
                    XLS_SH_R_C=XLS_SH_R.createCell(4);
                    XLS_SH_R_C.setCellStyle(csData);
                      if(row.getProductName()!=null)
                      {
                    XLS_SH_R_C.setCellValue(row.getProductName().toString());
                      }
                    XLS_SH_R_C=XLS_SH_R.createCell(5);
                    XLS_SH_R_C.setCellStyle(csRight);
                      if(row.getQuantity()!=null)
                      {
                          XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale));
                      }
                    XLS_SH_R_C=XLS_SH_R.createCell(6);
                    XLS_SH_R_C.setCellStyle(csRight);
                      if(row.getInvoicedGrossAmount()!=null)
                      {
                          XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmount(),locale));
                      }
                    XLS_SH_R_C=XLS_SH_R.createCell(7);
                    XLS_SH_R_C.setCellStyle(csData);
                      if(row.getRecieptNo()!=null)
                      {
                    XLS_SH_R_C.setCellValue(row.getRecieptNo().toString());
                      }
                    XLS_SH_R_C=XLS_SH_R.createCell(8);
                    XLS_SH_R_C.setCellStyle(csData);                    
                    if(row.getInvoiceNumberCollective()!=null) {
                     XLS_SH_R_C.setCellValue(row.getInvoiceNumberCollective().toString());
                     }else {
                        if(row.getInvoiceNumberNonCollective()!=null) {
                        XLS_SH_R_C.setCellValue(row.getInvoiceNumberNonCollective().toString());
                             }
                         }                      
                    XLS_SH_R_C=XLS_SH_R.createCell(9);
                    XLS_SH_R_C.setCellStyle(csRight);
                    if(row.getOdometerPortal()!=null) {
                        XLS_SH_R_C.setCellValue(row.getOdometerPortal().toString());
                    }else {                        
                        if(row.getOdometer()!=null) {
                        XLS_SH_R_C.setCellValue(row.getOdometer().toString());
                             }                        
                    }
                    
                      XLS_SH_R_C=XLS_SH_R.createCell(10);
                      XLS_SH_R_C.setCellStyle(csRight); 
                      if(row.getkmTotal()!=null)
                      {
                      XLS_SH_R_C.setCellValue(row.getkmTotal().toString());
                      }
                      
                      XLS_SH_R_C=XLS_SH_R.createCell(11);
                      XLS_SH_R_C.setCellStyle(csRight);   
                      if(row.getkmPerLt()!=null)
                      {
                      XLS_SH_R_C.setCellValue(row.getkmPerLt().toString());
                      }
                      
                      XLS_SH_R_C=XLS_SH_R.createCell(12);
                      XLS_SH_R_C.setCellStyle(csRight);  
                      if(row.getltPerHundred()!=null)
                      {
                      XLS_SH_R_C.setCellValue(row.getltPerHundred().toString());                    
                      }
                    
                  }
                
                }

                
                //rowVal=rowVal+1;
                XLS_SH_R= XLS_SH.createRow(++rowVal);        
                XLS_SH_R= XLS_SH.createRow(++rowVal);        
                
                XLS_SH_R= XLS_SH.createRow(++rowVal);
                XLS_SH_R_C=XLS_SH_R.createCell(0);
                XLS_SH_R_C.setCellStyle(cs);        
                XLS_SH_R_C.setCellValue("Total Price");
                XLS_SH_R_C=XLS_SH_R.createCell(6);
                XLS_SH_R_C.setCellStyle(csTotalAmt);  
                if(sum!=null)
                {
                    XLS_SH_R_C.setCellValue(formatConversion(sum,locale));
                }
                 iterator.closeRowSetIterator();   
            }else {               
                String[] strHead=strDriver.split(",");                
                HSSFCellStyle css = XLS.createCellStyle();
                HSSFFont fcss =XLS.createFont();
                fcss.setFontHeightInPoints((short) 10);
                fcss.setItalic(true);        
                fcss.setColor((short)0);
                css.setFont(fcss);
                XLS_SH_R= XLS_SH.createRow(9);
                for (int col = 0; col < strHead.length; col++)
                {
                XLS_SH_R_C =XLS_SH_R.createCell(col);
                XLS_SH_R_C.setCellStyle(css);
                XLS_SH_R_C.setCellValue(strHead[col].toString());
                }
                
                int rowVal=9;
                
                ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");                
                RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);                      
                iterator.reset();
                while (iterator.hasNext()) {                       
                  PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();
                    rowVal=rowVal+1;
                    XLS_SH_R= XLS_SH.createRow(rowVal);
                    if(row!=null) {
                    XLS_SH_R_C=XLS_SH_R.createCell(0);
                    XLS_SH_R_C.setCellStyle(csData);
                         if(row.getTransactionDt()!=null)
                         {
                             String time="";
                             if(row.getTransactionTime()!=null) {
                                 time=getTimeHour(row.getTransactionTime());
                             }
                             XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().getTime()))+time);
                         }
                         XLS_SH_R_C=XLS_SH_R.createCell(1);
                         XLS_SH_R_C.setCellStyle(csData);
                         if(row.getDriverName()!=null)
                         {
                         XLS_SH_R_C.setCellValue(row.getDriverName().toString());
                         }
                    XLS_SH_R_C=XLS_SH_R.createCell(2);
                    XLS_SH_R_C.setCellStyle(csData);
                         if(row.getStationName()!=null)
                         {
                    XLS_SH_R_C.setCellValue(row.getStationName().toString());
                         }
                    XLS_SH_R_C=XLS_SH_R.createCell(3);
                    XLS_SH_R_C.setCellStyle(csData);
                         if(row.getPurchaseCountryCode()!=null)
                         {
                    XLS_SH_R_C.setCellValue(row.getPurchaseCountryCode().toString());
                         }
                    XLS_SH_R_C=XLS_SH_R.createCell(4);
                    XLS_SH_R_C.setCellStyle(csData);
                         if(row.getProductName()!=null)
                         {
                    XLS_SH_R_C.setCellValue(row.getProductName().toString());
                         }
                    XLS_SH_R_C=XLS_SH_R.createCell(5);
                    XLS_SH_R_C.setCellStyle(csRight);
                         if(row.getQuantity()!=null)
                         {
                             XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale));
                         }
                    XLS_SH_R_C=XLS_SH_R.createCell(6);
                    XLS_SH_R_C.setCellStyle(csRight);
                         if(row.getInvoicedGrossAmount()!=null)
                         {
                             XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmount(),locale));
                         }
                    XLS_SH_R_C=XLS_SH_R.createCell(7);
                    XLS_SH_R_C.setCellStyle(csData);
                         if(row.getRecieptNo()!=null)
                         {
                    XLS_SH_R_C.setCellValue(row.getRecieptNo().toString());
                         }
                    XLS_SH_R_C=XLS_SH_R.createCell(8);
                    XLS_SH_R_C.setCellStyle(csData);
                    if(row.getInvoiceNumberCollective()!=null) {
                     XLS_SH_R_C.setCellValue(row.getInvoiceNumberCollective().toString());
                     }else {
                        if(row.getInvoiceNumberNonCollective()!=null) {
                        XLS_SH_R_C.setCellValue(row.getInvoiceNumberNonCollective().toString());
                             }
                         }   
                     }
                
                }

                
                //rowVal=rowVal+1;
                XLS_SH_R= XLS_SH.createRow(++rowVal);        
                XLS_SH_R= XLS_SH.createRow(++rowVal);        
                
                XLS_SH_R= XLS_SH.createRow(++rowVal);
                XLS_SH_R_C=XLS_SH_R.createCell(0);
                XLS_SH_R_C.setCellStyle(cs);        
                XLS_SH_R_C.setCellValue("Total Price");
                XLS_SH_R_C=XLS_SH_R.createCell(6);
                XLS_SH_R_C.setCellStyle(csTotalAmt);  
                if(sum!=null)
                {
                    XLS_SH_R_C.setCellValue(formatConversion(sum,locale));
                }
                 iterator.closeRowSetIterator(); 
                
                
            }            
            
        }    
        
        XLS.write(outputStream);  
        outputStream.close();        
        
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public void specificExportExcelListener(FacesContext facesContext,
                                            OutputStream outputStream) throws IOException {
        System.out.println("Entering getValues..");
        String selectedValues="";
        List l = this.getShuttleValue();
        System.out.println("Size =="+l.size());
        //StringBuilder text = new StringBuilder("Size = ").append(getSelectedEmployees().size()).append(", Items added are: ");
               for (int i = 0; i <l.size(); i++ ) {
                   //text.append("Item ").append(i).append(" = ").append(l.get(i)).append(", ");
                   System.out.println("Item ="+i+" value== "+l.get(i));
                   selectedValues=selectedValues+l.get(i).toString().trim()+",";                   
               }  
               System.out.println("Formed String ="+selectedValues);
               String passedString=selectedValues.substring(0, selectedValues.length()-1);
               
                
                
                
        String partnerCompanyName="";
        ViewObject partnerVO = ADFUtils.getViewObject("PrtPartnerVO1Iterator");
        partnerVO.setWhereClause("PARTNER_ID =: partnerId");
        partnerVO.defineNamedWhereClauseParam("partnerId", partnerId,null);
        partnerVO.executeQuery();
        if (partnerVO.getEstimatedRowCount() != 0) {
            while(partnerVO.hasNext()) {
                PrtPartnerVORowImpl row=(PrtPartnerVORowImpl)partnerVO.next();
                partnerCompanyName=row.getFirstLastName();
            }
        }        
       
        HSSFWorkbook XLS = new HSSFWorkbook();
        HSSFRow XLS_SH_R=null;
        HSSFCell XLS_SH_R_C=null;
        int intRow=0;
        HSSFCellStyle cs = XLS.createCellStyle();
        HSSFFont f =XLS.createFont();
        
        //create sheet
        HSSFSheet XLS_SH=XLS.createSheet();
        XLS.setSheetName(0,"TransactionReport");
        
        f.setFontHeightInPoints((short) 10);
        f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);        
        f.setColor((short)0);
        cs.setFont(f);
        
        HSSFCellStyle csRight = XLS.createCellStyle();
        HSSFFont fnumberData =XLS.createFont();
        fnumberData.setFontHeightInPoints((short) 10);        
        fnumberData.setColor((short)0);        
        csRight.setFont(fnumberData);
        csRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT );
        
        HSSFCellStyle csTotalAmt = XLS.createCellStyle();
        HSSFFont fontTotal =XLS.createFont();
        fontTotal.setFontHeightInPoints((short) 10);        
        fontTotal.setColor((short)0);    
        fontTotal.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);        
        csTotalAmt.setFont(fontTotal);
        csTotalAmt.setAlignment(HSSFCellStyle.ALIGN_RIGHT );
        
        HSSFCellStyle csData = XLS.createCellStyle();
        HSSFFont fData =XLS.createFont();
        fData.setFontHeightInPoints((short) 10);            
        fData.setColor((short)0);
        csData.setFont(fData);        
        
        
        XLS_SH.setColumnWidth(50, 50);
        XLS_SH_R=XLS_SH.createRow(0);
        XLS_SH_R_C=XLS_SH_R.createCell(0);
        XLS_SH_R_C.setCellStyle(cs);        
        XLS_SH_R_C.setCellValue("Company: "+partnerCompanyName);
              
        
        
        XLS_SH_R= XLS_SH.createRow(1);
        XLS_SH_R_C=XLS_SH_R.createCell(0);
        XLS_SH_R_C.setCellStyle(cs);        
        XLS_SH_R_C.setCellValue("Account: "+getBindings().getAccount().getValue().toString()+partnerCompanyName);  
        
        
        
        XLS_SH_R= XLS_SH.createRow(2);
        XLS_SH_R_C=XLS_SH_R.createCell(0);
        XLS_SH_R_C.setCellStyle(cs);        
        XLS_SH_R_C.setCellValue("Terminal: "+checkALL((populateStringValues(getBindings().getTerminalType().getValue().toString())),"Terminal")); 
        
        XLS_SH_R= XLS_SH.createRow(3);
        XLS_SH_R_C=XLS_SH_R.createCell(0);
        XLS_SH_R_C.setCellStyle(cs);        
        XLS_SH_R_C.setCellValue("Type: "+checkALL((populateStringValues(getBindings().getTransationType().getValue().toString())),"Type"));        
        
        
        XLS_SH_R= XLS_SH.createRow(4);
        XLS_SH_R_C=XLS_SH_R.createCell(0);
        XLS_SH_R_C.setCellStyle(cs);        
        XLS_SH_R_C.setCellValue("Period: "+formatConversion((Date)getBindings().getFromDate().getValue())+" to "+formatConversion((Date)getBindings().getToDate().getValue()));
        
        for(int row=5;row<=7;row++) {
            XLS_SH_R= XLS_SH.createRow(row);            
        }                  
        
        String[] headerValues=passedString.split(",");
        
        XLS_SH_R= XLS_SH.createRow(8);       
        XLS_SH_R_C=XLS_SH_R.createCell(5);
        XLS_SH_R_C.setCellStyle(cs);  
        for(int i=0;i<headerValues.length;i++)
        {
        if("Total Amount".equalsIgnoreCase(headerValues[i].toString().trim())) {
        XLS_SH_R_C.setCellValue("*Note : All prices below are in "+currencyCode);
        }
        }
        
        
        
        HSSFCellStyle css = XLS.createCellStyle();
        HSSFFont fcss =XLS.createFont();
        fcss.setFontHeightInPoints((short) 10);
        fcss.setItalic(true);        
        fcss.setColor((short)0);
        css.setFont(fcss);
        XLS_SH_R= XLS_SH.createRow(9);
        for (int col = 0; col < headerValues.length; col++)
        {
        XLS_SH_R_C =XLS_SH_R.createCell(col);
        XLS_SH_R_C.setCellStyle(css);
        XLS_SH_R_C.setCellValue(headerValues[col].toString());
        }
        
        int rowVal=9;                
        boolean val=false;
        int valLoc=0;
        ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");                
        RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);                      
        iterator.reset();
        while (iterator.hasNext()) {                       
        PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();
        rowVal=rowVal+1;
        XLS_SH_R= XLS_SH.createRow(rowVal);
        if(row!=null) {
        
        for (int cellValue = 0; cellValue < headerValues.length; cellValue++)
        {
      
                        
                    if("Date".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getTransactionDt()!=null)
                        {       
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                            String time="";
                            if(row.getTransactionTime()!=null) {
                                time=getTimeHour(row.getTransactionTime());
                            }
                        XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().getTime()))+ "  "+time);
                        }
                    }else if("Station".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getStationName()!=null)
                        {
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                        XLS_SH_R_C.setCellValue(row.getStationName().toString());
                        }
                    } else if("Country".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getPurchaseCountryCode()!=null)
                        {
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                        XLS_SH_R_C.setCellValue(row.getPurchaseCountryCode().toString());
                        }
                    }else if("Product".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getPurchaseCountryCode()!=null)
                        {
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                        XLS_SH_R_C.setCellValue(row.getPurchaseCountryCode().toString());
                        }
                    }else if("Vol".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getQuantity()!=null)
                        {
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csRight);
                        XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale) + " "+row.getUnitOfMeasure());
                        }
                    }else if("Total Amount".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getInvoicedGrossAmount()!=null)
                        {
                            val=true;
                            valLoc=cellValue;
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csRight);
                            XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmount(),locale));
                        }
                    }else if("Receipt No".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getRecieptNo()!=null)
                        {
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getRecieptNo().toString());
                        }
                    }else if("Invoice No".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                 XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                                 XLS_SH_R_C.setCellStyle(csData);
                            if(row.getInvoiceNumberCollective()!=null) {
                             XLS_SH_R_C.setCellValue(row.getInvoiceNumberCollective().toString());
                             }else {
                                if(row.getInvoiceNumberNonCollective()!=null) {
                                XLS_SH_R_C.setCellValue(row.getInvoiceNumberNonCollective().toString());
                                     }
                                 }                                
                    }else if("Card".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getKsid()!=null)
                            {
                                XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getKsid().toString());
                            }
                    }else if("Vehicle No".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getVehicleNumber()!=null)
                            {
                                XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getVehicleNumber().toString());
                            }
                    }else if("Odometer".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                        XLS_SH_R_C.setCellStyle(csRight);
                        if(row.getOdometerPortal()!=null) {
                            XLS_SH_R_C.setCellValue(row.getOdometerPortal().toString());
                        }else {                        
                            if(row.getOdometer()!=null) {
                            XLS_SH_R_C.setCellValue(row.getOdometer().toString());
                                 }                        
                        }
                    }else if("TotalKM".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getkmTotal()!=null)
                            {
                                XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csRight);
                            XLS_SH_R_C.setCellValue(row.getkmTotal().toString());
                            }
                    }else if("KM/L".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getkmPerLt()!=null)
                            {
                                XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csRight);
                            XLS_SH_R_C.setCellValue(row.getkmPerLt().toString());
                            }
                    }else if("L/100KM".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getltPerHundred()!=null)
                            {
                                XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csRight);
                            XLS_SH_R_C.setCellValue(row.getltPerHundred().toString());
                            }
                    }else {
                        if("Driver Name".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getDriverName()!=null)
                             {
                             XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getDriverName().toString());
                            }
                        }
                    }
            }
         }
       }
        
        //rowVal=rowVal+1;
        XLS_SH_R= XLS_SH.createRow(++rowVal);        
        XLS_SH_R= XLS_SH.createRow(++rowVal);  
        
        if(val)
        {
         XLS_SH_R= XLS_SH.createRow(++rowVal);
         XLS_SH_R_C=XLS_SH_R.createCell(0);
         XLS_SH_R_C.setCellStyle(cs);        
         XLS_SH_R_C.setCellValue("Total Price");
         if(valLoc>0)
            {
            XLS_SH_R_C=XLS_SH_R.createCell(valLoc);               
             XLS_SH_R_C.setCellStyle(csTotalAmt);  
                if(sum!=null)
                {
                XLS_SH_R_C.setCellValue(formatConversion(sum,locale));
                 }
            }
        }
     iterator.closeRowSetIterator();   
                
           
        XLS.write(outputStream);  
        outputStream.close();  
    }

    public void exportExcelSpecificAction(ActionEvent actionEvent) {        
        if("CardGroup".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {
            String[] strHead=strCardGroup.split(","); 
            shuttleList  = new ArrayList<SelectItem>();
            for (int col = 0; col < strHead.length; col++)
            {
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(strHead[col].toString());
                selectItem.setValue(strHead[col].toString());
                shuttleList.add(selectItem);
            }
            
        }else if("Card".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {
            String[] strHead=strCard.split(",");     
            shuttleList  = new ArrayList<SelectItem>();
            for (int col = 0; col < strHead.length; col++)
            {
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(strHead[col].toString());
                selectItem.setValue(strHead[col].toString());
                shuttleList.add(selectItem);
            }
        }else if("Vehicle".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {
            String[] strHead=strVehicle.split(",");  
            shuttleList  = new ArrayList<SelectItem>();
            for (int col = 0; col < strHead.length; col++)
            {
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(strHead[col].toString());
                selectItem.setValue(strHead[col].toString());
                shuttleList.add(selectItem);
            }
        }else {
            String[] strHead=strDriver.split(","); 
            shuttleList  = new ArrayList<SelectItem>();
            for (int col = 0; col < strHead.length; col++)
            {
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(strHead[col].toString());
                selectItem.setValue(strHead[col].toString());
                shuttleList.add(selectItem);
            }
        } 
        getBindings().getSpecificColumns().show(new RichPopup.PopupHints());
    }

   

    public void specificExcelAction(ActionEvent actionEvent) {
        // Add event code here...  
        
    }

    public void getValuesForExcel(ActionEvent actionEvent) {    
        List l = this.getShuttleValue();
        System.out.println("Size =="+l.size());
        if(l.size()>0)
        {
        getBindings().getConfirmationExcel().show(new RichPopup.PopupHints());
        }else {            
            if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR")) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR"),"");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }            
            
        }
    }

    public String confirmationCancelAction() {
        getBindings().getConfirmationExcel().hide();
        return null;
    }

    public String excelDownLoad() {
        
        return null;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
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
        private RichSelectOneRadio cardCardGrpDrVhOneRadio;
        private RichPanelGroupLayout showSearchResultPG;
        private RichPopup specificColumns;
        private RichPopup confirmationExcel;
        private RichSelectManyShuttle shuttleExcel;
        private RichPanelGroupLayout selectedPGL;
        

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
        
        public void setCardCardGrpDrVhOneRadio(RichSelectOneRadio cardCardGrpDrVhOneRadio) {
            this.cardCardGrpDrVhOneRadio = cardCardGrpDrVhOneRadio;
        }

        public RichSelectOneRadio getCardCardGrpDrVhOneRadio() {
            return cardCardGrpDrVhOneRadio;
        }
        
        public void setShowSearchResultPG(RichPanelGroupLayout showSearchResultPG) {
            this.showSearchResultPG = showSearchResultPG;
        }

        public RichPanelGroupLayout getShowSearchResultPG() {
            return showSearchResultPG;
        }

        public void setSpecificColumns(RichPopup specificColumns) {
            this.specificColumns = specificColumns;
        }

        public RichPopup getSpecificColumns() {
            return specificColumns;
        }

        public void setShuttleExcel(RichSelectManyShuttle shuttleExcel) {
            this.shuttleExcel = shuttleExcel;
        }

        public RichSelectManyShuttle getShuttleExcel() {
            return shuttleExcel;
        }

        public void setConfirmationExcel(RichPopup confirmationExcel) {
            this.confirmationExcel = confirmationExcel;
        }

        public RichPopup getConfirmationExcel() {
            return confirmationExcel;
        }

        public void setSelectedPGL(RichPanelGroupLayout selectedPGL) {
            this.selectedPGL = selectedPGL;
        }

        public RichPanelGroupLayout getSelectedPGL() {
            return selectedPGL;
        }
    }
}
    
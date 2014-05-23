package com.sfr.engage.transactionoverviewtaskflow;


import com.sfr.core.bean.User;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.model.queries.rvo.PrtCardDriverVehicleInfoRVORowImpl;
import com.sfr.engage.model.queries.rvo.PrtCardTransactionOverviewRVORowImpl;
import com.sfr.engage.model.queries.rvo.PrtExportInfoRVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtPartnerVORowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.ADFUtils;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;
import com.sfr.util.validations.Conversion;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectManyShuttle;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.util.ResetUtils;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

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
    
    private ArrayList<SelectItem> partnerIdList; 
    private String partnerIdValue;
    private ArrayList<SelectItem> accountIdList; 
    private List<String> accountIdValue;
    private ArrayList<SelectItem> reportFormatList; 
    private String reportFormatValue;      
    private ArrayList<SelectItem> termianlList;    
    private List shuttleList = new ArrayList();
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
    private boolean reportDefault = false;
    private boolean reportRawData = false;
    private boolean reportInternationalTx = false;
    private boolean reportPriceSpecification = false;
    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    private List<PartnerInfo> partnerInfoList;
    private Boolean isTableVisible=false;
    ResourceBundle resourceBundle;
    private Float sum=0.000f;
    private String cardGroupSubtypePassValues;
    private String cardGroupMaintypePassValue;
    private String cardGroupSeqPassValues;
    private String partnerId;
    private String contentType;
    private String fileName;
    private Locale locale;
    private String partnerCountry=null;
    Conversion conversionUtility;
    private String lang;
    private String currencyCode;
    private String strCardGroup;
    private String strCard;
    private String strVehicle;
    private String strDriver;
    
    private String strCardGroupPrePopulated;
    private String strCardPrePopulated;
    private String strVehiclePrePopulated;
    private String strDriverPrePopulated;
    
    private String strCardGroupExtra;
    private String strCardExtra;
    private String strVehicleExtra;
    private String strDriverExtra;
    
    private String vehicleNumberOdometer = "";
    private String odometerPortal = null;
    private String urefTransactionId = null;
    private String palsCountryCode = null;
    public static final ADFLogger _logger = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();
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
            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
            if(partnerInfoList!=null){               
                partnerIdList=new ArrayList<SelectItem>();
                for(int k=0;k<partnerInfoList.size();k++)
                {                   
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(partnerInfoList.get(k).getPartnerName().toString());
                selectItem.setValue(partnerInfoList.get(k).getPartnerValue().toString());
                partnerIdList.add(selectItem);                
                System.out.println("partner list"+partnerInfoList.get(k).getPartnerValue().toString());
                }
            }
        }         
                          
        
        terminalValue.add("HOME");
        terminalValue.add("EXTERNAL");
        
        typeValue.add("PRI");
        typeValue.add("PR");
        typeValue.add("INV");
        
        //lang=(String)session.getAttribute(Constants.SESSION_LANGUAGE);        
        
        if(session!= null) {
        lang = (String)session.getAttribute(Constants.userLang);            
        }
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Language :"+lang);  
        
        if(lang=="NO")
        {
        currencyCode=conversionUtility.getCurrencyCode(lang);
        locale=conversionUtility.getLocaleFromCountryCode(lang);
        }else if(lang=="SE") {
            currencyCode=conversionUtility.getCurrencyCode(lang);
            locale=conversionUtility.getLocaleFromCountryCode(lang);
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
   

    public void setAccountIdValue(List<String> accountIdValue) {
        this.accountIdValue = accountIdValue;
    }

    public List<String> getAccountIdValue() {
        return accountIdValue;
    }
    
    
    public void partnerIdValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue() != null){
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "partner Id=====>"+valueChangeEvent.getNewValue()); 
            partnerId=valueChangeEvent.getNewValue().toString();
            if(partnerInfoList!=null){  
                accountIdList=new ArrayList<SelectItem>();
                accountIdValue=new ArrayList<String>();
                for(int k=0;k<partnerInfoList.size();k++)
                {                 
                if(valueChangeEvent.getNewValue().toString().trim().equalsIgnoreCase(partnerInfoList.get(k).getPartnerValue().toString()))   {  
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Partner Id is found :K"+k);  
                    if(partnerInfoList.get(k).getAccountList()!=null && partnerInfoList.get(k).getAccountList().size()>0) {
                        for(int ac=0;ac<partnerInfoList.get(k).getAccountList().size();ac++) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + partnerInfoList.get(k).getAccountList().get(ac).getAccountNumber().toString());
                            SelectItem selectItem = new SelectItem();
                            selectItem.setLabel(partnerInfoList.get(k).getAccountList().get(ac).getAccountNumber().toString());
                            selectItem.setValue(partnerInfoList.get(k).getAccountList().get(ac).getAccountNumber().toString());
                            accountIdList.add(selectItem); 
                            accountIdValue.add(partnerInfoList.get(k).getAccountList().get(ac).getAccountNumber().toString());                            
                        }
                    }
                     
                }                
                }
            }
            getBindings().getCardCardGrpDrVhOneRadio().setValue(null);
            cardIdPGL     =false;
            cardGPGL      =false;
            vNumberPGL    =false;
            dNamePGL      =false;                         
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());  
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL()); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardCardGrpDrVhOneRadio());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "account list is created");            
        }
        
    
    }

    public void radioButtonValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if(getBindings().getAccount().getValue()!=null)
        {
            if(valueChangeEvent.getNewValue() != null){
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "value of radioButon value change event======>"+valueChangeEvent.getNewValue());
                if(valueChangeEvent.getNewValue().equals("CardGroup")){
                    cardGPGL   = true;
                    cardIdPGL = false;
                    dNamePGL   = false;
                    vNumberPGL = false;
                    String accountValue=populateStringValues(getBindings().getAccount().getValue().toString());
                    populateValue(valueChangeEvent.getNewValue().toString(),accountValue);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
                }else if(valueChangeEvent.getNewValue().equals("Card")){
                    cardGPGL   = false;
                    cardIdPGL    = true;
                    dNamePGL   = false;
                    vNumberPGL = false;
                    String accountValue=populateStringValues(getBindings().getAccount().getValue().toString());
                    populateValue(valueChangeEvent.getNewValue().toString(),accountValue);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
                }else if(valueChangeEvent.getNewValue().equals("Vehicle")){
                    cardGPGL   = false;
                    cardIdPGL  = false;
                    dNamePGL   = false;
                    vNumberPGL = true;
                    String accountValue=populateStringValues(getBindings().getAccount().getValue().toString());
                    populateValue(valueChangeEvent.getNewValue().toString(),accountValue);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
                }else{
                    cardGPGL   = false;
                    cardIdPGL    = false;
                    dNamePGL   = true;
                    vNumberPGL = false;
                    String accountValue=populateStringValues(getBindings().getAccount().getValue().toString());
                    populateValue(valueChangeEvent.getNewValue().toString(),accountValue);
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
//           shuttleList.add(new javax.faces.model.SelectItem("Value1"));
//                   shuttleList.add(new javax.faces.model.SelectItem("Value2"));
//                   shuttleList.add(new javax.faces.model.SelectItem("Value3"));
//                   shuttleList.add(new javax.faces.model.SelectItem("Option1"));
//                   shuttleList.add(new javax.faces.model.SelectItem("Option2"));
//                   shuttleList.add(new javax.faces.model.SelectItem("Option3"));
         return shuttleList;
       }
      
       public List getShuttleValue() {           
           if(getBindings().getCardCardGrpDrVhOneRadio().getValue() != null && getBindings().getReportFormat().getValue()!= null)
           {
           if("CardGroup".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {  
               shuttleValue= new ArrayList();
               ViewObject prtExportInfoRVO = ADFUtils.getViewObject("PrtExportInfoRVO1Iterator"); 
               prtExportInfoRVO.setNamedWhereClauseParam("country_Code", lang);
               prtExportInfoRVO.setNamedWhereClauseParam("report_Page", "TRANSACTION");
               prtExportInfoRVO.setNamedWhereClauseParam("report_Type", getBindings().getReportFormat().getValue().toString());
               prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria", getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());                           
               prtExportInfoRVO.executeQuery();           
               _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + " PrtExportInfoRVO Estimated Row Count in CardGroup shuttle:"+prtExportInfoRVO.getEstimatedRowCount());
               if(prtExportInfoRVO.getEstimatedRowCount() > 0){
                   while(prtExportInfoRVO.hasNext()) {
                       PrtExportInfoRVORowImpl prtExportRow=(PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                       strCardGroupPrePopulated=prtExportRow.getPrePopulatedColumns();                       
                   }
               }            
               if(strCardGroupPrePopulated!=null)
               {
               
               String[] strHead=strCardGroupPrePopulated.split(",");              
               for (int col = 0; col < strHead.length; col++)
               {                
                   shuttleValue.add(strHead[col].toString());
               }  
              }
           }else if("Card".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {
                shuttleValue= new ArrayList();                        
                ViewObject prtExportInfoRVO = ADFUtils.getViewObject("PrtExportInfoRVO1Iterator"); 
                prtExportInfoRVO.setNamedWhereClauseParam("country_Code", lang);
                prtExportInfoRVO.setNamedWhereClauseParam("report_Page", "TRANSACTION");
                prtExportInfoRVO.setNamedWhereClauseParam("report_Type", getBindings().getReportFormat().getValue().toString());
                prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria", getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());     
                prtExportInfoRVO.executeQuery();           
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + " PrtExportInfoRVO Estimated Row Count in Card shuttle:"+prtExportInfoRVO.getEstimatedRowCount());
                if(prtExportInfoRVO.getEstimatedRowCount() > 0){
                    while(prtExportInfoRVO.hasNext()) {
                        PrtExportInfoRVORowImpl prtExportRow=(PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                        strCardPrePopulated=prtExportRow.getPrePopulatedColumns();                       
                    }
                }            
                if(strCardPrePopulated!=null)
                {                               
                String[] strHead=strCardPrePopulated.split(","); 
                for (int col = 0; col < strHead.length; col++)
                {
                shuttleValue.add(strHead[col].toString());
                }
                }
            }else if("Vehicle".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {
                shuttleValue= new ArrayList();                  
                ViewObject prtExportInfoRVO = ADFUtils.getViewObject("PrtExportInfoRVO1Iterator"); 
                prtExportInfoRVO.setNamedWhereClauseParam("country_Code", lang);
                prtExportInfoRVO.setNamedWhereClauseParam("report_Page", "TRANSACTION");
                prtExportInfoRVO.setNamedWhereClauseParam("report_Type", getBindings().getReportFormat().getValue().toString());
                prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria", getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());            
                prtExportInfoRVO.executeQuery();           
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + " PrtExportInfoRVO Estimated Row Count in vehicle shuttle:"+prtExportInfoRVO.getEstimatedRowCount());
                if(prtExportInfoRVO.getEstimatedRowCount() > 0){
                    while(prtExportInfoRVO.hasNext()) {
                        PrtExportInfoRVORowImpl prtExportRow=(PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                        strVehiclePrePopulated=prtExportRow.getPrePopulatedColumns();                       
                    }
                }            
                if(strVehiclePrePopulated!=null)
                { 
                 String[] strHead=strVehiclePrePopulated.split(","); 
                 for (int col = 0; col < strHead.length; col++)
                 {
                 shuttleValue.add(strHead[col].toString());
                 }
                }
            }else {
                shuttleValue= new ArrayList();                        
                ViewObject prtExportInfoRVO = ADFUtils.getViewObject("PrtExportInfoRVO1Iterator"); 
                prtExportInfoRVO.setNamedWhereClauseParam("country_Code", lang);
                prtExportInfoRVO.setNamedWhereClauseParam("report_Page", "TRANSACTION");
                prtExportInfoRVO.setNamedWhereClauseParam("report_Type", getBindings().getReportFormat().getValue().toString());
                prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria", getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());               
                prtExportInfoRVO.executeQuery();           
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + " PrtExportInfoRVO Estimated Row Count in Driver shuttle:"+prtExportInfoRVO.getEstimatedRowCount());
                if(prtExportInfoRVO.getEstimatedRowCount() > 0){
                    while(prtExportInfoRVO.hasNext()) {
                        PrtExportInfoRVORowImpl prtExportRow=(PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                        strDriverPrePopulated=prtExportRow.getPrePopulatedColumns();                       
                    }
                }            
                if(strDriverPrePopulated!=null)
                {                
                String[] strHead=strDriverPrePopulated.split(","); 
                for (int col = 0; col < strHead.length; col++)
                {
                shuttleValue.add(strHead[col].toString());
                 }
                }
            } 
           
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
    
    public void populateValue(String paramType,String accountValue){
        if(paramType != null){
            String[] listValues=accountValue.split(","); 
            if(paramType.equals("CardGroup")){
                cardGroupList  = new ArrayList<SelectItem>();
                cardGroupValue  = new ArrayList<String>();
                if(partnerInfoList!=null){                      
                    for(int k=0;k<partnerInfoList.size();k++)
                    {                 
                    if(partnerId.equalsIgnoreCase(partnerInfoList.get(k).getPartnerValue().toString()))   { 
                                for(int accVal=0;accVal<listValues.length;accVal++) { 
                            for(int ac=0;ac<partnerInfoList.get(k).getAccountList().size();ac++) {  
                                if(listValues[accVal].trim().equalsIgnoreCase(partnerInfoList.get(k).getAccountList().get(ac).getAccountNumber().toString())) {
                                    for(int cg =0 ; cg< partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().size(); cg++){                                    
                                    SelectItem selectItem = new SelectItem();
                                    selectItem.setLabel(partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupName().toString());
                                    selectItem.setValue(partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupID().toString());
                                    cardGroupList.add(selectItem);
                                    cardGroupValue.add(partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupID().toString());  
                                }                                    
                                }
                                    
                                }
                                              
                            }
                        }
                         
                                    
                    }
                }            
            }else if(paramType.equals("Card")){
                cardNumberList  = new ArrayList<SelectItem>();
                cardNumberValue  = new ArrayList<String>();
                if(partnerInfoList!=null){                      
                    for(int k=0;k<partnerInfoList.size();k++)
                    {                 
                    if(partnerId.equalsIgnoreCase(partnerInfoList.get(k).getPartnerValue().toString()))   { 
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + " Card:"+partnerId);
                                for(int accVal=0;accVal<listValues.length;accVal++) { 
                                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "accountID:"+listValues[accVal].trim());
                            for(int ac=0;ac<partnerInfoList.get(k).getAccountList().size();ac++) {  
                                if(listValues[accVal].trim().equalsIgnoreCase(partnerInfoList.get(k).getAccountList().get(ac).getAccountNumber().toString())) {
                                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "accountID Found:"+listValues[accVal].trim());
                                    
                                    
                                    
                                    for(int cg =0 ; cg< partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().size(); cg++){
                                        
                                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "cardGroupId:"+partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().size());
                                        
                                    if( partnerInfoList.get(k).getAccountList().get(ac).getCardGroup() != null &&  partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().size()>0){   
                                        
                                        for(int cc =0 ; cc<partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().size(); cc++){
                                            if(partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID()!= null && partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID()!= null){
                                    SelectItem selectItem = new SelectItem();
                                    selectItem.setLabel(partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
                                    selectItem.setValue(partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                    cardNumberList.add(selectItem);
                                    cardNumberValue.add(partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());  
                                                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "card:"+partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
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
                        vo.setNamedWhereClauseParam("accountValue",populateStringValues(getBindings().account.getValue().toString()));
                        }
                        vo.setNamedWhereClauseParam("countryCd", lang);
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
    
    public String searchResults() {
        sum=0.000f;       
        //String terminalPassingValues  = null;
        String transTypePassingValues = null;
        String cardNumberPasingValues = null ;
        String cardGroupPassingValues = null;
        String  newFromDate = null;
        String  newToDate = null;
        if(getBindings().getPartner().getValue() != null && getBindings().getReportFormat().getValue() != null && getBindings().getFromDate().getValue() != null && getBindings().getToDate().getValue() != null){
//            if(getBindings().getTerminalType().getValue() != null){
//            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "value of terminal type================>"+getBindings().getTerminalType().getValue().toString().trim());
//               terminalPassingValues =  populateStringValues(getBindings().getTerminalType().getValue().toString());
//            }else{
//                showErrorMessage("ENGAGE_NO_TERMINAL_TYPE");
//                return null;
//            }
            
            if(getBindings().getTransationType().getValue() != null){
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "value of transaction type================>"+getBindings().getTransationType().getValue().toString().trim());
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
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "value of new from date ================>"+newFromDate);
                    showErrorMessage("ENGAGE_VALID_FROM_TO_DATE");
                    return null;
                }
            }
        
            if(cardGPGL){
                if(getBindings().getCardGroup().getValue() != null){
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "value of card group================>"+getBindings().getCardGroup().getValue().toString().trim());
                    cardGroupPassingValues =  populateStringValues(getBindings().getCardGroup().getValue().toString());
                    populateCardGroupValues(cardGroupPassingValues);
                }
                else{
                    showErrorMessage("ENGAGE_NO_CARD_GROUP");
                    return null;
                }
            }
            
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "bollean value of vehicle====>"+vNumberPGL+dNamePGL);
            
            if(cardIdPGL ){
                if(getBindings().getCard().getValue() != null){
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "value of card  for card Id================>"+getBindings().getCard().getValue().toString().trim());
                    cardNumberPasingValues =  populateStringValues(getBindings().getCard().getValue().toString());
                }else{
                    showErrorMessage("ENGAGE_NO_CARD");
                    return null;
                }
            }
            
            if(vNumberPGL){
                if(getBindings().getVehicleNumber().getValue() != null){
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "value of card  for vehicle ================>"+getBindings().getVehicleNumber().getValue().toString().trim());
                    cardNumberPasingValues =  populateStringValues(getBindings().getVehicleNumber().getValue().toString());
                }else{
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Is it coming inside vehicle else number block");
                    showErrorMessage("ENGAGE_NO_VEHICLE");
                    return null;
                }
            }
            
            if(dNamePGL){
                if(getBindings().getDriverName().getValue() != null){
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "value of card driver================>"+getBindings().getDriverName().getValue().toString().trim());
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
       // _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "value of terminal pass ================>"+terminalPassingValues);
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "value of trans type ================>"+transTypePassingValues);
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "value of card111111 ================>"+cardNumberPasingValues);
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Account Values ================>"+getBindings().getAccount().getValue());
        
        isTableVisible = false;
        ViewObject vo = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");
        vo.setNamedWhereClauseParam("accountId", populateStringValues(getBindings().getAccount().getValue().toString()));
        vo.setNamedWhereClauseParam("countryCd", lang);
        vo.setNamedWhereClauseParam("partnerId", getBindings().getPartner().getValue().toString());
        
            if("INSTR(:type,TRANSACTION_TYPE)<>0 AND INSTR(:card,KSID)<>0 AND TRANSACTION_DT >= :fromDate AND TRANSACTION_DT <= :toDate".equalsIgnoreCase(vo.getWhereClause())){
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "inside  card where removal class");
                vo.removeNamedWhereClauseParam("card");
                //vo.removeNamedWhereClauseParam("terminal");
                vo.removeNamedWhereClauseParam("type");
                vo.removeNamedWhereClauseParam("fromDate");
                vo.removeNamedWhereClauseParam("toDate");
                //vo.removeNamedWhereClauseParam("partnerNumber");
                //vo.removeNamedWhereClauseParam("accountId");
                vo.setWhereClause("");
                vo.executeQuery();
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "count of vo=====>"+vo.getEstimatedRowCount());
            }else{
                if("INSTR(:type,TRANSACTION_TYPE)<>0 AND INSTR(:cardGrpMainType,CARDGROUP_MAIN_TYPE)<>0 AND INSTR(:cardgrpSubType,CARDGROUP_SUB_TYPE)<>0 AND INSTR(:cardGrpSeq,CARDGROUP_SEQ)<>0 AND TRANSACTION_DT >= :fromDate AND TRANSACTION_DT <= :toDate".equalsIgnoreCase(vo.getWhereClause())){
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "inside  card group where removal class");
                    vo.removeNamedWhereClauseParam("cardGrpMainType");
                    vo.removeNamedWhereClauseParam("cardgrpSubType");
                    vo.removeNamedWhereClauseParam("cardGrpSeq");
                    //vo.removeNamedWhereClauseParam("terminal");
                    vo.removeNamedWhereClauseParam("type");
                    vo.removeNamedWhereClauseParam("fromDate");
                    vo.removeNamedWhereClauseParam("toDate");
                    //vo.removeNamedWhereClauseParam("partnerNumber");
                    //vo.removeNamedWhereClauseParam("accountId");
                    vo.setWhereClause("");
                    vo.executeQuery();
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "count of vo1111111=====>"+vo.getEstimatedRowCount());
                }
            }
        
        if (cardIdPGL || vNumberPGL || dNamePGL) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Inside block for card");
            vo.setWhereClause("INSTR(:type,TRANSACTION_TYPE)<>0 AND INSTR(:card,KSID)<>0 AND TRANSACTION_DT >= :fromDate AND TRANSACTION_DT <= :toDate");
            vo.defineNamedWhereClauseParam("card", cardNumberPasingValues, null);
        }else{
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Coming inside card group block");
            vo.setWhereClause("INSTR(:type,TRANSACTION_TYPE)<>0 AND INSTR(:cardGrpMainType,CARDGROUP_MAIN_TYPE)<>0 AND INSTR(:cardgrpSubType,CARDGROUP_SUB_TYPE)<>0 AND INSTR(:cardGrpSeq,CARDGROUP_SEQ)<>0 AND TRANSACTION_DT >= :fromDate AND TRANSACTION_DT <= :toDate");
            vo.defineNamedWhereClauseParam("cardGrpMainType", cardGroupMaintypePassValue, null);
            vo.defineNamedWhereClauseParam("cardgrpSubType", cardGroupSubtypePassValues, null);
            vo.defineNamedWhereClauseParam("cardGrpSeq", cardGroupSeqPassValues, null);
        }
            //vo.defineNamedWhereClauseParam("terminal", "EXTERNAL" , null);
            vo.defineNamedWhereClauseParam("type", transTypePassingValues, null);
            vo.defineNamedWhereClauseParam("fromDate", newFromDate , null);
            vo.defineNamedWhereClauseParam("toDate", newToDate, null);
            //vo.defineNamedWhereClauseParam("countryCd", lang, null);
            //vo.defineNamedWhereClauseParam("partnerNumber", partnerId, null);
            //vo.defineNamedWhereClauseParam("accountId", getBindings().getAccount().getValue().toString().trim(), null);
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "where clause of view object=====>"+vo.getWhereClause());
            vo.executeQuery();
            isTableVisible = true;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowSearchResultPG());
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "where clause of view object=====>"+vo.getWhereClause());
            sum=0.000f;
            RowSetIterator iterator = vo.createRowSetIterator(null); 
            iterator.reset();
            if (vo.getEstimatedRowCount() != 0) {           
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Inside Estimated row count" + vo.getEstimatedRowCount());
                System.out.println("Summ =="+sum);
                while (iterator.hasNext()) {                       
                PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();           
                    System.out.println("Temp ="+row.getInvoicedGrossAmountRebated().floatValue());
                    Float temp = row.getInvoicedGrossAmountRebated().floatValue();                    
                    sum = sum + temp;
                }  
                
//                for(int i=0;i<=vo.getEstimatedRowCount();i++ ){
//                    Row rw = vo.getRowAtRangeIndex(i);
//                        if(rw != null){                             
//                            Float temp = rw.getAttribute("InvoicedNetAmountRebated");
//                            sum = sum + temp;
//                        }
//                            //                vo.getViewObject().next();
//                }
                
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
    
    public String searchTransactionAction_event(ActionEvent actionEvent) {
        if(getBindings().getReportFormat().getValue()!=null) {
            if("Raw Data".equalsIgnoreCase(getBindings().getReportFormat().getValue().toString())) {
                reportRawData = true;
                reportInternationalTx = false;
                reportPriceSpecification = false;
                reportDefault=false;
            }else if("International".equalsIgnoreCase(getBindings().getReportFormat().getValue().toString())) {
                reportRawData = false;
                reportInternationalTx = true;
                reportPriceSpecification = false;
                reportDefault=false;
            }else if("Default".equalsIgnoreCase(getBindings().getReportFormat().getValue().toString())) {
                reportRawData = false;
                reportInternationalTx = false;
                reportPriceSpecification = false;
                reportDefault=true;
            }else
            { 
                if("Price Specification".equalsIgnoreCase(getBindings().getReportFormat().getValue().toString())) {
                reportRawData = false;
                reportInternationalTx = false;
                reportPriceSpecification = true;
                    reportDefault=false;
                }
            }
        } 
        searchResults();
        return null;
    }
    
    public void clearSearchListener(ActionEvent actionEvent) {
        // Add event code here...
        getBindings().getPartner().setValue(null);
        this.accountIdValue=null;
        //getBindings().getAccount().setValue(null);
        this.accountIdValue=null;
        this.partnerIdValue=null;
        this.reportFormatValue=null;
        getBindings().getCardCardGrpDrVhOneRadio().setValue(null);
        getBindings().getReportFormat().setValue(null);
        getBindings().getFromDate().setValue(null);
        getBindings().getToDate().setValue(null);
        isTableVisible=false;
        cardIdPGL     =false;
        cardGPGL      =false;
        vNumberPGL    =false;
        dNamePGL      =false; 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartner()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());  
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardCardGrpDrVhOneRadio());  
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getReportFormat());
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
              
              _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "card group main type======>"+cardGroupMaintypePassValue);
              _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "card group sub type===>"+cardGroupSubtypePassValues);
              _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "card group sequence value====>"+cardGroupSeqPassValues);
        }
    }
    
    public void accountValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue()!=null) {
            getBindings().getCardCardGrpDrVhOneRadio().setValue(null);
            cardGPGL   = false;
            cardIdPGL  = false;
            dNamePGL   = false;
            vNumberPGL = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardNoPGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNamePGL());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVhNumberPGL());
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
//        // Add event code here...
//        String partnerCompanyName="";
//        ViewObject partnerVO = ADFUtils.getViewObject("PrtPartnerVO1Iterator");
//        partnerVO.setWhereClause("PARTNER_ID =: partnerId");
//        partnerVO.defineNamedWhereClauseParam("partnerId", partnerId,null);
//        partnerVO.executeQuery();
//        if (partnerVO.getEstimatedRowCount() != 0) {
//            while(partnerVO.hasNext()) {
//                PrtPartnerVORowImpl row=(PrtPartnerVORowImpl)partnerVO.next();
//                partnerCompanyName=row.getFirstLastName();
//            }
//        }        
//        
//        HSSFWorkbook XLS = new HSSFWorkbook();
//        HSSFRow XLS_SH_R=null;
//        HSSFCell XLS_SH_R_C=null;
//        int intRow=0;
//        HSSFCellStyle cs = XLS.createCellStyle();
//        HSSFFont f =XLS.createFont();
//        
//        //create sheet
//        HSSFSheet XLS_SH=XLS.createSheet();
//        XLS.setSheetName(0,"TransactionReport");
//        
//        f.setFontHeightInPoints((short) 10);
//        f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);        
//        f.setColor((short)0);
//        cs.setFont(f);
//        
//        HSSFCellStyle csRight = XLS.createCellStyle();
//        HSSFFont fnumberData =XLS.createFont();
//        fnumberData.setFontHeightInPoints((short) 10);        
//        fnumberData.setColor((short)0);        
//        csRight.setFont(fnumberData);
//        csRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT );
//        
//        HSSFCellStyle csTotalAmt = XLS.createCellStyle();
//        HSSFFont fontTotal =XLS.createFont();
//        fontTotal.setFontHeightInPoints((short) 10);        
//        fontTotal.setColor((short)0);    
//        fontTotal.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);        
//        csTotalAmt.setFont(fontTotal);
//        csTotalAmt.setAlignment(HSSFCellStyle.ALIGN_RIGHT );
//        
//        HSSFCellStyle csData = XLS.createCellStyle();
//        HSSFFont fData =XLS.createFont();
//        fData.setFontHeightInPoints((short) 10);            
//        fData.setColor((short)0);
//        csData.setFont(fData);        
//       
//       
//        XLS_SH.setColumnWidth(50, 50);
//        XLS_SH_R=XLS_SH.createRow(0);
//        XLS_SH_R_C=XLS_SH_R.createCell(0);
//        XLS_SH_R_C.setCellStyle(cs);        
//        XLS_SH_R_C.setCellValue("Company: "+partnerCompanyName);
//              
//       
//        
//        XLS_SH_R= XLS_SH.createRow(1);
//        XLS_SH_R_C=XLS_SH_R.createCell(0);
//        XLS_SH_R_C.setCellStyle(cs);        
//        XLS_SH_R_C.setCellValue("Account: "+getBindings().getAccount().getValue().toString()+partnerCompanyName);  
//        
//        
//        
//        XLS_SH_R= XLS_SH.createRow(2);
//        XLS_SH_R_C=XLS_SH_R.createCell(0);
//        XLS_SH_R_C.setCellStyle(cs);        
//        XLS_SH_R_C.setCellValue("Terminal: "+checkALL((populateStringValues(getBindings().getTerminalType().getValue().toString())),"Terminal")); 
//        
//        XLS_SH_R= XLS_SH.createRow(3);
//        XLS_SH_R_C=XLS_SH_R.createCell(0);
//        XLS_SH_R_C.setCellStyle(cs);        
//        XLS_SH_R_C.setCellValue("Type: "+checkALL((populateStringValues(getBindings().getTransationType().getValue().toString())),"Type"));        
//       
//        
//        XLS_SH_R= XLS_SH.createRow(4);
//        XLS_SH_R_C=XLS_SH_R.createCell(0);
//        XLS_SH_R_C.setCellStyle(cs);        
//        XLS_SH_R_C.setCellValue("Period: "+formatConversion((Date)getBindings().getFromDate().getValue())+" to "+formatConversion((Date)getBindings().getToDate().getValue()));
//        
//        for(int row=5;row<=7;row++) {
//            XLS_SH_R= XLS_SH.createRow(row);            
//        }                  
//       
//        
//        XLS_SH_R= XLS_SH.createRow(8);       
//        XLS_SH_R_C=XLS_SH_R.createCell(5);
//        XLS_SH_R_C.setCellStyle(cs);  
//        XLS_SH_R_C.setCellValue("*Note : All prices below are in "+"NOK");
//        
//        if(getBindings().getCardCardGrpDrVhOneRadio().getValue()!=null) {            
//            if("CardGroup".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {                
//                String[] strHead=strCardGroup.split(",");                
//                HSSFCellStyle css = XLS.createCellStyle();
//                HSSFFont fcss =XLS.createFont();
//                fcss.setFontHeightInPoints((short) 10);
//                fcss.setItalic(true);        
//                fcss.setColor((short)0);
//                css.setFont(fcss);
//                XLS_SH_R= XLS_SH.createRow(9);
//                for (int col = 0; col < strHead.length; col++)
//                {
//                XLS_SH_R_C =XLS_SH_R.createCell(col);
//                XLS_SH_R_C.setCellStyle(css);
//                XLS_SH_R_C.setCellValue(strHead[col].toString());
//                }
//                int rowVal=9;                
//                
//                ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");                
//                RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);                      
//                iterator.reset();
//                while (iterator.hasNext()) {                       
//                  PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();
//                    rowVal=rowVal+1;
//                    XLS_SH_R= XLS_SH.createRow(rowVal);
//                    if(row!=null) {
//                    XLS_SH_R_C=XLS_SH_R.createCell(0);
//                    XLS_SH_R_C.setCellStyle(csData);
//                    if(row.getTransactionDt()!=null)
//                    {       
//                        String time="";
//                        if(row.getTransactionTime()!=null) {
//                            time=getTimeHour(row.getTransactionTime());
//                        }
//                    XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().getTime()))+time);
//                    }
//                    XLS_SH_R_C=XLS_SH_R.createCell(1);
//                    XLS_SH_R_C.setCellStyle(csData);
//                    if(row.getStationName()!=null)
//                    {
//                    XLS_SH_R_C.setCellValue(row.getStationName().toString());
//                    }
//                    XLS_SH_R_C=XLS_SH_R.createCell(2);
//                    XLS_SH_R_C.setCellStyle(csData);
//                    if(row.getPurchaseCountryCode()!=null)
//                    {
//                    XLS_SH_R_C.setCellValue(row.getPurchaseCountryCode().toString());
//                    }
//                    XLS_SH_R_C=XLS_SH_R.createCell(3);
//                    XLS_SH_R_C.setCellStyle(csData);
//                    if(row.getProductName()!=null)
//                    {
//                    XLS_SH_R_C.setCellValue(row.getProductName().toString());
//                    }
//                    XLS_SH_R_C=XLS_SH_R.createCell(4);                    
//                    XLS_SH_R_C.setCellStyle(csRight);
//                    if(row.getQuantity()!=null)
//                    {
//                    
//                    XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale));
//                    }
//                    XLS_SH_R_C=XLS_SH_R.createCell(5);                    
//                    XLS_SH_R_C.setCellStyle(csRight);
//                    if(row.getInvoicedGrossAmount()!=null)
//                    {
//                        //_logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Locale ="+formatConversion(row.getInvoicedGrossAmount(),locale));                        
//                    XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmount(),locale));
//                    }
//                    XLS_SH_R_C=XLS_SH_R.createCell(6);
//                    XLS_SH_R_C.setCellStyle(csData);
//                    if(row.getRecieptNo()!=null)
//                    {
//                    XLS_SH_R_C.setCellValue(row.getRecieptNo().toString());
//                    }
//                    XLS_SH_R_C=XLS_SH_R.createCell(7);
//                    XLS_SH_R_C.setCellStyle(csData);
//                    if(row.getInvoiceNumberCollective()!=null) {
//                     XLS_SH_R_C.setCellValue(row.getInvoiceNumberCollective().toString());
//                     }else {
//                        if(row.getInvoiceNumberNonCollective()!=null) {
//                        XLS_SH_R_C.setCellValue(row.getInvoiceNumberNonCollective().toString());
//                             }
//                         }   
//                     }
//                
//                }
//
//               
//                //rowVal=rowVal+1;
//                XLS_SH_R= XLS_SH.createRow(++rowVal);        
//                XLS_SH_R= XLS_SH.createRow(++rowVal);        
//                
//                XLS_SH_R= XLS_SH.createRow(++rowVal);
//                XLS_SH_R_C=XLS_SH_R.createCell(0);
//                XLS_SH_R_C.setCellStyle(cs);        
//                XLS_SH_R_C.setCellValue("Total Price");
//                XLS_SH_R_C=XLS_SH_R.createCell(5);               
//                XLS_SH_R_C.setCellStyle(csTotalAmt);  
//                if(sum!=null)
//                {
//                XLS_SH_R_C.setCellValue(formatConversion(sum,locale));
//                }
//                 iterator.closeRowSetIterator();   
//                
//            }else if("Card".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())){                
//                String[] strHead=strCard.split(",");                
//                HSSFCellStyle css = XLS.createCellStyle();
//                HSSFFont fcss =XLS.createFont();
//                fcss.setFontHeightInPoints((short) 10);
//                fcss.setItalic(true);        
//                fcss.setColor((short)0);
//                css.setFont(fcss);
//                XLS_SH_R= XLS_SH.createRow(9);
//                for (int col = 0; col < strHead.length; col++)
//                {
//                XLS_SH_R_C =XLS_SH_R.createCell(col);
//                XLS_SH_R_C.setCellStyle(css);
//                XLS_SH_R_C.setCellValue(strHead[col].toString());
//                }
//                
//                int rowVal=9;                
//                
//                ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");                
//                RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);                      
//                iterator.reset();
//                while (iterator.hasNext()) {                        
//                  PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();
//                    rowVal=rowVal+1;
//                    XLS_SH_R= XLS_SH.createRow(rowVal);
//                    if(row!=null) {
//                    XLS_SH_R_C=XLS_SH_R.createCell(0);
//                    XLS_SH_R_C.setCellStyle(csData);
//                         if(row.getTransactionDt()!=null)
//                         {
//                             String time="";
//                             if(row.getTransactionTime()!=null) {
//                                 time=getTimeHour(row.getTransactionTime());
//                             }
//                             XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().getTime()))+time);
//                         }
//                    XLS_SH_R_C=XLS_SH_R.createCell(1);
//                    XLS_SH_R_C.setCellStyle(csData);
//                         if(row.getKsid()!=null)
//                         {
//                    XLS_SH_R_C.setCellValue(row.getKsid().toString());
//                         }
//                    XLS_SH_R_C=XLS_SH_R.createCell(2);
//                    XLS_SH_R_C.setCellStyle(csData);
//                         if(row.getStationName()!=null)
//                         {
//                    XLS_SH_R_C.setCellValue(row.getStationName().toString());
//                         }
//                    XLS_SH_R_C=XLS_SH_R.createCell(3);
//                    XLS_SH_R_C.setCellStyle(csData);
//                         if(row.getPurchaseCountryCode()!=null)
//                         {
//                    XLS_SH_R_C.setCellValue(row.getPurchaseCountryCode().toString());
//                         }
//                    XLS_SH_R_C=XLS_SH_R.createCell(4);
//                    XLS_SH_R_C.setCellStyle(csData);
//                         if(row.getProductName()!=null)
//                         {
//                    XLS_SH_R_C.setCellValue(row.getProductName().toString());
//                         }
//                    XLS_SH_R_C=XLS_SH_R.createCell(5);
//                    XLS_SH_R_C.setCellStyle(csRight);
//                         if(row.getQuantity()!=null)
//                         {
//                             XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale));
//                         }
//                    XLS_SH_R_C=XLS_SH_R.createCell(6);
//                    XLS_SH_R_C.setCellStyle(csRight);
//                         if(row.getInvoicedGrossAmount()!=null)
//                         {
//                             XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmount(),locale));
//                         }
//                    XLS_SH_R_C=XLS_SH_R.createCell(7);
//                    XLS_SH_R_C.setCellStyle(csData);
//                         if(row.getRecieptNo()!=null)
//                         {
//                    XLS_SH_R_C.setCellValue(row.getRecieptNo().toString());
//                         }
//                    XLS_SH_R_C=XLS_SH_R.createCell(8);
//                    XLS_SH_R_C.setCellStyle(csData);
//                    if(row.getInvoiceNumberCollective()!=null) {
//                     XLS_SH_R_C.setCellValue(row.getInvoiceNumberCollective().toString());
//                     }else {
//                        if(row.getInvoiceNumberNonCollective()!=null) {
//                        XLS_SH_R_C.setCellValue(row.getInvoiceNumberNonCollective().toString());
//                             }
//                         }   
//                     }
//                
//                }
//
//                
//                //rowVal=rowVal+1;
//                XLS_SH_R= XLS_SH.createRow(++rowVal);        
//                XLS_SH_R= XLS_SH.createRow(++rowVal);        
//                
//                XLS_SH_R= XLS_SH.createRow(++rowVal);
//                XLS_SH_R_C=XLS_SH_R.createCell(0);
//                XLS_SH_R_C.setCellStyle(cs);        
//                XLS_SH_R_C.setCellValue("Total Price");
//                XLS_SH_R_C=XLS_SH_R.createCell(6);
//                XLS_SH_R_C.setCellStyle(csTotalAmt);  
//                if(sum!=null)
//                {
//                    XLS_SH_R_C.setCellValue(formatConversion(sum,locale));
//                }
//                 iterator.closeRowSetIterator();   
//                
//            }else if("Vehicle".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())){               
//                String[] strHead=strVehicle.split(",");                
//                HSSFCellStyle css = XLS.createCellStyle();
//                HSSFFont fcss =XLS.createFont();
//                fcss.setFontHeightInPoints((short) 10);
//                fcss.setItalic(true);        
//                fcss.setColor((short)0);
//                css.setFont(fcss);
//                XLS_SH_R= XLS_SH.createRow(9);
//                for (int col = 0; col < strHead.length; col++)
//                {
//                XLS_SH_R_C =XLS_SH_R.createCell(col);
//                XLS_SH_R_C.setCellStyle(css);
//                XLS_SH_R_C.setCellValue(strHead[col].toString());
//                }
//                int rowVal=9;
//                
//                ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");                
//                RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);                      
//                iterator.reset();
//                while (iterator.hasNext()) {                        
//                  PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();
//                    rowVal=rowVal+1;
//                    XLS_SH_R= XLS_SH.createRow(rowVal);
//                    if(row!=null) {
//                    XLS_SH_R_C=XLS_SH_R.createCell(0);
//                    XLS_SH_R_C.setCellStyle(csData);
//                      if(row.getTransactionDt()!=null)
//                      {
//                          String time="";
//                          if(row.getTransactionTime()!=null) {
//                              time=getTimeHour(row.getTransactionTime());
//                          }
//                          XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().getTime()))+time);
//                      }
//                    XLS_SH_R_C=XLS_SH_R.createCell(1);
//                    XLS_SH_R_C.setCellStyle(csData);
//                      if(row.getVehicleNumber()!=null)
//                      {
//                    XLS_SH_R_C.setCellValue(row.getVehicleNumber().toString());
//                      }
//                    XLS_SH_R_C=XLS_SH_R.createCell(2);
//                    XLS_SH_R_C.setCellStyle(csData);
//                      if(row.getStationName()!=null)
//                      {
//                    XLS_SH_R_C.setCellValue(row.getStationName().toString());
//                      }
//                    XLS_SH_R_C=XLS_SH_R.createCell(3);
//                    XLS_SH_R_C.setCellStyle(csData);
//                      if(row.getPurchaseCountryCode()!=null)
//                      {
//                    XLS_SH_R_C.setCellValue(row.getPurchaseCountryCode().toString());
//                      }
//                    XLS_SH_R_C=XLS_SH_R.createCell(4);
//                    XLS_SH_R_C.setCellStyle(csData);
//                      if(row.getProductName()!=null)
//                      {
//                    XLS_SH_R_C.setCellValue(row.getProductName().toString());
//                      }
//                    XLS_SH_R_C=XLS_SH_R.createCell(5);
//                    XLS_SH_R_C.setCellStyle(csRight);
//                      if(row.getQuantity()!=null)
//                      {
//                          XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale));
//                      }
//                    XLS_SH_R_C=XLS_SH_R.createCell(6);
//                    XLS_SH_R_C.setCellStyle(csRight);
//                      if(row.getInvoicedGrossAmount()!=null)
//                      {
//                          XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmount(),locale));
//                      }
//                    XLS_SH_R_C=XLS_SH_R.createCell(7);
//                    XLS_SH_R_C.setCellStyle(csData);
//                      if(row.getRecieptNo()!=null)
//                      {
//                    XLS_SH_R_C.setCellValue(row.getRecieptNo().toString());
//                      }
//                    XLS_SH_R_C=XLS_SH_R.createCell(8);
//                    XLS_SH_R_C.setCellStyle(csData);                    
//                    if(row.getInvoiceNumberCollective()!=null) {
//                     XLS_SH_R_C.setCellValue(row.getInvoiceNumberCollective().toString());
//                     }else {
//                        if(row.getInvoiceNumberNonCollective()!=null) {
//                        XLS_SH_R_C.setCellValue(row.getInvoiceNumberNonCollective().toString());
//                             }
//                         }                      
//                    XLS_SH_R_C=XLS_SH_R.createCell(9);
//                    XLS_SH_R_C.setCellStyle(csRight);
//                    if(row.getOdometerPortal()!=null) {
//                        XLS_SH_R_C.setCellValue(row.getOdometerPortal().toString());
//                    }else {                        
//                        if(row.getOdometer()!=null) {
//                        XLS_SH_R_C.setCellValue(row.getOdometer().toString());
//                             }                        
//                    }
//                    
//                      XLS_SH_R_C=XLS_SH_R.createCell(10);
//                      XLS_SH_R_C.setCellStyle(csRight); 
//                      if(row.getkmTotal()!=null)
//                      {
//                      XLS_SH_R_C.setCellValue(row.getkmTotal().toString());
//                      }
//                      
//                      XLS_SH_R_C=XLS_SH_R.createCell(11);
//                      XLS_SH_R_C.setCellStyle(csRight);   
//                      if(row.getkmPerLt()!=null)
//                      {
//                      XLS_SH_R_C.setCellValue(row.getkmPerLt().toString());
//                      }
//                      
//                      XLS_SH_R_C=XLS_SH_R.createCell(12);
//                      XLS_SH_R_C.setCellStyle(csRight);  
//                      if(row.getltPerHundred()!=null)
//                      {
//                      XLS_SH_R_C.setCellValue(row.getltPerHundred().toString());                    
//                      }
//                    
//                  }
//                
//                }
//
//                
//                //rowVal=rowVal+1;
//                XLS_SH_R= XLS_SH.createRow(++rowVal);        
//                XLS_SH_R= XLS_SH.createRow(++rowVal);        
//                
//                XLS_SH_R= XLS_SH.createRow(++rowVal);
//                XLS_SH_R_C=XLS_SH_R.createCell(0);
//                XLS_SH_R_C.setCellStyle(cs);        
//                XLS_SH_R_C.setCellValue("Total Price");
//                XLS_SH_R_C=XLS_SH_R.createCell(6);
//                XLS_SH_R_C.setCellStyle(csTotalAmt);  
//                if(sum!=null)
//                {
//                    XLS_SH_R_C.setCellValue(formatConversion(sum,locale));
//                }
//                 iterator.closeRowSetIterator();   
//            }else {               
//                String[] strHead=strDriver.split(",");                
//                HSSFCellStyle css = XLS.createCellStyle();
//                HSSFFont fcss =XLS.createFont();
//                fcss.setFontHeightInPoints((short) 10);
//                fcss.setItalic(true);        
//                fcss.setColor((short)0);
//                css.setFont(fcss);
//                XLS_SH_R= XLS_SH.createRow(9);
//                for (int col = 0; col < strHead.length; col++)
//                {
//                XLS_SH_R_C =XLS_SH_R.createCell(col);
//                XLS_SH_R_C.setCellStyle(css);
//                XLS_SH_R_C.setCellValue(strHead[col].toString());
//                }
//                
//                int rowVal=9;
//                
//                ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");                
//                RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);                      
//                iterator.reset();
//                while (iterator.hasNext()) {                       
//                  PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();
//                    rowVal=rowVal+1;
//                    XLS_SH_R= XLS_SH.createRow(rowVal);
//                    if(row!=null) {
//                    XLS_SH_R_C=XLS_SH_R.createCell(0);
//                    XLS_SH_R_C.setCellStyle(csData);
//                         if(row.getTransactionDt()!=null)
//                         {
//                             String time="";
//                             if(row.getTransactionTime()!=null) {
//                                 time=getTimeHour(row.getTransactionTime());
//                             }
//                             XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().getTime()))+time);
//                         }
//                         XLS_SH_R_C=XLS_SH_R.createCell(1);
//                         XLS_SH_R_C.setCellStyle(csData);
//                         if(row.getDriverName()!=null)
//                         {
//                         XLS_SH_R_C.setCellValue(row.getDriverName().toString());
//                         }
//                    XLS_SH_R_C=XLS_SH_R.createCell(2);
//                    XLS_SH_R_C.setCellStyle(csData);
//                         if(row.getStationName()!=null)
//                         {
//                    XLS_SH_R_C.setCellValue(row.getStationName().toString());
//                         }
//                    XLS_SH_R_C=XLS_SH_R.createCell(3);
//                    XLS_SH_R_C.setCellStyle(csData);
//                         if(row.getPurchaseCountryCode()!=null)
//                         {
//                    XLS_SH_R_C.setCellValue(row.getPurchaseCountryCode().toString());
//                         }
//                    XLS_SH_R_C=XLS_SH_R.createCell(4);
//                    XLS_SH_R_C.setCellStyle(csData);
//                         if(row.getProductName()!=null)
//                         {
//                    XLS_SH_R_C.setCellValue(row.getProductName().toString());
//                         }
//                    XLS_SH_R_C=XLS_SH_R.createCell(5);
//                    XLS_SH_R_C.setCellStyle(csRight);
//                         if(row.getQuantity()!=null)
//                         {
//                             XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale));
//                         }
//                    XLS_SH_R_C=XLS_SH_R.createCell(6);
//                    XLS_SH_R_C.setCellStyle(csRight);
//                         if(row.getInvoicedGrossAmount()!=null)
//                         {
//                             XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmount(),locale));
//                         }
//                    XLS_SH_R_C=XLS_SH_R.createCell(7);
//                    XLS_SH_R_C.setCellStyle(csData);
//                         if(row.getRecieptNo()!=null)
//                         {
//                    XLS_SH_R_C.setCellValue(row.getRecieptNo().toString());
//                         }
//                    XLS_SH_R_C=XLS_SH_R.createCell(8);
//                    XLS_SH_R_C.setCellStyle(csData);
//                    if(row.getInvoiceNumberCollective()!=null) {
//                     XLS_SH_R_C.setCellValue(row.getInvoiceNumberCollective().toString());
//                     }else {
//                        if(row.getInvoiceNumberNonCollective()!=null) {
//                        XLS_SH_R_C.setCellValue(row.getInvoiceNumberNonCollective().toString());
//                             }
//                         }   
//                     }
//                
//                }
//
//                
//                //rowVal=rowVal+1;
//                XLS_SH_R= XLS_SH.createRow(++rowVal);        
//                XLS_SH_R= XLS_SH.createRow(++rowVal);        
//                
//                XLS_SH_R= XLS_SH.createRow(++rowVal);
//                XLS_SH_R_C=XLS_SH_R.createCell(0);
//                XLS_SH_R_C.setCellStyle(cs);        
//                XLS_SH_R_C.setCellValue("Total Price");
//                XLS_SH_R_C=XLS_SH_R.createCell(6);
//                XLS_SH_R_C.setCellStyle(csTotalAmt);  
//                if(sum!=null)
//                {
//                    XLS_SH_R_C.setCellValue(formatConversion(sum,locale));
//                }
//                 iterator.closeRowSetIterator(); 
//                
//                
//            }            
//            
//        }    
//        
//        XLS.write(outputStream);  
//        outputStream.close();        
//        
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public void specificExportExcelListener(FacesContext facesContext,
                                            OutputStream outputStream) throws IOException,
                                                                              SQLException {
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Entering getValues..");
        String selectedValues="";        
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Size =="+shuttleValue.size());
        //StringBuilder text = new StringBuilder("Size = ").append(getSelectedEmployees().size()).append(", Items added are: ");
               for (int i = 0; i <shuttleValue.size(); i++ ) {
                   //text.append("Item ").append(i).append(" = ").append(l.get(i)).append(", ");
                   _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Item ="+i+" value== "+shuttleValue.get(i));
                   selectedValues=selectedValues+shuttleValue.get(i).toString().trim()+",";                   
               }  
               _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Formed String ="+selectedValues);
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
        
        if("xls".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString()))
        {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Report in Excel Format");
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
        XLS_SH_R_C.setCellValue("Account: "+populateStringValues(getBindings().getAccount().getValue().toString()));  
        
            XLS_SH_R= XLS_SH.createRow(2);
            XLS_SH_R_C=XLS_SH_R.createCell(0);
            XLS_SH_R_C.setCellStyle(cs);        
            XLS_SH_R_C.setCellValue("Type: "+checkALL((populateStringValues(getBindings().getTransationType().getValue().toString())),"Type")); 
        
        XLS_SH_R= XLS_SH.createRow(3);
        XLS_SH_R_C=XLS_SH_R.createCell(0);
        XLS_SH_R_C.setCellStyle(cs);        
        XLS_SH_R_C.setCellValue("ReportFormat: "+getBindings().getReportFormat().getValue().toString()); 
        
        XLS_SH_R= XLS_SH.createRow(4);
        XLS_SH_R_C=XLS_SH_R.createCell(0);
        XLS_SH_R_C.setCellStyle(cs);        
        XLS_SH_R_C.setCellValue("Period: "+formatConversion((Date)getBindings().getFromDate().getValue())+" to "+formatConversion((Date)getBindings().getToDate().getValue()));
        
        for(int row=5;row<=7;row++) {
            XLS_SH_R= XLS_SH.createRow(row);            
        }                  
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Passed String ="+passedString);
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
                                time=getTimeHour(row.getTransactionTime().timestampValue());
                            }
                        XLS_SH_R_C.setCellValue(formatConversion(new Date(row.getTransactionDt().timestampValue().getTime()))+ "  "+time);
                        }
                    }else if("Account".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getAccountId()!=null)
                        {
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                        XLS_SH_R_C.setCellValue(row.getAccountId().toString());
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
                    } else if("Currency".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getPurchaseCurrency()!=null)
                        {
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                        XLS_SH_R_C.setCellValue(row.getPurchaseCurrency().toString());
                        }
                    }else if("Product".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getProductName()!=null)
                        {
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                        XLS_SH_R_C.setCellValue(row.getProductName().toString());
                        }
                    }else if("Vol".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getQuantity()!=null)
                        {
                            String unitOfMeasure="";
                            if(row.getUnitOfMeasure()!=null) {
                                unitOfMeasure=row.getUnitOfMeasure();
                            }
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csRight);
                        XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale) + " "+unitOfMeasure);
                        }
                    }else if("ForeginUnitPrice".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getCurrencyUnitPrice()!=null)
                        {
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csRight);
                        XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getCurrencyUnitPrice().toString())),locale));
                        }
                    }else if("ForeginGrossAmount".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        
                        if(row.getCurrencyGrossAmount()!=null)
                        {
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csRight);
                        XLS_SH_R_C.setCellValue(formatConversion((Float.parseFloat(row.getCurrencyGrossAmount().toString())),locale));
                        }
                    }else if("Total Amount".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Header String ="+headerValues[cellValue].toString().trim());                        
                        if(row.getInvoicedGrossAmountRebated()!=null)
                        {
                            val=true;
                            valLoc=cellValue;
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csRight);
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Total Amount ="+row.getInvoicedGrossAmountRebated());
                            XLS_SH_R_C.setCellValue(formatConversion(row.getInvoicedGrossAmountRebated(),locale));
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
                            if(row.getCard1Id()!=null)
                            {
                                XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getCard1Id().toString());
                            }
                    }else if("Vehicle No".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Vehicle Number ="+row.getVehicleNumber()); 
                            if(row.getVehicleNumber()!=null)
                            {
                                XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getVehicleNumber().toString());
                            }
                    }else if("InternalName".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Internal Name ="+row.getInternalName()); 
                            if(row.getInternalName()!=null)
                            {
                                XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getInternalName().toString());
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
                    }else if("CardGroup".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getCardgroupMainType()!=null)
                            {
                                XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getCardgroupMainType().toString() + row.getCardgroupSubType() + row.getCardgroupSeq());
                            }
                    } else if("DriverNumber".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getDriverNumber()!=null)
                            {
                                XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                                XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getDriverNumber().toString());
                            }
                    } 
                    else {
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
        System.out.println("Inside Value");
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
        else if("csv".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Report in CSV Format");
            PrintWriter out=new PrintWriter(outputStream);            
            String[] headerValues=passedString.split(",");
            for (int col = 0; col < headerValues.length; col++)
            {            
            out.print(headerValues[col].toString());
            if(col<headerValues.length-1)
            {
            out.print(";");
            }
            }
            out.println();           
            ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");                
            RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);                      
            iterator.reset();
            while (iterator.hasNext()) {                       
            PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();           
            if(row!=null) {
            
            for (int cellValue = 0; cellValue < headerValues.length; cellValue++)
            {
            
                            
                        if("Date".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getTransactionDt()!=null)
                            {      
                                
                                String time="";
                                if(row.getTransactionTime()!=null) {
                                    time=getTimeHour(row.getTransactionTime().timestampValue());
                                }
                            out.print(formatConversion(new Date(row.getTransactionDt().timestampValue().getTime()))+ " "+time);
                                 if(cellValue != headerValues.length-1)
                                 {
                                out.print(";");
                                    }
                            }
                        }else if("Account".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getAccountId()!=null)
                            {                               
                            out.print(row.getAccountId().toString());                                
                            }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("Station".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getStationName()!=null)
                            {                               
                            out.print(row.getStationName().toString());                                
                            }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        } else if("Country".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getPurchaseCountryCode()!=null)
                            {
                               out.print(row.getPurchaseCountryCode().toString());                               
                            }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("Currency".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getPurchaseCurrency()!=null)
                            {
                               out.print(row.getPurchaseCurrency().toString());                               
                            }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("Product".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getProductName()!=null)
                            {
                               out.print(row.getProductName().toString());
                            }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("Vol".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getQuantity()!=null)
                            {
                                String unitOfMeasure="";
                                if(row.getUnitOfMeasure()!=null) {
                                    unitOfMeasure=row.getUnitOfMeasure();
                                }
                                out.print(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale) + " "+unitOfMeasure);
                            }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("ForeginUnitPrice".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getCurrencyUnitPrice()!=null)
                            {
                                out.print(formatConversion((Float.parseFloat(row.getCurrencyUnitPrice().toString())),locale));
                            }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("ForeginGrossAmount".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getCurrencyGrossAmount()!=null)
                            {
                                out.print(formatConversion((Float.parseFloat(row.getCurrencyGrossAmount().toString())),locale));
                            }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("Total Amount".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                            if(row.getInvoicedNetAmountRebated()!=null)
                            {                                                          
                               out.print(formatConversion(row.getInvoicedNetAmountRebated(),locale));
                            }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("Invoice No".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {                                    
                                if(row.getInvoiceNumberCollective()!=null) {
                                 out.print(row.getInvoiceNumberCollective().toString());
                                 }else {
                                    if(row.getInvoiceNumberNonCollective()!=null) {
                                    out.print(row.getInvoiceNumberNonCollective().toString());
                                         }
                                     }   
                                
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("Card".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                if(row.getCard1Id()!=null)
                                {
                                    out.print(row.getCard1Id().toString());
                                }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("Vehicle No".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                if(row.getVehicleNumber()!=null)
                                {
                                    out.print(row.getVehicleNumber().toString());
                                }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("InternalName".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                if(row.getInternalName()!=null)
                                {
                                    out.print(row.getInternalName().toString());
                                }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("Odometer".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {                            
                            if(row.getOdometerPortal()!=null) {
                                out.print(row.getOdometerPortal().toString());
                            }else {                        
                                if(row.getOdometer()!=null) {
                                out.print(row.getOdometer().toString());
                                     }                        
                            }
                            
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("TotalKM".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                if(row.getkmTotal()!=null)
                                {
                                    out.print(row.getkmTotal().toString());
                                }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("KM/L".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                if(row.getkmPerLt()!=null)
                                {
                                    out.print(row.getkmPerLt().toString());
                                }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("L/100KM".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                if(row.getltPerHundred()!=null)
                                {
                                   out.print(row.getltPerHundred().toString());
                                }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        }else if("CardGroup".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                if(row.getCardgroupMainType()!=null)
                                {
                                   out.print(row.getCardgroupMainType().toString() + row.getCardgroupSubType() + row.getCardgroupSeq());
                                }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        } else if("DriverNumber".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                if(row.getDriverNumber()!=null)
                                {
                                   out.print(row.getDriverNumber().toString());
                                }
                            if(cellValue != headerValues.length-1)
                            {
                            out.print(";");
                               }
                        } 
                        else {
                            if("Driver Name".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                if(row.getDriverName()!=null)
                                 {
                                 out.print(row.getDriverName().toString());
                                }
                                if(cellValue != headerValues.length-1)
                                {
                                out.print(";");
                                   }
                            }
                        }
                }
            out.println();
             }
            }
            
            out.println();            
            iterator.closeRowSetIterator();
            
            out.close();
            
        }else {
            if("csv2".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString())) {                
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Report in CSV2 Format");
                PrintWriter out=new PrintWriter(outputStream);               
                String[] headerValues=passedString.split(",");                
                for (int col = 0; col < headerValues.length; col++)
                {            
                out.print(headerValues[col].toString());
                if(col<headerValues.length-1)
                {
                out.print("|");
                }
                }
                out.println();               
                ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");                
                RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);                      
                iterator.reset();
                while (iterator.hasNext()) {                       
                PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();           
                if(row!=null) {
                
                for (int cellValue = 0; cellValue < headerValues.length; cellValue++)
                {
                
                                
                                if("Date".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                    if(row.getTransactionDt()!=null)
                                    {      
                                        
                                        String time="";
                                        if(row.getTransactionTime()!=null) {
                                            time=getTimeHour(row.getTransactionTime().timestampValue());
                                        }
                                    out.print(formatConversion(new Date(row.getTransactionDt().timestampValue().getTime()))+ " "+time);
                                         if(cellValue != headerValues.length-1)
                                         {
                                        out.print("|");
                                            }
                                    }
                                }else if("Account".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                    if(row.getAccountId()!=null)
                                    {                               
                                    out.print(row.getAccountId().toString());                                
                                    }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("Station".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                    if(row.getStationName()!=null)
                                    {                               
                                    out.print(row.getStationName().toString());                                
                                    }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                } else if("Country".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                    if(row.getPurchaseCountryCode()!=null)
                                    {
                                       out.print(row.getPurchaseCountryCode().toString());                               
                                    }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("Currency".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                    if(row.getPurchaseCurrency()!=null)
                                    {
                                       out.print(row.getPurchaseCurrency().toString());                               
                                    }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("Product".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                    if(row.getProductName()!=null)
                                    {
                                       out.print(row.getProductName().toString());
                                    }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("Vol".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                    if(row.getQuantity()!=null)
                                    {
                                        String unitOfMeasure="";
                                        if(row.getUnitOfMeasure()!=null) {
                                            unitOfMeasure=row.getUnitOfMeasure();
                                        }
                                        out.print(formatConversion((Float.parseFloat(row.getQuantity().toString())),locale) + " "+unitOfMeasure);
                                    }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("ForeginUnitPrice".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                    if(row.getCurrencyUnitPrice()!=null)
                                    {
                                        out.print(formatConversion((Float.parseFloat(row.getCurrencyUnitPrice().toString())),locale));
                                    }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("ForeginGrossAmount".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                    if(row.getCurrencyGrossAmount()!=null)
                                    {
                                        out.print(formatConversion((Float.parseFloat(row.getCurrencyGrossAmount().toString())),locale));
                                    }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("Total Amount".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                    if(row.getInvoicedNetAmountRebated()!=null)
                                    {                                                          
                                       out.print(formatConversion(row.getInvoicedNetAmountRebated(),locale));
                                    }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("Invoice No".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {                                    
                                        if(row.getInvoiceNumberCollective()!=null) {
                                         out.print(row.getInvoiceNumberCollective().toString());
                                         }else {
                                            if(row.getInvoiceNumberNonCollective()!=null) {
                                            out.print(row.getInvoiceNumberNonCollective().toString());
                                                 }
                                             }   
                                        
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("Card".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                        if(row.getCard1Id()!=null)
                                        {
                                            out.print(row.getCard1Id().toString());
                                        }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("Vehicle No".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                        if(row.getVehicleNumber()!=null)
                                        {
                                            out.print(row.getVehicleNumber().toString());
                                        }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("InternalName".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                        if(row.getInternalName()!=null)
                                        {
                                            out.print(row.getInternalName().toString());
                                        }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("Odometer".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {                            
                                    if(row.getOdometerPortal()!=null) {
                                        out.print(row.getOdometerPortal().toString());
                                    }else {                        
                                        if(row.getOdometer()!=null) {
                                        out.print(row.getOdometer().toString());
                                             }                        
                                    }
                                    
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("TotalKM".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                        if(row.getkmTotal()!=null)
                                        {
                                            out.print(row.getkmTotal().toString());
                                        }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("KM/L".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                        if(row.getkmPerLt()!=null)
                                        {
                                            out.print(row.getkmPerLt().toString());
                                        }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("L/100KM".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                        if(row.getltPerHundred()!=null)
                                        {
                                           out.print(row.getltPerHundred().toString());
                                        }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                }else if("CardGroup".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                        if(row.getCardgroupMainType()!=null)
                                        {
                                           out.print(row.getCardgroupMainType().toString() + row.getCardgroupSubType() + row.getCardgroupSeq());
                                        }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                } else if("DriverNumber".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                        if(row.getDriverNumber()!=null)
                                        {
                                           out.print(row.getDriverNumber().toString());
                                        }
                                    if(cellValue != headerValues.length-1)
                                    {
                                    out.print("|");
                                       }
                                } 
                                else {
                                    if("Driver Name".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                                        if(row.getDriverName()!=null)
                                         {
                                         out.print(row.getDriverName().toString());
                                        }
                                        if(cellValue != headerValues.length-1)
                                        {
                                        out.print("|");
                                           }
                                    }
                                }
                        }
                out.println();
                 }
                }
                
                out.println();               
                iterator.closeRowSetIterator();
                
                out.close();
                
            }
        }
        if("PARTNER_ID =: partnerId".equalsIgnoreCase(partnerVO.getWhereClause())){
            partnerVO.removeNamedWhereClauseParam("partnerId");            
            partnerVO.setWhereClause("");
            partnerVO.executeQuery();            
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Removing PartnerId where clause");
        }
        
    }

    public void exportExcelSpecificAction(ActionEvent actionEvent) {        
        if("CardGroup".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {                                     
            ViewObject prtExportInfoRVO = ADFUtils.getViewObject("PrtExportInfoRVO1Iterator"); 
            prtExportInfoRVO.setNamedWhereClauseParam("country_Code", lang);
            prtExportInfoRVO.setNamedWhereClauseParam("report_Page", "TRANSACTION");
            prtExportInfoRVO.setNamedWhereClauseParam("report_Type", getBindings().getReportFormat().getValue().toString());
            prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria", getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());                
            prtExportInfoRVO.executeQuery();
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + " PrtExportInfoRVO Estimated Row Count in CardGroup:"+prtExportInfoRVO.getEstimatedRowCount());
            if(prtExportInfoRVO.getEstimatedRowCount() > 0){
                while(prtExportInfoRVO.hasNext()) {
                    PrtExportInfoRVORowImpl prtExportRow=(PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                    strCardGroup=prtExportRow.getTotalColumns();
                    strCardGroupExtra=prtExportRow.getExtraColumns();
                }
            }            
            if(strCardGroup!=null)
            {            
            String[] strHead=strCardGroup.split(","); 
            shuttleList  = new ArrayList<SelectItem>();
            for (int col = 0; col < strHead.length; col++)
            {
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(strHead[col].toString());
                selectItem.setValue(strHead[col].toString());
                shuttleList.add(selectItem);
            } 
            }
        } else if("Card".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {            
                ViewObject prtExportInfoRVO = ADFUtils.getViewObject("PrtExportInfoRVO1Iterator"); 
                prtExportInfoRVO.setNamedWhereClauseParam("country_Code", lang);
                prtExportInfoRVO.setNamedWhereClauseParam("report_Page", "TRANSACTION");
                prtExportInfoRVO.setNamedWhereClauseParam("report_Type", getBindings().getReportFormat().getValue().toString());
                prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria", getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());            
            prtExportInfoRVO.executeQuery();           
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + " PrtExportInfoRVO Estimated Row Count in Card:"+prtExportInfoRVO.getEstimatedRowCount());
            if(prtExportInfoRVO.getEstimatedRowCount() > 0){
                while(prtExportInfoRVO.hasNext()) {
                    PrtExportInfoRVORowImpl prtExportRow=(PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                    strCard=prtExportRow.getTotalColumns();
                    strCardExtra=prtExportRow.getExtraColumns();
                }
            }            
            if(strCard!=null)
            {  
            String[] strHead=strCard.split(",");     
            shuttleList  = new ArrayList<SelectItem>();
            for (int col = 0; col < strHead.length; col++)
            {
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(strHead[col].toString());
                selectItem.setValue(strHead[col].toString());
                shuttleList.add(selectItem);
            }            
            }
        }else if("Vehicle".equalsIgnoreCase(getBindings().getCardCardGrpDrVhOneRadio().getValue().toString())) {                     
            ViewObject prtExportInfoRVO = ADFUtils.getViewObject("PrtExportInfoRVO1Iterator"); 
            prtExportInfoRVO.setNamedWhereClauseParam("country_Code", lang);
            prtExportInfoRVO.setNamedWhereClauseParam("report_Page", "TRANSACTION");
            prtExportInfoRVO.setNamedWhereClauseParam("report_Type", getBindings().getReportFormat().getValue().toString());
            prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria", getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());               
            prtExportInfoRVO.executeQuery();           
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + " PrtExportInfoRVO Estimated Row Count in Vehicle:"+prtExportInfoRVO.getEstimatedRowCount());
            if(prtExportInfoRVO.getEstimatedRowCount() > 0){
                while(prtExportInfoRVO.hasNext()) {
                    PrtExportInfoRVORowImpl prtExportRow=(PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                    strVehicle=prtExportRow.getTotalColumns();
                    strVehicleExtra=prtExportRow.getExtraColumns();
                }
            }            
            if(strVehicle!=null)
            { 
            String[] strHead=strVehicle.split(",");  
            shuttleList  = new ArrayList<SelectItem>();
            for (int col = 0; col < strHead.length; col++)
            {
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(strHead[col].toString());
                selectItem.setValue(strHead[col].toString());
                shuttleList.add(selectItem);
            }
            }
        }else {
            ViewObject prtExportInfoRVO = ADFUtils.getViewObject("PrtExportInfoRVO1Iterator");                         
                prtExportInfoRVO.setNamedWhereClauseParam("country_Code", lang);
                prtExportInfoRVO.setNamedWhereClauseParam("report_Page", "TRANSACTION");
                prtExportInfoRVO.setNamedWhereClauseParam("report_Type", getBindings().getReportFormat().getValue().toString());
                prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria", getBindings().getCardCardGrpDrVhOneRadio().getValue().toString());            
            prtExportInfoRVO.executeQuery();           
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + " PrtExportInfoRVO Estimated Row Count in Driver:"+prtExportInfoRVO.getEstimatedRowCount());
            if(prtExportInfoRVO.getEstimatedRowCount() > 0){
                while(prtExportInfoRVO.hasNext()) {
                    PrtExportInfoRVORowImpl prtExportRow=(PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                    strDriver=prtExportRow.getTotalColumns();
                    strDriverExtra=prtExportRow.getExtraColumns();
                }
            }            
            if(strDriver!=null)
            { 
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
        } 
        
        boolean result=false;
        if(getBindings().getCardCardGrpDrVhOneRadio().getValue()!=null) {
            if(strCardGroup!=null) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShuttleExcel());  
                result=true;
                getBindings().getSelectionExportOneRadio().setValue(null);
                getBindings().getSpecificColumns().show(new RichPopup.PopupHints()); 
            }   else if(strCard!=null) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShuttleExcel());  
                result=true;
                getBindings().getSelectionExportOneRadio().setValue(null);
                getBindings().getSpecificColumns().show(new RichPopup.PopupHints()); 
            }else if(strVehicle!=null) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShuttleExcel());  
                result=true;
                getBindings().getSelectionExportOneRadio().setValue(null);
                getBindings().getSpecificColumns().show(new RichPopup.PopupHints()); 
            }else
            {
                if(strDriver!=null) {
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShuttleExcel());  
                result=true;
                getBindings().getSelectionExportOneRadio().setValue(null);
                getBindings().getSpecificColumns().show(new RichPopup.PopupHints()); 
                }
            }
        }
        
        if(!result) {
            if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR")) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR"),"");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }   
        }
        
       
    }

   

    public void specificExcelAction(ActionEvent actionEvent) {
        // Add event code here...  
        
    }

    public void getValuesForExcel(ActionEvent actionEvent) {  
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Size =="+shuttleValue.size());
        if(shuttleValue.size()>0 && getBindings().getSelectionExportOneRadio().getValue()!= null)
        {
        getBindings().getConfirmationExcel().show(new RichPopup.PopupHints());
        }else {      
            if(shuttleValue.size()<0)
            {
            if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR")) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR"),"");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }   
            }else {
                if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR_SELECTION")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR_SELECTION"),"");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }  
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

    public String odometerEditAction() {
            if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vnumberkey")!=null)
            {
            vehicleNumberOdometer = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vnumberkey").toString().trim();
            }
            if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("odometerportalkey") !=null){
                odometerPortal = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("odometerportalkey").toString().trim();
            }else{
                odometerPortal = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("odometerkey").toString().trim();
            }
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "uref id=================>"+AdfFacesContext.getCurrentInstance().getPageFlowScope().get("ureftranskey"));
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "pals country id=================>"+AdfFacesContext.getCurrentInstance().getPageFlowScope().get("palscountrykey"));
            urefTransactionId = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("ureftranskey").toString().trim();
            palsCountryCode = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("palscountrykey").toString().trim();
            getBindings().getEditOdometerPopup().show(new RichPopup.PopupHints());
            
            return null;
        }
    
    public String editOdometerSave() {
        User user = null;
        String modifiedBy = null;
        user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
        modifiedBy = user.getFirstName().concat(" ").concat(user.getLastName());
                
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("updateOdometerPortal");
        operationBinding.getParamsMap().put("urefTransactionId",urefTransactionId);
        operationBinding.getParamsMap().put("palsCountryCode", palsCountryCode);
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "odometer portal popup value=======>"+getBindings().getOdometerPortalValue().getValue());
        operationBinding.getParamsMap().put("modifiedBy", modifiedBy);        
        operationBinding.getParamsMap().put("odoMeterPortalValue", getBindings().getOdometerPortalValue().getValue());
        Object result = operationBinding.execute();
        if (operationBinding.getErrors().isEmpty()) {
            getBindings().getEditOdometerPopup().hide();
            searchResults();
        }
        return null;
    }
    
    public String editDriverCancel() {
        ResetUtils.reset(getBindings().getEditOdometerPopup());
        getBindings().getEditOdometerPopup().hide();
        return null;
        
    }

    public void setVehicleNumberOdometer(String vehicleNumberOdometer) {
        this.vehicleNumberOdometer = vehicleNumberOdometer;
    }

    public String getVehicleNumberOdometer() {
        return vehicleNumberOdometer;
    }

    public void setOdometerPortal(String odometerPortal) {
        this.odometerPortal = odometerPortal;
    }

    public String getOdometerPortal() {
        return odometerPortal;
    }

    public void setUrefTransactionId(String urefTransactionId) {
        this.urefTransactionId = urefTransactionId;
    }

    public String getUrefTransactionId() {
        return urefTransactionId;
    }

    public void setPalsCountryCode(String palsCountryCode) {
        this.palsCountryCode = palsCountryCode;
    }

    public String getPalsCountryCode() {
        return palsCountryCode;
    }

 public void  odometerLink_Action(ActionEvent event) {
//        FacesContext context = FacesContext.getCurrentInstance();
//           UIComponent source = (UIComponent)event.getSource();
//           String alignId = source.getClientId(context);
//           RichPopup.PopupHints hints = new RichPopup.PopupHints();
//              hints.add(RichPopup.PopupHints.HintTypes.HINT_ALIGN_ID,source)
//              .add(RichPopup.PopupHints.HintTypes.HINT_LAUNCH_ID,source)
//              .add(RichPopup.PopupHints.HintTypes.HINT_ALIGN,
//              RichPopup.PopupHints.AlignTypes.ALIGN_AFTER_END);
          
        getBindings().getOdometer_PopUp().show(new RichPopup.PopupHints());
       
    }
 public static Object invokeEL(String el, Class[] paramTypes, Object[] params) {
           FacesContext facesContext = FacesContext.getCurrentInstance();
           ELContext elContext = facesContext.getELContext();
           ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
           MethodExpression exp = expressionFactory.createMethodExpression(elContext, el, Object.class, paramTypes);

           return exp.invoke(elContext, params);
       }

    public void queryListener(QueryEvent queryEvent) {
        // Add event code here...
        invokeEL("#{bindings.PrtCardTransactionOverviewRVO12Query.processQuery}", new Class[] { QueryEvent.class }, new Object[] { queryEvent });
        ViewObject prtCardTransactionOverViewRVO = ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");                
        RowSetIterator iterator = prtCardTransactionOverViewRVO.createRowSetIterator(null);   
        System.out.println("Summ =="+sum);
        iterator.reset();
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "Estimated Row Count ="+prtCardTransactionOverViewRVO.getEstimatedRowCount());
        sum=0.000f;
        while (iterator.hasNext()) {                       
        PrtCardTransactionOverviewRVORowImpl row = (PrtCardTransactionOverviewRVORowImpl)iterator.next();  
            System.out.println("Temp ="+row.getInvoicedGrossAmountRebated());
            Float temp = row.getInvoicedGrossAmountRebated();            
            sum = sum + temp;
        }  
        System.out.println("Summ =="+sum);
    }

    public void selectionExport(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        if("xls".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString()))
        {            
            contentType="application/vnd.ms-excel";
        }else if("csv".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString()))
        {
            contentType="text/plain";
        }else {
            if("csv2".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString()))
                    {
                        contentType="text/plain";
                    }
        }
        return contentType;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        
        if("xls".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString()))
        {           
            fileName="Transaction_Report.xls";           
        }else if("csv".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString()))
        {
            fileName="Transaction_Report.csv";
        }else {
            if("csv2".equalsIgnoreCase(getBindings().getSelectionExportOneRadio().getValue().toString()))
                    {
                        fileName="Transaction_Report.csv";
                    }
        }
        return fileName;
    }

    public void setPartnerIdList(ArrayList<SelectItem> partnerIdList) {
        this.partnerIdList = partnerIdList;
    }

    public ArrayList<SelectItem> getPartnerIdList() {
        return partnerIdList;
    }

    public void setPartnerIdValue(String partnerIdValue) {
        this.partnerIdValue = partnerIdValue;
    }

    public String getPartnerIdValue() {
        return partnerIdValue;
    }

    public ArrayList<SelectItem> getReportFormatList() {        
        if (reportFormatList == null) {
            reportFormatList = new ArrayList<SelectItem>();            
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel("Default");
            selectItem.setValue("Default");
            reportFormatList.add(selectItem);
            SelectItem selectItem1 = new SelectItem();
            selectItem1.setLabel("Raw Data");
            selectItem1.setValue("Raw Data");
            reportFormatList.add(selectItem1);
            SelectItem selectItem2 = new SelectItem();
            selectItem2.setLabel("International");
            selectItem2.setValue("International");
            reportFormatList.add(selectItem2);
            SelectItem selectItem3 = new SelectItem();
            selectItem3.setLabel("Price Specification");
            selectItem3.setValue("Price Specification");
            reportFormatList.add(selectItem3);
        }
        return reportFormatList;
    }

    public void setReportFormatValue(String reportFormatValue) {
        this.reportFormatValue = reportFormatValue;
    }

    public String getReportFormatValue() {
        return reportFormatValue;
    }

    public void setReportDefault(boolean reportDefault) {
        this.reportDefault = reportDefault;
    }

    public boolean isReportDefault() {
        return reportDefault;
    }

    public void setReportRawData(boolean reportRawData) {
        this.reportRawData = reportRawData;
    }

    public boolean isReportRawData() {
        return reportRawData;
    }

    public void setReportInternationalTx(boolean reportInternationalTx) {
        this.reportInternationalTx = reportInternationalTx;
    }

    public boolean isReportInternationalTx() {
        return reportInternationalTx;
    }

    public void setReportPriceSpecification(boolean reportPriceSpecification) {
        this.reportPriceSpecification = reportPriceSpecification;
    }

    public boolean isReportPriceSpecification() {
        return reportPriceSpecification;
    }

    public class Bindings {
        private RichSelectManyChoice account;
        private RichSelectOneChoice partner;
        private RichSelectOneChoice reportFormat;
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
        private RichSelectOneRadio selectionExportOneRadio;
        private RichPanelGroupLayout showSearchResultPG;
        private RichPopup specificColumns;
        private RichPopup confirmationExcel;
        private RichSelectManyShuttle shuttleExcel;
        private RichPanelGroupLayout selectedPGL;
        private RichPopup editOdometerPopup;
        private RichInputText odometerPortalValue;
        private RichPopup odometer_PopUp;
        private RichTable searchTable;
        
        public void setOdometer_PopUp(RichPopup odometer_PopUp) {
            this.odometer_PopUp = odometer_PopUp;
        }

        public RichPopup getOdometer_PopUp() {
            return odometer_PopUp;
        }
        
        
        public void setAccount(RichSelectManyChoice account) {
            this.account = account;
        }

        public RichSelectManyChoice getAccount() {
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

        public void setEditOdometerPopup(RichPopup editOdometerPopup) {
            this.editOdometerPopup = editOdometerPopup;
        }

        public RichPopup getEditOdometerPopup() {
            return editOdometerPopup;
        }
        
        public void setOdometerPortalValue(RichInputText odometerPortalValue) {
            this.odometerPortalValue = odometerPortalValue;
        }

        public RichInputText getOdometerPortalValue() {
            return odometerPortalValue;
        }

        public void setSearchTable(RichTable searchTable) {
            this.searchTable = searchTable;
        }

        public RichTable getSearchTable() {
            return searchTable;
        }

        public void setSelectionExportOneRadio(RichSelectOneRadio selectionExportOneRadio) {
            this.selectionExportOneRadio = selectionExportOneRadio;
        }

        public RichSelectOneRadio getSelectionExportOneRadio() {
            return selectionExportOneRadio;
        }

        public void setPartner(RichSelectOneChoice partner) {
            this.partner = partner;
        }

        public RichSelectOneChoice getPartner() {
            return partner;
        }

        public void setReportFormat(RichSelectOneChoice reportFormat) {
            this.reportFormat = reportFormat;
        }

        public RichSelectOneChoice getReportFormat() {
            return reportFormat;
        }
    }
}
    
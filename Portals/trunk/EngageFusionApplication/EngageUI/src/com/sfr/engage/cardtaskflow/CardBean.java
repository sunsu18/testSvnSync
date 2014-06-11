package com.sfr.engage.cardtaskflow;

import com.sfr.engage.core.PartnerInfo;

import com.sfr.engage.core.ValueListSplit;
import com.sfr.engage.invoiceoverviewtaskflow.InvoiceOverviewBean;
import com.sfr.engage.model.queries.rvo.PrtCardDriverVehicleInfoRVORowImpl;
import com.sfr.engage.model.queries.rvo.PrtCardTransactionOverviewRVORowImpl;
import com.sfr.engage.model.queries.rvo.PrtExportInfoRVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtCardgroupVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtPartnerVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtViewCardsVORowImpl;import com.sfr.engage.model.queries.uvo.PrtViewVehicleDriverVORowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.ADFUtils;
import com.sfr.util.AccessDataControl;

import com.sfr.util.constants.Constants;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import java.sql.SQLException;

import java.sql.Timestamp;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
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
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectManyShuttle;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.javatools.marshal.StringConversion;

import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CardBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    private Boolean isTableVisible=false;
    private PartnerInfo partnerInfo;
    private List<PartnerInfo> partnerInfoList;
    private String cardGroupSubtypePassValues;
    private String cardGroupMaintypePassValue;
    private String cardGroupSeqPassValues;
    private String partnerId;
    private String partnerCountry;
    private String lang;
    public static final ADFLogger _logger = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();
    private ArrayList<SelectItem> accountIdList;
    private List<String> accountIdValue;
    private ArrayList<SelectItem> cardGroupList;
    private List<String> cardGroupValue;
    private ArrayList<SelectItem> statusList;
    private List<String> statusValue;
    private ArrayList<SelectItem> partnerIdList;
    private String partnerIdValue;
    ResourceBundle resourceBundle;
    private boolean driverPGL   = false;
    private boolean vehiclePGL = false;
    private String cardAssociation = null;
    private String associatedAccount = null;
    private ArrayList<SelectItem> vehicleNumberList;
    private String vehicleNumberValue;
    private ArrayList<SelectItem> driverNameList;
    private String driverNameValue;
    Map<String,String> truckDriverList = new HashMap<String,String>();
    private String displayDriverName;
    private String  displayVehicleName;
    private String strViewCardTotalColumns="";
    private String strViewCardExtraColumns="";
    private String strViewCardPrepopulatedColumns="";
    private List shuttleList = new ArrayList();
    private List shuttleValue;
    private boolean shuttleStatus=false;
    private String driverNumber = null;
    private String vehicleNumber = null;
    private RichSelectOneChoice driverNameAssociation;
    private String warningMsg = null;
    private String infoMsg = null;
    private boolean showErrorMsgEditFlag = false;
    private String internalCardNumber = null;
    private String cardEmbossNum = null;
    private String InternalName=null;
        private String DriverNumber=null;
        private String DriverName=null;
        private String VehicleNumber=null;
        private String vehicleModifiedBy =null;
        private String vehicleModifiedDate =null;
        private String driverModifiedBy =null;
        private String  driverModifiedDate =null;
        private boolean vehicleModifiedByVisible =false;
        private boolean vehicleModifiedDateVisible =false;
        private boolean driverModifiedByVisible =false;
        private boolean  driverModifiedDateVisible =false; 
        private boolean  showEditInfoMessage =false;
        private ValueListSplit valueList;
        private String accountQuery="(";
        private String cardGroupQuery="(";
        private Map<String,String> mapAccountListValue; 
        private Map<String,String> mapCardGroupListValue;      
       
        
   public CardBean() {
        super();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        statusValue = new ArrayList<String>();
        valueList = new ValueListSplit();

        resourceBundle = new EngageResourceBundle();
        partnerId=null;
        if(session.getAttribute("Partner_Object_List") != null){
            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
            if(partnerInfoList!=null && partnerInfoList.size()>0){
                partnerIdList = new ArrayList<SelectItem>();
                for(int k=0;k<partnerInfoList.size();k++){
                    SelectItem selectItem = new SelectItem();
                    if(partnerInfoList.get(k).getPartnerName()!=null && partnerInfoList.get(k).getPartnerValue()!=null)
                    {
                    selectItem.setLabel(partnerInfoList.get(k).getPartnerName().toString());
                    selectItem.setValue(partnerInfoList.get(k).getPartnerValue().toString());
                    partnerIdList.add(selectItem);
                    }
                }
            }
        }
        
        if(session!=null) {
            if(session.getAttribute("view_card_account_Query")!=null)
            {            
            accountQuery=session.getAttribute("view_card_account_Query").toString().trim();
            mapAccountListValue= (Map<String,String>)session.getAttribute("map_Account_List");
            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                 "account Query & mapAccountList is found");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                     "account "+accountQuery);
            }
            if(session.getAttribute("view_card_cardGroup_Query")!=null)
            {
            cardGroupQuery=session.getAttribute("view_card_cardGroup_Query").toString().trim();
            mapCardGroupListValue= (Map<String,String>)session.getAttribute("map_CardGroup_List");
             _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                 "CardGroup Query & mapCardGroupList is found");
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                                     "CardGroup "+cardGroupQuery);
            }            
        }

        statusValue.add("0");
        

        if(session!= null) {
        lang = (String)session.getAttribute(Constants.userLang);
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

    public ArrayList<SelectItem> getAccountIdList() {
        return accountIdList;
    }

    public void setAccountIdValue(List<String> accountIdValue) {
        this.accountIdValue = accountIdValue;
    }

    public List<String> getAccountIdValue() {
        return accountIdValue;
    }

    public void setCardGroupList(ArrayList<SelectItem> cardGroupList) {
        this.cardGroupList = cardGroupList;
    }

    public ArrayList<SelectItem> getCardGroupList() {
        return cardGroupList;
    }

    public void setCardGroupValue(List<String> cardGroupValue) {
        this.cardGroupValue = cardGroupValue;
    }

    public List<String> getCardGroupValue() {
        return cardGroupValue;
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

    public String[] StringConversion(String passedVal) {
        List<String> container;
//        String tempString = passedVal.substring(1, passedVal.length() - 1);
        String[] val = passedVal.split(",");
        return val;
    }

    /**
     * @param valueChangeEvent
     */
    public void accountValueChangeListener(ValueChangeEvent valueChangeEvent) {
        isTableVisible = false;
        if(valueChangeEvent.getNewValue()!=null) {

        String[] accountString= StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()));
        cardGroupList = new ArrayList<SelectItem>();
        cardGroupValue = new ArrayList<String>();

            for(int z=0 ; z<partnerInfoList.size(); z++){

        if(partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0){
            for(int i=0 ; i<partnerInfoList.get(z).getAccountList().size(); i++){
                for(int j=0;j<accountString.length;j++) {
                    if(partnerInfoList.get(z).getAccountList().get(i).getAccountNumber()!= null && partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().equals(accountString[j].trim())){
                        if(partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null && partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size()>0){
                            for(int k =0 ; k< partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size(); k++){
                                if(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID()!= null){
                                SelectItem selectItem = new SelectItem();
                                selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupName().toString());
                                selectItem.setValue(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                cardGroupList.add(selectItem);
                                cardGroupValue.add(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                }
                            }
                        }
                    }
                }
            }
        }
            }
        }
        else{
            getBindings().getCardGroup().setValue(null);
            getBindings().getStatus().setValue(null);
            this.cardGroupValue=null;
            this.statusValue=null;
        }

    }




    public void setStatusList(ArrayList<SelectItem> statusList) {
        this.statusList = statusList;
    }

    /**
     * @param errorVar
     * @return
     */
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

    public ArrayList<SelectItem> getStatusList() {

        if (statusList == null) {
            statusList = new ArrayList<SelectItem>();
            SelectItem selectItem = new SelectItem();
          
           if (resourceBundle.containsKey("UNBLOCKED"))
           {
            selectItem.setLabel(resourceBundle.getObject("UNBLOCKED").toString());
            selectItem.setValue("0");
            statusList.add(selectItem);
           }
            SelectItem selectItem1 = new SelectItem();
        if (resourceBundle.containsKey("TEMPORARY_BLOCKED"))
        {
            selectItem1.setLabel(resourceBundle.getObject("TEMPORARY_BLOCKED").toString());
            selectItem1.setValue("1");
            statusList.add(selectItem1);
        }
            SelectItem selectItem2 = new SelectItem();
        if (resourceBundle.containsKey("PERMANENT_BLOCKED"))
        {
            selectItem2.setLabel(resourceBundle.getObject("PERMANENT_BLOCKED").toString());
            selectItem2.setValue("2");
            statusList.add(selectItem2);
        }


    }
    return statusList;
    }

    public void setStatusValue(List<String> statusValue) {
        this.statusValue = statusValue;
    }

    public List<String> getStatusValue() {
    
      
        return statusValue;
    }

    public void setAccountIdList(ArrayList<SelectItem> accountIdList) {
        this.accountIdList = accountIdList;
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

    public void setPartnerInfoList(List<PartnerInfo> partnerInfoList) {
        this.partnerInfoList = partnerInfoList;
    }

    public List<PartnerInfo> getPartnerInfoList() {
        return partnerInfoList;
    }

    public void partnerValueChangeListener(ValueChangeEvent valueChangeEvent) {
    isTableVisible = false;

        if(valueChangeEvent.getNewValue()!=null) {
      accountIdList  = new ArrayList<SelectItem>();
        accountIdValue = new ArrayList<String>();
        cardGroupList = new ArrayList<SelectItem>();
        cardGroupValue = new ArrayList<String>();

            String partnerSelected = valueChangeEvent.getNewValue().toString();
                 if( partnerSelected!= null){
                     for(int i=0 ; i<partnerInfoList.size(); i++){
                         if(partnerInfoList.get(i).getPartnerValue().toString()!= null && partnerInfoList.get(i).getPartnerValue().toString().equals(partnerSelected.trim())){
                             if(partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0){

                                 for(int j=0;j<partnerInfoList.get(i).getAccountList().size();j++){

                                     if(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() != null){

                                         SelectItem selectItem = new SelectItem();
                                         selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                         selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                         accountIdList.add(selectItem);
                                         accountIdValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                     
                                     }


                                     for(int k=0;k<partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size();k++){
                                     if(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() != null){

                                         SelectItem selectItem = new SelectItem();
                                         selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupName().toString());
                                         selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                         cardGroupList.add(selectItem);
                                         cardGroupValue.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                       
                                     }

                                }
                            }
                        }
                    }
                }
                }
                statusValue.add("0");
                statusValue.add("1");
                statusValue.add("2");
            }
        else {
            getBindings().getCardGroup().setValue(null);
            getBindings().getAccount().setValue(null);
            this.cardGroupValue = null;
            this.cardGroupList = null;
            this.accountIdValue = null;
            this.accountIdList = null;
        }



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

              _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " "   + "card group main type======>"+cardGroupMaintypePassValue);
              _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " "   + "card group sub type===>"+cardGroupSubtypePassValues);
              _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " "   + "card group sequence value====>"+cardGroupSeqPassValues);
        }
    }

    public void setIsTableVisible(Boolean isTableVisible) {
        this.isTableVisible = isTableVisible;
    }

    public Boolean getIsTableVisible() {
        return isTableVisible;
    }

    public String searchViewCardAction_event(ActionEvent actionEvent) {
        searchResults();
        return null;
    }


    public String searchResults() {
        isTableVisible = false;
//        String accountPassingValues = null;
        String statusPassingValues = null;
//        String cardGroupPassingValues = null;
        try {
            if (getBindings().getPartner().getValue() != null) {
                if (getBindings().getAccount().getValue() != null) {
//                    accountPassingValues = populateStringValues(getBindings().getAccount().getValue().toString());
                } else {
                    showErrorMessage("ENGAGE_NO_ACCOUNT");
                    return null;
                }

                if (getBindings().getStatus().getValue() != null) {
                    statusPassingValues = populateStringValues(getBindings().getStatus().getValue().toString());
                } else {
                    showErrorMessage("ENGAGE_NO_STATUS");
                    return null;
                }

                if (getBindings().getCardGroup().getValue() != null) {
//                    cardGroupPassingValues = populateStringValues(getBindings().getCardGroup().getValue().toString());
//                    populateCardGroupValues(cardGroupPassingValues);

                } else {
                    showErrorMessage("ENGAGE_NO_CARD_GROUP");
                    return null;
                }
                
                
                


                
                
                if (getBindings().getPartner().getValue() != null) {
                    ViewObject vo = ADFUtils.getViewObject("PrtViewCardsVO1Iterator");
                    
                    //remove acc & cardgroup
                    
                    if(accountQuery.length()>1 && accountQuery != null && cardGroupQuery.length() > 1){
                        if(vo.getWhereClause() != null){
                            if(((accountQuery+"AND "+ cardGroupQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim())) || 
                               ((accountQuery+" AND "+ cardGroupQuery).trim().equalsIgnoreCase(vo.getWhereClause().trim()))) {
                                if(mapAccountListValue!=null){
                                    for(int i=0;i< mapAccountListValue.size();i++) {
                                            String values="account"+i;                            
                                            vo.removeNamedWhereClauseParam(values);
                                    }
                                }else{
                                    vo.removeNamedWhereClauseParam("account");
                                }
                                if(mapCardGroupListValue!=null){
                                    for(int i=0;i< mapCardGroupListValue.size();i++) {
                                            String values="cardGroup"+i;                            
                                            vo.removeNamedWhereClauseParam(values);
                                    }
                                }else{
                                    vo.removeNamedWhereClauseParam("cardGroup");
                                }
                                
                                vo.setWhereClause("");
                                vo.executeQuery();
                            }
                        }                        
                    }
                    accountQuery="(";
                    cardGroupQuery="(";
                    
                    //account query
                    
                    if(accountIdValue.size()>250) {      
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                         " " + "Account Values > 250 ");
                        mapAccountListValue=valueList.callValueList(accountIdValue.size(), accountIdValue);         
                        for(int i=0;i<mapAccountListValue.size();i++) {
                            String values="account"+i;
                            accountQuery=accountQuery+"INSTR(:"+values+",ACCOUNT_ID)<>0 OR ";
                        }
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +"Account Query Values ="+accountQuery);
                        accountQuery=accountQuery.substring(0, accountQuery.length()-3);
                        accountQuery=accountQuery+")";        
                    }
                    else {
                        mapAccountListValue=null;
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                          " " + "Account Values < 250 ");
                        accountQuery="(INSTR(:account,ACCOUNT_ID)<>0 ) ";                 
                    } 
                    
                    
                    //cardgroup query
                    
                    
                    if(cardGroupValue.size()>250) {      
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                         " " + "CardGroup Values > 250 ");
                        mapCardGroupListValue=valueList.callValueList(cardGroupValue.size(), cardGroupValue);         
                        for(int i=0;i<mapCardGroupListValue.size();i++) {
                            String values="cardGroup"+i;
                            cardGroupQuery=cardGroupQuery+"INSTR(:"+values+",CARDGROUP_MAIN_TYPE||CARDGROUP_SUB_TYPE||CARDGROUP_SEQ)<>0 OR ";
                        }
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +"CARDGROUP Query Values ="+cardGroupQuery);
                        cardGroupQuery=cardGroupQuery.substring(0, cardGroupQuery.length()-3);
                        cardGroupQuery=cardGroupQuery+")"; 
                    }
                    else {
                        mapCardGroupListValue=null;
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                         " " + "CardGroup Values < 250 ");
                        cardGroupQuery="(INSTR(:cardGroup,CARDGROUP_MAIN_TYPE||CARDGROUP_SUB_TYPE||CARDGROUP_SEQ)<>0) ";                 
                    } 
                    
                    
                    
//                    vo.setNamedWhereClauseParam("accountID", accountPassingValues);
                    vo.setNamedWhereClauseParam("partnerId", getBindings().getPartner().getValue().toString().trim());
                    vo.setNamedWhereClauseParam("status", statusPassingValues);
//                    vo.setNamedWhereClauseParam("cgMain", cardGroupMaintypePassValue);
//                    vo.setNamedWhereClauseParam("cgSub", cardGroupSubtypePassValues);
//                    vo.setNamedWhereClauseParam("cgSeq", cardGroupSeqPassValues);
                    vo.setNamedWhereClauseParam("countryCd", lang);
                    
                    vo.setWhereClause(accountQuery+"AND "+cardGroupQuery);
                    
                    if(accountIdValue.size()>250) {      
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                         " " + "Account Values > 250 ");
                        mapAccountListValue=valueList.callValueList(accountIdValue.size(), accountIdValue); 
                        for(int i=0;i<mapAccountListValue.size();i++) {
                            String values="account"+i;
                            String listName="listName"+i;
                            vo.defineNamedWhereClauseParam(values, mapAccountListValue.get(listName),
                                                                            null);
                        } 
                    }
                    else {
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                          " " + "Account Values < 250 ");
                        vo.defineNamedWhereClauseParam("account", populateStringValues(getBindings().getAccount().getValue().toString()),null);
                    }
                    
                    
                    if(cardGroupValue.size()>250) {      
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                          " " + "CardGroup Values > 250 ");
                        mapCardGroupListValue=valueList.callValueList(cardGroupValue.size(), cardGroupValue); 
                        for(int i=0;i<mapCardGroupListValue.size();i++) {
                            String values="cardGroup"+i;
                            String listName="listName"+i;
                            vo.defineNamedWhereClauseParam(values, mapCardGroupListValue.get(listName),
                                                                                    null);
                        }
                    }
                    else{
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                         " " + "CardGroup Values < 250 ");
                        vo.defineNamedWhereClauseParam("cardGroup", populateStringValues(getBindings().getCardGroup().getValue().toString()),null);
                    }
                    
                    vo.executeQuery();
                    
                    session.setAttribute("view_card_account_Query",accountQuery);
                    session.setAttribute("view_card_map_Account_List",mapAccountListValue);
                    session.setAttribute("view_card_cardGroup_Query",cardGroupQuery);
                    session.setAttribute("view_card_map_CardGroup_List",mapCardGroupListValue);
                    _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " +"Queries are saved in session");
                    
                    isTableVisible = true;
                }
            } else {
                showErrorMessage("ENGAGE_NO_PARTNER");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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

    public void clearSearchListener(ActionEvent actionEvent) {
        getBindings().getPartner().setValue(null);
        getBindings().getStatus().setValue(null);
        this.partnerIdValue = null;
        this.accountIdValue = null;
        accountIdList = new ArrayList<SelectItem>();
        this.cardGroupValue = null;
        cardGroupList = new ArrayList<SelectItem>();
        statusValue.add("0");
        statusValue.add("1");
        statusValue.add("2");
        isTableVisible=false;

    }

    public void radioButtonValueChangeListener(ValueChangeEvent valueChangeEvent) {
        
        if (valueChangeEvent.getNewValue() != null) {
            if (valueChangeEvent.getNewValue().equals("Driver")) {
                this.getBindings().getVehicleNumber().setValue(null);
                this.displayVehicleName = null;
                showErrorMsgEditFlag = false;
                driverPGL = true;
                vehiclePGL = false;
                vehicleModifiedByVisible=false;
                vehicleModifiedDateVisible=false;
                driverModifiedByVisible=false;
                driverModifiedDateVisible=false;
                populateValue(valueChangeEvent.getNewValue().toString());

                 if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") != null){
                    driverNameValue=AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber").toString().trim();
                    if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedBy") != null &&
                       AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedDate") != null){
                         vehicleModifiedByVisible=false;
                         vehicleModifiedDateVisible=false;
                         driverModifiedByVisible=true;
                         driverModifiedDateVisible=true;
                         driverModifiedBy= AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedBy").toString().trim();
                         driverModifiedDate=AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedDate").toString().trim();
                     } 
                 }
                 
                if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverName") != null){
                    displayDriverName=AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverName").toString().trim();  
                }
                     
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNumber());
            }
            else if (valueChangeEvent.getNewValue().equals("Vehicle")) {
                this.getBindings().getDriverNumber().setValue(null);
                showErrorMsgEditFlag = false;
                this.displayDriverName = null;
                driverPGL = false;
                vehiclePGL = true;
                populateValue(valueChangeEvent.getNewValue().toString());
                if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") != null){
                    vehicleNumberValue = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber").toString().trim();
                    if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedBy") != null &&
                       AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedDate") != null ){
                        driverModifiedByVisible=false;
                        driverModifiedDateVisible=false;
                        vehicleModifiedByVisible=true;
                        vehicleModifiedDateVisible=true;
                        vehicleModifiedBy= AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedBy").toString().trim();
                        vehicleModifiedDate=AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedDate").toString().trim();
                    } 
                }
                     
                     if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("InternalName") != null)
                     {
                         displayVehicleName=AdfFacesContext.getCurrentInstance().getPageFlowScope().get("InternalName").toString().trim();  
                     }
                     
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleNumber());
              
            }
        }
    }

    public void setDriverPGL(boolean driverPGL) {
        this.driverPGL = driverPGL;
    }

    public boolean isDriverPGL() {
        return driverPGL;
    }

    public void setVehiclePGL(boolean vehiclePGL) {
        this.vehiclePGL = vehiclePGL;
    }

    public boolean isVehiclePGL() {
        return vehiclePGL;
    }

    public void statusValueChangeListener(ValueChangeEvent valueChangeEvent) {
       isTableVisible = false;
    }

    public void cardGroupValueChangeListener(ValueChangeEvent valueChangeEvent) {
       isTableVisible = false;
    }

    public void setCardAssociation(String cardAssociation) {
        this.cardAssociation = cardAssociation;
    }

    public String getCardAssociation() {
        return cardAssociation;
    }

  

    public void populateValue(String paramType){
      
        if (paramType != null) {
            associatedAccount = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim();
            if (paramType.equals("Vehicle") || paramType.equals("Driver")) {
                if (vehiclePGL) {
                    vehicleNumberList = new ArrayList<SelectItem>();
                }
                if (driverPGL) {
                    driverNameList = new ArrayList<SelectItem>();
                }
                ViewObject vo = ADFUtils.getViewObject("PrtViewVehicleDriverVO1Iterator");
                if (associatedAccount != null) {
                    vo.setNamedWhereClauseParam("accountValue", associatedAccount);
                }
                vo.setNamedWhereClauseParam("countryCd", lang);
                vo.setNamedWhereClauseParam("paramValue", paramType);
                if (paramType == "Driver") {
                    vo.setNamedWhereClauseParam("driverNumber", null);
                } else {
                    vo.setNamedWhereClauseParam("vehicleNumber", null);
                }
                vo.executeQuery();
           
                if (vo.getEstimatedRowCount() > 0) {
                    for (int n = 0; n < vo.getEstimatedRowCount(); n++) {
                        while (vo.hasNext()) {
                            PrtViewVehicleDriverVORowImpl currRow = (PrtViewVehicleDriverVORowImpl)vo.next();
                            if (currRow != null) {
                                if (paramType.equals("Vehicle")) {
                                    SelectItem selectItem = new SelectItem();
                                    if (currRow.getAttribute("VehicleNumber") != null) {
                                        selectItem.setLabel(currRow.getVehicleNumber().toString().trim());
                                        selectItem.setValue(currRow.getVehicleNumber().toString().trim());
                                        truckDriverList.put(currRow.getVehicleNumber().toString(), currRow.getInternalName());
                                    }
                                    vehicleNumberList.add(selectItem);
                                } else {
                                    SelectItem selectItem = new SelectItem();
                                    if (currRow.getAttribute("DriverNumber") != null) {
                                        selectItem.setLabel(currRow.getDriverNumber().toString().trim());
                                        selectItem.setValue(currRow.getDriverNumber().toString().trim());
                                        truckDriverList.put(currRow.getDriverNumber().toString(), currRow.getDriverName());
                                    }
                                    driverNameList.add(selectItem);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void setAssociatedAccount(String associatedAccount) {
        this.associatedAccount = associatedAccount;
    }

    public String getAssociatedAccount() {
        return associatedAccount;
    }

    public void setVehicleNumberList(ArrayList<SelectItem> vehicleNumberList) {
        this.vehicleNumberList = vehicleNumberList;
    }

    public ArrayList<SelectItem> getVehicleNumberList() {
        return vehicleNumberList;
    }

    public void setVehicleNumberValue(String vehicleNumberValue) {
        this.vehicleNumberValue = vehicleNumberValue;
    }

    public String getVehicleNumberValue() {
        return vehicleNumberValue;
    }

    public void setDriverNameList(ArrayList<SelectItem> driverNameList) {
        this.driverNameList = driverNameList;
    }

    public ArrayList<SelectItem> getDriverNameList() {
        return driverNameList;
    }

    public void setDriverNameValue(String driverNameValue) {
//        if( AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") != null){
//            this.driverNameValue = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber").toString().trim();
//        }
        this.driverNameValue = driverNameValue;
    }

    public String getDriverNameValue() {
        return driverNameValue;
    }

    public void setTruckDriverList(Map<String, String> truckDriverList) {
        this.truckDriverList = truckDriverList;
    }

    public Map<String, String> getTruckDriverList() {
        return truckDriverList;
    }

    public void driverValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
         
            displayDriverName = truckDriverList.get(valueChangeEvent.getNewValue().toString());
           

        }
    }

    public void setDisplayDriverName(String displayDriverName) {
        this.displayDriverName = displayDriverName;
    }

    public String getDisplayDriverName() {
        return displayDriverName;
    }

    public void vehicleValueChangeListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {

            displayVehicleName = truckDriverList.get(valueChangeEvent.getNewValue().toString());

        }
    }

    public void setDisplayVehicleName(String displayVehicleName) {
        this.displayVehicleName = displayVehicleName;
    }

    public String getDisplayVehicleName() {
        return displayVehicleName;
    }

   public void checkVehicleAssociation() {

      
       

        ViewObject vehicleVo = ADFUtils.getViewObject("PrtViewVehicleDriverVO1Iterator");

       
        vehicleVo.setNamedWhereClauseParam("countryCd", lang);

        vehicleVo.setNamedWhereClauseParam("paramValue", "Vehicle");
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount") != null) {
         
            vehicleVo.setNamedWhereClauseParam("accountValue",
                                               AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim());
        }

        if (getBindings().getVehicleNumber().getValue() != null) {
           
            vehicleVo.setNamedWhereClauseParam("vehicleNumber", getBindings().getVehicleNumber().getValue());

        }
        vehicleVo.executeQuery();
       
        if (vehicleVo.getEstimatedRowCount() > 0) {
         
            while (vehicleVo.hasNext()) {
             
                PrtViewVehicleDriverVORowImpl currRow = (PrtViewVehicleDriverVORowImpl)vehicleVo.next();
            
                if (currRow != null) {
                    if (currRow.getCardNumber() != null) {

                           
                        if (resourceBundle.containsKey("TRUCK_CARD_ALREADY_EXIST")) {

                            showErrorMsgEditFlag = true;
                            warningMsg = resourceBundle.getObject("TRUCK_CARD_ALREADY_EXIST").toString().concat(" ").concat(currRow.getCardEmbossNum());
                            
                        }
                    
                    }

                    else {

                        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber") != null) {
                            internalCardNumber = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber").toString().trim();
                        }
                        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount") != null) {
                            associatedAccount = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim();
                        }
                        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
                        OperationBinding operationBinding = bindings.getOperationBinding("updateVehicleDriver");
                        operationBinding.getParamsMap().put("cardNumber", internalCardNumber);
                        operationBinding.getParamsMap().put("type", "Vehicle");
                        operationBinding.getParamsMap().put("countryCd", lang);
                        operationBinding.getParamsMap().put("vehicleDriverValue", getBindings().getVehicleNumber().getValue());
                        operationBinding.getParamsMap().put("associatedAccount", associatedAccount);

                        Object result = operationBinding.execute();
                        
                        String accountPassingValues = null;
                        String statusPassingValues = null;
                        String cardGroupPassingValues = null;
                        
                        if (getBindings().getPartner().getValue() != null) {
                            if (getBindings().getAccount().getValue() != null) {
                                accountPassingValues = populateStringValues(getBindings().getAccount().getValue().toString());
                            } else {
                                showErrorMessage("ENGAGE_NO_ACCOUNT");
                               
                            }

                            if (getBindings().getStatus().getValue() != null) {
                                statusPassingValues = populateStringValues(getBindings().getStatus().getValue().toString());
                            } else {
                                showErrorMessage("ENGAGE_NO_STATUS");
                                
                            }

                            if (getBindings().getCardGroup().getValue() != null) {
                                cardGroupPassingValues = populateStringValues(getBindings().getCardGroup().getValue().toString());
                                populateCardGroupValues(cardGroupPassingValues);

                            } else {
                                showErrorMessage("ENGAGE_NO_CARD_GROUP");
                               
                            }

                            if (getBindings().getPartner().getValue() != null) {
                                ViewObject vo = ADFUtils.getViewObject("PrtViewCardsVO1Iterator");
                                
                                
                                
                                if(session.getAttribute("view_card_account_Query")!=null)
                                {            
                                accountQuery=session.getAttribute("view_card_account_Query").toString().trim();
                                }
                                if(session.getAttribute("view_card_cardGroup_Query")!=null)
                                {
                                cardGroupQuery=session.getAttribute("view_card_cardGroup_Query").toString().trim();
                                }
                                
                                
                                
//                                vo.setNamedWhereClauseParam("accountID", accountPassingValues);
                                vo.setNamedWhereClauseParam("partnerId", getBindings().getPartner().getValue().toString().trim());
                                vo.setNamedWhereClauseParam("status", statusPassingValues);
//                                vo.setNamedWhereClauseParam("cgMain", cardGroupMaintypePassValue);
//                                vo.setNamedWhereClauseParam("cgSub", cardGroupSubtypePassValues);
//                                vo.setNamedWhereClauseParam("cgSeq", cardGroupSeqPassValues);
                                vo.setNamedWhereClauseParam("countryCd", lang);
                                
                                 vo.setWhereClause(accountQuery+"AND "+cardGroupQuery);
                    
                    if(accountIdValue.size()>250) {      
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                         " " + "Account Values > 250 ");
                        mapAccountListValue=valueList.callValueList(accountIdValue.size(), accountIdValue); 
                        for(int i=0;i<mapAccountListValue.size();i++) {
                            String values="account"+i;
                            String listName="listName"+i;
                            vo.defineNamedWhereClauseParam(values, mapAccountListValue.get(listName),
                                                                            null);
                        } 
                    }
                    else {
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                          " " + "Account Values < 250 ");
                        vo.defineNamedWhereClauseParam("account", populateStringValues(getBindings().getAccount().getValue().toString()),null);
                    }
                    
                    
                    if(cardGroupValue.size()>250) {      
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                          " " + "CardGroup Values > 250 ");
                        mapCardGroupListValue=valueList.callValueList(cardGroupValue.size(), cardGroupValue); 
                        for(int i=0;i<mapCardGroupListValue.size();i++) {
                            String values="cardGroup"+i;
                            String listName="listName"+i;
                            vo.defineNamedWhereClauseParam(values, mapCardGroupListValue.get(listName),
                                                                                    null);
                        }
                    }
                    else{
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                         " " + "CardGroup Values < 250 ");
                        vo.defineNamedWhereClauseParam("cardGroup", populateStringValues(getBindings().getCardGroup().getValue().toString()),null);
                    }
                    
                    
                                
                                vo.executeQuery();

                                isTableVisible = true;
                            }
                        } 
                        

                        if (resourceBundle.containsKey("VEHICLE_ASSOCIATED")) {
                            getBindings().getTruckdriverDetails().hide();
                            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("VEHICLE_ASSOCIATED"), "");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                        }
                    }

                }
            }
        }

    }

    public void checkDriverAssociation() {

          
            ViewObject driverVo = ADFUtils.getViewObject("PrtViewVehicleDriverVO1Iterator");

          

            driverVo.setNamedWhereClauseParam("countryCd", lang);

            driverVo.setNamedWhereClauseParam("paramValue", "Driver");

            if( getBindings().getDriverNumber().getValue() != null)
            {
              
            driverVo.setNamedWhereClauseParam("driverNumber", getBindings().getDriverNumber().getValue());

            }
            if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount") != null) {
              
                driverVo.setNamedWhereClauseParam("accountValue",AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim());
            }
            driverVo.executeQuery();
          
            if(driverVo.getEstimatedRowCount() > 0)
            {
               
                while (driverVo.hasNext()) {
                  
                       PrtViewVehicleDriverVORowImpl currRow =(PrtViewVehicleDriverVORowImpl)driverVo.next();
                  
                        if (currRow != null) {
                            if(currRow.getCardNumber() != null)
                            {
                       
            if (resourceBundle.containsKey("DRIVER_CARD_ALREADY_EXIST")) {
             
                showErrorMsgEditFlag=true;
                warningMsg = resourceBundle.getObject("DRIVER_CARD_ALREADY_EXIST").toString().concat(" ").concat(currRow.getCardEmbossNum());
              
            }
            }

                            else {
                              
                                if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber") != null)
                                {
                                internalCardNumber=AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber").toString().trim();
                                }
                                if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount") != null)
                                {
                                associatedAccount = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim();
                                }
                                BindingContainer bindings =  BindingContext.getCurrent().getCurrentBindingsEntry();
                                OperationBinding operationBinding = bindings.getOperationBinding("updateVehicleDriver");
                                operationBinding.getParamsMap().put("cardNumber", internalCardNumber);

                                operationBinding.getParamsMap().put("type", "Driver");
                                operationBinding.getParamsMap().put("countryCd", lang);

                                operationBinding.getParamsMap().put("vehicleDriverValue",getBindings().getDriverNumber().getValue() );

                                operationBinding.getParamsMap().put("associatedAccount", associatedAccount);


                                Object result = operationBinding.execute();
                                
                                
                                
                                String accountPassingValues = null;
                                String statusPassingValues = null;
                                String cardGroupPassingValues = null;
                                
                                if (getBindings().getPartner().getValue() != null) {
                                    if (getBindings().getAccount().getValue() != null) {
                                        accountPassingValues = populateStringValues(getBindings().getAccount().getValue().toString());
                                    } else {
                                        showErrorMessage("ENGAGE_NO_ACCOUNT");
                                       
                                    }

                                    if (getBindings().getStatus().getValue() != null) {
                                        statusPassingValues = populateStringValues(getBindings().getStatus().getValue().toString());
                                    } else {
                                        showErrorMessage("ENGAGE_NO_STATUS");
                                        
                                    }

                                    if (getBindings().getCardGroup().getValue() != null) {
                                        cardGroupPassingValues = populateStringValues(getBindings().getCardGroup().getValue().toString());
                                        populateCardGroupValues(cardGroupPassingValues);

                                    } else {
                                        showErrorMessage("ENGAGE_NO_CARD_GROUP");
                                       
                                    }

                                    if (getBindings().getPartner().getValue() != null) {
                                        ViewObject vo = ADFUtils.getViewObject("PrtViewCardsVO1Iterator");
                                        
                                        
                                        
                                        if(session.getAttribute("view_card_account_Query")!=null)
                                        {            
                                        accountQuery=session.getAttribute("view_card_account_Query").toString().trim();
                                        }
                                        if(session.getAttribute("view_card_cardGroup_Query")!=null)
                                        {
                                        cardGroupQuery=session.getAttribute("view_card_cardGroup_Query").toString().trim();
                                        }
                                        
                                        
                                        
                                        //                                vo.setNamedWhereClauseParam("accountID", accountPassingValues);
                                        vo.setNamedWhereClauseParam("partnerId", getBindings().getPartner().getValue().toString().trim());
                                        vo.setNamedWhereClauseParam("status", statusPassingValues);
                                        //                                vo.setNamedWhereClauseParam("cgMain", cardGroupMaintypePassValue);
                                        //                                vo.setNamedWhereClauseParam("cgSub", cardGroupSubtypePassValues);
                                        //                                vo.setNamedWhereClauseParam("cgSeq", cardGroupSeqPassValues);
                                        vo.setNamedWhereClauseParam("countryCd", lang);
                                        
                                                            vo.setWhereClause(accountQuery+"AND "+cardGroupQuery);
                                        
                                        if(accountIdValue.size()>250) {
                                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                                 " " + "Account Values > 250 ");
                                        mapAccountListValue=valueList.callValueList(accountIdValue.size(), accountIdValue);
                                        for(int i=0;i<mapAccountListValue.size();i++) {
                                        String values="account"+i;
                                        String listName="listName"+i;
                                        vo.defineNamedWhereClauseParam(values, mapAccountListValue.get(listName),
                                                                                    null);
                                        }
                                        }
                                        else {
                                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                                  " " + "Account Values < 250 ");
                                        vo.defineNamedWhereClauseParam("account", populateStringValues(getBindings().getAccount().getValue().toString()),null);
                                        }
                                        
                                        
                                        if(cardGroupValue.size()>250) {
                                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                                  " " + "CardGroup Values > 250 ");
                                        mapCardGroupListValue=valueList.callValueList(cardGroupValue.size(), cardGroupValue);
                                        for(int i=0;i<mapCardGroupListValue.size();i++) {
                                        String values="cardGroup"+i;
                                        String listName="listName"+i;
                                        vo.defineNamedWhereClauseParam(values, mapCardGroupListValue.get(listName),
                                                                                            null);
                                        }
                                        }
                                        else{
                                        _logger.info(accessDC.getDisplayRecord() + this.getClass() +
                                                                 " " + "CardGroup Values < 250 ");
                                        vo.defineNamedWhereClauseParam("cardGroup", populateStringValues(getBindings().getCardGroup().getValue().toString()),null);
                                        }
                                        
                                        
                                        
                                        

                                        vo.executeQuery();

                                        isTableVisible = true;
                                    }
                                } 
                                

                               if (resourceBundle.containsKey("DRIVER_ASSOCIATED")) {
                                    getBindings().getTruckdriverDetails().hide();
                                FacesMessage msg =
                                new FacesMessage(FacesMessage.SEVERITY_INFO, (String)resourceBundle.getObject("DRIVER_ASSOCIATED"),
                                "");
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                                }

                            }

                        }
                }
            }

        }


    public void exportExcelSpecificAction(ActionEvent actionEvent) {
        shuttleStatus=false;
        ViewObject prtExportInfoRVO = ADFUtils.getViewObject("PrtExportInfoRVO1Iterator");
        prtExportInfoRVO.setNamedWhereClauseParam("country_Code", lang);
        prtExportInfoRVO.setNamedWhereClauseParam("report_Page", "VIEWCARDS");
        prtExportInfoRVO.setNamedWhereClauseParam("report_Type", "Default");
        prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria", "Default");
        prtExportInfoRVO.executeQuery();
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + " PrtExportInfoRVO Estimated Row Count in CardGroup:"+prtExportInfoRVO.getEstimatedRowCount());
        if(prtExportInfoRVO.getEstimatedRowCount() > 0){
            while(prtExportInfoRVO.hasNext()) {
                PrtExportInfoRVORowImpl prtExportRow=(PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                strViewCardTotalColumns=prtExportRow.getTotalColumns();
                strViewCardExtraColumns=prtExportRow.getExtraColumns();
            }
        }
        if(strViewCardTotalColumns!=null)
        {
        String[] strHead=strViewCardTotalColumns.split(",");
        shuttleList  = new ArrayList<SelectItem>();
        for (int col = 0; col < strHead.length; col++){
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel(strHead[col].toString());
            selectItem.setValue(strHead[col].toString());
            shuttleList.add(selectItem);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShuttleExcel());
        getBindings().getSpecificColumns().show(new RichPopup.PopupHints());
        }else {
            if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR_DB")) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR_DB"),"");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public String saveVehicleDriver() {

        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") != null ||
           AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") != null ){
            if(vehiclePGL){
                if(getBindings().getVehicleNumber().getValue() == null){
                    showErrorMsgEditFlag = true;
                    warningMsg =  resourceBundle.getObject("VEHICLE_EMPTY").toString();
                }
                 else if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") != null){
                    if (resourceBundle.containsKey("DRIVER_CARD_EXIST")){
                        showErrorMsgEditFlag=true;
                        warningMsg =  resourceBundle.getObject("DRIVER_CARD_EXIST").toString().concat(" ").concat(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverName").toString());
                    }
                }
                else if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") != null &&
                        getBindings().getVehicleNumber().getValue().equals(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber"))){
                            System.out.println("entered same vehicle.");
                            getBindings().getTruckdriverDetails().hide();
                }
                else{
                    System.out.println("check vehicle association");
                    checkVehicleAssociation();
                }
            }
            else{
                if(driverPGL){  
                    if(getBindings().getDriverNumber().getValue() == null){
                        showErrorMsgEditFlag = true;
                        warningMsg =  resourceBundle.getObject("DRIVER_EMPTY").toString();
                    }
                    else if( AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") != null){
                        if (resourceBundle.containsKey("TRUCK_CARD_EXIST")){
                            showErrorMsgEditFlag=true;
                            warningMsg =  resourceBundle.getObject("TRUCK_CARD_EXIST").toString().concat(" ").concat(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber").toString());
                        }
                    }
                    else if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") != null &&
                            getBindings().getDriverNumber().getValue().equals(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber"))){
                        getBindings().getTruckdriverDetails().hide();
                    }
                    else{
                        checkDriverAssociation();
                    }
                }
            }
        }
        else{
            if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") == null &&
               AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") == null ){
                    if(vehiclePGL){
                        if(getBindings().getVehicleNumber().getValue() == null){
                            showErrorMsgEditFlag = true;
                            warningMsg =  resourceBundle.getObject("VEHICLE_EMPTY").toString();
                        }
                        else{
                            checkVehicleAssociation();
                        }
                    }
                    else{
                        if(driverPGL){
                            if(getBindings().getDriverNumber().getValue() == null){
                                showErrorMsgEditFlag = true;
                                warningMsg =  resourceBundle.getObject("DRIVER_EMPTY").toString();
                            }
                            else{
                                checkDriverAssociation();
                            }
                        }
                    }
            }
        }
        
        showEditInfoMessage = false;
//        getBindings().getVehicleDriverRadio().setSubmittedValue(null);
//        getBindings().getVehicleDriverRadio().setValue(null);
//        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleDriverRadio());

        return null;
    }

    /**
     * @param driverNumber
     */
    public void setDriverNumber(String driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getDriverNumber() {
        return driverNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setDriverNameAssociation(RichSelectOneChoice driverNameAssociation) {
        this.driverNameAssociation = driverNameAssociation;
    }

    public RichSelectOneChoice getDriverNameAssociation() {
        return driverNameAssociation;
    }

    public void setWarningMsg(String warningMsg) {
        this.warningMsg = warningMsg;
    }

    public String getWarningMsg() {
        return warningMsg;
    }

    public void setShowErrorMsgEditFlag(boolean showErrorMsgEditFlag) {
        this.showErrorMsgEditFlag = showErrorMsgEditFlag;
    }

    public boolean isShowErrorMsgEditFlag() {
        return showErrorMsgEditFlag;
    }

    public void setInternalCardNumber(String internalCardNumber) {
        this.internalCardNumber = internalCardNumber;
    }

    public String getInternalCardNumber() {
        return internalCardNumber;
    }

    public String closePopUp() {             
        return null;
    }
    
    public List getShuttleList() {
        return shuttleList;
    }

    public void setShuttleValue(List shuttleValue) {
        this.shuttleValue = shuttleValue;
    }

    public List getShuttleValue() {
        if(!shuttleStatus)
        {
        shuttleValue= new ArrayList();
        ViewObject prtExportInfoRVO = ADFUtils.getViewObject("PrtExportInfoRVO1Iterator");
        prtExportInfoRVO.setNamedWhereClauseParam("country_Code", lang);
        prtExportInfoRVO.setNamedWhereClauseParam("report_Page", "VIEWCARDS");
        prtExportInfoRVO.setNamedWhereClauseParam("report_Type", "Default");
        prtExportInfoRVO.setNamedWhereClauseParam("select_Criteria", "Default");
        prtExportInfoRVO.executeQuery();
        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + " PrtExportInfoRVO Estimated Row Count in CardGroup shuttle:"+prtExportInfoRVO.getEstimatedRowCount());
        if(prtExportInfoRVO.getEstimatedRowCount() > 0){
            while(prtExportInfoRVO.hasNext()) {
                PrtExportInfoRVORowImpl prtExportRow=(PrtExportInfoRVORowImpl)prtExportInfoRVO.next();
                strViewCardPrepopulatedColumns=prtExportRow.getPrePopulatedColumns();
            }
        }
        if(strViewCardPrepopulatedColumns!=null){
            String[] strHead=strViewCardPrepopulatedColumns.split(",");
            for (int col = 0; col < strHead.length; col++){
                shuttleValue.add(strHead[col].toString());
            }
        }
        }
        return shuttleValue;
    }

    public void getValuesForExcel(ActionEvent actionEvent) {

        if(shuttleValue == null) {
            if (resourceBundle.containsKey("TRANSACTION_SPECIFIC_ERROR")) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     (String)resourceBundle.getObject("TRANSACTION_SPECIFIC_ERROR"),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }else
        {
            if (shuttleValue.size() > 0) {
                 shuttleStatus=true;
                getBindings().getConfirmationExcel().show(new RichPopup.PopupHints());
            }
        }

    }

    public String excelDownLoad() {
        return null;
    }

    public String formatConversion(Date date) {
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    public String checkALL(String selectedValues,String type) {
        String val="";
        String[] listValues=selectedValues.split(",");
        if(listValues.length>1) {
            if("Account".equalsIgnoreCase(type)) {
                if(accountIdList.size()  == listValues.length) {
                    val="ALL";
                }else {
                    val=selectedValues;
                }
            }else if("CardGroup".equalsIgnoreCase(type)) {
                if(cardGroupList.size()==listValues.length) {
                    val="ALL";
                }else {
                    val=selectedValues;
                }
            }else if("Status".equalsIgnoreCase(type)) {
                if(statusList.size()==listValues.length) {
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

    public String statusConversion(String statusLabel){
        if(statusLabel != null){
            statusLabel = statusLabel.trim();

            if(statusLabel.equalsIgnoreCase("0")) {
                
                if (resourceBundle.containsKey("UNBLOCKED"))
                
                return resourceBundle.getObject("UNBLOCKED").toString();
            }
            else if(statusLabel.equalsIgnoreCase("1")){
                
                
                if (resourceBundle.containsKey("TEMPORARY_BLOCKED"))
                return resourceBundle.getObject("TEMPORARY_BLOCKED").toString() ;
            }
            else if(statusLabel.equalsIgnoreCase("2")){
                
                if (resourceBundle.containsKey("PERMANENT_BLOCKED"))
                return resourceBundle.getObject("PERMANENT_BLOCKED").toString() ; 
            }
        }
        return null;
    }

    public String statusConversionList(String status){
        if(status != null){
            String statusValueList = "";
            String[] sta = status.split(",");
            for(int i=0; i < sta.length; i++){
               
                statusValueList = statusValueList + statusConversion(sta[i]) + ",";
            }
            statusValueList = statusValueList.substring(0, statusValueList.length() - 1);
            return statusValueList;

        }
        return null;
    }



    public void specificExportExcelListener(FacesContext facesContext, OutputStream outputStream) throws IOException, SQLException {
      
        String selectedValues="";
        for (int i = 0; i <shuttleValue.size(); i++ ) {
            selectedValues = selectedValues + shuttleValue.get(i).toString().trim() + ",";
        }
        selectedValues=selectedValues.substring(0, selectedValues.length()-1);
       
        int partnerIndex = 0;
        String partnerCompanyName="";
        for(int z=0 ; z<partnerInfoList.size(); z++){
            if((partnerInfoList.get(z).getPartnerValue()).equalsIgnoreCase(getBindings().getPartner().getValue().toString().trim())){
                partnerCompanyName = partnerInfoList.get(z).getPartnerName();
                partnerIndex = z;
            }
        }

        String cardGroupDescName="";
        String[] cardGroupDescList = StringConversion(populateStringValues(getBindings().getCardGroup().getValue().toString().trim()));
        String[] accountString = StringConversion(populateStringValues(getBindings().getAccount().getValue().toString().trim()));

        if(partnerInfoList.get(partnerIndex).getAccountList() != null && partnerInfoList.get(partnerIndex).getAccountList().size() > 0){
            for(int i=0 ; i<partnerInfoList.get(partnerIndex).getAccountList().size(); i++){
                if(accountString.length > 0){
                    for(int j=0;j<accountString.length;j++) {
                        if(partnerInfoList.get(partnerIndex).getAccountList().get(i).getAccountNumber()!= null && partnerInfoList.get(partnerIndex).getAccountList().get(i).getAccountNumber().trim().equals(accountString[j].trim())){
                            if(partnerInfoList.get(partnerIndex).getAccountList().get(i).getCardGroup() != null && partnerInfoList.get(partnerIndex).getAccountList().get(i).getCardGroup().size()>0){
                                for(int k =0 ; k< partnerInfoList.get(partnerIndex).getAccountList().get(i).getCardGroup().size(); k++){
                                    if(partnerInfoList.get(partnerIndex).getAccountList().get(i).getCardGroup().get(k).getCardGroupID() != null && cardGroupDescList.length > 0){
                                        for(int cg=0 ; cg< cardGroupDescList.length ; cg++){
                                            if((partnerInfoList.get(partnerIndex).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().trim()).equals(cardGroupDescList[cg].toString().trim())){
                                                cardGroupDescName = cardGroupDescName + partnerInfoList.get(partnerIndex).getAccountList().get(i).getCardGroup().get(k).getCardGroupName() + ",";
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
        cardGroupDescName = (String)cardGroupDescName.subSequence(0, (cardGroupDescName.length())-1);


        HSSFWorkbook XLS = new HSSFWorkbook();
        HSSFRow XLS_SH_R=null;
        HSSFCell XLS_SH_R_C=null;
        int intRow=0;
        HSSFCellStyle cs = XLS.createCellStyle();
        HSSFFont f =XLS.createFont();

        //create sheet
        HSSFSheet XLS_SH=XLS.createSheet();
        XLS.setSheetName(0,"CardReport");

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
        XLS_SH_R_C.setCellValue("Account: "+checkALL((populateStringValues(getBindings().getAccount().getValue().toString())),"Account"));

        XLS_SH_R= XLS_SH.createRow(2);
        XLS_SH_R_C=XLS_SH_R.createCell(0);
        XLS_SH_R_C.setCellStyle(cs);
        XLS_SH_R_C.setCellValue("CardGroup: "+checkALL(cardGroupDescName,"CardGroup"));



//        populateCardGroupValues(populateStringValues(getBindings().getCardGroup().getValue().toString());

        XLS_SH_R= XLS_SH.createRow(3);
        XLS_SH_R_C=XLS_SH_R.createCell(0);
        XLS_SH_R_C.setCellStyle(cs);
      
        XLS_SH_R_C.setCellValue("Status: "+checkALL((statusConversionList(populateStringValues(getBindings().getStatus().getValue().toString()))),"Status"));

        for(int row=4;row<=6;row++) {
            XLS_SH_R= XLS_SH.createRow(row);
        }

        String[] headerValues=selectedValues.split(",");

        HSSFCellStyle css = XLS.createCellStyle();
        HSSFFont fcss =XLS.createFont();
        fcss.setFontHeightInPoints((short) 10);
        fcss.setItalic(true);
        fcss.setColor((short)0);
        css.setFont(fcss);
        XLS_SH_R= XLS_SH.createRow(6);
        for (int col = 0; col < headerValues.length; col++){
            XLS_SH_R_C =XLS_SH_R.createCell(col);
            XLS_SH_R_C.setCellStyle(css);
            XLS_SH_R_C.setCellValue(headerValues[col].toString());
        }

        int rowVal=6;

        ViewObject prtViewCardsVO = ADFUtils.getViewObject("PrtViewCardsVO1Iterator");
        RowSetIterator iterator = prtViewCardsVO.createRowSetIterator(null);
        iterator.reset();
        while (iterator.hasNext()) {
          PrtViewCardsVORowImpl row = (PrtViewCardsVORowImpl)iterator.next();
            rowVal=rowVal+1;
            XLS_SH_R= XLS_SH.createRow(rowVal);
            if(row!=null) {
                for (int cellValue = 0; cellValue < headerValues.length; cellValue++){
                    if("Account".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getAccountId()!=null){
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getAccountId().toString());
                        }
                    }
                    else if("CardGroup".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getCardgroupDescription()!=null){
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getCardgroupDescription());
                        }
                    }
                    else if("Type".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getCardType()!=null){
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getCardType().toString());
                        }
                    }
                    else if("Card".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getCardEmbossNum()!=null){
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getCardEmbossNum().toString());
                        }
                    }
                    else if("Vehicle".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getVehicleNumber()!=null){
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getVehicleNumber().toString());
                        }
                    }
                    else if("Driver".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getDriverName()!=null){
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(row.getDriverName().toString());
                        }
                    }
                    else if("Status".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getBlockAction()!=null){
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                            XLS_SH_R_C.setCellValue(statusConversion(row.getBlockAction().toString()));
                        }
                    }
                    else if("Expiry".equalsIgnoreCase(headerValues[cellValue].toString().trim())) {
                        if(row.getCardExpiry()!=null){
                            XLS_SH_R_C=XLS_SH_R.createCell(cellValue);
                            XLS_SH_R_C.setCellStyle(csData);
                            java.sql.Date date=row.getCardExpiry().dateValue();
                            Date passedDate=new Date(date.getTime());
                            XLS_SH_R_C.setCellValue(formatConversion(passedDate));
//                            XLS_SH_R_C.setCellValue(row.getCardExpiry().toString().trim());
                        }
                    }
                }

            }
        }
        iterator.closeRowSetIterator();
        XLS.write(outputStream);
        outputStream.close();


    }


    public String confirmationCancelAction() {
        getBindings().getConfirmationExcel().hide();
        return null;
    }

    public void setCardEmbossNum(String cardEmbossNum) {
        this.cardEmbossNum = cardEmbossNum;
    }

    public String getCardEmbossNum() {
        return cardEmbossNum;
    }

    public void setInternalName(String InternalName) {
        this.InternalName = InternalName;
    }

    public String getInternalName() {
        return InternalName;
    }

    public void setDriverNumber1(String DriverNumber) {
        this.DriverNumber = DriverNumber;
    }

    public String getDriverNumber1() {
        return DriverNumber;
    }

    public void editVehicleDriverListener(ActionEvent actionEvent) {        
        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardAssociation") != null)
        cardAssociation = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardAssociation").toString().trim();
        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber") != null)
        internalCardNumber= AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber").toString().trim();
        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardEmbossNum") != null)
        cardEmbossNum = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardEmbossNum").toString().trim();
        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("InternalName") != null)
        InternalName = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("InternalName").toString().trim();
        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") != null)
        DriverNumber = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber").toString().trim();
        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverName") != null)
        DriverName = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverName").toString().trim();
        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") != null)
        VehicleNumber = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber").toString().trim();        
        this.driverNameValue=null;
        this.vehicleNumberValue=null;
        driverNameList=new ArrayList<SelectItem>();
        vehicleNumberList=new ArrayList<SelectItem>();
        //this.getBindings().getDriverNumber().setValue(null);
        //this.getBindings().getVehicleNumber().setValue(null);
        this.displayDriverName=null;
        this.displayVehicleName = null;
//        if(getBindings().getVehicleDriverRadio().getValue() == null) {
//          
//            getBindings().getVehicleDriverRadio().setValue(null);
//        }
        
//        getBindings().getVehicleDriverRadio().setValue("");
        //AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleDriverRadio());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDriverNumber());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleNumber());        
        getBindings().getTruckdriverDetails().show(new RichPopup.PopupHints());
        
        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") != null || AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") != null ) {
            
            if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") != null) {
                if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedBy") != null)
                vehicleModifiedBy=AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedBy").toString().trim();
                if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedDate") != null)
                vehicleModifiedDate=AdfFacesContext.getCurrentInstance().getPageFlowScope().get("vehicleModifiedDate").toString().trim();
//                vehicleModifiedDate = formatConversion(new Date(vehicleModifiedDate));
                
                if (resourceBundle.containsKey("TRUCK_CARD_ALREADY_EXIST")) {

                    showEditInfoMessage = true;
             
                    infoMsg = resourceBundle.getObject("CARD_ASSOCIATED_VEHICLE").toString().concat(" "+VehicleNumber+" ").concat(" ")+resourceBundle.getObject("MODIFIED_BY").toString().concat(" "+vehicleModifiedBy+" ")+resourceBundle.getObject("MODIFIED_DATE").toString().concat(" "+vehicleModifiedDate+" ");
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditInfoMessage());
                }
            }
            else {
                if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") != null) {
                    if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedBy") != null)
                    driverModifiedBy=AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedBy").toString().trim();
                    if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedDate") != null)
                    driverModifiedDate=AdfFacesContext.getCurrentInstance().getPageFlowScope().get("driverModifiedDate").toString().trim();
//                    driverModifiedDate = formatConversion(new Date(driverModifiedDate));
                    
                    if (resourceBundle.containsKey("DRIVER_CARD_ALREADY_EXIST")) {

                        showEditInfoMessage = true;
                      
                        infoMsg =resourceBundle.getObject("CARD_ASSOCIATED_DRIVER").toString().concat(" "+DriverNumber+" ").concat("")+resourceBundle.getObject("MODIFIED_BY").toString().concat(" "+driverModifiedBy+" ")+resourceBundle.getObject("MODIFIED_DATE").toString().concat(" "+driverModifiedDate+" ");
                        System.out.println("warningMsg" + infoMsg);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowEditInfoMessage());
                    }
                    
                }
             }
           
        }else{
            showEditInfoMessage = false;
            getBindings().getVehicleDriverRadio().setSubmittedValue(null);
            getBindings().getVehicleDriverRadio().setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleDriverRadio());
        }
    }



    
    public void closePopUpListener(ActionEvent actionEvent) {
        getBindings().getVehicleDriverRadio().setSubmittedValue(null);
        getBindings().getVehicleDriverRadio().setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleDriverRadio());
        getBindings().getTruckdriverDetails().hide();
    }

    public void setVehicleModifiedBy(String vehicleModifiedBy) {
        this.vehicleModifiedBy = vehicleModifiedBy;
    }

    public String getVehicleModifiedBy() {
        return vehicleModifiedBy;
    }

    public void setVehicleModifiedDate(String vehicleModifiedDate) {
        this.vehicleModifiedDate = vehicleModifiedDate;
    }

    public String getVehicleModifiedDate() {
        return vehicleModifiedDate;
    }

    public void setDriverModifiedBy(String driverModifiedBy) {
        this.driverModifiedBy = driverModifiedBy;
    }

    public String getDriverModifiedBy() {
        return driverModifiedBy;
    }

    public void setDriverModifiedDate(String driverModifiedDate) {
        this.driverModifiedDate = driverModifiedDate;
    }

    public String getDriverModifiedDate() {
        return driverModifiedDate;
    }

    public void setVehicleModifiedByVisible(boolean vehicleModifiedByVisible) {
        this.vehicleModifiedByVisible = vehicleModifiedByVisible;
    }

    public boolean isVehicleModifiedByVisible() {
        return vehicleModifiedByVisible;
    }

    public void setVehicleModifiedDateVisible(boolean vehicleModifiedDateVisible) {
        this.vehicleModifiedDateVisible = vehicleModifiedDateVisible;
    }

    public boolean isVehicleModifiedDateVisible() {
        return vehicleModifiedDateVisible;
    }

    public void setDriverModifiedByVisible(boolean driverModifiedByVisible) {
        this.driverModifiedByVisible = driverModifiedByVisible;
    }

    public boolean isDriverModifiedByVisible() {
        return driverModifiedByVisible;
    }

    public void setDriverModifiedDateVisible(boolean driverModifiedDateVisible) {
        this.driverModifiedDateVisible = driverModifiedDateVisible;
    }

    public boolean isDriverModifiedDateVisible() {
        return driverModifiedDateVisible;
    }

    public void setInfoMsg(String infoMsg) {
        this.infoMsg = infoMsg;
    }

    public String getInfoMsg() {
        return infoMsg;
    }

    public void setShowEditInfoMessage(boolean showEditInfoMessage) {
        this.showEditInfoMessage = showEditInfoMessage;
    }

    public boolean isShowEditInfoMessage() {
        return showEditInfoMessage;
    }


    public class Bindings {
        private RichSelectOneChoice partner;
        private RichSelectManyChoice cardGroup;
        private RichSelectManyChoice account;
        private RichSelectManyChoice status;
        private RichPopup truckdriverDetails;
 private RichSelectOneChoice driverNumber;
        private RichSelectOneChoice vehicleNumber;
        private RichOutputText showEditErrorMessage;
        private RichSelectOneRadio vehicleDriverRadio;
        private RichSelectManyShuttle shuttleExcel;
        private RichPopup specificColumns;
        private RichPopup confirmationExcel;
            private RichPanelGroupLayout driverPgl;
                        private RichPanelGroupLayout vehiclePgl;
        private RichOutputText showEditInfoMessage;
        public void setPartner(RichSelectOneChoice partner) {
            this.partner = partner;
        }

        public RichSelectOneChoice getPartner() {
            return partner;
        }

        public void setCardGroup(RichSelectManyChoice cardGroup) {
            this.cardGroup = cardGroup;
        }

        public RichSelectManyChoice getCardGroup() {
            return cardGroup;
        }

        public void setAccount(RichSelectManyChoice account) {
            this.account = account;
        }

        public RichSelectManyChoice getAccount() {
            return account;
        }

        public void setStatus(RichSelectManyChoice status) {
            this.status = status;
        }

        public RichSelectManyChoice getStatus() {
            return status;
        }

        public void setTruckdriverDetails(RichPopup truckdriverDetails) {
            this.truckdriverDetails = truckdriverDetails;
        }

        public RichPopup getTruckdriverDetails() {
            showErrorMsgEditFlag=false;
            vehiclePGL=false;
            driverPGL=false;

            return truckdriverDetails;
        }

 public void setDriverNumber(RichSelectOneChoice driverNumber) {
            this.driverNumber = driverNumber;
        }

        public RichSelectOneChoice getDriverNumber() {
            return driverNumber;
        }

        public void setVehicleNumber(RichSelectOneChoice vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
        }

        public RichSelectOneChoice getVehicleNumber() {
            return vehicleNumber;
        }

        public void setShowEditErrorMessage(RichOutputText showEditErrorMessage) {
            this.showEditErrorMessage = showEditErrorMessage;
        }

        public RichOutputText getShowEditErrorMessage() {
            return showEditErrorMessage;
        }

        public void setVehicleDriverRadio(RichSelectOneRadio vehicleDriverRadio) {
            this.vehicleDriverRadio = vehicleDriverRadio;
        }

        public RichSelectOneRadio getVehicleDriverRadio() {
            return vehicleDriverRadio;
        }

        public void setShuttleExcel(RichSelectManyShuttle shuttleExcel) {
            this.shuttleExcel = shuttleExcel;
        }

        public RichSelectManyShuttle getShuttleExcel() {
            return shuttleExcel;
        }

        public void setSpecificColumns(RichPopup specificColumns) {
            this.specificColumns = specificColumns;
        }

        public RichPopup getSpecificColumns() {
            return specificColumns;
        }

        public void setConfirmationExcel(RichPopup confirmationExcel) {
            this.confirmationExcel = confirmationExcel;
        }

        public RichPopup getConfirmationExcel() {
            return confirmationExcel;
        }

        public void setDriverPgl(RichPanelGroupLayout driverPgl) {
            this.driverPgl = driverPgl;
        }

        public RichPanelGroupLayout getDriverPgl() {
            return driverPgl;
        }

        public void setVehiclePgl(RichPanelGroupLayout vehiclePgl) {
            this.vehiclePgl = vehiclePgl;
        }

        public RichPanelGroupLayout getVehiclePgl() {
            return vehiclePgl;
        }

        public void setShowEditInfoMessage(RichOutputText showEditInfoMessage) {
            this.showEditInfoMessage = showEditInfoMessage;
        }

        public RichOutputText getShowEditInfoMessage() {
            return showEditInfoMessage;
        }
    }
    }


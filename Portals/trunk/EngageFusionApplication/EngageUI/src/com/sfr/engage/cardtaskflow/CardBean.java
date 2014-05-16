package com.sfr.engage.cardtaskflow;

import com.sfr.engage.core.PartnerInfo;

import com.sfr.engage.invoiceoverviewtaskflow.InvoiceOverviewBean;
import com.sfr.engage.transactionoverviewtaskflow.TransactionOverviewBean;
import com.sfr.util.ADFUtils;
import com.sfr.util.AccessDataControl;

import com.sfr.util.constants.Constants;

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

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.javatools.marshal.StringConversion;

import oracle.jbo.ViewObject;

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
  

    public CardBean() {
        super();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        statusValue = new ArrayList<String>();
        partnerIdList= new ArrayList<SelectItem>();        
        partnerId=null;
        if(session.getAttribute("Partner_Object_List") != null){
            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
            if(partnerInfoList!=null && partnerInfoList.size()>0){
                System.out.println("inside if");
                for(int k=0;k<partnerInfoList.size();k++)
                {
                    System.out.println("inside for");
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(partnerInfoList.get(k).getPartnerValue().toString());
                selectItem.setValue(partnerInfoList.get(k).getPartnerValue().toString());
                partnerIdList.add(selectItem);
                //partnerIdValue.add(partnerInfoList.get(k).getPartnerValue().toString());
                System.out.println("partner list"+partnerInfoList.get(k).getPartnerValue().toString());
                }
            }
            
        }
          
        
        statusValue.add("1,2");
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
                                selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
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
    }


   

    public void setStatusList(ArrayList<SelectItem> statusList) {
        this.statusList = statusList;
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

    public ArrayList<SelectItem> getStatusList() {
        
        if (statusList == null) {
            statusList = new ArrayList<SelectItem>();
            SelectItem selectItem = new SelectItem();
            selectItem.setLabel("Blocked");
            selectItem.setValue("1,2");
            statusList.add(selectItem);
            SelectItem selectItem1 = new SelectItem();
            selectItem1.setLabel("Unblocked");
            selectItem1.setValue("0");
            statusList.add(selectItem1);
           
        
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
        
       
        if(valueChangeEvent.getNewValue()!=null) {
           
           System.out.println("partner selected"+valueChangeEvent.getNewValue()) ; 
              
        accountIdList  = new ArrayList<SelectItem>();
        accountIdValue = new ArrayList<String>();  
        cardGroupList = new ArrayList<SelectItem>();
        cardGroupValue = new ArrayList<String>();

            String partnerSelected = valueChangeEvent.getNewValue().toString();
                 if( partnerSelected!= null){
             System.out.println("inside if--------------------" +partnerSelected);
                   
                     for(int i=0 ; i<partnerInfoList.size(); i++){
                         
                 System.out.println("inside for");
                         if(partnerInfoList.get(i).getPartnerValue().toString()!= null && partnerInfoList.get(i).getPartnerValue().toString().equals(partnerSelected.trim())){
                             if(partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0){
                                 System.out.println("inside second if" + partnerInfoList.get(i).getAccountList().size()+"account size");
                                 for(int j=0;j<partnerInfoList.get(i).getAccountList().size();j++){
                                     System.out.println("inside second for");
                                     if(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() != null){
                                         System.out.println("inside final if");
                                         SelectItem selectItem = new SelectItem();
                                         selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                         selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                         accountIdList.add(selectItem);
                                         accountIdValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                         System.out.println("accountvalue" +accountIdValue );
                                     }
                                     
                                     
                                     for(int k=0;k<partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size();k++){
                                     if(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() != null){
                                         System.out.println("inside final if");
                                         SelectItem selectItem = new SelectItem();
                                         selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                         selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                         cardGroupList.add(selectItem);
                                        cardGroupValue.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                         System.out.println("cardGroupValue" + cardGroupValue);
                                     }
                                     
                                     }
                                     
                                 }  
                             }
                          
                          
                          
                          
                          
                          
                             
                        }
                    }
                }
                statusValue.add("1,2");
                statusValue.add("0");
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
              
              _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "card group main type======>"+cardGroupMaintypePassValue);
              _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "card group sub type===>"+cardGroupSubtypePassValues);
              _logger.info(accessDC.getDisplayRecord() + this.getClass() + " "   + "card group sequence value====>"+cardGroupSeqPassValues);
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
        String accountPassingValues  = null;
        String statusPassingValues = null;
        String cardGroupPassingValues = null;
        
        if(getBindings().getAccount().getValue() != null && getBindings().getPartner().getValue() != null && getBindings().getStatus().getValue() != null && getBindings().getCardGroup().getValue() != null){
            
            
            if(getBindings().getAccount().getValue() != null){
                   accountPassingValues =  populateStringValues(getBindings().getAccount().getValue().toString());
                   System.out.println("account values"+accountPassingValues);
                }else{
                    showErrorMessage("ENGAGE_NO_ACCOUNT");
                    return null;
                }
            
            if(getBindings().getStatus().getValue() != null){
                   statusPassingValues =  populateStringValues(getBindings().getStatus().getValue().toString());
                    System.out.println("status values"+statusPassingValues);
                }else{
                    showErrorMessage("ENGAGE_NO_STATUS");
                    return null;
                }
            
            if(getBindings().getCardGroup().getValue() != null){
            cardGroupPassingValues =  populateStringValues(getBindings().getCardGroup().getValue().toString());
                populateCardGroupValues(cardGroupPassingValues);
                System.out.println("cardgroup values"+cardGroupPassingValues);
            }
            else{
                showErrorMessage("ENGAGE_NO_CARD_GROUP");
                return null;
            }
            
            if(getBindings().getPartner().getValue() != null){
                ViewObject vo = ADFUtils.getViewObject("PrtViewCardsVO1Iterator");
                vo.setNamedWhereClauseParam("accountID", accountPassingValues);
                vo.setNamedWhereClauseParam("partnerId",getBindings().getPartner().getValue().toString().trim());
                vo.setNamedWhereClauseParam("status",statusPassingValues);
                vo.setNamedWhereClauseParam("cgMain",cardGroupMaintypePassValue);
                vo.setNamedWhereClauseParam("cgSub",cardGroupSubtypePassValues);
                vo.setNamedWhereClauseParam("cgSeq",cardGroupSeqPassValues);
                vo.setNamedWhereClauseParam("countryCd", lang);
                vo.executeQuery();
                
                 System.out.println("estimated count"+vo.getEstimatedRowCount());
                isTableVisible = true;
            }
            else{
                showErrorMessage("ENGAGE_NO_PARTNER");
                return null;
            }
            
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
        // Add event code here...
        getBindings().getAccount().setValue(null);
        getBindings().getCardGroup().setValue(null);
        getBindings().getPartner().setValue(null);
        getBindings().getStatus().setValue(null);
        this.partnerIdValue = null;
        this.accountIdValue = null;
        this.cardGroupValue = null;
        statusValue.add("1,2");
        statusValue.add("0");
        isTableVisible=false;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());  
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartner()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getStatus());
    }


    public class Bindings {
    
        private RichSelectOneChoice partner;
        private RichSelectManyChoice cardGroup;
        private RichSelectManyChoice account;
        private RichSelectManyChoice status;

        
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
    }
}

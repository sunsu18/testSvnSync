package com.sfr.engage.userdisplaytaskflow;

import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.core.ValueListSplit;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.ADFUtils;
import com.sfr.util.AccessDataControl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanRadio;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.ViewObject;

public class UserDisplayBean {
    
    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    public static final ADFLogger _logger = AccessDataControl.getSFRLogger();
    private AccessDataControl accessDC = new AccessDataControl();
    private List<SelectItem> partnerIdList;
    private List<String> partnerIdValue;
    private List<PartnerInfo> partnerInfoList;
    private List<SelectItem> accountIdList;
    private List<String> accountIdValue;
    private List<SelectItem> cardGroupList;
    private List<String> cardGroupValue;
    private List<SelectItem> cardNumberList;
    private List<String> cardNumberValue;
    private List<String> suggestedCardNumberList;
    private List<SelectItem> roleList;
    private List<String> roleValue;
    private String lang;
    private transient Bindings bindings;
    private ResourceBundle resourceBundle;
    private Boolean isTableVisible = false;
    private Boolean isCgMgrVisible = false;
    private Boolean isEmpVisible = false;
    private Boolean isAccMgrVisible = false;
    private Boolean isAdminVisible = false;
    private RichSelectBooleanRadio multipleCardRadio;
    private String searchString;
    private String accountQuery = "(";
    private String cardGroupQuery = "(";
    private String cardQuery = "(";
    private Map<String, String> mapAccountListValue;
    private Map<String, String> mapCardGroupListValue;
    private Map<String, String> mapCardListValue;
    private ValueListSplit valueList;

    public UserDisplayBean() {
        super();
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside Constructor of User Display");
        resourceBundle = new EngageResourceBundle();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        partnerIdValue = new ArrayList<String>();
        roleValue = new ArrayList<String>();
        roleValue.add("WCP_CARD_B2B_ADMIN");
        roleValue.add("WCP_CARD_B2B_MGR_AC");
        roleValue.add("WCP_CARD_B2B_MGR_CG");
        roleValue.add("WCP_CARD_B2B_EMP");
        if (session.getAttribute("Partner_Object_List") != null) {
            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
            if (partnerInfoList != null && partnerInfoList.size() > 0) {
                partnerIdList = new ArrayList<SelectItem>();
                accountIdList = new ArrayList<SelectItem>();
                accountIdValue = new ArrayList<String>();
                cardGroupList = new ArrayList<SelectItem>();
                cardGroupValue = new ArrayList<String>();
                cardNumberList = new ArrayList<SelectItem>();
                cardNumberValue = new ArrayList<String>();
                suggestedCardNumberList = new ArrayList<String>();
                for (int i = 0; i < partnerInfoList.size(); i++) {
                    lang = partnerInfoList.get(0).getCountry().toString().trim();
                    if (partnerInfoList.get(i).getPartnerName() != null && partnerInfoList.get(i).getPartnerValue() != null) {
                        SelectItem selectItem = new SelectItem();
                        selectItem.setLabel(partnerInfoList.get(i).getPartnerName().toString());
                        selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString());
                        partnerIdList.add(selectItem);
                        partnerIdValue.add(partnerInfoList.get(i).getPartnerValue().toString());
                    }

                    if (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {
                        for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
                            if (partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() != null) {
                                SelectItem selectItem = new SelectItem();
                                selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                accountIdList.add(selectItem);
                                accountIdValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            }
                            for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
                                if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() != null) {
                                    SelectItem selectItem = new SelectItem();
                                    selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                    selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                        partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                    cardGroupList.add(selectItem);
                                    cardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                       partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                }
                                
                                for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++){
                                    if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                        partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() != null){
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                            selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                            cardNumberList.add(selectItem);
                                            suggestedCardNumberList.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                            cardNumberValue.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                        }
                                }
                            }
                        }
                    }
                }

                Collections.sort(accountIdList, comparator);
                Collections.sort(cardGroupList, comparator);
                Collections.sort(cardNumberList, comparator);
            }
        }
        
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting Constructor of User Display");
    }
    
    Comparator<SelectItem> comparator = new Comparator<SelectItem>() {
        @Override
        public int compare(SelectItem s1, SelectItem s2) {
            return s1.getLabel().compareTo(s2.getLabel());
        }
    };
    
    /**
     * @return bindings Object
     */
    public Bindings getBindings() {
        if (bindings == null) {
            bindings = new Bindings();
        }
        return bindings;
    }
    
    public String populateStringValues(String var) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside populateStringValues method of User Display");
        String passingValues = null;
        if (var != null) {
            String lovValues = var.trim();
            String selectedValues = lovValues.substring(1, lovValues.length() - 1);
            passingValues = selectedValues.trim();
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting populateStringValues method of User Display");
        return passingValues;
    }
    
    public String[] StringConversion(String passedVal) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside StringConversion method of User Display");
        String[] val = passedVal.split(",");
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting StringConversion method of User Display");
        return val;
    }
    
    public void partnerValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside partnerValueChangeListener method of User Display");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null) {
            accountIdList = new ArrayList<SelectItem>();
            accountIdValue = new ArrayList<String>();
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();
            cardNumberList = new ArrayList<SelectItem>();
            cardNumberValue = new ArrayList<String>();
            
            String[] partnerString = StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()));

            if (partnerString.length > 0) {
                for (int i = 0; i < partnerInfoList.size(); i++) {
                    for (int p = 0; p < partnerString.length; p++) {
                        if (partnerInfoList.get(i).getPartnerValue().toString() != null &&
                            partnerInfoList.get(i).getPartnerValue().toString().equals(partnerString[p].trim())) {
                            if (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {

                                for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {

                                    if (partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() != null) {
                                        SelectItem selectItem = new SelectItem();
                                        selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                        selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                        accountIdList.add(selectItem);
                                        accountIdValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                    }


                                    for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
                                        if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() != null) {
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                            selectItem.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                            cardGroupList.add(selectItem);
                                            cardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                               partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                        }
                                        
                                        for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++) {
                                            if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() != null){
                                                    SelectItem selectItem = new SelectItem();
                                                    selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                                    selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                    cardNumberList.add(selectItem);
                                                    cardNumberValue.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
                Collections.sort(accountIdList, comparator);
                Collections.sort(cardGroupList, comparator);
                Collections.sort(cardNumberList, comparator);
            }
        } else {
            getBindings().getAccount().setValue(null);
            getBindings().getCardGroup().setValue(null);
            getBindings().getCard().setValue(null);
            this.accountIdValue = null;
            this.accountIdList = null;
            this.cardGroupValue = null;
            this.cardGroupList = null;
            this.cardNumberList = null;
            this.cardNumberValue = null;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside partnerValueChangeListener method of User Display");
    }
    
    public void accountValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside accountValueChangeListener method of User Display");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null) {
            String[] accountString = StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()));
            cardGroupList = new ArrayList<SelectItem>();
            cardGroupValue = new ArrayList<String>();
            cardNumberList = new ArrayList<SelectItem>();
            cardNumberValue = new ArrayList<String>();
            
            for (int z = 0; z < partnerInfoList.size(); z++) {
                if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                        for (int j = 0; j < accountString.length; j++) {
                            if (partnerInfoList.get(z).getAccountList().get(i).getAccountNumber() != null &&
                                partnerInfoList.get(z).getAccountList().get(i).getAccountNumber().equals(accountString[j].trim())) {
                                if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() > 0) {
                                    for (int k = 0; k < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size(); k++) {
                                        if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID() != null) {
                                            SelectItem selectItem = new SelectItem();
                                            selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getDisplayCardGroupIdName().toString());
                                            selectItem.setValue(partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                            cardGroupList.add(selectItem);
                                            cardGroupValue.add(partnerInfoList.get(z).getPartnerValue().toString().trim() +
                                                               partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCardGroupID().toString());
                                        }
                                        
                                        for (int cc = 0; cc < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().size(); cc++) {
                                            if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                                partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getExternalCardID() != null){
                                                    SelectItem selectItem = new SelectItem();
                                                    selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getExternalCardID().toString());
                                                    selectItem.setValue(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                    cardNumberList.add(selectItem);
                                                    cardNumberValue.add(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(k).getCard().get(cc).getCardID().toString());
                                                }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Collections.sort(accountIdList, comparator);
                    Collections.sort(cardGroupList, comparator);
                    Collections.sort(cardNumberList, comparator);
                }
            }

        } else {
            getBindings().getCardGroup().setValue(null);
            getBindings().getCard().setValue(null);
            this.cardGroupValue = null;
            this.cardNumberValue = null;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside accountValueChangeListener method of User Display");
    }
    
    public void cardgroupValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside cardgroupValueChangeListener method of User Display");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null) {
            String[] cardgroupString = StringConversion(populateStringValues(valueChangeEvent.getNewValue().toString()));
            cardNumberList = new ArrayList<SelectItem>();
            cardNumberValue = new ArrayList<String>();
            
            for (int z = 0; z < partnerInfoList.size(); z++) {
                if (partnerInfoList.get(z).getAccountList() != null && partnerInfoList.get(z).getAccountList().size() > 0) {
                    for (int i = 0; i < partnerInfoList.get(z).getAccountList().size(); i++) {
                        if(partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                           partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size() > 0){
                            for(int cg = 0; cg < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().size(); cg++){
                                for (int cgs = 0; cgs < cardgroupString.length; cgs++){
                                    if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup() != null &&
                                        (partnerInfoList.get(z).getPartnerValue().toString().trim() + 
                                         partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCardGroupID().toString().trim()).equals(cardgroupString[cgs].trim())){
                                             for (int cc = 0; cc < partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().size(); cc++) {
                                                if (partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID() != null &&
                                                    partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getExternalCardID() != null){
                                                    SelectItem selectItem = new SelectItem();
                                                    selectItem.setLabel(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
                                                    selectItem.setValue(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                                    cardNumberList.add(selectItem);
                                                    cardNumberValue.add(partnerInfoList.get(z).getAccountList().get(i).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                                }
                                             }
                                         }
                                }
                            }
                           }
                    }
                    Collections.sort(cardGroupList, comparator);
                    Collections.sort(cardNumberList, comparator);
                }
            }
        } else {
            getBindings().getCard().setValue(null);
            this.cardNumberValue = null;
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside cardgroupValueChangeListener method of User Display");
    }
    
    public void multicardRadioValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside multicardRadioValueChangeListener method of User Display");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().equals(true)) {
            getBindings().getPartner().setDisabled(false);
            getBindings().getAccount().setDisabled(false);
            getBindings().getCardGroup().setDisabled(false);
            getBindings().getCard().setDisabled(false);
            getBindings().getRole().setDisabled(false);
            getBindings().getSearchStringInputtext().resetValue();
            getBindings().getSearchStringInputtext().setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartner());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRole());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchStringInputtext());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
        }
        else if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().equals(false)) {
            getBindings().getPartner().setDisabled(true);
            getBindings().getAccount().setDisabled(true);
            getBindings().getCardGroup().setDisabled(true);
            getBindings().getCard().setDisabled(true);
            getBindings().getRole().setDisabled(true);
            getBindings().getSearchStringInputtext().setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartner());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRole());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchStringInputtext());
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside multicardRadioValueChangeListener method of User Display");
    }

    public void singlecardRadioValueChangeListener(ValueChangeEvent valueChangeEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside singlecardRadioValueChangeListener method of User Display");
        isTableVisible = false;
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().equals(true)) {
            getBindings().getPartner().setDisabled(true);
            getBindings().getAccount().setDisabled(true);
            getBindings().getCardGroup().setDisabled(true);
            getBindings().getCard().setDisabled(true);
            getBindings().getRole().setDisabled(true);
            getBindings().getSearchStringInputtext().setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartner());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRole());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchStringInputtext());
        }
        else if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().equals(false)) {
            getBindings().getPartner().setDisabled(false);
            getBindings().getAccount().setDisabled(false);
            getBindings().getCardGroup().setDisabled(false);
            getBindings().getCard().setDisabled(false);
            getBindings().getRole().setDisabled(false);
            getBindings().getSearchStringInputtext().setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartner());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRole());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchStringInputtext());
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside singlecardRadioValueChangeListener method of User Display");
    }

    public void roleValueChangeListener(ValueChangeEvent valueChangeEvent) {
        isTableVisible = false;
    }

    public List<SelectItem> getRoleList() {
        if (roleList == null) {
            roleList = new ArrayList<SelectItem>();
            SelectItem selectItem = new SelectItem();
            if (resourceBundle.containsKey("B2B_ADMIN")) {
                selectItem.setLabel(resourceBundle.getObject("B2B_ADMIN").toString());
                selectItem.setValue("WCP_CARD_B2B_ADMIN");
                roleList.add(selectItem);
            }
            SelectItem selectItem1 = new SelectItem();
            if (resourceBundle.containsKey("B2B_MANAGER_AC")) {
                selectItem1.setLabel(resourceBundle.getObject("B2B_MANAGER_AC").toString());
                selectItem1.setValue("WCP_CARD_B2B_MGR_AC");
                roleList.add(selectItem1);
            }
            SelectItem selectItem2 = new SelectItem();
            if (resourceBundle.containsKey("B2B_MANAGER_CG")) {
                selectItem2.setLabel(resourceBundle.getObject("B2B_MANAGER_CG").toString());
                selectItem2.setValue("WCP_CARD_B2B_MGR_CG");
                roleList.add(selectItem2);
            }
            SelectItem selectItem3 = new SelectItem();
            if (resourceBundle.containsKey("B2B_EMPLOYEE")) {
                selectItem3.setLabel(resourceBundle.getObject("B2B_EMPLOYEE").toString());
                selectItem3.setValue("WCP_CARD_B2B_EMP");
                roleList.add(selectItem3);
            }
        }
        return roleList;
    }
    
    /**
     * @param errorVar
     * @return
     */
    public String showErrorMessage(String errorVar) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside showErrorMessage method of User Dispaly");
        if (errorVar != null) {
            if (resourceBundle.containsKey(errorVar)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject(errorVar), "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting showErrorMessage method of User Dispaly");
        return null;
    }
    
    public List suggestedSearchString(String string) {
        ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();
        for(int i = 0; i < suggestedCardNumberList.size(); i++){
            if(suggestedCardNumberList.get(i).toString().toUpperCase().contains(string)){
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel(suggestedCardNumberList.get(i));
                selectItem.setValue(suggestedCardNumberList.get(i));
                selectItems.add(selectItem);
            }
        }
        return selectItems;
    }
    
    public void searchResult(ActionEvent actionEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside searchResult method of User Display");
        isTableVisible = true;
        isCgMgrVisible = false;
        isEmpVisible = false;
        isAccMgrVisible = false;
        isAdminVisible = false;
        if(getBindings().getSearchStringInputtext().getValue() != null && getBindings().getSearchStringInputtext().getValue().equals(true)){
            isCgMgrVisible = true;
            isEmpVisible = true;
            isAccMgrVisible = true;
            isAdminVisible = true;
        }else{
            if(getBindings().getRole().getValue().toString().contains("WCP_CARD_B2B_ADMIN")){
                isAdminVisible = true;
            }
            if(getBindings().getRole().getValue().toString().contains("WCP_CARD_B2B_MGR_AC")){
                isAccMgrVisible = true;
            }
            if(getBindings().getRole().getValue().toString().contains("WCP_CARD_B2B_MGR_CG")){
                isCgMgrVisible = true;
            }
            if(getBindings().getRole().getValue().toString().contains("WCP_CARD_B2B_EMP")){
                isEmpVisible = true;
            }
        }
        try{
            if(getBindings().getMultipleCardRadio().getValue().equals(true) && getBindings().getSingleCardRadio().getValue().equals(false)){
                if(getBindings().getPartner().getValue() != null){
                    if(getBindings().getAccount().getValue() != null){
                    } else {
                        showErrorMessage("ENGAGE_NO_ACCOUNT");
                    }
                    
                    if (getBindings().getCardGroup().getValue() != null) {
                    } else {
                        showErrorMessage("ENGAGE_NO_CARD_GROUP");
                    }
                    
                    if (getBindings().getCard().getValue() != null) {
                    } else {
                        showErrorMessage("ENGAGE_NO_CARD");
                    }
                    
                    if (getBindings().getRole().getValue() != null) {
                    } else {
                        showErrorMessage("ENGAGE_NO_ROLE");
                    }
                    
                    if(getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_ADMIN")){
                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForAdminRVO1Iterator");
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName","WCP_CARD_B2B_ADMIN");
                        vo.setNamedWhereClauseParam("PID", populateStringValues(getBindings().getPartner().getValue().toString().trim()));
                        vo.executeQuery();
                        isTableVisible = true;
                    }
                    if(getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_MGR_AC")){
                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForAccMgrRVO1Iterator");
                        if (accountQuery.length() > 1 && accountQuery != null) {
                            if (vo.getWhereClause() != null) {
                                if (accountQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
                                    if (mapAccountListValue != null) {
                                        for (int i = 0; i < mapAccountListValue.size(); i++) {
                                            String values = "account" + i;
                                            vo.removeNamedWhereClauseParam(values);
                                        }
                                    } else {
                                        vo.removeNamedWhereClauseParam("account");
                                    }
                                    vo.setWhereClause("");
                                    vo.executeQuery();
                                }
                            }
                        }
                        accountQuery = "(";  
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName","WCP_CARD_B2B_MGR");
                        vo.setNamedWhereClauseParam("PID", populateStringValues(getBindings().getPartner().getValue().toString()));
                        if (accountIdValue.size() > 150) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
                            mapAccountListValue = valueList.callValueList(accountIdValue.size(), accountIdValue);
                            for (int i = 0; i < mapAccountListValue.size(); i++) {
                                String values = "account" + i;
                                accountQuery = accountQuery + "INSTR(:" + values + ",ACCOUNT_ID)<>0 OR ";
                            }
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Account Query Values =" + accountQuery);
                            accountQuery = accountQuery.substring(0, accountQuery.length() - 3);
                            accountQuery = accountQuery + ")";
                        } else {
                            mapAccountListValue = null;
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                            accountQuery = "INSTR(:account,ACCOUNT_ID)<>0  ";
                        }
                        vo.setWhereClause(accountQuery);
                        String passingRole = "WCP_CARD_B2B_MGR";
                        
                        if (accountIdValue.size() > 150) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values > 150 ");
                            mapAccountListValue = valueList.callValueList(accountIdValue.size(), accountIdValue);
                            for (int i = 0; i < mapAccountListValue.size(); i++) {
                                String values = "account" + i;
                                String listName = "listName" + i;
                                vo.defineNamedWhereClauseParam(values, mapAccountListValue.get(listName), null);
                            }
                        } else {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                            vo.defineNamedWhereClauseParam("account", populateStringValues(getBindings().getAccount().getValue().toString()), null);
                        }
                        
                        vo.executeQuery();
                        session.setAttribute("user_display_account_Query", accountQuery);
                        session.setAttribute("user_display_map_Account_List", mapAccountListValue);
                        isTableVisible = true;
                    }
                    if(getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_MGR_CG")){
                        
                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForCgMgrRVO1Iterator");
                        if (cardGroupQuery.length() > 1 && cardGroupQuery != null) {
                            if (vo.getWhereClause() != null) {
                                if (cardGroupQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
                                    if (mapCardGroupListValue != null) {
                                        for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                                            String values = "cardGroup" + i;
                                            vo.removeNamedWhereClauseParam(values);
                                        }
                                    } else {
                                        vo.removeNamedWhereClauseParam("cardGroup");
                                    }
                                    vo.setWhereClause("");
                                    vo.executeQuery();
                                }
                            }
                        }
                        cardGroupQuery = "(";                        

                        if (cardGroupValue.size() > 150) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values > 150 ");
                            mapCardGroupListValue = valueList.callValueList(cardGroupValue.size(), cardGroupValue);
                            for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                                String values = "cardGroup" + i;
                                cardGroupQuery = cardGroupQuery + "INSTR(:" + values + ",PARTNER_ID||CARD_GROUP)<>0 OR ";
                            }
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + "CARDGROUP Query Values =" + cardGroupQuery);
                            cardGroupQuery = cardGroupQuery.substring(0, cardGroupQuery.length() - 3);
                            cardGroupQuery = cardGroupQuery + ")";
                        } else {
                            mapCardGroupListValue = null;
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 ");
                            cardGroupQuery = "INSTR(:cardGroup,PARTNER_ID||CARD_GROUP)<>0 ";
                        }
                        
                        vo.setWhereClause(cardGroupQuery);
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName", "WCP_CARD_B2B_MGR");
                        vo.setNamedWhereClauseParam("PID", populateStringValues(getBindings().getPartner().getValue().toString()));
                        if (cardGroupValue.size() > 150) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values > 150 ");
                            mapCardGroupListValue = valueList.callValueList(cardGroupValue.size(), cardGroupValue);
                            for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                                String values = "cardGroup" + i;
                                String listName = "listName" + i;
                                vo.defineNamedWhereClauseParam(values, mapCardGroupListValue.get(listName), null);
                            }
                        } else {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 ");
                            vo.defineNamedWhereClauseParam("cardGroup", populateStringValues(getBindings().getCardGroup().getValue().toString()), null);
                        }
                        
                        vo.executeQuery();
                        session.setAttribute("user_display_cardGroup_Query", cardGroupQuery);
                        session.setAttribute("user_display_map_CardGroup_List", mapCardGroupListValue);
                        isTableVisible = true;
                    }
                    if(getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_EMP")){
                        
                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForEmpRVO1Iterator");
                        if (cardQuery.length() > 1 && cardQuery != null) {
                            if (vo.getWhereClause() != null) {
                                if (cardQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
                                    if (mapCardListValue != null) {
                                        for (int i = 0; i < mapCardListValue.size(); i++) {
                                            String values = "card" + i;
                                            vo.removeNamedWhereClauseParam(values);
                                        }
                                    } else {
                                        vo.removeNamedWhereClauseParam("card");
                                    }
                                    vo.setWhereClause("");
                                    vo.executeQuery();
                                }
                            }
                        }
                        cardQuery = "(";
                        
                        if (cardNumberValue.size() > 150) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values > 150 ");
                            mapCardListValue = valueList.callValueList(cardNumberValue.size(), cardNumberValue);
                            for (int i = 0; i < mapCardListValue.size(); i++) {
                                String values = "card" + i;
                                cardQuery = cardQuery + "INSTR(:" + values + ",CARD)<>0 OR ";
                            }
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + "CARD Query Values =" + cardQuery);
                            cardQuery = cardQuery.substring(0, cardQuery.length() - 3);
                            cardQuery = cardQuery + ")";
                        } else {
                            mapCardListValue = null;
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values < 150 ");
                            cardQuery = "INSTR(:card,CARD)<>0 ";
                        }
                        vo.setWhereClause(cardQuery);
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName", "WCP_CARD_B2B_EMP");
                        vo.setNamedWhereClauseParam("PID", populateStringValues(getBindings().getPartner().getValue().toString()));
                        if (cardNumberValue.size() > 150) {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values > 150 ");
                            mapCardListValue = valueList.callValueList(cardNumberValue.size(), cardNumberValue);
                            for (int i = 0; i < mapCardListValue.size(); i++) {
                                String values = "card" + i;
                                String listName = "listName" + i;
                                vo.defineNamedWhereClauseParam(values, mapCardListValue.get(listName), null);
                            }
                        } else {
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values < 150 ");
                            vo.defineNamedWhereClauseParam("card", populateStringValues(getBindings().getCard().getValue().toString()), null);
                        }
                        
                        vo.executeQuery();
                        session.setAttribute("user_display_card_Query", cardQuery);
                        session.setAttribute("user_display_map_Card_List", mapCardListValue);
                        isTableVisible = true;
                    }
                    
                }else {
                    showErrorMessage("ENGAGE_NO_PARTNER");
                }
            } else if(getBindings().getSingleCardRadio().getValue().equals(true) && getBindings().getMultipleCardRadio().getValue().equals(false)){
                if(getBindings().getSearchStringInputtext().getValue() != null){
                    String enteredCard = getBindings().getSearchStringInputtext().getValue().toString();
                    String passingPartner = "",passingAccount = "",passingCardgroup = "",passingCard = "",passingRole = "WCP_CARD_B2B_ADMIN,WCP_CARD_B2B_MGR_AC,WCP_CARD_B2B_MGR_CG,WCP_CARD_B2B_EMP";
                    for (int i = 0; i < partnerInfoList.size(); i++) {
                        if (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {
                            for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
                                for (int k = 0; k < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); k++) {
                                    for (int cc = 0; cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size(); cc++){
                                        if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID() != null &&
                                            partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID() != null &&
                                            partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getExternalCardID().equalsIgnoreCase(enteredCard)){
                                            passingCard = partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(cc).getCardID();
                                            passingCardgroup = partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString();
                                            passingAccount = partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString();
                                            passingPartner = partnerInfoList.get(i).getPartnerValue().toString();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    //execute query
                    if (session != null) {
                        if (session.getAttribute("user_display_account_Query") != null) {
                            accountQuery = session.getAttribute("user_display_account_Query").toString().trim();
                            mapAccountListValue = (Map<String, String>)session.getAttribute("user_display_map_Account_List");
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account Query & mapAccountList is found");
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "account " + accountQuery);
                        }
                        if (session.getAttribute("user_display_cardGroup_Query") != null) {
                            cardGroupQuery = session.getAttribute("user_display_cardGroup_Query").toString().trim();
                            mapCardGroupListValue = (Map<String, String>)session.getAttribute("user_display_map_CardGroup_List");
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Query & mapCardGroupList is found");
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup " + cardGroupQuery);
                        }
                        if (session.getAttribute("user_display_card_Query") != null) {
                            cardQuery = session.getAttribute("user_display_card_Query").toString().trim();
                            mapCardListValue = (Map<String, String>)session.getAttribute("user_display_map_Card_List");
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Query & mapCardList is found");
                            _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card " + cardQuery);
                        }
                    }
                    
                    if(getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_ADMIN")){
                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForAdminRVO1Iterator");
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName","WCP_CARD_B2B_ADMIN");
                        vo.setNamedWhereClauseParam("PID", passingPartner);
                        vo.executeQuery();
                        isTableVisible = true;
                    }
                    if(getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_MGR_AC")){
                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForAccMgrRVO1Iterator");
                        if (accountQuery.length() > 1 && accountQuery != null) {
                            if (vo.getWhereClause() != null) {
                                if (accountQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
                                    if (mapAccountListValue != null) {
                                        for (int i = 0; i < mapAccountListValue.size(); i++) {
                                            String values = "account" + i;
                                            vo.removeNamedWhereClauseParam(values);
                                        }
                                    } else {
                                        vo.removeNamedWhereClauseParam("account");
                                    }
                                    vo.setWhereClause("");
                                    vo.executeQuery();
                                }
                            }
                        }
                        accountQuery = "(";                    
                        
                        mapAccountListValue = null;
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                        accountQuery = "INSTR(:account,ACCOUNT_ID)<>0 ";
                        vo.setWhereClause(accountQuery);
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName","WCP_CARD_B2B_MGR");
                        vo.setNamedWhereClauseParam("PID", passingPartner);
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Account Values < 150 ");
                        vo.defineNamedWhereClauseParam("account", passingAccount, null);
                        vo.executeQuery();
                        session.setAttribute("user_display_account_Query", accountQuery);
                        isAccMgrVisible = true;
                    }
                    if(getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_MGR_CG")){
                        
                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForCgMgrRVO1Iterator");
                        if (cardGroupQuery.length() > 1 && cardGroupQuery != null) {
                            if (vo.getWhereClause() != null) {
                                if (cardGroupQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
                                    if (mapCardGroupListValue != null) {
                                        for (int i = 0; i < mapCardGroupListValue.size(); i++) {
                                            String values = "cardGroup" + i;
                                            vo.removeNamedWhereClauseParam(values);
                                        }
                                    } else {
                                        vo.removeNamedWhereClauseParam("cardGroup");
                                    }
                                    vo.setWhereClause("");
                                    vo.executeQuery();
                                }
                            }
                        }
                        cardGroupQuery = "(";                        

                        mapCardGroupListValue = null;
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 ");
                        cardGroupQuery = "INSTR(:cardGroup,PARTNER_ID||CARD_GROUP)<>0 ";
                        
                        vo.setWhereClause(cardGroupQuery);
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName", "WCP_CARD_B2B_MGR");
                        vo.setNamedWhereClauseParam("PID", passingPartner);
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "CardGroup Values < 150 ");
                        vo.defineNamedWhereClauseParam("cardGroup", passingCardgroup, null);
                        
                        vo.executeQuery();
                        session.setAttribute("user_display_cardGroup_Query", cardGroupQuery);
                        session.setAttribute("user_display_map_CardGroup_List", mapCardGroupListValue);
                        isTableVisible = true;
                    }
                    if(getBindings().getRole().getValue().toString().trim().contains("WCP_CARD_B2B_EMP")){
                        
                        ViewObject vo = ADFUtils.getViewObject("PrtUserDisplayForEmpRVO1Iterator");
                        if (cardQuery.length() > 1 && cardQuery != null) {
                            if (vo.getWhereClause() != null) {
                                if (cardQuery.trim().equalsIgnoreCase(vo.getWhereClause().trim())) {
                                    if (mapCardListValue != null) {
                                        for (int i = 0; i < mapCardListValue.size(); i++) {
                                            String values = "card" + i;
                                            vo.removeNamedWhereClauseParam(values);
                                        }
                                    } else {
                                        vo.removeNamedWhereClauseParam("card");
                                    }
                                    vo.setWhereClause("");
                                    vo.executeQuery();
                                }
                            }
                        }
                        cardQuery = "(";
                        
                        mapCardListValue = null;
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values < 150 ");
                        cardQuery = "INSTR(:card,CARD)<>0 ";
                        vo.setWhereClause(cardQuery);
                        vo.setNamedWhereClauseParam("cc", lang);
                        vo.setNamedWhereClauseParam("roleName", "WCP_CARD_B2B_EMP");
                        vo.setNamedWhereClauseParam("PID", passingPartner);
                        _logger.info(accessDC.getDisplayRecord() + this.getClass() + " " + "Card Values < 150 ");
                        vo.defineNamedWhereClauseParam("card", passingCard, null);
                        
                        vo.executeQuery();
                        session.setAttribute("user_display_card_Query", cardQuery);
                        session.setAttribute("user_display_map_Card_List", mapCardListValue);
                        isTableVisible = true;
                    }
                } else{
                    showErrorMessage("ENGAGE_NO_CARD");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside searchResult method of User Display");
    }
    
    public void clearSearchListener(ActionEvent actionEvent) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside clearSearchListener method of User Display");
        getBindings().getPartner().setValue(null);
        getBindings().getAccount().setValue(null);
        getBindings().getCardGroup().setValue(null);
        getBindings().getCard().setValue(null);
        getBindings().getSearchStringInputtext().resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchStringInputtext());
        this.partnerIdValue = null;
        this.accountIdValue = null;
        accountIdList = new ArrayList<SelectItem>();
        this.cardGroupValue = null;
        cardGroupList = new ArrayList<SelectItem>();
        this.cardNumberValue = null;
        cardNumberList = new ArrayList<SelectItem>();
        roleValue = new ArrayList<String>();
        roleValue.add("WCP_CARD_B2B_ADMIN");
        roleValue.add("WCP_CARD_B2B_MGR_AC");
        roleValue.add("WCP_CARD_B2B_MGR_CG");
        roleValue.add("WCP_CARD_B2B_EMP");
        isTableVisible = false;
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Outside clearSearchListener method of User Display");
    }
    
    public void setPartnerIdList(List<SelectItem> partnerIdList) {
        this.partnerIdList = partnerIdList;
    }

    public List<SelectItem> getPartnerIdList() {
        return partnerIdList;
    }

    public void setPartnerIdValue(List<String> partnerIdValue) {
        this.partnerIdValue = partnerIdValue;
    }

    public List<String> getPartnerIdValue() {
        return partnerIdValue;
    }

    public void setCardNumberList(List<SelectItem> cardNumberList) {
        this.cardNumberList = cardNumberList;
    }

    public List<SelectItem> getCardNumberList() {
        return cardNumberList;
    }

    public void setCardNumberValue(List<String> cardNumberValue) {
        this.cardNumberValue = cardNumberValue;
    }

    public List<String> getCardNumberValue() {
        return cardNumberValue;
    }

    public void setAccountIdList(List<SelectItem> accountIdList) {
        this.accountIdList = accountIdList;
    }

    public List<SelectItem> getAccountIdList() {
        return accountIdList;
    }

    public void setAccountIdValue(List<String> accountIdValue) {
        this.accountIdValue = accountIdValue;
    }

    public List<String> getAccountIdValue() {
        return accountIdValue;
    }

    public void setCardGroupList(List<SelectItem> cardGroupList) {
        this.cardGroupList = cardGroupList;
    }

    public List<SelectItem> getCardGroupList() {
        return cardGroupList;
    }

    public void setCardGroupValue(List<String> cardGroupValue) {
        this.cardGroupValue = cardGroupValue;
    }

    public List<String> getCardGroupValue() {
        return cardGroupValue;
    }

    public void setRoleList(List<SelectItem> roleList) {
        this.roleList = roleList;
    }

    public void setRoleValue(List<String> roleValue) {
        this.roleValue = roleValue;
    }

    public List<String> getRoleValue() {
        return roleValue;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setIsTableVisible(Boolean isTableVisible) {
        this.isTableVisible = isTableVisible;
    }

    public Boolean getIsTableVisible() {
        return isTableVisible;
    }

    public void setMultipleCardRadio(RichSelectBooleanRadio multipleCardRadio) {
        this.multipleCardRadio = multipleCardRadio;
    }

    public RichSelectBooleanRadio getMultipleCardRadio() {
        return multipleCardRadio;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setIsCgMgrVisible(Boolean isCgMgrVisible) {
        this.isCgMgrVisible = isCgMgrVisible;
    }

    public Boolean getIsCgMgrVisible() {
        return isCgMgrVisible;
    }

    public void setIsEmpVisible(Boolean isEmpVisible) {
        this.isEmpVisible = isEmpVisible;
    }

    public Boolean getIsEmpVisible() {
        return isEmpVisible;
    }

    public void setIsAccMgrVisible(Boolean isAccMgrVisible) {
        this.isAccMgrVisible = isAccMgrVisible;
    }

    public Boolean getIsAccMgrVisible() {
        return isAccMgrVisible;
    }

    public void setIsAdminVisible(Boolean isAdminVisible) {
        this.isAdminVisible = isAdminVisible;
    }

    public Boolean getIsAdminVisible() {
        return isAdminVisible;
    }

    public class Bindings {
        
        private RichSelectManyChoice partner;
        private RichSelectManyChoice account;
        private RichSelectManyChoice cardGroup;
        private RichSelectManyChoice card;
        private RichSelectManyChoice role;
        private RichSelectBooleanRadio multipleCardRadio;
        private RichSelectBooleanRadio singleCardRadio;
        private RichInputText searchStringInputtext;
        
        public void setPartner(RichSelectManyChoice partner) {
            this.partner = partner;
        }

        public RichSelectManyChoice getPartner() {
            return partner;
        }

        public void setAccount(RichSelectManyChoice account) {
            this.account = account;
        }

        public RichSelectManyChoice getAccount() {
            return account;
        }

        public void setCardGroup(RichSelectManyChoice cardGroup) {
            this.cardGroup = cardGroup;
        }

        public RichSelectManyChoice getCardGroup() {
            return cardGroup;
        }

        public void setCard(RichSelectManyChoice card) {
            this.card = card;
        }

        public RichSelectManyChoice getCard() {
            return card;
        }

        public void setRole(RichSelectManyChoice role) {
            this.role = role;
        }

        public RichSelectManyChoice getRole() {
            return role;
        }
        
        public void setMultipleCardRadio(RichSelectBooleanRadio multipleCardRadio) {
            this.multipleCardRadio = multipleCardRadio;
        }

        public RichSelectBooleanRadio getMultipleCardRadio() {
            return multipleCardRadio;
        }

        public void setSingleCardRadio(RichSelectBooleanRadio singleCardRadio) {
            this.singleCardRadio = singleCardRadio;
        }

        public RichSelectBooleanRadio getSingleCardRadio() {
            return singleCardRadio;
        }

        public void setSearchStringInputtext(RichInputText searchStringInputtext) {
            this.searchStringInputtext = searchStringInputtext;
        }

        public RichInputText getSearchStringInputtext() {
            return searchStringInputtext;
        }
    }
}

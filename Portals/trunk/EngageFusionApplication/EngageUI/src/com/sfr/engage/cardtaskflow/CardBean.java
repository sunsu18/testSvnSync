package com.sfr.engage.cardtaskflow;

import com.sfr.engage.core.PartnerInfo;

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
    private boolean showErrorMsgEditFlag = false;
    private String internalCardNumber = null;
    private String cardEmbossNum = null;

   public CardBean() {
        super();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        statusValue = new ArrayList<String>();
        partnerIdList= new ArrayList<SelectItem>();
        resourceBundle = new EngageResourceBundle();
        partnerId=null;
        if(session.getAttribute("Partner_Object_List") != null){
            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
            if(partnerInfoList!=null && partnerInfoList.size()>0){

                for(int k=0;k<partnerInfoList.size();k++){
                    SelectItem selectItem = new SelectItem();
                    selectItem.setLabel(partnerInfoList.get(k).getPartnerName().toString());
                    selectItem.setValue(partnerInfoList.get(k).getPartnerValue().toString());
                    partnerIdList.add(selectItem);
                }
            }
        }


        statusValue.add("0");
        statusValue.add("1");
        statusValue.add("2");

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
            selectItem.setLabel("Unblocked");
            selectItem.setValue("0");
            statusList.add(selectItem);

            SelectItem selectItem1 = new SelectItem();
            selectItem1.setLabel("Temporary Blocked");
            selectItem1.setValue("1");
            statusList.add(selectItem1);

            SelectItem selectItem2 = new SelectItem();
            selectItem2.setLabel("Permanent Blocked");
            selectItem2.setValue("2");
            statusList.add(selectItem2);



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
//        System.out.println("--------------------------inside partner change listener");
//        System.out.println("new partner id after change is-------------"+valueChangeEvent.getNewValue());
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
//                                         System.out.println("-----------------------added to acc list "+accountIdValue);
                                     }


                                     for(int k=0;k<partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size();k++){
                                     if(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID() != null){

                                         SelectItem selectItem = new SelectItem();
                                         selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupName().toString());
                                         selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
                                         cardGroupList.add(selectItem);
                                         cardGroupValue.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID().toString());
//                                         System.out.println("-----------------------added to cg list "+cardGroupValue);
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
        String accountPassingValues = null;
        String statusPassingValues = null;
        String cardGroupPassingValues = null;
        try {
            if (getBindings().getPartner().getValue() != null) {
                if (getBindings().getAccount().getValue() != null) {
                    accountPassingValues = populateStringValues(getBindings().getAccount().getValue().toString());
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
                    cardGroupPassingValues = populateStringValues(getBindings().getCardGroup().getValue().toString());
                    populateCardGroupValues(cardGroupPassingValues);

                } else {
                    showErrorMessage("ENGAGE_NO_CARD_GROUP");
                    return null;
                }

                if (getBindings().getPartner().getValue() != null) {
                    ViewObject vo = ADFUtils.getViewObject("PrtViewCardsVO1Iterator");
                    vo.setNamedWhereClauseParam("accountID", accountPassingValues);
                    vo.setNamedWhereClauseParam("partnerId", getBindings().getPartner().getValue().toString().trim());
                    vo.setNamedWhereClauseParam("status", statusPassingValues);
                    vo.setNamedWhereClauseParam("cgMain", cardGroupMaintypePassValue);
                    vo.setNamedWhereClauseParam("cgSub", cardGroupSubtypePassValues);
                    vo.setNamedWhereClauseParam("cgSeq", cardGroupSeqPassValues);
                    vo.setNamedWhereClauseParam("countryCd", lang);
                    vo.executeQuery();

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
                driverPGL = true;
                vehiclePGL = false;
//                System.out.println("driver" + valueChangeEvent.getNewValue().toString());
                populateValue(valueChangeEvent.getNewValue().toString());
               

            } else if (valueChangeEvent.getNewValue().equals("Vehicle")) {
                this.getBindings().getDriverNumber().setValue(null);
                this.displayDriverName = null;
                driverPGL = false;
                vehiclePGL = true;
//                System.out.println("vehicle" + valueChangeEvent.getNewValue().toString());
                populateValue(valueChangeEvent.getNewValue().toString());
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

    public String editDetails() {
        cardAssociation = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardAssociation").toString().trim();
        internalCardNumber= AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber").toString().trim();
        cardEmbossNum = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("cardEmbossNum").toString().trim();
        getBindings().getVehicleDriverRadio().setValue(null);
        this.getBindings().getDriverNumber().setValue(null);
        this.getBindings().getVehicleNumber().setValue(null);
        this.displayDriverName=null;
        this.displayVehicleName = null;
        getBindings().getTruckdriverDetails().show(new RichPopup.PopupHints());

        return null;
    }

    public void populateValue(String paramType){
        //        System.out.println("inside populate value param type"+paramType);
        if (paramType != null) {
            associatedAccount = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim();
            //            System.out.println(associatedAccount+"associatedAccount");
            if (paramType.equals("Vehicle") || paramType.equals("Driver")) {
                if (vehiclePGL) {
                    System.out.println("vehiclePGL");
                    vehicleNumberList = new ArrayList<SelectItem>();
                }
                if (driverPGL) {
                    System.out.println("driverPGL");
                    driverNameList = new ArrayList<SelectItem>();

                }
                ViewObject vo = ADFUtils.getViewObject("PrtViewVehicleDriverVO1Iterator");
                System.out.println(vo + "ViewObject");
                if (associatedAccount != null) {
                    System.out.println("inside if" + associatedAccount);
                    vo.setNamedWhereClauseParam("accountValue", associatedAccount);
                }
                System.out.println("country code" + lang);
                vo.setNamedWhereClauseParam("countryCd", lang);

                vo.setNamedWhereClauseParam("paramValue", paramType);
                if (paramType == "Driver") {
                    vo.setNamedWhereClauseParam("driverNumber", null);
                } else {
                    vo.setNamedWhereClauseParam("vehicleNumber", null);
                }
                vo.executeQuery();
                System.out.println("outside if" + vo.getEstimatedRowCount());
                if (vo.getEstimatedRowCount() > 0) {
                    for (int n = 0; n < vo.getEstimatedRowCount(); n++) {
                        while (vo.hasNext()) {
                            PrtViewVehicleDriverVORowImpl currRow = (PrtViewVehicleDriverVORowImpl)vo.next();
                            if (currRow != null) {
                                if (paramType.equals("Vehicle")) {
//                                    System.out.println("param---------" + paramType);
                                    SelectItem selectItem = new SelectItem();

                                    if (currRow.getAttribute("VehicleNumber") != null) {
//                                        System.out.println("VehicleNumber" + currRow.getVehicleNumber());
                                        selectItem.setLabel(currRow.getVehicleNumber().toString());
                                        selectItem.setValue(currRow.getVehicleNumber().toString());
                                        truckDriverList.put(currRow.getVehicleNumber().toString(), currRow.getInternalName());

                                    }
                                    vehicleNumberList.add(selectItem);
                                } else {
//                                    System.out.println("param---------" + paramType);
                                    SelectItem selectItem = new SelectItem();

                                    if (currRow.getAttribute("DriverNumber") != null) {
//                                        System.out.println("getDriverNumber" + currRow.getDriverNumber());
                                        selectItem.setLabel(currRow.getDriverNumber().toString());
                                        selectItem.setValue(currRow.getDriverNumber().toString());
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
            //         System.out.println("inside driver value change");
            displayDriverName = truckDriverList.get(valueChangeEvent.getNewValue().toString());
            //        System.out.println("driver name"+ displayDriverName);

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
//            System.out.println("inside vehicle value change");
            displayVehicleName = truckDriverList.get(valueChangeEvent.getNewValue().toString());
//            System.out.println("driver name" + displayVehicleName);
        }
    }

    public void setDisplayVehicleName(String displayVehicleName) {
        this.displayVehicleName = displayVehicleName;
    }

    public String getDisplayVehicleName() {
        return displayVehicleName;
    }

   public void checkVehicleAssociation() {

        System.out.println("inside vehicle association method");
        System.out.println("vehicle number already associated is" + AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber"));

        ViewObject vehicleVo = ADFUtils.getViewObject("PrtViewVehicleDriverVO1Iterator");

        System.out.println("view object" + vehicleVo);

        vehicleVo.setNamedWhereClauseParam("countryCd", lang);

        vehicleVo.setNamedWhereClauseParam("paramValue", "Vehicle");
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount") != null) {
            System.out.println(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim() + "account");
            vehicleVo.setNamedWhereClauseParam("accountValue",
                                               AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim());
        }

        if (getBindings().getVehicleNumber().getValue() != null) {
            System.out.println("VehicleNumber in where clause" + getBindings().getVehicleNumber().getValue());
            vehicleVo.setNamedWhereClauseParam("vehicleNumber", getBindings().getVehicleNumber().getValue());

        }
        vehicleVo.executeQuery();
        System.out.println("estimated row count of vehicle vo" + vehicleVo.getEstimatedRowCount());
        if (vehicleVo.getEstimatedRowCount() > 0) {
            System.out.println("curr row > 0" + vehicleVo.getEstimatedRowCount());
            while (vehicleVo.hasNext()) {
                System.out.println("inside while of vehicle");
                PrtViewVehicleDriverVORowImpl currRow = (PrtViewVehicleDriverVORowImpl)vehicleVo.next();
//                System.out.println("currRow" + currRow);
//                System.out.println("card number" + currRow.getCardNumber());
                if (currRow != null) {
                    if (currRow.getCardNumber() != null) {
//                        System.out.println("card number" + currRow.getCardNumber());
                        if (resourceBundle.containsKey("TRUCK_CARD_ALREADY_EXIST")) {
//                            System.out.println("vehicle already associated");
                            showErrorMsgEditFlag = true;
                            warningMsg = resourceBundle.getObject("TRUCK_CARD_ALREADY_EXIST").toString().concat(" ").concat(currRow.getCardEmbossNum());
                            System.out.println("warningMsg" + warningMsg);
                        }
                    }

                    else {
//                        System.out.println("internal card number" + AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber"));
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
                                vo.setNamedWhereClauseParam("accountID", accountPassingValues);
                                vo.setNamedWhereClauseParam("partnerId", getBindings().getPartner().getValue().toString().trim());
                                vo.setNamedWhereClauseParam("status", statusPassingValues);
                                vo.setNamedWhereClauseParam("cgMain", cardGroupMaintypePassValue);
                                vo.setNamedWhereClauseParam("cgSub", cardGroupSubtypePassValues);
                                vo.setNamedWhereClauseParam("cgSeq", cardGroupSeqPassValues);
                                vo.setNamedWhereClauseParam("countryCd", lang);
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

            System.out.println("inside driver association method");
            System.out.println("driver number already associated is" + AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber"));

            ViewObject driverVo = ADFUtils.getViewObject("PrtViewVehicleDriverVO1Iterator");

            System.out.println("view object" + driverVo);

            driverVo.setNamedWhereClauseParam("countryCd", lang);

            driverVo.setNamedWhereClauseParam("paramValue", "Driver");

            if( getBindings().getDriverNumber().getValue() != null)
            {
                System.out.println("getDriverNumber in where clause"+ getBindings().getDriverNumber().getValue());
            driverVo.setNamedWhereClauseParam("driverNumber", getBindings().getDriverNumber().getValue());

            }
            if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount") != null) {
                System.out.println(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim()+ "account");
                driverVo.setNamedWhereClauseParam("accountValue",AdfFacesContext.getCurrentInstance().getPageFlowScope().get("associatedAccount").toString().trim());
            }
            driverVo.executeQuery();
            System.out.println("estimated row count of driver vo" + driverVo.getEstimatedRowCount());
            if(driverVo.getEstimatedRowCount() > 0)
            {
                System.out.println("curr row > 0" + driverVo.getEstimatedRowCount());
                while (driverVo.hasNext()) {
                    System.out.println("inside while of driverVo");
                       PrtViewVehicleDriverVORowImpl currRow =(PrtViewVehicleDriverVORowImpl)driverVo.next();
                    System.out.println("currRow"+currRow);
                    System.out.println("card number" + currRow.getCardEmbossNum());
                        if (currRow != null) {
                            if(currRow.getCardNumber() != null)
                            {
                       System.out.println("card number" + currRow.getCardNumber());
            if (resourceBundle.containsKey("DRIVER_CARD_ALREADY_EXIST")) {
                System.out.println("driver already associated");
                showErrorMsgEditFlag=true;
                warningMsg = resourceBundle.getObject("DRIVER_CARD_ALREADY_EXIST").toString().concat(" ").concat(currRow.getCardEmbossNum());
                System.out.println("warningMsg" + warningMsg);
            }
            }

                            else {
                                System.out.println("internal card number" + AdfFacesContext.getCurrentInstance().getPageFlowScope().get("internalCardNumber") );
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
                                        vo.setNamedWhereClauseParam("accountID", accountPassingValues);
                                        vo.setNamedWhereClauseParam("partnerId", getBindings().getPartner().getValue().toString().trim());
                                        vo.setNamedWhereClauseParam("status", statusPassingValues);
                                        vo.setNamedWhereClauseParam("cgMain", cardGroupMaintypePassValue);
                                        vo.setNamedWhereClauseParam("cgSub", cardGroupSubtypePassValues);
                                        vo.setNamedWhereClauseParam("cgSeq", cardGroupSeqPassValues);
                                        vo.setNamedWhereClauseParam("countryCd", lang);
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

        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") != null || AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") != null )
        {

     if(vehiclePGL) {
                System.out.println(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") +"inside vehiclepgl");
                if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") != null)
                {

                    System.out.println("inside vehiclepgl driver ! null");
                if (resourceBundle.containsKey("DRIVER_CARD_EXIST"))
                {

                    showErrorMsgEditFlag=true;
                    System.out.println("driver already associated");
                    warningMsg =  resourceBundle.getObject("DRIVER_CARD_EXIST").toString().concat(" ").concat(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber").toString());
                }

            }
                else
                {
                    if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") != null)
                    {
                    checkVehicleAssociation();
                   // getBindings().getTruckdriverDetails().hide();


                    }
                }
            }
        else
            {

            if(driverPGL)
            {
            System.out.println(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") +"inside driverPGL");
            if( AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") != null)
            {
            System.out.println("inside driverPGL  ! null");

            if (resourceBundle.containsKey("TRUCK_CARD_EXIST"))
            {
                System.out.println("vehicle already associated");
                showErrorMsgEditFlag=true;
                warningMsg =  resourceBundle.getObject("TRUCK_CARD_EXIST").toString().concat(" ").concat(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber").toString());
            }

            }
            else
            {
                if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") != null)
                    {
                checkDriverAssociation();
               // getBindings().getTruckdriverDetails().hide();
            }
            }
            }
        }
        }

        else
        {


        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("DriverNumber") == null && AdfFacesContext.getCurrentInstance().getPageFlowScope().get("VehicleNumber") == null )
        {
            if(vehiclePGL)
            {


                checkVehicleAssociation();
            }
            else
            {
        if(driverPGL)
        {

        checkDriverAssociation();
        }
            }
        }
        }
        
     

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
     getBindings().getVehicleDriverRadio().setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getVehicleDriverRadio());
        getBindings().getTruckdriverDetails().hide();
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
//            System.out.println("status getting converted--------------------------" + statusLabel);
            if(statusLabel.equalsIgnoreCase("0")) {
                return "Unblocked";
            }
            else if(statusLabel.equalsIgnoreCase("1")){
                return "Temporary Blocked";
            }
            else if(statusLabel.equalsIgnoreCase("2")){
                return "Permanent Blocked";
            }
        }
        return null;
    }

    public String statusConversionList(String status){
        if(status != null){
            String statusValueList = "";
            String[] sta = status.split(",");
            for(int i=0; i < sta.length; i++){
                System.out.println("creating , seperated string--------------" + statusValueList);
                statusValueList = statusValueList + statusConversion(sta[i]) + ",";
            }
            statusValueList = statusValueList.substring(0, statusValueList.length() - 1);
            return statusValueList;

        }
        return null;
    }



    public void specificExportExcelListener(FacesContext facesContext, OutputStream outputStream) throws IOException, SQLException {
        System.out.println("Shuttle Size ="+shuttleValue.size());
        String selectedValues="";
        for (int i = 0; i <shuttleValue.size(); i++ ) {
            selectedValues = selectedValues + shuttleValue.get(i).toString().trim() + ",";
        }
        selectedValues=selectedValues.substring(0, selectedValues.length()-1);
        System.out.println("Selected Values"+selectedValues);

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
        System.out.println("list before modifing+++++++++++++++++++++++++++++++++" + getBindings().getStatus().getValue().toString());
        System.out.println("populated string list+++++++++++++++++++++++++++++++++" + populateStringValues(getBindings().getStatus().getValue().toString()));
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
        }    }
    }


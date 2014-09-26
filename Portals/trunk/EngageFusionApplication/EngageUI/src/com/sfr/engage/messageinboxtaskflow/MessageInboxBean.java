package com.sfr.engage.messageinboxtaskflow;

import com.sfr.core.bean.EngageEmaiUtilityl;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.invoiceoverviewtaskflow.InvoiceOverviewBean;

import com.sfr.engage.model.queries.rvo.PrtCustomerCardMapRVO1RowImpl;
import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.ADFUtils;

import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;
import com.sfr.util.validations.Conversion;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import java.util.List;
import java.util.ResourceBundle;

import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.ViewObject;

public class MessageInboxBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    private boolean isMessageAdmin = true;
    private List<SelectItem> categoryList;
    private String categoryValue = null;
    private List<SelectItem> messageTypeList;
    private List<String> messageTypeValue = null;
    ResourceBundle resourceBundle;
    private List<SelectItem> partnerList = null;
    private List<String> partnerValue = null;
    private List<String> accountValue;
    private List<SelectItem> accountList = null;
    private List<String> cardGroupValue;
    private List<SelectItem> cardGroupList = null;
    private List<String> cardValue;
    private List<SelectItem> cardList = null;
    private String customerTypeValue;
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    private AccessDataControl accessDC = new AccessDataControl();
    private Conversion conversionUtility;
    private String langSession = "";
    private boolean resultFromTo = true;
    private boolean resultToFrom = true;
    private boolean isSearchTableVisible = false;
    private List<PartnerInfo> partnerInfoList;


    public MessageInboxBean() {
        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 " constructor of Message Inbox");
        conversionUtility = new Conversion();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        resourceBundle = new EngageResourceBundle();
        partnerList = new ArrayList<SelectItem>();
        partnerValue = new ArrayList<String>();
        accountList = new ArrayList<SelectItem>();
        accountValue = new ArrayList<String>();
        cardGroupList = new ArrayList<SelectItem>();
        cardGroupValue = new ArrayList<String>();
        cardValue = new ArrayList<String>();
        cardList = new ArrayList<SelectItem>();
        messageTypeValue = new ArrayList<String>();

        if (resourceBundle.containsKey("ENGAGE_CATEGORY_ADMIN")) {
            categoryValue =
                    resourceBundle.getObject("ENGAGE_CATEGORY_ADMIN").toString();
        } else {
            categoryValue = "";
        }
        messageTypeValue.add("NO");
        messageTypeValue.add("YES");
        if (session != null) {


            if (session.getAttribute("Partner_Object_List") != null) {
                partnerInfoList =
                        (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
            }

            if (partnerInfoList != null && partnerInfoList.size() > 0) {
                for (int i = 0; i < partnerInfoList.size(); i++) {
                    if (partnerInfoList.get(i).getPartnerName() != null &&
                        partnerInfoList.get(i).getPartnerValue() != null) {
                        SelectItem selectItemPartner = new SelectItem();
                        selectItemPartner.setLabel(partnerInfoList.get(i).getPartnerName().toString());
                        selectItemPartner.setValue(partnerInfoList.get(i).getPartnerValue().toString());
                        partnerList.add(selectItemPartner);
                        partnerValue.add(partnerInfoList.get(i).getPartnerValue().toString());
                    }

                    if (partnerInfoList.get(i).getAccountList() != null &&
                        partnerInfoList.get(i).getAccountList().size() > 0) {
                        for (int j = 0;
                             j < partnerInfoList.get(i).getAccountList().size();
                             j++) {
                            if (partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() !=
                                null) {
                                SelectItem selectItem = new SelectItem();
                                selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                accountList.add(selectItem);
                                accountValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                                if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup() !=
                                    null &&
                                    partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size() >
                                    0) {


                                    for (int cg = 0;
                                         cg < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size();
                                         cg++) {

                                        SelectItem selectItemCardGroup =
                                            new SelectItem();
                                        selectItemCardGroup.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getDisplayCardGroupIdName().toString());
                                        selectItemCardGroup.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                                     partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCardGroupID().toString());
                                        cardGroupList.add(selectItemCardGroup);
                                        cardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                           partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCardGroupID().toString());


                                        if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard() !=
                                            null &&
                                            partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().size() >
                                            0) {
                                            for (int cc = 0;
                                                 cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().size();
                                                 cc++) {
                                                SelectItem selectItemCard =
                                                    new SelectItem();
                                                selectItemCard.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
                                                selectItemCard.setValue(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());
                                                cardList.add(selectItemCard);
                                                cardValue.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().get(cc).getCardID().toString());

                                            }


                                        }


                                        Collections.sort(cardGroupList,
                                                         comparator);

                                    }
                                }
                            }
                        }
                    }
                }

            }

            langSession = (String)session.getAttribute(Constants.userLang);
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " langSession" + langSession);

            Date dateNow = new java.util.Date();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(dateNow);
            gc.add(GregorianCalendar.MONTH, -3);
            Date dateBefore = gc.getTime();
            SimpleDateFormat dateformat =
                               new SimpleDateFormat("dd-MMM-yy");
            String tmpFrom = dateformat.format(dateBefore);

            String tmpTo = dateformat.format(dateBefore); 

            Set<String> cardTypeSet = new HashSet<String>();
            List<String> customerTypeList = new ArrayList<String>();
            if (session.getAttribute("cardTypeList") != null) {

                cardTypeSet =
                        (Set<String>)session.getAttribute("cardTypeList");
            }
            List<String> cardTypeListTemp = new ArrayList<String>(cardTypeSet);
            String cardTypeList =
                cardTypeListTemp.toString().substring(1, cardTypeListTemp.toString().length() -
                                                      1).replace("", "");
            
            ViewObject prtCustomerCardMapVO =
                ADFUtils.getViewObject("PrtCustomerCardMapRVO1_1Iterator");
            prtCustomerCardMapVO.setNamedWhereClauseParam("cardType",
                                                          cardTypeList);
            prtCustomerCardMapVO.executeQuery();
            if (prtCustomerCardMapVO.getEstimatedRowCount() != 0) {
                while (prtCustomerCardMapVO.hasNext()) {
                    PrtCustomerCardMapRVO1RowImpl currRow =
                        (PrtCustomerCardMapRVO1RowImpl)prtCustomerCardMapVO.next();
                    if (currRow != null) {
                        customerTypeList.add(currRow.getCustomerType());
                        customerTypeValue =
                                customerTypeList.toString().substring(1,
                                                                      customerTypeList.toString().length() -
                                                                      1).replace("",
                                                                                 "");
                    }
                }
                customerTypeValue = customerTypeValue + ",ALL";

            }
			
			
			                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " lang passed in PRTPCMFEEDS is " +
                         conversionUtility.getCustomerCountryCode(langSession));
                if (customerTypeValue != null) {
                ViewObject prtPCMFeedsVO =
                    ADFUtils.getViewObject("PrtPcmFeedsRVO1Iterator");
                                     
                    
                    if(prtPCMFeedsVO.getWhereClause() != null && "INSTR(:customerType,CUSTOMER_TYPE)<>0  AND INFORMATION_TYPE =:infoType AND COUNTRY_CODE=:countryCode AND EFFECTIVE_DATE >=:fromDate AND EFFECTIVE_DATE <=:toDate AND INSTR(:showFlag,trim(SHOW_FLAG))<>0".equalsIgnoreCase(prtPCMFeedsVO.getWhereClause())){
                        prtPCMFeedsVO.removeNamedWhereClauseParam("customerType");
                        prtPCMFeedsVO.removeNamedWhereClauseParam("infoType");
                        prtPCMFeedsVO.removeNamedWhereClauseParam("countryCode");
                        prtPCMFeedsVO.removeNamedWhereClauseParam("fromDate");
                        prtPCMFeedsVO.removeNamedWhereClauseParam("toDate");
                        prtPCMFeedsVO.removeNamedWhereClauseParam("showFlag");
                        prtPCMFeedsVO.setWhereClause("");
                        prtPCMFeedsVO.executeQuery();
                    }
                    

                    prtPCMFeedsVO.setWhereClause("INSTR(:customerType,CUSTOMER_TYPE)<>0  AND INFORMATION_TYPE =:infoType AND COUNTRY_CODE=:countryCode AND EFFECTIVE_DATE <=:fromDate AND END_DATE >=:toDate");
                    prtPCMFeedsVO.defineNamedWhereClauseParam("customerType",
                                                              customerTypeValue,
                                                              null);
                    prtPCMFeedsVO.defineNamedWhereClauseParam("infoType",
                                                              "MESSAGES",
                                                              null);
                    prtPCMFeedsVO.defineNamedWhereClauseParam("countryCode",
                                                              conversionUtility.getCustomerCountryCode(langSession),
                                                              null);
                    prtPCMFeedsVO.defineNamedWhereClauseParam("fromDate",
                                                              tmpFrom,
                                                              null);
                    prtPCMFeedsVO.defineNamedWhereClauseParam("toDate",
                                                              tmpTo,
                                                              null);
                    prtPCMFeedsVO.executeQuery();
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " + "Information Row Count " +
                             prtPCMFeedsVO.getEstimatedRowCount());

							 
				    if (prtPCMFeedsVO.getEstimatedRowCount() > 0) {
				        isSearchTableVisible = true;
				    } 
                    else{
                        isSearchTableVisible = false;
                    }
							 
				}

        }

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

    public void setIsMessageAdmin(boolean isMessageAdmin) {
        this.isMessageAdmin = isMessageAdmin;
    }

    public boolean isIsMessageAdmin() {
        return isMessageAdmin;
    }


    public void setCategoryList(List<SelectItem> categoryList) {
        this.categoryList = categoryList;
    }

    public List<SelectItem> getCategoryList() {
        if (categoryList == null) {
            categoryList = new ArrayList<SelectItem>();
            SelectItem selectItem = new SelectItem();
            if (resourceBundle.containsKey("ENGAGE_CATEGORY_ADMIN")) {
                selectItem.setLabel(resourceBundle.getObject("ENGAGE_CATEGORY_ADMIN").toString());
                selectItem.setValue(resourceBundle.getObject("ENGAGE_CATEGORY_ADMIN").toString());
                categoryList.add(selectItem);
            }
            SelectItem selectItem1 = new SelectItem();
            if (resourceBundle.containsKey("ENGAGE_CATEGORY_NON_ADMIN")) {
                selectItem1.setLabel(resourceBundle.getObject("ENGAGE_CATEGORY_NON_ADMIN").toString());
                selectItem1.setValue(resourceBundle.getObject("ENGAGE_CATEGORY_NON_ADMIN").toString());
                categoryList.add(selectItem1);
            }

        }
        return categoryList;
    }


    public void setMessageTypeList(List<SelectItem> messageTypeList) {
        this.messageTypeList = messageTypeList;
    }

    public List<SelectItem> getMessageTypeList() {
        if (messageTypeList == null) {
            messageTypeList = new ArrayList<SelectItem>();

            SelectItem selectItem1 = new SelectItem();
            if (resourceBundle.containsKey("ENG_READ")) {
                selectItem1.setLabel(resourceBundle.getObject("ENG_READ").toString());
                selectItem1.setValue("NO");
                messageTypeList.add(selectItem1);
            }
            SelectItem selectItem2 = new SelectItem();
            if (resourceBundle.containsKey("ENG_UNREAD")) {
                selectItem2.setLabel(resourceBundle.getObject("ENG_UNREAD").toString());
                selectItem2.setValue("YES");
                messageTypeList.add(selectItem2);
            }
        }
        return messageTypeList;
    }

    public String[] StringConversion(String passedVal) {

        List<String> container;

        String[] val = passedVal.split(",");

        return val;
    }

    public void partnerValueChangeListener(ValueChangeEvent valueChangeEvent) {

    }

    public void accountValueChangeListener(ValueChangeEvent valueChangeEvent) {

    }

    public void cgValueChangeListener(ValueChangeEvent valueChangeEvent) {

    }

    public void clearSearchListener(ActionEvent actionEvent) {


        this.partnerValue = null;
        isSearchTableVisible = false;
        messageTypeValue = null;
        getBindings().getFromDate().setValue(null);
        getBindings().getToDate().setValue(null);
        accountList = new ArrayList<SelectItem>();
        accountValue = new ArrayList<String>();
        cardGroupValue = new ArrayList<String>();
        cardGroupList = new ArrayList<SelectItem>();
        cardValue = new ArrayList<String>();
        cardList = new ArrayList<SelectItem>();
        //        messageTypeList=new ArrayList<SelectItem>();

        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFromDate());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getToDate());
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getMessageType());

    }

    public String showErrorMessage(String errorVar) {
        if (errorVar != null) {
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

    public String populateStringValues(String var) {
        String passingValues = null;
        if (var != null) {
            String lovValues = var.trim();
            String selectedValues =
                lovValues.substring(1, lovValues.length() - 1);
            passingValues = selectedValues.trim();

        }
        return passingValues;
    }

    public void searchResultsListener(ActionEvent actionEvent) {
        String newFromDate = null;
        String newToDate = null;
        String messageValue = null;
        isSearchTableVisible = false;
        
        if(getBindings().getMessageType().getValue() != null) {           
        displayErrorComponent(getBindings().getMessageType(), false);    
        if (isMessageAdmin) {
            if (getBindings().getCategory().getValue() != null &&
                getBindings().getMessageType().getValue() != null &&
                getBindings().getFromDate().getValue() != null &&
                getBindings().getToDate().getValue() != null) {


                if (getBindings().getFromDate().getValue() != null &&
                    getBindings().getToDate().getValue() != null) {
                    DateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
                    Date effectiveFromDate =
                        (java.util.Date)getBindings().getFromDate().getValue();
                    Date effectiveToDate1 =
                        (java.util.Date)getBindings().getToDate().getValue();
                    newFromDate = sdf.format(effectiveFromDate);
                    newToDate = sdf.format(effectiveToDate1);

                    if (effectiveToDate1.before(effectiveFromDate)) {
                        log.info(accessDC.getDisplayRecord() +
                                 this.getClass() + " " +
                                 "value of new from date ================>" +
                                 newFromDate);
                        showErrorMessage("ENGAGE_VALID_FROM_TO_DATE");
                        return;
                    }
                }


                if (getBindings().getMessageType().getValue() != null) {
                    messageValue =
                            populateStringValues(getBindings().getMessageType().getValue().toString());
                }

                Date date = new Date();
                java.sql.Date passedDate = new java.sql.Date(date.getTime());
                Set<String> cardTypeSet = new HashSet<String>();
                List<String> customerTypeList = new ArrayList<String>();
                if (session.getAttribute("cardTypeList") != null) {

                    cardTypeSet =
                            (Set<String>)session.getAttribute("cardTypeList");
                }

                List<String> cardTypeListTemp =
                    new ArrayList<String>(cardTypeSet);
                String cardTypeList =
                    cardTypeListTemp.toString().substring(1, cardTypeListTemp.toString().length() -
                                                          1).replace("", "");

                ViewObject prtCustomerCardMapVO =
                    ADFUtils.getViewObject("PrtCustomerCardMapRVO1_1Iterator");
                prtCustomerCardMapVO.setNamedWhereClauseParam("cardType",
                                                              cardTypeList);
                prtCustomerCardMapVO.executeQuery();
                if (prtCustomerCardMapVO.getEstimatedRowCount() != 0) {
                    while (prtCustomerCardMapVO.hasNext()) {
                        PrtCustomerCardMapRVO1RowImpl currRow =
                            (PrtCustomerCardMapRVO1RowImpl)prtCustomerCardMapVO.next();
                        if (currRow != null) {
                            customerTypeList.add(currRow.getCustomerType());
                            customerTypeValue =
                                    customerTypeList.toString().substring(1,
                                                                          customerTypeList.toString().length() -
                                                                          1).replace("",
                                                                                     "");
                        }
                    }
                    customerTypeValue = customerTypeValue + ",ALL";
                }
                if (customerTypeValue != null) {
                    ViewObject prtPCMFeedsVO =
                        ADFUtils.getViewObject("PrtPcmFeedsRVO1Iterator");


                    if (prtPCMFeedsVO.getWhereClause() != null &&
                        "INSTR(:customerType,CUSTOMER_TYPE)<>0  AND INFORMATION_TYPE =:infoType AND COUNTRY_CODE=:countryCode AND EFFECTIVE_DATE <=:fromDate AND END_DATE >=:toDate".equalsIgnoreCase(prtPCMFeedsVO.getWhereClause())) {
                        prtPCMFeedsVO.removeNamedWhereClauseParam("customerType");
                        prtPCMFeedsVO.removeNamedWhereClauseParam("infoType");
                        prtPCMFeedsVO.removeNamedWhereClauseParam("countryCode");
                        prtPCMFeedsVO.removeNamedWhereClauseParam("fromDate");
                        prtPCMFeedsVO.removeNamedWhereClauseParam("toDate");
                        prtPCMFeedsVO.setWhereClause("");
                        prtPCMFeedsVO.executeQuery();
                    }


                    prtPCMFeedsVO.setWhereClause("INSTR(:customerType,CUSTOMER_TYPE)<>0  AND INFORMATION_TYPE =:infoType AND COUNTRY_CODE=:countryCode AND EFFECTIVE_DATE >=:fromDate AND EFFECTIVE_DATE <=:toDate AND INSTR(:showFlag,trim(SHOW_FLAG))<>0");


                    prtPCMFeedsVO.defineNamedWhereClauseParam("customerType",
                                                              customerTypeValue,
                                                              null);
                    prtPCMFeedsVO.defineNamedWhereClauseParam("infoType",
                                                              "MESSAGES",
                                                              null);
                    prtPCMFeedsVO.defineNamedWhereClauseParam("countryCode",
                                                              conversionUtility.getCustomerCountryCode(langSession),
                                                              null);
                    prtPCMFeedsVO.defineNamedWhereClauseParam("fromDate",
                                                              newFromDate,
                                                              null);
                    prtPCMFeedsVO.defineNamedWhereClauseParam("toDate",
                                                              newToDate, null);
                    prtPCMFeedsVO.defineNamedWhereClauseParam("showFlag",
                                                              messageValue,
                                                              null);
                    prtPCMFeedsVO.executeQuery();
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             " " + "Messages Row Count " +
                             prtPCMFeedsVO.getEstimatedRowCount());
                    if (prtPCMFeedsVO.getEstimatedRowCount() > 0) {
                        isSearchTableVisible = true;
                    } else {
                        isSearchTableVisible = false;
                        if(messageValue.equalsIgnoreCase("NO")) {
                            FacesMessage msg =
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                 (String)resourceBundle.getObject("ENG_NO_READ_MSGS"),
                                                 "");
                            FacesContext.getCurrentInstance().addMessage(null,
                                                                         msg);                            
                        }
                        
                        else  if(messageValue.equalsIgnoreCase("YES")) {
                            FacesMessage msg =
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                 (String)resourceBundle.getObject("ENG_NO_UNREAD_MSGS"),
                                                 "");
                            FacesContext.getCurrentInstance().addMessage(null,
                                                                         msg);                            
                        }
                        
                        else { 
                        if (resourceBundle.containsKey("ENG_NO_ADMIN_MSGS")) {
                            FacesMessage msg =
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                 (String)resourceBundle.getObject("ENG_NO_ADMIN_MSGS"),
                                                 "");
                            FacesContext.getCurrentInstance().addMessage(null,
                                                                         msg);
                        }
                        }
                    }
                }

            } else {
                showErrorMessage("ENGAGE_SELECT_TRANSACTION_MANDATORY");
                return;
            }
        } else {
            isSearchTableVisible = false;
            if (resourceBundle.containsKey("ENG_NO_ADMIN_MSGS")) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                     (String)resourceBundle.getObject("NO_DATA"),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null,
                                                             msg);
            }
            return;
        }
        
    }
    else {
        displayErrorComponent(getBindings().getMessageType(), true);    
        showErrorMessage("ENGAGE_SELECT_TRANSACTION_MANDATORY");          
    }
    }


    public void displayErrorComponent(UIComponent component, boolean status) {

        RichSelectManyChoice soc = new RichSelectManyChoice();

         if (component instanceof RichSelectManyChoice) {
            soc = (RichSelectManyChoice)component;
            if (status) {
                soc.setContentStyle("af_mandatoryfield");
                if (component.getId().contains("selectManyChoice1") ||
                    component.getId().contains("smc1") ||
                    component.getId().contains("selectManyChoice3") ||
                    component.getId().contains("selectManyChoice4") ||
                    component.getId().contains("smc4"))
                    soc.setContentStyle("af_mandatoryfield");

            } else {
                soc.setContentStyle("af_mandatoryfield");
                if (component.getId().contains("selectManyChoice1") ||
                    component.getId().contains("smc1") ||
                    component.getId().contains("selectManyChoice3") ||
                    component.getId().contains("selectManyChoice4") ||
                    component.getId().contains("smc4"))
                    soc.setContentStyle("af_mandatoryfield");
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(soc);
        }
      
    }

    private Boolean isComponentEmpty(UIComponent rit1) {

        RichSelectManyChoice soc = new RichSelectManyChoice();
        if (rit1 instanceof RichSelectManyChoice) {
            soc = (RichSelectManyChoice)rit1;
            if (soc.getValue() == null || soc.getValue().equals("")) {
              
                displayErrorComponent(soc, true);
                return true;
            } else {
                displayErrorComponent(soc, false);
                return false;
            }
        }
        return true;
    }


    public void setPartnerList(List<SelectItem> partnerList) {
        this.partnerList = partnerList;
    }

    public List<SelectItem> getPartnerList() {
        return partnerList;
    }

    public void setPartnerValue(List<String> partnerValue) {
        this.partnerValue = partnerValue;
    }

    public List<String> getPartnerValue() {
        return partnerValue;
    }

    public void setAccountValue(List<String> accountValue) {
        this.accountValue = accountValue;
    }

    public List<String> getAccountValue() {
        return accountValue;
    }

    public void setAccountList(List<SelectItem> accountList) {
        this.accountList = accountList;
    }

    public List<SelectItem> getAccountList() {
        return accountList;
    }

    public void setCardGroupValue(List<String> cardGroupValue) {
        this.cardGroupValue = cardGroupValue;
    }

    public List<String> getCardGroupValue() {
        return cardGroupValue;
    }

    public void setCardGroupList(List<SelectItem> cardGroupList) {
        this.cardGroupList = cardGroupList;
    }

    public List<SelectItem> getCardGroupList() {
        return cardGroupList;
    }

    public void setCardValue(List<String> cardValue) {
        this.cardValue = cardValue;
    }

    public List<String> getCardValue() {
        return cardValue;
    }

    public void setCardList(List<SelectItem> cardList) {
        this.cardList = cardList;
    }

    public List<SelectItem> getCardList() {
        return cardList;
    }

    public void setCustomerTypeValue(String customerTypeValue) {
        this.customerTypeValue = customerTypeValue;
    }

    public String getCustomerTypeValue() {
        return customerTypeValue;
    }

    //    public void categoryVCE(ValueChangeEvent valueChangeEvent) {
    //        if(valueChangeEvent.getNewValue()!=null && valueChangeEvent.getNewValue().equals("Admin")){
    //            log.info(accessDC.getDisplayRecord() + this.getClass() +
    //                     " " + "inside category VCE for Admin ");
    //            isMessageAdmin=true;
    //        }
    //        else{
    //            log.info(accessDC.getDisplayRecord() + this.getClass() +
    //                     " " + "inside category VCE for non-Admin ");
    //            isMessageAdmin=false;
    //            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartnerNumber());
    //            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
    //            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
    //            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());
    //
    //        }
    //    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public void setMessageTypeValue(List<String> messageTypeValue) {
        this.messageTypeValue = messageTypeValue;
    }

    public List<String> getMessageTypeValue() {
        return messageTypeValue;
    }

    public void categoryValueChangeEvent(ValueChangeEvent valueChangeEvent) {
        isSearchTableVisible = false;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().searchTablePanel);
        log.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                 "inside category VCE");


        if (valueChangeEvent.getNewValue() != null &&
            valueChangeEvent.getNewValue().equals(resourceBundle.getObject("ENGAGE_CATEGORY_ADMIN").toString())) {
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                               " " + "inside category VCE for Admin ");
            isMessageAdmin = true;

        } else {
            log.info(accessDC.getDisplayRecord() + this.getClass() + " " +
                     "inside category VCE for non-Admin ");
            isMessageAdmin = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getPartnerNumber());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroup());
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCard());

        }
    }

    public void setIsSearchTableVisible(boolean isSearchTableVisible) {
        this.isSearchTableVisible = isSearchTableVisible;
    }

    public boolean isIsSearchTableVisible() {
        return isSearchTableVisible;
    }

    public void messageTypeValueChangeEvent(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            isSearchTableVisible = false;
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().searchTablePanel);
        }
    }


    public class Bindings {

        private RichSelectManyChoice partnerNumber;
        private RichSelectManyChoice cardGroup;
        private RichSelectManyChoice account;
        private RichSelectManyChoice card;
        private RichInputDate fromDate;
        private RichInputDate toDate;
        private RichSelectOneChoice category;
        private RichSelectManyChoice messageType;
        private RichPanelGroupLayout searchTablePanel;

        public void setPartnerNumber(RichSelectManyChoice partnerNumber) {
            this.partnerNumber = partnerNumber;
        }

        public RichSelectManyChoice getPartnerNumber() {
            return partnerNumber;
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

        public void setCard(RichSelectManyChoice card) {
            this.card = card;
        }

        public RichSelectManyChoice getCard() {
            return card;
        }

        public void setFromDate(RichInputDate fromDate) {

            if (resultFromTo) {

                Date dateNow = new java.util.Date();
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(dateNow);
                gc.add(GregorianCalendar.MONTH, -3);
                Date dateBefore = gc.getTime();
                SimpleDateFormat dateformat =
                    new SimpleDateFormat("dd.MM.yyyy");
                String tmp = dateformat.format(dateBefore);
                fromDate.setValue(tmp);
                resultFromTo = false;
            }

            this.fromDate = fromDate;


        }

        public RichInputDate getFromDate() {
            return fromDate;
        }

        public void setToDate(RichInputDate toDate) {
            if (resultToFrom) {

                Date dateNow = new java.util.Date();
                SimpleDateFormat dateformat =
                    new SimpleDateFormat("dd.MM.yyyy");
                String tmp = dateformat.format(dateNow);
                toDate.setValue(tmp);
                resultToFrom = false;
            }
            this.toDate = toDate;
        }

        public RichInputDate getToDate() {
            return toDate;
        }

        public void setCategory(RichSelectOneChoice category) {
            this.category = category;
        }

        public RichSelectOneChoice getCategory() {
            return category;
        }

        public void setMessageType(RichSelectManyChoice messageType) {
            this.messageType = messageType;
        }

        public RichSelectManyChoice getMessageType() {
            return messageType;
        }

        public void setSearchTablePanel(RichPanelGroupLayout searchTablePanel) {
            this.searchTablePanel = searchTablePanel;
        }

        public RichPanelGroupLayout getSearchTablePanel() {
            return searchTablePanel;
        }
    }
}

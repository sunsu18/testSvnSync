package com.sfr.engage.invoiceoverviewtaskflow;

import com.sfr.engage.authenticatedhometaskflow.AuthenticatedHomeBean;

import com.sfr.engage.model.queries.uvo.PrtAccountVORowImpl;
import com.sfr.util.ADFUtils;

import java.io.Serializable;

import java.util.ArrayList;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.ViewObject;

public class InvoiceOverviewBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    private String accountValue;
    private List<String> cardGroupValue;
    private String invoiceTypeValue;    
    private List<String> cardValue;    
    private ArrayList<SelectItem> accountList = null;
    private ArrayList<SelectItem> invoiceTypeList = null;
    private boolean searchResults=false;
    private boolean cardGroup=false;
    private boolean card=false;
    
    public InvoiceOverviewBean() {
        super();
    }
    
    public ArrayList<SelectItem> getAccountList() {
        if (accountList == null) {
            accountList = new ArrayList<SelectItem>();            
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel("100");
                selectItem.setValue("100");
                accountList.add(selectItem);
            SelectItem selectItem1 = new SelectItem();
            selectItem1.setLabel("200");
            selectItem1.setValue("200");
            accountList.add(selectItem1);
//            ViewObject vo = ADFUtils.getViewObject("PrtAccountVO1Iterator");
//            vo.setNamedWhereClauseParam("countryCode", "en_US");
//            vo.executeQuery();
//            accountList = new ArrayList<SelectItem>();
//            while (vo.hasNext()) {
//                PrtAccountVORowImpl currRow = (PrtAccountVORowImpl)vo.next();
//                if (currRow.getAccountId() != null) {
//                    SelectItem selectItem = new SelectItem();
//                    selectItem.setLabel(currRow.getAccountId());
//                    selectItem.setValue(currRow.getAccountId());
//                    accountList.add(selectItem);
//                }
//            }
        }
        return accountList;
    }
    
    public ArrayList<SelectItem> getInvoiceTypeList() {
        if (invoiceTypeList == null) {
//            ViewObject vo = ADFUtils.getViewObject("PrtAccountRVO1Iterator");
//            vo.setNamedWhereClauseParam("countryCode", "en_US");
//            vo.executeQuery();
            invoiceTypeList = new ArrayList<SelectItem>();            
                SelectItem selectItem = new SelectItem();
                selectItem.setLabel("Truck");
                selectItem.setValue("Truck");
                invoiceTypeList.add(selectItem);
            SelectItem selectItem1 = new SelectItem();
            selectItem1.setLabel("Truck V");
            selectItem1.setValue("Truck V");
            invoiceTypeList.add(selectItem1);
            SelectItem selectItem2 = new SelectItem();
            selectItem2.setLabel("Bulk");
            selectItem2.setValue("Bulk");
            invoiceTypeList.add(selectItem2);
            
//            while (vo.hasNext()) {
//                PrtAccountRVORowImpl currRow = (PrtAccountRVORowImpl)vo.next();
//                if (currRow.getAccountId() != null) {
//                    SelectItem selectItem = new SelectItem();
//                    selectItem.setLabel(currRow.getAccountId());
//                    selectItem.setValue(currRow.getAccountId());
//                    accountList.add(selectItem);
//                }
//            }
        }
        return invoiceTypeList;
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

    public void setAccountValue(String accountValue) {
        this.accountValue = accountValue;
    }

    public String getAccountValue() {
        return accountValue;
    }

    public void setInvoiceTypeValue(String invoiceTypeValue) {
        this.invoiceTypeValue = invoiceTypeValue;
    }

    public String getInvoiceTypeValue() {
        return invoiceTypeValue;
    }

    public void searchResultsListener(ActionEvent actionEvent) {
        // Add event code here...
        searchResults=true;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
    }

    public void clearSearchListener(ActionEvent actionEvent) {
        // Add event code here...
        getBindings().getAccount().setValue(null);
        getBindings().getCardGpCardList().setValue(null);
        this.invoiceTypeValue=null;
        getBindings().getFromDate().setValue(null);
        getBindings().getToDate().setValue(null);
        searchResults=false;
        card=false;
        cardGroup=false;
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccount()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());  
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardPGL()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getFromDate());  
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getToDate()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getInvoiceType()); 
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getSearchResults());
    }

    public void setCardValue(List<String> cardValue) {
        this.cardValue = cardValue;
    }

    public List<String> getCardValue() {
        return cardValue;
    }

    public void setCardGroupValue(List<String> cardGroupValue) {
        this.cardGroupValue = cardGroupValue;
    }

    public List<String> getCardGroupValue() {
        return cardGroupValue;
    }

    public String invoiceDetailsCancel() {
        getBindings().getInvoiceDetails().hide();
        return null;
    }

    public String invoiceNumberAction() {
        ViewObject vo =
            ADFUtils.getViewObject("PrtCardTransactionOverviewRVO1Iterator");       
      
        
        getBindings().getInvoiceDetails().show(new RichPopup.PopupHints());
        return null;
    }

    public void cgValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(valueChangeEvent.getNewValue()!=null) {
            System.out.println("Value ="+valueChangeEvent.getNewValue());
            if(valueChangeEvent.getNewValue().equals("CardGroup")) {
            cardGroup=true;
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());  
                card=false;
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardPGL());  
                
            }else{
                card=true;
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardPGL());  
                cardGroup=false;
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupPGL());  
                      
            }            
            
        }
    }

    public void setSearchResults(boolean searchResults) {
        this.searchResults = searchResults;
    }

    public boolean isSearchResults() {
        return searchResults;
    }

    public void setCardGroup(boolean cardGroup) {
        this.cardGroup = cardGroup;
    }

    public boolean isCardGroup() {
        return cardGroup;
    }

    public void setCard(boolean card) {
        this.card = card;
    }

    public boolean isCard() {
        return card;
    }

    public class Bindings {
        private RichSelectOneChoice account;
        private RichSelectOneChoice invoiceType;
        private RichSelectManyChoice cardGroup;
        private RichSelectManyChoice card;
        private RichInputDate fromDate;
        private RichInputDate toDate;
        private RichSelectOneRadio cardGpCardList;
        private RichPopup invoiceDetails;
        private RichPanelGroupLayout searchResults;
        private RichPanelGroupLayout cardGroupPGL;
        private RichPanelGroupLayout cardPGL;

        public void setAccount(RichSelectOneChoice account) {
            this.account = account;
        }

        public RichSelectOneChoice getAccount() {
            return account;
        }

        public void setInvoiceType(RichSelectOneChoice invoiceType) {
            this.invoiceType = invoiceType;
        }

        public RichSelectOneChoice getInvoiceType() {
            return invoiceType;
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

        public void setSearchResults(RichPanelGroupLayout searchResults) {
            this.searchResults = searchResults;
        }

        public RichPanelGroupLayout getSearchResults() {
            return searchResults;
        }

        public void setInvoiceDetails(RichPopup invoiceDetails) {
            this.invoiceDetails = invoiceDetails;
        }

        public RichPopup getInvoiceDetails() {
            return invoiceDetails;
        }

        public void setCardGroupPGL(RichPanelGroupLayout cardGroupPGL) {
            this.cardGroupPGL = cardGroupPGL;
        }

        public RichPanelGroupLayout getCardGroupPGL() {
            return cardGroupPGL;
        }

        public void setCardPGL(RichPanelGroupLayout cardPGL) {
            this.cardPGL = cardPGL;
        }

        public RichPanelGroupLayout getCardPGL() {
            return cardPGL;
        }

        public void setCardGpCardList(RichSelectOneRadio cardGpCardList) {
            this.cardGpCardList = cardGpCardList;
        }

        public RichSelectOneRadio getCardGpCardList() {
            return cardGpCardList;
        }
    }
}

package com.sfr.engage.invoiceoverviewtaskflow;

import com.sfr.engage.authenticatedhometaskflow.AuthenticatedHomeBean;

import com.sfr.engage.model.queries.uvo.PrtAccountVORowImpl;
import com.sfr.engage.utility.util.ADFUtils;

import java.io.Serializable;

import java.util.ArrayList;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

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
    
    public InvoiceOverviewBean() {
        super();
    }
    
    public ArrayList<SelectItem> getAccountList() {
        if (accountList == null) {
            ViewObject vo = ADFUtils.getViewObject("PrtAccountRVO1Iterator");
            vo.setNamedWhereClauseParam("countryCode", "en_US");
            vo.executeQuery();
            accountList = new ArrayList<SelectItem>();
            while (vo.hasNext()) {
                PrtAccountVORowImpl currRow = (PrtAccountVORowImpl)vo.next();
                if (currRow.getAccountId() != null) {
                    SelectItem selectItem = new SelectItem();
                    selectItem.setLabel(currRow.getAccountId());
                    selectItem.setValue(currRow.getAccountId());
                    accountList.add(selectItem);
                }
            }
        }
        return accountList;
    }
    
    public ArrayList<SelectItem> getInvoiceTypeList() {
        if (invoiceTypeList == null) {
//            ViewObject vo = ADFUtils.getViewObject("PrtAccountRVO1Iterator");
//            vo.setNamedWhereClauseParam("countryCode", "en_US");
//            vo.executeQuery();
            invoiceTypeList = new ArrayList<SelectItem>();
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
        return accountList;
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
    }

    public void clearSearchListener(ActionEvent actionEvent) {
        // Add event code here...
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
        getBindings().getInvoiceDetails().show(new RichPopup.PopupHints());
        return null;
    }

    public class Bindings {
        private RichSelectOneChoice account;
        private RichSelectOneChoice invoiceType;
        private RichSelectManyChoice cardGroup;
        private RichSelectManyChoice card;
        private RichInputDate fromDate;
        private RichInputDate toDate;
        private RichPopup invoiceDetails;
        private RichPanelGroupLayout searchResults;

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
    }
}

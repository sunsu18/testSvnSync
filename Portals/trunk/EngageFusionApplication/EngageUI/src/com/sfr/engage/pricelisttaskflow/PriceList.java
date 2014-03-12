package com.sfr.engage.pricelisttaskflow;

import java.util.Locale;

import oracle.adf.view.rich.component.rich.data.RichTable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;


import oracle.jbo.ViewObject;

public class PriceList {
    private RichTable priceTable;
    private Locale locale = new Locale("sv", "SE");
    private String country;

    public PriceList() {

        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = (HttpSession)request.getSession();
        //country = (String)session.getAttribute("lang");

        country = "NO";
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter;
        if (bindings != null) {
            iter = bindings.findIteratorBinding("PriceListRVO1Iterator");
            System.out.println("DC Iterator bindings found in pricelist");
        } else {
            System.out.println("bindings is null");
            iter = null;
        }
        ViewObject vo = iter.getViewObject();
        vo.setNamedWhereClauseParam("countrycode", country);
        vo.executeQuery();
    }

    public void setPriceTable(RichTable priceTable) {
        this.priceTable = priceTable;
    }

    public RichTable getPriceTable() {
        return priceTable;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}

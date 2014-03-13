package com.sfr.engage.pricelisttaskflow;

import com.sfr.engage.model.queries.rvo.KeyDisplayRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtGenKeyDisplayRVORowImpl;
import java.util.Locale;

import oracle.adf.view.rich.component.rich.data.RichTable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;


import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.component.rich.output.RichSpacer;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.jbo.ViewObject;

public class PriceList {
    private RichTable priceTable;
    private Locale locale = new Locale("sv", "SE");
    private String country;
    private RichOutputText keycode;
    private RichSpacer spacer_getmapping;
    private RichSpacer spacer_mapping;

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

    public void mapvalue(ClientEvent clientEvent) {
        // Add event code here...
       
    }

    public void setKeycode(RichOutputText keycode) {
        this.keycode = keycode;
        System.out.println("keycode=====>"+keycode);
    }

    public RichOutputText getKeycode() {
      
        return keycode;
    }

    public void setSpacer_getmapping(RichSpacer spacer_getmapping) {
        this.spacer_getmapping = spacer_getmapping;
    }

    public RichSpacer getSpacer_getmapping() {
        
        return spacer_getmapping;
    }

    public void setSpacer_mapping(RichSpacer spacer_mapping) {
        this.spacer_mapping = spacer_mapping;
    }

    public RichSpacer getSpacer_mapping() {
//        System.out.println("keycode = " + getKeycode().getValue().toString());
//        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
//        DCIteratorBinding iter;
//        if (bindings != null) {
//            iter = bindings.findIteratorBinding("PrtGenKeyDisplayRVO1Iterator");
//            System.out.println("DC Iterator bindings found in keydisplay");
//        } else {
//            System.out.println("bindings is null");
//            iter = null;
//        }
//        ViewObject vo = iter.getViewObject();
//        vo.setNamedWhereClauseParam("displaykeycode", getKeycode().getValue().toString());
//        vo.executeQuery();
//        if(vo.getEstimatedRowCount()!=0) {
//            while (vo.hasNext()) {
//                PrtGenKeyDisplayRVORowImpl currRow=(PrtGenKeyDisplayRVORowImpl)vo.next();
//                if(currRow!=null) {
//                    String res=currRow.getDisplayValue();
//                    System.out.println("result is " + res);
//                }
//            }
//        }
        return spacer_mapping;
    }
}

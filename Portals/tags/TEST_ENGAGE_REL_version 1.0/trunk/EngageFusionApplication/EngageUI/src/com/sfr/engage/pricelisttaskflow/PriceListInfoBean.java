package com.sfr.engage.pricelisttaskflow;


import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;
import com.sfr.util.validations.Conversion;

import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.jbo.ViewObject;


public class PriceListInfoBean {
    private RichTable pricelistinfotable;
    private Locale locale;
    private String country;
    private String currencyCode;
    private AccessDataControl accessDC = new AccessDataControl();
    public static final ADFLogger LOGGER = AccessDataControl.getSFRLogger();
    private Conversion conversionUtility = new Conversion();

    public PriceListInfoBean() {
        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside Constructor of PriceListInfoBean");
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = (HttpSession)request.getSession();
        if (session != null) {

            country = (String)session.getAttribute(Constants.userLang);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Session not null and lang of user is " + country);
            locale = conversionUtility.getLocaleFromCountryCode(country);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Session not null and locale of user is " + locale);
            currencyCode = conversionUtility.getCurrencyCode(country);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Session not null and Currency code is " + currencyCode);
        } else {
            country = "NO";
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Session null hence lang set as " + country);
            locale = new Locale("sv", "SE");
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Session null and locale of user is " + locale);
            currencyCode = conversionUtility.getCurrencyCode(country);
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Session null and Currency code is " + currencyCode);
        }

        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter;
        if (bindings != null) {
            iter = bindings.findIteratorBinding("PriceListRVO1Iterator");

        } else {
            LOGGER.severe(accessDC.getDisplayRecord() + this.getClass() + "PriceListRVO1Iterator bindings is null");
            iter = null;
        }
        ViewObject vo = iter.getViewObject();
        vo.setNamedWhereClauseParam("currencycode", currencyCode);
        vo.setNamedWhereClauseParam("pricinguom", "LT");
        vo.executeQuery();
        if (vo.getEstimatedRowCount() != 0) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Number of items fetched from database table for price list is " +
                     vo.getEstimatedRowCount());
        }

        LOGGER.fine(accessDC.getDisplayRecord() + this.getClass() + "Exiting from Constructor of PriceListInfoBean");
    }

    public void setPricelistinfotable(RichTable pricelistinfotable) {
        this.pricelistinfotable = pricelistinfotable;
    }

    public RichTable getPricelistinfotable() {
        return pricelistinfotable;
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

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setAccessDC(AccessDataControl accessDC) {
        this.accessDC = accessDC;
    }

    public AccessDataControl getAccessDC() {
        return accessDC;
    }

    public void setConversionUtility(Conversion conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    public Conversion getConversionUtility() {
        return conversionUtility;
    }
}

package com.sfr.engage.pricelisttaskflow;


import com.sfr.engage.utility.util.Conversion;

import java.io.Serializable;

import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.jbo.ViewObject;


public class PriceList_new implements Serializable{
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    
    private transient Bindings bindings;
    private Locale locale = new Locale("sv", "SE");
    private String country;
    private String currency_code;
//    private static ADFLogger logger = ADFLogger.createADFLogger("En_portal");
    
    /**
     * @return bindings Object
     */
    public Bindings getBindings() {
        if (bindings == null) {
            bindings = new Bindings();
        }
        return bindings;
    }
    
    public PriceList_new() {
        
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = (HttpSession)request.getSession();
        //country = (String)session.getAttribute("lang");
        
        country = "NO";
        Conversion convert = new Conversion();
        currency_code = convert.getCurrencyCode(country);
        System.out.println("Currency code " + currency_code);
//        logger.info("Currency code " + currency_code);
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter;
        if (bindings != null) {
            iter = bindings.findIteratorBinding("PriceListNewRVO1Iterator");
            System.out.println("DC Iterator bindings found in pricelist");
        } else {
            System.out.println("bindings is null");
            iter = null;
        }
        ViewObject vo = iter.getViewObject();
        vo.setNamedWhereClauseParam("currencycode", currency_code );
        vo.setNamedWhereClauseParam("pricinguom", "LT" );
        vo.executeQuery();
    }


    /**
     * @param locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * @return
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param currency_code
     */
    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    /**
     * @return
     */
    public String getCurrency_code() {
        return currency_code;
    }

//    public static void setLogger(ADFLogger logger) {
//        PriceList_new.logger = logger;
//    }
//
//    public static ADFLogger getLogger() {
//        return logger;
//    }
    public class Bindings {
        private RichTable pricelisttable;

        /**
         * @param pricelisttable
         */
        public void setPricelisttable(RichTable pricelisttable) {
            this.pricelisttable = pricelisttable;
        }

        /**
         * @return
         */
        public RichTable getPricelisttable() {
            return pricelisttable;
        }
        
    }
}

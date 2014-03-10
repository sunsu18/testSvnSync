package com.sfr.engage.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.output.RichSpacer;

import oracle.jbo.ViewObject;
import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;


import oracle.binding.BindingContainer;

import oracle.jbo.ViewObject;

public class HomeCarouselBean {
    String customerType = "B2B";
    String lang = "SE";
    private RichSpacer testSpacer;

    public HomeCarouselBean() {
    
            
       
    


        }
        


        public String timepass() {
            // Add event code here...
            return null;
        }

        public void goProductCatalogListener(ActionEvent actionEvent) {
            System.out.println("link again");
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }

        public String getCustomerType() {
            return customerType;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getLang() {
            return lang;
        }

    public void setTestSpacer(RichSpacer testSpacer) {
        this.testSpacer = testSpacer;
    }

    public RichSpacer getTestSpacer() {
        
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = (HttpSession)request.getSession();
        lang = (String)session.getAttribute("lang");
        
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding iter;
                if (bindings != null) {
                    iter = bindings.findIteratorBinding("ProductsDisplayRVO1Iterator");
                    System.out.println("DC Iterator bindings found");
                } else {
                    System.out.println("bindings is null");
                    iter = null;
                }
                ViewObject vo = iter.getViewObject();
                vo.setNamedWhereClauseParam("countryCode", "SE");
                vo.setNamedWhereClauseParam("catalogType", "PP");
                vo.setNamedWhereClauseParam("customerType", "B2B");
                vo.executeQuery();
        
        return testSpacer;
    }
}

package com.sfr.engage.util;

import com.sfr.engage.model.resources.EngageResourceBundle;
import java.util.ResourceBundle;
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
    String profile = "private";
    private RichSpacer testSpacer;
    String banner;
    String card1;
    String card2;
    String card_name1;
    String card_name2;
    String card_desc1;
    String card_desc2;
    ResourceBundle resourceBundle;
    
    public HomeCarouselBean() {

            getCard1();
            getCard2();
            resourceBundle=new EngageResourceBundle();
            getCard_name1();
            getCard_name2();
            getCard_desc1();
            getCard_desc2();   
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
        HttpSession session = (HttpSession)request.getSession(false);
        lang = (String)session.getAttribute("lang");
        profile=(String)session.getAttribute("profile");
//        lang= request.getParameter("lang");
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

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }
    
    public void setBanner(String banner) {
        this.banner = banner;
    }
    
//    public String getBanner() {
//        
//        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
//        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
//        HttpSession session = (HttpSession)request.getSession(false);
//        lang = (String)session.getAttribute("lang");
//        profile = (String)session.getAttribute("profile");
////        profile=request.getParameter("profile");
////        lang=request.getParameter("lang");
//        
//        if(profile.equalsIgnoreCase("private")){
//            if(lang.equalsIgnoreCase("se_SE")){
//                banner="Banner2";
//            }
//            else if(lang.equalsIgnoreCase("en_US")){
//                banner="Banner2";                
//            }
//            else if(lang.equalsIgnoreCase("da_UK")){
//                banner="Banner1";                
//            }
//            else if(lang.equalsIgnoreCase("no_NO")){
//                banner="Banner3";                
//            }
//        }
//        
//        else if(profile.equalsIgnoreCase("business")){
//            if(lang.equalsIgnoreCase("se_SE")){
//                banner="Banner2";
//            }
//            else if(lang.equalsIgnoreCase("en_US")){
//                banner="Banner2";                
//            }
//            else if(lang.equalsIgnoreCase("da_UK")){
//                banner="Banner1";                
//            }
//            else if(lang.equalsIgnoreCase("no_NO")){
//                banner="Banner3";                
//            }
//        }
//        
//        return banner;
//    }

    public void setCard1(String card1) {
        this.card1 = card1;
    }

    public String getCard1() {
        
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = (HttpSession)request.getSession(false);
        lang = (String)session.getAttribute("lang");
        profile = (String)session.getAttribute("profile");
//                profile=request.getParameter("profile");
//        lang=request.getParameter("lang");
        
        if(profile.equalsIgnoreCase("private")){
            if(lang.equalsIgnoreCase("se_SE")){
                card1="Europe-Card";
            }
            else if(lang.equalsIgnoreCase("en_US")){
                card1="Europe-Card";                
            }
            else if(lang.equalsIgnoreCase("da_DK")){
                card1="Company-Card";                
            }
            else if(lang.equalsIgnoreCase("no_NO")){
                card1="Company-Card";                
            }
        }
        else if(profile.equalsIgnoreCase("business")){
            if(lang.equalsIgnoreCase("se_SE")){
                card1="Europe-Card";
            }
            else if(lang.equalsIgnoreCase("en_US")){
                card1="Europe-Card";                
            }
            else if(lang.equalsIgnoreCase("da_DK")){
                card1="Company-Card";                
            }
            else if(lang.equalsIgnoreCase("no_NO")){
                card1="Company-Card";                
            }
        }
        return card1;
    }
    
    public void setCard2(String card2) {
        this.card2 = card2;
    }

    public String getCard2() {
        
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = (HttpSession)request.getSession(false);
        lang = (String)session.getAttribute("lang");
        profile = (String)session.getAttribute("profile");
//                profile=request.getParameter("profile");
//        lang=request.getParameter("lang");
        
        if(profile.equalsIgnoreCase("private")){
            if(lang.equalsIgnoreCase("se_SE")){
                card2="Master-Card";
            }
            else if(lang.equalsIgnoreCase("en_US")){
                card2="Master-Card";                
            }
            else if(lang.equalsIgnoreCase("da_DK")){
                card2="Company-Card";                
            }
            else if(lang.equalsIgnoreCase("no_NO")){
                card2="Company-Card";                
            }
        }
        else if(profile.equalsIgnoreCase("business")){
            if(lang.equalsIgnoreCase("se_SE")){
                card2="Company-Card";
            }
            else if(lang.equalsIgnoreCase("en_US")){
                card2="Europe-Card";                
            }
            else if(lang.equalsIgnoreCase("da_DK")){
                card2="Company-Card";                
            }
            else if(lang.equalsIgnoreCase("no_NO")){
                card2="Company-Card";                
            }
        }
        return card2;
    }

    public String getCard_name1() {
           
           if(card1.equalsIgnoreCase("Company-Card")){
               card_name1="STATOIL_COMPANY_CARD";
               if(resourceBundle.containsKey("STATOIL_COMPANY_CARD")) {
                   card_name1 =(String)resourceBundle.getObject("STATOIL_COMPANY_CARD");
               }
           }
           else if(card1.equalsIgnoreCase("Truck-Card")){
               card_name1="STATOIL_TRUCK_CARD";
               if(resourceBundle.containsKey("STATOIL_TRUCK_CARD")) {
                   card_name1 =(String)resourceBundle.getObject("STATOIL_TRUCK_CARD");
               }
           }
           else if(card1.equalsIgnoreCase("Europe-Card")){
               card_name1="STATOIL_EUROPE_CARD";
               if(resourceBundle.containsKey("STATOIL_EUROPE_CARD")) {
                   card_name1 =(String)resourceBundle.getObject("STATOIL_EUROPE_CARD");
               }
           }
           else if(card1.equalsIgnoreCase("Master-Card")){
               card_name1="STATOIL_MASTER_CARD}";
               if(resourceBundle.containsKey("STATOIL_MASTER_CARD")) {
                   card_name1 =(String)resourceBundle.getObject("STATOIL_MASTER_CARD");
               }
           }
           return card_name1;
       }

       public void setCard_name2(String card_name2) {
           this.card_name2 = card_name2;
       }

       public String getCard_name2() {
           
           if(card2.equalsIgnoreCase("Company-Card")){
               card_name2="STATOIL_COMPANY_CARD";     
               if (resourceBundle.containsKey("STATOIL_COMPANY_CARD")) {
                   card_name2 =(String)resourceBundle.getObject("STATOIL_COMPANY_CARD");
               }
           }
           else if(card1.equalsIgnoreCase("Truck-Card")){
               card_name2="STATOIL_TRUCK_CARD";
               if (resourceBundle.containsKey("STATOIL_TRUCK_CARD")) {
                   card_name2 =(String)resourceBundle.getObject("STATOIL_TRUCK_CARD");
               }
           }
           else if(card2.equalsIgnoreCase("Europe-Card")){
               card_name2="STATOIL_EUROPE_CARD";
               if (resourceBundle.containsKey("STATOIL_EUROPE_CARD")) {
                   card_name2 =(String)resourceBundle.getObject("STATOIL_EUROPE_CARD");
               }
           }
           else if(card2.equalsIgnoreCase("Master-Card")){
               card_name2="STATOIL_MASTER_CARD";
               if (resourceBundle.containsKey("STATOIL_MASTER_CARD")) {
                   card_name2 =(String)resourceBundle.getObject("STATOIL_MASTER_CARD");
               }
           }
           return card_name2;
       }

       public void setCard_name1(String card_name1) {
           this.card_name1 = card_name1;
       }

    public void setCard_desc1(String card_desc1) {
        this.card_desc1 = card_desc1;
    }

    public String getCard_desc1() {
        
        if(card1.equalsIgnoreCase("Company-Card")){
            if (resourceBundle.containsKey("STATOIL_COMPANY_CARD_DESC")) {
                card_desc1 =(String)resourceBundle.getObject("STATOIL_COMPANY_CARD_DESC");
            }
        }
        else if(card1.equalsIgnoreCase("Truck-Card")){
            if (resourceBundle.containsKey("STATOIL_TRUCK_CARD_DESC")) {
                card_desc1 =(String)resourceBundle.getObject("STATOIL_TRUCK_CARD_DESC");
            }
        }
        else if(card1.equalsIgnoreCase("Europe-Card")){
            if (resourceBundle.containsKey("STATOIL_EUROPE_CARD_DESC")) {
                card_desc1 =(String)resourceBundle.getObject("STATOIL_EUROPE_CARD_DESC");
            }
        }
        else if(card1.equalsIgnoreCase("Master-Card")){
            if (resourceBundle.containsKey("STATOIL_MASTER_CARD_DESC")) {
                card_desc1 =(String)resourceBundle.getObject("STATOIL_MASTER_CARD_DESC");
            }
        }
        return card_desc1;
    }

    public void setCard_desc2(String card_desc2) {
        this.card_desc2 = card_desc2;
    }

    public String getCard_desc2() {
        
        if(card2.equalsIgnoreCase("Company-Card")){
            if (resourceBundle.containsKey("STATOIL_COMPANY_CARD_DESC")) {
                card_desc2 =(String)resourceBundle.getObject("STATOIL_COMPANY_CARD_DESC");
            }
        }
        else if(card1.equalsIgnoreCase("Truck-Card")){
            if (resourceBundle.containsKey("STATOIL_TRUCK_CARD_DESC")) {
                card_desc2 =(String)resourceBundle.getObject("STATOIL_TRUCK_CARD_DESC");
            }
        }
        else if(card2.equalsIgnoreCase("Europe-Card")){
            if (resourceBundle.containsKey("STATOIL_EUROPE_CARD_DESC")) {
                card_desc2 =(String)resourceBundle.getObject("STATOIL_EUROPE_CARD_DESC");
            }
        }
        else if(card2.equalsIgnoreCase("Master-Card")){
            if (resourceBundle.containsKey("STATOIL_MASTER_CARD_DESC")) {
                card_desc2 =(String)resourceBundle.getObject("STATOIL_MASTER_CARD_DESC");
            }
        }
        return card_desc2;
    }
  
}

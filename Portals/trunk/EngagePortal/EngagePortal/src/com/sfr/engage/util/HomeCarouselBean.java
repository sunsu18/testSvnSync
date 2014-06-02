package com.sfr.engage.util;


import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.AccessDataControl;
import com.sfr.util.validations.Conversion;

import java.io.IOException;

import java.util.ResourceBundle;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.output.RichSpacer;

import oracle.jbo.ViewObject;


/**
 * TODO : ASHTHA - 02, May, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC
 *  2. Override toString() method
 *  3. Does not implement the Session replication design approach
 */
public class HomeCarouselBean {

    String customerType = "B2B";
    String lang = "SE";
    String profile = "private";
    private RichSpacer testSpacer;
    String banner;
    String card1;
    String card2;
    // TODO : ASHTHA - 02, May, 2014 : Shouldn't all params have same standard as card1/card2
    String card_name1;
    String card_name2;
    String card_desc1;
    String card_desc2;
    String card1_learn_more;
    String card2_learn_more;
    String card1_apply_now;
    String card2_apply_now;
    ResourceBundle resourceBundle;

    String WsPortalCatalogLink = "";

    /**
     * Default Constructor : initialized UI components
     */
    public HomeCarouselBean() {
        getCard1();
        getCard2();
        resourceBundle = new EngageResourceBundle();
        getCard_name1();
        getCard_name2();
        getCard_desc1();
        getCard_desc2();

    }

    // TODO : ASHTHA - 02, May, 2014 : Remove unnecessary methods. Avoid using names like 'timepass'

    /**
     * @return
     */
    public String timepass() {
        // Add event code here...
        return null;
    }

    // TODO : ASHTHA - 02, May, 2014 : Remove below method if not used. SOP not as per format

    /**
     * @param actionEvent
     */
    public void goProductCatalogListener(ActionEvent actionEvent) {
        System.out.println("link again");
    }

    /**
     * @param customerType
     */
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    /**
     * @return
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     * @param lang
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * @return
     */
    public String getLang() {
        return lang;
    }

    /**
     * @param testSpacer
     */
    public void setTestSpacer(RichSpacer testSpacer) {
        this.testSpacer = testSpacer;
    }

    // TODO : ASHTHA - 02, May, 2014 : Write in java doc why did you use a Test Spacer. Instead of Test Spacer give it a more logical name

    /**
     * @return
     */
    public RichSpacer getTestSpacer() {
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = (HttpSession)request.getSession(false); // TODO : ASHTHA - 02, May, 2014 : Remove unnecessary casting

        if(session!=null)
        { WsPortalCatalogLink = "https://shop.statoilfuelretail.com/WsPortal/faces/sfr/productCatalog?lang="+ session.getAttribute("lang")+"&profile="+session.getAttribute("profile");}
        Conversion conv = new Conversion();
        lang = (String)session.getAttribute("lang");
        profile = (String)session.getAttribute("profile");
        if(profile.equalsIgnoreCase("business"))
            { customerType = "B2B";}
        else
            { customerType = "B2C";}

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
        // TODO : ASHTHA - 02, May, 2014 : Query hardcodes the params. Instead values fetched from session should be used
        vo.setNamedWhereClauseParam("countryCode", conv.getLangForWERCSURL((lang)));

        vo.setNamedWhereClauseParam("catalogType", "PP");
        vo.setNamedWhereClauseParam("customerType", customerType);
        System.out.println("countryCode + customerType" + conv.getLangForWERCSURL((lang)) + ","+customerType);
        vo.executeQuery();

        return testSpacer;
    }

    /**
     * @param profile
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     * @return
     */
    public String getProfile() {
        return profile;
    }

    /**
     * @param banner
     */
    public void setBanner(String banner) {
        this.banner = banner;
    }

    // TODO : ASHTHA - 02, May, 2014 : Remove the below block comment if unnecessary
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

    /**
     * @param card1
     */
    public void setCard1(String card1) {
        this.card1 = card1;
    }

    /**
     * @return
     */
    public String getCard1() {
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = (HttpSession)request.getSession(false); // TODO : ASHTHA - 02, May, 2014 : Remove unnecessary casting
        lang = (String)session.getAttribute("lang");
        profile = (String)session.getAttribute("profile");

        // TODO : ASHTHA - 02, May, 2014 : Move the logic into newly added Image Map table
        if (profile.equalsIgnoreCase("private")) {
            if (lang.equalsIgnoreCase("se_SE")) {
                card1 = "Statoil-Commercial-Cards";
            } else if (lang.equalsIgnoreCase("en_US")) {
                card1 = "Statoil-Commercial-Cards";   // To be changed later
            } else if (lang.equalsIgnoreCase("da_DK")) {
                card1 = "Statoil-Business-Cards";
            } else if (lang.equalsIgnoreCase("no_NO")) {
                card1 = "Statoil-Business-Cards";   //To be changed later
            }
        } else if (profile.equalsIgnoreCase("business")) {
            if (lang.equalsIgnoreCase("se_SE")) {
                card1 = "Statoil-Commercial-Cards";
                } else if (lang.equalsIgnoreCase("en_US")) {
                card1 = "Statoil-Commercial-Cards";   // To be changed later
                } else if (lang.equalsIgnoreCase("da_DK")) {
                card1 = "Statoil-Business-Cards";
                } else if (lang.equalsIgnoreCase("no_NO")) {
                card1 = "Statoil-Business-Cards";   //To be changed later
            }
        }
        return card1;
    }

    /**
     * @param card2
     */
    public void setCard2(String card2) {
        this.card2 = card2;
    }

    /**
     * @return
     */
    public String getCard2() {

        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = (HttpSession)request.getSession(false); // TODO : ASHTHA - 02, May, 2014 : Remove unnecessary casting
        lang = (String)session.getAttribute("lang");
        profile = (String)session.getAttribute("profile");

        // TODO : ASHTHA - 02, May, 2014 : Move the logic into newly added Image Map table
        if (profile.equalsIgnoreCase("private")) {
            if (lang.equalsIgnoreCase("se_SE")) {
                card2 = "Statoil-MasterCard";
            } else if (lang.equalsIgnoreCase("en_US")) {
                card2 = "Statoil-MasterCard";   //To be changed later
            } else if (lang.equalsIgnoreCase("da_DK")) {
                card2 = "Europe-Card";
            } else if (lang.equalsIgnoreCase("no_NO")) {
                card2 = "Europe-Card";  //To be changed later
            }
        } else if (profile.equalsIgnoreCase("business")) {
            if (lang.equalsIgnoreCase("se_SE")) {
                card2 = "Statoil-MasterCard";
                } else if (lang.equalsIgnoreCase("en_US")) {
                card2 = "Statoil-MasterCard";   //To be changed later
                } else if (lang.equalsIgnoreCase("da_DK")) {
                card2 = "Europe-Card";
                } else if (lang.equalsIgnoreCase("no_NO")) {
                card2 = "Europe-Card";  //To be changed later
            }
        }
        return card2;
    }

    /**
     * @return
     */
    public String getCard_name1() {
        // TODO : ASHTHA - 02, May, 2014 : Logic for fetching text can be simplified below, if basic naming rules are followed.
        // Then you need not iterate and check what actual card description key is.
        // So instead of description key from STATOIL_COMPANY_CARD it should be, Company-Card_desc as eg
        /**
         *if (resourceBundle.containsKey("STATOIL_COMPANY_CARD")) {
                card_name1 = (String)resourceBundle.getObject("card1" + "_desc");
            }
         */

        if (card1.equalsIgnoreCase("Statoil-Commercial-Cards")) {
            card_name1 = "STATOIL_COMMERCIAL_CARD"; // TODO : ASHTHA - 02, May, 2014 : Is this needed. If not remove from all the below.
            if (resourceBundle.containsKey("STATOIL_COMMERCIAL_CARD")) {
                card_name1 = (String)resourceBundle.getObject("STATOIL_COMMERCIAL_CARD");
            }
        } else if (card1.equalsIgnoreCase("Statoil-Business-Cards")) {
            card_name1 = "STATOIL_BUSINESS_CARD";
            if (resourceBundle.containsKey("STATOIL_BUSINESS_CARD")) {
                card_name1 = (String)resourceBundle.getObject("STATOIL_BUSINESS_CARD");
            }
        } 

        return card_name1;
    }

    /**
     * @param card_name2
     */
    public void setCard_name2(String card_name2) {
        this.card_name2 = card_name2;
    }

    /**
     * @return
     */
    public String getCard_name2() {

        if (card2.equalsIgnoreCase("Statoil-MasterCard")) {
            card_name2 = "STATOIL_MASTER_CARD";
            if (resourceBundle.containsKey("STATOIL_MASTER_CARD")) {
                card_name2 = (String)resourceBundle.getObject("STATOIL_MASTER_CARD");
            }
        } else if (card1.equalsIgnoreCase("Europe-Card")) {
            card_name2 = "STATOIL_EUROPE_CARD";
            if (resourceBundle.containsKey("STATOIL_EUROPE_CARD")) {
                card_name2 = (String)resourceBundle.getObject("STATOIL_EUROPE_CARD");
            }
        } 

        return card_name2;
    }

    /**
     * @param card_name1
     */
    public void setCard_name1(String card_name1) {
        this.card_name1 = card_name1;
    }

    /**
     * @param card_desc1
     */
    public void setCard_desc1(String card_desc1) {
        this.card_desc1 = card_desc1;
    }

    /**
     * @return
     */
    public String getCard_desc1() {

        if (card1.equalsIgnoreCase("Statoil-Commercial-Cards")) {
            if (resourceBundle.containsKey("STATOIL_COMMERCIAL_CARD_TEXT")) {
                card_desc1 = (String)resourceBundle.getObject("STATOIL_COMMERCIAL_CARD_TEXT");
            }
        } else if (card1.equalsIgnoreCase("Statoil-Business-Cards")) {
            if (resourceBundle.containsKey("STATOIL_BUSINESS_CARD_TEXT")) {
                card_desc1 = (String)resourceBundle.getObject("STATOIL_BUSINESS_CARD_TEXT");
            }
        } 

        return card_desc1;
    }

    /**
     * @param card_desc2
     */
    public void setCard_desc2(String card_desc2) {
        this.card_desc2 = card_desc2;
    }

    /**
     * @return
     */
    public String getCard_desc2() {

        if (card2.equalsIgnoreCase("Statoil-MasterCard")) {
            if (resourceBundle.containsKey("STATOIL_MASTER_CARD_TEXT")) {
                card_desc2 = (String)resourceBundle.getObject("STATOIL_MASTER_CARD_TEXT");
            }
        } else if (card2.equalsIgnoreCase("Europe-Card")) {
            if (resourceBundle.containsKey("STATOIL_EUROPE_CARD_TEXT")) {
                card_desc2 = (String)resourceBundle.getObject("STATOIL_EUROPE_CARD_TEXT");
            }
        } 

        return card_desc2;
    }


    /**
     * @param card1_learn_more
     */
    public void setCard1_learn_more(String card1_learn_more) {
        this.card1_learn_more = card1_learn_more;
    }

    /**
     * @return
     */
    public String getCard1_learn_more() {

        if (card1.equalsIgnoreCase("Statoil-Commercial-Cards")) {
            if (resourceBundle.containsKey("STATOIL_COMMERCIAL_CARD_LINK")) {
                card1_learn_more = (String)resourceBundle.getObject("STATOIL_COMMERCIAL_CARD_LINK");
            }
        } else if (card1.equalsIgnoreCase("Statoil-Business-Cards")) {
            if (resourceBundle.containsKey("STATOIL_BUSINESS_CARD_LINK")) {
                card1_learn_more = (String)resourceBundle.getObject("STATOIL_BUSINESS_CARD_LINK");
            }
        } 

        return card1_learn_more;
    }

    /**
     * @param card2_learn_more
     */
    public void setCard2_learn_more(String card2_learn_more) {
        this.card2_learn_more = card2_learn_more;
    }

    /**
     * @return
     */
    public String getCard2_learn_more() {
        if (card2.equalsIgnoreCase("Statoil-MasterCard")) {
            if (resourceBundle.containsKey("STATOIL_MASTER_CARD_LINK")) {
                card2_learn_more = (String)resourceBundle.getObject("STATOIL_MASTER_CARD_LINK");
            }
        } else if (card2.equalsIgnoreCase("Europe-Card")) {
            if (resourceBundle.containsKey("STATOIL_EUROPE_CARD_LINK")) {
                card2_learn_more = (String)resourceBundle.getObject("STATOIL_EUROPE_CARD_LINK");
            }
        } 

        return card2_learn_more;
    }




    public void goProductCatalog(ActionEvent actionEvent) {
        // Add event code here...
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = (HttpSession)request.getSession(false);


        try {
            //System.out.println("Request Context ="+ ectx.getRequestContextPath());
            //String urlRedirect = request.getContextPath() + "/faces/card/transaction/transactions";
            System.out.println("https://shop.statoilfuelretail.com/WsPortal/faces/sfr/productCatalog?lang="+ session.getAttribute("lang")+"&profile="+session.getAttribute("profile"));
            ectx.redirect("https://shop.statoilfuelretail.com/WsPortal/faces/sfr/productCatalog?lang="+ session.getAttribute("lang")+"&profile="+session.getAttribute("profile"));



        } catch (IOException e) {
            System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() +" Error while redirecting to Product Catalog overview page");
        }
    }

    public void setWsPortalCatalogLink(String WsPortalCatalogLink) {
        this.WsPortalCatalogLink = WsPortalCatalogLink;
    }

    public String getWsPortalCatalogLink() {
        return WsPortalCatalogLink;
    }
}

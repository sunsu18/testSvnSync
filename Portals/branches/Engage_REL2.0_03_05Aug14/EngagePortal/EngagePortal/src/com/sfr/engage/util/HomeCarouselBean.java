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
import oracle.adf.share.logging.ADFLogger;
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
    String cardName1;
    String cardName2;
    String cardDesc1;
    String cardDesc2;
    String card1LearnMore;
    String card2LearnMore;
    ResourceBundle resourceBundle;
    AccessDataControl accessDC = new AccessDataControl();
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    

    String wsPortalCatalogLink = "";

    /**
     * Default Constructor : initialized UI components
     */
    public HomeCarouselBean() {
        getCard1();
        getCard2();
        resourceBundle = new EngageResourceBundle();
        getcardName1();
        getcardName2();
        getcardDesc1();
        getcardDesc2();

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
        HttpSession session = request.getSession(false); 

        if(session!=null)
        { wsPortalCatalogLink = "https://shop.statoilfuelretail.com/WsPortal/faces/sfr/productCatalog?lang="+ session.getAttribute("lang")+"&profile="+session.getAttribute("profile");}
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

        } else {
                 log.severe(accessDC.getDisplayRecord() + this.getClass() + " Error : ProductsDisplayRVO1Iterator bindings is null");
            iter = null;
        }
        ViewObject vo = iter.getViewObject();

        vo.setNamedWhereClauseParam("countryCode", conv.getLangForWERCSURL((lang)));

        vo.setNamedWhereClauseParam("catalogType", "PP");
        vo.setNamedWhereClauseParam("customerType", customerType);
        log.info(accessDC.getDisplayRecord() + this.getClass() + "countryCode + customerType" + conv.getLangForWERCSURL((lang)) + ","+customerType);
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
        HttpSession session = request.getSession(false);
        lang = (String)session.getAttribute("lang");
        profile = (String)session.getAttribute("profile");

        // TODO : ASHTHA - 02, May, 2014 : Move the logic into newly added Image Map table
        if (profile.equalsIgnoreCase("private")) {
            if (lang.equalsIgnoreCase("se_SE")) {
                card1 = "Statoil-Commercial-Cards";
            } else if (lang.equalsIgnoreCase("en_US")) {
                card1 = "Statoil-Commercial-Cards";   
                // To be changed later
            } else if (lang.equalsIgnoreCase("da_DK")) {
                card1 = "Statoil-Business-Cards";
            } else if (lang.equalsIgnoreCase("no_NO")) {
                card1 = "Statoil-Business-Cards";   
                //To be changed later
            }
        } else if (profile.equalsIgnoreCase("business")) {
            if (lang.equalsIgnoreCase("se_SE")) {
                card1 = "Statoil-Commercial-Cards";
                } else if (lang.equalsIgnoreCase("en_US")) {
                card1 = "Statoil-Commercial-Cards";   
                // To be changed later
                } else if (lang.equalsIgnoreCase("da_DK")) {
                card1 = "Statoil-Business-Cards";
                } else if (lang.equalsIgnoreCase("no_NO")) {
                card1 = "Statoil-Business-Cards";   
                //To be changed later
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
        HttpSession session = request.getSession(false); 
        lang = (String)session.getAttribute("lang");
        profile = (String)session.getAttribute("profile");

        // TODO : ASHTHA - 02, May, 2014 : Move the logic into newly added Image Map table
        if (profile.equalsIgnoreCase("private")) {
            if (lang.equalsIgnoreCase("se_SE")) {
                card2 = "Statoil-MasterCard";
            } else if (lang.equalsIgnoreCase("en_US")) {
                card2 = "Statoil-MasterCard";   
                //To be changed later
            } else if (lang.equalsIgnoreCase("da_DK")) {
                card2 = "Europe-Card";
            } else if (lang.equalsIgnoreCase("no_NO")) {
                card2 = "Europe-Card";  
                //To be changed later
            }
        } else if (profile.equalsIgnoreCase("business")) {
            if (lang.equalsIgnoreCase("se_SE")) {
                card2 = "Statoil-MasterCard";
                } else if (lang.equalsIgnoreCase("en_US")) {
                card2 = "Statoil-MasterCard";   
                //To be changed later
                } else if (lang.equalsIgnoreCase("da_DK")) {
                card2 = "Europe-Card";
                } else if (lang.equalsIgnoreCase("no_NO")) {
                card2 = "Europe-Card"; 
                //To be changed later
            }
        }
        return card2;
    }

    /**
     * @return
     */
    public String getcardName1() {
        // TODO : ASHTHA - 02, May, 2014 : Logic for fetching text can be simplified below, if basic naming rules are followed.
        // Then you need not iterate and check what actual card description key is.
        // So instead of description key from STATOIL_COMPANY_CARD it should be, Company-Card_desc as eg
        /**
         *if (resourceBundle.containsKey("STATOIL_COMPANY_CARD")) {
                cardName1 = (String)resourceBundle.getObject("card1" + "_desc");
            }
         */

        if (card1.equalsIgnoreCase("Statoil-Commercial-Cards")) {
            cardName1 = "STATOIL_COMMERCIAL_CARD"; 
            // TODO : ASHTHA - 02, May, 2014 : Is this needed. If not remove from all the below.
            if (resourceBundle.containsKey("STATOIL_COMMERCIAL_CARD")) {
                cardName1 = (String)resourceBundle.getObject("STATOIL_COMMERCIAL_CARD");
            }
        } else if (card1.equalsIgnoreCase("Statoil-Business-Cards")) {

            cardName1 = "STATOIL_BUSINESS_CARD";
            if (resourceBundle.containsKey("STATOIL_BUSINESS_CARD")) {
                cardName1 = (String)resourceBundle.getObject("STATOIL_BUSINESS_CARD");
            }
        }

        return cardName1;
    }

    /**
     * @param cardName2
     */
    public void setcardName2(String cardName2) {
        this.cardName2 = cardName2;
    }

    /**
     * @return
     */
    public String getcardName2() {

        if (card2.equalsIgnoreCase("Statoil-MasterCard")) {
            cardName2 = "STATOIL_MASTER_CARD";
            if (resourceBundle.containsKey("STATOIL_MASTER_CARD")) {
                cardName2 = (String)resourceBundle.getObject("STATOIL_MASTER_CARD");
            }
        } else if (card2.equalsIgnoreCase("Europe-Card")) {

            cardName2 = "STATOIL_EUROPE_CARD";
            if (resourceBundle.containsKey("STATOIL_EUROPE_CARD")) {

                cardName2 = (String)resourceBundle.getObject("STATOIL_EUROPE_CARD");
            }
        }

        return cardName2;
    }

    /**
     * @param cardName1
     */
    public void setcardName1(String cardName1) {
        this.cardName1 = cardName1;
    }

    /**
     * @param cardDesc1
     */
    public void setcardDesc1(String cardDesc1) {
        this.cardDesc1 = cardDesc1;
    }

    /**
     * @return
     */
    public String getcardDesc1() {

        if (card1.equalsIgnoreCase("Statoil-Commercial-Cards")) {
            if (resourceBundle.containsKey("STATOIL_COMMERCIAL_CARD_TEXT")) {
                cardDesc1 = (String)resourceBundle.getObject("STATOIL_COMMERCIAL_CARD_TEXT");
            }
        } else if (card1.equalsIgnoreCase("Statoil-Business-Cards")) {
            if (resourceBundle.containsKey("STATOIL_BUSINESS_CARD_TEXT")) {
                cardDesc1 = (String)resourceBundle.getObject("STATOIL_BUSINESS_CARD_TEXT");
            }
        }

        return cardDesc1;
    }

    /**
     * @param cardDesc2
     */
    public void setcardDesc2(String cardDesc2) {
        this.cardDesc2 = cardDesc2;
    }

    /**
     * @return
     */
    public String getcardDesc2() {

        if (card2.equalsIgnoreCase("Statoil-MasterCard")) {
            if (resourceBundle.containsKey("STATOIL_MASTER_CARD_TEXT")) {
                cardDesc2 = (String)resourceBundle.getObject("STATOIL_MASTER_CARD_TEXT");
            }
        } else if (card2.equalsIgnoreCase("Europe-Card")) {
            if (resourceBundle.containsKey("STATOIL_EUROPE_CARD_TEXT")) {
                cardDesc2 = (String)resourceBundle.getObject("STATOIL_EUROPE_CARD_TEXT");
            }
        }

        return cardDesc2;
    }


    /**
     * @param card1LearnMore
     */
    public void setcard1LearnMore(String card1LearnMore) {
        this.card1LearnMore = card1LearnMore;
    }

    /**
     * @return
     */
    public String getcard1LearnMore() {

        if (card1.equalsIgnoreCase("Statoil-Commercial-Cards")) {
            if (resourceBundle.containsKey("STATOIL_COMMERCIAL_CARD_LINK")) {
                card1LearnMore = (String)resourceBundle.getObject("STATOIL_COMMERCIAL_CARD_LINK");
            }
        } else if (card1.equalsIgnoreCase("Statoil-Business-Cards")) {
            if (resourceBundle.containsKey("STATOIL_BUSINESS_CARD_LINK")) {
                card1LearnMore = (String)resourceBundle.getObject("STATOIL_BUSINESS_CARD_LINK");
            }
        }

        return card1LearnMore;
    }

    /**
     * @param card2LearnMore
     */
    public void setcard2LearnMore(String card2LearnMore) {
        this.card2LearnMore = card2LearnMore;
    }

    /**
     * @return
     */
    public String getcard2LearnMore() {
        if (card2.equalsIgnoreCase("Statoil-MasterCard")) {
            if (resourceBundle.containsKey("STATOIL_MASTER_CARD_LINK")) {
                card2LearnMore = (String)resourceBundle.getObject("STATOIL_MASTER_CARD_LINK");
            }
        } else if (card2.equalsIgnoreCase("Europe-Card")) {
            if (resourceBundle.containsKey("STATOIL_EUROPE_CARD_LINK")) {
                card2LearnMore = (String)resourceBundle.getObject("STATOIL_EUROPE_CARD_LINK");
            }
        }

        return card2LearnMore;
    }




    public void goProductCatalog(ActionEvent actionEvent) {
    
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = request.getSession(false);


        try {
          
            ectx.redirect("https://shop.statoilfuelretail.com/WsPortal/faces/sfr/productCatalog?lang="+ session.getAttribute("lang")+"&profile="+session.getAttribute("profile"));



        } catch (IOException e) {
            log.severe(AccessDataControl.getDisplayRecord() + this.getClass() +" Error : while redirecting to Product Catalog overview page");
        }
    }

    public void setwsPortalCatalogLink(String wsPortalCatalogLink) {
        this.wsPortalCatalogLink = wsPortalCatalogLink;
    }

    public String getwsPortalCatalogLink() {
        return wsPortalCatalogLink;
    }
}

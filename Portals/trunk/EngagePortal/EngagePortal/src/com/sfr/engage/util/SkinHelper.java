package com.sfr.engage.util;


import com.sfr.util.AccessDataControl;
import com.sfr.util.ThreadSerialization;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * TODO : ASHTHA - 02, May, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC
 *  2. Override toString() method
 */
public class SkinHelper extends ThreadSerialization {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;

    private String portal;
    private String skinStyleCardPrivate = "sfrCardPrivate";
    private String skinStyleCardBusiness = "sfrCardBusiness";
    private String skinStyleJet = "sfrJet";
    private String skinStylePetro = "sfrPetro";
    private String profile;
    AccessDataControl accessDC = new AccessDataControl();
    private String locale;


    private String currentSkin;

    /**
     */
    public SkinHelper() {
        super();
    }

    /**
     * @param currentSkin
     */
    public void setCurrentSkin(String currentSkin) {
        this.currentSkin = currentSkin;
    }

    /**
     * @return
     */
    public String getCurrentSkin() {
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = request.getSession(); 

//        if (request != null && request.getParameter("profile") != null) {
//            profile = request.getParameter("profile");
//        } else {
//            profile = "private";
//        }

        if(session.getAttribute("profile") != null)
        {profile = (String)session.getAttribute("profile");}
        else
        {profile = "business";}

        if (request.getParameter("portal") != null &&
            (request.getParameter("portal").toString().equalsIgnoreCase("card") || request.getParameter("portal").toString().equalsIgnoreCase("jet") ||
             request.getParameter("portal").toString().equalsIgnoreCase("petro"))) {

            session.setAttribute("portal", request.getParameter("portal"));
        } else {
            String default_profile = "card";

            session.setAttribute("portal", default_profile);
        }

        portal = (String)session.getAttribute("portal");


        if (portal == null) {
            currentSkin = skinStyleCardPrivate;
        } else if ("card".equalsIgnoreCase(portal)) {
            if (profile.equalsIgnoreCase("private")) {
                currentSkin = skinStyleCardPrivate;
                session.setAttribute("profile","private");
            } else {
                currentSkin = skinStyleCardBusiness;
                session.setAttribute("profile","business");
            }


        } else if ("jet".equalsIgnoreCase(portal)) {
            currentSkin = skinStyleJet;
        } else {
            currentSkin = skinStylePetro;
        }
        return currentSkin;
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

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        HttpSession session = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();

                if(session.getAttribute("lang")!= null && session.getAttribute("lang").toString().equalsIgnoreCase("se_SE")) {
                    locale = "sv";
                }
                else
                    if(session.getAttribute("lang")!= null && session.getAttribute("lang").toString().equalsIgnoreCase("ee_EE")) {
                    locale = "et";
                }
                else
                if(session.getAttribute("lang")!= null && session.getAttribute("lang").toString().equalsIgnoreCase("no_NO") || session.getAttribute("lang").toString().equalsIgnoreCase("da_DK") || session.getAttribute("lang").toString().equalsIgnoreCase("lv_LV") || session.getAttribute("lang").toString().equalsIgnoreCase("lt_LT") || session.getAttribute("lang").toString().equalsIgnoreCase("pl_PL"))
                {
                locale = session.getAttribute("lang").toString().substring(0,2);
                }

                return locale;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }

    public String getPortal() {
        return portal;
    }

    public void setSkinStyleCardPrivate(String skinStyleCardPrivate) {
        this.skinStyleCardPrivate = skinStyleCardPrivate;
    }

    public String getSkinStyleCardPrivate() {
        return skinStyleCardPrivate;
    }

    public void setSkinStyleCardBusiness(String skinStyleCardBusiness) {
        this.skinStyleCardBusiness = skinStyleCardBusiness;
    }

    public String getSkinStyleCardBusiness() {
        return skinStyleCardBusiness;
    }

    public void setSkinStyleJet(String skinStyleJet) {
        this.skinStyleJet = skinStyleJet;
    }

    public String getSkinStyleJet() {
        return skinStyleJet;
    }

    public void setSkinStylePetro(String skinStylePetro) {
        this.skinStylePetro = skinStylePetro;
    }

    public String getSkinStylePetro() {
        return skinStylePetro;
    }
}

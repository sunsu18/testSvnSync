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

    String portal;
    String style;
    String skinStyleCardPrivate = "sfrCardPrivate";
    String skinStyleCardBusiness = "sfrCardBusiness";
    String skinStyleJet = "sfrJet";
    String skinStylePetro = "sfrPetro";
    String profile;
    AccessDataControl accessDC = new AccessDataControl();
    String locale;


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
        HttpSession session = (HttpSession)request.getSession(); // TODO : ASHTHA - 02, May, 2014 : Remove unnecessary casting

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
                if(session.getAttribute("lang")!= null && session.getAttribute("lang").toString().equalsIgnoreCase("no_NO") || session.getAttribute("lang").toString().equalsIgnoreCase("da_DK") || session.getAttribute("lang").toString().equalsIgnoreCase("hu_HU") || session.getAttribute("lang").toString().equalsIgnoreCase("ro_RO") || session.getAttribute("lang").toString().equalsIgnoreCase("bg_BG"))
                {
                locale = session.getAttribute("lang").toString().substring(0,2);
                }

                return locale;
    }
}

package com.sfr.engage.util;

import com.sfr.engage.utility.constant.Constants;
import com.sfr.engage.validations.ThreadSerialization;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class SkinHelper extends ThreadSerialization {
    @SuppressWarnings("compatibility:-8414258424579585737")
    private static final long serialVersionUID = 1L;

    String portal;
    String style;
    String skinStyleCard = "sfrCard";
    String skinStyleJet = "sfrJet";
    String skinStylePetro = "sfrPetro";


    //request.getSession(true);
    private String currentSkin;

    public SkinHelper() {
        super();
    }

    public void setCurrentSkin(String currentSkin) {
        this.currentSkin = currentSkin;
    }

    public String getCurrentSkin() {
        System.out.println("inside getCurrentSkin");
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = (HttpSession)request.getSession();
        System.out.println("request ---" + request.getParameter("portal"));

        if (request.getParameter("portal") != null &&
            (request.getParameter("portal").toString().equalsIgnoreCase("card") || request.getParameter("portal").toString().equalsIgnoreCase("jet") ||
             request.getParameter("portal").toString().equalsIgnoreCase("petro"))) {
            System.out.println("before setting in session portal is " + request.getParameter("portal"));
            session.setAttribute("portal", request.getParameter("portal"));
        } else {
            String default_profile = "card";
            System.out.println("PROFILE SET TO DEFAULT = " + default_profile);
            session.setAttribute("portal", default_profile);
        }


        portal = (String)session.getAttribute("portal");
        System.out.println("from session" + (String)session.getAttribute("portal"));

        if (portal == null) {
            currentSkin = skinStyleCard;
            System.out.println("skinStyleCard --------------------------------------");
        } else if (portal.equals("card")) {
            System.out.println("skinStyleCard");
            currentSkin = skinStyleCard;
        } else if (portal.equals("jet")) {
            System.out.println("skinStyleJet");
            currentSkin = skinStyleJet;
        } else {
            System.out.println("skinStylePetro");
            currentSkin = skinStylePetro;
        }
        return currentSkin;
    }
}

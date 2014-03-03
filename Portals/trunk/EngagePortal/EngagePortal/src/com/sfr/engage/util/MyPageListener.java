package com.sfr.engage.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;

public class MyPageListener implements PagePhaseListener {
  
    public MyPageListener() {
        super();
    }

    public void afterPhase(PagePhaseEvent pagePhaseEvent) {
    }

    public void beforePhase(PagePhaseEvent pagePhaseEvent) {
        System.out.println("Before phase");
               
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = (HttpSession)request.getSession();
        String lang = (String)request.getAttribute("lang");
        System.out.println("Inside Home carousal bean");
        if(lang == null){
        System.out.println("Inside Home carousal bean- If block");
            lang="SE";
        }
        session.setAttribute("lang", lang);
    }

  
}

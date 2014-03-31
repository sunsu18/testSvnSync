package com.sfr.engage.util;

import com.sfr.services.core.dao.factory.DAOFactory;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;

import java.security.Principal;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.security.idm.IdentityStore;
import oracle.security.idm.Role;
import oracle.security.idm.RoleManager;
import oracle.security.idm.UserManager;
import oracle.security.jps.JpsContext;
import oracle.security.jps.JpsContextFactory;
import oracle.security.jps.service.idstore.IdentityStoreService;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class LoginBean {
    public LoginBean() {
        super();
    }
    
    public String sfrLogout() {
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = request.getSession(false);

        String lang = DAOFactory.getDefaultLanguage(); 
        if (session.getAttribute(Constants.SESSION_LANGUAGE) != null) {
            lang = (String)session.getAttribute(Constants.SESSION_LANGUAGE);
        }

       
        String temp = "/sfrCommon/faces/logout.jspx?lang=" + lang;

        if (request.getServerName().contains("240") || request.getServerName().contains("53")) {
           temp = request.getContextPath() + "/adfAuthentication?logout=logout&end_url=/faces/card/home";
              //temp = "CustomerPortal/faces/card/home.jspx";
        } else {
            HttpServletResponse response = (HttpServletResponse)ectx.getResponse();
            delete(request, response);
            temp = getLogoutURL("LOGOUT_URL") + "/oam/server/logout?end_url=" + getLogoutURL("PP_LOGOUT_REDIRECT_URL") + "/sfrCommon/faces/logout?lang=" + lang;
        }
        
        try {    
            ectx.redirect(temp);
        } catch (Exception ex) {
            ex.getMessage();
        }
        return null;
    }

    public void delete(HttpServletRequest req, HttpServletResponse res) {
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("/PartnerPortal")) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    res.addCookie(cookie);
                }
            }
        }
    }

    public void eraseCookie(String c_name, String value) {
        FacesContext fctx = null;
        ExtendedRenderKitService erks = null;
        StringBuilder sb1 = null;
        try {
            fctx = FacesContext.getCurrentInstance();
            erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
            sb1 = new StringBuilder();
            sb1.append("var date = new Date();");
            sb1.append("alert(\"Inside Erase Cookie\");");
            sb1.append("date.setTime(date.getTime() - (7 * 24 * 60 * 60 * 1000));");
            sb1.append("var expires = date.toGMTString();");
            sb1.append("document.cookie=\"");
            sb1.append(c_name);
            sb1.append("\"+ \"=\" +\'");
            sb1.append(value);
            sb1.append("\'+ expires + \"; path=/\";");
            
            erks.addScript(fctx, sb1.toString());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public String getCookieVal(String c_name) {
        String val = null;
        Cookie dum = null;
        try {
            val = null;
            dum = getCookie(c_name);
            if (dum != null) {
                val = dum.getValue();
            }
        } catch (Exception e) {
             e.getMessage();
        }

        return val;
    }


    public Cookie getCookie(String cookieName) {
        HttpServletRequest httpServletRequest = null;
        Cookie[] cookies = null;
        try {
            httpServletRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();

            cookies = httpServletRequest.getCookies();

            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {

                    if (cookies[i].getName().equalsIgnoreCase(cookieName)) {
                        return cookies[i];
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public boolean createUser(String username, String password) {
        try {
            JpsContextFactory jps = JpsContextFactory.getContextFactory();
            JpsContext jpsContext = jps.getContext();
            IdentityStoreService storeService = jpsContext.getServiceInstance(IdentityStoreService.class);
            IdentityStore is = storeService.getIdmStore();
            UserManager mn = is.getUserManager();
            RoleManager rm = is.getRoleManager();
            Principal p = mn.createUser(username, password.toCharArray()).getPrincipal();
            Role r = is.searchRole(is.SEARCH_BY_NAME, "member");
            rm.grantRole(r, p);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String getLogoutURL(String param) {
        
        String logoutURL = DAOFactory.getPropertyValue(param);
        if(logoutURL.equalsIgnoreCase("blank"))
        {
            return "";
        }
        return logoutURL;
    }

}

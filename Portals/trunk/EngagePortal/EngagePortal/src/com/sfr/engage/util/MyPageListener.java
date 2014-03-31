package com.sfr.engage.util;

import com.sfr.core.bean.User;
import com.sfr.engage.model.datacontrol.UserClient;

import com.sfr.services.core.dao.factory.DAOFactory;
import com.sfr.util.AccessDataControl;

import com.sfr.util.constants.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.faces.event.PhaseId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.controller.v2.lifecycle.ADFLifecycle;
import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.share.security.SecurityContext;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.webcenter.portalframework.sitestructure.SiteStructure;
import oracle.webcenter.portalframework.sitestructure.SiteStructureContext;

public class MyPageListener implements PagePhaseListener {
    private User user = null;
    private boolean passwordChangeRequired;
    

    public void afterPhase(PagePhaseEvent pagePhaseEvent) {
        if (pagePhaseEvent.getPhaseId() == Lifecycle.PREPARE_RENDER_ID) {
            try {
                SiteStructureContext ctx = SiteStructureContext.getInstance();
                SiteStructure model = ctx.getDefaultSiteStructure();
                model.invalidateCache();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void beforePhase(PagePhaseEvent pagePhaseEvent) {
        System.out.println("Before phase");
               
        //ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        //HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        //HttpSession session = (HttpSession)request.getSession();
        
        ExternalContext ectx = null;
        HttpServletRequest request = null;
        HttpSession session = null;
        ADFContext adfCtx = null;
        SecurityContext securityContext = null;
        UserClient userClient = null;
        Integer phase = pagePhaseEvent.getPhaseId();
        adfCtx = ADFContext.getCurrent();
        securityContext = adfCtx.getSecurityContext();
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession();
        boolean isChangeInRedirectionRequired = false;
        
        try {
            
            String lang = (String)request.getParameter("lang");
            if (lang == null) {
                lang = "en_US";
            }
            session.setAttribute("lang", lang);
            //profile
            String profile = (String)request.getParameter("profile");
            if (profile == null) {
                profile = "private";
            }
            session.setAttribute("profile", profile);
            
            if (phase.equals(ADFLifecycle.PREPARE_MODEL_ID)) {
                // Read lang request parameter
                if (session == null) {                    
                    session = request.getSession(true); //if session is null then create new session
                    
                    System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".beforePhase : "+"Session is NULL"+" New Session ID: " + session.getId());
                }
                    
                }
            
            
            if (phase == 9) {
                // check if user is authenticated, read user information and set in session
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".beforePhase : "+"IN PHASE 9 ");
                if (securityContext.isAuthenticated()) {
                    if (request.getParameter(Constants.SESSION_LANGUAGE) != null &&
                        (request.getParameter(Constants.SESSION_LANGUAGE).toString().equalsIgnoreCase(Constants.LANGUAGE_ENGLISH))) {

                        session.setAttribute(Constants.SESSION_ORIGINAL_LANGUAGE, request.getParameter(Constants.SESSION_LANGUAGE));
                      
                        if (session.getAttribute(Constants.SESSION_LANGUAGE) == null) {
                            session.setAttribute(Constants.SESSION_LANGUAGE, Constants.LANGUAGE_ENGLISH);
                            System.out.println("First login session PP:PH9:MPL: Language:"+session.getAttribute(Constants.SESSION_LANGUAGE));
                        }
                    } else if (session.getAttribute(Constants.SESSION_LANGUAGE) == null) {
                        session.setAttribute(Constants.SESSION_LANGUAGE, Constants.LANGUAGE_ENGLISH);
                    }
                    
                     if (session.getAttribute(Constants.SESSION_USER_INFO) == null) {
                        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".beforePhase : "+"UserInfo in session is null , Fetching the data");
                        setUserInfoInSession(request, session, userClient, securityContext);
                        boolean usererror = (String)session.getAttribute("SESSION_USER_ERROR") == "" ? false : true;
                        if (usererror) {
                            return;
                        }

                    } else {
                        if (user == null) {
                            user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
                            //Todo : Below if loop to be removed once Incident 23951 is fixed. 
                            //SOP's added for troubleshooting incorrect Customer Association problem

                            if (null != user.getRolelist()){
                                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass() +"Incident 23951 - 1 ->"+ 
                                                   user.getUserID() + user.getEmailID() + user.getRolelist());
                                
                            }
                        }
                    }
                }
            }
            
            if (phase == PhaseId.RESTORE_VIEW.getOrdinal()) {
                
                if (!AdfFacesContext.getCurrentInstance().isPostback()) {
                FacesContext facesCtx = FacesContext.getCurrentInstance();
                String currentViewId = facesCtx.getViewRoot().getViewId();
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".beforePhase : "+"facesCtx.getViewRoot().getViewId():: " + currentViewId);
                // if user is authenticated and requested for sign in page then redirect to home page

                if (currentViewId.contains("signin") && securityContext.isAuthenticated()) {
                    System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".beforePhase : "+"Inside OAM authenticated");
                    session.setAttribute(Constants.SESSION_PRIMARY_REQUEST_PAGE_ID, "/faces/card/home");
                    String requestedPage = (String)session.getAttribute(Constants.SESSION_PRIMARY_REQUEST_PAGE_ID);
                    ectx.redirect(ectx.getRequestContextPath() + requestedPage);
                   }
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    
    private void setUserInfoInSession(HttpServletRequest request, HttpSession session, UserClient userClient,
                                      SecurityContext securityContext) throws Exception {
        DAOFactory factory = DAOFactory.getInstance();
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".setUserInfoInSession : "+"MyPageListener.setUserInfoInSession: OPSS called for user: " + securityContext.getUserName());
        List<User> userList = new ArrayList<User>();
        boolean success = false;
        userClient=new UserClient();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 3 && success == false ; i++) {
            
            userList = userClient.searchUserWithUserId(securityContext.getUserName());
            long elapsedTime = System.currentTimeMillis() - startTime;
            session.setAttribute("SESSION_USER_ERROR", "");
            if (securityContext.getUserName().equalsIgnoreCase("weblogic") || securityContext.getUserName().equalsIgnoreCase("wcpadmin")) {
                success = true;
            } else {
                success = validateOPSSCall(userList, session);
                
            }
            if (!success) {
                return;
            }else{
                String methodName = this.getClass().toString()+".setUserInfoInSession";
            }
        }
        
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".setUserInfoInSession : "+"securityContext.getUserName() = <" + securityContext.getUserName() + ">");
        session.setAttribute(Constants.SESSION_USER_NAME, securityContext.getUserName());

        if (userList != null && !userList.isEmpty()) {
            setUser(userList.get(0));
            if (getUser() != null) {
                session.setAttribute(Constants.SESSION_USER_INFO, user);
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".setUserInfoInSession : "+"user.getUserID = <" + user.getUserID() + ">");
                session.setAttribute(Constants.SESSION_USER_DISPLAY_NAME, user.getFirstName());
                if(user.getRolelist() != null && !user.getRolelist().isEmpty())
                {
                    System.out.println("Checking user get role list is proper or not===================>");
                if(user.getRoleList().contains(Constants.ROLE_WCP_CARD_ADMIN)){
                    System.out.println("user role name=======>"+user.getRoleList());
                }
                    
                    //getLanguageForLocalization(session,user.getRolelist());
                }
            } else {
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".setUserInfoInSession : "+"MyPageListener.setUserInfoInSession:  User in userList was Null");
            }
        } else {
            System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".setUserInfoInSession : "+" MyPageListener.setUserInfoInSession: userList was either null or empty");
        }
    }
    
    boolean validateOPSSCall(List<User> liuser, HttpSession session) {
        if (liuser != null && liuser.size() > 0) {
            User us = liuser.get(0);
            if (us != null) {
                if (us.getEmailID() == null) {
                    //                    navigateToErrorPage("ERROR_OPSS");
                    session.setAttribute("SESSION_USER_ERROR", "ERROR_OPSS");
                    System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".validateOPSSCall : "+"User EMAIL is NULL");
                    return false;
                }
                if (us.getRolelist() == null) {
                    //                    navigateToErrorPage("ERROR_OPSS");
                    session.setAttribute("SESSION_USER_ERROR", "ERROR_OPSS");
                    System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".validateOPSSCall : "+"User ROLELIST is NULL");
                    return false;
                } else {
                    if (us.getRolelist().contains("WCP_Administrators") || us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_SFR) 
                     || us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_JET) ||us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN) 
                     || us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) || us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_PETRO) 
                     || us.getRolelist().contains(Constants.ROLE_WCP_ICSR) || us.getRolelist().contains(Constants.ROLE_WCP_ISSM) 
                     ||us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_EMP) ||us.getRolelist().contains(Constants.ROLE_WCP_CARD_ADMIN) 
                     || us.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR) ||us.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)) 
                    {
                        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".validateOPSSCall : "+"Proper roles found");
                    } else {
                        //                        navigateToErrorPage("ERROR_NOROLE");
                        session.setAttribute("SESSION_USER_ERROR", "ERROR_NOROLE");
                        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".validateOPSSCall : "+"Roles found are:" + us.getRolelist() + " You are not authorized to enter Partner Portal.");
                        return false;
                    }
                }
            } else {
                //                navigateToErrorPage("ERROR_OPSS");
                session.setAttribute("SESSION_USER_ERROR", "ERROR_OPSS");
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".validateOPSSCall : "+"User OBJECT is NULL");
                return false;
            }
        } else {
            session.setAttribute("SESSION_USER_ERROR", "ERROR_OPSS");
            System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".validateOPSSCall : "+"user LIST is NULL");
            return false;
        }
        return true;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setPasswordChangeRequired(boolean passwordChangeRequired) {
        this.passwordChangeRequired = passwordChangeRequired;
    }

    public boolean isPasswordChangeRequired() {
        return passwordChangeRequired;
    }
}

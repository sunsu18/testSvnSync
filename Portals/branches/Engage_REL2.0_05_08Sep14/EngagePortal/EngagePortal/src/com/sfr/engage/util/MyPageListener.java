package com.sfr.engage.util;


import com.sfr.core.bean.Roles;
import com.sfr.core.bean.User;
import com.sfr.engage.core.AccountInfo;
import com.sfr.engage.core.CardGroupInfo;
import com.sfr.engage.core.CardInfo;
//import com.sfr.engage.core.MyCardComp;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.model.datacontrol.UserClient;
import com.sfr.engage.model.queries.uvo.PrtAccountVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtCardVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtCardgroupVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtPartnerVORowImpl;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;
import com.sfr.util.validations.Conversion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.controller.v2.lifecycle.ADFLifecycle;
import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.share.security.SecurityContext;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.javatools.resourcebundle.ResourceBundleManagerRT;

import oracle.jbo.ViewObject;

import oracle.webcenter.portalframework.sitestructure.SiteStructure;
import oracle.webcenter.portalframework.sitestructure.SiteStructureContext;


public class MyPageListener implements PagePhaseListener {

    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    private AccessDataControl accessDC = new AccessDataControl();
    private Conversion conv = new Conversion();
    private boolean passwordChangeRequired;
    private boolean consistsTwoCardStatus = false;
    private User user = null;

    private PartnerInfo part = new PartnerInfo();
    private AccountInfo acc = new AccountInfo();
    private CardGroupInfo cardgrp = new CardGroupInfo();
    private CardInfo card = new CardInfo();

    private List<AccountInfo> accountlist = new ArrayList<AccountInfo>();
    private List<CardGroupInfo> cardgrouplist = new ArrayList<CardGroupInfo>();
    private List<CardInfo> cardlist = new ArrayList<CardInfo>();
    private List<CardInfo> unblockedcardlist = new ArrayList<CardInfo>();
    private List<CardInfo> perBlockAndTempBlockCardList;
    private List<CardInfo> perBlockAndActiveCardList;
    private List<CardInfo> perBlockCardList;
    private List<CardInfo> tempBlockCardList;
    private List<CardInfo> activeCardList;
    private List<PartnerInfo> partnerlist = new ArrayList<PartnerInfo>();
    private List<PartnerInfo> partnerListSession = new ArrayList<PartnerInfo>();

 
    private boolean addflagaccount = false;
    private boolean addflagcardgroup = false;
    private boolean addflagcard = false;
    private boolean executeEmp = false;
    private boolean executeAcc = false;
    private boolean accountOverView = false;
    private boolean cardGroupOverview = false;
    private boolean skipOtherRoles = false;
    private AccountInfo accountCheck = new AccountInfo();
    private CardGroupInfo cardGrpCheck = new CardGroupInfo();
    private boolean isChangeInRedirectionRequired = false;


    private Set cardTypeHS = new HashSet();

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
        log.fine(accessDC.getDisplayRecord() + this.getClass() +
                 " Before Phase");

        Integer phase = pagePhaseEvent.getPhaseId();

        boolean createCardGroupList = false;
        boolean createCardList = false;
        List<CardGroupInfo>  cardGrpListCheck = new ArrayList<CardGroupInfo>();

        try {
            if (phase.equals(ADFLifecycle.PREPARE_MODEL_ID)) {
                HttpServletRequest request =
                    ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
                HttpSession session =
                    ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();

                
                if (session != null) {
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             ".beforePhase : " +
                             "********************Session is NOT NULL*************************");
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             ".beforePhase : " +
                             "################Session ID: " + session.getId());
                } else {
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             ".beforePhase : " + "Session is NULL");
                    //if session is null then create new session
                    session = request.getSession(true);
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             ".beforePhase : " + "Session ID: " +
                             session.getId());
                }

                String lang = request.getParameter("lang");

                if (lang == null) {
                    if (session.getAttribute("lang") != null) {
                        lang = (String)session.getAttribute("lang");
                    } else {
                        lang = "se_SE";
                    }
                }
                session.setAttribute("lang", lang);

                String profile = request.getParameter("profile");
                if (profile == null) {
                    if (session.getAttribute("profile") != null) {
                        profile = (String)session.getAttribute("profile");
                    } else {
                        profile = "business";
                    }
                }
                session.setAttribute("profile", profile);
                session.setAttribute(Constants.SESSION_PORTAL_NAME,
                                     Constants.EN_PORTAL);
            }

            if (phase == 9) {
                HttpSession session =
                    ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
                // check if user is authenticated, read user information and set in session
                SecurityContext securityContext =
                    ADFContext.getCurrent().getSecurityContext();
                if (securityContext.isAuthenticated()) {


                    if (session.getAttribute(Constants.SESSION_USER_INFO) ==
                        null) {
                        log.info(accessDC.getDisplayRecord() +
                                 this.getClass() + ".beforePhase : " +
                                 "UserInfo in session is null , Fetching the data");
                        setUserInfoInSession();
                        boolean usererror =
                            (String)session.getAttribute("SESSION_USER_ERROR") ==
                            "" ? false : true;
                        if (usererror) {
                            return;
                        }
                        ResourceBundleManagerRT rt =
                            (ResourceBundleManagerRT)ResourceBundleManagerRT.getResourceBundleManager();
                        rt.flush();
                    } else {
                        if (user == null) {
                            user =
(User)session.getAttribute(Constants.SESSION_USER_INFO);
                        }
                    }
                }
            }

            if (phase == PhaseId.RESTORE_VIEW.getOrdinal()) {
                ExternalContext ectx =
                    FacesContext.getCurrentInstance().getExternalContext();
                HttpSession session =
                    ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
                if (!AdfFacesContext.getCurrentInstance().isPostback()) {
                    FacesContext facesCtx = FacesContext.getCurrentInstance();
                    String currentViewId = facesCtx.getViewRoot().getViewId();
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             ".beforePhase : " +
                             "facesCtx.getViewRoot().getViewId():: " +
                             currentViewId);

                    // if user is authenticated and requested for sign in page then redirect to home page
                    SecurityContext securityContext =
                        ADFContext.getCurrent().getSecurityContext();

                    if (currentViewId.contains("login")) {
                        if (securityContext.isAuthenticated()) {
                            log.info(accessDC.getDisplayRecord() +
                                     this.getClass() + ".beforePhase : " +
                                     "Inside OAM authenticated");
                            session.setAttribute(Constants.SESSION_PRIMARY_REQUEST_PAGE_ID,
                                                 "/faces/card/home");
                            String requestedPage =
                                (String)session.getAttribute(Constants.SESSION_PRIMARY_REQUEST_PAGE_ID);
                            ectx.redirect(ectx.getRequestContextPath() +
                                          requestedPage);
                        } else {
                            log.info(accessDC.getDisplayRecord() +
                                     this.getClass() + ".beforePhase : " +
                                     "Request is for login however Authorization enabled = " +
                                     ADFContext.getCurrent().getSecurityContext().isAuthorizationEnabled() +
                                     "user in class is " + user);
                            log.info(accessDC.getDisplayRecord() +
                                     this.getClass() + ".beforePhase : " +
                                     "User in session =<" +
                                     session.getAttribute(Constants.SESSION_USER_INFO) +
                                     ">");

                        }
                    }


                    if (securityContext.isAuthenticated() &&
                        currentViewId.contains("home") &&
                        user.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR) &&
                        session.getAttribute("IS_SELECT_ASSOCIATION_DONE") ==
                        null) {
                        session.setAttribute("IS_SELECT_ASSOCIATION_DONE",
                                             "true");
                        session.setAttribute("CSR", true);
                        session.setAttribute(Constants.SESSION_PRIMARY_REQUEST_PAGE_ID,
                                             "/faces/selectAssociation");
                        log.info("redirect to select association");
                        String requestedPage =
                            (String)session.getAttribute(Constants.SESSION_PRIMARY_REQUEST_PAGE_ID);
                        ectx.redirect(ectx.getRequestContextPath() +
                                      requestedPage);

                    } else if (securityContext.isAuthenticated() &&
                               currentViewId.contains("home") &&
                               session.getAttribute("IS_HOME_REDIRECTION_DONE") ==
                               null) {
                        session.setAttribute("IS_HOME_REDIRECTION_DONE",
                                             "true");
                        log.info("redirect to home page");
                        session.setAttribute(Constants.SESSION_PRIMARY_REQUEST_PAGE_ID,
                                             "/faces/card/home");
                        String requestedPage =
                            (String)session.getAttribute(Constants.SESSION_PRIMARY_REQUEST_PAGE_ID);
                        ectx.redirect(ectx.getRequestContextPath() +
                                      requestedPage);
                    }

                    // To bypass png amd jpeg and gif requests
                    if (currentViewId.contains(".jpeg") ||
                        currentViewId.contains(".gif") ||
                        currentViewId.contains(".jpg") ||
                        currentViewId.contains(".png")) {
                        return;
                    }
                }
            }

            if (phase.equals(ADFLifecycle.INIT_CONTEXT_ID)) {

                HttpSession session =
                    ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();

                List<PartnerInfo> partnerInfoList =
                    new ArrayList<PartnerInfo>();
                PartnerInfo partnerobj;

                if (session.getAttribute(Constants.SESSION_USER_INFO) !=
                    null &&
                    session.getAttribute("executePartnerObjLogic") == null) {


                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             " Executing logic to prepare Partner object");


                    partnerInfoList = new ArrayList<PartnerInfo>();

                    partnerlist = new ArrayList<PartnerInfo>();

                    user =
(User)session.getAttribute(Constants.SESSION_USER_INFO);
                    log.info("useremail after selection " + user.getEmailID());
                    //This for loop is to go through the entire roleList and corressponding id's and to fetch the partner ids and keep it in partnerInfoList
                    for (int i = 0; i < user.getRoleList().size(); i++) {

                        if (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_ADMIN) ||
                            (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2C_SFR)) ||
                            (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR) ||
                             (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_EMP))) &&
                            user.getRoleList().get(i).getIdString() != null)

                        {


                            int idlist = 0;
                            log.info(accessDC.getDisplayRecord() +
                                     this.getClass() +
                                     " user.getRoleList().get(i).getIdString().size()" +
                                     user.getRoleList().get(i).getIdString().size());
                            do {
                                partnerobj = new PartnerInfo();

                                int pIdStart =
                                    user.getRoleList().get(i).getIdString().get(idlist).indexOf("PP");

                                String pid =
                                    user.getRoleList().get(i).getIdString().get(idlist).substring(pIdStart +
                                                                                                  2,
                                                                                                  pIdStart +
                                                                                                  10);
                                partnerobj.setPartnerValue(pid);

                                if (partnerInfoList.size() > 0) {

                                    boolean addflag = false;
                                    for (int k = 0;
                                         k < partnerInfoList.size(); k++) {

                                        if (partnerInfoList.get(k).getPartnerValue().equalsIgnoreCase(pid)) {

                                            addflag = true;
                                            break;
                                        }


                                    }
                                    if (!addflag)
                                    {   partnerInfoList.add(partnerobj);}
                                } else {
                                    partnerInfoList.add(partnerobj);

                                }
                                idlist++;
                            } while (idlist <
                                     user.getRoleList().get(i).getIdString().size());


                        }
                    }

                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             " Final Partner list size after going through the entire RoleList " +
                             partnerInfoList.size());

                    accountlist = new ArrayList<AccountInfo>();


                    if (partnerInfoList.size() != 0) {

                        partnerlist = partnerInfoList;
                        //TODO : HITK - Check if this does not works properly then add partnerInfoList in session and
                        //in executeAdmin function read from session


                    }
                    partnerListSession = new ArrayList<PartnerInfo>();


                    cardTypeHS = new HashSet();

                    for (int i = 0; i < user.getRoleList().size(); i++) {


                        if (user.getRoleList().get(i).getRoleName().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN)) {
                            log.info(accessDC.getDisplayRecord() +
                                     this.getClass() +
                                     " User contains Admin role");


                            if (user.getRoleList().get(i).getIdString() !=
                                null) {
                                session.setAttribute("partnerLang",
                                                     user.getRoleList().get(i).getIdString().get(0).toString().substring(0,
                                                                                                                         2));
                                session.setAttribute(Constants.userLang,
                                                     user.getRoleList().get(i).getIdString().get(0).toString().substring(0,
                                                                                                                         2));
                                log.info(accessDC.getDisplayRecord() +
                                         this.getClass() +
                                         " partner lang in session setted as " +
                                         session.getAttribute("partnerLang"));

                                int partIndex = 0;
                                do {
                                    executeAdmin(user, partIndex);
                                    partIndex++;
                                } while (partIndex < user.getRoleList().get(i).getIdString().size());
                            }
                            System.out.println(accessDC.getDisplayRecord() + this.getClass() + " Session variable added to avoid multiple execution of code on my page listner after executing B2B Admin role");
                            log.info(accessDC.getDisplayRecord() + this.getClass() + " Session variable added to avoid multiple execution of code on my page listner after executing B2B Admin role");
                            System.out.println(accessDC.getDisplayRecord() + this.getClass() + "cardTypeHS size is " + cardTypeHS.size());
                            log.info(accessDC.getDisplayRecord() + this.getClass() + "cardTypeHS size is " + cardTypeHS.size());
                            session.setAttribute("cardTypeList", cardTypeHS);

                        }

                    }


                    if (session != null)

                    {
                        log.info(accessDC.getDisplayRecord() +
                                 this.getClass() +
                                 "Session is not null & executePartnerObjLogic value is " +
                                 session.getAttribute("executePartnerObjLogic"));

                        if (session.getAttribute("executePartnerObjLogic") ==
                            null) {

                            for (int partid = 0; partid < partnerlist.size();
                                 partid++) {
                                String partnerId =
                                    partnerlist.get(partid).getPartnerValue().toString();
                                part = new PartnerInfo();
                                accountlist = new ArrayList<AccountInfo>();

                                //This variable is just to compare if partner Id from partner list matches with id's from IDM's getattributes
                                log.info(accessDC.getDisplayRecord() +
                                         this.getClass() +
                                         accessDC.getDisplayRecord() +
                                         this.getClass() +
                                         " inside partner iterator " + partid);
                                //TODO : HITK - For multipartner add one for loop for partnerlist size
                                //TODO : HITK- Also to check if partner list values are proper or else get it from session
                                skipOtherRoles = false;

                                if (partnerListSession != null) {
                                    for (int k = 0;
                                         k < partnerListSession.size(); k++){
                                        if (partnerListSession.get(k).getPartnerValue().equalsIgnoreCase(partnerId) &&
                                            partnerListSession.get(k).isCompanyOverview()) {
                                            skipOtherRoles = true;
                                            break;
                                        } else
                                        {  skipOtherRoles = false; }
                                }
                                }
                                if (!skipOtherRoles) {
                                    for (int i = 0;
                                         i < user.getRoleList().size(); i++) {

                                        if (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR)) {
                                            DCBindingContainer bindings;
                                            partnerInfoList =
                                                    new ArrayList<PartnerInfo>();
                                            if (user.getRoleList().get(i).getIdString() !=
                                                null) {


                                                int idlist = 0;
                                                log.info(accessDC.getDisplayRecord() +
                                                         this.getClass() +
                                                         " TEMP ------------->" +
                                                         user.getRoleList().get(i).getIdString().size());
                                                log.info(accessDC.getDisplayRecord() +
                                                         this.getClass() +
                                                         " Partner id " +
                                                         partid + " " +
                                                         user.getRoleList().get(i).getIdString().get(idlist));

                                                if (session.getAttribute("partnerLang") ==
                                                    null){
                                                    session.setAttribute("partnerLang",
                                                                         user.getRoleList().get(i).getIdString().get(0).toString().substring(0,
                                                                                                                                             2));}
                                                if (session.getAttribute(Constants.userLang) ==
                                                    null)
                                                { session.setAttribute(Constants.userLang,
                                                                         user.getRoleList().get(i).getIdString().get(0).toString().substring(0,
                                                                                                                                             2)); }

                                                log.info(accessDC.getDisplayRecord() +
                                                         this.getClass() +
                                                         " partner lang in session setted as " +
                                                         session.getAttribute("partnerLang"));


                                                int pIdStart =
                                                    user.getRoleList().get(i).getIdString().get(partid).indexOf("PP");

                                                String pid =
                                                    user.getRoleList().get(i).getIdString().get(partid).substring(pIdStart +
                                                                                                                  2,
                                                                                                                  pIdStart +
                                                                                                                  10);


                                                part.setPartnerValue(partnerId);
                                                part.setPartnerName(getPartnerName(partnerId));
                                                if (session.getAttribute("partnerLang") !=
                                                    null) {
                                                    part.setCountry((String)session.getAttribute("partnerLang"));
                                                } else {
                                                    log.info(accessDC.getDisplayRecord() +
                                                             this.getClass() +
                                                             " partner language null in session");
                                                    part.setCountry(null);
                                                }
                                                part.setCompanyOverview(false);


                                                do {
                                                    if (idlist != 0) {


                                                        pIdStart =
                                                                user.getRoleList().get(i).getIdString().get(idlist).indexOf("PP");

                                                        pid =
user.getRoleList().get(i).getIdString().get(idlist).substring(pIdStart + 2,
                                                              pIdStart + 10);

                                                    }
                                                    if (partnerId != null &&
                                                        partnerId.equals(pid)) {


                                                        executeEmp = false;

                                                        if (user.getRoleList().get(i).getIdString().get(idlist).contains("AC") &&
                                                            partnerId.equals(user.getRoleList().get(i).getIdString().get(idlist).substring(pIdStart +
                                                                                                                                           2,
                                                                                                                                           pIdStart +
                                                                                                                                           10))) {


                                                            log.info(accessDC.getDisplayRecord() +
                                                                     this.getClass() +
                                                                     " Account B2B Manager");

                                                            acc =
new AccountInfo();
                                                            cardgrouplist =
                                                                    new ArrayList<CardGroupInfo>();
                                                            int accIdStart =
                                                                user.getRoleList().get(i).getIdString().get(idlist).indexOf("AC");

                                                            String accid =
                                                                user.getRoleList().get(i).getIdString().get(idlist).substring(accIdStart +
                                                                                                                              2,
                                                                                                                              accIdStart +
                                                                                                                              12);
                                                            log.info(accessDC.getDisplayRecord() +
                                                                     this.getClass() +
                                                                     " account id " +
                                                                     accid);
                                                            acc.setAccountNumber(accid);
                                                            acc.setAccountOverview(true);
                                                            log.info(accessDC.getDisplayRecord() +
                                                                     this.getClass() +
                                                                     " Account overview should be visible");

                                                            AccountInfo accountCheck =
                                                                new AccountInfo();
                                                            addflagaccount =
                                                                    false;
                                                            for (int listsize =
                                                                 0;
                                                                 listsize < accountlist.size();
                                                                 listsize++) {
                                                                log.info(accessDC.getDisplayRecord() +
                                                                         this.getClass() +
                                                                         " account id value in account list " +
                                                                         accountlist.get(listsize).getAccountNumber());
                                                                accountCheck =
                                                                        accountlist.get(listsize);

                                                                if (accountCheck.getAccountNumber().equalsIgnoreCase(acc.getAccountNumber())) {
                                                                    log.info(accessDC.getDisplayRecord() +
                                                                             this.getClass() +
                                                                             "Account ID already exists in partner object");
                                                                
                                                                    if (accountCheck.isAccountOverview()) {
                                                                        addflagaccount =
                                                                                true;
                                                                        break;
                                                                    }
                                                                }
                                                            }


                                                            if (!addflagaccount) {
                                                                //This means account only does not exists or Account exists but account overview is false, so in both the cases we have to create new cardgrplist new cardlist .....


                                                                String accountID =
                                                                    acc.getAccountNumber();
                                                                bindings =
                                                                        (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry().get("pageTemplateBinding");
                                                                if (bindings ==
                                                                    null ||
                                                                    bindings.findIteratorBinding("PrtCardgroupVO1Iterator") ==
                                                                    null) {
                                                                    bindings =
                                                                            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                                                                }

                                                                DCIteratorBinding iter2;
                                                                if (bindings !=
                                                                    null) {
                                                                    iter2 =
                                                                            bindings.findIteratorBinding("PrtCardgroupVO1Iterator");

                                                                } else {
                                                                    log.info(accessDC.getDisplayRecord() +
                                                                             this.getClass() +
                                                                             " Error : PrtCardgroupVO1Iterator is null");
                                                                    iter2 =
                                                                            null;
                                                                }

                                                                if (iter2 !=
                                                                    null) {
                                                                    ViewObject vo2 =
                                                                        iter2.getViewObject();


                                                                    if ("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub".equalsIgnoreCase(vo2.getWhereClause())) {

                                                                        vo2.removeNamedWhereClauseParam("cgid");
                                                                        vo2.removeNamedWhereClauseParam("cc");
                                                                        vo2.removeNamedWhereClauseParam("cgmain");
                                                                        vo2.removeNamedWhereClauseParam("cgsub");
                                                                        vo2.setWhereClause("");
                                                                        vo2.executeQuery();

                                                                    }

                                                                    vo2.setWhereClause("ACCOUNT_ID =: accid AND COUNTRY_CODE =: cc");
                                                                    vo2.defineNamedWhereClauseParam("accid",
                                                                                                    accountID,
                                                                                                    null);
                                                                    vo2.defineNamedWhereClauseParam("cc",
                                                                                                    (String)session.getAttribute(Constants.userLang),
                                                                                                    null);


                                                                    vo2.executeQuery();
                                                                    log.info(accessDC.getDisplayRecord() +
                                                                             this.getClass() +
                                                                             " row count from cardgroup vo" +
                                                                             vo2.getEstimatedRowCount());

                                                                    if (vo2.getEstimatedRowCount() !=
                                                                        0) {


                                                                        while (vo2.hasNext()) {


                                                                            cardgrp =
                                                                                    new CardGroupInfo();
                                                                            cardlist =
                                                                                    new ArrayList<CardInfo>();
                                                                            unblockedcardlist =
                                                                                    new ArrayList<CardInfo>();

                                                                            PrtCardgroupVORowImpl currRowcardgrp =
                                                                                (PrtCardgroupVORowImpl)vo2.next();

                                                                            if (currRowcardgrp !=
                                                                                null) {


                                                                                if (currRowcardgrp.getCardgroupSeq() !=
                                                                                    null) {
                                                                                    log.info(accessDC.getDisplayRecord() +
                                                                                             this.getClass() +
                                                                                             " Cardgroup id is " +
                                                                                             currRowcardgrp.getCardgroupSeq());
                                                                                    cardgrp.setCardGroupID((currRowcardgrp.getCardgroupMainType().toString().concat(currRowcardgrp.getCardgroupSubType().toString())).concat(currRowcardgrp.getCardgroupSeq().toString()));
                                                                                    cardgrp.setCardGroupMainType(currRowcardgrp.getCardgroupMainType().toString());
                                                                                    cardgrp.setCardGroupSeq(currRowcardgrp.getCardgroupSeq().toString());
                                                                                    cardgrp.setCardGroupSubType(currRowcardgrp.getCardgroupSubType().toString());

                                                                                    if (currRowcardgrp.getCardgroupDescription() !=
                                                                                        null) {
                                                                                        cardgrp.setCardGroupName(currRowcardgrp.getCardgroupDescription().toString());
                                                                                        cardgrp.setDisplayCardGroupIdName((currRowcardgrp.getCardgroupMainType().toString().concat(currRowcardgrp.getCardgroupSubType().toString())).concat(currRowcardgrp.getCardgroupSeq().toString()) +
                                                                                                                          "-" +
                                                                                                                          currRowcardgrp.getCardgroupDescription().toString());
                                                                                        log.info("------->" +
                                                                                                 currRowcardgrp.getCardgroupDescription().toString());
                                                                                    } else {
                                                                                        cardgrp.setCardGroupName(cardgrp.getCardGroupID());
                                                                                        cardgrp.setDisplayCardGroupIdName((currRowcardgrp.getCardgroupMainType().toString().concat(currRowcardgrp.getCardgroupSubType().toString())).concat(currRowcardgrp.getCardgroupSeq().toString()));
                                                                                    }


                                                                                    cardgrp.setCardGroupOverview(true);

                                                                                }

                                                                                addflagcardgroup =
                                                                                        false;
                                                                                for (int k =
                                                                                     0;
                                                                                     k <
                                                                                     cardgrouplist.size();
                                                                                     k++) {
                                                                                    log.info(accessDC.getDisplayRecord() +
                                                                                             this.getClass() +
                                                                                             " cardgroup id value in cardgroup list " +
                                                                                             cardgrouplist.get(k).getCardGroupID());
                                                                                    log.info(accessDC.getDisplayRecord() +
                                                                                             this.getClass() +
                                                                                             " New cardgroup id value to compare" +
                                                                                             cardgrp.getCardGroupID());


                                                                                    if (cardgrouplist.get(k).getCardGroupID().equalsIgnoreCase(cardgrp.getCardGroupID())) {
                                                                                        log.info(accessDC.getDisplayRecord() +
                                                                                                 this.getClass() +
                                                                                                 " cardgroup id already exists in cardgroup list");
                                                                                        addflagcardgroup =
                                                                                                true;
                                                                                        break;
                                                                                    }
                                                                                }


                                                                            }
                                                                            if (!addflagcardgroup)
                                                                            //add cardlist in cardgroup

                                                                            {

                                                                                String cardgroupMainType =
                                                                                    cardgrp.getCardGroupMainType().toString();
                                                                                String cardgroupSubType =
                                                                                    cardgrp.getCardGroupSubType().toString();
                                                                                String cardgroupSeq =
                                                                                    cardgrp.getCardGroupSeq().toString();

                                                                                bindings =
                                                                                        (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry().get("pageTemplateBinding");
                                                                                if (bindings ==
                                                                                    null ||
                                                                                    bindings.findIteratorBinding("PrtCardVO1Iterator") ==
                                                                                    null) {
                                                                                    bindings =
                                                                                            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                                                                                }
                                                                                DCIteratorBinding iter3;
                                                                                if (bindings !=
                                                                                    null) {
                                                                                    iter3 =
                                                                                            bindings.findIteratorBinding("PrtCardVO1Iterator");

                                                                                } else {
                                                                                    log.info(accessDC.getDisplayRecord() +
                                                                                             this.getClass() +
                                                                                             " Error : PrtCardVO1Iterator is null");
                                                                                    iter3 =
                                                                                            null;
                                                                                }

                                                                                if (iter3 !=
                                                                                    null) {
                                                                                    ViewObject vo3 =
                                                                                        iter3.getViewObject();

                                                                                    vo3 =
iter3.getViewObject();

                                                                                    if ("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub".equalsIgnoreCase(vo3.getWhereClause())) {

                                                                                        vo3.removeNamedWhereClauseParam("cgid");
                                                                                        vo3.removeNamedWhereClauseParam("cc");
                                                                                        vo3.removeNamedWhereClauseParam("cgmain");
                                                                                        vo3.removeNamedWhereClauseParam("cgsub");
                                                                                        vo3.setWhereClause("");
                                                                                        vo3.executeQuery();
                                                                                    }

                                                                                    if ("PRT_CARD_PK =: cardid AND COUNTRY_CODE =: cc".equalsIgnoreCase(vo3.getWhereClause())) {

                                                                                        vo3.removeNamedWhereClauseParam("cardid");
                                                                                        vo3.removeNamedWhereClauseParam("cc");
                                                                                        vo3.setWhereClause("");
                                                                                        vo3.executeQuery();
                                                                                    }

                                                                                    vo3.setWhereClause("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub");

                                                                                    vo3.defineNamedWhereClauseParam("cgid",
                                                                                                                    cardgroupSeq,
                                                                                                                    null);
                                                                                    vo3.defineNamedWhereClauseParam("cc",
                                                                                                                    (String)session.getAttribute(Constants.userLang),
                                                                                                                    null);
                                                                                    vo3.defineNamedWhereClauseParam("cgmain",
                                                                                                                    cardgroupMainType,
                                                                                                                    null);
                                                                                    vo3.defineNamedWhereClauseParam("cgsub",
                                                                                                                    cardgroupSubType,
                                                                                                                    null);


                                                                                    vo3.executeQuery();
                                                                                    log.info(accessDC.getDisplayRecord() +
                                                                                             this.getClass() +
                                                                                             " row count from cardgroup vo" +
                                                                                             vo3.getEstimatedRowCount());

                                                                                    if (vo3.getEstimatedRowCount() !=
                                                                                        0) {


                                                                                        while (vo3.hasNext()) {


                                                                                            card =
new CardInfo();
                                                                                            PrtCardVORowImpl currRowcard =
                                                                                                (PrtCardVORowImpl)vo3.next();

                                                                                            if (currRowcard !=
                                                                                                null) {


                                                                                                if (currRowcard.getPrtCardPk() !=
                                                                                                    null) {

                                                                                                    card.setCardID(currRowcard.getPrtCardPk().toString());


                                                                                                }

                                                                                                if (currRowcard.getBlockAction() !=
                                                                                                    null &&
                                                                                                    currRowcard.getBlockAction().equalsIgnoreCase("2")) {
                                                                                                    log.info("card is not expired");

                                                                                                } else if (currRowcard.getCardExpiry() ==
                                                                                                           null ||
                                                                                                           currRowcard.getCardExpiry().after(new Date())) {
                                                                                                    unblockedcardlist.add(card);
                                                                                                }

                                                                                                if (currRowcard.getCardEmbossNum() !=
                                                                                                    null) {
                                                                                                    card.setExternalCardID(currRowcard.getCardEmbossNum().toString());
                                                                                                }

                                                                                                if (currRowcard.getCardTextline2() !=
                                                                                                    null) {
                                                                                                    card.setCardTextline2(currRowcard.getCardTextline2().toString());
                                                                                                }

                                                                                                addflagcard =
                                                                                                        false;
                                                                                                for (int k =
                                                                                                     0;
                                                                                                     k <
                                                                                                     cardlist.size();
                                                                                                     k++) {
                                                                                                    log.info(accessDC.getDisplayRecord() +
                                                                                                             this.getClass() +
                                                                                                             " cardgroup id value in cardgroup list " +
                                                                                                             cardlist.get(k).getCardID());
                                                                                                    log.info(accessDC.getDisplayRecord() +
                                                                                                             this.getClass() +
                                                                                                             " New cardgroup id value to compare" +
                                                                                                             card.getCardID());


                                                                                                    if (cardlist.get(k).getCardID().equalsIgnoreCase(card.getCardID())) {
                                                                                                        log.info(accessDC.getDisplayRecord() +
                                                                                                                 this.getClass() +
                                                                                                                 " card id already exists in card list");
                                                                                                        addflagcard =
                                                                                                                true;
                                                                                                        break;
                                                                                                    }
                                                                                                }


                                                                                            }
                                                                                            if (!addflagcard)
                                                                                            {  cardlist.add(card);}


                                                                                        }

                                                                                        cardgrp.setCard(cardlist);
                                                                                        cardgrp.setUnblockedCardList(unblockedcardlist);
                                                                                        cardgrouplist.add(cardgrp);

                                                                                    }

                                                                                }


                                                                            }


                                                                        }
                                                                        acc.setCardGroup(cardgrouplist);
                                                                        for (int z =
                                                                             0;
                                                                             z <
                                                                             accountlist.size();
                                                                             z++){
                                                                            if (accountlist.get(z).getAccountNumber().equals(acc.getAccountNumber())) {

                                                                                accountlist.set(z,
                                                                                                acc);

                                                                            } 
                                                                            }
                                                                        if (!accountlist.contains(acc))
                                                                        { accountlist.add(acc);}


                                                                    }

                                                                }


                                                            }
                                                            part.setAccountList(accountlist);

                                                        } else if (user.getRoleList().get(i).getIdString().get(idlist).contains("CG") &&
                                                                   partnerId.equals(user.getRoleList().get(i).getIdString().get(idlist).substring(pIdStart +
                                                                                                                                                  2,
                                                                                                                                                  pIdStart +
                                                                                                                                                  10))) {
                                                            AccountInfo accountCheck =
                                                                new AccountInfo();


                                                            int cgIdStart =
                                                                user.getRoleList().get(i).getIdString().get(idlist).indexOf("CG");

                                                            String cardGroupID =
                                                                user.getRoleList().get(i).getIdString().get(idlist).substring(cgIdStart +
                                                                                                                              2);
                                                            log.info(accessDC.getDisplayRecord() +
                                                                     this.getClass() +
                                                                     " CardGroupId is " +
                                                                     cardGroupID);

                                                            log.info(accessDC.getDisplayRecord() +
                                                                     this.getClass() +
                                                                     " Card Group B2B Manager");

                                                            acc =
new AccountInfo();

                                                            cardgrp =
                                                                    new CardGroupInfo();
                                                            cardlist =
                                                                    new ArrayList<CardInfo>();
                                                            unblockedcardlist =
                                                                    new ArrayList<CardInfo>();


                                                            String cardgroupMainType =
                                                                user.getRoleList().get(i).getIdString().get(idlist).substring(cgIdStart +
                                                                                                                              2,
                                                                                                                              cgIdStart +
                                                                                                                              5);
                                                            String cardgroupSubType =
                                                                user.getRoleList().get(i).getIdString().get(idlist).substring(cgIdStart +
                                                                                                                              5,
                                                                                                                              cgIdStart +
                                                                                                                              8);
                                                            String cardgroupSeq =
                                                                user.getRoleList().get(i).getIdString().get(idlist).substring(cgIdStart +
                                                                                                                              8);
                                                            cardgrp.setCardGroupMainType(cardgroupMainType);
                                                            cardgrp.setCardGroupSubType(cardgroupSubType);
                                                            cardgrp.setCardGroupSeq(cardgroupSeq);
                                                            cardgrp.setCardGroupID((cardgroupMainType.concat(cardgroupSubType).concat(cardgroupSeq)));
                                                            cardgrp.setCardGroupName(getcardGroupName(cardgroupMainType,
                                                                                                      cardgroupSubType,
                                                                                                      cardgroupSeq));
                                                            if (cardgrp.getCardGroupName() !=
                                                                null) {
                                                                cardgrp.setDisplayCardGroupIdName((cardgroupMainType.concat(cardgroupSubType)).concat(cardgroupSeq) +
                                                                                                  "-" +
                                                                                                  cardgrp.getCardGroupName());
                                                            } else

                                                            { cardgrp.setDisplayCardGroupIdName((cardgroupMainType.concat(cardgroupSubType).concat(cardgroupSeq))); }


                                                            cardgrp.setCardGroupOverview(true);


                                                            log.info(accessDC.getDisplayRecord() +
                                                                     this.getClass() +
                                                                     " cardgroup seq " +
                                                                     cardgroupSeq);
                                                            log.info(accessDC.getDisplayRecord() +
                                                                     this.getClass() +
                                                                     " Cardgroup Maintype " +
                                                                     cardgroupMainType);
                                                            log.info(accessDC.getDisplayRecord() +
                                                                     this.getClass() +
                                                                     " CardgroupSubtype " +
                                                                     cardgroupSubType);

                                                            //Execute CardgroupVO to fetch corresponding Account number


                                                            bindings =
                                                                    (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry().get("pageTemplateBinding");
                                                            if (bindings ==
                                                                null ||
                                                                bindings.findIteratorBinding("PrtCardVO1Iterator") ==
                                                                null) {
                                                                bindings =
                                                                        (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                                                            }
                                                            DCIteratorBinding iter3;
                                                            if (bindings !=
                                                                null) {
                                                                iter3 =
                                                                        bindings.findIteratorBinding("PrtCardVO1Iterator");

                                                            } else {
                                                                log.info(accessDC.getDisplayRecord() +
                                                                         this.getClass() +
                                                                         "Error : PrtCardVO1Iterator is null");
                                                                iter3 = null;
                                                            }


                                                            if (iter3 !=
                                                                null) {
                                                                ViewObject vo3 =
                                                                    iter3.getViewObject();

                                                                vo3 =
iter3.getViewObject();


                                                                if ("PRT_CARD_PK =: cardid AND COUNTRY_CODE =: cc".equalsIgnoreCase(vo3.getWhereClause())) {

                                                                    vo3.removeNamedWhereClauseParam("cardid");
                                                                    vo3.removeNamedWhereClauseParam("cc");
                                                                    vo3.setWhereClause("");
                                                                    vo3.executeQuery();
                                                                }


                                                                vo3.setWhereClause("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub");

                                                                vo3.defineNamedWhereClauseParam("cgid",
                                                                                                cardgroupSeq,
                                                                                                null);
                                                                vo3.defineNamedWhereClauseParam("cc",
                                                                                                (String)session.getAttribute(Constants.userLang),
                                                                                                null);
                                                                vo3.defineNamedWhereClauseParam("cgmain",
                                                                                                cardgroupMainType,
                                                                                                null);
                                                                vo3.defineNamedWhereClauseParam("cgsub",
                                                                                                cardgroupSubType,
                                                                                                null);


                                                                vo3.executeQuery();
                                                                log.info(accessDC.getDisplayRecord() +
                                                                         this.getClass() +
                                                                         "row count from cardgroup vo" +
                                                                         vo3.getEstimatedRowCount());

                                                                if (vo3.getEstimatedRowCount() !=
                                                                    0) {


                                                                    while (vo3.hasNext()) {


                                                                        card =
new CardInfo();
                                                                        PrtCardVORowImpl currRowcard =
                                                                            (PrtCardVORowImpl)vo3.next();

                                                                        if (currRowcard !=
                                                                            null) {


                                                                            if (currRowcard.getPrtCardPk() !=
                                                                                null) {

                                                                                card.setCardID(currRowcard.getPrtCardPk().toString());


                                                                            }

                                                                            if (currRowcard.getBlockAction() !=
                                                                                null &&
                                                                                currRowcard.getBlockAction().equalsIgnoreCase("2")) {
                                                                                log.info("card is not expired");

                                                                            } else if (currRowcard.getCardExpiry() ==
                                                                                       null ||
                                                                                       currRowcard.getCardExpiry().after(new Date())) {
                                                                                unblockedcardlist.add(card);
                                                                            }
                                                                            if (currRowcard.getCardEmbossNum() !=
                                                                                null) {
                                                                                card.setExternalCardID(currRowcard.getCardEmbossNum().toString());
                                                                                card.setDisplayCardNumber("XXXX" +
                                                                                                          currRowcard.getCardEmbossNum().toString().substring(currRowcard.getCardEmbossNum().toString().length() -
                                                                                                                                                              4,
                                                                                                                                                              currRowcard.getCardEmbossNum().toString().length()));
                                                                            }

                                                                            if (currRowcard.getCardTextline2() !=
                                                                                null) {
                                                                                card.setCardTextline2(currRowcard.getCardTextline2().toString());
                                                                            }

                                                                            if (currRowcard.getBlockAction() !=
                                                                                null)
                                                                            {  card.setBlockAction(currRowcard.getBlockAction().toString()); }

                                                                            if (currRowcard.getBlockAction() !=
                                                                                null &&
                                                                                currRowcard.getBlockAction().equalsIgnoreCase("1") ||
                                                                                currRowcard.getBlockAction().equalsIgnoreCase("0") &&
                                                                                currRowcard.getCardExpiry() !=
                                                                                null &&
                                                                                currRowcard.getCardExpiry().before(new Date())) {

                                                                                card.setBlockAction("2");


                                                                            }

                                                                            if (currRowcard.getAccountId() !=
                                                                                null) {

                                                                                acc.setAccountNumber(currRowcard.getAccountId().toString());
                                                                            }

                                                                            cardlist.add(card);
                                                                        }
                                                                    }

                                                                }
                                                                accountCheck =
                                                                        new AccountInfo();
                                                                createCardGroupList =
                                                                        true;
                                                                addflagaccount =
                                                                        false;
                                                                for (int listsize =
                                                                     0;
                                                                     listsize <
                                                                     accountlist.size();
                                                                     listsize++) {
                                                                    log.info(accessDC.getDisplayRecord() +
                                                                             this.getClass() +
                                                                             " account id value in account list " +
                                                                             accountlist.get(listsize).getAccountNumber());
                                                                    accountCheck =
                                                                            accountlist.get(listsize);

                                                                    if (accountCheck.getAccountNumber().equalsIgnoreCase(acc.getAccountNumber())) {
                                                                        log.info(accessDC.getDisplayRecord() +
                                                                                 this.getClass() +
                                                                                 " Account ID already exists in partner object");
                                                                        createCardGroupList =
                                                                                false;

                                                                        if (accountCheck.isAccountOverview()) {
                                                                            addflagaccount =
                                                                                    true;
                                                                            break;
                                                                        }
                                                                        break;
                                                                    }
                                                                }
                                                                if (!addflagaccount &&
                                                                    accountlist.size() >
                                                                    0) {
                                                                    if (createCardGroupList) {
                                                                        cardgrp.setCard(cardlist);
                                                                        log.info("unblocked list " +
                                                                                 unblockedcardlist.size());
                                                                        cardgrp.setUnblockedCardList(unblockedcardlist);
                                                                        cardgrp.setCardGroupOverview(true);
                                                                        cardgrouplist =
                                                                                new ArrayList<CardGroupInfo>();
                                                                        cardgrouplist.add(cardgrp);
                                                                        acc.setCardGroup(cardgrouplist);
                                                                        acc.setAccountOverview(false);
                                                                        accountlist.add(acc);
                                                                        part.setAccountList(accountlist);
                                                                        part.setCompanyOverview(false);
                                                                    } else {
                                                                        //Account exists so take cardgroup list from that account object & before adding just check we have to add or update
                                                                        //the existing one
                                                                        cardgrouplist =
                                                                                accountCheck.getCardGroup();
                                                                        cardgrp.setCard(cardlist);
                                                                        cardgrp.setCardGroupOverview(true);

                                                                        addflagcardgroup =
                                                                                false;
                                                                        for (int k =
                                                                             0;
                                                                             k <
                                                                             cardgrouplist.size();
                                                                             k++) {
                                                                            log.info(accessDC.getDisplayRecord() +
                                                                                     this.getClass() +
                                                                                     " cardgroup id value in cardgroup list " +
                                                                                     cardgrouplist.get(k).getCardGroupID());
                                                                            log.info(accessDC.getDisplayRecord() +
                                                                                     this.getClass() +
                                                                                     " New cardgroup id value to compare" +
                                                                                     cardgrp.getCardGroupID());

                                                                            if (cardgrouplist.get(k).getCardGroupID().equalsIgnoreCase(cardgrp.getCardGroupID())) {
                                                                                log.info(accessDC.getDisplayRecord() +
                                                                                         this.getClass() +
                                                                                         " cardgroup id already exists in cardgroup list");
                                                                                addflagcardgroup =
                                                                                        true;
                                                                                break;
                                                                            }
                                                                        }
                                                                        if (addflagcardgroup) {

                                                                            //Replace the old cardgroup with new one
                                                                            for (int z =
                                                                                 0;
                                                                                 z <
                                                                                 cardgrouplist.size();
                                                                                 z++) {
                                                                                if (cardgrouplist.get(z).getCardGroupMainType().equals(cardgrp.getCardGroupMainType()) &&
                                                                                    cardgrouplist.get(z).getCardGroupSubType().equals(cardgrp.getCardGroupSubType()) &&
                                                                                    cardgrouplist.get(z).getCardGroupSeq().equals(cardgrp.getCardGroupSeq())) {

                                                                                    cardgrouplist.set(z,
                                                                                                      cardgrp);

                                                                                }
                                                                            }

                                                                            //Replace the new account also with old one by adding newly created cardgrp obj to the cardgrp list
                                                                            for (int z =
                                                                                 0;
                                                                                 z <
                                                                                 accountlist.size();
                                                                                 z++)
                                                                            {
                                                                                if (accountlist.get(z).getAccountNumber().equals(acc.getAccountNumber())) {

                                                                                    acc.setCardGroup(cardgrouplist);
                                                                                    accountlist.set(z,
                                                                                                    acc);
                                                                                    part.setAccountList(accountlist);
                                                                                    break;

                                                                                }
                                                                        }

                                                                        } else {
                                                                            //Add cardgrp to the cardgrp list and then replacae account

                                                                            cardgrouplist.add(cardgrp);
                                                                            acc.setCardGroup(cardgrouplist);
                                                                            //Replace the new account also with old one by adding newly created cardgrp obj to the cardgrp list
                                                                            for (int z =
                                                                                 0;
                                                                                 z <
                                                                                 accountlist.size();
                                                                                 z++)
                                                                            {
                                                                                if (accountlist.get(z).getAccountNumber().equals(acc.getAccountNumber())) {

                                                                                    accountlist.set(z,
                                                                                                    acc);
                                                                                    part.setAccountList(accountlist);
                                                                                    break;

                                                                                }
                                                                        }
                                                                            
                                                                        }
                                                                    }
                                                                }
                                                                if (accountlist.size() ==
                                                                    0) {
                                                                    cardgrp.setCard(cardlist);
                                                                    log.info("unblocked list " +
                                                                             unblockedcardlist.size());
                                                                    cardgrp.setUnblockedCardList(unblockedcardlist);
                                                                    cardgrp.setCardGroupOverview(true);
                                                                    cardgrouplist =
                                                                            new ArrayList<CardGroupInfo>();
                                                                    cardgrouplist.add(cardgrp);
                                                                    acc.setCardGroup(cardgrouplist);
                                                                    acc.setAccountOverview(false);
                                                                    accountlist.add(acc);
                                                                    part.setAccountList(accountlist);
                                                                    part.setCompanyOverview(false);

                                                                }

                                                            }
                                                        }
                                                    }
                                                    idlist++;
                                                } while (idlist <
                                                         user.getRoleList().get(i).getIdString().size());
                                            }

                                        } else if (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_EMP)) {
                                            log.info(accessDC.getDisplayRecord() +
                                                     this.getClass() +
                                                     " CARD AMIN as user role =======>" +
                                                     user.getRoleList().get(i).getRoleName());


                                            int idlist = 0;

                                            log.info(accessDC.getDisplayRecord() +
                                                     this.getClass() +
                                                     "TEMP ------------->" +
                                                     user.getRoleList().get(i).getIdString().size());
                                            DCBindingContainer bindings;
                                            do {
                                                executeEmp = false;
                                                log.info(accessDC.getDisplayRecord() +
                                                         this.getClass() +
                                                         " User is B2B Employee");
                                                createCardGroupList = false;
                                                acc = new AccountInfo();
                                                cardgrp = new CardGroupInfo();

                                                createCardGroupList = false;
                                                createCardList = true;


                                                int ccIdStart =
                                                    user.getRoleList().get(i).getIdString().get(idlist).indexOf("CC");

                                                String cardId =
                                                    user.getRoleList().get(i).getIdString().get(idlist).substring(ccIdStart +
                                                                                                                  2);

                                                int pIdStart =
                                                    user.getRoleList().get(i).getIdString().get(idlist).indexOf("PP");
                                                String pid =
                                                    user.getRoleList().get(i).getIdString().get(idlist).substring(pIdStart +
                                                                                                                  2,
                                                                                                                  pIdStart +
                                                                                                                  10);

                                                if (partnerId != null &&
                                                    partnerId.equals(pid)) {


                                                    bindings =
                                                            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry().get("pageTemplateBinding");
                                                    if (bindings == null ||
                                                        bindings.findIteratorBinding("PrtCardVO1Iterator") ==
                                                        null) {
                                                        bindings =
                                                                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                                                    }
                                                    DCIteratorBinding iter3;
                                                    if (bindings != null) {
                                                        iter3 =
                                                                bindings.findIteratorBinding("PrtCardVO1Iterator");

                                                    } else {
                                                        log.info(accessDC.getDisplayRecord() +
                                                                 this.getClass() +
                                                                 " Error : PrtCardVO1Iterator is null");
                                                        iter3 = null;
                                                    }


                                                    if (iter3 != null) {
                                                        ViewObject vo3 =
                                                            iter3.getViewObject();

                                                        if ("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub".equalsIgnoreCase(vo3.getWhereClause())) {

                                                            vo3.removeNamedWhereClauseParam("cgid");
                                                            vo3.removeNamedWhereClauseParam("cc");
                                                            vo3.removeNamedWhereClauseParam("cgmain");
                                                            vo3.removeNamedWhereClauseParam("cgsub");
                                                            vo3.setWhereClause("");
                                                            vo3.executeQuery();
                                                        }

                                                        vo3.setWhereClause("PRT_CARD_PK =: cardid AND COUNTRY_CODE =: cc");
                                                        vo3.defineNamedWhereClauseParam("cardid",
                                                                                        cardId,
                                                                                        null);
                                                        vo3.defineNamedWhereClauseParam("cc",
                                                                                        (String)session.getAttribute(Constants.userLang),
                                                                                        null);


                                                        vo3.executeQuery();
                                                        log.info(accessDC.getDisplayRecord() +
                                                                 this.getClass() +
                                                                 " row count from cardgroup vo" +
                                                                 vo3.getEstimatedRowCount());
                                                        if (vo3.getEstimatedRowCount() !=
                                                            0) {

                                                            while (vo3.hasNext()) {
                                                                card =
new CardInfo();
                                                                PrtCardVORowImpl currRowcard =
                                                                    (PrtCardVORowImpl)vo3.next();
                                                                if (currRowcard !=
                                                                    null) {


                                                                    if (currRowcard.getPrtCardPk() !=
                                                                        null) {

                                                                        card.setCardID(currRowcard.getPrtCardPk().toString());

                                                                        if (currRowcard.getCardEmbossNum() !=
                                                                            null) {
                                                                            card.setExternalCardID(currRowcard.getCardEmbossNum().toString());
                                                                        }

                                                                        if (currRowcard.getCardTextline2() !=
                                                                            null) {
                                                                            card.setCardTextline2(currRowcard.getCardTextline2().toString());
                                                                        }

                                                                    }
                                                                    if (currRowcard.getCardgroupMainType() !=
                                                                        null &&
                                                                        currRowcard.getCardgroupSubType() !=
                                                                        null &&
                                                                        currRowcard.getCardgroupSeq() !=
                                                                        null &&
                                                                        currRowcard.getAccountId() !=
                                                                        null &&
                                                                        currRowcard.getPartnerId() !=
                                                                        null &&
                                                                        currRowcard.getAccountId() !=
                                                                        null &&
                                                                        currRowcard.getPartnerId() !=
                                                                        null) {
                                                                        cardgrp.setCardGroupMainType((currRowcard.getCardgroupMainType().toString()));
                                                                        cardgrp.setCardGroupSubType((currRowcard.getCardgroupSubType().toString()));
                                                                        cardgrp.setCardGroupSeq(currRowcard.getCardgroupSeq().toString());
                                                                        cardgrp.setCardGroupID((currRowcard.getCardgroupMainType().toString().concat(currRowcard.getCardgroupSubType().toString())).concat(currRowcard.getCardgroupSeq().toString()));
                                                                        cardgrp.setCardGroupName(getcardGroupName(currRowcard.getCardgroupMainType().toString(),
                                                                                                                  currRowcard.getCardgroupSubType().toString(),
                                                                                                                  currRowcard.getCardgroupSeq().toString()));

                                                                        if (cardgrp.getCardGroupName() !=
                                                                            null)
                                                                        { cardgrp.setDisplayCardGroupIdName((currRowcard.getCardgroupMainType().toString().concat((currRowcard.getCardgroupSubType().toString()))).concat(currRowcard.getCardgroupSeq().toString()) +
                                                                                                              "-" +
                                                                                                              cardgrp.getCardGroupName()); }
                                                                        else
                                                                        { cardgrp.setDisplayCardGroupIdName((currRowcard.getCardgroupMainType().toString().concat((currRowcard.getCardgroupSubType().toString()))).concat(currRowcard.getCardgroupSeq().toString()));
                                                                        }


                                                                        acc.setAccountNumber(currRowcard.getAccountId().toString());
                                                                        part.setPartnerValue(currRowcard.getPartnerId().toString());
                                                                        part.setPartnerName(getPartnerName(currRowcard.getPartnerId().toString()));
                                                                        if (session.getAttribute("partnerLang") !=
                                                                            null) {
                                                                            part.setCountry((String)session.getAttribute("partnerLang"));
                                                                        } else {
                                                                            log.info(accessDC.getDisplayRecord() +
                                                                                     this.getClass() +
                                                                                     " partner language null in session");
                                                                            part.setCountry(null);
                                                                        }


                                                                    }
                                                                }
                                                            }
                                                            accountCheck =
                                                                    new AccountInfo();
                                                            cardGrpCheck =
                                                                    new CardGroupInfo();

                                                            accountOverView =
                                                                    false;
                                                            cardGroupOverview =
                                                                    false;
                                                            addflagaccount =
                                                                    true;
                                                            for (int listsize =
                                                                 0;
                                                                 listsize < accountlist.size();
                                                                 listsize++) {

                                                                log.info(accessDC.getDisplayRecord() +
                                                                         this.getClass() +
                                                                         "account id value in account list " +
                                                                         accountlist.get(listsize).getAccountNumber());
                                                                accountCheck =
                                                                        accountlist.get(listsize);


                                                                if (accountCheck.getAccountNumber().equalsIgnoreCase(acc.getAccountNumber())) {
                                                                    createCardGroupList =
                                                                            false;
                                                                    cardgrouplist =
                                                                            accountCheck.getCardGroup();
                                                                    addflagaccount =
                                                                            false;

                                                                    if (accountCheck.isAccountOverview()) {
                                                                        accountOverView =
                                                                                true;
                                                                        break;
                                                                    }
                                                                     cardGrpListCheck =
                                                                            accountCheck.getCardGroup();

                                                                    for (int cardgrplistsize =
                                                                         0;
                                                                         cardgrplistsize <
                                                                          cardGrpListCheck.size();
                                                                         cardgrplistsize++) {
                                                                        log.info(accessDC.getDisplayRecord() +
                                                                                 this.getClass() +
                                                                                 " cardgrp id value in account list " +
                                                                                  cardGrpListCheck.get(cardgrplistsize).getCardGroupMainType());
                                                                        log.info(accessDC.getDisplayRecord() +
                                                                                 this.getClass() +
                                                                                 " cardgrp id value in account list " +
                                                                                  cardGrpListCheck.get(cardgrplistsize).getCardGroupSubType());
                                                                        log.info(accessDC.getDisplayRecord() +
                                                                                 this.getClass() +
                                                                                 " cardgrp id value in account list " +
                                                                                  cardGrpListCheck.get(cardgrplistsize).getCardGroupSeq());
                                                                        cardGrpCheck =
                                                                                 cardGrpListCheck.get(cardgrplistsize);
                                                                        if (cardGrpCheck.getCardGroupMainType().equalsIgnoreCase(cardgrp.getCardGroupMainType()) &&
                                                                            cardGrpCheck.getCardGroupSubType().equalsIgnoreCase(cardgrp.getCardGroupSubType()) &&
                                                                            cardGrpCheck.getCardGroupSeq().equalsIgnoreCase(cardgrp.getCardGroupSeq())) {
                                                                            createCardList =
                                                                                    false;

                                                                            if (cardGrpCheck.isCardGroupOverview()) {
                                                                                cardGroupOverview =
                                                                                        true;
                                                                                break;
                                                                            } else {
                                                                                //Take out the cardgrp obj from list and modify the same cardgrp object to add card in cardgrp's object list
                                                                                cardlist =
                                                                                        cardGrpCheck.getCard();
                                                                                if (!cardlist.contains(card))
                                                                                {  cardlist.add(card);}
                                                                                cardGrpCheck.setCard(cardlist);
                                                                                cardGrpCheck.setCardGroupOverview(false);
                                                                            }
                                                                            //Replace the old cardgrp obj in list with new one cardGrpCheck
                                                                            for (int z =
                                                                                 0;
                                                                                 z <
                                                                                 cardgrouplist.size();
                                                                                 z++) {
                                                                                if (cardgrouplist.get(z).getCardGroupMainType().equals(cardGrpCheck.getCardGroupMainType()) &&
                                                                                    cardgrouplist.get(z).getCardGroupMainType().equals(cardGrpCheck.getCardGroupMainType()) &&
                                                                                    cardgrouplist.get(z).getCardGroupMainType().equals(cardGrpCheck.getCardGroupMainType())) {

                                                                                    cardgrouplist.set(z,
                                                                                                      cardGrpCheck);
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    if (createCardList ==
                                                                        true) {
                                                                        //Cardgrp doesnot exists
                                                                        cardlist =
                                                                                new ArrayList<CardInfo>();
                                                                        cardlist.add(card);
                                                                        cardgrp.setCard(cardlist);
                                                                        cardgrp.setCardGroupOverview(false);

                                                                        cardgrouplist.add(cardgrp);
                                                                    }
                                                                    if (cardGroupOverview ==
                                                                        false) {
                                                                        acc.setCardGroup(cardgrouplist);
                                                                        //Replace the old account with the new one account obj
                                                                        for (int z =
                                                                             0;
                                                                             z <
                                                                             accountlist.size();
                                                                             z++){
                                                                            if (accountlist.get(z).getAccountNumber().equals(acc.getAccountNumber())) {

                                                                                accountlist.set(z,
                                                                                                acc);
                                                                            }
                                                                             }
                                                                        part.setAccountList(accountlist);
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                            if (addflagaccount ==
                                                                true) {
                                                                //that means either account list is 0 or account doesnot exists in accountlist

                                                                cardgrouplist =
                                                                        new ArrayList<CardGroupInfo>();
                                                                cardlist =
                                                                        new ArrayList<CardInfo>();
                                                                cardlist.add(card);
                                                                cardgrp.setCard(cardlist);
                                                                cardgrp.setCardGroupOverview(false);
                                                                cardgrouplist.add(cardgrp);
                                                                acc.setCardGroup(cardgrouplist);
                                                                acc.setAccountOverview(false);
                                                                accountlist.add(acc);
                                                                part.setAccountList(accountlist);

                                                            }
                                                        }
                                                    }
                                                }
                                                idlist++;
                                            } while (idlist <
                                                     user.getRoleList().get(i).getIdString().size());

                                        }
                                    }
                                    partnerListSession.add(part);
                                }
                            }
                            if (session != null &&
                                partnerListSession != null &&
                                partnerListSession.size() > 0) {
                                session.setAttribute("Partner_Object_List",
                                                     partnerListSession);


                                log.info(accessDC.getDisplayRecord() +
                                         this.getClass() +
                                         " partner list added");
                                log.info(accessDC.getDisplayRecord() +
                                         this.getClass() +
                                         " session not null");
                                session.setAttribute("executePartnerObjLogic",
                                                     "no");
                                log.info(accessDC.getDisplayRecord() +
                                         this.getClass() +
                                         " Session variable added");
                                log.info(accessDC.getDisplayRecord() +
                                         this.getClass() +
                                         " flag value changed now");

                            }
                        }
                    }

                }
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " Exit From Binding phase");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUserInfoInSession() throws Exception {
        SecurityContext securityContext =
            ADFContext.getCurrent().getSecurityContext();
        HttpServletRequest request =
            ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession session = request.getSession();


        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 ".setUserInfoInSession : " +
                 "MyPageListener.setUserInfoInSession: OPSS called for user: " +
                 securityContext.getUserName());
        List<User> userList = new ArrayList<User>();
        boolean success = false;
        UserClient userClient = new UserClient();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 3 && success == false; i++) {

            if (request.getServerName().contains("101") ||
                request.getServerName().contains("localhost") ||
                request.getServerName().contains("127.0")) {
               // userList.add(populateUser(Constants.ROLE_WCP_CARD_B2B_ADMIN));
                if(securityContext.getUserName().equalsIgnoreCase("weblogic")){
                    System.out.println("Inside setusersession infor for weblogic user");
                    userList = userClient.searchUserWithUserId("B2BMgr1@test.com", "DB");
                    
                }
            } else {
                //userList = userClient.searchUserWithUserId(securityContext.getUserName());
                userList = userClient.searchUserWithUserId(securityContext.getUserName(), "DB");
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            session.setAttribute("SESSION_USER_ERROR", "");
            if (securityContext.getUserName().equalsIgnoreCase("weblogic") ||
                securityContext.getUserName().equalsIgnoreCase("wcpadmin")) {
                success = true;
            } else {
                success = validateOPSSCall(userList, session);
            }
            if (!success) {
                return;
            }
        }

        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 ".setUserInfoInSession : " +
                 "securityContext.getUserName() = <" +
                 securityContext.getUserName() + ">");
        session.setAttribute(Constants.SESSION_USER_NAME,
                             securityContext.getUserName());


        if (userList != null)
        {   log.info(accessDC.getDisplayRecord() + this.getClass() +
                     ".setUserInfoInSession : " + "userlist size " +
                     userList.size()); }
        else
        { log.info(accessDC.getDisplayRecord() + this.getClass() +
                     "userlist size is null");}

        if (userList != null && !userList.isEmpty()) {
            //TODO : We are setting 1st user value from the List. Is that right ?
            //Do we get multiple results from OPSS call while searching using UserName ?
            //If we end up with multiple results for given username, we may always pick the 1st item in the object, which could be incorrect.
            setUser(userList.get(0));
            if (getUser() != null) {
                session.setAttribute(Constants.SESSION_USER_INFO, user);
                session.setAttribute(Constants.userLang,
                                     conv.getLangForWERCSURL(user.getLang().toString()));
                if (user.getCountry() != null) {
                    log.info("Taking lang paramter from user object inside setUserInfoSession mehtod ");
                    session.setAttribute(Constants.DISPLAY_PORTAL_LANG,
                                         user.getCountry().toString());
                    session.setAttribute(Constants.AUTHENTICATE_FLAG, "true");
                    // Added by siddharth
                }


                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         ".setUserInfoInSession : " + "user.getUserID = <" +
                         user.getUserID() + ">");
                session.setAttribute(Constants.SESSION_USER_DISPLAY_NAME,
                                     user.getFirstName());
                if (user.getRolelist() != null &&
                    !user.getRolelist().isEmpty()) {
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             "Checking user get role list is proper or not===================>");
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             "RoleList size      -----------" +
                             user.getRoleList().size());
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             "RoleName " +
                             user.getRoleList().get(0).getRoleName());


                }
            } else {
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         ".setUserInfoInSession : " +
                         "MyPageListener.setUserInfoInSession:  User in userList was Null");
            }
        } else {
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     ".setUserInfoInSession : " +
                     " MyPageListener.setUserInfoInSession: userList was either null or empty");
        }
    }

    boolean validateOPSSCall(List<User> liuser, HttpSession session) {
        if (liuser != null && liuser.size() > 0) {

            //TODO : Also kindly follow naming conventions for variables used across application.
            User us = liuser.get(0);
            if (us != null) {
                if (us.getEmailID() == null) {

                    session.setAttribute("SESSION_USER_ERROR", "ERROR_OPSS");
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             ".validateOPSSCall : " + "User EMAIL is NULL");
                    return false;
                }
                if (us.getRolelist() == null) {

                    session.setAttribute("SESSION_USER_ERROR", "ERROR_OPSS");
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             ".validateOPSSCall : " + "User ROLELIST is NULL");
                    return false;
                } else {
                    if (us.getRolelist().contains("WCP_Administrators") ||
                        us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_SFR) ||
                        us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_JET) ||
                        us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN) ||
                        us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) ||
                        us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_PETRO) ||
                        us.getRolelist().contains(Constants.ROLE_WCP_ICSR) ||
                        us.getRolelist().contains(Constants.ROLE_WCP_ISSM) ||
                        us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_EMP) ||
                        us.getRolelist().contains(Constants.ROLE_WCP_CARD_ADMIN) ||
                        us.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR) ||
                        us.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)) {
                        log.info(accessDC.getDisplayRecord() +
                                 this.getClass() + ".validateOPSSCall : " +
                                 "Proper roles found");
                    } else {

                        session.setAttribute("SESSION_USER_ERROR",
                                             "ERROR_NOROLE");
                        log.info(accessDC.getDisplayRecord() +
                                 this.getClass() + ".validateOPSSCall : " +
                                 "Roles found are:" + us.getRolelist() +
                                 " You are not authorized to enter Partner Portal.");
                        return false;
                    }
                }
            } else {

                session.setAttribute("SESSION_USER_ERROR", "ERROR_OPSS");
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         ".validateOPSSCall : " + "User OBJECT is NULL");
                return false;
            }
        } else {
            session.setAttribute("SESSION_USER_ERROR", "ERROR_OPSS");
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     ".validateOPSSCall : " + "user LIST is NULL");
            return false;
        }
        return true;
    }

//TODO : HITK - user obj passed for future use
    private void executeAdmin(User user, int partIndex) {
        //Since the user logged in has Admin role so not much handling / filtering is required at this point
        //1.Take partner id as request for Account View Object , 2.Execute Account VO, 3.Take the account id from Account View Object result, 4.Pass it to CardGroup View Object,
        //5.Execute CardGroup VO, 6.Take the CardGroup View id from CardGroup View Object result, 7.Pass it to Card View Object, 8.Execute Card VO
        //Prepare Cardlist from Card VO,
        //Prepare CardGroup list from CardGroup VO
        //Prepare Accountlist from Account VO

        ExternalContext ectx =
            FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = request.getSession(false);

        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 accessDC.getDisplayRecord() + this.getClass() +
                 " Entered in execute Admin function");
        part = new PartnerInfo();
        accountlist = new ArrayList<AccountInfo>();
        cardgrouplist = new ArrayList<CardGroupInfo>();


        String partnerid = partnerlist.get(partIndex).getPartnerValue();


        DCBindingContainer bindings;

        bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry().get("pageTemplateBinding");
        if (bindings == null ||
            bindings.findIteratorBinding("PrtAccountVO1Iterator") == null) {
            bindings =
                    (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        }


        DCIteratorBinding iter1;
        if (bindings != null &&
            bindings.findIteratorBinding("PrtAccountVO1Iterator") != null) {
            iter1 = bindings.findIteratorBinding("PrtAccountVO1Iterator");

            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     accessDC.getDisplayRecord() + this.getClass() +
                     " Partner id passed in Account VO is " + partnerid);

            part.setPartnerValue(partnerid);

            part.setPartnerName(getPartnerName(partnerid));

            if (session.getAttribute("partnerLang") != null) {
                part.setCountry((String)session.getAttribute("partnerLang"));
            } else {
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " partner language null in session");
                part.setCountry(null);
            }


            part.setCompanyOverview(true);

            ViewObject accountVO = iter1.getViewObject();
            accountVO.setWhereClause("PARTNER_ID =: pid");
            accountVO.defineNamedWhereClauseParam("pid", partnerid, null);
            accountVO.setNamedWhereClauseParam("countryCode",
                                               (String)session.getAttribute("partnerLang"));

            accountVO.executeQuery();


            if (accountVO.getEstimatedRowCount() != 0) {
                while (accountVO.hasNext()) {
                    acc = new AccountInfo();
                    cardgrouplist = new ArrayList<CardGroupInfo>();
                    PrtAccountVORowImpl currRow =
                        (PrtAccountVORowImpl)accountVO.next();
                    if (currRow != null) {
                        if (currRow.getAccountId() != null) {

                            acc.setAccountNumber(currRow.getAccountId().toString());
                            acc.setAccountOverview(true);
                        }

                        //Below logic is just to find out that account id is already present in accountlist
                        //TODO : HITK : This can be avoided since at DB level we have already put the constraint to avoid duplicatre records.....
                        addflagaccount = false;


                    }
                    String accountID = acc.getAccountNumber();

                    bindings =
                            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry().get("pageTemplateBinding");
                    if (bindings == null ||
                        bindings.findIteratorBinding("PrtCardgroupVO1Iterator") ==
                        null) {
                        bindings =
                                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                    }
                   


                    DCIteratorBinding iter2;
                    if (bindings != null) {
                        iter2 =
                                bindings.findIteratorBinding("PrtCardgroupVO1Iterator");

                    } else {
                        log.severe(accessDC.getDisplayRecord() +
                                 this.getClass() +
                                 "Error : PrtCardgroupVO1Iterator Bindings is null in my page listner for Admin role");
                        iter2 = null;
                    }
                    ViewObject cardGroupVO = iter2.getViewObject();


                    if ("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub".equalsIgnoreCase(cardGroupVO.getWhereClause())) {

                        cardGroupVO.removeNamedWhereClauseParam("cgid");
                        cardGroupVO.removeNamedWhereClauseParam("cc");
                        cardGroupVO.removeNamedWhereClauseParam("cgmain");
                        cardGroupVO.removeNamedWhereClauseParam("cgsub");
                        cardGroupVO.setWhereClause("");
                        cardGroupVO.executeQuery();
                    }
                    cardGroupVO = iter2.getViewObject();
                    cardGroupVO.setWhereClause("ACCOUNT_ID =: accid AND COUNTRY_CODE =: cc");

                    cardGroupVO.defineNamedWhereClauseParam("accid", accountID,
                                                            null);
                    cardGroupVO.defineNamedWhereClauseParam("cc",
                                                            (String)session.getAttribute(Constants.userLang),
                                                            null);

                    cardGroupVO.executeQuery();


                    if (cardGroupVO.getEstimatedRowCount() != 0) {
                        while (cardGroupVO.hasNext()) {
                            cardgrp = new CardGroupInfo();
                            cardlist = new ArrayList<CardInfo>();
                            unblockedcardlist = new ArrayList<CardInfo>();
                            activeCardList = new ArrayList<CardInfo>();
                            tempBlockCardList = new ArrayList<CardInfo>();
                            perBlockCardList = new ArrayList<CardInfo>();
                            perBlockAndActiveCardList =
                                    new ArrayList<CardInfo>();
                            perBlockAndTempBlockCardList =
                                    new ArrayList<CardInfo>();
                            PrtCardgroupVORowImpl currRowcardgrp =
                                (PrtCardgroupVORowImpl)cardGroupVO.next();

                            if (currRowcardgrp != null) {
                                if (currRowcardgrp.getCardgroupSeq() != null) {
                               

                                    cardgrp.setCardGroupID((currRowcardgrp.getCardgroupMainType().toString().concat(currRowcardgrp.getCardgroupSubType().toString())).concat(currRowcardgrp.getCardgroupSeq().toString()));
                                    cardgrp.setCardGroupMainType(currRowcardgrp.getCardgroupMainType().toString());
                                    cardgrp.setCardGroupSeq(currRowcardgrp.getCardgroupSeq().toString());
                                    cardgrp.setCardGroupSubType(currRowcardgrp.getCardgroupSubType().toString());
                                    if (currRowcardgrp.getCardgroupDescription() !=
                                        null) {
                                        cardgrp.setCardGroupName(currRowcardgrp.getCardgroupDescription().toString());
                                        cardgrp.setDisplayCardGroupIdName((currRowcardgrp.getCardgroupMainType().toString().concat(currRowcardgrp.getCardgroupSubType().toString())).concat(currRowcardgrp.getCardgroupSeq().toString()) +
                                                                          "-" +
                                                                          currRowcardgrp.getCardgroupDescription().toString());
                                    } else {
                                        cardgrp.setCardGroupName(cardgrp.getCardGroupID());
                                        cardgrp.setDisplayCardGroupIdName((currRowcardgrp.getCardgroupMainType().toString().concat(currRowcardgrp.getCardgroupSubType().toString())).concat(currRowcardgrp.getCardgroupSeq().toString()));
                                    }

                                    cardgrp.setCardGroupOverview(true);
                                }

                                addflagcardgroup = false;


                            }


                            String cardgroupMainType =
                                cardgrp.getCardGroupMainType().toString();
                            String cardgroupSubType =
                                cardgrp.getCardGroupSubType().toString();
                            String cardgroupSeq =
                                cardgrp.getCardGroupSeq().toString();


                            bindings =
                                    (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry().get("pageTemplateBinding");
                            if (bindings == null ||
                                bindings.findIteratorBinding("PrtCardVO1Iterator") ==
                                null) {
                                bindings =
                                        (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                            }


                            DCIteratorBinding iter3;
                            if (bindings != null) {
                                iter3 =
                                        bindings.findIteratorBinding("PrtCardVO1Iterator");

                            } else {
                                log.info(accessDC.getDisplayRecord() +
                                         this.getClass() +
                                         " PrtCardVO1Iterator Bindings is null in my page listner for Admin role");

                                iter3 = null;
                            }
                            ViewObject cardVO = iter3.getViewObject();

                            if ("PRT_CARD_PK =: cardid AND COUNTRY_CODE =: cc".equalsIgnoreCase(cardVO.getWhereClause())) {

                                cardVO.removeNamedWhereClauseParam("cardid");
                                cardVO.removeNamedWhereClauseParam("cc");
                                cardVO.setWhereClause("");
                                cardVO.executeQuery();
                            }

                            cardVO = iter3.getViewObject();
                            cardVO.setWhereClause("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub AND ACCOUNT_ID=: acid");


                            cardVO.defineNamedWhereClauseParam("cgid",
                                                               cardgroupSeq,
                                                               null);
                            cardVO.defineNamedWhereClauseParam("cc",
                                                               (String)session.getAttribute("partnerLang"),
                                                               null);


                            cardVO.defineNamedWhereClauseParam("cgmain",
                                                               cardgroupMainType,
                                                               null);
                            cardVO.defineNamedWhereClauseParam("cgsub",
                                                               cardgroupSubType,
                                                               null);
                            cardVO.defineNamedWhereClauseParam("acid",
                                                               accountID,
                                                               null);


                            cardVO.executeQuery();

                            if (cardVO.getEstimatedRowCount() != 0) {
                                cardViewObject(cardVO);
                            }
                            if (!addflagcardgroup) {
                                //Collections.sort(cardlist, new MyCardComp());
                                cardgrp.setCard(cardlist);
                                cardgrp.setUnblockedCardList(unblockedcardlist);
                                cardgrp.setPerBlockAndActiveCardList(perBlockAndActiveCardList);
                                cardgrp.setPerBlockAndTempBlockCardList(perBlockAndTempBlockCardList);
                                cardgrp.setPerBlockCardList(perBlockCardList);
                                cardgrp.setTempBlockCardList(tempBlockCardList);
                                cardgrp.setActiveCardList(activeCardList);


                                cardgrouplist.add(cardgrp);
                            }
                        }
                    }

                    if (!addflagaccount) {
                        acc.setCardGroup(cardgrouplist);
                        accountlist.add(acc);
                        part.setAccountList(accountlist);
                        log.info("Two Card System Value:" +
                                 part.getPartnerName() + "Status :" +
                                 consistsTwoCardStatus);
                        part.setConsistsTwoCard(consistsTwoCardStatus);
                    }
                }
            }
            partnerListSession.add(part);
            if (session != null &&
                session.getAttribute("executePartnerObjLogic") == null) {

                session.setAttribute("Partner_Object_List",
                                     partnerListSession);
                log.info(accessDC.getDisplayRecord() + this.getClass() +
                         " Partner object added in session");

            }

        } else {
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " Error : PrtAccountVO1Iterator Bindings is null in my page listner for Admin role");
            iter1 = null;
        }
        log.info(accessDC.getDisplayRecord() + this.getClass() +
                 " Exit from execute Admin function");
    }

    private void cardViewObject(ViewObject cardVO) {

        while (cardVO.hasNext()) {
            card = new CardInfo();
            PrtCardVORowImpl currRowcard = (PrtCardVORowImpl)cardVO.next();
            if (currRowcard != null) {

//                if(currRowcard.getCardEmbossNum().toString()!=null) {
//                   // log.info("card no " + currRowcard.getCardEmbossNum().toString());
//                }
                if (currRowcard.getPrtCardPk() != null) {


                    card.setCardID(currRowcard.getPrtCardPk().toString());
                    card.setCardOverview(true);
                }

                if (currRowcard.getCardType() != null) {
                    cardTypeHS.add(currRowcard.getCardType().toString());
                }

                if (currRowcard.getCardEmbossNum() != null) {
                    card.setExternalCardID(currRowcard.getCardEmbossNum().toString());
                    card.setDisplayCardNumber("XXXX" +
                                              currRowcard.getCardEmbossNum().toString().substring(currRowcard.getCardEmbossNum().toString().length() -
                                                                                                  4,
                                                                                                  currRowcard.getCardEmbossNum().toString().length()));
                }

                if (currRowcard.getCardTextline2() != null) {
                    card.setCardTextline2(currRowcard.getCardTextline2().toString());
                }

                if (currRowcard.getBlockAction() != null)
                { 
                    card.setBlockAction(currRowcard.getBlockAction().toString());
                }

                if (currRowcard.getBlockAction() != null &&
                    currRowcard.getBlockAction().equalsIgnoreCase("1") ||
                    currRowcard.getBlockAction().equalsIgnoreCase("0") &&
                    currRowcard.getCardExpiry() != null &&
                    currRowcard.getCardExpiry().before(new Date())) {

                    card.setBlockAction("2");
                    
                    //log.info("manually setting card no " + currRowcard.getCardEmbossNum().toString());
                    


                }

                if (currRowcard.getCardType() != null &&
                    currRowcard.getCardType().toString().startsWith("2") &&
                    !consistsTwoCardStatus) {

                    consistsTwoCardStatus = true;

                }
                addflagcard = false;


                if (currRowcard.getCardExpiry() == null || currRowcard.getCardExpiry().after(new Date()) && !card.getBlockAction().equalsIgnoreCase("2")) {
                    
                    unblockedcardlist.add(card);
                  
                }

                if (currRowcard.getBlockAction() != null &&
                    currRowcard.getBlockAction().equalsIgnoreCase("0")) 
                    {
                        if (currRowcard.getCardExpiry().after(new Date()))
                        {
                        activeCardList.add(card);
                           
                        }
                        else 
                        {
                        perBlockCardList.add(card);
                        perBlockAndTempBlockCardList.add(card);
                            
                        }


                    perBlockAndActiveCardList.add(card);
                   
                } else if (currRowcard.getBlockAction() != null &&
                           currRowcard.getBlockAction().equalsIgnoreCase("1")) 
                            {

                                if (currRowcard.getCardExpiry().after(new Date()))
                                { 
                                tempBlockCardList.add(card); 
                                   
                                }
                                else {
                                        perBlockCardList.add(card);
                                        perBlockAndActiveCardList.add(card);
                                       
                                    }

                    perBlockAndTempBlockCardList.add(card);
                   
                    
                } else if (currRowcard.getBlockAction() != null &&
                           currRowcard.getBlockAction().equalsIgnoreCase("2")) {
                    perBlockCardList.add(card);
                    perBlockAndActiveCardList.add(card);
                    perBlockAndTempBlockCardList.add(card);
                    
                
                }


            }
            if (!addflagcard)
            {
                cardlist.add(card);
                
            }
        }

    }

    public String getPartnerName(String partnerid) {
        SecurityContext securityContext =
            ADFContext.getCurrent().getSecurityContext();
        HttpServletRequest request =
            ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession session = request.getSession();

        String partnerName = "";
        DCBindingContainer bindings;

        bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry().get("pageTemplateBinding");
        if (bindings == null ||
            bindings.findIteratorBinding("PrtPartnerVO1Iterator") == null) {
            bindings =
                    (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        }


        DCIteratorBinding iter1;
        if (bindings != null &&
            bindings.findIteratorBinding("PrtPartnerVO1Iterator") != null) {
            iter1 = bindings.findIteratorBinding("PrtPartnerVO1Iterator");
            ViewObject partnerVO = iter1.getViewObject();
            partnerVO.setWhereClause("PARTNER_ID =: pid AND COUNTRY_CODE =: countryCode");
           
            partnerVO.defineNamedWhereClauseParam("pid", partnerid, null);

            partnerVO.defineNamedWhereClauseParam("countryCode",
                                                  (String)session.getAttribute("partnerLang"),
                                                  null);
           
            partnerVO.executeQuery();
           
            if (partnerVO.getEstimatedRowCount() > 0) {
                PrtPartnerVORowImpl currRowcard =
                    (PrtPartnerVORowImpl)partnerVO.next();
                if (currRowcard != null &&
                    currRowcard.getCompanyName() != null) {

                    partnerName = currRowcard.getCompanyName().toString();

                }
            }
            if ("PARTNER_ID =: pid AND COUNTRY_CODE =: countryCode".equalsIgnoreCase(partnerVO.getWhereClause())) {
                partnerVO.removeNamedWhereClauseParam("pid");
                partnerVO.removeNamedWhereClauseParam("countryCode");
                partnerVO.setWhereClause("");
                partnerVO.executeQuery();
            }
        } else {
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     accessDC.getDisplayRecord() + this.getClass() +
                     " Error : PrtPartnerVO1Iterator Bindings is null in my page listner");
            iter1 = null;
        }
        return partnerName;
    }

    public String getcardGroupName(String cardgroupMainType,
                                   String cardgroupSubType,
                                   String cardgroupSeq) {
        String cardGroupName = "";
        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter1;


        HttpServletRequest request =
            ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession session = request.getSession();

        if (bindings != null) {
            iter1 = bindings.findIteratorBinding("PrtCardgroupVO1Iterator");
            ViewObject cardGroupVO = iter1.getViewObject();

            if ("ACCOUNT_ID =: accid AND COUNTRY_CODE =: cc".equalsIgnoreCase(cardGroupVO.getWhereClause())) {

                cardGroupVO.removeNamedWhereClauseParam("accid");
                cardGroupVO.removeNamedWhereClauseParam("cc");
                cardGroupVO.setWhereClause("");
                cardGroupVO.executeQuery();
            }
            cardGroupVO.setWhereClause("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub");
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " cardGroup id passed in cardGroup VO is " +
                     cardgroupMainType + " " + cardgroupSubType + " " +
                     cardgroupSeq);

            cardGroupVO.defineNamedWhereClauseParam("cgid", cardgroupSeq,
                                                    null);

            cardGroupVO.defineNamedWhereClauseParam("cc",
                                                    (String)session.getAttribute("partnerLang"),
                                                    null);
            cardGroupVO.defineNamedWhereClauseParam("cgmain",
                                                    cardgroupMainType, null);
            cardGroupVO.defineNamedWhereClauseParam("cgsub", cardgroupSubType,
                                                    null);
            cardGroupVO.executeQuery();
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " RowCount for cardGroupVO  " +
                     cardGroupVO.getEstimatedRowCount());

            if (cardGroupVO.getEstimatedRowCount() > 0) {
                PrtCardgroupVORowImpl currRowcard =
                    (PrtCardgroupVORowImpl)cardGroupVO.next();
                if (currRowcard != null &&
                    currRowcard.getCardgroupDescription() != null) {

                    cardGroupName =
                            currRowcard.getCardgroupDescription().toString();
                    log.info(accessDC.getDisplayRecord() + this.getClass() +
                             " cardGroupName returned from database for cardGroupid " +
                             cardgroupMainType + " " + cardgroupSubType + " " +
                             cardgroupSeq + " is " + cardGroupName);

                }
            }
        } else {
            log.info(accessDC.getDisplayRecord() + this.getClass() +
                     " PrtCardGroupVO1Iterator Bindings is null in my page listner");
            iter1 = null;
        }
        return cardGroupName;
    }

    private User populateUser(String role) {
        User user = new User();
        user.setFirstName("Hanif");
        user.setLastName("Merchant");
        user.setLang("da_DK");
        user.setCountry("DK");

        user.setAuthenticated(true);
        user.setDob(new Date());


        List<Roles> listrole = new ArrayList<Roles>();
        Roles rr = new Roles();
        List<String> idString = new ArrayList<String>();

        rr.setRoleName(role);
        idString.add("DKPP26773218");
        idString.add("NOPP04978201");
//        idString.add("SEPP04377487");
        rr.setIdString(idString);
        listrole.add(rr);


        user.setRoleList(listrole);


        user.setRolelist(Constants.ROLE_WCP_CARD_B2B_ADMIN);

        user.setUserID("B2BMgr1@test.com");
        user.setEmailID("hiten.karamchandani@lntinfotech.com");

        return user;
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

    public void setPartnerlist(List<PartnerInfo> partnerlist) {
        this.partnerlist = partnerlist;
    }

    public List<PartnerInfo> getPartnerlist() {
        return partnerlist;
    }

    public void setConsistsTwoCardStatus(boolean consistsTwoCardStatus) {
        this.consistsTwoCardStatus = consistsTwoCardStatus;
    }

    public boolean isConsistsTwoCardStatus() {
        return consistsTwoCardStatus;
    }

    public void setPart(PartnerInfo part) {
        this.part = part;
    }

    public PartnerInfo getPart() {
        return part;
    }

    public void setAcc(AccountInfo acc) {
        this.acc = acc;
    }

    public AccountInfo getAcc() {
        return acc;
    }

    public void setCardgrp(CardGroupInfo cardgrp) {
        this.cardgrp = cardgrp;
    }

    public CardGroupInfo getCardgrp() {
        return cardgrp;
    }

    public void setCard(CardInfo card) {
        this.card = card;
    }

    public CardInfo getCard() {
        return card;
    }

    public void setAccountlist(List<AccountInfo> accountlist) {
        this.accountlist = accountlist;
    }

    public List<AccountInfo> getAccountlist() {
        return accountlist;
    }

    public void setCardgrouplist(List<CardGroupInfo> cardgrouplist) {
        this.cardgrouplist = cardgrouplist;
    }

    public List<CardGroupInfo> getCardgrouplist() {
        return cardgrouplist;
    }

    public void setCardlist(List<CardInfo> cardlist) {
        this.cardlist = cardlist;
    }

    public List<CardInfo> getCardlist() {
        return cardlist;
    }

    public void setUnblockedcardlist(List<CardInfo> unblockedcardlist) {
        this.unblockedcardlist = unblockedcardlist;
    }

    public List<CardInfo> getUnblockedcardlist() {
        return unblockedcardlist;
    }

    public void setPerBlockAndTempBlockCardList(List<CardInfo> perBlockAndTempBlockCardList) {
        this.perBlockAndTempBlockCardList = perBlockAndTempBlockCardList;
    }

    public List<CardInfo> getPerBlockAndTempBlockCardList() {
        return perBlockAndTempBlockCardList;
    }

    public void setPerBlockAndActiveCardList(List<CardInfo> perBlockAndActiveCardList) {
        this.perBlockAndActiveCardList = perBlockAndActiveCardList;
    }

    public List<CardInfo> getPerBlockAndActiveCardList() {
        return perBlockAndActiveCardList;
    }

    public void setPerBlockCardList(List<CardInfo> perBlockCardList) {
        this.perBlockCardList = perBlockCardList;
    }

    public List<CardInfo> getPerBlockCardList() {
        return perBlockCardList;
    }

    public void setTempBlockCardList(List<CardInfo> tempBlockCardList) {
        this.tempBlockCardList = tempBlockCardList;
    }

    public List<CardInfo> getTempBlockCardList() {
        return tempBlockCardList;
    }

    public void setActiveCardList(List<CardInfo> activeCardList) {
        this.activeCardList = activeCardList;
    }

    public List<CardInfo> getActiveCardList() {
        return activeCardList;
    }

    public void setPartnerListSession(List<PartnerInfo> partnerListSession) {
        this.partnerListSession = partnerListSession;
    }

    public List<PartnerInfo> getPartnerListSession() {
        return partnerListSession;
    }

    public void setAddflagaccount(boolean addflagaccount) {
        this.addflagaccount = addflagaccount;
    }

    public boolean isAddflagaccount() {
        return addflagaccount;
    }

    public void setAddflagcardgroup(boolean addflagcardgroup) {
        this.addflagcardgroup = addflagcardgroup;
    }

    public boolean isAddflagcardgroup() {
        return addflagcardgroup;
    }

    public void setAddflagcard(boolean addflagcard) {
        this.addflagcard = addflagcard;
    }

    public boolean isAddflagcard() {
        return addflagcard;
    }

    public void setExecuteEmp(boolean executeEmp) {
        this.executeEmp = executeEmp;
    }

    public boolean isExecuteEmp() {
        return executeEmp;
    }

    public void setExecuteAcc(boolean executeAcc) {
        this.executeAcc = executeAcc;
    }

    public boolean isExecuteAcc() {
        return executeAcc;
    }

    public void setAccountOverView(boolean accountOverView) {
        this.accountOverView = accountOverView;
    }

    public boolean isAccountOverView() {
        return accountOverView;
    }

    public void setCardGroupOverview(boolean cardGroupOverview) {
        this.cardGroupOverview = cardGroupOverview;
    }

    public boolean isCardGroupOverview() {
        return cardGroupOverview;
    }

    public void setSkipOtherRoles(boolean skipOtherRoles) {
        this.skipOtherRoles = skipOtherRoles;
    }

    public boolean isSkipOtherRoles() {
        return skipOtherRoles;
    }

    public void setAccessDC(AccessDataControl accessDC) {
        this.accessDC = accessDC;
    }

    public AccessDataControl getAccessDC() {
        return accessDC;
    }

    public void setConv(Conversion conv) {
        this.conv = conv;
    }

    public Conversion getConv() {
        return conv;
    }

    public void setaccountCheck(AccountInfo accountCheck) {
        this.accountCheck = accountCheck;
    }

    public AccountInfo getaccountCheck() {
        return accountCheck;
    }

    public void setcardGrpCheck(CardGroupInfo cardGrpCheck) {
        this.cardGrpCheck = cardGrpCheck;
    }

    public CardGroupInfo getcardGrpCheck() {
        return cardGrpCheck;
    }

    public void setIsChangeInRedirectionRequired(boolean isChangeInRedirectionRequired) {
        this.isChangeInRedirectionRequired = isChangeInRedirectionRequired;
    }

    public boolean isIsChangeInRedirectionRequired() {
        return isChangeInRedirectionRequired;
    }

    public void setCardTypeHS(Set cardTypeHS) {
        this.cardTypeHS = cardTypeHS;
    }

    public Set getCardTypeHS() {
        return cardTypeHS;
    }
}

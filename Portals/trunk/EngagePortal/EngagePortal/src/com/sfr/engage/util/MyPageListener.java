package com.sfr.engage.util;


import com.sfr.core.bean.Roles;
import com.sfr.core.bean.User;
import com.sfr.engage.core.AccountInfo;
import com.sfr.engage.core.CardGroupInfo;
import com.sfr.engage.core.CardInfo;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.model.datacontrol.UserClient;
import com.sfr.engage.model.queries.uvo.PrtAccountVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtCardVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtCardgroupVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtPartnerVORowImpl;
import com.sfr.services.core.dao.factory.DAOFactory;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;
import com.sfr.util.validations.Conversion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.share.security.SecurityContext;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.ViewObject;

import oracle.webcenter.portalframework.sitestructure.SiteStructure;
import oracle.webcenter.portalframework.sitestructure.SiteStructureContext;


public class MyPageListener implements PagePhaseListener {

    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();
    Conversion conv = new Conversion();
    private boolean passwordChangeRequired;
    private User user = null;

    PartnerInfo part = new PartnerInfo();
    AccountInfo acc = new AccountInfo();
    CardGroupInfo cardgrp = new CardGroupInfo();
    CardInfo card = new CardInfo();

    List<AccountInfo> accountlist = new ArrayList<AccountInfo>();
    List<CardGroupInfo> cardgrouplist = new ArrayList<CardGroupInfo>();
    List<CardInfo> cardlist = new ArrayList<CardInfo>();
    List<PartnerInfo> partnerlist = new ArrayList<PartnerInfo>();
    List<PartnerInfo> partnerListSession = new ArrayList<PartnerInfo>();



    boolean addflagaccount = false;
    boolean addflagcardgroup = false;
    boolean addflagcard = false;
    boolean executeEmp = false;
    boolean executeAcc = false;
    boolean accountOverView = false;
    boolean cardGroupOverview = false;
    boolean skipOtherRoles = false;
    AccountInfo account_check = new AccountInfo();
    CardGroupInfo cardgrp_check = new CardGroupInfo();


    HashSet cardTypeHS = new HashSet();

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
        //log.fine(accessDC.getDisplayRecord() +  this.getClass() + " Before Phase");
        //TODO : Amit - We need to check, how many times beforePhase gets/should be called for respective page,
        //as it could improve or degrade render time. Presently for Home page alone, it shows 5-7 beforePhase calls.
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

        boolean createCardGroupList = false;
        boolean createCardList = false;
        List<CardGroupInfo> cardgrplist_check = new ArrayList<CardGroupInfo>();

        try {
            String lang = (String)request.getParameter("lang");
            //            System.out.println("Before phase lang from url is " + request.getParameter("lang"));
            if (lang == null) {
                if (session.getAttribute("lang") != null) {
                    lang = (String)session.getAttribute("lang");
                } else {
                    lang = "se_SE";
                }
            }
            session.setAttribute("lang", lang);
            //profile
            String profile = (String)request.getParameter("profile");
            if (profile == null) {
                if (session.getAttribute("profile") != null) {
                    profile = (String)session.getAttribute("profile");
                } else {
                    profile = "business";
                }
            }
            session.setAttribute("profile", profile);

            if (phase.equals(ADFLifecycle.PREPARE_MODEL_ID)) {
                // Read lang request parameter
                //                System.out.println("Phase is PREPARE_MODEL_ID");
                if (session == null) {
                    session = request.getSession(true); //if session is null then create new session
                    //                    System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".beforePhase : " + "Session is NULL" + " New Session ID: " +
                    //                                       session.getId());
                } else {
                    //                    System.out.println("Session is not null so take the previous");
                    session = request.getSession(false);
                    session.setAttribute(Constants.SESSION_PORTAL_NAME, Constants.EN_PORTAL);
                }
            }

            if (phase == 9) {
                // if (!AdfFacesContext.getCurrentInstance().isPostback()) {
                // check if user is authenticated, read user information and set in session
                securityContext = ADFContext.getCurrent().getSecurityContext();
                if (securityContext.isAuthenticated()) {
                    //                    System.out.println("This should execute only after authentication is true");

                    if (session.getAttribute(Constants.SESSION_USER_INFO) == null) {
                        System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".beforePhase : " +
                                           "UserInfo in session is null , Fetching the data");
                        setUserInfoInSession(request, session, userClient, securityContext);
                        boolean usererror = (String)session.getAttribute("SESSION_USER_ERROR") == "" ? false : true;
                        if (usererror) {
                            return;
                        }
                    } else {
                        if (user == null) {
                            user = (User)session.getAttribute(Constants.SESSION_USER_INFO);

                            //TODO: Below if loop to be removed once Incident 23951 is fixed.
                            //SOP's added for troubleshooting incorrect Customer Association problem
                            if (null != user.getRolelist()) {
                                System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + "Incident 23951 - 1 ->" + user.getUserID() +
                                                   user.getEmailID() + user.getRolelist());

                            }
                        }
                    }
                }
                //}
            }


            if (phase.equals(ADFLifecycle.INIT_CONTEXT_ID)) {


                List<PartnerInfo> partnerinfo_list = new ArrayList<PartnerInfo>();

                PartnerInfo partnerobj;

                if (session.getAttribute(Constants.SESSION_USER_INFO) != null) {

                    if (session != null)
                        if (session.getAttribute("executePartnerObjLogic") == null) {
                            //System.out.println("Executing logic to prepare partner object");

                            System.out.println(accessDC.getDisplayRecord() + this.getClass() + " Executing logic to prepare Partner object");


                            partnerinfo_list = new ArrayList<PartnerInfo>();

                            partnerlist = new ArrayList<PartnerInfo>();


                            //This for loop is to go through the entire roleList and corressponding id's and to fetch the partner ids and keep it in partnerinfo_list
                            for (int i = 0; i < user.getRoleList().size(); i++) {

                                if (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_ADMIN) ||
                                    (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2C_SFR)) ||
                                    (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR) ||
                                     (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_EMP))))

                                {

                                    if (user.getRoleList().get(i).getIdString() != null) {
                                        int idlist = 0;
                                        System.out.println(accessDC.getDisplayRecord() + this.getClass() + " user.getRoleList().get(i).getIdString().size()" +
                                                           user.getRoleList().get(i).getIdString().size());
                                        do {
                                            partnerobj = new PartnerInfo();

                                            int pid_start = user.getRoleList().get(i).getIdString().get(idlist).indexOf("PP");

                                            String pid = user.getRoleList().get(i).getIdString().get(idlist).substring(pid_start + 2, pid_start + 10);
                                            partnerobj.setPartnerValue(pid);

                                            if (partnerinfo_list.size() > 0) {

                                                boolean addflag = false;
                                                for (int k = 0; k < partnerinfo_list.size(); k++) {

                                                    if (partnerinfo_list.get(k).getPartnerValue().equalsIgnoreCase(pid)) {

                                                        addflag = true;
                                                        break;
                                                    }


                                                }
                                                if (!addflag)
                                                    partnerinfo_list.add(partnerobj);
                                            } else {
                                                partnerinfo_list.add(partnerobj);

                                            }
                                            idlist++;
                                        } while (idlist < user.getRoleList().get(i).getIdString().size());

                                    }


                                }


                            }


                            System.out.println(accessDC.getDisplayRecord() + this.getClass() +
                                               " Final Partner list size after going through the entire RoleList " + partnerinfo_list.size());

                            accountlist = new ArrayList<AccountInfo>();


                            if (partnerinfo_list.size() != 0) {

                                partnerlist = partnerinfo_list;
                                //TODO : HITK - Check if this does not works properly then add partnerinfo_list in session and
                                //in executeAdmin function read from session


                            }

                            //TODO : HITK - To remove this after testing
                            System.out.println(accessDC.getDisplayRecord() + this.getClass() +
                                               " Final Partner list 2 size after going through the entire RoleList " + partnerlist.size());
                            partnerListSession = new ArrayList<PartnerInfo>();


                            cardTypeHS = new HashSet();

                            for (int i = 0; i < user.getRoleList().size(); i++) {


                                if (user.getRoleList().get(i).getRoleName().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN)) {
                                    System.out.println(accessDC.getDisplayRecord() + this.getClass() + " User contains Admin role");

                                    if (user.getRoleList().get(i).getIdString() != null) {
                                        int partIndex = 0;
                                        do {
                                            executeAdmin(user, partIndex);
                                            partIndex++;
                                        } while (partIndex < user.getRoleList().get(i).getIdString().size());
                                    }
                                    // session.setAttribute("executePartnerObjLogic", "no");
                                    System.out.println((accessDC.getDisplayRecord() + this.getClass() +
                                                        " Session variable added to avoid multiple execution of code on my page listner after executing B2B Admin role"));
                                    System.out.println("cardTypeHS size is " + cardTypeHS.size());
                                    session.setAttribute("cardTypeList", cardTypeHS);

                                }

                            }


                            if (session != null)

                            {
                                System.out.println("Session is not null & executePartnerObjLogic value is " + session.getAttribute("executePartnerObjLogic"));

                                if (session.getAttribute("executePartnerObjLogic") == null) {

                                    for (int partid = 0; partid < partnerlist.size(); partid++) {
                                        String partnerId = partnerlist.get(partid).getPartnerValue().toString();
                                        part = new PartnerInfo();
                                        accountlist = new ArrayList<AccountInfo>();

                                        //This variable is just to compare if partner Id from partner list matches with id's from IDM's getattributes
                                        System.out.println(" inside partner iterator " + partid);
                                        //TODO : HITK - For multipartner add one for loop for partnerlist size
                                        //TODO : Also to check if partner list values are proper or else get it from session
                                        skipOtherRoles = false;

                                        if (partnerListSession != null) {
                                            for (int k = 0; k < partnerListSession.size(); k++)
                                                if (partnerListSession.get(k).getPartnerValue().equalsIgnoreCase(partnerId) &&
                                                    partnerListSession.get(k).isCompanyOverview()) {
                                                    skipOtherRoles = true;
                                                    break;
                                                } else
                                                    skipOtherRoles = false;
                                        }
                                        if (!skipOtherRoles) {
                                            for (int i = 0; i < user.getRoleList().size(); i++) {

                                                if (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR)) {
                                                    DCBindingContainer bindings;
                                                    partnerinfo_list = new ArrayList<PartnerInfo>();
                                                    if (user.getRoleList().get(i).getIdString() != null) {


                                                        int idlist = 0;
                                                        System.out.println("TEMP ------------->" + user.getRoleList().get(i).getIdString().size());
                                                        System.out.println("Partner id " + partid + " " + user.getRoleList().get(i).getIdString().get(idlist));

                                                        int pid_start = user.getRoleList().get(i).getIdString().get(partid).indexOf("PP");
                                                        //                                                        System.out.println("pid start index " + pid_start);
                                                        String pid =
                                                            user.getRoleList().get(i).getIdString().get(partid).substring(pid_start + 2, pid_start + 10);
                                                        //                                                        System.out.println("partner id " + pid);

                                                        part.setPartnerValue(partnerId);
                                                        part.setPartnerName(getPartnerName(partnerId));
                                                        part.setCountry(conv.getLangForWERCSURL(user.getLang().toString()));
                                                        part.setCompanyOverview(false);


                                                        do {
                                                            if (idlist != 0) {
                                                                System.out.println(" In iterator  " + idlist);

                                                                pid_start = user.getRoleList().get(i).getIdString().get(idlist).indexOf("PP");
                                                                System.out.println("pid start index " + pid_start);
                                                                pid =
user.getRoleList().get(i).getIdString().get(idlist).substring(pid_start + 2, pid_start + 10);
                                                                System.out.println("partner id " + pid);
                                                            }
                                                            if (partnerId != null)
                                                                if (partnerId.equals(pid)) {


                                                                    executeEmp = false;

                                                                    if (user.getRoleList().get(i).getIdString().get(idlist).contains("AC") &&
                                                                        partnerId.equals(user.getRoleList().get(i).getIdString().get(idlist).substring(pid_start +
                                                                                                                                                       2,
                                                                                                                                                       pid_start +
                                                                                                                                                       10))) {


                                                                        System.out.println("Account B2B Manager");

                                                                        acc = new AccountInfo();
                                                                        cardgrouplist = new ArrayList<CardGroupInfo>();
                                                                        int accid_start = user.getRoleList().get(i).getIdString().get(idlist).indexOf("AC");
                                                                        System.out.println("accid start index " + accid_start);
                                                                        String accid =
                                                                            user.getRoleList().get(i).getIdString().get(idlist).substring(accid_start + 2,
                                                                                                                                          accid_start + 12);
                                                                        System.out.println("account id " + accid);
                                                                        acc.setAccountNumber(accid);
                                                                        acc.setAccountOverview(true);
                                                                        System.out.println("Account overview should be visible");

                                                                        AccountInfo account_check = new AccountInfo();
                                                                        addflagaccount = false;
                                                                        for (int listsize = 0; listsize < accountlist.size(); listsize++) {
                                                                            System.out.println("account id value in account list " +
                                                                                               accountlist.get(listsize).getAccountNumber());
                                                                            account_check = accountlist.get(listsize);
                                                                            //                                                      //cardgrouplist_check = account_check.getCardGroup();
                                                                            //
                                                                            if (account_check.getAccountNumber().equalsIgnoreCase(acc.getAccountNumber())) {
                                                                                System.out.println("Account ID already exists in partner object");
                                                                                //                                                        executeEmp = true;
                                                                                if (account_check.isAccountOverview()) {
                                                                                    addflagaccount = true;
                                                                                    break;
                                                                                }
                                                                            }
                                                                        }


                                                                        //System.out.println("Execute employee " + executeEmp);
                                                                        if (!addflagaccount) {
                                                                            //This means account only does not exists or Account exists but account overview is false, so in both the cases we have to create new cardgrplist new cardlist .....


                                                                            String AccountID = acc.getAccountNumber();
                                                                            bindings =
                                                                                    (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                                                                            DCIteratorBinding iter2;
                                                                            if (bindings != null) {
                                                                                iter2 = bindings.findIteratorBinding("PrtCardgroupVO1Iterator");
                                                                                System.out.println("DC Iterator bindings for Card group found in mypagelistner");
                                                                            } else {
                                                                                System.out.println("bindings is null");
                                                                                iter2 = null;
                                                                            }
                                                                            ViewObject vo2 = iter2.getViewObject();


                                                                            if ("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub".equalsIgnoreCase(vo2.getWhereClause())) {
                                                                                System.out.println(" checking this ");
                                                                                vo2.removeNamedWhereClauseParam("cgid");
                                                                                vo2.removeNamedWhereClauseParam("cc");
                                                                                vo2.removeNamedWhereClauseParam("cgmain");
                                                                                vo2.removeNamedWhereClauseParam("cgsub");
                                                                                vo2.setWhereClause("");
                                                                                vo2.executeQuery();

                                                                            }

                                                                            vo2.setWhereClause("ACCOUNT_ID =: accid AND COUNTRY_CODE =: cc");
                                                                            vo2.defineNamedWhereClauseParam("accid", AccountID, null);
                                                                            vo2.defineNamedWhereClauseParam("cc",
                                                                                                            (String)session.getAttribute(Constants.userLang),
                                                                                                            null);


                                                                            vo2.executeQuery();
                                                                            System.out.println("row count from cardgroup vo" + vo2.getEstimatedRowCount());

                                                                            if (vo2.getEstimatedRowCount() != 0) {

                                                                                //System.out.println("RowCount in helpinfo "+vo.getEstimatedRowCount());

                                                                                while (vo2.hasNext()) {


                                                                                    cardgrp = new CardGroupInfo();
                                                                                    cardlist = new ArrayList<CardInfo>();
                                                                                    PrtCardgroupVORowImpl currRowcardgrp = (PrtCardgroupVORowImpl)vo2.next();

                                                                                    if (currRowcardgrp != null) {
                                                                                        System.out.println(" row != null");

                                                                                        if (currRowcardgrp.getCardgroupSeq() != null) {
                                                                                            System.out.println("Cardgroup id is " +
                                                                                                               currRowcardgrp.getCardgroupSeq());
                                                                                            cardgrp.setCardGroupID((currRowcardgrp.getCardgroupMainType().toString().concat(currRowcardgrp.getCardgroupSubType().toString())).concat(currRowcardgrp.getCardgroupSeq().toString()));
                                                                                            cardgrp.setCardGroupMainType(currRowcardgrp.getCardgroupMainType().toString());
                                                                                            cardgrp.setCardGroupSeq(currRowcardgrp.getCardgroupSeq().toString());
                                                                                            cardgrp.setCardGroupSubType(currRowcardgrp.getCardgroupSubType().toString());
                                                                                            cardgrp.setCardGroupName(currRowcardgrp.getCardgroupDescription().toString());
                                                                                            cardgrp.setCardGroupOverview(true);

                                                                                        }

                                                                                        addflagcardgroup = false;
                                                                                        for (int k = 0; k < cardgrouplist.size(); k++) {
                                                                                            System.out.println("cardgroup id value in cardgroup list " +
                                                                                                               cardgrouplist.get(k).getCardGroupID());
                                                                                            System.out.println("New cardgroup id value to compare" +
                                                                                                               cardgrp.getCardGroupID());


                                                                                            if (cardgrouplist.get(k).getCardGroupID().equalsIgnoreCase(cardgrp.getCardGroupID())) {
                                                                                                System.out.println("cardgroup id already exists in cardgroup list");
                                                                                                addflagcardgroup = true;
                                                                                                break;
                                                                                            }
                                                                                        }


                                                                                    }
                                                                                    if (!addflagcardgroup)
                                                                                    //add cardlist in cardgroup

                                                                                    {

                                                                                        String CardgroupMainType = cardgrp.getCardGroupMainType().toString();
                                                                                        String CardgroupSubType = cardgrp.getCardGroupSubType().toString();
                                                                                        String CardgroupSeq = cardgrp.getCardGroupSeq().toString();
                                                                                        bindings =
                                                                                                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                                                                                        DCIteratorBinding iter3;
                                                                                        if (bindings != null) {
                                                                                            iter3 = bindings.findIteratorBinding("PrtCardVO1Iterator");
                                                                                            System.out.println("DC Iterator bindings for Card VO found in mypagelistner");
                                                                                        } else {
                                                                                            System.out.println("bindings is null");
                                                                                            iter3 = null;
                                                                                        }
                                                                                        ViewObject vo3 = iter3.getViewObject();

                                                                                        vo3 = iter3.getViewObject();

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

                                                                                        vo3.defineNamedWhereClauseParam("cgid", CardgroupSeq, null);
                                                                                        vo3.defineNamedWhereClauseParam("cc",
                                                                                                                        (String)session.getAttribute(Constants.userLang),
                                                                                                                        null);
                                                                                        vo3.defineNamedWhereClauseParam("cgmain", CardgroupMainType, null);
                                                                                        vo3.defineNamedWhereClauseParam("cgsub", CardgroupSubType, null);


                                                                                        vo3.executeQuery();
                                                                                        System.out.println("row count from cardgroup vo" +
                                                                                                           vo3.getEstimatedRowCount());

                                                                                        if (vo3.getEstimatedRowCount() != 0) {

                                                                                            //System.out.println("RowCount in helpinfo "+vo.getEstimatedRowCount());

                                                                                            while (vo3.hasNext()) {


                                                                                                card = new CardInfo();
                                                                                                PrtCardVORowImpl currRowcard = (PrtCardVORowImpl)vo3.next();

                                                                                                if (currRowcard != null) {
                                                                                                    System.out.println(" row != null");

                                                                                                    if (currRowcard.getPrtCardPk() != null) {
                                                                                                        System.out.println("Card id is " +
                                                                                                                           currRowcard.getPrtCardPk());
                                                                                                        card.setCardID(currRowcard.getPrtCardPk().toString());


                                                                                                    }
                                                                                                    if (currRowcard.getCardEmbossNum() != null)
                                                                                                        card.setExternalCardID(currRowcard.getCardEmbossNum().toString());


                                                                                                    addflagcard = false;
                                                                                                    for (int k = 0; k < cardlist.size(); k++) {
                                                                                                        System.out.println("cardgroup id value in cardgroup list " +
                                                                                                                           cardlist.get(k).getCardID());
                                                                                                        System.out.println("New cardgroup id value to compare" +
                                                                                                                           card.getCardID());


                                                                                                        if (cardlist.get(k).getCardID().equalsIgnoreCase(card.getCardID())) {
                                                                                                            System.out.println("card id already exists in card list");
                                                                                                            addflagcard = true;
                                                                                                            break;
                                                                                                        }
                                                                                                    }


                                                                                                }
                                                                                                if (!addflagcard)
                                                                                                    cardlist.add(card);


                                                                                            }

                                                                                            cardgrp.setCard(cardlist);
                                                                                            cardgrouplist.add(cardgrp);

                                                                                        }


                                                                                        //                                                                        cardgrp.setCard(cardlist);
                                                                                        //                                                                        cardgrouplist.add(cardgrp);

                                                                                    }


                                                                                }
                                                                                acc.setCardGroup(cardgrouplist);
                                                                                for (int z = 0; z < accountlist.size(); z++)
                                                                                    if (accountlist.get(z).getAccountNumber().equals(acc.getAccountNumber())) {
                                                                                        //accountInterchange = accountlist.get(z);
                                                                                        accountlist.set(z, acc);

                                                                                    }
                                                                                if (!accountlist.contains(acc))
                                                                                    accountlist.add(acc);


                                                                                //  part.setAccountList(accountlist);
                                                                            }


                                                                            //acc.setCardGroup(cardgrouplist);
                                                                            //AccountInfo accountInterchange = new AccountInfo();

                                                                            //                                                            for (int z = 0;
                                                                            //                                                                 z < accountlist.size();
                                                                            //                                                                 z++)
                                                                            //                                                                if (accountlist.get(z).getAccountNumber().equals(acc.getAccountNumber())) {
                                                                            //                                                                    //accountInterchange = accountlist.get(z);
                                                                            //                                                                    accountlist.set(z,
                                                                            //                                                                                    acc);
                                                                            //
                                                                            //                                                                }
                                                                            //                                                            if (!accountlist.contains(acc))
                                                                            //                                                                accountlist.add(acc);
                                                                            //
                                                                            //                                                            //TODO : Remove from here add it at the end after completing for CG
                                                                            //                                                            part.setAccountList(accountlist);


                                                                        }
                                                                        part.setAccountList(accountlist);

                                                                    } else if (user.getRoleList().get(i).getIdString().get(idlist).contains("CG") &&
                                                                               partnerId.equals(user.getRoleList().get(i).getIdString().get(idlist).substring(pid_start +
                                                                                                                                                              2,
                                                                                                                                                              pid_start +
                                                                                                                                                              10))) {
                                                                        AccountInfo account_check = new AccountInfo();
                                                                        List<CardGroupInfo> cardgrouplist_check = new ArrayList<CardGroupInfo>();
                                                                        boolean executeCardGroupLogic = false;
                                                                        int cgid_start = user.getRoleList().get(i).getIdString().get(idlist).indexOf("CG");
                                                                        System.out.println("cgid start index " + cgid_start);
                                                                        String CardGroupID =
                                                                            user.getRoleList().get(i).getIdString().get(idlist).substring(cgid_start + 2);
                                                                        System.out.println("CardGroupId is " + CardGroupID);
                                                                        int cardgrp_count = 0;
                                                                        System.out.println("Card Group B2B Manager");

                                                                        acc = new AccountInfo();

                                                                        cardgrp = new CardGroupInfo();
                                                                        cardlist = new ArrayList<CardInfo>();
                                                                        String accountId_cardVO = "";


                                                                        String CardgroupMainType =
                                                                            user.getRoleList().get(i).getIdString().get(idlist).substring(cgid_start + 2,
                                                                                                                                          cgid_start + 5);
                                                                        String CardgroupSubType =
                                                                            user.getRoleList().get(i).getIdString().get(idlist).substring(cgid_start + 5,
                                                                                                                                          cgid_start + 8);
                                                                        String CardgroupSeq =
                                                                            user.getRoleList().get(i).getIdString().get(idlist).substring(cgid_start + 8);
                                                                        cardgrp.setCardGroupMainType(CardgroupMainType);
                                                                        cardgrp.setCardGroupSubType(CardgroupSubType);
                                                                        cardgrp.setCardGroupSeq(CardgroupSeq);
                                                                        cardgrp.setCardGroupID((CardgroupMainType.concat(CardgroupSubType).concat(CardgroupSeq)));
                                                                        cardgrp.setCardGroupName(getcardGroupName(CardgroupMainType, CardgroupSubType,
                                                                                                                  CardgroupSeq));
                                                                        cardgrp.setCardGroupOverview(true);


                                                                        System.out.println("cardgroup seq " + CardgroupSeq);
                                                                        System.out.println("Cardgroup Maintype " + CardgroupMainType);
                                                                        System.out.println("CardgroupSubtype " + CardgroupSubType);

                                                                        //Execute CardgroupVO to fetch corresponding Account number

                                                                        bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                                                                        DCIteratorBinding iter3;
                                                                        if (bindings != null) {
                                                                            iter3 = bindings.findIteratorBinding("PrtCardVO1Iterator");
                                                                            System.out.println("DC Iterator bindings for Card VO found in mypagelistner");
                                                                        } else {
                                                                            System.out.println("bindings is null");
                                                                            iter3 = null;
                                                                        }
                                                                        ViewObject vo3 = iter3.getViewObject();

                                                                        vo3 = iter3.getViewObject();


                                                                        if ("PRT_CARD_PK =: cardid AND COUNTRY_CODE =: cc".equalsIgnoreCase(vo3.getWhereClause())) {
                                                                            System.out.println("Remove query executed");
                                                                            vo3.removeNamedWhereClauseParam("cardid");
                                                                            vo3.removeNamedWhereClauseParam("cc");
                                                                            vo3.setWhereClause("");
                                                                            vo3.executeQuery();
                                                                        }


                                                                        vo3.setWhereClause("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub");

                                                                        vo3.defineNamedWhereClauseParam("cgid", CardgroupSeq, null);
                                                                        vo3.defineNamedWhereClauseParam("cc", (String)session.getAttribute(Constants.userLang),
                                                                                                        null);
                                                                        vo3.defineNamedWhereClauseParam("cgmain", CardgroupMainType, null);
                                                                        vo3.defineNamedWhereClauseParam("cgsub", CardgroupSubType, null);


                                                                        vo3.executeQuery();
                                                                        System.out.println("row count from cardgroup vo" + vo3.getEstimatedRowCount());

                                                                        if (vo3.getEstimatedRowCount() != 0) {

                                                                            //System.out.println("RowCount in helpinfo "+vo.getEstimatedRowCount());

                                                                            while (vo3.hasNext()) {


                                                                                card = new CardInfo();
                                                                                PrtCardVORowImpl currRowcard = (PrtCardVORowImpl)vo3.next();

                                                                                if (currRowcard != null) {
                                                                                    System.out.println(" row != null");

                                                                                    if (currRowcard.getPrtCardPk() != null) {
                                                                                        System.out.println("Card id is " + currRowcard.getPrtCardPk());
                                                                                        card.setCardID(currRowcard.getPrtCardPk().toString());

                                                                                        //                                                                        accountId_cardVO =
                                                                                        //                                                                                currRowcard.getAccountId().toString();
                                                                                    }
                                                                                    if (currRowcard.getCardEmbossNum() != null)
                                                                                        card.setExternalCardID(currRowcard.getCardEmbossNum().toString());

                                                                                    if (currRowcard.getAccountId() != null) {
                                                                                        System.out.println("Account Id is " + currRowcard.getAccountId());
                                                                                        acc.setAccountNumber(currRowcard.getAccountId().toString());
                                                                                    }

                                                                                    cardlist.add(card);
                                                                                }
                                                                            }

                                                                        }
                                                                        account_check = new AccountInfo();
                                                                        createCardGroupList = true;
                                                                        addflagaccount = false;
                                                                        for (int listsize = 0; listsize < accountlist.size(); listsize++) {
                                                                            System.out.println("account id value in account list " +
                                                                                               accountlist.get(listsize).getAccountNumber());
                                                                            account_check = accountlist.get(listsize);
                                                                            //                                                      //cardgrouplist_check = account_check.getCardGroup();
                                                                            //
                                                                            if (account_check.getAccountNumber().equalsIgnoreCase(acc.getAccountNumber())) {
                                                                                System.out.println("Account ID already exists in partner object");
                                                                                createCardGroupList = false;
                                                                                //                                                        executeEmp = true;
                                                                                if (account_check.isAccountOverview()) {
                                                                                    addflagaccount = true;
                                                                                    break;
                                                                                }
                                                                                break;
                                                                            }
                                                                        }
                                                                        if (!addflagaccount && accountlist.size() > 0) {
                                                                            if (createCardGroupList) {
                                                                                cardgrp.setCard(cardlist);
                                                                                cardgrp.setCardGroupOverview(true);
                                                                                cardgrouplist = new ArrayList<CardGroupInfo>();
                                                                                cardgrouplist.add(cardgrp);
                                                                                acc.setCardGroup(cardgrouplist);
                                                                                acc.setAccountOverview(false);
                                                                                accountlist.add(acc);
                                                                                part.setAccountList(accountlist);
                                                                                part.setCompanyOverview(false);
                                                                            } else {
                                                                                //Account exists so take cardgroup list from that account object & before adding just check we have to add or update
                                                                                //the existing one
                                                                                cardgrouplist = account_check.getCardGroup();
                                                                                cardgrp.setCard(cardlist);
                                                                                cardgrp.setCardGroupOverview(true);

                                                                                addflagcardgroup = false;
                                                                                for (int k = 0; k < cardgrouplist.size(); k++) {
                                                                                    System.out.println("cardgroup id value in cardgroup list " +
                                                                                                       cardgrouplist.get(k).getCardGroupID());
                                                                                    System.out.println("New cardgroup id value to compare" +
                                                                                                       cardgrp.getCardGroupID());

                                                                                    if (cardgrouplist.get(k).getCardGroupID().equalsIgnoreCase(cardgrp.getCardGroupID())) {
                                                                                        System.out.println("cardgroup id already exists in cardgroup list");
                                                                                        addflagcardgroup = true;
                                                                                        break;
                                                                                    }
                                                                                }
                                                                                if (addflagcardgroup) {

                                                                                    //Replace the old cardgroup with new one
                                                                                    for (int z = 0; z < cardgrouplist.size(); z++) {
                                                                                        if (cardgrouplist.get(z).getCardGroupMainType().equals(cardgrp.getCardGroupMainType()) &&
                                                                                            cardgrouplist.get(z).getCardGroupSubType().equals(cardgrp.getCardGroupSubType()) &&
                                                                                            cardgrouplist.get(z).getCardGroupSeq().equals(cardgrp.getCardGroupSeq())) {
                                                                                            //accountInterchange = accountlist.get(z);
                                                                                            cardgrouplist.set(z, cardgrp);

                                                                                        }
                                                                                    }

                                                                                    //Replace the new account also with old one by adding newly created cardgrp obj to the cardgrp list
                                                                                    for (int z = 0; z < accountlist.size(); z++)
                                                                                        if (accountlist.get(z).getAccountNumber().equals(acc.getAccountNumber())) {
                                                                                            //accountInterchange = accountlist.get(z);
                                                                                            acc.setCardGroup(cardgrouplist);
                                                                                            accountlist.set(z, acc);
                                                                                            part.setAccountList(accountlist);
                                                                                            break;

                                                                                        }

                                                                                } else {
                                                                                    //Add cardgrp to the cardgrp list and then replacae account

                                                                                    cardgrouplist.add(cardgrp);
                                                                                    acc.setCardGroup(cardgrouplist);
                                                                                    //Replace the new account also with old one by adding newly created cardgrp obj to the cardgrp list
                                                                                    for (int z = 0; z < accountlist.size(); z++)
                                                                                        if (accountlist.get(z).getAccountNumber().equals(acc.getAccountNumber())) {
                                                                                            //accountInterchange = accountlist.get(z);
                                                                                            accountlist.set(z, acc);
                                                                                            part.setAccountList(accountlist);
                                                                                            break;

                                                                                        }
                                                                                }
                                                                            }
                                                                        }
                                                                        if (accountlist.size() == 0)
                                                                        {
                                                                            cardgrp.setCard(cardlist);
                                                                            cardgrp.setCardGroupOverview(true);
                                                                            cardgrouplist = new ArrayList<CardGroupInfo>();
                                                                            cardgrouplist.add(cardgrp);
                                                                            acc.setCardGroup(cardgrouplist);
                                                                            acc.setAccountOverview(false);
                                                                            accountlist.add(acc);
                                                                            part.setAccountList(accountlist);
                                                                            part.setCompanyOverview(false);
                                                                            //partnerListSession.add(part);
                                                                        }
                                                                    }
                                                                }
                                                            idlist++;
                                                        } while (idlist < user.getRoleList().get(i).getIdString().size());
                                                    }
                                                    //flagexecute = false;
                                                }
                                                else if (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_EMP))
                                                {
                                                    System.out.println("CARD AMIN as user role =======>" + user.getRoleList().get(i).getRoleName());

                                                    // if(partnerinfo_list.size() == 1 && user.getRoleList().get(0).getIdString().get(0).contains("PP"+ partnerinfo_list.get(0).getPartnerValue()))
                                                    //                    {
                                                    int idlist = 0;
                                                    CardInfo card_temp = new CardInfo(); // use case : when we get multiple cards belonging to same cardgroup
                                                    List<CardInfo> cardgrplist_temp =
                                                        // use case : when we get multiple cards belonging to same cardgroup
                                                        new ArrayList<CardInfo>();

                                                    System.out.println("TEMP ------------->" + user.getRoleList().get(i).getIdString().size());
                                                    do {
                                                        executeEmp = false;
                                                        System.out.println("User is B2B Employee");
                                                        createCardGroupList = false;
                                                        acc = new AccountInfo();
                                                        cardgrp = new CardGroupInfo();

                                                        createCardGroupList = false;
                                                        createCardList = true;
                                                        // cardlist = new ArrayList<CardInfo>();

                                                        int ccid_start = user.getRoleList().get(i).getIdString().get(idlist).indexOf("CC");
                                                        System.out.println("ccid start index " + ccid_start);
                                                        String cardId = user.getRoleList().get(i).getIdString().get(idlist).substring(ccid_start + 2);
                                                        System.out.println("Card id is " + cardId);
                                                        int pid_start = user.getRoleList().get(i).getIdString().get(idlist).indexOf("PP");
                                                        String pid =
                                                            user.getRoleList().get(i).getIdString().get(idlist).substring(pid_start + 2, pid_start + 10);

                                                        if (partnerId != null)
                                                            if (partnerId.equals(pid)) {

                                                                DCBindingContainer bindings =
                                                                    (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                                                                DCIteratorBinding iter3;
                                                                if (bindings != null) {
                                                                    iter3 = bindings.findIteratorBinding("PrtCardVO1Iterator");
                                                                    System.out.println("DC Iterator bindings for Card VO found in mypagelistner");
                                                                } else {
                                                                    System.out.println("bindings is null");
                                                                    iter3 = null;
                                                                }
                                                                ViewObject vo3 = iter3.getViewObject();

                                                                if ("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub".equalsIgnoreCase(vo3.getWhereClause())) {
                                                                    System.out.println("Remove query executed");
                                                                    vo3.removeNamedWhereClauseParam("cgid");
                                                                    vo3.removeNamedWhereClauseParam("cc");
                                                                    vo3.removeNamedWhereClauseParam("cgmain");
                                                                    vo3.removeNamedWhereClauseParam("cgsub");
                                                                    vo3.setWhereClause("");
                                                                    vo3.executeQuery();
                                                                }

                                                                vo3.setWhereClause("PRT_CARD_PK =: cardid AND COUNTRY_CODE =: cc");
                                                                vo3.defineNamedWhereClauseParam("cardid", cardId, null);
                                                                vo3.defineNamedWhereClauseParam("cc", (String)session.getAttribute(Constants.userLang), null);

                                                                System.out.println(vo3.getQuery());
                                                                vo3.executeQuery();
                                                                System.out.println("row count from cardgroup vo" + vo3.getEstimatedRowCount());
                                                                if (vo3.getEstimatedRowCount() != 0) {
                                                                    //System.out.println("RowCount in helpinfo "+vo.getEstimatedRowCount());
                                                                    while (vo3.hasNext()) {
                                                                        card = new CardInfo();
                                                                        PrtCardVORowImpl currRowcard = (PrtCardVORowImpl)vo3.next();
                                                                        if (currRowcard != null) {
                                                                            System.out.println(" row != null");

                                                                            if (currRowcard.getPrtCardPk() != null) {
                                                                                System.out.println("Card id is " + currRowcard.getPrtCardPk());
                                                                                card.setCardID(currRowcard.getPrtCardPk().toString());
                                                                                card.setExternalCardID(currRowcard.getCardEmbossNum().toString());
                                                                            }
                                                                            if (currRowcard.getCardgroupMainType() != null &&
                                                                                currRowcard.getCardgroupSubType() != null &&
                                                                                currRowcard.getCardgroupSeq() != null && currRowcard.getAccountId() != null &&
                                                                                currRowcard.getPartnerId() != null && currRowcard.getAccountId() != null &&
                                                                                currRowcard.getPartnerId() != null) {
                                                                                cardgrp.setCardGroupMainType((currRowcard.getCardgroupMainType().toString()));
                                                                                cardgrp.setCardGroupSubType((currRowcard.getCardgroupSubType().toString()));
                                                                                cardgrp.setCardGroupSeq(currRowcard.getCardgroupSeq().toString());
                                                                                cardgrp.setCardGroupID((currRowcard.getCardgroupMainType().toString().concat(currRowcard.getCardgroupSubType().toString())).concat(currRowcard.getCardgroupSeq().toString()));
                                                                                cardgrp.setCardGroupName(getcardGroupName(currRowcard.getCardgroupMainType().toString(),
                                                                                                                          currRowcard.getCardgroupSubType().toString(),
                                                                                                                          currRowcard.getCardgroupSeq().toString()));
                                                                                acc.setAccountNumber(currRowcard.getAccountId().toString());
                                                                                part.setPartnerValue(currRowcard.getPartnerId().toString());
                                                                                part.setPartnerName(getPartnerName(currRowcard.getPartnerId().toString()));
                                                                            }
                                                                        }
                                                                    }
                                                                    account_check = new AccountInfo();
                                                                    cardgrp_check = new CardGroupInfo();

                                                                    accountOverView = false;
                                                                    cardGroupOverview = false;
                                                                    addflagaccount = true;
                                                                    for (int listsize = 0; listsize < accountlist.size(); listsize++) {

                                                                        System.out.println("account id value in account list " +
                                                                                           accountlist.get(listsize).getAccountNumber());
                                                                        account_check = accountlist.get(listsize);
                                                                        //cardgrouplist_check = account_check.getCardGroup();

                                                                        if (account_check.getAccountNumber().equalsIgnoreCase(acc.getAccountNumber())) {
                                                                            createCardGroupList = false;
                                                                            cardgrouplist = account_check.getCardGroup();
                                                                            addflagaccount = false;

                                                                            if (account_check.isAccountOverview()) {
                                                                                accountOverView = true;
                                                                                break;
                                                                            }
                                                                            cardgrplist_check = account_check.getCardGroup();
                                                                            //System.out.println("Cardgroup exists in account already present in accountlist");
                                                                            //executeEmp = true;
                                                                            //createCardList = false;
                                                                            for (int cardgrplistsize = 0; cardgrplistsize < cardgrplist_check.size();
                                                                                 cardgrplistsize++) {
                                                                                System.out.println("cardgrp id value in account list " +
                                                                                                   cardgrplist_check.get(cardgrplistsize).getCardGroupMainType());
                                                                                System.out.println("cardgrp id value in account list " +
                                                                                                   cardgrplist_check.get(cardgrplistsize).getCardGroupSubType());
                                                                                System.out.println("cardgrp id value in account list " +
                                                                                                   cardgrplist_check.get(cardgrplistsize).getCardGroupSeq());
                                                                                cardgrp_check = cardgrplist_check.get(cardgrplistsize);
                                                                                if (cardgrp_check.getCardGroupMainType().equalsIgnoreCase(cardgrp.getCardGroupMainType()) &&
                                                                                    cardgrp_check.getCardGroupSubType().equalsIgnoreCase(cardgrp.getCardGroupSubType()) &&
                                                                                    cardgrp_check.getCardGroupSeq().equalsIgnoreCase(cardgrp.getCardGroupSeq())) {
                                                                                    createCardList = false;

                                                                                    if (cardgrp_check.isCardGroupOverview()) {
                                                                                        cardGroupOverview = true;
                                                                                        break;
                                                                                    } else {
                                                                                        //Take out the cardgrp obj from list and modify the same cardgrp object to add card in cardgrp's object list
                                                                                        cardlist = cardgrp_check.getCard();
                                                                                        if (!cardlist.contains(card))
                                                                                            cardlist.add(card);
                                                                                        cardgrp_check.setCard(cardlist);
                                                                                        cardgrp_check.setCardGroupOverview(false);
                                                                                    }
                                                                                    //Replace the old cardgrp obj in list with new one cardgrp_check
                                                                                    for (int z = 0; z < cardgrouplist.size(); z++) {
                                                                                        if (cardgrouplist.get(z).getCardGroupMainType().equals(cardgrp_check.getCardGroupMainType()) &&
                                                                                            cardgrouplist.get(z).getCardGroupMainType().equals(cardgrp_check.getCardGroupMainType()) &&
                                                                                            cardgrouplist.get(z).getCardGroupMainType().equals(cardgrp_check.getCardGroupMainType())) {
                                                                                            //accountInterchange = accountlist.get(z);
                                                                                            cardgrouplist.set(z, cardgrp_check);
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                            if (createCardList == true) { //Cardgrp doesnot exists
                                                                                cardlist = new ArrayList<CardInfo>();
                                                                                cardlist.add(card);
                                                                                cardgrp.setCard(cardlist);
                                                                                cardgrp.setCardGroupOverview(false);
                                                                                // cardgrouplist = cardgrplist_check;
                                                                                cardgrouplist.add(cardgrp);
                                                                            }
                                                                            if (cardGroupOverview == false) {
                                                                                acc.setCardGroup(cardgrouplist);
                                                                                //Replace the old account with the new one account obj
                                                                                for (int z = 0; z < accountlist.size(); z++)
                                                                                    if (accountlist.get(z).getAccountNumber().equals(acc.getAccountNumber())) {
                                                                                        //accountInterchange = accountlist.get(z);
                                                                                        accountlist.set(z, acc);
                                                                                    }
                                                                                part.setAccountList(accountlist);
                                                                                break;
                                                                            }
                                                                        }
                                                                    }
                                                                    if (addflagaccount == true) {
                                                                        //that means either account list is 0 or account doesnot exists in accountlist

                                                                        cardgrouplist = new ArrayList<CardGroupInfo>();
                                                                        cardlist = new ArrayList<CardInfo>();
                                                                        cardlist.add(card);
                                                                        cardgrp.setCard(cardlist);
                                                                        cardgrp.setCardGroupOverview(false);
                                                                        cardgrouplist.add(cardgrp);
                                                                        acc.setCardGroup(cardgrouplist);
                                                                        acc.setAccountOverview(false);
                                                                        accountlist.add(acc);
                                                                        part.setAccountList(accountlist);
                                                                        // partnerListSession.add(part);
                                                                    }
                                                                }
                                                            }
                                                        idlist++;
                                                    } while (idlist < user.getRoleList().get(i).getIdString().size());
                                                    //cardlist
                                                    if (accountOverView != true) {
                                                        //                                        session.setAttribute("Partner_Object_List",
                                                        //                                                             partnerListSession);
                                                        //                                        accountlist.clear();
                                                        //                                            cardgrouplist.clear();
                                                        //                                            cardlist.clear();
                                                        //                                            partnerlist.clear();
                                                        //                                        System.out.println("partner list added");
                                                        //
                                                        //
                                                        //                                        System.out.println("session not null");
                                                        //                                        session.setAttribute("executePartnerObjLogic",
                                                        //                                                             "no");
                                                        //                                        System.out.println("Session variable added");
                                                    }
                                                }
                                            }
                                            partnerListSession.add(part);
                                        }
                                    }
                                    if (session != null) {
                                        session.setAttribute("Partner_Object_List", partnerListSession);

                                        //                                            accountlist.clear();
                                        //                                                cardgrouplist.clear();
                                        //                                                cardlist.clear();
                                        //                                                partnerlist.clear();
                                        System.out.println("partner list added");
                                        System.out.println("session not null");
                                        session.setAttribute("executePartnerObjLogic", "no");
                                        System.out.println("Session variable added");
                                        System.out.println("flag value changed now");

                                    }
                                }
                            }
                        }
                }
                System.out.println("Exit From Binding phase");
                // }
            }
            if (phase == PhaseId.RESTORE_VIEW.getOrdinal()) {
                if (!AdfFacesContext.getCurrentInstance().isPostback()) {
                    FacesContext facesCtx = FacesContext.getCurrentInstance();
                    String currentViewId = facesCtx.getViewRoot().getViewId();
                    System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".beforePhase : " + "facesCtx.getViewRoot().getViewId():: " +
                                       currentViewId);
                    // if user is authenticated and requested for sign in page then redirect to home page
                    securityContext = ADFContext.getCurrent().getSecurityContext();
                    if (currentViewId.contains("login") && securityContext.isAuthenticated()) {
                        System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".beforePhase : " + "Inside OAM authenticated");
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
        System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".setUserInfoInSession : " +
                           "MyPageListener.setUserInfoInSession: OPSS called for user: " + securityContext.getUserName());
        List<User> userList = new ArrayList<User>();
        boolean success = false;
        userClient = new UserClient();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 3 && success == false; i++) {
            //TODO : Amit - This will be removed when integrated with IDAM for OPSS call.
            if (request.getServerName().contains("101") || request.getServerName().contains("localhost")) {
                //TODO : Amit - Users to be fetched from IDM for actual testing.
                userList.add(populateUser(Constants.ROLE_WCP_CARD_B2B_ADMIN));
            } else {
                userList = userClient.searchUserWithUserId(securityContext.getUserName());
            }
            long elapsedTime = System.currentTimeMillis() - startTime;
            session.setAttribute("SESSION_USER_ERROR", "");
            if (securityContext.getUserName().equalsIgnoreCase("weblogic") || securityContext.getUserName().equalsIgnoreCase("wcpadmin")) {
                success = true;
            } else {
                success = validateOPSSCall(userList, session);
            }
            if (!success) {
                return;
            } else {
                //TODO : Didnt get purpose of this block? This else can be removed if not needed.
                String methodName = this.getClass().toString() + ".setUserInfoInSession";
            }
        }

        System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".setUserInfoInSession : " + "securityContext.getUserName() = <" +
                           securityContext.getUserName() + ">");
        session.setAttribute(Constants.SESSION_USER_NAME, securityContext.getUserName());


        if (userList != null)
            System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".setUserInfoInSession : " + "userlist size " + userList.size());
        else
            System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + "userlist size is null");

        if (userList != null && !userList.isEmpty()) {
            //TODO : We are setting 1st user value from the List. Is that right ?
            //Do we get multiple results from OPSS call while searching using UserName ?
            //If we end up with multiple results for given username, we may always pick the 1st item in the object, which could be incorrect.
            setUser(userList.get(0));
            if (getUser() != null) {
                session.setAttribute(Constants.SESSION_USER_INFO, user);
                session.setAttribute(Constants.userLang, conv.getLangForWERCSURL(user.getLang().toString()));
                System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".setUserInfoInSession : " + "user.getUserID = <" +
                                   user.getUserID() + ">");
                session.setAttribute(Constants.SESSION_USER_DISPLAY_NAME, user.getFirstName());
                if (user.getRolelist() != null && !user.getRolelist().isEmpty()) {
                    System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() +
                                       "Checking user get role list is proper or not===================>");
                    System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + "RoleList size      -----------" + user.getRoleList().size());
                    System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + "RoleName " + user.getRoleList().get(0).getRoleName());
                    //System.out.println("Role of admin" +
                    //              (Constants.ROLE_WCP_CARD_ADMIN));

                }
            } else {
                System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".setUserInfoInSession : " +
                                   "MyPageListener.setUserInfoInSession:  User in userList was Null");
            }
        } else {
            System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".setUserInfoInSession : " +
                               " MyPageListener.setUserInfoInSession: userList was either null or empty");
        }
    }

    boolean validateOPSSCall(List<User> liuser, HttpSession session) {
        if (liuser != null && liuser.size() > 0) {
            //TODO : Why liuser ? Are we searching multiple users in a single request from IDM ? Again, from liuser we are only picking 1st value. Plz check.
            //TODO : Also kindly follow naming conventions for variables used across application.
            User us = liuser.get(0);
            if (us != null) {
                if (us.getEmailID() == null) {
                    //                    navigateToErrorPage("ERROR_OPSS");
                    session.setAttribute("SESSION_USER_ERROR", "ERROR_OPSS");
                    System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".validateOPSSCall : " + "User EMAIL is NULL");
                    return false;
                }
                if (us.getRolelist() == null) {
                    //                    navigateToErrorPage("ERROR_OPSS");
                    session.setAttribute("SESSION_USER_ERROR", "ERROR_OPSS");
                    System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".validateOPSSCall : " + "User ROLELIST is NULL");
                    return false;
                } else {
                    if (us.getRolelist().contains("WCP_Administrators") || us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_SFR) ||
                        us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_JET) || us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN) ||
                        us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) || us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_PETRO) ||
                        us.getRolelist().contains(Constants.ROLE_WCP_ICSR) || us.getRolelist().contains(Constants.ROLE_WCP_ISSM) ||
                        us.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_EMP) || us.getRolelist().contains(Constants.ROLE_WCP_CARD_ADMIN) ||
                        us.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR) || us.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)) {
                        System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".validateOPSSCall : " + "Proper roles found");
                    } else {
                        //                        navigateToErrorPage("ERROR_NOROLE");
                        session.setAttribute("SESSION_USER_ERROR", "ERROR_NOROLE");
                        System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".validateOPSSCall : " + "Roles found are:" +
                                           us.getRolelist() + " You are not authorized to enter Partner Portal.");
                        return false;
                    }
                }
            } else {
                //                navigateToErrorPage("ERROR_OPSS");
                session.setAttribute("SESSION_USER_ERROR", "ERROR_OPSS");
                System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".validateOPSSCall : " + "User OBJECT is NULL");
                return false;
            }
        } else {
            session.setAttribute("SESSION_USER_ERROR", "ERROR_OPSS");
            System.out.println(AccessDataControl.getDisplayRecord() + this.getClass() + ".validateOPSSCall : " + "user LIST is NULL");
            return false;
        }
        return true;
    }

    //TODO : Amit - Below method to be deleted before moving to Dev or highler envs.

    private void executeAdmin(User user, int partIndex) {
        //Since the user logged in has Admin role so not much handling / filtering is required at this point
        //1.Take partner id as request for Account View Object , 2.Execute Account VO, 3.Take the account id from Account View Object result, 4.Pass it to CardGroup View Object,
        //5.Execute CardGroup VO, 6.Take the CardGroup View id from CardGroup View Object result, 7.Pass it to Card View Object, 8.Execute Card VO
        //Prepare Cardlist from Card VO,
        //Prepare CardGroup list from CardGroup VO
        //Prepare Accountlist from Account VO

        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = request.getSession(false);

        System.out.println(accessDC.getDisplayRecord() + this.getClass() + " Entered in execute Admin function");
        part = new PartnerInfo();
        accountlist = new ArrayList<AccountInfo>();
        cardgrouplist = new ArrayList<CardGroupInfo>();
        cardlist = new ArrayList<CardInfo>();

        System.out.println(accessDC.getDisplayRecord() + this.getClass() + "  partIndex ----> " + partIndex);
        String Partnerid = partnerlist.get(partIndex).getPartnerValue();

        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();

        DCIteratorBinding iter1;
        if (bindings != null) {
            iter1 = bindings.findIteratorBinding("PrtAccountVO1Iterator");
        } else {
            System.out.println(accessDC.getDisplayRecord() + this.getClass() + " PrtAccountVO1Iterator Bindings is null in my page listner for Admin role");
            iter1 = null;
        }
        System.out.println(accessDC.getDisplayRecord() + this.getClass() + " Partner id passed in Account VO is " + Partnerid);

        part.setPartnerValue(Partnerid);
        part.setPartnerName(getPartnerName(Partnerid));
        part.setCountry(conv.getLangForWERCSURL(user.getLang().toString()));
        part.setCompanyOverview(true);

        ViewObject accountVO = iter1.getViewObject();
        accountVO.setWhereClause("PARTNER_ID =: pid");
        accountVO.defineNamedWhereClauseParam("pid", Partnerid, null);
        accountVO.setNamedWhereClauseParam("countryCode", (conv.getLangForWERCSURL(user.getLang().toString())));
        System.out.println(accountVO.getQuery());
        accountVO.executeQuery();
        System.out.println(accessDC.getDisplayRecord() + this.getClass() + " RowCount for Account VO  " + accountVO.getEstimatedRowCount());

        if (accountVO.getEstimatedRowCount() != 0) {
            while (accountVO.hasNext()) {
                acc = new AccountInfo();
                cardgrouplist = new ArrayList<CardGroupInfo>();
                PrtAccountVORowImpl currRow = (PrtAccountVORowImpl)accountVO.next();
                if (currRow != null) {
                    if (currRow.getAccountId() != null) {
                        System.out.println(accessDC.getDisplayRecord() + this.getClass() + " Result from Execution of Account VO : account id is " +
                                           currRow.getAccountId());
                        acc.setAccountNumber(currRow.getAccountId().toString());
                        acc.setAccountOverview(true);
                    }

                    //Below logic is just to find out that account id is already present in accountlist
                    //TODO : HITK : This can be avoided since at DB level we have already put the constraint to avoid duplicatre records.....
                    addflagaccount = false;
                    //                    for (int k = 0; k < accountlist.size(); k++) {
                    //                        System.out.println("account id value in account list " +
                    //                                           accountlist.get(k).getAccountNumber());
                    //                        System.out.println("New account id value to compare" +
                    //                                           acc.getAccountNumber());
                    //
                    //
                    //                        if (accountlist.get(k).getAccountNumber().equalsIgnoreCase(acc.getAccountNumber())) {
                    //                            System.out.println("account id already exists in account list");
                    //                            addflagaccount = true;
                    //                                            }
                    //                    }        break;


                }
                String AccountID = acc.getAccountNumber();
                bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding iter2;
                if (bindings != null) {
                    iter2 = bindings.findIteratorBinding("PrtCardgroupVO1Iterator");

                } else {
                    System.out.println(accessDC.getDisplayRecord() + this.getClass() +
                                       " PrtCardgroupVO1Iterator Bindings is null in my page listner for Admin role");
                    iter2 = null;
                }
                ViewObject cardGroupVO = iter2.getViewObject();
                System.out.println(" checkinng cardgroup query " + cardGroupVO.getWhereClause());

                if ("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub".equalsIgnoreCase(cardGroupVO.getWhereClause())) {
                    System.out.println(" checking this ");
                    cardGroupVO.removeNamedWhereClauseParam("cgid");
                    cardGroupVO.removeNamedWhereClauseParam("cc");
                    cardGroupVO.removeNamedWhereClauseParam("cgmain");
                    cardGroupVO.removeNamedWhereClauseParam("cgsub");
                    cardGroupVO.setWhereClause("");
                    cardGroupVO.executeQuery();
                }
                cardGroupVO = iter2.getViewObject();
                cardGroupVO.setWhereClause("ACCOUNT_ID =: accid AND COUNTRY_CODE =: cc");
                //                System.out.println(accessDC.getDisplayRecord() +  this.getClass() + " Account id passed in CardGroup VO is " + AccountID);
                cardGroupVO.defineNamedWhereClauseParam("accid", AccountID, null);
                cardGroupVO.defineNamedWhereClauseParam("cc", (String)session.getAttribute(Constants.userLang), null);

                cardGroupVO.executeQuery();
                System.out.println(accessDC.getDisplayRecord() + this.getClass() + " Row count from Cardgroup vo" + cardGroupVO.getEstimatedRowCount());

                if (cardGroupVO.getEstimatedRowCount() != 0) {
                    while (cardGroupVO.hasNext()) {
                        cardgrp = new CardGroupInfo();
                        cardlist = new ArrayList<CardInfo>();
                        PrtCardgroupVORowImpl currRowcardgrp = (PrtCardgroupVORowImpl)cardGroupVO.next();

                        if (currRowcardgrp != null) {
                            if (currRowcardgrp.getCardgroupSeq() != null) {
                                //                                System.out.println(accessDC.getDisplayRecord() +  this.getClass() + "Result from Execution of CardGroup VO: Cardgroup id is " +
                                //                                                   (currRowcardgrp.getCardgroupMainType().toString().concat(currRowcardgrp.getCardgroupSubType().toString())).concat(currRowcardgrp.getCardgroupSeq().toString()));

                                cardgrp.setCardGroupID((currRowcardgrp.getCardgroupMainType().toString().concat(currRowcardgrp.getCardgroupSubType().toString())).concat(currRowcardgrp.getCardgroupSeq().toString()));
                                cardgrp.setCardGroupMainType(currRowcardgrp.getCardgroupMainType().toString());
                                cardgrp.setCardGroupSeq(currRowcardgrp.getCardgroupSeq().toString());
                                cardgrp.setCardGroupSubType(currRowcardgrp.getCardgroupSubType().toString());
                                if (currRowcardgrp.getCardgroupDescription() != null) {
                                    cardgrp.setCardGroupName(currRowcardgrp.getCardgroupDescription().toString());
                                } else {
                                    cardgrp.setCardGroupName(cardgrp.getCardGroupID());
                                }

                                cardgrp.setCardGroupOverview(true);
                            }

                            addflagcardgroup = false;
                            //Below logic is just to find out that cardgroup id is already present in cardgrouplist
                            //TODO : HITK : This can be avoided since at DB level we have already put the constraint to avoid duplicatre records.....
                            //                            for (int k = 0; k < cardgrouplist.size(); k++) {
                            //                                System.out.println("cardgroup id value in cardgroup list " +
                            //                                                   cardgrouplist.get(k).getCardGroupID());
                            //                                System.out.println("New cardgroup id value to compare" +
                            //                                                   cardgrp.getCardGroupID());
                            //
                            //
                            //                                if (cardgrouplist.get(k).getCardGroupID().equalsIgnoreCase(cardgrp.getCardGroupID())) {
                            //                                    System.out.println("cardgroup id already exists in cardgroup list");
                            //                                    addflagcardgroup = true;
                            //                                    break;
                            //                                }
                            //                            }


                        }


                        String CardgroupMainType = cardgrp.getCardGroupMainType().toString();
                        String CardgroupSubType = cardgrp.getCardGroupSubType().toString();
                        String CardgroupSeq = cardgrp.getCardGroupSeq().toString();
                        bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                        DCIteratorBinding iter3;
                        if (bindings != null) {
                            iter3 = bindings.findIteratorBinding("PrtCardVO1Iterator");

                        } else {
                            System.out.println(accessDC.getDisplayRecord() + this.getClass() +
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
                        System.out.println(accessDC.getDisplayRecord() + this.getClass() + " CardGroup id passed in Card VO is " + CardgroupMainType +
                                           CardgroupSubType + CardgroupSeq);

                        cardVO.defineNamedWhereClauseParam("cgid", CardgroupSeq, null);
                        cardVO.defineNamedWhereClauseParam("cc", (conv.getLangForWERCSURL(user.getLang().toString())), null);
                        cardVO.defineNamedWhereClauseParam("cgmain", CardgroupMainType, null);
                        cardVO.defineNamedWhereClauseParam("cgsub", CardgroupSubType, null);
                        cardVO.defineNamedWhereClauseParam("acid", AccountID, null);


                        cardVO.executeQuery();
                        System.out.println(accessDC.getDisplayRecord() + this.getClass() + " Row count from Card vo" + cardVO.getEstimatedRowCount());
                        if (cardVO.getEstimatedRowCount() != 0) {
                            cardViewObject(cardVO);
                        }
                        if (!addflagcardgroup) {
                            cardgrp.setCard(cardlist);
                            cardgrouplist.add(cardgrp);
                        }
                    }
                }

                if (!addflagaccount) {
                    acc.setCardGroup(cardgrouplist);
                    accountlist.add(acc);
                    part.setAccountList(accountlist);
                }
            }
        }
        partnerListSession.add(part);
        if (session != null) {
            if (session.getAttribute("executePartnerObjLogic") == null) {
                session.setAttribute("Partner_Object_List", partnerListSession);
                System.out.println(accessDC.getDisplayRecord() + this.getClass() + " Partner object added in session");
            }
        }
        System.out.println(accessDC.getDisplayRecord() + this.getClass() + " Exit from execute Admin function");
    }

    private void cardViewObject(ViewObject cardVO) {
        //        System.out.println(accessDC.getDisplayRecord() +  this.getClass() + " Entered in cardViewObject function");
        while (cardVO.hasNext()) {
            card = new CardInfo();
            PrtCardVORowImpl currRowcard = (PrtCardVORowImpl)cardVO.next();
            if (currRowcard != null) {
                if (currRowcard.getPrtCardPk() != null) {
                    //                    System.out.println(accessDC.getDisplayRecord() +  this.getClass() + " Result from Card Vo : Card id is " +
                    //                                                           currRowcard.getPrtCardPk());

                    card.setCardID(currRowcard.getPrtCardPk().toString());
                    card.setCardOverview(true);
                }

                if (currRowcard.getCardType() != null) {
                    cardTypeHS.add(currRowcard.getCardType().toString());
                }

                if (currRowcard.getCardEmbossNum() != null)
                    card.setExternalCardID(currRowcard.getCardEmbossNum().toString());
                addflagcard = false;
                //Below logic is just to find out that card id is already present in cardgrouplist
                //TODO : HITK : This can be avoided since at DB level we have already put the constraint to avoid duplicatre records.....
                //                for (int k = 0; k < cardlist.size(); k++) {
                //                    System.out.println("cardgroup id value in cardgroup list " +
                //                                       cardlist.get(k).getCardID());
                //                    System.out.println("New cardgroup id value to compare" +
                //                                       card.getCardID());
                //
                //
                //                    if (cardlist.get(k).getCardID().equalsIgnoreCase(card.getCardID())) {
                //                        System.out.println("card id already exists in card list");
                //                        addflagcard = true;
                //                        break;
                //                    }
                //                }

            }
            if (!addflagcard)
                cardlist.add(card);
        }
        System.out.println(accessDC.getDisplayRecord() + this.getClass() + " Exit from cardViewObject function");
    }

    public String getPartnerName(String partnerid) {
        String partnerName = "";
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter1;
        if (bindings != null && bindings.findIteratorBinding("PrtPartnerVO1Iterator") != null) {
            iter1 = bindings.findIteratorBinding("PrtPartnerVO1Iterator");
            ViewObject partnerVO = iter1.getViewObject();
            partnerVO.setWhereClause("PARTNER_ID =: pid AND COUNTRY_CODE =: countryCode");
            //            System.out.println(accessDC.getDisplayRecord() + this.getClass() + " Partner id passed in Partner VO is " + partnerid);
            partnerVO.defineNamedWhereClauseParam("pid", partnerid, null);
            partnerVO.defineNamedWhereClauseParam("countryCode", (conv.getLangForWERCSURL(user.getLang().toString())), null);
            partnerVO.executeQuery();
            //            System.out.println(accessDC.getDisplayRecord() + this.getClass() + " RowCount for Partner VO  " + partnerVO.getEstimatedRowCount());
            if (partnerVO.getEstimatedRowCount() > 0) {
                PrtPartnerVORowImpl currRowcard = (PrtPartnerVORowImpl)partnerVO.next();
                if (currRowcard != null) {
                    if (currRowcard.getCompanyName() != null) {
                        partnerName = currRowcard.getCompanyName().toString();
                    }
                }
            }
            if ("PARTNER_ID =: pid AND COUNTRY_CODE =: countryCode".equalsIgnoreCase(partnerVO.getWhereClause())) {
                partnerVO.removeNamedWhereClauseParam("pid");
                partnerVO.removeNamedWhereClauseParam("countryCode");
                partnerVO.setWhereClause("");
                partnerVO.executeQuery();
            }
        } else {
            System.out.println(accessDC.getDisplayRecord() + this.getClass() + " PrtPartnerVO1Iterator Bindings is null in my page listner");
            iter1 = null;
        }
        return partnerName;
    }

    public String getcardGroupName(String CardgroupMainType, String CardgroupSubType, String CardgroupSeq) {
        String cardGroupName = "";
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter1;

        if (bindings != null) {
            iter1 = bindings.findIteratorBinding("PrtCardgroupVO1Iterator");
            ViewObject cardGroupVO = iter1.getViewObject();
            System.out.println(" Checking query " + cardGroupVO.getWhereClause());
            if ("ACCOUNT_ID =: accid AND COUNTRY_CODE =: cc".equalsIgnoreCase(cardGroupVO.getWhereClause())) {
                System.out.println(" EQual matches");
                cardGroupVO.removeNamedWhereClauseParam("accid");
                cardGroupVO.removeNamedWhereClauseParam("cc");
                cardGroupVO.setWhereClause("");
                cardGroupVO.executeQuery();
            }
            cardGroupVO.setWhereClause("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub");
            System.out.println(accessDC.getDisplayRecord() + this.getClass() + " cardGroup id passed in cardGroup VO is " + CardgroupMainType + " " +
                               CardgroupSubType + " " + CardgroupSeq);

            cardGroupVO.defineNamedWhereClauseParam("cgid", CardgroupSeq, null);
            cardGroupVO.defineNamedWhereClauseParam("cc", (conv.getLangForWERCSURL(user.getLang().toString())), null);
            cardGroupVO.defineNamedWhereClauseParam("cgmain", CardgroupMainType, null);
            cardGroupVO.defineNamedWhereClauseParam("cgsub", CardgroupSubType, null);
            cardGroupVO.executeQuery();
            System.out.println(accessDC.getDisplayRecord() + this.getClass() + " RowCount for cardGroupVO  " + cardGroupVO.getEstimatedRowCount());

            if (cardGroupVO.getEstimatedRowCount() > 0) {
                PrtCardgroupVORowImpl currRowcard = (PrtCardgroupVORowImpl)cardGroupVO.next();
                if (currRowcard != null) {
                    if (currRowcard.getCardgroupDescription() != null) {
                        cardGroupName = currRowcard.getCardgroupDescription().toString();
                        System.out.println(accessDC.getDisplayRecord() + this.getClass() + " cardGroupName returned from database for cardGroupid " +
                                           CardgroupMainType + " " + CardgroupSubType + " " + CardgroupSeq + " is " + cardGroupName);
                    }
                }
            }
        } else {
            System.out.println(accessDC.getDisplayRecord() + this.getClass() + " PrtCardGroupVO1Iterator Bindings is null in my page listner");
            iter1 = null;
        }
        return cardGroupName;
    }

    private User populateUser(String role) {
        User user = new User();
        user.setFirstName("Hanif");
        user.setLastName("Merchant");
        user.setLang("no_NO");
        //        user.setEmailID("h.m@m.h");
        //  user.setRolelist(Constants.ROLE_WCP_CARD_ADMIN);
        //user.setPosition(Constants.ROLE_WCP_B2BM);
        user.setAuthenticated(true);
        user.setDob(new Date());


        List<Roles> listrole = new ArrayList<Roles>();
        Roles rr = new Roles();
        List<String> idString = new ArrayList<String>();


        //        rr= new Roles();


        //                rr= new Roles();
        //                idString = new ArrayList<String>();
        rr.setRoleName(Constants.ROLE_WCP_CARD_B2B_ADMIN);
        idString.add("NOPP26773218");
        idString.add("NOPP26773219");
        rr.setIdString(idString);
        listrole.add(rr);


        rr = new Roles();
        rr.setRoleName(Constants.ROLE_WCP_CARD_B2B_MGR);
        idString = new ArrayList<String>();
        idString.add("NOPP26773218CGSLUTRX00001");
        //////
        idString.add("NOPP26773219CGSLUTRX00003");
        idString.add("NOPP26773219AC0022883799");
        idString.add("NOPP26773218CGSLUTRX00006");
        rr.setIdString(idString);
        listrole.add(rr);

        //

        //        rr = new Roles();
        //                        rr.setRoleName(Constants.ROLE_WCP_CARD_B2B_MGR);
        //                        idString = new ArrayList<String>();
        //                        idString.add("NOPP26773218CGSLUTRX00001");
        //
        //                        idString.add("NOPP26773218CGSLUTRX00002");
        //        idString.add("NOPP26773218AC0022883797");
        //                idString.add("NOPP26773218CGSLUTRX00006");
        //                        rr.setIdString(idString);
        //                        listrole.add(rr);

        //                rr = new Roles();
        //                                rr.setRoleName(Constants.ROLE_WCP_CARD_B2B_MGR);
        ////                                idString = new ArrayList<String>();
        //                                idString.add("NOPP26773218CGSLUTRX00001");
        //                //
        //                                idString.add("NOPP26773218CGSLUTRX00002");
        //                //        idString.add("NOPP26773218AC0022883797");
        //        //                idString.add("NOPP26773218CGSLUTRX00006");
        //                                rr.setIdString(idString);
        //                                listrole.add(rr);

        //                rr = new Roles();
        //                rr.setRoleName(Constants.ROLE_WCP_CARD_B2B_MGR);
        //                idString = new ArrayList<String>();
        //                idString.add("NOPP26773218AC0022883797");
        ////                idString.add("NOPP26773218AC0022883898");
        //        ////        idString.add("NOPP26773218AC0022883797");
        //                rr.setIdString(idString);
        //                listrole.add(rr);
        //
        rr = new Roles();
        rr.setRoleName(Constants.ROLE_WCP_CARD_B2B_EMP);
        idString = new ArrayList<String>();
        //////
        idString.add("NOPP26773218CC0058589246");
        ////                idString.add("NOPP26773218CC0058589248");
        idString.add("NOPP26773219CC0058589215");
        ////                idString.add("NOPP26773218CC0058589003");
        rr.setIdString(idString);
        listrole.add(rr);

        //        rr = new Roles();
        //                rr.setRoleName(Constants.ROLE_WCP_CARD_CSR);
        //                                idString = new ArrayList<String>();
        //        //
        //                        idString.add("101");
        ////                idString.add("NOPP26773218CC0058589248");
        //        //
        //        //                idString.add("NOPP26773218CC0058589003");
        //                rr.setIdString(idString);
        //                listrole.add(rr);


        user.setRoleList(listrole);
        //                user.setRolelist(Constants.ROLE_WCP_CARD_ADMIN + "|" +
        //                               Constants.ROLE_WCP_CARD_B2B_EMP);

        user.setRolelist(Constants.ROLE_WCP_CARD_B2B_MGR);
        user.setUserID("B2BMgr1@test.com");

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

}

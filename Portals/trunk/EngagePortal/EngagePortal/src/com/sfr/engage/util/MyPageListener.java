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
import com.sfr.services.core.dao.factory.DAOFactory;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;

import java.util.ArrayList;
import java.util.Date;
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
import oracle.adf.share.security.SecurityContext;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.ViewObject;

import oracle.webcenter.portalframework.sitestructure.SiteStructure;
import oracle.webcenter.portalframework.sitestructure.SiteStructureContext;


//import com.sfr.engage.core.PartnerListInfo;

//import com.sfr.engage.model.queries.uvo.PrtAccountVORowImpl;


public class MyPageListener implements PagePhaseListener {
    private User user = null;
    private boolean passwordChangeRequired;
    private List<AccountInfo> accountlist_session = null;
    private List<CardGroupInfo> cardgrouplist_session = null;
    private List<CardInfo> cardlist_session = null;
    private List<PartnerInfo> partnerlist;
    private HttpServletRequest request;
    private ExternalContext ectx;
    private HttpSession session;


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
                    session =
                            request.getSession(true); //if session is null then create new session

                    System.out.println(AccessDataControl.getDisplayRecord() +
                                       this.getClass() + ".beforePhase : " +
                                       "Session is NULL" +
                                       " New Session ID: " + session.getId());
                }

            }


            if (phase == 9) {
                // check if user is authenticated, read user information and set in session
                System.out.println(AccessDataControl.getDisplayRecord() +
                                   this.getClass() + ".beforePhase : " +
                                   "IN PHASE 9 ");
                if (securityContext.isAuthenticated()) {
                    if (request.getParameter(Constants.SESSION_LANGUAGE) !=
                        null &&
                        (request.getParameter(Constants.SESSION_LANGUAGE).toString().equalsIgnoreCase(Constants.LANGUAGE_ENGLISH))) {

                        session.setAttribute(Constants.SESSION_ORIGINAL_LANGUAGE,
                                             request.getParameter(Constants.SESSION_LANGUAGE));

                        if (session.getAttribute(Constants.SESSION_LANGUAGE) ==
                            null) {
                            session.setAttribute(Constants.SESSION_LANGUAGE,
                                                 Constants.LANGUAGE_ENGLISH);
                            System.out.println("First login session PP:PH9:MPL: Language:" +
                                               session.getAttribute(Constants.SESSION_LANGUAGE));
                        }
                    } else if (session.getAttribute(Constants.SESSION_LANGUAGE) ==
                               null) {
                        session.setAttribute(Constants.SESSION_LANGUAGE,
                                             Constants.LANGUAGE_ENGLISH);
                    }

                    if (session.getAttribute(Constants.SESSION_USER_INFO) ==
                        null) {
                        System.out.println(AccessDataControl.getDisplayRecord() +
                                           this.getClass() +
                                           ".beforePhase : " +
                                           "UserInfo in session is null , Fetching the data");
                        setUserInfoInSession(request, session, userClient,
                                             securityContext);
                        boolean usererror =
                            (String)session.getAttribute("SESSION_USER_ERROR") ==
                            "" ? false : true;
                        if (usererror) {
                            return;
                        }

                    } else {
                        if (user == null) {
                            user =
(User)session.getAttribute(Constants.SESSION_USER_INFO);
                            //TODO: Below if loop to be removed once Incident 23951 is fixed.
                            //SOP's added for troubleshooting incorrect Customer Association problem

                            if (null != user.getRolelist()) {
                                System.out.println(AccessDataControl.getDisplayRecord() +
                                                   this.getClass() +
                                                   "Incident 23951 - 1 ->" +
                                                   user.getUserID() +
                                                   user.getEmailID() +
                                                   user.getRolelist());

                            }
                        }
                    }
                }
            }


            if (phase.equals(ADFLifecycle.INIT_CONTEXT_ID)) {

                //    if (!AdfFacesContext.getCurrentInstance().isPostback()) {

                List<PartnerInfo> partnerinfo_list =
                    new ArrayList<PartnerInfo>();
                List<AccountInfo> accountinfo_list =
                    new ArrayList<AccountInfo>();
                List<CardGroupInfo> cardgroupinfo_list =
                    new ArrayList<CardGroupInfo>();
                List<CardInfo> cardinfo_list = new ArrayList<CardInfo>();


                PartnerInfo partnerobj;

                if (user != null) {

                    for (int i = 0; i < user.getRoleList().size(); i++) {

                        if (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_ADMIN) ||
                            (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2C_SFR)) ||
                            (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR)))

                        {
                            System.out.println("CARD AMIN as user role name=======>" +
                                               user.getRoleList().get(i).getRoleName());


                            if (user.getRoleList().get(i).getIdString() !=
                                null) {
                                int idlist = 0;
                                System.out.println("TEMP ------------->" +
                                                   user.getRoleList().get(i).getIdString().size());
                                do {
                                    partnerobj = new PartnerInfo();
                                    System.out.println("Partner id " + idlist +
                                                       " " +
                                                       user.getRoleList().get(i).getIdString().get(idlist));
                                    int pid_start =
                                        user.getRoleList().get(i).getIdString().get(idlist).indexOf("PP");
                                    System.out.println("pid start index " +
                                                       pid_start);
                                    String pid =
                                        user.getRoleList().get(i).getIdString().get(idlist).substring(pid_start +
                                                                                                      2,
                                                                                                      pid_start +
                                                                                                      5);
                                    System.out.println("partner id " + pid);
                                    partnerobj.setPartnerValue(pid);

                                    if (partnerinfo_list.size() > 0) {
                                        System.out.println("partner info list size" +
                                                           partnerinfo_list.size());

                                        for (int k = 0;
                                             k < partnerinfo_list.size();
                                             k++) {
                                            System.out.println("temp for loop pid value in list " +
                                                               partnerinfo_list.get(k).getPartnerValue());
                                        }
                                        boolean addflag = false;
                                        for (int k = 0;
                                             k < partnerinfo_list.size();
                                             k++) {
                                            System.out.println("pid value in list " +
                                                               partnerinfo_list.get(k).getPartnerValue());
                                            System.out.println("New pid value to compare" +
                                                               pid);
                                            if (partnerinfo_list.get(k).getPartnerValue().equalsIgnoreCase(pid)) {
                                                System.out.println("pid already exists in partner list");
                                                addflag = true;
                                                break;
                                            }


                                        }
                                        if (!addflag)
                                            partnerinfo_list.add(partnerobj);
                                    } else {
                                        partnerinfo_list.add(partnerobj);
                                        System.out.println("value setted in partnere info list" +
                                                           partnerobj.getPartnerValue());

                                    }
                                    idlist++;
                                } while (idlist <
                                         user.getRoleList().get(i).getIdString().size());

                            }


                        }


                    }


                    System.out.println("Partner id final list size" +
                                       partnerinfo_list.size());
                    System.out.println("teetette " +
                                       user.getRoleList().get(0).getIdString().get(0));
                    System.out.println("gfgfgf " +
                                       partnerinfo_list.get(0).getPartnerValue());

                    PartnerInfo part = new PartnerInfo();
                    AccountInfo acc = new AccountInfo();
                    ;
                    CardGroupInfo cardgrp = new CardGroupInfo();
                    CardInfo card = new CardInfo();
                    List<AccountInfo> accountlist =
                        new ArrayList<AccountInfo>();
                    List<CardGroupInfo> cardgrouplist =
                        new ArrayList<CardGroupInfo>();
                    List<CardInfo> cardlist = new ArrayList<CardInfo>();
                    List<PartnerInfo> partnerlist =
                        new ArrayList<PartnerInfo>();
                    boolean addflagaccount = false;
                    boolean addflagcardgroup = false;
                    boolean addflagcard = false;


                    if (partnerinfo_list.size() == 1) {
                        partnerlist.add(partnerinfo_list.get(0));
                        part.setPartnerValue(partnerinfo_list.get(0).toString());

                    }


                    for (int i = 0; i < user.getRoleList().size(); i++) {

                        if (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_ADMIN))

                        {
                            //   partnerinfo_list = new ArrayList<PartnerInfo>();
                            System.out.println("CARD AMIN as user role =======>" +
                                               user.getRoleList().get(i).getRoleName());


                            // if(partnerinfo_list.size() == 1 && user.getRoleList().get(0).getIdString().get(0).contains("PP"+ partnerinfo_list.get(0).getPartnerValue()))
                            //                    {
                            int idlist = 0;
                            System.out.println("TEMP ------------->" +
                                               user.getRoleList().get(i).getIdString().size());
                            do {

                                String Partnerid =
                                    partnerlist.get(0).getPartnerValue();
                                //Execute Account VO wrt to partnerinfo_list.get(0).getPartnerValue() and store the account list in session


                                DCBindingContainer bindings =
                                    (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                                //  DCBindingContainer dc1 = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry().get("pageTemplateBinding");
                                DCIteratorBinding iter1;
                                // if(dc1!= null) {
                                //     iter = bindings.findIteratorBinding("PrtAccountRVO1Iterator");
                                //     System.out.println("DC Iterator bindings for Account found in mypagelistner template");
                                //  }
                                //  else
                                //      System.out.println("no bindings on template");


                                if (bindings != null) {
                                    iter1 =
                                            bindings.findIteratorBinding("PrtAccountVO1Iterator");
                                    System.out.println("DC Iterator bindings for Account found in mypagelistner");
                                } else {
                                    System.out.println("bindings is null");
                                    iter1 = null;
                                }

                                ViewObject vo1 = iter1.getViewObject();
                                vo1.setWhereClause("PARTNER_ID =: pid");
                                System.out.println("Partner id in query " +
                                                   Partnerid);
                                part.setPartnerValue(Partnerid);
                                vo1.defineNamedWhereClauseParam("pid",
                                                                Partnerid,
                                                                null);
                                vo1.setNamedWhereClauseParam("countryCode",
                                                             "no_NO");
                                System.out.println(vo1.getQuery());
                                vo1.executeQuery();
                                System.out.println("row count from account vo " +
                                                   vo1.getEstimatedRowCount());


                                if (vo1.getEstimatedRowCount() != 0) {

                                    System.out.println("RowCount for account VO  " +
                                                       vo1.getEstimatedRowCount());

                                    while (vo1.hasNext()) {
                                        acc = new AccountInfo();
                                        cardgrouplist =
                                                new ArrayList<CardGroupInfo>();


                                        PrtAccountVORowImpl currRow =
                                            (PrtAccountVORowImpl)vo1.next();

                                        if (currRow != null) {
                                            System.out.println(" row != null");

                                            if (currRow.getAccountId() !=
                                                null) {
                                                System.out.println("Account id is " +
                                                                   currRow.getAccountId());
                                                acc.setAccountNumber(currRow.getAccountId().toString());
                                            }

                                            addflagaccount = false;
                                            for (int k = 0;
                                                 k < accountlist.size(); k++) {
                                                System.out.println("account id value in account list " +
                                                                   accountlist.get(k).getAccountNumber());
                                                System.out.println("New account id value to compare" +
                                                                   acc.getAccountNumber());


                                                if (accountlist.get(k).getAccountNumber().equalsIgnoreCase(acc.getAccountNumber())) {
                                                    System.out.println("account id already exists in account list");
                                                    addflagaccount = true;
                                                    break;
                                                }
                                            }


                                        }
                                        //System.out.println("j value is " + j);


                                        //Execute Cardgroup VO wrt to account list from session and store the cardgroup ids list  in session(account list got from above logic)
                                        //Use for loop


                                        String AccountID =
                                            acc.getAccountNumber();
                                        bindings =
                                                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                                        DCIteratorBinding iter2;
                                        if (bindings != null) {
                                            iter2 =
                                                    bindings.findIteratorBinding("PrtCardgroupVO1Iterator");
                                            System.out.println("DC Iterator bindings for Card group found in mypagelistner");
                                        } else {
                                            System.out.println("bindings is null");
                                            iter2 = null;
                                        }
                                        ViewObject vo2 = iter2.getViewObject();

                                        vo2 = iter2.getViewObject();
                                        vo2.setWhereClause("ACCOUNT_ID =: accid");
                                        vo2.defineNamedWhereClauseParam("accid",
                                                                        AccountID,
                                                                        null);


                                        vo2.executeQuery();
                                        System.out.println("row count from cardgroup vo" +
                                                           vo2.getEstimatedRowCount());

                                        if (vo2.getEstimatedRowCount() != 0) {

                                            //System.out.println("RowCount in helpinfo "+vo.getEstimatedRowCount());

                                            while (vo2.hasNext()) {


                                                cardgrp = new CardGroupInfo();
                                                cardlist =
                                                        new ArrayList<CardInfo>();
                                                PrtCardgroupVORowImpl currRowcardgrp =
                                                    (PrtCardgroupVORowImpl)vo2.next();

                                                if (currRowcardgrp != null) {
                                                    System.out.println(" row != null");

                                                    if (currRowcardgrp.getCardgroupSeq() !=
                                                        null) {
                                                        System.out.println("Cardgroup id is " +
                                                                           currRowcardgrp.getCardgroupSeq());
                                                        cardgrp.setCardGroupID(currRowcardgrp.getCardgroupSeq().toString());
                                                    }

                                                    addflagcardgroup = false;
                                                    for (int k = 0;
                                                         k < cardgrouplist.size();
                                                         k++) {
                                                        System.out.println("cardgroup id value in cardgroup list " +
                                                                           cardgrouplist.get(k).getCardGroupID());
                                                        System.out.println("New cardgroup id value to compare" +
                                                                           cardgrp.getCardGroupID());


                                                        if (cardgrouplist.get(k).getCardGroupID().equalsIgnoreCase(cardgrp.getCardGroupID())) {
                                                            System.out.println("cardgroup id already exists in cardgroup list");
                                                            addflagcardgroup =
                                                                    true;
                                                            break;
                                                        }
                                                    }


                                                }


                                                String CardgroupID =
                                                    cardgrp.getCardGroupID();
                                                bindings =
                                                        (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                                                DCIteratorBinding iter3;
                                                if (bindings != null) {
                                                    iter3 =
                                                            bindings.findIteratorBinding("PrtCardVO1Iterator");
                                                    System.out.println("DC Iterator bindings for Card VO found in mypagelistner");
                                                } else {
                                                    System.out.println("bindings is null");
                                                    iter3 = null;
                                                }
                                                ViewObject vo3 =
                                                    iter3.getViewObject();

                                                vo3 = iter3.getViewObject();
                                                vo3.setWhereClause("CARDGROUP_SEQ =: cardgroupid");
                                                vo3.defineNamedWhereClauseParam("cardgroupid",
                                                                                CardgroupID,
                                                                                null);


                                                vo3.executeQuery();
                                                System.out.println("row count from cardgroup vo" +
                                                                   vo3.getEstimatedRowCount());

                                                if (vo3.getEstimatedRowCount() !=
                                                    0) {

                                                    //System.out.println("RowCount in helpinfo "+vo.getEstimatedRowCount());

                                                    while (vo3.hasNext()) {


                                                        card = new CardInfo();
                                                        PrtCardVORowImpl currRowcard =
                                                            (PrtCardVORowImpl)vo3.next();

                                                        if (currRowcard !=
                                                            null) {
                                                            System.out.println(" row != null");

                                                            if (currRowcard.getPrtCardPk() !=
                                                                null) {
                                                                System.out.println("Card id is " +
                                                                                   currRowcard.getPrtCardPk());
                                                                card.setCardID(currRowcard.getPrtCardPk().toString());
                                                            }

                                                            addflagcard =
                                                                    false;
                                                            for (int k = 0;
                                                                 k < cardlist.size();
                                                                 k++) {
                                                                System.out.println("cardgroup id value in cardgroup list " +
                                                                                   cardlist.get(k).getCardID());
                                                                System.out.println("New cardgroup id value to compare" +
                                                                                   card.getCardID());


                                                                if (cardlist.get(k).getCardID().equalsIgnoreCase(card.getCardID())) {
                                                                    System.out.println("card id already exists in card list");
                                                                    addflagcard =
                                                                            true;
                                                                    break;
                                                                }
                                                            }


                                                        }
                                                        if (!addflagcard)
                                                            cardlist.add(card);


                                                    }


                                                }


                                                if (!addflagcardgroup)
                                                //add cardlist in cardgroup

                                                {
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


                                    //Execute Card VO wrt to Cardgroup list and store the card list in session, using for loop


                                    /*                       //Temp hardcoding

                                                        card = new CardInfo();
                                                        card.setCardID("999");



                                                        cardgrp = new CardGroupInfo();
                                                        cardgrp.setCardGroupID("888");
                                                        cardlist = new ArrayList<CardInfo>();
                                                        cardlist.add(card);
                                                        cardgrp.setCard(cardlist);


                                                        acc = new AccountInfo();

                                                        acc.setAccountNumber("501");
                                                        cardgrouplist.add(cardgrp);
                                                        acc.setCardGroup(cardgrouplist);




                                                        part.setPartnerValue(partnerinfo_list.get(i).getPartnerValue());

                                                        accountlist.add(acc);

                                                        part.setAccountList(accountlist);



                                                        partnerlist.add(part); */

                                    //                                                        ectx = FacesContext.getCurrentInstance().getExternalContext();
                                    //                                                                request = (HttpServletRequest)ectx.getRequest();
                                    //                                                                session = request.getSession(false);
                                    //
                                    //                                                        if(session != null)
                                    //                                                        {
                                    //                                                            session.setAttribute("Partner_Object_List", part);
                                    //                                                          System.out.println("partner list added");
                                    //
                                    //                                                        }


                                }
                                idlist++;
                                ectx =
FacesContext.getCurrentInstance().getExternalContext();
                                request =
                                        (HttpServletRequest)ectx.getRequest();
                                session = request.getSession(false);

                                if (session != null) {
                                    session.setAttribute("Partner_Object_List",
                                                         part);
                                    System.out.println("partner list added");

                                }
                            } while (idlist <
                                     user.getRoleList().get(i).getIdString().size());

                            //}


                        } else if (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR))

                        {
                            partnerinfo_list = new ArrayList<PartnerInfo>();
                            //  partnerlist = new ArrayList<PartnerInfo>();
                            if (user.getRoleList().get(i).getIdString() !=
                                null) {

                                int idlist = 0;
                                System.out.println("TEMP ------------->" +
                                                   user.getRoleList().get(i).getIdString().size());


                                System.out.println("Partner id " + idlist +
                                                   " " +
                                                   user.getRoleList().get(i).getIdString().get(idlist));
                                int pid_start =
                                    user.getRoleList().get(i).getIdString().get(idlist).indexOf("PP");
                                System.out.println("pid start index " +
                                                   pid_start);
                                String pid =
                                    user.getRoleList().get(i).getIdString().get(idlist).substring(pid_start +
                                                                                                  2,
                                                                                                  pid_start +
                                                                                                  5);
                                System.out.println("partner id " + pid);

                                if (partnerlist.size() > 0)
                                    if (!partnerlist.get(0).getPartnerValue().equals(pid))

                                    {


                                        part.setPartnerValue(pid);

                                        do {


                                            if (user.getRoleList().get(i).getIdString().get(idlist).contains("AC")) {
                                                //Inside this dont execute any VO since we are geting Partner id and account id from IDM

                                                System.out.println("Account B2B Manager");

                                                //part = new PartnerInfo();
                                                //part.setPartnerValue(partnerinfo_list.get(i).getPartnerValue());

                                                acc = new AccountInfo();
                                                int accid_start =
                                                    user.getRoleList().get(i).getIdString().get(idlist).indexOf("AC");
                                                System.out.println("accid start index " +
                                                                   accid_start);
                                                String accid =
                                                    user.getRoleList().get(i).getIdString().get(idlist).substring(accid_start +
                                                                                                                  2,
                                                                                                                  accid_start +
                                                                                                                  5);
                                                System.out.println("account id " +
                                                                   accid);
                                                acc.setAccountNumber(accid);
                                                //      acc.setCardGroup(arg0);
                                                //                                             List<AccountInfo> accountlist = new ArrayList<AccountInfo>();
                                                accountlist.add(acc);
                                                part.setAccountList(accountlist);
                                                //partnerinfo_list.add(partnerobj);


                                            } else if (user.getRoleList().get(i).getIdString().get(idlist).contains("CG")) {

                                                System.out.println("Card Group B2B Manager");


                                                //  part = new PartnerInfo();
                                                //part.setPartnerValue(partnerinfo_list.get(i).getPartnerValue());


                                                int cgid_start =
                                                    user.getRoleList().get(i).getIdString().get(idlist).indexOf("CG");
                                                System.out.println("cgid start index " +
                                                                   cgid_start);
                                                String cgid =
                                                    user.getRoleList().get(i).getIdString().get(idlist).substring(cgid_start +
                                                                                                                  8);
                                                System.out.println("cardgroup id " +
                                                                   cgid);

                                                //Execute CardgroupVO to fetch corresponding Account number
                                                //check in accountlist if it already exists then do nothing
                                                //else
                                                //acc = new AccountInfo();
                                                //Execute cardVO to fetch corresponding cards

                                                cardgrp = new CardGroupInfo();
                                                card = new CardInfo();
                                                cardgrp.setCardGroupID(cgid);
                                                cardgrouplist.add(cardgrp);
                                                //   card
                                                //      part.

                                                //Execute Cardgrp VO and fetch corressponding accout


                                            }


                                            idlist++;
                                        } while (idlist <
                                                 user.getRoleList().get(i).getIdString().size());

                                    }

                            }

                        }


                    }
                }
            }
            if (phase == PhaseId.RESTORE_VIEW.getOrdinal()) {

                if (!AdfFacesContext.getCurrentInstance().isPostback()) {
                    FacesContext facesCtx = FacesContext.getCurrentInstance();
                    String currentViewId = facesCtx.getViewRoot().getViewId();
                    System.out.println(AccessDataControl.getDisplayRecord() +
                                       this.getClass() + ".beforePhase : " +
                                       "facesCtx.getViewRoot().getViewId():: " +
                                       currentViewId);
                    // if user is authenticated and requested for sign in page then redirect to home page

                    if (currentViewId.contains("login") &&
                        securityContext.isAuthenticated()) {
                        System.out.println(AccessDataControl.getDisplayRecord() +
                                           this.getClass() +
                                           ".beforePhase : " +
                                           "Inside OAM authenticated");
                        session.setAttribute(Constants.SESSION_PRIMARY_REQUEST_PAGE_ID,
                                             "/faces/card/home");
                        String requestedPage =
                            (String)session.getAttribute(Constants.SESSION_PRIMARY_REQUEST_PAGE_ID);
                        ectx.redirect(ectx.getRequestContextPath() +
                                      requestedPage);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUserInfoInSession(HttpServletRequest request,
                                      HttpSession session,
                                      UserClient userClient,
                                      SecurityContext securityContext) throws Exception {
        DAOFactory factory = DAOFactory.getInstance();
        System.out.println(AccessDataControl.getDisplayRecord() +
                           this.getClass() + ".setUserInfoInSession : " +
                           "MyPageListener.setUserInfoInSession: OPSS called for user: " +
                           securityContext.getUserName());
        List<User> userList = new ArrayList<User>();
        boolean success = false;
        userClient = new UserClient();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 3 && success == false; i++) {
            //TODO : Amit - Is this Role list or UserList ? We are populating Role in the userList object. Plz check.
            userList.add(populateUser(Constants.ROLE_WCP_CARD_ADMIN));
            //TODO : Amit - Users to be fetched from IDM for actual testing.
            //userList = userClient.searchUserWithUserId(securityContext.getUserName());
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
            } else {
                //TODO : Didnt get purpose of this block? This else can be removed if not needed.
                String methodName =
                    this.getClass().toString() + ".setUserInfoInSession";
            }
        }

        System.out.println(AccessDataControl.getDisplayRecord() +
                           this.getClass() + ".setUserInfoInSession : " +
                           "securityContext.getUserName() = <" +
                           securityContext.getUserName() + ">");
        session.setAttribute(Constants.SESSION_USER_NAME,
                             securityContext.getUserName());
        
        //TODO : Amit - Remove such SOPs/conditions when functionality is tested. Try incorporating logs/SOPs within required methods. 
        //Below SOPs end up adding to complexitity of the class 
        
        if (userList != null)
            System.out.println("userlist size " + userList.size());
        else
            System.out.println("userlist size is null");

        if (userList != null && !userList.isEmpty()) {
            //TODO : We are setting 1st user value from the List. Is that right ? 
            //Do we get multiple results from OPSS call while searching using UserName ? 
            //If we end up with multiple results for given username, we may always pick the 1st item in the object, which could be incorrect.
            setUser(userList.get(0));
            if (getUser() != null) {
                session.setAttribute(Constants.SESSION_USER_INFO, user);
                System.out.println(AccessDataControl.getDisplayRecord() +
                                   this.getClass() +
                                   ".setUserInfoInSession : " +
                                   "user.getUserID = <" + user.getUserID() +
                                   ">");
                session.setAttribute(Constants.SESSION_USER_DISPLAY_NAME,
                                     user.getFirstName());
                if (user.getRolelist() != null &&
                    !user.getRolelist().isEmpty()) {
                    System.out.println("Checking user get role list is proper or not===================>");
                    System.out.println("RoleList size      -----------" +
                                       user.getRoleList().size());
                    System.out.println("RoleList size" +
                                       user.getRoleList().get(0).getRoleName());
                    System.out.println("Role of admin" +
                                       (Constants.ROLE_WCP_CARD_ADMIN));

                    //logic for role access


                    //   if(partnerinfo_list.size() == 1 && user.getRoleList().get(0).getIdString().get(0).contains("PP"+ partnerinfo_list.get(0).getPartnerValue()))
                    //   {
                    //only for admin logic
                    //    System.out.println("It should work only for admin");
                    //Execute Account VO wrt to partnerinfo_list.get(0).getPartnerValue() and store the account list in session
                    // if (!AdfFacesContext.getCurrentInstance().isPostback())
                    //{

                    //                    FacesContext facesCtx = FacesContext.getCurrentInstance();
                    //                    String currentViewId = facesCtx.getViewRoot().getViewId();

                    //                    if(currentViewId.contains("home.jspx"))
                    //                    {

                    //        System.out.println("User on home page it may go on account summary page so load data now");
                    /*

                    vo.executeQuery();
                    //   System.out.println("row count " + vo.getEstimatedRowCount());


                    accountlist_session = new ArrayList<AccountInfo>();
                    if (vo.getEstimatedRowCount() != 0) {

                        //System.out.println("RowCount in helpinfo "+vo.getEstimatedRowCount());

                        while (vo.hasNext()) {
                            AccountInfo account = new AccountInfo();
                            PrtAccountVORowImpl currRow =
                                (PrtAccountVORowImpl)vo.next();

                            if (currRow != null) {
                                // System.out.println (" row != null");

                                if (currRow.getP != null) {
                                    //  System.out.println("Question is " + currRow.getKeyQuest());
                                    help.setQuestion(currRow.getKeyQuest().toString());
                                }
                                if (currRow.getKeyAnswer() != null) {
                                    //System.out.println("Answer is " + currRow.getKeyAnswer());
                                    help.setAnswer(currRow.getKeyAnswer());
                                }
                                accountlist_session.add(account);
                            }

                        }
                        //System.out.println("j value is " + j);

                    }



                    //Execute Cardgroup VO wrt to account list from session and store the cardgroup ids list  in session(account list got from above logic)



                    //Execute Card VO wrt to Cardgroup list and store the card list in session

                */
                    //}
                    //  }

                    //  }
                    //   else
                    //   {
                    //complex logic


                    //      }


                    //getLanguageForLocalization(session,user.getRolelist());
                }
            } else {
                System.out.println(AccessDataControl.getDisplayRecord() +
                                   this.getClass() +
                                   ".setUserInfoInSession : " +
                                   "MyPageListener.setUserInfoInSession:  User in userList was Null");
            }
        } else {
            System.out.println(AccessDataControl.getDisplayRecord() +
                               this.getClass() + ".setUserInfoInSession : " +
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
                    System.out.println(AccessDataControl.getDisplayRecord() +
                                       this.getClass() +
                                       ".validateOPSSCall : " +
                                       "User EMAIL is NULL");
                    return false;
                }
                if (us.getRolelist() == null) {
                    //                    navigateToErrorPage("ERROR_OPSS");
                    session.setAttribute("SESSION_USER_ERROR", "ERROR_OPSS");
                    System.out.println(AccessDataControl.getDisplayRecord() +
                                       this.getClass() +
                                       ".validateOPSSCall : " +
                                       "User ROLELIST is NULL");
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
                        System.out.println(AccessDataControl.getDisplayRecord() +
                                           this.getClass() +
                                           ".validateOPSSCall : " +
                                           "Proper roles found");
                    } else {
                        //                        navigateToErrorPage("ERROR_NOROLE");
                        session.setAttribute("SESSION_USER_ERROR",
                                             "ERROR_NOROLE");
                        System.out.println(AccessDataControl.getDisplayRecord() +
                                           this.getClass() +
                                           ".validateOPSSCall : " +
                                           "Roles found are:" +
                                           us.getRolelist() +
                                           " You are not authorized to enter Partner Portal.");
                        return false;
                    }
                }
            } else {
                //                navigateToErrorPage("ERROR_OPSS");
                session.setAttribute("SESSION_USER_ERROR", "ERROR_OPSS");
                System.out.println(AccessDataControl.getDisplayRecord() +
                                   this.getClass() + ".validateOPSSCall : " +
                                   "User OBJECT is NULL");
                return false;
            }
        } else {
            session.setAttribute("SESSION_USER_ERROR", "ERROR_OPSS");
            System.out.println(AccessDataControl.getDisplayRecord() +
                               this.getClass() + ".validateOPSSCall : " +
                               "user LIST is NULL");
            return false;
        }
        return true;
    }

    //TODO : Amit - Below method to be deleted before moving to Dev or highler envs.
    private User populateUser(String role) {
        User user = new User();
        user.setFirstName("Hanif");
        user.setLastName("Merchant");
        //        user.setEmailID("h.m@m.h");
        //  user.setRolelist(Constants.ROLE_WCP_CARD_ADMIN);
        //user.setPosition(Constants.ROLE_WCP_B2BM);
        user.setAuthenticated(true);
        user.setDob(new Date());


        List<Roles> listrole = new ArrayList<Roles>();
        Roles rr = new Roles();
        rr.setRoleName(Constants.ROLE_WCP_CARD_ADMIN);
        List<String> idString = new ArrayList<String>();
        idString.add("NOPP101");
        // idString.add("NOPP105");
        rr.setIdString(idString);
        listrole.add(rr);

        //            rr= new Roles();
        //            rr.setRoleName(Constants.ROLE_WCP_CARD_B2B_MGR);
        //            List<String> idString2 = new ArrayList<String>();
        //            idString2.add("NOPP101AC888");
        //            idString2.add("NOPP101AC999");
        //            rr.setIdString(idString2);
        //            listrole.add(rr);

        user.setRoleList(listrole);
        user.setRolelist(Constants.ROLE_WCP_CARD_ADMIN + "|" +
                         Constants.ROLE_WCP_CARD_B2B_MGR);
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

    public void setAccountlist_session(List<AccountInfo> accountlist_session) {
        this.accountlist_session = accountlist_session;
    }

    public List<AccountInfo> getAccountlist_session() {
        return accountlist_session;
    }

    public void setCardgrouplist_session(List<CardGroupInfo> cardgrouplist_session) {
        this.cardgrouplist_session = cardgrouplist_session;
    }

    public List<CardGroupInfo> getCardgrouplist_session() {
        return cardgrouplist_session;
    }

    public void setCardlist_session(List<CardInfo> cardlist_session) {
        this.cardlist_session = cardlist_session;
    }

    public List<CardInfo> getCardlist_session() {
        return cardlist_session;
    }

    public void setPartnerlist(List<PartnerInfo> partnerlist) {
        this.partnerlist = partnerlist;
    }

    public List<PartnerInfo> getPartnerlist() {
        return partnerlist;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setEctx(ExternalContext ectx) {
        this.ectx = ectx;
    }

    public ExternalContext getEctx() {
        return ectx;
    }
}

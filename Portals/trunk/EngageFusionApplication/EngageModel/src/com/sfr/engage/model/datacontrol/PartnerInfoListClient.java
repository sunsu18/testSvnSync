package com.sfr.engage.model.datacontrol;

import com.sfr.core.bean.User;
import com.sfr.engage.core.AccountInfo;
import com.sfr.engage.core.CardGroupInfo;
import com.sfr.engage.core.CardInfo;
import com.sfr.engage.core.PartnerInfo;

import com.sfr.engage.model.queries.uvo.PrtAccountVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtCardVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtCardgroupVORowImpl;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;

import com.sfr.util.validations.Conversion;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.ViewObject;

public class PartnerInfoListClient {
    
    private List<PartnerInfo> partnerlist;
        private HttpServletRequest request;
        private ExternalContext ectx;
        private HttpSession session;
        private User user = null;
    private static boolean flagexecute = false;
    Conversion conv = new Conversion();
        
    public PartnerInfoListClient() {
        super();
    }

    public void setPartnerlist(List<PartnerInfo> partnerlist) {
        this.partnerlist = partnerlist;
    }

    public List<PartnerInfo> getPartnerlist() {
        
        System.out.println("SOP of client");
               partnerlist=new ArrayList<PartnerInfo>();
               PartnerInfo partner = new PartnerInfo();
               
               
               ectx = FacesContext.getCurrentInstance().getExternalContext();
               request = (HttpServletRequest)ectx.getRequest();
               session = request.getSession(false);
               
               if(session!= null) {
                   /*
                   if (user == null) {
                       user =(User)session.getAttribute(Constants.SESSION_USER_INFO);}
                   for (int i = 0; i < user.getRoleList().size(); i++) {
                       
                       
                   }
                   if (null != user.getRolelist()) {
                       System.out.println(AccessDataControl.getDisplayRecord() +
                                          this.getClass() +
                                          "Incident 23951 - 1 ->" +
                                          user.getUserID() +
                                          user.getEmailID() +
                                          user.getRolelist());

                   }
                   
                   List<PartnerInfo> partnerinfo_list =
                       new ArrayList<PartnerInfo>();
                   
                   PartnerInfo partnerobj;
                   
                   if (user != null) {

                       for (int i = 0; i < user.getRoleList().size(); i++) {

                           if (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_ADMIN) ||
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
                                                                                                         10);
                                       System.out.println("partner id " + pid);
                                       partnerobj.setPartnerValue(pid);

                                       if (partnerinfo_list.size() > 0) {
                                           System.out.println("partner info list size" +
                                                              partnerinfo_list.size());
                                           //TODO : Remove unwanted code.
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
                           flagexecute = true;
                           //TODO : To check why Partner value is not setted properly
                           //   part.setPartnerValue(partnerinfo_list.get(0).toString());

                       }


                       for (int i = 0; i < user.getRoleList().size(); i++) {
                           System.out.println("Inside for loop");

                           if (user.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_ADMIN))

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
                                   part.setCountry(conv.getCustomerCountryCode(user.getLang().toString()));
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
                                                           cardgrp.setCardGroupID((currRowcardgrp.getCardgroupMainType().toString().concat(currRowcardgrp.getCardgroupSubType().toString())).concat(currRowcardgrp.getCardgroupSeq().toString()));
                                                           cardgrp.setCardGroupMainType(currRowcardgrp.getCardgroupMainType().toString());
                                                           cardgrp.setCardGroupSeq(currRowcardgrp.getCardgroupSeq().toString());
                                                           cardgrp.setCardGroupSubType(currRowcardgrp.getCardgroupSubType().toString());
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
                                                   String CardgroupMainType =
                                                       cardgrp.getCardGroupMainType().toString();
                                                   String CardgroupSubType =
                                                       cardgrp.getCardGroupSubType().toString();
                                                   String CardgroupSeq =
                                                       cardgrp.getCardGroupSeq().toString();
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
                                                   //TODO : Change hard coded contry code
                                                   vo3 = iter3.getViewObject();
                                                   vo3.setWhereClause("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub");

                                                   vo3.defineNamedWhereClauseParam("cgid",
                                                                                   CardgroupSeq,
                                                                                   null);
                                                   vo3.defineNamedWhereClauseParam("cc",
                                                                                   "no_NO",
                                                                                   null);
                                                   vo3.defineNamedWhereClauseParam("cgmain",
                                                                                   CardgroupMainType,
                                                                                   null);
                                                   vo3.defineNamedWhereClauseParam("cgsub",
                                                                                   CardgroupSubType,
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
                                                                   card.setExternalCardID(currRowcard.getExtCardNum().toString());
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
                               System.out.println("inside card b2b manager");
                               DCBindingContainer bindings;
                               partnerinfo_list = new ArrayList<PartnerInfo>();
                               if (user.getRoleList().get(i).getIdString() !=
                                   null ) {
                                   System.out.println("Rolelist not null");
                                   
                                   if(session!=null)
                                      // if(session.getAttribute("executePartnerObjLogic") != null)
                                       {

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
                                                                                                     10);
                                   System.out.println("partner id " + pid);

                                   if (partnerlist.size() > 0)
                                       if (partnerlist.get(0).getPartnerValue().equals(pid))

                                       {


                                           part.setPartnerValue(pid);

                                           do {


                                               if (user.getRoleList().get(i).getIdString().get(idlist).contains("AC")) {


                                                   System.out.println("Account B2B Manager");

                                                   acc = new AccountInfo();
                                                   int accid_start =
                                                       user.getRoleList().get(i).getIdString().get(idlist).indexOf("AC");
                                                   System.out.println("accid start index " +
                                                                      accid_start);
                                                   String accid =
                                                       user.getRoleList().get(i).getIdString().get(idlist).substring(accid_start +
                                                                                                                     2,
                                                                                                                     accid_start +
                                                                                                                     12);
                                                   System.out.println("account id " +
                                                                      accid);
                                                   acc.setAccountNumber(accid);


                                                   addflagaccount = false;
                                                   for (int k = 0;
                                                        k < accountlist.size();
                                                        k++) {
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
                                                   ViewObject vo2 =
                                                       iter2.getViewObject();

                                                   vo2 = iter2.getViewObject();
                                                   vo2.setWhereClause("ACCOUNT_ID =: accid");
                                                   vo2.defineNamedWhereClauseParam("accid",
                                                                                   AccountID,
                                                                                   null);


                                                   vo2.executeQuery();
                                                   System.out.println("row count from cardgroup vo" +
                                                                      vo2.getEstimatedRowCount());

                                                   if (vo2.getEstimatedRowCount() !=
                                                       0) {

                                                       //System.out.println("RowCount in helpinfo "+vo.getEstimatedRowCount());

                                                       while (vo2.hasNext()) {


                                                           cardgrp =
                                                                   new CardGroupInfo();
                                                           cardlist =
                                                                   new ArrayList<CardInfo>();
                                                           PrtCardgroupVORowImpl currRowcardgrp =
                                                               (PrtCardgroupVORowImpl)vo2.next();

                                                           if (currRowcardgrp !=
                                                               null) {
                                                               System.out.println(" row != null");

                                                               if (currRowcardgrp.getCardgroupSeq() !=
                                                                   null) {
                                                                   System.out.println("Cardgroup id is " +
                                                                                      currRowcardgrp.getCardgroupSeq());
                                                                   cardgrp.setCardGroupID(currRowcardgrp.getCardgroupSeq().toString());
                                                               }

                                                               addflagcardgroup =
                                                                       false;
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

                                                           vo3 =
                   iter3.getViewObject();
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


                                                                   card =new CardInfo();
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
                                                                       for (int k =
                                                                            0;
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

                                                       //TODO : Remove from here add it at the end after completing for CG
                                                       part.setAccountList(accountlist);
                                                   }

                                               } else if (user.getRoleList().get(i).getIdString().get(idlist).contains("CG")) {

                                                   System.out.println("Card Group B2B Manager");

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
                               flagexecute = false;
                               if (session != null) {
                                   System.out.println("session not null");
                                   session.setAttribute("executePartnerObjLogic",
                                                        "no");
                                   System.out.println("Session variable added");
                               }
                               System.out.println("flag value changed now");
                           }


                       }
                   }*/
                   System.out.println("partner list from session");
                   partner = (PartnerInfo)session.getAttribute("Partner_Object_List");
                   
                   System.out.println("partner value from session in string " + partner.getPartnerValue().toString());
                   System.out.println("partner value from session " + partner.getPartnerValue());
                     
                   partnerlist.add(partner);
                   System.out.println("size of list===>"+partnerlist.size());
               }
               else
                   System.out.println("session null in client");
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

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}

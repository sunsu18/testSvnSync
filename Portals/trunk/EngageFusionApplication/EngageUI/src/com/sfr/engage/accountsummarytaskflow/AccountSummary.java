package com.sfr.engage.accountsummarytaskflow;


import com.sfr.engage.core.AccountInfo;
import com.sfr.engage.core.CardGroupInfo;

import com.sfr.engage.core.PartnerInfo;

import com.sfr.engage.model.queries.uvo.PrtCardVORowImpl;


import java.io.Serializable;

import java.util.ArrayList;


import java.util.Iterator;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTree;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;


import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class AccountSummary implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;

    private String overviewText;

    private String id;

    private ArrayList<SelectItem> cardTypeList;

    private String accountId;
    private String cardGroupId;
    private String cardId;
    private String partnerId;

    RowKeySetImpl rksImpl;

    private PartnerInfo partner = new PartnerInfo();
    private AccountInfo account = new AccountInfo();

    private List<AccountInfo> AccountList = new ArrayList<AccountInfo>();
    private List<CardGroupInfo> cardgrouplist = new ArrayList<CardGroupInfo>();
    private HttpServletRequest request;
    private ExternalContext ectx;
    private HttpSession session;
    private boolean displayAccountOverview = false;
    private boolean displayCardGroupOverview = false;
    private transient Bindings bindings;
    JUCtrlHierNodeBinding dropNodeParent;
    JUCtrlHierNodeBinding rootNode;


    public AccountSummary() {

    }

    public void setPartner(PartnerInfo partner) {
        this.partner = partner;
    }

    public PartnerInfo getPartner() {
        return partner;
    }

    public void setAccount(AccountInfo account) {
        this.account = account;
    }

    public AccountInfo getAccount() {
        return account;
    }

    public class Bindings {
        private RichPanelGroupLayout accountOverview;
        private RichPanelGroupLayout cardGroupOverview;
        private RichPanelGroupLayout cardOverview;
        private RichPanelGroupLayout companyOverview;
        private RichPanelGroupLayout restrictedAccess;


        public void setAccountOverview(RichPanelGroupLayout accountOverview) {
            this.accountOverview = accountOverview;
        }

        public RichPanelGroupLayout getAccountOverview() {
            return accountOverview;
        }

        public void setCardGroupOverview(RichPanelGroupLayout cardGroupOverview) {
            this.cardGroupOverview = cardGroupOverview;
        }

        public RichPanelGroupLayout getCardGroupOverview() {
            return cardGroupOverview;
        }

        public void setCardOverview(RichPanelGroupLayout cardOverview) {
            this.cardOverview = cardOverview;
        }

        public RichPanelGroupLayout getCardOverview() {
            return cardOverview;
        }

        public void setCompanyOverview(RichPanelGroupLayout companyOverview) {
            this.companyOverview = companyOverview;
        }

        public RichPanelGroupLayout getCompanyOverview() {
            return companyOverview;
        }

        


        public void setRestrictedAccess(RichPanelGroupLayout restrictedAccess) {
            this.restrictedAccess = restrictedAccess;
        }

        public RichPanelGroupLayout getRestrictedAccess() {
            return restrictedAccess;
        }


       
    }

    public void treeListner(SelectionEvent selectionEvent) {

        if (session != null) {
            System.out.println("partner list from session");
            partner = (PartnerInfo)session.getAttribute("Partner_Object_List");
            AccountList = partner.getAccountList();


            System.out.println("partner value from session in string " +
                               partner.getPartnerValue().toString());
            System.out.println("partner value from session " +
                               partner.getPartnerValue());

        }

        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);


        // Add event code here...

        System.out.println("Inside tree listner");
        JUCtrlHierNodeBinding nodeBinding1 = null;
        Row rw = null;
        String rowType = "";
        RichTree tree1 = (RichTree)selectionEvent.getSource();
        RowKeySet rowKeySet1 = selectionEvent.getAddedSet();

        Iterator rksIterator = rowKeySet1.iterator();
        while (rksIterator.hasNext()) {
            List key1 = (List)rksIterator.next();
            JUCtrlHierBinding treeBinding = null;
            JUCtrlHierBinding treeBinding2 = null;
            CollectionModel collectionModel =
                (CollectionModel)tree1.getValue();
            treeBinding = (JUCtrlHierBinding)collectionModel.getWrappedData();
            treeBinding2 = (JUCtrlHierBinding)collectionModel.getRowData();
            rksImpl = new RowKeySetImpl();
            nodeBinding1 = treeBinding.findNodeByKeyPath(key1);


            rksImpl.add(key1);
            //System.out.println(" rksImpl-------->" +  rksImpl);
            //the first key to add is the node that received the drop
            //operation (departments).
          rootNode = treeBinding.getRootNodeBinding();
            //System.out.println("root node--------->" + rootNode);
           dropNodeParent = nodeBinding1.getParent();
            ///System.out.println("dropNodeParent--------->" + dropNodeParent);


            for (Object o : nodeBinding1.getAttributeValues()) {

                id = o.toString();


            }


            rw = nodeBinding1.getRow();
            rowType = rw.getStructureDef().getDefName();
            //System.out.println("treeSelectionListener : " + ".............." + rowType);
        }

        if (rowType.contains("AccountInfo")) {

            //clicked node belongs to account so execute Account overview
            accountOverview();


        } else if (rowType.contains("CardGroupInfo")) {

            //clicked node belongs to cardGroup so execute cardGroup overview
            cardGroupOverview();

        } else if (rowType.contains("CardInfo")) {
            //clicked node belongs to card so execute card overview
            cardOverview();

        } else {

            //clicked node belongs to partner so execute partner overview
            companyOverview();

        }


    }


    public void setOverviewText(String overviewText) {
        this.overviewText = overviewText;
    }

    public String getOverviewText() {
        return overviewText;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public void setCardTypeList(ArrayList<SelectItem> cardTypeList) {
        this.cardTypeList = cardTypeList;
    }

    public ArrayList<SelectItem> getCardTypeList() {
        return cardTypeList;
    }


    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setCardGroupId(String cardGroupId) {
        this.cardGroupId = cardGroupId;
    }

    public String getCardGroupId() {
        return cardGroupId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setDisplayAccountOverview(boolean displayAccountOverview) {
        this.displayAccountOverview = displayAccountOverview;
    }

    public boolean isDisplayAccountOverview() {
        return displayAccountOverview;
    }

    public void setDisplayCardGroupOverview(boolean displayCardGroupOverview) {
        this.displayCardGroupOverview = displayCardGroupOverview;
    }

    public boolean isDisplayCardGroupOverview() {
        return displayCardGroupOverview;
    }


    public void hideAll() {

        getBindings().getAccountOverview().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccountOverview());
        getBindings().getCompanyOverview().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCompanyOverview());
        getBindings().getCardOverview().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardOverview());
        getBindings().getCardGroupOverview().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupOverview());

    }

    public AccountSummary.Bindings getBindings() {
        if (bindings == null) {
            bindings = new Bindings();
        }
        return bindings;
    }

    public void accountOverview() {

        hideAll();
        System.out.println("Inside AccountOverview function");
        getBindings().getRestrictedAccess().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());

        for (int k = 0; k < AccountList.size(); k++) {
            //            System.out.println("account id value in account list " +
            //                               AccountList.get(k).getAccountNumber());
            //            System.out.println("New account id value to compare" +
            //                               id);


            if (AccountList.get(k).getAccountNumber().equalsIgnoreCase(id)) {
                //                System.out.println("account id exists in account list");
                displayAccountOverview =
                        AccountList.get(k).isAccountOverview();
                break;
            }
        }


        if (displayAccountOverview) {
            System.out.println("Account node clicked, Account Overview is true in partner object & Accountid" +
                               accountId);
            partnerId = dropNodeParent.toString();
            accountId = id;

            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter1;

            if (bindings != null) {
                iter1 = bindings.findIteratorBinding("PrtAccountVO2Iterator");
                //            System.out.println("DC Iterator bindings for Account found in mypagelistner");
            } else {
                System.out.println("account bindings inside account Overview is null");
                iter1 = null;
            }
            //
            if (iter1 != null) {

                ViewObject vo_acc = iter1.getViewObject();
                vo_acc.setWhereClause("ACCOUNT_ID =: accid");
                vo_acc.defineNamedWhereClauseParam("accid", id, null);
                vo_acc.setNamedWhereClauseParam("countryCode", "no_NO");
                System.out.println(vo_acc.getQuery());
                vo_acc.executeQuery();

            }
            getBindings().getAccountOverview().setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccountOverview());


        } else {
            hideAll();
            System.out.println("Account node clicked But Account Overview is false in partner object & Accountid " +
                               accountId);
            getBindings().getRestrictedAccess().setVisible(true);
            //               System.out.println("restricted access visible");
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());
        }
        System.out.println("Exiting AccountOverview function");
    }

    public void cardGroupOverview() {
        System.out.println("Inside cardGroupOverview function");

        hideAll();
        getBindings().getRestrictedAccess().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());


        for (int k = 0; k < AccountList.size(); k++) {
            //            System.out.println("account id value in account list " +
            //                               AccountList.get(k).getAccountNumber());
            account = AccountList.get(k);
            cardgrouplist = account.getCardGroup();

            for (int cardgrp_count = 0; cardgrp_count < cardgrouplist.size();
                 cardgrp_count++) {
                if (cardgrouplist.get(cardgrp_count).getCardGroupID().equalsIgnoreCase(id)) {
                    //                System.out.println("cardgroup id exists in cardgroup list of account " + account.getAccountNumber());
                    displayCardGroupOverview =
                            cardgrouplist.get(cardgrp_count).isCardGroupOverview();
                    break;
                }
            }
        }


        if (displayCardGroupOverview) {

            System.out.println("cardGroup node clicked, cardGroup Overview is true in partner object & cardGroupId " +
                               id);
            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter1;


            if (bindings != null) {
                iter1 =
                        bindings.findIteratorBinding("PrtCardgroupVO3Iterator");
                //            System.out.println("DC Iterator bindings for CardGroup found in mypagelistner");
            } else {
                System.out.println("card group bindings inside cardGroup Overview is null");
                iter1 = null;
            }
            //
            if (iter1 != null) {

                ViewObject vo_cg = iter1.getViewObject();
                String maintype = id.substring(0, 3);
                //        System.out.println("Main type "+maintype);
                String subtype = id.substring(3, 6);
                //        System.out.println("Sub type "+ subtype);
                String cardgroupseq = id.substring(6);
                //        System.out.println("card seq " + cardgroupseq);

                vo_cg.setWhereClause("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub");


                vo_cg.defineNamedWhereClauseParam("cgid", cardgroupseq, null);

                vo_cg.defineNamedWhereClauseParam("cc", "no_NO", null);
                vo_cg.defineNamedWhereClauseParam("cgmain", maintype, null);
                vo_cg.defineNamedWhereClauseParam("cgsub", subtype, null);

                //        System.out.println(vo_cg.getQuery());
                vo_cg.executeQuery();
                //        System.out.println("rows " + vo_cg.getEstimatedRowCount());

                bindings =
                        (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding iter3;
                if (bindings != null) {
                    iter3 = bindings.findIteratorBinding("PrtCardVO4Iterator");
                    //                System.out.println("DC Iterator bindings for Card VO found in mypagelistner");
                } else {
                    System.out.println("card bindings inside cardGroup Overview is null");
                    iter3 = null;
                }

                ViewObject vo3 = iter3.getViewObject();

                if ("PRT_CARD_PK =: cardid AND COUNTRY_CODE =: cc".equalsIgnoreCase(vo3.getWhereClause())) {
                    //                System.out.println("Remove query2 executed");
                    vo3.removeNamedWhereClauseParam("cardid");
                    vo3.removeNamedWhereClauseParam("cc");
                    vo3.setWhereClause("");
                    vo3.executeQuery();
                }


                vo3 = iter3.getViewObject();
                vo3.setWhereClause("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub");
                vo3.defineNamedWhereClauseParam("cgid", cardgroupseq, null);

                vo3.defineNamedWhereClauseParam("cc", "no_NO", null);
                vo3.defineNamedWhereClauseParam("cgmain", maintype, null);
                vo3.defineNamedWhereClauseParam("cgsub", subtype, null);


                vo3.executeQuery();
                //            System.out.println("vo3 estimated rows " + vo3.getEstimatedRowCount());
                if (vo3.getEstimatedRowCount() != 0) {
                    cardTypeList = new ArrayList<SelectItem>();
                    while (vo3.hasNext()) {
                        PrtCardVORowImpl currRow =
                            (PrtCardVORowImpl)vo3.next();
                        if (currRow.getCardType() != null) {
                            SelectItem selectItem = new SelectItem();
                            selectItem.setLabel(currRow.getCardType());
                            selectItem.setValue(currRow.getCardType());
                            cardTypeList.add(selectItem);

                        }
                    }

                    //                System.out.println("Cardtypelist " + cardTypeList.size());

                }
                //            System.out.println("vo3 getWhereClause" + vo3.getWhereClause());


            }

            getBindings().getCardGroupOverview().setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupOverview());


            partnerId =dropNodeParent.getParent().toString();
            cardGroupId = id;
            accountId =dropNodeParent.toString();


        } else {

            hideAll();
            System.out.println("cardGroup node clicked But cardGroup Overview is false in partner object & cardGroupId " +
                               id);
            getBindings().getRestrictedAccess().setVisible(true);
            //            System.out.println("restricted access visible");
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());
        }

        System.out.println("Exiting cardGroupOverview function");

    }

    public void cardOverview() {
        System.out.println("Inside cardOverview function");


        hideAll();
        getBindings().getRestrictedAccess().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());

        System.out.println("card node clicked & cardid " + id);

        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter1;


        if (bindings != null) {
            iter1 = bindings.findIteratorBinding("PrtCardVO4Iterator");
            //            System.out.println("DC Iterator bindings for Card found in Account Summary");
        } else {
            System.out.println("card bindings inside card Overviewis null");
            iter1 = null;
        }
        //
        if (iter1 != null) {

            ViewObject vo_card = iter1.getViewObject();

            if ("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub".equalsIgnoreCase(vo_card.getWhereClause())) {
                //                System.out.println("Remove query executed");
                vo_card.removeNamedWhereClauseParam("cgid");
                vo_card.removeNamedWhereClauseParam("cc");
                vo_card.removeNamedWhereClauseParam("cgmain");
                vo_card.removeNamedWhereClauseParam("cgsub");
                vo_card.setWhereClause("");
                vo_card.executeQuery();
            }

            vo_card.setWhereClause("PRT_CARD_PK =: cardid AND COUNTRY_CODE =: cc");

            vo_card.defineNamedWhereClauseParam("cardid", id, null);

            vo_card.defineNamedWhereClauseParam("cc", "no_NO", null);

            //        System.out.println(vo_card.getQuery());
            vo_card.executeQuery();


        }

        getBindings().getCardOverview().setVisible(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardOverview());


        cardId = id;


        cardGroupId = dropNodeParent.toString();
        dropNodeParent = dropNodeParent.getParent();
        accountId = dropNodeParent.toString();
        partnerId = dropNodeParent.getParent().toString();

        System.out.println("Exiting cardOverview function");
    }

    public void companyOverview() {
        System.out.println("Inside companyOverview function");

        hideAll();
        getBindings().getRestrictedAccess().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());

        //            DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        //            DCIteratorBinding iter1;
        //
        //            if (bindings != null) {
        //                iter1 = bindings.findIteratorBinding("PrtPartnerVO1Iterator");
        //                System.out.println("DC Iterator bindings for Card found in mypagelistner");
        //            } else {
        //                System.out.println("card bindings is null");
        //                iter1 = null;
        //            }
        //            //
        //            if(iter1!=null)
        //            {
        //
        //            ViewObject vo_card = iter1.getViewObject();
        //            vo_part.setWhereClause("PARTNER_ID =: partid");
        //            vo_part.defineNamedWhereClauseParam("partid",id,null);
        //            vo_part.setWhereClause("COUNTRY_CODE =: cc");
        //            vo_part.defineNamedWhereClauseParam("cc","no_NO",null);
        //
        //            System.out.println(vo_part.getQuery());
        //            vo_part.executeQuery();
        //
        //            }


        if (session != null) {
            //        System.out.println("partner list from session");
            partner = (PartnerInfo)session.getAttribute("Partner_Object_List");

            //        System.out.println("partner value from session in string " + partner.getPartnerValue().toString());
            System.out.println("partner value from session " +
                               partner.getPartnerValue());

        }
        if (partner.isCompanyOverview()) {
            System.out.println("partner node clicked, company/partner Overview is true in partner object & partnerId " +
                               partner.getPartnerValue().toString());
            getBindings().getCompanyOverview().setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCompanyOverview());
        } else {
            hideAll();
            System.out.println("partner node clicked But company/partner Overview is false in partner object & partnerId " +
                               partner.getPartnerValue().toString());
            getBindings().getRestrictedAccess().setVisible(true);
            System.out.println("restricted access visible");
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());
        }

        System.out.println("Exiting commpanyOverview function");
    }
}



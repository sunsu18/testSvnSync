package com.sfr.engage.accountsummarytaskflow;

import java.util.Iterator;
import java.util.List;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTree;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class AccountSummary {
    private RichPanelGroupLayout accountOverview;
    private RichPanelGroupLayout cardGroupOverview;
    private RichPanelGroupLayout cardOverview;
    private RichPanelGroupLayout companyOverview;
    private RichPanelGroupLayout cardOverviewTextPanel;
    private RichPanelGroupLayout cardGroupOverviewTextPanel;
    private RichPanelGroupLayout accountOverviewTextPanel;
    private RichPanelGroupLayout companyOverviewTextPanel;
    private String overviewText;
    private RichOutputText overviewOutputText;
    private String id;


    public AccountSummary() {
    }

    public void Tree_Listner(SelectionEvent selectionEvent) {
        // Add event code here...
        
        System.out.println("Inside listner");
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
        CollectionModel collectionModel = (CollectionModel)tree1.getValue();
        treeBinding = (JUCtrlHierBinding)collectionModel.getWrappedData();
        treeBinding2 = (JUCtrlHierBinding)collectionModel.getRowData();
        
        nodeBinding1 = treeBinding.findNodeByKeyPath(key1);
        System.out.println("String 1--------->" + treeBinding.getPath());
            System.out.println("String 2---------->" + treeBinding.getAllRowsInRange().length);
        //System.out.println("Node Binding1 " + nodeBinding1.getRowKey().toString());
        for (Object o : nodeBinding1.getAttributeValues()) {
            System.out.println("Node Binding2 ");
        System.out.println("Selected values " + o.toString());
             id = o.toString();
        }
//        for (Object o : nodeBinding1.getParent().getAttributeValues()) {
//            System.out.println("Node Binding333 ");
//        System.out.println("Selected values " + o.toString());
//             id = o.toString();
//        }
            
//            System.out.println("qwerty   ------ " + nodeBinding1.getParent().getAttribute("cardGroupID").toString());
        
        
//        System.out.println("--------------> " + nodeBinding1.getParent().getAttribute("Cat1shortdesc").toString());
        //System.out.println("Node Binding3 " + nodeBinding1.getRowKey().toString());
            
        rw = nodeBinding1.getRow();
        rowType = rw.getStructureDef().getDefName();
        System.out.println("treeSelectionListener : " + ".............." + rowType);
    }
        
        if(rowType.contains("AccountInfo")) {
            System.out.println("Account node clicked");
            companyOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(companyOverview);
            DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter1;
//            System.out.println("qwerty   ACC ------ " + nodeBinding1.getParent().getAttribute("accountNumber").toString());
//            System.out.println("qwerty   ACC PART ------ " + nodeBinding1.getParent().getAttribute("partnerValue").toString());
           
            if (bindings != null) {
                iter1 = bindings.findIteratorBinding("PrtAccountVO2Iterator");
                System.out.println("DC Iterator bindings for Account found in mypagelistner");
            } else {
                System.out.println("account bindings is null");
                iter1 = null;
            }
//            
            if(iter1!=null)
            {
            
            ViewObject vo_acc = iter1.getViewObject();
            vo_acc.setWhereClause("ACCOUNT_ID =: accid");
            vo_acc.defineNamedWhereClauseParam("accid",id,null);
            vo_acc.setNamedWhereClauseParam("countryCode", "no_NO");
            System.out.println(vo_acc.getQuery());                                                        
            vo_acc.executeQuery();
            
            }
            accountOverview.setVisible(true);
           AdfFacesContext.getCurrentInstance().addPartialTarget(accountOverview);
            cardOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cardOverview);
            cardGroupOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cardGroupOverview);
            

        }else if(rowType.contains("CardGroupInfo")) {
            System.out.println("cardgroup node clicked");
            companyOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(companyOverview);
            DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter1;
//            System.out.println("qwerty   CARDGRP ------ " + nodeBinding1.getParent().getAttribute("accountNumber").toString());
//            System.out.println("qwerty   CARDGRP PART ------ " + nodeBinding1.getParent().getAttribute("partnerValue").toString());
//            System.out.println("qwerty   CARDGRP PART ------ " + nodeBinding1.getParent().getAttribute("cardGroupID").toString());
         
            
            if (bindings != null) {
                iter1 = bindings.findIteratorBinding("PrtCardgroupVO3Iterator");
                System.out.println("DC Iterator bindings for CardGroup found in mypagelistner");
            } else {
                System.out.println("card group bindings is null");
                iter1 = null;
            }
            //
            if(iter1!=null)
            {
            
            ViewObject vo_cg = iter1.getViewObject();
            vo_cg.setWhereClause("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc");

            //vo_cg.setWhereClause("CARDGROUP_SEQ =: cgid");
            vo_cg.defineNamedWhereClauseParam("cgid",id,null);
           // vo_cg.setWhereClause("COUNTRY_CODE =: cc");
            vo_cg.defineNamedWhereClauseParam("cc","no_NO",null);
         
            System.out.println(vo_cg.getQuery());                                                        
            vo_cg.executeQuery();
            
            }
            
            
            
            
            cardGroupOverview.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cardGroupOverview);
            accountOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(accountOverview);
            cardOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cardOverview);
            

        }else if(rowType.contains("CardInfo")) {
            System.out.println("card node clicked");
            companyOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(companyOverview);
            
            DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter1;
            
//            System.out.println("qwerty   cardID ------ " + nodeBinding1.getParent().getAttribute("accountNumber").toString());
//            System.out.println("qwerty   cardID PART ------ " + nodeBinding1.getParent().getAttribute("partnerValue").toString());
//            System.out.println("qwerty   cardID PART ------ " + nodeBinding1.getParent().getAttribute("cardGroupID").toString());
//            System.out.println("qwerty   cardID PART ------ " + nodeBinding1.getParent().getAttribute("cardID").toString());
         
            
            if (bindings != null) {
                iter1 = bindings.findIteratorBinding("PrtCardVO4Iterator");
                System.out.println("DC Iterator bindings for Card found in mypagelistner");
            } else {
                System.out.println("card bindings is null");
                iter1 = null;
            }
            //
            if(iter1!=null)
            {
            
            ViewObject vo_card = iter1.getViewObject();
            vo_card.setWhereClause("PRT_CARD_PK =: cardid AND COUNTRY_CODE =: cc");
            //vo_card.setWhereClause("PRT_CARD_PK =: cardid");
            vo_card.defineNamedWhereClauseParam("cardid",id,null);
           //s vo_card.setWhereClause("COUNTRY_CODE =: cc");
            vo_card.defineNamedWhereClauseParam("cc","no_NO",null);
            
            System.out.println(vo_card.getQuery());                                                        
            vo_card.executeQuery();
            
            }
            
            cardOverview.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cardOverview);
            accountOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(accountOverview);
            cardGroupOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cardGroupOverview);
            

        }
        else {
            System.out.println("partner node clicked");
            
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
            
            
            
            companyOverview.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(companyOverview);
            cardOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cardOverview);
            accountOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(accountOverview);
            cardGroupOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cardGroupOverview);
            

        }
        
        
}

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

    public void setOverviewText(String overviewText) {
        this.overviewText = overviewText;
    }

    public String getOverviewText() {
        return overviewText;
    }

    public void setOverviewOutputText(RichOutputText overviewOutputText) {
        this.overviewOutputText = overviewOutputText;
    }

    public RichOutputText getOverviewOutputText() {
        return overviewOutputText;
    }

    public void setCardOverviewTextPanel(RichPanelGroupLayout cardOverviewTextPanel) {
        this.cardOverviewTextPanel = cardOverviewTextPanel;
    }

    public RichPanelGroupLayout getCardOverviewTextPanel() {
        return cardOverviewTextPanel;
    }

    public void setCardGroupOverviewTextPanel(RichPanelGroupLayout cardGroupOverviewTextPanel) {
        this.cardGroupOverviewTextPanel = cardGroupOverviewTextPanel;
    }

    public RichPanelGroupLayout getCardGroupOverviewTextPanel() {
        return cardGroupOverviewTextPanel;
    }

    public void setAccountOverviewTextPanel(RichPanelGroupLayout accountOverviewTextPanel) {
        this.accountOverviewTextPanel = accountOverviewTextPanel;
    }

    public RichPanelGroupLayout getAccountOverviewTextPanel() {
        return accountOverviewTextPanel;
    }

    public void setCompanyOverviewTextPanel(RichPanelGroupLayout companyOverviewTextPanel) {
        this.companyOverviewTextPanel = companyOverviewTextPanel;
    }

    public RichPanelGroupLayout getCompanyOverviewTextPanel() {
        return companyOverviewTextPanel;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

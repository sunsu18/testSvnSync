package com.sfr.engage.accountsummarytaskflow;

import java.util.Iterator;
import java.util.List;

import oracle.adf.view.rich.component.rich.data.RichTree;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
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
        CollectionModel collectionModel = (CollectionModel)tree1.getValue();
        treeBinding = (JUCtrlHierBinding)collectionModel.getWrappedData();
        nodeBinding1 = treeBinding.findNodeByKeyPath(key1);
        System.out.println("Node Binding " + nodeBinding1);
        rw = nodeBinding1.getRow();
        rowType = rw.getStructureDef().getDefName();
        System.out.println("treeSelectionListener : " + ".............." + rowType);
    }
        
        if(rowType.contains("AccountInfo")) {
            System.out.println("Account node clicked");
            companyOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(companyOverview);
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
            cardOverview.setVisible(true);
           
            AdfFacesContext.getCurrentInstance().addPartialTarget(cardOverview);
            accountOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(accountOverview);
            cardGroupOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cardGroupOverview);
            

        }
        else {
            System.out.println("partner node clicked");
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
}

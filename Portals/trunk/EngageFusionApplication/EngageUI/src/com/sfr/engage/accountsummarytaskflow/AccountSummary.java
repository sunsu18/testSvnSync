package com.sfr.engage.accountsummarytaskflow;


import com.sfr.engage.core.AccountInfo;
import com.sfr.engage.core.CardGroupInfo;
import com.sfr.engage.core.CardInfo;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.engage.model.queries.uvo.PrtAccountVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtCardVORowImpl;
import com.sfr.util.ADFUtils;

import java.util.ArrayList;

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

import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

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

    private ArrayList<SelectItem> cardTypeList;

    private String accountId;
    private String cardGroupId;
    private String cardId;
    private String partnerId;
    JUCtrlHierNodeBinding dropNodeParent;
    RowKeySetImpl rksImpl;
    JUCtrlHierNodeBinding rootNode;
    PartnerInfo partner = new PartnerInfo();
    AccountInfo account = new AccountInfo();
    
    List <AccountInfo> AccountList = new ArrayList<AccountInfo>();
    List<CardGroupInfo> cardgrouplist = new ArrayList<CardGroupInfo>();
    private HttpServletRequest request;
    private ExternalContext ectx;
    private HttpSession session;
    private boolean displayAccountOverview = false;
    private boolean displayCardGroupOverview = false;
    
   
    public AccountSummary() {
    }

    public void Tree_Listner(SelectionEvent selectionEvent) {
        
    if(session!= null) {
        System.out.println("partner list from session");
        partner = (PartnerInfo)session.getAttribute("Partner_Object_List");
        AccountList = partner.getAccountList();
                
        
        System.out.println("partner value from session in string " + partner.getPartnerValue().toString());
        System.out.println("partner value from session " + partner.getPartnerValue());

    }
        
    ectx = FacesContext.getCurrentInstance().getExternalContext();
    request = (HttpServletRequest)ectx.getRequest();
    session = request.getSession(false);
    
   


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
       rksImpl = new RowKeySetImpl();
        nodeBinding1 = treeBinding.findNodeByKeyPath(key1);
     
      

        rksImpl.add(key1);
        System.out.println(" rksImpl-------->" +  rksImpl);
        //the first key to add is the node that received the drop
        //operation (departments).            
         rootNode = treeBinding.getRootNodeBinding();
        System.out.println("root node--------->" + rootNode);
       dropNodeParent =  nodeBinding1.getParent();
        System.out.println("dropNodeParent--------->" + dropNodeParent);
           
      
        for (Object o : nodeBinding1.getAttributeValues()) {
    
             id = o.toString();
            
       
            
        }
      
            
        rw = nodeBinding1.getRow();
        rowType = rw.getStructureDef().getDefName();
        System.out.println("treeSelectionListener : " + ".............." + rowType);
    }
        
        if(rowType.contains("AccountInfo")) {
            
            for (int k = 0;
                 k < AccountList.size();
                 k++) {
                System.out.println("account id value in account list " +
                                   AccountList.get(k).getAccountNumber());
                System.out.println("New account id value to compare" +
                                   id);


                if (AccountList.get(k).getAccountNumber().equalsIgnoreCase(id)) {
                    System.out.println("account id exists in account list");
                    displayAccountOverview =
                    AccountList.get(k).isAccountOverview();
                    break;
                }
            }
            
          
               if(displayAccountOverview)
               {
            partnerId=dropNodeParent.toString();
            accountId=id;
            System.out.println("Account node clicked");
            System.out.println("Accountid"+accountId);
            companyOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(companyOverview);
            DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter1;
     
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
        }  

        }else if(rowType.contains("CardGroupInfo")) {
            System.out.println("cardgroup node clicked");
            
            
            for (int k = 0;
                 k < AccountList.size();
                 k++) {
                System.out.println("account id value in account list " +
                                   AccountList.get(k).getAccountNumber());
                account = AccountList.get(k);
                cardgrouplist = account.getCardGroup();
                
                for(int cardgrp_count;cardgrp_count<cardgrouplist.size();cardgrp_count++)
                {
                if (cardgrouplist.get(cardgrp_count).getCardGroupID().equalsIgnoreCase(id)) {
                    System.out.println("cardgroup id exists in cardgroup list of account " + account.getAccountNumber());
                    displayCardGroupOverview =
                    cardgrouplist.get(cardgrp_count).isCardGroupOverview();
                    break;
                }
                }
            }
            
            
            if(displayCardGroupOverview)
            {
            
            companyOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(companyOverview);
            DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter1;
     
            
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
            String maintype = id.substring(0,3);
            System.out.println("Main type "+maintype);
            String subtype= id.substring(3,6);
            System.out.println("Sub type "+ subtype);
            String cardgroupseq= id.substring(6);
            System.out.println("card seq " + cardgroupseq);
            
            vo_cg.setWhereClause("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub" );


            vo_cg.defineNamedWhereClauseParam("cgid",cardgroupseq,null);

            vo_cg.defineNamedWhereClauseParam("cc","no_NO",null);
                vo_cg.defineNamedWhereClauseParam("cgmain","maintype",null);
                vo_cg.defineNamedWhereClauseParam("cgsub","subtype",null);
         
            System.out.println(vo_cg.getQuery());                                                        
            vo_cg.executeQuery();
            System.out.println("rows " + vo_cg.getEstimatedRowCount());
            
                bindings =(DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding iter3;
                if (bindings != null) {
                    iter3 =bindings.findIteratorBinding("PrtCardVO4Iterator");
                    System.out.println("DC Iterator bindings for Card VO found in mypagelistner");
                } else {
                    System.out.println("bindings is null");
                    iter3 = null;
                }
                
                ViewObject vo3 =iter3.getViewObject();

                vo3 = iter3.getViewObject();
                vo3.setWhereClause("CARDGROUP_SEQ =: cgid");
                vo3.defineNamedWhereClauseParam("cgid",id,null);
                
                
                vo3.executeQuery();
                
                if (vo3.getEstimatedRowCount() != 0) {
                    cardTypeList = new ArrayList<SelectItem>();
                    while (vo3.hasNext()) {
                        PrtCardVORowImpl currRow = (PrtCardVORowImpl)vo3.next();
                        if (currRow.getCardType() != null) {
                            SelectItem selectItem = new SelectItem();
                            selectItem.setLabel(currRow.getCardType());
                            selectItem.setValue(currRow.getCardType());
                            cardTypeList.add(selectItem);
                            
                        }
                    }
                    
                    
            
                }
                System.out.println("vo3 getWhereClause" + vo3.getWhereClause());
                
                if ("CARDGROUP_SEQ =: cgid".equalsIgnoreCase(vo3.getWhereClause())) {
                    System.out.println("Remove query executed");
                    vo3.removeNamedWhereClauseParam("cgid");
                    vo3.setWhereClause("");
                    vo3.executeQuery(); }
                
                
                
                
            
            }
            
            cardGroupOverview.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cardGroupOverview);
            accountOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(accountOverview);
            cardOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(cardOverview);
           
            partnerId=dropNodeParent.getParent().toString();
            cardGroupId=id;
            accountId=dropNodeParent.toString();
          
             
        
            
        }
            

        }else if(rowType.contains("CardInfo")) {
            System.out.println("card node clicked");
            companyOverview.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(companyOverview);
            
            DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter1;
            
         
            if (bindings != null) {
                iter1 = bindings.findIteratorBinding("PrtCardVO4Iterator");
                System.out.println("DC Iterator bindings for Card found in Account Summary");
            } else {
                System.out.println("card bindings is null");
                iter1 = null;
            }
            //
            if(iter1!=null)
            {
            
            ViewObject vo_card = iter1.getViewObject();
            vo_card.setWhereClause("PRT_CARD_PK =: cardid AND COUNTRY_CODE =: cc");

            vo_card.defineNamedWhereClauseParam("cardid",id,null);

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
            
            
            cardId=id;
      
        
         cardGroupId=dropNodeParent.toString();
            dropNodeParent=dropNodeParent.getParent();
          accountId=dropNodeParent.toString();
         partnerId=dropNodeParent.getParent().toString();
        

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
            
             ;
    
    if(session!= null) {
        System.out.println("partner list from session");
        partner = (PartnerInfo)session.getAttribute("Partner_Object_List");
        
        System.out.println("partner value from session in string " + partner.getPartnerValue().toString());
        System.out.println("partner value from session " + partner.getPartnerValue());

    }
            if(partner.isCompanyOverview())
            {
            companyOverview.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(companyOverview);
            }
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
}

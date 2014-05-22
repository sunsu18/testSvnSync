package com.sfr.engage.accountsummarytaskflow;


import com.sfr.core.bean.User;
import com.sfr.engage.core.AccountInfo;
import com.sfr.engage.core.CardGroupInfo;

import com.sfr.engage.core.PartnerInfo;

import com.sfr.engage.model.queries.uvo.PrtCardVORowImpl;


import com.sfr.util.AccessDataControl;

import com.sfr.util.constants.Constants;

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
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTree;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;


import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.component.UIXTree;
import org.apache.myfaces.trinidad.event.AttributeChangeEvent;
import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class AccountSummary implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;

    private String overviewText;

    private String id;

    private ArrayList<String> cardTypeList;

    private String accountId;
    private String cardGroupId;
    private String cardId;
    private String partnerId;
    private String partnerIdName;

    RowKeySetImpl rksImpl;

    private PartnerInfo partner = new PartnerInfo();
    private AccountInfo account = new AccountInfo();

    private List<AccountInfo> AccountList = new ArrayList<AccountInfo>();
    private List<PartnerInfo> partnerList = new ArrayList<PartnerInfo>();
    private List<CardGroupInfo> cardgrouplist = new ArrayList<CardGroupInfo>();
    private HttpServletRequest request;
    private ExternalContext ectx;
    private HttpSession session;
    private boolean displayAccountOverview = false;
    private boolean displayCardGroupOverview = false;
    private transient Bindings bindings;
    JUCtrlHierNodeBinding dropNodeParent;
    JUCtrlHierNodeBinding rootNode;
    AccessDataControl accessDC = new AccessDataControl();
    private boolean businessProfile = false;
    private boolean privateProfile = false;
    private String profile = "private";
    
    String cardType="";
    private User userInfo;
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    private String firstName="";
    private String lastName="";
    private List<AccountInfo> AccountListDefault = new ArrayList<AccountInfo>();
    private List<PartnerInfo> partnerListDefault = new ArrayList<PartnerInfo>();
    private List<CardGroupInfo> cardgrouplistDefault = new ArrayList<CardGroupInfo>();
    private List<CardGroupInfo> cardlistDefault = new ArrayList<CardGroupInfo>();
    private String partnerName="";
    private String accountName="";
    private String cardgroupName="";
    private String cardName="";
    private PartnerInfo partnerDefault = new PartnerInfo();
    private AccountInfo accountDefault = new AccountInfo();
    private boolean isPartner=false;
    private boolean ismanager=false;
    private boolean isEmployee=false;                       

    
    //    public static final ADFLogger log = ADFLogger.createADFLogger("Engage_Portal");
    


    public AccountSummary() {
        
        
        log.fine(accessDC.getDisplayRecord() +  this.getClass() + " Inside Constructor of Account Summary");
        RichTree tree = getBindings().getAdfTree();
        if(tree!=null)
        {
            RowKeySet _disclosedRowKeys = tree.getDisclosedRowKeys();  
            
            if (_disclosedRowKeys != null && _disclosedRowKeys.size() > 0) {  
            _disclosedRowKeys.clear();  
          }
            else
			log.info(accessDC.getDisplayRecord() + this.getClass() +" No key to disclose in adf tree");
                
          tree.setDisclosedRowKeys(_disclosedRowKeys);
        }
        else
        {
            
			
            //log.warning(accessDC.getDisplayRecord() +"Adf tree bindings is null");
            
            ectx = FacesContext.getCurrentInstance().getExternalContext();
            request = (HttpServletRequest)ectx.getRequest();
            session = request.getSession(false);
            
            if(session!=null)
            { 
                System.out.println("temp1----------------------> " +  "session not null");
                if(session.getAttribute("profile")!=null)
                {   
                    System.out.println("temp1----------------------> " +  "session getAttribute(profile) not null");
                    profile = (String)session.getAttribute("profile");
                    System.out.println("temp1----------------------> " +  "profile from session " + profile);
                    if (profile.equalsIgnoreCase("business")) {
                        System.out.println("temp1----------------------> " +  "profile from session is business");
                        businessProfile = true;
                        privateProfile = false;
                    }else if (profile.equalsIgnoreCase("private")) {
                        System.out.println("temp1----------------------> " +  "profile from session is private");
                        businessProfile = false;
                        privateProfile = true;
                        
                    }
                }
            }
            
            if (session != null) {
                
                    log.info(accessDC.getDisplayRecord() + " session not null getting adfTree from session");
                if(session.getAttribute("adfTree")!= null)
                {   tree = (RichTree)session.getAttribute("adfTree");
                    RowKeySet _disclosedRowKeys = tree.getDisclosedRowKeys();  
                    
                    if (_disclosedRowKeys != null && _disclosedRowKeys.size() > 0) {  
                    _disclosedRowKeys.clear();  
                    }
                    else
                    
                    log.info(accessDC.getDisplayRecord() + " No key to disclose in adf tree");
                    tree.setDisclosedRowKeys(_disclosedRowKeys);
                }
                else
                    
                log.info(accessDC.getDisplayRecord() + " Session is not null but still adf tree is null it may be due to first hit on Account Summary");
                }
            
        } 

log.fine(accessDC.getDisplayRecord() + this.getClass() +" Exiting from Constructor of Account Summary");

if(session!=null){
    if (null != session.getAttribute(Constants.SESSION_USER_INFO))
        userInfo =
                (User)session.getAttribute(Constants.SESSION_USER_INFO);
    
    if (userInfo != null) {
     
     firstName=userInfo.getFirstName();
        List<String> temp= new ArrayList<String>();
        List<String> temp1= new ArrayList<String>();
        List<String> temp2= new ArrayList<String>();
        List<String> temp3= new ArrayList<String>();
        partnerListDefault = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
     for(int i=0; i< userInfo.getRoleList().size(); i++){
         for(int j=0;j<userInfo.getRoleList().get(i).getIdString().size();j++)    {
             
     if(userInfo.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_ADMIN)){
        isPartner=true;
         
          if(partnerListDefault!=null) {
                      for(int k=0;k<partnerListDefault.size();k++)
                      {  
                          int a = userInfo.getRoleList().get(i).getIdString().get(j).indexOf("PP");
                          if(partnerListDefault.get(k).getPartnerValue().equals(userInfo.getRoleList().get(i).getIdString().get(j).substring(a +2,a +10))){
                              temp.add(partnerListDefault.get(k).getPartnerName());
                              partnerName=temp.toString().substring(1, temp.toString().length()-1).replace("", "");   
                          }
                      }
                  }
        
    }
     if(userInfo.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_MGR)){
         if (userInfo.getRoleList().get(i).getIdString().get(j).contains("AC")) {
         ismanager=true;

                     
         int b =userInfo.getRoleList().get(i).getIdString().get(j).indexOf("AC");
             temp1.add(userInfo.getRoleList().get(i).getIdString().get(j).substring(b +2,b +12));
             accountName=temp1.toString().substring(1, temp1.toString().length()-1).replace("", "");   
                
         }   
         if(userInfo.getRoleList().get(i).getIdString().get(j).contains("CG")){
             ismanager=true;
             if(partnerListDefault!=null) {
                         for(int k=0;k<partnerListDefault.size();k++)
                         {  
                             int c =userInfo.getRoleList().get(i).getIdString().get(j).indexOf("CG");
                             
                             
                             for(int ac=0;ac<partnerListDefault.get(k).getAccountList().size();ac++){
                                 for(int cg=0;cg<partnerListDefault.get(k).getAccountList().get(ac).getCardGroup().size();cg++)   { 
                                 
                                if(partnerListDefault.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupID().equals(userInfo.getRoleList().get(i).getIdString().get(j).substring(c +2))){
                                 temp2.add(partnerListDefault.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCardGroupID());
                                 cardgroupName=temp2.toString().substring(1, temp2.toString().length()-1).replace("", "");   
                                     }
                             }
                         }
                             }
                         }
                     }
             
         }
     
    if(userInfo.getRoleList().get(i).getRoleName().equals(Constants.ROLE_WCP_CARD_B2B_EMP)){
        
                 if(userInfo.getRoleList().get(i).getIdString().get(j).contains("CC")){
                     isEmployee=true;
                     if(partnerListDefault!=null) {
                                 for(int k=0;k<partnerListDefault.size();k++)
                                 {  
                                     int d =userInfo.getRoleList().get(i).getIdString().get(j).indexOf("CC");
                                     
                                     
                                     for(int ac=0;ac<partnerListDefault.get(k).getAccountList().size();ac++){
                                         for(int cg=0;cg<partnerListDefault.get(k).getAccountList().get(ac).getCardGroup().size();cg++)   { 
                                         
                                         for(int cc=0;cc<partnerListDefault.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().size();cc++)   { 
                                         
                                         
                                        if(partnerListDefault.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getCardID().equals(userInfo.getRoleList().get(i).getIdString().get(j).substring(d +2))){
                                         temp3.add(partnerListDefault.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cc).getExternalCardID());
                                         cardName=temp3.toString().substring(1, temp3.toString().length()-1).replace("", "");   
                                             }
                                         }
                                     }
                                 }
                                     }
                                 }
                             }
        
        
                 
                 
             }
         }
    
     }   

    
//    if(session.getAttribute("Partner_Object_List") != null){
//    List<String> temp= new ArrayList<String>();
//    List<String> temp1= new ArrayList<String>();
//    List<String> temp2= new ArrayList<String>();
//    List<String> temp3= new ArrayList<String>();
//        partnerListDefault = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
//        if(partnerListDefault!=null) {
//            for(int i=0;i<partnerListDefault.size();i++)
//            {
//            
//            if(partnerListDefault.get(i).isCompanyOverview()){
//            isPartner=true;
//            temp.add(partnerListDefault.get(i).getPartnerName());
//            partnerName=temp.toString().substring(1, temp.toString().length()-1).replace("", "");
//           
//            }
           
            
//                for(int j=0;j<partnerListDefault.get(i).getAccountList().size();j++){ 
//                    
//                    if(partnerListDefault.get(i).getAccountList().get(j).isAccountOverview()){
//                        ismanager=true;
//                        temp1.add(partnerListDefault.get(i).getAccountList().get(j).getAccountNumber());
//                        accountName=temp1.toString().substring(1, temp1.toString().length()-1).replace("", "");
//                        
//                    }
//                   
//                     
//                        for(int k=0;k<partnerListDefault.get(i).getAccountList().get(j).getCardGroup().size();k++){
//                            
//                            if(partnerListDefault.get(i).getAccountList().get(j).getCardGroup().get(k).isCardGroupOverview()){
//                                ismanager=true;
//                                temp2.add(partnerListDefault.get(i).getAccountList().get(j).getCardGroup().get(k).getCardGroupID());
//                                cardgroupName=temp2.toString().substring(1, temp2.toString().length()-1).replace("", "");  
//                            }
//                              
//                        for(int l=0;l<partnerListDefault.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().size();l++){
//                            
//                            if(partnerListDefault.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(l).isCardOverview()){
//                                isEmployee=true;
//                                temp3.add(partnerListDefault.get(i).getAccountList().get(j).getCardGroup().get(k).getCard().get(l).getExternalCardID());
//                                cardName=temp3.toString().substring(1, temp3.toString().length()-1).replace("", ""); 
//                            }
//                                                       
//                        
//                        
//                        }
//                       
//                     
//                    }
//                }
            }
        }


    
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

    public void setBusinessProfile(boolean businessProfile) {
        this.businessProfile = businessProfile;
    }
    public String close_Action() {
       getBindings().getShowAllPopUp().cancel();
        return null;
    }

    public boolean isBusinessProfile() {
        return businessProfile;
    }

    public void setPrivateProfile(boolean privateProfile) {
        this.privateProfile = privateProfile;
    }

    public boolean isPrivateProfile() {
        return privateProfile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }


    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setCardgroupName(String cardgroupName) {
        this.cardgroupName = cardgroupName;
    }

    public String getCardgroupName() {
        return cardgroupName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setIsPartner(boolean isPartner) {
        this.isPartner = isPartner;
    }

    public boolean isIsPartner() {
        return isPartner;
    }

    public void setIsmanager(boolean ismanager) {
        this.ismanager = ismanager;
    }

    public boolean isIsmanager() {
        return ismanager;
    }

    public void setIsEmployee(boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    public boolean isIsEmployee() {
        return isEmployee;
    }

    public void setPartnerIdName(String partnerIdName) {
        this.partnerIdName = partnerIdName;
    }

    public String getPartnerIdName() {
        return partnerIdName;
    }


    public class Bindings {
        private RichPanelGroupLayout accountOverview;
        private RichPanelGroupLayout cardGroupOverview;
        private RichPanelGroupLayout cardOverview;
        private RichPanelGroupLayout companyOverview;
        private RichPanelGroupLayout restrictedAccess;
        private RichTree adfTree;
        private RichOutputText cardTypeOT;
        private RichPopup showAllPopUp;
        private RichPanelGroupLayout defaultPanel;
        public void setDefaultPanel(RichPanelGroupLayout defaultPanel) {
            this.defaultPanel = defaultPanel;
        }

        public RichPanelGroupLayout getDefaultPanel() {
            return defaultPanel;
        }
        public void setCardTypeOT(RichOutputText cardTypeOT) {
            this.cardTypeOT = cardTypeOT;
        }

        public RichOutputText getCardTypeOT() {
            return cardTypeOT;
        }

        public void setShowAllPopUp(RichPopup showAllPopUp) {
            this.showAllPopUp = showAllPopUp;
        }

        public RichPopup getShowAllPopUp() {
            return showAllPopUp;
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

       


        public void setRestrictedAccess(RichPanelGroupLayout restrictedAccess) {
            this.restrictedAccess = restrictedAccess;
        }

        public RichPanelGroupLayout getRestrictedAccess() {
            return restrictedAccess;
        }


        public void setAdfTree(RichTree adfTree) {
		            if(adfTree !=null) {
//                
//                RichTree tree = adfTree; 
//                //UIComponent tree = adfTree;
//                if (tree != null) { 
//                    System.out.println("UIComponent tree not null");
//                    CollectionModel model =
//                        (CollectionModel)tree.getValue();
//                   
//                    if(model!= null)
//                    {
//                    System.out.println("Model not null");
//                    JUCtrlHierBinding treeBinding = (JUCtrlHierBinding)model.getWrappedData();
//                    if(treeBinding!=null) {
//                        System.out.println("Tree binding is not null");
//                        JUCtrlHierNodeBinding rootNode = 
//                         treeBinding.getRootNodeBinding();
//                        if(rootNode != null)
//                            System.out.println("Root node not null");
//                        else
//                            System.out.println("Root node is null");
//                    }else
//                        System.out.println("Tree binding is null");
//                }  
//
//                }
//                else 
//                    System.out.println("UIComponent tree is null");
                        
            }
            this.adfTree = adfTree;
        }

        public RichTree getAdfTree() {
            if(adfTree !=null) {
               
            }
            return adfTree;
        }

       
    }

       

    public void treeListner(SelectionEvent selectionEvent) {
log.fine(accessDC.getDisplayRecord() + this.getClass() + " Entering in tree selection listner ");
System.out.println("Selection event " + selectionEvent.getSource());
        
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);


        // Add event code here...

        
        log.info(accessDC.getDisplayRecord() + this.getClass() + " Inside tree listner");
        
        JUCtrlHierNodeBinding nodeBinding1 = null;
        Row rw = null;
        String rowType = "";
        RichTree tree1 = (RichTree)selectionEvent.getSource();
        if(tree1 != null)
        {
            
			log.info(accessDC.getDisplayRecord() + this.getClass() + " Tree is not null in selection listner");
            if (session != null) {
                session.setAttribute("adfTree", tree1);
                
				log.info(accessDC.getDisplayRecord() + this.getClass() + " adftree stored in session");
                
                
            }
            else
			log.warning(accessDC.getDisplayRecord() + this.getClass() + " Session null and adf tree not stored in session");
               
            
            
        }
        else
		 log.warning(accessDC.getDisplayRecord() + this.getClass() + " Tree is null in selection listner also");
        
        
        RowKeySet rowKeySet1 = selectionEvent.getAddedSet();
    System.out.println("RowKeySet " + rowKeySet1);
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
            System.out.println("Node Binding 1 " + nodeBinding1);


            rksImpl.add(key1);
            
          rootNode = treeBinding.getRootNodeBinding();
           System.out.println("rootNode " + rootNode);
           dropNodeParent = nodeBinding1.getParent();
            
            
            for(Object ob : nodeBinding1.getParent().getAttributeValues()) {
                if(ob != null)
                {System.out.println("dropNodeParent after conversion " + ob.toString());}
                break;
            }
            
            for (Object o : nodeBinding1.getAttributeValues()) {

                id = o.toString();
                System.out.println(" id is " + id);
                break;


            }
            if (session != null) {
                
                if(session.getAttribute("Partner_Object_List") != null){
                    
                    partnerList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
                    if(partnerList!=null) {
                        for(int k=0;k<partnerList.size();k++)
                        {
                            if(partnerList.get(k).getPartnerValue().toString().equals(id)) {
                                partner = partnerList.get(k);
                                AccountList = partner.getAccountList();
                            }
                            
                        }

                    }
                }
                
               
                

            }

            


            rw = nodeBinding1.getRow();
            rowType = rw.getStructureDef().getDefName();
            
        }

        if (rowType.contains("AccountInfo")) {
            //hiding default panel
            getBindings().getDefaultPanel().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDefaultPanel());
            //clicked node belongs to account so execute Account overview
            accountOverview();


        } else if (rowType.contains("CardGroupInfo")) {
            //hiding default panel
            getBindings().getDefaultPanel().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDefaultPanel());
            //clicked node belongs to cardGroup so execute cardGroup overview
            
            for (Object o : nodeBinding1.getAttributeValues()) {

               // id = o.toString();
                cardGroupId = o.toString();
                System.out.println(" inside cardgroup overview id is " + cardGroupId);
            //                break;


            }
            cardGroupOverview();

        } else if (rowType.contains("CardInfo")) {
            //hiding default panel
            getBindings().getDefaultPanel().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDefaultPanel());
            //clicked node belongs to card so execute card overview
            for (Object o : nodeBinding1.getAttributeValues()) {

               // id = o.toString();
                cardId = o.toString();
                System.out.println(" inside card overview id is " + id);
//                break;


            }
            cardOverview();

        } else {
            //hiding default panel
            getBindings().getDefaultPanel().setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getDefaultPanel());
            
            for (Object o : nodeBinding1.getAttributeValues()) {

                partnerIdName = o.toString();
                System.out.println(" partnerIdName is " + id);
//                break;


            }
            //clicked node belongs to partner so execute partner overview
//            partnerIdName = id;
            companyOverview();

        }

log.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting from tree selection listner ");
    }
    
    
//    public void toggle(RowDisclosureEvent event) {
//            System.out.println("Disclose Listner called");
//            if (session != null) {
//                
//                partner = (PartnerInfo)session.getAttribute("Partner_Object_List");
//                AccountList = partner.getAccountList();
//
//
//                
//                System.out.println("partner value from session " +
//                                   partner.getPartnerValue());
//
//            }
//
//            ectx = FacesContext.getCurrentInstance().getExternalContext();
//            request = (HttpServletRequest)ectx.getRequest();
//            session = request.getSession(false);
//
//
//            // Add event code here...
//
//            System.out.println("Inside tree listner");
//            JUCtrlHierNodeBinding nodeBinding1 = null;
//            Row rw = null;
//            String rowType = "";
//            RichTree tree1 = (RichTree)event.getSource();
//            if(tree1 != null)
//            {
//                System.out.println("Tree is not null in selection listner");
//                if (session != null) {
//                    session.setAttribute("adfTree", tree1);
//                    System.out.println("adftree stored in session");
//                    
//                    
//                }
//                else
//                    System.out.println("Session null and adf tree not stored in session");
//                
//                
//            }
//            else
//            System.out.println("Tree is null in selection listner also");
//            
//            RowKeySet rowKeySet1 = event.getAddedSet();
//
//            Iterator rksIterator = rowKeySet1.iterator();
//            while (rksIterator.hasNext()) {
//                List key1 = (List)rksIterator.next();
//                JUCtrlHierBinding treeBinding = null;
//                JUCtrlHierBinding treeBinding2 = null;
//                CollectionModel collectionModel =
//                    (CollectionModel)tree1.getValue();
//                treeBinding = (JUCtrlHierBinding)collectionModel.getWrappedData();
//                treeBinding2 = (JUCtrlHierBinding)collectionModel.getRowData();
//                rksImpl = new RowKeySetImpl();
//                nodeBinding1 = treeBinding.findNodeByKeyPath(key1);
//
//
//                rksImpl.add(key1);
//                
//              rootNode = treeBinding.getRootNodeBinding();
//               
//               dropNodeParent = nodeBinding1.getParent();
//
//
//
//                for (Object o : nodeBinding1.getAttributeValues()) {
//
//                    id = o.toString();
//
//
//                }
//
//
//                rw = nodeBinding1.getRow();
//                rowType = rw.getStructureDef().getDefName();
//                
//            }
//
//            if (rowType.contains("AccountInfo")) {
//
//                //clicked node belongs to account so execute Account overview
//                accountOverview();
//
//
//            } else if (rowType.contains("CardGroupInfo")) {
//
//                //clicked node belongs to cardGroup so execute cardGroup overview
//                cardGroupOverview();
//
//            } else if (rowType.contains("CardInfo")) {
//                //clicked node belongs to card so execute card overview
//                cardOverview();
//
//            } else {
//
//                //clicked node belongs to partner so execute partner overview
//                companyOverview();
//
//            }
//        }
    public void processAttributeChange(AttributeChangeEvent event) {
        
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


    public void setCardTypeList(ArrayList<String> cardTypeList) {
        this.cardTypeList = cardTypeList;
    }

    public ArrayList<String> getCardTypeList() {
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
	log.fine(accessDC.getDisplayRecord() + this.getClass() + " Entering in account overview function ");

        hideAll();
        
        getBindings().getRestrictedAccess().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());

              
        partnerId = dropNodeParent.toString();
        System.out.println("partnerId inside accountoverview " + partnerId );
        
        partnerIdName = partnerId.substring(partnerId.indexOf(" ")+1);
        
//        for(Object ob : dropNodeParent.getAttributeValues()) {
//            if(ob != null)
//            {System.out.println("dropNodeParent after conversion " + ob.toString());
//             partnerId = ob.toString();}
//            break;
//        }
        
        partnerId = partnerId.substring(0, partnerId.indexOf(" "));
        System.out.println("partnerId inside accountoverview " + partnerId );
        
        
        accountId = id;
        
        if(partnerList!=null) {
            for(int k=0;k<partnerList.size();k++)
            {
                if(partnerList.get(k).getPartnerValue().toString().equals(partnerId)) {
                    partner = partnerList.get(k);
                    AccountList = partner.getAccountList();
                }
                
            }

        }
        for (int k = 0; k < AccountList.size(); k++) {

            if (AccountList.get(k).getAccountNumber().equalsIgnoreCase(id)) {
                System.out.println("Account matched");
                displayAccountOverview =
                        AccountList.get(k).isAccountOverview();
                break;
            }
        }


        if (displayAccountOverview) {
          log.info(accessDC.getDisplayRecord() + this.getClass() + " Account node clicked, Account Overview is true in partner object " + partner.getPartnerValue() +" & Accountid " + id);
//            partnerId = dropNodeParent.toString();
//            accountId = id;

            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter1;

            if (bindings != null) {
                iter1 = bindings.findIteratorBinding("PrtAccountVO2Iterator");

            } else {
			log.severe(accessDC.getDisplayRecord() + this.getClass() + " account bindings inside account Overview is null");
                
                iter1 = null;
            }

            if (iter1 != null) {
               

                ViewObject accountVO = iter1.getViewObject();
                
                if(("PARTNER_ID =: pid").equalsIgnoreCase(accountVO.getWhereClause()))
                {
                    
                    accountVO.removeNamedWhereClauseParam("pid");
                    
                    accountVO.setWhereClause("");
                    accountVO.executeQuery();
                    
                    
                }
                accountVO.setWhereClause("ACCOUNT_ID =: accid");
                accountVO.defineNamedWhereClauseParam("accid", id, null);
                accountVO.setNamedWhereClauseParam("countryCode", (String)session.getAttribute(Constants.userLang));
               
                accountVO.executeQuery();

            }
            getBindings().getAccountOverview().setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getAccountOverview());


        } else {
            hideAll();
            
            log.info(accessDC.getDisplayRecord() + this.getClass() + " Account node clicked But Account Overview is false in partner object " + partner.getPartnerValue() +" & Accountid " + id);
            getBindings().getRestrictedAccess().setVisible(true);

            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());
        }
        
		log.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting from AccountOverview function ");
    }

    public void cardGroupOverview() {
        
            log.fine(accessDC.getDisplayRecord() + this.getClass() + " Entering in cardGroupOverview function ");

        hideAll();
        getBindings().getRestrictedAccess().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());
        
        partnerId =dropNodeParent.getParent().toString();
        System.out.println("partnerId inside cardgroupoverview " + partnerId );
        
        partnerIdName = partnerId.substring(partnerId.indexOf(" ")+1);
//        for(Object ob : dropNodeParent.getParent().getAttributeValues()) {
//            if(ob != null)
//            {System.out.println("dropNodeParent after conversion " + ob.toString());
//             partnerId = ob.toString();}
//            break;
//        }
        
        partnerId = partnerId.substring(0, partnerId.indexOf(" "));
        System.out.println("partnerId inside cardgroupoverview " + partnerId );
        
       
        
        //cardGroupId = id;
        accountId =dropNodeParent.toString();
        
        if(partnerList!=null) {
                    for(int k=0;k<partnerList.size();k++)
                    {
                        if(partnerList.get(k).getPartnerValue().toString().equals(partnerId)) {
                            partner = partnerList.get(k);
                            AccountList = partner.getAccountList();
                        }
                        
                    }

                }
 

        for (int k = 0; k < AccountList.size(); k++) {
            account = AccountList.get(k);
            cardgrouplist = account.getCardGroup();

            for (int cardgrp_count = 0; cardgrp_count < cardgrouplist.size();
                 cardgrp_count++) {
                if (cardgrouplist.get(cardgrp_count).getCardGroupID().equalsIgnoreCase(id)) {

                    displayCardGroupOverview =
                            cardgrouplist.get(cardgrp_count).isCardGroupOverview();
                    break;
                }
            }
        }


        if (displayCardGroupOverview) {

            
            log.info(accessDC.getDisplayRecord() + this.getClass() + " cardGroup node clicked, cardGroup Overview is true in partner object " + partner.getPartnerValue() +" & cardGroupId " + id);
            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter1;


            if (bindings != null) {
                iter1 =
                        bindings.findIteratorBinding("PrtCardgroupVO3Iterator");
                
            } else {
                
                log.severe(accessDC.getDisplayRecord() + this.getClass() + " card bindings inside cardGroup Overview is null ");
				iter1 = null;
            }
            
            if (iter1 != null) {

                ViewObject cardGroupVO = iter1.getViewObject();
                
                
                if(("ACCOUNT_ID =: accid AND COUNTRY_CODE =: cc").equalsIgnoreCase(cardGroupVO.getWhereClause())) {
                    
                    cardGroupVO.removeNamedWhereClauseParam("accid");
                    cardGroupVO.removeNamedWhereClauseParam("cc");
                    cardGroupVO.setWhereClause("");
                    cardGroupVO.executeQuery();
                }
                String maintype = id.substring(0, 3);
                
                String subtype = id.substring(3, 6);
                
                String cardgroupseq = id.substring(6);
                

                cardGroupVO.setWhereClause("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub");


                cardGroupVO.defineNamedWhereClauseParam("cgid", cardgroupseq, null);

                cardGroupVO.defineNamedWhereClauseParam("cc", (String)session.getAttribute(Constants.userLang), null);
                cardGroupVO.defineNamedWhereClauseParam("cgmain", maintype, null);
                cardGroupVO.defineNamedWhereClauseParam("cgsub", subtype, null);

                
                cardGroupVO.executeQuery();
                

                bindings =
                        (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding iter3;
                if (bindings != null) {
                    iter3 = bindings.findIteratorBinding("PrtCardVO4Iterator");
                
                } else {
                    log.severe(accessDC.getDisplayRecord() + this.getClass() + " card bindings in PrtCardVO4Iterator is null");
                   
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
                cardVO.setWhereClause("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub");
                cardVO.defineNamedWhereClauseParam("cgid", cardgroupseq, null);

                cardVO.defineNamedWhereClauseParam("cc", (String)session.getAttribute(Constants.userLang), null);
                cardVO.defineNamedWhereClauseParam("cgmain", maintype, null);
                cardVO.defineNamedWhereClauseParam("cgsub", subtype, null);


                cardVO.executeQuery();
                
                if (cardVO.getEstimatedRowCount() != 0) {
                    cardTypeList = new ArrayList<String>();
                    while (cardVO.hasNext()) {
                        PrtCardVORowImpl currRow =
                            (PrtCardVORowImpl)cardVO.next();
                        if (currRow.getCardType() != null) {
                            
                            cardTypeList.add(currRow.getCardType());
                            String card=cardTypeList.toString();
                            cardType=card.substring(1, card.length() - 1).replace("", "");

                        }
                    }

                    

                }
				log.info(accessDC.getDisplayRecord() + this.getClass() + " CardType List size inside cardgroup Overview " + cardTypeList.size());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getShowAllPopUp());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardTypeOT());


            }

            getBindings().getCardGroupOverview().setVisible(true);
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardGroupOverview());





        } else {

            hideAll();
            
            log.info(accessDC.getDisplayRecord() + this.getClass() + " cardGroup node clicked But cardGroup Overview is false in partner object " + partner.getPartnerValue() +" & cardGroupId " +
                               id);
            getBindings().getRestrictedAccess().setVisible(true);
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());
        }

        
		 log.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting cardGroupOverview function ");

    }

    public void cardOverview() {
        
	log.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside cardOverview function");


        hideAll();
        getBindings().getRestrictedAccess().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());
        
       // cardId = id;
        
       for(Object ob : dropNodeParent.getAttributeValues()) {
           cardGroupId = ob.toString();
           System.out.println("cardgroupID after conversion " + cardGroupId);
           
       }
       
        


        //cardGroupId = dropNodeParent.toString();
        dropNodeParent = dropNodeParent.getParent();
        accountId = dropNodeParent.toString();
        partnerId = dropNodeParent.getParent().toString();
        System.out.println("partnerId inside cardoverview " + partnerId );
        partnerIdName = partnerId.substring(partnerId.indexOf(" ")+1); 
        
//        for(Object ob : dropNodeParent.getParent().getAttributeValues()) {
//            if(ob != null)
//            {System.out.println("dropNodeParent after conversion " + ob.toString());
//             partnerId = ob.toString();}
//            break;
//        }
        
        partnerId = partnerId.substring(0, partnerId.indexOf(" "));
        System.out.println("partnerId inside cardoverview " + partnerId );

        
		log.info(accessDC.getDisplayRecord() + this.getClass() + " card node clicked for partner object" + partner.getPartnerValue() +" & cardid " + id);

        DCBindingContainer bindings =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter1;


        if (bindings != null) {
            iter1 = bindings.findIteratorBinding("PrtCardVO4Iterator");
            
        } else {
           
		   log.warning(accessDC.getDisplayRecord() + this.getClass() + " card bindings inside card Overview is null");
            iter1 = null;
        }
        
        if (iter1 != null) {

            ViewObject cardVO = iter1.getViewObject();

            if ("CARDGROUP_SEQ =: cgid AND COUNTRY_CODE =: cc AND CARDGROUP_MAIN_TYPE=: cgmain AND CARDGROUP_SUB_TYPE=: cgsub".equalsIgnoreCase(cardVO.getWhereClause())) {
                
                cardVO.removeNamedWhereClauseParam("cgid");
                cardVO.removeNamedWhereClauseParam("cc");
                cardVO.removeNamedWhereClauseParam("cgmain");
                cardVO.removeNamedWhereClauseParam("cgsub");
                cardVO.setWhereClause("");
                cardVO.executeQuery();
            }

            cardVO.setWhereClause("PRT_CARD_PK =: cardid AND COUNTRY_CODE =: cc");

            cardVO.defineNamedWhereClauseParam("cardid", id, null);

            cardVO.defineNamedWhereClauseParam("cc", (String)session.getAttribute(Constants.userLang), null);

            
            cardVO.executeQuery();


        }

        getBindings().getCardOverview().setVisible(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCardOverview());


       

        
		log.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting cardOverview function");
    }

    public void companyOverview() {
        
		log.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside companyOverview function");

        hideAll();
        getBindings().getRestrictedAccess().setVisible(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());

                    DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                    DCIteratorBinding iter1;
        //
                    if (bindings != null) {
                        iter1 = bindings.findIteratorBinding("PrtPartnerVO1Iterator");
                        System.out.println("DC Iterator bindings for Card found in mypagelistner");
                    } else {
                        System.out.println("card bindings is null");
                        iter1 = null;
                    }
                    //
                    if(iter1!=null)
                    {
        //
                    ViewObject partnerVO = iter1.getViewObject();
                    partnerVO.setWhereClause("PARTNER_ID =: partid AND COUNTRY_CODE =: cc");
                    partnerVO.defineNamedWhereClauseParam("partid",id,null);
        
                    partnerVO.defineNamedWhereClauseParam("cc",(String)session.getAttribute(Constants.userLang),null);
        //
    
                    partnerVO.executeQuery();
        //
                    }


        if (session != null) {
            		  log.info(accessDC.getDisplayRecord() + this.getClass() + " partner value from session " +
                               partner.getPartnerValue());


        }
        if (partner.isCompanyOverview()) {

			log.info(accessDC.getDisplayRecord() + this.getClass() + " partner node clicked, company/partner Overview is true in partner object & partnerId " +
                               partner.getPartnerValue().toString());
            getBindings().getCompanyOverview().setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getCompanyOverview());
        } else {
            hideAll();
        
			log.info(accessDC.getDisplayRecord() + this.getClass() + " partner node clicked But company/partner Overview is false in partner object & partnerId " +
                               partner.getPartnerValue().toString());
            getBindings().getRestrictedAccess().setVisible(true);
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(getBindings().getRestrictedAccess());
        }
log.fine(accessDC.getDisplayRecord() + this.getClass() + " Exiting commpanyOverview function");
        
    }
}



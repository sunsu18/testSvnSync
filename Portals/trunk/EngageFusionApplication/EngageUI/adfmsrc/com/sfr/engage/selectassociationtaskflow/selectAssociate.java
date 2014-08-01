package com.sfr.engage.selectassociationtaskflow;

import com.sfr.core.bean.User;

import com.sfr.engage.core.UserDetails;
import com.sfr.util.AccessDataControl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import com.sfr.util.constants.Constants;

import java.io.IOException;

import org.apache.myfaces.trinidad.event.SelectionEvent;

public class selectAssociate {
    private RichPanelGroupLayout searchResultsIdm;
    public String selectedUser;
    private RichSelectBooleanRadio selectedBoolean;
    private RichTable searchUsersTable;
    //private String [][] myArr;
    
    

    public selectAssociate() {
        super();
    }
    
    private RichInputText searchText;
    private List<User> userlist = new ArrayList<User>();
    private List<UserDetails> userdetailslist = new ArrayList<UserDetails>();
    HttpSession session = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();

    public void setSearchText(RichInputText searchText) {
        this.searchText = searchText;
    }

    public RichInputText getSearchText() {
        return searchText;
    }

    public void setUserlist(List<User> userlist) {
        this.userlist = userlist;
    }

    public List<User> getUserlist() {
        return userlist;
    }
    
    public void SearchIDMUsers(ActionEvent actionEvent) {
        // Add event code here...
        
        System.out.println("searching");
        if(searchText.getValue()!=null)
        {
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding =
            bindings.getOperationBinding("searchUser");
        System.out.println((AccessDataControl.getDisplayRecord()+this.getClass() + "searchText is " + searchText.getValue().toString()));
        
        if(searchText.getValue().toString().length()==8)
        {   
            System.out.println("appending lang");
            
            if(session!=null)
            String finaluserid = session.getAttribute(Constants.userLang)+"PP"+ searchText.getValue().toString();
            System.out.println("finaluserid " + finaluserid);
            operationBinding.getParamsMap().put("customerId", finaluserid);
            userlist = (List<User>)operationBinding.execute();
        }
        
        System.out.println("searched user list size is  " + userlist.size());
       // myArr = new String [userlist.size()][2];
       UserDetails userdetails;
            for(int b=0;b<userlist.size();b++)
            {
                System.out.println("user " + b + " -> " + userlist.get(b).getEmailID());
                System.out.println("user " + b + " -> " + userlist.get(b).getFirstName());
                
                for(int r=0;r<userlist.get(b).getRoleList().size();r++) {
                    if(userlist.get(b).getRoleList().get(r).getRoleName().equalsIgnoreCase(Constants.ROLE_WCP_CARD_B2B_ADMIN))
                    
                    {   userdetails = new UserDetails();
                      
                        userdetails.setUseremail(userlist.get(b).getEmailID()); 
                        if(userlist.get(b).getRoleList().get(r).getIdString()!=null)
                        userdetails.setPartnerids(userlist.get(b).getRoleList().get(r).getIdString().toString().replaceAll("PP", "").replaceAll("NO", "").replaceAll("DK", "").replaceAll("SE", ""));    
                        
                        if(userlist.get(b).getFirstName()!=null)
                            userdetails.setFirstname(userlist.get(b).getFirstName());
                        
                        if(userlist.get(b).getLastName()!=null)
                            userdetails.setLastname(userlist.get(b).getLastName()); 
                        
                        userdetailslist.add(userdetails);
                        //myArr[b][0] =  userlist.get(b).getEmailID();
                        // myArr[b][1] =  userlist.get(b).getRoleList().get(r).getIdString().toString();
                    }
                }
                
                
               
                    
                
            }
            
            System.out.println("userdetails are as follows ");
            for(int b=0;b<userdetailslist.size();b++)
                System.out.println("User email -> " + userdetailslist.get(b).getUseremail() + " partners ids " + userdetailslist.get(b).getPartnerids());
            
            
//        System.out.println("2d array data is ");
//        for(int b=0;b<userlist.size();b++) {
//            System.out.println("user " + myArr[b][0]);
//            System.out.println("ids " + myArr[b][1]);
//        }
        searchResultsIdm.setVisible(true);  
    AdfFacesContext.getCurrentInstance().addPartialTarget(searchResultsIdm);
    
        }
        
    }

    public void setSearchResultsIdm(RichPanelGroupLayout searchResultsIdm) {
        this.searchResultsIdm = searchResultsIdm;
    }

    public RichPanelGroupLayout getSearchResultsIdm() {
        return searchResultsIdm;
    }

    public void userconfirmed(ActionEvent actionEvent) {
        // Add event code here...
        //System.out.println("selected boolean " + selectedBoolean.getValue().toString());
        
        RichTable userTable = getSearchUsersTable();
        UserDetails selectedRow = (UserDetails)userTable.getSelectedRowData();
        
        if (selectedRow != null) {
        System.out.println("selected row is " + selectedRow.getUseremail());
        
        }


        
        
        System.out.println("User email ==>"+ selectedRow.getUseremail());
        selectedUser = selectedRow.getUseremail();
        
        for(int k=0;k<userlist.size();k++) {
            if(userlist.get(k).getEmailID().equalsIgnoreCase(selectedUser))
            {
                session.setAttribute(Constants.SESSION_USER_INFO,userlist.get(k));
                System.out.println("user in session is " + userlist.get(k).getEmailID());
                break;
            }
        }
        
        System.out.println("redirecting to home page");
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ectx.redirect(ectx.getRequestContextPath() + "/faces/card/home");
        } catch (IOException e) {
            System.out.println("exception " + e.getMessage());
        }
    }

//    public void userselectionlistner(SelectionEvent selectionEvent) {
//        // Add event code here...
//        System.out.println("User email idddddd ==>"+AdfFacesContext.getCurrentInstance().getPageFlowScope().get("userEmail"));
//    }

//    public void userselectionchanged(ValueChangeEvent valueChangeEvent) {
//        // Add event code here...
//        System.out.println("User email id ==>"+AdfFacesContext.getCurrentInstance().getPageFlowScope().get("userEmail"));
//        
//        selectedUser = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("userEmail").toString();
//        
//        
//        
//    }

    public void setSelectedBoolean(RichSelectBooleanRadio selectedBoolean) {
        this.selectedBoolean = selectedBoolean;
    }

    public RichSelectBooleanRadio getSelectedBoolean() {
        return selectedBoolean;
    }

    public void setSearchUsersTable(RichTable searchUsersTable) {
        this.searchUsersTable = searchUsersTable;
    }

    public RichTable getSearchUsersTable() {
        return searchUsersTable;
    }

//    public void setMyArr(String[][] myArr) {
//        this.myArr = myArr;
//    }
//
//    public String[][] getMyArr() {
//        return myArr;
//    }

    public void setUserdetailslist(List<UserDetails> userdetailslist) {
        this.userdetailslist = userdetailslist;
    }

    public List<UserDetails> getUserdetailslist() {
        return userdetailslist;
    }
}

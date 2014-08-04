package com.sfr.engage.AssociationSelection;

import com.sfr.core.bean.User;
import com.sfr.engage.core.UserDetails;

import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class AssociationSelection {
    private RichInputText searchText;
    private RichPanelGroupLayout searchResultsIdm;
    private List<UserDetails> userdetailslist = new ArrayList<UserDetails>();
    private RichTable searchUsersTable;
    private List<User> userlist = new ArrayList<User>();
    HttpSession session = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
    UserDetails userdetails;
    public String selectedUser;
    private RichCommandButton confirmButton;
    EngageResourceBundle resourceBundle = new EngageResourceBundle();

    public AssociationSelection() {
    }

    public void setSearchText(RichInputText searchText) {
        this.searchText = searchText;
    }

    public RichInputText getSearchText() {
        return searchText;
    }

    public void SearchIDMUsers(ActionEvent actionEvent) {
        // Add event code here...
        userdetailslist = new ArrayList<UserDetails>();
        userlist = new ArrayList<User>();
        userdetailslist.clear();
        String finaluserid ="";
        System.out.println("searching");
        String regex = "\\d+";
        if(searchText.getValue()!=null && searchText.getValue().toString().matches(regex))
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
            finaluserid = (session.getAttribute(Constants.DISPLAY_PORTAL_LANG).toString()).concat("PP").concat(searchText.getValue().toString());
            System.out.println("finaluserid " + finaluserid);
            operationBinding.getParamsMap().put("customerId", finaluserid);
            userlist = (List<User>)operationBinding.execute();
            
            System.out.println("searched user list size is  " + userlist.size()); 
        // myArr = new String [userlist.size()][2];
        
        
        if(userlist!=null && userlist.size()>0)
        {
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
            
            confirmButton.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(confirmButton);
        searchResultsIdm.setVisible(true);  
        AdfFacesContext.getCurrentInstance().addPartialTarget(searchResultsIdm);
        
        }
        else {
            searchResultsIdm.setVisible(true);  
            AdfFacesContext.getCurrentInstance().addPartialTarget(searchResultsIdm); 
            confirmButton.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(confirmButton);
        }
        
        }
        else {
            searchResultsIdm.setVisible(false);  
            AdfFacesContext.getCurrentInstance().addPartialTarget(searchResultsIdm);
            confirmButton.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(confirmButton);
            showErrorMessage("PLEASE_ENTER_VALID_PARTNER_ID");
        }
        
        }
        else {
            searchResultsIdm.setVisible(false);  
            AdfFacesContext.getCurrentInstance().addPartialTarget(searchResultsIdm);
            confirmButton.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(confirmButton);
            showErrorMessage("PLEASE_ENTER_VALID_PARTNER_ID");
            
        }
    }
    
    public String showErrorMessage(String errorVar) {
        System.out.println("throwing error message");
        if (errorVar != null) {
            
            if (resourceBundle.containsKey(errorVar)) {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     (String)resourceBundle.getObject(errorVar),
                                     "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
        }
        return null;
    }

    public void setSearchResultsIdm(RichPanelGroupLayout searchResultsIdm) {
        this.searchResultsIdm = searchResultsIdm;
    }

    public RichPanelGroupLayout getSearchResultsIdm() {
        return searchResultsIdm;
    }

    public void setUserdetailslist(List<UserDetails> userdetailslist) {
        this.userdetailslist = userdetailslist;
    }

    public List<UserDetails> getUserdetailslist() {
        return userdetailslist;
    }

    public void setSearchUsersTable(RichTable searchUsersTable) {
        this.searchUsersTable = searchUsersTable;
    }

    public RichTable getSearchUsersTable() {
        return searchUsersTable;
    }

    public void userconfirmed(ActionEvent actionEvent) {
        // Add event code here...
        //System.out.println("selected boolean " + selectedBoolean.getValue().toString());
        
        RichTable userTable = getSearchUsersTable();
        UserDetails selectedRow = (UserDetails)userTable.getSelectedRowData();
        
        if (selectedRow != null) {
        System.out.println("selected row is " + selectedRow.getUseremail());
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
        else {
            showErrorMessage("PLEASE_SELECT_A_USER");
        }
    
    }

    public void setUserlist(List<User> userlist) {
        this.userlist = userlist;
    }

    public List<User> getUserlist() {
        return userlist;
    }

    public void setConfirmButton(RichCommandButton confirmButton) {
        this.confirmButton = confirmButton;
    }

    public RichCommandButton getConfirmButton() {
        return confirmButton;
    }
}

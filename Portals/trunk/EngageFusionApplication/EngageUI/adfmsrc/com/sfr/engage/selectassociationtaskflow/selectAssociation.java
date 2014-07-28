package com.sfr.engage.selectassociationtaskflow;

import com.sfr.core.bean.User;
import com.sfr.util.AccessDataControl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class selectAssociation {
    private RichInputText searchText;
    private List<User> userlist = new ArrayList<User>();

    public selectAssociation() {
    }

    public void SearchIDMUsers(ActionEvent actionEvent) {
        // Add event code here...
        
        System.out.println("searching");
        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding =
            bindings.getOperationBinding("searchUser");
        System.out.println((AccessDataControl.getDisplayRecord()+this.getClass() + "searchText is " + searchText.getValue().toString()));
        
        operationBinding.getParamsMap().put("customerId", searchText.getValue().toString());
        userlist = (List<User>)operationBinding.execute();
        
        System.out.println("searched user list size is  " + userlist.size());
        
        for(int b=0;b<userlist.size();b++)
            System.out.println("user " + b + " -> " + userlist.get(b).getEmailID());
        
    }

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
}

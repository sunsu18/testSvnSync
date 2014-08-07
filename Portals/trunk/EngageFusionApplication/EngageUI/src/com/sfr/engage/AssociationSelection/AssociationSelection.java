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
import oracle.adf.share.logging.ADFLogger;
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
    private HttpSession session = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
    private UserDetails userdetails;
    public String selectedUser;
    private RichCommandButton confirmButton;
    private EngageResourceBundle resourceBundle = new EngageResourceBundle();
    public static final ADFLogger _logger = AccessDataControl.getSFRLogger();
    private AccessDataControl accessDC = new AccessDataControl();

    public AssociationSelection() {
    }

    public void setSearchText(RichInputText searchText) {
        this.searchText = searchText;
    }

    public RichInputText getSearchText() {
        return searchText;
    }

    public void SearchIDMUsers(ActionEvent actionEvent) {
        userdetailslist = new ArrayList<UserDetails>();
        userlist = new ArrayList<User>();
        userdetailslist.clear();
        String finaluserid = "";
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "searching");
        String regex = "\\d+";
        if (searchText.getValue() != null && searchText.getValue().toString().matches(regex)) {
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("searchUser");
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "searchText is " + searchText.getValue().toString());

            if (searchText.getValue().toString().length() == 8) {
                _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Appending lang");
                if (session != null)
                    finaluserid = (session.getAttribute(Constants.DISPLAY_PORTAL_LANG).toString()).concat("PP").concat(searchText.getValue().toString());
                _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "finaluserid " + finaluserid);
                operationBinding.getParamsMap().put("customerId", finaluserid);
                userlist = (List<User>)operationBinding.execute();
                _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "searched user list size is  " + userlist.size());

                if (userlist != null && userlist.size() > 0) {
                    for (int b = 0; b < userlist.size(); b++) {
                        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "user " + b + " -> " + userlist.get(b).getEmailID());
                        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "user " + b + " -> " + userlist.get(b).getFirstName());
                        for (int r = 0; r < userlist.get(b).getRoleList().size(); r++) {
                            if (userlist.get(b).getRoleList().get(r).getRoleName().equalsIgnoreCase(Constants.ROLE_WCP_CARD_B2B_ADMIN)) {
                                userdetails = new UserDetails();
                                userdetails.setUseremail(userlist.get(b).getEmailID());
                                if (userlist.get(b).getRoleList().get(r).getIdString() != null) {
                                    userdetails.setPartnerids(userlist.get(b).getRoleList().get(r).getIdString().toString().replaceAll("PP",
                                                                                                                                       "").replaceAll("NO",
                                                                                                                                                      "").replaceAll("DK",
                                                                                                                                                                     "").replaceAll("SE",
                                                                                                                                                                                    ""));
                                }
                                if (userlist.get(b).getFirstName() != null) {
                                    userdetails.setFirstname(userlist.get(b).getFirstName());
                                }
                                if (userlist.get(b).getLastName() != null) {
                                    userdetails.setLastname(userlist.get(b).getLastName());
                                }
                                userdetailslist.add(userdetails);
                            }
                        }


                    }
                    _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "userdetails are as follows ");
                    for (int b = 0; b < userdetailslist.size(); b++) {
                        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "User email -> " + userdetailslist.get(b).getUseremail() +
                                     " partners ids " + userdetailslist.get(b).getPartnerids());
                    }
                    confirmButton.setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(confirmButton);
                    searchResultsIdm.setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(searchResultsIdm);

                } else {
                    searchResultsIdm.setVisible(true);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(searchResultsIdm);
                    confirmButton.setVisible(false);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(confirmButton);
                }

            } else {
                searchResultsIdm.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(searchResultsIdm);
                confirmButton.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(confirmButton);
                showErrorMessage("PLEASE_ENTER_VALID_PARTNER_ID");
            }

        } else {
            searchResultsIdm.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(searchResultsIdm);
            confirmButton.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(confirmButton);
            showErrorMessage("PLEASE_ENTER_VALID_PARTNER_ID");

        }
    }

    public String showErrorMessage(String errorVar) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Throwing error message");
        if (errorVar != null) {
            if (resourceBundle.containsKey(errorVar)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, (String)resourceBundle.getObject(errorVar), "");
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
        RichTable userTable = getSearchUsersTable();
        UserDetails selectedRow = (UserDetails)userTable.getSelectedRowData();
        if (selectedRow != null) {
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "selected row is " + selectedRow.getUseremail());
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "User email ==>" + selectedRow.getUseremail());
            selectedUser = selectedRow.getUseremail();
            for (int k = 0; k < userlist.size(); k++) {
                if (userlist.get(k).getEmailID().equalsIgnoreCase(selectedUser)) {
                    session.setAttribute(Constants.SESSION_USER_INFO, userlist.get(k));
                    _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "user in session is " + userlist.get(k).getEmailID());
                    break;
                }
            }
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "redirecting to home page");
            ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
            try {
                ectx.redirect(ectx.getRequestContextPath() + "/faces/card/home");
            } catch (IOException e) {
                _logger.severe(accessDC.getDisplayRecord() + this.getClass() + "exception " + e.getMessage());
            }
        } else {
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

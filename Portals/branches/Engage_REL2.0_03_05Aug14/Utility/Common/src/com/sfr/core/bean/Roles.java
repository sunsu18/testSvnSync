package com.sfr.core.bean;

import java.util.ArrayList;
import java.util.List;

public class Roles {
    private String roleName;
    private List<Integer> customerID;
    private List<String> idString;
    private boolean assigned;


    public Roles() {
        super();
    }

    public Roles(Roles newRole) {
        super();
        this.roleName = newRole.getRoleName();
        this.assigned = newRole.isAssigned();
        if (newRole.getCustomerID() != null)
            this.customerID = new ArrayList<Integer>(newRole.getCustomerID());
        if (newRole.getIdString() != null)
            this.idString = new ArrayList<String>(newRole.getIdString());
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setCustomerID(List<Integer> customerID) {
        this.customerID = customerID;
    }

    public List<Integer> getCustomerID() {
        return customerID;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public boolean isAssigned() {
        return assigned;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--RoleStarts");
        sb.append("--RoleName=<" + this.getRoleName() + ">");
        sb.append("--CustomerID=<" + this.getCustomerID() + ">");
        sb.append("--AirportID=<" + this.getIdString() + ">");
        sb.append("--Assigned=<" + this.isAssigned() + ">");
        sb.append("--RoleEnds");
        return sb.toString();
    }

    public void setIdString(List<String> idString) {
        this.idString = idString;
    }

    public List<String> getIdString() {
        return idString;
    }
}

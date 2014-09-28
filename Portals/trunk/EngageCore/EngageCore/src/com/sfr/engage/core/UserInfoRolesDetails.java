package com.sfr.engage.core;

import java.io.Serializable;

public class UserInfoRolesDetails implements Serializable {
    @SuppressWarnings("compatibility:8035622705877005723")
    private static final long serialVersionUID = 1L;

    private String roleName;
    private String associationValue;
    private String partnerId;
    private boolean checkUserRole;
    private String associationType;

    public UserInfoRolesDetails() {
        super();
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setAssociationValue(String associationValue) {
        this.associationValue = associationValue;
    }

    public String getAssociationValue() {
        return associationValue;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setCheckUserRole(boolean checkUserRole) {
        this.checkUserRole = checkUserRole;
    }

    public boolean isCheckUserRole() {
        return checkUserRole;
    }

    public void setAssociationType(String associationType) {
        this.associationType = associationType;
    }

    public String getAssociationType() {
        return associationType;
    }
}

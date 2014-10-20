package com.sfr.engage.core;

import java.io.Serializable;

public class UserDetails implements Serializable {
    @SuppressWarnings("compatibility:-2043160001042835794")
    private static final long serialVersionUID = 1L;

    public UserDetails() {
        super();
    }

    private String firstname;
    private String lastname;
    private String useremail;
    private String partnerids;


    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setPartnerids(String partnerids) {
        this.partnerids = partnerids;
    }

    public String getPartnerids() {
        return partnerids;
    }
}

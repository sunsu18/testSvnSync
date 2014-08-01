package com.sfr.engage.core;

public class UserDetails {
    public UserDetails() {
        super();
    }
    
    public String firstname;
    public String lastname;
    public String useremail;
    public String partnerids;

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
}

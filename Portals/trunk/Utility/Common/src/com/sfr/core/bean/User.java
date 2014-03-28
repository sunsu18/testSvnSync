package com.sfr.core.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class User extends BaseBean {
//    private static final long serialVersionUID = 1L;
    private Date dob;//
    private String firstName;//
    private String lastName;//
    private String middleName;//
    private String phoneNumber;//
    // For creation, the position will be the first role which is there in roles table on UI. 
    // While updation, the position which we get from search is forwarded.
    private String position;        //    
    private String userID;//
    //To find if the user is active or not. If the user is not active than do not display user.
    private boolean active;//
    // To find if the user is internal. 
    // For internal user, we do not allow to update the basic details and delete user. 
    // We also do not allow the user password to be changed.
    private boolean internal;//
    // Basic indentifier for user. All WSDL operations are based on this.
    private String emailID;//
    // This field will be used for users who are SFR employees. 
    // JDE user Employee number will be given in this field.
    private List<Integer> EmployeeNumber;
    private boolean authenticated;//not used
    private boolean passwordChangeNeeded;//not used
    // Language of user. IDM will send email of password to user in this language. 
    // Portal passes this while creation. 
    // While updation, the language which we get from search is forwarded.
    private String lang;//
    private String password;//not used
    // Contains all the roles which the user is having. 
    // Used for driving the features of portal.
    private String rolelist;//
    // List of Roles Object.
    // Roles object contain the roleName and corresponding association. 
    // For multiple roles, the list will contain multiple roles object each having the role name and association.
    private List<Roles> roleList;//
    // List of the roles newly assigned.
    private List<String> rolesAssigned;//
    // List of the roles revoked.
    private List<String> rolesRevoked;//
    // Used in the case of Service station users. 
    // We have to pass the designation in this field and the corresponding AD groups will be assigned to the SS user.
    private String designation;
    //The basic site ID for COCO/CODO Manager and COCO employee. CODO and DODO employees do not have primary site ID.
    private Integer primarySiteID;
    
    public User() {
        super();
    }

    public User(User newUser) {
        super();
        this.active = newUser.isActive();//
        this.authenticated = newUser.isAuthenticated();
        this.dob = newUser.getDob();
        this.emailID = newUser.getEmailID();//
        this.EmployeeNumber = newUser.getEmployeeNumber();
        this.firstName = newUser.getFirstName();
        this.internal = newUser.isInternal();
        this.lang = newUser.getLang();
        this.lastName = newUser.getLastName();
        this.middleName = newUser.getMiddleName();
        this.password = newUser.getPassword();
        this.passwordChangeNeeded = newUser.isPasswordChangeNeeded();
        this.phoneNumber = newUser.getPhoneNumber();
        this.position = newUser.getPosition();
        this.rolelist = newUser.getRolelist();
        this.userID = newUser.getUserID();
        if(newUser.getRoleList()!=null)
            this.roleList=new ArrayList<Roles>(newUser.getRoleList());
        if(newUser.getRolesRevoked()!=null)
            this.rolesRevoked=new ArrayList<String>(newUser.getRolesRevoked());
        if(newUser.getRolesAssigned()!=null)
            this.rolesAssigned=new ArrayList<String>(newUser.getRolesAssigned());
        this.primarySiteID = newUser.getPrimarySiteID();
        this.designation = newUser.getDesignation();
    }
    

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setPasswordChangeNeeded(boolean passwordChangeNeeded) {
        this.passwordChangeNeeded = passwordChangeNeeded;
    }

    public boolean isPasswordChangeNeeded() {
        return passwordChangeNeeded;
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

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }


    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }


    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDob() {
        return dob;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRolelist(String rolelist) {
        this.rolelist = rolelist;
    }

    public String getRolelist() {
        return rolelist;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        sb.append("Object Printing Starts---------"+NEW_LINE);
        sb.append(this.getClass().getName() + " Object {" );
        sb.append("--userID=<"+this.userID+">" );
        sb.append("--emailID=<"+this.emailID+">" );
        sb.append("--isActive=<"+this.isActive()+">" );
        sb.append("--isInternal=<"+this.isInternal()+">" );
        sb.append("--FirstName=<"+this.firstName+">" );
        sb.append("--MiddleName=<"+this.middleName+">" );
        sb.append("--LastName=<"+this.lastName+">" );
        sb.append("--phoneNumber=<"+this.phoneNumber+">" );
        sb.append("--position=<"+this.position+">" );
        sb.append("--lang=<"+this.lang+">" );
        sb.append("--DOB=<"+this.dob+">" );
        sb.append("--primarySiteID=<"+this.primarySiteID+">" );
        sb.append("--designation=<"+this.designation+">" );
        sb.append("--rolelist=<"+this.rolelist+">" );
        sb.append("--rolesAssigned=<"+this.rolesAssigned+">" );
        sb.append("--rolesRevoked=<"+this.rolesRevoked+">" );
        sb.append("--roleList=<"+this.roleList+">" + NEW_LINE);
        
        sb.append("} Object printing ends---------"+NEW_LINE);
        return sb.toString();
    }


    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setEmployeeNumber(List<Integer> EmployeeNumber) {
        this.EmployeeNumber = EmployeeNumber;
    }

    public List<Integer> getEmployeeNumber() {
        return EmployeeNumber;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    public boolean isInternal() {
        return internal;
    }

    public void setRoleList(List<Roles> roleList) {
        this.roleList = roleList;
    }

    public List<Roles> getRoleList() {
        return roleList;
    }

    public void setRolesAssigned(List<String> rolesAssigned) {
        this.rolesAssigned = rolesAssigned;
    }

    public List<String> getRolesAssigned() {
        return rolesAssigned;
    }

    public void setRolesRevoked(List<String> rolesRevoked) {
        this.rolesRevoked = rolesRevoked;
    }

    public List<String> getRolesRevoked() {
        return rolesRevoked;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setPrimarySiteID(Integer primarySiteID) {
        this.primarySiteID = primarySiteID;
    }

    public Integer getPrimarySiteID() {
        return primarySiteID;
    }
}

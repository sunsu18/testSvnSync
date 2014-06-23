package com.sfr.services.core.dao;


import com.sfr.services.client.proxy.user.OIMUserManagermentImpl;
import com.sfr.services.client.proxy.user.type.Identity;
import com.sfr.services.client.proxy.user.type.OimUserManagementResult;
import com.sfr.core.bean.BaseBean;
import com.sfr.core.bean.BusinessError;
import com.sfr.core.bean.Roles;
import com.sfr.core.bean.User;
import com.sfr.services.core.dao.factory.DAOFactory;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.NamingException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import oracle.adf.share.logging.ADFLogger;

import oracle.security.idm.IMException;
import oracle.security.idm.IdentityStore;
import oracle.security.idm.Role;
import oracle.security.idm.SearchParameters;
import oracle.security.idm.SearchResponse;
import oracle.security.idm.SimpleSearchFilter;
import oracle.security.idm.UserProfile;
import oracle.security.jps.JpsContext;
import oracle.security.jps.JpsContextFactory;
import oracle.security.jps.JpsException;
import oracle.security.jps.service.idstore.IdentityStoreService;

public class UserDAO {
    public UserDAO() {
    }
    
    /**
     * Method for changing password that returns success if password is changed
     * and error codes incase of failure
     * @param userID
     * @param oldPassword
     * @param newPassword
     * @return BaseBean
     */
    public BaseBean changePasswordWS(String userID, String oldPassword, String newPassword) {
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".changePasswordWS : "+"UserDAO.chnagePasswordWS : userID=" + userID + " oldPassword=" + oldPassword + " newPassword=" + newPassword);
        long startTime = System.currentTimeMillis();
        OIMUserManagermentImpl oIMUserManagermentImpl = DAOFactory.getInstance().getOIMUserManagermentImpl();
        OimUserManagementResult oimResult = oIMUserManagermentImpl.changePassword(userID, oldPassword, newPassword);
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".changePasswordWS : "+"  OIM Change Password  "+"Response Time: [" +elapsedTime +"] milliseconds ");
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".changePasswordWS : "+"oimResult " + oimResult.getWServiceStatus());
        //parse response
        return parseOimUserManagementResult(oimResult);
    }


    /**
     * Method for creating user using web service
     * @param  user
     * @return BaseBean
     * @throws DatatypeConfigurationException
     */
    public BaseBean createUserWS(User user) throws DatatypeConfigurationException {

        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".createUserWS : "+"UserDAO.createUserWS:-------->" + user);
        //create request object
        Identity identity =new Identity();
        try{
         identity = createIdentityFromUser(user);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(this.getClass()+".createUserWS :"+displayIdentity(identity));

        long startTime = System.currentTimeMillis();
        OIMUserManagermentImpl  oIMUserManagermentImpl = DAOFactory.getInstance().getOIMUserManagermentImpl();
        OimUserManagementResult oimResult = oIMUserManagermentImpl.createOIMUser(identity, "portal");
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".createUserWS : "+"  OIM create User  "+"Response Time: [" +elapsedTime +"] milliseconds ");
         //parse response
        return parseOimUserManagementResult(oimResult);
    }

    private String displayIdentity(Identity identity) {
        StringBuilder sb = new StringBuilder();
        sb.append("Displaying Identity Starts:::::::::");
        sb.append("identity.getUserRoles():<" + identity.getUserRoles() + ">");
        sb.append("--identity.getEmployeeNumber():<" + identity.getEmployeeNumber() + ">");
        sb.append("--identity.getAssociatedAirportID():<" + identity.getAssociatedAirportID() + ">");
        sb.append("--identity.getAviationEmployeeID():<" + identity.getAviationEmployeeID() + ">");
        sb.append("--identity.getB2BEmployeeCustomerID():<" + identity.getB2BEmployeeCustomerID() + ">");
        sb.append("--identity.getB2BManagerCustomerID():<" + identity.getB2BManagerCustomerID() + ">");
        sb.append("--identity.getB2CCustomerID():<" + identity.getB2CCustomerID() + ">");
        sb.append("--identity.getDOB():<" + identity.getDOB() + ">");
        sb.append("--identity.getEmail():<" + identity.getEmail() + ">");
        sb.append("--identity.getExternalCSR():<" + identity.getExternalCSR() + ">");
        sb.append("--identity.getExternalUSERTYPE():<" + identity.getExternalUSERTYPE() + ">");
        sb.append("--identity.getFirstName():<" + identity.getFirstName() + ">");
        sb.append("--identity.getInternalCSR():<" + identity.getInternalCSR() + ">");
        sb.append("--identity.getLastName():<" + identity.getLastName() + ">");
        sb.append("--identity.getMarineEmployee():<" + identity.getMarineEmployee() + ">");
        sb.append("--identity.getMarineManager():<" + identity.getMarineManager() + ">");
        sb.append("--identity.getMiddleName():<" + identity.getMiddleName() + ">");
        sb.append("--identity.getPhoneNumber():<" + identity.getPhoneNumber() + ">");
        sb.append("--identity.getPosition():<" + identity.getPosition() + ">");
        sb.append("--identity.getResellerCustomerID():<" + identity.getResellerCustomerID() + ">");
        sb.append("--identity.getResellerEmployeeID():<" + identity.getResellerEmployeeID() + ">");
        sb.append("--identity.getSupplierID():<" + identity.getSupplierID() + ">");
        sb.append("--identity.getUserLang():<" + identity.getUserLang() + ">");
        sb.append("--identity.getUserRoles():<" + identity.getUserRoles() + ">");
        sb.append("--identity.getWebshopManager():<" + identity.getWebshopManager() + ">");
        sb.append("--identity.getAddRoleList():<" + identity.getAddRoleList() + ">");
        sb.append("--identity.getRemoveRoleList():<" + identity.getRemoveRoleList() + ">");
        sb.append("--identity.getDesignation():<" + identity.getDesignation() + ">");
        sb.append("--identity.getSiteIDs():<" + identity.getSiteIDs() + ">");
        
        /* Engage Portal Starts here*/
        
        sb.append("--identity.getCARDADMINID():<" + identity.getCARDADMINID() + ">");
        sb.append("--identity.getCARDB2BADMINID():<" + identity.getCARDB2BADMINID() + ">");
        sb.append("--identity.getCARDB2BEMPID():<" + identity.getCARDB2BEMPID() + ">");
        sb.append("--identity.getCARDB2BMGRID():<" + identity.getCARDB2BMGRID() + ">");
        sb.append("--identity.getCARDB2CJETID():<" + identity.getCARDB2CJETID() + ">");
        sb.append("--identity.getCARDB2CPETROID():<" + identity.getCARDB2CPETROID() + ">");
        sb.append("--identity.getCARDB2CSFRID():<" + identity.getCARDB2CSFRID() + ">");
        sb.append("--identity.getCARDCSRID():<" + identity.getCARDCSRID() + ">");
        sb.append("--identity.getCARDSALESREPID():<" + identity.getCARDSALESREPID() + ">");
        
        
        /*Engage Portal ends here*/
        sb.append("Displaying Identity ends::::::::::::");
        return sb.toString();
    }

    /**
     * Method for deleting user using webservice.
     * @param userID
     * @return BaseBean
     */
    public BaseBean deleteUserWS(String userID) {
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".deleteUserWS : "+"UserDAO.deleteUserWS:   <" + userID + ">");
        long startTime = System.currentTimeMillis();
        OIMUserManagermentImpl  oIMUserManagermentImpl = DAOFactory.getInstance().getOIMUserManagermentImpl();
        OimUserManagementResult oimResult = oIMUserManagermentImpl.deleteOIMUser(userID);
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".deleteUserWS : "+"  OIM delete User  "+"Response Time: [" +elapsedTime +"] milliseconds ");
       
        //parse response
        return parseOimUserManagementResult(oimResult);
    }


    // Update will overwrite customer id as per current logic
    /**
     * Method for updating user using webservice
     * @param user
     * @return BaseBean
     * @throws DatatypeConfigurationException
     */
    public BaseBean updateUserWS(User user) throws DatatypeConfigurationException {

        //create request object
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".updateUserWS : "+"UserDAO.updateUserWS:  Printing User Object     ::::" + user);

        Identity identity =new Identity();
               try{
                identity = createIdentityFromUser(user);
               }
               catch(Exception e){
                       e.printStackTrace();
               } 

        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".updateUserWS : "+"Displaying Identity for Update User"+displayIdentity(identity));
        long startTime = System.currentTimeMillis();
        OIMUserManagermentImpl  oIMUserManagermentImpl = DAOFactory.getInstance().getOIMUserManagermentImpl();
        OimUserManagementResult oimResult = oIMUserManagermentImpl.updateOIMUser(identity, "portal");
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".updateUserWS : "+"  OIM update User  "+"Response Time: [" +elapsedTime +"] milliseconds ");
        //parse response
        return parseOimUserManagementResult(oimResult);
    }


    /**
     * Method for searching user using webservice
     * @param customerId
     * @return List of searched users
     * @throws NumberFormatException
     */
    public List<User> searchUserWS(String customerId) throws NumberFormatException {
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWS : "+"userDAO cust id " + customerId);

        long startTime = System.currentTimeMillis();
        OIMUserManagermentImpl  oIMUserManagermentImpl = DAOFactory.getInstance().getOIMUserManagermentImpl();
        OimUserManagementResult oimResult = oIMUserManagermentImpl.searchOIMUser(customerId);
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWS : "+"  OIM search User  "+"Response Time: [" +elapsedTime +"] milliseconds ");
       
        if (null != oimResult) {
            try {
                return parseOimUserManagementResultToUsers(oimResult);
            } catch (Exception e) {
                // TODO: Add catch code
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return null;
    }


    /**
     * Method to convert date to UTC format
     * @param selectedDate
     * @return Date in UTC format
     * @throws ParseException
     */
    public Date convertDateValueToUTCFormat(String selectedDate) throws ParseException {
        String date = null;

        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss zzz yyyy");
        Date dateInUTCFormat = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date selectedDateFormat = sdf.parse(selectedDate);
            date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")).format(selectedDateFormat);
            dateInUTCFormat = formatter.parse(date);
            System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".convertDateValueToUTCFormat : "+"dateInUTCFormat " + dateInUTCFormat);
        } catch (ParseException e) {
            e.printStackTrace();
            //throw e;
        }
        return dateInUTCFormat;
    }


    /**
     * Method to convert date to ISO8601Format
     * @param selectedDate
     * @return
     * @throws ParseException
     */
    public static Date convertDateValueFromISO8601Format(String selectedDate) throws ParseException {

        Date dateInISO8601 = null;

        try {
            //selectedDate = "20100112230100z";
            selectedDate = selectedDate.replace("z", "UTC");
            DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssz");
            dateInISO8601 = formatter.parse(selectedDate);
            System.out.println(AccessDataControl.getDisplayRecord()+"UserDAO.convertDateValueFromISO8601Format : "+"dateInISO8601 " + selectedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateInISO8601;
    }

    /**
     * method to serch user with userId
     * @param userId
     * @return User
     * @throws Exception
     */
    public User searchUserWithUserId(String userId) throws Exception {
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"UserDAO.searchUserWithUserId : UserId=<" + userId + ">");
        User user = new User();
        JpsContext jpsCtx;
        IdentityStoreService service;
        IdentityStore idStore;
        try {

            jpsCtx = JpsContextFactory.getContextFactory().getContext();
            service = jpsCtx.getServiceInstance(IdentityStoreService.class);
            idStore = service.getIdmStore();
            SimpleSearchFilter ssfilter;
            if (userId.contains("@")) {
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"OPSS search using MAIL");
                ssfilter = idStore.getSimpleSearchFilter("mail", SimpleSearchFilter.TYPE_EQUAL, userId);
            } else {
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"OPSS search using USER_NAME");
                ssfilter = idStore.getSimpleSearchFilter(UserProfile.USER_NAME, SimpleSearchFilter.TYPE_EQUAL, userId);
                System.out.println("value of ssfilter======>"+ssfilter.getValue());
            }
            SearchParameters params = new SearchParameters(ssfilter, SearchParameters.SEARCH_USERS_ONLY);
            System.out.println("value of [params======>"+params);
            SearchResponse response = idStore.searchProfiles(params);

            oracle.security.idm.Identity id = null;
            System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"UserDAO.searchUserWithUserId : Identity=<" + id + ">");

            while (response.hasNext()) {

                id = response.next();
            }
            UserProfile profile = (UserProfile)id;
            user = new User();
            if (profile != null) {

                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"UserDAO.searchUserWithUserId : Input UserId=<" + userId + ">");
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"UserDAO.searchUserWithUserId : profile.USER_ID=<" + profile.USER_ID + ">");
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"UserDAO.searchUserWithUserId : profile.getUserID()=<" + profile.getUserID() + ">");
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"UserDAO.searchUserWithUserId : profile.USER_NAME=<" + profile.USER_NAME + ">");
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"UserDAO.searchUserWithUserId : profile.getUserName()=<" + profile.getUserName() + ">");
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"UserDAO.searchUserWithUserId : profile.MT_UID=<" + profile.MT_UID + ">");
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"UserDAO.searchUserWithUserId : profile.getAllUserProperties()=<" + profile.getAllUserProperties() + ">");
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"UserDAO.searchUserWithUserId : profile=<" + profile + ">");
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"UserDAO.searchUserWithUserId : profile.getPropertyVal(mail)=<" + profile.getPropertyVal("mail") + ">");


                if (null != userId) {
                    user.setUserID(userId.trim());
                }
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"email ID:" + profile.getPropertyVal(Constants.OPSS_MAIL));
                if (null != profile.getPropertyVal(Constants.OPSS_MAIL)) {
                    user.setEmailID((String)profile.getPropertyVal(Constants.OPSS_MAIL));
                }

                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"IS CUSTOMER ACTIVE:" + profile.getPropertyVal(Constants.OPSS_ORCL_IS_ENABLED));
                if (profile.getPropertyVal(Constants.OPSS_ORCL_IS_ENABLED) != null) {
                    user.setActive(profile.getPropertyVal(Constants.OPSS_ORCL_IS_ENABLED).toString().equalsIgnoreCase("enabled") ? true : false);
                }


                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"First name:" + profile.getFirstName());
                if (null != profile.getFirstName()) {
                    user.setFirstName(profile.getFirstName().trim());
                }
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Last name:" + profile.getLastName());
                if (null != profile.getLastName()) {
                    user.setLastName(profile.getLastName().trim());
                }
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Middle name:" + profile.getPropertyVal(Constants.OPSS_MIDDLE_NAME));
                if (null != profile.getPropertyVal(Constants.OPSS_MIDDLE_NAME)) {
                    user.setMiddleName((String)profile.getPropertyVal(Constants.OPSS_MIDDLE_NAME));
                }
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"DOB from OPSS:" + profile.getPropertyVal(Constants.OPSS_DOB));
                if (null != profile.getPropertyVal(Constants.OPSS_DOB)) {
                    user.setDob(convertDateValueFromISO8601Format((String)profile.getPropertyVal(Constants.OPSS_DOB)));
                    System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"DOB from converted:" +
                                       convertDateValueFromISO8601Format((String)profile.getPropertyVal(Constants.OPSS_DOB)));
                }

                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"User Position " + profile.getEmployeeType());
                if (null != profile.getEmployeeType()) {
                    user.setPosition(profile.getEmployeeType());
                }

                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Phone:" + profile.getBusinessMobile());
                if (null != profile.getBusinessMobile()) {
                    user.setPhoneNumber(profile.getBusinessMobile());
                }
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"lang :" + profile.getPropertyVal(Constants.OPSS_LANG));
                if (null != profile.getPropertyVal(Constants.OPSS_LANG)) {
                    user.setLang((String)profile.getPropertyVal(Constants.OPSS_LANG));
                }
                
                if (null != profile.getPropertyVal("designation")) {
                    user.setDesignation((String)profile.getPropertyVal("designation"));
                }
                if (null != profile.getPropertyVal("title")) {
                    user.setPrimarySiteID(Integer.parseInt((String)profile.getPropertyVal("title")));
                }
                
                String roleList = getUserRoleList(idStore, id);
                if (null != roleList) {
                    user.setRolelist(roleList);
                }
                /*To check whether user is external or internal
                  user is set as external if searchType is External, for all other values user will be considered as Internal*/
                if (profile.getPropertyVal(Constants.searchType) != null && profile.getPropertyVal(Constants.searchType).toString().equalsIgnoreCase(Constants.External)) {
                    user.setInternal(false);
                } else {
                    user.setInternal(true);
                }

//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"isInternal:" + profile.getPropertyVal(Constants.searchType));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"CustomerId:" + profile.getPropertyVal("customerId"));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"employeeNumber:" + profile.getPropertyVal("employeeNumber"));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"B2B Manager:" + profile.getPropertyVal(Constants.OPSS_WCP_B2BM));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"B2B Employee" + profile.getPropertyVal(Constants.OPSS_WCP_B2BEMP));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"B2B Customer:" + profile.getPropertyVal(Constants.OPSS_WCP_B2C));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Aviation Supervisor:" + profile.getPropertyVal(Constants.OPSS_WCP_AVSUP));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Reseller Mgr:" + profile.getPropertyVal(Constants.OPSS_WCP_RESM));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"External SS mgr:" + profile.getPropertyVal(Constants.OPSS_WCP_ESSM));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"External SS Employee:" + profile.getPropertyVal(Constants.OPSS_WCP_ESSEMP));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Internal SS mgr:" + profile.getPropertyVal(Constants.OPSS_WCP_ISSM));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Internal SS Employee:" + profile.getPropertyVal(Constants.OPSS_WCP_ISSEMP));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Reseller Employee:" + profile.getPropertyVal(Constants.OPSS_WCP_RESEMP));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Internal CSR:" + profile.getPropertyVal(Constants.OPSS_WCP_ICSR));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Aviation Employee:" + profile.getPropertyVal(Constants.OPSS_WCP_AVEMP));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Marine Sup:" + profile.getPropertyVal(Constants.OPSS_WCP_MRSUP));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Marine Emp:" + profile.getPropertyVal(Constants.OPSS_WCP_MREMP));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Webshop mgr:" + profile.getPropertyVal(Constants.OPSS_WCP_WSM));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"designation:" + profile.getPropertyVal("designation"));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"title:" + profile.getPropertyVal("title"));
//                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"SiteIDs:" + profile.getPropertyVal("sstation_ManagerExt"));
                
                /*Enage Portal starts here*/
                
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Card B2b mager" + profile.getPropertyVal(Constants.OPSS_WCP_CARD_ADMIN));
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Card petro" + profile.getPropertyVal(Constants.OPSS_WCP_CARD_B2C_PETRO));
                
                
                
                /* Engage portal end here*/
                

                user.setRoleList(new ArrayList<Roles>());

                if (roleList.contains(Constants.ROLE_WCP_AVSUP)) {
    //                    user.setWcp_AVSUP_CustomerID(converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_AVSUP)));
                    List<String> apID=converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_AVSUP));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_AVSUP, null, apID, user.getRoleList()));
                }
                if (roleList.contains(Constants.ROLE_WCP_AVEMP)) {
    //                    user.setWcp_AVEMP_CustomerID(converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_AVEMP)));
                    List<String> apID=converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_AVEMP));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_AVEMP, null, apID, user.getRoleList()));
                }

                if (roleList.contains(Constants.ROLE_WCP_B2C)) {
    //                    user.setWcp_B2C_CustomerID(converttolist(profile.getPropertyVal(Constants.OPSS_WCP_B2C)));
                    List<Integer> custID=converttolist(profile.getPropertyVal(Constants.OPSS_WCP_B2C));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_B2C, custID, null, user.getRoleList()));
                }
                if (roleList.contains(Constants.ROLE_WCP_B2BM)) {
    //                    user.setWcp_B2BM_CustomerID(converttolist(profile.getPropertyVal(Constants.OPSS_WCP_B2BM)));
                    List<Integer> custID=converttolist(profile.getPropertyVal(Constants.OPSS_WCP_B2BM));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_B2BM, custID, null, user.getRoleList()));

                }
                if (roleList.contains(Constants.ROLE_WCP_B2BEMP)) {
    //                    user.setWcp_B2BEMP_CustomerID(converttolist(profile.getPropertyVal(Constants.OPSS_WCP_B2BEMP)));
                    List<Integer> custID=converttolist(profile.getPropertyVal(Constants.OPSS_WCP_B2BEMP));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_B2BEMP, custID, null, user.getRoleList()));
                }
                if (roleList.contains(Constants.ROLE_WCP_RESM)) {
    //                    user.setWcp_RESM_CustomerID(converttolist(profile.getPropertyVal(Constants.OPSS_WCP_RESM)));
                    List<Integer> custID=converttolist(profile.getPropertyVal(Constants.OPSS_WCP_RESM));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_RESM, custID, null, user.getRoleList()));
                }
                if (roleList.contains(Constants.ROLE_WCP_RESEMP)) {
    //                    user.setWcp_RESEMP_CustomerID(converttolist(profile.getPropertyVal(Constants.OPSS_WCP_RESEMP)));
                    List<Integer> custID=converttolist(profile.getPropertyVal(Constants.OPSS_WCP_RESEMP));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_RESEMP, custID, null, user.getRoleList()));
                }
                if (roleList.contains(Constants.ROLE_WCP_ICSR)) {
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ICSR, user.getRoleList()));
                }
                
                if (roleList.contains(Constants.ROLE_WCP_ISSM) ) {
                    List<Integer> custID = converttolist(profile.getPropertyVal(Constants.OPSS_WCP_ESSM));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ISSM, custID, null, user.getRoleList()));
                }
                    
                if ( roleList.contains(Constants.ROLE_WCP_ESSM) ) {
                    List<Integer> custID = converttolist(profile.getPropertyVal(Constants.OPSS_WCP_ESSM));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ESSM, custID, null, user.getRoleList()));
                }    
                if (roleList.contains(Constants.ROLE_WCP_ISSEMP) ) {
                    List<Integer> custID = converttolist(profile.getPropertyVal(Constants.OPSS_WCP_ESSM));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ISSEMP, custID, null, user.getRoleList()));
                }
                    
                if ( roleList.contains(Constants.ROLE_WCP_ESSEMP) ) {
                    List<Integer> custID = converttolist(profile.getPropertyVal(Constants.OPSS_WCP_ESSM));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ESSEMP, custID, null, user.getRoleList()));
                }   
                    
                                                
               /* 
                if (roleList.contains(Constants.ROLE_WCP_ISSM)) {
                    List<Integer> li = new ArrayList<Integer>();
                    try {
                        if (null != profile.getPropertyVal("employeeNumber")) {
                            Integer i = Integer.parseInt(profile.getPropertyVal("employeeNumber").toString());
                            if (null != i) {
                                li.add(i);
    //                                user.setWcp_ISSM_CustomerID(li);
                                user.setEmployeeNumber(li);
                            }
                        }
                    } catch (Exception nfe) {
                        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"ERROR IN TYPE CAST:" + nfe.getCause() + ":" + nfe.getMessage());
                    }
    //                    if (user.getWcp_ISSM_CustomerID() == null || user.getWcp_ISSM_CustomerID().size() == 0)
                        li.addAll(converttolist(profile.getPropertyVal(Constants.OPSS_WCP_ISSM)));
                    
                    List<Integer> custID=li;
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ISSM, custID, null, user.getRoleList()));
                }
                
                if (roleList.contains(Constants.ROLE_WCP_ESSM)) {
                    List<Integer> li = new ArrayList<Integer>();
                    try {
                        if (null != profile.getPropertyVal("employeeNumber")) {
                            Integer i = Integer.parseInt(profile.getPropertyVal("employeeNumber").toString());
                            if (null != i) {
                                li.add(i);
    //                                user.setWcp_ESSM_CustomerID(li);
                                user.setEmployeeNumber(li);
                            }
                        }
                    } catch (Exception nfe) {
                        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"ERROR IN TYPE CAST:" + nfe.getCause() + ":" + nfe.getMessage());
                    }
    //                    if (user.getWcp_ESSM_CustomerID() == null || user.getWcp_ESSM_CustomerID().size() == 0)
                        li.addAll(converttolist(profile.getPropertyVal(Constants.OPSS_WCP_ESSM)));
                    
                    List<Integer> custID=li;
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ESSM, custID, null, user.getRoleList()));
                }
                
                if (roleList.contains(Constants.ROLE_WCP_ISSEMP)) {
                    List<Integer> li = new ArrayList<Integer>();
                    try {
                        if (null != profile.getPropertyVal("employeeNumber")) {
                            Integer i = Integer.parseInt(profile.getPropertyVal("employeeNumber").toString());
                            if (null != i) {
                                li.add(i);
    //                                user.setWcp_ISSEMP_CustomerID(li);
                                user.setEmployeeNumber(li);
                            }
                        }
                    } catch (Exception nfe) {
                        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"ERROR IN TYPE CAST:" + nfe.getCause() + ":" + nfe.getMessage());
                    }
    //                    if (user.getWcp_ISSEMP_CustomerID() == null || user.getWcp_ISSEMP_CustomerID().size() == 0)
                        li.addAll(converttolist(profile.getPropertyVal(Constants.OPSS_WCP_ISSEMP)));
                    
                    List<Integer> custID=li;
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ISSEMP, custID, null, user.getRoleList()));
                }
                
                if (roleList.contains(Constants.ROLE_WCP_ESSEMP)) {
    //                    user.setWcp_ESSEMP_CustomerID(converttolist(profile.getPropertyVal(Constants.OPSS_WCP_ESSEMP)));
                    List<Integer> custID=converttolist(profile.getPropertyVal(Constants.OPSS_WCP_ESSEMP));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ESSEMP, custID, null, user.getRoleList()));

                }
                */
                if (roleList.contains(Constants.ROLE_WCP_MRSUP)) {
    //                    user.setWcp_MRSUP_CustomerID(converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_MRSUP)));
                    List<String> apid=converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_MRSUP));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_MRSUP, null, apid, user.getRoleList()));

                }
                if (roleList.contains(Constants.ROLE_WCP_MREMP)) {
    //                    user.setWcp_MREMP_CustomerID(converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_MREMP)));
                    List<String> apid=converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_MREMP));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_MREMP, null, apid, user.getRoleList()));
                }
                if (roleList.contains(Constants.ROLE_WCP_WSM)) {
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_WSM,  user.getRoleList()));
                }
                if (roleList.contains(Constants.ROLE_WCP_ECSR)) {
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ECSR,  user.getRoleList()));
                }
                if (roleList.contains(Constants.ROLE_WCP_SUPP)) {
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_SUPP,  user.getRoleList()));
                }
                if (roleList.contains(Constants.ROLE_WCP_SSSUPP)) {
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_SSSUPP, user.getRoleList()));
                }
                
                if(user.getRolelist().contains(Constants.ROLE_WCP_WEBSUP)) {
                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_WEBSUP, user.getRoleList()));  
                      
                }  
                
                
                if(user.getRolelist().contains(Constants.ROLE_WCP_AVISUP)) {
                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_AVISUP, user.getRoleList()));     
                }
                
                if(user.getRolelist().contains(Constants.ROLE_WCP_MARSUP)) {
                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_MARSUP, user.getRoleList()));     
                }
                
                if(user.getRolelist().contains(Constants.ROLE_WCP_RESSUP)) {
                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_RESSUP, user.getRoleList()));     
                }
                
                /* Engage Portal Roles List for OPSS*/
                
                if (roleList.contains(Constants.ROLE_WCP_CARD_B2C_SFR)) {
                //                    user.setWcp_AVEMP_CustomerID(converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_AVEMP)));
                    List<String> custID=converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_CARD_B2C_SFR));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_B2C_SFR, null, custID, user.getRoleList()));
                }
                
                if (roleList.contains(Constants.ROLE_WCP_CARD_B2C_JET)) {
                //                    user.setWcp_AVEMP_CustomerID(converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_AVEMP)));
                    List<String> custID=converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_CARD_B2C_JET));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_B2C_JET, null, custID, user.getRoleList()));
                }
                
                if (roleList.contains(Constants.ROLE_WCP_CARD_B2B_ADMIN)) {
                //                    user.setWcp_AVEMP_CustomerID(converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_AVEMP)));
                    List<String> custID=converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_CARD_B2B_ADMIN));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_B2B_ADMIN, null, custID, user.getRoleList()));
                }
                
                if (roleList.contains(Constants.ROLE_WCP_CARD_B2B_MGR)) {
                //                    user.setWcp_AVEMP_CustomerID(converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_AVEMP)));
                    List<String> custID=converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_CARD_B2B_MGR));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_B2B_MGR, null, custID, user.getRoleList()));
                }
                
                if (roleList.contains(Constants.ROLE_WCP_CARD_B2C_PETRO)) {
                //                    user.setWcp_AVEMP_CustomerID(converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_AVEMP)));
                    List<String> custID=converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_CARD_B2C_PETRO));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_B2C_PETRO, null, custID, user.getRoleList()));
                }
                
                if (roleList.contains(Constants.ROLE_WCP_CARD_B2B_EMP)) {
                //                    user.setWcp_AVEMP_CustomerID(converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_AVEMP)));
                    List<String> custID=converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_CARD_B2B_EMP));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_B2B_EMP, null, custID, user.getRoleList()));
                }
                
                if (roleList.contains(Constants.ROLE_WCP_CARD_ADMIN)) {
                //                    user.setWcp_AVEMP_CustomerID(converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_AVEMP)));
                    System.out.println("property value of=====================>"+profile.getPropertyVal(Constants.OPSS_WCP_CARD_ADMIN));
                    List<String> custID=converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_CARD_ADMIN));
                    System.out.println("List of WCP card admin================================================================>"+custID);
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_ADMIN, null, custID, user.getRoleList()));
                }
                
                if (roleList.contains(Constants.ROLE_WCP_CARD_CSR)) {
                //                    user.setWcp_AVEMP_CustomerID(converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_AVEMP)));
                    List<String> custID=converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_CARD_CSR));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_CSR, null, custID, user.getRoleList()));
                }
                
                if (roleList.contains(Constants.ROLE_WCP_CARD_SALES_REP)) {
                //                    user.setWcp_AVEMP_CustomerID(converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_AVEMP)));
                    List<String> custID=converttolistString(profile.getPropertyVal(Constants.OPSS_WCP_CARD_SALES_REP));
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_SALES_REP, null, custID, user.getRoleList()));
                }
                
                /*if (roleList.contains(Constants.ROLE_WCP_CARD_ADMIN)) {
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_ADMIN, user.getRoleList()));
                }
                
                if (roleList.contains(Constants.ROLE_WCP_CARD_CSR)) {
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_CSR, user.getRoleList()));
                }
                
                if (roleList.contains(Constants.ROLE_WCP_CARD_SALES_REP)) {
                    user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_SALES_REP, user.getRoleList()));
                }*/
                
                /* Engage Portal Roles List Ended for OPSS*/
            }
        }


        catch (oracle.security.idm.ObjectNotFoundException noobjex) {
            noobjex.printStackTrace();
            user.setStatus("Error");
            BusinessError be = new BusinessError();
            be.setErrorMessage("User does not exist");
            List<BusinessError> li = new ArrayList<BusinessError>();
            li.add(be);
            user.setErrorList(li);
        } catch (Exception e) {
            e.printStackTrace();
            user.setStatus("Error");
            BusinessError be = new BusinessError();
            be.setErrorMessage("User does not exist");
            List<BusinessError> li = new ArrayList<BusinessError>();
            li.add(be);
            user.setErrorList(li);
            // throw e;
        }
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+user);
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".searchUserWithUserId : "+"Exiting searchUserWithUserId");
        return user;
    }

    private List<Integer> converttolist(Object cid) {
        List<Integer> custids = new ArrayList<Integer>();
        if (null != cid && !cid.toString().isEmpty() && cid.toString().contains("?")) {
            return custids;
        }
        if (null != cid && !cid.toString().isEmpty() && cid.toString().contains("-")) {
            return custids;
        }
        if (null != cid && !cid.toString().isEmpty() && cid.toString().contains("|")) {
            String customerIdString1 = (String)cid;
            customerIdString1=customerIdString1.replaceAll("[|]+", "|");
            String customerIdString = customerIdString1.substring(1, customerIdString1.length() - 1);

            String[] customerIDArr = customerIdString.split("\\|");

            List<Integer> customerID = new ArrayList<Integer>();
            for (int iCount = 0; iCount < customerIDArr.length; iCount++) {
                try {
                    customerID.add(Integer.parseInt(customerIDArr[iCount]));
                } catch (Exception e) {
                    System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".converttolist : "+"Number format exception-->" + e);
                }
            }

            custids.addAll(customerID);
        }
        //        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".methodName : "+"UserDAO.converttolist: User customer id " + custids.size());
        return custids;
    }
    
    private List<String> converttolistString(Object cid) {
        List<String> custids = new ArrayList<String>();
        if (null != cid && !cid.toString().isEmpty() && cid.toString().contains("?")) {
            return custids;
        }
        if (null != cid && !cid.toString().isEmpty() && cid.toString().contains("-")) {
            return custids;
        }
        if (null != cid && !cid.toString().isEmpty() && cid.toString().contains("|")) {
            String customerIdString1 = (String)cid;
            customerIdString1=customerIdString1.replaceAll("[|]+", "|");
            String customerIdString = customerIdString1.substring(1, customerIdString1.length() - 1);

            String[] customerIDArr = customerIdString.split("\\|");

            List<String> customerID = new ArrayList<String>();
            for (int iCount = 0; iCount < customerIDArr.length; iCount++) {
                try {
                    customerID.add(customerIDArr[iCount]);
                } catch (Exception e) {
                    System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".converttolist : "+" exception-->" + e);
                }
            }

            custids.addAll(customerID);
        }
        //        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".methodName : "+"UserDAO.converttolist: User customer id " + custids.size());
        return custids;
    }

    private List<String> convertRoleStringToList(Object cid) {
        List<String> listOfRoles = new ArrayList<String>();
        if (null != cid && !cid.toString().isEmpty() && cid.toString().contains("|")) {
            String customerIdString1 = (String)cid;
            customerIdString1=customerIdString1.replaceAll("[|]+", "|");
            String customerIdString = customerIdString1.substring(1, customerIdString1.length() - 1);

            String[] roleArray = customerIdString.split("\\|");

            List<String> roleName = new ArrayList<String>();
            for (int iCount = 0; iCount < roleArray.length; iCount++) {
                try {
                    roleName.add(roleArray[iCount]);
                } catch (Exception e) {
                    System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".converttolist : "+" exception-->" + e);
                }
            }

            listOfRoles.addAll(roleName);
        }
        //        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".methodName : "+"UserDAO.converttolist: User customer id " + listOfRoles.size());
        return listOfRoles;
    }

    private String getUserRoleList(IdentityStore idStore, oracle.security.idm.Identity id) {
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".getUserRoleList : "+"Inside getUserRoleList");
        Role role = null;
        String roleList = "";
        try{
            System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".getUserRoleList : "+"id.getPrincipal()::" + id.getPrincipal());
            SearchResponse responseRoles = idStore.getRoleManager().getGrantedRoles(id.getPrincipal(), true);
            System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".getUserRoleList : "+"id.getPrincipal()::" + id.getPrincipal());
            while (responseRoles.hasNext()) {
                role = (Role)responseRoles.next();
                if (roleList.equals("")) {
                    roleList = role.getName();
                } else {
                    roleList = roleList + "|" + role.getName();
                }
            }
            if(roleList!=null){
                roleList="|"+ roleList+"|";
            }
            if (null != roleList) {
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".getUserRoleList : "+"Rolelist " + roleList);
            }
        }catch(Exception e){
            System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".getUserRoleList : "+"Exception Occured:<"+e.getCause()+"><"+e.getMessage()+">");
            e.printStackTrace();
            roleList="";
        }
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".getUserRoleList : "+"Exiting getUserRoleList");
        return roleList;
    }


    /**
     * Method for checking whether change password is required for the user on login
     * @param userId
     * @return BaseBean
     */
    public BaseBean isPasswordChangeRequiredWS(String userId) {
        long startTime = System.currentTimeMillis();
        OIMUserManagermentImpl  oIMUserManagermentImpl = DAOFactory.getInstance().getOIMUserManagermentImpl();
        OimUserManagementResult oimResult = oIMUserManagermentImpl.isUserChangePasswordNextLogin(userId);
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".isPasswordChangeRequiredWS : "+"  OIM Password Required  "+"Response Time: [" +elapsedTime +"] milliseconds ");
            //parse response
        return parseOimUserManagementResult(oimResult);
    }


    /**
     * Method which provides new password incase of forgot password
     * @param userId
     * @return BaseBean
     */
    public BaseBean forgotPasswordWS(String userId) {
        OimUserManagementResult oimResult = new OimUserManagementResult();
        try {
            System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".forgotPasswordWS : "+"Forgot Password called for USer id: " + userId);
            long startTime = System.currentTimeMillis();
            OIMUserManagermentImpl  oIMUserManagermentImpl = DAOFactory.getInstance().getOIMUserManagermentImpl();
            oimResult = oIMUserManagermentImpl.forgotPassword(userId);

            long elapsedTime = System.currentTimeMillis() - startTime;
            System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".forgotPasswordWS : "+"  OIM Forgot Password  "+"Response Time: [" +elapsedTime +"] milliseconds ");
           
        } catch (Exception r) {
            r.printStackTrace();

        }
        //parse response
        return parseOimUserManagementResult(oimResult);


    }

    private Identity createIdentityFromUser(User user) throws DatatypeConfigurationException,NullPointerException {
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".createIdentityFromUser : "+"USER Inside createIdentityFromUser::::::::::::::::");
        Identity identity = new Identity();
        if (null != user) {
            identity.setUserRoles(user.getRolelist());
            identity.setEmail(user.getEmailID());
            identity.setFirstName(user.getFirstName());
            identity.setMiddleName(user.getMiddleName());
            identity.setLastName(user.getLastName());
            identity.setPhoneNumber(user.getPhoneNumber());
            identity.setPosition(user.getPosition());
            identity.setUserLang(user.getLang());
            if (null != user.getPrimarySiteID())
            identity.setTitle(user.getPrimarySiteID().toString());
            identity.setDesignation(user.getDesignation());
            
            if (null != user.getDob())
                identity.setDOB(convertDateToXMLGregorianCal(user.getDob()));
            if (user.getEmployeeNumber() != null && user.getEmployeeNumber().size() != 0)
                identity.setEmployeeNumber(convertListToString(user.getEmployeeNumber()));
            

            List<Roles> rolelist = user.getRoleList();
            List<String> newRoleList = new ArrayList<String>();
            if (rolelist != null) {
                for (int count = 0; count < rolelist.size(); count++) {
                    Roles role = rolelist.get(count);
                    if (role != null) {
                        addAssociationToIdentity(role.getRoleName(), role.getCustomerID(), role.getIdString(), role.isAssigned(), identity);
                        newRoleList.add(role.getRoleName());
                    }
                }
            }
            List<String> assignedRoles = new ArrayList<String>();
            List<String> revokedRoles = new ArrayList<String>();
            List<String> previousRoles = convertRoleStringToList(user.getRolelist());

            createAssignedAndRevokedRolesList(newRoleList, previousRoles, assignedRoles, revokedRoles);

            if (assignedRoles != null)
                identity.getAddRoleList().addAll(assignedRoles);
            if (revokedRoles != null)
                identity.getRemoveRoleList().addAll(revokedRoles);


        }
        return identity;
    }

    private BaseBean parseOimUserManagementResult(OimUserManagementResult oimResult) {

        BaseBean baseBean = new BaseBean();
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".parseOimUserManagementResult : "+"control");

        if (null != oimResult && null != oimResult.getWServiceStatus()) {
            System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".parseOimUserManagementResult : "+"UserDAO.parseOimUserManagementResult:  oimResult.getWServiceResult()" + oimResult.getWServiceResult());
            if (oimResult.getWServiceStatus().equals("Error")) {
                baseBean.setStatus("Error");
                if (null != oimResult.getOIMwServiceError() && oimResult.getOIMwServiceError().size() > 0) {
                    List<BusinessError> errorList = new ArrayList<BusinessError>();
                    for (int iCount = 0; iCount < oimResult.getOIMwServiceError().size(); iCount++) {
                        BusinessError businessError = new BusinessError();
                        businessError.setErrorCode(oimResult.getOIMwServiceError().get(iCount));
                        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".parseOimUserManagementResult : "+oimResult.getOIMwServiceError().get(iCount));
                        errorList.add(businessError);
                    }
                    baseBean.setErrorList(errorList);
                }
            } else {
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".parseOimUserManagementResult : "+"UserDAO.parseOimUserManagementResult:  Inside success");
                if (oimResult.getWServiceStatus().equals("Success") && oimResult.getWServiceResult().equals("NorecordFound")) {
                    baseBean.setStatus("No Records");
                } else if (null != oimResult.getWServiceResult() && oimResult.getWServiceResult().equals("Change")) {
                    baseBean.setStatus("Yes");
                } else if (null != oimResult.getWServiceResult() && oimResult.getWServiceResult().equals("NoChange")) {
                    baseBean.setStatus("No");
                } else {
                    baseBean.setStatus("Success");
                }
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".parseOimUserManagementResult : "+"UserDAO.parseOimUserManagementResult: baseBean STATUS " + baseBean.getStatus());
            }
        } else {
            System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".parseOimUserManagementResult : "+"oimResult is null");
        }
        return baseBean;
    }


    private String convertListToString(List list) {
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".convertListToString : "+"UserDAO.convertListToString: list to convert-- " + list);
        String result = "" + list.get(0);

        for (int iCount = 1; iCount < list.size(); iCount++) {
            result = result + "|" + list.get(iCount);
        }
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".convertListToString : "+"UserDAO.convertListToString: resultant String -- " + result);
        return result;

    }

    private XMLGregorianCalendar convertDateToXMLGregorianCal(Date date) throws DatatypeConfigurationException {
        GregorianCalendar gregFromDate = new GregorianCalendar();
        gregFromDate.setTime(date);
        XMLGregorianCalendar xmlGregoryDate;
        xmlGregoryDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregFromDate);
        return xmlGregoryDate;
    }


    /*
     * Method for parsing identity object into user object
     */
    private List<User> parseOimUserManagementResultToUsers(OimUserManagementResult oimResult) {
        System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".parseOimUserManagementResultToUsers : "+"parseOimUserManagementResultToUsers");
        List<User> userList = new ArrayList<User>();
        User user = new User();
        if (null != oimResult && null != oimResult.getWServiceStatus()) {
            if (oimResult.getWServiceStatus().equals("Error")) {
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".parseOimUserManagementResultToUsers : "+"oimResult.getOIMwServiceError():::" + oimResult.getOIMwServiceError());
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".parseOimUserManagementResultToUsers : "+"Inside error");
                user.setStatus("Error");
                if (null != oimResult.getOIMwServiceError() && oimResult.getOIMwServiceError().size() > 0) {

                    List<BusinessError> errorList = new ArrayList<BusinessError>();


                    for (int iCount = 0; iCount < oimResult.getOIMwServiceError().size(); iCount++) {
                        BusinessError businessError = new BusinessError();
                        businessError.setErrorCode(oimResult.getOIMwServiceError().get(iCount));
                        errorList.add(businessError);
                    }

                    user.setErrorList(errorList);
                }

                userList.add(user);

                return userList;

            } else {
                System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".parseOimUserManagementResultToUsers : "+"Inside success");

                if (oimResult.getWServiceStatus().equals("Success") && oimResult.getWServiceResult().equals("NorecordFound")) {

                    System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".parseOimUserManagementResultToUsers : "+"Inside success but zero results");

                    return userList;

                } else {

                    System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".parseOimUserManagementResultToUsers : "+"Inside success more than 0 results " + oimResult.getOIMwServiceIdentityResult().size());
                    int numberofUsers = 0;
                    for (Identity identityResult : oimResult.getOIMwServiceIdentityResult()) {
                        numberofUsers++;
                        System.out.print(AccessDataControl.getDisplayRecord()+this.getClass()+".parseOimUserManagementResultToUsers : "+"User Number: " + numberofUsers);

                        if (null != identityResult) {
                            user = new User();
                            System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".parseOimUserManagementResultToUsers : "+"Displaying identity for search users"+displayIdentity(identityResult));
                            
                            if (null != identityResult.getEmail()) {
                                user.setUserID(identityResult.getEmail().trim());
                            }
                            System.out.println(AccessDataControl.getDisplayRecord()+this.getClass()+".parseOimUserManagementResultToUsers : "+"identityResult.getEmail():<" + identityResult.getEmail() + ">");
                            if (null != identityResult.getEmail()) {
                                user.setEmailID(identityResult.getEmail().trim());
                            }
                            
                            if (null != identityResult.getFirstName()) {
                                user.setFirstName(identityResult.getFirstName().trim());
                            }
                            
                            if (null != identityResult.getLastName()) {
                                user.setLastName(identityResult.getLastName().trim());
                            }
                            
                            if (null != identityResult.getMiddleName()) {
                                user.setMiddleName(identityResult.getMiddleName().trim());
                            }
                            
                            if (null != identityResult.getDOB()) {
                                user.setDob(identityResult.getDOB().toGregorianCalendar().getTime());
                            }
                            
                            if (null != identityResult.getPosition()) {
                                user.setPosition(identityResult.getPosition().trim());
                            }
                            
                            if (null != identityResult.getPhoneNumber()) {
                                user.setPhoneNumber(identityResult.getPhoneNumber().trim());
                            }
                            
                            if (null != identityResult.getUserLang()) {
                                user.setLang(identityResult.getUserLang().trim());
                            }
                            
                            if (null != identityResult.getDesignation()) {
                                user.setDesignation(identityResult.getDesignation().trim());
                            }
                            
                            if (null != identityResult.getTitle() && !identityResult.getTitle().isEmpty()) {
                                try{
                                user.setPrimarySiteID(Integer.parseInt(identityResult.getTitle().trim()));
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }

                            if (null != identityResult.getUserRoles()) {
                                user.setRolelist(identityResult.getUserRoles().trim());
                            }else{
                                user.setRolelist("");
                            }
                            System.out.println("Rolelist after WSDL search:"+user.getRolelist());
                            
                            if (null != identityResult.getExternalUSERTYPE() && !identityResult.getExternalUSERTYPE().equals("")) {
                                if(identityResult.getExternalUSERTYPE().equalsIgnoreCase("internal")){
                                    user.setInternal(true);    
                                }else if(identityResult.getExternalUSERTYPE().equalsIgnoreCase("intext")){
                                    user.setInternal(true);    
                                }else if(identityResult.getExternalUSERTYPE().equalsIgnoreCase("external")){
                                    user.setInternal(false);    
                                }else{
                                    user.setInternal(true);    
                                }
                            }else{
                                user.setInternal(true);    
                            }
                            user.setRoleList(new ArrayList<Roles>());
                    
                            if (user.getRolelist().contains(Constants.ROLE_WCP_AVSUP) && identityResult.getAssociatedAirportID() != null && !identityResult.getAssociatedAirportID().isEmpty()) {
    //                                user.setWcp_AVSUP_CustomerID(converttolistString(identityResult.getAssociatedAirportID()));
                                List<String> airportID = converttolistString(identityResult.getAssociatedAirportID());
                                if(airportID==null || airportID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_AVSUP+"but Airport IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_AVSUP, null,airportID, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_AVEMP) &&  identityResult.getAviationEmployeeID() != null && !identityResult.getAviationEmployeeID().isEmpty()) {
    //                                user.setWcp_AVEMP_CustomerID(converttolistString(identityResult.getAviationEmployeeID()));
                                List<String> airportID = converttolistString(identityResult.getAviationEmployeeID());
                                if(airportID==null || airportID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_AVEMP+"but Airport IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_AVEMP, null,airportID, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_B2BEMP) && identityResult.getB2BEmployeeCustomerID() != null) {
    //                                user.setWcp_B2BEMP_CustomerID(converttolist(identityResult.getB2BEmployeeCustomerID()));
                                List<Integer> custID=converttolist(identityResult.getB2BEmployeeCustomerID());
                                if(custID==null || custID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_B2BEMP+"but Customer IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_B2BEMP, custID,null, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_B2BM) && identityResult.getB2BManagerCustomerID() != null) {
    //                                user.setWcp_B2BM_CustomerID(converttolist(identityResult.getB2BManagerCustomerID()));
                                List<Integer> custID=converttolist(identityResult.getB2BManagerCustomerID());
                                if(custID==null || custID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_B2BM+"but Customer IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_B2BM, custID,null, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_B2C) && identityResult.getB2CCustomerID() != null) {
    //                                user.setWcp_B2C_CustomerID(converttolist(identityResult.getB2CCustomerID()));
                                List<Integer> custID=converttolist(identityResult.getB2CCustomerID());
                                if(custID==null || custID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_B2C+"but Customer IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_B2C, custID,null, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_ECSR)) {
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ECSR, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_ICSR)) {
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ICSR, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_MREMP) && identityResult.getMarineEmployee() != null) {
    //                                user.setWcp_MREMP_CustomerID(converttolistString(identityResult.getMarineEmployee()));
                                List<String> custID=converttolistString(identityResult.getMarineEmployee());
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_MREMP, null, custID, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_MRSUP) && identityResult.getMarineManager() != null) {
    //                                user.setWcp_MRSUP_CustomerID(converttolistString(identityResult.getMarineManager()));
                                List<String> custID=converttolistString(identityResult.getMarineManager());
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_MRSUP, null, custID, user.getRoleList()));
                            }
                            

                            if (user.getRolelist().contains(Constants.ROLE_WCP_RESM) && identityResult.getResellerCustomerID() != null) {
    //                                user.setWcp_RESM_CustomerID(converttolist(identityResult.getResellerCustomerID()));
                                List<Integer> custID=converttolist(identityResult.getResellerCustomerID());
                                if(custID==null || custID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_RESM+"but Customer IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_RESM, custID,null, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_RESEMP) && identityResult.getResellerEmployeeID() != null) {
    //                                user.setWcp_RESEMP_CustomerID(converttolist(identityResult.getResellerEmployeeID()));
                                List<Integer> custID=converttolist(identityResult.getResellerEmployeeID());
                                if(custID==null || custID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_RESEMP+"but Customer IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_RESEMP, custID,null, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_ISSM)) {
                                List<Integer> tempList=converttolist(identityResult.getSiteIDs());
                                if(tempList==null || tempList.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_ISSM+"but Customer IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ISSM, tempList,null, user.getRoleList()));  
                            }
                            if (user.getRolelist().contains(Constants.ROLE_WCP_ESSM)) {
                                List<Integer> tempList=converttolist(identityResult.getSiteIDs());
                                if(tempList==null || tempList.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_ESSM+"but Customer IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ESSM, tempList,null, user.getRoleList()));  
                            }
                            if (user.getRolelist().contains(Constants.ROLE_WCP_ISSEMP)) {
                                List<Integer> tempList=converttolist(identityResult.getSiteIDs());
                                if(tempList==null || tempList.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_ISSEMP+"but Customer IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ISSEMP, tempList,null, user.getRoleList()));  
                            } 
                            if (user.getRolelist().contains(Constants.ROLE_WCP_ESSEMP)) {
                                
                                List<Integer> tempList=converttolist(identityResult.getSiteIDs());
                                if(tempList==null || tempList.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_ESSEMP+"but Customer IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_ESSEMP, tempList,null, user.getRoleList()));  
                            }
                       
                                                              
                            if (user.getRolelist().contains(Constants.ROLE_WCP_SUPP)) {
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_SUPP, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_WSM)) {
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_WSM, user.getRoleList()));
                            }
                           
                            if(user.getRolelist().contains(Constants.ROLE_WCP_SSSUPP)) {
                            user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_SSSUPP, user.getRoleList()));   
                            }


                            if(user.getRolelist().contains(Constants.ROLE_WCP_WEBSUP)) {
                            user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_WEBSUP, user.getRoleList()));    
                            }  
                            
                            
                            if(user.getRolelist().contains(Constants.ROLE_WCP_AVISUP)) {
                            user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_AVISUP, user.getRoleList()));     
                            }
                            
                            if(user.getRolelist().contains(Constants.ROLE_WCP_MARSUP)) {
                            user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_MARSUP, user.getRoleList()));     
                            }
                            
                            if(user.getRolelist().contains(Constants.ROLE_WCP_RESSUP)) {
                            user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_RESSUP, user.getRoleList()));     
                            }
                            
                            /* Engage Portal Specific added*/
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_SFR) && identityResult.getCARDB2CSFRID() != null && !identityResult.getCARDB2CSFRID().isEmpty()) {
                            //                                user.setWcp_AVSUP_CustomerID(converttolistString(identityResult.getAssociatedAirportID()));
                                List<String> custID = converttolistString(identityResult.getCARDB2CSFRID());
                                if(custID==null || custID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_CARD_B2C_SFR+"but B2C SFR IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_B2C_SFR, null,custID, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_JET) && identityResult.getCARDB2CJETID() != null && !identityResult.getCARDB2CJETID().isEmpty()) {
                            //                                user.setWcp_AVSUP_CustomerID(converttolistString(identityResult.getAssociatedAirportID()));
                                List<String> custID = converttolistString(identityResult.getCARDB2CJETID());
                                if(custID==null || custID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_CARD_B2C_JET+"but B2C Jet IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_B2C_JET, null,custID, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_ADMIN) && identityResult.getCARDB2BADMINID() != null && !identityResult.getCARDB2BADMINID().isEmpty()) {
                            //                                user.setWcp_AVSUP_CustomerID(converttolistString(identityResult.getAssociatedAirportID()));
                                List<String> custID = converttolistString(identityResult.getCARDB2BADMINID());
                                if(custID==null || custID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_CARD_B2B_ADMIN+"but B2B Admin IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_B2B_ADMIN, null,custID, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_MGR) && identityResult.getCARDB2BMGRID() != null && !identityResult.getCARDB2BMGRID().isEmpty()) {
                            //                                user.setWcp_AVSUP_CustomerID(converttolistString(identityResult.getAssociatedAirportID()));
                                List<String> custID = converttolistString(identityResult.getCARDB2BMGRID());
                                if(custID==null || custID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_CARD_B2B_MGR+"but B2B Mgr IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_B2B_MGR, null,custID, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2C_PETRO) && identityResult.getCARDB2CPETROID() != null && !identityResult.getCARDB2CPETROID().isEmpty()) {
                            //                                user.setWcp_AVSUP_CustomerID(converttolistString(identityResult.getAssociatedAirportID()));
                                List<String> custID = converttolistString(identityResult.getCARDB2CPETROID());
                                if(custID==null || custID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_CARD_B2C_PETRO+"but B2C Petro IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_B2C_PETRO, null,custID, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_B2B_EMP) && identityResult.getCARDB2BEMPID() != null && !identityResult.getCARDB2BEMPID().isEmpty()) {
                            //                                user.setWcp_AVSUP_CustomerID(converttolistString(identityResult.getAssociatedAirportID()));
                                List<String> custID = converttolistString(identityResult.getCARDB2BEMPID());
                                if(custID==null || custID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_CARD_B2B_EMP+"but B2B Emp IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_B2B_EMP, null,custID, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_ADMIN) && identityResult.getCARDADMINID() != null && !identityResult.getCARDADMINID().isEmpty()) {
                            //                                user.setWcp_AVSUP_CustomerID(converttolistString(identityResult.getAssociatedAirportID()));
                                List<String> custID = converttolistString(identityResult.getCARDADMINID());
                                if(custID==null || custID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_CARD_ADMIN+"but Card Admin IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_ADMIN, null,custID, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR) && identityResult.getCARDCSRID() != null && !identityResult.getCARDCSRID().isEmpty()) {
                            //                                user.setWcp_AVSUP_CustomerID(converttolistString(identityResult.getAssociatedAirportID()));
                                List<String> custID = converttolistString(identityResult.getCARDCSRID());
                                if(custID==null || custID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_CARD_CSR+"but CSR IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_CSR, null,custID, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP) && identityResult.getCARDSALESREPID() != null && !identityResult.getCARDSALESREPID().isEmpty()) {
                            //                                user.setWcp_AVSUP_CustomerID(converttolistString(identityResult.getAssociatedAirportID()));
                                List<String> custID = converttolistString(identityResult.getCARDSALESREPID());
                                if(custID==null || custID.size()<1){
                                    System.out.println("Role found"+Constants.ROLE_WCP_CARD_SALES_REP+"but Card Sales Rep IDs not found");
                                }
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_SALES_REP, null,custID, user.getRoleList()));
                            }
                            
                            /*if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_ADMIN)) {
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_ADMIN, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_CSR)) {
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_CSR, user.getRoleList()));
                            }
                            
                            if (user.getRolelist().contains(Constants.ROLE_WCP_CARD_SALES_REP)) {
                                user.setRoleList(addRoleToRoleList(Constants.ROLE_WCP_CARD_SALES_REP, user.getRoleList()));
                            }*/
                            
                            /* Added till here */
                            
                            
                            List<String> totalPortalRoles=new ArrayList<String>();
                            try {
                              totalPortalRoles= DAOFactory.getSingleColumnDataFromFeature("ROLE", "MANAGED_IN_PORTALS", null, null, "TRUE", "CONTROL_ATTR");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            
                            List<String> roleslistofuser = convertRoleStringToList(user.getRolelist());
                            
                            for(int i=0;i<roleslistofuser.size();i++){
                                boolean covered=false;
                                for(int j=0;j<user.getRoleList().size();j++)    {
                                    if(roleslistofuser.get(i).equals(user.getRoleList().get(j).getRoleName())){
                                            covered=true;
                                            break;
                                    }    
                                }
                                if(!covered){
                                    for(int j=0;j<totalPortalRoles.size();j++){
                                        if(roleslistofuser.get(i).equals(totalPortalRoles.get(j))){
                                            user.setRoleList(addRoleToRoleList(totalPortalRoles.get(j), user.getRoleList()));  
                                            break;
                                        }    
                                    }
                                }
                            }
                            
                            
                            if (user != null && user.getFirstName() != null)
                                userList.add(user);
                        }
                    }
                    System.out.println(".parseOimUserManagementResultToUsers : "+"Total Number of Users: " + numberofUsers);
                    System.out.println(".parseOimUserManagementResultToUsers : "+"Number of Valid Users: " + userList);
                }
            }
            return userList;
        }
        return null;
    }

    public static void main(String[] args) {
        
        UserDAO dao = new UserDAO();
     
        
        
        
    //
    //        User u = new User();
    //        u.setFirstName("LNTFN5019120");
    //        u.setMiddleName("LNTFN5019120");
    //        u.setLastName("LNTFN5091120");
    //        u.setDob(new Date());
    //        u.setEmailID("LNTFN5091210@test.com");
    //        u.setPhoneNumber("101001000");
    //        u.setPosition("WCP_ICSR");
    //        List<Integer> ls2 = new ArrayList<Integer>();
    //        ls2.add(1001420);
    //
    //        u.setWcp_B2BM_CustomerID(ls2);
    //        List<Integer> ls3 = new ArrayList<Integer>();
    //        ls3.add(1001420);
    //        u.setWcp_B2BEMP_CustomerID(ls3);
    //        List<Integer> ls4 = new ArrayList<Integer>();
    //        ls4.add(1054641);
    //        u.setWcp_B2C_CustomerID(ls4);
    //        List<Integer> ls5 = new ArrayList<Integer>();
    //        ls5.add(1054338);
    //        u.setWcp_RESM_CustomerID(ls5);
    //        List<Integer> ls6 = new ArrayList<Integer>();
    //        ls6.add(1054310);
    //        u.setWcp_RESEMP_CustomerID(ls6);
    //        List<Integer> ls7 = new ArrayList<Integer>();
    //        ls7.add(1054641);
    //        u.setWcp_ISSEMP_CustomerID(ls7);
    //        List<Integer> ls8 = new ArrayList<Integer>();
    //        ls8.add(1054338);
    //        u.setWcp_ISSM_CustomerID(ls8);
    //        List<Integer> ls9 = new ArrayList<Integer>();
    //        ls9.add(1054310);
    //        u.setWcp_ESSM_CustomerID(ls9);
    //        List<Integer> ls10 = new ArrayList<Integer>();
    //        ls10.add(1054308);
    //        u.setWcp_ESSEMP_CustomerID(ls10);
    //        List<Integer> ls = new ArrayList<Integer>();
    //        ls.add(1001420);
    //
    //        List<String> ls1 = new ArrayList<String>();
    //        ls1.add("BLL");
    //        u.setWcp_AVSUP_CustomerID(ls1);
    //        u.setWcp_AVEMP_CustomerID(ls1);
    //
    //        List<Integer> ls11 = new ArrayList<Integer>();
    //        ls11.add(1);
    //        u.setWcp_ECSR_CustomerID(ls11);
    //        List<Integer> ls12 = new ArrayList<Integer>();
    //        ls12.add(2);
    //        u.setWcp_ICSR_CustomerID(ls12);
    //        List<Integer> ls13 = new ArrayList<Integer>();
    //        ls13.add(3);
    //        u.setWcp_WSM_CustomerID(ls13);
    //        List<Integer> ls14 = new ArrayList<Integer>();
    //        ls14.add(4);
    //        u.setWcp_SUPP_CustomerID(ls14);
    //        List<Integer> ls15 = new ArrayList<Integer>();
    //
    //
    //
    //        u.setLang("en_US");
    //        // change password


        try {
            dao.searchUserWS("1054641");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private List<Roles> addRoleToRoleList(String roleName, List<Integer> customerID,List<String> AirportID, List<Roles> rolesList){
        Roles role=new Roles();
        role.setRoleName(roleName);
        role.setCustomerID(customerID);
        role.setIdString(AirportID);
        rolesList.add(role);
        return rolesList;
    }
    
    
    private List<Roles> addRoleToRoleList(String roleName, List<Roles> rolesList){
        Roles role=new Roles();
        role.setRoleName(roleName);
        role.setAssigned(true);
        rolesList.add(role);
        return rolesList;
    }
    /*
    private List<Roles> addRoleToRoleList(String roleName,List<Integer> customerID, Date startDate, Date endDate, List<Roles> rolesList){
        Roles role=new Roles();
        role.setRoleName(roleName);
        role.setCustomerID(customerID);
        role.setStartDate(startDate);
        role.setEndDate(endDate);
        rolesList.add(role);
        return rolesList;
    }
    */
    private void addAssociationToIdentity(String rolename, List<Integer> customerID, List<String> idString, boolean isassigned, Identity identity) {
        //AV user creation 2 types.
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_AVEMP)) {
            identity.setAviationEmployeeID(convertListToString(idString));
        }
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_AVSUP)) {
            identity.setAssociatedAirportID(convertListToString(idString));
        }

        //webshop user creation 3 types
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_B2C)) {
            identity.setB2CCustomerID(convertListToString(customerID));
        }
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_B2BEMP)) {
            identity.setB2BEmployeeCustomerID(convertListToString(customerID));
        }
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_B2BM)) {
            identity.setB2BManagerCustomerID(convertListToString(customerID));
        }

        //SS user creation 4 types. 4 SS types are mutually exclusive. 
        //So for a user only one of 4 SS roles can be present.
        if (rolename != null && (rolename.equals(Constants.ROLE_WCP_ISSM)||rolename.equals(Constants.ROLE_WCP_ESSM)||
            rolename.equals(Constants.ROLE_WCP_ISSEMP)|| rolename.equals(Constants.ROLE_WCP_ESSEMP))) {
            identity.setSiteIDs(convertListToString(customerID));
        }
        
        //Reseller user creation 2 types.
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_RESM)) {
            identity.setResellerCustomerID(convertListToString(customerID));
        }
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_RESEMP)) {
            identity.setResellerEmployeeID(convertListToString(customerID));
        }

        //Marine user creation 2 types.
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_MRSUP)) {
            identity.setMarineManager(convertListToString(idString));
        }
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_MREMP)) {
            identity.setMarineEmployee(convertListToString(idString));
        }

        //CSR user creation 2 types.
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_ICSR)) {
            identity.setInternalCSR("ICSR");
        }
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_ECSR)) {
            identity.setExternalCSR("ECSR");
        }

        //WSM user creation 1 types.
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_WSM)) {
            identity.setWebshopManager("WSM");
        }
        //SUPP user creation 1 types.
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_SUPP)) {
            identity.setSupplierID("SUPP");
        }
        
        /* Engage Portal Related User Creation*/
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_CARD_B2B_ADMIN)) {
            identity.setCARDB2BADMINID(convertListToString(idString));
        }
        
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_CARD_B2B_EMP)) {
            identity.setCARDB2BEMPID(convertListToString(idString));
        }
        
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_CARD_B2B_MGR)) {
            identity.setCARDB2BMGRID(convertListToString(idString));
        }
        
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_CARD_B2C_JET)) {
            identity.setCARDB2CJETID(convertListToString(idString));
        }
        
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_CARD_B2C_PETRO)) {
            identity.setCARDB2CPETROID(convertListToString(idString));
        }
        
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_CARD_B2C_SFR)) {
            identity.setCARDB2CSFRID(convertListToString(idString));
        }
        
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_CARD_ADMIN)) {
            identity.setCARDADMINID(convertListToString(idString));
        }
        
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_CARD_CSR)) {
            identity.setCARDCSRID(convertListToString(idString));
        }
        
        if (rolename != null && rolename.equals(Constants.ROLE_WCP_CARD_SALES_REP)) {
            identity.setCARDSALESREPID(convertListToString(idString));
        }
        /* Engage Portal Related User Creation Ended*/
    }

    private void createAssignedAndRevokedRolesList(List<String> newRoleList, List<String> previousRoles, List<String> assignedRoles, List<String> revokedRoles) {
        List<String> totalPortalRoles=new ArrayList<String>();
        try {
          totalPortalRoles= DAOFactory.getSingleColumnDataFromFeature("ROLE", "MANAGED_IN_PORTALS", null, null, "TRUE", "CONTROL_ATTR");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < newRoleList.size(); i++) {
            boolean isfound = false;
            for (int j = previousRoles.size() - 1; j >= 0; j--) {
                if (newRoleList.get(i).equals(previousRoles.get(j))) {
                    isfound = true;
                    previousRoles.remove(j);
                    break;
                }
            }
            if (!isfound) {
                assignedRoles.add(newRoleList.get(i));
            }
        }
        if(previousRoles.size()>0){
            for(int i=0;i<previousRoles.size();i++){
                for(int j=0;j<totalPortalRoles.size();j++){
                    if(previousRoles.get(i).equals(totalPortalRoles.get(j))){
                        revokedRoles.add(previousRoles.get(i));
                    }    
                }
            }       
        }
    }
}

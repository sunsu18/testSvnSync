package com.sfr.services.core.bo;

import com.sfr.core.bean.BaseBean;
import com.sfr.core.bean.User;
import com.sfr.services.core.dao.UserDAO;
import com.sfr.util.AccessDataControl;

import java.sql.SQLException;

import java.util.List;

import javax.naming.NamingException;

import javax.xml.datatype.DatatypeConfigurationException;

import oracle.adf.share.logging.ADFLogger;

public class UserBO {
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();
    public UserBO() {
    }
    
    public BaseBean changePassword(String userID, String oldPassword, String newPassword) {
        UserDAO userDAO = new UserDAO();
        log.info(accessDC.getDisplayRecord() + this.getClass() + " .changePassword : " + "in change password of BO");
        return userDAO.changePasswordWS(userID, oldPassword, newPassword);
    }

    public BaseBean createUser(User user) throws DatatypeConfigurationException, SQLException, NamingException {
        UserDAO userDAO = new UserDAO();
        return userDAO.createUserWS(user);
    }

    public BaseBean deleteUser(String userID) {
        UserDAO userDAO = new UserDAO();
        return userDAO.deleteUserWS(userID);
    }

    public BaseBean updateUser(User user) throws DatatypeConfigurationException {
        UserDAO userDAO = new UserDAO();
        return userDAO.updateUserWS(user);
    }

    public List<User> searchUser(String customerId) throws NumberFormatException {
        log.info(accessDC.getDisplayRecord() + this.getClass() + " .searchUser : " + "UserBo cust id " + customerId);
        UserDAO userDAO = new UserDAO();
        return userDAO.searchUserWS(customerId);
    }

    public User searchUserWithUserId(String userId) throws NumberFormatException, Exception {
        UserDAO userDAO = new UserDAO();
        User userBean = new User();
        try {
            log.info(accessDC.getDisplayRecord() + this.getClass() + " .searchUserWithUserId : " + "user id in BO " + userId);
            userBean = userDAO.searchUserWithUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userBean;
    }

    public BaseBean isPasswordChangeRequired(String userId) {
        UserDAO userDAO = new UserDAO();
        return userDAO.isPasswordChangeRequiredWS(userId);
    }

    public BaseBean forgotPassword(String userId) {
        UserDAO userDAO = new UserDAO();
        BaseBean base = new BaseBean();
        try {
            base = userDAO.forgotPasswordWS(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base;
    }
}

package com.sfr.engage.model.datacontrol;


import com.sfr.core.bean.BaseBean;
import com.sfr.core.bean.User;
import com.sfr.services.core.bo.UserBO;
import com.sfr.util.AccessDataControl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.xml.datatype.DatatypeConfigurationException;
import oracle.adf.share.logging.ADFLogger;


public class UserClient {
    UserBO userBO;
    public static final ADFLogger _logger = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();

    public UserClient() {
        userBO = new UserBO();
    }

    public BaseBean changePassword(String userID, String oldPassword,
                                   String newPassword) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + ".changePassword : "+"In change password of client");
        if (userID != null && oldPassword != null && newPassword != null) {
            return userBO.changePassword(userID, oldPassword, newPassword);
        } else {
            return null;
        }
    }

    public BaseBean createUser(User user) throws DatatypeConfigurationException, SQLException, NamingException {
        BaseBean baseBean = new BaseBean();
        if (user != null) {
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + ".createUser : "+"------FIRST NAME ----------" + user.getFirstName());
            baseBean = userBO.createUser(user);
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + ".createUser : "+"-----------BASE BEAN-------" + baseBean.getStatus());
            return baseBean;
        } else {
            return null;
        }
    }

    public BaseBean deleteUser(String userID, String customerId,Boolean isUserDeleteForced) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + ".deleteUser : "+"inside deleteUser");
        if (userID != null && customerId != null) {
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + ".deleteUser : "+"USER ID "+userID+"\nCUSTOMER ID "+customerId+"\nFLAG --"+isUserDeleteForced);
            return userBO.deleteUser(userID);
        } else {
            return null;
        }
    }

    public BaseBean updateUser(User user) throws DatatypeConfigurationException {
        if (user != null) {
            return userBO.updateUser(user);
        } else {
            return null;
        }
    }

    public List<User> searchUser(String customerId) throws NumberFormatException {
        if (customerId != null) {
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + ".searchUser : "+"CUSTOMER ID in user client "+customerId);
            return userBO.searchUser(customerId);
        } else {
            return null;
        }
    }

    public BaseBean isPasswordChangeRequired(String userId) {
        if (userId != null) {
            return userBO.isPasswordChangeRequired(userId);
        } else {
            return null;
        }
    }

    public BaseBean forgotPassword(String userId) {
        if (userId != null) {
            BaseBean base=new BaseBean();
            try{
            base=userBO.forgotPassword(userId);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return base;
        } else {
            return null;
        }
    }

    public List<User> searchUserWithUserId(String userId) throws NumberFormatException,
                                                                 Exception {
        List<User> userList= new ArrayList<User>();
        if (userId != null) {
            userList.add(userBO.searchUserWithUserId(userId));
            return userList;
        } else {
            return null;
        }
    }
    
    public List<User> searchUserWithUserId(String userId,String param) throws NumberFormatException,
                                                                 Exception {
        List<User> userList= new ArrayList<User>();
        if (userId != null) {
            userList.add(userBO.searchUserWithUserId(userId, param));
            return userList;
        } else {
            return null;
        }
    }
}

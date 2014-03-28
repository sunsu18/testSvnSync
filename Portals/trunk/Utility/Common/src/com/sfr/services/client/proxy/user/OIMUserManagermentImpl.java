package com.sfr.services.client.proxy.user;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (11.1.1.0.0, build 111209.0821.28162)

@WebService(wsdlLocation="http://elb1cn12.statoilfuelretail.com:10021/OIMWebServices_ENGAGE/OIMUserManagermentImplPort?WSDL",
  targetNamespace="http://ws.oim.sfr.com/", name="OIMUserManagermentImpl")
@XmlSeeAlso(
  { com.sfr.services.client.proxy.user.type.ObjectFactory.class })
public interface OIMUserManagermentImpl
{
  @WebMethod
  @Action(input="http://ws.oim.sfr.com/OIMUserManagermentImpl/updateOIMUserRequest",
    output="http://ws.oim.sfr.com/OIMUserManagermentImpl/updateOIMUserResponse")
  @ResponseWrapper(localName="updateOIMUserResponse", targetNamespace="http://ws.oim.sfr.com/",
    className="com.sfr.services.client.proxy.user.type.UpdateOIMUserResponse")
  @RequestWrapper(localName="updateOIMUser", targetNamespace="http://ws.oim.sfr.com/",
    className="com.sfr.services.client.proxy.user.type.UpdateOIMUser")
  @WebResult(targetNamespace="")
  public com.sfr.services.client.proxy.user.type.OimUserManagementResult updateOIMUser(@WebParam(targetNamespace="",
      name="Identity")
    com.sfr.services.client.proxy.user.type.Identity identity,
    @WebParam(targetNamespace="", name="Service_Name")
    String serviceName);

  @WebMethod
  @Action(input="http://ws.oim.sfr.com/OIMUserManagermentImpl/createOIMUserRequest",
    output="http://ws.oim.sfr.com/OIMUserManagermentImpl/createOIMUserResponse")
  @ResponseWrapper(localName="createOIMUserResponse", targetNamespace="http://ws.oim.sfr.com/",
    className="com.sfr.services.client.proxy.user.type.CreateOIMUserResponse")
  @RequestWrapper(localName="createOIMUser", targetNamespace="http://ws.oim.sfr.com/",
    className="com.sfr.services.client.proxy.user.type.CreateOIMUser")
  @WebResult(targetNamespace="")
  public com.sfr.services.client.proxy.user.type.OimUserManagementResult createOIMUser(@WebParam(targetNamespace="",
      name="Identity")
    com.sfr.services.client.proxy.user.type.Identity identity,
    @WebParam(targetNamespace="", name="Service_Name")
    String serviceName);

  @WebMethod
  @Action(input="http://ws.oim.sfr.com/OIMUserManagermentImpl/deleteOIMUserRequest",
    output="http://ws.oim.sfr.com/OIMUserManagermentImpl/deleteOIMUserResponse")
  @ResponseWrapper(localName="deleteOIMUserResponse", targetNamespace="http://ws.oim.sfr.com/",
    className="com.sfr.services.client.proxy.user.type.DeleteOIMUserResponse")
  @RequestWrapper(localName="deleteOIMUser", targetNamespace="http://ws.oim.sfr.com/",
    className="com.sfr.services.client.proxy.user.type.DeleteOIMUser")
  @WebResult(targetNamespace="")
  public com.sfr.services.client.proxy.user.type.OimUserManagementResult deleteOIMUser(@WebParam(targetNamespace="",
      name="UserID")
    String userID);

  @WebMethod
  @Action(input="http://ws.oim.sfr.com/OIMUserManagermentImpl/changePasswordRequest",
    output="http://ws.oim.sfr.com/OIMUserManagermentImpl/changePasswordResponse")
  @ResponseWrapper(localName="changePasswordResponse", targetNamespace="http://ws.oim.sfr.com/",
    className="com.sfr.services.client.proxy.user.type.ChangePasswordResponse")
  @RequestWrapper(localName="changePassword", targetNamespace="http://ws.oim.sfr.com/",
    className="com.sfr.services.client.proxy.user.type.ChangePassword")
  @WebResult(targetNamespace="")
  public com.sfr.services.client.proxy.user.type.OimUserManagementResult changePassword(@WebParam(targetNamespace="",
      name="UserID")
    String userID, @WebParam(targetNamespace="", name="Old_Password")
    String oldPassword, @WebParam(targetNamespace="", name="new_Password")
    String newPassword);

  @WebMethod
  @Action(input="http://ws.oim.sfr.com/OIMUserManagermentImpl/isUserChangePasswordNextLoginRequest",
    output="http://ws.oim.sfr.com/OIMUserManagermentImpl/isUserChangePasswordNextLoginResponse")
  @ResponseWrapper(localName="isUserChangePasswordNextLoginResponse",
    targetNamespace="http://ws.oim.sfr.com/", className="com.sfr.services.client.proxy.user.type.IsUserChangePasswordNextLoginResponse")
  @RequestWrapper(localName="isUserChangePasswordNextLogin",
    targetNamespace="http://ws.oim.sfr.com/", className="com.sfr.services.client.proxy.user.type.IsUserChangePasswordNextLogin")
  @WebResult(targetNamespace="")
  public com.sfr.services.client.proxy.user.type.OimUserManagementResult isUserChangePasswordNextLogin(@WebParam(targetNamespace="",
      name="UserID")
    String userID);

  @WebMethod
  @Action(input="http://ws.oim.sfr.com/OIMUserManagermentImpl/searchOIMUserRequest",
    output="http://ws.oim.sfr.com/OIMUserManagermentImpl/searchOIMUserResponse")
  @ResponseWrapper(localName="searchOIMUserResponse", targetNamespace="http://ws.oim.sfr.com/",
    className="com.sfr.services.client.proxy.user.type.SearchOIMUserResponse")
  @RequestWrapper(localName="searchOIMUser", targetNamespace="http://ws.oim.sfr.com/",
    className="com.sfr.services.client.proxy.user.type.SearchOIMUser")
  @WebResult(targetNamespace="")
  public com.sfr.services.client.proxy.user.type.OimUserManagementResult searchOIMUser(@WebParam(targetNamespace="",
      name="CustomerID")
    String customerID);

  @WebMethod
  @Action(input="http://ws.oim.sfr.com/OIMUserManagermentImpl/forgotPasswordRequest",
    output="http://ws.oim.sfr.com/OIMUserManagermentImpl/forgotPasswordResponse")
  @ResponseWrapper(localName="forgotPasswordResponse", targetNamespace="http://ws.oim.sfr.com/",
    className="com.sfr.services.client.proxy.user.type.ForgotPasswordResponse")
  @RequestWrapper(localName="forgotPassword", targetNamespace="http://ws.oim.sfr.com/",
    className="com.sfr.services.client.proxy.user.type.ForgotPassword")
  @WebResult(targetNamespace="")
  public com.sfr.services.client.proxy.user.type.OimUserManagementResult forgotPassword(@WebParam(targetNamespace="",
      name="UserID")
    String userID);
}


package com.sfr.services.client.proxy.user.type;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sfr.services.client.proxy.user.type package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ChangePasswordResponse_QNAME = new QName("http://ws.oim.sfr.com/", "changePasswordResponse");
    private final static QName _DeleteOIMUser_QNAME = new QName("http://ws.oim.sfr.com/", "deleteOIMUser");
    private final static QName _DeleteOIMUserResponse_QNAME = new QName("http://ws.oim.sfr.com/", "deleteOIMUserResponse");
    private final static QName _UpdateOIMUserResponse_QNAME = new QName("http://ws.oim.sfr.com/", "updateOIMUserResponse");
    private final static QName _IsUserChangePasswordNextLoginResponse_QNAME = new QName("http://ws.oim.sfr.com/", "isUserChangePasswordNextLoginResponse");
    private final static QName _ChangePassword_QNAME = new QName("http://ws.oim.sfr.com/", "changePassword");
    private final static QName _UpdateOIMUser_QNAME = new QName("http://ws.oim.sfr.com/", "updateOIMUser");
    private final static QName _CreateOIMUserResponse_QNAME = new QName("http://ws.oim.sfr.com/", "createOIMUserResponse");
    private final static QName _ForgotPasswordResponse_QNAME = new QName("http://ws.oim.sfr.com/", "forgotPasswordResponse");
    private final static QName _IsUserChangePasswordNextLogin_QNAME = new QName("http://ws.oim.sfr.com/", "isUserChangePasswordNextLogin");
    private final static QName _SearchOIMUserResponse_QNAME = new QName("http://ws.oim.sfr.com/", "searchOIMUserResponse");
    private final static QName _CreateOIMUser_QNAME = new QName("http://ws.oim.sfr.com/", "createOIMUser");
    private final static QName _ForgotPassword_QNAME = new QName("http://ws.oim.sfr.com/", "forgotPassword");
    private final static QName _SearchOIMUser_QNAME = new QName("http://ws.oim.sfr.com/", "searchOIMUser");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sfr.services.client.proxy.user.type
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SearchOIMUser }
     * 
     */
    public SearchOIMUser createSearchOIMUser() {
        return new SearchOIMUser();
    }

    /**
     * Create an instance of {@link CreateOIMUser }
     * 
     */
    public CreateOIMUser createCreateOIMUser() {
        return new CreateOIMUser();
    }

    /**
     * Create an instance of {@link ForgotPassword }
     * 
     */
    public ForgotPassword createForgotPassword() {
        return new ForgotPassword();
    }

    /**
     * Create an instance of {@link SearchOIMUserResponse }
     * 
     */
    public SearchOIMUserResponse createSearchOIMUserResponse() {
        return new SearchOIMUserResponse();
    }

    /**
     * Create an instance of {@link ForgotPasswordResponse }
     * 
     */
    public ForgotPasswordResponse createForgotPasswordResponse() {
        return new ForgotPasswordResponse();
    }

    /**
     * Create an instance of {@link CreateOIMUserResponse }
     * 
     */
    public CreateOIMUserResponse createCreateOIMUserResponse() {
        return new CreateOIMUserResponse();
    }

    /**
     * Create an instance of {@link UpdateOIMUser }
     * 
     */
    public UpdateOIMUser createUpdateOIMUser() {
        return new UpdateOIMUser();
    }

    /**
     * Create an instance of {@link IsUserChangePasswordNextLogin }
     * 
     */
    public IsUserChangePasswordNextLogin createIsUserChangePasswordNextLogin() {
        return new IsUserChangePasswordNextLogin();
    }

    /**
     * Create an instance of {@link ChangePasswordResponse }
     * 
     */
    public ChangePasswordResponse createChangePasswordResponse() {
        return new ChangePasswordResponse();
    }

    /**
     * Create an instance of {@link DeleteOIMUser }
     * 
     */
    public DeleteOIMUser createDeleteOIMUser() {
        return new DeleteOIMUser();
    }

    /**
     * Create an instance of {@link ChangePassword }
     * 
     */
    public ChangePassword createChangePassword() {
        return new ChangePassword();
    }

    /**
     * Create an instance of {@link UpdateOIMUserResponse }
     * 
     */
    public UpdateOIMUserResponse createUpdateOIMUserResponse() {
        return new UpdateOIMUserResponse();
    }

    /**
     * Create an instance of {@link IsUserChangePasswordNextLoginResponse }
     * 
     */
    public IsUserChangePasswordNextLoginResponse createIsUserChangePasswordNextLoginResponse() {
        return new IsUserChangePasswordNextLoginResponse();
    }

    /**
     * Create an instance of {@link DeleteOIMUserResponse }
     * 
     */
    public DeleteOIMUserResponse createDeleteOIMUserResponse() {
        return new DeleteOIMUserResponse();
    }

    /**
     * Create an instance of {@link OimUserManagementResult }
     * 
     */
    public OimUserManagementResult createOimUserManagementResult() {
        return new OimUserManagementResult();
    }

    /**
     * Create an instance of {@link Identity }
     * 
     */
    public Identity createIdentity() {
        return new Identity();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangePasswordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.oim.sfr.com/", name = "changePasswordResponse")
    public JAXBElement<ChangePasswordResponse> createChangePasswordResponse(ChangePasswordResponse value) {
        return new JAXBElement<ChangePasswordResponse>(_ChangePasswordResponse_QNAME, ChangePasswordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteOIMUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.oim.sfr.com/", name = "deleteOIMUser")
    public JAXBElement<DeleteOIMUser> createDeleteOIMUser(DeleteOIMUser value) {
        return new JAXBElement<DeleteOIMUser>(_DeleteOIMUser_QNAME, DeleteOIMUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteOIMUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.oim.sfr.com/", name = "deleteOIMUserResponse")
    public JAXBElement<DeleteOIMUserResponse> createDeleteOIMUserResponse(DeleteOIMUserResponse value) {
        return new JAXBElement<DeleteOIMUserResponse>(_DeleteOIMUserResponse_QNAME, DeleteOIMUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOIMUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.oim.sfr.com/", name = "updateOIMUserResponse")
    public JAXBElement<UpdateOIMUserResponse> createUpdateOIMUserResponse(UpdateOIMUserResponse value) {
        return new JAXBElement<UpdateOIMUserResponse>(_UpdateOIMUserResponse_QNAME, UpdateOIMUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsUserChangePasswordNextLoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.oim.sfr.com/", name = "isUserChangePasswordNextLoginResponse")
    public JAXBElement<IsUserChangePasswordNextLoginResponse> createIsUserChangePasswordNextLoginResponse(IsUserChangePasswordNextLoginResponse value) {
        return new JAXBElement<IsUserChangePasswordNextLoginResponse>(_IsUserChangePasswordNextLoginResponse_QNAME, IsUserChangePasswordNextLoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangePassword }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.oim.sfr.com/", name = "changePassword")
    public JAXBElement<ChangePassword> createChangePassword(ChangePassword value) {
        return new JAXBElement<ChangePassword>(_ChangePassword_QNAME, ChangePassword.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOIMUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.oim.sfr.com/", name = "updateOIMUser")
    public JAXBElement<UpdateOIMUser> createUpdateOIMUser(UpdateOIMUser value) {
        return new JAXBElement<UpdateOIMUser>(_UpdateOIMUser_QNAME, UpdateOIMUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOIMUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.oim.sfr.com/", name = "createOIMUserResponse")
    public JAXBElement<CreateOIMUserResponse> createCreateOIMUserResponse(CreateOIMUserResponse value) {
        return new JAXBElement<CreateOIMUserResponse>(_CreateOIMUserResponse_QNAME, CreateOIMUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ForgotPasswordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.oim.sfr.com/", name = "forgotPasswordResponse")
    public JAXBElement<ForgotPasswordResponse> createForgotPasswordResponse(ForgotPasswordResponse value) {
        return new JAXBElement<ForgotPasswordResponse>(_ForgotPasswordResponse_QNAME, ForgotPasswordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsUserChangePasswordNextLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.oim.sfr.com/", name = "isUserChangePasswordNextLogin")
    public JAXBElement<IsUserChangePasswordNextLogin> createIsUserChangePasswordNextLogin(IsUserChangePasswordNextLogin value) {
        return new JAXBElement<IsUserChangePasswordNextLogin>(_IsUserChangePasswordNextLogin_QNAME, IsUserChangePasswordNextLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchOIMUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.oim.sfr.com/", name = "searchOIMUserResponse")
    public JAXBElement<SearchOIMUserResponse> createSearchOIMUserResponse(SearchOIMUserResponse value) {
        return new JAXBElement<SearchOIMUserResponse>(_SearchOIMUserResponse_QNAME, SearchOIMUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOIMUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.oim.sfr.com/", name = "createOIMUser")
    public JAXBElement<CreateOIMUser> createCreateOIMUser(CreateOIMUser value) {
        return new JAXBElement<CreateOIMUser>(_CreateOIMUser_QNAME, CreateOIMUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ForgotPassword }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.oim.sfr.com/", name = "forgotPassword")
    public JAXBElement<ForgotPassword> createForgotPassword(ForgotPassword value) {
        return new JAXBElement<ForgotPassword>(_ForgotPassword_QNAME, ForgotPassword.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchOIMUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.oim.sfr.com/", name = "searchOIMUser")
    public JAXBElement<SearchOIMUser> createSearchOIMUser(SearchOIMUser value) {
        return new JAXBElement<SearchOIMUser>(_SearchOIMUser_QNAME, SearchOIMUser.class, null, value);
    }

}

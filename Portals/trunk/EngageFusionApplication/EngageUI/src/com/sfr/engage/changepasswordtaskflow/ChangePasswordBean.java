package com.sfr.engage.changepasswordtaskflow;

import com.sfr.core.bean.BaseBean;
import com.sfr.engage.authenticatedhometaskflow.AuthenticatedHomeBean;

import com.sfr.engage.model.resources.EngageResourceBundle;

import com.sfr.util.AccessDataControl;

import java.io.Serializable;

import java.util.HashMap;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;

public class ChangePasswordBean implements Serializable {

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    private ResourceBundle resourceBundle;
    private String lang;

    /**
     * @return bindings Object
     */
    public Bindings getBindings() {
        if (bindings == null) {
            bindings = new Bindings();
        }
        return bindings;
    }

    public ChangePasswordBean() {
        super();
        resourceBundle = new EngageResourceBundle();
        lang = "en_US";
    }

    public String changeUserPassword() {
        try {
            BaseBean result = new BaseBean();
            boolean status = true;
            if (getBindings().getOldPasswordIT().getValue() == null ||
                getBindings().getOldPasswordIT().getValue().toString().length() <
                0) {
                status = false;
                if (resourceBundle.containsKey("ENGAGE_NO_OLD_PASSWORD")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("ENGAGE_NO_OLD_PASSWORD"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
            if (getBindings().getNewPasswordIT().getValue() == null ||
                getBindings().getNewPasswordIT().getValue().toString().length() <
                0) {
                status = false;
                if (resourceBundle.containsKey("ENGAGE_NO_NEW_PASSWORD")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("ENGAGE_NO_NEW_PASSWORD"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
            if (getBindings().getConfirmPasswordIT().getValue() == null ||
                getBindings().getConfirmPasswordIT().getValue().toString().length() <
                0) {
                status = false;
                if (resourceBundle.containsKey("ENGAGE_NO_CONFIRM_PASSWORD")) {
                    FacesMessage msg =
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         (String)resourceBundle.getObject("ENGAGE_NO_CONFIRM_PASSWORD"),
                                         "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
            if (status) {

                if (getBindings().getNewPasswordIT().getValue().equals(getBindings().getConfirmPasswordIT().getValue())) {
                    //System.out.println("Password Changed Successfully");
                    BindingContainer bindings =
                        BindingContext.getCurrent().getCurrentBindingsEntry();
                    OperationBinding operationBinding =
                        bindings.getOperationBinding("changePassword");
                    operationBinding.getParamsMap().put("userID", "TestCR482@sample.com");
                    operationBinding.getParamsMap().put("oldPassword", getBindings().getOldPasswordIT().getValue().toString());
                    operationBinding.getParamsMap().put("newPassword", getBindings().getNewPasswordIT().getValue().toString());
                    result = (BaseBean)operationBinding.execute();

                    if (result.getStatus() != null &&
                        result.getStatus().equalsIgnoreCase("error")) {
                        String error = null;

                        if (result.getErrorList() != null &&
                            result.getErrorList().size() != 0 &&
                            result.getErrorList().get(0) != null &&
                            result.getErrorList().get(0).getErrorCode() !=
                            null) {

                            error =
                                    result.getErrorList().get(0).getErrorCode();
                            error = getWSDLErrorMessage(error);

                        } else {
                            error = "ENGAGE_UNKNOWN_ERROR";
                            error = getWSDLErrorMessage(error);
                        }
                        FacesMessage msg =
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                             error, "");
                        FacesContext.getCurrentInstance().addMessage(null,
                                                                     msg);

                    } else {
                        if (resourceBundle.containsKey("ENGAGE_PASSWORD_CHANGED_SUCCESS")) {
                            FacesMessage msg =
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                 (String)resourceBundle.getObject("ENGAGE_PASSWORD_CHANGED_SUCCESS"),
                                                 "");
                            FacesContext.getCurrentInstance().addMessage(null,
                                                                         msg);
                        }
//                        try {
//
//                            ExternalContext ectx =
//                                FacesContext.getCurrentInstance().getExternalContext();
//                            HttpServletRequest request =
//                                (HttpServletRequest)ectx.getRequest();
//                            ectx =
//                            FacesContext.getCurrentInstance().getExternalContext();
//                            request = (HttpServletRequest)ectx.getRequest();
//                            //                        HttpSession session = request.getSession(false);
//                            //                        session.setAttribute("passwordChangeRequired", Constants.NO);
//                            //                        String requestedPage = (String)session.getAttribute(Constants.SESSION_PRIMARY_REQUEST_PAGE_ID);
//                            ectx.redirect(ectx.getRequestContextPath() +
//                                          "/faces/card/home");
//                        } catch (Exception e) {
//                            FacesMessage msg =
//                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                                                 e.getMessage(), "");
//                            FacesContext.getCurrentInstance().addMessage(null,
//                                                                         msg);
//                        }
//
                    }

                } else {
                    if (resourceBundle.containsKey("ENGAGE_NO_SAME_PASSWORD")) {
                        FacesMessage msg =
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                             (String)resourceBundle.getObject("ENGAGE_NO_SAME_PASSWORD"),
                                             "");
                        FacesContext.getCurrentInstance().addMessage(null,
                                                                     msg);
                    }
                }

            }

        } catch (JboException jboEx) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                 jboEx.getMessage(),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null,
                                                         msg);
        } catch (Exception e) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                 e.getMessage(),
                                 "");
            FacesContext.getCurrentInstance().addMessage(null,
                                                         msg);
        }
        return null;
    }


    private String getWSDLErrorMessage(String error) {

        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding =
            bindings.getOperationBinding("getWebServiceErrorMessage");
        operationBinding.getParamsMap().put("errorMessage", error);
        operationBinding.getParamsMap().put("countryCode", lang);
        String result = (String)operationBinding.execute();

        if (result == null || result.equals("")) {
            result = error;
        }
        return result;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public class Bindings {

        private RichInputText oldPasswordIT;
        private RichInputText newPasswordIT;
        private RichInputText confirmPasswordIT;

        public void setOldPasswordIT(RichInputText oldPasswordIT) {
            this.oldPasswordIT = oldPasswordIT;
        }

        public RichInputText getOldPasswordIT() {
            return oldPasswordIT;
        }

        public void setNewPasswordIT(RichInputText newPasswordIT) {
            this.newPasswordIT = newPasswordIT;
        }

        public RichInputText getNewPasswordIT() {
            return newPasswordIT;
        }

        public void setConfirmPasswordIT(RichInputText confirmPasswordIT) {
            this.confirmPasswordIT = confirmPasswordIT;
        }

        public RichInputText getConfirmPasswordIT() {
            return confirmPasswordIT;
        }
    }


}


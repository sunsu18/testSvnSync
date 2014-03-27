package com.sfr.engage.changepasswordtaskflow;

import com.sfr.engage.authenticatedhometaskflow.AuthenticatedHomeBean;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.input.RichInputText;

public class ChangePasswordBean implements Serializable  {
    
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    
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


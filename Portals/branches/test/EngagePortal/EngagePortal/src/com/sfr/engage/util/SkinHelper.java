package com.sfr.engage.util;

import com.sfr.core.bean.User;
import com.sfr.engage.model.queries.uvo.PrtUserPreferredLangVORowImpl;
import com.sfr.util.ADFUtils;
import com.sfr.util.AccessDataControl;
import com.sfr.util.ThreadSerialization;
import com.sfr.util.constants.Constants;
import com.sfr.util.validations.Conversion;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.binding.OperationBinding;
import oracle.javatools.resourcebundle.ResourceBundleManagerRT;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;


/**
 * TODO : ASHTHA - 02, May, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC
 *  2. Override toString() method
 */
public class SkinHelper extends ThreadSerialization {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;

    String portal;
    String style;
    String skinStyleCardPrivate = "sfrCardPrivate";
    String skinStyleCardBusiness = "sfrCardBusiness";
    String skinStyleJet = "sfrJet";
    String skinStylePetro = "sfrPetro";
    String profile;
    private AccessDataControl accessDC = new AccessDataControl();
    String locale;
    public static final ADFLogger _logger = AccessDataControl.getSFRLogger();
    public String flag;
    private String currentSkin;

    /**
     */
    public SkinHelper() {
        super();        
    }

    /**
     * @param currentSkin
     */
    public void setCurrentSkin(String currentSkin) {
        this.currentSkin = currentSkin;
    }

    /**
     * @return
     */
    public String getCurrentSkin() {
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = (HttpSession)request.getSession(); // TODO : ASHTHA - 02, May, 2014 : Remove unnecessary casting

        if(session.getAttribute("profile") != null)
        {profile = (String)session.getAttribute("profile");}
        else
        {profile = "business";}

        if (request.getParameter("portal") != null &&
            (request.getParameter("portal").toString().equalsIgnoreCase("card") || request.getParameter("portal").toString().equalsIgnoreCase("jet") ||
             request.getParameter("portal").toString().equalsIgnoreCase("petro"))) {

            session.setAttribute("portal", request.getParameter("portal"));
        } else {
            String default_profile = "card";

            session.setAttribute("portal", default_profile);
        }

        portal = (String)session.getAttribute("portal");


        if (portal == null) {
            currentSkin = skinStyleCardPrivate;
        } else if ("card".equalsIgnoreCase(portal)) {
            if (profile.equalsIgnoreCase("private")) {
                currentSkin = skinStyleCardPrivate;
                session.setAttribute("profile","private");
            } else {
                currentSkin = skinStyleCardBusiness;
                session.setAttribute("profile","business");
            }


        } else if ("jet".equalsIgnoreCase(portal)) {
            currentSkin = skinStyleJet;
        } else {
            currentSkin = skinStylePetro;
        }
        return currentSkin;
    }

    /**
     * @param profile
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     * @return
     */
    public String getProfile() {
        return profile;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        HttpSession session = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
        String authenticatedUser = "false";
        Conversion conv = new Conversion();
        if (session.getAttribute(Constants.AUTHENTICATE_FLAG) != null) {
            authenticatedUser = (String)session.getAttribute(Constants.AUTHENTICATE_FLAG);
        }


        if (session.getAttribute("lang") != null &&
            !authenticatedUser.equals("true")) {
            locale = (String)session.getAttribute("lang");
        } else if (session.getAttribute(Constants.DISPLAY_PORTAL_LANG) !=
                   null && authenticatedUser.equals("true")) {
            User user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
            String userEmail = user.getEmailID();            
            DCBindingContainer bindings;
            bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry().get("pageTemplateBinding");
            if (bindings == null || bindings.findIteratorBinding("PrtUserPreferredLangVO1Iterator") == null) {
                bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            }
            DCIteratorBinding iter;
            if (bindings != null) {
                iter = bindings.findIteratorBinding("PrtUserPreferredLangVO1Iterator");
            } else {
                iter = null;
            }
            if (iter != null) {
                ViewObject vo = iter.getViewObject();
                vo.setWhereClause("PrtUserPreferredLangEO.USER_ID=:userEmail");
                vo.defineNamedWhereClauseParam("userEmail", userEmail, null);
                vo.executeQuery();
                if (vo.getEstimatedRowCount() != 0) {
                    while (vo.hasNext()) {
                        PrtUserPreferredLangVORowImpl currRow =
                            (PrtUserPreferredLangVORowImpl)vo.next();
                        locale = currRow.getPreferredLang();
                    }
                } else {
                    locale = conv.getCustomerCountryCode((String)session.getAttribute(Constants.DISPLAY_PORTAL_LANG));
                }
                if ("PrtUserPreferredLangEO.USER_ID=:userEmail".equalsIgnoreCase(vo.getWhereClause())) {
                    vo.removeNamedWhereClauseParam("userEmail");
                    vo.setWhereClause("");
                    vo.executeQuery();
                }
            } else {
                locale = "se_SE";
            }
        }
        
        return locale;
    }

    public void setAccessDC(AccessDataControl accessDC) {
        this.accessDC = accessDC;
    }

    public AccessDataControl getAccessDC() {
        return accessDC;
    }

    public void changePreferredLangToEng(){
        String selectedFlag = "en_US";
        changePreferredLang(selectedFlag);
    }
    
    public void changePreferredLangToOtherLang(){
        Conversion conv = new Conversion();
        HttpSession session = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
        String selectedFlag = conv.getCustomerCountryCode((String)session.getAttribute(Constants.DISPLAY_PORTAL_LANG));
        changePreferredLang(selectedFlag);
    }

    public void changePreferredLang(String selectedFlag) {
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + " Inside changePreferredLang of SkinHelper");
        HttpSession session = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
        User user = (User)session.getAttribute(Constants.SESSION_USER_INFO);
        String userEmail = user.getEmailID();
        DCBindingContainer bindings;
        bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry().get("pageTemplateBinding");
        if (bindings == null || bindings.findIteratorBinding("PrtUserPreferredLangVO1Iterator") == null) {
            bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        }
        DCIteratorBinding iter;
        if (bindings != null) {
            iter = bindings.findIteratorBinding("PrtUserPreferredLangVO1Iterator");
        } else {
            iter = null;
        }
        if (iter != null) {
            ViewObject vo = iter.getViewObject();
            vo.setWhereClause("PrtUserPreferredLangEO.USER_ID=:userEmail");
            vo.defineNamedWhereClauseParam("userEmail", userEmail, null);
            vo.executeQuery();
            if (vo.getEstimatedRowCount() != 0) {
                while (vo.hasNext()) {
                    PrtUserPreferredLangVORowImpl currRow = (PrtUserPreferredLangVORowImpl)vo.next();
                    if(currRow.getUserId() != null && currRow.getPreferredLang() != null){
                        String preferredLang = currRow.getPreferredLang();
                        String userName = "";
                        if(user.getFirstName() != null){
                            userName = user.getFirstName();
                        }
                        if(preferredLang.equalsIgnoreCase(selectedFlag)){
                        }
                        else{
                            currRow.remove();
                            //instead of removing can I jst edit the col?
                            Row row = vo.createRow();
                            row.setAttribute("UserId", userEmail);
                            row.setAttribute("PreferredLang", selectedFlag);
                            row.setAttribute("ModifiedBy", userName);
                        }

                    }
                }
            }else {
                _logger.info(accessDC.getDisplayRecord() + this.getClass() + "Record not found");
                String userName = "";
                if(user.getFirstName() != null){
                    userName = user.getFirstName();
                }
                Row row = vo.createRow();
                row.setAttribute("UserId", userEmail);
                row.setAttribute("PreferredLang", selectedFlag);
                row.setAttribute("ModifiedBy", userName);
                //commit should be done here or outside else block?
            }
            OperationBinding op = ADFUtils.findOperation("Commit");
            op.execute();                
            ResourceBundleManagerRT rt = (ResourceBundleManagerRT)ResourceBundleManagerRT.getResourceBundleManager();
            rt.flush();

            if("PrtUserPreferredLangEO.USER_ID=:userEmail".equalsIgnoreCase(vo.getWhereClause())){
                vo.removeNamedWhereClauseParam("userEmail");
                vo.setWhereClause("");
                vo.executeQuery();
            }            
            
            FacesContext fctx = FacesContext.getCurrentInstance();
            ExternalContext ectx = fctx.getExternalContext();
            HttpSession sessionPage = (HttpSession)ectx.getSession(true);            
            sessionPage.setAttribute("refreshCondition", "true");            
            String refreshpage = fctx.getViewRoot().getViewId();
            if (refreshpage.contains("/home")) {
                ViewHandler ViewH = fctx.getApplication().getViewHandler();
                UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
                UIV.setViewId(refreshpage);
                fctx.setViewRoot(UIV);                
            }
            
        }
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        HttpSession session = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
        Conversion conv = new Conversion();
        String preferredLang = conv.getCustomerCountryCode((String)session.getAttribute(Constants.DISPLAY_PORTAL_LANG));
        if(preferredLang.equalsIgnoreCase("da_DK")){
            flag = "Denmark";
        } else if(preferredLang.equalsIgnoreCase("se_SE")){
            flag = "Swedish";
        } else if(preferredLang.equalsIgnoreCase("no_NO")){
            flag = "Norway";
        }
        return flag;
    }
}

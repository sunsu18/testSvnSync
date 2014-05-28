package com.sfr.engage.helptaskflow;


import com.sfr.engage.core.Help;
import com.sfr.engage.model.queries.rvo.PrtGenHelpRVORowImpl;


import com.sfr.util.AccessDataControl;

import com.sfr.util.constants.Constants;

import com.sfr.util.validations.Conversion;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;

import javax.faces.context.ExternalContext;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.jbo.ViewObject;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.share.logging.ADFLogger;

public class HelpInfoBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private List<Help> helpList = null;
    private transient Bindings bindings;
    private String countryPortal;
    private String langPortal;
    private String portal;
    AccessDataControl accessDC = new AccessDataControl();
    Conversion conversionUtility = new Conversion();
    
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    
    

    public HelpInfoBean() {
        log.fine(accessDC.getDisplayRecord()+ this.getClass() + " Inside Constructor of HelpInfo Bean");
        ExternalContext ectx =
            FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
        HttpSession session = (HttpSession)request.getSession();
        
        if(session.getAttribute(Constants.userLang) != null){
            countryPortal = (String)session.getAttribute(Constants.userLang);
        }
        else {
            countryPortal = (String)session.getAttribute("lang").toString().substring(3); 
        }

        if (session.getAttribute(Constants.HELPLIST+countryPortal) == null) {
            log.info(accessDC.getDisplayRecord() + this.getClass() +" helpList is null so creating list of Q&A");
            
          
            
            if(session.getAttribute(Constants.userLang) != null)
            {   
                
                countryPortal = (String)session.getAttribute(Constants.userLang);
                log.info(accessDC.getDisplayRecord()+ this.getClass() + " Session not null and lang/country of user is " + countryPortal);
                langPortal = conversionUtility.getCustomerCountryCode(countryPortal);
                log.info(accessDC.getDisplayRecord()+ this.getClass() + " Session not null and lang of portal is " + langPortal);
                portal = Constants.ENGAGE;
            }
            else 
            {
                //Read from URL
                
               countryPortal = (String)session.getAttribute("lang").toString().substring(3);
               log.info(accessDC.getDisplayRecord()+ this.getClass() + " Session not null and countryPortal from url is " + countryPortal);
                langPortal =  (String)session.getAttribute("lang");
                log.info(accessDC.getDisplayRecord()+ this.getClass() + " Session not null and lang of Portal from url is " + langPortal);
                portal = Constants.ENGAGE;
            }
            
                
                
      
//            else {
//                
//                countryPortal = "SE";
//                log.info(accessDC.getDisplayRecord()+ this.getClass() + " Session is null and lang/country of user is " + countryPortal);
//                langPortal = conversionUtility.getCustomerCountryCode(countryPortal);
//                log.info(accessDC.getDisplayRecord()+ this.getClass() + " Session is null and lang of portal is " + langPortal);
//                portal = Constants.ENGAGE;
//                
//            }

            

            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter;
            if (bindings != null) {
                iter = bindings.findIteratorBinding("PrtGenHelpRVO1Iterator");
               
            } else {
                log.warning(accessDC.getDisplayRecord() + this.getClass() +" PrtGenHelpRVOIterator bindings is nul"); 

                iter = null;
            }
            ViewObject vo = iter.getViewObject();
            if(countryPortal.equalsIgnoreCase("US")) {
                countryPortal = "SE";
                langPortal = "se_SE";
            }
            log.info(accessDC.getDisplayRecord() + this.getClass() +" country, lang and portalname passed for FAQ are " + countryPortal +", " + langPortal+ " and " + portal);
            vo.setNamedWhereClauseParam("country", countryPortal);
            vo.setNamedWhereClauseParam("lang", langPortal);
            vo.setNamedWhereClauseParam("portalname", portal);
            vo.executeQuery();
            


            helpList = new ArrayList<Help>();
            if (vo.getEstimatedRowCount() != 0) {

                
                log.info(accessDC.getDisplayRecord() + this.getClass() +" RowCount in helpinfo "+vo.getEstimatedRowCount());

                while (vo.hasNext()) {
                    Help help = new Help();
                    PrtGenHelpRVORowImpl currRow =
                        (PrtGenHelpRVORowImpl)vo.next();

                    if (currRow != null) {
                       

                        if (currRow.getKeyQuest() != null) {
                            
                            help.setQuestion(currRow.getKeyQuest().toString());
                        }
                        if (currRow.getKeyAnswer() != null) {
                            
                            help.setAnswer(currRow.getKeyAnswer());
                        }
                        helpList.add(help);
                    }

                }
                
                
               session.setAttribute(Constants.HELPLIST+countryPortal, helpList); 

            }

            
        }
        else
        {
            log.info(accessDC.getDisplayRecord() + " HelpList is not null so no need to create list of Q&A"); 
            helpList = (List<Help>)session.getAttribute(Constants.HELPLIST+countryPortal);
            
        }
        log.fine(accessDC.getDisplayRecord() + " Exiting Constructor of HelpInfo Bean"); 
     
    }

    /**
     * @return
     */
    public HelpInfoBean.Bindings getBindings() {
        if (bindings == null) {
            bindings = new Bindings();
        }
        return bindings;
    }

    public void setCountryPortal(String countryPortal) {
        this.countryPortal = countryPortal;
    }

    public String getCountryPortal() {
        return countryPortal;
    }

    public void setLangPortal(String langPortal) {
        this.langPortal = langPortal;
    }

    public String getLangPortal() {
        return langPortal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }

    public String getPortal() {
        return portal;
    }

    public void setHelpList(List<Help> helpList) {
        this.helpList = helpList;
    }

    public List<Help> getHelpList() {
        return helpList;
    }

    public class Bindings {
        private RichPanelGroupLayout helpinfopanel;

        public void setHelpinfopanel(RichPanelGroupLayout helpinfopanel) {
            this.helpinfopanel = helpinfopanel;
        }

        public RichPanelGroupLayout getHelpinfopanel() {
            return helpinfopanel;
        }
    }


    
}

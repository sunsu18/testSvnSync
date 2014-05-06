package com.sfr.engage.helptaskflow;


import com.sfr.engage.core.Help;
import com.sfr.engage.model.queries.rvo.PrtGenHelpRVORowImpl;


import com.sfr.util.AccessDataControl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

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
    private static List<Help> helpList = null;
    private transient Bindings bindings;
    AccessDataControl accessDC = new AccessDataControl();
    
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    
    

    public HelpInfoBean() {
        log.fine(accessDC.getDisplayRecord()+ this.getClass() + "Inside Constructor of HelpInfo Bean");


        if (helpList == null) {
            log.info(accessDC.getDisplayRecord() + this.getClass() +" helpList is null so creating list of Q&A");

            ExternalContext ectx =
                FacesContext.getCurrentInstance().getExternalContext();
            HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
            HttpSession session = (HttpSession)request.getSession();
            //country = (String)session.getAttribute("lang");

            String country_portal = "SE";
            String lang_portal = "se_SE";
            String portal = "ENGAGE";

            DCBindingContainer bindings =
                (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding iter;
            if (bindings != null) {
                iter = bindings.findIteratorBinding("PrtGenHelpRVO1Iterator");
               
            } else {
                log.warning(accessDC.getDisplayRecord() + this.getClass() +"PrtGenHelpRVOIterator bindings is nul"); 

                iter = null;
            }
            ViewObject vo = iter.getViewObject();
            vo.setNamedWhereClauseParam("country", country_portal);
            vo.setNamedWhereClauseParam("lang", lang_portal);
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
                

            }

            
        }
        else
        {
            log.info(accessDC.getDisplayRecord() + "helpList is not null so no need to create list of Q&A"); 
           
            
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

    public class Bindings {
        private RichPanelGroupLayout helpinfopanel;

        public void setHelpinfopanel(RichPanelGroupLayout helpinfopanel) {
            this.helpinfopanel = helpinfopanel;
        }

        public RichPanelGroupLayout getHelpinfopanel() {
            return helpinfopanel;
        }
    }


    /**
     * @param helpList
     */
    public void setHelpList(List<Help> helpList) {
        HelpInfoBean.helpList = helpList;
    }

    /**
     * @return
     */
    public List<Help> getHelpList() {
        return helpList;
    }
}

package com.sfr.engage.helptaskflow;

import com.sfr.engage.core.DriverInfo;
import com.sfr.engage.core.Help;
import com.sfr.engage.model.queries.rvo.PrtGenHelpRVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtDriverInformationVORowImpl;

import com.sfr.engage.pricelisttaskflow.PriceList_new;

import com.sfr.engage.pricelisttaskflow.PriceList_new.Bindings;

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

public class HelpInfo implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;    
    private static List<Help> helpList=null;
    private transient Bindings bindings;

    public HelpInfo() {
        
            if(helpList == null)
            {
                       
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
                HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
                HttpSession session = (HttpSession)request.getSession();
                //country = (String)session.getAttribute("lang");
                
                String country_portal = "SE";
                String lang_portal = "se_SE";
                String portal = "ENGAGE";
              
                DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding iter;
                if (bindings != null) {
                    iter = bindings.findIteratorBinding("PrtGenHelpRVO1Iterator");
                    System.out.println("DC Iterator bindings found in helpinfo");
                } else {
                    System.out.println("bindings is null");
                    iter = null;
                }
                ViewObject vo = iter.getViewObject();
                vo.setNamedWhereClauseParam("country", country_portal );
                vo.setNamedWhereClauseParam("lang", lang_portal );
                vo.setNamedWhereClauseParam("portalname", portal );
                vo.executeQuery();
             //   System.out.println("row count " + vo.getEstimatedRowCount());
                
           
            
             helpList=new ArrayList<Help>();
        if (vo.getEstimatedRowCount() != 0) {
                
            //System.out.println("RowCount in helpinfo "+vo.getEstimatedRowCount());
            
                while (vo.hasNext()) {
                        Help help = new Help();
                    PrtGenHelpRVORowImpl currRow = (PrtGenHelpRVORowImpl)vo.next();
                  
                    if (currRow != null) {
                    // System.out.println (" row != null");
                        
                        if(currRow.getKeyQuest() != null){
                      //  System.out.println("Question is " + currRow.getKeyQuest());
                        help.setQuestion(currRow.getKeyQuest().toString());
                        }
                        if(currRow.getKeyAnswer()!= null){
                        //System.out.println("Answer is " + currRow.getKeyAnswer());
                        help.setAnswer(currRow.getKeyAnswer());
                        }
                            helpList.add(help); 
                        }
                     
                    }
                //System.out.println("j value is " + j);
                
            }
        
        //System.out.println("helplist size " + helplist.size());
            }           

        }

    /**
     * @return
     */
    public HelpInfo.Bindings getBindings() {
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
        HelpInfo.helpList = helpList;
    }

    /**
     * @return
     */
    public List<Help> getHelpList() {
        return helpList;
    }
}

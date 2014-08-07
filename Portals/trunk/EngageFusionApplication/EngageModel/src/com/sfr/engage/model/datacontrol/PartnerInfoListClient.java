package com.sfr.engage.model.datacontrol;

import com.sfr.core.bean.User;
import com.sfr.engage.core.PartnerInfo;
import com.sfr.util.AccessDataControl;
import com.sfr.util.validations.Conversion;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import oracle.adf.share.logging.ADFLogger;



public class PartnerInfoListClient {
    
    private List<PartnerInfo> partnerlist;
        private HttpServletRequest request;
        private ExternalContext ectx;
        private HttpSession session;
        private User user = null;
    
    Conversion conv = new Conversion();
    AccessDataControl accessDC = new AccessDataControl();
    
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
        
    public PartnerInfoListClient() {
        super();
    }

    public void setPartnerlist(List<PartnerInfo> partnerlist) {
        this.partnerlist = partnerlist;
    }

    public List<PartnerInfo> getPartnerlist() {
        
        
               partnerlist=new ArrayList<PartnerInfo>();
               
               
               
               ectx = FacesContext.getCurrentInstance().getExternalContext();
               request = (HttpServletRequest)ectx.getRequest();
               session = request.getSession(false);
               
               if(session!= null) {
                   partnerlist = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
                   
                   for(int z=0; z < partnerlist.size();z++)
                   {
                       
                       log.info(accessDC.getDisplayRecord() +  this.getClass() + " Partner value from session in string " + partnerlist.get(z).getPartnerValue().toString());
                   }
                  
                   
               }
               else
               {  
               log.severe(accessDC.getDisplayRecord() +  this.getClass() + " Session null in client so tree will not be rendered");
               }
        return partnerlist;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setEctx(ExternalContext ectx) {
        this.ectx = ectx;
    }

    public ExternalContext getEctx() {
        return ectx;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}

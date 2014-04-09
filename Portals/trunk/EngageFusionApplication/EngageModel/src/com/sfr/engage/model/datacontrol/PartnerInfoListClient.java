package com.sfr.engage.model.datacontrol;

import com.sfr.engage.core.PartnerInfo;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PartnerInfoListClient {
    
    private List<PartnerInfo> partnerlist;
        private HttpServletRequest request;
        private ExternalContext ectx;
        private HttpSession session;
        
    public PartnerInfoListClient() {
        super();
    }

    public void setPartnerlist(List<PartnerInfo> partnerlist) {
        this.partnerlist = partnerlist;
    }

    public List<PartnerInfo> getPartnerlist() {
        
        System.out.println("SOP of client");
               partnerlist=new ArrayList<PartnerInfo>();
               PartnerInfo partner = new PartnerInfo();
               
               
               ectx = FacesContext.getCurrentInstance().getExternalContext();
               request = (HttpServletRequest)ectx.getRequest();
               session = request.getSession(false);
               
               if(session!= null) {
                   
                   System.out.println("partner list from session");
                   partner = (PartnerInfo)session.getAttribute("Partner_Object_List");
                   
                   System.out.println("partner value from session in string " + partner.getPartnerValue().toString());
                   System.out.println("partner value from session " + partner.getPartnerValue());
                     
                   partnerlist.add(partner);
                   System.out.println("size of list===>"+partnerlist.size());
               }
               else
                   System.out.println("session null in client");
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
}

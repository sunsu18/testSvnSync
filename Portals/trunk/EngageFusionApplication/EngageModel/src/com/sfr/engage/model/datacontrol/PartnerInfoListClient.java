package com.sfr.engage.model.datacontrol;

import com.sfr.core.bean.User;
import com.sfr.engage.core.AccountInfo;
import com.sfr.engage.core.CardGroupInfo;
import com.sfr.engage.core.CardInfo;
import com.sfr.engage.core.PartnerInfo;

import com.sfr.engage.model.queries.uvo.PrtAccountVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtCardVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtCardgroupVORowImpl;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;

import com.sfr.util.validations.Conversion;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.ViewObject;

public class PartnerInfoListClient {
    
    private List<PartnerInfo> partnerlist;
        private HttpServletRequest request;
        private ExternalContext ectx;
        private HttpSession session;
        private User user = null;
    private static boolean flagexecute = false;
    Conversion conv = new Conversion();
        
    public PartnerInfoListClient() {
        super();
    }

    public void setPartnerlist(List<PartnerInfo> partnerlist) {
        this.partnerlist = partnerlist;
    }

    public List<PartnerInfo> getPartnerlist() {
        
        //System.out.println("SOP of client");
               partnerlist=new ArrayList<PartnerInfo>();
               PartnerInfo partner = new PartnerInfo();
               
               
               ectx = FacesContext.getCurrentInstance().getExternalContext();
               request = (HttpServletRequest)ectx.getRequest();
               session = request.getSession(false);
               
               if(session!= null) {
                    //System.out.println("partner list from session");
                   partner = (PartnerInfo)session.getAttribute("Partner_Object_List");
                   
                   System.out.println("Partner value from session in string " + partner.getPartnerValue().toString());
                   //System.out.println("partner value from session " + partner.getPartnerValue());
                     
                   partnerlist.add(partner);
                   
               }
               else
                   System.out.println("Session null in client so tree will not be rendered");
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

package com.sfr.engage.alertstaskflow;

import com.sfr.engage.core.FuelTimings;

import com.sfr.engage.core.PartnerInfo;

import com.sfr.engage.model.resources.EngageResourceBundle;
import com.sfr.util.constants.Constants;

import com.sfr.util.validations.Conversion;

import java.util.ArrayList;
import java.util.List;

import java.util.ResourceBundle;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

public class Alerts {
    private List<FuelTimings> fueltimings = new ArrayList<FuelTimings>();
    private RichPopup alert1Popup;
    private RichInputText fromTimings;
    private RichInputText toTimings;
    private HttpSession session;
    private ExternalContext ectx;
    private HttpServletRequest request;
    private List<PartnerInfo> partnerInfoList;
    private List<SelectItem> partnerList = null;
    private List<String> partnerValue = null;
    private List<String> accountValue;
    private List<String> cardGroupValue;

    private List<SelectItem> accountList = null;
  
    private List<SelectItem> cardGroupList = null;
    private List<SelectItem> cardList = null;
    private List<String> cardValue;
    private RichPopup alert2Popup;
    private String langValue;

    public Alerts() {
        ectx = FacesContext.getCurrentInstance().getExternalContext();
        request = (HttpServletRequest)ectx.getRequest();
        session = request.getSession(false);
        partnerInfoList = new ArrayList<PartnerInfo>();
        partnerList = new ArrayList<SelectItem>();
        partnerValue = new ArrayList<String>();
        accountList = new ArrayList<SelectItem>();
        accountValue = new ArrayList<String>();
        cardGroupList = new ArrayList<SelectItem>();
        cardGroupValue = new ArrayList<String>();
        cardList = new ArrayList<SelectItem>();
        cardValue = new ArrayList<String>();
        Conversion conv = new Conversion();
        ResourceBundle resourceBundle = new EngageResourceBundle();
        
        if (session.getAttribute(Constants.DISPLAY_PORTAL_LANG) !=null) {
                        langValue = conv.getCustomerCountryCode((String)session.getAttribute(Constants.DISPLAY_PORTAL_LANG));
        }
        
        
        
        FuelTimings f = new FuelTimings();
        if (resourceBundle.containsKey("MONDAY")) {
            f.setWeekday((String)resourceBundle.getObject("MONDAY"));
        }
        f.setFrom("8:00");
        f.setTo("17:00");
        fueltimings.add(f);
        
        f = new FuelTimings();
        if (resourceBundle.containsKey("TUESDAY")) {
            f.setWeekday((String)resourceBundle.getObject("TUESDAY"));
        }
                f.setFrom("9:00");
                f.setTo("18:00");
                fueltimings.add(f);
                
        f = new FuelTimings();
        if (resourceBundle.containsKey("WEDNESDAY")) {
            f.setWeekday((String)resourceBundle.getObject("WEDNESDAY"));
        }
                f.setFrom("8:30");
                f.setTo("17:30");
                fueltimings.add(f);
                
        f = new FuelTimings();
        if (resourceBundle.containsKey("THURSDAY")) {
            f.setWeekday((String)resourceBundle.getObject("THURSDAY"));
        }
                f.setFrom("8:00");
                f.setTo("17:00");
                fueltimings.add(f);
                
        f = new FuelTimings();
        if (resourceBundle.containsKey("FRIDAY")) {
            f.setWeekday((String)resourceBundle.getObject("FRIDAY"));
        }
                f.setFrom("8:00");
                f.setTo("17:00");
                fueltimings.add(f);
                
        f = new FuelTimings();
        if (resourceBundle.containsKey("SATURDAY")) {
            f.setWeekday((String)resourceBundle.getObject("SATURDAY"));
        }
                f.setFrom("8:00");
                f.setTo("17:00");
                fueltimings.add(f);
                
        f = new FuelTimings();
        if (resourceBundle.containsKey("SUNDAY")) {
            f.setWeekday((String)resourceBundle.getObject("SUNDAY"));
        }
                f.setFrom("8:00");
                f.setTo("17:00");
                fueltimings.add(f);
                
                
        if (session.getAttribute("Partner_Object_List") != null) {
            System.out.println("partnerlist not null");
            partnerInfoList = (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
        }

        if (partnerInfoList != null && partnerInfoList.size() > 0) {
            for (int i = 0; i < partnerInfoList.size(); i++) {
                if (partnerInfoList.get(i).getPartnerName() != null && partnerInfoList.get(i).getPartnerValue() != null) {
                    SelectItem selectItemPartner = new SelectItem();
                    selectItemPartner.setLabel(partnerInfoList.get(i).getPartnerName().toString());
                    selectItemPartner.setValue(partnerInfoList.get(i).getPartnerValue().toString());
                    partnerList.add(selectItemPartner);
                    partnerValue.add(partnerInfoList.get(i).getPartnerValue().toString());
                }
                
                if (partnerInfoList.get(i).getAccountList() != null && partnerInfoList.get(i).getAccountList().size() > 0) {
                    for (int j = 0; j < partnerInfoList.get(i).getAccountList().size(); j++) {
                        if (partnerInfoList.get(i).getAccountList().get(j).getAccountNumber() != null) {
                            SelectItem selectItem = new SelectItem();
                            selectItem.setLabel(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            selectItem.setValue(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            accountList.add(selectItem);
                            accountValue.add(partnerInfoList.get(i).getAccountList().get(j).getAccountNumber().toString());
                            if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup() != null &&
                                partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size() > 0) {
                             

                                for (int cg = 0; cg < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().size(); cg++) {

                                    SelectItem selectItemCardGroup = new SelectItem();
                                    selectItemCardGroup.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getDisplayCardGroupIdName().toString());
                                    selectItemCardGroup.setValue(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                                 partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCardGroupID().toString());
                                    cardGroupList.add(selectItemCardGroup);
                                    cardGroupValue.add(partnerInfoList.get(i).getPartnerValue().toString().trim() +
                                                       partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCardGroupID().toString());
                                    
                                    if(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard()!=null && 
                                       partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().size() > 0) {
                                           
                                               for (int cc = 0;
                                                    cc < partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().size(); cc++) {
                                                   if (partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().get(cc).getCardID() !=
                                                       null &&
                                                       partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().get(cc).getExternalCardID() !=
                                                       null) {
                                                       SelectItem selectItemCard = new SelectItem();
                                                       selectItemCard.setLabel(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().get(cc).getExternalCardID().toString());
                                                       selectItemCard.setValue(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().get(cc).getCardID());
                                                       cardList.add(selectItemCard);
                                                       cardValue.add(partnerInfoList.get(i).getAccountList().get(j).getCardGroup().get(cg).getCard().get(cc).getCardID());
                                                   }
                                               }
                                       }
                                 
                                   

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void configureBusinessHoursAlert(ActionEvent actionEvent) {
      
        RichPopup.PopupHints ps = new RichPopup.PopupHints();
        alert1Popup.show(ps);
        
    }

    public void setFueltimings(List<FuelTimings> fueltimings) {
        this.fueltimings = fueltimings;
    }

    public List<FuelTimings> getFueltimings() {
        return fueltimings;
    }

    public void setAlert1Popup(RichPopup alert1Popup) {
        this.alert1Popup = alert1Popup;
    }

    public RichPopup getAlert1Popup() {
        return alert1Popup;
    }

    public void claoseAlert1Popup(ActionEvent actionEvent) {
 
        getAlert1Popup().hide();
        getAlert2Popup().hide();
    }

    public void editAlert1Timings(ActionEvent actionEvent) {
        
        fromTimings.setReadOnly(false);
        toTimings.setReadOnly(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(fromTimings);
        AdfFacesContext.getCurrentInstance().addPartialTarget(toTimings);
    }

    public void setFromTimings(RichInputText fromTimings) {
        this.fromTimings = fromTimings;
    }

    public RichInputText getFromTimings() {
        return fromTimings;
    }

    public void setToTimings(RichInputText toTimings) {
        this.toTimings = toTimings;
    }

    public RichInputText getToTimings() {
        return toTimings;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setEctx(ExternalContext ectx) {
        this.ectx = ectx;
    }

    public ExternalContext getEctx() {
        return ectx;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setPartnerInfoList(List<PartnerInfo> partnerInfoList) {
        this.partnerInfoList = partnerInfoList;
    }

    public List<PartnerInfo> getPartnerInfoList() {
        return partnerInfoList;
    }

    public void setPartnerList(List<SelectItem> partnerList) {
        this.partnerList = partnerList;
    }

    public List<SelectItem> getPartnerList() {
        return partnerList;
    }

    public void setPartnerValue(List<String> partnerValue) {
        this.partnerValue = partnerValue;
    }

    public List<String> getPartnerValue() {
        return partnerValue;
    }

    public void setAccountValue(List<String> accountValue) {
        this.accountValue = accountValue;
    }

    public List<String> getAccountValue() {
        return accountValue;
    }

    public void setCardGroupValue(List<String> cardGroupValue) {
        this.cardGroupValue = cardGroupValue;
    }

    public List<String> getCardGroupValue() {
        return cardGroupValue;
    }

    public void setAccountList(List<SelectItem> accountList) {
        this.accountList = accountList;
    }

    public List<SelectItem> getAccountList() {
        return accountList;
    }

    public void setCardGroupList(List<SelectItem> cardGroupList) {
        this.cardGroupList = cardGroupList;
    }

    public List<SelectItem> getCardGroupList() {
        return cardGroupList;
    }

    public void setCardList(List<SelectItem> cardList) {
        this.cardList = cardList;
    }

    public List<SelectItem> getCardList() {
        return cardList;
    }

    public void setCardValue(List<String> cardValue) {
        this.cardValue = cardValue;
    }

    public List<String> getCardValue() {
        return cardValue;
    }

    public void setAlert2Popup(RichPopup alert2Popup) {
        this.alert2Popup = alert2Popup;
    }

    public RichPopup getAlert2Popup() {
        return alert2Popup;
    }

    public void configureFuelCapacityAlert(ActionEvent actionEvent) {
        RichPopup.PopupHints ps = new RichPopup.PopupHints();
        alert2Popup.show(ps);
    }

    public void setLangValue(String langValue) {
        this.langValue = langValue;
    }

    public String getLangValue() {
        return langValue;
    }
}

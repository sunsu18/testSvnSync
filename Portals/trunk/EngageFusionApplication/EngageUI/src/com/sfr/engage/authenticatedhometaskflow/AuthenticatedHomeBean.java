package com.sfr.engage.authenticatedhometaskflow;


import com.sfr.engage.core.Messages;
import com.sfr.engage.model.queries.rvo.PrtGenStringRVORowImpl;

import com.sfr.engage.model.queries.rvo.PrtPcmFeedsRVORowImpl;
import com.sfr.engage.utility.util.ADFUtils;

import com.sfr.engage.vehicleinfotaskflow.VehicleInfoBean;

import java.io.Serializable;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.jbo.ViewObject;

public class AuthenticatedHomeBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    private String infoValue="";
    private List<Messages> messages;
    private boolean infoPanelVisible;


    /**
     * @return bindings Object
     */
    public Bindings getBindings() {
        if (bindings == null) {
            bindings = new Bindings();
        }
        return bindings;
    }

    public AuthenticatedHomeBean() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
        Date date = new Date();
        System.out.println(dateFormat.format(date));       
        java.sql.Date passedDate=new  java.sql.Date(date.getTime());
        ViewObject prtPCMFeedsVO = ADFUtils.getViewObject("PrtPcmFeedsRVO1Iterator");        
        prtPCMFeedsVO.setWhereClause("CUSTOMER_TYPE =: customerType AND INFORMATION_TYPE =:infoType AND COUNTRY_CODE=:countryCode AND EFFECTIVE_DATE <=:fromDate AND END_DATE >=:toDate");
        prtPCMFeedsVO.defineNamedWhereClauseParam("customerType", "B2B FLEET", null);
        prtPCMFeedsVO.defineNamedWhereClauseParam("infoType", "INFO_STATOIL", null);
        prtPCMFeedsVO.defineNamedWhereClauseParam("countryCode", "se_SE", null);
        prtPCMFeedsVO.defineNamedWhereClauseParam("fromDate", passedDate, null);
        prtPCMFeedsVO.defineNamedWhereClauseParam("toDate", passedDate, null);
        System.out.println("Query ="+prtPCMFeedsVO.getQuery());
        prtPCMFeedsVO.executeQuery();
        System.out.println("Information Row Count "+prtPCMFeedsVO.getEstimatedRowCount());
       
        if (prtPCMFeedsVO.getEstimatedRowCount() != 0) 
        {
            infoPanelVisible=true;
            System.out.println("coming inside INFORMATION block");          
                while (prtPCMFeedsVO.hasNext()) {
                    PrtPcmFeedsRVORowImpl currRow =
                        (PrtPcmFeedsRVORowImpl)prtPCMFeedsVO.next();

                    if (currRow != null) {
                        if(currRow.getMessageLang()!=null)
                        {
                        infoValue = infoValue+currRow.getMessageLang();
                        }else {
                            if(currRow.getMessageEnglish()!=null) {
                                infoValue = infoValue+currRow.getMessageLang();
                            }
                        }
                    }
                }            
        }
       if(infoValue==null) {
           infoPanelVisible=false;
       }
        
        prtPCMFeedsVO.defineNamedWhereClauseParam("customerType", "B2B FLEET", null);
        prtPCMFeedsVO.defineNamedWhereClauseParam("infoType", "MESSAGES", null);
        prtPCMFeedsVO.defineNamedWhereClauseParam("countryCode", "se_SE", null);
        prtPCMFeedsVO.defineNamedWhereClauseParam("fromDate", passedDate, null);
        prtPCMFeedsVO.defineNamedWhereClauseParam("toDate", passedDate, null);
        prtPCMFeedsVO.executeQuery();
        System.out.println("Messages Row Count "+prtPCMFeedsVO.getEstimatedRowCount());
        if (prtPCMFeedsVO.getEstimatedRowCount() != 0) 
        {          
            messages=new ArrayList<Messages>();
            System.out.println("coming inside MESSAGE block");          
                while (prtPCMFeedsVO.hasNext()) {
                    PrtPcmFeedsRVORowImpl currRow =
                        (PrtPcmFeedsRVORowImpl)prtPCMFeedsVO.next();

                    if (currRow != null) {
                        Messages message=new Messages();
                        if(currRow.getMessageLang()!=null)
                        {
                        message.setMessage(currRow.getMessageLang());                       
                        }else {
                            if(currRow.getMessageEnglish()!=null) {
                                message.setMessage(currRow.getMessageEnglish());
                            }
                        }
                        if(message.getMessage()!=null)
                        {
                        messages.add(message);
                        }
                    }
                }            
        } 
        
        if ("CUSTOMER_TYPE =: customerType AND INFORMATION_TYPE =:infoType AND COUNTRY_CODE=:countryCode AND EFFECTIVE_DATE <=:fromDate AND END_DATE >=:toDate".equalsIgnoreCase(prtPCMFeedsVO.getWhereClause())) {
            prtPCMFeedsVO.removeNamedWhereClauseParam("customerType");            
            prtPCMFeedsVO.removeNamedWhereClauseParam("infoType");
            prtPCMFeedsVO.removeNamedWhereClauseParam("countryCode");
            prtPCMFeedsVO.removeNamedWhereClauseParam("fromDate");
            prtPCMFeedsVO.removeNamedWhereClauseParam("toDate");            
            prtPCMFeedsVO.setWhereClause("");
            prtPCMFeedsVO.executeQuery();
        }        
        
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

    public List<Messages> getMessages() {
        return messages;
    }

    public void setInfoPanelVisible(boolean infoPanelVisible) {
        this.infoPanelVisible = infoPanelVisible;
    }

    public boolean isInfoPanelVisible() {
        return infoPanelVisible;
    }

    public void setInfoValue(String infoValue) {
        this.infoValue = infoValue;
    }

    public String getInfoValue() {
        return infoValue;
    }


    public class Bindings {
        private RichOutputText infoText;
        private RichPanelGroupLayout infoPanel;

        public void setInfoText(RichOutputText infoText) {
            this.infoText = infoText;
        }

        public RichOutputText getInfoText() {
            return infoText;
        }
        
        public void setInfoPanel(RichPanelGroupLayout infoPanel) {
            this.infoPanel = infoPanel;
        }

        public RichPanelGroupLayout getInfoPanel() {
            return infoPanel;
        }
    }
}


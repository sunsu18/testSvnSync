package com.sfr.engage.authenticatedhometaskflow;


import com.sfr.engage.core.Messages;
import com.sfr.engage.model.queries.rvo.PrtGenStringRVORowImpl;

import com.sfr.engage.utility.util.ADFUtils;

import com.sfr.engage.vehicleinfotaskflow.VehicleInfoBean;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.jbo.ViewObject;

public class AuthenticatedHomeBean implements Serializable {
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private transient Bindings bindings;
    private String Infovalue;
    private List<Messages> messages;

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
        ViewObject vo = ADFUtils.getViewObject("PrtGenStringRVO1Iterator");
        System.out.println("query=====>" + vo.getQuery());
        vo.setWhereClause("ID =:var");
        vo.defineNamedWhereClauseParam("var", 102613, null);
        vo.executeQuery();

        if (vo.getEstimatedRowCount() != 0) {
            System.out.println("coming inside this block");
            for (int j = 0; j < vo.getEstimatedRowCount(); j++) {
                while (vo.hasNext()) {
                    PrtGenStringRVORowImpl currRow =
                        (PrtGenStringRVORowImpl)vo.next();

                    if (currRow != null) {
                        Infovalue = (String)currRow.getAttribute("KeyValue");
                    }
                }
            }
        }
        
        if ("ID =:var".equalsIgnoreCase(vo.getWhereClause())) {
            vo.removeNamedWhereClauseParam("var");            
            vo.setWhereClause("");
            vo.executeQuery();
        }
        
        vo.setWhereClause("KEY_VALUE =:keyValue");
        vo.defineNamedWhereClauseParam("keyValue", "Administration", null);
        vo.executeQuery();

        if (vo.getEstimatedRowCount() != 0) {
            messages=new ArrayList<Messages>();
            System.out.println("coming inside this block");
            for (int j = 0; j < vo.getEstimatedRowCount(); j++) {
                while (vo.hasNext()) {
                    PrtGenStringRVORowImpl currRow =
                        (PrtGenStringRVORowImpl)vo.next();

                    if (currRow != null) {
                        Messages message=new Messages();
                        message.setMessage(currRow.getKeyValue()); 
                        messages.add(message);
                        }                    
                }
            }
        }
    }


    public void setInfovalue(String Infovalue) {
        this.Infovalue = Infovalue;
    }

    public String getInfovalue() {
        return Infovalue;
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

    public List<Messages> getMessages() {
        return messages;
    }

    public class Bindings {
        private RichOutputText infoText;

        public void setInfoText(RichOutputText infoText) {
            this.infoText = infoText;
        }

        public RichOutputText getInfoText() {
            return infoText;
        }
    }
}


package com.sfr.engage.model.queries.rvo;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jul 01 21:06:45 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtHomeTransactionsRVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public PrtHomeTransactionsRVOImpl() {
    }

    /**
     * Returns the bind variable value for countryCode.
     * @return bind variable value for countryCode
     */
    public String getcountryCode() {
        return (String)getNamedWhereClauseParam("countryCode");
    }

    /**
     * Sets <code>value</code> for bind variable countryCode.
     * @param value value to bind as countryCode
     */
    public void setcountryCode(String value) {
        setNamedWhereClauseParam("countryCode", value);
    }

    /**
     * Returns the bind variable value for partnerId.
     * @return bind variable value for partnerId
     */
    public String getpartnerId() {
        return (String)getNamedWhereClauseParam("partnerId");
    }

    /**
     * Sets <code>value</code> for bind variable partnerId.
     * @param value value to bind as partnerId
     */
    public void setpartnerId(String value) {
        setNamedWhereClauseParam("partnerId", value);
    }
}
package com.sfr.engage.model.queries.uvo;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Jun 06 12:15:53 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtNewInvoiceVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public PrtNewInvoiceVOImpl() {
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

    /**
     * Returns the bind variable value for accountId.
     * @return bind variable value for accountId
     */
    public String getaccountId() {
        return (String)getNamedWhereClauseParam("accountId");
    }

    /**
     * Sets <code>value</code> for bind variable accountId.
     * @param value value to bind as accountId
     */
    public void setaccountId(String value) {
        setNamedWhereClauseParam("accountId", value);
    }

    /**
     * Returns the bind variable value for fromDateBV.
     * @return bind variable value for fromDateBV
     */
    public String getfromDateBV() {
        return (String)getNamedWhereClauseParam("fromDateBV");
    }

    /**
     * Sets <code>value</code> for bind variable fromDateBV.
     * @param value value to bind as fromDateBV
     */
    public void setfromDateBV(String value) {
        setNamedWhereClauseParam("fromDateBV", value);
    }

    /**
     * Returns the bind variable value for toDateBV.
     * @return bind variable value for toDateBV
     */
    public String gettoDateBV() {
        return (String)getNamedWhereClauseParam("toDateBV");
    }

    /**
     * Sets <code>value</code> for bind variable toDateBV.
     * @param value value to bind as toDateBV
     */
    public void settoDateBV(String value) {
        setNamedWhereClauseParam("toDateBV", value);
    }
}

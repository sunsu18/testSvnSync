package com.sfr.engage.model.queries.rvo;

import java.sql.ResultSet;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jun 30 12:43:17 IST 2014
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
     * executeQueryForCollection - overridden for custom java data source support.
     */
    protected void executeQueryForCollection(Object qc, Object[] params,
                                             int noUserParams) {
        super.executeQueryForCollection(qc, params, noUserParams);
    }

    /**
     * hasNextForCollection - overridden for custom java data source support.
     */
    protected boolean hasNextForCollection(Object qc) {
        boolean bRet = super.hasNextForCollection(qc);
        return bRet;
    }

    /**
     * createRowFromResultSet - overridden for custom java data source support.
     */
    protected ViewRowImpl createRowFromResultSet(Object qc,
                                                 ResultSet resultSet) {
        ViewRowImpl value = super.createRowFromResultSet(qc, resultSet);
        return value;
    }

    /**
     * getQueryHitCount - overridden for custom java data source support.
     */
    public long getQueryHitCount(ViewRowSetImpl viewRowSet) {
        long value = super.getQueryHitCount(viewRowSet);
        return value;
    }

    /**
     * Returns the bind variable value for userid.
     * @return bind variable value for userid
     */
    public String getuserid() {
        return (String)getNamedWhereClauseParam("userid");
    }

    /**
     * Sets <code>value</code> for bind variable userid.
     * @param value value to bind as userid
     */
    public void setuserid(String value) {
        setNamedWhereClauseParam("userid", value);
    }

    /**
     * Returns the bind variable value for role.
     * @return bind variable value for role
     */
    public String getrole() {
        return (String)getNamedWhereClauseParam("role");
    }

    /**
     * Sets <code>value</code> for bind variable role.
     * @param value value to bind as role
     */
    public void setrole(String value) {
        setNamedWhereClauseParam("role", value);
    }
}

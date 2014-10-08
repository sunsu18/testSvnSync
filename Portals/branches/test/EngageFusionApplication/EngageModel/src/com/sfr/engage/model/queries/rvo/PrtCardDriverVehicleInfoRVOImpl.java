package com.sfr.engage.model.queries.rvo;

import java.sql.ResultSet;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri May 16 17:39:47 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtCardDriverVehicleInfoRVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public PrtCardDriverVehicleInfoRVOImpl() {
    }


    /**
     * Returns the bind variable value for countryCd.
     * @return bind variable value for countryCd
     */
    public String getcountryCd() {
        return (String)getNamedWhereClauseParam("countryCd");
    }

    /**
     * Sets <code>value</code> for bind variable countryCd.
     * @param value value to bind as countryCd
     */
    public void setcountryCd(String value) {
        setNamedWhereClauseParam("countryCd", value);
    }


    /**
     * Returns the bind variable value for paramValue.
     * @return bind variable value for paramValue
     */
    public String getparamValue() {
        return (String)getNamedWhereClauseParam("paramValue");
    }

    /**
     * Sets <code>value</code> for bind variable paramValue.
     * @param value value to bind as paramValue
     */
    public void setparamValue(String value) {
        setNamedWhereClauseParam("paramValue", value);
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
}

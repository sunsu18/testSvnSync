package com.sfr.engage.model.queries.rvo;

import java.sql.ResultSet;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri May 16 15:16:30 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtExportInfoRVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public PrtExportInfoRVOImpl() {
    }

    /**
     * Returns the bind variable value for country_Code.
     * @return bind variable value for country_Code
     */
    public String getcountry_Code() {
        return (String)getNamedWhereClauseParam("country_Code");
    }

    /**
     * Sets <code>value</code> for bind variable country_Code.
     * @param value value to bind as country_Code
     */
    public void setcountry_Code(String value) {
        setNamedWhereClauseParam("country_Code", value);
    }

    /**
     * Returns the bind variable value for report_Page.
     * @return bind variable value for report_Page
     */
    public String getreport_Page() {
        return (String)getNamedWhereClauseParam("report_Page");
    }

    /**
     * Sets <code>value</code> for bind variable report_Page.
     * @param value value to bind as report_Page
     */
    public void setreport_Page(String value) {
        setNamedWhereClauseParam("report_Page", value);
    }

    /**
     * Returns the bind variable value for report_Type.
     * @return bind variable value for report_Type
     */
    public String getreport_Type() {
        return (String)getNamedWhereClauseParam("report_Type");
    }

    /**
     * Sets <code>value</code> for bind variable report_Type.
     * @param value value to bind as report_Type
     */
    public void setreport_Type(String value) {
        setNamedWhereClauseParam("report_Type", value);
    }

    /**
     * Returns the bind variable value for select_Criteria.
     * @return bind variable value for select_Criteria
     */
    public String getselect_Criteria() {
        return (String)getNamedWhereClauseParam("select_Criteria");
    }

    /**
     * Sets <code>value</code> for bind variable select_Criteria.
     * @param value value to bind as select_Criteria
     */
    public void setselect_Criteria(String value) {
        setNamedWhereClauseParam("select_Criteria", value);
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

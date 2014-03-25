package com.sfr.engage.model.queries.rvo;

import com.sfr.engage.model.entities.PrtAccountEOImpl;

import java.math.BigDecimal;

import java.sql.Date;
import java.sql.Timestamp;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Mar 24 13:32:57 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtAccountRVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CountryCode {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getCountryCode();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setCountryCode((String)value);
            }
        }
        ,
        AccountId {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getAccountId();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setAccountId((String)value);
            }
        }
        ,
        PartnerId {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getPartnerId();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setPartnerId((String)value);
            }
        }
        ,
        CreatedDate {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getCreatedDate();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setCreatedDate((Date)value);
            }
        }
        ,
        CreatedBy {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setCreatedBy((String)value);
            }
        }
        ,
        ChangeDate {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getChangeDate();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setChangeDate((Date)value);
            }
        }
        ,
        ChangeBy {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getChangeBy();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setChangeBy((String)value);
            }
        }
        ,
        AccountReconCode {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getAccountReconCode();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setAccountReconCode((String)value);
            }
        }
        ,
        AccountStmtCode {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getAccountStmtCode();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setAccountStmtCode((String)value);
            }
        }
        ,
        InterestCode {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getInterestCode();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setInterestCode((String)value);
            }
        }
        ,
        AccountGroup {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getAccountGroup();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setAccountGroup((String)value);
            }
        }
        ,
        BlockCode {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getBlockCode();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setBlockCode((String)value);
            }
        }
        ,
        AccountDesc {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getAccountDesc();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setAccountDesc((String)value);
            }
        }
        ,
        CreditBlockCode {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getCreditBlockCode();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setCreditBlockCode((String)value);
            }
        }
        ,
        VersionNumber {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getVersionNumber();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setVersionNumber((BigDecimal)value);
            }
        }
        ,
        DunningLevelCode {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getDunningLevelCode();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setDunningLevelCode((String)value);
            }
        }
        ,
        CreditBlockDate {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getCreditBlockDate();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setCreditBlockDate((Date)value);
            }
        }
        ,
        CreditBlockTime {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getCreditBlockTime();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setCreditBlockTime((Timestamp)value);
            }
        }
        ,
        AccountBlockDate {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getAccountBlockDate();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setAccountBlockDate((Date)value);
            }
        }
        ,
        AccountBlockTime {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getAccountBlockTime();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setAccountBlockTime((Timestamp)value);
            }
        }
        ,
        DunningProcId {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getDunningProcId();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setDunningProcId((String)value);
            }
        }
        ,
        GiroNumber {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getGiroNumber();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setGiroNumber((String)value);
            }
        }
        ,
        ModifiedBy {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getModifiedBy();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setModifiedBy((String)value);
            }
        }
        ,
        ModifiedDate {
            public Object get(PrtAccountRVORowImpl obj) {
                return obj.getModifiedDate();
            }

            public void put(PrtAccountRVORowImpl obj, Object value) {
                obj.setModifiedDate((Timestamp)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtAccountRVORowImpl object);

        public abstract void put(PrtAccountRVORowImpl object, Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }
    public static final int COUNTRYCODE = AttributesEnum.CountryCode.index();
    public static final int ACCOUNTID = AttributesEnum.AccountId.index();
    public static final int PARTNERID = AttributesEnum.PartnerId.index();
    public static final int CREATEDDATE = AttributesEnum.CreatedDate.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int CHANGEDATE = AttributesEnum.ChangeDate.index();
    public static final int CHANGEBY = AttributesEnum.ChangeBy.index();
    public static final int ACCOUNTRECONCODE = AttributesEnum.AccountReconCode.index();
    public static final int ACCOUNTSTMTCODE = AttributesEnum.AccountStmtCode.index();
    public static final int INTERESTCODE = AttributesEnum.InterestCode.index();
    public static final int ACCOUNTGROUP = AttributesEnum.AccountGroup.index();
    public static final int BLOCKCODE = AttributesEnum.BlockCode.index();
    public static final int ACCOUNTDESC = AttributesEnum.AccountDesc.index();
    public static final int CREDITBLOCKCODE = AttributesEnum.CreditBlockCode.index();
    public static final int VERSIONNUMBER = AttributesEnum.VersionNumber.index();
    public static final int DUNNINGLEVELCODE = AttributesEnum.DunningLevelCode.index();
    public static final int CREDITBLOCKDATE = AttributesEnum.CreditBlockDate.index();
    public static final int CREDITBLOCKTIME = AttributesEnum.CreditBlockTime.index();
    public static final int ACCOUNTBLOCKDATE = AttributesEnum.AccountBlockDate.index();
    public static final int ACCOUNTBLOCKTIME = AttributesEnum.AccountBlockTime.index();
    public static final int DUNNINGPROCID = AttributesEnum.DunningProcId.index();
    public static final int GIRONUMBER = AttributesEnum.GiroNumber.index();
    public static final int MODIFIEDBY = AttributesEnum.ModifiedBy.index();
    public static final int MODIFIEDDATE = AttributesEnum.ModifiedDate.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtAccountRVORowImpl() {
    }

    /**
     * Gets PrtAccountEO entity object.
     * @return the PrtAccountEO
     */
    public PrtAccountEOImpl getPrtAccountEO() {
        return (PrtAccountEOImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for COUNTRY_CODE using the alias name CountryCode.
     * @return the COUNTRY_CODE
     */
    public String getCountryCode() {
        return (String) getAttributeInternal(COUNTRYCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for COUNTRY_CODE using the alias name CountryCode.
     * @param value value to set the COUNTRY_CODE
     */
    public void setCountryCode(String value) {
        setAttributeInternal(COUNTRYCODE, value);
    }

    /**
     * Gets the attribute value for ACCOUNT_ID using the alias name AccountId.
     * @return the ACCOUNT_ID
     */
    public String getAccountId() {
        return (String) getAttributeInternal(ACCOUNTID);
    }

    /**
     * Sets <code>value</code> as attribute value for ACCOUNT_ID using the alias name AccountId.
     * @param value value to set the ACCOUNT_ID
     */
    public void setAccountId(String value) {
        setAttributeInternal(ACCOUNTID, value);
    }

    /**
     * Gets the attribute value for PARTNER_ID using the alias name PartnerId.
     * @return the PARTNER_ID
     */
    public String getPartnerId() {
        return (String) getAttributeInternal(PARTNERID);
    }

    /**
     * Sets <code>value</code> as attribute value for PARTNER_ID using the alias name PartnerId.
     * @param value value to set the PARTNER_ID
     */
    public void setPartnerId(String value) {
        setAttributeInternal(PARTNERID, value);
    }

    /**
     * Gets the attribute value for CREATED_DATE using the alias name CreatedDate.
     * @return the CREATED_DATE
     */
    public Date getCreatedDate() {
        return (Date) getAttributeInternal(CREATEDDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATED_DATE using the alias name CreatedDate.
     * @param value value to set the CREATED_DATE
     */
    public void setCreatedDate(Date value) {
        setAttributeInternal(CREATEDDATE, value);
    }

    /**
     * Gets the attribute value for CREATED_BY using the alias name CreatedBy.
     * @return the CREATED_BY
     */
    public String getCreatedBy() {
        return (String) getAttributeInternal(CREATEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATED_BY using the alias name CreatedBy.
     * @param value value to set the CREATED_BY
     */
    public void setCreatedBy(String value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**
     * Gets the attribute value for CHANGE_DATE using the alias name ChangeDate.
     * @return the CHANGE_DATE
     */
    public Date getChangeDate() {
        return (Date) getAttributeInternal(CHANGEDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for CHANGE_DATE using the alias name ChangeDate.
     * @param value value to set the CHANGE_DATE
     */
    public void setChangeDate(Date value) {
        setAttributeInternal(CHANGEDATE, value);
    }

    /**
     * Gets the attribute value for CHANGE_BY using the alias name ChangeBy.
     * @return the CHANGE_BY
     */
    public String getChangeBy() {
        return (String) getAttributeInternal(CHANGEBY);
    }

    /**
     * Sets <code>value</code> as attribute value for CHANGE_BY using the alias name ChangeBy.
     * @param value value to set the CHANGE_BY
     */
    public void setChangeBy(String value) {
        setAttributeInternal(CHANGEBY, value);
    }

    /**
     * Gets the attribute value for ACCOUNT_RECON_CODE using the alias name AccountReconCode.
     * @return the ACCOUNT_RECON_CODE
     */
    public String getAccountReconCode() {
        return (String) getAttributeInternal(ACCOUNTRECONCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for ACCOUNT_RECON_CODE using the alias name AccountReconCode.
     * @param value value to set the ACCOUNT_RECON_CODE
     */
    public void setAccountReconCode(String value) {
        setAttributeInternal(ACCOUNTRECONCODE, value);
    }

    /**
     * Gets the attribute value for ACCOUNT_STMT_CODE using the alias name AccountStmtCode.
     * @return the ACCOUNT_STMT_CODE
     */
    public String getAccountStmtCode() {
        return (String) getAttributeInternal(ACCOUNTSTMTCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for ACCOUNT_STMT_CODE using the alias name AccountStmtCode.
     * @param value value to set the ACCOUNT_STMT_CODE
     */
    public void setAccountStmtCode(String value) {
        setAttributeInternal(ACCOUNTSTMTCODE, value);
    }

    /**
     * Gets the attribute value for INTEREST_CODE using the alias name InterestCode.
     * @return the INTEREST_CODE
     */
    public String getInterestCode() {
        return (String) getAttributeInternal(INTERESTCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for INTEREST_CODE using the alias name InterestCode.
     * @param value value to set the INTEREST_CODE
     */
    public void setInterestCode(String value) {
        setAttributeInternal(INTERESTCODE, value);
    }

    /**
     * Gets the attribute value for ACCOUNT_GROUP using the alias name AccountGroup.
     * @return the ACCOUNT_GROUP
     */
    public String getAccountGroup() {
        return (String) getAttributeInternal(ACCOUNTGROUP);
    }

    /**
     * Sets <code>value</code> as attribute value for ACCOUNT_GROUP using the alias name AccountGroup.
     * @param value value to set the ACCOUNT_GROUP
     */
    public void setAccountGroup(String value) {
        setAttributeInternal(ACCOUNTGROUP, value);
    }

    /**
     * Gets the attribute value for BLOCK_CODE using the alias name BlockCode.
     * @return the BLOCK_CODE
     */
    public String getBlockCode() {
        return (String) getAttributeInternal(BLOCKCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for BLOCK_CODE using the alias name BlockCode.
     * @param value value to set the BLOCK_CODE
     */
    public void setBlockCode(String value) {
        setAttributeInternal(BLOCKCODE, value);
    }

    /**
     * Gets the attribute value for ACCOUNT_DESC using the alias name AccountDesc.
     * @return the ACCOUNT_DESC
     */
    public String getAccountDesc() {
        return (String) getAttributeInternal(ACCOUNTDESC);
    }

    /**
     * Sets <code>value</code> as attribute value for ACCOUNT_DESC using the alias name AccountDesc.
     * @param value value to set the ACCOUNT_DESC
     */
    public void setAccountDesc(String value) {
        setAttributeInternal(ACCOUNTDESC, value);
    }

    /**
     * Gets the attribute value for CREDIT_BLOCK_CODE using the alias name CreditBlockCode.
     * @return the CREDIT_BLOCK_CODE
     */
    public String getCreditBlockCode() {
        return (String) getAttributeInternal(CREDITBLOCKCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for CREDIT_BLOCK_CODE using the alias name CreditBlockCode.
     * @param value value to set the CREDIT_BLOCK_CODE
     */
    public void setCreditBlockCode(String value) {
        setAttributeInternal(CREDITBLOCKCODE, value);
    }

    /**
     * Gets the attribute value for VERSION_NUMBER using the alias name VersionNumber.
     * @return the VERSION_NUMBER
     */
    public BigDecimal getVersionNumber() {
        return (BigDecimal) getAttributeInternal(VERSIONNUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for VERSION_NUMBER using the alias name VersionNumber.
     * @param value value to set the VERSION_NUMBER
     */
    public void setVersionNumber(BigDecimal value) {
        setAttributeInternal(VERSIONNUMBER, value);
    }

    /**
     * Gets the attribute value for DUNNING_LEVEL_CODE using the alias name DunningLevelCode.
     * @return the DUNNING_LEVEL_CODE
     */
    public String getDunningLevelCode() {
        return (String) getAttributeInternal(DUNNINGLEVELCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for DUNNING_LEVEL_CODE using the alias name DunningLevelCode.
     * @param value value to set the DUNNING_LEVEL_CODE
     */
    public void setDunningLevelCode(String value) {
        setAttributeInternal(DUNNINGLEVELCODE, value);
    }

    /**
     * Gets the attribute value for CREDIT_BLOCK_DATE using the alias name CreditBlockDate.
     * @return the CREDIT_BLOCK_DATE
     */
    public Date getCreditBlockDate() {
        return (Date) getAttributeInternal(CREDITBLOCKDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for CREDIT_BLOCK_DATE using the alias name CreditBlockDate.
     * @param value value to set the CREDIT_BLOCK_DATE
     */
    public void setCreditBlockDate(Date value) {
        setAttributeInternal(CREDITBLOCKDATE, value);
    }

    /**
     * Gets the attribute value for CREDIT_BLOCK_TIME using the alias name CreditBlockTime.
     * @return the CREDIT_BLOCK_TIME
     */
    public Timestamp getCreditBlockTime() {
        return (Timestamp) getAttributeInternal(CREDITBLOCKTIME);
    }

    /**
     * Sets <code>value</code> as attribute value for CREDIT_BLOCK_TIME using the alias name CreditBlockTime.
     * @param value value to set the CREDIT_BLOCK_TIME
     */
    public void setCreditBlockTime(Timestamp value) {
        setAttributeInternal(CREDITBLOCKTIME, value);
    }

    /**
     * Gets the attribute value for ACCOUNT_BLOCK_DATE using the alias name AccountBlockDate.
     * @return the ACCOUNT_BLOCK_DATE
     */
    public Date getAccountBlockDate() {
        return (Date) getAttributeInternal(ACCOUNTBLOCKDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for ACCOUNT_BLOCK_DATE using the alias name AccountBlockDate.
     * @param value value to set the ACCOUNT_BLOCK_DATE
     */
    public void setAccountBlockDate(Date value) {
        setAttributeInternal(ACCOUNTBLOCKDATE, value);
    }

    /**
     * Gets the attribute value for ACCOUNT_BLOCK_TIME using the alias name AccountBlockTime.
     * @return the ACCOUNT_BLOCK_TIME
     */
    public Timestamp getAccountBlockTime() {
        return (Timestamp) getAttributeInternal(ACCOUNTBLOCKTIME);
    }

    /**
     * Sets <code>value</code> as attribute value for ACCOUNT_BLOCK_TIME using the alias name AccountBlockTime.
     * @param value value to set the ACCOUNT_BLOCK_TIME
     */
    public void setAccountBlockTime(Timestamp value) {
        setAttributeInternal(ACCOUNTBLOCKTIME, value);
    }

    /**
     * Gets the attribute value for DUNNING_PROC_ID using the alias name DunningProcId.
     * @return the DUNNING_PROC_ID
     */
    public String getDunningProcId() {
        return (String) getAttributeInternal(DUNNINGPROCID);
    }

    /**
     * Sets <code>value</code> as attribute value for DUNNING_PROC_ID using the alias name DunningProcId.
     * @param value value to set the DUNNING_PROC_ID
     */
    public void setDunningProcId(String value) {
        setAttributeInternal(DUNNINGPROCID, value);
    }

    /**
     * Gets the attribute value for GIRO_NUMBER using the alias name GiroNumber.
     * @return the GIRO_NUMBER
     */
    public String getGiroNumber() {
        return (String) getAttributeInternal(GIRONUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for GIRO_NUMBER using the alias name GiroNumber.
     * @param value value to set the GIRO_NUMBER
     */
    public void setGiroNumber(String value) {
        setAttributeInternal(GIRONUMBER, value);
    }

    /**
     * Gets the attribute value for MODIFIED_BY using the alias name ModifiedBy.
     * @return the MODIFIED_BY
     */
    public String getModifiedBy() {
        return (String) getAttributeInternal(MODIFIEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for MODIFIED_BY using the alias name ModifiedBy.
     * @param value value to set the MODIFIED_BY
     */
    public void setModifiedBy(String value) {
        setAttributeInternal(MODIFIEDBY, value);
    }

    /**
     * Gets the attribute value for MODIFIED_DATE using the alias name ModifiedDate.
     * @return the MODIFIED_DATE
     */
    public Timestamp getModifiedDate() {
        return (Timestamp) getAttributeInternal(MODIFIEDDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for MODIFIED_DATE using the alias name ModifiedDate.
     * @param value value to set the MODIFIED_DATE
     */
    public void setModifiedDate(Timestamp value) {
        setAttributeInternal(MODIFIEDDATE, value);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index,
                                           AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value,
                                         AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}

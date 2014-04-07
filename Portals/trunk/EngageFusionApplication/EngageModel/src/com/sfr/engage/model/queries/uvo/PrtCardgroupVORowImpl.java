package com.sfr.engage.model.queries.uvo;

import java.math.BigDecimal;

import java.sql.Date;
import java.sql.Timestamp;

import oracle.jbo.RowIterator;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Apr 07 17:39:35 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtCardgroupVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CountryCode {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getCountryCode();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setCountryCode((String)value);
            }
        }
        ,
        PartnerId {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getPartnerId();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setPartnerId((String)value);
            }
        }
        ,
        AccountId {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getAccountId();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setAccountId((String)value);
            }
        }
        ,
        CardgroupMainType {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getCardgroupMainType();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setCardgroupMainType((String)value);
            }
        }
        ,
        CardgroupSubType {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getCardgroupSubType();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setCardgroupSubType((String)value);
            }
        }
        ,
        CardgroupSeq {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getCardgroupSeq();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setCardgroupSeq((String)value);
            }
        }
        ,
        EstbGroup {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getEstbGroup();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setEstbGroup((String)value);
            }
        }
        ,
        BusiRelationGroup {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getBusiRelationGroup();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setBusiRelationGroup((String)value);
            }
        }
        ,
        CreatedDate {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getCreatedDate();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setCreatedDate((Date)value);
            }
        }
        ,
        CreatedBy {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setCreatedBy((String)value);
            }
        }
        ,
        ChangeDate {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getChangeDate();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setChangeDate((Date)value);
            }
        }
        ,
        ChangeBy {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getChangeBy();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setChangeBy((String)value);
            }
        }
        ,
        PayProof {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getPayProof();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setPayProof((String)value);
            }
        }
        ,
        DeleteCode {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getDeleteCode();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setDeleteCode((String)value);
            }
        }
        ,
        MarketingCode {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getMarketingCode();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setMarketingCode((String)value);
            }
        }
        ,
        ArchiveRef {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getArchiveRef();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setArchiveRef((String)value);
            }
        }
        ,
        NotAvgFrom {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getNotAvgFrom();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setNotAvgFrom((String)value);
            }
        }
        ,
        NotAvgTo {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getNotAvgTo();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setNotAvgTo((String)value);
            }
        }
        ,
        VersionNumber {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getVersionNumber();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setVersionNumber((BigDecimal)value);
            }
        }
        ,
        CardType {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getCardType();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setCardType((String)value);
            }
        }
        ,
        PayDocLevel {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getPayDocLevel();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setPayDocLevel((String)value);
            }
        }
        ,
        BusiRelationNace {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getBusiRelationNace();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setBusiRelationNace((String)value);
            }
        }
        ,
        DebitBankAccount {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getDebitBankAccount();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setDebitBankAccount((String)value);
            }
        }
        ,
        DebitRefNumber {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getDebitRefNumber();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setDebitRefNumber((String)value);
            }
        }
        ,
        BusiRelationDesc {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getBusiRelationDesc();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setBusiRelationDesc((String)value);
            }
        }
        ,
        PurchaseLimitCode {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getPurchaseLimitCode();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setPurchaseLimitCode((String)value);
            }
        }
        ,
        BlockCode {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getBlockCode();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setBlockCode((String)value);
            }
        }
        ,
        BlockDate {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getBlockDate();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setBlockDate((Date)value);
            }
        }
        ,
        BlockTime {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getBlockTime();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setBlockTime((Timestamp)value);
            }
        }
        ,
        ModifiedBy {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getModifiedBy();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setModifiedBy((String)value);
            }
        }
        ,
        ModifiedDate {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getModifiedDate();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setModifiedDate((Timestamp)value);
            }
        }
        ,
        PrtCardVO {
            public Object get(PrtCardgroupVORowImpl obj) {
                return obj.getPrtCardVO();
            }

            public void put(PrtCardgroupVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtCardgroupVORowImpl object);

        public abstract void put(PrtCardgroupVORowImpl object, Object value);

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
    public static final int PARTNERID = AttributesEnum.PartnerId.index();
    public static final int ACCOUNTID = AttributesEnum.AccountId.index();
    public static final int CARDGROUPMAINTYPE = AttributesEnum.CardgroupMainType.index();
    public static final int CARDGROUPSUBTYPE = AttributesEnum.CardgroupSubType.index();
    public static final int CARDGROUPSEQ = AttributesEnum.CardgroupSeq.index();
    public static final int ESTBGROUP = AttributesEnum.EstbGroup.index();
    public static final int BUSIRELATIONGROUP = AttributesEnum.BusiRelationGroup.index();
    public static final int CREATEDDATE = AttributesEnum.CreatedDate.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int CHANGEDATE = AttributesEnum.ChangeDate.index();
    public static final int CHANGEBY = AttributesEnum.ChangeBy.index();
    public static final int PAYPROOF = AttributesEnum.PayProof.index();
    public static final int DELETECODE = AttributesEnum.DeleteCode.index();
    public static final int MARKETINGCODE = AttributesEnum.MarketingCode.index();
    public static final int ARCHIVEREF = AttributesEnum.ArchiveRef.index();
    public static final int NOTAVGFROM = AttributesEnum.NotAvgFrom.index();
    public static final int NOTAVGTO = AttributesEnum.NotAvgTo.index();
    public static final int VERSIONNUMBER = AttributesEnum.VersionNumber.index();
    public static final int CARDTYPE = AttributesEnum.CardType.index();
    public static final int PAYDOCLEVEL = AttributesEnum.PayDocLevel.index();
    public static final int BUSIRELATIONNACE = AttributesEnum.BusiRelationNace.index();
    public static final int DEBITBANKACCOUNT = AttributesEnum.DebitBankAccount.index();
    public static final int DEBITREFNUMBER = AttributesEnum.DebitRefNumber.index();
    public static final int BUSIRELATIONDESC = AttributesEnum.BusiRelationDesc.index();
    public static final int PURCHASELIMITCODE = AttributesEnum.PurchaseLimitCode.index();
    public static final int BLOCKCODE = AttributesEnum.BlockCode.index();
    public static final int BLOCKDATE = AttributesEnum.BlockDate.index();
    public static final int BLOCKTIME = AttributesEnum.BlockTime.index();
    public static final int MODIFIEDBY = AttributesEnum.ModifiedBy.index();
    public static final int MODIFIEDDATE = AttributesEnum.ModifiedDate.index();
    public static final int PRTCARDVO = AttributesEnum.PrtCardVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtCardgroupVORowImpl() {
    }

    /**
     * Gets PrtCardgroupEO entity object.
     * @return the PrtCardgroupEO
     */
    public EntityImpl getPrtCardgroupEO() {
        return (EntityImpl)getEntity(0);
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
     * Gets the attribute value for CARDGROUP_MAIN_TYPE using the alias name CardgroupMainType.
     * @return the CARDGROUP_MAIN_TYPE
     */
    public String getCardgroupMainType() {
        return (String) getAttributeInternal(CARDGROUPMAINTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for CARDGROUP_MAIN_TYPE using the alias name CardgroupMainType.
     * @param value value to set the CARDGROUP_MAIN_TYPE
     */
    public void setCardgroupMainType(String value) {
        setAttributeInternal(CARDGROUPMAINTYPE, value);
    }

    /**
     * Gets the attribute value for CARDGROUP_SUB_TYPE using the alias name CardgroupSubType.
     * @return the CARDGROUP_SUB_TYPE
     */
    public String getCardgroupSubType() {
        return (String) getAttributeInternal(CARDGROUPSUBTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for CARDGROUP_SUB_TYPE using the alias name CardgroupSubType.
     * @param value value to set the CARDGROUP_SUB_TYPE
     */
    public void setCardgroupSubType(String value) {
        setAttributeInternal(CARDGROUPSUBTYPE, value);
    }

    /**
     * Gets the attribute value for CARDGROUP_SEQ using the alias name CardgroupSeq.
     * @return the CARDGROUP_SEQ
     */
    public String getCardgroupSeq() {
        return (String) getAttributeInternal(CARDGROUPSEQ);
    }

    /**
     * Sets <code>value</code> as attribute value for CARDGROUP_SEQ using the alias name CardgroupSeq.
     * @param value value to set the CARDGROUP_SEQ
     */
    public void setCardgroupSeq(String value) {
        setAttributeInternal(CARDGROUPSEQ, value);
    }

    /**
     * Gets the attribute value for ESTB_GROUP using the alias name EstbGroup.
     * @return the ESTB_GROUP
     */
    public String getEstbGroup() {
        return (String) getAttributeInternal(ESTBGROUP);
    }

    /**
     * Sets <code>value</code> as attribute value for ESTB_GROUP using the alias name EstbGroup.
     * @param value value to set the ESTB_GROUP
     */
    public void setEstbGroup(String value) {
        setAttributeInternal(ESTBGROUP, value);
    }

    /**
     * Gets the attribute value for BUSI_RELATION_GROUP using the alias name BusiRelationGroup.
     * @return the BUSI_RELATION_GROUP
     */
    public String getBusiRelationGroup() {
        return (String) getAttributeInternal(BUSIRELATIONGROUP);
    }

    /**
     * Sets <code>value</code> as attribute value for BUSI_RELATION_GROUP using the alias name BusiRelationGroup.
     * @param value value to set the BUSI_RELATION_GROUP
     */
    public void setBusiRelationGroup(String value) {
        setAttributeInternal(BUSIRELATIONGROUP, value);
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
     * Gets the attribute value for PAY_PROOF using the alias name PayProof.
     * @return the PAY_PROOF
     */
    public String getPayProof() {
        return (String) getAttributeInternal(PAYPROOF);
    }

    /**
     * Sets <code>value</code> as attribute value for PAY_PROOF using the alias name PayProof.
     * @param value value to set the PAY_PROOF
     */
    public void setPayProof(String value) {
        setAttributeInternal(PAYPROOF, value);
    }

    /**
     * Gets the attribute value for DELETE_CODE using the alias name DeleteCode.
     * @return the DELETE_CODE
     */
    public String getDeleteCode() {
        return (String) getAttributeInternal(DELETECODE);
    }

    /**
     * Sets <code>value</code> as attribute value for DELETE_CODE using the alias name DeleteCode.
     * @param value value to set the DELETE_CODE
     */
    public void setDeleteCode(String value) {
        setAttributeInternal(DELETECODE, value);
    }

    /**
     * Gets the attribute value for MARKETING_CODE using the alias name MarketingCode.
     * @return the MARKETING_CODE
     */
    public String getMarketingCode() {
        return (String) getAttributeInternal(MARKETINGCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for MARKETING_CODE using the alias name MarketingCode.
     * @param value value to set the MARKETING_CODE
     */
    public void setMarketingCode(String value) {
        setAttributeInternal(MARKETINGCODE, value);
    }

    /**
     * Gets the attribute value for ARCHIVE_REF using the alias name ArchiveRef.
     * @return the ARCHIVE_REF
     */
    public String getArchiveRef() {
        return (String) getAttributeInternal(ARCHIVEREF);
    }

    /**
     * Sets <code>value</code> as attribute value for ARCHIVE_REF using the alias name ArchiveRef.
     * @param value value to set the ARCHIVE_REF
     */
    public void setArchiveRef(String value) {
        setAttributeInternal(ARCHIVEREF, value);
    }

    /**
     * Gets the attribute value for NOT_AVG_FROM using the alias name NotAvgFrom.
     * @return the NOT_AVG_FROM
     */
    public String getNotAvgFrom() {
        return (String) getAttributeInternal(NOTAVGFROM);
    }

    /**
     * Sets <code>value</code> as attribute value for NOT_AVG_FROM using the alias name NotAvgFrom.
     * @param value value to set the NOT_AVG_FROM
     */
    public void setNotAvgFrom(String value) {
        setAttributeInternal(NOTAVGFROM, value);
    }

    /**
     * Gets the attribute value for NOT_AVG_TO using the alias name NotAvgTo.
     * @return the NOT_AVG_TO
     */
    public String getNotAvgTo() {
        return (String) getAttributeInternal(NOTAVGTO);
    }

    /**
     * Sets <code>value</code> as attribute value for NOT_AVG_TO using the alias name NotAvgTo.
     * @param value value to set the NOT_AVG_TO
     */
    public void setNotAvgTo(String value) {
        setAttributeInternal(NOTAVGTO, value);
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
     * Gets the attribute value for CARD_TYPE using the alias name CardType.
     * @return the CARD_TYPE
     */
    public String getCardType() {
        return (String) getAttributeInternal(CARDTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for CARD_TYPE using the alias name CardType.
     * @param value value to set the CARD_TYPE
     */
    public void setCardType(String value) {
        setAttributeInternal(CARDTYPE, value);
    }

    /**
     * Gets the attribute value for PAY_DOC_LEVEL using the alias name PayDocLevel.
     * @return the PAY_DOC_LEVEL
     */
    public String getPayDocLevel() {
        return (String) getAttributeInternal(PAYDOCLEVEL);
    }

    /**
     * Sets <code>value</code> as attribute value for PAY_DOC_LEVEL using the alias name PayDocLevel.
     * @param value value to set the PAY_DOC_LEVEL
     */
    public void setPayDocLevel(String value) {
        setAttributeInternal(PAYDOCLEVEL, value);
    }

    /**
     * Gets the attribute value for BUSI_RELATION_NACE using the alias name BusiRelationNace.
     * @return the BUSI_RELATION_NACE
     */
    public String getBusiRelationNace() {
        return (String) getAttributeInternal(BUSIRELATIONNACE);
    }

    /**
     * Sets <code>value</code> as attribute value for BUSI_RELATION_NACE using the alias name BusiRelationNace.
     * @param value value to set the BUSI_RELATION_NACE
     */
    public void setBusiRelationNace(String value) {
        setAttributeInternal(BUSIRELATIONNACE, value);
    }

    /**
     * Gets the attribute value for DEBIT_BANK_ACCOUNT using the alias name DebitBankAccount.
     * @return the DEBIT_BANK_ACCOUNT
     */
    public String getDebitBankAccount() {
        return (String) getAttributeInternal(DEBITBANKACCOUNT);
    }

    /**
     * Sets <code>value</code> as attribute value for DEBIT_BANK_ACCOUNT using the alias name DebitBankAccount.
     * @param value value to set the DEBIT_BANK_ACCOUNT
     */
    public void setDebitBankAccount(String value) {
        setAttributeInternal(DEBITBANKACCOUNT, value);
    }

    /**
     * Gets the attribute value for DEBIT_REF_NUMBER using the alias name DebitRefNumber.
     * @return the DEBIT_REF_NUMBER
     */
    public String getDebitRefNumber() {
        return (String) getAttributeInternal(DEBITREFNUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for DEBIT_REF_NUMBER using the alias name DebitRefNumber.
     * @param value value to set the DEBIT_REF_NUMBER
     */
    public void setDebitRefNumber(String value) {
        setAttributeInternal(DEBITREFNUMBER, value);
    }

    /**
     * Gets the attribute value for BUSI_RELATION_DESC using the alias name BusiRelationDesc.
     * @return the BUSI_RELATION_DESC
     */
    public String getBusiRelationDesc() {
        return (String) getAttributeInternal(BUSIRELATIONDESC);
    }

    /**
     * Sets <code>value</code> as attribute value for BUSI_RELATION_DESC using the alias name BusiRelationDesc.
     * @param value value to set the BUSI_RELATION_DESC
     */
    public void setBusiRelationDesc(String value) {
        setAttributeInternal(BUSIRELATIONDESC, value);
    }

    /**
     * Gets the attribute value for PURCHASE_LIMIT_CODE using the alias name PurchaseLimitCode.
     * @return the PURCHASE_LIMIT_CODE
     */
    public String getPurchaseLimitCode() {
        return (String) getAttributeInternal(PURCHASELIMITCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for PURCHASE_LIMIT_CODE using the alias name PurchaseLimitCode.
     * @param value value to set the PURCHASE_LIMIT_CODE
     */
    public void setPurchaseLimitCode(String value) {
        setAttributeInternal(PURCHASELIMITCODE, value);
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
     * Gets the attribute value for BLOCK_DATE using the alias name BlockDate.
     * @return the BLOCK_DATE
     */
    public Date getBlockDate() {
        return (Date) getAttributeInternal(BLOCKDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for BLOCK_DATE using the alias name BlockDate.
     * @param value value to set the BLOCK_DATE
     */
    public void setBlockDate(Date value) {
        setAttributeInternal(BLOCKDATE, value);
    }

    /**
     * Gets the attribute value for BLOCK_TIME using the alias name BlockTime.
     * @return the BLOCK_TIME
     */
    public Timestamp getBlockTime() {
        return (Timestamp) getAttributeInternal(BLOCKTIME);
    }

    /**
     * Sets <code>value</code> as attribute value for BLOCK_TIME using the alias name BlockTime.
     * @param value value to set the BLOCK_TIME
     */
    public void setBlockTime(Timestamp value) {
        setAttributeInternal(BLOCKTIME, value);
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
     * Gets the associated <code>RowIterator</code> using master-detail link PrtCardVO.
     */
    public RowIterator getPrtCardVO() {
        return (RowIterator)getAttributeInternal(PRTCARDVO);
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

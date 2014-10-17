package com.sfr.engage.model.entities;

import oracle.jbo.Key;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Oct 10 18:07:10 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtNotificationEOImpl extends EntityImpl {
    private static EntityDefImpl mDefinitionObject;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CountryCode {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getCountryCode();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setCountryCode((String)value);
            }
        }
        ,
        NotiId {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getNotiId();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setNotiId((DBSequence)value);
            }
        }
        ,
        RuleId {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getRuleId();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setRuleId((String)value);
            }
        }
        ,
        RuleIsenabled {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getRuleIsenabled();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setRuleIsenabled((String)value);
            }
        }
        ,
        SubId {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getSubId();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setSubId((String)value);
            }
        }
        ,
        UserId {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getUserId();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setUserId((String)value);
            }
        }
        ,
        Partner {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getPartner();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setPartner((String)value);
            }
        }
        ,
        AccountId {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getAccountId();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setAccountId((String)value);
            }
        }
        ,
        CardgroupMain {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getCardgroupMain();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setCardgroupMain((String)value);
            }
        }
        ,
        CardgroupSub {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getCardgroupSub();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setCardgroupSub((String)value);
            }
        }
        ,
        CardgroupSeq {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getCardgroupSeq();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setCardgroupSeq((String)value);
            }
        }
        ,
        CardPk {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getCardPk();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setCardPk((String)value);
            }
        }
        ,
        EmbossCardNum {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getEmbossCardNum();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setEmbossCardNum((String)value);
            }
        }
        ,
        TransactionTime {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getTransactionTime();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setTransactionTime((Timestamp)value);
            }
        }
        ,
        NotiCreated {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getNotiCreated();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setNotiCreated((Date)value);
            }
        }
        ,
        NotiCategory {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getNotiCategory();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setNotiCategory((String)value);
            }
        }
        ,
        NotiSubcategory {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getNotiSubcategory();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setNotiSubcategory((String)value);
            }
        }
        ,
        ShowFlag {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getShowFlag();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setShowFlag((String)value);
            }
        }
        ,
        NotiDescription {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getNotiDescription();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setNotiDescription((String)value);
            }
        }
        ,
        ModifiedBy {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getModifiedBy();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setModifiedBy((String)value);
            }
        }
        ,
        ModifiedDate {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getModifiedDate();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        NotiDescriptionLocalised {
            public Object get(PrtNotificationEOImpl obj) {
                return obj.getNotiDescriptionLocalised();
            }

            public void put(PrtNotificationEOImpl obj, Object value) {
                obj.setNotiDescriptionLocalised((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtNotificationEOImpl object);

        public abstract void put(PrtNotificationEOImpl object, Object value);

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
    public static final int NOTIID = AttributesEnum.NotiId.index();
    public static final int RULEID = AttributesEnum.RuleId.index();
    public static final int RULEISENABLED = AttributesEnum.RuleIsenabled.index();
    public static final int SUBID = AttributesEnum.SubId.index();
    public static final int USERID = AttributesEnum.UserId.index();
    public static final int PARTNER = AttributesEnum.Partner.index();
    public static final int ACCOUNTID = AttributesEnum.AccountId.index();
    public static final int CARDGROUPMAIN = AttributesEnum.CardgroupMain.index();
    public static final int CARDGROUPSUB = AttributesEnum.CardgroupSub.index();
    public static final int CARDGROUPSEQ = AttributesEnum.CardgroupSeq.index();
    public static final int CARDPK = AttributesEnum.CardPk.index();
    public static final int EMBOSSCARDNUM = AttributesEnum.EmbossCardNum.index();
    public static final int TRANSACTIONTIME = AttributesEnum.TransactionTime.index();
    public static final int NOTICREATED = AttributesEnum.NotiCreated.index();
    public static final int NOTICATEGORY = AttributesEnum.NotiCategory.index();
    public static final int NOTISUBCATEGORY = AttributesEnum.NotiSubcategory.index();
    public static final int SHOWFLAG = AttributesEnum.ShowFlag.index();
    public static final int NOTIDESCRIPTION = AttributesEnum.NotiDescription.index();
    public static final int MODIFIEDBY = AttributesEnum.ModifiedBy.index();
    public static final int MODIFIEDDATE = AttributesEnum.ModifiedDate.index();
    public static final int NOTIDESCRIPTIONLOCALISED = AttributesEnum.NotiDescriptionLocalised.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtNotificationEOImpl() {
    }


    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = EntityDefImpl.findDefObject("com.sfr.engage.model.entities.PrtNotificationEO");
        }
        return mDefinitionObject;
    }

    /**
     * Gets the attribute value for CountryCode, using the alias name CountryCode.
     * @return the CountryCode
     */
    public String getCountryCode() {
        return (String)getAttributeInternal(COUNTRYCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for CountryCode.
     * @param value value to set the CountryCode
     */
    public void setCountryCode(String value) {
        setAttributeInternal(COUNTRYCODE, value);
    }

    /**
     * Gets the attribute value for NotiId, using the alias name NotiId.
     * @return the NotiId
     */
    public DBSequence getNotiId() {
        return (DBSequence)getAttributeInternal(NOTIID);
    }

    /**
     * Sets <code>value</code> as the attribute value for NotiId.
     * @param value value to set the NotiId
     */
    public void setNotiId(DBSequence value) {
        setAttributeInternal(NOTIID, value);
    }

    /**
     * Gets the attribute value for RuleId, using the alias name RuleId.
     * @return the RuleId
     */
    public String getRuleId() {
        return (String)getAttributeInternal(RULEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for RuleId.
     * @param value value to set the RuleId
     */
    public void setRuleId(String value) {
        setAttributeInternal(RULEID, value);
    }

    /**
     * Gets the attribute value for RuleIsenabled, using the alias name RuleIsenabled.
     * @return the RuleIsenabled
     */
    public String getRuleIsenabled() {
        return (String)getAttributeInternal(RULEISENABLED);
    }

    /**
     * Sets <code>value</code> as the attribute value for RuleIsenabled.
     * @param value value to set the RuleIsenabled
     */
    public void setRuleIsenabled(String value) {
        setAttributeInternal(RULEISENABLED, value);
    }

    /**
     * Gets the attribute value for SubId, using the alias name SubId.
     * @return the SubId
     */
    public String getSubId() {
        return (String)getAttributeInternal(SUBID);
    }

    /**
     * Sets <code>value</code> as the attribute value for SubId.
     * @param value value to set the SubId
     */
    public void setSubId(String value) {
        setAttributeInternal(SUBID, value);
    }

    /**
     * Gets the attribute value for UserId, using the alias name UserId.
     * @return the UserId
     */
    public String getUserId() {
        return (String)getAttributeInternal(USERID);
    }

    /**
     * Sets <code>value</code> as the attribute value for UserId.
     * @param value value to set the UserId
     */
    public void setUserId(String value) {
        setAttributeInternal(USERID, value);
    }

    /**
     * Gets the attribute value for Partner, using the alias name Partner.
     * @return the Partner
     */
    public String getPartner() {
        return (String)getAttributeInternal(PARTNER);
    }

    /**
     * Sets <code>value</code> as the attribute value for Partner.
     * @param value value to set the Partner
     */
    public void setPartner(String value) {
        setAttributeInternal(PARTNER, value);
    }

    /**
     * Gets the attribute value for AccountId, using the alias name AccountId.
     * @return the AccountId
     */
    public String getAccountId() {
        return (String)getAttributeInternal(ACCOUNTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for AccountId.
     * @param value value to set the AccountId
     */
    public void setAccountId(String value) {
        setAttributeInternal(ACCOUNTID, value);
    }

    /**
     * Gets the attribute value for CardgroupMain, using the alias name CardgroupMain.
     * @return the CardgroupMain
     */
    public String getCardgroupMain() {
        return (String)getAttributeInternal(CARDGROUPMAIN);
    }

    /**
     * Sets <code>value</code> as the attribute value for CardgroupMain.
     * @param value value to set the CardgroupMain
     */
    public void setCardgroupMain(String value) {
        setAttributeInternal(CARDGROUPMAIN, value);
    }

    /**
     * Gets the attribute value for CardgroupSub, using the alias name CardgroupSub.
     * @return the CardgroupSub
     */
    public String getCardgroupSub() {
        return (String)getAttributeInternal(CARDGROUPSUB);
    }

    /**
     * Sets <code>value</code> as the attribute value for CardgroupSub.
     * @param value value to set the CardgroupSub
     */
    public void setCardgroupSub(String value) {
        setAttributeInternal(CARDGROUPSUB, value);
    }

    /**
     * Gets the attribute value for CardgroupSeq, using the alias name CardgroupSeq.
     * @return the CardgroupSeq
     */
    public String getCardgroupSeq() {
        return (String)getAttributeInternal(CARDGROUPSEQ);
    }

    /**
     * Sets <code>value</code> as the attribute value for CardgroupSeq.
     * @param value value to set the CardgroupSeq
     */
    public void setCardgroupSeq(String value) {
        setAttributeInternal(CARDGROUPSEQ, value);
    }

    /**
     * Gets the attribute value for CardPk, using the alias name CardPk.
     * @return the CardPk
     */
    public String getCardPk() {
        return (String)getAttributeInternal(CARDPK);
    }

    /**
     * Sets <code>value</code> as the attribute value for CardPk.
     * @param value value to set the CardPk
     */
    public void setCardPk(String value) {
        setAttributeInternal(CARDPK, value);
    }

    /**
     * Gets the attribute value for EmbossCardNum, using the alias name EmbossCardNum.
     * @return the EmbossCardNum
     */
    public String getEmbossCardNum() {
        return (String)getAttributeInternal(EMBOSSCARDNUM);
    }

    /**
     * Sets <code>value</code> as the attribute value for EmbossCardNum.
     * @param value value to set the EmbossCardNum
     */
    public void setEmbossCardNum(String value) {
        setAttributeInternal(EMBOSSCARDNUM, value);
    }

    /**
     * Gets the attribute value for TransactionTime, using the alias name TransactionTime.
     * @return the TransactionTime
     */
    public Timestamp getTransactionTime() {
        return (Timestamp)getAttributeInternal(TRANSACTIONTIME);
    }

    /**
     * Sets <code>value</code> as the attribute value for TransactionTime.
     * @param value value to set the TransactionTime
     */
    public void setTransactionTime(Timestamp value) {
        setAttributeInternal(TRANSACTIONTIME, value);
    }

    /**
     * Gets the attribute value for NotiCreated, using the alias name NotiCreated.
     * @return the NotiCreated
     */
    public Date getNotiCreated() {
        return (Date)getAttributeInternal(NOTICREATED);
    }

    /**
     * Sets <code>value</code> as the attribute value for NotiCreated.
     * @param value value to set the NotiCreated
     */
    public void setNotiCreated(Date value) {
        setAttributeInternal(NOTICREATED, value);
    }

    /**
     * Gets the attribute value for NotiCategory, using the alias name NotiCategory.
     * @return the NotiCategory
     */
    public String getNotiCategory() {
        return (String)getAttributeInternal(NOTICATEGORY);
    }

    /**
     * Sets <code>value</code> as the attribute value for NotiCategory.
     * @param value value to set the NotiCategory
     */
    public void setNotiCategory(String value) {
        setAttributeInternal(NOTICATEGORY, value);
    }

    /**
     * Gets the attribute value for NotiSubcategory, using the alias name NotiSubcategory.
     * @return the NotiSubcategory
     */
    public String getNotiSubcategory() {
        return (String)getAttributeInternal(NOTISUBCATEGORY);
    }

    /**
     * Sets <code>value</code> as the attribute value for NotiSubcategory.
     * @param value value to set the NotiSubcategory
     */
    public void setNotiSubcategory(String value) {
        setAttributeInternal(NOTISUBCATEGORY, value);
    }

    /**
     * Gets the attribute value for ShowFlag, using the alias name ShowFlag.
     * @return the ShowFlag
     */
    public String getShowFlag() {
        return (String)getAttributeInternal(SHOWFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for ShowFlag.
     * @param value value to set the ShowFlag
     */
    public void setShowFlag(String value) {
        setAttributeInternal(SHOWFLAG, value);
    }

    /**
     * Gets the attribute value for NotiDescription, using the alias name NotiDescription.
     * @return the NotiDescription
     */
    public String getNotiDescription() {
        return (String)getAttributeInternal(NOTIDESCRIPTION);
    }

    /**
     * Sets <code>value</code> as the attribute value for NotiDescription.
     * @param value value to set the NotiDescription
     */
    public void setNotiDescription(String value) {
        setAttributeInternal(NOTIDESCRIPTION, value);
    }

    /**
     * Gets the attribute value for ModifiedBy, using the alias name ModifiedBy.
     * @return the ModifiedBy
     */
    public String getModifiedBy() {
        return (String)getAttributeInternal(MODIFIEDBY);
    }

    /**
     * Sets <code>value</code> as the attribute value for ModifiedBy.
     * @param value value to set the ModifiedBy
     */
    public void setModifiedBy(String value) {
        setAttributeInternal(MODIFIEDBY, value);
    }

    /**
     * Gets the attribute value for ModifiedDate, using the alias name ModifiedDate.
     * @return the ModifiedDate
     */
    public Timestamp getModifiedDate() {
        return (Timestamp)getAttributeInternal(MODIFIEDDATE);
    }


    /**
     * Gets the attribute value for NotiDescriptionLocalised, using the alias name NotiDescriptionLocalised.
     * @return the NotiDescriptionLocalised
     */
    public String getNotiDescriptionLocalised() {
        return (String)getAttributeInternal(NOTIDESCRIPTIONLOCALISED);
    }

    /**
     * Sets <code>value</code> as the attribute value for NotiDescriptionLocalised.
     * @param value value to set the NotiDescriptionLocalised
     */
    public void setNotiDescriptionLocalised(String value) {
        setAttributeInternal(NOTIDESCRIPTIONLOCALISED, value);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception {
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
    protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }

    /**
     * @param notiId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(DBSequence notiId) {
        return new Key(new Object[]{notiId});
    }


}

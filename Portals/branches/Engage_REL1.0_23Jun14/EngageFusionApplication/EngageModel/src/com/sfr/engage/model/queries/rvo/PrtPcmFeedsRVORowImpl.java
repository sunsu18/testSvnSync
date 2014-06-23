package com.sfr.engage.model.queries.rvo;

import com.sfr.engage.model.entities.PrtPcmFeedsEOImpl;

import java.math.BigDecimal;

import java.sql.Date;
import java.sql.Timestamp;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Mar 25 16:43:47 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtPcmFeedsRVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        PrtPcmFeedsPk {
            public Object get(PrtPcmFeedsRVORowImpl obj) {
                return obj.getPrtPcmFeedsPk();
            }

            public void put(PrtPcmFeedsRVORowImpl obj, Object value) {
                obj.setPrtPcmFeedsPk((BigDecimal)value);
            }
        }
        ,
        CustomerType {
            public Object get(PrtPcmFeedsRVORowImpl obj) {
                return obj.getCustomerType();
            }

            public void put(PrtPcmFeedsRVORowImpl obj, Object value) {
                obj.setCustomerType((String)value);
            }
        }
        ,
        InformationType {
            public Object get(PrtPcmFeedsRVORowImpl obj) {
                return obj.getInformationType();
            }

            public void put(PrtPcmFeedsRVORowImpl obj, Object value) {
                obj.setInformationType((String)value);
            }
        }
        ,
        PromotionCheck {
            public Object get(PrtPcmFeedsRVORowImpl obj) {
                return obj.getPromotionCheck();
            }

            public void put(PrtPcmFeedsRVORowImpl obj, Object value) {
                obj.setPromotionCheck((String)value);
            }
        }
        ,
        EffectiveDate {
            public Object get(PrtPcmFeedsRVORowImpl obj) {
                return obj.getEffectiveDate();
            }

            public void put(PrtPcmFeedsRVORowImpl obj, Object value) {
                obj.setEffectiveDate((Date)value);
            }
        }
        ,
        EndDate {
            public Object get(PrtPcmFeedsRVORowImpl obj) {
                return obj.getEndDate();
            }

            public void put(PrtPcmFeedsRVORowImpl obj, Object value) {
                obj.setEndDate((Date)value);
            }
        }
        ,
        MessageLang {
            public Object get(PrtPcmFeedsRVORowImpl obj) {
                return obj.getMessageLang();
            }

            public void put(PrtPcmFeedsRVORowImpl obj, Object value) {
                obj.setMessageLang((String)value);
            }
        }
        ,
        MessageEnglish {
            public Object get(PrtPcmFeedsRVORowImpl obj) {
                return obj.getMessageEnglish();
            }

            public void put(PrtPcmFeedsRVORowImpl obj, Object value) {
                obj.setMessageEnglish((String)value);
            }
        }
        ,
        CountryCode {
            public Object get(PrtPcmFeedsRVORowImpl obj) {
                return obj.getCountryCode();
            }

            public void put(PrtPcmFeedsRVORowImpl obj, Object value) {
                obj.setCountryCode((String)value);
            }
        }
        ,
        ModifiedBy {
            public Object get(PrtPcmFeedsRVORowImpl obj) {
                return obj.getModifiedBy();
            }

            public void put(PrtPcmFeedsRVORowImpl obj, Object value) {
                obj.setModifiedBy((String)value);
            }
        }
        ,
        ModifiedDate {
            public Object get(PrtPcmFeedsRVORowImpl obj) {
                return obj.getModifiedDate();
            }

            public void put(PrtPcmFeedsRVORowImpl obj, Object value) {
                obj.setModifiedDate((Timestamp)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtPcmFeedsRVORowImpl object);

        public abstract void put(PrtPcmFeedsRVORowImpl object, Object value);

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
    public static final int PRTPCMFEEDSPK = AttributesEnum.PrtPcmFeedsPk.index();
    public static final int CUSTOMERTYPE = AttributesEnum.CustomerType.index();
    public static final int INFORMATIONTYPE = AttributesEnum.InformationType.index();
    public static final int PROMOTIONCHECK = AttributesEnum.PromotionCheck.index();
    public static final int EFFECTIVEDATE = AttributesEnum.EffectiveDate.index();
    public static final int ENDDATE = AttributesEnum.EndDate.index();
    public static final int MESSAGELANG = AttributesEnum.MessageLang.index();
    public static final int MESSAGEENGLISH = AttributesEnum.MessageEnglish.index();
    public static final int COUNTRYCODE = AttributesEnum.CountryCode.index();
    public static final int MODIFIEDBY = AttributesEnum.ModifiedBy.index();
    public static final int MODIFIEDDATE = AttributesEnum.ModifiedDate.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtPcmFeedsRVORowImpl() {
    }

    /**
     * Gets PrtPcmFeedsEO entity object.
     * @return the PrtPcmFeedsEO
     */
    public PrtPcmFeedsEOImpl getPrtPcmFeedsEO() {
        return (PrtPcmFeedsEOImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for PRT_PCM_FEEDS_PK using the alias name PrtPcmFeedsPk.
     * @return the PRT_PCM_FEEDS_PK
     */
    public BigDecimal getPrtPcmFeedsPk() {
        return (BigDecimal) getAttributeInternal(PRTPCMFEEDSPK);
    }

    /**
     * Sets <code>value</code> as attribute value for PRT_PCM_FEEDS_PK using the alias name PrtPcmFeedsPk.
     * @param value value to set the PRT_PCM_FEEDS_PK
     */
    public void setPrtPcmFeedsPk(BigDecimal value) {
        setAttributeInternal(PRTPCMFEEDSPK, value);
    }

    /**
     * Gets the attribute value for CUSTOMER_TYPE using the alias name CustomerType.
     * @return the CUSTOMER_TYPE
     */
    public String getCustomerType() {
        return (String) getAttributeInternal(CUSTOMERTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for CUSTOMER_TYPE using the alias name CustomerType.
     * @param value value to set the CUSTOMER_TYPE
     */
    public void setCustomerType(String value) {
        setAttributeInternal(CUSTOMERTYPE, value);
    }

    /**
     * Gets the attribute value for INFORMATION_TYPE using the alias name InformationType.
     * @return the INFORMATION_TYPE
     */
    public String getInformationType() {
        return (String) getAttributeInternal(INFORMATIONTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for INFORMATION_TYPE using the alias name InformationType.
     * @param value value to set the INFORMATION_TYPE
     */
    public void setInformationType(String value) {
        setAttributeInternal(INFORMATIONTYPE, value);
    }

    /**
     * Gets the attribute value for PROMOTION_CHECK using the alias name PromotionCheck.
     * @return the PROMOTION_CHECK
     */
    public String getPromotionCheck() {
        return (String) getAttributeInternal(PROMOTIONCHECK);
    }

    /**
     * Sets <code>value</code> as attribute value for PROMOTION_CHECK using the alias name PromotionCheck.
     * @param value value to set the PROMOTION_CHECK
     */
    public void setPromotionCheck(String value) {
        setAttributeInternal(PROMOTIONCHECK, value);
    }

    /**
     * Gets the attribute value for EFFECTIVE_DATE using the alias name EffectiveDate.
     * @return the EFFECTIVE_DATE
     */
    public Date getEffectiveDate() {
        return (Date) getAttributeInternal(EFFECTIVEDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for EFFECTIVE_DATE using the alias name EffectiveDate.
     * @param value value to set the EFFECTIVE_DATE
     */
    public void setEffectiveDate(Date value) {
        setAttributeInternal(EFFECTIVEDATE, value);
    }

    /**
     * Gets the attribute value for END_DATE using the alias name EndDate.
     * @return the END_DATE
     */
    public Date getEndDate() {
        return (Date) getAttributeInternal(ENDDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for END_DATE using the alias name EndDate.
     * @param value value to set the END_DATE
     */
    public void setEndDate(Date value) {
        setAttributeInternal(ENDDATE, value);
    }

    /**
     * Gets the attribute value for MESSAGE_LANG using the alias name MessageLang.
     * @return the MESSAGE_LANG
     */
    public String getMessageLang() {
        return (String) getAttributeInternal(MESSAGELANG);
    }

    /**
     * Sets <code>value</code> as attribute value for MESSAGE_LANG using the alias name MessageLang.
     * @param value value to set the MESSAGE_LANG
     */
    public void setMessageLang(String value) {
        setAttributeInternal(MESSAGELANG, value);
    }

    /**
     * Gets the attribute value for MESSAGE_ENGLISH using the alias name MessageEnglish.
     * @return the MESSAGE_ENGLISH
     */
    public String getMessageEnglish() {
        return (String) getAttributeInternal(MESSAGEENGLISH);
    }

    /**
     * Sets <code>value</code> as attribute value for MESSAGE_ENGLISH using the alias name MessageEnglish.
     * @param value value to set the MESSAGE_ENGLISH
     */
    public void setMessageEnglish(String value) {
        setAttributeInternal(MESSAGEENGLISH, value);
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

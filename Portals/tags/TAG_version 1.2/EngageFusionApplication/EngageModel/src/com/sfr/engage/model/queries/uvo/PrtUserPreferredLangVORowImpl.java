package com.sfr.engage.model.queries.uvo;

import com.sfr.engage.model.entities.PrtUserPreferredLangEOImpl;

import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Aug 11 10:55:50 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtUserPreferredLangVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        SNo {
            public Object get(PrtUserPreferredLangVORowImpl obj) {
                return obj.getSNo();
            }

            public void put(PrtUserPreferredLangVORowImpl obj, Object value) {
                obj.setSNo((DBSequence)value);
            }
        }
        ,
        UserId {
            public Object get(PrtUserPreferredLangVORowImpl obj) {
                return obj.getUserId();
            }

            public void put(PrtUserPreferredLangVORowImpl obj, Object value) {
                obj.setUserId((String)value);
            }
        }
        ,
        PreferredLang {
            public Object get(PrtUserPreferredLangVORowImpl obj) {
                return obj.getPreferredLang();
            }

            public void put(PrtUserPreferredLangVORowImpl obj, Object value) {
                obj.setPreferredLang((String)value);
            }
        }
        ,
        ModifiedBy {
            public Object get(PrtUserPreferredLangVORowImpl obj) {
                return obj.getModifiedBy();
            }

            public void put(PrtUserPreferredLangVORowImpl obj, Object value) {
                obj.setModifiedBy((String)value);
            }
        }
        ,
        ModifiedDate {
            public Object get(PrtUserPreferredLangVORowImpl obj) {
                return obj.getModifiedDate();
            }

            public void put(PrtUserPreferredLangVORowImpl obj, Object value) {
                obj.setModifiedDate((Timestamp)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtUserPreferredLangVORowImpl object);

        public abstract void put(PrtUserPreferredLangVORowImpl object,
                                 Object value);

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
    public static final int SNO = AttributesEnum.SNo.index();
    public static final int USERID = AttributesEnum.UserId.index();
    public static final int PREFERREDLANG = AttributesEnum.PreferredLang.index();
    public static final int MODIFIEDBY = AttributesEnum.ModifiedBy.index();
    public static final int MODIFIEDDATE = AttributesEnum.ModifiedDate.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtUserPreferredLangVORowImpl() {
    }

    /**
     * Gets PrtUserPreferredLangEO entity object.
     * @return the PrtUserPreferredLangEO
     */
    public PrtUserPreferredLangEOImpl getPrtUserPreferredLangEO() {
        return (PrtUserPreferredLangEOImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for S_NO using the alias name SNo.
     * @return the S_NO
     */
    public DBSequence getSNo() {
        return (DBSequence)getAttributeInternal(SNO);
    }

    /**
     * Sets <code>value</code> as attribute value for S_NO using the alias name SNo.
     * @param value value to set the S_NO
     */
    public void setSNo(DBSequence value) {
        setAttributeInternal(SNO, value);
    }

    /**
     * Gets the attribute value for USER_ID using the alias name UserId.
     * @return the USER_ID
     */
    public String getUserId() {
        return (String) getAttributeInternal(USERID);
    }

    /**
     * Sets <code>value</code> as attribute value for USER_ID using the alias name UserId.
     * @param value value to set the USER_ID
     */
    public void setUserId(String value) {
        setAttributeInternal(USERID, value);
    }

    /**
     * Gets the attribute value for PREFERRED_LANG using the alias name PreferredLang.
     * @return the PREFERRED_LANG
     */
    public String getPreferredLang() {
        return (String) getAttributeInternal(PREFERREDLANG);
    }

    /**
     * Sets <code>value</code> as attribute value for PREFERRED_LANG using the alias name PreferredLang.
     * @param value value to set the PREFERRED_LANG
     */
    public void setPreferredLang(String value) {
        setAttributeInternal(PREFERREDLANG, value);
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

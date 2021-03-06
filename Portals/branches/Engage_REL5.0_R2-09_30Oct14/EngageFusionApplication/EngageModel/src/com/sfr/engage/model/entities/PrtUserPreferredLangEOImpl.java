package com.sfr.engage.model.entities;

import oracle.jbo.Key;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Aug 08 10:55:50 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtUserPreferredLangEOImpl extends EntityImpl {
    private static EntityDefImpl mDefinitionObject;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        SNo {
            public Object get(PrtUserPreferredLangEOImpl obj) {
                return obj.getSNo();
            }

            public void put(PrtUserPreferredLangEOImpl obj, Object value) {
                obj.setSNo((DBSequence)value);
            }
        }
        ,
        UserId {
            public Object get(PrtUserPreferredLangEOImpl obj) {
                return obj.getUserId();
            }

            public void put(PrtUserPreferredLangEOImpl obj, Object value) {
                obj.setUserId((String)value);
            }
        }
        ,
        PreferredLang {
            public Object get(PrtUserPreferredLangEOImpl obj) {
                return obj.getPreferredLang();
            }

            public void put(PrtUserPreferredLangEOImpl obj, Object value) {
                obj.setPreferredLang((String)value);
            }
        }
        ,
        ModifiedBy {
            public Object get(PrtUserPreferredLangEOImpl obj) {
                return obj.getModifiedBy();
            }

            public void put(PrtUserPreferredLangEOImpl obj, Object value) {
                obj.setModifiedBy((String)value);
            }
        }
        ,
        ModifiedDate {
            public Object get(PrtUserPreferredLangEOImpl obj) {
                return obj.getModifiedDate();
            }

            public void put(PrtUserPreferredLangEOImpl obj, Object value) {
                obj.setModifiedDate((Timestamp)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtUserPreferredLangEOImpl object);

        public abstract void put(PrtUserPreferredLangEOImpl object,
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
    public PrtUserPreferredLangEOImpl() {
    }


    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = EntityDefImpl.findDefObject("com.sfr.engage.model.entities.PrtUserPreferredLangEO");
        }
        return mDefinitionObject;
    }

    /**
     * Gets the attribute value for SNo, using the alias name SNo.
     * @return the SNo
     */
    public DBSequence getSNo() {
        return (DBSequence)getAttributeInternal(SNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for SNo.
     * @param value value to set the SNo
     */
    public void setSNo(DBSequence value) {
        setAttributeInternal(SNO, value);
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
     * Gets the attribute value for PreferredLang, using the alias name PreferredLang.
     * @return the PreferredLang
     */
    public String getPreferredLang() {
        return (String)getAttributeInternal(PREFERREDLANG);
    }

    /**
     * Sets <code>value</code> as the attribute value for PreferredLang.
     * @param value value to set the PreferredLang
     */
    public void setPreferredLang(String value) {
        setAttributeInternal(PREFERREDLANG, value);
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
     * Sets <code>value</code> as the attribute value for ModifiedDate.
     * @param value value to set the ModifiedDate
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


    /**
     * @param sNo key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(DBSequence sNo) {
        return new Key(new Object[]{sNo});
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
        super.lock();
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        super.doDML(operation, e);
    }
}

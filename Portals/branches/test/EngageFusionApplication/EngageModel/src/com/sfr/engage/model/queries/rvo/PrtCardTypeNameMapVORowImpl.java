package com.sfr.engage.model.queries.rvo;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Jun 19 16:02:14 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtCardTypeNameMapVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Country {
            public Object get(PrtCardTypeNameMapVORowImpl obj) {
                return obj.getCountry();
            }

            public void put(PrtCardTypeNameMapVORowImpl obj, Object value) {
                obj.setCountry((String)value);
            }
        }
        ,
        CardType {
            public Object get(PrtCardTypeNameMapVORowImpl obj) {
                return obj.getCardType();
            }

            public void put(PrtCardTypeNameMapVORowImpl obj, Object value) {
                obj.setCardType((String)value);
            }
        }
        ,
        TypeName {
            public Object get(PrtCardTypeNameMapVORowImpl obj) {
                return obj.getTypeName();
            }

            public void put(PrtCardTypeNameMapVORowImpl obj, Object value) {
                obj.setTypeName((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtCardTypeNameMapVORowImpl object);

        public abstract void put(PrtCardTypeNameMapVORowImpl object,
                                 Object value);

        public int index() {
            return PrtCardTypeNameMapVORowImpl.AttributesEnum.firstIndex() + ordinal();
        }

        public static int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return PrtCardTypeNameMapVORowImpl.AttributesEnum.firstIndex() + PrtCardTypeNameMapVORowImpl.AttributesEnum.staticValues().length;
        }

        public static AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = PrtCardTypeNameMapVORowImpl.AttributesEnum.values();
            }
            return vals;
        }
    }
    public static final int COUNTRY = PrtCardTypeNameMapVORowImpl.AttributesEnum.Country.index();
    public static final int CARDTYPE = PrtCardTypeNameMapVORowImpl.AttributesEnum.CardType.index();
    public static final int TYPENAME = PrtCardTypeNameMapVORowImpl.AttributesEnum.TypeName.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtCardTypeNameMapVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Country.
     * @return the Country
     */
    public String getCountry() {
        return (String) getAttributeInternal(COUNTRY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Country.
     * @param value value to set the  Country
     */
    public void setCountry(String value) {
        setAttributeInternal(COUNTRY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CardType.
     * @return the CardType
     */
    public String getCardType() {
        return (String) getAttributeInternal(CARDTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CardType.
     * @param value value to set the  CardType
     */
    public void setCardType(String value) {
        setAttributeInternal(CARDTYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TypeName.
     * @return the TypeName
     */
    public String getTypeName() {
        return (String) getAttributeInternal(TYPENAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TypeName.
     * @param value value to set the  TypeName
     */
    public void setTypeName(String value) {
        setAttributeInternal(TYPENAME, value);
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
        if ((index >= PrtCardTypeNameMapVORowImpl.AttributesEnum.firstIndex()) && (index < PrtCardTypeNameMapVORowImpl.AttributesEnum.count())) {
            return PrtCardTypeNameMapVORowImpl.AttributesEnum.staticValues()[index - PrtCardTypeNameMapVORowImpl.AttributesEnum.firstIndex()].get(this);
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
        if ((index >= PrtCardTypeNameMapVORowImpl.AttributesEnum.firstIndex()) && (index < PrtCardTypeNameMapVORowImpl.AttributesEnum.count())) {
            PrtCardTypeNameMapVORowImpl.AttributesEnum.staticValues()[index - PrtCardTypeNameMapVORowImpl.AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}

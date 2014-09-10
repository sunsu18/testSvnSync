package com.sfr.engage.model.queries.uvo;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed May 28 16:52:13 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtViewVehicleDriverVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        DriverName {
            public Object get(PrtViewVehicleDriverVORowImpl obj) {
                return obj.getDriverName();
            }

            public void put(PrtViewVehicleDriverVORowImpl obj, Object value) {
                obj.setDriverName((String)value);
            }
        }
        ,
        DriverNumber {
            public Object get(PrtViewVehicleDriverVORowImpl obj) {
                return obj.getDriverNumber();
            }

            public void put(PrtViewVehicleDriverVORowImpl obj, Object value) {
                obj.setDriverNumber((String)value);
            }
        }
        ,
        VehicleNumber {
            public Object get(PrtViewVehicleDriverVORowImpl obj) {
                return obj.getVehicleNumber();
            }

            public void put(PrtViewVehicleDriverVORowImpl obj, Object value) {
                obj.setVehicleNumber((String)value);
            }
        }
        ,
        InternalName {
            public Object get(PrtViewVehicleDriverVORowImpl obj) {
                return obj.getInternalName();
            }

            public void put(PrtViewVehicleDriverVORowImpl obj, Object value) {
                obj.setInternalName((String)value);
            }
        }
        ,
        CardNumber {
            public Object get(PrtViewVehicleDriverVORowImpl obj) {
                return obj.getCardNumber();
            }

            public void put(PrtViewVehicleDriverVORowImpl obj, Object value) {
                obj.setCardNumber((String)value);
            }
        }
        ,
        CardEmbossNum {
            public Object get(PrtViewVehicleDriverVORowImpl obj) {
                return obj.getCardEmbossNum();
            }

            public void put(PrtViewVehicleDriverVORowImpl obj, Object value) {
                obj.setCardEmbossNum((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtViewVehicleDriverVORowImpl object);

        public abstract void put(PrtViewVehicleDriverVORowImpl object,
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
    public static final int DRIVERNAME = AttributesEnum.DriverName.index();
    public static final int DRIVERNUMBER = AttributesEnum.DriverNumber.index();
    public static final int VEHICLENUMBER = AttributesEnum.VehicleNumber.index();
    public static final int INTERNALNAME = AttributesEnum.InternalName.index();
    public static final int CARDNUMBER = AttributesEnum.CardNumber.index();
    public static final int CARDEMBOSSNUM = AttributesEnum.CardEmbossNum.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtViewVehicleDriverVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute DriverName.
     * @return the DriverName
     */
    public String getDriverName() {
        return (String) getAttributeInternal(DRIVERNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DriverName.
     * @param value value to set the  DriverName
     */
    public void setDriverName(String value) {
        setAttributeInternal(DRIVERNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DriverNumber.
     * @return the DriverNumber
     */
    public String getDriverNumber() {
        return (String) getAttributeInternal(DRIVERNUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DriverNumber.
     * @param value value to set the  DriverNumber
     */
    public void setDriverNumber(String value) {
        setAttributeInternal(DRIVERNUMBER, value);
    }

    /**
     * Gets the attribute value for the calculated attribute VehicleNumber.
     * @return the VehicleNumber
     */
    public String getVehicleNumber() {
        return (String) getAttributeInternal(VEHICLENUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute VehicleNumber.
     * @param value value to set the  VehicleNumber
     */
    public void setVehicleNumber(String value) {
        setAttributeInternal(VEHICLENUMBER, value);
    }

    /**
     * Gets the attribute value for the calculated attribute InternalName.
     * @return the InternalName
     */
    public String getInternalName() {
        return (String) getAttributeInternal(INTERNALNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InternalName.
     * @param value value to set the  InternalName
     */
    public void setInternalName(String value) {
        setAttributeInternal(INTERNALNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CardNumber.
     * @return the CardNumber
     */
    public String getCardNumber() {
        return (String) getAttributeInternal(CARDNUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CardNumber.
     * @param value value to set the  CardNumber
     */
    public void setCardNumber(String value) {
        setAttributeInternal(CARDNUMBER, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CardEmbossNum.
     * @return the CardEmbossNum
     */
    public String getCardEmbossNum() {
        return (String) getAttributeInternal(CARDEMBOSSNUM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CardEmbossNum.
     * @param value value to set the  CardEmbossNum
     */
    public void setCardEmbossNum(String value) {
        setAttributeInternal(CARDEMBOSSNUM, value);
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
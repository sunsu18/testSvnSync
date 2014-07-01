package com.sfr.engage.model.queries.rvo;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Jun 11 15:12:18 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtCardTransactionVehicleInfoRVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        VehicleNumber {
            public Object get(PrtCardTransactionVehicleInfoRVORowImpl obj) {
                return obj.getVehicleNumber();
            }

            public void put(PrtCardTransactionVehicleInfoRVORowImpl obj, Object value) {
                obj.setVehicleNumber((String)value);
            }
        }
        ,
        PrtCardPk {
            public Object get(PrtCardTransactionVehicleInfoRVORowImpl obj) {
                return obj.getPrtCardPk();
            }

            public void put(PrtCardTransactionVehicleInfoRVORowImpl obj, Object value) {
                obj.setPrtCardPk((String)value);
            }
        }
        ,
        ReferenceNumber {
            public Object get(PrtCardTransactionVehicleInfoRVORowImpl obj) {
                return obj.getReferenceNumber();
            }

            public void put(PrtCardTransactionVehicleInfoRVORowImpl obj, Object value) {
                obj.setReferenceNumber((String)value);
            }
        }
        ,
        AccountNumber {
            public Object get(PrtCardTransactionVehicleInfoRVORowImpl obj) {
                return obj.getAccountNumber();
            }

            public void put(PrtCardTransactionVehicleInfoRVORowImpl obj, Object value) {
                obj.setAccountNumber((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtCardTransactionVehicleInfoRVORowImpl object);

        public abstract void put(PrtCardTransactionVehicleInfoRVORowImpl object,
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


    public static final int VEHICLENUMBER = AttributesEnum.VehicleNumber.index();
    public static final int PRTCARDPK = AttributesEnum.PrtCardPk.index();
    public static final int REFERENCENUMBER = AttributesEnum.ReferenceNumber.index();
    public static final int ACCOUNTNUMBER = AttributesEnum.AccountNumber.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtCardTransactionVehicleInfoRVORowImpl() {
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
     * Gets the attribute value for the calculated attribute PrtCardPk.
     * @return the PrtCardPk
     */
    public String getPrtCardPk() {
        return (String) getAttributeInternal(PRTCARDPK);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PrtCardPk.
     * @param value value to set the  PrtCardPk
     */
    public void setPrtCardPk(String value) {
        setAttributeInternal(PRTCARDPK, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ReferenceNumber.
     * @return the ReferenceNumber
     */
    public String getReferenceNumber() {
        return (String) getAttributeInternal(REFERENCENUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ReferenceNumber.
     * @param value value to set the  ReferenceNumber
     */
    public void setReferenceNumber(String value) {
        setAttributeInternal(REFERENCENUMBER, value);
    }

    /**
     * Gets the attribute value for the calculated attribute AccountNumber.
     * @return the AccountNumber
     */
    public String getAccountNumber() {
        return (String) getAttributeInternal(ACCOUNTNUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute AccountNumber.
     * @param value value to set the  AccountNumber
     */
    public void setAccountNumber(String value) {
        setAttributeInternal(ACCOUNTNUMBER, value);
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

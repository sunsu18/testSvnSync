package com.sfr.engage.model.queries.rvo;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Sep 11 16:01:18 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtCardFuelCapacityRVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CountryCode {
            public Object get(PrtCardFuelCapacityRVORowImpl obj) {
                return obj.getCountryCode();
            }

            public void put(PrtCardFuelCapacityRVORowImpl obj, Object value) {
                obj.setCountryCode((String)value);
            }
        }
        ,
        SubscrId {
            public Object get(PrtCardFuelCapacityRVORowImpl obj) {
                return obj.getSubscrId();
            }

            public void put(PrtCardFuelCapacityRVORowImpl obj, Object value) {
                obj.setSubscrId((Number)value);
            }
        }
        ,
        FuelPerDay {
            public Object get(PrtCardFuelCapacityRVORowImpl obj) {
                return obj.getFuelPerDay();
            }

            public void put(PrtCardFuelCapacityRVORowImpl obj, Object value) {
                obj.setFuelPerDay((Number)value);
            }
        }
        ,
        FuelPerWeek {
            public Object get(PrtCardFuelCapacityRVORowImpl obj) {
                return obj.getFuelPerWeek();
            }

            public void put(PrtCardFuelCapacityRVORowImpl obj, Object value) {
                obj.setFuelPerWeek((Number)value);
            }
        }
        ,
        FuelPerMonth {
            public Object get(PrtCardFuelCapacityRVORowImpl obj) {
                return obj.getFuelPerMonth();
            }

            public void put(PrtCardFuelCapacityRVORowImpl obj, Object value) {
                obj.setFuelPerMonth((Number)value);
            }
        }
        ,
        RuleFuelCapId {
            public Object get(PrtCardFuelCapacityRVORowImpl obj) {
                return obj.getRuleFuelCapId();
            }

            public void put(PrtCardFuelCapacityRVORowImpl obj, Object value) {
                obj.setRuleFuelCapId((Number)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtCardFuelCapacityRVORowImpl object);

        public abstract void put(PrtCardFuelCapacityRVORowImpl object,
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
    public static final int COUNTRYCODE = AttributesEnum.CountryCode.index();
    public static final int SUBSCRID = AttributesEnum.SubscrId.index();
    public static final int FUELPERDAY = AttributesEnum.FuelPerDay.index();
    public static final int FUELPERWEEK = AttributesEnum.FuelPerWeek.index();
    public static final int FUELPERMONTH = AttributesEnum.FuelPerMonth.index();
    public static final int RULEFUELCAPID = AttributesEnum.RuleFuelCapId.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtCardFuelCapacityRVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute CountryCode.
     * @return the CountryCode
     */
    public String getCountryCode() {
        return (String) getAttributeInternal(COUNTRYCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CountryCode.
     * @param value value to set the  CountryCode
     */
    public void setCountryCode(String value) {
        setAttributeInternal(COUNTRYCODE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SubscrId.
     * @return the SubscrId
     */
    public Number getSubscrId() {
        return (Number) getAttributeInternal(SUBSCRID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SubscrId.
     * @param value value to set the  SubscrId
     */
    public void setSubscrId(Number value) {
        setAttributeInternal(SUBSCRID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute FuelPerDay.
     * @return the FuelPerDay
     */
    public Number getFuelPerDay() {
        return (Number) getAttributeInternal(FUELPERDAY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute FuelPerDay.
     * @param value value to set the  FuelPerDay
     */
    public void setFuelPerDay(Number value) {
        setAttributeInternal(FUELPERDAY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute FuelPerWeek.
     * @return the FuelPerWeek
     */
    public Number getFuelPerWeek() {
        return (Number) getAttributeInternal(FUELPERWEEK);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute FuelPerWeek.
     * @param value value to set the  FuelPerWeek
     */
    public void setFuelPerWeek(Number value) {
        setAttributeInternal(FUELPERWEEK, value);
    }

    /**
     * Gets the attribute value for the calculated attribute FuelPerMonth.
     * @return the FuelPerMonth
     */
    public Number getFuelPerMonth() {
        return (Number) getAttributeInternal(FUELPERMONTH);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute FuelPerMonth.
     * @param value value to set the  FuelPerMonth
     */
    public void setFuelPerMonth(Number value) {
        setAttributeInternal(FUELPERMONTH, value);
    }

    /**
     * Gets the attribute value for the calculated attribute RuleFuelCapId.
     * @return the RuleFuelCapId
     */
    public Number getRuleFuelCapId() {
        return (Number) getAttributeInternal(RULEFUELCAPID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute RuleFuelCapId.
     * @param value value to set the  RuleFuelCapId
     */
    public void setRuleFuelCapId(Number value) {
        setAttributeInternal(RULEFUELCAPID, value);
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

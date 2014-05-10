package com.sfr.engage.model.queries.uvo;

import com.sfr.engage.model.entities.PrtTruckInformationEOImpl;

import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu May 08 16:24:36 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtTruckInformationVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        PrtTruckInformationPk {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getPrtTruckInformationPk();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setPrtTruckInformationPk((DBSequence)value);
            }
        }
        ,
        AccountNumber {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getAccountNumber();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setAccountNumber((String)value);
            }
        }
        ,
        CardNumber {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getCardNumber();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setCardNumber((String)value);
            }
        }
        ,
        VehicleNumber {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getVehicleNumber();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setVehicleNumber((String)value);
            }
        }
        ,
        InternalName {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getInternalName();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setInternalName((String)value);
            }
        }
        ,
        RegistrationNumber {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getRegistrationNumber();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setRegistrationNumber((String)value);
            }
        }
        ,
        Brand {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getBrand();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setBrand((String)value);
            }
        }
        ,
        Year {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getYear();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setYear((Number)value);
            }
        }
        ,
        RegistrationDate {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getRegistrationDate();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setRegistrationDate((Date)value);
            }
        }
        ,
        EndDate {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getEndDate();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setEndDate((Date)value);
            }
        }
        ,
        FuelType {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getFuelType();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setFuelType((String)value);
            }
        }
        ,
        MaxFuel {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getMaxFuel();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setMaxFuel((Number)value);
            }
        }
        ,
        Odometer {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getOdometer();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setOdometer((Number)value);
            }
        }
        ,
        Remarks {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getRemarks();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setRemarks((String)value);
            }
        }
        ,
        ModifiedBy {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getModifiedBy();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setModifiedBy((String)value);
            }
        }
        ,
        ModifiedDate {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getModifiedDate();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setModifiedDate((Timestamp)value);
            }
        }
        ,
        CountryCode {
            public Object get(PrtTruckInformationVORowImpl obj) {
                return obj.getCountryCode();
            }

            public void put(PrtTruckInformationVORowImpl obj, Object value) {
                obj.setCountryCode((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtTruckInformationVORowImpl object);

        public abstract void put(PrtTruckInformationVORowImpl object,
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

    public static final int PRTTRUCKINFORMATIONPK = AttributesEnum.PrtTruckInformationPk.index();
    public static final int ACCOUNTNUMBER = AttributesEnum.AccountNumber.index();
    public static final int CARDNUMBER = AttributesEnum.CardNumber.index();
    public static final int VEHICLENUMBER = AttributesEnum.VehicleNumber.index();
    public static final int INTERNALNAME = AttributesEnum.InternalName.index();
    public static final int REGISTRATIONNUMBER = AttributesEnum.RegistrationNumber.index();
    public static final int BRAND = AttributesEnum.Brand.index();
    public static final int YEAR = AttributesEnum.Year.index();
    public static final int REGISTRATIONDATE = AttributesEnum.RegistrationDate.index();
    public static final int ENDDATE = AttributesEnum.EndDate.index();
    public static final int FUELTYPE = AttributesEnum.FuelType.index();
    public static final int MAXFUEL = AttributesEnum.MaxFuel.index();
    public static final int ODOMETER = AttributesEnum.Odometer.index();
    public static final int REMARKS = AttributesEnum.Remarks.index();
    public static final int MODIFIEDBY = AttributesEnum.ModifiedBy.index();
    public static final int MODIFIEDDATE = AttributesEnum.ModifiedDate.index();
    public static final int COUNTRYCODE = AttributesEnum.CountryCode.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtTruckInformationVORowImpl() {
    }

    /**
     * Gets PrtTruckInformationEO entity object.
     * @return the PrtTruckInformationEO
     */
    public PrtTruckInformationEOImpl getPrtTruckInformationEO() {
        return (PrtTruckInformationEOImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for PRT_TRUCK_INFORMATION_PK using the alias name PrtTruckInformationPk.
     * @return the PRT_TRUCK_INFORMATION_PK
     */
    public DBSequence getPrtTruckInformationPk() {
        return (DBSequence)getAttributeInternal(PRTTRUCKINFORMATIONPK);
    }

    /**
     * Sets <code>value</code> as attribute value for PRT_TRUCK_INFORMATION_PK using the alias name PrtTruckInformationPk.
     * @param value value to set the PRT_TRUCK_INFORMATION_PK
     */
    public void setPrtTruckInformationPk(DBSequence value) {
        setAttributeInternal(PRTTRUCKINFORMATIONPK, value);
    }

    /**
     * Gets the attribute value for ACCOUNT_NUMBER using the alias name AccountNumber.
     * @return the ACCOUNT_NUMBER
     */
    public String getAccountNumber() {
        return (String) getAttributeInternal(ACCOUNTNUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for ACCOUNT_NUMBER using the alias name AccountNumber.
     * @param value value to set the ACCOUNT_NUMBER
     */
    public void setAccountNumber(String value) {
        setAttributeInternal(ACCOUNTNUMBER, value);
    }

    /**
     * Gets the attribute value for CARD_NUMBER using the alias name CardNumber.
     * @return the CARD_NUMBER
     */
    public String getCardNumber() {
        return (String) getAttributeInternal(CARDNUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for CARD_NUMBER using the alias name CardNumber.
     * @param value value to set the CARD_NUMBER
     */
    public void setCardNumber(String value) {
        setAttributeInternal(CARDNUMBER, value);
    }

    /**
     * Gets the attribute value for VEHICLE_NUMBER using the alias name VehicleNumber.
     * @return the VEHICLE_NUMBER
     */
    public String getVehicleNumber() {
        return (String) getAttributeInternal(VEHICLENUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for VEHICLE_NUMBER using the alias name VehicleNumber.
     * @param value value to set the VEHICLE_NUMBER
     */
    public void setVehicleNumber(String value) {
        setAttributeInternal(VEHICLENUMBER, value);
    }

    /**
     * Gets the attribute value for INTERNAL_NAME using the alias name InternalName.
     * @return the INTERNAL_NAME
     */
    public String getInternalName() {
        return (String) getAttributeInternal(INTERNALNAME);
    }

    /**
     * Sets <code>value</code> as attribute value for INTERNAL_NAME using the alias name InternalName.
     * @param value value to set the INTERNAL_NAME
     */
    public void setInternalName(String value) {
        setAttributeInternal(INTERNALNAME, value);
    }

    /**
     * Gets the attribute value for REGISTRATION_NUMBER using the alias name RegistrationNumber.
     * @return the REGISTRATION_NUMBER
     */
    public String getRegistrationNumber() {
        return (String) getAttributeInternal(REGISTRATIONNUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for REGISTRATION_NUMBER using the alias name RegistrationNumber.
     * @param value value to set the REGISTRATION_NUMBER
     */
    public void setRegistrationNumber(String value) {
        setAttributeInternal(REGISTRATIONNUMBER, value);
    }

    /**
     * Gets the attribute value for BRAND using the alias name Brand.
     * @return the BRAND
     */
    public String getBrand() {
        return (String) getAttributeInternal(BRAND);
    }

    /**
     * Sets <code>value</code> as attribute value for BRAND using the alias name Brand.
     * @param value value to set the BRAND
     */
    public void setBrand(String value) {
        setAttributeInternal(BRAND, value);
    }

    /**
     * Gets the attribute value for YEAR using the alias name Year.
     * @return the YEAR
     */
    public Number getYear() {
        return (Number) getAttributeInternal(YEAR);
    }

    /**
     * Sets <code>value</code> as attribute value for YEAR using the alias name Year.
     * @param value value to set the YEAR
     */
    public void setYear(Number value) {
        setAttributeInternal(YEAR, value);
    }

    /**
     * Gets the attribute value for REGISTRATION_DATE using the alias name RegistrationDate.
     * @return the REGISTRATION_DATE
     */
    public Date getRegistrationDate() {
        return (Date) getAttributeInternal(REGISTRATIONDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for REGISTRATION_DATE using the alias name RegistrationDate.
     * @param value value to set the REGISTRATION_DATE
     */
    public void setRegistrationDate(Date value) {
        setAttributeInternal(REGISTRATIONDATE, value);
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
     * Gets the attribute value for FUEL_TYPE using the alias name FuelType.
     * @return the FUEL_TYPE
     */
    public String getFuelType() {
        return (String) getAttributeInternal(FUELTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for FUEL_TYPE using the alias name FuelType.
     * @param value value to set the FUEL_TYPE
     */
    public void setFuelType(String value) {
        setAttributeInternal(FUELTYPE, value);
    }

    /**
     * Gets the attribute value for MAX_FUEL using the alias name MaxFuel.
     * @return the MAX_FUEL
     */
    public Number getMaxFuel() {
        return (Number) getAttributeInternal(MAXFUEL);
    }

    /**
     * Sets <code>value</code> as attribute value for MAX_FUEL using the alias name MaxFuel.
     * @param value value to set the MAX_FUEL
     */
    public void setMaxFuel(Number value) {
        setAttributeInternal(MAXFUEL, value);
    }

    /**
     * Gets the attribute value for ODOMETER using the alias name Odometer.
     * @return the ODOMETER
     */
    public Number getOdometer() {
        return (Number) getAttributeInternal(ODOMETER);
    }

    /**
     * Sets <code>value</code> as attribute value for ODOMETER using the alias name Odometer.
     * @param value value to set the ODOMETER
     */
    public void setOdometer(Number value) {
        setAttributeInternal(ODOMETER, value);
    }

    /**
     * Gets the attribute value for REMARKS using the alias name Remarks.
     * @return the REMARKS
     */
    public String getRemarks() {
        return (String) getAttributeInternal(REMARKS);
    }

    /**
     * Sets <code>value</code> as attribute value for REMARKS using the alias name Remarks.
     * @param value value to set the REMARKS
     */
    public void setRemarks(String value) {
        setAttributeInternal(REMARKS, value);
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

package com.sfr.engage.model.queries.uvo;

import com.sfr.engage.model.entities.PrtDriverInformationEOImpl;

import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu May 08 15:19:05 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtDriverInformationVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        PrtDriverInformationPk {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getPrtDriverInformationPk();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setPrtDriverInformationPk((DBSequence)value);
            }
        }
        ,
        AccountNumber {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getAccountNumber();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setAccountNumber((String)value);
            }
        }
        ,
        CardNumber {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getCardNumber();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setCardNumber((String)value);
            }
        }
        ,
        DriverNumber {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getDriverNumber();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setDriverNumber((String)value);
            }
        }
        ,
        DriverName {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getDriverName();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setDriverName((String)value);
            }
        }
        ,
        Nationality {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getNationality();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setNationality((String)value);
            }
        }
        ,
        MobileNumber {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getMobileNumber();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setMobileNumber((Number)value);
            }
        }
        ,
        Remarks {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getRemarks();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setRemarks((String)value);
            }
        }
        ,
        PassportNumber {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getPassportNumber();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setPassportNumber((String)value);
            }
        }
        ,
        PassportExpiry {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getPassportExpiry();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setPassportExpiry((Date)value);
            }
        }
        ,
        LicenseNumber {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getLicenseNumber();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setLicenseNumber((String)value);
            }
        }
        ,
        LicenseExpiry {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getLicenseExpiry();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setLicenseExpiry((Date)value);
            }
        }
        ,
        EmployStart {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getEmployStart();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setEmployStart((Date)value);
            }
        }
        ,
        EmployEnd {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getEmployEnd();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setEmployEnd((Date)value);
            }
        }
        ,
        ModifiedBy {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getModifiedBy();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setModifiedBy((String)value);
            }
        }
        ,
        ModifiedDate {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getModifiedDate();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setModifiedDate((Timestamp)value);
            }
        }
        ,
        CountryCode {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getCountryCode();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setCountryCode((String)value);
            }
        }
        ,
        ReferenceNumber {
            public Object get(PrtDriverInformationVORowImpl obj) {
                return obj.getReferenceNumber();
            }

            public void put(PrtDriverInformationVORowImpl obj, Object value) {
                obj.setReferenceNumber((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtDriverInformationVORowImpl object);

        public abstract void put(PrtDriverInformationVORowImpl object,
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

    public static final int PRTDRIVERINFORMATIONPK = AttributesEnum.PrtDriverInformationPk.index();
    public static final int ACCOUNTNUMBER = AttributesEnum.AccountNumber.index();
    public static final int CARDNUMBER = AttributesEnum.CardNumber.index();
    public static final int DRIVERNUMBER = AttributesEnum.DriverNumber.index();
    public static final int DRIVERNAME = AttributesEnum.DriverName.index();
    public static final int NATIONALITY = AttributesEnum.Nationality.index();
    public static final int MOBILENUMBER = AttributesEnum.MobileNumber.index();
    public static final int REMARKS = AttributesEnum.Remarks.index();
    public static final int PASSPORTNUMBER = AttributesEnum.PassportNumber.index();
    public static final int PASSPORTEXPIRY = AttributesEnum.PassportExpiry.index();
    public static final int LICENSENUMBER = AttributesEnum.LicenseNumber.index();
    public static final int LICENSEEXPIRY = AttributesEnum.LicenseExpiry.index();
    public static final int EMPLOYSTART = AttributesEnum.EmployStart.index();
    public static final int EMPLOYEND = AttributesEnum.EmployEnd.index();
    public static final int MODIFIEDBY = AttributesEnum.ModifiedBy.index();
    public static final int MODIFIEDDATE = AttributesEnum.ModifiedDate.index();
    public static final int COUNTRYCODE = AttributesEnum.CountryCode.index();
    public static final int REFERENCENUMBER = AttributesEnum.ReferenceNumber.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtDriverInformationVORowImpl() {
    }

    /**
     * Gets PrtDriverInformationEO entity object.
     * @return the PrtDriverInformationEO
     */
    public PrtDriverInformationEOImpl getPrtDriverInformationEO() {
        return (PrtDriverInformationEOImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for PRT_DRIVER_INFORMATION_PK using the alias name PrtDriverInformationPk.
     * @return the PRT_DRIVER_INFORMATION_PK
     */
    public DBSequence getPrtDriverInformationPk() {
        return (DBSequence)getAttributeInternal(PRTDRIVERINFORMATIONPK);
    }

    /**
     * Sets <code>value</code> as attribute value for PRT_DRIVER_INFORMATION_PK using the alias name PrtDriverInformationPk.
     * @param value value to set the PRT_DRIVER_INFORMATION_PK
     */
    public void setPrtDriverInformationPk(DBSequence value) {
        setAttributeInternal(PRTDRIVERINFORMATIONPK, value);
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
     * Gets the attribute value for DRIVER_NUMBER using the alias name DriverNumber.
     * @return the DRIVER_NUMBER
     */
    public String getDriverNumber() {
        return (String) getAttributeInternal(DRIVERNUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for DRIVER_NUMBER using the alias name DriverNumber.
     * @param value value to set the DRIVER_NUMBER
     */
    public void setDriverNumber(String value) {
        setAttributeInternal(DRIVERNUMBER, value);
    }

    /**
     * Gets the attribute value for DRIVER_NAME using the alias name DriverName.
     * @return the DRIVER_NAME
     */
    public String getDriverName() {
        return (String) getAttributeInternal(DRIVERNAME);
    }

    /**
     * Sets <code>value</code> as attribute value for DRIVER_NAME using the alias name DriverName.
     * @param value value to set the DRIVER_NAME
     */
    public void setDriverName(String value) {
        setAttributeInternal(DRIVERNAME, value);
    }

    /**
     * Gets the attribute value for NATIONALITY using the alias name Nationality.
     * @return the NATIONALITY
     */
    public String getNationality() {
        return (String) getAttributeInternal(NATIONALITY);
    }

    /**
     * Sets <code>value</code> as attribute value for NATIONALITY using the alias name Nationality.
     * @param value value to set the NATIONALITY
     */
    public void setNationality(String value) {
        setAttributeInternal(NATIONALITY, value);
    }

    /**
     * Gets the attribute value for MOBILE_NUMBER using the alias name MobileNumber.
     * @return the MOBILE_NUMBER
     */
    public Number getMobileNumber() {
        return (Number) getAttributeInternal(MOBILENUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for MOBILE_NUMBER using the alias name MobileNumber.
     * @param value value to set the MOBILE_NUMBER
     */
    public void setMobileNumber(Number value) {
        setAttributeInternal(MOBILENUMBER, value);
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
     * Gets the attribute value for PASSPORT_NUMBER using the alias name PassportNumber.
     * @return the PASSPORT_NUMBER
     */
    public String getPassportNumber() {
        return (String) getAttributeInternal(PASSPORTNUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for PASSPORT_NUMBER using the alias name PassportNumber.
     * @param value value to set the PASSPORT_NUMBER
     */
    public void setPassportNumber(String value) {
        setAttributeInternal(PASSPORTNUMBER, value);
    }

    /**
     * Gets the attribute value for PASSPORT_EXPIRY using the alias name PassportExpiry.
     * @return the PASSPORT_EXPIRY
     */
    public Date getPassportExpiry() {
        return (Date) getAttributeInternal(PASSPORTEXPIRY);
    }

    /**
     * Sets <code>value</code> as attribute value for PASSPORT_EXPIRY using the alias name PassportExpiry.
     * @param value value to set the PASSPORT_EXPIRY
     */
    public void setPassportExpiry(Date value) {
        setAttributeInternal(PASSPORTEXPIRY, value);
    }

    /**
     * Gets the attribute value for LICENSE_NUMBER using the alias name LicenseNumber.
     * @return the LICENSE_NUMBER
     */
    public String getLicenseNumber() {
        return (String) getAttributeInternal(LICENSENUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for LICENSE_NUMBER using the alias name LicenseNumber.
     * @param value value to set the LICENSE_NUMBER
     */
    public void setLicenseNumber(String value) {
        setAttributeInternal(LICENSENUMBER, value);
    }

    /**
     * Gets the attribute value for LICENSE_EXPIRY using the alias name LicenseExpiry.
     * @return the LICENSE_EXPIRY
     */
    public Date getLicenseExpiry() {
        return (Date) getAttributeInternal(LICENSEEXPIRY);
    }

    /**
     * Sets <code>value</code> as attribute value for LICENSE_EXPIRY using the alias name LicenseExpiry.
     * @param value value to set the LICENSE_EXPIRY
     */
    public void setLicenseExpiry(Date value) {
        setAttributeInternal(LICENSEEXPIRY, value);
    }

    /**
     * Gets the attribute value for EMPLOY_START using the alias name EmployStart.
     * @return the EMPLOY_START
     */
    public Date getEmployStart() {
        return (Date) getAttributeInternal(EMPLOYSTART);
    }

    /**
     * Sets <code>value</code> as attribute value for EMPLOY_START using the alias name EmployStart.
     * @param value value to set the EMPLOY_START
     */
    public void setEmployStart(Date value) {
        setAttributeInternal(EMPLOYSTART, value);
    }

    /**
     * Gets the attribute value for EMPLOY_END using the alias name EmployEnd.
     * @return the EMPLOY_END
     */
    public Date getEmployEnd() {
        return (Date) getAttributeInternal(EMPLOYEND);
    }

    /**
     * Sets <code>value</code> as attribute value for EMPLOY_END using the alias name EmployEnd.
     * @param value value to set the EMPLOY_END
     */
    public void setEmployEnd(Date value) {
        setAttributeInternal(EMPLOYEND, value);
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
     * Gets the attribute value for REFERENCE_NUMBER using the alias name ReferenceNumber.
     * @return the REFERENCE_NUMBER
     */
    public String getReferenceNumber() {
        return (String) getAttributeInternal(REFERENCENUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for REFERENCE_NUMBER using the alias name ReferenceNumber.
     * @param value value to set the REFERENCE_NUMBER
     */
    public void setReferenceNumber(String value) {
        setAttributeInternal(REFERENCENUMBER, value);
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

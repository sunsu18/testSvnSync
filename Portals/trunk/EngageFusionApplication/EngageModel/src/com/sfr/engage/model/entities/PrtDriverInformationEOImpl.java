package com.sfr.engage.model.entities;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Mar 13 12:50:04 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtDriverInformationEOImpl extends EntityImpl {
    private static EntityDefImpl mDefinitionObject;

    @Override
    protected void doDML(int i, TransactionEvent transactionEvent) {
        System.out.println("Is it coming inside this method");
        super.doDML(i, transactionEvent);
    }

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        PrtDriverInformationPk {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getPrtDriverInformationPk();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setPrtDriverInformationPk((DBSequence)value);
            }
        }
        ,
        AccountId {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getAccountId();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setAccountId((String)value);
            }
        }
        ,
        CardNumber {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getCardNumber();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setCardNumber((String)value);
            }
        }
        ,
        DriverNumber {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getDriverNumber();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setDriverNumber((String)value);
            }
        }
        ,
        DriverName {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getDriverName();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setDriverName((String)value);
            }
        }
        ,
        Nationality {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getNationality();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setNationality((String)value);
            }
        }
        ,
        MobileNumber {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getMobileNumber();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setMobileNumber((Number)value);
            }
        }
        ,
        Remarks {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getRemarks();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setRemarks((String)value);
            }
        }
        ,
        PassportNumber {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getPassportNumber();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setPassportNumber((String)value);
            }
        }
        ,
        PassportExpiry {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getPassportExpiry();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setPassportExpiry((Date)value);
            }
        }
        ,
        LicenseNumber {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getLicenseNumber();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setLicenseNumber((String)value);
            }
        }
        ,
        LicenseExpiry {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getLicenseExpiry();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setLicenseExpiry((Date)value);
            }
        }
        ,
        EmployStart {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getEmployStart();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setEmployStart((Date)value);
            }
        }
        ,
        EmployEnd {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getEmployEnd();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setEmployEnd((Date)value);
            }
        }
        ,
        ModifiedBy {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getModifiedBy();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setModifiedBy((String)value);
            }
        }
        ,
        ModifiedDate {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getModifiedDate();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CountryCode {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getCountryCode();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setCountryCode((String)value);
            }
        }
        ,
        PrtAccountEO {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getPrtAccountEO();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setPrtAccountEO((PrtAccountEOImpl)value);
            }
        }
        ,
        PrtAccountEO1 {
            public Object get(PrtDriverInformationEOImpl obj) {
                return obj.getPrtAccountEO1();
            }

            public void put(PrtDriverInformationEOImpl obj, Object value) {
                obj.setPrtAccountEO1((PrtAccountEOImpl)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtDriverInformationEOImpl object);

        public abstract void put(PrtDriverInformationEOImpl object,
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
    public static final int ACCOUNTID = AttributesEnum.AccountId.index();
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
    public static final int PRTACCOUNTEO = AttributesEnum.PrtAccountEO.index();
    public static final int PRTACCOUNTEO1 = AttributesEnum.PrtAccountEO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtDriverInformationEOImpl() {
    }


    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = EntityDefImpl.findDefObject("com.sfr.engage.model.entities.PrtDriverInformationEO");
        }
        return mDefinitionObject;
    }

    /**
     * Gets the attribute value for PrtDriverInformationPk, using the alias name PrtDriverInformationPk.
     * @return the PrtDriverInformationPk
     */
    public DBSequence getPrtDriverInformationPk() {
        return (DBSequence)getAttributeInternal(PRTDRIVERINFORMATIONPK);
    }

    /**
     * Sets <code>value</code> as the attribute value for PrtDriverInformationPk.
     * @param value value to set the PrtDriverInformationPk
     */
    public void setPrtDriverInformationPk(DBSequence value) {
        setAttributeInternal(PRTDRIVERINFORMATIONPK, value);
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
     * Gets the attribute value for CardNumber, using the alias name CardNumber.
     * @return the CardNumber
     */
    public String getCardNumber() {
        return (String)getAttributeInternal(CARDNUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for CardNumber.
     * @param value value to set the CardNumber
     */
    public void setCardNumber(String value) {
        setAttributeInternal(CARDNUMBER, value);
    }

    /**
     * Gets the attribute value for DriverNumber, using the alias name DriverNumber.
     * @return the DriverNumber
     */
    public String getDriverNumber() {
        return (String)getAttributeInternal(DRIVERNUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for DriverNumber.
     * @param value value to set the DriverNumber
     */
    public void setDriverNumber(String value) {
        setAttributeInternal(DRIVERNUMBER, value);
    }

    /**
     * Gets the attribute value for DriverName, using the alias name DriverName.
     * @return the DriverName
     */
    public String getDriverName() {
        return (String)getAttributeInternal(DRIVERNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for DriverName.
     * @param value value to set the DriverName
     */
    public void setDriverName(String value) {
        setAttributeInternal(DRIVERNAME, value);
    }

    /**
     * Gets the attribute value for Nationality, using the alias name Nationality.
     * @return the Nationality
     */
    public String getNationality() {
        return (String)getAttributeInternal(NATIONALITY);
    }

    /**
     * Sets <code>value</code> as the attribute value for Nationality.
     * @param value value to set the Nationality
     */
    public void setNationality(String value) {
        setAttributeInternal(NATIONALITY, value);
    }

    /**
     * Gets the attribute value for MobileNumber, using the alias name MobileNumber.
     * @return the MobileNumber
     */
    public Number getMobileNumber() {
        return (Number)getAttributeInternal(MOBILENUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for MobileNumber.
     * @param value value to set the MobileNumber
     */
    public void setMobileNumber(Number value) {
        setAttributeInternal(MOBILENUMBER, value);
    }

    /**
     * Gets the attribute value for Remarks, using the alias name Remarks.
     * @return the Remarks
     */
    public String getRemarks() {
        return (String)getAttributeInternal(REMARKS);
    }

    /**
     * Sets <code>value</code> as the attribute value for Remarks.
     * @param value value to set the Remarks
     */
    public void setRemarks(String value) {
        setAttributeInternal(REMARKS, value);
    }

    /**
     * Gets the attribute value for PassportNumber, using the alias name PassportNumber.
     * @return the PassportNumber
     */
    public String getPassportNumber() {
        return (String)getAttributeInternal(PASSPORTNUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for PassportNumber.
     * @param value value to set the PassportNumber
     */
    public void setPassportNumber(String value) {
        setAttributeInternal(PASSPORTNUMBER, value);
    }

    /**
     * Gets the attribute value for PassportExpiry, using the alias name PassportExpiry.
     * @return the PassportExpiry
     */
    public Date getPassportExpiry() {
        return (Date)getAttributeInternal(PASSPORTEXPIRY);
    }

    /**
     * Sets <code>value</code> as the attribute value for PassportExpiry.
     * @param value value to set the PassportExpiry
     */
    public void setPassportExpiry(Date value) {
        setAttributeInternal(PASSPORTEXPIRY, value);
    }

    /**
     * Gets the attribute value for LicenseNumber, using the alias name LicenseNumber.
     * @return the LicenseNumber
     */
    public String getLicenseNumber() {
        return (String)getAttributeInternal(LICENSENUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for LicenseNumber.
     * @param value value to set the LicenseNumber
     */
    public void setLicenseNumber(String value) {
        setAttributeInternal(LICENSENUMBER, value);
    }

    /**
     * Gets the attribute value for LicenseExpiry, using the alias name LicenseExpiry.
     * @return the LicenseExpiry
     */
    public Date getLicenseExpiry() {
        return (Date)getAttributeInternal(LICENSEEXPIRY);
    }

    /**
     * Sets <code>value</code> as the attribute value for LicenseExpiry.
     * @param value value to set the LicenseExpiry
     */
    public void setLicenseExpiry(Date value) {
        setAttributeInternal(LICENSEEXPIRY, value);
    }

    /**
     * Gets the attribute value for EmployStart, using the alias name EmployStart.
     * @return the EmployStart
     */
    public Date getEmployStart() {
        return (Date)getAttributeInternal(EMPLOYSTART);
    }

    /**
     * Sets <code>value</code> as the attribute value for EmployStart.
     * @param value value to set the EmployStart
     */
    public void setEmployStart(Date value) {
        setAttributeInternal(EMPLOYSTART, value);
    }

    /**
     * Gets the attribute value for EmployEnd, using the alias name EmployEnd.
     * @return the EmployEnd
     */
    public Date getEmployEnd() {
        return (Date)getAttributeInternal(EMPLOYEND);
    }

    /**
     * Sets <code>value</code> as the attribute value for EmployEnd.
     * @param value value to set the EmployEnd
     */
    public void setEmployEnd(Date value) {
        setAttributeInternal(EMPLOYEND, value);
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
     * @return the associated entity PrtAccountEOImpl.
     */
    public PrtAccountEOImpl getPrtAccountEO() {
        return (PrtAccountEOImpl)getAttributeInternal(PRTACCOUNTEO);
    }

    /**
     * Sets <code>value</code> as the associated entity PrtAccountEOImpl.
     */
    public void setPrtAccountEO(PrtAccountEOImpl value) {
        setAttributeInternal(PRTACCOUNTEO, value);
    }


    /**
     * @return the associated entity PrtAccountEOImpl.
     */
    public PrtAccountEOImpl getPrtAccountEO1() {
        return (PrtAccountEOImpl)getAttributeInternal(PRTACCOUNTEO1);
    }

    /**
     * Sets <code>value</code> as the associated entity PrtAccountEOImpl.
     */
    public void setPrtAccountEO1(PrtAccountEOImpl value) {
        setAttributeInternal(PRTACCOUNTEO1, value);
    }


    /**
     * @param prtDriverInformationPk key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(DBSequence prtDriverInformationPk) {
        return new Key(new Object[]{prtDriverInformationPk});
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        super.create(attributeList);
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
        super.lock();
    }

    
}

package com.sfr.engage.model.queries.uvo;

import java.math.BigDecimal;

import java.sql.Date;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Apr 13 17:32:13 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtInvoiceVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        InvoiceNumber {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getInvoiceNumber();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setInvoiceNumber((String)value);
            }
        }
        ,
        CountryCode {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getCountryCode();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setCountryCode((String)value);
            }
        }
        ,
        CurrencyCode {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getCurrencyCode();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setCurrencyCode((String)value);
            }
        }
        ,
        InvoiceDate {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getInvoiceDate();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setInvoiceDate((Date)value);
            }
        }
        ,
        InvoiceType {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getInvoiceType();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setInvoiceType((String)value);
            }
        }
        ,
        InvoiceSubType {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getInvoiceSubType();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setInvoiceSubType((String)value);
            }
        }
        ,
        InvoiceDueDate {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getInvoiceDueDate();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setInvoiceDueDate((Date)value);
            }
        }
        ,
        Net {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getNet();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setNet((BigDecimal)value);
            }
        }
        ,
        Vat {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getVat();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setVat((BigDecimal)value);
            }
        }
        ,
        TotalAmount {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getTotalAmount();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setTotalAmount((BigDecimal)value);
            }
        }
        ,
        AccountId {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getAccountId();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setAccountId((String)value);
            }
        }
        ,
        InvoiceUcmType {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getInvoiceUcmType();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setInvoiceUcmType((String)value);
            }
        }
        ,
        InvoiceGrouping {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getInvoiceGrouping();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setInvoiceGrouping((String)value);
            }
        }
        ,
        PartnerId {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getPartnerId();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setPartnerId((String)value);
            }
        }
        ,
        CardgroupMainType {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getCardgroupMainType();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setCardgroupMainType((String)value);
            }
        }
        ,
        CardgroupSubType {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getCardgroupSubType();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setCardgroupSubType((String)value);
            }
        }
        ,
        CardgroupSeq {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getCardgroupSeq();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setCardgroupSeq((String)value);
            }
        }
        ,
        InvoicePayRef {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getInvoicePayRef();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setInvoicePayRef((BigDecimal)value);
            }
        }
        ,
        PrtCardPk {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getPrtCardPk();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setPrtCardPk((String)value);
            }
        }
        ,
        Address1 {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getAddress1();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setAddress1((String)value);
            }
        }
        ,
        Address2 {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getAddress2();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setAddress2((String)value);
            }
        }
        ,
        City {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getCity();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setCity((String)value);
            }
        }
        ,
        PostalCode {
            public Object get(PrtInvoiceVORowImpl obj) {
                return obj.getPostalCode();
            }

            public void put(PrtInvoiceVORowImpl obj, Object value) {
                obj.setPostalCode((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtInvoiceVORowImpl object);

        public abstract void put(PrtInvoiceVORowImpl object, Object value);

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
    public static final int INVOICENUMBER = AttributesEnum.InvoiceNumber.index();
    public static final int COUNTRYCODE = AttributesEnum.CountryCode.index();
    public static final int CURRENCYCODE = AttributesEnum.CurrencyCode.index();
    public static final int INVOICEDATE = AttributesEnum.InvoiceDate.index();
    public static final int INVOICETYPE = AttributesEnum.InvoiceType.index();
    public static final int INVOICESUBTYPE = AttributesEnum.InvoiceSubType.index();
    public static final int INVOICEDUEDATE = AttributesEnum.InvoiceDueDate.index();
    public static final int NET = AttributesEnum.Net.index();
    public static final int VAT = AttributesEnum.Vat.index();
    public static final int TOTALAMOUNT = AttributesEnum.TotalAmount.index();
    public static final int ACCOUNTID = AttributesEnum.AccountId.index();
    public static final int INVOICEUCMTYPE = AttributesEnum.InvoiceUcmType.index();
    public static final int INVOICEGROUPING = AttributesEnum.InvoiceGrouping.index();
    public static final int PARTNERID = AttributesEnum.PartnerId.index();
    public static final int CARDGROUPMAINTYPE = AttributesEnum.CardgroupMainType.index();
    public static final int CARDGROUPSUBTYPE = AttributesEnum.CardgroupSubType.index();
    public static final int CARDGROUPSEQ = AttributesEnum.CardgroupSeq.index();
    public static final int INVOICEPAYREF = AttributesEnum.InvoicePayRef.index();
    public static final int PRTCARDPK = AttributesEnum.PrtCardPk.index();
    public static final int ADDRESS1 = AttributesEnum.Address1.index();
    public static final int ADDRESS2 = AttributesEnum.Address2.index();
    public static final int CITY = AttributesEnum.City.index();
    public static final int POSTALCODE = AttributesEnum.PostalCode.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtInvoiceVORowImpl() {
    }

    /**
     * Gets PrtInvoiceEO entity object.
     * @return the PrtInvoiceEO
     */
    public EntityImpl getPrtInvoiceEO() {
        return (EntityImpl)getEntity(0);
    }

    /**
     * Gets the attribute value for INVOICE_NUMBER using the alias name InvoiceNumber.
     * @return the INVOICE_NUMBER
     */
    public String getInvoiceNumber() {
        return (String) getAttributeInternal(INVOICENUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for INVOICE_NUMBER using the alias name InvoiceNumber.
     * @param value value to set the INVOICE_NUMBER
     */
    public void setInvoiceNumber(String value) {
        setAttributeInternal(INVOICENUMBER, value);
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
     * Gets the attribute value for CURRENCY_CODE using the alias name CurrencyCode.
     * @return the CURRENCY_CODE
     */
    public String getCurrencyCode() {
        return (String) getAttributeInternal(CURRENCYCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for CURRENCY_CODE using the alias name CurrencyCode.
     * @param value value to set the CURRENCY_CODE
     */
    public void setCurrencyCode(String value) {
        setAttributeInternal(CURRENCYCODE, value);
    }

    /**
     * Gets the attribute value for INVOICE_DATE using the alias name InvoiceDate.
     * @return the INVOICE_DATE
     */
    public Date getInvoiceDate() {
        return (Date) getAttributeInternal(INVOICEDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for INVOICE_DATE using the alias name InvoiceDate.
     * @param value value to set the INVOICE_DATE
     */
    public void setInvoiceDate(Date value) {
        setAttributeInternal(INVOICEDATE, value);
    }

    /**
     * Gets the attribute value for INVOICE_TYPE using the alias name InvoiceType.
     * @return the INVOICE_TYPE
     */
    public String getInvoiceType() {
        return (String) getAttributeInternal(INVOICETYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for INVOICE_TYPE using the alias name InvoiceType.
     * @param value value to set the INVOICE_TYPE
     */
    public void setInvoiceType(String value) {
        setAttributeInternal(INVOICETYPE, value);
    }

    /**
     * Gets the attribute value for INVOICE_SUB_TYPE using the alias name InvoiceSubType.
     * @return the INVOICE_SUB_TYPE
     */
    public String getInvoiceSubType() {
        return (String) getAttributeInternal(INVOICESUBTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for INVOICE_SUB_TYPE using the alias name InvoiceSubType.
     * @param value value to set the INVOICE_SUB_TYPE
     */
    public void setInvoiceSubType(String value) {
        setAttributeInternal(INVOICESUBTYPE, value);
    }

    /**
     * Gets the attribute value for INVOICE_DUE_DATE using the alias name InvoiceDueDate.
     * @return the INVOICE_DUE_DATE
     */
    public Date getInvoiceDueDate() {
        return (Date) getAttributeInternal(INVOICEDUEDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for INVOICE_DUE_DATE using the alias name InvoiceDueDate.
     * @param value value to set the INVOICE_DUE_DATE
     */
    public void setInvoiceDueDate(Date value) {
        setAttributeInternal(INVOICEDUEDATE, value);
    }

    /**
     * Gets the attribute value for NET using the alias name Net.
     * @return the NET
     */
    public BigDecimal getNet() {
        return (BigDecimal) getAttributeInternal(NET);
    }

    /**
     * Sets <code>value</code> as attribute value for NET using the alias name Net.
     * @param value value to set the NET
     */
    public void setNet(BigDecimal value) {
        setAttributeInternal(NET, value);
    }

    /**
     * Gets the attribute value for VAT using the alias name Vat.
     * @return the VAT
     */
    public BigDecimal getVat() {
        return (BigDecimal) getAttributeInternal(VAT);
    }

    /**
     * Sets <code>value</code> as attribute value for VAT using the alias name Vat.
     * @param value value to set the VAT
     */
    public void setVat(BigDecimal value) {
        setAttributeInternal(VAT, value);
    }

    /**
     * Gets the attribute value for TOTAL_AMOUNT using the alias name TotalAmount.
     * @return the TOTAL_AMOUNT
     */
    public BigDecimal getTotalAmount() {
        return (BigDecimal) getAttributeInternal(TOTALAMOUNT);
    }

    /**
     * Sets <code>value</code> as attribute value for TOTAL_AMOUNT using the alias name TotalAmount.
     * @param value value to set the TOTAL_AMOUNT
     */
    public void setTotalAmount(BigDecimal value) {
        setAttributeInternal(TOTALAMOUNT, value);
    }

    /**
     * Gets the attribute value for ACCOUNT_ID using the alias name AccountId.
     * @return the ACCOUNT_ID
     */
    public String getAccountId() {
        return (String) getAttributeInternal(ACCOUNTID);
    }

    /**
     * Sets <code>value</code> as attribute value for ACCOUNT_ID using the alias name AccountId.
     * @param value value to set the ACCOUNT_ID
     */
    public void setAccountId(String value) {
        setAttributeInternal(ACCOUNTID, value);
    }

    /**
     * Gets the attribute value for INVOICE_UCM_TYPE using the alias name InvoiceUcmType.
     * @return the INVOICE_UCM_TYPE
     */
    public String getInvoiceUcmType() {
        return (String) getAttributeInternal(INVOICEUCMTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for INVOICE_UCM_TYPE using the alias name InvoiceUcmType.
     * @param value value to set the INVOICE_UCM_TYPE
     */
    public void setInvoiceUcmType(String value) {
        setAttributeInternal(INVOICEUCMTYPE, value);
    }

    /**
     * Gets the attribute value for INVOICE_GROUPING using the alias name InvoiceGrouping.
     * @return the INVOICE_GROUPING
     */
    public String getInvoiceGrouping() {
        return (String) getAttributeInternal(INVOICEGROUPING);
    }

    /**
     * Sets <code>value</code> as attribute value for INVOICE_GROUPING using the alias name InvoiceGrouping.
     * @param value value to set the INVOICE_GROUPING
     */
    public void setInvoiceGrouping(String value) {
        setAttributeInternal(INVOICEGROUPING, value);
    }

    /**
     * Gets the attribute value for PARTNER_ID using the alias name PartnerId.
     * @return the PARTNER_ID
     */
    public String getPartnerId() {
        return (String) getAttributeInternal(PARTNERID);
    }

    /**
     * Sets <code>value</code> as attribute value for PARTNER_ID using the alias name PartnerId.
     * @param value value to set the PARTNER_ID
     */
    public void setPartnerId(String value) {
        setAttributeInternal(PARTNERID, value);
    }

    /**
     * Gets the attribute value for CARDGROUP_MAIN_TYPE using the alias name CardgroupMainType.
     * @return the CARDGROUP_MAIN_TYPE
     */
    public String getCardgroupMainType() {
        return (String) getAttributeInternal(CARDGROUPMAINTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for CARDGROUP_MAIN_TYPE using the alias name CardgroupMainType.
     * @param value value to set the CARDGROUP_MAIN_TYPE
     */
    public void setCardgroupMainType(String value) {
        setAttributeInternal(CARDGROUPMAINTYPE, value);
    }

    /**
     * Gets the attribute value for CARDGROUP_SUB_TYPE using the alias name CardgroupSubType.
     * @return the CARDGROUP_SUB_TYPE
     */
    public String getCardgroupSubType() {
        return (String) getAttributeInternal(CARDGROUPSUBTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for CARDGROUP_SUB_TYPE using the alias name CardgroupSubType.
     * @param value value to set the CARDGROUP_SUB_TYPE
     */
    public void setCardgroupSubType(String value) {
        setAttributeInternal(CARDGROUPSUBTYPE, value);
    }

    /**
     * Gets the attribute value for CARDGROUP_SEQ using the alias name CardgroupSeq.
     * @return the CARDGROUP_SEQ
     */
    public String getCardgroupSeq() {
        return (String) getAttributeInternal(CARDGROUPSEQ);
    }

    /**
     * Sets <code>value</code> as attribute value for CARDGROUP_SEQ using the alias name CardgroupSeq.
     * @param value value to set the CARDGROUP_SEQ
     */
    public void setCardgroupSeq(String value) {
        setAttributeInternal(CARDGROUPSEQ, value);
    }

    /**
     * Gets the attribute value for INVOICE_PAY_REF using the alias name InvoicePayRef.
     * @return the INVOICE_PAY_REF
     */
    public BigDecimal getInvoicePayRef() {
        return (BigDecimal) getAttributeInternal(INVOICEPAYREF);
    }

    /**
     * Sets <code>value</code> as attribute value for INVOICE_PAY_REF using the alias name InvoicePayRef.
     * @param value value to set the INVOICE_PAY_REF
     */
    public void setInvoicePayRef(BigDecimal value) {
        setAttributeInternal(INVOICEPAYREF, value);
    }

    /**
     * Gets the attribute value for PRT_CARD_PK using the alias name PrtCardPk.
     * @return the PRT_CARD_PK
     */
    public String getPrtCardPk() {
        return (String) getAttributeInternal(PRTCARDPK);
    }

    /**
     * Sets <code>value</code> as attribute value for PRT_CARD_PK using the alias name PrtCardPk.
     * @param value value to set the PRT_CARD_PK
     */
    public void setPrtCardPk(String value) {
        setAttributeInternal(PRTCARDPK, value);
    }

    /**
     * Gets the attribute value for ADDRESS_1 using the alias name Address1.
     * @return the ADDRESS_1
     */
    public String getAddress1() {
        return (String) getAttributeInternal(ADDRESS1);
    }

    /**
     * Sets <code>value</code> as attribute value for ADDRESS_1 using the alias name Address1.
     * @param value value to set the ADDRESS_1
     */
    public void setAddress1(String value) {
        setAttributeInternal(ADDRESS1, value);
    }

    /**
     * Gets the attribute value for ADDRESS_2 using the alias name Address2.
     * @return the ADDRESS_2
     */
    public String getAddress2() {
        return (String) getAttributeInternal(ADDRESS2);
    }

    /**
     * Sets <code>value</code> as attribute value for ADDRESS_2 using the alias name Address2.
     * @param value value to set the ADDRESS_2
     */
    public void setAddress2(String value) {
        setAttributeInternal(ADDRESS2, value);
    }

    /**
     * Gets the attribute value for CITY using the alias name City.
     * @return the CITY
     */
    public String getCity() {
        return (String) getAttributeInternal(CITY);
    }

    /**
     * Sets <code>value</code> as attribute value for CITY using the alias name City.
     * @param value value to set the CITY
     */
    public void setCity(String value) {
        setAttributeInternal(CITY, value);
    }

    /**
     * Gets the attribute value for POSTAL_CODE using the alias name PostalCode.
     * @return the POSTAL_CODE
     */
    public String getPostalCode() {
        return (String) getAttributeInternal(POSTALCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for POSTAL_CODE using the alias name PostalCode.
     * @param value value to set the POSTAL_CODE
     */
    public void setPostalCode(String value) {
        setAttributeInternal(POSTALCODE, value);
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

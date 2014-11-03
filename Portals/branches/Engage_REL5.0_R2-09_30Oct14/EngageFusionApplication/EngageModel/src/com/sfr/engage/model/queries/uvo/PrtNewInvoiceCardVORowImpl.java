package com.sfr.engage.model.queries.uvo;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Oct 29 16:52:55 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtNewInvoiceCardVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Finalinvoice {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getFinalinvoice();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setFinalinvoice((String)value);
            }
        }
        ,
        InvoicedCard {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getInvoicedCard();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setInvoicedCard((String)value);
            }
        }
        ,
        InvoicingDate {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getInvoicingDate();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setInvoicingDate((Date)value);
            }
        }
        ,
        CountryCode {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getCountryCode();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setCountryCode((String)value);
            }
        }
        ,
        InvoiceDate {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getInvoiceDate();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setInvoiceDate((Date)value);
            }
        }
        ,
        InvoicingDueDate {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getInvoicingDueDate();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setInvoicingDueDate((Date)value);
            }
        }
        ,
        PartnerId {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getPartnerId();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setPartnerId((String)value);
            }
        }
        ,
        AccountId {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getAccountId();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setAccountId((String)value);
            }
        }
        ,
        CardgroupMainType {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getCardgroupMainType();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setCardgroupMainType((String)value);
            }
        }
        ,
        CardgroupSubType {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getCardgroupSubType();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setCardgroupSubType((String)value);
            }
        }
        ,
        CardgroupSeq {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getCardgroupSeq();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setCardgroupSeq((String)value);
            }
        }
        ,
        InvGrossAmt {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getInvGrossAmt();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setInvGrossAmt((Number)value);
            }
        }
        ,
        InvVatAmt {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getInvVatAmt();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setInvVatAmt((Number)value);
            }
        }
        ,
        InvoiceDocType {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getInvoiceDocType();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setInvoiceDocType((String)value);
            }
        }
        ,
        InvoiceLevel {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getInvoiceLevel();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setInvoiceLevel((String)value);
            }
        }
        ,
        netAmount {
            public Object get(PrtNewInvoiceCardVORowImpl obj) {
                return obj.getnetAmount();
            }

            public void put(PrtNewInvoiceCardVORowImpl obj, Object value) {
                obj.setnetAmount((Number)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtNewInvoiceCardVORowImpl object);

        public abstract void put(PrtNewInvoiceCardVORowImpl object, Object value);

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


    public static final int FINALINVOICE = AttributesEnum.Finalinvoice.index();
    public static final int INVOICEDCARD = AttributesEnum.InvoicedCard.index();
    public static final int INVOICINGDATE = AttributesEnum.InvoicingDate.index();
    public static final int COUNTRYCODE = AttributesEnum.CountryCode.index();
    public static final int INVOICEDATE = AttributesEnum.InvoiceDate.index();
    public static final int INVOICINGDUEDATE = AttributesEnum.InvoicingDueDate.index();
    public static final int PARTNERID = AttributesEnum.PartnerId.index();
    public static final int ACCOUNTID = AttributesEnum.AccountId.index();
    public static final int CARDGROUPMAINTYPE = AttributesEnum.CardgroupMainType.index();
    public static final int CARDGROUPSUBTYPE = AttributesEnum.CardgroupSubType.index();
    public static final int CARDGROUPSEQ = AttributesEnum.CardgroupSeq.index();
    public static final int INVGROSSAMT = AttributesEnum.InvGrossAmt.index();
    public static final int INVVATAMT = AttributesEnum.InvVatAmt.index();
    public static final int INVOICEDOCTYPE = AttributesEnum.InvoiceDocType.index();
    public static final int INVOICELEVEL = AttributesEnum.InvoiceLevel.index();
    public static final int NETAMOUNT = AttributesEnum.netAmount.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtNewInvoiceCardVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Finalinvoice.
     * @return the Finalinvoice
     */
    public String getFinalinvoice() {
        return (String) getAttributeInternal(FINALINVOICE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Finalinvoice.
     * @param value value to set the  Finalinvoice
     */
    public void setFinalinvoice(String value) {
        setAttributeInternal(FINALINVOICE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute InvoicedCard.
     * @return the InvoicedCard
     */
    public String getInvoicedCard() {
        return (String) getAttributeInternal(INVOICEDCARD);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvoicedCard.
     * @param value value to set the  InvoicedCard
     */
    public void setInvoicedCard(String value) {
        setAttributeInternal(INVOICEDCARD, value);
    }

    /**
     * Gets the attribute value for the calculated attribute InvoicingDate.
     * @return the InvoicingDate
     */
    public Date getInvoicingDate() {
        return (Date) getAttributeInternal(INVOICINGDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvoicingDate.
     * @param value value to set the  InvoicingDate
     */
    public void setInvoicingDate(Date value) {
        setAttributeInternal(INVOICINGDATE, value);
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
     * Gets the attribute value for the calculated attribute InvoiceDate.
     * @return the InvoiceDate
     */
    public Date getInvoiceDate() {
        return (Date) getAttributeInternal(INVOICEDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvoiceDate.
     * @param value value to set the  InvoiceDate
     */
    public void setInvoiceDate(Date value) {
        setAttributeInternal(INVOICEDATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute InvoicingDueDate.
     * @return the InvoicingDueDate
     */
    public Date getInvoicingDueDate() {
        return (Date) getAttributeInternal(INVOICINGDUEDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvoicingDueDate.
     * @param value value to set the  InvoicingDueDate
     */
    public void setInvoicingDueDate(Date value) {
        setAttributeInternal(INVOICINGDUEDATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PartnerId.
     * @return the PartnerId
     */
    public String getPartnerId() {
        return (String) getAttributeInternal(PARTNERID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PartnerId.
     * @param value value to set the  PartnerId
     */
    public void setPartnerId(String value) {
        setAttributeInternal(PARTNERID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute AccountId.
     * @return the AccountId
     */
    public String getAccountId() {
        return (String) getAttributeInternal(ACCOUNTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute AccountId.
     * @param value value to set the  AccountId
     */
    public void setAccountId(String value) {
        setAttributeInternal(ACCOUNTID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CardgroupMainType.
     * @return the CardgroupMainType
     */
    public String getCardgroupMainType() {
        return (String) getAttributeInternal(CARDGROUPMAINTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CardgroupMainType.
     * @param value value to set the  CardgroupMainType
     */
    public void setCardgroupMainType(String value) {
        setAttributeInternal(CARDGROUPMAINTYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CardgroupSubType.
     * @return the CardgroupSubType
     */
    public String getCardgroupSubType() {
        return (String) getAttributeInternal(CARDGROUPSUBTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CardgroupSubType.
     * @param value value to set the  CardgroupSubType
     */
    public void setCardgroupSubType(String value) {
        setAttributeInternal(CARDGROUPSUBTYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CardgroupSeq.
     * @return the CardgroupSeq
     */
    public String getCardgroupSeq() {
        return (String) getAttributeInternal(CARDGROUPSEQ);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CardgroupSeq.
     * @param value value to set the  CardgroupSeq
     */
    public void setCardgroupSeq(String value) {
        setAttributeInternal(CARDGROUPSEQ, value);
    }

    /**
     * Gets the attribute value for the calculated attribute InvGrossAmt.
     * @return the InvGrossAmt
     */
    public Number getInvGrossAmt() {
        return (Number) getAttributeInternal(INVGROSSAMT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvGrossAmt.
     * @param value value to set the  InvGrossAmt
     */
    public void setInvGrossAmt(Number value) {
        setAttributeInternal(INVGROSSAMT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute InvVatAmt.
     * @return the InvVatAmt
     */
    public Number getInvVatAmt() {
        return (Number) getAttributeInternal(INVVATAMT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvVatAmt.
     * @param value value to set the  InvVatAmt
     */
    public void setInvVatAmt(Number value) {
        setAttributeInternal(INVVATAMT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute InvoiceDocType.
     * @return the InvoiceDocType
     */
    public String getInvoiceDocType() {
        return (String) getAttributeInternal(INVOICEDOCTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvoiceDocType.
     * @param value value to set the  InvoiceDocType
     */
    public void setInvoiceDocType(String value) {
        setAttributeInternal(INVOICEDOCTYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute InvoiceLevel.
     * @return the InvoiceLevel
     */
    public String getInvoiceLevel() {
        return (String) getAttributeInternal(INVOICELEVEL);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvoiceLevel.
     * @param value value to set the  InvoiceLevel
     */
    public void setInvoiceLevel(String value) {
        setAttributeInternal(INVOICELEVEL, value);
    }

    /**
     * Gets the attribute value for the calculated attribute netAmount.
     * @return the netAmount
     */
    public Number getnetAmount() {
            //TODO : To be fetched from PALS
            oracle.jbo.domain.Number num;
            float net = 0.0f;
            if (getInvGrossAmt() != null && getInvVatAmt() != null) {
                net = getInvGrossAmt().floatValue() - getInvVatAmt().floatValue();
                num = new oracle.jbo.domain.Number(net);
            } else {
                num = (Number) getAttributeInternal(NETAMOUNT);
            }
            return num;
        }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute netAmount.
     * @param value value to set the  netAmount
     */
    public void setnetAmount(Number value) {
        setAttributeInternal(NETAMOUNT, value);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception {
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
    protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}

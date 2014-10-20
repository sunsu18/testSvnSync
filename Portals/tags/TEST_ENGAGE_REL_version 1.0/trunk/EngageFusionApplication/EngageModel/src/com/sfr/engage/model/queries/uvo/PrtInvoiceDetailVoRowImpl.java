package com.sfr.engage.model.queries.uvo;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Jun 07 12:24:17 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtInvoiceDetailVoRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        SumPrdQuantity {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getSumPrdQuantity();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setSumPrdQuantity((Number)value);
            }
        },
        Card1Id {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getCard1Id();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setCard1Id((String)value);
            }
        },
        CardEmbossNum {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getCardEmbossNum();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setCardEmbossNum((String)value);
            }
        },
        CardTextline2 {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getCardTextline2();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setCardTextline2((String)value);
            }
        },
        PartnerId {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getPartnerId();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setPartnerId((String)value);
            }
        },
        CountryCode {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getCountryCode();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setCountryCode((String)value);
            }
        },
        InvoiceNumber {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getInvoiceNumber();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setInvoiceNumber((String)value);
            }
        },
        CollectiveInvoiceNumber {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getCollectiveInvoiceNumber();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setCollectiveInvoiceNumber((String)value);
            }
        },
        InvoiceDate {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getInvoiceDate();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setInvoiceDate((Date)value);
            }
        },
        AccountId {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getAccountId();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setAccountId((String)value);
            }
        },
        InvoicingDueDate {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getInvoicingDueDate();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setInvoicingDueDate((Date)value);
            }
        },
        InvTotalGrossAmt {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getInvTotalGrossAmt();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setInvTotalGrossAmt((Number)value);
            }
        },
        InvoicingDate {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getInvoicingDate();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setInvoicingDate((Date)value);
            }
        },
        InvTotalVatAmt {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getInvTotalVatAmt();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setInvTotalVatAmt((Number)value);
            }
        },
        InvoicedCard {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getInvoicedCard();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setInvoicedCard((String)value);
            }
        },
        InvNetAmount {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getInvNetAmount();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setInvNetAmount((Number)value);
            }
        },
        FuelingQty {
            public Object get(PrtInvoiceDetailVoRowImpl obj) {
                return obj.getFuelingQty();
            }

            public void put(PrtInvoiceDetailVoRowImpl obj, Object value) {
                obj.setFuelingQty((Float)value);
            }
        };
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtInvoiceDetailVoRowImpl object);

        public abstract void put(PrtInvoiceDetailVoRowImpl object, Object value);

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


    public static final int SUMPRDQUANTITY = AttributesEnum.SumPrdQuantity.index();
    public static final int CARD1ID = AttributesEnum.Card1Id.index();
    public static final int CARDEMBOSSNUM = AttributesEnum.CardEmbossNum.index();
    public static final int CARDTEXTLINE2 = AttributesEnum.CardTextline2.index();
    public static final int PARTNERID = AttributesEnum.PartnerId.index();
    public static final int COUNTRYCODE = AttributesEnum.CountryCode.index();
    public static final int INVOICENUMBER = AttributesEnum.InvoiceNumber.index();
    public static final int COLLECTIVEINVOICENUMBER = AttributesEnum.CollectiveInvoiceNumber.index();
    public static final int INVOICEDATE = AttributesEnum.InvoiceDate.index();
    public static final int ACCOUNTID = AttributesEnum.AccountId.index();
    public static final int INVOICINGDUEDATE = AttributesEnum.InvoicingDueDate.index();
    public static final int INVTOTALGROSSAMT = AttributesEnum.InvTotalGrossAmt.index();
    public static final int INVOICINGDATE = AttributesEnum.InvoicingDate.index();
    public static final int INVTOTALVATAMT = AttributesEnum.InvTotalVatAmt.index();
    public static final int INVOICEDCARD = AttributesEnum.InvoicedCard.index();
    public static final int INVNETAMOUNT = AttributesEnum.InvNetAmount.index();
    public static final int FUELINGQTY = AttributesEnum.FuelingQty.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtInvoiceDetailVoRowImpl() {
    }


    /**
     * Gets the attribute value for the calculated attribute Card1Id.
     * @return the Card1Id
     */
    public String getCard1Id() {
        return (String)getAttributeInternal(CARD1ID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Card1Id.
     * @param value value to set the  Card1Id
     */
    public void setCard1Id(String value) {
        setAttributeInternal(CARD1ID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CardEmbossNum.
     * @return the CardEmbossNum
     */
    public String getCardEmbossNum() {
        return (String)getAttributeInternal(CARDEMBOSSNUM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CardEmbossNum.
     * @param value value to set the  CardEmbossNum
     */
    public void setCardEmbossNum(String value) {
        setAttributeInternal(CARDEMBOSSNUM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CardTextline2.
     * @return the CardTextline2
     */
    public String getCardTextline2() {
        return (String)getAttributeInternal(CARDTEXTLINE2);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CardTextline2.
     * @param value value to set the  CardTextline2
     */
    public void setCardTextline2(String value) {
        setAttributeInternal(CARDTEXTLINE2, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PartnerId.
     * @return the PartnerId
     */
    public String getPartnerId() {
        return (String)getAttributeInternal(PARTNERID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PartnerId.
     * @param value value to set the  PartnerId
     */
    public void setPartnerId(String value) {
        setAttributeInternal(PARTNERID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CountryCode.
     * @return the CountryCode
     */
    public String getCountryCode() {
        return (String)getAttributeInternal(COUNTRYCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CountryCode.
     * @param value value to set the  CountryCode
     */
    public void setCountryCode(String value) {
        setAttributeInternal(COUNTRYCODE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute InvoiceNumber.
     * @return the InvoiceNumber
     */
    public String getInvoiceNumber() {
        return (String)getAttributeInternal(INVOICENUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvoiceNumber.
     * @param value value to set the  InvoiceNumber
     */
    public void setInvoiceNumber(String value) {
        setAttributeInternal(INVOICENUMBER, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CollectiveInvoiceNumber.
     * @return the CollectiveInvoiceNumber
     */
    public String getCollectiveInvoiceNumber() {
        return (String)getAttributeInternal(COLLECTIVEINVOICENUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CollectiveInvoiceNumber.
     * @param value value to set the  CollectiveInvoiceNumber
     */
    public void setCollectiveInvoiceNumber(String value) {
        setAttributeInternal(COLLECTIVEINVOICENUMBER, value);
    }

    /**
     * Gets the attribute value for the calculated attribute InvoiceDate.
     * @return the InvoiceDate
     */
    public Date getInvoiceDate() {
        return (Date)getAttributeInternal(INVOICEDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvoiceDate.
     * @param value value to set the  InvoiceDate
     */
    public void setInvoiceDate(Date value) {
        setAttributeInternal(INVOICEDATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute AccountId.
     * @return the AccountId
     */
    public String getAccountId() {
        return (String)getAttributeInternal(ACCOUNTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute AccountId.
     * @param value value to set the  AccountId
     */
    public void setAccountId(String value) {
        setAttributeInternal(ACCOUNTID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute InvoicingDueDate.
     * @return the InvoicingDueDate
     */
    public Date getInvoicingDueDate() {
        return (Date)getAttributeInternal(INVOICINGDUEDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvoicingDueDate.
     * @param value value to set the  InvoicingDueDate
     */
    public void setInvoicingDueDate(Date value) {
        setAttributeInternal(INVOICINGDUEDATE, value);
    }


    /**
     * Gets the attribute value for the calculated attribute InvTotalGrossAmt.
     * @return the InvTotalGrossAmt
     */
    public Number getInvTotalGrossAmt() {
        return (Number)getAttributeInternal(INVTOTALGROSSAMT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvTotalGrossAmt.
     * @param value value to set the  InvTotalGrossAmt
     */
    public void setInvTotalGrossAmt(Number value) {
        setAttributeInternal(INVTOTALGROSSAMT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute InvoicingDate.
     * @return the InvoicingDate
     */
    public Date getInvoicingDate() {
        return (Date)getAttributeInternal(INVOICINGDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvoicingDate.
     * @param value value to set the  InvoicingDate
     */
    public void setInvoicingDate(Date value) {
        setAttributeInternal(INVOICINGDATE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute InvTotalVatAmt.
     * @return the InvTotalVatAmt
     */
    public Number getInvTotalVatAmt() {
        return (Number)getAttributeInternal(INVTOTALVATAMT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvTotalVatAmt.
     * @param value value to set the  InvTotalVatAmt
     */
    public void setInvTotalVatAmt(Number value) {
        setAttributeInternal(INVTOTALVATAMT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute InvoicedCard.
     * @return the InvoicedCard
     */
    public String getInvoicedCard() {
        return (String)getAttributeInternal(INVOICEDCARD);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvoicedCard.
     * @param value value to set the  InvoicedCard
     */
    public void setInvoicedCard(String value) {
        setAttributeInternal(INVOICEDCARD, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SumPrdQuantity.
     * @return the SumPrdQuantity
     */
    public Number getSumPrdQuantity() {
        return (Number)getAttributeInternal(SUMPRDQUANTITY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SumPrdQuantity.
     * @param value value to set the  SumPrdQuantity
     */
    public void setSumPrdQuantity(Number value) {
        setAttributeInternal(SUMPRDQUANTITY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute InvNetAmount.
     * @return the InvNetAmount
     */
    public Number getInvNetAmount() {

        //TODO : To be fetched from PALS
        oracle.jbo.domain.Number num;
        float net = 0.0f;
        if (getInvTotalGrossAmt() != null && getInvTotalVatAmt() != null) {
            net = getInvTotalGrossAmt().floatValue() - getInvTotalVatAmt().floatValue();
            num = new oracle.jbo.domain.Number(net);
        } else {
            num = (Number)getAttributeInternal(INVNETAMOUNT);
        }
        return num;
    }


    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute InvNetAmount.
     * @param value value to set the  InvNetAmount
     */
    public void setInvNetAmount(Number value) {
        setAttributeInternal(INVNETAMOUNT, value);
    }


    /**
     * Gets the attribute value for the calculated attribute FuelingQty.
     * @return the FuelingQty
     */
    public Float getFuelingQty() {
        Float result = 0.0f;
        if (getSumPrdQuantity() != null) {
            result = Float.parseFloat(getSumPrdQuantity().toString().trim());
        }
        return result;

    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute FuelingQty.
     * @param value value to set the  FuelingQty
     */
    public void setFuelingQty(Float value) {
        setAttributeInternal(FUELINGQTY, value);
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

package com.sfr.engage.model.entities;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Apr 30 15:08:13 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtCardTransactionHeaderEOImpl extends EntityImpl {
    private static EntityDefImpl mDefinitionObject;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        DeltaTimestamp {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getDeltaTimestamp();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setDeltaTimestamp((Timestamp)value);
            }
        }
        ,
        LastUpdated {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getLastUpdated();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setLastUpdated((Date)value);
            }
        }
        ,
        UrefTransactionId {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getUrefTransactionId();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setUrefTransactionId((String)value);
            }
        }
        ,
        PalsCountryCode {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getPalsCountryCode();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setPalsCountryCode((String)value);
            }
        }
        ,
        PurchaseCountryCode {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getPurchaseCountryCode();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setPurchaseCountryCode((String)value);
            }
        }
        ,
        OrderId {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getOrderId();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setOrderId((String)value);
            }
        }
        ,
        PrelimId {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getPrelimId();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setPrelimId((String)value);
            }
        }
        ,
        Card1Id {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getCard1Id();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setCard1Id((String)value);
            }
        }
        ,
        Card2Id {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getCard2Id();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setCard2Id((String)value);
            }
        }
        ,
        CardId2Info {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getCardId2Info();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setCardId2Info((String)value);
            }
        }
        ,
        OdometerPortal {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getOdometerPortal();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setOdometerPortal((String)value);
            }
        }
        ,
        Odometer {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getOdometer();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setOdometer((String)value);
            }
        }
        ,
        TransactionDt {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getTransactionDt();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setTransactionDt((Date)value);
            }
        }
        ,
        TransactionTime {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getTransactionTime();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setTransactionTime((Timestamp)value);
            }
        }
        ,
        StationName {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getStationName();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setStationName((String)value);
            }
        }
        ,
        StationGroupId {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getStationGroupId();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setStationGroupId((String)value);
            }
        }
        ,
        StationGroupName {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getStationGroupName();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setStationGroupName((String)value);
            }
        }
        ,
        IccInvoiceNumber {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getIccInvoiceNumber();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setIccInvoiceNumber((String)value);
            }
        }
        ,
        InvoiceNumberNonCollective {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getInvoiceNumberNonCollective();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setInvoiceNumberNonCollective((String)value);
            }
        }
        ,
        InvoicingDate {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getInvoicingDate();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setInvoicingDate((Date)value);
            }
        }
        ,
        RecieptNo {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getRecieptNo();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setRecieptNo((String)value);
            }
        }
        ,
        PurchaseCurrency {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getPurchaseCurrency();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setPurchaseCurrency((String)value);
            }
        }
        ,
        ExchangeRate {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getExchangeRate();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setExchangeRate((Number)value);
            }
        }
        ,
        IccYn {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getIccYn();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setIccYn((String)value);
            }
        }
        ,
        TransactionType {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getTransactionType();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setTransactionType((String)value);
            }
        }
        ,
        PrelimStatusCode {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getPrelimStatusCode();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setPrelimStatusCode((String)value);
            }
        }
        ,
        InvoiceNumberCollective {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getInvoiceNumberCollective();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setInvoiceNumberCollective((String)value);
            }
        }
        ,
        Ksid {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getKsid();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setKsid((String)value);
            }
        }
        ,
        PartnerId {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getPartnerId();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setPartnerId((String)value);
            }
        }
        ,
        AccountId {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getAccountId();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setAccountId((String)value);
            }
        }
        ,
        CardgroupMainType {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getCardgroupMainType();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setCardgroupMainType((String)value);
            }
        }
        ,
        CardgroupSubType {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getCardgroupSubType();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setCardgroupSubType((String)value);
            }
        }
        ,
        CardgroupSeq {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getCardgroupSeq();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setCardgroupSeq((String)value);
            }
        }
        ,
        Terminal {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getTerminal();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setTerminal((String)value);
            }
        }
        ,
        ModifiedBy {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getModifiedBy();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setModifiedBy((String)value);
            }
        }
        ,
        LastModifiedDate {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getLastModifiedDate();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setLastModifiedDate((Date)value);
            }
        }
        ,
        PreviousOdometer {
            public Object get(PrtCardTransactionHeaderEOImpl obj) {
                return obj.getPreviousOdometer();
            }

            public void put(PrtCardTransactionHeaderEOImpl obj, Object value) {
                obj.setPreviousOdometer((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtCardTransactionHeaderEOImpl object);

        public abstract void put(PrtCardTransactionHeaderEOImpl object,
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
    public static final int DELTATIMESTAMP = AttributesEnum.DeltaTimestamp.index();
    public static final int LASTUPDATED = AttributesEnum.LastUpdated.index();
    public static final int UREFTRANSACTIONID = AttributesEnum.UrefTransactionId.index();
    public static final int PALSCOUNTRYCODE = AttributesEnum.PalsCountryCode.index();
    public static final int PURCHASECOUNTRYCODE = AttributesEnum.PurchaseCountryCode.index();
    public static final int ORDERID = AttributesEnum.OrderId.index();
    public static final int PRELIMID = AttributesEnum.PrelimId.index();
    public static final int CARD1ID = AttributesEnum.Card1Id.index();
    public static final int CARD2ID = AttributesEnum.Card2Id.index();
    public static final int CARDID2INFO = AttributesEnum.CardId2Info.index();
    public static final int ODOMETERPORTAL = AttributesEnum.OdometerPortal.index();
    public static final int ODOMETER = AttributesEnum.Odometer.index();
    public static final int TRANSACTIONDT = AttributesEnum.TransactionDt.index();
    public static final int TRANSACTIONTIME = AttributesEnum.TransactionTime.index();
    public static final int STATIONNAME = AttributesEnum.StationName.index();
    public static final int STATIONGROUPID = AttributesEnum.StationGroupId.index();
    public static final int STATIONGROUPNAME = AttributesEnum.StationGroupName.index();
    public static final int ICCINVOICENUMBER = AttributesEnum.IccInvoiceNumber.index();
    public static final int INVOICENUMBERNONCOLLECTIVE = AttributesEnum.InvoiceNumberNonCollective.index();
    public static final int INVOICINGDATE = AttributesEnum.InvoicingDate.index();
    public static final int RECIEPTNO = AttributesEnum.RecieptNo.index();
    public static final int PURCHASECURRENCY = AttributesEnum.PurchaseCurrency.index();
    public static final int EXCHANGERATE = AttributesEnum.ExchangeRate.index();
    public static final int ICCYN = AttributesEnum.IccYn.index();
    public static final int TRANSACTIONTYPE = AttributesEnum.TransactionType.index();
    public static final int PRELIMSTATUSCODE = AttributesEnum.PrelimStatusCode.index();
    public static final int INVOICENUMBERCOLLECTIVE = AttributesEnum.InvoiceNumberCollective.index();
    public static final int KSID = AttributesEnum.Ksid.index();
    public static final int PARTNERID = AttributesEnum.PartnerId.index();
    public static final int ACCOUNTID = AttributesEnum.AccountId.index();
    public static final int CARDGROUPMAINTYPE = AttributesEnum.CardgroupMainType.index();
    public static final int CARDGROUPSUBTYPE = AttributesEnum.CardgroupSubType.index();
    public static final int CARDGROUPSEQ = AttributesEnum.CardgroupSeq.index();
    public static final int TERMINAL = AttributesEnum.Terminal.index();
    public static final int MODIFIEDBY = AttributesEnum.ModifiedBy.index();
    public static final int LASTMODIFIEDDATE = AttributesEnum.LastModifiedDate.index();
    public static final int PREVIOUSODOMETER = AttributesEnum.PreviousOdometer.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtCardTransactionHeaderEOImpl() {
    }

    /**
     * Gets the attribute value for DeltaTimestamp, using the alias name DeltaTimestamp.
     * @return the DeltaTimestamp
     */
    public Timestamp getDeltaTimestamp() {
        return (Timestamp)getAttributeInternal(DELTATIMESTAMP);
    }

    /**
     * Sets <code>value</code> as the attribute value for DeltaTimestamp.
     * @param value value to set the DeltaTimestamp
     */
    public void setDeltaTimestamp(Timestamp value) {
        setAttributeInternal(DELTATIMESTAMP, value);
    }

    /**
     * Gets the attribute value for LastUpdated, using the alias name LastUpdated.
     * @return the LastUpdated
     */
    public Date getLastUpdated() {
        return (Date)getAttributeInternal(LASTUPDATED);
    }

    /**
     * Sets <code>value</code> as the attribute value for LastUpdated.
     * @param value value to set the LastUpdated
     */
    public void setLastUpdated(Date value) {
        setAttributeInternal(LASTUPDATED, value);
    }

    /**
     * Gets the attribute value for UrefTransactionId, using the alias name UrefTransactionId.
     * @return the UrefTransactionId
     */
    public String getUrefTransactionId() {
        return (String)getAttributeInternal(UREFTRANSACTIONID);
    }

    /**
     * Sets <code>value</code> as the attribute value for UrefTransactionId.
     * @param value value to set the UrefTransactionId
     */
    public void setUrefTransactionId(String value) {
        setAttributeInternal(UREFTRANSACTIONID, value);
    }

    /**
     * Gets the attribute value for PalsCountryCode, using the alias name PalsCountryCode.
     * @return the PalsCountryCode
     */
    public String getPalsCountryCode() {
        return (String)getAttributeInternal(PALSCOUNTRYCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for PalsCountryCode.
     * @param value value to set the PalsCountryCode
     */
    public void setPalsCountryCode(String value) {
        setAttributeInternal(PALSCOUNTRYCODE, value);
    }

    /**
     * Gets the attribute value for PurchaseCountryCode, using the alias name PurchaseCountryCode.
     * @return the PurchaseCountryCode
     */
    public String getPurchaseCountryCode() {
        return (String)getAttributeInternal(PURCHASECOUNTRYCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for PurchaseCountryCode.
     * @param value value to set the PurchaseCountryCode
     */
    public void setPurchaseCountryCode(String value) {
        setAttributeInternal(PURCHASECOUNTRYCODE, value);
    }

    /**
     * Gets the attribute value for OrderId, using the alias name OrderId.
     * @return the OrderId
     */
    public String getOrderId() {
        return (String)getAttributeInternal(ORDERID);
    }

    /**
     * Sets <code>value</code> as the attribute value for OrderId.
     * @param value value to set the OrderId
     */
    public void setOrderId(String value) {
        setAttributeInternal(ORDERID, value);
    }

    /**
     * Gets the attribute value for PrelimId, using the alias name PrelimId.
     * @return the PrelimId
     */
    public String getPrelimId() {
        return (String)getAttributeInternal(PRELIMID);
    }

    /**
     * Sets <code>value</code> as the attribute value for PrelimId.
     * @param value value to set the PrelimId
     */
    public void setPrelimId(String value) {
        setAttributeInternal(PRELIMID, value);
    }

    /**
     * Gets the attribute value for Card1Id, using the alias name Card1Id.
     * @return the Card1Id
     */
    public String getCard1Id() {
        return (String)getAttributeInternal(CARD1ID);
    }

    /**
     * Sets <code>value</code> as the attribute value for Card1Id.
     * @param value value to set the Card1Id
     */
    public void setCard1Id(String value) {
        setAttributeInternal(CARD1ID, value);
    }

    /**
     * Gets the attribute value for Card2Id, using the alias name Card2Id.
     * @return the Card2Id
     */
    public String getCard2Id() {
        return (String)getAttributeInternal(CARD2ID);
    }

    /**
     * Sets <code>value</code> as the attribute value for Card2Id.
     * @param value value to set the Card2Id
     */
    public void setCard2Id(String value) {
        setAttributeInternal(CARD2ID, value);
    }

    /**
     * Gets the attribute value for CardId2Info, using the alias name CardId2Info.
     * @return the CardId2Info
     */
    public String getCardId2Info() {
        return (String)getAttributeInternal(CARDID2INFO);
    }

    /**
     * Sets <code>value</code> as the attribute value for CardId2Info.
     * @param value value to set the CardId2Info
     */
    public void setCardId2Info(String value) {
        setAttributeInternal(CARDID2INFO, value);
    }

    /**
     * Gets the attribute value for OdometerPortal, using the alias name OdometerPortal.
     * @return the OdometerPortal
     */
    public String getOdometerPortal() {
        return (String)getAttributeInternal(ODOMETERPORTAL);
    }

    /**
     * Sets <code>value</code> as the attribute value for OdometerPortal.
     * @param value value to set the OdometerPortal
     */
    public void setOdometerPortal(String value) {
        setAttributeInternal(ODOMETERPORTAL, value);
    }

    /**
     * Gets the attribute value for Odometer, using the alias name Odometer.
     * @return the Odometer
     */
    public String getOdometer() {
        return (String)getAttributeInternal(ODOMETER);
    }

    /**
     * Sets <code>value</code> as the attribute value for Odometer.
     * @param value value to set the Odometer
     */
    public void setOdometer(String value) {
        setAttributeInternal(ODOMETER, value);
    }

    /**
     * Gets the attribute value for TransactionDt, using the alias name TransactionDt.
     * @return the TransactionDt
     */
    public Date getTransactionDt() {
        return (Date)getAttributeInternal(TRANSACTIONDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for TransactionDt.
     * @param value value to set the TransactionDt
     */
    public void setTransactionDt(Date value) {
        setAttributeInternal(TRANSACTIONDT, value);
    }

    /**
     * Gets the attribute value for TransactionTime, using the alias name TransactionTime.
     * @return the TransactionTime
     */
    public Timestamp getTransactionTime() {
        return (Timestamp)getAttributeInternal(TRANSACTIONTIME);
    }

    /**
     * Sets <code>value</code> as the attribute value for TransactionTime.
     * @param value value to set the TransactionTime
     */
    public void setTransactionTime(Timestamp value) {
        setAttributeInternal(TRANSACTIONTIME, value);
    }

    /**
     * Gets the attribute value for StationName, using the alias name StationName.
     * @return the StationName
     */
    public String getStationName() {
        return (String)getAttributeInternal(STATIONNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for StationName.
     * @param value value to set the StationName
     */
    public void setStationName(String value) {
        setAttributeInternal(STATIONNAME, value);
    }

    /**
     * Gets the attribute value for StationGroupId, using the alias name StationGroupId.
     * @return the StationGroupId
     */
    public String getStationGroupId() {
        return (String)getAttributeInternal(STATIONGROUPID);
    }

    /**
     * Sets <code>value</code> as the attribute value for StationGroupId.
     * @param value value to set the StationGroupId
     */
    public void setStationGroupId(String value) {
        setAttributeInternal(STATIONGROUPID, value);
    }

    /**
     * Gets the attribute value for StationGroupName, using the alias name StationGroupName.
     * @return the StationGroupName
     */
    public String getStationGroupName() {
        return (String)getAttributeInternal(STATIONGROUPNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for StationGroupName.
     * @param value value to set the StationGroupName
     */
    public void setStationGroupName(String value) {
        setAttributeInternal(STATIONGROUPNAME, value);
    }

    /**
     * Gets the attribute value for IccInvoiceNumber, using the alias name IccInvoiceNumber.
     * @return the IccInvoiceNumber
     */
    public String getIccInvoiceNumber() {
        return (String)getAttributeInternal(ICCINVOICENUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for IccInvoiceNumber.
     * @param value value to set the IccInvoiceNumber
     */
    public void setIccInvoiceNumber(String value) {
        setAttributeInternal(ICCINVOICENUMBER, value);
    }

    /**
     * Gets the attribute value for InvoiceNumberNonCollective, using the alias name InvoiceNumberNonCollective.
     * @return the InvoiceNumberNonCollective
     */
    public String getInvoiceNumberNonCollective() {
        return (String)getAttributeInternal(INVOICENUMBERNONCOLLECTIVE);
    }

    /**
     * Sets <code>value</code> as the attribute value for InvoiceNumberNonCollective.
     * @param value value to set the InvoiceNumberNonCollective
     */
    public void setInvoiceNumberNonCollective(String value) {
        setAttributeInternal(INVOICENUMBERNONCOLLECTIVE, value);
    }

    /**
     * Gets the attribute value for InvoicingDate, using the alias name InvoicingDate.
     * @return the InvoicingDate
     */
    public Date getInvoicingDate() {
        return (Date)getAttributeInternal(INVOICINGDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for InvoicingDate.
     * @param value value to set the InvoicingDate
     */
    public void setInvoicingDate(Date value) {
        setAttributeInternal(INVOICINGDATE, value);
    }

    /**
     * Gets the attribute value for RecieptNo, using the alias name RecieptNo.
     * @return the RecieptNo
     */
    public String getRecieptNo() {
        return (String)getAttributeInternal(RECIEPTNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for RecieptNo.
     * @param value value to set the RecieptNo
     */
    public void setRecieptNo(String value) {
        setAttributeInternal(RECIEPTNO, value);
    }

    /**
     * Gets the attribute value for PurchaseCurrency, using the alias name PurchaseCurrency.
     * @return the PurchaseCurrency
     */
    public String getPurchaseCurrency() {
        return (String)getAttributeInternal(PURCHASECURRENCY);
    }

    /**
     * Sets <code>value</code> as the attribute value for PurchaseCurrency.
     * @param value value to set the PurchaseCurrency
     */
    public void setPurchaseCurrency(String value) {
        setAttributeInternal(PURCHASECURRENCY, value);
    }

    /**
     * Gets the attribute value for ExchangeRate, using the alias name ExchangeRate.
     * @return the ExchangeRate
     */
    public Number getExchangeRate() {
        return (Number)getAttributeInternal(EXCHANGERATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for ExchangeRate.
     * @param value value to set the ExchangeRate
     */
    public void setExchangeRate(Number value) {
        setAttributeInternal(EXCHANGERATE, value);
    }

    /**
     * Gets the attribute value for IccYn, using the alias name IccYn.
     * @return the IccYn
     */
    public String getIccYn() {
        return (String)getAttributeInternal(ICCYN);
    }

    /**
     * Sets <code>value</code> as the attribute value for IccYn.
     * @param value value to set the IccYn
     */
    public void setIccYn(String value) {
        setAttributeInternal(ICCYN, value);
    }

    /**
     * Gets the attribute value for TransactionType, using the alias name TransactionType.
     * @return the TransactionType
     */
    public String getTransactionType() {
        return (String)getAttributeInternal(TRANSACTIONTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for TransactionType.
     * @param value value to set the TransactionType
     */
    public void setTransactionType(String value) {
        setAttributeInternal(TRANSACTIONTYPE, value);
    }

    /**
     * Gets the attribute value for PrelimStatusCode, using the alias name PrelimStatusCode.
     * @return the PrelimStatusCode
     */
    public String getPrelimStatusCode() {
        return (String)getAttributeInternal(PRELIMSTATUSCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for PrelimStatusCode.
     * @param value value to set the PrelimStatusCode
     */
    public void setPrelimStatusCode(String value) {
        setAttributeInternal(PRELIMSTATUSCODE, value);
    }

    /**
     * Gets the attribute value for InvoiceNumberCollective, using the alias name InvoiceNumberCollective.
     * @return the InvoiceNumberCollective
     */
    public String getInvoiceNumberCollective() {
        return (String)getAttributeInternal(INVOICENUMBERCOLLECTIVE);
    }

    /**
     * Sets <code>value</code> as the attribute value for InvoiceNumberCollective.
     * @param value value to set the InvoiceNumberCollective
     */
    public void setInvoiceNumberCollective(String value) {
        setAttributeInternal(INVOICENUMBERCOLLECTIVE, value);
    }

    /**
     * Gets the attribute value for Ksid, using the alias name Ksid.
     * @return the Ksid
     */
    public String getKsid() {
        return (String)getAttributeInternal(KSID);
    }

    /**
     * Sets <code>value</code> as the attribute value for Ksid.
     * @param value value to set the Ksid
     */
    public void setKsid(String value) {
        setAttributeInternal(KSID, value);
    }

    /**
     * Gets the attribute value for PartnerId, using the alias name PartnerId.
     * @return the PartnerId
     */
    public String getPartnerId() {
        return (String)getAttributeInternal(PARTNERID);
    }

    /**
     * Sets <code>value</code> as the attribute value for PartnerId.
     * @param value value to set the PartnerId
     */
    public void setPartnerId(String value) {
        setAttributeInternal(PARTNERID, value);
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
     * Gets the attribute value for CardgroupMainType, using the alias name CardgroupMainType.
     * @return the CardgroupMainType
     */
    public String getCardgroupMainType() {
        return (String)getAttributeInternal(CARDGROUPMAINTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for CardgroupMainType.
     * @param value value to set the CardgroupMainType
     */
    public void setCardgroupMainType(String value) {
        setAttributeInternal(CARDGROUPMAINTYPE, value);
    }

    /**
     * Gets the attribute value for CardgroupSubType, using the alias name CardgroupSubType.
     * @return the CardgroupSubType
     */
    public String getCardgroupSubType() {
        return (String)getAttributeInternal(CARDGROUPSUBTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for CardgroupSubType.
     * @param value value to set the CardgroupSubType
     */
    public void setCardgroupSubType(String value) {
        setAttributeInternal(CARDGROUPSUBTYPE, value);
    }

    /**
     * Gets the attribute value for CardgroupSeq, using the alias name CardgroupSeq.
     * @return the CardgroupSeq
     */
    public String getCardgroupSeq() {
        return (String)getAttributeInternal(CARDGROUPSEQ);
    }

    /**
     * Sets <code>value</code> as the attribute value for CardgroupSeq.
     * @param value value to set the CardgroupSeq
     */
    public void setCardgroupSeq(String value) {
        setAttributeInternal(CARDGROUPSEQ, value);
    }

    /**
     * Gets the attribute value for Terminal, using the alias name Terminal.
     * @return the Terminal
     */
    public String getTerminal() {
        return (String)getAttributeInternal(TERMINAL);
    }

    /**
     * Sets <code>value</code> as the attribute value for Terminal.
     * @param value value to set the Terminal
     */
    public void setTerminal(String value) {
        setAttributeInternal(TERMINAL, value);
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
     * Gets the attribute value for LastModifiedDate, using the alias name LastModifiedDate.
     * @return the LastModifiedDate
     */
    public Date getLastModifiedDate() {
        return (Date)getAttributeInternal(LASTMODIFIEDDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for LastModifiedDate.
     * @param value value to set the LastModifiedDate
     */
    public void setLastModifiedDate(Date value) {
        setAttributeInternal(LASTMODIFIEDDATE, value);
    }

    /**
     * Gets the attribute value for PreviousOdometer, using the alias name PreviousOdometer.
     * @return the PreviousOdometer
     */
    public String getPreviousOdometer() {
        return (String)getAttributeInternal(PREVIOUSODOMETER);
    }

    /**
     * Sets <code>value</code> as the attribute value for PreviousOdometer.
     * @param value value to set the PreviousOdometer
     */
    public void setPreviousOdometer(String value) {
        setAttributeInternal(PREVIOUSODOMETER, value);
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
     * @param urefTransactionId key constituent
     * @param palsCountryCode key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String urefTransactionId,
                                       String palsCountryCode) {
        return new Key(new Object[]{urefTransactionId, palsCountryCode});
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = EntityDefImpl.findDefObject("com.sfr.engage.model.entities.PrtCardTransactionHeaderEO");
        }
        return mDefinitionObject;
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

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        super.doDML(operation, e);
    }
}
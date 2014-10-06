package com.sfr.engage.model.queries.rvo;

import com.sfr.engage.core.PartnerInfo;

import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jun 30 12:43:16 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PrtHomeTransactionsRVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
     private ExternalContext ectx=FacesContext.getCurrentInstance().getExternalContext();
     private HttpServletRequest request=(HttpServletRequest)ectx.getRequest();
     private HttpSession session=request.getSession(false);
     private List<PartnerInfo> partnerInfoList;
    public enum AttributesEnum {
        PurchaseCurrency {
            public Object get(PrtHomeTransactionsRVORowImpl obj) {
                return obj.getPurchaseCurrency();
            }

            public void put(PrtHomeTransactionsRVORowImpl obj, Object value) {
                obj.setPurchaseCurrency((String)value);
            }
        }
        ,
        PartnerId {
            public Object get(PrtHomeTransactionsRVORowImpl obj) {
                return obj.getPartnerId();
            }

            public void put(PrtHomeTransactionsRVORowImpl obj, Object value) {
                obj.setPartnerId((String)value);
            }
        }
        ,
        Ksid {
            public Object get(PrtHomeTransactionsRVORowImpl obj) {
                return obj.getKsid();
            }

            public void put(PrtHomeTransactionsRVORowImpl obj, Object value) {
                obj.setKsid((String)value);
            }
        }
        ,
        TransactionTime {
            public Object get(PrtHomeTransactionsRVORowImpl obj) {
                return obj.getTransactionTime();
            }

            public void put(PrtHomeTransactionsRVORowImpl obj, Object value) {
                obj.setTransactionTime((Timestamp)value);
            }
        }
        ,
        TransactionDt {
            public Object get(PrtHomeTransactionsRVORowImpl obj) {
                return obj.getTransactionDt();
            }

            public void put(PrtHomeTransactionsRVORowImpl obj, Object value) {
                obj.setTransactionDt((Date)value);
            }
        }
        ,
        TransactionType {
            public Object get(PrtHomeTransactionsRVORowImpl obj) {
                return obj.getTransactionType();
            }

            public void put(PrtHomeTransactionsRVORowImpl obj, Object value) {
                obj.setTransactionType((String)value);
            }
        }
        ,
        Card1Id {
            public Object get(PrtHomeTransactionsRVORowImpl obj) {
                return obj.getCard1Id();
            }

            public void put(PrtHomeTransactionsRVORowImpl obj, Object value) {
                obj.setCard1Id((String)value);
            }
        }
        ,
        StationName {
            public Object get(PrtHomeTransactionsRVORowImpl obj) {
                return obj.getStationName();
            }

            public void put(PrtHomeTransactionsRVORowImpl obj, Object value) {
                obj.setStationName((String)value);
            }
        }
        ,
        ProductName {
            public Object get(PrtHomeTransactionsRVORowImpl obj) {
                return obj.getProductName();
            }

            public void put(PrtHomeTransactionsRVORowImpl obj, Object value) {
                obj.setProductName((String)value);
            }
        }
        ,
        CurrencyGrossAmount {
            public Object get(PrtHomeTransactionsRVORowImpl obj) {
                return obj.getCurrencyGrossAmount();
            }

            public void put(PrtHomeTransactionsRVORowImpl obj, Object value) {
                obj.setCurrencyGrossAmount((Number)value);
            }
        }
        ,
        Quantity {
            public Object get(PrtHomeTransactionsRVORowImpl obj) {
                return obj.getQuantity();
            }

            public void put(PrtHomeTransactionsRVORowImpl obj, Object value) {
                obj.setQuantity((Number)value);
            }
        }
        ,
        UnitOfMeasure {
            public Object get(PrtHomeTransactionsRVORowImpl obj) {
                return obj.getUnitOfMeasure();
            }

            public void put(PrtHomeTransactionsRVORowImpl obj, Object value) {
                obj.setUnitOfMeasure((String)value);
            }
        }
        ,
        Card {
            public Object get(PrtHomeTransactionsRVORowImpl obj) {
                return obj.getCard();
            }

            public void put(PrtHomeTransactionsRVORowImpl obj, Object value) {
                obj.setCard((String)value);
            }
        }
        ,
        CardTextLine2 {
            public Object get(PrtHomeTransactionsRVORowImpl obj) {
                return obj.getCardTextLine2();
            }

            public void put(PrtHomeTransactionsRVORowImpl obj, Object value) {
                obj.setCardTextLine2((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(PrtHomeTransactionsRVORowImpl object);

        public abstract void put(PrtHomeTransactionsRVORowImpl object,
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


    public static final int PURCHASECURRENCY = AttributesEnum.PurchaseCurrency.index();
    public static final int PARTNERID = AttributesEnum.PartnerId.index();
    public static final int KSID = AttributesEnum.Ksid.index();
    public static final int TRANSACTIONTIME = AttributesEnum.TransactionTime.index();
    public static final int TRANSACTIONDT = AttributesEnum.TransactionDt.index();
    public static final int TRANSACTIONTYPE = AttributesEnum.TransactionType.index();
    public static final int CARD1ID = AttributesEnum.Card1Id.index();
    public static final int STATIONNAME = AttributesEnum.StationName.index();
    public static final int PRODUCTNAME = AttributesEnum.ProductName.index();
    public static final int CURRENCYGROSSAMOUNT = AttributesEnum.CurrencyGrossAmount.index();
    public static final int QUANTITY = AttributesEnum.Quantity.index();
    public static final int UNITOFMEASURE = AttributesEnum.UnitOfMeasure.index();
    public static final int CARD = AttributesEnum.Card.index();
    public static final int CARDTEXTLINE2 = AttributesEnum.CardTextLine2.index();

    /**
     * This is the default constructor (do not remove).
     */
    public PrtHomeTransactionsRVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute PurchaseCurrency.
     * @return the PurchaseCurrency
     */
    public String getPurchaseCurrency() {
        return (String) getAttributeInternal(PURCHASECURRENCY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PurchaseCurrency.
     * @param value value to set the  PurchaseCurrency
     */
    public void setPurchaseCurrency(String value) {
        setAttributeInternal(PURCHASECURRENCY, value);
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
     * Gets the attribute value for the calculated attribute Ksid.
     * @return the Ksid
     */
    public String getKsid() {
        return (String) getAttributeInternal(KSID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Ksid.
     * @param value value to set the  Ksid
     */
    public void setKsid(String value) {
        setAttributeInternal(KSID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransactionTime.
     * @return the TransactionTime
     */
    public Timestamp getTransactionTime() {
        return (Timestamp) getAttributeInternal(TRANSACTIONTIME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransactionTime.
     * @param value value to set the  TransactionTime
     */
    public void setTransactionTime(Timestamp value) {
        setAttributeInternal(TRANSACTIONTIME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransactionDt.
     * @return the TransactionDt
     */
    public Date getTransactionDt() {
        return (Date) getAttributeInternal(TRANSACTIONDT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransactionDt.
     * @param value value to set the  TransactionDt
     */
    public void setTransactionDt(Date value) {
        setAttributeInternal(TRANSACTIONDT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TransactionType.
     * @return the TransactionType
     */
    public String getTransactionType() {
        return (String) getAttributeInternal(TRANSACTIONTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TransactionType.
     * @param value value to set the  TransactionType
     */
    public void setTransactionType(String value) {
        setAttributeInternal(TRANSACTIONTYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Card1Id.
     * @return the Card1Id
     */
    public String getCard1Id() {
        return (String) getAttributeInternal(CARD1ID);        
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Card1Id.
     * @param value value to set the  Card1Id
     */
    public void setCard1Id(String value) {
        setAttributeInternal(CARD1ID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute StationName.
     * @return the StationName
     */
    public String getStationName() {
        return (String) getAttributeInternal(STATIONNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute StationName.
     * @param value value to set the  StationName
     */
    public void setStationName(String value) {
        setAttributeInternal(STATIONNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ProductName.
     * @return the ProductName
     */
    public String getProductName() {
        return (String) getAttributeInternal(PRODUCTNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ProductName.
     * @param value value to set the  ProductName
     */
    public void setProductName(String value) {
        setAttributeInternal(PRODUCTNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CurrencyGrossAmount.
     * @return the CurrencyGrossAmount
     */
    public Number getCurrencyGrossAmount() {
        return (Number) getAttributeInternal(CURRENCYGROSSAMOUNT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CurrencyGrossAmount.
     * @param value value to set the  CurrencyGrossAmount
     */
    public void setCurrencyGrossAmount(Number value) {
        setAttributeInternal(CURRENCYGROSSAMOUNT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Quantity.
     * @return the Quantity
     */
    public Number getQuantity() {
        
        if(getUnitOfMeasure() != null && ("STK").equalsIgnoreCase(getUnitOfMeasure())){
            oracle.jbo.domain.Number num;
            return new oracle.jbo.domain.Number(1);
        }
        else{
            return (Number) getAttributeInternal(QUANTITY);
        } 
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Quantity.
     * @param value value to set the  Quantity
     */
    public void setQuantity(Number value) {
        setAttributeInternal(QUANTITY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute UnitOfMeasure.
     * @return the UnitOfMeasure
     */
    public String getUnitOfMeasure() {
        return (String) getAttributeInternal(UNITOFMEASURE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute UnitOfMeasure.
     * @param value value to set the  UnitOfMeasure
     */
    public void setUnitOfMeasure(String value) {
        setAttributeInternal(UNITOFMEASURE, value);
    }


    /**
     * Gets the attribute value for the calculated attribute Card.
     * @return the Card
     */
    public String getCard() {
        String result="";
        result = "XXXX" + getCard1Id().toString().substring(getCard1Id().length()-4, getCard1Id().toString().length());
        return result;
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Card.
     * @param value value to set the  Card
     */
    public void setCard(String value) {
        setAttributeInternal(CARD, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CardTextLine2.
     * @return the CardTextLine2
     */
    public String getCardTextLine2() {
        String cardTextLine="";
        if(session!=null) {
            if (session.getAttribute("Partner_Object_List") != null) {
                partnerInfoList =
                        (List<PartnerInfo>)session.getAttribute("Partner_Object_List");
               
                String partnerId=getPartnerId();
                String cardId=getKsid().toString().trim();
                if (partnerInfoList != null) {
                    for (int k = 0; k < partnerInfoList.size(); k++) {
                        if (partnerId.equalsIgnoreCase(partnerInfoList.get(k).getPartnerValue().toString())) {
                                for (int ac = 0;ac < partnerInfoList.get(k).getAccountList().size();ac++) {
                                        for (int cg = 0;cg < partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().size();cg++) {
                                            for (int cd = 0;cd < partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().size();cd++) {
                                            if(cardId.equalsIgnoreCase(partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cd).getCardID())) {
                                                if(partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cd).getCardTextline2() != null)
                                                {
                                               cardTextLine=  partnerInfoList.get(k).getAccountList().get(ac).getCardGroup().get(cg).getCard().get(cd).getCardTextline2().toString();
                                                }
                                            }
                                          }
                                        }

                            }
                        }

                    }
                }
            
            }
        }
        return cardTextLine;
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CardTextLine2.
     * @param value value to set the  CardTextLine2
     */
    public void setCardTextLine2(String value) {
        setAttributeInternal(CARDTEXTLINE2, value);
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

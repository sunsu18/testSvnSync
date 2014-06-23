package com.sfr.core.bean;

import java.math.BigDecimal;

import java.math.BigInteger;

import java.util.Date;
import java.util.List;

public class Customer extends BaseBean {

    private static final long serialVersionUID = 1L;
    private String profile; // B2B or B2C
    private String customerType; // company Type industrial etc.
    private String customerTypeDescription;
    //equivalent to JDE CustomerResults/soldTo/entityTypeCode as per cust serach
    // CustomerResults/SoldTo/searchType as per cust Info
    private String taxID; // SSN for B2C only
    private String organizationID; // for B2B only
    private Integer monthlyConsumption;
    private BigDecimal requestedCreditLimit;
    private String paymentTerms; // default 14 days
    private COOPInfo coopInfo;
    private String vatID; // equivalent to CustomerResults/soldTo/vatID  (not present in customer info wsdl)
    private Address soldToAddress; // CustomerResults/SoldTo/addressLine1,2,3,4 ,postalcode, city, country etc.
    private List<ShippingInfo> shippingInfoList;
    private String b2cFirstName;
    private String b2cMiddleName;
    private String b2cLastName;
    private Date b2cDOB;
    private Address payor; // payer details
    private boolean duplicateFlag; // (Y/N) This would be passed by the BSSV if Duplicate record is found, alongwith the address details of the Customer
    private String actionType; // Action type from the Portal  U = Update
    private boolean creditStoppedFlag; //CustomerResults/soldTo/holdCode
    //below defines variables to be reviewed by hanif
    private String billingFrequeny;
    private String paymentType;
    private String finalNote;
    private BigDecimal percentageTotalCreditApplied;
    private String deliveryInstrution1;
    private String deliveryInstrution2;
    private BigDecimal creditLimit;
    private BigInteger lastFetchedSoldToAddressNumber;
    private String enableThirdPartyCall;   //This flag is contain Y/N, which will decide whether the JDE will call Thrid party Service or not.
    private Integer totalNumberofShipTo;

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setTaxID(String taxID) {
        this.taxID = taxID;
    }

    public String getTaxID() {
        return taxID;
    }

    public void setVatID(String vatID) {
        this.vatID = vatID;
    }

    public String getVatID() {
        return vatID;
    }

    public void setSoldToAddress(Address soldToAddress) {
        this.soldToAddress = soldToAddress;
    }

    public Address getSoldToAddress() {
        return soldToAddress;
    }

    public void setShippingInfoList(List<ShippingInfo> shippingInfoList) {
        this.shippingInfoList = shippingInfoList;
    }

    public List<ShippingInfo> getShippingInfoList() {
        return shippingInfoList;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setPayor(Address payor) {
        this.payor = payor;
    }

    public Address getPayor() {
        return payor;
    }

    public void setRequestedCreditLimit(BigDecimal requestedCreditLimit) {
        this.requestedCreditLimit = requestedCreditLimit;
    }

    public BigDecimal getRequestedCreditLimit() {
        return requestedCreditLimit;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setB2cFirstName(String b2cFirstName) {
        this.b2cFirstName = b2cFirstName;
    }

    public String getB2cFirstName() {
        return b2cFirstName;
    }

    public void setB2cMiddleName(String b2cMiddleName) {
        this.b2cMiddleName = b2cMiddleName;
    }

    public String getB2cMiddleName() {
        return b2cMiddleName;
    }

    public void setB2cLastName(String b2cLastName) {
        this.b2cLastName = b2cLastName;
    }

    public String getB2cLastName() {
        return b2cLastName;
    }

    public void setB2cDOB(Date b2cDOB) {
        this.b2cDOB = b2cDOB;
    }

    public Date getB2cDOB() {
        return b2cDOB;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }

    public void setMonthlyConsumption(Integer monthlyConsumption) {
        this.monthlyConsumption = monthlyConsumption;
    }

    public Integer getMonthlyConsumption() {
        return monthlyConsumption;
    }

    public void setCoopInfo(COOPInfo coopInfo) {
        this.coopInfo = coopInfo;
    }

    public COOPInfo getCoopInfo() {
        return coopInfo;
    }

    public void setDuplicateFlag(boolean duplicateFlag) {
        this.duplicateFlag = duplicateFlag;
    }

    public boolean isDuplicateFlag() {
        return duplicateFlag;
    }

    public void setCreditStoppedFlag(boolean creditStoppedFlag) {
        this.creditStoppedFlag = creditStoppedFlag;
    }

    public boolean isCreditStoppedFlag() {
        return creditStoppedFlag;
    }

    public void setCustomerTypeDescription(String customerTypeDescription) {
        this.customerTypeDescription = customerTypeDescription;
    }

    public String getCustomerTypeDescription() {
        return customerTypeDescription;
    }



    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setFinalNote(String finalNote) {
        this.finalNote = finalNote;
    }

    public String getFinalNote() {
        return finalNote;
    }

    public void setPercentageTotalCreditApplied(BigDecimal percentageTotalCreditApplied) {
        this.percentageTotalCreditApplied = percentageTotalCreditApplied;
    }

    public BigDecimal getPercentageTotalCreditApplied() {
        return percentageTotalCreditApplied;
    }

    public void setDeliveryInstrution1(String deliveryInstrution1) {
        this.deliveryInstrution1 = deliveryInstrution1;
    }

    public String getDeliveryInstrution1() {
        return deliveryInstrution1;
    }

    public void setDeliveryInstrution2(String deliveryInstrution2) {
        this.deliveryInstrution2 = deliveryInstrution2;
    }

    public String getDeliveryInstrution2() {
        return deliveryInstrution2;
    }

    public void setBillingFrequeny(String billingFrequeny) {
        this.billingFrequeny = billingFrequeny;
    }

    public String getBillingFrequeny() {
        return billingFrequeny;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }


   @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        sb.append("Customer Printing Starts---------"+ NEW_LINE );
        sb.append("actionType=<" + this.actionType + ">" );
        sb.append("b2cDOB=<" + this.b2cDOB + ">" );
        sb.append("b2cFirstName=<" + this.b2cFirstName + ">" );

        sb.append("b2cMiddleName=<" + this.b2cMiddleName + ">" );
        sb.append("b2cLastName=<" + this.b2cLastName + ">" );
        sb.append("billingFrequeny=<" + this.billingFrequeny + ">" );


        sb.append("creditLimit=<" + this.creditLimit + ">" );
        sb.append("creditStoppedFlag=<" + this.creditStoppedFlag + ">" );

        sb.append("customerType=<" + this.customerType + ">" );
        sb.append("customerTypeDescription=<" + this.customerTypeDescription + ">" );
        sb.append("deliveryInstrution1=<" + this.deliveryInstrution1 + ">" );

        sb.append("deliveryInstrution2=<" + this.deliveryInstrution2 + ">" );
        sb.append("duplicateFlag=<" + this.duplicateFlag + ">" );
        sb.append("finalNote=<" + this.finalNote + ">" );

        sb.append("monthlyConsumption=<" + this.monthlyConsumption + ">" );
        sb.append("organizationID=<" + this.organizationID + ">" );
        sb.append("paymentTerms=<" + this.paymentTerms + ">" );

        sb.append("paymentType=<" + this.paymentType + ">" );

        sb.append("percentageTotalCreditApplied=<" + this.percentageTotalCreditApplied + ">" );

        sb.append("profile=<" + this.profile + ">" );
        sb.append("requestedCreditLimit=<" + this.requestedCreditLimit + ">" );
        sb.append("taxID=<" + this.taxID + ">" );
        sb.append("enableThirdPartyCall=<" + this.enableThirdPartyCall + ">" );
        sb.append("vatID=<" + this.vatID + ">"+ NEW_LINE );
        sb.append("totalNumberofShipTo=<" + this.totalNumberofShipTo + ">"+ NEW_LINE );
        sb.append("\tSold to Address begins:---------------->");
        if (this.soldToAddress != null) {
            sb.append( this.soldToAddress );
        } else {
            sb.append("Address IS NULL" );
        }
        sb.append("<------------------Sold to Address ends."+NEW_LINE);
        
        sb.append("\tpayor Address begins:---------------->");
        if (this.payor != null) {
            sb.append( this.payor );
        } else {
            sb.append("Address IS NULL");
        }
        sb.append("<------------------Payor Address ends."+NEW_LINE);

        if (this.shippingInfoList != null) {
            sb.append("\tshippingInfoList.size()=<" + shippingInfoList.size() + ">=" + NEW_LINE);

            for (int i = 0; i < this.shippingInfoList.size(); i++) {
                sb.append("\t\tshippingInfoList object nubmer :-> " + i + NEW_LINE);
                ShippingInfo ship = shippingInfoList.get(i);
                if (ship != null)
                    sb.append( ship.toString());
                else
                    sb.append("shippingInfoList.get(" + i + ") is null" + NEW_LINE);
            }
        } else {
            sb.append("----->shippingInfoList IS NULL<---------" + NEW_LINE);
        }

        sb.append("\tCoopinfo begins :----------------> ");
        if (this.coopInfo != null) {
            sb.append(this.coopInfo );
        } else {
            sb.append("Coopinfo IS NULL " );
        }
        sb.append("----------------<Coopinfo ends  "+NEW_LINE);

        sb.append("Customer printing ends---------" );


        return sb.toString();
    }

    public void setEnableThirdPartyCall(String soliditat) {
        this.enableThirdPartyCall = soliditat;
    }

    public String getEnableThirdPartyCall() {
        return enableThirdPartyCall;
    }

    public void setLastFetchedSoldToAddressNumber(BigInteger lastFetchedSoldToAddressNumber) {
        this.lastFetchedSoldToAddressNumber = lastFetchedSoldToAddressNumber;
    }

    public BigInteger getLastFetchedSoldToAddressNumber() {
        return lastFetchedSoldToAddressNumber;
    }


    public void setTotalNumberofShipTo(Integer totalNumberofShipTo) {
        this.totalNumberofShipTo = totalNumberofShipTo;
    }

    public Integer getTotalNumberofShipTo() {
        return totalNumberofShipTo;
    }
}



 /**
  * @author      Lopamudra Choudhury <lopamudra.choudhury@lntinfotech.com>
  * @version     1.6                 (the version of the package this class was first added to)                   
  * @since       2012-06-14          (a date or the version number of this program)
  */
  package com.sfr.core.bean;

import com.sfr.util.AccessDataControl;

import java.math.BigDecimal;

import java.util.List;

import java.util.Date;

import oracle.adf.share.logging.ADFLogger;

public class Order extends BaseBean {

    private static final long serialVersionUID = 1L;
    private Integer orderNumber; // verified - used in header
    private String orderNumber2;// for reseller
    private Date orderDate; // verified - used in header
    private String orderType; // Not displayed
    private String orderCompany; //Not displayed
    private String branchPlantCode; //Will be used in case of Marine and Reseller
    private String transactionType; // verified - Aviation
    private String paymentMethodType;//Aviation
    private String paymentMethod; // verified - Aviation
    private String paymentMethodDescription;
    private String paymentTerms; // not diaplayed at the moment

    private String skipperName;
    private Integer shipToNumber;
    private String contactPerson; // verified
    private String contactPhone; // verified
    private String contactNote; // Reseller and Marine
    private String noteToDriver; // verified
    private Date deliveryDate; // verified
    private String referenceNumber; // verified - used in header
    private String shippingInstruction; //(Delivery Instruction)//limited characters
    private String shippingInfoText; // Not yet clear shippingInfo vs. shippingInstruction
    private String exciseDutyStatus; // Not clear where this will be used (Naveen follow up)
    private String invoiceInfo; // unlimited characters - will come from UDC

    private ShippingInfo shippingInfo; //Shipping address of the order
    private String status; //verified - used in header as Invoice status
    private String currrencyCode;
    private String reportSentStatusFlag;
    /*
	
     * Invoice Amount = Net Invoice AMount + Additional charges
     * Net Invoice AMount = Invoice Amount - Additional charges
     * Gross Invoice AMount = Invoice amount + VAT
     */
    private BigDecimal netInvoiceAmount; // Verified
    private BigDecimal grossInvoiceAmount; // Verified - Displayed in header
    private BigDecimal VAT; // Verified - Displayed in header
    private BigDecimal additionalAmount; //--should be Double as length is 15 in fsd
    private Double shippingCharges; //--should be Double as length is 15 in fsd

    private boolean isOrderCredited; // Will be used to control visibility of "Credit Order" button in Aviation
    private Address soldToAddress;

    private List<OrderedProduct> orderedProductList; 
    private String recordType;
    private String headerActionType;
    private String valueChain;
    private String creditOrderNumberHdr;//used in Aviation 
    private boolean transactionTypeCreditFlag;
    private String hasMoreRecords;
    private boolean extraCharges;// used for Reseller and Webshop
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();
    public Order() {
        super();
    }

    public Order(Integer orderNumber, Address deliveryAddress, List<OrderedProduct> orderedProductList) {
        log.info(accessDC.getDisplayRecord() + this.getClass() + " Order.Order : "+"---size---" + orderedProductList.size());
        this.orderNumber = orderNumber;
        //this.deliveryAddress = deliveryAddress;
        this.orderedProductList = orderedProductList;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderCompany(String orderCompany) {
        this.orderCompany = orderCompany;
    }

    public String getOrderCompany() {
        return orderCompany;
    }

    public void setBranchPlantCode(String branchPlantCode) {
        this.branchPlantCode = branchPlantCode;
    }

    public String getBranchPlantCode() {
        return branchPlantCode;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setSoldToAddress(Address soldToAddress) {
        this.soldToAddress = soldToAddress;
    }

    public Address getSoldToAddress() {
        return soldToAddress;
    }

    public void setOrderedProductList(List<OrderedProduct> orderedProductList) {
        this.orderedProductList = orderedProductList;
    }

    public List<OrderedProduct> getOrderedProductList() {
        return orderedProductList;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setNoteToDriver(String noteToDriver) {
        this.noteToDriver = noteToDriver;
    }

    public String getNoteToDriver() {
        return noteToDriver;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }


    public void setContactNote(String contactNote) {
        this.contactNote = contactNote;
    }

    public String getContactNote() {
        return contactNote;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setShippingInstruction(String shippingInstruction) {
        this.shippingInstruction = shippingInstruction;
    }

    public String getShippingInstruction() {
        return shippingInstruction;
    }

    public void setShippingInfoText(String shippingInfoText) {
        this.shippingInfoText = shippingInfoText;
    }

    public String getShippingInfoText() {
        return shippingInfoText;
    }

    public void setExciseDutyStatus(String exciseDutyStatus) {
        this.exciseDutyStatus = exciseDutyStatus;
    }

    public String getExciseDutyStatus() {
        return exciseDutyStatus;
    }

    public void setInvoiceInfo(String invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public String getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

  

    public void setIsOrderCredited(boolean isOrderCredited) {
        this.isOrderCredited = isOrderCredited;
    }

    public boolean isIsOrderCredited() {
        return isOrderCredited;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

   

   

    public void setShippingCharges(Double shippingCharges) {
        this.shippingCharges = shippingCharges;
    }

    public Double getShippingCharges() {
        return shippingCharges;
    }

    public void setHeaderActionType(String headerActionType) {
        this.headerActionType = headerActionType;
    }

    public String getHeaderActionType() {
        return headerActionType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setSkipperName(String skipperName) {
        this.skipperName = skipperName;
    }

    public String getSkipperName() {
        return skipperName;
    }

    public void setShipToNumber(Integer shipToNumber) {
        this.shipToNumber = shipToNumber;
    }

    public Integer getShipToNumber() {
        return shipToNumber;
    }

    public void setValueChain(String valueChain) {
        this.valueChain = valueChain;
    }

    public String getValueChain() {
        return valueChain;
    }

    public void setNetInvoiceAmount(BigDecimal netInvoiceAmount) {
        this.netInvoiceAmount = netInvoiceAmount;
    }

    public BigDecimal getNetInvoiceAmount() {
        return netInvoiceAmount;
    }

    public void setGrossInvoiceAmount(BigDecimal grossInvoiceAmount) {
        this.grossInvoiceAmount = grossInvoiceAmount;
    }

    public BigDecimal getGrossInvoiceAmount() {
        return grossInvoiceAmount;
    }

    public void setVAT(BigDecimal VAT) {
        this.VAT = VAT;
    }

    public BigDecimal getVAT() {
        return VAT;
    }

    public void setCurrrencyCode(String currrencyCode) {
        this.currrencyCode = currrencyCode;
    }

    public String getCurrrencyCode() {
        return currrencyCode;
    }

    public void setCreditOrderNumberHdr(String creditOrderNumberHdr) {
        this.creditOrderNumberHdr = creditOrderNumberHdr;
    }

    public String getCreditOrderNumberHdr() {
        return creditOrderNumberHdr;
    }

    public void setTransactionTypeCreditFlag(boolean transactionTypeCreditFlag) {
        this.transactionTypeCreditFlag = transactionTypeCreditFlag;
    }

    public boolean isTransactionTypeCreditFlag() {
        return transactionTypeCreditFlag;
    }

    public void setOrderNumber2(String orderNumber2) {
        this.orderNumber2 = orderNumber2;
    }

    public String getOrderNumber2() {
        return orderNumber2;
    }

    public void setHasMoreRecords(String hasMoreRecords) {
        this.hasMoreRecords = hasMoreRecords;
    }

    public String getHasMoreRecords() {
        return hasMoreRecords;
    }

    public void setExtraCharges(boolean extraCharges) {
        this.extraCharges = extraCharges;
    }

    public boolean isExtraCharges() {
        return extraCharges;
    }

    public void setAdditionalAmount(BigDecimal additionalAmount) {
        this.additionalAmount = additionalAmount;
    }

    public BigDecimal getAdditionalAmount() {
        return additionalAmount;
    }

    public void setPaymentMethodType(String paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public String getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodDescription(String paymentMethodDescription) {
        this.paymentMethodDescription = paymentMethodDescription;
    }

    public String getPaymentMethodDescription() {
        return paymentMethodDescription;
    }


    public void setReportSentStatusFlag(String reportSentStatusFlag) {
        this.reportSentStatusFlag = reportSentStatusFlag;
    }

    public String getReportSentStatusFlag() {
        return reportSentStatusFlag;
    }
}

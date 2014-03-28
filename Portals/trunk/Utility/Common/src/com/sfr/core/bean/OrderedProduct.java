 /**
  * @author      Lopamudra Choudhury <lopamudra.choudhury@lntinfotech.com>
  * @version     1.6                 (the version of the package this class was first added to)                   
  * @since       2012-06-14          (a date or the version number of this program)
  */
package com.sfr.core.bean;

import java.math.BigDecimal;

import java.util.Date;

public class OrderedProduct extends Product  {

    //TODO: Hanif Merchant: Need to remove duplicate entries for Net, Gross and tank for Marine
    private static final long serialVersionUID = 1L;
    private int orderNumber; // verified - used in detail of Invoice
    private int quantity; // verified - used in detail of Invoice and Order
    private int lineNo; // mapped to PortalLineNumber in S0131 WSDL
    private int invoiceNumber; // verified - used in detail of Order
    private String invoiceType; // Not to be displayed
    private String invoiceCompany; // Not to be displayed
    private String tracking; // to be verified when doing order
    private String orderType; // to be verified when doing order
    private String orderCompany; // to be verified when doing order
    private String orderLineType;
    private String orderLastStatus; // Not to be displayed
    private String orderStatusDescription;
    private String orderNextStatus; // Not to be displayed
    private Integer deliveryNumber; // Not to be displayed
    private Integer undeliveredQuantity; // Not to be displayed.
    private String locationCode; // Not to be displayed.

    private BigDecimal netInvoiceAmount; // Verified
    private BigDecimal grossInvoiceAmount; // Verified - Displayed in header
    private BigDecimal VAT; // Verified - Displayed in header
    private BigDecimal additionalAmount; //--should be Double as length is 15 in fsd
    private Double shippingCharges; //--should be Double as length is 15 in fsd

    private String tankId; // displayed only for Reseller and Webshop
    private String tankDescription; // displayed only for Reseller and Webshop
    private BigDecimal volume; // to be verified when doing order
    private BigDecimal temperature; // to be verified when doing order
    private BigDecimal density; // to be verified when doing order
    /*
      * These will be used to set isOrderCredited flag in Order bean.
      * Set to TRUE if atleast one of the product has a credit order number
      */
    private String creditOrderNumber;
    private String creditOrderType;
    private String creditOrderCompany;
    private String creditOrderLineNumber;
    /*
      * Either of this will be used to track Lubs order
      */
    private String pieceTrackingNumber;
    private String shipmentTrackingNumber;
    /*
      * Reseller planning specific information at product level
      */
//    private int rebate; // to be verified when doing order --will it be Integer
    private BigDecimal rebate;
    private Date shippingDate; // to be verified when doing order -- Date
    private String dssFlag; // to be verified when doing order
    private Date deliveryFromDate;
    private Date deliveryToDate;
    private Date planDate;
    private Date updateDate;
    /* Reseller values at product level in the List ------JIGAR */
    private String fullfillFlag;
    private String resellerPlanningFlag;
    private String vehicleNumber;
    private String tripNumber;
    private String specialTreatment;
    private String completeDelivery;
    private String uniqueId; // for reseller(fuels) to traverse
    private String detailActionType;//for reseller updation and addition

    /* Marine values added in the List ------JIGAR */
    private String tankNumber;
    private String exemptionCode;
    private String exemptionType;
//    private Double netValue;
//    private Double grossValue;
//    private Double vat;
     private String CurrrencyCode;
     private BigDecimal originalOrderLineNumber;
     private String originalOrderNumber;
     private String modifiedExemptionCode;
     private String modifiedDutyStatusCode;
     private BigDecimal lineNumber;
     private String dutyStatusCode;
     
     private boolean isZeroFlagPrice;
    public OrderedProduct() {
        super();
    }
    public OrderedProduct(OrderedProduct newProd) {
        super();
        this.rebate=newProd.getRebate();
        this.volume=newProd.getVolume();
        this.orderNumber=newProd.getOrderNumber();
        this.quantity=newProd.getQuantity();
        this.lineNo=newProd.getLineNo(); // mapped to PortalLineNumber in S0131 WSDL
        this.invoiceNumber=newProd.getInvoiceNumber(); // verified - used in detail of Order
        this.invoiceType=newProd.getInvoiceType(); // Not to be displayed
        this.invoiceCompany=newProd.getInvoiceCompany(); // Not to be displayed
        this.tracking=newProd.getTracking(); // to be verified when doing order
        this.orderType=newProd.getOrderType(); // to be verified when doing order
        this.orderCompany=newProd.getOrderCompany(); // to be verified when doing order
        this.orderLineType=newProd.getOrderLineType();
        this.orderLastStatus=newProd.getOrderLastStatus(); // Not to be displayed
        this.orderStatusDescription=newProd.getOrderStatusDescription();
        this.orderNextStatus=newProd.getOrderNextStatus(); // Not to be displayed
        this.deliveryNumber=newProd.getDeliveryNumber(); // Not to be displayed
        this.undeliveredQuantity=newProd.getUndeliveredQuantity(); // Not to be displayed.
        this.locationCode=newProd.getLocationCode();
        this.netInvoiceAmount=newProd.getNetInvoiceAmount();
        this.shippingCharges=newProd.getShippingCharges();
        this.additionalAmount=newProd.getAdditionalAmount();
        this.tankDescription=newProd.getTankDescription();
        this.temperature=newProd.getTemperature();
        this.density=newProd.getDensity();
        this.creditOrderNumber=newProd.getCreditOrderNumber();
        this.creditOrderType=newProd.getCreditOrderType();
        this.creditOrderCompany=newProd.getCreditOrderCompany();
        this.creditOrderLineNumber=newProd.getCreditOrderLineNumber();
        this.pieceTrackingNumber=newProd.getPieceTrackingNumber();
        this.shipmentTrackingNumber=newProd.getShipmentTrackingNumber();
        this.resellerPlanningFlag=newProd.getResellerPlanningFlag();
        this.fullfillFlag=newProd.getFullfillFlag();
        this.deliveryFromDate=newProd.getDeliveryFromDate();
        this.deliveryToDate=newProd.getDeliveryToDate();
        this.planDate=newProd.getPlanDate();
        this.vehicleNumber=newProd.getVehicleNumber();
        this.tripNumber=newProd.getTripNumber();
        this.tankId=newProd.getTankId();   
        this.netInvoiceAmount=newProd.getNetInvoiceAmount(); // Verified
        this.grossInvoiceAmount=newProd.getGrossInvoiceAmount(); // Verified - Displayed in header
        this.VAT=newProd.getVAT(); 
        this.shippingDate=newProd.getShippingDate();
        this.dssFlag=newProd.getDssFlag();
        this.updateDate=newProd.getUpdateDate();
        this.specialTreatment=newProd.getSpecialTreatment();
        this.completeDelivery=newProd.getCompleteDelivery();
        this.uniqueId=newProd.getUniqueId();
        this.detailActionType=newProd.getDetailActionType();
        this.tankNumber=newProd.getTankNumber();
        this.exemptionCode=newProd.getExemptionCode();
        this.exemptionType=newProd.getExemptionType();
        this.CurrrencyCode=newProd.getCurrrencyCode();
        this.originalOrderLineNumber=newProd.getOriginalOrderLineNumber();
        this.originalOrderNumber=newProd.getOriginalOrderNumber();
        this.modifiedExemptionCode=newProd.getModifiedExemptionCode();
        this.modifiedDutyStatusCode=newProd.getModifiedDutyStatusCode();
        this.lineNumber=newProd.getLineNumber();
      
            
        this.setProductNo(newProd.getProductNo());
        this.setProductNameJDE(newProd.getProductNameJDE());
        this.setSrp03(newProd.getSrp03());
        this.setSrp09(newProd.getSrp09());
        this.setUserResRef(newProd.getUserResRef());
        this.setProductDescription(newProd.getProductDescription());
        this.setItemName(newProd.getItemName());
        this.setUnitPrice(newProd.getUnitPrice());
        this.setUnitPriceAmt(newProd.getUnitPriceAmt());
        this.setUnitOfMeasure(newProd.getUnitOfMeasure());
        this.setSrp01(newProd.getSrp01());
        this.setSrp02(newProd.getSrp02());
        this.setSrp04(newProd.getSrp04());
        this.setSrp05(newProd.getSrp05());
        this.setSrp06(newProd.getSrp06());
        this.setSrp07(newProd.getSrp07());
        this.setSrp08(newProd.getSrp08());
        this.setSrp010(newProd.getSrp010());
        this.setStockingType(newProd.getStockingType());
        this.setUserResCode(newProd.getUserResCode());
        this.setAvailability(newProd.getAvailability());
        this.setUnSPSCCode(newProd.getUnSPSCCode());
        this.setDescLine1(newProd.getDescLine1());
        this.setDescLine2(newProd.getDescLine2());
        this.setPackageDescription(newProd.getPackageDescription());
        this.setFavorite(newProd.getFavorite());
        this.setDefaultQuantity(newProd.getDefaultQuantity());
      
    } //mapped to JDE lineNumber in S0131 & S0239
      /**
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        
    }

    /**
     * @return
     */
    public Integer getQuantity() {
        return quantity;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setUndeliveredQuantity(Integer undeliveredQuantity) {
        this.undeliveredQuantity = undeliveredQuantity;
    }

    public Integer getUndeliveredQuantity() {
        return undeliveredQuantity;
    }


    public void setTracking(String tracking) {
        this.tracking = tracking;
    }

    public String getTracking() {
        return tracking;
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

    public void setTankId(String tankId) {
        this.tankId = tankId;
    }

    public String getTankId() {
        return tankId;
    }

    public void setTankDescription(String tankDescription) {
        this.tankDescription = tankDescription;
    }

    public String getTankDescription() {
        return tankDescription;
    }
    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setDensity(BigDecimal density) {
        this.density = density;
    }

    public BigDecimal getDensity() {
        return density;
    }

    public void setDssFlag(String dssFlag) {
        this.dssFlag = dssFlag;
    }

    public String getDssFlag() {
        return dssFlag;
    }

   

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceCompany(String invoiceCompany) {
        this.invoiceCompany = invoiceCompany;
    }

    public String getInvoiceCompany() {
        return invoiceCompany;
    }

    public void setOrderLastStatus(String orderLastStatus) {
        this.orderLastStatus = orderLastStatus;
    }

    public String getOrderLastStatus() {
        return orderLastStatus;
    }

    public void setOrderNextStatus(String orderNextStatus) {
        this.orderNextStatus = orderNextStatus;
    }

    public String getOrderNextStatus() {
        return orderNextStatus;
    }

    public void setDeliveryNumber(Integer deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public Integer getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setShippingCharges(Double shippingCharges) {
        this.shippingCharges = shippingCharges;
    }

    public Double getShippingCharges() {
        return shippingCharges;
    }

    public void setCreditOrderNumber(String creditOrderNumber) {
        this.creditOrderNumber = creditOrderNumber;
    }

    public String getCreditOrderNumber() {
        return creditOrderNumber;
    }

    public void setCreditOrderType(String creditOrderType) {
        this.creditOrderType = creditOrderType;
    }

    public String getCreditOrderType() {
        return creditOrderType;
    }

    public void setCreditOrderCompany(String creditOrderCompany) {
        this.creditOrderCompany = creditOrderCompany;
    }

    public String getCreditOrderCompany() {
        return creditOrderCompany;
    }

    public void setCreditOrderLineNumber(String creditOrderLineNumber) {
        this.creditOrderLineNumber = creditOrderLineNumber;
    }

    public String getCreditOrderLineNumber() {
        return creditOrderLineNumber;
    }

    public void setPieceTrackingNumber(String pieceTrackingNumber) {
        this.pieceTrackingNumber = pieceTrackingNumber;
    }

    public String getPieceTrackingNumber() {
        return pieceTrackingNumber;
    }

    public void setShipmentTrackingNumber(String shipmentTrackingNumber) {
        this.shipmentTrackingNumber = shipmentTrackingNumber;
    }

    public String getShipmentTrackingNumber() {
        return shipmentTrackingNumber;
    }

    public void setDeliveryFromDate(Date deliveryFromDate) {
        this.deliveryFromDate = deliveryFromDate;
    }

    public Date getDeliveryFromDate() {
        return deliveryFromDate;
    }

    public void setDeliveryToDate(Date deliveryToDate) {
        this.deliveryToDate = deliveryToDate;
    }

    public Date getDeliveryToDate() {
        return deliveryToDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setTankNumber(String tankNumber) {
        this.tankNumber = tankNumber;
    }

    public String getTankNumber() {
        return tankNumber;
    }

    public void setExemptionCode(String exemptionCode) {
        this.exemptionCode = exemptionCode;
    }

    public String getExemptionCode() {
        return exemptionCode;
    }

    public void setExemptionType(String exemptionType) {
        this.exemptionType = exemptionType;
    }

    public String getExemptionType() {
        return exemptionType;
    }

    public void setFullfillFlag(String fullfillFlag) {
        this.fullfillFlag = fullfillFlag;
    }

    public String getFullfillFlag() {
        return fullfillFlag;
    }

    public void setResellerPlanningFlag(String resellerPlanningFlag) {
        this.resellerPlanningFlag = resellerPlanningFlag;
    }

    public String getResellerPlanningFlag() {
        return resellerPlanningFlag;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setTripNumber(String tripNumber) {
        this.tripNumber = tripNumber;
    }

    public String getTripNumber() {
        return tripNumber;
    }

    public void setSpecialTreatment(String specialTreatment) {
        this.specialTreatment = specialTreatment;
    }

    public String getSpecialTreatment() {
        return specialTreatment;
    }

    public void setCompleteDelivery(String completeDelivery) {
        this.completeDelivery = completeDelivery;
    }

    public String getCompleteDelivery() {
        return completeDelivery;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setDetailActionType(String detailActionType) {
        this.detailActionType = detailActionType;
    }

    public String getDetailActionType() {
        return detailActionType;
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

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getVolume() {
        return volume;
    }

  
    public void setCurrrencyCode(String CurrrencyCode) {
        this.CurrrencyCode = CurrrencyCode;
    }

    public String getCurrrencyCode() {
        return CurrrencyCode;
    }

    public void setOriginalOrderLineNumber(BigDecimal originalOrderLineNumber) {
        this.originalOrderLineNumber = originalOrderLineNumber;
    }

    public BigDecimal getOriginalOrderLineNumber() {
        return originalOrderLineNumber;
    }

    public void setOriginalOrderNumber(String originalOrderNumber) {
        this.originalOrderNumber = originalOrderNumber;
    }

    public String getOriginalOrderNumber() {
        return originalOrderNumber;
    }

    public void setModifiedExemptionCode(String modifiedExemptionCode) {
        this.modifiedExemptionCode = modifiedExemptionCode;
    }

    public String getModifiedExemptionCode() {
        return modifiedExemptionCode;
    }

    public void setModifiedDutyStatusCode(String modifiedDutyStatusCode) {
        this.modifiedDutyStatusCode = modifiedDutyStatusCode;
    }

    public String getModifiedDutyStatusCode() {
        return modifiedDutyStatusCode;
    }

    public void setAdditionalAmount(BigDecimal additionalAmount) {
        this.additionalAmount = additionalAmount;
    }

    public BigDecimal getAdditionalAmount() {
        return additionalAmount;
    }

    public void setOrderLineType(String orderLineType) {
        this.orderLineType = orderLineType;
    }

    public String getOrderLineType() {
        return orderLineType;
    }

    public void setLineNumber(BigDecimal lineNumber) {
        this.lineNumber = lineNumber;
    }

    public BigDecimal getLineNumber() {
        return lineNumber;
    }

    public void setRebate(BigDecimal rebate) {
        this.rebate = rebate;
    }

    public BigDecimal getRebate() {
        return rebate;
    }

    public void setIsZeroFlagPrice(boolean isZeroFlagPrice) {
        this.isZeroFlagPrice = isZeroFlagPrice;
    }

    public boolean isIsZeroFlagPrice() {
        return isZeroFlagPrice;
    }

    public void setDutyStatusCode(String dutyStatusCode) {
        this.dutyStatusCode = dutyStatusCode;
    }

    public String getDutyStatusCode() {
        return dutyStatusCode;
    }

    public void setOrderStatusDescription(String orderStatusDescription) {
        this.orderStatusDescription = orderStatusDescription;
    }

    public String getOrderStatusDescription() {
        return orderStatusDescription;
    }
}

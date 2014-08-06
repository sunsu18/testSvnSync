package com.sfr.core.bean;

import java.math.BigDecimal;


/**
 * @author      Lopamudra Choudhury <lopamudra.choudhury@lntinfotech.com>
 * @version     1.6                 (the version of the package this class was first added to)
 * @since       2012-06-14          (a date or the version number of this program)
 */


public class Product extends BaseBean {

    private static final long serialVersionUID = 1L;
    private String productNo; // verified
    private String productNameJDE; //16July with h,m,l
    private String productDescription; // to be verified with PCM
    private String itemName;
    private int unitPrice; // to be verified with PCM --should be Double
    private BigDecimal unitPriceAmt; // replacement for unitPrice
    private String unitOfMeasure; // to be verified with PCM
    private String srp01;
    private String srp02;
    private String srp03;
    private String srp04;
    private String srp05;
    private String srp06;
    private String srp07;
    private String srp08;
    private String srp09;
    private String srp010;
    private String stockingType;
    private String userResCode;
    private String userResRef;
    private BigDecimal availability;
    private String unSPSCCode;
    private String DescLine1;
    private String DescLine2;
    private String PackageDescription;
    private Boolean favorite;
    private String defaultQuantity = "1";
    private BigDecimal discount;

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setProductNameJDE(String productNameJDE) {
        this.productNameJDE = productNameJDE;
    }

    public String getProductNameJDE() {
        return productNameJDE;
    }

    public void setSrp01(String srp01) {
        this.srp01 = srp01;
    }

    public String getSrp01() {
        return srp01;
    }

    public void setSrp02(String srp02) {
        this.srp02 = srp02;
    }

    public String getSrp02() {
        return srp02;
    }

    public void setSrp03(String srp03) {
        this.srp03 = srp03;
    }

    public String getSrp03() {
        return srp03;
    }

    public void setSrp04(String srp04) {
        this.srp04 = srp04;
    }

    public String getSrp04() {
        return srp04;
    }

    public void setSrp05(String srp05) {
        this.srp05 = srp05;
    }

    public String getSrp05() {
        return srp05;
    }

    public void setSrp06(String srp06) {
        this.srp06 = srp06;
    }

    public String getSrp06() {
        return srp06;
    }

    public void setSrp07(String srp07) {
        this.srp07 = srp07;
    }

    public String getSrp07() {
        return srp07;
    }

    public void setSrp08(String srp08) {
        this.srp08 = srp08;
    }

    public String getSrp08() {
        return srp08;
    }

    public void setSrp09(String srp09) {
        this.srp09 = srp09;
    }

    public String getSrp09() {
        return srp09;
    }

    public void setSrp010(String srp010) {
        this.srp010 = srp010;
    }

    public String getSrp010() {
        return srp010;
    }

    public void setStockingType(String stockingType) {
        this.stockingType = stockingType;
    }

    public String getStockingType() {
        return stockingType;
    }

    public void setUserResCode(String userResCode) {
        this.userResCode = userResCode;
    }

    public String getUserResCode() {
        return userResCode;
    }

    public void setUserResRef(String userResRef) {
        this.userResRef = userResRef;
    }

    public String getUserResRef() {
        return userResRef;
    }

    public void setUnitPriceAmt(BigDecimal unitPriceAmt) {
        this.unitPriceAmt = unitPriceAmt;
    }

    public BigDecimal getUnitPriceAmt() {
        return unitPriceAmt;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setAvailability(BigDecimal availability) {
        this.availability = availability;
    }

    public BigDecimal getAvailability() {
        return availability;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public void setUnSPSCCode(String unSPSCCode) {
        this.unSPSCCode = unSPSCCode;
    }

    public String getUnSPSCCode() {
        return unSPSCCode;
    }

    public void setDescLine1(String DescLine1) {
        this.DescLine1 = DescLine1;
    }

    public String getDescLine1() {
        return DescLine1;
    }

    public void setDescLine2(String DescLine2) {
        this.DescLine2 = DescLine2;
    }

    public String getDescLine2() {
        return DescLine2;
    }


    public void setPackageDescription(String PackageDescription) {
        this.PackageDescription = PackageDescription;
    }

    public String getPackageDescription() {
        return PackageDescription;
    }


    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setDefaultQuantity(String defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public String getDefaultQuantity() {
        return defaultQuantity;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}

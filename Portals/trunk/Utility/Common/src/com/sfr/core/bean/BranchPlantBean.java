package com.sfr.core.bean;

import com.sfr.util.AccessDataControl;
import com.sfr.util.ThreadSerialization;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import java.util.TreeSet;

import java.util.logging.Logger;

import oracle.adf.share.logging.ADFLogger;

import oracle.javatools.parser.java.v2.internal.compiler.Obj;

public class BranchPlantBean extends ThreadSerialization{
    private static final long serialVersionUID = 1L;
    boolean selectBranchPlant;
    String brBusinessUnit;
    String brDescription;
    Integer brAddrNo;
    Customer brCustomer;
    String userRole;
    Integer idmCustomerID;
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();

    public void setSelectBranchPlant(boolean selectBranchPlant) {
        this.selectBranchPlant = selectBranchPlant;
    }

    public boolean isSelectBranchPlant() {
        return selectBranchPlant;
    }

    public void setBrBusinessUnit(String brBusinessUnit) {
        this.brBusinessUnit = brBusinessUnit;
    }

    public String getBrBusinessUnit() {
        return brBusinessUnit;
    }

    public void setBrDescription(String brDescription) {
        this.brDescription = brDescription;
    }

    public String getBrDescription() {
        return brDescription;
    }

 

    public void setBrCustomer(Customer brCustomer) {
        this.brCustomer = brCustomer;
    }

    public Customer getBrCustomer() {
        return brCustomer;
    }

    public void setBrAddrNo(Integer brAddrNo) {
        this.brAddrNo = brAddrNo;
    }

    public Integer getBrAddrNo() {
        return brAddrNo;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setIdmCustomerID(Integer idmCustomerID) {
        this.idmCustomerID = idmCustomerID;
    }

    public Integer getIdmCustomerID() {
        return idmCustomerID;
    }

    @Override
    public boolean equals(Object obj) {
        log.info(accessDC.getDisplayRecord() + this.getClass() + " BranchPlantBean.equals : "+"Inside equals");
        if(obj!= null) {
            if(obj instanceof BranchPlantBean) {
                BranchPlantBean bp = (BranchPlantBean)obj;
                if (bp.getBrBusinessUnit() != null) {
                    if (bp.getBrBusinessUnit().equalsIgnoreCase(this.brBusinessUnit)) {
                        return true;
                    }
                }
            }    
        }
        log.info(accessDC.getDisplayRecord() + this.getClass() +" BranchPlantBean.equals : "+"obj!= null" + obj);
        
        return false;
        
    }
    
    @Override
    public int hashCode() {
        int prime = 17;
        int result = 1;
        
        if (this.brBusinessUnit != null) {
            result = result + this.brBusinessUnit.hashCode() * prime;
        }

        return result;
    }
}

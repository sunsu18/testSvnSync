package com.sfr.engage.model.module;

import com.sfr.engage.model.queries.rvo.ProductsDisplayRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtGenStringRVOImpl;

import oracle.jbo.server.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Mar 06 20:05:06 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class EngageAppModuleImpl extends ApplicationModuleImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public EngageAppModuleImpl() {
    }

    /**
     * Container's getter for PrtGenStringRVO1.
     * @return PrtGenStringRVO1
     */
    public PrtGenStringRVOImpl getPrtGenStringRVO1() {
        return (PrtGenStringRVOImpl)findViewObject("PrtGenStringRVO1");
    }

    /**
     * Container's getter for ProductsDisplayRVO1.
     * @return ProductsDisplayRVO1
     */
    public ProductsDisplayRVOImpl getProductsDisplayRVO1() {
        return (ProductsDisplayRVOImpl)findViewObject("ProductsDisplayRVO1");
    }
}

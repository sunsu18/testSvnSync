package com.sfr.engage.model.module;


import com.sfr.engage.model.module.common.EngageAppModule;
import com.sfr.engage.model.queries.rvo.ProductsDisplayRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtCardDriverVehicleInfoRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtCardTransactionInvoiceRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtCardTransactionOverviewRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtCustomerCardMapRVO1Impl;
import com.sfr.engage.model.queries.rvo.PrtGenHelpRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtGenStringRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtPcmFeedsRVOImpl;
import com.sfr.engage.model.queries.uvo.PrtAccountVOImpl;
import com.sfr.engage.model.queries.uvo.PrtCardTransactionHeaderVOImpl;
import com.sfr.engage.model.queries.uvo.PrtCardTransactionHeaderVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtCardVOImpl;
import com.sfr.engage.model.queries.uvo.PrtCardgroupVOImpl;
import com.sfr.engage.model.queries.uvo.PrtDriverInformationVOImpl;
import com.sfr.engage.model.queries.uvo.PrtInvoiceVOImpl;
import com.sfr.engage.model.queries.uvo.PrtPartnerVOImpl;
import com.sfr.engage.model.queries.uvo.PrtTruckInformationVOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;

// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Mar 06 20:05:06 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class EngageAppModuleImpl extends ApplicationModuleImpl implements EngageAppModule {
    private Date sysDate;
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


    /**
     * Container's getter for PriceListRVO1.
     * @return PriceListRVO1
     */
    public ViewObjectImpl getPriceListRVO1() {
        return (ViewObjectImpl)findViewObject("PriceListRVO1");
    }


    /**
     * Container's getter for PrtTruckInformationVO1.
     * @return PrtTruckInformationVO1
     */
    public ViewObjectImpl getPrtTruckInformationVO1() {
        return (ViewObjectImpl)findViewObject("PrtTruckInformationVO1");
    }

    /**
     * Container's getter for PrtTruckInformationVO2.
     * @return PrtTruckInformationVO2
     */
    public PrtTruckInformationVOImpl getPrtTruckInformationVO2() {
        return (PrtTruckInformationVOImpl)findViewObject("PrtTruckInformationVO2");
    }
/**
     * Container's getter for PrtDriverInformationVO1.
     * @return PrtDriverInformationVO1
     */
    public ViewObjectImpl getPrtDriverInformationVO1() {
        return (ViewObjectImpl)findViewObject("PrtDriverInformationVO1");
    }


    /**
     * Container's getter for PrtDriverInformationVO2.
     * @return PrtDriverInformationVO2
     */
    public PrtDriverInformationVOImpl getPrtDriverInformationVO2() {
        return (PrtDriverInformationVOImpl)findViewObject("PrtDriverInformationVO2");
    }

    /**
     * Container's getter for PriceListNewRVO1.
     * @return PriceListNewRVO1
     */
    public ViewObjectImpl getPriceListNewRVO1() {
        return (ViewObjectImpl)findViewObject("PriceListNewRVO1");
    }
/**
     * Container's getter for PrtGenHelpVO1.
     * @return PrtGenHelpVO1
     */
    public ViewObjectImpl getPrtGenHelpVO1() {
        return (ViewObjectImpl)findViewObject("PrtGenHelpVO1");
    }

    /**
     * Container's getter for PrtGenHelpRVO1.
     * @return PrtGenHelpRVO1
     */
    public ViewObjectImpl getPrtGenHelpRVO1() {
        return (ViewObjectImpl)findViewObject("PrtGenHelpRVO1");
    }
    
/**
     * @param accountId
     * @param type
     * @param countryCd
     * @param regDriverValue
     * This method is used to delete Driver/Truck details for the Account.
     */
    public void deleteAllForAccount(String accountId, String type, String countryCd, String regDriverValue ){
        System.out.println("Account Number in Application module===>"+accountId);
        System.out.println("type in application module====>"+type);
        System.out.println("countryCd in application module=====>"+countryCd);
        System.out.println("regDriverValue in apppilcation module====>"+regDriverValue);
        
        Connection con=null;
        PreparedStatement pStmt = null;
        String statement = null;
                try {
                    Statement stmt = getDBTransaction().createStatement(0);
                    con = stmt.getConnection();
                    if(type.equals("driver")){
                        if(regDriverValue!= null){
                        System.out.println("Inside this block of driver in application moule");
                        statement = "DELETE PRT_DRIVER_INFORMATION where ACCOUNT_ID = '"+accountId+"' and COUNTRY_CODE ='"+countryCd+"' and DRIVER_NAME ='"+regDriverValue+"'";
                        }else{
                            System.out.println("Inside else block of driver in application moule");
                        statement = "DELETE PRT_DRIVER_INFORMATION where ACCOUNT_ID = '"+accountId+"' and COUNTRY_CODE ='"+countryCd+"'";
                        }
                    }else{
                        if(regDriverValue!= null){
                        System.out.println("Inside this block of vehicle in application moule");
                        statement = "DELETE PRT_TRUCK_INFORMATION where ACCOUNT_NUMBER = '"+accountId+"' and REGISTRATION_NUMBER ='"+regDriverValue+"'";
                        }else{
                            System.out.println("Inside else block of vehilce in application moule");
                        statement = "DELETE PRT_TRUCK_INFORMATION where ACCOUNT_NUMBER = '"+accountId+"'";
                        }
                    }                    
                   
                    pStmt = con.prepareStatement(statement);   
                    pStmt.executeUpdate();
                    con.commit();
                } catch (SQLException sqle) {
                    sqle.getMessage();
                } finally {
                    try {
                        pStmt.close();
                    } catch (SQLException sqle) {
                        sqle.getMessage();
                    }
                }
    }
    
    public void updateOdometerPortal(String urefTransactionId, String palsCountryCode, String odoMeterPortalValue, String modifiedBy){
        System.out.println("transaction id in Application module===>"+urefTransactionId);
        System.out.println("countryCd in application module=====>"+palsCountryCode);
        System.out.println("odometer value in apppilcation module====>"+odoMeterPortalValue);
        
        try {
            ViewObject rvo = getPrtCardTransactionHeaderVO1();
            ViewCriteria vc = rvo.createViewCriteria();
            ViewCriteriaRow vcr = vc.createViewCriteriaRow();
            vcr.setAttribute("UrefTransactionId", urefTransactionId);
            vcr.setAttribute("PalsCountryCode", palsCountryCode);
            vc.add(vcr);
            rvo.applyViewCriteria(vc);
            rvo.executeQuery();

            while (rvo.hasNext()) {
                PrtCardTransactionHeaderVORowImpl currRow =
                    (PrtCardTransactionHeaderVORowImpl)rvo.next();
                if (currRow != null) {
                    currRow.setAttribute("OdometerPortal",odoMeterPortalValue);
                    currRow.setAttribute("ModifiedBy", modifiedBy);
                    currRow.setAttribute("LastModifiedDate",getSysDate());
                }
            }
            getDBTransaction().commit();
        }catch (JboException jbe) {
            jbe.getMessage();
        } 
        catch (Exception e) {
            e.getMessage();
        }
    }


    public String getWebServiceErrorMessage(String errorMessage, String countryCode) {
        String translatedValue = "";
        PrtGenStringRVOImpl vo = getPrtGenStringRVO1();
        ViewCriteria vc = vo.createViewCriteria();
        ViewCriteriaRow vcr = vc.createViewCriteriaRow();
        vcr.setAttribute("KeyCode", errorMessage);
        vc.add(vcr);
        vcr.setAttribute("TypeValue", "GENERAL_ERROR_MESSAGE");
        vc.add(vcr);
        vcr.setAttribute("LangCode", countryCode);
        vc.add(vcr);
        vo.applyViewCriteria(vc);
        vo.executeQuery();
        System.out.println("getGeneralErrorMessage Row Count:" + vo.getRowCount());
        while (vo.hasNext()) {
            Row tran = vo.next();
            translatedValue = (String)tran.getAttribute("KeyValue");
        }
        return translatedValue;
    }

    /**
     * Container's getter for PrtPcmFeedsRVO1.
     * @return PrtPcmFeedsRVO1
     */
    public ViewObjectImpl getPrtPcmFeedsRVO1() {
        return (ViewObjectImpl)findViewObject("PrtPcmFeedsRVO1");
    }

    /**
     * Container's getter for PrtCustomerCardMapRVO1_1.
     * @return PrtCustomerCardMapRVO1_1
     */
    public PrtCustomerCardMapRVO1Impl getPrtCustomerCardMapRVO1_1() {
        return (PrtCustomerCardMapRVO1Impl)findViewObject("PrtCustomerCardMapRVO1_1");
    }

    /**
     * Container's getter for PrtInvoiceVO1.
     * @return PrtInvoiceVO1
     */
    public ViewObjectImpl getPrtInvoiceVO1() {
        return (ViewObjectImpl)findViewObject("PrtInvoiceVO1");
    }

    /**
     * Container's getter for PrtCardVO1.
     * @return PrtCardVO1
     */
    public ViewObjectImpl getPrtCardVO1() {
        return (ViewObjectImpl)findViewObject("PrtCardVO1");
    }

    /**
     * Container's getter for PrtCardgroupVO1.
     * @return PrtCardgroupVO1
     */
    public ViewObjectImpl getPrtCardgroupVO1() {
        return (ViewObjectImpl)findViewObject("PrtCardgroupVO1");
    }

    /**
     * Container's getter for PrtPartnerVO1.
     * @return PrtPartnerVO1
     */
    public ViewObjectImpl getPrtPartnerVO1() {
        return (ViewObjectImpl)findViewObject("PrtPartnerVO1");
    }

    /**
     * Container's getter for PrtCardgroupVO2.
     * @return PrtCardgroupVO2
     */
    public ViewObjectImpl getPrtCardgroupVO2() {
        return (ViewObjectImpl)findViewObject("PrtCardgroupVO2");
    }

    /**
     * Container's getter for PrtCardVO2.
     * @return PrtCardVO2
     */
    public ViewObjectImpl getPrtCardVO2() {
        return (ViewObjectImpl)findViewObject("PrtCardVO2");
    }

    /**
     * Container's getter for PrtCardVO3.
     * @return PrtCardVO3
     */
    public ViewObjectImpl getPrtCardVO3() {
        return (ViewObjectImpl)findViewObject("PrtCardVO3");
    }

    /**
     * Container's getter for PrtCardgroupPrtPartnerFk1Link1.
     * @return PrtCardgroupPrtPartnerFk1Link1
     */
    public ViewLinkImpl getPrtCardgroupPrtPartnerFk1Link1() {
        return (ViewLinkImpl)findViewLink("PrtCardgroupPrtPartnerFk1Link1");
    }

    /**
     * Container's getter for PrtCardPrtCardgroupFk1Link1.
     * @return PrtCardPrtCardgroupFk1Link1
     */
    public ViewLinkImpl getPrtCardPrtCardgroupFk1Link1() {
        return (ViewLinkImpl)findViewLink("PrtCardPrtCardgroupFk1Link1");
    }

    /**
     * Container's getter for PrtCardPrtPartnerFk1Link1.
     * @return PrtCardPrtPartnerFk1Link1
     */
    public ViewLinkImpl getPrtCardPrtPartnerFk1Link1() {
        return (ViewLinkImpl)findViewLink("PrtCardPrtPartnerFk1Link1");
    }

    /**
     * Container's getter for PrtAccountVO1.
     * @return PrtAccountVO1
     */
    public PrtAccountVOImpl getPrtAccountVO1() {
        return (PrtAccountVOImpl)findViewObject("PrtAccountVO1");
    }


    /**
     * Container's getter for PrtAccountVO2.
     * @return PrtAccountVO2
     */
    public PrtAccountVOImpl getPrtAccountVO2() {
        return (PrtAccountVOImpl)findViewObject("PrtAccountVO2");
    }

    /**
     * Container's getter for PrtCardgroupVO3.
     * @return PrtCardgroupVO3
     */
    public PrtCardgroupVOImpl getPrtCardgroupVO3() {
        return (PrtCardgroupVOImpl)findViewObject("PrtCardgroupVO3");
    }

    /**
     * Container's getter for PrtCardVO4.
     * @return PrtCardVO4
     */
    public PrtCardVOImpl getPrtCardVO4() {
        return (PrtCardVOImpl)findViewObject("PrtCardVO4");
    }

    /**
     * Container's getter for PrtCardDriverVehicleInfoRVO1.
     * @return PrtCardDriverVehicleInfoRVO1
     */
    public PrtCardDriverVehicleInfoRVOImpl getPrtCardDriverVehicleInfoRVO1() {
        return (PrtCardDriverVehicleInfoRVOImpl)findViewObject("PrtCardDriverVehicleInfoRVO1");
    }

    /**
     * Container's getter for PrtInvoiceVO2.
     * @return PrtInvoiceVO2
     */
    public ViewObjectImpl getPrtInvoiceVO2() {
        return (ViewObjectImpl)findViewObject("PrtInvoiceVO2");
    }

    /**
     * Container's getter for PrtCardTransactionOverviewRVO1.
     * @return PrtCardTransactionOverviewRVO1
     */
    public PrtCardTransactionOverviewRVOImpl getPrtCardTransactionOverviewRVO1() {
        return (PrtCardTransactionOverviewRVOImpl)findViewObject("PrtCardTransactionOverviewRVO1");
    }

    /**
     * Container's getter for PrtCardTransactionInvoiceRVO1.
     * @return PrtCardTransactionInvoiceRVO1
     */
    public PrtCardTransactionInvoiceRVOImpl getPrtCardTransactionInvoiceRVO1() {
        return (PrtCardTransactionInvoiceRVOImpl)findViewObject("PrtCardTransactionInvoiceRVO1");
    }

    /**
     * Container's getter for PrtCardTransactionHeaderVO1.
     * @return PrtCardTransactionHeaderVO1
     */
    public ViewObjectImpl getPrtCardTransactionHeaderVO1() {
        return (ViewObjectImpl)findViewObject("PrtCardTransactionHeaderVO1");
    }

    public void setSysDate(Date sysDate) {
        this.sysDate = sysDate;
    }

    public Date getSysDate() {
        java.util.Date currDate=GregorianCalendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String sqlDateString=sdf.format(currDate);
        java.sql.Date sqlDate=java.sql.Date.valueOf(sqlDateString);
        Date newJboDate=new Date(sqlDate);
        this.setSysDate(newJboDate);
        return newJboDate;
    }

    /**
     * Container's getter for PrtLatestCardTransactionsRVO1.
     * @return PrtLatestCardTransactionsRVO1
     */
    public ViewObjectImpl getPrtLatestCardTransactionsRVO1() {
        return (ViewObjectImpl)findViewObject("PrtLatestCardTransactionsRVO1");
    }

    /**
     * Container's getter for PrtHomeInvoiceRVO1.
     * @return PrtHomeInvoiceRVO1
     */
    public ViewObjectImpl getPrtHomeInvoiceRVO1() {
        return (ViewObjectImpl)findViewObject("PrtHomeInvoiceRVO1");
    }

    /**
     * Container's getter for PrtHomeInvoiceRVO2.
     * @return PrtHomeInvoiceRVO2
     */
    public ViewObjectImpl getPrtHomeInvoiceRVO2() {
        return (ViewObjectImpl)findViewObject("PrtHomeInvoiceRVO2");
    }

    /**
     * Container's getter for PrtHomeTransactionsRVO1.
     * @return PrtHomeTransactionsRVO1
     */
    public ViewObjectImpl getPrtHomeTransactionsRVO1() {
        return (ViewObjectImpl)findViewObject("PrtHomeTransactionsRVO1");
    }
}

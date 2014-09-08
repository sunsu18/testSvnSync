package com.sfr.engage.model.module;


import com.sfr.engage.model.module.common.EngageAppModule;
import com.sfr.engage.model.queries.rvo.ProductsDisplayRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtCardDriverVehicleInfoRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtCardTransactionHeaderUrefIdUpdateOdometerRvoImpl;
import com.sfr.engage.model.queries.rvo.PrtCardTransactionInvoiceRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtCardTransactionOverviewRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtCardTransactionVehicleInfoRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtCardTypeNameMapVOImpl;
import com.sfr.engage.model.queries.rvo.PrtCustomerCardMapRVO1Impl;
import com.sfr.engage.model.queries.rvo.PrtExportInfoRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtGenHelpRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtGenStringRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtHomeTransactionsRVOImpl;
import com.sfr.engage.model.queries.rvo.PrtPcmFeedsRVOImpl;
import com.sfr.engage.model.queries.uvo.PrtAccountVOImpl;
import com.sfr.engage.model.queries.uvo.PrtCardTransactionHeaderVOImpl;
import com.sfr.engage.model.queries.uvo.PrtCardTransactionHeaderVORowImpl;
import com.sfr.engage.model.queries.uvo.PrtCardVOImpl;
import com.sfr.engage.model.queries.uvo.PrtCardgroupVOImpl;
import com.sfr.engage.model.queries.uvo.PrtDriverInformationVOImpl;
import com.sfr.engage.model.queries.uvo.PrtInvoiceDetailVoImpl;
import com.sfr.engage.model.queries.uvo.PrtInvoiceVOImpl;
import com.sfr.engage.model.queries.uvo.PrtNewInvoiceVOImpl;
import com.sfr.engage.model.queries.uvo.PrtPartnerVOImpl;
import com.sfr.engage.model.queries.uvo.PrtTruckInformationVOImpl;
import com.sfr.engage.model.queries.uvo.PrtUserPreferredLangVOImpl;
import com.sfr.engage.model.queries.uvo.PrtViewCardsVOImpl;
import com.sfr.engage.model.queries.uvo.PrtViewVehicleDriverVOImpl;
import com.sfr.util.AccessDataControl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;


// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Mar 06 20:05:06 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class EngageAppModuleImpl extends ApplicationModuleImpl implements EngageAppModule {
    public static final ADFLogger _logger = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();
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
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Account Number in Application module" + accountId);

            Connection con=null;
            PreparedStatement pStmt = null;
            String statement = null;
                    try {
                        Statement stmt = getDBTransaction().createStatement(0);
                        con = stmt.getConnection();
                        if(type.equals("driver")){
                            if(regDriverValue!= null){
                                _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside this block of driver in application moule");
                                statement = "DELETE PRT_DRIVER_INFORMATION where ACCOUNT_NUMBER = '"+accountId+"' and COUNTRY_CODE ='"+countryCd+"' and DRIVER_NAME ='"+regDriverValue+"'";
                            }else{
                                _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside else block of driver in application moule");
                                statement = "DELETE PRT_DRIVER_INFORMATION where ACCOUNT_NUMBER = '"+accountId+"' and COUNTRY_CODE ='"+countryCd+"'";
                            }
                        }else{
                            if(regDriverValue!= null){
                                _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside this block of vehicle in application moule");
                                statement = "DELETE PRT_TRUCK_INFORMATION where ACCOUNT_NUMBER = '"+accountId+"' and REGISTRATION_NUMBER ='"+regDriverValue+"'";
                            }else{
                                _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside else block of vehilce in application moule");
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

        public void updateVehicleDriver(String cardNumber, String type, String countryCd, String vehicleDriverValue,String associatedAccount,String modifiedBy ) {
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside update vehicle driver method in AM");
            Connection con=null;
            PreparedStatement pStmt = null;
            String statement = null;
            

            try {
               String modifiedDate = null;
                DateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
                java.util.Date effectiveFromDate =GregorianCalendar.getInstance().getTime();;
                modifiedDate = sdf.format(effectiveFromDate);
                
                Statement stmt = getDBTransaction().createStatement(0);
                con = stmt.getConnection();
                
                if(cardNumber == null) {
                    cardNumber=""; 
                }
                if(type.equals("Driver")){
                    if(vehicleDriverValue!= null){
                        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside this block of driver in application moule");
                        statement = "UPDATE PRT_DRIVER_INFORMATION SET CARD_NUMBER = '"+cardNumber+"', MODIFIED_BY = '"+modifiedBy+"',MODIFIED_DATE = '"+modifiedDate+"' where COUNTRY_CODE ='"+countryCd+"' and DRIVER_NUMBER ='"+vehicleDriverValue+"' and ACCOUNT_NUMBER ='"+associatedAccount+"'";
                    }
                 
                }else if(type.equals("Vehicle")){
                        if(vehicleDriverValue!= null ){
                            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside this block of vehicle in application moule");
                            statement = "UPDATE PRT_TRUCK_INFORMATION SET CARD_NUMBER = '"+cardNumber+"', MODIFIED_BY = '"+modifiedBy+"',MODIFIED_DATE = '"+modifiedDate+"' where COUNTRY_CODE ='"+countryCd+"' and VEHICLE_NUMBER ='"+vehicleDriverValue+"' and ACCOUNT_NUMBER ='"+associatedAccount+"'";
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

        public void updatePreviousOdometer(String cardNumber, String accountId, String countryCd, String partnerId, String transactionId, String previousOdometer ) {
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() +
                         "Inside update previous odometer");
            Connection con=null;
            PreparedStatement pStmt = null;
            String statement = null;
    
            try {
                Statement stmt = getDBTransaction().createStatement(0);
                con = stmt.getConnection();
                
                if(cardNumber != null && accountId != null && countryCd != null && partnerId != null && transactionId != null){
                _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside this block of update previous odometer in application moule");
                statement = "UPDATE PRT_CARD_TRANSACTION_HEADER SET PREVIOUS_ODOMETER = '"+previousOdometer+"' where PALS_COUNTRY_CODE ='"+countryCd+"' and KSID ='"+cardNumber+"' and ACCOUNT_ID ='"+accountId+"' and PARTNER_ID ='"+partnerId+"' and UREF_TRANSACTION_ID ='"+transactionId+"'";
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "Inside UpdateOdometerPortal mehthod in application module");
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
                    currRow.setAttribute("PortalModifiedDate",getSysDate());
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
        _logger.fine(accessDC.getDisplayRecord() + this.getClass() + "getGeneralErrorMessage Row Count: " + vo.getRowCount());
        while (vo.hasNext()) {
            Row tran = vo.next();
            translatedValue = (String)tran.getAttribute("KeyValue");
        }
        return translatedValue;
    }

    public String getTranslation(String translationkey, String ccCode) {
        String translatedValue = "";
        PrtGenStringRVOImpl vo = getPrtGenStringRVO1();
        ViewCriteria vc = vo.createViewCriteria();
        ViewCriteriaRow vcr = vc.createViewCriteriaRow();
        vcr.setAttribute("KeyCode", translationkey);
        vc.add(vcr);
        vcr.setAttribute("LangCode", ccCode);
        vc.add(vcr);
        vo.applyViewCriteria(vc);
        vo.executeQuery();
        if(vo!=null && vo.getRowCount()!=1){
            _logger.fine(accessDC.getDisplayRecord() + this.getClass() + ".getTranslation : vo=<" + vo.getRowCount()+"> key=<" +translationkey+"> and lang=<"+ccCode+">"+"vo.hasNext<"+vo.hasNext()+">");
        }
        while (vo.hasNext()) {
            Row tran = vo.next();
            if(tran.getAttribute("KeyValue") != null){
                translatedValue = (String)tran.getAttribute("KeyValue");
            }
            break;
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

    /**
     * Container's getter for PrtPartnerVO1.
     * @return PrtPartnerVO1
     */
    public PrtPartnerVOImpl getPrtPartnerVO1() {
        return (PrtPartnerVOImpl)findViewObject("PrtPartnerVO1");
    }

    /**
     * Container's getter for PrtAccountVO1.
     * @return PrtAccountVO1
     */
    public PrtAccountVOImpl getPrtAccountVO1() {
        return (PrtAccountVOImpl)findViewObject("PrtAccountVO1");
    }

    /**
     * Container's getter for PrtCardgroupVO1.
     * @return PrtCardgroupVO1
     */
    public PrtCardgroupVOImpl getPrtCardgroupVO1() {
        return (PrtCardgroupVOImpl)findViewObject("PrtCardgroupVO1");
    }


    /**
     * Container's getter for PrtDriverInformationVO3.
     * @return PrtDriverInformationVO3
     */
    public PrtDriverInformationVOImpl getPrtDriverInformationVO3() {
        return (PrtDriverInformationVOImpl)findViewObject("PrtDriverInformationVO3");
    }

    /**
     * Container's getter for PrtTruckInformationVO3.
     * @return PrtTruckInformationVO3
     */
    public PrtTruckInformationVOImpl getPrtTruckInformationVO3() {
        return (PrtTruckInformationVOImpl)findViewObject("PrtTruckInformationVO3");
    }
 /**
     * Container's getter for PrtExportInfoRVO1.
     * @return PrtExportInfoRVO1
     */
    public PrtExportInfoRVOImpl getPrtExportInfoRVO1() {
        return (PrtExportInfoRVOImpl)findViewObject("PrtExportInfoRVO1");
    }


    /**
     * Container's getter for PrtViewVehicleDriverVO1.
     * @return PrtViewVehicleDriverVO1
     */
    public PrtViewVehicleDriverVOImpl getPrtViewVehicleDriverVO1() {
        return (PrtViewVehicleDriverVOImpl)findViewObject("PrtViewVehicleDriverVO1");
    }

    /**
     * Container's getter for PrtCardVO1.
     * @return PrtCardVO1
     */
    public PrtCardVOImpl getPrtCardVO1() {
        return (PrtCardVOImpl)findViewObject("PrtCardVO1");
    }


    /**
     * Container's getter for PrtNewInvoiceVO2.
     * @return PrtNewInvoiceVO2
     */
    public PrtNewInvoiceVOImpl getPrtNewInvoiceVO2() {
        return (PrtNewInvoiceVOImpl)findViewObject("PrtNewInvoiceVO2");
    }

    /**
     * Container's getter for PrtInvoiceDetailVo1.
     * @return PrtInvoiceDetailVo1
     */
    public PrtInvoiceDetailVoImpl getPrtInvoiceDetailVo1() {
        return (PrtInvoiceDetailVoImpl)findViewObject("PrtInvoiceDetailVo1");
    }

    /**
     * Container's getter for PrtHomeInvoiceRVO1.
     * @return PrtHomeInvoiceRVO1
     */
    public ViewObjectImpl getPrtHomeInvoiceRVO1() {
        return (ViewObjectImpl)findViewObject("PrtHomeInvoiceRVO1");
    }

    /**
     * Container's getter for PrtNewInvoiceVO1.
     * @return PrtNewInvoiceVO1
     */
    public PrtNewInvoiceVOImpl getPrtNewInvoiceVO1() {
        return (PrtNewInvoiceVOImpl)findViewObject("PrtNewInvoiceVO1");
    }

    /**
     * Container's getter for PrtCardTransactionVehicleInfoRVO1.
     * @return PrtCardTransactionVehicleInfoRVO1
     */
    public PrtCardTransactionVehicleInfoRVOImpl getPrtCardTransactionVehicleInfoRVO1() {
        return (PrtCardTransactionVehicleInfoRVOImpl)findViewObject("PrtCardTransactionVehicleInfoRVO1");
    }

    /**
     * Container's getter for PrtCardTransactionHeaderUrefIdUpdateOdometerRvo1.
     * @return PrtCardTransactionHeaderUrefIdUpdateOdometerRvo1
     */
    public PrtCardTransactionHeaderUrefIdUpdateOdometerRvoImpl getPrtCardTransactionHeaderUrefIdUpdateOdometerRvo1() {
        return (PrtCardTransactionHeaderUrefIdUpdateOdometerRvoImpl)findViewObject("PrtCardTransactionHeaderUrefIdUpdateOdometerRvo1");
    }


    /**
     * Container's getter for PrtCardTypeNameMap1.
     * @return PrtCardTypeNameMap1
     */
    public ViewObjectImpl getPrtCardTypeNameMap1() {
        return (ViewObjectImpl)findViewObject("PrtCardTypeNameMap1");
    }

    /**
     * Container's getter for PrtViewCardsVO1.
     * @return PrtViewCardsVO1
     */
    public PrtViewCardsVOImpl getPrtViewCardsVO1() {
        return (PrtViewCardsVOImpl)findViewObject("PrtViewCardsVO1");
    }

    /**
     * Container's getter for PrtCardsPerRVO1.
     * @return PrtCardsPerRVO1
     */
    public ViewObjectImpl getPrtCardsPerRVO1() {
        return (ViewObjectImpl)findViewObject("PrtCardsPerRVO1");
    }


    /**
     * Container's getter for PrtUserPreferredLangVO1.
     * @return PrtUserPreferredLangVO1
     */
    public ViewObjectImpl getPrtUserPreferredLangVO1() {
        return (ViewObjectImpl)findViewObject("PrtUserPreferredLangVO1");
    }


    /**
     * Container's getter for PrtUserDisplayForAdminRVO1.
     * @return PrtUserDisplayForAdminRVO1
     */
    public ViewObjectImpl getPrtUserDisplayForAdminRVO1() {
        return (ViewObjectImpl)findViewObject("PrtUserDisplayForAdminRVO1");
    }

    /**
     * Container's getter for PrtUserDisplayForAccMgrRVO1.
     * @return PrtUserDisplayForAccMgrRVO1
     */
    public ViewObjectImpl getPrtUserDisplayForAccMgrRVO1() {
        return (ViewObjectImpl)findViewObject("PrtUserDisplayForAccMgrRVO1");
    }

    /**
     * Container's getter for PrtUserDisplayForCgMgrRVO1.
     * @return PrtUserDisplayForCgMgrRVO1
     */
    public ViewObjectImpl getPrtUserDisplayForCgMgrRVO1() {
        return (ViewObjectImpl)findViewObject("PrtUserDisplayForCgMgrRVO1");
    }

    /**
     * Container's getter for PrtUserDisplayForEmpRVO1.
     * @return PrtUserDisplayForEmpRVO1
     */
    public ViewObjectImpl getPrtUserDisplayForEmpRVO1() {
        return (ViewObjectImpl)findViewObject("PrtUserDisplayForEmpRVO1");
    }

    /**
     * Container's getter for PrtCardFuelCapacityVO1.
     * @return PrtCardFuelCapacityVO1
     */
    public ViewObjectImpl getPrtCardFuelCapacityVO1() {
        return (ViewObjectImpl)findViewObject("PrtCardFuelCapacityVO1");
    }

    /**
     * Container's getter for PrtCardRuleBusinessHoursVO1.
     * @return PrtCardRuleBusinessHoursVO1
     */
    public ViewObjectImpl getPrtCardRuleBusinessHoursVO1() {
        return (ViewObjectImpl)findViewObject("PrtCardRuleBusinessHoursVO1");
    }

    /**
     * Container's getter for PrtCardRuleSubscriptionVO1.
     * @return PrtCardRuleSubscriptionVO1
     */
    public ViewObjectImpl getPrtCardRuleSubscriptionVO1() {
        return (ViewObjectImpl)findViewObject("PrtCardRuleSubscriptionVO1");
    }

    /**
     * Container's getter for PrtCardRuleBusinessHoursVO2.
     * @return PrtCardRuleBusinessHoursVO2
     */
    public ViewObjectImpl getPrtCardRuleBusinessHoursVO2() {
        return (ViewObjectImpl)findViewObject("PrtCardRuleBusinessHoursVO2");
    }

    /**
     * Container's getter for PrtCardFuelCapacityVO2.
     * @return PrtCardFuelCapacityVO2
     */
    public ViewObjectImpl getPrtCardFuelCapacityVO2() {
        return (ViewObjectImpl)findViewObject("PrtCardFuelCapacityVO2");
    }

    /**
     * Container's getter for PrtCardRuleBusinessHoFk1Link1.
     * @return PrtCardRuleBusinessHoFk1Link1
     */
    public ViewLinkImpl getPrtCardRuleBusinessHoFk1Link1() {
        return (ViewLinkImpl)findViewLink("PrtCardRuleBusinessHoFk1Link1");
    }

    /**
     * Container's getter for PrtCardFuelCapacityPrFk1Link1.
     * @return PrtCardFuelCapacityPrFk1Link1
     */
    public ViewLinkImpl getPrtCardFuelCapacityPrFk1Link1() {
        return (ViewLinkImpl)findViewLink("PrtCardFuelCapacityPrFk1Link1");
    }
}

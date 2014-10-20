package com.sfr.engage.model.module.client;


import com.sfr.engage.model.module.common.EngageAppModule;

import oracle.jbo.client.remote.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Mar 24 13:52:24 IST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class EngageAppModuleClient extends ApplicationModuleImpl implements EngageAppModule {
    /**
     * This is the default constructor (do not remove).
     */
    public EngageAppModuleClient() {
    }


    public void deleteAllForAccount(String accountId, String type, String countryCd, String regDriverValue) {
        Object _ret =
            this.riInvokeExportedMethod(this, "deleteAllForAccount", new String[] { "java.lang.String", "java.lang.String", "java.lang.String", "java.lang.String" },
                                        new Object[] { accountId, type, countryCd, regDriverValue });
        return;
    }

    public String getWebServiceErrorMessage(String errorMessage, String countryCode) {
        Object _ret =
            this.riInvokeExportedMethod(this, "getWebServiceErrorMessage", new String[] { "java.lang.String", "java.lang.String" }, new Object[] { errorMessage,
                                                                                                                                                   countryCode });
        return (String)_ret;
    }

    public void updateOdometerPortal(String urefTransactionId, String palsCountryCode, String odoMeterPortalValue, String modifiedBy) {
        Object _ret =
            this.riInvokeExportedMethod(this, "updateOdometerPortal", new String[] { "java.lang.String", "java.lang.String", "java.lang.String", "java.lang.String" },
                                        new Object[] { urefTransactionId, palsCountryCode, odoMeterPortalValue, modifiedBy });
        return;
    }

    public String getTranslation(String translationkey, String ccCode) {
        Object _ret =
            this.riInvokeExportedMethod(this, "getTranslation", new String[] { "java.lang.String", "java.lang.String" }, new Object[] { translationkey,
                                                                                                                                        ccCode });
        return (String)_ret;
    }

    public void updatePreviousOdometer(String cardNumber, String accountId, String countryCd, String partnerId, String transactionId,
                                       String previousOdometer) {
        Object _ret =
            this.riInvokeExportedMethod(this, "updatePreviousOdometer", new String[] { "java.lang.String", "java.lang.String", "java.lang.String", "java.lang.String",
                                                                                       "java.lang.String", "java.lang.String" },
                                        new Object[] { cardNumber, accountId, countryCd, partnerId, transactionId, previousOdometer });
        return;
    }

    public void updateVehicleDriver(String cardNumber, String type, String countryCd, String vehicleDriverValue, String associatedAccount, String modifiedBy) {
        Object _ret =
            this.riInvokeExportedMethod(this, "updateVehicleDriver", new String[] { "java.lang.String", "java.lang.String", "java.lang.String", "java.lang.String",
                                                                                    "java.lang.String", "java.lang.String" },
                                        new Object[] { cardNumber, type, countryCd, vehicleDriverValue, associatedAccount, modifiedBy });
        return;
    }

    public void deleteAlert(String subsId, String UserId, String countryCd, String RuleId) {
        Object _ret =
            this.riInvokeExportedMethod(this, "deleteAlert", new String[] { "java.lang.String", "java.lang.String", "java.lang.String", "java.lang.String" },
                                        new Object[] { subsId, UserId, countryCd, RuleId });
        return;
    }
}
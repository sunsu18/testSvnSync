package com.sfr.engage.model.resources;

import java.util.ListResourceBundle;
import com.sfr.engage.model.queries.rvo.PrtGenStringRVOImpl;

import com.sfr.engage.model.queries.rvo.PrtGenStringRVORowImpl;

import com.sfr.engage.utility.util.AccessDataControl;

import java.sql.SQLException;

import java.util.HashMap;


import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.ApplicationModule;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.client.Configuration;

public class EngageResourceBundle extends ListResourceBundle{
    
    //public static final ADFLogger _logger = AccessDataControl.getSFRLogger();
        
        public EngageResourceBundle() {
            super();
        }
        
        /**
             * This variable holds all keys and values of UI component text resource for localization.
             */
        private Object contents [][]={{"TOP_PRODUCTS_ENGAGE",""},{"SIGN_IN",""},{"CARD_PORTAL",""},
                                      {"CARD_SERVICES_WHENEVER_YOU_WANT",""},{"CLICK_THE_BELOW_LINK_TO_LOGIN",""},
                                      {"NEW_TO_CARD",""},{"REGISTER_HERE",""},{"CARD_GROUPS",""},{"CARD_GROUPS_DESC",""},
                                      {"STATOIL_COMPANY_CARD",""},{"STATOIL_COMPANY_CARD_DESC",""},{"LEARN_MORE",""},
                                      {"APPLY_NOW",""},{"STATOIL_TRUCK_CARD_DESC",""},{"STATOIL_TRUCK_CARD",""},{"LOG_IN",""},
                                      {"STATOIL_EUROPE_CARD_DESC",""},{"STATOIL_EUROPE_CARD",""},{"STATOIL_MASTER_CARD",""},
                                      {"STATOIL_MASTER_CARD_DESC",""},{"LOGIN_IN_ENGAGE",""},{"VIEW_CATALOG",""},
					{ "PRICES", "" }, { "LATEST_UPDATE", "" }, { "PRODUCTS", "" }, { "UNIT_PRICE", "" },
    { "DRIVER_INFO", "" },{ "VEHICLE_INFO", "" }, { "LINKED_ACCOUNT", "" },{ "DRIVER_NAME", "" },{ "REGISTRATION_NUMBER", "" },{ "SEARCH_RESULTS", "" },
    { "SAVE", "" },{ "DELETE", "" }, { "ADD", "" },  { "ACCOUNT", "" } ,  { "CANCEL", "" }, { "SEARCH", "" },                                     
    { "VEHICLE_NUMBER", "" },{ "CARD_NUMBER", "" }, { "INTERNAL_NAME", "" },  { "REMARKS", "" }, { "FUEL_TYPE", "" }, 
    { "MAX_FUEL", "" },{ "ODOMETER", "" }, { "MORE_DETAILS", "" },  { "NATIONALITY", "" }, { "MOBILE_NUMBER", "" }, { "DRIVER_NUMBER", "" }
                                      };
               

            /**
             * This method populates the values of all resource keys from database through PrtGenTranslationVO based on lang parameter.
             * @return
             */
            @Override
            protected Object[][] getContents() {
                //_logger.info("Inside getContents() method,to fetch all keys and values for specific language.");
                long startTime = System.currentTimeMillis();
                clearCache();
                
                String langValue;
                String key       = "";
                String value     = "";
                
                ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
                HttpServletRequest request = (HttpServletRequest)ectx.getRequest();
                HttpSession session = request.getSession();
                
                if (session.getAttribute("lang") != null) {
                    langValue = (String)session.getAttribute("lang");
                } else {
                    langValue = "en_US";
                }
                
                try {
                    
                    if (session.getAttribute("TRANSLATION_" + langValue) != null) {
                        contents = (Object[][])session.getAttribute("TRANSLATION_" + langValue);
                        return contents;
                    }
                    
                    HashMap<String, String> map = parseHashMap(contents);
                    
                    String amDef = "com.sfr.engage.model.module.EngageAppModule";
                    String config = "EngageAppModuleLocal";

                    ApplicationModule am =
                        Configuration.createRootApplicationModule(amDef, config);
                    
                    PrtGenStringRVOImpl vo =
                        (PrtGenStringRVOImpl)am.findViewObject("PrtGenStringRVO1");

                    ViewCriteria vc = vo.createViewCriteria();
                    ViewCriteriaRow vcr1 = vc.createViewCriteriaRow();
                    vcr1.setAttribute("LangCode", langValue);
                    vc.add(vcr1);
                    vo.applyViewCriteria(vc);
                    vo.executeQuery();
                    
                    while (vo.hasNext()) {
                        PrtGenStringRVORowImpl currRow = (PrtGenStringRVORowImpl)vo.next();
                        key = currRow.getKeyCode();
                        value = currRow.getKeyValue();

                        if (map.containsKey(key)) {
                            map.put(key, value);
                        }
                    }
                    vo.clearCache();

                    Configuration.releaseRootApplicationModule(am, true);

                    parseArray(map);
                } 
                catch(SQLException sqe){
                    //_logger.severe("Unexpected Exception on execution of sql query");
                    //sqe.getMessage();
                }
                catch (Exception e) {
                    // TODO: Add catch code
                    //_logger.severe("Unexpected Exception on execution of sql query inside main Exception catch block.");
                    //e.printStackTrace();
                }
                
                long elapsedTime = System.currentTimeMillis() - startTime;

                session.setAttribute("TRANSLATION_" + langValue, contents);
                
                //_logger.info("Exiting getContents() method.");
                return contents;
            }
            
            /**
             *This methods replaces default values of keys with those retrieved from database.
             * @param map
             */
            private void parseArray(HashMap<String, String> map) {
                //_logger.info("Inside parseArray method.");
                for (int i = 0; i < contents.length; i++) {
                    contents[i][1] = map.get(contents[i][0]);
                }
                //_logger.info("Exiting parseArray method.");
            }

            /**
             *This methods replaces default values of keys with those retrieved from database.
             * @param params
             * @return
             * @throws Exception
             */
            private HashMap<String, String> parseHashMap(Object[][] params) throws Exception {
                //_logger.info("Inside parseHashMap method.");
                try {
                    HashMap<String, String> map = new HashMap<String, String>(params.length);
                    for (int i = 0; i < params.length; i++) {
                        map.put((String)params[i][0], (String)params[i][1]);
                    }
                    return map;
                } catch (Exception e) {
                    //_logger.severe("Unexpected Exception caught while putting databse keys and value.");
                    //e.printStackTrace();
                    throw e;
                }
            }

    
}

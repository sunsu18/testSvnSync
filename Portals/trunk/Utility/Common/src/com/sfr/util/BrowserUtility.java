package com.sfr.util;

import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;

import oracle.binding.BindingContainer;

import oracle.jbo.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.context.Agent;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class BrowserUtility {
    public BrowserUtility() {
        super();
    }
    
    
    /**
     * ADF Logger declaration
     * Author : CKH Balu
     */
    //public static final ADFLogger LOGGER = ADFLogger.createADFLogger(BrowserUtility.class);
    public static final ADFLogger LOGGER = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();
    
    
    
    /**
     * Todo
     */
    public static void refreshManually() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExtendedRenderKitService service = Service.getRenderKitService(context, ExtendedRenderKitService.class);
        service.addScript(context, "refreshPage()");
    }
    
    
    /**
     * Todo
     */
    
    
    public static void checkUserBrowser() {         
        AccessDataControl accessDC = new AccessDataControl();
        LOGGER.info("CheckUserBrowser() method execution starts here......");
       
        RequestContext requestCtx = RequestContext.getCurrentInstance();
        Agent agent = requestCtx.getAgent();
        String version = agent.getAgentVersion();
        String browser = agent.getAgentName();
        String platform = agent.getPlatformName();
        String platformVersion = agent.getPlatformVersion();

         LOGGER.info("===================================================");
         LOGGER.info("Your browser information: ");
         LOGGER.info("===================================================");
         LOGGER.info("Browser: "+browser);
         LOGGER.info("Browser Version : "+version);
         LOGGER.info("Browser Platform: "+platform);
         LOGGER.info("Browser Platform Version: "+platformVersion);
         LOGGER.info("===================================================");
        
        LOGGER.info(accessDC.getDisplayRecord()+"BrowserUtility.checkUserBrowser : Your browser information: \n"
        +"====== \n Browser: "+browser+"\n Browser Version : "+version
        +"\n Browser Platform: "+platform+"Browser Platform Version: "+platformVersion+"======");
        
        
        if ( "webkit".equalsIgnoreCase(browser) && (StringUtils.substringBefore(version, ".")).equalsIgnoreCase("535") ) {
            LOGGER.info("It is Chrome browser");
            LOGGER.info(accessDC.getDisplayRecord()+"BrowserUtility.checkUserBrowser : "+"It is Chrome browser");
            refreshManually();
        }
        else if ("webkit".equalsIgnoreCase(browser) && (StringUtils.substringBefore(version, ".")).equalsIgnoreCase("534") ) {
            LOGGER.info("It is Safari browser");
            LOGGER.info(accessDC.getDisplayRecord()+"BrowserUtility.checkUserBrowser : "+"It is Safari browser");
            refreshManually();            
        }
        else if ("ie".equalsIgnoreCase(browser) ) {
            if ("7".equalsIgnoreCase(StringUtils.substringBefore(version, "."))) {
                LOGGER.info("Internet Explorer 7");
                LOGGER.info(accessDC.getDisplayRecord()+"BrowserUtility.checkUserBrowser : "+"Internet Explorer 7");
            }
            else if ("8".equalsIgnoreCase(StringUtils.substringBefore(version, "."))) {
                LOGGER.info("Internet Explorer 8");
                LOGGER.info(accessDC.getDisplayRecord()+"BrowserUtility.checkUserBrowser : "+"Internet Explorer 8");
            }
            else if ("9".equalsIgnoreCase(StringUtils.substringBefore(version, "."))) {
                LOGGER.info("Internet Explorer 9");
                LOGGER.info(accessDC.getDisplayRecord()+"BrowserUtility.checkUserBrowser : "+"Internet Explorer 9");
            }
            else {
                LOGGER.info("New version of IE");
                LOGGER.info(accessDC.getDisplayRecord()+"BrowserUtility.checkUserBrowser : "+"New version of IE");
            } 
            refreshManually();
        }
        else if ("gecko".equalsIgnoreCase(browser)) {
            LOGGER.info("Firefox browser");
            refreshManually();
        }
        else {
            refreshManually();
            LOGGER.info("Some other browser not in category IE, Firefox, Chrome, Safari. Check with Portal Administrator if there is any browser incompatiable issue.");
        }
        
        
        LOGGER.info("MyUtils-checkUserBrowser() method execution ends");
    }
    
    public static BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    
    /**
     * Todo
     * @param iterator  EL expression
     * @return ViewObject
     */
    
    
    public static ViewObject currentRowOfVO(String iterator) {
        DCBindingContainer dcCPVO = (DCBindingContainer)getBindings();
        DCIteratorBinding iterCPVO = dcCPVO.findIteratorBinding(iterator);
        if (iterCPVO == null)
            return null;
        return iterCPVO.getViewObject();
    }
}

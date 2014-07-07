package com.sfr.util;


import com.sfr.core.bean.BranchPlantBean;
import com.sfr.core.bean.User;
import com.sfr.util.constants.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.sql.DataSource;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;


public class AccessDataControl extends ThreadSerialization {

    private static final long serialVersionUID = -2965850154831147949L;

    public AccessDataControl() {
        super();
    }

    /**
     *
     * @param methodName
     * @param paramMap
     * @return Object:This is the return value of the method in application module.
     */
    public void callAppModuleMethod(String methodName, HashMap paramMap) throws Exception {
        OperationBinding operationBinding = bindClientMethodWithParameters(methodName, paramMap);
        operationBinding.execute();
        if(!operationBinding.getErrors().isEmpty()){
            List list=operationBinding.getErrors();
            Iterator itr=list.iterator();
            String error="";
            while(itr.hasNext()){
                error=itr.next().toString();
                //System.out.println(AccessDataControl.getDisplayRecord()+".methodName : "+"---inside iterator callAppModuleMethod----"+itr.next());
            }
            System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.callAppModuleMethod : Error occured " +error);
            throw new Exception(error);
        } else {
            System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.callAppModuleMethod : No Error Occured while calling method :" +methodName);
        }
    }

    /**
     *
     * @param methodName
     * @param paramMap
     * @return
     */
    public String callDCForErrorMsg(String methodName, HashMap paramMap) {
        OperationBinding operationBinding = bindClientMethodWithParameters(methodName, paramMap);
        operationBinding.execute();
        return (String)operationBinding.getResult();
    }

    /**
     *
     * @return
     */
    public static BindingContainer getBindings() {

        if (BindingContext.getCurrent().getCurrentBindingsEntry() == null) {// This code added 'cause
            System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.getBindings: null one");  // in Partner Portal phase listener sometimes it
            DCBindingContainer container = null;                            // finds BindingContext.getCurrent().getCurrentBindingsEntry()==null.
            BindingContext bcx = BindingContext.getCurrent();               // This doesnot happen for WsPortal.
            if (bcx != null) {
                System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.getBindings: bcx not null one");
                container = bcx.findBindingContainer("portal_partnerTemplatePageDef");

                System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.getBindings: container" + container);
                return container;
            }
        }
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     *
     * @param methodName
     * @param paramMap
     * @return
     */
    public Map<String, Object> populateComboBoxValues(String methodName,
                                            HashMap paramMap) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        OperationBinding operationBinding = bindClientMethodWithParameters(methodName, paramMap);
        if(operationBinding != null) {
            operationBinding.execute();
            returnMap = (Map<String, Object>)operationBinding.getResult();
        }
        else {
            System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.populateComboBoxValues : operationBinding is NULL for methodName=<" + methodName + "> and paramMap=<" + paramMap + ">");
        }
        return returnMap;
    }

    /**
     *
     * @param paramMap
     * @param operationBinding
     * @return
     */
    private OperationBinding bindParamList(HashMap paramMap,
                                           OperationBinding operationBinding) {
        Set<Object> keyList=null;
        if(paramMap != null &&  operationBinding != null){
        keyList = paramMap.keySet();
        Iterator itr;
        itr = keyList.iterator();

        while(itr.hasNext()){
              String key = (String)itr.next();

              operationBinding.getParamsMap().put(key, paramMap.get(key));
          }}
        return operationBinding;
    }

    /**
     *
     * @param methodName
     * @param paramMap
     * @return
     */
    private OperationBinding bindClientMethodWithParameters(String methodName,
                                                            HashMap paramMap) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = null;
        if (bindings != null) {
            operationBinding = bindings.getOperationBinding(methodName);
            if (operationBinding == null) {
                System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.bindClientMethodWithParameters : operationBinding is NULL");
            }
            if (paramMap != null) {
                bindParamList(paramMap, operationBinding);
            } else {
                System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.bindClientMethodWithParameters : paramMap is NULL");
            }
        } else {
            System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.bindClientMethodWithParameters : ERROR: Could not find Bindings");
        }
        return operationBinding;
    }

    /**
     *
     * @param methodName
     * @param typeValue
     * @param countryCode
     * @return
     */
    public Map<String, Object> populateMap(String methodName, String typeValue,
                                           String countryCode) {
        HashMap paramMap = new HashMap();
        paramMap.put("nameOfLOV", typeValue);
        paramMap.put("countryCode", countryCode);
        Map<String, Object> valueKey = new LinkedHashMap<String, Object>();
        valueKey = populateComboBoxValues(methodName, paramMap);

        return valueKey;
    }


    /**
     * Author: LnT Infotech (NH); July 25
     * This method fetches LOV information from a given table based on country code
     * @param methodName
     * @param countryCode
     * @return
     */
    public Map<String, Object> populateMap(String methodName,String countryCode) {
        HashMap paramList = new HashMap();
        paramList.put("countryCode",countryCode);
        Map<String, Object> valueKey= new LinkedHashMap<String, Object>();
        valueKey=populateComboBoxValues(methodName,paramList);
        return valueKey;
    }

    /**
       * @description This method is created to get List of Bean from (Reseller) client
       * @param methodName
       * @param paramMap
       * @return
       * @author Lopc
       */
      public Object callAppModuleMethodWithResultSet(String methodName, HashMap paramMap) throws Exception {
          OperationBinding operationBinding=null;
          if(bindClientMethodWithParameters(methodName, paramMap)!=null)
           operationBinding = bindClientMethodWithParameters(methodName, paramMap);

          if(operationBinding!=null){
          operationBinding.execute();

          if(!operationBinding.getErrors().isEmpty()){
              List list=operationBinding.getErrors();
              Iterator itr=list.iterator();
              String error="";
              while(itr.hasNext()){
                  error=itr.next().toString();
                  //System.out.println(AccessDataControl.getDisplayRecord()+".methodName : "+"---inside iterator callAppModuleMethod----"+itr.next());
              }
              System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.callAppModuleMethodWithResultSet : Error occured " +error);
              throw new Exception(error);
          }

          return operationBinding.getResult();
          }else{
              return "";
          }
      }

    /**
     * @param featureName
     * @param featureValue
     * @param controlAttr
     * @param controlAttrValue
     * @return
     */
    public String getFeatureValueBySQL(String featureName, String featureValue, String controlAttr, String controlAttrValue) {

        List<String> result = null;
        String value = null;
        List<String> params = new ArrayList<String>();
        params.add(featureName);
        params.add(featureValue);
        params.add(controlAttr);
        params.add(controlAttrValue);
        try {
            result =
                executeSqlStmt("select value from prt_gen_feature where trim(feature_name) = ? and  trim(feature_value) = ? and trim(control_attr) = ? and trim(control_attr_value) = ? ",params, "value");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        if (result != null && result.size() > 0) {
            value = result.get(0);
        }
        return value;
    }

    public String getStringValueBySQL(String type, String typevalue, String keycode, String langcode) {

        List<String> result = null;
        String keyvalue = null;
        List<String> params = new ArrayList<String>();
        params.add(type);
        params.add(typevalue);
        params.add(keycode);
        params.add(langcode);
        try {
            result =
                executeSqlStmt("select key_value from prt_gen_string where trim(type) = ? and trim(type_value) = ? and trim(key_code) = ? and trim(lang_code) = ? ",params, "key_value");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        if (result != null && result.size() > 0) {
            keyvalue = result.get(0);
        }
        return keyvalue;
    }

    public List<BranchPlantBean> getBranchPlantListBySQL(int managerID) {
        List<BranchPlantBean> branchPlantList = new ArrayList<BranchPlantBean>();
        List<String> params = new ArrayList<String>();
        Integer managerIDInt = managerID;
        if(managerIDInt != null){
        String mngrID = managerIDInt.toString();
        params.add(mngrID);
        }
        try {
            branchPlantList =
                executeSqlStmtBranchplant("select BUSINESS_UNIT,DESCRIPTION,ADDRESS_NUMBER from prt_load_branch_plant where trim(BUSINESS_UNIT) = ? ",params);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return branchPlantList;
    }

    public String getFeatureValue(String methodName, String featureName, String featureValue, String controlAttr, String controlAttrValue) {
        System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.getFeatureValue : methodName=<" + methodName + "> featureName" + featureName + "> featureValue=<" +
                           featureValue + "> controlAttr=<" + controlAttr + "> controlAttrValue=<" + controlAttrValue + ">");
        HashMap paramMap = new HashMap();

        paramMap.put("featureName", featureName);
        paramMap.put("featureValue", featureValue);
        paramMap.put("controlAttr", controlAttr);
        paramMap.put("controlAttrValue", controlAttrValue);

        OperationBinding operationBinding = bindClientMethodWithParameters(methodName, paramMap);
        if(operationBinding == null) {
            System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.getFeatureValue : operationBinding is Null");
            return "";
        }
        else{
            System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.getFeatureValue : operationBinding is NOT Null");
            operationBinding.execute();
        return (String)operationBinding.getResult();
        }
    }

    /**
     * @param methodName
     * @param airlineCode
     * @return
     */
    public String getAirlineCustomerNo(String methodName,String airlineCode,String destinationCountry){

                HashMap paramMap = new HashMap();
                paramMap.put("airlineCode", airlineCode);
                paramMap.put("destinationCountry",destinationCountry);
                OperationBinding operationBinding = bindClientMethodWithParameters(methodName, paramMap);
                operationBinding.execute();
                return (String)operationBinding.getResult();
            }

    public Map<String, Object> getLOVMap(String methodName) {
         HashMap paramList = new HashMap();
         Map<String, Object> valueKey= new LinkedHashMap<String, Object>();
         valueKey=populateComboBoxValues(methodName,paramList);
         return valueKey;
     }

     public String populateAirportCountry(String methodName,String airportCode) {

                 HashMap paramMap = new HashMap();

                 paramMap.put("airportCode", airportCode);

                 OperationBinding operationBinding = bindClientMethodWithParameters(methodName,paramMap);
                 operationBinding.execute();
                 return (String)operationBinding.getResult();
             }

     public String getAirlineCode(String methodName,Integer shippingAddress){

                 HashMap paramMap = new HashMap();
                 paramMap.put("shippingAddress", shippingAddress);
                 OperationBinding operationBinding = bindClientMethodWithParameters(methodName, paramMap);
                 operationBinding.execute();
                 return (String)operationBinding.getResult();
             }



     public String getShipToNo(String methodName,String airlineCode,String country){

                 HashMap paramMap = new HashMap();
                 paramMap.put("airlineCode", airlineCode);
                 paramMap.put("country", country);
                 OperationBinding operationBinding = bindClientMethodWithParameters(methodName, paramMap);
                 operationBinding.execute();
                 return (String)operationBinding.getResult();
             }

     public Map<String, Object> getLOVAirline(String methodName,String country) {
         HashMap paramList = new HashMap();
         paramList.put("country", country);
         Map<String, Object> valueKey= new LinkedHashMap<String, Object>();
         valueKey=populateComboBoxValues(methodName,paramList);
         return valueKey;
     }

    public static ViewObject currentRowOfVO(String iterator) {
        DCBindingContainer dcCPVO = (DCBindingContainer)getBindings();
        DCIteratorBinding iterCPVO = dcCPVO.findIteratorBinding(iterator);
        if (iterCPVO == null)
            return null;
        return iterCPVO.getViewObject();
    }

    /** This method is used in PCM to call "execute with param" method with view criteria from data control
     * @param executeParamsMethod
     * @param paramMap
     * @param iterator
     * @return
     * @throws Exception
     */
    public RowSetIterator executeWithParam(String executeParamsMethod, HashMap paramMap, String iterator) throws Exception {
        RowSetIterator rowSetItr = null;
        ViewObject viewObjectRVO = null;

        System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.executeWithParam : "+"------------------ACCESSDATACONTROL------------------- "
                                                                +"Execute Params Method Name : " +executeParamsMethod);
        Iterator entries = paramMap.entrySet().iterator();
        while (entries.hasNext()) {
          Map.Entry thisEntry = (Map.Entry) entries.next();

            System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.executeWithParam : "+"Map Key = " +thisEntry.getKey() +" --  Map Value = " +thisEntry.getValue());
        }

        callAppModuleMethod(executeParamsMethod, paramMap);
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();

        if (bindings != null) {
            DCIteratorBinding iterRVO = (DCIteratorBinding)bindings.get(iterator);
            if (iterRVO != null) {
                viewObjectRVO = iterRVO.getViewObject();
            } else {
                System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.executeWithParam : "+"ITERATOR NOT FOUND iterator=<" + iterator + "> in executeParamsMethod=<" + executeParamsMethod + ">");
            }
            if (viewObjectRVO != null)
                rowSetItr = viewObjectRVO.createRowSetIterator(null);
        } else {
            System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.executeWithParam : "+"NO BINDINGS FOUND for executeParamsMethod=<" + executeParamsMethod + ">");
        }

        return rowSetItr;
    }

     /** This method is used in PCM to call "execute with param" method with view criteria from data control for "Country dropdowns" or other LOVs
     * @param executeParams
     * @param paramMap
     * @param iterator
     * @return
     * @throws Exception
     */
    public Map<String,Object> getLovValues(String executeParams,
                                                           HashMap paramMap,
                                                           String iterator) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        RowSetIterator rowsAvailable =
        executeWithParam(executeParams,paramMap,iterator);

        while (rowsAvailable.hasNext()) {

            Row rr = rowsAvailable.next();
            if(rr.getAttribute("KeyValue") != null && rr.getAttribute("KeyCode") != null)
            map.put(rr.getAttribute("KeyValue").toString(), rr.getAttribute("KeyCode").toString());

        }
        return map;
    }

    /**
     * @param iterator
     * @return
     * @throws Exception
     */
    public RowSetIterator getDataFromViewIterator(String iterator) throws Exception {

        BindingContainer bindings =
            BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iterRVO = (DCIteratorBinding)bindings.get(iterator);
        ViewObject viewObjectRVO = iterRVO.getViewObject();
        RowSetIterator rowSetItr = viewObjectRVO.createRowSetIterator(null);

        return rowSetItr;
    }

    /**
     * Static method that returns the Prefix for the Logger.
     * E.g. [2013/05/23 14:07:36 : PORTAL : USEREMAIL : 325235XX]
     * @return
     */
    public static String getDisplayRecord() {
        HttpSession session = null;
        ExternalContext ectx;
        HttpServletRequest request;
        String emailId = null, sessionId = null;
        String portal = null;

        java.util.Date date= new java.util.Date();
        Timestamp t=new Timestamp(date.getTime());

        if (FacesContext.getCurrentInstance() != null) {
            ectx = FacesContext.getCurrentInstance().getExternalContext();
            request = (HttpServletRequest)ectx.getRequest();
            if (request != null) {
                session = request.getSession(false);
                if (session != null) {
                    // System.out.println(AccessDataControl.getDisplayRecord()+".methodName : "+"Session id is "+session.getId());
                    sessionId = session.getId().substring(session.getId().length() - 8);
                    if (session.getAttribute(Constants.SESSION_USER_INFO) != null) {
                        User userBean = (User)session.getAttribute(Constants.SESSION_USER_INFO);
                        if (userBean != null && userBean.getEmailID() != null) {
                            emailId = userBean.getEmailID();
                        }
                    }
                    if (session.getAttribute(Constants.SESSION_PORTAL_NAME) != null) {
                        portal = (String)session.getAttribute(Constants.SESSION_PORTAL_NAME);
                    }
                }
            }
        }
        String result = "[" + t + " : " + portal+" : "+ emailId + " : "+sessionId+  "]";
        return result;
    }

    public static ADFLogger getSFRLogger() {
        HttpSession session = null;
        ExternalContext ectx;
        HttpServletRequest request;
        String portal=null;
        if (FacesContext.getCurrentInstance() != null) {
            ectx = FacesContext.getCurrentInstance().getExternalContext();
            if (ectx != null) {
                request = (HttpServletRequest)ectx.getRequest();
                if (request != null) {
                    session = request.getSession(false);
                    if (session != null) {
                        if (session.getAttribute(Constants.SESSION_PORTAL_NAME) != null) {
                            portal = (String)session.getAttribute(Constants.SESSION_PORTAL_NAME);
                        }
                    }
                }
            }
        }

        ADFLogger logger=null;
        if(portal==null){
            portal=Constants.EN_PORTAL;
        }
        if(portal.equals(Constants.EN_PORTAL)){
            logger=ADFLogger.createADFLogger(ConfigurationUtility.getPropertyValue("LOGGER_ENGAGE"));
        }else if(portal.equals(Constants.PP_PORTAL)){
            logger=ADFLogger.createADFLogger(ConfigurationUtility.getPropertyValue("LOGGER_PARTNER"));
        }else if(portal.equals(Constants.PCM_PORTAL)){
            logger=ADFLogger.createADFLogger(ConfigurationUtility.getPropertyValue("LOGGER_PCM"));
        }else{
            logger=ADFLogger.createADFLogger(ConfigurationUtility.getPropertyValue("LOGGER_WEBSHOP"));
        }

        return logger;
    }

    public ViewObject fetchVOForBindParams(String executeParamsMethod, HashMap paramMap, String iterator) throws Exception {
        //RowSetIterator rowSetItr = null;
        ViewObject viewObjectRVO = null;

        System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.fetchVOForBindParams : "+"------------------ACCESSDATACONTROL-------------------"
                                                                +" Execute Params Method Name : " +executeParamsMethod);
        Iterator entries = paramMap.entrySet().iterator();
        while (entries.hasNext()) {
          Map.Entry thisEntry = (Map.Entry) entries.next();

            System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.fetchVOForBindParams : "+"Map Key = " +thisEntry.getKey()
                                                                                                            +"-- Map Value = " +thisEntry.getValue());
        }

        callAppModuleMethod(executeParamsMethod, paramMap);
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();

        if (bindings != null) {
            DCIteratorBinding iterRVO = (DCIteratorBinding)bindings.get(iterator);
            if (iterRVO != null) {
                viewObjectRVO = iterRVO.getViewObject();
            } else {
                System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.fetchVOForBindParams : "+"ITERATOR NOT FOUND iterator=<" + iterator + "> in executeParamsMethod=<" + executeParamsMethod + ">");
            }
            //if (viewObjectRVO != null)
               // rowSetItr = viewObjectRVO.createRowSetIterator(null);
        } else {
            System.out.println(AccessDataControl.getDisplayRecord()+"AccessDataControl.fetchVOForBindParams : "+"NO BINDINGS FOUND for executeParamsMethod=<" + executeParamsMethod + ">");
        }

        return viewObjectRVO;
    }

    public static List<String> executeSqlStmt(String sqlQuery, List<String> params, String returnParam) throws SQLException, NamingException {
        PreparedStatement objPrepStmt = null;
        ResultSet objRS = null;
        List<String> result = new ArrayList<String>();
        Connection connection = null;
        try {
            connection = getJNDIConnection();
            if (null != connection) {
                objPrepStmt = connection.prepareStatement(sqlQuery);
                if (null != objPrepStmt) {
                    for (int iCount = 0; iCount < params.size(); iCount++) {
                        objPrepStmt.setString(iCount + 1, params.get(iCount));
                    }
                    objRS = objPrepStmt.executeQuery();
                    if (null != objRS) {
                        while (objRS.next()) {
                            if (objRS.getString(returnParam) != null)//Adding while performing US16
                                result.add(objRS.getString(returnParam).trim());
                        }
                    }
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw sqe;
        } catch (NamingException ne) {
            ne.printStackTrace();
            throw ne;
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
        return result;
    }


    public static List<BranchPlantBean> executeSqlStmtBranchplant(String sqlQuery, List<String> params) throws SQLException, NamingException {
        PreparedStatement objPrepStmt = null;
        ResultSet objRS = null;
        List<BranchPlantBean> branchPlantList = new ArrayList<BranchPlantBean>();
        Connection connection = null;
        try {
            connection = getJNDIConnection();
            if (null != connection) {
                objPrepStmt = connection.prepareStatement(sqlQuery);
                if (null != objPrepStmt) {
                    for (int j = 0; j < params.size(); j++) {
                        objPrepStmt.setString(j + 1, params.get(0));
                    }
                    objRS = objPrepStmt.executeQuery();

                    System.out.println("Inside executeSqlStmtBranchplant - objRS - :"+objRS);
                    if (null != objRS) {
                        while (objRS.next()) {
                            BranchPlantBean branchPlantObject = new BranchPlantBean();
                            if (objRS.getString("BUSINESS_UNIT") != null)
                                branchPlantObject.setBrBusinessUnit(objRS.getString("BUSINESS_UNIT").trim());
                            if (objRS.getString("DESCRIPTION") != null)
                                branchPlantObject.setBrDescription(objRS.getString("DESCRIPTION").trim());
                                branchPlantObject.setBrAddrNo(objRS.getInt("ADDRESS_NUMBER"));
                                branchPlantList.add(branchPlantObject);
                        }
                    }
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw sqe;
        } catch (NamingException ne) {
            ne.printStackTrace();
            throw ne;
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
        return branchPlantList;
    }

    public static Connection getJNDIConnection() throws SQLException, NamingException {
        Connection connection = null;
        InitialContext objinitialContext = null;
        DataSource datasource = null;
        try {
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            objinitialContext = new InitialContext(env);
            datasource = (DataSource)objinitialContext.lookup(ConfigurationUtility.getPropertyValue("JNDI_NAME"));
            if (datasource != null) {
                connection = datasource.getConnection();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw sqle;
        } catch (NamingException ne) {
            ne.printStackTrace();
            throw ne;
        } finally {
            try {
                if (objinitialContext != null) {
                    objinitialContext.close();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                throw ne;
            }
        }
        return connection;
    }
}

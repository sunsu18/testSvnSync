package com.sfr.util.constants;


public class Constants {
    public Constants() {
        super();
    }


    // Constants required for Values sent by OIM WS
    public static final String NO = "No";
    public static final String YES = "No";


    // Constants to hold User Roles
    public static final String STATOIL_IMAGE_URL = "STATOIL_IMAGE_URL";
    public static final String SELECTED_ROLE_LIST = "SELECTED_ROLE_LIST";
    public static final String ROLE_WCP_B2C = "WCP_B2C";
    public static final String ROLE_WCP_B2BM = "WCP_B2BM";
    public static final String ROLE_WCP_B2BEMP = "WCP_B2BEMP";
    public static final String ROLE_WCP_RESM = "WCP_RESM";
    public static final String ROLE_WCP_RESEMP = "WCP_RESEMP";
    public static final String ROLE_WCP_ICSR = "WCP_ICSR";
    public static final String ROLE_WCP_ISSM = "WCP_ISSM";
    public static final String ROLE_WCP_ESSM = "WCP_ESSM";
    public static final String ROLE_WCP_ISSEMP = "WCP_ISSEMP";
    public static final String ROLE_WCP_ESSEMP = "WCP_ESSEMP";
    public static final String ROLE_WCP_AVSUP = "WCP_AVSUP";
    public static final String ROLE_WCP_AVEMP = "WCP_AVEMP";
    public static final String ROLE_WCP_MRSUP = "WCP_MRSUP";
    public static final String ROLE_WCP_MREMP = "WCP_MREMP";
    public static final String ROLE_WCP_WSM = "WCP_WSM";
    public static final String ROLE_WCP_ECSR = "WCP_ECSR";
    public static final String ROLE_WCP_SUPP = "WCP_SUPP";
    public static final String ROLE_WCP_SSSUPP = "WCP_Station_Support";
    public static final String ROLE_WCP_WEBSUP = "WCP_Webshop_Support";
    public static final String ROLE_WCP_AVISUP = "WCP_Aviation_Support";
    public static final String ROLE_WCP_MARSUP = "WCP_Marine_Support";
    public static final String ROLE_WCP_RESSUP = "WCP_Reseller_Support";
    public static final String ROLE_WCP_DSSCIT = "WCP_DSS_CIT";


    // for UCM
    public static final String UCM_USERNAME = "UCM_USERNAME";
    public static final String UCM_PASSWORD = "UCM_PASSWORD";

    public static final String WSPORTAL_LINK = "WSPORTAL_LINK";
    public static final String STATOILFUELRETAIL_LINK =
        "STATOILFUELRETAIL_LINK";

    public static final String ENGAGE_XCONTENTTYPE = "XCONTENTTYPE";
    public static final String ENGAGE_XSUBTYPE = "XSUBTYPE";


    //for PDF path
    public static final String TEMP_PDF_PATH = "TEMP_PDF_PATH";

    //Constants to hold the ParameterValue for OPSS call.
    public static final String OPSS_MAIL = "mail";
    public static final String OPSS_ORCL_IS_ENABLED = "orclIsEnabled";
    public static final String OPSS_MIDDLE_NAME = "middleName";
    public static final String OPSS_DOB = "dateOfBirth";
    public static final String OPSS_LANG = "language";
    public static final String OPSS_WCP_B2C = "B2C_CUSTOMERID";
    public static final String OPSS_WCP_B2BM = "B2BMANAGER_CUSTOMERID";
    public static final String OPSS_WCP_B2BEMP = "B2BEMPLOYEE_CUSTOMERID";
    public static final String OPSS_WCP_RESM = "RESELLER_CUSTOMERID";
    public static final String OPSS_WCP_RESEMP = "RESELLER_EMPLOYEEID";
    public static final String OPSS_WCP_ICSR = "INTERNAL_CSR";
    public static final String OPSS_WCP_ISSM = "SERVICESTATION_MANAGER";
    public static final String OPSS_WCP_ESSM = "SSTATION_MANAGEREXT";
    public static final String OPSS_WCP_ISSEMP = "SSTATION_EMPLOYEEINT";
    public static final String OPSS_WCP_ESSEMP = "SSTATION_EMPLOYEEEXT";
    public static final String OPSS_WCP_AVSUP = "ASSOCIATED_AIRPORTID";
    public static final String OPSS_WCP_AVEMP = "AVIATION_EMPLOYEE";
    public static final String OPSS_WCP_MRSUP = "MARINE_MANAGER";
    public static final String OPSS_WCP_MREMP = "MARINE_EMPLOYEE";
    public static final String OPSS_WCP_WSM = "WEBSHOP_MANAGER";

    //Constants for sessions
    public static final String SESSION_B2C_CUSTOMER =
        "SESSION_B2C_CUSTOMER"; //Object Customer
    public static final String SESSION_B2B_CUSTOMER =
        "SESSION_B2B_CUSTOMER"; //Object Customer
    public static final String SESSION_B2BEMP_CUSTOMER =
        "SESSION_B2BEMP_CUSTOMER"; //Object Customer
    public static final String SESSION_RESELLER_CUSTOMER =
        "SESSION_RESELLER_CUSTOMER"; //Object Customer
    public static final String SESSION_RESELLEREMP_CUSTOMER =
        "SESSION_RESELLEREMP_CUSTOMER"; //Object Customer
    public static final String SESSION_ICSR_CUSTOMER = "SESSION_ICSR_CUSTOMER";
    public static final String SESSION_ISSM_BRANCHPLANT =
        "SESSION_ISSM_BRANCHPLANT"; //Object BranchPlantBean
    public static final String SESSION_ESSM_BRANCHPLANT =
        "SESSION_ESSM_BRANCHPLANT"; //Object BranchPlantBean
    public static final String SESSION_ISSEMP_BRANCHPLANT =
        "SESSION_ISSEMP_BRANCHPLANT"; //Object BranchPlantBean
    public static final String SESSION_ESSEMP_BRANCHPLANT =
        "SESSION_ESSEMP_BRANCHPLANT"; //Object BranchPlantBean
    public static final String SESSION_AVSUP =
        "SESSION_AVSUP"; // Object String
    public static final String SESSION_AVEMP =
        "SESSION_AVEMP"; // Object String
    public static final String SESSION_MRSUP =
        "SESSION_MRSUP"; //HashMap<String,String>. But will contain only one/zero record
    public static final String SESSION_MREMP =
        "SESSION_MREMP"; //HashMap<String,String>. But will contain only one/zero record
    public static final String SESSION_SELECT_MARINE_BRANCH_PLANT =
        "SESSION_SELECT_MARINE_BRANCH_PLANT"; //HashMap<String,String>. But will contain only one/zero record
    public static final String SESSION_MR_BP_MAP =
        "SESSION_MR_BP_MAP"; //HashMap<String,String>. Will contain more than one records only
    public static final String SESSION_WM = "SESSION_WM";
    public static final String SESSION_ECSR_CUSTOMER = "SESSION_ECSR_CUSTOMER";
    public static final String SESSION_SUP_CUSTOMER = "SESSION_SUP_CUSTOMER";
    public static final String SESSION_NAVIGATION_FROM_RESSELER =
        "NAVIGATION_FROM_RESELLER";
    public static final String SESSION_NAVIGATION_FROM_RESELLER_IS_RESETED =
        "YES";
    public static final String SESSION_SSSUPP_BRANCHPLANT =
        "SESSION_SSSUPP_BRANCHPLANT";
    public static final String SESSION_WEBSUP_CUSTOMER =
        "SESSION_WEBSUP_CUSTOMER";
    public static final String SESSION_RESSUP_CUSTOMER =
        "SESSION_RESSUP_CUSTOMER";
    public static final String SESSION_MARSUP_CUSTOMER =
        "SESSION_MARSUP_CUSTOMER";
    public static final String SESSION_AVISUP_CUSTOMER =
        "SESSION_AVISUP_CUSTOMER";


    public static final String SESSION_ERROR_SOLDTOVALIDATE =
        "SESSION_ERROR_SOLDTOVALIDATE";
    public static final String SESSION_HASMORE = "HASMORERECORDS";
    public static final String SESSION_HASMORE_BILLTOADDRESS =
        "HASMOREBILLTOADDRESS";

    public static final String IS_MULTIPLE = "IS_MULTIPLE";
    public static final String SELECTION_DONE = "SELECTION_DONE";


    public static final String SESSION_USER_NAME =
        "USER_NAME"; // This string is used for fetching the transaction originator string from IDM
    public static final String SESSION_USER_DISPLAY_NAME =
        "USER_DISPLAY_NAME"; // This string is used to display name


    public static final String SESSION_PRIMARY_REQUEST_PAGE_ID =
        "primaryRequestPageId";

    public static final String VALUE_CHAIN_RESELLER = "RS";
    public static final String VALUE_CHAIN_MARINE = "MR";
    public static final String VALUE_CHAIN_WEBSHOP = "WB";
    public static final String VALUE_CHAIN_AVIATION = "AV";
    public static final String VALUE_CHAIN_SERVICE_STATION = "SS";
    public static final String VALUE_CHAIN_SS_ARRP_INQUIRY = "RS";

    public static final String SESSION_USER_INFO = "USER_INFO";

    public static final String SESSION_CUSTOMER_INFO = "CUSTOMER_INFO";
    public static final String SESSION_CUSTOMER_LIST = "CUSTOMER_LIST";
    public static final String SESSION_SELECTED_CUSTOMER = "SELECTED_CUSTOMER";

    public static final String SESSION_LANGUAGE = "lang";
    public static final String SESSION_ORIGINAL_LANGUAGE = "originalLanguage";
    public static final String SESSION_PROFILE = "profile";

    public static final String SESSION_AIRPORT_LIST = "airport_list";
    public static final String SESSION_TEXT_LOGGED_IN_AIRPORT =
        "LOGGED_IN_AIRPORT";
    public static final String SESSION_TEXT_LOGGED_IN_AP_COUNTRY =
        "LOGGED_IN_AP_COUNTRY";

    public static final String SESSION_SELECTED_BRANCH_PLANT =
        "SELECTED_BRANCH_PLANT";
    public static final String SESSION_BRANCH_PLANT_LIST = "BRANCH_PLANT_LIST";
    public static final String SESSION_BRANCH_PLANT_BEAN_LIST =
        "BRANCH_PLANT_BEAN_LIST";


    public static final String SESSION_HAS_ERROR_OCCURED = "HAS_ERROR_OCCURED";
    public static final String SESSION_USER_INFO_BEAN = "userInfoBean";
    public static final String SESSION_USER_ERROR = "SESSION_USER_ERROR";

    public static final String LANGUAGE_ENGLISH = "en_US";
    public static final String LANGUAGE_NORWEGIAN = "no_NO";
    public static final String LANGUAGE_SWEDISH = "se_SE";
    public static final String LANGUAGE_LATVIA = "lv_LV";
    public static final String LANGUAGE_DANISH = "da_DK";
    public static final String LANGUAGE_LITHUNIA = "lt_LT";
    public static final String LANGUAGE_POLAND = "pl_PL";
    public static final String LANGUAGE_ESTONIA = "et_EE";
    public static final String LANGUAGE_ETHIOPIA = "et-EE";

    // Credit Card
    public static final String MERCHANT = "MERCHANT";
    public static final String PAYTYPE = "PAYTYPE";
    public static final String TEST = "TEST";
    public static final String ACCEPT_URL = "ACCEPT_URL";
    public static final String CANCEL_URL = "CANCEL_URL";
    public static final String CALLBACK_URL = "CALLBACK_URL";
    public static final String CURRENCY = "CURRENCY";
    public static final String LANG = "LANG";
    public static final String MAKE_TICKET = "MAKE_TICKET";
    public static final String CAPTURE_DIBS_WSDL_URL = "CAPTURE_DIBS_WSDL_URL";
    public static final String CANCEL_DIBS_WSDL_URL = "CANCEL_DIBS_WSDL_URL";
    public static final String REFUND_DIBS_WSDL_URL = "REFUND_DIBS_WSDL_URL";


    //public static final String LANGUAGE_EN = "en_US";
    //public static final String LANGUAGE_EN = "en_US";
    //public static final String LANGUAGE_EN = "en_US";

    public static final String LOV_INVOICE_SEARCH_CRITERIA = "INV_SEARCH_CRT";
    public static final String LOV_ORDER_SEARCH_CRITERIA = "ORD_SEARCH_CRT";
    public static final String LOV_LEDGER_SEARCH_CRITERIA = "LEDG_SEARCH_CRT";
    public static final String LOV_AVIATION_SEARCH_CRITERIA = "AV_SEARCH_CRT";
    public static final String LOV_UDC_AV_TRANSACTION_TYPE =
        "UDC_AV_TRANSACTION_TYPE";
    public static final String LOV_UDC_RUSH_ORDER = "UDC_RUSH_ORDER";
    public static final String LOV_UDC_SPECIAL_TREATMENT_FLAG =
        "UDC_SPECIAL_TREATMENT_FLAG";
    public static final String LOV_UDC_AV_PAYMENT_METHOD =
        "UDC_AV_PAYMENT_METHOD";
    public static final String LOV_UDC_AVIATION_PAYMENT_METHOD =
        "UDC_AVIATION_PAYMENT_METHOD";
    public static final String LOV_UDC_LOCATION_CODE = "UDC_LOCATION_CODE";
    public static final String LOV_UDC_EXCISE_DUTY_HANDLING_CODE =
        "UDC_EXCISE_DUTY_HANDLING_CODE";
    public static final String LOV_UDC_EXCISE_DUTY_EXEMPTION =
        "UDC_EXCISE_DUTY_EXEMPTION";
    public static final String LOV_UDC_RS_TRANSACTION_TYPE =
        "UDC_RS_TRANSACTION_TYPE";
    public static final String LOV_UDC_RS_PAYMENT_METHOD =
        "UDC_RS_PAYMENT_METHOD";
    public static final String LOV_UDC_RS_PAYMENT_TERMS =
        "UDC_RS_PAYMENT_TERMS";
    public static final String LOV_UDC_MR_TRANSACTION_TYPE =
        "UDC_MR_TRANSACTION_TYPE";
    public static final String UDC_FAST_FUELS = "UDC_FAST_FUELS";
    public static final String UDC_FAST_LUBES = "UDC_FAST_LUBES";
    public static final String UDC_AV_TANK_NUMBER = "UDC_AV_TANK_NUMBER";
    public static final String LOV_AV_SALES_SEARCH_CRT = "AV_SALES_SEARCH_CRT";
    

    public static final String RADIO_LEDGER = "LEDG_RADIO_VALUE";
    //public static final String LOV_AVIATION_ORDER_SEARCH_CRITERIA = "INV_SEARCH_CRT";
    //public static final String LOV_SS_INVOICE_SEARCH_CRITERIA = "INV_SEARCH_CRT";
    //public static final String LOV_SS_AR_AP_SEARCH_CRITERIA = "INV_SEARCH_CRT";
    public static final String WARNING = "WARNING";
    public static final String GENERIC = "GEN";
    public static final String INVOICE_NO = "INVOICE_NO";
    public static final String DATE_RANGE = "DATE_RANGE";
    public static final String DELIVERY_TICKET_NO = "DELIVERY_TICKET_NO";
    public static final String ORDER_NO = "ORDER_NO";
    public static final String TRACKING = "TRACKING";
    public static final String COUNTRY = "COUNTRY";
    public static final String OPEN_ITEM = "OPEN_ITEM";
    public static final String CLEARED_ITEM = "CLEARED_ITEM";
    public static final String BOTH_ITEM = "BOTH_ITEM";
    public static final String ORDER_NUMBER = "orderNumber";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String AIRPORT_NUMBER = "airportNumber";
    public static final String AIRPORT_CODE = "airportCode";
    public static final String DELIVERY_TICKET_NUMBER = "deliveryTicketNumber";
    public static final String KEY_CODE = "keyCode";
    public static final String SHIPPING_ADDRESS_NO = "shippingAddress";
    public static final String BRANCH_PLANT_NUMBER = "branchPlantNumber";
    public static final String MSG_START_DATE = "Start date";
    public static final String MSG_END_DATE = "End date";
    public static final String MSG_BRANCH_PLANT = "Branch plant";
    public static final String MSG_SHOULD_NOT_BE_BLANK =
        " should not be blank.";
    public static final String ARTICLE_NAME = "Articlename";
    public static final String UNIT_OF_MEASUREMENT = "Unitofmeasurement";
    public static final String ITEM_NUMBER = "Itemnumber";
    public static final String NON_EU_CODE = "non_EU_Code";
    public static final String country = "country";
    public static final String CORRECTON = "CORRECTION";
    public static final String branchPlant = "branchPlant";
    public static final String orderType = "orderType";
    public static final String shippingAddressNumber = "shippingAddressNumber";
    public static final String REFUELLING = "REFUELLING";
    public static final String DEFUELING = "DEFUELING";
    public static final String ORDER_TYPE = "SA";
    public static final String CREDIT_CARD_PAYMENT_METHOD = "CC";
    public static final String CARNET_CARD_PAYMENT_METHOD = "CN";
    public static final String CONTRACT_PAYMENT_METHOD = "CO";
    public static final String UDC_PRODUCT_CODE_55 = "55";
    public static final String UDC_USERD_DEFINED_CODES_AP = "AP";
    public static final String productCode = "productCode";
    public static final String userDefinedCodes = "userDefinedCodes";
    public static final String userDefinedCode = "userDefinedCode";
    public static final String userLang = "userLang";
    public static final String UserReservedReference4 =
        "UserReservedReference4";
    public static final String ItemName = "ItemName";
    public static final String ItemNumber = "ItemNumber";
    public static final String Uom = "Uom";
    public static final String customInteger1 = "customInteger1";
    public static final String customInteger2 = "customInteger2";
    public static final String customDate1 = "customDate1";
    public static final String UCM_INVOICE_CONTENT_KEY = "dDocName";
    public static final String searchType = "searchType";
    public static final String External = "External";
    public static final String REPRISING_PRODUCT_CODE = "55";
    public static final String REPRISING_USER_DEFINED_CODES = "PH";
    public static final String MR_TRANSACTION_TYPE = "ALREADY DELIVERED";


    public static final String METHOD_GET_LOV_STRING_VALUES =
        "getLOVStringValues";
    public static final String METHOD_SEARCH_CUSTOMER_INVOICES =
        "searchCustomerInvoices";
    public static final String METHOD_SEARCH_CUSTOMER_ORDERS =
        "searchWebshopOrders";
    public static final String METHOD_GET_ERROR_MESSAGE = "getErrorMessage";
    public static final String METHOD_GET_FEATURE_KEY_VALUE =
        "getFeatureKeyValue";
    public static final String METHOD_SEARCH_CUSTOMER_LEDGER_INFO =
        "searchCustomerLedgerInformation";
    public static final String METHOD_SEARCH_SS_INVOICES = "searchSSInvoices";
    public static final String METHOD_SEARCH_AR_ACCOUNT_INFO =
        "searchARAccountInformation";
    public static final String METHOD_GET_LOV_STRING_VALUES_SORTED =
        "getLOVStringValuesSorted";
    public static final String CREDIT_CARD = "CREDIT_CARD";
    public static final String WEBSHOP = "WEBSHOP";
    public static final String COUNTRYY = "COUNTRY";
    public static final String SEARCH_CUST_SHIP_TO = "SEARCH_CUST_SHIP_TO";
    public static final String SEARCH_CUST_FORM_ADD = "SEARCH_CUST_FORM_ADD";
    public static final String MAIL = "MAIL";
    //public static final String RESELLER="RESELLER";


    /*Customer Creation Strings*/
    public static final String EXECUTE_WITH_ORGANIZATION_ID_FORMAT =
        "EWOrganizationIDFormat";
    public static final String EXECUTE_WITH_ORGANIZATION_ID_FORMAT_VALIDATION_RVO_ITERATOR =
        "EWOrganizationIDFormatValidationRVO1Iterator";
    public static final String COUNTRY_CODE_AND_CUSTOMER_TYPE =
        "countryCodeAndCustomerType";
    public static final String DESCRIPTION = "Description";
    public static final String CUSTOMER_TYPE_B2C = "CBC";
    public static final String CUSTOMER_TYPE_B2B = "CBB";

    /* Aviation Strings*/
    public static final String METHOD_GET_LOV_AIRPORT_CODE =
        "getLOVAirportCode";
    public static final String METHOD_GET_LOV_AIRLINE_CODE =
        "getLOVAirlineCode";
    public static final String METHOD_GET_AIRLINE_CODE = "getAirlineCode";
    public static final String METHOD_GET_AIRLINE_CCUSTOMER_NO =
        "getAirlineCustomerNo";
    public static final String METHOD_VALIDATE_AVIATION_ORDER =
        "validateAviationOrder";
    public static final String METHOD_SAVE_AVIATION_ORDER =
        "saveAviationOrder";
    public static final String METHOD_GET_AIRPORT_COUNTRY =
        "getAirportCountry";
    public static final String METHOD_SEARCH_AVIATION_ORDERS =
        "searchAviationOrders";
    public static final String METHOD_CREDIT_AVIATION_ORDER =
        "creditAviationOrder";
    public static final String AVIATION_ORDER = "aviationOrder";
    public static final String EXECUTE_WITH_PARAMS_FOR_MATERIAL_LOV =
        "ExecuteWithParamsForMaterialLOV";
    public static final String EWGET_SPECIAL_HANDLING_CODE =
        "EWPgetSpecialHandlingCode";
    public static final String PRT_LOAD_UDCINFO_RVO_ITERATOR =
        "PrtLoadUDCInfoRVO1Iterator";
    public static final String PRT_CATALOG_ITEMS_MARINE_AVIATION_RVO_ITERATOR =
        "PrtCatalogItemsMarineAviationRVO1Iterator";
    public static final String GET_TAX_RATE_AREA_CODE = "getTaxRateAreaCode";
    public static final String GET_BRANCH_PLANT_CODE = "getBranchPlantCode";
    public static final String GET_AIRLINE_NUMBER = "getAirlineNumber";
    public static final String AVIATION = "AVIATION";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String CUSTOMER_TYPE = "customerType";

    /*Aviation dynamic components ID's*/
    public static final String newPGL = "newPGL";
    public static final String spacer1Dyn = "spacer1Dyn";
    public static final String materialSOCDyn = "materialSOCDyn";
    public static final String spacer2Dyn = "spacer2Dyn";
    public static final String volumeITDyn = "volumeITDyn";
    public static final String spacer3Dyn = "spacer3Dyn";
    public static final String uomOT = "uomOT";
    public static final String spacer4Dyn = "spacer4Dyn";
    public static final String temperature1OTDyn = "temperature1OTDyn";
    public static final String spacer5Dyn = "spacer5Dyn";
    public static final String density1OTDyn = "density1OTDyn";
    public static final String spacer6Dyn = "spacer6Dyn";
    public static final String tankNumberSOCDyn = "tankNumberSOCDyn";
    public static final String spacer8Dyn = "spacer8Dyn";
    public static final String netValue1OTDynamic = "netValue1OTDynamic";
    public static final String spacer1Dynamic = "spacer1Dynamic";
    public static final String vat1OTDynamic = "vat1OTDynamic";
    public static final String spacer2Dynamic = "spacer2Dynamic";
    public static final String grossValue1OTDynamic = "grossValue1OTDynamic";
    public static final String spacer3Dynamic = "spacer3Dynamic";
    public static final String spacer4Dynamic = "spacer4Dynamic";
    public static final String spacer5Dynamic = "spacer5Dynamic";
    public static final String spacer6Dynamic = "spacer6Dynamic";
    public static final String UnitPrice1OTDynamic = "UnitPrice1OTDynamic";
    public static final String Rebate1OTDynamic = "Rebate1OTDynamic";
    public static final String ItemAvailability1OTDynamic =
        "ItemAvailability1OTDynamic";
    public static final String addLinkDyn = "addLinkDyn";
    public static final String spacer7Dyn = "spacer7Dyn";
    public static final String deleteLinkDyn = "deleteLinkDyn";
    public static final String Paneltemp = "Paneltemp";
    public static final String Paneldensity = "Paneldensity";
    public static final String Paneluom = "Paneluom";
    public static final String Panelnet = "Panelnet";
    public static final String Panelvat = "Panelvat";
    public static final String Panelgross = "Panelgross";

    public static final String PRT_CATALOG_ITEMS_RVOIITERATOR =
        "PrtCatalogItemsRVO1Iterator";
    public static final String METHOD_VALIDATE_MARINE_ORDER =
        "validateMarineOrder";
    public static final String METHOD_SAVE_MARINE_ORDER = "saveMarineOrder";
    public static final String METHOD_SEARCH_MARINE_ORDER =
        "searchMarineOrders";
    public static final String METHOD_CREDIT_MARINE_ORDER =
        "creditMarineOrder";
    public static final String METHOD_GET_BRANCH_PLANT_LOV =
        "getBranchPlantLOV";
    public static final String METHOD_GET_MARINE_CUSTOMER_INFO =
        "getMarineCustomerInfo";
    public static final String METHOD_SEARCH_AVIATION_SALES_DETAIL =
        "searchAviationSalesReport";

    public static final String MARINE_ORDER = "marineOrder";
    public static final String ER_START_DT_GR_END_DT = "getErrorMessage";

    public static final String COUNTRY_CODE = "countryCode";
    public static final String COUNTRY_CD_SWEDEN = "SE";
    public static final String COUNTRY_CD_NORWAY = "NO";
    public static final String COUNTRY_CD_DENMARK = "DK";
    public static final String COUNTRY_CD_UK = "US";
    public static final String COUNTRY_CD_ESTONIA = "EE";
    public static final String COUNTRY_CD_LITHUANIA = "LT";
    public static final String COUNTRY_CD_POLAND = "PL";
    public static final String COUNTRY_CD_LATVIA = "LV";
    public static final String COUNTRY_CD_GREENLAND = "GL";
    public static final String COUNTRY_CD_FAROEISLANDS = "FO";

    //    public static final String COUNTRY_CD_POLAND = "SE";
    //    public static final String COUNTRY_CD_FINLAND = "NO";
    //    public static final String COUNTRY_CD_LATVIA = "DK";
    //    public static final String COUNTRY_CD_LITH = "UK";
    //    public static final String COUNTRY_CD_ESTONIA = "UK";
    //    public static final String COUNTRY_CD_EUTH = "UK";

    //content styles for marine to be used
    // public static final String STYLE_WIDTH_225 = "width_225";
    //public static final String STYLE_WIDTH_45 = "width_45";
    //public static final String STYLE_WIDTH_25 = "width_25";
    //public static final String STYLE_WIDTH_140 = "width_140";
    //public static final String STYLE_WIDTH_175 = "width_175";
    //public static final String STYLE_WIDTH_215 = "width_215";


    //content styles for aviation to be used
    public static final String STYLE_WIDTH_40 = "width_40";
    public static final String STYLE_WIDTH_50 = "width_50";
    public static final String STYLE_WIDTH_55 = "width_55";
    public static final String STYLE_WIDTH_70 = "width_70";
    public static final String STYLE_HEADING_3 = "heading3";
    public static final String WIDTH_300 = "width:300px;";
    public static final String WIDTH_60 = "width:60px;";
    public static final String WIDTH_40 = "width:40px;";
    public static final String WIDTH_50 = "width:50px;";
    public static final String WIDTH_70 = "width:70px;";
    public static final String WIDTH_65 = "width:65px;";
    public static final String HORIZONTAL = "horizontal";
    public static final String WIDTH_5 = "5";
    public static final String WIDTH_10 = "10";
    public static final String WIDTH_15 = "15";
    public static final String WIDTH_20 = "20";
    public static final String W_60 = "60";
    public static final String WIDTH_30 = "30";

    public static final String STYLE_PADDING_10_BOTTOM = "padding_10_bottom";
    public static final String STYLE_WIDTH_40_TEXT_LEFT = "width_40_text_left";
    public static final String STYLE_PADDING_7 = "padding_7";
    public static final String STYLE_PADDING_7_BORDER = "padding_7_border";

    public static final String IMAGES_PLUS_BTN = "/images/blueadd.gif";
    public static final String IMAGES_TRASH_BTN =
        "/images/1337087516_trash_16x16.gif";

    public static final String TEXT_ADD_MORE = "Add More";
    public static final String TEXT_TEMPERATURE = "Temperature";
    public static final String TEXT_DENSITY = "Density";
    public static final String TEXT_GROSS_VALUE = "Gross Value";
    public static final String TEXT_NET_VALUE = "Net Value";
    public static final String TEXT_VAT = "VAT";
    public static final String TEXT_PLEASE_SELECT = "Please Select";
    public static final String TEXT_MORE_DETAILS = "More Details";
    public static final String LOV_USER_TYPE_CRT = "USER_TYPE_CRT";
    //help constant
    public static final String SESSION_PORTAL_NAME = "PORTAL_NAME";
    public static final String WS_PORTAL = "WS_PORTAL";
    public static final String PP_PORTAL = "PP_PORTAL";
    public static final String EN_PORTAL = "ENGAGE_PORTAL";
    public static final String ENGAGE = "ENGAGE";
    public static final String HELPLIST = "HELPLIST";
    public static final String PCM_PORTAL = "PCM_PORTAL";
    //public static final String WS_HELP = "WS_HELP";
    public static final String WS_HELP_FAQ = "WS_HELP_FAQ";
    //public static final String PP_HELP = "PP_HELP";
    public static final String PP_HELP_FAQ = "PP_HELP_FAQ";
    //public static final String PCM_HELP = "PCM_HELP";
    public static final String PCM_HELP_FAQ = "PCM_HELP_FAQ";


    public static final String CUSTOMER_INFO_WSDL_URL =
        "CUSTOMER_INFO_WSDL_URL"; //customer WDSL URL
    public static final String CUSTOMER_CREATION_FINAL_WSDL_URL =
        "CUSTOMER_CREATION_FINAL_WSDL_URL"; //customer WDSL URL
    public static final String CUSTOMER_CREATION_FIRST_LEVEL_WSDL_URL =
        "CUSTOMER_CREATION_FIRST_LEVEL_WSDL_URL"; //customer WDSL URL
    public static final String ORDER_INQ_WSDL_URL =
        "ORDER_INQ_WSDL_URL"; //order inquiry
    public static final String ORDER_VALIDATE_WSDL_URL =
        "ORDER_VALIDATE_WSDL_URL"; //order validation
    public static final String UCM_WSDL_URL = "UCM_WSDL_URL";
    public static final String CUSTOMER_SEARCH_WSDL_URL =
        "CUSTOMER_SEARCH_WSDL_URL";
    public static final String INVOICE_INQ_WSDL_URL = "INVOICE_INQ_WSDL_URL";
    public static final String LEDGER_INQ_WSDL_URL = "LEDGER_INQ_WSDL_URL";
    public static final String OIM_WSDL_URL = "OIM_WSDL_URL";
    public static final String ENGAGE_OIM_WSDL_URL = "ENGAGE_OIM_WSDL_URL";
    public static final String SS_INVOICE_INQ_WSDL_URL =
        "SS_INVOICE_INQ_WSDL_URL";
    /*Reseller constants */
    public static final String SELECTED_SHIP_TO_NUMBER =
        "SELECTED_SHIP_TO_NUMBER";

    public static final String LUBESORDER = "LUBESORDER";
    public static final String FUELORDER = "FUELORDER";
    public static final String getTranslation = "getTranslation";
    public static final String getGeneralErrorMessage =
        "getGeneralErrorMessage";
    public static final String getErrorMessage = "getErrorMessage";
    public static final String ORDER_CANNOT_BE_VALIDATED_DUE_TO_ERROR =
        "ORDER_CANNOT_BE_VALIDATED_DUE_TO_ERROR";
    //Fuels
    public static final String PanelFuels = "PanelFuels";
    public static final String ItemNumberITFuels = "ItemNumberITFuels";
    public static final String spacer1Fuels = "spacer1Fuels";
    public static final String MaterialSOCFuels = "MaterialSOCFuels";
    public static final String spacer2Fuels = "spacer2Fuels";
    public static final String volumeITFuels = "volumeITFuels";
    public static final String spacer3Fuels = "spacer3Fuels";
    public static final String uomOTFuels = "uomOTFuels";
    public static final String spacer4Fuels = "spacer4Fuels";
    public static final String DiscountITFuels = "DiscountITFuels";
    public static final String spacer5Fuels = "spacer5Fuels";
    public static final String TankNumberSOCFuels = "TankNumberSOCFuels";
    public static final String spacer6Fuels = "spacer6Fuels";
    public static final String FulfillFlagFuels = "FulfillFlagFuels";
    public static final String spacer7Fuels = "spacer7Fuels";
    public static final String netValueOTFuels = "netValueOTFuels";
    public static final String spacer8Fuels = "spacer8Fuels";
    public static final String vatOTFuels = "vatOTFuels";
    public static final String spacer9Fuels = "spacer9Fuels";
    public static final String grossValueOTFuels = "grossValueOTFuels";
    public static final String spacer10Fuels = "spacer10Fuels";
    public static final String moreDetailsFuels = "moreDetailsFuels";
    public static final String spacer11Fuels = "spacer11Fuels";
    public static final String addLinkFuels = "addLinkFuels";
    public static final String spacer12Fuels = "spacer12Fuels";
    public static final String deleteLinkFuels = "deleteLinkFuels";
    public static final String spacer13Fuels = "spacer13Fuels";
    public static final String PaneluomFuels = "PaneluomFuels";
    public static final String PanelnetFuels = "PanelnetFuels";
    public static final String PanelvatFuels = "PanelvatFuels";
    public static final String PanelgrossFuels = "PanelgrossFuels";

    /*Lubes*/
    public static final String PanelLubes = "PanelLubes";
    public static final String spacer1Lubes = "spacer1Lubes";
    public static final String ItemNumberITLubes = "ItemNumberITLubes";
    public static final String spacer2Lubes = "spacer2Lubes";
    public static final String MaterialSOCLubes = "MaterialSOCLubes";
    public static final String spacer3Lubes = "spacer3Lubes";
    public static final String volumeITLubes = "volumeITLubes";
    public static final String spacer4Lubes = "spacer4Lubes";
    public static final String uomOTLubes = "uomOTLubes";
    public static final String spacer5Lubes = "spacer5Lubes";
    public static final String OrderCodeSOCLubes = "OrderCodeSOCLubes";
    public static final String spacer6Lubes = "spacer6Lubes";

    public static final String spacer7Lubes = "spacer7Lubes";
    public static final String SpecialTreatmentSOCLubes =
        "SpecialTreatmentSOCLubes";
    public static final String spacer8Lubes = "spacer8Lubes";
    public static final String DiscountITLubes = "DiscountITLubes";
    public static final String spacer9Lubes = "spacer9Lubes";
    public static final String CompleteDeliveryLubes = "CompleteDeliveryLubes";
    public static final String spacer10Lubes = "spacer10Lubes";
    public static final String addLinkLubes = "addLinkLubes";
    public static final String deleteLinkLubes = "deleteLinkLubes";
    public static final String PaneluomLubes = "PaneluomLubes";
    public static final String newLine = "newLine";

    public static final String netValueOTDynamic = "netValueOTDynamic";
    public static final String vatOTDynamic = "vatOTDynamic";
    public static final String grossValueOTDynamic = "grossValueOTDynamic";
    public static final String UnitPriceOTDynamic = "UnitPriceOTDynamic";
    public static final String RebateOTDynamic = "RebateOTDynamic";
    public static final String ItemAvailabilityOTDynamic =
        "ItemAvailabilityOTDynamic";
    /* in aviation
    netValue1OTDynamic = "netValue1OTDynamic";
        public static final String spacer1Dynamic = "spacer1Dynamic";
        public static final String vat1OTDynamic = "vat1OTDynamic";
        public static final String spacer2Dynamic = "spacer2Dynamic";
        public static final String grossValue1OTDynamic = "grossValue1OTDynamic";
        public static final String spacer3Dynamic = "spacer3Dynamic";
     */

    public static final String validateResellerOrder = "validateResellerOrder";
    public static final String saveResellerOrder = "saveResellerOrder";
    public static final String searchResellerOrders = "searchResellerOrders";
    public static final String EWPFuelsLubesCriteria = "EWPFuelsLubesCriteria";
    public static final String PrtCatalogItemsRVO1Iterator =
        "PrtCatalogItemsRVO1Iterator";
    public static final String EWPLubesCriteria = "EWPLubesCriteria";
    public static final String PrtCatalogItemsLubesRVO2Iterator =
        "PrtCatalogItemsLubesRVO2Iterator";
    public static final String panelDyn = "panelDyn";
    public static final String spacerPopup = "spacerPopup";
    public static final String saveButtonPopup = "saveButtonPopup";
    public static final String Srp03 = "Srp03";
    public static final String Srp09 = "Srp09";

    //Styles
    public static final String width_30 = "width_30";
    //    public static final String spacer3Dynamic = "spacer3Dynamic";
    //    public static final String spacer3Dynamic = "spacer3Dynamic";
    //    public static final String spacer3Dynamic = "spacer3Dynamic";
    //    public static final String spacer3Dynamic = "spacer3Dynamic";
    //    public static final String spacer3Dynamic = "spacer3Dynamic";


    /*Reseller Ends*/
    public static final String QUESTION_PREFIX = "Q";
    public static final String ANSWER_PREFIX = "A";
    public static final String RECORD_TYPE_ADDITION = "A";
    public static final String RECORD_TYPE_VALIDATION = "V";
    public static final String RECORD_TYPE_MODIFY = "M";
    public static final String HEADER_ACTION_TYPE_ADDITION = "1";
    public static final String HEADER_ACTION_TYPE_UPDATION = "2";
    public static final String HEADER_ACTION_TYPE_CANCELLATION = "3";
    public static final String DETAIL_ACTION_TYPE_ADDITION = "1";
    public static final String DETAIL_ACTION_TYPE_VALIDATION = "1";
    public static final String DETAIL_ACTION_TYPE_UPDATION = "2";
    public static final String DETAIL_ACTION_TYPE_CANCELLATION = "3";
    public static final String INTERNATIONAL_CO2 = "03";
    public static final String DOMESTIC_CO2 = "01";
    public static final String TAX_EXPLANATION_CODE = "E";
    public static final String INTERNATIONAL_TRUE_FLAG = "1";
    public static final String INTERNATIONAL_FALSE_FLAG = "0";
    public static final String VAT_FLAG = "Y";
    public static final String PAYMENT_METHOD = "?";
    public static final String HAS_MORE_RECORDS = "Y";
    public static final String STOCKING_TYPE = "S";

    public static final String STATUS_DUE = "D";
    public static final String STATUS_OVER_DUE = "O";
    public static final String STATUS_NOT_DUE = "N";
    public static final String MENU = "MENU";
    public static final String RESELLER = "RESELLER";
    public static final String MARINE = "MARINE";

    public static final String SOLIDITAT_FLAG_PRODUCT_CODE = "55";
    public static final String SOLIDITAT_FLAG_USER_DEFINE_CODE = "P3";
    public static final String SOLIDITAT_FLAG_CREDIT_CHECK = "CC";
    public static final String SOLIDITAT_FLAG_YES = "Y";
    public static final String SOLIDITAT_FLAG_NO = "N";
    public static final String wsportal = "WsPortal";
    /*PCM constants*/
    public static final String onEditDatabaseValues = "onEditDatabaseValues";
    public static final String srtDateFormatOld = "srtDateFormatOld";
    public static final String endDateFormatOld = "endDateFormatOld";
    public static final String selectCatalogId = "selectCatalogId";
    public static final String START_DATE_BEFORE_EQUALS_END_DATE =
        "You cannot set effective start date for the catalog same as or before current date";
    public static final String START_DATE_AFTER_END_DATE =
        "Effective start date cannot be after the end date for the catalog";
    public static final String DATE_DATABASE_CHECK_FAIL =
        "Catalog with same date combination already exists!";
    public static final String END_DATE_BEFORE_START_DATE =
        "You cannot set effective end date for the catalog before the start date";
    public static final String END_DATE_BEFORE_CURRENT_DATE =
        "End date cannot be before the current date for the catalog";
    public static final String RECORD_UPDATE_SUCCESS =
        "Record updated successfully.";
    public static final String RECORD_INSERT_SUCCESS =
        "Record has been inserted successfully.";
    public static final String RECORD_NOT_INSERTED_TRY_AGAIN =
        "Record could not be inserted. Please try again.";
    public static final String RECORD_UPDATE_FAIL_TRY_AGAIN =
        "Record update failed. Please try again.";
    public static final String HIGHLIGHTED_FIELDS_MANDATORY =
        "Highlighted fields are mandatory!";
    public static final String MAX_ITEMS_PER_PAGE = "20";
    public static final String CONVENIENCE_ITEMS_IDENTIFIER = "02";
    public static final String CONV_LUBES_ITEMS_IDENTIFIER = "CL";
    public static final String PROMOTION_CATALOG_IDENTIFIER = "PP";
    public static final String MAX_PROMOTION_ITEMS_VALID_MSG =
        "Promotion Catalog cannot contain more than 15 items !";
    public static final String MAX_PROMOTION_ITEMS_VALID_COUNT = "15";
    public static final String CATALOG_ITEMS_UPDATE_SUCCESS =
        "Catalog item list has been modified successfully.";
    public static final String CATALOG_ITEMS_NO_CHANGE_MSG =
        "Catalog item list has not been modified as no change found. Please try again.";
    public static final String CATALOG_ITEMS_UPDATE_FAILED =
        "Catalog item list modification failed. Please try again.";

    // Constants to hold user roles for Engage Portal Users

    public static final String ROLE_WCP_CARD_B2C_SFR = "WCP_CARD_B2C_SFR";
    public static final String ROLE_WCP_CARD_B2C_JET = "WCP_CARD_B2C_JET";
    public static final String ROLE_WCP_CARD_B2B_ADMIN = "WCP_CARD_B2B_ADMIN";
    public static final String ROLE_WCP_CARD_B2B_MGR = "WCP_CARD_B2B_MGR";
    public static final String ROLE_WCP_CARD_B2C_PETRO = "WCP_CARD_B2C_PETRO";
    public static final String ROLE_WCP_CARD_B2B_EMP = "WCP_CARD_B2B_EMP";
    public static final String ROLE_WCP_CARD_ADMIN = "WCP_CARD_ADMIN";
    public static final String ROLE_WCP_CARD_CSR = "WCP_CARD_CSR";
    public static final String ROLE_WCP_CARD_SALES_REP = "WCP_CARD_SALES_REP";


    //Constants to hold the ParameterValue of Engage Portal for OPSS call


    public static final String OPSS_WCP_CARD_B2C_SFR = "CARD_B2C_SFR";
    public static final String OPSS_WCP_CARD_B2C_JET = "CARD_B2C_JET";
    public static final String OPSS_WCP_CARD_B2B_ADMIN = "CARD_B2B_ADMIN";
    public static final String OPSS_WCP_CARD_B2B_MGR = "CARD_B2B_MGR";
    public static final String OPSS_WCP_CARD_B2C_PETRO = "CARD_B2C_PETRO";
    public static final String OPSS_WCP_CARD_B2B_EMP = "CARD_B2B_EMP";
    public static final String OPSS_WCP_CARD_ADMIN = "CARD_ADMIN";
    public static final String OPSS_WCP_CARD_CSR = "CARD_CSR";
    public static final String OPSS_WCP_CARD_SALES_REP = "CARD_SALES_REP";


    //Engage Portal constants
    public static final String ENGAGE_UCM_WSDL_URL = "ENGAGE_UCM_WSDL_URL";
    public static final String ENGAGE_UCM_USERNAME = "ENGAGE_UCM_USERNAME";
    public static final String ENGAGE_UCM_PASSWORD = "ENGAGE_UCM_PASSWORD";
    public static final String ENGAGE_REPORT_DELIMITER = "\\|";
    public static final String DISPLAY_PORTAL_LANG = "DISPLAY_PORTAL_LANG";
    public static final String AUTHENTICATE_FLAG = "AUTHENTICATE_FLAG";
    public static final String ENGAGE_SUBSCRIBE_ALERT = "ENGAGE_SUBSCRIBE_ALERT";
    public static final String ENGAGE_PORTAL = "ENGAGE_PORTAL";
    public static final String ENGAGE_2_IN_1_CARD_CHECK = "BAK,BBK,BCK,BDK,BEK,BFK,B5K,B4K,B3K,B2K,B1K,B0K,SAK,SBK,SCK,SDK,SEK,SFK,S5K,S4K,S3K,S2K,S1K,S0K";
    public static final String ENGAGE_INVOICE_TX_LINK = "InvoiceTransactionLink";


}

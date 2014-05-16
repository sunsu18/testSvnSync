CREATE TABLE PRT_CARD_TRANSACTION_HEADER 
(
  PALS_COUNTRY_CODE VARCHAR2(2 BYTE) NOT NULL 
, UREF_TRANSACTION_ID VARCHAR2(10 BYTE) NOT NULL 
, PURCHASE_COUNTRY_CODE VARCHAR2(2 BYTE) 
, ORDER_ID VARCHAR2(10 BYTE) 
, PRELIM_ID VARCHAR2(10 BYTE) 
, CARD_1_ID VARCHAR2(20 BYTE) 
, CARD_2_ID VARCHAR2(20 BYTE) 
, CARD_ID_2_INFO VARCHAR2(2 BYTE) 
, ODOMETER_PORTAL VARCHAR2(7 BYTE) 
, ODOMETER VARCHAR2(7 BYTE) 
, TRANSACTION_DT DATE 
, TRANSACTION_TIME TIMESTAMP(8) 
, STATION_NAME VARCHAR2(45 BYTE) 
, ICC_INVOICE_NUMBER VARCHAR2(10 BYTE) 
, INVOICE_NUMBER_NON_COLLECTIVE VARCHAR2(9 BYTE) 
, INVOICING_DATE DATE 
, PURCHASE_CURRENCY VARCHAR2(3 BYTE) 
, EXCHANGE_RATE FLOAT(126) 
, ICC_YN VARCHAR2(1 BYTE) 
, TRANSACTION_TYPE VARCHAR2(3 BYTE) 
, PRELIM_STATUS_CODE VARCHAR2(3 BYTE) 
, INVOICE_NUMBER_COLLECTIVE VARCHAR2(9 BYTE) 
, KSID VARCHAR2(10 BYTE) 
, PARTNER_ID VARCHAR2(8 BYTE) 
, ACCOUNT_ID VARCHAR2(10 BYTE) 
, CARDGROUP_MAIN_TYPE VARCHAR2(3 BYTE) 
, CARDGROUP_SUB_TYPE VARCHAR2(3 BYTE) 
, CARDGROUP_SEQ VARCHAR2(5 BYTE) 
, MODIFIED_BY VARCHAR2(50 BYTE) NOT NULL 
, PREVIOUS_ODOMETER VARCHAR2(7 BYTE) 
, PALS_ORDER_MAIN_TYPE VARCHAR2(3 BYTE) 
, PALS_ORDER_SUB_TYPE VARCHAR2(3 BYTE) 
, ORDER_BACKWARD_REF VARCHAR2(10 BYTE) 
, ORDER_FORWARD_REF VARCHAR2(10 BYTE) 
, ORDER_VALID_CODE VARCHAR2(3 BYTE) 
, ORDER_CREATE_CODE VARCHAR2(3 BYTE) 
, SITE_NUMBER VARCHAR2(5 BYTE) 
, INVOICED_CARD_ID VARCHAR2(10 BYTE) 
, TERMINAL_ID VARCHAR2(2 BYTE) 
, TERMINAL_SEQ VARCHAR2(6 BYTE) 
, INVOICE_FLAG VARCHAR2(10 BYTE) 
, WM_TRANS_REF_NUMBER VARCHAR2(12 BYTE) 
, PALS_MODIFIED_DATE DATE NOT NULL 
, PALS_MODIFIED_BY VARCHAR2(6 BYTE) NOT NULL 
, PORTAL_MODIFIED_DATE DATE NOT NULL 
, CONSTRAINT PRT_CARD_TRANSACTION_HEAD_PK PRIMARY KEY 
  (
    UREF_TRANSACTION_ID 
  , PALS_COUNTRY_CODE 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE "WCPCUSTOM" 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 2 
  NEXT 1 
  MINEXTENTS 1 
  MAXEXTENTS 2147483645 
  BUFFER_POOL DEFAULT 
) 
PARTITION BY LIST (PALS_COUNTRY_CODE) 
(
  PARTITION PRT_CARD_TRANSACTION_HEADER_SE VALUES ('SE') 
  LOGGING 
  TABLESPACE "WCPCUSTOM" 
  PCTFREE 10 
  INITRANS 1 
  STORAGE 
  ( 
    INITIAL 65536 
    NEXT 1048576 
    MINEXTENTS 1 
    MAXEXTENTS 2147483645 
    BUFFER_POOL DEFAULT 
  ) 
  NOCOMPRESS  
, PARTITION PRT_CARD_TRANSACTION_HEADER_NO VALUES ('NO') 
  LOGGING 
  TABLESPACE "WCPCUSTOM" 
  PCTFREE 10 
  INITRANS 1 
  STORAGE 
  ( 
    INITIAL 65536 
    NEXT 1048576 
    MINEXTENTS 1 
    MAXEXTENTS 2147483645 
    BUFFER_POOL DEFAULT 
  ) 
  NOCOMPRESS  
, PARTITION PRT_CARD_TRANSACTION_HEADER_DK VALUES ('DK') 
  LOGGING 
  TABLESPACE "WCPCUSTOM" 
  PCTFREE 10 
  INITRANS 1 
  STORAGE 
  ( 
    INITIAL 65536 
    NEXT 1048576 
    MINEXTENTS 1 
    MAXEXTENTS 2147483645 
    BUFFER_POOL DEFAULT 
  ) 
  NOCOMPRESS  
, PARTITION PRT_CARD_TRANSACTION_HEADER_LV VALUES ('LV') 
  LOGGING 
  TABLESPACE "WCPCUSTOM" 
  PCTFREE 10 
  INITRANS 1 
  STORAGE 
  ( 
    INITIAL 65536 
    NEXT 1048576 
    MINEXTENTS 1 
    MAXEXTENTS 2147483645 
    BUFFER_POOL DEFAULT 
  ) 
  NOCOMPRESS  
, PARTITION PRT_CARD_TRANSACTION_HEADER_LT VALUES ('LT') 
  LOGGING 
  TABLESPACE "WCPCUSTOM" 
  PCTFREE 10 
  INITRANS 1 
  STORAGE 
  ( 
    INITIAL 65536 
    NEXT 1048576 
    MINEXTENTS 1 
    MAXEXTENTS 2147483645 
    BUFFER_POOL DEFAULT 
  ) 
  NOCOMPRESS  
, PARTITION PRT_CARD_TRANSACTION_HEADER_EE VALUES ('EE') 
  LOGGING 
  TABLESPACE "WCPCUSTOM" 
  PCTFREE 10 
  INITRANS 1 
  STORAGE 
  ( 
    INITIAL 65536 
    NEXT 1048576 
    MINEXTENTS 1 
    MAXEXTENTS 2147483645 
    BUFFER_POOL DEFAULT 
  ) 
  NOCOMPRESS  
, PARTITION PRT_CARD_TRANSACTION_HEADER_PL VALUES ('PL') 
  LOGGING 
  TABLESPACE "WCPCUSTOM" 
  PCTFREE 10 
  INITRANS 1 
  STORAGE 
  ( 
    INITIAL 65536 
    NEXT 1048576 
    MINEXTENTS 1 
    MAXEXTENTS 2147483645 
    BUFFER_POOL DEFAULT 
  ) 
  NOCOMPRESS  
);

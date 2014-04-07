CREATE TABLE PRT_CARD_TRANSACTION_HEADER 
(
  DELTA_TIMESTAMP DATE 
, LAST_UPDATED DATE 
, UREF_TRANSACTION_ID VARCHAR2(10 BYTE) NOT NULL 
, PALS_COUNTRY_CODE VARCHAR2(8 BYTE) NOT NULL 
, PURCHASE_COUNTRY_CODE VARCHAR2(2 BYTE) 
, ORDER_ID VARCHAR2(10 BYTE) 
, PRELIM_ID VARCHAR2(10 BYTE) 
, CARD_1_ID VARCHAR2(20 BYTE) 
, CARD_2_ID VARCHAR2(20 BYTE) 
, CARD_ID_2_INFO VARCHAR2(2 BYTE) 
, ODOMETER_PORTAL VARCHAR2(7 BYTE) 
, ODOMETER VARCHAR2(7 BYTE) 
, TRANSACTION_DT DATE 
, TRANSACTION_TIME DATE 
, STATION_NAME VARCHAR2(30 BYTE) 
, STATION_GROUP_ID VARCHAR2(3 BYTE) 
, STATION_GROUP_NAME VARCHAR2(30 BYTE) 
, ICC_INVOICE_NUMBER VARCHAR2(10 BYTE) 
, INVOICE_NUMBER_NON_COLLECTIVE VARCHAR2(9 BYTE) 
, INVOICING_DATE DATE 
, RECIEPT_NO VARCHAR2(8 BYTE) 
, PURCHASE_CURRENCY VARCHAR2(3 BYTE) 
, EXCHANGE_RATE NUMBER(13, 0) 
, ICC_YN VARCHAR2(1 BYTE) 
, TRANSACTION_TYPE VARCHAR2(3 BYTE) 
, PRELIM_STATUS_CODE VARCHAR2(3 BYTE) 
, INVOICE_NUMBER_COLLECTIVE VARCHAR2(9 BYTE) 
, KSID VARCHAR2(20 BYTE) 
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
  INITIAL 8 
  NEXT 128 
  MINEXTENTS 1 
  MAXEXTENTS 2147483645 
  BUFFER_POOL DEFAULT 
) 
PARTITION BY LIST (PALS_COUNTRY_CODE) 
(
  PARTITION PRT_CARD_TRANSACTION_HEADER_SE VALUES ('se_SE') 
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
, PARTITION PRT_CARD_TRANSACTION_HEADER_NO VALUES ('no_NO') 
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

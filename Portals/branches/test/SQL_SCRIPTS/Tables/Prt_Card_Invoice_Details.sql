CREATE TABLE PRT_CARD_INVOICE_DETAILS 
(
  COUNTRY_CODE VARCHAR2(2 BYTE) NOT NULL 
, INVOICE_DOC_NUMBER VARCHAR2(9 BYTE) NOT NULL 
, INVOICE_DOC_TYPE VARCHAR2(3 BYTE) NOT NULL 
, ACCOUNT_ID VARCHAR2(10 BYTE) NOT NULL 
, PAYMENT_DOC_DETAIL_TYPE VARCHAR2(3 BYTE) 
, ACCOUNT_TYPE VARCHAR2(3 BYTE) 
, PAYMENT_DOC_REF_NUMBER VARCHAR2(5 BYTE) NOT NULL 
, CUSTOMER_ID VARCHAR2(17 BYTE) 
, PRINT_PAYMENT_DOC VARCHAR2(1 BYTE) 
, AUTOGIRO_CODE_TYPE VARCHAR2(3 BYTE) 
, PAYMENT_DUE_DATE VARCHAR2(3 BYTE) 
, DAYS_BETWEEN_INVOICE_DUE NUMBER 
, INVOICE_CURRENCY VARCHAR(3 BYTE) 
, INV_GROSS_AMT FLOAT(126) 
, INV_VAT_AMT FLOAT(126) 
, CURR_NET_VAT_FREE FLOAT(126) 
, CURR_NET_VAT_MANDATORY FLOAT(126) 
, ROUND_INV_GROSS_AMT FLOAT(126) 
, ROUND_INV_VAT_AMT FLOAT(126) 
, ROUND_CURR_NET_VAT_FREE FLOAT(126) 
, ROUND_CURR_NET_VAT_MANDATORY FLOAT(126) 
, MODIFIED_BY VARCHAR2(50 BYTE) NOT NULL 
, MODIFIED_DATE TIMESTAMP(6) NOT NULL 
, CONSTRAINT PRT_CARD_INVOICE_DETAILS_PK PRIMARY KEY 
  (
    COUNTRY_CODE 
  , INVOICE_DOC_NUMBER 
  , INVOICE_DOC_TYPE 
  , ACCOUNT_ID 
  , PAYMENT_DOC_REF_NUMBER 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE "WCPCUSTOM" 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  BUFFER_POOL DEFAULT 
) 
PARTITION BY LIST (COUNTRY_CODE) 
(
  PARTITION PRT_CARD_INV_DETAILS_SWEDEN VALUES ('SE') 
  LOGGING 
  TABLESPACE "WCPCUSTOM" 
  PCTFREE 10 
  INITRANS 1 
  STORAGE 
  ( 
    BUFFER_POOL DEFAULT 
  ) 
  NOCOMPRESS  
, PARTITION PRT_CARD_INV_DETAILS_NORWAY VALUES ('NO') 
  LOGGING 
  TABLESPACE "WCPCUSTOM" 
  PCTFREE 10 
  INITRANS 1 
  STORAGE 
  ( 
    BUFFER_POOL DEFAULT 
  ) 
  NOCOMPRESS  
, PARTITION PRT_CARD_INV_DETAILS_DENMARK VALUES ('DK') 
  LOGGING 
  TABLESPACE "WCPCUSTOM" 
  PCTFREE 10 
  INITRANS 1 
  STORAGE 
  ( 
    BUFFER_POOL DEFAULT 
  ) 
  NOCOMPRESS  
, PARTITION PRT_CARD_INV_DETAILS_LATVIA VALUES ('LV') 
  LOGGING 
  TABLESPACE "WCPCUSTOM" 
  PCTFREE 10 
  INITRANS 1 
  STORAGE 
  ( 
    BUFFER_POOL DEFAULT 
  ) 
  NOCOMPRESS  
, PARTITION PRT_CARD_INV_DETAILS_LITHUANIA VALUES ('LT') 
  LOGGING 
  TABLESPACE "WCPCUSTOM" 
  PCTFREE 10 
  INITRANS 1 
  STORAGE 
  ( 
    BUFFER_POOL DEFAULT 
  ) 
  NOCOMPRESS  
, PARTITION PRT_CARD_INV_DETAILS_ESTONIA VALUES ('EE') 
  LOGGING 
  TABLESPACE "WCPCUSTOM" 
  PCTFREE 10 
  INITRANS 1 
  STORAGE 
  ( 
    BUFFER_POOL DEFAULT 
  ) 
  NOCOMPRESS  
, PARTITION PRT_CARD_INV_DETAILS_POLAND VALUES ('PL') 
  LOGGING 
  TABLESPACE "WCPCUSTOM" 
  PCTFREE 10 
  INITRANS 1 
  STORAGE 
  ( 
    BUFFER_POOL DEFAULT 
  ) 
  NOCOMPRESS  
);
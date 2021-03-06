CREATE TABLE PRT_ACCOUNT 
(
  COUNTRY_CODE VARCHAR2(2 BYTE) NOT NULL 
, ACCOUNT_ID VARCHAR2(10 BYTE) NOT NULL 
, PARTNER_ID VARCHAR2(8 BYTE) NOT NULL
, CUSTOMER_GROUP VARCHAR2(2 BYTE) NOT NULL
, MODIFIED_BY VARCHAR2(50 BYTE) NOT NULL 
, MODIFIED_DATE TIMESTAMP(6) NOT NULL 

, CREATED_DATE DATE 
, CREATED_BY VARCHAR2(6 BYTE) 
, CHANGE_DATE DATE 
, CHANGE_BY VARCHAR2(6 BYTE) 
, ACCOUNT_RECON_CODE VARCHAR2(1 BYTE) 
, ACCOUNT_STMT_CODE VARCHAR2(1 BYTE) 
, INTEREST_CODE VARCHAR2(1 BYTE) 
, ACCOUNT_GROUP VARCHAR2(2 BYTE) 
, BLOCK_CODE VARCHAR2(3 BYTE) 
, ACCOUNT_DESC VARCHAR2(20 BYTE) 
, CREDIT_BLOCK_CODE VARCHAR2(3 BYTE) 
, VERSION_NUMBER NUMBER(*, 0) 
, DUNNING_LEVEL_CODE VARCHAR2(3 BYTE) 
, CREDIT_BLOCK_DATE DATE 
, CREDIT_BLOCK_TIME TIMESTAMP(6) 
, ACCOUNT_BLOCK_DATE DATE 
, ACCOUNT_BLOCK_TIME TIMESTAMP(6) 
, DUNNING_PROC_ID VARCHAR2(4 BYTE) 
, GIRO_NUMBER VARCHAR2(16 BYTE) 
, CONSTRAINT PRT_ACCOUNT_PK PRIMARY KEY 
  (
    COUNTRY_CODE 
  , ACCOUNT_ID 
  )
  ENABLE 
) 

PARTITION BY LIST(COUNTRY_CODE) (
    PARTITION PRT_ACCOUNT_SWEDEN VALUES ('SE'),
    PARTITION PRT_ACCOUNT_NORWAY VALUES ('NO'),
    PARTITION PRT_ACCOUNT_DENMARK VALUES ('DK'),
    PARTITION PRT_ACCOUNT_LATVIA VALUES ('LV'),
    PARTITION PRT_ACCOUNT_LITHUANIA VALUES ('LT'),
    PARTITION PRT_ACCOUNT_ESTONIA VALUES ('EE'),
    PARTITION PRT_ACCOUNT_POLAND VALUES ('PL')
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
);

ALTER TABLE PRT_ACCOUNT
ADD CONSTRAINT PRT_ACCOUNT_PRT_PARTNER_FK1 FOREIGN KEY
(
  COUNTRY_CODE 
, PARTNER_ID 
)
REFERENCES PRT_PARTNER
(
  COUNTRY_CODE 
, PARTNER_ID 
)
ENABLE;

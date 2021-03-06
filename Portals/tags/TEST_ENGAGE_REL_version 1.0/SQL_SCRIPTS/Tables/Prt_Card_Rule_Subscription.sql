CREATE TABLE PRT_CARD_RULE_SUBSCRIPTION 
(
  COUNTRY_CODE VARCHAR2(2 BYTE) NOT NULL 
, USER_ID VARCHAR2(100 BYTE) NOT NULL 
, SUBSCR_ID NUMBER NOT NULL 
, RULE_ID NUMBER NOT NULL 
, SUBSCR_DATE DATE NOT NULL 
, SUBSCR_STATUS VARCHAR2(20 BYTE) 
, PARTNER_ID VARCHAR2(8 BYTE) 
, ACCOUNT_ID VARCHAR2(10 BYTE) 
, CARDGROUP_MAIN VARCHAR2(3 BYTE) 
, CARDGROUP_SUB VARCHAR2(3 BYTE) 
, CARDGROUP_SEQ VARCHAR2(5 BYTE) 
, CARD_KSID VARCHAR2(10 BYTE) 
, MODIFIED_BY VARCHAR2(50 BYTE) NOT NULL 
, MODIFIED_DATE TIMESTAMP(6) NOT NULL 
, CONSTRAINT PRT_CARD_RULE_SUBSCRIPTIO_PK PRIMARY KEY 
  (
    SUBSCR_ID 
  , COUNTRY_CODE 
  )
  ENABLE 
) 

PARTITION BY LIST(COUNTRY_CODE) (
    PARTITION PRT_CARD_RULE_SUB_SWEDEN VALUES ('SE'),
    PARTITION PRT_CARD_RULE_SUB_NORWAY VALUES ('NO'),
    PARTITION PRT_CARD_RULE_SUB_DENMARK VALUES ('DK'),
    PARTITION PRT_CARD_RULE_SUB_LATVIA VALUES ('LV'),
    PARTITION PRT_CARD_RULE_SUB_LITHUANIA VALUES ('LT'),
    PARTITION PRT_CARD_RULE_SUB_ESTONIA VALUES ('EE'),
    PARTITION PRT_CARD_RULE_SUB_POLAND VALUES ('PL')
)

LOGGING 
TABLESPACE "WCPCUSTOM" 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  BUFFER_POOL DEFAULT 
);

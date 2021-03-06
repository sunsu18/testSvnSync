CREATE TABLE PRT_CARD_TRANSACTION_REPORT 
(
  COUNTRY_CODE VARCHAR2(2 BYTE) NOT NULL 
, REPORT_ID NUMBER NOT NULL 
, SUBSCR_ID VARCHAR2(20 BYTE) NOT NULL 
, REPORT_TEMPLATE VARCHAR2(20 BYTE) 
, REPORT_SELECTION VARCHAR2(20 BYTE) 
, REPORT_COLUMNS VARCHAR2(1024 BYTE) 
, MODIFIED_BY VARCHAR2(50 BYTE) NOT NULL 
, MODIFIED_DATE TIMESTAMP(6) NOT NULL 
) 

PARTITION BY LIST(COUNTRY_CODE) (
    PARTITION PRT_CARD_TX_REPORT_SWEDEN VALUES ('SE'),
    PARTITION PRT_CARD_TX_REPORT_NORWAY VALUES ('NO'),
    PARTITION PRT_CARD_TX_REPORT_DENMARK VALUES ('DK'),
    PARTITION PRT_CARD_TX_REPORT_LATVIA VALUES ('LV'),
    PARTITION PRT_CARD_TX_REPORT_LITHUANIA VALUES ('LT'),
    PARTITION PRT_CARD_TX_REPORT_ESTONIA VALUES ('EE'),
    PARTITION PRT_CARD_TX_REPORT_POLAND VALUES ('PL')
)


LOGGING 
TABLESPACE "WCPCUSTOM" 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  BUFFER_POOL DEFAULT 
);

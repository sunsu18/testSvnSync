CREATE TABLE PRT_CARD_USER_INFORMATION 
(
  COUNTRY_CODE VARCHAR2(2 BYTE) NOT NULL 
, USER_EMAIL VARCHAR2(100 BYTE) NOT NULL 
, USER_FIRST_NAME VARCHAR2(50 BYTE) 
, USER_MIDDLE_NAME VARCHAR2(50 BYTE) 
, USER_LAST_NAME VARCHAR2(50 BYTE) 
, USER_DOB DATE 
, USER_PHONE_NO NUMBER 
, USER_LANG VARCHAR2(20 BYTE) 
, USER_SHORTNAME VARCHAR2(20 BYTE) 
, USER_STATUS VARCHAR2(20 BYTE) 
, MODIFIED_BY VARCHAR2(50 BYTE) NOT NULL 
, MODIFIED_DATE TIMESTAMP(6) NOT NULL 

, CONSTRAINT PRT_CARD_USER_INFORMATION_PK PRIMARY KEY 
  (
    USER_EMAIL 
  , COUNTRY_CODE 
  )
  ENABLE 
) 

PARTITION BY LIST(COUNTRY_CODE) (
    PARTITION PRT_CARD_USER_INFO_SWEDEN VALUES ('SE'),
    PARTITION PRT_CARD_USER_INFO_NORWAY VALUES ('NO'),
    PARTITION PRT_CARD_USER_INFO_DENMARK VALUES ('DK'),
    PARTITION PRT_CARD_USER_INFO_LATVIA VALUES ('LV'),
    PARTITION PRT_CARD_USER_INFO_LITHUANIA VALUES ('LT'),
    PARTITION PRT_CARD_USER_INFO_ESTONIA VALUES ('EE'),
    PARTITION PRT_CARD_USER_INFO_POLAND VALUES ('PL')
)


LOGGING 
TABLESPACE "WCPCUSTOM" 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  BUFFER_POOL DEFAULT 
);
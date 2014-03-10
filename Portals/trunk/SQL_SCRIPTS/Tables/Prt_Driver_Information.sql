CREATE TABLE PRT_DRIVER_INFORMATION 
(
  PRT_DRIVER_INFORMATION_PK VARCHAR2(20 BYTE) NOT NULL 
, ACCOUNT_NUMBER VARCHAR2(20 BYTE) NOT NULL 
, CARD_NUMBER VARCHAR2(30 BYTE) 
, DRIVER_NUMBER VARCHAR2(20 BYTE) NOT NULL 
, DRIVER_NAME VARCHAR2(30 BYTE) NOT NULL 
, NATIONALITY VARCHAR2(15 BYTE) 
, MOBILE_NUMBER NUMBER 
, REMARKS VARCHAR2(30 BYTE) 
, PASSPORT_NUMBER VARCHAR2(20 BYTE) 
, PASSPORT_EXPIRY DATE 
, LICENSE_NUMBER VARCHAR2(20 BYTE) 
, LICENSE_EXPIRY DATE 
, EMPLOY_START DATE 
, EMPLOY_END DATE 
, MODIFIED_BY VARCHAR2(20 BYTE) NOT NULL 
, MODIFIED_DATE TIMESTAMP(6) NOT NULL 
, CONSTRAINT PRT_DRIVER_INFORMATION_PK PRIMARY KEY 
  (
    PRT_DRIVER_INFORMATION_PK 
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
);
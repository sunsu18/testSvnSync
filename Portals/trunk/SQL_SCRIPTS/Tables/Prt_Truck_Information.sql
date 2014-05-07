CREATE TABLE PRT_TRUCK_INFORMATION 
(
  PRT_TRUCK_INFORMATION_PK VARCHAR2(20 BYTE) NOT NULL
, COUNTRY_CODE VARCHAR2(2 BYTE) NOT NULL 
, ACCOUNT_NUMBER VARCHAR2(10 BYTE) NOT NULL 
, CARD_NUMBER VARCHAR2(20 BYTE) 
, VEHICLE_NUMBER VARCHAR2(20 BYTE) NOT NULL 
, INTERNAL_NAME VARCHAR2(20 BYTE) NOT NULL 
, REGISTRATION_NUMBER VARCHAR2(20 BYTE) 
, BRAND VARCHAR2(20 BYTE) 
, YEAR NUMBER 
, REGISTRATION_DATE DATE 
, END_DATE DATE 
, FUEL_TYPE VARCHAR2(15 BYTE) 
, MAX_FUEL NUMBER 
, ODOMETER NUMBER 
, REMARKS VARCHAR2(20 BYTE) 
, MODIFIED_BY VARCHAR2(50 BYTE) NOT NULL 
, MODIFIED_DATE TIMESTAMP(6) NOT NULL 
, CONSTRAINT PRT_TRUCK_INFORMATION_PK PRIMARY KEY 
  (
    PRT_TRUCK_INFORMATION_PK 
  )
  ENABLE 
) 

PARTITION BY LIST(COUNTRY_CODE) (
    PARTITION PRT_TRUCK_INFO_SWEDEN VALUES ('SE'),
    PARTITION PRT_TRUCK_INFO_NORWAY VALUES ('NO'),
    PARTITION PRT_TRUCK_INFO_DENMARK VALUES ('DK'),
    PARTITION PRT_TRUCK_INFO_LATVIA VALUES ('LV'),
    PARTITION PRT_TRUCK_INFO_LITHUANIA VALUES ('LT'),
    PARTITION PRT_TRUCK_INFO_ESTONIA VALUES ('EE'),
    PARTITION PRT_TRUCK_INFO_POLAND VALUES ('PL')
)


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
);

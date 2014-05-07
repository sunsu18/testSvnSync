CREATE TABLE ENGAGE_SOA_DELTATABLE 
(
  INTERFACEID VARCHAR2(20) NOT NULL 
, COUNTRYCODE VARCHAR2(20) NOT NULL 
, PALS_DELTA_TIMESTAMP TIMESTAMP 
, UPDATEDBY VARCHAR2(40) 
, MODIFIEDBYTIMESTAMP TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
, CONSTRAINT ENGAGE_SOA_DELTATABLE_PK PRIMARY KEY 
  (
    INTERFACEID 
  , COUNTRYCODE 
  )
  ENABLE 
);
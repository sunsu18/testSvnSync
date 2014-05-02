CREATE TABLE ENGAGE_PALS_PAR_DTA_ERRORLOG 
(
  DELTA_TS TIMESTAMP NOT NULL 
, PARTNER_ID VARCHAR2(8) 
, PARTNER_TP VARCHAR2(3) 
, REDIGERT_NV VARCHAR2(60) 
, FODSEL_DT DATE DEFAULT '01-JAN-0001' 
, PERSON_NR VARCHAR2(15) 
, ORGANISASJON_NR VARCHAR2(20) 
, KJONN VARCHAR2(1) 
, MVAREG_NR VARCHAR2(20) 
, PRIADRESSE_1 VARCHAR2(60) 
, PRIADRESSE_2 VARCHAR2(60) 
, PRIADRPOST_NR VARCHAR2(10) 
, PRIADRPOSTSTED VARCHAR2(60) 
, PRIADRLOPE_NR VARCHAR2(5) 
, PRIADRLAND_KD VARCHAR2(2) 
, PRIADRNIVA_KD VARCHAR2(3) 
, PRIADRESSE_TP VARCHAR2(3) 
, SOA_ERROR_CODE VARCHAR2(20) NOT NULL 
, SOA_ERROR_DESC VARCHAR2(300) 
, UPDATED_BY VARCHAR2(40) 
, UPDATED_TIMESTAMP TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
, EMAIL_FLAG INTEGER 
, DELTA_RECIPT_FLAG INTEGER 
, CONSTRAINT ENGAGE_PALS_PAR_DTA_ERROR_PK PRIMARY KEY 
  (
    DELTA_TS 
  , SOA_ERROR_CODE 
  )
  ENABLE 
);

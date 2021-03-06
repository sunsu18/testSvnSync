
  CREATE TABLE "SOACUSTOM"."ENGAGE_SOA_TXNDTL_ERRORLOG" 
   (	"DELTA_TS" TIMESTAMP (6) NOT NULL ENABLE, 
	"UREF_TRANSACTION_ID" VARCHAR2(20 BYTE), 
	"DETAIL_NO" VARCHAR2(20 BYTE), 
	"ORDER_ID" VARCHAR2(20 BYTE), 
	"PRELIM_ID" VARCHAR2(20 BYTE), 
	"ORDER_LINE_NUMBER" VARCHAR2(20 BYTE), 
	"PRICE_ELEMENT_CODE" VARCHAR2(20 BYTE), 
	"PRICE_ELEMENT_RATE" FLOAT(126), 
	"ORDER_LINE_REF" VARCHAR2(20 BYTE), 
	"PALS_P_GROUP_ID" VARCHAR2(20 BYTE), 
	"PALS_P_GROUP_SUBGROUP_ID" VARCHAR2(20 BYTE), 
	"FUEL_TRANS_FLAG" VARCHAR2(20 BYTE), 
	"PRODUCT_NAME" VARCHAR2(100 BYTE), 
	"UNIT_OF_MEASURE" VARCHAR2(20 BYTE), 
	"QUANTITY" FLOAT(126), 
	"VAT_RATE" FLOAT(126), 
	"CURRENCY_UNIT_PRICE" FLOAT(126), 
	"CURRENCY_GROSS_AMOUNT" FLOAT(126), 
	"INVOICED_NET_UNIT_PRC_REBATED" FLOAT(126), 
	"INVOICED_UNIT_PRICE_REBATED" FLOAT(126), 
	"INVOICED_NET_AMOUNT_RABETED" FLOAT(126), 
	"INVOICED_GROSS_AMOUNT_REBATED" FLOAT(126), 
	"INVOIVED_VAT_REBATED" FLOAT(126), 
	"PALS_MODIFIED_DATE" DATE, 
	"CUSTOMER_SERVICE_ID" VARCHAR2(20 BYTE), 
	"EMAIL_FLAG" NUMBER(*,0), 
	"SOA_ERROR_CODE" VARCHAR2(20 BYTE) NOT NULL ENABLE, 
	"SOA_ERROR_DESC" VARCHAR2(1000 BYTE), 
	"DELTA_RECIPT_FLAG" NUMBER, 
	"MODIFIED_BY" VARCHAR2(20 BYTE), 
	"MODIFIED_DATETIME" TIMESTAMP (6) DEFAULT CURRENT_TIMESTAMP, 
	 CONSTRAINT "TXNDTL_ERRORLOG_PK" PRIMARY KEY ("DELTA_TS", "SOA_ERROR_CODE")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SOACUSTOM"  ENABLE
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SOACUSTOM" ;

  CREATE OR REPLACE TRIGGER "SOACUSTOM"."TRXNDTL_ERRORLOG_TRIG" 
BEFORE INSERT OR UPDATE ON ENGAGE_SOA_TXNDTL_ERRORLOG 
FOR EACH ROW 
BEGIN
:new.MODIFIED_DATETIME := CURRENT_TIMESTAMP;
:new.MODIFIED_BY := SYS_CONTEXT('USERENV','OS_USER');
END;
/
ALTER TRIGGER "SOACUSTOM"."TRXNDTL_ERRORLOG_TRIG" ENABLE;

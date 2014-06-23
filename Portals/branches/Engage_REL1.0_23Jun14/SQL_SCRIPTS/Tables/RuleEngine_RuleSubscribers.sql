
  CREATE TABLE "WCPCUSTOM"."RULEENGINE_RULESUBSCRIBERS" 
   (	"RULEID" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	"CUSTOMERID" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	"FREQUENCY" VARCHAR2(20 BYTE), 
	"LASTMAILSENT" TIMESTAMP (2), 
	"STATUS" VARCHAR2(10 BYTE) NOT NULL ENABLE, 
	"CHANNELPROTOCOL" VARCHAR2(50 BYTE) NOT NULL ENABLE, 
	"PARAM1" VARCHAR2(500 BYTE), 
	"PARAM2" VARCHAR2(500 BYTE), 
	"PARAM3" VARCHAR2(500 BYTE), 
	 CONSTRAINT "RULEENGINE_RULESUBSCRIBER_UK1" UNIQUE ("RULEID", "CUSTOMERID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "WCPCUSTOM"  ENABLE
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "WCPCUSTOM" ;

  CREATE UNIQUE INDEX "WCPCUSTOM"."RULEENGINE_RULESUBSCRIBER_UK1" ON "WCPCUSTOM"."RULEENGINE_RULESUBSCRIBERS" ("RULEID", "CUSTOMERID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "WCPCUSTOM" ;
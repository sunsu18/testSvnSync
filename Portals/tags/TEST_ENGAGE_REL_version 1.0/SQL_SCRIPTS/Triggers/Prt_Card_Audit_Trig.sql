create or replace
TRIGGER PRT_CARD_AUDIT_TRIG AFTER
INSERT
OR
UPDATE
OR
DELETE 
ON PRT_CARD FOR EACH ROW 

declare

v_action char(3);
v_user  PRT_CARD_AUDIT.U_ID%type;
v_audit_time PRT_CARD_AUDIT.NEW_TIMESTAMP%type;
v_cid varchar2(2);
v_id varchar2(10);

BEGIN

  IF INSERTING then
    v_action := 'INS';
    v_audit_time := sysdate;
    v_cid := :NEW.COUNTRY_CODE;
    v_id := :NEW.PRT_CARD_PK;
    
  ELSIF UPDATING then
    v_action := 'UPD';
    v_audit_time := sysdate;
    v_cid := :NEW.COUNTRY_CODE;
    v_id := :NEW.PRT_CARD_PK;
  ELSIF DELETING then
    v_action := 'DEL';
    v_audit_time := sysdate;
    v_cid := :OLD.COUNTRY_CODE;
     v_id := :OLD.PRT_CARD_PK;
  ELSE
  



  v_action := '?!!';
     v_audit_time := sysdate;
  END IF;

  begin
    v_user := sys_context(namespace => 'USERENV',attribute => 'OS_USER');
    if (v_user is null OR v_user in ('',' ','SYSTEM')) then
      v_user := USER;
    end if;
  exception when others then v_user := USER;
  end;

  INSERT INTO PRT_CARD_AUDIT
         (
         AUDIT_ID 
    , COUNTRY_CODE 
    , PRT_CARD_PK 
, OLD_PARTNER_ID 
, OLD_ACCOUNT_ID
, OLD_CARDGROUP_MAIN_TYPE 
, OLD_CARDGROUP_SUB_TYPE 
, OLD_CARDGROUP_SEQ 
, OLD_CARD_IDENTIFIER 
, OLD_CARD_TYPE 
, OLD_MAGNETIC_CODE
, OLD_CARD_EMBOSS_NUM
, OLD_INVOICE_INFORMATION
, OLD_CARD_EXPIRY 
, OLD_BLOCK_TYPE 
, OLD_BLOCK_CODE 
, OLD_BLOCK_ACTION 
, OLD_BLOCK_DATE
, OLD_BLOCK_TIME 
, OLD_BLOCK_LEVEL 
, OLD_MANUFACTURED_DATE 
, OLD_ROUTEX_CUSTOMER_NUMBER
, OLD_INVOICE_ADDR1
, OLD_INVOICE_ADDR2
, OLD_INVOICE_POSTAL_CODE
, OLD_INVOICE_CITY
, OLD_ADDR_SEQ 
, OLD_INVOICE_COUNTRY 
, OLD_INVOICE_ADDR_LEVEL 
, OLD_INVOICE_ADDR_TYPE
, OLD_MODIFIED_BY
, OLD_MODIFIED_DATE 
, NEW_PARTNER_ID 
, NEW_ACCOUNT_ID 
, NEW_CARDGROUP_MAIN_TYPE 
, NEW_CARDGROUP_SUB_TYPE 
, NEW_CARDGROUP_SEQ 
, NEW_CARD_IDENTIFIER 
, NEW_CARD_TYPE 
, NEW_MAGNETIC_CODE 
, NEW_CARD_EMBOSS_NUM 
, NEW_INVOICE_INFORMATION 
, NEW_CARD_EXPIRY 
, NEW_BLOCK_TYPE 
, NEW_BLOCK_CODE 
, NEW_BLOCK_ACTION 
, NEW_BLOCK_DATE 
, NEW_BLOCK_TIME 
, NEW_BLOCK_LEVEL 
, NEW_MANUFACTURED_DATE
, NEW_ROUTEX_CUSTOMER_NUMBER 
, NEW_INVOICE_ADDR1 
, NEW_INVOICE_ADDR2  
, NEW_INVOICE_POSTAL_CODE 
, NEW_INVOICE_CITY 
, NEW_ADDR_SEQ 
, NEW_INVOICE_COUNTRY
, NEW_INVOICE_ADDR_LEVEL 
, NEW_INVOICE_ADDR_TYPE 
, NEW_MODIFIED_BY
, NEW_MODIFIED_DATE 
    , U_ID
    , ACTION
    , NEW_TIMESTAMP
          )
  values
         ( PRT_CARD_AUDIT_SEQ.NEXTVAL,
            v_cid
           , v_id 
, :OLD.PARTNER_ID 
, :OLD.ACCOUNT_ID
, :OLD.CARDGROUP_MAIN_TYPE 
, :OLD.CARDGROUP_SUB_TYPE 
, :OLD.CARDGROUP_SEQ 
, :OLD.CARD_IDENTIFIER 
, :OLD.CARD_TYPE 
, :OLD.MAGNETIC_CODE
, :OLD.CARD_EMBOSS_NUM
, :OLD.INVOICE_INFORMATION
, :OLD.CARD_EXPIRY 
, :OLD.BLOCK_TYPE 
, :OLD.BLOCK_CODE 
, :OLD.BLOCK_ACTION 
, :OLD.BLOCK_DATE
, :OLD.BLOCK_TIME 
, :OLD.BLOCK_LEVEL 
, :OLD.MANUFACTURED_DATE 
, :OLD.ROUTEX_CUSTOMER_NUMBER
, :OLD.INVOICE_ADDR1
, :OLD.INVOICE_ADDR2
, :OLD.INVOICE_POSTAL_CODE
, :OLD.INVOICE_CITY
, :OLD.ADDR_SEQ 
, :OLD.INVOICE_COUNTRY 
, :OLD.INVOICE_ADDR_LEVEL 
, :OLD.INVOICE_ADDR_TYPE
, :OLD.MODIFIED_BY
, :OLD.MODIFIED_DATE 
, :NEW.PARTNER_ID 
, :NEW.ACCOUNT_ID 
, :NEW.CARDGROUP_MAIN_TYPE 
, :NEW.CARDGROUP_SUB_TYPE 
, :NEW.CARDGROUP_SEQ 
, :NEW.CARD_IDENTIFIER 
, :NEW.CARD_TYPE 
, :NEW.MAGNETIC_CODE 
, :NEW.CARD_EMBOSS_NUM 
, :NEW.INVOICE_INFORMATION 
, :NEW.CARD_EXPIRY 
, :NEW.BLOCK_TYPE 
, :NEW.BLOCK_CODE 
, :NEW.BLOCK_ACTION 
, :NEW.BLOCK_DATE 
, :NEW.BLOCK_TIME 
, :NEW.BLOCK_LEVEL 
, :NEW.MANUFACTURED_DATE
, :NEW.ROUTEX_CUSTOMER_NUMBER 
, :NEW.INVOICE_ADDR1 
, :NEW.INVOICE_ADDR2  
, :NEW.INVOICE_POSTAL_CODE 
, :NEW.INVOICE_CITY 
, :NEW.ADDR_SEQ 
, :NEW.INVOICE_COUNTRY
, :NEW.INVOICE_ADDR_LEVEL 
, :NEW.INVOICE_ADDR_TYPE 
, :NEW.MODIFIED_BY
, :NEW.MODIFIED_DATE 
           ,  v_user
           ,  v_action
           ,  v_audit_time         
          );
       
END;
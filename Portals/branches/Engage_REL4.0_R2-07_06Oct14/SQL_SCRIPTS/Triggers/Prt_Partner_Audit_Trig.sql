create or replace
TRIGGER PRT_PARTNER_AUDIT_TRIG AFTER
INSERT
OR
UPDATE
OR
DELETE 
ON PRT_PARTNER FOR EACH ROW 

declare

v_action char(3);
v_user  PRT_PARTNER_AUDIT.U_ID%type;
v_audit_time PRT_PARTNER_AUDIT.NEW_TIMESTAMP%type;
v_cid varchar2(2);
v_id varchar2(8);

BEGIN

  IF INSERTING then
    v_action := 'INS';
    v_audit_time := sysdate;
    v_cid := :NEW.COUNTRY_CODE;
    v_id := :NEW.PARTNER_ID;
    
  ELSIF UPDATING then
    v_action := 'UPD';
    v_audit_time := sysdate;
    v_cid := :NEW.COUNTRY_CODE;
    v_id := :NEW.PARTNER_ID;
  ELSIF DELETING then
    v_action := 'DEL';
    v_audit_time := sysdate;
    v_cid := :OLD.COUNTRY_CODE;
     v_id := :OLD.PARTNER_ID;
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

  INSERT INTO PRT_PARTNER_AUDIT
         (
         AUDIT_ID 
    , COUNTRY_CODE 
    , PARTNER_ID 
    , OLD_COMPANY_TYPE 
    , OLD_COMPANY_NAME 
    , OLD_DOB 
    , OLD_PERSON_NUMBER
    , OLD_ORG_ID 
    , OLD_GENDER 
    , OLD_VAT_REG_NUMBER 
    , OLD_PARTNER_ADDR1 
    , OLD_PARTNER_ADDR2 
    , OLD_PARTNER_POSTAL_CODE
    , OLD_PARTNER_CITY 
    , OLD_PARTNER_ADDR_SEQ 
    , OLD_PARTNER_COUNTRY 
    , OLD_PARTNER_ADDR_LEVEL 
    , OLD_PARTNER_ADDR_TYPE 
    , OLD_MODIFIED_BY 
    , OLD_MODIFIED_DATE 
    , NEW_COMPANY_TYPE
    , NEW_COMPANY_NAME 
    , NEW_DOB
    , NEW_PERSON_NUMBER 
    , NEW_ORG_ID 
    , NEW_GENDER 
    , NEW_VAT_REG_NUMBER 
    , NEW_PARTNER_ADDR1 
    , NEW_PARTNER_ADDR2 
    , NEW_PARTNER_POSTAL_CODE 
    , NEW_PARTNER_CITY 
    , NEW_PARTNER_ADDR_SEQ 
    , NEW_PARTNER_COUNTRY 
    , NEW_PARTNER_ADDR_LEVEL 
    , NEW_PARTNER_ADDR_TYPE 
    , NEW_MODIFIED_BY
    , NEW_MODIFIED_DATE
    , U_ID
    , ACTION
    , NEW_TIMESTAMP
          )
  values
         ( PRT_PARTNER_AUDIT_SEQ.NEXTVAL,
            v_cid
           , v_id 
    , :OLD.COMPANY_TYPE 
    , :OLD.COMPANY_NAME 
    , :OLD.DOB 
    , :OLD.PERSON_NUMBER
    , :OLD.ORG_ID 
    , :OLD.GENDER 
    , :OLD.VAT_REG_NUMBER 
    , :OLD.PARTNER_ADDR1 
    , :OLD.PARTNER_ADDR2 
    , :OLD.PARTNER_POSTAL_CODE
    , :OLD.PARTNER_CITY 
    , :OLD.PARTNER_ADDR_SEQ 
    , :OLD.PARTNER_COUNTRY 
    , :OLD.PARTNER_ADDR_LEVEL 
    , :OLD.PARTNER_ADDR_TYPE 
    , :OLD.MODIFIED_BY 
    , :OLD.MODIFIED_DATE 
    , :NEW.COMPANY_TYPE
    , :NEW.COMPANY_NAME 
    , :NEW.DOB
    , :NEW.PERSON_NUMBER 
    , :NEW.ORG_ID 
    , :NEW.GENDER 
    , :NEW.VAT_REG_NUMBER 
    , :NEW.PARTNER_ADDR1 
    , :NEW.PARTNER_ADDR2 
    , :NEW.PARTNER_POSTAL_CODE 
    , :NEW.PARTNER_CITY 
    , :NEW.PARTNER_ADDR_SEQ 
    , :NEW.PARTNER_COUNTRY 
    , :NEW.PARTNER_ADDR_LEVEL 
    , :NEW.PARTNER_ADDR_TYPE 
    , :NEW.MODIFIED_BY
    , :NEW.MODIFIED_DATE
           ,  v_user
           ,  v_action
           ,  v_audit_time         
          );
       
END;
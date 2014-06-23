create or replace
TRIGGER PRT_ACCOUNT_AUDIT_TRIG AFTER
INSERT
OR
UPDATE
OR
DELETE 
ON PRT_ACCOUNT FOR EACH ROW 

declare

v_action char(3);
v_user  PRT_ACCOUNT_AUDIT.U_ID%type;
v_audit_time PRT_ACCOUNT_AUDIT.NEW_TIMESTAMP%type;
v_cid varchar2(2);
v_id varchar2(10);

BEGIN

  IF INSERTING then
    v_action := 'INS';
    v_audit_time := sysdate;
    v_cid := :NEW.COUNTRY_CODE;
    v_id := :NEW.ACCOUNT_ID;
    
  ELSIF UPDATING then
    v_action := 'UPD';
    v_audit_time := sysdate;
    v_cid := :NEW.COUNTRY_CODE;
    v_id := :NEW.ACCOUNT_ID;
  ELSIF DELETING then
    v_action := 'DEL';
    v_audit_time := sysdate;
    v_cid := :OLD.COUNTRY_CODE;
     v_id := :OLD.ACCOUNT_ID;
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

  INSERT INTO PRT_ACCOUNT_AUDIT
         (
         AUDIT_ID 
    , COUNTRY_CODE 
    , ACCOUNT_ID 
    , OLD_PARTNER_ID 
    , OLD_CUSTOMER_GROUP 
    , OLD_MODIFIED_BY 
    , OLD_MODIFIED_DATE 
    , NEW_PARTNER_ID 
    , NEW_CUSTOMER_GROUP 
    , NEW_MODIFIED_BY 
    , NEW_MODIFIED_DATE 
    , U_ID
    , ACTION
    , NEW_TIMESTAMP
          )
  values
         ( PRT_ACCOUNT_AUDIT_SEQ.NEXTVAL,
            v_cid
           , v_id 
    , :OLD.PARTNER_ID 
    , :OLD.CUSTOMER_GROUP 
    , :OLD.MODIFIED_BY 
    , :OLD.MODIFIED_DATE 
    , :NEW.PARTNER_ID 
    , :NEW.CUSTOMER_GROUP 
    , :NEW.MODIFIED_BY 
    , :NEW.MODIFIED_DATE 
           ,  v_user
           ,  v_action
           ,  v_audit_time         
          );
       
END;
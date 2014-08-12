create or replace
TRIGGER PRT_CARDGROUP_AUDIT_TRIG AFTER
INSERT
OR
UPDATE
OR
DELETE 
ON PRT_CARDGROUP FOR EACH ROW 

declare

v_action char(3);
v_user  PRT_CARDGROUP_AUDIT.U_ID%type;
v_audit_time PRT_CARDGROUP_AUDIT.NEW_TIMESTAMP%type;
v_cid varchar2(2);


BEGIN

  IF INSERTING then
    v_action := 'INS';
    v_audit_time := sysdate;
    v_cid := :NEW.COUNTRY_CODE;
    
  ELSIF UPDATING then
    v_action := 'UPD';
    v_audit_time := sysdate;
    v_cid := :NEW.COUNTRY_CODE;

  ELSIF DELETING then
    v_action := 'DEL';
    v_audit_time := sysdate;
    v_cid := :OLD.COUNTRY_CODE;

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

  INSERT INTO PRT_CARDGROUP_AUDIT
         (
         AUDIT_ID 
    , COUNTRY_CODE 
    , OLD_PARTNER_ID 
    , OLD_ACCOUNT_ID 
    , OLD_CARDGROUP_MAIN_TYPE 
    , OLD_CARDGROUP_SUB_TYPE 
    , OLD_CARDGROUP_SEQ 
    , OLD_CARDGROUP_DESCRIPTION 
    , OLD_MODIFIED_BY 
    , OLD_MODIFIED_DATE 
    , NEW_PARTNER_ID 
    , NEW_ACCOUNT_ID 
    , NEW_CARDGROUP_MAIN_TYPE
    , NEW_CARDGROUP_SUB_TYPE 
    , NEW_CARDGROUP_SEQ 
    , NEW_CARDGROUP_DESCRIPTION 
    , NEW_MODIFIED_BY 
    , NEW_MODIFIED_DATE 
    , U_ID
    , ACTION
    , NEW_TIMESTAMP
          )
  values
         ( PRT_CARDGROUP_AUDIT_SEQ.NEXTVAL
           , v_cid 
    , :OLD.PARTNER_ID 
    , :OLD.ACCOUNT_ID 
    , :OLD.CARDGROUP_MAIN_TYPE 
    , :OLD.CARDGROUP_SUB_TYPE 
    , :OLD.CARDGROUP_SEQ 
    , :OLD.CARDGROUP_DESCRIPTION 
    , :OLD.MODIFIED_BY 
    , :OLD.MODIFIED_DATE 
    , :NEW.PARTNER_ID 
    , :NEW.ACCOUNT_ID 
    , :NEW.CARDGROUP_MAIN_TYPE
    , :NEW.CARDGROUP_SUB_TYPE 
    , :NEW.CARDGROUP_SEQ 
    , :NEW.CARDGROUP_DESCRIPTION 
    , :NEW.MODIFIED_BY 
    , :NEW.MODIFIED_DATE 
           ,  v_user
           ,  v_action
           ,  v_audit_time         
          );
       
END;
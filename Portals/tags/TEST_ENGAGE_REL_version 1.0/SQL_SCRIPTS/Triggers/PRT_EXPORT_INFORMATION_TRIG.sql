create or replace
TRIGGER PRT_EXPORT_INFORMATION_TRIG BEFORE INSERT ON PRT_EXPORT_INFORMATION
FOR EACH ROW 
BEGIN

if (:NEW.S_NO IS NULL) then
SELECT PRT_EXPORT_INFORMATION_SEQ.NEXTVAL INTO :NEW.S_NO FROM DUAL;
end if;
END;





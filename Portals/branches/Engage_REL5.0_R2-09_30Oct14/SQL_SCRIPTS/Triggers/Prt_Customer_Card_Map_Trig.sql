create or replace
TRIGGER PRT_CUSTOMER_CARD_MAP_TRIG BEFORE INSERT ON PRT_CUSTOMER_CARD_MAP
FOR EACH ROW 
BEGIN

if (:NEW.PRT_CUSTOMER_CARD_MAP_PK IS NULL) then
SELECT PRT_CUSTOMER_CARD_MAP_SEQ.NEXTVAL INTO :NEW.PRT_CUSTOMER_CARD_MAP_PK FROM DUAL;
end if;
END;
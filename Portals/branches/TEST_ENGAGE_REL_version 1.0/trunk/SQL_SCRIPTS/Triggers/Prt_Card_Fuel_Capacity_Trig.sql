CREATE OR REPLACE TRIGGER PRT_CARD_FUEL_CAPACITY_TRIG BEFORE INSERT ON  PRT_CARD_FUEL_CAPACITY
FOR EACH ROW 
BEGIN
if (:NEW.RULE_FUEL_CAP_ID  IS NULL) then
SELECT PRT_CARD_FUEL_CAPACITY_SEQ.NEXTVAL INTO :NEW.RULE_FUEL_CAP_ID FROM DUAL;
end if;
END;
/
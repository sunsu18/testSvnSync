CREATE OR REPLACE TRIGGER PRT_CARD_NOTIFICATIONS_TRIG BEFORE INSERT ON  PRT_CARD_NOTIFICATIONS
FOR EACH ROW 
BEGIN
if (:NEW.NOTI_ID  IS NULL) then
SELECT PRT_CARD_NOTIFICATIONS_SEQ.NEXTVAL INTO :NEW.NOTI_ID FROM DUAL;
end if;
END;
/
CREATE OR REPLACE TRIGGER PRT_CARD_TX_REPORT_TRIG BEFORE INSERT ON  PRT_CARD_TRANSACTION_REPORT
FOR EACH ROW 
BEGIN
if (:NEW.REPORT_ID  IS NULL) then
SELECT PRT_CARD_TX_REPORT_SEQ.NEXTVAL INTO :NEW.REPORT_ID FROM DUAL;
end if;
END;
/
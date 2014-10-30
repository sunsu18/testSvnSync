CREATE OR REPLACE TRIGGER PRT_CARD_REPORT_DETAILS_TRIG BEFORE INSERT ON  PRT_CARD_REPORT_DETAILS
FOR EACH ROW 
BEGIN
if (:NEW.REP_DETAIL_ID  IS NULL) then
SELECT PRT_CARD_REPORT_DETAILS_SEQ.NEXTVAL INTO :NEW.REP_DETAIL_ID FROM DUAL;
end if;
END;
/
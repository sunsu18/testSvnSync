CREATE TRIGGER "PRT_REPORT_INFORMATION_TRIG" BEFORE
  INSERT ON PRT_REPORT_INFORMATION FOR EACH ROW BEGIN IF
    (
      :NEW.S_NO IS NULL
    )
    THEN
  SELECT PRT_REPORT_INFORMATION_SEQ.NEXTVAL
  INTO :NEW.S_NO
  FROM DUAL;
END IF;
END;
/

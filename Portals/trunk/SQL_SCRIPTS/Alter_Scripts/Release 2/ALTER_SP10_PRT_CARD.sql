ALTER TABLE PRT_CARD
  ADD CARD_PURCHASE_PROFILE varchar2(3);
  
ALTER TABLE PRT_CARD
  ADD CARD_TEXTLINE1 varchar2(30);
  
ALTER TABLE PRT_CARD
  ADD CARD_TEXTLINE2 varchar2(30);
  
ALTER TABLE PRT_CARD
  ADD CARD_INVOICE_EMAIL varchar2(50);
  
  
ALTER TABLE PRT_CARD
  DROP COLUMN CARD_IDENTIFIER;
    
ALTER TABLE PRT_CARD
  DROP COLUMN MAGNETIC_CODE;
    
ALTER TABLE PRT_CARD
  DROP COLUMN ROUTEX_CUSTOMER_NUMBER;
    
ALTER TABLE PRT_CARD
  DROP COLUMN ADDR_SEQ;
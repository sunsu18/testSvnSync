CREATE PROCEDURE PRT_GET_REPORT_RESOURCEBUNDLE 
(
  PRT_REPORT_PAGE IN PRT_REPORT_INFORMATION.REPORT_PAGE%TYPE,
  p_REPORT OUT SYS_REFCURSOR
)
AS 
BEGIN
    OPEN p_REPORT FOR
  SELECT EN,DK,ET,LT,LV,NO_NO,SE,PL FROM PRT_REPORT_INFORMATION WHERE REPORT_PAGE=PRT_REPORT_PAGE;
END PRT_GET_REPORT_RESOURCEBUNDLE;
/

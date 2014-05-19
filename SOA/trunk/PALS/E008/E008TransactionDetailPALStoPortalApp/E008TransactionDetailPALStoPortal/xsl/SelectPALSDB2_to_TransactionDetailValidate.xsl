<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../SelectPALSDB2DatabaseAdapterV1.wsdl"/>
      <rootElement name="Cc078Vs1CssolnjDtaCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectPALSDB2DatabaseAdapterV1"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../SelectCountPALSDB2DatabaseAdapterV1.wsdl"/>
      <rootElement name="ValidationPayload" namespace="http://www.statoilfuelretail.com/integration/E008/ValidatePALSDB2"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [FRI MAY 16 15:04:20 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:ns1="http://www.statoilfuelretail.com/integration/E008/ValidatePALSDB2"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E008TransactionDetailPALStoPortalApp/E008TransactionDetailPALStoPortal/SelectCountPALSDB2DatabaseAdapterV1"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectPALSDB2DatabaseAdapterV1"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/SelectCountPALSDB2DatabaseAdapterV1"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E008TransactionDetailPALStoPortalApp/E008TransactionDetailPALStoPortal/SelectPALSDB2DatabaseAdapterV1"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl plt wsdl top xsd tns ns1 ns0 db aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:template match="/">
    <ns1:ValidationPayload>
      <xsl:for-each select="/top:Cc078Vs1CssolnjDtaCollection/top:Cc078Vs1CssolnjDta">
        <ns1:TransactionData>
          <ns1:UREF_TRANSACTION_ID>
            <xsl:value-of select="top:salgstransId"/>
          </ns1:UREF_TRANSACTION_ID>
          <ns1:DETAIL_NO>
            <xsl:value-of select="top:salgstranslnjLnr"/>
          </ns1:DETAIL_NO>
          <ns1:ORDER_ID>
            <xsl:value-of select="top:ordreId"/>
          </ns1:ORDER_ID>
          <ns1:PRELIM_ID>
            <xsl:value-of select="top:prelimId"/>
          </ns1:PRELIM_ID>
          <ns1:ORDER_LINE_NUMBER>
            <xsl:value-of select="top:ordrelinjeLnr"/>
          </ns1:ORDER_LINE_NUMBER>
          <ns1:PRICE_ELEMENT_CODE>
            <xsl:value-of select="top:prpslKd"/>
          </ns1:PRICE_ELEMENT_CODE>
          <ns1:PRICE_ELEMENT_RATE>
            <xsl:value-of select="top:prpslsats"/>
          </ns1:PRICE_ELEMENT_RATE>
          <ns1:ORDER_LINE_REF>
            <xsl:value-of select="top:tilordrelinjeLnr"/>
          </ns1:ORDER_LINE_REF>
          <ns1:PALS_P_GROUP_ID>
            <xsl:value-of select="top:stasvaregrpKd"/>
          </ns1:PALS_P_GROUP_ID>
          <ns1:PALS_P_GROUP_SUBGROUP_ID>
            <xsl:value-of select="top:kvalitet"/>
          </ns1:PALS_P_GROUP_SUBGROUP_ID>
          <ns1:FUEL_TRANS_FLAG>
            <xsl:value-of select="top:drivstoffJn"/>
          </ns1:FUEL_TRANS_FLAG>
          <ns1:PRODUCT_NAME>
            <xsl:value-of select="top:lokproduktNv"/>
          </ns1:PRODUCT_NAME>
          <ns1:UNIT_OF_MEASURE>
            <xsl:value-of select="top:olkvantitetTp"/>
          </ns1:UNIT_OF_MEASURE>
          <ns1:QUANTITY>
            <xsl:value-of select="top:olkvantitet"/>
          </ns1:QUANTITY>
          <ns1:VAT_RATE>
            <xsl:value-of select="top:olmvasats"/>
          </ns1:VAT_RATE>
          <ns1:CURRENCY_UNIT_PRICE>
            <xsl:value-of select="top:olopgbrtenhpr"/>
          </ns1:CURRENCY_UNIT_PRICE>
          <ns1:CURRENCY_GROSS_AMOUNT>
            <xsl:value-of select="top:olopgbrtBel"/>
          </ns1:CURRENCY_GROSS_AMOUNT>
          <ns1:INV_NET_UNIT_PRICE_REBATED>
            <xsl:value-of select="top:olbernetenhpr"/>
          </ns1:INV_NET_UNIT_PRICE_REBATED>
          <ns1:INVOICED_UNIT_PRICE_REBATED>
            <xsl:value-of select="top:olberbrtenhpr"/>
          </ns1:INVOICED_UNIT_PRICE_REBATED>
          <ns1:INVOICED_NET_AMOUNT_REBATED>
            <xsl:value-of select="top:olbernetBel"/>
          </ns1:INVOICED_NET_AMOUNT_REBATED>
          <ns1:INVOICED_GROSS_AMOUNT_REBATED>
            <xsl:value-of select="top:olberbrtBel"/>
          </ns1:INVOICED_GROSS_AMOUNT_REBATED>
          <ns1:INVOIVED_VAT_REBATED>
            <xsl:value-of select="top:olbermvaBel"/>
          </ns1:INVOIVED_VAT_REBATED>
          <ns1:PALS_MODIFIED_DATE>
            <xsl:value-of select="top:endretDt"/>
          </ns1:PALS_MODIFIED_DATE>
          <ns1:PALS_MODIFIED_BY>
            <xsl:value-of select="top:endretSgn"/>
          </ns1:PALS_MODIFIED_BY>
        </ns1:TransactionData>
      </xsl:for-each>
    </ns1:ValidationPayload>
  </xsl:template>
</xsl:stylesheet>

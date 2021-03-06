<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../SelectPALSDB2DatabaseAdapterV1.wsdl"/>
      <rootElement name="Cc078Vs1CssolnjDtaCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectPALSDB2DatabaseAdapterV1"/>
    </source>
    <source type="WSDL">
      <schema location="../E008TransactionDetailPALStoPortalClient.wsdl"/>
      <rootElement name="invocationMsg" namespace="http://www.statoilfuelretail.com/integration/E008/E008InvocationReqMsg"/>
      <param name="ReceiveInvocationMsgIV.invocationPayload" />
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../MergeTransactionDetailErrorLogDatabaseAdapterV1.wsdl"/>
      <rootElement name="EngageSoaTxndtlErrorlogCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeTransactionDetailErrorLogDatabaseAdapterV1"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [TUE JUL 01 18:03:00 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:ns1="http://xmlns.oracle.com/pcbpel/adapter/db/E008TransactionDetailPALStoPortalApp/E008TransactionDetailPALStoPortal/MergeTransactionDetailErrorLogDatabaseAdapterV1"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:inp1="http://www.statoilfuelretail.com/integration/E008/E008InvocationReqMsg"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:ns2="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeTransactionDetailErrorLogDatabaseAdapterV1"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectPALSDB2DatabaseAdapterV1"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:ns0="http://oracle.com/sca/soapservice/E008TransactionDetailPALStoPortalApp/E008TransactionDetailPALStoPortal/E008TransactionDetailPALStoPortalClient"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E008TransactionDetailPALStoPortalApp/E008TransactionDetailPALStoPortal/SelectPALSDB2DatabaseAdapterV1"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl inp1 plt wsdl top ns0 tns xsd ns1 ns2 aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:param name="ReceiveInvocationMsgIV.invocationPayload"/>
  <xsl:template match="/">
    <ns2:EngageSoaTxndtlErrorlogCollection>
      <xsl:for-each select="/top:Cc078Vs1CssolnjDtaCollection/top:Cc078Vs1CssolnjDta">
        <xsl:if test='(string-length(normalize-space(top:endretSgn)) > 6.0) or (((string-length(normalize-space(top:salgstransId)) > 10.0) or ((((normalize-space(top:salgstransId) = "") or (normalize-space(top:salgstranslnjLnr) = "")) or (normalize-space(top:endretDt) = "")) or (normalize-space(top:endretSgn) = ""))) or (string-length(normalize-space(top:salgstranslnjLnr)) > 5.0))'>
          <ns2:EngageSoaTxndtlErrorlog>
            <ns2:deltaTs>
              <xsl:value-of select="top:deltaTs"/>
            </ns2:deltaTs>
            <ns2:urefTransactionId>
              <xsl:value-of select="top:salgstransId"/>
            </ns2:urefTransactionId>
            <ns2:detailNo>
              <xsl:value-of select="top:salgstranslnjLnr"/>
            </ns2:detailNo>
            <ns2:orderId>
              <xsl:value-of select="top:ordreId"/>
            </ns2:orderId>
            <ns2:prelimId>
              <xsl:value-of select="top:prelimId"/>
            </ns2:prelimId>
            <ns2:orderLineNumber>
              <xsl:value-of select="top:ordrelinjeLnr"/>
            </ns2:orderLineNumber>
            <ns2:priceElementCode>
              <xsl:value-of select="top:prpslKd"/>
            </ns2:priceElementCode>
            <ns2:priceElementRate>
              <xsl:value-of select="top:prpslsats"/>
            </ns2:priceElementRate>
            <ns2:orderLineRef>
              <xsl:value-of select="top:tilordrelinjeLnr"/>
            </ns2:orderLineRef>
            <ns2:palsPGroupId>
              <xsl:value-of select="top:stasvaregrpKd"/>
            </ns2:palsPGroupId>
            <ns2:palsPGroupSubgroupId>
              <xsl:value-of select="top:kvalitet"/>
            </ns2:palsPGroupSubgroupId>
            <ns2:fuelTransFlag>
              <xsl:value-of select="top:drivstoffJn"/>
            </ns2:fuelTransFlag>
            <ns2:productName>
              <xsl:value-of select="top:lokproduktNv"/>
            </ns2:productName>
            <ns2:unitOfMeasure>
              <xsl:value-of select="top:olkvantitetTp"/>
            </ns2:unitOfMeasure>
            <ns2:quantity>
              <xsl:value-of select="top:olkvantitet"/>
            </ns2:quantity>
            <ns2:vatRate>
              <xsl:value-of select="top:olmvasats"/>
            </ns2:vatRate>
            <ns2:currencyUnitPrice>
              <xsl:value-of select="top:olopgbrtenhpr"/>
            </ns2:currencyUnitPrice>
            <ns2:currencyGrossAmount>
              <xsl:value-of select="top:olopgbrtBel"/>
            </ns2:currencyGrossAmount>
            <ns2:invoicedNetUnitPrcRebated>
              <xsl:value-of select="top:olbernetenhpr"/>
            </ns2:invoicedNetUnitPrcRebated>
            <ns2:invoicedUnitPriceRebated>
              <xsl:value-of select="top:olberbrtenhpr"/>
            </ns2:invoicedUnitPriceRebated>
            <ns2:invoicedNetAmountRabeted>
              <xsl:value-of select="top:olbernetBel"/>
            </ns2:invoicedNetAmountRabeted>
            <ns2:invoicedGrossAmountRebated>
              <xsl:value-of select="top:olberbrtBel"/>
            </ns2:invoicedGrossAmountRebated>
            <ns2:invoivedVatRebated>
              <xsl:value-of select="top:olbermvaBel"/>
            </ns2:invoivedVatRebated>
            <ns2:palsModifiedDate>
              <xsl:value-of select="top:endretDt"/>
            </ns2:palsModifiedDate>
            <ns2:customerServiceId>
              <xsl:value-of select="top:endretSgn"/>
            </ns2:customerServiceId>
            <ns2:emailFlag>
              <xsl:text disable-output-escaping="no">0</xsl:text>
            </ns2:emailFlag>
            <ns2:soaErrorCode>
              <xsl:text disable-output-escaping="no">BE-014</xsl:text>
            </ns2:soaErrorCode>
            <ns2:soaErrorDesc>
              <xsl:text disable-output-escaping="no">Missing mandatory field or Size of field exceeds the expected length in fetched data from source table. Unable to process the record.</xsl:text>
            </ns2:soaErrorDesc>
            <ns2:deltaReciptFlag>
              <xsl:text disable-output-escaping="no">0</xsl:text>
            </ns2:deltaReciptFlag>
            <ns2:modifiedBy>
              <xsl:value-of select="$ReceiveInvocationMsgIV.invocationPayload/inp1:invocationMsg/inp1:UserName"/>
            </ns2:modifiedBy>
            <ns2:modifiedDatetime>
              <xsl:value-of select="xp20:current-dateTime()"/>
            </ns2:modifiedDatetime>
            <ns2:countryCode>
              <xsl:value-of select="$ReceiveInvocationMsgIV.invocationPayload/inp1:invocationMsg/inp1:CountryCode"/>
            </ns2:countryCode>
          </ns2:EngageSoaTxndtlErrorlog>
        </xsl:if>
      </xsl:for-each>
    </ns2:EngageSoaTxndtlErrorlogCollection>
  </xsl:template>
</xsl:stylesheet>

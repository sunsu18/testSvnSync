<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../E007PalsTransactionHeadertoPortalService.wsdl"/>
      <rootElement name="invocationMsg" namespace="http://www.statoilfuelretail.com/integration/E007/E007InvocationReqMsg"/>
    </source>
    <source type="WSDL">
      <schema location="../SelectPALSTxnHeaderRecordsDBAdapterV1.wsdl"/>
      <rootElement name="Cc077Vs1CssordDtaCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectPALSTxnHeaderRecordsDBAdapterV1"/>
      <param name="InvokeSelectPALSTxnRecordDBAdapterV1_OV.Cc077Vs1CssordDtaCollection" />
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../UpdateTxnHeaderPortalDBAdapterV1.wsdl"/>
      <rootElement name="PrtCardTransactionHeaderCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/UpdateTxnHeaderPortalDBAdapterV1"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [THU JUN 12 14:29:24 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:tns="http://oracle.com/sca/soapservice/E007PALSTransactionHeadertoPortalApp/E007PALSTransactionHeadertoPortal/E007PalsTransactionHeadertoPortalService"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:ns3="http://xmlns.oracle.com/pcbpel/adapter/db/top/UpdateTxnHeaderPortalDBAdapterV1"
                xmlns:ns1="http://www.statoilfuelretail.com/integration/E007/TxnHeaderValidate"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectPALSTxnHeaderRecordsDBAdapterV1"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E007PALSTransactionHeadertoPortalApp/E007PALSTransactionHeadertoPortal/SelectPALSTxnHeaderRecordsDBAdapterV1"
                xmlns:inp1="http://www.statoilfuelretail.com/integration/E007/E007InvocationReqMsg"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:ns2="http://xmlns.oracle.com/pcbpel/adapter/db/E007PALSTransactionHeadertoPortalApp/E007PALSTransactionHeadertoPortal/UpdateTxnHeaderPortalDBAdapterV1"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl tns plt wsdl ns1 top ns0 inp1 xsd ns3 ns2 aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:param name="InvokeSelectPALSTxnRecordDBAdapterV1_OV.Cc077Vs1CssordDtaCollection"/>
  <xsl:template match="/">
    <ns3:PrtCardTransactionHeaderCollection>
      <xsl:for-each select="$InvokeSelectPALSTxnRecordDBAdapterV1_OV.Cc077Vs1CssordDtaCollection/top:Cc077Vs1CssordDtaCollection/top:Cc077Vs1CssordDta">
        <xsl:variable name="lastOdometerReading"
                      select="oraext:query-database(concat(&quot;select ODOMETER from PRT_CARD_TRANSACTION_HEADER where KSID = '&quot;,top:leveringksId,&quot;' and PALS_COUNTRY_CODE = '&quot;,/inp1:invocationMsg/inp1:CountryCode,&quot;' and TRANSACTION_TIME = ( select max(TRANSACTION_TIME) from PRT_CARD_TRANSACTION_HEADER where TRANSACTION_TIME &lt; '&quot;,xp20:format-dateTime(concat(top:ordrelevDt,&quot;T&quot;,top:ordrelevTid),&quot;[D01]-[MN,*-3]-[Y0001] [h01]:[m01]:[s01] [PN]&quot;),&quot;' and KSID = '&quot;,top:leveringksId,&quot;'and PALS_COUNTRY_CODE = '&quot;,/inp1:invocationMsg/inp1:CountryCode,&quot;' and PARTNER_ID = '&quot;,top:partnerId,&quot;' and ACCOUNT_ID = '&quot;,top:kontoId,&quot;' )&quot;),false(),false(),&quot;jdbc:oracle:thin:wcpcustom/wcpcustom@10.24.237.66:1521/wcp001t.statoilfuelretail.com&quot;)"/>
        <xsl:variable name="lastPortalOdometerReading"
                      select="oraext:query-database(concat(&quot;select ODOMETER_PORTAL from PRT_CARD_TRANSACTION_HEADER where KSID = '&quot;,top:leveringksId,&quot;' and PALS_COUNTRY_CODE = '&quot;,/inp1:invocationMsg/inp1:CountryCode,&quot;' and TRANSACTION_TIME = ( select max(TRANSACTION_TIME) from PRT_CARD_TRANSACTION_HEADER where TRANSACTION_TIME &lt; '&quot;,xp20:format-dateTime(concat(top:ordrelevDt,&quot;T&quot;,top:ordrelevTid),&quot;[D01]-[MN,*-3]-[Y0001] [h01]:[m01]:[s01] [PN]&quot;),&quot;' and KSID = '&quot;,top:leveringksId,&quot;'and PALS_COUNTRY_CODE = '&quot;,/inp1:invocationMsg/inp1:CountryCode,&quot;' and PARTNER_ID = '&quot;,top:partnerId,&quot;' and ACCOUNT_ID = '&quot;,top:kontoId,&quot;' )&quot;),false(),false(),&quot;jdbc:oracle:thin:wcpcustom/wcpcustom@10.24.237.66:1521/wcp001t.statoilfuelretail.com&quot;)"/>
        <ns3:PrtCardTransactionHeader>
          <ns3:palsCountryCode>
            <xsl:value-of select="/inp1:invocationMsg/inp1:CountryCode"/>
          </ns3:palsCountryCode>
          <!--ns3:palsCountryCode>
            <xsl:value-of select="$test"/>
          </ns3:palsCountryCode-->
          <ns3:urefTransactionId>
            <xsl:value-of select="top:salgstransId"/>
          </ns3:urefTransactionId>
          <xsl:choose>
            <xsl:when test='(normalize-space($lastOdometerReading) != "") and (normalize-space($lastPortalOdometerReading) != "")'>
              <ns3:previousOdometer>
                <xsl:value-of select="$lastPortalOdometerReading"/>
              </ns3:previousOdometer>
            </xsl:when>
            <xsl:when test='(normalize-space($lastOdometerReading) != "") and (normalize-space($lastPortalOdometerReading) = "")'>
              <ns3:previousOdometer>
                <xsl:value-of select="$lastOdometerReading"/>
              </ns3:previousOdometer>
            </xsl:when>
            <xsl:when test='(normalize-space($lastOdometerReading) = "") and (normalize-space($lastPortalOdometerReading) != "")'>
              <ns3:previousOdometer>
                <xsl:value-of select="$lastPortalOdometerReading"/>
              </ns3:previousOdometer>
            </xsl:when>
            <xsl:otherwise>
              <ns3:previousOdometer>
                <xsl:text disable-output-escaping="no">0</xsl:text>
              </ns3:previousOdometer>
            </xsl:otherwise>
          </xsl:choose>
        </ns3:PrtCardTransactionHeader>
      </xsl:for-each>
    </ns3:PrtCardTransactionHeaderCollection>
  </xsl:template>
</xsl:stylesheet>

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
      <schema location="../MergeTxnHeaderPortalDBAdapterV1.wsdl"/>
      <rootElement name="PrtCardTransactionHeaderCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeTxnHeaderPortalDBAdapterV1"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [TUE AUG 12 20:38:28 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:tns="http://oracle.com/sca/soapservice/E007PALSTransactionHeadertoPortalApp/E007PALSTransactionHeadertoPortal/E007PalsTransactionHeadertoPortalService"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:ns2="http://xmlns.oracle.com/pcbpel/adapter/db/E007PALSTransactionHeadertoPortalApp/E007PALSTransactionHeadertoPortal/MergeTxnHeaderPortalDBAdapterV1"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
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
                xmlns:ns3="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeTxnHeaderPortalDBAdapterV1"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl tns plt wsdl ns1 top ns0 inp1 xsd ns2 ns3 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:param name="InvokeSelectPALSTxnRecordDBAdapterV1_OV.Cc077Vs1CssordDtaCollection"/>
  <xsl:template match="/">
    <ns3:PrtCardTransactionHeaderCollection>
      <xsl:for-each select="$InvokeSelectPALSTxnRecordDBAdapterV1_OV.Cc077Vs1CssordDtaCollection/top:Cc077Vs1CssordDtaCollection/top:Cc077Vs1CssordDta">
        <xsl:if test='(normalize-space(top:salgstransId) != "") and ((string-length(normalize-space(top:salgstransId)) &lt;= 10.0) and (normalize-space(top:endretDt) != ""))'>
          <xsl:if test="not((top:slgtransstatusKd = 'PRE') and (top:prelimstatusKd = 'EXP'))">
            <ns3:PrtCardTransactionHeader>
              <ns3:palsCountryCode>
                <xsl:value-of select="/inp1:invocationMsg/inp1:CountryCode"/>
              </ns3:palsCountryCode>
              <ns3:urefTransactionId>
                <xsl:value-of select="top:salgstransId"/>
              </ns3:urefTransactionId>
              <ns3:purchaseCountryCode>
                <xsl:value-of select="top:landKd"/>
              </ns3:purchaseCountryCode>
              <ns3:orderId>
                <xsl:value-of select="top:ordreId"/>
              </ns3:orderId>
              <ns3:prelimId>
                <xsl:value-of select="top:prelimId"/>
              </ns3:prelimId>
              <ns3:card1_Id>
                <xsl:value-of select="top:eksternId"/>
              </ns3:card1_Id>
              <!--xsl:choose>
              <xsl:when test="top:ekstrainputKd = 'V2'">
                <ns3:card2_Id>
                  <xsl:value-of select="top:fakekspksId"/>
                </ns3:card2_Id>
              </xsl:when>
              <xsl:otherwise>
                <ns3:card2_Id>
                  <xsl:value-of select="top:ekstrainputTx"/>
                </ns3:card2_Id>
              </xsl:otherwise>
            </xsl:choose-->
              <ns3:card2_Id>
                <xsl:value-of select="top:ekstrainputTx"/>
              </ns3:card2_Id>
              <ns3:cardId2_Info>
                <xsl:value-of select="top:ekstrainputKd"/>
              </ns3:cardId2_Info>
              <ns3:odometer>
                <xsl:value-of select="top:kmstand"/>
              </ns3:odometer>
              <ns3:transactionDt>
                <xsl:value-of select="top:ordrelevDt"/>
              </ns3:transactionDt>
              <ns3:transactionTime>
                <xsl:value-of select='concat(top:ordrelevDt,"T",substring-before(top:ordrelevTid,"+"))'/>
              </ns3:transactionTime>
              <ns3:stationName>
                <xsl:value-of select="top:salgstedNv"/>
              </ns3:stationName>
              <ns3:iccInvoiceNumber>
                <xsl:value-of select="top:rtxmvafakNr"/>
              </ns3:iccInvoiceNumber>
              <ns3:invoiceNumberNonCollective>
                <xsl:value-of select="top:fakturaNr"/>
              </ns3:invoiceNumberNonCollective>
              <ns3:invoicingDate>
                <xsl:value-of select="top:fakturaDt"/>
              </ns3:invoicingDate>
              <ns3:purchaseCurrency>
                <xsl:value-of select="top:valutaKd"/>
              </ns3:purchaseCurrency>
              <ns3:exchangeRate>
                <xsl:value-of select="top:valutakurs"/>
              </ns3:exchangeRate>
              <ns3:iccYn>
                <xsl:value-of select="top:iccordreJn"/>
              </ns3:iccYn>
              <ns3:transactionType>
                <xsl:value-of select="top:slgtransstatusKd"/>
              </ns3:transactionType>
              <ns3:prelimStatusCode>
                <xsl:value-of select="top:prelimstatusKd"/>
              </ns3:prelimStatusCode>
              <ns3:invoiceNumberCollective>
                <xsl:value-of select="top:faksmndNr"/>
              </ns3:invoiceNumberCollective>
              <ns3:ksid>
                <xsl:value-of select="top:leveringksId"/>
              </ns3:ksid>
              <ns3:partnerId>
                <xsl:value-of select="top:partnerId"/>
              </ns3:partnerId>
              <ns3:accountId>
                <xsl:value-of select="top:kontoId"/>
              </ns3:accountId>
              <ns3:cardgroupMainType>
                <xsl:value-of select="top:fForholdHtp"/>
              </ns3:cardgroupMainType>
              <ns3:cardgroupSubType>
                <xsl:value-of select="top:fForholdUtp"/>
              </ns3:cardgroupSubType>
              <ns3:cardgroupSeq>
                <xsl:value-of select="top:fForholdLnr"/>
              </ns3:cardgroupSeq>
              <ns3:palsOrderMainType>
                <xsl:value-of select="top:ordreHtp"/>
              </ns3:palsOrderMainType>
              <ns3:palsOrderSubType>
                <xsl:value-of select="top:ordreUtp"/>
              </ns3:palsOrderSubType>
              <ns3:orderBackwardRef>
                <xsl:value-of select="top:ordrefraId"/>
              </ns3:orderBackwardRef>
              <ns3:orderForwardRef>
                <xsl:value-of select="top:ordretilId"/>
              </ns3:orderForwardRef>
              <ns3:orderValidCode>
                <xsl:value-of select="top:ordregylKd"/>
              </ns3:orderValidCode>
              <ns3:orderCreateCode>
                <xsl:value-of select="top:ordopprettKd"/>
              </ns3:orderCreateCode>
              <ns3:siteNumber>
                <xsl:value-of select="top:utstyrNr"/>
              </ns3:siteNumber>
              <ns3:invoicedCardId>
                <xsl:value-of select="top:fakekspksId"/>
              </ns3:invoicedCardId>
              <ns3:terminalId>
                <xsl:value-of select="top:terminalId"/>
              </ns3:terminalId>
              <ns3:terminalSeq>
                <xsl:value-of select="top:terminalLnr"/>
              </ns3:terminalSeq>
              <ns3:invoiceFlag>
                <xsl:value-of select="top:ordvispafakJn"/>
              </ns3:invoiceFlag>
              <ns3:wmTransRefNumber>
                <xsl:value-of select="top:stasurefNr"/>
              </ns3:wmTransRefNumber>
              <ns3:palsModifiedDate>
                <xsl:value-of select="top:endretDt"/>
              </ns3:palsModifiedDate>
              <ns3:palsModifiedBy>
                <xsl:value-of select="top:endretSgn"/>
              </ns3:palsModifiedBy>
              <ns3:soaModifiedDate>
                <xsl:value-of select="xp20:current-dateTime()"/>
              </ns3:soaModifiedDate>
              <ns3:soaModifiedBy>
                <xsl:value-of select="/inp1:invocationMsg/inp1:UserName"/>
              </ns3:soaModifiedBy>
            </ns3:PrtCardTransactionHeader>
          </xsl:if>
        </xsl:if>
      </xsl:for-each>
    </ns3:PrtCardTransactionHeaderCollection>
  </xsl:template>
</xsl:stylesheet>

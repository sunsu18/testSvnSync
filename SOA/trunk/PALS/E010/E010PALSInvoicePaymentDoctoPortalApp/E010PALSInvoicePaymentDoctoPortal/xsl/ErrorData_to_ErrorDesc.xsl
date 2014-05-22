<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../SelectErrorRecordsSOADBAdapterV1.wsdl"/>
      <rootElement name="SelectErrorRecordsSOADBAdapterV1OutputCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/SelectErrorRecordsSOADBAdapterV1"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../InsertInvoicePaymentSOAErrorLogDBAdapterV1.wsdl"/>
      <rootElement name="EngageSoaInvPmtErrorlogCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/InsertInvoicePaymentSOAErrorLogDBAdapterV1"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [THU MAY 22 19:34:01 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/SelectErrorRecordsSOADBAdapterV1"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/InsertInvoicePaymentSOAErrorLogDBAdapterV1"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/InsertInvoicePaymentSOAErrorLogDBAdapterV1"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/SelectErrorRecordsSOADBAdapterV1"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl plt wsdl db xsd tns top ns0 aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:template match="/">
    <top:EngageSoaInvPmtErrorlogCollection>
      <xsl:for-each select="/db:SelectErrorRecordsSOADBAdapterV1OutputCollection/db:SelectErrorRecordsSOADBAdapterV1Output">
        <top:EngageSoaInvPmtErrorlog>
          <top:errorDesc>
            <xsl:value-of select='concat(db:ERROR_DESC," For the Partner ID:",db:PARTNER_ID,", Invoice document Number: ",db:INVOICE_DOC_NUMBER,", Delta Time Stamp: ",db:DELTA_TS,"&lt;br/>")'/>
          </top:errorDesc>
        </top:EngageSoaInvPmtErrorlog>
      </xsl:for-each>
    </top:EngageSoaInvPmtErrorlogCollection>
  </xsl:template>
</xsl:stylesheet>

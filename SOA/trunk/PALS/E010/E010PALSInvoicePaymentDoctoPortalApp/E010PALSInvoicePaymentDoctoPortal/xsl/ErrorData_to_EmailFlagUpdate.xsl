<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../../../../../PALS%20Interfaces/E010/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/SelectErrorRecordsSOADBAdapterV1.wsdl"/>
      <rootElement name="SelectErrorRecordsSOADBAdapterV1OutputCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/SelectErrorRecordsSOADBAdapterV1"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../../../../../PALS%20Interfaces/E010/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/UpdateEmailFlagSOAErrorLogDBAdapterV1.wsdl"/>
      <rootElement name="EngageSoaInvPmtErrorlogCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/UpdateEmailFlagSOAErrorLogDBAdapterV1"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [THU MAY 22 19:55:11 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/UpdateEmailFlagSOAErrorLogDBAdapterV1"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/SelectErrorRecordsSOADBAdapterV1"
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
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/SelectErrorRecordsSOADBAdapterV1"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/UpdateEmailFlagSOAErrorLogDBAdapterV1"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl plt wsdl db xsd tns top ns0 aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:template match="/">
    <top:EngageSoaInvPmtErrorlogCollection>
      <xsl:for-each select="/db:SelectErrorRecordsSOADBAdapterV1OutputCollection/db:SelectErrorRecordsSOADBAdapterV1Output">
        <top:EngageSoaInvPmtErrorlog>
          <top:deltaTs>
            <xsl:value-of select="db:DELTA_TS"/>
          </top:deltaTs>
          <top:countryCode>
            <xsl:value-of select="db:COUNTRY_CODE"/>
          </top:countryCode>
          <top:emailFlag>
            <xsl:text disable-output-escaping="no">1</xsl:text>
          </top:emailFlag>
        </top:EngageSoaInvPmtErrorlog>
      </xsl:for-each>
    </top:EngageSoaInvPmtErrorlogCollection>
  </xsl:template>
</xsl:stylesheet>

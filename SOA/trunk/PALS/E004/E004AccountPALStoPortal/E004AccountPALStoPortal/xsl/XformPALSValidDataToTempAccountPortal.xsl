<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../E004AccountPALStoPortal.wsdl"/>
      <rootElement name="invocationMsg" namespace="http://www.statoilfuelretail.com/integration/engage/E004AccountPALStoPortal/E004AccountPALStoPortal/E004AccountPALStoPortal"/>
    </source>
    <source type="WSDL">
      <schema location="../PALSDB2DBAdapter.wsdl"/>
      <rootElement name="Cc071Vs1CssktoDtaCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/PALSDB2DBAdapter"/>
      <param name="InvokePALSDB2DBAdapter_PALSDB2DBAdapterSelect_OutputVariable.Cc071Vs1CssktoDtaCollection" />
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../InsertPALSDataToTempAccountPortalDBAdapter.wsdl"/>
      <rootElement name="SoaAccountTempCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/InsertPALSDataToTempAccountPortalDBAdapter"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [THU OCT 30 15:45:46 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E004AccountPALStoPortal/E004AccountPALStoPortal/InsertPALSDataToTempAccountPortalDBAdapter"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/PALSDB2DBAdapter"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:ns1="http://www.statoilfuelretail.com/integration/engage"
                xmlns:plnk="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:client="http://www.statoilfuelretail.com/integration/engage/E004AccountPALStoPortal/E004AccountPALStoPortal/E004AccountPALStoPortal"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:ns2="http://xmlns.oracle.com/pcbpel/adapter/db/top/InsertPALSDataToTempAccountPortalDBAdapter"
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
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E004AccountPALStoPortal/E004AccountPALStoPortal/PALSDB2DBAdapter"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:ns3="http://www.statoilfuelretail.com/integration/engage/ErrorHandling.xsd"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl top ns1 plnk client wsdl xsd tns ns3 ns0 ns2 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:param name="InvokePALSDB2DBAdapter_PALSDB2DBAdapterSelect_OutputVariable.Cc071Vs1CssktoDtaCollection"/>
  <xsl:template match="/">
    <ns2:SoaAccountTempCollection>
      <xsl:for-each select="$InvokePALSDB2DBAdapter_PALSDB2DBAdapterSelect_OutputVariable.Cc071Vs1CssktoDtaCollection/top:Cc071Vs1CssktoDtaCollection/top:Cc071Vs1CssktoDta">
        <!--        <xsl:if test='(string-length(top:partnerId) = 8.0) and ((string-length(top:kontoId) = 10.0) and ((string-length(top:kundeGrp) = 2.0) and ((top:kontoId != "") and ((top:partnerId != "") and ((top:deltaTs != "") and (top:kundeGrp != ""))))))'>
-->
        <xsl:if test='(string-length(top:partnerId) &lt;= 8.0) and ((string-length(top:kontoId) &lt;= 10.0) and ((normalize-space(top:kontoId) != "") and ((normalize-space(top:partnerId) != "") and (normalize-space(top:deltaTs) != ""))))'>
          <ns2:SoaAccountTemp>
            <ns2:countryCode>
              <xsl:value-of select="/client:invocationMsg/client:CountryCode"/>
            </ns2:countryCode>
            <ns2:deltaTs>
              <xsl:value-of select="top:deltaTs"/>
            </ns2:deltaTs>
            <ns2:accountId>
              <xsl:value-of select="top:kontoId"/>
            </ns2:accountId>
            <ns2:partnerId>
              <xsl:value-of select="top:partnerId"/>
            </ns2:partnerId>
            <ns2:modifiedBy>
              <xsl:value-of select="/client:invocationMsg/client:UserName"/>
            </ns2:modifiedBy>
            <ns2:modifiedDate>
              <xsl:value-of select="xp20:current-dateTime()"/>
            </ns2:modifiedDate>
            <ns2:accountName>
              <xsl:value-of select="top:kontoBetegn"/>
            </ns2:accountName>
            <ns2:customerSegment>
              <xsl:value-of select="top:csskndsegment"/>
            </ns2:customerSegment>
          </ns2:SoaAccountTemp>
        </xsl:if>
      </xsl:for-each>
    </ns2:SoaAccountTempCollection>
  </xsl:template>
</xsl:stylesheet>

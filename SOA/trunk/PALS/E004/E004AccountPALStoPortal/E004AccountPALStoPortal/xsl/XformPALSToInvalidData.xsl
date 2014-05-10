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
      <schema location="../SOAErrorDBAdapter.wsdl"/>
      <rootElement name="EngagePalsAccDtaErrorlogCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/SOAErrorDBAdapter"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [SAT MAY 10 12:39:58 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E004AccountPALStoPortal/E004AccountPALStoPortal/SOAErrorDBAdapter"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/PALSDB2DBAdapter"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:ns2="http://xmlns.oracle.com/pcbpel/adapter/db/top/SOAErrorDBAdapter"
                xmlns:ns1="http://www.statoilfuelretail.com/integration/engage"
                xmlns:plnk="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:client="http://www.statoilfuelretail.com/integration/engage/E004AccountPALStoPortal/E004AccountPALStoPortal/E004AccountPALStoPortal"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
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
                exclude-result-prefixes="xsi xsl top ns1 plnk client wsdl xsd tns ns3 ns0 ns2 aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:param name="InvokePALSDB2DBAdapter_PALSDB2DBAdapterSelect_OutputVariable.Cc071Vs1CssktoDtaCollection"/>
  <xsl:template match="/">
    <ns2:EngagePalsAccDtaErrorlogCollection>
      <xsl:for-each select="$InvokePALSDB2DBAdapter_PALSDB2DBAdapterSelect_OutputVariable.Cc071Vs1CssktoDtaCollection/top:Cc071Vs1CssktoDtaCollection/top:Cc071Vs1CssktoDta">
        <xsl:if test='(string-length(top:partnerId) != 8.0) or ((string-length(top:kontoId) != 10.0) or ((string-length(top:kundeGrp) != 2.0) or ((top:kontoId = "") or ((top:partnerId = "") or ((top:deltaTs = "") or (top:kundeGrp = ""))))))'>
          <ns2:EngagePalsAccDtaErrorlog>
            <ns2:deltaTs>
              <xsl:value-of select="top:deltaTs"/>
            </ns2:deltaTs>
            <ns2:accountId>
              <xsl:value-of select="top:kontoId"/>
            </ns2:accountId>
            <ns2:partnerId>
              <xsl:value-of select="top:partnerId"/>
            </ns2:partnerId>
            <ns2:accountCustomerGroup>
              <xsl:value-of select="top:kundeGrp"/>
            </ns2:accountCustomerGroup>
            <ns2:soaErrorCode>
              <xsl:text disable-output-escaping="no">BE-014</xsl:text>
            </ns2:soaErrorCode>
            <ns2:soaErrorDesc>
              <xsl:text disable-output-escaping="no">'Missing Mandatory field or size of field exceeds the expected length in fetched data from sourcetable. Unable to process the record.'</xsl:text>
            </ns2:soaErrorDesc>
            <ns2:updatedBy>
              <xsl:value-of select="/client:invocationMsg/client:UserName"/>
            </ns2:updatedBy>
            <ns2:updatedTimestamp>
              <xsl:value-of select="xp20:current-dateTime()"/>
            </ns2:updatedTimestamp>
            <ns2:countryCode>
              <xsl:value-of select="/client:invocationMsg/client:CountryCode"/>
            </ns2:countryCode>
          </ns2:EngagePalsAccDtaErrorlog>
        </xsl:if>
      </xsl:for-each>
    </ns2:EngagePalsAccDtaErrorlogCollection>
  </xsl:template>
</xsl:stylesheet>

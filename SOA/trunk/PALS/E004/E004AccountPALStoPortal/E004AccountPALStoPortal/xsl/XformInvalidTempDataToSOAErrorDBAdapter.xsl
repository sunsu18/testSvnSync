<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../SelectInValidDataTempAccountPortalDBAdapter.wsdl"/>
      <rootElement name="SelectInValidDataTempAccountPortalDBAdapterOutputCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/SelectInValidDataTempAccountPortalDBAdapter"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../SOAErrorDBAdapter.wsdl"/>
      <rootElement name="EngagePalsAccDtaErrorlogCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/SOAErrorDBAdapter"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [SAT MAY 10 12:42:43 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E004AccountPALStoPortal/E004AccountPALStoPortal/SOAErrorDBAdapter"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/SOAErrorDBAdapter"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/SelectInValidDataTempAccountPortalDBAdapter"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E004AccountPALStoPortal/E004AccountPALStoPortal/SelectInValidDataTempAccountPortalDBAdapter"
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
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl plt wsdl db tns xsd ns0 top aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:template match="/">
    <top:EngagePalsAccDtaErrorlogCollection>
      <xsl:for-each select="/db:SelectInValidDataTempAccountPortalDBAdapterOutputCollection/db:SelectInValidDataTempAccountPortalDBAdapterOutput">
        <top:EngagePalsAccDtaErrorlog>
          <top:deltaTs>
            <xsl:value-of select="db:DELTA_TS"/>
          </top:deltaTs>
          <top:accountId>
            <xsl:if test="db:ACCOUNT_ID/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:ACCOUNT_ID/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:ACCOUNT_ID"/>
          </top:accountId>
          <top:partnerId>
            <xsl:if test="db:PARTNER_ID/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:PARTNER_ID/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:PARTNER_ID"/>
          </top:partnerId>
          <top:accountCustomerGroup>
            <xsl:if test="db:CUSTOMER_GROUP/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:CUSTOMER_GROUP/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:CUSTOMER_GROUP"/>
          </top:accountCustomerGroup>
          <top:soaErrorCode>
            <xsl:text disable-output-escaping="no">'BE-026'</xsl:text>
          </top:soaErrorCode>
          <top:soaErrorDesc>
            <xsl:text disable-output-escaping="no">'Integrity Constraint Violation'</xsl:text>
          </top:soaErrorDesc>
          <top:updatedBy>
            <xsl:if test="db:MODIFIED_BY/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:MODIFIED_BY/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:MODIFIED_BY"/>
          </top:updatedBy>
          <top:updatedTimestamp>
            <xsl:if test="db:MODIFIED_DATE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:MODIFIED_DATE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:MODIFIED_DATE"/>
          </top:updatedTimestamp>
          <top:emailFlag>
            <xsl:text disable-output-escaping="no">0</xsl:text>
          </top:emailFlag>
          <top:deltaReciptFlag>
            <xsl:text disable-output-escaping="no">0</xsl:text>
          </top:deltaReciptFlag>
          <top:countryCode>
            <xsl:if test="db:COUNTRY_CODE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:COUNTRY_CODE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:COUNTRY_CODE"/>
          </top:countryCode>
        </top:EngagePalsAccDtaErrorlog>
      </xsl:for-each>
    </top:EngagePalsAccDtaErrorlogCollection>
  </xsl:template>
</xsl:stylesheet>
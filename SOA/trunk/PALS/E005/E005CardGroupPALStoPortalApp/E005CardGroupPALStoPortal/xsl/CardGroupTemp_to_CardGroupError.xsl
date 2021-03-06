<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../SelectSOACardGroupTempDatabaseAdapterV1.wsdl"/>
      <rootElement name="SelectSOACardGroupTempDatabaseAdapterV1OutputCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/SelectSOACardGroupTempDatabaseAdapterV1"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../MergeCardGroupErrorLogDatabaseAdapterV1.wsdl"/>
      <rootElement name="EngageSoaCardgroupErrorlogCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeCardGroupErrorLogDatabaseAdapterV1"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [THU OCT 30 19:46:18 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E005CardGroupPALStoPortalApp/E005CardGroupPALStoPortal/SelectSOACardGroupTempDatabaseAdapterV1"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeCardGroupErrorLogDatabaseAdapterV1"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/SelectSOACardGroupTempDatabaseAdapterV1"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E005CardGroupPALStoPortalApp/E005CardGroupPALStoPortal/MergeCardGroupErrorLogDatabaseAdapterV1"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl tns plt wsdl db xsd top ns0 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:template match="/">
    <top:EngageSoaCardgroupErrorlogCollection>
      <xsl:for-each select="/db:SelectSOACardGroupTempDatabaseAdapterV1OutputCollection/db:SelectSOACardGroupTempDatabaseAdapterV1Output">
        <top:EngageSoaCardgroupErrorlog>
          <top:deltaTs>
            <xsl:value-of select="db:DELTA_TS"/>
          </top:deltaTs>
          <top:partnerId>
            <xsl:if test="db:PARTNER_ID/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:PARTNER_ID/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:PARTNER_ID"/>
          </top:partnerId>
          <top:fForholdHtp>
            <xsl:if test="db:CARDGROUP_MAIN_TYPE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:CARDGROUP_MAIN_TYPE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:CARDGROUP_MAIN_TYPE"/>
          </top:fForholdHtp>
          <top:fForholdUtp>
            <xsl:if test="db:CARDGROUP_SUB_TYPE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:CARDGROUP_SUB_TYPE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:CARDGROUP_SUB_TYPE"/>
          </top:fForholdUtp>
          <top:fForholdLnr>
            <xsl:if test="db:CARDGROUP_SEQ/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:CARDGROUP_SEQ/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:CARDGROUP_SEQ"/>
          </top:fForholdLnr>
          <top:kontoId>
            <xsl:if test="db:ACCOUNT_ID/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:ACCOUNT_ID/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:ACCOUNT_ID"/>
          </top:kontoId>
          <top:fForholdTx>
            <xsl:if test="db:CARDGROUP_DESCRIPTION/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:CARDGROUP_DESCRIPTION/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:CARDGROUP_DESCRIPTION"/>
          </top:fForholdTx>
          <top:emailflag>
            <xsl:text disable-output-escaping="no">0</xsl:text>
          </top:emailflag>
          <top:soaErrorCode>
            <xsl:text disable-output-escaping="no">BE-025</xsl:text>
          </top:soaErrorCode>
          <top:soaErrorDesc>
            <xsl:text disable-output-escaping="no">Foreign Key Constraint Violated</xsl:text>
          </top:soaErrorDesc>
          <top:deltaReciptFlag>
            <xsl:text disable-output-escaping="no">0</xsl:text>
          </top:deltaReciptFlag>
          <top:modifiedBy>
            <xsl:if test="db:MODIFIED_BY/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:MODIFIED_BY/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:MODIFIED_BY"/>
          </top:modifiedBy>
          <top:modifiedDatetime>
            <xsl:if test="db:MODIFIED_DATE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:MODIFIED_DATE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:MODIFIED_DATE"/>
          </top:modifiedDatetime>
          <top:countryCode>
            <xsl:value-of select="db:COUNTRY_CODE"/>
          </top:countryCode>
          <top:routexCustomerNumber>
            <xsl:if test="db:ROUTEX_CUSTOMER_NUMBER/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:ROUTEX_CUSTOMER_NUMBER/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:ROUTEX_CUSTOMER_NUMBER"/>
          </top:routexCustomerNumber>
        </top:EngageSoaCardgroupErrorlog>
      </xsl:for-each>
    </top:EngageSoaCardgroupErrorlogCollection>
  </xsl:template>
</xsl:stylesheet>

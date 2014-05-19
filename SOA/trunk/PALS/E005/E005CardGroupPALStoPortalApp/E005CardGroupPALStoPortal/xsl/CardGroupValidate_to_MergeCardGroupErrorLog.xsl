<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../MergeCardGroupPortalDatabaseAdaptorV1.wsdl"/>
      <rootElement name="CardGroup" namespace="http://www.statoilfuelretail.com/integration/E005/MergeCardGroupValidate"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../MergeCardGroupErrorLogDatabaseAdapterV1.wsdl"/>
      <rootElement name="EngageSoaCardgroupErrorlogCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeCardGroupErrorLogDatabaseAdapterV1"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [THU MAY 15 12:32:46 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E005CardGroupPALStoPortalApp/E005CardGroupPALStoPortal/MergeCardGroupPortalDatabaseAdaptorV1"
                xmlns:ns2="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeCardGroupErrorLogDatabaseAdapterV1"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E005CardGroupPALStoPortalApp/E005CardGroupPALStoPortal/MergeCardGroupErrorLogDatabaseAdapterV1"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:ns1="http://www.statoilfuelretail.com/integration/E005/MergeCardGroupValidate"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeCardGroupPortalDatabaseAdaptorV1"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl tns plt wsdl xsd ns1 top ns2 ns0 aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:template match="/">
    <ns2:EngageSoaCardgroupErrorlogCollection>
      <xsl:for-each select="/ns1:CardGroup/ns1:CardGroupData">
        <xsl:if test='((((((((((normalize-space(ns1:ACCOUNT_ID) = "") or (string-length(normalize-space(ns1:ACCOUNT_ID)) > 10.0)) or (string-length(normalize-space(ns1:PARTNER_ID)) > 8.0)) or (string-length(normalize-space(ns1:CARDGROUP_MAIN_TYPE)) > 3.0)) or (string-length(normalize-space(ns1:CARDGROUP_SUB_TYPE)) > 3.0)) or (string-length(normalize-space(ns1:CARDGROUP_SEQ)) > 5.0)) or (string-length(normalize-space(ns1:CARDGROUP_DESCRIPTION)) > 45.0)) or (normalize-space(ns1:PARTNER_ID) = "")) or (normalize-space(ns1:CARDGROUP_MAIN_TYPE) = "")) or (normalize-space(ns1:CARDGROUP_SUB_TYPE) = "")) or (normalize-space(ns1:CARDGROUP_SEQ) = "")'>
          <ns2:EngageSoaCardgroupErrorlog>
            <ns2:deltaTs>
              <xsl:value-of select="ns1:DELTA_TS"/>
            </ns2:deltaTs>
            <ns2:partnerId>
              <xsl:value-of select="ns1:PARTNER_ID"/>
            </ns2:partnerId>
            <ns2:fForholdHtp>
              <xsl:value-of select="ns1:CARDGROUP_MAIN_TYPE"/>
            </ns2:fForholdHtp>
            <ns2:fForholdUtp>
              <xsl:value-of select="ns1:CARDGROUP_SUB_TYPE"/>
            </ns2:fForholdUtp>
            <ns2:fForholdLnr>
              <xsl:value-of select="ns1:CARDGROUP_SEQ"/>
            </ns2:fForholdLnr>
            <ns2:kontoId>
              <xsl:value-of select="ns1:ACCOUNT_ID"/>
            </ns2:kontoId>
            <ns2:fForholdTx>
              <xsl:value-of select="ns1:CARDGROUP_DESCRIPTION"/>
            </ns2:fForholdTx>
            <ns2:emailflag>
              <xsl:text disable-output-escaping="no">0</xsl:text>
            </ns2:emailflag>
            <ns2:soaErrorCode>
              <xsl:text disable-output-escaping="no">BE-014</xsl:text>
            </ns2:soaErrorCode>
            <ns2:soaErrorDesc>
              <xsl:text disable-output-escaping="no">Missing mandatory field or Size of field exceeds the expected length in fetched data from source table</xsl:text>
            </ns2:soaErrorDesc>
            <ns2:deltaReciptFlag>
              <xsl:text disable-output-escaping="no">0</xsl:text>
            </ns2:deltaReciptFlag>
          </ns2:EngageSoaCardgroupErrorlog>
        </xsl:if>
      </xsl:for-each>
    </ns2:EngageSoaCardgroupErrorlogCollection>
  </xsl:template>
</xsl:stylesheet>

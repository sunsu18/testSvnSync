<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../SelectValidCardGroupTempDatabaseAdapterV1.wsdl"/>
      <rootElement name="SelectValidCardGroupTempDatabaseAdapterV1OutputCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/SelectValidCardGroupTempDatabaseAdapterV1"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../MergeCardGroupPortalDatabaseAdaptorV1.wsdl"/>
      <rootElement name="PrtCardgroupCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeCardGroupPortalDatabaseAdaptorV1"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [THU OCT 30 19:47:48 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E005CardGroupPALStoPortalApp/E005CardGroupPALStoPortal/MergeCardGroupPortalDatabaseAdaptorV1"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E005CardGroupPALStoPortalApp/E005CardGroupPALStoPortal/SelectValidCardGroupTempDatabaseAdapterV1"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:ns1="http://www.statoilfuelretail.com/integration/E005/MergeCardGroupValidate"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/SelectValidCardGroupTempDatabaseAdapterV1"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeCardGroupPortalDatabaseAdaptorV1"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl plt wsdl tns xsd db ns0 top xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:template match="/">
    <top:PrtCardgroupCollection>
      <xsl:for-each select="/db:SelectValidCardGroupTempDatabaseAdapterV1OutputCollection/db:SelectValidCardGroupTempDatabaseAdapterV1Output">
        <top:PrtCardgroup>
          <top:countryCode>
            <xsl:value-of select="db:COUNTRY_CODE"/>
          </top:countryCode>
          <top:partnerId>
            <xsl:value-of select="db:PARTNER_ID"/>
          </top:partnerId>
          <top:accountId>
            <xsl:value-of select="db:ACCOUNT_ID"/>
          </top:accountId>
          <top:cardgroupMainType>
            <xsl:value-of select="db:CARDGROUP_MAIN_TYPE"/>
          </top:cardgroupMainType>
          <top:cardgroupSubType>
            <xsl:value-of select="db:CARDGROUP_SUB_TYPE"/>
          </top:cardgroupSubType>
          <top:cardgroupSeq>
            <xsl:value-of select="db:CARDGROUP_SEQ"/>
          </top:cardgroupSeq>
          <top:cardgroupDescription>
            <xsl:if test="db:CARDGROUP_DESCRIPTION/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:CARDGROUP_DESCRIPTION/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:CARDGROUP_DESCRIPTION"/>
          </top:cardgroupDescription>
          <top:modifiedBy>
            <xsl:value-of select="db:MODIFIED_BY"/>
          </top:modifiedBy>
          <top:modifiedDate>
            <xsl:value-of select="db:MODIFIED_DATE"/>
          </top:modifiedDate>
          <top:routexCustomerNumber>
            <xsl:if test="db:ROUTEX_CUSTOMER_NUMBER/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:ROUTEX_CUSTOMER_NUMBER/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:ROUTEX_CUSTOMER_NUMBER"/>
          </top:routexCustomerNumber>
        </top:PrtCardgroup>
      </xsl:for-each>
    </top:PrtCardgroupCollection>
  </xsl:template>
</xsl:stylesheet>

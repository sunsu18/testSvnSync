<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../SelectValidDataTempAccountPortalDBAdapter.wsdl"/>
      <rootElement name="SelectValidDataTempAccountPortalDBAdapterOutputCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/SelectValidDataTempAccountPortalDBAdapter"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../MergeAccountPortalDBAdapter.wsdl"/>
      <rootElement name="PrtAccountCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeAccountPortalDBAdapter"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [FRI MAY 09 12:46:38 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/SelectValidDataTempAccountPortalDBAdapter"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E004AccountPALStoPortal/E004AccountPALStoPortal/MergeAccountPortalDBAdapter"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E004AccountPALStoPortal/E004AccountPALStoPortal/SelectValidDataTempAccountPortalDBAdapter"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeAccountPortalDBAdapter"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl db plt wsdl tns xsd ns0 top aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:template match="/">
    <top:PrtAccountCollection>
      <xsl:for-each select="/db:SelectValidDataTempAccountPortalDBAdapterOutputCollection/db:SelectValidDataTempAccountPortalDBAdapterOutput">
        <top:PrtAccount>
          <top:countryCode>
            <xsl:value-of select="db:COUNTRY_CODE"/>
          </top:countryCode>
          <top:accountId>
            <xsl:value-of select="db:ACCOUNT_ID"/>
          </top:accountId>
          <top:partnerId>
            <xsl:value-of select="db:PARTNER_ID"/>
          </top:partnerId>
          <top:customerGroup>
            <xsl:value-of select="db:CUSTOMER_GROUP"/>
          </top:customerGroup>
          <top:modifiedBy>
            <xsl:value-of select="db:MODIFIED_BY"/>
          </top:modifiedBy>
          <top:modifiedDate>
            <xsl:value-of select="db:MODIFIED_DATE"/>
          </top:modifiedDate>
        </top:PrtAccount>
      </xsl:for-each>
    </top:PrtAccountCollection>
  </xsl:template>
</xsl:stylesheet>

<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../E003ACcountSummaryPalstoPortalService.wsdl"/>
      <rootElement name="invocationMsg" namespace="http://www.statoilfuelretail.com/integration/E003/E003InvocationReqMsg"/>
    </source>
    <source type="WSDL">
      <schema location="../SelectPALSDB2DBAdapterV1.wsdl"/>
      <rootElement name="Cc070Vs1CssparDtaCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectPALSDB2DBAdapterV1"/>
      <param name="InvokePALSDB2DBAdapter_OV.Cc070Vs1CssparDtaCollection" />
    </source>
  </mapSources>
  <mapTargets>
    <target type="XSD">
      <schema location="../xsd/AccountSummaryPartnerValidate.xsd"/>
      <rootElement name="AccountSummaryPartner" namespace="http://www.statoilfuelretail.com/integration/E003/MergeCardGroupValidate"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [FRI OCT 17 10:31:17 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E003AccountSummaryPALStoPortalApp/E003AccountSummaryPALStoPortal/SelectPALSDB2DBAdapterV1"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectPALSDB2DBAdapterV1"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:inp1="http://www.statoilfuelretail.com/integration/E003/E003InvocationReqMsg"
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
                xmlns:tns="http://oracle.com/sca/soapservice/E003AccountSummaryPALStoPortalApp/E003AccountSummaryPALStoPortal/E003ACcountSummaryPalstoPortalService"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:ns1="http://www.statoilfuelretail.com/integration/E003/MergeCardGroupValidate"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl ns0 top plt inp1 wsdl xsd tns ns1 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:param name="InvokePALSDB2DBAdapter_OV.Cc070Vs1CssparDtaCollection"/>
  <xsl:template match="/">
    <ns1:AccountSummaryPartner>
      <xsl:for-each select="$InvokePALSDB2DBAdapter_OV.Cc070Vs1CssparDtaCollection/top:Cc070Vs1CssparDtaCollection/top:Cc070Vs1CssparDta">
        <ns1:AccountSummaryPartnerData>
          <ns1:COUNTRY_CODE>
            <xsl:value-of select="/inp1:invocationMsg/inp1:CountryCode"/>
          </ns1:COUNTRY_CODE>
          <ns1:Delta_TS>
            <xsl:value-of select="top:deltaTs"/>
          </ns1:Delta_TS>
          <ns1:PARTNER_ID>
            <xsl:value-of select="top:partnerId"/>
          </ns1:PARTNER_ID>
          <ns1:COMPANY_TYPE>
            <xsl:value-of select="top:partnerTp"/>
          </ns1:COMPANY_TYPE>
          <ns1:COMPANY_NAME>
            <xsl:value-of select="top:redigertNv"/>
          </ns1:COMPANY_NAME>
          <ns1:DOB>
            <xsl:value-of select="top:fodselDt"/>
          </ns1:DOB>
          <ns1:PERSON_NUMBER>
            <xsl:value-of select="top:personNr"/>
          </ns1:PERSON_NUMBER>
          <ns1:ORG_ID>
            <xsl:value-of select="top:organisasjonNr"/>
          </ns1:ORG_ID>
          <ns1:GENDER>
            <xsl:value-of select="top:kjonn"/>
          </ns1:GENDER>
          <ns1:VAT_REG_NUMBER>
            <xsl:value-of select="top:mvaregNr"/>
          </ns1:VAT_REG_NUMBER>
          <ns1:PARTNER_ADDR1>
            <xsl:value-of select="top:priadresse1"/>
          </ns1:PARTNER_ADDR1>
          <ns1:PARTNER_ADDR2>
            <xsl:value-of select="top:priadresse2"/>
          </ns1:PARTNER_ADDR2>
          <ns1:PARTNER_POSTAL_CODE>
            <xsl:value-of select="top:priadrpostNr"/>
          </ns1:PARTNER_POSTAL_CODE>
          <ns1:PARTNER_CITY>
            <xsl:value-of select="top:priadrpoststed"/>
          </ns1:PARTNER_CITY>
          <ns1:PARTNER_COUNTRY>
            <xsl:value-of select="top:priadrlandKd"/>
          </ns1:PARTNER_COUNTRY>
          <ns1:PARTNER_ADDR_TYPE>
            <xsl:value-of select="top:priadresseTp"/>
          </ns1:PARTNER_ADDR_TYPE>
        </ns1:AccountSummaryPartnerData>
      </xsl:for-each>
    </ns1:AccountSummaryPartner>
  </xsl:template>
</xsl:stylesheet>

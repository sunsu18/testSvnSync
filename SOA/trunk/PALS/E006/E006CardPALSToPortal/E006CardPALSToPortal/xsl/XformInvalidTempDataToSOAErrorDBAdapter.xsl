<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../SelectInValidDataTempCardPortalDBAdapter.wsdl"/>
      <rootElement name="SelectInValidDataTempCardPortalDBAdapterOutputCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/SelectInValidDataTempCardPortalDBAdapter"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../SOAErrorDBAdapter.wsdl"/>
      <rootElement name="EngagePalsCardDtaErrorlogCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/SOAErrorDBAdapter"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [THU MAY 15 19:17:57 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E006CardPALSToPortal/E006CardPALSToPortal/SelectInValidDataTempCardPortalDBAdapter"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/SOAErrorDBAdapter"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E006CardPALSToPortal/E006CardPALSToPortal/SOAErrorDBAdapter"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:db="http://xmlns.oracle.com/pcbpel/adapter/db/SelectInValidDataTempCardPortalDBAdapter"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl tns plt wsdl db xsd top ns0 aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:template match="/">
    <top:EngagePalsCardDtaErrorlogCollection>
      <xsl:for-each select="/db:SelectInValidDataTempCardPortalDBAdapterOutputCollection/db:SelectInValidDataTempCardPortalDBAdapterOutput">
        <top:EngagePalsCardDtaErrorlog>
          <top:deltaTs>
            <xsl:if test="db:DELTA_TS/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:DELTA_TS/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:DELTA_TS"/>
          </top:deltaTs>
          <top:countryCode>
            <xsl:if test="db:COUNTRY_CODE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:COUNTRY_CODE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:COUNTRY_CODE"/>
          </top:countryCode>
          <top:partnerId>
            <xsl:if test="db:PARTNER_ID/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:PARTNER_ID/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:PARTNER_ID"/>
          </top:partnerId>
          <top:cardgroupMainType>
            <xsl:if test="db:CARDGROUP_MAIN_TYPE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:CARDGROUP_MAIN_TYPE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:CARDGROUP_MAIN_TYPE"/>
          </top:cardgroupMainType>
          <top:cardgroupSubType>
            <xsl:if test="db:CARDGROUP_SUB_TYPE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:CARDGROUP_SUB_TYPE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:CARDGROUP_SUB_TYPE"/>
          </top:cardgroupSubType>
          <top:cardgroupSeq>
            <xsl:if test="db:CARDGROUP_SEQ/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:CARDGROUP_SEQ/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:CARDGROUP_SEQ"/>
          </top:cardgroupSeq>
          <top:prtCardPk>
            <xsl:if test="db:PRT_CARD_PK/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:PRT_CARD_PK/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:PRT_CARD_PK"/>
          </top:prtCardPk>
          <top:cardIdentifier>
            <xsl:if test="db:CARD_IDENTIFIER/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:CARD_IDENTIFIER/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:CARD_IDENTIFIER"/>
          </top:cardIdentifier>
          <top:cardType>
            <xsl:if test="db:CARD_TYPE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:CARD_TYPE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:CARD_TYPE"/>
          </top:cardType>
          <top:magneticCode>
            <xsl:if test="db:MAGNETIC_CODE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:MAGNETIC_CODE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:MAGNETIC_CODE"/>
          </top:magneticCode>
          <top:cardEmbossNum>
            <xsl:if test="db:CARD_EMBOSS_NUM/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:CARD_EMBOSS_NUM/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:CARD_EMBOSS_NUM"/>
          </top:cardEmbossNum>
          <top:invoiceInformation>
            <xsl:if test="db:INVOICE_INFORMATION/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:INVOICE_INFORMATION/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:INVOICE_INFORMATION"/>
          </top:invoiceInformation>
          <top:cardExpiry>
            <xsl:if test="db:CARD_EXPIRY/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:CARD_EXPIRY/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:CARD_EXPIRY"/>
          </top:cardExpiry>
          <top:blockType>
            <xsl:if test="db:BLOCK_TYPE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:BLOCK_TYPE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:BLOCK_TYPE"/>
          </top:blockType>
          <top:blockCode>
            <xsl:if test="db:BLOCK_CODE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:BLOCK_CODE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:BLOCK_CODE"/>
          </top:blockCode>
          <top:blockAction>
            <xsl:if test="db:BLOCK_ACTION/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:BLOCK_ACTION/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:BLOCK_ACTION"/>
          </top:blockAction>
          <top:blockDate>
            <xsl:if test="db:BLOCK_DATE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:BLOCK_DATE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:BLOCK_DATE"/>
          </top:blockDate>
          <top:blockTime>
            <xsl:if test="db:BLOCK_TIME/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:BLOCK_TIME/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:BLOCK_TIME"/>
          </top:blockTime>
          <top:blockLevel>
            <xsl:if test="db:BLOCK_LEVEL/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:BLOCK_LEVEL/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:BLOCK_LEVEL"/>
          </top:blockLevel>
          <top:manufacturedDate>
            <xsl:if test="db:MANUFACTURED_DATE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:MANUFACTURED_DATE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:MANUFACTURED_DATE"/>
          </top:manufacturedDate>
          <top:routexCustomerNumber>
            <xsl:if test="db:ROUTEX_CUSTOMER_NUMBER/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:ROUTEX_CUSTOMER_NUMBER/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:ROUTEX_CUSTOMER_NUMBER"/>
          </top:routexCustomerNumber>
          <top:invoiceAddr1>
            <xsl:if test="db:INVOICE_ADDR1/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:INVOICE_ADDR1/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:INVOICE_ADDR1"/>
          </top:invoiceAddr1>
          <top:invoiceAddr2>
            <xsl:if test="db:INVOICE_ADDR2/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:INVOICE_ADDR2/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:INVOICE_ADDR2"/>
          </top:invoiceAddr2>
          <top:invoicePostalCode>
            <xsl:if test="db:INVOICE_POSTAL_CODE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:INVOICE_POSTAL_CODE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:INVOICE_POSTAL_CODE"/>
          </top:invoicePostalCode>
          <top:invoiceCity>
            <xsl:if test="db:INVOICE_CITY/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:INVOICE_CITY/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:INVOICE_CITY"/>
          </top:invoiceCity>
          <top:addrSeq>
            <xsl:if test="db:ADDR_SEQ/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:ADDR_SEQ/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:ADDR_SEQ"/>
          </top:addrSeq>
          <top:invoiceCountry>
            <xsl:if test="db:INVOICE_COUNTRY/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:INVOICE_COUNTRY/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:INVOICE_COUNTRY"/>
          </top:invoiceCountry>
          <top:invoiceAddrLevel>
            <xsl:if test="db:INVOICE_ADDR_LEVEL/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:INVOICE_ADDR_LEVEL/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:INVOICE_ADDR_LEVEL"/>
          </top:invoiceAddrLevel>
          <top:invoiceAddrType>
            <xsl:if test="db:INVOICE_ADDR_TYPE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:INVOICE_ADDR_TYPE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:INVOICE_ADDR_TYPE"/>
          </top:invoiceAddrType>
          <top:modifiedBy>
            <xsl:if test="db:MODIFIED_BY/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:MODIFIED_BY/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:MODIFIED_BY"/>
          </top:modifiedBy>
          <top:modifiedDate>
            <xsl:if test="db:MODIFIED_DATE/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="db:MODIFIED_DATE/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="db:MODIFIED_DATE"/>
          </top:modifiedDate>
          <top:soaErrorCode>
            <xsl:text disable-output-escaping="no">'BE-026'</xsl:text>
          </top:soaErrorCode>
          <top:soaErrorDesc>
            <xsl:text disable-output-escaping="no">'Integrity Constraint Violation'</xsl:text>
          </top:soaErrorDesc>
          <top:emailFlag>
            <xsl:text disable-output-escaping="no">0</xsl:text>
          </top:emailFlag>
          <top:deltaReciptFlag>
            <xsl:text disable-output-escaping="no">0</xsl:text>
          </top:deltaReciptFlag>
        </top:EngagePalsCardDtaErrorlog>
      </xsl:for-each>
    </top:EngagePalsCardDtaErrorlogCollection>
  </xsl:template>
</xsl:stylesheet>

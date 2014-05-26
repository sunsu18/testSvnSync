<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../E006CardPALSToPortal.wsdl"/>
      <rootElement name="Card" namespace="http://www.statoilfuelretail.com/integration/engage"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../SOAErrorDBAdapter.wsdl"/>
      <rootElement name="EngagePalsCardDtaErrorlogCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/SOAErrorDBAdapter"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [TUE MAY 20 18:38:10 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/SOAErrorDBAdapter"
                xmlns:ns1="http://www.statoilfuelretail.com/integration/engage"
                xmlns:plnk="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E006CardPALSToPortal/E006CardPALSToPortal/SOAErrorDBAdapter"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:client="http://www.statoilfuelretail.com/integration/engage/E006CardPALSToPortal/E006CardPALSToPortal/E006CardPALSToPortal"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl ns1 plnk wsdl xsd client top tns aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:template match="/">
    <top:EngagePalsCardDtaErrorlogCollection>
      <xsl:for-each select="/ns1:Card">
        <!--<xsl:if test='((((((string-length(/ns1:Card/ns1:FieldsDetails/ns1:partnerId) > 8.0) or (string-length(/ns1:Card/ns1:FieldsDetails/ns1:cardgroupMainType) > 3.0)) or (string-length(/ns1:Card/ns1:FieldsDetails/ns1:prtCardPk) > 10.0)) or ((string-length(/ns1:Card/ns1:FieldsDetails/ns1:cardgroupSubType) > 3.0) or (string-length(/ns1:Card/ns1:FieldsDetails/ns1:cardgroupSeq) > 5.0))) or (string-length(/ns1:Card/ns1:FieldsDetails/ns1:cardIdentifier) > 3.0)) or ((string-length(/ns1:Card/ns1:FieldsDetails/ns1:cardType) > 3.0) or (string-length(/ns1:Card/ns1:FieldsDetails/ns1:cardEmbossNum) > 20.0))) or (((normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:cardgroupSubType) = "") or (normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:cardgroupSeq) = "")) or ((normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:partnerId) = "") or (normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:cardgroupMainType) = ""))or ((((normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:cardIdentifier) = "") or (normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:cardType) = "")) or (normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:prtCardPk) = "")) or ((normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:cardEmbossNum) = "") or (normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:accountId) = ""))))'>
-->
        <xsl:if test='((((((string-length(/ns1:Card/ns1:FieldsDetails/ns1:partnerId) > 8.0) or (string-length(/ns1:Card/ns1:FieldsDetails/ns1:cardgroupMainType) > 3.0)) or (string-length(/ns1:Card/ns1:FieldsDetails/ns1:prtCardPk) > 10.0)) or ((string-length(/ns1:Card/ns1:FieldsDetails/ns1:cardgroupSubType) > 3.0) or (string-length(/ns1:Card/ns1:FieldsDetails/ns1:cardgroupSeq) > 5.0))) or (string-length(/ns1:Card/ns1:FieldsDetails/ns1:cardIdentifier) > 3.0)) or ((string-length(/ns1:Card/ns1:FieldsDetails/ns1:cardType) > 3.0) or (string-length(/ns1:Card/ns1:FieldsDetails/ns1:cardEmbossNum) > 20.0))) or (((normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:cardgroupSubType) = "") or (normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:cardgroupSeq) = "")) or (((normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:partnerId) = "") or (normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:cardgroupMainType) = "")) or ((((normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:cardIdentifier) = "") or (normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:cardType) = "")) or (normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:prtCardPk) = "")) or (normalize-space(/ns1:Card/ns1:FieldsDetails/ns1:cardEmbossNum) = ""))))'>
          <top:EngagePalsCardDtaErrorlog>
            <top:deltaTs>
              <xsl:value-of select="ns1:FieldsDetails/ns1:Delta_TS"/>
            </top:deltaTs>
            <top:countryCode>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:countryCode"/>
            </top:countryCode>
            <top:partnerId>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:partnerId"/>
            </top:partnerId>
            <top:cardgroupMainType>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:cardgroupMainType"/>
            </top:cardgroupMainType>
            <top:cardgroupSubType>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:cardgroupSubType"/>
            </top:cardgroupSubType>
            <top:cardgroupSeq>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:cardgroupSeq"/>
            </top:cardgroupSeq>
            <top:prtCardPk>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:prtCardPk"/>
            </top:prtCardPk>
            <top:cardIdentifier>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:cardIdentifier"/>
            </top:cardIdentifier>
            <top:cardType>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:cardType"/>
            </top:cardType>
            <top:magneticCode>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:magneticCode"/>
            </top:magneticCode>
            <top:cardEmbossNum>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:cardEmbossNum"/>
            </top:cardEmbossNum>
            <top:invoiceInformation>
              <xsl:if test="/ns1:Card/ns1:FieldsDetails/ns1:invoiceInformation/@xsi:nil">
                <xsl:attribute name="xsi:nil">
                  <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:invoiceInformation/@xsi:nil"/>
                </xsl:attribute>
              </xsl:if>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:invoiceInformation"/>
            </top:invoiceInformation>
            <top:cardExpiry>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:cardExpiry"/>
            </top:cardExpiry>
            <top:blockType>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:blockType"/>
            </top:blockType>
            <top:blockCode>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:blockCode"/>
            </top:blockCode>
            <top:blockAction>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:blockAction"/>
            </top:blockAction>
            <top:blockDate>
              <xsl:if test="/ns1:Card/ns1:FieldsDetails/ns1:blockDate/@xsi:nil">
                <xsl:attribute name="xsi:nil">
                  <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:blockDate/@xsi:nil"/>
                </xsl:attribute>
              </xsl:if>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:blockDate"/>
            </top:blockDate>
            <top:blockTime>
              <xsl:if test="/ns1:Card/ns1:FieldsDetails/ns1:blockTime/@xsi:nil">
                <xsl:attribute name="xsi:nil">
                  <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:blockTime/@xsi:nil"/>
                </xsl:attribute>
              </xsl:if>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:blockTime"/>
            </top:blockTime>
            <top:blockLevel>
              <xsl:if test="/ns1:Card/ns1:FieldsDetails/ns1:blockLevel/@xsi:nil">
                <xsl:attribute name="xsi:nil">
                  <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:blockLevel/@xsi:nil"/>
                </xsl:attribute>
              </xsl:if>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:blockLevel"/>
            </top:blockLevel>
            <top:manufacturedDate>
              <xsl:if test="/ns1:Card/ns1:FieldsDetails/ns1:manufacturedDate/@xsi:nil">
                <xsl:attribute name="xsi:nil">
                  <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:manufacturedDate/@xsi:nil"/>
                </xsl:attribute>
              </xsl:if>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:manufacturedDate"/>
            </top:manufacturedDate>
            <top:routexCustomerNumber>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:routexCustomerNumber"/>
            </top:routexCustomerNumber>
            <top:invoiceAddr1>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:invoiceAddr1"/>
            </top:invoiceAddr1>
            <top:invoiceAddr2>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:invoiceAddr2"/>
            </top:invoiceAddr2>
            <top:invoicePostalCode>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:invoicePostalCode"/>
            </top:invoicePostalCode>
            <top:invoiceCity>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:invoiceCity"/>
            </top:invoiceCity>
            <top:addrSeq>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:addrSeq"/>
            </top:addrSeq>
            <top:invoiceCountry>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:invoiceCountry"/>
            </top:invoiceCountry>
            <top:invoiceAddrLevel>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:invoiceAddrLevel"/>
            </top:invoiceAddrLevel>
            <top:invoiceAddrType>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:invoiceAddrType"/>
            </top:invoiceAddrType>
            <top:modifiedBy>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:modifiedBy"/>
            </top:modifiedBy>
            <top:modifiedDate>
              <xsl:value-of select="/ns1:Card/ns1:FieldsDetails/ns1:modifiedDate"/>
            </top:modifiedDate>
            <top:soaErrorCode>
              <xsl:text disable-output-escaping="no">BE-014</xsl:text>
            </top:soaErrorCode>
            <top:soaErrorDesc>
              <xsl:text disable-output-escaping="no">'Missing Mandatory field or size of field exceeds the expected length in fetched data from sourcetable. Unable to process the record.'</xsl:text>
            </top:soaErrorDesc>
            <top:emailFlag>
              <xsl:text disable-output-escaping="no">0</xsl:text>
            </top:emailFlag>
            <top:deltaReciptFlag>
              <xsl:text disable-output-escaping="no">0</xsl:text>
            </top:deltaReciptFlag>
          </top:EngagePalsCardDtaErrorlog>
        </xsl:if>
      </xsl:for-each>
    </top:EngagePalsCardDtaErrorlogCollection>
  </xsl:template>
</xsl:stylesheet>

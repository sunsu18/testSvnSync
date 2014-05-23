<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../../../../../PALS%20Interfaces/E010/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/MergeInvoicePaymentPortalDBAdapterV1.wsdl"/>
      <rootElement name="PrtCardPaymentDocCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeInvoicePaymentPortalDBAdapterV1"/>
    </source>
    <source type="WSDL">
      <schema location="../../../../../PALS%20Interfaces/E010/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/SelectInvoicePaymentPALSDB2DBAdapterV1.wsdl"/>
      <rootElement name="Cc075Vs1CssbdokDtaCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectInvoicePaymentPALSDB2DBAdapterV1"/>
      <param name="InvokeSelectInvoicePaymentsPALSDB2DBAdapterV1_OV.Cc075Vs1CssbdokDtaCollection" />
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../../../../../PALS%20Interfaces/E010/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/InsertInvoicePaymentSOAErrorLogDBAdapterV1.wsdl"/>
      <rootElement name="EngageSoaInvPmtErrorlogCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/InsertInvoicePaymentSOAErrorLogDBAdapterV1"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [FRI MAY 23 20:35:15 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeInvoicePaymentPortalDBAdapterV1"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:ns3="http://xmlns.oracle.com/pcbpel/adapter/db/top/InsertInvoicePaymentSOAErrorLogDBAdapterV1"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:ns2="http://xmlns.oracle.com/pcbpel/adapter/db/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/InsertInvoicePaymentSOAErrorLogDBAdapterV1"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/MergeInvoicePaymentPortalDBAdapterV1"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:ns1="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectInvoicePaymentPALSDB2DBAdapterV1"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/SelectInvoicePaymentPALSDB2DBAdapterV1"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl top plt wsdl tns xsd ns1 ns0 ns3 ns2 aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:param name="InvokeSelectInvoicePaymentsPALSDB2DBAdapterV1_OV.Cc075Vs1CssbdokDtaCollection"/>
  <xsl:template match="/">
    <ns3:EngageSoaInvPmtErrorlogCollection>
      <xsl:for-each select="/top:PrtCardPaymentDocCollection/top:PrtCardPaymentDoc">
        <xsl:if test='top:accountId = ""'>
          <ns3:EngageSoaInvPmtErrorlog>
            <xsl:for-each select="$InvokeSelectInvoicePaymentsPALSDB2DBAdapterV1_OV.Cc075Vs1CssbdokDtaCollection/ns1:Cc075Vs1CssbdokDtaCollection/ns1:Cc075Vs1CssbdokDta">
<!--              <xsl:if test="normalize-space(ns1:fakdokTp) = normalize-space(top:invoiceDocType)">
                <xsl:if test="normalize-space(top:invoiceDocNumber) = normalize-space(ns1:fakdokNr)">-->
                   <ns3:deltaTs>
                    <xsl:value-of select="ns1:deltaTs"/>
                  </ns3:deltaTs>	
<!--                </xsl:if>
              </xsl:if>-->
            </xsl:for-each>
            <ns3:countryCode>
              <xsl:value-of select="top:countryCode"/>
            </ns3:countryCode>
            <ns3:invoiceDocNumber>
              <xsl:value-of select="top:invoiceDocNumber"/>
            </ns3:invoiceDocNumber>
            <ns3:invoiceDocType>
              <xsl:value-of select="top:invoiceDocType"/>
            </ns3:invoiceDocType>
            <ns3:partnerId>
              <xsl:value-of select="top:partnerId"/>
            </ns3:partnerId>
            <ns3:companyType>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:companyType/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:companyType"/>
            </ns3:companyType>
            <ns3:cardgroupMainType>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:cardgroupMainType/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:cardgroupMainType"/>
            </ns3:cardgroupMainType>
            <ns3:cardgroupSubType>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:cardgroupSubType/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:cardgroupSubType"/>
            </ns3:cardgroupSubType>
            <ns3:cardgroupSeq>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:cardgroupSeq/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:cardgroupSeq"/>
            </ns3:cardgroupSeq>
            <ns3:invoicingDate>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:invoicingDate/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:invoicingDate"/>
            </ns3:invoicingDate>
            <ns3:invoicingDueDate>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:invoicingDueDate/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:invoicingDueDate"/>
            </ns3:invoicingDueDate>
            <ns3:invoiceLevel>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:invoiceLevel/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:invoiceLevel"/>
            </ns3:invoiceLevel>
            <ns3:paymentCode>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:paymentCode/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:paymentCode"/>
            </ns3:paymentCode>
            <ns3:electronicInvoiceType>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:electronicInvoiceType/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:electronicInvoiceType"/>
            </ns3:electronicInvoiceType>
            <ns3:invoiceLangCode>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:invoiceLangCode/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:invoiceLangCode"/>
            </ns3:invoiceLangCode>
            <ns3:groupingInvoiceType>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:groupingInvoiceType/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:groupingInvoiceType"/>
            </ns3:groupingInvoiceType>
            <ns3:printInvoice>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:printInvoice/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:printInvoice"/>
            </ns3:printInvoice>
            <ns3:partnerShortname>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:partnerShortname/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:partnerShortname"/>
            </ns3:partnerShortname>
            <ns3:invoiceAddr1>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:invoiceAddr1/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:invoiceAddr1"/>
            </ns3:invoiceAddr1>
            <ns3:invoiceAddr2>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:invoiceAddr2/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:invoiceAddr2"/>
            </ns3:invoiceAddr2>
            <ns3:invoiceAddr3>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:invoiceAddr3/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:invoiceAddr3"/>
            </ns3:invoiceAddr3>
            <ns3:invoiceCountry>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:invoiceCountry/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:invoiceCountry"/>
            </ns3:invoiceCountry>
            <ns3:invoiceReceiver>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:invoiceReceiver/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:invoiceReceiver"/>
            </ns3:invoiceReceiver>
            <ns3:companyName>
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:companyName/@xsi:nil"/>
              </xsl:attribute>
              <xsl:value-of select="top:companyName"/>
            </ns3:companyName>
            <ns3:errorCode>
              <xsl:text disable-output-escaping="no">BE-020</xsl:text>
            </ns3:errorCode>
            <ns3:errorDesc>
              <xsl:text disable-output-escaping="no">'Account ID not found'</xsl:text>
            </ns3:errorDesc>
            <ns3:emailFlag>
              <xsl:text disable-output-escaping="no">0</xsl:text>
            </ns3:emailFlag>
            <ns3:deltaFlag>
              <xsl:text disable-output-escaping="no">0</xsl:text>
            </ns3:deltaFlag>
            <ns3:modifiedBy>
              <xsl:value-of select="top:modifiedBy"/>
            </ns3:modifiedBy>
            <ns3:modifiedDate>
              <xsl:value-of select="top:modifiedDate"/>
            </ns3:modifiedDate>
          </ns3:EngageSoaInvPmtErrorlog>
        </xsl:if>
      </xsl:for-each>
    </ns3:EngageSoaInvPmtErrorlogCollection>
  </xsl:template>
</xsl:stylesheet>

<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../../../../../PALS%20Interfaces/E010/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/E010PALSInvoicePaymentdoctoPortalWrapper.wsdl"/>
      <rootElement name="PrtCardPaymentDocCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/InvoicePaymentPortalValidation"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../../../../../PALS%20Interfaces/E010/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/MergeInvoicePaymentPortalDBAdapterV1.wsdl"/>
      <rootElement name="PrtCardPaymentDocCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeInvoicePaymentPortalDBAdapterV1"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [FRI MAY 23 21:50:41 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeInvoicePaymentPortalDBAdapterV1"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:ns2="http://xmlns.oracle.com/pcbpel/adapter/db/top/InvoicePaymentPortalValidation"
                xmlns:ns1="http://www.statoilfuelretail.com/integration/E010InvoiceCardPaymentValidate"
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
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/MergeInvoicePaymentPortalDBAdapterV1"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:tns="http://oracle.com/sca/soapservice/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/E010PALSInvoicePaymentdoctoPortal"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl bpws ns2 ns1 plnk wsdl tns xsd top ns0 aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:template match="/">
    <top:PrtCardPaymentDocCollection>
      <xsl:for-each select="/ns2:PrtCardPaymentDocCollection/ns2:PrtCardPaymentDoc">
        <top:PrtCardPaymentDoc>
          <top:countryCode>
            <xsl:value-of select="ns2:countryCode"/>
          </top:countryCode>
          <top:invoiceDocNumber>
            <xsl:value-of select="ns2:invoiceDocNumber"/>
          </top:invoiceDocNumber>
          <top:invoiceDocType>
            <xsl:value-of select="ns2:invoiceDocType"/>
          </top:invoiceDocType>
          <top:partnerId>
            <xsl:value-of select="ns2:partnerId"/>
          </top:partnerId>
          <top:companyType>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:companyType/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:companyType"/>
          </top:companyType>
          <top:accountId>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:accountId/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:accountId"/>
          </top:accountId>
          <top:cardgroupMainType>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:cardgroupMainType/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:cardgroupMainType"/>
          </top:cardgroupMainType>
          <top:cardgroupSubType>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:cardgroupSubType/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:cardgroupSubType"/>
          </top:cardgroupSubType>
          <top:cardgroupSeq>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:cardgroupSeq/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:cardgroupSeq"/>
          </top:cardgroupSeq>
          <top:invoicingDate>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:invoicingDate/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:invoicingDate"/>
          </top:invoicingDate>
          <top:invoicingDueDate>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:invoicingDueDate/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:invoicingDueDate"/>
          </top:invoicingDueDate>
          <top:invoiceLevel>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:invoiceLevel/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:invoiceLevel"/>
          </top:invoiceLevel>
          <top:paymentCode>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:paymentCode/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:paymentCode"/>
          </top:paymentCode>
          <top:electronicInvoiceType>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:electronicInvoiceType/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:electronicInvoiceType"/>
          </top:electronicInvoiceType>
          <top:invoiceLangCode>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:invoiceLangCode/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:invoiceLangCode"/>
          </top:invoiceLangCode>
          <top:groupingInvoiceType>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:groupingInvoiceType/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:groupingInvoiceType"/>
          </top:groupingInvoiceType>
          <top:printInvoice>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:printInvoice/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:printInvoice"/>
          </top:printInvoice>
          <top:partnerShortname>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:partnerShortname/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:partnerShortname"/>
          </top:partnerShortname>
          <top:invoiceAddr1>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:invoiceAddr1/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:invoiceAddr1"/>
          </top:invoiceAddr1>
          <top:invoiceAddr2>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:invoiceAddr2/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:invoiceAddr2"/>
          </top:invoiceAddr2>
          <top:invoiceAddr3>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:invoiceAddr3/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:invoiceAddr3"/>
          </top:invoiceAddr3>
          <top:invoiceCountry>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:invoiceCountry/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:invoiceCountry"/>
          </top:invoiceCountry>
          <top:invoiceReceiver>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:invoiceReceiver/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:invoiceReceiver"/>
          </top:invoiceReceiver>
          <top:companyName>
            <xsl:attribute name="xsi:nil">
              <xsl:value-of select="ns2:companyName/@xsi:nil"/>
            </xsl:attribute>
            <xsl:value-of select="ns2:companyName"/>
          </top:companyName>
          <top:modifiedBy>
            <xsl:value-of select="ns2:modifiedBy"/>
          </top:modifiedBy>
          <top:modifiedDate>
            <xsl:value-of select="ns2:modifiedDate"/>
          </top:modifiedDate>
        </top:PrtCardPaymentDoc>
      </xsl:for-each>
    </top:PrtCardPaymentDocCollection>
  </xsl:template>
</xsl:stylesheet>

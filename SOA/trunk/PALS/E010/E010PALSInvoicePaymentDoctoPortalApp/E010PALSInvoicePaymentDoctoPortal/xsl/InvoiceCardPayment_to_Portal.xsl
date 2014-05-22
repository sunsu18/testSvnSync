<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../E010PALSInvoicePaymentdoctoPortal.wsdl"/>
      <rootElement name="invocationMsg" namespace="http://www.statoilfuelretail.com/integration/E010/E010InvocationReqMsg"/>
    </source>
    <source type="WSDL">
      <schema location="../SelectInvoicePaymentPALSDB2DBAdapterV1.wsdl"/>
      <rootElement name="Cc075Vs1CssbdokDtaCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectInvoicePaymentPALSDB2DBAdapterV1"/>
      <param name="InvokeSelectInvoicePaymentsPALSDB2DBAdapterV1_OV.Cc075Vs1CssbdokDtaCollection" />
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../MergeInvoicePaymentPortalDBAdapterV1.wsdl"/>
      <rootElement name="PrtCardPaymentDocCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeInvoicePaymentPortalDBAdapterV1"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [THU MAY 22 15:12:19 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:ns2="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeInvoicePaymentPortalDBAdapterV1"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:inp1="http://www.statoilfuelretail.com/integration/E010/E010InvocationReqMsg"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
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
                xmlns:ns1="http://xmlns.oracle.com/pcbpel/adapter/db/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/MergeInvoicePaymentPortalDBAdapterV1"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:tns="http://oracle.com/sca/soapservice/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/E010PALSInvoicePaymentdoctoPortal"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectInvoicePaymentPALSDB2DBAdapterV1"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E010PALSInvoicePaymentDoctoPortalApp/E010PALSInvoicePaymentDoctoPortal/SelectInvoicePaymentPALSDB2DBAdapterV1"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl inp1 plt wsdl tns xsd top ns0 ns2 ns1 aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:param name="InvokeSelectInvoicePaymentsPALSDB2DBAdapterV1_OV.Cc075Vs1CssbdokDtaCollection"/>
  <xsl:template match="/">
    <ns2:PrtCardPaymentDocCollection>
      <xsl:for-each select="$InvokeSelectInvoicePaymentsPALSDB2DBAdapterV1_OV.Cc075Vs1CssbdokDtaCollection/top:Cc075Vs1CssbdokDtaCollection/top:Cc075Vs1CssbdokDta">
        <xsl:if test='(string-length(/inp1:invocationMsg/inp1:CountryCode) = 2.0) and ((normalize-space(top:fakdokNr) != "") and ((normalize-space(/inp1:invocationMsg/inp1:CountryCode) != "") and (normalize-space(top:fakdokTp) != "")))'>
          <ns2:PrtCardPaymentDoc>
            <ns2:countryCode>
              <xsl:value-of select="/inp1:invocationMsg/inp1:CountryCode"/>
            </ns2:countryCode>
            <ns2:invoiceDocNumber>
              <xsl:value-of select="top:fakdokNr"/>
            </ns2:invoiceDocNumber>
            <ns2:invoiceDocType>
              <xsl:value-of select="top:fakdokTp"/>
            </ns2:invoiceDocType>
            <ns2:partnerId>
              <xsl:value-of select="top:partnerId"/>
            </ns2:partnerId>
            <ns2:companyType>
              <xsl:value-of select="top:partnerTp"/>
            </ns2:companyType>
            <ns2:cardgroupMainType>
              <xsl:value-of select="top:fForholdHtp"/>
            </ns2:cardgroupMainType>
            <ns2:cardgroupSubType>
              <xsl:value-of select="top:fForholdUtp"/>
            </ns2:cardgroupSubType>
            <ns2:cardgroupSeq>
              <xsl:value-of select="top:fForholdLnr"/>
            </ns2:cardgroupSeq>
            <ns2:invoicingDate>
              <xsl:value-of select="top:fakturaDt"/>
            </ns2:invoicingDate>
            <ns2:invoicingDueDate>
              <xsl:value-of select="top:forfallDt"/>
            </ns2:invoicingDueDate>
            <ns2:invoiceLevel>
              <xsl:value-of select="top:betdoknivaKd"/>
            </ns2:invoiceLevel>
            <ns2:paymentCode>
              <xsl:value-of select="top:betMaateKd"/>
            </ns2:paymentCode>
            <ns2:electronicInvoiceType>
              <xsl:value-of select="top:fakelektroKd"/>
            </ns2:electronicInvoiceType>
            <ns2:invoiceLangCode>
              <xsl:value-of select="top:sprakKd"/>
            </ns2:invoiceLangCode>
            <ns2:groupingInvoiceType>
              <xsl:value-of select="top:faksmndKd"/>
            </ns2:groupingInvoiceType>
            <ns2:printInvoice>
              <xsl:value-of select="top:fakutskriftJn"/>
            </ns2:printInvoice>
            <ns2:partnerShortname>
              <xsl:value-of select="top:kortNv"/>
            </ns2:partnerShortname>
            <ns2:invoiceAddr1>
              <xsl:value-of select="top:adresselnj1"/>
            </ns2:invoiceAddr1>
            <ns2:invoiceAddr2>
              <xsl:value-of select="top:adresselnj2"/>
            </ns2:invoiceAddr2>
            <ns2:invoiceAddr3>
              <xsl:value-of select="top:adresselnj3"/>
            </ns2:invoiceAddr3>
            <ns2:invoiceCountry>
              <xsl:value-of select="top:landNv"/>
            </ns2:invoiceCountry>
            <ns2:invoiceReceiver>
              <xsl:value-of select="top:fakmottakerNv"/>
            </ns2:invoiceReceiver>
            <ns2:companyName>
              <xsl:value-of select="top:redigertNv"/>
            </ns2:companyName>
            <ns2:modifiedBy>
              <xsl:value-of select="/inp1:invocationMsg/inp1:UserName"/>
            </ns2:modifiedBy>
            <ns2:modifiedDate>
              <xsl:value-of select="xp20:current-dateTime()"/>
            </ns2:modifiedDate>
          </ns2:PrtCardPaymentDoc>
        </xsl:if>
      </xsl:for-each>
    </ns2:PrtCardPaymentDocCollection>
  </xsl:template>
</xsl:stylesheet>

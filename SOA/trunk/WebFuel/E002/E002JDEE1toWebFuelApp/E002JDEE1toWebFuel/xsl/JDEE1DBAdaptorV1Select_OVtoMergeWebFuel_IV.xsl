<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../SelectJDEE1DBAdaptorV1.wsdl"/>
      <rootElement name="F5542112Collection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectJDEE1DBAdaptorV1"/>
      <param name="InvokeSelectJDEE1DBAdaptorV1Select_OV.F5542112Collection" />
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../MergeWebFuelDBAdapterV1.wsdl"/>
      <rootElement name="PrtListPricesCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeWebFuelDBAdapterV1"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [THU MAR 06 19:02:07 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E002JDEE1toWebFuelApp/E002JDEE1toWebFuel/SelectJDEE1DBAdaptorV1"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E002JDEE1toWebFuelApp/E002JDEE1toWebFuel/MergeWebFuelDBAdapterV1"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:plnk="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:client="http://www.statoilfuelretail.com/integration/E002/E002JDEE1toWebFuelApp/E002JDEE1toWebFuel/E002JDEE1toWebFuelBPEL"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:ns1="http://www.statoilfuelretail.com/integration/E002/E002InvocationReqMsg"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:ns2="http://xmlns.oracle.com/pcbpel/adapter/db/top/MergeWebFuelDBAdapterV1"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectJDEE1DBAdaptorV1"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl tns plnk wsdl xsd top ns0 ns2 aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:param name="InvokeSelectJDEE1DBAdaptorV1Select_OV.F5542112Collection"/>
  <xsl:param name="UserName"/>
  <xsl:template match="/">
    <ns2:PrtListPricesCollection>
      <xsl:for-each select="/top:F5542112Collection/top:F5542112">
        <ns2:PrtListPrices>
          <ns2:priceType>
            <xsl:text disable-output-escaping="no">TRUCK</xsl:text>
          </ns2:priceType>
          <ns2:itemNum>
            <xsl:value-of select="normalize-space(top:tllitm)"/>
          </ns2:itemNum>
          <ns2:descLine1>
            <xsl:if test="top:tldsc1/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="top:tldsc1/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="top:tldsc1"/>
          </ns2:descLine1>
          <ns2:effectiveDate>
            <xsl:value-of select='substring(xp20:add-dayTimeDuration-to-dateTime(concat(substring((top:tlpefj + 1900000.0),1.0,4.0),"-01-01T00:00:00+00:00"),concat("P",string((number(substring(top:tlpefj,4.0)) - 1.0)),"D")),1.0,10.0)'/>
          </ns2:effectiveDate>
          <ns2:endDate>
            <xsl:text disable-output-escaping="no">9999-12-31</xsl:text>
          </ns2:endDate>
          <ns2:unitPrice>
            <xsl:value-of select='format-number(normalize-space(top:tluprc),"##.00") div 10000.0'/>
          </ns2:unitPrice>
          <ns2:currencyCode>
            <xsl:value-of select="normalize-space(top:tlcrcd)"/>
          </ns2:currencyCode>
          <ns2:countryCode>
            <xsl:value-of select='dvm:lookupValue("WebFuelCountryCode.dvm","CurrencyCode",normalize-space(top:tlcrcd),"CountryCode","Invalid")'/>
          </ns2:countryCode>
          <ns2:unitQty>
            <xsl:value-of select="normalize-space(top:tluorg) div 10000.0"/>
          </ns2:unitQty>
          <ns2:pricingUom>
            <xsl:value-of select="normalize-space(top:tluom4)"/>
          </ns2:pricingUom>
          <ns2:modifiedBy>
            <xsl:value-of select="$UserName"/>
          </ns2:modifiedBy>
          <ns2:modifiedDate>
            <xsl:value-of select="xp20:current-dateTime()"/>
          </ns2:modifiedDate>
        </ns2:PrtListPrices>
      </xsl:for-each>
    </ns2:PrtListPricesCollection>
  </xsl:template>
</xsl:stylesheet>

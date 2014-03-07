<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../../../../../SOA%20Code/E002/E002JDEE1toWebFuelApp/E002JDEE1toWebFuel/E002JDEE1toWebFuelBPEL.wsdl"/>
      <rootElement name="F5542112Collection" namespace="http://www.statoilfuelretail.com/integration/E002/SelectJDEE1DBAdaptorV1_validation"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../../../../../SOA%20Code/E002/E002JDEE1toWebFuelApp/E002JDEE1toWebFuel/E002JDEE1toWebFuelBPEL.wsdl"/>
      <rootElement name="F5542112Collection" namespace="http://www.statoilfuelretail.com/integration/E002/SelectJDEE1DBAdaptorV1_validation"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [FRI MAR 07 17:31:32 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:ns1="http://www.statoilfuelretail.com/integration/E002/E002InvocationReqMsg"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:ns2="http://www.statoilfuelretail.com/integration/E002/SelectJDEE1DBAdaptorV1_validation"
                xmlns:plnk="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:client="http://www.statoilfuelretail.com/integration/E002/E002JDEE1toWebFuelApp/E002JDEE1toWebFuel/E002JDEE1toWebFuelBPEL"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl ns1 ns2 plnk xsd wsdl client xp20 bpws aia mhdr bpel oraext dvm hwf med ids bpm xdk xref ora socket ldap">
  <xsl:template match="/">
    <ns2:F5542112Collection>
      <xsl:for-each select="/ns2:F5542112Collection/ns2:F5542112">
        <xsl:if test='(normalize-space(ns2:tlcrcd) = "") or (((((normalize-space(ns2:tlpefj) = "") or (normalize-space(ns2:tllitm) = "")) or (normalize-space(ns2:tluorg) = "")) or (normalize-space(ns2:tluprc) = "")) or (normalize-space(ns2:tluom4) = ""))'>
          <ns2:F5542112>
            <xsl:if test='normalize-space(ns2:tlpefj) = ""'>
              <ns2:tlpefj>
                <xsl:value-of select='concat("effectiveDate missing for Item no: ",ns2:tllitm,"&lt;br/>")'/>
              </ns2:tlpefj>
            </xsl:if>
            <xsl:if test='normalize-space(ns2:tllitm) = ""'>
              <ns2:tllitm>
                <xsl:text disable-output-escaping="no">Missing itemNuimber
</xsl:text>
              </ns2:tllitm>
            </xsl:if>
            <xsl:if test='normalize-space(ns2:tluorg) = ""'>
              <ns2:tluorg>
                <xsl:value-of select='concat("unitQuantity missing for itemNumber",ns2:tllitm,"&lt;br/>")'/>
              </ns2:tluorg>
            </xsl:if>
            <xsl:if test='normalize-space(ns2:tluprc) = ""'>
              <ns2:tluprc>
                <xsl:value-of select='concat("unitPrice missing for itemNumber: ",ns2:tllitm,"&lt;br/>")'/>
              </ns2:tluprc>
            </xsl:if>
            <xsl:if test='normalize-space(ns2:tluom4) = ""'>
              <ns2:tluom4>
                <xsl:value-of select='concat("PricingUOM missing for itemNumber: ",ns2:tllitm,"&lt;br/>")'/>
              </ns2:tluom4>
            </xsl:if>
            <xsl:if test='normalize-space(ns2:tlcrcd) = ""'>
              <ns2:tlcrcd>
                <xsl:value-of select='concat("currencyCode missing for itemNumber: ",ns2:tllitm,"&lt;br/>")'/>
              </ns2:tlcrcd>
            </xsl:if>
          </ns2:F5542112>
        </xsl:if>
      </xsl:for-each>
    </ns2:F5542112Collection>
  </xsl:template>
</xsl:stylesheet>

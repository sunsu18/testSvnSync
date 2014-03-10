<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../../../../../SOA%20Code/E002/E002JDEE1toWebFuelApp/E002JDEE1toWebFuel/SelectJDEE1DBAdaptorV1.wsdl"/>
      <rootElement name="F5542112Collection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectJDEE1DBAdaptorV1"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../../../../../SOA%20Code/E002/E002JDEE1toWebFuelApp/E002JDEE1toWebFuel/E002JDEE1toWebFuelBPEL.wsdl"/>
      <rootElement name="F5542112Collection" namespace="http://www.statoilfuelretail.com/integration/E002/SelectJDEE1DBAdaptorV1_validation"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [FRI MAR 07 11:27:54 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E002JDEE1toWebFuelApp/E002JDEE1toWebFuel/SelectJDEE1DBAdaptorV1"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
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
                xmlns:ns2="http://www.statoilfuelretail.com/integration/E002/SelectJDEE1DBAdaptorV1_validation"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectJDEE1DBAdaptorV1"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl tns plt wsdl xsd top client ns1 ns2 aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:template match="/">
    <ns2:F5542112Collection>
      <xsl:for-each select="/top:F5542112Collection/top:F5542112">
        <ns2:F5542112>
          <ns2:tlkcoo>
            <xsl:value-of select="top:tlkcoo"/>
          </ns2:tlkcoo>
          <ns2:tldoco>
            <xsl:value-of select="top:tldoco"/>
          </ns2:tldoco>
          <ns2:tldcto>
            <xsl:value-of select="top:tldcto"/>
          </ns2:tldcto>
          <ns2:tllnid>
            <xsl:value-of select="top:tllnid"/>
          </ns2:tllnid>
          <ns2:tlpefj>
            <xsl:value-of select="top:tlpefj"/>
          </ns2:tlpefj>
          <ns2:tllitm>
            <xsl:value-of select="top:tllitm"/>
          </ns2:tllitm>
          <ns2:tldsc1>
            <xsl:value-of select="top:tldsc1"/>
          </ns2:tldsc1>
          <ns2:tluorg>
            <xsl:value-of select="top:tluorg"/>
          </ns2:tluorg>
          <ns2:tluprc>
            <xsl:value-of select="top:tluprc"/>
          </ns2:tluprc>
          <ns2:tluom4>
            <xsl:value-of select="top:tluom4"/>
          </ns2:tluom4>
          <ns2:tlcrcd>
            <xsl:value-of select="top:tlcrcd"/>
          </ns2:tlcrcd>
          <ns2:tly55Intst>
            <xsl:value-of select="top:tly55Intst"/>
          </ns2:tly55Intst>
        </ns2:F5542112>
      </xsl:for-each>
    </ns2:F5542112Collection>
  </xsl:template>
</xsl:stylesheet>
<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../BusinessHoursSubscriptionDBAdapter.wsdl"/>
      <rootElement name="PrtCardRuleSubscriptionCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/BusinessHoursSubscriptionDBAdapter"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../SOARuleEngineServiceJMSConsumerV1.wsdl"/>
      <rootElement name="ParameterData" namespace="http://www.lntinfotech.com/integration/RuleServiceABO"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [FRI SEP 26 23:51:24 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/BusinessHoursSubscriptionDBAdapter"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/E013BusinessHourRuleApp/E013BusinessHourRule/BusinessHoursSubscriptionDBAdapter"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:imp1="http://www.lntinfotech.com/integration/RuleServiceABO"
                xmlns:pc="http://xmlns.oracle.com/pcbpel/"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/jms/E013BusinessHourRuleApp/E013BusinessHourRule/SOARuleEngineServiceJMSConsumerV1"
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
                xmlns:jca="http://xmlns.oracle.com/pcbpel/wsdl/jca/"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:bpmn="http://schemas.oracle.com/bpm/xpath"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl top tns plt wsdl xsd imp1 pc ns0 jca xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:template match="/">
      <imp1:ParameterData>
          <xsl:for-each select="/top:PrtCardRuleSubscriptionCollection/top:PrtCardRuleSubscription/top:prtCardRuleBusinessHoursCollection/top:PrtCardRuleBusinessHours">
        <xsl:if test='top:day = "Monday"'>
          <monday>
            <startTime>
              <xsl:value-of select="top:busiStartFrom"/>
            </startTime>
            <endTime>
              <xsl:value-of select="top:busiStartTo"/>
            </endTime>
          </monday>
        </xsl:if>
        <xsl:if test='top:day = "Tuesday"'>
          <tuesday>
            <startTime>
              <xsl:value-of select="top:busiStartFrom"/>
            </startTime>
            <endTime>
              <xsl:value-of select="top:busiStartTo"/>
            </endTime>
          </tuesday>
        </xsl:if>
        <xsl:if test='top:day = "Wednesday"'>
          <wednesday>
            <startTime>
              <xsl:value-of select="top:busiStartFrom"/>
            </startTime>
            <endTime>
              <xsl:value-of select="top:busiStartTo"/>
            </endTime>
          </wednesday>
        </xsl:if>
        <xsl:if test='top:day = "Thursday"'>
          <thursday>
            <startTime>
              <xsl:value-of select="top:busiStartFrom"/>
            </startTime>
            <endTime>
              <xsl:value-of select="top:busiStartTo"/>
            </endTime>
          </thursday>
        </xsl:if>
        <xsl:if test='top:day = "Friday"'>
          <friday>
            <startTime>
              <xsl:value-of select="top:busiStartFrom"/>
            </startTime>
            <endTime>
              <xsl:value-of select="top:busiStartTo"/>
            </endTime>
          </friday>
        </xsl:if>
        <xsl:if test='top:day = "Saturday"'>
          <saturday>
            <startTime>
              <xsl:value-of select="top:busiStartFrom"/>
            </startTime>
            <endTime>
              <xsl:value-of select="top:busiStartTo"/>
            </endTime>
          </saturday>
        </xsl:if>
        <xsl:if test='top:day = "Sunday"'>
          <sunday>
            <startTime>
              <xsl:value-of select="top:busiStartFrom"/>
            </startTime>
            <endTime>
              <xsl:value-of select="top:busiStartTo"/>
            </endTime>
          </sunday>
        </xsl:if>
            </xsl:for-each>
        <processingDateTime>
          <xsl:value-of select="/top:PrtCardRuleSubscriptionCollection/top:PrtCardRuleSubscription/top:lastExecutedUptoTime"/>
        </processingDateTime>
        <CountryCode>
          <xsl:value-of select="/top:PrtCardRuleSubscriptionCollection/top:PrtCardRuleSubscription/top:countryCode"/>
        </CountryCode>
      </imp1:ParameterData>

  </xsl:template>
</xsl:stylesheet>

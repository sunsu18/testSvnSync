<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../SelectRuleEngineSubscribtionsDBAdapterV1.wsdl"/>
      <rootElement name="RuleengineRulesubscribersCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectRuleEngineSubscribtionsDBAdapterV1"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../ProcessSubscriptions.wsdl"/>
      <rootElement name="RuleServiceRequest" namespace="http://www.lntinfotech.com/integration/RuleServiceABO"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [FRI AUG 22 21:15:04 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:client="http://xmlns.oracle.com/SOARuleEngineApp/SOARuleEngine/ProcessSubscriptions"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:ns0="http://www.lntinfotech.com/integration/RuleServiceABO"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/SelectRuleEngineSubscribtionsDBAdapterV1"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/SOARuleEngineApp/SOARuleEngine/SelectRuleEngineSubscribtionsDBAdapterV1"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:ns1="http://www.lntinfotech.com/integration/SOARuleEngine"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:plnk="http://docs.oasis-open.org/wsbpel/2.0/plnktype"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl top tns plt wsdl xsd client ns0 ns1 plnk xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:template match="/">
    <ns0:RuleServiceRequest>
      <RuleID>
        <xsl:value-of select="/top:RuleengineRulesubscribersCollection/top:RuleengineRulesubscribers/top:rrsRrmRuleid/top:rrmRuleid"/>
      </RuleID>
      <RuleSubscriptionID>
        <xsl:value-of select="/top:RuleengineRulesubscribersCollection/top:RuleengineRulesubscribers/top:rrsId"/>
      </RuleSubscriptionID>
      <NotificationInfo>
        <NotificationMode>
          <xsl:value-of select="/top:RuleengineRulesubscribersCollection/top:RuleengineRulesubscribers/top:rrsChannelprotocol"/>
        </NotificationMode>
        <FirstName>
          <xsl:value-of select="/top:RuleengineRulesubscribersCollection/top:RuleengineRulesubscribers/top:rrsRcmCustomerid/top:rcmFirstname"/>
        </FirstName>
        <LastName>
          <xsl:value-of select="/top:RuleengineRulesubscribersCollection/top:RuleengineRulesubscribers/top:rrsRcmCustomerid/top:rcmLastname"/>
        </LastName>
        <EmailID>
          <xsl:value-of select="/top:RuleengineRulesubscribersCollection/top:RuleengineRulesubscribers/top:rrsRcmCustomerid/top:rcmEmailid"/>
        </EmailID>
        <MobileNumber>
          <xsl:value-of select="/top:RuleengineRulesubscribersCollection/top:RuleengineRulesubscribers/top:rrsRcmCustomerid/top:rcmMobilenumber"/>
        </MobileNumber>
      </NotificationInfo>
      <!--Parameters>
        <xsl:value-of select="oraext:parseXML(oraext:parseXML(/top:RuleengineRulesubscribersCollection/top:RuleengineRulesubscribers/top:rrsParameters))"/>
      </Parameters-->
    </ns0:RuleServiceRequest>
  </xsl:template>
</xsl:stylesheet>

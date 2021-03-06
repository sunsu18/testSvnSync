<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../Subscription.wsdl"/>
      <rootElement name="UnsubscribeRequest" namespace="http://www.lntinfotech.com/integration/SOARuleEngineSubscription"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../UpdateRuleEngineSubscriptionsDBAdapterV1.wsdl"/>
      <rootElement name="RuleengineRulesubscribersCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/UpdateRuleEngineSubscriptionsDBAdapterV1"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [FRI SEP 12 21:25:49 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/UpdateRuleEngineSubscriptionsDBAdapterV1"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction"
                xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
                xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
                xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
                xmlns:plnk="http://docs.oasis-open.org/wsbpel/2.0/plnktype"
                xmlns:client="http://www.lntinfotech.com/integration/SOARuleEngineSubscription/Subscription"
                xmlns:med="http://schemas.oracle.com/mediator/xpath"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
                xmlns:ns1="http://www.lntinfotech.com/integration/SOARuleEngineSubscription"
                xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/db/SOARuleEngineSubscriptionApp/SOARuleEngineSubscription/UpdateRuleEngineSubscriptionsDBAdapterV1"
                xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl wsdl plnk client ns1 xsd plt top tns xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref ldap">
  <xsl:template match="/">
    <top:RuleengineRulesubscribersCollection>
      <xsl:for-each select="/ns1:UnsubscribeRequest/UnsubscribeCollection">
        <top:RuleengineRulesubscribers>
          <top:rrsRcmCustomerid>
            <xsl:value-of select="CustomerID"/>
          </top:rrsRcmCustomerid>
          <top:rrsStatus>
            <xsl:text disable-output-escaping="no">U</xsl:text>
          </top:rrsStatus>
          <top:rrsId>
            <xsl:value-of select="SubscriptionID"/>
          </top:rrsId>
          <top:rrsNextscheduletime>
            <xsl:text disable-output-escaping="no">00000000000000</xsl:text>
          </top:rrsNextscheduletime>
        </top:RuleengineRulesubscribers>
      </xsl:for-each>
    </top:RuleengineRulesubscribersCollection>
  </xsl:template>
</xsl:stylesheet>

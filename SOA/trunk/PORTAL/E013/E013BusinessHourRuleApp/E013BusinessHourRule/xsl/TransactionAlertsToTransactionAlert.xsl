<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../SOARuleEngineServiceJMSConsumerV1.wsdl"/>
      <rootElement name="TransactionData" namespace="http://www.lntinfotech.com/integration/RuleServiceABO"/>
    </source>
    <source type="WSDL">
      <schema location="../SOARuleEngineServiceJMSConsumerV1.wsdl"/>
      <rootElement name="RuleServiceRequest" namespace="http://www.lntinfotech.com/integration/RuleServiceABO"/>
      <param name="RuleServiceABO_Consume_IV.body" />
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../insertCardNotificationsDBAdpterV1.wsdl"/>
      <rootElement name="PrtCardNotificationsCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/insertCardNotificationDBAdpterV1"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [WED SEP 24 11:12:15 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:imp1="http://www.lntinfotech.com/integration/RuleServiceABO"
                xmlns:pc="http://xmlns.oracle.com/pcbpel/"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/jms/E013BusinessHourRuleApp/E013BusinessHourRule/SOARuleEngineServiceJMSConsumerV1"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                xmlns:ora="http://schemas.oracle.com/xpath/extension"
                xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/insertCardNotificationDBAdpterV1"
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/E013BusinessHourRuleApp/E013BusinessHourRule/insertCardNotificationsDBAdpterV1"
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
                exclude-result-prefixes="xsi xsl imp1 pc tns plt wsdl jca xsd top ns0 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:param name="RuleServiceABO_Consume_IV.body"/>
  <xsl:template match="/">
    <top:PrtCardNotificationsCollection>
      <top:PrtCardNotifications>
        <top:notiId>
          <xsl:text disable-output-escaping="no"></xsl:text>
        </top:notiId>
        <top:ruleId>
          <xsl:value-of select="$RuleServiceABO_Consume_IV.body/imp1:RuleServiceRequest/RuleID"/>
        </top:ruleId>
        <top:ruleIsenabled>
          <xsl:text disable-output-escaping="no">Yes</xsl:text>
        </top:ruleIsenabled>
        <top:subId>
          <xsl:value-of select="$RuleServiceABO_Consume_IV.body/imp1:RuleServiceRequest/RuleSubscriptionID"/>
        </top:subId>
        <top:userId>
          <xsl:value-of select="$RuleServiceABO_Consume_IV.body/imp1:RuleServiceRequest/NotificationInfo/EmailID"/>
        </top:userId>
        <top:partner>
          <xsl:value-of select="/imp1:TransactionData/Transaction/PARTNER_ID"/>
        </top:partner>
        <top:accountId>
          <xsl:value-of select="/imp1:TransactionData/Transaction/ACCOUNT_ID"/>
        </top:accountId>
        <top:cardgroupMain>
          <xsl:value-of select="/imp1:TransactionData/Transaction/CARDGROUP_MAIN_TYPE"/>
        </top:cardgroupMain>
        <top:cardgroupSub>
          <xsl:value-of select="/imp1:TransactionData/Transaction/CARDGROUP_SUB_TYPE"/>
        </top:cardgroupSub>
        <top:cardgroupSeq>
          <xsl:value-of select="/imp1:TransactionData/Transaction/CARDGROUP_SEQ"/>
        </top:cardgroupSeq>
        <top:cardPk>
          <xsl:value-of select="/imp1:TransactionData/Transaction/KSID"/>
        </top:cardPk>
        <top:embossCardNum>
          <xsl:value-of select="/imp1:TransactionData/Transaction/CARD_1_ID"/>
        </top:embossCardNum>
        <top:transactionTime>
          <xsl:value-of select="/imp1:TransactionData/Transaction/TRANSACTION_TIME"/>
        </top:transactionTime>
        <top:notiCreated>
          <xsl:value-of select="xp20:current-dateTime()"/>
        </top:notiCreated>
        <top:showFlag>
          <xsl:text disable-output-escaping="no">Yes</xsl:text>
        </top:showFlag>
        <top:notiDescription>
          <xsl:text disable-output-escaping="no">SetByPortal</xsl:text>
        </top:notiDescription>
        <top:modifiedBy>
          <xsl:value-of select="/imp1:TransactionData/Transaction/MODIFIED_BY"/>
        </top:modifiedBy>
        <top:modifiedDate>
          <xsl:value-of select="xp20:current-dateTime()"/>
        </top:modifiedDate>
      </top:PrtCardNotifications>
    </top:PrtCardNotificationsCollection>
  </xsl:template>
</xsl:stylesheet>

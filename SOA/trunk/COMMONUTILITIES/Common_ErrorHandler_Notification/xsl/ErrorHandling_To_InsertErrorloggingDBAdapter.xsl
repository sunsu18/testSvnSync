<?xml version="1.0" encoding="UTF-8" ?>
<?oracle-xsl-mapper
  <!-- SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY. -->
  <mapSources>
    <source type="WSDL">
      <schema location="../SOACommonErrorHandlerJMSConsumerV1.wsdl"/>
      <rootElement name="ErrorInfo" namespace="http://www.statoilfuelretail.com/integration/engage/ErrorHandling.xsd"/>
    </source>
  </mapSources>
  <mapTargets>
    <target type="WSDL">
      <schema location="../InsertErrorloggingDBAdapterV1.wsdl"/>
      <rootElement name="EngageSoaErrorLogsCollection" namespace="http://xmlns.oracle.com/pcbpel/adapter/db/top/InsertErrorloggingDBAdapterV1"/>
    </target>
  </mapTargets>
  <!-- GENERATED BY ORACLE XSL MAPPER 11.1.1.6.0(build 111214.0600.1553) AT [FRI FEB 28 16:05:26 IST 2014]. -->
?>
<xsl:stylesheet version="1.0"
                xmlns:aia="http://www.oracle.com/XSL/Transform/java/oracle.apps.aia.core.xpath.AIAFunctions"
                xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
                xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
                xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:pc="http://xmlns.oracle.com/pcbpel/"
                xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
                xmlns:plt="http://schemas.xmlsoap.org/ws/2003/05/partner-link/"
                xmlns:top="http://xmlns.oracle.com/pcbpel/adapter/db/top/InsertErrorloggingDBAdapterV1"
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
                xmlns:ns0="http://xmlns.oracle.com/pcbpel/adapter/db/ErrorHandlingFramework/Common_ErrorHandler_Notification/InsertErrorloggingDBAdapterV1"
                xmlns:tns="http://xmlns.oracle.com/pcbpel/adapter/jms/ErrorHandlingFramework/Common_ErrorHandler_Notification/SOACommonErrorHandlerJMSConsumerV1"
                xmlns:imp1="http://www.statoilfuelretail.com/integration/engage/ErrorHandling.xsd"
                xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
                exclude-result-prefixes="xsi xsl pc plt wsdl jca xsd tns imp1 top ns0 aia bpws xp20 bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap">
  <xsl:template match="/">
    <top:EngageSoaErrorLogsCollection>
      <xsl:if test="/imp1:ErrorInfo/imp1:CommonErrorData">
        <xsl:if test="/imp1:ErrorInfo/imp1:ErrorDetails">
          <top:EngageSoaErrorLogs>
            <xsl:if test="/imp1:ErrorInfo/imp1:CommonErrorData/imp1:ProjectID">
              <top:interfaceId>
                <xsl:value-of select="/imp1:ErrorInfo/imp1:CommonErrorData/imp1:ProjectID"/>
              </top:interfaceId>
            </xsl:if>
            <xsl:if test="/imp1:ErrorInfo/imp1:CommonErrorData/imp1:ProjectName">
              <top:interfaceName>
                <xsl:value-of select="/imp1:ErrorInfo/imp1:CommonErrorData/imp1:ProjectName"/>
              </top:interfaceName>
            </xsl:if>
            <xsl:if test="/imp1:ErrorInfo/imp1:CommonErrorData/imp1:ComponentName">
              <top:componentName>
                <xsl:value-of select="/imp1:ErrorInfo/imp1:CommonErrorData/imp1:ComponentName"/>
              </top:componentName>
            </xsl:if>
            <xsl:if test="/imp1:ErrorInfo/imp1:CommonErrorData/imp1:InstanceID">
              <top:instanceId>
                <xsl:value-of select="/imp1:ErrorInfo/imp1:CommonErrorData/imp1:InstanceID"/>
              </top:instanceId>
            </xsl:if>
            <xsl:if test="/imp1:ErrorInfo/imp1:CommonErrorData/imp1:Source">
              <top:source>
                <xsl:value-of select="/imp1:ErrorInfo/imp1:CommonErrorData/imp1:Source"/>
              </top:source>
            </xsl:if>
            <xsl:if test="/imp1:ErrorInfo/imp1:CommonErrorData/imp1:Target">
              <top:target>
                <xsl:value-of select="/imp1:ErrorInfo/imp1:CommonErrorData/imp1:Target"/>
              </top:target>
            </xsl:if>
            <xsl:if test="/imp1:ErrorInfo/imp1:ErrorDetails/imp1:ErrorCode">
              <top:errorCode>
                <xsl:value-of select="/imp1:ErrorInfo/imp1:ErrorDetails/imp1:ErrorCode"/>
              </top:errorCode>
            </xsl:if>
            <xsl:if test="/imp1:ErrorInfo/imp1:ErrorDetails/imp1:ErrorDescription">
              <top:errorDescription>
                <xsl:value-of select="/imp1:ErrorInfo/imp1:ErrorDetails/imp1:ErrorDescription"/>
              </top:errorDescription>
            </xsl:if>
            <top:errorSeverity>
              <xsl:value-of select='dvm:lookupValue("ErrorInfo_ErrorDetails.dvm","ErrorCode",/imp1:ErrorInfo/imp1:ErrorDetails/imp1:ErrorCode,"Severity","1")'/>
            </top:errorSeverity>
            <xsl:if test="/imp1:ErrorInfo/imp1:CommonErrorData/imp1:Timestamp">
              <top:instanceTime>
                <xsl:value-of select="/imp1:ErrorInfo/imp1:CommonErrorData/imp1:Timestamp"/>
              </top:instanceTime>
            </xsl:if>
            <top:rowId>
              <xsl:value-of select="oraext:sequence-next-val('ENGAGE_SOA_ERROR_LOGS_ROWID','jdbc:oracle:thin:soacustom/soacustom@10.24.237.66:1521/soa001d.statoilfuelretail.com')"/>
            </top:rowId>
          </top:EngageSoaErrorLogs>
        </xsl:if>
      </xsl:if>
    </top:EngageSoaErrorLogsCollection>
  </xsl:template>
</xsl:stylesheet>

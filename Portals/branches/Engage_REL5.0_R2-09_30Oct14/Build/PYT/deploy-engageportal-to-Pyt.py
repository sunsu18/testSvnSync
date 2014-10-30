import sys


#local
#adminUrl='t3://localhost:7101'
#adminUser='weblogic'
#adminPassword='weblogic1'
#targetsName='DefaultServer'

#Dev
adminUrl='t3://10.24.240.12:11011'
adminUser='weblogic'
adminPassword='weblogic1'
targetsName='Cluster_ENG_cus'

applicationName='EngagePortal_application1'
stageModeValue='stage'

artifactLocation=r'C:\Users\10607024\.jenkins\jobs\Engage Build and Deployment\workspace\EngagePortal\deploy\EngagePortal_application1.ear'

connect(adminUser,adminPassword,adminUrl)
activate()
deploy(applicationName,artifactLocation,targets=targetsName,stageMode=stageModeValue,upload='true',remote='true')
disconnect()

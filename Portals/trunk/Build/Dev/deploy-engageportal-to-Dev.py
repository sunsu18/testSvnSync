import sys


#local
#adminUrl='t3://localhost:7101'
#adminUser='weblogic'
#adminPassword='weblogic1'
#targetsName='DefaultServer'

#Dev
adminUrl='t3://10.24.240.12:11301'
adminUser='weblogic'
adminPassword='weblogic1'
targetsName='server_cus_ENG_DEV01'

applicationName='EngagePortal_application1'
stageModeValue='stage'

artifactLocation=r'D:\jENKINS\jobs\Engage Trunk Build\workspace\EngagePortal\deploy\EngagePortal_application1.ear'

print ("Hello, Python!")
connect(adminUser,adminPassword,adminUrl)
edit()
startEdit()
deploy(applicationName,artifactLocation,targets=targetsName,stageMode=stageModeValue,upload='true',remote='true')
save()
activate()
disconnect()

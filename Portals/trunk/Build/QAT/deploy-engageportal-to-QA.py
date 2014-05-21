import sys


#QA
adminUrl='t3://10.24.240.37:11011'
adminUser='weblogic'
adminPassword='weblogic1'
targetsName='cluster_cus_ENG_QAT01'

applicationName='EngagePortal_application1'
stageModeValue='stage'

artifactLocation=r'C:\Users\10607024\.jenkins\jobs\Engage Build and Deployment\workspace\EngagePortal\deploy\EngagePortal_application1.ear'

connect(adminUser,adminPassword,adminUrl)
deploy(applicationName,artifactLocation,targets=targetsName,stageMode=stageModeValue,upload='true',remote='true')
disconnect()

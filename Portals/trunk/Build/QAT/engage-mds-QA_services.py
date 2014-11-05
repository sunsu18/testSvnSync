import sys


#QA
adminUrl='t3://10.24.240.37:11101'
adminUser='weblogic'
adminPassword='weblogic1'

connect(adminUser,adminPassword,adminUrl)
domainRuntime()

def deployAll():
 print 'Starting the MDS configuration changes...'
 
 #QA
 archive = getMDSArchiveConfig(fromLocation=r'C:\Program Files (x86)\Jenkins\jobs\Engage Build and Deployment\workspace\EngagePortal\deploy\EngagePortal_application1.ear')
 archive.setAppMetadataRepository(repository='mds-CustomPortalDS',partition='EngagePortal_application1_V2.0',type='DB',jndi='jdbc/mds/CustomPortalDS')
 archive.save()
 disconnect()
 print 'Done with the MDS configuration changes...'
 
try:
  deployAll()


except:
  print "Unexpected error: ", sys.exc_info()[0]
  dumpStack()
  raise
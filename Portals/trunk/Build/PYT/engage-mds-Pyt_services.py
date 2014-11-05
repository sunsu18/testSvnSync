import sys

#local
#adminUrl='t3://localhost:7101'
#adminUser='weblogic'
#adminPassword='weblogic1'

#PYT
adminUrl='t3://10.24.240.12:11011'
adminUser='weblogic'
adminPassword='weblogic1'

connect(adminUser,adminPassword,adminUrl)
domainRuntime()

def deployAll():
 print 'Starting the MDS configuration changes...'
 
 #local
 #archive = getMDSArchiveConfig(fromLocation=r'D:\SVN\Engage\Portals\trunk\EngagePortal\deploy\EngagePortal_application1.ear')
 #archive.setAppMetadataRepository(repository='mds-integServerRepos',partition='EngagePortal_application1_V2.0',type='File',path='/C:/Users/10607024.NMUMARL/AppData/Roaming/JDeveloper/system11.1.1.6.38.61.92/DefaultDomain/webcenter/mds-integServerRepos')
 
 #PYT
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
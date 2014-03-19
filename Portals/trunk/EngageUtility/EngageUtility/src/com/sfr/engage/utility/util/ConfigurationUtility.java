package com.sfr.engage.utility.util;

import java.io.FileInputStream;
import java.io.IOException;

import java.io.Serializable;

import java.util.Enumeration;
import java.util.Properties;

public class ConfigurationUtility implements Serializable{

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    
    public ConfigurationUtility() {
        super();
    }    

    /**
     * This method is used for reading the values from properties file of environment
     * @param pName
     * @return
     */
    public static String getPropertyValue(String pName) {
        Properties propertiesFile = new Properties();
        //        System.out.println(System.getenv("ENV_PROPERTIES_PATH"));
        String key = "";
        try {
            if (System.getenv("ENV_PROPERTIES_PATH") != null) {
                StringBuffer mystr =
                    new StringBuffer(System.getenv("ENV_PROPERTIES_PATH").concat("/environment.properties").replace('\\', '/')); //specify path of the file
                propertiesFile.load(new FileInputStream(mystr.toString()));
                Enumeration e = propertiesFile.propertyNames();
                while (e.hasMoreElements()) {
                    key = (String)e.nextElement();
                    if (key.equalsIgnoreCase(pName)) {
                        //System.out.println(key + " = " + propertiesFile.getProperty(key));
                        return propertiesFile.getProperty(key);
                    }
                }
            } else {
                System.out.println("************************ CANNOT READ ENVIRONMENT PROPERTIES ******************");
            }
        } catch (IOException e) {
            System.out.println("Error Occured while getting connection"+e.getMessage());            
        }
        return null;
    }


}

package com.sfr.engage.utility.util;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Enumeration;
import java.util.Properties;

public class ConfigurationUtility {
    public ConfigurationUtility() {
        super();
    }

    /*
     This method is used for reading the values from properties file of environment
    */

    public static String getPropertyValue(String PName) {
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
                    if (key.equalsIgnoreCase(PName)) {
                        //                        System.out.println(key + " = " + propertiesFile.getProperty(key));
                        return propertiesFile.getProperty(key);
                    }
                }
            } else {
                System.out.println("************************ CANNOT READ ENVIRONMENT PROPERTIES ******************");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}

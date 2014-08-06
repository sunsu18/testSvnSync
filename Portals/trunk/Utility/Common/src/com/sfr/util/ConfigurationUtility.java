package com.sfr.util;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Enumeration;
import java.util.Properties;

import oracle.adf.share.logging.ADFLogger;

public class ConfigurationUtility {
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();

    public ConfigurationUtility() {
        super();
    }

    public static String getPropertyValue(String PName) {
        Properties propertiesFile = new Properties();

        String key = "";
        try {
            if (System.getenv("ENV_PROPERTIES_PATH") != null) {
                StringBuffer mystr =
                    new StringBuffer(System.getenv("ENV_PROPERTIES_PATH").concat("/environment.properties").replace('\\',
                                                                                                                    '/')); //specify path of the file
                propertiesFile.load(new FileInputStream(mystr.toString()));
                Enumeration e = propertiesFile.propertyNames();
                while (e.hasMoreElements()) {
                    key = (String)e.nextElement();
                    if (key.equalsIgnoreCase(PName)) {

                        return propertiesFile.getProperty(key);
                    }
                }
            } else {
                log.info("************************ CANNOT READ ENVIRONMENT PROPERTIES ******************");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

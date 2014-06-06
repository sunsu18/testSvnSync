package com.sfr.util.validations;


import com.sfr.util.constants.Constants;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Conversion {
    public Conversion() {
        super();
    }

    public String getCurrencyCode(String country) {
        if (country == null || country.isEmpty())
        {
            return null;
        }else if(country.equalsIgnoreCase("SE"))
        {
            return "SEK";
        }else if (country.equalsIgnoreCase("NO"))
        {
            return "NOK";
        }else if(country.equalsIgnoreCase("DK"))
        {
            return "DKK";
        }
        else
        {
            return "US";
        }

    }

    public String getCustomerCountryCode(String countryCode)
    {
        if(countryCode.equalsIgnoreCase("NO"))
            return "no_NO";
        if(countryCode.equalsIgnoreCase("SE"))
            return "se_SE";
        if(countryCode.equalsIgnoreCase("DK"))
            return "da_DK";
        else

    return "no_NO";
    }
    public Locale getLocaleFromCountryCode(String countryCode) {
        if (countryCode == null || countryCode.isEmpty())
            return null;

        if (countryCode.equalsIgnoreCase(Constants.COUNTRY_CD_SWEDEN))
            return new Locale("sv", "SE");
        if (countryCode.equalsIgnoreCase(Constants.COUNTRY_CD_DENMARK))
            return new Locale("da", "DK");

        if (countryCode.equalsIgnoreCase(Constants.COUNTRY_CD_NORWAY))
            return new Locale("no", "NO");
        else
            return new Locale("en", "US");


    }

    public String getCountryCode(String CurrencyCode) {
        if (CurrencyCode == null || CurrencyCode.isEmpty())
            return null;
        if (CurrencyCode.equalsIgnoreCase("SEK"))
            return "SE";
        if (CurrencyCode.equalsIgnoreCase("NOK"))
            return "NO";
        if (CurrencyCode.equalsIgnoreCase("DKK"))
            return "DK";
        else
            return "US";

    }


    public String getLangForWERCSURL(String countryCode) {

//        System.out.println(AccessDataControl.getDisplayRecord()+"Conversion.getLangForWERCSURL : "+"countryCode::::"+countryCode);
        String lang = "";

       if (countryCode != null && !countryCode.isEmpty()){

        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("se_SE", "SE");
        paramMap.put("no_NO", "NO");
        paramMap.put("da_DK", "DK");
        paramMap.put("pl_PL", "PL");
        paramMap.put("lv_LV", "LV");
        paramMap.put("et_EE", "EE");
        paramMap.put("lt-LT", "LT");
        paramMap.put("en_EN", "EN");

       lang = paramMap.get(countryCode);

        return lang;
        }
       else
           return lang="SE";
    }

    public static String getLangFromCountryCode(String countryCode) {
//        System.out.print("Conversion.getLangFromCountryCode : " +
//                         "countryCode:<" + countryCode + ">");
        String lang = Constants.LANGUAGE_SWEDISH;
        if (countryCode != null && !countryCode.isEmpty()) {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put(Constants.COUNTRY_CD_SWEDEN,Constants.LANGUAGE_SWEDISH);
            paramMap.put(Constants.COUNTRY_CD_NORWAY,Constants.LANGUAGE_NORWEGIAN);
            paramMap.put(Constants.COUNTRY_CD_DENMARK,Constants.LANGUAGE_DANISH);
            paramMap.put(Constants.COUNTRY_CD_POLAND,Constants.LANGUAGE_POLAND);
            paramMap.put(Constants.COUNTRY_CD_LATVIA,Constants.LANGUAGE_LATVIA);
            paramMap.put(Constants.COUNTRY_CD_ESTONIA,Constants.LANGUAGE_ESTONIA);
            paramMap.put(Constants.COUNTRY_CD_LITHUANIA,Constants.LANGUAGE_LITHUNIA);
            paramMap.put(Constants.COUNTRY_CD_UK, Constants.LANGUAGE_ENGLISH);

            lang = paramMap.get(countryCode);
        }
//        System.out.println("lang=<" + lang + ">");
        return lang;
    }
}

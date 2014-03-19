package com.sfr.engage.utility.util;

import java.io.Serializable;

public class Conversion implements Serializable{

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    
    public Conversion() {
        super();
    }

    /**
     * This Method will return Country Code for translations.
     * @param country
     * @return
     */
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
        }else if(country.equalsIgnoreCase("DE"))
        {
            return "DKK";
        }
        else
        {
            return "US";
        }

    }

}

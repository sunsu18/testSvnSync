package com.sfr.engage.utility.util;

public class Conversion {
    public Conversion() {
        super();
    }
    
    public String getCurrencyCode(String Country) {
        if (Country == null || Country.isEmpty())
            return null;
        if (Country.equalsIgnoreCase("SE"))
            return "SEK";
        if (Country.equalsIgnoreCase("NO"))
            return "NOK";
        if (Country.equalsIgnoreCase("DE"))
            return "DKK";
        else
            return "US";

    }

}

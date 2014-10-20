package com.sfr.util;

/**
  * @author      Lopamudra Choudhury <lopamudra.choudhury@lntinfotech.com>
  * @version     1.6                 (the version of the package this class was first added to)
  * @since       2012-06-14          (a date or the version number of this program)
  */


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import oracle.adf.share.logging.ADFLogger;

public class DateCalculation {

    SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss zzz yyyy");
    Map<String, String> myMap = new HashMap<String, String>();
    public static final ADFLogger log = AccessDataControl.getSFRLogger();
    AccessDataControl accessDC = new AccessDataControl();

    public DateCalculation() {
        super();
    }

    /**
     *@Description: This method calculates the start date and end date provided by the customer
     * @param selectedCriteria Holds choosen search criteria
     * @return myMap
     */
    public Map calculateDate(String selectedCriteria) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat =
            new SimpleDateFormat("E MMM dd 00:00:00 zzz yyyy");
        if (selectedCriteria.equals("SINCE_YESTERDAY")) {
            myMap.put("endDate", dateFormat.format(cal.getTime()));
            cal.add(Calendar.DATE, -1);
            myMap.put("startDate", dateFormat.format(cal.getTime()));
        } else if (selectedCriteria.equals("TODAY")) {
            myMap.put("endDate", dateFormat.format(cal.getTime()));
            // cal.add(Calendar., -1);
            myMap.put("startDate", dateFormat.format(cal.getTime()));
        } else if (selectedCriteria.equals("LAST_YEAR")) {
            myMap.put("endDate", dateFormat.format(cal.getTime()));
            cal.add(Calendar.MONTH, -12);
            myMap.put("startDate", dateFormat.format(cal.getTime()));
        } else if (selectedCriteria.equals("LAST_MONTH")) {
            myMap.put("endDate", dateFormat.format(cal.getTime()));
            cal.add(Calendar.MONTH, -1);
            myMap.put("startDate", dateFormat.format(cal.getTime()));
        } else if (selectedCriteria.equals("LAST_WEEK")) {
            myMap.put("endDate", dateFormat.format(cal.getTime()));
            cal.add(Calendar.DATE, -7);
            myMap.put("startDate", dateFormat.format(cal.getTime()));
        }
        for (Map.Entry<String, String> dt : myMap.entrySet()) {

            log.info(accessDC.getDisplayRecord() +
                     "DateCalculation.calculateDate : " + "myMapKey" +
                     dt.getKey() + "  myMapValue" + dt.getValue());
        }
        return myMap;
    }

    /**
     *
     * @param selectedDate Holds choosen date
     * @return date which is converted to UTC format (yyyy-MM-dd'T'HH:mm:ss) for SOA WSDL
     */
    public String convertDateToUTCFormat(String selectedDate) {
        String dateInUTCFormat = "";
        Date selectedDateFormat = null;
        try {
            selectedDateFormat = sdf.parse(selectedDate);
            dateInUTCFormat =
                    (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")).format(selectedDateFormat);
            log.info(accessDC.getDisplayRecord() +
                     "DateCalculation.convertDateToUTCFormat : " +
                     "dateInUTCFormat in utility convertDateToUTCFormat::" +
                     dateInUTCFormat);
        } catch (ParseException e) {
            log.info(accessDC.getDisplayRecord() +
                     "DateCalculation.convertDateToUTCFormat : " + e);
        }

        log.info(accessDC.getDisplayRecord() +
                 "DateCalculation.convertDateToUTCFormat : " +
                 "selectedDateFormat Date " + selectedDateFormat +
                 "Converted Date " + dateInUTCFormat);
        return dateInUTCFormat;
    }

    public boolean validateDateRange(String startDate, String endDate,
                                     String criteriaStr) throws ParseException {
        log.info(accessDC.getDisplayRecord() +
                 "DateCalculation.validateDateRange : " +
                 "Inside validate date range method");
        Date srtDateFormat = null;
        Date endDateFormat = null;
        srtDateFormat = sdf.parse(startDate);
        endDateFormat = sdf.parse(endDate);
        if (criteriaStr.equals("TODAY")) {
            int i = srtDateFormat.compareTo(endDateFormat);
            log.info(accessDC.getDisplayRecord() +
                     "DateCalculation.validateDateRange : " + "---VALUE---" +
                     i);
            if (i == 0)
                return true;
            else
                return false;
        } else {

            log.info(accessDC.getDisplayRecord() +
                     "DateCalculation.validateDateRange : " +
                     "srtDateFormat:: " + srtDateFormat +
                     "--endDateFormat:: " + endDateFormat +
                     "--srtDateFormat.before(endDateFormat):: " +
                     srtDateFormat.before(endDateFormat));
            return srtDateFormat.before(endDateFormat);
        }
    }

    public boolean validateDate(String date) throws ParseException {

        Date srtDateFormat = null;

        Calendar currDtCal = Calendar.getInstance();
        Date currDt = currDtCal.getTime();
        log.info(accessDC.getDisplayRecord() +
                 "DateCalculation.validateDate : " + "current date:::::::" +
                 currDt);
        srtDateFormat = sdf.parse(date);

        if (srtDateFormat.after(currDt)) {
            log.info(accessDC.getDisplayRecord() +
                     "DateCalculation.validateDate : " +
                     "srtDateFormat.after(currDt) in if:" +
                     srtDateFormat.after(currDt));
            return true;
        } else {
            log.info(accessDC.getDisplayRecord() +
                     "DateCalculation.validateDate : " +
                     "srtDateFormat.after(currDt) in else:" +
                     srtDateFormat.after(currDt));
            return false;
        }
    }

    public boolean dateCompare(String startDate, String endDate,
                               String criteriaStr) throws ParseException {

        Date srtDateFormat = null;
        Date endDateFormat = null;

        Calendar currDtCal = Calendar.getInstance();
        Date currDt = currDtCal.getTime();
        log.info(accessDC.getDisplayRecord() +
                 "DateCalculation.dateCompare : " + "current date:::::::" +
                 currDt);
        srtDateFormat = sdf.parse(startDate);
        endDateFormat = sdf.parse(endDate);

        log.info(accessDC.getDisplayRecord() +
                 "DateCalculation.dateCompare : " + "strtdate:::::::" +
                 srtDateFormat);
        if (criteriaStr.equals("DATE_RANGE")) {

            log.info(accessDC.getDisplayRecord() +
                     "DateCalculation.dateCompare : inside if for date range --  srtDateFormat.after(currDt) --" +
                     srtDateFormat.after(currDt));
            if (srtDateFormat.after(currDt))
                return false;
            else
                return true;
        } else {
            log.info(accessDC.getDisplayRecord() +
                     "DateCalculation.dateCompare : " + "srtDateFormat:: " +
                     srtDateFormat);

            return currDt.after(srtDateFormat);
        }
    }

    public boolean dateEqual(String startDate, String endDate,
                             String criteriaStr) throws ParseException {

        Date srtDateFormat = null;
        Date endDateFormat = null;

        srtDateFormat = sdf.parse(startDate);
        endDateFormat = sdf.parse(endDate);
        log.info(accessDC.getDisplayRecord() + "DateCalculation.dateEqual : " +
                 "strtdate:::::::" + srtDateFormat);
        if (criteriaStr.equals("DATE_RANGE")) {
            if (srtDateFormat.equals(endDateFormat))
                return true;
            else
                return false;
        } else {


            return srtDateFormat.before(endDateFormat);
        }
    }

    public Date convertDateValueToUTCFormat(String selectedDate) {
        log.info(accessDC.getDisplayRecord() +
                 "DateCalculation.convertDateValueToUTCFormat : " +
                 "selectedDate" + selectedDate);
        String date = "";
        Date dateInUTCFormat = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date selectedDateFormat = null;
        try {
            selectedDateFormat = sdf.parse(selectedDate);
            date =
(new SimpleDateFormat("yyyy-MM-dd'T'00:00:00")).format(selectedDateFormat);
            dateInUTCFormat = formatter.parse(date);
        } catch (ParseException e) {
            log.info(accessDC.getDisplayRecord() +
                     "DateCalculation.convertDateValueToUTCFormat : " + e);
        }

        log.info(accessDC.getDisplayRecord() +
                 "DateCalculation.convertDateValueToUTCFormat : " +
                 "dateInUTCFormat in convertDateValueToUTCFormat" +
                 dateInUTCFormat);
        return dateInUTCFormat;
    }

    public boolean dateOutOdBound(String endDate,
                                  String criteriaStr) throws ParseException {
        log.info(accessDC.getDisplayRecord() +
                 "DateCalculation.dateOutOdBound : " + "endDate bound" +
                 endDate);
        SimpleDateFormat sdf =
            new SimpleDateFormat("E MMM dd hh:mm:ss zzz yyyy");
        //         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String selectedDate1 = "Sat Jan 01 00:00:00 IST 2089";
        Date outOfBoundDate = sdf.parse(selectedDate1);

        //Date endDateFormat = null;
        Date endDateFormat = sdf.parse(endDate);
        log.info(accessDC.getDisplayRecord() +
                 "DateCalculation.dateOutOdBound : " + "endDateFormat: " +
                 endDateFormat + " -- outOfBoundDate: " + outOfBoundDate);
        //Date outOfBoundDate=new Date(2089-01-01);

        if (criteriaStr.equals("DATE_RANGE")) {
            log.info(accessDC.getDisplayRecord() +
                     "DateCalculation.dateOutOdBound : " +
                     endDateFormat.after(outOfBoundDate));

            if (endDateFormat.after(outOfBoundDate))
                return false;
            else

                return true;
        } else {

            return endDateFormat.before(outOfBoundDate);
        }
    }

    public void convertXMLGregorianCalendarToDate(Date date) throws DatatypeConfigurationException,
                                                                    ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GregorianCalendar startDateTime = new GregorianCalendar();
        XMLGregorianCalendar calendar = null;
        startDateTime.setTime(date);
        calendar =
                DatatypeFactory.newInstance().newXMLGregorianCalendar(startDateTime);

        String xcs = df.format(calendar.toGregorianCalendar().getTime());
        log.info(accessDC.getDisplayRecord() +
                 "DateCalculation.convertXMLGregorianCalendarToDate : Convert XMLGregorianCalendar To Date \n" +
                xcs);
    }


    //    public static void main(String[] args) throws DatatypeConfigurationException, ParseException {
    //        new DateCalculation().calculateDate("TODAY");
    //        new DateCalculation().convertDateToUTCFormat("Tue Dec 11 12:30:11 CET 1984");
    //        new DateCalculation().convertDateValueToUTCFormat("Tue Dec 11 12:30:11 CET 1984");
    //    }
}

package com.ragtagger.brag.utils;


import com.ragtagger.brag.exception.InvalidDateFormatException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by alpesh.rathod on 2/2/2018.
 */

public class DateFormatter {

    public static final String MM_DD_YYYY = "MM-dd-yyyy";
    public static final String MM_DD_YY = "MM-dd-yy";
    public static final String dd_MMM_YYYY_HH_MM_A = "dd-MMM-yyyy hh:mm a";
    public static final String HH_MM_A = "hh:mm a";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String dd_MMM_YYYY = "dd MMM yyyy";
    public static final String dd = "dd";
    public static final String EEEE = "EEEE";

    public static final String TIMEZONE_UTC = "UTC";
    public static final String MMM = "MMM";
    public static final String YYYY = "yyyy";

    public static String convertStringDateToStringDate(String strDate, String strFormatOld, String strFormatNew) throws
            InvalidDateFormatException {

        DateFormat formatter = new SimpleDateFormat(strFormatOld);
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            AppLogger.e(e.getMessage(),e.getCause());
            //e.printStackTrace();
            throw new InvalidDateFormatException(String.format
                    ("Invalid date format", strFormatOld, strDate));
        }
        SimpleDateFormat newFormat = new SimpleDateFormat(strFormatNew);
        return newFormat.format(date);
    }

    public static String convertDateToStringDate(Date date, String strFormatNew) {
        SimpleDateFormat newFormat = new SimpleDateFormat(strFormatNew);
        return newFormat.format(date);
    }

    public static String convertDateToStringDate(Date date, String strFormatNew, String strTimeZone) {
        SimpleDateFormat newFormat = new SimpleDateFormat(strFormatNew);
        newFormat.setTimeZone(TimeZone.getTimeZone(strTimeZone));
        return newFormat.format(date);
    }

    public static String convertMillisToStringDate(Long millis, String strFormatNew) {
        Date date = new Date(millis);
        SimpleDateFormat newFormat = new SimpleDateFormat(strFormatNew);
        return newFormat.format(date);
    }

    public static String convertMillisToStringDate(String strMillis, String strFormatNew) throws InvalidDateFormatException {
        long millis = 0;
        try {
            millis = Long.parseLong(strMillis);
        } catch (NumberFormatException e) {
            AppLogger.e(e.getMessage(),e.getCause());
            //e.printStackTrace();
            throw new InvalidDateFormatException(String.format("Invalid millisecond", strMillis));
        }

        Date date = new Date(millis);
        SimpleDateFormat newFormat = new SimpleDateFormat(strFormatNew);
        return newFormat.format(date);
    }

    public static String convertMillisToStringDate(long millis) {
        Date date = new Date(millis);
        SimpleDateFormat newFormat = new SimpleDateFormat(MM_DD_YYYY, Locale.getDefault());
        return newFormat.format(date);
    }

    public static Date convertStringDateToDate(String strDate, String strFormatOld) throws InvalidDateFormatException {
        DateFormat formatter = new SimpleDateFormat(strFormatOld);
        Date date = null;

        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            AppLogger.e(e.getMessage(),e.getCause());
            //e.printStackTrace();
            throw new InvalidDateFormatException(String.format("Invalid date format", strFormatOld, strDate));
        }
        return date;
    }

    public static Date convertStringDateToDate(String strDate) {
        DateFormat formatter = new SimpleDateFormat(MM_DD_YYYY, Locale.getDefault());
        Date date = null;

        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            AppLogger.e(e.getMessage(),e.getCause());
            //e.printStackTrace();
        }
        return date;
    }

    public static String getIndividualPartsOfDate(String strFullDate, String
            strDateFormat, String strMonthFormat) {


        // Creating a simple date formatter object for "yyyy-MM-dd" date format.
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        Date date = null;
        try {
            date = sdf.parse(strFullDate);
        } catch (ParseException e) {
            AppLogger.e(e.getMessage(),e.getCause());
            //e.printStackTrace();
            return "";
        }
        SimpleDateFormat sdfMonth = new SimpleDateFormat(strMonthFormat);
        return sdfMonth.format(date);
    }
}

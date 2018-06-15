package com.udacity.newsapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 *
 */
public final class DateUtils {

    /**
     * Allows others classes instantiates a empty DateUtils object.
     */
    public DateUtils() {
    }

    /**
     * This method when called receives a date as String format in this pattern: yyyy-MM-dd'T'HH:mm:ss
     * and return a String with this pattern: HH:mm LLL dd, yyyy
     *
     * @param dateString is the date in a string format is this pattern yyyy-MM-dd'T'HH:mm:ss
     * @return the date as a string with this new format (HH:mm LLL dd, yyyy) using the formatDate method.
     */
    public static String newsSimpleDateFormat(String dateString){
        String dateStringFormated = null;
        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            date = dateFormat.parse(dateString);
            dateStringFormated = formatDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStringFormated;
    }

    /**
     * This method receives the date (data type Date) as input parameter.
     * @param dateObject is the Date object to be formated.
     * @return a String object with the date formated according to the SimpleDateFormat method pattern: HH:mm LLL dd, yyyy.
     */
    private static String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

}

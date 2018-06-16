package com.udacity.newsapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public final class DateUtils {

    /**
     * Allows others classes instantiates a empty DateUtils object.
     */
    public DateUtils() {
    }

    /**
     * This method when called receives a date as String format in this pattern: yyyy-MM-dd'T'HH:mm:ss
     * and return a date as a String object with this pattern: HH:mm LLL dd, yyyy
     *
     * @param dateString is the date in a string format is this pattern yyyy-MM-dd'T'HH:mm:ss
     * @return the date as a string with this new format.
     */
    public static String newsSimpleDateFormat(String dateString){
        String dateStringFormated = null;
        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

        try {
            date = dateFormat.parse(dateString);
            dateStringFormated = formatDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStringFormated;
    }
    /**
     * This method receives the Date object as input parameter.
     * @param dateObject is the Date object to be formated.
     * @return a String object with the date formated according to the SimpleDateFormat method pattern: HH:mm LLL dd, yyyy.
     */
    private static String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm LLL dd, yyyy", Locale.ENGLISH);
        return dateFormat.format(dateObject);
    }


    /**
     * This method when called receives a date as String format in this pattern: yyyy-MM-dd'T'HH:mm:ss
     * and return a date as a String object with this pattern: LLL dd, yyyy
     *
     * @param dateString is the date in a string format is this pattern yyyy-MM-dd'T'HH:mm:ss
     * @return the date as a string with this new format.
     */
    public static String datePickerFormat(String dateString){
        String dateStringFormated = null;
        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
            date = dateFormat.parse(dateString);
            dateStringFormated = selectedDatePattern(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStringFormated;
    }
    private static String selectedDatePattern(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy", Locale.ENGLISH);
        return dateFormat.format(dateObject);
    }

    // Return as date as a String "yyyy-DD-dd".
    public static String buildMyDate(int year, int month, int day){

        StringBuilder stringBuilderDate = new StringBuilder();
        stringBuilderDate.append(year);
        stringBuilderDate.append("-");
        stringBuilderDate.append(month);
        stringBuilderDate.append("-");
        stringBuilderDate.append(day);

        return stringBuilderDate.toString();
    }

}

package com.accountabilibuddies.accountabilibuddies.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * Function generates the required string the the format MM/dd/yyyy
     * @param selectedYear
     * @param selectedDay
     * @param selectedMonth
     * @return
     */
    public static String createSelectedDateString (
            int selectedYear, int selectedDay, int selectedMonth) {

        String year = String.valueOf(selectedYear);
        String month = String.valueOf(selectedMonth + 1);
        String day = String.valueOf(selectedDay);

        StringBuilder builder = new StringBuilder();
        builder.append(month).append("/")
                .append(day).append("/")
                .append(year);

        return builder.toString();
    }

    /**
     * Returns the time in 12 hour format from a given date object
     * @param date
     * @return
     */
    public static String getTimeFromDate(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
        return sdf.format(date);

    }

    /**
     * Returns the Month and date in ex: Jan 21 format
     * @param date
     * @return
     */
    public static String getDayDateFromDate(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("MMM d");
        return sdf.format(date);
    }

    public static String getDate(int delay) {
        //Date in format "EEE, d MMM yyyy"
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, delay);
        Date today = calendar.getTime();
        return dateFormat.format(today);
    }

    public static String getTime(int delay) {
        //Time in format "hh:mm aaa"
        DateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, delay);
        Date today = calendar.getTime();
        return dateFormat.format(today);
    }
}

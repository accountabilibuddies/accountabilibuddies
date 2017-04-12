package com.accountabilibuddies.accountabilibuddies.util;

import java.text.SimpleDateFormat;
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


    public static String getTimeFromDate(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(date);

    }
}

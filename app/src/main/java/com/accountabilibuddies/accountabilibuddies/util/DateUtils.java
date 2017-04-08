package com.accountabilibuddies.accountabilibuddies.util;

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
}

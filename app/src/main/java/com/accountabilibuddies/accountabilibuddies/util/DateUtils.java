package com.accountabilibuddies.accountabilibuddies.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    static DateFormat formatter = new SimpleDateFormat("dd MMM yy");

    public static Date getDate(int selectedYear, int selectedDay, int selectedMonth) {

        Calendar c = Calendar.getInstance();
        c.set(selectedYear, selectedMonth, selectedDay, 0, 0);

        return c.getTime();
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
    public static String getDateFromDate(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("MMM d");
        return sdf.format(date);
    }

    /**
     * Get date in MMM d, yyyy format
     * @param date
     * @return
     */
    public static String getDate(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
        return sdf.format(date);
    }

    public static String getRelativeTimeAgo(Date postdate) {
        String relativeDate;
        long dateMillis = postdate.getTime();

        long timeDiff = (System.currentTimeMillis() - dateMillis)/1000;

        if (timeDiff < 5)
            relativeDate = "Just now";
        else if (timeDiff < 60)
            relativeDate = String.format(Locale.ENGLISH, "%ds",timeDiff);
        else if (timeDiff < 60 * 60)
            relativeDate = String.format(Locale.ENGLISH, "%dm", timeDiff / 60);
        else if (timeDiff < 60 * 60 * 24)
            relativeDate = String.format(Locale.ENGLISH, "%dh", timeDiff / (60 * 60));
        else {
            Date date = new Date(dateMillis);
            relativeDate = formatter.format(date);
        }

        return relativeDate;
    }

    public static String getRelativeTimeFuture(Date postdate) {
        String relativeDate;
        long dateMillis = postdate.getTime();

        long timeDiff = (dateMillis - System.currentTimeMillis())/1000;

        if (timeDiff < 5)
            relativeDate = "Just now";
        else if (timeDiff < 60)
            relativeDate = String.format(Locale.ENGLISH, "%ds",timeDiff);
        else if (timeDiff < 60 * 60)
            relativeDate = String.format(Locale.ENGLISH, "%dm", timeDiff / 60);
        else if (timeDiff < 60 * 60 * 24)
            relativeDate = String.format(Locale.ENGLISH, "%dh", timeDiff / (60 * 60));
        else {
            Date date = new Date(dateMillis);
            relativeDate = formatter.format(date);
        }

        return relativeDate;
    }
}

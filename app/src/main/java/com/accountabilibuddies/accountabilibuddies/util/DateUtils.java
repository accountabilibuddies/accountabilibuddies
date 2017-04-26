package com.accountabilibuddies.accountabilibuddies.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String createSelectedDateString (
            int selectedYear, int selectedDay, int selectedMonth) {

        Calendar c = Calendar.getInstance();
        c.set(selectedYear, selectedMonth, selectedDay, 0, 0);

        return getDate(c.getTime());
    }

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
     * Get date in MMM d, YYYY format
     * @param date
     * @return
     */
    public static String getDate(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
        return sdf.format(date);
    }

    public static String getDate(Calendar calendar) {
        //Date in format "EEE, d MMM yyyy"
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        Date today = calendar.getTime();
        return dateFormat.format(today);
    }

    public static String getTime(Calendar calendar) {
        //Time in format "hh:mm aaa"
        DateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
        Date today = calendar.getTime();
        return dateFormat.format(today);
    }

    /**
     * Function convers date in MMM d, YYYY format to a Calendar object
     * @param date
     * @return
     */
    public static Calendar getCalFromString(String date) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");
        try {
            cal.setTime(sdf.parse(date));
        } catch (ParseException e) {
            Log.e("ERROR", "Unable to parse date");
        }
        return cal;
    }

    public static String getRelativeTimeAgo(Date postdate) {
        String relativeDate = "";
        long dateMillis = postdate.getTime();

        long timeDiff = (System.currentTimeMillis() - dateMillis)/1000;

        if (timeDiff < 5)
            relativeDate = "Just now";
        else if (timeDiff < 60)
            relativeDate = String.format(Locale.ENGLISH, "%d sec",timeDiff);
        else if (timeDiff < 60 * 60)
            relativeDate = String.format(Locale.ENGLISH, "%d min", timeDiff / 60);
        else if (timeDiff < 60 * 60 * 24)
            relativeDate = String.format(Locale.ENGLISH, "%d hour", timeDiff / (60 * 60));
        else {
            Date date = new Date(dateMillis);
            DateFormat formatter = new SimpleDateFormat("dd MMM yy");
            relativeDate = formatter.format(date);
        }

        return relativeDate;
    }

    public static String getRelativeTimeFuture(Date postdate) {
        String relativeDate = "";
        long dateMillis = postdate.getTime();

        long timeDiff = (dateMillis - System.currentTimeMillis())/1000;

        if (timeDiff < 5)
            relativeDate = "Just now";
        else if (timeDiff < 60)
            relativeDate = String.format(Locale.ENGLISH, "%d sec",timeDiff);
        else if (timeDiff < 60 * 60)
            relativeDate = String.format(Locale.ENGLISH, "%d min", timeDiff / 60);
        else if (timeDiff < 60 * 60 * 24)
            relativeDate = String.format(Locale.ENGLISH, "%d hour", timeDiff / (60 * 60));
        else {
            Date date = new Date(dateMillis);
            DateFormat formatter = new SimpleDateFormat("dd MMM yy");
            relativeDate = formatter.format(date);
        }

        return relativeDate;
    }
}

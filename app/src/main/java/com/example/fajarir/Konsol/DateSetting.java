package com.example.fajarir.Konsol;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by WIN 8 on 27/08/2017.
 */

public class DateSetting {

    public static String getTimeStringFromDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        return dateFormat.format(date);
    }
    public static String getDateStringFromDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        return dateFormat.format(date);
    }

    public static String getLastMessageTimestamp(Date utcDate) {
        if (utcDate != null) {
            Calendar todayCalendar = Calendar.getInstance();
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTime(utcDate);

            if (todayCalendar.get(Calendar.DATE) == localCalendar.get(Calendar.DATE)) {
                return getTimeStringFromDate(utcDate);
            } else if ((todayCalendar.get(Calendar.DATE) - localCalendar.get(Calendar.DATE)) == 1) {
                return "Yesterday";
            } else {
                return getDateStringFromDate(utcDate);
            }
        } else {
            return null;
        }
    }
}

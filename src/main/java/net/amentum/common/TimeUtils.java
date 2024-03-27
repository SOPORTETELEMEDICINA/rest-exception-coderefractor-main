package net.amentum.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dev06 on 14/03/17.
 */
public class TimeUtils {
    public final static String SHORT_DATE = "dd/MM/yyyy";
    public final static String LONG_DATE = "dd/MM/yyyy HH:mm:ss";

    public static Date parseDate(String dateToParse,String pattern) throws Exception{
        DateFormat format = new SimpleDateFormat(pattern);
        return format.parse(dateToParse);
    }

    public static Date addTime(Date date, Integer addTo, Integer timeToAdd){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(addTo, timeToAdd);
        return calendar.getTime();
    }
}

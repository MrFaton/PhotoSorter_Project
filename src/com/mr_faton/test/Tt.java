package com.mr_faton.test;


import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by root on 04.05.2015.
 */
public class Tt {
    public static void main(String[] args) {
        String[] months = new DateFormatSymbols().getMonths();
        System.out.println(Arrays.toString(months));
        Calendar mCalendar = Calendar.getInstance();
        String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        System.out.println(month);
        String str = String.format("%tB", mCalendar);
        System.out.println(str);
    }
}

package com.example.demo.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dates {

    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

    public static Date today() {
        try {
            return yyyyMMdd.parse(yyyyMMdd.format(new Date()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String format(Date date) {
        return yyyyMMdd.format(date);
    }
}

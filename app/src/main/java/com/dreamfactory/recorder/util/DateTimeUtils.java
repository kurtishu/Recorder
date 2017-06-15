package com.dreamfactory.recorder.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    private static final String DATE_FORMAT_STRING_DEFAULT = "yyyy-MM-ddHH:mm:ss";

    public static String getDefaultDate() {
        return new SimpleDateFormat(DATE_FORMAT_STRING_DEFAULT, Locale.CHINA).format(new Date());
    }
}

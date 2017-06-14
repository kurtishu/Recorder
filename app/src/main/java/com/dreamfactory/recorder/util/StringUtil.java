package com.dreamfactory.recorder.util;


public class StringUtil {

    public static String formatCounting(int duration) {

        if (duration == 0) {
            return "00:00:00";
        }

        int seconds = duration % 1000;
        int minutes = duration / 1000 % 60;
        int hours = duration / 60000 % 60;

        StringBuffer sb = new StringBuffer();
        sb.append(hours > 9 ? hours : "0" + hours);
        sb.append(":");
        sb.append(minutes > 9 ? minutes : "0" + hours);
        sb.append(":");
        sb.append(seconds > 9 ? seconds : "0" + hours);
        return sb.toString();
    }
}

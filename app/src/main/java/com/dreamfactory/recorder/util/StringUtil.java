package com.dreamfactory.recorder.util;


public class StringUtil {

    public static String formatCounting(int duration) {

        if (duration == 0) {
            return "00:00:00";
        }

        int hours = duration / 3600;
        int minutes = (duration - hours * 3600) / 60;
        int seconds = duration- hours * 3600 - minutes * 60;

        StringBuffer sb = new StringBuffer();
        sb.append(hours > 9 ? hours : "0" + hours);
        sb.append(":");
        sb.append(minutes > 9 ? minutes : "0" + minutes);
        sb.append(":");
        sb.append(seconds > 9 ? seconds : "0" + seconds);
        return sb.toString();
    }

    public static boolean isEmpty(String message) {
        if(message == null || "".equals(message.trim())) {
            return true;
        }
        return false;
    }
}

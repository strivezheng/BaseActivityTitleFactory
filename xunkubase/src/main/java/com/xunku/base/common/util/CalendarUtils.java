package com.xunku.base.common.util;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created 任晓光 on 2016/10/23.
 */

public class CalendarUtils {

    Calendar calendar = Calendar.getInstance();


    // 获取去年的年份
    public static int getLastYear() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -1);
        return c.get(Calendar.YEAR);
    }

    // 获取下一年年份
    public static int getNextYear() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
        return c.get(Calendar.YEAR);
    }

    // 获取上一个月的月份
    public static int getLastMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return c.get(Calendar.MONTH) + 1;
    }

    // 获取下一个月的月份
    public static int getNextMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        return c.get(Calendar.MONTH) + 1;
    }

    private static SimpleDateFormat DATE_FORMAT_TILL_SECOND = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat DATE_FORMAT_TILL_NOMIAO = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    private static SimpleDateFormat DATE_FORMAT_TILL_HM = new SimpleDateFormat(
            "HH:mm");

    /**
     * 日期字符串转换为Date
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date strToDate(String dateStr, String format) {
        Date date = null;
        if (!TextUtils.isEmpty(dateStr)) {
            DateFormat df = new SimpleDateFormat(format);
            try {
                date = df.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static String timeNoYearNoSecond(String dataStr) {
        String str = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = sdf.parse(dataStr);
            SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd HH:mm");
            str = sdf1.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String timeYMD(String dataStr) {
        String str = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = sdf.parse(dataStr);
            str = sdf.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 日期逻辑
     *
     * @param dateStr 日期字符串
     * @return
     */
    public static String timeLogic(String dateStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.DAY_OF_MONTH);
        long now = calendar.getTimeInMillis();
        Date date = strToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
        calendar.setTime(date);
        long past = calendar.getTimeInMillis();

        // 相差的秒数
        long time = (now - past) / 1000;

        StringBuffer sb = new StringBuffer();

        if (time > 0 && time < 60) { // 1小时内
            return sb.append(time + "秒前").toString();
        } else if (time > 60 && time < 3600) {
            return sb.append(time / 60 + "分钟前").toString();
        } else if (time >= 3600 && time < 3600 * 24) {
            return sb.append(time / 3600 + "小时前").toString();
        } else if (time >= 3600 * 24 && time < 3600 * 48) {
            return sb.append("昨天").append(" ").append(dateStr.split(" ")[1]).toString();
        } else if (time >= 3600 * 48 && time < 3600 * 72) {
            return sb.append("前天").append(" ").append(dateStr.split(" ")[1]).toString();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = null;
            try {
                Date parse = sdf.parse(dateStr);
                SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd HH:mm");
                str = sdf1.format(parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}

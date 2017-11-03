package com.finley.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 郑远锋 on 2017/6/18.
 */
public class DateFormatterUtil {
    public static final String YYYY_MM_DD_HH_MM_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_ss_SS = "yyyy-MM-dd HH:mm:ss SS";
    public static final String YYYYMMDDHHMMssSS = "yyyyMMddHHmmssSS";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY = "yyyy";
    public static final String MM = "MM";
    public static final String DD = "dd";


    /**
     * 将时间转换为 20170619140809111 格式的字符串
     * @param date
     * @return
     */
    public static String getTimeStampToMsec(Date date) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMssSS);
        strDate = sdf.format(date);
        return strDate;
    }

    /**
     *  将时间转换为 2017-06-19 14:08:09 格式的字符串
     * @param date
     * @return
     */
    public static String getStandardTime(Date date) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_ss);
        strDate = sdf.format(date);
        return strDate;
    }

}
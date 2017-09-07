package cn.lonecloud.market.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by lonecloud on 2017/8/27.
 * 时间转换器
 * @author lonecloud
 */
public class DateTimeUtil {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH-mm-ss";

    /**
     * 默认采用yyyy-MM-dd HH-mm-ss将时间转换成字符串
     * @param dataTimeStr
     * @return
     */
    public static Date strToDate(String dataTimeStr) {
        return strToDate(dataTimeStr, STANDARD_FORMAT);
    }

    /**
     * 默认采用yyyy-MM-dd HH-mm-ss将字符串转换成时间
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        return dateToStr(date, STANDARD_FORMAT);
    }

    /**
     * 将时间转换成字符串
     * @param dataTimeStr
     * @param formatStr
     * @return
     */
    public static Date strToDate(String dataTimeStr, String formatStr) {
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormat.parseDateTime(dataTimeStr);
        return dateTime.toDate();
    }

    /**
     * 将字符串转换成时间
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToStr(Date date, String formatStr) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dt = new DateTime(date);
        return dt.toString(formatStr);
    }
}

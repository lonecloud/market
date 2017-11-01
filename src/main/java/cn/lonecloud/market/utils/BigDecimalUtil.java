package cn.lonecloud.market.utils;

import java.math.BigDecimal;

/**
 * Created by lonecloud on 2017/10/3.
 */
public class BigDecimalUtil {

    public static BigDecimal add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2);
    }

    public static BigDecimal sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2);
    }

    public static BigDecimal multiply(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2);
    }

    public static BigDecimal divide(double d1, double d2) {
        return divide(d1, d2, 2, BigDecimal.ROUND_HALF_UP);//四舍五入
    }

    public static BigDecimal divide(double d1, double d2, int scale, int method) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2, scale, method);//四舍五入
    }

}

package com.sixinfor.common.utils;

import java.math.BigDecimal;

/**
 * Created by malia on 2017/5/22 16:26.
 *
 * @project SixinIMAndroidProject
 * @package：com.sixinfor.common.utils
 * @describe：Float 加减乘除运算
 * @change
 * @chang time
 */

public class FloatCalculation {
    /**
     * 加法
     * @param v1
     * @param v2
     * @return
     */
    public static float add(Float v1, Float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.add(b2).floatValue();
    }

    /**
     * 减法
     * @param v1
     * @param v2
     * @return
     */
    public static float subtract(Float v1, Float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.subtract(b2).floatValue();
    }

    /**
     * 乘法
     * @param v1
     * @param v2
     * @return
     */
    public static float multiply(Float v1, Float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        BigDecimal bigDecimal = b1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.floatValue();
    }

    /**
     * 进行除法运算
     * ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
     * @param v1
     * @param v2
     * @return
     */
    public static float divide(Float v1, Float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}

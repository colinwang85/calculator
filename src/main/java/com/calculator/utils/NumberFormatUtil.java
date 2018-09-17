package com.calculator.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberFormatUtil {
    public static final Integer SCALE_STORE = 15;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.CEILING;
    private static final Integer SCALE_DISPLAY = 10;

    public static BigDecimal bigDecimalScaleFormat(String str) {
        return new BigDecimal(str).setScale(SCALE_STORE, ROUNDING_MODE);
    }

    public static BigDecimal bigDecimalScaleFormat(Double d) {
        return bigDecimalScaleFormat(d + "");
    }

    public static String bigDecimalReadScaleFormat(BigDecimal in) {
        return new BigDecimal(in.toString()).setScale(SCALE_DISPLAY, ROUNDING_MODE).stripTrailingZeros().toPlainString();
    }
}

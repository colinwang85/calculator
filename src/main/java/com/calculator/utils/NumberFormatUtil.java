package com.calculator.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberFormatUtil {
    public static final Integer SCALE_STORE = 15;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.CEILING;
    private static final Integer SCALE_DISPLAY = 10;

    public static BigDecimal bigDecimalSaveFormatter(String n) {
        return new BigDecimal(n).setScale(SCALE_STORE, ROUNDING_MODE);
    }

    public static BigDecimal bigDecimalSaveFormatter(Double d) {
        return new BigDecimal(d + "").setScale(SCALE_STORE, ROUNDING_MODE);
    }

    public static BigDecimal bigDecimalSaveFormatter(BigDecimal n) {
        return n.setScale(SCALE_STORE, ROUNDING_MODE);
    }

    public static String bigDecimalReadFormatter(BigDecimal n) {
        BigDecimal nn = new BigDecimal(n.toString());
        return nn.setScale(SCALE_DISPLAY, ROUNDING_MODE).stripTrailingZeros().toPlainString();
    }
}

package com.bot.service.util;

import java.math.BigDecimal;

public class CalculateUtils {

    private CalculateUtils(){}

    private static final BigDecimal PERCENTS = new BigDecimal(100);
    private static final int ROUND_TO = 4;


    public static float getPercent(BigDecimal from, BigDecimal total) {
        return from.divide(total, ROUND_TO, BigDecimal.ROUND_HALF_UP).multiply(PERCENTS).floatValue();
    }

}

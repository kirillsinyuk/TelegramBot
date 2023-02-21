package com.planner.service.util;

import java.math.*;

public class CalculateUtils {

    private CalculateUtils(){}

    private static final BigDecimal PERCENTS = new BigDecimal(100);
    private static final int ROUND_TO = 4;


    public static float getPercent(BigDecimal from, BigDecimal total) {
        return from.divide(total, ROUND_TO, RoundingMode.HALF_UP).multiply(PERCENTS).floatValue();
    }

    public static int getRandomInt(int max){
        int low = 0;
        return (int) ((Math.random() * max-low) + low);
    }

}

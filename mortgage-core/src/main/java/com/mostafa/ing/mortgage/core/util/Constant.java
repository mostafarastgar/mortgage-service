package com.mostafa.ing.mortgage.core.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class Constant {
    public static final BigDecimal MONTHS_IN_YEAR = new BigDecimal(12);
    public static final int ROUNDING_SCALE = 10;
    public static final BigDecimal PERCENT = new BigDecimal(100);
}

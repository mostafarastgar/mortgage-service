package com.mostafa.ing.mortgage.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ValidationCode {
    CODE_VALID("1000", false),
    CODE_INVALID_BASED_ON_HOME_VALUE("4101", true),
    CODE_INVALID_BASED_ON_MAXIMUM_ALLOWED("4102", true),
    CODE_INVALID_CURRENCY("4201", false),
    CODE_INVALID_MATURITY_PERIOD("4202", false),
    CODE_INVALID_MATURITY_PERIOD_MONTHS("4203", false),
    CODE_TOO_BIG_MATURITY_PERIOD_MONTHS("4204", false),
    CODE_INVALID_INCOME("4205", false),
    CODE_NEGATIVE_LOAD_VALUE("4206", false),
    CODE_NEGATIVE_HOME_VALUE("4207", false),
    CODE_TOO_LOW_INCOME("4208", false);

    private final String value;
    private final boolean businessError;
}

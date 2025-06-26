package com.mostafa.ing.mortgage.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ValidationCode {
    CODE_VALID("1000"),
    CODE_INVALID_BASED_ON_HOME_VALUE("4001"),
    CODE_INVALID_BASED_ON_MAXIMUM_ALLOWED("4002"),
    CODE_INVALID_CURRENCY("4002"),
    CODE_INVALID_MATURITY_PERIOD("4003"),
    CODE_INVALID_MATURITY_PERIOD_MONTHS("4004"),
    CODE_TOO_BIG_MATURITY_PERIOD_MONTHS("4005"),
    CODE_INVALID_INCOME("4006"),
    CODE_NEGATIVE_LOAD_VALUE("4007"),
    CODE_NEGATIVE_HOME_VALUE("4008"),
    CODE_TOO_LOW_INCOME("4009");

    private final String value;
}

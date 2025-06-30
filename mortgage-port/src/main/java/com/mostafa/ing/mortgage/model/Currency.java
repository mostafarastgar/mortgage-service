package com.mostafa.ing.mortgage.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This enum represents the currency used in the mortgage application.
 * At the moment, only Euro (EUR) is supported.
 */
@RequiredArgsConstructor
@Getter
public enum Currency {
    EUR("Euro");
    private final String value;
}

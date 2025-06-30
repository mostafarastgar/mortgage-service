package com.mostafa.ing.mortgage.presentation.mapper;

import com.mostafa.ing.mortgage.model.Amount;
import com.mostafa.ing.mortgage.model.Currency;
import com.mostafa.ing.mortgage.presentation.dto.AmountDto;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class AmountMapper {
    private static final int SCALE = 2;

    public static Amount toAmount(AmountDto amountDto) {
        return new Amount(
                Currency.valueOf(amountDto.currency()),
                new BigDecimal(amountDto.value()));
    }

    public static AmountDto toDto(Amount amount) {
        return new AmountDto(
                amount.currency().getValue(),
                amount.value().setScale(SCALE, RoundingMode.HALF_UP).toString());
    }
}

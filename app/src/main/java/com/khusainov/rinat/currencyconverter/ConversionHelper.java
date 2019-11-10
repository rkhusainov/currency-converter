package com.khusainov.rinat.currencyconverter;

import androidx.annotation.Nullable;

import com.khusainov.rinat.currencyconverter.model.CurrencyData;

import java.util.List;

public class ConversionHelper {
    public String convert(
            @Nullable List<CurrencyData> currencies,
            int fromCurrencyIndex,
            int toCurrencyIndex,
            @Nullable double amount) {

        CurrencyData base = currencies.get(fromCurrencyIndex);
        CurrencyData quoted = currencies.get(toCurrencyIndex);

        double result = amount * base.getValue().doubleValue() * quoted.getNominal() / quoted.getValue().doubleValue() / base.getNominal();
        return String.valueOf(result);
    }
}

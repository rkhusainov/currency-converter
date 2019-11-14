package com.khusainov.rinat.currencyconverter.presentation.utils;

import androidx.annotation.Nullable;

import com.khusainov.rinat.currencyconverter.R;
import com.khusainov.rinat.currencyconverter.data.model.CurrencyData;

import java.util.List;

public class CurrencyConverter {

    private final IResourceWrapper mResourceWrapper;

    public CurrencyConverter(IResourceWrapper resourceWrapper) {
        mResourceWrapper = resourceWrapper;
    }

    public String convert(
            @Nullable List<CurrencyData> currencies,
            int fromCurrencyIndex,
            int toCurrencyIndex,
            @Nullable double amount) {

        CurrencyData base = currencies.get(fromCurrencyIndex);
        CurrencyData quoted = currencies.get(toCurrencyIndex);

        double result = amount * base.getValue().doubleValue() * quoted.getNominal() / quoted.getValue().doubleValue() / base.getNominal();
        double roundResult = Math.round(result * 100.0) / 100.0;
        String formattedNumber = mResourceWrapper.getString(R.string.you_will_get, roundResult, quoted.getCharCode());
        return formattedNumber;
    }

    public String convertionRate(
            @Nullable List<CurrencyData> currencies,
            int fromCurrencyIndex,
            int toCurrencyIndex
    ) {
        CurrencyData base = currencies.get(fromCurrencyIndex);
        CurrencyData quoted = currencies.get(toCurrencyIndex);
        double rate = base.getValue().doubleValue() * quoted.getNominal() / quoted.getValue().doubleValue() / base.getNominal();
        double roundRate = Math.round(rate * 100.0) / 100.0;
        String formattedRate = mResourceWrapper.getString(R.string.conversion_rate, roundRate, base.getCharCode(), quoted.getCharCode());
        return formattedRate;
    }
}

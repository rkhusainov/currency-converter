package com.khusainov.rinat.currencyconverter.data.repository;

import com.khusainov.rinat.currencyconverter.data.model.CurrencyData;
import com.khusainov.rinat.currencyconverter.data.model.CurrencyResponse;
import com.khusainov.rinat.currencyconverter.presentation.utils.ApiUtils;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class CurrencyRepository {
    public Single<List<CurrencyData>> getCurrencies() {
        return ApiUtils.getApi().loadCurrencies().map(new Function<CurrencyResponse, List<CurrencyData>>() {
            @Override
            public List<CurrencyData> apply(CurrencyResponse currencyResponse) throws Exception {
                return currencyResponse.getCurrencyList();
            }
        });
    }
}

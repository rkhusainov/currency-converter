package com.khusainov.rinat.currencyconverter.data.api;

import com.khusainov.rinat.currencyconverter.data.model.CurrencyResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CurrencyApi {
    @GET("scripts/XML_daily.asp")
    Single<CurrencyResponse> loadCurrencies();
}

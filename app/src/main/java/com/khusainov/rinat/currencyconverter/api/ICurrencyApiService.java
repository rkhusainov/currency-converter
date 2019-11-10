package com.khusainov.rinat.currencyconverter.api;

import com.khusainov.rinat.currencyconverter.model.CurrencyResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ICurrencyApiService {
    @GET("scripts/XML_daily.asp")
    Single<CurrencyResponse> loadCurrencies();
}
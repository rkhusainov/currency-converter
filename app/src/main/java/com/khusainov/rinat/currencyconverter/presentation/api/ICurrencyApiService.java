package com.khusainov.rinat.currencyconverter.presentation.api;

import com.khusainov.rinat.currencyconverter.data.model.CurrencyResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ICurrencyApiService {
    @GET("scripts/XML_daily.asp")
    Single<CurrencyResponse> loadCurrencies();
}
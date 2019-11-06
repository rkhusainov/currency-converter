package com.khusainov.rinat.currencyconverter.presentation.utils;

import com.google.gson.Gson;
import com.khusainov.rinat.currencyconverter.data.api.CurrencyApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static com.khusainov.rinat.currencyconverter.BuildConfig.API_URL;

public class ApiUtils {
    private static OkHttpClient sClient;
    private static Retrofit sRetrofit;
    private static Gson sGson;
    private static CurrencyApi sApi;

    private static Retrofit getRetrofit() {
        if (sGson == null) {
            sGson = new Gson();
        }

        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return sRetrofit;
    }

    public static CurrencyApi getApi() {
        if (sApi == null) {
            sApi = getRetrofit().create(CurrencyApi.class);
        }
        return sApi;
    }
}

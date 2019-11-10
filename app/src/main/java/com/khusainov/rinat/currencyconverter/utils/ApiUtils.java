package com.khusainov.rinat.currencyconverter.utils;

import com.khusainov.rinat.currencyconverter.api.ICurrencyApiService;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.khusainov.rinat.currencyconverter.BuildConfig.API_URL;
import static retrofit2.converter.simplexml.SimpleXmlConverterFactory.create;

public class ApiUtils {
    private static Retrofit sRetrofit;
    private static ICurrencyApiService sApi;

    private static Retrofit getRetrofit() {
        Strategy strategy = new AnnotationStrategy();
        Serializer serializer = new Persister(strategy);

        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(create(serializer))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return sRetrofit;
    }

    public static ICurrencyApiService getApi() {
        if (sApi == null) {
            sApi = getRetrofit().create(ICurrencyApiService.class);
        }
        return sApi;
    }
}

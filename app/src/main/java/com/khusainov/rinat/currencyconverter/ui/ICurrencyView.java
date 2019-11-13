package com.khusainov.rinat.currencyconverter.ui;

import com.khusainov.rinat.currencyconverter.model.CurrencyData;

import java.util.List;

public interface ICurrencyView {
    void showProgress();

    void hideProgress();

    void showError();

    void showData(List<CurrencyData> currencies);

    void showConverted(String number, String rate);
}

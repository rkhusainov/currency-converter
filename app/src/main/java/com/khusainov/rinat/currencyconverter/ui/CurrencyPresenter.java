package com.khusainov.rinat.currencyconverter.ui;

import com.khusainov.rinat.currencyconverter.model.CurrencyData;
import com.khusainov.rinat.currencyconverter.model.CurrencyResponse;
import com.khusainov.rinat.currencyconverter.utils.ApiUtils;
import com.khusainov.rinat.currencyconverter.utils.CurrencyConverter;
import com.khusainov.rinat.currencyconverter.utils.IResourceWrapper;

import java.math.BigDecimal;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CurrencyPresenter {

    private Disposable mDisposable;
    private ICurrencyView mCurrencyView;
    private IResourceWrapper mIResourceWrapper;
    private CurrencyConverter mCurrencyConverter;

    public CurrencyPresenter(ICurrencyView currencyView, IResourceWrapper resourceWrapper) {
        mCurrencyView = currencyView;
        mIResourceWrapper = resourceWrapper;
        mCurrencyConverter = new CurrencyConverter(mIResourceWrapper);
    }

    public void convert(List<CurrencyData> currencies, int fromIndex, int toIndex, double amount) {
        String convertedNumber = mCurrencyConverter.convert(currencies, fromIndex, toIndex, amount);
        String rate = mCurrencyConverter.convertionRate(currencies, fromIndex, toIndex);
        mCurrencyView.showConverted(convertedNumber, rate);
    }

    public void loadCurrencies() {
        mDisposable = ApiUtils.getApi().loadCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mCurrencyView.showProgress();
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mCurrencyView.hideProgress();
                    }
                })
                .subscribe(new Consumer<CurrencyResponse>() {
                    @Override
                    public void accept(CurrencyResponse currencyResponse) throws Exception {
                        List<CurrencyData> currencies = currencyResponse.getCurrencyList();
                        addRussianCurrency(currencies);
                        mCurrencyView.showData(currencies);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mCurrencyView.showError();
                    }
                });
    }

    private void addRussianCurrency(List<CurrencyData> currencies) {
        CurrencyData currency = new CurrencyData(
                "rub_id",
                "RUS",
                1,
                "Российский рубль",
                BigDecimal.ONE
        );
        currencies.add(0, currency);
    }

    public void onHandleDetach() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}

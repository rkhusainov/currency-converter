package com.khusainov.rinat.currencyconverter.presentation;

import com.khusainov.rinat.currencyconverter.data.model.CurrencyData;
import com.khusainov.rinat.currencyconverter.data.model.CurrencyResponse;
import com.khusainov.rinat.currencyconverter.data.repository.CurrencyRepository;
import com.khusainov.rinat.currencyconverter.presentation.utils.ApiUtils;
import com.khusainov.rinat.currencyconverter.presentation.utils.CurrencyConverter;
import com.khusainov.rinat.currencyconverter.presentation.utils.IResourceWrapper;

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
    private CurrencyRepository mRepository;

    public CurrencyPresenter(ICurrencyView currencyView, CurrencyRepository currencyRepository, IResourceWrapper resourceWrapper) {
        mCurrencyView = currencyView;
        mRepository = currencyRepository;
        mIResourceWrapper = resourceWrapper;
        mCurrencyConverter = new CurrencyConverter(mIResourceWrapper);
    }

    public void convert(List<CurrencyData> currencies, int fromIndex, int toIndex, double amount) {
        String convertedNumber = mCurrencyConverter.convert(currencies, fromIndex, toIndex, amount);
        String rate = mCurrencyConverter.convertionRate(currencies, fromIndex, toIndex);
        mCurrencyView.showConverted(convertedNumber, rate);
    }

    public void loadCurrencies() {
        mDisposable = mRepository.getCurrencies()
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
                .subscribe(new Consumer<List<CurrencyData>>() {
                    @Override
                    public void accept(List<CurrencyData> currencies) throws Exception {
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

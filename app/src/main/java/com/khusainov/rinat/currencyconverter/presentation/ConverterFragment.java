package com.khusainov.rinat.currencyconverter.presentation;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.khusainov.rinat.currencyconverter.R;
import com.khusainov.rinat.currencyconverter.data.model.CurrencyResponse;
import com.khusainov.rinat.currencyconverter.presentation.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ConverterFragment extends Fragment {

    public static ConverterFragment newInstance() {
        return new ConverterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_converter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCurrencies();
    }

    @SuppressLint("CheckResult")
    private void getCurrencies() {
        ApiUtils.getApi().loadCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CurrencyResponse>() {
                    @Override
                    public void accept(CurrencyResponse currencyResponse) throws Exception {
                        currencyResponse.getCurrencyList();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(), getResources().getString(R.string.response_error), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

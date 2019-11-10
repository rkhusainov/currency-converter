package com.khusainov.rinat.currencyconverter.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.khusainov.rinat.currencyconverter.R;
import com.khusainov.rinat.currencyconverter.model.CurrencyData;
import com.khusainov.rinat.currencyconverter.model.CurrencyResponse;
import com.khusainov.rinat.currencyconverter.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ConverterFragment extends Fragment {

    private Spinner mSpinnerFrom;
    private Spinner mSpinnerTo;
    private EditText mFromAmount;
    private TextView mResultTextView;
    private TextView mConversionRateTextView;
    private Button mConvertButton;
    private View mLoadingView;
    private List<CurrencyData> mCurrencyDataList = new ArrayList<>();
    private Disposable mDisposable;

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
        mSpinnerFrom = view.findViewById(R.id.spinner_from);
        mSpinnerTo = view.findViewById(R.id.spinner_to);
        mFromAmount = view.findViewById(R.id.from_amount);
        mResultTextView = view.findViewById(R.id.tv_result);
        mConversionRateTextView = view.findViewById(R.id.tv_conversion_rate);
        mConvertButton = view.findViewById(R.id.btn_convert);
        mLoadingView = view.findViewById(R.id.loading_view);
        loadCurrencies();

        initSpinnerFrom();
        initSpinnerTo();

        mConvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initSpinnerFrom() {
        mSpinnerFrom.setAdapter(new CurrencyAdapter(mCurrencyDataList));
        mSpinnerFrom.setOnItemSelectedListener(new OnCurrencySelectedListener());
    }

    private void initSpinnerTo() {
        mSpinnerTo.setAdapter(new CurrencyAdapter(mCurrencyDataList));
        mSpinnerTo.setOnItemSelectedListener(new OnCurrencySelectedListener());
    }


    public void loadCurrencies() {
        mDisposable = ApiUtils.getApi().loadCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CurrencyResponse>() {
                    @Override
                    public void accept(CurrencyResponse currencyResponse) throws Exception {
                        mCurrencyDataList.addAll(currencyResponse.getCurrencyList());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(), "Error request", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    private class OnCurrencySelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getContext(), mSpinnerFrom.getSelectedItemPosition() + " " + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}

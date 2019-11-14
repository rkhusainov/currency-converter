package com.khusainov.rinat.currencyconverter.presentation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.khusainov.rinat.currencyconverter.data.model.CurrencyData;
import com.khusainov.rinat.currencyconverter.presentation.utils.ResourceWrapper;

import java.util.ArrayList;
import java.util.List;

public class ConverterFragment extends Fragment implements ICurrencyView {

    private Spinner mSpinnerFrom;
    private Spinner mSpinnerTo;
    private EditText mFromAmount;
    private TextView mResultTextView;
    private TextView mConversionRateTextView;
    private Button mConvertButton;
    private View mLoadingView;
    private View mContentView;
    private View mErrorView;
    private List<CurrencyData> mCurrencies = new ArrayList<>();

    private CurrencyPresenter mCurrencyPresenter;

    public static ConverterFragment newInstance() {
        return new ConverterFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrencyPresenter = new CurrencyPresenter(this, new ResourceWrapper(getResources()));
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
        mContentView = view.findViewById(R.id.content_layout);
        mErrorView = view.findViewById(R.id.errorView);

        mConvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mFromAmount.getText().toString().isEmpty()) {
                    updateResult();
                    hideKeyBoard();
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.enter_amount_of_currency), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateResult() {
        mCurrencyPresenter.convert(
                mCurrencies,
                mSpinnerFrom.getSelectedItemPosition(),
                mSpinnerTo.getSelectedItemPosition(),
                Double.parseDouble(mFromAmount.getText().toString()));
    }

    private void hideKeyBoard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onStart() {
        super.onStart();
        mCurrencyPresenter.loadCurrencies();
    }

    private void initSpinnerFrom() {
        mSpinnerFrom.setOnItemSelectedListener(new OnCurrencySelectedListener());
        mSpinnerFrom.setAdapter(new CurrencyAdapter(mCurrencies));
    }

    private void initSpinnerTo() {
        mSpinnerTo.setOnItemSelectedListener(new OnCurrencySelectedListener());
        mSpinnerTo.setAdapter(new CurrencyAdapter(mCurrencies));
        mSpinnerTo.setSelection(1);
    }


    @Override
    public void showProgress() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        mContentView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData(List<CurrencyData> currencies) {
        mCurrencies.addAll(currencies);
        initSpinnerFrom();
        initSpinnerTo();
        mContentView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showConverted(String number, String rate) {
        mResultTextView.setText(number);
        mConversionRateTextView.setText(rate);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCurrencyPresenter.onHandleDetach();
    }

    private class OnCurrencySelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (!mFromAmount.getText().toString().isEmpty()) {
                updateResult();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}

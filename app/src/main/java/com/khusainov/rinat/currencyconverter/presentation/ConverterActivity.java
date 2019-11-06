package com.khusainov.rinat.currencyconverter.presentation;

import androidx.fragment.app.Fragment;

import com.khusainov.rinat.currencyconverter.common.SingleFragmentActivity;

public class ConverterActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return ConverterFragment.newInstance();
    }
}

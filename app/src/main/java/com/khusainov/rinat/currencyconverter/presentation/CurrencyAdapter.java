package com.khusainov.rinat.currencyconverter.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.khusainov.rinat.currencyconverter.data.model.CurrencyData;

import java.util.List;

public class CurrencyAdapter extends BaseAdapter {

    private List<CurrencyData> mCurrencies;

    public CurrencyAdapter(List<CurrencyData> currencies) {
        mCurrencies = currencies;
    }

    @Override
    public int getCount() {
        return mCurrencies.size();
    }

    @Override
    public CurrencyData getItem(int position) {
        return mCurrencies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            CurrencyHolder currencyHolder = new CurrencyHolder(convertView);
            convertView.setTag(currencyHolder);
        }

        CurrencyData currency = getItem(position);
        CurrencyHolder currencyHolder = (CurrencyHolder) convertView.getTag();
        if (currency != null) {
            currencyHolder.mCurrencyName.setText(currency.getName());
        }
        return convertView;
    }

    private class CurrencyHolder {
        private TextView mCurrencyName;

        private CurrencyHolder(View view) {
            mCurrencyName = view.findViewById(android.R.id.text1);
        }
    }
}

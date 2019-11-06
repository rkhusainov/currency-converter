package com.khusainov.rinat.currencyconverter.data.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "ValCurs", strict = false)
public class CurrencyResponse {

    @ElementList(inline = true)
    private List<CurrencyData> mCurrencyList;

    public List<CurrencyData> getCurrencyList() {
        return new ArrayList<>(mCurrencyList);
    }
}

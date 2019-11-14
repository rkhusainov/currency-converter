package com.khusainov.rinat.currencyconverter.presentation.utils;

import androidx.annotation.StringRes;

public interface IResourceWrapper {
    /**
     * Получить строку
     *
     * @param resId идентификатор строки
     */
    String getString(@StringRes int resId);

    /**
     * Получить форматированную строку
     *
     * @param resId      идентификатор строки
     * @param formatArgs аргументы форматирования
     */
    String getString(@StringRes int resId, Object... formatArgs);
}

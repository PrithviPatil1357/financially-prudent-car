package com.prithvipatil.financiallyprudentcar.Util;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatUtil {
    public static String currencyWithChosenLocalisation(double value, Locale locale) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        return nf.format(value);
    }
}

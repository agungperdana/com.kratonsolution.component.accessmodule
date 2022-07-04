package com.kratonsolution.belian.access.module.router;

import com.google.common.base.Strings;

public class NumberHelper {

    public static final int toInt(String numberString) {

        if(!Strings.isNullOrEmpty(numberString))
            return Integer.parseInt(numberString);

        return 0;
    }
}

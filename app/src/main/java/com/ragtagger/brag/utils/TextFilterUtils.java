package com.ragtagger.brag.utils;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by alpesh.rathod on 3/16/2018.
 */

public class TextFilterUtils {

    public static InputFilter getAlphaNumericFilter(){
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence src, int i, int i1, Spanned spanned, int i2, int i3) {
                if (src.equals("")) { // for backspace
                    return src;
                }
                if (src.toString().matches("^[a-zA-Z0-9]*$")) {
                    return src;
                }
                return "";
            }
        };
    }

    public static InputFilter getLengthFilter(int length){
        return new InputFilter.LengthFilter(length);
    }
}

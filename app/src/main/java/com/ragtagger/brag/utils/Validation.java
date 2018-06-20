package com.ragtagger.brag.utils;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nikhil.vadoliya on 25-09-2017.
 */


public class Validation {

    public static boolean isEmailValid(EditText editText) {
        String regExpn = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        CharSequence inputStr = editText.getText().toString().trim();

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();

//        CharSequence inputStr = editText.getText().toString().trim();
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(inputStr).matches();
    }

    public static boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public static boolean isEmpty(TextView etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    //Don't trim in password field because space first character or last character may be space
    public static boolean isEmptyPassword(EditText etText) {
        return etText.getText().toString().length() == 0;
    }

    public static boolean isValidMobileNum(EditText mEditText) {

        return mEditText.getText().toString().trim().length() == 10;
    }

    public static boolean isValidGST(EditText mEditText) {

        return mEditText.getText().toString().trim().length() == 15;
    }

    public static boolean isValidPincode(EditText mEditText) {

        return mEditText.getText().toString().trim().length() == 6 && !mEditText.getText().toString().trim().startsWith("0");
    }

    public static boolean isTwoStringSameFormPassword(EditText mEditTextFirst, EditText mEditTextSecond) {
        return (mEditTextFirst.getText().toString()).equals(mEditTextSecond.getText().toString());
    }


}

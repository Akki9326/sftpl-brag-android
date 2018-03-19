package com.ragtagger.brag.utils;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.widget.EditText;

/**
 * Created by nikhil.vadoliya on 25-09-2017.
 */


public class Validation {

    public static boolean isEmailValid(EditText editText) {
//        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@" + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|" + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
//        CharSequence inputStr = editText.getText().toString().trim();
//
//        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(inputStr);
//
//        return matcher.matches();

        CharSequence inputStr = editText.getText().toString().trim();
        return android.util.Patterns.EMAIL_ADDRESS.matcher(inputStr).matches();
    }

    public static boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    //Don't trim in password field because space first character or last character may be space
    public static boolean isEmptyPassword(EditText etText) {
        return etText.getText().toString().length() == 0;
    }

    public static boolean isValidMobileNum(EditText mEditText) {

        return mEditText.getText().toString().trim().length() == 10 ? true : false;
    }

    public static boolean isValidGST(EditText mEditText) {

        return mEditText.getText().toString().trim().length() == 15 ? true : false;
    }

    public static boolean isValidPincode(EditText mEditText) {

        return mEditText.getText().toString().trim().length() == 6 ? true : false;
    }


}

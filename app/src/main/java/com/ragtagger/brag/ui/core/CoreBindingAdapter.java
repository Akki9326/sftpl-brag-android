package com.ragtagger.brag.ui.core;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.BindingAdapter;
import android.support.design.widget.BottomNavigationView;
import android.widget.EditText;
import android.widget.ImageView;

import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.views.RoundView;

/**
 * Created by nikhil.vadoliya on 14-02-2018.
 */

/*
 * This class use in Binding in Recycleview
 * -it is provide attr in xml file
 * for example : if u want load image by picasso than make one method loadImage and give annotation @BindingAdapter
 * -In u write in BindingAdapter that u in xml file here bind:imageUrl use in xml file for load image from url*/
public class CoreBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        Utility.imageSetCenterInside(imageView.getContext(), url, imageView);
    }


    @BindingAdapter("onNavigationItemSelected")
    public static void setOnNavigationItemSelectedListener(
            BottomNavigationView view, BottomNavigationView.OnNavigationItemSelectedListener listener) {
        view.setOnNavigationItemSelectedListener(listener);
    }

    @BindingAdapter("bind:text")
    public static void bindEditText(EditText editText, CharSequence value) {
        if (!editText.getText().toString().equals(value.toString())) {
            editText.setText(value);
        }
    }

    @BindingAdapter("bind:roundColor")
    public static void roundColor(RoundView imageView, int color) {
        imageView.setColorFromResource(color);
    }


}

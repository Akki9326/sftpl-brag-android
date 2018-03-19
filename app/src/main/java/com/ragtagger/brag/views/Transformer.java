package com.ragtagger.brag.views;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.support.v4.view.ViewPager;
import android.view.View;

import com.ragtagger.brag.R;

/**
 * Created by nikhil.vadoliya on 21-11-2017.
 */


public class Transformer implements ViewPager.PageTransformer{
    @Override
    public void transformPage(View page, float position) {
        if(position >= -1 && position <= 1){
            page.findViewById(R.id.imageView).setTranslationX(-position * page.getWidth() / 2);
        } else {
            page.setAlpha(1);
        }
    }
}

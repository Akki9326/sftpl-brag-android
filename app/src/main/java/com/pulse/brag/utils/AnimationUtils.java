package com.pulse.brag.utils;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by nikhil.vadoliya on 19-02-2018.
 */


public class AnimationUtils {

    private AnimationUtils() {
        throw new Error("Do not need instantiate!");
    }

    public static void setHolderAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", -150, 150);
        ;
    }


}

package com.pulse.brag.utils;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.animation.AnimatorSet;
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

    public static void setHolderAnimation(View view, boolean b) {
        AnimatorSet animatorSet=new AnimatorSet();
        ObjectAnimator animatorSlide = ObjectAnimator.ofFloat(view, "translationY", b ? 150 :-150, 1f);
        animatorSlide.setDuration(500);
        animatorSlide.start();
        ObjectAnimator animatorFabe = ObjectAnimator.ofFloat(view, "alpha", b ? 0 : 1, 1f);
        animatorFabe.setDuration(500);
        animatorFabe.start();
        animatorSet.playTogether(animatorFabe,animatorSlide);
        animatorSet.start();
    }


}

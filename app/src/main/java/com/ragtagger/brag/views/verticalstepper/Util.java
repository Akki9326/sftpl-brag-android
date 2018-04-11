package com.ragtagger.brag.views.verticalstepper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import java.util.concurrent.atomic.AtomicInteger;

class Util {

    static int getThemeColor( Context context, int attr ) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(
            typedValue.data,
            new int[] { attr } );
        int color = a.getColor( 0, 0 );
        a.recycle();
        return color;
    }
}

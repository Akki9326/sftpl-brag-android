package com.ragtagger.brag.views;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.ragtagger.brag.R;

/**
 * Created by nikhil.vadoliya on 08-12-2017.
 */


public class RoundView extends View {

    private int height = 0;
    private int width = 0;

    private Paint paint;
    private int color;

    private Rect rectangle;
    Context context;

    public RoundView(Context context) {
        super(context);
        init(context, null);
    }

    public RoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {

        this.context = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.RoundView, 0, 0);
        try {
            color = a.getColor(R.styleable.RoundView_round_color, Color.WHITE);
//            color = a.getInteger(R.styleable.RoundView_round_color, Color.WHITE);
        } finally {
            a.recycle();
        }
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = Math.min(canvas.getWidth(), canvas.getHeight() / 2);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paint);
    }


    public void setColorHex(String color) {
        paint.setColor(Color.parseColor(color));
        invalidate();
    }

    public void setColorFromResource(int color) {
        paint.setColor(ContextCompat.getColor(context, color));
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        height = 10;
        width = 10;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}

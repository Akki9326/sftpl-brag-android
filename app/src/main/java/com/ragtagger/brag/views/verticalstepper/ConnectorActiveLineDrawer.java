package com.ragtagger.brag.views.verticalstepper;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.content.res.ResourcesCompat;

import com.ragtagger.brag.R;
import com.ragtagger.brag.utils.Utility;

/**
 * Created by nikhil.vadoliya on 04-04-2018.
 */


public class ConnectorActiveLineDrawer {
    private final Paint paint1 = new Paint();
    private final Paint paint2 = new Paint();

    private final RectF line1 = new RectF();
    private final RectF line2 = new RectF();


    void adjust(Context context, int width, int height) {
        int pink = ResourcesCompat.getColor(
                context.getResources(),
                R.color.pink,
                null);

        int gray = ResourcesCompat.getColor(
                context.getResources(),
                R.color.gray,
                null);


        paint1.setColor(pink);
        line1.left = Utility.dpToPx(context, 19.5f);
        line1.right = Utility.dpToPx(context, 21.5f);
        line1.top = Utility.dpToPx(context, 20);
        line1.bottom = Utility.dpToPx(context, height / 2);;
        paint2.setColor(gray);
        line2.left = Utility.dpToPx(context, 19.5f);
        line2.right = Utility.dpToPx(context, 21.5f);
        line2.top = Utility.dpToPx(context, height / 3);
        line2.bottom = height;
    }

    void draw(Canvas canvas) {
        canvas.drawRect(line1, paint1);
        canvas.drawRect(line2, paint2);
    }
}

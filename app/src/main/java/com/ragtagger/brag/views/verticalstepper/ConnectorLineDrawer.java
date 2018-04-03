package com.ragtagger.brag.views.verticalstepper;

import android.content.Context;
import android.graphics.*;
import android.support.v4.content.res.ResourcesCompat;

import com.ragtagger.brag.R;
import com.ragtagger.brag.utils.Utility;

class ConnectorLineDrawer {
    private final Paint paint = new Paint();

    private final RectF line = new RectF();



    void setPaintColor(Context context, int color) {
        int pink = ResourcesCompat.getColor(
                context.getResources(),
                color,
                null);
        paint.setColor(pink);
    }

    void adjust(Context context, int width, int height) {
        line.left = Utility.dpToPx(context, 19.5f);
        line.right = Utility.dpToPx(context, 20.5f);
        line.top = Utility.dpToPx(context, 15);
        line.bottom = height;
    }

    void draw(Canvas canvas) {
        canvas.drawRect(line, paint);
    }
}

package com.ragtagger.brag.views.verticalstepper;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ragtagger.brag.R;

public class VerticalStepperItemCircleView extends FrameLayout {
    private Context context;
    private ImageView mImgActiveOrComplete, mImgInactive;

    public VerticalStepperItemCircleView(Context context) {
        super(context);
        initialize(context);
    }

    public VerticalStepperItemCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public VerticalStepperItemCircleView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VerticalStepperItemCircleView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context);
    }

    private void initialize(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(
                R.layout.vertical_stepper_view_item_circle,
                this,
                true);

        mImgInactive = findViewById(R.id.vertical_stepper_view_item_circle_inactive);
        mImgActiveOrComplete = findViewById(R.id.vertical_stepper_view_item_circle_complete_active);
    }

    public void setBackgroundActive() {
        /*GradientDrawable drawable = (GradientDrawable) ContextCompat
                .getDrawable(
                        getContext(),
                        R.drawable.vertical_stepper_view_item_circle_active);
        drawable.setColor(Util
                .getThemeColor(getContext(), R.attr.colorAccent));*/
        setBackgroundResource(R.drawable.vertical_stepper_view_item_circle_active);
    }

    public void setBackgroundComplete() {

        setBackgroundResource(R.drawable.vertical_stepper_view_item_circle_complate);

    }

    public void setBackgroundInactive() {
        setBackgroundResource(R.drawable.vertical_stepper_view_item_circle_inactive);
    }

    public void setInActive() {
        mImgActiveOrComplete.setVisibility(View.GONE);
        mImgInactive.setVisibility(View.VISIBLE);
        mImgInactive.setBackgroundResource(R.drawable.ic_check_white_18dp);
    }

    public void setIconCheck() {
        setIconResource(R.drawable.ic_check_white_18dp);
    }

    public void setIconEdit() {
        setIconResource(R.drawable.ic_download);
    }

    public void setIconResource(int id) {
        mImgInactive.setVisibility(View.GONE);
        mImgActiveOrComplete.setVisibility(View.VISIBLE);
        mImgActiveOrComplete.setImageResource(id);
    }
}

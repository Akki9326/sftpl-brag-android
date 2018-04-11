package com.ragtagger.brag.views.verticalstepper;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ragtagger.brag.R;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;

public class VerticalStepperItemView extends FrameLayout {
    public static int STATE_INACTIVE = 0;

    public static int STATE_ACTIVE = 1;

    public static int STATE_COMPLETE = 2;

    private boolean showConnectorLine = true;


    private VerticalStepperItemCircleView circle;


    private LinearLayout wrapper;
    private View mViewLine;
    private TextView title;

    private TextView date;
    private int connectionColor;

    private int state = STATE_INACTIVE;
    Context context;

    public VerticalStepperItemView(Context context) {
        super(context);
        this.context = context;
        initialize(context);
    }

    public VerticalStepperItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public VerticalStepperItemView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VerticalStepperItemView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context);
    }

    private void initialize(Context context) {
        setWillNotDraw(false);
        setClipChildren(false);
        setClipToPadding(false);

        int padding = (int) Utility.dpToPx(context, 8);
        setPadding(padding, 0, padding, 0);

        LayoutInflater.from(context).inflate(
                R.layout.item_list_stepper_order_status,
                this,
                true);

        circle = findViewById(R.id.vertical_stepper_view_item_circle);
        wrapper = findViewById(R.id.vertical_stepper_view_item_wrapper);
        title = findViewById(R.id.vertical_stepper_view_item_title);
        date = findViewById(R.id.vertical_stepper_view_item_summary);
        mViewLine = findViewById(R.id.view_line);

        Utility.applyTypeFace(getContext(), wrapper);
//        contentWrapper = (FrameLayout) findViewById(R.id.vertical_stepper_view_item_content_wrapper);

    }

    public void setShowConnectorLine(boolean show) {
        showConnectorLine = show;
//        setMarginBottom();
        if (showConnectorLine) {
            switch (Constants.OrderStatusStepper.values()[getConnectionColor()]) {
                case ACTIVE:
                    mViewLine.setBackgroundResource(R.drawable.vertical_connection_stepper_active);
                    break;

                case COMPLETE:
                    mViewLine.setBackgroundResource(R.drawable.vertical_connection_stepper_complete);
                    break;
                case INACTIVE:
                    mViewLine.setBackgroundResource(R.drawable.vertical_connection_stepper_inactive);
                    break;
            }
            mViewLine.setVisibility(VISIBLE);
        } else {
            mViewLine.setVisibility(GONE);
        }

    }

    public boolean getShowConnectorLine() {
        return showConnectorLine;
    }

    public int getConnectionColor() {
        return this.connectionColor;
    }

    public void setConnectionColor(int connectionColor) {
        this.connectionColor = connectionColor;
    }

    public void setInActiveCircle() {

        if (state != STATE_COMPLETE)
            circle.setInActive();
    }

    public void setTitle(CharSequence title) {
        this.title.setText(title);
    }

    public void setDate(CharSequence date) {
        this.date.setText(date);

        if (state == STATE_COMPLETE)
            this.date.setVisibility(VISIBLE);
    }


    public void setState(int state) {
        this.state = state;

        if (state == STATE_INACTIVE)
            setStateInactive();
        else if (state == STATE_ACTIVE)
            setStateActive();
        else
            setStateComplete();
    }

    public int getState() {
        return state;
    }

    private void setStateInactive() {
       /* circle.setIconEdit();
        setMarginBottom(false);
        circle.setInActive();
        circle.setBackgroundInactive();
        title.setTextColor(ResourcesCompat.getColor(
                getResources(),
                R.color.vertical_stepper_view_black_38,
                null));
        date.setVisibility(View.VISIBLE);*/
        setStateActive();
    }

    private void setStateActive() {
        circle.setIconEdit();
//        setMarginBottom();
        circle.setInActive();
        circle.setBackgroundActive();
        title.setTextColor(ResourcesCompat.getColor(
                getResources(),
                R.color.gray,
                null));
        date.setVisibility(View.VISIBLE);
    }

    private void setStateComplete() {
//        setMarginBottom();
        circle.setBackgroundComplete();
        circle.setIconCheck();

        title.setTextColor(ResourcesCompat.getColor(
                getResources(),
                R.color.text_black,
                null));
        date.setVisibility(TextUtils.isEmpty(date.getText()) ? View.GONE
                : View.VISIBLE);
    }

    private void setMarginBottom() {
        MarginLayoutParams params = (MarginLayoutParams) wrapper
                .getLayoutParams();


        /*if (!getShowConnectorLine())
            params.bottomMargin = 0;
        else
            params.bottomMargin = (int) Utility.dpToPx(getContext(), 40);*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(
            int width,
            int height,
            int oldWidth,
            int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

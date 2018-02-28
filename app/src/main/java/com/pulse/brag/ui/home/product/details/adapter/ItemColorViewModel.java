package com.pulse.brag.ui.home.product.details.adapter;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

/**
 * Created by alpesh.rathod on 2/20/2018.
 */

public class ItemColorViewModel extends BaseObservable {

    private Context mContext;
    private int mPosition;
    private String mColor;
    private boolean mSelected;
    private ItemColorViewModelListener mListener;

    public ItemColorViewModel(Context context, int position,String color,boolean selected, ItemColorViewModelListener listener) {
        this.mContext = context;
        this.mPosition=position;
        this.mColor = color;
        this.mSelected=selected;
        this.mListener = listener;
    }

    public int getPosition() {
        return mPosition;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        this.mColor = color;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        this.mSelected = selected;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public void onColorClick(View v) {
        mListener.OnColorClick(mColor,mPosition);
    }

    public interface ItemColorViewModelListener {
        void OnColorClick(String color,int pos);
    }
}

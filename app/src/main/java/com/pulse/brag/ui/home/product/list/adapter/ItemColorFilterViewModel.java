package com.pulse.brag.ui.home.product.list.adapter;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;

import com.pulse.brag.ui.home.product.list.adapter.model.ColorModel;
import com.pulse.brag.views.RoundView;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class ItemColorFilterViewModel extends BaseObservable {
    private Context mContext;
    private int mPosition;
    private ColorModel mColorModel;
    private ItemColorFilterViewModel.ItemColorViewModelListener mListener;

    public ItemColorFilterViewModel(Context context, int position, ColorModel colorModel, ItemColorFilterViewModel.ItemColorViewModelListener listener) {
        this.mContext = context;
        this.mPosition = position;
        this.mListener = listener;
        this.mColorModel = colorModel;
    }

    public int getPosition() {
        return mPosition;
    }

    public String getFilterColor() {
        return mColorModel.getColor();
    }

    public void setColorModel(ColorModel colorModel) {
        this.mColorModel = colorModel;
    }

    public boolean isSelected() {
        return mColorModel.isSelected();
    }


    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public void onColorClick(View v) {
        mListener.OnColorClick(mColorModel, mPosition);
    }

    @BindingAdapter({"bind:colorFilter"})
    public static void bindColor(RoundView view, String color) {
        view.setColor(color);
    }

    public interface ItemColorViewModelListener {
        void OnColorClick(ColorModel model, int pos);
    }
}

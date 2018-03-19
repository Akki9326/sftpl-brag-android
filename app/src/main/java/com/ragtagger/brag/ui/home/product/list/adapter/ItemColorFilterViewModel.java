package com.ragtagger.brag.ui.home.product.list.adapter;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;

import com.ragtagger.brag.data.model.datas.DataFilter;
import com.ragtagger.brag.views.RoundView;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class ItemColorFilterViewModel extends BaseObservable {
    private Context mContext;
    private int mPosition;
    private DataFilter.ColorCode mColorModel;
    private ItemColorFilterViewModel.ItemColorViewModelListener mListener;

    public ItemColorFilterViewModel(Context context, int position, DataFilter.ColorCode colorModel, ItemColorFilterViewModel.ItemColorViewModelListener listener) {
        this.mContext = context;
        this.mPosition = position;
        this.mListener = listener;
        this.mColorModel = colorModel;
    }

    public String getTitle() {
        return mColorModel.getColor().length() >= 3 ? mColorModel.getColor().substring(0, 3) + mColorModel.getVariant() : mColorModel.getColor() + mColorModel.getVariant();
    }

    public int getPosition() {
        return mPosition;
    }

    public String getFilterColor() {
        return mColorModel.getHash();
    }

    public void setColorModel(DataFilter.ColorCode colorModel) {
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
        view.setColorHex(color);
    }

    public interface ItemColorViewModelListener {
        void OnColorClick(DataFilter.ColorCode model, int pos);
    }
}

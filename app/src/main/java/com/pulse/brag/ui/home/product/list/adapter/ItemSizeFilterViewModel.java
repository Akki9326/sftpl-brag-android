package com.pulse.brag.ui.home.product.list.adapter;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;

import com.pulse.brag.ui.home.product.list.adapter.model.ColorModel;
import com.pulse.brag.ui.home.product.list.adapter.model.SizeModel;
import com.pulse.brag.views.RoundView;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class ItemSizeFilterViewModel extends BaseObservable {

    private Context mContext;
    private int mPosition;
    private SizeModel mmSizeModel;
    private ItemSizeFilterViewModel.ItemSizeViewModelListener mListener;

    public ItemSizeFilterViewModel(Context context, int position, SizeModel colorModel, ItemSizeFilterViewModel.ItemSizeViewModelListener listener) {
        this.mContext = context;
        this.mPosition = position;
        this.mListener = listener;
        this.mmSizeModel = colorModel;
    }

    public int getPosition() {
        return mPosition;
    }

    public String getFilterSize() {
        return mmSizeModel.getSize();
    }

    public void setSizeModel(SizeModel sizeModel) {
        this.mmSizeModel = sizeModel;
    }

    public boolean isSelected() {
        return mmSizeModel.isSelected();
    }


    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public void onSizeClick(View v) {
        mListener.OnSizeClick(mmSizeModel, mPosition);
    }

    public interface ItemSizeViewModelListener {
        void OnSizeClick(SizeModel model, int pos);
    }
}

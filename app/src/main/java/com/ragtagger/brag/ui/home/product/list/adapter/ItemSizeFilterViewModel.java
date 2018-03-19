package com.ragtagger.brag.ui.home.product.list.adapter;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.ragtagger.brag.data.model.datas.DataFilter;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class ItemSizeFilterViewModel extends BaseObservable {

    private Context mContext;
    private int mPosition;
    private DataFilter.SizeCode mmSizeModel;
    private ItemSizeFilterViewModel.ItemSizeViewModelListener mListener;

    public ItemSizeFilterViewModel(Context context, int position, DataFilter.SizeCode colorModel, ItemSizeFilterViewModel.ItemSizeViewModelListener listener) {
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

    public void setSizeModel(DataFilter.SizeCode sizeModel) {
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
        void OnSizeClick(DataFilter.SizeCode model, int pos);
    }
}

package com.ragtagger.brag.ui.home.product.list.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.datas.DataFilter;
import com.ragtagger.brag.databinding.ItemListSizeFilterBinding;
import com.ragtagger.brag.ui.core.CoreViewHolder;
import com.ragtagger.brag.ui.home.product.list.filter.listener.IFilterSelectedListener;
import com.ragtagger.brag.utils.Utility;

import java.util.List;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class SizeFilterAdapter extends RecyclerView.Adapter<SizeFilterAdapter.ItemViewHolder> {

    Activity mActivity;
    List<DataFilter.SizeCode> mListSize;
    IFilterSelectedListener mOnProductSizeSelectListener;

    public SizeFilterAdapter(Activity mActivity, List<DataFilter.SizeCode> mListSize, IFilterSelectedListener onProductSizeSelectListener) {
        this.mActivity = mActivity;
        this.mListSize = mListSize;
        mOnProductSizeSelectListener = onProductSizeSelectListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListSizeFilterBinding itemListSizeFilterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_list_size_filter, parent, false);
        return new SizeFilterAdapter.ItemViewHolder(itemListSizeFilterBinding);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.bindSizeData(position, mListSize.get(position));
    }

    @Override
    public int getItemCount() {
        return mListSize.size();
    }

    public void resetSize(List<DataFilter.SizeCode> list) {
        if (mListSize != null && mListSize.size() > 0)
            mListSize.clear();

        mListSize.addAll(list);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends CoreViewHolder implements ItemSizeFilterViewModel.ItemSizeViewModelListener {

        ItemListSizeFilterBinding itemBinding;
        int pos;

        public ItemViewHolder(ItemListSizeFilterBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;
            Utility.applyTypeFace(this.itemView.getContext(), itemBinding.baseLayout);
        }

        void bindSizeData(int pos, DataFilter.SizeCode sizeModel) {
            this.pos = pos;
            itemBinding.setItemModel(new ItemSizeFilterViewModel(itemView.getContext(), pos, sizeModel, this));
            itemBinding.executePendingBindings();

        }

        @Override
        public void onBind(int position) {

        }

        @Override
        public void OnSizeClick(DataFilter.SizeCode model, int pos) {
            mListSize.get(pos).setSelected(!model.isSelected());
            mOnProductSizeSelectListener.onSelectedSize(model.isSelected(), model);
            notifyItemChanged(pos);
        }
    }
}

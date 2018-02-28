package com.pulse.brag.ui.home.product.list.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pulse.brag.R;
import com.pulse.brag.databinding.ItemListSizeFilterBinding;
import com.pulse.brag.callback.IOnProductSizeSelectListener;
import com.pulse.brag.ui.core.CoreViewHolder;
import com.pulse.brag.ui.home.product.list.adapter.model.SizeModel;
import com.pulse.brag.utils.Utility;

import java.util.List;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class SizeFilterAdapter extends RecyclerView.Adapter<SizeFilterAdapter.ItemViewHolder> {

    Activity mActivity;
    List<SizeModel> mListSize;
    IOnProductSizeSelectListener mOnProductSizeSelectListener;

    public SizeFilterAdapter(Activity mActivity, List<SizeModel> mListSize, IOnProductSizeSelectListener onProductSizeSelectListener) {
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

    public void resetSize(List<SizeModel> list) {
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

        void bindSizeData(int pos, SizeModel sizeModel) {
            this.pos = pos;
            itemBinding.setItemModel(new ItemSizeFilterViewModel(itemView.getContext(), pos, sizeModel, this));

        }

        @Override
        public void onBind(int position) {

        }

        @Override
        public void OnSizeClick(SizeModel model, int pos) {
            mListSize.get(pos).setSelected(!model.isSelected());
            notifyItemChanged(pos);
        }
    }
}

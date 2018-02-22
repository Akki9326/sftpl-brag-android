package com.pulse.brag.ui.home.product.list.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.databinding.ItemListColorFilterBinding;
import com.pulse.brag.databinding.ItemListSizeFilterBinding;
import com.pulse.brag.interfaces.OnProductSizeSelectListener;
import com.pulse.brag.ui.core.CoreViewHolder;
import com.pulse.brag.ui.home.product.list.adapter.model.ColorModel;
import com.pulse.brag.ui.home.product.list.adapter.model.SizeModel;
import com.pulse.brag.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class SizeFilterAdapter extends RecyclerView.Adapter<SizeFilterAdapter.ItemViewHolder> {

    Activity mActivity;
    List<SizeModel> mListSize;
    OnProductSizeSelectListener mOnProductSizeSelectListener;

    public SizeFilterAdapter(Activity mActivity, List<SizeModel> mListSize, OnProductSizeSelectListener onProductSizeSelectListener) {
        this.mActivity = mActivity;
        this.mListSize = mListSize;
        mOnProductSizeSelectListener = onProductSizeSelectListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*View view1 = LayoutInflater.from(mActivity).inflate(R.layout.item_list_size_filter, null);
        ItemViewHolder viewHolder1 = new ItemViewHolder(view1);
        return viewHolder1;*/

        ItemListSizeFilterBinding itemListSizeFilterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_list_size_filter, parent, false);
        return new SizeFilterAdapter.ItemViewHolder(itemListSizeFilterBinding);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.bindSizeData(position,mListSize.get(position));

        /*holder.mTxtSize.setText(mListSize.get(position));
        holder.mView.setSelected(mSeletedList.get(position));
        Utility.applyTypeFace(mActivity, holder.mBaseLayout);
        holder.mView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mOnProductSizeSelectListener.OnSelectedSize(position);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mListSize.size();
    }



    public class ItemViewHolder extends CoreViewHolder implements ItemSizeFilterViewModel.ItemSizeViewModelListener {
        /*TextView mTxtSize;
        View mView;
        LinearLayout mBaseLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mBaseLayout = (LinearLayout) itemView.findViewById(R.id.base_layout);
            Utility.applyTypeFace(mActivity, mBaseLayout);
            mTxtSize = (TextView) itemView.findViewById(R.id.textView_size);
        }*/

        ItemListSizeFilterBinding itemBinding;
        int pos;

        public ItemViewHolder(ItemListSizeFilterBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;
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

package com.ragtagger.brag.ui.home.product.list.adapter;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ragtagger.brag.callback.IOnItemClickListener;
import com.ragtagger.brag.callback.IOnProductButtonClickListener;
import com.ragtagger.brag.data.model.datas.DataProductList;
import com.ragtagger.brag.databinding.ItemGridProductBinding;
import com.ragtagger.brag.ui.core.CoreViewHolder;
import com.ragtagger.brag.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 03-10-2017.
 */


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {


    Context mContext;
    List<DataProductList.Products> mListRespones;
    private IOnItemClickListener mOnItemClickListener;
    private IOnProductButtonClickListener mAddButtonClickListener;

    private static final int LIST_ITEM = 0;
    private static final int GRID_ITEM = 1;
    boolean isSwitchView = true;


    public ProductListAdapter(Context mContext, IOnItemClickListener mOnItemClickListener, IOnProductButtonClickListener mAddButtonClickListener, List<DataProductList.Products> mListRespones) {
        this.mContext = mContext;
        this.mOnItemClickListener = mOnItemClickListener;
        this.mListRespones = new ArrayList<>();
        this.mListRespones = mListRespones;
        this.mAddButtonClickListener = mAddButtonClickListener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGridProductBinding itemGridProductBinding = ItemGridProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(itemGridProductBinding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.bindProduct(position, mListRespones.get(position));
    }

    @Override
    public int getItemCount() {
        return mListRespones.size();
    }

    public void reset() {
        if (mListRespones != null)
            mListRespones.clear();
        notifyDataSetChanged();
    }

    public void addList(List<DataProductList.Products> dataList) {
        mListRespones.addAll(dataList);
        notifyDataSetChanged();
    }

    public List<DataProductList.Products> getList() {
        return mListRespones;
    }

    public DataProductList.Products getItem(int pos) {
        if (mListRespones != null && pos < mListRespones.size())
            return mListRespones.get(pos);
        else return null;
    }

    public class MyViewHolder extends CoreViewHolder implements ItemProductViewModel.ItemProductViewModelListener {

        ItemGridProductBinding mItemProductBinding;

        public MyViewHolder(ItemGridProductBinding itemGridProductBinding) {
            super(itemGridProductBinding.baseLayout);
            this.mItemProductBinding = itemGridProductBinding;
            Utility.applyTypeFace(mContext, mItemProductBinding.baseLayout);
        }


        void bindProduct(int posistion, DataProductList.Products product) {
            mItemProductBinding.setViewModel(new ItemProductViewModel(itemView.getContext(), product, posistion, this));
            mItemProductBinding.executePendingBindings();
        }

        @Override
        public void onBind(int position) {

        }

        @Override
        public void OnAddClick(int position, DataProductList.Products product) {
            mAddButtonClickListener.OnAddListener(position);
        }

        @Override
        public void OnCartClick(int position, DataProductList.Products product) {
            mAddButtonClickListener.OnCartClick(position);
        }

        @Override
        public void OnListItemClick(int position, DataProductList.Products product) {
            mOnItemClickListener.onItemClick(position);
        }
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (isSwitchView) {
//            return LIST_ITEM;
//        } else {
//            return GRID_ITEM;
//        }
//    }

    public boolean toggleItemViewType() {
        isSwitchView = !isSwitchView;
        return isSwitchView;
    }
}

package com.ragtagger.brag.ui.home.adapter;


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

import com.ragtagger.brag.databinding.ItemGridCategoryBinding;
import com.ragtagger.brag.callback.IOnItemClickListener;
import com.ragtagger.brag.data.model.datas.DataCategoryList;
import com.ragtagger.brag.ui.core.CoreViewHolder;
import com.ragtagger.brag.ui.home.category.CategoryItemViewModel;
import com.ragtagger.brag.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 14-02-2018.
 */


public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    List<DataCategoryList.Category> listRespones = new ArrayList<>();
    Context context;
    IOnItemClickListener onItemClickListener;

    public CategoryListAdapter(Context context, List<DataCategoryList.Category> listRespones
            , IOnItemClickListener onItemClickListener) {
        this.listRespones = listRespones;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemGridCategoryBinding itemGridCategoryBinding = ItemGridCategoryBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemGridCategoryBinding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(position, listRespones.get(position));

    }

    @Override
    public int getItemCount() {
        return (null != listRespones ? listRespones.size() : 0);
    }


    public class ViewHolder extends CoreViewHolder implements CategoryItemViewModel.OnItemClickListener {

        ItemGridCategoryBinding itemBinding;

        public ViewHolder(ItemGridCategoryBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;

            Utility.applyTypeFace(context, itemBinding.baseLayout);
        }

        public void onBind(int position, DataCategoryList.Category responseData) {
            itemBinding.setViewModel(new CategoryItemViewModel(itemView.getContext(), position, responseData, this));
            itemBinding.executePendingBindings();
        }

        @Override
        public void onBind(final int position) {
        }


        @Override
        public void onItemClick(int position, DataCategoryList.Category responeData) {
            onItemClickListener.onItemClick(position);
        }
    }


}

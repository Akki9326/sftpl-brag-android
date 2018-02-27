package com.pulse.brag.adapters;


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
import android.view.View;
import android.view.ViewGroup;

import com.pulse.brag.databinding.ItemGridCategoryBinding;
import com.pulse.brag.callback.IOnItemClickListener;
import com.pulse.brag.pojo.datas.CategoryListResponseData;
import com.pulse.brag.ui.core.CoreViewHolder;
import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 14-02-2018.
 */


public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    List<CategoryListResponseData> listRespones = new ArrayList<>();
    Context context;
    IOnItemClickListener onItemClickListener;
    public CategoryListAdapter(Context context, List<CategoryListResponseData> listRespones
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
        holder.onBind(position);


    }

    @Override
    public int getItemCount() {
        return (null != listRespones ? listRespones.size() : 0);
    }


    public class ViewHolder extends CoreViewHolder /*implements CategoryListResponseData.ItemViewModelListener */ {

        ItemGridCategoryBinding itemBinding;

        public ViewHolder(ItemGridCategoryBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;

            Utility.applyTypeFace(context, itemBinding.baseLayout);
        }

        @Override
        public void onBind(final int position) {
            //new CategoryListResponseData(this);
            CategoryListResponseData listResponseData = listRespones.get(position);
            itemBinding.setViewModel(listResponseData);
            //itemBinding.setViewModel(listRespones.get(position));
            itemBinding.executePendingBindings();
            itemBinding.baseLayout.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }


    }




}

package com.ragtagger.brag.ui.home.adapter;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ragtagger.brag.callback.IOnItemClickListener;
import com.ragtagger.brag.data.model.datas.DataCategoryList;
import com.ragtagger.brag.data.model.datas.DataProductList;
import com.ragtagger.brag.data.model.datas.DataSubCategory;
import com.ragtagger.brag.databinding.ItemGridCategoryBinding;
import com.ragtagger.brag.databinding.ItemGridSubCategoryBinding;
import com.ragtagger.brag.ui.core.CoreViewHolder;
import com.ragtagger.brag.ui.home.category.CategoryItemViewModel;
import com.ragtagger.brag.ui.home.subcategory.SubCategoryItemViewModel;
import com.ragtagger.brag.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 14-02-2018.
 */
public class SubCategoryListAdapter extends RecyclerView.Adapter<SubCategoryListAdapter.ViewHolder> {

    List<DataSubCategory.DataObject> listRespones = new ArrayList<>();
    Context context;
    IOnItemClickListener onItemClickListener;

    public SubCategoryListAdapter(Context context, List<DataSubCategory.DataObject> listRespones
            , IOnItemClickListener onItemClickListener) {
        this.listRespones = listRespones;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }


    public void addList(List<DataSubCategory.DataObject> dataList, int start, int count) {
        listRespones.addAll(dataList);
        notifyItemRangeInserted(start, count);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemGridSubCategoryBinding itemGridCategoryBinding = ItemGridSubCategoryBinding
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


    public class ViewHolder extends CoreViewHolder implements SubCategoryItemViewModel.OnItemClickListener {

        ItemGridSubCategoryBinding itemBinding;
        boolean isClicked = false;

        public ViewHolder(ItemGridSubCategoryBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;
            Utility.applyTypeFace(context, itemBinding.baseLayout);
        }

        public void onBind(int position, DataSubCategory.DataObject responseData) {
            itemBinding.setViewModel(new SubCategoryItemViewModel(itemView.getContext(), position, responseData, this));
            itemBinding.executePendingBindings();
        }

        @Override
        public void onBind(final int position) {
        }

        private void enabledClick() {
            if (isClicked)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isClicked = false;
                    }
                }, 250);
        }

        @Override
        public void onItemClick(int position, DataSubCategory.DataObject responeData) {
            if (!isClicked) {
                isClicked = true;
                onItemClickListener.onItemClick(position);
                enabledClick();
            }
        }
    }

}

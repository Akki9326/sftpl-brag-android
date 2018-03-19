package com.ragtagger.brag.ui.order.adapter;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ragtagger.brag.R;
import com.ragtagger.brag.databinding.ItemListOrderBinding;
import com.ragtagger.brag.data.model.datas.DataMyOrder;
import com.ragtagger.brag.ui.core.CoreViewHolder;
import com.ragtagger.brag.ui.order.MyOrderItemViewModel;
import com.ragtagger.brag.utils.Utility;

import java.util.Collections;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 21-02-2018.
 */


public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.ViewHolder> {

    List<DataMyOrder> listRespones = Collections.emptyList();
    OnItemClick onItemClick;
    Activity activity;

    public MyOrderListAdapter(Activity activity, List<DataMyOrder> listRespones
            , OnItemClick onItemClick) {
        this.listRespones = listRespones;
        this.activity = activity;
        this.onItemClick = onItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListOrderBinding itemListOrderBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_list_order, parent, false);
        return new ViewHolder(itemListOrderBinding);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindCartData(position, listRespones.get(position));
        Log.i("Adapter", "onBindViewHolder: holder-" + listRespones.get(position).getTotalAmount());

    }

    @Override
    public int getItemCount() {
        return listRespones.size();
    }


    public interface OnItemClick {
        void onItemClick(int position, DataMyOrder responeData);
    }

    public class ViewHolder extends CoreViewHolder implements MyOrderItemViewModel.OnItemClick {

        ItemListOrderBinding itemBind;
        int pos;

        public ViewHolder(ItemListOrderBinding itemView) {
            super(itemView.getRoot());
            this.itemBind = itemView;
            Utility.applyTypeFace(activity, itemBind.baseLayout);
        }


        void bindCartData(int position, DataMyOrder responeData) {
            pos = position;
            itemBind.setViewModel(new MyOrderItemViewModel(itemView.getContext(), position, responeData, this));
            itemBind.executePendingBindings();
        }

        @Override
        public void onBind(int position) {
        }


        @Override
        public void onItemClick(int position, DataMyOrder responeData) {
            onItemClick.onItemClick(position, responeData);
        }
    }
}

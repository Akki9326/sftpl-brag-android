package com.pulse.brag.adapters;


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
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pulse.brag.R;
import com.pulse.brag.databinding.ItemListOrderBinding;
import com.pulse.brag.data.model.datas.MyOrderListResponeData;
import com.pulse.brag.ui.core.CoreViewHolder;
import com.pulse.brag.ui.order.MyOrderItemViewModel;
import com.pulse.brag.utils.Utility;

import java.util.Collections;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 21-02-2018.
 */


public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.ViewHolder> {

    List<MyOrderListResponeData> listRespones = Collections.emptyList();
    OnItemClick onItemClick;
    Activity activity;

    public MyOrderListAdapter(Activity activity, List<MyOrderListResponeData> listRespones
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

    }

    @Override
    public int getItemCount() {
        return listRespones.size();
    }


    public interface OnItemClick {
        void onItemClick(int position, MyOrderListResponeData responeData);
    }

    public class ViewHolder extends CoreViewHolder implements MyOrderItemViewModel.OnItemClick {

        ItemListOrderBinding itemBind;
        int pos;

        public ViewHolder(ItemListOrderBinding itemView) {
            super(itemView.getRoot());
            this.itemBind = itemView;
            Utility.applyTypeFace(activity, itemBind.baseLayout);
        }


        void bindCartData(int position, MyOrderListResponeData responeData) {
            pos = position;
            if (itemBind.getViewModel() == null) {
                itemBind.setViewModel(new MyOrderItemViewModel(itemView.getContext(), position, responeData, this));
            } else {
                itemBind.getViewModel().setMyOrderData(responeData);
            }
        }

        @Override
        public void onBind(int position) {
        }


        @Override
        public void onItemClick(int position, MyOrderListResponeData responeData) {
            onItemClick.onItemClick(position, responeData);
        }
    }
}

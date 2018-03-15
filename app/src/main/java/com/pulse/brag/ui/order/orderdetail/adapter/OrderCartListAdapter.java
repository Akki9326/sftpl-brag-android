package com.pulse.brag.ui.order.orderdetail.adapter;


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
import com.pulse.brag.data.model.datas.DataCart;
import com.pulse.brag.databinding.ItemListOrderedCartBinding;
import com.pulse.brag.ui.core.CoreViewHolder;
import com.pulse.brag.ui.order.orderdetail.OrderCartItemViewMobel;
import com.pulse.brag.utils.Utility;

import java.util.Collections;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 14-03-2018.
 */


public class OrderCartListAdapter extends RecyclerView.Adapter<OrderCartListAdapter.ViewHolder> {

    List<DataCart> listRespones = Collections.emptyList();
    Activity activity;

    public OrderCartListAdapter(Activity activity, List<DataCart> listRespone) {
        this.listRespones = listRespone;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListOrderedCartBinding itemListOrderCartBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_list_ordered_cart, parent, false);
        return new ViewHolder(itemListOrderCartBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindCartData(position, listRespones.get(position));
    }

    @Override
    public int getItemCount() {
        return listRespones.size();
    }

    public class ViewHolder extends CoreViewHolder {

        ItemListOrderedCartBinding itemBind;
        int pos;

        public ViewHolder(ItemListOrderedCartBinding itemView) {
            super(itemView.getRoot());
            this.itemBind = itemView;
            Utility.applyTypeFace(activity, itemBind.baseLayout);
        }


        void bindCartData(int position, DataCart responeData) {
            pos = position;
            itemBind.setViewModel(new OrderCartItemViewMobel(itemView.getContext(), responeData));
            itemBind.executePendingBindings();
        }

        @Override
        public void onBind(int position) {
        }
    }
}

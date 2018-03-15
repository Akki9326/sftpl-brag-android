package com.pulse.brag.ui.cart.adapter;


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
import com.pulse.brag.databinding.ItemListPlaceOrderBinding;
import com.pulse.brag.data.model.datas.DataCart;
import com.pulse.brag.ui.cart.placeorder.PlaceOrderItemViewModel;
import com.pulse.brag.ui.core.CoreViewHolder;
import com.pulse.brag.utils.Utility;

import java.util.Collections;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 21-02-2018.
 */


public class PlaceOrderCartListAdapter extends RecyclerView.Adapter<PlaceOrderCartListAdapter.ViewHolder> {

    List<DataCart> listRespones = Collections.emptyList();
    OnItemClick onItemClick;
    Activity activity;

    public PlaceOrderCartListAdapter(Activity activity, List<DataCart> listRespones, OnItemClick onItemClick) {
        this.listRespones = listRespones;
        this.activity = activity;
        this.onItemClick = onItemClick;
    }


    public void qtyUpdate(int position, int qty) {
        listRespones.get(position).setQuantity(qty);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListPlaceOrderBinding itemListPlaceOrder = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_list_place_order, parent, false);
        return new ViewHolder(itemListPlaceOrder);
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
        public void onQtyClick(int position, DataCart responeData);
    }

    public class ViewHolder extends CoreViewHolder implements PlaceOrderItemViewModel.OnItemClick {

        ItemListPlaceOrderBinding itemBind;
        int pos;

        public ViewHolder(ItemListPlaceOrderBinding itemView) {
            super(itemView.getRoot());
            this.itemBind = itemView;
            Utility.applyTypeFace(activity, itemBind.baseLayout);
        }


        void bindCartData(int position, DataCart responeData) {
            pos = position;
            itemBind.setPlaceOrderData(new PlaceOrderItemViewModel(itemView.getContext(), position, responeData, this));
            itemBind.executePendingBindings();
        }

        @Override
        public void onBind(int position) {
        }


        @Override
        public void onQtyClick(int position, DataCart responeData) {
            onItemClick.onQtyClick(pos, responeData);
        }
    }
}

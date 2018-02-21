package com.pulse.brag.adapters;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pulse.brag.R;
import com.pulse.brag.databinding.ItemListOrderBinding;
import com.pulse.brag.pojo.datas.MyOrderListResponeData;
import com.pulse.brag.ui.core.CoreViewHolder;
import com.pulse.brag.ui.myorder.MyOrderItemViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 21-02-2018.
 */


public class MyOrderList1Adapter extends RecyclerView.Adapter<MyOrderList1Adapter.ViewHolder> {

    List<MyOrderListResponeData> listRespones = Collections.emptyList();
    OnItemClick onItemClick;

    public MyOrderList1Adapter(List<MyOrderListResponeData> listRespones
            , OnItemClick onItemClick) {
        this.listRespones = listRespones;
        this.onItemClick = onItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListOrderBinding itemListCartBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_list_order, parent, false);
        return new ViewHolder(itemListCartBinding);

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
        public void onItemClick(int position, MyOrderListResponeData responeData);
    }

    public class ViewHolder extends CoreViewHolder implements MyOrderItemViewModel.OnItemClick {

        ItemListOrderBinding itemBind;
        int pos;

        public ViewHolder(ItemListOrderBinding itemView) {
            super(itemView.getRoot());
            this.itemBind = itemView;
        }


        void bindCartData(int position, MyOrderListResponeData responeData) {
            pos = position;
            if (itemBind.getViewModle() == null) {
                itemBind.setViewModle(new MyOrderItemViewModel(itemView.getContext(), position, responeData, this));
            } else {
                itemBind.getViewModle().setMyOrderData(responeData);
            }
        }

        @Override
        public void onBind(int position) {
        }


        @Override
        public void onItemClick(int position, MyOrderListResponeData responeData) {

        }
    }
}

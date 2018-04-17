package com.ragtagger.brag.ui.cart.adapter;


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

import com.ragtagger.brag.R;
import com.ragtagger.brag.databinding.ItemListCartBinding;
import com.ragtagger.brag.data.model.datas.DataCart;
import com.ragtagger.brag.ui.cart.CartItemViewModel;
import com.ragtagger.brag.ui.core.CoreViewHolder;
import com.ragtagger.brag.utils.Utility;

import java.util.Collections;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 19-02-2018.
 */


public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    List<DataCart> listRespones = Collections.emptyList();
    OnItemClick onItemClick;
    Activity activity;

    public CartListAdapter(Activity activity, List<DataCart> listRespones
            , OnItemClick onItemClick) {
        this.listRespones = listRespones;
        this.activity = activity;
        this.onItemClick = onItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListCartBinding itemListCartBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_list_cart, parent, false);
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


    public void removeItem(DataCart responeData) {
        int pos = listRespones.indexOf(responeData);
        listRespones.remove(responeData);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, listRespones.size());

    }

    public void addAll(List<DataCart> list) {
        listRespones.addAll(list);
        notifyDataSetChanged();
    }

    public void qtyUpdate(int position, int qty) {
        listRespones.get(position).setQuantity(qty);
    }

    public interface OnItemClick {
        void onDeleteItem(int position, DataCart responeData);

        void onQtyClick(int position, DataCart responeData);
    }

    public class ViewHolder extends CoreViewHolder implements CartItemViewModel.OnItemClick {

        ItemListCartBinding itemBind;
        int pos;

        public ViewHolder(ItemListCartBinding itemView) {
            super(itemView.getRoot());
            this.itemBind = itemView;
            //AnimationUtils.setHolderAnimatcateion(itemView.getRoot());
            Utility.applyTypeFace(activity, itemBind.baseLayout);
        }


        void bindCartData(int position, DataCart responeData) {
            pos = position;
            itemBind.setCartData(new CartItemViewModel(itemView.getContext(), position, responeData, this));
            itemBind.executePendingBindings();
        }

        @Override
        public void onBind(int position) {
        }


        @Override
        public void onDeleteItem(int position, DataCart responeData) {
            onItemClick.onDeleteItem(pos, responeData);
        }

        @Override
        public void onQtyClick(int position, DataCart responeData) {
            onItemClick.onQtyClick(pos, responeData);
        }
    }
}

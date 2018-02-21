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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pulse.brag.R;
import com.pulse.brag.databinding.ItemListCartBinding;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.pojo.datas.CartListResponeData;
import com.pulse.brag.ui.cart.CartItemViewModel;
import com.pulse.brag.ui.core.CoreViewHolder;

import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by nikhil.vadoliya on 19-02-2018.
 */


public class CartList1Adapter extends RecyclerView.Adapter<CartList1Adapter.ViewHolder> {

    List<CartListResponeData> listRespones = Collections.emptyList();
    OnItemClick onItemClick;

    public CartList1Adapter(List<CartListResponeData> listRespones
            , OnItemClick onItemClick) {
        this.listRespones = listRespones;
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


    public void removeItem(CartListResponeData responeData) {
        int pos = listRespones.indexOf(responeData);
        listRespones.remove(responeData);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, listRespones.size());

    }
    public void addAll(List<CartListResponeData> list){
        listRespones.addAll(list);
        notifyDataSetChanged();
    }

    public void qtyUpdate(int position, int qty) {
        listRespones.get(position).setQty(qty);
    }

    public interface OnItemClick {
        public void onDeleteItem(int position, CartListResponeData responeData);
        public void onQtyClick(int position, CartListResponeData responeData);
    }

    public class ViewHolder extends CoreViewHolder implements CartItemViewModel.OnItemClick {

        ItemListCartBinding itemBind;
        int pos;

        public ViewHolder(ItemListCartBinding itemView) {
            super(itemView.getRoot());
            this.itemBind = itemView;
        }


        void bindCartData(int position, CartListResponeData responeData) {
            pos = position;
            if (itemBind.getCartData() == null) {
                itemBind.setCartData(new CartItemViewModel(itemView.getContext(), position, responeData, this));
            } else {
                itemBind.getCartData().setCartData(responeData);
            }
        }

        @Override
        public void onBind(int position) {
//            CartListResponeData responeData = listRespones.get(position);
//            itemBind.setViewModel(responeData);
//            itemBind.executePendingBindings();
        }


        @Override
        public void onDeleteItem(int position, CartListResponeData responeData) {
            onItemClick.onDeleteItem(pos, responeData);
        }

        @Override
        public void onQtyClick(int position, CartListResponeData responeData) {
            onItemClick.onQtyClick(pos, responeData);
        }
    }
}

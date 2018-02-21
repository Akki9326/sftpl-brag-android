package com.pulse.brag.ui.home.product.list.adapter;

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

import com.pulse.brag.databinding.ItemGridProductBinding;
import com.pulse.brag.ui.core.CoreViewHolder;
import com.pulse.brag.interfaces.OnAddButtonClickListener;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.pojo.DummeyDataRespone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 03-10-2017.
 */


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {


    Context mContext;
    List<DummeyDataRespone> mListRespones;
    private OnItemClickListener mOnItemClickListener;
    private OnAddButtonClickListener mAddButtonClickListener;

    private static final int LIST_ITEM = 0;
    private static final int GRID_ITEM = 1;
    boolean isSwitchView = true;


    public ProductListAdapter(Context mContext, OnItemClickListener mOnItemClickListener, OnAddButtonClickListener mAddButtonClickListener, List<DummeyDataRespone> mListRespones) {
        this.mContext = mContext;
        this.mOnItemClickListener = mOnItemClickListener;
        this.mListRespones = new ArrayList<>();
        this.mListRespones = mListRespones;
        this.mAddButtonClickListener = mAddButtonClickListener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGridProductBinding itemGridProductBinding = ItemGridProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(itemGridProductBinding);

        /*View view;
        MyViewHolder viewHolder1;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_grid_product, null);
        viewHolder1 = new MyViewHolder(view);
        return viewHolder1;*/

//        if (viewType == LIST_ITEM) {
//            view = LayoutInflater.from(mContext).inflate(R.layout.item_list_product, null);
//        } else {
//            view = LayoutInflater.from(mContext).inflate(R.layout.item_grid_product, null);
//        }
//        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.bindProduct(position,mListRespones.get(position));

        /*DummeyDataRespone data = mListRespones.get(position);

        Utility.applyTypeFace(mContext, holder.mLinearLayout);
        Utility.imageSet(mContext, data.getAvatar(), holder.mImageView);
        holder.mTxtProdName.setText(data.getFirst_name() + " " + data.getLast_name());
        holder.mTxtProdPrice.setText("Rs 500.00");
//        holder.mTxtProdPrice.setText(data.getPriceWithSym(mContext));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(position);
            }
        });

        holder.mLinerAdd.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mAddButtonClickListener.OnAddListener(position);

            }
        });*/
    }


    @Override
    public int getItemCount() {
        return mListRespones.size();
    }

    public class MyViewHolder extends CoreViewHolder implements ItemProductViewModel.ItemProductViewModelListener {

        ItemGridProductBinding mItemProductBinding;

        public MyViewHolder(ItemGridProductBinding itemGridProductBinding) {
            super(itemGridProductBinding.baseLayout);
            this.mItemProductBinding=itemGridProductBinding;
        }

        /*View mView;
        ImageView mImageView;
        TextView mTxtProdName, mTxtProdPrice;
        LinearLayout mLinearLayout;
        LinearLayout mLinerAdd;

        public MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mImageView = (ImageView) itemView.findViewById(R.id.imageView_product);
            mTxtProdName = (TextView) itemView.findViewById(R.id.textview_product_name);
            mTxtProdPrice = (TextView) itemView.findViewById(R.id.textView_price);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.base_layout);
            mLinerAdd = (LinearLayout) itemView.findViewById(R.id.linear_add);
        }*/

        void bindProduct(int posistion,DummeyDataRespone product){
            if(mItemProductBinding.getViewModel() == null){
                mItemProductBinding.setViewModel(new ItemProductViewModel(itemView.getContext(),product,posistion,this));
                mItemProductBinding.executePendingBindings();
            }else {
                mItemProductBinding.getViewModel().setProduct(product);
            }
        }

        @Override
        public void onBind(int position) {

        }

        @Override
        public void OnAddClick(int position, DummeyDataRespone product) {
            mAddButtonClickListener.OnAddListener(position);
        }

        @Override
        public void OnListItemClick(int position, DummeyDataRespone product) {
            mOnItemClickListener.onItemClick(position);
        }
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (isSwitchView) {
//            return LIST_ITEM;
//        } else {
//            return GRID_ITEM;
//        }
//    }

    public boolean toggleItemViewType() {
        isSwitchView = !isSwitchView;
        return isSwitchView;
    }
}

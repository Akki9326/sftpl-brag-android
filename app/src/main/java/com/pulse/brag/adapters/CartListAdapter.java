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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.interfaces.OnQtyClickListener;
import com.pulse.brag.pojo.datas.CartListResponeData;
import com.pulse.brag.callback.OnSingleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 04-12-2017.
 */


public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {

    Context mContext;
    List<CartListResponeData> mListRespones;
    OnItemClickListener mOnItemClickListener;
    OnQtyClickListener mOnQtyClickListener;

    public CartListAdapter(Context mContext, List<CartListResponeData> mListRespones,
                           OnItemClickListener onItemClickListener, OnQtyClickListener onQtyClickListener) {
        this.mContext = mContext;
        this.mListRespones = new ArrayList<>();
        this.mListRespones = mListRespones;
        mOnItemClickListener = onItemClickListener;
        mOnQtyClickListener = onQtyClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_cart, null);
        MyViewHolder viewHolder1 = new MyViewHolder(view);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final CartListResponeData data = mListRespones.get(position);
        Utility.imageSet(mContext, data.getProduct_image(), holder.mImgProduct);
        holder.mTxtProductName.setText(data.getProduct_name());
        holder.mTxtSize.setText(data.getSize());
        holder.mTxtPrice.setText(data.getPriceWithSym());
        holder.mImgColor.setImageBitmap(Utility.getRoundBitmap(data.getColor(), true));
        holder.mTxtQty.setText("" + data.getQty());

        holder.mImgDelete.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mOnItemClickListener.onItemClick(position);
            }
        });
        holder.mTxtQty.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mOnQtyClickListener.onQtyClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mListRespones.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mBaseLayout;
        private ImageView mImgProduct, mImgColor;
        private TextView mTxtProductName, mTxtSize, mTxtPrice, mTxtQty;
        private ImageView mImgDelete;

        public MyViewHolder(View itemView) {
            super(itemView);

            mBaseLayout = (LinearLayout) itemView.findViewById(R.id.base_layout);
            mImgProduct = (ImageView) itemView.findViewById(R.id.imageview_product);
            mImgDelete = (ImageView) itemView.findViewById(R.id.imageview_delete);
            mTxtProductName = (TextView) itemView.findViewById(R.id.textview_product_name);
            mTxtPrice = (TextView) itemView.findViewById(R.id.textView_price);
            mTxtSize = (TextView) itemView.findViewById(R.id.textView_size);
            mImgColor = (ImageView) itemView.findViewById(R.id.imageview_color);
            mTxtQty = (TextView) itemView.findViewById(R.id.textview_qty);
            Utility.applyTypeFace(mContext, mBaseLayout);

        }
    }
}

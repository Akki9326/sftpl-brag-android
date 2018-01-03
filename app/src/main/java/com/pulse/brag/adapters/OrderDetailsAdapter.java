package com.pulse.brag.adapters;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.pojo.datas.OrderListResponeData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 02-01-2018.
 */


public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderViewHolder> {

    Activity activity;
    List<OrderListResponeData> mList;

    public OrderDetailsAdapter(Activity activity, List<OrderListResponeData> mList) {
        this.activity = activity;
        this.mList = new ArrayList<>();
        this.mList = mList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_list_order, null);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        OrderListResponeData data = mList.get(position);

        holder.mTxtOrderId.setText(data.getOrder_id());
        holder.mTxtQty.setText(data.getProductQtyWithLabel(activity));
        holder.mTxtProdName.setText(data.getProduct_name());
        holder.mTxtPrice.setText(data.getProductPriceWithSym());

        Utility.imageSet(activity, data.getProduct_image_url(), holder.mImgProduct);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtOrderId, mTxtProdName, mTxtPrice, mTxtQty;
        ImageView mImgProduct;

        public OrderViewHolder(View itemView) {
            super(itemView);

            Utility.applyTypeFace(activity, (LinearLayout) itemView.findViewById(R.id.base_layout));

            mTxtOrderId = itemView.findViewById(R.id.textview_order_id);
            mTxtPrice = itemView.findViewById(R.id.textview_product_price);
            mTxtProdName = itemView.findViewById(R.id.textview_product_name);
            mTxtQty = itemView.findViewById(R.id.textview_qty);
            mImgProduct = itemView.findViewById(R.id.imageview_product);
        }
    }
}

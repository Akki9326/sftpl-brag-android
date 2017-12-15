package com.pulse.brag.adapters;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.pojo.response.CollectionListResponse;
import com.pulse.brag.pojo.response.ProductListResponse;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by nikhil.vadoliya on 13-12-2017.
 */


public class CollectionListAdapter extends RecyclerView.Adapter<CollectionListAdapter.MyViewHolder> {

    Activity mActivity;
    List<CollectionListResponse> mListResponses;
    OnItemClickListener mItemClickListener;

    public CollectionListAdapter(Activity activity, List<CollectionListResponse> mListResponses, OnItemClickListener mItemClickListener) {
        this.mActivity = activity;
        this.mListResponses = new ArrayList<>();
        this.mListResponses = mListResponses;
        this.mItemClickListener = mItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(mActivity).inflate(R.layout.item_list_collection_even, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(mActivity).inflate(R.layout.item_list_collection_odd, parent, false);
                break;

        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


//        Utility.imageSet(mActivity, mListResponses.get(position).getUrl(), holder.mImgBackground);
        holder.mText.setText(mListResponses.get(position).getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        //1 for odd and 2 for even
        return position % 2 != 0 ? 1 : 2;
    }

    @Override
    public int getItemCount() {
        return mListResponses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mImgBackground;
        TextView mText;
        View mView;

        public MyViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            Utility.applyTypeFace(mActivity, (LinearLayout) itemView.findViewById(R.id.base_layout));
            mImgBackground = (ImageView) itemView.findViewById(R.id.imageview_product_img);
            mText = (TextView) itemView.findViewById(R.id.textview_product_name);

//            mImgBackground = (ImageView) itemView.findViewById(R.id.imageView_collection_background);
//            mText = (TextView) itemView.findViewById(R.id.textview_collection_label);
        }
    }
}

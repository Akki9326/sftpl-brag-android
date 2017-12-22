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
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.pojo.NotificationResponseData;
import com.pulse.brag.pojo.datas.NotificationListData;
import com.pulse.brag.views.OnSingleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 18-12-2017.
 */


public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {

    Activity mActivity;
    List<NotificationListData> mListData;
    OnItemClickListener onItemClickListener;

    public NotificationListAdapter(Activity mActivity, List<NotificationListData> mListData, OnItemClickListener onItemClickListener) {
        this.mActivity = mActivity;
        this.mListData = new ArrayList<>();
        this.mListData = mListData;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public NotificationListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(mActivity).inflate(R.layout.item_list_notification, null);
        NotificationListAdapter.MyViewHolder viewHolder1 = new NotificationListAdapter.MyViewHolder(view1);
        return viewHolder1;

    }

    @Override
    public void onBindViewHolder(NotificationListAdapter.MyViewHolder holder, final int position) {
        holder.mTxtTitle.setText(mListData.get(position).getTitle());
        holder.mTxtDetail.setText(mListData.get(position).getDescrioption());

       /* if (mListData.get(position).isRead()) {
            holder.mView.setBackgroundColor(Color.WHITE);
        } else {
            holder.mView.setBackgroundColor(mActivity.getResources().getColor(R.color.gray_10));
        }*/

        holder.mView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtTitle;
        TextView mTxtDetail;
        View mView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mTxtTitle = itemView.findViewById(R.id.textview_title);
            mTxtDetail = itemView.findViewById(R.id.textview_description);
        }
    }
}


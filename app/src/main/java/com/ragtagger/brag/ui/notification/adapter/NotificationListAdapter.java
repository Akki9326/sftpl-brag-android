package com.ragtagger.brag.ui.notification.adapter;


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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.IOnItemClickListener;
import com.ragtagger.brag.data.model.datas.DataNotificationList;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.databinding.ItemListNotificationBinding;
import com.ragtagger.brag.ui.core.CoreViewHolder;
import com.ragtagger.brag.ui.notification.NotificationItemViewModel;
import com.ragtagger.brag.utils.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 18-12-2017.
 */


public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {

    Activity mActivity;
    List<DataNotificationList> mListData;
    IOnItemClickListener onItemClickListener;

    public NotificationListAdapter(Activity mActivity, List<DataNotificationList> mListData, IOnItemClickListener onItemClickListener) {
        this.mActivity = mActivity;
        this.mListData = new ArrayList<>();
        this.mListData = mListData;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public NotificationListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        ItemListNotificationBinding itemListNotificationBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_list_notification, parent, false);
        return new MyViewHolder(itemListNotificationBinding);

    }

    @Override
    public void onBindViewHolder(NotificationListAdapter.MyViewHolder holder, final int position) {

        holder.bindCartData(position, mListData.get(position));

       /* if (mListData.get(position).isRead()) {
            holder.mView.setBackgroundColor(Color.WHITE);
        } else {
            holder.mView.setBackgroundColor(mActivity.getResources().getColor(R.color.gray_10));
        }*/


    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public class MyViewHolder extends CoreViewHolder implements NotificationItemViewModel.OnItemClick {

        ItemListNotificationBinding mBind;
        int pos;

        public MyViewHolder(ItemListNotificationBinding itemView) {
            super(itemView.getRoot());
            this.mBind = itemView;
            Utility.applyTypeFace(mActivity, mBind.baseLayout);
        }

        void bindCartData(int position, DataNotificationList responeData) {
            pos = position;
            mBind.setViewModel(new NotificationItemViewModel(itemView.getContext(), responeData, position, this));
            mBind.executePendingBindings();
        }

        @Override
        public void onBind(int position) {

        }

        @Override
        public void onItemClick(int position, DataNotificationList responeData) {
            onItemClickListener.onItemClick(position);
        }
    }
}


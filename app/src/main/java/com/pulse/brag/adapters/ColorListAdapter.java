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
import android.widget.LinearLayout;

import com.pulse.brag.R;
import com.pulse.brag.interfaces.OnProductColorSelectListener;
import com.pulse.brag.views.OnSingleClickListener;
import com.pulse.brag.views.RoundView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 08-12-2017.
 */


public class ColorListAdapter extends RecyclerView.Adapter<ColorListAdapter.MyViewHolder> {

    Activity mActivity;
    List<String> mList;
    OnProductColorSelectListener colorSelectListener;
    int mSeletedPos;
    List<Boolean> mBooleanList;

    MyViewHolder mViewHolder;

    public ColorListAdapter(Activity mActivity, List<String> mList, int mSeletedPos, OnProductColorSelectListener colorSelectListener) {
        this.mActivity = mActivity;
        this.mList = new ArrayList<>();
        this.mList = mList;
        this.colorSelectListener = colorSelectListener;
        this.mSeletedPos = mSeletedPos;

        int pos = 0;
        mBooleanList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            mBooleanList.add(pos++, Boolean.FALSE);
        }
        mBooleanList.add(0, Boolean.TRUE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(mActivity).inflate(R.layout.item_list_color, null);
        MyViewHolder viewHolder1 = new MyViewHolder(view1);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        mViewHolder = holder;

        holder.mRoundColor.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                colorSelectListener.onSeleteColor(position);
            }
        });
        if (mBooleanList.get(position)) {
            holder.mRoundSelector.setVisibility(View.VISIBLE);
        } else {
            holder.mRoundSelector.setVisibility(View.INVISIBLE);
        }
        holder.mRoundSelector.setColor(mList.get(position));
        holder.mRoundColor.setColor(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setSelectorItem(int pos) {
        if (mSeletedPos != pos) {
            mBooleanList.set(pos, Boolean.TRUE);
            mBooleanList.set(mSeletedPos, Boolean.FALSE);
            mSeletedPos = pos;
            notifyDataSetChanged();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        View mBaseView;
        RoundView mRoundColor, mRoundSelector;

        LinearLayout mBaseLayout;


        public MyViewHolder(View itemView) {
            super(itemView);
            mBaseView = itemView;
            mRoundColor = (RoundView) itemView.findViewById(R.id.roundview_product_color);
            mRoundSelector = (RoundView) itemView.findViewById(R.id.roundview_selector);

        }
    }
}

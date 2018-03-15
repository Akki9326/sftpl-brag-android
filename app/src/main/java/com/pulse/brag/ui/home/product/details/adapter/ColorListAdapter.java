package com.pulse.brag.ui.home.product.details.adapter;


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
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.callback.IOnProductColorSelectListener;
import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.model.datas.DataProductList;
import com.pulse.brag.utils.Common;
import com.pulse.brag.views.RoundView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 08-12-2017.
 */


public class ColorListAdapter extends RecyclerView.Adapter<ColorListAdapter.MyViewHolder> {

    Activity mActivity;
    List<DataProductList.Products> mList;
    IOnProductColorSelectListener colorSelectListener;
    int mSeletedPos;
    List<Boolean> mSeletedList;

    MyViewHolder mViewHolder;

    public ColorListAdapter(Activity mActivity, List<DataProductList.Products> mList, int mSeletedPos, IOnProductColorSelectListener colorSelectListener) {
        this.mActivity = mActivity;
        this.mList = new ArrayList<>();
        this.mList = mList;
        this.colorSelectListener = colorSelectListener;
        this.mSeletedPos = mSeletedPos;

        fillSelectedList(mSeletedPos);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(mActivity).inflate(R.layout.item_list_color, null);
        MyViewHolder viewHolder1 = new MyViewHolder(view1);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        mViewHolder = holder;

        holder.mRoundColor.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                colorSelectListener.onSelectedColor(mSeletedPos, position, mList.get(position).getSizes());
            }
        });
        if (mSeletedList.get(position)) {
            holder.mRoundSelector.setVisibility(View.VISIBLE);
        } else {
            holder.mRoundSelector.setVisibility(View.INVISIBLE);
        }
        holder.mRoundSelector.setColorHex(Common.isNotNullOrEmpty(mList.get(position).getColorHexCode()) ? mList.get(position).getColorHexCode() : "#000000");
        holder.mRoundColor.setColorHex(Common.isNotNullOrEmpty(mList.get(position).getColorHexCode()) ? mList.get(position).getColorHexCode() : "#000000");
        holder.mName.setText(mList.get(position).getColorCode().length() >= 3 ? mList.get(position).getColorCode().substring(0, 3) + mList.get(position).getColorFamily() : mList.get(position).getColorCode() + mList.get(position).getColorFamily());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void reset(List<DataProductList.Products> list, int selectedPos) {
        if (mList != null)
            mList.clear();

        if (mSeletedList != null)
            mSeletedList.clear();
        mList.addAll(list);
        fillSelectedList(selectedPos);
        notifyDataSetChanged();
    }

    private void fillSelectedList(int selectedPos) {
        int pos = 0;
        mSeletedList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            mSeletedList.add(pos++, Boolean.FALSE);
        }
        mSeletedList.add(selectedPos, Boolean.TRUE);
    }

    public void setSelectedItem(int pos) {
        mSeletedList.set(pos, Boolean.TRUE);
        mSeletedList.set(mSeletedPos, Boolean.FALSE);
        mSeletedPos = pos;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        View mBaseView;
        RoundView mRoundColor, mRoundSelector;
        TextView mName;

        LinearLayout mBaseLayout;


        public MyViewHolder(View itemView) {
            super(itemView);
            mBaseView = itemView;
            mRoundColor = itemView.findViewById(R.id.roundview_product_color);
            mRoundSelector = itemView.findViewById(R.id.roundview_selector);
            mName = itemView.findViewById(R.id.textview_color);

        }
    }
}

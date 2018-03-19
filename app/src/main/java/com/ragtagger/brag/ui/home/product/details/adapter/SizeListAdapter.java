package com.ragtagger.brag.ui.home.product.details.adapter;

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

import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.model.datas.DataProductList;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.callback.IOnProductSizeSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 25-10-2017.
 */


public class SizeListAdapter extends RecyclerView.Adapter<SizeListAdapter.MyViewHolder> {
    Activity mActivity;
    List<DataProductList.Size> mListSize;
    List<Boolean> mSeletedList;
    IOnProductSizeSelectListener mOnProductSizeSelectListener;
    int mSeletedPos;

    public SizeListAdapter(Activity mActivity, List<DataProductList.Size> mListSize, int selectedPos, IOnProductSizeSelectListener onProductSizeSelectListener) {
        this.mActivity = mActivity;
        this.mListSize = mListSize;
        this.mSeletedPos = selectedPos;
        mOnProductSizeSelectListener = onProductSizeSelectListener;

        fillSelectedList(selectedPos);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(mActivity).inflate(R.layout.item_list_size, null);
        MyViewHolder viewHolder1 = new MyViewHolder(view1);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.mTxtSize.setText(mListSize.get(position).getSizeCode());
        holder.mView.setSelected(mSeletedList.get(position));
        Utility.applyTypeFace(mActivity, holder.mBaseLayout);
        holder.mView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (mSeletedPos != position) {
                    setSelectedItem(position);
                    mOnProductSizeSelectListener.OnSelectedSize(mSeletedPos, position, mListSize.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListSize.size();
    }

    public void reset(List<DataProductList.Size> list, int selectedPos) {
        if (mListSize != null)
            mListSize.clear();

        if (mSeletedList != null)
            mSeletedList.clear();
        mListSize.addAll(list);
        fillSelectedList(selectedPos);
        notifyDataSetChanged();
    }

    private void fillSelectedList(int mSelectedPos) {
        int pos = 0;
        mSeletedList = new ArrayList<>();
        for (int i = 0; i < mListSize.size(); i++) {
            mSeletedList.add(pos++, Boolean.FALSE);
        }
        mSeletedList.add(mSelectedPos, Boolean.TRUE);
    }

    public void setSelectedItem(int pos) {
        mSeletedList.set(pos, Boolean.TRUE);
        mSeletedList.set(mSeletedPos, Boolean.FALSE);
        mSeletedPos = pos;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtSize;
        View mView;
        LinearLayout mBaseLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mBaseLayout = (LinearLayout) itemView.findViewById(R.id.base_layout);
            Utility.applyTypeFace(mActivity, mBaseLayout);
            mTxtSize = (TextView) itemView.findViewById(R.id.textView_size);
        }
    }
}

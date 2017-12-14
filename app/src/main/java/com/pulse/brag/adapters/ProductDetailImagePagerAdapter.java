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
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pulse.brag.R;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.pojo.response.ImagePagerResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 26-10-2017.
 */


public class ProductDetailImagePagerAdapter extends PagerAdapter {
    Activity mContext;
    LayoutInflater mLayoutInflater;
    List<ImagePagerResponse> mPagerRespones;
    OnItemClickListener mItemClickListener;

    public ProductDetailImagePagerAdapter(Activity context, List<ImagePagerResponse> mPagerRespones, OnItemClickListener mOnItemClickListener) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mPagerRespones = new ArrayList<>();
        this.mPagerRespones.addAll(mPagerRespones);
        this.mItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getCount() {
        return mPagerRespones.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_image_pager, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Utility.imageSet(mContext, mPagerRespones.get(position).getUrl(), imageView);
        container.addView(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(position);


            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}

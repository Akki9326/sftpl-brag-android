package com.pulse.brag.adapters;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.pulse.brag.R;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.pojo.respones.ImagePagerRespone;
import com.pulse.brag.views.pinchtozoom.PinchToZoom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 26-10-2017.
 */


public class FullScreenImagePagerAdaper extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<ImagePagerRespone> mPagerRespones;

    public FullScreenImagePagerAdaper(Context context, List<ImagePagerRespone> mPagerRespones) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mPagerRespones = new ArrayList<>();
        this.mPagerRespones.addAll(mPagerRespones);
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
        View itemView = mLayoutInflater.inflate(R.layout.item_full_image, container, false);

        PinchToZoom imageView = (PinchToZoom) itemView.findViewById(R.id.imageView);
        Utility.imageSet(mContext, mPagerRespones.get(position).getUrl(), imageView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}


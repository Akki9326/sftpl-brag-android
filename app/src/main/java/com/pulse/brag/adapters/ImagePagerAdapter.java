package com.pulse.brag.adapters;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.databinding.library.baseAdapters.BR;
import com.pulse.brag.R;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.pojo.response.ImagePagerResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 29-09-2017.
 */


public class ImagePagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    List<ImagePagerResponse> mPagerRespones;

    public ImagePagerAdapter(Context context, List<ImagePagerResponse> mPagerRespones) {
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


        ViewDataBinding dataBinding = DataBindingUtil.inflate(mLayoutInflater
                , R.layout.item_image_pager, null, false);
        dataBinding.setVariable(BR.viewModel, mPagerRespones.get(position));
        container.addView(dataBinding.getRoot());
        return dataBinding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}

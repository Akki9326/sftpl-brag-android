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
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.pulse.brag.R;
import com.pulse.brag.databinding.ItemFullImageBinding;
import com.pulse.brag.data.model.response.RImagePager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 26-10-2017.
 */


public class FullScreenImagePagerAdaper extends PagerAdapter implements ImagePagerItem.ItemImageClickListener {
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<RImagePager> mPagerRespones;
    IOnFullImagePageClickListener mListener;

    public FullScreenImagePagerAdaper(Context context, List<RImagePager> mPagerRespones, IOnFullImagePageClickListener listener) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mPagerRespones = new ArrayList<>();
        this.mPagerRespones.addAll(mPagerRespones);
        this.mListener = listener;
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
        /*View itemView = mLayoutInflater.inflate(R.layout.item_full_image, container, false);

        PinchToZoom imageView = (PinchToZoom) itemView.findViewById(R.id.imageView);
        Utility.imageSetCenterInside(mContext, mPagerRespones.get(position).getUrl(), imageView);
        container.addView(itemView);
        return itemView;*/

        ItemFullImageBinding dataBinding = DataBindingUtil.inflate(mLayoutInflater
                , R.layout.item_full_image, null, false);
        dataBinding.setViewModel(new ImagePagerItem(mPagerRespones.get(position), mContext, position, this));
        //dataBinding.setVariable(BR.viewModel, mPagerRespones.get(position));
        container.addView(dataBinding.getRoot());
        return dataBinding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public void onItemClick(int position, RImagePager item) {
        mListener.onFullImagePageClick(position, item);
    }

    public interface IOnFullImagePageClickListener {
        void onFullImagePageClick(int pos, RImagePager item);
    }

}


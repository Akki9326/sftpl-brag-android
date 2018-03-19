package com.ragtagger.brag.adapters;

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

import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.response.RImagePager;
import com.ragtagger.brag.databinding.ItemImagePagerBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 29-09-2017.
 */


public class ImagePagerAdapter extends PagerAdapter implements ImagePagerItem.ItemImageClickListener {

    Context mContext;
    LayoutInflater mLayoutInflater;
    List<RImagePager> mPagerRespones;
    IOnImagePageClickListener mListener;

    public ImagePagerAdapter(Context context, List<RImagePager> mPagerRespones, IOnImagePageClickListener listener) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mPagerRespones = new ArrayList<>();
        this.mPagerRespones.addAll(mPagerRespones);
        mListener = listener;
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


        ItemImagePagerBinding dataBinding = DataBindingUtil.inflate(mLayoutInflater
                , R.layout.item_image_pager, null, false);
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
        mListener.onImagePageClick(position, item);
    }

    public interface IOnImagePageClickListener {
        void onImagePageClick(int pos, RImagePager item);
    }
}

package com.pulse.brag.fragments;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.adapters.ImagePagerAdapter;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.pojo.ImagePagerRespone;
import com.pulse.brag.views.CustomViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by nikhil.vadoliya on 04-10-2017.
 */


public class ProductDetailFragment extends BaseFragment implements BaseInterface {

    View mView;
    ViewPager mViewPager;
    CustomViewPagerIndicator mViewPagerIndicator;
    TextView mTxtProductName, mTxtProPrice, mTxtProDetails, mTxtProShortDetail;
    RecyclerView mRecyclerViewColor;
    RecyclerView mRecyclerViewSize;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_product_detail, container, false);
            initializeData();
            setListeners();
            showData();
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbar();
    }

    @Override
    public void setToolbar() {
        ((BaseActivity) getActivity()).showToolbar(true, false, true, "Product Detail");
    }

    @Override
    public void initializeData() {

        mViewPager = (ViewPager) mView.findViewById(R.id.view_pager);
        mViewPagerIndicator = (CustomViewPagerIndicator) mView.findViewById(R.id.pager_view);
        mTxtProductName = (TextView) mView.findViewById(R.id.textview_product_name);
        mTxtProDetails = (TextView) mView.findViewById(R.id.textView_description);
        mTxtProShortDetail = (TextView) mView.findViewById(R.id.textView_short_des);
        mTxtProPrice = (TextView) mView.findViewById(R.id.textView_price);

        mRecyclerViewColor = (RecyclerView) mView.findViewById(R.id.recycleView_color);
        mRecyclerViewSize = (RecyclerView) mView.findViewById(R.id.recycleView_size);

        Utility.applyTypeFace(getContext(), (LinearLayout) mView.findViewById(R.id.base_layout));

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void showData() {

        List<ImagePagerRespone> imagePagerResponeList = new ArrayList<>();
        imagePagerResponeList.add(new ImagePagerRespone("http://cdn.shopify.com/s/files/1/1629/9535/products/2_front_7b6ad7b4-2320-420b-badd-07087487111f_1024x1024.jpg?v=1491997975%22", "CLASSIC BIKINI"));
        imagePagerResponeList.add(new ImagePagerRespone("http://cdn.shopify.com/s/files/1/1629/9535/products/3_front_c39c2cb7-5048-4e46-b37f-3855d570c6c1_large.jpg?v=1491990016", ""));
        imagePagerResponeList.add(new ImagePagerRespone("http://kingfisher.scene7.com/is/image/Kingfisher/5011583143836_04i?crop=15,15,1981,1398&anchor=1005,714&anchor=1007,1007&wid=600", ""));
        imagePagerResponeList.add(new ImagePagerRespone("https://wallpaperscraft.com/image/usa_new_york_top_view_skyscrapers_112139_602x339.jpg", ""));
        imagePagerResponeList.add(new ImagePagerRespone("http://cdn01.androidauthority.net/wp-content/uploads/2015/11/00-best-backgrounds-and-wallpaper-apps-for-android.jpg", ""));

        mViewPager.setAdapter(new ImagePagerAdapter(getActivity(), imagePagerResponeList));
        mViewPagerIndicator.setViewPager(mViewPager);

        mTxtProductName.setText("Classic Pullover T-shirt Bralette - White");
        mTxtProPrice.setText("Rs 1200");

        String mShortDetail = " Lucille Curtis |Juana Hanson |Lila Flores |Kevin Rodriguez |Ebony Norman |Celia Rodriquez |Jake Morales |Jane Farmer |Willie tRivera |Freeman";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTxtProShortDetail.setText(Html.fromHtml(Utility.getTextWithBullet(mShortDetail), Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST));
        }

    }
}

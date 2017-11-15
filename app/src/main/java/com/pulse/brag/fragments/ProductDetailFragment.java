package com.pulse.brag.fragments;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.adapters.ColorListAdapter;
import com.pulse.brag.adapters.ProductDetailImagePagerAdapter;
import com.pulse.brag.adapters.SizeListAdapter;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.interfaces.OnProductColorSelectListener;
import com.pulse.brag.interfaces.OnProductSizeSelectListener;
import com.pulse.brag.pojo.respones.ImagePagerRespone;
import com.pulse.brag.views.CustomViewPagerIndicator;
import com.pulse.brag.views.HorizontalSpacingDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 04-10-2017.
 */


public class ProductDetailFragment extends BaseFragment implements BaseInterface, OnItemClickListener, OnProductSizeSelectListener, OnProductColorSelectListener {

    View mView;
    ViewPager mViewPager;
    CustomViewPagerIndicator mViewPagerIndicator;
    TextView mTxtProductName, mTxtProPrice, mTxtProDetails, mTxtProShortDetail;
    RecyclerView mRecyclerViewColor;
    RecyclerView mRecyclerViewSize;
    ImageView mImgAdd, mImgMinus;
    TextView mTxtQty;

    ColorListAdapter mColorListAdapter;
    SizeListAdapter mSizeListAdapter;

    List<String> mIntegerList;
    List<String> mStringList;
    List<ImagePagerRespone> imagePagerResponeList;
    int mQuality;

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
        mTxtQty = (TextView) mView.findViewById(R.id.textView_qty);
        mImgAdd = (ImageView) mView.findViewById(R.id.imageView_add);
        mImgMinus = (ImageView) mView.findViewById(R.id.imageView_minus);

        mRecyclerViewColor = (RecyclerView) mView.findViewById(R.id.recycleView_color);
        mRecyclerViewColor.setHasFixedSize(true);
        mRecyclerViewColor.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewSize = (RecyclerView) mView.findViewById(R.id.recycleView_size);
        mRecyclerViewColor.setHasFixedSize(true);
        mRecyclerViewSize.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        Utility.applyTypeFace(getContext(), (RelativeLayout) mView.findViewById(R.id.base_layout));

        mIntegerList = new ArrayList<>();
        mStringList = new ArrayList<>();

        mQuality = Integer.parseInt(mTxtQty.getText().toString());


    }

    @Override
    public void setListeners() {
        mImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mQuality++;
                mTxtQty.setText("" + mQuality);
            }
        });
        mImgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mQuality == 1) {
                    return;
                }
                mQuality--;
                mTxtQty.setText("" + mQuality);
            }
        });

    }

    @Override
    public void showData() {

        imagePagerResponeList = new ArrayList<>();
        imagePagerResponeList.add(new ImagePagerRespone("http://cdn.shopify.com/s/files/1/1629/9535/products/2_front_7b6ad7b4-2320-420b-badd-07087487111f_1024x1024.jpg?v=1491997975%22", "CLASSIC BIKINI"));
        imagePagerResponeList.add(new ImagePagerRespone("http://cdn.shopify.com/s/files/1/1629/9535/products/3_front_c39c2cb7-5048-4e46-b37f-3855d570c6c1_large.jpg?v=1491990016", ""));
        imagePagerResponeList.add(new ImagePagerRespone("http://kingfisher.scene7.com/is/image/Kingfisher/5011583143836_04i?crop=15,15,1981,1398&anchor=1005,714&anchor=1007,1007&wid=600", ""));
        imagePagerResponeList.add(new ImagePagerRespone("https://wallpaperscraft.com/image/usa_new_york_top_view_skyscrapers_112139_602x339.jpg", ""));
        imagePagerResponeList.add(new ImagePagerRespone("http://cdn01.androidauthority.net/wp-content/uploads/2015/11/00-best-backgrounds-and-wallpaper-apps-for-android.jpg", ""));

        mViewPager.setAdapter(new ProductDetailImagePagerAdapter(getActivity(), imagePagerResponeList, this));
        mViewPagerIndicator.setViewPager(mViewPager);


        mTxtProductName.setText("Classic Pullover T-shirt Bralette - White");
        mTxtProPrice.setText("Rs. 1200");

        String mShortDetail = " Lucille Curtis |Juana Hanson |Lila Flores |Kevin Rodriguez |Ebony Norman |Celia Rodriquez |Jake Morales |Jane Farmer |Willie tRivera |Freeman";


        mTxtProShortDetail.setText(Html.fromHtml(Utility.getTextWithBullet(mShortDetail)));


        mIntegerList.add("#F44336");
        mIntegerList.add("#E91E63");
        mIntegerList.add("#9C27B0");
        mIntegerList.add("#2196F3");
        mIntegerList.add("#FF9800");
        mIntegerList.add("#FF5722");
        mIntegerList.add("#3F51B5");

        mColorListAdapter = new ColorListAdapter(getActivity(), mIntegerList, 0, this);
        mRecyclerViewColor.setAdapter(mColorListAdapter);
        mRecyclerViewColor.addItemDecoration(new HorizontalSpacingDecoration(10));


        List<String> mStringList = new ArrayList<>();
        mStringList.add("S");
        mStringList.add("S");
        mStringList.add("M");
        mStringList.add("X");
        mStringList.add("XL");
        mStringList.add("XXL");
        mStringList.add("XXL");

        mSizeListAdapter = new SizeListAdapter(getActivity(), mStringList, 0, this);
        mRecyclerViewSize.setAdapter(mSizeListAdapter);
        mRecyclerViewSize.addItemDecoration(new HorizontalSpacingDecoration(10));
    }

    @Override
    public void onItemClick(int position) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.BUNDLE_IMAGE_LIST, (ArrayList<? extends Parcelable>) imagePagerResponeList);
        args.putInt(Constants.BUNDLE_POSITION, position);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        DialogFragment mDialogFragmentImage = new FullScreenImageDialogFragment();
        mDialogFragmentImage.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
        mDialogFragmentImage.setArguments(args);
        mDialogFragmentImage.show(fm, "");
    }


    @Override
    public void OnSelectedSize(int pos) {
        mSizeListAdapter.setSelectedItem(pos);
    }

    @Override
    public void onSeleteColor(int pos) {
        mColorListAdapter.setSelectorItem(pos);

    }
}

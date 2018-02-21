package com.pulse.brag.ui.fragments;

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
import com.pulse.brag.ui.activities.BaseActivity;
import com.pulse.brag.adapters.ColorListAdapter;
import com.pulse.brag.adapters.ProductDetailImagePagerAdapter;
import com.pulse.brag.adapters.SizeListAdapter;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.interfaces.OnProductColorSelectListener;
import com.pulse.brag.interfaces.OnProductSizeSelectListener;
import com.pulse.brag.pojo.DummeyDataRespone;
import com.pulse.brag.pojo.response.ImagePagerResponse;
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
    ProductDetailImagePagerAdapter mDetailImagePagerAdapter;
    List<String> mIntegerList;
    List<String> mStringList;
    List<ImagePagerResponse> imagePagerResponeList;
    int mQuality;


    public static ProductDetailFragment newInstance(DummeyDataRespone dataRespone) {

        Bundle args = new Bundle();
        args.putParcelable(Constants.BUNDLE_SELETED_PRODUCT, dataRespone);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
        ((BaseActivity) getActivity()).showToolbar(true, false, true, getString(R.string.toolbar_label_product_detail));
    }

    @Override
    public void initializeData() {

        mViewPager =  mView.findViewById(R.id.view_pager);

        mViewPagerIndicator =  mView.findViewById(R.id.pager_view);
        mTxtProductName =mView.findViewById(R.id.textview_product_name);
        mTxtProDetails = mView.findViewById(R.id.textView_description);
        mTxtProShortDetail = mView.findViewById(R.id.textView_short_des);
        mTxtProPrice = mView.findViewById(R.id.textView_price);
        mTxtQty = mView.findViewById(R.id.textView_qty);
        mImgAdd =  mView.findViewById(R.id.imageView_add);
        mImgMinus = mView.findViewById(R.id.imageView_minus);

        mRecyclerViewColor =  mView.findViewById(R.id.recycleView_color);
        mRecyclerViewColor.setHasFixedSize(true);
        mRecyclerViewColor.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewSize =  mView.findViewById(R.id.recycleView_size);
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
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/files/tripper-collection-landing-banner.jpg?17997587327459325", "CLASSIC BIKINI"));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/IMG_9739_grande.jpg?v=1499673727", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/IMG_9739_grande.jpg?v=1499673727", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/Banner-image_grande.jpg?v=1494221088", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/neon-post-classic_grande.jpg?v=1492607080", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/Banner-image_grande.jpg?v=1494221088", ""));

        mDetailImagePagerAdapter = new ProductDetailImagePagerAdapter(getActivity(), imagePagerResponeList, this);
        mViewPager.setAdapter(mDetailImagePagerAdapter);
        mViewPagerIndicator.setViewPager(mViewPager);


        if (getArguments()!=null && getArguments().containsKey(Constants.BUNDLE_SELETED_PRODUCT)) {
            DummeyDataRespone dataRespone = getArguments().getParcelable(Constants.BUNDLE_SELETED_PRODUCT);
            mTxtProductName.setText(dataRespone.getFirst_name());
        }
        mTxtProPrice.setText(Utility.getIndianCurrencePriceFormate(500));

        String mShortDetail = " Lucille Curtis |Juana Hanson |Lila Flores |Kevin Rodriguez |Ebony Norman |Celia Rodriquez |Jake Morales |Jane Farmer |Willie tRivera |Freeman";


        mTxtProShortDetail.setText(Html.fromHtml(Utility.getTextWithBullet(mShortDetail)));


//        mIntegerList.add("#F44336");
        mIntegerList.add("#000000");
//        mIntegerList.add("#E91E63");
//        mIntegerList.add("#9C27B0");
//        mIntegerList.add("#2196F3");
//        mIntegerList.add("#FF9800");
//        mIntegerList.add("#FF5722");
//        mIntegerList.add("#3F51B5");

        mColorListAdapter = new ColorListAdapter(getActivity(), mIntegerList, 0, this);
        mRecyclerViewColor.setAdapter(mColorListAdapter);
        mRecyclerViewColor.addItemDecoration(new HorizontalSpacingDecoration(10));


        List<String> mStringList = new ArrayList<>();
        mStringList.add("XS");
        mStringList.add("S");
        mStringList.add("M");
        mStringList.add("L");
//        mStringList.add("X");
//        mStringList.add("XL");
//        mStringList.add("XXL");
//        mStringList.add("XXL");

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
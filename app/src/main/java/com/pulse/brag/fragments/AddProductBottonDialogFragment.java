package com.pulse.brag.fragments;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.activities.MainActivity;
import com.pulse.brag.adapters.ColorListAdapter;
import com.pulse.brag.adapters.ImagePagerAdapter;
import com.pulse.brag.adapters.SizeListAdapter;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.interfaces.OnProductColorSelectListener;
import com.pulse.brag.interfaces.OnProductSizeSelectListener;
import com.pulse.brag.pojo.DummeyDataRespone;
import com.pulse.brag.pojo.requests.AddToCartRequest;
import com.pulse.brag.pojo.response.ImagePagerResponse;
import com.pulse.brag.views.CustomViewPagerIndicator;
import com.pulse.brag.views.HorizontalSpacingDecoration;
import com.pulse.brag.views.OnSingleClickListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by nikhil.vadoliya on 06-11-2017.
 */


public class AddProductBottonDialogFragment extends DialogFragment implements OnProductSizeSelectListener,
        OnProductColorSelectListener, BaseInterface {

    View mView;
    LinearLayout mLinerDummey;
    RecyclerView mRecyclerViewColor, mRecyclerViewSize;
    TextView mTxtQty, mTxtTitle, mTxtAddCart;
    TextView mTxtMax;
    EditText mEditQty;
    ViewPager mViewPager;
    CustomViewPagerIndicator mViewPagerIndicator;
    NestedScrollView mScrollView;

    ColorListAdapter mColorListAdapter;
    SizeListAdapter mSizeListAdapter;

    DummeyDataRespone data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_fragment_add_product, container, false);
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_DialogWhenLarge_NoActionBar);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeData();
        setListeners();
        attachKeyboardListeners();
        showData();
    }


    @Override
    public void setToolbar() {

    }

    @Override
    public void initializeData() {

        mRecyclerViewColor = (RecyclerView) mView.findViewById(R.id.recycleView_color);
        mRecyclerViewColor.setHasFixedSize(true);
        mRecyclerViewColor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mRecyclerViewSize = (RecyclerView) mView.findViewById(R.id.recycleView_size);
        mRecyclerViewColor.setHasFixedSize(true);
        mRecyclerViewSize.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mTxtTitle = (TextView) mView.findViewById(R.id.textview_product_name);
        mTxtQty = (TextView) mView.findViewById(R.id.textView_qty);
        mLinerDummey = (LinearLayout) mView.findViewById(R.id.linear_dummey);
        mTxtAddCart = (TextView) mView.findViewById(R.id.textview_add_cart);
        mEditQty = (EditText) mView.findViewById(R.id.edittext_qty);
        mTxtMax = (TextView) mView.findViewById(R.id.textview_max);
        mViewPager = (ViewPager) mView.findViewById(R.id.view_pager);
        mViewPagerIndicator = (CustomViewPagerIndicator) mView.findViewById(R.id.pager_view);
        mScrollView = (NestedScrollView) mView.findViewById(R.id.scrollView);

        Utility.applyTypeFace(getActivity(), (LinearLayout) mView.findViewById(R.id.base_layout));

        data = getArguments().getParcelable(Constants.BUNDLE_SELETED_PRODUCT);

    }

    @Override
    public void setListeners() {

        mLinerDummey.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                dismiss();
            }
        });
        mTxtAddCart.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                ((MainActivity) getActivity()).addToCartAPI(new AddToCartRequest());
                dismiss();
            }
        });


        mEditQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.smoothScrollTo(0, mTxtAddCart.getTop() + 205);
            }
        });


        mEditQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    if (s.toString().trim().length() == 0 || new Integer(s.toString()).intValue() == 0) {
                        mTxtMax.setTextColor(getResources().getColor(R.color.text_black));
                        mTxtAddCart.setTextColor(getResources().getColor(R.color.gray_transparent));
                        mTxtAddCart.setEnabled(false);
                    } else if (new Integer(s.toString()).intValue() > 50) {
                        mTxtMax.setTextColor(Color.RED);
                        mTxtAddCart.setTextColor(getResources().getColor(R.color.gray_transparent));
                        mTxtAddCart.setEnabled(false);
                    } else {
                        mTxtMax.setTextColor(getResources().getColor(R.color.text_black));
                        mTxtAddCart.setTextColor(getResources().getColor(R.color.text_black));
                        mTxtAddCart.setEnabled(true);
                    }
                }
            }
        });
    }

    public void showData() {

        // TODO: 15-12-2017 Qty max lenght
        mEditQty.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        mTxtTitle.setText(data.getFirst_name() + " " + data.getLast_name());
        List<String> mIntegerList = new ArrayList<>();
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
        mStringList.add("XXXL");

        mSizeListAdapter = new SizeListAdapter(getActivity(), mStringList, 0, this);
        mRecyclerViewSize.setAdapter(mSizeListAdapter);
        mRecyclerViewSize.addItemDecoration(new HorizontalSpacingDecoration(10));

        List<ImagePagerResponse> imagePagerResponeList = new ArrayList<>();
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/files/tripper-collection-landing-banner.jpg?17997587327459325", "CLASSIC BIKINI"));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/IMG_9739_grande.jpg?v=1499673727", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/Banner-image_grande.jpg?v=1494221088", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/neon-post-classic_grande.jpg?v=1492607080", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/Banner-image_grande.jpg?v=1494221088", ""));

        mViewPager.setAdapter(new ImagePagerAdapter(getActivity(), imagePagerResponeList));
        mViewPagerIndicator.setViewPager(mViewPager);
    }

    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            int heightDiff = rootLayout.getRootView().getHeight() - rootLayout.getHeight();
            int contentViewTop = getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();


            if (heightDiff <= contentViewTop) {
                Log.i(TAG, "onGlobalLayout: KeyboardWillHide");
            } else {
                mScrollView.smoothScrollTo(0, mTxtAddCart.getTop() + 205);
                Log.i(TAG, "onGlobalLayout: KeyboardWillShow");
            }
        }
    };

    private boolean keyboardListenersAttached = false;
    private ViewGroup rootLayout;

    protected void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }

        rootLayout = (LinearLayout) mView.findViewById(R.id.base_layout);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);

        keyboardListenersAttached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (keyboardListenersAttached) {
            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
        }
    }

    @Override
    public void onSeleteColor(int pos) {
        mColorListAdapter.setSelectorItem(pos);
    }

    @Override
    public void OnSelectedSize(int pos) {
        mSizeListAdapter.setSelectedItem(pos);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(130);
        dialog.getWindow().setBackgroundDrawable(d);
        dialog.getWindow()
                .getAttributes().windowAnimations = R.style.AddProdDialogAnimation;
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }
}

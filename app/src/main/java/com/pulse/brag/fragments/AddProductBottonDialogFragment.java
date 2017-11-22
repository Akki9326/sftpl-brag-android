package com.pulse.brag.fragments;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.activities.MainActivity;
import com.pulse.brag.adapters.ColorListAdapter;
import com.pulse.brag.adapters.SizeListAdapter;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.interfaces.OnProductColorSelectListener;
import com.pulse.brag.interfaces.OnProductSizeSelectListener;
import com.pulse.brag.pojo.DummeyDataRespone;
import com.pulse.brag.pojo.DummeyRespone;
import com.pulse.brag.pojo.requests.AddToCartRequest;
import com.pulse.brag.pojo.respones.CollectionListRespone;
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
    }

    public void showData() {

        mTxtTitle.setText("Name");
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
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }
}

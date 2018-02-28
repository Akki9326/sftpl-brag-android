package com.pulse.brag.views;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pulse.brag.R;
import com.pulse.brag.adapters.FullScreenImagePagerAdaper;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.data.model.response.ImagePagerResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 26-10-2017.
 */


public class FullScreenImageDialogFragment extends DialogFragment {

    ViewPager mViewPager;
    List<ImagePagerResponse> mPagerRespones;
    int pos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_full_screen, container, false);

        if (getArguments().containsKey(Constants.BUNDLE_IMAGE_LIST)) {
            mPagerRespones = getArguments().getParcelableArrayList(Constants.BUNDLE_IMAGE_LIST);
            pos = getArguments().getInt(Constants.BUNDLE_POSITION);
        }

        if (getArguments().containsKey(Constants.BUNDLE_IMAGE_URL)) {
            mPagerRespones = new ArrayList<>();
            mPagerRespones.add(new ImagePagerResponse(getArguments().getString(Constants.BUNDLE_IMAGE_URL), ""));
            pos = 0;
        }

        ((ImageView) v.findViewById(R.id.ImageView_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mViewPager = v.findViewById(R.id.view_pager);
//        mViewPager.setPageTransformer(false, new Transformer());
        mViewPager.setAdapter(new FullScreenImagePagerAdaper(getActivity(), mPagerRespones));
        mViewPager.setCurrentItem(pos);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
    }
}

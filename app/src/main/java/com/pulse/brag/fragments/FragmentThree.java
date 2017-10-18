package com.pulse.brag.fragments;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pulse.brag.R;

/**
 * Created by nikhil.vadoliya on 26-09-2017.
 */


public class FragmentThree extends Fragment {


    public static FragmentThree newInstance() {

        Bundle args = new Bundle();

        FragmentThree fragment = new FragmentThree();
        fragment.setArguments(args);
        return fragment;
    }

    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_three, container, false);
        return mView;
    }
}

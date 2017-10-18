package com.pulse.brag.fragments;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.pulse.brag.R;
import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.fragments.BaseFragment;
import com.pulse.brag.fragments.FragmentOne;
import com.pulse.brag.fragments.FragmentThree;
import com.pulse.brag.fragments.FragmentTwo;
import com.pulse.brag.fragments.LogInFragment;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;

/**
 * Created by nikhil.vadoliya on 03-10-2017.
 */


public class HomeFragment extends BaseFragment implements BaseInterface {

    View mView;

    BottomNavigationView mNavigationView;
    FrameLayout mFrameLayoutOne;
    FrameLayout mFrameLayoutCategory;
    FrameLayout mFrameLayoutOrder;
    FrameLayout mFrameLayoutMore;

    boolean isAddedOne, isAddedCateg, isAddedOrder, isAddedFour;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
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
        ((BaseActivity) getActivity()).showToolbar(false, true,true);
    }

    @Override
    public void initializeData() {
        mNavigationView = (BottomNavigationView) mView.findViewById(R.id.bottom_sheet);
        Utility.removeShiftModeInBottomNavigation(mNavigationView);

        mFrameLayoutOne = (FrameLayout) mView.findViewById(R.id.fragment_container_one);
        mFrameLayoutCategory = (FrameLayout) mView.findViewById(R.id.fragment_container_category);
        mFrameLayoutOrder = (FrameLayout) mView.findViewById(R.id.fragment_container_order);
        mFrameLayoutMore = (FrameLayout) mView.findViewById(R.id.fragment_container_more);

        setFragmentInTab();
        isAddedOne = true;
    }

    @Override
    public void setListeners() {

        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.bottombaritem_golf:
                        mFrameLayoutOne.setVisibility(View.VISIBLE);
                        mFrameLayoutCategory.setVisibility(View.GONE);
                        mFrameLayoutOrder.setVisibility(View.GONE);
                        mFrameLayoutMore.setVisibility(View.GONE);

                        return true;

                    case R.id.bottombaritem_category:

                        if (!isAddedCateg) {
                            isAddedCateg = true;
                            FragmentTransaction transactionTwo = getChildFragmentManager().beginTransaction();
                            transactionTwo.replace(R.id.fragment_container_category, new FragmentTwo(), "Two");
                            transactionTwo.commit();
                        }
                        mFrameLayoutOne.setVisibility(View.GONE);
                        mFrameLayoutCategory.setVisibility(View.VISIBLE);
                        mFrameLayoutOrder.setVisibility(View.GONE);
                        mFrameLayoutMore.setVisibility(View.GONE);
                        return true;

                    case R.id.bottombaritem_order:

                        if (!isAddedOrder) {
                            isAddedOrder = true;
                            FragmentTransaction transactionTwo = getChildFragmentManager().beginTransaction();
                            transactionTwo.replace(R.id.fragment_container_order, new FragmentThree(), "Three");
                            transactionTwo.commit();
                        }
                        mFrameLayoutOne.setVisibility(View.GONE);
                        mFrameLayoutCategory.setVisibility(View.GONE);
                        mFrameLayoutOrder.setVisibility(View.VISIBLE);
                        mFrameLayoutMore.setVisibility(View.GONE);
                        return true;

                    case R.id.bottombaritem_more:
                        if (!isAddedFour) {
                            isAddedFour = true;
                            FragmentTransaction transactionTwo = getChildFragmentManager().beginTransaction();
                            transactionTwo.replace(R.id.fragment_container_more, new LogInFragment(), "Four");
                            transactionTwo.commit();
                        }
                        mFrameLayoutOne.setVisibility(View.GONE);
                        mFrameLayoutCategory.setVisibility(View.GONE);
                        mFrameLayoutOrder.setVisibility(View.GONE);
                        mFrameLayoutMore.setVisibility(View.VISIBLE);
                        return true;


                }

                return false;
            }
        });
    }

    @Override
    public void showData() {

    }

    private void setFragmentInTab() {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_one, new FragmentOne(), "One");
        transaction.commit();
    }
}

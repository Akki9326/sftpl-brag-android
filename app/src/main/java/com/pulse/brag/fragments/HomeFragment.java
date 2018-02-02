package com.pulse.brag.fragments;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pulse.brag.BragApp;
import com.pulse.brag.R;
import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.views.CustomTypefaceSpan;

/**
 * Created by nikhil.vadoliya on 03-10-2017.
 */


public class HomeFragment extends BaseFragment implements BaseInterface {

    View mView;

    BottomNavigationView mNavigationView;
    BottomNavigationMenuView bottomNavigationMenuView;
    BottomNavigationItemView itemView;
    FrameLayout mFrameLayoutCategory;
    FrameLayout mFrameLayoutCollection;
    FrameLayout mFrameLayoutOrder;
    FrameLayout mFrameLayoutMore;

    //badge
    TextView txtBadge;
    View badge;

    boolean isAddedCategory, isAddedCollection, isAddedOrder, isAddedMore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
            initializeData();
            setListeners();
            setNotificationBadge();
            showData();
        }
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mUpdateNotification,
                new IntentFilter(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbar();
    }

    @Override
    public void setToolbar() {


        switch (mNavigationView.getSelectedItemId()) {
            case R.id.bottombar_item_categoty:
                ((BaseActivity) getActivity()).showToolbar(false, true, true);
                break;
            case R.id.bottombar_item_collection:
                ((BaseActivity) getActivity()).showToolbar(false, true, true);

                break;
            case R.id.bottombar_item_order:
                ((BaseActivity) getActivity()).showToolbar(false, false, false, getString(R.string.toolbar_label_quick_order));

                break;
            case R.id.bottombar_item_more:
                ((BaseActivity) getActivity()).showToolbar(false, false, false, getString(R.string.toolbar_label_more));

                break;
        }
    }

    @Override
    public void initializeData() {
        mNavigationView = (BottomNavigationView) mView.findViewById(R.id.bottom_sheet);
        Utility.removeShiftModeInBottomNavigation(mNavigationView);
        bottomNavigationMenuView = (BottomNavigationMenuView) mNavigationView.getChildAt(0);

//        BottomNavigationMenuView menuView = (BottomNavigationMenuView) mNavigationView.getChildAt(0);
//        for (int i = 0; i < menuView.getChildCount(); i++) {
//            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
//            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
//            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
//            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
//            iconView.setLayoutParams(layoutParams);
//        }


        //apply  font to BottonNavigationBar
        Menu menu = mNavigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            applyFontToMenuItem(mi);
        }
        mFrameLayoutCategory = (FrameLayout) mView.findViewById(R.id.fragment_container_category);
        mFrameLayoutCollection = (FrameLayout) mView.findViewById(R.id.fragment_container_collection);
        mFrameLayoutOrder = (FrameLayout) mView.findViewById(R.id.fragment_container_order);
        mFrameLayoutMore = (FrameLayout) mView.findViewById(R.id.fragment_container_more);

        setFragmentInTab();
        isAddedCategory = true;


    }

    private void setNotificationBadge() {

        View v = bottomNavigationMenuView.getChildAt(3);
        itemView = (BottomNavigationItemView) v;
        badge = LayoutInflater.from(getActivity())
                .inflate(R.layout.bottom_navigation_notification_badge, bottomNavigationMenuView, false);
        txtBadge = badge.findViewById(R.id.notifications_badge);
        if (BragApp.NotificationNumber > 0) {
            itemView.addView(badge);
            txtBadge.setText(Utility.getBadgeNumber(BragApp.NotificationNumber));
        }

    }

    @Override
    public void setListeners() {

        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.bottombar_item_categoty:
                        mFrameLayoutCategory.setVisibility(View.VISIBLE);
                        mFrameLayoutCollection.setVisibility(View.GONE);
                        mFrameLayoutOrder.setVisibility(View.GONE);
                        mFrameLayoutMore.setVisibility(View.GONE);

                        ((BaseActivity) getActivity()).showToolbar(false, true, true);
                        return true;

                    case R.id.bottombar_item_collection:

                        if (!isAddedCollection) {
                            isAddedCollection = true;
                            FragmentTransaction transactionTwo = getChildFragmentManager().beginTransaction();
                            transactionTwo.replace(R.id.fragment_container_collection, new CollectionFragment(), "Collection_Tag");
                            transactionTwo.commit();
                        }
                        mFrameLayoutCategory.setVisibility(View.GONE);
                        mFrameLayoutCollection.setVisibility(View.VISIBLE);
                        mFrameLayoutOrder.setVisibility(View.GONE);
                        mFrameLayoutMore.setVisibility(View.GONE);

                        ((BaseActivity) getActivity()).showToolbar(false, true, true);
                        return true;

                    case R.id.bottombar_item_order:

                        if (!isAddedOrder) {
                            isAddedOrder = true;
                            FragmentTransaction transactionTwo = getChildFragmentManager().beginTransaction();
                            transactionTwo.replace(R.id.fragment_container_order, new QuickOrderFragment(), "Quick_Order_Tag");
                            transactionTwo.commit();
                        }
                        mFrameLayoutCategory.setVisibility(View.GONE);
                        mFrameLayoutCollection.setVisibility(View.GONE);
                        mFrameLayoutOrder.setVisibility(View.VISIBLE);
                        mFrameLayoutMore.setVisibility(View.GONE);

                        ((BaseActivity) getActivity()).showToolbar(false, false, false, getString(R.string.toolbar_label_quick_order));

                        return true;

                    case R.id.bottombar_item_more:
                        if (!isAddedMore) {
                            isAddedMore = true;
                            FragmentTransaction transactionTwo = getChildFragmentManager().beginTransaction();
                            transactionTwo.replace(R.id.fragment_container_more, new MoreFragment(), "More_Tag");
                            transactionTwo.commit();
                        }
                        mFrameLayoutCategory.setVisibility(View.GONE);
                        mFrameLayoutCollection.setVisibility(View.GONE);
                        mFrameLayoutOrder.setVisibility(View.GONE);
                        mFrameLayoutMore.setVisibility(View.VISIBLE);

                        ((BaseActivity) getActivity()).showToolbar(false, false, false, getString(R.string.toolbar_label_more));
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
        transaction.replace(R.id.fragment_container_category, new CategoryFragment(), "Category_Tag");
        transaction.commit();

        ((BaseActivity) getActivity()).showToolbar(false, true, true);

    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "font/opensans_regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private BroadcastReceiver mUpdateNotification = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BragApp.NotificationNumber > 0) {
                txtBadge.setText(Utility.getBadgeNumber(BragApp.NotificationNumber));
            } else {
                itemView.removeView(badge);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mUpdateNotification);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            setToolbar();
    }
}

package com.ragtagger.brag.ui.home;

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
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.R;
import com.ragtagger.brag.databinding.FragmentHomeBinding;
import com.ragtagger.brag.ui.collection.CollectionFragment;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.home.category.CategoryFragment;
import com.ragtagger.brag.ui.home.product.list.ProductListFragment;
import com.ragtagger.brag.ui.more.MoreFragment;
import com.ragtagger.brag.ui.toolbar.ToolbarActivity;
import com.ragtagger.brag.utils.AppLogger;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.views.CustomTypefaceSpan;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

/**
 * Created by nikhil.vadoliya on 03-10-2017.
 */


public class HomeFragment extends CoreFragment<FragmentHomeBinding, HomeViewModel> implements HomeNavigator {


    @Inject
    HomeViewModel homeViewModel;
    FragmentHomeBinding mFragmentHomeBinding;

    BottomNavigationMenuView bottomNavigationMenuView;
    BottomNavigationItemView itemView;
    //badge
    TextView txtBadge;
    View badge;

    boolean isAddedCategory, isAddedCollection, isAddedOrder, isAddedMore;

    private BroadcastReceiver mUpdateNotificationMore = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setNotificationBadge();
        }
    };

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeViewModel.setNavigator(this);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mUpdateNotificationMore,
                new IntentFilter(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION_PUSH_MORE));
    }

    @Override
    public void beforeViewCreated() {

    }

    @Override
    public void afterViewCreated() {
        mFragmentHomeBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), mFragmentHomeBinding.baseLayout);
        Utility.removeShiftModeInBottomNavigation(mFragmentHomeBinding.bottomNavigation);

        //Bottom sheet icon size change
        bottomNavigationMenuView = (BottomNavigationMenuView) (mFragmentHomeBinding.bottomNavigation).getChildAt(0);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) (mFragmentHomeBinding.bottomNavigation).getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        //apply  custom font to BottonNavigationBar
        Menu menu = (mFragmentHomeBinding.bottomNavigation).getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            applyFontToMenuItem(mi);
        }

        //Default categoryFragment Add
        setFragmentInTab();
        isAddedCategory = true;
        initNotificationBadge();
    }


    @Override
    public void setUpToolbar() {
        if (isAdded()) {
            //((ToolbarActivity) getActivity()).showToolbar(false, true, true);
            switch (mFragmentHomeBinding.bottomNavigation.getSelectedItemId()) {
                case R.id.bottombar_item_categoty:
                    setToolbarCategory();
                    AppLogger.e(TAG + ": setUpToolbar:  Category");
                    break;
                case R.id.bottombar_item_collection:
                    setToolbarCollection();
                    AppLogger.e(TAG + ": setUpToolbar:  Collection");
                    break;
                case R.id.bottombar_item_order:
                    setToolbarQuickOrder();
                    AppLogger.e(TAG + ": setUpToolbar:  Quick Order");
                    break;
                case R.id.bottombar_item_more:
                    setToolbarMore();
                    AppLogger.e(TAG + ": setUpToolbar:  More");
                    break;
            }
        }

    }

    @Override
    public HomeViewModel getViewModel() {
        return homeViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            setUpToolbar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mUpdateNotificationMore);
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "font/opensans_regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void setNotificationBadge() {
        if (isAdded()) {
            if (!Utility.isConnection(getActivity())) {
                if (((ViewGroup) bottomNavigationMenuView.findViewById(R.id.bottombar_item_more)).getChildCount() > 2)
                    ((ViewGroup) bottomNavigationMenuView.findViewById(R.id.bottombar_item_more)).removeViewAt(2);
            } else {
                if (BragApp.NotificationNumber > 0) {
                    //if badge layout not added
                    if (itemView.getChildCount() == 2) {
                        initNotificationBadge();
                    }
                    txtBadge.setText(Utility.getBadgeNumber(BragApp.NotificationNumber));
                } else {
                    if (((ViewGroup) bottomNavigationMenuView.findViewById(R.id.bottombar_item_more)).getChildCount() > 2)
                        ((ViewGroup) bottomNavigationMenuView.findViewById(R.id.bottombar_item_more)).removeViewAt(2);
                }
            }
            //update in More screen list
            Intent intent = new Intent(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION_MORE);
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        }
    }

    public void initNotificationBadge() {
        View v = bottomNavigationMenuView.getChildAt(3);
        itemView = (BottomNavigationItemView) v;
        badge = LayoutInflater.from(getActivity())
                .inflate(R.layout.bottom_navigation_notification_badge, itemView, false);
        txtBadge = badge.findViewById(R.id.notifications_badge);

        itemView.addView(badge);
        setNotificationBadge();
    }


    private void setFragmentInTab() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_category, CategoryFragment.getInstance(), "Category_Tag");
        transaction.commit();
    }

    @Override
    public void openCategoryFragment() {
        mFragmentHomeBinding.fragmentContainerCategory.setVisibility(View.VISIBLE);
        mFragmentHomeBinding.fragmentContainerCollection.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerOrder.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerMore.setVisibility(View.GONE);
    }

    @Override
    public void setToolbarCategory() {
        if (mActivity != null && mActivity instanceof ToolbarActivity)
            ((ToolbarActivity) mActivity).showToolbar(false, true, true);
    }

    @Override
    public void openCollectionFragment() {
        if (!isAddedCollection) {
            isAddedCollection = true;
            FragmentTransaction transactionTwo = getChildFragmentManager().beginTransaction();
            transactionTwo.replace(R.id.fragment_container_collection, CollectionFragment.getInstance(), "Collection_Tag");
            transactionTwo.commit();
        }
        mFragmentHomeBinding.fragmentContainerCategory.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerCollection.setVisibility(View.VISIBLE);
        mFragmentHomeBinding.fragmentContainerOrder.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerMore.setVisibility(View.GONE);
    }

    @Override
    public void setToolbarCollection() {
        if (mActivity != null && mActivity instanceof ToolbarActivity)
            ((ToolbarActivity) mActivity).showToolbar(false, true, true);
    }

    @Override
    public void openQuickOrderFragement() {
        if (!isAddedOrder) {
            isAddedOrder = true;
            FragmentTransaction transactionTwo = getChildFragmentManager().beginTransaction();
            transactionTwo.replace(R.id.fragment_container_order, ProductListFragment.newInstance(true), "Quick_Order_Tag");
            transactionTwo.commit();
        }
        mFragmentHomeBinding.fragmentContainerCategory.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerCollection.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerOrder.setVisibility(View.VISIBLE);
        mFragmentHomeBinding.fragmentContainerMore.setVisibility(View.GONE);
    }

    @Override
    public void setToolbarQuickOrder() {
        if (mActivity != null && mActivity instanceof ToolbarActivity)
            ((ToolbarActivity) mActivity).showToolbar(false, false, true, getString(R.string.toolbar_label_quick_order));
    }

    @Override
    public void openMoreFragment() {
        if (!isAddedMore) {
            isAddedMore = true;
            FragmentTransaction transactionTwo = getChildFragmentManager().beginTransaction();
            transactionTwo.replace(R.id.fragment_container_more, MoreFragment.getInstance(), "More_Tag");
            transactionTwo.commit();
        }
        mFragmentHomeBinding.fragmentContainerCategory.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerCollection.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerOrder.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void setToolbarMore() {
        if (mActivity != null && mActivity instanceof ToolbarActivity)
            ((ToolbarActivity) mActivity).showToolbar(false, false, false, getString(R.string.toolbar_label_more));
    }


}

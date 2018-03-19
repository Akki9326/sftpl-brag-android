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
import android.util.Log;
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
import com.ragtagger.brag.ui.core.CoreActivity;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.home.category.CategoryFragment;
import com.ragtagger.brag.ui.home.product.list.ProductListFragment;
import com.ragtagger.brag.ui.more.MoreFragment;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.views.CustomTypefaceSpan;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;
import static com.ragtagger.brag.utils.Constants.BUNDLE_KEY_PRODUCT_LIST_TITLE;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel.setNavigator(this);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mUpdateNotification,
                new IntentFilter(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION));

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

    }


    @Override
    public void setUpToolbar() {

        Log.i(TAG, "setUpToolbar: " + mFragmentHomeBinding.bottomNavigation.getSelectedItemId());
        ((CoreActivity) getActivity()).showToolbar(false, true, true);
        switch (mFragmentHomeBinding.bottomNavigation.getSelectedItemId()) {
            case R.id.bottombar_item_categoty:
                setToolbarCategory();
                Log.i(TAG, "setUpToolbar:  category");
                break;
            case R.id.bottombar_item_collection:
                setToolbarCollection();
                Log.i(TAG, "setUpToolbar:  collection");
                break;
            case R.id.bottombar_item_order:
                setToolbarQuickOrder();
                Log.i(TAG, "setUpToolbar:  order");
                break;
            case R.id.bottombar_item_more:
                setToolbarMore();
                Log.i(TAG, "setUpToolbar:  more");
                break;
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


    public void setNotificationBadge() {

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


    private void setFragmentInTab() {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_category, new CategoryFragment(), "Category_Tag");
        transaction.commit();

        ((CoreActivity) getActivity()).showToolbar(false, true, true);

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
            // TODO: 16-02-2018 set toolbar by bottomsheet selected
            setUpToolbar();

    }

    @Override
    public void openCategoryFragment() {
        mFragmentHomeBinding.fragmentContainerCategory.setVisibility(View.VISIBLE);
        mFragmentHomeBinding.fragmentContainerCollection.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerOrder.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerMore.setVisibility(View.GONE);
    }

    @Override
    public void openCollectionFragment() {
        if (!isAddedCollection) {
            isAddedCollection = true;
            FragmentTransaction transactionTwo = getChildFragmentManager().beginTransaction();
            transactionTwo.replace(R.id.fragment_container_collection, new CollectionFragment(), "Collection_Tag");
            transactionTwo.commit();
        }
        mFragmentHomeBinding.fragmentContainerCategory.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerCollection.setVisibility(View.VISIBLE);
        mFragmentHomeBinding.fragmentContainerOrder.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerMore.setVisibility(View.GONE);
    }

    @Override
    public void openQuickOrderFragement() {
        if (!isAddedOrder) {
            isAddedOrder = true;
            FragmentTransaction transactionTwo = getChildFragmentManager().beginTransaction();

            Bundle bundle = new Bundle();
            bundle.putString(BUNDLE_KEY_PRODUCT_LIST_TITLE, getString(R.string.label_quick_order));
            ProductListFragment fragment = new ProductListFragment();
            fragment.setArguments(bundle);
            transactionTwo.replace(R.id.fragment_container_order, fragment, "Quick_Order_Tag");
            transactionTwo.commit();
        }
        mFragmentHomeBinding.fragmentContainerCategory.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerCollection.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerOrder.setVisibility(View.VISIBLE);
        mFragmentHomeBinding.fragmentContainerMore.setVisibility(View.GONE);

    }

    @Override
    public void openMoreFragment() {
        if (!isAddedMore) {
            isAddedMore = true;
            FragmentTransaction transactionTwo = getChildFragmentManager().beginTransaction();
            transactionTwo.replace(R.id.fragment_container_more, new MoreFragment(), "More_Tag");
            transactionTwo.commit();
        }
        mFragmentHomeBinding.fragmentContainerCategory.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerCollection.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerOrder.setVisibility(View.GONE);
        mFragmentHomeBinding.fragmentContainerMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void setToolbarCategory() {
        ((CoreActivity) (getActivity())).showToolbar(false, true, true);
    }

    @Override
    public void setToolbarCollection() {
        ((CoreActivity) (getActivity())).showToolbar(false, true, true);
    }

    @Override
    public void setToolbarQuickOrder() {
        ((CoreActivity) (getActivity())).showToolbar(false, false, false, getString(R.string.toolbar_label_quick_order));

    }

    @Override
    public void setToolbarMore() {
        ((CoreActivity) (getActivity())).showToolbar(false, false, false, getString(R.string.toolbar_label_more));
    }


}

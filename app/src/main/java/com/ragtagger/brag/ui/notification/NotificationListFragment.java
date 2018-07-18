package com.ragtagger.brag.ui.notification;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.IOnItemClickListener;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataNotificationList;
import com.ragtagger.brag.data.model.response.RNotificationList;
import com.ragtagger.brag.databinding.FragmentNotificationListBinding;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.home.HomeFragment;
import com.ragtagger.brag.ui.home.product.details.ProductDetailFragment;
import com.ragtagger.brag.ui.main.MainActivity;
import com.ragtagger.brag.ui.notification.adapter.NotificationListAdapter;
import com.ragtagger.brag.ui.order.orderdetail.OrderDetailFragment;
import com.ragtagger.brag.ui.toolbar.ToolbarActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.views.erecyclerview.loadmore.DefaultLoadMoreFooter;
import com.ragtagger.brag.views.erecyclerview.loadmore.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 18-12-2017.
 */


public class NotificationListFragment extends CoreFragment<FragmentNotificationListBinding, NotificationListViewModel> implements NotificationListNavigator, IOnItemClickListener {

    private static final int LOAD_LIST = 1;
    private static final int LOAD_MORE = 5;

    @Inject
    NotificationListViewModel mNotificationListViewModel;
    FragmentNotificationListBinding mFragmentNotificationListBinding;

    NotificationListAdapter mListAdapter;
    List<DataNotificationList> mListData;

    private int ACTION = 0;
    private int PAGE_NUM = 1;

    int totalNotification;
    int position;
    boolean isListApi = false;
    private long mLastClickTime = 0;

    private OnLoadMoreListener mOnLoadMoreListener = new OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mFragmentNotificationListBinding.swipeRefreshLayout.isRefreshing()) {
                        mFragmentNotificationListBinding.recycleviewNotification.loadMoreComplete(false);
                        return;
                    }

                    if (mListData.size() != totalNotification) {
                        ACTION = LOAD_MORE;
                        checkInternetAndCallApi(false);
                    } else {
                        mFragmentNotificationListBinding.recycleviewNotification.loadMoreComplete(true);
                    }
                }
            }, 500);
        }
    };

    public static NotificationListFragment getInstance() {
        return new NotificationListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationListViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
        mListData = new ArrayList<>();
        mListAdapter = new NotificationListAdapter(getActivity(), mListData, this);
    }

    @Override
    public void afterViewCreated() {
        mFragmentNotificationListBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), mFragmentNotificationListBinding.baseLayout);

        mNotificationListViewModel.setListVisibility(true);

        mFragmentNotificationListBinding.recycleviewNotification.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mFragmentNotificationListBinding.recycleviewNotification.setLayoutManager(layoutManager);
        mFragmentNotificationListBinding.recycleviewNotification.setMotionEventSplittingEnabled(false);
        mFragmentNotificationListBinding.recycleviewNotification.setPageSize(20);
        mFragmentNotificationListBinding.recycleviewNotification.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        mFragmentNotificationListBinding.recycleviewNotification.setLoadMoreView(DefaultLoadMoreFooter.getResource(), null);
        mFragmentNotificationListBinding.recycleviewNotification.setOnLoadMoreListener(mOnLoadMoreListener);


        mFragmentNotificationListBinding.layoutNoInternet.textviewRetry.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                checkInternetAndCallApi(true);
            }
        });

        PAGE_NUM = 1;
        ACTION = LOAD_LIST;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkInternetAndCallApi(true);
            }
        }, 300);

    }

    @Override
    public void setUpToolbar() {
        ((ToolbarActivity) mActivity).showToolbar(true, false, false, Utility.getNotificationlabel(mActivity));
    }

    @Override
    public NotificationListViewModel getViewModel() {
        return mNotificationListViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_notification_list;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            setUpToolbar();
    }

    private void checkInternetAndCallApi(boolean isShowLoader) {
        if (isAdded()) {
            isListApi = true;
            if (Utility.isConnection(getActivity())) {
                ((ToolbarActivity) mActivity).enableBackButton(false);
                mNotificationListViewModel.setNoInternet(false);
                if (ACTION == LOAD_MORE) {
                    PAGE_NUM++;
                } else {
                    //for pull to refresh
                    if (isShowLoader)
                        showProgress();
                    PAGE_NUM = 1;
                }
                mNotificationListViewModel.callGetNotificationListApi(PAGE_NUM);
            } else {
                switch (ACTION) {
                    case LOAD_LIST:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mNotificationListViewModel.setNoInternet(true);
                            }
                        }, 200);

                        break;
                    case LOAD_MORE:
                        AlertUtils.showAlertMessage(getActivity(), 0, null, null);
                        break;
                }
                hideLoader();
                mFragmentNotificationListBinding.recycleviewNotification.loadMoreComplete(false);
                ((ToolbarActivity) mActivity).enableBackButton(true);
            }
        }
    }

    public void hideLoader() {
        if (mFragmentNotificationListBinding.swipeRefreshLayout.isRefreshing()) {
            mFragmentNotificationListBinding.swipeRefreshLayout.setRefreshing(false);
        } else {
            hideProgress();
            mFragmentNotificationListBinding.recycleviewNotification.loadMoreComplete(false);
        }
    }

    @Override
    public void onItemClick(int position) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        this.position = position;

        DataNotificationList dataNotification = mListData.get(position);
        //read notification api
        isListApi = false;
        switch (Constants.NotificationType.values()[dataNotification.getNotificationType()]) {
            case TEXT:
                break;
            case USER:
                break;
            case ITEM:
                ((MainActivity) getActivity()).pushFragments(ProductDetailFragment.newInstance(dataNotification.getWhatId(), true, false), true, true);
                break;
            case ORDER:
                ((MainActivity) getActivity()).pushFragments(OrderDetailFragment.newInstance(dataNotification.getWhatId()), true, true);
                break;
            default:
                AlertUtils.showAlertMessage(getActivity(), 1, null, null);
        }


        if (Utility.isConnection(getActivity())) {
            if (!dataNotification.isAttended())
                mNotificationListViewModel.notificationRead(dataNotification.getId());
        } else {
            setNotificationRead();
        }
    }

    @Override
    public void performSwipeToRefresh() {
        if (!mFragmentNotificationListBinding.recycleviewNotification.isLoadingData()) {
            mFragmentNotificationListBinding.swipeRefreshLayout.setRefreshing(true);
            ACTION = LOAD_LIST;
            mFragmentNotificationListBinding.recycleviewNotification.setIsLoadingMore(true);
            checkInternetAndCallApi(false);
        } else {
            mFragmentNotificationListBinding.swipeRefreshLayout.setRefreshing(false);
            mFragmentNotificationListBinding.recycleviewNotification.setIsLoadingMore(true);
        }
    }

    @Override
    public void setNotificationList(RNotificationList notificationList, List<DataNotificationList> lists) {
        switch (ACTION) {
            case LOAD_LIST:
                totalNotification = notificationList.getCount();
                mListData.clear();
                mListData.addAll(lists);
                mListAdapter = new NotificationListAdapter(getActivity(), mListData, this);
                mFragmentNotificationListBinding.recycleviewNotification.setAdapter(mListAdapter);
                mFragmentNotificationListBinding.swipeRefreshLayout.setRefreshing(false);

                //issue of space in bottom of recycleview in last item when total item size small than 20;
                if (totalNotification <= 20)
                    mFragmentNotificationListBinding.recycleviewNotification.setIsLoadingMore(false);

                mNotificationListViewModel.setListVisibility(!mListData.isEmpty());

                break;
            case LOAD_MORE:
                mListData.addAll(lists);
                mListAdapter.notifyDataSetChanged();
                mFragmentNotificationListBinding.recycleviewNotification.loadMoreComplete(false);

                break;
        }
        ((ToolbarActivity) mActivity).enableBackButton(true);

    }


    @Override
    public void setNotificationRead() {
        if (!mListData.get(position).isAttended()) {
            mListData.get(position).setAttended(true);
            mListAdapter.notifyDataSetChanged();
            BragApp.NotificationNumber--;
        }
        //update in More screen
        Intent intent = new Intent(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    @Override
    public void setNotificationReadFailed() {
        setNotificationRead();
    }

    @Override
    public void onApiSuccess() {
        hideLoader();
        setUpToolbar();
        //updated notification count display
        Intent intent = new Intent(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    @Override
    public void onApiError(ApiError error) {
        hideLoader();
        if (isListApi && (error.getHttpCode() == 0 || error.getHttpCode() == Constants.IErrorCode.notInternetConnErrorCode)) {
            switch (ACTION) {
                case LOAD_LIST:
                    mNotificationListViewModel.setNoInternet(true);
                    break;
                case LOAD_MORE:
                    AlertUtils.showAlertMessage(getActivity(), 0, null, null);
                    break;
            }
            return;
        }

        isListApi = true;
        ((ToolbarActivity) mActivity).enableBackButton(true);
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }

}

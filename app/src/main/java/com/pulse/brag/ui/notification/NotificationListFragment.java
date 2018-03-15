package com.pulse.brag.ui.notification;


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
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.pulse.brag.BR;
import com.pulse.brag.BragApp;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.datas.DataNotificationList;
import com.pulse.brag.databinding.FragmentNotificationListBinding;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.notification.adapter.NotificationListAdapter;
import com.pulse.brag.views.erecyclerview.loadmore.DefaultLoadMoreFooter;
import com.pulse.brag.views.erecyclerview.loadmore.OnLoadMoreListener;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.callback.IOnItemClickListener;
import com.pulse.brag.callback.OnSingleClickListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 18-12-2017.
 */


public class NotificationListFragment extends CoreFragment<FragmentNotificationListBinding, NotificationListViewModel> implements NotificationListNavigator, IOnItemClickListener {

    @Inject
    NotificationListViewModel mNotificationListViewModel;

    FragmentNotificationListBinding mFragmentNotificationListBinding;

    static int ACTION = 0;
    static final int LOAD_MORE = 2;
    static final int LOAD_LIST = 1;

    NotificationListAdapter mListAdapter;
    List<DataNotificationList> mListData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationListViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
        mListData = new ArrayList<>();
        mListAdapter = new NotificationListAdapter(getActivity(), mListData, this);

        ACTION = LOAD_LIST;
    }

    @Override
    public void afterViewCreated() {
        mFragmentNotificationListBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), mFragmentNotificationListBinding.baseLayout);

        mFragmentNotificationListBinding.recycleviewNotification.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mFragmentNotificationListBinding.recycleviewNotification.setLayoutManager(layoutManager);
        mFragmentNotificationListBinding.recycleviewNotification.setMotionEventSplittingEnabled(false);
        mFragmentNotificationListBinding.recycleviewNotification.setPageSize(10);
        mFragmentNotificationListBinding.recycleviewNotification.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        mFragmentNotificationListBinding.recycleviewNotification.setLoadMoreView(DefaultLoadMoreFooter.getResource(), null);
        mFragmentNotificationListBinding.recycleviewNotification.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (mListData.size() != 60) {
                            ACTION = LOAD_MORE;
                            checkInternet();
                        } else {
                            mFragmentNotificationListBinding.recycleviewNotification.loadMoreComplete(true);
                        }
                    }
                }, 500);
            }
        });

        mActivity.mTxtReadAll.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                BragApp.NotificationNumber = 0;
                Intent intent = new Intent(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                setUpToolbar();
            }
        });

        checkInternet();
    }

    @Override
    public void setUpToolbar() {
        if (BragApp.NotificationNumber > 0) {
            mActivity.showToolbar(true, false,
                    Utility.getNotificationlabel(getActivity()), getString(R.string.toolbar_label_right_read_all));
        } else {
            mActivity.showToolbar(true, false, false, Utility.getNotificationlabel(getActivity()));
        }
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

    private void checkInternet() {
        if (Utility.isConnection(getContext())) {
            showData();
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null, null);
            mFragmentNotificationListBinding.recycleviewNotification.loadMoreComplete(false);
        }
    }

    public void showData() {

        if (ACTION == LOAD_LIST) {
            mListData.clear();

            mListData.add(new DataNotificationList("1", "Lorem Ipsum is simply dummy text of the printing and typesetting ",
                    "Entrance strongly packages she out rendered get quitting denoting led. Dwelling confined improved it he no doubtful raptures. Several carried through an of up attempt gravity. Situation to be at offending elsewhere distrusts if. ", false));
            mListData.add(new DataNotificationList("1", "It is a long established fact that a reader will be distracted by the ",
                    "Particular use for considered projection cultivated. Worth of do doubt shall it their. Extensive existence up me contained he pronounce do. Excellence inquietude assistance precaution any impression man sufficient. ", true));
            mListData.add(new DataNotificationList("1", "Text ever since the 1500s, when an unknown printer took a galley ",
                    ". Justice joy manners boy met resolve produce. Bed head loud next plan rent had easy add him. As earnestly shameless elsewhere defective estimable fulfilled of. Esteem my advice it an excuse enable.", false));
            mListData.add(new DataNotificationList("1", "His having within saw become ask passed misery giving. ",
                    "questions get too fulfilled. He fact in we case miss sake Text ever since the 1500s, when an unknown printer took a galley", false));
            mListData.add(new DataNotificationList("1", "Recommend questions get too fulfilled. He fact in we case miss sake",
                    " attempted add arranging age gentleman concluded. Get who uncommonly our expression ten increasing considered occasional travelling. Ever read tell year give may men call its. Piqued son turned fat income played end wicket.", true));
            mListData.add(new DataNotificationList("1", "Lorem Ipsum is simply dummy text of the printing and typesetting ",
                    "Entrance strongly packages she out rendered get quitting denoting led. Dwelling confined improved it he no doubtful raptures. Several carried through an of up attempt gravity. Situation to be at offending elsewhere distrusts if. ", false));
            mListData.add(new DataNotificationList("1", "It is a long established fact that a reader will be distracted by the ",
                    "Particular use for considered projection cultivated. Worth of do doubt shall it their. Extensive existence up me contained he pronounce do. Excellence inquietude assistance precaution any impression man sufficient. ", false));
            mListData.add(new DataNotificationList("1", "Text ever since the 1500s, when an unknown printer took a galley ",
                    ". Justice joy manners boy met resolve produce. Bed head loud next plan rent had easy add him. As earnestly shameless elsewhere defective estimable fulfilled of. Esteem my advice it an excuse enable.", false));
            mListData.add(new DataNotificationList("1", "His having within saw become ask passed misery giving. ",
                    "questions get too fulfilled. He fact in we case miss sake Text ever since the 1500s, when an unknown printer took a galley", false));
            mListData.add(new DataNotificationList("1", "Recommend questions get too fulfilled. He fact in we case miss sake",
                    " attempted add arranging age gentleman concluded. Get who uncommonly our expression ten increasing considered occasional travelling. Ever read tell year give may men call its. Piqued son turned fat income played end wicket.", false));

            mListAdapter = new NotificationListAdapter(getActivity(), mListData, this);
            mFragmentNotificationListBinding.recycleviewNotification.setAdapter(mListAdapter);

        } else {

            ArrayList<DataNotificationList> localList = new ArrayList<>();
            localList.add(new DataNotificationList("1", "Lorem Ipsum is simply dummy text of the printing and typesetting ",
                    "Entrance strongly packages she out rendered get quitting denoting led. Dwelling confined improved it he no doubtful raptures. Several carried through an of up attempt gravity. Situation to be at offending elsewhere distrusts if. ", true));
            localList.add(new DataNotificationList("1", "It is a long established fact that a reader will be distracted by the ",
                    "Particular use for considered projection cultivated. Worth of do doubt shall it their. Extensive existence up me contained he pronounce do. Excellence inquietude assistance precaution any impression man sufficient. ", true));
            localList.add(new DataNotificationList("1", "Text ever since the 1500s, when an unknown printer took a galley ",
                    ". Justice joy manners boy met resolve produce. Bed head loud next plan rent had easy add him. As earnestly shameless elsewhere defective estimable fulfilled of. Esteem my advice it an excuse enable.", true));
            localList.add(new DataNotificationList("1", "His having within saw become ask passed misery giving. ",
                    "questions get too fulfilled. He fact in we case miss sake Text ever since the 1500s, when an unknown printer took a galley", true));
            localList.add(new DataNotificationList("1", "Recommend questions get too fulfilled. He fact in we case miss sake",
                    " attempted add arranging age gentleman concluded. Get who uncommonly our expression ten increasing considered occasional travelling. Ever read tell year give may men call its. Piqued son turned fat income played end wicket.", true));
            localList.add(new DataNotificationList("1", "Lorem Ipsum is simply dummy text of the printing and typesetting ",
                    "Entrance strongly packages she out rendered get quitting denoting led. Dwelling confined improved it he no doubtful raptures. Several carried through an of up attempt gravity. Situation to be at offending elsewhere distrusts if. ", true));
            localList.add(new DataNotificationList("1", "It is a long established fact that a reader will be distracted by the ",
                    "Particular use for considered projection cultivated. Worth of do doubt shall it their. Extensive existence up me contained he pronounce do. Excellence inquietude assistance precaution any impression man sufficient. ", true));
            localList.add(new DataNotificationList("1", "Text ever since the 1500s, when an unknown printer took a galley ",
                    ". Justice joy manners boy met resolve produce. Bed head loud next plan rent had easy add him. As earnestly shameless elsewhere defective estimable fulfilled of. Esteem my advice it an excuse enable.", true));
            localList.add(new DataNotificationList("1", "His having within saw become ask passed misery giving. ",
                    "questions get too fulfilled. He fact in we case miss sake Text ever since the 1500s, when an unknown printer took a galley", true));
            localList.add(new DataNotificationList("1", "Recommend questions get too fulfilled. He fact in we case miss sake",
                    " attempted add arranging age gentleman concluded. Get who uncommonly our expression ten increasing considered occasional travelling. Ever read tell year give may men call its. Piqued son turned fat income played end wicket.", true));


            mListData.addAll(localList);
            mListAdapter.notifyDataSetChanged();
            mFragmentNotificationListBinding.recycleviewNotification.loadMoreComplete(false);
        }


    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onApiSuccess() {
        hideProgress();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }

    @Override
    public void readNotification() {
        BragApp.NotificationNumber--;
        Intent intent = new Intent(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        setUpToolbar();
    }
}

package com.pulse.brag.fragments;


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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pulse.brag.BragApp;
import com.pulse.brag.R;
import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.adapters.NotificationListAdapter;
import com.pulse.brag.erecyclerview.ERecyclerView;
import com.pulse.brag.erecyclerview.loadmore.DefaultLoadMoreFooter;
import com.pulse.brag.erecyclerview.loadmore.OnLoadMoreListener;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.pojo.datas.NotificationListData;
import com.pulse.brag.views.OnSingleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil.vadoliya on 18-12-2017.
 */


public class NotificationListFragment extends BaseFragment implements BaseInterface, OnItemClickListener {

    static int ACTION = 0;
    static final int LOAD_MORE = 2;
    static final int LOAD_LIST = 1;

    View mView;
    ERecyclerView mRecyclerView;
    TextView mTxtRead;
    BaseActivity mActivity;

    NotificationListAdapter mListAdapter;
    List<NotificationListData> mListData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_notification_list, container, false);
            initializeData();
            setListeners();
            ACTION = LOAD_LIST;
            checkInternet();
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbar();
    }

    private void checkInternet() {
        if (Utility.isConnection(getContext())) {
            showData();
        } else {
            Utility.showAlertMessage(getContext(), 0, null);
            mRecyclerView.loadMoreComplete(false);
        }
    }

    @Override
    public void setToolbar() {
        if (BragApp.NotificationNumber > 0) {
            mActivity.showToolbar(true, false,
                    mActivity.getNotificationlabel(), getString(R.string.toolbar_label_right_read_all));
        } else {
            mActivity.showToolbar(true, false, false, mActivity.getNotificationlabel());
        }
    }

    @Override
    public void initializeData() {

        mRecyclerView = mView.findViewById(R.id.recycleview_notification);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setMotionEventSplittingEnabled(false);
        mRecyclerView.setPageSize(10);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));

        mTxtRead = mView.findViewById(R.id.textview_read);
        mListData = new ArrayList<>();

        mActivity = ((BaseActivity) getActivity());

    }

    @Override
    public void setListeners() {

        mActivity.mTxtReadAll.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                BragApp.NotificationNumber = 0;
                Intent intent = new Intent(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                setToolbar();
            }
        });
        mTxtRead.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                BragApp.NotificationNumber--;
                Intent intent = new Intent(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                setToolbar();
            }
        });
        mRecyclerView.setLoadMoreView(DefaultLoadMoreFooter.getResource(), null);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (mListData.size() != 60) {
                            ACTION = LOAD_MORE;
                            checkInternet();
                        } else {
                            mRecyclerView.loadMoreComplete(true);
                        }
                    }
                }, 500);
            }
        });
    }

    @Override
    public void showData() {

        if (ACTION == LOAD_LIST) {
            mListData.add(new NotificationListData("1", "Lorem Ipsum is simply dummy text of the printing and typesetting ",
                    "Entrance strongly packages she out rendered get quitting denoting led. Dwelling confined improved it he no doubtful raptures. Several carried through an of up attempt gravity. Situation to be at offending elsewhere distrusts if. ", false));
            mListData.add(new NotificationListData("1", "It is a long established fact that a reader will be distracted by the ",
                    "Particular use for considered projection cultivated. Worth of do doubt shall it their. Extensive existence up me contained he pronounce do. Excellence inquietude assistance precaution any impression man sufficient. ", true));
            mListData.add(new NotificationListData("1", "Text ever since the 1500s, when an unknown printer took a galley ",
                    ". Justice joy manners boy met resolve produce. Bed head loud next plan rent had easy add him. As earnestly shameless elsewhere defective estimable fulfilled of. Esteem my advice it an excuse enable.", false));
            mListData.add(new NotificationListData("1", "His having within saw become ask passed misery giving. ",
                    "questions get too fulfilled. He fact in we case miss sake Text ever since the 1500s, when an unknown printer took a galley",false));
            mListData.add(new NotificationListData("1", "Recommend questions get too fulfilled. He fact in we case miss sake",
                    " attempted add arranging age gentleman concluded. Get who uncommonly our expression ten increasing considered occasional travelling. Ever read tell year give may men call its. Piqued son turned fat income played end wicket.", true));
            mListData.add(new NotificationListData("1", "Lorem Ipsum is simply dummy text of the printing and typesetting ",
                    "Entrance strongly packages she out rendered get quitting denoting led. Dwelling confined improved it he no doubtful raptures. Several carried through an of up attempt gravity. Situation to be at offending elsewhere distrusts if. ", false));
            mListData.add(new NotificationListData("1", "It is a long established fact that a reader will be distracted by the ",
                    "Particular use for considered projection cultivated. Worth of do doubt shall it their. Extensive existence up me contained he pronounce do. Excellence inquietude assistance precaution any impression man sufficient. ", false));
            mListData.add(new NotificationListData("1", "Text ever since the 1500s, when an unknown printer took a galley ",
                    ". Justice joy manners boy met resolve produce. Bed head loud next plan rent had easy add him. As earnestly shameless elsewhere defective estimable fulfilled of. Esteem my advice it an excuse enable.", false));
            mListData.add(new NotificationListData("1", "His having within saw become ask passed misery giving. ",
                    "questions get too fulfilled. He fact in we case miss sake Text ever since the 1500s, when an unknown printer took a galley",false));
            mListData.add(new NotificationListData("1", "Recommend questions get too fulfilled. He fact in we case miss sake",
                    " attempted add arranging age gentleman concluded. Get who uncommonly our expression ten increasing considered occasional travelling. Ever read tell year give may men call its. Piqued son turned fat income played end wicket.", false));

            mListAdapter = new NotificationListAdapter(getActivity(), mListData, this);
            mRecyclerView.setAdapter(mListAdapter);

        } else {
            mListData.add(new NotificationListData("1", "Lorem Ipsum is simply dummy text of the printing and typesetting ",
                    "Entrance strongly packages she out rendered get quitting denoting led. Dwelling confined improved it he no doubtful raptures. Several carried through an of up attempt gravity. Situation to be at offending elsewhere distrusts if. ", true));
            mListData.add(new NotificationListData("1", "It is a long established fact that a reader will be distracted by the ",
                    "Particular use for considered projection cultivated. Worth of do doubt shall it their. Extensive existence up me contained he pronounce do. Excellence inquietude assistance precaution any impression man sufficient. ", true));
            mListData.add(new NotificationListData("1", "Text ever since the 1500s, when an unknown printer took a galley ",
                    ". Justice joy manners boy met resolve produce. Bed head loud next plan rent had easy add him. As earnestly shameless elsewhere defective estimable fulfilled of. Esteem my advice it an excuse enable.", true));
            mListData.add(new NotificationListData("1", "His having within saw become ask passed misery giving. ",
                    "questions get too fulfilled. He fact in we case miss sake Text ever since the 1500s, when an unknown printer took a galley",true));
            mListData.add(new NotificationListData("1", "Recommend questions get too fulfilled. He fact in we case miss sake",
                    " attempted add arranging age gentleman concluded. Get who uncommonly our expression ten increasing considered occasional travelling. Ever read tell year give may men call its. Piqued son turned fat income played end wicket.", true));
            mListData.add(new NotificationListData("1", "Lorem Ipsum is simply dummy text of the printing and typesetting ",
                    "Entrance strongly packages she out rendered get quitting denoting led. Dwelling confined improved it he no doubtful raptures. Several carried through an of up attempt gravity. Situation to be at offending elsewhere distrusts if. ", true));
            mListData.add(new NotificationListData("1", "It is a long established fact that a reader will be distracted by the ",
                    "Particular use for considered projection cultivated. Worth of do doubt shall it their. Extensive existence up me contained he pronounce do. Excellence inquietude assistance precaution any impression man sufficient. ", true));
            mListData.add(new NotificationListData("1", "Text ever since the 1500s, when an unknown printer took a galley ",
                    ". Justice joy manners boy met resolve produce. Bed head loud next plan rent had easy add him. As earnestly shameless elsewhere defective estimable fulfilled of. Esteem my advice it an excuse enable.", true));
            mListData.add(new NotificationListData("1", "His having within saw become ask passed misery giving. ",
                    "questions get too fulfilled. He fact in we case miss sake Text ever since the 1500s, when an unknown printer took a galley",true));
            mListData.add(new NotificationListData("1", "Recommend questions get too fulfilled. He fact in we case miss sake",
                    " attempted add arranging age gentleman concluded. Get who uncommonly our expression ten increasing considered occasional travelling. Ever read tell year give may men call its. Piqued son turned fat income played end wicket.", true));

            mListAdapter.notifyDataSetChanged();
            mRecyclerView.loadMoreComplete(false);
        }


    }

    @Override
    public void onItemClick(int position) {

    }
}

package com.ragtagger.brag.ui.order.orderstatushistory;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.android.databinding.library.baseAdapters.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataOrderStatus;
import com.ragtagger.brag.databinding.FragmentOrderStatusBinding;
import com.ragtagger.brag.ui.core.CoreActivity;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.views.verticalstepper.VerticalStepperView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 02-04-2018.
 */


public class OrderStatusFragment extends CoreFragment<FragmentOrderStatusBinding, OrderStatusViewModel>
        implements OrderStatusNavigator {

    @Inject
    OrderStatusViewModel mOrderStatusViewModel;
    FragmentOrderStatusBinding mFragmentOrderStatusBinding;

    MainStepperAdapter mainStepperAdapter;

    private int mInterval = 1000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    int i;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderStatusViewModel.setNavigator(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_status;
    }

    @Override
    public void beforeViewCreated() {

    }

    @Override
    public void afterViewCreated() {
        mFragmentOrderStatusBinding = getViewDataBinding();


       /* mainStepperAdapter = new MainStepperAdapter(getActivity(), dataOrderStatuses);


        mFragmentOrderStatusBinding.stepperList.setAdapter(mainStepperAdapter);
//        mHandler = new Handler();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startRepeatingTask();
//            }
//        }, 1000);

        for (int i = 0; i < dataOrderStatuses.size()-1; i++) {
            mainStepperAdapter.next();
        }*/
    }

    @Override
    public void setUpToolbar() {
        ((CoreActivity) getActivity()).showToolbar(true, false, false, getString(R.string.toolbar_label_order_status));
    }

    @Override
    public OrderStatusViewModel getViewModel() {
        return mOrderStatusViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onApiSuccess() {

    }

    @Override
    public void onApiError(ApiError error) {

    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                i++;
                mainStepperAdapter.next();
            } finally {
                if (i == mainStepperAdapter.getCount()) {
                    stopRepeatingTask();
                }
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };
}

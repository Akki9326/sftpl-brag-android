package com.pulse.brag.ui.authentication.profile.addeditaddress.statedialog;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.datas.StateListRespone;
import com.pulse.brag.databinding.DialogFragmentListSelectorBinding;
import com.pulse.brag.ui.authentication.profile.addeditaddress.statedialog.adapter.StateListAdapter;
import com.pulse.brag.ui.core.CoreDialogFragment;
import com.pulse.brag.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 07-03-2018.
 */


public class StateDialogFragement extends CoreDialogFragment<DialogFragmentListSelectorBinding, StateDialogViewModel>
        implements StateDialogNavigator {


    @Inject
    StateDialogViewModel mStateDialogViewModel;

    DialogFragmentListSelectorBinding mDialogListSelectorBinding;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_DialogWhenLarge_NoActionBar);
        mStateDialogViewModel.setNavigator(this);
    }

    @Override
    public Dialog onCreateFragmentDialog(Bundle savedInstanceState, Dialog dialog) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }

    @Override
    public void beforeViewCreated() {

    }

    @Override
    public void afterViewCreated() {
        mDialogListSelectorBinding = getViewDataBinding();
        Utility.applyTypeFace(getContext(), mDialogListSelectorBinding.baseLayout);


        List<StateListRespone> mList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mList.add(new StateListRespone("Id " + i, "State " + i));
        }
        mDialogListSelectorBinding.listview.setAdapter(new StateListAdapter(getContext(), mList));

    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public StateDialogViewModel getViewModel() {
        return mStateDialogViewModel;
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

    @Override
    public int getLayoutId() {
        return R.layout.dialog_fragment_list_selector;
    }
}

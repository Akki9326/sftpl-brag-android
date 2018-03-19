package com.ragtagger.brag.ui.authentication.profile.addeditaddress.statedialog;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataState;
import com.ragtagger.brag.databinding.DialogFragmentListSelectorBinding;
import com.ragtagger.brag.ui.authentication.profile.addeditaddress.statedialog.adapter.StateListAdapter;
import com.ragtagger.brag.ui.core.CoreDialogFragment;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 07-03-2018.
 */


public class StateDialogFragment extends CoreDialogFragment<DialogFragmentListSelectorBinding, StateDialogViewModel>
        implements StateDialogNavigator {


    @Inject
    StateDialogViewModel mStateDialogViewModel;

    DialogFragmentListSelectorBinding mDialogListSelectorBinding;

    StateListAdapter adapter;
    List<DataState> mStateList;

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
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return dialog;
    }

    @Override
    public void beforeViewCreated() {

        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_KEY_STATE_LIST)) {
            mStateList = new ArrayList<>();
            mStateList = getArguments().getParcelableArrayList(Constants.BUNDLE_KEY_STATE_LIST);
        }
    }

    @Override
    public void afterViewCreated() {
        mDialogListSelectorBinding = getViewDataBinding();
        Utility.applyTypeFace(getContext(), mDialogListSelectorBinding.baseLayout);

        adapter = new StateListAdapter(getContext(), mStateList);
        mDialogListSelectorBinding.listview.setAdapter(adapter);

        mDialogListSelectorBinding.edittextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });

        mDialogListSelectorBinding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataState responeData = (DataState) parent.getItemAtPosition(position);
                onStateSelect(responeData);


            }
        });

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

    @Override
    public void onClose() {
        dismiss();
    }

    @Override
    public void onStateSelect(DataState data) {
        Intent intent = new Intent();
        intent.putExtra(Constants.BUNDLE_KEY_STATE, (Parcelable) data);
        getTargetFragment().onActivityResult(1, 1, intent);
        dismiss();
    }


}

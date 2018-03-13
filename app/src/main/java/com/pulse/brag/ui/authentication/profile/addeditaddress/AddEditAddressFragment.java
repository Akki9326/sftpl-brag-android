package com.pulse.brag.ui.authentication.profile.addeditaddress;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.TextView;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.datas.StateData;
import com.pulse.brag.data.model.requests.QAddAddress;
import com.pulse.brag.databinding.FragmentAddEditAddressBinding;
import com.pulse.brag.ui.authentication.profile.UserProfileActivity;
import com.pulse.brag.ui.authentication.profile.addeditaddress.statedialog.StateDialogFragment;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.utils.Validation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 06-03-2018.
 */


public class AddEditAddressFragment extends CoreFragment<FragmentAddEditAddressBinding, AddEditAddressViewModel> implements AddEditAddressNavigator {


    @Inject
    AddEditAddressViewModel mAddEditViewModel;

    FragmentAddEditAddressBinding mAddEditAddressBinding;

    private String stateId;
    private List<StateData> mStateList;
    StateData selectedState;
    public int REQUEST_STATE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mAddEditViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {


    }

    @Override
    public void afterViewCreated() {
        mAddEditAddressBinding = getViewDataBinding();
        mStateList = new ArrayList<>();
        Utility.applyTypeFace(getContext(), mAddEditAddressBinding.baseLayout);

    }

    @Override
    public void setUpToolbar() {
        ((UserProfileActivity) getActivity()).showToolBar(getString(R.string.toolbar_label_address));
    }

    @Override
    public AddEditAddressViewModel getViewModel() {
        return mAddEditViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_edit_address;
    }

    @Override
    public void onApiSuccess() {
        hideProgress();
        getActivity().onBackPressed();

    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(),null);
    }

    @Override
    public void onAddOrUpdateAddress() {


        if (Validation.isEmpty(mAddEditAddressBinding.edittextAddress)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_empty_address));
        } else if (Validation.isEmpty(mAddEditAddressBinding.edittextCity)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_empty_city));
        } else if (mAddEditAddressBinding.textviewState.getText().toString().isEmpty()) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_empty_state));
        } else if (Validation.isEmpty(mAddEditAddressBinding.edittextPincode)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_empty_pincode));
        } else if (Utility.isConnection(getContext())) {

            QAddAddress qAddAddress = new QAddAddress();
            qAddAddress.setAddress(mAddEditAddressBinding.edittextAddress.getText().toString());
            qAddAddress.setCity(mAddEditAddressBinding.edittextCity.getText().toString());
            qAddAddress.setLandmark(mAddEditAddressBinding.edittextLandmark.getText().toString());
            qAddAddress.setPincode(Integer.valueOf(mAddEditAddressBinding.edittextPincode.getText().toString()));
            qAddAddress.setState(selectedState);
            showProgress();
            mAddEditViewModel.AddorUpdateAddress(qAddAddress);
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null,null);
        }
    }

    @Override
    public boolean onEditorActionPincode(TextView textView, int i, KeyEvent keyEvent) {
        onAddOrUpdateAddress();
        return false;
    }

    @Override
    public void onOpenStateListDialog() {

        if (mStateList.isEmpty()) {
            showProgress();
            mAddEditViewModel.getStateListAPI();
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(Constants.BUNDLE_KEY_STATE_LIST, (ArrayList<? extends Parcelable>) mStateList);
            StateDialogFragment dialogFragment = new StateDialogFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.setTargetFragment(this, REQUEST_STATE);
            dialogFragment.show(getFragmentManager(), "");
        }


    }


    @Override
    public void onApiSuccessState(List<StateData> data) {
        hideProgress();
        mStateList.addAll(data);
    }


    @Override
    public void onApiErrorState(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(),null);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_STATE) {
            selectedState = data.getParcelableExtra(Constants.BUNDLE_KEY_STATE);
            mAddEditViewModel.updateState(selectedState.getText());
            stateId = selectedState.getId();
        }
    }
}

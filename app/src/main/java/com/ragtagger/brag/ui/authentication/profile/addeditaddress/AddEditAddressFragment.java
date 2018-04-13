package com.ragtagger.brag.ui.authentication.profile.addeditaddress;


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
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.widget.TextView;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataState;
import com.ragtagger.brag.data.model.datas.DataUserAddress;
import com.ragtagger.brag.databinding.FragmentAddEditAddressBinding;
import com.ragtagger.brag.ui.authentication.profile.UserProfileActivity;
import com.ragtagger.brag.ui.authentication.profile.addeditaddress.statedialog.StateDialogFragment;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 06-03-2018.
 */


public class AddEditAddressFragment extends CoreFragment<FragmentAddEditAddressBinding, AddEditAddressViewModel> implements AddEditAddressNavigator {

    private static final int REQUEST_STATE = 1;

    @Inject
    AddEditAddressViewModel mAddEditViewModel;
    FragmentAddEditAddressBinding mAddEditAddressBinding;

    List<DataState> mStateList;
    DataState selectedState;

    String mAddressId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        checkInternetAndCallApi();
        if (mAddEditViewModel.getDataManager().getUserData().getAddresses() != null
                && !mAddEditViewModel.getDataManager().getUserData().getAddresses().isEmpty()) {
            selectedState = new DataState(mAddEditViewModel.getStateId(), mAddEditViewModel.getStateText());
            mAddressId = mAddEditViewModel.getDataManager().getUserData().getAddresses().get(0).getId();
            mAddEditViewModel.updateState(mAddEditViewModel.getDataManager().getUserData().getAddresses().get(0).getState().getText());
        }
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

    private void checkInternetAndCallApi() {
        if (Utility.isConnection(getActivity())) {
            mAddEditViewModel.callGetUserProfileApi();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_STATE) {
            selectedState = data.getParcelableExtra(Constants.BUNDLE_KEY_STATE);
            mAddEditViewModel.updateState(selectedState.getText());
        }
    }

    @Override
    public boolean onEditorActionPincode(TextView textView, int i, KeyEvent keyEvent) {
        if (mAddEditViewModel.isAddressAvaliable.get()) {
            performClickUpdate();
        } else {
            performClickOnAdd();
        }
        return false;
    }

    @Override
    public void setUserProfile() {
        hideProgress();
        if (mAddEditViewModel.isAddressAvaliable.get()) {
            DataUserAddress userAddress = mAddEditViewModel.getUserAddress();

            selectedState = new DataState(userAddress.getState().getId(), userAddress.getState().getText());
            mAddressId = userAddress.getId();

            mAddEditAddressBinding.edittextAddress.setText(userAddress.getAddress());
            mAddEditAddressBinding.edittextCity.setText(userAddress.getCity());
            mAddEditAddressBinding.edittextLandmark.setText(userAddress.getLandmark());
            mAddEditAddressBinding.edittextPincode.setText(String.valueOf(userAddress.getPincode()));
            mAddEditAddressBinding.textviewState.setText(userAddress.getState().getText());
        }
    }

    @Override
    public void onApiErrorUserProfile(ApiError error) {
        hideProgress();
    }

    @Override
    public void performClickOnAdd() {
        if (isAdded())
            mAddEditViewModel.validateAddUpdateAddressForm(getActivity(), true, mAddEditAddressBinding.edittextAddress, mAddEditAddressBinding.edittextCity, mAddEditAddressBinding.textviewState, mAddEditAddressBinding.edittextPincode);
    }

    @Override
    public void performClickUpdate() {
        if (isAdded())
            mAddEditViewModel.validateAddUpdateAddressForm(getActivity(), false, mAddEditAddressBinding.edittextAddress, mAddEditAddressBinding.edittextCity, mAddEditAddressBinding.textviewState, mAddEditAddressBinding.edittextPincode);
    }

    @Override
    public void noInternetAlert() {
        AlertUtils.showAlertMessage(getActivity(), 0, null, null);
    }

    @Override
    public void invalidAddOrUpdateForm(String msg) {
        AlertUtils.showAlertMessage(getActivity(), msg);
    }

    @Override
    public void validAddAddressForm() {
        showProgress();
        mAddEditViewModel.callAddAddressApi(mAddEditAddressBinding.edittextAddress.getText().toString(), mAddEditAddressBinding.edittextCity.getText().toString(), mAddEditAddressBinding.edittextLandmark.getText().toString(), Integer.valueOf(mAddEditAddressBinding.edittextPincode.getText().toString()), selectedState);
    }

    @Override
    public void validUpdateAddressForm() {
        showProgress();
        mAddEditViewModel.callUpdateAddressApi(mAddressId, mAddEditAddressBinding.edittextAddress.getText().toString(), mAddEditAddressBinding.edittextCity.getText().toString(), mAddEditAddressBinding.edittextLandmark.getText().toString(), Integer.valueOf(mAddEditAddressBinding.edittextPincode.getText().toString()), selectedState);
    }


    @Override
    public void performClickState() {
        if (mStateList.isEmpty()) {
            if (Utility.isConnection(getActivity())) {
                showProgress();
                mAddEditViewModel.callGetStateListApi();
            } else {
                noInternetAlert();
            }
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
    public void setStateList(List<DataState> data) {
        hideProgress();
        mStateList.addAll(data);
    }

    @Override
    public void onApiSuccess() {
        hideProgress();
        Intent intent = new Intent(Constants.LOCALBROADCAST_UPDATE_PROFILE);
        intent.putExtra(Constants.BUNDLE_IS_ADDRESS_UPDATE, true);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        getActivity().onBackPressed();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }
}

package com.pulse.brag.ui.authentication.profile.addeditaddress;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentAddEditAddressBinding;
import com.pulse.brag.ui.authentication.profile.UserProfileActivity;
import com.pulse.brag.ui.authentication.profile.addeditaddress.statedialog.StateDialogFragement;
import com.pulse.brag.ui.core.CoreActivity;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Common;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.utils.Validation;
import com.pulse.brag.views.keyboardvisibilityevent.KeyboardVisibilityEvent;
import com.pulse.brag.views.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import com.pulse.brag.views.webview.WebviewDialogFragment;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 06-03-2018.
 */


public class AddEditAddressFragment extends CoreFragment<FragmentAddEditAddressBinding, AddEditAddressViewModel> implements AddEditAddressNavigator {


    @Inject
    AddEditAddressViewModel mAddEditViewModel;

    FragmentAddEditAddressBinding mAddEditAddressBinding;


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

    }

    @Override
    public void onApiError(ApiError error) {

    }

    @Override
    public void onAddOrUpdateAddress() {


        if (Validation.isEmpty(mAddEditAddressBinding.edittextAddress)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_empty_address));
        } else if (mAddEditAddressBinding.textviewCity.getText().toString().isEmpty()) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_empty_city));
        } else if (mAddEditAddressBinding.textviewState.getText().toString().isEmpty()) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_empty_state));
        } else if (Validation.isEmpty(mAddEditAddressBinding.edittextPincode)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_empty_pincode));
        } else {
            Toast.makeText(mActivity, "Update", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onEditorActionPincode(TextView textView, int i, KeyEvent keyEvent) {
        onAddOrUpdateAddress();
        return false;
    }

    @Override
    public void onOpenStateListDialog() {
        Bundle bundle = new Bundle();
        StateDialogFragement dialogFragment = new StateDialogFragement();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getChildFragmentManager(), "");
    }

    @Override
    public void onOpenCityListDialog() {
        Bundle bundle = new Bundle();
        StateDialogFragement dialogFragment = new StateDialogFragement();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getChildFragmentManager(), "");
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}

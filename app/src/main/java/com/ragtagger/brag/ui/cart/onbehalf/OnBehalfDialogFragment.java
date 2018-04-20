package com.ragtagger.brag.ui.cart.onbehalf;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataUser;
import com.ragtagger.brag.databinding.DialogFragmentSelectCustomerBinding;
import com.ragtagger.brag.ui.core.CoreDialogFragment;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;

import javax.inject.Inject;

public class OnBehalfDialogFragment extends CoreDialogFragment<DialogFragmentSelectCustomerBinding, OnBehalfDialogViewModel> implements OnBehalfDialogNavigator {

    public static final int RESULT_CODE_CUSTOMER = 2;

    @Inject
    OnBehalfDialogViewModel mOnBehalfDialogViewModel;
    DialogFragmentSelectCustomerBinding mDialogFragmentSelectCustomerBinding;

    String mCustomer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog);
        mOnBehalfDialogViewModel.setNavigator(this);
    }

    @Override
    public Dialog onCreateFragmentDialog(Bundle savedInstanceState, Dialog dialog) {
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    @Override
    public void beforeViewCreated() {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (getArguments() != null) {
            if (getArguments().containsKey(Constants.BUNDLE_SELECTED_CUSTOMER))
                mCustomer = getArguments().getString(Constants.BUNDLE_SELECTED_CUSTOMER);
        }
    }

    @Override
    public void afterViewCreated() {
        mDialogFragmentSelectCustomerBinding = getViewDataBinding();
        Utility.applyTypeFace(getActivity(), mDialogFragmentSelectCustomerBinding.baseLayout);
        if (mCustomer != null)
            mOnBehalfDialogViewModel.setCustomer(mCustomer);
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public OnBehalfDialogViewModel getViewModel() {
        return mOnBehalfDialogViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_fragment_select_customer;
    }

    @Override
    public void performClickDone(View view) {
        if (isAdded())
            mOnBehalfDialogViewModel.validateCustomerIdForm(getActivity(), mDialogFragmentSelectCustomerBinding.edittextCustomer);
    }

    @Override
    public void noInternetAlert() {
        AlertUtils.showAlertMessage(getActivity(), 0, null, null);
    }

    @Override
    public void validCustomerIdForm() {
        showProgress();
        mOnBehalfDialogViewModel.callCheckCustomerApi(mDialogFragmentSelectCustomerBinding.edittextCustomer.getText().toString().trim());
    }

    @Override
    public void invalidCustomerIdForm(String msg) {
        AlertUtils.showAlertMessage(getActivity(), msg);
    }

    @Override
    public void setCustomerData(DataUser customerData) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BUNDLE_CHECKED_CUSTOMER, customerData);
        intent.putExtras(bundle);
        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_CODE_CUSTOMER, intent);
        dismiss();
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
}

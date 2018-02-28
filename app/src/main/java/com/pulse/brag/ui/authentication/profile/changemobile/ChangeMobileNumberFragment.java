package com.pulse.brag.ui.authentication.profile.changemobile;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentChangeMobileNumBinding;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.authentication.profile.UserProfileActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.utils.Validation;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 11-12-2017.
 */


public class ChangeMobileNumberFragment extends CoreFragment<FragmentChangeMobileNumBinding, ChangeMobNumberViewModel> implements ChangeMobNumberNavigator/*extends BaseFragment implements BaseInterface*/ {

    @Inject
    ChangeMobNumberViewModel mChangeMobNumberViewModel;
    FragmentChangeMobileNumBinding mFragmentChangeMobileNumBinding;

    /*View mView;
    TextView mTxtMobileNum;
    EditText mEdtPass;
    ImageView mImgToggle;
    TextView mTxtDone;*/

    String mobilenum;

    public static ChangeMobileNumberFragment newInstance(String mobilenum) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_MOBILE, mobilenum);
        ChangeMobileNumberFragment fragment = new ChangeMobileNumberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChangeMobNumberViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
        mobilenum = getArguments().getString(Constants.BUNDLE_MOBILE);
    }

    @Override
    public void afterViewCreated() {
        mFragmentChangeMobileNumBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), (LinearLayout) mFragmentChangeMobileNumBinding.baseLayout);
        mChangeMobNumberViewModel.setNewMobileNumber(mobilenum);
    }

    @Override
    public void setUpToolbar() {
        ((UserProfileActivity) getActivity()).showToolBar("Change Mobile Number");
    }

    @Override
    public ChangeMobNumberViewModel getViewModel() {
        return mChangeMobNumberViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_change_mobile_num;
    }

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_change_mobile_num, container, false);
            initializeData();
            setListeners();
            showData();
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbar();
    }

    @Override
    public void setToolbar() {
//        ((UserProfileActivity) getActivity()).showToolBar("Change Mobile Number");
    }

    @Override
    public void initializeData() {
        mTxtMobileNum = (TextView) mView.findViewById(R.id.textview_new_mobile_num);
        mEdtPass = (EditText) mView.findViewById(R.id.edittext_password);
        mImgToggle = (ImageView) mView.findViewById(R.id.imageview_pass_visible);
        mTxtDone = (TextView) mView.findViewById(R.id.textview_done);

        mobilenum = getArguments().getString(Constants.BUNDLE_MOBILE);
        mTxtMobileNum.setText(mobilenum);

        Utility.applyTypeFace(getActivity(), (LinearLayout) mView.findViewById(R.id.base_layout));
    }

    @Override
    public void setListeners() {


        mTxtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationAndAPICall();
            }
        });
        mImgToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImgToggle.isSelected()) {
                    mImgToggle.setSelected(false);
                    mEdtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEdtPass.setSelection(mEdtPass.getText().length());
                } else {
                    mImgToggle.setSelected(true);
                    mEdtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEdtPass.setSelection(mEdtPass.getText().length());
                }
            }
        });
        mEdtPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    mTxtDone.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void showData() {

    }*/

    /*private void validationAndAPICall() {
        if (Validation.isEmpty(mEdtPass)) {
            //Utility.showAlertMessage(getActivity(), getString(R.string.error_pass));
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_pass));
        } else if (Utility.isConnection(getActivity())) {
            ChangeMobileNumberAPICall();
        } else {
            //Utility.showAlertMessage(getActivity(), 0,null);
            AlertUtils.showAlertMessage(getActivity(), 0, null);
        }
    }

    private void ChangeMobileNumberAPICall() {
        // TODO: 13-12-2017 if you display mobile number in more Fragment than isStatus==true update mobile number display
        showProgressDialog();
        ChangeMobileNumberRequest changeMobileNumberRequest = new ChangeMobileNumberRequest(mTxtMobileNum.getText().toString()
                , mEdtPass.getText().toString());
        ApiClient.changeApiBaseUrl("http://103.204.192.148/brag/api/v1/");
        Call<GeneralResponse> mResponeCall = ApiClient.getInstance(getActivity()).getApiResp().changeMobileNum(changeMobileNumberRequest);
        mResponeCall.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                hideProgressDialog();
                if (response.isSuccessful()) {
                    GeneralResponse data = response.body();
                    if (data.isStatus()) {
                        PreferencesManager.getInstance().setUserData(new Gson().toJson(data.getData()));
                        ((UserProfileActivity) getActivity()).finish();
                        ((UserProfileActivity) getActivity()).overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    } else {
                        //Utility.showAlertMessage(getActivity(), data.getErrorCode(),data.getMessage());
                        AlertUtils.showAlertMessage(getActivity(), data.getErrorCode(), data.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                hideProgressDialog();
                //Utility.showAlertMessage(getActivity(), t);
                AlertUtils.showAlertMessage(getActivity(), t);
            }
        });
    }*/


    @Override
    public void onApiSuccess() {
        hideProgress();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
    }

    @Override
    public void done() {
        if (Validation.isEmpty(mFragmentChangeMobileNumBinding.edittextPassword)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_pass));
        } else if (Utility.isConnection(getActivity())) {
            showProgress();
            mChangeMobNumberViewModel.changeMobNumber(mFragmentChangeMobileNumBinding.textviewNewMobileNum.getText().toString()
                    , mFragmentChangeMobileNumBinding.edittextPassword.getText().toString());
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null);
        }
    }

    @Override
    public void hideUnhidePass() {
        if (mFragmentChangeMobileNumBinding.imageviewPassVisible.isSelected()) {
            mFragmentChangeMobileNumBinding.imageviewPassVisible.setSelected(false);
            mFragmentChangeMobileNumBinding.edittextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mFragmentChangeMobileNumBinding.edittextPassword.setSelection(mFragmentChangeMobileNumBinding.edittextPassword.getText().length());
        } else {
            mFragmentChangeMobileNumBinding.imageviewPassVisible.setSelected(true);
            mFragmentChangeMobileNumBinding.edittextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mFragmentChangeMobileNumBinding.edittextPassword.setSelection(mFragmentChangeMobileNumBinding.edittextPassword.getText().length());
        }
    }

    @Override
    public boolean onEditorActionPass(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            mFragmentChangeMobileNumBinding.textviewDone.performClick();
            return true;
        }
        return false;
    }

    @Override
    public void finishActivity() {
        ((UserProfileActivity) getActivity()).finish();
        ((UserProfileActivity) getActivity()).overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}

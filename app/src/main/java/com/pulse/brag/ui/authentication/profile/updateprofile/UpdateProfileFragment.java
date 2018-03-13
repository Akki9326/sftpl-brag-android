package com.pulse.brag.ui.authentication.profile.updateprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.LinearLayout;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentEditUserProfileBinding;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.authentication.profile.UserProfileActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.utils.Validation;

import javax.inject.Inject;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public class UpdateProfileFragment extends CoreFragment<FragmentEditUserProfileBinding, UpdateProfileViewModel> implements UpdateProfileNavigator {

    @Inject
    UpdateProfileViewModel mUserProfileViewModel;
    FragmentEditUserProfileBinding mFragmentEditUserProfileBinding;

    String mobile;

    public static UpdateProfileFragment newInstance(String mobile) {
        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_MOBILE, mobile);
        UpdateProfileFragment fragment = new UpdateProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserProfileViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
        mobile = getArguments().getString(Constants.BUNDLE_MOBILE);
    }

    @Override
    public void afterViewCreated() {
        mFragmentEditUserProfileBinding = getViewDataBinding();
        Utility.applyTypeFace(getActivity(), (LinearLayout) mFragmentEditUserProfileBinding.baseLayout);
    }

    @Override
    public void setUpToolbar() {
        ((UserProfileActivity) getActivity()).showToolBar(getString(R.string.toolbar_label_update_password));
    }

    @Override
    public UpdateProfileViewModel getViewModel() {
        return mUserProfileViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_edit_user_profile;
    }

    @Override
    public void onApiSuccess() {
        hideProgress();
        Intent intent = new Intent(Constants.LOCALBROADCAST_UPDATE_PROFILE);
        intent.putExtra(Constants.BUNDLE_IS_ADDRESS_UPDATE, false);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        getActivity().onBackPressed();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(),null);
    }

    @Override
    public void update() {
        if (Validation.isEmpty(mFragmentEditUserProfileBinding.edittextFirstname)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_please_enter_first_name));
        } else if (Validation.isEmpty(mFragmentEditUserProfileBinding.edittextEmail)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_please_email));
        } else if (!Validation.isEmailValid(mFragmentEditUserProfileBinding.edittextEmail)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_email_valid));
        } else if (Utility.isConnection(getActivity())) {
            Utility.hideSoftkeyboard(getActivity());
            showProgress();
            mUserProfileViewModel.updateProfileAPI(mFragmentEditUserProfileBinding.edittextFirstname.getText().toString()
                    , mFragmentEditUserProfileBinding.edittextLastname.getText().toString()
                    , mFragmentEditUserProfileBinding.edittextEmail.getText().toString());
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null,null);
        }
    }

    @Override
    public void showMsgProfileUpdated() {
        AlertUtils.showAlertMessage(getBaseActivity(), getString(R.string.msg_profile_update_successfull), true);
    }
}

package com.ragtagger.brag.ui.authentication.profile.updateprofile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputFilter;
import android.view.View;
import android.widget.LinearLayout;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataChannel;
import com.ragtagger.brag.data.model.datas.DataSaleType;
import com.ragtagger.brag.data.model.datas.DataState;
import com.ragtagger.brag.data.model.datas.DataUser;
import com.ragtagger.brag.databinding.FragmentEditUserProfileBinding;
import com.ragtagger.brag.ui.authentication.profile.addeditaddress.statedialog.StateDialogFragment;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.authentication.profile.UserProfileActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.AppLogger;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.TextFilterUtils;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;
import com.ragtagger.brag.views.dropdown.DropdownItem;
import com.ragtagger.brag.views.dropdown.DropdownUtils;
import com.ragtagger.brag.views.dropdown.IOnDropDownItemClick;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public class UpdateProfileFragment extends CoreFragment<FragmentEditUserProfileBinding, UpdateProfileViewModel> implements UpdateProfileNavigator {

    public static final int REQUEST_STATE = 1;

    @Inject
    UpdateProfileViewModel mUserProfileViewModel;
    FragmentEditUserProfileBinding mFragmentEditUserProfileBinding;

    ArrayList<DropdownItem> mUserTypeDropDown;
    ArrayList<DropdownItem> mChannelDropDown;
    ArrayList<DropdownItem> mSaleTypeDropDown;

    List<DataState> mStateList;
    List<DataChannel> mChannelList;
    List<DataSaleType> mSalesTypeList;

    DataChannel mSelectedChannel;
    DataSaleType mSelectedSalesType;
    DataState mSelectedState;

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
        mUserTypeDropDown = new ArrayList<>();
        mChannelDropDown = new ArrayList<>();
        mSaleTypeDropDown = new ArrayList<>();
        mStateList = new ArrayList<>();
        mChannelList = new ArrayList<>();
        mSalesTypeList = new ArrayList<>();

        mobile = getArguments().getString(Constants.BUNDLE_MOBILE);
    }

    @Override
    public void afterViewCreated() {
        mFragmentEditUserProfileBinding = getViewDataBinding();
        mFragmentEditUserProfileBinding.edittextGstIn.setFilters(new InputFilter[]{TextFilterUtils.getAlphaNumericFilter(), TextFilterUtils.getLengthFilter(15)});
        Utility.applyTypeFace(getActivity(), (LinearLayout) mFragmentEditUserProfileBinding.baseLayout);

        checkInternetAndCallApi();
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

    private void checkInternetAndCallApi() {
        if (isAdded()) {
            if (Utility.isConnection(getActivity())) {
                showProgress();
                mUserProfileViewModel.callGetProfileApi();
            } else {
                mUserProfileViewModel.fillUserData();
                noInternetAlert();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_STATE) {
            mSelectedState = data.getParcelableExtra(Constants.BUNDLE_KEY_STATE);
            mUserProfileViewModel.setState(mSelectedState.getText());
        }
    }

    @Override
    public void afterGetProfile(DataUser dataUser) {
        mUserProfileViewModel.fillUserData();
        mUserProfileViewModel.callGetRequiredDataApi();
    }

    @Override
    public void setState(List<DataState> states) {
        mStateList = states;
    }

    @Override
    public void setSelectedState(DataState state) {
        mSelectedState = state;
    }

    @Override
    public void setChannel(List<DataChannel> channels) {
        int i = 0;
        if (channels != null && channels.size() > 0) {
            mChannelList = channels;
            for (DataChannel channel : channels) {
                mChannelDropDown.add(new DropdownItem(channel.getText(), i));
                i++;
            }
        }
    }

    @Override
    public void setSelectedChannel(DataChannel channel) {
        mSelectedChannel = channel;
    }

    @Override
    public void setSalesType(List<DataSaleType> salesTypes) {
        int i = 0;
        if (salesTypes != null && salesTypes.size() > 0) {
            mSalesTypeList = salesTypes;
            for (DataSaleType salesType : salesTypes) {
                mSaleTypeDropDown.add(new DropdownItem(salesType.getText(), i));
                i++;
            }
        }
    }

    @Override
    public void setSelectedSaleType(DataSaleType saleType) {
        mSelectedSalesType = saleType;
    }


    @Override
    public void afterGettingRequiredData() {
        hideProgress();
    }

    @Override
    public void performClickChannelDropdown(View view) {
        if (mChannelList == null || mChannelList.isEmpty()) {
        } else {
            try {
                DropdownUtils.DropDownSpinner(getActivity(), mChannelDropDown, view, new IOnDropDownItemClick() {
                    @Override
                    public void onItemClick(String str, int i) {
                        setSelectedChannel(mChannelList.get(i));
                    }
                });
            } catch (Exception e) {
                AppLogger.e(e.getMessage());
            }
        }
    }

    @Override
    public void performClickSaleTypeDropdown(View view) {
        if (mSalesTypeList == null || mSalesTypeList.isEmpty()) {
        } else {
            try {
                DropdownUtils.DropDownSpinner(getActivity(), mSaleTypeDropDown, view, new IOnDropDownItemClick() {
                    @Override
                    public void onItemClick(String str, int i) {
                        setSelectedSaleType(mSalesTypeList.get(i));
                    }
                });
            } catch (Exception e) {
                AppLogger.e(e.getMessage());
            }
        }
    }

    @Override
    public void performClickState(View view) {
        if (mStateList == null || mStateList.isEmpty()) {
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
    public void performUpdateClick() {
        if (isAdded())
            mUserProfileViewModel.validateUpdateProfileForm(getActivity(), mFragmentEditUserProfileBinding.edittextFirstname, mFragmentEditUserProfileBinding.edittextEmail, mFragmentEditUserProfileBinding.edittextGstIn, mFragmentEditUserProfileBinding.edittextState);
    }

    @Override
    public void noInternetAlert() {
        AlertUtils.showAlertMessage(getActivity(), 0, null, null);
    }

    @Override
    public void validProfileForm() {
        Utility.hideSoftkeyboard(getActivity());
        showProgress();
        mUserProfileViewModel.updateProfileAPI(mFragmentEditUserProfileBinding.edittextFirstname.getText().toString()
                , mFragmentEditUserProfileBinding.edittextLastname.getText().toString()
                , mFragmentEditUserProfileBinding.edittextEmail.getText().toString()
                , mFragmentEditUserProfileBinding.edittextGstIn.getText().toString(), mSelectedState, mSelectedChannel, mSelectedSalesType);
    }

    @Override
    public void invalidProfileForm(String msg) {
        AlertUtils.showAlertMessage(getActivity(), msg);
    }


    @Override
    public void showMsgProfileUpdated() {
        AlertUtils.showAlertMessage(getBaseActivity(), getString(R.string.msg_profile_update_successfull), true);
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
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }
}

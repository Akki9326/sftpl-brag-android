package com.pulse.brag.ui.authentication.profile.addeditaddress;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.GeneralResponse;
import com.pulse.brag.data.model.datas.UserAddress;
import com.pulse.brag.data.model.datas.UserData;
import com.pulse.brag.data.model.requests.QAddAddress;
import com.pulse.brag.data.model.response.LoginResponse;
import com.pulse.brag.data.model.response.RStateList;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.ui.core.CoreViewModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by nikhil.vadoliya on 06-03-2018.
 */


public class AddEditAddressViewModel extends CoreViewModel<AddEditAddressNavigator> {

    ObservableField<String> state = new ObservableField<>();
    ObservableField<Boolean> isAddressAvaliable = new ObservableField<>();
    UserAddress userAddress;

    public AddEditAddressViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onAdd() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onAddAddress();
            }
        };
    }

    public View.OnClickListener onUpdate() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onUpdateAddress();
            }
        };
    }

    public boolean onEditorActionPincode(@NonNull final TextView textView, final int actionId,
                                         @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionPincode(textView, actionId, keyEvent);
    }

    public View.OnClickListener onStateClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onOpenStateListDialog();
            }
        };
    }

    public void updateState(String state) {
        this.state.set(state);
    }

    public ObservableField<String> getState() {
        return state;

    }

    public void AddAddress(QAddAddress addAddress) {

        Call<GeneralResponse> mAddAddress = getDataManager().addAddress(addAddress);
        mAddAddress.enqueue(new ApiResponse<GeneralResponse>() {
            @Override
            public void onSuccess(GeneralResponse generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                } else {
                    getNavigator().onApiError(new ApiError(generalResponse.getErrorCode(), generalResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    public void UpdateAddress(UserAddress addAddress) {
        UserData userData = getDataManager().getUserData();
        List<UserAddress> mUserAddresses = new ArrayList<>();
        mUserAddresses.add(addAddress);
        userData.setAddresses(mUserAddresses);
        Call<GeneralResponse> mAddAddress = getDataManager().updateAddress(userData);
        mAddAddress.enqueue(new ApiResponse<GeneralResponse>() {
            @Override
            public void onSuccess(GeneralResponse generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                } else {
                    getNavigator().onApiError(new ApiError(generalResponse.getErrorCode(), generalResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    public void getStateListAPI() {

        Call<RStateList> stateList = getDataManager().getStateList("state/list");
        stateList.enqueue(new ApiResponse<RStateList>() {
            @Override
            public void onSuccess(RStateList rStateList, Headers headers) {
                if (rStateList.isStatus()) {
                    getNavigator().onApiSuccessState(rStateList.getData());
                    getNavigator().onOpenStateListDialog();
                } else {
                    getNavigator().onApiErrorState(new ApiError(rStateList.getErrorCode(), rStateList.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiErrorState(t);
            }
        });
    }

    public void getUserProfile() {
        Call<LoginResponse> responseCall = getDataManager().getUserProfile("user/getProfile");
        responseCall.enqueue(new ApiResponse<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse loginResponse, Headers headers) {
                if (loginResponse.isStatus()) {
                    if (loginResponse.getData().getAddresses() == null || loginResponse.getData().getAddresses().isEmpty()) {
                        setIsAddressAvaliable(false);
                    } else {
                        setUserAddress(loginResponse.getData().getAddresses().get(0));
                        setIsAddressAvaliable(true);
                    }
                    getNavigator().onApiSuccessUserProfile();
                } else {
                    getNavigator().onApiErrorUserProfile(new ApiError(loginResponse.getErrorCode(), loginResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiErrorUserProfile(new ApiError(t.getHttpCode(), t.getMessage()));
            }
        });
    }

    public ObservableField<Boolean> IsAddressAvaliable() {
        return isAddressAvaliable;
    }

    public void setIsAddressAvaliable(boolean isAddressAvaliable) {
        this.isAddressAvaliable.set(isAddressAvaliable);
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public String getAddress() {
        if (getDataManager().getUserData().getAddresses() != null && !getDataManager().getUserData().getAddresses().isEmpty()) {
            setIsAddressAvaliable(true);
            return getDataManager().getUserData().getAddresses().get(0).getAddress().trim();
        } else {
            return "";
        }
    }

    public String getLandmark() {
        if (getDataManager().getUserData().getAddresses() != null && !getDataManager().getUserData().getAddresses().isEmpty()) {
            return getDataManager().getUserData().getAddresses().get(0).getLandmark().trim();
        } else {
            return "";
        }
    }

    public String getCity() {
        if (getDataManager().getUserData().getAddresses() != null && !getDataManager().getUserData().getAddresses().isEmpty()) {
            return getDataManager().getUserData().getAddresses().get(0).getCity().trim();
        } else {
            return "";
        }
    }

    public String getStateText() {
        if (getDataManager().getUserData().getAddresses() != null && !getDataManager().getUserData().getAddresses().isEmpty()) {
            return getDataManager().getUserData().getAddresses().get(0).getState().getText().trim();
        } else {
            return "";
        }
    }

    public String getStateId() {
        if (getDataManager().getUserData().getAddresses() != null && !getDataManager().getUserData().getAddresses().isEmpty()) {
            return getDataManager().getUserData().getAddresses().get(0).getState().getId().trim();
        } else {
            return "";
        }
    }

    public String getPincode() {
        if (getDataManager().getUserData().getAddresses() != null && !getDataManager().getUserData().getAddresses().isEmpty()) {
            return String.valueOf(getDataManager().getUserData().getAddresses().get(0).getPincode()).trim();
        } else {
            return "";
        }
    }
}

package com.ragtagger.brag.ui.authentication.profile.addeditaddress;


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

import com.google.gson.Gson;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataUserAddress;
import com.ragtagger.brag.data.model.datas.DataUser;
import com.ragtagger.brag.data.model.requests.QAddAddress;
import com.ragtagger.brag.data.model.response.RLogin;
import com.ragtagger.brag.data.model.response.RStateList;
import com.ragtagger.brag.data.model.response.RUserAddress;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;

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
    DataUserAddress userAddress;

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

    public void AddAddress(final QAddAddress addAddress) {

        Call<RUserAddress> mAddAddress = getDataManager().addAddress(addAddress);
        mAddAddress.enqueue(new ApiResponse<RUserAddress>() {
            @Override
            public void onSuccess(RUserAddress generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    DataUser userData = getDataManager().getUserData();
                    userData.setAddresses(generalResponse.getAddresses());
                    getDataManager().setUserData(new Gson().toJson(userData));
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

    public void UpdateAddress(DataUserAddress addAddress) {
        DataUser userData = getDataManager().getUserData();
        List<DataUserAddress> mUserAddresses = new ArrayList<>();
        mUserAddresses.add(addAddress);
        userData.setAddresses(mUserAddresses);
        Call<RLogin> mAddAddress = getDataManager().updateProfile(userData);
        mAddAddress.enqueue(new ApiResponse<RLogin>() {
            @Override
            public void onSuccess(RLogin generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getDataManager().setUserData(new Gson().toJson(generalResponse.getData()));
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getNavigator().onApiSuccess();
                        }
                    }, 500);
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
        Call<RLogin> responseCall = getDataManager().getUserProfile("user/getProfile");
        responseCall.enqueue(new ApiResponse<RLogin>() {
            @Override
            public void onSuccess(RLogin loginResponse, Headers headers) {
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

    public DataUserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(DataUserAddress userAddress) {
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

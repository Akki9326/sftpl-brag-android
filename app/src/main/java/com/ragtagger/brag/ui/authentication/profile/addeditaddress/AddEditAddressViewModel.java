package com.ragtagger.brag.ui.authentication.profile.addeditaddress;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Activity;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataState;
import com.ragtagger.brag.data.model.datas.DataUserAddress;
import com.ragtagger.brag.data.model.datas.DataUser;
import com.ragtagger.brag.data.model.requests.QAddAddress;
import com.ragtagger.brag.data.model.response.RLogin;
import com.ragtagger.brag.data.model.response.RStateList;
import com.ragtagger.brag.data.model.response.RUserAddress;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

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

    public ObservableField<Boolean> IsAddressAvaliable() {
        return isAddressAvaliable;
    }

    public void setIsAddressAvaliable(boolean isAddressAvaliable) {
        this.isAddressAvaliable.set(isAddressAvaliable);
    }

    public void updateState(String state) {
        this.state.set(state);
    }

    public ObservableField<String> getState() {
        return state;
    }


    public View.OnClickListener onAddClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickOnAdd();
            }
        };
    }

    public View.OnClickListener onUpdateClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickUpdate();
            }
        };
    }

    public View.OnClickListener onStateClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickState();
            }
        };
    }

    public boolean onEditorActionPincode(@NonNull final TextView textView, final int actionId,
                                         @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionPincode(textView, actionId, keyEvent);
    }

    void validateAddUpdateAddressForm(Activity activity, boolean addAddress, EditText address, EditText city, TextView state, EditText pincode) {
        if (Validation.isEmpty(address)) {
            getNavigator().invalidAddOrUpdateForm(activity.getString(R.string.error_empty_address));
        } else if (Validation.isEmpty(city)) {
            getNavigator().invalidAddOrUpdateForm(activity.getString(R.string.error_empty_city));
        } else if (Validation.isEmpty(state)) {
            getNavigator().invalidAddOrUpdateForm(activity.getString(R.string.error_empty_state));
        } else if (Validation.isEmpty(pincode)) {
            getNavigator().invalidAddOrUpdateForm(activity.getString(R.string.error_empty_pincode));
        } else if (!Validation.isValidPincode(pincode)) {
            getNavigator().invalidAddOrUpdateForm(activity.getString(R.string.error_pincode_valid));
        } else if (Utility.isConnection(activity)) {
            if (addAddress) getNavigator().validAddAddressForm();
            else getNavigator().validUpdateAddressForm();
        } else {
            getNavigator().noInternetAlert();
        }
    }


    public void callAddAddressApi(String address, String city, String landmark, int pincode, DataState state) {
        QAddAddress qAddAddress = new QAddAddress();
        qAddAddress.setAddress(address);
        qAddAddress.setCity(city);
        qAddAddress.setLandmark(landmark);
        qAddAddress.setPincode(pincode);
        qAddAddress.setState(state);
        getDataManager().addAddress(qAddAddress).enqueue(new ApiResponse<RUserAddress>() {
            @Override
            public void onSuccess(RUserAddress generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    //add address in prefrence of userdata.
                    DataUser userData = getDataManager().getUserData();
                    List<DataUserAddress> userAddresses = new ArrayList<>();
                    userAddresses.add(generalResponse.getData());
                    userData.setAddresses(userAddresses);
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


    public void callUpdateAddressApi(String id, String address, String city, String landmark, int pincode, DataState state) {
        DataUserAddress userAddress = new DataUserAddress();
        userAddress.setId(id);
        userAddress.setAddress(address);
        userAddress.setCity(city);
        userAddress.setLandmark(landmark);
        userAddress.setPincode(pincode);
        userAddress.setState(state);

        List<DataUserAddress> mUserAddresses = new ArrayList<>();
        mUserAddresses.add(userAddress);

        DataUser userData = getDataManager().getUserData();
        userData.setAddresses(mUserAddresses);
        getDataManager().updateProfile(userData).enqueue(new ApiResponse<RLogin>() {
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

    public void callGetStateListApi() {
        Call<RStateList> stateList = getDataManager().getStateList("state/list");
        stateList.enqueue(new ApiResponse<RStateList>() {
            @Override
            public void onSuccess(RStateList rStateList, Headers headers) {
                if (rStateList.isStatus()) {
                    getNavigator().setStateList(rStateList.getData());
                    getNavigator().performClickState();
                } else {
                    getNavigator().onApiError(new ApiError(rStateList.getErrorCode(), rStateList.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    public void callGetUserProfileApi() {
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
                    getNavigator().setUserProfile();
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

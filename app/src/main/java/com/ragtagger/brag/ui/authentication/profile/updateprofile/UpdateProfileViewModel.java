package com.ragtagger.brag.ui.authentication.profile.updateprofile;

import android.app.Activity;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataChannel;
import com.ragtagger.brag.data.model.datas.DataSaleType;
import com.ragtagger.brag.data.model.datas.DataState;
import com.ragtagger.brag.data.model.datas.DataUser;
import com.ragtagger.brag.data.model.response.RGetRequired;
import com.ragtagger.brag.data.model.response.RLogin;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

import okhttp3.Headers;
import retrofit2.Call;


/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public class UpdateProfileViewModel extends CoreViewModel<UpdateProfileNavigator> {
    public UpdateProfileViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    ObservableField<String> firstName = new ObservableField<>();
    ObservableField<String> lastName = new ObservableField<>();
    ObservableField<String> email = new ObservableField<>();
    ObservableField<String> gstNum = new ObservableField<>();
    ObservableField<String> channel = new ObservableField<>();
    ObservableField<String> saleType = new ObservableField<>();
    ObservableField<String> state = new ObservableField<>();

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public ObservableField<String> getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public ObservableField<String> getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public ObservableField<String> getEmail() {
        return email;
    }

    public void setGstNum(String gstNum) {
        this.gstNum.set(gstNum);
    }

    public ObservableField<String> getGstNum() {
        return gstNum;
    }

    public void setChannel(String channel) {
        this.channel.set(channel);
    }

    public ObservableField<String> getChannel() {
        return channel;
    }

    public void setSaleType(String saleType) {
        this.saleType.set(saleType);
    }

    public ObservableField<String> getSaleType() {
        return saleType;
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public ObservableField<String> getState() {
        return state;
    }

    public View.OnClickListener onChannelDropdownClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickChannelDropdown(v);
            }
        };
    }

    public View.OnClickListener onSaleTypeDropdownClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickSaleTypeDropdown(v);
            }
        };
    }

    public View.OnClickListener onStateClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickState(v);
            }
        };
    }

    public View.OnClickListener onUpdateClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performUpdateClick();
            }
        };
    }

    void callGetProfileApi() {
        getDataManager().getProfile().enqueue(new ApiResponse<RLogin>() {
            @Override
            public void onSuccess(RLogin rLogin, Headers headers) {
                if (rLogin.isStatus())
                    getDataManager().setUserData(new Gson().toJson(rLogin.getData()));
                getNavigator().afterGetProfile(getDataManager().getUserData());
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    void callGetRequiredDataApi() {
        getDataManager().getRequired().enqueue(new ApiResponse<RGetRequired>() {
            @Override
            public void onSuccess(RGetRequired rGetRequired, Headers headers) {
                if (rGetRequired.isStatus()) {
                    getNavigator().afterGettingRequiredData();
                    if (rGetRequired.getData() != null) {
                        if (rGetRequired.getData().getStates() != null) {
                            getNavigator().setState(rGetRequired.getData().getStates());
                        }
                        if (rGetRequired.getData().getChannels() != null) {
                            getNavigator().setChannel(rGetRequired.getData().getChannels());
                        }

                        if (rGetRequired.getData().getSalesTypes() != null) {
                            getNavigator().setSalesType(rGetRequired.getData().getSalesTypes());
                        }
                    } else {
                        getNavigator().onApiError(new ApiError(Constants.IErrorCode.defaultErrorCode, Constants.IErrorMessage.IO_EXCEPTION));
                    }

                } else {
                    getNavigator().onApiError(new ApiError(rGetRequired.getErrorCode(), rGetRequired.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    void validateUpdateProfileForm(Activity activity, EditText firstName, EditText email, EditText gstIn, TextView state) {
        if (Validation.isEmpty(firstName)) {
            getNavigator().invalidProfileForm(activity.getString(R.string.error_please_enter_first_name));
        } else if (Validation.isEmpty(email)) {
            getNavigator().invalidProfileForm(activity.getString(R.string.error_please_email));
        } else if (!Validation.isEmailValid(email)) {
            getNavigator().invalidProfileForm(activity.getString(R.string.error_email_valid));
        } else if (Validation.isEmpty(gstIn)) {
            getNavigator().invalidProfileForm(activity.getString(R.string.error_enter_gst));
        } else if (!Validation.isValidGST(gstIn)) {
            getNavigator().invalidProfileForm(activity.getString(R.string.error_gst_valid));
        } else if (Utility.isConnection(activity)) {
            getNavigator().validProfileForm();
        } else {
            getNavigator().noInternetAlert();
        }
    }

    public void updateProfileAPI(String firstName, String lastName, String email, String gstNum, DataState state, DataChannel channel, DataSaleType saleType) {
        DataUser userData = getDataManager().getUserData();
        userData.setFirstName(firstName);
        userData.setLastName(lastName);
        userData.setEmail(email);
        userData.setGstin(gstNum);
        userData.setState(state);
        userData.setChannelModel(channel);
        userData.setSaleTypeModel(saleType);
        Call<RLogin> responseCall = getDataManager().updateProfile(userData);
        responseCall.enqueue(new ApiResponse<RLogin>() {
            @Override
            public void onSuccess(RLogin loginResponse, Headers headers) {
                if (loginResponse.isStatus()) {
                    getDataManager().setUserData(new Gson().toJson(loginResponse.getData()));
                    getNavigator().onApiSuccess();

                } else {
                    getNavigator().onApiError(new ApiError(loginResponse.getErrorCode(), loginResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    void fillUserData() {
        setFirstName(getDataManager().getUserData().getFirstName());
        setLastName(getDataManager().getUserData().getLastName());
        setEmail(getDataManager().getUserData().getEmail());
        setGstNum(getDataManager().getUserData().getGstin());
        setChannel(getDataManager().getUserData().getChannelModel().getText());
        setSaleType(getDataManager().getUserData().getSaleTypeModel().getText());
        setState(getDataManager().getUserData().getState().getText());

        getNavigator().setSelectedState(getDataManager().getUserData().getState());
        getNavigator().setSelectedChannel(getDataManager().getUserData().getChannelModel());
        getNavigator().setSelectedSaleType(getDataManager().getUserData().getSaleTypeModel());
    }
}

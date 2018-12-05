package com.ragtagger.brag.ui.authentication.signup;

import android.app.Activity;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataChannel;
import com.ragtagger.brag.data.model.datas.DataSaleType;
import com.ragtagger.brag.data.model.datas.DataState;
import com.ragtagger.brag.data.model.requests.QAddAddress;
import com.ragtagger.brag.data.model.requests.QSignUp;
import com.ragtagger.brag.data.model.response.RGetRequired;
import com.ragtagger.brag.data.model.response.RSignUp;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public class SignUpViewModel extends CoreViewModel<SignUpNavigator> {

    ObservableField<Boolean> isSalesRepresentative = new ObservableField<>();

    public SignUpViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public void setIsSalesRepresentative(boolean isVisible) {
        this.isSalesRepresentative.set(isVisible);
    }

    public ObservableField<Boolean> getIsSalesRepresentative() {
        return isSalesRepresentative;
    }

    public View.OnClickListener onTypeDropdownClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickUserTypeDropdown(v);
            }
        };
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

    public View.OnClickListener onSignUpClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickSignUp();
            }
        };
    }

    public boolean onEditorActionConfirmPass(@NonNull final TextView textView, final int actionId,
                                             @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionConfirmPass(textView, actionId, keyEvent);
    }

    void callGetRequiredDataApi() {
        getDataManager().getRequired().enqueue(new ApiResponse<RGetRequired>() {
            @Override
            public void onSuccess(RGetRequired rGetRequired, Headers headers) {
                if (rGetRequired.isStatus()) {
                    getNavigator().onApiSuccess();
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

    void validateSignUpForm(Activity activity, EditText firstName, EditText email, EditText mobile, EditText password, EditText confirmPassword, EditText gstin,
                            EditText address, EditText city, EditText pincode, TextView state, boolean isSalesRepresentative) {
        if (Validation.isEmpty(firstName)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_please_enter_first_name));
        } /*else if (Validation.isEmpty(email)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_please_email));
        } */ else if (!Validation.isEmpty(email) && !Validation.isEmailValid(email)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_email_valid));
        } else if (Validation.isEmpty(mobile)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_enter_mobile));
        } else if (!Validation.isValidMobileNum(mobile)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_mobile_valid));
        } else if (Validation.isEmptyPassword(password)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_pass));
        } else if (Validation.isEmptyPassword(confirmPassword)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_confirm_pass));
        } else if (!(password.getText().toString().equals(confirmPassword.getText().toString()))) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_password_not_match));
        } else if (!isSalesRepresentative && Validation.isEmpty(gstin)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_enter_gst));
        } else if (!isSalesRepresentative && !Validation.isValidGST(gstin)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_gst_valid));
        } else if (!isSalesRepresentative && Validation.isEmpty(address)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_empty_address));
        } else if (!isSalesRepresentative && Validation.isEmpty(city)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_empty_city));
        } else if (!isSalesRepresentative && Validation.isEmpty(state)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_empty_state));
        } else if (!isSalesRepresentative && Validation.isEmpty(pincode)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_empty_pincode));
        } else if (!isSalesRepresentative && !Validation.isValidPincode(pincode)) {
            getNavigator().invalidSignUpForm(activity.getString(R.string.error_pincode_valid));
        } else if (Utility.isConnection(activity)) {
            getNavigator().validSignUpForm();
        } else {
            getNavigator().noInternetAlert();
        }
    }


    void callSignUpApi(String firstname, String lastname, String email, String mobilenumber, String password, int userType, String gstin,
                       DataState state, DataSaleType salesType, DataChannel channel, QAddAddress mAddress) {
        QSignUp signInRequest;
        if (userType == Constants.UserType.SALES_REPRESENTATIVE.getId()) {
            signInRequest = new QSignUp(firstname, lastname, email != null && email.equals("") ? null : email, mobilenumber, password, userType, gstin, state, salesType, channel);
        } else {
            signInRequest = new QSignUp(firstname, lastname, email != null && email.equals("") ? null : email, mobilenumber, password, userType, gstin, state, salesType, channel, mAddress);
        }
        Call<RSignUp> mSignUpResponeCall = getDataManager().userSignIn(signInRequest);
        mSignUpResponeCall.enqueue(new ApiResponse<RSignUp>() {
            @Override
            public void onSuccess(RSignUp signUpResponse, Headers headers) {
                if (signUpResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().pushOtpFragment();
                } else {
                    getNavigator().onApiError(new ApiError(signUpResponse.getErrorCode(), signUpResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    public boolean onEditorActionPincode(@NonNull final TextView textView, final int actionId,
                                         @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionPincode(textView, actionId, keyEvent);
    }


}

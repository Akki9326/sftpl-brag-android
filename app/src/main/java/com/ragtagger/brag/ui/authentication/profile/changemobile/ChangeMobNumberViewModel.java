package com.ragtagger.brag.ui.authentication.profile.changemobile;

import android.app.Activity;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ragtagger.brag.R;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.requests.QGenerateOtpForChangeMobile;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.data.model.response.RGeneralData;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

public class ChangeMobNumberViewModel extends CoreViewModel<ChangeMobNumberNavigator> {

    private final ObservableField<String> newMobileNumber = new ObservableField<>();

    public ChangeMobNumberViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public ObservableField<String> getNewMobileNumber() {
        return newMobileNumber;
    }

    public void setNewMobileNumber(String mobileNumber) {
        newMobileNumber.set(mobileNumber);
    }


    public View.OnClickListener onPassHideUnhideClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickHideUnhidePassword();
            }
        };
    }

    public View.OnClickListener onDoneClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickDone();
            }
        };
    }

    public boolean onEditorActionPass(@NonNull final TextView textView, final int actionId,
                                      @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionPass(textView, actionId, keyEvent);
    }

    void validateChangeMobileForm(Activity activity, EditText mobileNumber, EditText password) {
        if (Validation.isEmpty(mobileNumber)) {
            getNavigator().invalidChangeMobileForm(activity.getString(R.string.error_enter_mobile));
        } else if (!Validation.isValidMobileNum(mobileNumber)) {
            getNavigator().invalidChangeMobileForm(activity.getString(R.string.error_mobile_valid));
        } else if (Validation.isEmpty(password)) {
            getNavigator().invalidChangeMobileForm(activity.getString(R.string.error_pass));
        } else if (Utility.isConnection(activity)) {
            getNavigator().validChangeMobileForm();
        } else {
            getNavigator().noInternetAlert();
        }
    }

    public void callSendOtpForChangeMobApi(String newMobileNumber, String password) {
        QGenerateOtpForChangeMobile generateOtpForChangeMobile = new QGenerateOtpForChangeMobile(newMobileNumber, password);
        Call<RGeneralData> responeCall = getDataManager().generateOTPForMobileChange(generateOtpForChangeMobile);
        responeCall.enqueue(new ApiResponse<RGeneralData>() {
            @Override
            public void onSuccess(RGeneralData generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().pushOTPFragment();
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


}

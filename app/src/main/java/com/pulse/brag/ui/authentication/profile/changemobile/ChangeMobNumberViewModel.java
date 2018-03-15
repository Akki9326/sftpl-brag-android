package com.pulse.brag.ui.authentication.profile.changemobile;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.requests.QGenerateOtpForChangeMobile;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.data.model.response.RGeneralData;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.callback.OnSingleClickListener;

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


    public View.OnClickListener onDoneClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().done();
            }
        };
    }

    public View.OnClickListener onPassHideUnhideClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().hideUnhidePass();
            }
        };
    }

    public boolean onEditorActionPass(@NonNull final TextView textView, final int actionId,
                                      @Nullable final KeyEvent keyEvent) {
        return getNavigator().onEditorActionPass(textView, actionId, keyEvent);
    }

    public void sendOtpForChangeMob(String newMobileNumber, String password) {
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

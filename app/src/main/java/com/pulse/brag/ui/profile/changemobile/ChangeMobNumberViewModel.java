package com.pulse.brag.ui.profile.changemobile;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.pojo.GeneralResponse;
import com.pulse.brag.pojo.requests.ChangeMobileNumberRequest;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.views.OnSingleClickListener;

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

    public void setNewMobileNumber(String mobileNumber){
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

    public void changeMobNumber(String mobile, String password){
        // TODO: 13-12-2017 if you display mobile number in more Fragment than isStatus==true update mobile number display
        ChangeMobileNumberRequest changeMobileNumberRequest = new ChangeMobileNumberRequest(mobile,password);
        Call<GeneralResponse> mResponeCall = getDataManager().changeMobileNum(changeMobileNumberRequest);
        mResponeCall.enqueue(new ApiResponse<GeneralResponse>() {
            @Override
            public void onSuccess(GeneralResponse generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getDataManager().setUserData(new Gson().toJson(generalResponse.getData()));
                    getNavigator().finishActivity();
                } else {
                    getNavigator().onApiError(new ApiError(generalResponse.getErrorCode(), generalResponse.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);

            }
        });

       /* ApiClient.changeApiBaseUrl("http://103.204.192.148/brag/api/v1/");
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
        });*/
    }
}

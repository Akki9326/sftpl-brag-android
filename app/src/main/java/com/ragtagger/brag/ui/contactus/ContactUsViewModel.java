package com.ragtagger.brag.ui.contactus;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.response.RGeneralData;
import com.ragtagger.brag.data.model.requests.QContactUs;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

public class ContactUsViewModel extends CoreViewModel<ContactUsNavigator> {
    public ContactUsViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onSendClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickSend();
            }
        };
    }

    void validateContactUsForm(Activity activity, EditText name, EditText email, EditText msg) {
        if (Validation.isEmpty(name)) {
            getNavigator().invalidContactUsForm(activity.getString(R.string.error_empty_name));
        } else if (Validation.isEmpty(email)) {
            getNavigator().invalidContactUsForm(activity.getString(R.string.error_please_email));
        } else if (!Validation.isEmailValid(email)) {
            getNavigator().invalidContactUsForm(activity.getString(R.string.error_email_valid));
        } else if (Validation.isEmpty(msg)) {
            getNavigator().invalidContactUsForm(activity.getString(R.string.error_empty_message));
        } else if (Utility.isConnection(activity)) {
            getNavigator().validContactUsForm();
        } else {
            getNavigator().noInternetAlert();
        }
    }

    public void callSendMessageApi(String name, String email, String message) {
        getDataManager().contactUs(new QContactUs(name, email, message)).enqueue(new ApiResponse<RGeneralData>() {
            @Override
            public void onSuccess(RGeneralData generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().goBack();
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

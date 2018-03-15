package com.pulse.brag.ui.more;

import android.databinding.ObservableField;
import android.view.View;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.remote.ApiResponse;
import com.pulse.brag.data.model.response.RGeneralData;
import com.pulse.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/26/2018.
 */

public class MoreViewModel extends CoreViewModel<MoreNavigator> {

    private final ObservableField<String> fullName = new ObservableField<>();
    private final ObservableField<String> fullAddress = new ObservableField<>();
    private final ObservableField<Boolean> isEmptyAddress = new ObservableField<>();


    public MoreViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public ObservableField<String> getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        fullName.set(name);
    }

    public View.OnClickListener onProfileClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().profile();
            }
        };
    }

    public void logout() {
        Call<RGeneralData> responeCall = getDataManager().logoutCall();
        responeCall.enqueue(new ApiResponse<RGeneralData>() {
            @Override
            public void onSuccess(RGeneralData generalResponse, Headers headers) {
                if (generalResponse.isStatus()) {
                    getNavigator().onApiSuccess();
                    getDataManager().logout();
                    getNavigator().logout();
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

    public ObservableField<String> getFullAddress() {

        return fullAddress;
    }

    public void setFullAddress(String address) {
        setIsEmptyAddress(address.isEmpty() ? true : false);
        fullAddress.set(address);
    }

    public ObservableField<Boolean> getIsEmptyAddress() {
        return isEmptyAddress;
    }

    public void setIsEmptyAddress(boolean isEmptyAddress) {
        this.isEmptyAddress.set(isEmptyAddress);
    }
}

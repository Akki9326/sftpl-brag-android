package com.ragtagger.brag.ui.notification;

import android.databinding.ObservableField;
import android.view.View;

import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.response.RNotificationList;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/27/2018.
 */

public class NotificationListViewModel extends CoreViewModel<NotificationListNavigator> {

    private final ObservableField<Boolean> noInternet = new ObservableField<>();

    private final ObservableField<Boolean> noResult = new ObservableField<>();


    public ObservableField<Boolean> getNoInternet() {
        return noInternet;
    }

    public void setNoInternet(boolean noInternet) {
        this.noInternet.set(noInternet);
    }

    public ObservableField<Boolean> getNoResult() {
        return noResult;
    }

    public void setNoResult(boolean noResult) {
        this.noResult.set(noResult);
    }

    public NotificationListViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onReadClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().readNotification();
            }
        };
    }

    public void getNotifications(int page) {
        Call<RNotificationList> call = getDataManager().getNotifications(page);
        call.enqueue(new ApiResponse<RNotificationList>() {
            @Override
            public void onSuccess(RNotificationList rNotificationList, Headers headers) {
                if (rNotificationList.isStatus()) {
                    getNavigator().onApiSuccess();
                    if (rNotificationList.getData() != null && rNotificationList.getData().getObjects() != null && rNotificationList.getData().getObjects().size() > 0) {
                        //display data
                        getNavigator().setData(rNotificationList.getData());
                        getNavigator().setProductList(rNotificationList.getData().getCount(), rNotificationList.getData().getObjects());
                    } else {
                        getNavigator().onNoData();
                    }
                } else {
                    getNavigator().onApiError(new ApiError(rNotificationList.getErrorCode(), rNotificationList.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }
}

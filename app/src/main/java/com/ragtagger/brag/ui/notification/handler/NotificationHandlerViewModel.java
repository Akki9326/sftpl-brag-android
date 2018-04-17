package com.ragtagger.brag.ui.notification.handler;

import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.response.RGeneralData;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.toolbar.ToolbarViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/27/2018.
 */

public class NotificationHandlerViewModel extends ToolbarViewModel<NotificationHandlerNavigator> {

    public NotificationHandlerViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    /*ObservableField<String> toolbarTitle = new ObservableField<>();

    public ObservableField<String> getToolbarTitle() {
        return toolbarTitle;
    }

    public void updateToolbarTitle(String title) {
        this.toolbarTitle.set(title);
    }*/

    void notificationRead(String id) {
        Call<RGeneralData> notificationCall = getDataManager().notificationRead(id);
        notificationCall.enqueue(new ApiResponse<RGeneralData>() {
            @Override
            public void onSuccess(RGeneralData rNotification, Headers headers) {
                if (rNotification.isStatus()) {
                    getNavigator().onApiSuccessNotificationRead();
                } else {
                    getNavigator().onAPiErrorNotificationRead(new ApiError(rNotification.getErrorCode(), rNotification.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onAPiErrorNotificationRead(t);
            }
        });
    }
}

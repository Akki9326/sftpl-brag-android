package com.ragtagger.brag.ui.notification;

import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.response.RNotification;
import com.ragtagger.brag.data.remote.ApiResponse;
import com.ragtagger.brag.ui.core.CoreViewModel;

import okhttp3.Headers;
import retrofit2.Call;

/**
 * Created by alpesh.rathod on 2/27/2018.
 */

public class NotificationListViewModel extends CoreViewModel<NotificationListNavigator> {

    private final ObservableField<Boolean> visibility = new ObservableField<>();
    ObservableField<Boolean> noInternet = new ObservableField<>();


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

    public void getNotificationListAPI(int page) {
        Call<RNotification> notificationCall = getDataManager().getNotificationList(page);
        notificationCall.enqueue(new ApiResponse<RNotification>() {
            @Override
            public void onSuccess(RNotification rNotification, Headers headers) {
                if (rNotification.isStatus()) {
                    getNavigator().onApiSuccess();
                    getNavigator().getNotificationList(rNotification.getData(), rNotification.getData().getObjects());
                } else {
                    getNavigator().onApiError(new ApiError(rNotification.getErrorCode(), rNotification.getMessage()));
                }
            }

            @Override
            public void onError(ApiError t) {
                getNavigator().onApiError(t);
            }
        });
    }

    public ObservableField<Boolean> getListVisibility() {
        return visibility;
    }

    public void setListVisibility(boolean visibility) {
        this.visibility.set(visibility);
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNavigator().swipeToRefresh();
            }
        };
    }

    public int[] getColorSchemeResources() {
        return new int[]{
                R.color.pink,
        };
    }

    public ObservableField<Boolean> getNoInternet() {
        return noInternet;
    }

    public void setNoInternet(boolean noInternet) {
        this.noInternet.set(noInternet);
    }
}

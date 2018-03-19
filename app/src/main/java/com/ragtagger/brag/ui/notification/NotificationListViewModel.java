package com.ragtagger.brag.ui.notification;

import android.view.View;

import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.ui.core.CoreViewModel;

/**
 * Created by alpesh.rathod on 2/27/2018.
 */

public class NotificationListViewModel extends CoreViewModel<NotificationListNavigator> {
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
}

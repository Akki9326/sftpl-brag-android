package com.pulse.brag.ui.notification;

import android.view.View;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.core.CoreViewModel;

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

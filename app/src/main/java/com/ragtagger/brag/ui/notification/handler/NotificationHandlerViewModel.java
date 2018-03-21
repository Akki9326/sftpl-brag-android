package com.ragtagger.brag.ui.notification.handler;

import android.databinding.ObservableField;

import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.ui.core.CoreViewModel;

/**
 * Created by alpesh.rathod on 2/27/2018.
 */

public class NotificationHandlerViewModel extends CoreViewModel {

    public NotificationHandlerViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    ObservableField<String> toolbarTitle = new ObservableField<>();

    public ObservableField<String> getToolbarTitle() {
        return toolbarTitle;
    }

    public void updateToolbarTitle(String title) {
        this.toolbarTitle.set(title);
    }
}

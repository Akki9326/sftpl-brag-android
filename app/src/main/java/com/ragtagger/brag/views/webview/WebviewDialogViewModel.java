package com.ragtagger.brag.views.webview;

import android.databinding.ObservableField;
import android.view.View;

import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.ui.core.CoreViewModel;

/**
 * Created by alpesh.rathod on 2/28/2018.
 */

public class WebviewDialogViewModel extends CoreViewModel<WebviewDialogNavigator> {

    private final ObservableField<String> title = new ObservableField<>();
    private final ObservableField<String> subTitle = new ObservableField<>();

    public WebviewDialogViewModel(IDataManager dataManager) {
        super(dataManager);
    }


    public ObservableField<String> getTitle() {
        return title;
    }

    public ObservableField<String> getSubTitle() {
        return subTitle;
    }

    public void updateTitle(String title) {
        this.title.set(title);
    }

    public void updateSubTitle(String subTitle) {
        this.subTitle.set(subTitle);
    }

    public View.OnClickListener onCloseClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().close();
            }
        };
    }
}

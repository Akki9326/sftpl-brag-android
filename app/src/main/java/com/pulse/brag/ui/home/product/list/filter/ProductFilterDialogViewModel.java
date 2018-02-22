package com.pulse.brag.ui.home.product.list.filter;

import android.view.View;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.core.CoreViewModel;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class ProductFilterDialogViewModel extends CoreViewModel<ProductFilterDialogNavigator> {
    public ProductFilterDialogViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onDismissClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().dismissFragment();
            }
        };
    }

    public View.OnClickListener onApplyClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().applyFilter();
            }
        };
    }

    public View.OnClickListener onResetClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().resetFilter();
            }
        };
    }
}

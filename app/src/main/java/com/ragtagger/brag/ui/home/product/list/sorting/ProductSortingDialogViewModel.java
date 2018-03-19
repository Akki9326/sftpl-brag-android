package com.ragtagger.brag.ui.home.product.list.sorting;

import android.view.View;
import android.widget.RadioGroup;

import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.ui.core.CoreViewModel;

/**
 * Created by alpesh.rathod on 2/21/2018.
 */

public class ProductSortingDialogViewModel extends CoreViewModel<ProductSortingDialogNavigator> {

    public ProductSortingDialogViewModel(IDataManager dataManager) {
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

    public void onSortingChanged(RadioGroup radioGroup, int id) {
        getNavigator().updateSorting(radioGroup,id);
    }


}

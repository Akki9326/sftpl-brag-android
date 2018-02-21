package com.pulse.brag.ui.home.product.list.sorting;

import android.databinding.ObservableField;
import android.view.View;
import android.widget.RadioGroup;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.utils.Constants;

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

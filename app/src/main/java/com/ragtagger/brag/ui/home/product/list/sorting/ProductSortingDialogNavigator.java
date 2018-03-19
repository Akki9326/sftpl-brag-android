package com.ragtagger.brag.ui.home.product.list.sorting;

import android.widget.RadioGroup;

import com.ragtagger.brag.ui.core.CoreNavigator;

/**
 * Created by alpesh.rathod on 2/21/2018.
 */

public interface ProductSortingDialogNavigator extends CoreNavigator{
    void dismissFragment();
    void updateSorting(RadioGroup radioGroup, int id);
}

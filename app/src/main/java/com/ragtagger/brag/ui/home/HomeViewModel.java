package com.ragtagger.brag.ui.home;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.ragtagger.brag.R;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.ui.core.CoreViewModel;

/**
 * Created by nikhil.vadoliya on 15-02-2018.
 */


public class HomeViewModel extends CoreViewModel<HomeNavigator> {
    public HomeViewModel(IDataManager dataManager) {
        super(dataManager);
    }


    public boolean onNavigationClick(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottombar_item_categoty:
                getNavigator().openCategoryFragment();
                getNavigator().setToolbarCategory();
                return true;
            case R.id.bottombar_item_collection:
                getNavigator().openCollectionFragment();
                getNavigator().setToolbarCollection();
                return true;
            case R.id.bottombar_item_order:
                getNavigator().openQuickOrderFragement();
                getNavigator().setToolbarQuickOrder();
                return true;
            case R.id.bottombar_item_more:
                getNavigator().openMoreFragment();
                getNavigator().setToolbarMore();
                return true;
        }
        return false;
    }


}

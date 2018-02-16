package com.pulse.brag.ui.home;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

/**
 * Created by nikhil.vadoliya on 15-02-2018.
 */


public interface HomeNavigator {

    void openCategoryFragment();

    void openCollectionFragment();

    void openQuickOrderFragement();

    void openMoreFragment();

    void setToolbarCategory();

    void setToolbarCollection();

    void setToolbarQuickOrder();

    void setToolbarMore();
}

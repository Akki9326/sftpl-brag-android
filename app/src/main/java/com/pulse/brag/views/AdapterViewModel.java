package com.pulse.brag.views;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.pulse.brag.adapters.CategoryListAdapter;

/**
 * Created by nikhil.vadoliya on 16-02-2018.
 */


public class AdapterViewModel {

    private ItemViewModelListener listener;

    public AdapterViewModel(ItemViewModelListener listener) {
        this.listener = listener;
    }

    public void onClickItem(){
        listener.onClickItem();
    }

    public interface ItemViewModelListener {
        void onClickItem();
    }


}

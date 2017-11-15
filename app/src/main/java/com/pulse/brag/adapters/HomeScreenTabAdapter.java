package com.pulse.brag.adapters;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pulse.brag.fragments.CategoryFragment;
import com.pulse.brag.fragments.CollectionFragment;
import com.pulse.brag.fragments.OrderDatailFragment;

/**
 * Created by nikhil.vadoliya on 26-09-2017.
 */


public class HomeScreenTabAdapter extends FragmentPagerAdapter {

    public HomeScreenTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CategoryFragment.newInstance();
            case 1:
                return CollectionFragment.newInstance();
            case 2:
                return OrderDatailFragment.newInstance();
        }
        return CategoryFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 3;
    }

}

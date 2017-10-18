package com.pulse.brag.activities;

import android.os.Bundle;

import com.pulse.brag.fragments.HomeFragment;
import com.pulse.brag.R;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);


        setBagCount(60);
        pushFragments(new HomeFragment(), false, false);

    }
}


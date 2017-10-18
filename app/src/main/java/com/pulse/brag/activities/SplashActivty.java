package com.pulse.brag.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.pulse.brag.R;
import com.pulse.brag.helper.PreferencesManager;
import com.pulse.brag.helper.Utility;

public class SplashActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_activty);

        Utility.applyTypeFace(getApplicationContext(), (RelativeLayout) findViewById(R.id.base_layout));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (PreferencesManager.getInstance().isLogin()) {
                    startActivity(new Intent(SplashActivty.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivty.this, LoginActivity.class));
                }
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        }, 2000);
    }
}

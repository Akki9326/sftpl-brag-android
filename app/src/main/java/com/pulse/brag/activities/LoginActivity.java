package com.pulse.brag.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.pulse.brag.R;
import com.pulse.brag.fragments.LogInFragment;
import com.pulse.brag.interfaces.BaseInterface;

public class LoginActivity extends AppCompatActivity implements BaseInterface {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);


        initializeData();
        setListeners();
        showData();
    }

    @Override
    public void setToolbar() {

    }

    @Override
    public void initializeData() {
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void showData() {

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.login_contrainer, new LogInFragment()).commit();
    }
}

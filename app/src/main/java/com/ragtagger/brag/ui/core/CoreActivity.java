package com.ragtagger.brag.ui.core;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.IFragmentCallback;
import com.ragtagger.brag.callback.IFragmentLoader;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Common;
import com.ragtagger.brag.utils.NetworkUtils;
import com.ragtagger.brag.utils.ToastUtils;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.views.CustomProgressDialog;

import dagger.android.AndroidInjection;


/**
 * Created by alpesh.rathod on 2/1/2018.
 */

public abstract class CoreActivity<B extends CoreActivity, T extends ViewDataBinding, V extends CoreViewModel> extends AppCompatActivity implements IFragmentLoader, IFragmentCallback {

    // this can probably depend on isLoading variable of CoreViewModel,
    // since its going to be common for all the activities

    Toolbar mToolbar;
    TextView mToolbarTitle, mTxtBagCount;
    public TextView mTxtReadAll;
    ImageView mImgBack;
    ImageView mImgLogo;

    LinearLayout mLinearCart;
    CustomProgressDialog mProgressDialog;
    RelativeLayout mRelText;

    protected B bActivity = (B) this;
    private T mViewDataBinding;
    private V mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        beforeLayoutSet();
        performDataBinding();
        afterLayoutSet();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted(requestCode);
        } else {
            onPermissionDenied(requestCode);
        }
    }

    @Override
    public void pushFragment(int containerId, Fragment fragment, boolean shouldAnimate, boolean addToStack, String tag) {

    }

    @Override
    public void pushFragment(int containerId, Fragment fragment, boolean shouldAnimate, boolean addToStack) {

    }

    @Override
    public void pushFragment(int containerId, Fragment fragment, boolean shouldAnimate, String tag) {

    }

    @Override
    public void pushFragment(int containerId, Fragment fragment, boolean shouldAnimate) {

    }

    /**
     * called when fragment attached to the current
     * activity
     */
    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    public abstract void beforeLayoutSet();

    public abstract void afterLayoutSet();

    public void onPermissionGranted(int request) {
    }

    public void onPermissionDenied(int request) {
    }

    private void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    public B getActivityInstance() {
        return bActivity;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public IDataManager getDataManager() {
        return getViewModel().getDataManager();
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void showKeyboard() {
        View view = this.getCurrentFocus();
        Common.showKeyboard(this, view);
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        Common.hideKeyboard(this, view);
    }

    protected void showProgress() {
    }

    protected void hideProgress() {
    }

    public void showAlert(String msg) {
        if (bActivity != null) AlertUtils.createProgressDialog(bActivity, msg);
    }

    public void showToast(String msg) {
        ToastUtils.show(this, msg);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermission(String[] permission, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission, requestCode);
        }
    }

    public void setUpToolbar(Toolbar toolbar, TextView toolbarTitle, ImageView back, ImageView logo, LinearLayout linearCart, TextView bagCount, RelativeLayout relText, TextView textReadAll) {

        mToolbar = toolbar;
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mToolbarTitle = toolbarTitle;
        mImgBack = back;
        mImgLogo = logo;
        mLinearCart = linearCart;
        mTxtBagCount = bagCount;
        mRelText = relText;
        mTxtReadAll = textReadAll;


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Utility.applyTypeFace(getApplicationContext(), toolbar);


        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.hideSoftkeyboard(CoreActivity.this, view);
                onBackPressed();
            }
        });
    }

    /**
     * Main activity fragment toolbar
     *
     * @param isBackBtn
     * @param isLogo
     * @param isCardBtn
     */
    public void showToolbar(boolean isBackBtn, boolean isLogo, boolean isCardBtn) {
        mTxtReadAll.setVisibility(View.GONE);
        if (isBackBtn) {
            mImgBack.setVisibility(View.VISIBLE);
            mRelText.setVisibility(View.VISIBLE);
        } else {
            mImgBack.setVisibility(View.GONE);
        }
        if (isLogo) {
            mImgLogo.setVisibility(View.VISIBLE);
            mToolbarTitle.setVisibility(View.GONE);
            mRelText.setVisibility(View.GONE);
        } else {
            mToolbarTitle.setVisibility(View.VISIBLE);
            mRelText.setVisibility(View.VISIBLE);
            mImgLogo.setVisibility(View.GONE);
        }
        if (isCardBtn) {
            mLinearCart.setVisibility(View.VISIBLE);
        } else {
            mLinearCart.setVisibility(View.GONE);
        }
    }

    public void showToolbar(boolean isBack, boolean isLogo, boolean isCard, String title) {
        showToolbar(isBack, isLogo, isCard);
        if (!isLogo)
            mToolbarTitle.setText(title);
    }

    /**
     * When title available but logo not there
     *
     * @param isBack
     * @param isLogo
     * @param title
     * @param rightLabel
     */
    public void showToolbar(boolean isBack, boolean isLogo, String title, String rightLabel) {
        mTxtReadAll.setVisibility(View.VISIBLE);
        mToolbarTitle.setText(title);
        mTxtReadAll.setText(rightLabel);
        if (isBack) {
            mImgBack.setVisibility(View.VISIBLE);
            mRelText.setVisibility(View.VISIBLE);
        } else {
            mImgBack.setVisibility(View.GONE);
        }
        if (isLogo) {
            mImgLogo.setVisibility(View.VISIBLE);
            mToolbarTitle.setVisibility(View.GONE);
            mRelText.setVisibility(View.GONE);
        } else {
            mToolbarTitle.setVisibility(View.VISIBLE);
            mRelText.setVisibility(View.VISIBLE);
            mImgLogo.setVisibility(View.GONE);
        }
    }

    public void setBagCount(int num) {
        if (num == 0) {
            mTxtBagCount.setVisibility(View.GONE);
        } else {
            mTxtBagCount.setVisibility(View.VISIBLE);
            mTxtBagCount.setText(Utility.getBadgeNumber(num));
        }
    }

    public String getNotificationlabel() {
        if (BragApp.NotificationNumber > 0) {
            return getString(R.string.toolbar_label_notification) + " (" + Utility.getBadgeNumber(BragApp.NotificationNumber) + ")";
        } else {
            return getString(R.string.toolbar_label_notification);
        }
    }


    public interface OnToolbarSetupListener {
        void setUpToolbar();
    }

    public interface OnDrawerStateListener {
        boolean manageDrawerOnBackPressed();
    }
}

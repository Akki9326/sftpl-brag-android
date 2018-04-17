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
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.IFragmentCallback;
import com.ragtagger.brag.callback.IFragmentLoader;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Common;
import com.ragtagger.brag.utils.NetworkUtils;
import com.ragtagger.brag.utils.ToastUtils;
import com.ragtagger.brag.views.CustomProgressDialog;

import dagger.android.AndroidInjection;


/**
 * Created by alpesh.rathod on 2/1/2018.
 */

public abstract class CoreActivity<B extends CoreActivity, T extends ViewDataBinding, V extends CoreViewModel> extends AppCompatActivity implements IFragmentLoader, IFragmentCallback {

    // this can probably depend on isLoading variable of CoreViewModel,
    // since its going to be common for all the activities
    CustomProgressDialog mProgressDialog;

    protected B bActivity = (B) this;
    private T mViewDataBinding;
    protected V mViewModel;

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
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (shouldAnimate) {
            ft.setCustomAnimations(R.anim.right_in, R.anim.left_out,
                    R.anim.left_in, R.anim.right_out);
        }
        if (addToStack) {
            ft.addToBackStack(tag);
        }
        if (getSupportFragmentManager().findFragmentById(containerId) != null) {
            ft.hide(getSupportFragmentManager().findFragmentById(containerId));
        }
        ft.add(containerId, fragment);

        if (!isFinishing()) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
    }

    @Override
    public void pushFragment(int containerId, Fragment fragment, boolean shouldAnimate, boolean addToStack) {
        pushFragment(containerId, fragment, shouldAnimate, addToStack, fragment.getClass().getSimpleName());
    }

    @Override
    public void pushFragment(int containerId, Fragment fragment, boolean shouldAnimate, String tag) {
        pushFragment(containerId, fragment, shouldAnimate, false, tag);
    }

    @Override
    public void pushFragment(int containerId, Fragment fragment, boolean shouldAnimate) {
        pushFragment(containerId, fragment, shouldAnimate, false, fragment.getClass().getSimpleName());
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
        if (bActivity != null) AlertUtils.showAlertMessage(bActivity, msg);
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

    public interface OnToolbarSetupListener {
        void setUpToolbar();
    }

    public interface OnDrawerStateListener {
        boolean manageDrawerOnBackPressed();
    }
}

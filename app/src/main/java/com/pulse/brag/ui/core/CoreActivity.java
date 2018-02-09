package com.pulse.brag.ui.core;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.pulse.brag.callback.IFragmentCallback;
import com.pulse.brag.callback.IFragmentLoader;
import com.pulse.brag.utils.Common;
import com.pulse.brag.utils.NetworkUtils;
import com.pulse.brag.utils.ToastUtils;

import dagger.android.AndroidInjection;


/**
 * Created by alpesh.rathod on 2/1/2018.
 */

public abstract class CoreActivity<B extends CoreActivity,T extends ViewDataBinding, V extends CoreViewModel> extends AppCompatActivity implements IFragmentLoader, IFragmentCallback {

    // TODO
    // this can probably depend on isLoading variable of CoreViewModel,
    // since its going to be common for all the activities
    private ProgressDialog mProgressDialog;

    protected B bActivity = (B)this;
    private T mViewDataBinding;
    private V mViewModel;

    //private List<Fragment> fragmentList = new ArrayList<>();
    //private FragmentStack fragmentStack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        beforeLayoutSet();
        performDataBinding();

        //init fragmentstack here if required
        //fragmentStack = new FragmentStack(this, getSupportFragmentManager());
    }

    private void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();

        if (bActivity instanceof OnToolbarSetupListener){
            //((OnToolbarSetupListener) bActivity).setUpToolbar();
        }

    }

    private void showProgress(String msg) {

    }

    private void hideProgress() {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermission(String[] permission, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void showKeyboard(){
        View view = this.getCurrentFocus();
        Common.showKeyboard(this, view);
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        Common.hideKeyboard(this, view);
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void showToast(String msg) {
        ToastUtils.show(this,msg);
    }

    public B getActivityInstance() {
        return bActivity;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
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

    @Override
    public void pushFragment(int containerId, Fragment fragment, boolean addToStack, String tag) {

    }

    @Override
    public void pushFragment(int containerId, Fragment fragment, boolean addToStack) {

    }

    @Override
    public void pushFragment(int containerId, Fragment fragment, String tag) {

    }

    @Override
    public void pushFragment(int containerId, Fragment fragment) {

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

    public interface OnToolbarSetupListener {
        void setUpToolbar(Toolbar toolbar);
    }

    public interface OnDrawerStateListener {
        boolean manageDrawerOnBackPressed();
    }
}

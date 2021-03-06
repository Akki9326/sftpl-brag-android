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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ragtagger.brag.utils.AppLogger;
import com.ragtagger.brag.views.CustomProgressDialog;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by alpesh.rathod on 2/1/2018.
 */

public abstract class CoreFragment<T extends ViewDataBinding, V extends CoreViewModel> extends Fragment {

    private View mRootView;
    private CustomProgressDialog mProgressDialog;

    private T mViewDataBinding;
    private V mViewModel;
    protected CoreActivity mActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CoreActivity) {
            CoreActivity activity = (CoreActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
        mProgressDialog = new CustomProgressDialog(mActivity == null ? getActivity() : mActivity);
        setHasOptionsMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        beforeViewCreated();
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRootView = mViewDataBinding.getRoot();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
        afterViewCreated();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mActivity instanceof CoreActivity.OnToolbarSetupListener) {
            setUpToolbar();
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
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

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * To perform operation before view set like bundle data and other data initialization
     */
    public abstract void beforeViewCreated();

    /**
     * To perform operation after view set
     */
    public abstract void afterViewCreated();


    /**
     * Setup toolbar
     */
    public abstract void setUpToolbar();


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


    public void onPermissionGranted(int request) {
    }

    public void onPermissionDenied(int request) {
    }

    private void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
    }

    public CoreActivity getBaseActivity() {
        return mActivity;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public boolean isNetworkConnected() {
        return mActivity != null && mActivity.isNetworkConnected();
    }

    public void showKeyboard() {
        if (mActivity != null) {
            mActivity.showKeyboard();
        }
    }

    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    public void showProgress() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing())
                    hideProgress();
                mProgressDialog.show("");
            }
        } catch (Exception e) {
            AppLogger.e("show progress - " + e.getMessage(), e.getCause());
        }
    }

    public void hideProgress() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss("");
                mProgressDialog.dismiss();
                mProgressDialog.hide("");
                mProgressDialog.hide();
            }
        } catch (Exception e) {
            AppLogger.e("hide progress - " + e.getMessage(), e.getCause());
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                mActivity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }


}

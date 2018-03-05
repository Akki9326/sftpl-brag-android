package com.pulse.brag.ui.core;

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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pulse.brag.utils.AppLogger;
import com.pulse.brag.utils.Common;
import com.pulse.brag.views.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by alpesh.rathod on 2/1/2018.
 */

public abstract class CoreFragment<T extends ViewDataBinding, V extends CoreViewModel> extends Fragment {

    protected CoreActivity mActivity;
    private T mViewDataBinding;
    private V mViewModel;
    private View mRootView;


    private CustomProgressDialog mProgressDialog;

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

    private void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
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
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> needrequest = new ArrayList<>();
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(getActivity(), permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    needrequest.add(permission);
                }
            }
            if (needrequest.size() > 0) {
                requestPermissions(needrequest.toArray(new String[needrequest.size()]), requestCode);
                return;
            }
        }
        onPermissionGranted(requestCode);*/
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


    public void onPermissionGranted(int request) {
    }

    public void onPermissionDenied(int request) {
    }


    public void showProgress() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing())
                    hideProgress();
                mProgressDialog.show("");
            }
        } catch (Exception e) {
            AppLogger.e(e.getMessage(), e.getCause());
        }
    }

    public void hideProgress() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss("");
            }
        } catch (Exception e) {
            AppLogger.e(e.getMessage(), e.getCause());
        }
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

    public boolean isNetworkConnected() {
        return mActivity != null && mActivity.isNetworkConnected();
    }


    public CoreActivity getBaseActivity() {
        return mActivity;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

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

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();
}

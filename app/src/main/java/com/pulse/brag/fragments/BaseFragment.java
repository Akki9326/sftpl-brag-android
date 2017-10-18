package com.pulse.brag.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.pulse.brag.activities.BaseActivity;
import com.pulse.brag.views.CustomProgressDialog;

/**
 * Created by paras.sampat on 26-05-2017.
 */
public class BaseFragment extends Fragment {

    private CustomProgressDialog mProgressDialog;
    public Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new CustomProgressDialog(getActivity());
        mActivity = ((BaseActivity) getActivity());
    }


    public void showProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing())
                    hideProgressDialog();
                mProgressDialog.show("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void hideProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss("");
            }
        } catch (Exception e) {

        }

    }
}

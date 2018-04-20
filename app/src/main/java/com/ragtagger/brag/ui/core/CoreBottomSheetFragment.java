package com.ragtagger.brag.ui.core;

import android.databinding.ViewDataBinding;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;

import com.ragtagger.brag.views.CustomProgressDialog;

/**
 * Created by alpesh.rathod on 2/21/2018.
 */

public class CoreBottomSheetFragment<T extends ViewDataBinding, V extends CoreViewModel> extends BottomSheetDialogFragment{

    private CoreActivity mActivity;
    private T mViewDataBinding;
    private V mViewModel;
    private View mRootView;


    private CustomProgressDialog mProgressDialog;

}

package com.pulse.brag.ui.home.product.list.sorting;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.DialogFragmentProductSortingBinding;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.ui.core.CoreDialogFragment;
import com.pulse.brag.ui.home.product.list.ProductListFragment;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;

import javax.inject.Inject;

/**
 * Created by alpesh.rathod on 2/21/2018.
 */

public class ProductSortingDialogFragment extends CoreDialogFragment<DialogFragmentProductSortingBinding, ProductSortingDialogViewModel> implements ProductSortingDialogNavigator {

    private static final int RES_SORTING = 2;

    @Inject
    ProductSortingDialogViewModel mProductSortingDialogViewModel;

    DialogFragmentProductSortingBinding mFragmentProductSortingBinding;

    int mSorting;
    boolean isFirst;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_DialogWhenLarge_NoActionBar);
        mProductSortingDialogViewModel.setNavigator(this);
    }

    @Override
    public Dialog onCreateFragmentDialog(Bundle savedInstanceState, Dialog dialog) {
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(130);
        dialog.getWindow().setBackgroundDrawable(d);

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }

    @Override
    public void beforeViewCreated() {
        if (getArguments().containsKey(Constants.BUNDLE_PRODUCT_SORTING)) {
            mSorting = getArguments().getInt(Constants.BUNDLE_PRODUCT_SORTING);
            isFirst = true;
        }
    }

    @Override
    public void afterViewCreated() {
        mFragmentProductSortingBinding = getViewDataBinding();
        Utility.applyTypeFace(getActivity(), (LinearLayout) mFragmentProductSortingBinding.baseLayout);
        checkRadio(mSorting);
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public ProductSortingDialogViewModel getViewModel() {
        return mProductSortingDialogViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_fragment_product_sorting;
    }

    @Override
    public void onApiSuccess() {
        hideProgress();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
    }

    @Override
    public void dismissFragment() {
        dismissDialog("");
    }

    @Override
    public void updateSorting(RadioGroup radioGroup, int id) {

        IOnSortListener listener = (IOnSortListener)getParentFragment();

        if (!isFirst && prveCheckedId != id) {
            prveCheckedId = id;
            if (id == mFragmentProductSortingBinding.rbPopularity.getId()) {
                //mSortingListener.onSorting(Constants.ProductSorting.POPULARITY.ordinal());
                listener.onSort(Constants.ProductSorting.POPULARITY.ordinal());
                dismissDialog("");
            } else if (id == mFragmentProductSortingBinding.rbPriceLowToHigh.getId()) {
                //mSortingListener.onSorting(Constants.ProductSorting.PRICE_LOW_TO_HEIGH.ordinal());
                listener.onSort(Constants.ProductSorting.PRICE_LOW_TO_HEIGH.ordinal());
                dismissDialog("");
            } else if (id == mFragmentProductSortingBinding.rbPriceHighToLow.getId()) {
                //mSortingListener.onSorting(Constants.ProductSorting.PRICE_HEIGH_TO_LOW.ordinal());
                listener.onSort(Constants.ProductSorting.PRICE_HEIGH_TO_LOW.ordinal());
                dismissDialog("");
            }

        }
        isFirst = false;
    }

    int prveCheckedId;

    private void checkRadio(int sorting) {
        switch (Constants.ProductSorting.values()[sorting]) {
            case POPULARITY:
                prveCheckedId =mFragmentProductSortingBinding.rbPopularity.getId();
                mFragmentProductSortingBinding.rgSorting.check(mFragmentProductSortingBinding.rbPopularity.getId());
                break;
            case PRICE_LOW_TO_HEIGH:
                prveCheckedId =mFragmentProductSortingBinding.rbPriceLowToHigh.getId();
                mFragmentProductSortingBinding.rgSorting.check(mFragmentProductSortingBinding.rbPriceLowToHigh.getId());
                break;
            case PRICE_HEIGH_TO_LOW:
                prveCheckedId =mFragmentProductSortingBinding.rbPriceHighToLow.getId();
                mFragmentProductSortingBinding.rgSorting.check(mFragmentProductSortingBinding.rbPriceHighToLow.getId());
                break;
        }

    }

    public interface  IOnSortListener{
        void onSort(int type);
    }

}

package com.ragtagger.brag.ui.home.product.list.sorting;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.databinding.DialogFragmentProductSortingBinding;
import com.ragtagger.brag.ui.core.CoreDialogFragment;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;

import javax.inject.Inject;

/**
 * Created by alpesh.rathod on 2/21/2018.
 */

public class ProductSortingDialogFragment extends CoreDialogFragment<DialogFragmentProductSortingBinding, ProductSortingDialogViewModel> implements ProductSortingDialogNavigator {


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
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(),null);
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
            if (id == mFragmentProductSortingBinding.rbPriceLowToHigh.getId()) {
                listener.onSort(Constants.SortBy.PRICE_ASC.ordinal());
                dismissDialog("");
            } else if (id == mFragmentProductSortingBinding.rbPriceHighToLow.getId()) {
                listener.onSort(Constants.SortBy.PRICE_DESC.ordinal());
                dismissDialog("");
            }else if (id == mFragmentProductSortingBinding.rbDateAsc.getId()) {
                listener.onSort(Constants.SortBy.DATE_ASC.ordinal());
                dismissDialog("");
            } else if (id == mFragmentProductSortingBinding.rbDateDesc.getId()) {
                listener.onSort(Constants.SortBy.DATE_DESC.ordinal());
                dismissDialog("");
            }

        }
        isFirst = false;
    }

    int prveCheckedId;

    private void checkRadio(int sorting) {
        switch (Constants.SortBy.values()[sorting]) {
            case PRICE_ASC:
                prveCheckedId =mFragmentProductSortingBinding.rbPriceLowToHigh.getId();
                mFragmentProductSortingBinding.rgSorting.check(mFragmentProductSortingBinding.rbPriceLowToHigh.getId());
                break;
            case PRICE_DESC:
                prveCheckedId =mFragmentProductSortingBinding.rbPriceHighToLow.getId();
                mFragmentProductSortingBinding.rgSorting.check(mFragmentProductSortingBinding.rbPriceHighToLow.getId());
                break;
            case DATE_ASC:
                prveCheckedId =mFragmentProductSortingBinding.rbDateAsc.getId();
                mFragmentProductSortingBinding.rgSorting.check(mFragmentProductSortingBinding.rbDateAsc.getId());
                break;
            case DATE_DESC:
                prveCheckedId =mFragmentProductSortingBinding.rbDateDesc.getId();
                mFragmentProductSortingBinding.rgSorting.check(mFragmentProductSortingBinding.rbDateDesc.getId());
                break;
        }

    }

    public interface  IOnSortListener{
        void onSort(int type);
    }

}

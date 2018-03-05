package com.pulse.brag.ui.home.product.list.filter;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.DialogFragmentProductFilterBinding;
import com.pulse.brag.callback.IOnProductColorSelectListener;
import com.pulse.brag.callback.IOnProductSizeSelectListener;
import com.pulse.brag.ui.core.CoreDialogFragment;
import com.pulse.brag.ui.home.product.list.adapter.ColorFilterAdapter;
import com.pulse.brag.ui.home.product.list.adapter.model.ColorModel;
import com.pulse.brag.ui.home.product.list.adapter.SizeFilterAdapter;
import com.pulse.brag.ui.home.product.list.adapter.model.SizeModel;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.views.HorizontalSpacingDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class ProductFilterDialogFragment extends CoreDialogFragment<DialogFragmentProductFilterBinding, ProductFilterDialogViewModel> implements ProductFilterDialogNavigator, IOnProductColorSelectListener, IOnProductSizeSelectListener {

    @Inject
    ProductFilterDialogViewModel mProductFilterDialogViewModel;

    @Inject
    @Named("colorFilter")
    GridLayoutManager mColorLayoutManager;

    @Inject
    @Named("sizeFilter")
    GridLayoutManager mSizeLayoutManager;

    DialogFragmentProductFilterBinding mDialogFragmentProductFilterBinding;

    List<ColorModel> colorModels;
    List<SizeModel> sizeModels;

    ColorFilterAdapter mColorAdapter;
    SizeFilterAdapter mSizeAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_DialogWhenLarge_NoActionBar);
        mProductFilterDialogViewModel.setNavigator(this);
    }

    @Override
    public Dialog onCreateFragmentDialog(Bundle savedInstanceState, Dialog dialog) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounder_corner_solid_white);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Override
    public void beforeViewCreated() {
        colorModels = new ArrayList<>();
        sizeModels = new ArrayList<>();
    }

    @Override
    public void afterViewCreated() {
        mDialogFragmentProductFilterBinding = getViewDataBinding();
        Utility.applyTypeFace(getActivity(), (LinearLayout) mDialogFragmentProductFilterBinding.baseLayout);

        mDialogFragmentProductFilterBinding.recycleViewColor.setHasFixedSize(true);
        mDialogFragmentProductFilterBinding.recycleViewColor.setLayoutManager(mColorLayoutManager);

        mDialogFragmentProductFilterBinding.recycleViewSize.setHasFixedSize(true);
        mDialogFragmentProductFilterBinding.recycleViewSize.setLayoutManager(mSizeLayoutManager);

        showData();
    }

    @Override
    public void setUpToolbar() {
        mProductFilterDialogViewModel.updateTitle(getString(R.string.label_filter_camel));
    }

    @Override
    public ProductFilterDialogViewModel getViewModel() {
        return mProductFilterDialogViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_fragment_product_filter;
    }

    public void showData() {

        colorModels = mProductFilterDialogViewModel.buildColorList();
        mColorAdapter = new ColorFilterAdapter(getActivity(), colorModels, this);
        mDialogFragmentProductFilterBinding.recycleViewColor.setAdapter(mColorAdapter);
        mDialogFragmentProductFilterBinding.recycleViewColor.addItemDecoration(new HorizontalSpacingDecoration(10));


        sizeModels = mProductFilterDialogViewModel.buildSizeList();
        mSizeAdapter = new SizeFilterAdapter(getActivity(), sizeModels, this);
        mDialogFragmentProductFilterBinding.recycleViewSize.setAdapter(mSizeAdapter);
        mDialogFragmentProductFilterBinding.recycleViewSize.addItemDecoration(new HorizontalSpacingDecoration(10));

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
    public void onSeleteColor(int pos) {

    }

    @Override
    public void OnSelectedSize(int pos) {

    }

    @Override
    public void dismissFragment() {
        dismissDialog("");
    }

    @Override
    public void applyFilter() {

    }

    @Override
    public void resetFilter() {
        // TODO: 2/23/2018 Handle through boolean like already reset than no need to reset again
        mColorAdapter.resetList(mProductFilterDialogViewModel.buildColorList());
        mSizeAdapter.resetSize(mProductFilterDialogViewModel.buildSizeList());


    }

    public interface IFilterApplyListener {
        void applyFilter(String color, int colorPos, String size, int sizePos);
    }
}

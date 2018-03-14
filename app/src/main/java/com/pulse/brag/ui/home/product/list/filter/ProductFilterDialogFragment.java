package com.pulse.brag.ui.home.product.list.filter;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.datas.DataFilter;
import com.pulse.brag.data.model.requests.QProductList;
import com.pulse.brag.databinding.DialogFragmentProductFilterBinding;
import com.pulse.brag.ui.core.CoreDialogFragment;
import com.pulse.brag.ui.home.product.list.adapter.ColorFilterAdapter;
import com.pulse.brag.ui.home.product.list.adapter.SizeFilterAdapter;
import com.pulse.brag.ui.home.product.list.filter.listener.IFilterSelectedListener;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.views.HorizontalSpacingDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by alpesh.rathod on 2/22/2018.
 */

public class ProductFilterDialogFragment extends CoreDialogFragment<DialogFragmentProductFilterBinding, ProductFilterDialogViewModel> implements ProductFilterDialogNavigator, IFilterSelectedListener {

    @Inject
    ProductFilterDialogViewModel mProductFilterDialogViewModel;

    @Inject
    @Named("colorFilter")
    GridLayoutManager mColorLayoutManager;

    @Inject
    @Named("sizeFilter")
    GridLayoutManager mSizeLayoutManager;

    DialogFragmentProductFilterBinding mDialogFragmentProductFilterBinding;

    List<DataFilter.ColorCode> colorModels;
    List<DataFilter.SizeCode> sizeModels;

    ColorFilterAdapter mColorAdapter;
    SizeFilterAdapter mSizeAdapter;

    private String mCategory;
    private String mSubCategory;
    private String mSeasonCode;

    DataFilter.Filter mAppliedFilter;
    HashMap<String, DataFilter.ColorCode> mMapSelectedColor;
    HashMap<String, DataFilter.SizeCode> mMapSelectedSize;

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
        mMapSelectedColor = new HashMap<>();
        mMapSelectedSize = new HashMap<>();

        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_CATEGORY_NAME)) {
            mCategory = getArguments().getString(Constants.BUNDLE_CATEGORY_NAME);
        }

        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_SUB_CATEGORY_NAME)) {
            mSubCategory = getArguments().getString(Constants.BUNDLE_SUB_CATEGORY_NAME);
        }

        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_SEASON_CODE)) {
            mSeasonCode = getArguments().getString(Constants.BUNDLE_SEASON_CODE);
        }

        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_APPLIED_FILTER)) {
            mAppliedFilter = getArguments().getParcelable(Constants.BUNDLE_APPLIED_FILTER);
        }
    }

    @Override
    public void afterViewCreated() {
        mDialogFragmentProductFilterBinding = getViewDataBinding();
        Utility.applyTypeFace(getActivity(), (LinearLayout) mDialogFragmentProductFilterBinding.baseLayout);

        mDialogFragmentProductFilterBinding.recycleViewColor.setHasFixedSize(true);
        mDialogFragmentProductFilterBinding.recycleViewColor.setLayoutManager(mColorLayoutManager);

        mDialogFragmentProductFilterBinding.recycleViewSize.setHasFixedSize(true);
        mDialogFragmentProductFilterBinding.recycleViewSize.setLayoutManager(mSizeLayoutManager);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showData();
            }
        }, 500);

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
        if (Utility.isConnection(getActivity())) {
            mProductFilterDialogViewModel.getFilter(mCategory, mSubCategory, mSeasonCode, mAppliedFilter);
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null, new AlertUtils.IDismissDialogListener() {
                @Override
                public void dismissDialog(Dialog dialog) {
                    dialog.dismiss();
                    dismissFragment();
                }
            });
        }
    }

    @Override
    public void onApiSuccess() {
        hideProgress();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), new AlertUtils.IDismissDialogListener() {
            @Override
            public void dismissDialog(Dialog dialog) {
                dialog.dismiss();
                dismissFragment();
            }
        });
    }


    @Override
    public void dismissFragment() {
        dismissDialog("");
    }

    @Override
    public void applyFilter() {
        IFilterApplyListener listener = (IFilterApplyListener) getParentFragment();
        QProductList.Filter filter = new QProductList.Filter();

        List<QProductList.ColorCode> colorList = new ArrayList<>();
        if (mMapSelectedColor != null && mMapSelectedColor.size() > 0)
            for (Object value : mMapSelectedColor.values()) {
                DataFilter.ColorCode color = (DataFilter.ColorCode) value;
                colorList.add(new QProductList.ColorCode(color.getColor(), color.getHash()));
            }
        if (colorList.size() > 0)
            filter.setColorCodes(colorList);

        List<String> sizeList = new ArrayList<>();
        if (mMapSelectedSize != null && mMapSelectedSize.size() > 0)
            for (Object value : mMapSelectedSize.values()) {
                DataFilter.SizeCode size = (DataFilter.SizeCode) value;
                sizeList.add(size.getSize());
            }
        if (sizeList.size() > 0)
            filter.setSizeCodes(sizeList);

        listener.applyFilter(filter);
        dismissFragment();
    }

    @Override
    public void resetFilter() {
        if (mMapSelectedColor != null)
            mMapSelectedColor.clear();
        if (mMapSelectedSize != null)
            mMapSelectedSize.clear();

        for (int i = 0; i < colorModels.size(); i++) {
            if (colorModels.get(i).isSelected()) {
                colorModels.get(i).setSelected(false);
                mColorAdapter.notifyItemChanged(i);
            }
        }

        for (int i = 0; i < sizeModels.size(); i++) {
            if (sizeModels.get(i).isSelected()) {
                sizeModels.get(i).setSelected(false);
                mSizeAdapter.notifyItemChanged(i);
            }
        }
    }

    @Override
    public void setColorFilter(List<DataFilter.ColorCode> colorCodeList, HashMap<String, DataFilter.ColorCode> selectedMap) {
        mMapSelectedColor.clear();
        mMapSelectedColor.putAll(selectedMap);
        colorModels.clear();
        colorModels.addAll(colorCodeList);
        mColorAdapter = new ColorFilterAdapter(getActivity(), colorModels, this);
        mDialogFragmentProductFilterBinding.recycleViewColor.setAdapter(mColorAdapter);
        mDialogFragmentProductFilterBinding.recycleViewColor.addItemDecoration(new HorizontalSpacingDecoration(10));
    }

    @Override
    public void noColorFilter() {

    }

    @Override
    public void setSizeList(List<DataFilter.SizeCode> sizeList, HashMap<String, DataFilter.SizeCode> selectedMap) {
        mMapSelectedSize.clear();
        mMapSelectedSize.putAll(selectedMap);
        sizeModels.clear();
        sizeModels.addAll(sizeList);
        mSizeAdapter = new SizeFilterAdapter(getActivity(), sizeModels, this);
        mDialogFragmentProductFilterBinding.recycleViewSize.setAdapter(mSizeAdapter);
        mDialogFragmentProductFilterBinding.recycleViewSize.addItemDecoration(new HorizontalSpacingDecoration(10));
    }

    @Override
    public void noSizeFilter() {

    }

    @Override
    public void onSelectedColor(boolean isSelected, DataFilter.ColorCode item) {
        if (isSelected)
            mMapSelectedColor.put(item.getColor(), item);
        else {
            if (mMapSelectedColor.containsKey(item.getColor()))
                mMapSelectedColor.remove(item.getColor());
        }
    }

    @Override
    public void onSelectedSize(boolean isSelected, DataFilter.SizeCode item) {
        if (isSelected)
            mMapSelectedSize.put(item.getSize(), item);
        else {
            if (mMapSelectedSize.containsKey(item.getSize()))
                mMapSelectedSize.remove(item.getSize());
        }
    }

    public interface IFilterApplyListener {
        void applyFilter(QProductList.Filter filter);
    }
}

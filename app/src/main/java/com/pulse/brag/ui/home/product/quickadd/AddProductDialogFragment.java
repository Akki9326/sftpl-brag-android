package com.pulse.brag.ui.home.product.quickadd;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.InputFilter;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.adapters.ImagePagerAdapter;
import com.pulse.brag.callback.IOnProductColorSelectListener;
import com.pulse.brag.callback.IOnProductSizeSelectListener;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.datas.DataAddToCart;
import com.pulse.brag.data.model.datas.DataProductList;
import com.pulse.brag.data.model.response.ImagePagerResponse;
import com.pulse.brag.databinding.DialogFragmentAddProductBinding;
import com.pulse.brag.ui.core.CoreDialogFragment;
import com.pulse.brag.ui.home.product.details.adapter.ColorListAdapter;
import com.pulse.brag.ui.home.product.details.adapter.SizeListAdapter;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.views.HorizontalSpacingDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 06-11-2017.
 */


public class AddProductDialogFragment extends CoreDialogFragment<DialogFragmentAddProductBinding, AddProductDialogViewModel> implements AddProductDialogNavigator, IOnProductSizeSelectListener,
        IOnProductColorSelectListener, ImagePagerAdapter.IOnImagePageClickListener/*, BaseInterface */ {

    @Inject
    AddProductDialogViewModel mAddProductDialogViewModel;
    DialogFragmentAddProductBinding mDialogFragmentAddProductBinding;

    ColorListAdapter mColorListAdapter;
    SizeListAdapter mSizeListAdapter;

    DataProductList.Products mProductDetails;
    DataProductList.Size mProduct;

    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            int heightDiff = mDialogFragmentAddProductBinding.baseLayout.getRootView().getHeight() - mDialogFragmentAddProductBinding.baseLayout.getHeight();
            int contentViewTop = getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
            if (heightDiff <= contentViewTop) {

            } else {
                mDialogFragmentAddProductBinding.scrollView.smoothScrollTo(0, mDialogFragmentAddProductBinding.textviewAddCart.getTop() + 205);

            }
        }
    };
    private boolean keyboardListenersAttached = false;

    protected void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }
        //rootLayout = (LinearLayout) mView.findViewById(R.id.base_layout);
        mDialogFragmentAddProductBinding.baseLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);
        keyboardListenersAttached = true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_DialogWhenLarge_NoActionBar);
        mAddProductDialogViewModel.setNavigator(this);
    }


    @Override
    public void onDetach() {
        if (keyboardListenersAttached) {
            mDialogFragmentAddProductBinding.baseLayout.getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
        }
        super.onDetach();

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

//        dialog.getWindow().getAttributes().windowAnimations = R.style.AddProdDialogAnimation;
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }

    @Override
    public void beforeViewCreated() {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_SELETED_PRODUCT)) {
            mProductDetails = getArguments().getParcelable(Constants.BUNDLE_SELETED_PRODUCT);
        }
    }

    @Override
    public void afterViewCreated() {
        mDialogFragmentAddProductBinding = getViewDataBinding();
        Utility.applyTypeFace(getActivity(), (LinearLayout) mDialogFragmentAddProductBinding.baseLayout);

        mDialogFragmentAddProductBinding.recycleViewColor.setHasFixedSize(true);
        mDialogFragmentAddProductBinding.recycleViewColor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mDialogFragmentAddProductBinding.recycleViewSize.setHasFixedSize(true);
        mDialogFragmentAddProductBinding.recycleViewSize.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        attachKeyboardListeners();

        if (mProductDetails != null) {
            List<String> mIntegerList = new ArrayList<>();
            mIntegerList.add("#000000");
            mColorListAdapter = new ColorListAdapter(getActivity(), mIntegerList, 0, this);
            mDialogFragmentAddProductBinding.recycleViewColor.setAdapter(mColorListAdapter);
            mDialogFragmentAddProductBinding.recycleViewColor.addItemDecoration(new HorizontalSpacingDecoration(10));

            List<DataProductList.Size> sizeList = new ArrayList<>();
            int pos = 0;
            int selectedPos = 0;
            for (DataProductList.Size size : mProductDetails.getSizes()) {
                if (size.isIsDefault()) {
                    selectedPos = pos;
                    mProduct = size;
                }
                pos++;
                sizeList.add(size);
            }
            mSizeListAdapter = new SizeListAdapter(getActivity(), sizeList, selectedPos, this);
            mDialogFragmentAddProductBinding.recycleViewSize.setAdapter(mSizeListAdapter);
            mDialogFragmentAddProductBinding.recycleViewSize.addItemDecoration(new HorizontalSpacingDecoration(10));
            showData(mProduct.getDescription(), mProduct.getStockData(), mProduct.getUnitPrice(), mProduct.getImages());
        }
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public AddProductDialogViewModel getViewModel() {
        return mAddProductDialogViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_fragment_add_product;
    }

    public void showData(String description, int stockData, double unitPrice, List<String> bannerImages) {
        //// TODO: 3/12/2018 if data not available than display no data screen

        List<ImagePagerResponse> imagePagerResponeList = new ArrayList<>();
        for (String url : bannerImages) {
            imagePagerResponeList.add(new ImagePagerResponse(url, ""));
        }
        mDialogFragmentAddProductBinding.viewPager.setAdapter(new ImagePagerAdapter(getActivity(), imagePagerResponeList, this));
        mDialogFragmentAddProductBinding.pagerView.setViewPager(mDialogFragmentAddProductBinding.viewPager);

        if (stockData > 0)
            mAddProductDialogViewModel.updateQty(String.valueOf(1));

        mAddProductDialogViewModel.updateProductName(description);
        mAddProductDialogViewModel.updateMaxQty(String.valueOf(stockData));
        mAddProductDialogViewModel.updateNotifyMe(stockData <= 0);
        mDialogFragmentAddProductBinding.edittextQty.setFilters(new InputFilter[]{new InputFilter.LengthFilter(String.valueOf(mProductDetails.getStockData()).length())});
    }

    @Override
    public void onSelectedColor(int pos) {
        mColorListAdapter.setSelectorItem(pos);
    }

    @Override
    public void OnSelectedSize(int prevPos, int pos, DataProductList.Size item) {
        if (prevPos != pos) {
            mSizeListAdapter.setSelectedItem(pos);
            mProduct = item;
            showData(item.getDescription(), item.getStockData(), item.getUnitPrice(), item.getImages());
        }
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
    public void editQty() {
        mDialogFragmentAddProductBinding.scrollView.smoothScrollTo(0, mDialogFragmentAddProductBinding.textviewAddCart.getTop() + 205);
    }

    @Override
    public void addToCart() {
        if (Utility.isConnection(getActivity())) {
            mAddProductDialogViewModel.addToCart(mProduct.getNo(), Integer.parseInt(mDialogFragmentAddProductBinding.edittextQty.getText().toString()));
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null,null);
        }

    }

    @Override
    public void onAddedToCart(List<DataAddToCart> data) {
        ((MainActivity) getBaseActivity()).addToCartAPI(data.size());
        dismissDialog("");
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s != null) {
            if (s.toString().trim().length() == 0 || Integer.valueOf(s.toString()) == 0) {
                mDialogFragmentAddProductBinding.textviewMax.setTextColor(getResources().getColor(R.color.text_black));
                mDialogFragmentAddProductBinding.textviewAddCart.setTextColor(getResources().getColor(R.color.gray_transparent));
                mDialogFragmentAddProductBinding.textviewAddCart.setEnabled(false);
            } else if (Integer.valueOf(s.toString()) > mProduct.getStockData()) {
                mDialogFragmentAddProductBinding.textviewMax.setTextColor(Color.RED);
                mDialogFragmentAddProductBinding.textviewAddCart.setTextColor(getResources().getColor(R.color.gray_transparent));
                mDialogFragmentAddProductBinding.textviewAddCart.setEnabled(false);
            } else {
                mDialogFragmentAddProductBinding.textviewMax.setTextColor(getResources().getColor(R.color.text_black));
                mDialogFragmentAddProductBinding.textviewAddCart.setTextColor(getResources().getColor(R.color.text_black));
                mDialogFragmentAddProductBinding.textviewAddCart.setEnabled(true);
            }
        }
    }

    @Override
    public void notifyMe() {
        if (Utility.isConnection(getActivity())) {
            mAddProductDialogViewModel.notifyMe(mProduct.getNo());
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null,null);

        }
    }

    @Override
    public void onNotifyMeSuccess(String msg) {
        AlertUtils.showAlertMessage(getBaseActivity(), msg);
    }

    @Override
    public void onImagePageClick(int pos, ImagePagerResponse item) {

    }
}

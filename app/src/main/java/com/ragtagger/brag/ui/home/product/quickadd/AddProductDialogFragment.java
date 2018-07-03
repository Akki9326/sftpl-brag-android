package com.ragtagger.brag.ui.home.product.quickadd;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.InputFilter;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ragtagger.brag.BR;
import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.R;
import com.ragtagger.brag.adapters.ImagePagerAdapter;
import com.ragtagger.brag.callback.IOnProductColorSelectListener;
import com.ragtagger.brag.callback.IOnProductSizeSelectListener;
import com.ragtagger.brag.data.model.ApiError;
import com.ragtagger.brag.data.model.datas.DataAddToCart;
import com.ragtagger.brag.data.model.datas.DataProductList;
import com.ragtagger.brag.data.model.response.RImagePager;
import com.ragtagger.brag.databinding.DialogFragmentAddProductBinding;
import com.ragtagger.brag.ui.core.CoreDialogFragment;
import com.ragtagger.brag.ui.home.product.details.adapter.ColorListAdapter;
import com.ragtagger.brag.ui.home.product.details.adapter.SizeListAdapter;
import com.ragtagger.brag.ui.main.MainActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.views.HorizontalSpacingDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nikhil.vadoliya on 06-11-2017.
 */


public class AddProductDialogFragment extends CoreDialogFragment<DialogFragmentAddProductBinding, AddProductDialogViewModel> implements AddProductDialogNavigator, IOnProductSizeSelectListener,
        IOnProductColorSelectListener, ImagePagerAdapter.IOnImagePageClickListener {

    @Inject
    AddProductDialogViewModel mAddProductDialogViewModel;
    DialogFragmentAddProductBinding mDialogFragmentAddProductBinding;

    ColorListAdapter mColorListAdapter;

    DataProductList mProductData;
    List<DataProductList.Products> mProductList;
    DataProductList.Size mSizedProduct;
    int mSelectedColorPosition = 0;
    String mSizeGuide;
    boolean isDefaultAdded = false;

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
            mProductData = null;
            mProductData = getArguments().getParcelable(Constants.BUNDLE_SELETED_PRODUCT);
        }

        if (getArguments().containsKey(Constants.BUNDLE_PRODUCT_LISt)) {
            mProductList = getArguments().getParcelableArrayList(Constants.BUNDLE_PRODUCT_LISt);
        }

        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_POSITION)) {
            mSelectedColorPosition = getArguments().getInt(Constants.BUNDLE_POSITION);
        }

        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_SIZE_GUIDE)) {
            mSizeGuide = getArguments().getString(Constants.BUNDLE_SIZE_GUIDE);
        }


    }

    @Override
    public void afterViewCreated() {
        mDialogFragmentAddProductBinding = getViewDataBinding();
        Utility.applyTypeFace(getActivity(), (LinearLayout) mDialogFragmentAddProductBinding.baseLayout);

        mDialogFragmentAddProductBinding.recycleViewColor.setHasFixedSize(true);
        mDialogFragmentAddProductBinding.recycleViewColor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mDialogFragmentAddProductBinding.recycleViewColor.addItemDecoration(new HorizontalSpacingDecoration(10));

        mDialogFragmentAddProductBinding.recycleViewSize.setHasFixedSize(true);
        mDialogFragmentAddProductBinding.recycleViewSize.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mDialogFragmentAddProductBinding.recycleViewSize.addItemDecoration(new HorizontalSpacingDecoration(10));


        attachKeyboardListeners();

        if (mProductData != null) {
            List<DataProductList.Products> colorList = new ArrayList<>();
            if (mProductList != null && mProductList.size() > 0)
                colorList.addAll(mProductList);

            if (mColorListAdapter != null) {
                mColorListAdapter.reset(colorList, mSelectedColorPosition);
            } else {
                mColorListAdapter = new ColorListAdapter(getActivity(), colorList, mSelectedColorPosition, this);
                mDialogFragmentAddProductBinding.recycleViewColor.setAdapter(mColorListAdapter);
                mDialogFragmentAddProductBinding.recycleViewColor.addItemDecoration(new HorizontalSpacingDecoration(10));
                mDialogFragmentAddProductBinding.recycleViewColor.scrollToPosition(mSelectedColorPosition);
            }

            onColorProductSelected(mProductList.get(mSelectedColorPosition).getSizes(), mProductList.get(mSelectedColorPosition).getItemCategoryCode());
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

    private void onColorProductSelected(List<DataProductList.Size> sizes, String category) {
        mSizeGuide = BragApp.getInstance().getSizeGuide(category);
        mAddProductDialogViewModel.updateSizeGuide(mSizeGuide != null);

        int pos = 0;
        int selectedPos = 0;
        for (DataProductList.Size size : sizes) {
            if (size.isIsDefault()) {
                selectedPos = pos;
                mSizedProduct = size;
            }
            pos++;
        }

        mDialogFragmentAddProductBinding.recycleViewSize.setAdapter(null);
        SizeListAdapter mSizeListAdapter = new SizeListAdapter(getActivity(), sizes, selectedPos, this);
        mDialogFragmentAddProductBinding.recycleViewSize.setAdapter(mSizeListAdapter);
        mDialogFragmentAddProductBinding.recycleViewSize.scrollToPosition(selectedPos);
        showData();
    }

    public void showData() {
        //// TODO: 3/12/2018 if data not available than display no data screen

        if (mSizedProduct != null) {
            isDefaultAdded = mSizedProduct.isIsDefault();
            List<RImagePager> imagePagerResponeList = new ArrayList<>();
            for (String url : mSizedProduct.getImages()) {
                imagePagerResponeList.add(new RImagePager(url, ""));
            }
            mDialogFragmentAddProductBinding.viewPager.setAdapter(new ImagePagerAdapter(getActivity(), imagePagerResponeList, this));
            mDialogFragmentAddProductBinding.pagerView.setViewPager(mDialogFragmentAddProductBinding.viewPager);

            mAddProductDialogViewModel.updateQty("");
            mDialogFragmentAddProductBinding.textviewMax.setTextColor(getResources().getColor(R.color.text_black));
            mDialogFragmentAddProductBinding.textviewAddCart.setTextColor(getResources().getColor(R.color.gray_transparent));
            mDialogFragmentAddProductBinding.textviewAddCart.setEnabled(false);
            if (mSizedProduct.getStockData() > 0) {
                mAddProductDialogViewModel.updateQty(String.valueOf(1));
                mDialogFragmentAddProductBinding.textviewMax.setTextColor(getResources().getColor(R.color.text_black));
                mDialogFragmentAddProductBinding.textviewAddCart.setTextColor(getResources().getColor(R.color.text_black));
                mDialogFragmentAddProductBinding.textviewAddCart.setEnabled(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialogFragmentAddProductBinding.edittextQty.setSelection(1);
                    }
                }, 100);
            }

            mAddProductDialogViewModel.updateProductName(mSizedProduct.getDescription());
            mAddProductDialogViewModel.updateMaxQty(String.valueOf(mSizedProduct.getStockData()));
            mAddProductDialogViewModel.updateNotifyMe(mSizedProduct.getStockData() <= 0);


            mAddProductDialogViewModel.updateProPrice(Utility.getIndianCurrencyPriceFormat(mSizedProduct.getUnitPrice()));
            mDialogFragmentAddProductBinding.edittextQty.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        }
    }

    @Override
    public void onSelectedColor(int prevPos, int pos, List<DataProductList.Size> sizes) {
        if (prevPos != pos) {
            mSelectedColorPosition = pos;
            mColorListAdapter.setSelectedItem(pos);
            onColorProductSelected(sizes, mColorListAdapter.getItem(pos).getItemCategoryCode());
        }
    }

    @Override
    public void OnSelectedSize(int prevPos, int pos, DataProductList.Size item) {
        mSizedProduct = item;
        showData();
    }


    @Override
    public void onApiSuccess() {
        hideProgress();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
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
            mAddProductDialogViewModel.addToCart(mSizedProduct.getNo(), Integer.parseInt(mDialogFragmentAddProductBinding.edittextQty.getText().toString()));
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null, null);
        }

    }

    @Override
    public void onAddedToCart(List<DataAddToCart> data) {
        if (isDefaultAdded) {
            Intent intent = new Intent(Constants.ACTION_UPDATE_CART_ICON_STATE);
            intent.putExtra(Constants.BUNDLE_POSITION, mSelectedColorPosition);
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        }
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getBaseActivity()).updateCartNum();

        if (isAdded())
            ((MainActivity) getBaseActivity()).showToast(getString(R.string.label_item_added_to_cart));
        dismissDialog("");
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s != null) {
            if (s.toString().trim().length() == 0 || Integer.valueOf(s.toString()) == 0) {
                mDialogFragmentAddProductBinding.textviewMax.setTextColor(getResources().getColor(R.color.text_black));
                mDialogFragmentAddProductBinding.textviewAddCart.setTextColor(getResources().getColor(R.color.gray_transparent));
                mDialogFragmentAddProductBinding.textviewAddCart.setEnabled(false);
            } else if (Integer.valueOf(s.toString()) > mSizedProduct.getStockData()) {
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
            mAddProductDialogViewModel.notifyMe(mSizedProduct.getNo());
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null, null);

        }
    }

    @Override
    public void onNotifyMeSuccess(String msg) {
        AlertUtils.showAlertMessage(getBaseActivity(), msg);
    }

    @Override
    public void onImagePageClick(int pos, RImagePager item) {

    }
}

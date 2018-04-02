package com.ragtagger.brag.ui.home.product.details;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.ragtagger.brag.databinding.FragmentProductDetailBinding;
import com.ragtagger.brag.ui.core.CoreActivity;
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.home.product.details.adapter.ColorListAdapter;
import com.ragtagger.brag.ui.home.product.details.adapter.SizeListAdapter;
import com.ragtagger.brag.ui.main.MainActivity;
import com.ragtagger.brag.ui.notification.handler.NotificationHandlerActivity;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.views.FullScreenImageDialogFragment;
import com.ragtagger.brag.views.HorizontalSpacingDecoration;
import com.ragtagger.brag.views.keyboardvisibilityevent.KeyboardVisibilityEvent;
import com.ragtagger.brag.views.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import com.ragtagger.brag.views.webview.WebviewDialogFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by nikhil.vadoliya on 04-10-2017.
 */


public class ProductDetailFragment extends CoreFragment<FragmentProductDetailBinding, ProductDetailViewModel> implements /*IOnItemClickListener,*/ IOnProductSizeSelectListener, IOnProductColorSelectListener, ProductDetailNavigator, ImagePagerAdapter.IOnImagePageClickListener /*BaseInterface,*/ {

    @Inject
    ProductDetailViewModel mProductDetailViewModel;
    @Inject
    @Named("forColor")
    LinearLayoutManager mColorLayoutManager;

    @Inject
    @Named("forSize")
    LinearLayoutManager mSizeLayoutManager;
    FragmentProductDetailBinding mFragmentProductDetailBinding;

    ColorListAdapter mColorListAdapter;

    DataProductList mProductData;
    List<DataProductList.Products> mProductList;
    DataProductList.Size mSizedProduct;
    int mSelectedColorPosition = 0;

    List<RImagePager> imagePagerResponeList;
    int mQuality;
    String mSizeGuide;
    boolean isDefaultAdded = false;
    String mItemId = null;
    Boolean isFromNotification;
    boolean resize;
    boolean isKeyboardOpen = false;

    private KeyboardVisibilityEventListener mKeyboardEventListener = new KeyboardVisibilityEventListener() {
        @Override
        public void onVisibilityChanged(boolean isOpen) {
            isKeyboardOpen = isOpen;
            if (isOpen) {
                FrameLayout.LayoutParams relativeParams = (FrameLayout.LayoutParams) mFragmentProductDetailBinding.baseLayout.getLayoutParams();
                relativeParams.setMargins(0, -50, 0, 0);  // left, top, right, bottom
                mFragmentProductDetailBinding.baseLayout.setLayoutParams(relativeParams);
                mFragmentProductDetailBinding.textViewQty.setCursorVisible(true);

            } else {
                mFragmentProductDetailBinding.textViewQty.setCursorVisible(false);
            }
            updateQtyCursor();
        }
    };


    public static ProductDetailFragment newInstance(DataProductList dataRespone, List<DataProductList.Products> list, int position, String sizeGuide, boolean isFromNotification/*, ProductListFragment targetFragment, int reqCode*/) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.BUNDLE_SELETED_PRODUCT, dataRespone);
        args.putInt(Constants.BUNDLE_POSITION, position);
        args.putString(Constants.BUNDLE_SIZE_GUIDE, sizeGuide);
        args.putBoolean(Constants.BUNDLE_IS_FROM_NOTIFICATION, isFromNotification);
        args.putParcelableArrayList(Constants.BUNDLE_PRODUCT_LISt, (ArrayList<? extends Parcelable>) list);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductDetailFragment newInstance(String itemId, boolean isFromNotification, boolean resize) {
        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_ITEM_ID, itemId);
        args.putBoolean(Constants.BUNDLE_IS_FROM_NOTIFICATION, isFromNotification);
        args.putBoolean(Constants.BUNDLE_IS_RESIZE_NOTIFICATION, resize);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mProductDetailViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
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

        resize = false;
        if (getArguments() != null) {
            if (getArguments().containsKey(Constants.BUNDLE_ITEM_ID)) {
                mItemId = getArguments().getString(Constants.BUNDLE_ITEM_ID);
            }
            if (getArguments().containsKey(Constants.BUNDLE_IS_FROM_NOTIFICATION)) {
                isFromNotification = getArguments().getBoolean(Constants.BUNDLE_IS_FROM_NOTIFICATION);
            }
            if (getArguments().containsKey(Constants.BUNDLE_IS_RESIZE_NOTIFICATION)) {
                resize = getArguments().getBoolean(Constants.BUNDLE_IS_RESIZE_NOTIFICATION);
            }
        }
        imagePagerResponeList = new ArrayList<>();
    }

    @Override
    public void afterViewCreated() {
        mFragmentProductDetailBinding = getViewDataBinding();
        Utility.applyTypeFace(getContext(), mFragmentProductDetailBinding.baseLayout);

        mProductDetailViewModel.updateIsLoading(true);
        mFragmentProductDetailBinding.recycleViewColor.setHasFixedSize(true);
        mFragmentProductDetailBinding.recycleViewColor.setLayoutManager(mColorLayoutManager);
        mFragmentProductDetailBinding.recycleViewSize.setHasFixedSize(true);
        mFragmentProductDetailBinding.recycleViewSize.setLayoutManager(mSizeLayoutManager);
        mFragmentProductDetailBinding.recycleViewSize.addItemDecoration(new HorizontalSpacingDecoration(10));

        //Is from Notification
        if (isFromNotification) {
            if (resize) {
                FrameLayout.LayoutParams relativeParams = (FrameLayout.LayoutParams) mFragmentProductDetailBinding.baseLayout.getLayoutParams();
                relativeParams.setMargins(0, -25, 0, 0);  // left, top, right, bottom
                mFragmentProductDetailBinding.baseLayout.setLayoutParams(relativeParams);

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mFragmentProductDetailBinding.llScroll.getLayoutParams();
                params.setMargins(0, 0, 0, 0);
                mFragmentProductDetailBinding.llScroll.setLayoutParams(params);
            }

            checkInternet();
        } else {
            if (mProductData != null) {
                List<DataProductList.Products> colorList = new ArrayList<>();
                if (mProductList != null && mProductList.size() > 0)
                    colorList.addAll(mProductList);

                if (mColorListAdapter != null) {
                    mColorListAdapter.reset(colorList, mSelectedColorPosition);
                } else {
                    mColorListAdapter = new ColorListAdapter(getActivity(), colorList, mSelectedColorPosition, this);
                    mFragmentProductDetailBinding.recycleViewColor.setAdapter(mColorListAdapter);
                    mFragmentProductDetailBinding.recycleViewColor.addItemDecoration(new HorizontalSpacingDecoration(10));
                    mFragmentProductDetailBinding.recycleViewColor.scrollToPosition(mSelectedColorPosition);
                    //mFragmentProductDetailBinding.recycleViewColor.smoothScrollToPosition(mSelectedColorPosition);
                }

                onColorProductSelected(mProductList.get(mSelectedColorPosition).getSizes(), mProductList.get(mSelectedColorPosition).getItemCategoryCode());
            }
        }

        KeyboardVisibilityEvent.setEventListener(
                getActivity(), mKeyboardEventListener);
    }

    @Override
    public void setUpToolbar() {
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).showToolbar(true, false, true, getString(R.string.toolbar_label_product_detail));
        else if (getActivity() instanceof NotificationHandlerActivity)
            ((NotificationHandlerActivity) getActivity()).showPushToolbar(true, true, getString(R.string.toolbar_label_product_detail));
    }

    @Override
    public ProductDetailViewModel getViewModel() {
        return mProductDetailViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_detail;
    }


    private void onColorProductSelected(List<DataProductList.Size> sizes, String category) {
        mSizeGuide = BragApp.getInstance().getSizeGuide(category);
        mProductDetailViewModel.updateSizeGuide(mSizeGuide != null);
        int pos = 0;
        int selectedPos = 0;
        for (DataProductList.Size size : sizes) {
            if (size.isIsDefault()) {
                selectedPos = pos;
                mSizedProduct = size;
            }
            pos++;
        }

        mFragmentProductDetailBinding.recycleViewSize.setAdapter(null);
        SizeListAdapter mSizeListAdapter = new SizeListAdapter(getActivity(), sizes, selectedPos, this);
        mFragmentProductDetailBinding.recycleViewSize.setAdapter(mSizeListAdapter);
        mFragmentProductDetailBinding.recycleViewSize.scrollToPosition(selectedPos);

        showData();
    }

    public void checkInternet() {
        if (Utility.isConnection(getActivity())) {
            mProductDetailViewModel.getProductDetail(mItemId);
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null, null);
        }
    }

    private void updateQtyCursor() {
        if (isKeyboardOpen)
            if (mFragmentProductDetailBinding.textViewQty.getText().toString().length() > 0) {
                mFragmentProductDetailBinding.textViewQty.setSelection(mFragmentProductDetailBinding.textViewQty.getText().toString().length());
            } else {
                mFragmentProductDetailBinding.textViewQty.setSelection(0);
            }
    }


    public void showData() {

        //// TODO: 3/12/2018 if data not available than display no data screen
        mProductDetailViewModel.updateIsLoading(false);
        if (mSizedProduct != null) {

            isDefaultAdded = mSizedProduct.isIsDefault();
            imagePagerResponeList.clear();
            for (String url : mSizedProduct.getImages()) {
                imagePagerResponeList.add(new RImagePager(url, ""));
            }
            mFragmentProductDetailBinding.viewPager.setAdapter(new ImagePagerAdapter(getActivity(), imagePagerResponeList, this));
            mFragmentProductDetailBinding.pagerIndicator.setViewPager(mFragmentProductDetailBinding.viewPager);

            mFragmentProductDetailBinding.textViewQty.setText("");
            mFragmentProductDetailBinding.textViewAvailQty.setTextColor(getResources().getColor(R.color.gray_transparent));
            mFragmentProductDetailBinding.textviewAddCart.setTextColor(getResources().getColor(R.color.disabled_gray));
            mFragmentProductDetailBinding.textviewAddCart.setEnabled(false);
            updateQtyCursor();
            if (mSizedProduct.getStockData() > 0) {
                mQuality = 1;
                mFragmentProductDetailBinding.textViewQty.setText(String.valueOf(1));
                mFragmentProductDetailBinding.textViewAvailQty.setTextColor(getResources().getColor(R.color.gray_transparent));
                mFragmentProductDetailBinding.textviewAddCart.setTextColor(getResources().getColor(R.color.white));
                mFragmentProductDetailBinding.textviewAddCart.setEnabled(true);
                updateQtyCursor();
            }

            mProductDetailViewModel.updateProductProDetail(mSizedProduct.getDescription());
            mProductDetailViewModel.updateProductProShortDetail(mSizedProduct.getDescription2Modified());
            mProductDetailViewModel.updateNotifyMe(mSizedProduct.getStockData() <= 0);
            mProductDetailViewModel.updateProductProPrice(Utility.getIndianCurrencyPriceFormat(mSizedProduct.getUnitPrice()));
            mProductDetailViewModel.updateMaxQty(String.valueOf(mSizedProduct.getStockData()));
        }
    }


    @Override
    public void OnSelectedSize(int prevPos, int pos, DataProductList.Size item) {
        mSizedProduct = item;
        showData();
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
    public void onApiSuccess() {
        hideProgress();
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);

    }

    @Override
    public void sizeGuide() {
//        pushSizeGuideFragment();
        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_IMAGE_URL, mSizeGuide);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        DialogFragment mDialogFragmentImage = new FullScreenImageDialogFragment();
        mDialogFragmentImage.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
        mDialogFragmentImage.setArguments(args);
        mDialogFragmentImage.show(fm, "");
    }

    @Override
    public void plus() {
        if (mFragmentProductDetailBinding.textViewQty.getText().toString().length() > 0 && Integer.parseInt(mFragmentProductDetailBinding.textViewQty.getText().toString()) < mSizedProduct.getStockData()) {//if (mQuality < mSizedProduct.getStockData()) {
            mQuality = Integer.parseInt(mFragmentProductDetailBinding.textViewQty.getText().toString());
            mQuality++;
            mFragmentProductDetailBinding.textViewQty.setText(String.valueOf(mQuality));
        } else if (mFragmentProductDetailBinding.textViewQty.getText().toString().length() == 0) {
            mQuality = 1;
            mFragmentProductDetailBinding.textViewQty.setText(String.valueOf(1));
        } else {
            ((CoreActivity) getActivity()).showToast(getString(R.string.error_no_more_quantity));
        }
        updateQtyCursor();

    }

    @Override
    public void minus() {
        if (mFragmentProductDetailBinding.textViewQty.getText().toString().length() > 0 && Integer.parseInt(mFragmentProductDetailBinding.textViewQty.getText().toString()) == 1) {
            return;
        } else if (mFragmentProductDetailBinding.textViewQty.getText().toString().length() > 0) {
            mQuality = Integer.parseInt(mFragmentProductDetailBinding.textViewQty.getText().toString());
            mQuality--;
            mFragmentProductDetailBinding.textViewQty.setText(String.valueOf(mQuality));
            updateQtyCursor();
        }
    }

    @Override
    public void onEditTextQty() {


    }

    @Override
    public void addToCart() {
        if (Utility.isConnection(getActivity())) {
            mProductDetailViewModel.addToCart(mSizedProduct.getNo(), Integer.parseInt(mFragmentProductDetailBinding.textViewQty.getText().toString()));
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null, null);
        }
    }

    @Override
    public void onAddedToCart(List<DataAddToCart> data) {
        if (getActivity() instanceof MainActivity) {
            if (isDefaultAdded) {
                Intent intent = new Intent(Constants.ACTION_UPDATE_CART_ICON_STATE);
                intent.putExtra(Constants.BUNDLE_POSITION, mSelectedColorPosition);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
            ((MainActivity) getActivity()).updateCartNum();
        } else if (getActivity() instanceof NotificationHandlerActivity) {
            ((NotificationHandlerActivity) getActivity()).updateCartNum();
        }
        ((CoreActivity) getActivity()).showToast(getString(R.string.label_item_added_to_cart));
    }

    @Override
    public void notifyMe() {
        if (Utility.isConnection(getActivity())) {
            mProductDetailViewModel.notifyMe(mSizedProduct.getNo());
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null, null);
        }
    }

    @Override
    public void onNotifyMeSuccess(String msg) {
        AlertUtils.showAlertMessage(getBaseActivity(), msg);
    }

    @Override
    public void onApiSuccessProductDetail(DataProductList.Products products) {
        hideProgress();
        if (products != null) {
            List<DataProductList.Products> colorList = new ArrayList<>();

            //color list
            colorList.add(products);
            if (mColorListAdapter != null) {
                mColorListAdapter.reset(colorList, mSelectedColorPosition);
            } else {
                mColorListAdapter = new ColorListAdapter(getActivity(), colorList, mSelectedColorPosition, this);
                mFragmentProductDetailBinding.recycleViewColor.setAdapter(mColorListAdapter);
                mFragmentProductDetailBinding.recycleViewColor.addItemDecoration(new HorizontalSpacingDecoration(10));
                mFragmentProductDetailBinding.recycleViewColor.scrollToPosition(mSelectedColorPosition);
                //mFragmentProductDetailBinding.recycleViewColor.smoothScrollToPosition(mSelectedColorPosition);
            }

            //size list
            /*List<DataProductList.Size> mSizes = new ArrayList<>();
            mSizes.add(new DataProductList.Size(products.getNo(), products.getDescription(), products.getDescription2()
                    , products.getSizeCode(), products.getUnitOfMeasure(), products.getUnitPrice(), products.getStockData()
                    , true, products.getImages()));*/
            onColorProductSelected(products.getSizes(), products.getItemCategoryCode());
        }


    }


    @Override
    public void onApiErrorProductDetail(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage(), null);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s != null) {
            if (s.toString().trim().length() == 0 || Integer.valueOf(s.toString()) == 0) {
                mFragmentProductDetailBinding.textViewAvailQty.setTextColor(getResources().getColor(R.color.gray_transparent));
                mFragmentProductDetailBinding.textviewAddCart.setTextColor(getResources().getColor(R.color.disabled_gray));
                mFragmentProductDetailBinding.textviewAddCart.setEnabled(false);
            } else if (Integer.valueOf(s.toString()) > mSizedProduct.getStockData()) {
                mFragmentProductDetailBinding.textViewAvailQty.setTextColor(Color.RED);
                mFragmentProductDetailBinding.textviewAddCart.setTextColor(getResources().getColor(R.color.disabled_gray));
                mFragmentProductDetailBinding.textviewAddCart.setEnabled(false);
            } else {
                mFragmentProductDetailBinding.textViewAvailQty.setTextColor(getResources().getColor(R.color.gray_transparent));
                mFragmentProductDetailBinding.textviewAddCart.setTextColor(getResources().getColor(R.color.white));
                mFragmentProductDetailBinding.textviewAddCart.setEnabled(true);
            }
        }
    }

    @Override
    public void updatePushCart() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).updateCartNum();
        } else if (getActivity() instanceof NotificationHandlerActivity) {
            ((NotificationHandlerActivity) getActivity()).updateCartNum();
        }
    }

    @Override
    public boolean onEditorActionHide(TextView textView, int i, KeyEvent keyEvent) {
        Utility.hideSoftkeyboard(getActivity());
        return false;
    }

    @Override
    public void onImagePageClick(int pos, RImagePager item) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.BUNDLE_IMAGE_LIST, (ArrayList<? extends Parcelable>) imagePagerResponeList);
        args.putInt(Constants.BUNDLE_POSITION, pos);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        DialogFragment mDialogFragmentImage = new FullScreenImageDialogFragment();
        mDialogFragmentImage.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
        mDialogFragmentImage.setArguments(args);
        mDialogFragmentImage.show(fm, "");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            setUpToolbar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}

package com.ragtagger.brag.ui.home.product.details;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

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
import com.ragtagger.brag.ui.core.CoreFragment;
import com.ragtagger.brag.ui.home.product.details.adapter.ColorListAdapter;
import com.ragtagger.brag.ui.home.product.details.adapter.SizeListAdapter;
import com.ragtagger.brag.ui.main.MainActivity;
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


    public static ProductDetailFragment newInstance(DataProductList dataRespone, List<DataProductList.Products> list, int position, String sizeGuide, boolean isFromNotification/*, ProductListFragment targetFragment, int reqCode*/) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.BUNDLE_SELETED_PRODUCT, dataRespone);
        args.putInt(Constants.BUNDLE_POSITION, position);
        args.putString(Constants.BUNDLE_SIZE_GUIDE, sizeGuide);
        args.putBoolean(Constants.BUNDLE_IS_FROM_NOTIFICATION, isFromNotification);
        args.putParcelableArrayList(Constants.BUNDLE_PRODUCT_LISt, (ArrayList<? extends Parcelable>) list);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        //fragment.setTargetFragment(targetFragment, reqCode);
        return fragment;
    }

    public static ProductDetailFragment newInstance(String itemId, boolean isFromNotification) {
        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_ITEM_ID, itemId);
        args.putBoolean(Constants.BUNDLE_IS_FROM_NOTIFICATION, isFromNotification);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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

        if (getArguments() != null) {
            if (getArguments().containsKey(Constants.BUNDLE_ITEM_ID)) {
                mItemId = getArguments().getString(Constants.BUNDLE_ITEM_ID);
            }
            if (getArguments().containsKey(Constants.BUNDLE_IS_FROM_NOTIFICATION)) {
                isFromNotification = getArguments().getBoolean(Constants.BUNDLE_IS_FROM_NOTIFICATION);
            }
        }
        imagePagerResponeList = new ArrayList<>();
    }

    @Override
    public void afterViewCreated() {
        mFragmentProductDetailBinding = getViewDataBinding();
        Utility.applyTypeFace(getContext(), mFragmentProductDetailBinding.baseLayout);


        mFragmentProductDetailBinding.recycleViewColor.setHasFixedSize(true);
        mFragmentProductDetailBinding.recycleViewColor.setLayoutManager(mColorLayoutManager);
        mFragmentProductDetailBinding.recycleViewSize.setHasFixedSize(true);
        mFragmentProductDetailBinding.recycleViewSize.setLayoutManager(mSizeLayoutManager);
        mFragmentProductDetailBinding.recycleViewSize.addItemDecoration(new HorizontalSpacingDecoration(10));

        //Is from Notification
        if (isFromNotification) {
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
                }

                onColorProductSelected(mProductList.get(mSelectedColorPosition).getSizes(), mProductList.get(mSelectedColorPosition).getItemCategoryCode());
            }
        }

        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            FrameLayout.LayoutParams relativeParams = (FrameLayout.LayoutParams) mFragmentProductDetailBinding.baseLayout.getLayoutParams();
                            relativeParams.setMargins(0, -50, 0, 0);  // left, top, right, bottom
                            mFragmentProductDetailBinding.baseLayout.setLayoutParams(relativeParams);
//                            mFragmentProductDetailBinding.dummay.setMinimumHeight(KeyboardVisibilityEvent.getKeyBoardHeight() * 2);
                        } else {
//                            mFragmentProductDetailBinding.dummay.setMinimumHeight(0);
                        }
                    }
                });
    }

    @Override
    public void setUpToolbar() {
        ((MainActivity) getActivity()).showToolbar(true, false, true, getString(R.string.toolbar_label_product_detail));
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

        showData();
    }

    public void checkInternet() {
        if (Utility.isConnection(getActivity())) {
            mProductDetailViewModel.getProductDetail(mItemId);
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null, null);
        }
    }


    public void showData() {

        //// TODO: 3/12/2018 if data not available than display no data screen

        if (mSizedProduct != null) {

            isDefaultAdded = mSizedProduct.isIsDefault();
            if (mSizedProduct.getStockData() > 0) {
                mProductDetailViewModel.updateQty(String.valueOf(1));
                mQuality = 1;
            }
            mProductDetailViewModel.updateProductProDetail(mSizedProduct.getDescription());
            mProductDetailViewModel.updateProductProShortDetail(mSizedProduct.getDescription2());
            mProductDetailViewModel.updateNotifyMe(mSizedProduct.getStockData() <= 0);
            mProductDetailViewModel.updateProductProPrice(Utility.getIndianCurrencyPriceFormat(mSizedProduct.getUnitPrice()));
            mProductDetailViewModel.updateMaxQty(String.valueOf(mSizedProduct.getStockData()));

            imagePagerResponeList.clear();
            for (String url : mSizedProduct.getImages()) {
                imagePagerResponeList.add(new RImagePager(url, ""));
            }
            mFragmentProductDetailBinding.viewPager.setAdapter(new ImagePagerAdapter(getActivity(), imagePagerResponeList, this));
            mFragmentProductDetailBinding.pagerIndicator.setViewPager(mFragmentProductDetailBinding.viewPager);
        }
    }

    private void pushSizeGuideFragment() {
        // TODO: 2/20/2018 Change url to size guide
        Bundle bundle;
        WebviewDialogFragment dialogFragment;
        bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_TITLE, "Size Guide");
        bundle.putString(Constants.BUNDLE_SUBTITLE, mSizeGuide);
        dialogFragment = new WebviewDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getChildFragmentManager(), "");
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
        if (mQuality < mSizedProduct.getStockData()) {
            mQuality = Integer.parseInt(mFragmentProductDetailBinding.textViewQty.getText().toString());
            mQuality++;
            mProductDetailViewModel.updateQty(String.valueOf(mQuality));
        } else {
            ((MainActivity) getActivity()).showToast(getString(R.string.error_no_more_quantity));
        }
    }

    @Override
    public void minus() {
        if (mQuality == 1) {
            return;
        }

        mQuality = Integer.parseInt(mFragmentProductDetailBinding.textViewQty.getText().toString());
        mQuality--;
        mProductDetailViewModel.updateQty(String.valueOf(mQuality));
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
        if (isDefaultAdded) {
            Intent intent = new Intent(Constants.ACTION_UPDATE_CART_ICON_STATE);
            intent.putExtra(Constants.BUNDLE_POSITION, mSelectedColorPosition);
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        }
        ((MainActivity) getBaseActivity()).updateCartNum();
        ((MainActivity) getBaseActivity()).showToast(getString(R.string.label_item_added_to_cart));
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
            colorList.add(products);

            if (mColorListAdapter != null) {
                mColorListAdapter.reset(colorList, mSelectedColorPosition);
            } else {
                mColorListAdapter = new ColorListAdapter(getActivity(), colorList, mSelectedColorPosition, this);
                mFragmentProductDetailBinding.recycleViewColor.setAdapter(mColorListAdapter);
                mFragmentProductDetailBinding.recycleViewColor.addItemDecoration(new HorizontalSpacingDecoration(10));
            }
            List<DataProductList.Size> mSizes = new ArrayList<>();
            mSizes.add(new DataProductList.Size(products.getNo(), products.getDescription(), products.getDescription2()
                    , products.getSizeCode(), products.getUnitOfMeasure(), products.getUnitPrice(), products.getStockData()
                    , true, products.getImages()));
            onColorProductSelected(mSizes, products.getItemCategoryCode());
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

            } else if (Integer.valueOf(s.toString()) > mSizedProduct.getStockData()) {

            } else {

            }
        }
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

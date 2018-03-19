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


    public static ProductDetailFragment newInstance(DataProductList dataRespone, List<DataProductList.Products> list, int position, String sizeGuide/*, ProductListFragment targetFragment, int reqCode*/) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.BUNDLE_SELETED_PRODUCT, dataRespone);
        args.putInt(Constants.BUNDLE_POSITION, position);
        args.putString(Constants.BUNDLE_SIZE_GUIDE, sizeGuide);
        args.putParcelableArrayList(Constants.BUNDLE_PRODUCT_LISt, (ArrayList<? extends Parcelable>) list);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        //fragment.setTargetFragment(targetFragment, reqCode);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        imagePagerResponeList = new ArrayList<>();
    }

    @Override
    public void afterViewCreated() {
        mFragmentProductDetailBinding = getViewDataBinding();
        Utility.applyTypeFace(getContext(), (RelativeLayout) mFragmentProductDetailBinding.baseLayout);


        mFragmentProductDetailBinding.recycleViewColor.setHasFixedSize(true);
        mFragmentProductDetailBinding.recycleViewColor.setLayoutManager(mColorLayoutManager);
        mFragmentProductDetailBinding.recycleViewSize.setHasFixedSize(true);
        mFragmentProductDetailBinding.recycleViewSize.setLayoutManager(mSizeLayoutManager);
        mFragmentProductDetailBinding.recycleViewSize.addItemDecoration(new HorizontalSpacingDecoration(10));

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


    public void showData() {

        //// TODO: 3/12/2018 if data not available than display no data screen

        if (mSizedProduct != null) {


            if (mSizedProduct.getStockData() > 0) {
                mProductDetailViewModel.updateQty(String.valueOf(1));
                mQuality = 1;
            }
            mProductDetailViewModel.updateProductProDetail(mSizedProduct.getDescription());
            mProductDetailViewModel.updateProductProShortDetail(mSizedProduct.getDescription2());
            mProductDetailViewModel.updateNotifyMe(mSizedProduct.getStockData() <= 0);
            mProductDetailViewModel.updateProductProPrice(Utility.getIndianCurrencyPriceFormat(mSizedProduct.getUnitPrice()));

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
        pushSizeGuideFragment();
    }

    @Override
    public void plus() {
        if (mQuality < mSizedProduct.getStockData()) {
            mQuality++;
            mProductDetailViewModel.updateQty(String.valueOf(mQuality));
        } else {
            // TODO: 3/9/2018 display toast no qty available
        }
    }

    @Override
    public void minus() {
        if (mQuality == 1) {
            return;
        }
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
        Intent intent = new Intent(Constants.ACTION_UPDATE_CART_ICON_STATE);
        intent.putExtra(Constants.BUNDLE_POSITION, mSelectedColorPosition);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
        ((MainActivity) getBaseActivity()).updateCartNum();
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
}

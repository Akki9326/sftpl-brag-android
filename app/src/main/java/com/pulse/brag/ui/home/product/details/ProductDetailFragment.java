package com.pulse.brag.ui.home.product.details;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.RelativeLayout;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.adapters.ImagePagerAdapter;
import com.pulse.brag.callback.IOnProductColorSelectListener;
import com.pulse.brag.callback.IOnProductSizeSelectListener;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.data.model.datas.DataAddToCart;
import com.pulse.brag.data.model.datas.DataProductList;
import com.pulse.brag.data.model.response.ImagePagerResponse;
import com.pulse.brag.databinding.FragmentProductDetailBinding;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.home.product.details.adapter.ColorListAdapter;
import com.pulse.brag.ui.home.product.details.adapter.SizeListAdapter;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.views.FullScreenImageDialogFragment;
import com.pulse.brag.views.HorizontalSpacingDecoration;
import com.pulse.brag.views.webview.WebviewDialogFragment;

import java.lang.reflect.Field;
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
    SizeListAdapter mSizeListAdapter;
    DataProductList.Products mProductDetails;
    DataProductList.Size mProduct;

    List<ImagePagerResponse> imagePagerResponeList;
    int mQuality;


    public static ProductDetailFragment newInstance(DataProductList.Products dataRespone) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.BUNDLE_SELETED_PRODUCT, dataRespone);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
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
            mProductDetails = null;
            mProductDetails = getArguments().getParcelable(Constants.BUNDLE_SELETED_PRODUCT);
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

        if (mProductDetails != null) {
            List<String> colorList = new ArrayList<>();
            colorList.add("#000000");
            mColorListAdapter = new ColorListAdapter(getActivity(), colorList, 0, this);
            mFragmentProductDetailBinding.recycleViewColor.setAdapter(mColorListAdapter);
            mFragmentProductDetailBinding.recycleViewColor.addItemDecoration(new HorizontalSpacingDecoration(10));


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
            mFragmentProductDetailBinding.recycleViewSize.setAdapter(mSizeListAdapter);
            mFragmentProductDetailBinding.recycleViewSize.addItemDecoration(new HorizontalSpacingDecoration(10));

            showData(mProduct.getDescription(), mProduct.getDescription2(), mProduct.getStockData(), mProduct.getUnitPrice(), mProduct.getImages());
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


    public void showData(String description, String description2, int stockData, double unitPrice, List<String> bannerImages) {

        //// TODO: 3/12/2018 if data not available than display no data screen

        if (stockData > 0) {
            mProductDetailViewModel.updateQty(String.valueOf(1));
            mQuality = 1;
        }
        mProductDetailViewModel.updateProductProDetail(description);
        mProductDetailViewModel.updateProductProShortDetail(description2);
        mProductDetailViewModel.updateNotifyMe(stockData <= 0);
        mProductDetailViewModel.updateProductProPrice(Utility.getIndianCurrencyPriceFormat(unitPrice));

        imagePagerResponeList.clear();
        for (String url : bannerImages) {
            imagePagerResponeList.add(new ImagePagerResponse(url, ""));
        }

        mFragmentProductDetailBinding.viewPager.setAdapter(new ImagePagerAdapter(getActivity(), imagePagerResponeList, this));
        mFragmentProductDetailBinding.pagerIndicator.setViewPager(mFragmentProductDetailBinding.viewPager);


    }

    private void pushSizeGuideFragment() {
        // TODO: 2/20/2018 Change url to size guide
        Bundle bundle;
        WebviewDialogFragment dialogFragment;
        bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_TITLE, "Size Guide");
        bundle.putString(Constants.BUNDLE_SUBTITLE, "https://bragstore.com/pages/privacy-policy");
        dialogFragment = new WebviewDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getChildFragmentManager(), "");
    }


    @Override
    public void OnSelectedSize(int prevPos, int pos, DataProductList.Size item) {
        if (prevPos != pos) {
            mSizeListAdapter.setSelectedItem(pos);
            mProduct = item;
            showData(item.getDescription(), item.getDescription2(), item.getStockData(), item.getUnitPrice(), item.getImages());
        }
    }

    @Override
    public void onSelectedColor(int pos) {
        mColorListAdapter.setSelectorItem(pos);
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
    public void sizeGuide() {
        pushSizeGuideFragment();
    }

    @Override
    public void plus() {
        if (mQuality < mProduct.getStockData()) {
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
            mProductDetailViewModel.addToCart(mProduct.getNo(), Integer.parseInt(mFragmentProductDetailBinding.textViewQty.getText().toString()));
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null,null);
        }
    }

    @Override
    public void onAddedToCart(List<DataAddToCart> data) {
        ((MainActivity) getBaseActivity()).addToCartAPI(data.size());
    }

    @Override
    public void notifyMe() {
        if (Utility.isConnection(getActivity())) {
            mProductDetailViewModel.notifyMe(mProduct.getNo());
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

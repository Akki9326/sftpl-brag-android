package com.pulse.brag.ui.category;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pulse.brag.BR;
import com.pulse.brag.R;
import com.pulse.brag.adapters.CategoryListAdapter;
import com.pulse.brag.adapters.ImagePagerAdapter;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentCategoryBinding;
import com.pulse.brag.erecyclerview.GridSpacingItemDecoration;
import com.pulse.brag.interfaces.OnItemClickListener;
import com.pulse.brag.pojo.datas.CategoryListResponseData;
import com.pulse.brag.pojo.response.ImagePagerResponse;
import com.pulse.brag.ui.core.CoreActivity;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.ui.subcategory.SubCategoryFragment;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

/**
 * Created by nikhil.vadoliya on 14-02-2018.
 */


public class CategoryFragment extends CoreFragment<FragmentCategoryBinding, CategoryViewModel> implements CategoryNavigator, OnItemClickListener {

    @Inject
    CategoryViewModel categoryViewModel;

    FragmentCategoryBinding mFragmentCategoryBinding;

    List<CategoryListResponseData> mCategoryList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryViewModel.setNavigator(this);
    }

  /*  @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }*/

    @Override
    public void beforeViewCreated() {

    }

    @Override
    public void afterViewCreated() {
        mFragmentCategoryBinding = getViewDataBinding();
        Utility.applyTypeFace(getBaseActivity(), mFragmentCategoryBinding.baseLayout);

        mFragmentCategoryBinding.recycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mFragmentCategoryBinding.recycleView.setHasFixedSize(true);
        mFragmentCategoryBinding.recycleView.setMotionEventSplittingEnabled(false);
        mFragmentCategoryBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 0, false));


        checkInternet();
    }

    @Override
    public void setUpToolbar() {

    }


    private void checkInternet() {

        if (Utility.isConnection(getContext())) {
            showProgress();
            categoryViewModel.getCategoryData();
        } else {
            AlertUtils.showAlertMessage(getActivity(), 0, null);
        }


    }

    @Override
    public CategoryViewModel getViewModel() {
        return categoryViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    public void onApiSuccess() {
        hideProgress();
        List<ImagePagerResponse> imagePagerResponeList = new ArrayList<>();
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/files/tripper-collection-landing-banner.jpg?17997587327459325", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/IMG_9739_grande.jpg?v=1499673727", ""));

        mCategoryList = new ArrayList<>();
        mCategoryList.add(new CategoryListResponseData("", "BRALETTES", "https://cdn.shopify.com/s/files/1/1629/9535/products/Chandini_SLIDE_Pattern_B_BRAG-Optical59961_grande.jpg?v=1516609967", 0));
        mCategoryList.add(new CategoryListResponseData("", "SPORTS BRA", "https://cdn.shopify.com/s/files/1/1629/9535/files/Sport-Inside-Banner.jpg?17088418355138553923", 0));
        mCategoryList.add(new CategoryListResponseData("", "PANTIES", "http://cdn.shopify.com/s/files/1/1629/9535/products/2_front_7b6ad7b4-2320-420b-badd-07087487111f_large.jpg?v=1516609968", 0));


        mFragmentCategoryBinding.viewPager.setAdapter(new ImagePagerAdapter(getBaseActivity(), imagePagerResponeList));
        mFragmentCategoryBinding.pagerView.setViewPager(mFragmentCategoryBinding.viewPager);

        CategoryListAdapter adapter = new CategoryListAdapter(getContext(), mCategoryList, this);

        mFragmentCategoryBinding.recycleView.setAdapter(adapter);
        mFragmentCategoryBinding.recycleView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onApiError(ApiError error) {
        hideProgress();
        AlertUtils.showAlertMessage(getActivity(), error.getHttpCode(), error.getMessage());
    }


    @Override
    public void onItemClick(int position) {
        ((MainActivity) getActivity()).pushFragments(SubCategoryFragment.newInstance(mCategoryList.get(position).getUrl(), mCategoryList.get(position).getOptionName()), true, true);

    }
}

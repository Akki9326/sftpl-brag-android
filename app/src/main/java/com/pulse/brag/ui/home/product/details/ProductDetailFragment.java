package com.pulse.brag.ui.home.product.details;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.widget.RelativeLayout;

import com.android.databinding.library.baseAdapters.BR;
import com.pulse.brag.R;
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.FragmentProductDetailBinding;
import com.pulse.brag.adapters.ProductDetailImagePagerAdapter;
import com.pulse.brag.data.model.requests.AddToCartRequest;
import com.pulse.brag.ui.core.CoreFragment;
import com.pulse.brag.ui.fragments.FullScreenImageDialogFragment;
import com.pulse.brag.ui.fragments.WebviewDialogFragment;
import com.pulse.brag.ui.home.product.details.adapter.ColorListAdapter;
import com.pulse.brag.ui.home.product.details.adapter.SizeListAdapter;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.callback.IOnItemClickListener;
import com.pulse.brag.callback.IOnProductColorSelectListener;
import com.pulse.brag.callback.IOnProductSizeSelectListener;
import com.pulse.brag.data.model.DummeyDataRespone;
import com.pulse.brag.data.model.response.ImagePagerResponse;
import com.pulse.brag.views.HorizontalSpacingDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by nikhil.vadoliya on 04-10-2017.
 */


public class ProductDetailFragment extends CoreFragment<FragmentProductDetailBinding, ProductDetailViewModel> implements IOnItemClickListener, IOnProductSizeSelectListener, IOnProductColorSelectListener, ProductDetailNavigator /*BaseInterface,*/ {

    @Inject
    ProductDetailViewModel mProductDetailViewModel;
    @Inject
    @Named("forColor")
    LinearLayoutManager mColorLayoutManager;

    @Inject
    @Named("forSize")
    LinearLayoutManager mSizeLayoutManager;
    FragmentProductDetailBinding mFragmentProductDetailBinding;

    /*View mView;
    ViewPager mViewPager;
    CustomViewPagerIndicator mViewPagerIndicator;
    TextView mTxtProductName, mTxtProPrice, mTxtProDetails, mTxtProShortDetail;
    RecyclerView mRecyclerViewColor;
    RecyclerView mRecyclerViewSize;
    ImageView mImgAdd, mImgMinus;
    TextView mTxtQty;*/

    ColorListAdapter mColorListAdapter;
    SizeListAdapter mSizeListAdapter;
    ProductDetailImagePagerAdapter mDetailImagePagerAdapter;
    List<String> mIntegerList;
    List<String> mStringList;
    List<ImagePagerResponse> imagePagerResponeList;
    int mQuality;


    public static ProductDetailFragment newInstance(DummeyDataRespone dataRespone) {

        Bundle args = new Bundle();
        args.putParcelable(Constants.BUNDLE_SELETED_PRODUCT, dataRespone);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

   /* @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_product_detail, container, false);
            initializeData();
            setListeners();
            showData();
        }
        return mView;
    }*/

    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbar();
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductDetailViewModel.setNavigator(this);
    }

    @Override
    public void beforeViewCreated() {
        mIntegerList = new ArrayList<>();
        mStringList = new ArrayList<>();
    }

    @Override
    public void afterViewCreated() {
        mFragmentProductDetailBinding = getViewDataBinding();
        Utility.applyTypeFace(getContext(), (RelativeLayout) mFragmentProductDetailBinding.baseLayout);

        mFragmentProductDetailBinding.recycleViewColor.setHasFixedSize(true);
        mFragmentProductDetailBinding.recycleViewColor.setLayoutManager(mColorLayoutManager);
        mFragmentProductDetailBinding.recycleViewSize.setHasFixedSize(true);
        mFragmentProductDetailBinding.recycleViewSize.setLayoutManager(mSizeLayoutManager);

        mProductDetailViewModel.updateQty(String.valueOf(1));
        //mQuality = Integer.parseInt(mFragmentProductDetailBinding.textViewQty.getText().toString());
        mQuality = 1;

        mProductDetailViewModel.updateProductName(getString(R.string.text_s));
        mProductDetailViewModel.updateProductProShortDetail(getString(R.string.text_s));
        mProductDetailViewModel.updateNotifyMe(false);

        showData();
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

    /*@Override
    public void setToolbar() {
        ((MainActivity) getActivity()).showToolbar(true, false, true, getString(R.string.toolbar_label_product_detail));
    }

    @Override
    public void initializeData() {

        mViewPager =  mView.findViewById(R.id.view_pager);

        mViewPagerIndicator =  mView.findViewById(R.id.pager_view);
        mTxtProductName =mView.findViewById(R.id.textview_product_name);
        mTxtProDetails = mView.findViewById(R.id.textView_description);
        mTxtProShortDetail = mView.findViewById(R.id.textView_short_des);
        mTxtProPrice = mView.findViewById(R.id.textView_price);
        mTxtQty = mView.findViewById(R.id.textView_qty);
        mImgAdd =  mView.findViewById(R.id.imageView_add);
        mImgMinus = mView.findViewById(R.id.imageView_minus);

        mRecyclerViewColor =  mView.findViewById(R.id.recycleView_color);
        mRecyclerViewColor.setHasFixedSize(true);
        mRecyclerViewColor.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewSize =  mView.findViewById(R.id.recycleView_size);
        mRecyclerViewColor.setHasFixedSize(true);
        mRecyclerViewSize.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        Utility.applyTypeFace(getContext(), (RelativeLayout) mView.findViewById(R.id.base_layout));

        mIntegerList = new ArrayList<>();
        mStringList = new ArrayList<>();

        mQuality = Integer.parseInt(mTxtQty.getText().toString());


    }

    @Override
    public void setListeners() {
        mImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mQuality++;
                mTxtQty.setText("" + mQuality);
            }
        });
        mImgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mQuality == 1) {
                    return;
                }
                mQuality--;
                mTxtQty.setText("" + mQuality);
            }
        });

    }

    @Override
    public void showData() {

        imagePagerResponeList = new ArrayList<>();
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/files/tripper-collection-landing-banner.jpg?17997587327459325", "CLASSIC BIKINI"));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/IMG_9739_grande.jpg?v=1499673727", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/IMG_9739_grande.jpg?v=1499673727", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/Banner-image_grande.jpg?v=1494221088", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/neon-post-classic_grande.jpg?v=1492607080", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/Banner-image_grande.jpg?v=1494221088", ""));

        mDetailImagePagerAdapter = new ProductDetailImagePagerAdapter(getActivity(), imagePagerResponeList, this);
        mViewPager.setAdapter(mDetailImagePagerAdapter);
        mViewPagerIndicator.setViewPager(mViewPager);


        if (getArguments()!=null && getArguments().containsKey(Constants.BUNDLE_SELETED_PRODUCT)) {
            DummeyDataRespone dataRespone = getArguments().getParcelable(Constants.BUNDLE_SELETED_PRODUCT);
            mTxtProductName.setText(dataRespone.getFirst_name());
        }
        mTxtProPrice.setText(Utility.getIndianCurrencePriceFormate(500));

        String mShortDetail = " Lucille Curtis |Juana Hanson |Lila Flores |Kevin Rodriguez |Ebony Norman |Celia Rodriquez |Jake Morales |Jane Farmer |Willie tRivera |Freeman";


        mTxtProShortDetail.setText(Html.fromHtml(Utility.getTextWithBullet(mShortDetail)));


//        mIntegerList.add("#F44336");
        mIntegerList.add("#000000");
//        mIntegerList.add("#E91E63");
//        mIntegerList.add("#9C27B0");
//        mIntegerList.add("#2196F3");
//        mIntegerList.add("#FF9800");
//        mIntegerList.add("#FF5722");
//        mIntegerList.add("#3F51B5");

        mColorListAdapter = new ColorListAdapter(getActivity(), mIntegerList, 0, this);
        mRecyclerViewColor.setAdapter(mColorListAdapter);
        mRecyclerViewColor.addItemDecoration(new HorizontalSpacingDecoration(10));


        List<String> mStringList = new ArrayList<>();
        mStringList.add("XS");
        mStringList.add("S");
        mStringList.add("M");
        mStringList.add("L");
//        mStringList.add("X");
//        mStringList.add("XL");
//        mStringList.add("XXL");
//        mStringList.add("XXL");

        mSizeListAdapter = new SizeListAdapter(getActivity(), mStringList, 0, this);
        mRecyclerViewSize.setAdapter(mSizeListAdapter);
        mRecyclerViewSize.addItemDecoration(new HorizontalSpacingDecoration(10));
    }*/

    public void showData() {
        imagePagerResponeList = new ArrayList<>();
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/files/tripper-collection-landing-banner.jpg?17997587327459325", "CLASSIC BIKINI"));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/IMG_9739_grande.jpg?v=1499673727", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/IMG_9739_grande.jpg?v=1499673727", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/Banner-image_grande.jpg?v=1494221088", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/neon-post-classic_grande.jpg?v=1492607080", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/Banner-image_grande.jpg?v=1494221088", ""));

        mDetailImagePagerAdapter = new ProductDetailImagePagerAdapter(getActivity(), imagePagerResponeList, this);
        mFragmentProductDetailBinding.viewPager.setAdapter(mDetailImagePagerAdapter);
        mFragmentProductDetailBinding.pagerIndicator.setViewPager(mFragmentProductDetailBinding.viewPager);


        if (getArguments() != null && getArguments().containsKey(Constants.BUNDLE_SELETED_PRODUCT)) {
            DummeyDataRespone dataRespone = getArguments().getParcelable(Constants.BUNDLE_SELETED_PRODUCT);
            mProductDetailViewModel.updateProductName(dataRespone.getFirst_name());
        }
        mProductDetailViewModel.updateProductProPrice(Utility.getIndianCurrencePriceFormate(500));

        String mShortDetail = " Lucille Curtis |Juana Hanson |Lila Flores |Kevin Rodriguez |Ebony Norman |Celia Rodriquez |Jake Morales |Jane Farmer |Willie tRivera |Freeman";
        mProductDetailViewModel.updateProductProShortDetail(Html.fromHtml(Utility.getTextWithBullet(mShortDetail)).toString());


        mIntegerList.add("#000000");

        mColorListAdapter = new ColorListAdapter(getActivity(), mIntegerList, 0, this);
        mFragmentProductDetailBinding.recycleViewColor.setAdapter(mColorListAdapter);
        mFragmentProductDetailBinding.recycleViewColor.addItemDecoration(new HorizontalSpacingDecoration(10));


        List<String> mStringList = new ArrayList<>();
        mStringList.add("XS");
        mStringList.add("S");
        mStringList.add("M");
        mStringList.add("L");
//        mStringList.add("X");
//        mStringList.add("XL");
//        mStringList.add("XXL");
//        mStringList.add("XXL");

        mSizeListAdapter = new SizeListAdapter(getActivity(), mStringList, 0, this);
        mFragmentProductDetailBinding.recycleViewSize.setAdapter(mSizeListAdapter);
        mFragmentProductDetailBinding.recycleViewSize.addItemDecoration(new HorizontalSpacingDecoration(10));
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
    public void onItemClick(int position) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.BUNDLE_IMAGE_LIST, (ArrayList<? extends Parcelable>) imagePagerResponeList);
        args.putInt(Constants.BUNDLE_POSITION, position);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        DialogFragment mDialogFragmentImage = new FullScreenImageDialogFragment();
        mDialogFragmentImage.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
        mDialogFragmentImage.setArguments(args);
        mDialogFragmentImage.show(fm, "");

    }


    @Override
    public void OnSelectedSize(int pos) {
        mSizeListAdapter.setSelectedItem(pos);
        mProductDetailViewModel.updateNotifyMe(false);
    }

    @Override
    public void onSeleteColor(int pos) {
        mColorListAdapter.setSelectorItem(pos);
        mProductDetailViewModel.updateNotifyMe(true);

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
    public void sizeGuide() {
        pushSizeGuideFragment();
    }

    @Override
    public void plus() {
        mQuality++;
        mProductDetailViewModel.updateQty(String.valueOf(mQuality));
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
        ((MainActivity)getBaseActivity()).addToCartAPI(new AddToCartRequest());
    }

    @Override
    public void notifyMe() {
        // TODO: 2/20/2018 check for new parameter
        mProductDetailViewModel.notifyMe("", "", "");
    }
}

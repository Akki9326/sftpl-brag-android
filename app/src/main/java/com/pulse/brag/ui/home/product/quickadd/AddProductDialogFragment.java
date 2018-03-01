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
import com.pulse.brag.data.model.ApiError;
import com.pulse.brag.databinding.DialogFragmentAddProductBinding;
import com.pulse.brag.data.model.requests.AddToCartRequest;
import com.pulse.brag.ui.core.CoreDialogFragment;
import com.pulse.brag.ui.home.product.details.adapter.ColorListAdapter;
import com.pulse.brag.adapters.ImagePagerAdapter;
import com.pulse.brag.ui.home.product.details.adapter.SizeListAdapter;
import com.pulse.brag.ui.main.MainActivity;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.callback.IOnProductColorSelectListener;
import com.pulse.brag.callback.IOnProductSizeSelectListener;
import com.pulse.brag.data.model.DummeyDataRespone;
import com.pulse.brag.data.model.response.ImagePagerResponse;
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


    /*View mView;
    LinearLayout mLinerDummey;
    RecyclerView mRecyclerViewColor, mRecyclerViewSize;
    TextView mTxtQty, mTxtTitle, mTxtAddCart;
    TextView mTxtMax;
    EditText mEditQty;
    ViewPager mViewPager;
    CustomViewPagerIndicator mViewPagerIndicator;
    NestedScrollView mNestedScrollView;*/

    ColorListAdapter mColorListAdapter;
    SizeListAdapter mSizeListAdapter;

    DummeyDataRespone data;

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mView = inflater.inflate(R.layout.dialog_fragment_add_product, container, false);
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_DialogWhenLarge_NoActionBar);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeData();
        setListeners();
        attachKeyboardListeners();
        showData();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(130);
        dialog.getWindow().setBackgroundDrawable(d);

//        dialog.getWindow().getAttributes().windowAnimations = R.style.AddProdDialogAnimation;
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        return dialog;
    }


    @Override
    public void setToolbar() {

    }

    @Override
    public void initializeData() {

        mRecyclerViewColor = mView.findViewById(R.id.recycleView_color);
        mRecyclerViewColor.setHasFixedSize(true);
        mRecyclerViewColor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mRecyclerViewSize = mView.findViewById(R.id.recycleView_size);
        mRecyclerViewColor.setHasFixedSize(true);
        mRecyclerViewSize.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mTxtTitle = mView.findViewById(R.id.textview_product_name);
        mTxtQty = mView.findViewById(R.id.textView_qty);
        mLinerDummey = mView.findViewById(R.id.linear_dummey);
        mTxtAddCart = mView.findViewById(R.id.textview_add_cart);
        mEditQty = mView.findViewById(R.id.edittext_qty);
        mTxtMax = mView.findViewById(R.id.textview_max);
        mViewPager = mView.findViewById(R.id.view_pager);
        mViewPagerIndicator = mView.findViewById(R.id.pager_view);
        mNestedScrollView = mView.findViewById(R.id.scrollView);

        Utility.applyTypeFace(getActivity(), (LinearLayout) mView.findViewById(R.id.base_layout));

        data = getArguments().getParcelable(Constants.BUNDLE_SELETED_PRODUCT);

    }

    @Override
    public void setListeners() {

        ((LinearLayout) mView.findViewById(R.id.base_layout)).setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                dismiss();
            }
        });
        mTxtAddCart.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
//                ((MainActivity) getActivity()).addToCartAPI(new AddToCartRequest());
                dismiss();
            }
        });


        mEditQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNestedScrollView.smoothScrollTo(0, mTxtAddCart.getTop() + 205);
            }
        });

        mTxtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //prevent onclick of baselayout
            }
        });
        mEditQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    if (s.toString().trim().length() == 0 || new Integer(s.toString()).intValue() == 0) {
                        mTxtMax.setTextColor(getResources().getColor(R.color.text_black));
                        mTxtAddCart.setTextColor(getResources().getColor(R.color.gray_transparent));
                        mTxtAddCart.setEnabled(false);
                    } else if (new Integer(s.toString()).intValue() > 50) {
                        mTxtMax.setTextColor(Color.RED);
                        mTxtAddCart.setTextColor(getResources().getColor(R.color.gray_transparent));
                        mTxtAddCart.setEnabled(false);
                    } else {
                        mTxtMax.setTextColor(getResources().getColor(R.color.text_black));
                        mTxtAddCart.setTextColor(getResources().getColor(R.color.text_black));
                        mTxtAddCart.setEnabled(true);
                    }
                }
            }
        });


    }

    public void showData() {

        // TODO: 15-12-2017 Qty max lenght
        mEditQty.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        mTxtTitle.setText(data.getFirst_name() + " " + data.getLast_name());
        List<String> mIntegerList = new ArrayList<>();
        mIntegerList.add("#F44336");
        mIntegerList.add("#E91E63");
        mIntegerList.add("#9C27B0");
        mIntegerList.add("#2196F3");
        mIntegerList.add("#FF9800");
        mIntegerList.add("#FF5722");
        mIntegerList.add("#3F51B5");


        mColorListAdapter = new ColorListAdapter(getActivity(), mIntegerList, 0, this);
        mRecyclerViewColor.setAdapter(mColorListAdapter);
        mRecyclerViewColor.addItemDecoration(new HorizontalSpacingDecoration(10));

        List<String> mStringList = new ArrayList<>();
        mStringList.add("S");
        mStringList.add("M");
        mStringList.add("X");
        mStringList.add("XL");
        mStringList.add("XXL");
        mStringList.add("XXXL");

        mSizeListAdapter = new SizeListAdapter(getActivity(), mStringList, 0, this);
        mRecyclerViewSize.setAdapter(mSizeListAdapter);
        mRecyclerViewSize.addItemDecoration(new HorizontalSpacingDecoration(10));

        List<ImagePagerResponse> imagePagerResponeList = new ArrayList<>();
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/files/tripper-collection-landing-banner.jpg?17997587327459325", "CLASSIC BIKINI"));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/IMG_9739_grande.jpg?v=1499673727", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/Banner-image_grande.jpg?v=1494221088", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/neon-post-classic_grande.jpg?v=1492607080", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/Banner-image_grande.jpg?v=1494221088", ""));

        mViewPager.setAdapter(new ImagePagerAdapter(getActivity(), imagePagerResponeList));
        mViewPagerIndicator.setViewPager(mViewPager);
    }*/

    public void showData() {

        // TODO: 15-12-2017 Qty max lenght
        mDialogFragmentAddProductBinding.edittextQty.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        mDialogFragmentAddProductBinding.textviewProductName.setText(data.getFirst_name() + " " + data.getLast_name());

        List<String> mIntegerList = new ArrayList<>();
        mIntegerList.add("#F44336");
        mIntegerList.add("#E91E63");
        mIntegerList.add("#9C27B0");
        mIntegerList.add("#2196F3");
        mIntegerList.add("#FF9800");
        mIntegerList.add("#FF5722");
        mIntegerList.add("#3F51B5");
        mColorListAdapter = new ColorListAdapter(getActivity(), mIntegerList, 0, this);
        mDialogFragmentAddProductBinding.recycleViewColor.setAdapter(mColorListAdapter);
        mDialogFragmentAddProductBinding.recycleViewColor.addItemDecoration(new HorizontalSpacingDecoration(10));

        List<String> mStringList = new ArrayList<>();
        mStringList.add("S");
        mStringList.add("M");
        mStringList.add("X");
        mStringList.add("XL");
        mStringList.add("XXL");
        mStringList.add("XXXL");
        mSizeListAdapter = new SizeListAdapter(getActivity(), mStringList, 0, this);
        mDialogFragmentAddProductBinding.recycleViewSize.setAdapter(mSizeListAdapter);
        mDialogFragmentAddProductBinding.recycleViewSize.addItemDecoration(new HorizontalSpacingDecoration(10));

        List<ImagePagerResponse> imagePagerResponeList = new ArrayList<>();
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/files/tripper-collection-landing-banner.jpg?17997587327459325", "CLASSIC BIKINI"));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/IMG_9739_grande.jpg?v=1499673727", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/Banner-image_grande.jpg?v=1494221088", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/neon-post-classic_grande.jpg?v=1492607080", ""));
        imagePagerResponeList.add(new ImagePagerResponse("http://cdn.shopify.com/s/files/1/1629/9535/articles/Banner-image_grande.jpg?v=1494221088", ""));

        mDialogFragmentAddProductBinding.viewPager.setAdapter(new ImagePagerAdapter(getActivity(), imagePagerResponeList, this));
        mDialogFragmentAddProductBinding.pagerView.setViewPager(mDialogFragmentAddProductBinding.viewPager);
    }

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
    //private ViewGroup rootLayout;

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
        //setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_DialogWhenLarge_NoActionBar);
        data = getArguments().getParcelable(Constants.BUNDLE_SELETED_PRODUCT);
    }

    @Override
    public void afterViewCreated() {
        mDialogFragmentAddProductBinding = getViewDataBinding();
        Utility.applyTypeFace(getActivity(), (LinearLayout) mDialogFragmentAddProductBinding.baseLayout);

        mDialogFragmentAddProductBinding.recycleViewColor.setHasFixedSize(true);
        mDialogFragmentAddProductBinding.recycleViewColor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mDialogFragmentAddProductBinding.recycleViewSize.setHasFixedSize(true);
        mDialogFragmentAddProductBinding.recycleViewSize.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mAddProductDialogViewModel.updateMaxQty(String.valueOf(1));
        mAddProductDialogViewModel.updateNotifyMe(false);

        attachKeyboardListeners();
        showData();
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

    @Override
    public void onSeleteColor(int pos) {
        mColorListAdapter.setSelectorItem(pos);
        mAddProductDialogViewModel.updateNotifyMe(true);
    }

    @Override
    public void OnSelectedSize(int pos) {
        mSizeListAdapter.setSelectedItem(pos);
        mAddProductDialogViewModel.updateNotifyMe(false);
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
    public void dismissFragment() {
        dismissDialog("");
    }

    @Override
    public void editQty() {
        mDialogFragmentAddProductBinding.scrollView.smoothScrollTo(0, mDialogFragmentAddProductBinding.textviewAddCart.getTop() + 205);
    }

    @Override
    public void addToCart() {
        ((MainActivity) getBaseActivity()).addToCartAPI(new AddToCartRequest());
        dismissDialog("");
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s != null) {
            if (s.toString().trim().length() == 0 || new Integer(s.toString()).intValue() == 0) {
                mDialogFragmentAddProductBinding.textviewMax.setTextColor(getResources().getColor(R.color.text_black));
                mDialogFragmentAddProductBinding.textviewAddCart.setTextColor(getResources().getColor(R.color.gray_transparent));
                mDialogFragmentAddProductBinding.textviewAddCart.setEnabled(false);
            } else if (new Integer(s.toString()).intValue() > 50) {
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
        // TODO: 2/20/2018 check for new parameter
        mAddProductDialogViewModel.notifyMe("", "", "");
    }

    @Override
    public void onImagePageClick(int pos, ImagePagerResponse item) {

    }
}

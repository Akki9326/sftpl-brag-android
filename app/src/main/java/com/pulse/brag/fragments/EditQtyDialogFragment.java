package com.pulse.brag.fragments;


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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pulse.brag.R;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.interfaces.BaseInterface;
import com.pulse.brag.views.OnSingleClickListener;

/**
 * Created by nikhil.vadoliya on 05-12-2017.
 */


public class EditQtyDialogFragment extends DialogFragment implements BaseInterface {

    public static final int RESULT_CODE_QTY = 2;

    View mView;
    TextView mTxtProduct, mTxtDone, mTxtMax;
    EditText mEdtQty;
    ImageView mImgProduct;

    int qty;
    boolean isValidQty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mView = inflater.inflate(R.layout.dialog_fragment_edit_qty, container, false);
        initializeData();
        setListeners();
        return mView;
    }

    @Override
    public void setToolbar() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initializeData() {
        mTxtProduct = (TextView) mView.findViewById(R.id.textview_product_name);
        mTxtDone = (TextView) mView.findViewById(R.id.textview_done);
        mTxtMax = (TextView) mView.findViewById(R.id.textview_max);
        mEdtQty = (EditText) mView.findViewById(R.id.edittext_qty);
        mImgProduct = (ImageView) mView.findViewById(R.id.imageview_product_img);

        isValidQty = true;

        Utility.applyTypeFace(getActivity(), (LinearLayout) mView.findViewById(R.id.base_layout));

        if (getArguments().containsKey(Constants.BUNDLE_PRODUCT_NAME)) {
            mTxtProduct.setText(getArguments().getString(Constants.BUNDLE_PRODUCT_NAME));
        }
        if (getArguments().containsKey(Constants.BUNDLE_QTY)) {
            qty = getArguments().getInt(Constants.BUNDLE_QTY);
            mEdtQty.setText("" + qty);
            mEdtQty.setSelection(mEdtQty.getText().length());

        }
        if (getArguments().containsKey(Constants.BUNDLE_PRODUCT_IMG)) {
            Utility.imageSet(getActivity(), getArguments().getString(Constants.BUNDLE_PRODUCT_IMG), mImgProduct);
        }

        // TODO: 07-12-2017 max length of qty
        mEdtQty.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    @Override
    public void setListeners() {

        mTxtDone.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

//                if (!mTxtDone.isSelected()) {
//                    return;
//                }
                if (mEdtQty.getText().toString().isEmpty()) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_enter_qty));
                } else if (Integer.parseInt(mEdtQty.getText().toString()) == 0) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_qty_not_empty));
                } else if (!isValidQty) {
                    Utility.showAlertMessage(getActivity(), getString(R.string.error_valid_qty));
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(Constants.BUNDLE_QTY, Integer.valueOf(mEdtQty.getText().toString()));
                    getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_CODE_QTY, intent);
                    dismiss();
                }


            }
        });
        mEdtQty.addTextChangedListener(new TextWatcher() {
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
                        mTxtDone.setTextColor(getResources().getColor(R.color.gray_transparent));
                        isValidQty = false;
                        mTxtDone.setEnabled(false);
                    } else if (new Integer(s.toString()).intValue() > 50) {
                        mTxtMax.setTextColor(Color.RED);
                        mTxtDone.setTextColor(getResources().getColor(R.color.gray_transparent));
                        isValidQty = false;
                        mTxtDone.setEnabled(false);
                    } else {
                        mTxtMax.setTextColor(getResources().getColor(R.color.text_black));
                        mTxtDone.setTextColor(getResources().getColor(R.color.text_black));
                        isValidQty = true;
                        mTxtDone.setEnabled(true);
                    }
                }
            }
        });
    }

    @Override
    public void showData() {

    }
}

package com.pulse.brag.ui.cart.editquantity;


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
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.pulse.brag.R;
import com.pulse.brag.BR;
import com.pulse.brag.databinding.DialogFragmentEditQtyBinding;
import com.pulse.brag.ui.core.CoreDialogFragment;
import com.pulse.brag.utils.AlertUtils;
import com.pulse.brag.utils.Constants;
import com.pulse.brag.utils.Utility;
import com.pulse.brag.utils.Validation;

import javax.inject.Inject;


/**
 * Created by nikhil.vadoliya on 05-12-2017.
 */


public class EditQtyDialogFragment extends CoreDialogFragment<DialogFragmentEditQtyBinding, EditQtyDialogViewModel>
        implements EditQtyDialogNavigator {

    public static final int RESULT_CODE_QTY = 2;


    @Inject
    EditQtyDialogViewModel mEditQtyDialogViewModel;
    DialogFragmentEditQtyBinding mDialogFragmentEditQtyBinding;


    int qty;
    boolean isValidQty;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog);
        mEditQtyDialogViewModel.setNavigator(this);
    }

    @Override
    public Dialog onCreateFragmentDialog(Bundle savedInstanceState, Dialog dialog) {
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    @Override
    public void beforeViewCreated() {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (getArguments().containsKey(Constants.BUNDLE_QTY))
            qty = getArguments().getInt(Constants.BUNDLE_QTY);
    }

    @Override
    public void afterViewCreated() {
        mDialogFragmentEditQtyBinding = getViewDataBinding();

        isValidQty = true;
        Utility.applyTypeFace(getActivity(), mDialogFragmentEditQtyBinding.baseLayout);

        mEditQtyDialogViewModel.setQty(qty);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialogFragmentEditQtyBinding.edittextQty.setSelection(mDialogFragmentEditQtyBinding.edittextQty.getText().length());

            }
        }, 200);

        // TODO: 07-12-2017 max length of qty
        mDialogFragmentEditQtyBinding.edittextQty.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
    }

    @Override
    public void setUpToolbar() {

    }

    @Override
    public EditQtyDialogViewModel getViewModel() {
        return mEditQtyDialogViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_fragment_edit_qty;
    }

    @Override
    public void onDoneClick() {

        if (!mDialogFragmentEditQtyBinding.textviewDone.isEnabled()) {
            return;
        } else if (Validation.isEmpty(mDialogFragmentEditQtyBinding.edittextQty)) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_enter_qty));
        } else if (Integer.parseInt(mDialogFragmentEditQtyBinding.edittextQty.getText().toString()) == 0) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_qty_not_empty));
        } else if (!isValidQty) {
            AlertUtils.showAlertMessage(getActivity(), getString(R.string.error_valid_qty));
        } else {
            Intent intent = new Intent();
            intent.putExtra(Constants.BUNDLE_QTY, Integer.valueOf(mDialogFragmentEditQtyBinding.edittextQty.getText().toString()));
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_CODE_QTY, intent);
            dismiss();
        }

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s != null) {
            if (s.toString().trim().length() == 0 || new Integer(s.toString()).intValue() == 0) {
                mDialogFragmentEditQtyBinding.textviewMax.setTextColor(getResources().getColor(R.color.text_black));
                mDialogFragmentEditQtyBinding.textviewDone.setTextColor(getResources().getColor(R.color.gray_transparent));
                isValidQty = false;
                mDialogFragmentEditQtyBinding.textviewDone.setEnabled(false);
            } else if (new Integer(s.toString()).intValue() > 50) {
                mDialogFragmentEditQtyBinding.textviewMax.setTextColor(Color.RED);
                mDialogFragmentEditQtyBinding.textviewDone.setTextColor(getResources().getColor(R.color.gray_transparent));
                isValidQty = false;
                mDialogFragmentEditQtyBinding.textviewDone.setEnabled(false);
            } else {
                mDialogFragmentEditQtyBinding.textviewMax.setTextColor(getResources().getColor(R.color.text_black));
                mDialogFragmentEditQtyBinding.textviewDone.setTextColor(getResources().getColor(R.color.text_black));
                isValidQty = true;
                mDialogFragmentEditQtyBinding.textviewDone.setEnabled(true);
            }
        }
    }
}

package com.ragtagger.brag.ui.cart.editquantity;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ragtagger.brag.R;
import com.ragtagger.brag.callback.OnSingleClickListener;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.utils.AlertUtils;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.utils.Validation;

/**
 * Created by nikhil.vadoliya on 20-02-2018.
 */


public class EditQtyDialogViewModel extends CoreViewModel<EditQtyDialogNavigator> {

    ObservableField<Integer> qty = new ObservableField<>();
    ObservableField<Integer> availableStock = new ObservableField<>();

    public EditQtyDialogViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onDoneClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().performClickDone();
            }
        };
    }

    public ObservableField<Integer> getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty.set(qty);
    }

    public void afterTextChanged(Editable s) {
        getNavigator().performActionAfterTextChanged(s);
    }

    public void setAvailableStock(int stock) {
        availableStock.set(stock);
    }

    public ObservableField<Integer> getAvailableStock() {
        return availableStock;
    }

    void validateEditQtyForm(Activity activity, TextView done, EditText qty, boolean validQty) {
        if (!done.isEnabled()) {
            return;
        } else if (Validation.isEmpty(qty)) {
            getNavigator().invalidEditQtyForm(activity.getString(R.string.error_enter_qty));
        } else if (Integer.parseInt(qty.getText().toString()) == 0) {
            getNavigator().invalidEditQtyForm(activity.getString(R.string.error_qty_not_empty));
        } else if (!validQty) {
            getNavigator().invalidEditQtyForm(activity.getString(R.string.error_valid_qty));
        } else {
            getNavigator().validEditQtyForm();
        }
    }
}

package com.pulse.brag.ui.editquantity;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.ObservableField;
import android.text.Editable;
import android.view.View;

import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.views.OnSingleClickListener;

/**
 * Created by nikhil.vadoliya on 20-02-2018.
 */


public class EditQtyDialogViewModel extends CoreViewModel<EditQtyDialogNavigator> {

    ObservableField<Integer> qty = new ObservableField<>();

    public EditQtyDialogViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public View.OnClickListener onDoneClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onDoneClick();
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
        getNavigator().afterTextChanged(s);
    }
}

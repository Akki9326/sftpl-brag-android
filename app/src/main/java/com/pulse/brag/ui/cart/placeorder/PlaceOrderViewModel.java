package com.pulse.brag.ui.cart.placeorder;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.databinding.ObservableField;
import android.view.View;

import com.pulse.brag.callback.OnSingleClickListener;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.ui.core.CoreViewModel;

/**
 * Created by nikhil.vadoliya on 21-02-2018.
 */


public class PlaceOrderViewModel extends CoreViewModel<PlaceOrderNavigator> {

    ObservableField<String> total = new ObservableField<>();
    ObservableField<String> listSize = new ObservableField<>();
    String itemsLable;

    public PlaceOrderViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public void setTotal(String total) {
        this.total.set(total);
    }

    public ObservableField<String> getTotalPrice() {
        return total;
    }

    public String getFullName() {
        return getDataManager().getUserData().getFullName();
    }

    public String getMobileNum() {
        return getDataManager().getUserData().getMobileNumber();
    }

    public void setListSize(int listSize) {
        this.listSize.set(String.valueOf(listSize));
        if (listSize > 1) {
            itemsLable = "items";
        } else {
            itemsLable = "item";
        }
    }

    public ObservableField<String> getListSize() {
        return listSize;
    }

    public String getItemsLable() {
        return itemsLable;
    }

    public View.OnClickListener onContinueClick() {
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onContinueClick();
            }
        };
    }

    public View.OnClickListener onEditAddress(){
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onEditAddress();
            }
        };
    }

    public View.OnClickListener onPriceLabelClick(){
        return new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                getNavigator().onPriceLabelClick();
            }
        };
    }
}

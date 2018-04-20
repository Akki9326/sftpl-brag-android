package com.ragtagger.brag.ui.cart.onbehalf;

import android.view.View;

import com.ragtagger.brag.data.model.datas.DataUser;
import com.ragtagger.brag.ui.core.CoreNavigator;

public interface OnBehalfDialogNavigator extends CoreNavigator{

    void performClickDone(View view);

    void noInternetAlert();

    void validCustomerIdForm();

    void invalidCustomerIdForm(String msgv);

    void setCustomerData(DataUser customerData);
}

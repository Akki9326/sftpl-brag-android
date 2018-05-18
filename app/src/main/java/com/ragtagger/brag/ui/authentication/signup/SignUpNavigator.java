package com.ragtagger.brag.ui.authentication.signup;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ragtagger.brag.data.model.datas.DataChannel;
import com.ragtagger.brag.data.model.datas.DataSaleType;
import com.ragtagger.brag.data.model.datas.DataState;
import com.ragtagger.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public interface SignUpNavigator extends CoreNavigator {

    boolean onEditorActionConfirmPass(TextView textView, int i, KeyEvent keyEvent);

    boolean onEditorActionPincode(TextView textView, int i, KeyEvent keyEvent);

    void setState(List<DataState> states);

    void setChannel(List<DataChannel> channels);

    void setSalesType(List<DataSaleType> salesTypes);

    void performClickUserTypeDropdown(View view);

    void performClickChannelDropdown(View view);

    void performClickSaleTypeDropdown(View view);

    void performClickState(View view);

    void performClickSignUp();

    void noInternetAlert();

    void validSignUpForm();

    void invalidSignUpForm(String msg);

    void pushOtpFragment();
}

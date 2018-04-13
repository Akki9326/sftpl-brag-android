package com.ragtagger.brag.ui.authentication.profile.updateprofile;

import android.view.View;

import com.ragtagger.brag.data.model.datas.DataChannel;
import com.ragtagger.brag.data.model.datas.DataSaleType;
import com.ragtagger.brag.data.model.datas.DataState;
import com.ragtagger.brag.data.model.datas.DataUser;
import com.ragtagger.brag.ui.core.CoreNavigator;

import java.util.List;

/**
 * Created by alpesh.rathod on 2/15/2018.
 */

public interface UpdateProfileNavigator extends CoreNavigator {

    void afterGetProfile(DataUser dataUser);

    void setState(List<DataState> states);

    void setSelectedState(DataState state);

    void setChannel(List<DataChannel> channels);

    void setSelectedChannel(DataChannel channel);

    void setSalesType(List<DataSaleType> salesTypes);

    void setSelectedSaleType(DataSaleType saleType);

    void afterGettingRequiredData();

    void performClickChannelDropdown(View view);

    void performClickSaleTypeDropdown(View view);

    void performClickState(View view);

    void performUpdateClick();

    void noInternetAlert();

    void validProfileForm();

    void invalidProfileForm(String msg);

    void showMsgProfileUpdated();
}

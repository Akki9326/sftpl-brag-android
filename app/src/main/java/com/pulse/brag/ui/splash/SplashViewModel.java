package com.pulse.brag.ui.splash;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pulse.brag.helper.PreferencesManager;
import com.pulse.brag.helper.Utility;
import com.pulse.brag.ui.core.CoreViewModel;
import com.pulse.brag.data.IDataManager;
import com.pulse.brag.utils.AppLogger;

/**
 * Created by alpesh.rathod on 2/8/2018.
 */

public class SplashViewModel extends CoreViewModel<SplashNavigator> {

    public SplashViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public void setDeviceNameAndOS() {
        /*if (PreferencesManager.getInstance().getDeviceType().isEmpty()) {
            PreferencesManager.getInstance().setDeviceTypeAndOsVer(Utility.getDeviceName(), android.os.Build.VERSION.RELEASE);
        }
        AppLogger.i(getClass().getSimpleName()+" : setDeviceNameAndOS: device token " + FirebaseInstanceId.getInstance().getToken());
        PreferencesManager.getInstance().setDeviceToken(FirebaseInstanceId.getInstance().getToken());*/


        if (getDataManager().getDeviceType().isEmpty()){
            getDataManager().setDeviceTypeAndOsVer(Utility.getDeviceName(),android.os.Build.VERSION.RELEASE);
        }

        AppLogger.i(getClass().getSimpleName()+" : setDeviceNameAndOS: device token " + FirebaseInstanceId.getInstance().getToken());
        getDataManager().setDeviceToken(FirebaseInstanceId.getInstance().getToken());
    }

    public void decideNextActivity(){
        if (getDataManager().isLogin()) {
            getNavigator().openMainActivity();
        } else {
            getNavigator().openLoginFragmentWithAnimation();
        }
    }

}

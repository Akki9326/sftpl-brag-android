package com.ragtagger.brag.ui.splash;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ragtagger.brag.utils.Utility;
import com.ragtagger.brag.ui.core.CoreViewModel;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.utils.AppLogger;

/**
 * Created by alpesh.rathod on 2/8/2018.
 */

public class SplashViewModel extends CoreViewModel<SplashNavigator> {

    public SplashViewModel(IDataManager dataManager) {
        super(dataManager);
    }

    public void setDeviceNameAndOS() {
        if (getDataManager().getDeviceType().isEmpty()) {
            getDataManager().setDeviceTypeAndOsVer(Utility.getDeviceName(), android.os.Build.VERSION.RELEASE);
        }

        AppLogger.i(getClass().getSimpleName() + " : setDeviceNameAndOS: device token " + FirebaseInstanceId.getInstance().getToken());
        getDataManager().setDeviceToken(FirebaseInstanceId.getInstance().getToken());
    }

    public void decideNextActivity() {
        if (getDataManager().isLogin()) {
            getNavigator().openMainActivity();
        } else {
            getNavigator().openLoginFragmentWithAnimation();
        }
    }

}

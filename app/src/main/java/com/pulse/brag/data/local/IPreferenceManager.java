package com.pulse.brag.data.local;

import com.pulse.brag.pojo.datas.UserData;

/**
 * Created by alpesh.rathod on 2/8/2018.
 */

public interface IPreferenceManager {
    void setIsLogin(boolean isLogin);

    boolean isLogin();

    void setAccessToken(String token);

    String getAccessToken();

    void setDeviceToken(String deviceToken);

    String getDeviceToken();

    void setNotificationId(int notificationId);

    int getNotificationId();

    void setDeviceTypeAndOsVer(String deviceName, String osVersion);

    String getOsVersion();

    String getDeviceType();

    void setUserData(String s);

    UserData getUserData();

    void logout();

}

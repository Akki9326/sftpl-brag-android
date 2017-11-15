package com.pulse.brag;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.pulse.brag.activities.MainActivity;
import com.pulse.brag.activities.SplashActivty;
import com.pulse.brag.enums.NotificationType;
import com.pulse.brag.helper.Constants;
import com.pulse.brag.helper.PreferencesManager;
import com.pulse.brag.pojo.NotificationResponeData;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Provider;

import static android.content.ContentValues.TAG;

/**
 * Created by nikhil.vadoliya on 15-11-2017.
 */


public class FCMService extends FirebaseMessagingService {


    private NotificationUtils notificationUtils;


    private String MESSAGE = "message";
    private String TITLE = "title";
    private String N_TYPE = "notificationType";
    private String WHAT_ID = "whatId";
    private String N_ID = "notificationId";
    private int mNotificationId;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage == null)
            return;

        Log.d(getClass().getSimpleName(), "payload : " + remoteMessage.toString());
        Log.d(getClass().getSimpleName(), "From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(getClass().getSimpleName(), "Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
        }

        sendNotification(remoteMessage.getData().get(MESSAGE),
                remoteMessage.getData().get(TITLE),
                Integer.parseInt(remoteMessage.getData().get(N_TYPE)),
                remoteMessage.getData().get(WHAT_ID),
                remoteMessage.getData().get(N_ID));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(String message, String title, int ntype, String whatid, String nid) {

        NotificationResponeData modelNotification = new NotificationResponeData();
        modelNotification.setBody(message);
        modelNotification.setTitle(title);
        modelNotification.setId(nid);
        modelNotification.setContentId(whatid);
        modelNotification.setNotificationType(ntype);


        mNotificationId = PreferencesManager.getInstance().getNotificationId();
        mNotificationId++;
        PreferencesManager.getInstance().setNotificationId(mNotificationId);

        Intent notificationIntent = null;
        PendingIntent simplePendingIntent = null;

        switch (NotificationType.values()[ntype]) {

            case TEXT:
                notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
//                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                notificationIntent.putExtra(Constants.BUNDLE_NOTIFICATION_MODEL, modelNotification);
                notificationIntent.putExtra(Constants.BUNDLE_KEY_NOTIFICATION_ID, mNotificationId);
                simplePendingIntent =
                        PendingIntent.getActivity(getApplicationContext(), mNotificationId,
                                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                break;

            case LOGIN:
                notificationIntent = new Intent(getApplicationContext(), SplashActivty.class);
//              notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                notificationIntent.putExtra(Constants.BUNDLE_NOTIFICATION_MODEL, modelNotification);
                notificationIntent.putExtra(Constants.BUNDLE_KEY_NOTIFICATION_ID, mNotificationId);
                simplePendingIntent =
                        PendingIntent.getActivity(getApplicationContext(), mNotificationId,
                                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                break;

            case OTHER:

                notificationIntent = new Intent(getApplicationContext(), NotificationHandlerActivity.class);
//                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                notificationIntent.putExtra(Constants.BUNDLE_NOTIFICATION_MODEL, modelNotification);
                notificationIntent.putExtra(Constants.BUNDLE_KEY_NOTIFICATION_ID, mNotificationId);
                simplePendingIntent =
                        PendingIntent.getActivity(getApplicationContext(), mNotificationId,
                                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                break;
        }

        Notification notification = null;
        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        if (ntype != NotificationType.TEXT.ordinal()) {
            stackBuilder.addNextIntent(new Intent(getApplicationContext(), MainActivity.class));
//                stackBuilder.addParentStack(HomeScreenActivity.this);
        }
        stackBuilder.addNextIntent(notificationIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setColor(getResources().getColor(R.color.pink))
                    .setContentIntent(simplePendingIntent)
                    .build();
        } else {
            notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentIntent(simplePendingIntent)
                    .build();
        }


        notification.defaults |= Notification.DEFAULT_SOUND;

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;

//        final PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(2, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(resultPendingIntent);
        final NotificationManager mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notification.contentIntent = simplePendingIntent;
        mNotificationManager.notify(mNotificationId, notification);


    }


    // if u handle notification by app foreground or background
    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent("pushNotification");
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new com.pulse.brag.NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            // If the app is in background, firebase itself handles the notification
        }
    }
}

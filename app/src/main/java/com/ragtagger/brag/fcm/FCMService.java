package com.ragtagger.brag.fcm;


/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ragtagger.brag.BragApp;
import com.ragtagger.brag.R;
import com.ragtagger.brag.data.IDataManager;
import com.ragtagger.brag.data.model.datas.DataNotification;
import com.ragtagger.brag.ui.main.MainActivity;
import com.ragtagger.brag.ui.notification.handler.NotificationHandlerActivity;
import com.ragtagger.brag.ui.splash.SplashActivity;
import com.ragtagger.brag.utils.Constants;
import com.ragtagger.brag.utils.NotificationUtils;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

/**
 * Created by nikhil.vadoliya on 15-11-2017.
 */


public class FCMService extends FirebaseMessagingService {

    @Inject
    IDataManager mDataManager;


    private NotificationUtils notificationUtils;

    NotificationManager mNotificationManager;

    private String MESSAGE = "message";
    private String TITLE = "title";
    private String N_TYPE = "notificationType";
    private String WHAT_ID = "whatId";
    private String N_ID = "notificationId";
    private int mNotificationId;

    public static final String ANDROID_CHANNEL_ID = "com.ragtagger.brag.ANDROID";
    public static final String ANDROID_CHANNEL_NAME = "Orders and Notify ";

    @Override
    public void onCreate() {
        super.onCreate();
        ((BragApp) getApplication()).getAppComponent().inject(this);
    }

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

        mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        DataNotification modelNotification = new DataNotification();
        modelNotification.setBody(message);
        modelNotification.setTitle(title);
        modelNotification.setId(nid);
        modelNotification.setContentId(whatid);
        modelNotification.setNotificationType(ntype);


        mNotificationId = mDataManager.getNotificationId();
        mNotificationId++;
        mDataManager.setNotificationId(mNotificationId);

        Intent notificationIntent = null;
        PendingIntent simplePendingIntent = null;

        switch (Constants.NotificationType.values()[ntype]) {

            case TEXT:
                notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                //notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                notificationIntent.putExtra(Constants.BUNDLE_NOTIFICATION_MODEL, modelNotification);
                notificationIntent.putExtra(Constants.BUNDLE_KEY_NOTIFICATION_ID, mNotificationId);
                notificationIntent.putExtra(Constants.BUNDLE_KEY_NOTIFICATION_WHAT_ID, whatid);
                simplePendingIntent =
                        PendingIntent.getActivity(getApplicationContext(), mNotificationId,
                                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                break;

            case USER:
                notificationIntent = new Intent(getApplicationContext(), SplashActivity.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                notificationIntent.putExtra(Constants.BUNDLE_NOTIFICATION_MODEL, modelNotification);
                notificationIntent.putExtra(Constants.BUNDLE_KEY_NOTIFICATION_ID, mNotificationId);
                notificationIntent.putExtra(Constants.BUNDLE_KEY_NOTIFICATION_WHAT_ID, whatid);
                simplePendingIntent =
                        PendingIntent.getActivity(getApplicationContext(), mNotificationId,
                                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                break;

            case ORDER:
                notificationIntent = new Intent(getApplicationContext(), NotificationHandlerActivity.class);
                notificationIntent.putExtra(Constants.BUNDLE_NOTIFICATION_MODEL, modelNotification);
                notificationIntent.putExtra(Constants.BUNDLE_KEY_NOTIFICATION_ID, mNotificationId);
                notificationIntent.putExtra(Constants.BUNDLE_KEY_NOTIFICATION_WHAT_ID, whatid);
                simplePendingIntent =
                        PendingIntent.getActivity(getApplicationContext(), mNotificationId,
                                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                break;

            case ITEM:
                notificationIntent = new Intent(getApplicationContext(), NotificationHandlerActivity.class);
                notificationIntent.putExtra(Constants.BUNDLE_NOTIFICATION_MODEL, modelNotification);
                notificationIntent.putExtra(Constants.BUNDLE_KEY_NOTIFICATION_ID, mNotificationId);
                simplePendingIntent =
                        PendingIntent.getActivity(getApplicationContext(), mNotificationId,
                                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                break;
        }

        Notification notification = null;
        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        if (NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(new Intent(getApplicationContext(), MainActivity.class));
        }
        stackBuilder.addNextIntent(notificationIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
            notification = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setColor(getColor(R.color.semi_pink))
                    .setContentIntent(simplePendingIntent)
                    .build();


        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setColor(getResources().getColor(R.color.semi_pink))
                    .setContentIntent(simplePendingIntent)
                    .build();
        } else {
            notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentIntent(simplePendingIntent)
                    .build();
        }


        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;

        //final PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(2, PendingIntent.FLAG_UPDATE_CURRENT);
        //builder.setContentIntent(resultPendingIntent);

        notification.contentIntent = simplePendingIntent;
        mNotificationManager.notify(mNotificationId, notification);


      /*  if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            BragApp.NotificationNumber++;
            Intent intent = new Intent(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }*/
    }


    // if u handle notification by app foreground or background
    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent("pushNotification");
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            // If the app is in background, firebase itself handles the notification
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels() {

        NotificationChannel notificationChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setShowBadge(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        mNotificationManager.createNotificationChannel(notificationChannel);
    }
}

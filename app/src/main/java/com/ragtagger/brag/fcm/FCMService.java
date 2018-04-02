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


    NotificationManager mNotificationManager;
    NotificationChannel mNotificationChannel;

    private String MESSAGE = "message";
    private String TITLE = "title";
    private String N_TYPE = "notificationType";
    private String WHAT_ID = "whatId";
    private String N_ID = "notificationId";
    private String BADGE = "badge";
    private int mNotificationId;
    private int notificationCount = 0;

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
                remoteMessage.getData().get(N_ID), Integer.parseInt(remoteMessage.getData().get(BADGE)));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(String message, String title, int ntype, String whatid, String nid, int badge) {
        BragApp.NotificationNumber = badge;
        //update in More screen list
        Intent intent = new Intent(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION_PUSH_MORE);
        LocalBroadcastManager.getInstance(FCMService.this).sendBroadcast(intent);

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
        notificationCount++;

        mDataManager.setNotificationId(mNotificationId);

        Intent notificationIntent = null;
        PendingIntent simplePendingIntent = null;

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            Intent backIntent = new Intent(FCMService.this, MainActivity.class);
            backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

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

            if (mNotificationChannel == null) {
                createChannels();
            }
            mNotificationManager.createNotificationChannel(mNotificationChannel);

            notification = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setColor(getColor(R.color.semi_pink))
                    .setNumber(notificationCount)
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


        notification.contentIntent = simplePendingIntent;
        mNotificationManager.notify(mNotificationId, notification);


      /*  if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            BragApp.NotificationNumber++;
            Intent intent = new Intent(Constants.LOCALBROADCAST_UPDATE_NOTIFICATION);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }*/
    }


    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels() {


        mNotificationChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        mNotificationChannel.enableLights(true);
        mNotificationChannel.setLightColor(Color.RED);
        mNotificationChannel.setShowBadge(true);
        mNotificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

    }
}
